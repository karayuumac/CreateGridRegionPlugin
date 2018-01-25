package com.github.karayuu.creategridregionplugin.util.direction

import com.github.karayuu.creategridregionplugin.util.collection.distanceTo
import com.github.karayuu.creategridregionplugin.util.collection.progress
import org.bukkit.Location

/**
 * [Location]からの相対方向にある方位を取得します。
 * @param direction プレーヤーからの相対方向
 * @return プレーヤーからの指定された方向にある方位を表す[CardinalDirection]
 */
fun Location.cardinalDirectionOn(direction: RelativeDirection) = RelativeDirection.AHEAD.distanceTo(direction)
        .let { turnNumber -> CardinalDirection.fromLocation(this).progress(turnNumber) }