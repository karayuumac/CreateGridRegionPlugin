package com.github.karayuu.creategridregionplugin.menu.menus.grid.buttons

import com.github.karayuu.creategridregionplugin.menu.component.Button
import com.github.karayuu.creategridregionplugin.menu.component.Icon
import com.github.karayuu.creategridregionplugin.menu.menus.grid.GridMenuIssuerR
import com.github.karayuu.creategridregionplugin.util.selection.GridSelection
import com.github.karayuu.creategridregionplugin.util.openInventoryOf
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * ユニット数の増減量をトグルするボタンを表すクラスです。
 */
class UnitChangeToggleButton(private val gridSelection: GridSelection,
                             private val menuIssuer: GridMenuIssuerR): Button {
    override val icon = getIcon(gridSelection)

    override val action: (InventoryClickEvent) -> Unit = { event ->
        val newGridSelection = gridSelection.toggleUnitChange()
        event.whoClicked.openInventoryOf(menuIssuer.replicateWith(newGridSelection))
    }

    private fun getIcon(gridSelection: GridSelection) = Icon(
            Material.STAINED_GLASS_PANE,
            name = "${ChatColor.GREEN}拡張単位の変更",
            lore = listOf(
                    "${ChatColor.GREEN}現在のユニット指定量",
                    "${ChatColor.AQUA}${gridSelection.unitChange}${ChatColor.GREEN}ユニット" +
                            "(${ChatColor.AQUA}${gridSelection.unitChange.toBlockWidth()}${ChatColor.GREEN}ブロック)" +
                            "/1クリック",
                    "${ChatColor.RED}${ChatColor.UNDERLINE}クリックで変更"
            )
    )

}
