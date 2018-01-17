package com.github.karayuu.creategridregionplugin.util

/**
 * (x, z)座標を表すタプル
 */
typealias Vec2 = Pair<Double, Double>
operator fun Vec2.plus(vec2: Vec2) = Vec2(first + vec2.first, second + vec2.second)
operator fun Vec2.times(scalar: Double) = Vec2(first * scalar, second * scalar)

