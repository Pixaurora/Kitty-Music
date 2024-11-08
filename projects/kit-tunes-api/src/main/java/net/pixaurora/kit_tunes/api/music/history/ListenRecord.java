package net.pixaurora.kit_tunes.api.music.history;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.scrobble.ScrobblerId;

public class ListenRecord {
    private final Track track;
    private final Optional<Album> album;
    private final Instant timestamp;

    private final ListenDurations durations;

    private final List<ScrobblerId> succeededScrobblers;

    public ListenRecord(Track track, Optional<Album> album, Instant timestamp, ListenDurations durations,
            List<ScrobblerId> succeededScrobblers) {
        this.track = track;
        this.album = album;
        this.timestamp = timestamp;
        this.durations = durations;
        this.succeededScrobblers = succeededScrobblers;
    }

    public Track track() {
        return this.track;
    }

    public Optional<Album> album() {
        return this.album;
    }

    public Instant timestamp() {
        return this.timestamp;
    }

    public ListenDurations durations() {
        return this.durations;
    }

    public List<ScrobblerId> succeededScrobblers() {
        return this.succeededScrobblers;
    }

    public void succeededFor(ScrobblerId scrobbler) {
        this.succeededScrobblers.add(scrobbler);
    }
}
