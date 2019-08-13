/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.database;

import android.annotation.UnsupportedAppUsage;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.util.Log;

public class SortCursor
extends AbstractCursor {
    private static final String TAG = "SortCursor";
    private final int ROWCACHESIZE;
    private int[][] mCurRowNumCache;
    @UnsupportedAppUsage
    private Cursor mCursor;
    private int[] mCursorCache = new int[64];
    @UnsupportedAppUsage
    private Cursor[] mCursors;
    private int mLastCacheHit = -1;
    private DataSetObserver mObserver = new DataSetObserver(){

        @Override
        public void onChanged() {
            SortCursor.this.mPos = -1;
        }

        @Override
        public void onInvalidated() {
            SortCursor.this.mPos = -1;
        }
    };
    private int[] mRowNumCache = new int[64];
    private int[] mSortColumns;

    @UnsupportedAppUsage
    public SortCursor(Cursor[] object, String object2) {
        int n;
        this.ROWCACHESIZE = 64;
        this.mCursors = object;
        int n2 = this.mCursors.length;
        this.mSortColumns = new int[n2];
        for (n = 0; n < n2; ++n) {
            object = this.mCursors;
            if (object[n] == null) continue;
            object[n].registerDataSetObserver(this.mObserver);
            this.mCursors[n].moveToFirst();
            this.mSortColumns[n] = this.mCursors[n].getColumnIndexOrThrow((String)object2);
        }
        this.mCursor = null;
        object2 = "";
        for (n = 0; n < n2; ++n) {
            block5 : {
                Object object3;
                block7 : {
                    block6 : {
                        object3 = this.mCursors;
                        object = object2;
                        if (object3[n] == null) break block5;
                        if (!object3[n].isAfterLast()) break block6;
                        object = object2;
                        break block5;
                    }
                    object3 = this.mCursors[n].getString(this.mSortColumns[n]);
                    if (this.mCursor == null) break block7;
                    object = object2;
                    if (((String)object3).compareToIgnoreCase((String)object2) >= 0) break block5;
                }
                object = object3;
                this.mCursor = this.mCursors[n];
            }
            object2 = object;
        }
        for (n = this.mRowNumCache.length - 1; n >= 0; --n) {
            this.mRowNumCache[n] = -2;
        }
        this.mCurRowNumCache = new int[64][n2];
    }

    @Override
    public void close() {
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            Cursor[] arrcursor = this.mCursors;
            if (arrcursor[i] == null) continue;
            arrcursor[i].close();
        }
    }

    @Override
    public void deactivate() {
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            Cursor[] arrcursor = this.mCursors;
            if (arrcursor[i] == null) continue;
            arrcursor[i].deactivate();
        }
    }

    @Override
    public byte[] getBlob(int n) {
        return this.mCursor.getBlob(n);
    }

    @Override
    public String[] getColumnNames() {
        Cursor[] arrcursor = this.mCursor;
        if (arrcursor != null) {
            return arrcursor.getColumnNames();
        }
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            arrcursor = this.mCursors;
            if (arrcursor[i] == null) continue;
            return arrcursor[i].getColumnNames();
        }
        throw new IllegalStateException("No cursor that can return names");
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
        int n3;
        Object object;
        int n4;
        block18 : {
            block17 : {
                if (n == n2) {
                    return true;
                }
                int n5 = n2 % 64;
                if (this.mRowNumCache[n5] == n2) {
                    n = this.mCursorCache[n5];
                    this.mCursor = this.mCursors[n];
                    Cursor cursor = this.mCursor;
                    if (cursor == null) {
                        Log.w(TAG, "onMove: cache results in a null cursor.");
                        return false;
                    }
                    cursor.moveToPosition(this.mCurRowNumCache[n5][n]);
                    this.mLastCacheHit = n5;
                    return true;
                }
                this.mCursor = null;
                n3 = this.mCursors.length;
                if (this.mLastCacheHit >= 0) {
                    for (n4 = 0; n4 < n3; ++n4) {
                        object = this.mCursors;
                        if (object[n4] == null) continue;
                        object[n4].moveToPosition(this.mCurRowNumCache[this.mLastCacheHit][n4]);
                    }
                }
                if (n2 < n) break block17;
                n4 = n;
                if (n != -1) break block18;
            }
            for (n = 0; n < n3; ++n) {
                object = this.mCursors;
                if (object[n] == null) continue;
                object[n].moveToFirst();
            }
            n4 = 0;
        }
        n = n4;
        if (n4 < 0) {
            n = 0;
        }
        n4 = -1;
        int n6 = n;
        n = n4;
        while (n6 <= n2) {
            Object object2 = "";
            n = -1;
            for (n4 = 0; n4 < n3; ++n4) {
                int n7;
                block19 : {
                    Object object3;
                    block21 : {
                        block20 : {
                            object3 = this.mCursors;
                            n7 = n;
                            object = object2;
                            if (object3[n4] == null) break block19;
                            if (!object3[n4].isAfterLast()) break block20;
                            n7 = n;
                            object = object2;
                            break block19;
                        }
                        object3 = this.mCursors[n4].getString(this.mSortColumns[n4]);
                        if (n < 0) break block21;
                        n7 = n;
                        object = object2;
                        if (((String)object3).compareToIgnoreCase((String)object2) >= 0) break block19;
                    }
                    object = object3;
                    n7 = n4;
                }
                n = n7;
                object2 = object;
            }
            if (n6 == n2) break;
            object = this.mCursors;
            if (object[n] != null) {
                object[n].moveToNext();
            }
            ++n6;
        }
        this.mCursor = this.mCursors[n];
        this.mRowNumCache[n5] = n2;
        this.mCursorCache[n5] = n;
        for (n = 0; n < n3; ++n) {
            object = this.mCursors;
            if (object[n] == null) continue;
            this.mCurRowNumCache[n5][n] = object[n].getPosition();
        }
        this.mLastCacheHit = -1;
        return true;
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
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        int n = this.mCursors.length;
        for (int i = 0; i < n; ++i) {
            Cursor[] arrcursor = this.mCursors;
            if (arrcursor[i] == null) continue;
            arrcursor[i].unregisterDataSetObserver(dataSetObserver);
        }
    }

}

