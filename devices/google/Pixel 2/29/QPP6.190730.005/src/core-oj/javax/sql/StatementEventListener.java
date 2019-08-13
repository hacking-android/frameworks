/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.util.EventListener;
import javax.sql.StatementEvent;

public interface StatementEventListener
extends EventListener {
    public void statementClosed(StatementEvent var1);

    public void statementErrorOccurred(StatementEvent var1);
}

