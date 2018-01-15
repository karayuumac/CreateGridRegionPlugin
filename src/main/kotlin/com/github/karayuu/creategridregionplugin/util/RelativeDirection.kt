package com.github.karayuu.creategridregionplugin.util

/**
 * 相対的な方向を示す列挙型です。
 */
enum class RelativeDirection {
    AHEAD,
    RIGHT,
    BEHIND,
    LEFT;

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