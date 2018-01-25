package com.github.karayuu.creategridregionplugin.menu.menus.grid

import com.github.karayuu.creategridregionplugin.menu.MenuSession
import com.github.karayuu.creategridregionplugin.menu.menus.grid.buttons.*
import com.github.karayuu.creategridregionplugin.util.direction.RelativeDirection
import com.github.karayuu.creategridregionplugin.util.selection.GridSelection
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryView
import kotlin.properties.Delegates

class GridMenuSession(private val issueTargetPlayer: Player): MenuSession() {
    override val title = "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}グリッド式保護メニュー"

    init {
        super.buttons = computeButtons()
    }

    var gridSelection: GridSelection by Delegates.observable(GridSelection()) { _, _, _ ->
        super.buttons = computeButtons()
    }

    private val extendOrReduceButtonFactory = { direction: RelativeDirection, iconData: Int ->
        object: GridExtendOrReduceButton(direction, issueTargetPlayer, gridSelection, this) {
            override val iconData = iconData
        }
    }

    private fun computeButtons() = mapOf(
            0 to UnitChangeToggleButton(gridSelection, this),
            1 to extendOrReduceButtonFactory(RelativeDirection.AHEAD, 14),
            3 to extendOrReduceButtonFactory(RelativeDirection.LEFT, 10),
            4 to CurrentGridSelectionButton(gridSelection),
            5 to extendOrReduceButtonFactory(RelativeDirection.RIGHT, 5),
            6 to ResetButton(),
            7 to extendOrReduceButtonFactory(RelativeDirection.BEHIND, 13),
            8 to CreateRegionButton(issueTargetPlayer)
    )

    /**
     * 発行対象プレーヤーにインベントリを送信する
     */
    fun send(): InventoryView = issueTargetPlayer.openInventory(super.getInventory())
}