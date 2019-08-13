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

public class ActivityConfigurationChangeItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<ActivityConfigurationChangeItem> CREATOR = new Parcelable.Creator<ActivityConfigurationChangeItem>(){

        @Override
        public ActivityConfigurationChangeItem createFromParcel(Parcel parcel) {
            return new ActivityConfigurationChangeItem(parcel);
        }

        public ActivityConfigurationChangeItem[] newArray(int n) {
            return new ActivityConfigurationChangeItem[n];
        }
    };
    private Configuration mConfiguration;

    private ActivityConfigurationChangeItem() {
    }

    private ActivityConfigurationChangeItem(Parcel parcel) {
        this.mConfiguration = parcel.readTypedObject(Configuration.CREATOR);
    }

    public static ActivityConfigurationChangeItem obtain(Configuration configuration) {
        ActivityConfigurationChangeItem activityConfigurationChangeItem;
        ActivityConfigurationChangeItem activityConfigurationChangeItem2 = activityConfigurationChangeItem = ObjectPool.obtain(ActivityConfigurationChangeItem.class);
        if (activityConfigurationChangeItem == null) {
            activityConfigurationChangeItem2 = new ActivityConfigurationChangeItem();
        }
        activityConfigurationChangeItem2.mConfiguration = configuration;
        return activityConfigurationChangeItem2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (ActivityConfigurationChangeItem)object;
            return Objects.equals(this.mConfiguration, ((ActivityConfigurationChangeItem)object).mConfiguration);
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityConfigChanged");
        clientTransactionHandler.handleActivityConfigurationChanged(iBinder, this.mConfiguration, -1);
        Trace.traceEnd(64L);
    }

    public int hashCode() {
        return this.mConfiguration.hashCode();
    }

    @Override
    public void preExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder) {
        clientTransactionHandler.updatePendingActivityConfiguration(iBinder, this.mConfiguration);
    }

    @Override
    public void recycle() {
        this.mConfiguration = null;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ActivityConfigurationChange{config=");
        stringBuilder.append(this.mConfiguration);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedObject(this.mConfiguration, n);
    }

}

