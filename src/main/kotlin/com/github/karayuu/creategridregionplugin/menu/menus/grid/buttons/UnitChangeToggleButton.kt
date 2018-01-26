package com.github.karayuu.creategridregionplugin.menu.menus.grid.buttons

import com.github.karayuu.creategridregionplugin.menu.component.Button
import com.github.karayuu.creategridregionplugin.menu.component.Icon
import com.github.karayuu.creategridregionplugin.menu.menus.grid.GridMenuSession
import com.github.karayuu.creategridregionplugin.util.selection.GridSelection
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * ユニット数の増減量をトグルするボタンを表すクラスです。
 */
class UnitChangeToggleButton(gridSelection: GridSelection,
                             private val menuSession: GridMenuSession): Button {
    override val icon = Icon(
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

    override val action: (InventoryClickEvent) -> Unit = {
        menuSession.gridSelection = menuSession.gridSelection.toggleUnitChange()
    }
}
