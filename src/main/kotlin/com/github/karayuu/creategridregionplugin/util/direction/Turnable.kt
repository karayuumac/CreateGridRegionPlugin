package com.github.karayuu.creategridregionplugin.util.direction

/**
 * 右側に他の[Turnable]が存在するようなオブジェクトへのインターフェースです。
 * 二次元にある「方向」や「方位」の概念を抽象化しています。
 * @param R 右に存在する方向のクラス
 */
interface Turnable<out R: Turnable<R>> {
    /**
     * このオブジェクトが指し示す方向の右にある方向を返します。
     */
    fun directionOnRight(): R

    /**
     * 何度[directionOnRight]を呼べば同じ方向に返ってくるかを示す正の整数。
     */
    val period: Int
}