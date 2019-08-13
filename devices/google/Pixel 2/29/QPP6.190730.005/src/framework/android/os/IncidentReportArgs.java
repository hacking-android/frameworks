/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.IntArray;
import java.util.ArrayList;

@SystemApi
public final class IncidentReportArgs
implements Parcelable {
    public static final Parcelable.Creator<IncidentReportArgs> CREATOR = new Parcelable.Creator<IncidentReportArgs>(){

        @Override
        public IncidentReportArgs createFromParcel(Parcel parcel) {
            return new IncidentReportArgs(parcel);
        }

        public IncidentReportArgs[] newArray(int n) {
            return new IncidentReportArgs[n];
        }
    };
    private boolean mAll;
    private final ArrayList<byte[]> mHeaders = new ArrayList();
    private int mPrivacyPolicy;
    private String mReceiverCls;
    private String mReceiverPkg;
    private final IntArray mSections = new IntArray();

    public IncidentReportArgs() {
        this.mPrivacyPolicy = 200;
    }

    public IncidentReportArgs(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public void addHeader(byte[] arrby) {
        this.mHeaders.add(arrby);
    }

    public void addSection(int n) {
        if (!this.mAll && n > 1) {
            this.mSections.add(n);
        }
    }

    public boolean containsSection(int n) {
        boolean bl = this.mAll || this.mSections.indexOf(n) >= 0;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isAll() {
        return this.mAll;
    }

    public void readFromParcel(Parcel parcel) {
        int n;
        boolean bl = parcel.readInt() != 0;
        this.mAll = bl;
        this.mSections.clear();
        int n2 = parcel.readInt();
        for (n = 0; n < n2; ++n) {
            this.mSections.add(parcel.readInt());
        }
        this.mHeaders.clear();
        n2 = parcel.readInt();
        for (n = 0; n < n2; ++n) {
            this.mHeaders.add(parcel.createByteArray());
        }
        this.mPrivacyPolicy = parcel.readInt();
        this.mReceiverPkg = parcel.readString();
        this.mReceiverCls = parcel.readString();
    }

    public int sectionCount() {
        return this.mSections.size();
    }

    public void setAll(boolean bl) {
        this.mAll = bl;
        if (bl) {
            this.mSections.clear();
        }
    }

    public void setPrivacyPolicy(int n) {
        this.mPrivacyPolicy = n != 0 && n != 100 && n != 200 ? 200 : n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Incident(");
        if (this.mAll) {
            stringBuilder.append("all");
        } else {
            int n = this.mSections.size();
            if (n > 0) {
                stringBuilder.append(this.mSections.get(0));
            }
            for (int i = 1; i < n; ++i) {
                stringBuilder.append(" ");
                stringBuilder.append(this.mSections.get(i));
            }
        }
        stringBuilder.append(", ");
        stringBuilder.append(this.mHeaders.size());
        stringBuilder.append(" headers), ");
        stringBuilder.append("privacy: ");
        stringBuilder.append(this.mPrivacyPolicy);
        stringBuilder.append("receiver pkg: ");
        stringBuilder.append(this.mReceiverPkg);
        stringBuilder.append("receiver cls: ");
        stringBuilder.append(this.mReceiverCls);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.mAll);
        int n2 = this.mSections.size();
        parcel.writeInt(n2);
        for (n = 0; n < n2; ++n) {
            parcel.writeInt(this.mSections.get(n));
        }
        n2 = this.mHeaders.size();
        parcel.writeInt(n2);
        for (n = 0; n < n2; ++n) {
            parcel.writeByteArray(this.mHeaders.get(n));
        }
        parcel.writeInt(this.mPrivacyPolicy);
        parcel.writeString(this.mReceiverPkg);
        parcel.writeString(this.mReceiverCls);
    }

}

