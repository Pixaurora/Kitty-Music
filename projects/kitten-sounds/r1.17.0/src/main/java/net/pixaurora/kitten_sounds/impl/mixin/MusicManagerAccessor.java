package net.pixaurora.kitten_sounds.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.sounds.MusicManager;

@Mixin(MusicManager.class)
public interface MusicManagerAccessor {
    @Accessor
    public int getNextSongDelay();
}
