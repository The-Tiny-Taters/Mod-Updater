package the_tiny_taters.mod_updater.util

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import net.minecraft.server.command.ServerCommandSource

typealias Dispatcher = CommandDispatcher<Source>
typealias Node = LiteralCommandNode<Source>
typealias Context = CommandContext<Source>
typealias Source = ServerCommandSource
