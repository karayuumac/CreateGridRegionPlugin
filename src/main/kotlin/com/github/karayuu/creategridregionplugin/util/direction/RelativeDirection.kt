package com.github.karayuu.creategridregionplugin.util.direction

/**
 * 相対的な方向を示す列挙型です。
 */
enum class RelativeDirection(val localizedName: String) {
    AHEAD("前"),
    RIGHT("右"),
    BEHIND("後ろ"),
    LEFT("左");

    /**
     * このオブジェクトが指し示す方向を向いた時に右手にある方向を取得します。
     */
    fun onRightHand() = when (this) {
        AHEAD -> RIGHT
        RIGHT -> BEHIND
        BEHIND -> LEFT
        LEFT -> AHEAD
    }

    /**
     * このオブジェクトが指し示す方向から[time]回時計回りに回転した相対方向を求めます。
     */
    fun turnRight(time: Int): RelativeDirection = when {
        time == 0 -> this
        time < 0 -> turnRight((time % 4) + 4)
        else -> onRightHand().turnRight((time % 4) - 1)
    }
}