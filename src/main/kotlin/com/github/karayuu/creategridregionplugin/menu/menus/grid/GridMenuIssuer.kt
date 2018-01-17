package com.github.karayuu.creategridregionplugin.menu.menus.grid

import com.github.karayuu.creategridregionplugin.menu.MenuIssuerWithSound
import com.github.karayuu.creategridregionplugin.menu.component.Button
import com.github.karayuu.creategridregionplugin.menu.menus.grid.buttons.*
import com.github.karayuu.creategridregionplugin.util.selection.GridSelection
import com.github.karayuu.creategridregionplugin.util.direction.RelativeDirection
import com.github.karayuu.creategridregionplugin.util.SoundConfiguration
import com.github.karayuu.creategridregionplugin.util.select
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

/**
 * グリッドメニューの発行を担当するクラス
 * @author karayuu, kory33
 */
class GridMenuIssuer(private val issueTargetPlayer: Player,
                     private val gridSelection: GridSelection) : MenuIssuerWithSound() {
    override val size = InventoryType.DISPENSER.defaultSize

    override val openingSound = SoundConfiguration(Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)

    override val title = "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}グリッド式保護メニュー"

    /**
     * グリッド領域を拡大縮小するためのボタンのファクトリ関数
     */
    private val extendOrReduceButtonFactory = { direction: RelativeDirection, iconData: Int ->
        object: GridExtendOrReduceButton(direction, issueTargetPlayer, gridSelection, this) {
            override val iconData = iconData
        }
    }

    override val buttonMap: Map<Int, Button> = mapOf(
            0 to UnitChangeToggleButton(gridSelection, this),
            1 to extendOrReduceButtonFactory(RelativeDirection.AHEAD, 14),
            3 to extendOrReduceButtonFactory(RelativeDirection.LEFT, 10),
            4 to CurrentGridSelectionButton(gridSelection),
            5 to extendOrReduceButtonFactory(RelativeDirection.RIGHT, 5),
            6 to ResetButton(),
            5 to extendOrReduceButtonFactory(RelativeDirection.BEHIND, 13),
            8 to CreateRegionButton(issueTargetPlayer)
    )

    init {
        val selection = gridSelection.toWorldEditSelection(issueTargetPlayer.location)
        issueTargetPlayer.select(selection)
    }

    /**
     * 新しいグリッド領域を用いて新規のメニュー発行担当オブジェクトを作成する。
     * 発行先のプレーヤーは新規オブジェクトに受け継がれる。
     */
    fun replicateWith(newGridSelection: GridSelection) = GridMenuIssuer(issueTargetPlayer, newGridSelection)
}