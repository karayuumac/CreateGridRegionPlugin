package com.github.karayuu.creategridregionplugin.util.selection

import com.github.karayuu.creategridregionplugin.util.Vec2
import com.github.karayuu.creategridregionplugin.util.direction.CardinalDirection
import com.github.karayuu.creategridregionplugin.util.direction.CardinalDirection.EAST
import com.github.karayuu.creategridregionplugin.util.direction.RelativeDirection
import com.github.karayuu.creategridregionplugin.util.direction.RelativeDirection.AHEAD
import com.github.karayuu.creategridregionplugin.util.plus
import com.github.karayuu.creategridregionplugin.util.times
import com.sk89q.worldedit.bukkit.selections.Selection
import org.bukkit.Location

/**
 * グリッド領域の選択状態を表すデータクラス
 *
 * @author kory33, karayuu
 */
data class GridSelection(val unitChange: UnitChange = UnitChange.ONE,
                         val selectionSize: DirectionalSelectionSize = DirectionalSelectionSize()) {
    /**
     * グリッド領域を指定方向に拡大します。(結果の選択状態の正常性は保証されません。)
     * @param direction 領域拡大の方向
     * @return 新しいグリッド領域の選択状態
     */
    private fun extendAlong(direction: RelativeDirection) =
            withNewSize(selectionSize.addUnitAlong(direction, unitChange.amount))

    /**
     * グリッド領域を指定方向に縮小します。(結果の選択状態の正常性は保証されません。)
     * @param direction 領域縮小の方向
     * @return 新しいグリッド領域の選択状態
     */
    private fun reduceAlong(direction: RelativeDirection) =
            withNewSize(selectionSize.reduceUnitAlong(direction, unitChange.amount))

    /**
     * グリッド領域が指定方向に拡大可能かを返します
     * @param direction 領域拡大の方向
     * @return true: 拡張可能 / false: 拡張不可能
     */
    fun canExtendAlong(direction: RelativeDirection) = extendAlong(direction).selectionSize.isValid()

    /**
     * グリッド領域が指定方向に縮小可能かを返します
     * @param direction 領域縮小の方向
     * @return true: 縮小可能 / false: 縮小不可能
     */
    fun canReduceAlong(direction: RelativeDirection) = reduceAlong(direction).selectionSize.isValid()

    /**
     * グリッド領域が指定方向に拡張できるなら拡張された領域を、そうでないならこの領域を取得します。
     * @param direction 領域拡大の方向
     * @return 新しいグリッド領域の選択状態
     */
    fun safeExtendAlong(direction: RelativeDirection) = if (canExtendAlong(direction)) extendAlong(direction) else this

    /**
     * グリッド領域が指定方向に縮小できるなら縮小された領域を、そうでないならこの領域を取得します。
     * @param direction 領域縮小の方向
     * @return 新しいグリッド領域の選択状態
     */
    fun safeReduceAlong(direction: RelativeDirection) = if (canReduceAlong(direction)) reduceAlong(direction) else this

    /**
     * 1クリックでの増減量を切り替えます。
     *
     * @return 新しいグリッド領域の選択状態
     */
    fun toggleUnitChange() = GridSelection(unitChange.getNextSize())

    /**
     * 与えられた領域サイズで新しいグリッド領域を用意します。
     * @param selectionSize 新しい領域サイズ
     * @return 新しいグリッド領域の選択状態
     */
    private fun withNewSize(selectionSize: DirectionalSelectionSize) =
            GridSelection(unitChange = unitChange, selectionSize = selectionSize)

    /**
     * 与えられた[Location]を中心とするグリッド領域を構築し[Selection]として返します。
     * @param centerLocation グリッド領域発行の中心地点を表す[Location]
     * @return [centerLocation]の向きを考慮したグリッド領域全体を表す選択領域[Selection]
     */
    fun toWorldEditSelection(centerLocation: Location): Selection {
        val locationCardinalDirection = CardinalDirection.fromLocation(centerLocation)

        /**
         * [centerLocation]から東(+X)への時計回りの回転回数
         */
        val rotationToEast = generateSequence(0, { it + 1 })
                .find { locationCardinalDirection.turnRight(it) == EAST }!!

        /**
         * 中央グリッドの-XZ方向にある頂点からグリッド領域の-XZ方向にある頂点までのベクトル
         */
        val minRelativeToCenterMin = Vec2(
                // 西向き(-X方向)のユニット数
                selectionSize[AHEAD.turnRight(rotationToEast - 2)].toDouble(),
                // 北向き(-Z方向)のユニット数
                selectionSize[AHEAD.turnRight(rotationToEast - 1)].toDouble()
        ) * (-15.0)

        /**
         * 中央グリッドの+XZ方向にある頂点からグリッド領域の+XZ方向にある頂点までのベクトル
         */
        val maxRelativeToCenterMin = Vec2(
                // 東向き(+X方向)のユニット数
                selectionSize[AHEAD.turnRight(rotationToEast)].toDouble(),
                // 南向き(+Z方向)のユニット数
                selectionSize[AHEAD.turnRight(rotationToEast + 1)].toDouble()
        ) * 15.0 + Vec2(15.0, 15.0)

        /**
         * [centerLocation]を含むグリッドの-XZ方向にある頂点の座標
         */
        val centerGridMinPoint = Vec2(
                Math.floor(centerLocation.x / 15.0) * 15.0,
                Math.floor(centerLocation.z / 15.0) * 15.0
        )

        val absoluteMinPoint = centerGridMinPoint + minRelativeToCenterMin
        val absoluteMaxPoint = centerGridMinPoint + maxRelativeToCenterMin

        return VertExtendedCuboidSelection(centerLocation.world, absoluteMinPoint, absoluteMaxPoint)
    }

    /**
     * 一回の操作でのユニット数の増減を表す列挙型
     */
    enum class UnitChange(val amount: Int) {
        ONE(1), TEN(10), HUNDRED(100);

        fun getNextSize() = when(this) {
            ONE -> TEN
            TEN -> HUNDRED
            HUNDRED -> ONE
        }

        override fun toString() = amount.toString()

        fun toBlockWidth() = amount * 15
    }
}
