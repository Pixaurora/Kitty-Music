package net.pixaurora.kitten_heart.impl.listener;

import java.io.IOException;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackMiddleEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.music.history.ListenRecord;

public class ScrobblingMusicListener implements MusicEventListener {
    @Override
    public void onTrackStart(TrackStartEvent event) {
        if (!event.track().isPresent()) {
            return;
        }

        Track track = event.track().get();
        ListeningProgress progress = event.progress();

        ListenRecord record = new ListenRecord(track, progress);

        KitTunes.SCROBBLER_CACHE.execute(
                scrobblers -> scrobblers.startScrobbling(KitTunes.CLIENT, record));
    }

    @Override
    public void onTrackMiddleReached(TrackMiddleEvent event) {
        if (!event.track().isPresent()) {
            return;
        }

        ListenRecord record = new ListenRecord(event.track().get(), event.progress());

        KitTunes.SCROBBLER_CACHE
                .execute(scrobblers -> scrobblers.completeScrobbling(KitTunes.CLIENT, record));
    }

    @Override
    public void onTrackEnd(TrackEndEvent event) {
        if (!event.track().isPresent()) {
            return;
        }

        ListenRecord record = new ListenRecord(event.track().get(), event.progress());

        KitTunes.LISTEN_HISTORY.execute(history -> history.record(record));
        try {
            KitTunes.LISTEN_HISTORY.save();
        } catch (IOException e) {
            KitTunes.LOGGER.error("Failed to save listen history while scrobbling!", e);
        }
    }
}
