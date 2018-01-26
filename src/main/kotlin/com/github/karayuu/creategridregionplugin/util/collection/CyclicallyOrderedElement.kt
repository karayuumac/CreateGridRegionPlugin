package com.github.karayuu.creategridregionplugin.util.collection

/**
 * 巡回順序(Cyclic order)が定義される集合Sの元を表すクラスです。
 *
 * [setSize]は集合Sの要素数を表します。
 *
 * Sは全てのx∈Sに対して以下の条件を満たす関数succ: (S) -> Sを持たなければなりません：
 * * succ^[setSize](s) = s ([setSize]回の巡回で元に戻ってくる)
 * * S = { succ^n(s) | 0 <= n < [setSize] } ([setSize]-1回の巡回でSの全てを通る)
 *
 * Sは例えば方位を表す集合であったり、離散的な色空間であったりします。
 *
 * @param S 巡回順序を定義する対象のクラス
 *
 * @author kory33
 */
interface CyclicallyOrderedElement<out S: CyclicallyOrderedElement<S>> {
    /**
     * succ(this)を返します。
     */
    fun successor(): S

    /**
     * 集合Sの要素数。
     */
    val setSize: Int
}

/**
 * 順序集合[S]の中で、レシーバーから引数まで何度巡回を行えばいいかを探索します。
 */
fun <S: CyclicallyOrderedElement<S>> S.distanceTo(element: S): Int = when(this) {
    element -> 0
    else -> 1 + successor().distanceTo(element)
}

/**
 * 順序集合[S]の中で、レシーバから[target]回の巡回を行った結果を計算します。
 */
fun <S: CyclicallyOrderedElement<S>> S.progress(target: Int): S = (target % setSize).let { relativePhase ->
    when {
        relativePhase != target -> progress(relativePhase)
        relativePhase == 0 -> this
        relativePhase < 0 -> progress(relativePhase + setSize)
        else -> successor().progress(target - 1)
    }
}