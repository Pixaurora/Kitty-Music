package net.pixaurora.kitten_sounds.impl.service;

import net.minecraft.client.Minecraft;
import net.pixaurora.kitten_heart.impl.service.MusicCompat;

public class MusicCompatImpl implements MusicCompat {
    private static final long MILLIS_PER_TICK = 50;

    @Override
    public long millisToNextSong() {
        return MILLIS_PER_TICK * Minecraft.INSTANCE.soundSystem.musicCooldown;
    }
}
