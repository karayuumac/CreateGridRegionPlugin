package com.github.karayuu.creategridregionplugin.menu.menus.grid.buttons

import com.github.karayuu.creategridregionplugin.menu.component.Button
import com.github.karayuu.creategridregionplugin.menu.component.Icon
import com.github.karayuu.creategridregionplugin.menu.menus.grid.GridMenuIssuerR
import com.github.karayuu.creategridregionplugin.player.property.GridSelection
import com.github.karayuu.creategridregionplugin.util.SoundConfiguration
import com.github.karayuu.creategridregionplugin.util.openInventoryOf
import com.github.karayuu.creategridregionplugin.util.playSound
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * [GridMenuIssuerR]のリセットを行うボタンのクラス
 */
class ResetButton: Button {
    private val buttonSound = SoundConfiguration(Sound.BLOCK_ANVIL_DESTROY, 0.5F, 1F)

    override val icon = Icon(
            Material.STAINED_GLASS_PANE,
            damage = 4,
            name = "${ChatColor.RED}全設定リセット",
            lore = listOf("${ChatColor.RED}${ChatColor.UNDERLINE}取扱注意！！")
    )

    override val action: (InventoryClickEvent) -> Unit = { event ->
        val player = event.whoClicked as? Player
        player?.playSound(buttonSound)
        player?.openInventoryOf(GridMenuIssuerR(player, GridSelection()))
    }
}