package com.github.karayuu.listener.listeners

import com.github.karayuu.menu.Menu
import com.github.karayuu.menu.MenuReopsitory
import com.github.karayuu.util.toPlayerData
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
class MenuListener : Listener {
    @EventHandler
    fun onNormal(event: InventoryClickEvent) {
        val playerData = (event.whoClicked as Player).toPlayerData() ?: return
        val menu = event.inventory.holder as? Menu ?: return

        if (event.clickedInventory != null) {
            event.isCancelled = true
            menu.getButton(playerData, event.slot)?.onClick(playerData, event)
        }
    }

    @EventHandler
    fun onNormal(event: PlayerInteractEvent) {
        //アイテムがない場合終了
        event.item ?: return

        val trigger = when (event.action) {
            Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> MenuReopsitory.Trigger.RIGHT_CLICK
            Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK -> MenuReopsitory.Trigger.LEFT_CLICK
            else -> return
        }

        event.player.toPlayerData()?.let { playerData ->
            MenuReopsitory.getMenuToOpen(playerData, event, trigger)?.let { menu ->
                event.isCancelled = true
                menu.openMenu(playerData)
                menu.init(playerData)
            }
        }
    }
}
