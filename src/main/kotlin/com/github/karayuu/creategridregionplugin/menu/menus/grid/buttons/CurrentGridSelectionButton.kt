package com.github.karayuu.creategridregionplugin.menu.menus.grid.buttons

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.github.karayuu.creategridregionplugin.menu.component.Button
import com.github.karayuu.creategridregionplugin.menu.component.Icon
import com.github.karayuu.creategridregionplugin.util.selection.GridSelection
import com.github.karayuu.creategridregionplugin.util.direction.RelativeDirection
import com.github.karayuu.creategridregionplugin.util.direction.RelativeDirection.*
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import java.text.NumberFormat

/**
 * [GridSelection]の情報を確認するためのボタン
 */
class CurrentGridSelectionButton(private val gridSelection: GridSelection): Button {
    private val numberFormat = NumberFormat.getNumberInstance()

    private fun GridSelection.loreForDirectionOf(direction: RelativeDirection) =
            "${ChatColor.GRAY}${direction.localizedName}方向：" +
                    "${ChatColor.AQUA}${selectionSize[direction]}${ChatColor.GRAY}ユニット" +
                    "(${ChatColor.AQUA}${numberFormat.format(selectionSize[direction] * 15)}${ChatColor.GRAY}ブロック)"

    override val icon = Icon(
            Material.STAINED_GLASS_PANE,
            damage = 11,
            name = "${ChatColor.DARK_GREEN}現在の設定",
            lore = getIconLore()
    )

    private fun getIconLore(): List<String> {
        val directionLore = listOf(AHEAD, BEHIND, RIGHT, LEFT).map { gridSelection.loreForDirectionOf(it) }
        val unitInformationLore = listOf(
                "${ChatColor.GRAY}保護ユニット数：${ChatColor.AQUA}${gridSelection.selectionSize.calculateTotalUnits()}",
                "${ChatColor.GRAY}保護ユニット上限値：${ChatColor.RED}${CreateGridRegionPlugin.configFile.unitLimit}"
        )

        return directionLore + unitInformationLore
    }

    override val action: (InventoryClickEvent) -> Unit = {}
}