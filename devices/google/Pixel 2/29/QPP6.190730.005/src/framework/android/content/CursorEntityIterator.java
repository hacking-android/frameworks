/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.Entity;
import android.content.EntityIterator;
import android.database.Cursor;
import android.os.RemoteException;

public abstract class CursorEntityIterator
implements EntityIterator {
    private final Cursor mCursor;
    private boolean mIsClosed = false;

    @UnsupportedAppUsage
    public CursorEntityIterator(Cursor cursor) {
        this.mCursor = cursor;
        this.mCursor.moveToFirst();
    }

    @Override
    public final void close() {
        if (!this.mIsClosed) {
            this.mIsClosed = true;
            this.mCursor.close();
            return;
        }
        throw new IllegalStateException("closing when already closed");
    }

    public abstract Entity getEntityAndIncrementCursor(Cursor var1) throws RemoteException;

    @Override
    public final boolean hasNext() {
        if (!this.mIsClosed) {
            return this.mCursor.isAfterLast() ^ true;
        }
        throw new IllegalStateException("calling hasNext() when the iterator is closed");
    }

    @Override
    public Entity next() {
        if (!this.mIsClosed) {
            if (this.hasNext()) {
                try {
                    Entity entity = this.getEntityAndIncrementCursor(this.mCursor);
                    return entity;
                }
                catch (RemoteException remoteException) {
                    throw new RuntimeException("caught a remote exception, this process will die soon", remoteException);
                }
            }
            throw new IllegalStateException("you may only call next() if hasNext() is true");
        }
        throw new IllegalStateException("calling next() when the iterator is closed");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove not supported by EntityIterators");
    }

    @Override
    public final void reset() {
        if (!this.mIsClosed) {
            this.mCursor.moveToFirst();
            return;
        }
        throw new IllegalStateException("calling reset() when the iterator is closed");
    }
}

