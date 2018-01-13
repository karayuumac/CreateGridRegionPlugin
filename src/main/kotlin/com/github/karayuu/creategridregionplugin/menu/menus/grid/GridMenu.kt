package com.github.karayuu.creategridregionplugin.menu.menus.grid

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.github.karayuu.creategridregionplugin.menu.Menu
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
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import java.text.NumberFormat

/**
 * Created by karayuu on 2017/12/26
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
object GridMenu : Menu() {
    private val config = CreateGridRegionPlugin.configFile
    
    override val type: InventoryType
        get() = InventoryType.DISPENSER
    override val openSound = SoundConfiguration(Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)
    override fun getTitle(playerData: PlayerData): String =
            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" + "グリッド式保護メニュー"

    override fun init(playerData: PlayerData) {
        //グリッド設定リセット
        gridResetFunction(playerData)
    }

    init {
        //NumberFormat
        val nfNum = NumberFormat.getNumberInstance()

        //以下buttonの設定
        registButton(0, object : Button {
            override fun getItemStack(playerData: PlayerData): ItemStack {
                return ItemStack(Material.STAINED_GLASS_PANE, 1, 0).apply {
                    setTitle("拡張単位の変更", prefix = "${ChatColor.GREEN}")
                    setLore("${ChatColor.GREEN}現在のユニット指定量",
                            "${ChatColor.AQUA}${playerData.gridRegion.unitPerClick}${ChatColor.GREEN}ユニット(${ChatColor.AQUA}" +
                                    "${playerData.gridRegion.unitPerClick * 15}${ChatColor.GREEN}ブロック)/1クリック",
                            "${ChatColor.RED}${ChatColor.UNDERLINE}クリックで変更", prefix = "")
                }
            }

            override fun onClick(playerData: PlayerData, event: InventoryClickEvent) {
                val player = playerData.player
                player.playSound(player.location, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)
                playerData.gridRegion.toggleUnitPerClick()
                reopen(playerData)
            }
        })

        registButton(1, object : Button {
            override fun getItemStack(playerData: PlayerData): ItemStack {
                return ItemStack(Material.STAINED_GLASS_PANE, 1, 14).apply {
                    setTitle("前に${playerData.gridRegion.unitPerClick}ユニット増やす/減らす", prefix = "${ChatColor.DARK_GREEN}")
                    addGridStatesLore(playerData, DirectionType.AHEAD)
                }
            }

            override fun onClick(playerData: PlayerData, event: InventoryClickEvent) {
                gridExtendAndReduceSafely(playerData, DirectionType.AHEAD, event)
            }
        })

        registButton(3, object : Button {
            override fun getItemStack(playerData: PlayerData): ItemStack {
                return ItemStack(Material.STAINED_GLASS_PANE, 1, 10).apply {
                    setTitle("左に${playerData.gridRegion.unitPerClick}ユニット増やす/減らす", prefix = "${ChatColor.DARK_GREEN}")
                    addGridStatesLore(playerData, DirectionType.LEFT)
                }
            }

            override fun onClick(playerData: PlayerData, event: InventoryClickEvent) {
                gridExtendAndReduceSafely(playerData, DirectionType.LEFT, event)
            }
        })

        registButton(4, object : Button {
            override fun getItemStack(playerData: PlayerData): ItemStack {
                return ItemStack(Material.STAINED_GLASS_PANE, 1, 11).apply {
                    val unitMap = playerData.gridRegion.getUnitMap()
                    setTitle("現在の設定", prefix = "${ChatColor.DARK_GREEN}")
                    setLore("${ChatColor.GRAY}前方向：${ChatColor.AQUA}${unitMap[DirectionType.AHEAD]}${ChatColor.GRAY}ユニット"
                            + "(${ChatColor.AQUA}${nfNum.format((unitMap[DirectionType.AHEAD] ?: 0) * 15)}${ChatColor.GRAY}ブロック)",
                            "${ChatColor.GRAY}後ろ方向：${ChatColor.AQUA}${unitMap[DirectionType.BEHIND]}${ChatColor.GRAY}ユニット"
                                    + "(${ChatColor.AQUA}${nfNum.format((unitMap[DirectionType.BEHIND] ?: 0) * 15)}${ChatColor.GRAY}ブロック)",
                            "${ChatColor.GRAY}右方向：${ChatColor.AQUA}${unitMap[DirectionType.RIGHT]}${ChatColor.GRAY}ユニット"
                                    + "(${ChatColor.AQUA}${nfNum.format((unitMap[DirectionType.RIGHT] ?: 0) * 15)}${ChatColor.GRAY}ブロック)",
                            "${ChatColor.GRAY}左方向：${ChatColor.AQUA}${unitMap[DirectionType.LEFT]}${ChatColor.GRAY}ユニット"
                                    + "(${ChatColor.AQUA}${nfNum.format((unitMap[DirectionType.LEFT] ?: 0) * 15)}${ChatColor.GRAY}ブロック)",
                            "${ChatColor.GRAY}保護ユニット数：${ChatColor.AQUA}${playerData.gridRegion.calcGridUnitAmount()}",
                            "${ChatColor.GRAY}保護ユニット上限値：${ChatColor.RED}${config.unitLimit}", prefix = "")
                }
            }

            override fun onClick(playerData: PlayerData, event: InventoryClickEvent) {

            }
        })

        registButton(5, object : Button {
            override fun getItemStack(playerData: PlayerData): ItemStack {
                return ItemStack(Material.STAINED_GLASS_PANE, 1, 5).apply {
                    setTitle("右に${playerData.gridRegion.unitPerClick}ユニット増やす/減らす", prefix = "${ChatColor.DARK_GREEN}")
                    addGridStatesLore(playerData, DirectionType.RIGHT)
                }
            }

            override fun onClick(playerData: PlayerData, event: InventoryClickEvent) {
                gridExtendAndReduceSafely(playerData, DirectionType.RIGHT, event)
            }
        })

        registButton(6, object : Button {
            override fun getItemStack(playerData: PlayerData): ItemStack {
                return ItemStack(Material.STAINED_GLASS_PANE, 1, 4).apply {
                    setTitle("全設定リセット", prefix = "${ChatColor.RED}")
                    addLore("取扱注意！！", prefix = "${ChatColor.RED}${ChatColor.UNDERLINE}")
                }
            }

            override fun onClick(playerData: PlayerData, event: InventoryClickEvent) {
                val player = playerData.player
                player.playSound(player.location, Sound.BLOCK_ANVIL_DESTROY, 0.5F, 1F)
                gridResetFunction(playerData)
                reopen(playerData)
            }
        })

        registButton(7, object : Button {
            override fun getItemStack(playerData: PlayerData): ItemStack {
                return ItemStack(Material.STAINED_GLASS_PANE, 1, 13).apply {
                    setTitle("後ろに${playerData.gridRegion.unitPerClick}ユニット増やす/減らす", prefix = "${ChatColor.DARK_GREEN}")
                    addGridStatesLore(playerData, DirectionType.BEHIND)
                }
            }

            override fun onClick(playerData: PlayerData, event: InventoryClickEvent) {
                gridExtendAndReduceSafely(playerData, DirectionType.BEHIND, event)
            }
        })

        registButton(8, object : Button {
            override fun getItemStack(playerData: PlayerData): ItemStack = if (!playerData.gridRegion.canCreateRegion) {
                ItemStack(Material.WOOL, 1, 14).apply {
                    setTitle("保護作成", prefix = "${ChatColor.RED}")
                    setLore("${ChatColor.RED}${ChatColor.UNDERLINE}以下の原因により保護を作成できません。",
                            "${ChatColor.RED}・保護の範囲がほかの保護と重複している",
                            "${ChatColor.RED}・保護の作成上限に達している", prefix = "")
                }
            } else {
                ItemStack(Material.WOOL, 1, 11).apply {
                    setTitle("保護作成", prefix = "${ChatColor.RED}")
                    setLore("クリックで作成", prefix = "${ChatColor.RED}${ChatColor.UNDERLINE}")
                }
            }

            override fun onClick(playerData: PlayerData, event: InventoryClickEvent) {
                if (!playerData.gridRegion.canCreateRegion) {
                    return
                }

                val player = playerData.player
                player.closeInventory()
                player.chat("//expect vert")
                createRegion(player)
                config.setPlayerRegionNum(player, config.getPlayerRegionNum(player) + 1)
                player.playSound(player.location, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)
            }
        })
    }

    private fun ItemStack.addGridStatesLore(playerData: PlayerData, directionType: DirectionType) {
        addLore("${ChatColor.GREEN}左クリックで増加",
                "${ChatColor.RED}右クリックで減少",
                "${ChatColor.GRAY}---------------",
                "${ChatColor.GRAY}方向：${ChatColor.AQUA}${playerData.player.getDirectionString(directionType)}", prefix = "")
        if (!playerData.gridRegion.canExtendGrid(directionType)) cantExtendLore()
        if (!playerData.gridRegion.canReduceGrid(directionType)) cantReduceLore()
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
            setWGSelection(playerData)
            canCreateRegion(playerData)
            reopen(playerData)
        } else if (event.isRightClick) {
            if (!gridData.canReduceGrid(directionType)) {
                return
            }
            player.playSound(player.location, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1F, 1F)
            gridData.addUnitAmount(directionType, gridData.unitPerClick * (-1))
            setWGSelection(playerData)
            canCreateRegion(playerData)
            reopen(playerData)
        }
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
