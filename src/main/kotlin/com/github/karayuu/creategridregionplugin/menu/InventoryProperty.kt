package com.github.karayuu.creategridregionplugin.menu

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * インベントリのタイプまたはサイズを保持しているクラスです。
 *
 * @author
 */
class InventoryProperty {
    private val type: InventoryType?
    private val size: Int?

    private constructor(type: InventoryType?, size: Int?) {
        this.type = type
        this.size = size
    }

    private fun notifyIllegalState(): Nothing = throw IllegalStateException("null has been passed to the constructor!")

    constructor(type: InventoryType): this(type, null)

    constructor(size: Int): this(null, size)

    fun createInventoryWith(inventoryHolder: InventoryHolder, title: String): Inventory = when {
        this.type == null -> Bukkit.createInventory(inventoryHolder, this.size!!, title)
        this.size == null -> Bukkit.createInventory(inventoryHolder, this.type, title)
        else -> notifyIllegalState()
    }

    fun toSize() = this.size ?: (this.type?.defaultSize ?: notifyIllegalState())
}