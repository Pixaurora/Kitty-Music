package net.pixaurora.catculator.impl.http;

import net.pixaurora.catculator.api.http.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ResponseImpl implements Response {
    private final int status;
    private final byte[] body;
    private final Map<String, String> headers;

    private ResponseImpl(int status, byte[] body, Map<String, String> headers) {
        this.status = status;
        this.body = body;
        this.headers = headers;
    }

    @Override
    public int status() {
        return this.status;
    }

    @Override
    public byte[] body() {
        return this.body;
    }

    @Override
    public @Nullable String header(@NotNull String name) {
        return this.headers.get(name);
    }
}
