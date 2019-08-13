/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.LoadedApk;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.util.Log;
import android.util.LogWriter;
import com.android.internal.util.IndentingPrintWriter;
import java.io.Writer;

public class PendingTransactionActions {
    private boolean mCallOnPostCreate;
    private Bundle mOldState;
    private boolean mReportRelaunchToWM;
    private boolean mRestoreInstanceState;
    private StopInfo mStopInfo;

    public PendingTransactionActions() {
        this.clear();
    }

    public void clear() {
        this.mRestoreInstanceState = false;
        this.mCallOnPostCreate = false;
        this.mOldState = null;
        this.mStopInfo = null;
    }

    public Bundle getOldState() {
        return this.mOldState;
    }

    public StopInfo getStopInfo() {
        return this.mStopInfo;
    }

    public void setCallOnPostCreate(boolean bl) {
        this.mCallOnPostCreate = bl;
    }

    public void setOldState(Bundle bundle) {
        this.mOldState = bundle;
    }

    public void setReportRelaunchToWindowManager(boolean bl) {
        this.mReportRelaunchToWM = bl;
    }

    public void setRestoreInstanceState(boolean bl) {
        this.mRestoreInstanceState = bl;
    }

    public void setStopInfo(StopInfo stopInfo) {
        this.mStopInfo = stopInfo;
    }

    public boolean shouldCallOnPostCreate() {
        return this.mCallOnPostCreate;
    }

    public boolean shouldReportRelaunchToWindowManager() {
        return this.mReportRelaunchToWM;
    }

    public boolean shouldRestoreInstanceState() {
        return this.mRestoreInstanceState;
    }

    public static class StopInfo
    implements Runnable {
        private static final String TAG = "ActivityStopInfo";
        private ActivityThread.ActivityClientRecord mActivity;
        private CharSequence mDescription;
        private PersistableBundle mPersistentState;
        private Bundle mState;

        @Override
        public void run() {
            try {
                ActivityTaskManager.getService().activityStopped(this.mActivity.token, this.mState, this.mPersistentState, this.mDescription);
                return;
            }
            catch (RemoteException remoteException) {
                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(new LogWriter(5, TAG), "  ");
                indentingPrintWriter.println("Bundle stats:");
                Bundle.dumpStats(indentingPrintWriter, this.mState);
                indentingPrintWriter.println("PersistableBundle stats:");
                Bundle.dumpStats(indentingPrintWriter, this.mPersistentState);
                if (remoteException instanceof TransactionTooLargeException && this.mActivity.packageInfo.getTargetSdkVersion() < 24) {
                    Log.e(TAG, "App sent too much data in instance state, so it was ignored", remoteException);
                    return;
                }
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void setActivity(ActivityThread.ActivityClientRecord activityClientRecord) {
            this.mActivity = activityClientRecord;
        }

        public void setDescription(CharSequence charSequence) {
            this.mDescription = charSequence;
        }

        public void setPersistentState(PersistableBundle persistableBundle) {
            this.mPersistentState = persistableBundle;
        }

        public void setState(Bundle bundle) {
            this.mState = bundle;
        }
    }

}

