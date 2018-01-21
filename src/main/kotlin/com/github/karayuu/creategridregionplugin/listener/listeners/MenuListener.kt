package com.github.karayuu.creategridregionplugin.listener.listeners

import com.github.karayuu.creategridregionplugin.menu.MenuIssuer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.event.inventory.InventoryOpenEvent

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
class MenuListener : Listener {
    /**
     * @return イベントに関連付けられたインベントリの発行者が[MenuIssuer]ならそれを[MenuIssuer]にキャストしたもの、
     * それ以外はnullを返します。
     */
    private fun InventoryEvent.getMenuIssuer() = inventory?.holder as? MenuIssuer

    @EventHandler
    fun onMenuClick(event: InventoryClickEvent) {
        val menuIssuer = event.getMenuIssuer() ?: return

        menuIssuer.getBoundAction(event.slot).invoke(event)

        event.isCancelled = true
    }

    @EventHandler
    fun onMenuClose(event: InventoryCloseEvent) = event.getMenuIssuer()?.onMenuClose(event)

    @EventHandler
    fun onMenuOpen(event: InventoryOpenEvent) = event.getMenuIssuer()?.onMenuOpen(event)
}
