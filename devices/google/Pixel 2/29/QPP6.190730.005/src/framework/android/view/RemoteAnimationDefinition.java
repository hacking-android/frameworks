/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.app.WindowConfiguration;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.SparseArray;
import android.view.RemoteAnimationAdapter;

public class RemoteAnimationDefinition
implements Parcelable {
    public static final Parcelable.Creator<RemoteAnimationDefinition> CREATOR = new Parcelable.Creator<RemoteAnimationDefinition>(){

        @Override
        public RemoteAnimationDefinition createFromParcel(Parcel parcel) {
            return new RemoteAnimationDefinition(parcel);
        }

        public RemoteAnimationDefinition[] newArray(int n) {
            return new RemoteAnimationDefinition[n];
        }
    };
    private final SparseArray<RemoteAnimationAdapterEntry> mTransitionAnimationMap;

    @UnsupportedAppUsage
    public RemoteAnimationDefinition() {
        this.mTransitionAnimationMap = new SparseArray();
    }

    public RemoteAnimationDefinition(Parcel parcel) {
        int n = parcel.readInt();
        this.mTransitionAnimationMap = new SparseArray(n);
        for (int i = 0; i < n; ++i) {
            int n2 = parcel.readInt();
            RemoteAnimationAdapterEntry remoteAnimationAdapterEntry = (RemoteAnimationAdapterEntry)parcel.readTypedObject(RemoteAnimationAdapterEntry.CREATOR);
            this.mTransitionAnimationMap.put(n2, remoteAnimationAdapterEntry);
        }
    }

    @UnsupportedAppUsage
    public void addRemoteAnimation(int n, @WindowConfiguration.ActivityType int n2, RemoteAnimationAdapter remoteAnimationAdapter) {
        this.mTransitionAnimationMap.put(n, new RemoteAnimationAdapterEntry(remoteAnimationAdapter, n2));
    }

    @UnsupportedAppUsage
    public void addRemoteAnimation(int n, RemoteAnimationAdapter remoteAnimationAdapter) {
        this.addRemoteAnimation(n, 0, remoteAnimationAdapter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public RemoteAnimationAdapter getAdapter(int n, ArraySet<Integer> arraySet) {
        RemoteAnimationAdapterEntry remoteAnimationAdapterEntry = this.mTransitionAnimationMap.get(n);
        if (remoteAnimationAdapterEntry == null) {
            return null;
        }
        if (remoteAnimationAdapterEntry.activityTypeFilter != 0 && !arraySet.contains(remoteAnimationAdapterEntry.activityTypeFilter)) {
            return null;
        }
        return remoteAnimationAdapterEntry.adapter;
    }

    public boolean hasTransition(int n, ArraySet<Integer> arraySet) {
        boolean bl = this.getAdapter(n, arraySet) != null;
        return bl;
    }

    public void setCallingPid(int n) {
        for (int i = this.mTransitionAnimationMap.size() - 1; i >= 0; --i) {
            this.mTransitionAnimationMap.valueAt((int)i).adapter.setCallingPid(n);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        int n2 = this.mTransitionAnimationMap.size();
        parcel.writeInt(n2);
        for (int i = 0; i < n2; ++i) {
            parcel.writeInt(this.mTransitionAnimationMap.keyAt(i));
            parcel.writeTypedObject(this.mTransitionAnimationMap.valueAt(i), n);
        }
    }

    private static class RemoteAnimationAdapterEntry
    implements Parcelable {
        private static final Parcelable.Creator<RemoteAnimationAdapterEntry> CREATOR = new Parcelable.Creator<RemoteAnimationAdapterEntry>(){

            @Override
            public RemoteAnimationAdapterEntry createFromParcel(Parcel parcel) {
                return new RemoteAnimationAdapterEntry(parcel);
            }

            public RemoteAnimationAdapterEntry[] newArray(int n) {
                return new RemoteAnimationAdapterEntry[n];
            }
        };
        @WindowConfiguration.ActivityType
        final int activityTypeFilter;
        final RemoteAnimationAdapter adapter;

        private RemoteAnimationAdapterEntry(Parcel parcel) {
            this.adapter = (RemoteAnimationAdapter)parcel.readParcelable(RemoteAnimationAdapter.class.getClassLoader());
            this.activityTypeFilter = parcel.readInt();
        }

        RemoteAnimationAdapterEntry(RemoteAnimationAdapter remoteAnimationAdapter, int n) {
            this.adapter = remoteAnimationAdapter;
            this.activityTypeFilter = n;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeParcelable(this.adapter, n);
            parcel.writeInt(this.activityTypeFilter);
        }

    }

}

