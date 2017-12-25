package com.github.karayuu.player

import com.github.karayuu.util.toPlayer
import org.bukkit.entity.Player
import java.util.*

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
data class PlayerData(val uuid: UUID) {
    /** プレイヤー */
    val player: Player
        get() = uuid.toPlayer()!!

    /** プレイヤー名 */
    val name: String
        get() = player.name
}
