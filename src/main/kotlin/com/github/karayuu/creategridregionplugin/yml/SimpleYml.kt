/*
package com.github.karayuu.yml

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * Created by karayuu on 2017/12/26
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * ymlファイルを作る際の抽象クラス。ymlを作る際はこれを継承して下さい。
 */
abstract class SimpleYml(name: String = "") {
    /** ファイル名 */
    val filename = (if (name.isNotEmpty()) name else this.javaClass.simpleName.removeSuffix("Yml")).toLowerCase() + ".yml"
    /** ファイル */
    open var file = File(CreateGridRegionPlugin.plugin.dataFolder, filename)

    /** 設定ファイル */
    open lateinit var fc: FileConfiguration

    fun register() {
        makeFile()
        load()
        onAvailable()
    }

    /**
     * fileの生成時に使用する。FileConfiguartionは初期化処理がまだなので使用禁止。
     */
    open protected fun makeFile() {
        if (!file.exists()) {
            CreateGridRegionPlugin.plugin.saveResource(filename, false)
        }
    }

    /**
     * FileConfigurationのロード
     */
    open protected fun load() {
        fc = YamlConfiguration.loadConfiguration(file)
    }

    /**
     * FileConfigurationの初期化後1度だけ呼び出されるメソッド
     */
    open protected fun onAvailable() {

    }
}
*/
