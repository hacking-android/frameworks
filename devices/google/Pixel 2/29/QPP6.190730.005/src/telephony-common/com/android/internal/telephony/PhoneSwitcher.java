/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.ConnectivityManager
 *  android.net.ConnectivityManager$NetworkCallback
 *  android.net.MatchAllNetworkSpecifier
 *  android.net.Network
 *  android.net.NetworkCapabilities
 *  android.net.NetworkFactory
 *  android.net.NetworkRequest
 *  android.net.NetworkSpecifier
 *  android.net.StringNetworkSpecifier
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.telephony.PhoneCapability
 *  android.telephony.PhoneStateListener
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.util.LocalLog
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.IOnSubscriptionsChangedListener
 *  com.android.internal.telephony.IOnSubscriptionsChangedListener$Stub
 *  com.android.internal.telephony.ISetOpportunisticDataCallback
 *  com.android.internal.telephony.ITelephonyRegistry
 *  com.android.internal.telephony.ITelephonyRegistry$Stub
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.MatchAllNetworkSpecifier;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkFactory;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.StringNetworkSpecifier;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.PhoneCapability;
import android.telephony.PhoneStateListener;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.LocalLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CellularNetworkValidator;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.IOnSubscriptionsChangedListener;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.RadioConfig;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony._$$Lambda$PhoneSwitcher$WfAxZbJDpCUxBytiUchQ87aGijQ;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import com.android.internal.telephony.dataconnection.DcRequest;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.nano.TelephonyProto;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PhoneSwitcher
extends Handler {
    @VisibleForTesting
    public static int DEFAULT_DATA_OVERRIDE_TIMEOUT_MS = 0;
    private static final int DEFAULT_EMERGENCY_PHONE_ID = 0;
    private static final int DEFAULT_NETWORK_CHANGE_TIMEOUT_MS = 5000;
    private static final int DEFAULT_VALIDATION_EXPIRATION_TIME = 2000;
    @VisibleForTesting
    public static int ECBM_DEFAULT_DATA_SWITCH_BASE_TIME_MS = 0;
    @VisibleForTesting
    public static final int EVENT_DATA_ENABLED_CHANGED = 114;
    private static final int EVENT_EMERGENCY_TOGGLE = 105;
    private static final int EVENT_MODEM_COMMAND_DONE = 112;
    private static final int EVENT_MODEM_COMMAND_RETRY = 113;
    private static final int EVENT_NETWORK_VALIDATION_DONE = 110;
    private static final int EVENT_OPPT_DATA_SUB_CHANGED = 107;
    private static final int EVENT_OVERRIDE_DDS_FOR_EMERGENCY = 115;
    @VisibleForTesting
    public static final int EVENT_PRECISE_CALL_STATE_CHANGED = 109;
    private static final int EVENT_PRIMARY_DATA_SUB_CHANGED = 101;
    private static final int EVENT_RADIO_AVAILABLE = 108;
    private static final int EVENT_RADIO_CAPABILITY_CHANGED = 106;
    private static final int EVENT_RELEASE_NETWORK = 104;
    private static final int EVENT_REMOVE_DDS_EMERGENCY_OVERRIDE = 116;
    private static final int EVENT_REMOVE_DEFAULT_NETWORK_CHANGE_CALLBACK = 111;
    private static final int EVENT_REQUEST_NETWORK = 103;
    private static final int EVENT_SUBSCRIPTION_CHANGED = 102;
    private static final int HAL_COMMAND_ALLOW_DATA = 1;
    private static final int HAL_COMMAND_PREFERRED_DATA = 2;
    private static final int HAL_COMMAND_UNKNOWN = 0;
    private static final String LOG_TAG = "PhoneSwitcher";
    private static final int MAX_LOCAL_LOG_LINES = 30;
    private static final int MODEM_COMMAND_RETRY_PERIOD_MS = 5000;
    private static final boolean REQUESTS_CHANGED = true;
    private static final boolean REQUESTS_UNCHANGED = false;
    private static final boolean VDBG = false;
    private static PhoneSwitcher sPhoneSwitcher;
    private final RegistrantList mActivePhoneRegistrants;
    private final CommandsInterface[] mCommandsInterfaces;
    private ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private final BroadcastReceiver mDefaultDataChangedReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            PhoneSwitcher.this.obtainMessage(101).sendToTarget();
        }
    };
    private final ConnectivityManager.NetworkCallback mDefaultNetworkCallback = new ConnectivityManager.NetworkCallback(){

        public void onAvailable(Network object) {
            if (PhoneSwitcher.this.mConnectivityManager.getNetworkCapabilities(object).hasTransport(0)) {
                object = PhoneSwitcher.this;
                ((PhoneSwitcher)((Object)object)).logDataSwitchEvent(((PhoneSwitcher)((Object)object)).mOpptDataSubId, 2, 0);
            }
            PhoneSwitcher.this.removeDefaultNetworkChangeCallback();
        }
    };
    private EmergencyOverrideRequest mEmergencyOverride;
    private int mHalCommandToUse = 0;
    private Boolean mHasRegisteredDefaultNetworkChangeCallback = false;
    private final LocalLog mLocalLog;
    @UnsupportedAppUsage
    private int mMaxActivePhones;
    @UnsupportedAppUsage
    private final int mNumPhones;
    private int mOpptDataSubId = Integer.MAX_VALUE;
    private int mPhoneIdInVoiceCall = -1;
    @VisibleForTesting
    public final PhoneStateListener mPhoneStateListener;
    private final PhoneState[] mPhoneStates;
    private final int[] mPhoneSubscriptions;
    @UnsupportedAppUsage
    private final Phone[] mPhones;
    @VisibleForTesting
    protected int mPreferredDataPhoneId = -1;
    private int mPreferredDataSubId = -1;
    private int mPrimaryDataSubId = -1;
    private final List<DcRequest> mPrioritizedDcRequests = new ArrayList<DcRequest>();
    private RadioConfig mRadioConfig;
    private ISetOpportunisticDataCallback mSetOpptSubCallback;
    private final SubscriptionController mSubscriptionController;
    private final IOnSubscriptionsChangedListener mSubscriptionsChangedListener = new IOnSubscriptionsChangedListener.Stub(){

        public void onSubscriptionsChanged() {
            PhoneSwitcher.this.obtainMessage(102).sendToTarget();
        }
    };
    @VisibleForTesting
    public final CellularNetworkValidator.ValidationCallback mValidationCallback = new _$$Lambda$PhoneSwitcher$WfAxZbJDpCUxBytiUchQ87aGijQ(this);
    private final CellularNetworkValidator mValidator;

    static {
        ECBM_DEFAULT_DATA_SWITCH_BASE_TIME_MS = 5000;
        DEFAULT_DATA_OVERRIDE_TIMEOUT_MS = 5000;
        sPhoneSwitcher = null;
    }

    @VisibleForTesting
    public PhoneSwitcher(int n, int n2, Context object, SubscriptionController networkCapabilities, Looper looper, ITelephonyRegistry iTelephonyRegistry, CommandsInterface[] arrcommandsInterface, Phone[] arrphone) {
        super(looper);
        this.mContext = object;
        this.mNumPhones = n2;
        this.mPhones = arrphone;
        this.mPhoneSubscriptions = new int[n2];
        this.mMaxActivePhones = n;
        this.mLocalLog = new LocalLog(30);
        this.mSubscriptionController = networkCapabilities;
        this.mRadioConfig = RadioConfig.getInstance(this.mContext);
        this.mPhoneStateListener = new PhoneStateListener(looper){

            public void onPhoneCapabilityChanged(PhoneCapability phoneCapability) {
                PhoneSwitcher.this.onPhoneCapabilityChangedInternal(phoneCapability);
            }
        };
        this.mValidator = CellularNetworkValidator.getInstance();
        this.mActivePhoneRegistrants = new RegistrantList();
        this.mPhoneStates = new PhoneState[n2];
        for (n = 0; n < n2; ++n) {
            this.mPhoneStates[n] = new PhoneState();
            networkCapabilities = this.mPhones;
            if (networkCapabilities[n] == null) continue;
            networkCapabilities[n].registerForEmergencyCallToggle(this, 105, null);
            this.mPhones[n].registerForPreciseCallStateChanged(this, 109, null);
            if (this.mPhones[n].getImsPhone() != null) {
                this.mPhones[n].getImsPhone().registerForPreciseCallStateChanged(this, 109, null);
            }
            this.mPhones[n].getDataEnabledSettings().registerForDataEnabledChanged(this, 114, null);
        }
        this.mCommandsInterfaces = arrcommandsInterface;
        if (n2 > 0) {
            this.mCommandsInterfaces[0].registerForAvailable(this, 108, null);
        }
        try {
            iTelephonyRegistry.addOnSubscriptionsChangedListener(object.getOpPackageName(), this.mSubscriptionsChangedListener);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        this.mConnectivityManager = (ConnectivityManager)object.getSystemService("connectivity");
        this.mContext.registerReceiver(this.mDefaultDataChangedReceiver, new IntentFilter("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED"));
        networkCapabilities = new NetworkCapabilities();
        networkCapabilities.addTransportType(0);
        networkCapabilities.addCapability(0);
        networkCapabilities.addCapability(1);
        networkCapabilities.addCapability(2);
        networkCapabilities.addCapability(3);
        networkCapabilities.addCapability(4);
        networkCapabilities.addCapability(5);
        networkCapabilities.addCapability(7);
        networkCapabilities.addCapability(8);
        networkCapabilities.addCapability(9);
        networkCapabilities.addCapability(10);
        networkCapabilities.addCapability(13);
        networkCapabilities.addCapability(12);
        networkCapabilities.addCapability(23);
        networkCapabilities.setNetworkSpecifier((NetworkSpecifier)new MatchAllNetworkSpecifier());
        object = new PhoneSwitcherNetworkRequestListener(looper, (Context)object, networkCapabilities, this);
        object.setScoreFilter(101);
        object.register();
        this.log("PhoneSwitcher started");
    }

    @VisibleForTesting
    public PhoneSwitcher(int n, Looper looper) {
        super(looper);
        this.mMaxActivePhones = 0;
        this.mSubscriptionController = null;
        this.mCommandsInterfaces = null;
        this.mContext = null;
        this.mPhoneStates = null;
        this.mPhones = null;
        this.mLocalLog = null;
        this.mActivePhoneRegistrants = null;
        this.mNumPhones = n;
        this.mPhoneSubscriptions = new int[n];
        this.mRadioConfig = RadioConfig.getInstance(this.mContext);
        this.mPhoneStateListener = new PhoneStateListener(looper){

            public void onPhoneCapabilityChanged(PhoneCapability phoneCapability) {
                PhoneSwitcher.this.onPhoneCapabilityChangedInternal(phoneCapability);
            }
        };
        this.mValidator = CellularNetworkValidator.getInstance();
    }

    @UnsupportedAppUsage
    private void activate(int n) {
        this.switchPhone(n, true);
    }

    private void collectReleaseNetworkMetrics(NetworkRequest object) {
        if (this.mNumPhones > 1 && object.networkCapabilities.hasCapability(0)) {
            object = new TelephonyProto.TelephonyEvent.OnDemandDataSwitch();
            object.apn = 2;
            object.state = 2;
            TelephonyMetrics.getInstance().writeOnDemandDataSwitch((TelephonyProto.TelephonyEvent.OnDemandDataSwitch)object);
        }
    }

    private void collectRequestNetworkMetrics(NetworkRequest object) {
        if (this.mNumPhones > 1 && object.networkCapabilities.hasCapability(0)) {
            object = new TelephonyProto.TelephonyEvent.OnDemandDataSwitch();
            object.apn = 2;
            object.state = 1;
            TelephonyMetrics.getInstance().writeOnDemandDataSwitch((TelephonyProto.TelephonyEvent.OnDemandDataSwitch)object);
        }
    }

    @UnsupportedAppUsage
    private void deactivate(int n) {
        this.switchPhone(n, false);
    }

    private Phone findPhoneById(int n) {
        if (n >= 0 && n < this.mNumPhones) {
            return this.mPhones[n];
        }
        return null;
    }

    public static PhoneSwitcher getInstance() {
        return sPhoneSwitcher;
    }

    private int getSubIdForDefaultNetworkRequests() {
        if (this.mSubscriptionController.isActiveSubId(this.mOpptDataSubId)) {
            return this.mOpptDataSubId;
        }
        return this.mPrimaryDataSubId;
    }

    private int getSubIdFromNetworkRequest(NetworkRequest networkRequest) {
        networkRequest = networkRequest.networkCapabilities.getNetworkSpecifier();
        if (networkRequest == null) {
            return Integer.MAX_VALUE;
        }
        if (networkRequest instanceof StringNetworkSpecifier) {
            try {
                int n = Integer.parseInt(((StringNetworkSpecifier)networkRequest).specifier);
                return n;
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("NumberFormatException on ");
                stringBuilder.append(((StringNetworkSpecifier)networkRequest).specifier);
                Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
                return -1;
            }
        }
        return -1;
    }

    private boolean isCallActive(Phone phone) {
        boolean bl = false;
        if (phone == null) {
            return false;
        }
        if (phone.getForegroundCall().getState() == Call.State.ACTIVE || phone.getForegroundCall().getState() == Call.State.ALERTING) {
            bl = true;
        }
        return bl;
    }

    private boolean isEmergency() {
        if (this.isInEmergencyCallbackMode()) {
            return true;
        }
        for (Phone phone : this.mPhones) {
            if (phone == null) continue;
            if (phone.isInEmergencyCall()) {
                return true;
            }
            if ((phone = phone.getImsPhone()) == null || !phone.isInEmergencyCall()) continue;
            return true;
        }
        return false;
    }

    private boolean isInEmergencyCallbackMode() {
        for (Phone phone : this.mPhones) {
            if (phone == null) continue;
            if (phone.isInEcm()) {
                return true;
            }
            if ((phone = phone.getImsPhone()) == null || !phone.isInEcm()) continue;
            return true;
        }
        return false;
    }

    private boolean isPhoneInVoiceCallChanged() {
        int n = this.mPhoneIdInVoiceCall;
        this.mPhoneIdInVoiceCall = -1;
        Phone[] arrphone = this.mPhones;
        int n2 = arrphone.length;
        boolean bl = false;
        for (int i = 0; i < n2; ++i) {
            Phone phone = arrphone[i];
            if (!this.isCallActive(phone) && !this.isCallActive(phone.getImsPhone())) {
                continue;
            }
            this.mPhoneIdInVoiceCall = phone.getPhoneId();
            break;
        }
        if (this.mPhoneIdInVoiceCall != n) {
            bl = true;
        }
        return bl;
    }

    @UnsupportedAppUsage
    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
        this.mLocalLog.log(string);
    }

    private void logDataSwitchEvent(int n, int n2, int n3) {
        if (n == Integer.MAX_VALUE) {
            n = this.mPrimaryDataSubId;
        }
        TelephonyProto.TelephonyEvent.DataSwitch dataSwitch = new TelephonyProto.TelephonyEvent.DataSwitch();
        dataSwitch.state = n2;
        dataSwitch.reason = n3;
        TelephonyMetrics.getInstance().writeDataSwitch(n, dataSwitch);
    }

    public static PhoneSwitcher make(int n, int n2, Context context, SubscriptionController subscriptionController, Looper looper, ITelephonyRegistry iTelephonyRegistry, CommandsInterface[] arrcommandsInterface, Phone[] arrphone) {
        if (sPhoneSwitcher == null) {
            sPhoneSwitcher = new PhoneSwitcher(n, n2, context, subscriptionController, looper, iTelephonyRegistry, arrcommandsInterface, arrphone);
        }
        return sPhoneSwitcher;
    }

    private void notifyPreferredDataSubIdChanged() {
        ITelephonyRegistry iTelephonyRegistry = ITelephonyRegistry.Stub.asInterface((IBinder)ServiceManager.getService((String)"telephony.registry"));
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("notifyPreferredDataSubIdChanged to ");
            stringBuilder.append(this.mPreferredDataSubId);
            this.log(stringBuilder.toString());
            iTelephonyRegistry.notifyActiveDataSubIdChanged(this.mPreferredDataSubId);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private boolean onEvaluate(boolean bl, String iterator) {
        Serializable serializable = new StringBuilder((String)((Object)iterator));
        boolean bl2 = this.isEmergency();
        boolean bl3 = false;
        if (bl2) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("onEvaluate for reason ");
            ((StringBuilder)serializable).append((String)((Object)iterator));
            ((StringBuilder)serializable).append(" aborted due to Emergency");
            this.log(((StringBuilder)serializable).toString());
            return false;
        }
        bl2 = bl3;
        if (this.mHalCommandToUse != 2) {
            bl2 = bl3;
            if (bl) {
                bl2 = true;
            }
        }
        bl = bl2;
        int n = this.mSubscriptionController.getDefaultDataSubId();
        if (n != this.mPrimaryDataSubId) {
            ((StringBuilder)serializable).append(" mPrimaryDataSubId ");
            ((StringBuilder)serializable).append(this.mPrimaryDataSubId);
            ((StringBuilder)serializable).append("->");
            ((StringBuilder)serializable).append(n);
            this.mPrimaryDataSubId = n;
        }
        boolean bl4 = false;
        for (n = 0; n < this.mNumPhones; ++n) {
            int n2 = this.mSubscriptionController.getSubIdUsingPhoneId(n);
            if (SubscriptionManager.isValidSubscriptionId((int)n2)) {
                bl4 = true;
            }
            if (n2 == this.mPhoneSubscriptions[n]) continue;
            ((StringBuilder)serializable).append(" phone[");
            ((StringBuilder)serializable).append(n);
            ((StringBuilder)serializable).append("] ");
            ((StringBuilder)serializable).append(this.mPhoneSubscriptions[n]);
            ((StringBuilder)serializable).append("->");
            ((StringBuilder)serializable).append(n2);
            this.mPhoneSubscriptions[n] = n2;
            bl = true;
        }
        if (!bl4) {
            this.transitionToEmergencyPhone();
        }
        n = this.mPreferredDataPhoneId;
        if (bl4) {
            this.updatePreferredDataPhoneId();
        }
        if (n != this.mPreferredDataPhoneId) {
            ((StringBuilder)serializable).append(" preferred phoneId ");
            ((StringBuilder)serializable).append(n);
            ((StringBuilder)serializable).append("->");
            ((StringBuilder)serializable).append(this.mPreferredDataPhoneId);
            bl = true;
        }
        if (bl) {
            iterator = new StringBuilder();
            ((StringBuilder)((Object)iterator)).append("evaluating due to ");
            ((StringBuilder)((Object)iterator)).append(((StringBuilder)serializable).toString());
            this.log(((StringBuilder)((Object)iterator)).toString());
            if (this.mHalCommandToUse == 2) {
                for (n = 0; n < this.mNumPhones; ++n) {
                    this.mPhoneStates[n].active = true;
                }
                this.sendRilCommands(this.mPreferredDataPhoneId);
            } else {
                serializable = new ArrayList();
                if (this.mMaxActivePhones == this.mPhones.length) {
                    for (n = 0; n < this.mMaxActivePhones; ++n) {
                        serializable.add(this.mPhones[n].getPhoneId());
                    }
                } else {
                    iterator = this.mPrioritizedDcRequests.iterator();
                    while (iterator.hasNext()) {
                        n = this.phoneIdForRequest(((DcRequest)iterator.next()).networkRequest);
                        if (n == -1 || serializable.contains(n)) continue;
                        serializable.add(n);
                        if (serializable.size() < this.mMaxActivePhones) continue;
                        break;
                    }
                    if (serializable.size() < this.mMaxActivePhones && serializable.contains(this.mPreferredDataPhoneId) && SubscriptionManager.isUsableSubIdValue((int)this.mPreferredDataPhoneId)) {
                        serializable.add(this.mPreferredDataPhoneId);
                    }
                }
                for (n = 0; n < this.mNumPhones; ++n) {
                    if (serializable.contains(n)) continue;
                    this.deactivate(n);
                }
                iterator = serializable.iterator();
                while (iterator.hasNext()) {
                    this.activate((Integer)iterator.next());
                }
            }
            this.notifyPreferredDataSubIdChanged();
            this.mActivePhoneRegistrants.notifyRegistrants();
        }
        return bl;
    }

    private void onPhoneCapabilityChangedInternal(PhoneCapability object) {
        int n = TelephonyManager.getDefault().getNumberOfModemsWithSimultaneousDataConnections();
        if (this.mMaxActivePhones != n) {
            this.mMaxActivePhones = n;
            object = new StringBuilder();
            ((StringBuilder)object).append("Max active phones changed to ");
            ((StringBuilder)object).append(this.mMaxActivePhones);
            this.log(((StringBuilder)object).toString());
            this.onEvaluate(false, "phoneCfgChanged");
        }
    }

    private void onReleaseNetwork(NetworkRequest networkRequest) {
        DcRequest dcRequest = new DcRequest(networkRequest, this.mContext);
        if (this.mPrioritizedDcRequests.remove(dcRequest)) {
            this.onEvaluate(true, "netReleased");
            this.collectReleaseNetworkMetrics(networkRequest);
        }
    }

    private void onRequestNetwork(NetworkRequest networkRequest) {
        DcRequest dcRequest = new DcRequest(networkRequest, this.mContext);
        if (!this.mPrioritizedDcRequests.contains(dcRequest)) {
            this.collectRequestNetworkMetrics(networkRequest);
            this.mPrioritizedDcRequests.add(dcRequest);
            Collections.sort(this.mPrioritizedDcRequests);
            this.onEvaluate(true, "netRequest");
        }
    }

    private void onValidationDone(int n, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onValidationDone: ");
        CharSequence charSequence = bl ? "passed" : "failed";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" on subId ");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        if (!this.mSubscriptionController.isActiveSubId(n)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("onValidationDone: subId ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" is no longer active");
            this.log(((StringBuilder)charSequence).toString());
            n = 2;
        } else if (!bl) {
            n = 1;
        } else {
            if (this.mSubscriptionController.isOpportunistic(n)) {
                this.setOpportunisticSubscriptionInternal(n);
            } else {
                this.setOpportunisticSubscriptionInternal(Integer.MAX_VALUE);
            }
            n = 0;
        }
        this.sendSetOpptCallbackHelper(this.mSetOpptSubCallback, n);
        this.mSetOpptSubCallback = null;
    }

    private int phoneIdForRequest(NetworkRequest networkRequest) {
        int n;
        int n2 = this.getSubIdFromNetworkRequest(networkRequest);
        if (n2 == Integer.MAX_VALUE) {
            return this.mPreferredDataPhoneId;
        }
        if (n2 == -1) {
            return -1;
        }
        int n3 = SubscriptionManager.isValidPhoneId((int)this.mPreferredDataPhoneId) ? this.mPhoneSubscriptions[this.mPreferredDataPhoneId] : -1;
        if (networkRequest.hasCapability(12) && networkRequest.hasCapability(13) && n2 != n3 && n2 != this.mValidator.getSubIdInValidation()) {
            return -1;
        }
        int n4 = -1;
        n3 = 0;
        do {
            n = n4;
            if (n3 >= this.mNumPhones) break;
            if (this.mPhoneSubscriptions[n3] == n2) {
                n = n3;
                break;
            }
            ++n3;
        } while (true);
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void registerDefaultNetworkChangeCallback() {
        this.removeDefaultNetworkChangeCallback();
        Boolean bl = this.mHasRegisteredDefaultNetworkChangeCallback;
        synchronized (bl) {
            this.mHasRegisteredDefaultNetworkChangeCallback = true;
            this.mConnectivityManager.registerDefaultNetworkCallback(this.mDefaultNetworkCallback);
            this.sendMessageDelayed(this.obtainMessage(111), 5000L);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeDefaultNetworkChangeCallback() {
        Boolean bl = this.mHasRegisteredDefaultNetworkChangeCallback;
        synchronized (bl) {
            if (this.mHasRegisteredDefaultNetworkChangeCallback.booleanValue()) {
                this.mHasRegisteredDefaultNetworkChangeCallback = false;
                this.removeMessages(111);
                this.mConnectivityManager.unregisterNetworkCallback(this.mDefaultNetworkCallback);
            }
            return;
        }
    }

    private void sendRilCommands(int n) {
        if (SubscriptionManager.isValidPhoneId((int)n) && n < this.mNumPhones) {
            Message message = Message.obtain((Handler)this, (int)112, (Object)n);
            int n2 = this.mHalCommandToUse;
            if (n2 != 1 && n2 != 0) {
                n2 = this.mPreferredDataPhoneId;
                if (n == n2) {
                    this.mRadioConfig.setPreferredDataModem(n2, message);
                }
            } else if (this.mNumPhones > 1) {
                this.mCommandsInterfaces[n].setDataAllowed(this.isPhoneActive(n), message);
            }
            return;
        }
    }

    private void sendSetOpptCallbackHelper(ISetOpportunisticDataCallback object, int n) {
        if (object == null) {
            return;
        }
        try {
            object.onComplete(n);
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("RemoteException ");
            ((StringBuilder)object).append((Object)remoteException);
            this.log(((StringBuilder)object).toString());
        }
    }

    private void setOpportunisticDataSubscription(int n, boolean bl, ISetOpportunisticDataCallback iSetOpportunisticDataCallback) {
        if (!this.mSubscriptionController.isActiveSubId(n) && n != Integer.MAX_VALUE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't switch data to inactive subId ");
            stringBuilder.append(n);
            this.log(stringBuilder.toString());
            this.sendSetOpptCallbackHelper(iSetOpportunisticDataCallback, 2);
            return;
        }
        int n2 = n == Integer.MAX_VALUE ? this.mPrimaryDataSubId : n;
        if (this.mValidator.isValidating() && (!bl || n2 != this.mValidator.getSubIdInValidation())) {
            this.mValidator.stopValidation();
        }
        if (n == this.mOpptDataSubId) {
            this.sendSetOpptCallbackHelper(iSetOpportunisticDataCallback, 0);
            return;
        }
        if (this.mValidator.isValidationFeatureSupported() && bl) {
            this.logDataSwitchEvent(n, 1, 3);
            this.registerDefaultNetworkChangeCallback();
            this.mSetOpptSubCallback = iSetOpportunisticDataCallback;
            this.mValidator.validate(n2, 2000, false, this.mValidationCallback);
        } else {
            this.setOpportunisticSubscriptionInternal(n);
            this.sendSetOpptCallbackHelper(iSetOpportunisticDataCallback, 0);
        }
    }

    private void setOpportunisticSubscriptionInternal(int n) {
        if (this.mOpptDataSubId != n) {
            this.mOpptDataSubId = n;
            if (this.onEvaluate(false, "oppt data subId changed")) {
                this.logDataSwitchEvent(this.mOpptDataSubId, 1, 3);
                this.registerDefaultNetworkChangeCallback();
            }
        }
    }

    private void switchPhone(int n, boolean bl) {
        PhoneState phoneState = this.mPhoneStates[n];
        if (phoneState.active == bl) {
            return;
        }
        phoneState.active = bl;
        StringBuilder stringBuilder = new StringBuilder();
        String string = bl ? "activate " : "deactivate ";
        stringBuilder.append(string);
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        phoneState.lastRequested = System.currentTimeMillis();
        this.sendRilCommands(n);
    }

    private void transitionToEmergencyPhone() {
        if (this.mPreferredDataPhoneId != 0) {
            this.log("No active subscriptions: resetting preferred phone to 0 for emergency");
            this.mPreferredDataPhoneId = 0;
        }
        if (this.mPreferredDataSubId != -1) {
            this.mPreferredDataSubId = -1;
            this.notifyPreferredDataSubIdChanged();
        }
    }

    private void updateHalCommandToUse() {
        int n = this.mRadioConfig.isSetPreferredDataCommandSupported() ? 2 : 1;
        this.mHalCommandToUse = n;
    }

    private void updatePreferredDataPhoneId() {
        Phone phone = this.findPhoneById(this.mPhoneIdInVoiceCall);
        Object object = this.mEmergencyOverride;
        if (object != null && this.findPhoneById(((EmergencyOverrideRequest)object).mPhoneId) != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("updatePreferredDataPhoneId: preferred data overridden for emergency. phoneId = ");
            ((StringBuilder)object).append(this.mEmergencyOverride.mPhoneId);
            this.log(((StringBuilder)object).toString());
            this.mPreferredDataPhoneId = this.mEmergencyOverride.mPhoneId;
        } else if (phone != null && phone.getDataEnabledSettings().isDataEnabled(17)) {
            this.mPreferredDataPhoneId = this.mPhoneIdInVoiceCall;
        } else {
            int n;
            int n2 = this.getSubIdForDefaultNetworkRequests();
            int n3 = n = -1;
            if (SubscriptionManager.isUsableSubIdValue((int)n2)) {
                int n4 = 0;
                do {
                    n3 = n;
                    if (n4 >= this.mNumPhones) break;
                    if (this.mPhoneSubscriptions[n4] == n2) {
                        n3 = n4;
                        break;
                    }
                    ++n4;
                } while (true);
            }
            this.mPreferredDataPhoneId = n3;
        }
        this.mPreferredDataSubId = this.mSubscriptionController.getSubIdUsingPhoneId(this.mPreferredDataPhoneId);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter object, String[] arrstring) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)object, "  ");
        indentingPrintWriter.println("PhoneSwitcher:");
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < this.mNumPhones; ++i) {
            object = this.mPhoneStates[i];
            calendar.setTimeInMillis(((PhoneState)object).lastRequested);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PhoneId(");
            stringBuilder.append(i);
            stringBuilder.append(") active=");
            stringBuilder.append(((PhoneState)object).active);
            stringBuilder.append(", lastRequest=");
            object = ((PhoneState)object).lastRequested == 0L ? "never" : String.format("%tm-%td %tH:%tM:%tS.%tL", calendar, calendar, calendar, calendar, calendar, calendar);
            stringBuilder.append((String)object);
            indentingPrintWriter.println(stringBuilder.toString());
        }
        indentingPrintWriter.increaseIndent();
        this.mLocalLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
    }

    public int getOpportunisticDataSubscriptionId() {
        return this.mOpptDataSubId;
    }

    public int getPreferredDataPhoneId() {
        return this.mPreferredDataPhoneId;
    }

    public void handleMessage(Message object) {
        int n = ((Message)object).what;
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        Boolean bl4 = false;
        switch (n) {
            default: {
                break;
            }
            case 116: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Emergency override removed - ");
                ((StringBuilder)object).append(this.mEmergencyOverride);
                this.log(((StringBuilder)object).toString());
                this.mEmergencyOverride = null;
                this.onEvaluate(false, "emer_rm_override_dds");
                break;
            }
            case 115: {
                object = (EmergencyOverrideRequest)((Message)object).obj;
                Object object2 = this.mEmergencyOverride;
                if (object2 != null) {
                    if (((EmergencyOverrideRequest)object2).mPhoneId != ((EmergencyOverrideRequest)object).mPhoneId) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("emergency override requested for phone id ");
                        ((StringBuilder)object2).append(((EmergencyOverrideRequest)object).mPhoneId);
                        ((StringBuilder)object2).append(" when there is already an override in place for phone id ");
                        ((StringBuilder)object2).append(this.mEmergencyOverride.mPhoneId);
                        ((StringBuilder)object2).append(". Ignoring.");
                        this.log(((StringBuilder)object2).toString());
                        if (!((EmergencyOverrideRequest)object).isCallbackAvailable()) break;
                        ((EmergencyOverrideRequest)object).mOverrideCompleteFuture.complete(bl4);
                        break;
                    }
                    if (this.mEmergencyOverride.isCallbackAvailable()) {
                        this.mEmergencyOverride.mOverrideCompleteFuture.complete(bl4);
                    }
                    this.mEmergencyOverride = object;
                } else {
                    this.mEmergencyOverride = object;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("new emergency override - ");
                ((StringBuilder)object).append(this.mEmergencyOverride);
                this.log(((StringBuilder)object).toString());
                this.removeMessages(116);
                this.sendMessageDelayed(this.obtainMessage(116), (long)DEFAULT_DATA_OVERRIDE_TIMEOUT_MS);
                if (this.onEvaluate(false, "emer_override_dds")) break;
                this.mEmergencyOverride.sendOverrideCompleteCallbackResultAndClear(true);
                break;
            }
            case 113: {
                n = (Integer)((Message)object).obj;
                object = new StringBuilder();
                ((StringBuilder)object).append("Resend modem command on phone ");
                ((StringBuilder)object).append(n);
                this.log(((StringBuilder)object).toString());
                this.sendRilCommands(n);
                break;
            }
            case 112: {
                bl4 = (AsyncResult)((Message)object).obj;
                if (bl4 == null || ((AsyncResult)bl4).exception != null) {
                    bl3 = false;
                }
                if (this.mEmergencyOverride != null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Emergency override result sent = ");
                    ((StringBuilder)object).append(bl3);
                    this.log(((StringBuilder)object).toString());
                    this.mEmergencyOverride.sendOverrideCompleteCallbackResultAndClear(bl3);
                    break;
                }
                if (bl3) break;
                n = (Integer)((AsyncResult)bl4).userObj;
                object = new StringBuilder();
                ((StringBuilder)object).append("Modem command failed. with exception ");
                ((StringBuilder)object).append(((AsyncResult)bl4).exception);
                this.log(((StringBuilder)object).toString());
                this.sendMessageDelayed(Message.obtain((Handler)this, (int)113, (Object)n), 5000L);
                break;
            }
            case 111: {
                this.removeDefaultNetworkChangeCallback();
                break;
            }
            case 110: {
                n = ((Message)object).arg1;
                bl3 = ((Message)object).arg2 == 1 ? bl : false;
                this.onValidationDone(n, bl3);
                break;
            }
            case 109: {
                if (!this.isPhoneInVoiceCallChanged()) break;
                object = this.mEmergencyOverride;
                if (object != null && ((EmergencyOverrideRequest)object).mPendingOriginatingCall) {
                    this.removeMessages(116);
                    if (this.mPhoneIdInVoiceCall == -1) {
                        this.sendMessageDelayed(this.obtainMessage(116), (long)(this.mEmergencyOverride.mGnssOverrideTimeMs + ECBM_DEFAULT_DATA_SWITCH_BASE_TIME_MS));
                        this.mEmergencyOverride.mPendingOriginatingCall = false;
                    }
                }
            }
            case 114: {
                if (!this.onEvaluate(false, "EVENT_PRECISE_CALL_STATE_CHANGED")) break;
                this.logDataSwitchEvent(this.mOpptDataSubId, 1, 2);
                this.registerDefaultNetworkChangeCallback();
                break;
            }
            case 108: {
                this.updateHalCommandToUse();
                this.onEvaluate(false, "EVENT_RADIO_AVAILABLE");
                break;
            }
            case 107: {
                n = ((Message)object).arg1;
                bl3 = ((Message)object).arg2 == 1 ? bl2 : false;
                this.setOpportunisticDataSubscription(n, bl3, (ISetOpportunisticDataCallback)((Message)object).obj);
                break;
            }
            case 106: {
                this.sendRilCommands(((Message)object).arg1);
                break;
            }
            case 105: {
                bl3 = this.isInEmergencyCallbackMode();
                if (this.mEmergencyOverride != null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Emergency override - ecbm status = ");
                    ((StringBuilder)object).append(bl3);
                    this.log(((StringBuilder)object).toString());
                    if (bl3) {
                        this.removeMessages(116);
                        this.mEmergencyOverride.mRequiresEcmFinish = true;
                    } else if (this.mEmergencyOverride.mRequiresEcmFinish) {
                        this.sendMessageDelayed(this.obtainMessage(116), (long)this.mEmergencyOverride.mGnssOverrideTimeMs);
                    }
                }
                this.onEvaluate(true, "emergencyToggle");
                break;
            }
            case 104: {
                this.onReleaseNetwork((NetworkRequest)((Message)object).obj);
                break;
            }
            case 103: {
                this.onRequestNetwork((NetworkRequest)((Message)object).obj);
                break;
            }
            case 102: {
                this.onEvaluate(false, "subChanged");
                break;
            }
            case 101: {
                if (!this.onEvaluate(false, "primary data subId changed")) break;
                this.logDataSwitchEvent(this.mOpptDataSubId, 1, 1);
                this.registerDefaultNetworkChangeCallback();
            }
        }
    }

    boolean isEmergencyNetworkRequest(NetworkRequest networkRequest) {
        return networkRequest.hasCapability(10);
    }

    @VisibleForTesting
    protected boolean isPhoneActive(int n) {
        return this.mPhoneStates[n].active;
    }

    public /* synthetic */ void lambda$new$0$PhoneSwitcher(boolean bl, int n) {
        Message.obtain((Handler)this, (int)110, (int)n, (int)bl).sendToTarget();
    }

    public void onRadioCapChanged(int n) {
        this.validatePhoneId(n);
        Message message = this.obtainMessage(106);
        message.arg1 = n;
        message.sendToTarget();
    }

    public void overrideDefaultDataForEmergency(int n, int n2, CompletableFuture<Boolean> completableFuture) {
        this.validatePhoneId(n);
        Message message = this.obtainMessage(115);
        EmergencyOverrideRequest emergencyOverrideRequest = new EmergencyOverrideRequest();
        emergencyOverrideRequest.mPhoneId = n;
        emergencyOverrideRequest.mGnssOverrideTimeMs = n2 * 1000;
        emergencyOverrideRequest.mOverrideCompleteFuture = completableFuture;
        message.obj = emergencyOverrideRequest;
        message.sendToTarget();
    }

    public void registerForActivePhoneSwitch(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mActivePhoneRegistrants.add((Registrant)handler);
        handler.notifyRegistrant();
    }

    public boolean shouldApplyNetworkRequest(NetworkRequest networkRequest, int n) {
        this.validatePhoneId(n);
        boolean bl = this.isPhoneActive(n);
        boolean bl2 = false;
        if (bl && (this.mSubscriptionController.getSubIdUsingPhoneId(n) != -1 || this.isEmergencyNetworkRequest(networkRequest))) {
            if (n == this.phoneIdForRequest(networkRequest)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public void trySetOpportunisticDataSubscription(int n, boolean bl, ISetOpportunisticDataCallback iSetOpportunisticDataCallback) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Try set opportunistic data subscription to subId ");
        stringBuilder.append(n);
        String string = bl ? " with " : " without ";
        stringBuilder.append(string);
        stringBuilder.append("validation");
        this.log(stringBuilder.toString());
        this.obtainMessage(107, n, (int)bl, (Object)iSetOpportunisticDataCallback).sendToTarget();
    }

    public void unregisterForActivePhoneSwitch(Handler handler) {
        this.mActivePhoneRegistrants.remove(handler);
    }

    @VisibleForTesting
    protected void validatePhoneId(int n) {
        if (n >= 0 && n < this.mNumPhones) {
            return;
        }
        throw new IllegalArgumentException("Invalid PhoneId");
    }

    private static final class EmergencyOverrideRequest {
        int mGnssOverrideTimeMs = -1;
        CompletableFuture<Boolean> mOverrideCompleteFuture;
        boolean mPendingOriginatingCall = true;
        int mPhoneId = -1;
        boolean mRequiresEcmFinish = false;

        private EmergencyOverrideRequest() {
        }

        boolean isCallbackAvailable() {
            boolean bl = this.mOverrideCompleteFuture != null;
            return bl;
        }

        void sendOverrideCompleteCallbackResultAndClear(boolean bl) {
            if (this.isCallbackAvailable()) {
                this.mOverrideCompleteFuture.complete(bl);
                this.mOverrideCompleteFuture = null;
            }
        }

        public String toString() {
            return String.format("EmergencyOverrideRequest: [phoneId= %d, overrideMs= %d, hasCallback= %b, ecmFinishStatus= %b]", this.mPhoneId, this.mGnssOverrideTimeMs, this.isCallbackAvailable(), this.mRequiresEcmFinish);
        }
    }

    private static class PhoneState {
        public volatile boolean active = false;
        public long lastRequested = 0L;

        private PhoneState() {
        }
    }

    private static class PhoneSwitcherNetworkRequestListener
    extends NetworkFactory {
        private final PhoneSwitcher mPhoneSwitcher;

        public PhoneSwitcherNetworkRequestListener(Looper looper, Context context, NetworkCapabilities networkCapabilities, PhoneSwitcher phoneSwitcher) {
            super(looper, context, "PhoneSwitcherNetworkRequstListener", networkCapabilities);
            this.mPhoneSwitcher = phoneSwitcher;
        }

        protected void needNetworkFor(NetworkRequest networkRequest, int n) {
            Message message = this.mPhoneSwitcher.obtainMessage(103);
            message.obj = networkRequest;
            message.sendToTarget();
        }

        protected void releaseNetworkFor(NetworkRequest networkRequest) {
            Message message = this.mPhoneSwitcher.obtainMessage(104);
            message.obj = networkRequest;
            message.sendToTarget();
        }
    }

}

