package com.github.karayuu.creategridregionplugin

import com.github.karayuu.creategridregionplugin.command.CommandRegistration
import com.github.karayuu.creategridregionplugin.listener.ListenerRegistration
import com.github.karayuu.creategridregionplugin.yml.ymls.Config
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * Created by karayuu on 2017/12/24
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
class CreateGridRegionPlugin : JavaPlugin() {

    companion object {
        /** CreateGridRegionPluginのインスタンス */
        lateinit var instance: CreateGridRegionPlugin
        /** configファイルのインスタンス */
        lateinit var configFile: Config
    }

    /**
     * 起動時の処理
     */
    override fun onEnable() {
        instance = this
        CommandRegistration.register()
        //YmlRegistration.register()
        configFile = Config(this)
        configFile.loadConfig()
        ListenerRegistration.register()
        Bukkit.getServer().logger.info("[CreateGridRegionPlugin] 起動完了")
    }

    /**
     * 終了時の処理
     */
    override fun onDisable() {
        configFile.saveConfig()
        Bukkit.getServer().logger.info("[CreateGridRegionPlugin] 終了完了")
    }
}
