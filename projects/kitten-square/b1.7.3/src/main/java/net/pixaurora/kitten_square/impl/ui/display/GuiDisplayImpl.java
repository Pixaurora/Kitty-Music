package net.pixaurora.kitten_square.impl.ui.display;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.vertex.BufferBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;
import net.pixaurora.kitten_square.impl.ui.ConversionCacheImpl;
import net.pixaurora.kitten_square.impl.ui.widget.TextBoxImpl;

public class GuiDisplayImpl implements GuiDisplay {
    private final GuiElement element;
    private final ConversionCacheImpl conversions;

    private int offsetX;
    private int offsetY;

    public GuiDisplayImpl(GuiElement element, ConversionCacheImpl conversions, int offsetX, int offsetY) {
        this.element = element;
        this.conversions = conversions;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public GuiDisplayImpl(GuiElement element, ConversionCacheImpl conversions) {
        this(element, conversions, 0, 0);
    }

    @Override
    public void drawTexture(ResourcePath path, Size size, Point pos) {
        this.drawGuiTextureSubsection(path, size, pos, size, Point.ZERO);
    }

    @Override
    public void drawGuiTextureSubsection(ResourcePath path, Size size, Point pos, Size subsection, Point offset) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.INSTANCE.textureManager.load(this.conversions.convert(path)));

        int x = pos.x();
        int y = pos.y();
        float textureWidth = size.width();
        float textureHeight = size.height();

        float u = offset.x();
        float v = offset.y();
        int width = subsection.width();
        int height = subsection.height();

        float invertedTexWidth = 1.0f / textureWidth;
        float invertedTexHeight = 1.0f / textureHeight;
        BufferBuilder bufferBuilder = BufferBuilder.INSTANCE;
        bufferBuilder.start();
        bufferBuilder.vertex(x, y + height, 0.0, u * invertedTexWidth, (v + (float) height) * invertedTexHeight);
        bufferBuilder.vertex(x + width, y + height, 0.0, (u + (float) width) * invertedTexWidth,
                (v + (float) height) * invertedTexHeight);
        bufferBuilder.vertex(x + width, y, 0.0, (u + (float) width) * invertedTexWidth, v * invertedTexHeight);
        bufferBuilder.vertex(x, y, 0.0, u * invertedTexWidth, v * invertedTexHeight);
        bufferBuilder.end();
    }

    @Override
    public void drawText(Component text, Color color, Point pos, boolean shadowed) {
        element.drawString(Minecraft.INSTANCE.textRenderer, this.conversions.convert(text), pos.x() + offsetX,
                pos.y() + offsetY, color.hex());
    }

    @Override
    public void drawTextBox(TextBox textBox) {
        if (!(textBox instanceof TextBoxImpl)) {
            throw new UnsupportedOperationException("Unsupported instance of textbox");
        }

        TextBoxImpl impl = (TextBoxImpl) textBox;

        int x = impl.startPos.x() + offsetX;
        int y = impl.startPos.y() + offsetY;

        for (String line : impl.lines) {
            this.element.drawString(Minecraft.INSTANCE.textRenderer, line, x, y, impl.color.hex());

            y += MinecraftClient.textHeight();
        }
    }
}
