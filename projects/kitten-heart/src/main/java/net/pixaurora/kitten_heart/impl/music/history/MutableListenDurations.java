package net.pixaurora.kitten_heart.impl.music.history;

import java.time.Duration;

import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.history.ListenDurations;

public class MutableListenDurations implements ListenDurations {
    private final ListeningProgress progress;
    private final Duration full;

    public MutableListenDurations(ListeningProgress progress, Duration full) {
        this.progress = progress;
        this.full = full;
    }

    @Override
    public Duration progress() {
        return this.progress.amountPlayed();
    }

    @Override
    public Duration full() {
        return this.full;
    }
}
