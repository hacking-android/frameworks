/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.IRemoteAnimationRunner;

public class RemoteAnimationAdapter
implements Parcelable {
    public static final Parcelable.Creator<RemoteAnimationAdapter> CREATOR = new Parcelable.Creator<RemoteAnimationAdapter>(){

        @Override
        public RemoteAnimationAdapter createFromParcel(Parcel parcel) {
            return new RemoteAnimationAdapter(parcel);
        }

        public RemoteAnimationAdapter[] newArray(int n) {
            return new RemoteAnimationAdapter[n];
        }
    };
    private int mCallingPid;
    private final boolean mChangeNeedsSnapshot;
    private final long mDuration;
    private final IRemoteAnimationRunner mRunner;
    private final long mStatusBarTransitionDelay;

    public RemoteAnimationAdapter(Parcel parcel) {
        this.mRunner = IRemoteAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
        this.mDuration = parcel.readLong();
        this.mStatusBarTransitionDelay = parcel.readLong();
        this.mChangeNeedsSnapshot = parcel.readBoolean();
    }

    @UnsupportedAppUsage
    public RemoteAnimationAdapter(IRemoteAnimationRunner iRemoteAnimationRunner, long l, long l2) {
        this(iRemoteAnimationRunner, l, l2, false);
    }

    @UnsupportedAppUsage
    public RemoteAnimationAdapter(IRemoteAnimationRunner iRemoteAnimationRunner, long l, long l2, boolean bl) {
        this.mRunner = iRemoteAnimationRunner;
        this.mDuration = l;
        this.mChangeNeedsSnapshot = bl;
        this.mStatusBarTransitionDelay = l2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCallingPid() {
        return this.mCallingPid;
    }

    public boolean getChangeNeedsSnapshot() {
        return this.mChangeNeedsSnapshot;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public IRemoteAnimationRunner getRunner() {
        return this.mRunner;
    }

    public long getStatusBarTransitionDelay() {
        return this.mStatusBarTransitionDelay;
    }

    public void setCallingPid(int n) {
        this.mCallingPid = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongInterface(this.mRunner);
        parcel.writeLong(this.mDuration);
        parcel.writeLong(this.mStatusBarTransitionDelay);
        parcel.writeBoolean(this.mChangeNeedsSnapshot);
    }

}

