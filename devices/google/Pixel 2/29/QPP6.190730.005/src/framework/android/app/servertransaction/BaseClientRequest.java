/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.app.servertransaction.ObjectPoolItem;
import android.app.servertransaction.PendingTransactionActions;
import android.os.IBinder;

public interface BaseClientRequest
extends ObjectPoolItem {
    public void execute(ClientTransactionHandler var1, IBinder var2, PendingTransactionActions var3);

    default public void postExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
    }

    default public void preExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder) {
    }
}

