package net.pixaurora.kitten_sounds.impl.service;

import net.minecraft.client.Minecraft;
import net.pixaurora.kitten_heart.impl.service.MusicCompat;
import net.pixaurora.kitten_sounds.impl.mixin.MusicManagerAccessor;

public class MusicCompatImpl implements MusicCompat {
    private static final long MILLIS_PER_TICK = 50;

    private final Minecraft client = Minecraft.getInstance();

    @Override
    public long millisToNextSong() {
        long nextSongDelay = ((MusicManagerAccessor) this.client.getMusicManager()).getNextSongDelay();
        return MILLIS_PER_TICK * nextSongDelay;
    }
}
