/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.Cursor;
import android.database.CursorWindow;

public interface CrossProcessCursor
extends Cursor {
    public void fillWindow(int var1, CursorWindow var2);

    public CursorWindow getWindow();

    public boolean onMove(int var1, int var2);
}

