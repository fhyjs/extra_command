package org.eu.hanana.reimu.mc.extra_cmd.quilt;

import net.fabricmc.api.ModInitializer;
import org.quiltmc.loader.api.ModContainer;

import org.eu.hanana.reimu.mc.extra_cmd.fabriclike.ExampleModFabricLike;

public final class ExampleModQuilt implements ModInitializer {
    @Override
    public void onInitialize() {
        // Run the Fabric-like setup.
        ExampleModFabricLike.init();
    }
}
