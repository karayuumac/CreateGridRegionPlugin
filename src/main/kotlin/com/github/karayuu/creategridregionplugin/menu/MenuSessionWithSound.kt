package com.github.karayuu.creategridregionplugin.menu

import com.github.karayuu.creategridregionplugin.util.SoundConfiguration
import com.github.karayuu.creategridregionplugin.util.playSound
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

/**
 * インベントリ開閉時に音が鳴るようなメニューのセッションを表すクラスです。
 */
abstract class MenuSessionWithSound: MenuSession() {
    /** プレイヤーがメニューを開いた際の音 */
    open val openingSound = SoundConfiguration(Sound.BLOCK_FENCE_GATE_OPEN, 1F, 0.1F)

    /** プレイヤーがメニューを閉じた際の音 */
    open val closingSound = SoundConfiguration(Sound.BLOCK_FENCE_GATE_OPEN, 1F, 0.1F)

    override fun onMenuOpen(event: InventoryOpenEvent) {
        val player = event.player as? Player ?: return
        player.playSound(openingSound)
    }

    override fun onMenuClose(event: InventoryCloseEvent) {
        val player = event.player as? Player ?: return
        player.playSound(closingSound)
    }
}