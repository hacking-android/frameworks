/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class ContentQueryMap
extends Observable {
    private String[] mColumnNames;
    private ContentObserver mContentObserver;
    private volatile Cursor mCursor;
    private boolean mDirty = false;
    private Handler mHandlerForUpdateNotifications = null;
    private boolean mKeepUpdated = false;
    private int mKeyColumn;
    private Map<String, ContentValues> mValues = null;

    public ContentQueryMap(Cursor cursor, String string2, boolean bl, Handler handler) {
        this.mCursor = cursor;
        this.mColumnNames = this.mCursor.getColumnNames();
        this.mKeyColumn = this.mCursor.getColumnIndexOrThrow(string2);
        this.mHandlerForUpdateNotifications = handler;
        this.setKeepUpdated(bl);
        if (!bl) {
            this.readCursorIntoCache(cursor);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readCursorIntoCache(Cursor cursor) {
        synchronized (this) {
            int n = this.mValues != null ? this.mValues.size() : 0;
            Object object = new HashMap(n);
            this.mValues = object;
            while (cursor.moveToNext()) {
                object = new ContentValues();
                for (n = 0; n < this.mColumnNames.length; ++n) {
                    if (n == this.mKeyColumn) continue;
                    ((ContentValues)object).put(this.mColumnNames[n], cursor.getString(n));
                }
                this.mValues.put(cursor.getString(this.mKeyColumn), (ContentValues)object);
            }
            return;
        }
    }

    public void close() {
        synchronized (this) {
            if (this.mContentObserver != null) {
                this.mCursor.unregisterContentObserver(this.mContentObserver);
                this.mContentObserver = null;
            }
            this.mCursor.close();
            this.mCursor = null;
            return;
        }
    }

    protected void finalize() throws Throwable {
        if (this.mCursor != null) {
            this.close();
        }
        Object.super.finalize();
    }

    public Map<String, ContentValues> getRows() {
        synchronized (this) {
            if (this.mDirty) {
                this.requery();
            }
            Map<String, ContentValues> map = this.mValues;
            return map;
        }
    }

    public ContentValues getValues(String object) {
        synchronized (this) {
            if (this.mDirty) {
                this.requery();
            }
            object = this.mValues.get(object);
            return object;
        }
    }

    public void requery() {
        Cursor cursor = this.mCursor;
        if (cursor == null) {
            return;
        }
        this.mDirty = false;
        if (!cursor.requery()) {
            return;
        }
        this.readCursorIntoCache(cursor);
        this.setChanged();
        this.notifyObservers();
    }

    public void setKeepUpdated(boolean bl) {
        if (bl == this.mKeepUpdated) {
            return;
        }
        this.mKeepUpdated = bl;
        if (!this.mKeepUpdated) {
            this.mCursor.unregisterContentObserver(this.mContentObserver);
            this.mContentObserver = null;
        } else {
            if (this.mHandlerForUpdateNotifications == null) {
                this.mHandlerForUpdateNotifications = new Handler();
            }
            if (this.mContentObserver == null) {
                this.mContentObserver = new ContentObserver(this.mHandlerForUpdateNotifications){

                    @Override
                    public void onChange(boolean bl) {
                        if (ContentQueryMap.this.countObservers() != 0) {
                            ContentQueryMap.this.requery();
                        } else {
                            ContentQueryMap.this.mDirty = true;
                        }
                    }
                };
            }
            this.mCursor.registerContentObserver(this.mContentObserver);
            this.mDirty = true;
        }
    }

}

