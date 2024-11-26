package net.pixaurora.kitten_cube.impl.ui.screen;

import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.RelativeAlignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.WidgetAnchor;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public class WidgetContainer<T extends Widget> {
    private final T widget;
    private final ScreenTemplate screen;

    private Optional<Alignment> customizedAlignment;

    public WidgetContainer(T widget, ScreenTemplate screen) {
        this.widget = widget;
        this.screen = screen;
        this.customizedAlignment = Optional.empty();
    }

    public T get() {
        return this.widget;
    }

    public WidgetContainer<T> customizedAlignment(Alignment newAlignment) {
        this.customizedAlignment = Optional.of(newAlignment);

        return this;
    }

    public Alignment relativeAlignment(WidgetAnchor anchor) {
        return new RelativeAlignment(this.customizedAlignment().orElseGet(this.screen::alignmentMethod), this.widget,
                anchor);
    }

    public void onWindowUpdate(Size window) {
        this.widget.onWindowUpdate(window);
    }

    public Optional<Alignment> customizedAlignment() {
        if (this.customizedAlignment.isPresent()) {
            return this.customizedAlignment;
        } else {
            return this.widget.alignmentMethod();
        }
    }
}
