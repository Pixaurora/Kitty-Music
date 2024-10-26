package net.pixaurora.catculator.impl.http;

import net.pixaurora.catculator.api.http.Server;

public class ServerImpl implements Server {
    private final long ptr;

    public ServerImpl() {
        this.ptr = create();
    }

    private static native long create();

    @Override
    public String run() {
        return this.run0();
    }

    private native String run0();

    @Override
    public void close() {
        this.drop();
    }

    private native void drop();
}
