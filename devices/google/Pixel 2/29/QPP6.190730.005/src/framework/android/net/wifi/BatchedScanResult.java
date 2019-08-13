/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.SystemApi;
import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SystemApi
@Deprecated
public class BatchedScanResult
implements Parcelable {
    public static final Parcelable.Creator<BatchedScanResult> CREATOR = new Parcelable.Creator<BatchedScanResult>(){

        @Override
        public BatchedScanResult createFromParcel(Parcel parcel) {
            BatchedScanResult batchedScanResult = new BatchedScanResult();
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            batchedScanResult.truncated = bl;
            for (n = parcel.readInt(); n > 0; --n) {
                batchedScanResult.scanResults.add(ScanResult.CREATOR.createFromParcel(parcel));
            }
            return batchedScanResult;
        }

        public BatchedScanResult[] newArray(int n) {
            return new BatchedScanResult[n];
        }
    };
    private static final String TAG = "BatchedScanResult";
    public final List<ScanResult> scanResults = new ArrayList<ScanResult>();
    public boolean truncated;

    public BatchedScanResult() {
    }

    public BatchedScanResult(BatchedScanResult object) {
        this.truncated = ((BatchedScanResult)object).truncated;
        for (ScanResult scanResult : ((BatchedScanResult)object).scanResults) {
            this.scanResults.add(new ScanResult(scanResult));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("BatchedScanResult: ");
        stringBuffer.append("truncated: ");
        stringBuffer.append(String.valueOf(this.truncated));
        stringBuffer.append("scanResults: [");
        for (ScanResult scanResult : this.scanResults) {
            stringBuffer.append(" <");
            stringBuffer.append(scanResult.toString());
            stringBuffer.append("> ");
        }
        stringBuffer.append(" ]");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.truncated);
        parcel.writeInt(this.scanResults.size());
        Iterator<ScanResult> iterator = this.scanResults.iterator();
        while (iterator.hasNext()) {
            iterator.next().writeToParcel(parcel, n);
        }
    }

}

