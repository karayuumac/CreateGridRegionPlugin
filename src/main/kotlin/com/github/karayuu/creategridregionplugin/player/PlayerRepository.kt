package com.github.karayuu.creategridregionplugin.player

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.github.karayuu.creategridregionplugin.util.sendMessage
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * プレイヤーデータの総合管理オブジェクト
 */
object PlayerRepository {
    /** ロード済みPlayerMap */
    private val loadedPlayerMap = mutableMapOf<UUID, PlayerData>()

    /**
     * playerのプレイヤーデータを取得します。
     * @param player playerDataがほしいplayer
     * @return playerData
     */
    fun getPlayerData(player: Player) = getPlayerData(player.uniqueId)

    fun getPlayerData(uuid: UUID) : PlayerData? = loadedPlayerMap[uuid]

    /*
    /**
     * uuidに該当するプレイヤーのプレイヤーデータがロード中か返します。
     * @param uuid 捜索するuuid
     * @return ロード中: true / それ以外: false
     */
    private fun isloading(uuid: UUID) = loadingPlayersList.contains(uuid)

    /**
     * uuidに該当するプレイヤーのプレイヤーデータがロード待機中か返します。
     * @param uuid 捜索するuuid
     * @return ロード待機中: true / それ以外: false
     */
    private fun isWaiting(uuid: UUID) = waitingPlayersList.contains(uuid)
    */

    /**
     * プレイヤー参加時にロード済みマップに追加します。
     * @param player 参加したplayer
     */
    fun join(player: Player) {
        val uuid = player.uniqueId
        if (!loadedPlayerMap.contains(uuid)) {
            sendMessage(player, "${ChatColor.GREEN}プレイヤーデータをロードしています。しばらくお待ちください．．．")
            loadedPlayerMap.put(uuid, PlayerData(uuid))
            sendMessage(player, "${ChatColor.GREEN}プレイヤーデータロード完了！")
        }
    }

    /**
     * プレイヤー退出時にロード済みマップからプレイヤーデータを削除します
     * @param player 退出したプレイヤー
     */
    fun quit(player: Player) {
        val uuid = player.uniqueId
        loadedPlayerMap.remove(uuid)
    }

    fun onEnable() {
        CreateGridRegionPlugin.plugin.server.onlinePlayers.forEach { player -> join(player) }
    }
}
