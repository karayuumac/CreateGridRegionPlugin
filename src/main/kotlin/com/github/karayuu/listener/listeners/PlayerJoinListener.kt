package com.github.karayuu.listener.listeners

import com.github.karayuu.player.PlayerRepository
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
class PlayerJoinListener : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun addToLoadedPlayerList(event: PlayerJoinEvent) {
        PlayerRepository.join(event.player)
    }
}
