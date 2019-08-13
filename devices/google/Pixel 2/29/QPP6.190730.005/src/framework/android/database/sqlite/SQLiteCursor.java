/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.sqlite.DatabaseObjectNotClosedException;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.StrictMode;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SQLiteCursor
extends AbstractWindowedCursor {
    static final int NO_COUNT = -1;
    static final String TAG = "SQLiteCursor";
    private Map<String, Integer> mColumnNameMap;
    private final String[] mColumns;
    private int mCount = -1;
    private int mCursorWindowCapacity;
    private final SQLiteCursorDriver mDriver;
    @UnsupportedAppUsage
    private final String mEditTable;
    private boolean mFillWindowForwardOnly;
    @UnsupportedAppUsage
    private final SQLiteQuery mQuery;
    private final Throwable mStackTrace;

    public SQLiteCursor(SQLiteCursorDriver sQLiteCursorDriver, String string2, SQLiteQuery sQLiteQuery) {
        if (sQLiteQuery != null) {
            this.mStackTrace = StrictMode.vmSqliteObjectLeaksEnabled() ? new DatabaseObjectNotClosedException().fillInStackTrace() : null;
            this.mDriver = sQLiteCursorDriver;
            this.mEditTable = string2;
            this.mColumnNameMap = null;
            this.mQuery = sQLiteQuery;
            this.mColumns = sQLiteQuery.getColumnNames();
            return;
        }
        throw new IllegalArgumentException("query object cannot be null");
    }

    @Deprecated
    public SQLiteCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String string2, SQLiteQuery sQLiteQuery) {
        this(sQLiteCursorDriver, string2, sQLiteQuery);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void fillWindow(int n) {
        this.clearOrCreateWindow(this.getDatabase().getPath());
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("requiredPos cannot be negative, but was ");
            stringBuilder.append(n);
            Preconditions.checkArgumentNonnegative(n, stringBuilder.toString());
            if (this.mCount == -1) {
                this.mCount = this.mQuery.fillWindow(this.mWindow, n, n, true);
                this.mCursorWindowCapacity = this.mWindow.getNumRows();
                if (!Log.isLoggable(TAG, 3)) return;
                stringBuilder = new StringBuilder();
                stringBuilder.append("received count(*) from native_fill_window: ");
                stringBuilder.append(this.mCount);
                Log.d(TAG, stringBuilder.toString());
                return;
            }
            int n2 = this.mFillWindowForwardOnly ? n : DatabaseUtils.cursorPickFillWindowStartPosition(n, this.mCursorWindowCapacity);
            this.mQuery.fillWindow(this.mWindow, n2, n, false);
            return;
        }
        catch (RuntimeException runtimeException) {
            this.closeWindow();
            throw runtimeException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        super.close();
        synchronized (this) {
            this.mQuery.close();
            this.mDriver.cursorClosed();
            return;
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.mDriver.cursorDeactivated();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void finalize() {
        try {
            if (this.mWindow == null) return;
            if (this.mStackTrace != null) {
                String string2 = this.mQuery.getSql();
                int n = string2.length();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Finalizing a Cursor that has not been deactivated or closed. database = ");
                stringBuilder.append(this.mQuery.getDatabase().getLabel());
                stringBuilder.append(", table = ");
                stringBuilder.append(this.mEditTable);
                stringBuilder.append(", query = ");
                int n2 = 1000;
                if (n <= 1000) {
                    n2 = n;
                }
                stringBuilder.append(string2.substring(0, n2));
                StrictMode.onSqliteObjectLeaked(stringBuilder.toString(), this.mStackTrace);
            }
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    @Override
    public int getColumnIndex(String object) {
        Serializable serializable;
        int n;
        Object object2;
        if (this.mColumnNameMap == null) {
            object2 = this.mColumns;
            int n2 = ((String[])object2).length;
            serializable = new HashMap(n2, 1.0f);
            for (n = 0; n < n2; ++n) {
                ((HashMap)serializable).put(object2[n], n);
            }
            this.mColumnNameMap = serializable;
        }
        n = ((String)object).lastIndexOf(46);
        object2 = object;
        if (n != -1) {
            serializable = new Exception();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("requesting column name with table name -- ");
            ((StringBuilder)object2).append((String)object);
            Log.e(TAG, ((StringBuilder)object2).toString(), (Throwable)serializable);
            object2 = ((String)object).substring(n + 1);
        }
        if ((object = this.mColumnNameMap.get(object2)) != null) {
            return (Integer)object;
        }
        return -1;
    }

    @Override
    public String[] getColumnNames() {
        return this.mColumns;
    }

    @Override
    public int getCount() {
        if (this.mCount == -1) {
            this.fillWindow(0);
        }
        return this.mCount;
    }

    public SQLiteDatabase getDatabase() {
        return this.mQuery.getDatabase();
    }

    @Override
    public boolean onMove(int n, int n2) {
        if (this.mWindow == null || n2 < this.mWindow.getStartPosition() || n2 >= this.mWindow.getStartPosition() + this.mWindow.getNumRows()) {
            this.fillWindow(n2);
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean requery() {
        if (this.isClosed()) {
            return false;
        }
        synchronized (this) {
            if (!this.mQuery.getDatabase().isOpen()) {
                return false;
            }
            if (this.mWindow != null) {
                this.mWindow.clear();
            }
            this.mPos = -1;
            this.mCount = -1;
            this.mDriver.cursorRequeried(this);
        }
        try {
            return super.requery();
        }
        catch (IllegalStateException illegalStateException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("requery() failed ");
            stringBuilder.append(illegalStateException.getMessage());
            Log.w(TAG, stringBuilder.toString(), illegalStateException);
            return false;
        }
    }

    public void setFillWindowForwardOnly(boolean bl) {
        this.mFillWindowForwardOnly = bl;
    }

    public void setSelectionArguments(String[] arrstring) {
        this.mDriver.setBindArguments(arrstring);
    }

    @Override
    public void setWindow(CursorWindow cursorWindow) {
        super.setWindow(cursorWindow);
        this.mCount = -1;
    }
}

