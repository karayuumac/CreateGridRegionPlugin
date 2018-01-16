package com.github.karayuu.creategridregionplugin.util

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
        AHEAD  -> RIGHT
        RIGHT  -> BEHIND
        BEHIND -> LEFT
        LEFT   -> AHEAD
    }
}