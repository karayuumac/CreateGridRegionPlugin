package com.github.karayuu.creategridregionplugin.menu.remake

import org.bukkit.event.inventory.InventoryClickEvent

/**
 * ボタンを標準化したインターフェースです。
 * @author kory33
 */
class Button(val icon: Icon, val action: (InventoryClickEvent) -> Unit)