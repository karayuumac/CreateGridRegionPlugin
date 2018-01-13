package com.github.karayuu.util

import com.github.karayuu.CreateGridRegionPlugin

/**
 * Created by karayuu on 2018/01/13
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
/**
 * 警告をコンソールに表示させます。
 */
fun sendWarning(msg: String) = CreateGridRegionPlugin.plugin.logger.warning(msg)

fun sendInfo(msg: String) = CreateGridRegionPlugin.plugin.logger.info(msg)
