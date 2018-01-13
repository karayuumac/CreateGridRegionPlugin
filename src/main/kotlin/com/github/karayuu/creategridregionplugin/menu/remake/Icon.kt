package com.github.karayuu.creategridregionplugin.menu.remake

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author unicroak
 */
data class Icon(
        val material: Material,
        val data: Int,
        val amount: Int = 1,
        val isEnchanted: Boolean = false,
        val name: String? = null,
        val lore: List<String> = listOf()
) {
    val itemStack: ItemStack
        get() {
            val itemStack = ItemStack(material, amount, data.toShort())
            val itemMeta = itemStack.itemMeta.clone()

            if (isEnchanted) {
                itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true)
                itemMeta.itemFlags += ItemFlag.HIDE_ENCHANTS
            }

            name?.let { itemMeta.displayName = name }

            if (lore.isNotEmpty()) {
                itemMeta.lore = lore
            }

            itemStack.itemMeta = itemMeta
            return itemStack
        }

}