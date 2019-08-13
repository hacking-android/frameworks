/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import java.io.Closeable;

public abstract class SQLiteClosable
implements Closeable {
    @UnsupportedAppUsage
    private int mReferenceCount = 1;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void acquireReference() {
        synchronized (this) {
            if (this.mReferenceCount > 0) {
                ++this.mReferenceCount;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("attempt to re-open an already-closed object: ");
            stringBuilder.append(this);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    @Override
    public void close() {
        this.releaseReference();
    }

    protected abstract void onAllReferencesReleased();

    @Deprecated
    protected void onAllReferencesReleasedFromContainer() {
        this.onAllReferencesReleased();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void releaseReference() {
        boolean bl;
        synchronized (this) {
            int n = this.mReferenceCount;
            bl = true;
            this.mReferenceCount = --n;
            if (n != 0) return;
        }
        if (!bl) return;
        this.onAllReferencesReleased();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void releaseReferenceFromContainer() {
        boolean bl;
        synchronized (this) {
            int n = this.mReferenceCount;
            bl = true;
            this.mReferenceCount = --n;
            if (n != 0) return;
        }
        if (!bl) return;
        this.onAllReferencesReleasedFromContainer();
    }
}

