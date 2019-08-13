/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Loader.ForceLoadContentObserver
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Loader;
import android.content.Loader.ForceLoadContentObserver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

@Deprecated
public class CursorLoader
extends AsyncTaskLoader<Cursor> {
    @UnsupportedAppUsage
    CancellationSignal mCancellationSignal;
    Cursor mCursor;
    @UnsupportedAppUsage
    final ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver();
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;
    Uri mUri;

    public CursorLoader(Context context) {
        super(context);
    }

    public CursorLoader(Context context, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        super(context);
        this.mUri = uri;
        this.mProjection = arrstring;
        this.mSelection = string2;
        this.mSelectionArgs = arrstring2;
        this.mSortOrder = string3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        synchronized (this) {
            if (this.mCancellationSignal != null) {
                this.mCancellationSignal.cancel();
            }
            return;
        }
    }

    @Override
    public void deliverResult(Cursor cursor) {
        if (this.isReset()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        Cursor cursor2 = this.mCursor;
        this.mCursor = cursor;
        if (this.isStarted()) {
            super.deliverResult(cursor);
        }
        if (cursor2 != null && cursor2 != cursor && !cursor2.isClosed()) {
            cursor2.close();
        }
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string2, fileDescriptor, printWriter, arrstring);
        printWriter.print(string2);
        printWriter.print("mUri=");
        printWriter.println(this.mUri);
        printWriter.print(string2);
        printWriter.print("mProjection=");
        printWriter.println(Arrays.toString(this.mProjection));
        printWriter.print(string2);
        printWriter.print("mSelection=");
        printWriter.println(this.mSelection);
        printWriter.print(string2);
        printWriter.print("mSelectionArgs=");
        printWriter.println(Arrays.toString(this.mSelectionArgs));
        printWriter.print(string2);
        printWriter.print("mSortOrder=");
        printWriter.println(this.mSortOrder);
        printWriter.print(string2);
        printWriter.print("mCursor=");
        printWriter.println(this.mCursor);
        printWriter.print(string2);
        printWriter.print("mContentChanged=");
        printWriter.println(this.mContentChanged);
    }

    public String[] getProjection() {
        return this.mProjection;
    }

    public String getSelection() {
        return this.mSelection;
    }

    public String[] getSelectionArgs() {
        return this.mSelectionArgs;
    }

    public String getSortOrder() {
        return this.mSortOrder;
    }

    public Uri getUri() {
        return this.mUri;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Cursor loadInBackground() {
        Object object;
        synchronized (this) {
            if (this.isLoadInBackgroundCanceled()) {
                OperationCanceledException operationCanceledException = new OperationCanceledException();
                throw operationCanceledException;
            }
            object = new CancellationSignal();
            this.mCancellationSignal = object;
        }
        try {
            object = this.getContext().getContentResolver().query(this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder, this.mCancellationSignal);
            if (object == null) return object;
            try {
                object.getCount();
                object.registerContentObserver((ContentObserver)this.mObserver);
                return object;
            }
            catch (RuntimeException runtimeException) {
                object.close();
                throw runtimeException;
            }
        }
        finally {
            synchronized (this) {
                this.mCancellationSignal = null;
            }
        }
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        this.onStopLoading();
        Cursor cursor = this.mCursor;
        if (cursor != null && !cursor.isClosed()) {
            this.mCursor.close();
        }
        this.mCursor = null;
    }

    @Override
    protected void onStartLoading() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            this.deliverResult(cursor);
        }
        if (this.takeContentChanged() || this.mCursor == null) {
            this.forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        this.cancelLoad();
    }

    public void setProjection(String[] arrstring) {
        this.mProjection = arrstring;
    }

    public void setSelection(String string2) {
        this.mSelection = string2;
    }

    public void setSelectionArgs(String[] arrstring) {
        this.mSelectionArgs = arrstring;
    }

    public void setSortOrder(String string2) {
        this.mSortOrder = string2;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }
}

