package net.pixaurora.kitten_heart.impl;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.Optional;

import net.pixaurora.catculator.api.music.SoundFile;
import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_heart.impl.error.UnhandledKitTunesException;
import net.pixaurora.kitten_heart.impl.event.TrackEventImpl;
import net.pixaurora.kitten_heart.impl.music.control.MusicControls;
import net.pixaurora.kitten_heart.impl.music.metadata.MusicMetadata;
import net.pixaurora.kitten_heart.impl.music.progress.PlayingSong;
import net.pixaurora.kitten_heart.impl.music.progress.PolledListeningProgress;
import net.pixaurora.kitten_heart.impl.resource.temp.FileAccess;

public class EventHandling {
    private static final Map<ListeningProgress, PlayingSong> PLAYING_TRACKS = new HashMap<>();
    private static List<Runnable> MAIN_THREAD_TASKS = new ArrayList<>();

    public static PolledListeningProgress handleTrackStart(ResourcePath path, MusicControls controls) {
        PolledListeningProgress progress = new PolledListeningProgress();

        KitTunes.EXECUTOR.execute(() -> {
            TrackStartEvent event = createStartEvent(path, progress, controls);

            processEvent(listener -> listener.onTrackStart(event));
        });

        return progress;
    }

    public static void handleTrackEnd(ListeningProgress progress) {
        KitTunes.EXECUTOR.execute(() -> {
            handleTrackEnd(getTrackInfo(progress, true));
        });
    }

    private static void handleTrackEnd(PlayingSong song) {
        TrackEndEvent event = new TrackEventImpl(song);

        recordTrack(event);

        processEvent(listener -> listener.onTrackEnd(event));
    }

    private static void recordTrack(TrackEndEvent event) {
        if (!event.record().isPresent()) {
            return;
        }

        ListenRecord record = event.record().get();

        KitTunes.LISTEN_HISTORY.execute(history -> history.record(record));
        try {
            KitTunes.LISTEN_HISTORY.save();
        } catch (IOException e) {
            KitTunes.LOGGER.error("Failed to save listen history while scrobbling!", e);
        }
    }

    public static void init() {
    }

    public static void tick() {
        synchronized (MAIN_THREAD_TASKS) {
            for (Runnable task : MAIN_THREAD_TASKS) {
                task.run();
            }

            MAIN_THREAD_TASKS.clear();
        }

        synchronized (PLAYING_TRACKS) {
            for (PlayingSong song : PLAYING_TRACKS.values()) {
                if (song.sentMiddleEvent()) {
                    continue;
                }

                if (song.canSendMiddleEvent()) {
                    processEvent(listener -> listener
                            .onTrackMiddleReached(new TrackEventImpl(song)));
                }
            }
        }
    }

    public static void addMainThreadTask(Runnable task) {
        synchronized (MAIN_THREAD_TASKS) {
            MAIN_THREAD_TASKS.add(task);
        }
    }

    public static boolean isTracking(ListeningProgress progress) {
        synchronized (PLAYING_TRACKS) {
            return PLAYING_TRACKS.containsKey(progress);
        }
    }

    public static boolean isTrackingAnything() {
        synchronized (PLAYING_TRACKS) {
            return PLAYING_TRACKS.size() > 0;
        }
    }

    public static void stop() {
        synchronized (PLAYING_TRACKS) {
            PLAYING_TRACKS.values().forEach(EventHandling::handleTrackEnd);
        }

        tick(); // Tick one last time to clear any remaining tasks out.
    }

    public static Collection<PlayingSong> playingSongs() {
        synchronized (PLAYING_TRACKS) {
            return PLAYING_TRACKS.values();
        }
    }

    private static TrackStartEvent createStartEvent(ResourcePath path, ListeningProgress progress,
            MusicControls controls) {
        Optional<Track> track = MusicMetadata.matchTrack(path);

        if (track.isPresent()) {
            Duration songDuration = UnhandledKitTunesException.runOrThrow(() -> songDuration(path));

            MusicMetadata.asMutable().giveDuration(track.get(), songDuration);
        }

        PlayingSong song = new PlayingSong(path, track, progress, controls);

        synchronized (PLAYING_TRACKS) {
            PLAYING_TRACKS.put(progress, song);
        }

        return new TrackEventImpl(song);
    }

    private static Duration songDuration(ResourcePath path) throws IOException {
        try (FileAccess resource = MinecraftClient.accessResource(path)) {
            return SoundFile.parseDuration(resource.path());
        }
    }

    private static PlayingSong getTrackInfo(ListeningProgress progress,
            boolean flushFromMap) {
        synchronized (PLAYING_TRACKS) {
            PlayingSong trackInfo = Objects.requireNonNull(PLAYING_TRACKS.get(progress));

            if (flushFromMap) {
                PLAYING_TRACKS.remove(progress);
            }

            return trackInfo;
        }
    }

    private static void processEvent(Consumer<MusicEventListener> event) {
        for (MusicEventListener listener : KitTunes.MUSIC_LISTENERS) {
            Runnable eventAction = () -> event.accept(listener);

            if (listener.isSynchronized()) {
                addMainThreadTask(eventAction);
            } else {
                KitTunes.EXECUTOR.execute(eventAction);
            }
        }
    }
}
