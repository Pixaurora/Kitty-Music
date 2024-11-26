package net.pixaurora.kitten_square.impl.ui.display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FormattedCharSequence;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;
import net.pixaurora.kitten_square.impl.ui.ConversionCacheImpl;
import net.pixaurora.kitten_square.impl.ui.widget.TextBoxImpl;

public class GuiDisplayImpl implements GuiDisplay {
    private final GuiGraphics graphics;

    private final ConversionCacheImpl conversions;

    TutorialToast toast;

    public GuiDisplayImpl(GuiGraphics graphics, ConversionCacheImpl conversions) {
        this.graphics = graphics;
        this.conversions = conversions;
    }

    @Override
    public void drawTexture(ResourcePath path, int width, int height, int x, int y) {
        this.graphics.blit(RenderType::guiTextured, conversions.convert(path), x, y, 0.0F, 0.0F, width, height, width,
                height);
    }

    @Override
    public void drawGuiTextureSubsection(ResourcePath path, int width, int height, int x, int y, int subsectionWidth,
            int subsectionHeight, int offsetX, int offsetY) {
        this.graphics.blitSprite(RenderType::guiTextured, conversions.convert(path), width, height, offsetX, offsetY, x,
                y, subsectionWidth, subsectionHeight);
    }

    @SuppressWarnings("resource")
    @Override
    public void drawText(Component text, Color color, int x, int y, boolean shadowed) {
        this.graphics.drawString(Minecraft.getInstance().font, conversions.convert(text), x, y, color.hex(), shadowed);
    }

    @SuppressWarnings("resource")
    @Override
    public void drawTextBox(TextBox textBox) {
        if (textBox instanceof TextBoxImpl) {
            TextBoxImpl impl = (TextBoxImpl) textBox;

            int y = impl.startPos.y();

            for (FormattedCharSequence line : impl.lines) {
                this.graphics.drawString(Minecraft.getInstance().font, line, impl.startPos.x(), y, impl.color.hex(),
                        false);

                y += MinecraftClient.textHeight();
            }
        } else {
            throw new UnsupportedOperationException("Unsupported instance of textbox");
        }
    }
}
