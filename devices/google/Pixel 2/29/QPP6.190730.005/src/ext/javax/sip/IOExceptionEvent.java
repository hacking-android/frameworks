/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import java.util.EventObject;

public class IOExceptionEvent
extends EventObject {
    private String mHost;
    private int mPort;
    private String mTransport;

    public IOExceptionEvent(Object object, String string, int n, String string2) {
        super(object);
        this.mHost = string;
        this.mPort = n;
        this.mTransport = string2;
    }

    public String getHost() {
        return this.mHost;
    }

    public int getPort() {
        return this.mPort;
    }

    public String getTransport() {
        return this.mTransport;
    }
}

