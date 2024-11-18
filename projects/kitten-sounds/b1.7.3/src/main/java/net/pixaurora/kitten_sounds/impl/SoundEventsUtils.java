package net.pixaurora.kitten_sounds.impl;

import net.minecraft.client.sound.system.SoundEngine;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.resource.ResourcePathImpl;
import paulscode.sound.SoundSystem;

public class SoundEventsUtils {
    public static SoundSystem system() {
        return SoundEngine.system;
    }

    public static ResourcePath minecraftTypeToInternalType(String identifier) {
        return new ResourcePathImpl("", identifier);
    }
}
