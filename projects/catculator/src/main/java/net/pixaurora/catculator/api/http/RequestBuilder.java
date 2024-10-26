package net.pixaurora.catculator.api.http;

import net.pixaurora.catculator.api.error.ClientResponseException;
import org.jetbrains.annotations.NotNull;

public interface RequestBuilder {
    @NotNull Response send() throws ClientResponseException;

    @NotNull RequestBuilder body(byte[] data);
    @NotNull RequestBuilder query(String key, String value);
    @NotNull RequestBuilder header(String key, String value);
}
