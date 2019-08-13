/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.HandlerExecutor
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.PersistableBundle
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.os.SystemProperties
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.telecom.TelecomManager
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.telephony.ims.ImsCallProfile
 *  android.telephony.ims.ImsCallSession
 *  android.telephony.ims.ImsMmTelManager
 *  android.telephony.ims.ImsMmTelManager$CapabilityCallback
 *  android.telephony.ims.ImsMmTelManager$RegistrationCallback
 *  android.telephony.ims.ImsReasonInfo
 *  android.telephony.ims.aidl.IImsCapabilityCallback
 *  android.telephony.ims.aidl.IImsCapabilityCallback$Stub
 *  android.telephony.ims.aidl.IImsConfig
 *  android.telephony.ims.aidl.IImsConfigCallback
 *  android.telephony.ims.aidl.IImsRegistrationCallback
 *  android.telephony.ims.aidl.IImsSmsListener
 *  android.telephony.ims.feature.CapabilityChangeRequest
 *  android.telephony.ims.feature.CapabilityChangeRequest$CapabilityPair
 *  android.telephony.ims.feature.MmTelFeature
 *  android.telephony.ims.feature.MmTelFeature$Listener
 *  android.telephony.ims.feature.MmTelFeature$MmTelCapabilities
 *  android.util.Log
 *  com.android.ims.-$
 *  com.android.ims.-$$Lambda
 *  com.android.ims.-$$Lambda$ImsManager
 *  com.android.ims.-$$Lambda$ImsManager$Flxe43OUFnnU0pgnksvwPE6o3Mk
 *  com.android.ims.-$$Lambda$ImsManager$YhRaDrc3t9_7beNiU5gQcqZilOw
 *  com.android.ims.-$$Lambda$VPAygt3Y-cyud4AweDbrpru2LJ8
 *  com.android.ims.-$$Lambda$a4IO_gY853vtN_bjQR9bZYk4Js0
 *  com.android.ims.ImsConfig
 *  com.android.ims.ImsConfigListener
 *  com.android.ims.ImsException
 *  com.android.ims.ImsUtInterface
 *  com.android.ims.internal.IImsCallSession
 *  com.android.ims.internal.IImsEcbm
 *  com.android.ims.internal.IImsMultiEndpoint
 *  com.android.ims.internal.IImsUt
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.ITelephony
 *  com.android.internal.telephony.ITelephony$Stub
 */
package com.android.ims;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsCallSession;
import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.aidl.IImsSmsListener;
import android.telephony.ims.feature.CapabilityChangeRequest;
import android.telephony.ims.feature.MmTelFeature;
import android.util.Log;
import com.android.ims.-$;
import com.android.ims.ImsCall;
import com.android.ims.ImsConfig;
import com.android.ims.ImsConfigListener;
import com.android.ims.ImsConnectionStateListener;
import com.android.ims.ImsEcbm;
import com.android.ims.ImsException;
import com.android.ims.ImsMultiEndpoint;
import com.android.ims.ImsUt;
import com.android.ims.ImsUtInterface;
import com.android.ims.MmTelFeatureConnection;
import com.android.ims._$$Lambda$ImsManager$Connector$1$QkUK3GnYms22eckyg3OL_BmtP3M;
import com.android.ims._$$Lambda$ImsManager$Connector$1$noNC6hbyVe0dHnOoOYgo9PyTSxw;
import com.android.ims._$$Lambda$ImsManager$Connector$N5r1SvOgM0jfHDwKkcQbyw_uTP0;
import com.android.ims._$$Lambda$ImsManager$Connector$yM9scWJWjDp_h0yrkCgrjFZH5oI;
import com.android.ims._$$Lambda$ImsManager$D1JuJ3ba2jMHWDKlSpm03meBR1c;
import com.android.ims._$$Lambda$ImsManager$Flxe43OUFnnU0pgnksvwPE6o3Mk;
import com.android.ims._$$Lambda$ImsManager$LiW49wt0wLMYHjgtAwL8NLIATfs;
import com.android.ims._$$Lambda$ImsManager$YhRaDrc3t9_7beNiU5gQcqZilOw;
import com.android.ims._$$Lambda$ImsManager$_6YCQyhjHBSdrm4ZBEMUQ2AAqOY;
import com.android.ims._$$Lambda$VPAygt3Y_cyud4AweDbrpru2LJ8;
import com.android.ims._$$Lambda$a4IO_gY853vtN_bjQR9bZYk4Js0;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsUt;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.ITelephony;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ImsManager {
    public static final String ACTION_IMS_INCOMING_CALL = "com.android.ims.IMS_INCOMING_CALL";
    public static final String ACTION_IMS_REGISTRATION_ERROR = "com.android.ims.REGISTRATION_ERROR";
    public static final String ACTION_IMS_SERVICE_DOWN = "com.android.ims.IMS_SERVICE_DOWN";
    public static final String ACTION_IMS_SERVICE_UP = "com.android.ims.IMS_SERVICE_UP";
    private static final boolean DBG = true;
    public static final String EXTRA_CALL_ID = "android:imsCallID";
    public static final String EXTRA_IS_UNKNOWN_CALL = "android:isUnknown";
    public static final String EXTRA_PHONE_ID = "android:phone_id";
    public static final String EXTRA_SERVICE_ID = "android:imsServiceId";
    public static final String EXTRA_USSD = "android:ussd";
    public static final String FALSE = "false";
    public static final int INCOMING_CALL_RESULT_CODE = 101;
    private static final int MAX_RECENT_DISCONNECT_REASONS = 16;
    public static final String PROPERTY_DBG_ALLOW_IMS_OFF_OVERRIDE = "persist.dbg.allow_ims_off";
    public static final int PROPERTY_DBG_ALLOW_IMS_OFF_OVERRIDE_DEFAULT = 0;
    public static final String PROPERTY_DBG_VOLTE_AVAIL_OVERRIDE = "persist.dbg.volte_avail_ovr";
    public static final int PROPERTY_DBG_VOLTE_AVAIL_OVERRIDE_DEFAULT = 0;
    public static final String PROPERTY_DBG_VT_AVAIL_OVERRIDE = "persist.dbg.vt_avail_ovr";
    public static final int PROPERTY_DBG_VT_AVAIL_OVERRIDE_DEFAULT = 0;
    public static final String PROPERTY_DBG_WFC_AVAIL_OVERRIDE = "persist.dbg.wfc_avail_ovr";
    public static final int PROPERTY_DBG_WFC_AVAIL_OVERRIDE_DEFAULT = 0;
    private static final int RESPONSE_WAIT_TIME_MS = 3000;
    private static final int SUB_PROPERTY_NOT_INITIALIZED = -1;
    private static final int SYSTEM_PROPERTY_NOT_SET = -1;
    private static final String TAG = "ImsManager";
    public static final String TRUE = "true";
    private static HashMap<Integer, ImsManager> sImsManagerInstances = new HashMap();
    private final boolean mConfigDynamicBind;
    private CarrierConfigManager mConfigManager;
    private boolean mConfigUpdated = false;
    private Context mContext;
    private ImsEcbm mEcbm = null;
    @VisibleForTesting
    public ExecutorFactory mExecutorFactory = _$$Lambda$ImsManager$Flxe43OUFnnU0pgnksvwPE6o3Mk.INSTANCE;
    private ImsConfigListener mImsConfigListener;
    private MmTelFeatureConnection mMmTelFeatureConnection = null;
    private ImsMultiEndpoint mMultiEndpoint = null;
    private int mPhoneId;
    private ConcurrentLinkedDeque<ImsReasonInfo> mRecentDisconnectReasons = new ConcurrentLinkedDeque();
    private Set<MmTelFeatureConnection.IFeatureUpdate> mStatusCallbacks = new CopyOnWriteArraySet<MmTelFeatureConnection.IFeatureUpdate>();
    private ImsUt mUt = null;

    @VisibleForTesting
    public ImsManager(Context context, int n) {
        this.mContext = context;
        this.mPhoneId = n;
        this.mConfigDynamicBind = this.mContext.getResources().getBoolean(17891428);
        this.mConfigManager = (CarrierConfigManager)context.getSystemService("carrier_config");
        this.createImsService();
    }

    private void addToRecentDisconnectReasons(ImsReasonInfo imsReasonInfo) {
        if (imsReasonInfo == null) {
            return;
        }
        while (this.mRecentDisconnectReasons.size() >= 16) {
            this.mRecentDisconnectReasons.removeFirst();
        }
        this.mRecentDisconnectReasons.addLast(imsReasonInfo);
    }

    private static String booleanToPropertyString(boolean bl) {
        String string = bl ? "1" : "0";
        return string;
    }

    private void checkAndThrowExceptionIfServiceUnavailable() throws ImsException {
        block2 : {
            block4 : {
                block3 : {
                    if (!ImsManager.isImsSupportedOnDevice(this.mContext)) break block2;
                    MmTelFeatureConnection mmTelFeatureConnection = this.mMmTelFeatureConnection;
                    if (mmTelFeatureConnection != null && mmTelFeatureConnection.isBinderAlive()) break block3;
                    this.createImsService();
                    if (this.mMmTelFeatureConnection == null) break block4;
                }
                return;
            }
            throw new ImsException("Service is unavailable", 106);
        }
        throw new ImsException("IMS not supported on device.", 150);
    }

    private ImsCallSession createCallSession(ImsCallProfile imsCallProfile) throws ImsException {
        try {
            imsCallProfile = new ImsCallSession(this.mMmTelFeatureConnection.createCallSession(imsCallProfile));
            return imsCallProfile;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CreateCallSession: Error, remote exception: ");
            stringBuilder.append(remoteException.getMessage());
            Rlog.w((String)TAG, (String)stringBuilder.toString());
            throw new ImsException("createCallSession()", (Throwable)remoteException, 106);
        }
    }

    private void createImsService() {
        this.mMmTelFeatureConnection = MmTelFeatureConnection.create(this.mContext, this.mPhoneId);
        this.mMmTelFeatureConnection.setStatusCallback(new MmTelFeatureConnection.IFeatureUpdate(){

            @Override
            public void notifyStateChanged() {
                ImsManager.this.mStatusCallbacks.forEach(_$$Lambda$a4IO_gY853vtN_bjQR9bZYk4Js0.INSTANCE);
            }

            @Override
            public void notifyUnavailable() {
                ImsManager.this.mStatusCallbacks.forEach(_$$Lambda$VPAygt3Y_cyud4AweDbrpru2LJ8.INSTANCE);
            }
        });
    }

    public static void factoryReset(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            ((ImsManager)object).factoryReset();
        }
        ImsManager.loge("factoryReset: ImsManager null.");
    }

    private boolean getBooleanCarrierConfig(String string) {
        PersistableBundle persistableBundle = null;
        CarrierConfigManager carrierConfigManager = this.mConfigManager;
        if (carrierConfigManager != null) {
            persistableBundle = carrierConfigManager.getConfigForSubId(this.getSubId());
        }
        if (persistableBundle != null) {
            return persistableBundle.getBoolean(string);
        }
        return CarrierConfigManager.getDefaultConfig().getBoolean(string);
    }

    private static String getCallId(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return bundle.getString(EXTRA_CALL_ID);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ImsManager getInstance(Context object, int n) {
        HashMap<Integer, ImsManager> hashMap = sImsManagerInstances;
        synchronized (hashMap) {
            if (!sImsManagerInstances.containsKey(n)) {
                ImsManager imsManager = new ImsManager((Context)object, n);
                sImsManagerInstances.put(n, imsManager);
                return imsManager;
            }
            object = sImsManagerInstances.get(n);
            if (object != null) {
                ((ImsManager)object).connectIfServiceIsAvailable();
            }
            return object;
        }
    }

    private int getIntCarrierConfig(String string) {
        PersistableBundle persistableBundle = null;
        CarrierConfigManager carrierConfigManager = this.mConfigManager;
        if (carrierConfigManager != null) {
            persistableBundle = carrierConfigManager.getConfigForSubId(this.getSubId());
        }
        if (persistableBundle != null) {
            return persistableBundle.getInt(string);
        }
        return CarrierConfigManager.getDefaultConfig().getInt(string);
    }

    private boolean getProvisionedBool(ImsConfig object, int n) throws ImsException {
        int n2 = object.getProvisionedValue(n);
        if (n2 != -1) {
            boolean bl = true;
            if (n2 != 1) {
                bl = false;
            }
            return bl;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("getProvisionedBool failed with error for item: ");
        ((StringBuilder)object).append(n);
        throw new ImsException(((StringBuilder)object).toString(), 103);
    }

    private boolean getProvisionedBoolNoException(int n) {
        try {
            boolean bl = this.getProvisionedBool(this.getConfigInterface(), n);
            return bl;
        }
        catch (ImsException imsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getProvisionedBoolNoException: operation failed for item=");
            stringBuilder.append(n);
            stringBuilder.append(". Exception:");
            stringBuilder.append(imsException.getMessage());
            stringBuilder.append(". Returning false.");
            Log.w((String)TAG, (String)stringBuilder.toString());
            return false;
        }
    }

    private int getSettingFromSubscriptionManager(String string, String string2) {
        int n;
        int n2 = n = SubscriptionManager.getIntegerSubscriptionProperty((int)this.getSubId(), (String)string, (int)-1, (Context)this.mContext);
        if (n == -1) {
            n2 = this.getIntCarrierConfig(string2);
        }
        return n2;
    }

    private int getSubId() {
        int n;
        int[] arrn = SubscriptionManager.getSubId((int)this.mPhoneId);
        int n2 = n = -1;
        if (arrn != null) {
            n2 = n;
            if (arrn.length >= 1) {
                n2 = arrn[0];
            }
        }
        return n2;
    }

    private Executor getThreadExecutor() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        return new HandlerExecutor(new Handler(Looper.myLooper()));
    }

    public static int getWfcMode(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).getWfcMode();
        }
        ImsManager.loge("getWfcMode: ImsManager null, returning default value.");
        return 0;
    }

    public static int getWfcMode(Context object, boolean bl) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).getWfcMode(bl);
        }
        ImsManager.loge("getWfcMode: ImsManager null, returning default value.");
        return 0;
    }

    private boolean isDataEnabled() {
        return new TelephonyManager(this.mContext, this.getSubId()).isDataCapable();
    }

    public static boolean isEnhanced4gLteModeSettingEnabledByUser(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isEnhanced4gLteModeSettingEnabledByUser();
        }
        ImsManager.loge("isEnhanced4gLteModeSettingEnabledByUser: ImsManager null, returning default value.");
        return false;
    }

    private boolean isGbaValid() {
        boolean bl = this.getBooleanCarrierConfig("carrier_ims_gba_required_bool");
        boolean bl2 = true;
        if (bl) {
            String string = new TelephonyManager(this.mContext, this.getSubId()).getIsimIst();
            if (string == null) {
                ImsManager.loge("isGbaValid - ISF is NULL");
                return true;
            }
            if (string.length() <= 1 || ((byte)string.charAt(1) & 2) == 0) {
                bl2 = false;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isGbaValid - GBA capable=");
            stringBuilder.append(bl2);
            stringBuilder.append(", ISF=");
            stringBuilder.append(string);
            ImsManager.log(stringBuilder.toString());
            return bl2;
        }
        return true;
    }

    private boolean isImsNeeded(CapabilityChangeRequest capabilityChangeRequest) {
        return capabilityChangeRequest.getCapabilitiesToEnable().stream().anyMatch(_$$Lambda$ImsManager$YhRaDrc3t9_7beNiU5gQcqZilOw.INSTANCE);
    }

    public static boolean isImsSupportedOnDevice(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.telephony.ims");
    }

    private boolean isImsTurnOffAllowed() {
        boolean bl = this.isTurnOffImsAllowedByPlatform() && (!this.isWfcEnabledByPlatform() || !this.isWfcEnabledByUser());
        return bl;
    }

    public static boolean isNonTtyOrTtyOnVolteEnabled(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isNonTtyOrTtyOnVolteEnabled();
        }
        ImsManager.loge("isNonTtyOrTtyOnVolteEnabled: ImsManager null, returning default value.");
        return false;
    }

    private boolean isSubIdValid(int n) {
        boolean bl = SubscriptionManager.isValidSubscriptionId((int)n) && n != Integer.MAX_VALUE;
        return bl;
    }

    private boolean isTurnOffImsAllowedByPlatform() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PROPERTY_DBG_ALLOW_IMS_OFF_OVERRIDE);
        stringBuilder.append(Integer.toString(this.mPhoneId));
        if (SystemProperties.getInt((String)stringBuilder.toString(), (int)-1) != 1 && SystemProperties.getInt((String)PROPERTY_DBG_ALLOW_IMS_OFF_OVERRIDE, (int)-1) != 1) {
            return this.getBooleanCarrierConfig("carrier_allow_turnoff_ims_bool");
        }
        return true;
    }

    private static boolean isTurnOffImsAllowedByPlatform(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ImsManager.super.isTurnOffImsAllowedByPlatform();
        }
        ImsManager.loge("isTurnOffImsAllowedByPlatform: ImsManager null, returning default value.");
        return true;
    }

    public static boolean isVolteEnabledByPlatform(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isVolteEnabledByPlatform();
        }
        ImsManager.loge("isVolteEnabledByPlatform: ImsManager null, returning default value.");
        return false;
    }

    private boolean isVolteProvisioned() {
        return this.getProvisionedBoolNoException(10);
    }

    public static boolean isVolteProvisionedOnDevice(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isVolteProvisionedOnDevice();
        }
        ImsManager.loge("isVolteProvisionedOnDevice: ImsManager null, returning default value.");
        return true;
    }

    public static boolean isVtEnabledByPlatform(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isVtEnabledByPlatform();
        }
        ImsManager.loge("isVtEnabledByPlatform: ImsManager null, returning default value.");
        return false;
    }

    public static boolean isVtEnabledByUser(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isVtEnabledByUser();
        }
        ImsManager.loge("isVtEnabledByUser: ImsManager null, returning default value.");
        return false;
    }

    private boolean isVtProvisioned() {
        return this.getProvisionedBoolNoException(11);
    }

    public static boolean isVtProvisionedOnDevice(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isVtProvisionedOnDevice();
        }
        ImsManager.loge("isVtProvisionedOnDevice: ImsManager null, returning default value.");
        return true;
    }

    public static boolean isWfcEnabledByPlatform(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isWfcEnabledByPlatform();
        }
        ImsManager.loge("isWfcEnabledByPlatform: ImsManager null, returning default value.");
        return false;
    }

    public static boolean isWfcEnabledByUser(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isWfcEnabledByUser();
        }
        ImsManager.loge("isWfcEnabledByUser: ImsManager null, returning default value.");
        return true;
    }

    private boolean isWfcProvisioned() {
        return this.getProvisionedBoolNoException(28);
    }

    public static boolean isWfcProvisionedOnDevice(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isWfcProvisionedOnDevice();
        }
        ImsManager.loge("isWfcProvisionedOnDevice: ImsManager null, returning default value.");
        return true;
    }

    public static boolean isWfcRoamingEnabledByUser(Context object) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            return ((ImsManager)object).isWfcRoamingEnabledByUser();
        }
        ImsManager.loge("isWfcRoamingEnabledByUser: ImsManager null, returning default value.");
        return false;
    }

    static /* synthetic */ boolean lambda$isImsNeeded$3(CapabilityChangeRequest.CapabilityPair capabilityPair) {
        boolean bl = capabilityPair.getCapability() != 4;
        return bl;
    }

    static /* synthetic */ void lambda$new$0(Runnable runnable) {
        new Thread(runnable).start();
    }

    private static void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private static void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    private static void loge(String string, Throwable throwable) {
        Rlog.e((String)TAG, (String)string, (Throwable)throwable);
    }

    private ImsReasonInfo makeACopy(ImsReasonInfo imsReasonInfo) {
        Parcel parcel = Parcel.obtain();
        imsReasonInfo.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        imsReasonInfo = (ImsReasonInfo)ImsReasonInfo.CREATOR.createFromParcel(parcel);
        parcel.recycle();
        return imsReasonInfo;
    }

    private void setAdvanced4GMode(boolean bl) throws ImsException {
        this.checkAndThrowExceptionIfServiceUnavailable();
        if (bl) {
            this.setLteFeatureValues(bl);
            ImsManager.log("setAdvanced4GMode: turnOnIms");
            this.turnOnIms();
        } else {
            if (this.isImsTurnOffAllowed()) {
                ImsManager.log("setAdvanced4GMode: turnOffIms");
                this.turnOffIms();
            }
            this.setLteFeatureValues(bl);
        }
    }

    public static void setEnhanced4gLteModeSetting(Context object, boolean bl) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            ((ImsManager)object).setEnhanced4gLteModeSetting(bl);
        }
        ImsManager.loge("setEnhanced4gLteModeSetting: ImsManager null, value not set.");
    }

    private void setLteFeatureValues(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setLteFeatureValues: ");
        stringBuilder.append(bl);
        ImsManager.log(stringBuilder.toString());
        stringBuilder = new CapabilityChangeRequest();
        boolean bl2 = true;
        if (bl) {
            stringBuilder.addCapabilitiesToEnableForTech(1, 0);
        } else {
            stringBuilder.addCapabilitiesToDisableForTech(1, 0);
        }
        if (this.isVtEnabledByPlatform()) {
            boolean bl3 = this.getBooleanCarrierConfig("ignore_data_enabled_changed_for_video_calls");
            if (!bl || !this.isVtEnabledByUser() || !bl3 && !this.isDataEnabled()) {
                bl2 = false;
            }
            if (bl2) {
                stringBuilder.addCapabilitiesToEnableForTech(2, 0);
            } else {
                stringBuilder.addCapabilitiesToDisableForTech(2, 0);
            }
        }
        try {
            this.mMmTelFeatureConnection.changeEnabledCapabilities((CapabilityChangeRequest)stringBuilder, null);
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("setLteFeatureValues: Exception: ");
            stringBuilder2.append(remoteException.getMessage());
            Log.e((String)TAG, (String)stringBuilder2.toString());
        }
    }

    private void setProvisionedBool(ImsConfig object, int n, int n2) throws ImsException {
        if (object.setConfig(n, n2) == 0) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("setProvisionedBool failed with error for item: ");
        ((StringBuilder)object).append(n);
        throw new ImsException(((StringBuilder)object).toString(), 103);
    }

    private boolean setProvisionedBoolNoException(int n, int n2) {
        try {
            this.setProvisionedBool(this.getConfigInterface(), n, n2);
            return true;
        }
        catch (ImsException imsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setProvisionedBoolNoException: operation failed for item=");
            stringBuilder.append(n);
            stringBuilder.append(", value=");
            stringBuilder.append(n2);
            stringBuilder.append(". Exception:");
            stringBuilder.append(imsException.getMessage());
            Log.w((String)TAG, (String)stringBuilder.toString());
            return false;
        }
    }

    private void setRttConfig(boolean bl) {
        int n = bl ? 1 : 0;
        this.mExecutorFactory.executeRunnable(new _$$Lambda$ImsManager$_6YCQyhjHBSdrm4ZBEMUQ2AAqOY(this, bl, n));
    }

    public static void setVtSetting(Context object, boolean bl) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            ((ImsManager)object).setVtSetting(bl);
        }
        ImsManager.loge("setVtSetting: ImsManager null, can not set value.");
    }

    public static void setWfcMode(Context object, int n) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            ((ImsManager)object).setWfcMode(n);
        }
        ImsManager.loge("setWfcMode: ImsManager null, can not set value.");
    }

    public static void setWfcMode(Context object, int n, boolean bl) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            ((ImsManager)object).setWfcMode(n, bl);
        }
        ImsManager.loge("setWfcMode: ImsManager null, can not set value.");
    }

    private void setWfcModeInternal(int n) {
        this.mExecutorFactory.executeRunnable(new _$$Lambda$ImsManager$LiW49wt0wLMYHjgtAwL8NLIATfs(this, n));
    }

    public static void setWfcRoamingSetting(Context object, boolean bl) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            ((ImsManager)object).setWfcRoamingSetting(bl);
        }
        ImsManager.loge("setWfcRoamingSetting: ImsManager null, value not set.");
    }

    private void setWfcRoamingSettingInternal(boolean bl) {
        int n = bl ? 1 : 0;
        this.mExecutorFactory.executeRunnable(new _$$Lambda$ImsManager$D1JuJ3ba2jMHWDKlSpm03meBR1c(this, n));
    }

    public static void setWfcSetting(Context object, boolean bl) {
        if ((object = ImsManager.getInstance((Context)object, SubscriptionManager.getDefaultVoicePhoneId())) != null) {
            ((ImsManager)object).setWfcSetting(bl);
        }
        ImsManager.loge("setWfcSetting: ImsManager null, can not set value.");
    }

    private void turnOffIms() throws ImsException {
        ((TelephonyManager)this.mContext.getSystemService("phone")).disableIms(this.mPhoneId);
    }

    private void turnOnIms() throws ImsException {
        ((TelephonyManager)this.mContext.getSystemService("phone")).enableIms(this.mPhoneId);
    }

    public static void updateImsServiceConfig(Context object, int n, boolean bl) {
        if ((object = ImsManager.getInstance((Context)object, n)) != null) {
            ((ImsManager)object).updateImsServiceConfig(bl);
        }
        ImsManager.loge("updateImsServiceConfig: ImsManager null, returning without update.");
    }

    private void updateUtFeatureValue(CapabilityChangeRequest capabilityChangeRequest) {
        boolean bl;
        Object object;
        boolean bl2 = this.isSuppServicesOverUtEnabledByPlatform();
        boolean bl3 = this.getBooleanCarrierConfig("carrier_ut_provisioning_required_bool");
        boolean bl4 = bl = true;
        if (bl3) {
            object = ITelephony.Stub.asInterface((IBinder)ServiceManager.getService((String)"phone"));
            bl4 = bl;
            if (object != null) {
                try {
                    bl4 = object.isMmTelCapabilityProvisionedInCache(this.getSubId(), 4, 0);
                }
                catch (RemoteException remoteException) {
                    Log.e((String)TAG, (String)"updateUtFeatureValue: couldn't reach telephony! returning provisioned");
                    bl4 = bl;
                }
            }
        }
        bl = bl2 && bl4;
        object = new StringBuilder();
        ((StringBuilder)object).append("updateUtFeatureValue: available = ");
        ((StringBuilder)object).append(bl2);
        ((StringBuilder)object).append(", isProvisioned = ");
        ((StringBuilder)object).append(bl4);
        ((StringBuilder)object).append(", enabled = ");
        ((StringBuilder)object).append(bl);
        ImsManager.log(((StringBuilder)object).toString());
        if (bl) {
            capabilityChangeRequest.addCapabilitiesToEnableForTech(4, 0);
        } else {
            capabilityChangeRequest.addCapabilitiesToDisableForTech(4, 0);
        }
    }

    private void updateVideoCallFeatureValue(CapabilityChangeRequest capabilityChangeRequest) {
        boolean bl = this.isVtEnabledByPlatform();
        boolean bl2 = this.isVtEnabledByUser();
        boolean bl3 = this.isNonTtyOrTtyOnVolteEnabled();
        boolean bl4 = this.isDataEnabled();
        boolean bl5 = this.getBooleanCarrierConfig("ignore_data_enabled_changed_for_video_calls");
        boolean bl6 = bl && bl2 && bl3 && (bl5 || bl4);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateVideoCallFeatureValue: available = ");
        stringBuilder.append(bl);
        stringBuilder.append(", enabled = ");
        stringBuilder.append(bl2);
        stringBuilder.append(", nonTTY = ");
        stringBuilder.append(bl3);
        stringBuilder.append(", data enabled = ");
        stringBuilder.append(bl4);
        ImsManager.log(stringBuilder.toString());
        if (bl6) {
            capabilityChangeRequest.addCapabilitiesToEnableForTech(2, 0);
        } else {
            capabilityChangeRequest.addCapabilitiesToDisableForTech(2, 0);
        }
    }

    private void updateVolteFeatureValue(CapabilityChangeRequest capabilityChangeRequest) {
        boolean bl = this.isVolteEnabledByPlatform();
        boolean bl2 = this.isEnhanced4gLteModeSettingEnabledByUser();
        boolean bl3 = this.isNonTtyOrTtyOnVolteEnabled();
        boolean bl4 = bl && bl2 && bl3;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateVolteFeatureValue: available = ");
        stringBuilder.append(bl);
        stringBuilder.append(", enabled = ");
        stringBuilder.append(bl2);
        stringBuilder.append(", nonTTY = ");
        stringBuilder.append(bl3);
        ImsManager.log(stringBuilder.toString());
        if (bl4) {
            capabilityChangeRequest.addCapabilitiesToEnableForTech(1, 0);
        } else {
            capabilityChangeRequest.addCapabilitiesToDisableForTech(1, 0);
        }
    }

    private void updateWfcFeatureAndProvisionedValues(CapabilityChangeRequest capabilityChangeRequest) {
        boolean bl = new TelephonyManager(this.mContext, this.getSubId()).isNetworkRoaming();
        boolean bl2 = this.isWfcEnabledByPlatform();
        boolean bl3 = this.isWfcEnabledByUser();
        int n = this.getWfcMode(bl);
        bl = this.isWfcRoamingEnabledByUser();
        boolean bl4 = bl2 && bl3;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateWfcFeatureAndProvisionedValues: available = ");
        stringBuilder.append(bl2);
        stringBuilder.append(", enabled = ");
        stringBuilder.append(bl3);
        stringBuilder.append(", mode = ");
        stringBuilder.append(n);
        stringBuilder.append(", roaming = ");
        stringBuilder.append(bl);
        ImsManager.log(stringBuilder.toString());
        if (bl4) {
            capabilityChangeRequest.addCapabilitiesToEnableForTech(1, 1);
        } else {
            capabilityChangeRequest.addCapabilitiesToDisableForTech(1, 1);
        }
        if (!bl4) {
            n = 1;
            bl = false;
        }
        this.setWfcModeInternal(n);
        this.setWfcRoamingSettingInternal(bl);
    }

    public void acknowledgeSms(int n, int n2, int n3) throws ImsException {
        try {
            this.mMmTelFeatureConnection.acknowledgeSms(n, n2, n3);
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("acknowledgeSms()", (Throwable)remoteException, 106);
        }
    }

    public void acknowledgeSmsReport(int n, int n2, int n3) throws ImsException {
        try {
            this.mMmTelFeatureConnection.acknowledgeSmsReport(n, n2, n3);
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("acknowledgeSmsReport()", (Throwable)remoteException, 106);
        }
    }

    public void addCapabilitiesCallback(ImsMmTelManager.CapabilityCallback capabilityCallback) throws ImsException {
        if (capabilityCallback != null) {
            this.checkAndThrowExceptionIfServiceUnavailable();
            try {
                capabilityCallback.setExecutor(this.getThreadExecutor());
                this.mMmTelFeatureConnection.addCapabilityCallback(capabilityCallback.getBinder());
                ImsManager.log("Capability Callback registered.");
                return;
            }
            catch (IllegalStateException illegalStateException) {
                throw new ImsException("addCapabilitiesCallback(IF)", (Throwable)illegalStateException, 106);
            }
        }
        throw new NullPointerException("capabilities callback can't be null");
    }

    public void addCapabilitiesCallbackForSubscription(IImsCapabilityCallback iImsCapabilityCallback, int n) throws RemoteException {
        if (iImsCapabilityCallback != null) {
            this.mMmTelFeatureConnection.addCapabilityCallbackForSubscription(iImsCapabilityCallback, n);
            ImsManager.log("Capability Callback registered for subscription.");
            return;
        }
        throw new IllegalArgumentException("registration callback can't be null");
    }

    @VisibleForTesting
    public void addNotifyStatusChangedCallbackIfAvailable(MmTelFeatureConnection.IFeatureUpdate iFeatureUpdate) throws ImsException {
        if (this.mMmTelFeatureConnection.isBinderAlive()) {
            if (iFeatureUpdate != null) {
                this.mStatusCallbacks.add(iFeatureUpdate);
            }
            return;
        }
        throw new ImsException("Binder is not active!", 106);
    }

    public void addProvisioningCallbackForSubscription(IImsConfigCallback iImsConfigCallback, int n) {
        if (iImsConfigCallback != null) {
            this.mMmTelFeatureConnection.addProvisioningCallbackForSubscription(iImsConfigCallback, n);
            ImsManager.log("Capability Callback registered for subscription.");
            return;
        }
        throw new IllegalArgumentException("provisioning callback can't be null");
    }

    public void addRegistrationCallback(ImsMmTelManager.RegistrationCallback registrationCallback) throws ImsException {
        if (registrationCallback != null) {
            try {
                registrationCallback.setExecutor(this.getThreadExecutor());
                this.mMmTelFeatureConnection.addRegistrationCallback(registrationCallback.getBinder());
                ImsManager.log("Registration Callback registered.");
                return;
            }
            catch (IllegalStateException illegalStateException) {
                throw new ImsException("addRegistrationCallback(IRIB)", (Throwable)illegalStateException, 106);
            }
        }
        throw new NullPointerException("registration callback can't be null");
    }

    public void addRegistrationCallbackForSubscription(IImsRegistrationCallback iImsRegistrationCallback, int n) throws RemoteException {
        if (iImsRegistrationCallback != null) {
            this.mMmTelFeatureConnection.addRegistrationCallbackForSubscription(iImsRegistrationCallback, n);
            ImsManager.log("Registration Callback registered.");
            return;
        }
        throw new IllegalArgumentException("registration callback can't be null");
    }

    public void addRegistrationListener(int n, ImsConnectionStateListener imsConnectionStateListener) throws ImsException {
        this.addRegistrationListener(imsConnectionStateListener);
    }

    public void addRegistrationListener(final ImsConnectionStateListener imsConnectionStateListener) throws ImsException {
        if (imsConnectionStateListener != null) {
            this.addRegistrationCallback(imsConnectionStateListener);
            this.addCapabilitiesCallback(new ImsMmTelManager.CapabilityCallback(){

                public void onCapabilitiesStatusChanged(MmTelFeature.MmTelCapabilities mmTelCapabilities) {
                    imsConnectionStateListener.onFeatureCapabilityChangedAdapter(ImsManager.this.getRegistrationTech(), mmTelCapabilities);
                }
            });
            ImsManager.log("Registration Callback registered.");
            return;
        }
        throw new NullPointerException("listener can't be null");
    }

    public void changeMmTelCapability(int n, int n2, boolean bl) throws ImsException {
        CapabilityChangeRequest capabilityChangeRequest = new CapabilityChangeRequest();
        if (bl) {
            capabilityChangeRequest.addCapabilitiesToEnableForTech(n, n2);
        } else {
            capabilityChangeRequest.addCapabilitiesToDisableForTech(n, n2);
        }
        this.changeMmTelCapability(capabilityChangeRequest);
    }

    public void changeMmTelCapability(CapabilityChangeRequest object) throws ImsException {
        this.checkAndThrowExceptionIfServiceUnavailable();
        try {
            Object object22 = new StringBuilder();
            ((StringBuilder)object22).append("changeMmTelCapability: changing capabilities for sub: ");
            ((StringBuilder)object22).append(this.getSubId());
            ((StringBuilder)object22).append(", request: ");
            ((StringBuilder)object22).append(object);
            Log.i((String)TAG, (String)((StringBuilder)object22).toString());
            this.mMmTelFeatureConnection.changeEnabledCapabilities((CapabilityChangeRequest)object, null);
            if (this.mImsConfigListener == null) {
                return;
            }
            for (CapabilityChangeRequest.CapabilityPair capabilityPair : object.getCapabilitiesToEnable()) {
                this.mImsConfigListener.onSetFeatureResponse(capabilityPair.getCapability(), capabilityPair.getRadioTech(), 1, -1);
            }
            for (Object object22 : object.getCapabilitiesToDisable()) {
                this.mImsConfigListener.onSetFeatureResponse(object22.getCapability(), object22.getRadioTech(), 0, -1);
            }
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("changeMmTelCapability(CCR)", (Throwable)remoteException, 106);
        }
    }

    public void close() {
        MmTelFeatureConnection mmTelFeatureConnection = this.mMmTelFeatureConnection;
        if (mmTelFeatureConnection != null) {
            mmTelFeatureConnection.closeConnection();
        }
        this.mUt = null;
        this.mEcbm = null;
        this.mMultiEndpoint = null;
    }

    public void connectIfServiceIsAvailable() {
        MmTelFeatureConnection mmTelFeatureConnection = this.mMmTelFeatureConnection;
        if (mmTelFeatureConnection == null || !mmTelFeatureConnection.isBinderAlive()) {
            this.createImsService();
        }
    }

    public ImsCallProfile createCallProfile(int n, int n2) throws ImsException {
        this.checkAndThrowExceptionIfServiceUnavailable();
        try {
            ImsCallProfile imsCallProfile = this.mMmTelFeatureConnection.createCallProfile(n, n2);
            return imsCallProfile;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("createCallProfile()", (Throwable)remoteException, 106);
        }
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("ImsManager:");
        object = new StringBuilder();
        ((StringBuilder)object).append("  device supports IMS = ");
        ((StringBuilder)object).append(ImsManager.isImsSupportedOnDevice(this.mContext));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mPhoneId = ");
        ((StringBuilder)object).append(this.mPhoneId);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mConfigUpdated = ");
        ((StringBuilder)object).append(this.mConfigUpdated);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mImsServiceProxy = ");
        ((StringBuilder)object).append(this.mMmTelFeatureConnection);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  mDataEnabled = ");
        ((StringBuilder)object).append(this.isDataEnabled());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  ignoreDataEnabledChanged = ");
        ((StringBuilder)object).append(this.getBooleanCarrierConfig("ignore_data_enabled_changed_for_video_calls"));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isGbaValid = ");
        ((StringBuilder)object).append(this.isGbaValid());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isImsTurnOffAllowed = ");
        ((StringBuilder)object).append(this.isImsTurnOffAllowed());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isNonTtyOrTtyOnVolteEnabled = ");
        ((StringBuilder)object).append(this.isNonTtyOrTtyOnVolteEnabled());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isVolteEnabledByPlatform = ");
        ((StringBuilder)object).append(this.isVolteEnabledByPlatform());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isVolteProvisionedOnDevice = ");
        ((StringBuilder)object).append(this.isVolteProvisionedOnDevice());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isEnhanced4gLteModeSettingEnabledByUser = ");
        ((StringBuilder)object).append(this.isEnhanced4gLteModeSettingEnabledByUser());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isVtEnabledByPlatform = ");
        ((StringBuilder)object).append(this.isVtEnabledByPlatform());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isVtEnabledByUser = ");
        ((StringBuilder)object).append(this.isVtEnabledByUser());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isWfcEnabledByPlatform = ");
        ((StringBuilder)object).append(this.isWfcEnabledByPlatform());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isWfcEnabledByUser = ");
        ((StringBuilder)object).append(this.isWfcEnabledByUser());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  getWfcMode = ");
        ((StringBuilder)object).append(this.getWfcMode());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isWfcRoamingEnabledByUser = ");
        ((StringBuilder)object).append(this.isWfcRoamingEnabledByUser());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isVtProvisionedOnDevice = ");
        ((StringBuilder)object).append(this.isVtProvisionedOnDevice());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  isWfcProvisionedOnDevice = ");
        ((StringBuilder)object).append(this.isWfcProvisionedOnDevice());
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
    }

    public void factoryReset() {
        int n = this.getSubId();
        if (this.isSubIdValid(n)) {
            SubscriptionManager.setSubscriptionProperty((int)n, (String)"volte_vt_enabled", (String)ImsManager.booleanToPropertyString(this.getBooleanCarrierConfig("enhanced_4g_lte_on_by_default_bool")));
            SubscriptionManager.setSubscriptionProperty((int)n, (String)"wfc_ims_enabled", (String)ImsManager.booleanToPropertyString(this.getBooleanCarrierConfig("carrier_default_wfc_ims_enabled_bool")));
            SubscriptionManager.setSubscriptionProperty((int)n, (String)"wfc_ims_mode", (String)Integer.toString(this.getIntCarrierConfig("carrier_default_wfc_ims_mode_int")));
            SubscriptionManager.setSubscriptionProperty((int)n, (String)"wfc_ims_roaming_enabled", (String)ImsManager.booleanToPropertyString(this.getBooleanCarrierConfig("carrier_default_wfc_ims_roaming_enabled_bool")));
            SubscriptionManager.setSubscriptionProperty((int)n, (String)"vt_ims_enabled", (String)ImsManager.booleanToPropertyString(true));
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("factoryReset: invalid sub id, can not reset siminfo db settings; subId=");
            stringBuilder.append(n);
            ImsManager.loge(stringBuilder.toString());
        }
        this.updateImsServiceConfig(true);
    }

    public ImsConfig getConfigInterface() throws ImsException {
        this.checkAndThrowExceptionIfServiceUnavailable();
        IImsConfig iImsConfig = this.mMmTelFeatureConnection.getConfigInterface();
        if (iImsConfig != null) {
            return new ImsConfig(iImsConfig);
        }
        throw new ImsException("getConfigInterface()", 131);
    }

    public ImsEcbm getEcbmInterface() throws ImsException {
        Object object;
        block5 : {
            ImsEcbm imsEcbm;
            object = this.mEcbm;
            if (object != null && object.isBinderAlive()) {
                return this.mEcbm;
            }
            this.checkAndThrowExceptionIfServiceUnavailable();
            try {
                object = this.mMmTelFeatureConnection.getEcbmInterface();
                if (object == null) break block5;
            }
            catch (RemoteException remoteException) {
                throw new ImsException("getEcbmInterface()", (Throwable)remoteException, 106);
            }
            this.mEcbm = imsEcbm = new ImsEcbm((IImsEcbm)object);
            return this.mEcbm;
        }
        object = new ImsException("getEcbmInterface()", 901);
        throw object;
    }

    public int getImsServiceState() throws ImsException {
        return this.mMmTelFeatureConnection.getFeatureState();
    }

    public ImsMultiEndpoint getMultiEndpointInterface() throws ImsException {
        Object object;
        block5 : {
            IImsMultiEndpoint iImsMultiEndpoint;
            object = this.mMultiEndpoint;
            if (object != null && object.isBinderAlive()) {
                return this.mMultiEndpoint;
            }
            this.checkAndThrowExceptionIfServiceUnavailable();
            try {
                iImsMultiEndpoint = this.mMmTelFeatureConnection.getMultiEndpointInterface();
                if (iImsMultiEndpoint == null) break block5;
            }
            catch (RemoteException remoteException) {
                throw new ImsException("getMultiEndpointInterface()", (Throwable)remoteException, 106);
            }
            this.mMultiEndpoint = object = new ImsMultiEndpoint(iImsMultiEndpoint);
            return this.mMultiEndpoint;
        }
        object = new ImsException("getMultiEndpointInterface()", 902);
        throw object;
    }

    public ArrayList<ImsReasonInfo> getRecentImsDisconnectReasons() {
        ArrayList<ImsReasonInfo> arrayList = new ArrayList<ImsReasonInfo>();
        Iterator<ImsReasonInfo> iterator = this.mRecentDisconnectReasons.iterator();
        while (iterator.hasNext()) {
            arrayList.add(this.makeACopy(iterator.next()));
        }
        return arrayList;
    }

    public int getRegistrationTech() {
        try {
            int n = this.mMmTelFeatureConnection.getRegistrationTech();
            return n;
        }
        catch (RemoteException remoteException) {
            Log.w((String)TAG, (String)"getRegistrationTech: no connection to ImsService.");
            return -1;
        }
    }

    public String getSmsFormat() throws ImsException {
        try {
            String string = this.mMmTelFeatureConnection.getSmsFormat();
            return string;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("getSmsFormat()", (Throwable)remoteException, 106);
        }
    }

    public ImsUtInterface getSupplementaryServiceConfiguration() throws ImsException {
        Object object;
        block5 : {
            IImsUt iImsUt;
            object = this.mUt;
            if (object != null && object.isBinderAlive()) {
                return this.mUt;
            }
            this.checkAndThrowExceptionIfServiceUnavailable();
            try {
                iImsUt = this.mMmTelFeatureConnection.getUtInterface();
                if (iImsUt == null) break block5;
            }
            catch (RemoteException remoteException) {
                throw new ImsException("getSupplementaryServiceConfiguration()", (Throwable)remoteException, 106);
            }
            this.mUt = object = new ImsUt(iImsUt);
            return this.mUt;
        }
        object = new ImsException("getSupplementaryServiceConfiguration()", 801);
        throw object;
    }

    public int getWfcMode() {
        return this.getWfcMode(false);
    }

    public int getWfcMode(boolean bl) {
        int n;
        if (!bl) {
            n = !this.getBooleanCarrierConfig("editable_wfc_mode_bool") ? this.getIntCarrierConfig("carrier_default_wfc_ims_mode_int") : this.getSettingFromSubscriptionManager("wfc_ims_mode", "carrier_default_wfc_ims_mode_int");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getWfcMode - setting=");
            stringBuilder.append(n);
            ImsManager.log(stringBuilder.toString());
        } else {
            n = this.getBooleanCarrierConfig("use_wfc_home_network_mode_in_roaming_network_bool") ? this.getWfcMode(false) : (!this.getBooleanCarrierConfig("editable_wfc_roaming_mode_bool") ? this.getIntCarrierConfig("carrier_default_wfc_ims_roaming_mode_int") : this.getSettingFromSubscriptionManager("wfc_ims_roaming_mode", "carrier_default_wfc_ims_roaming_mode_int"));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getWfcMode (roaming) - setting=");
            stringBuilder.append(n);
            ImsManager.log(stringBuilder.toString());
        }
        return n;
    }

    public boolean isDynamicBinding() {
        return this.mConfigDynamicBind;
    }

    public boolean isEnhanced4gLteModeSettingEnabledByUser() {
        int n = SubscriptionManager.getIntegerSubscriptionProperty((int)this.getSubId(), (String)"volte_vt_enabled", (int)-1, (Context)this.mContext);
        boolean bl = this.getBooleanCarrierConfig("enhanced_4g_lte_on_by_default_bool");
        if (this.getBooleanCarrierConfig("editable_enhanced_4g_lte_bool") && !this.getBooleanCarrierConfig("hide_enhanced_4g_lte_bool") && n != -1) {
            bl = true;
            if (n != 1) {
                bl = false;
            }
            return bl;
        }
        return bl;
    }

    public boolean isNonTtyOrTtyOnVolteEnabled() {
        boolean bl = this.isTtyOnVoLteCapable();
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        TelecomManager telecomManager = (TelecomManager)this.mContext.getSystemService("telecom");
        if (telecomManager == null) {
            Log.w((String)TAG, (String)"isNonTtyOrTtyOnVolteEnabled: telecom not available");
            return true;
        }
        if (telecomManager.getCurrentTtyMode() != 0) {
            bl2 = false;
        }
        return bl2;
    }

    public boolean isServiceAvailable() {
        this.connectIfServiceIsAvailable();
        return this.mMmTelFeatureConnection.isBinderAlive();
    }

    public boolean isServiceReady() {
        this.connectIfServiceIsAvailable();
        return this.mMmTelFeatureConnection.isBinderReady();
    }

    public boolean isSuppServicesOverUtEnabledByPlatform() {
        boolean bl;
        block1 : {
            int n = ((TelephonyManager)this.mContext.getSystemService("phone")).getSimState(this.mPhoneId);
            bl = false;
            if (n != 5) {
                return false;
            }
            if (!this.getBooleanCarrierConfig("carrier_supports_ss_over_ut_bool") || !this.isGbaValid()) break block1;
            bl = true;
        }
        return bl;
    }

    public boolean isTtyOnVoLteCapable() {
        return this.getBooleanCarrierConfig("carrier_volte_tty_supported_bool");
    }

    public boolean isVolteEnabledByPlatform() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PROPERTY_DBG_VOLTE_AVAIL_OVERRIDE);
        stringBuilder.append(Integer.toString(this.mPhoneId));
        int n = SystemProperties.getInt((String)stringBuilder.toString(), (int)-1);
        boolean bl = true;
        if (n != 1 && SystemProperties.getInt((String)PROPERTY_DBG_VOLTE_AVAIL_OVERRIDE, (int)-1) != 1) {
            if (!(this.mContext.getResources().getBoolean(17891403) && this.getBooleanCarrierConfig("carrier_volte_available_bool") && this.isGbaValid())) {
                bl = false;
            }
            return bl;
        }
        return true;
    }

    public boolean isVolteProvisionedOnDevice() {
        if (this.getBooleanCarrierConfig("carrier_volte_provisioning_required_bool")) {
            return this.isVolteProvisioned();
        }
        return true;
    }

    public boolean isVtEnabledByPlatform() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PROPERTY_DBG_VT_AVAIL_OVERRIDE);
        stringBuilder.append(Integer.toString(this.mPhoneId));
        int n = SystemProperties.getInt((String)stringBuilder.toString(), (int)-1);
        boolean bl = true;
        if (n != 1 && SystemProperties.getInt((String)PROPERTY_DBG_VT_AVAIL_OVERRIDE, (int)-1) != 1) {
            if (!(this.mContext.getResources().getBoolean(17891404) && this.getBooleanCarrierConfig("carrier_vt_available_bool") && this.isGbaValid())) {
                bl = false;
            }
            return bl;
        }
        return true;
    }

    public boolean isVtEnabledByUser() {
        boolean bl;
        int n = SubscriptionManager.getIntegerSubscriptionProperty((int)this.getSubId(), (String)"vt_ims_enabled", (int)-1, (Context)this.mContext);
        boolean bl2 = bl = true;
        if (n != -1) {
            bl2 = n == 1 ? bl : false;
        }
        return bl2;
    }

    public boolean isVtProvisionedOnDevice() {
        if (this.getBooleanCarrierConfig("carrier_volte_provisioning_required_bool")) {
            return this.isVtProvisioned();
        }
        return true;
    }

    public boolean isWfcEnabledByPlatform() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PROPERTY_DBG_WFC_AVAIL_OVERRIDE);
        stringBuilder.append(Integer.toString(this.mPhoneId));
        int n = SystemProperties.getInt((String)stringBuilder.toString(), (int)-1);
        boolean bl = true;
        if (n != 1 && SystemProperties.getInt((String)PROPERTY_DBG_WFC_AVAIL_OVERRIDE, (int)-1) != 1) {
            if (!(this.mContext.getResources().getBoolean(17891405) && this.getBooleanCarrierConfig("carrier_wfc_ims_available_bool") && this.isGbaValid())) {
                bl = false;
            }
            return bl;
        }
        return true;
    }

    public boolean isWfcEnabledByUser() {
        int n = SubscriptionManager.getIntegerSubscriptionProperty((int)this.getSubId(), (String)"wfc_ims_enabled", (int)-1, (Context)this.mContext);
        if (n == -1) {
            return this.getBooleanCarrierConfig("carrier_default_wfc_ims_enabled_bool");
        }
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isWfcProvisionedOnDevice() {
        if (this.getBooleanCarrierConfig("carrier_volte_override_wfc_provisioning_bool") && !this.isVolteProvisionedOnDevice()) {
            return false;
        }
        if (this.getBooleanCarrierConfig("carrier_volte_provisioning_required_bool")) {
            return this.isWfcProvisioned();
        }
        return true;
    }

    public boolean isWfcRoamingEnabledByUser() {
        int n = SubscriptionManager.getIntegerSubscriptionProperty((int)this.getSubId(), (String)"wfc_ims_roaming_enabled", (int)-1, (Context)this.mContext);
        if (n == -1) {
            return this.getBooleanCarrierConfig("carrier_default_wfc_ims_roaming_enabled_bool");
        }
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public /* synthetic */ void lambda$setRttConfig$4$ImsManager(boolean bl, int n) {
        try {
            String string = ImsManager.class.getSimpleName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Setting RTT enabled to ");
            stringBuilder.append(bl);
            Log.i((String)string, (String)stringBuilder.toString());
            this.getConfigInterface().setProvisionedValue(66, n);
        }
        catch (ImsException imsException) {
            String string = ImsManager.class.getSimpleName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to set RTT value enabled to ");
            stringBuilder.append(bl);
            stringBuilder.append(": ");
            stringBuilder.append((Object)imsException);
            Log.e((String)string, (String)stringBuilder.toString());
        }
    }

    public /* synthetic */ void lambda$setWfcModeInternal$1$ImsManager(int n) {
        try {
            this.getConfigInterface().setConfig(27, n);
        }
        catch (ImsException imsException) {
            // empty catch block
        }
    }

    public /* synthetic */ void lambda$setWfcRoamingSettingInternal$2$ImsManager(int n) {
        try {
            this.getConfigInterface().setConfig(26, n);
        }
        catch (ImsException imsException) {
            // empty catch block
        }
    }

    public ImsCall makeCall(ImsCallProfile imsCallProfile, String[] arrstring, ImsCall.Listener listener) throws ImsException {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("makeCall :: profile=");
        ((StringBuilder)object).append((Object)imsCallProfile);
        ImsManager.log(((StringBuilder)object).toString());
        this.checkAndThrowExceptionIfServiceUnavailable();
        object = new ImsCall(this.mContext, imsCallProfile);
        ((ImsCall)object).setListener(listener);
        imsCallProfile = this.createCallSession(imsCallProfile);
        if (arrstring != null && arrstring.length == 1) {
            ((ImsCall)object).start((ImsCallSession)imsCallProfile, arrstring[0]);
        } else {
            ((ImsCall)object).start((ImsCallSession)imsCallProfile, arrstring);
        }
        return object;
    }

    public void onSmsReady() throws ImsException {
        try {
            this.mMmTelFeatureConnection.onSmsReady();
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("onSmsReady()", (Throwable)remoteException, 106);
        }
    }

    public void open(MmTelFeature.Listener listener) throws ImsException {
        this.checkAndThrowExceptionIfServiceUnavailable();
        if (listener != null) {
            try {
                this.mMmTelFeatureConnection.openConnection(listener);
                return;
            }
            catch (RemoteException remoteException) {
                throw new ImsException("open()", (Throwable)remoteException, 106);
            }
        }
        throw new NullPointerException("listener can't be null");
    }

    public boolean queryMmTelCapability(final int n, final int n2) throws ImsException {
        this.checkAndThrowExceptionIfServiceUnavailable();
        final LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque(1);
        try {
            MmTelFeatureConnection mmTelFeatureConnection = this.mMmTelFeatureConnection;
            IImsCapabilityCallback.Stub stub = new IImsCapabilityCallback.Stub(){

                public void onCapabilitiesStatusChanged(int n3) {
                }

                public void onChangeCapabilityConfigurationError(int n4, int n22, int n3) {
                }

                public void onQueryCapabilityConfiguration(int n3, int n22, boolean bl) {
                    if (n3 == n && n22 == n2) {
                        linkedBlockingDeque.offer(bl);
                    }
                }
            };
            mmTelFeatureConnection.queryEnabledCapabilities(n, n2, (IImsCapabilityCallback)stub);
        }
        catch (RemoteException remoteException) {
            throw new ImsException("queryMmTelCapability()", (Throwable)remoteException, 106);
        }
        try {
            boolean bl = (Boolean)linkedBlockingDeque.poll(3000L, TimeUnit.MILLISECONDS);
            return bl;
        }
        catch (InterruptedException interruptedException) {
            Log.w((String)TAG, (String)"queryMmTelCapability: interrupted while waiting for response");
            return false;
        }
    }

    public void removeCapabilitiesCallback(ImsMmTelManager.CapabilityCallback capabilityCallback) throws ImsException {
        if (capabilityCallback != null) {
            this.checkAndThrowExceptionIfServiceUnavailable();
            this.mMmTelFeatureConnection.removeCapabilityCallback(capabilityCallback.getBinder());
            return;
        }
        throw new NullPointerException("capabilities callback can't be null");
    }

    public void removeCapabilitiesCallbackForSubscription(IImsCapabilityCallback iImsCapabilityCallback, int n) {
        if (iImsCapabilityCallback != null) {
            this.mMmTelFeatureConnection.removeCapabilityCallbackForSubscription(iImsCapabilityCallback, n);
            return;
        }
        throw new IllegalArgumentException("capabilities callback can't be null");
    }

    void removeNotifyStatusChangedCallback(MmTelFeatureConnection.IFeatureUpdate iFeatureUpdate) {
        if (iFeatureUpdate != null) {
            this.mStatusCallbacks.remove(iFeatureUpdate);
        } else {
            Log.w((String)TAG, (String)"removeNotifyStatusChangedCallback: callback is null!");
        }
    }

    public void removeProvisioningCallbackForSubscription(IImsConfigCallback iImsConfigCallback, int n) {
        if (iImsConfigCallback != null) {
            this.mMmTelFeatureConnection.removeProvisioningCallbackForSubscription(iImsConfigCallback, n);
            return;
        }
        throw new IllegalArgumentException("provisioning callback can't be null");
    }

    public void removeRegistrationCallbackForSubscription(IImsRegistrationCallback iImsRegistrationCallback, int n) {
        if (iImsRegistrationCallback != null) {
            this.mMmTelFeatureConnection.removeRegistrationCallbackForSubscription(iImsRegistrationCallback, n);
            return;
        }
        throw new IllegalArgumentException("registration callback can't be null");
    }

    public void removeRegistrationListener(ImsMmTelManager.RegistrationCallback registrationCallback) {
        if (registrationCallback != null) {
            this.mMmTelFeatureConnection.removeRegistrationCallback(registrationCallback.getBinder());
            ImsManager.log("Registration callback removed.");
            return;
        }
        throw new NullPointerException("registration callback can't be null");
    }

    public void removeRegistrationListener(ImsConnectionStateListener imsConnectionStateListener) throws ImsException {
        if (imsConnectionStateListener != null) {
            this.checkAndThrowExceptionIfServiceUnavailable();
            this.mMmTelFeatureConnection.removeRegistrationCallback(imsConnectionStateListener.getBinder());
            ImsManager.log("Registration Callback/Listener registered.");
            return;
        }
        throw new NullPointerException("listener can't be null");
    }

    public void sendSms(int n, int n2, String string, String string2, boolean bl, byte[] arrby) throws ImsException {
        try {
            this.mMmTelFeatureConnection.sendSms(n, n2, string, string2, bl, arrby);
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("sendSms()", (Throwable)remoteException, 106);
        }
    }

    public void setConfigListener(ImsConfigListener imsConfigListener) {
        this.mImsConfigListener = imsConfigListener;
    }

    public void setEnhanced4gLteModeSetting(boolean bl) {
        int n;
        int n2;
        if (bl && !this.isVolteProvisionedOnDevice()) {
            ImsManager.log("setEnhanced4gLteModeSetting: Not possible to enable VoLTE due to provisioning.");
            return;
        }
        int n3 = this.getSubId();
        if (!this.getBooleanCarrierConfig("editable_enhanced_4g_lte_bool") || this.getBooleanCarrierConfig("hide_enhanced_4g_lte_bool")) {
            bl = this.getBooleanCarrierConfig("enhanced_4g_lte_on_by_default_bool");
        }
        if ((n2 = SubscriptionManager.getIntegerSubscriptionProperty((int)n3, (String)"volte_vt_enabled", (int)-1, (Context)this.mContext)) != (n = bl ? 1 : 0)) {
            if (this.isSubIdValid(n3)) {
                SubscriptionManager.setSubscriptionProperty((int)n3, (String)"volte_vt_enabled", (String)ImsManager.booleanToPropertyString(bl));
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setEnhanced4gLteModeSetting: invalid sub id, can not set property in  siminfo db; subId=");
                stringBuilder.append(n3);
                ImsManager.loge(stringBuilder.toString());
            }
            if (this.isNonTtyOrTtyOnVolteEnabled()) {
                try {
                    this.setAdvanced4GMode(bl);
                }
                catch (ImsException imsException) {
                    // empty catch block
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRttEnabled(boolean bl) {
        ImsException imsException2;
        block4 : {
            block3 : {
                if (bl) {
                    try {
                        this.setEnhanced4gLteModeSetting(bl);
                        break block3;
                    }
                    catch (ImsException imsException2) {
                        break block4;
                    }
                }
                boolean bl2 = bl || this.isEnhanced4gLteModeSettingEnabledByUser();
                this.setAdvanced4GMode(bl2);
            }
            this.setRttConfig(bl);
            return;
        }
        String string = ImsManager.class.getSimpleName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to set RTT enabled to ");
        stringBuilder.append(bl);
        stringBuilder.append(": ");
        stringBuilder.append((Object)imsException2);
        Log.e((String)string, (String)stringBuilder.toString());
    }

    public void setSmsListener(IImsSmsListener iImsSmsListener) throws ImsException {
        try {
            this.mMmTelFeatureConnection.setSmsListener(iImsSmsListener);
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("setSmsListener()", (Throwable)remoteException, 106);
        }
    }

    public void setTtyMode(int n) throws ImsException {
        if (!this.getBooleanCarrierConfig("carrier_volte_tty_supported_bool")) {
            boolean bl = n == 0 && this.isEnhanced4gLteModeSettingEnabledByUser();
            this.setAdvanced4GMode(bl);
        }
    }

    public void setUiTTYMode(Context context, int n, Message message) throws ImsException {
        this.checkAndThrowExceptionIfServiceUnavailable();
        try {
            this.mMmTelFeatureConnection.setUiTTYMode(n, message);
            return;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("setTTYMode()", (Throwable)remoteException, 106);
        }
    }

    public void setVolteProvisioned(boolean bl) {
        int n = bl ? 1 : 0;
        this.setProvisionedBoolNoException(10, n);
    }

    public void setVtProvisioned(boolean bl) {
        int n = bl ? 1 : 0;
        this.setProvisionedBoolNoException(11, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setVtSetting(boolean bl) {
        if (bl && !this.isVtProvisionedOnDevice()) {
            ImsManager.log("setVtSetting: Not possible to enable Vt due to provisioning.");
            return;
        }
        int n = this.getSubId();
        if (this.isSubIdValid(n)) {
            SubscriptionManager.setSubscriptionProperty((int)n, (String)"vt_ims_enabled", (String)ImsManager.booleanToPropertyString(bl));
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setVtSetting: sub id invalid, skip modifying vt state in subinfo db; subId=");
            stringBuilder.append(n);
            ImsManager.loge(stringBuilder.toString());
        }
        try {
            this.changeMmTelCapability(2, 0, bl);
            if (bl) {
                ImsManager.log("setVtSetting(b) : turnOnIms");
                this.turnOnIms();
                return;
            }
            if (!this.isTurnOffImsAllowedByPlatform()) return;
            if (this.isVolteEnabledByPlatform()) {
                if (this.isEnhanced4gLteModeSettingEnabledByUser()) return;
            }
            ImsManager.log("setVtSetting(b) : imsServiceAllowTurnOff -> turnOffIms");
            this.turnOffIms();
            return;
        }
        catch (ImsException imsException) {
            ImsManager.loge("setVtSetting(b): ", imsException);
        }
    }

    public void setWfcMode(int n) {
        this.setWfcMode(n, false);
    }

    public void setWfcMode(int n, boolean bl) {
        int n2 = this.getSubId();
        if (this.isSubIdValid(n2)) {
            if (!bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setWfcMode(i,b) - setting=");
                stringBuilder.append(n);
                ImsManager.log(stringBuilder.toString());
                SubscriptionManager.setSubscriptionProperty((int)n2, (String)"wfc_ims_mode", (String)Integer.toString(n));
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setWfcMode(i,b) (roaming) - setting=");
                stringBuilder.append(n);
                ImsManager.log(stringBuilder.toString());
                SubscriptionManager.setSubscriptionProperty((int)n2, (String)"wfc_ims_roaming_mode", (String)Integer.toString(n));
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setWfcMode(i,b): invalid sub id, skip setting setting in siminfo db; subId=");
            stringBuilder.append(n2);
            ImsManager.loge(stringBuilder.toString());
        }
        if (bl == ((TelephonyManager)this.mContext.getSystemService("phone")).isNetworkRoaming(this.getSubId())) {
            this.setWfcModeInternal(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setWfcNonPersistent(boolean bl, int n) {
        boolean bl2 = true;
        if (!bl) {
            n = 1;
        }
        try {
            this.changeMmTelCapability(1, 1, bl);
            this.setWfcModeInternal(n);
            if (!bl || !this.isWfcRoamingEnabledByUser()) {
                bl2 = false;
            }
            this.setWfcRoamingSettingInternal(bl2);
            if (bl) {
                ImsManager.log("setWfcSetting() : turnOnIms");
                this.turnOnIms();
                return;
            }
            if (!this.isTurnOffImsAllowedByPlatform()) return;
            if (this.isVolteEnabledByPlatform()) {
                if (this.isEnhanced4gLteModeSettingEnabledByUser()) return;
            }
            ImsManager.log("setWfcSetting() : imsServiceAllowTurnOff -> turnOffIms");
            this.turnOffIms();
            return;
        }
        catch (ImsException imsException) {
            ImsManager.loge("setWfcSetting(): ", imsException);
        }
    }

    public void setWfcProvisioned(boolean bl) {
        int n = bl ? 1 : 0;
        this.setProvisionedBoolNoException(28, n);
    }

    public void setWfcRoamingSetting(boolean bl) {
        SubscriptionManager.setSubscriptionProperty((int)this.getSubId(), (String)"wfc_ims_roaming_enabled", (String)ImsManager.booleanToPropertyString(bl));
        this.setWfcRoamingSettingInternal(bl);
    }

    public void setWfcSetting(boolean bl) {
        if (bl && !this.isWfcProvisionedOnDevice()) {
            ImsManager.log("setWfcSetting: Not possible to enable WFC due to provisioning.");
            return;
        }
        int n = this.getSubId();
        if (this.isSubIdValid(n)) {
            SubscriptionManager.setSubscriptionProperty((int)n, (String)"wfc_ims_enabled", (String)ImsManager.booleanToPropertyString(bl));
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setWfcSetting: invalid sub id, can not set WFC setting in siminfo db; subId=");
            stringBuilder.append(n);
            ImsManager.loge(stringBuilder.toString());
        }
        this.setWfcNonPersistent(bl, this.getWfcMode(((TelephonyManager)this.mContext.getSystemService("phone")).isNetworkRoaming(n)));
    }

    public int shouldProcessCall(boolean bl, String[] arrstring) throws ImsException {
        try {
            int n = this.mMmTelFeatureConnection.shouldProcessCall(bl, arrstring);
            return n;
        }
        catch (RemoteException remoteException) {
            throw new ImsException("shouldProcessCall()", (Throwable)remoteException, 106);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsCall takeCall(IImsCallSession object, Bundle bundle, ImsCall.Listener listener) throws ImsException {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("takeCall :: incomingCall=");
        ((StringBuilder)object2).append((Object)bundle);
        ImsManager.log(((StringBuilder)object2).toString());
        this.checkAndThrowExceptionIfServiceUnavailable();
        if (bundle == null) throw new ImsException("Can't retrieve session with null intent", 101);
        if (ImsManager.getCallId(bundle) == null) throw new ImsException("Call ID missing in the incoming call intent", 101);
        if (object != null) {
            try {
                object2 = new ImsCall(this.mContext, object.getCallProfile());
                bundle = new ImsCallSession(object);
                ((ImsCall)object2).attachSession((ImsCallSession)bundle);
                ((ImsCall)object2).setListener(listener);
                return object2;
            }
            catch (Throwable throwable) {
                throw new ImsException("takeCall()", throwable, 0);
            }
        }
        object = new ImsException("No pending session for the call", 107);
        throw object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateImsServiceConfig(boolean bl) {
        if (!bl && new TelephonyManager(this.mContext, this.getSubId()).getSimState() != 5) {
            ImsManager.log("updateImsServiceConfig: SIM not ready");
            return;
        }
        if (this.mConfigUpdated) {
            if (!bl) return;
        }
        try {
            CapabilityChangeRequest capabilityChangeRequest = new CapabilityChangeRequest();
            this.updateVolteFeatureValue(capabilityChangeRequest);
            this.updateWfcFeatureAndProvisionedValues(capabilityChangeRequest);
            this.updateVideoCallFeatureValue(capabilityChangeRequest);
            bl = this.updateRttConfigValue();
            this.updateUtFeatureValue(capabilityChangeRequest);
            this.changeMmTelCapability(capabilityChangeRequest);
            if (!bl && this.isTurnOffImsAllowedByPlatform() && !this.isImsNeeded(capabilityChangeRequest)) {
                ImsManager.log("updateImsServiceConfig: turnOffIms");
                this.turnOffIms();
            } else {
                ImsManager.log("updateImsServiceConfig: turnOnIms");
                this.turnOnIms();
            }
            this.mConfigUpdated = true;
            return;
        }
        catch (ImsException imsException) {
            ImsManager.loge("updateImsServiceConfig: ", imsException);
            this.mConfigUpdated = false;
        }
    }

    public boolean updateRttConfigValue() {
        boolean bl = this.getBooleanCarrierConfig("rtt_supported_bool");
        Object object = this.mContext.getContentResolver();
        boolean bl2 = false;
        boolean bl3 = Settings.Secure.getInt((ContentResolver)object, (String)"rtt_calling_mode", (int)0) != 0;
        String string = ImsManager.class.getSimpleName();
        object = new StringBuilder();
        ((StringBuilder)object).append("update RTT value ");
        ((StringBuilder)object).append(bl3);
        Log.i((String)string, (String)((StringBuilder)object).toString());
        if (bl) {
            this.setRttConfig(bl3);
        }
        boolean bl4 = bl2;
        if (bl) {
            bl4 = bl2;
            if (bl3) {
                bl4 = true;
            }
        }
        return bl4;
    }

    public static class Connector
    extends Handler {
        private static final int CEILING_SERVICE_RETRY_COUNT = 6;
        private static final int IMS_RETRY_STARTING_TIMEOUT_MS = 500;
        private final Context mContext;
        private final Executor mExecutor;
        private final Runnable mGetServiceRunnable = new _$$Lambda$ImsManager$Connector$N5r1SvOgM0jfHDwKkcQbyw_uTP0(this);
        private ImsManager mImsManager;
        private final Listener mListener;
        private final Object mLock = new Object();
        private MmTelFeatureConnection.IFeatureUpdate mNotifyStatusChangedCallback = new MmTelFeatureConnection.IFeatureUpdate(this){
            final /* synthetic */ Connector this$0;
            {
                this.this$0 = connector;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            public /* synthetic */ void lambda$notifyStateChanged$0$ImsManager$Connector$1() {
                var1_1 = 0;
                var2_2 = Connector.access$300(this.this$0);
                // MONITORENTER : var2_2
                if (Connector.access$400(this.this$0) != null) {
                    var1_1 = Connector.access$400(this.this$0).getImsServiceState();
                }
                // MONITOREXIT : var2_2
                if (var1_1 == 0 || var1_1 == 1) ** GOTO lbl16
                if (var1_1 == 2) ** GOTO lbl14
                try {
                    Log.w((String)"ImsManager", (String)"Unexpected State!");
                    return;
lbl14: // 1 sources:
                    Connector.access$500(this.this$0);
                    return;
lbl16: // 1 sources:
                    Connector.access$100(this.this$0);
                    return;
                }
                catch (ImsException var2_3) {
                    Connector.access$100(this.this$0);
                    Connector.access$200(this.this$0);
                }
            }

            public /* synthetic */ void lambda$notifyUnavailable$1$ImsManager$Connector$1() {
                this.this$0.notifyNotReady();
                this.this$0.retryGetImsService();
            }

            @Override
            public void notifyStateChanged() {
                this.this$0.mExecutor.execute(new _$$Lambda$ImsManager$Connector$1$QkUK3GnYms22eckyg3OL_BmtP3M(this));
            }

            @Override
            public void notifyUnavailable() {
                this.this$0.mExecutor.execute(new _$$Lambda$ImsManager$Connector$1$noNC6hbyVe0dHnOoOYgo9PyTSxw(this));
            }
        };
        private final int mPhoneId;
        private int mRetryCount = 0;
        @VisibleForTesting
        public RetryTimeout mRetryTimeout = new _$$Lambda$ImsManager$Connector$yM9scWJWjDp_h0yrkCgrjFZH5oI(this);

        public Connector(Context context, int n, Listener listener) {
            this.mContext = context;
            this.mPhoneId = n;
            this.mListener = listener;
            this.mExecutor = new HandlerExecutor((Handler)this);
        }

        @VisibleForTesting
        public Connector(Context context, int n, Listener listener, Executor executor) {
            this.mContext = context;
            this.mPhoneId = n;
            this.mListener = listener;
            this.mExecutor = executor;
        }

        static /* synthetic */ Object access$300(Connector connector) {
            return connector.mLock;
        }

        static /* synthetic */ ImsManager access$400(Connector connector) {
            return connector.mImsManager;
        }

        static /* synthetic */ void access$500(Connector connector) throws ImsException {
            connector.notifyReady();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void getImsService() throws ImsException {
            Object object = this.mLock;
            synchronized (object) {
                this.mImsManager = ImsManager.getInstance(this.mContext, this.mPhoneId);
                this.mImsManager.addNotifyStatusChangedCallbackIfAvailable(this.mNotifyStatusChangedCallback);
            }
            this.mNotifyStatusChangedCallback.notifyStateChanged();
        }

        private void notifyNotReady() {
            this.mListener.connectionUnavailable();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void notifyReady() throws ImsException {
            ImsManager imsManager;
            Object object = this.mLock;
            synchronized (object) {
                imsManager = this.mImsManager;
            }
            try {
                this.mListener.connectionReady(imsManager);
                object = this.mLock;
            }
            catch (ImsException imsException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Connector: notifyReady exception: ");
                ((StringBuilder)object).append(imsException.getMessage());
                Log.w((String)ImsManager.TAG, (String)((StringBuilder)object).toString());
                throw imsException;
            }
            synchronized (object) {
                this.mRetryCount = 0;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void retryGetImsService() {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mImsManager != null) {
                    this.mImsManager.removeNotifyStatusChangedCallback(this.mNotifyStatusChangedCallback);
                    this.mImsManager = null;
                }
                this.removeCallbacks(this.mGetServiceRunnable);
                this.postDelayed(this.mGetServiceRunnable, (long)this.mRetryTimeout.get());
                return;
            }
        }

        public void connect() {
            if (!ImsManager.isImsSupportedOnDevice(this.mContext)) {
                return;
            }
            this.mRetryCount = 0;
            this.post(this.mGetServiceRunnable);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void disconnect() {
            this.removeCallbacks(this.mGetServiceRunnable);
            Object object = this.mLock;
            synchronized (object) {
                if (this.mImsManager != null) {
                    this.mImsManager.removeNotifyStatusChangedCallback(this.mNotifyStatusChangedCallback);
                }
            }
            this.notifyNotReady();
        }

        public /* synthetic */ void lambda$new$0$ImsManager$Connector() {
            try {
                this.getImsService();
            }
            catch (ImsException imsException) {
                this.retryGetImsService();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public /* synthetic */ int lambda$new$1$ImsManager$Connector() {
            Object object = this.mLock;
            synchronized (object) {
                int n = this.mRetryCount;
                if (this.mRetryCount <= 6) {
                    ++this.mRetryCount;
                }
                return (1 << n) * 500;
            }
        }

        public static interface Listener {
            public void connectionReady(ImsManager var1) throws ImsException;

            public void connectionUnavailable();
        }

        @VisibleForTesting
        public static interface RetryTimeout {
            public int get();
        }

    }

    @VisibleForTesting
    public static interface ExecutorFactory {
        public void executeRunnable(Runnable var1);
    }

}

