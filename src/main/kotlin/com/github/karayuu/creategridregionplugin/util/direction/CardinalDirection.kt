package com.github.karayuu.creategridregionplugin.util.direction

import org.bukkit.Location

/**
 * 東西南北の方角とそれに付随するデータをまとめた列挙型です。
 * @param localizedName ロケール化された方角名
 */
enum class CardinalDirection(val localizedName: String): FourWayDirection<CardinalDirection> {
    NORTH("北(North)"), SOUTH("南(South)"), EAST("東(East)"), WEST("西(West)");

    override fun successor() = when(this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }

    companion object {
        fun fromLocation(location: Location): CardinalDirection {
            val rotation = ((location.yaw + 180) % 360).let {
                when {
                    it < 0 -> it + 360
                    else -> it
                }
            }

            return when {
                45 <= rotation && rotation < 135  -> EAST
                135 <= rotation && rotation < 225 -> SOUTH
                225 <= rotation && rotation < 315 -> WEST
                else -> NORTH
            }
        }
    }
}