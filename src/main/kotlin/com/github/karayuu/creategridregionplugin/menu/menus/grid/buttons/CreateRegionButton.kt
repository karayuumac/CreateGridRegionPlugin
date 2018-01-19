package com.github.karayuu.creategridregionplugin.menu.menus.grid.buttons

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.github.karayuu.creategridregionplugin.menu.component.Button
import com.github.karayuu.creategridregionplugin.menu.component.Icon
import com.github.karayuu.creategridregionplugin.util.SoundConfiguration
import com.github.karayuu.creategridregionplugin.util.canCreateRegionWithSelection
import com.github.karayuu.creategridregionplugin.util.createRegion
import com.github.karayuu.creategridregionplugin.util.playSound
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class CreateRegionButton(regionOwner: Player): Button {
    private val soundOnRegionCreate = SoundConfiguration(Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)

    override val icon = if (regionOwner.canCreateRegionWithSelection()) {
        Icon(
                Material.WOOL,
                damage = 14,
                name = "${ChatColor.RED}保護作成",
                lore = listOf(
                        "${ChatColor.RED}${ChatColor.UNDERLINE}以下の原因により保護を作成できません。",
                        "${ChatColor.RED}・保護の範囲がほかの保護と重複している",
                        "${ChatColor.RED}・保護の作成上限に達している"
                )
        )
    } else {
        Icon(
                Material.WOOL,
                damage = 11,
                name = "${ChatColor.RED}保護作成",
                lore = listOf("${ChatColor.RED}${ChatColor.UNDERLINE}クリックで作成")
        )
    }

    override val action = fun(_: InventoryClickEvent) {
        if (!regionOwner.canCreateRegionWithSelection()) {
            return
        }

        val config = CreateGridRegionPlugin.configFile

        regionOwner.closeInventory()
        regionOwner.createRegion()

        config.setPlayerRegionNum(regionOwner, config.getPlayerRegionNum(regionOwner) + 1)
        regionOwner.playSound(soundOnRegionCreate)
    }
}