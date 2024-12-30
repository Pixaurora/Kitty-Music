package net.pixaurora.kitten_sounds.impl;

import java.util.concurrent.atomic.AtomicReference;

import net.pixaurora.kitten_heart.impl.music.control.MusicControls;
import net.pixaurora.kitten_heart.impl.music.control.PlaybackState;

public class MusicControlsImpl implements MusicControls {
    private String source;
    private final AtomicReference<PlaybackState> playbackState = new AtomicReference<>(PlaybackState.STOPPED);

    public void channel(String source) {
        this.source = source;
    }

    @Override
    public void pause() {
        SoundEventsUtils.system().pause(this.source);
    }

    @Override
    public void unpause() {
        SoundEventsUtils.system().play(this.source);
    }

    @Override
    public PlaybackState playbackState() {
        return this.playbackState.get();
    }

    public void updatePlaybackState() {
        this.playbackState.set(computePlaybackState());
    }

    public PlaybackState computePlaybackState() {
        if (SoundEventsUtils.system().playing(this.source)) {
            return PlaybackState.PLAYING;
        } else {
            return PlaybackState.PAUSED;
        }
    }
}
