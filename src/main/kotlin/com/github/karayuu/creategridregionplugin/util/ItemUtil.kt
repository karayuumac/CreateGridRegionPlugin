package com.github.karayuu.creategridregionplugin.util

import org.bukkit.ChatColor
import org.bukkit.inventory.ItemStack

/**
 * Created by karayuu on 2017/12/26
 * Developer of Gigantic☆Seichi Server
 * Support at dev-basic or dev-extreme channel of Discord
 */

/**
 * ItemStackの表示名を変更します
 */
infix fun ItemStack.setDisplayName(name: String) {
    itemMeta = itemMeta.also { meta ->
        meta.displayName = name
    }
}

/**
 * ItemStackの表示名を変更できます(メニュー向け)
 * nameにChatColorを指定した場合,prefixには""を指定してください
 */
fun ItemStack.setTitle(name: String, prefix: String = "${ChatColor.AQUA}${ChatColor.BOLD}${ChatColor.UNDERLINE}") {
    itemMeta = itemMeta.also { meta ->
        meta.displayName = prefix + name
    }
}

/**
 * 説明文を変更します
 * nameにChatColorを指定した場合,prefixには""を指定してください
 */
fun ItemStack.setLore(vararg lore: String, prefix: String = "${ChatColor.GRAY}") {
    itemMeta = itemMeta.also { meta ->
        meta.lore = lore.map { lore -> "${ChatColor.RESET}$prefix$lore" }.toList()
    }
}

/**
 * 説明文を追加します
 * nameにChatColorを指定した場合,prefixには""を指定してください
 */
fun ItemStack.addLore(vararg lore: String, prefix: String = "${ChatColor.GRAY}") {
    itemMeta = itemMeta.also { meta ->
        lore.map { "${ChatColor.RESET}$prefix$it" }.let { newLore ->
            meta.lore = meta.lore?.apply { addAll(newLore) } ?: newLore
        }
    }
}

/**
 * クリックで開くボタンを追加します
 */
fun ItemStack.addClickToOpenLore() {
    addLore("${ChatColor.GREEN}${ChatColor.BOLD}${ChatColor.UNDERLINE}" + "クリックで開く")
}

/**
 * グリッド式保護「これ以上拡大できません」警告lore
 */
fun ItemStack.cantExtendLore() {
    addLore("${ChatColor.RED}${ChatColor.UNDERLINE}これ以上拡大できません", prefix = "")
}

/**
 * グリッド式保護「これ以上縮小できません」警告lore
 */
fun ItemStack.cantReduceLore() {
    addLore("${ChatColor.RED}${ChatColor.UNDERLINE}これ以上縮小できません", prefix = "")
}
