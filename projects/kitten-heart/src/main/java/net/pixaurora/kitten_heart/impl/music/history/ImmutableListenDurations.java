package net.pixaurora.kitten_heart.impl.music.history;

import java.time.Duration;

import net.pixaurora.kit_tunes.api.music.history.ListenDurations;

public class ImmutableListenDurations implements ListenDurations {
    private final Duration progress;
    private final Duration full;

    public ImmutableListenDurations(Duration progress, Duration full) {
        this.progress = progress;
        this.full = full;
    }

    @Override
    public Duration progress() {
        return this.progress;
    }

    @Override
    public Duration full() {
        return this.full;
    }
}
