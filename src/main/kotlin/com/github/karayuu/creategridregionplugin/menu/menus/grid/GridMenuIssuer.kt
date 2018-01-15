package com.github.karayuu.creategridregionplugin.menu.menus.grid

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.github.karayuu.creategridregionplugin.menu.MenuIssuerWithSound
import com.github.karayuu.creategridregionplugin.menu.component.Button
import com.github.karayuu.creategridregionplugin.menu.component.Icon
import com.github.karayuu.creategridregionplugin.player.PlayerData
import com.github.karayuu.creategridregionplugin.player.property.GridRegion.DirectionType
import com.github.karayuu.creategridregionplugin.util.*
import com.sk89q.worldedit.bukkit.selections.Selection
import com.sk89q.worldguard.bukkit.WorldConfiguration
import com.sk89q.worldguard.bukkit.commands.AsyncCommandHelper
import com.sk89q.worldguard.bukkit.commands.task.RegionAdder
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.managers.RegionManager
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion
import com.sk89q.worldguard.protection.util.DomainInputResolver
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import java.text.NumberFormat

/**
 * Created by karayuu on 2017/12/26
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * グリッドメニューの発行を担当するクラス
 *
 * @author karayuu, kory33
 */
class GridMenuIssuer(private val playerData: PlayerData) : MenuIssuerWithSound() {
    private val config = CreateGridRegionPlugin.configFile

    private val issueTargetPlayer = playerData.player

    override val size = InventoryType.DISPENSER.defaultSize

    override val openingSound = SoundConfiguration(Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)

    override val title = "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}グリッド式保護メニュー"

    override val buttonMap: Map<Int, Button>

    init {
        gridResetFunction()

        //NumberFormat
        val nfNum = NumberFormat.getNumberInstance()

        val gridRegion = playerData.gridRegion
        val icon0 = Icon(
                Material.STAINED_GLASS_PANE,
                name = "${ChatColor.GREEN}拡張単位の変更",
                lore = listOf(
                        "${ChatColor.GREEN}現在のユニット指定量",
                        "${ChatColor.AQUA}${gridRegion.unitPerClick}${ChatColor.GREEN}ユニット" +
                                "(${ChatColor.AQUA}${gridRegion.unitPerClick * 15}${ChatColor.GREEN}ブロック)/1クリック",
                        "${ChatColor.RED}${ChatColor.UNDERLINE}クリックで変更"
                )
        )
        val button0 = Button(icon0) {
            issueTargetPlayer.playSound(issueTargetPlayer.location, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)
            playerData.gridRegion.toggleUnitPerClick()
            issueTargetPlayer.openInventoryOf(replicate())
        }

        val icon1 = Icon(
                Material.STAINED_GLASS_PANE,
                damage = 14,
                name = "${ChatColor.DARK_GREEN}前に${playerData.gridRegion.unitPerClick}ユニット増やす/減らす",
                lore = getGridStatesLore(playerData, DirectionType.AHEAD)
        )
        val button1 = Button(icon1) { event ->
            gridExtendAndReduceSafely(playerData, DirectionType.AHEAD, event)
        }

        val icon3 = Icon(
                Material.STAINED_GLASS_PANE,
                damage = 10,
                name = "${ChatColor.DARK_GREEN}左に${playerData.gridRegion.unitPerClick}ユニット増やす/減らす",
                lore = getGridStatesLore(playerData, DirectionType.LEFT)
        )
        val button3 = Button(icon3) { event ->
            gridExtendAndReduceSafely(playerData, DirectionType.LEFT, event)
        }

        val unitMap = playerData.gridRegion.getUnitMap()
        val icon4 = Icon(
                Material.STAINED_GLASS_PANE,
                damage = 11,
                name = "${ChatColor.DARK_GREEN}現在の設定",
                lore = listOf(
                        "${ChatColor.GRAY}前方向：${ChatColor.AQUA}${unitMap[DirectionType.AHEAD]}${ChatColor.GRAY}ユニット"
                                + "(${ChatColor.AQUA}${nfNum.format((unitMap[DirectionType.AHEAD] ?: 0) * 15)}${ChatColor.GRAY}ブロック)",
                        "${ChatColor.GRAY}後ろ方向：${ChatColor.AQUA}${unitMap[DirectionType.BEHIND]}${ChatColor.GRAY}ユニット"
                                + "(${ChatColor.AQUA}${nfNum.format((unitMap[DirectionType.BEHIND] ?: 0) * 15)}${ChatColor.GRAY}ブロック)",
                        "${ChatColor.GRAY}右方向：${ChatColor.AQUA}${unitMap[DirectionType.RIGHT]}${ChatColor.GRAY}ユニット"
                                + "(${ChatColor.AQUA}${nfNum.format((unitMap[DirectionType.RIGHT] ?: 0) * 15)}${ChatColor.GRAY}ブロック)",
                        "${ChatColor.GRAY}左方向：${ChatColor.AQUA}${unitMap[DirectionType.LEFT]}${ChatColor.GRAY}ユニット"
                                + "(${ChatColor.AQUA}${nfNum.format((unitMap[DirectionType.LEFT] ?: 0) * 15)}${ChatColor.GRAY}ブロック)",
                        "${ChatColor.GRAY}保護ユニット数：${ChatColor.AQUA}${playerData.gridRegion.calcGridUnitAmount()}",
                        "${ChatColor.GRAY}保護ユニット上限値：${ChatColor.RED}${config.unitLimit}"
                )
        )
        val button4 = Button(icon4) {}

        val icon5 = Icon(
                Material.STAINED_GLASS_PANE,
                damage = 5,
                name = "${ChatColor.DARK_GREEN}右に${playerData.gridRegion.unitPerClick}ユニット増やす/減らす",
                lore = getGridStatesLore(playerData, DirectionType.RIGHT)
        )
        val button5 = Button(icon5) { event ->
            gridExtendAndReduceSafely(playerData, DirectionType.RIGHT, event)
        }

        val icon6 = Icon(
                Material.STAINED_GLASS_PANE,
                damage = 4,
                name = "${ChatColor.RED}全設定リセット",
                lore = listOf("${ChatColor.RED}${ChatColor.UNDERLINE}取扱注意！！")
        )
        val button6 = Button(icon6) {
            issueTargetPlayer.playSound(issueTargetPlayer.location, Sound.BLOCK_ANVIL_DESTROY, 0.5F, 1F)
            gridResetFunction(playerData)
            issueTargetPlayer.openInventoryOf(replicate())
        }

        val icon7 = Icon(
                Material.STAINED_GLASS_PANE,
                damage = 13,
                name = "${ChatColor.DARK_GREEN}後ろに${playerData.gridRegion.unitPerClick}ユニット増やす/減らす",
                lore = getGridStatesLore(playerData, DirectionType.BEHIND)
        )
        val button7 = Button(icon7) { event ->
            gridExtendAndReduceSafely(playerData, DirectionType.BEHIND, event)
        }

        val icon8 = if (!playerData.gridRegion.canCreateRegion) {
            Icon(
                    Material.WOOL,
                    damage = 14,
                    name = "${ChatColor.RED}保護作成",
                    lore = listOf(
                            "${ChatColor.RED}${ChatColor.UNDERLINE}以下の原因により保護を作成できません。",
                            "${ChatColor.RED}・保護の範囲がほかの保護と重複している",
                            "${ChatColor.RED}・保護の作成上限に達している"
                    )
            )
        } else {
            Icon(
                    Material.WOOL,
                    damage = 11,
                    name = "${ChatColor.RED}保護作成",
                    lore = listOf("${ChatColor.RED}${ChatColor.UNDERLINE}クリックで作成")
            )
        }

        val button8 = Button(icon8) {
            if (!playerData.gridRegion.canCreateRegion) {
                return@Button
            }

            val player = playerData.player
            player.closeInventory()
            player.chat("//expect vert")
            createRegion(player)
            config.setPlayerRegionNum(player, config.getPlayerRegionNum(player) + 1)
            player.playSound(player.location, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)

        }

        buttonMap = mapOf(
                Pair(0, button0),
                Pair(1, button1),
                Pair(3, button3),
                Pair(4, button4),
                Pair(5, button5),
                Pair(6, button6),
                Pair(7, button7),
                Pair(8, button8)
        )
    }

    private fun replicate() = GridMenuIssuer(playerData)

    // ---------------------------------------------------------------- //
    // TODO これより下にあるメソッドはこのクラスに属するべきではありません
    // ---------------------------------------------------------------- //

    private fun gridResetFunction() {
        val gridData = playerData.gridRegion
        val issueTargetPlayer = playerData.player

        //各種unit数を0にリセット
        for (type in DirectionType.values()) {
            gridData.setUnitAmount(type, 0)
        }

        //始点座標Map(最短)
        val start: Map<String, Double> = getNearlyUnitStart(issueTargetPlayer)
        //終点座標Map(最短)
        val end: Map<String, Double> = getNearlyUnitEnd(issueTargetPlayer)

        //範囲選択
        wgSelect(Location(issueTargetPlayer.world, start["x"] ?: 0.0, 0.0, start["z"] ?: 0.0),
                Location(issueTargetPlayer.world, end["x"] ?: 0.0, 256.0, end["z"] ?: 0.0), issueTargetPlayer)
        canCreateRegion(playerData)
    }

    private fun getGridStatesLore(playerData: PlayerData, directionType: DirectionType) = arrayListOf(
            "${ChatColor.GREEN}左クリックで増加",
            "${ChatColor.RED}右クリックで減少",
            "${ChatColor.GRAY}---------------",
            "${ChatColor.GRAY}方向：${ChatColor.AQUA}${issueTargetPlayer.getDirectionString(directionType)}"
    ).also {
        if (!playerData.gridRegion.canExtendGrid(directionType)) {
            it.add("${ChatColor.RED}${ChatColor.UNDERLINE}これ以上拡大できません")
        }

        if (!playerData.gridRegion.canReduceGrid(directionType)) {
            it.add("\"${ChatColor.RED}${ChatColor.UNDERLINE}これ以上縮小できません\"")
        }
    }

    private fun gridExtendAndReduceSafely(playerData: PlayerData, directionType: DirectionType, event: InventoryClickEvent) {
        val gridData = playerData.gridRegion
        val player = playerData.player

        if (event.isLeftClick) {
            if (!gridData.canExtendGrid(directionType)) {
                return
            }
            player.playSound(player.location, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)
            gridData.addUnitAmount(directionType, gridData.unitPerClick)
        } else if (event.isRightClick) {
            if (!gridData.canReduceGrid(directionType)) {
                return
            }
            player.playSound(player.location, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)
            gridData.addUnitAmount(directionType, gridData.unitPerClick * (-1))
        }

        setWGSelection(playerData)
        canCreateRegion(playerData)
        issueTargetPlayer.openInventoryOf(replicate())
    }

    private fun gridResetFunction(playerData: PlayerData) {
        val gridData = playerData.gridRegion
        val player = playerData.player
        //各種unit数を0にリセット
        for (type in DirectionType.values()) {
            gridData.setUnitAmount(type, 0)
        }
        //始点座標Map(最短)
        val start: Map<String, Double> = getNearlyUnitStart(player)
        //終点座標Map(最短)
        val end: Map<String, Double> = getNearlyUnitEnd(player)
        //範囲選択
        wgSelect(Location(player.world, start["x"] ?: 0.0, 0.0, start["z"] ?: 0.0),
                Location(player.world, end["x"] ?: 0.0, 256.0, end["z"] ?: 0.0), player)
        canCreateRegion(playerData)
    }

    private fun setWGSelection(playerData: PlayerData) {
        val gridData = playerData.gridRegion
        val unitMap = gridData.getUnitMap()
        val player = playerData.player
        val direction = player.getDirection()
        val world = player.world

        val aheadUnitAmount = unitMap[DirectionType.AHEAD] ?: 0
        val leftUnitAmount = unitMap[DirectionType.LEFT] ?: 0
        val rightUnitAmount = unitMap[DirectionType.RIGHT] ?: 0
        val behindUnitAmount = unitMap[DirectionType.BEHIND] ?: 0

        //0ユニット指定の始点/終点のx,z座標
        val startX = getNearlyUnitStart(player)["x"] ?: 0.0
        val startZ = getNearlyUnitStart(player)["z"] ?: 0.0
        val endX = getNearlyUnitEnd(player)["x"] ?: 0.0
        val endZ = getNearlyUnitEnd(player)["z"] ?: 0.0

        val startLoc = Location(world, 0.0, 0.0, 0.0)
        val endLoc = Location(world, 0.0, 256.0, 0.0)

        when(direction) {
            Direction.NORTH -> {
                startLoc.x = startX - 15 * leftUnitAmount
                startLoc.z = startZ - 15 * aheadUnitAmount
                endLoc.x = endX + 15 * rightUnitAmount
                endLoc.z = endZ + 15 * behindUnitAmount
            }
            Direction.EAST -> {
                startLoc.x = startX - 15 * behindUnitAmount
                startLoc.z = startZ - 15 * leftUnitAmount
                endLoc.x = endX + 15 * aheadUnitAmount
                endLoc.z = endZ + 15 * rightUnitAmount
            }
            Direction.SOUTH -> {
                startLoc.x = startX - 15 * rightUnitAmount
                startLoc.z = startZ - 15 * behindUnitAmount
                endLoc.x = endX + 15 * leftUnitAmount
                endLoc.z = endZ + 15 * aheadUnitAmount
            }
            Direction.WEST -> {
                startLoc.x = startX - 15 * aheadUnitAmount
                startLoc.z = startZ - 15 * rightUnitAmount
                endLoc.x = endX + 15 * behindUnitAmount
                endLoc.z = endZ + 15 * leftUnitAmount
            }
        }
        wgSelect(startLoc, endLoc, player)
    }

    private fun wgSelect(loc1: Location, loc2: Location, player: Player) {
        player.chat("//;")
        player.chat("//pos1 " + loc1.blockX + "," + loc1.blockY + "," + loc1.blockZ)
        player.chat("//pos2 " + loc2.blockX + "," + loc2.blockY + "," + loc2.blockZ)
    }

    private fun canCreateRegion(playerData: PlayerData) {
        val gridData = playerData.gridRegion
        val player = playerData.player
        val selection: Selection = WORLD_EDIT.getSelection(player)
        val manager: RegionManager = WORLD_GUARD.getRegionManager(player.world)
        val wcfg: WorldConfiguration = WORLD_GUARD.globalStateManager.get(player.world)

        val region = ProtectedCuboidRegion(player.name + "_" + config.getPlayerRegionNum(player),
                selection.nativeMinimumPoint.toBlockVector(), selection.nativeMaximumPoint.toBlockVector())
        val regions: ApplicableRegionSet = manager.getApplicableRegions(region)

        if (regions.size() != 0) {
            gridData.canCreateRegion = false
            return
        }

        val maxRegionCount: Int = wcfg.getMaxRegionCount(player)
        if (maxRegionCount >= 0 && manager.getRegionCountOfPlayer(WORLD_GUARD.wrapPlayer(player)) >= maxRegionCount) {
            gridData.canCreateRegion = false
            return
        }

        gridData.canCreateRegion = true
    }

    private fun createRegion(player: Player) {
        val selection: Selection = WORLD_EDIT.getSelection(player)
        val playerRegionNum = config.getPlayerRegionNum(player)
        val region = ProtectedCuboidRegion(player.name + "_" + playerRegionNum,
                selection.nativeMinimumPoint.toBlockVector(), selection.nativeMaximumPoint.toBlockVector())
        val manager: RegionManager = WORLD_GUARD.getRegionManager(player.world)

        val task = RegionAdder(WORLD_GUARD, manager, region)
        task.locatorPolicy = DomainInputResolver.UserLocatorPolicy.UUID_ONLY
        task.ownersInput = arrayOf(player.name)
        val future = WORLD_GUARD.executorService.submit(task)

        AsyncCommandHelper.wrap(future, WORLD_GUARD, player).formatUsing(player.name + "_" + playerRegionNum)
                .registerWithSupervisor("保護申請中").thenRespondWith("保護申請完了。保護名: '%s'", "保護作成失敗")
    }

    /**
     * グリッド単位における最短の始点のx,z座標を取得します
     * @param player 該当プレイヤー
     * @return x,z座標のMap
     */
    private fun getNearlyUnitStart(player: Player) : Map<String, Double> {
        val result : MutableMap<String, Double> = mutableMapOf()

        val playerX = player.location.blockX
        val playerZ = player.location.blockZ

        if (playerX % 15 == 0) {
            result["x"] = playerX.toDouble()
        } else {
            result["x"] = Math.ceil((playerX / 15).toDouble()) * 15
        }

        if (playerZ % 15 == 0) {
            result["z"] = playerZ.toDouble()
        } else {
            result["z"] = Math.ceil((playerZ / 15).toDouble()) * 15
        }

        return result.toMap()
    }

    /**
     * ユニット単位における最短の終点(始点から対角になる)のx,z座標を取得します。
     * @param player 該当プレイヤー
     * @return x,z座標のMap
     */
    private fun getNearlyUnitEnd(player: Player) : Map<String, Double> {
        val startCoordinate: Map<String, Double> = getNearlyUnitStart(player)

        val resultMap: MutableMap<String, Double> = mutableMapOf()

        resultMap["x"] = (startCoordinate["x"] ?: 0.0) + 14.0
        resultMap["z"] = (startCoordinate["z"] ?: 0.0) + 14.0

        return resultMap.toMap()
    }
}
