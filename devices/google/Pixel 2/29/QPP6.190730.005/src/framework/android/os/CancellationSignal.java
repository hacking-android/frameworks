/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.ICancellationSignal;
import android.os.OperationCanceledException;
import android.os.RemoteException;

public final class CancellationSignal {
    private boolean mCancelInProgress;
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;
    private ICancellationSignal mRemote;

    public static ICancellationSignal createTransport() {
        return new Transport();
    }

    public static CancellationSignal fromTransport(ICancellationSignal iCancellationSignal) {
        if (iCancellationSignal instanceof Transport) {
            return ((Transport)iCancellationSignal).mCancellationSignal;
        }
        return null;
    }

    private void waitForCancelFinishedLocked() {
        while (this.mCancelInProgress) {
            try {
                this.wait();
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void cancel() {
        block15 : {
            Throwable throwable2;
            block14 : {
                // MONITORENTER : this
                if (this.mIsCanceled) {
                    // MONITOREXIT : this
                    return;
                }
                this.mIsCanceled = true;
                this.mCancelInProgress = true;
                OnCancelListener onCancelListener = this.mOnCancelListener;
                ICancellationSignal iCancellationSignal = this.mRemote;
                // MONITOREXIT : this
                if (onCancelListener != null) {
                    try {
                        onCancelListener.onCancel();
                    }
                    catch (Throwable throwable2) {
                        break block14;
                    }
                }
                if (iCancellationSignal == null) break block15;
                try {
                    iCancellationSignal.cancel();
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            // MONITORENTER : this
            this.mCancelInProgress = false;
            this.notifyAll();
            // MONITOREXIT : this
            throw throwable2;
        }
        // MONITORENTER : this
        this.mCancelInProgress = false;
        this.notifyAll();
        // MONITOREXIT : this
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isCanceled() {
        synchronized (this) {
            return this.mIsCanceled;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnCancelListener(OnCancelListener onCancelListener) {
        synchronized (this) {
            this.waitForCancelFinishedLocked();
            if (this.mOnCancelListener == onCancelListener) {
                return;
            }
            this.mOnCancelListener = onCancelListener;
            if (this.mIsCanceled && onCancelListener != null) {
                // MONITOREXIT [2, 3] lbl8 : MonitorExitStatement: MONITOREXIT : this
                onCancelListener.onCancel();
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRemote(ICancellationSignal iCancellationSignal) {
        synchronized (this) {
            this.waitForCancelFinishedLocked();
            if (this.mRemote == iCancellationSignal) {
                return;
            }
            this.mRemote = iCancellationSignal;
            if (!this.mIsCanceled) return;
            if (iCancellationSignal == null) {
                return;
            }
        }
        try {
            iCancellationSignal.cancel();
            return;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void throwIfCanceled() {
        if (!this.isCanceled()) {
            return;
        }
        throw new OperationCanceledException();
    }

    public static interface OnCancelListener {
        public void onCancel();
    }

    private static final class Transport
    extends ICancellationSignal.Stub {
        final CancellationSignal mCancellationSignal = new CancellationSignal();

        private Transport() {
        }

        @Override
        public void cancel() throws RemoteException {
            this.mCancellationSignal.cancel();
        }
    }

}

