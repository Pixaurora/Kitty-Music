package net.pixaurora.kitten_sounds.impl.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.client.sound.system.SoundEngine;
import net.minecraft.client.sound.system.SoundFile;
import net.pixaurora.kitten_sounds.impl.MusicPolling;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
    @ModifyExpressionValue(method = "tickMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/system/Sounds;getRandom()Lnet/minecraft/client/sound/system/SoundFile;"))
    private SoundFile onBackgroundMusicQueued(SoundFile sound) {
        if (sound != null) {
            MusicPolling.onPlaySong(sound, "BgMusic");
        }

        return sound;
    }

    @ModifyExpressionValue(method = "playRecord", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/system/Sounds;getRandom(Ljava/lang/String;)Lnet/minecraft/client/sound/system/SoundFile;"))
    private SoundFile onRecordQueued(SoundFile sound) {
        if (sound != null) {
            MusicPolling.onPlaySong(sound, "streaming");
        }

        return sound;
    }

    /*
     * There's a short time between when we queue a song and it registers as
     * "playing."
     * Because of this, we skip ticking music cooldowns until it's playing.
     */
    @ModifyExpressionValue(method = "tickMusic", at = @At(value = "FIELD", target = "Lnet/minecraft/client/sound/system/SoundEngine;started:Z", opcode = Opcodes.GETSTATIC))
    private boolean isWaitingForSongToStart(boolean started) {
        return started && MusicPolling.TRACKS_TO_POLL.isEmpty();
    }

    @Inject(method = "tickMusic", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo cInfo) {
        MusicPolling.pollTrackProgress();
    }
}
