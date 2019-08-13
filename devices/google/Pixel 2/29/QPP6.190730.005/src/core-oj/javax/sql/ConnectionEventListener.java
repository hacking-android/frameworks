/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.util.EventListener;
import javax.sql.ConnectionEvent;

public interface ConnectionEventListener
extends EventListener {
    public void connectionClosed(ConnectionEvent var1);

    public void connectionErrorOccurred(ConnectionEvent var1);
}

