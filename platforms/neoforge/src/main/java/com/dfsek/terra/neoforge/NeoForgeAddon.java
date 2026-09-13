package com.dfsek.terra.neoforge;

import com.dfsek.terra.lifecycle.LifecyclePlatform;
import com.dfsek.terra.mod.MinecraftAddon;


public final class NeoForgeAddon extends MinecraftAddon {
    public NeoForgeAddon(LifecyclePlatform platform) {
        super(platform);
    }

    @Override
    public String getID() {
        return "terra-neoforge";
    }
}
