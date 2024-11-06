package net.pixaurora.kitten_cube.impl.ui.screen.align;

import java.util.function.Function;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public class RelativeAlignment implements AlignmentStrategy {
    private final AlignmentStrategy baseAlignment;

    private final Function<Widget, Point> offset;
    private final Widget widget;

    public RelativeAlignment(AlignmentStrategy baseAlignment, Function<Widget, Point> offset, Widget widget) {
        this.baseAlignment = baseAlignment;
        this.offset = offset;
        this.widget = widget;
    }

    @Override
    public Point align(Point original, Size window) {
        return this.baseAlignment.align(original, window).offset(this.offset.apply(this.widget));
    }

    @Override
    public Point inverseAlign(Point aligned, Size window) {
        return this.baseAlignment.inverseAlign(aligned, window).offset(this.offset.apply(this.widget).scaledBy(-1));
    }
}
