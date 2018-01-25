package com.github.karayuu.creategridregionplugin.util.direction

import com.github.karayuu.creategridregionplugin.util.collection.CyclicallyOrderedElement

/**
 * 相対的な方向を示す列挙型です。
 */
enum class RelativeDirection(val localizedName: String): CyclicallyOrderedElement<RelativeDirection> {
    AHEAD("前"),
    RIGHT("右"),
    BEHIND("後ろ"),
    LEFT("左");

    override val setSize = 4

    override fun successor() = when (this) {
        AHEAD -> RIGHT
        RIGHT -> BEHIND
        BEHIND -> LEFT
        LEFT -> AHEAD
    }
}