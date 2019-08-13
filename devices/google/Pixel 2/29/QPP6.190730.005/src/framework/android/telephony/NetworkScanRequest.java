/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.RadioAccessSpecifier;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class NetworkScanRequest
implements Parcelable {
    public static final Parcelable.Creator<NetworkScanRequest> CREATOR = new Parcelable.Creator<NetworkScanRequest>(){

        @Override
        public NetworkScanRequest createFromParcel(Parcel parcel) {
            return new NetworkScanRequest(parcel);
        }

        public NetworkScanRequest[] newArray(int n) {
            return new NetworkScanRequest[n];
        }
    };
    public static final int MAX_BANDS = 8;
    public static final int MAX_CHANNELS = 32;
    public static final int MAX_INCREMENTAL_PERIODICITY_SEC = 10;
    public static final int MAX_MCC_MNC_LIST_SIZE = 20;
    public static final int MAX_RADIO_ACCESS_NETWORKS = 8;
    public static final int MAX_SEARCH_MAX_SEC = 3600;
    public static final int MAX_SEARCH_PERIODICITY_SEC = 300;
    public static final int MIN_INCREMENTAL_PERIODICITY_SEC = 1;
    public static final int MIN_SEARCH_MAX_SEC = 60;
    public static final int MIN_SEARCH_PERIODICITY_SEC = 5;
    public static final int SCAN_TYPE_ONE_SHOT = 0;
    public static final int SCAN_TYPE_PERIODIC = 1;
    private boolean mIncrementalResults;
    private int mIncrementalResultsPeriodicity;
    private int mMaxSearchTime;
    private ArrayList<String> mMccMncs;
    private int mScanType;
    private int mSearchPeriodicity;
    private RadioAccessSpecifier[] mSpecifiers;

    public NetworkScanRequest(int n, RadioAccessSpecifier[] arrradioAccessSpecifier, int n2, int n3, boolean bl, int n4, ArrayList<String> arrayList) {
        this.mScanType = n;
        this.mSpecifiers = arrradioAccessSpecifier != null ? (RadioAccessSpecifier[])arrradioAccessSpecifier.clone() : null;
        this.mSearchPeriodicity = n2;
        this.mMaxSearchTime = n3;
        this.mIncrementalResults = bl;
        this.mIncrementalResultsPeriodicity = n4;
        this.mMccMncs = arrayList != null ? (ArrayList)arrayList.clone() : new ArrayList();
    }

    private NetworkScanRequest(Parcel parcel) {
        this.mScanType = parcel.readInt();
        this.mSpecifiers = (RadioAccessSpecifier[])parcel.readParcelableArray(Object.class.getClassLoader(), RadioAccessSpecifier.class);
        this.mSearchPeriodicity = parcel.readInt();
        this.mMaxSearchTime = parcel.readInt();
        this.mIncrementalResults = parcel.readBoolean();
        this.mIncrementalResultsPeriodicity = parcel.readInt();
        this.mMccMncs = new ArrayList();
        parcel.readStringList(this.mMccMncs);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object arrayList) {
        NetworkScanRequest networkScanRequest;
        boolean bl;
        block3 : {
            bl = false;
            try {
                networkScanRequest = (NetworkScanRequest)((Object)arrayList);
                if (arrayList != null) break block3;
                return false;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }
        if (this.mScanType == networkScanRequest.mScanType && Arrays.equals(this.mSpecifiers, networkScanRequest.mSpecifiers) && this.mSearchPeriodicity == networkScanRequest.mSearchPeriodicity && this.mMaxSearchTime == networkScanRequest.mMaxSearchTime && this.mIncrementalResults == networkScanRequest.mIncrementalResults && this.mIncrementalResultsPeriodicity == networkScanRequest.mIncrementalResultsPeriodicity && (arrayList = this.mMccMncs) != null && arrayList.equals(networkScanRequest.mMccMncs)) {
            bl = true;
        }
        return bl;
    }

    public boolean getIncrementalResults() {
        return this.mIncrementalResults;
    }

    public int getIncrementalResultsPeriodicity() {
        return this.mIncrementalResultsPeriodicity;
    }

    public int getMaxSearchTime() {
        return this.mMaxSearchTime;
    }

    public ArrayList<String> getPlmns() {
        return (ArrayList)this.mMccMncs.clone();
    }

    public int getScanType() {
        return this.mScanType;
    }

    public int getSearchPeriodicity() {
        return this.mSearchPeriodicity;
    }

    public RadioAccessSpecifier[] getSpecifiers() {
        Object object = this.mSpecifiers;
        object = object == null ? null : (RadioAccessSpecifier[])object.clone();
        return object;
    }

    public int hashCode() {
        int n = this.mScanType;
        int n2 = Arrays.hashCode(this.mSpecifiers);
        int n3 = this.mSearchPeriodicity;
        int n4 = this.mMaxSearchTime;
        boolean bl = this.mIncrementalResults;
        int n5 = 1;
        if (!bl) {
            n5 = 0;
        }
        return n * 31 + n2 * 37 + n3 * 41 + n4 * 43 + n5 * 47 + this.mIncrementalResultsPeriodicity * 53 + this.mMccMncs.hashCode() * 59;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mScanType);
        parcel.writeParcelableArray((Parcelable[])this.mSpecifiers, n);
        parcel.writeInt(this.mSearchPeriodicity);
        parcel.writeInt(this.mMaxSearchTime);
        parcel.writeBoolean(this.mIncrementalResults);
        parcel.writeInt(this.mIncrementalResultsPeriodicity);
        parcel.writeStringList(this.mMccMncs);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ScanType {
    }

}

