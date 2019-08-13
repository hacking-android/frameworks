/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.util.EventListener;
import javax.sql.RowSetEvent;

public interface RowSetListener
extends EventListener {
    public void cursorMoved(RowSetEvent var1);

    public void rowChanged(RowSetEvent var1);

    public void rowSetChanged(RowSetEvent var1);
}

