package com.github.karayuu.creategridregionplugin.menu

import com.github.karayuu.creategridregionplugin.menu.component.Button
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * メニューを発行するクラスを抽象化したものです。
 * 実装するクラスは[buttonMap]にスロットとボタンの対応を示してください。
 *
 * @author karayuu
 * @author kory33
 */
abstract class MenuIssuer: InventoryHolder {
    /** メニューのスロットIDとボタンの対応関係を示すMap */
    abstract val buttonMap: Map<Int, Button>

    /** Menuのタイトル */
    abstract val title: String

    /** インベントリのサイズまたはタイプを与えるプロパティ */
    open val inventoryProperty = InventoryProperty(InventoryType.CHEST)

    /**
     * 発行したメニューが開かれたときのアクションを実行します
     */
    abstract fun onMenuOpen(event: InventoryOpenEvent)

    /**
     * 発行したメニューが閉じられたときのアクションを実行します
     */
    abstract fun onMenuClose(event: InventoryCloseEvent)

    /**
     * スロット番号から、関連付けられたアクションを取得します。
     * @param slotId Menu内のslot番号
     * @return 指定スロットのボタンに関連付けられたアクションを取得します。
     * スロットに何もセットされていないなら空のラムダを返します。
     */
    fun getBoundAction(slotId: Int) = buttonMap[slotId]?.action ?: {}

    /**
     * インベントリを取得します。
     * @return inventory
     */
    override fun getInventory(): Inventory = inventoryProperty.createInventoryWith(this, title).also { inventory ->
        buttonMap.filterKeys { it < inventoryProperty.toSize() }.forEach { (slotNumber, button) ->
            inventory.setItem(slotNumber, button.icon.itemStack)
        }
    }
}
