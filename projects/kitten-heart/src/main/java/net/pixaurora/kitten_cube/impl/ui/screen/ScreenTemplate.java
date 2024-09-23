package net.pixaurora.kitten_cube.impl.ui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.AlignedGuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.screen.align.PointManager;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public abstract class ScreenTemplate implements Screen {
    private boolean initializedWidgets = false;

    private Optional<PointManager> defaultAligner = Optional.empty();
    private Optional<Size> window = Optional.empty();

    private final List<WidgetContainer> widgets = new ArrayList<>();

    @Override
    public final void draw(GuiDisplay gui, Point mousePos) {
        PointManager defaultAligner = this.defaultAligner.get();

        for (WidgetContainer widget : this.widgets) {
            PointManager aligner = widget.customizedAligner().orElse(defaultAligner);

            GuiDisplay alignedGui = new AlignedGuiDisplay(gui, aligner);
            mousePos = this.alignmentMethod().inverseAlign(mousePos, this.window.get());

            widget.get().draw(alignedGui, mousePos);
        }
    }

    @Override
    public final void init(Size window) {
        if (!this.initializedWidgets) {
            this.initializedWidgets = true;
            this.firstInit();
        }

        this.updateWindow(window);
    }

    @Override
    public final void handleClick(Point mousePos, MouseButton button) {
        mousePos = this.alignmentMethod().inverseAlign(mousePos, this.window.get());

        for (WidgetContainer widget : this.widgets) {
            if (widget.get().isWithinBounds(mousePos)) {
                widget.get().onClick(mousePos, button);
                return;
            }
        }
    }

    private void updateWindow(Size window) {
        this.defaultAligner = Optional.of(new PointManager(this.alignmentMethod(), window));
        this.window = Optional.of(window);

        for (WidgetContainer widget : this.widgets) {
            widget.onWindowUpdate(window);
        }
    }

    protected final <W extends Widget> W addWidget(W widget) {
        this.widgets.add(new WidgetContainer(widget));
        return widget;
    }

    protected abstract AlignmentStrategy alignmentMethod();

    protected abstract void firstInit();

}
