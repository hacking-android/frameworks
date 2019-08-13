/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.TelephonyHistogram;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ClientRequestStats
implements Parcelable {
    public static final Parcelable.Creator<ClientRequestStats> CREATOR = new Parcelable.Creator<ClientRequestStats>(){

        @Override
        public ClientRequestStats createFromParcel(Parcel parcel) {
            return new ClientRequestStats(parcel);
        }

        public ClientRequestStats[] newArray(int n) {
            return new ClientRequestStats[n];
        }
    };
    private static final int REQUEST_HISTOGRAM_BUCKET_COUNT = 5;
    private String mCallingPackage;
    private long mCompletedRequestsCount = 0L;
    private long mCompletedRequestsWakelockTime = 0L;
    private long mPendingRequestsCount = 0L;
    private long mPendingRequestsWakelockTime = 0L;
    private SparseArray<TelephonyHistogram> mRequestHistograms = new SparseArray();

    public ClientRequestStats() {
    }

    public ClientRequestStats(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public ClientRequestStats(ClientRequestStats parcelable2) {
        this.mCallingPackage = ((ClientRequestStats)parcelable2).getCallingPackage();
        this.mCompletedRequestsCount = ((ClientRequestStats)parcelable2).getCompletedRequestsCount();
        this.mCompletedRequestsWakelockTime = ((ClientRequestStats)parcelable2).getCompletedRequestsWakelockTime();
        this.mPendingRequestsCount = ((ClientRequestStats)parcelable2).getPendingRequestsCount();
        this.mPendingRequestsWakelockTime = ((ClientRequestStats)parcelable2).getPendingRequestsWakelockTime();
        for (TelephonyHistogram telephonyHistogram : ((ClientRequestStats)parcelable2).getRequestHistograms()) {
            this.mRequestHistograms.put(telephonyHistogram.getId(), telephonyHistogram);
        }
    }

    public void addCompletedWakelockTime(long l) {
        this.mCompletedRequestsWakelockTime += l;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCallingPackage() {
        return this.mCallingPackage;
    }

    public long getCompletedRequestsCount() {
        return this.mCompletedRequestsCount;
    }

    public long getCompletedRequestsWakelockTime() {
        return this.mCompletedRequestsWakelockTime;
    }

    public long getPendingRequestsCount() {
        return this.mPendingRequestsCount;
    }

    public long getPendingRequestsWakelockTime() {
        return this.mPendingRequestsWakelockTime;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<TelephonyHistogram> getRequestHistograms() {
        SparseArray<TelephonyHistogram> sparseArray = this.mRequestHistograms;
        synchronized (sparseArray) {
            ArrayList<TelephonyHistogram> arrayList = new ArrayList<TelephonyHistogram>(this.mRequestHistograms.size());
            int n = 0;
            while (n < this.mRequestHistograms.size()) {
                TelephonyHistogram telephonyHistogram = new TelephonyHistogram(this.mRequestHistograms.valueAt(n));
                arrayList.add(telephonyHistogram);
                ++n;
            }
            return arrayList;
        }
    }

    public void incrementCompletedRequestsCount() {
        ++this.mCompletedRequestsCount;
    }

    public void readFromParcel(Parcel object) {
        this.mCallingPackage = ((Parcel)object).readString();
        this.mCompletedRequestsWakelockTime = ((Parcel)object).readLong();
        this.mCompletedRequestsCount = ((Parcel)object).readLong();
        this.mPendingRequestsWakelockTime = ((Parcel)object).readLong();
        this.mPendingRequestsCount = ((Parcel)object).readLong();
        Object object2 = new ArrayList();
        ((Parcel)object).readTypedList(object2, TelephonyHistogram.CREATOR);
        object = ((ArrayList)object2).iterator();
        while (object.hasNext()) {
            object2 = (TelephonyHistogram)object.next();
            this.mRequestHistograms.put(((TelephonyHistogram)object2).getId(), (TelephonyHistogram)object2);
        }
    }

    public void setCallingPackage(String string2) {
        this.mCallingPackage = string2;
    }

    public void setPendingRequestsCount(long l) {
        this.mPendingRequestsCount = l;
    }

    public void setPendingRequestsWakelockTime(long l) {
        this.mPendingRequestsWakelockTime = l;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ClientRequestStats{mCallingPackage='");
        stringBuilder.append(this.mCallingPackage);
        stringBuilder.append('\'');
        stringBuilder.append(", mCompletedRequestsWakelockTime=");
        stringBuilder.append(this.mCompletedRequestsWakelockTime);
        stringBuilder.append(", mCompletedRequestsCount=");
        stringBuilder.append(this.mCompletedRequestsCount);
        stringBuilder.append(", mPendingRequestsWakelockTime=");
        stringBuilder.append(this.mPendingRequestsWakelockTime);
        stringBuilder.append(", mPendingRequestsCount=");
        stringBuilder.append(this.mPendingRequestsCount);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateRequestHistograms(int n, int n2) {
        SparseArray<TelephonyHistogram> sparseArray = this.mRequestHistograms;
        synchronized (sparseArray) {
            TelephonyHistogram telephonyHistogram;
            TelephonyHistogram telephonyHistogram2 = telephonyHistogram = this.mRequestHistograms.get(n);
            if (telephonyHistogram == null) {
                telephonyHistogram2 = new TelephonyHistogram(1, n, 5);
                this.mRequestHistograms.put(n, telephonyHistogram2);
            }
            telephonyHistogram2.addTimeTaken(n2);
            return;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mCallingPackage);
        parcel.writeLong(this.mCompletedRequestsWakelockTime);
        parcel.writeLong(this.mCompletedRequestsCount);
        parcel.writeLong(this.mPendingRequestsWakelockTime);
        parcel.writeLong(this.mPendingRequestsCount);
        parcel.writeTypedList(this.getRequestHistograms());
    }

}

