/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Resources
 *  android.hardware.radio.V1_0.CallForwardInfo
 *  android.hardware.radio.V1_0.Carrier
 *  android.hardware.radio.V1_0.CarrierRestrictions
 *  android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo
 *  android.hardware.radio.V1_0.CdmaSmsAck
 *  android.hardware.radio.V1_0.CdmaSmsAddress
 *  android.hardware.radio.V1_0.CdmaSmsMessage
 *  android.hardware.radio.V1_0.CdmaSmsSubaddress
 *  android.hardware.radio.V1_0.CdmaSmsWriteArgs
 *  android.hardware.radio.V1_0.CellInfo
 *  android.hardware.radio.V1_0.DataProfileInfo
 *  android.hardware.radio.V1_0.Dial
 *  android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo
 *  android.hardware.radio.V1_0.GsmSmsMessage
 *  android.hardware.radio.V1_0.HardwareConfig
 *  android.hardware.radio.V1_0.HardwareConfigModem
 *  android.hardware.radio.V1_0.HardwareConfigSim
 *  android.hardware.radio.V1_0.IRadio
 *  android.hardware.radio.V1_0.IRadioIndication
 *  android.hardware.radio.V1_0.IRadioResponse
 *  android.hardware.radio.V1_0.IccIo
 *  android.hardware.radio.V1_0.ImsSmsMessage
 *  android.hardware.radio.V1_0.LceDataInfo
 *  android.hardware.radio.V1_0.NvWriteItem
 *  android.hardware.radio.V1_0.RadioCapability
 *  android.hardware.radio.V1_0.RadioResponseInfo
 *  android.hardware.radio.V1_0.SelectUiccSub
 *  android.hardware.radio.V1_0.SetupDataCallResult
 *  android.hardware.radio.V1_0.SimApdu
 *  android.hardware.radio.V1_0.SmsWriteArgs
 *  android.hardware.radio.V1_0.UusInfo
 *  android.hardware.radio.V1_1.IRadio
 *  android.hardware.radio.V1_1.ImsiEncryptionInfo
 *  android.hardware.radio.V1_1.KeepaliveRequest
 *  android.hardware.radio.V1_1.NetworkScanRequest
 *  android.hardware.radio.V1_1.RadioAccessSpecifier
 *  android.hardware.radio.V1_2.CellInfo
 *  android.hardware.radio.V1_2.IRadio
 *  android.hardware.radio.V1_2.LinkCapacityEstimate
 *  android.hardware.radio.V1_2.NetworkScanRequest
 *  android.hardware.radio.V1_3.IRadio
 *  android.hardware.radio.V1_4.CarrierRestrictionsWithPriority
 *  android.hardware.radio.V1_4.CellInfo
 *  android.hardware.radio.V1_4.DataProfileInfo
 *  android.hardware.radio.V1_4.IRadio
 *  android.hardware.radio.V1_4.SetupDataCallResult
 *  android.net.ConnectivityManager
 *  android.net.KeepalivePacketData
 *  android.net.LinkAddress
 *  android.net.LinkProperties
 *  android.net.NetworkUtils
 *  android.os.AsyncResult
 *  android.os.Build
 *  android.os.Handler
 *  android.os.IHwBinder
 *  android.os.IHwBinder$DeathRecipient
 *  android.os.Message
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.RemoteException
 *  android.os.SystemClock
 *  android.os.SystemProperties
 *  android.os.WorkSource
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.service.carrier.CarrierIdentifier
 *  android.telephony.AccessNetworkConstants
 *  android.telephony.AccessNetworkConstants$AccessNetworkType
 *  android.telephony.CarrierRestrictionRules
 *  android.telephony.CellInfo
 *  android.telephony.ClientRequestStats
 *  android.telephony.ImsiEncryptionInfo
 *  android.telephony.ModemActivityInfo
 *  android.telephony.NetworkScanRequest
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.RadioAccessFamily
 *  android.telephony.RadioAccessSpecifier
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.TelephonyHistogram
 *  android.telephony.data.ApnSetting
 *  android.telephony.data.DataCallResponse
 *  android.telephony.data.DataProfile
 *  android.telephony.emergency.EmergencyNumber
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.SparseArray
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$RIL
 *  com.android.internal.telephony.-$$Lambda$RIL$803u4JiCud_JSoDndvAhT13ZZqU
 *  com.android.internal.telephony.-$$Lambda$RIL$Ir4pOMTf7R0Jtw4O3F7JgMVtXO4
 *  com.android.internal.telephony.-$$Lambda$RIL$ZGWeCQ9boMO1_J1_yQ82l_jK-Nc
 *  com.android.internal.telephony.-$$Lambda$RIL$zYsQZAc3z9bM5fCaq_J0dn5kjjo
 *  com.android.internal.telephony.gsm.SmsBroadcastConfigInfo
 *  com.android.internal.telephony.uicc.IccUtils
 *  com.android.internal.util.Preconditions
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.hardware.radio.V1_0.Carrier;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.CdmaSmsAck;
import android.hardware.radio.V1_0.CdmaSmsAddress;
import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.CdmaSmsSubaddress;
import android.hardware.radio.V1_0.CdmaSmsWriteArgs;
import android.hardware.radio.V1_0.CellInfo;
import android.hardware.radio.V1_0.DataProfileInfo;
import android.hardware.radio.V1_0.Dial;
import android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.GsmSmsMessage;
import android.hardware.radio.V1_0.HardwareConfigModem;
import android.hardware.radio.V1_0.HardwareConfigSim;
import android.hardware.radio.V1_0.IRadioIndication;
import android.hardware.radio.V1_0.IRadioResponse;
import android.hardware.radio.V1_0.IccIo;
import android.hardware.radio.V1_0.ImsSmsMessage;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.NvWriteItem;
import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.V1_0.SelectUiccSub;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_0.SimApdu;
import android.hardware.radio.V1_0.SmsWriteArgs;
import android.hardware.radio.V1_0.UusInfo;
import android.hardware.radio.V1_1.ImsiEncryptionInfo;
import android.hardware.radio.V1_1.KeepaliveRequest;
import android.hardware.radio.V1_2.IRadio;
import android.hardware.radio.V1_2.NetworkScanRequest;
import android.hardware.radio.V1_4.CarrierRestrictionsWithPriority;
import android.hardware.radio.deprecated.V1_0.IOemHook;
import android.hardware.radio.deprecated.V1_0.IOemHookIndication;
import android.hardware.radio.deprecated.V1_0.IOemHookResponse;
import android.net.ConnectivityManager;
import android.net.KeepalivePacketData;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.NetworkUtils;
import android.os.AsyncResult;
import android.os.Build;
import android.os.Handler;
import android.os.IHwBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.WorkSource;
import android.provider.Settings;
import android.service.carrier.CarrierIdentifier;
import android.telephony.AccessNetworkConstants;
import android.telephony.CarrierRestrictionRules;
import android.telephony.ClientRequestStats;
import android.telephony.ModemActivityInfo;
import android.telephony.PhoneNumberUtils;
import android.telephony.RadioAccessFamily;
import android.telephony.RadioAccessSpecifier;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.TelephonyHistogram;
import android.telephony.data.ApnSetting;
import android.telephony.data.DataCallResponse;
import android.telephony.data.DataProfile;
import android.telephony.emergency.EmergencyNumber;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.BaseCommands;
import com.android.internal.telephony.CallForwardInfo;
import com.android.internal.telephony.ClientWakelockTracker;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.DriverCall;
import com.android.internal.telephony.HalVersion;
import com.android.internal.telephony.HardwareConfig;
import com.android.internal.telephony.LinkCapacityEstimate;
import com.android.internal.telephony.OemHookIndication;
import com.android.internal.telephony.OemHookResponse;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.RILRequest;
import com.android.internal.telephony.RadioBugDetector;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.RadioIndication;
import com.android.internal.telephony.RadioResponse;
import com.android.internal.telephony.TelephonyDevController;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony._$$Lambda$RIL$803u4JiCud_JSoDndvAhT13ZZqU;
import com.android.internal.telephony._$$Lambda$RIL$Ir4pOMTf7R0Jtw4O3F7JgMVtXO4;
import com.android.internal.telephony._$$Lambda$RIL$ZGWeCQ9boMO1_J1_yQ82l_jK_Nc;
import com.android.internal.telephony._$$Lambda$RIL$zYsQZAc3z9bM5fCaq_J0dn5kjjo;
import com.android.internal.telephony.cat.ComprehensionTlv;
import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.cdma.CdmaInformationRecords;
import com.android.internal.telephony.cdma.CdmaSmsBroadcastConfigInfo;
import com.android.internal.telephony.gsm.SmsBroadcastConfigInfo;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class RIL
extends BaseCommands
implements CommandsInterface {
    private static final int DEFAULT_ACK_WAKE_LOCK_TIMEOUT_MS = 200;
    private static final int DEFAULT_BLOCKING_MESSAGE_RESPONSE_TIMEOUT_MS = 2000;
    private static final int DEFAULT_WAKE_LOCK_TIMEOUT_MS = 60000;
    static final String EMPTY_ALPHA_LONG = "";
    static final String EMPTY_ALPHA_SHORT = "";
    static final int EVENT_ACK_WAKE_LOCK_TIMEOUT = 4;
    static final int EVENT_BLOCKING_RESPONSE_TIMEOUT = 5;
    static final int EVENT_RADIO_PROXY_DEAD = 6;
    static final int EVENT_WAKE_LOCK_TIMEOUT = 2;
    public static final int FOR_ACK_WAKELOCK = 1;
    public static final int FOR_WAKELOCK = 0;
    static final String[] HIDL_SERVICE_NAME;
    public static final int INVALID_WAKELOCK = -1;
    static final int IRADIO_GET_SERVICE_DELAY_MILLIS = 4000;
    public static final HalVersion RADIO_HAL_VERSION_1_0;
    public static final HalVersion RADIO_HAL_VERSION_1_1;
    public static final HalVersion RADIO_HAL_VERSION_1_2;
    public static final HalVersion RADIO_HAL_VERSION_1_3;
    public static final HalVersion RADIO_HAL_VERSION_1_4;
    public static final HalVersion RADIO_HAL_VERSION_UNKNOWN;
    static final String RILJ_ACK_WAKELOCK_NAME = "RILJ_ACK_WL";
    static final boolean RILJ_LOGD = true;
    static final boolean RILJ_LOGV = false;
    static final String RILJ_LOG_TAG = "RILJ";
    static final String RILJ_WAKELOCK_TAG = "*telephony-radio*";
    static final int RIL_HISTOGRAM_BUCKET_COUNT = 5;
    static SparseArray<TelephonyHistogram> mRilTimeHistograms;
    final PowerManager.WakeLock mAckWakeLock;
    final int mAckWakeLockTimeout;
    volatile int mAckWlSequenceNum = 0;
    private WorkSource mActiveWakelockWorkSource;
    private final ClientWakelockTracker mClientWakelockTracker = new ClientWakelockTracker();
    Set<Integer> mDisabledOemHookServices = new HashSet<Integer>();
    Set<Integer> mDisabledRadioServices = new HashSet<Integer>();
    boolean mIsMobileNetworkSupported;
    Object[] mLastNITZTimeInfo;
    private TelephonyMetrics mMetrics = TelephonyMetrics.getInstance();
    OemHookIndication mOemHookIndication;
    volatile IOemHook mOemHookProxy = null;
    OemHookResponse mOemHookResponse;
    final Integer mPhoneId;
    private WorkSource mRILDefaultWorkSource;
    private RadioBugDetector mRadioBugDetector = null;
    RadioIndication mRadioIndication;
    volatile android.hardware.radio.V1_0.IRadio mRadioProxy = null;
    final AtomicLong mRadioProxyCookie = new AtomicLong(0L);
    final RadioProxyDeathRecipient mRadioProxyDeathRecipient;
    RadioResponse mRadioResponse;
    private HalVersion mRadioVersion = RADIO_HAL_VERSION_UNKNOWN;
    @UnsupportedAppUsage
    SparseArray<RILRequest> mRequestList = new SparseArray();
    final RilHandler mRilHandler;
    @UnsupportedAppUsage
    AtomicBoolean mTestingEmergencyCall = new AtomicBoolean(false);
    @UnsupportedAppUsage
    final PowerManager.WakeLock mWakeLock;
    int mWakeLockCount;
    final int mWakeLockTimeout;
    volatile int mWlSequenceNum = 0;

    static {
        RADIO_HAL_VERSION_UNKNOWN = HalVersion.UNKNOWN;
        RADIO_HAL_VERSION_1_0 = new HalVersion(1, 0);
        RADIO_HAL_VERSION_1_1 = new HalVersion(1, 1);
        RADIO_HAL_VERSION_1_2 = new HalVersion(1, 2);
        RADIO_HAL_VERSION_1_3 = new HalVersion(1, 3);
        RADIO_HAL_VERSION_1_4 = new HalVersion(1, 4);
        mRilTimeHistograms = new SparseArray();
        HIDL_SERVICE_NAME = new String[]{"slot1", "slot2", "slot3"};
    }

    @UnsupportedAppUsage
    public RIL(Context context, int n, int n2) {
        this(context, n, n2, null);
    }

    @UnsupportedAppUsage
    public RIL(Context object, int n, int n2, Integer n3) {
        super((Context)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RIL: init preferredNetworkType=");
        stringBuilder.append(n);
        stringBuilder.append(" cdmaSubscription=");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        this.riljLog(stringBuilder.toString());
        this.mContext = object;
        this.mCdmaSubscription = n2;
        this.mPreferredNetworkType = n;
        this.mPhoneType = 0;
        n = n3 == null ? 0 : n3;
        this.mPhoneId = n;
        if (this.isRadioBugDetectionEnabled()) {
            this.mRadioBugDetector = new RadioBugDetector((Context)object, this.mPhoneId);
        }
        this.mIsMobileNetworkSupported = ((ConnectivityManager)object.getSystemService("connectivity")).isNetworkSupported(0);
        this.mRadioResponse = new RadioResponse(this);
        this.mRadioIndication = new RadioIndication(this);
        this.mOemHookResponse = new OemHookResponse(this);
        this.mOemHookIndication = new OemHookIndication(this);
        this.mRilHandler = new RilHandler();
        this.mRadioProxyDeathRecipient = new RadioProxyDeathRecipient();
        n3 = (PowerManager)object.getSystemService("power");
        this.mWakeLock = n3.newWakeLock(1, RILJ_WAKELOCK_TAG);
        this.mWakeLock.setReferenceCounted(false);
        this.mAckWakeLock = n3.newWakeLock(1, RILJ_ACK_WAKELOCK_NAME);
        this.mAckWakeLock.setReferenceCounted(false);
        this.mWakeLockTimeout = SystemProperties.getInt((String)"ro.ril.wake_lock_timeout", (int)60000);
        this.mAckWakeLockTimeout = SystemProperties.getInt((String)"ro.ril.wake_lock_timeout", (int)200);
        this.mWakeLockCount = 0;
        this.mRILDefaultWorkSource = new WorkSource(object.getApplicationInfo().uid, object.getPackageName());
        this.mActiveWakelockWorkSource = new WorkSource();
        TelephonyDevController.getInstance();
        TelephonyDevController.registerRIL(this);
        this.getRadioProxy(null);
        this.getOemHookProxy(null);
        object = new StringBuilder();
        ((StringBuilder)object).append("Radio HAL version: ");
        ((StringBuilder)object).append(this.mRadioVersion);
        this.riljLog(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void acquireWakeLock(RILRequest rILRequest, int n) {
        synchronized (rILRequest) {
            if (rILRequest.mWakeLockType != -1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to aquire wakelock for ");
                stringBuilder.append(rILRequest.serialString());
                Rlog.d((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
                return;
            }
            if (n != 0) {
                if (n != 1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Acquiring Invalid Wakelock type ");
                    stringBuilder.append(n);
                    Rlog.w((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
                    return;
                }
                PowerManager.WakeLock wakeLock = this.mAckWakeLock;
                synchronized (wakeLock) {
                    this.mAckWakeLock.acquire();
                    ++this.mAckWlSequenceNum;
                    Message message = this.mRilHandler.obtainMessage(4);
                    message.arg1 = this.mAckWlSequenceNum;
                    this.mRilHandler.sendMessageDelayed(message, (long)this.mAckWakeLockTimeout);
                }
            } else {
                PowerManager.WakeLock wakeLock = this.mWakeLock;
                synchronized (wakeLock) {
                    this.mWakeLock.acquire();
                    ++this.mWakeLockCount;
                    ++this.mWlSequenceNum;
                    String string = rILRequest.getWorkSourceClientId();
                    if (!this.mClientWakelockTracker.isClientActive(string)) {
                        this.mActiveWakelockWorkSource.add(rILRequest.mWorkSource);
                        this.mWakeLock.setWorkSource(this.mActiveWakelockWorkSource);
                    }
                    this.mClientWakelockTracker.startTracking(rILRequest.mClientId, rILRequest.mRequest, rILRequest.mSerial, this.mWakeLockCount);
                    string = this.mRilHandler.obtainMessage(2);
                    ((Message)string).arg1 = this.mWlSequenceNum;
                    this.mRilHandler.sendMessageDelayed((Message)string, (long)this.mWakeLockTimeout);
                }
            }
            rILRequest.mWakeLockType = n;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void addRequest(RILRequest rILRequest) {
        this.acquireWakeLock(rILRequest, 0);
        SparseArray<RILRequest> sparseArray = this.mRequestList;
        synchronized (sparseArray) {
            rILRequest.mStartTimeMs = SystemClock.elapsedRealtime();
            this.mRequestList.append(rILRequest.mSerial, (Object)rILRequest);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void addToRilHistogram(RILRequest rILRequest) {
        int n = (int)(SystemClock.elapsedRealtime() - rILRequest.mStartTimeMs);
        SparseArray<TelephonyHistogram> sparseArray = mRilTimeHistograms;
        synchronized (sparseArray) {
            TelephonyHistogram telephonyHistogram;
            TelephonyHistogram telephonyHistogram2 = telephonyHistogram = (TelephonyHistogram)mRilTimeHistograms.get(rILRequest.mRequest);
            if (telephonyHistogram == null) {
                telephonyHistogram2 = new TelephonyHistogram(1, rILRequest.mRequest, 5);
                mRilTimeHistograms.put(rILRequest.mRequest, (Object)telephonyHistogram2);
            }
            telephonyHistogram2.addTimeTaken(n);
            return;
        }
    }

    public static void appendPrimitiveArrayToArrayList(byte[] arrby, ArrayList<Byte> arrayList) {
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(arrby[i]);
        }
    }

    public static byte[] arrayListToPrimitiveArray(ArrayList<Byte> arrayList) {
        byte[] arrby = new byte[arrayList.size()];
        for (int i = 0; i < arrby.length; ++i) {
            arrby[i] = arrayList.get(i);
        }
        return arrby;
    }

    private String censoredTerminalResponse(String charSequence) {
        Object object;
        block8 : {
            Object object2 = IccUtils.hexStringToBytes((String)charSequence);
            object = charSequence;
            if (object2 == null) break block8;
            object = ComprehensionTlv.decodeMany((byte[])object2, 0);
            int n = 0;
            try {
                object2 = object.iterator();
            }
            catch (Exception exception) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Could not censor the terminal response: ");
                ((StringBuilder)charSequence).append(exception);
                Rlog.e((String)RILJ_LOG_TAG, (String)((StringBuilder)charSequence).toString());
                object = null;
            }
            do {
                object = charSequence;
                if (!object2.hasNext()) break;
                ComprehensionTlv comprehensionTlv = (ComprehensionTlv)object2.next();
                object = charSequence;
                if (ComprehensionTlvTag.TEXT_STRING.value() == comprehensionTlv.getTag()) {
                    object = Arrays.copyOfRange(comprehensionTlv.getRawValue(), n, comprehensionTlv.getValueIndex() + comprehensionTlv.getLength());
                    object = ((String)charSequence).toLowerCase().replace(IccUtils.bytesToHexString((byte[])object).toLowerCase(), "********");
                }
                int n2 = comprehensionTlv.getValueIndex();
                n = comprehensionTlv.getLength();
                n = n2 + n;
                charSequence = object;
            } while (true);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void clearRequestList(int n, boolean bl) {
        SparseArray<RILRequest> sparseArray = this.mRequestList;
        synchronized (sparseArray) {
            StringBuilder stringBuilder;
            int n2 = this.mRequestList.size();
            if (bl) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("clearRequestList  mWakeLockCount=");
                stringBuilder.append(this.mWakeLockCount);
                stringBuilder.append(" mRequestList=");
                stringBuilder.append(n2);
                Rlog.d((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
            }
            int n3 = 0;
            do {
                if (n3 >= n2) {
                    this.mRequestList.clear();
                    return;
                }
                RILRequest rILRequest = (RILRequest)this.mRequestList.valueAt(n3);
                if (bl) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(n3);
                    stringBuilder.append(": [");
                    stringBuilder.append(rILRequest.mSerial);
                    stringBuilder.append("] ");
                    stringBuilder.append(RIL.requestToString(rILRequest.mRequest));
                    Rlog.d((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
                }
                rILRequest.onError(n, null);
                this.decrementWakeLock(rILRequest);
                rILRequest.release();
                ++n3;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private boolean clearWakeLock(int n) {
        if (n == 0) {
            PowerManager.WakeLock wakeLock = this.mWakeLock;
            synchronized (wakeLock) {
                if (this.mWakeLockCount == 0 && !this.mWakeLock.isHeld()) {
                    return false;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("NOTE: mWakeLockCount is ");
                stringBuilder.append(this.mWakeLockCount);
                stringBuilder.append("at time of clearing");
                Rlog.d((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
                this.mWakeLockCount = 0;
                this.mWakeLock.release();
                this.mClientWakelockTracker.stopTrackingAll();
                stringBuilder = new WorkSource();
                this.mActiveWakelockWorkSource = stringBuilder;
                return true;
            }
        }
        PowerManager.WakeLock wakeLock = this.mAckWakeLock;
        synchronized (wakeLock) {
            if (!this.mAckWakeLock.isHeld()) {
                return false;
            }
            this.mAckWakeLock.release();
            return true;
        }
    }

    private void constructCdmaSendSmsRilRequest(CdmaSmsMessage object, byte[] cdmaSmsSubaddress) {
        int n;
        DataInputStream dataInputStream;
        int n2;
        boolean bl;
        block13 : {
            dataInputStream = new DataInputStream(new ByteArrayInputStream((byte[])cdmaSmsSubaddress));
            ((CdmaSmsMessage)object).teleserviceId = dataInputStream.readInt();
            n = dataInputStream.readInt();
            boolean bl2 = false;
            bl = n == 1;
            ((CdmaSmsMessage)object).isServicePresent = bl;
            ((CdmaSmsMessage)object).serviceCategory = dataInputStream.readInt();
            object.address.digitMode = dataInputStream.read();
            object.address.numberMode = dataInputStream.read();
            object.address.numberType = dataInputStream.read();
            object.address.numberPlan = dataInputStream.read();
            n2 = dataInputStream.read();
            for (n = 0; n < n2; ++n) {
                object.address.digits.add(dataInputStream.readByte());
                continue;
            }
            object.subAddress.subaddressType = dataInputStream.read();
            cdmaSmsSubaddress = ((CdmaSmsMessage)object).subAddress;
            bl = bl2;
            if ((byte)dataInputStream.read() != 1) break block13;
            bl = true;
        }
        cdmaSmsSubaddress.odd = bl;
        n2 = (byte)dataInputStream.read();
        for (n = 0; n < n2; ++n) {
            object.subAddress.digits.add(dataInputStream.readByte());
            continue;
        }
        n2 = dataInputStream.read();
        for (n = 0; n < n2; ++n) {
            try {
                ((CdmaSmsMessage)object).bearerData.add(dataInputStream.readByte());
                continue;
            }
            catch (IOException iOException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("sendSmsCdma: conversion from input stream to object failed: ");
                ((StringBuilder)object).append(iOException);
                this.riljLog(((StringBuilder)object).toString());
                break;
            }
        }
    }

    private GsmSmsMessage constructGsmSendSmsRilRequest(String string, String string2) {
        GsmSmsMessage gsmSmsMessage = new GsmSmsMessage();
        String string3 = "";
        if (string == null) {
            string = "";
        }
        gsmSmsMessage.smscPdu = string;
        if (string2 == null) {
            string2 = string3;
        }
        gsmSmsMessage.pdu = string2;
        return gsmSmsMessage;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public static DataCallResponse convertDataCallResult(Object arrstring) {
        String string;
        int n;
        int n2;
        CharSequence charSequence;
        Object object;
        int n3;
        int n4;
        int n5;
        void var2_7;
        CharSequence charSequence2;
        int n6;
        Object object2;
        int n7;
        int n8;
        if (arrstring == null) {
            return null;
        }
        Object object3 = null;
        Object var2_2 = null;
        Object object4 = null;
        String[] arrstring2 = null;
        if (arrstring instanceof SetupDataCallResult) {
            void var2_4;
            object = (SetupDataCallResult)arrstring;
            n5 = ((SetupDataCallResult)object).status;
            n4 = ((SetupDataCallResult)object).suggestedRetryTime;
            n7 = ((SetupDataCallResult)object).cid;
            n2 = ((SetupDataCallResult)object).active;
            n8 = ApnSetting.getProtocolIntFromString((String)((SetupDataCallResult)object).type);
            object2 = ((SetupDataCallResult)object).ifname;
            arrstring = object3;
            if (!TextUtils.isEmpty((CharSequence)((SetupDataCallResult)object).addresses)) {
                arrstring = ((SetupDataCallResult)object).addresses.split("\\s+");
            }
            if (!TextUtils.isEmpty((CharSequence)((SetupDataCallResult)object).dnses)) {
                String[] arrstring3 = ((SetupDataCallResult)object).dnses.split("\\s+");
            }
            if (!TextUtils.isEmpty((CharSequence)((SetupDataCallResult)object).gateways)) {
                object4 = ((SetupDataCallResult)object).gateways.split("\\s+");
            }
            if (!TextUtils.isEmpty((CharSequence)((SetupDataCallResult)object).pcscf)) {
                arrstring2 = ((SetupDataCallResult)object).pcscf.split("\\s+");
            }
            n6 = ((SetupDataCallResult)object).mtu;
            object3 = var2_4;
            Object object5 = object2;
        } else {
            if (!(arrstring instanceof android.hardware.radio.V1_4.SetupDataCallResult)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported SetupDataCallResult ");
                stringBuilder.append(arrstring);
                Rlog.e((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
                return null;
            }
            object2 = (android.hardware.radio.V1_4.SetupDataCallResult)arrstring;
            n5 = ((android.hardware.radio.V1_4.SetupDataCallResult)object2).cause;
            n4 = ((android.hardware.radio.V1_4.SetupDataCallResult)object2).suggestedRetryTime;
            n7 = ((android.hardware.radio.V1_4.SetupDataCallResult)object2).cid;
            n2 = ((android.hardware.radio.V1_4.SetupDataCallResult)object2).active;
            n8 = ((android.hardware.radio.V1_4.SetupDataCallResult)object2).type;
            String string2 = ((android.hardware.radio.V1_4.SetupDataCallResult)object2).ifname;
            arrstring = (String[])((android.hardware.radio.V1_4.SetupDataCallResult)object2).addresses.stream().toArray((IntFunction<A[]>)_$$Lambda$RIL$zYsQZAc3z9bM5fCaq_J0dn5kjjo.INSTANCE);
            object3 = (String[])((android.hardware.radio.V1_4.SetupDataCallResult)object2).dnses.stream().toArray((IntFunction<A[]>)_$$Lambda$RIL$Ir4pOMTf7R0Jtw4O3F7JgMVtXO4.INSTANCE);
            object4 = (String[])((android.hardware.radio.V1_4.SetupDataCallResult)object2).gateways.stream().toArray((IntFunction<A[]>)_$$Lambda$RIL$803u4JiCud_JSoDndvAhT13ZZqU.INSTANCE);
            arrstring2 = (String[])((android.hardware.radio.V1_4.SetupDataCallResult)object2).pcscf.stream().toArray((IntFunction<A[]>)_$$Lambda$RIL$ZGWeCQ9boMO1_J1_yQ82l_jK_Nc.INSTANCE);
            n6 = ((android.hardware.radio.V1_4.SetupDataCallResult)object2).mtu;
        }
        object = new ArrayList();
        if (arrstring != null) {
            int n9 = arrstring.length;
            for (n3 = 0; n3 < n9; ++n3) {
                string = arrstring[n3].trim();
                if (string.isEmpty()) continue;
                try {
                    if (string.split("/").length == 2) {
                        object2 = new LinkAddress(string);
                    } else {
                        object2 = NetworkUtils.numericToInetAddress((String)string);
                        n = object2 instanceof Inet4Address ? 32 : 128;
                        object2 = new LinkAddress((InetAddress)object2, n);
                    }
                    object.add(object2);
                    continue;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("Unknown address: ");
                    ((StringBuilder)charSequence2).append(string);
                    Rlog.e((String)RILJ_LOG_TAG, (String)((StringBuilder)charSequence2).toString(), (Throwable)illegalArgumentException);
                }
            }
        }
        object2 = new ArrayList();
        if (object3 != null) {
            n = ((String[])object3).length;
            for (n3 = 0; n3 < n; ++n3) {
                charSequence2 = object3[n3].trim();
                try {
                    object2.add(NetworkUtils.numericToInetAddress((String)charSequence2));
                    continue;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Unknown dns: ");
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    Rlog.e((String)RILJ_LOG_TAG, (String)((StringBuilder)charSequence).toString(), (Throwable)illegalArgumentException);
                }
            }
        }
        object3 = new ArrayList();
        if (object4 != null) {
            n = ((String[])object4).length;
            for (n3 = 0; n3 < n; ++n3) {
                charSequence = object4[n3].trim();
                try {
                    object3.add(NetworkUtils.numericToInetAddress((String)charSequence));
                    continue;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("Unknown gateway: ");
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    Rlog.e((String)RILJ_LOG_TAG, (String)((StringBuilder)charSequence2).toString(), (Throwable)illegalArgumentException);
                }
            }
        }
        object4 = new ArrayList();
        if (arrstring2 != null) {
            n = arrstring2.length;
            for (n3 = 0; n3 < n; ++n3) {
                string = arrstring2[n3].trim();
                try {
                    object4.add(NetworkUtils.numericToInetAddress((String)string));
                    continue;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("Unknown pcscf: ");
                    ((StringBuilder)charSequence2).append(string);
                    Rlog.e((String)RILJ_LOG_TAG, (String)((StringBuilder)charSequence2).toString(), (Throwable)illegalArgumentException);
                }
            }
        }
        return new DataCallResponse(n5, n4, n7, n2, n8, (String)var2_7, (List)object, (List)object2, (List)object3, (List)object4, n6);
    }

    @VisibleForTesting
    public static ArrayList<DataCallResponse> convertDataCallResultList(List<? extends Object> object) {
        ArrayList<DataCallResponse> arrayList = new ArrayList<DataCallResponse>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(RIL.convertDataCallResult(object.next()));
        }
        return arrayList;
    }

    @VisibleForTesting
    public static ArrayList<android.telephony.CellInfo> convertHalCellInfoList(ArrayList<CellInfo> object) {
        ArrayList<android.telephony.CellInfo> arrayList = new ArrayList<android.telephony.CellInfo>(((ArrayList)object).size());
        long l = SystemClock.elapsedRealtimeNanos();
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            CellInfo cellInfo = (CellInfo)object.next();
            cellInfo.timeStamp = l;
            arrayList.add(android.telephony.CellInfo.create((CellInfo)cellInfo));
        }
        return arrayList;
    }

    @VisibleForTesting
    public static ArrayList<android.telephony.CellInfo> convertHalCellInfoList_1_2(ArrayList<android.hardware.radio.V1_2.CellInfo> object) {
        ArrayList<android.telephony.CellInfo> arrayList = new ArrayList<android.telephony.CellInfo>(((ArrayList)object).size());
        long l = SystemClock.elapsedRealtimeNanos();
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            android.hardware.radio.V1_2.CellInfo cellInfo = (android.hardware.radio.V1_2.CellInfo)object.next();
            cellInfo.timeStamp = l;
            arrayList.add(android.telephony.CellInfo.create((android.hardware.radio.V1_2.CellInfo)cellInfo));
        }
        return arrayList;
    }

    @VisibleForTesting
    public static ArrayList<android.telephony.CellInfo> convertHalCellInfoList_1_4(ArrayList<android.hardware.radio.V1_4.CellInfo> object) {
        ArrayList<android.telephony.CellInfo> arrayList = new ArrayList<android.telephony.CellInfo>(((ArrayList)object).size());
        long l = SystemClock.elapsedRealtimeNanos();
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            arrayList.add(android.telephony.CellInfo.create((android.hardware.radio.V1_4.CellInfo)((android.hardware.radio.V1_4.CellInfo)object.next()), (long)l));
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    static ArrayList<HardwareConfig> convertHalHwConfigList(ArrayList<android.hardware.radio.V1_0.HardwareConfig> object, RIL arrayList) {
        arrayList = new ArrayList(((ArrayList)object).size());
        Iterator iterator = ((ArrayList)object).iterator();
        while (iterator.hasNext()) {
            android.hardware.radio.V1_0.HardwareConfig hardwareConfig = (android.hardware.radio.V1_0.HardwareConfig)iterator.next();
            int n = hardwareConfig.type;
            if (n != 0) {
                if (n != 1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("RIL_REQUEST_GET_HARDWARE_CONFIG invalid hardward type:");
                    ((StringBuilder)object).append(n);
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                object = new HardwareConfig(n);
                ((HardwareConfig)object).assignSim(hardwareConfig.uuid, hardwareConfig.state, ((HardwareConfigSim)hardwareConfig.sim.get((int)0)).modemUuid);
            } else {
                object = new HardwareConfig(n);
                HardwareConfigModem hardwareConfigModem = (HardwareConfigModem)hardwareConfig.modem.get(0);
                ((HardwareConfig)object).assignModem(hardwareConfig.uuid, hardwareConfig.state, hardwareConfigModem.rilModel, hardwareConfigModem.rat, hardwareConfigModem.maxVoice, hardwareConfigModem.maxData, hardwareConfigModem.maxStandby);
            }
            arrayList.add((HardwareConfig)object);
        }
        return arrayList;
    }

    static LinkCapacityEstimate convertHalLceData(LceDataInfo object, RIL rIL) {
        int n = ((LceDataInfo)object).lastHopCapacityKbps;
        int n2 = Byte.toUnsignedInt(((LceDataInfo)object).confidenceLevel);
        int n3 = ((LceDataInfo)object).lceSuspended ? 1 : 0;
        LinkCapacityEstimate linkCapacityEstimate = new LinkCapacityEstimate(n, n2, n3);
        object = new StringBuilder();
        ((StringBuilder)object).append("LCE capacity information received:");
        ((StringBuilder)object).append(linkCapacityEstimate);
        rIL.riljLog(((StringBuilder)object).toString());
        return linkCapacityEstimate;
    }

    static LinkCapacityEstimate convertHalLceData(android.hardware.radio.V1_2.LinkCapacityEstimate object, RIL rIL) {
        LinkCapacityEstimate linkCapacityEstimate = new LinkCapacityEstimate(((android.hardware.radio.V1_2.LinkCapacityEstimate)object).downlinkCapacityKbps, ((android.hardware.radio.V1_2.LinkCapacityEstimate)object).uplinkCapacityKbps);
        object = new StringBuilder();
        ((StringBuilder)object).append("LCE capacity information received:");
        ((StringBuilder)object).append(linkCapacityEstimate);
        rIL.riljLog(((StringBuilder)object).toString());
        return linkCapacityEstimate;
    }

    static RadioCapability convertHalRadioCapability(android.hardware.radio.V1_0.RadioCapability radioCapability, RIL rIL) {
        int n = radioCapability.session;
        int n2 = radioCapability.phase;
        int n3 = RIL.convertToNetworkTypeBitMask(radioCapability.raf);
        String string = radioCapability.logicalModemUuid;
        int n4 = radioCapability.status;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("convertHalRadioCapability: session=");
        stringBuilder.append(n);
        stringBuilder.append(", phase=");
        stringBuilder.append(n2);
        stringBuilder.append(", rat=");
        stringBuilder.append(n3);
        stringBuilder.append(", logicModemUuid=");
        stringBuilder.append(string);
        stringBuilder.append(", status=");
        stringBuilder.append(n4);
        stringBuilder.append(", rcRil.raf=");
        stringBuilder.append(radioCapability.raf);
        rIL.riljLog(stringBuilder.toString());
        return new RadioCapability(rIL.mPhoneId, n, n2, n3, string, n4);
    }

    private static String convertNullToEmptyString(String string) {
        if (string == null) {
            string = "";
        }
        return string;
    }

    private android.hardware.radio.V1_1.RadioAccessSpecifier convertRadioAccessSpecifierToRadioHAL(RadioAccessSpecifier arrn) {
        ArrayList arrayList;
        int n;
        android.hardware.radio.V1_1.RadioAccessSpecifier radioAccessSpecifier = new android.hardware.radio.V1_1.RadioAccessSpecifier();
        radioAccessSpecifier.radioAccessNetwork = arrn.getRadioAccessNetwork();
        int n2 = arrn.getRadioAccessNetwork();
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("radioAccessNetwork ");
                    stringBuilder.append(arrn.getRadioAccessNetwork());
                    stringBuilder.append(" not supported!");
                    Log.wtf((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
                    return null;
                }
                arrayList = radioAccessSpecifier.eutranBands;
            } else {
                arrayList = radioAccessSpecifier.utranBands;
            }
        } else {
            arrayList = radioAccessSpecifier.geranBands;
        }
        int[] arrn2 = arrn.getBands();
        int n3 = 0;
        if (arrn2 != null) {
            arrn2 = arrn.getBands();
            n = arrn2.length;
            for (n2 = 0; n2 < n; ++n2) {
                arrayList.add(arrn2[n2]);
            }
        }
        if (arrn.getChannels() != null) {
            arrn = arrn.getChannels();
            n = arrn.length;
            for (n2 = n3; n2 < n; ++n2) {
                n3 = arrn[n2];
                radioAccessSpecifier.channels.add(n3);
            }
        }
        return radioAccessSpecifier;
    }

    private static int convertRanToHalRan(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return 0;
                        }
                        return 5;
                    }
                    return 4;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    private static DataProfileInfo convertToHalDataProfile10(DataProfile dataProfile) {
        DataProfileInfo dataProfileInfo = new DataProfileInfo();
        dataProfileInfo.profileId = dataProfile.getProfileId();
        dataProfileInfo.apn = dataProfile.getApn();
        dataProfileInfo.protocol = ApnSetting.getProtocolStringFromInt((int)dataProfile.getProtocolType());
        dataProfileInfo.roamingProtocol = ApnSetting.getProtocolStringFromInt((int)dataProfile.getRoamingProtocolType());
        dataProfileInfo.authType = dataProfile.getAuthType();
        dataProfileInfo.user = dataProfile.getUserName();
        dataProfileInfo.password = dataProfile.getPassword();
        dataProfileInfo.type = dataProfile.getType();
        dataProfileInfo.maxConnsTime = dataProfile.getMaxConnectionsTime();
        dataProfileInfo.maxConns = dataProfile.getMaxConnections();
        dataProfileInfo.waitTime = dataProfile.getWaitTime();
        dataProfileInfo.enabled = dataProfile.isEnabled();
        dataProfileInfo.supportedApnTypesBitmap = dataProfile.getSupportedApnTypesBitmask();
        dataProfileInfo.bearerBitmap = ServiceState.convertNetworkTypeBitmaskToBearerBitmask((int)dataProfile.getBearerBitmask()) << 1;
        dataProfileInfo.mtu = dataProfile.getMtu();
        dataProfileInfo.mvnoType = 0;
        dataProfileInfo.mvnoMatchData = "";
        return dataProfileInfo;
    }

    private static android.hardware.radio.V1_4.DataProfileInfo convertToHalDataProfile14(DataProfile dataProfile) {
        android.hardware.radio.V1_4.DataProfileInfo dataProfileInfo = new android.hardware.radio.V1_4.DataProfileInfo();
        dataProfileInfo.apn = dataProfile.getApn();
        dataProfileInfo.protocol = dataProfile.getProtocolType();
        dataProfileInfo.roamingProtocol = dataProfile.getRoamingProtocolType();
        dataProfileInfo.authType = dataProfile.getAuthType();
        dataProfileInfo.user = dataProfile.getUserName();
        dataProfileInfo.password = dataProfile.getPassword();
        dataProfileInfo.type = dataProfile.getType();
        dataProfileInfo.maxConnsTime = dataProfile.getMaxConnectionsTime();
        dataProfileInfo.maxConns = dataProfile.getMaxConnections();
        dataProfileInfo.waitTime = dataProfile.getWaitTime();
        dataProfileInfo.enabled = dataProfile.isEnabled();
        dataProfileInfo.supportedApnTypesBitmap = dataProfile.getSupportedApnTypesBitmask();
        dataProfileInfo.bearerBitmap = ServiceState.convertNetworkTypeBitmaskToBearerBitmask((int)dataProfile.getBearerBitmask()) << 1;
        dataProfileInfo.mtu = dataProfile.getMtu();
        dataProfileInfo.persistent = dataProfile.isPersistent();
        dataProfileInfo.preferred = dataProfile.isPreferred();
        int n = dataProfileInfo.persistent ? dataProfile.getProfileId() : -1;
        dataProfileInfo.profileId = n;
        return dataProfileInfo;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int convertToHalMvnoType(String string) {
        int n = string.hashCode();
        if (n != 102338) {
            if (n != 114097) {
                if (n != 3236474) return 0;
                if (!string.equals("imsi")) return 0;
                return 1;
            }
            if (!string.equals("spn")) return 0;
            return 3;
        }
        if (!string.equals("gid")) return 0;
        return 2;
    }

    private static int convertToHalRadioAccessFamily(int n) {
        int n2 = 0;
        if (((long)n & 32768L) != 0L) {
            n2 = 0 | 65536;
        }
        int n3 = n2;
        if (((long)n & 1L) != 0L) {
            n3 = n2 | 2;
        }
        n2 = n3;
        if (((long)n & 2L) != 0L) {
            n2 = n3 | 4;
        }
        n3 = n2;
        if (((long)n & 8L) != 0L) {
            n3 = n2 | 16;
        }
        n2 = n3;
        if (((long)n & 64L) != 0L) {
            n2 = n3 | 64;
        }
        n3 = n2;
        if (((long)n & 16L) != 0L) {
            n3 = n2 | 128;
        }
        n2 = n3;
        if (((long)n & 32L) != 0L) {
            n2 = n3 | 256;
        }
        n3 = n2;
        if (((long)n & 2048L) != 0L) {
            n3 = n2 | 4096;
        }
        n2 = n3;
        if (((long)n & 8192L) != 0L) {
            n2 = n3 | 8192;
        }
        n3 = n2;
        if (((long)n & 256L) != 0L) {
            n3 = n2 | 1024;
        }
        n2 = n3;
        if (((long)n & 128L) != 0L) {
            n2 = n3 | 512;
        }
        n3 = n2;
        if (((long)n & 512L) != 0L) {
            n3 = n2 | 2048;
        }
        n2 = n3;
        if (((long)n & 16384L) != 0L) {
            n2 = n3 | 32768;
        }
        n3 = n2;
        if (((long)n & 4L) != 0L) {
            n3 = n2 | 8;
        }
        n2 = n3;
        if (((long)n & 65536L) != 0L) {
            n2 = n3 | 131072;
        }
        n3 = n2;
        if (((long)n & 4096L) != 0L) {
            n3 = n2 | 16384;
        }
        n2 = n3;
        if (((long)n & 262144L) != 0L) {
            n2 = n3 | 524288;
        }
        n3 = n2;
        if (((long)n & 524288L) != 0L) {
            n3 = n2 | 1048576;
        }
        n = n3 == 0 ? 1 : n3;
        return n;
    }

    private static int convertToHalResetNvType(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return -1;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public static int convertToNetworkTypeBitMask(int n) {
        int n2;
        block20 : {
            int n3 = 0;
            if ((65536 & n) != 0) {
                n3 = (int)((long)0 | 32768L);
            }
            n2 = n3;
            if ((n & 2) != 0) {
                n2 = (int)((long)n3 | 1L);
            }
            n3 = n2;
            if ((n & 4) != 0) {
                n3 = (int)((long)n2 | 2L);
            }
            n2 = n3;
            if ((n & 16) != 0) {
                n2 = (int)((long)n3 | 8L);
            }
            n3 = n2;
            if ((n & 32) != 0) {
                n3 = (int)((long)n2 | 8L);
            }
            n2 = n3;
            if ((n & 64) != 0) {
                n2 = (int)((long)n3 | 64L);
            }
            n3 = n2;
            if ((n & 128) != 0) {
                n3 = (int)((long)n2 | 16L);
            }
            n2 = n3;
            if ((n & 256) != 0) {
                n2 = (int)((long)n3 | 32L);
            }
            n3 = n2;
            if ((n & 4096) != 0) {
                n3 = (int)((long)n2 | 2048L);
            }
            n2 = n3;
            if ((n & 8192) != 0) {
                n2 = (int)((long)n3 | 8192L);
            }
            n3 = n2;
            if ((n & 1024) != 0) {
                n3 = (int)((long)n2 | 256L);
            }
            n2 = n3;
            if ((n & 512) != 0) {
                n2 = (int)((long)n3 | 128L);
            }
            n3 = n2;
            if ((n & 2048) != 0) {
                n3 = (int)((long)n2 | 512L);
            }
            n2 = n3;
            if ((32768 & n) != 0) {
                n2 = (int)((long)n3 | 16384L);
            }
            n3 = n2;
            if ((n & 8) != 0) {
                n3 = (int)((long)n2 | 4L);
            }
            n2 = n3;
            if ((131072 & n) != 0) {
                n2 = (int)((long)n3 | 65536L);
            }
            n3 = n2;
            if ((n & 16384) != 0) {
                n3 = (int)((long)n2 | 4096L);
            }
            n2 = n3;
            if ((524288 & n) != 0) {
                n2 = (int)((long)n3 | 262144L);
            }
            n3 = n2;
            if ((1048576 & n) != 0) {
                n3 = (int)((long)n2 | 524288L);
            }
            n2 = n3;
            if ((262144 & n) != 0) {
                n2 = (int)((long)n3 | 131072L);
            }
            if (n2 != 0) break block20;
            n2 = 0;
        }
        return n2;
    }

    @VisibleForTesting
    public static ArrayList<Carrier> createCarrierRestrictionList(List<CarrierIdentifier> object) {
        ArrayList<Carrier> arrayList = new ArrayList<Carrier>();
        Iterator<CarrierIdentifier> iterator = object.iterator();
        while (iterator.hasNext()) {
            CarrierIdentifier carrierIdentifier = iterator.next();
            Carrier carrier = new Carrier();
            carrier.mcc = RIL.convertNullToEmptyString(carrierIdentifier.getMcc());
            carrier.mnc = RIL.convertNullToEmptyString(carrierIdentifier.getMnc());
            int n = 0;
            object = null;
            if (!TextUtils.isEmpty((CharSequence)carrierIdentifier.getSpn())) {
                n = 1;
                object = carrierIdentifier.getSpn();
            } else if (!TextUtils.isEmpty((CharSequence)carrierIdentifier.getImsi())) {
                n = 2;
                object = carrierIdentifier.getImsi();
            } else if (!TextUtils.isEmpty((CharSequence)carrierIdentifier.getGid1())) {
                n = 3;
                object = carrierIdentifier.getGid1();
            } else if (!TextUtils.isEmpty((CharSequence)carrierIdentifier.getGid2())) {
                n = 4;
                object = carrierIdentifier.getGid2();
            }
            carrier.matchType = n;
            carrier.matchData = RIL.convertNullToEmptyString((String)object);
            arrayList.add(carrier);
        }
        return arrayList;
    }

    private SimApdu createSimApdu(int n, int n2, int n3, int n4, int n5, int n6, String string) {
        SimApdu simApdu = new SimApdu();
        simApdu.sessionId = n;
        simApdu.cla = n2;
        simApdu.instruction = n3;
        simApdu.p1 = n4;
        simApdu.p2 = n5;
        simApdu.p3 = n6;
        simApdu.data = RIL.convertNullToEmptyString(string);
        return simApdu;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void decrementWakeLock(RILRequest rILRequest) {
        synchronized (rILRequest) {
            int n = rILRequest.mWakeLockType;
            if (n != -1) {
                if (n != 0) {
                    if (n != 1) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Decrementing Invalid Wakelock type ");
                        stringBuilder.append(rILRequest.mWakeLockType);
                        Rlog.w((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
                    }
                } else {
                    PowerManager.WakeLock wakeLock = this.mWakeLock;
                    synchronized (wakeLock) {
                        Object object = this.mClientWakelockTracker;
                        String string = rILRequest.mClientId;
                        int n2 = rILRequest.mRequest;
                        int n3 = rILRequest.mSerial;
                        n = this.mWakeLockCount > 1 ? this.mWakeLockCount - 1 : 0;
                        ((ClientWakelockTracker)object).stopTracking(string, n2, n3, n);
                        object = rILRequest.getWorkSourceClientId();
                        if (!this.mClientWakelockTracker.isClientActive((String)object)) {
                            this.mActiveWakelockWorkSource.remove(rILRequest.mWorkSource);
                            this.mWakeLock.setWorkSource(this.mActiveWakelockWorkSource);
                        }
                        if (this.mWakeLockCount > 1) {
                            --this.mWakeLockCount;
                        } else {
                            this.mWakeLockCount = 0;
                            this.mWakeLock.release();
                        }
                    }
                }
            }
            rILRequest.mWakeLockType = -1;
            return;
        }
    }

    private void emergencyDial(String arrayList, EmergencyNumber emergencyNumber, boolean bl, int n, UUSInfo uUSInfo, Message object) {
        block4 : {
            android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
            android.hardware.radio.V1_4.IRadio iRadio2 = (android.hardware.radio.V1_4.IRadio)iRadio;
            if (iRadio == null) break block4;
            object = this.obtainRequest(205, (Message)object, this.mRILDefaultWorkSource);
            iRadio = new Dial();
            iRadio.address = RIL.convertNullToEmptyString((String)((Object)arrayList));
            iRadio.clir = n;
            if (uUSInfo != null) {
                arrayList = new UusInfo();
                ((UusInfo)arrayList).uusType = uUSInfo.getType();
                ((UusInfo)arrayList).uusDcs = uUSInfo.getDcs();
                ((UusInfo)arrayList).uusData = new String(uUSInfo.getUserData());
                iRadio.uusInfo.add(arrayList);
            }
            arrayList = new StringBuilder();
            ((StringBuilder)((Object)arrayList)).append(((RILRequest)object).serialString());
            ((StringBuilder)((Object)arrayList)).append("> ");
            ((StringBuilder)((Object)arrayList)).append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(((StringBuilder)((Object)arrayList)).toString());
            int n2 = ((RILRequest)object).mSerial;
            int n3 = emergencyNumber.getEmergencyServiceCategoryBitmaskInternalDial();
            arrayList = emergencyNumber.getEmergencyUrns() != null ? new ArrayList(emergencyNumber.getEmergencyUrns()) : new ArrayList();
            n = emergencyNumber.getEmergencyCallRouting();
            boolean bl2 = emergencyNumber.getEmergencyNumberSourceBitmask() == 32;
            try {
                iRadio2.emergencyDial(n2, (Dial)iRadio, n3, arrayList, n, bl, bl2);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "emergencyDial", (Exception)throwable);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private RILRequest findAndRemoveRequestFromList(int n) {
        SparseArray<RILRequest> sparseArray = this.mRequestList;
        synchronized (sparseArray) {
            RILRequest rILRequest = (RILRequest)this.mRequestList.get(n);
            if (rILRequest != null) {
                this.mRequestList.remove(n);
            }
            return rILRequest;
        }
    }

    private WorkSource getDeafultWorkSourceIfInvalid(WorkSource workSource) {
        WorkSource workSource2 = workSource;
        if (workSource == null) {
            workSource2 = this.mRILDefaultWorkSource;
        }
        return workSource2;
    }

    @UnsupportedAppUsage
    private static Object getResponseForTimedOutRILRequest(RILRequest rILRequest) {
        if (rILRequest == null) {
            return null;
        }
        Object var1_1 = null;
        rILRequest = rILRequest.mRequest != 135 ? var1_1 : new ModemActivityInfo(0L, 0, 0, new int[5], 0, 0);
        return rILRequest;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List<TelephonyHistogram> getTelephonyRILTimingHistograms() {
        SparseArray<TelephonyHistogram> sparseArray = mRilTimeHistograms;
        synchronized (sparseArray) {
            ArrayList<TelephonyHistogram> arrayList = new ArrayList<TelephonyHistogram>(mRilTimeHistograms.size());
            int n = 0;
            while (n < mRilTimeHistograms.size()) {
                TelephonyHistogram telephonyHistogram = new TelephonyHistogram((TelephonyHistogram)mRilTimeHistograms.valueAt(n));
                arrayList.add(telephonyHistogram);
                ++n;
            }
            return arrayList;
        }
    }

    private void handleRadioProxyExceptionForRR(RILRequest object, String string, Exception exception) {
        object = new StringBuilder();
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(": ");
        ((StringBuilder)object).append(exception);
        this.riljLoge(((StringBuilder)object).toString());
        this.resetProxyAndRequestList();
    }

    private boolean isRadioBugDetectionEnabled() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean bl = false;
        if (Settings.Global.getInt((ContentResolver)contentResolver, (String)"enable_radio_bug_detection", (int)0) != 0) {
            bl = true;
        }
        return bl;
    }

    static /* synthetic */ String[] lambda$convertDataCallResult$0(int n) {
        return new String[n];
    }

    static /* synthetic */ String[] lambda$convertDataCallResult$1(int n) {
        return new String[n];
    }

    static /* synthetic */ String[] lambda$convertDataCallResult$2(int n) {
        return new String[n];
    }

    static /* synthetic */ String[] lambda$convertDataCallResult$3(int n) {
        return new String[n];
    }

    private RILRequest obtainRequest(int n, Message object, WorkSource workSource) {
        object = RILRequest.obtain(n, object, workSource);
        this.addRequest((RILRequest)object);
        return object;
    }

    public static ArrayList<Byte> primitiveArrayToArrayList(byte[] arrby) {
        ArrayList<Byte> arrayList = new ArrayList<Byte>(arrby.length);
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(arrby[i]);
        }
        return arrayList;
    }

    public static ArrayList<Integer> primitiveArrayToArrayList(int[] arrn) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>(arrn.length);
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(arrn[i]);
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    static String requestToString(int n) {
        if (n != 800) {
            switch (n) {
                default: {
                    switch (n) {
                        default: {
                            switch (n) {
                                default: {
                                    return "<unknown request>";
                                }
                                case 203: {
                                    return "RIL_REQUEST_SET_LINK_CAPACITY_REPORTING_CRITERIA";
                                }
                                case 202: {
                                    return "RIL_REQUEST_SET_SIGNAL_STRENGTH_REPORTING_CRITERIA";
                                }
                                case 201: {
                                    return "RIL_REQUEST_SET_LOGICAL_TO_PHYSICAL_SLOT_MAPPING";
                                }
                                case 200: 
                            }
                            return "RIL_REQUEST_GET_SLOT_STATUS";
                        }
                        case 147: {
                            return "RIL_REQUEST_GET_MODEM_STATUS";
                        }
                        case 146: {
                            return "RIL_REQUEST_ENABLE_MODEM";
                        }
                        case 145: {
                            return "RIL_REQUEST_STOP_KEEPALIVE";
                        }
                        case 144: {
                            return "RIL_REQUEST_START_KEEPALIVE";
                        }
                        case 143: {
                            return "RIL_REQUEST_STOP_NETWORK_SCAN";
                        }
                        case 142: {
                            return "RIL_REQUEST_START_NETWORK_SCAN";
                        }
                        case 141: {
                            return "RIL_REQUEST_SET_CARRIER_INFO_IMSI_ENCRYPTION";
                        }
                        case 140: {
                            return "RIL_REQUEST_SET_SIM_CARD_POWER";
                        }
                        case 139: {
                            return "RIL_REQUEST_SET_UNSOLICITED_RESPONSE_FILTER";
                        }
                        case 138: {
                            return "RIL_REQUEST_SEND_DEVICE_STATE";
                        }
                        case 137: {
                            return "RIL_REQUEST_GET_ALLOWED_CARRIERS";
                        }
                        case 136: {
                            return "RIL_REQUEST_SET_ALLOWED_CARRIERS";
                        }
                        case 135: {
                            return "RIL_REQUEST_GET_ACTIVITY_INFO";
                        }
                        case 134: {
                            return "RIL_REQUEST_PULL_LCEDATA";
                        }
                        case 133: {
                            return "RIL_REQUEST_STOP_LCE";
                        }
                        case 132: {
                            return "RIL_REQUEST_START_LCE";
                        }
                        case 131: {
                            return "RIL_REQUEST_SET_RADIO_CAPABILITY";
                        }
                        case 130: {
                            return "RIL_REQUEST_GET_RADIO_CAPABILITY";
                        }
                        case 129: {
                            return "RIL_REQUEST_SHUTDOWN";
                        }
                        case 128: 
                    }
                    return "RIL_REQUEST_SET_DATA_PROFILE";
                }
                case 125: {
                    return "RIL_REQUEST_SIM_AUTHENTICATION";
                }
                case 124: {
                    return "GET_HARDWARE_CONFIG";
                }
                case 123: {
                    return "RIL_REQUEST_ALLOW_DATA";
                }
                case 122: {
                    return "RIL_REQUEST_SET_UICC_SUBSCRIPTION";
                }
                case 121: {
                    return "RIL_REQUEST_NV_RESET_CONFIG";
                }
                case 120: {
                    return "RIL_REQUEST_NV_WRITE_CDMA_PRL";
                }
                case 119: {
                    return "RIL_REQUEST_NV_WRITE_ITEM";
                }
                case 118: {
                    return "RIL_REQUEST_NV_READ_ITEM";
                }
                case 117: {
                    return "RIL_REQUEST_SIM_TRANSMIT_APDU_CHANNEL";
                }
                case 116: {
                    return "RIL_REQUEST_SIM_CLOSE_CHANNEL";
                }
                case 115: {
                    return "RIL_REQUEST_SIM_OPEN_CHANNEL";
                }
                case 114: {
                    return "RIL_REQUEST_SIM_TRANSMIT_APDU_BASIC";
                }
                case 113: {
                    return "RIL_REQUEST_IMS_SEND_SMS";
                }
                case 112: {
                    return "RIL_REQUEST_IMS_REGISTRATION_STATE";
                }
                case 111: {
                    return "RIL_REQUEST_SET_INITIAL_ATTACH_APN";
                }
                case 110: {
                    return "RIL_REQUEST_SET_CELL_INFO_LIST_RATE";
                }
                case 109: {
                    return "RIL_REQUEST_GET_CELL_INFO_LIST";
                }
                case 108: {
                    return "RIL_REQUEST_VOICE_RADIO_TECH";
                }
                case 107: {
                    return "RIL_REQUEST_STK_SEND_ENVELOPE_WITH_STATUS";
                }
                case 106: {
                    return "RIL_REQUEST_ACKNOWLEDGE_INCOMING_GSM_SMS_WITH_PDU";
                }
                case 105: {
                    return "RIL_REQUEST_ISIM_AUTHENTICATION";
                }
                case 104: {
                    return "RIL_REQUEST_CDMA_GET_SUBSCRIPTION_SOURCE";
                }
                case 103: {
                    return "RIL_REQUEST_REPORT_STK_SERVICE_IS_RUNNING";
                }
                case 102: {
                    return "RIL_REQUEST_REPORT_SMS_MEMORY_STATUS";
                }
                case 101: {
                    return "RIL_REQUEST_SET_SMSC_ADDRESS";
                }
                case 100: {
                    return "RIL_REQUEST_GET_SMSC_ADDRESS";
                }
                case 99: {
                    return "REQUEST_EXIT_EMERGENCY_CALLBACK_MODE";
                }
                case 98: {
                    return "RIL_REQUEST_DEVICE_IDENTITY";
                }
                case 97: {
                    return "RIL_REQUEST_CDMA_DELETE_SMS_ON_RUIM";
                }
                case 96: {
                    return "RIL_REQUEST_CDMA_WRITE_SMS_TO_RUIM";
                }
                case 95: {
                    return "RIL_REQUEST_CDMA_SUBSCRIPTION";
                }
                case 94: {
                    return "RIL_REQUEST_CDMA_BROADCAST_ACTIVATION";
                }
                case 93: {
                    return "RIL_REQUEST_CDMA_SET_BROADCAST_CONFIG";
                }
                case 92: {
                    return "RIL_REQUEST_CDMA_GET_BROADCAST_CONFIG";
                }
                case 91: {
                    return "RIL_REQUEST_GSM_BROADCAST_ACTIVATION";
                }
                case 90: {
                    return "RIL_REQUEST_GSM_SET_BROADCAST_CONFIG";
                }
                case 89: {
                    return "RIL_REQUEST_GSM_GET_BROADCAST_CONFIG";
                }
                case 88: {
                    return "RIL_REQUEST_CDMA_SMS_ACKNOWLEDGE";
                }
                case 87: {
                    return "RIL_REQUEST_CDMA_SEND_SMS";
                }
                case 86: {
                    return "RIL_REQUEST_CDMA_VALIDATE_AND_WRITE_AKEY";
                }
                case 85: {
                    return "RIL_REQUEST_CDMA_BURST_DTMF";
                }
                case 84: {
                    return "RIL_REQUEST_CDMA_FLASH";
                }
                case 83: {
                    return "RIL_REQUEST_CDMA_QUERY_PREFERRED_VOICE_PRIVACY_MODE";
                }
                case 82: {
                    return "RIL_REQUEST_CDMA_SET_PREFERRED_VOICE_PRIVACY_MODE";
                }
                case 81: {
                    return "RIL_REQUEST_QUERY_TTY_MODE";
                }
                case 80: {
                    return "RIL_REQUEST_SET_TTY_MODE";
                }
                case 79: {
                    return "RIL_REQUEST_CDMA_QUERY_ROAMING_PREFERENCE";
                }
                case 78: {
                    return "RIL_REQUEST_CDMA_SET_ROAMING_PREFERENCE";
                }
                case 77: {
                    return "RIL_REQUEST_CDMA_SET_SUBSCRIPTION_SOURCE";
                }
                case 76: {
                    return "REQUEST_SET_LOCATION_UPDATES";
                }
                case 75: {
                    return "REQUEST_GET_NEIGHBORING_CELL_IDS";
                }
                case 74: {
                    return "REQUEST_GET_PREFERRED_NETWORK_TYPE";
                }
                case 73: {
                    return "REQUEST_SET_PREFERRED_NETWORK_TYPE";
                }
                case 72: {
                    return "REQUEST_EXPLICIT_CALL_TRANSFER";
                }
                case 71: {
                    return "REQUEST_STK_HANDLE_CALL_SETUP_REQUESTED_FROM_SIM";
                }
                case 70: {
                    return "REQUEST_STK_SEND_TERMINAL_RESPONSE";
                }
                case 69: {
                    return "REQUEST_STK_SEND_ENVELOPE_COMMAND";
                }
                case 68: {
                    return "REQUEST_STK_SET_PROFILE";
                }
                case 67: {
                    return "REQUEST_STK_GET_PROFILE";
                }
                case 66: {
                    return "QUERY_AVAILABLE_BAND_MODE";
                }
                case 65: {
                    return "SET_BAND_MODE";
                }
                case 64: {
                    return "DELETE_SMS_ON_SIM";
                }
                case 63: {
                    return "WRITE_SMS_TO_SIM";
                }
                case 62: {
                    return "SET_SUPP_SVC_NOTIFICATION";
                }
                case 61: {
                    return "SCREEN_STATE";
                }
                case 60: {
                    return "OEM_HOOK_STRINGS";
                }
                case 59: {
                    return "OEM_HOOK_RAW";
                }
                case 58: {
                    return "RESET_RADIO";
                }
                case 57: {
                    return "DATA_CALL_LIST";
                }
                case 56: {
                    return "LAST_DATA_CALL_FAIL_CAUSE";
                }
                case 55: {
                    return "QUERY_CLIP";
                }
                case 54: {
                    return "GET_MUTE";
                }
                case 53: {
                    return "SET_MUTE";
                }
                case 52: {
                    return "SEPARATE_CONNECTION";
                }
                case 51: {
                    return "BASEBAND_VERSION";
                }
                case 50: {
                    return "DTMF_STOP";
                }
                case 49: {
                    return "DTMF_START";
                }
                case 48: {
                    return "QUERY_AVAILABLE_NETWORKS ";
                }
                case 47: {
                    return "SET_NETWORK_SELECTION_MANUAL";
                }
                case 46: {
                    return "SET_NETWORK_SELECTION_AUTOMATIC";
                }
                case 45: {
                    return "QUERY_NETWORK_SELECTION_MODE";
                }
                case 44: {
                    return "CHANGE_BARRING_PASSWORD";
                }
                case 43: {
                    return "SET_FACILITY_LOCK";
                }
                case 42: {
                    return "QUERY_FACILITY_LOCK";
                }
                case 41: {
                    return "DEACTIVATE_DATA_CALL";
                }
                case 40: {
                    return "ANSWER";
                }
                case 39: {
                    return "GET_IMEISV";
                }
                case 38: {
                    return "GET_IMEI";
                }
                case 37: {
                    return "SMS_ACKNOWLEDGE";
                }
                case 36: {
                    return "SET_CALL_WAITING";
                }
                case 35: {
                    return "QUERY_CALL_WAITING";
                }
                case 34: {
                    return "SET_CALL_FORWARD";
                }
                case 33: {
                    return "QUERY_CALL_FORWARD_STATUS";
                }
                case 32: {
                    return "SET_CLIR";
                }
                case 31: {
                    return "GET_CLIR";
                }
                case 30: {
                    return "CANCEL_USSD";
                }
                case 29: {
                    return "SEND_USSD";
                }
                case 28: {
                    return "SIM_IO";
                }
                case 27: {
                    return "SETUP_DATA_CALL";
                }
                case 26: {
                    return "SEND_SMS_EXPECT_MORE";
                }
                case 25: {
                    return "SEND_SMS";
                }
                case 24: {
                    return "DTMF";
                }
                case 23: {
                    return "RADIO_POWER";
                }
                case 22: {
                    return "OPERATOR";
                }
                case 21: {
                    return "DATA_REGISTRATION_STATE";
                }
                case 20: {
                    return "VOICE_REGISTRATION_STATE";
                }
                case 19: {
                    return "SIGNAL_STRENGTH";
                }
                case 18: {
                    return "LAST_CALL_FAIL_CAUSE";
                }
                case 17: {
                    return "UDUB";
                }
                case 16: {
                    return "CONFERENCE";
                }
                case 15: {
                    return "REQUEST_SWITCH_WAITING_OR_HOLDING_AND_ACTIVE";
                }
                case 14: {
                    return "HANGUP_FOREGROUND_RESUME_BACKGROUND";
                }
                case 13: {
                    return "HANGUP_WAITING_OR_BACKGROUND";
                }
                case 12: {
                    return "HANGUP";
                }
                case 11: {
                    return "GET_IMSI";
                }
                case 10: {
                    return "DIAL";
                }
                case 9: {
                    return "GET_CURRENT_CALLS";
                }
                case 8: {
                    return "ENTER_NETWORK_DEPERSONALIZATION";
                }
                case 7: {
                    return "CHANGE_SIM_PIN2";
                }
                case 6: {
                    return "CHANGE_SIM_PIN";
                }
                case 5: {
                    return "ENTER_SIM_PUK2";
                }
                case 4: {
                    return "ENTER_SIM_PIN2";
                }
                case 3: {
                    return "ENTER_SIM_PUK";
                }
                case 2: {
                    return "ENTER_SIM_PIN";
                }
                case 1: 
            }
            return "GET_SIM_STATUS";
        }
        return "RIL_RESPONSE_ACKNOWLEDGEMENT";
    }

    private void resetProxyAndRequestList() {
        this.mRadioProxy = null;
        this.mOemHookProxy = null;
        this.mRadioProxyCookie.incrementAndGet();
        this.setRadioState(2, true);
        RILRequest.resetSerial();
        this.clearRequestList(1, false);
        this.getRadioProxy(null);
        this.getOemHookProxy(null);
    }

    @UnsupportedAppUsage
    static String responseToString(int n) {
        if (n != 1100) {
            if (n != 1101) {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                return "<unknown response>";
                            }
                            case 1050: {
                                return "RIL_UNSOL_KEEPALIVE_STATUS";
                            }
                            case 1049: {
                                return "RIL_UNSOL_NETWORK_SCAN_RESULT";
                            }
                            case 1048: {
                                return "RIL_UNSOL_CARRIER_INFO_IMSI_ENCRYPTION";
                            }
                            case 1047: {
                                return "UNSOL_MODEM_RESTART";
                            }
                            case 1046: {
                                return "UNSOL_PCO_DATA";
                            }
                            case 1045: {
                                return "UNSOL_LCE_INFO_RECV";
                            }
                            case 1044: {
                                return "UNSOL_STK_CC_ALPHA_NOTIFY";
                            }
                            case 1043: {
                                return "UNSOL_ON_SS";
                            }
                            case 1042: 
                        }
                        return "RIL_UNSOL_RADIO_CAPABILITY";
                    }
                    case 1040: {
                        return "RIL_UNSOL_HARDWARE_CONFIG_CHANGED";
                    }
                    case 1039: {
                        return "UNSOL_SRVCC_STATE_NOTIFY";
                    }
                    case 1038: {
                        return "RIL_UNSOL_UICC_SUBSCRIPTION_STATUS_CHANGED";
                    }
                    case 1037: {
                        return "UNSOL_RESPONSE_IMS_NETWORK_STATE_CHANGED";
                    }
                    case 1036: {
                        return "UNSOL_CELL_INFO_LIST";
                    }
                    case 1035: {
                        return "UNSOL_VOICE_RADIO_TECH_CHANGED";
                    }
                    case 1034: {
                        return "UNSOL_RIL_CONNECTED";
                    }
                    case 1033: {
                        return "UNSOL_EXIT_EMERGENCY_CALLBACK_MODE";
                    }
                    case 1032: {
                        return "UNSOL_CDMA_PRL_CHANGED";
                    }
                    case 1031: {
                        return "CDMA_SUBSCRIPTION_SOURCE_CHANGED";
                    }
                    case 1030: {
                        return "UNSOL_RESEND_INCALL_MUTE";
                    }
                    case 1029: {
                        return "UNSOL_RINGBACK_TONE";
                    }
                    case 1028: {
                        return "UNSOL_OEM_HOOK_RAW";
                    }
                    case 1027: {
                        return "UNSOL_CDMA_INFO_REC";
                    }
                    case 1026: {
                        return "UNSOL_CDMA_OTA_PROVISION_STATUS";
                    }
                    case 1025: {
                        return "UNSOL_CDMA_CALL_WAITING";
                    }
                    case 1024: {
                        return "UNSOL_ENTER_EMERGENCY_CALLBACK_MODE";
                    }
                    case 1023: {
                        return "UNSOL_RESTRICTED_STATE_CHANGED";
                    }
                    case 1022: {
                        return "UNSOL_CDMA_RUIM_SMS_STORAGE_FULL";
                    }
                    case 1021: {
                        return "UNSOL_RESPONSE_NEW_BROADCAST_SMS";
                    }
                    case 1020: {
                        return "UNSOL_RESPONSE_CDMA_NEW_SMS";
                    }
                    case 1019: {
                        return "UNSOL_RESPONSE_SIM_STATUS_CHANGED";
                    }
                    case 1018: {
                        return "UNSOL_CALL_RING";
                    }
                    case 1017: {
                        return "UNSOL_SIM_REFRESH";
                    }
                    case 1016: {
                        return "UNSOL_SIM_SMS_STORAGE_FULL";
                    }
                    case 1015: {
                        return "UNSOL_STK_CALL_SETUP";
                    }
                    case 1014: {
                        return "UNSOL_STK_EVENT_NOTIFY";
                    }
                    case 1013: {
                        return "UNSOL_STK_PROACTIVE_COMMAND";
                    }
                    case 1012: {
                        return "UNSOL_STK_SESSION_END";
                    }
                    case 1011: {
                        return "UNSOL_SUPP_SVC_NOTIFICATION";
                    }
                    case 1010: {
                        return "UNSOL_DATA_CALL_LIST_CHANGED";
                    }
                    case 1009: {
                        return "UNSOL_SIGNAL_STRENGTH";
                    }
                    case 1008: {
                        return "UNSOL_NITZ_TIME_RECEIVED";
                    }
                    case 1007: {
                        return "UNSOL_ON_USSD_REQUEST";
                    }
                    case 1006: {
                        return "UNSOL_ON_USSD";
                    }
                    case 1005: {
                        return "UNSOL_RESPONSE_NEW_SMS_ON_SIM";
                    }
                    case 1004: {
                        return "UNSOL_RESPONSE_NEW_SMS_STATUS_REPORT";
                    }
                    case 1003: {
                        return "UNSOL_RESPONSE_NEW_SMS";
                    }
                    case 1002: {
                        return "UNSOL_RESPONSE_NETWORK_STATE_CHANGED";
                    }
                    case 1001: {
                        return "UNSOL_RESPONSE_CALL_STATE_CHANGED";
                    }
                    case 1000: 
                }
                return "UNSOL_RESPONSE_RADIO_STATE_CHANGED";
            }
            return "RIL_UNSOL_PHYSICAL_CHANNEL_CONFIG";
        }
        return "RIL_UNSOL_ICC_SLOT_STATUS";
    }

    @UnsupportedAppUsage
    static String retToString(int n, Object object) {
        if (object == null) {
            return "";
        }
        if (n != 11 && n != 115 && n != 117 && n != 38 && n != 39) {
            if (object instanceof int[]) {
                int[] arrn = (int[])object;
                int n2 = arrn.length;
                object = new StringBuilder("{");
                if (n2 > 0) {
                    ((StringBuilder)object).append(arrn[0]);
                    for (n = 0 + 1; n < n2; ++n) {
                        ((StringBuilder)object).append(", ");
                        ((StringBuilder)object).append(arrn[n]);
                    }
                }
                ((StringBuilder)object).append("}");
                object = ((StringBuilder)object).toString();
            } else if (object instanceof String[]) {
                object = (String[])object;
                int n3 = ((String[])object).length;
                StringBuilder stringBuilder = new StringBuilder("{");
                if (n3 > 0) {
                    if (n == 98) {
                        n = 0 + 1;
                        stringBuilder.append(Rlog.pii((String)RILJ_LOG_TAG, (Object)object[0]));
                    } else {
                        n = 0 + 1;
                        stringBuilder.append((String)object[0]);
                    }
                    while (n < n3) {
                        stringBuilder.append(", ");
                        stringBuilder.append((String)object[n]);
                        ++n;
                    }
                }
                stringBuilder.append("}");
                object = stringBuilder.toString();
            } else if (n == 9) {
                Object object2 = (ArrayList)object;
                object = new StringBuilder("{");
                object2 = ((ArrayList)object2).iterator();
                while (object2.hasNext()) {
                    DriverCall driverCall = (DriverCall)object2.next();
                    ((StringBuilder)object).append("[");
                    ((StringBuilder)object).append(driverCall);
                    ((StringBuilder)object).append("] ");
                }
                ((StringBuilder)object).append("}");
                object = ((StringBuilder)object).toString();
            } else if (n == 75) {
                ArrayList arrayList2 = (ArrayList)object;
                object = new StringBuilder("{");
                for (ArrayList arrayList2 : arrayList2) {
                    ((StringBuilder)object).append("[");
                    ((StringBuilder)object).append(arrayList2);
                    ((StringBuilder)object).append("] ");
                }
                ((StringBuilder)object).append("}");
                object = ((StringBuilder)object).toString();
            } else if (n == 33) {
                CallForwardInfo[] arrcallForwardInfo = (CallForwardInfo[])object;
                int n4 = arrcallForwardInfo.length;
                object = new StringBuilder("{");
                for (n = 0; n < n4; ++n) {
                    ((StringBuilder)object).append("[");
                    ((StringBuilder)object).append(arrcallForwardInfo[n]);
                    ((StringBuilder)object).append("] ");
                }
                ((StringBuilder)object).append("}");
                object = ((StringBuilder)object).toString();
            } else if (n == 124) {
                Object object3 = (ArrayList)object;
                object = new StringBuilder(" ");
                object3 = ((ArrayList)object3).iterator();
                while (object3.hasNext()) {
                    HardwareConfig hardwareConfig = (HardwareConfig)object3.next();
                    ((StringBuilder)object).append("[");
                    ((StringBuilder)object).append(hardwareConfig);
                    ((StringBuilder)object).append("] ");
                }
                object = ((StringBuilder)object).toString();
            } else {
                object = object.toString();
            }
            return object;
        }
        return "";
    }

    private void sendAck() {
        RILRequest rILRequest = RILRequest.obtain(800, null, this.mRILDefaultWorkSource);
        this.acquireWakeLock(rILRequest, 1);
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy(null);
        if (iRadio != null) {
            try {
                iRadio.responseAcknowledgement();
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR(rILRequest, "sendAck", (Exception)throwable);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sendAck: ");
                stringBuilder.append(throwable);
                this.riljLoge(stringBuilder.toString());
            }
        } else {
            Rlog.e((String)RILJ_LOG_TAG, (String)"Error trying to send ack, radioProxy = null");
        }
        rILRequest.release();
    }

    private int translateStatus(int n) {
        if ((n &= 7) != 1) {
            if (n != 3) {
                if (n != 5) {
                    if (n != 7) {
                        return 1;
                    }
                    return 2;
                }
                return 3;
            }
            return 0;
        }
        return 1;
    }

    @Override
    public void acceptCall(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(40, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.acceptCall(((RILRequest)object).mSerial);
                this.mMetrics.writeRilAnswer(this.mPhoneId, ((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "acceptCall", (Exception)throwable);
            }
        }
    }

    @Override
    public void acknowledgeIncomingGsmSmsWithPdu(boolean bl, String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(106, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" success = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.acknowledgeIncomingGsmSmsWithPdu(((RILRequest)object).mSerial, bl, RIL.convertNullToEmptyString(string));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "acknowledgeIncomingGsmSmsWithPdu", (Exception)throwable);
            }
        }
    }

    @Override
    public void acknowledgeLastIncomingCdmaSms(boolean bl, int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(88, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" success = ");
            stringBuilder.append(bl);
            stringBuilder.append(" cause = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            stringBuilder = new CdmaSmsAck();
            ((CdmaSmsAck)stringBuilder).errorClass = bl ^ true;
            ((CdmaSmsAck)stringBuilder).smsCauseCode = n;
            try {
                iRadio.acknowledgeLastIncomingCdmaSms(((RILRequest)object).mSerial, (CdmaSmsAck)stringBuilder);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "acknowledgeLastIncomingCdmaSms", (Exception)throwable);
            }
        }
    }

    @Override
    public void acknowledgeLastIncomingGsmSms(boolean bl, int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(37, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" success = ");
            stringBuilder.append(bl);
            stringBuilder.append(" cause = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.acknowledgeLastIncomingGsmSms(((RILRequest)object).mSerial, bl, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "acknowledgeLastIncomingGsmSms", (Exception)throwable);
            }
        }
    }

    @Override
    public void cancelPendingUssd(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(30, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.cancelPendingUssd(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "cancelPendingUssd", (Exception)throwable);
            }
        }
    }

    @Override
    public void changeBarringPassword(String string, String string2, String string3, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(44, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append("facility = ");
            stringBuilder.append(string);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setBarringPassword(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), RIL.convertNullToEmptyString(string2), RIL.convertNullToEmptyString(string3));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "changeBarringPassword", (Exception)throwable);
            }
        }
    }

    @Override
    public void changeIccPin(String string, String string2, Message message) {
        this.changeIccPinForApp(string, string2, null, message);
    }

    @Override
    public void changeIccPin2(String string, String string2, Message message) {
        this.changeIccPin2ForApp(string, string2, null, message);
    }

    @Override
    public void changeIccPin2ForApp(String string, String string2, String string3, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(7, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" oldPin = ");
            stringBuilder.append(string);
            stringBuilder.append(" newPin = ");
            stringBuilder.append(string2);
            stringBuilder.append(" aid = ");
            stringBuilder.append(string3);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.changeIccPin2ForApp(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), RIL.convertNullToEmptyString(string2), RIL.convertNullToEmptyString(string3));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "changeIccPin2ForApp", (Exception)throwable);
            }
        }
    }

    @Override
    public void changeIccPinForApp(String string, String string2, String string3, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(6, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" oldPin = ");
            stringBuilder.append(string);
            stringBuilder.append(" newPin = ");
            stringBuilder.append(string2);
            stringBuilder.append(" aid = ");
            stringBuilder.append(string3);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.changeIccPinForApp(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), RIL.convertNullToEmptyString(string2), RIL.convertNullToEmptyString(string3));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "changeIccPinForApp", (Exception)throwable);
            }
        }
    }

    @Override
    public void conference(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(16, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.conference(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "conference", (Exception)throwable);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void deactivateDataCall(int n, int n2, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio == null) return;
        object = this.obtainRequest(41, (Message)object, this.mRILDefaultWorkSource);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((RILRequest)object).serialString());
        stringBuilder.append("> ");
        stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
        stringBuilder.append(" cid = ");
        stringBuilder.append(n);
        stringBuilder.append(" reason = ");
        stringBuilder.append(n2);
        this.riljLog(stringBuilder.toString());
        try {
            if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_2)) {
                ((IRadio)iRadio).deactivateDataCall_1_2(((RILRequest)object).mSerial, n, n2);
            } else {
                int n3 = ((RILRequest)object).mSerial;
                boolean bl = n2 == 2;
                iRadio.deactivateDataCall(n3, n, bl);
            }
            this.mMetrics.writeRilDeactivateDataCall(this.mPhoneId, ((RILRequest)object).mSerial, n, n2);
            return;
        }
        catch (RemoteException | RuntimeException throwable) {
            this.handleRadioProxyExceptionForRR((RILRequest)object, "deactivateDataCall", (Exception)throwable);
        }
    }

    @Override
    public void deleteSmsOnRuim(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(97, (Message)object, this.mRILDefaultWorkSource);
            try {
                iRadio.deleteSmsOnRuim(object.mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "deleteSmsOnRuim", (Exception)throwable);
            }
        }
    }

    @Override
    public void deleteSmsOnSim(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(64, (Message)object, this.mRILDefaultWorkSource);
            try {
                iRadio.deleteSmsOnSim(object.mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "deleteSmsOnSim", (Exception)throwable);
            }
        }
    }

    @Override
    public void dial(String string, boolean bl, EmergencyNumber emergencyNumber, boolean bl2, int n, Message message) {
        this.dial(string, bl, emergencyNumber, bl2, n, null, message);
    }

    @Override
    public void dial(String charSequence, boolean bl, EmergencyNumber emergencyNumber, boolean bl2, int n, UUSInfo uUSInfo, Message object) {
        if (bl && this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_4) && emergencyNumber != null) {
            this.emergencyDial((String)charSequence, emergencyNumber, bl2, n, uUSInfo, (Message)object);
            return;
        }
        emergencyNumber = this.getRadioProxy((Message)object);
        if (emergencyNumber != null) {
            object = this.obtainRequest(10, (Message)object, this.mRILDefaultWorkSource);
            Dial dial = new Dial();
            dial.address = RIL.convertNullToEmptyString((String)charSequence);
            dial.clir = n;
            if (uUSInfo != null) {
                charSequence = new UusInfo();
                ((UusInfo)charSequence).uusType = uUSInfo.getType();
                ((UusInfo)charSequence).uusDcs = uUSInfo.getDcs();
                ((UusInfo)charSequence).uusData = new String(uUSInfo.getUserData());
                dial.uusInfo.add(charSequence);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(((RILRequest)object).serialString());
            ((StringBuilder)charSequence).append("> ");
            ((StringBuilder)charSequence).append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(((StringBuilder)charSequence).toString());
            try {
                emergencyNumber.dial(((RILRequest)object).mSerial, dial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "dial", (Exception)throwable);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] object2) {
        object = new StringBuilder();
        ((StringBuilder)object).append("RIL: ");
        ((StringBuilder)object).append(this);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mWakeLock=");
        ((StringBuilder)object).append((Object)this.mWakeLock);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mWakeLockTimeout=");
        ((StringBuilder)object).append(this.mWakeLockTimeout);
        printWriter.println(((StringBuilder)object).toString());
        object = this.mRequestList;
        synchronized (object) {
            StringBuilder stringBuilder;
            object2 = this.mWakeLock;
            synchronized (object2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(" mWakeLockCount=");
                stringBuilder.append(this.mWakeLockCount);
                printWriter.println(stringBuilder.toString());
            }
            int n = this.mRequestList.size();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" mRequestList count=");
            ((StringBuilder)object2).append(n);
            printWriter.println(((StringBuilder)object2).toString());
            int n2 = 0;
            do {
                if (n2 >= n) {
                    // MONITOREXIT [6, 8, 9] lbl40 : MonitorExitStatement: MONITOREXIT : var1_1
                    object = new StringBuilder();
                    ((StringBuilder)object).append(" mLastNITZTimeInfo=");
                    ((StringBuilder)object).append(Arrays.toString(this.mLastNITZTimeInfo));
                    printWriter.println(((StringBuilder)object).toString());
                    object = new StringBuilder();
                    ((StringBuilder)object).append(" mTestingEmergencyCall=");
                    ((StringBuilder)object).append(this.mTestingEmergencyCall.get());
                    printWriter.println(((StringBuilder)object).toString());
                    this.mClientWakelockTracker.dumpClientRequestTracker(printWriter);
                    return;
                }
                object2 = (RILRequest)this.mRequestList.valueAt(n2);
                stringBuilder = new StringBuilder();
                stringBuilder.append("  [");
                stringBuilder.append(((RILRequest)object2).mSerial);
                stringBuilder.append("] ");
                stringBuilder.append(RIL.requestToString(((RILRequest)object2).mRequest));
                printWriter.println(stringBuilder.toString());
                ++n2;
            } while (true);
        }
    }

    @Override
    public void enableModem(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (this.mRadioVersion.less(RADIO_HAL_VERSION_1_3)) {
            if (object != null) {
                AsyncResult.forMessage((Message)object, null, (Throwable)CommandException.fromRilErrno(6));
                object.sendToTarget();
            }
            return;
        }
        if ((iRadio = (android.hardware.radio.V1_3.IRadio)iRadio) != null) {
            object = this.obtainRequest(146, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" enable = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.enableModem(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "enableModem", (Exception)throwable);
            }
        }
    }

    @Override
    public void exitEmergencyCallbackMode(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(99, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.exitEmergencyCallbackMode(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "exitEmergencyCallbackMode", (Exception)throwable);
            }
        }
    }

    @Override
    public void explicitCallTransfer(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(72, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.explicitCallTransfer(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "explicitCallTransfer", (Exception)throwable);
            }
        }
    }

    @Override
    public void getAllowedCarriers(Message object, WorkSource workSource) {
        Object object2 = this.getDeafultWorkSourceIfInvalid(workSource);
        workSource = this.getRadioProxy((Message)object);
        if (workSource == null) {
            return;
        }
        object = this.obtainRequest(137, (Message)object, (WorkSource)object2);
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(((RILRequest)object).serialString());
        ((StringBuilder)object2).append("> ");
        ((StringBuilder)object2).append(RIL.requestToString(((RILRequest)object).mRequest));
        this.riljLog(((StringBuilder)object2).toString());
        if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_4)) {
            this.riljLog("RIL.java - Using IRadio 1.4 or greater");
            workSource = (android.hardware.radio.V1_4.IRadio)workSource;
            try {
                workSource.getAllowedCarriers_1_4(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getAllowedCarriers_1_4", (Exception)throwable);
            }
        } else {
            this.riljLog("RIL.java - Using IRadio 1.3 or lower");
            try {
                workSource.getAllowedCarriers(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getAllowedCarriers", (Exception)throwable);
            }
        }
    }

    @Override
    public void getAvailableNetworks(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(48, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getAvailableNetworks(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getAvailableNetworks", (Exception)throwable);
            }
        }
    }

    @Override
    public void getBasebandVersion(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(51, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getBasebandVersion(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getBasebandVersion", (Exception)throwable);
            }
        }
    }

    @Override
    public void getCDMASubscription(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(95, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getCDMASubscription(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getCDMASubscription", (Exception)throwable);
            }
        }
    }

    @Override
    public void getCLIR(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(31, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getClir(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getCLIR", (Exception)throwable);
            }
        }
    }

    @Override
    public void getCdmaBroadcastConfig(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(92, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getCdmaBroadcastConfig(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getCdmaBroadcastConfig", (Exception)throwable);
            }
        }
    }

    @Override
    public void getCdmaSubscriptionSource(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(104, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getCdmaSubscriptionSource(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getCdmaSubscriptionSource", (Exception)throwable);
            }
        }
    }

    @Override
    public void getCellInfoList(Message object, WorkSource workSource) {
        Object object2 = this.getDeafultWorkSourceIfInvalid(workSource);
        workSource = this.getRadioProxy((Message)object);
        if (workSource != null) {
            object = this.obtainRequest(109, (Message)object, (WorkSource)object2);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(((RILRequest)object).serialString());
            ((StringBuilder)object2).append("> ");
            ((StringBuilder)object2).append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(((StringBuilder)object2).toString());
            try {
                workSource.getCellInfoList(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getCellInfoList", (Exception)throwable);
            }
        }
    }

    @Override
    public List<ClientRequestStats> getClientRequestStats() {
        return this.mClientWakelockTracker.getClientRequestStats();
    }

    @Override
    public void getCurrentCalls(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(9, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getCurrentCalls(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getCurrentCalls", (Exception)throwable);
            }
        }
    }

    @Override
    public void getDataCallList(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(57, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getDataCallList(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getDataCallList", (Exception)throwable);
            }
        }
    }

    @Override
    public void getDataRegistrationState(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(21, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getDataRegistrationState(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getDataRegistrationState", (Exception)throwable);
            }
        }
    }

    @Override
    public void getDeviceIdentity(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(98, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getDeviceIdentity(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getDeviceIdentity", (Exception)throwable);
            }
        }
    }

    @Override
    public void getGsmBroadcastConfig(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(89, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getGsmBroadcastConfig(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getGsmBroadcastConfig", (Exception)throwable);
            }
        }
    }

    public HalVersion getHalVersion() {
        return this.mRadioVersion;
    }

    @Override
    public void getHardwareConfig(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(124, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getHardwareConfig(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getHardwareConfig", (Exception)throwable);
            }
        }
    }

    @Override
    public void getIMEI(Message message) {
        throw new RuntimeException("getIMEI not expected to be called");
    }

    @Override
    public void getIMEISV(Message message) {
        throw new RuntimeException("getIMEISV not expected to be called");
    }

    @Override
    public void getIMSI(Message message) {
        this.getIMSIForApp(null, message);
    }

    @Override
    public void getIMSIForApp(String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(11, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append(">  ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" aid = ");
            stringBuilder.append(string);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getImsiForApp(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getIMSIForApp", (Exception)throwable);
            }
        }
    }

    @Override
    public void getIccCardStatus(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(1, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getIccCardStatus(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getIccCardStatus", (Exception)throwable);
            }
        }
    }

    @Override
    public void getIccSlotsStatus(Message message) {
        if (message != null) {
            AsyncResult.forMessage((Message)message, null, (Throwable)CommandException.fromRilErrno(6));
            message.sendToTarget();
        }
    }

    @Override
    public void getImsRegistrationState(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(112, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getImsRegistrationState(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getImsRegistrationState", (Exception)throwable);
            }
        }
    }

    @Override
    public void getLastCallFailCause(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(18, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getLastCallFailCause(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getLastCallFailCause", (Exception)throwable);
            }
        }
    }

    @Override
    public void getLastDataCallFailCause(Message message) {
        throw new RuntimeException("getLastDataCallFailCause not expected to be called");
    }

    @Deprecated
    @Override
    public void getLastPdpFailCause(Message message) {
        throw new RuntimeException("getLastPdpFailCause not expected to be called");
    }

    @Override
    public void getModemActivityInfo(Message object, WorkSource workSource) {
        Object object2 = this.getDeafultWorkSourceIfInvalid(workSource);
        workSource = this.getRadioProxy((Message)object);
        if (workSource != null) {
            object = this.obtainRequest(135, (Message)object, (WorkSource)object2);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(((RILRequest)object).serialString());
            ((StringBuilder)object2).append("> ");
            ((StringBuilder)object2).append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(((StringBuilder)object2).toString());
            try {
                workSource.getModemActivityInfo(((RILRequest)object).mSerial);
                workSource = this.mRilHandler.obtainMessage(5);
                workSource.obj = null;
                workSource.arg1 = ((RILRequest)object).mSerial;
                this.mRilHandler.sendMessageDelayed((Message)workSource, 2000L);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getModemActivityInfo", (Exception)throwable);
            }
        }
    }

    @Override
    public void getModemStatus(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (this.mRadioVersion.less(RADIO_HAL_VERSION_1_3)) {
            if (object != null) {
                AsyncResult.forMessage((Message)object, null, (Throwable)CommandException.fromRilErrno(6));
                object.sendToTarget();
            }
            return;
        }
        if ((iRadio = (android.hardware.radio.V1_3.IRadio)iRadio) != null) {
            object = this.obtainRequest(147, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getModemStackStatus(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getModemStatus", (Exception)throwable);
            }
        }
    }

    @Override
    public void getMute(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(54, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getMute(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getMute", (Exception)throwable);
            }
        }
    }

    @Override
    public void getNetworkSelectionMode(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(45, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getNetworkSelectionMode(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getNetworkSelectionMode", (Exception)throwable);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public IOemHook getOemHookProxy(Message object) {
        synchronized (this) {
            if (!this.mIsMobileNetworkSupported) {
                if (object == null) return null;
                AsyncResult.forMessage((Message)object, null, (Throwable)CommandException.fromRilErrno(1));
                object.sendToTarget();
                return null;
            }
            if (this.mOemHookProxy != null) {
                return this.mOemHookProxy;
            }
            try {
                if (this.mDisabledOemHookServices.contains(this.mPhoneId)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("getOemHookProxy: mOemHookProxy for ");
                    stringBuilder.append(HIDL_SERVICE_NAME[this.mPhoneId]);
                    stringBuilder.append(" is disabled");
                    this.riljLoge(stringBuilder.toString());
                } else {
                    this.mOemHookProxy = IOemHook.getService(HIDL_SERVICE_NAME[this.mPhoneId], true);
                    if (this.mOemHookProxy != null) {
                        this.mOemHookProxy.setResponseFunctions(this.mOemHookResponse, this.mOemHookIndication);
                    } else {
                        this.mDisabledOemHookServices.add(this.mPhoneId);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("getOemHookProxy: mOemHookProxy for ");
                        stringBuilder.append(HIDL_SERVICE_NAME[this.mPhoneId]);
                        stringBuilder.append(" is disabled");
                        this.riljLoge(stringBuilder.toString());
                    }
                }
            }
            catch (RemoteException remoteException) {
                this.mOemHookProxy = null;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("OemHookProxy getService/setResponseFunctions: ");
                stringBuilder.append((Object)remoteException);
                this.riljLoge(stringBuilder.toString());
            }
            catch (NoSuchElementException noSuchElementException) {
                this.mOemHookProxy = null;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("IOemHook service is not on the device HAL: ");
                stringBuilder.append(noSuchElementException);
                this.riljLoge(stringBuilder.toString());
            }
            if (this.mOemHookProxy != null) return this.mOemHookProxy;
            if (object == null) return this.mOemHookProxy;
            AsyncResult.forMessage((Message)object, null, (Throwable)CommandException.fromRilErrno(1));
            object.sendToTarget();
            return this.mOemHookProxy;
        }
    }

    @Override
    public void getOperator(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(22, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getOperator(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getOperator", (Exception)throwable);
            }
        }
    }

    @Deprecated
    @Override
    public void getPDPContextList(Message message) {
        this.getDataCallList(message);
    }

    @Override
    public void getPreferredNetworkType(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(74, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            if (this.mRadioVersion.lessOrEqual(RADIO_HAL_VERSION_1_3)) {
                try {
                    iRadio.getPreferredNetworkType(((RILRequest)object).mSerial);
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "getPreferredNetworkType", (Exception)throwable);
                }
            } else if (this.mRadioVersion.equals(RADIO_HAL_VERSION_1_4)) {
                iRadio = (android.hardware.radio.V1_4.IRadio)iRadio;
                try {
                    iRadio.getPreferredNetworkTypeBitmap(((RILRequest)object).mSerial);
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "getPreferredNetworkTypeBitmap", (Exception)throwable);
                }
            }
        }
    }

    @Override
    public void getPreferredVoicePrivacy(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(83, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getPreferredVoicePrivacy(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getPreferredVoicePrivacy", (Exception)throwable);
            }
        }
    }

    @VisibleForTesting
    public RadioBugDetector getRadioBugDetector() {
        if (this.mRadioBugDetector == null) {
            this.mRadioBugDetector = new RadioBugDetector(this.mContext, this.mPhoneId);
        }
        return this.mRadioBugDetector;
    }

    @Override
    public void getRadioCapability(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(130, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getRadioCapability(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getRadioCapability", (Exception)throwable);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public android.hardware.radio.V1_0.IRadio getRadioProxy(Message message) {
        synchronized (this) {
            block23 : {
                if (!this.mIsMobileNetworkSupported) {
                    if (message == null) return null;
                    AsyncResult.forMessage((Message)message, null, (Throwable)CommandException.fromRilErrno(1));
                    message.sendToTarget();
                    return null;
                }
                if (this.mRadioProxy != null) {
                    return this.mRadioProxy;
                }
                try {
                    if (this.mDisabledRadioServices.contains(this.mPhoneId)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("getRadioProxy: mRadioProxy for ");
                        stringBuilder.append(HIDL_SERVICE_NAME[this.mPhoneId]);
                        stringBuilder.append(" is disabled");
                        this.riljLoge(stringBuilder.toString());
                        break block23;
                    }
                    try {
                        this.mRadioProxy = android.hardware.radio.V1_4.IRadio.getService((String)HIDL_SERVICE_NAME[this.mPhoneId], (boolean)true);
                        this.mRadioVersion = RADIO_HAL_VERSION_1_4;
                    }
                    catch (NoSuchElementException noSuchElementException) {
                        // empty catch block
                    }
                    Object object = this.mRadioProxy;
                    if (object == null) {
                        try {
                            this.mRadioProxy = android.hardware.radio.V1_3.IRadio.getService((String)HIDL_SERVICE_NAME[this.mPhoneId], (boolean)true);
                            this.mRadioVersion = RADIO_HAL_VERSION_1_3;
                        }
                        catch (NoSuchElementException noSuchElementException) {
                            // empty catch block
                        }
                    }
                    if ((object = this.mRadioProxy) == null) {
                        try {
                            this.mRadioProxy = IRadio.getService((String)HIDL_SERVICE_NAME[this.mPhoneId], (boolean)true);
                            this.mRadioVersion = RADIO_HAL_VERSION_1_2;
                        }
                        catch (NoSuchElementException noSuchElementException) {
                            // empty catch block
                        }
                    }
                    if ((object = this.mRadioProxy) == null) {
                        try {
                            this.mRadioProxy = android.hardware.radio.V1_1.IRadio.getService((String)HIDL_SERVICE_NAME[this.mPhoneId], (boolean)true);
                            this.mRadioVersion = RADIO_HAL_VERSION_1_1;
                        }
                        catch (NoSuchElementException noSuchElementException) {
                            // empty catch block
                        }
                    }
                    if ((object = this.mRadioProxy) == null) {
                        try {
                            this.mRadioProxy = android.hardware.radio.V1_0.IRadio.getService((String)HIDL_SERVICE_NAME[this.mPhoneId], (boolean)true);
                            this.mRadioVersion = RADIO_HAL_VERSION_1_0;
                        }
                        catch (NoSuchElementException noSuchElementException) {
                            // empty catch block
                        }
                    }
                    if (this.mRadioProxy != null) {
                        this.mRadioProxy.linkToDeath((IHwBinder.DeathRecipient)this.mRadioProxyDeathRecipient, this.mRadioProxyCookie.incrementAndGet());
                        this.mRadioProxy.setResponseFunctions((IRadioResponse)this.mRadioResponse, (IRadioIndication)this.mRadioIndication);
                    } else {
                        this.mDisabledRadioServices.add(this.mPhoneId);
                        object = new StringBuilder();
                        ((StringBuilder)object).append("getRadioProxy: mRadioProxy for ");
                        ((StringBuilder)object).append(HIDL_SERVICE_NAME[this.mPhoneId]);
                        ((StringBuilder)object).append(" is disabled");
                        this.riljLoge(((StringBuilder)object).toString());
                    }
                }
                catch (RemoteException remoteException) {
                    this.mRadioProxy = null;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("RadioProxy getService/setResponseFunctions: ");
                    stringBuilder.append((Object)remoteException);
                    this.riljLoge(stringBuilder.toString());
                }
            }
            if (this.mRadioProxy != null) return this.mRadioProxy;
            this.riljLoge("getRadioProxy: mRadioProxy == null");
            if (message == null) return this.mRadioProxy;
            AsyncResult.forMessage((Message)message, null, (Throwable)CommandException.fromRilErrno(1));
            message.sendToTarget();
            return this.mRadioProxy;
        }
    }

    @VisibleForTesting
    public RilHandler getRilHandler() {
        return this.mRilHandler;
    }

    @VisibleForTesting
    public SparseArray<RILRequest> getRilRequestList() {
        return this.mRequestList;
    }

    @Override
    public void getSignalStrength(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(19, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_4)) {
                iRadio = (android.hardware.radio.V1_4.IRadio)iRadio;
                try {
                    iRadio.getSignalStrength_1_4(((RILRequest)object).mSerial);
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "getSignalStrength_1_4", (Exception)throwable);
                }
            } else {
                try {
                    iRadio.getSignalStrength(((RILRequest)object).mSerial);
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "getSignalStrength", (Exception)throwable);
                }
            }
        }
    }

    @Override
    public void getSmscAddress(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(100, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getSmscAddress(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getSmscAddress", (Exception)throwable);
            }
        }
    }

    @Override
    public void getVoiceRadioTechnology(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(108, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getVoiceRadioTechnology(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getVoiceRadioTechnology", (Exception)throwable);
            }
        }
    }

    @Override
    public void getVoiceRegistrationState(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(20, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getVoiceRegistrationState(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getVoiceRegistrationState", (Exception)throwable);
            }
        }
    }

    @VisibleForTesting
    public PowerManager.WakeLock getWakeLock(int n) {
        PowerManager.WakeLock wakeLock = n == 0 ? this.mWakeLock : this.mAckWakeLock;
        return wakeLock;
    }

    @Override
    public void handleCallSetupRequestFromSim(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(71, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.handleStkCallSetupRequestFromSim(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getAllowedCarriers", (Exception)throwable);
            }
        }
    }

    @Override
    public void hangupConnection(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(12, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" gsmIndex = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.hangup(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "hangupConnection", (Exception)throwable);
            }
        }
    }

    @UnsupportedAppUsage
    @Override
    public void hangupForegroundResumeBackground(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(14, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.hangupForegroundResumeBackground(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "hangupForegroundResumeBackground", (Exception)throwable);
            }
        }
    }

    @UnsupportedAppUsage
    @Override
    public void hangupWaitingOrBackground(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(13, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.hangupWaitingOrBackground(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "hangupWaitingOrBackground", (Exception)throwable);
            }
        }
    }

    @Override
    public void iccCloseLogicalChannel(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(116, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" channel = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.iccCloseLogicalChannel(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "iccCloseLogicalChannel", (Exception)throwable);
            }
        }
    }

    @Override
    public void iccIO(int n, int n2, String string, int n3, int n4, int n5, String string2, String string3, Message message) {
        this.iccIOForApp(n, n2, string, n3, n4, n5, string2, string3, null, message);
    }

    @Override
    public void iccIOForApp(int n, int n2, String string, int n3, int n4, int n5, String string2, String string3, String string4, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            StringBuilder stringBuilder;
            object = this.obtainRequest(28, (Message)object, this.mRILDefaultWorkSource);
            if (Build.IS_DEBUGGABLE) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object).serialString());
                stringBuilder.append("> iccIO: ");
                stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                stringBuilder.append(" command = 0x");
                stringBuilder.append(Integer.toHexString(n));
                stringBuilder.append(" fileId = 0x");
                stringBuilder.append(Integer.toHexString(n2));
                stringBuilder.append(" path = ");
                stringBuilder.append(string);
                stringBuilder.append(" p1 = ");
                stringBuilder.append(n3);
                stringBuilder.append(" p2 = ");
                stringBuilder.append(n4);
                stringBuilder.append(" p3 =  data = ");
                stringBuilder.append(string2);
                stringBuilder.append(" aid = ");
                stringBuilder.append(string4);
                this.riljLog(stringBuilder.toString());
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object).serialString());
                stringBuilder.append("> iccIO: ");
                stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                this.riljLog(stringBuilder.toString());
            }
            stringBuilder = new IccIo();
            ((IccIo)stringBuilder).command = n;
            ((IccIo)stringBuilder).fileId = n2;
            ((IccIo)stringBuilder).path = RIL.convertNullToEmptyString(string);
            ((IccIo)stringBuilder).p1 = n3;
            ((IccIo)stringBuilder).p2 = n4;
            ((IccIo)stringBuilder).p3 = n5;
            ((IccIo)stringBuilder).data = RIL.convertNullToEmptyString(string2);
            ((IccIo)stringBuilder).pin2 = RIL.convertNullToEmptyString(string3);
            ((IccIo)stringBuilder).aid = RIL.convertNullToEmptyString(string4);
            try {
                iRadio.iccIOForApp(((RILRequest)object).mSerial, (IccIo)stringBuilder);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "iccIOForApp", (Exception)throwable);
            }
        }
    }

    @Override
    public void iccOpenLogicalChannel(String string, int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(115, (Message)object, this.mRILDefaultWorkSource);
            if (Build.IS_DEBUGGABLE) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object).serialString());
                stringBuilder.append("> ");
                stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                stringBuilder.append(" aid = ");
                stringBuilder.append(string);
                stringBuilder.append(" p2 = ");
                stringBuilder.append(n);
                this.riljLog(stringBuilder.toString());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object).serialString());
                stringBuilder.append("> ");
                stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                this.riljLog(stringBuilder.toString());
            }
            try {
                iRadio.iccOpenLogicalChannel(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "iccOpenLogicalChannel", (Exception)throwable);
            }
        }
    }

    @Override
    public void iccTransmitApduBasicChannel(int n, int n2, int n3, int n4, int n5, String string, Message object) {
        block4 : {
            android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
            if (iRadio == null) break block4;
            object = this.obtainRequest(114, (Message)object, this.mRILDefaultWorkSource);
            if (Build.IS_DEBUGGABLE) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object).serialString());
                stringBuilder.append("> ");
                stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                stringBuilder.append(String.format(" cla = 0x%02X ins = 0x%02X", n, n2));
                stringBuilder.append(String.format(" p1 = 0x%02X p2 = 0x%02X p3 = 0x%02X", n3, n4, n5));
                stringBuilder.append(" data = ");
                stringBuilder.append(string);
                this.riljLog(stringBuilder.toString());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object).serialString());
                stringBuilder.append("> ");
                stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                this.riljLog(stringBuilder.toString());
            }
            string = this.createSimApdu(0, n, n2, n3, n4, n5, string);
            try {
                iRadio.iccTransmitApduBasicChannel(((RILRequest)object).mSerial, (SimApdu)string);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "iccTransmitApduBasicChannel", (Exception)throwable);
            }
        }
    }

    @Override
    public void iccTransmitApduLogicalChannel(int n, int n2, int n3, int n4, int n5, int n6, String charSequence, Message object) {
        if (n > 0) {
            android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
            if (iRadio != null) {
                object = this.obtainRequest(117, (Message)object, this.mRILDefaultWorkSource);
                if (Build.IS_DEBUGGABLE) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(((RILRequest)object).serialString());
                    stringBuilder.append("> ");
                    stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                    stringBuilder.append(String.format(" channel = %d", n));
                    stringBuilder.append(String.format(" cla = 0x%02X ins = 0x%02X", n2, n3));
                    stringBuilder.append(String.format(" p1 = 0x%02X p2 = 0x%02X p3 = 0x%02X", n4, n5, n6));
                    stringBuilder.append(" data = ");
                    stringBuilder.append((String)charSequence);
                    this.riljLog(stringBuilder.toString());
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(((RILRequest)object).serialString());
                    stringBuilder.append("> ");
                    stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                    this.riljLog(stringBuilder.toString());
                }
                charSequence = this.createSimApdu(n, n2, n3, n4, n5, n6, (String)charSequence);
                try {
                    iRadio.iccTransmitApduLogicalChannel(((RILRequest)object).mSerial, (SimApdu)charSequence);
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "iccTransmitApduLogicalChannel", (Exception)throwable);
                }
            }
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Invalid channel in iccTransmitApduLogicalChannel: ");
        ((StringBuilder)charSequence).append(n);
        throw new RuntimeException(((StringBuilder)charSequence).toString());
    }

    @UnsupportedAppUsage
    @Override
    public void invokeOemRilRequestRaw(byte[] arrby, Message object) {
        IOemHook iOemHook = this.getOemHookProxy((Message)object);
        if (iOemHook != null) {
            object = this.obtainRequest(59, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append("[");
            stringBuilder.append(IccUtils.bytesToHexString((byte[])arrby));
            stringBuilder.append("]");
            this.riljLog(stringBuilder.toString());
            try {
                iOemHook.sendRequestRaw(((RILRequest)object).mSerial, RIL.primitiveArrayToArrayList(arrby));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "invokeOemRilRequestRaw", (Exception)throwable);
            }
        } else {
            this.riljLog("Radio Oem Hook Service is disabled for P and later devices. ");
        }
    }

    @Override
    public void invokeOemRilRequestStrings(String[] arrstring, Message object) {
        IOemHook iOemHook = this.getOemHookProxy((Message)object);
        if (iOemHook != null) {
            int n;
            StringBuilder stringBuilder;
            RILRequest rILRequest = this.obtainRequest(60, (Message)object, this.mRILDefaultWorkSource);
            object = "";
            for (n = 0; n < arrstring.length; ++n) {
                stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append(arrstring[n]);
                stringBuilder.append(" ");
                object = stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(rILRequest.serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(rILRequest.mRequest));
            stringBuilder.append(" strings = ");
            stringBuilder.append((String)object);
            this.riljLog(stringBuilder.toString());
            try {
                n = rILRequest.mSerial;
                object = new ArrayList(Arrays.asList(arrstring));
                iOemHook.sendRequestStrings(n, (ArrayList<String>)object);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR(rILRequest, "invokeOemRilRequestStrings", (Exception)throwable);
            }
        } else {
            this.riljLog("Radio Oem Hook Service is disabled for P and later devices. ");
        }
    }

    @UnsupportedAppUsage
    RadioCapability makeStaticRadioCapability() {
        int n = 0;
        Object object = this.mContext.getResources().getString(17039766);
        if (!TextUtils.isEmpty((CharSequence)object)) {
            n = RadioAccessFamily.rafTypeFromString((String)object);
        }
        object = new RadioCapability(this.mPhoneId, 0, 0, n, "", 1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Faking RIL_REQUEST_GET_RADIO_CAPABILITY response using ");
        stringBuilder.append(n);
        this.riljLog(stringBuilder.toString());
        return object;
    }

    @UnsupportedAppUsage
    void notifyRegistrantsCdmaInfoRec(CdmaInformationRecords cdmaInformationRecords) {
        if (cdmaInformationRecords.record instanceof CdmaInformationRecords.CdmaDisplayInfoRec) {
            if (this.mDisplayInfoRegistrants != null) {
                this.unsljLogRet(1027, cdmaInformationRecords.record);
                this.mDisplayInfoRegistrants.notifyRegistrants(new AsyncResult(null, cdmaInformationRecords.record, null));
            }
        } else if (cdmaInformationRecords.record instanceof CdmaInformationRecords.CdmaSignalInfoRec) {
            if (this.mSignalInfoRegistrants != null) {
                this.unsljLogRet(1027, cdmaInformationRecords.record);
                this.mSignalInfoRegistrants.notifyRegistrants(new AsyncResult(null, cdmaInformationRecords.record, null));
            }
        } else if (cdmaInformationRecords.record instanceof CdmaInformationRecords.CdmaNumberInfoRec) {
            if (this.mNumberInfoRegistrants != null) {
                this.unsljLogRet(1027, cdmaInformationRecords.record);
                this.mNumberInfoRegistrants.notifyRegistrants(new AsyncResult(null, cdmaInformationRecords.record, null));
            }
        } else if (cdmaInformationRecords.record instanceof CdmaInformationRecords.CdmaRedirectingNumberInfoRec) {
            if (this.mRedirNumInfoRegistrants != null) {
                this.unsljLogRet(1027, cdmaInformationRecords.record);
                this.mRedirNumInfoRegistrants.notifyRegistrants(new AsyncResult(null, cdmaInformationRecords.record, null));
            }
        } else if (cdmaInformationRecords.record instanceof CdmaInformationRecords.CdmaLineControlInfoRec) {
            if (this.mLineControlInfoRegistrants != null) {
                this.unsljLogRet(1027, cdmaInformationRecords.record);
                this.mLineControlInfoRegistrants.notifyRegistrants(new AsyncResult(null, cdmaInformationRecords.record, null));
            }
        } else if (cdmaInformationRecords.record instanceof CdmaInformationRecords.CdmaT53ClirInfoRec) {
            if (this.mT53ClirInfoRegistrants != null) {
                this.unsljLogRet(1027, cdmaInformationRecords.record);
                this.mT53ClirInfoRegistrants.notifyRegistrants(new AsyncResult(null, cdmaInformationRecords.record, null));
            }
        } else if (cdmaInformationRecords.record instanceof CdmaInformationRecords.CdmaT53AudioControlInfoRec && this.mT53AudCntrlInfoRegistrants != null) {
            this.unsljLogRet(1027, cdmaInformationRecords.record);
            this.mT53AudCntrlInfoRegistrants.notifyRegistrants(new AsyncResult(null, cdmaInformationRecords.record, null));
        }
    }

    @UnsupportedAppUsage
    void notifyRegistrantsRilConnectionChanged(int n) {
        this.mRilVersion = n;
        if (this.mRilConnectedRegistrants != null) {
            this.mRilConnectedRegistrants.notifyRegistrants(new AsyncResult(null, (Object)new Integer(n), null));
        }
    }

    @Override
    public void nvReadItem(int n, Message object, WorkSource workSource) {
        Object object2 = this.getDeafultWorkSourceIfInvalid(workSource);
        workSource = this.getRadioProxy((Message)object);
        if (workSource != null) {
            object = this.obtainRequest(118, (Message)object, (WorkSource)object2);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(((RILRequest)object).serialString());
            ((StringBuilder)object2).append("> ");
            ((StringBuilder)object2).append(RIL.requestToString(((RILRequest)object).mRequest));
            ((StringBuilder)object2).append(" itemId = ");
            ((StringBuilder)object2).append(n);
            this.riljLog(((StringBuilder)object2).toString());
            try {
                workSource.nvReadItem(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "nvReadItem", (Exception)throwable);
            }
        }
    }

    @Override
    public void nvResetConfig(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(121, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" resetType = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.nvResetConfig(((RILRequest)object).mSerial, RIL.convertToHalResetNvType(n));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "nvResetConfig", (Exception)throwable);
            }
        }
    }

    @Override
    public void nvWriteCdmaPrl(byte[] arrby, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(120, (Message)object, this.mRILDefaultWorkSource);
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append(((RILRequest)object).serialString());
            ((StringBuilder)serializable).append("> ");
            ((StringBuilder)serializable).append(RIL.requestToString(((RILRequest)object).mRequest));
            ((StringBuilder)serializable).append(" PreferredRoamingList = 0x");
            ((StringBuilder)serializable).append(IccUtils.bytesToHexString((byte[])arrby));
            this.riljLog(((StringBuilder)serializable).toString());
            serializable = new ArrayList();
            for (int i = 0; i < arrby.length; ++i) {
                ((ArrayList)serializable).add(arrby[i]);
            }
            try {
                iRadio.nvWriteCdmaPrl(((RILRequest)object).mSerial, (ArrayList)serializable);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "nvWriteCdmaPrl", (Exception)throwable);
            }
        }
    }

    @Override
    public void nvWriteItem(int n, String string, Message object, WorkSource workSource) {
        Object object2 = this.getDeafultWorkSourceIfInvalid(workSource);
        workSource = this.getRadioProxy((Message)object);
        if (workSource != null) {
            object = this.obtainRequest(119, (Message)object, (WorkSource)object2);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(((RILRequest)object).serialString());
            ((StringBuilder)object2).append("> ");
            ((StringBuilder)object2).append(RIL.requestToString(((RILRequest)object).mRequest));
            ((StringBuilder)object2).append(" itemId = ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(" itemValue = ");
            ((StringBuilder)object2).append(string);
            this.riljLog(((StringBuilder)object2).toString());
            object2 = new NvWriteItem();
            ((NvWriteItem)object2).itemId = n;
            ((NvWriteItem)object2).value = RIL.convertNullToEmptyString(string);
            try {
                workSource.nvWriteItem(((RILRequest)object).mSerial, (NvWriteItem)object2);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "nvWriteItem", (Exception)throwable);
            }
        }
    }

    void processIndication(int n) {
        if (n == 1) {
            this.sendAck();
            this.riljLog("Unsol response received; Sending ack to ril.cpp");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void processRequestAck(int n) {
        Object object = this.mRequestList;
        // MONITORENTER : object
        RILRequest rILRequest = (RILRequest)this.mRequestList.get(n);
        // MONITOREXIT : object
        if (rILRequest == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("processRequestAck: Unexpected solicited ack response! serial: ");
            ((StringBuilder)object).append(n);
            Rlog.w((String)RILJ_LOG_TAG, (String)((StringBuilder)object).toString());
            return;
        }
        this.decrementWakeLock(rILRequest);
        object = new StringBuilder();
        ((StringBuilder)object).append(rILRequest.serialString());
        ((StringBuilder)object).append(" Ack < ");
        ((StringBuilder)object).append(RIL.requestToString(rILRequest.mRequest));
        this.riljLog(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public RILRequest processResponse(RadioResponseInfo object) {
        int n = ((RadioResponseInfo)object).serial;
        int n2 = ((RadioResponseInfo)object).error;
        int n3 = ((RadioResponseInfo)object).type;
        if (n3 == 1) {
            Object object2 = this.mRequestList;
            // MONITORENTER : object2
            object = (RILRequest)this.mRequestList.get(n);
            // MONITOREXIT : object2
            if (object == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unexpected solicited ack response! sn: ");
                ((StringBuilder)object2).append(n);
                Rlog.w((String)RILJ_LOG_TAG, (String)((StringBuilder)object2).toString());
                return object;
            }
            this.decrementWakeLock((RILRequest)object);
            object2 = this.mRadioBugDetector;
            if (object2 != null) {
                ((RadioBugDetector)object2).detectRadioBug(((RILRequest)object).mRequest, n2);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(((RILRequest)object).serialString());
            ((StringBuilder)object2).append(" Ack < ");
            ((StringBuilder)object2).append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(((StringBuilder)object2).toString());
            return object;
        }
        object = this.findAndRemoveRequestFromList(n);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("processResponse: Unexpected response! serial: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" error: ");
            ((StringBuilder)object).append(n2);
            Rlog.e((String)RILJ_LOG_TAG, (String)((StringBuilder)object).toString());
            return null;
        }
        this.addToRilHistogram((RILRequest)object);
        Object object3 = this.mRadioBugDetector;
        if (object3 != null) {
            ((RadioBugDetector)object3).detectRadioBug(((RILRequest)object).mRequest, n2);
        }
        if (n3 == 2) {
            this.sendAck();
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Response received for ");
            ((StringBuilder)object3).append(((RILRequest)object).serialString());
            ((StringBuilder)object3).append(" ");
            ((StringBuilder)object3).append(RIL.requestToString(((RILRequest)object).mRequest));
            ((StringBuilder)object3).append(" Sending ack to ril.cpp");
            this.riljLog(((StringBuilder)object3).toString());
        }
        if ((n = ((RILRequest)object).mRequest) != 3 && n != 5) {
            if (n == 129) {
                this.setRadioState(2, false);
            }
        } else if (this.mIccStatusChangedRegistrants != null) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("ON enter sim puk fakeSimStatusChanged: reg count=");
            ((StringBuilder)object3).append(this.mIccStatusChangedRegistrants.size());
            this.riljLog(((StringBuilder)object3).toString());
            this.mIccStatusChangedRegistrants.notifyRegistrants();
        }
        if (n2 != 0) {
            n2 = ((RILRequest)object).mRequest;
            if (n2 != 2 && n2 != 4 && n2 != 43 && n2 != 6 && n2 != 7) {
                return object;
            }
            if (this.mIccStatusChangedRegistrants == null) return object;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("ON some errors fakeSimStatusChanged: reg count=");
            ((StringBuilder)object3).append(this.mIccStatusChangedRegistrants.size());
            this.riljLog(((StringBuilder)object3).toString());
            this.mIccStatusChangedRegistrants.notifyRegistrants();
            return object;
        }
        if (((RILRequest)object).mRequest != 14) {
            return object;
        }
        if (!this.mTestingEmergencyCall.getAndSet(false)) return object;
        if (this.mEmergencyCallbackModeRegistrant == null) return object;
        this.riljLog("testing emergency call, notify ECM Registrants");
        this.mEmergencyCallbackModeRegistrant.notifyRegistrant();
        return object;
    }

    @VisibleForTesting
    public void processResponseDone(RILRequest rILRequest, RadioResponseInfo radioResponseInfo, Object object) {
        if (radioResponseInfo.error == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(rILRequest.serialString());
            stringBuilder.append("< ");
            stringBuilder.append(RIL.requestToString(rILRequest.mRequest));
            stringBuilder.append(" ");
            stringBuilder.append(RIL.retToString(rILRequest.mRequest, object));
            this.riljLog(stringBuilder.toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(rILRequest.serialString());
            stringBuilder.append("< ");
            stringBuilder.append(RIL.requestToString(rILRequest.mRequest));
            stringBuilder.append(" error ");
            stringBuilder.append(radioResponseInfo.error);
            this.riljLog(stringBuilder.toString());
            rILRequest.onError(radioResponseInfo.error, object);
        }
        this.mMetrics.writeOnRilSolicitedResponse(this.mPhoneId, rILRequest.mSerial, radioResponseInfo.error, rILRequest.mRequest, object);
        if (radioResponseInfo.type == 0) {
            this.decrementWakeLock(rILRequest);
        }
        rILRequest.release();
    }

    @Deprecated
    @Override
    public void pullLceData(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(134, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.pullLceData(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "pullLceData", (Exception)throwable);
            }
        }
    }

    @Override
    public void queryAvailableBandMode(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(66, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getAvailableBandModes(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "queryAvailableBandMode", (Exception)throwable);
            }
        }
    }

    @Override
    public void queryCLIP(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(55, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getClip(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "queryCLIP", (Exception)throwable);
            }
        }
    }

    @Override
    public void queryCallForwardStatus(int n, int n2, String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(33, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" cfreason = ");
            stringBuilder.append(n);
            stringBuilder.append(" serviceClass = ");
            stringBuilder.append(n2);
            this.riljLog(stringBuilder.toString());
            stringBuilder = new android.hardware.radio.V1_0.CallForwardInfo();
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).reason = n;
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).serviceClass = n2;
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).toa = PhoneNumberUtils.toaFromString((String)string);
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).number = RIL.convertNullToEmptyString(string);
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).timeSeconds = 0;
            try {
                iRadio.getCallForwardStatus(((RILRequest)object).mSerial, (android.hardware.radio.V1_0.CallForwardInfo)stringBuilder);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "queryCallForwardStatus", (Exception)throwable);
            }
        }
    }

    @Override
    public void queryCallWaiting(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(35, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" serviceClass = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getCallWaiting(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "queryCallWaiting", (Exception)throwable);
            }
        }
    }

    @Override
    public void queryCdmaRoamingPreference(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(79, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getCdmaRoamingPreference(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "queryCdmaRoamingPreference", (Exception)throwable);
            }
        }
    }

    @Override
    public void queryFacilityLock(String string, String string2, int n, Message message) {
        this.queryFacilityLockForApp(string, string2, n, null, message);
    }

    @Override
    public void queryFacilityLockForApp(String string, String string2, int n, String string3, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(42, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" facility = ");
            stringBuilder.append(string);
            stringBuilder.append(" serviceClass = ");
            stringBuilder.append(n);
            stringBuilder.append(" appId = ");
            stringBuilder.append(string3);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getFacilityLockForApp(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), RIL.convertNullToEmptyString(string2), n, RIL.convertNullToEmptyString(string3));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "getFacilityLockForApp", (Exception)throwable);
            }
        }
    }

    @Override
    public void queryTTYMode(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(81, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.getTTYMode(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "queryTTYMode", (Exception)throwable);
            }
        }
    }

    @Override
    public void rejectCall(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(17, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.rejectCall(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "rejectCall", (Exception)throwable);
            }
        }
    }

    @Override
    public void reportSmsMemoryStatus(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(102, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" available = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.reportSmsMemoryStatus(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "reportSmsMemoryStatus", (Exception)throwable);
            }
        }
    }

    @Override
    public void reportStkServiceIsRunning(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(103, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.reportStkServiceIsRunning(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "reportStkServiceIsRunning", (Exception)throwable);
            }
        }
    }

    @Override
    public void requestIccSimAuthentication(int n, String string, String string2, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(125, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.requestIccSimAuthentication(((RILRequest)object).mSerial, n, RIL.convertNullToEmptyString(string), RIL.convertNullToEmptyString(string2));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "requestIccSimAuthentication", (Exception)throwable);
            }
        }
    }

    @Override
    public void requestShutdown(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(129, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.requestShutdown(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "requestShutdown", (Exception)throwable);
            }
        }
    }

    @Override
    public void resetRadio(Message message) {
        throw new RuntimeException("resetRadio not expected to be called");
    }

    @UnsupportedAppUsage
    void riljLog(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" [SUB");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append("]");
        Rlog.d((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
    }

    void riljLoge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" [SUB");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append("]");
        Rlog.e((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
    }

    void riljLoge(String string, Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" [SUB");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append("]");
        Rlog.e((String)RILJ_LOG_TAG, (String)stringBuilder.toString(), (Throwable)exception);
    }

    void riljLogv(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" [SUB");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append("]");
        Rlog.v((String)RILJ_LOG_TAG, (String)stringBuilder.toString());
    }

    @Override
    public void sendBurstDtmf(String string, int n, int n2, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(85, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" dtmfString = ");
            stringBuilder.append(string);
            stringBuilder.append(" on = ");
            stringBuilder.append(n);
            stringBuilder.append(" off = ");
            stringBuilder.append(n2);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.sendBurstDtmf(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), n, n2);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendBurstDtmf", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendCDMAFeatureCode(String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(84, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" featureCode = ");
            stringBuilder.append(string);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.sendCDMAFeatureCode(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendCDMAFeatureCode", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendCdmaSms(byte[] arrby, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(87, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            stringBuilder = new CdmaSmsMessage();
            this.constructCdmaSendSmsRilRequest((CdmaSmsMessage)stringBuilder, arrby);
            try {
                iRadio.sendCdmaSms(((RILRequest)object).mSerial, (CdmaSmsMessage)stringBuilder);
                this.mMetrics.writeRilSendSms(this.mPhoneId, ((RILRequest)object).mSerial, 2, 2);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendCdmaSms", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendDeviceState(int n, boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(138, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" ");
            stringBuilder.append(n);
            stringBuilder.append(":");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.sendDeviceState(((RILRequest)object).mSerial, n, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendDeviceState", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendDtmf(char c, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(24, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                int n = ((RILRequest)object).mSerial;
                stringBuilder = new StringBuilder();
                stringBuilder.append(c);
                stringBuilder.append("");
                iRadio.sendDtmf(n, stringBuilder.toString());
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendDtmf", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendEnvelope(String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(69, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" contents = ");
            stringBuilder.append(string);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.sendEnvelope(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendEnvelope", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendEnvelopeWithStatus(String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(107, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" contents = ");
            stringBuilder.append(string);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.sendEnvelopeWithStatus(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendEnvelopeWithStatus", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendImsCdmaSms(byte[] arrby, int n, int n2, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(113, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            stringBuilder = new ImsSmsMessage();
            ((ImsSmsMessage)stringBuilder).tech = 2;
            n = (byte)n;
            boolean bl = true;
            if (n < 1) {
                bl = false;
            }
            ((ImsSmsMessage)stringBuilder).retry = bl;
            ((ImsSmsMessage)stringBuilder).messageRef = n2;
            CdmaSmsMessage cdmaSmsMessage = new CdmaSmsMessage();
            this.constructCdmaSendSmsRilRequest(cdmaSmsMessage, arrby);
            ((ImsSmsMessage)stringBuilder).cdmaMessage.add(cdmaSmsMessage);
            try {
                iRadio.sendImsSms(((RILRequest)object).mSerial, (ImsSmsMessage)stringBuilder);
                this.mMetrics.writeRilSendSms(this.mPhoneId, ((RILRequest)object).mSerial, 3, 2);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendImsCdmaSms", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendImsGsmSms(String string, String string2, int n, int n2, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(113, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            stringBuilder = new ImsSmsMessage();
            ((ImsSmsMessage)stringBuilder).tech = 1;
            boolean bl = (byte)n >= 1;
            ((ImsSmsMessage)stringBuilder).retry = bl;
            ((ImsSmsMessage)stringBuilder).messageRef = n2;
            string = this.constructGsmSendSmsRilRequest(string, string2);
            ((ImsSmsMessage)stringBuilder).gsmMessage.add(string);
            try {
                iRadio.sendImsSms(((RILRequest)object).mSerial, (ImsSmsMessage)stringBuilder);
                this.mMetrics.writeRilSendSms(this.mPhoneId, ((RILRequest)object).mSerial, 3, 1);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendImsGsmSms", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendSMS(String string, String string2, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(25, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            string = this.constructGsmSendSmsRilRequest(string, string2);
            try {
                iRadio.sendSms(((RILRequest)object).mSerial, (GsmSmsMessage)string);
                this.mMetrics.writeRilSendSms(this.mPhoneId, ((RILRequest)object).mSerial, 1, 1);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendSMS", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendSMSExpectMore(String string, String string2, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(26, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            string = this.constructGsmSendSmsRilRequest(string, string2);
            try {
                iRadio.sendSMSExpectMore(((RILRequest)object).mSerial, (GsmSmsMessage)string);
                this.mMetrics.writeRilSendSms(this.mPhoneId, ((RILRequest)object).mSerial, 1, 1);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendSMSExpectMore", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendTerminalResponse(String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            RILRequest rILRequest = this.obtainRequest(70, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(rILRequest.serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(rILRequest.mRequest));
            stringBuilder.append(" contents = ");
            object = Build.IS_DEBUGGABLE ? string : this.censoredTerminalResponse(string);
            stringBuilder.append((String)object);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.sendTerminalResponseToSim(rILRequest.mSerial, RIL.convertNullToEmptyString(string));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR(rILRequest, "sendTerminalResponse", (Exception)throwable);
            }
        }
    }

    @Override
    public void sendUSSD(String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(29, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" ussd = ");
            stringBuilder.append("*******");
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.sendUssd(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "sendUSSD", (Exception)throwable);
            }
        }
    }

    @Override
    public void separateConnection(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(52, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" gsmIndex = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.separateConnection(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "separateConnection", (Exception)throwable);
            }
        }
    }

    @Override
    public void setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules, Message message, WorkSource object) {
        this.riljLog("RIL.java - setAllowedCarriers");
        Preconditions.checkNotNull((Object)carrierRestrictionRules, (Object)"Carrier restriction cannot be null.");
        object = this.getDeafultWorkSourceIfInvalid((WorkSource)object);
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy(message);
        if (iRadio == null) {
            return;
        }
        object = this.obtainRequest(136, message, (WorkSource)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((RILRequest)object).serialString());
        stringBuilder.append("> ");
        stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
        stringBuilder.append(" params: ");
        stringBuilder.append((Object)carrierRestrictionRules);
        this.riljLog(stringBuilder.toString());
        int n = 0;
        int n2 = carrierRestrictionRules.getMultiSimPolicy();
        int n3 = 1;
        boolean bl = true;
        if (n2 == 1) {
            n = 1;
        }
        if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_4)) {
            this.riljLog("RIL.java - Using IRadio 1.4 or greater");
            message = (android.hardware.radio.V1_4.IRadio)iRadio;
            iRadio = new CarrierRestrictionsWithPriority();
            iRadio.allowedCarriers = RIL.createCarrierRestrictionList(carrierRestrictionRules.getAllowedCarriers());
            iRadio.excludedCarriers = RIL.createCarrierRestrictionList(carrierRestrictionRules.getExcludedCarriers());
            if (carrierRestrictionRules.getDefaultCarrierRestriction() != 0) {
                bl = false;
            }
            iRadio.allowedCarriersPrioritized = bl;
            try {
                message.setAllowedCarriers_1_4(((RILRequest)object).mSerial, (CarrierRestrictionsWithPriority)iRadio, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setAllowedCarriers_1_4", (Exception)throwable);
            }
        } else {
            bl = carrierRestrictionRules.isAllCarriersAllowed();
            n2 = !(bl || carrierRestrictionRules.getExcludedCarriers().isEmpty() && carrierRestrictionRules.getDefaultCarrierRestriction() == 0) ? 0 : 1;
            n = n2 != 0 && n == 0 ? n3 : 0;
            if (n == 0) {
                this.riljLoge("setAllowedCarriers does not support excluded list on IRadio version less than 1.4");
                if (message != null) {
                    AsyncResult.forMessage((Message)message, null, (Throwable)CommandException.fromRilErrno(6));
                    message.sendToTarget();
                }
                return;
            }
            this.riljLog("RIL.java - Using IRadio 1.3 or lower");
            message = new CarrierRestrictions();
            message.allowedCarriers = RIL.createCarrierRestrictionList(carrierRestrictionRules.getAllowedCarriers());
            try {
                iRadio.setAllowedCarriers(((RILRequest)object).mSerial, bl, (CarrierRestrictions)message);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setAllowedCarriers", (Exception)throwable);
            }
        }
    }

    @Override
    public void setBandMode(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(65, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" bandMode = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setBandMode(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setBandMode", (Exception)throwable);
            }
        }
    }

    @Override
    public void setCLIR(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(32, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" clirMode = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setClir(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setCLIR", (Exception)throwable);
            }
        }
    }

    @Override
    public void setCallForward(int n, int n2, int n3, String string, int n4, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(34, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" action = ");
            stringBuilder.append(n);
            stringBuilder.append(" cfReason = ");
            stringBuilder.append(n2);
            stringBuilder.append(" serviceClass = ");
            stringBuilder.append(n3);
            stringBuilder.append(" timeSeconds = ");
            stringBuilder.append(n4);
            this.riljLog(stringBuilder.toString());
            stringBuilder = new android.hardware.radio.V1_0.CallForwardInfo();
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).status = n;
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).reason = n2;
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).serviceClass = n3;
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).toa = PhoneNumberUtils.toaFromString((String)string);
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).number = RIL.convertNullToEmptyString(string);
            ((android.hardware.radio.V1_0.CallForwardInfo)stringBuilder).timeSeconds = n4;
            try {
                iRadio.setCallForward(((RILRequest)object).mSerial, (android.hardware.radio.V1_0.CallForwardInfo)stringBuilder);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setCallForward", (Exception)throwable);
            }
        }
    }

    @Override
    public void setCallWaiting(boolean bl, int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(36, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" enable = ");
            stringBuilder.append(bl);
            stringBuilder.append(" serviceClass = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setCallWaiting(((RILRequest)object).mSerial, bl, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setCallWaiting", (Exception)throwable);
            }
        }
    }

    @Override
    public void setCarrierInfoForImsiEncryption(android.telephony.ImsiEncryptionInfo arrby, Message object) {
        Preconditions.checkNotNull((Object)arrby, (Object)"ImsiEncryptionInfo cannot be null.");
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_1)) {
                iRadio = (android.hardware.radio.V1_1.IRadio)iRadio;
                object = this.obtainRequest(141, (Message)object, this.mRILDefaultWorkSource);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object).serialString());
                stringBuilder.append("> ");
                stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                this.riljLog(stringBuilder.toString());
                stringBuilder = new ImsiEncryptionInfo();
                ((ImsiEncryptionInfo)stringBuilder).mnc = arrby.getMnc();
                ((ImsiEncryptionInfo)stringBuilder).mcc = arrby.getMcc();
                ((ImsiEncryptionInfo)stringBuilder).keyIdentifier = arrby.getKeyIdentifier();
                if (arrby.getExpirationTime() != null) {
                    ((ImsiEncryptionInfo)stringBuilder).expirationTime = arrby.getExpirationTime().getTime();
                }
                for (byte by : arrby.getPublicKey().getEncoded()) {
                    ArrayList arrayList = ((ImsiEncryptionInfo)stringBuilder).carrierKey;
                    Byte by2 = new Byte(by);
                    arrayList.add(by2);
                }
                try {
                    iRadio.setCarrierInfoForImsiEncryption(((RILRequest)object).mSerial, (ImsiEncryptionInfo)stringBuilder);
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "setCarrierInfoForImsiEncryption", (Exception)throwable);
                }
            } else if (object != null) {
                AsyncResult.forMessage((Message)object, null, (Throwable)CommandException.fromRilErrno(6));
                object.sendToTarget();
            }
        }
    }

    @Override
    public void setCdmaBroadcastActivation(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(94, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" activate = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setCdmaBroadcastActivation(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setCdmaBroadcastActivation", (Exception)throwable);
            }
        }
    }

    @Override
    public void setCdmaBroadcastConfig(CdmaSmsBroadcastConfigInfo[] object, Message object2) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object2);
        if (iRadio != null) {
            object2 = this.obtainRequest(93, (Message)object2, this.mRILDefaultWorkSource);
            ArrayList<CdmaBroadcastSmsConfigInfo> arrayList = new ArrayList<CdmaBroadcastSmsConfigInfo>();
            for (CdmaSmsBroadcastConfigInfo cdmaSmsBroadcastConfigInfo : object) {
                int n = cdmaSmsBroadcastConfigInfo.getFromServiceCategory();
                while (n <= cdmaSmsBroadcastConfigInfo.getToServiceCategory()) {
                    CdmaBroadcastSmsConfigInfo cdmaBroadcastSmsConfigInfo = new CdmaBroadcastSmsConfigInfo();
                    cdmaBroadcastSmsConfigInfo.serviceCategory = n++;
                    cdmaBroadcastSmsConfigInfo.language = cdmaSmsBroadcastConfigInfo.getLanguage();
                    cdmaBroadcastSmsConfigInfo.selected = cdmaSmsBroadcastConfigInfo.isSelected();
                    arrayList.add(cdmaBroadcastSmsConfigInfo);
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(((RILRequest)object2).serialString());
            ((StringBuilder)object).append("> ");
            ((StringBuilder)object).append(RIL.requestToString(((RILRequest)object2).mRequest));
            ((StringBuilder)object).append(" with ");
            ((StringBuilder)object).append(arrayList.size());
            ((StringBuilder)object).append(" configs : ");
            this.riljLog(((StringBuilder)object).toString());
            object = arrayList.iterator();
            while (object.hasNext()) {
                this.riljLog(((CdmaBroadcastSmsConfigInfo)object.next()).toString());
            }
            try {
                iRadio.setCdmaBroadcastConfig(((RILRequest)object2).mSerial, arrayList);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object2, "setCdmaBroadcastConfig", (Exception)throwable);
            }
        }
    }

    @Override
    public void setCdmaRoamingPreference(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(78, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" cdmaRoamingType = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setCdmaRoamingPreference(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setCdmaRoamingPreference", (Exception)throwable);
            }
        }
    }

    @Override
    public void setCdmaSubscriptionSource(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(77, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" cdmaSubscription = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setCdmaSubscriptionSource(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setCdmaSubscriptionSource", (Exception)throwable);
            }
        }
    }

    @Override
    public void setCellInfoListRate(int n, Message object, WorkSource workSource) {
        Object object2 = this.getDeafultWorkSourceIfInvalid(workSource);
        workSource = this.getRadioProxy((Message)object);
        if (workSource != null) {
            object = this.obtainRequest(110, (Message)object, (WorkSource)object2);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(((RILRequest)object).serialString());
            ((StringBuilder)object2).append("> ");
            ((StringBuilder)object2).append(RIL.requestToString(((RILRequest)object).mRequest));
            ((StringBuilder)object2).append(" rateInMillis = ");
            ((StringBuilder)object2).append(n);
            this.riljLog(((StringBuilder)object2).toString());
            try {
                workSource.setCellInfoListRate(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setCellInfoListRate", (Exception)throwable);
            }
        }
    }

    @Override
    public void setDataAllowed(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(123, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" allowed = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setDataAllowed(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setDataAllowed", (Exception)throwable);
            }
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void setDataProfile(DataProfile[] var1_1, boolean var2_3, Message var3_4) {
        var4_5 = this.getRadioProxy((Message)var3_4);
        if (var4_5 == null) return;
        var6_7 = var5_6 = null;
        var7_47 = this.mRadioVersion.greaterOrEqual(RIL.RADIO_HAL_VERSION_1_4);
        var8_48 = 0;
        var9_49 = 0;
        if (!var7_47) ** GOTO lbl50
        var6_8 = var5_6;
        var4_5 = (android.hardware.radio.V1_4.IRadio)var4_5;
        var6_9 = var5_6;
        var6_10 = var3_4 = this.obtainRequest(128, (Message)var3_4, this.mRILDefaultWorkSource);
        var6_11 = var3_4;
        var10_50 = new ArrayList<android.hardware.radio.V1_4.DataProfileInfo>();
        var6_12 = var3_4;
        var8_48 = var1_1.length;
        for (var11_52 = 0; var11_52 < var8_48; ++var11_52) {
            var6_14 = var3_4;
            var10_50.add(RIL.convertToHalDataProfile14(var1_1[var11_52]));
        }
        var6_15 = var3_4;
        var6_16 = var3_4;
        var5_6 = new StringBuilder();
        var6_17 = var3_4;
        var5_6.append(var3_4.serialString());
        var6_18 = var3_4;
        var5_6.append("> ");
        var6_19 = var3_4;
        var5_6.append(RIL.requestToString(var3_4.mRequest));
        var6_20 = var3_4;
        var5_6.append(" with data profiles : ");
        var6_21 = var3_4;
        this.riljLog(var5_6.toString());
        var6_22 = var3_4;
        var8_48 = var1_1.length;
        for (var11_52 = var9_49; var11_52 < var8_48; ++var11_52) {
            var6_24 = var3_4;
            this.riljLog(var1_1[var11_52].toString());
        }
        var6_25 = var3_4;
        var4_5.setDataProfile_1_4(var3_4.mSerial, var10_50);
        return;
lbl50: // 1 sources:
        var6_26 = var5_6;
        var6_27 = var5_6;
        var10_51 = new ArrayList<DataProfileInfo>();
        var6_28 = var5_6;
        for (DataProfile var12_54 : var1_1) {
            var6_30 = var5_6;
            if (!var12_54.isPersistent()) continue;
            var6_31 = var5_6;
            var10_51.add(RIL.convertToHalDataProfile10(var12_54));
        }
        var6_33 = var5_6;
        if (var10_51.isEmpty() != false) return;
        var6_34 = var5_6;
        var6_35 = var3_4 = this.obtainRequest(128, (Message)var3_4, this.mRILDefaultWorkSource);
        var6_36 = var3_4;
        var5_6 = new StringBuilder();
        var6_37 = var3_4;
        var5_6.append(var3_4.serialString());
        var6_38 = var3_4;
        var5_6.append("> ");
        var6_39 = var3_4;
        var5_6.append(RIL.requestToString(var3_4.mRequest));
        var6_40 = var3_4;
        var5_6.append(" with data profiles : ");
        var6_41 = var3_4;
        this.riljLog(var5_6.toString());
        var6_42 = var3_4;
        var9_49 = var1_1.length;
        for (var11_53 = var8_48; var11_53 < var9_49; ++var11_53) {
            var6_44 = var3_4;
            this.riljLog(var1_1[var11_53].toString());
        }
        var6_45 = var3_4;
        try {
            var4_5.setDataProfile(var3_4.mSerial, var10_51, var2_3);
            return;
        }
        catch (RemoteException | RuntimeException var1_2) {
            this.handleRadioProxyExceptionForRR((RILRequest)var6_46, "setDataProfile", (Exception)var1_2);
        }
    }

    @Override
    public void setFacilityLock(String string, boolean bl, String string2, int n, Message message) {
        this.setFacilityLockForApp(string, bl, string2, n, null, message);
    }

    @Override
    public void setFacilityLockForApp(String string, boolean bl, String string2, int n, String string3, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(43, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" facility = ");
            stringBuilder.append(string);
            stringBuilder.append(" lockstate = ");
            stringBuilder.append(bl);
            stringBuilder.append(" serviceClass = ");
            stringBuilder.append(n);
            stringBuilder.append(" appId = ");
            stringBuilder.append(string3);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setFacilityLockForApp(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), bl, RIL.convertNullToEmptyString(string2), n, RIL.convertNullToEmptyString(string3));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setFacilityLockForApp", (Exception)throwable);
            }
        }
    }

    @Override
    public void setGsmBroadcastActivation(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(91, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" activate = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setGsmBroadcastActivation(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setGsmBroadcastActivation", (Exception)throwable);
            }
        }
    }

    @Override
    public void setGsmBroadcastConfig(SmsBroadcastConfigInfo[] arrsmsBroadcastConfigInfo, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            int n;
            object = this.obtainRequest(90, (Message)object, this.mRILDefaultWorkSource);
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append(((RILRequest)object).serialString());
            ((StringBuilder)serializable).append("> ");
            ((StringBuilder)serializable).append(RIL.requestToString(((RILRequest)object).mRequest));
            ((StringBuilder)serializable).append(" with ");
            ((StringBuilder)serializable).append(arrsmsBroadcastConfigInfo.length);
            ((StringBuilder)serializable).append(" configs : ");
            this.riljLog(((StringBuilder)serializable).toString());
            for (n = 0; n < arrsmsBroadcastConfigInfo.length; ++n) {
                this.riljLog(arrsmsBroadcastConfigInfo[n].toString());
            }
            serializable = new ArrayList();
            int n2 = arrsmsBroadcastConfigInfo.length;
            for (n = 0; n < n2; ++n) {
                GsmBroadcastSmsConfigInfo gsmBroadcastSmsConfigInfo = new GsmBroadcastSmsConfigInfo();
                gsmBroadcastSmsConfigInfo.fromServiceId = arrsmsBroadcastConfigInfo[n].getFromServiceId();
                gsmBroadcastSmsConfigInfo.toServiceId = arrsmsBroadcastConfigInfo[n].getToServiceId();
                gsmBroadcastSmsConfigInfo.fromCodeScheme = arrsmsBroadcastConfigInfo[n].getFromCodeScheme();
                gsmBroadcastSmsConfigInfo.toCodeScheme = arrsmsBroadcastConfigInfo[n].getToCodeScheme();
                gsmBroadcastSmsConfigInfo.selected = arrsmsBroadcastConfigInfo[n].isSelected();
                ((ArrayList)serializable).add(gsmBroadcastSmsConfigInfo);
            }
            try {
                iRadio.setGsmBroadcastConfig(((RILRequest)object).mSerial, (ArrayList)serializable);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setGsmBroadcastConfig", (Exception)throwable);
            }
        }
    }

    @Override
    public void setInitialAttachApn(DataProfile dataProfile, boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(111, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append((Object)dataProfile);
            this.riljLog(stringBuilder.toString());
            try {
                if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_4)) {
                    ((android.hardware.radio.V1_4.IRadio)iRadio).setInitialAttachApn_1_4(((RILRequest)object).mSerial, RIL.convertToHalDataProfile14(dataProfile));
                } else {
                    iRadio.setInitialAttachApn(((RILRequest)object).mSerial, RIL.convertToHalDataProfile10(dataProfile), dataProfile.isPersistent(), bl);
                }
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setInitialAttachApn", (Exception)throwable);
            }
        }
    }

    @Override
    public void setLinkCapacityReportingCriteria(int n, int n2, int n3, int[] arrn, int[] arrn2, int n4, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            if (this.mRadioVersion.less(RADIO_HAL_VERSION_1_2)) {
                this.riljLoge("setLinkCapacityReportingCriteria ignored on IRadio version less than 1.2");
                return;
            }
            object = this.obtainRequest(203, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                ((IRadio)iRadio).setLinkCapacityReportingCriteria(((RILRequest)object).mSerial, n, n2, n3, RIL.primitiveArrayToArrayList(arrn), RIL.primitiveArrayToArrayList(arrn2), RIL.convertRanToHalRan(n4));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setLinkCapacityReportingCriteria", (Exception)throwable);
            }
        }
    }

    @Override
    public void setLocationUpdates(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(76, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" enable = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setLocationUpdates(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setLocationUpdates", (Exception)throwable);
            }
        }
    }

    @Override
    public void setLogicalToPhysicalSlotMapping(int[] arrn, Message message) {
        if (message != null) {
            AsyncResult.forMessage((Message)message, null, (Throwable)CommandException.fromRilErrno(6));
            message.sendToTarget();
        }
    }

    @Override
    public void setMute(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(53, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" enableMute = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setMute(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setMute", (Exception)throwable);
            }
        }
    }

    @Override
    public void setNetworkSelectionModeAutomatic(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(46, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setNetworkSelectionModeAutomatic(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setNetworkSelectionModeAutomatic", (Exception)throwable);
            }
        }
    }

    @Override
    public void setNetworkSelectionModeManual(String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(47, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" operatorNumeric = ");
            stringBuilder.append(string);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setNetworkSelectionModeManual(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setNetworkSelectionModeManual", (Exception)throwable);
            }
        }
    }

    @Override
    public void setOnNITZTime(Handler handler, int n, Object object) {
        super.setOnNITZTime(handler, n, object);
        if (this.mLastNITZTimeInfo != null) {
            this.mNITZTimeRegistrant.notifyRegistrant(new AsyncResult(null, (Object)this.mLastNITZTimeInfo, null));
        }
    }

    @Override
    public void setPhoneType(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setPhoneType=");
        stringBuilder.append(n);
        stringBuilder.append(" old value=");
        stringBuilder.append(this.mPhoneType);
        this.riljLog(stringBuilder.toString());
        this.mPhoneType = n;
    }

    @Override
    public void setPreferredNetworkType(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(73, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" networkType = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            this.mPreferredNetworkType = n;
            this.mMetrics.writeSetPreferredNetworkType(this.mPhoneId, n);
            if (this.mRadioVersion.lessOrEqual(RADIO_HAL_VERSION_1_3)) {
                try {
                    iRadio.setPreferredNetworkType(((RILRequest)object).mSerial, n);
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "setPreferredNetworkType", (Exception)throwable);
                }
            } else if (this.mRadioVersion.equals(RADIO_HAL_VERSION_1_4)) {
                iRadio = (android.hardware.radio.V1_4.IRadio)iRadio;
                try {
                    iRadio.setPreferredNetworkTypeBitmap(((RILRequest)object).mSerial, RIL.convertToHalRadioAccessFamily(RadioAccessFamily.getRafFromNetworkType((int)n)));
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "setPreferredNetworkTypeBitmap", (Exception)throwable);
                }
            }
        }
    }

    @Override
    public void setPreferredVoicePrivacy(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(82, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" enable = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setPreferredVoicePrivacy(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setPreferredVoicePrivacy", (Exception)throwable);
            }
        }
    }

    @Override
    public void setRadioCapability(RadioCapability radioCapability, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(131, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" RadioCapability = ");
            stringBuilder.append(radioCapability.toString());
            this.riljLog(stringBuilder.toString());
            stringBuilder = new android.hardware.radio.V1_0.RadioCapability();
            ((android.hardware.radio.V1_0.RadioCapability)stringBuilder).session = radioCapability.getSession();
            ((android.hardware.radio.V1_0.RadioCapability)stringBuilder).phase = radioCapability.getPhase();
            ((android.hardware.radio.V1_0.RadioCapability)stringBuilder).raf = radioCapability.getRadioAccessFamily();
            ((android.hardware.radio.V1_0.RadioCapability)stringBuilder).logicalModemUuid = RIL.convertNullToEmptyString(radioCapability.getLogicalModemUuid());
            ((android.hardware.radio.V1_0.RadioCapability)stringBuilder).status = radioCapability.getStatus();
            try {
                iRadio.setRadioCapability(((RILRequest)object).mSerial, (android.hardware.radio.V1_0.RadioCapability)stringBuilder);
            }
            catch (Exception exception) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setRadioCapability", exception);
            }
        }
    }

    @UnsupportedAppUsage
    @Override
    public void setRadioPower(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(23, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" on = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setRadioPower(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setRadioPower", (Exception)throwable);
            }
        }
    }

    @Override
    public void setSignalStrengthReportingCriteria(int n, int n2, int[] arrn, int n3, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            if (this.mRadioVersion.less(RADIO_HAL_VERSION_1_2)) {
                this.riljLoge("setSignalStrengthReportingCriteria ignored on IRadio version less than 1.2");
                return;
            }
            object = this.obtainRequest(202, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                ((IRadio)iRadio).setSignalStrengthReportingCriteria(((RILRequest)object).mSerial, n, n2, RIL.primitiveArrayToArrayList(arrn), RIL.convertRanToHalRan(n3));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setSignalStrengthReportingCriteria", (Exception)throwable);
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void setSimCardPower(int var1_1, Message var2_2, WorkSource var3_5) {
        var3_5 = this.getDeafultWorkSourceIfInvalid((WorkSource)var3_5);
        var4_6 = this.getRadioProxy(var2_2);
        if (var4_6 == null) return;
        var3_5 = this.obtainRequest(140, var2_2, (WorkSource)var3_5);
        var5_7 = new StringBuilder();
        var5_7.append(var3_5.serialString());
        var5_7.append("> ");
        var5_7.append(RIL.requestToString(var3_5.mRequest));
        var5_7.append(" ");
        var5_7.append(var1_1);
        this.riljLog(var5_7.toString());
        if (this.mRadioVersion.greaterOrEqual(RIL.RADIO_HAL_VERSION_1_1)) {
            try {
                ((android.hardware.radio.V1_1.IRadio)var4_6).setSimCardPower_1_1(var3_5.mSerial, var1_1);
                return;
            }
            catch (RemoteException | RuntimeException var2_3) {
                this.handleRadioProxyExceptionForRR((RILRequest)var3_5, "setSimCardPower", (Exception)var2_3);
                return;
            }
        }
        if (var1_1 == 0) ** GOTO lbl34
        if (var1_1 == 1) ** GOTO lbl32
        if (var2_2 == null) return;
        try {
            AsyncResult.forMessage((Message)var2_2, null, (Throwable)CommandException.fromRilErrno(6));
            var2_2.sendToTarget();
            return;
lbl32: // 1 sources:
            var4_6.setSimCardPower(var3_5.mSerial, true);
            return;
lbl34: // 1 sources:
            var4_6.setSimCardPower(var3_5.mSerial, false);
            return;
        }
        catch (RemoteException | RuntimeException var2_4) {
            this.handleRadioProxyExceptionForRR((RILRequest)var3_5, "setSimCardPower", (Exception)var2_4);
        }
    }

    @Override
    public void setSmscAddress(String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(101, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" address = ");
            stringBuilder.append(string);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setSmscAddress(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setSmscAddress", (Exception)throwable);
            }
        }
    }

    @Override
    public void setSuppServiceNotifications(boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(62, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" enable = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setSuppServiceNotifications(((RILRequest)object).mSerial, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setSuppServiceNotifications", (Exception)throwable);
            }
        }
    }

    @Override
    public void setTTYMode(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(80, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" ttyMode = ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.setTTYMode(((RILRequest)object).mSerial, n);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setTTYMode", (Exception)throwable);
            }
        }
    }

    @Override
    public void setUiccSubscription(int n, int n2, int n3, int n4, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(122, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" slot = ");
            stringBuilder.append(n);
            stringBuilder.append(" appIndex = ");
            stringBuilder.append(n2);
            stringBuilder.append(" subId = ");
            stringBuilder.append(n3);
            stringBuilder.append(" subStatus = ");
            stringBuilder.append(n4);
            this.riljLog(stringBuilder.toString());
            stringBuilder = new SelectUiccSub();
            ((SelectUiccSub)stringBuilder).slot = n;
            ((SelectUiccSub)stringBuilder).appIndex = n2;
            ((SelectUiccSub)stringBuilder).subType = n3;
            ((SelectUiccSub)stringBuilder).actStatus = n4;
            try {
                iRadio.setUiccSubscription(((RILRequest)object).mSerial, (SelectUiccSub)stringBuilder);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "setUiccSubscription", (Exception)throwable);
            }
        }
    }

    @Override
    public void setUnsolResponseFilter(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(139, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" ");
            stringBuilder.append(n);
            this.riljLog(stringBuilder.toString());
            if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_2)) {
                try {
                    ((IRadio)iRadio).setIndicationFilter_1_2(((RILRequest)object).mSerial, n);
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "setIndicationFilter_1_2", (Exception)throwable);
                }
            } else {
                try {
                    iRadio.setIndicationFilter(((RILRequest)object).mSerial, n & 7);
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "setIndicationFilter", (Exception)throwable);
                }
            }
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void setupDataCall(int n, DataProfile dataProfile, boolean bl, boolean bl2, int n2, LinkProperties object, Message object2) {
        void var2_9;
        block18 : {
            Object object4;
            Object object3;
            DataProfileInfo dataProfileInfo;
            block17 : {
                boolean bl3;
                Object object5;
                block16 : {
                    object3 = this.getRadioProxy((Message)object2);
                    if (object3 == null) return;
                    object2 = this.obtainRequest(27, (Message)object2, this.mRILDefaultWorkSource);
                    dataProfileInfo = new ArrayList();
                    object4 = new ArrayList<String>();
                    if (object != null) {
                        object5 = object.getAddresses().iterator();
                        while (object5.hasNext()) {
                            dataProfileInfo.add(((InetAddress)object5.next()).getHostAddress());
                        }
                        object = object.getDnsServers().iterator();
                        while (object.hasNext()) {
                            ((ArrayList)object4).add(((InetAddress)object.next()).getHostAddress());
                        }
                    }
                    bl3 = this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_4);
                    if (!bl3) break block16;
                    object = (android.hardware.radio.V1_4.IRadio)object3;
                    object3 = RIL.convertToHalDataProfile14(dataProfile);
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append(((RILRequest)object2).serialString());
                    ((StringBuilder)object5).append("> ");
                    ((StringBuilder)object5).append(RIL.requestToString(((RILRequest)object2).mRequest));
                    ((StringBuilder)object5).append(",accessNetworkType=");
                    ((StringBuilder)object5).append(AccessNetworkConstants.AccessNetworkType.toString((int)n));
                    ((StringBuilder)object5).append(",isRoaming=");
                    ((StringBuilder)object5).append(bl);
                    ((StringBuilder)object5).append(",allowRoaming=");
                    ((StringBuilder)object5).append(bl2);
                    ((StringBuilder)object5).append(",");
                    ((StringBuilder)object5).append((Object)dataProfile);
                    ((StringBuilder)object5).append(",addresses=");
                    ((StringBuilder)object5).append((Object)dataProfileInfo);
                    ((StringBuilder)object5).append(",dnses=");
                    ((StringBuilder)object5).append(object4);
                    this.riljLog(((StringBuilder)object5).toString());
                    int n3 = ((RILRequest)object2).mSerial;
                    try {
                        object.setupDataCall_1_4(n3, n, (android.hardware.radio.V1_4.DataProfileInfo)object3, bl2, n2, (ArrayList)dataProfileInfo, (ArrayList)object4);
                        return;
                    }
                    catch (RemoteException | RuntimeException throwable) {}
                    break block18;
                    catch (RemoteException | RuntimeException throwable) {}
                    break block18;
                }
                bl3 = this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_2);
                if (!bl3) break block17;
                object = (IRadio)object3;
                object5 = RIL.convertToHalDataProfile10(dataProfile);
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(((RILRequest)object2).serialString());
                ((StringBuilder)object3).append("> ");
                ((StringBuilder)object3).append(RIL.requestToString(((RILRequest)object2).mRequest));
                ((StringBuilder)object3).append(",accessNetworkType=");
                ((StringBuilder)object3).append(AccessNetworkConstants.AccessNetworkType.toString((int)n));
                ((StringBuilder)object3).append(",isRoaming=");
                ((StringBuilder)object3).append(bl);
                ((StringBuilder)object3).append(",allowRoaming=");
                ((StringBuilder)object3).append(bl2);
                ((StringBuilder)object3).append(",");
                ((StringBuilder)object3).append((Object)dataProfile);
                ((StringBuilder)object3).append(",addresses=");
                ((StringBuilder)object3).append((Object)dataProfileInfo);
                ((StringBuilder)object3).append(",dnses=");
                ((StringBuilder)object3).append(object4);
                this.riljLog(((StringBuilder)object3).toString());
                int n4 = ((RILRequest)object2).mSerial;
                bl3 = dataProfile.isPersistent();
                try {
                    object.setupDataCall_1_2(n4, n, (DataProfileInfo)object5, bl3, bl2, bl, n2, (ArrayList)dataProfileInfo, (ArrayList)object4);
                    return;
                }
                catch (RemoteException | RuntimeException throwable) {}
                break block18;
                catch (RemoteException | RuntimeException throwable) {}
                break block18;
            }
            object = object2;
            try {
                dataProfileInfo = RIL.convertToHalDataProfile10(dataProfile);
                object4 = PhoneFactory.getPhone(this.mPhoneId);
                n = object4 != null && (object4 = object4.getServiceState()) != null ? object4.getRilDataRadioTechnology() : 0;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append(((RILRequest)object).serialString());
                ((StringBuilder)object4).append("> ");
                ((StringBuilder)object4).append(RIL.requestToString(((RILRequest)object).mRequest));
                ((StringBuilder)object4).append(",dataRat=");
                ((StringBuilder)object4).append(n);
                ((StringBuilder)object4).append(",isRoaming=");
                ((StringBuilder)object4).append(bl);
                ((StringBuilder)object4).append(",allowRoaming=");
                ((StringBuilder)object4).append(bl2);
                ((StringBuilder)object4).append(",");
                ((StringBuilder)object4).append((Object)dataProfile);
                this.riljLog(((StringBuilder)object4).toString());
                object3.setupDataCall(((RILRequest)object).mSerial, n, dataProfileInfo, dataProfile.isPersistent(), bl2, bl);
                return;
            }
            catch (RemoteException | RuntimeException throwable) {}
            break block18;
            catch (RemoteException | RuntimeException throwable) {
                // empty catch block
            }
        }
        this.handleRadioProxyExceptionForRR((RILRequest)object2, "setupDataCall", (Exception)var2_9);
    }

    @Override
    public void startDtmf(char c, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(49, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                int n = ((RILRequest)object).mSerial;
                stringBuilder = new StringBuilder();
                stringBuilder.append(c);
                stringBuilder.append("");
                iRadio.startDtmf(n, stringBuilder.toString());
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "startDtmf", (Exception)throwable);
            }
        }
    }

    @Override
    public void startLceService(int n, boolean bl, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_2)) {
            return;
        }
        if (iRadio != null) {
            object = this.obtainRequest(132, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" reportIntervalMs = ");
            stringBuilder.append(n);
            stringBuilder.append(" pullMode = ");
            stringBuilder.append(bl);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.startLceService(((RILRequest)object).mSerial, n, bl);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "startLceService", (Exception)throwable);
            }
        }
    }

    @Override
    public void startNattKeepalive(int n, KeepalivePacketData keepalivePacketData, int n2, Message message) {
        Preconditions.checkNotNull((Object)keepalivePacketData, (Object)"KeepaliveRequest cannot be null.");
        Object object = this.getRadioProxy(message);
        if (object == null) {
            this.riljLoge("Radio Proxy object is null!");
            return;
        }
        if (this.mRadioVersion.less(RADIO_HAL_VERSION_1_1)) {
            if (message != null) {
                AsyncResult.forMessage((Message)message, null, (Throwable)CommandException.fromRilErrno(6));
                message.sendToTarget();
            }
            return;
        }
        android.hardware.radio.V1_1.IRadio iRadio = (android.hardware.radio.V1_1.IRadio)object;
        object = this.obtainRequest(144, message, this.mRILDefaultWorkSource);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((RILRequest)object).serialString());
        stringBuilder.append("> ");
        stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
        this.riljLog(stringBuilder.toString());
        try {
            stringBuilder = new KeepaliveRequest();
            ((KeepaliveRequest)stringBuilder).cid = n;
            if (keepalivePacketData.dstAddress instanceof Inet4Address) {
                ((KeepaliveRequest)stringBuilder).type = 0;
            } else if (keepalivePacketData.dstAddress instanceof Inet6Address) {
                ((KeepaliveRequest)stringBuilder).type = 1;
            } else {
                AsyncResult.forMessage((Message)message, null, (Throwable)CommandException.fromRilErrno(44));
                message.sendToTarget();
                return;
            }
            RIL.appendPrimitiveArrayToArrayList(keepalivePacketData.srcAddress.getAddress(), ((KeepaliveRequest)stringBuilder).sourceAddress);
            ((KeepaliveRequest)stringBuilder).sourcePort = keepalivePacketData.srcPort;
            RIL.appendPrimitiveArrayToArrayList(keepalivePacketData.dstAddress.getAddress(), ((KeepaliveRequest)stringBuilder).destinationAddress);
            ((KeepaliveRequest)stringBuilder).destinationPort = keepalivePacketData.dstPort;
            ((KeepaliveRequest)stringBuilder).maxKeepaliveIntervalMillis = n2;
            iRadio.startKeepalive(((RILRequest)object).mSerial, (KeepaliveRequest)stringBuilder);
        }
        catch (RemoteException | RuntimeException throwable) {
            this.handleRadioProxyExceptionForRR((RILRequest)object, "startNattKeepalive", (Exception)throwable);
        }
    }

    @Override
    public void startNetworkScan(android.telephony.NetworkScanRequest object, Message object2) {
        block15 : {
            android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object2);
            if (iRadio != null) {
                boolean bl = this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_2);
                int n = 0;
                if (bl) {
                    NetworkScanRequest networkScanRequest = new NetworkScanRequest();
                    networkScanRequest.type = object.getScanType();
                    networkScanRequest.interval = object.getSearchPeriodicity();
                    networkScanRequest.maxSearchTime = object.getMaxSearchTime();
                    networkScanRequest.incrementalResultsPeriodicity = object.getIncrementalResultsPeriodicity();
                    networkScanRequest.incrementalResults = object.getIncrementalResults();
                    for (RadioAccessSpecifier radioAccessSpecifier : object.getSpecifiers()) {
                        if ((radioAccessSpecifier = this.convertRadioAccessSpecifierToRadioHAL(radioAccessSpecifier)) == null) {
                            return;
                        }
                        networkScanRequest.specifiers.add(radioAccessSpecifier);
                    }
                    networkScanRequest.mccMncs.addAll(object.getPlmns());
                    object = this.obtainRequest(142, (Message)object2, this.mRILDefaultWorkSource);
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(((RILRequest)object).serialString());
                    ((StringBuilder)object2).append("> ");
                    ((StringBuilder)object2).append(RIL.requestToString(((RILRequest)object).mRequest));
                    this.riljLog(((StringBuilder)object2).toString());
                    try {
                        if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_4)) {
                            ((android.hardware.radio.V1_4.IRadio)iRadio).startNetworkScan_1_4(((RILRequest)object).mSerial, networkScanRequest);
                            break block15;
                        }
                        ((IRadio)iRadio).startNetworkScan_1_2(((RILRequest)object).mSerial, networkScanRequest);
                    }
                    catch (RemoteException | RuntimeException throwable) {
                        this.handleRadioProxyExceptionForRR((RILRequest)object, "startNetworkScan", (Exception)throwable);
                    }
                } else if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_1)) {
                    iRadio = (android.hardware.radio.V1_1.IRadio)iRadio;
                    android.hardware.radio.V1_1.NetworkScanRequest networkScanRequest = new android.hardware.radio.V1_1.NetworkScanRequest();
                    networkScanRequest.type = object.getScanType();
                    networkScanRequest.interval = object.getSearchPeriodicity();
                    object = object.getSpecifiers();
                    int n2 = ((RadioAccessSpecifier[])object).length;
                    for (int i = n; i < n2; ++i) {
                        RadioAccessSpecifier radioAccessSpecifier = object[i];
                        if ((radioAccessSpecifier = this.convertRadioAccessSpecifierToRadioHAL(radioAccessSpecifier)) == null) {
                            return;
                        }
                        networkScanRequest.specifiers.add(radioAccessSpecifier);
                    }
                    object = this.obtainRequest(142, (Message)object2, this.mRILDefaultWorkSource);
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(((RILRequest)object).serialString());
                    ((StringBuilder)object2).append("> ");
                    ((StringBuilder)object2).append(RIL.requestToString(((RILRequest)object).mRequest));
                    this.riljLog(((StringBuilder)object2).toString());
                    try {
                        iRadio.startNetworkScan(((RILRequest)object).mSerial, networkScanRequest);
                    }
                    catch (RemoteException | RuntimeException throwable) {
                        this.handleRadioProxyExceptionForRR((RILRequest)object, "startNetworkScan", (Exception)throwable);
                    }
                } else if (object2 != null) {
                    AsyncResult.forMessage((Message)object2, null, (Throwable)CommandException.fromRilErrno(6));
                    object2.sendToTarget();
                }
            }
        }
    }

    @Override
    public void stopDtmf(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(50, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.stopDtmf(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "stopDtmf", (Exception)throwable);
            }
        }
    }

    @Override
    public void stopLceService(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_2)) {
            return;
        }
        if (iRadio != null) {
            object = this.obtainRequest(133, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.stopLceService(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "stopLceService", (Exception)throwable);
            }
        }
    }

    @Override
    public void stopNattKeepalive(int n, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio == null) {
            Rlog.e((String)RILJ_LOG_TAG, (String)"Radio Proxy object is null!");
            return;
        }
        if (this.mRadioVersion.less(RADIO_HAL_VERSION_1_1)) {
            if (object != null) {
                AsyncResult.forMessage((Message)object, null, (Throwable)CommandException.fromRilErrno(6));
                object.sendToTarget();
            }
            return;
        }
        iRadio = (android.hardware.radio.V1_1.IRadio)iRadio;
        object = this.obtainRequest(145, (Message)object, this.mRILDefaultWorkSource);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((RILRequest)object).serialString());
        stringBuilder.append("> ");
        stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
        this.riljLog(stringBuilder.toString());
        try {
            iRadio.stopKeepalive(((RILRequest)object).mSerial, n);
        }
        catch (RemoteException | RuntimeException throwable) {
            this.handleRadioProxyExceptionForRR((RILRequest)object, "stopNattKeepalive", (Exception)throwable);
        }
    }

    @Override
    public void stopNetworkScan(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            if (this.mRadioVersion.greaterOrEqual(RADIO_HAL_VERSION_1_1)) {
                iRadio = (android.hardware.radio.V1_1.IRadio)iRadio;
                object = this.obtainRequest(143, (Message)object, this.mRILDefaultWorkSource);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object).serialString());
                stringBuilder.append("> ");
                stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                this.riljLog(stringBuilder.toString());
                try {
                    iRadio.stopNetworkScan(((RILRequest)object).mSerial);
                }
                catch (RemoteException | RuntimeException throwable) {
                    this.handleRadioProxyExceptionForRR((RILRequest)object, "stopNetworkScan", (Exception)throwable);
                }
            } else if (object != null) {
                AsyncResult.forMessage((Message)object, null, (Throwable)CommandException.fromRilErrno(6));
                object.sendToTarget();
            }
        }
    }

    @Override
    public void supplyIccPin(String string, Message message) {
        this.supplyIccPinForApp(string, null, message);
    }

    @Override
    public void supplyIccPin2(String string, Message message) {
        this.supplyIccPin2ForApp(string, null, message);
    }

    @Override
    public void supplyIccPin2ForApp(String string, String string2, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(4, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" aid = ");
            stringBuilder.append(string2);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.supplyIccPin2ForApp(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), RIL.convertNullToEmptyString(string2));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "supplyIccPin2ForApp", (Exception)throwable);
            }
        }
    }

    @Override
    public void supplyIccPinForApp(String string, String string2, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(2, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" aid = ");
            stringBuilder.append(string2);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.supplyIccPinForApp(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), RIL.convertNullToEmptyString(string2));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "supplyIccPinForApp", (Exception)throwable);
            }
        }
    }

    @Override
    public void supplyIccPuk(String string, String string2, Message message) {
        this.supplyIccPukForApp(string, string2, null, message);
    }

    @Override
    public void supplyIccPuk2(String string, String string2, Message message) {
        this.supplyIccPuk2ForApp(string, string2, null, message);
    }

    @Override
    public void supplyIccPuk2ForApp(String string, String string2, String string3, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(5, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" aid = ");
            stringBuilder.append(string3);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.supplyIccPuk2ForApp(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), RIL.convertNullToEmptyString(string2), RIL.convertNullToEmptyString(string3));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "supplyIccPuk2ForApp", (Exception)throwable);
            }
        }
    }

    @Override
    public void supplyIccPukForApp(String string, String string2, String string3, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(3, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" aid = ");
            stringBuilder.append(string3);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.supplyIccPukForApp(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string), RIL.convertNullToEmptyString(string2), RIL.convertNullToEmptyString(string3));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "supplyIccPukForApp", (Exception)throwable);
            }
        }
    }

    @Override
    public void supplyNetworkDepersonalization(String string, Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(8, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            stringBuilder.append(" netpin = ");
            stringBuilder.append(string);
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.supplyNetworkDepersonalization(((RILRequest)object).mSerial, RIL.convertNullToEmptyString(string));
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "supplyNetworkDepersonalization", (Exception)throwable);
            }
        }
    }

    @Override
    public void switchWaitingOrHoldingAndActive(Message object) {
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(15, (Message)object, this.mRILDefaultWorkSource);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((RILRequest)object).serialString());
            stringBuilder.append("> ");
            stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
            this.riljLog(stringBuilder.toString());
            try {
                iRadio.switchWaitingOrHoldingAndActive(((RILRequest)object).mSerial);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "switchWaitingOrHoldingAndActive", (Exception)throwable);
            }
        }
    }

    @Override
    public void testingEmergencyCall() {
        this.riljLog("testingEmergencyCall");
        this.mTestingEmergencyCall.set(true);
    }

    @UnsupportedAppUsage
    void unsljLog(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[UNSL]< ");
        stringBuilder.append(RIL.responseToString(n));
        this.riljLog(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    void unsljLogMore(int n, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[UNSL]< ");
        stringBuilder.append(RIL.responseToString(n));
        stringBuilder.append(" ");
        stringBuilder.append(string);
        this.riljLog(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    void unsljLogRet(int n, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[UNSL]< ");
        stringBuilder.append(RIL.responseToString(n));
        stringBuilder.append(" ");
        stringBuilder.append(RIL.retToString(n, object));
        this.riljLog(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    void unsljLogvRet(int n, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[UNSL]< ");
        stringBuilder.append(RIL.responseToString(n));
        stringBuilder.append(" ");
        stringBuilder.append(RIL.retToString(n, object));
        this.riljLogv(stringBuilder.toString());
    }

    void writeMetricsCallRing(char[] arrc) {
        this.mMetrics.writeRilCallRing(this.mPhoneId, arrc);
    }

    void writeMetricsModemRestartEvent(String string) {
        this.mMetrics.writeModemRestartEvent(this.mPhoneId, string);
    }

    void writeMetricsSrvcc(int n) {
        this.mMetrics.writeRilSrvcc(this.mPhoneId, n);
    }

    @Override
    public void writeSmsToRuim(int n, String string, Message object) {
        n = this.translateStatus(n);
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(96, (Message)object, this.mRILDefaultWorkSource);
            CdmaSmsWriteArgs cdmaSmsWriteArgs = new CdmaSmsWriteArgs();
            cdmaSmsWriteArgs.status = n;
            this.constructCdmaSendSmsRilRequest(cdmaSmsWriteArgs.message, string.getBytes());
            try {
                iRadio.writeSmsToRuim(object.mSerial, cdmaSmsWriteArgs);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "writeSmsToRuim", (Exception)throwable);
            }
        }
    }

    @Override
    public void writeSmsToSim(int n, String string, String string2, Message object) {
        n = this.translateStatus(n);
        android.hardware.radio.V1_0.IRadio iRadio = this.getRadioProxy((Message)object);
        if (iRadio != null) {
            object = this.obtainRequest(63, (Message)object, this.mRILDefaultWorkSource);
            SmsWriteArgs smsWriteArgs = new SmsWriteArgs();
            smsWriteArgs.status = n;
            smsWriteArgs.smsc = RIL.convertNullToEmptyString(string);
            smsWriteArgs.pdu = RIL.convertNullToEmptyString(string2);
            try {
                iRadio.writeSmsToSim(object.mSerial, smsWriteArgs);
            }
            catch (RemoteException | RuntimeException throwable) {
                this.handleRadioProxyExceptionForRR((RILRequest)object, "writeSmsToSim", (Exception)throwable);
            }
        }
    }

    final class RadioProxyDeathRecipient
    implements IHwBinder.DeathRecipient {
        RadioProxyDeathRecipient() {
        }

        public void serviceDied(long l) {
            RIL.this.riljLog("serviceDied");
            RIL.this.mRilHandler.sendMessage(RIL.this.mRilHandler.obtainMessage(6, (Object)l));
        }
    }

    @VisibleForTesting
    public class RilHandler
    extends Handler {
        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n != 2) {
                if (n == 4) {
                    if (((Message)object).arg1 != RIL.this.mAckWlSequenceNum) return;
                    RIL.this.clearWakeLock(1);
                    return;
                }
                if (n != 5) {
                    if (n != 6) {
                        return;
                    }
                    RIL rIL = RIL.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("handleMessage: EVENT_RADIO_PROXY_DEAD cookie = ");
                    stringBuilder.append(((Message)object).obj);
                    stringBuilder.append(" mRadioProxyCookie = ");
                    stringBuilder.append(RIL.this.mRadioProxyCookie.get());
                    rIL.riljLog(stringBuilder.toString());
                    if (((Long)((Message)object).obj).longValue() != RIL.this.mRadioProxyCookie.get()) return;
                    RIL.this.resetProxyAndRequestList();
                    return;
                }
                n = ((Message)object).arg1;
                object = RIL.this.findAndRemoveRequestFromList(n);
                if (object == null) {
                    return;
                }
                if (((RILRequest)object).mResult != null) {
                    Object object2 = RIL.getResponseForTimedOutRILRequest((RILRequest)object);
                    AsyncResult.forMessage((Message)((RILRequest)object).mResult, (Object)object2, null);
                    ((RILRequest)object).mResult.sendToTarget();
                    RIL.this.mMetrics.writeOnRilTimeoutResponse(RIL.this.mPhoneId, ((RILRequest)object).mSerial, ((RILRequest)object).mRequest);
                }
                RIL.this.decrementWakeLock((RILRequest)object);
                ((RILRequest)object).release();
                return;
            }
            SparseArray<RILRequest> sparseArray = RIL.this.mRequestList;
            synchronized (sparseArray) {
                if (((Message)object).arg1 != RIL.this.mWlSequenceNum) return;
                if (!RIL.this.clearWakeLock(0)) return;
                if (RIL.this.mRadioBugDetector != null) {
                    RIL.this.mRadioBugDetector.processWakelockTimeout();
                }
                int n2 = RIL.this.mRequestList.size();
                object = new StringBuilder();
                ((StringBuilder)object).append("WAKE_LOCK_TIMEOUT  mRequestList=");
                ((StringBuilder)object).append(n2);
                Rlog.d((String)RIL.RILJ_LOG_TAG, (String)((StringBuilder)object).toString());
                n = 0;
                while (n < n2) {
                    object = (RILRequest)RIL.this.mRequestList.valueAt(n);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(n);
                    stringBuilder.append(": [");
                    stringBuilder.append(((RILRequest)object).mSerial);
                    stringBuilder.append("] ");
                    stringBuilder.append(RIL.requestToString(((RILRequest)object).mRequest));
                    Rlog.d((String)RIL.RILJ_LOG_TAG, (String)stringBuilder.toString());
                    ++n;
                }
                return;
            }
        }
    }

}

