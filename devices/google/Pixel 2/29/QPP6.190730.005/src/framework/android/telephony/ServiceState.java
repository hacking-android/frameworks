/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.-$
 *  android.telephony.-$$Lambda
 *  android.telephony.-$$Lambda$MLKtmRGKP3e0WU7x_KyS5-Vg8q4
 */
package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.-$;
import android.telephony.AccessNetworkUtils;
import android.telephony.DataSpecificRegistrationInfo;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.Rlog;
import android.telephony._$$Lambda$MLKtmRGKP3e0WU7x_KyS5_Vg8q4;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceState
implements Parcelable {
    public static final Parcelable.Creator<ServiceState> CREATOR;
    static final boolean DBG = false;
    public static final int DUPLEX_MODE_FDD = 1;
    public static final int DUPLEX_MODE_TDD = 2;
    public static final int DUPLEX_MODE_UNKNOWN = 0;
    public static final int FREQUENCY_RANGE_HIGH = 3;
    public static final int FREQUENCY_RANGE_LOW = 1;
    public static final int FREQUENCY_RANGE_MID = 2;
    public static final int FREQUENCY_RANGE_MMWAVE = 4;
    private static final List<Integer> FREQUENCY_RANGE_ORDER;
    public static final int FREQUENCY_RANGE_UNKNOWN = -1;
    static final String LOG_TAG = "PHONE";
    private static final int NEXT_RIL_RADIO_TECHNOLOGY = 21;
    public static final int RIL_RADIO_CDMA_TECHNOLOGY_BITMASK = 6392;
    public static final int RIL_RADIO_TECHNOLOGY_1xRTT = 6;
    public static final int RIL_RADIO_TECHNOLOGY_EDGE = 2;
    public static final int RIL_RADIO_TECHNOLOGY_EHRPD = 13;
    public static final int RIL_RADIO_TECHNOLOGY_EVDO_0 = 7;
    public static final int RIL_RADIO_TECHNOLOGY_EVDO_A = 8;
    public static final int RIL_RADIO_TECHNOLOGY_EVDO_B = 12;
    public static final int RIL_RADIO_TECHNOLOGY_GPRS = 1;
    public static final int RIL_RADIO_TECHNOLOGY_GSM = 16;
    public static final int RIL_RADIO_TECHNOLOGY_HSDPA = 9;
    public static final int RIL_RADIO_TECHNOLOGY_HSPA = 11;
    public static final int RIL_RADIO_TECHNOLOGY_HSPAP = 15;
    public static final int RIL_RADIO_TECHNOLOGY_HSUPA = 10;
    public static final int RIL_RADIO_TECHNOLOGY_IS95A = 4;
    public static final int RIL_RADIO_TECHNOLOGY_IS95B = 5;
    @UnsupportedAppUsage(maxTargetSdk=28)
    public static final int RIL_RADIO_TECHNOLOGY_IWLAN = 18;
    public static final int RIL_RADIO_TECHNOLOGY_LTE = 14;
    public static final int RIL_RADIO_TECHNOLOGY_LTE_CA = 19;
    public static final int RIL_RADIO_TECHNOLOGY_NR = 20;
    public static final int RIL_RADIO_TECHNOLOGY_TD_SCDMA = 17;
    public static final int RIL_RADIO_TECHNOLOGY_UMTS = 3;
    public static final int RIL_RADIO_TECHNOLOGY_UNKNOWN = 0;
    @SystemApi
    public static final int ROAMING_TYPE_DOMESTIC = 2;
    @SystemApi
    public static final int ROAMING_TYPE_INTERNATIONAL = 3;
    @SystemApi
    public static final int ROAMING_TYPE_NOT_ROAMING = 0;
    @SystemApi
    public static final int ROAMING_TYPE_UNKNOWN = 1;
    public static final int STATE_EMERGENCY_ONLY = 2;
    public static final int STATE_IN_SERVICE = 0;
    public static final int STATE_OUT_OF_SERVICE = 1;
    public static final int STATE_POWER_OFF = 3;
    public static final int UNKNOWN_ID = -1;
    static final boolean VDBG = false;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mCdmaDefaultRoamingIndicator;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mCdmaEriIconIndex;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mCdmaEriIconMode;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mCdmaRoamingIndicator;
    private int[] mCellBandwidths;
    private int mChannelNumber;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private boolean mCssIndicator;
    private String mDataOperatorAlphaLong;
    private String mDataOperatorAlphaShort;
    private String mDataOperatorNumeric;
    private int mDataRegState;
    private boolean mIsEmergencyOnly;
    private boolean mIsIwlanPreferred;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private boolean mIsManualNetworkSelection;
    private int mLteEarfcnRsrpBoost;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mNetworkId;
    private final List<NetworkRegistrationInfo> mNetworkRegistrationInfos;
    private int mNrFrequencyRange;
    private String mOperatorAlphaLongRaw;
    private String mOperatorAlphaShortRaw;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mSystemId;
    private String mVoiceOperatorAlphaLong;
    private String mVoiceOperatorAlphaShort;
    private String mVoiceOperatorNumeric;
    private int mVoiceRegState;

    static {
        FREQUENCY_RANGE_ORDER = Arrays.asList(-1, 1, 2, 3, 4);
        CREATOR = new Parcelable.Creator<ServiceState>(){

            @Override
            public ServiceState createFromParcel(Parcel parcel) {
                return new ServiceState(parcel);
            }

            public ServiceState[] newArray(int n) {
                return new ServiceState[n];
            }
        };
    }

    public ServiceState() {
        this.mVoiceRegState = 1;
        this.mDataRegState = 1;
        this.mCellBandwidths = new int[0];
        this.mLteEarfcnRsrpBoost = 0;
        this.mNetworkRegistrationInfos = new ArrayList<NetworkRegistrationInfo>();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public ServiceState(Parcel parcel) {
        boolean bl = true;
        this.mVoiceRegState = 1;
        this.mDataRegState = 1;
        this.mCellBandwidths = new int[0];
        this.mLteEarfcnRsrpBoost = 0;
        this.mNetworkRegistrationInfos = new ArrayList<NetworkRegistrationInfo>();
        this.mVoiceRegState = parcel.readInt();
        this.mDataRegState = parcel.readInt();
        this.mVoiceOperatorAlphaLong = parcel.readString();
        this.mVoiceOperatorAlphaShort = parcel.readString();
        this.mVoiceOperatorNumeric = parcel.readString();
        this.mDataOperatorAlphaLong = parcel.readString();
        this.mDataOperatorAlphaShort = parcel.readString();
        this.mDataOperatorNumeric = parcel.readString();
        boolean bl2 = parcel.readInt() != 0;
        this.mIsManualNetworkSelection = bl2;
        bl2 = parcel.readInt() != 0;
        this.mCssIndicator = bl2;
        this.mNetworkId = parcel.readInt();
        this.mSystemId = parcel.readInt();
        this.mCdmaRoamingIndicator = parcel.readInt();
        this.mCdmaDefaultRoamingIndicator = parcel.readInt();
        this.mCdmaEriIconIndex = parcel.readInt();
        this.mCdmaEriIconMode = parcel.readInt();
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.mIsEmergencyOnly = bl2;
        this.mLteEarfcnRsrpBoost = parcel.readInt();
        List<NetworkRegistrationInfo> list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            parcel.readList(this.mNetworkRegistrationInfos, NetworkRegistrationInfo.class.getClassLoader());
        }
        this.mChannelNumber = parcel.readInt();
        this.mCellBandwidths = parcel.createIntArray();
        this.mNrFrequencyRange = parcel.readInt();
        this.mOperatorAlphaLongRaw = parcel.readString();
        this.mOperatorAlphaShortRaw = parcel.readString();
        this.mIsIwlanPreferred = parcel.readBoolean();
    }

    public ServiceState(ServiceState serviceState) {
        this.mVoiceRegState = 1;
        this.mDataRegState = 1;
        this.mCellBandwidths = new int[0];
        this.mLteEarfcnRsrpBoost = 0;
        this.mNetworkRegistrationInfos = new ArrayList<NetworkRegistrationInfo>();
        this.copyFrom(serviceState);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static boolean bearerBitmapHasCdma(int n) {
        boolean bl = (ServiceState.convertNetworkTypeBitmaskToBearerBitmask(n) & 6392) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public static boolean bitmaskHasTech(int n, int n2) {
        boolean bl = true;
        if (n == 0) {
            return true;
        }
        if (n2 >= 1) {
            if ((1 << n2 - 1 & n) == 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public static int convertBearerBitmaskToNetworkTypeBitmask(int n) {
        if (n == 0) {
            return 0;
        }
        int n2 = 0;
        for (int i = 0; i < 21; ++i) {
            int n3 = n2;
            if (ServiceState.bitmaskHasTech(n, i)) {
                n3 = n2 | ServiceState.getBitmaskForTech(ServiceState.rilRadioTechnologyToNetworkType(i));
            }
            n2 = n3;
        }
        return n2;
    }

    public static int convertNetworkTypeBitmaskToBearerBitmask(int n) {
        if (n == 0) {
            return 0;
        }
        int n2 = 0;
        for (int i = 0; i < 21; ++i) {
            int n3 = n2;
            if (ServiceState.bitmaskHasTech(n, ServiceState.rilRadioTechnologyToNetworkType(i))) {
                n3 = n2 | ServiceState.getBitmaskForTech(i);
            }
            n2 = n3;
        }
        return n2;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static boolean equalsHandlesNulls(Object object, Object object2) {
        boolean bl = object == null ? object2 == null : object.equals(object2);
        return bl;
    }

    public static final int getBetterNRFrequencyRange(int n, int n2) {
        if (FREQUENCY_RANGE_ORDER.indexOf(n) <= FREQUENCY_RANGE_ORDER.indexOf(n2)) {
            n = n2;
        }
        return n;
    }

    public static int getBitmaskForTech(int n) {
        if (n >= 1) {
            return 1 << n - 1;
        }
        return 0;
    }

    public static int getBitmaskFromString(String arrstring) {
        arrstring = arrstring.split("\\|");
        int n = arrstring.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3;
            block3 : {
                String string2 = arrstring[i];
                try {
                    n3 = Integer.parseInt(string2.trim());
                    if (n3 != 0) break block3;
                    return 0;
                }
                catch (NumberFormatException numberFormatException) {
                    return 0;
                }
            }
            n2 |= ServiceState.getBitmaskForTech(n3);
        }
        return n2;
    }

    public static final String getRoamingLogString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return "UNKNOWN";
                    }
                    return "International Roaming";
                }
                return "Domestic Roaming";
            }
            return "roaming";
        }
        return "home";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void init() {
        this.mVoiceRegState = 1;
        this.mDataRegState = 1;
        this.mChannelNumber = -1;
        this.mCellBandwidths = new int[0];
        this.mVoiceOperatorAlphaLong = null;
        this.mVoiceOperatorAlphaShort = null;
        this.mVoiceOperatorNumeric = null;
        this.mDataOperatorAlphaLong = null;
        this.mDataOperatorAlphaShort = null;
        this.mDataOperatorNumeric = null;
        this.mIsManualNetworkSelection = false;
        this.mCssIndicator = false;
        this.mNetworkId = -1;
        this.mSystemId = -1;
        this.mCdmaRoamingIndicator = -1;
        this.mCdmaDefaultRoamingIndicator = -1;
        this.mCdmaEriIconIndex = -1;
        this.mCdmaEriIconMode = -1;
        this.mIsEmergencyOnly = false;
        this.mLteEarfcnRsrpBoost = 0;
        this.mNrFrequencyRange = -1;
        List<NetworkRegistrationInfo> list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            this.mNetworkRegistrationInfos.clear();
            NetworkRegistrationInfo.Builder builder = new NetworkRegistrationInfo.Builder();
            this.addNetworkRegistrationInfo(builder.setDomain(1).setTransportType(1).setRegistrationState(4).build());
            builder = new NetworkRegistrationInfo.Builder();
            this.addNetworkRegistrationInfo(builder.setDomain(2).setTransportType(1).setRegistrationState(4).build());
        }
        this.mOperatorAlphaLongRaw = null;
        this.mOperatorAlphaShortRaw = null;
        this.mIsIwlanPreferred = false;
    }

    @UnsupportedAppUsage
    public static boolean isCdma(int n) {
        boolean bl = n == 4 || n == 5 || n == 6 || n == 7 || n == 8 || n == 12 || n == 13;
        return bl;
    }

    @UnsupportedAppUsage
    public static boolean isGsm(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = bl;
            if (n != 2) {
                bl2 = bl;
                if (n != 3) {
                    bl2 = bl;
                    if (n != 9) {
                        bl2 = bl;
                        if (n != 10) {
                            bl2 = bl;
                            if (n != 11) {
                                bl2 = bl;
                                if (n != 14) {
                                    bl2 = bl;
                                    if (n != 15) {
                                        bl2 = bl;
                                        if (n != 16) {
                                            bl2 = bl;
                                            if (n != 17) {
                                                bl2 = bl;
                                                if (n != 18) {
                                                    bl2 = n == 19 ? bl : false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl2;
    }

    public static boolean isLte(int n) {
        boolean bl = n == 14 || n == 19;
        return bl;
    }

    @UnsupportedAppUsage
    public static ServiceState mergeServiceStates(ServiceState serviceState, ServiceState serviceState2) {
        if (serviceState2.mVoiceRegState != 0) {
            return serviceState;
        }
        serviceState = new ServiceState(serviceState);
        serviceState.mVoiceRegState = serviceState2.mVoiceRegState;
        serviceState.mIsEmergencyOnly = false;
        return serviceState;
    }

    public static int networkTypeToRilRadioTechnology(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 19: {
                return 19;
            }
            case 18: {
                return 18;
            }
            case 17: {
                return 17;
            }
            case 16: {
                return 16;
            }
            case 15: {
                return 15;
            }
            case 14: {
                return 13;
            }
            case 13: {
                return 14;
            }
            case 12: {
                return 12;
            }
            case 10: {
                return 11;
            }
            case 9: {
                return 10;
            }
            case 8: {
                return 9;
            }
            case 7: {
                return 6;
            }
            case 6: {
                return 8;
            }
            case 5: {
                return 7;
            }
            case 4: {
                return 4;
            }
            case 3: {
                return 3;
            }
            case 2: {
                return 2;
            }
            case 1: 
        }
        return 1;
    }

    @UnsupportedAppUsage
    public static ServiceState newFromBundle(Bundle bundle) {
        ServiceState serviceState = new ServiceState();
        serviceState.setFromNotifierBundle(bundle);
        return serviceState;
    }

    public static int rilRadioTechnologyToAccessNetworkType(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 18: {
                return 5;
            }
            case 14: 
            case 19: {
                return 3;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 12: 
            case 13: {
                return 4;
            }
            case 3: 
            case 9: 
            case 10: 
            case 11: 
            case 15: 
            case 17: {
                return 2;
            }
            case 1: 
            case 2: 
            case 16: 
        }
        return 1;
    }

    public static int rilRadioTechnologyToNetworkType(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 20: {
                return 20;
            }
            case 19: {
                return 19;
            }
            case 18: {
                return 18;
            }
            case 17: {
                return 17;
            }
            case 16: {
                return 16;
            }
            case 15: {
                return 15;
            }
            case 14: {
                return 13;
            }
            case 13: {
                return 14;
            }
            case 12: {
                return 12;
            }
            case 11: {
                return 10;
            }
            case 10: {
                return 9;
            }
            case 9: {
                return 8;
            }
            case 8: {
                return 6;
            }
            case 7: {
                return 5;
            }
            case 6: {
                return 7;
            }
            case 4: 
            case 5: {
                return 4;
            }
            case 3: {
                return 3;
            }
            case 2: {
                return 2;
            }
            case 1: 
        }
        return 1;
    }

    @UnsupportedAppUsage
    public static String rilRadioTechnologyToString(int n) {
        String string2;
        switch (n) {
            default: {
                string2 = "Unexpected";
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected radioTechnology=");
                stringBuilder.append(n);
                Rlog.w(LOG_TAG, stringBuilder.toString());
                break;
            }
            case 19: {
                string2 = "LTE_CA";
                break;
            }
            case 18: {
                string2 = "IWLAN";
                break;
            }
            case 17: {
                string2 = "TD-SCDMA";
                break;
            }
            case 16: {
                string2 = "GSM";
                break;
            }
            case 15: {
                string2 = "HSPAP";
                break;
            }
            case 14: {
                string2 = "LTE";
                break;
            }
            case 13: {
                string2 = "eHRPD";
                break;
            }
            case 12: {
                string2 = "EvDo-rev.B";
                break;
            }
            case 11: {
                string2 = "HSPA";
                break;
            }
            case 10: {
                string2 = "HSUPA";
                break;
            }
            case 9: {
                string2 = "HSDPA";
                break;
            }
            case 8: {
                string2 = "EvDo-rev.A";
                break;
            }
            case 7: {
                string2 = "EvDo-rev.0";
                break;
            }
            case 6: {
                string2 = "1xRTT";
                break;
            }
            case 5: {
                string2 = "CDMA-IS95B";
                break;
            }
            case 4: {
                string2 = "CDMA-IS95A";
                break;
            }
            case 3: {
                string2 = "UMTS";
                break;
            }
            case 2: {
                string2 = "EDGE";
                break;
            }
            case 1: {
                string2 = "GPRS";
                break;
            }
            case 0: {
                string2 = "Unknown";
            }
        }
        return string2;
    }

    public static String rilServiceStateToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return "UNKNOWN";
                    }
                    return "POWER_OFF";
                }
                return "EMERGENCY_ONLY";
            }
            return "OUT_OF_SERVICE";
        }
        return "IN_SERVICE";
    }

    public static String roamingTypeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown roaming type ");
                        stringBuilder.append(n);
                        return stringBuilder.toString();
                    }
                    return "INTERNATIONAL";
                }
                return "DOMESTIC";
            }
            return "UNKNOWN";
        }
        return "NOT_ROAMING";
    }

    @UnsupportedAppUsage
    private void setFromNotifierBundle(Bundle parcelable) {
        if ((parcelable = (ServiceState)parcelable.getParcelable("android.intent.extra.SERVICE_STATE")) != null) {
            this.copyFrom((ServiceState)parcelable);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addNetworkRegistrationInfo(NetworkRegistrationInfo networkRegistrationInfo) {
        if (networkRegistrationInfo == null) {
            return;
        }
        List<NetworkRegistrationInfo> list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            Object object;
            for (int i = 0; i < this.mNetworkRegistrationInfos.size(); ++i) {
                object = this.mNetworkRegistrationInfos.get(i);
                if (((NetworkRegistrationInfo)object).getTransportType() != networkRegistrationInfo.getTransportType() || ((NetworkRegistrationInfo)object).getDomain() != networkRegistrationInfo.getDomain()) continue;
                this.mNetworkRegistrationInfos.remove(i);
                break;
            }
            object = this.mNetworkRegistrationInfos;
            NetworkRegistrationInfo networkRegistrationInfo2 = new NetworkRegistrationInfo(networkRegistrationInfo);
            object.add(networkRegistrationInfo2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void copyFrom(ServiceState serviceState) {
        this.mVoiceRegState = serviceState.mVoiceRegState;
        this.mDataRegState = serviceState.mDataRegState;
        this.mVoiceOperatorAlphaLong = serviceState.mVoiceOperatorAlphaLong;
        this.mVoiceOperatorAlphaShort = serviceState.mVoiceOperatorAlphaShort;
        this.mVoiceOperatorNumeric = serviceState.mVoiceOperatorNumeric;
        this.mDataOperatorAlphaLong = serviceState.mDataOperatorAlphaLong;
        this.mDataOperatorAlphaShort = serviceState.mDataOperatorAlphaShort;
        this.mDataOperatorNumeric = serviceState.mDataOperatorNumeric;
        this.mIsManualNetworkSelection = serviceState.mIsManualNetworkSelection;
        this.mCssIndicator = serviceState.mCssIndicator;
        this.mNetworkId = serviceState.mNetworkId;
        this.mSystemId = serviceState.mSystemId;
        this.mCdmaRoamingIndicator = serviceState.mCdmaRoamingIndicator;
        this.mCdmaDefaultRoamingIndicator = serviceState.mCdmaDefaultRoamingIndicator;
        this.mCdmaEriIconIndex = serviceState.mCdmaEriIconIndex;
        this.mCdmaEriIconMode = serviceState.mCdmaEriIconMode;
        this.mIsEmergencyOnly = serviceState.mIsEmergencyOnly;
        this.mChannelNumber = serviceState.mChannelNumber;
        Object object = serviceState.mCellBandwidths;
        object = object == null ? null : Arrays.copyOf(object, ((int[])object).length);
        this.mCellBandwidths = object;
        this.mLteEarfcnRsrpBoost = serviceState.mLteEarfcnRsrpBoost;
        object = this.mNetworkRegistrationInfos;
        synchronized (object) {
            this.mNetworkRegistrationInfos.clear();
            this.mNetworkRegistrationInfos.addAll(serviceState.getNetworkRegistrationInfoList());
        }
        this.mNrFrequencyRange = serviceState.mNrFrequencyRange;
        this.mOperatorAlphaLongRaw = serviceState.mOperatorAlphaLongRaw;
        this.mOperatorAlphaShortRaw = serviceState.mOperatorAlphaShortRaw;
        this.mIsIwlanPreferred = serviceState.mIsIwlanPreferred;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean equals(Object list) {
        boolean bl = list instanceof ServiceState;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        ServiceState serviceState = (ServiceState)((Object)list);
        list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            if (this.mVoiceRegState != serviceState.mVoiceRegState) return bl2;
            if (this.mDataRegState != serviceState.mDataRegState) return bl2;
            if (this.mIsManualNetworkSelection != serviceState.mIsManualNetworkSelection) return bl2;
            if (this.mChannelNumber != serviceState.mChannelNumber) return bl2;
            if (!Arrays.equals(this.mCellBandwidths, serviceState.mCellBandwidths)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mVoiceOperatorAlphaLong, serviceState.mVoiceOperatorAlphaLong)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mVoiceOperatorAlphaShort, serviceState.mVoiceOperatorAlphaShort)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mVoiceOperatorNumeric, serviceState.mVoiceOperatorNumeric)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mDataOperatorAlphaLong, serviceState.mDataOperatorAlphaLong)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mDataOperatorAlphaShort, serviceState.mDataOperatorAlphaShort)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mDataOperatorNumeric, serviceState.mDataOperatorNumeric)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mCssIndicator, serviceState.mCssIndicator)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mNetworkId, serviceState.mNetworkId)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mSystemId, serviceState.mSystemId)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mCdmaRoamingIndicator, serviceState.mCdmaRoamingIndicator)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mCdmaDefaultRoamingIndicator, serviceState.mCdmaDefaultRoamingIndicator)) return bl2;
            if (this.mIsEmergencyOnly != serviceState.mIsEmergencyOnly) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mOperatorAlphaLongRaw, serviceState.mOperatorAlphaLongRaw)) return bl2;
            if (!ServiceState.equalsHandlesNulls(this.mOperatorAlphaShortRaw, serviceState.mOperatorAlphaShortRaw)) return bl2;
            if (this.mNetworkRegistrationInfos.size() != serviceState.mNetworkRegistrationInfos.size()) return bl2;
            if (!this.mNetworkRegistrationInfos.containsAll(serviceState.mNetworkRegistrationInfos)) return bl2;
            if (this.mNrFrequencyRange != serviceState.mNrFrequencyRange) return bl2;
            if (this.mIsIwlanPreferred != serviceState.mIsIwlanPreferred) return bl2;
            return true;
        }
    }

    @UnsupportedAppUsage
    public void fillInNotifierBundle(Bundle bundle) {
        bundle.putParcelable("android.intent.extra.SERVICE_STATE", this);
        bundle.putInt("voiceRegState", this.mVoiceRegState);
        bundle.putInt("dataRegState", this.mDataRegState);
        bundle.putInt("dataRoamingType", this.getDataRoamingType());
        bundle.putInt("voiceRoamingType", this.getVoiceRoamingType());
        bundle.putString("operator-alpha-long", this.mVoiceOperatorAlphaLong);
        bundle.putString("operator-alpha-short", this.mVoiceOperatorAlphaShort);
        bundle.putString("operator-numeric", this.mVoiceOperatorNumeric);
        bundle.putString("data-operator-alpha-long", this.mDataOperatorAlphaLong);
        bundle.putString("data-operator-alpha-short", this.mDataOperatorAlphaShort);
        bundle.putString("data-operator-numeric", this.mDataOperatorNumeric);
        bundle.putBoolean("manual", this.mIsManualNetworkSelection);
        bundle.putInt("radioTechnology", this.getRilVoiceRadioTechnology());
        bundle.putInt("dataRadioTechnology", this.getRadioTechnology());
        bundle.putBoolean("cssIndicator", this.mCssIndicator);
        bundle.putInt("networkId", this.mNetworkId);
        bundle.putInt("systemId", this.mSystemId);
        bundle.putInt("cdmaRoamingIndicator", this.mCdmaRoamingIndicator);
        bundle.putInt("cdmaDefaultRoamingIndicator", this.mCdmaDefaultRoamingIndicator);
        bundle.putBoolean("emergencyOnly", this.mIsEmergencyOnly);
        bundle.putBoolean("isDataRoamingFromRegistration", this.getDataRoamingFromRegistration());
        bundle.putBoolean("isUsingCarrierAggregation", this.isUsingCarrierAggregation());
        bundle.putInt("LteEarfcnRsrpBoost", this.mLteEarfcnRsrpBoost);
        bundle.putInt("ChannelNumber", this.mChannelNumber);
        bundle.putIntArray("CellBandwidths", this.mCellBandwidths);
        bundle.putInt("mNrFrequencyRange", this.mNrFrequencyRange);
        bundle.putString("operator-alpha-long-raw", this.mOperatorAlphaLongRaw);
        bundle.putString("operator-alpha-short-raw", this.mOperatorAlphaShortRaw);
    }

    @UnsupportedAppUsage
    public int getCdmaDefaultRoamingIndicator() {
        return this.mCdmaDefaultRoamingIndicator;
    }

    @UnsupportedAppUsage
    public int getCdmaEriIconIndex() {
        return this.mCdmaEriIconIndex;
    }

    @UnsupportedAppUsage
    public int getCdmaEriIconMode() {
        return this.mCdmaEriIconMode;
    }

    public int getCdmaNetworkId() {
        return this.mNetworkId;
    }

    @UnsupportedAppUsage
    public int getCdmaRoamingIndicator() {
        return this.mCdmaRoamingIndicator;
    }

    public int getCdmaSystemId() {
        return this.mSystemId;
    }

    public int[] getCellBandwidths() {
        int[] arrn;
        int[] arrn2 = arrn = this.mCellBandwidths;
        if (arrn == null) {
            arrn2 = new int[]{};
        }
        return arrn2;
    }

    public int getChannelNumber() {
        return this.mChannelNumber;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getCssIndicator() {
        return (int)this.mCssIndicator;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getDataNetworkType() {
        NetworkRegistrationInfo networkRegistrationInfo = this.getNetworkRegistrationInfo(2, 2);
        NetworkRegistrationInfo networkRegistrationInfo2 = this.getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo != null && networkRegistrationInfo.isInService()) {
            if (networkRegistrationInfo2.isInService() && !this.mIsIwlanPreferred) {
                return networkRegistrationInfo2.getAccessNetworkTechnology();
            }
            return networkRegistrationInfo.getAccessNetworkTechnology();
        }
        int n = networkRegistrationInfo2 != null ? networkRegistrationInfo2.getAccessNetworkTechnology() : 0;
        return n;
    }

    public String getDataOperatorAlphaLong() {
        return this.mDataOperatorAlphaLong;
    }

    @UnsupportedAppUsage
    public String getDataOperatorAlphaShort() {
        return this.mDataOperatorAlphaShort;
    }

    @UnsupportedAppUsage
    public String getDataOperatorNumeric() {
        return this.mDataOperatorNumeric;
    }

    @UnsupportedAppUsage
    public int getDataRegState() {
        return this.mDataRegState;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean getDataRoaming() {
        boolean bl = this.getDataRoamingType() != 0;
        return bl;
    }

    public boolean getDataRoamingFromRegistration() {
        boolean bl = true;
        NetworkRegistrationInfo networkRegistrationInfo = this.getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo != null) {
            if (networkRegistrationInfo.getRegistrationState() != 5) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getDataRoamingType() {
        NetworkRegistrationInfo networkRegistrationInfo = this.getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo != null) {
            return networkRegistrationInfo.getRoamingType();
        }
        return 0;
    }

    public int getDuplexMode() {
        if (!ServiceState.isLte(this.getRilDataRadioTechnology())) {
            return 0;
        }
        return AccessNetworkUtils.getDuplexModeForEutranBand(AccessNetworkUtils.getOperatingBandForEarfcn(this.mChannelNumber));
    }

    public boolean getIsManualSelection() {
        return this.mIsManualNetworkSelection;
    }

    public int getLteEarfcnRsrpBoost() {
        return this.mLteEarfcnRsrpBoost;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public NetworkRegistrationInfo getNetworkRegistrationInfo(int n, int n2) {
        List<NetworkRegistrationInfo> list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            NetworkRegistrationInfo networkRegistrationInfo;
            Iterator<NetworkRegistrationInfo> iterator = this.mNetworkRegistrationInfos.iterator();
            do {
                if (iterator.hasNext()) continue;
                return null;
            } while ((networkRegistrationInfo = iterator.next()).getTransportType() != n2 || networkRegistrationInfo.getDomain() != n);
            return new NetworkRegistrationInfo(networkRegistrationInfo);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public List<NetworkRegistrationInfo> getNetworkRegistrationInfoList() {
        List<NetworkRegistrationInfo> list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            ArrayList<NetworkRegistrationInfo> arrayList = new ArrayList<NetworkRegistrationInfo>();
            Iterator<NetworkRegistrationInfo> iterator = this.mNetworkRegistrationInfos.iterator();
            while (iterator.hasNext()) {
                NetworkRegistrationInfo networkRegistrationInfo = iterator.next();
                NetworkRegistrationInfo networkRegistrationInfo2 = new NetworkRegistrationInfo(networkRegistrationInfo);
                arrayList.add(networkRegistrationInfo2);
            }
            return arrayList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public List<NetworkRegistrationInfo> getNetworkRegistrationInfoListForDomain(int n) {
        ArrayList<NetworkRegistrationInfo> arrayList = new ArrayList<NetworkRegistrationInfo>();
        List<NetworkRegistrationInfo> list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            Iterator<NetworkRegistrationInfo> iterator = this.mNetworkRegistrationInfos.iterator();
            while (iterator.hasNext()) {
                NetworkRegistrationInfo networkRegistrationInfo = iterator.next();
                if (networkRegistrationInfo.getDomain() != n) continue;
                NetworkRegistrationInfo networkRegistrationInfo2 = new NetworkRegistrationInfo(networkRegistrationInfo);
                arrayList.add(networkRegistrationInfo2);
            }
            return arrayList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public List<NetworkRegistrationInfo> getNetworkRegistrationInfoListForTransportType(int n) {
        ArrayList<NetworkRegistrationInfo> arrayList = new ArrayList<NetworkRegistrationInfo>();
        List<NetworkRegistrationInfo> list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            Iterator<NetworkRegistrationInfo> iterator = this.mNetworkRegistrationInfos.iterator();
            while (iterator.hasNext()) {
                NetworkRegistrationInfo networkRegistrationInfo = iterator.next();
                if (networkRegistrationInfo.getTransportType() != n) continue;
                NetworkRegistrationInfo networkRegistrationInfo2 = new NetworkRegistrationInfo(networkRegistrationInfo);
                arrayList.add(networkRegistrationInfo2);
            }
            return arrayList;
        }
    }

    public int getNrFrequencyRange() {
        return this.mNrFrequencyRange;
    }

    public int getNrState() {
        NetworkRegistrationInfo networkRegistrationInfo = this.getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo == null) {
            return -1;
        }
        return networkRegistrationInfo.getNrState();
    }

    public String getOperatorAlpha() {
        if (TextUtils.isEmpty(this.mVoiceOperatorAlphaLong)) {
            return this.mVoiceOperatorAlphaShort;
        }
        return this.mVoiceOperatorAlphaLong;
    }

    public String getOperatorAlphaLong() {
        return this.mVoiceOperatorAlphaLong;
    }

    public String getOperatorAlphaLongRaw() {
        return this.mOperatorAlphaLongRaw;
    }

    public String getOperatorAlphaShort() {
        return this.mVoiceOperatorAlphaShort;
    }

    public String getOperatorAlphaShortRaw() {
        return this.mOperatorAlphaShortRaw;
    }

    public String getOperatorNumeric() {
        return this.mVoiceOperatorNumeric;
    }

    @UnsupportedAppUsage
    public int getRadioTechnology() {
        Rlog.e(LOG_TAG, "ServiceState.getRadioTechnology() DEPRECATED will be removed *******");
        return this.getRilDataRadioTechnology();
    }

    @UnsupportedAppUsage
    public int getRilDataRadioTechnology() {
        return ServiceState.networkTypeToRilRadioTechnology(this.getDataNetworkType());
    }

    @UnsupportedAppUsage
    public int getRilVoiceRadioTechnology() {
        NetworkRegistrationInfo networkRegistrationInfo = this.getNetworkRegistrationInfo(1, 1);
        if (networkRegistrationInfo != null) {
            return ServiceState.networkTypeToRilRadioTechnology(networkRegistrationInfo.getAccessNetworkTechnology());
        }
        return 0;
    }

    public boolean getRoaming() {
        boolean bl = this.getVoiceRoaming() || this.getDataRoaming();
        return bl;
    }

    public int getState() {
        return this.getVoiceRegState();
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getVoiceNetworkType() {
        NetworkRegistrationInfo networkRegistrationInfo = this.getNetworkRegistrationInfo(1, 1);
        if (networkRegistrationInfo != null) {
            return networkRegistrationInfo.getAccessNetworkTechnology();
        }
        return 0;
    }

    @UnsupportedAppUsage
    public String getVoiceOperatorAlphaLong() {
        return this.mVoiceOperatorAlphaLong;
    }

    @UnsupportedAppUsage
    public String getVoiceOperatorAlphaShort() {
        return this.mVoiceOperatorAlphaShort;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public String getVoiceOperatorNumeric() {
        return this.mVoiceOperatorNumeric;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getVoiceRegState() {
        return this.mVoiceRegState;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean getVoiceRoaming() {
        boolean bl = this.getVoiceRoamingType() != 0;
        return bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public int getVoiceRoamingType() {
        NetworkRegistrationInfo networkRegistrationInfo = this.getNetworkRegistrationInfo(1, 1);
        if (networkRegistrationInfo != null) {
            return networkRegistrationInfo.getRoamingType();
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int hashCode() {
        List<NetworkRegistrationInfo> list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            return Objects.hash(this.mVoiceRegState, this.mDataRegState, this.mChannelNumber, Arrays.hashCode(this.mCellBandwidths), this.mVoiceOperatorAlphaLong, this.mVoiceOperatorAlphaShort, this.mVoiceOperatorNumeric, this.mDataOperatorAlphaLong, this.mDataOperatorAlphaShort, this.mDataOperatorNumeric, this.mIsManualNetworkSelection, this.mCssIndicator, this.mNetworkId, this.mSystemId, this.mCdmaRoamingIndicator, this.mCdmaDefaultRoamingIndicator, this.mCdmaEriIconIndex, this.mCdmaEriIconMode, this.mIsEmergencyOnly, this.mLteEarfcnRsrpBoost, this.mNetworkRegistrationInfos, this.mNrFrequencyRange, this.mOperatorAlphaLongRaw, this.mOperatorAlphaShortRaw, this.mIsIwlanPreferred);
        }
    }

    @UnsupportedAppUsage
    public boolean isEmergencyOnly() {
        return this.mIsEmergencyOnly;
    }

    public boolean isUsingCarrierAggregation() {
        Parcelable parcelable = this.getNetworkRegistrationInfo(2, 1);
        if (parcelable != null && (parcelable = ((NetworkRegistrationInfo)parcelable).getDataSpecificInfo()) != null) {
            return ((DataSpecificRegistrationInfo)parcelable).isUsingCarrierAggregation();
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public ServiceState sanitizeLocationInfo(boolean bl) {
        ServiceState serviceState = new ServiceState(this);
        List<NetworkRegistrationInfo> list = serviceState.mNetworkRegistrationInfos;
        // MONITORENTER : list
        List list2 = serviceState.mNetworkRegistrationInfos.stream().map(_$$Lambda$MLKtmRGKP3e0WU7x_KyS5_Vg8q4.INSTANCE).collect(Collectors.toList());
        serviceState.mNetworkRegistrationInfos.clear();
        serviceState.mNetworkRegistrationInfos.addAll(list2);
        // MONITOREXIT : list
        if (!bl) {
            return serviceState;
        }
        serviceState.mDataOperatorAlphaLong = null;
        serviceState.mDataOperatorAlphaShort = null;
        serviceState.mDataOperatorNumeric = null;
        serviceState.mVoiceOperatorAlphaLong = null;
        serviceState.mVoiceOperatorAlphaShort = null;
        serviceState.mVoiceOperatorNumeric = null;
        return serviceState;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setCdmaDefaultRoamingIndicator(int n) {
        this.mCdmaDefaultRoamingIndicator = n;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setCdmaEriIconIndex(int n) {
        this.mCdmaEriIconIndex = n;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setCdmaEriIconMode(int n) {
        this.mCdmaEriIconMode = n;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setCdmaRoamingIndicator(int n) {
        this.mCdmaRoamingIndicator = n;
    }

    public void setCdmaSystemAndNetworkId(int n, int n2) {
        this.mSystemId = n;
        this.mNetworkId = n2;
    }

    public void setCellBandwidths(int[] arrn) {
        this.mCellBandwidths = arrn;
    }

    public void setChannelNumber(int n) {
        this.mChannelNumber = n;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setCssIndicator(int n) {
        boolean bl = n != 0;
        this.mCssIndicator = bl;
    }

    public void setDataOperatorAlphaLong(String string2) {
        this.mDataOperatorAlphaLong = string2;
    }

    public void setDataOperatorName(String string2, String string3, String string4) {
        this.mDataOperatorAlphaLong = string2;
        this.mDataOperatorAlphaShort = string3;
        this.mDataOperatorNumeric = string4;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setDataRegState(int n) {
        this.mDataRegState = n;
    }

    @UnsupportedAppUsage
    public void setDataRoaming(boolean bl) {
        this.setDataRoamingType((int)bl);
    }

    public void setDataRoamingType(int n) {
        NetworkRegistrationInfo networkRegistrationInfo;
        NetworkRegistrationInfo networkRegistrationInfo2 = networkRegistrationInfo = this.getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo == null) {
            networkRegistrationInfo2 = new NetworkRegistrationInfo.Builder().setDomain(2).setTransportType(1).build();
        }
        networkRegistrationInfo2.setRoamingType(n);
        this.addNetworkRegistrationInfo(networkRegistrationInfo2);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setEmergencyOnly(boolean bl) {
        this.mIsEmergencyOnly = bl;
    }

    public void setIsManualSelection(boolean bl) {
        this.mIsManualNetworkSelection = bl;
    }

    public void setIsUsingCarrierAggregation(boolean bl) {
        Parcelable parcelable = this.getNetworkRegistrationInfo(2, 1);
        if (parcelable != null && (parcelable = ((NetworkRegistrationInfo)parcelable).getDataSpecificInfo()) != null) {
            ((DataSpecificRegistrationInfo)parcelable).setIsUsingCarrierAggregation(bl);
        }
    }

    public void setIwlanPreferred(boolean bl) {
        this.mIsIwlanPreferred = bl;
    }

    public void setLteEarfcnRsrpBoost(int n) {
        this.mLteEarfcnRsrpBoost = n;
    }

    public void setNrFrequencyRange(int n) {
        this.mNrFrequencyRange = n;
    }

    @UnsupportedAppUsage
    public void setOperatorAlphaLong(String string2) {
        this.mVoiceOperatorAlphaLong = string2;
        this.mDataOperatorAlphaLong = string2;
    }

    public void setOperatorAlphaLongRaw(String string2) {
        this.mOperatorAlphaLongRaw = string2;
    }

    public void setOperatorAlphaShortRaw(String string2) {
        this.mOperatorAlphaShortRaw = string2;
    }

    public void setOperatorName(String string2, String string3, String string4) {
        this.mVoiceOperatorAlphaLong = string2;
        this.mVoiceOperatorAlphaShort = string3;
        this.mVoiceOperatorNumeric = string4;
        this.mDataOperatorAlphaLong = string2;
        this.mDataOperatorAlphaShort = string3;
        this.mDataOperatorNumeric = string4;
    }

    public void setRilDataRadioTechnology(int n) {
        NetworkRegistrationInfo networkRegistrationInfo;
        Rlog.e(LOG_TAG, "ServiceState.setRilDataRadioTechnology() called. It's encouraged to use addNetworkRegistrationInfo() instead *******");
        NetworkRegistrationInfo networkRegistrationInfo2 = networkRegistrationInfo = this.getNetworkRegistrationInfo(2, 1);
        if (networkRegistrationInfo == null) {
            networkRegistrationInfo2 = new NetworkRegistrationInfo.Builder().setDomain(2).setTransportType(1).build();
        }
        networkRegistrationInfo2.setAccessNetworkTechnology(ServiceState.rilRadioTechnologyToNetworkType(n));
        this.addNetworkRegistrationInfo(networkRegistrationInfo2);
    }

    public void setRilVoiceRadioTechnology(int n) {
        NetworkRegistrationInfo networkRegistrationInfo;
        Rlog.e(LOG_TAG, "ServiceState.setRilVoiceRadioTechnology() called. It's encouraged to use addNetworkRegistrationInfo() instead *******");
        NetworkRegistrationInfo networkRegistrationInfo2 = networkRegistrationInfo = this.getNetworkRegistrationInfo(1, 1);
        if (networkRegistrationInfo == null) {
            networkRegistrationInfo2 = new NetworkRegistrationInfo.Builder().setDomain(1).setTransportType(1).build();
        }
        networkRegistrationInfo2.setAccessNetworkTechnology(ServiceState.rilRadioTechnologyToNetworkType(n));
        this.addNetworkRegistrationInfo(networkRegistrationInfo2);
    }

    public void setRoaming(boolean bl) {
        this.setVoiceRoaming(bl);
        this.setDataRoaming(bl);
    }

    public void setState(int n) {
        this.setVoiceRegState(n);
    }

    public void setStateOff() {
        this.init();
        this.mVoiceRegState = 3;
        this.mDataRegState = 3;
    }

    public void setStateOutOfService() {
        this.init();
    }

    public void setVoiceOperatorAlphaLong(String string2) {
        this.mVoiceOperatorAlphaLong = string2;
    }

    public void setVoiceOperatorName(String string2, String string3, String string4) {
        this.mVoiceOperatorAlphaLong = string2;
        this.mVoiceOperatorAlphaShort = string3;
        this.mVoiceOperatorNumeric = string4;
    }

    @UnsupportedAppUsage
    public void setVoiceRegState(int n) {
        this.mVoiceRegState = n;
    }

    @UnsupportedAppUsage
    public void setVoiceRoaming(boolean bl) {
        this.setVoiceRoamingType((int)bl);
    }

    public void setVoiceRoamingType(int n) {
        NetworkRegistrationInfo networkRegistrationInfo;
        NetworkRegistrationInfo networkRegistrationInfo2 = networkRegistrationInfo = this.getNetworkRegistrationInfo(1, 1);
        if (networkRegistrationInfo == null) {
            networkRegistrationInfo2 = new NetworkRegistrationInfo.Builder().setDomain(1).setTransportType(1).build();
        }
        networkRegistrationInfo2.setRoamingType(n);
        this.addNetworkRegistrationInfo(networkRegistrationInfo2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        List<NetworkRegistrationInfo> list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{mVoiceRegState=");
            stringBuilder.append(this.mVoiceRegState);
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("(");
            ((StringBuilder)charSequence).append(ServiceState.rilServiceStateToString(this.mVoiceRegState));
            ((StringBuilder)charSequence).append(")");
            stringBuilder.append(((StringBuilder)charSequence).toString());
            stringBuilder.append(", mDataRegState=");
            stringBuilder.append(this.mDataRegState);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("(");
            ((StringBuilder)charSequence).append(ServiceState.rilServiceStateToString(this.mDataRegState));
            ((StringBuilder)charSequence).append(")");
            stringBuilder.append(((StringBuilder)charSequence).toString());
            stringBuilder.append(", mChannelNumber=");
            stringBuilder.append(this.mChannelNumber);
            stringBuilder.append(", duplexMode()=");
            stringBuilder.append(this.getDuplexMode());
            stringBuilder.append(", mCellBandwidths=");
            stringBuilder.append(Arrays.toString(this.mCellBandwidths));
            stringBuilder.append(", mVoiceOperatorAlphaLong=");
            stringBuilder.append(this.mVoiceOperatorAlphaLong);
            stringBuilder.append(", mVoiceOperatorAlphaShort=");
            stringBuilder.append(this.mVoiceOperatorAlphaShort);
            stringBuilder.append(", mDataOperatorAlphaLong=");
            stringBuilder.append(this.mDataOperatorAlphaLong);
            stringBuilder.append(", mDataOperatorAlphaShort=");
            stringBuilder.append(this.mDataOperatorAlphaShort);
            stringBuilder.append(", isManualNetworkSelection=");
            stringBuilder.append(this.mIsManualNetworkSelection);
            charSequence = this.mIsManualNetworkSelection ? "(manual)" : "(automatic)";
            stringBuilder.append((String)charSequence);
            stringBuilder.append(", getRilVoiceRadioTechnology=");
            stringBuilder.append(this.getRilVoiceRadioTechnology());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("(");
            ((StringBuilder)charSequence).append(ServiceState.rilRadioTechnologyToString(this.getRilVoiceRadioTechnology()));
            ((StringBuilder)charSequence).append(")");
            stringBuilder.append(((StringBuilder)charSequence).toString());
            stringBuilder.append(", getRilDataRadioTechnology=");
            stringBuilder.append(this.getRilDataRadioTechnology());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("(");
            ((StringBuilder)charSequence).append(ServiceState.rilRadioTechnologyToString(this.getRilDataRadioTechnology()));
            ((StringBuilder)charSequence).append(")");
            stringBuilder.append(((StringBuilder)charSequence).toString());
            stringBuilder.append(", mCssIndicator=");
            charSequence = this.mCssIndicator ? "supported" : "unsupported";
            stringBuilder.append((String)charSequence);
            stringBuilder.append(", mNetworkId=");
            stringBuilder.append(this.mNetworkId);
            stringBuilder.append(", mSystemId=");
            stringBuilder.append(this.mSystemId);
            stringBuilder.append(", mCdmaRoamingIndicator=");
            stringBuilder.append(this.mCdmaRoamingIndicator);
            stringBuilder.append(", mCdmaDefaultRoamingIndicator=");
            stringBuilder.append(this.mCdmaDefaultRoamingIndicator);
            stringBuilder.append(", mIsEmergencyOnly=");
            stringBuilder.append(this.mIsEmergencyOnly);
            stringBuilder.append(", isUsingCarrierAggregation=");
            stringBuilder.append(this.isUsingCarrierAggregation());
            stringBuilder.append(", mLteEarfcnRsrpBoost=");
            stringBuilder.append(this.mLteEarfcnRsrpBoost);
            stringBuilder.append(", mNetworkRegistrationInfos=");
            stringBuilder.append(this.mNetworkRegistrationInfos);
            stringBuilder.append(", mNrFrequencyRange=");
            stringBuilder.append(this.mNrFrequencyRange);
            stringBuilder.append(", mOperatorAlphaLongRaw=");
            stringBuilder.append(this.mOperatorAlphaLongRaw);
            stringBuilder.append(", mOperatorAlphaShortRaw=");
            stringBuilder.append(this.mOperatorAlphaShortRaw);
            stringBuilder.append(", mIsIwlanPreferred=");
            stringBuilder.append(this.mIsIwlanPreferred);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mVoiceRegState);
        parcel.writeInt(this.mDataRegState);
        parcel.writeString(this.mVoiceOperatorAlphaLong);
        parcel.writeString(this.mVoiceOperatorAlphaShort);
        parcel.writeString(this.mVoiceOperatorNumeric);
        parcel.writeString(this.mDataOperatorAlphaLong);
        parcel.writeString(this.mDataOperatorAlphaShort);
        parcel.writeString(this.mDataOperatorNumeric);
        parcel.writeInt((int)this.mIsManualNetworkSelection);
        parcel.writeInt((int)this.mCssIndicator);
        parcel.writeInt(this.mNetworkId);
        parcel.writeInt(this.mSystemId);
        parcel.writeInt(this.mCdmaRoamingIndicator);
        parcel.writeInt(this.mCdmaDefaultRoamingIndicator);
        parcel.writeInt(this.mCdmaEriIconIndex);
        parcel.writeInt(this.mCdmaEriIconMode);
        parcel.writeInt((int)this.mIsEmergencyOnly);
        parcel.writeInt(this.mLteEarfcnRsrpBoost);
        List<NetworkRegistrationInfo> list = this.mNetworkRegistrationInfos;
        synchronized (list) {
            parcel.writeList(this.mNetworkRegistrationInfos);
        }
        parcel.writeInt(this.mChannelNumber);
        parcel.writeIntArray(this.mCellBandwidths);
        parcel.writeInt(this.mNrFrequencyRange);
        parcel.writeString(this.mOperatorAlphaLongRaw);
        parcel.writeString(this.mOperatorAlphaShortRaw);
        parcel.writeBoolean(this.mIsIwlanPreferred);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DuplexMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FrequencyRange {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RilRadioTechnology {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RoamingType {
    }

}

