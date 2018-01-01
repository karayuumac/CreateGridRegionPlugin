package com.github.karayuu

import com.github.karayuu.command.CommandRegistration
import com.github.karayuu.listener.ListenerRegistration
import com.github.karayuu.player.PlayerRepository
import com.github.karayuu.yml.ymls.Config
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
        lateinit var plugin: CreateGridRegionPlugin
        /** configファイルのインスタンス */
        lateinit var configFile: Config
    }

    /**
     * 起動時の処理
     */
    override fun onEnable() {
        plugin = this
        CommandRegistration.register()
        //YmlRegistration.register()
        configFile = Config(this)
        configFile.loadConfig()
        ListenerRegistration.regist()
        PlayerRepository.onEnable()
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
