package com.github.karayuu.util

import com.github.karayuu.CreateGridRegionPlugin
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import org.bukkit.Bukkit

/**
 * Created by karayuu on 2017/12/26
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * プラグインを停止させます．
 */
fun disable(vararg msg: String) {
    msg.toList().forEach { sendWarning(it) }
    Bukkit.getServer().pluginManager.disablePlugin(CreateGridRegionPlugin.plugin)
}

// ワールドガードAPIを返す
val WORLD_GUARD: WorldGuardPlugin by lazy {
    val pl = Bukkit.getServer().pluginManager
            .getPlugin("WorldGuard")
    if (pl == null) {
        disable("WorldGuard not working")
        throw Exception("")
    } else pl as WorldGuardPlugin

}

// ワールドエディットAPIを返す
val WORLD_EDIT: WorldEditPlugin by lazy {
    val pl = Bukkit.getServer().pluginManager
            .getPlugin("WorldEdit")
    if (pl == null) {
        disable("WorldEdit not working")
        throw Exception("")
    } else pl as WorldEditPlugin
}
