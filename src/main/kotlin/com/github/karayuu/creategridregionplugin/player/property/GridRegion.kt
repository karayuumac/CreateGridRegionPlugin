package com.github.karayuu.creategridregionplugin.player.property

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
import com.github.karayuu.creategridregionplugin.util.direction.RelativeDirection
import java.util.*

/**
 * Created by karayuu on 2017/12/25
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * グリッド式保護関連のデータ
 */
class GridRegion(val uuid: UUID) {
    private val config = CreateGridRegionPlugin.configFile

    /** 前方Unit数 */
    private var aheadUnit: Int = 0
    /** 後方Unit数 */
    private var behindUnit: Int = 0
    /** 右方Unit数 */
    private var rightUnit: Int = 0
    /** 左方Unit数 */
    private var leftUnit: Int = 0

    /** 指定Unitで保護作成可能かどうか */
    var canCreateRegion: Boolean = true
    /** 1クリックで何Unit増加/減少させるか */
    var unitPerClick: Int = 1
        private set

    /**
     * すべての方向のunit数を格納したMapを返します。
     * @return すべての方向のunit数を格納したMap
     */
    fun getUnitMap() : Map<RelativeDirection, Int> {
        val unitMap: MutableMap<RelativeDirection, Int> = mutableMapOf()

        unitMap[RelativeDirection.AHEAD] = aheadUnit
        unitMap[RelativeDirection.BEHIND] = behindUnit
        unitMap[RelativeDirection.RIGHT] = rightUnit
        unitMap[RelativeDirection.LEFT] =leftUnit

        return unitMap.toMap()
    }

    /**
     * unit数を計算して返します。
     * @return unit数
     */
    fun calcGridUnitAmount() : Int = (aheadUnit + 1 + behindUnit) * (rightUnit + 1 + leftUnit)

    /**
     * グリッドが拡大可能かを返します
     * @param type 方向Enum
     * @return true: 拡張可能 / false: 拡張不可能
     */
    fun canExtendGrid(type: RelativeDirection) : Boolean {
        val unitLimit = config.unitLimit
        val unitMap = getUnitMap()

        //拡大すると仮定する
        val assumedAmount = (unitMap[type] ?: 0) + unitPerClick

        //各種設定値
        val ahead = unitMap[RelativeDirection.AHEAD] ?: 0
        val behind = unitMap[RelativeDirection.BEHIND] ?: 0
        val right = unitMap[RelativeDirection.RIGHT] ?: 0
        val left = unitMap[RelativeDirection.LEFT] ?: 0

        //合計unit数(判断用)
        val assumedUnitAmount = when (type) {
            RelativeDirection.AHEAD -> (assumedAmount + 1 + behind) * (right + 1 + left)
            RelativeDirection.BEHIND -> (ahead + 1 + assumedAmount) * (right + 1 + left)
            RelativeDirection.RIGHT -> (ahead + 1 + behind) * (assumedAmount + 1 + left)
            RelativeDirection.LEFT -> (ahead + 1 + behind) * (right + 1 + assumedAmount)
        }

        return assumedUnitAmount <= unitLimit
    }

    /**
     * グリッドが縮小可能かを返します
     * @param type 方向Enum
     * @return true: 縮小可能 / false: 縮小不可能
     */
    fun canReduceGrid(type: RelativeDirection) : Boolean =
            0 <= ((getUnitMap()[type] ?: 0) - unitPerClick)

    /**
     * unit数を設定します
     * @param type 方向Enum
     * @param amount 設定値
     */
    fun setUnitAmount(type: RelativeDirection, amount: Int) {
        when (type) {
            RelativeDirection.AHEAD -> aheadUnit = amount
            RelativeDirection.BEHIND -> behindUnit = amount
            RelativeDirection.RIGHT -> rightUnit = amount
            RelativeDirection.LEFT -> leftUnit = amount
        }
    }

    /**
     * unit数を増加させます
     * @param type 方向Enum
     * @param increament 増加量
     */
    fun addUnitAmount(type: RelativeDirection, increament: Int) {
        when (type) {
            RelativeDirection.AHEAD -> aheadUnit += increament
            RelativeDirection.BEHIND -> behindUnit += increament
            RelativeDirection.RIGHT -> rightUnit += increament
            RelativeDirection.LEFT -> leftUnit += increament
        }
    }

    /**
     * 1クリックでの増加量を変化させます
     * 不正値がセットされていた場合1に戻します
     */
    fun toggleUnitPerClick() {
        unitPerClick = when (unitPerClick) {
            1 -> 10
            10 -> 100
            100 -> 1
            else -> 1
        }
    }

}
