package com.github.karayuu.creategridregionplugin.menu

import com.github.karayuu.creategridregionplugin.player.PlayerData
import org.bukkit.event.player.PlayerInteractEvent

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * Menuの中でも何かをトリガーにして開くメニューを作る際に継承するクラス。
 */
abstract class TriggeredMenu : Menu() {
    /** メニューを開く為のトリガー */
    abstract val triggers: Array<MenuReopsitory.Trigger>

    /** メニューを開ける条件を設定する為の関数 */
    abstract fun canOpen(playerData: PlayerData, event: PlayerInteractEvent) : Boolean
}
