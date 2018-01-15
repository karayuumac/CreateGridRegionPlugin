package com.github.karayuu.creategridregionplugin.util

import org.bukkit.Location

/**
 * 東西南北の方角とそれに付随するデータをまとめた列挙型です。
 * @param localizedName ロケール化された方角名
 */
enum class CardinalDirection(val localizedName: String) {
    NORTH("北(North)"), SOUTH("南(South)"), EAST("東(East)"), WEST("西(West)");

    /**
     * このオブジェクトが指し示す方位を向いた時に右手にある方位を取得します。
     */
    fun onRightHand() = when(this) {
        NORTH -> EAST
        EAST  -> SOUTH
        SOUTH -> WEST
        WEST  -> NORTH
    }

    companion object {
        fun fromLocation(location: Location): CardinalDirection {
            var rotation: Float = (location.yaw + 180) % 360

            if (rotation < 0) rotation += 360

            return when {
                45 <= rotation && rotation < 135  -> EAST
                135 <= rotation && rotation < 225 -> SOUTH
                225 <= rotation && rotation < 315 -> WEST
                else -> NORTH
            }
        }
    }
}