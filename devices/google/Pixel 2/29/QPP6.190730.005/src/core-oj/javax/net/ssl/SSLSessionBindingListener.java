/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.util.EventListener;
import javax.net.ssl.SSLSessionBindingEvent;

public interface SSLSessionBindingListener
extends EventListener {
    public void valueBound(SSLSessionBindingEvent var1);

    public void valueUnbound(SSLSessionBindingEvent var1);
}

