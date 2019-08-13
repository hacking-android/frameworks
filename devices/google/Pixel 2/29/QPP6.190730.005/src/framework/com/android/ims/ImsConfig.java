/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.telephony.Rlog;
import android.telephony.ims.ProvisioningManager;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsConfigCallback;
import com.android.ims.ImsException;
import java.util.concurrent.Executor;

@Deprecated
public class ImsConfig {
    public static final String ACTION_IMS_CONFIG_CHANGED = "com.android.intent.action.IMS_CONFIG_CHANGED";
    public static final String ACTION_IMS_FEATURE_CHANGED = "com.android.intent.action.IMS_FEATURE_CHANGED";
    public static final String EXTRA_CHANGED_ITEM = "item";
    public static final String EXTRA_NEW_VALUE = "value";
    private static final String TAG = "ImsConfig";
    private boolean DBG = true;
    private final IImsConfig miConfig;

    public ImsConfig(IImsConfig iImsConfig) {
        this.miConfig = iImsConfig;
    }

    private Executor getThreadExecutor() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        return new HandlerExecutor(new Handler(Looper.myLooper()));
    }

    public void addConfigCallback(ProvisioningManager.Callback callback) throws ImsException {
        callback.setExecutor(this.getThreadExecutor());
        this.addConfigCallback(callback.getBinder());
    }

    public void addConfigCallback(IImsConfigCallback iImsConfigCallback) throws ImsException {
        if (this.DBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("addConfigCallback: ");
            stringBuilder.append(iImsConfigCallback);
            Rlog.d(TAG, stringBuilder.toString());
        }
        try {
            this.miConfig.addImsConfigCallback(iImsConfigCallback);
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("addConfigCallback()", remoteException, 131);
        }
    }

    public int getConfigInt(int n) throws ImsException {
        int n2;
        block2 : {
            StringBuilder stringBuilder;
            try {
                n2 = this.miConfig.getConfigInt(n);
                if (!this.DBG) break block2;
                stringBuilder = new StringBuilder();
                stringBuilder.append("getInt(): item = ");
            }
            catch (RemoteException remoteException) {
                throw new ImsException("getInt()", remoteException, 131);
            }
            stringBuilder.append(n);
            stringBuilder.append(", ret =");
            stringBuilder.append(n2);
            Rlog.d(TAG, stringBuilder.toString());
        }
        return n2;
    }

    public String getConfigString(int n) throws ImsException {
        String string2;
        block2 : {
            StringBuilder stringBuilder;
            try {
                string2 = this.miConfig.getConfigString(n);
                if (!this.DBG) break block2;
                stringBuilder = new StringBuilder();
                stringBuilder.append("getConfigString(): item = ");
            }
            catch (RemoteException remoteException) {
                throw new ImsException("getConfigString()", remoteException, 131);
            }
            stringBuilder.append(n);
            stringBuilder.append(", ret =");
            stringBuilder.append(string2);
            Rlog.d(TAG, stringBuilder.toString());
        }
        return string2;
    }

    public String getProvisionedStringValue(int n) throws ImsException {
        return this.getConfigString(n);
    }

    public int getProvisionedValue(int n) throws ImsException {
        return this.getConfigInt(n);
    }

    public boolean isBinderAlive() {
        return this.miConfig.asBinder().isBinderAlive();
    }

    public void removeConfigCallback(IImsConfigCallback iImsConfigCallback) throws ImsException {
        if (this.DBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removeConfigCallback: ");
            stringBuilder.append(iImsConfigCallback);
            Rlog.d(TAG, stringBuilder.toString());
        }
        try {
            this.miConfig.removeImsConfigCallback(iImsConfigCallback);
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("removeConfigCallback()", remoteException, 131);
        }
    }

    public int setConfig(int n, int n2) throws ImsException {
        int n3;
        block3 : {
            StringBuilder stringBuilder;
            if (this.DBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("setConfig(): item = ");
                stringBuilder.append(n);
                stringBuilder.append("value = ");
                stringBuilder.append(n2);
                Rlog.d(TAG, stringBuilder.toString());
            }
            try {
                n3 = this.miConfig.setConfigInt(n, n2);
                if (!this.DBG) break block3;
                stringBuilder = new StringBuilder();
                stringBuilder.append("setConfig(): item = ");
            }
            catch (RemoteException remoteException) {
                throw new ImsException("setConfig()", remoteException, 131);
            }
            stringBuilder.append(n);
            stringBuilder.append(" value = ");
            stringBuilder.append(n2);
            stringBuilder.append(" ret = ");
            stringBuilder.append(n3);
            Rlog.d(TAG, stringBuilder.toString());
        }
        return n3;
    }

    public int setConfig(int n, String string2) throws ImsException {
        int n2;
        block3 : {
            StringBuilder stringBuilder;
            if (this.DBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("setConfig(): item = ");
                stringBuilder.append(n);
                stringBuilder.append("value = ");
                stringBuilder.append(string2);
                Rlog.d(TAG, stringBuilder.toString());
            }
            try {
                n2 = this.miConfig.setConfigString(n, string2);
                if (!this.DBG) break block3;
                stringBuilder = new StringBuilder();
                stringBuilder.append("setConfig(): item = ");
            }
            catch (RemoteException remoteException) {
                throw new ImsException("setConfig()", remoteException, 131);
            }
            stringBuilder.append(n);
            stringBuilder.append(" value = ");
            stringBuilder.append(string2);
            stringBuilder.append(" ret = ");
            stringBuilder.append(n2);
            Rlog.d(TAG, stringBuilder.toString());
        }
        return n2;
    }

    public int setProvisionedStringValue(int n, String string2) throws ImsException {
        return this.setConfig(n, string2);
    }

    public int setProvisionedValue(int n, int n2) throws ImsException {
        return this.setConfig(n, n2);
    }

    public static class ConfigConstants {
        public static final int AMR_BANDWIDTH_EFFICIENT_PT = 50;
        public static final int AMR_DEFAULT_MODE = 53;
        public static final int AMR_OCTET_ALIGNED_PT = 49;
        public static final int AMR_WB_BANDWIDTH_EFFICIENT_PT = 48;
        public static final int AMR_WB_OCTET_ALIGNED_PT = 47;
        public static final int AVAILABILITY_CACHE_EXPIRATION = 19;
        public static final int CANCELLATION_TIMER = 4;
        public static final int CAPABILITIES_CACHE_EXPIRATION = 18;
        public static final int CAPABILITIES_POLL_INTERVAL = 20;
        public static final int CAPABILITY_DISCOVERY_ENABLED = 17;
        public static final int CAPAB_POLL_LIST_SUB_EXP = 23;
        public static final int CONFIG_START = 0;
        public static final int DOMAIN_NAME = 12;
        public static final int DTMF_NB_PT = 52;
        public static final int DTMF_WB_PT = 51;
        public static final int EAB_SETTING_ENABLED = 25;
        public static final int GZIP_FLAG = 24;
        public static final int KEEP_ALIVE_ENABLED = 32;
        public static final int LBO_PCSCF_ADDRESS = 31;
        public static final int LVC_SETTING_ENABLED = 11;
        public static final int MAX_NUMENTRIES_IN_RCL = 22;
        public static final int MIN_SE = 3;
        public static final int MOBILE_DATA_ENABLED = 29;
        public static final int PROVISIONED_CONFIG_END = 66;
        public static final int PROVISIONED_CONFIG_START = 0;
        public static final int PUBLISH_TIMER = 15;
        public static final int PUBLISH_TIMER_EXTENDED = 16;
        public static final int REGISTRATION_RETRY_BASE_TIME_SEC = 33;
        public static final int REGISTRATION_RETRY_MAX_TIME_SEC = 34;
        public static final int RTT_SETTING_ENABLED = 66;
        public static final int SILENT_REDIAL_ENABLE = 6;
        public static final int SIP_ACK_RECEIPT_WAIT_TIME_MSEC = 43;
        public static final int SIP_ACK_RETX_WAIT_TIME_MSEC = 44;
        public static final int SIP_INVITE_REQ_RETX_INTERVAL_MSEC = 37;
        public static final int SIP_INVITE_RSP_RETX_INTERVAL_MSEC = 42;
        public static final int SIP_INVITE_RSP_RETX_WAIT_TIME_MSEC = 39;
        public static final int SIP_INVITE_RSP_WAIT_TIME_MSEC = 38;
        public static final int SIP_NON_INVITE_REQ_RETX_INTERVAL_MSEC = 40;
        public static final int SIP_NON_INVITE_REQ_RETX_WAIT_TIME_MSEC = 45;
        public static final int SIP_NON_INVITE_RSP_RETX_WAIT_TIME_MSEC = 46;
        public static final int SIP_NON_INVITE_TXN_TIMEOUT_TIMER_MSEC = 41;
        public static final int SIP_SESSION_TIMER = 2;
        public static final int SIP_T1_TIMER = 7;
        public static final int SIP_T2_TIMER = 8;
        public static final int SIP_TF_TIMER = 9;
        public static final int SMS_FORMAT = 13;
        public static final int SMS_OVER_IP = 14;
        public static final int SMS_PSI = 54;
        public static final int SOURCE_THROTTLE_PUBLISH = 21;
        public static final int SPEECH_END_PORT = 36;
        public static final int SPEECH_START_PORT = 35;
        public static final int TDELAY = 5;
        public static final int TH_1x = 59;
        public static final int TH_LTE1 = 56;
        public static final int TH_LTE2 = 57;
        public static final int TH_LTE3 = 58;
        public static final int T_EPDG_1X = 64;
        public static final int T_EPDG_LTE = 62;
        public static final int T_EPDG_WIFI = 63;
        public static final int VICE_SETTING_ENABLED = 65;
        public static final int VIDEO_QUALITY = 55;
        public static final int VLT_SETTING_ENABLED = 10;
        public static final int VOCODER_AMRMODESET = 0;
        public static final int VOCODER_AMRWBMODESET = 1;
        public static final int VOICE_OVER_WIFI_MODE = 27;
        public static final int VOICE_OVER_WIFI_ROAMING = 26;
        public static final int VOICE_OVER_WIFI_SETTING_ENABLED = 28;
        public static final int VOLTE_USER_OPT_IN_STATUS = 30;
        public static final int VOWT_A = 60;
        public static final int VOWT_B = 61;
    }

    public static class FeatureConstants {
        public static final int FEATURE_TYPE_UNKNOWN = -1;
        public static final int FEATURE_TYPE_UT_OVER_LTE = 4;
        public static final int FEATURE_TYPE_UT_OVER_WIFI = 5;
        public static final int FEATURE_TYPE_VIDEO_OVER_LTE = 1;
        public static final int FEATURE_TYPE_VIDEO_OVER_WIFI = 3;
        public static final int FEATURE_TYPE_VOICE_OVER_LTE = 0;
        public static final int FEATURE_TYPE_VOICE_OVER_WIFI = 2;
    }

    public static class FeatureValueConstants {
        public static final int ERROR = -1;
        public static final int OFF = 0;
        public static final int ON = 1;
    }

    public static class OperationStatusConstants {
        public static final int FAILED = 1;
        public static final int SUCCESS = 0;
        public static final int UNKNOWN = -1;
        public static final int UNSUPPORTED_CAUSE_DISABLED = 4;
        public static final int UNSUPPORTED_CAUSE_NONE = 2;
        public static final int UNSUPPORTED_CAUSE_RAT = 3;
    }

    public static class OperationValuesConstants {
        public static final int VIDEO_QUALITY_HIGH = 1;
        public static final int VIDEO_QUALITY_LOW = 0;
        public static final int VIDEO_QUALITY_UNKNOWN = -1;
    }

    public static class VideoQualityFeatureValuesConstants {
        public static final int HIGH = 1;
        public static final int LOW = 0;
    }

    public static class WfcModeFeatureValueConstants {
        public static final int CELLULAR_PREFERRED = 1;
        public static final int WIFI_ONLY = 0;
        public static final int WIFI_PREFERRED = 2;
    }

}

