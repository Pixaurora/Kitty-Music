package net.pixaurora.kitten_square.impl;

import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_square.impl.service.UICompatImpl;

public class FakeComponent implements Component {
    private final String text;

    public FakeComponent(String text) {
        this.text = text;
    }

    public String text() {
        return this.text;
    }

    @Override
    public Component concat(Component component) {
        return new FakeComponent(this.text + UICompatImpl.internalToMinecraftType(component));
    }
}
