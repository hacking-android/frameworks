/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.Rlog;
import android.telephony.ims.ImsCallForwardInfo;
import android.telephony.ims.ImsSsInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SystemApi
public final class ImsSsData
implements Parcelable {
    public static final Parcelable.Creator<ImsSsData> CREATOR;
    public static final int RESULT_SUCCESS = 0;
    public static final int SERVICE_CLASS_DATA = 2;
    public static final int SERVICE_CLASS_DATA_CIRCUIT_ASYNC = 32;
    public static final int SERVICE_CLASS_DATA_CIRCUIT_SYNC = 16;
    public static final int SERVICE_CLASS_DATA_PACKET_ACCESS = 64;
    public static final int SERVICE_CLASS_DATA_PAD = 128;
    public static final int SERVICE_CLASS_FAX = 4;
    public static final int SERVICE_CLASS_NONE = 0;
    public static final int SERVICE_CLASS_SMS = 8;
    public static final int SERVICE_CLASS_VOICE = 1;
    public static final int SS_ACTIVATION = 0;
    public static final int SS_ALL_BARRING = 18;
    public static final int SS_ALL_DATA_TELESERVICES = 3;
    public static final int SS_ALL_TELESERVICES_EXCEPT_SMS = 5;
    public static final int SS_ALL_TELESEVICES = 1;
    public static final int SS_ALL_TELE_AND_BEARER_SERVICES = 0;
    public static final int SS_BAIC = 16;
    public static final int SS_BAIC_ROAMING = 17;
    public static final int SS_BAOC = 13;
    public static final int SS_BAOIC = 14;
    public static final int SS_BAOIC_EXC_HOME = 15;
    public static final int SS_CFU = 0;
    public static final int SS_CFUT = 6;
    public static final int SS_CF_ALL = 4;
    public static final int SS_CF_ALL_CONDITIONAL = 5;
    public static final int SS_CF_BUSY = 1;
    public static final int SS_CF_NOT_REACHABLE = 3;
    public static final int SS_CF_NO_REPLY = 2;
    public static final int SS_CLIP = 7;
    public static final int SS_CLIR = 8;
    public static final int SS_CNAP = 11;
    public static final int SS_COLP = 9;
    public static final int SS_COLR = 10;
    public static final int SS_DEACTIVATION = 1;
    public static final int SS_ERASURE = 4;
    public static final int SS_INCOMING_BARRING = 20;
    public static final int SS_INCOMING_BARRING_ANONYMOUS = 22;
    public static final int SS_INCOMING_BARRING_DN = 21;
    public static final int SS_INTERROGATION = 2;
    public static final int SS_OUTGOING_BARRING = 19;
    public static final int SS_REGISTRATION = 3;
    public static final int SS_SMS_SERVICES = 4;
    public static final int SS_TELEPHONY = 2;
    public static final int SS_WAIT = 12;
    private static final String TAG;
    private List<ImsCallForwardInfo> mCfInfo;
    private List<ImsSsInfo> mImsSsInfo;
    private int[] mSsInfo;
    public final int requestType;
    public final int result;
    public final int serviceClass;
    public final int serviceType;
    public final int teleserviceType;

    static {
        TAG = ImsSsData.class.getCanonicalName();
        CREATOR = new Parcelable.Creator<ImsSsData>(){

            @Override
            public ImsSsData createFromParcel(Parcel parcel) {
                return new ImsSsData(parcel);
            }

            public ImsSsData[] newArray(int n) {
                return new ImsSsData[n];
            }
        };
    }

    public ImsSsData(int n, int n2, int n3, int n4, int n5) {
        this.serviceType = n;
        this.requestType = n2;
        this.teleserviceType = n3;
        this.serviceClass = n4;
        this.result = n5;
    }

    private ImsSsData(Parcel parcel) {
        this.serviceType = parcel.readInt();
        this.requestType = parcel.readInt();
        this.teleserviceType = parcel.readInt();
        this.serviceClass = parcel.readInt();
        this.result = parcel.readInt();
        this.mSsInfo = parcel.createIntArray();
        this.mCfInfo = parcel.readParcelableList(new ArrayList(), this.getClass().getClassLoader());
        this.mImsSsInfo = parcel.readParcelableList(new ArrayList(), this.getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<ImsCallForwardInfo> getCallForwardInfo() {
        return this.mCfInfo;
    }

    public int getRequestType() {
        return this.requestType;
    }

    public int getResult() {
        return this.result;
    }

    public int getServiceClass() {
        return this.serviceClass;
    }

    public int getServiceType() {
        return this.serviceType;
    }

    public List<ImsSsInfo> getSuppServiceInfo() {
        return this.mImsSsInfo;
    }

    public int[] getSuppServiceInfoCompat() {
        Object object = this.mSsInfo;
        if (object != null) {
            return object;
        }
        int[] arrn = new int[2];
        object = this.mImsSsInfo;
        if (object != null && object.size() != 0) {
            if (this.isTypeClir()) {
                arrn[0] = this.mImsSsInfo.get(0).getClirOutgoingState();
                arrn[1] = this.mImsSsInfo.get(0).getClirInterrogationStatus();
                return arrn;
            }
            if (this.isTypeColr()) {
                arrn[0] = this.mImsSsInfo.get(0).getProvisionStatus();
            }
            arrn[0] = this.mImsSsInfo.get(0).getStatus();
            arrn[1] = this.mImsSsInfo.get(0).getProvisionStatus();
            return arrn;
        }
        Rlog.e(TAG, "getSuppServiceInfoCompat: Could not parse mImsSsInfo, returning empty int[]");
        return arrn;
    }

    public int getTeleserviceType() {
        return this.teleserviceType;
    }

    public boolean isTypeBarring() {
        boolean bl = this.getServiceType() == 13 || this.getServiceType() == 14 || this.getServiceType() == 15 || this.getServiceType() == 16 || this.getServiceType() == 17 || this.getServiceType() == 18 || this.getServiceType() == 19 || this.getServiceType() == 20;
        return bl;
    }

    public boolean isTypeCF() {
        boolean bl;
        block0 : {
            int n = this.getServiceType();
            bl = true;
            if (n == 0 || this.getServiceType() == 1 || this.getServiceType() == 2 || this.getServiceType() == 3 || this.getServiceType() == 4 || this.getServiceType() == 5) break block0;
            bl = false;
        }
        return bl;
    }

    public boolean isTypeCW() {
        boolean bl = this.getServiceType() == 12;
        return bl;
    }

    public boolean isTypeCf() {
        return this.isTypeCF();
    }

    public boolean isTypeClip() {
        boolean bl = this.getServiceType() == 7;
        return bl;
    }

    public boolean isTypeClir() {
        boolean bl = this.getServiceType() == 8;
        return bl;
    }

    public boolean isTypeColp() {
        boolean bl = this.getServiceType() == 9;
        return bl;
    }

    public boolean isTypeColr() {
        boolean bl = this.getServiceType() == 10;
        return bl;
    }

    public boolean isTypeCw() {
        return this.isTypeCW();
    }

    public boolean isTypeIcb() {
        boolean bl = this.getServiceType() == 21 || this.getServiceType() == 22;
        return bl;
    }

    public boolean isTypeInterrogation() {
        boolean bl = this.getRequestType() == 2;
        return bl;
    }

    public boolean isTypeUnConditional() {
        boolean bl = this.getServiceType() == 0 || this.getServiceType() == 4;
        return bl;
    }

    public void setCallForwardingInfo(ImsCallForwardInfo[] arrimsCallForwardInfo) {
        this.mCfInfo = Arrays.asList(arrimsCallForwardInfo);
    }

    public void setImsSpecificSuppServiceInfo(ImsSsInfo[] arrimsSsInfo) {
        this.mImsSsInfo = Arrays.asList(arrimsSsInfo);
    }

    public void setSuppServiceInfo(int[] arrn) {
        this.mSsInfo = arrn;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ImsSsData] ServiceType: ");
        stringBuilder.append(this.getServiceType());
        stringBuilder.append(" RequestType: ");
        stringBuilder.append(this.getRequestType());
        stringBuilder.append(" TeleserviceType: ");
        stringBuilder.append(this.getTeleserviceType());
        stringBuilder.append(" ServiceClass: ");
        stringBuilder.append(this.getServiceClass());
        stringBuilder.append(" Result: ");
        stringBuilder.append(this.getResult());
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.getServiceType());
        parcel.writeInt(this.getRequestType());
        parcel.writeInt(this.getTeleserviceType());
        parcel.writeInt(this.getServiceClass());
        parcel.writeInt(this.getResult());
        parcel.writeIntArray(this.mSsInfo);
        parcel.writeParcelableList(this.mCfInfo, 0);
        parcel.writeParcelableList(this.mImsSsInfo, 0);
    }

    public static final class Builder {
        private ImsSsData mImsSsData;

        public Builder(int n, int n2, int n3, int n4, int n5) {
            this.mImsSsData = new ImsSsData(n, n2, n3, n4, n5);
        }

        public ImsSsData build() {
            return this.mImsSsData;
        }

        public Builder setCallForwardingInfo(List<ImsCallForwardInfo> list) {
            this.mImsSsData.mCfInfo = list;
            return this;
        }

        public Builder setSuppServiceInfo(List<ImsSsInfo> list) {
            this.mImsSsData.mImsSsInfo = list;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RequestType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ServiceClassFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ServiceType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TeleserviceType {
    }

}

