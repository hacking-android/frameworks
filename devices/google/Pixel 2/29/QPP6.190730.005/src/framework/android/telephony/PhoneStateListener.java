/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.telephony.CallAttributes;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.DataConnectionRealTimeInfo;
import android.telephony.PhoneCapability;
import android.telephony.PhysicalChannelConfig;
import android.telephony.PreciseCallState;
import android.telephony.PreciseDataConnectionState;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$0s34qsuHFsa43jUHrTkD62ni6Ds;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$1M3m0i6211i2YjWyTDT7l0bJm3I;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$2VMO21pFQN_JN3kpn6vQN1zPFEU;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$2cMrwdqnKBpixpApeIX38rmRLak;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$4NHt5Shg_DHV_T1IxfcQLHP5_j0;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5J_sdvem6pUpdVwRdm8IbDhvuv8;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5Uf5OZWCyPD0lZtySzbYw18FWhU;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5rF2IFj8mrb7uZc0HMKiuCodUn0;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5t7yF_frkRH7MdItRlwmP00irsM;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5uu_05j4ojTh9mEHkN_ynQqQRGM;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$6czWSGzxct0CXPVO54T0aq05qls;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Bzok3Q_pjLC0O4ulkDfbWru0v6w;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$H1CbxYUcdxs1WggP_RRULTY01K8;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$HEcWn_J1WRb0wLERu2qoMIZDfjY;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Hbn6_eZxY2p3rjOfStodI04A8E8;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$IU278K5QbmReF_mbpcNVAvVlhFI;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$JalixlMNdjktPsNntP_JT9pymhs;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$M39is_Zyt8D7Camw2NS4EGTDn_s;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$OIAjnTzp_YIf6Y7jPFABi9BXZvs;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$OfwFKKtcQHRmtv70FCopw6FDAAU;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Q2A8FgYlU8_D6PD78tThGut_rTc;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Q_Cpm8aB8qYt8lGxD5PXek__4bA;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$RC2x2ijetA_pQrLa4QakzMBjh_k;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$TYOBpOfoS3xjFssrzOJyHTelndw;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$TqrkuLPlaG_ucU7VbLS4tnf8hG8;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$W65ui1dCCc_JnQa7gon1I7Bz7Sk;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$WYWtWHdkZDxBd9anjoxyZozPWHc;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$XyayAGWQZC2dNjwr697SfSGBBOc;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$YY3srkIkMm8vTSFJZHoiKzUUrGs;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$_CiOzgf6ys4EwlCYOVUsuz9YQ5c;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$aysbwPqxcLV_5w6LP0TzZu2D_ew;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$bELzxgwsPigyVKYkAXBO2BjcSm8;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$bI97h5HT_IYvguXIcngwUrpGxrw;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$d9DVwzLraeX80tegF_wEzf_k2FI;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$dUc3j82sK9P9Zpaq_91n9bk_Rpc;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$eYTgM6ABgThWqEatVha4ZuIpI0A;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$hxq77a5O_MUfoptHg15ipzFvMkI;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$i4r8mBqOfCy4bnbF_JG7ujDXEOQ;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$icX71zgNszuMfnDaCmahcqWacFM;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$ipH9N0fJiGE9EBJHahQeXcCZXzo;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jGj_qFMdpjbsKaUErqJEeOALEGo;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jNtyZYh5ZAuvyDZA_6f30zhW_dI;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jclAV5yU3RtV94suRvvhafvGuhw;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jlNX9JiqGSNg9W49vDcKucKdeCI;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$lHL69WZlO89JjNC1LLvFWp2OuKY;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nMiL2eSbUjYsM_AZ8joz_n4dLz0;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nR7W5ox6SCgPxtH9IRcENwKeFI4;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nnG75RvQ1_1KZGJk1ySeCH1JJRg;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nrGqSRBJrc3_EwotCDNwfKeizIo;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$oDAZqs8paeefe_3k_uRKV5plQW4;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$okPCYOx4UxYuvUHlM2iS425QGIg;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$t2gWJ_jA36kAdNXSmlzw85aU_tM;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$uC5syhzl229gIpaK7Jfs__OCJxQ;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$y_tK7my_uXPo_oQ7AytfnekGEbU;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$ygzOWFRiY4sZQ4WYUPIefqgiGvM;
import android.telephony._$$Lambda$PhoneStateListener$IPhoneStateListenerStub$yvQnAlFGg5EWDG2vcA9X_4xnalA;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsReasonInfo;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.IPhoneStateListener;
import dalvik.system.VMRuntime;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class PhoneStateListener {
    private static final boolean DBG = false;
    public static final int LISTEN_ACTIVE_DATA_SUBSCRIPTION_ID_CHANGE = 4194304;
    @SystemApi
    public static final int LISTEN_CALL_ATTRIBUTES_CHANGED = 67108864;
    @SystemApi
    public static final int LISTEN_CALL_DISCONNECT_CAUSES = 33554432;
    public static final int LISTEN_CALL_FORWARDING_INDICATOR = 8;
    public static final int LISTEN_CALL_STATE = 32;
    public static final int LISTEN_CARRIER_NETWORK_CHANGE = 65536;
    public static final int LISTEN_CELL_INFO = 1024;
    public static final int LISTEN_CELL_LOCATION = 16;
    public static final int LISTEN_DATA_ACTIVATION_STATE = 262144;
    public static final int LISTEN_DATA_ACTIVITY = 128;
    @Deprecated
    public static final int LISTEN_DATA_CONNECTION_REAL_TIME_INFO = 8192;
    public static final int LISTEN_DATA_CONNECTION_STATE = 64;
    public static final int LISTEN_EMERGENCY_NUMBER_LIST = 16777216;
    @SystemApi
    public static final int LISTEN_IMS_CALL_DISCONNECT_CAUSES = 134217728;
    public static final int LISTEN_MESSAGE_WAITING_INDICATOR = 4;
    public static final int LISTEN_NONE = 0;
    @Deprecated
    public static final int LISTEN_OEM_HOOK_RAW_EVENT = 32768;
    public static final int LISTEN_OTASP_CHANGED = 512;
    public static final int LISTEN_PHONE_CAPABILITY_CHANGE = 2097152;
    public static final int LISTEN_PHYSICAL_CHANNEL_CONFIGURATION = 1048576;
    @SystemApi
    public static final int LISTEN_PRECISE_CALL_STATE = 2048;
    @SystemApi
    public static final int LISTEN_PRECISE_DATA_CONNECTION_STATE = 4096;
    @SystemApi
    public static final int LISTEN_RADIO_POWER_STATE_CHANGED = 8388608;
    public static final int LISTEN_SERVICE_STATE = 1;
    @Deprecated
    public static final int LISTEN_SIGNAL_STRENGTH = 2;
    public static final int LISTEN_SIGNAL_STRENGTHS = 256;
    @SystemApi
    public static final int LISTEN_SRVCC_STATE_CHANGED = 16384;
    public static final int LISTEN_USER_MOBILE_DATA_STATE = 524288;
    @SystemApi
    public static final int LISTEN_VOICE_ACTIVATION_STATE = 131072;
    private static final String LOG_TAG = "PhoneStateListener";
    @UnsupportedAppUsage
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public final IPhoneStateListener callback;
    @UnsupportedAppUsage
    protected Integer mSubId;

    public PhoneStateListener() {
        this(null, Looper.myLooper());
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public PhoneStateListener(Looper looper) {
        this(null, looper);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public PhoneStateListener(Integer n) {
        this(n, Looper.myLooper());
        if (n != null && VMRuntime.getRuntime().getTargetSdkVersion() >= 29) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PhoneStateListener with subId: ");
            stringBuilder.append(n);
            stringBuilder.append(" is not supported, use default constructor");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public PhoneStateListener(Integer n, Looper object) {
        this(n, new HandlerExecutor(new Handler((Looper)object)));
        if (n != null && VMRuntime.getRuntime().getTargetSdkVersion() >= 29) {
            object = new StringBuilder();
            ((StringBuilder)object).append("PhoneStateListener with subId: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" is not supported, use default constructor");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

    private PhoneStateListener(Integer n, Executor executor) {
        if (executor != null) {
            this.mSubId = n;
            this.callback = new IPhoneStateListenerStub(this, executor);
            return;
        }
        throw new IllegalArgumentException("PhoneStateListener Executor must be non-null");
    }

    public PhoneStateListener(Executor executor) {
        this(null, executor);
    }

    private void log(String string2) {
        Rlog.d(LOG_TAG, string2);
    }

    public void onActiveDataSubscriptionIdChanged(int n) {
    }

    @SystemApi
    public void onCallAttributesChanged(CallAttributes callAttributes) {
    }

    @SystemApi
    public void onCallDisconnectCauseChanged(int n, int n2) {
    }

    public void onCallForwardingIndicatorChanged(boolean bl) {
    }

    public void onCallStateChanged(int n, String string2) {
    }

    public void onCarrierNetworkChange(boolean bl) {
    }

    public void onCellInfoChanged(List<CellInfo> list) {
    }

    public void onCellLocationChanged(CellLocation cellLocation) {
    }

    public void onDataActivationStateChanged(int n) {
    }

    public void onDataActivity(int n) {
    }

    @UnsupportedAppUsage
    public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
    }

    public void onDataConnectionStateChanged(int n) {
    }

    public void onDataConnectionStateChanged(int n, int n2) {
    }

    public void onEmergencyNumberListChanged(Map<Integer, List<EmergencyNumber>> map) {
    }

    @SystemApi
    public void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) {
    }

    public void onMessageWaitingIndicatorChanged(boolean bl) {
    }

    @UnsupportedAppUsage
    public void onOemHookRawEvent(byte[] arrby) {
    }

    @UnsupportedAppUsage
    public void onOtaspChanged(int n) {
    }

    public void onPhoneCapabilityChanged(PhoneCapability phoneCapability) {
    }

    public void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> list) {
    }

    @SystemApi
    public void onPreciseCallStateChanged(PreciseCallState preciseCallState) {
    }

    @SystemApi
    public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) {
    }

    @SystemApi
    public void onRadioPowerStateChanged(int n) {
    }

    public void onServiceStateChanged(ServiceState serviceState) {
    }

    @Deprecated
    public void onSignalStrengthChanged(int n) {
    }

    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
    }

    @SystemApi
    public void onSrvccStateChanged(int n) {
    }

    public void onUserMobileDataStateChanged(boolean bl) {
    }

    @SystemApi
    public void onVoiceActivationStateChanged(int n) {
    }

    private static class IPhoneStateListenerStub
    extends IPhoneStateListener.Stub {
        private Executor mExecutor;
        private WeakReference<PhoneStateListener> mPhoneStateListenerWeakRef;

        IPhoneStateListenerStub(PhoneStateListener phoneStateListener, Executor executor) {
            this.mPhoneStateListenerWeakRef = new WeakReference<PhoneStateListener>(phoneStateListener);
            this.mExecutor = executor;
        }

        static /* synthetic */ void lambda$onActiveDataSubIdChanged$52(PhoneStateListener phoneStateListener, int n) {
            phoneStateListener.onActiveDataSubscriptionIdChanged(n);
        }

        static /* synthetic */ void lambda$onCallAttributesChanged$50(PhoneStateListener phoneStateListener, CallAttributes callAttributes) {
            phoneStateListener.onCallAttributesChanged(callAttributes);
        }

        static /* synthetic */ void lambda$onCallDisconnectCauseChanged$24(PhoneStateListener phoneStateListener, int n, int n2) {
            phoneStateListener.onCallDisconnectCauseChanged(n, n2);
        }

        static /* synthetic */ void lambda$onCallForwardingIndicatorChanged$6(PhoneStateListener phoneStateListener, boolean bl) {
            phoneStateListener.onCallForwardingIndicatorChanged(bl);
        }

        static /* synthetic */ void lambda$onCallStateChanged$10(PhoneStateListener phoneStateListener, int n, String string2) {
            phoneStateListener.onCallStateChanged(n, string2);
        }

        static /* synthetic */ void lambda$onCarrierNetworkChange$40(PhoneStateListener phoneStateListener, boolean bl) {
            phoneStateListener.onCarrierNetworkChange(bl);
        }

        static /* synthetic */ void lambda$onCellInfoChanged$20(PhoneStateListener phoneStateListener, List list) {
            phoneStateListener.onCellInfoChanged(list);
        }

        static /* synthetic */ void lambda$onCellLocationChanged$8(PhoneStateListener phoneStateListener, CellLocation cellLocation) {
            phoneStateListener.onCellLocationChanged(cellLocation);
        }

        static /* synthetic */ void lambda$onDataActivationStateChanged$34(PhoneStateListener phoneStateListener, int n) {
            phoneStateListener.onDataActivationStateChanged(n);
        }

        static /* synthetic */ void lambda$onDataActivity$14(PhoneStateListener phoneStateListener, int n) {
            phoneStateListener.onDataActivity(n);
        }

        static /* synthetic */ void lambda$onDataConnectionRealTimeInfoChanged$28(PhoneStateListener phoneStateListener, DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
            phoneStateListener.onDataConnectionRealTimeInfoChanged(dataConnectionRealTimeInfo);
        }

        static /* synthetic */ void lambda$onDataConnectionStateChanged$12(PhoneStateListener phoneStateListener, int n, int n2) {
            phoneStateListener.onDataConnectionStateChanged(n, n2);
            phoneStateListener.onDataConnectionStateChanged(n);
        }

        static /* synthetic */ void lambda$onEmergencyNumberListChanged$44(PhoneStateListener phoneStateListener, Map map) {
            phoneStateListener.onEmergencyNumberListChanged(map);
        }

        static /* synthetic */ void lambda$onImsCallDisconnectCauseChanged$54(PhoneStateListener phoneStateListener, ImsReasonInfo imsReasonInfo) {
            phoneStateListener.onImsCallDisconnectCauseChanged(imsReasonInfo);
        }

        static /* synthetic */ void lambda$onMessageWaitingIndicatorChanged$4(PhoneStateListener phoneStateListener, boolean bl) {
            phoneStateListener.onMessageWaitingIndicatorChanged(bl);
        }

        static /* synthetic */ void lambda$onOemHookRawEvent$38(PhoneStateListener phoneStateListener, byte[] arrby) {
            phoneStateListener.onOemHookRawEvent(arrby);
        }

        static /* synthetic */ void lambda$onOtaspChanged$18(PhoneStateListener phoneStateListener, int n) {
            phoneStateListener.onOtaspChanged(n);
        }

        static /* synthetic */ void lambda$onPhoneCapabilityChanged$46(PhoneStateListener phoneStateListener, PhoneCapability phoneCapability) {
            phoneStateListener.onPhoneCapabilityChanged(phoneCapability);
        }

        static /* synthetic */ void lambda$onPhysicalChannelConfigurationChanged$42(PhoneStateListener phoneStateListener, List list) {
            phoneStateListener.onPhysicalChannelConfigurationChanged(list);
        }

        static /* synthetic */ void lambda$onPreciseCallStateChanged$22(PhoneStateListener phoneStateListener, PreciseCallState preciseCallState) {
            phoneStateListener.onPreciseCallStateChanged(preciseCallState);
        }

        static /* synthetic */ void lambda$onPreciseDataConnectionStateChanged$26(PhoneStateListener phoneStateListener, PreciseDataConnectionState preciseDataConnectionState) {
            phoneStateListener.onPreciseDataConnectionStateChanged(preciseDataConnectionState);
        }

        static /* synthetic */ void lambda$onRadioPowerStateChanged$48(PhoneStateListener phoneStateListener, int n) {
            phoneStateListener.onRadioPowerStateChanged(n);
        }

        static /* synthetic */ void lambda$onServiceStateChanged$0(PhoneStateListener phoneStateListener, ServiceState serviceState) {
            phoneStateListener.onServiceStateChanged(serviceState);
        }

        static /* synthetic */ void lambda$onSignalStrengthChanged$2(PhoneStateListener phoneStateListener, int n) {
            phoneStateListener.onSignalStrengthChanged(n);
        }

        static /* synthetic */ void lambda$onSignalStrengthsChanged$16(PhoneStateListener phoneStateListener, SignalStrength signalStrength) {
            phoneStateListener.onSignalStrengthsChanged(signalStrength);
        }

        static /* synthetic */ void lambda$onSrvccStateChanged$30(PhoneStateListener phoneStateListener, int n) {
            phoneStateListener.onSrvccStateChanged(n);
        }

        static /* synthetic */ void lambda$onUserMobileDataStateChanged$36(PhoneStateListener phoneStateListener, boolean bl) {
            phoneStateListener.onUserMobileDataStateChanged(bl);
        }

        static /* synthetic */ void lambda$onVoiceActivationStateChanged$32(PhoneStateListener phoneStateListener, int n) {
            phoneStateListener.onVoiceActivationStateChanged(n);
        }

        public /* synthetic */ void lambda$onActiveDataSubIdChanged$53$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nnG75RvQ1_1KZGJk1ySeCH1JJRg(phoneStateListener, n));
        }

        public /* synthetic */ void lambda$onCallAttributesChanged$51$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, CallAttributes callAttributes) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5t7yF_frkRH7MdItRlwmP00irsM(phoneStateListener, callAttributes));
        }

        public /* synthetic */ void lambda$onCallDisconnectCauseChanged$25$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n, int n2) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$hxq77a5O_MUfoptHg15ipzFvMkI(phoneStateListener, n, n2));
        }

        public /* synthetic */ void lambda$onCallForwardingIndicatorChanged$7$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, boolean bl) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$WYWtWHdkZDxBd9anjoxyZozPWHc(phoneStateListener, bl));
        }

        public /* synthetic */ void lambda$onCallStateChanged$11$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n, String string2) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$6czWSGzxct0CXPVO54T0aq05qls(phoneStateListener, n, string2));
        }

        public /* synthetic */ void lambda$onCarrierNetworkChange$41$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, boolean bl) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jlNX9JiqGSNg9W49vDcKucKdeCI(phoneStateListener, bl));
        }

        public /* synthetic */ void lambda$onCellInfoChanged$21$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, List list) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Q2A8FgYlU8_D6PD78tThGut_rTc(phoneStateListener, list));
        }

        public /* synthetic */ void lambda$onCellLocationChanged$9$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, CellLocation cellLocation) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$2cMrwdqnKBpixpApeIX38rmRLak(phoneStateListener, cellLocation));
        }

        public /* synthetic */ void lambda$onDataActivationStateChanged$35$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$W65ui1dCCc_JnQa7gon1I7Bz7Sk(phoneStateListener, n));
        }

        public /* synthetic */ void lambda$onDataActivity$15$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$JalixlMNdjktPsNntP_JT9pymhs(phoneStateListener, n));
        }

        public /* synthetic */ void lambda$onDataConnectionRealTimeInfoChanged$29$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$IU278K5QbmReF_mbpcNVAvVlhFI(phoneStateListener, dataConnectionRealTimeInfo));
        }

        public /* synthetic */ void lambda$onDataConnectionStateChanged$13$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n, int n2) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$dUc3j82sK9P9Zpaq_91n9bk_Rpc(phoneStateListener, n, n2));
        }

        public /* synthetic */ void lambda$onEmergencyNumberListChanged$45$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, Map map) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jGj_qFMdpjbsKaUErqJEeOALEGo(phoneStateListener, map));
        }

        public /* synthetic */ void lambda$onImsCallDisconnectCauseChanged$55$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, ImsReasonInfo imsReasonInfo) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$eYTgM6ABgThWqEatVha4ZuIpI0A(phoneStateListener, imsReasonInfo));
        }

        public /* synthetic */ void lambda$onMessageWaitingIndicatorChanged$5$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, boolean bl) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$TqrkuLPlaG_ucU7VbLS4tnf8hG8(phoneStateListener, bl));
        }

        public /* synthetic */ void lambda$onOemHookRawEvent$39$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, byte[] arrby) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jclAV5yU3RtV94suRvvhafvGuhw(phoneStateListener, arrby));
        }

        public /* synthetic */ void lambda$onOtaspChanged$19$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$H1CbxYUcdxs1WggP_RRULTY01K8(phoneStateListener, n));
        }

        public /* synthetic */ void lambda$onPhoneCapabilityChanged$47$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, PhoneCapability phoneCapability) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$lHL69WZlO89JjNC1LLvFWp2OuKY(phoneStateListener, phoneCapability));
        }

        public /* synthetic */ void lambda$onPhysicalChannelConfigurationChanged$43$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, List list) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nMiL2eSbUjYsM_AZ8joz_n4dLz0(phoneStateListener, list));
        }

        public /* synthetic */ void lambda$onPreciseCallStateChanged$23$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, PreciseCallState preciseCallState) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$4NHt5Shg_DHV_T1IxfcQLHP5_j0(phoneStateListener, preciseCallState));
        }

        public /* synthetic */ void lambda$onPreciseDataConnectionStateChanged$27$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, PreciseDataConnectionState preciseDataConnectionState) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$HEcWn_J1WRb0wLERu2qoMIZDfjY(phoneStateListener, preciseDataConnectionState));
        }

        public /* synthetic */ void lambda$onRadioPowerStateChanged$49$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$bI97h5HT_IYvguXIcngwUrpGxrw(phoneStateListener, n));
        }

        public /* synthetic */ void lambda$onServiceStateChanged$1$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, ServiceState serviceState) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nrGqSRBJrc3_EwotCDNwfKeizIo(phoneStateListener, serviceState));
        }

        public /* synthetic */ void lambda$onSignalStrengthChanged$3$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5J_sdvem6pUpdVwRdm8IbDhvuv8(phoneStateListener, n));
        }

        public /* synthetic */ void lambda$onSignalStrengthsChanged$17$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, SignalStrength signalStrength) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$0s34qsuHFsa43jUHrTkD62ni6Ds(phoneStateListener, signalStrength));
        }

        public /* synthetic */ void lambda$onSrvccStateChanged$31$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$ygzOWFRiY4sZQ4WYUPIefqgiGvM(phoneStateListener, n));
        }

        public /* synthetic */ void lambda$onUserMobileDataStateChanged$37$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, boolean bl) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5Uf5OZWCyPD0lZtySzbYw18FWhU(phoneStateListener, bl));
        }

        public /* synthetic */ void lambda$onVoiceActivationStateChanged$33$PhoneStateListener$IPhoneStateListenerStub(PhoneStateListener phoneStateListener, int n) throws Exception {
            this.mExecutor.execute(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$y_tK7my_uXPo_oQ7AytfnekGEbU(phoneStateListener, n));
        }

        @Override
        public void onActiveDataSubIdChanged(int n) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$ipH9N0fJiGE9EBJHahQeXcCZXzo(this, phoneStateListener, n));
        }

        @Override
        public void onCallAttributesChanged(CallAttributes callAttributes) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Q_Cpm8aB8qYt8lGxD5PXek__4bA(this, phoneStateListener, callAttributes));
        }

        @Override
        public void onCallDisconnectCauseChanged(int n, int n2) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$icX71zgNszuMfnDaCmahcqWacFM(this, phoneStateListener, n, n2));
        }

        @Override
        public void onCallForwardingIndicatorChanged(boolean bl) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$1M3m0i6211i2YjWyTDT7l0bJm3I(this, phoneStateListener, bl));
        }

        @Override
        public void onCallStateChanged(int n, String string2) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$oDAZqs8paeefe_3k_uRKV5plQW4(this, phoneStateListener, n, string2));
        }

        @Override
        public void onCarrierNetworkChange(boolean bl) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$YY3srkIkMm8vTSFJZHoiKzUUrGs(this, phoneStateListener, bl));
        }

        @Override
        public void onCellInfoChanged(List<CellInfo> list) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$yvQnAlFGg5EWDG2vcA9X_4xnalA(this, phoneStateListener, list));
        }

        @Override
        public void onCellLocationChanged(Bundle object) {
            object = CellLocation.newFromBundle((Bundle)object);
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Hbn6_eZxY2p3rjOfStodI04A8E8(this, phoneStateListener, (CellLocation)object));
        }

        @Override
        public void onDataActivationStateChanged(int n) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$t2gWJ_jA36kAdNXSmlzw85aU_tM(this, phoneStateListener, n));
        }

        @Override
        public void onDataActivity(int n) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$XyayAGWQZC2dNjwr697SfSGBBOc(this, phoneStateListener, n));
        }

        @Override
        public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$OfwFKKtcQHRmtv70FCopw6FDAAU(this, phoneStateListener, dataConnectionRealTimeInfo));
        }

        @Override
        public void onDataConnectionStateChanged(int n, int n2) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$2VMO21pFQN_JN3kpn6vQN1zPFEU(this, phoneStateListener, n, n2));
        }

        @Override
        public void onEmergencyNumberListChanged(Map map) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$d9DVwzLraeX80tegF_wEzf_k2FI(this, phoneStateListener, map));
        }

        @Override
        public void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Bzok3Q_pjLC0O4ulkDfbWru0v6w(this, phoneStateListener, imsReasonInfo));
        }

        @Override
        public void onMessageWaitingIndicatorChanged(boolean bl) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$okPCYOx4UxYuvUHlM2iS425QGIg(this, phoneStateListener, bl));
        }

        @Override
        public void onOemHookRawEvent(byte[] arrby) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jNtyZYh5ZAuvyDZA_6f30zhW_dI(this, phoneStateListener, arrby));
        }

        @Override
        public void onOtaspChanged(int n) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$i4r8mBqOfCy4bnbF_JG7ujDXEOQ(this, phoneStateListener, n));
        }

        @Override
        public void onPhoneCapabilityChanged(PhoneCapability phoneCapability) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$_CiOzgf6ys4EwlCYOVUsuz9YQ5c(this, phoneStateListener, phoneCapability));
        }

        @Override
        public void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> list) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$OIAjnTzp_YIf6Y7jPFABi9BXZvs(this, phoneStateListener, list));
        }

        @Override
        public void onPreciseCallStateChanged(PreciseCallState preciseCallState) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$bELzxgwsPigyVKYkAXBO2BjcSm8(this, phoneStateListener, preciseCallState));
        }

        @Override
        public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$RC2x2ijetA_pQrLa4QakzMBjh_k(this, phoneStateListener, preciseDataConnectionState));
        }

        @Override
        public void onRadioPowerStateChanged(int n) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$TYOBpOfoS3xjFssrzOJyHTelndw(this, phoneStateListener, n));
        }

        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$uC5syhzl229gIpaK7Jfs__OCJxQ(this, phoneStateListener, serviceState));
        }

        @Override
        public void onSignalStrengthChanged(int n) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$M39is_Zyt8D7Camw2NS4EGTDn_s(this, phoneStateListener, n));
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$aysbwPqxcLV_5w6LP0TzZu2D_ew(this, phoneStateListener, signalStrength));
        }

        @Override
        public void onSrvccStateChanged(int n) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nR7W5ox6SCgPxtH9IRcENwKeFI4(this, phoneStateListener, n));
        }

        @Override
        public void onUserMobileDataStateChanged(boolean bl) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5uu_05j4ojTh9mEHkN_ynQqQRGM(this, phoneStateListener, bl));
        }

        @Override
        public void onVoiceActivationStateChanged(int n) {
            PhoneStateListener phoneStateListener = (PhoneStateListener)this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5rF2IFj8mrb7uZc0HMKiuCodUn0(this, phoneStateListener, n));
        }
    }

}

