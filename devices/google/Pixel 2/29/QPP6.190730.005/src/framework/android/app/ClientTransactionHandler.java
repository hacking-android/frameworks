/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.app.ActivityThread;
import android.app.LoadedApk;
import android.app.ResultInfo;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.PendingTransactionActions;
import android.app.servertransaction.TransactionExecutor;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.MergedConfiguration;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.content.ReferrerIntent;
import java.util.List;
import java.util.Map;

public abstract class ClientTransactionHandler {
    public abstract void countLaunchingActivities(int var1);

    @VisibleForTesting
    public void executeTransaction(ClientTransaction clientTransaction) {
        clientTransaction.preExecute(this);
        this.getTransactionExecutor().execute(clientTransaction);
        clientTransaction.recycle();
    }

    public abstract Map<IBinder, ClientTransactionItem> getActivitiesToBeDestroyed();

    public abstract Activity getActivity(IBinder var1);

    public abstract ActivityThread.ActivityClientRecord getActivityClient(IBinder var1);

    public abstract LoadedApk getPackageInfoNoCheck(ApplicationInfo var1, CompatibilityInfo var2);

    abstract TransactionExecutor getTransactionExecutor();

    public abstract void handleActivityConfigurationChanged(IBinder var1, Configuration var2, int var3);

    public abstract void handleConfigurationChanged(Configuration var1);

    public abstract void handleDestroyActivity(IBinder var1, boolean var2, int var3, boolean var4, String var5);

    public abstract Activity handleLaunchActivity(ActivityThread.ActivityClientRecord var1, PendingTransactionActions var2, Intent var3);

    public abstract void handleMultiWindowModeChanged(IBinder var1, boolean var2, Configuration var3);

    public abstract void handleNewIntent(IBinder var1, List<ReferrerIntent> var2);

    public abstract void handlePauseActivity(IBinder var1, boolean var2, boolean var3, int var4, PendingTransactionActions var5, String var6);

    public abstract void handlePictureInPictureModeChanged(IBinder var1, boolean var2, Configuration var3);

    public abstract void handleRelaunchActivity(ActivityThread.ActivityClientRecord var1, PendingTransactionActions var2);

    public abstract void handleResumeActivity(IBinder var1, boolean var2, boolean var3, String var4);

    public abstract void handleSendResult(IBinder var1, List<ResultInfo> var2, String var3);

    public abstract void handleStartActivity(ActivityThread.ActivityClientRecord var1, PendingTransactionActions var2);

    public abstract void handleStopActivity(IBinder var1, boolean var2, int var3, PendingTransactionActions var4, boolean var5, String var6);

    public abstract void handleTopResumedActivityChanged(IBinder var1, boolean var2, String var3);

    public abstract void handleWindowVisibility(IBinder var1, boolean var2);

    public abstract void performRestartActivity(IBinder var1, boolean var2);

    public abstract ActivityThread.ActivityClientRecord prepareRelaunchActivity(IBinder var1, List<ResultInfo> var2, List<ReferrerIntent> var3, int var4, MergedConfiguration var5, boolean var6);

    public abstract void reportRelaunch(IBinder var1, PendingTransactionActions var2);

    public abstract void reportStop(PendingTransactionActions var1);

    void scheduleTransaction(ClientTransaction clientTransaction) {
        clientTransaction.preExecute(this);
        this.sendMessage(159, clientTransaction);
    }

    abstract void sendMessage(int var1, Object var2);

    public abstract void updatePendingActivityConfiguration(IBinder var1, Configuration var2);

    public abstract void updatePendingConfiguration(Configuration var1);

    public abstract void updateProcessState(int var1, boolean var2);
}

