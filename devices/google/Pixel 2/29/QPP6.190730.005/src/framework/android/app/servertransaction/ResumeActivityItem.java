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

public class ResumeActivityItem
extends ActivityLifecycleItem {
    public static final Parcelable.Creator<ResumeActivityItem> CREATOR = new Parcelable.Creator<ResumeActivityItem>(){

        @Override
        public ResumeActivityItem createFromParcel(Parcel parcel) {
            return new ResumeActivityItem(parcel);
        }

        public ResumeActivityItem[] newArray(int n) {
            return new ResumeActivityItem[n];
        }
    };
    private static final String TAG = "ResumeActivityItem";
    private boolean mIsForward;
    private int mProcState;
    private boolean mUpdateProcState;

    private ResumeActivityItem() {
    }

    private ResumeActivityItem(Parcel parcel) {
        this.mProcState = parcel.readInt();
        this.mUpdateProcState = parcel.readBoolean();
        this.mIsForward = parcel.readBoolean();
    }

    public static ResumeActivityItem obtain(int n, boolean bl) {
        ResumeActivityItem resumeActivityItem;
        ResumeActivityItem resumeActivityItem2 = resumeActivityItem = ObjectPool.obtain(ResumeActivityItem.class);
        if (resumeActivityItem == null) {
            resumeActivityItem2 = new ResumeActivityItem();
        }
        resumeActivityItem2.mProcState = n;
        resumeActivityItem2.mUpdateProcState = true;
        resumeActivityItem2.mIsForward = bl;
        return resumeActivityItem2;
    }

    public static ResumeActivityItem obtain(boolean bl) {
        ResumeActivityItem resumeActivityItem;
        ResumeActivityItem resumeActivityItem2 = resumeActivityItem = ObjectPool.obtain(ResumeActivityItem.class);
        if (resumeActivityItem == null) {
            resumeActivityItem2 = new ResumeActivityItem();
        }
        resumeActivityItem2.mProcState = -1;
        resumeActivityItem2.mUpdateProcState = false;
        resumeActivityItem2.mIsForward = bl;
        return resumeActivityItem2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (ResumeActivityItem)object;
            if (this.mProcState != ((ResumeActivityItem)object).mProcState || this.mUpdateProcState != ((ResumeActivityItem)object).mUpdateProcState || this.mIsForward != ((ResumeActivityItem)object).mIsForward) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityResume");
        clientTransactionHandler.handleResumeActivity(iBinder, true, this.mIsForward, "RESUME_ACTIVITY");
        Trace.traceEnd(64L);
    }

    @Override
    public int getTargetState() {
        return 3;
    }

    public int hashCode() {
        return ((17 * 31 + this.mProcState) * 31 + this.mUpdateProcState) * 31 + this.mIsForward;
    }

    @Override
    public void postExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        try {
            ActivityTaskManager.getService().activityResumed(iBinder);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void preExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder) {
        if (this.mUpdateProcState) {
            clientTransactionHandler.updateProcessState(this.mProcState, false);
        }
    }

    @Override
    public void recycle() {
        super.recycle();
        this.mProcState = -1;
        this.mUpdateProcState = false;
        this.mIsForward = false;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ResumeActivityItem{procState=");
        stringBuilder.append(this.mProcState);
        stringBuilder.append(",updateProcState=");
        stringBuilder.append(this.mUpdateProcState);
        stringBuilder.append(",isForward=");
        stringBuilder.append(this.mIsForward);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mProcState);
        parcel.writeBoolean(this.mUpdateProcState);
        parcel.writeBoolean(this.mIsForward);
    }

}

