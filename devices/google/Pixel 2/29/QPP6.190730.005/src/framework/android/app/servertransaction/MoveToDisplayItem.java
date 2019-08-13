/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import java.util.Objects;

public class MoveToDisplayItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<MoveToDisplayItem> CREATOR = new Parcelable.Creator<MoveToDisplayItem>(){

        @Override
        public MoveToDisplayItem createFromParcel(Parcel parcel) {
            return new MoveToDisplayItem(parcel);
        }

        public MoveToDisplayItem[] newArray(int n) {
            return new MoveToDisplayItem[n];
        }
    };
    private Configuration mConfiguration;
    private int mTargetDisplayId;

    private MoveToDisplayItem() {
    }

    private MoveToDisplayItem(Parcel parcel) {
        this.mTargetDisplayId = parcel.readInt();
        this.mConfiguration = parcel.readTypedObject(Configuration.CREATOR);
    }

    public static MoveToDisplayItem obtain(int n, Configuration configuration) {
        MoveToDisplayItem moveToDisplayItem;
        MoveToDisplayItem moveToDisplayItem2 = moveToDisplayItem = ObjectPool.obtain(MoveToDisplayItem.class);
        if (moveToDisplayItem == null) {
            moveToDisplayItem2 = new MoveToDisplayItem();
        }
        moveToDisplayItem2.mTargetDisplayId = n;
        moveToDisplayItem2.mConfiguration = configuration;
        return moveToDisplayItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (MoveToDisplayItem)object;
            if (this.mTargetDisplayId != ((MoveToDisplayItem)object).mTargetDisplayId || !Objects.equals(this.mConfiguration, ((MoveToDisplayItem)object).mConfiguration)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityMovedToDisplay");
        clientTransactionHandler.handleActivityConfigurationChanged(iBinder, this.mConfiguration, this.mTargetDisplayId);
        Trace.traceEnd(64L);
    }

    public int hashCode() {
        return (17 * 31 + this.mTargetDisplayId) * 31 + this.mConfiguration.hashCode();
    }

    @Override
    public void recycle() {
        this.mTargetDisplayId = 0;
        this.mConfiguration = null;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MoveToDisplayItem{targetDisplayId=");
        stringBuilder.append(this.mTargetDisplayId);
        stringBuilder.append(",configuration=");
        stringBuilder.append(this.mConfiguration);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mTargetDisplayId);
        parcel.writeTypedObject(this.mConfiguration, n);
    }

}

