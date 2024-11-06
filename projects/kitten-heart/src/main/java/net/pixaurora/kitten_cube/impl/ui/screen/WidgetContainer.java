package net.pixaurora.kitten_cube.impl.ui.screen;

import java.util.Optional;
import java.util.function.BiFunction;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.AlignmentStrategy;
import net.pixaurora.kitten_cube.impl.ui.screen.align.PointManager;
import net.pixaurora.kitten_cube.impl.ui.screen.align.RelativeAlignment;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public class WidgetContainer<T extends Widget> {
    private final T widget;
    private Optional<Size> window;
    private Optional<AlignmentStrategy> customizedAlignment;
    private Optional<PointManager> aligner;

    public WidgetContainer(T widget) {
        this.widget = widget;
        this.window = Optional.empty();
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
        return new RelativeAlignment(this.customizedAlignment.orElse(Alignment.TOP_LEFT),
                alignment.offset.apply(this.widget.pos(), this.widget.size()));
    }

    public void onWindowUpdate(Size window) {
        this.window = Optional.of(window);
        this.widget.onWindowUpdate(window);

        this.updateAlignment();
    }

    private void updateAlignment() {
        Size window = this.window.orElseThrow(RuntimeException::new);

        Optional<AlignmentStrategy> alignment = this.customizedAlignment.isPresent() ? this.customizedAlignment
                : this.widget.alignmentMethod();
        this.aligner = alignment.map(alignment0 -> new PointManager(alignment0, window));
    }

    public static enum AlignedToCorner {
        TOP_LEFT((widgetPos, widgetSize) -> widgetPos),
        TOP_RIGHT((widgetPos, widgetSize) -> widgetPos.offset(widgetSize.x(), 0)),
        BOTTOM_LEFT((widgetPos, widgetSize) -> widgetPos.offset(0, widgetSize.y())),
        BOTTOM_RIGHT((widgetPos, widgetSize) -> widgetPos.offset(widgetSize)),
        ;

        private final BiFunction<Point, Size, Point> offset;

        private AlignedToCorner(BiFunction<Point, Size, Point> offset) {
            this.offset = offset;
        }

    }
}
