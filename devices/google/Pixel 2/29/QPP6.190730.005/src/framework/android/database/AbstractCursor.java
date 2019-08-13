/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.CrossProcessCursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCursor
implements CrossProcessCursor {
    private static final String TAG = "Cursor";
    @Deprecated
    protected boolean mClosed;
    private final ContentObservable mContentObservable = new ContentObservable();
    @Deprecated
    protected ContentResolver mContentResolver;
    protected Long mCurrentRowID;
    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Bundle mExtras = Bundle.EMPTY;
    @UnsupportedAppUsage
    private Uri mNotifyUri;
    private List<Uri> mNotifyUris;
    @Deprecated
    protected int mPos = -1;
    protected int mRowIdColumnIndex;
    private ContentObserver mSelfObserver;
    private final Object mSelfObserverLock = new Object();
    private boolean mSelfObserverRegistered;
    protected HashMap<Long, Map<String, Object>> mUpdatedRows;

    protected void checkPosition() {
        if (-1 != this.mPos && this.getCount() != this.mPos) {
            return;
        }
        throw new CursorIndexOutOfBoundsException(this.mPos, this.getCount());
    }

    @Override
    public void close() {
        this.mClosed = true;
        this.mContentObservable.unregisterAll();
        this.onDeactivateOrClose();
    }

    @Override
    public void copyStringToBuffer(int n, CharArrayBuffer charArrayBuffer) {
        String string2 = this.getString(n);
        if (string2 != null) {
            char[] arrc = charArrayBuffer.data;
            if (arrc != null && arrc.length >= string2.length()) {
                string2.getChars(0, string2.length(), arrc, 0);
            } else {
                charArrayBuffer.data = string2.toCharArray();
            }
            charArrayBuffer.sizeCopied = string2.length();
        } else {
            charArrayBuffer.sizeCopied = 0;
        }
    }

    @Override
    public void deactivate() {
        this.onDeactivateOrClose();
    }

    @Override
    public void fillWindow(int n, CursorWindow cursorWindow) {
        DatabaseUtils.cursorFillWindow(this, n, cursorWindow);
    }

    protected void finalize() {
        ContentObserver contentObserver = this.mSelfObserver;
        if (contentObserver != null && this.mSelfObserverRegistered) {
            this.mContentResolver.unregisterContentObserver(contentObserver);
        }
        try {
            if (!this.mClosed) {
                this.close();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public byte[] getBlob(int n) {
        throw new UnsupportedOperationException("getBlob is not supported");
    }

    @Override
    public int getColumnCount() {
        return this.getColumnNames().length;
    }

    @Override
    public int getColumnIndex(String arrstring) {
        int n = arrstring.lastIndexOf(46);
        Object object = arrstring;
        if (n != -1) {
            object = new Exception();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("requesting column name with table name -- ");
            stringBuilder.append((String)arrstring);
            Log.e(TAG, stringBuilder.toString(), (Throwable)object);
            object = arrstring.substring(n + 1);
        }
        arrstring = this.getColumnNames();
        int n2 = arrstring.length;
        for (n = 0; n < n2; ++n) {
            if (!arrstring[n].equalsIgnoreCase((String)object)) continue;
            return n;
        }
        return -1;
    }

    @Override
    public int getColumnIndexOrThrow(String string2) {
        CharSequence charSequence;
        int n = this.getColumnIndex(string2);
        if (n >= 0) {
            return n;
        }
        String string3 = "";
        try {
            charSequence = Arrays.toString(this.getColumnNames());
            string3 = charSequence;
        }
        catch (Exception exception) {
            Log.d(TAG, "Cannot collect column names for debug purposes", exception);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("column '");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("' does not exist. Available columns: ");
        ((StringBuilder)charSequence).append(string3);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Override
    public String getColumnName(int n) {
        return this.getColumnNames()[n];
    }

    @Override
    public abstract String[] getColumnNames();

    @Override
    public abstract int getCount();

    @Override
    public abstract double getDouble(int var1);

    @Override
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override
    public abstract float getFloat(int var1);

    @Override
    public abstract int getInt(int var1);

    @Override
    public abstract long getLong(int var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Uri getNotificationUri() {
        Object object = this.mSelfObserverLock;
        synchronized (object) {
            return this.mNotifyUri;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<Uri> getNotificationUris() {
        Object object = this.mSelfObserverLock;
        synchronized (object) {
            return this.mNotifyUris;
        }
    }

    @Override
    public final int getPosition() {
        return this.mPos;
    }

    @Override
    public abstract short getShort(int var1);

    @Override
    public abstract String getString(int var1);

    @Override
    public int getType(int n) {
        return 3;
    }

    @Deprecated
    protected Object getUpdatedField(int n) {
        return null;
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        return false;
    }

    @Override
    public CursorWindow getWindow() {
        return null;
    }

    @Override
    public final boolean isAfterLast() {
        int n = this.getCount();
        boolean bl = true;
        if (n == 0) {
            return true;
        }
        if (this.mPos != this.getCount()) {
            bl = false;
        }
        return bl;
    }

    @Override
    public final boolean isBeforeFirst() {
        int n = this.getCount();
        boolean bl = true;
        if (n == 0) {
            return true;
        }
        if (this.mPos != -1) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean isClosed() {
        return this.mClosed;
    }

    @Deprecated
    protected boolean isFieldUpdated(int n) {
        return false;
    }

    @Override
    public final boolean isFirst() {
        boolean bl = this.mPos == 0 && this.getCount() != 0;
        return bl;
    }

    @Override
    public final boolean isLast() {
        int n = this.getCount();
        boolean bl = this.mPos == n - 1 && n != 0;
        return bl;
    }

    @Override
    public abstract boolean isNull(int var1);

    @Override
    public final boolean move(int n) {
        return this.moveToPosition(this.mPos + n);
    }

    @Override
    public final boolean moveToFirst() {
        return this.moveToPosition(0);
    }

    @Override
    public final boolean moveToLast() {
        return this.moveToPosition(this.getCount() - 1);
    }

    @Override
    public final boolean moveToNext() {
        return this.moveToPosition(this.mPos + 1);
    }

    @Override
    public final boolean moveToPosition(int n) {
        int n2 = this.getCount();
        if (n >= n2) {
            this.mPos = n2;
            return false;
        }
        if (n < 0) {
            this.mPos = -1;
            return false;
        }
        n2 = this.mPos;
        if (n == n2) {
            return true;
        }
        boolean bl = this.onMove(n2, n);
        this.mPos = !bl ? -1 : n;
        return bl;
    }

    @Override
    public final boolean moveToPrevious() {
        return this.moveToPosition(this.mPos - 1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void onChange(boolean bl) {
        Object object = this.mSelfObserverLock;
        synchronized (object) {
            this.mContentObservable.dispatchChange(bl, null);
            if (this.mNotifyUris != null && bl) {
                int n = this.mNotifyUris.size();
                for (int i = 0; i < n; ++i) {
                    Uri uri = this.mNotifyUris.get(i);
                    this.mContentResolver.notifyChange(uri, this.mSelfObserver);
                }
            }
            return;
        }
    }

    protected void onDeactivateOrClose() {
        ContentObserver contentObserver = this.mSelfObserver;
        if (contentObserver != null) {
            this.mContentResolver.unregisterContentObserver(contentObserver);
            this.mSelfObserverRegistered = false;
        }
        this.mDataSetObservable.notifyInvalidated();
    }

    @Override
    public boolean onMove(int n, int n2) {
        return true;
    }

    @Override
    public void registerContentObserver(ContentObserver contentObserver) {
        this.mContentObservable.registerObserver(contentObserver);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.registerObserver(dataSetObserver);
    }

    @Override
    public boolean requery() {
        if (this.mSelfObserver != null && !this.mSelfObserverRegistered) {
            int n = this.mNotifyUris.size();
            for (int i = 0; i < n; ++i) {
                Uri uri = this.mNotifyUris.get(i);
                this.mContentResolver.registerContentObserver(uri, true, this.mSelfObserver);
            }
            this.mSelfObserverRegistered = true;
        }
        this.mDataSetObservable.notifyChanged();
        return true;
    }

    @Override
    public Bundle respond(Bundle bundle) {
        return Bundle.EMPTY;
    }

    @Override
    public void setExtras(Bundle bundle) {
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        this.mExtras = bundle;
    }

    @Override
    public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
        this.setNotificationUris(contentResolver, Arrays.asList(uri));
    }

    @Override
    public void setNotificationUris(ContentResolver contentResolver, List<Uri> list) {
        Preconditions.checkNotNull(contentResolver);
        Preconditions.checkNotNull(list);
        this.setNotificationUris(contentResolver, list, contentResolver.getUserId(), true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setNotificationUris(ContentResolver object, List<Uri> list, int n, boolean bl) {
        Object object2 = this.mSelfObserverLock;
        synchronized (object2) {
            this.mNotifyUris = list;
            this.mNotifyUri = this.mNotifyUris.get(0);
            this.mContentResolver = object;
            if (this.mSelfObserver != null) {
                this.mContentResolver.unregisterContentObserver(this.mSelfObserver);
                this.mSelfObserverRegistered = false;
            }
            if (bl) {
                this.mSelfObserver = object = new SelfContentObserver(this);
                int n2 = this.mNotifyUris.size();
                for (int i = 0; i < n2; ++i) {
                    object = this.mNotifyUris.get(i);
                    this.mContentResolver.registerContentObserver((Uri)object, true, this.mSelfObserver, n);
                }
                this.mSelfObserverRegistered = true;
            }
            return;
        }
    }

    @Override
    public void unregisterContentObserver(ContentObserver contentObserver) {
        if (!this.mClosed) {
            this.mContentObservable.unregisterObserver(contentObserver);
        }
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.unregisterObserver(dataSetObserver);
    }

    protected static class SelfContentObserver
    extends ContentObserver {
        WeakReference<AbstractCursor> mCursor;

        public SelfContentObserver(AbstractCursor abstractCursor) {
            super(null);
            this.mCursor = new WeakReference<AbstractCursor>(abstractCursor);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return false;
        }

        @Override
        public void onChange(boolean bl) {
            AbstractCursor abstractCursor = (AbstractCursor)this.mCursor.get();
            if (abstractCursor != null) {
                abstractCursor.onChange(false);
            }
        }
    }

}

