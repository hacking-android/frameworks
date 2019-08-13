/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;
import com.android.internal.telephony.uicc.IccUtils;
import java.util.Arrays;

public class SmsCbEtwsInfo
implements Parcelable {
    public static final Parcelable.Creator<SmsCbEtwsInfo> CREATOR = new Parcelable.Creator<SmsCbEtwsInfo>(){

        @Override
        public SmsCbEtwsInfo createFromParcel(Parcel parcel) {
            return new SmsCbEtwsInfo(parcel);
        }

        public SmsCbEtwsInfo[] newArray(int n) {
            return new SmsCbEtwsInfo[n];
        }
    };
    public static final int ETWS_WARNING_TYPE_EARTHQUAKE = 0;
    public static final int ETWS_WARNING_TYPE_EARTHQUAKE_AND_TSUNAMI = 2;
    public static final int ETWS_WARNING_TYPE_OTHER_EMERGENCY = 4;
    public static final int ETWS_WARNING_TYPE_TEST_MESSAGE = 3;
    public static final int ETWS_WARNING_TYPE_TSUNAMI = 1;
    public static final int ETWS_WARNING_TYPE_UNKNOWN = -1;
    private final boolean mActivatePopup;
    private final boolean mEmergencyUserAlert;
    private final boolean mPrimary;
    private final byte[] mWarningSecurityInformation;
    private final int mWarningType;

    public SmsCbEtwsInfo(int n, boolean bl, boolean bl2, boolean bl3, byte[] arrby) {
        this.mWarningType = n;
        this.mEmergencyUserAlert = bl;
        this.mActivatePopup = bl2;
        this.mPrimary = bl3;
        this.mWarningSecurityInformation = arrby;
    }

    SmsCbEtwsInfo(Parcel parcel) {
        this.mWarningType = parcel.readInt();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.mEmergencyUserAlert = bl2;
        bl2 = parcel.readInt() != 0;
        this.mActivatePopup = bl2;
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.mPrimary = bl2;
        this.mWarningSecurityInformation = parcel.createByteArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getPrimaryNotificationSignature() {
        byte[] arrby = this.mWarningSecurityInformation;
        if (arrby != null && arrby.length >= 50) {
            return Arrays.copyOfRange(arrby, 7, 50);
        }
        return null;
    }

    public long getPrimaryNotificationTimestamp() {
        Object object = this.mWarningSecurityInformation;
        if (object != null && ((byte[])object).length >= 7) {
            int n = IccUtils.gsmBcdByteToInt(object[0]);
            int n2 = IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[1]);
            int n3 = IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[2]);
            int n4 = IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[3]);
            int n5 = IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[4]);
            int n6 = IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[5]);
            byte by = this.mWarningSecurityInformation[6];
            int n7 = IccUtils.gsmBcdByteToInt((byte)(by & -9));
            if ((by & 8) != 0) {
                n7 = -n7;
            }
            object = new Time("UTC");
            ((Time)object).year = n + 2000;
            ((Time)object).month = n2 - 1;
            ((Time)object).monthDay = n3;
            ((Time)object).hour = n4;
            ((Time)object).minute = n5;
            ((Time)object).second = n6;
            return ((Time)object).toMillis(true) - (long)(n7 * 15 * 60 * 1000);
        }
        return 0L;
    }

    public int getWarningType() {
        return this.mWarningType;
    }

    public boolean isEmergencyUserAlert() {
        return this.mEmergencyUserAlert;
    }

    public boolean isPopupAlert() {
        return this.mActivatePopup;
    }

    public boolean isPrimary() {
        return this.mPrimary;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SmsCbEtwsInfo{warningType=");
        stringBuilder.append(this.mWarningType);
        stringBuilder.append(", emergencyUserAlert=");
        stringBuilder.append(this.mEmergencyUserAlert);
        stringBuilder.append(", activatePopup=");
        stringBuilder.append(this.mActivatePopup);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mWarningType);
        parcel.writeInt((int)this.mEmergencyUserAlert);
        parcel.writeInt((int)this.mActivatePopup);
        parcel.writeInt((int)this.mPrimary);
        parcel.writeByteArray(this.mWarningSecurityInformation);
    }

}

