package com.github.karayuu.creategridregionplugin.menu.menus.grid

import com.github.karayuu.creategridregionplugin.menu.InventoryProperty
import com.github.karayuu.creategridregionplugin.menu.MenuSessionWithSound
import com.github.karayuu.creategridregionplugin.menu.menus.grid.buttons.*
import com.github.karayuu.creategridregionplugin.util.SoundConfiguration
import com.github.karayuu.creategridregionplugin.util.direction.RelativeDirection
import com.github.karayuu.creategridregionplugin.util.select
import com.github.karayuu.creategridregionplugin.util.selection.GridSelection
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryView
import kotlin.properties.Delegates

/**
 * グリッド保護作成用のGUIセッションを表すクラスです。
 *
 * [gridSelection]を`public`な内部状態として持ち、これに新しい[GridSelection]のインスタンスを代入することで
 * メニューの再描画と領域の再選択が行えます。
 */
class GridMenuSession(private val issueTargetPlayer: Player): MenuSessionWithSound() {
    override val openingSound = SoundConfiguration(Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)
    override val title = "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}グリッド式保護メニュー"
    override val inventoryProperty = InventoryProperty(InventoryType.DISPENSER)

    private val extendOrReduceButtonFactory = { selection: GridSelection, direction: RelativeDirection, iconData: Int ->
        object: GridExtendOrReduceButton(direction, issueTargetPlayer, selection, this) {
            override val iconData = iconData
        }
    }

    var gridSelection: GridSelection by Delegates.observable(GridSelection()) { _, _, newSelection ->
        updateStateWith(newSelection)
    }

    init {
        updateStateWith(gridSelection)
    }

    private fun updateStateWith(newSelection: GridSelection) {
        issueTargetPlayer.select(newSelection.toWorldEditSelection(issueTargetPlayer.location))

        super.buttons = mapOf(
                0 to UnitChangeToggleButton(newSelection, this),
                1 to extendOrReduceButtonFactory(newSelection, RelativeDirection.AHEAD, 14),
                3 to extendOrReduceButtonFactory(newSelection, RelativeDirection.LEFT, 10),
                4 to CurrentGridSelectionButton(newSelection),
                5 to extendOrReduceButtonFactory(newSelection, RelativeDirection.RIGHT, 5),
                6 to ResetButton(this),
                7 to extendOrReduceButtonFactory(newSelection, RelativeDirection.BEHIND, 13),
                8 to CreateRegionButton(issueTargetPlayer)
        )
    }

    /**
     * 発行対象プレーヤーにインベントリを送信する
     */
    fun send(): InventoryView = issueTargetPlayer.openInventory(super.getInventory())

    /**
     * グリッド選択領域をリセットする
     */
    fun resetState() {
        gridSelection = GridSelection()
    }
}