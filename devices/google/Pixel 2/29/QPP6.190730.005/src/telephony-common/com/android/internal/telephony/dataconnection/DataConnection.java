/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.KeepalivePacketData
 *  android.net.LinkProperties
 *  android.net.NetworkCapabilities
 *  android.net.NetworkInfo
 *  android.net.NetworkInfo$DetailedState
 *  android.net.NetworkMisc
 *  android.net.NetworkRequest
 *  android.net.NetworkSpecifier
 *  android.net.ProxyInfo
 *  android.net.StringNetworkSpecifier
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.os.SystemClock
 *  android.telephony.AccessNetworkConstants
 *  android.telephony.DataFailCause
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.TelephonyManager
 *  android.telephony.data.ApnSetting
 *  android.telephony.data.DataCallResponse
 *  android.telephony.data.DataProfile
 *  android.text.TextUtils
 *  android.util.Pair
 *  android.util.StatsLog
 *  android.util.TimeUtils
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 *  com.android.internal.telephony.dataconnection.-$
 *  com.android.internal.telephony.dataconnection.-$$Lambda
 *  com.android.internal.telephony.dataconnection.-$$Lambda$DataConnection
 *  com.android.internal.telephony.dataconnection.-$$Lambda$DataConnection$-tFSpFGzTv_UdpzJlTMOvg8VO98
 *  com.android.internal.telephony.dataconnection.-$$Lambda$XZAGhHrbkIDyusER4MAM6luKcT0
 *  com.android.internal.util.AsyncChannel
 *  com.android.internal.util.IState
 *  com.android.internal.util.IndentingPrintWriter
 *  com.android.internal.util.State
 *  com.android.internal.util.StateMachine
 */
package com.android.internal.telephony.dataconnection;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.net.KeepalivePacketData;
import android.net.LinkProperties;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkMisc;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.ProxyInfo;
import android.net.StringNetworkSpecifier;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.SystemClock;
import android.telephony.AccessNetworkConstants;
import android.telephony.DataFailCause;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.data.ApnSetting;
import android.telephony.data.DataCallResponse;
import android.telephony.data.DataProfile;
import android.text.TextUtils;
import android.util.Pair;
import android.util.StatsLog;
import android.util.TimeUtils;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CallTracker;
import com.android.internal.telephony.CarrierSignalAgent;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.LinkCapacityEstimate;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.dataconnection.-$;
import com.android.internal.telephony.dataconnection.ApnContext;
import com.android.internal.telephony.dataconnection.ApnSettingUtils;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import com.android.internal.telephony.dataconnection.DataServiceManager;
import com.android.internal.telephony.dataconnection.DcController;
import com.android.internal.telephony.dataconnection.DcFailBringUp;
import com.android.internal.telephony.dataconnection.DcNetworkAgent;
import com.android.internal.telephony.dataconnection.DcTesterFailBringUpAll;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.telephony.dataconnection.KeepaliveStatus;
import com.android.internal.telephony.dataconnection.TelephonyNetworkFactory;
import com.android.internal.telephony.dataconnection._$$Lambda$DataConnection$_tFSpFGzTv_UdpzJlTMOvg8VO98;
import com.android.internal.telephony.dataconnection._$$Lambda$XZAGhHrbkIDyusER4MAM6luKcT0;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.util.AsyncChannel;
import com.android.internal.util.IState;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.State;
import com.android.internal.util.StateMachine;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class DataConnection
extends StateMachine {
    static final int BASE = 262144;
    private static final int CMD_TO_STRING_COUNT = 27;
    private static final boolean DBG = true;
    private static final int DEFAULT_INTERNET_CONNECTION_SCORE = 50;
    static final int EVENT_BW_REFRESH_RESPONSE = 262158;
    static final int EVENT_CONNECT = 262144;
    static final int EVENT_DATA_CONNECTION_DRS_OR_RAT_CHANGED = 262155;
    static final int EVENT_DATA_CONNECTION_OVERRIDE_CHANGED = 262161;
    static final int EVENT_DATA_CONNECTION_ROAM_OFF = 262157;
    static final int EVENT_DATA_CONNECTION_ROAM_ON = 262156;
    static final int EVENT_DATA_CONNECTION_VOICE_CALL_ENDED = 262160;
    static final int EVENT_DATA_CONNECTION_VOICE_CALL_STARTED = 262159;
    static final int EVENT_DATA_STATE_CHANGED = 262151;
    static final int EVENT_DEACTIVATE_DONE = 262147;
    static final int EVENT_DISCONNECT = 262148;
    static final int EVENT_DISCONNECT_ALL = 262150;
    static final int EVENT_KEEPALIVE_STARTED = 262163;
    static final int EVENT_KEEPALIVE_START_REQUEST = 262165;
    static final int EVENT_KEEPALIVE_STATUS = 262162;
    static final int EVENT_KEEPALIVE_STOPPED = 262164;
    static final int EVENT_KEEPALIVE_STOP_REQUEST = 262166;
    static final int EVENT_LINK_CAPACITY_CHANGED = 262167;
    static final int EVENT_LOST_CONNECTION = 262153;
    static final int EVENT_REEVALUATE_DATA_CONNECTION_PROPERTIES = 262170;
    static final int EVENT_REEVALUATE_RESTRICTED_STATE = 262169;
    static final int EVENT_RESET = 262168;
    static final int EVENT_RIL_CONNECTED = 262149;
    static final int EVENT_SETUP_DATA_CONNECTION_DONE = 262145;
    static final int EVENT_TEAR_DOWN_NOW = 262152;
    private static final int HANDOVER_STATE_BEING_TRANSFERRED = 2;
    private static final int HANDOVER_STATE_COMPLETED = 3;
    private static final int HANDOVER_STATE_IDLE = 1;
    private static final String NETWORK_TYPE = "MOBILE";
    private static final String NULL_IP = "0.0.0.0";
    private static final int OTHER_CONNECTION_SCORE = 45;
    private static final String RAT_NAME_5G = "nr";
    private static final String RAT_NAME_EVDO = "evdo";
    private static final String TCP_BUFFER_SIZES_1XRTT = "16384,32768,131072,4096,16384,102400";
    private static final String TCP_BUFFER_SIZES_EDGE = "4093,26280,70800,4096,16384,70800";
    private static final String TCP_BUFFER_SIZES_EHRPD = "131072,262144,1048576,4096,16384,524288";
    private static final String TCP_BUFFER_SIZES_EVDO = "4094,87380,262144,4096,16384,262144";
    private static final String TCP_BUFFER_SIZES_GPRS = "4092,8760,48000,4096,8760,48000";
    private static final String TCP_BUFFER_SIZES_HSDPA = "61167,367002,1101005,8738,52429,262114";
    private static final String TCP_BUFFER_SIZES_HSPA = "40778,244668,734003,16777,100663,301990";
    private static final String TCP_BUFFER_SIZES_HSPAP = "122334,734003,2202010,32040,192239,576717";
    private static final String TCP_BUFFER_SIZES_LTE = "524288,1048576,2097152,262144,524288,1048576";
    private static final String TCP_BUFFER_SIZES_NR = "2097152,6291456,16777216,512000,2097152,8388608";
    private static final String TCP_BUFFER_SIZES_UMTS = "58254,349525,1048576,58254,349525,1048576";
    private static final boolean VDBG = true;
    private static AtomicInteger mInstanceNumber = new AtomicInteger(0);
    private static String[] sCmdToString;
    private AsyncChannel mAc;
    private DcActivatingState mActivatingState;
    private DcActiveState mActiveState;
    private final Map<ApnContext, ConnectionParams> mApnContexts;
    private ApnSetting mApnSetting;
    public int mCid;
    private ConnectionParams mConnectionParams;
    private long mCreateTime;
    private int mDataRegState;
    private DataServiceManager mDataServiceManager;
    private DcController mDcController;
    private int mDcFailCause;
    private DcTesterFailBringUpAll mDcTesterFailBringUpAll;
    private DcTracker mDct;
    private DcDefaultState mDefaultState;
    private int mDisabledApnTypeBitMask;
    private DisconnectParams mDisconnectParams;
    private DcDisconnectionErrorCreatingConnection mDisconnectingErrorCreatingConnection;
    private DcDisconnectingState mDisconnectingState;
    private DcNetworkAgent mHandoverSourceNetworkAgent;
    private int mHandoverState;
    private int mId;
    private DcInactiveState mInactiveState;
    private int mLastFailCause;
    private long mLastFailTime;
    private LinkProperties mLinkProperties;
    private DcNetworkAgent mNetworkAgent;
    private NetworkInfo mNetworkInfo;
    private String[] mPcscfAddr;
    private Phone mPhone;
    PendingIntent mReconnectIntent;
    private boolean mRestrictedNetworkOverride;
    private int mRilRat;
    private int mScore;
    private int mSubId;
    private int mSubscriptionOverride;
    int mTag;
    private final String mTagSuffix;
    private final int mTransportType;
    private boolean mUnmeteredUseOnly;
    private Object mUserData;

    static {
        String[] arrstring = sCmdToString = new String[27];
        arrstring[0] = "EVENT_CONNECT";
        arrstring[1] = "EVENT_SETUP_DATA_CONNECTION_DONE";
        arrstring[3] = "EVENT_DEACTIVATE_DONE";
        arrstring[4] = "EVENT_DISCONNECT";
        arrstring[5] = "EVENT_RIL_CONNECTED";
        arrstring[6] = "EVENT_DISCONNECT_ALL";
        arrstring[7] = "EVENT_DATA_STATE_CHANGED";
        arrstring[8] = "EVENT_TEAR_DOWN_NOW";
        arrstring[9] = "EVENT_LOST_CONNECTION";
        arrstring[11] = "EVENT_DATA_CONNECTION_DRS_OR_RAT_CHANGED";
        arrstring[12] = "EVENT_DATA_CONNECTION_ROAM_ON";
        arrstring[13] = "EVENT_DATA_CONNECTION_ROAM_OFF";
        arrstring[14] = "EVENT_BW_REFRESH_RESPONSE";
        arrstring[15] = "EVENT_DATA_CONNECTION_VOICE_CALL_STARTED";
        arrstring[16] = "EVENT_DATA_CONNECTION_VOICE_CALL_ENDED";
        arrstring[17] = "EVENT_DATA_CONNECTION_OVERRIDE_CHANGED";
        arrstring[18] = "EVENT_KEEPALIVE_STATUS";
        arrstring[19] = "EVENT_KEEPALIVE_STARTED";
        arrstring[20] = "EVENT_KEEPALIVE_STOPPED";
        arrstring[21] = "EVENT_KEEPALIVE_START_REQUEST";
        arrstring[22] = "EVENT_KEEPALIVE_STOP_REQUEST";
        arrstring[23] = "EVENT_LINK_CAPACITY_CHANGED";
        arrstring[24] = "EVENT_RESET";
        arrstring[25] = "EVENT_REEVALUATE_RESTRICTED_STATE";
        arrstring[26] = "EVENT_REEVALUATE_DATA_CONNECTION_PROPERTIES";
    }

    private DataConnection(Phone phone, String string, int n, DcTracker dcTracker, DataServiceManager dataServiceManager, DcTesterFailBringUpAll dcTesterFailBringUpAll, DcController dcController) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DC-");
        stringBuilder.append(string);
        super(stringBuilder.toString(), dcController.getHandler());
        this.mDct = null;
        this.mLinkProperties = new LinkProperties();
        this.mRilRat = Integer.MAX_VALUE;
        this.mDataRegState = Integer.MAX_VALUE;
        this.mDisabledApnTypeBitMask = 0;
        this.mApnContexts = new ConcurrentHashMap<ApnContext, ConnectionParams>();
        this.mReconnectIntent = null;
        this.mUnmeteredUseOnly = false;
        this.mRestrictedNetworkOverride = false;
        this.mDefaultState = new DcDefaultState();
        this.mInactiveState = new DcInactiveState();
        this.mActivatingState = new DcActivatingState();
        this.mActiveState = new DcActiveState();
        this.mDisconnectingState = new DcDisconnectingState();
        this.mDisconnectingErrorCreatingConnection = new DcDisconnectionErrorCreatingConnection();
        this.mTagSuffix = string;
        this.setLogRecSize(300);
        this.setLogOnlyTransitions(true);
        this.log("DataConnection created");
        this.mPhone = phone;
        this.mDct = dcTracker;
        this.mDataServiceManager = dataServiceManager;
        this.mTransportType = dataServiceManager.getTransportType();
        this.mDcTesterFailBringUpAll = dcTesterFailBringUpAll;
        this.mDcController = dcController;
        this.mId = n;
        this.mCid = -1;
        phone = this.mPhone.getServiceState();
        this.mRilRat = phone.getRilDataRadioTechnology();
        this.mDataRegState = this.mPhone.getServiceState().getDataRegState();
        n = phone.getDataNetworkType();
        this.mNetworkInfo = new NetworkInfo(0, n, NETWORK_TYPE, TelephonyManager.getNetworkTypeName((int)n));
        this.mNetworkInfo.setRoaming(phone.getDataRoaming());
        this.mNetworkInfo.setIsAvailable(true);
        this.addState((State)this.mDefaultState);
        this.addState((State)this.mInactiveState, (State)this.mDefaultState);
        this.addState((State)this.mActivatingState, (State)this.mDefaultState);
        this.addState((State)this.mActiveState, (State)this.mDefaultState);
        this.addState((State)this.mDisconnectingState, (State)this.mDefaultState);
        this.addState((State)this.mDisconnectingErrorCreatingConnection, (State)this.mDefaultState);
        this.setInitialState((State)this.mInactiveState);
    }

    static /* synthetic */ int access$5172(DataConnection dataConnection, int n) {
        dataConnection.mDisabledApnTypeBitMask = n = dataConnection.mDisabledApnTypeBitMask & n;
        return n;
    }

    static /* synthetic */ int access$5176(DataConnection dataConnection, int n) {
        dataConnection.mDisabledApnTypeBitMask = n = dataConnection.mDisabledApnTypeBitMask | n;
        return n;
    }

    private int calculateScore() {
        int n = 45;
        Iterator<ApnContext> iterator = this.mApnContexts.keySet().iterator();
        while (iterator.hasNext()) {
            int n2;
            block2 : {
                NetworkRequest networkRequest;
                Iterator<NetworkRequest> iterator2 = iterator.next().getNetworkRequests().iterator();
                do {
                    n2 = n;
                    if (!iterator2.hasNext()) break block2;
                } while (!(networkRequest = iterator2.next()).hasCapability(12) || networkRequest.networkCapabilities.getNetworkSpecifier() != null);
                n2 = 50;
            }
            n = n2;
        }
        return n;
    }

    private void checkSetMtu(ApnSetting object, LinkProperties object2) {
        if (object2 == null) {
            return;
        }
        if (object != null) {
            if (object2.getMtu() != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("MTU set by call response to: ");
                ((StringBuilder)object).append(object2.getMtu());
                this.log(((StringBuilder)object).toString());
                return;
            }
            if (object.getMtu() != 0) {
                object2.setMtu(object.getMtu());
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("MTU set by APN to: ");
                ((StringBuilder)object2).append(object.getMtu());
                this.log(((StringBuilder)object2).toString());
                return;
            }
            int n = this.mPhone.getContext().getResources().getInteger(17694844);
            if (n != 0) {
                object2.setMtu(n);
                object = new StringBuilder();
                ((StringBuilder)object).append("MTU set by config resource to: ");
                ((StringBuilder)object).append(n);
                this.log(((StringBuilder)object).toString());
            }
            return;
        }
    }

    private void clearSettings() {
        this.log("clearSettings");
        this.mCreateTime = -1L;
        this.mLastFailTime = -1L;
        this.mLastFailCause = 0;
        this.mCid = -1;
        this.mPcscfAddr = new String[5];
        this.mLinkProperties = new LinkProperties();
        this.mApnContexts.clear();
        this.mApnSetting = null;
        this.mUnmeteredUseOnly = false;
        this.mRestrictedNetworkOverride = false;
        this.mDcFailCause = 0;
        this.mDisabledApnTypeBitMask = 0;
        this.mSubId = -1;
    }

    static String cmdToString(int n) {
        CharSequence charSequence = null;
        CharSequence charSequence2 = charSequence;
        if ((n -= 262144) >= 0) {
            String[] arrstring = sCmdToString;
            charSequence2 = charSequence;
            if (n < arrstring.length) {
                charSequence2 = arrstring[n];
            }
        }
        charSequence = charSequence2;
        if (charSequence2 == null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("0x");
            ((StringBuilder)charSequence2).append(Integer.toHexString(262144 + n));
            charSequence = ((StringBuilder)charSequence2).toString();
        }
        return charSequence;
    }

    private int connect(ConnectionParams object) {
        Object object2 = new StringBuilder();
        object2.append("connect: carrier='");
        object2.append(this.mApnSetting.getEntryName());
        object2.append("' APN='");
        object2.append(this.mApnSetting.getApnName());
        object2.append("' proxy='");
        object2.append(this.mApnSetting.getProxyAddressAsString());
        object2.append("' port='");
        object2.append(this.mApnSetting.getProxyPort());
        object2.append("'");
        this.log(object2.toString());
        if (((ConnectionParams)object).mApnContext != null) {
            ((ConnectionParams)object).mApnContext.requestLog("DataConnection.connect");
        }
        if (this.mDcTesterFailBringUpAll.getDcFailBringUp().mCounter > 0) {
            object2 = new DataCallResponse(this.mDcTesterFailBringUpAll.getDcFailBringUp().mFailCause, this.mDcTesterFailBringUpAll.getDcFailBringUp().mSuggestedRetryTime, 0, 0, 0, "", null, null, null, null, 0);
            object = this.obtainMessage(262145, object);
            AsyncResult.forMessage((Message)object, (Object)object2, null);
            this.sendMessage((Message)object);
            object = new StringBuilder();
            ((StringBuilder)object).append("connect: FailBringUpAll=");
            ((StringBuilder)object).append(this.mDcTesterFailBringUpAll.getDcFailBringUp());
            ((StringBuilder)object).append(" send error response=");
            ((StringBuilder)object).append(object2);
            this.log(((StringBuilder)object).toString());
            object = this.mDcTesterFailBringUpAll.getDcFailBringUp();
            --((DcFailBringUp)object).mCounter;
            return 0;
        }
        this.mCreateTime = -1L;
        this.mLastFailTime = -1L;
        this.mLastFailCause = 0;
        Object object3 = this.obtainMessage(262145, object);
        ((Message)object3).obj = object;
        DataProfile dataProfile = DcTracker.createDataProfile(this.mApnSetting, ((ConnectionParams)object).mProfileId, this.mApnSetting.equals((Object)this.mDct.getPreferredApn()));
        boolean bl = this.mPhone.getServiceState().getDataRoamingFromRegistration();
        boolean bl2 = this.mPhone.getDataRoamingEnabled() || bl && !this.mPhone.getServiceState().getDataRoaming();
        object2 = null;
        int n = 1;
        if (((ConnectionParams)object).mRequestType == 2) {
            object2 = this.mPhone.getDcTracker(this.getHandoverSourceTransport());
            if (object2 != null && ((ConnectionParams)object).mApnContext != null) {
                DataConnection dataConnection = ((DcTracker)((Object)object2)).getDataConnectionByApnType(((ConnectionParams)object).mApnContext.getApnType());
                if (dataConnection == null) {
                    this.loge("connect: Can't find data connection for handover.");
                    return 65542;
                }
                object2 = dataConnection.getLinkProperties();
                this.mHandoverSourceNetworkAgent = dataConnection.getNetworkAgent();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Get the handover source network agent: ");
                stringBuilder.append((Object)this.mHandoverSourceNetworkAgent);
                this.log(stringBuilder.toString());
                dataConnection.setHandoverState(2);
                if (object2 == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("connect: Can't find link properties of handover data connection. dc=");
                    ((StringBuilder)object).append((Object)dataConnection);
                    this.loge(((StringBuilder)object).toString());
                    return 65542;
                }
                n = 3;
            } else {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("connect: Handover failed. dcTracker=");
                ((StringBuilder)object3).append(object2);
                ((StringBuilder)object3).append(", apnContext=");
                ((StringBuilder)object3).append(((ConnectionParams)object).mApnContext);
                this.loge(((StringBuilder)object3).toString());
                return 65542;
            }
        }
        this.mDataServiceManager.setupDataCall(ServiceState.rilRadioTechnologyToAccessNetworkType((int)((ConnectionParams)object).mRilRat), dataProfile, bl, bl2, n, (LinkProperties)object2, (Message)object3);
        TelephonyMetrics.getInstance().writeSetupDataCall(this.mPhone.getPhoneId(), ((ConnectionParams)object).mRilRat, dataProfile.getProfileId(), dataProfile.getApn(), dataProfile.getProtocolType());
        return 0;
    }

    private void dumpToLog() {
        this.dump(null, new PrintWriter(new StringWriter(0)){

            @Override
            public void flush() {
            }

            @Override
            public void println(String string) {
                DataConnection.this.logd(string);
            }
        }, null);
    }

    private int getHandoverSourceTransport() {
        int n;
        block0 : {
            int n2 = this.mTransportType;
            n = 1;
            if (n2 != 1) break block0;
            n = 2;
        }
        return n;
    }

    private long getSuggestedRetryDelay(DataCallResponse dataCallResponse) {
        if (dataCallResponse.getSuggestedRetryTime() < 0) {
            this.log("No suggested retry delay.");
            return -2L;
        }
        if (dataCallResponse.getSuggestedRetryTime() == Integer.MAX_VALUE) {
            this.log("Modem suggested not retrying.");
            return -1L;
        }
        return dataCallResponse.getSuggestedRetryTime();
    }

    private boolean initConnection(ConnectionParams object) {
        Object object2;
        ApnContext apnContext = ((ConnectionParams)object).mApnContext;
        if (this.mApnSetting == null) {
            this.mApnSetting = apnContext.getApnSetting();
        }
        if ((object2 = this.mApnSetting) != null && object2.canHandleType(apnContext.getApnTypeBitmask())) {
            ++this.mTag;
            this.mConnectionParams = object;
            this.mConnectionParams.mTag = this.mTag;
            this.mApnContexts.put(apnContext, (ConnectionParams)object);
            object = new StringBuilder();
            ((StringBuilder)object).append("initConnection:  RefCount=");
            ((StringBuilder)object).append(this.mApnContexts.size());
            ((StringBuilder)object).append(" mApnList=");
            ((StringBuilder)object).append(this.mApnContexts);
            ((StringBuilder)object).append(" mConnectionParams=");
            ((StringBuilder)object).append(this.mConnectionParams);
            this.log(((StringBuilder)object).toString());
            return true;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("initConnection: incompatible apnSetting in ConnectionParams cp=");
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).append(" dc=");
        ((StringBuilder)object2).append((Object)this);
        this.log(((StringBuilder)object2).toString());
        return false;
    }

    private boolean isDnsOk(String[] arrstring) {
        if (NULL_IP.equals(arrstring[0]) && NULL_IP.equals(arrstring[1]) && !this.mPhone.isDnsCheckDisabled() && !DataConnection.isIpAddress(this.mApnSetting.getMmsProxyAddressAsString())) {
            this.log(String.format("isDnsOk: return false apn.types=%d APN_TYPE_MMS=%s isIpAddress(%s)=%s", this.mApnSetting.getApnTypeBitmask(), "mms", this.mApnSetting.getMmsProxyAddressAsString(), DataConnection.isIpAddress(this.mApnSetting.getMmsProxyAddressAsString())));
            return false;
        }
        return true;
    }

    @VisibleForTesting
    public static boolean isIpAddress(String string) {
        if (string == null) {
            return false;
        }
        return InetAddress.isNumeric((String)string);
    }

    private boolean isNRConnected() {
        boolean bl = this.mPhone.getServiceState().getNrState() == 3;
        return bl;
    }

    private boolean isUnmeteredUseOnly() {
        if (this.mTransportType == 2) {
            return false;
        }
        if (this.mPhone.getDataEnabledSettings().isDataEnabled()) {
            return false;
        }
        if (this.mDct.getDataRoamingEnabled() && this.mPhone.getServiceState().getDataRoaming()) {
            return false;
        }
        Iterator<ApnContext> iterator = this.mApnContexts.keySet().iterator();
        while (iterator.hasNext()) {
            if (!ApnSettingUtils.isMeteredApnType(iterator.next().getApnTypeBitmask(), this.mPhone)) continue;
            return false;
        }
        return true;
    }

    static /* synthetic */ String[] lambda$onSetupConnectionCompleted$0(int n) {
        return new String[n];
    }

    public static DataConnection makeDataConnection(Phone object, int n, DcTracker object2, DataServiceManager dataServiceManager, DcTesterFailBringUpAll dcTesterFailBringUpAll, DcController dcController) {
        String string = dataServiceManager.getTransportType() == 1 ? "C" : "I";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("-");
        stringBuilder.append(mInstanceNumber.incrementAndGet());
        object2 = new DataConnection((Phone)object, stringBuilder.toString(), n, (DcTracker)((Object)object2), dataServiceManager, dcTesterFailBringUpAll, dcController);
        object2.start();
        object = new StringBuilder();
        ((StringBuilder)object).append("Made ");
        ((StringBuilder)object).append(object2.getName());
        ((DataConnection)((Object)object2)).log(((StringBuilder)object).toString());
        return object2;
    }

    private static String msgToString(Message object) {
        if (object == null) {
            object = "null";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{what=");
            stringBuilder.append(DataConnection.cmdToString(object.what));
            stringBuilder.append(" when=");
            TimeUtils.formatDuration((long)(object.getWhen() - SystemClock.uptimeMillis()), (StringBuilder)stringBuilder);
            if (object.arg1 != 0) {
                stringBuilder.append(" arg1=");
                stringBuilder.append(object.arg1);
            }
            if (object.arg2 != 0) {
                stringBuilder.append(" arg2=");
                stringBuilder.append(object.arg2);
            }
            if (object.obj != null) {
                stringBuilder.append(" obj=");
                stringBuilder.append(object.obj);
            }
            stringBuilder.append(" target=");
            stringBuilder.append((Object)object.getTarget());
            stringBuilder.append(" replyTo=");
            stringBuilder.append((Object)object.replyTo);
            stringBuilder.append("}");
            object = stringBuilder.toString();
        }
        return object;
    }

    private void notifyAllWithEvent(ApnContext apnContext, int n, String string) {
        NetworkInfo networkInfo = this.mNetworkInfo;
        networkInfo.setDetailedState(networkInfo.getDetailedState(), string, this.mNetworkInfo.getExtraInfo());
        for (ConnectionParams connectionParams : this.mApnContexts.values()) {
            ApnContext apnContext2 = connectionParams.mApnContext;
            if (apnContext2 == apnContext) continue;
            if (string != null) {
                apnContext2.setReason(string);
            }
            apnContext2 = new Pair((Object)apnContext2, (Object)connectionParams.mConnectionGeneration);
            connectionParams = this.mDct.obtainMessage(n, this.mCid, connectionParams.mRequestType, (Object)apnContext2);
            AsyncResult.forMessage((Message)connectionParams);
            connectionParams.sendToTarget();
        }
    }

    private void notifyConnectCompleted(ConnectionParams object, int n, boolean bl) {
        Message message;
        Object object2 = message = null;
        int n2 = n;
        if (object != null) {
            object2 = message;
            n2 = n;
            if (((ConnectionParams)object).mOnCompletedMsg != null) {
                message = ((ConnectionParams)object).mOnCompletedMsg;
                ((ConnectionParams)object).mOnCompletedMsg = null;
                object2 = ((ConnectionParams)object).mApnContext;
                long l = System.currentTimeMillis();
                message.arg1 = this.mCid;
                message.arg2 = ((ConnectionParams)object).mRequestType;
                if (n == 0) {
                    this.mCreateTime = l;
                    AsyncResult.forMessage((Message)message);
                } else {
                    this.mLastFailCause = n;
                    this.mLastFailTime = l;
                    n2 = n;
                    if (n == 0) {
                        n2 = 65536;
                    }
                    AsyncResult.forMessage((Message)message, (Object)n2, (Throwable)new Throwable(DataFailCause.toString((int)n2)));
                    n = n2;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("notifyConnectCompleted at ");
                ((StringBuilder)object).append(l);
                ((StringBuilder)object).append(" cause=");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" connectionCompletedMsg=");
                ((StringBuilder)object).append(DataConnection.msgToString(message));
                this.log(((StringBuilder)object).toString());
                message.sendToTarget();
                n2 = n;
            }
        }
        if (bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Send to all. ");
            ((StringBuilder)object).append(object2);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(DataFailCause.toString((int)n2));
            this.log(((StringBuilder)object).toString());
            this.notifyAllWithEvent((ApnContext)object2, 270371, DataFailCause.toString((int)n2));
        }
    }

    private void notifyDisconnectCompleted(DisconnectParams disconnectParams, boolean bl) {
        this.log("NotifyDisconnectCompleted");
        CharSequence charSequence = null;
        String string = null;
        String string2 = null;
        Object object = charSequence;
        String string3 = string2;
        if (disconnectParams != null) {
            object = charSequence;
            string3 = string2;
            if (disconnectParams.mOnCompletedMsg != null) {
                string2 = disconnectParams.mOnCompletedMsg;
                disconnectParams.mOnCompletedMsg = null;
                object = string;
                if (((Message)string2).obj instanceof ApnContext) {
                    object = (ApnContext)((Message)string2).obj;
                }
                string = disconnectParams.mReason;
                charSequence = string2.toString();
                string3 = ((Message)string2).obj instanceof String ? (String)((Message)string2).obj : "<no-reason>";
                this.log(String.format("msg=%s msg.obj=%s", charSequence, string3));
                AsyncResult.forMessage((Message)string2);
                string2.sendToTarget();
                string3 = string;
            }
        }
        if (bl) {
            string = string3;
            if (string3 == null) {
                string = DataFailCause.toString((int)65536);
            }
            this.notifyAllWithEvent((ApnContext)object, 270351, string);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("NotifyDisconnectCompleted DisconnectParams=");
        ((StringBuilder)object).append(disconnectParams);
        this.log(((StringBuilder)object).toString());
    }

    private SetupResult onSetupConnectionCompleted(int n, DataCallResponse object, ConnectionParams object2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onSetupConnectionCompleted: resultCode=");
        stringBuilder.append(n);
        stringBuilder.append(", response=");
        stringBuilder.append(object);
        this.log(stringBuilder.toString());
        if (object2.mTag != this.mTag) {
            object = new StringBuilder();
            object.append("onSetupConnectionCompleted stale cp.tag=");
            object.append(object2.mTag);
            object.append(", mtag=");
            object.append(this.mTag);
            this.log(object.toString());
            object = SetupResult.ERROR_STALE;
        } else if (n == 4) {
            object = SetupResult.ERROR_RADIO_NOT_AVAILABLE;
            object.mFailCause = 65537;
        } else if (object.getCause() != 0) {
            if (object.getCause() == 65537) {
                object = SetupResult.ERROR_RADIO_NOT_AVAILABLE;
                object.mFailCause = 65537;
            } else {
                object2 = SetupResult.ERROR_DATA_SERVICE_SPECIFIC_ERROR;
                ((SetupResult)object2).mFailCause = DataFailCause.getFailCause((int)object.getCause());
                object = object2;
            }
        } else {
            this.log("onSetupConnectionCompleted received successful DataCallResponse");
            this.mCid = object.getId();
            this.mPcscfAddr = (String[])object.getPcscfAddresses().stream().map(_$$Lambda$XZAGhHrbkIDyusER4MAM6luKcT0.INSTANCE).toArray((IntFunction<A[]>)_$$Lambda$DataConnection$_tFSpFGzTv_UdpzJlTMOvg8VO98.INSTANCE);
            object = this.updateLinkProperty((DataCallResponse)object).setupResult;
        }
        return object;
    }

    /*
     * Exception decompiling
     */
    private SetupResult setLinkProperties(DataCallResponse var1_1, LinkProperties var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[WHILELOOP]], but top level block is 3[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private boolean shouldRestrictNetwork() {
        boolean bl;
        block4 : {
            boolean bl2 = false;
            Iterator<ApnContext> iterator = this.mApnContexts.keySet().iterator();
            do {
                bl = bl2;
                if (!iterator.hasNext()) break block4;
            } while (!iterator.next().hasRestrictedRequests(true));
            bl = true;
        }
        if (!bl) {
            return false;
        }
        if (!ApnSettingUtils.isMetered(this.mApnSetting, this.mPhone)) {
            return false;
        }
        if (!this.mPhone.getDataEnabledSettings().isDataEnabled()) {
            return true;
        }
        return !this.mDct.getDataRoamingEnabled() && this.mPhone.getServiceState().getDataRoaming();
    }

    static void slog(String string) {
        Rlog.d((String)"DC", (String)string);
    }

    private void tearDownData(Object object) {
        int n = 1;
        Object object2 = null;
        int n2 = n;
        Object object3 = object2;
        if (object != null) {
            n2 = n;
            object3 = object2;
            if (object instanceof DisconnectParams) {
                DisconnectParams disconnectParams = (DisconnectParams)object;
                object2 = disconnectParams.mApnContext;
                if (!TextUtils.equals((CharSequence)disconnectParams.mReason, (CharSequence)"radioTurnedOff") && !TextUtils.equals((CharSequence)disconnectParams.mReason, (CharSequence)"pdpReset")) {
                    n2 = n;
                    object3 = object2;
                    if (disconnectParams.mReleaseType == 3) {
                        n2 = 3;
                        object3 = object2;
                    }
                } else {
                    n2 = 2;
                    object3 = object2;
                }
            }
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("tearDownData. mCid=");
        ((StringBuilder)object2).append(this.mCid);
        ((StringBuilder)object2).append(", reason=");
        ((StringBuilder)object2).append(n2);
        object2 = ((StringBuilder)object2).toString();
        this.log((String)object2);
        if (object3 != null) {
            ((ApnContext)object3).requestLog((String)object2);
        }
        this.mDataServiceManager.deactivateDataCall(this.mCid, n2, this.obtainMessage(262147, this.mTag, 0, object));
    }

    private void updateNetworkInfo() {
        ServiceState serviceState = this.mPhone.getServiceState();
        int n = serviceState.getDataNetworkType();
        this.mNetworkInfo.setSubtype(n, TelephonyManager.getNetworkTypeName((int)n));
        this.mNetworkInfo.setRoaming(serviceState.getDataRoaming());
    }

    private void updateNetworkInfoSuspendState() {
        ServiceStateTracker serviceStateTracker;
        if (this.mNetworkAgent == null) {
            Rlog.e((String)this.getName(), (String)"Setting suspend state without a NetworkAgent");
        }
        if ((serviceStateTracker = this.mPhone.getServiceStateTracker()).getCurrentDataConnectionState() != 0) {
            this.mNetworkInfo.setDetailedState(NetworkInfo.DetailedState.SUSPENDED, null, this.mNetworkInfo.getExtraInfo());
        } else {
            if (!serviceStateTracker.isConcurrentVoiceAndDataAllowed() && this.mPhone.getCallTracker().getState() != PhoneConstants.State.IDLE) {
                this.mNetworkInfo.setDetailedState(NetworkInfo.DetailedState.SUSPENDED, null, this.mNetworkInfo.getExtraInfo());
                return;
            }
            this.mNetworkInfo.setDetailedState(NetworkInfo.DetailedState.CONNECTED, null, this.mNetworkInfo.getExtraInfo());
        }
    }

    private void updateScore() {
        int n = this.mScore;
        this.mScore = this.calculateScore();
        if (n != this.mScore && this.mNetworkAgent != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Updating score from ");
            stringBuilder.append(n);
            stringBuilder.append(" to ");
            stringBuilder.append(this.mScore);
            this.log(stringBuilder.toString());
            this.mNetworkAgent.sendNetworkScore(this.mScore, this);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void updateTcpBufferSizes(int var1_1) {
        var2_2 = null;
        var3_3 = var1_1;
        if (var1_1 == 19) {
            var3_3 = 14;
        }
        var4_4 = ServiceState.rilRadioTechnologyToString((int)var3_3).toLowerCase(Locale.ROOT);
        if (var3_3 == 7 || var3_3 == 8 || var3_3 == 12) {
            var4_5 = "evdo";
        }
        var5_22 = var4_6;
        if (var3_3 == 14) {
            var5_22 = var4_6;
            if (this.isNRConnected()) {
                var5_22 = "nr";
            }
        }
        var4_7 = this.mPhone.getContext().getResources().getStringArray(17236039);
        var1_1 = 0;
        do {
            var6_24 = var2_2;
            if (var1_1 >= var4_7.length) break;
            var6_23 = var4_7[var1_1].split(":");
            if (var5_22.equals(var6_23[0]) && var6_23.length == 2) {
                var6_25 = var6_23[1];
                break;
            }
            ++var1_1;
        } while (true);
        var4_8 = var6_26;
        if (var6_26 == null) {
            if (var3_3 != 1) {
                if (var3_3 != 2) {
                    if (var3_3 != 3) {
                        if (var3_3 != 19) {
                            switch (var3_3) {
                                default: {
                                    var4_9 = var6_26;
                                    ** break;
                                }
                                case 15: {
                                    var4_10 = "122334,734003,2202010,32040,192239,576717";
                                    ** break;
                                }
                                case 13: {
                                    var4_11 = "131072,262144,1048576,4096,16384,524288";
                                    ** break;
                                }
                                case 10: 
                                case 11: {
                                    var4_12 = "40778,244668,734003,16777,100663,301990";
                                    ** break;
                                }
                                case 9: {
                                    var4_13 = "61167,367002,1101005,8738,52429,262114";
                                    ** break;
                                }
                                case 7: 
                                case 8: 
                                case 12: {
                                    var4_14 = "4094,87380,262144,4096,16384,262144";
                                    ** break;
                                }
                                case 6: {
                                    var4_15 = "16384,32768,131072,4096,16384,102400";
                                    ** break;
                                }
                                case 14: 
                            }
                        }
                        if (this.isNRConnected()) {
                            var4_16 = "2097152,6291456,16777216,512000,2097152,8388608";
                            ** break;
                        }
                        var4_17 = "524288,1048576,2097152,262144,524288,1048576";
                        ** break;
lbl58: // 9 sources:
                    } else {
                        var4_18 = "58254,349525,1048576,58254,349525,1048576";
                    }
                } else {
                    var4_19 = "4093,26280,70800,4096,16384,70800";
                }
            } else {
                var4_20 = "4092,8760,48000,4096,8760,48000";
            }
        }
        this.mLinkProperties.setTcpBufferSizes((String)var4_21);
    }

    public void bringUp(ApnContext apnContext, int n, int n2, Message message, int n3, int n4, int n5) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bringUp: apnContext=");
        stringBuilder.append(apnContext);
        stringBuilder.append(" onCompletedMsg=");
        stringBuilder.append((Object)message);
        this.log(stringBuilder.toString());
        this.sendMessage(262144, (Object)new ConnectionParams(apnContext, n, n2, message, n3, n4, n5));
    }

    void dispose() {
        this.log("dispose: call quiteNow()");
        this.quitNow();
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter = new IndentingPrintWriter((Writer)printWriter, " ");
        printWriter.print("DataConnection ");
        super.dump(fileDescriptor, printWriter, arrstring);
        printWriter.flush();
        printWriter.increaseIndent();
        Object object = new StringBuilder();
        ((StringBuilder)object).append("transport type=");
        ((StringBuilder)object).append(AccessNetworkConstants.transportTypeToString((int)this.mTransportType));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mApnContexts.size=");
        ((StringBuilder)object).append(this.mApnContexts.size());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mApnContexts=");
        ((StringBuilder)object).append(this.mApnContexts);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mApnSetting=");
        ((StringBuilder)object).append((Object)this.mApnSetting);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mTag=");
        ((StringBuilder)object).append(this.mTag);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mCid=");
        ((StringBuilder)object).append(this.mCid);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mConnectionParams=");
        ((StringBuilder)object).append(this.mConnectionParams);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mDisconnectParams=");
        ((StringBuilder)object).append(this.mDisconnectParams);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mDcFailCause=");
        ((StringBuilder)object).append(this.mDcFailCause);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mPhone=");
        ((StringBuilder)object).append(this.mPhone);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mSubId=");
        ((StringBuilder)object).append(this.mSubId);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mLinkProperties=");
        ((StringBuilder)object).append((Object)this.mLinkProperties);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
        object = new StringBuilder();
        ((StringBuilder)object).append("mDataRegState=");
        ((StringBuilder)object).append(this.mDataRegState);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mHandoverState=");
        ((StringBuilder)object).append(this.mHandoverState);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mRilRat=");
        ((StringBuilder)object).append(this.mRilRat);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mNetworkCapabilities=");
        ((StringBuilder)object).append((Object)this.getNetworkCapabilities());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mCreateTime=");
        ((StringBuilder)object).append(TimeUtils.logTimeOfDay((long)this.mCreateTime));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mLastFailTime=");
        ((StringBuilder)object).append(TimeUtils.logTimeOfDay((long)this.mLastFailTime));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mLastFailCause=");
        ((StringBuilder)object).append(this.mLastFailCause);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mUserData=");
        ((StringBuilder)object).append(this.mUserData);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mSubscriptionOverride=");
        ((StringBuilder)object).append(Integer.toHexString(this.mSubscriptionOverride));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mRestrictedNetworkOverride=");
        ((StringBuilder)object).append(this.mRestrictedNetworkOverride);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mUnmeteredUseOnly=");
        ((StringBuilder)object).append(this.mUnmeteredUseOnly);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mInstanceNumber=");
        ((StringBuilder)object).append(mInstanceNumber);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mAc=");
        ((StringBuilder)object).append((Object)this.mAc);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mScore=");
        ((StringBuilder)object).append(this.mScore);
        printWriter.println(((StringBuilder)object).toString());
        object = this.mNetworkAgent;
        if (object != null) {
            ((DcNetworkAgent)((Object)object)).dump(fileDescriptor, printWriter, arrstring);
        }
        printWriter.decreaseIndent();
        printWriter.println();
        printWriter.flush();
    }

    public List<ApnContext> getApnContexts() {
        return new ArrayList<ApnContext>(this.mApnContexts.keySet());
    }

    ApnSetting getApnSetting() {
        return this.mApnSetting;
    }

    int getCid() {
        return this.mCid;
    }

    public ConnectionParams getConnectionParams() {
        return this.mConnectionParams;
    }

    public int getDataConnectionId() {
        return this.mId;
    }

    LinkProperties getLinkProperties() {
        return new LinkProperties(this.mLinkProperties);
    }

    DcNetworkAgent getNetworkAgent() {
        return this.mNetworkAgent;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public NetworkCapabilities getNetworkCapabilities() {
        block50 : {
            var1_1 = new NetworkCapabilities();
            var1_1.addTransportType(0);
            var2_2 = this.mApnSetting;
            if (var2_2 == null) break block50;
            block42 : for (String var2_4 : ApnSetting.getApnTypesStringFromBitmask((int)(var2_2.getApnTypeBitmask() & this.mDisabledApnTypeBitMask)).split(",")) {
                block51 : {
                    if (!this.mRestrictedNetworkOverride && this.mUnmeteredUseOnly && ApnSettingUtils.isMeteredApnType(ApnSetting.getApnTypesBitmaskFromString((String)var2_4), this.mPhone)) {
                        var6_8 = new StringBuilder();
                        var6_8.append("Dropped the metered ");
                        var6_8.append(var2_4);
                        var6_8.append(" for the unmetered data call.");
                        this.log(var6_8.toString());
                        continue;
                    }
                    switch (var2_4.hashCode()) {
                        case 1629013393: {
                            if (!var2_4.equals("emergency")) break;
                            var7_9 = 9;
                            break block51;
                        }
                        case 1544803905: {
                            if (!var2_4.equals("default")) break;
                            var7_9 = 1;
                            break block51;
                        }
                        case 3541982: {
                            if (!var2_4.equals("supl")) break;
                            var7_9 = 3;
                            break block51;
                        }
                        case 3149046: {
                            if (!var2_4.equals("fota")) break;
                            var7_9 = 5;
                            break block51;
                        }
                        case 108243: {
                            if (!var2_4.equals("mms")) break;
                            var7_9 = 2;
                            break block51;
                        }
                        case 107938: {
                            if (!var2_4.equals("mcx")) break;
                            var7_9 = 10;
                            break block51;
                        }
                        case 104399: {
                            if (!var2_4.equals("ims")) break;
                            var7_9 = 6;
                            break block51;
                        }
                        case 99837: {
                            if (!var2_4.equals("dun")) break;
                            var7_9 = 4;
                            break block51;
                        }
                        case 98292: {
                            if (!var2_4.equals("cbs")) break;
                            var7_9 = 7;
                            break block51;
                        }
                        case 3352: {
                            if (!var2_4.equals("ia")) break;
                            var7_9 = 8;
                            break block51;
                        }
                        case 42: {
                            if (!var2_4.equals("*")) break;
                            var7_9 = 0;
                            break block51;
                        }
                    }
                    ** break;
lbl63: // 1 sources:
                    var7_9 = -1;
                }
                switch (var7_9) {
                    default: {
                        continue block42;
                    }
                    case 10: {
                        var1_1.addCapability(23);
                        continue block42;
                    }
                    case 9: {
                        var1_1.addCapability(10);
                        continue block42;
                    }
                    case 8: {
                        var1_1.addCapability(7);
                        continue block42;
                    }
                    case 7: {
                        var1_1.addCapability(5);
                        continue block42;
                    }
                    case 6: {
                        var1_1.addCapability(4);
                        continue block42;
                    }
                    case 5: {
                        var1_1.addCapability(3);
                        continue block42;
                    }
                    case 4: {
                        var1_1.addCapability(2);
                        continue block42;
                    }
                    case 3: {
                        var1_1.addCapability(1);
                        continue block42;
                    }
                    case 2: {
                        var1_1.addCapability(0);
                        continue block42;
                    }
                    case 1: {
                        var1_1.addCapability(12);
                        continue block42;
                    }
                    case 0: 
                }
                var1_1.addCapability(12);
                var1_1.addCapability(0);
                var1_1.addCapability(1);
                var1_1.addCapability(3);
                var1_1.addCapability(4);
                var1_1.addCapability(5);
                var1_1.addCapability(7);
                var1_1.addCapability(2);
            }
            if (this.mUnmeteredUseOnly && !this.mRestrictedNetworkOverride || !ApnSettingUtils.isMetered(this.mApnSetting, this.mPhone)) {
                var1_1.addCapability(11);
            } else {
                var1_1.removeCapability(11);
            }
            var1_1.maybeMarkCapabilitiesRestricted();
        }
        if (this.mRestrictedNetworkOverride) {
            var1_1.removeCapability(13);
            var1_1.removeCapability(2);
        }
        var7_9 = 14;
        var5_7 = 14;
        var4_6 = this.mRilRat;
        if (var4_6 != 19) {
            switch (var4_6) {
                default: {
                    ** break;
                }
                case 15: {
                    var7_9 = 11264;
                    var5_7 = 43008;
                    ** break;
                }
                case 14: {
                    var7_9 = 51200;
                    var5_7 = 102400;
                    ** break;
                }
                case 13: {
                    var7_9 = 153;
                    var5_7 = 2516;
                    ** break;
                }
                case 12: {
                    var7_9 = 1843;
                    var5_7 = 5017;
                    ** break;
                }
                case 11: {
                    var7_9 = 5898;
                    var5_7 = 14336;
                    ** break;
                }
                case 10: {
                    var7_9 = 5898;
                    var5_7 = 14336;
                    ** break;
                }
                case 9: {
                    var7_9 = 2048;
                    var5_7 = 14336;
                    ** break;
                }
                case 8: {
                    var7_9 = 1843;
                    var5_7 = 3174;
                    ** break;
                }
                case 7: {
                    var7_9 = 153;
                    var5_7 = 2457;
                    ** break;
                }
                case 6: {
                    var7_9 = 100;
                    var5_7 = 100;
                    ** break;
                }
                case 4: 
                case 5: {
                    var7_9 = 14;
                    var5_7 = 14;
                    ** break;
                }
                case 3: {
                    var7_9 = 384;
                    var5_7 = 384;
                    ** break;
                }
                case 2: {
                    var7_9 = 59;
                    var5_7 = 236;
                    ** break;
                }
                case 1: 
            }
            var7_9 = 80;
            var5_7 = 80;
            ** break;
lbl202: // 15 sources:
        } else {
            var7_9 = 51200;
            var5_7 = 102400;
        }
        var1_1.setLinkUpstreamBandwidthKbps(var7_9);
        var1_1.setLinkDownstreamBandwidthKbps(var5_7);
        var1_1.setNetworkSpecifier((NetworkSpecifier)new StringNetworkSpecifier(Integer.toString(this.mSubId)));
        var1_1.setCapability(18, this.mPhone.getServiceState().getDataRoaming() ^ true);
        var1_1.addCapability(20);
        if ((1 & this.mSubscriptionOverride) != 0) {
            var1_1.addCapability(11);
        }
        if ((2 & this.mSubscriptionOverride) == 0) return var1_1;
        var1_1.removeCapability(20);
        return var1_1;
    }

    public String[] getPcscfAddresses() {
        return this.mPcscfAddr;
    }

    protected String getWhatToString(int n) {
        return DataConnection.cmdToString(n);
    }

    boolean hasBeenTransferred() {
        boolean bl = this.mHandoverState == 3;
        return bl;
    }

    boolean isActivating() {
        boolean bl = this.getCurrentState() == this.mActivatingState;
        return bl;
    }

    boolean isActive() {
        boolean bl = this.getCurrentState() == this.mActiveState;
        return bl;
    }

    boolean isDisconnecting() {
        boolean bl = this.getCurrentState() == this.mDisconnectingState;
        return bl;
    }

    boolean isInactive() {
        boolean bl = this.getCurrentState() == this.mInactiveState;
        return bl;
    }

    public boolean isIpv4Connected() {
        boolean bl;
        block1 : {
            InetAddress inetAddress;
            boolean bl2 = false;
            Iterator iterator = this.mLinkProperties.getAddresses().iterator();
            do {
                bl = bl2;
                if (!iterator.hasNext()) break block1;
            } while (!((inetAddress = (InetAddress)iterator.next()) instanceof Inet4Address) || ((Inet4Address)(inetAddress = (Inet4Address)inetAddress)).isAnyLocalAddress() || ((Inet4Address)inetAddress).isLinkLocalAddress() || ((Inet4Address)inetAddress).isLoopbackAddress() || ((Inet4Address)inetAddress).isMulticastAddress());
            bl = true;
        }
        return bl;
    }

    public boolean isIpv6Connected() {
        boolean bl;
        block1 : {
            InetAddress inetAddress;
            boolean bl2 = false;
            Iterator iterator = this.mLinkProperties.getAddresses().iterator();
            do {
                bl = bl2;
                if (!iterator.hasNext()) break block1;
            } while (!((inetAddress = (InetAddress)iterator.next()) instanceof Inet6Address) || ((Inet6Address)(inetAddress = (Inet6Address)inetAddress)).isAnyLocalAddress() || ((Inet6Address)inetAddress).isLinkLocalAddress() || ((Inet6Address)inetAddress).isLoopbackAddress() || ((Inet6Address)inetAddress).isMulticastAddress());
            bl = true;
        }
        return bl;
    }

    protected void log(String string) {
        Rlog.d((String)this.getName(), (String)string);
    }

    protected void logd(String string) {
        Rlog.d((String)this.getName(), (String)string);
    }

    protected void loge(String string) {
        Rlog.e((String)this.getName(), (String)string);
    }

    protected void loge(String string, Throwable throwable) {
        Rlog.e((String)this.getName(), (String)string, (Throwable)throwable);
    }

    protected void logi(String string) {
        Rlog.i((String)this.getName(), (String)string);
    }

    protected void logv(String string) {
        Rlog.v((String)this.getName(), (String)string);
    }

    protected void logw(String string) {
        Rlog.w((String)this.getName(), (String)string);
    }

    public void onSubscriptionOverride(int n, int n2) {
        this.mSubscriptionOverride = this.mSubscriptionOverride & n | n2 & n;
        this.sendMessage(this.obtainMessage(262161));
    }

    void reevaluateDataConnectionProperties() {
        this.sendMessage(262170);
        this.log("reevaluate data connection properties");
    }

    void reevaluateRestrictedState() {
        this.sendMessage(262169);
        this.log("reevaluate restricted state");
    }

    public void reset() {
        this.sendMessage(262168);
        this.log("reset");
    }

    void setHandoverState(int n) {
        this.mHandoverState = n;
    }

    void setLinkPropertiesHttpProxy(ProxyInfo proxyInfo) {
        this.mLinkProperties.setHttpProxy(proxyInfo);
    }

    @VisibleForTesting
    public boolean shouldSkip464Xlat() {
        int n = this.mApnSetting.getSkip464Xlat();
        boolean bl = false;
        if (n != 0) {
            if (n != 1) {
                NetworkCapabilities networkCapabilities = this.getNetworkCapabilities();
                if (networkCapabilities.hasCapability(4) && !networkCapabilities.hasCapability(12)) {
                    bl = true;
                }
                return bl;
            }
            return true;
        }
        return false;
    }

    public void tearDown(ApnContext apnContext, String string, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tearDown: apnContext=");
        stringBuilder.append(apnContext);
        stringBuilder.append(" reason=");
        stringBuilder.append(string);
        stringBuilder.append(" onCompletedMsg=");
        stringBuilder.append((Object)message);
        this.log(stringBuilder.toString());
        this.sendMessage(262148, (Object)new DisconnectParams(apnContext, string, 2, message));
    }

    public void tearDownAll(String string, int n, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tearDownAll: reason=");
        stringBuilder.append(string);
        stringBuilder.append(", releaseType=");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        this.sendMessage(262150, (Object)new DisconnectParams(null, string, n, message));
    }

    void tearDownNow() {
        this.log("tearDownNow()");
        this.sendMessage(this.obtainMessage(262152));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(this.toStringSimple());
        stringBuilder.append(" mApnContexts=");
        stringBuilder.append(this.mApnContexts);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public String toStringSimple() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getName());
        stringBuilder.append(": State=");
        stringBuilder.append(this.getCurrentState().getName());
        stringBuilder.append(" mApnSetting=");
        stringBuilder.append((Object)this.mApnSetting);
        stringBuilder.append(" RefCount=");
        stringBuilder.append(this.mApnContexts.size());
        stringBuilder.append(" mCid=");
        stringBuilder.append(this.mCid);
        stringBuilder.append(" mCreateTime=");
        stringBuilder.append(this.mCreateTime);
        stringBuilder.append(" mLastastFailTime=");
        stringBuilder.append(this.mLastFailTime);
        stringBuilder.append(" mLastFailCause=");
        stringBuilder.append(this.mLastFailCause);
        stringBuilder.append(" mTag=");
        stringBuilder.append(this.mTag);
        stringBuilder.append(" mLinkProperties=");
        stringBuilder.append((Object)this.mLinkProperties);
        stringBuilder.append(" linkCapabilities=");
        stringBuilder.append((Object)this.getNetworkCapabilities());
        stringBuilder.append(" mRestrictedNetworkOverride=");
        stringBuilder.append(this.mRestrictedNetworkOverride);
        return stringBuilder.toString();
    }

    @VisibleForTesting
    public UpdateLinkPropertyResult updateLinkProperty(DataCallResponse object) {
        UpdateLinkPropertyResult updateLinkPropertyResult = new UpdateLinkPropertyResult(this.mLinkProperties);
        if (object == null) {
            return updateLinkPropertyResult;
        }
        updateLinkPropertyResult.newLp = new LinkProperties();
        updateLinkPropertyResult.setupResult = this.setLinkProperties((DataCallResponse)object, updateLinkPropertyResult.newLp);
        if (updateLinkPropertyResult.setupResult != SetupResult.SUCCESS) {
            object = new StringBuilder();
            ((StringBuilder)object).append("updateLinkProperty failed : ");
            ((StringBuilder)object).append((Object)updateLinkPropertyResult.setupResult);
            this.log(((StringBuilder)object).toString());
            return updateLinkPropertyResult;
        }
        updateLinkPropertyResult.newLp.setHttpProxy(this.mLinkProperties.getHttpProxy());
        this.checkSetMtu(this.mApnSetting, updateLinkPropertyResult.newLp);
        this.mLinkProperties = updateLinkPropertyResult.newLp;
        this.updateTcpBufferSizes(this.mRilRat);
        if (!updateLinkPropertyResult.oldLp.equals((Object)updateLinkPropertyResult.newLp)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("updateLinkProperty old LP=");
            ((StringBuilder)object).append((Object)updateLinkPropertyResult.oldLp);
            this.log(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("updateLinkProperty new LP=");
            ((StringBuilder)object).append((Object)updateLinkPropertyResult.newLp);
            this.log(((StringBuilder)object).toString());
        }
        if (!updateLinkPropertyResult.newLp.equals((Object)updateLinkPropertyResult.oldLp) && (object = this.mNetworkAgent) != null) {
            ((DcNetworkAgent)((Object)object)).sendLinkProperties(this.mLinkProperties, this);
        }
        return updateLinkPropertyResult;
    }

    public static class ConnectionParams {
        ApnContext mApnContext;
        final int mConnectionGeneration;
        Message mOnCompletedMsg;
        int mProfileId;
        final int mRequestType;
        int mRilRat;
        final int mSubId;
        int mTag;

        ConnectionParams(ApnContext apnContext, int n, int n2, Message message, int n3, int n4, int n5) {
            this.mApnContext = apnContext;
            this.mProfileId = n;
            this.mRilRat = n2;
            this.mOnCompletedMsg = message;
            this.mConnectionGeneration = n3;
            this.mRequestType = n4;
            this.mSubId = n5;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{mTag=");
            stringBuilder.append(this.mTag);
            stringBuilder.append(" mApnContext=");
            stringBuilder.append(this.mApnContext);
            stringBuilder.append(" mProfileId=");
            stringBuilder.append(this.mProfileId);
            stringBuilder.append(" mRat=");
            stringBuilder.append(this.mRilRat);
            stringBuilder.append(" mOnCompletedMsg=");
            stringBuilder.append(DataConnection.msgToString(this.mOnCompletedMsg));
            stringBuilder.append(" mRequestType=");
            stringBuilder.append(DcTracker.requestTypeToString(this.mRequestType));
            stringBuilder.append(" mSubId=");
            stringBuilder.append(this.mSubId);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    private class DcActivatingState
    extends State {
        private DcActivatingState() {
        }

        public void enter() {
            int n = DataConnection.this.mPhone.getPhoneId();
            int n2 = DataConnection.this.mId;
            long l = DataConnection.this.mApnSetting != null ? (long)DataConnection.this.mApnSetting.getApnTypeBitmask() : 0L;
            boolean bl = DataConnection.this.mApnSetting != null ? DataConnection.this.mApnSetting.canHandleType(17) : false;
            StatsLog.write((int)75, (int)2, (int)n, (int)n2, (long)l, (boolean)bl);
            DataConnection.this.setHandoverState(1);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean processMessage(Message object) {
            Object object2 = DataConnection.this;
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("DcActivatingState: msg=");
            ((StringBuilder)object3).append(DataConnection.msgToString(object));
            ((DataConnection)((Object)object2)).log(((StringBuilder)object3).toString());
            int n = object.what;
            if (n != 262144) {
                if (n != 262145) {
                    if (n != 262155) {
                        object3 = DataConnection.this;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("DcActivatingState not handled msg.what=");
                        ((StringBuilder)object2).append(DataConnection.this.getWhatToString(object.what));
                        ((StringBuilder)object2).append(" RefCount=");
                        ((StringBuilder)object2).append(DataConnection.this.mApnContexts.size());
                        ((DataConnection)((Object)object3)).log(((StringBuilder)object2).toString());
                        return false;
                    }
                } else {
                    Object object4;
                    Object object5;
                    object3 = (ConnectionParams)object.obj;
                    object2 = (DataCallResponse)object.getData().getParcelable("data_call_response");
                    SetupResult setupResult = DataConnection.this.onSetupConnectionCompleted(object.arg1, (DataCallResponse)object2, (ConnectionParams)object3);
                    if (setupResult != SetupResult.ERROR_STALE && DataConnection.this.mConnectionParams != object3) {
                        object4 = DataConnection.this;
                        object5 = new StringBuilder();
                        ((StringBuilder)object5).append("DcActivatingState: WEIRD mConnectionsParams:");
                        ((StringBuilder)object5).append(DataConnection.this.mConnectionParams);
                        ((StringBuilder)object5).append(" != cp:");
                        ((StringBuilder)object5).append(object3);
                        ((DataConnection)((Object)object4)).loge(((StringBuilder)object5).toString());
                    }
                    object5 = DataConnection.this;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("DcActivatingState onSetupConnectionCompleted result=");
                    ((StringBuilder)object4).append((Object)setupResult);
                    ((StringBuilder)object4).append(" dc=");
                    ((StringBuilder)object4).append((Object)DataConnection.this);
                    ((DataConnection)((Object)object5)).log(((StringBuilder)object4).toString());
                    if (((ConnectionParams)object3).mApnContext != null) {
                        object5 = ((ConnectionParams)object3).mApnContext;
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("onSetupConnectionCompleted result=");
                        ((StringBuilder)object4).append((Object)setupResult);
                        ((ApnContext)object5).requestLog(((StringBuilder)object4).toString());
                    }
                    if ((n = 2.$SwitchMap$com$android$internal$telephony$dataconnection$DataConnection$SetupResult[setupResult.ordinal()]) != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                if (n != 4) {
                                    if (n != 5) throw new RuntimeException("Unknown SetupResult, should not happen");
                                    DataConnection dataConnection = DataConnection.this;
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append("DcActivatingState: stale EVENT_SETUP_DATA_CONNECTION_DONE tag:");
                                    ((StringBuilder)object2).append(((ConnectionParams)object3).mTag);
                                    ((StringBuilder)object2).append(" != mTag:");
                                    ((StringBuilder)object2).append(DataConnection.this.mTag);
                                    dataConnection.loge(((StringBuilder)object2).toString());
                                    return true;
                                } else {
                                    long l = DataConnection.this.getSuggestedRetryDelay((DataCallResponse)object2);
                                    ((ConnectionParams)object3).mApnContext.setModemSuggestedDelay(l);
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append("DcActivatingState: ERROR_DATA_SERVICE_SPECIFIC_ERROR  delay=");
                                    ((StringBuilder)object2).append(l);
                                    ((StringBuilder)object2).append(" result=");
                                    ((StringBuilder)object2).append((Object)setupResult);
                                    ((StringBuilder)object2).append(" result.isRadioRestartFailure=");
                                    ((StringBuilder)object2).append(DataFailCause.isRadioRestartFailure((Context)DataConnection.this.mPhone.getContext(), (int)setupResult.mFailCause, (int)DataConnection.this.mPhone.getSubId()));
                                    ((StringBuilder)object2).append(" isPermanentFailure=");
                                    ((StringBuilder)object2).append(DataConnection.this.mDct.isPermanentFailure(setupResult.mFailCause));
                                    object2 = ((StringBuilder)object2).toString();
                                    DataConnection.this.log((String)object2);
                                    if (((ConnectionParams)object3).mApnContext != null) {
                                        ((ConnectionParams)object3).mApnContext.requestLog((String)object2);
                                    }
                                    DataConnection.this.mInactiveState.setEnterNotificationParams((ConnectionParams)object3, setupResult.mFailCause);
                                    DataConnection dataConnection = DataConnection.this;
                                    dataConnection.transitionTo((IState)dataConnection.mInactiveState);
                                }
                                return true;
                            } else {
                                DataConnection.this.tearDownData(object3);
                                DataConnection dataConnection = DataConnection.this;
                                dataConnection.transitionTo((IState)dataConnection.mDisconnectingErrorCreatingConnection);
                            }
                            return true;
                        } else {
                            DataConnection.this.mInactiveState.setEnterNotificationParams((ConnectionParams)object3, setupResult.mFailCause);
                            DataConnection dataConnection = DataConnection.this;
                            dataConnection.transitionTo((IState)dataConnection.mInactiveState);
                        }
                        return true;
                    } else {
                        DataConnection.this.mDcFailCause = 0;
                        DataConnection dataConnection = DataConnection.this;
                        dataConnection.transitionTo((IState)dataConnection.mActiveState);
                    }
                    return true;
                }
            }
            DataConnection.this.deferMessage(object);
            return true;
        }
    }

    private class DcActiveState
    extends State {
        private DcActiveState() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public void enter() {
            DataConnection dataConnection = DataConnection.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DcActiveState: enter dc=");
            stringBuilder.append((Object)DataConnection.this);
            dataConnection.log(stringBuilder.toString());
            int n = DataConnection.this.mPhone.getPhoneId();
            int n2 = DataConnection.this.mId;
            long l = DataConnection.this.mApnSetting != null ? (long)DataConnection.this.mApnSetting.getApnTypeBitmask() : 0L;
            boolean bl = DataConnection.this.mApnSetting != null ? DataConnection.this.mApnSetting.canHandleType(17) : false;
            StatsLog.write((int)75, (int)3, (int)n, (int)n2, (long)l, (boolean)bl);
            DataConnection.this.updateNetworkInfo();
            DataConnection.this.notifyAllWithEvent(null, 270336, "connected");
            DataConnection.this.mPhone.getCallTracker().registerForVoiceCallStarted(DataConnection.this.getHandler(), 262159, null);
            DataConnection.this.mPhone.getCallTracker().registerForVoiceCallEnded(DataConnection.this.getHandler(), 262160, null);
            DataConnection.this.mDcController.addActiveDcByCid(DataConnection.this);
            DataConnection.this.mNetworkInfo.setDetailedState(NetworkInfo.DetailedState.CONNECTED, DataConnection.this.mNetworkInfo.getReason(), null);
            DataConnection.this.mNetworkInfo.setExtraInfo(DataConnection.this.mApnSetting.getApnName());
            DataConnection dataConnection2 = DataConnection.this;
            dataConnection2.updateTcpBufferSizes(dataConnection2.mRilRat);
            NetworkMisc networkMisc = new NetworkMisc();
            if (DataConnection.this.mPhone.getCarrierSignalAgent().hasRegisteredReceivers("com.android.internal.telephony.CARRIER_SIGNAL_REDIRECTED")) {
                networkMisc.provisioningNotificationDisabled = true;
            }
            networkMisc.subscriberId = DataConnection.this.mPhone.getSubscriberId();
            networkMisc.skip464xlat = DataConnection.this.shouldSkip464Xlat();
            DataConnection dataConnection3 = DataConnection.this;
            dataConnection3.mRestrictedNetworkOverride = dataConnection3.shouldRestrictNetwork();
            DataConnection dataConnection4 = DataConnection.this;
            dataConnection4.mUnmeteredUseOnly = dataConnection4.isUnmeteredUseOnly();
            DataConnection dataConnection5 = DataConnection.this;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("mRestrictedNetworkOverride = ");
            stringBuilder2.append(DataConnection.this.mRestrictedNetworkOverride);
            stringBuilder2.append(", mUnmeteredUseOnly = ");
            stringBuilder2.append(DataConnection.this.mUnmeteredUseOnly);
            dataConnection5.log(stringBuilder2.toString());
            if (DataConnection.this.mConnectionParams != null && DataConnection.access$2400((DataConnection)DataConnection.this).mRequestType == 2) {
                DataConnection dataConnection6 = DataConnection.this.mPhone.getDcTracker(DataConnection.this.getHandoverSourceTransport()).getDataConnectionByApnType(DataConnection.access$2400((DataConnection)DataConnection.this).mApnContext.getApnType());
                if (dataConnection6 != null) {
                    dataConnection6.setHandoverState(3);
                }
                if (DataConnection.this.mHandoverSourceNetworkAgent == null) {
                    DataConnection.this.loge("Failed to get network agent from original data connection");
                    return;
                }
                DataConnection.this.log("Transfer network agent successfully.");
                DataConnection dataConnection7 = DataConnection.this;
                dataConnection7.mNetworkAgent = dataConnection7.mHandoverSourceNetworkAgent;
                DcNetworkAgent dcNetworkAgent = DataConnection.this.mNetworkAgent;
                DataConnection dataConnection8 = DataConnection.this;
                dcNetworkAgent.acquireOwnership(dataConnection8, dataConnection8.mTransportType);
                DataConnection.this.mNetworkAgent.sendNetworkCapabilities(DataConnection.this.getNetworkCapabilities(), DataConnection.this);
                DataConnection.this.mNetworkAgent.sendLinkProperties(DataConnection.this.mLinkProperties, DataConnection.this);
                DataConnection.this.mHandoverSourceNetworkAgent = null;
            } else {
                DataConnection dataConnection9 = DataConnection.this;
                dataConnection9.mScore = dataConnection9.calculateScore();
                TelephonyNetworkFactory telephonyNetworkFactory = PhoneFactory.getNetworkFactory(DataConnection.this.mPhone.getPhoneId());
                n = telephonyNetworkFactory == null ? -1 : telephonyNetworkFactory.getSerialNumber();
                DataConnection dataConnection10 = DataConnection.this;
                dataConnection10.mNetworkAgent = DcNetworkAgent.createDcNetworkAgent(dataConnection10, dataConnection10.mPhone, DataConnection.this.mNetworkInfo, DataConnection.this.mScore, networkMisc, n, DataConnection.this.mTransportType);
            }
            if (DataConnection.this.mTransportType == 1) {
                DataConnection.access$200((DataConnection)DataConnection.this).mCi.registerForNattKeepaliveStatus(DataConnection.this.getHandler(), 262162, null);
                DataConnection.access$200((DataConnection)DataConnection.this).mCi.registerForLceInfo(DataConnection.this.getHandler(), 262167, null);
            }
            TelephonyMetrics.getInstance().writeRilDataCallEvent(DataConnection.this.mPhone.getPhoneId(), DataConnection.this.mCid, DataConnection.this.mApnSetting.getApnTypeBitmask(), 1);
        }

        public void exit() {
            Object object = DataConnection.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DcActiveState: exit dc=");
            stringBuilder.append((Object)this);
            object.log(stringBuilder.toString());
            DataConnection.this.mNetworkInfo.getReason();
            object = DataConnection.this.mDcController.isExecutingCarrierChange() ? "carrierChange" : (DataConnection.this.mDisconnectParams != null && DataConnection.access$2500((DataConnection)DataConnection.this).mReason != null ? DataConnection.access$2500((DataConnection)DataConnection.this).mReason : DataFailCause.toString((int)DataConnection.this.mDcFailCause));
            DataConnection.this.mPhone.getCallTracker().unregisterForVoiceCallStarted(DataConnection.this.getHandler());
            DataConnection.this.mPhone.getCallTracker().unregisterForVoiceCallEnded(DataConnection.this.getHandler());
            if (DataConnection.this.mHandoverState != 2) {
                DataConnection.this.mNetworkInfo.setDetailedState(NetworkInfo.DetailedState.DISCONNECTED, (String)object, DataConnection.this.mNetworkInfo.getExtraInfo());
            }
            if (DataConnection.this.mTransportType == 1) {
                DataConnection.access$200((DataConnection)DataConnection.this).mCi.unregisterForNattKeepaliveStatus(DataConnection.this.getHandler());
                DataConnection.access$200((DataConnection)DataConnection.this).mCi.unregisterForLceInfo(DataConnection.this.getHandler());
            }
            if (DataConnection.this.mNetworkAgent != null) {
                DataConnection.this.mNetworkAgent.sendNetworkInfo(DataConnection.this.mNetworkInfo, DataConnection.this);
                DataConnection.this.mNetworkAgent.releaseOwnership(DataConnection.this);
            }
            DataConnection.this.mNetworkAgent = null;
            TelephonyMetrics.getInstance().writeRilDataCallEvent(DataConnection.this.mPhone.getPhoneId(), DataConnection.this.mCid, DataConnection.this.mApnSetting.getApnTypeBitmask(), 2);
        }

        public boolean processMessage(Message object) {
            boolean bl;
            switch (object.what) {
                default: {
                    DataConnection dataConnection = DataConnection.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DcActiveState not handled msg.what=");
                    stringBuilder.append(DataConnection.this.getWhatToString(object.what));
                    dataConnection.log(stringBuilder.toString());
                    bl = false;
                    break;
                }
                case 262170: {
                    DataConnection.this.updateScore();
                    bl = true;
                    break;
                }
                case 262169: {
                    if (DataConnection.this.mRestrictedNetworkOverride && !DataConnection.this.shouldRestrictNetwork()) {
                        object = DataConnection.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Data connection becomes not-restricted. dc=");
                        stringBuilder.append((Object)this);
                        object.log(stringBuilder.toString());
                        DataConnection.this.mRestrictedNetworkOverride = false;
                        DataConnection.this.mNetworkAgent.sendNetworkCapabilities(DataConnection.this.getNetworkCapabilities(), DataConnection.this);
                    }
                    if (DataConnection.this.mUnmeteredUseOnly && !DataConnection.this.isUnmeteredUseOnly()) {
                        DataConnection.this.mUnmeteredUseOnly = false;
                        DataConnection.this.mNetworkAgent.sendNetworkCapabilities(DataConnection.this.getNetworkCapabilities(), DataConnection.this);
                    }
                    bl = true;
                    break;
                }
                case 262167: {
                    AsyncResult asyncResult = (AsyncResult)object.obj;
                    if (asyncResult.exception != null) {
                        DataConnection dataConnection = DataConnection.this;
                        object = new StringBuilder();
                        object.append("EVENT_LINK_CAPACITY_CHANGED e=");
                        object.append(asyncResult.exception);
                        dataConnection.loge(object.toString());
                    } else {
                        object = (LinkCapacityEstimate)asyncResult.result;
                        NetworkCapabilities networkCapabilities = DataConnection.this.getNetworkCapabilities();
                        if (object.downlinkCapacityKbps != -1) {
                            networkCapabilities.setLinkDownstreamBandwidthKbps(object.downlinkCapacityKbps);
                        }
                        if (object.uplinkCapacityKbps != -1) {
                            networkCapabilities.setLinkUpstreamBandwidthKbps(object.uplinkCapacityKbps);
                        }
                        if (DataConnection.this.mNetworkAgent != null) {
                            DataConnection.this.mNetworkAgent.sendNetworkCapabilities(networkCapabilities, DataConnection.this);
                        }
                    }
                    bl = true;
                    break;
                }
                case 262166: {
                    int n = object.arg1;
                    int n2 = DataConnection.access$2100((DataConnection)DataConnection.this).keepaliveTracker.getHandleForSlot(n);
                    if (n2 < 0) {
                        DataConnection dataConnection = DataConnection.this;
                        object = new StringBuilder();
                        object.append("No slot found for stopSocketKeepalive! ");
                        object.append(n);
                        dataConnection.loge(object.toString());
                        bl = true;
                        break;
                    }
                    DataConnection dataConnection = DataConnection.this;
                    object = new StringBuilder();
                    object.append("Stopping keepalive with handle: ");
                    object.append(n2);
                    dataConnection.logd(object.toString());
                    DataConnection.access$200((DataConnection)DataConnection.this).mCi.stopNattKeepalive(n2, DataConnection.this.obtainMessage(262164, n2, n, null));
                    bl = true;
                    break;
                }
                case 262165: {
                    KeepalivePacketData keepalivePacketData = (KeepalivePacketData)object.obj;
                    int n = object.arg1;
                    int n3 = object.arg2;
                    if (DataConnection.this.mTransportType == 1) {
                        DataConnection.access$200((DataConnection)DataConnection.this).mCi.startNattKeepalive(DataConnection.this.mCid, keepalivePacketData, n3 * 1000, DataConnection.this.obtainMessage(262163, n, 0, null));
                    } else if (DataConnection.this.mNetworkAgent != null) {
                        DataConnection.this.mNetworkAgent.onSocketKeepaliveEvent(object.arg1, -20);
                    }
                    bl = true;
                    break;
                }
                case 262164: {
                    Object object2 = (AsyncResult)object.obj;
                    int n = object.arg1;
                    int n4 = object.arg2;
                    if (((AsyncResult)object2).exception != null) {
                        DataConnection dataConnection = DataConnection.this;
                        object = new StringBuilder();
                        object.append("EVENT_KEEPALIVE_STOPPED: error stopping keepalive for handle=");
                        object.append(n);
                        object.append(" e=");
                        object.append(((AsyncResult)object2).exception);
                        dataConnection.loge(object.toString());
                        DataConnection.access$2100((DataConnection)DataConnection.this).keepaliveTracker.handleKeepaliveStatus(new KeepaliveStatus(3));
                    } else {
                        object = DataConnection.this;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Keepalive Stop Requested for handle=");
                        ((StringBuilder)object2).append(n);
                        object.log(((StringBuilder)object2).toString());
                        DataConnection.access$2100((DataConnection)DataConnection.this).keepaliveTracker.handleKeepaliveStatus(new KeepaliveStatus(n, 1));
                    }
                    bl = true;
                    break;
                }
                case 262163: {
                    AsyncResult asyncResult = (AsyncResult)object.obj;
                    int n = object.arg1;
                    if (asyncResult.exception == null && asyncResult.result != null) {
                        object = (KeepaliveStatus)asyncResult.result;
                        if (object == null) {
                            DataConnection.this.loge("Null KeepaliveStatus received!");
                        } else {
                            DataConnection.access$2100((DataConnection)DataConnection.this).keepaliveTracker.handleKeepaliveStarted(n, (KeepaliveStatus)object);
                        }
                    } else {
                        object = DataConnection.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("EVENT_KEEPALIVE_STARTED: error starting keepalive, e=");
                        stringBuilder.append(asyncResult.exception);
                        object.loge(stringBuilder.toString());
                        DataConnection.this.mNetworkAgent.onSocketKeepaliveEvent(n, -31);
                    }
                    bl = true;
                    break;
                }
                case 262162: {
                    object = (AsyncResult)object.obj;
                    if (object.exception != null) {
                        DataConnection dataConnection = DataConnection.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("EVENT_KEEPALIVE_STATUS: error in keepalive, e=");
                        stringBuilder.append(object.exception);
                        dataConnection.loge(stringBuilder.toString());
                    }
                    if (object.result != null) {
                        object = (KeepaliveStatus)object.result;
                        DataConnection.access$2100((DataConnection)DataConnection.this).keepaliveTracker.handleKeepaliveStatus((KeepaliveStatus)object);
                    }
                    bl = true;
                    break;
                }
                case 262159: 
                case 262160: {
                    DataConnection.this.updateNetworkInfo();
                    DataConnection.this.updateNetworkInfoSuspendState();
                    if (DataConnection.this.mNetworkAgent != null) {
                        DataConnection.this.mNetworkAgent.sendNetworkCapabilities(DataConnection.this.getNetworkCapabilities(), DataConnection.this);
                        DataConnection.this.mNetworkAgent.sendNetworkInfo(DataConnection.this.mNetworkInfo, DataConnection.this);
                    }
                    bl = true;
                    break;
                }
                case 262158: {
                    Object object3 = (AsyncResult)object.obj;
                    if (object3.exception != null) {
                        object = DataConnection.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("EVENT_BW_REFRESH_RESPONSE: error ignoring, e=");
                        stringBuilder.append(object3.exception);
                        object.log(stringBuilder.toString());
                    } else {
                        object3 = (LinkCapacityEstimate)object3.result;
                        object = DataConnection.this.getNetworkCapabilities();
                        if (DataConnection.this.mPhone.getLceStatus() == 1) {
                            object.setLinkDownstreamBandwidthKbps(object3.downlinkCapacityKbps);
                            if (DataConnection.this.mNetworkAgent != null) {
                                DataConnection.this.mNetworkAgent.sendNetworkCapabilities((NetworkCapabilities)object, DataConnection.this);
                            }
                        }
                    }
                    bl = true;
                    break;
                }
                case 262156: 
                case 262157: 
                case 262161: {
                    DataConnection.this.updateNetworkInfo();
                    if (DataConnection.this.mNetworkAgent != null) {
                        DataConnection.this.mNetworkAgent.sendNetworkCapabilities(DataConnection.this.getNetworkCapabilities(), DataConnection.this);
                        DataConnection.this.mNetworkAgent.sendNetworkInfo(DataConnection.this.mNetworkInfo, DataConnection.this);
                    }
                    bl = true;
                    break;
                }
                case 262153: {
                    DataConnection dataConnection = DataConnection.this;
                    object = new StringBuilder();
                    object.append("DcActiveState EVENT_LOST_CONNECTION dc=");
                    object.append((Object)DataConnection.this);
                    dataConnection.log(object.toString());
                    DataConnection.this.mInactiveState.setEnterNotificationParams(65540);
                    object = DataConnection.this;
                    object.transitionTo((IState)((DataConnection)((Object)object)).mInactiveState);
                    bl = true;
                    break;
                }
                case 262150: {
                    DataConnection dataConnection = DataConnection.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DcActiveState EVENT_DISCONNECT clearing apn contexts, dc=");
                    stringBuilder.append((Object)DataConnection.this);
                    dataConnection.log(stringBuilder.toString());
                    object = (DisconnectParams)object.obj;
                    DataConnection.this.mDisconnectParams = (DisconnectParams)object;
                    DataConnection.this.mConnectionParams = null;
                    object.mTag = DataConnection.this.mTag;
                    DataConnection.this.tearDownData(object);
                    object = DataConnection.this;
                    object.transitionTo((IState)((DataConnection)((Object)object)).mDisconnectingState);
                    bl = true;
                    break;
                }
                case 262148: {
                    object = (DisconnectParams)object.obj;
                    Object object4 = DataConnection.this;
                    Object object5 = new StringBuilder();
                    ((StringBuilder)object5).append("DcActiveState: EVENT_DISCONNECT dp=");
                    ((StringBuilder)object5).append(object);
                    ((StringBuilder)object5).append(" dc=");
                    ((StringBuilder)object5).append((Object)DataConnection.this);
                    ((DataConnection)((Object)object4)).log(((StringBuilder)object5).toString());
                    if (DataConnection.this.mApnContexts.containsKey(object.mApnContext)) {
                        object5 = DataConnection.this;
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("DcActiveState msg.what=EVENT_DISCONNECT RefCount=");
                        ((StringBuilder)object4).append(DataConnection.this.mApnContexts.size());
                        ((DataConnection)((Object)object5)).log(((StringBuilder)object4).toString());
                        if (DataConnection.this.mApnContexts.size() == 1) {
                            DataConnection.this.mApnContexts.clear();
                            DataConnection.this.mDisconnectParams = (DisconnectParams)object;
                            DataConnection.this.mConnectionParams = null;
                            object.mTag = DataConnection.this.mTag;
                            DataConnection.this.tearDownData(object);
                            object = DataConnection.this;
                            object.transitionTo((IState)((DataConnection)((Object)object)).mDisconnectingState);
                        } else {
                            DataConnection.this.mApnContexts.remove(object.mApnContext);
                            DataConnection.access$5176(DataConnection.this, object.mApnContext.getApnTypeBitmask());
                            DataConnection.this.mNetworkAgent.sendNetworkCapabilities(DataConnection.this.getNetworkCapabilities(), DataConnection.this);
                            DataConnection.this.notifyDisconnectCompleted((DisconnectParams)object, false);
                        }
                    } else {
                        object4 = DataConnection.this;
                        object5 = new StringBuilder();
                        ((StringBuilder)object5).append("DcActiveState ERROR no such apnContext=");
                        ((StringBuilder)object5).append(object.mApnContext);
                        ((StringBuilder)object5).append(" in this dc=");
                        ((StringBuilder)object5).append((Object)DataConnection.this);
                        ((DataConnection)((Object)object4)).log(((StringBuilder)object5).toString());
                        DataConnection.this.notifyDisconnectCompleted((DisconnectParams)object, false);
                    }
                    bl = true;
                    break;
                }
                case 262144: {
                    ConnectionParams connectionParams = (ConnectionParams)object.obj;
                    DataConnection.this.mApnContexts.put(connectionParams.mApnContext, connectionParams);
                    DataConnection.access$5172(DataConnection.this, connectionParams.mApnContext.getApnTypeBitmask());
                    DataConnection.this.mNetworkAgent.sendNetworkCapabilities(DataConnection.this.getNetworkCapabilities(), DataConnection.this);
                    object = DataConnection.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DcActiveState: EVENT_CONNECT cp=");
                    stringBuilder.append(connectionParams);
                    stringBuilder.append(" dc=");
                    stringBuilder.append((Object)DataConnection.this);
                    object.log(stringBuilder.toString());
                    DataConnection.this.notifyConnectCompleted(connectionParams, 0, false);
                    bl = true;
                }
            }
            return bl;
        }
    }

    private class DcDefaultState
    extends State {
        private DcDefaultState() {
        }

        public void enter() {
            DataConnection.this.log("DcDefaultState: enter");
            DataConnection.this.mPhone.getServiceStateTracker().registerForDataRegStateOrRatChanged(DataConnection.this.mTransportType, DataConnection.this.getHandler(), 262155, null);
            DataConnection.this.mPhone.getServiceStateTracker().registerForDataRoamingOn(DataConnection.this.getHandler(), 262156, null);
            DataConnection.this.mPhone.getServiceStateTracker().registerForDataRoamingOff(DataConnection.this.getHandler(), 262157, null, true);
            DataConnection.this.mDcController.addDc(DataConnection.this);
        }

        public void exit() {
            DataConnection.this.log("DcDefaultState: exit");
            DataConnection.this.mPhone.getServiceStateTracker().unregisterForDataRegStateOrRatChanged(DataConnection.this.mTransportType, DataConnection.this.getHandler());
            DataConnection.this.mPhone.getServiceStateTracker().unregisterForDataRoamingOn(DataConnection.this.getHandler());
            DataConnection.this.mPhone.getServiceStateTracker().unregisterForDataRoamingOff(DataConnection.this.getHandler());
            DataConnection.this.mDcController.removeDc(DataConnection.this);
            if (DataConnection.this.mAc != null) {
                DataConnection.this.mAc.disconnected();
                DataConnection.this.mAc = null;
            }
            DataConnection.this.mApnContexts.clear();
            DataConnection dataConnection = DataConnection.this;
            dataConnection.mReconnectIntent = null;
            dataConnection.mDct = null;
            DataConnection.this.mApnSetting = null;
            DataConnection.this.mPhone = null;
            DataConnection.this.mDataServiceManager = null;
            DataConnection.this.mLinkProperties = null;
            DataConnection.this.mLastFailCause = 0;
            DataConnection.this.mUserData = null;
            DataConnection.this.mDcController = null;
            DataConnection.this.mDcTesterFailBringUpAll = null;
        }

        public boolean processMessage(Message object) {
            Object object2 = DataConnection.this;
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("DcDefault msg=");
            ((StringBuilder)object3).append(DataConnection.this.getWhatToString(((Message)object).what));
            ((StringBuilder)object3).append(" RefCount=");
            ((StringBuilder)object3).append(DataConnection.this.mApnContexts.size());
            ((DataConnection)((Object)object2)).log(((StringBuilder)object3).toString());
            switch (((Message)object).what) {
                default: {
                    object3 = DataConnection.this;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("DcDefaultState: shouldn't happen but ignore msg.what=");
                    ((StringBuilder)object2).append(DataConnection.this.getWhatToString(((Message)object).what));
                    ((DataConnection)((Object)object3)).log(((StringBuilder)object2).toString());
                    break;
                }
                case 262168: {
                    DataConnection.this.log("DcDefaultState: msg.what=REQ_RESET");
                    object = DataConnection.this;
                    object.transitionTo((IState)((DataConnection)((Object)object)).mInactiveState);
                    break;
                }
                case 262165: 
                case 262166: {
                    if (DataConnection.this.mNetworkAgent == null) break;
                    DataConnection.this.mNetworkAgent.onSocketKeepaliveEvent(((Message)object).arg1, -20);
                    break;
                }
                case 262156: 
                case 262157: 
                case 262161: {
                    DataConnection.this.updateNetworkInfo();
                    if (DataConnection.this.mNetworkAgent == null) break;
                    DataConnection.this.mNetworkAgent.sendNetworkCapabilities(DataConnection.this.getNetworkCapabilities(), DataConnection.this);
                    DataConnection.this.mNetworkAgent.sendNetworkInfo(DataConnection.this.mNetworkInfo, DataConnection.this);
                    break;
                }
                case 262155: {
                    object = (Pair)((AsyncResult)object.obj).result;
                    DataConnection.this.mDataRegState = (Integer)((Pair)object).first;
                    if (DataConnection.this.mRilRat != (Integer)((Pair)object).second) {
                        DataConnection.this.updateTcpBufferSizes((Integer)((Pair)object).second);
                    }
                    DataConnection.this.mRilRat = (Integer)((Pair)object).second;
                    object2 = DataConnection.this;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("DcDefaultState: EVENT_DATA_CONNECTION_DRS_OR_RAT_CHANGED drs=");
                    ((StringBuilder)object).append(DataConnection.this.mDataRegState);
                    ((StringBuilder)object).append(" mRilRat=");
                    ((StringBuilder)object).append(DataConnection.this.mRilRat);
                    ((DataConnection)((Object)object2)).log(((StringBuilder)object).toString());
                    DataConnection.this.updateNetworkInfo();
                    DataConnection.this.updateNetworkInfoSuspendState();
                    if (DataConnection.this.mNetworkAgent == null) break;
                    DataConnection.this.mNetworkAgent.sendNetworkCapabilities(DataConnection.this.getNetworkCapabilities(), DataConnection.this);
                    DataConnection.this.mNetworkAgent.sendNetworkInfo(DataConnection.this.mNetworkInfo, DataConnection.this);
                    DataConnection.this.mNetworkAgent.sendLinkProperties(DataConnection.this.mLinkProperties, DataConnection.this);
                    break;
                }
                case 262153: {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("DcDefaultState ignore EVENT_LOST_CONNECTION tag=");
                    ((StringBuilder)object2).append(((Message)object).arg1);
                    ((StringBuilder)object2).append(":mTag=");
                    ((StringBuilder)object2).append(DataConnection.this.mTag);
                    object = ((StringBuilder)object2).toString();
                    DataConnection.this.logAndAddLogRec((String)object);
                    break;
                }
                case 262152: {
                    DataConnection.this.log("DcDefaultState EVENT_TEAR_DOWN_NOW");
                    DataConnection.this.mDataServiceManager.deactivateDataCall(DataConnection.this.mCid, 1, null);
                    break;
                }
                case 262148: 
                case 262150: 
                case 262169: {
                    object2 = DataConnection.this;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("DcDefaultState deferring msg.what=");
                    ((StringBuilder)object3).append(DataConnection.this.getWhatToString(((Message)object).what));
                    ((StringBuilder)object3).append(" RefCount=");
                    ((StringBuilder)object3).append(DataConnection.this.mApnContexts.size());
                    ((DataConnection)((Object)object2)).log(((StringBuilder)object3).toString());
                    DataConnection.this.deferMessage((Message)object);
                    break;
                }
                case 262144: {
                    DataConnection.this.log("DcDefaultState: msg.what=EVENT_CONNECT, fail not expected");
                    object = (ConnectionParams)((Message)object).obj;
                    DataConnection.this.notifyConnectCompleted((ConnectionParams)object, 65536, false);
                }
            }
            return true;
        }
    }

    private class DcDisconnectingState
    extends State {
        private DcDisconnectingState() {
        }

        public void enter() {
            int n = DataConnection.this.mPhone.getPhoneId();
            int n2 = DataConnection.this.mId;
            long l = DataConnection.this.mApnSetting != null ? (long)DataConnection.this.mApnSetting.getApnTypeBitmask() : 0L;
            boolean bl = DataConnection.this.mApnSetting != null ? DataConnection.this.mApnSetting.canHandleType(17) : false;
            StatsLog.write((int)75, (int)4, (int)n, (int)n2, (long)l, (boolean)bl);
        }

        public boolean processMessage(Message object) {
            boolean bl;
            int n = object.what;
            if (n != 262144) {
                if (n != 262147) {
                    DataConnection dataConnection = DataConnection.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DcDisconnectingState not handled msg.what=");
                    stringBuilder.append(DataConnection.this.getWhatToString(object.what));
                    dataConnection.log(stringBuilder.toString());
                    bl = false;
                } else {
                    object = (DisconnectParams)object.obj;
                    Object object2 = new StringBuilder();
                    ((StringBuilder)object2).append("DcDisconnectingState msg.what=EVENT_DEACTIVATE_DONE RefCount=");
                    ((StringBuilder)object2).append(DataConnection.this.mApnContexts.size());
                    object2 = ((StringBuilder)object2).toString();
                    DataConnection.this.log((String)object2);
                    if (object.mApnContext != null) {
                        object.mApnContext.requestLog((String)object2);
                    }
                    if (object.mTag == DataConnection.this.mTag) {
                        DataConnection.this.mInactiveState.setEnterNotificationParams((DisconnectParams)object);
                        object = DataConnection.this;
                        object.transitionTo((IState)((DataConnection)((Object)object)).mInactiveState);
                    } else {
                        object2 = DataConnection.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("DcDisconnectState stale EVENT_DEACTIVATE_DONE dp.tag=");
                        stringBuilder.append(object.mTag);
                        stringBuilder.append(" mTag=");
                        stringBuilder.append(DataConnection.this.mTag);
                        ((DataConnection)((Object)object2)).log(stringBuilder.toString());
                    }
                    bl = true;
                }
            } else {
                DataConnection dataConnection = DataConnection.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("DcDisconnectingState msg.what=EVENT_CONNECT. Defer. RefCount = ");
                stringBuilder.append(DataConnection.this.mApnContexts.size());
                dataConnection.log(stringBuilder.toString());
                DataConnection.this.deferMessage(object);
                bl = true;
            }
            return bl;
        }
    }

    private class DcDisconnectionErrorCreatingConnection
    extends State {
        private DcDisconnectionErrorCreatingConnection() {
        }

        public void enter() {
            int n = DataConnection.this.mPhone.getPhoneId();
            int n2 = DataConnection.this.mId;
            long l = DataConnection.this.mApnSetting != null ? (long)DataConnection.this.mApnSetting.getApnTypeBitmask() : 0L;
            boolean bl = DataConnection.this.mApnSetting != null ? DataConnection.this.mApnSetting.canHandleType(17) : false;
            StatsLog.write((int)75, (int)5, (int)n, (int)n2, (long)l, (boolean)bl);
        }

        public boolean processMessage(Message object) {
            boolean bl;
            if (object.what != 262147) {
                DataConnection dataConnection = DataConnection.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("DcDisconnectionErrorCreatingConnection not handled msg.what=");
                stringBuilder.append(DataConnection.this.getWhatToString(object.what));
                dataConnection.log(stringBuilder.toString());
                bl = false;
            } else {
                object = (ConnectionParams)object.obj;
                if (object.mTag == DataConnection.this.mTag) {
                    DataConnection.this.log("DcDisconnectionErrorCreatingConnection msg.what=EVENT_DEACTIVATE_DONE");
                    if (object.mApnContext != null) {
                        object.mApnContext.requestLog("DcDisconnectionErrorCreatingConnection msg.what=EVENT_DEACTIVATE_DONE");
                    }
                    DataConnection.this.mInactiveState.setEnterNotificationParams((ConnectionParams)object, 65538);
                    object = DataConnection.this;
                    object.transitionTo((IState)((DataConnection)((Object)object)).mInactiveState);
                } else {
                    DataConnection dataConnection = DataConnection.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DcDisconnectionErrorCreatingConnection stale EVENT_DEACTIVATE_DONE dp.tag=");
                    stringBuilder.append(object.mTag);
                    stringBuilder.append(", mTag=");
                    stringBuilder.append(DataConnection.this.mTag);
                    dataConnection.log(stringBuilder.toString());
                }
                bl = true;
            }
            return bl;
        }
    }

    private class DcInactiveState
    extends State {
        private DcInactiveState() {
        }

        public void enter() {
            Object object = DataConnection.this;
            ++object.mTag;
            Object object2 = DataConnection.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("DcInactiveState: enter() mTag=");
            ((StringBuilder)object).append(DataConnection.this.mTag);
            ((DataConnection)((Object)object2)).log(((StringBuilder)object).toString());
            int n = DataConnection.this.mPhone.getPhoneId();
            int n2 = DataConnection.this.mId;
            long l = DataConnection.this.mApnSetting != null ? (long)DataConnection.this.mApnSetting.getApnTypeBitmask() : 0L;
            boolean bl = DataConnection.this.mApnSetting != null ? DataConnection.this.mApnSetting.canHandleType(17) : false;
            StatsLog.write((int)75, (int)1, (int)n, (int)n2, (long)l, (boolean)bl);
            if (DataConnection.this.mHandoverState == 2) {
                DataConnection.this.mHandoverState = 3;
            }
            if (DataConnection.this.mConnectionParams != null) {
                object = DataConnection.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("DcInactiveState: enter notifyConnectCompleted +ALL failCause=");
                ((StringBuilder)object2).append(DataConnection.this.mDcFailCause);
                object.log(((StringBuilder)object2).toString());
                object = DataConnection.this;
                object.notifyConnectCompleted(object.mConnectionParams, DataConnection.this.mDcFailCause, true);
            }
            if (DataConnection.this.mDisconnectParams != null) {
                object = DataConnection.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("DcInactiveState: enter notifyDisconnectCompleted +ALL failCause=");
                ((StringBuilder)object2).append(DataConnection.this.mDcFailCause);
                object.log(((StringBuilder)object2).toString());
                object = DataConnection.this;
                object.notifyDisconnectCompleted(object.mDisconnectParams, true);
            }
            if (DataConnection.this.mDisconnectParams == null && DataConnection.this.mConnectionParams == null && DataConnection.this.mDcFailCause != 0) {
                object = DataConnection.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("DcInactiveState: enter notifyAllDisconnectCompleted failCause=");
                ((StringBuilder)object2).append(DataConnection.this.mDcFailCause);
                object.log(((StringBuilder)object2).toString());
                object = DataConnection.this;
                object.notifyAllWithEvent(null, 270351, DataFailCause.toString((int)object.mDcFailCause));
            }
            DataConnection.this.mDcController.removeActiveDcByCid(DataConnection.this);
            DataConnection.this.clearSettings();
        }

        public void exit() {
        }

        public boolean processMessage(Message object) {
            switch (object.what) {
                default: {
                    DataConnection dataConnection = DataConnection.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DcInactiveState not handled msg.what=");
                    stringBuilder.append(DataConnection.this.getWhatToString(object.what));
                    dataConnection.log(stringBuilder.toString());
                    return false;
                }
                case 262168: 
                case 262169: {
                    DataConnection dataConnection = DataConnection.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DcInactiveState: msg.what=");
                    stringBuilder.append(DataConnection.this.getWhatToString(object.what));
                    stringBuilder.append(", ignore we're already done");
                    dataConnection.log(stringBuilder.toString());
                    return true;
                }
                case 262150: {
                    DataConnection.this.log("DcInactiveState: msg.what=EVENT_DISCONNECT_ALL");
                    DataConnection.this.notifyDisconnectCompleted((DisconnectParams)object.obj, false);
                    return true;
                }
                case 262148: {
                    DataConnection.this.log("DcInactiveState: msg.what=EVENT_DISCONNECT");
                    DataConnection.this.notifyDisconnectCompleted((DisconnectParams)object.obj, false);
                    return true;
                }
                case 262144: 
            }
            DataConnection.this.log("DcInactiveState: mag.what=EVENT_CONNECT");
            object = (ConnectionParams)object.obj;
            if (!DataConnection.this.initConnection((ConnectionParams)object)) {
                DataConnection.this.log("DcInactiveState: msg.what=EVENT_CONNECT initConnection failed");
                DataConnection.this.notifyConnectCompleted((ConnectionParams)object, 65538, false);
                object = DataConnection.this;
                object.transitionTo((IState)((DataConnection)((Object)object)).mInactiveState);
                return true;
            }
            int n = DataConnection.this.connect((ConnectionParams)object);
            if (n != 0) {
                DataConnection.this.log("DcInactiveState: msg.what=EVENT_CONNECT connect failed");
                DataConnection.this.notifyConnectCompleted((ConnectionParams)object, n, false);
                object = DataConnection.this;
                object.transitionTo((IState)((DataConnection)((Object)object)).mInactiveState);
                return true;
            }
            if (DataConnection.this.mSubId == -1) {
                DataConnection.this.mSubId = object.mSubId;
            }
            object = DataConnection.this;
            object.transitionTo((IState)((DataConnection)((Object)object)).mActivatingState);
            return true;
        }

        public void setEnterNotificationParams(int n) {
            DataConnection.this.mConnectionParams = null;
            DataConnection.this.mDisconnectParams = null;
            DataConnection.this.mDcFailCause = n;
        }

        public void setEnterNotificationParams(ConnectionParams connectionParams, int n) {
            DataConnection.this.log("DcInactiveState: setEnterNotificationParams cp,cause");
            DataConnection.this.mConnectionParams = connectionParams;
            DataConnection.this.mDisconnectParams = null;
            DataConnection.this.mDcFailCause = n;
        }

        public void setEnterNotificationParams(DisconnectParams disconnectParams) {
            DataConnection.this.log("DcInactiveState: setEnterNotificationParams dp");
            DataConnection.this.mConnectionParams = null;
            DataConnection.this.mDisconnectParams = disconnectParams;
            DataConnection.this.mDcFailCause = 0;
        }
    }

    public static class DisconnectParams {
        public ApnContext mApnContext;
        Message mOnCompletedMsg;
        String mReason;
        final int mReleaseType;
        int mTag;

        DisconnectParams(ApnContext apnContext, String string, int n, Message message) {
            this.mApnContext = apnContext;
            this.mReason = string;
            this.mReleaseType = n;
            this.mOnCompletedMsg = message;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{mTag=");
            stringBuilder.append(this.mTag);
            stringBuilder.append(" mApnContext=");
            stringBuilder.append(this.mApnContext);
            stringBuilder.append(" mReason=");
            stringBuilder.append(this.mReason);
            stringBuilder.append(" mReleaseType=");
            stringBuilder.append(DcTracker.releaseTypeToString(this.mReleaseType));
            stringBuilder.append(" mOnCompletedMsg=");
            stringBuilder.append(DataConnection.msgToString(this.mOnCompletedMsg));
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HandoverState {
    }

    public static enum SetupResult {
        SUCCESS,
        ERROR_RADIO_NOT_AVAILABLE,
        ERROR_INVALID_ARG,
        ERROR_STALE,
        ERROR_DATA_SERVICE_SPECIFIC_ERROR;
        
        public int mFailCause = DataFailCause.getFailCause((int)0);

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.name());
            stringBuilder.append("  SetupResult.mFailCause=");
            stringBuilder.append(this.mFailCause);
            return stringBuilder.toString();
        }
    }

    public static class UpdateLinkPropertyResult {
        public LinkProperties newLp;
        public LinkProperties oldLp;
        public SetupResult setupResult = SetupResult.SUCCESS;

        public UpdateLinkPropertyResult(LinkProperties linkProperties) {
            this.oldLp = linkProperties;
            this.newLp = linkProperties;
        }
    }

}

