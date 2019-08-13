/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import java.util.Map;

public class DestroyActivityItem
extends ActivityLifecycleItem {
    public static final Parcelable.Creator<DestroyActivityItem> CREATOR = new Parcelable.Creator<DestroyActivityItem>(){

        @Override
        public DestroyActivityItem createFromParcel(Parcel parcel) {
            return new DestroyActivityItem(parcel);
        }

        public DestroyActivityItem[] newArray(int n) {
            return new DestroyActivityItem[n];
        }
    };
    private int mConfigChanges;
    private boolean mFinished;

    private DestroyActivityItem() {
    }

    private DestroyActivityItem(Parcel parcel) {
        this.mFinished = parcel.readBoolean();
        this.mConfigChanges = parcel.readInt();
    }

    public static DestroyActivityItem obtain(boolean bl, int n) {
        DestroyActivityItem destroyActivityItem;
        DestroyActivityItem destroyActivityItem2 = destroyActivityItem = ObjectPool.obtain(DestroyActivityItem.class);
        if (destroyActivityItem == null) {
            destroyActivityItem2 = new DestroyActivityItem();
        }
        destroyActivityItem2.mFinished = bl;
        destroyActivityItem2.mConfigChanges = n;
        return destroyActivityItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (DestroyActivityItem)object;
            if (this.mFinished != ((DestroyActivityItem)object).mFinished || this.mConfigChanges != ((DestroyActivityItem)object).mConfigChanges) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityDestroy");
        clientTransactionHandler.handleDestroyActivity(iBinder, this.mFinished, this.mConfigChanges, false, "DestroyActivityItem");
        Trace.traceEnd(64L);
    }

    @Override
    public int getTargetState() {
        return 6;
    }

    public int hashCode() {
        return (17 * 31 + this.mFinished) * 31 + this.mConfigChanges;
    }

    @Override
    public void preExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder) {
        clientTransactionHandler.getActivitiesToBeDestroyed().put(iBinder, this);
    }

    @Override
    public void recycle() {
        super.recycle();
        this.mFinished = false;
        this.mConfigChanges = 0;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DestroyActivityItem{finished=");
        stringBuilder.append(this.mFinished);
        stringBuilder.append(",mConfigChanges=");
        stringBuilder.append(this.mConfigChanges);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.mFinished);
        parcel.writeInt(this.mConfigChanges);
    }

}

