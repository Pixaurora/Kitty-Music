package net.pixaurora.kitten_heart.impl.music.progress;

import java.time.Duration;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.Constants;
import net.pixaurora.kitten_heart.impl.music.control.MusicControls;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressProvider;

public class PlayingSong implements ProgressProvider {
    private final ResourcePath path;
    private final Optional<Track> track;

    private final ListeningProgress progress;
    private final MusicControls controls;

    private boolean hasSentMiddleEvent;
    private final long middleEventMillis;

    public PlayingSong(ResourcePath path, Optional<Track> track, ListeningProgress progress, MusicControls controls) {
        this.path = path;
        this.track = track;
        this.progress = progress;
        this.controls = controls;
        this.middleEventMillis = track.map(Track::duration).map(duration -> duration.toMillis() * 6 / 10)
                .orElse(Constants.MINIMUM_TIME_TO_SCROBBLE.toMillis());
    }

    public ResourcePath path() {
        return this.path;
    }

    public Optional<Track> track() {
        return this.track;
    }

    public ListeningProgress progress() {
        return this.progress;
    }

    public MusicControls controls() {
        return this.controls;
    }

    public boolean sentMiddleEvent() {
        return this.hasSentMiddleEvent;
    }

    public boolean canSendMiddleEvent() {
        if (this.middleEventMillis < this.progress.amountPlayed().toMillis()) {
            this.hasSentMiddleEvent = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double percentComplete() {
        return (double) this.playedDuration().toMillis() / this.totalDuration().toMillis();
    }

    @Override
    public Duration playedDuration() {
        return this.progress.amountPlayed();
    }

    @Override
    public Duration totalDuration() {
        if (this.track.isPresent()) {
            return this.track.get().duration();
        } else {
            return this.playedDuration();
        }
    }
}
