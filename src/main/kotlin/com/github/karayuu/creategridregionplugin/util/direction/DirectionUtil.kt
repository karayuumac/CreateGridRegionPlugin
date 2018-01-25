package com.github.karayuu.creategridregionplugin.util.direction

import org.bukkit.Location

/**
 * [Location]からの相対方向にある方位を取得します。
 * @param direction プレーヤーからの相対方向
 * @return プレーヤーからの指定された方向にある方位を表す[CardinalDirection]
 */
fun Location.cardinalDirectionOn(direction: RelativeDirection) = CardinalDirection.fromLocation(this).let { ahead ->
    val rotation = RelativeDirection.AHEAD.rotationTo(direction)
    ahead.rotate(rotation)
}