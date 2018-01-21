package com.github.karayuu.creategridregionplugin.listener

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.github.karayuu.creategridregionplugin.listener.listeners.MenuListener
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
     *
     * 例) `registerEvent(**Listener())`
     */
    fun register() {
        registerEvent(MenuListener())
    }

    private fun registerEvent(listener: Listener) = CreateGridRegionPlugin.instance.server.pluginManager.
            registerEvents(listener, CreateGridRegionPlugin.instance)
}
