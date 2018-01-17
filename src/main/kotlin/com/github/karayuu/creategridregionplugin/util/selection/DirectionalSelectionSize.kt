package com.github.karayuu.creategridregionplugin.util.selection

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.github.karayuu.creategridregionplugin.util.direction.RelativeDirection

/**
 * 選択領域のサイズを方向ごとの単位領域数で表すデータクラスです。
 *
 * このクラス内の任意のメソッドはレシーバの状態を変更しません。
 *
 * @author kory33
 */
data class DirectionalSelectionSize(private val ahead : Int = 0,
                                    private val behind: Int = 0,
                                    private val right : Int = 0,
                                    private val left  : Int = 0) {
    constructor(map: Map<RelativeDirection, Int>) : this(
            map[RelativeDirection.AHEAD] ?: 0,
            map[RelativeDirection.BEHIND] ?: 0,
            map[RelativeDirection.RIGHT] ?: 0,
            map[RelativeDirection.LEFT] ?: 0
    )

    private val unitMap = mapOf(
            RelativeDirection.AHEAD  to ahead,
            RelativeDirection.BEHIND to behind,
            RelativeDirection.RIGHT  to right,
            RelativeDirection.LEFT   to left
    )

    /**
     * 指定方向のユニット数を指定ユニットにセットした新しい領域サイズを取得します。
     *
     * @param direction ユニット数をセットする方向
     * @param newUnit セットするユニット数
     * @return 操作を行った後の領域サイズ
     */
    fun setUnitAlong(direction: RelativeDirection, newUnit: Int): DirectionalSelectionSize {
        /** 指定 [RelativeDirection] に対応するキーを [newUnit] で置換した新しい[Map] */
        val newUnits = unitMap.mapValues { (relativeDirection, oldUnit) ->
            when (relativeDirection) {
                direction -> newUnit
                else -> oldUnit
            }
        }

        return DirectionalSelectionSize(newUnits)
    }

    /**
     * 指定方向のユニット数を取得します。
     *
     * @return [direction]方向のユニット数
     */
    operator fun get(direction: RelativeDirection) = unitMap[direction]!!

    /**
     * 指定方向に指定ユニット数拡張した新しい領域サイズを取得します。
     *
     * @param direction 拡張する方向
     * @param unit 拡張するユニット数
     * @return 操作を行った後の領域サイズ
     */
    fun addUnitAlong(direction: RelativeDirection, unit: Int) = setUnitAlong(direction, this[direction] + unit)

    /**
     * 指定方向に指定ユニット数縮小した新しい領域サイズを取得します。
     *
     * @param direction 縮小する方向
     * @param unit 縮小するユニット数
     * @return 操作を行った後の領域サイズ
     */
    fun reduceUnitAlong(direction: RelativeDirection, unit: Int) = addUnitAlong(direction, -unit)

    /**
     * 領域の面積(単位はユニット二乗)を計算します。
     *
     * @return 領域の面積
     */
    fun calculateTotalUnits() = (ahead + behind + 1) * (left + right + 1)

    /**
     * 領域サイズが正常であるかを判定します。
     *
     * @return 領域が正常であれば`true`、それ以外は`false`
     */
    fun isValid(): Boolean {
        // とある方向へのユニット数が負であるならfalseを返す
        if (unitMap.any { (_, unit) -> unit < 0 }) {
            return false
        }

        return calculateTotalUnits() <= CreateGridRegionPlugin.configFile.unitLimit
    }
}