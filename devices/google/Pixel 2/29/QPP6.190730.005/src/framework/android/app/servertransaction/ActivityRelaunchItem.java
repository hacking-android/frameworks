/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.app.ResultInfo;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import android.util.MergedConfiguration;
import com.android.internal.content.ReferrerIntent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivityRelaunchItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<ActivityRelaunchItem> CREATOR = new Parcelable.Creator<ActivityRelaunchItem>(){

        @Override
        public ActivityRelaunchItem createFromParcel(Parcel parcel) {
            return new ActivityRelaunchItem(parcel);
        }

        public ActivityRelaunchItem[] newArray(int n) {
            return new ActivityRelaunchItem[n];
        }
    };
    private static final String TAG = "ActivityRelaunchItem";
    private ActivityThread.ActivityClientRecord mActivityClientRecord;
    private MergedConfiguration mConfig;
    private int mConfigChanges;
    private List<ReferrerIntent> mPendingNewIntents;
    private List<ResultInfo> mPendingResults;
    private boolean mPreserveWindow;

    private ActivityRelaunchItem() {
    }

    private ActivityRelaunchItem(Parcel parcel) {
        this.mPendingResults = parcel.createTypedArrayList(ResultInfo.CREATOR);
        this.mPendingNewIntents = parcel.createTypedArrayList(ReferrerIntent.CREATOR);
        this.mConfigChanges = parcel.readInt();
        this.mConfig = parcel.readTypedObject(MergedConfiguration.CREATOR);
        this.mPreserveWindow = parcel.readBoolean();
    }

    public static ActivityRelaunchItem obtain(List<ResultInfo> list, List<ReferrerIntent> list2, int n, MergedConfiguration mergedConfiguration, boolean bl) {
        ActivityRelaunchItem activityRelaunchItem;
        ActivityRelaunchItem activityRelaunchItem2 = activityRelaunchItem = ObjectPool.obtain(ActivityRelaunchItem.class);
        if (activityRelaunchItem == null) {
            activityRelaunchItem2 = new ActivityRelaunchItem();
        }
        activityRelaunchItem2.mPendingResults = list;
        activityRelaunchItem2.mPendingNewIntents = list2;
        activityRelaunchItem2.mConfigChanges = n;
        activityRelaunchItem2.mConfig = mergedConfiguration;
        activityRelaunchItem2.mPreserveWindow = bl;
        return activityRelaunchItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (ActivityRelaunchItem)object;
            if (!(Objects.equals(this.mPendingResults, ((ActivityRelaunchItem)object).mPendingResults) && Objects.equals(this.mPendingNewIntents, ((ActivityRelaunchItem)object).mPendingNewIntents) && this.mConfigChanges == ((ActivityRelaunchItem)object).mConfigChanges && Objects.equals(this.mConfig, ((ActivityRelaunchItem)object).mConfig) && this.mPreserveWindow == ((ActivityRelaunchItem)object).mPreserveWindow)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        if (this.mActivityClientRecord == null) {
            return;
        }
        Trace.traceBegin(64L, "activityRestart");
        clientTransactionHandler.handleRelaunchActivity(this.mActivityClientRecord, pendingTransactionActions);
        Trace.traceEnd(64L);
    }

    public int hashCode() {
        return ((((17 * 31 + Objects.hashCode(this.mPendingResults)) * 31 + Objects.hashCode(this.mPendingNewIntents)) * 31 + this.mConfigChanges) * 31 + Objects.hashCode(this.mConfig)) * 31 + this.mPreserveWindow;
    }

    @Override
    public void postExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.reportRelaunch(iBinder, pendingTransactionActions);
    }

    @Override
    public void preExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder) {
        this.mActivityClientRecord = clientTransactionHandler.prepareRelaunchActivity(iBinder, this.mPendingResults, this.mPendingNewIntents, this.mConfigChanges, this.mConfig, this.mPreserveWindow);
    }

    @Override
    public void recycle() {
        this.mPendingResults = null;
        this.mPendingNewIntents = null;
        this.mConfigChanges = 0;
        this.mConfig = null;
        this.mPreserveWindow = false;
        this.mActivityClientRecord = null;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ActivityRelaunchItem{pendingResults=");
        stringBuilder.append(this.mPendingResults);
        stringBuilder.append(",pendingNewIntents=");
        stringBuilder.append(this.mPendingNewIntents);
        stringBuilder.append(",configChanges=");
        stringBuilder.append(this.mConfigChanges);
        stringBuilder.append(",config=");
        stringBuilder.append(this.mConfig);
        stringBuilder.append(",preserveWindow");
        stringBuilder.append(this.mPreserveWindow);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedList(this.mPendingResults, n);
        parcel.writeTypedList(this.mPendingNewIntents, n);
        parcel.writeInt(this.mConfigChanges);
        parcel.writeTypedObject(this.mConfig, n);
        parcel.writeBoolean(this.mPreserveWindow);
    }

}

