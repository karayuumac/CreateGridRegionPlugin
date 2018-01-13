package com.github.karayuu.creategridregionplugin.player.property

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin
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
    fun getUnitMap() : Map<DirectionType, Int> {
        val unitMap: MutableMap<DirectionType, Int> = mutableMapOf()

        unitMap[DirectionType.AHEAD] = aheadUnit
        unitMap[DirectionType.BEHIND] = behindUnit
        unitMap[DirectionType.RIGHT] = rightUnit
        unitMap[DirectionType.LEFT] =leftUnit

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
    fun canExtendGrid(type: DirectionType) : Boolean {
        val unitLimit = config.unitLimit
        val unitMap = getUnitMap()

        //拡大すると仮定する
        val assumedAmount = (unitMap[type] ?: 0) + unitPerClick

        //各種設定値
        val ahead = unitMap[DirectionType.AHEAD] ?: 0
        val behind = unitMap[DirectionType.BEHIND] ?: 0
        val right = unitMap[DirectionType.RIGHT] ?: 0
        val left = unitMap[DirectionType.LEFT] ?: 0

        //合計unit数(判断用)
        val assumedUnitAmount = when (type) {
            DirectionType.AHEAD -> (assumedAmount + 1 + behind) * (right + 1 + left)
            DirectionType.BEHIND -> (ahead + 1 + assumedAmount) * (right + 1 + left)
            DirectionType.RIGHT -> (ahead + 1 + behind) * (assumedAmount + 1 + left)
            DirectionType.LEFT -> (ahead + 1 + behind) * (right + 1 + assumedAmount)
        }

        return assumedUnitAmount <= unitLimit
    }

    /**
     * グリッドが縮小可能かを返します
     * @param type 方向Enum
     * @return true: 縮小可能 / false: 縮小不可能
     */
    fun canReduceGrid(type: DirectionType) : Boolean =
            0 <= ((getUnitMap()[type] ?: 0) - unitPerClick)

    /**
     * unit数を設定します
     * @param type 方向Enum
     * @param amount 設定値
     */
    fun setUnitAmount(type: DirectionType, amount: Int) {
        when (type) {
            DirectionType.AHEAD -> aheadUnit = amount
            DirectionType.BEHIND -> behindUnit = amount
            DirectionType.RIGHT -> rightUnit = amount
            DirectionType.LEFT -> leftUnit = amount
        }
    }

    /**
     * unit数を増加させます
     * @param type 方向Enum
     * @param increament 増加量
     */
    fun addUnitAmount(type: DirectionType, increament: Int) {
        when (type) {
            DirectionType.AHEAD -> aheadUnit += increament
            DirectionType.BEHIND -> behindUnit += increament
            DirectionType.RIGHT -> rightUnit += increament
            DirectionType.LEFT -> leftUnit += increament
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

    /**
     * グリッド式保護で方向を指定するためのenum
     */
    enum class DirectionType {
        AHEAD,
        BEHIND,
        RIGHT,
        LEFT
    }
}
