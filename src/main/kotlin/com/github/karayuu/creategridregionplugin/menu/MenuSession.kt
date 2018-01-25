package com.github.karayuu.creategridregionplugin.menu

import com.github.karayuu.creategridregionplugin.menu.component.Button
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import kotlin.properties.Delegates

abstract class MenuSession: InventoryHolder {
    /** メニューインベントリのタイトル */
    abstract val title: String

    /** インベントリのサイズまたはタイプを与えるプロパティ */
    open val inventoryProperty = InventoryProperty(InventoryType.CHEST)

    /** メニューのスロットIDとボタンの対応関係を示すMap */
    protected var buttons: Map<Int, Button> by Delegates.observable(HashMap()) { _, _, _ ->
        sessionInventory.synchronizeToButtonMap()
    }

    private val sessionInventory: Inventory by lazy {
        inventoryProperty.createInventoryWith(this, title)
    }

    /**
     * スロット番号から、関連付けられたアクションを取得します。
     * @param slotId Menu内のslot番号
     * @return 指定スロットのボタンに関連付けられたアクションを取得します。
     * スロットに何もセットされていないなら空のラムダを返します。
     */
    fun getBoundAction(slotId: Int) = buttons[slotId]?.action ?: {}

    override fun getInventory() = sessionInventory

    private fun Inventory.synchronizeToButtonMap(): Inventory {
        this.clear()
        val validButtons = buttons.filterKeys { it < inventoryProperty.toSize() }
        validButtons.forEach { slotNumber, button -> this.setItem(slotNumber, button.icon.itemStack) }
        return this
    }
}