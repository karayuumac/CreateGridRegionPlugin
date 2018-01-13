package com.github.karayuu.creategridregionplugin.command

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.github.karayuu.creategridregionplugin.command.commands.GridCommand
import com.github.karayuu.creategridregionplugin.util.sendWarning
import org.bukkit.command.TabExecutor

/**
 * Created by karayuu on 2017/12/24
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
object CommandRegistration {
    fun register() {
        /** グリッド式保護メニューを開くgridコマンド*/
        registCommand(GridCommand(), "grid")
    }

    /**
     * 指定されたexecutorと名前(name)を結び付けてpluginに登録
     */
    private fun registCommand(executor: TabExecutor, name: String) {
        CreateGridRegionPlugin.plugin.getCommand(name).also { pluginCommand ->
            if (pluginCommand == null) {
                sendWarning("${executor::class.java.simpleName} を登録できませんでした。")
                sendWarning("plugin.ymlに$name を登録してください。")
            } else {
                pluginCommand.executor = executor
            }
        }
    }
}
