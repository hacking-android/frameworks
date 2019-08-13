/*
 * Decompiled with CFR 0.145.
 */
package android.telecom.Logging;

import android.telecom.Log;
import android.telecom.Logging.Session;

public abstract class Runnable {
    private final Object mLock;
    private final java.lang.Runnable mRunnable = new java.lang.Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Object object = Runnable.this.mLock;
            synchronized (object) {
                try {
                    Log.continueSession(Runnable.this.mSubsession, Runnable.this.mSubsessionName);
                    Runnable.this.loggedRun();
                    return;
                }
                finally {
                    if (Runnable.this.mSubsession != null) {
                        Log.endSession();
                        Runnable.this.mSubsession = null;
                    }
                }
            }
        }
    };
    private Session mSubsession;
    private final String mSubsessionName;

    public Runnable(String string2, Object object) {
        this.mLock = object == null ? new Object() : object;
        this.mSubsessionName = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel() {
        Object object = this.mLock;
        synchronized (object) {
            Log.cancelSubsession(this.mSubsession);
            this.mSubsession = null;
            return;
        }
    }

    public final java.lang.Runnable getRunnableToCancel() {
        return this.mRunnable;
    }

    public abstract void loggedRun();

    public java.lang.Runnable prepare() {
        this.cancel();
        this.mSubsession = Log.createSubsession();
        return this.mRunnable;
    }

}

