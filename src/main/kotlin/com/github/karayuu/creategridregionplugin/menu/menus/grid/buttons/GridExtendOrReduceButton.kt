package com.github.karayuu.creategridregionplugin.menu.menus.grid.buttons

import com.github.karayuu.creategridregionplugin.menu.component.Button
import com.github.karayuu.creategridregionplugin.menu.component.Icon
import com.github.karayuu.creategridregionplugin.menu.menus.grid.GridMenuIssuer
import com.github.karayuu.creategridregionplugin.util.direction.RelativeDirection
import com.github.karayuu.creategridregionplugin.util.direction.cardinalDirectionOn
import com.github.karayuu.creategridregionplugin.util.openInventoryOf
import com.github.karayuu.creategridregionplugin.util.selection.GridSelection
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * グリッド領域を拡大または縮小するボタンを表すクラス。
 * 実装するクラスはアイコンのアイテムの詳細を与える必要があります。
 */
abstract class GridExtendOrReduceButton(private val operationDirection: RelativeDirection,
                                        viewingPlayer: Player,
                                        private val gridSelection: GridSelection,
                                        private val menuIssuer: GridMenuIssuer): Button {
    override val icon: Icon by lazy {
        Icon(
                Material.STAINED_GLASS_PANE,
                damage = iconData,
                name = iconName,
                lore = iconLore
        )
    }

    override val action: (InventoryClickEvent) -> Unit = { event ->
        val newGridSelection = when {
            event.isLeftClick -> gridSelection.safeExtendAlong(operationDirection)
            event.isRightClick -> gridSelection.safeReduceAlong(operationDirection)
            else -> gridSelection
        }

        event.whoClicked.openInventoryOf(menuIssuer.replicateWith(newGridSelection))
    }

    private val iconName = "${ChatColor.DARK_GREEN}${operationDirection.localizedName}に" +
            "${gridSelection.unitChange}ユニット増やす/減らす"

    private val iconLore = arrayListOf(
            "${ChatColor.GREEN}左クリックで増加",
            "${ChatColor.RED}右クリックで減少",
            "${ChatColor.GRAY}---------------",
            "${ChatColor.GRAY}方向：${ChatColor.AQUA}${viewingPlayer.location.cardinalDirectionOn(operationDirection).localizedName}"
    ).also {
        if (!gridSelection.canExtendAlong(operationDirection)) {
            it.add("${ChatColor.RED}${ChatColor.UNDERLINE}これ以上拡大できません")
        }

        if (!gridSelection.canReduceAlong(operationDirection)) {
            it.add("${ChatColor.RED}${ChatColor.UNDERLINE}これ以上縮小できません")
        }
    }

    /**
     * アイコンに設定するデータ値
     */
    abstract val iconData: Int
}