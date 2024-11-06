package net.pixaurora.kitten_cube.impl.ui.screen;

import java.util.Optional;
import java.util.function.Function;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.screen.align.PointManager;
import net.pixaurora.kitten_cube.impl.ui.screen.align.RelativeAlignment;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public class WidgetContainer<T extends Widget> {
    private final T widget;
    private final ScreenTemplate screen;

    private Optional<AlignmentStrategy> customizedAlignment;
    private Optional<PointManager> aligner;

    public WidgetContainer(T widget, ScreenTemplate screen) {
        this.widget = widget;
        this.screen = screen;
        this.customizedAlignment = Optional.empty();
        this.aligner = Optional.empty();
    }

    public T get() {
        return this.widget;
    }

    public Optional<PointManager> customizedAligner() {
        return this.aligner;
    }

    public WidgetContainer<T> customizedAlignment(AlignmentStrategy newAlignment) {
        this.customizedAlignment = Optional.of(newAlignment);
        this.updateAlignment();

        return this;
    }

    public AlignmentStrategy relativeAlignment(AlignedToCorner alignment) {
        return new RelativeAlignment(this.customizedAlignment().orElse(this.screen.alignmentMethod()), alignment.offset,
                this.widget);
    }

    public void onWindowUpdate(Size window) {
        this.widget.onWindowUpdate(window);

        this.updateAlignment();
    }

    private Optional<AlignmentStrategy> customizedAlignment() {
        if (this.customizedAlignment.isPresent()) {
            return this.customizedAlignment;
        } else {
            return this.widget.alignmentMethod();
        }
    }

    private void updateAlignment() {
        Size window = this.screen.window();
        this.aligner = this.customizedAlignment().map(alignment -> new PointManager(alignment, window));
    }

    public static enum AlignedToCorner {
        TOP_LEFT(widget -> widget.pos()),
        TOP_RIGHT(widget -> widget.pos().offset(widget.size().width(), 0)),
        BOTTOM_LEFT(widget -> widget.pos().offset(0, widget.size().height())),
        BOTTOM_RIGHT(widget -> widget.pos().offset(widget.size())),
        ;

        private final Function<Widget, Point> offset;

        private AlignedToCorner(Function<Widget, Point> offset) {
            this.offset = offset;
        }

    }
}
