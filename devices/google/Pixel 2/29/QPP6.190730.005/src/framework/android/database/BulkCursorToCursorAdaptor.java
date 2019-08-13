/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.AbstractCursor;
import android.database.AbstractWindowedCursor;
import android.database.BulkCursorDescriptor;
import android.database.CursorWindow;
import android.database.IBulkCursor;
import android.database.IContentObserver;
import android.database.StaleDataException;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

public final class BulkCursorToCursorAdaptor
extends AbstractWindowedCursor {
    private static final String TAG = "BulkCursor";
    private IBulkCursor mBulkCursor;
    private String[] mColumns;
    private int mCount;
    private AbstractCursor.SelfContentObserver mObserverBridge = new AbstractCursor.SelfContentObserver(this);
    private boolean mWantsAllOnMoveCalls;

    private void throwIfCursorIsClosed() {
        if (this.mBulkCursor != null) {
            return;
        }
        throw new StaleDataException("Attempted to access a cursor after it has been closed.");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void close() {
        super.close();
        var1_1 = this.mBulkCursor;
        if (var1_1 == null) return;
        var1_1.close();
        this.mBulkCursor = null;
        return;
        {
            catch (RemoteException var1_3) {}
            {
                Log.w("BulkCursor", "Remote process exception when closing");
            }
        }
        ** finally { 
lbl12: // 1 sources:
        this.mBulkCursor = null;
        throw var1_2;
    }

    @Override
    public void deactivate() {
        super.deactivate();
        IBulkCursor iBulkCursor = this.mBulkCursor;
        if (iBulkCursor != null) {
            try {
                iBulkCursor.deactivate();
            }
            catch (RemoteException remoteException) {
                Log.w(TAG, "Remote process exception when deactivating");
            }
        }
    }

    @Override
    public String[] getColumnNames() {
        this.throwIfCursorIsClosed();
        return this.mColumns;
    }

    @Override
    public int getCount() {
        this.throwIfCursorIsClosed();
        return this.mCount;
    }

    @Override
    public Bundle getExtras() {
        this.throwIfCursorIsClosed();
        try {
            Bundle bundle = this.mBulkCursor.getExtras();
            return bundle;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public IContentObserver getObserver() {
        return this.mObserverBridge.getContentObserver();
    }

    public void initialize(BulkCursorDescriptor bulkCursorDescriptor) {
        this.mBulkCursor = bulkCursorDescriptor.cursor;
        this.mColumns = bulkCursorDescriptor.columnNames;
        this.mWantsAllOnMoveCalls = bulkCursorDescriptor.wantsAllOnMoveCalls;
        this.mCount = bulkCursorDescriptor.count;
        if (bulkCursorDescriptor.window != null) {
            this.setWindow(bulkCursorDescriptor.window);
        }
    }

    @Override
    public boolean onMove(int n, int n2) {
        this.throwIfCursorIsClosed();
        try {
            if (this.mWindow != null && n2 >= this.mWindow.getStartPosition() && n2 < this.mWindow.getStartPosition() + this.mWindow.getNumRows()) {
                if (this.mWantsAllOnMoveCalls) {
                    this.mBulkCursor.onMove(n2);
                }
            } else {
                this.setWindow(this.mBulkCursor.getWindow(n2));
            }
            return this.mWindow != null;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Unable to get window because the remote process is dead");
            return false;
        }
    }

    @Override
    public boolean requery() {
        block3 : {
            this.throwIfCursorIsClosed();
            try {
                this.mCount = this.mBulkCursor.requery(this.getObserver());
                if (this.mCount == -1) break block3;
                this.mPos = -1;
                this.closeWindow();
                super.requery();
                return true;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to requery because the remote process exception ");
                stringBuilder.append(exception.getMessage());
                Log.e(TAG, stringBuilder.toString());
                this.deactivate();
                return false;
            }
        }
        this.deactivate();
        return false;
    }

    @Override
    public Bundle respond(Bundle bundle) {
        this.throwIfCursorIsClosed();
        try {
            bundle = this.mBulkCursor.respond(bundle);
            return bundle;
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "respond() threw RemoteException, returning an empty bundle.", remoteException);
            return Bundle.EMPTY;
        }
    }
}

