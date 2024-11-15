package net.pixaurora.kitten_square.impl.ui;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.ConversionCache;
import net.pixaurora.kitten_square.impl.service.UICompatImpl;

public class ConversionCacheImpl extends ConversionCache<String, String> {
    @Override
    protected String resourceToMinecraftType(ResourcePath path) {
        return UICompatImpl.internalToMinecraftType(path);
    }

    @Override
    protected String componentToMinecraftType(
            net.pixaurora.kitten_cube.impl.text.Component component) {
        return UICompatImpl.internalToMinecraftType(component);
    }

}
