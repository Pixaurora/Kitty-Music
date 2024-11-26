package net.pixaurora.kitten_cube.impl.ui.screen.align;

import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public interface WidgetAnchor {
    public static WidgetAnchor TOP_LEFT = new TopLeft();
    public static WidgetAnchor TOP_RIGHT = new TopRight();
    public static WidgetAnchor BOTTOM_LEFT = new BottomLeft();
    public static WidgetAnchor BOTTOM_RIGHT = new BottomRight();

    public int anchorX(Widget widget);

    public int anchorY(Widget widget);

    public static class TopLeft implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return widget.pos().x();
        }

        @Override
        public int anchorY(Widget widget) {
            return widget.pos().y();
        }
    }

    public static class TopRight implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return widget.pos().x() + widget.size().width();
        }

        @Override
        public int anchorY(Widget widget) {
            return widget.pos().y();
        }
    }

    public static class BottomLeft implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return widget.pos().x();
        }

        @Override
        public int anchorY(Widget widget) {
            return widget.pos().y() + widget.size().height();
        }
    }

    public static class BottomRight implements WidgetAnchor {
        @Override
        public int anchorX(Widget widget) {
            return widget.pos().x() + widget.size().width();
        }

        @Override
        public int anchorY(Widget widget) {
            return widget.pos().y() + widget.size().height();
        }
    }
}
