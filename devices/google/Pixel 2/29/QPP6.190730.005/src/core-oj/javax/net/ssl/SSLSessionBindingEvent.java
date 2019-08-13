/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.util.EventObject;
import javax.net.ssl.SSLSession;

public class SSLSessionBindingEvent
extends EventObject {
    private static final long serialVersionUID = 3989172637106345L;
    private String name;

    public SSLSessionBindingEvent(SSLSession sSLSession, String string) {
        super(sSLSession);
        this.name = string;
    }

    public String getName() {
        return this.name;
    }

    public SSLSession getSession() {
        return (SSLSession)this.getSource();
    }
}

