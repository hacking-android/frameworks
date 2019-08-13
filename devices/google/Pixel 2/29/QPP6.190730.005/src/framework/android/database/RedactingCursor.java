/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.CharArrayBuffer;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.util.SparseArray;
import java.util.Map;

public class RedactingCursor
extends CrossProcessCursorWrapper {
    private final SparseArray<Object> mRedactions;

    private RedactingCursor(Cursor cursor, SparseArray<Object> sparseArray) {
        super(cursor);
        this.mRedactions = sparseArray;
    }

    public static Cursor create(Cursor cursor, Map<String, Object> map) {
        SparseArray<Object> sparseArray = new SparseArray<Object>();
        String[] arrstring = cursor.getColumnNames();
        for (int i = 0; i < arrstring.length; ++i) {
            if (!map.containsKey(arrstring[i])) continue;
            sparseArray.put(i, map.get(arrstring[i]));
        }
        if (sparseArray.size() == 0) {
            return cursor;
        }
        return new RedactingCursor(cursor, sparseArray);
    }

    @Override
    public void copyStringToBuffer(int n, CharArrayBuffer charArrayBuffer) {
        int n2 = this.mRedactions.indexOfKey(n);
        if (n2 >= 0) {
            charArrayBuffer.data = ((String)this.mRedactions.valueAt(n2)).toCharArray();
            charArrayBuffer.sizeCopied = charArrayBuffer.data.length;
        } else {
            super.copyStringToBuffer(n, charArrayBuffer);
        }
    }

    @Override
    public void fillWindow(int n, CursorWindow cursorWindow) {
        DatabaseUtils.cursorFillWindow(this, n, cursorWindow);
    }

    @Override
    public byte[] getBlob(int n) {
        int n2 = this.mRedactions.indexOfKey(n);
        if (n2 >= 0) {
            return (byte[])this.mRedactions.valueAt(n2);
        }
        return super.getBlob(n);
    }

    @Override
    public double getDouble(int n) {
        int n2 = this.mRedactions.indexOfKey(n);
        if (n2 >= 0) {
            return (Double)this.mRedactions.valueAt(n2);
        }
        return super.getDouble(n);
    }

    @Override
    public float getFloat(int n) {
        int n2 = this.mRedactions.indexOfKey(n);
        if (n2 >= 0) {
            return ((Float)this.mRedactions.valueAt(n2)).floatValue();
        }
        return super.getFloat(n);
    }

    @Override
    public int getInt(int n) {
        int n2 = this.mRedactions.indexOfKey(n);
        if (n2 >= 0) {
            return (Integer)this.mRedactions.valueAt(n2);
        }
        return super.getInt(n);
    }

    @Override
    public long getLong(int n) {
        int n2 = this.mRedactions.indexOfKey(n);
        if (n2 >= 0) {
            return (Long)this.mRedactions.valueAt(n2);
        }
        return super.getLong(n);
    }

    @Override
    public short getShort(int n) {
        int n2 = this.mRedactions.indexOfKey(n);
        if (n2 >= 0) {
            return (Short)this.mRedactions.valueAt(n2);
        }
        return super.getShort(n);
    }

    @Override
    public String getString(int n) {
        int n2 = this.mRedactions.indexOfKey(n);
        if (n2 >= 0) {
            return (String)this.mRedactions.valueAt(n2);
        }
        return super.getString(n);
    }

    @Override
    public int getType(int n) {
        int n2 = this.mRedactions.indexOfKey(n);
        if (n2 >= 0) {
            return DatabaseUtils.getTypeOfObject(this.mRedactions.valueAt(n2));
        }
        return super.getType(n);
    }

    @Override
    public CursorWindow getWindow() {
        return null;
    }

    @Override
    public Cursor getWrappedCursor() {
        throw new UnsupportedOperationException("Returning underlying cursor risks leaking redacted data");
    }

    @Override
    public boolean isNull(int n) {
        int n2 = this.mRedactions.indexOfKey(n);
        if (n2 >= 0) {
            boolean bl = this.mRedactions.valueAt(n2) == null;
            return bl;
        }
        return super.isNull(n);
    }
}

