package com.github.karayuu.listener

import com.github.karayuu.CreateGridRegionPlugin
import com.github.karayuu.listener.listeners.MenuListener
import com.github.karayuu.listener.listeners.PlayerJoinListener
import com.github.karayuu.listener.listeners.PlayerQuitListener
import org.bukkit.event.Listener

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * Listener総合管理オブジェクト
 */
object ListenerRegistration {
    /**
     * Listener登録用関数。Listenerは必ずここに追加してください。
     * ex) registEvent(**Listener())
     */
    fun regist() {
        registEvent(PlayerJoinListener())
        registEvent(PlayerQuitListener())
        registEvent(MenuListener())
    }

    private fun registEvent(listener: Listener) = CreateGridRegionPlugin.plugin.server.pluginManager.
            registerEvents(listener, CreateGridRegionPlugin.plugin)
}
