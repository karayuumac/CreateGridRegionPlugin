package com.github.karayuu.creategridregionplugin.util

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.sk89q.worldedit.bukkit.selections.Selection
import com.sk89q.worldguard.bukkit.WorldConfiguration
import com.sk89q.worldguard.bukkit.commands.AsyncCommandHelper
import com.sk89q.worldguard.bukkit.commands.task.RegionAdder
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.managers.RegionManager
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion
import com.sk89q.worldguard.protection.util.DomainInputResolver
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.InventoryView
import java.util.*

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * uuidからプレイヤーを取得します。
 * @param uuid 検索したいUUID
 * @return 該当player
 */
fun getPlayer(uuid: UUID) : Player? = CreateGridRegionPlugin.instance.server.getPlayer(uuid)

/**
 * uuidからプレイヤーを取得する拡張関数
 * @return 該当プレイヤー
 */
fun UUID.toPlayer() : Player? = getPlayer(this)

fun Player.playSound(soundConfig: SoundConfiguration) =
        playSound(this.location, soundConfig.sound, soundConfig.volume, soundConfig.pitch)

/**
 * プレイヤーにメッセージを送信します
 * @param player 送信するプレイヤー
 * @param msg 送信するメッセージ
 */
fun sendMessage(player: Player?, msg: String) = player?.sendMessage(msg)

/**
 * uuidからプレイヤーの名前を取得します
 * @param uuid 検索するプレイヤー
 * @return プレイヤー名 (if @null then return "No player")
 */
fun getName(uuid: UUID): String = getPlayer(uuid)?.name ?: "No Player"

/**
 * [HumanEntity]が与えられた[inventoryHolder]のインベントリを開きます。
 * @param [inventoryHolder] 開くインベントリの所有者
 * @return 開かれたインベントリに対応する[InventoryView]
 */
fun HumanEntity.openInventoryOf(inventoryHolder: InventoryHolder) = inventoryHolder.inventory?.let { openInventory(it) }

/**
 * プレーヤーが選択中の領域で保護領域を作成できるかを返します。
 */
fun Player.canCreateRegionWithSelection(): Boolean {
    val config = CreateGridRegionPlugin.configFile
    val selection: Selection = WORLD_EDIT.getSelection(this) ?: return false

    val manager: RegionManager = WORLD_GUARD.getRegionManager(world)
    val wcfg: WorldConfiguration = WORLD_GUARD.globalStateManager.get(world)

    val region = ProtectedCuboidRegion(name + "_" + config.getPlayerRegionNum(this),
            selection.nativeMinimumPoint.toBlockVector(), selection.nativeMaximumPoint.toBlockVector())
    val regions: ApplicableRegionSet = manager.getApplicableRegions(region)

    if (regions.size() != 0) {
        return false
    }

    val maxRegionCount: Int = wcfg.getMaxRegionCount(player)
    if (maxRegionCount >= 0 && manager.getRegionCountOfPlayer(WORLD_GUARD.wrapPlayer(player)) >= maxRegionCount) {
        return false
    }

    return true
}

/**
 * プレーヤーが選択中の領域で保護領域を作成します。
 */
fun Player.createRegion() {
    val selection: Selection = WORLD_EDIT.getSelection(this)
    val playerRegionNum = CreateGridRegionPlugin.configFile.getPlayerRegionNum(this)
    val region = ProtectedCuboidRegion(this.name + "_" + playerRegionNum,
            selection.nativeMinimumPoint.toBlockVector(), selection.nativeMaximumPoint.toBlockVector())
    val manager: RegionManager = WORLD_GUARD.getRegionManager(this.world)

    val task = RegionAdder(WORLD_GUARD, manager, region)
    task.locatorPolicy = DomainInputResolver.UserLocatorPolicy.UUID_ONLY
    task.ownersInput = arrayOf(this.name)
    val future = WORLD_GUARD.executorService.submit(task)

    AsyncCommandHelper.wrap(future, WORLD_GUARD, this).formatUsing(this.name + "_" + playerRegionNum)
            .registerWithSupervisor("保護申請中").thenRespondWith("保護申請完了。保護名: '%s'", "保護作成失敗")
}

/**
 * プレーヤーが領域を選択します。
 */
fun Player.select(selection: Selection) = WORLD_EDIT.setSelection(this, selection)