/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;

public class CrossProcessCursorWrapper
extends CursorWrapper
implements CrossProcessCursor {
    public CrossProcessCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @Override
    public void fillWindow(int n, CursorWindow cursorWindow) {
        if (this.mCursor instanceof CrossProcessCursor) {
            ((CrossProcessCursor)this.mCursor).fillWindow(n, cursorWindow);
            return;
        }
        DatabaseUtils.cursorFillWindow(this.mCursor, n, cursorWindow);
    }

    @Override
    public CursorWindow getWindow() {
        if (this.mCursor instanceof CrossProcessCursor) {
            return ((CrossProcessCursor)this.mCursor).getWindow();
        }
        return null;
    }

    @Override
    public boolean onMove(int n, int n2) {
        if (this.mCursor instanceof CrossProcessCursor) {
            return ((CrossProcessCursor)this.mCursor).onMove(n, n2);
        }
        return true;
    }
}

