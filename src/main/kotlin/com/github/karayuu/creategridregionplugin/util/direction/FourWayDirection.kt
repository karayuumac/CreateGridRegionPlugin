package com.github.karayuu.creategridregionplugin.util.direction

import com.github.karayuu.creategridregionplugin.util.collection.CyclicallyOrderedElement
import com.github.karayuu.creategridregionplugin.util.collection.distanceTo
import com.github.karayuu.creategridregionplugin.util.collection.progress

/**
 * 二次元平面において、「4方向」を指すことができるような方向を表すクラスです。
 *
 * 実装するクラスは、とある方向Dに対してDから時計回りに一番近い方向をsucc(D)と定めてください。
 *
 * @author kory33
 */
interface FourWayDirection<T: FourWayDirection<T>>: CyclicallyOrderedElement<T> {
    override val setSize: Int
        get() = 4
}

/**
 * [FourWayDirection.distanceTo]へのエイリアスです。
 */
fun <T: FourWayDirection<T>> T.rotate(times: Int) = this.progress(times)

/**
 * [FourWayDirection.progress]へのエイリアスです。
 */
fun <T: FourWayDirection<T>> T.rotationTo(direction: T) = this.distanceTo(direction)