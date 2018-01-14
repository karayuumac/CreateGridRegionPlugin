package com.github.karayuu.creategridregionplugin.util

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.github.karayuu.creategridregionplugin.player.PlayerRepository
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.InventoryView
import java.util.*

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * uuidからプレイヤーを取得します。
 * @param uuid 検索したいUUID
 * @return 該当player
 */
fun getPlayer(uuid: UUID) : Player? = CreateGridRegionPlugin.plugin.server.getPlayer(uuid)

/**
 * uuidからプレイヤーを取得する拡張関数
 * @return 該当プレイヤー
 */
fun UUID.toPlayer() : Player? = getPlayer(this)

fun Player.playSound(soundConfig: SoundConfiguration) =
        playSound(this.location, soundConfig.sound, soundConfig.volume, soundConfig.pitch)

/**
 * プレイヤーにメッセージを送信します
 * @param player 送信するプレイヤー
 * @param msg 送信するメッセージ
 */
fun sendMessage(player: Player?, msg: String) = player?.sendMessage(msg)

/**
 * uuidからプレイヤーの名前を取得します
 * @param uuid 検索するプレイヤー
 * @return プレイヤー名 (if @null then return "No player")
 */
fun getName(uuid: UUID): String = getPlayer(uuid)?.name ?: "No Player"

/**
 * Playerからプレイヤーデータを取得します。
 * @return 該当するプレイヤーのplayerData
 */
fun Player.toPlayerData() = PlayerRepository.getPlayerData(this)

/**
 * Playerが与えられた[inventoryHolder]のインベントリを開きます。
 * @param [inventoryHolder] 開くインベントリの所有者
 * @return 開かれたインベントリに対応する[InventoryView]
 */
fun Player.openInventoryOf(inventoryHolder: InventoryHolder) = inventoryHolder.inventory?.let { openInventory(it) }
