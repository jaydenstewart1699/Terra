/*
 * This file is part of Terra.
 *
 * Terra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package com.dfsek.terra.neoforge;

import net.minecraft.server.command.ServerCommandSource;
import net.neoforged.fml.common.Mod;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.neoforge.NeoForgeServerCommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dfsek.terra.api.command.CommandSender;
import com.dfsek.terra.api.event.events.platform.CommandRegistrationEvent;


@Mod(NeoForgeEntryPoint.MOD_ID)
public final class NeoForgeEntryPoint {
    public static final String MOD_ID = "terra";
    private static final Logger LOGGER = LoggerFactory.getLogger(NeoForgeEntryPoint.class);
    private static final NeoForgePlatform PLATFORM = new NeoForgePlatform();

    public NeoForgeEntryPoint() {
        LOGGER.info("Initializing Terra NeoForge mod...");

        NeoForgeServerCommandManager<CommandSender> manager = new NeoForgeServerCommandManager<>(
            ExecutionCoordinator.simpleCoordinator(),
            SenderMapper.create(
                serverCommandSource -> (CommandSender) serverCommandSource,
                commandSender -> (ServerCommandSource) commandSender)
        );

        manager.brigadierManager().setNativeNumberSuggestions(false);
        PLATFORM.getEventManager().callEvent(new CommandRegistrationEvent(manager));
    }

    public static NeoForgePlatform getPlatform() {
        return PLATFORM;
    }
}
