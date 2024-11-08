package net.pixaurora.kitten_heart.impl.event;

import java.util.Optional;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackMiddleEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.music.progress.PlayingSong;

public class TrackEventImpl implements TrackStartEvent, TrackMiddleEvent, TrackEndEvent {
    private final ResourcePath path;
    private final Optional<Track> track;
    private final Optional<ListenRecord> record;
    private final ListeningProgress progress;

    public TrackEventImpl(ResourcePath path, Optional<Track> track, Optional<ListenRecord> record,
            ListeningProgress progress) {
        this.path = path;
        this.track = track;
        this.progress = progress;
        this.record = record;
    }

    public TrackEventImpl(PlayingSong song) {
        this(song.path(), song.track(), song.record(), song.progress());
    }

    @Override
    public Optional<Track> track() {
        return this.track;
    }

    @Override
    public ResourcePath searchPath() {
        return this.path;
    }

    @Override
    public ListeningProgress progress() {
        return this.progress;
    }

    @Override
    public Optional<ListenRecord> record() {
        return this.record;
    }
}
