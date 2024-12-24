package net.pixaurora.kitten_square.impl.ui.toast;

import java.time.Duration;

import net.minecraft.client.gui.GuiElement;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.display.AlignedGuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.WindowOffsetAlignment;
import net.pixaurora.kitten_cube.impl.ui.toast.Toast;

public class ToastRenderer extends GuiElement {
    private static final long ENTER_LENGTH = Duration.ofSeconds(1).toMillis();
    private static final long EXIT_LENGTH = ENTER_LENGTH;

    private final Toast toast;

    private final int offsetY;
    private final long millisecondsShown;

    private final Alignment alignment;

    private boolean hasRendered;
    private long firstRenderedTime;

    public ToastRenderer(Toast toast, int offsetY) {
        this.toast = toast;
        this.offsetY = offsetY;
        this.millisecondsShown = toast.timeShown().toMillis() + Duration.ofSeconds(2).toMillis();
        this.alignment = new ToastAlignment();
    }

    public int width() {
        return this.toast.size().width();
    }

    public int height() {
        return offsetY + this.toast.size().height();
    }

    public boolean render(GuiDisplay display, Size window, long frameTime) {
        if (!this.hasRendered) {
            this.hasRendered = true;
            this.firstRenderedTime = frameTime;
        }

        this.toast.draw(new AlignedGuiDisplay(display, this.alignment, window));

        return frameTime - this.firstRenderedTime > this.millisecondsShown;
    }

    class ToastAlignment implements WindowOffsetAlignment {
        public ToastRenderer toast() {
            return ToastRenderer.this;
        }

        @Override
        public int offsetX(Size window) {
            return window.width() - this.toast().width() + this.animationOffset();
        }

        private int animationOffset() {
            long timeRendered = System.currentTimeMillis() - this.toast().firstRenderedTime;

            if (timeRendered < ENTER_LENGTH) {
                return animationOffset0(ENTER_LENGTH, timeRendered);
            } else if (timeRendered > EXIT_LENGTH + this.toast().millisecondsShown) {
                long animationTime = Math.min(this.toast().millisecondsShown - timeRendered, 0);
                return animationOffset0(EXIT_LENGTH, animationTime);
            } else {
                return 0;
            }
        }

        private int animationOffset0(long finishTime, long currentTime) {
            float percentOnScreen = (float) currentTime / finishTime;

            float offset = this.toast().width() * (1.0f - percentOnScreen);

            return (int) offset;
        }

        @Override
        public int offsetY(Size window) {
            return this.toast().offsetY;
        }
    }
}
