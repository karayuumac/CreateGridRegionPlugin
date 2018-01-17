package com.github.karayuu.creategridregionplugin.util.direction

import org.bukkit.Location

/**
 * Created by karayuu on 2017/12/29
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

private tailrec fun <T> T.repeatedlyApply(time: Int, function: (T) -> T): T = when {
    time < 1 -> this
    else -> repeatedlyApply(time - 1, function)
}

/**
 * [Location]からの相対方向がどの方位を指しているかの対応関係を取得します。
 * @return [RelativeDirection] がどの [CardinalDirection] に対応しているかの [Map]
 */
fun Location.getRelationToCardinalDirection() : Map<RelativeDirection, CardinalDirection> {
    val aheadCardinalDirection = CardinalDirection.fromLocation(this)

    // 右向きに回転する回数
    val turnNumbers = (0 until 4)

    return turnNumbers.map { turnNumber ->
        val relativeDirection = RelativeDirection.AHEAD.repeatedlyApply(turnNumber) { it.onRightHand() }
        val cardinalDirection = aheadCardinalDirection.repeatedlyApply(turnNumber) { it.onRightHand() }

        relativeDirection to cardinalDirection
    }.toMap()
}

/**
 * [Location]からの相対方向にある方位を取得します。
 * @param direction プレーヤーからの相対方向
 * @return プレーヤーからの指定された方向にある方位を表す[CardinalDirection]
 */
fun Location.cardinalDirectionOn(direction: RelativeDirection) = getRelationToCardinalDirection()[direction]!!