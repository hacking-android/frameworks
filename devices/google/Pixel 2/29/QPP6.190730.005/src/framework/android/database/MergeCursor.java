/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.AbstractCursor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;

public class MergeCursor
extends AbstractCursor {
    private Cursor mCursor;
    private Cursor[] mCursors;
    private DataSetObserver mObserver = new DataSetObserver(){

        @Override
        public void onChanged() {
            MergeCursor.this.mPos = -1;
        }

        @Override
        public void onInvalidated() {
            MergeCursor.this.mPos = -1;
        }
    };

    public MergeCursor(Cursor[] arrcursor) {
        this.mCursors = arrcursor;
        this.mCursor = arrcursor[0];
        for (int i = 0; i < (arrcursor = this.mCursors).length; ++i) {
            if (arrcursor[i] == null) continue;
            arrcursor[i].registerDataSetObserver(this.mObserver);
        }
    }

    @Override
    public void close() {
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            Cursor[] arrcursor = this.mCursors;
            if (arrcursor[i] == null) continue;
            arrcursor[i].close();
        }
        super.close();
    }

    @Override
    public void deactivate() {
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            Cursor[] arrcursor = this.mCursors;
            if (arrcursor[i] == null) continue;
            arrcursor[i].deactivate();
        }
        super.deactivate();
    }

    @Override
    public byte[] getBlob(int n) {
        return this.mCursor.getBlob(n);
    }

    @Override
    public String[] getColumnNames() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            return cursor.getColumnNames();
        }
        return new String[0];
    }

    @Override
    public int getCount() {
        int n = 0;
        int n2 = this.mCursors.length;
        for (int i = 0; i < n2; ++i) {
            Cursor[] arrcursor = this.mCursors;
            int n3 = n;
            if (arrcursor[i] != null) {
                n3 = n + arrcursor[i].getCount();
            }
            n = n3;
        }
        return n;
    }

    @Override
    public double getDouble(int n) {
        return this.mCursor.getDouble(n);
    }

    @Override
    public float getFloat(int n) {
        return this.mCursor.getFloat(n);
    }

    @Override
    public int getInt(int n) {
        return this.mCursor.getInt(n);
    }

    @Override
    public long getLong(int n) {
        return this.mCursor.getLong(n);
    }

    @Override
    public short getShort(int n) {
        return this.mCursor.getShort(n);
    }

    @Override
    public String getString(int n) {
        return this.mCursor.getString(n);
    }

    @Override
    public int getType(int n) {
        return this.mCursor.getType(n);
    }

    @Override
    public boolean isNull(int n) {
        return this.mCursor.isNull(n);
    }

    @Override
    public boolean onMove(int n, int n2) {
        Object object;
        this.mCursor = null;
        int n3 = 0;
        int n4 = this.mCursors.length;
        for (n = 0; n < n4; ++n) {
            object = this.mCursors;
            if (object[n] == null) continue;
            if (n2 < object[n].getCount() + n3) {
                this.mCursor = this.mCursors[n];
                break;
            }
            n3 += this.mCursors[n].getCount();
        }
        if ((object = this.mCursor) != null) {
            return object.moveToPosition(n2 - n3);
        }
        return false;
    }

    @Override
    public void registerContentObserver(ContentObserver contentObserver) {
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            Cursor[] arrcursor = this.mCursors;
            if (arrcursor[i] == null) continue;
            arrcursor[i].registerContentObserver(contentObserver);
        }
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            Cursor[] arrcursor = this.mCursors;
            if (arrcursor[i] == null) continue;
            arrcursor[i].registerDataSetObserver(dataSetObserver);
        }
    }

    @Override
    public boolean requery() {
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            Cursor[] arrcursor = this.mCursors;
            if (arrcursor[i] == null || arrcursor[i].requery()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void unregisterContentObserver(ContentObserver contentObserver) {
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            Cursor[] arrcursor = this.mCursors;
            if (arrcursor[i] == null) continue;
            arrcursor[i].unregisterContentObserver(contentObserver);
        }
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            Cursor[] arrcursor = this.mCursors;
            if (arrcursor[i] == null) continue;
            arrcursor[i].unregisterDataSetObserver(dataSetObserver);
        }
    }

}

