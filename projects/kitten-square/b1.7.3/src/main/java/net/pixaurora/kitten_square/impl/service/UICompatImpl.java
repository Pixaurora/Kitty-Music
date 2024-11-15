package net.pixaurora.kitten_square.impl.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.resource.language.I18n;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.sound.Sound;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;
import net.pixaurora.kitten_heart.impl.resource.temp.FileAccess;
import net.pixaurora.kitten_heart.impl.service.MinecraftUICompat;
import net.pixaurora.kitten_square.impl.FakeComponent;
import net.pixaurora.kitten_square.impl.SoundUtil;
import net.pixaurora.kitten_square.impl.ui.screen.MinecraftScreen;
import net.pixaurora.kitten_square.impl.ui.screen.ScreenImpl;
import net.pixaurora.kitten_square.impl.ui.toast.ToastImpl;
import net.pixaurora.kitten_square.impl.ui.widget.TextBoxImpl;

public class UICompatImpl implements MinecraftUICompat {
    // private final Minecraft client = Minecraft.INSTANCE;

    public static String internalToMinecraftType(ResourcePath path) {
        String namespacePart = path.namespace() != "" ? path.namespace() + "/" : "";
        return "assets/" + namespacePart + path.path();
    }

    public static String internalToMinecraftType(Component component) {
        if (component instanceof FakeComponent) {
            return ((FakeComponent) component).text();
        } else {
            throw new RuntimeException(
                    "Internal component is of an unconvertable type `" + component.getClass().getName() + "`!");
        }
    }

    @Override
    public void sendToast(net.pixaurora.kitten_cube.impl.ui.toast.Toast toast) {
        // this.client.getToasts().addToast(new ToastImpl(toast));
    }

    @Override
    public ResourcePath convertToRegularAsset(ResourcePath path) {
        return path;
    }

    @Override
    public ResourcePath convertToGuiAsset(ResourcePath path) {
        return path;
    }

    @Override
    public Component translatable(String key) {
        return new FakeComponent(I18n.translate(key));
    }

    @Override
    public Component translatableWithFallback(String key, String defaultText) {
        boolean translationExists = key != I18n.translate(key);

        Component component = translationExists ? this.translatable(key) : this.literal(defaultText);
        return component;
    }

    @Override
    public Component literal(String text) {
        return new FakeComponent(text);
    }

    @Override
    public int textHeight() {
        return 0;
        // return this.client.font.lineHeight;
    }

    @Override
    public int textWidth(Component text) {
        return 0;
        // return this.client.font.width(internalToMinecraftType(text));
    }

    @Override
    public void playSound(Sound sound) {
        // this.client.getSoundManager().play(SoundUtil.soundFromInternalID(sound));
    }

    @Override
    public void setScreen(Screen screen) {
        // net.minecraft.client.gui.screens.Screen mcScreen;
        // if (screen instanceof MinecraftScreen) {
        // mcScreen = ((MinecraftScreen) screen).parent();
        // } else {
        // mcScreen = new ScreenImpl(screen);
        // }
        // this.client.setScreen(mcScreen);
    }

    @Override
    public void openURL(String url) {
        // Util.getPlatform().openUri(url);
    }

    @Override
    public TextBox createTextbox(List<Component> lines, Color color, int maxLineLength, Point pos) {
        // List<FormattedCharSequence> text =
        // lines.stream().map(UICompatImpl::internalToMinecraftType)
        // .flatMap(line -> this.client.font.split(line,
        // maxLineLength).stream()).toList();

        return new TextBoxImpl(null, color, pos);
    }

    @Override
    public FileAccess accessResource(ResourcePath path) throws IOException {
        // TODO: Maybe change this workaround?
        // Resource resource =
        // this.client.getResourceManager().getResource(internalToMinecraftType(path))

        return FileAccess.create(Files.newInputStream(Paths.get("resources").resolve(internalToMinecraftType(path))));
    }
}
