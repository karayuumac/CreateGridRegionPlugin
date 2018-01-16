package com.github.karayuu.creategridregionplugin.menu.component

import org.bukkit.event.inventory.InventoryClickEvent

/**
 * [Button]の単純な実装を与えるクラスです。
 */
class SimpleButton(override val icon: Icon, override val action: (InventoryClickEvent) -> Unit): Button