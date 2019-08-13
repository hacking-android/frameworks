/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.servertransaction.BaseClientRequest;
import android.os.Parcelable;

public abstract class ClientTransactionItem
implements BaseClientRequest,
Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    public int getPostExecutionState() {
        return -1;
    }
}

