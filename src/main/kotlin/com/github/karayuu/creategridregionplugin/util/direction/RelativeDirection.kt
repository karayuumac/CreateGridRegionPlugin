package com.github.karayuu.creategridregionplugin.util.direction

/**
 * 相対的な方向を示す列挙型です。
 */
enum class RelativeDirection(val localizedName: String): Turnable<RelativeDirection> {
    AHEAD("前"),
    RIGHT("右"),
    BEHIND("後ろ"),
    LEFT("左");

    override val period = 4

    override fun directionOnRight() = when (this) {
        AHEAD -> RIGHT
        RIGHT -> BEHIND
        BEHIND -> LEFT
        LEFT -> AHEAD
    }
}