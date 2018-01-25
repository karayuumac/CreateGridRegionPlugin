package com.github.karayuu.creategridregionplugin.command.commands

import com.github.karayuu.creategridregionplugin.menu.menus.grid.GridMenuSession
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Created by karayuu on 2017/12/24
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
class GridCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (args == null || sender == null) {
            return false
        }

        if (sender !is Player) {
            return false
        }

        GridMenuSession(sender).send()

        return true
    }
}
