/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
public final class LteVopsSupportInfo
implements Parcelable {
    public static final Parcelable.Creator<LteVopsSupportInfo> CREATOR = new Parcelable.Creator<LteVopsSupportInfo>(){

        @Override
        public LteVopsSupportInfo createFromParcel(Parcel parcel) {
            return new LteVopsSupportInfo(parcel);
        }

        public LteVopsSupportInfo[] newArray(int n) {
            return new LteVopsSupportInfo[n];
        }
    };
    public static final int LTE_STATUS_NOT_AVAILABLE = 1;
    public static final int LTE_STATUS_NOT_SUPPORTED = 3;
    public static final int LTE_STATUS_SUPPORTED = 2;
    private final int mEmcBearerSupport;
    private final int mVopsSupport;

    public LteVopsSupportInfo(int n, int n2) {
        this.mVopsSupport = n;
        this.mEmcBearerSupport = n2;
    }

    private LteVopsSupportInfo(Parcel parcel) {
        this.mVopsSupport = parcel.readInt();
        this.mEmcBearerSupport = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof LteVopsSupportInfo) {
            if (this == object) {
                return true;
            }
            object = (LteVopsSupportInfo)object;
            boolean bl2 = bl;
            if (this.mVopsSupport == ((LteVopsSupportInfo)object).mVopsSupport) {
                bl2 = bl;
                if (this.mEmcBearerSupport == ((LteVopsSupportInfo)object).mEmcBearerSupport) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public int getEmcBearerSupport() {
        return this.mEmcBearerSupport;
    }

    public int getVopsSupport() {
        return this.mVopsSupport;
    }

    public int hashCode() {
        return Objects.hash(this.mVopsSupport, this.mEmcBearerSupport);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LteVopsSupportInfo :  mVopsSupport = ");
        stringBuilder.append(this.mVopsSupport);
        stringBuilder.append(" mEmcBearerSupport = ");
        stringBuilder.append(this.mEmcBearerSupport);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mVopsSupport);
        parcel.writeInt(this.mEmcBearerSupport);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LteVopsStatus {
    }

}

