package net.pixaurora.kitten_heart.impl.listener;

import java.io.IOException;
import java.time.Duration;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
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
    public void onTrackEnd(TrackEndEvent event) {
        if (!event.track().isPresent()) {
            return;
        }

        Track track = event.track().get();
        ListeningProgress progress = event.progress();

        ListenRecord record = new ListenRecord(track, progress);

        Duration requiredLength = track.duration().dividedBy(2);
        boolean longEnoughToScrobble = progress.amountPlayed().compareTo(requiredLength) > 0;

        if (longEnoughToScrobble) {
            KitTunes.SCROBBLER_CACHE
                    .execute(scrobblers -> scrobblers.completeScrobbling(KitTunes.CLIENT, record));
        } else {
            float amountPlayed = (float) progress.amountPlayed().toMillis() / 1000;
            float amountRequired = (float) requiredLength.toMillis() / 1000;
            KitTunes.LOGGER.info("Skipping scrobbling " + track.name() + " because it only played for " +
                    amountPlayed + " out of " + amountRequired + " seconds!");
        }

        KitTunes.LISTEN_HISTORY.execute(history -> history.record(record));
        try {
            KitTunes.LISTEN_HISTORY.save();
        } catch (IOException e) {
            KitTunes.LOGGER.error("Failed to save listen history while scrobbling!", e);
        }
    }

}
