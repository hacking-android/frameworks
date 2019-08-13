/*
 * Decompiled with CFR 0.145.
 */
package android.app.job;

import android.annotation.UnsupportedAppUsage;
import android.app.job.IJobCallback;
import android.app.job.JobWorkItem;
import android.content.ClipData;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;

public class JobParameters
implements Parcelable {
    public static final Parcelable.Creator<JobParameters> CREATOR = new Parcelable.Creator<JobParameters>(){

        @Override
        public JobParameters createFromParcel(Parcel parcel) {
            return new JobParameters(parcel);
        }

        public JobParameters[] newArray(int n) {
            return new JobParameters[n];
        }
    };
    public static final int REASON_CANCELED = 0;
    public static final int REASON_CONSTRAINTS_NOT_SATISFIED = 1;
    public static final int REASON_DEVICE_IDLE = 4;
    public static final int REASON_DEVICE_THERMAL = 5;
    public static final int REASON_PREEMPT = 2;
    public static final int REASON_TIMEOUT = 3;
    @UnsupportedAppUsage
    private final IBinder callback;
    private final ClipData clipData;
    private final int clipGrantFlags;
    private String debugStopReason;
    private final PersistableBundle extras;
    @UnsupportedAppUsage
    private final int jobId;
    private final String[] mTriggeredContentAuthorities;
    private final Uri[] mTriggeredContentUris;
    private final Network network;
    private final boolean overrideDeadlineExpired;
    private int stopReason;
    private final Bundle transientExtras;

    public JobParameters(IBinder iBinder, int n, PersistableBundle persistableBundle, Bundle bundle, ClipData clipData, int n2, boolean bl, Uri[] arruri, String[] arrstring, Network network) {
        this.jobId = n;
        this.extras = persistableBundle;
        this.transientExtras = bundle;
        this.clipData = clipData;
        this.clipGrantFlags = n2;
        this.callback = iBinder;
        this.overrideDeadlineExpired = bl;
        this.mTriggeredContentUris = arruri;
        this.mTriggeredContentAuthorities = arrstring;
        this.network = network;
    }

    private JobParameters(Parcel parcel) {
        this.jobId = parcel.readInt();
        this.extras = parcel.readPersistableBundle();
        this.transientExtras = parcel.readBundle();
        int n = parcel.readInt();
        boolean bl = false;
        if (n != 0) {
            this.clipData = ClipData.CREATOR.createFromParcel(parcel);
            this.clipGrantFlags = parcel.readInt();
        } else {
            this.clipData = null;
            this.clipGrantFlags = 0;
        }
        this.callback = parcel.readStrongBinder();
        if (parcel.readInt() == 1) {
            bl = true;
        }
        this.overrideDeadlineExpired = bl;
        this.mTriggeredContentUris = parcel.createTypedArray(Uri.CREATOR);
        this.mTriggeredContentAuthorities = parcel.createStringArray();
        this.network = parcel.readInt() != 0 ? Network.CREATOR.createFromParcel(parcel) : null;
        this.stopReason = parcel.readInt();
        this.debugStopReason = parcel.readString();
    }

    public static String getReasonName(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("unknown:");
                            stringBuilder.append(n);
                            return stringBuilder.toString();
                        }
                        return "device_idle";
                    }
                    return "timeout";
                }
                return "preempt";
            }
            return "constraints";
        }
        return "canceled";
    }

    public void completeWork(JobWorkItem jobWorkItem) {
        try {
            if (this.getCallback().completeWork(this.getJobId(), jobWorkItem.getWorkId())) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Given work is not active: ");
            stringBuilder.append(jobWorkItem);
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public JobWorkItem dequeueWork() {
        try {
            JobWorkItem jobWorkItem = this.getCallback().dequeueWork(this.getJobId());
            return jobWorkItem;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public IJobCallback getCallback() {
        return IJobCallback.Stub.asInterface(this.callback);
    }

    public ClipData getClipData() {
        return this.clipData;
    }

    public int getClipGrantFlags() {
        return this.clipGrantFlags;
    }

    public String getDebugStopReason() {
        return this.debugStopReason;
    }

    public PersistableBundle getExtras() {
        return this.extras;
    }

    public int getJobId() {
        return this.jobId;
    }

    public Network getNetwork() {
        return this.network;
    }

    public int getStopReason() {
        return this.stopReason;
    }

    public Bundle getTransientExtras() {
        return this.transientExtras;
    }

    public String[] getTriggeredContentAuthorities() {
        return this.mTriggeredContentAuthorities;
    }

    public Uri[] getTriggeredContentUris() {
        return this.mTriggeredContentUris;
    }

    public boolean isOverrideDeadlineExpired() {
        return this.overrideDeadlineExpired;
    }

    public void setStopReason(int n, String string2) {
        this.stopReason = n;
        this.debugStopReason = string2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.jobId);
        parcel.writePersistableBundle(this.extras);
        parcel.writeBundle(this.transientExtras);
        if (this.clipData != null) {
            parcel.writeInt(1);
            this.clipData.writeToParcel(parcel, n);
            parcel.writeInt(this.clipGrantFlags);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeStrongBinder(this.callback);
        parcel.writeInt((int)this.overrideDeadlineExpired);
        parcel.writeTypedArray((Parcelable[])this.mTriggeredContentUris, n);
        parcel.writeStringArray(this.mTriggeredContentAuthorities);
        if (this.network != null) {
            parcel.writeInt(1);
            this.network.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.stopReason);
        parcel.writeString(this.debugStopReason);
    }

}

