/*
package com.github.karayuu.yml.ymls

import com.github.karayuu.CreateGridRegionPlugin
import com.github.karayuu.yml.SimpleYml
import org.bukkit.entity.Player

/**
 * Created by karayuu on 2017/12/26
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * Config用Yml
 */
object ConfigYml : SimpleYml() {
    /** グリッド式保護最大Unit */
    val unitLimit: Int by lazy { fc.getInt("unitLimit") }

    /**
     * プレイヤーの次に指定すべきRegionの番号を保存します
     * @param player Player
     * @param num Regionの番号
     */
    fun setPlayerRegionNum(player: Player, num: Int) {
        if (!fc.isConfigurationSection("playerData.$player")) {
            fc.createSection("player.$player")
        }
        fc.set("player.$player", num)
    }

    /**
     * プレイヤーの次に指定すべきRegionの番号を取得します
     * @param player Player
     */
    fun getPlayerRegionNum(player: Player) : Int = fc.getInt("player.$player")
}
*/
