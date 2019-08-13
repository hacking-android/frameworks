/*
 * Decompiled with CFR 0.145.
 */
package javax.sql;

import java.util.EventObject;
import javax.sql.RowSet;

public class RowSetEvent
extends EventObject {
    static final long serialVersionUID = -1875450876546332005L;

    public RowSetEvent(RowSet rowSet) {
        super(rowSet);
    }
}

