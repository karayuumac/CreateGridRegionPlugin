package com.github.karayuu.menu

import com.github.karayuu.player.PlayerData
import org.bukkit.event.player.PlayerInteractEvent

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * メニューを総合管理するオブジェクト
 */
object MenuReopsitory {
    /** 作成したメニューのenum */
    enum class MenuType(val menu: Menu) {

    }

    enum class Trigger {
        RIGHT_CLICK,
        LEFT_CLICK
    }

    fun getMenuToOpen(playerData: PlayerData, event: PlayerInteractEvent, trigger: Trigger) : Menu? =
        MenuType.values().firstOrNull { menuType ->
            menuType.menu is TriggeredMenu && menuType.menu.triggers.contains(trigger) &&
                    menuType.menu.canOpen(playerData, event) }?.menu
}
