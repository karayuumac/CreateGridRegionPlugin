package com.github.karayuu.creategridregionplugin.listener.listeners

import com.github.karayuu.creategridregionplugin.menu.MenuIssuer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
class MenuListener : Listener {
    @EventHandler
    fun onMenuClick(event: InventoryClickEvent) {
        // インベントリの発行者がMenuIssuerでないならreturn
        val menuIssuer = event.inventory?.holder as? MenuIssuer ?: return

        menuIssuer.getBoundAction(event.slot).invoke(event)

        event.isCancelled = true
    }
}
