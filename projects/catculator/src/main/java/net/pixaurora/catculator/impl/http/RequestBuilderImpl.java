package net.pixaurora.catculator.impl.http;

import net.pixaurora.catculator.api.http.RequestBuilder;
import net.pixaurora.catculator.api.http.Response;
import org.jetbrains.annotations.NotNull;

public class RequestBuilderImpl implements RequestBuilder {
    private long ptr;

    private RequestBuilderImpl(long ptr) {
        this.ptr = ptr;
    }

    @Override
    public @NotNull Response send() {
        return this.send0();
    }

    @Override
    public @NotNull RequestBuilder body(byte[] data) {
        this.body0(data);
        return this;
    }

    @Override
    public @NotNull RequestBuilder query(String key, String value) {
        this.query0(key, value);
        return this;
    }

    @Override
    public @NotNull RequestBuilder header(String key, String value) {
        this.header0(key, value);
        return this;
    }

    private native @NotNull Response send0();

    private native void body0(byte[] data);
    private native void query0(String key, String value);
    private native void header0(String key, String value);
}
