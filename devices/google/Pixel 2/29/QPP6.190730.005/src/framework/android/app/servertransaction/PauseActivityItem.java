/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ActivityTaskManager;
import android.app.ClientTransactionHandler;
import android.app.servertransaction.ActivityLifecycleItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.Trace;

public class PauseActivityItem
extends ActivityLifecycleItem {
    public static final Parcelable.Creator<PauseActivityItem> CREATOR = new Parcelable.Creator<PauseActivityItem>(){

        @Override
        public PauseActivityItem createFromParcel(Parcel parcel) {
            return new PauseActivityItem(parcel);
        }

        public PauseActivityItem[] newArray(int n) {
            return new PauseActivityItem[n];
        }
    };
    private static final String TAG = "PauseActivityItem";
    private int mConfigChanges;
    private boolean mDontReport;
    private boolean mFinished;
    private boolean mUserLeaving;

    private PauseActivityItem() {
    }

    private PauseActivityItem(Parcel parcel) {
        this.mFinished = parcel.readBoolean();
        this.mUserLeaving = parcel.readBoolean();
        this.mConfigChanges = parcel.readInt();
        this.mDontReport = parcel.readBoolean();
    }

    public static PauseActivityItem obtain() {
        PauseActivityItem pauseActivityItem;
        PauseActivityItem pauseActivityItem2 = pauseActivityItem = ObjectPool.obtain(PauseActivityItem.class);
        if (pauseActivityItem == null) {
            pauseActivityItem2 = new PauseActivityItem();
        }
        pauseActivityItem2.mFinished = false;
        pauseActivityItem2.mUserLeaving = false;
        pauseActivityItem2.mConfigChanges = 0;
        pauseActivityItem2.mDontReport = true;
        return pauseActivityItem2;
    }

    public static PauseActivityItem obtain(boolean bl, boolean bl2, int n, boolean bl3) {
        PauseActivityItem pauseActivityItem;
        PauseActivityItem pauseActivityItem2 = pauseActivityItem = ObjectPool.obtain(PauseActivityItem.class);
        if (pauseActivityItem == null) {
            pauseActivityItem2 = new PauseActivityItem();
        }
        pauseActivityItem2.mFinished = bl;
        pauseActivityItem2.mUserLeaving = bl2;
        pauseActivityItem2.mConfigChanges = n;
        pauseActivityItem2.mDontReport = bl3;
        return pauseActivityItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (PauseActivityItem)object;
            if (this.mFinished != ((PauseActivityItem)object).mFinished || this.mUserLeaving != ((PauseActivityItem)object).mUserLeaving || this.mConfigChanges != ((PauseActivityItem)object).mConfigChanges || this.mDontReport != ((PauseActivityItem)object).mDontReport) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityPause");
        clientTransactionHandler.handlePauseActivity(iBinder, this.mFinished, this.mUserLeaving, this.mConfigChanges, pendingTransactionActions, "PAUSE_ACTIVITY_ITEM");
        Trace.traceEnd(64L);
    }

    @Override
    public int getTargetState() {
        return 4;
    }

    public int hashCode() {
        return (((17 * 31 + this.mFinished) * 31 + this.mUserLeaving) * 31 + this.mConfigChanges) * 31 + this.mDontReport;
    }

    @Override
    public void postExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        if (this.mDontReport) {
            return;
        }
        try {
            ActivityTaskManager.getService().activityPaused(iBinder);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void recycle() {
        super.recycle();
        this.mFinished = false;
        this.mUserLeaving = false;
        this.mConfigChanges = 0;
        this.mDontReport = false;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PauseActivityItem{finished=");
        stringBuilder.append(this.mFinished);
        stringBuilder.append(",userLeaving=");
        stringBuilder.append(this.mUserLeaving);
        stringBuilder.append(",configChanges=");
        stringBuilder.append(this.mConfigChanges);
        stringBuilder.append(",dontReport=");
        stringBuilder.append(this.mDontReport);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.mFinished);
        parcel.writeBoolean(this.mUserLeaving);
        parcel.writeInt(this.mConfigChanges);
        parcel.writeBoolean(this.mDontReport);
    }

}

