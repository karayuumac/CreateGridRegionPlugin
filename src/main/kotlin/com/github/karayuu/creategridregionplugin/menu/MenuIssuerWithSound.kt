package com.github.karayuu.creategridregionplugin.menu

import com.github.karayuu.creategridregionplugin.util.SoundConfiguration
import com.github.karayuu.creategridregionplugin.util.playSound
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

/**
 * 開閉時に音が鳴るメニューの発行者となるクラス
 * TODO 命名が酷い
 *
 * @author kory33
 */
abstract class MenuIssuerWithSound : MenuIssuer() {
    /** プレイヤーがメニューを開いた際の音 */
    open val openingSound = SoundConfiguration(Sound.BLOCK_FENCE_GATE_OPEN, 1F, 0.1F)

    /** プレイヤーがメニューを閉じた際の音 */
    open val closingSound = SoundConfiguration(Sound.BLOCK_FENCE_GATE_OPEN, 1F, 0.1F)

    override fun onOpen(event: InventoryOpenEvent) {
        val player = event.player as? Player ?: return
        player.playSound(openingSound)
    }

    override fun onClose(event: InventoryCloseEvent) {
        val player = event.player as? Player ?: return
        player.playSound(closingSound)
    }
}