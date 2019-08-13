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
import android.app.servertransaction.PauseActivityItem;
import android.app.servertransaction.ResumeActivityItem;
import android.app.servertransaction.StopActivityItem;
import android.content.ComponentName;
import android.os.IBinder;
import android.util.IntArray;
import com.android.internal.annotations.VisibleForTesting;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class TransactionExecutorHelper {
    private static final int DESTRUCTION_PENALTY = 10;
    private static final int[] ON_RESUME_PRE_EXCUTION_STATES = new int[]{2, 4};
    private IntArray mLifecycleSequence = new IntArray(6);

    private static Activity getActivityForToken(IBinder iBinder, ClientTransactionHandler clientTransactionHandler) {
        if (iBinder == null) {
            return null;
        }
        return clientTransactionHandler.getActivity(iBinder);
    }

    static String getActivityName(IBinder iBinder, ClientTransactionHandler object) {
        if ((object = TransactionExecutorHelper.getActivityForToken(iBinder, (ClientTransactionHandler)object)) != null) {
            return ((Activity)object).getComponentName().getClassName();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Not found for token: ");
        ((StringBuilder)object).append(iBinder);
        return ((StringBuilder)object).toString();
    }

    public static ActivityLifecycleItem getLifecycleRequestForCurrentState(ActivityThread.ActivityClientRecord object) {
        int n = ((ActivityThread.ActivityClientRecord)object).getLifecycleState();
        object = n != 4 ? (n != 5 ? ResumeActivityItem.obtain(false) : StopActivityItem.obtain(((ActivityThread.ActivityClientRecord)object).isVisibleFromServer(), 0)) : PauseActivityItem.obtain();
        return object;
    }

    static String getShortActivityName(IBinder iBinder, ClientTransactionHandler object) {
        if ((object = TransactionExecutorHelper.getActivityForToken(iBinder, (ClientTransactionHandler)object)) != null) {
            return ((Activity)object).getComponentName().getShortClassName();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Not found for token: ");
        ((StringBuilder)object).append(iBinder);
        return ((StringBuilder)object).toString();
    }

    static String getStateName(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected lifecycle state: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 7: {
                return "ON_RESTART";
            }
            case 6: {
                return "ON_DESTROY";
            }
            case 5: {
                return "ON_STOP";
            }
            case 4: {
                return "ON_PAUSE";
            }
            case 3: {
                return "ON_RESUME";
            }
            case 2: {
                return "ON_START";
            }
            case 1: {
                return "ON_CREATE";
            }
            case 0: {
                return "PRE_ON_CREATE";
            }
            case -1: 
        }
        return "UNDEFINED";
    }

    static int lastCallbackRequestingState(ClientTransaction object) {
        if ((object = ((ClientTransaction)object).getCallbacks()) != null && object.size() != 0) {
            int n = -1;
            int n2 = -1;
            for (int i = object.size() - 1; i >= 0; --i) {
                int n3 = ((ClientTransactionItem)object.get(i)).getPostExecutionState();
                int n4 = n;
                int n5 = n2;
                if (n3 != -1) {
                    if (n != -1 && n != n3) break;
                    n4 = n3;
                    n5 = i;
                }
                n = n4;
                n2 = n5;
            }
            return n2;
        }
        return -1;
    }

    private static boolean pathInvolvesDestruction(IntArray intArray) {
        int n = intArray.size();
        for (int i = 0; i < n; ++i) {
            if (intArray.get(i) != 6) continue;
            return true;
        }
        return false;
    }

    static String tId(ClientTransaction clientTransaction) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tId:");
        stringBuilder.append(clientTransaction.hashCode());
        stringBuilder.append(" ");
        return stringBuilder.toString();
    }

    static String transactionToString(ClientTransaction clientTransaction, ClientTransactionHandler clientTransactionHandler) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        String string2 = TransactionExecutorHelper.tId(clientTransaction);
        clientTransaction.dump(string2, printWriter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("Target activity: ");
        printWriter.append(stringBuilder.toString()).println(TransactionExecutorHelper.getActivityName(clientTransaction.getActivityToken(), clientTransactionHandler));
        return stringWriter.toString();
    }

    @VisibleForTesting
    public int getClosestOfStates(ActivityThread.ActivityClientRecord activityClientRecord, int[] arrn) {
        if (arrn != null && arrn.length != 0) {
            int n = activityClientRecord.getLifecycleState();
            int n2 = -1;
            int n3 = Integer.MAX_VALUE;
            for (int i = 0; i < arrn.length; ++i) {
                int n4;
                this.getLifecyclePath(n, arrn[i], false);
                int n5 = n4 = this.mLifecycleSequence.size();
                if (TransactionExecutorHelper.pathInvolvesDestruction(this.mLifecycleSequence)) {
                    n5 = n4 + 10;
                }
                n4 = n3;
                if (n3 > n5) {
                    n2 = arrn[i];
                    n4 = n5;
                }
                n3 = n4;
            }
            return n2;
        }
        return -1;
    }

    @VisibleForTesting
    public int getClosestPreExecutionState(ActivityThread.ActivityClientRecord object, int n) {
        if (n != -1) {
            if (n == 3) {
                return this.getClosestOfStates((ActivityThread.ActivityClientRecord)object, ON_RESUME_PRE_EXCUTION_STATES);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Pre-execution states for state: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" is not supported.");
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        return -1;
    }

    @VisibleForTesting
    public IntArray getLifecyclePath(int n, int n2, boolean bl) {
        if (n != -1 && n2 != -1) {
            if (n != 7 && n2 != 7) {
                if (n2 == 0 && n != n2) {
                    throw new IllegalArgumentException("Can only start in pre-onCreate state");
                }
                this.mLifecycleSequence.clear();
                if (n2 >= n) {
                    ++n;
                    while (n <= n2) {
                        this.mLifecycleSequence.add(n);
                        ++n;
                    }
                } else if (n == 4 && n2 == 3) {
                    this.mLifecycleSequence.add(3);
                } else if (n <= 5 && n2 >= 2) {
                    ++n;
                    while (n <= 5) {
                        this.mLifecycleSequence.add(n);
                        ++n;
                    }
                    this.mLifecycleSequence.add(7);
                    for (n = 2; n <= n2; ++n) {
                        this.mLifecycleSequence.add(n);
                    }
                } else {
                    ++n;
                    while (n <= 6) {
                        this.mLifecycleSequence.add(n);
                        ++n;
                    }
                    for (n = 1; n <= n2; ++n) {
                        this.mLifecycleSequence.add(n);
                    }
                }
                if (bl && this.mLifecycleSequence.size() != 0) {
                    IntArray intArray = this.mLifecycleSequence;
                    intArray.remove(intArray.size() - 1);
                }
                return this.mLifecycleSequence;
            }
            throw new IllegalArgumentException("Can't start or finish in intermittent RESTART state");
        }
        throw new IllegalArgumentException("Can't resolve lifecycle path for undefined state");
    }
}

