/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.BulkCursorDescriptor;
import android.database.BulkCursorNative;
import android.database.ContentObserver;
import android.database.CrossProcessCursor;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.IBulkCursor;
import android.database.IContentObserver;
import android.database.StaleDataException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

public final class CursorToBulkCursorAdaptor
extends BulkCursorNative
implements IBinder.DeathRecipient {
    private static final String TAG = "Cursor";
    private CrossProcessCursor mCursor;
    private CursorWindow mFilledWindow;
    private final Object mLock = new Object();
    private ContentObserverProxy mObserver;
    private final String mProviderName;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CursorToBulkCursorAdaptor(Cursor object, IContentObserver iContentObserver, String string2) {
        this.mCursor = object instanceof CrossProcessCursor ? (CrossProcessCursor)object : new CrossProcessCursorWrapper((Cursor)object);
        this.mProviderName = string2;
        object = this.mLock;
        synchronized (object) {
            this.createAndRegisterObserverProxyLocked(iContentObserver);
            return;
        }
    }

    private void closeFilledWindowLocked() {
        CursorWindow cursorWindow = this.mFilledWindow;
        if (cursorWindow != null) {
            cursorWindow.close();
            this.mFilledWindow = null;
        }
    }

    private void createAndRegisterObserverProxyLocked(IContentObserver iContentObserver) {
        if (this.mObserver == null) {
            this.mObserver = new ContentObserverProxy(iContentObserver, this);
            this.mCursor.registerContentObserver(this.mObserver);
            return;
        }
        throw new IllegalStateException("an observer is already registered");
    }

    private void disposeLocked() {
        if (this.mCursor != null) {
            this.unregisterObserverProxyLocked();
            this.mCursor.close();
            this.mCursor = null;
        }
        this.closeFilledWindowLocked();
    }

    private void throwIfCursorIsClosed() {
        if (this.mCursor != null) {
            return;
        }
        throw new StaleDataException("Attempted to access a cursor after it has been closed.");
    }

    private void unregisterObserverProxyLocked() {
        ContentObserverProxy contentObserverProxy = this.mObserver;
        if (contentObserverProxy != null) {
            this.mCursor.unregisterContentObserver(contentObserverProxy);
            this.mObserver.unlinkToDeath(this);
            this.mObserver = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void binderDied() {
        Object object = this.mLock;
        synchronized (object) {
            this.disposeLocked();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Object object = this.mLock;
        synchronized (object) {
            this.disposeLocked();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void deactivate() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCursor != null) {
                this.unregisterObserverProxyLocked();
                this.mCursor.deactivate();
            }
            this.closeFilledWindowLocked();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public BulkCursorDescriptor getBulkCursorDescriptor() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCursorIsClosed();
            BulkCursorDescriptor bulkCursorDescriptor = new BulkCursorDescriptor();
            bulkCursorDescriptor.cursor = this;
            bulkCursorDescriptor.columnNames = this.mCursor.getColumnNames();
            bulkCursorDescriptor.wantsAllOnMoveCalls = this.mCursor.getWantsAllOnMoveCalls();
            bulkCursorDescriptor.count = this.mCursor.getCount();
            bulkCursorDescriptor.window = this.mCursor.getWindow();
            if (bulkCursorDescriptor.window != null) {
                bulkCursorDescriptor.window.acquireReference();
            }
            return bulkCursorDescriptor;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Bundle getExtras() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCursorIsClosed();
            return this.mCursor.getExtras();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CursorWindow getWindow(int n) {
        Object object = this.mLock;
        synchronized (object) {
            CursorWindow cursorWindow;
            block8 : {
                block10 : {
                    CursorWindow cursorWindow2;
                    block11 : {
                        block9 : {
                            block7 : {
                                this.throwIfCursorIsClosed();
                                if (!this.mCursor.moveToPosition(n)) {
                                    this.closeFilledWindowLocked();
                                    return null;
                                }
                                cursorWindow = this.mCursor.getWindow();
                                if (cursorWindow == null) break block7;
                                this.closeFilledWindowLocked();
                                break block8;
                            }
                            cursorWindow2 = this.mFilledWindow;
                            if (cursorWindow2 != null) break block9;
                            cursorWindow = this.mFilledWindow = (cursorWindow = new CursorWindow(this.mProviderName));
                            break block10;
                        }
                        if (n < cursorWindow2.getStartPosition()) break block11;
                        cursorWindow = cursorWindow2;
                        if (n < cursorWindow2.getStartPosition() + cursorWindow2.getNumRows()) break block10;
                    }
                    cursorWindow2.clear();
                    cursorWindow = cursorWindow2;
                }
                this.mCursor.fillWindow(n, cursorWindow);
            }
            if (cursorWindow != null) {
                cursorWindow.acquireReference();
            }
            return cursorWindow;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onMove(int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCursorIsClosed();
            this.mCursor.onMove(this.mCursor.getPosition(), n);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int requery(IContentObserver object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            block4 : {
                this.throwIfCursorIsClosed();
                this.closeFilledWindowLocked();
                try {
                    boolean bl = this.mCursor.requery();
                    if (bl) break block4;
                }
                catch (IllegalStateException illegalStateException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(this.mProviderName);
                    ((StringBuilder)object).append(" Requery misuse db, mCursor isClosed:");
                    ((StringBuilder)object).append(this.mCursor.isClosed());
                    IllegalStateException illegalStateException2 = new IllegalStateException(((StringBuilder)object).toString(), illegalStateException);
                    throw illegalStateException2;
                }
                return -1;
            }
            this.unregisterObserverProxyLocked();
            this.createAndRegisterObserverProxyLocked((IContentObserver)object);
            return this.mCursor.getCount();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Bundle respond(Bundle bundle) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfCursorIsClosed();
            return this.mCursor.respond(bundle);
        }
    }

    private static final class ContentObserverProxy
    extends ContentObserver {
        protected IContentObserver mRemote;

        public ContentObserverProxy(IContentObserver iContentObserver, IBinder.DeathRecipient deathRecipient) {
            super(null);
            this.mRemote = iContentObserver;
            try {
                iContentObserver.asBinder().linkToDeath(deathRecipient, 0);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        @Override
        public boolean deliverSelfNotifications() {
            return false;
        }

        @Override
        public void onChange(boolean bl, Uri uri) {
            try {
                this.mRemote.onChange(bl, uri, Process.myUid());
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient) {
            return this.mRemote.asBinder().unlinkToDeath(deathRecipient, 0);
        }
    }

}

