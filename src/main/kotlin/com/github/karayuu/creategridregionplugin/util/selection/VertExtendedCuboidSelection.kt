package com.github.karayuu.creategridregionplugin.util.selection

import com.github.karayuu.creategridregionplugin.util.Vec2
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.bukkit.selections.CuboidSelection
import org.bukkit.World

class VertExtendedCuboidSelection(world: World, start: Vec2, end: Vec2)
    : CuboidSelection(world, Vector(start.first, 0.0, start.second), Vector(end.first, 256.0, end.second))