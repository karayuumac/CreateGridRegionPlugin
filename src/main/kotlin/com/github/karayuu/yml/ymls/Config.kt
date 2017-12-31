package com.github.karayuu.yml.ymls

import com.github.karayuu.CreateGridRegionPlugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player

/**
 * Created by karayuu on 2017/12/31
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
class Config(private val plugin: CreateGridRegionPlugin) {
    lateinit var fc: FileConfiguration

    init {
        saveDefaultConfig()
    }

    fun loadConfig() {
        fc = getConfig()
    }

    fun saveConfig() {
        plugin.saveConfig()
    }

    private fun saveDefaultConfig() {
        plugin.saveDefaultConfig()
    }

    private fun getConfig() : FileConfiguration = plugin.config

    /** グリッド式保護最大Unit */
    val unitLimit: Int by lazy { fc.getInt("unitLimit") }

    /**
     * プレイヤーの次に指定すべきRegionの番号を保存します
     * @param player Player
     * @param num Regionの番号
     */
    fun setPlayerRegionNum(player: Player, num: Int) {
        if (!fc.isConfigurationSection("player.$player")) {
            fc.createSection("player.$player")
        }
        fc.set("player.$player", num)
        saveConfig()
    }

    /**
     * プレイヤーの次に指定すべきRegionの番号を取得します
     * @param player Player
     */
    fun getPlayerRegionNum(player: Player) : Int = fc.getInt("player.$player")
}
