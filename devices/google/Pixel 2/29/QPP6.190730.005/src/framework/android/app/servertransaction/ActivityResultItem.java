/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.annotation.UnsupportedAppUsage;
import android.app.ClientTransactionHandler;
import android.app.ResultInfo;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivityResultItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<ActivityResultItem> CREATOR = new Parcelable.Creator<ActivityResultItem>(){

        @Override
        public ActivityResultItem createFromParcel(Parcel parcel) {
            return new ActivityResultItem(parcel);
        }

        public ActivityResultItem[] newArray(int n) {
            return new ActivityResultItem[n];
        }
    };
    @UnsupportedAppUsage
    private List<ResultInfo> mResultInfoList;

    private ActivityResultItem() {
    }

    private ActivityResultItem(Parcel parcel) {
        this.mResultInfoList = parcel.createTypedArrayList(ResultInfo.CREATOR);
    }

    public static ActivityResultItem obtain(List<ResultInfo> list) {
        ActivityResultItem activityResultItem;
        ActivityResultItem activityResultItem2 = activityResultItem = ObjectPool.obtain(ActivityResultItem.class);
        if (activityResultItem == null) {
            activityResultItem2 = new ActivityResultItem();
        }
        activityResultItem2.mResultInfoList = list;
        return activityResultItem2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (ActivityResultItem)object;
            return Objects.equals(this.mResultInfoList, ((ActivityResultItem)object).mResultInfoList);
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityDeliverResult");
        clientTransactionHandler.handleSendResult(iBinder, this.mResultInfoList, "ACTIVITY_RESULT");
        Trace.traceEnd(64L);
    }

    public int hashCode() {
        return this.mResultInfoList.hashCode();
    }

    @Override
    public void recycle() {
        this.mResultInfoList = null;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ActivityResultItem{resultInfoList=");
        stringBuilder.append(this.mResultInfoList);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedList(this.mResultInfoList, n);
    }

}

