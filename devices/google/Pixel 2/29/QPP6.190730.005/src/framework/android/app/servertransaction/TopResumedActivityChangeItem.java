/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ActivityTaskManager;
import android.app.ClientTransactionHandler;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.Trace;

public class TopResumedActivityChangeItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<TopResumedActivityChangeItem> CREATOR = new Parcelable.Creator<TopResumedActivityChangeItem>(){

        @Override
        public TopResumedActivityChangeItem createFromParcel(Parcel parcel) {
            return new TopResumedActivityChangeItem(parcel);
        }

        public TopResumedActivityChangeItem[] newArray(int n) {
            return new TopResumedActivityChangeItem[n];
        }
    };
    private boolean mOnTop;

    private TopResumedActivityChangeItem() {
    }

    private TopResumedActivityChangeItem(Parcel parcel) {
        this.mOnTop = parcel.readBoolean();
    }

    public static TopResumedActivityChangeItem obtain(boolean bl) {
        TopResumedActivityChangeItem topResumedActivityChangeItem;
        TopResumedActivityChangeItem topResumedActivityChangeItem2 = topResumedActivityChangeItem = ObjectPool.obtain(TopResumedActivityChangeItem.class);
        if (topResumedActivityChangeItem == null) {
            topResumedActivityChangeItem2 = new TopResumedActivityChangeItem();
        }
        topResumedActivityChangeItem2.mOnTop = bl;
        return topResumedActivityChangeItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (TopResumedActivityChangeItem)object;
            if (this.mOnTop != ((TopResumedActivityChangeItem)object).mOnTop) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "topResumedActivityChangeItem");
        clientTransactionHandler.handleTopResumedActivityChanged(iBinder, this.mOnTop, "topResumedActivityChangeItem");
        Trace.traceEnd(64L);
    }

    public int hashCode() {
        return 17 * 31 + this.mOnTop;
    }

    @Override
    public void postExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        if (this.mOnTop) {
            return;
        }
        try {
            ActivityTaskManager.getService().activityTopResumedStateLost();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void recycle() {
        this.mOnTop = false;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TopResumedActivityChangeItem{onTop=");
        stringBuilder.append(this.mOnTop);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.mOnTop);
    }

}

