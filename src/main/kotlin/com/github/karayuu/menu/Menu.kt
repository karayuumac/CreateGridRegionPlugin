package com.github.karayuu.menu

import com.github.karayuu.player.PlayerData
import com.github.karayuu.util.SoundConfiguration
import com.github.karayuu.util.playSound
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * メニュー作成用抽象クラス。
 */
abstract class Menu : InventoryHolder {
    /** メニューのボタンのMap */
    private val buttonMap : MutableMap<Int, Button> = mutableMapOf()

    /** インベントリのタイプ */
    open val type = InventoryType.CHEST
    /** インベントリのサイズ */
    open val size by lazy {
        type.defaultSize
    }
    /** プレイヤーがメニューを開いた際の音 */
    open val openSound = SoundConfiguration(Sound.BLOCK_FENCE_GATE_OPEN, 1F, 0.1F)
    /** プレイヤーがメニューを閉じた際の音 */
    open val closeSound = SoundConfiguration(Sound.BLOCK_FENCE_GATE_OPEN, 1F, 0.1F)

    /**
     * メニューのボタンのMapを取得します。
     * @return buttonMap(Map型)
     */
    protected fun getButtonMap() = buttonMap.toMap()

    /**
     * Menuのタイトルを取得します。
     * @param playerData Menuを開いているplayerのplayerData
     * @return Menuのタイトル
     */
    abstract fun getTitle(playerData: PlayerData) : String

    /**
     * Menuを開いた際の初期化処理を取得します。
     * reope時には実行されません。最初に開いた1回目だけで実行したい動作を実装してください。
     * reopen時にも実行したい初期化動作はinit{}を使用してください。
     * @param playerData Menuを開いているplayerのplayerData
     */
    abstract fun init(playerData: PlayerData)

    /**
     * Menuにbuttonを登録します。
     * @param slot 登録するbuttonのMenu内でのスロット番号
     * @param button 登録するbutton
     */
    protected fun registButton(slot: Int, button: Button) {
        buttonMap.put(slot, button)
    }

    /**
     * スロット番号のボタンを取得します。
     * @param playerData Menuを開いているplayerのplayerData
     * @param slot Menu内のslot番号
     * @return 指定スロットのbutton
     */
    open fun getButton(playerData: PlayerData, slot: Int) : Button? = buttonMap[slot]

    /**
     * Menuを開きます。
     * @param playerData Menuを開くプレイヤーのplayerData
     * @param playSound Menuを開く際音を鳴らすか否か
     */
    fun openMenu(playerData: PlayerData, playSound: Boolean = true) {
        init(playerData)
        playerData.player.openInventory(getInventory(playerData))
        if (playSound) playerData.player.playSound(openSound)
    }

    /**
     * インベントリを取得します。
     * @param playerData Menuを開くplayerのplayerData
     * @return inventory
     */
    open protected fun getInventory(playerData: PlayerData) : Inventory {
        val inventory = if (type == InventoryType.CHEST)
            Bukkit.createInventory(this, size, getTitle(playerData))
        else
            Bukkit.createInventory(this, type, getTitle(playerData))

        buttonMap.forEach { entry ->
            entry.value.getItemStack(playerData).let { itemStack ->
                inventory.setItem(entry.key, itemStack)
            }
        }
        return inventory
    }

    /**
     * Menuを開きなおします。init処理は実行されません。
     * @param playerData Menuを開くplayerのplayerData
     */
    fun reopen(playerData: PlayerData) {
        playerData.player.openInventory(getInventory(playerData))
    }

    override fun getInventory(): Inventory? = null

    /**
     * ボタンのインターフェース
     */
    interface Button {
        /**
         * Menuに表示されるItemStackを取得します。
         * @param playerData Menuを開いているplayerのplayerData
         * @return Itemstack
         */
        fun getItemStack(playerData: PlayerData) : ItemStack

        /**
         * ボタンをクリックした際に呼び出されます。
         * @param playerData クリックしたplayerのplayerData
         * @param event クリック時のInventoryClickEvent
         */
        fun onClick(playerData: PlayerData, event: InventoryClickEvent)
    }
}
