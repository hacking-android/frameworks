/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.QueuedWork;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public abstract class BroadcastReceiver {
    private boolean mDebugUnregister;
    @UnsupportedAppUsage
    private PendingResult mPendingResult;

    public final void abortBroadcast() {
        this.checkSynchronousHint();
        this.mPendingResult.mAbortBroadcast = true;
    }

    void checkSynchronousHint() {
        Object object = this.mPendingResult;
        if (object != null) {
            if (!((PendingResult)object).mOrderedHint && !this.mPendingResult.mInitialStickyHint) {
                object = new RuntimeException("BroadcastReceiver trying to return result during a non-ordered broadcast");
                ((Throwable)object).fillInStackTrace();
                Log.e("BroadcastReceiver", ((Throwable)object).getMessage(), (Throwable)object);
                return;
            }
            return;
        }
        throw new IllegalStateException("Call while result is not pending");
    }

    public final void clearAbortBroadcast() {
        PendingResult pendingResult = this.mPendingResult;
        if (pendingResult != null) {
            pendingResult.mAbortBroadcast = false;
        }
    }

    public final boolean getAbortBroadcast() {
        PendingResult pendingResult = this.mPendingResult;
        boolean bl = pendingResult != null ? pendingResult.mAbortBroadcast : false;
        return bl;
    }

    public final boolean getDebugUnregister() {
        return this.mDebugUnregister;
    }

    @UnsupportedAppUsage
    public final PendingResult getPendingResult() {
        return this.mPendingResult;
    }

    public final int getResultCode() {
        PendingResult pendingResult = this.mPendingResult;
        int n = pendingResult != null ? pendingResult.mResultCode : 0;
        return n;
    }

    public final String getResultData() {
        Object object = this.mPendingResult;
        object = object != null ? ((PendingResult)object).mResultData : null;
        return object;
    }

    public final Bundle getResultExtras(boolean bl) {
        Object object = this.mPendingResult;
        if (object == null) {
            return null;
        }
        Bundle bundle = ((PendingResult)object).mResultExtras;
        if (!bl) {
            return bundle;
        }
        object = bundle;
        if (bundle == null) {
            PendingResult pendingResult = this.mPendingResult;
            bundle = new Bundle();
            object = bundle;
            pendingResult.mResultExtras = bundle;
        }
        return object;
    }

    public int getSendingUserId() {
        return this.mPendingResult.mSendingUser;
    }

    public final PendingResult goAsync() {
        PendingResult pendingResult = this.mPendingResult;
        this.mPendingResult = null;
        return pendingResult;
    }

    public final boolean isInitialStickyBroadcast() {
        PendingResult pendingResult = this.mPendingResult;
        boolean bl = pendingResult != null ? pendingResult.mInitialStickyHint : false;
        return bl;
    }

    public final boolean isOrderedBroadcast() {
        PendingResult pendingResult = this.mPendingResult;
        boolean bl = pendingResult != null ? pendingResult.mOrderedHint : false;
        return bl;
    }

    public abstract void onReceive(Context var1, Intent var2);

    public IBinder peekService(Context object, Intent intent) {
        IActivityManager iActivityManager = ActivityManager.getService();
        Object var4_5 = null;
        try {
            intent.prepareToLeaveProcess((Context)object);
            object = iActivityManager.peekService(intent, intent.resolveTypeIfNeeded(((Context)object).getContentResolver()), ((Context)object).getOpPackageName());
        }
        catch (RemoteException remoteException) {
            object = var4_5;
        }
        return object;
    }

    public final void setDebugUnregister(boolean bl) {
        this.mDebugUnregister = bl;
    }

    public final void setOrderedHint(boolean bl) {
    }

    @UnsupportedAppUsage
    public final void setPendingResult(PendingResult pendingResult) {
        this.mPendingResult = pendingResult;
    }

    public final void setResult(int n, String string2, Bundle bundle) {
        this.checkSynchronousHint();
        PendingResult pendingResult = this.mPendingResult;
        pendingResult.mResultCode = n;
        pendingResult.mResultData = string2;
        pendingResult.mResultExtras = bundle;
    }

    public final void setResultCode(int n) {
        this.checkSynchronousHint();
        this.mPendingResult.mResultCode = n;
    }

    public final void setResultData(String string2) {
        this.checkSynchronousHint();
        this.mPendingResult.mResultData = string2;
    }

    public final void setResultExtras(Bundle bundle) {
        this.checkSynchronousHint();
        this.mPendingResult.mResultExtras = bundle;
    }

    public static class PendingResult {
        public static final int TYPE_COMPONENT = 0;
        public static final int TYPE_REGISTERED = 1;
        public static final int TYPE_UNREGISTERED = 2;
        @UnsupportedAppUsage
        boolean mAbortBroadcast;
        @UnsupportedAppUsage
        boolean mFinished;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        final int mFlags;
        @UnsupportedAppUsage
        final boolean mInitialStickyHint;
        @UnsupportedAppUsage
        final boolean mOrderedHint;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        int mResultCode;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        String mResultData;
        @UnsupportedAppUsage
        Bundle mResultExtras;
        @UnsupportedAppUsage
        final int mSendingUser;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        final IBinder mToken;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        final int mType;

        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public PendingResult(int n, String string2, Bundle bundle, int n2, boolean bl, boolean bl2, IBinder iBinder, int n3, int n4) {
            this.mResultCode = n;
            this.mResultData = string2;
            this.mResultExtras = bundle;
            this.mType = n2;
            this.mOrderedHint = bl;
            this.mInitialStickyHint = bl2;
            this.mToken = iBinder;
            this.mSendingUser = n3;
            this.mFlags = n4;
        }

        public final void abortBroadcast() {
            this.checkSynchronousHint();
            this.mAbortBroadcast = true;
        }

        void checkSynchronousHint() {
            if (!this.mOrderedHint && !this.mInitialStickyHint) {
                RuntimeException runtimeException = new RuntimeException("BroadcastReceiver trying to return result during a non-ordered broadcast");
                runtimeException.fillInStackTrace();
                Log.e("BroadcastReceiver", runtimeException.getMessage(), runtimeException);
                return;
            }
        }

        public final void clearAbortBroadcast() {
            this.mAbortBroadcast = false;
        }

        public final void finish() {
            block3 : {
                int n;
                block2 : {
                    n = this.mType;
                    if (n != 0) break block2;
                    final IActivityManager iActivityManager = ActivityManager.getService();
                    if (QueuedWork.hasPendingWork()) {
                        QueuedWork.queue(new Runnable(){

                            @Override
                            public void run() {
                                PendingResult.this.sendFinished(iActivityManager);
                            }
                        }, false);
                    } else {
                        this.sendFinished(iActivityManager);
                    }
                    break block3;
                }
                if (!this.mOrderedHint || n == 2) break block3;
                this.sendFinished(ActivityManager.getService());
            }
        }

        public final boolean getAbortBroadcast() {
            return this.mAbortBroadcast;
        }

        public final int getResultCode() {
            return this.mResultCode;
        }

        public final String getResultData() {
            return this.mResultData;
        }

        public final Bundle getResultExtras(boolean bl) {
            Bundle bundle = this.mResultExtras;
            if (!bl) {
                return bundle;
            }
            Bundle bundle2 = bundle;
            if (bundle == null) {
                bundle2 = bundle = new Bundle();
                this.mResultExtras = bundle;
            }
            return bundle2;
        }

        public int getSendingUserId() {
            return this.mSendingUser;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void sendFinished(IActivityManager object) {
            synchronized (this) {
                if (this.mFinished) {
                    object = new IllegalStateException("Broadcast already finished");
                    throw object;
                }
                this.mFinished = true;
                try {
                    if (this.mResultExtras != null) {
                        this.mResultExtras.setAllowFds(false);
                    }
                    if (this.mOrderedHint) {
                        object.finishReceiver(this.mToken, this.mResultCode, this.mResultData, this.mResultExtras, this.mAbortBroadcast, this.mFlags);
                    } else {
                        object.finishReceiver(this.mToken, 0, null, null, false, this.mFlags);
                    }
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                return;
            }
        }

        public void setExtrasClassLoader(ClassLoader classLoader) {
            Bundle bundle = this.mResultExtras;
            if (bundle != null) {
                bundle.setClassLoader(classLoader);
            }
        }

        public final void setResult(int n, String string2, Bundle bundle) {
            this.checkSynchronousHint();
            this.mResultCode = n;
            this.mResultData = string2;
            this.mResultExtras = bundle;
        }

        public final void setResultCode(int n) {
            this.checkSynchronousHint();
            this.mResultCode = n;
        }

        public final void setResultData(String string2) {
            this.checkSynchronousHint();
            this.mResultData = string2;
        }

        public final void setResultExtras(Bundle bundle) {
            this.checkSynchronousHint();
            this.mResultExtras = bundle;
        }

    }

}

