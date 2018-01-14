package com.github.karayuu.creategridregionplugin.menu.component

import org.bukkit.event.inventory.InventoryClickEvent

/**
 * ボタンを標準化したインターフェースです。
 * @author kory33
 */
class Button(val icon: Icon, val action: (InventoryClickEvent) -> Unit)