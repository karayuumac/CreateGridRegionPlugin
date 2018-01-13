package com.github.karayuu.creategridregionplugin.player

import com.github.karayuu.creategridregionplugin.player.property.GridRegion
import com.github.karayuu.creategridregionplugin.util.toPlayer
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
data class PlayerData(private val uuid: UUID) {
    /** プレイヤー */
    val player: Player
        get() = uuid.toPlayer()!!

    /** プレイヤー名 */
    val name: String
        get() = player.name

    /** グリッド式保護 */
    val gridRegion = GridRegion(uuid)
}
