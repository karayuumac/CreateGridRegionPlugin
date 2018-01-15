package com.github.karayuu.creategridregionplugin.util

import org.bukkit.entity.Player

/**
 * Created by karayuu on 2017/12/29
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * プレイヤーが向いている方位を取得します
 * @return [CardinalDirection]で表される方位
 */
fun Player.getCardinalDirection() = CardinalDirection.fromLocation(location)

/**
 * プレイヤーの方角を日本語で取得します
 * @param type プレイヤーの前・後ろ・右・左(DirectionType)
 * @deprecated use [cardinalDirectionOn]
 * @return 方角[日本語(英語)]
 */
fun Player.getDirectionString(type: RelativeDirection) : String {
    var rotation: Float = (player.location.yaw + 180) % 360
    val result: MutableMap<RelativeDirection, String> = mutableMapOf()

    if (rotation < 0) rotation += 360

    if (0 <= rotation && rotation < 45 || 315 <= rotation && rotation < 360) {
        result[RelativeDirection.BEHIND] = "南(South)"
        result[RelativeDirection.AHEAD] = "北(North)"
        result[RelativeDirection.LEFT] = "西(West)"
        result[RelativeDirection.RIGHT] = "東(East)"
    } else if (45 <= rotation && rotation < 135) {
        result[RelativeDirection.RIGHT] = "南(South)"
        result[RelativeDirection.LEFT] = "北(North)"
        result[RelativeDirection.BEHIND] = "西(West)"
        result[RelativeDirection.AHEAD] = "東(East)"
    } else if (135 <= rotation && rotation < 225) {
        result[RelativeDirection.AHEAD] = "南(South)"
        result[RelativeDirection.BEHIND] = "北(North)"
        result[RelativeDirection.RIGHT] = "西(West)"
        result[RelativeDirection.LEFT] = "東(East)"
    } else if (225 <= rotation && rotation < 315) {
        result[RelativeDirection.LEFT] = "南(South)"
        result[RelativeDirection.RIGHT] = "北(North)"
        result[RelativeDirection.AHEAD] = "西(West)"
        result[RelativeDirection.BEHIND] = "東(East)"
    }
    return result[type] ?: throw IllegalStateException("[CreateGridRegionPlugin]方角処理で重大なエラー。開発者に報告してください。")
}

private tailrec fun <T> T.repeatedlyApply(time: Int, function: (T) -> T): T = when {
    time < 1 -> this
    else -> repeatedlyApply(time - 1, function)
}

/**
 * プレーヤーの相対方向がどの方位を指しているかの対応関係を取得します。
 * @return [RelativeDirection] がどの [CardinalDirection] に対応しているかの [Map]
 */
fun Player.getRelationToCardinalDirection() : Map<RelativeDirection, CardinalDirection> {
    val aheadCardinalDirection = getCardinalDirection()

    // 右向きに回転する回数
    val turnNumbers = (0 until 4)

    return turnNumbers.map { turnNumber ->
        val relativeDirection = RelativeDirection.AHEAD.repeatedlyApply(turnNumber) { it.onRightHand() }
        val cardinalDirection = aheadCardinalDirection.repeatedlyApply(turnNumber) { it.onRightHand() }

        relativeDirection to cardinalDirection
    }.toMap()
}

fun Player.cardinalDirectionOn(direction: RelativeDirection) = getRelationToCardinalDirection()[direction]!!