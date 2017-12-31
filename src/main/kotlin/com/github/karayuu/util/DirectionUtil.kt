package com.github.karayuu.util

import com.github.karayuu.player.property.GridRegion
import org.bukkit.entity.Player

/**
 * Created by karayuu on 2017/12/29
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * プレイヤーのDirectionを取得します
 * @param player 該当プレイヤー
 * @return PlayerのDirection
 */
fun getPlayerDirection(player: Player) : Direction {
    var rotation: Float = (player.location.yaw + 180) % 360

    if (rotation < 0) rotation += 360

    if (0 <= rotation && rotation < 45) return Direction.NORTH
    if (45 <= rotation && rotation < 135) return Direction.EAST
    if (135 <= rotation && rotation < 225) return Direction.SOUTH
    if (225 <= rotation && rotation < 315) return Direction.WEST
    if (315 <= rotation && rotation < 360) return Direction.NORTH
    throw IllegalStateException("[CreateGridRegionPlugin]方角処理で重大なエラー。開発者に報告してください。")
}

/**
 * プレイヤーの方向と方角のMapを取得します
 * @param player 該当プレイヤー
 * @return DirectionTypeとString(方角)のMap
 */
fun getPlayerDirectionString(player: Player) : Map<GridRegion.DirectionType, String> {
    var rotation: Float = (player.location.yaw + 180) % 360
    val result: MutableMap<GridRegion.DirectionType, String> = mutableMapOf()

    if (rotation < 0) rotation += 360

    if (0 <= rotation && rotation < 45) {
        result[GridRegion.DirectionType.BEHIND] = "南(South)"
        result[GridRegion.DirectionType.AHEAD] = "北(North)"
        result[GridRegion.DirectionType.LEFT] = "西(West)"
        result[GridRegion.DirectionType.RIGHT] = "東(East)"
    } else if (45 <= rotation && rotation < 135) {
        result[GridRegion.DirectionType.RIGHT] = "南(South)"
        result[GridRegion.DirectionType.LEFT] = "北(North)"
        result[GridRegion.DirectionType.BEHIND] = "西(West)"
        result[GridRegion.DirectionType.AHEAD] = "東(East)"
    } else if (135 <= rotation && rotation < 225) {
        result[GridRegion.DirectionType.AHEAD] = "南(South)"
        result[GridRegion.DirectionType.BEHIND] = "北(North)"
        result[GridRegion.DirectionType.RIGHT] = "西(West)"
        result[GridRegion.DirectionType.LEFT] = "東(East)"
    } else if (225 <= rotation && rotation < 315) {
        result[GridRegion.DirectionType.LEFT] = "南(South)"
        result[GridRegion.DirectionType.RIGHT] = "北(North)"
        result[GridRegion.DirectionType.AHEAD] = "西(West)"
        result[GridRegion.DirectionType.BEHIND] = "東(East)"
    } else if (315 <= rotation && rotation < 360) {
        result[GridRegion.DirectionType.BEHIND] = "南(South)"
        result[GridRegion.DirectionType.AHEAD] = "北(North)"
        result[GridRegion.DirectionType.LEFT] = "西(West)"
        result[GridRegion.DirectionType.RIGHT] = "東(East)"
    }
    return result
}

enum class Direction {
    NORTH, SOUTH, EAST, WEST
}
