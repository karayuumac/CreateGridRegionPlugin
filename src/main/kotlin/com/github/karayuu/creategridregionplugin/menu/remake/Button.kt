package com.github.karayuu.creategridregionplugin.menu.remake

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * ボタンを標準化したインターフェースです。
 * @author kory33
 */
interface Button {
    /**
     * Menuに表示されるItemStackを取得します。
     * @return ItemStack
     */
    fun getItemStack() : ItemStack

    /**
     * ボタンをクリックした際に呼び出されます。
     * @param event クリック時のInventoryClickEvent
     */
    fun onClick(event: InventoryClickEvent)
}