/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import java.io.Closeable;
import java.util.Arrays;
import java.util.List;

public interface Cursor
extends Closeable {
    public static final int FIELD_TYPE_BLOB = 4;
    public static final int FIELD_TYPE_FLOAT = 2;
    public static final int FIELD_TYPE_INTEGER = 1;
    public static final int FIELD_TYPE_NULL = 0;
    public static final int FIELD_TYPE_STRING = 3;

    @Override
    public void close();

    public void copyStringToBuffer(int var1, CharArrayBuffer var2);

    @Deprecated
    public void deactivate();

    public byte[] getBlob(int var1);

    public int getColumnCount();

    public int getColumnIndex(String var1);

    public int getColumnIndexOrThrow(String var1) throws IllegalArgumentException;

    public String getColumnName(int var1);

    public String[] getColumnNames();

    public int getCount();

    public double getDouble(int var1);

    public Bundle getExtras();

    public float getFloat(int var1);

    public int getInt(int var1);

    public long getLong(int var1);

    public Uri getNotificationUri();

    default public List<Uri> getNotificationUris() {
        Object object = this.getNotificationUri();
        object = object == null ? null : Arrays.asList(object);
        return object;
    }

    public int getPosition();

    public short getShort(int var1);

    public String getString(int var1);

    public int getType(int var1);

    public boolean getWantsAllOnMoveCalls();

    public boolean isAfterLast();

    public boolean isBeforeFirst();

    public boolean isClosed();

    public boolean isFirst();

    public boolean isLast();

    public boolean isNull(int var1);

    public boolean move(int var1);

    public boolean moveToFirst();

    public boolean moveToLast();

    public boolean moveToNext();

    public boolean moveToPosition(int var1);

    public boolean moveToPrevious();

    public void registerContentObserver(ContentObserver var1);

    public void registerDataSetObserver(DataSetObserver var1);

    @Deprecated
    public boolean requery();

    public Bundle respond(Bundle var1);

    public void setExtras(Bundle var1);

    public void setNotificationUri(ContentResolver var1, Uri var2);

    default public void setNotificationUris(ContentResolver contentResolver, List<Uri> list) {
        this.setNotificationUri(contentResolver, list.get(0));
    }

    public void unregisterContentObserver(ContentObserver var1);

    public void unregisterDataSetObserver(DataSetObserver var1);
}

