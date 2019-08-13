/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SystemApi
public final class InstantAppIntentFilter
implements Parcelable {
    public static final Parcelable.Creator<InstantAppIntentFilter> CREATOR = new Parcelable.Creator<InstantAppIntentFilter>(){

        @Override
        public InstantAppIntentFilter createFromParcel(Parcel parcel) {
            return new InstantAppIntentFilter(parcel);
        }

        public InstantAppIntentFilter[] newArray(int n) {
            return new InstantAppIntentFilter[n];
        }
    };
    private final List<IntentFilter> mFilters = new ArrayList<IntentFilter>();
    private final String mSplitName;

    InstantAppIntentFilter(Parcel parcel) {
        this.mSplitName = parcel.readString();
        parcel.readList(this.mFilters, null);
    }

    public InstantAppIntentFilter(String string2, List<IntentFilter> list) {
        if (list != null && list.size() != 0) {
            this.mSplitName = string2;
            this.mFilters.addAll(list);
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<IntentFilter> getFilters() {
        return this.mFilters;
    }

    public String getSplitName() {
        return this.mSplitName;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mSplitName);
        parcel.writeList(this.mFilters);
    }

}

