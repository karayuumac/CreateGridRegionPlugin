package com.github.karayuu.util

import com.github.karayuu.CreateGridRegionPlugin

/**
 * Created by karayuu on 2017/12/24
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */
/**
 * 警告をコンソールに表示させます。
 */
fun sendWarning(msg: String) = CreateGridRegionPlugin.plugin.logger.warning(msg)
