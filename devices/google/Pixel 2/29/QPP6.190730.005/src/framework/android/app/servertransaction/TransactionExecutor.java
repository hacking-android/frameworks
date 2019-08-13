/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.Activity;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.PendingTransactionActions;
import android.app.servertransaction.TransactionExecutorHelper;
import android.content.Intent;
import android.os.IBinder;
import android.util.IntArray;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import java.util.List;
import java.util.Map;

public class TransactionExecutor {
    private static final boolean DEBUG_RESOLVER = false;
    private static final String TAG = "TransactionExecutor";
    private TransactionExecutorHelper mHelper = new TransactionExecutorHelper();
    private PendingTransactionActions mPendingActions = new PendingTransactionActions();
    private ClientTransactionHandler mTransactionHandler;

    public TransactionExecutor(ClientTransactionHandler clientTransactionHandler) {
        this.mTransactionHandler = clientTransactionHandler;
    }

    private void cycleToPath(ActivityThread.ActivityClientRecord activityClientRecord, int n, boolean bl, ClientTransaction clientTransaction) {
        int n2 = activityClientRecord.getLifecycleState();
        this.performLifecycleSequence(activityClientRecord, this.mHelper.getLifecyclePath(n2, n, bl), clientTransaction);
    }

    private void executeLifecycleState(ClientTransaction clientTransaction) {
        ActivityLifecycleItem activityLifecycleItem = clientTransaction.getLifecycleStateRequest();
        if (activityLifecycleItem == null) {
            return;
        }
        IBinder iBinder = clientTransaction.getActivityToken();
        ActivityThread.ActivityClientRecord activityClientRecord = this.mTransactionHandler.getActivityClient(iBinder);
        if (activityClientRecord == null) {
            return;
        }
        this.cycleToPath(activityClientRecord, activityLifecycleItem.getTargetState(), true, clientTransaction);
        activityLifecycleItem.execute(this.mTransactionHandler, iBinder, this.mPendingActions);
        activityLifecycleItem.postExecute(this.mTransactionHandler, iBinder, this.mPendingActions);
    }

    private void performLifecycleSequence(ActivityThread.ActivityClientRecord object, IntArray intArray, ClientTransaction object2) {
        int n = intArray.size();
        block9 : for (int i = 0; i < n; ++i) {
            int n2 = intArray.get(i);
            switch (n2) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unexpected lifecycle state: ");
                    ((StringBuilder)object).append(n2);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                case 7: {
                    this.mTransactionHandler.performRestartActivity(((ActivityThread.ActivityClientRecord)object).token, false);
                    continue block9;
                }
                case 6: {
                    ClientTransactionHandler clientTransactionHandler = this.mTransactionHandler;
                    IBinder iBinder = ((ActivityThread.ActivityClientRecord)object).token;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("performLifecycleSequence. cycling to:");
                    ((StringBuilder)object2).append(intArray.get(n - 1));
                    clientTransactionHandler.handleDestroyActivity(iBinder, false, 0, false, ((StringBuilder)object2).toString());
                    continue block9;
                }
                case 5: {
                    this.mTransactionHandler.handleStopActivity(((ActivityThread.ActivityClientRecord)object).token, false, 0, this.mPendingActions, false, "LIFECYCLER_STOP_ACTIVITY");
                    continue block9;
                }
                case 4: {
                    this.mTransactionHandler.handlePauseActivity(((ActivityThread.ActivityClientRecord)object).token, false, false, 0, this.mPendingActions, "LIFECYCLER_PAUSE_ACTIVITY");
                    continue block9;
                }
                case 3: {
                    this.mTransactionHandler.handleResumeActivity(((ActivityThread.ActivityClientRecord)object).token, false, ((ActivityThread.ActivityClientRecord)object).isForward, "LIFECYCLER_RESUME_ACTIVITY");
                    continue block9;
                }
                case 2: {
                    this.mTransactionHandler.handleStartActivity((ActivityThread.ActivityClientRecord)object, this.mPendingActions);
                    continue block9;
                }
                case 1: {
                    this.mTransactionHandler.handleLaunchActivity((ActivityThread.ActivityClientRecord)object, this.mPendingActions, null);
                }
            }
        }
    }

    @VisibleForTesting
    public void cycleToPath(ActivityThread.ActivityClientRecord activityClientRecord, int n, ClientTransaction clientTransaction) {
        this.cycleToPath(activityClientRecord, n, false, clientTransaction);
    }

    public void execute(ClientTransaction clientTransaction) {
        ClientTransactionItem clientTransactionItem;
        Object object;
        IBinder iBinder = clientTransaction.getActivityToken();
        if (iBinder != null && (clientTransactionItem = (object = this.mTransactionHandler.getActivitiesToBeDestroyed()).get(iBinder)) != null) {
            if (clientTransaction.getLifecycleStateRequest() == clientTransactionItem) {
                object.remove(iBinder);
            }
            if (this.mTransactionHandler.getActivityClient(iBinder) == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append(TransactionExecutorHelper.tId(clientTransaction));
                ((StringBuilder)object).append("Skip pre-destroyed transaction:\n");
                ((StringBuilder)object).append(TransactionExecutorHelper.transactionToString(clientTransaction, this.mTransactionHandler));
                Slog.w(TAG, ((StringBuilder)object).toString());
                return;
            }
        }
        this.executeCallbacks(clientTransaction);
        this.executeLifecycleState(clientTransaction);
        this.mPendingActions.clear();
    }

    @VisibleForTesting
    public void executeCallbacks(ClientTransaction clientTransaction) {
        List<ClientTransactionItem> list = clientTransaction.getCallbacks();
        if (list != null && !list.isEmpty()) {
            IBinder iBinder = clientTransaction.getActivityToken();
            Object object = this.mTransactionHandler.getActivityClient(iBinder);
            Object object2 = clientTransaction.getLifecycleStateRequest();
            int n = object2 != null ? ((ActivityLifecycleItem)object2).getTargetState() : -1;
            int n2 = TransactionExecutorHelper.lastCallbackRequestingState(clientTransaction);
            int n3 = list.size();
            for (int i = 0; i < n3; ++i) {
                object2 = list.get(i);
                int n4 = ((ClientTransactionItem)object2).getPostExecutionState();
                int n5 = this.mHelper.getClosestPreExecutionState((ActivityThread.ActivityClientRecord)object, ((ClientTransactionItem)object2).getPostExecutionState());
                if (n5 != -1) {
                    this.cycleToPath((ActivityThread.ActivityClientRecord)object, n5, clientTransaction);
                }
                object2.execute(this.mTransactionHandler, iBinder, this.mPendingActions);
                object2.postExecute(this.mTransactionHandler, iBinder, this.mPendingActions);
                object2 = object;
                if (object == null) {
                    object2 = this.mTransactionHandler.getActivityClient(iBinder);
                }
                if (n4 != -1 && object2 != null) {
                    boolean bl = i == n2 && n == n4;
                    this.cycleToPath((ActivityThread.ActivityClientRecord)object2, n4, bl, clientTransaction);
                }
                object = object2;
            }
            return;
        }
    }
}

