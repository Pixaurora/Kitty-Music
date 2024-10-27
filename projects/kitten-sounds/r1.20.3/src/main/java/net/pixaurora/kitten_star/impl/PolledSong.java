package net.pixaurora.kitten_star.impl;

import net.pixaurora.kitten_heart.impl.music.progress.PolledListeningProgress;

public class PolledSong<T> {
    private final T polled;

    private final PolledListeningProgress progress;
    private final MusicControlsImpl controls;

    public PolledSong(T polled, PolledSong<?> previous) {
        this.polled = polled;
        this.progress = previous.progress;
        this.controls = previous.controls;
    }

    public PolledSong(T polled, PolledListeningProgress progress, MusicControlsImpl controls) {
        this.polled = polled;
        this.progress = progress;
        this.controls = controls;
    }

    public T polled() {
        return this.polled;
    }

    public PolledListeningProgress progress() {
        return this.progress;
    }

    public MusicControlsImpl controls() {
        return this.controls;
    }
}
