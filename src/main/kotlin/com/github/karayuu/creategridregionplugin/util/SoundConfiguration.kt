package com.github.karayuu.creategridregionplugin.util

import org.bukkit.Sound

/**
 * 音を鳴らす際のパラメータをまとめたデータクラスです。
 *
 * TODO volumeとpitchの詳細な説明を書く
 *
 * @author kory33
 *
 * @param sound 音の種類
 * @param volume 音量
 * @param pitch 音程
 */
data class SoundConfiguration(val sound: Sound, val volume: Float, val pitch: Float)