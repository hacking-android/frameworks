/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;

public class StopActivityItem
extends ActivityLifecycleItem {
    public static final Parcelable.Creator<StopActivityItem> CREATOR = new Parcelable.Creator<StopActivityItem>(){

        @Override
        public StopActivityItem createFromParcel(Parcel parcel) {
            return new StopActivityItem(parcel);
        }

        public StopActivityItem[] newArray(int n) {
            return new StopActivityItem[n];
        }
    };
    private static final String TAG = "StopActivityItem";
    private int mConfigChanges;
    private boolean mShowWindow;

    private StopActivityItem() {
    }

    private StopActivityItem(Parcel parcel) {
        this.mShowWindow = parcel.readBoolean();
        this.mConfigChanges = parcel.readInt();
    }

    public static StopActivityItem obtain(boolean bl, int n) {
        StopActivityItem stopActivityItem;
        StopActivityItem stopActivityItem2 = stopActivityItem = ObjectPool.obtain(StopActivityItem.class);
        if (stopActivityItem == null) {
            stopActivityItem2 = new StopActivityItem();
        }
        stopActivityItem2.mShowWindow = bl;
        stopActivityItem2.mConfigChanges = n;
        return stopActivityItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (StopActivityItem)object;
            if (this.mShowWindow != ((StopActivityItem)object).mShowWindow || this.mConfigChanges != ((StopActivityItem)object).mConfigChanges) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityStop");
        clientTransactionHandler.handleStopActivity(iBinder, this.mShowWindow, this.mConfigChanges, pendingTransactionActions, true, "STOP_ACTIVITY_ITEM");
        Trace.traceEnd(64L);
    }

    @Override
    public int getTargetState() {
        return 5;
    }

    public int hashCode() {
        return (17 * 31 + this.mShowWindow) * 31 + this.mConfigChanges;
    }

    @Override
    public void postExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.reportStop(pendingTransactionActions);
    }

    @Override
    public void recycle() {
        super.recycle();
        this.mShowWindow = false;
        this.mConfigChanges = 0;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("StopActivityItem{showWindow=");
        stringBuilder.append(this.mShowWindow);
        stringBuilder.append(",configChanges=");
        stringBuilder.append(this.mConfigChanges);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.mShowWindow);
        parcel.writeInt(this.mConfigChanges);
    }

}

