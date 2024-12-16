package net.pixaurora.kitten_cube.impl.ui.screen.align;

import net.pixaurora.kitten_cube.impl.math.Size;

public class BasicAlignments {
    public static class TopLeftAlignment implements WindowOffsetAlignment {
        @Override
        public int offsetX(Size window) {
            return 0;
        }

        @Override
        public int offsetY(Size window) {
            return 0;
        }
    }

    public static class TopCenterAlignment implements WindowOffsetAlignment {
        @Override
        public int offsetX(Size window) {
            return window.width() / 2;
        }

        @Override
        public int offsetY(Size window) {
            return 0;
        }
    }

    public static class TrueCenterAlignment implements WindowOffsetAlignment {
        @Override
        public int offsetX(Size window) {
            return window.width() / 2;
        }

        @Override
        public int offsetY(Size window) {
            return window.height() / 2;
        }
    }

    public static class BottomCenterAlignment implements WindowOffsetAlignment {
        @Override
        public int offsetX(Size window) {
            return window.width() / 2;
        }

        @Override
        public int offsetY(Size window) {
            return window.height();
        }
    }
}
