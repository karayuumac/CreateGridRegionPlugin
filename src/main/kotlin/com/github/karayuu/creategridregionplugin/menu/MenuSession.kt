package com.github.karayuu.creategridregionplugin.menu

import com.github.karayuu.creategridregionplugin.menu.component.Button
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import kotlin.properties.Delegates

/**
 * インタラクティブなGUIメニューのセッションを表すクラスです。
 * このクラスは`protected`な内部状態として、GUIとして機能する内部インベントリ内のボタン配置を与える[buttons]を持ちます。
 *
 * [buttons]に新しいインスタンスが代入されるたびに内部インベントリが更新され、
 * Bukkitの仕様によりこのインベントリを見ているプレーヤー全員に対してメニューの再描画が行われます。
 *
 * 派生クラスは、[onMenuOpen]と[onMenuOpen]をメニューインベントリ開閉時のアクションとして定義することができます。
 *
 * @author kory33
 */
abstract class MenuSession: InventoryHolder {
    /** メニューインベントリのタイトル */
    abstract val title: String

    /** インベントリのサイズまたはタイプを与えるプロパティ */
    open val inventoryProperty = InventoryProperty(InventoryType.CHEST)

    /**
     * メニューのスロットIDとボタンの対応関係を示すMapです。
     * このフィールドに新しいMapが代入されるたびにメニューインベントリの更新が行われます。
     */
    protected var buttons: Map<Int, Button> by Delegates.observable(HashMap()) { _, _, newButtonMap ->
        sessionInventory.synchronizeTo(newButtonMap)
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

    /**
     * メニューのインベントリが開かれたときのアクションを実行します
     */
    abstract fun onMenuOpen(event: InventoryOpenEvent)

    /**
     * メニューのインベントリが閉じられたときのアクションを実行します
     */
    abstract fun onMenuClose(event: InventoryCloseEvent)

    override fun getInventory() = sessionInventory

    private fun Inventory.synchronizeTo(buttons: Map<Int, Button>): Inventory {
        this.clear()
        val validButtons = buttons.filterKeys { it < inventoryProperty.toSize() }
        validButtons.forEach { slotNumber, button -> this.setItem(slotNumber, button.icon.itemStack) }
        return this
    }
}