package net.pixaurora.kitten_sounds.impl;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.mojang.blaze3d.audio.Channel;

import net.minecraft.client.sounds.ChannelAccess.ChannelHandle;
import net.pixaurora.kitten_heart.impl.music.control.MusicControls;
import net.pixaurora.kitten_heart.impl.music.control.PlaybackState;

public class MusicControlsImpl implements MusicControls {
    private Optional<ChannelHandle> channel = Optional.empty();
    private final AtomicReference<PlaybackState> playbackState = new AtomicReference<>(PlaybackState.STOPPED);

    public void channel(ChannelHandle channel) {
        this.channel = Optional.of(channel);
    }

    @Override
    public void pause() {
        if (this.channel.isPresent()) {
            this.channel.get().execute(Channel::pause);
        }
    }

    @Override
    public void unpause() {
        if (this.channel.isPresent()) {
            this.channel.get().execute(Channel::unpause);
        }
    }

    @Override
    public PlaybackState playbackState() {
        return this.playbackState.get();
    }

    public void updatePlaybackState(Channel channel) {
        this.playbackState.set(computePlaybackState(channel));
    }

    public PlaybackState computePlaybackState(Channel channel) {
        if (channel.playing()) {
            return PlaybackState.PLAYING;
        } else {
            return PlaybackState.PAUSED;
        }
    }
}
