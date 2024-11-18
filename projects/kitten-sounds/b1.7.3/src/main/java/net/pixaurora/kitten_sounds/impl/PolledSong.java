package net.pixaurora.kitten_sounds.impl;

import net.pixaurora.kitten_heart.impl.music.progress.PolledListeningProgress;
import net.pixaurora.kitten_heart.impl.music.progress.SongProgressTracker;

public class PolledSong implements SongProgressTracker {
    private final String source;

    private final PolledListeningProgress progress;
    private final MusicControlsImpl controls;

    public PolledSong(String source, PolledSong previous) {
        this.source = source;
        this.progress = previous.progress;
        this.controls = previous.controls;
    }

    public PolledSong(String source, PolledListeningProgress progress, MusicControlsImpl controls) {
        this.source = source;
        this.progress = progress;
        this.controls = controls;
    }

    public String polled() {
        return this.source;
    }

    public PolledListeningProgress progress() {
        return this.progress;
    }

    public MusicControlsImpl controls() {
        return this.controls;
    }

    @Override
    public float kit_tunes$playbackPosition() {
        float millisPlayed = System.currentTimeMillis() - this.progress.startTime().toEpochMilli();
        return millisPlayed / 1000;
    }
}
