/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class AvailableNetworkInfo
implements Parcelable {
    public static final Parcelable.Creator<AvailableNetworkInfo> CREATOR = new Parcelable.Creator<AvailableNetworkInfo>(){

        @Override
        public AvailableNetworkInfo createFromParcel(Parcel parcel) {
            return new AvailableNetworkInfo(parcel);
        }

        public AvailableNetworkInfo[] newArray(int n) {
            return new AvailableNetworkInfo[n];
        }
    };
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = 3;
    public static final int PRIORITY_MED = 2;
    private ArrayList<Integer> mBands;
    private ArrayList<String> mMccMncs;
    private int mPriority;
    private int mSubId;

    public AvailableNetworkInfo(int n, int n2, List<String> list, List<Integer> list2) {
        this.mSubId = n;
        this.mPriority = n2;
        this.mMccMncs = new ArrayList<String>(list);
        this.mBands = new ArrayList<Integer>(list2);
    }

    private AvailableNetworkInfo(Parcel parcel) {
        this.mSubId = parcel.readInt();
        this.mPriority = parcel.readInt();
        this.mMccMncs = new ArrayList();
        parcel.readStringList(this.mMccMncs);
        this.mBands = new ArrayList();
        parcel.readList(this.mBands, Integer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object arrayList) {
        boolean bl;
        AvailableNetworkInfo availableNetworkInfo;
        block3 : {
            bl = false;
            try {
                availableNetworkInfo = (AvailableNetworkInfo)((Object)arrayList);
                if (arrayList != null) break block3;
                return false;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }
        if (this.mSubId == availableNetworkInfo.mSubId && this.mPriority == availableNetworkInfo.mPriority && (arrayList = this.mMccMncs) != null && arrayList.equals(availableNetworkInfo.mMccMncs) && this.mBands.equals(availableNetworkInfo.mBands)) {
            bl = true;
        }
        return bl;
    }

    public List<Integer> getBands() {
        return (List)this.mBands.clone();
    }

    public List<String> getMccMncs() {
        return (List)this.mMccMncs.clone();
    }

    public int getPriority() {
        return this.mPriority;
    }

    public int getSubId() {
        return this.mSubId;
    }

    public int hashCode() {
        return Objects.hash(this.mSubId, this.mPriority, this.mMccMncs, this.mBands);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AvailableNetworkInfo: mSubId: ");
        stringBuilder.append(this.mSubId);
        stringBuilder.append(" mPriority: ");
        stringBuilder.append(this.mPriority);
        stringBuilder.append(" mMccMncs: ");
        stringBuilder.append(Arrays.toString(this.mMccMncs.toArray()));
        stringBuilder.append(" mBands: ");
        stringBuilder.append(Arrays.toString(this.mBands.toArray()));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSubId);
        parcel.writeInt(this.mPriority);
        parcel.writeStringList(this.mMccMncs);
        parcel.writeList(this.mBands);
    }

}

