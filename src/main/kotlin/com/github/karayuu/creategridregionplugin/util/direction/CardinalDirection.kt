package com.github.karayuu.creategridregionplugin.util.direction

import org.bukkit.Location

/**
 * 東西南北の方角とそれに付随するデータをまとめた列挙型です。
 * @param localizedName ロケール化された方角名
 */
enum class CardinalDirection(val localizedName: String): Turnable<CardinalDirection> {
    NORTH("北(North)"), SOUTH("南(South)"), EAST("東(East)"), WEST("西(West)");

    override val period = 4

    override fun directionOnRight() = when(this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }

    companion object {
        fun fromLocation(location: Location): CardinalDirection {
            // yaw \in [-180, 180) なので rotation >= 0
            val rotation = (location.yaw + 180) % 360

            return when {
                45 <= rotation && rotation < 135  -> EAST
                135 <= rotation && rotation < 225 -> SOUTH
                225 <= rotation && rotation < 315 -> WEST
                else -> NORTH
            }
        }
    }
}