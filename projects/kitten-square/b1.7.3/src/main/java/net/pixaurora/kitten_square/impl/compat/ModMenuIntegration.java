package net.pixaurora.kitten_square.impl.compat;

import java.util.HashMap;
import java.util.Map;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import net.minecraft.client.gui.screen.Screen;
import net.pixaurora.kitten_heart.impl.Constants;
import net.pixaurora.kitten_heart.impl.ui.screen.KitTunesHomeScreen;
import net.pixaurora.kitten_square.impl.ui.screen.MinecraftScreen;
import net.pixaurora.kitten_square.impl.ui.screen.ScreenImpl;

public class ModMenuIntegration implements ModMenuApi {
    public ScreenImpl modHomeScreen(Screen parent) {
        return new ScreenImpl(new KitTunesHomeScreen(new MinecraftScreen(parent)));
    }

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> configScreenFactories = new HashMap<>();

        configScreenFactories.put(Constants.MOD_ID, this::modHomeScreen);

        return configScreenFactories;
    }
}
