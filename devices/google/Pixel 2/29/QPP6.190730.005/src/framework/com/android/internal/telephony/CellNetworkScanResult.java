/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.OperatorInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CellNetworkScanResult
implements Parcelable {
    public static final Parcelable.Creator<CellNetworkScanResult> CREATOR = new Parcelable.Creator<CellNetworkScanResult>(){

        @Override
        public CellNetworkScanResult createFromParcel(Parcel parcel) {
            return new CellNetworkScanResult(parcel);
        }

        public CellNetworkScanResult[] newArray(int n) {
            return new CellNetworkScanResult[n];
        }
    };
    public static final int STATUS_RADIO_GENERIC_FAILURE = 3;
    public static final int STATUS_RADIO_NOT_AVAILABLE = 2;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN_ERROR = 4;
    private final List<OperatorInfo> mOperators;
    private final int mStatus;

    public CellNetworkScanResult(int n, List<OperatorInfo> list) {
        this.mStatus = n;
        this.mOperators = list;
    }

    private CellNetworkScanResult(Parcel parcel) {
        this.mStatus = parcel.readInt();
        int n = parcel.readInt();
        if (n > 0) {
            this.mOperators = new ArrayList<OperatorInfo>();
            for (int i = 0; i < n; ++i) {
                this.mOperators.add(OperatorInfo.CREATOR.createFromParcel(parcel));
            }
        } else {
            this.mOperators = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<OperatorInfo> getOperators() {
        return this.mOperators;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CellNetworkScanResult: {");
        stringBuffer.append(" status:");
        stringBuffer.append(this.mStatus);
        Object object = this.mOperators;
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                OperatorInfo operatorInfo = (OperatorInfo)object.next();
                stringBuffer.append(" network:");
                stringBuffer.append(operatorInfo);
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mStatus);
        Object object = this.mOperators;
        if (object != null && object.size() > 0) {
            parcel.writeInt(this.mOperators.size());
            object = this.mOperators.iterator();
            while (object.hasNext()) {
                ((OperatorInfo)object.next()).writeToParcel(parcel, n);
            }
        } else {
            parcel.writeInt(0);
        }
    }

}

