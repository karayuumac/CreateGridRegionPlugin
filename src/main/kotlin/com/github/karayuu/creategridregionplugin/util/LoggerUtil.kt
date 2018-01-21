package com.github.karayuu.creategridregionplugin.util

import com.github.karayuu.creategridregionplugin.CreateGridRegionPlugin

/**
 * Created by karayuu on 2018/01/13
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
/**
 * 警告をコンソールに表示させます。
 */
fun sendWarning(msg: String) = CreateGridRegionPlugin.instance.logger.warning(msg)

fun sendInfo(msg: String) = CreateGridRegionPlugin.instance.logger.info(msg)
