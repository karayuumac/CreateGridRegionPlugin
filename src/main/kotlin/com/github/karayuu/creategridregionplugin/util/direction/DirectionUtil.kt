package com.github.karayuu.creategridregionplugin.util.direction

import org.bukkit.Location

/**
 * [Location]からの相対方向にある方位を取得します。
 * @param direction プレーヤーからの相対方向
 * @return プレーヤーからの指定された方向にある方位を表す[CardinalDirection]
 */
fun Location.cardinalDirectionOn(direction: RelativeDirection) = RelativeDirection.AHEAD.turnNumberTo(direction)
        .let { turnNumber -> CardinalDirection.fromLocation(this).turnRight(turnNumber) }

/**
 * 別の方向へ右に何度回転すればよいかを計算します。
 */
fun <T: Turnable<T>> T.turnNumberTo(direction: T) = (0 until period).first { this.turnRight(it) == direction }

/**
 * オブジェクトが指し示す方向から[turnNumber]回時計回りに回転した方向を求めます。
 */
fun <T: Turnable<T>> T.turnRight(turnNumber: Int): T = (turnNumber % period).let { phase ->
    when {
        phase != turnNumber -> turnRight(phase)
        phase == 0 -> this
        phase < 0 -> turnRight(phase + period)
        else -> directionOnRight().turnRight(turnNumber - 1)
    }
}