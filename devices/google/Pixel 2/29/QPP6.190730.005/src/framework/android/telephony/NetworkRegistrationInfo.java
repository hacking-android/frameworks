/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.-$
 *  android.telephony.-$$Lambda
 *  android.telephony.-$$Lambda$NetworkRegistrationInfo
 *  android.telephony.-$$Lambda$NetworkRegistrationInfo$1JuZmO5PoYGZY8bHhZYwvmqwOB0
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.-$;
import android.telephony.AccessNetworkConstants;
import android.telephony.CellIdentity;
import android.telephony.DataSpecificRegistrationInfo;
import android.telephony.LteVopsSupportInfo;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.VoiceSpecificRegistrationInfo;
import android.telephony._$$Lambda$NetworkRegistrationInfo$1JuZmO5PoYGZY8bHhZYwvmqwOB0;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SystemApi
public final class NetworkRegistrationInfo
implements Parcelable {
    public static final Parcelable.Creator<NetworkRegistrationInfo> CREATOR = new Parcelable.Creator<NetworkRegistrationInfo>(){

        @Override
        public NetworkRegistrationInfo createFromParcel(Parcel parcel) {
            return new NetworkRegistrationInfo(parcel);
        }

        public NetworkRegistrationInfo[] newArray(int n) {
            return new NetworkRegistrationInfo[n];
        }
    };
    public static final int DOMAIN_CS = 1;
    public static final int DOMAIN_PS = 2;
    public static final int NR_STATE_CONNECTED = 3;
    public static final int NR_STATE_NONE = -1;
    public static final int NR_STATE_NOT_RESTRICTED = 2;
    public static final int NR_STATE_RESTRICTED = 1;
    public static final int REGISTRATION_STATE_DENIED = 3;
    public static final int REGISTRATION_STATE_HOME = 1;
    public static final int REGISTRATION_STATE_NOT_REGISTERED_OR_SEARCHING = 0;
    public static final int REGISTRATION_STATE_NOT_REGISTERED_SEARCHING = 2;
    public static final int REGISTRATION_STATE_ROAMING = 5;
    public static final int REGISTRATION_STATE_UNKNOWN = 4;
    public static final int SERVICE_TYPE_DATA = 2;
    public static final int SERVICE_TYPE_EMERGENCY = 5;
    public static final int SERVICE_TYPE_SMS = 3;
    public static final int SERVICE_TYPE_UNKNOWN = 0;
    public static final int SERVICE_TYPE_VIDEO = 4;
    public static final int SERVICE_TYPE_VOICE = 1;
    private int mAccessNetworkTechnology;
    private final ArrayList<Integer> mAvailableServices;
    private CellIdentity mCellIdentity;
    private DataSpecificRegistrationInfo mDataSpecificInfo;
    private final int mDomain;
    private final boolean mEmergencyOnly;
    private int mNrState;
    private final int mRegistrationState;
    private final int mRejectCause;
    private int mRoamingType;
    private final int mTransportType;
    private VoiceSpecificRegistrationInfo mVoiceSpecificInfo;

    private NetworkRegistrationInfo(int n, int n2, int n3, int n4, int n5, boolean bl, List<Integer> list, CellIdentity cellIdentity) {
        this.mDomain = n;
        this.mTransportType = n2;
        this.mRegistrationState = n3;
        n = n3 == 5 ? 1 : 0;
        this.mRoamingType = n;
        this.mAccessNetworkTechnology = n4;
        this.mRejectCause = n5;
        list = list != null ? new ArrayList<Integer>(list) : new ArrayList<Integer>();
        this.mAvailableServices = list;
        this.mCellIdentity = cellIdentity;
        this.mEmergencyOnly = bl;
        this.mNrState = -1;
    }

    public NetworkRegistrationInfo(int n, int n2, int n3, int n4, int n5, boolean bl, List<Integer> list, CellIdentity cellIdentity, int n6, boolean bl2, boolean bl3, boolean bl4, LteVopsSupportInfo lteVopsSupportInfo, boolean bl5) {
        this(n, n2, n3, n4, n5, bl, list, cellIdentity);
        this.mDataSpecificInfo = new DataSpecificRegistrationInfo(n6, bl2, bl3, bl4, lteVopsSupportInfo, bl5);
        this.updateNrState(this.mDataSpecificInfo);
    }

    public NetworkRegistrationInfo(int n, int n2, int n3, int n4, int n5, boolean bl, List<Integer> list, CellIdentity cellIdentity, boolean bl2, int n6, int n7, int n8) {
        this(n, n2, n3, n4, n5, bl, list, cellIdentity);
        this.mVoiceSpecificInfo = new VoiceSpecificRegistrationInfo(bl2, n6, n7, n8);
    }

    private NetworkRegistrationInfo(Parcel parcel) {
        this.mDomain = parcel.readInt();
        this.mTransportType = parcel.readInt();
        this.mRegistrationState = parcel.readInt();
        this.mRoamingType = parcel.readInt();
        this.mAccessNetworkTechnology = parcel.readInt();
        this.mRejectCause = parcel.readInt();
        this.mEmergencyOnly = parcel.readBoolean();
        this.mAvailableServices = new ArrayList();
        parcel.readList(this.mAvailableServices, Integer.class.getClassLoader());
        this.mCellIdentity = (CellIdentity)parcel.readParcelable(CellIdentity.class.getClassLoader());
        this.mVoiceSpecificInfo = (VoiceSpecificRegistrationInfo)parcel.readParcelable(VoiceSpecificRegistrationInfo.class.getClassLoader());
        this.mDataSpecificInfo = (DataSpecificRegistrationInfo)parcel.readParcelable(DataSpecificRegistrationInfo.class.getClassLoader());
        this.mNrState = parcel.readInt();
    }

    public NetworkRegistrationInfo(NetworkRegistrationInfo networkRegistrationInfo) {
        Object object;
        this.mDomain = networkRegistrationInfo.mDomain;
        this.mTransportType = networkRegistrationInfo.mTransportType;
        this.mRegistrationState = networkRegistrationInfo.mRegistrationState;
        this.mRoamingType = networkRegistrationInfo.mRoamingType;
        this.mAccessNetworkTechnology = networkRegistrationInfo.mAccessNetworkTechnology;
        this.mRejectCause = networkRegistrationInfo.mRejectCause;
        this.mEmergencyOnly = networkRegistrationInfo.mEmergencyOnly;
        this.mAvailableServices = new ArrayList<Integer>(networkRegistrationInfo.mAvailableServices);
        if (networkRegistrationInfo.mCellIdentity != null) {
            object = Parcel.obtain();
            networkRegistrationInfo.mCellIdentity.writeToParcel((Parcel)object, 0);
            ((Parcel)object).setDataPosition(0);
            this.mCellIdentity = CellIdentity.CREATOR.createFromParcel((Parcel)object);
        }
        if ((object = networkRegistrationInfo.mVoiceSpecificInfo) != null) {
            this.mVoiceSpecificInfo = new VoiceSpecificRegistrationInfo((VoiceSpecificRegistrationInfo)object);
        }
        if ((object = networkRegistrationInfo.mDataSpecificInfo) != null) {
            this.mDataSpecificInfo = new DataSpecificRegistrationInfo((DataSpecificRegistrationInfo)object);
        }
        this.mNrState = networkRegistrationInfo.mNrState;
    }

    private NetworkRegistrationInfo copy() {
        Parcel parcel = Parcel.obtain();
        this.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        NetworkRegistrationInfo networkRegistrationInfo = new NetworkRegistrationInfo(parcel);
        parcel.recycle();
        return networkRegistrationInfo;
    }

    static /* synthetic */ String lambda$toString$0(Integer n) {
        return NetworkRegistrationInfo.serviceTypeToString(n);
    }

    private static String nrStateToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return "NONE";
                }
                return "CONNECTED";
            }
            return "NOT_RESTRICTED";
        }
        return "RESTRICTED";
    }

    public static String registrationStateToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unknown reg state ");
                                stringBuilder.append(n);
                                return stringBuilder.toString();
                            }
                            return "ROAMING";
                        }
                        return "UNKNOWN";
                    }
                    return "DENIED";
                }
                return "NOT_REG_SEARCHING";
            }
            return "HOME";
        }
        return "NOT_REG_OR_SEARCHING";
    }

    public static String serviceTypeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown service type ");
                            stringBuilder.append(n);
                            return stringBuilder.toString();
                        }
                        return "EMERGENCY";
                    }
                    return "VIDEO";
                }
                return "SMS";
            }
            return "DATA";
        }
        return "VOICE";
    }

    private void updateNrState(DataSpecificRegistrationInfo dataSpecificRegistrationInfo) {
        this.mNrState = -1;
        if (dataSpecificRegistrationInfo.isEnDcAvailable) {
            this.mNrState = !dataSpecificRegistrationInfo.isDcNrRestricted && dataSpecificRegistrationInfo.isNrAvailable ? 2 : 1;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof NetworkRegistrationInfo)) {
            return false;
        }
        object = (NetworkRegistrationInfo)object;
        if (!(this.mDomain == ((NetworkRegistrationInfo)object).mDomain && this.mTransportType == ((NetworkRegistrationInfo)object).mTransportType && this.mRegistrationState == ((NetworkRegistrationInfo)object).mRegistrationState && this.mRoamingType == ((NetworkRegistrationInfo)object).mRoamingType && this.mAccessNetworkTechnology == ((NetworkRegistrationInfo)object).mAccessNetworkTechnology && this.mRejectCause == ((NetworkRegistrationInfo)object).mRejectCause && this.mEmergencyOnly == ((NetworkRegistrationInfo)object).mEmergencyOnly && this.mAvailableServices.equals(((NetworkRegistrationInfo)object).mAvailableServices) && Objects.equals(this.mCellIdentity, ((NetworkRegistrationInfo)object).mCellIdentity) && Objects.equals(this.mVoiceSpecificInfo, ((NetworkRegistrationInfo)object).mVoiceSpecificInfo) && Objects.equals(this.mDataSpecificInfo, ((NetworkRegistrationInfo)object).mDataSpecificInfo) && this.mNrState == ((NetworkRegistrationInfo)object).mNrState)) {
            bl = false;
        }
        return bl;
    }

    public int getAccessNetworkTechnology() {
        return this.mAccessNetworkTechnology;
    }

    public List<Integer> getAvailableServices() {
        return Collections.unmodifiableList(this.mAvailableServices);
    }

    public CellIdentity getCellIdentity() {
        return this.mCellIdentity;
    }

    public DataSpecificRegistrationInfo getDataSpecificInfo() {
        return this.mDataSpecificInfo;
    }

    public int getDomain() {
        return this.mDomain;
    }

    public int getNrState() {
        return this.mNrState;
    }

    public int getRegistrationState() {
        return this.mRegistrationState;
    }

    public int getRejectCause() {
        return this.mRejectCause;
    }

    public int getRoamingType() {
        return this.mRoamingType;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public VoiceSpecificRegistrationInfo getVoiceSpecificInfo() {
        return this.mVoiceSpecificInfo;
    }

    public int hashCode() {
        return Objects.hash(this.mDomain, this.mTransportType, this.mRegistrationState, this.mRoamingType, this.mAccessNetworkTechnology, this.mRejectCause, this.mEmergencyOnly, this.mAvailableServices, this.mCellIdentity, this.mVoiceSpecificInfo, this.mDataSpecificInfo, this.mNrState);
    }

    public boolean isEmergencyEnabled() {
        return this.mEmergencyOnly;
    }

    public boolean isInService() {
        boolean bl;
        int n = this.mRegistrationState;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = n == 5 ? bl : false;
        }
        return bl2;
    }

    public boolean isRoaming() {
        boolean bl = this.mRoamingType != 0;
        return bl;
    }

    public NetworkRegistrationInfo sanitizeLocationInfo() {
        NetworkRegistrationInfo networkRegistrationInfo = this.copy();
        networkRegistrationInfo.mCellIdentity = null;
        return networkRegistrationInfo;
    }

    public void setAccessNetworkTechnology(int n) {
        int n2 = n;
        if (n == 19) {
            n = 13;
            DataSpecificRegistrationInfo dataSpecificRegistrationInfo = this.mDataSpecificInfo;
            n2 = n;
            if (dataSpecificRegistrationInfo != null) {
                dataSpecificRegistrationInfo.setIsUsingCarrierAggregation(true);
                n2 = n;
            }
        }
        this.mAccessNetworkTechnology = n2;
    }

    public void setNrState(int n) {
        this.mNrState = n;
    }

    public void setRoamingType(int n) {
        this.mRoamingType = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("NetworkRegistrationInfo{");
        stringBuilder.append(" domain=");
        Object object = this.mDomain == 1 ? "CS" : "PS";
        stringBuilder.append((String)object);
        stringBuilder.append(" transportType=");
        stringBuilder.append(AccessNetworkConstants.transportTypeToString(this.mTransportType));
        stringBuilder.append(" registrationState=");
        stringBuilder.append(NetworkRegistrationInfo.registrationStateToString(this.mRegistrationState));
        stringBuilder.append(" roamingType=");
        stringBuilder.append(ServiceState.roamingTypeToString(this.mRoamingType));
        stringBuilder.append(" accessNetworkTechnology=");
        stringBuilder.append(TelephonyManager.getNetworkTypeName(this.mAccessNetworkTechnology));
        stringBuilder.append(" rejectCause=");
        stringBuilder.append(this.mRejectCause);
        stringBuilder.append(" emergencyEnabled=");
        stringBuilder.append(this.mEmergencyOnly);
        stringBuilder.append(" availableServices=");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[");
        object = this.mAvailableServices;
        object = object != null ? object.stream().map(_$$Lambda$NetworkRegistrationInfo$1JuZmO5PoYGZY8bHhZYwvmqwOB0.INSTANCE).collect(Collectors.joining(",")) : null;
        stringBuilder2.append((String)object);
        stringBuilder2.append("]");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" cellIdentity=");
        stringBuilder.append(this.mCellIdentity);
        stringBuilder.append(" voiceSpecificInfo=");
        stringBuilder.append(this.mVoiceSpecificInfo);
        stringBuilder.append(" dataSpecificInfo=");
        stringBuilder.append(this.mDataSpecificInfo);
        stringBuilder.append(" nrState=");
        stringBuilder.append(NetworkRegistrationInfo.nrStateToString(this.mNrState));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mDomain);
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mRegistrationState);
        parcel.writeInt(this.mRoamingType);
        parcel.writeInt(this.mAccessNetworkTechnology);
        parcel.writeInt(this.mRejectCause);
        parcel.writeBoolean(this.mEmergencyOnly);
        parcel.writeList(this.mAvailableServices);
        parcel.writeParcelable(this.mCellIdentity, 0);
        parcel.writeParcelable(this.mVoiceSpecificInfo, 0);
        parcel.writeParcelable(this.mDataSpecificInfo, 0);
        parcel.writeInt(this.mNrState);
    }

    public static final class Builder {
        private int mAccessNetworkTechnology;
        private List<Integer> mAvailableServices;
        private CellIdentity mCellIdentity;
        private int mDomain;
        private boolean mEmergencyOnly;
        private int mRegistrationState;
        private int mRejectCause;
        private int mTransportType;

        public NetworkRegistrationInfo build() {
            return new NetworkRegistrationInfo(this.mDomain, this.mTransportType, this.mRegistrationState, this.mAccessNetworkTechnology, this.mRejectCause, this.mEmergencyOnly, this.mAvailableServices, this.mCellIdentity);
        }

        public Builder setAccessNetworkTechnology(int n) {
            this.mAccessNetworkTechnology = n;
            return this;
        }

        public Builder setAvailableServices(List<Integer> list) {
            this.mAvailableServices = list;
            return this;
        }

        public Builder setCellIdentity(CellIdentity cellIdentity) {
            this.mCellIdentity = cellIdentity;
            return this;
        }

        public Builder setDomain(int n) {
            this.mDomain = n;
            return this;
        }

        public Builder setEmergencyOnly(boolean bl) {
            this.mEmergencyOnly = bl;
            return this;
        }

        public Builder setRegistrationState(int n) {
            this.mRegistrationState = n;
            return this;
        }

        public Builder setRejectCause(int n) {
            this.mRejectCause = n;
            return this;
        }

        public Builder setTransportType(int n) {
            this.mTransportType = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Domain {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NRState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RegistrationState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ServiceType {
    }

}

