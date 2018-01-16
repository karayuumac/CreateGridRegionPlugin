package com.github.karayuu.creategridregionplugin.menu.component

import org.bukkit.event.inventory.InventoryClickEvent

/**
 * メニュー上のボタンを標準化したインターフェースです。
 * @author kory33
 */
interface Button {
    val icon: Icon
    val action: (InventoryClickEvent) -> Unit
}