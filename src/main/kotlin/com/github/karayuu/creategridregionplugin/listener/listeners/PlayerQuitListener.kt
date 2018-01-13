package com.github.karayuu.creategridregionplugin.listener.listeners

import com.github.karayuu.creategridregionplugin.player.PlayerRepository
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
class PlayerQuitListener : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun playerQuit(event: PlayerQuitEvent) {
        PlayerRepository.quit(event.player)
    }
}
