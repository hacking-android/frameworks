/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CellInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class NetworkScanResult
implements Parcelable {
    public static final Parcelable.Creator<NetworkScanResult> CREATOR = new Parcelable.Creator<NetworkScanResult>(){

        @Override
        public NetworkScanResult createFromParcel(Parcel parcel) {
            return new NetworkScanResult(parcel);
        }

        public NetworkScanResult[] newArray(int n) {
            return new NetworkScanResult[n];
        }
    };
    public static final int SCAN_STATUS_COMPLETE = 2;
    public static final int SCAN_STATUS_PARTIAL = 1;
    public List<CellInfo> networkInfos;
    public int scanError;
    public int scanStatus;

    public NetworkScanResult(int n, int n2, List<CellInfo> list) {
        this.scanStatus = n;
        this.scanError = n2;
        this.networkInfos = list;
    }

    private NetworkScanResult(Parcel parcel) {
        this.scanStatus = parcel.readInt();
        this.scanError = parcel.readInt();
        ArrayList<CellInfo> arrayList = new ArrayList<CellInfo>();
        parcel.readParcelableList(arrayList, Object.class.getClassLoader());
        this.networkInfos = arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        NetworkScanResult networkScanResult;
        boolean bl;
        block3 : {
            bl = false;
            try {
                networkScanResult = (NetworkScanResult)object;
                if (object != null) break block3;
                return false;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }
        if (this.scanStatus == networkScanResult.scanStatus && this.scanError == networkScanResult.scanError && this.networkInfos.equals(networkScanResult.networkInfos)) {
            bl = true;
        }
        return bl;
    }

    public int hashCode() {
        return this.scanStatus * 31 + this.scanError * 23 + Objects.hashCode(this.networkInfos) * 37;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("scanStatus=");
        stringBuilder2.append(this.scanStatus);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", scanError=");
        stringBuilder2.append(this.scanError);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", networkInfos=");
        stringBuilder2.append(this.networkInfos);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.scanStatus);
        parcel.writeInt(this.scanError);
        parcel.writeParcelableList(this.networkInfos, n);
    }

}

