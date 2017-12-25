package com.github.karayuu.command.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * Created by karayuu on 2017/12/24
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
class GridCommand : TabExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (args == null) {
            return false
        }

        if (sender !is Player) {
            return false
        }

        sender.sendMessage("これで実行されてますよう！")
        return true
    }

    override fun onTabComplete(sender: CommandSender?, command: Command?,
                               label: String?, args: Array<out String>?): MutableList<String> = mutableListOf()
}
