/*
package com.github.karayuu.yml

import com.github.karayuu.yml.ymls.ConfigYml

/**
 * Created by karayuu on 2017/12/26
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * Ymlファイルの総合管理オブジェクト
 */
object YmlRegistration {
    /**
     * Ymlのenum
     */
    enum class Yml(val simpleYml: SimpleYml) {
        CONFIG(ConfigYml),
    }

    /**
     * Ymlの初期化処理。onEnableで呼び出されることを想定
     */
    fun regist() {
        Yml.values().map { ymlEnum -> ymlEnum.simpleYml }.forEach { simpleYml ->
            simpleYml.regist()
        }
    }
}
*/
