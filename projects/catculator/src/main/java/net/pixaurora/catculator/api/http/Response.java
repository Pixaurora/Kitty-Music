package net.pixaurora.catculator.api.http;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Response {
    int status();
    byte[] body();
    @Nullable String header(@NotNull String name);

    default boolean ok() {
        return this.status() >= 200 && this.status() < 300;
    }
}
