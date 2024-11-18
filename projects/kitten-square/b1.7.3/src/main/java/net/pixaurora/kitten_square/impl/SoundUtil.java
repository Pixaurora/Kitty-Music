package net.pixaurora.kitten_square.impl;

// import net.minecraft.client.resources.sounds.SimpleSoundInstance;
// import net.minecraft.client.resources.sounds.SoundInstance;
// import net.minecraft.sounds.SoundEvents;
import net.pixaurora.kitten_cube.impl.ui.sound.Sound;

public class SoundUtil {
    public static String soundFromInternalID(Sound sound) {
        switch (sound) {
            case BUTTON_CLICK:
                return "random.click";
            default:
                throw new RuntimeException("Sound " + sound.name() + " was not mapped!");
        }
    }
}
