/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.net.ConnectivityManager
 *  android.net.ConnectivityManager$NetworkCallback
 *  android.net.Network
 *  android.net.NetworkCapabilities
 *  android.net.NetworkInfo
 *  android.net.NetworkRequest
 *  android.net.NetworkRequest$Builder
 *  android.net.NetworkStats
 *  android.net.NetworkStats$Entry
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.RemoteException
 *  android.os.SystemClock
 *  android.os.SystemProperties
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.telecom.ConferenceParticipant
 *  android.telecom.Connection
 *  android.telecom.Connection$RttTextStream
 *  android.telecom.Connection$VideoProvider
 *  android.telecom.TelecomManager
 *  android.telecom.VideoProfile
 *  android.telephony.CallQuality
 *  android.telephony.CarrierConfigManager
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.emergency.EmergencyNumber
 *  android.telephony.ims.ImsCallProfile
 *  android.telephony.ims.ImsCallSession
 *  android.telephony.ims.ImsMmTelManager
 *  android.telephony.ims.ImsMmTelManager$CapabilityCallback
 *  android.telephony.ims.ImsMmTelManager$RegistrationCallback
 *  android.telephony.ims.ImsReasonInfo
 *  android.telephony.ims.ImsStreamMediaProfile
 *  android.telephony.ims.ImsSuppServiceNotification
 *  android.telephony.ims.ProvisioningManager
 *  android.telephony.ims.ProvisioningManager$Callback
 *  android.telephony.ims.aidl.IImsConfigCallback
 *  android.telephony.ims.feature.ImsFeature
 *  android.telephony.ims.feature.ImsFeature$Capabilities
 *  android.telephony.ims.feature.MmTelFeature
 *  android.telephony.ims.feature.MmTelFeature$Listener
 *  android.telephony.ims.feature.MmTelFeature$MmTelCapabilities
 *  android.text.TextUtils
 *  android.util.ArrayMap
 *  android.util.Log
 *  android.util.Pair
 *  com.android.ims.ImsCall
 *  com.android.ims.ImsCall$Listener
 *  com.android.ims.ImsConfig
 *  com.android.ims.ImsConfigListener
 *  com.android.ims.ImsConfigListener$Stub
 *  com.android.ims.ImsEcbm
 *  com.android.ims.ImsEcbmStateListener
 *  com.android.ims.ImsException
 *  com.android.ims.ImsExternalCallStateListener
 *  com.android.ims.ImsManager
 *  com.android.ims.ImsManager$Connector
 *  com.android.ims.ImsManager$Connector$Listener
 *  com.android.ims.ImsManager$Connector$RetryTimeout
 *  com.android.ims.ImsMultiEndpoint
 *  com.android.ims.ImsUtInterface
 *  com.android.ims.internal.IImsCallSession
 *  com.android.ims.internal.IImsVideoCallProvider
 *  com.android.ims.internal.ImsVideoCallProviderWrapper
 *  com.android.ims.internal.ImsVideoCallProviderWrapper$ImsVideoProviderWrapperCallback
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.os.SomeArgs
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 *  com.android.internal.telephony.imsphone.-$
 *  com.android.internal.telephony.imsphone.-$$Lambda
 *  com.android.internal.telephony.imsphone.-$$Lambda$ImsPhoneCallTracker
 *  com.android.internal.telephony.imsphone.-$$Lambda$ImsPhoneCallTracker$QlPVd_3u4_verjHUDnkn6zaSe54
 *  com.android.internal.telephony.imsphone.-$$Lambda$ImsPhoneCallTracker$Zw03itjXT6-LrhiYuD-9nKFg2Wg
 */
package com.android.internal.telephony.imsphone;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.NetworkStats;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telecom.ConferenceParticipant;
import android.telecom.Connection;
import android.telecom.TelecomManager;
import android.telecom.VideoProfile;
import android.telephony.CallQuality;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsCallSession;
import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;
import android.telephony.ims.ProvisioningManager;
import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.feature.ImsFeature;
import android.telephony.ims.feature.MmTelFeature;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.ims.ImsCall;
import com.android.ims.ImsConfig;
import com.android.ims.ImsConfigListener;
import com.android.ims.ImsEcbm;
import com.android.ims.ImsEcbmStateListener;
import com.android.ims.ImsException;
import com.android.ims.ImsExternalCallStateListener;
import com.android.ims.ImsManager;
import com.android.ims.ImsMultiEndpoint;
import com.android.ims.ImsUtInterface;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsVideoCallProvider;
import com.android.ims.internal.ImsVideoCallProviderWrapper;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.SomeArgs;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.CallTracker;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.LocaleTracker;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneInternalInterface;
import com.android.internal.telephony.RestrictedState;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import com.android.internal.telephony.gsm.SuppServiceNotification;
import com.android.internal.telephony.imsphone.-$;
import com.android.internal.telephony.imsphone.ImsExternalCallTracker;
import com.android.internal.telephony.imsphone.ImsPhone;
import com.android.internal.telephony.imsphone.ImsPhoneCall;
import com.android.internal.telephony.imsphone.ImsPhoneConnection;
import com.android.internal.telephony.imsphone.ImsPhoneMmiCode;
import com.android.internal.telephony.imsphone.ImsPullCall;
import com.android.internal.telephony.imsphone._$$Lambda$ImsPhoneCallTracker$QlPVd_3u4_verjHUDnkn6zaSe54;
import com.android.internal.telephony.imsphone._$$Lambda$ImsPhoneCallTracker$R2Z9jNp4rrTM4H39vy492Fbmqyc;
import com.android.internal.telephony.imsphone._$$Lambda$ImsPhoneCallTracker$Zw03itjXT6_LrhiYuD_9nKFg2Wg;
import com.android.internal.telephony.metrics.CallQualityMetrics;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ImsPhoneCallTracker
extends CallTracker
implements ImsPullCall {
    private static final boolean DBG = true;
    private static final int EVENT_ANSWER_WAITING_CALL = 30;
    private static final int EVENT_CHECK_FOR_WIFI_HANDOVER = 25;
    private static final int EVENT_DATA_ENABLED_CHANGED = 23;
    private static final int EVENT_DIAL_PENDINGMO = 20;
    private static final int EVENT_EXIT_ECBM_BEFORE_PENDINGMO = 21;
    private static final int EVENT_HANGUP_PENDINGMO = 18;
    private static final int EVENT_ON_FEATURE_CAPABILITY_CHANGED = 26;
    private static final int EVENT_REDIAL_WIFI_E911_CALL = 28;
    private static final int EVENT_REDIAL_WIFI_E911_TIMEOUT = 29;
    private static final int EVENT_RESUME_NOW_FOREGROUND_CALL = 31;
    private static final int EVENT_SUPP_SERVICE_INDICATION = 27;
    private static final int EVENT_VT_DATA_USAGE_UPDATE = 22;
    private static final boolean FORCE_VERBOSE_STATE_LOGGING = false;
    private static final int HANDOVER_TO_WIFI_TIMEOUT_MS = 60000;
    static final String LOG_TAG = "ImsPhoneCallTracker";
    private static final int MAX_CALL_QUALITY_HISTORY = 10;
    static final int MAX_CONNECTIONS = 7;
    static final int MAX_CONNECTIONS_PER_CALL = 5;
    private static final int TIMEOUT_HANGUP_PENDINGMO = 500;
    private static final int TIMEOUT_PARTICIPANT_CONNECT_TIME_CACHE_MS = 60000;
    private static final int TIMEOUT_REDIAL_WIFI_E911_MS = 10000;
    private static final boolean VERBOSE_STATE_LOGGING = Rlog.isLoggable((String)"IPCTState", (int)2);
    static final String VERBOSE_STATE_TAG = "IPCTState";
    private boolean mAllowAddCallDuringVideoCall = true;
    @UnsupportedAppUsage
    private boolean mAllowEmergencyVideoCalls = false;
    private boolean mAlwaysPlayRemoteHoldTone = false;
    private boolean mAutoRetryFailedWifiEmergencyCall = false;
    @UnsupportedAppUsage
    public ImsPhoneCall mBackgroundCall = new ImsPhoneCall(this, "BG");
    private ImsCall mCallExpectedToResume = null;
    private final Map<String, CallQualityMetrics> mCallQualityMetrics = new ConcurrentHashMap<String, CallQualityMetrics>();
    private final ConcurrentLinkedQueue<CallQualityMetrics> mCallQualityMetricsHistory = new ConcurrentLinkedQueue();
    private boolean mCarrierConfigLoaded = false;
    private int mClirMode = 0;
    private final ProvisioningManager.Callback mConfigCallback = new ProvisioningManager.Callback(){

        private void sendConfigChangedIntent(int n, String string) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendConfigChangedIntent - [");
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append(string);
            stringBuilder.append("]");
            imsPhoneCallTracker.log(stringBuilder.toString());
            stringBuilder = new Intent("com.android.intent.action.IMS_CONFIG_CHANGED");
            stringBuilder.putExtra("item", n);
            stringBuilder.putExtra("value", string);
            if (ImsPhoneCallTracker.this.mPhone != null && ImsPhoneCallTracker.this.mPhone.getContext() != null) {
                ImsPhoneCallTracker.this.mPhone.getContext().sendBroadcast((Intent)stringBuilder);
            }
        }

        public void onProvisioningIntChanged(int n, int n2) {
            this.sendConfigChangedIntent(n, Integer.toString(n2));
        }

        public void onProvisioningStringChanged(int n, String string) {
            this.sendConfigChangedIntent(n, string);
        }
    };
    @UnsupportedAppUsage
    private ArrayList<ImsPhoneConnection> mConnections = new ArrayList();
    private final AtomicInteger mDefaultDialerUid = new AtomicInteger(-1);
    private boolean mDesiredMute = false;
    private boolean mDropVideoCallWhenAnsweringAudioCall = false;
    @UnsupportedAppUsage
    public ImsPhoneCall mForegroundCall = new ImsPhoneCall(this, "FG");
    @UnsupportedAppUsage
    public ImsPhoneCall mHandoverCall = new ImsPhoneCall(this, "HO");
    private boolean mHasAttemptedStartOfCallHandover = false;
    private HoldSwapState mHoldSwitchingState = HoldSwapState.INACTIVE;
    private boolean mIgnoreDataEnabledChangedForVideoCalls = false;
    private ImsCall.Listener mImsCallListener = new ImsCall.Listener(){

        private void updateConferenceParticipantsTiming(List<ConferenceParticipant> object) {
            object = object.iterator();
            while (object.hasNext()) {
                ConferenceParticipant conferenceParticipant = (ConferenceParticipant)object.next();
                CacheEntry cacheEntry = ImsPhoneCallTracker.this.findConnectionTimeUsePhoneNumber(conferenceParticipant);
                if (cacheEntry == null) continue;
                conferenceParticipant.setConnectTime(cacheEntry.mConnectTime);
                conferenceParticipant.setConnectElapsedTime(cacheEntry.mConnectElapsedTime);
                conferenceParticipant.setCallDirection(cacheEntry.mCallDirection);
            }
        }

        public void onCallHandover(ImsCall imsCall, int n, int n2, ImsReasonInfo imsReasonInfo) {
            boolean bl = ImsPhoneCallTracker.this.mPhone.getDefaultPhone().getDataEnabledSettings().isDataEnabled();
            Object object = ImsPhoneCallTracker.this;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("onCallHandover ::  srcAccessTech=");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(", targetAccessTech=");
            ((StringBuilder)object2).append(n2);
            ((StringBuilder)object2).append(", reasonInfo=");
            ((StringBuilder)object2).append((Object)imsReasonInfo);
            ((StringBuilder)object2).append(", dataEnabled=");
            ((StringBuilder)object2).append(ImsPhoneCallTracker.this.mIsDataEnabled);
            ((StringBuilder)object2).append("/");
            ((StringBuilder)object2).append(bl);
            ((StringBuilder)object2).append(", dataMetered=");
            ((StringBuilder)object2).append(ImsPhoneCallTracker.this.mIsViLteDataMetered);
            ((ImsPhoneCallTracker)object).log(((StringBuilder)object2).toString());
            if (ImsPhoneCallTracker.this.mIsDataEnabled != bl) {
                object2 = ImsPhoneCallTracker.this;
                object = new StringBuilder();
                ((StringBuilder)object).append("onCallHandover: data enabled state doesn't match! (was=");
                ((StringBuilder)object).append(ImsPhoneCallTracker.this.mIsDataEnabled);
                ((StringBuilder)object).append(", actually=");
                ((StringBuilder)object).append(bl);
                ((ImsPhoneCallTracker)object2).loge(((StringBuilder)object).toString());
                ImsPhoneCallTracker.this.mIsDataEnabled = bl;
            }
            boolean bl2 = false;
            boolean bl3 = n != 0 && n != 18 && n2 == 18;
            boolean bl4 = bl2;
            if (n == 18) {
                bl4 = bl2;
                if (n2 != 0) {
                    bl4 = bl2;
                    if (n2 != 18) {
                        bl4 = true;
                    }
                }
            }
            if ((object = ImsPhoneCallTracker.this.findConnection(imsCall)) != null) {
                object2 = ((ImsPhoneConnection)object).getCall();
                if (object2 != null) {
                    ((ImsPhoneCall)object2).maybeStopRingback();
                }
                if (((Connection)object).getDisconnectCause() == 0) {
                    if (bl3) {
                        ImsPhoneCallTracker.this.removeMessages(25);
                        if (ImsPhoneCallTracker.this.mNotifyHandoverVideoFromLTEToWifi && ImsPhoneCallTracker.this.mHasAttemptedStartOfCallHandover) {
                            ((Connection)object).onConnectionEvent("android.telephony.event.EVENT_HANDOVER_VIDEO_FROM_LTE_TO_WIFI", null);
                        }
                        ImsPhoneCallTracker.this.unregisterForConnectivityChanges();
                    } else if (bl4 && imsCall.isVideoCall()) {
                        ImsPhoneCallTracker.this.registerForConnectivityChanges();
                    }
                }
                if (bl3 && ImsPhoneCallTracker.this.mIsViLteDataMetered) {
                    ((ImsPhoneConnection)object).setLocalVideoCapable(true);
                }
                if (bl4 && imsCall.isVideoCall()) {
                    if (ImsPhoneCallTracker.this.mIsViLteDataMetered) {
                        ((ImsPhoneConnection)object).setLocalVideoCapable(ImsPhoneCallTracker.this.mIsDataEnabled);
                    }
                    if (ImsPhoneCallTracker.this.mNotifyHandoverVideoFromWifiToLTE && ImsPhoneCallTracker.this.mIsDataEnabled) {
                        if (((Connection)object).getDisconnectCause() == 0) {
                            ImsPhoneCallTracker.this.log("onCallHandover :: notifying of WIFI to LTE handover.");
                            ((Connection)object).onConnectionEvent("android.telephony.event.EVENT_HANDOVER_VIDEO_FROM_WIFI_TO_LTE", null);
                        } else {
                            ImsPhoneCallTracker.this.log("onCallHandover :: skip notify of WIFI to LTE handover for disconnected call.");
                        }
                    }
                    if (!ImsPhoneCallTracker.this.mIsDataEnabled && ImsPhoneCallTracker.this.mIsViLteDataMetered) {
                        ImsPhoneCallTracker.this.log("onCallHandover :: data is not enabled; attempt to downgrade.");
                        ImsPhoneCallTracker.this.downgradeVideoCall(1407, (ImsPhoneConnection)object);
                    }
                }
            } else {
                ImsPhoneCallTracker.this.loge("onCallHandover :: connection null.");
            }
            if (!ImsPhoneCallTracker.this.mHasAttemptedStartOfCallHandover) {
                ImsPhoneCallTracker.this.mHasAttemptedStartOfCallHandover = true;
            }
            ImsPhoneCallTracker.this.mMetrics.writeOnImsCallHandoverEvent(ImsPhoneCallTracker.this.mPhone.getPhoneId(), 18, imsCall.getCallSession(), n, n2, imsReasonInfo);
        }

        public void onCallHandoverFailed(ImsCall imsCall, int n, int n2, ImsReasonInfo object) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCallHandoverFailed :: srcAccessTech=");
            stringBuilder.append(n);
            stringBuilder.append(", targetAccessTech=");
            stringBuilder.append(n2);
            stringBuilder.append(", reasonInfo=");
            stringBuilder.append(object);
            imsPhoneCallTracker.log(stringBuilder.toString());
            ImsPhoneCallTracker.this.mMetrics.writeOnImsCallHandoverEvent(ImsPhoneCallTracker.this.mPhone.getPhoneId(), 19, imsCall.getCallSession(), n, n2, (ImsReasonInfo)object);
            n = n != 18 && n2 == 18 ? 1 : 0;
            object = ImsPhoneCallTracker.this.findConnection(imsCall);
            if (object != null && n != 0) {
                ImsPhoneCallTracker.this.log("onCallHandoverFailed - handover to WIFI Failed");
                ImsPhoneCallTracker.this.removeMessages(25);
                if (imsCall.isVideoCall() && ((Connection)object).getDisconnectCause() == 0) {
                    ImsPhoneCallTracker.this.registerForConnectivityChanges();
                }
                if (ImsPhoneCallTracker.this.mNotifyVtHandoverToWifiFail) {
                    ((Connection)object).onHandoverToWifiFailed();
                }
            }
            if (!ImsPhoneCallTracker.this.mHasAttemptedStartOfCallHandover) {
                ImsPhoneCallTracker.this.mHasAttemptedStartOfCallHandover = true;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onCallHeld(ImsCall imsCall) {
            Object object;
            Object object2;
            if (ImsPhoneCallTracker.this.mForegroundCall.getImsCall() == imsCall) {
                object = ImsPhoneCallTracker.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("onCallHeld (fg) ");
                ((StringBuilder)object2).append((Object)imsCall);
                ((ImsPhoneCallTracker)object).log(((StringBuilder)object2).toString());
            } else if (ImsPhoneCallTracker.this.mBackgroundCall.getImsCall() == imsCall) {
                object = ImsPhoneCallTracker.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("onCallHeld (bg) ");
                ((StringBuilder)object2).append((Object)imsCall);
                ((ImsPhoneCallTracker)object).log(((StringBuilder)object2).toString());
            }
            object2 = ImsPhoneCallTracker.this.mSyncHold;
            synchronized (object2) {
                object = ImsPhoneCallTracker.this.mBackgroundCall.getState();
                ImsPhoneCallTracker.this.processCallStateChange(imsCall, Call.State.HOLDING, 0);
                if (object == Call.State.ACTIVE) {
                    if (ImsPhoneCallTracker.this.mForegroundCall.getState() == Call.State.HOLDING && ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.SWAPPING_ACTIVE_AND_HELD) {
                        ImsPhoneCallTracker.this.sendEmptyMessage(31);
                    } else if (ImsPhoneCallTracker.this.mRingingCall.getState() == Call.State.WAITING && ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.HOLDING_TO_ANSWER_INCOMING) {
                        ImsPhoneCallTracker.this.sendEmptyMessage(30);
                    } else if (ImsPhoneCallTracker.this.mPendingMO != null && ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.HOLDING_TO_DIAL_OUTGOING) {
                        ImsPhoneCallTracker.this.dialPendingMO();
                        ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                        ImsPhoneCallTracker.this.logHoldSwapState("onCallHeld hold to dial");
                    } else {
                        ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                        ImsPhoneCallTracker.this.logHoldSwapState("onCallHeld normal case");
                    }
                } else if (object == Call.State.IDLE && (ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.SWAPPING_ACTIVE_AND_HELD || ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.HOLDING_TO_ANSWER_INCOMING) && ImsPhoneCallTracker.this.mForegroundCall.getState() == Call.State.HOLDING) {
                    ImsPhoneCallTracker.this.sendEmptyMessage(31);
                    ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                    ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                    ImsPhoneCallTracker.this.logHoldSwapState("onCallHeld premature termination of other call");
                }
            }
            ImsPhoneCallTracker.this.mMetrics.writeOnImsCallHeld(ImsPhoneCallTracker.this.mPhone.getPhoneId(), imsCall.getCallSession());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onCallHoldFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
            Object object = ImsPhoneCallTracker.this;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("onCallHoldFailed reasonCode=");
            ((StringBuilder)object2).append(imsReasonInfo.getCode());
            ((ImsPhoneCallTracker)object).log(((StringBuilder)object2).toString());
            object2 = ImsPhoneCallTracker.this.mSyncHold;
            synchronized (object2) {
                object = ImsPhoneCallTracker.this.mBackgroundCall.getState();
                if (imsReasonInfo.getCode() == 148) {
                    if (ImsPhoneCallTracker.this.mPendingMO != null) {
                        ImsPhoneCallTracker.this.dialPendingMO();
                    } else if (ImsPhoneCallTracker.this.mRingingCall.getState() == Call.State.WAITING && ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.HOLDING_TO_ANSWER_INCOMING) {
                        ImsPhoneCallTracker.this.sendEmptyMessage(30);
                    }
                    ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                } else if (ImsPhoneCallTracker.this.mPendingMO != null && ImsPhoneCallTracker.this.mPendingMO.isEmergency()) {
                    ImsPhoneCallTracker.this.mBackgroundCall.getImsCall().terminate(0);
                    if (imsCall != ImsPhoneCallTracker.this.mCallExpectedToResume) {
                        ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                    }
                } else if (ImsPhoneCallTracker.this.mRingingCall.getState() == Call.State.WAITING && ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.HOLDING_TO_ANSWER_INCOMING) {
                    ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                    ImsPhoneCallTracker.this.mForegroundCall.switchWith(ImsPhoneCallTracker.this.mBackgroundCall);
                    ImsPhoneCallTracker.this.logHoldSwapState("onCallHoldFailed unable to answer waiting call");
                } else if (object == Call.State.ACTIVE) {
                    ImsPhoneCallTracker.this.mForegroundCall.switchWith(ImsPhoneCallTracker.this.mBackgroundCall);
                    if (ImsPhoneCallTracker.this.mPendingMO != null) {
                        ImsPhoneCallTracker.this.mPendingMO.setDisconnectCause(36);
                        ImsPhoneCallTracker.this.sendEmptyMessageDelayed(18, 500L);
                    }
                    if (imsCall != ImsPhoneCallTracker.this.mCallExpectedToResume) {
                        ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                    }
                    ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                }
                object = ImsPhoneCallTracker.this.findConnection(imsCall);
                if (object != null) {
                    ((Connection)object).onConnectionEvent("android.telecom.event.CALL_HOLD_FAILED", null);
                }
                ImsPhoneCallTracker.this.mPhone.notifySuppServiceFailed(PhoneInternalInterface.SuppService.HOLD);
            }
            ImsPhoneCallTracker.this.mMetrics.writeOnImsCallHoldFailed(ImsPhoneCallTracker.this.mPhone.getPhoneId(), imsCall.getCallSession(), imsReasonInfo);
        }

        public void onCallHoldReceived(ImsCall imsCall) {
            ImsPhoneCallTracker.this.onCallHoldReceived(imsCall);
        }

        public void onCallMergeFailed(ImsCall object, ImsReasonInfo imsReasonInfo) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCallMergeFailed reasonInfo=");
            stringBuilder.append((Object)imsReasonInfo);
            imsPhoneCallTracker.log(stringBuilder.toString());
            ImsPhoneCallTracker.this.mPhone.notifySuppServiceFailed(PhoneInternalInterface.SuppService.CONFERENCE);
            object.resetIsMergeRequestedByConf(false);
            object = ImsPhoneCallTracker.this.findConnection((ImsCall)object);
            if (object != null) {
                ((Connection)object).onConferenceMergeFailed();
                ((ImsPhoneConnection)object).handleMergeComplete();
            }
        }

        public void onCallMerged(ImsCall imsCall, ImsCall object, boolean bl) {
            ImsPhoneCallTracker.this.log("onCallMerged");
            Object object2 = ImsPhoneCallTracker.this.findConnection(imsCall).getCall();
            ImsPhoneConnection imsPhoneConnection = ImsPhoneCallTracker.this.findConnection((ImsCall)object);
            object = imsPhoneConnection == null ? null : imsPhoneConnection.getCall();
            if (bl) {
                ImsPhoneCallTracker.this.switchAfterConferenceSuccess();
            }
            ((ImsPhoneCall)object2).merge((ImsPhoneCall)object, Call.State.ACTIVE);
            try {
                object = ImsPhoneCallTracker.this.findConnection(imsCall);
                object2 = ImsPhoneCallTracker.this;
                Object object3 = new StringBuilder();
                ((StringBuilder)object3).append("onCallMerged: ImsPhoneConnection=");
                ((StringBuilder)object3).append(object);
                ((ImsPhoneCallTracker)object2).log(((StringBuilder)object3).toString());
                object3 = ImsPhoneCallTracker.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("onCallMerged: CurrentVideoProvider=");
                ((StringBuilder)object2).append((Object)((Connection)object).getVideoProvider());
                ((ImsPhoneCallTracker)object3).log(((StringBuilder)object2).toString());
                ImsPhoneCallTracker.this.setVideoCallProvider((ImsPhoneConnection)object, imsCall);
                object3 = ImsPhoneCallTracker.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("onCallMerged: CurrentVideoProvider=");
                ((StringBuilder)object2).append((Object)((Connection)object).getVideoProvider());
                ((ImsPhoneCallTracker)object3).log(((StringBuilder)object2).toString());
            }
            catch (Exception exception) {
                object = ImsPhoneCallTracker.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onCallMerged: exception ");
                stringBuilder.append(exception);
                ((ImsPhoneCallTracker)object).loge(stringBuilder.toString());
            }
            object = ImsPhoneCallTracker.this;
            ((ImsPhoneCallTracker)object).processCallStateChange(((ImsPhoneCallTracker)object).mForegroundCall.getImsCall(), Call.State.ACTIVE, 0);
            if (imsPhoneConnection != null) {
                object = ImsPhoneCallTracker.this;
                ((ImsPhoneCallTracker)object).processCallStateChange(((ImsPhoneCallTracker)object).mBackgroundCall.getImsCall(), Call.State.HOLDING, 0);
            }
            if (!imsCall.isMergeRequestedByConf()) {
                ImsPhoneCallTracker.this.log("onCallMerged :: calling onMultipartyStateChanged()");
                this.onMultipartyStateChanged(imsCall, true);
            } else {
                ImsPhoneCallTracker.this.log("onCallMerged :: Merge requested by existing conference.");
                imsCall.resetIsMergeRequestedByConf(false);
            }
            ImsPhoneCallTracker.this.logState();
        }

        public void onCallProgressing(ImsCall imsCall) {
            ImsPhoneCallTracker.this.log("onCallProgressing");
            ImsPhoneCallTracker.this.mPendingMO = null;
            ImsPhoneCallTracker.this.processCallStateChange(imsCall, Call.State.ALERTING, 0);
            ImsPhoneCallTracker.this.mMetrics.writeOnImsCallProgressing(ImsPhoneCallTracker.this.mPhone.getPhoneId(), imsCall.getCallSession());
        }

        public void onCallQualityChanged(ImsCall object, CallQuality callQuality) {
            ImsPhoneCallTracker.this.mPhone.onCallQualityChanged(callQuality, ServiceState.rilRadioTechnologyToNetworkType((int)object.getRadioTechnology()));
            String string = object.getSession().getCallId();
            CallQualityMetrics callQualityMetrics = (CallQualityMetrics)ImsPhoneCallTracker.this.mCallQualityMetrics.get(string);
            object = callQualityMetrics;
            if (callQualityMetrics == null) {
                object = new CallQualityMetrics(ImsPhoneCallTracker.this.mPhone);
            }
            ((CallQualityMetrics)object).saveCallQuality(callQuality);
            ImsPhoneCallTracker.this.mCallQualityMetrics.put(string, object);
        }

        public void onCallResumeFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
            if (ImsPhoneCallTracker.this.mHoldSwitchingState != HoldSwapState.SWAPPING_ACTIVE_AND_HELD && ImsPhoneCallTracker.this.mHoldSwitchingState != HoldSwapState.PENDING_RESUME_FOREGROUND_AFTER_FAILURE) {
                if (ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.PENDING_SINGLE_CALL_UNHOLD) {
                    if (imsCall == ImsPhoneCallTracker.this.mCallExpectedToResume) {
                        ImsPhoneCallTracker.this.log("onCallResumeFailed: single call unhold case");
                        ImsPhoneCallTracker.this.mForegroundCall.switchWith(ImsPhoneCallTracker.this.mBackgroundCall);
                        ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                        ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                        ImsPhoneCallTracker.this.logHoldSwapState("onCallResumeFailed: single call");
                    } else {
                        Rlog.w((String)ImsPhoneCallTracker.LOG_TAG, (String)"onCallResumeFailed: got a resume failed for a different call in the single call unhold case");
                    }
                }
            } else {
                if (imsCall == ImsPhoneCallTracker.this.mCallExpectedToResume) {
                    ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onCallResumeFailed : switching ");
                    stringBuilder.append(ImsPhoneCallTracker.this.mForegroundCall);
                    stringBuilder.append(" with ");
                    stringBuilder.append(ImsPhoneCallTracker.this.mBackgroundCall);
                    imsPhoneCallTracker.log(stringBuilder.toString());
                    ImsPhoneCallTracker.this.mForegroundCall.switchWith(ImsPhoneCallTracker.this.mBackgroundCall);
                    if (ImsPhoneCallTracker.this.mForegroundCall.getState() == Call.State.HOLDING) {
                        ImsPhoneCallTracker.this.sendEmptyMessage(31);
                    }
                }
                ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                ImsPhoneCallTracker.this.logHoldSwapState("onCallResumeFailed: multi calls");
            }
            ImsPhoneCallTracker.this.mPhone.notifySuppServiceFailed(PhoneInternalInterface.SuppService.RESUME);
            ImsPhoneCallTracker.this.mMetrics.writeOnImsCallResumeFailed(ImsPhoneCallTracker.this.mPhone.getPhoneId(), imsCall.getCallSession(), imsReasonInfo);
        }

        public void onCallResumeReceived(ImsCall imsCall) {
            ImsPhoneCallTracker.this.log("onCallResumeReceived");
            Object object = ImsPhoneCallTracker.this.findConnection(imsCall);
            if (object != null) {
                if (ImsPhoneCallTracker.this.mOnHoldToneStarted) {
                    ImsPhoneCallTracker.this.mPhone.stopOnHoldTone((Connection)object);
                    ImsPhoneCallTracker.this.mOnHoldToneStarted = false;
                }
                ((Connection)object).onConnectionEvent("android.telecom.event.CALL_REMOTELY_UNHELD", null);
            }
            if (ImsPhoneCallTracker.this.mPhone.getContext().getResources().getBoolean(17891562) && ImsPhoneCallTracker.this.mSupportPauseVideo && VideoProfile.isVideo((int)((Connection)object).getVideoState())) {
                ((ImsPhoneConnection)object).changeToUnPausedState();
            }
            object = new SuppServiceNotification();
            ((SuppServiceNotification)object).notificationType = 1;
            ((SuppServiceNotification)object).code = 3;
            ImsPhoneCallTracker.this.mPhone.notifySuppSvcNotification((SuppServiceNotification)object);
            ImsPhoneCallTracker.this.mMetrics.writeOnImsCallResumeReceived(ImsPhoneCallTracker.this.mPhone.getPhoneId(), imsCall.getCallSession());
        }

        public void onCallResumed(ImsCall imsCall) {
            ImsPhoneCallTracker.this.log("onCallResumed");
            if (ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.SWAPPING_ACTIVE_AND_HELD || ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.PENDING_RESUME_FOREGROUND_AFTER_FAILURE || ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.PENDING_SINGLE_CALL_UNHOLD) {
                if (imsCall != ImsPhoneCallTracker.this.mCallExpectedToResume) {
                    ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onCallResumed : switching ");
                    stringBuilder.append(ImsPhoneCallTracker.this.mForegroundCall);
                    stringBuilder.append(" with ");
                    stringBuilder.append(ImsPhoneCallTracker.this.mBackgroundCall);
                    imsPhoneCallTracker.log(stringBuilder.toString());
                    ImsPhoneCallTracker.this.mForegroundCall.switchWith(ImsPhoneCallTracker.this.mBackgroundCall);
                } else {
                    ImsPhoneCallTracker.this.log("onCallResumed : expected call resumed.");
                }
                ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                ImsPhoneCallTracker.this.logHoldSwapState("onCallResumed");
            }
            ImsPhoneCallTracker.this.processCallStateChange(imsCall, Call.State.ACTIVE, 0);
            ImsPhoneCallTracker.this.mMetrics.writeOnImsCallResumed(ImsPhoneCallTracker.this.mPhone.getPhoneId(), imsCall.getCallSession());
        }

        public void onCallSessionTtyModeReceived(ImsCall imsCall, int n) {
            ImsPhoneCallTracker.this.mPhone.onTtyModeReceived(n);
        }

        public void onCallStartFailed(ImsCall object, ImsReasonInfo imsReasonInfo) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCallStartFailed reasonCode=");
            stringBuilder.append(imsReasonInfo.getCode());
            imsPhoneCallTracker.log(stringBuilder.toString());
            if (ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.HOLDING_TO_ANSWER_INCOMING && ImsPhoneCallTracker.this.mCallExpectedToResume != null && ImsPhoneCallTracker.this.mCallExpectedToResume == object) {
                ImsPhoneCallTracker.this.log("onCallStarted: starting a call as a result of a switch.");
                ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                ImsPhoneCallTracker.this.logHoldSwapState("onCallStartFailed");
            }
            if (ImsPhoneCallTracker.this.mPendingMO != null) {
                if (imsReasonInfo.getCode() == 146 && ImsPhoneCallTracker.this.mBackgroundCall.getState() == Call.State.IDLE && ImsPhoneCallTracker.this.mRingingCall.getState() == Call.State.IDLE) {
                    ImsPhoneCallTracker.this.mForegroundCall.detach(ImsPhoneCallTracker.this.mPendingMO);
                    object = ImsPhoneCallTracker.this;
                    ((ImsPhoneCallTracker)object).removeConnection(((ImsPhoneCallTracker)object).mPendingMO);
                    ImsPhoneCallTracker.this.mPendingMO.finalize();
                    ImsPhoneCallTracker.this.mPendingMO = null;
                    ImsPhoneCallTracker.this.mPhone.initiateSilentRedial();
                    return;
                }
                ImsPhoneCallTracker.this.sendCallStartFailedDisconnect((ImsCall)object, imsReasonInfo);
                ImsPhoneCallTracker.this.mMetrics.writeOnImsCallStartFailed(ImsPhoneCallTracker.this.mPhone.getPhoneId(), object.getCallSession(), imsReasonInfo);
            }
        }

        public void onCallStarted(ImsCall imsCall) {
            ImsPhoneCallTracker.this.log("onCallStarted");
            if (ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.HOLDING_TO_ANSWER_INCOMING && ImsPhoneCallTracker.this.mCallExpectedToResume != null && ImsPhoneCallTracker.this.mCallExpectedToResume == imsCall) {
                ImsPhoneCallTracker.this.log("onCallStarted: starting a call as a result of a switch.");
                ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                ImsPhoneCallTracker.this.logHoldSwapState("onCallStarted");
            }
            ImsPhoneCallTracker.this.mPendingMO = null;
            ImsPhoneCallTracker.this.processCallStateChange(imsCall, Call.State.ACTIVE, 0);
            if (ImsPhoneCallTracker.this.mNotifyVtHandoverToWifiFail && imsCall.isVideoCall() && !imsCall.isWifiCall()) {
                if (ImsPhoneCallTracker.this.isWifiConnected()) {
                    ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
                    imsPhoneCallTracker.sendMessageDelayed(imsPhoneCallTracker.obtainMessage(25, (Object)imsCall), 60000L);
                    ImsPhoneCallTracker.this.mHasAttemptedStartOfCallHandover = false;
                } else {
                    ImsPhoneCallTracker.this.registerForConnectivityChanges();
                    ImsPhoneCallTracker.this.mHasAttemptedStartOfCallHandover = true;
                }
            }
            ImsPhoneCallTracker.this.mMetrics.writeOnImsCallStarted(ImsPhoneCallTracker.this.mPhone.getPhoneId(), imsCall.getCallSession());
        }

        public void onCallSuppServiceReceived(ImsCall object, ImsSuppServiceNotification imsSuppServiceNotification) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("onCallSuppServiceReceived: suppServiceInfo=");
            ((StringBuilder)object).append((Object)imsSuppServiceNotification);
            imsPhoneCallTracker.log(((StringBuilder)object).toString());
            object = new SuppServiceNotification();
            ((SuppServiceNotification)object).notificationType = imsSuppServiceNotification.notificationType;
            ((SuppServiceNotification)object).code = imsSuppServiceNotification.code;
            ((SuppServiceNotification)object).index = imsSuppServiceNotification.index;
            ((SuppServiceNotification)object).number = imsSuppServiceNotification.number;
            ((SuppServiceNotification)object).history = imsSuppServiceNotification.history;
            ImsPhoneCallTracker.this.mPhone.notifySuppSvcNotification((SuppServiceNotification)object);
        }

        public void onCallTerminated(ImsCall object, ImsReasonInfo object2) {
            Object object3 = ImsPhoneCallTracker.this;
            Object object4 = new StringBuilder();
            object4.append("onCallTerminated reasonCode=");
            object4.append(object2.getCode());
            ((ImsPhoneCallTracker)object3).log(object4.toString());
            object3 = ImsPhoneCallTracker.this.findConnection(object);
            object4 = object3 != null ? ((ImsPhoneConnection)object3).getState() : Call.State.ACTIVE;
            int n = ImsPhoneCallTracker.this.getDisconnectCauseFromReasonInfo((ImsReasonInfo)object2, (Call.State)((Object)object4));
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            object4 = new StringBuilder();
            object4.append("cause = ");
            object4.append(n);
            object4.append(" conn = ");
            object4.append(object3);
            imsPhoneCallTracker.log(object4.toString());
            if (object3 != null && (object4 = ((Connection)object3).getVideoProvider()) instanceof ImsVideoCallProviderWrapper) {
                object4 = (ImsVideoCallProviderWrapper)object4;
                object4.unregisterForDataUsageUpdate((Handler)ImsPhoneCallTracker.this);
                object4.removeImsVideoProviderCallback((ImsVideoCallProviderWrapper.ImsVideoProviderWrapperCallback)object3);
            }
            if (ImsPhoneCallTracker.this.mOnHoldToneId == System.identityHashCode(object3)) {
                if (object3 != null && ImsPhoneCallTracker.this.mOnHoldToneStarted) {
                    ImsPhoneCallTracker.this.mPhone.stopOnHoldTone((Connection)object3);
                }
                ImsPhoneCallTracker.this.mOnHoldToneStarted = false;
                ImsPhoneCallTracker.this.mOnHoldToneId = -1;
            }
            int n2 = n;
            if (object3 != null) {
                if (((Connection)object3).isPulledCall() && (object2.getCode() == 1015 || object2.getCode() == 336 || object2.getCode() == 332) && ImsPhoneCallTracker.this.mPhone != null && ImsPhoneCallTracker.this.mPhone.getExternalCallTracker() != null) {
                    ImsPhoneCallTracker.this.log("Call pull failed.");
                    ((Connection)object3).onCallPullFailed(ImsPhoneCallTracker.this.mPhone.getExternalCallTracker().getConnectionById(((Connection)object3).getPulledDialogId()));
                    n2 = 0;
                } else {
                    n2 = n;
                    if (((Connection)object3).isIncoming()) {
                        n2 = n;
                        if (((Connection)object3).getConnectTime() == 0L) {
                            n2 = n;
                            if (n != 52) {
                                n2 = n == 2 ? 1 : 16;
                                imsPhoneCallTracker = ImsPhoneCallTracker.this;
                                object4 = new StringBuilder();
                                object4.append("Incoming connection of 0 connect time detected - translated cause = ");
                                object4.append(n2);
                                imsPhoneCallTracker.log(object4.toString());
                            }
                        }
                    }
                }
            }
            n = n2;
            if (n2 == 2) {
                n = n2;
                if (object3 != null) {
                    n = n2;
                    if (((ImsPhoneConnection)object3).getImsCall().isMerged()) {
                        n = 45;
                    }
                }
            }
            object4 = object.getSession().getCallId();
            ImsPhoneCallTracker.this.mMetrics.writeOnImsCallTerminated(ImsPhoneCallTracker.this.mPhone.getPhoneId(), object.getCallSession(), (ImsReasonInfo)object2, (CallQualityMetrics)ImsPhoneCallTracker.this.mCallQualityMetrics.get(object4), ((Connection)object3).getEmergencyNumberInfo(), ImsPhoneCallTracker.this.getNetworkCountryIso());
            object4 = (CallQualityMetrics)ImsPhoneCallTracker.this.mCallQualityMetrics.remove(object4);
            if (object4 != null) {
                ImsPhoneCallTracker.this.mCallQualityMetricsHistory.add(object4);
            }
            ImsPhoneCallTracker.this.pruneCallQualityMetricsHistory();
            ImsPhoneCallTracker.this.mPhone.notifyImsReason((ImsReasonInfo)object2);
            if (object2.getCode() == 1514 && ImsPhoneCallTracker.this.mAutoRetryFailedWifiEmergencyCall) {
                object2 = new Pair(object, object2);
                ImsPhoneCallTracker.this.mPhone.getDefaultPhone().getServiceStateTracker().registerForNetworkAttached(ImsPhoneCallTracker.this, 28, object2);
                object = ImsPhoneCallTracker.this;
                object.sendMessageDelayed(object.obtainMessage(29, object2), 10000L);
                ((ConnectivityManager)ImsPhoneCallTracker.this.mPhone.getContext().getSystemService("connectivity")).setAirplaneMode(false);
                return;
            }
            ImsPhoneCallTracker.this.processCallStateChange(object, Call.State.DISCONNECTED, n);
            if (ImsPhoneCallTracker.this.mForegroundCall.getState() != Call.State.ACTIVE && ImsPhoneCallTracker.this.mRingingCall.getState().isRinging()) {
                ImsPhoneCallTracker.this.mPendingMO = null;
            }
            if (ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.SWAPPING_ACTIVE_AND_HELD) {
                ImsPhoneCallTracker.this.log("onCallTerminated: Call terminated in the midst of Switching Fg and Bg calls.");
                if (object == ImsPhoneCallTracker.this.mCallExpectedToResume) {
                    object = ImsPhoneCallTracker.this;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("onCallTerminated: switching ");
                    ((StringBuilder)object2).append(ImsPhoneCallTracker.this.mForegroundCall);
                    ((StringBuilder)object2).append(" with ");
                    ((StringBuilder)object2).append(ImsPhoneCallTracker.this.mBackgroundCall);
                    object.log(((StringBuilder)object2).toString());
                    ImsPhoneCallTracker.this.mForegroundCall.switchWith(ImsPhoneCallTracker.this.mBackgroundCall);
                }
                object4 = ImsPhoneCallTracker.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("onCallTerminated: foreground call in state ");
                ((StringBuilder)object2).append((Object)ImsPhoneCallTracker.this.mForegroundCall.getState());
                ((StringBuilder)object2).append(" and ringing call in state ");
                object = ImsPhoneCallTracker.this.mRingingCall == null ? "null" : ImsPhoneCallTracker.this.mRingingCall.getState().toString();
                ((StringBuilder)object2).append((String)object);
                ((ImsPhoneCallTracker)object4).log(((StringBuilder)object2).toString());
                ImsPhoneCallTracker.this.sendEmptyMessage(31);
                ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                ImsPhoneCallTracker.this.logHoldSwapState("onCallTerminated swap active and hold case");
            } else if (ImsPhoneCallTracker.this.mHoldSwitchingState != HoldSwapState.PENDING_SINGLE_CALL_UNHOLD && ImsPhoneCallTracker.this.mHoldSwitchingState != HoldSwapState.PENDING_SINGLE_CALL_HOLD) {
                if (ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.HOLDING_TO_ANSWER_INCOMING) {
                    if (object == ImsPhoneCallTracker.this.mCallExpectedToResume) {
                        ImsPhoneCallTracker.this.mForegroundCall.switchWith(ImsPhoneCallTracker.this.mBackgroundCall);
                        ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                        ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                        ImsPhoneCallTracker.this.logHoldSwapState("onCallTerminated hold to answer case");
                        ImsPhoneCallTracker.this.sendEmptyMessage(31);
                    }
                } else if (ImsPhoneCallTracker.this.mHoldSwitchingState == HoldSwapState.HOLDING_TO_DIAL_OUTGOING) {
                    if (ImsPhoneCallTracker.this.mPendingMO != null && ImsPhoneCallTracker.this.mPendingMO.getDisconnectCause() == 0) {
                        if (object != ImsPhoneCallTracker.this.mPendingMO.getImsCall()) {
                            ImsPhoneCallTracker.this.sendEmptyMessage(20);
                            ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                            ImsPhoneCallTracker.this.logHoldSwapState("onCallTerminated hold to dial, dial pendingMo");
                        }
                    } else {
                        ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                        ImsPhoneCallTracker.this.logHoldSwapState("onCallTerminated hold to dial but no pendingMo");
                    }
                }
            } else {
                ImsPhoneCallTracker.this.mCallExpectedToResume = null;
                ImsPhoneCallTracker.this.mHoldSwitchingState = HoldSwapState.INACTIVE;
                ImsPhoneCallTracker.this.logHoldSwapState("onCallTerminated single call case");
            }
            if (ImsPhoneCallTracker.this.mShouldUpdateImsConfigOnDisconnect) {
                if (ImsPhoneCallTracker.this.mImsManager != null) {
                    ImsPhoneCallTracker.this.mImsManager.updateImsServiceConfig(true);
                }
                ImsPhoneCallTracker.this.mShouldUpdateImsConfigOnDisconnect = false;
            }
        }

        public void onCallUpdated(ImsCall imsCall) {
            ImsPhoneCallTracker.this.log("onCallUpdated");
            if (imsCall == null) {
                return;
            }
            ImsPhoneConnection imsPhoneConnection = ImsPhoneCallTracker.this.findConnection(imsCall);
            if (imsPhoneConnection != null) {
                ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onCallUpdated: profile is ");
                stringBuilder.append((Object)imsCall.getCallProfile());
                imsPhoneCallTracker.log(stringBuilder.toString());
                ImsPhoneCallTracker.this.processCallStateChange(imsCall, ((ImsPhoneCall)imsPhoneConnection.getCall()).mState, 0, true);
                ImsPhoneCallTracker.this.mMetrics.writeImsCallState(ImsPhoneCallTracker.this.mPhone.getPhoneId(), imsCall.getCallSession(), ((ImsPhoneCall)imsPhoneConnection.getCall()).mState);
            }
        }

        public void onConferenceParticipantsStateChanged(ImsCall object, List<ConferenceParticipant> list) {
            ImsPhoneCallTracker.this.log("onConferenceParticipantsStateChanged");
            object = ImsPhoneCallTracker.this.findConnection((ImsCall)object);
            if (object != null) {
                this.updateConferenceParticipantsTiming(list);
                ((Connection)object).updateConferenceParticipants(list);
            }
        }

        public void onMultipartyStateChanged(ImsCall object, boolean bl) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onMultipartyStateChanged to ");
            String string = bl ? "Y" : "N";
            stringBuilder.append(string);
            imsPhoneCallTracker.log(stringBuilder.toString());
            object = ImsPhoneCallTracker.this.findConnection((ImsCall)object);
            if (object != null) {
                ((Connection)object).updateMultipartyState(bl);
            }
        }

        public void onRttAudioIndicatorChanged(ImsCall object, ImsStreamMediaProfile imsStreamMediaProfile) {
            if ((object = ImsPhoneCallTracker.this.findConnection((ImsCall)object)) != null) {
                ((ImsPhoneConnection)object).onRttAudioIndicatorChanged(imsStreamMediaProfile);
            }
        }

        public void onRttMessageReceived(ImsCall object, String string) {
            if ((object = ImsPhoneCallTracker.this.findConnection((ImsCall)object)) != null) {
                ((ImsPhoneConnection)object).onRttMessageReceived(string);
            }
        }

        public void onRttModifyRequestReceived(ImsCall object) {
            if ((object = ImsPhoneCallTracker.this.findConnection((ImsCall)object)) != null) {
                ((Connection)object).onRttModifyRequestReceived();
            }
        }

        public void onRttModifyResponseReceived(ImsCall object, int n) {
            if ((object = ImsPhoneCallTracker.this.findConnection((ImsCall)object)) != null) {
                ((Connection)object).onRttModifyResponseReceived(n);
            }
        }
    };
    private final ImsMmTelManager.CapabilityCallback mImsCapabilityCallback = new ImsMmTelManager.CapabilityCallback(){

        public void onCapabilitiesStatusChanged(MmTelFeature.MmTelCapabilities mmTelCapabilities) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCapabilitiesStatusChanged: ");
            stringBuilder.append((Object)mmTelCapabilities);
            imsPhoneCallTracker.log(stringBuilder.toString());
            stringBuilder = SomeArgs.obtain();
            ((SomeArgs)stringBuilder).arg1 = mmTelCapabilities;
            ImsPhoneCallTracker.this.removeMessages(26);
            ImsPhoneCallTracker.this.obtainMessage(26, (Object)stringBuilder).sendToTarget();
        }
    };
    private ImsConfigListener.Stub mImsConfigListener = new ImsConfigListener.Stub(){

        public void onGetFeatureResponse(int n, int n2, int n3, int n4) {
        }

        public void onGetVideoQuality(int n, int n2) {
        }

        public void onSetFeatureResponse(int n, int n2, int n3, int n4) {
            ImsPhoneCallTracker.this.mMetrics.writeImsSetFeatureValue(ImsPhoneCallTracker.this.mPhone.getPhoneId(), n, n2, n3);
        }

        public void onSetVideoQuality(int n) {
        }
    };
    private ImsManager mImsManager;
    private final ImsManager.Connector mImsManagerConnector;
    private Map<Pair<Integer, String>, Integer> mImsReasonCodeMap = new ArrayMap();
    private final ImsMmTelManager.RegistrationCallback mImsRegistrationCallback = new ImsMmTelManager.RegistrationCallback(){

        public void onRegistered(int n) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onImsConnected imsRadioTech=");
            stringBuilder.append(n);
            imsPhoneCallTracker.log(stringBuilder.toString());
            ImsPhoneCallTracker.this.mPhone.setServiceState(0);
            ImsPhoneCallTracker.this.mPhone.setImsRegistered(true);
            ImsPhoneCallTracker.this.mMetrics.writeOnImsConnectionState(ImsPhoneCallTracker.this.mPhone.getPhoneId(), 1, null);
        }

        public void onRegistering(int n) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onImsProgressing imsRadioTech=");
            stringBuilder.append(n);
            imsPhoneCallTracker.log(stringBuilder.toString());
            ImsPhoneCallTracker.this.mPhone.setServiceState(1);
            ImsPhoneCallTracker.this.mPhone.setImsRegistered(false);
            ImsPhoneCallTracker.this.mMetrics.writeOnImsConnectionState(ImsPhoneCallTracker.this.mPhone.getPhoneId(), 2, null);
        }

        public void onSubscriberAssociatedUriChanged(Uri[] arruri) {
            ImsPhoneCallTracker.this.log("registrationAssociatedUriChanged");
            ImsPhoneCallTracker.this.mPhone.setCurrentSubscriberUris(arruri);
        }

        public void onUnregistered(ImsReasonInfo imsReasonInfo) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onImsDisconnected imsReasonInfo=");
            stringBuilder.append((Object)imsReasonInfo);
            imsPhoneCallTracker.log(stringBuilder.toString());
            ImsPhoneCallTracker.this.mPhone.setServiceState(1);
            ImsPhoneCallTracker.this.mPhone.setImsRegistered(false);
            ImsPhoneCallTracker.this.mPhone.processDisconnectReason(imsReasonInfo);
            ImsPhoneCallTracker.this.mMetrics.writeOnImsConnectionState(ImsPhoneCallTracker.this.mPhone.getPhoneId(), 3, imsReasonInfo);
        }
    };
    private ImsCall.Listener mImsUssdListener = new ImsCall.Listener(){

        public void onCallStartFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mImsUssdListener onCallStartFailed reasonCode=");
            stringBuilder.append(imsReasonInfo.getCode());
            imsPhoneCallTracker.log(stringBuilder.toString());
            this.onCallTerminated(imsCall, imsReasonInfo);
        }

        public void onCallStarted(ImsCall imsCall) {
            ImsPhoneCallTracker.this.log("mImsUssdListener onCallStarted");
            if (imsCall == ImsPhoneCallTracker.this.mUssdSession && ImsPhoneCallTracker.this.mPendingUssd != null) {
                AsyncResult.forMessage((Message)ImsPhoneCallTracker.this.mPendingUssd);
                ImsPhoneCallTracker.this.mPendingUssd.sendToTarget();
                ImsPhoneCallTracker.this.mPendingUssd = null;
            }
        }

        public void onCallTerminated(ImsCall imsCall, ImsReasonInfo object) {
            ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mImsUssdListener onCallTerminated reasonCode=");
            stringBuilder.append(object.getCode());
            imsPhoneCallTracker.log(stringBuilder.toString());
            ImsPhoneCallTracker.this.removeMessages(25);
            ImsPhoneCallTracker.this.mHasAttemptedStartOfCallHandover = false;
            ImsPhoneCallTracker.this.unregisterForConnectivityChanges();
            if (imsCall == ImsPhoneCallTracker.this.mUssdSession) {
                ImsPhoneCallTracker.this.mUssdSession = null;
                if (ImsPhoneCallTracker.this.mPendingUssd != null) {
                    object = new CommandException(CommandException.Error.GENERIC_FAILURE);
                    AsyncResult.forMessage((Message)ImsPhoneCallTracker.this.mPendingUssd, null, (Throwable)object);
                    ImsPhoneCallTracker.this.mPendingUssd.sendToTarget();
                    ImsPhoneCallTracker.this.mPendingUssd = null;
                }
            }
            imsCall.close();
        }

        public void onCallUssdMessageReceived(ImsCall object, int n, String string) {
            object = ImsPhoneCallTracker.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mImsUssdListener onCallUssdMessageReceived mode=");
            stringBuilder.append(n);
            ((ImsPhoneCallTracker)object).log(stringBuilder.toString());
            int n2 = -1;
            n = n != 0 ? (n != 1 ? n2 : 1) : 0;
            ImsPhoneCallTracker.this.mPhone.onIncomingUSSD(n, string);
        }
    };
    private boolean mIsDataEnabled = false;
    private boolean mIsInEmergencyCall = false;
    private boolean mIsMonitoringConnectivity = false;
    private boolean mIsViLteDataMetered = false;
    private PhoneInternalInterface.DialArgs mLastDialArgs = null;
    private String mLastDialString = null;
    private TelephonyMetrics mMetrics;
    private MmTelFeature.MmTelCapabilities mMmTelCapabilities = new MmTelFeature.MmTelCapabilities();
    private final MmTelFeatureListener mMmTelFeatureListener = new MmTelFeatureListener();
    private ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback(){

        public void onAvailable(Network network) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Network available: ");
            stringBuilder.append((Object)network);
            Rlog.i((String)ImsPhoneCallTracker.LOG_TAG, (String)stringBuilder.toString());
            ImsPhoneCallTracker.this.scheduleHandoverCheck();
        }
    };
    private boolean mNotifyHandoverVideoFromLTEToWifi = false;
    private boolean mNotifyHandoverVideoFromWifiToLTE = false;
    private boolean mNotifyVtHandoverToWifiFail = false;
    @UnsupportedAppUsage
    private int mOnHoldToneId = -1;
    @UnsupportedAppUsage
    private boolean mOnHoldToneStarted = false;
    private int mPendingCallVideoState;
    private Bundle mPendingIntentExtras;
    @UnsupportedAppUsage
    private ImsPhoneConnection mPendingMO;
    @UnsupportedAppUsage
    private Message mPendingUssd = null;
    @UnsupportedAppUsage
    ImsPhone mPhone;
    private final Map<String, CacheEntry> mPhoneNumAndConnTime = new ConcurrentHashMap<String, CacheEntry>();
    private PhoneNumberUtilsProxy mPhoneNumberUtilsProxy = _$$Lambda$ImsPhoneCallTracker$QlPVd_3u4_verjHUDnkn6zaSe54.INSTANCE;
    private List<PhoneStateListener> mPhoneStateListeners = new ArrayList<PhoneStateListener>();
    private BroadcastReceiver mReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent object2) {
            block1 : {
                block0 : {
                    if (!object2.getAction().equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) break block0;
                    int n = object2.getIntExtra("subscription", -1);
                    if (n != ImsPhoneCallTracker.this.mPhone.getSubId()) break block1;
                    ImsPhoneCallTracker.this.cacheCarrierConfiguration(n);
                    object = ImsPhoneCallTracker.this;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("onReceive : Updating mAllowEmergencyVideoCalls = ");
                    ((StringBuilder)object2).append(ImsPhoneCallTracker.this.mAllowEmergencyVideoCalls);
                    ((ImsPhoneCallTracker)object).log(((StringBuilder)object2).toString());
                    break block1;
                }
                if (!"android.telecom.action.CHANGE_DEFAULT_DIALER".equals(object2.getAction())) break block1;
                ImsPhoneCallTracker.this.mDefaultDialerUid.set(ImsPhoneCallTracker.this.getPackageUid((Context)object, object2.getStringExtra("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME")));
            }
        }
    };
    @UnsupportedAppUsage
    public ImsPhoneCall mRingingCall = new ImsPhoneCall(this, "RG");
    private SharedPreferenceProxy mSharedPreferenceProxy = _$$Lambda$ImsPhoneCallTracker$Zw03itjXT6_LrhiYuD_9nKFg2Wg.INSTANCE;
    private boolean mShouldUpdateImsConfigOnDisconnect = false;
    private Call.SrvccState mSrvccState = Call.SrvccState.NONE;
    private PhoneConstants.State mState = PhoneConstants.State.IDLE;
    private boolean mSupportDowngradeVtToAudio = false;
    private boolean mSupportPauseVideo = false;
    @UnsupportedAppUsage
    private boolean mSwitchingFgAndBgCalls = false;
    @UnsupportedAppUsage
    private Object mSyncHold = new Object();
    private boolean mTreatDowngradedVideoCallsAsVideoCalls = false;
    private final Queue<CacheEntry> mUnknownPeerConnTime = new LinkedBlockingQueue<CacheEntry>();
    private ImsCall mUssdSession = null;
    private ImsUtInterface mUtInterface;
    private RegistrantList mVoiceCallEndedRegistrants = new RegistrantList();
    private RegistrantList mVoiceCallStartedRegistrants = new RegistrantList();
    private final HashMap<Integer, Long> mVtDataUsageMap = new HashMap();
    private volatile NetworkStats mVtDataUsageSnapshot = null;
    private volatile NetworkStats mVtDataUsageUidSnapshot = null;
    private int pendingCallClirMode;
    private boolean pendingCallInEcm = false;

    public ImsPhoneCallTracker(ImsPhone imsPhone) {
        this(imsPhone, imsPhone.getContext().getMainExecutor());
    }

    @VisibleForTesting
    public ImsPhoneCallTracker(ImsPhone imsPhone, Executor executor) {
        this.mPhone = imsPhone;
        this.mMetrics = TelephonyMetrics.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        intentFilter.addAction("android.telecom.action.CHANGE_DEFAULT_DIALER");
        this.mPhone.getContext().registerReceiver(this.mReceiver, intentFilter);
        this.cacheCarrierConfiguration(this.mPhone.getSubId());
        this.mPhone.getDefaultPhone().getDataEnabledSettings().registerForDataEnabledChanged(this, 23, null);
        intentFilter = (TelecomManager)this.mPhone.getContext().getSystemService("telecom");
        this.mDefaultDialerUid.set(this.getPackageUid(this.mPhone.getContext(), intentFilter.getDefaultDialerPackage()));
        long l = SystemClock.elapsedRealtime();
        this.mVtDataUsageSnapshot = new NetworkStats(l, 1);
        this.mVtDataUsageUidSnapshot = new NetworkStats(l, 1);
        this.mImsManagerConnector = new ImsManager.Connector(imsPhone.getContext(), imsPhone.getPhoneId(), new ImsManager.Connector.Listener(){

            public void connectionReady(ImsManager imsManager) throws ImsException {
                ImsPhoneCallTracker.this.mImsManager = imsManager;
                ImsPhoneCallTracker.this.startListeningForCalls();
            }

            public void connectionUnavailable() {
                ImsPhoneCallTracker.this.stopListeningForCalls();
            }
        }, executor);
        this.mImsManagerConnector.connect();
    }

    @UnsupportedAppUsage
    private void addConnection(ImsPhoneConnection imsPhoneConnection) {
        synchronized (this) {
            this.mConnections.add(imsPhoneConnection);
            if (imsPhoneConnection.isEmergency()) {
                this.mIsInEmergencyCall = true;
                this.mPhone.sendEmergencyCallStateChange(true);
            }
            return;
        }
    }

    private void answerWaitingCall() throws ImsException {
        ImsCall imsCall = this.mRingingCall.getImsCall();
        if (imsCall != null) {
            imsCall.accept(ImsCallProfile.getCallTypeFromVideoState((int)this.mPendingCallVideoState));
            this.mMetrics.writeOnImsCommand(this.mPhone.getPhoneId(), imsCall.getSession(), 2);
        }
    }

    private void cacheCarrierConfiguration(int n) {
        Object object = (CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config");
        if (object != null && SubscriptionController.getInstance().isActiveSubId(n)) {
            if ((object = object.getConfigForSubId(n)) == null) {
                this.loge("cacheCarrierConfiguration: Empty carrier config.");
                this.mCarrierConfigLoaded = false;
                return;
            }
            this.mCarrierConfigLoaded = true;
            this.updateCarrierConfigCache((PersistableBundle)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("cacheCarrierConfiguration: No carrier config service found or not active subId = ");
        ((StringBuilder)object).append(n);
        this.loge(((StringBuilder)object).toString());
        this.mCarrierConfigLoaded = false;
    }

    private void cacheConnectionTimeWithPhoneNumber(ImsPhoneConnection imsPhoneConnection) {
        int n = imsPhoneConnection.isIncoming() ? 0 : 1;
        CacheEntry cacheEntry = new CacheEntry(SystemClock.elapsedRealtime(), imsPhoneConnection.getConnectTime(), imsPhoneConnection.getConnectTimeReal(), n);
        this.maintainConnectTimeCache();
        if (1 == imsPhoneConnection.getNumberPresentation()) {
            String string = this.getFormattedPhoneNumber(imsPhoneConnection.getAddress());
            if (this.mPhoneNumAndConnTime.containsKey(string) && imsPhoneConnection.getConnectTime() <= this.mPhoneNumAndConnTime.get(string).mConnectTime) {
                return;
            }
            this.mPhoneNumAndConnTime.put(string, cacheEntry);
        } else {
            this.mUnknownPeerConnTime.add(cacheEntry);
        }
    }

    private String cleanseInstantLetteringMessage(String string) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            return string;
        }
        Object object = (CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config");
        if (object == null) {
            return string;
        }
        Object object2 = object.getConfigForSubId(this.mPhone.getSubId());
        if (object2 == null) {
            return string;
        }
        String string2 = object2.getString("carrier_instant_lettering_invalid_chars_string");
        object = string;
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            object = string.replaceAll(string2, "");
        }
        object2 = object2.getString("carrier_instant_lettering_escaped_chars_string");
        string = object;
        if (!TextUtils.isEmpty((CharSequence)object2)) {
            string = this.escapeChars((String)object2, (String)object);
        }
        return string;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void dialInternal(ImsPhoneConnection imsPhoneConnection, int n, int n2, Bundle bundle) {
        block12 : {
            void var4_9;
            StringBuilder stringBuilder;
            block13 : {
                if (imsPhoneConnection == null) {
                    return;
                }
                if (imsPhoneConnection.getAddress() == null || imsPhoneConnection.getAddress().length() == 0 || imsPhoneConnection.getAddress().indexOf(78) >= 0) break block12;
                this.setMute(false);
                boolean bl = this.mPhoneNumberUtilsProxy.isEmergencyNumber(imsPhoneConnection.getAddress());
                int n3 = bl ? 2 : 1;
                int n4 = ImsCallProfile.getCallTypeFromVideoState((int)n2);
                imsPhoneConnection.setVideoState(n2);
                String string = imsPhoneConnection.getAddress();
                stringBuilder = this.mImsManager.createCallProfile(n3, n4);
                try {
                    stringBuilder.setCallExtraInt("oir", n);
                    if (bl) {
                        this.setEmergencyCallInfo((ImsCallProfile)stringBuilder, imsPhoneConnection);
                    }
                    if (bundle != null) {
                        if (bundle.containsKey("android.telecom.extra.CALL_SUBJECT")) {
                            bundle.putString("DisplayText", this.cleanseInstantLetteringMessage(bundle.getString("android.telecom.extra.CALL_SUBJECT")));
                        }
                        if (imsPhoneConnection.hasRttTextStream()) {
                            stringBuilder.mMediaProfile.mRttMode = 1;
                        }
                        if (bundle.containsKey("CallPull")) {
                            ((ImsCallProfile)stringBuilder).mCallExtras.putBoolean("CallPull", bundle.getBoolean("CallPull"));
                            n = bundle.getInt("android.telephony.ImsExternalCallTracker.extra.EXTERNAL_CALL_ID");
                            imsPhoneConnection.setIsPulledCall(true);
                            imsPhoneConnection.setPulledDialogId(n);
                        }
                        ((ImsCallProfile)stringBuilder).mCallExtras.putBundle("OemCallExtras", bundle);
                    }
                    ImsManager imsManager = this.mImsManager;
                    bundle = this.mImsCallListener;
                    bundle = imsManager.makeCall((ImsCallProfile)stringBuilder, new String[]{string}, (ImsCall.Listener)bundle);
                    imsPhoneConnection.setImsCall((ImsCall)bundle);
                    this.mMetrics.writeOnImsCallStart(this.mPhone.getPhoneId(), bundle.getSession());
                    this.setVideoCallProvider(imsPhoneConnection, (ImsCall)bundle);
                    imsPhoneConnection.setAllowAddCallDuringVideoCall(this.mAllowAddCallDuringVideoCall);
                    return;
                }
                catch (RemoteException remoteException) {
                    return;
                }
                catch (ImsException imsException) {}
                break block13;
                catch (RemoteException remoteException) {
                    return;
                }
                catch (ImsException imsException) {
                    // empty catch block
                }
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("dialInternal : ");
            stringBuilder.append(var4_9);
            this.loge(stringBuilder.toString());
            imsPhoneConnection.setDisconnectCause(36);
            this.sendEmptyMessageDelayed(18, 500L);
            this.retryGetImsService();
            return;
        }
        imsPhoneConnection.setDisconnectCause(7);
        this.sendEmptyMessageDelayed(18, 500L);
    }

    @UnsupportedAppUsage
    private void dialPendingMO() {
        boolean bl = this.isPhoneInEcbMode();
        boolean bl2 = this.mPendingMO.isEmergency();
        if (!(!bl || bl && bl2)) {
            this.sendEmptyMessage(21);
        } else {
            this.sendEmptyMessage(20);
        }
    }

    private void downgradeVideoCall(int n, ImsPhoneConnection imsPhoneConnection) {
        Object object = imsPhoneConnection.getImsCall();
        if (object != null) {
            if (imsPhoneConnection.hasCapabilities(3) && !this.mSupportPauseVideo) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("downgradeVideoCall :: callId=");
                stringBuilder.append(imsPhoneConnection.getTelecomCallId());
                stringBuilder.append(" Downgrade to audio");
                this.log(stringBuilder.toString());
                this.modifyVideoCall((ImsCall)object, 0);
            } else if (this.mSupportPauseVideo && n != 1407) {
                object = new StringBuilder();
                ((StringBuilder)object).append("downgradeVideoCall :: callId=");
                ((StringBuilder)object).append(imsPhoneConnection.getTelecomCallId());
                ((StringBuilder)object).append(" Pause audio");
                this.log(((StringBuilder)object).toString());
                this.mShouldUpdateImsConfigOnDisconnect = true;
                imsPhoneConnection.pauseVideo(2);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("downgradeVideoCall :: callId=");
                stringBuilder.append(imsPhoneConnection.getTelecomCallId());
                stringBuilder.append(" Disconnect call.");
                this.log(stringBuilder.toString());
                object.terminate(501, n);
            }
        }
    }

    private void dumpState() {
        int n;
        List<Connection> list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Phone State:");
        ((StringBuilder)((Object)list)).append((Object)this.mState);
        this.log(((StringBuilder)((Object)list)).toString());
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Ringing call: ");
        ((StringBuilder)((Object)list)).append(this.mRingingCall.toString());
        this.log(((StringBuilder)((Object)list)).toString());
        list = this.mRingingCall.getConnections();
        int n2 = list.size();
        for (n = 0; n < n2; ++n) {
            this.log(list.get(n).toString());
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Foreground call: ");
        ((StringBuilder)((Object)list)).append(this.mForegroundCall.toString());
        this.log(((StringBuilder)((Object)list)).toString());
        list = this.mForegroundCall.getConnections();
        n2 = list.size();
        for (n = 0; n < n2; ++n) {
            this.log(((Object)list.get(n)).toString());
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Background call: ");
        ((StringBuilder)((Object)list)).append(this.mBackgroundCall.toString());
        this.log(((StringBuilder)((Object)list)).toString());
        list = this.mBackgroundCall.getConnections();
        n2 = list.size();
        for (n = 0; n < n2; ++n) {
            this.log(((Object)list.get(n)).toString());
        }
    }

    private String escapeChars(String string, String arrc) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : arrc.toCharArray()) {
            if (string.contains(Character.toString(c))) {
                stringBuilder.append("\\");
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ImsPhoneConnection findConnection(ImsCall imsCall) {
        synchronized (this) {
            ImsCall imsCall2;
            ImsPhoneConnection imsPhoneConnection;
            Iterator<ImsPhoneConnection> iterator = this.mConnections.iterator();
            do {
                if (iterator.hasNext()) continue;
                return null;
            } while ((imsCall2 = (imsPhoneConnection = iterator.next()).getImsCall()) != imsCall);
            return imsPhoneConnection;
        }
    }

    private CacheEntry findConnectionTimeUsePhoneNumber(ConferenceParticipant object) {
        this.maintainConnectTimeCache();
        if (1 == object.getParticipantPresentation()) {
            if (object.getHandle() != null && object.getHandle().getSchemeSpecificPart() != null) {
                if (TextUtils.isEmpty((CharSequence)(object = ConferenceParticipant.getParticipantAddress((Uri)object.getHandle(), (String)this.getCountryIso()).getSchemeSpecificPart()))) {
                    return null;
                }
                object = this.getFormattedPhoneNumber((String)object);
                return this.mPhoneNumAndConnTime.get(object);
            }
            return null;
        }
        return this.mUnknownPeerConnTime.poll();
    }

    private String getCountryIso() {
        int n = this.mPhone.getSubId();
        Object object = SubscriptionManager.from((Context)this.mPhone.getContext()).getActiveSubscriptionInfo(n);
        object = object == null ? null : object.getCountryIso();
        return object;
    }

    private String getFormattedPhoneNumber(String string) {
        String string2 = this.getCountryIso();
        if (string2 == null) {
            return string;
        }
        if ((string2 = PhoneNumberUtils.formatNumberToE164((String)string, (String)string2)) != null) {
            string = string2;
        }
        return string;
    }

    private ImsException getImsManagerIsNullException() {
        return new ImsException("no ims manager", 102);
    }

    private String getNetworkCountryIso() {
        String string = "";
        Handler handler = this.mPhone;
        String string2 = string;
        if (handler != null) {
            handler = handler.getServiceStateTracker();
            string2 = string;
            if (handler != null) {
                handler = handler.getLocaleTracker();
                string2 = string;
                if (handler != null) {
                    string2 = handler.getCurrentCountry();
                }
            }
        }
        return string2;
    }

    private int getPackageUid(Context context, String string) {
        if (string == null) {
            return -1;
        }
        int n = -1;
        try {
            int n2;
            n = n2 = context.getPackageManager().getPackageUid(string, 0);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot find package uid. pkg = ");
            stringBuilder.append(string);
            this.loge(stringBuilder.toString());
        }
        return n;
    }

    private void handleDataEnabledChange(boolean bl, int n) {
        if (!bl) {
            for (ImsPhoneConnection imsPhoneConnection : this.mConnections) {
                Object object = imsPhoneConnection.getImsCall();
                if (object == null || !object.isVideoCall() || object.isWifiCall()) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("handleDataEnabledChange - downgrading ");
                ((StringBuilder)object).append(imsPhoneConnection);
                this.log(((StringBuilder)object).toString());
                this.downgradeVideoCall(n, imsPhoneConnection);
            }
        } else if (this.mSupportPauseVideo) {
            for (ImsPhoneConnection imsPhoneConnection : this.mConnections) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handleDataEnabledChange - resuming ");
                stringBuilder.append(imsPhoneConnection);
                this.log(stringBuilder.toString());
                if (!VideoProfile.isPaused((int)imsPhoneConnection.getVideoState()) || !imsPhoneConnection.wasVideoPausedFromSource(2)) continue;
                imsPhoneConnection.resumeVideo(2);
            }
            this.mShouldUpdateImsConfigOnDisconnect = false;
        }
    }

    @UnsupportedAppUsage
    private void handleEcmTimer(int n) {
        block0 : {
            this.mPhone.handleTimerInEmergencyCallbackMode(n);
            if (n == 0 || n == 1) break block0;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleEcmTimer, unsupported action ");
            stringBuilder.append(n);
            this.log(stringBuilder.toString());
        }
    }

    private void handleFeatureCapabilityChanged(ImsFeature.Capabilities object) {
        boolean bl = this.isVideoCallEnabled();
        StringBuilder stringBuilder = new StringBuilder(120);
        stringBuilder.append("handleFeatureCapabilityChanged: ");
        stringBuilder.append(object);
        this.mMmTelCapabilities = new MmTelFeature.MmTelCapabilities((ImsFeature.Capabilities)object);
        boolean bl2 = this.isVideoCallEnabled();
        bl = bl != bl2;
        stringBuilder.append(" isVideoEnabledStateChanged=");
        stringBuilder.append(bl);
        if (bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("handleFeatureCapabilityChanged - notifyForVideoCapabilityChanged=");
            ((StringBuilder)object).append(bl2);
            this.log(((StringBuilder)object).toString());
            this.mPhone.notifyForVideoCapabilityChanged(bl2);
        }
        this.log(stringBuilder.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("handleFeatureCapabilityChanged: isVolteEnabled=");
        ((StringBuilder)object).append(this.isVolteEnabled());
        ((StringBuilder)object).append(", isVideoCallEnabled=");
        ((StringBuilder)object).append(this.isVideoCallEnabled());
        ((StringBuilder)object).append(", isVowifiEnabled=");
        ((StringBuilder)object).append(this.isVowifiEnabled());
        ((StringBuilder)object).append(", isUtEnabled=");
        ((StringBuilder)object).append(this.isUtEnabled());
        this.log(((StringBuilder)object).toString());
        this.mPhone.onFeatureCapabilityChanged();
        this.mMetrics.writeOnImsCapabilities(this.mPhone.getPhoneId(), this.getImsRegistrationTech(), this.mMmTelCapabilities);
    }

    private void handleRadioNotAvailable() {
        this.pollCallsWhenSafe();
    }

    private void holdActiveCallForPendingMo() throws CallStateException {
        if (this.mHoldSwitchingState != HoldSwapState.PENDING_SINGLE_CALL_HOLD && this.mHoldSwitchingState != HoldSwapState.SWAPPING_ACTIVE_AND_HELD) {
            ImsCall imsCall = this.mForegroundCall.getImsCall();
            this.mHoldSwitchingState = HoldSwapState.HOLDING_TO_DIAL_OUTGOING;
            this.logHoldSwapState("holdActiveCallForPendingMo");
            this.mForegroundCall.switchWith(this.mBackgroundCall);
            try {
                imsCall.hold();
                this.mMetrics.writeOnImsCommand(this.mPhone.getPhoneId(), imsCall.getSession(), 5);
                return;
            }
            catch (ImsException imsException) {
                this.mForegroundCall.switchWith(this.mBackgroundCall);
                throw new CallStateException(imsException.getMessage());
            }
        }
        this.logi("Ignoring hold request while already holding or swapping");
    }

    private void internalClearDisconnected() {
        this.mRingingCall.clearDisconnected();
        this.mForegroundCall.clearDisconnected();
        this.mBackgroundCall.clearDisconnected();
        this.mHandoverCall.clearDisconnected();
    }

    private boolean isPhoneInEcbMode() {
        ImsPhone imsPhone = this.mPhone;
        boolean bl = imsPhone != null && imsPhone.isInEcm();
        return bl;
    }

    private boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.mPhone.getContext().getSystemService("connectivity");
        boolean bl = false;
        if (connectivityManager != null && (connectivityManager = connectivityManager.getActiveNetworkInfo()) != null && connectivityManager.isConnected()) {
            if (connectivityManager.getType() == 1) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    static /* synthetic */ boolean lambda$maintainConnectTimeCache$2(long l, Map.Entry entry) {
        boolean bl = ((CacheEntry)entry.getValue()).mCachedTime < l;
        return bl;
    }

    static /* synthetic */ SharedPreferences lambda$new$0(Context context) {
        return PreferenceManager.getDefaultSharedPreferences((Context)context);
    }

    static /* synthetic */ boolean lambda$new$1(String string) {
        return PhoneNumberUtils.isEmergencyNumber((String)string);
    }

    private void maintainConnectTimeCache() {
        long l = SystemClock.elapsedRealtime() - 60000L;
        this.mPhoneNumAndConnTime.entrySet().removeIf(new _$$Lambda$ImsPhoneCallTracker$R2Z9jNp4rrTM4H39vy492Fbmqyc(l));
        while (!this.mUnknownPeerConnTime.isEmpty() && this.mUnknownPeerConnTime.peek().mCachedTime < l) {
            this.mUnknownPeerConnTime.poll();
        }
    }

    private void maybeNotifyDataDisabled(boolean bl, int n) {
        if (!bl) {
            for (ImsPhoneConnection imsPhoneConnection : this.mConnections) {
                ImsCall imsCall = imsPhoneConnection.getImsCall();
                if (imsCall == null || !imsCall.isVideoCall() || imsCall.isWifiCall() || !imsPhoneConnection.hasCapabilities(3)) continue;
                if (n == 1406) {
                    imsPhoneConnection.onConnectionEvent("android.telephony.event.EVENT_DOWNGRADE_DATA_DISABLED", null);
                    continue;
                }
                if (n != 1405) continue;
                imsPhoneConnection.onConnectionEvent("android.telephony.event.EVENT_DOWNGRADE_DATA_LIMIT_REACHED", null);
            }
        }
    }

    private void maybeSetVideoCallProvider(ImsPhoneConnection imsPhoneConnection, ImsCall object) {
        if (imsPhoneConnection.getVideoProvider() == null && object.getCallSession().getVideoCallProvider() != null) {
            try {
                this.setVideoCallProvider(imsPhoneConnection, (ImsCall)object);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("maybeSetVideoCallProvider: exception ");
                ((StringBuilder)object).append((Object)remoteException);
                this.loge(((StringBuilder)object).toString());
            }
            return;
        }
    }

    private void modifyVideoCall(ImsCall object, int n) {
        if ((object = this.findConnection((ImsCall)object)) != null) {
            int n2 = ((Connection)object).getVideoState();
            if (((Connection)object).getVideoProvider() != null) {
                ((Connection)object).getVideoProvider().onSendSessionModifyRequest(new VideoProfile(n2), new VideoProfile(n));
            }
        }
    }

    private void notifyPhoneStateChanged(PhoneConstants.State state, PhoneConstants.State state2) {
        Iterator<PhoneStateListener> iterator = this.mPhoneStateListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onPhoneStateChanged(state, state2);
        }
    }

    private void onDataEnabledChanged(boolean bl, int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("onDataEnabledChanged: enabled=");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(", reason=");
        ((StringBuilder)object).append(n);
        this.log(((StringBuilder)object).toString());
        this.mIsDataEnabled = bl;
        if (!this.mIsViLteDataMetered) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignore data ");
            object = bl ? "enabled" : "disabled";
            stringBuilder.append((String)object);
            stringBuilder.append(" - carrier policy indicates that data is not metered for ViLTE calls.");
            this.log(stringBuilder.toString());
            return;
        }
        Iterator<ImsPhoneConnection> iterator = this.mConnections.iterator();
        do {
            boolean bl2 = iterator.hasNext();
            boolean bl3 = true;
            if (!bl2) break;
            object = iterator.next();
            ImsCall imsCall = ((ImsPhoneConnection)object).getImsCall();
            bl2 = bl3;
            if (!bl) {
                bl2 = imsCall != null && imsCall.isWifiCall() ? bl3 : false;
            }
            ((ImsPhoneConnection)object).setLocalVideoCapable(bl2);
        } while (true);
        int n2 = n == 3 ? 1405 : (n == 2 ? 1406 : 1406);
        this.maybeNotifyDataDisabled(bl, n2);
        this.handleDataEnabledChange(bl, n2);
        if (!this.mShouldUpdateImsConfigOnDisconnect && n != 0 && this.mCarrierConfigLoaded && (object = this.mImsManager) != null) {
            object.updateImsServiceConfig(true);
        }
    }

    private void processCallStateChange(ImsCall imsCall, Call.State state, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("processCallStateChange ");
        stringBuilder.append((Object)imsCall);
        stringBuilder.append(" state=");
        stringBuilder.append((Object)state);
        stringBuilder.append(" cause=");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        this.processCallStateChange(imsCall, state, n, false);
    }

    private void processCallStateChange(ImsCall imsCall, Call.State state, int n, boolean bl) {
        boolean bl2;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("processCallStateChange state=");
        ((StringBuilder)object).append((Object)state);
        ((StringBuilder)object).append(" cause=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" ignoreState=");
        ((StringBuilder)object).append(bl);
        this.log(((StringBuilder)object).toString());
        if (imsCall == null) {
            return;
        }
        object = this.findConnection(imsCall);
        if (object == null) {
            return;
        }
        ((ImsPhoneConnection)object).updateMediaCapabilities(imsCall);
        if (bl) {
            ((ImsPhoneConnection)object).updateAddressDisplay(imsCall);
            ((ImsPhoneConnection)object).updateExtras(imsCall);
            this.maybeSetVideoCallProvider((ImsPhoneConnection)object, imsCall);
            return;
        }
        bl = bl2 = ((ImsPhoneConnection)object).update(imsCall, state);
        if (state == Call.State.DISCONNECTED) {
            bl = ((ImsPhoneConnection)object).onDisconnect(n) || bl2;
            ((ImsPhoneCall)((ImsPhoneConnection)object).getCall()).detach((ImsPhoneConnection)object);
            this.removeConnection((ImsPhoneConnection)object);
        }
        if (bl) {
            if (((ImsPhoneConnection)object).getCall() == this.mHandoverCall) {
                return;
            }
            this.updatePhoneState();
            this.mPhone.notifyPreciseCallStateChanged();
        }
    }

    private void pruneCallQualityMetricsHistory() {
        if (this.mCallQualityMetricsHistory.size() > 10) {
            this.mCallQualityMetricsHistory.poll();
        }
    }

    private void registerForConnectivityChanges() {
        if (!this.mIsMonitoringConnectivity && this.mNotifyVtHandoverToWifiFail) {
            ConnectivityManager connectivityManager = (ConnectivityManager)this.mPhone.getContext().getSystemService("connectivity");
            if (connectivityManager != null) {
                Rlog.i((String)LOG_TAG, (String)"registerForConnectivityChanges");
                NetworkCapabilities networkCapabilities = new NetworkCapabilities();
                networkCapabilities.addTransportType(1);
                NetworkRequest.Builder builder = new NetworkRequest.Builder();
                builder.setCapabilities(networkCapabilities);
                connectivityManager.registerNetworkCallback(builder.build(), this.mNetworkCallback);
                this.mIsMonitoringConnectivity = true;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void removeConnection(ImsPhoneConnection iterator) {
        synchronized (this) {
            boolean bl;
            block3 : {
                ImsPhoneConnection imsPhoneConnection;
                this.mConnections.remove(iterator);
                if (!this.mIsInEmergencyCall) return;
                boolean bl2 = false;
                iterator = this.mConnections.iterator();
                do {
                    bl = bl2;
                    if (!iterator.hasNext()) break block3;
                } while ((imsPhoneConnection = iterator.next()) == null || !imsPhoneConnection.isEmergency());
                return;
            }
            if (bl) return;
            this.mIsInEmergencyCall = false;
            this.mPhone.sendEmergencyCallStateChange(false);
            return;
        }
    }

    private void resetImsCapabilities() {
        this.log("Resetting Capabilities...");
        boolean bl = this.isVideoCallEnabled();
        this.mMmTelCapabilities = new MmTelFeature.MmTelCapabilities();
        boolean bl2 = this.isVideoCallEnabled();
        if (bl != bl2) {
            this.mPhone.notifyForVideoCapabilityChanged(bl2);
        }
    }

    private void resetState() {
        this.mIsInEmergencyCall = false;
    }

    private void resumeForegroundCall() throws ImsException {
        ImsCall imsCall = this.mForegroundCall.getImsCall();
        if (imsCall != null) {
            imsCall.resume();
            this.mMetrics.writeOnImsCommand(this.mPhone.getPhoneId(), imsCall.getSession(), 6);
        }
    }

    private void retryGetImsService() {
        if (this.mImsManager.isServiceAvailable()) {
            return;
        }
        this.mImsManagerConnector.connect();
    }

    private void scheduleHandoverCheck() {
        ImsCall imsCall = this.mForegroundCall.getImsCall();
        ImsPhoneConnection imsPhoneConnection = this.mForegroundCall.getFirstConnection();
        if (this.mNotifyVtHandoverToWifiFail && imsCall != null && imsCall.isVideoCall() && imsPhoneConnection != null && imsPhoneConnection.getDisconnectCause() == 0) {
            if (!this.hasMessages(25)) {
                Rlog.i((String)LOG_TAG, (String)"scheduleHandoverCheck: schedule");
                this.sendMessageDelayed(this.obtainMessage(25, (Object)imsCall), 60000L);
            }
            return;
        }
    }

    private void sendImsServiceStateIntent(String string) {
        string = new Intent(string);
        string.putExtra("android:phone_id", this.mPhone.getPhoneId());
        ImsPhone imsPhone = this.mPhone;
        if (imsPhone != null && imsPhone.getContext() != null) {
            this.mPhone.getContext().sendBroadcast((Intent)string);
        }
    }

    private void setEmergencyCallInfo(ImsCallProfile imsCallProfile, Connection connection) {
        EmergencyNumber emergencyNumber = connection.getEmergencyNumberInfo();
        if (emergencyNumber != null) {
            imsCallProfile.setEmergencyCallInfo(emergencyNumber, connection.hasKnownUserIntentEmergency());
        }
    }

    private void setVideoCallProvider(ImsPhoneConnection imsPhoneConnection, ImsCall imsCall) throws RemoteException {
        IImsVideoCallProvider iImsVideoCallProvider = imsCall.getCallSession().getVideoCallProvider();
        if (iImsVideoCallProvider != null) {
            boolean bl = this.mPhone.getContext().getResources().getBoolean(17891562);
            iImsVideoCallProvider = new ImsVideoCallProviderWrapper(iImsVideoCallProvider);
            if (bl) {
                iImsVideoCallProvider.setUseVideoPauseWorkaround(bl);
            }
            imsPhoneConnection.setVideoProvider((Connection.VideoProvider)iImsVideoCallProvider);
            iImsVideoCallProvider.registerForDataUsageUpdate((Handler)this, 22, (Object)imsCall);
            iImsVideoCallProvider.addImsVideoProviderCallback((ImsVideoCallProviderWrapper.ImsVideoProviderWrapperCallback)imsPhoneConnection);
        }
    }

    private boolean shouldDisconnectActiveCallOnAnswer(ImsCall object, ImsCall imsCall) {
        boolean bl = false;
        if (object != null && imsCall != null) {
            if (!this.mDropVideoCallWhenAnsweringAudioCall) {
                return false;
            }
            boolean bl2 = object.isVideoCall() || this.mTreatDowngradedVideoCallsAsVideoCalls && object.wasVideoCall();
            boolean bl3 = object.isWifiCall();
            boolean bl4 = this.mImsManager.isWfcEnabledByPlatform() && this.mImsManager.isWfcEnabledByUser();
            boolean bl5 = imsCall.isVideoCall() ^ true;
            object = new StringBuilder();
            ((StringBuilder)object).append("shouldDisconnectActiveCallOnAnswer : isActiveCallVideo=");
            ((StringBuilder)object).append(bl2);
            ((StringBuilder)object).append(" isActiveCallOnWifi=");
            ((StringBuilder)object).append(bl3);
            ((StringBuilder)object).append(" isIncomingCallAudio=");
            ((StringBuilder)object).append(bl5);
            ((StringBuilder)object).append(" isVowifiEnabled=");
            ((StringBuilder)object).append(bl4);
            this.log(((StringBuilder)object).toString());
            boolean bl6 = bl;
            if (bl2) {
                bl6 = bl;
                if (bl3) {
                    bl6 = bl;
                    if (bl5) {
                        bl6 = bl;
                        if (!bl4) {
                            bl6 = true;
                        }
                    }
                }
            }
            return bl6;
        }
        return false;
    }

    private boolean shouldNumberBePlacedOnIms(boolean bl, String string) {
        block3 : {
            block4 : {
                block5 : {
                    try {
                        if (this.mImsManager == null) break block3;
                        int n = this.mImsManager.shouldProcessCall(bl, new String[]{string});
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("shouldProcessCall: number: ");
                        stringBuilder.append(Rlog.pii((String)LOG_TAG, (Object)string));
                        stringBuilder.append(", result: ");
                        stringBuilder.append(n);
                        Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
                        if (n == 0) break block4;
                        if (n == 1) break block5;
                    }
                    catch (ImsException imsException) {
                        Rlog.w((String)LOG_TAG, (String)"ImsService unavailable, shouldProcessCall returning false.");
                        return false;
                    }
                    Rlog.w((String)LOG_TAG, (String)"shouldProcessCall returned unknown result.");
                    return false;
                }
                Rlog.i((String)LOG_TAG, (String)"shouldProcessCall: place over CSFB instead.");
                return false;
            }
            return true;
        }
        Rlog.w((String)LOG_TAG, (String)"ImsManager unavailable, shouldProcessCall returning false.");
        return false;
    }

    private void startListeningForCalls() throws ImsException {
        this.log("startListeningForCalls");
        this.mImsManager.open((MmTelFeature.Listener)this.mMmTelFeatureListener);
        this.mImsManager.addRegistrationCallback(this.mImsRegistrationCallback);
        this.mImsManager.addCapabilitiesCallback(this.mImsCapabilityCallback);
        this.mImsManager.setConfigListener((ImsConfigListener)this.mImsConfigListener);
        this.mImsManager.getConfigInterface().addConfigCallback(this.mConfigCallback);
        this.getEcbmInterface().setEcbmStateListener(this.mPhone.getImsEcbmStateListener());
        if (this.mPhone.isInEcm()) {
            this.mPhone.exitEmergencyCallbackMode();
        }
        int n = Settings.Secure.getInt((ContentResolver)this.mPhone.getContext().getContentResolver(), (String)"preferred_tty_mode", (int)0);
        this.mImsManager.setUiTTYMode(this.mPhone.getContext(), n, null);
        ImsMultiEndpoint imsMultiEndpoint = this.getMultiEndpointInterface();
        if (imsMultiEndpoint != null) {
            imsMultiEndpoint.setExternalCallStateListener((ImsExternalCallStateListener)this.mPhone.getExternalCallTracker().getExternalCallStateListener());
        }
        this.mUtInterface = this.getUtInterface();
        imsMultiEndpoint = this.mUtInterface;
        if (imsMultiEndpoint != null) {
            imsMultiEndpoint.registerForSuppServiceIndication((Handler)this, 27, null);
        }
        if (this.mCarrierConfigLoaded) {
            this.mImsManager.updateImsServiceConfig(true);
        }
        this.sendImsServiceStateIntent("com.android.ims.IMS_SERVICE_UP");
    }

    private void stopListeningForCalls() {
        this.log("stopListeningForCalls");
        this.resetImsCapabilities();
        ImsManager imsManager = this.mImsManager;
        if (imsManager != null) {
            try {
                imsManager.getConfigInterface().removeConfigCallback(this.mConfigCallback.getBinder());
            }
            catch (ImsException imsException) {
                Log.w((String)LOG_TAG, (String)"stopListeningForCalls: unable to remove config callback.");
            }
            this.mImsManager.close();
        }
        this.sendImsServiceStateIntent("com.android.ims.IMS_SERVICE_DOWN");
    }

    @UnsupportedAppUsage
    private void switchAfterConferenceSuccess() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("switchAfterConferenceSuccess fg =");
        stringBuilder.append((Object)this.mForegroundCall.getState());
        stringBuilder.append(", bg = ");
        stringBuilder.append((Object)this.mBackgroundCall.getState());
        this.log(stringBuilder.toString());
        if (this.mBackgroundCall.getState() == Call.State.HOLDING) {
            this.log("switchAfterConferenceSuccess");
            this.mForegroundCall.switchWith(this.mBackgroundCall);
        }
    }

    private void transferHandoverConnections(ImsPhoneCall imsPhoneCall) {
        if (imsPhoneCall.mConnections != null) {
            for (Object object : imsPhoneCall.mConnections) {
                ((Connection)object).mPreHandoverState = imsPhoneCall.mState;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Connection state before handover is ");
                stringBuilder.append((Object)((Connection)object).getStateBeforeHandover());
                this.log(stringBuilder.toString());
            }
        }
        if (this.mHandoverCall.mConnections == null) {
            this.mHandoverCall.mConnections = imsPhoneCall.mConnections;
        } else {
            this.mHandoverCall.mConnections.addAll(imsPhoneCall.mConnections);
        }
        if (this.mHandoverCall.mConnections != null) {
            if (imsPhoneCall.getImsCall() != null) {
                imsPhoneCall.getImsCall().close();
            }
            for (Object object : this.mHandoverCall.mConnections) {
                ((ImsPhoneConnection)object).changeParent(this.mHandoverCall);
                ((ImsPhoneConnection)object).releaseWakeLock();
            }
        }
        if (imsPhoneCall.getState().isAlive()) {
            Object object;
            object = new StringBuilder();
            ((StringBuilder)object).append("Call is alive and state is ");
            ((StringBuilder)object).append((Object)imsPhoneCall.mState);
            this.log(((StringBuilder)object).toString());
            this.mHandoverCall.mState = imsPhoneCall.mState;
        }
        imsPhoneCall.mConnections.clear();
        imsPhoneCall.mState = Call.State.IDLE;
        if (this.mPendingMO != null) {
            this.logi("pending MO on handover, clearing...");
            this.mPendingMO = null;
        }
    }

    private void unregisterForConnectivityChanges() {
        if (this.mIsMonitoringConnectivity && this.mNotifyVtHandoverToWifiFail) {
            ConnectivityManager connectivityManager = (ConnectivityManager)this.mPhone.getContext().getSystemService("connectivity");
            if (connectivityManager != null) {
                Rlog.i((String)LOG_TAG, (String)"unregisterForConnectivityChanges");
                connectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
                this.mIsMonitoringConnectivity = false;
            }
            return;
        }
    }

    @UnsupportedAppUsage
    private void updatePhoneState() {
        PhoneConstants.State state = this.mState;
        Object object = this.mPendingMO;
        boolean bl = object == null || !((ImsPhoneConnection)object).getState().isAlive();
        this.mState = this.mRingingCall.isRinging() ? PhoneConstants.State.RINGING : (bl && this.mForegroundCall.isIdle() && this.mBackgroundCall.isIdle() ? PhoneConstants.State.IDLE : PhoneConstants.State.OFFHOOK);
        if (this.mState == PhoneConstants.State.IDLE && state != this.mState) {
            this.mVoiceCallEndedRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
        } else if (state == PhoneConstants.State.IDLE && state != this.mState) {
            this.mVoiceCallStartedRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updatePhoneState pendingMo = ");
        object = this.mPendingMO;
        object = object == null ? "null" : ((ImsPhoneConnection)object).getState();
        stringBuilder.append(object);
        stringBuilder.append(", fg= ");
        stringBuilder.append((Object)this.mForegroundCall.getState());
        stringBuilder.append("(");
        stringBuilder.append(this.mForegroundCall.getConnections().size());
        stringBuilder.append("), bg= ");
        stringBuilder.append((Object)this.mBackgroundCall.getState());
        stringBuilder.append("(");
        stringBuilder.append(this.mBackgroundCall.getConnections().size());
        stringBuilder.append(")");
        this.log(stringBuilder.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("updatePhoneState oldState=");
        ((StringBuilder)object).append((Object)state);
        ((StringBuilder)object).append(", newState=");
        ((StringBuilder)object).append((Object)this.mState);
        this.log(((StringBuilder)object).toString());
        if (this.mState != state) {
            this.mPhone.notifyPhoneStateChanged();
            this.mMetrics.writePhoneState(this.mPhone.getPhoneId(), this.mState);
            this.notifyPhoneStateChanged(state, this.mState);
        }
    }

    private void updateVtDataUsage(ImsCall imsCall, long l) {
        long l2 = 0L;
        if (this.mVtDataUsageMap.containsKey(imsCall.uniqueId)) {
            l2 = this.mVtDataUsageMap.get(imsCall.uniqueId);
        }
        l2 = l - l2;
        this.mVtDataUsageMap.put(imsCall.uniqueId, l);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateVtDataUsage: call=");
        stringBuilder.append((Object)imsCall);
        stringBuilder.append(", delta=");
        stringBuilder.append(l2);
        this.log(stringBuilder.toString());
        l = SystemClock.elapsedRealtime();
        int n = this.mPhone.getServiceState().getDataRoaming();
        imsCall = new NetworkStats(l, 1);
        imsCall.combineAllValues(this.mVtDataUsageSnapshot);
        imsCall.combineValues(new NetworkStats.Entry("vt_data0", -1, 1, 0, 1, n, 1, l2 / 2L, 0L, l2 / 2L, 0L, 0L));
        this.mVtDataUsageSnapshot = imsCall;
        stringBuilder = new NetworkStats(l, 1);
        stringBuilder.combineAllValues(this.mVtDataUsageUidSnapshot);
        if (this.mDefaultDialerUid.get() == -1) {
            imsCall = (TelecomManager)this.mPhone.getContext().getSystemService("telecom");
            this.mDefaultDialerUid.set(this.getPackageUid(this.mPhone.getContext(), imsCall.getDefaultDialerPackage()));
        }
        stringBuilder.combineValues(new NetworkStats.Entry("vt_data0", this.mDefaultDialerUid.get(), 1, 0, 1, n, 1, l2 / 2L, 0L, l2 / 2L, 0L, 0L));
        this.mVtDataUsageUidSnapshot = stringBuilder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void acceptCall(int n) throws CallStateException {
        this.log("acceptCall");
        if (this.mForegroundCall.getState().isAlive()) {
            if (this.mBackgroundCall.getState().isAlive()) throw new CallStateException("cannot accept call");
        }
        if (this.mRingingCall.getState() == Call.State.WAITING && this.mForegroundCall.getState().isAlive()) {
            this.setMute(false);
            boolean bl = false;
            ImsCall imsCall = this.mForegroundCall.getImsCall();
            ImsCall imsCall2 = this.mRingingCall.getImsCall();
            boolean bl2 = bl;
            if (this.mForegroundCall.hasConnections()) {
                bl2 = bl;
                if (this.mRingingCall.hasConnections()) {
                    bl2 = this.shouldDisconnectActiveCallOnAnswer(imsCall, imsCall2);
                }
            }
            this.mPendingCallVideoState = n;
            if (!bl2) {
                this.holdActiveCallForWaitingCall();
                return;
            }
            this.mForegroundCall.hangup();
            try {
                imsCall2.accept(ImsCallProfile.getCallTypeFromVideoState((int)n));
                return;
            }
            catch (ImsException imsException) {
                throw new CallStateException("cannot accept call");
            }
        }
        if (!this.mRingingCall.getState().isRinging()) throw new CallStateException("phone not ringing");
        this.log("acceptCall: incoming...");
        this.setMute(false);
        try {
            ImsCall imsCall = this.mRingingCall.getImsCall();
            if (imsCall != null) {
                imsCall.accept(ImsCallProfile.getCallTypeFromVideoState((int)n));
                this.mMetrics.writeOnImsCommand(this.mPhone.getPhoneId(), imsCall.getSession(), 2);
                return;
            }
            CallStateException callStateException = new CallStateException("no valid ims call");
            throw callStateException;
        }
        catch (ImsException imsException) {
            throw new CallStateException("cannot accept call");
        }
    }

    @VisibleForTesting
    public void addReasonCodeRemapping(Integer n, String string, Integer n2) {
        this.mImsReasonCodeMap.put((Pair<Integer, String>)new Pair((Object)n, (Object)string), n2);
    }

    void callEndCleanupHandOverCallIfAny() {
        if (this.mHandoverCall.mConnections.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callEndCleanupHandOverCallIfAny, mHandoverCall.mConnections=");
            stringBuilder.append(this.mHandoverCall.mConnections);
            this.log(stringBuilder.toString());
            this.mHandoverCall.mConnections.clear();
            this.mConnections.clear();
            this.mState = PhoneConstants.State.IDLE;
        }
    }

    public boolean canConference() {
        boolean bl = this.mForegroundCall.getState() == Call.State.ACTIVE && this.mBackgroundCall.getState() == Call.State.HOLDING && !this.mBackgroundCall.isFull() && !this.mForegroundCall.isFull();
        return bl;
    }

    public boolean canTransfer() {
        boolean bl = this.mForegroundCall.getState() == Call.State.ACTIVE && this.mBackgroundCall.getState() == Call.State.HOLDING;
        return bl;
    }

    public void cancelUSSD(Message message) {
        ImsCall imsCall = this.mUssdSession;
        if (imsCall == null) {
            return;
        }
        this.mPendingUssd = message;
        imsCall.terminate(501);
    }

    public void checkForDialIssues() throws CallStateException {
        if (!SystemProperties.get((String)"ro.telephony.disable-call", (String)"false").equals("true")) {
            if (this.mPendingMO == null) {
                if (!this.mRingingCall.isRinging()) {
                    if (!(this.mForegroundCall.getState().isAlive() & this.mBackgroundCall.getState().isAlive())) {
                        return;
                    }
                    throw new CallStateException(6, "Already an active foreground and background call.");
                }
                throw new CallStateException(4, "Can't place a call while another is ringing.");
            }
            throw new CallStateException(3, "Another outgoing call is already being dialed.");
        }
        throw new CallStateException(5, "ro.telephony.disable-call has been used to disable calling.");
    }

    @UnsupportedAppUsage
    public void clearDisconnected() {
        this.log("clearDisconnected");
        this.internalClearDisconnected();
        this.updatePhoneState();
        this.mPhone.notifyPreciseCallStateChanged();
    }

    public void conference() {
        CharSequence charSequence;
        ImsCall imsCall = this.mForegroundCall.getImsCall();
        if (imsCall == null) {
            this.log("conference no foreground ims call");
            return;
        }
        ImsCall imsCall2 = this.mBackgroundCall.getImsCall();
        if (imsCall2 == null) {
            this.log("conference no background ims call");
            return;
        }
        if (imsCall.isCallSessionMergePending()) {
            this.log("conference: skip; foreground call already in process of merging.");
            return;
        }
        if (imsCall2.isCallSessionMergePending()) {
            this.log("conference: skip; background call already in process of merging.");
            return;
        }
        long l = this.mForegroundCall.getEarliestConnectTime();
        long l2 = this.mBackgroundCall.getEarliestConnectTime();
        if (l > 0L && l2 > 0L) {
            l2 = Math.min(this.mForegroundCall.getEarliestConnectTime(), this.mBackgroundCall.getEarliestConnectTime());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("conference - using connect time = ");
            ((StringBuilder)charSequence).append(l2);
            this.log(((StringBuilder)charSequence).toString());
        } else if (l > 0L) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("conference - bg call connect time is 0; using fg = ");
            ((StringBuilder)charSequence).append(l);
            this.log(((StringBuilder)charSequence).toString());
            l2 = l;
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("conference - fg call connect time is 0; using bg = ");
            ((StringBuilder)charSequence).append(l2);
            this.log(((StringBuilder)charSequence).toString());
        }
        charSequence = "";
        Object object = this.mForegroundCall.getFirstConnection();
        if (object != null) {
            ((ImsPhoneConnection)object).setConferenceConnectTime(l2);
            ((ImsPhoneConnection)object).handleMergeStart();
            charSequence = ((Connection)object).getTelecomCallId();
            this.cacheConnectionTimeWithPhoneNumber((ImsPhoneConnection)object);
        }
        object = "";
        Object object2 = this.findConnection(imsCall2);
        if (object2 != null) {
            ((ImsPhoneConnection)object2).handleMergeStart();
            object = ((Connection)object2).getTelecomCallId();
            this.cacheConnectionTimeWithPhoneNumber((ImsPhoneConnection)object2);
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("conference: fgCallId=");
        ((StringBuilder)object2).append((String)charSequence);
        ((StringBuilder)object2).append(", bgCallId=");
        ((StringBuilder)object2).append((String)object);
        this.log(((StringBuilder)object2).toString());
        try {
            imsCall.merge(imsCall2);
        }
        catch (ImsException imsException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("conference ");
            ((StringBuilder)object).append(imsException.getMessage());
            this.log(((StringBuilder)object).toString());
        }
    }

    @UnsupportedAppUsage
    public Connection dial(String string, int n, Bundle bundle) throws CallStateException {
        return this.dial(string, (ImsPhone.ImsDialArgs)((ImsPhone.ImsDialArgs.Builder)((ImsPhone.ImsDialArgs.Builder)new ImsPhone.ImsDialArgs.Builder().setIntentExtras(bundle)).setVideoState(n)).setClirMode(this.getClirMode()).build());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Connection dial(String object, ImsPhone.ImsDialArgs imsDialArgs) throws CallStateException {
        boolean bl3;
        boolean bl2;
        int n2;
        int n;
        boolean bl;
        block35 : {
            block34 : {
                Object object2;
                block33 : {
                    // MONITORENTER : this
                    bl2 = this.isPhoneInEcbMode();
                    bl = this.mPhoneNumberUtilsProxy.isEmergencyNumber((String)object);
                    if (!this.shouldNumberBePlacedOnIms(bl, (String)object)) {
                        Rlog.i((String)LOG_TAG, (String)"dial: shouldNumberBePlacedOnIms = false");
                        object = new CallStateException("cs_fallback");
                        throw object;
                    }
                    n2 = imsDialArgs.clirMode;
                    n = imsDialArgs.videoState;
                    object2 = new StringBuilder();
                    object2.append("dial clirMode=");
                    object2.append(n2);
                    this.log(object2.toString());
                    if (bl) {
                        object2 = new StringBuilder();
                        object2.append("dial emergency call, set clirModIe=");
                        object2.append(2);
                        this.log(object2.toString());
                        n2 = 2;
                    }
                    this.clearDisconnected();
                    if (this.mImsManager == null) {
                        object = new CallStateException("service not available");
                        throw object;
                    }
                    this.checkForDialIssues();
                    if (bl2 && bl) {
                        this.handleEcmTimer(1);
                    }
                    if (bl && VideoProfile.isVideo((int)n) && !this.mAllowEmergencyVideoCalls) {
                        this.loge("dial: carrier does not support video emergency calls; downgrade to audio-only");
                        n = 0;
                    }
                    bl3 = false;
                    if (this.mForegroundCall.getState() == Call.State.ACTIVE) {
                        if (this.mBackgroundCall.getState() != Call.State.IDLE) {
                            object = new CallStateException(6, "Already too many ongoing calls.");
                            throw object;
                        }
                        bl3 = true;
                        this.mPendingCallVideoState = n;
                        this.mPendingIntentExtras = imsDialArgs.intentExtras;
                        this.holdActiveCallForPendingMo();
                    }
                    Object object3 = Call.State.IDLE;
                    object2 = Call.State.IDLE;
                    this.mClirMode = n2;
                    Object object4 = this.mSyncHold;
                    // MONITORENTER : object4
                    if (bl3) {
                        block32 : {
                            Object object5;
                            try {
                                object3 = this.mForegroundCall.getState();
                                object2 = this.mBackgroundCall.getState();
                                object5 = Call.State.ACTIVE;
                                if (object3 == object5) break block32;
                            }
                            catch (Throwable throwable) {
                                throw object;
                            }
                            object5 = Call.State.HOLDING;
                            if (object2 == object5) {
                                bl3 = false;
                                object5 = object3;
                                object3 = object2;
                                object2 = object5;
                                object2 = object3;
                            } else {
                                object5 = object2;
                                object2 = object3;
                                object2 = object5;
                            }
                            break block33;
                        }
                        object = new CallStateException("cannot dial in current state");
                        throw object;
                    }
                    object2 = object3;
                }
                this.mLastDialString = object;
                this.mLastDialArgs = imsDialArgs;
                object2 = new ImsPhoneConnection((Phone)this.mPhone, this.checkForTestEmergencyNumber((String)object), this, this.mForegroundCall, bl);
                this.mPendingMO = object2;
                if (bl) {
                    if (imsDialArgs.intentExtras == null) break block34;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("dial ims emergency dialer: ");
                    ((StringBuilder)object).append(imsDialArgs.intentExtras.getBoolean("android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL"));
                    Rlog.i((String)LOG_TAG, (String)((StringBuilder)object).toString());
                    this.mPendingMO.setHasKnownUserIntentEmergency(imsDialArgs.intentExtras.getBoolean("android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL"));
                }
            }
            this.mPendingMO.setVideoState(n);
            object = imsDialArgs.rttTextStream;
            if (object == null) break block35;
            try {
                this.log("dial: setting RTT stream on mPendingMO");
                this.mPendingMO.setCurrentRttTextStream(imsDialArgs.rttTextStream);
                // MONITOREXIT : object4
            }
            catch (Throwable throwable) {
                throw object;
            }
        }
        this.addConnection(this.mPendingMO);
        if (!bl3) {
            if (!(!bl2 || bl2 && bl)) {
                try {
                    this.getEcbmInterface().exitEmergencyCallbackMode();
                    this.mPhone.setOnEcbModeExitResponse(this, 14, null);
                    this.pendingCallClirMode = n2;
                    this.mPendingCallVideoState = n;
                    this.pendingCallInEcm = true;
                }
                catch (ImsException imsException) {
                    imsException.printStackTrace();
                    CallStateException callStateException = new CallStateException("service not available");
                    throw callStateException;
                }
            } else {
                this.dialInternal(this.mPendingMO, n2, n, imsDialArgs.intentExtras);
            }
        }
        this.updatePhoneState();
        this.mPhone.notifyPreciseCallStateChanged();
        object = this.mPendingMO;
        // MONITOREXIT : this
        return object;
        catch (Throwable throwable) {
            throw object;
        }
    }

    public void dispose() {
        this.log("dispose");
        this.mRingingCall.dispose();
        this.mBackgroundCall.dispose();
        this.mForegroundCall.dispose();
        this.mHandoverCall.dispose();
        this.clearDisconnected();
        ImsUtInterface imsUtInterface = this.mUtInterface;
        if (imsUtInterface != null) {
            imsUtInterface.unregisterForSuppServiceIndication((Handler)this);
        }
        this.mPhone.getContext().unregisterReceiver(this.mReceiver);
        this.mPhone.getDefaultPhone().getDataEnabledSettings().unregisterForDataEnabledChanged(this);
        this.mImsManagerConnector.disconnect();
    }

    @Override
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("ImsPhoneCallTracker extends:");
        super.dump((FileDescriptor)object, printWriter, arrstring);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" mVoiceCallEndedRegistrants=");
        stringBuilder.append((Object)this.mVoiceCallEndedRegistrants);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mVoiceCallStartedRegistrants=");
        stringBuilder.append((Object)this.mVoiceCallStartedRegistrants);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mRingingCall=");
        stringBuilder.append(this.mRingingCall);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mForegroundCall=");
        stringBuilder.append(this.mForegroundCall);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mBackgroundCall=");
        stringBuilder.append(this.mBackgroundCall);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mHandoverCall=");
        stringBuilder.append(this.mHandoverCall);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mPendingMO=");
        stringBuilder.append(this.mPendingMO);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mPhone=");
        stringBuilder.append(this.mPhone);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mDesiredMute=");
        stringBuilder.append(this.mDesiredMute);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mState=");
        stringBuilder.append((Object)this.mState);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mMmTelCapabilities=");
        stringBuilder.append((Object)this.mMmTelCapabilities);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mDefaultDialerUid=");
        stringBuilder.append(this.mDefaultDialerUid.get());
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mVtDataUsageSnapshot=");
        stringBuilder.append((Object)this.mVtDataUsageSnapshot);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mVtDataUsageUidSnapshot=");
        stringBuilder.append((Object)this.mVtDataUsageUidSnapshot);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mCallQualityMetrics=");
        stringBuilder.append(this.mCallQualityMetrics);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mCallQualityMetricsHistory=");
        stringBuilder.append(this.mCallQualityMetricsHistory);
        printWriter.println(stringBuilder.toString());
        printWriter.flush();
        printWriter.println("++++++++++++++++++++++++++++++++");
        try {
            if (this.mImsManager != null) {
                this.mImsManager.dump((FileDescriptor)object, printWriter, arrstring);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        object = this.mConnections;
        if (object != null && ((ArrayList)object).size() > 0) {
            printWriter.println("mConnections:");
            for (int i = 0; i < this.mConnections.size(); ++i) {
                object = new StringBuilder();
                ((StringBuilder)object).append("  [");
                ((StringBuilder)object).append(i);
                ((StringBuilder)object).append("]: ");
                ((StringBuilder)object).append(this.mConnections.get(i));
                printWriter.println(((StringBuilder)object).toString());
            }
        }
    }

    public void explicitCallTransfer() {
    }

    protected void finalize() {
        this.log("ImsPhoneCallTracker finalized");
    }

    public int getClirMode() {
        if (this.mSharedPreferenceProxy != null && this.mPhone.getDefaultPhone() != null) {
            SharedPreferences sharedPreferences = this.mSharedPreferenceProxy.getDefaultSharedPreferences(this.mPhone.getContext());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("clir_key");
            stringBuilder.append(this.mPhone.getDefaultPhone().getPhoneId());
            return sharedPreferences.getInt(stringBuilder.toString(), 0);
        }
        this.loge("dial; could not get default CLIR mode.");
        return 0;
    }

    @VisibleForTesting
    public int getDisconnectCauseFromReasonInfo(ImsReasonInfo imsReasonInfo, Call.State state) {
        switch (this.maybeRemapReasonCode(imsReasonInfo)) {
            default: {
                break;
            }
            case 1515: {
                return 25;
            }
            case 1514: {
                return 71;
            }
            case 1512: {
                return 60;
            }
            case 1407: {
                return 59;
            }
            case 1406: {
                return 54;
            }
            case 1405: {
                return 55;
            }
            case 1403: {
                return 53;
            }
            case 1016: {
                return 51;
            }
            case 1014: {
                return 52;
            }
            case 501: {
                return 3;
            }
            case 364: {
                return 64;
            }
            case 363: {
                return 63;
            }
            case 361: 
            case 510: {
                return 2;
            }
            case 352: 
            case 354: {
                return 9;
            }
            case 338: {
                return 4;
            }
            case 337: 
            case 341: {
                return 8;
            }
            case 333: {
                return 7;
            }
            case 332: {
                return 12;
            }
            case 321: 
            case 331: 
            case 340: 
            case 362: {
                return 12;
            }
            case 251: {
                return 68;
            }
            case 250: {
                return 67;
            }
            case 249: {
                return 70;
            }
            case 248: {
                return 69;
            }
            case 247: {
                return 66;
            }
            case 246: {
                return 48;
            }
            case 245: {
                return 47;
            }
            case 244: {
                return 46;
            }
            case 243: {
                return 58;
            }
            case 241: {
                return 21;
            }
            case 240: {
                return 20;
            }
            case 201: 
            case 202: 
            case 203: 
            case 335: {
                return 13;
            }
            case 143: 
            case 1404: {
                return 16;
            }
            case 112: 
            case 505: {
                if (state == Call.State.DIALING) {
                    return 62;
                }
                return 61;
            }
            case 111: {
                return 17;
            }
            case 108: {
                return 45;
            }
            case 106: 
            case 121: 
            case 122: 
            case 123: 
            case 124: 
            case 131: 
            case 132: 
            case 144: {
                return 18;
            }
            case 0: {
                if (this.mPhone.getDefaultPhone().getServiceStateTracker().mRestrictedState.isCsRestricted()) {
                    return 22;
                }
                if (this.mPhone.getDefaultPhone().getServiceStateTracker().mRestrictedState.isCsEmergencyRestricted()) {
                    return 24;
                }
                if (!this.mPhone.getDefaultPhone().getServiceStateTracker().mRestrictedState.isCsNormalRestricted()) break;
                return 23;
            }
        }
        return 36;
    }

    ImsEcbm getEcbmInterface() throws ImsException {
        ImsManager imsManager = this.mImsManager;
        if (imsManager != null) {
            return imsManager.getEcbmInterface();
        }
        throw this.getImsManagerIsNullException();
    }

    public int getImsRegistrationTech() {
        ImsManager imsManager = this.mImsManager;
        if (imsManager != null) {
            return imsManager.getRegistrationTech();
        }
        return -1;
    }

    ImsMultiEndpoint getMultiEndpointInterface() throws ImsException {
        ImsManager imsManager = this.mImsManager;
        if (imsManager != null) {
            try {
                imsManager = imsManager.getMultiEndpointInterface();
                return imsManager;
            }
            catch (ImsException imsException) {
                if (imsException.getCode() == 902) {
                    return null;
                }
                throw imsException;
            }
        }
        throw this.getImsManagerIsNullException();
    }

    public boolean getMute() {
        return this.mDesiredMute;
    }

    @Override
    public ImsPhone getPhone() {
        return this.mPhone;
    }

    @Override
    public PhoneConstants.State getState() {
        return this.mState;
    }

    @UnsupportedAppUsage
    public ImsUtInterface getUtInterface() throws ImsException {
        ImsManager imsManager = this.mImsManager;
        if (imsManager != null) {
            return imsManager.getSupplementaryServiceConfiguration();
        }
        throw this.getImsManagerIsNullException();
    }

    public NetworkStats getVtDataUsage(boolean bl) {
        NetworkStats networkStats;
        if (this.mState != PhoneConstants.State.IDLE) {
            Iterator<ImsPhoneConnection> iterator = this.mConnections.iterator();
            while (iterator.hasNext()) {
                networkStats = iterator.next().getVideoProvider();
                if (networkStats == null) continue;
                networkStats.onRequestConnectionDataUsage();
            }
        }
        networkStats = bl ? this.mVtDataUsageUidSnapshot : this.mVtDataUsageSnapshot;
        return networkStats;
    }

    @Override
    public void handleMessage(Message object) {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("handleMessage what=");
        ((StringBuilder)object2).append(((Message)object).what);
        this.log(((StringBuilder)object2).toString());
        switch (((Message)object).what) {
            default: {
                break;
            }
            case 31: {
                try {
                    this.resumeForegroundCall();
                }
                catch (ImsException imsException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("handleMessage EVENT_RESUME_NOW_FOREGROUND_CALL exception=");
                    ((StringBuilder)object).append((Object)imsException);
                    this.loge(((StringBuilder)object).toString());
                }
                break;
            }
            case 30: {
                try {
                    this.answerWaitingCall();
                }
                catch (ImsException imsException) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("handleMessage EVENT_ANSWER_WAITING_CALL exception=");
                    ((StringBuilder)object2).append((Object)imsException);
                    this.loge(((StringBuilder)object2).toString());
                }
                break;
            }
            case 29: {
                object = (Pair)((Message)object).obj;
                this.mPhone.getDefaultPhone().getServiceStateTracker().unregisterForNetworkAttached(this);
                this.removeMessages(28);
                this.sendCallStartFailedDisconnect((ImsCall)((Pair)object).first, (ImsReasonInfo)((Pair)object).second);
                break;
            }
            case 28: {
                object = (Pair)((AsyncResult)object.obj).userObj;
                this.removeMessages(29);
                this.mPhone.getDefaultPhone().getServiceStateTracker().unregisterForNetworkAttached(this);
                object2 = this.findConnection((ImsCall)((Pair)object).first);
                if (object2 == null) {
                    this.sendCallStartFailedDisconnect((ImsCall)((Pair)object).first, (ImsReasonInfo)((Pair)object).second);
                    break;
                }
                this.mForegroundCall.detach((ImsPhoneConnection)object2);
                this.removeConnection((ImsPhoneConnection)object2);
                try {
                    ((Connection)object2).onOriginalConnectionReplaced(this.mPhone.getDefaultPhone().dial(this.mLastDialString, this.mLastDialArgs));
                }
                catch (CallStateException callStateException) {
                    this.sendCallStartFailedDisconnect((ImsCall)((Pair)object).first, (ImsReasonInfo)((Pair)object).second);
                }
                break;
            }
            case 27: {
                object2 = (AsyncResult)((Message)object).obj;
                object = new ImsPhoneMmiCode(this.mPhone);
                try {
                    ((ImsPhoneMmiCode)object).setIsSsInfo(true);
                    ((ImsPhoneMmiCode)object).processImsSsData((AsyncResult)object2);
                }
                catch (ImsException imsException) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Exception in parsing SS Data: ");
                    ((StringBuilder)object2).append((Object)imsException);
                    Rlog.e((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                }
                break;
            }
            case 26: {
                object2 = (SomeArgs)((Message)object).obj;
                try {
                    this.handleFeatureCapabilityChanged((ImsFeature.Capabilities)((SomeArgs)object2).arg1);
                    break;
                }
                finally {
                    object2.recycle();
                }
            }
            case 25: {
                if (!(((Message)object).obj instanceof ImsCall)) break;
                object = (ImsCall)((Message)object).obj;
                if (object != this.mForegroundCall.getImsCall()) {
                    Rlog.i((String)LOG_TAG, (String)"handoverCheck: no longer FG; check skipped.");
                    this.unregisterForConnectivityChanges();
                    return;
                }
                if (!this.mHasAttemptedStartOfCallHandover) {
                    this.mHasAttemptedStartOfCallHandover = true;
                }
                if (object.isWifiCall()) break;
                object2 = this.findConnection((ImsCall)object);
                if (object2 != null) {
                    Rlog.i((String)LOG_TAG, (String)"handoverCheck: handover failed.");
                    ((Connection)object2).onHandoverToWifiFailed();
                }
                if (!object.isVideoCall() || ((Connection)object2).getDisconnectCause() != 0) break;
                this.registerForConnectivityChanges();
                break;
            }
            case 23: {
                object = (AsyncResult)((Message)object).obj;
                if (!(((AsyncResult)object).result instanceof Pair)) break;
                object = (Pair)((AsyncResult)object).result;
                this.onDataEnabledChanged((Boolean)((Pair)object).first, (Integer)((Pair)object).second);
                break;
            }
            case 22: {
                object2 = (AsyncResult)((Message)object).obj;
                object = (ImsCall)((AsyncResult)object2).userObj;
                object2 = (long)((Long)((AsyncResult)object2).result);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("VT data usage update. usage = ");
                stringBuilder.append(object2);
                stringBuilder.append(", imsCall = ");
                stringBuilder.append(object);
                this.log(stringBuilder.toString());
                if ((Long)object2 <= 0L) break;
                this.updateVtDataUsage((ImsCall)object, (Long)object2);
                break;
            }
            case 21: {
                if (this.mPendingMO == null) break;
                try {
                    this.getEcbmInterface().exitEmergencyCallbackMode();
                    this.mPhone.setOnEcbModeExitResponse(this, 14, null);
                    this.pendingCallClirMode = this.mClirMode;
                    this.pendingCallInEcm = true;
                }
                catch (ImsException imsException) {
                    imsException.printStackTrace();
                    this.mPendingMO.setDisconnectCause(36);
                    this.sendEmptyMessageDelayed(18, 500L);
                }
                break;
            }
            case 20: {
                this.dialInternal(this.mPendingMO, this.mClirMode, this.mPendingCallVideoState, this.mPendingIntentExtras);
                this.mPendingIntentExtras = null;
                break;
            }
            case 18: {
                object = this.mPendingMO;
                if (object != null) {
                    ((ImsPhoneConnection)object).onDisconnect();
                    this.removeConnection(this.mPendingMO);
                    this.mPendingMO = null;
                }
                this.mPendingIntentExtras = null;
                this.updatePhoneState();
                this.mPhone.notifyPreciseCallStateChanged();
                break;
            }
            case 14: {
                if (this.pendingCallInEcm) {
                    this.dialInternal(this.mPendingMO, this.pendingCallClirMode, this.mPendingCallVideoState, this.mPendingIntentExtras);
                    this.mPendingIntentExtras = null;
                    this.pendingCallInEcm = false;
                }
                this.mPhone.unsetOnEcbModeExitResponse(this);
            }
        }
    }

    @Override
    protected void handlePollCalls(AsyncResult asyncResult) {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void hangup(ImsPhoneCall var1_1) throws CallStateException {
        this.log("hangup call");
        if (var1_1.getConnections().size() == 0) throw new CallStateException("no connections");
        var2_3 = var1_1.getImsCall();
        var3_4 = false;
        if (var1_1 == this.mRingingCall) {
            this.log("(ringing) hangup incoming");
            var3_4 = true;
        } else if (var1_1 == this.mForegroundCall) {
            if (var1_1.isDialingOrAlerting()) {
                this.log("(foregnd) hangup dialing or alerting...");
            } else {
                this.log("(foregnd) hangup foreground");
            }
        } else {
            if (var1_1 != this.mBackgroundCall) {
                var2_3 = new StringBuilder();
                var2_3.append("ImsPhoneCall ");
                var2_3.append(var1_1);
                var2_3.append("does not belong to ImsPhoneCallTracker ");
                var2_3.append(this);
                throw new CallStateException(var2_3.toString());
            }
            this.log("(backgnd) hangup waiting or background");
        }
        var1_1.onHangupLocal();
        if (var2_3 == null) ** GOTO lbl37
        if (!var3_4) ** GOTO lbl34
        try {
            block10 : {
                var2_3.reject(504);
                this.mMetrics.writeOnImsCommand(this.mPhone.getPhoneId(), var2_3.getSession(), 3);
                break block10;
lbl34: // 1 sources:
                var2_3.terminate(501);
                this.mMetrics.writeOnImsCommand(this.mPhone.getPhoneId(), var2_3.getSession(), 4);
                break block10;
lbl37: // 1 sources:
                if (this.mPendingMO != null && var1_1 == this.mForegroundCall) {
                    this.mPendingMO.update(null, Call.State.DISCONNECTED);
                    this.mPendingMO.onDisconnect();
                    this.removeConnection(this.mPendingMO);
                    this.mPendingMO = null;
                    this.updatePhoneState();
                    this.removeMessages(20);
                }
            }
            this.mPhone.notifyPreciseCallStateChanged();
            return;
        }
        catch (ImsException var1_2) {
            throw new CallStateException(var1_2.getMessage());
        }
    }

    public void hangup(ImsPhoneConnection imsPhoneConnection) throws CallStateException {
        this.log("hangup connection");
        if (imsPhoneConnection.getOwner() == this) {
            this.hangup((ImsPhoneCall)imsPhoneConnection.getCall());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImsPhoneConnection ");
        stringBuilder.append(imsPhoneConnection);
        stringBuilder.append("does not belong to ImsPhoneCallTracker ");
        stringBuilder.append(this);
        throw new CallStateException(stringBuilder.toString());
    }

    public void holdActiveCall() throws CallStateException {
        if (this.mForegroundCall.getState() == Call.State.ACTIVE) {
            if (this.mHoldSwitchingState != HoldSwapState.PENDING_SINGLE_CALL_HOLD && this.mHoldSwitchingState != HoldSwapState.SWAPPING_ACTIVE_AND_HELD) {
                ImsCall imsCall = this.mForegroundCall.getImsCall();
                if (this.mBackgroundCall.getState().isAlive()) {
                    this.mCallExpectedToResume = this.mBackgroundCall.getImsCall();
                    this.mHoldSwitchingState = HoldSwapState.SWAPPING_ACTIVE_AND_HELD;
                } else {
                    this.mHoldSwitchingState = HoldSwapState.PENDING_SINGLE_CALL_HOLD;
                }
                this.logHoldSwapState("holdActiveCall");
                this.mForegroundCall.switchWith(this.mBackgroundCall);
                try {
                    imsCall.hold();
                    this.mMetrics.writeOnImsCommand(this.mPhone.getPhoneId(), imsCall.getSession(), 5);
                }
                catch (ImsException imsException) {
                    this.mForegroundCall.switchWith(this.mBackgroundCall);
                    throw new CallStateException(imsException.getMessage());
                }
            } else {
                this.logi("Ignoring hold request while already holding or swapping");
                return;
            }
        }
    }

    public void holdActiveCallForWaitingCall() throws CallStateException {
        boolean bl = !this.mBackgroundCall.getState().isAlive() && this.mRingingCall.getState() == Call.State.WAITING;
        if (bl) {
            ImsCall imsCall = this.mForegroundCall.getImsCall();
            this.mHoldSwitchingState = HoldSwapState.HOLDING_TO_ANSWER_INCOMING;
            this.mForegroundCall.switchWith(this.mBackgroundCall);
            this.logHoldSwapState("holdActiveCallForWaitingCall");
            try {
                imsCall.hold();
                this.mMetrics.writeOnImsCommand(this.mPhone.getPhoneId(), imsCall.getSession(), 5);
            }
            catch (ImsException imsException) {
                this.mForegroundCall.switchWith(this.mBackgroundCall);
                throw new CallStateException(imsException.getMessage());
            }
        }
    }

    public boolean isCarrierDowngradeOfVtCallSupported() {
        return this.mSupportDowngradeVtToAudio;
    }

    public boolean isImsCapabilityAvailable(int n, int n2) {
        boolean bl = this.getImsRegistrationTech() == n2 && this.mMmTelCapabilities.isCapable(n);
        return bl;
    }

    boolean isImsServiceReady() {
        ImsManager imsManager = this.mImsManager;
        if (imsManager == null) {
            return false;
        }
        return imsManager.isServiceReady();
    }

    public boolean isInEmergencyCall() {
        return this.mIsInEmergencyCall;
    }

    public boolean isUtEnabled() {
        return this.mMmTelCapabilities.isCapable(4);
    }

    public boolean isViLteDataMetered() {
        return this.mIsViLteDataMetered;
    }

    public boolean isVideoCallEnabled() {
        return this.mMmTelCapabilities.isCapable(2);
    }

    public boolean isVolteEnabled() {
        int n = this.getImsRegistrationTech();
        boolean bl = false;
        n = n == 0 ? 1 : 0;
        boolean bl2 = bl;
        if (n != 0) {
            bl2 = bl;
            if (this.mMmTelCapabilities.isCapable(1)) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public boolean isVowifiEnabled() {
        int n = this.getImsRegistrationTech();
        boolean bl = false;
        n = n == 1 ? 1 : 0;
        boolean bl2 = bl;
        if (n != 0) {
            bl2 = bl;
            if (this.mMmTelCapabilities.isCapable(1)) {
                bl2 = true;
            }
        }
        return bl2;
    }

    @UnsupportedAppUsage
    @Override
    protected void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    void logHoldSwapState(String string) {
        String string2 = "???";
        switch (this.mHoldSwitchingState) {
            default: {
                break;
            }
            case HOLDING_TO_DIAL_OUTGOING: {
                string2 = "HOLDING_TO_DIAL_OUTGOING";
                break;
            }
            case PENDING_RESUME_FOREGROUND_AFTER_FAILURE: {
                string2 = "PENDING_RESUME_FOREGROUND_AFTER_FAILURE";
                break;
            }
            case HOLDING_TO_ANSWER_INCOMING: {
                string2 = "HOLDING_TO_ANSWER_INCOMING";
                break;
            }
            case SWAPPING_ACTIVE_AND_HELD: {
                string2 = "SWAPPING_ACTIVE_AND_HELD";
                break;
            }
            case PENDING_SINGLE_CALL_UNHOLD: {
                string2 = "PENDING_SINGLE_CALL_UNHOLD";
                break;
            }
            case PENDING_SINGLE_CALL_HOLD: {
                string2 = "PENDING_SINGLE_CALL_HOLD";
                break;
            }
            case INACTIVE: {
                string2 = "INACTIVE";
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("holdSwapState set to ");
        stringBuilder.append(string2);
        stringBuilder.append(" at ");
        stringBuilder.append(string);
        this.logi(stringBuilder.toString());
    }

    void logState() {
        if (!VERBOSE_STATE_LOGGING) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current IMS PhoneCall State:\n");
        stringBuilder.append(" Foreground: ");
        stringBuilder.append(this.mForegroundCall);
        stringBuilder.append("\n");
        stringBuilder.append(" Background: ");
        stringBuilder.append(this.mBackgroundCall);
        stringBuilder.append("\n");
        stringBuilder.append(" Ringing: ");
        stringBuilder.append(this.mRingingCall);
        stringBuilder.append("\n");
        stringBuilder.append(" Handover: ");
        stringBuilder.append(this.mHandoverCall);
        stringBuilder.append("\n");
        Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @UnsupportedAppUsage
    protected void loge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
    }

    void logi(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.i((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @VisibleForTesting
    public int maybeRemapReasonCode(ImsReasonInfo imsReasonInfo) {
        int n = imsReasonInfo.getCode();
        CharSequence charSequence = imsReasonInfo.getExtraMessage();
        String string = charSequence;
        if (charSequence == null) {
            string = "";
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("maybeRemapReasonCode : fromCode = ");
        ((StringBuilder)charSequence).append(imsReasonInfo.getCode());
        ((StringBuilder)charSequence).append(" ; message = ");
        ((StringBuilder)charSequence).append(string);
        this.log(((StringBuilder)charSequence).toString());
        charSequence = new Pair((Object)n, (Object)string);
        Pair pair = new Pair(null, (Object)string);
        if (this.mImsReasonCodeMap.containsKey(charSequence)) {
            n = this.mImsReasonCodeMap.get(charSequence);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("maybeRemapReasonCode : fromCode = ");
            ((StringBuilder)charSequence).append(imsReasonInfo.getCode());
            ((StringBuilder)charSequence).append(" ; message = ");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" ; toCode = ");
            ((StringBuilder)charSequence).append(n);
            this.log(((StringBuilder)charSequence).toString());
            return n;
        }
        if (!string.isEmpty() && this.mImsReasonCodeMap.containsKey((Object)pair)) {
            n = this.mImsReasonCodeMap.get((Object)pair);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("maybeRemapReasonCode : fromCode(wildcard) = ");
            ((StringBuilder)charSequence).append(imsReasonInfo.getCode());
            ((StringBuilder)charSequence).append(" ; message = ");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" ; toCode = ");
            ((StringBuilder)charSequence).append(n);
            this.log(((StringBuilder)charSequence).toString());
            return n;
        }
        return n;
    }

    void notifySrvccState(Call.SrvccState srvccState) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("notifySrvccState state=");
        stringBuilder.append((Object)srvccState);
        this.log(stringBuilder.toString());
        this.mSrvccState = srvccState;
        if (this.mSrvccState == Call.SrvccState.COMPLETED) {
            this.resetState();
            this.transferHandoverConnections(this.mForegroundCall);
            this.transferHandoverConnections(this.mBackgroundCall);
            this.transferHandoverConnections(this.mRingingCall);
        }
    }

    @VisibleForTesting
    public void onCallHoldReceived(ImsCall imsCall) {
        this.log("onCallHoldReceived");
        Object object = this.findConnection(imsCall);
        if (object != null) {
            if (!this.mOnHoldToneStarted && (ImsPhoneCall.isLocalTone(imsCall) || this.mAlwaysPlayRemoteHoldTone) && ((ImsPhoneConnection)object).getState() == Call.State.ACTIVE) {
                this.mPhone.startOnHoldTone((Connection)object);
                this.mOnHoldToneStarted = true;
                this.mOnHoldToneId = System.identityHashCode(object);
            }
            ((Connection)object).onConnectionEvent("android.telecom.event.CALL_REMOTELY_HELD", null);
            if (this.mPhone.getContext().getResources().getBoolean(17891562) && this.mSupportPauseVideo && VideoProfile.isVideo((int)((Connection)object).getVideoState())) {
                ((ImsPhoneConnection)object).changeToPausedState();
            }
        }
        object = new SuppServiceNotification();
        ((SuppServiceNotification)object).notificationType = 1;
        ((SuppServiceNotification)object).code = 2;
        this.mPhone.notifySuppSvcNotification((SuppServiceNotification)object);
        this.mMetrics.writeOnImsCallHoldReceived(this.mPhone.getPhoneId(), imsCall.getCallSession());
    }

    @Override
    public void pullExternalCall(String object, int n, int n2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("CallPull", true);
        bundle.putInt("android.telephony.ImsExternalCallTracker.extra.EXTERNAL_CALL_ID", n2);
        try {
            object = this.dial((String)object, n, bundle);
            this.mPhone.notifyUnknownConnection((Connection)object);
        }
        catch (CallStateException callStateException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("pullExternalCall failed - ");
            ((StringBuilder)object).append(callStateException);
            this.loge(((StringBuilder)object).toString());
        }
    }

    @Override
    public void registerForVoiceCallEnded(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mVoiceCallEndedRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForVoiceCallStarted(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mVoiceCallStartedRegistrants.add((Registrant)handler);
    }

    public void registerPhoneStateListener(PhoneStateListener phoneStateListener) {
        this.mPhoneStateListeners.add(phoneStateListener);
    }

    public void rejectCall() throws CallStateException {
        this.log("rejectCall");
        if (this.mRingingCall.getState().isRinging()) {
            this.hangup(this.mRingingCall);
            return;
        }
        throw new CallStateException("phone not ringing");
    }

    public void sendCallStartFailedDisconnect(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
        this.mPendingMO = null;
        Object object = this.findConnection(imsCall);
        object = object != null ? object.getState() : Call.State.DIALING;
        int n = this.getDisconnectCauseFromReasonInfo(imsReasonInfo, (Call.State)((Object)object));
        this.processCallStateChange(imsCall, Call.State.DISCONNECTED, n);
        this.mPhone.notifyImsReason(imsReasonInfo);
    }

    public void sendDtmf(char c, Message message) {
        this.log("sendDtmf");
        ImsCall imsCall = this.mForegroundCall.getImsCall();
        if (imsCall != null) {
            imsCall.sendDtmf(c, message);
        }
    }

    public void sendUSSD(String charSequence, Message message) {
        this.log("sendUSSD");
        try {
            if (this.mUssdSession != null) {
                this.mPendingUssd = null;
                this.mUssdSession.sendUssd((String)charSequence);
                AsyncResult.forMessage((Message)message, null, null);
                message.sendToTarget();
                return;
            }
            if (this.mImsManager == null) {
                this.mPhone.sendErrorResponse(message, this.getImsManagerIsNullException());
                return;
            }
            ImsCallProfile imsCallProfile = this.mImsManager.createCallProfile(1, 2);
            imsCallProfile.setCallExtraInt("dialstring", 2);
            ImsManager imsManager = this.mImsManager;
            ImsCall.Listener listener = this.mImsUssdListener;
            this.mUssdSession = imsManager.makeCall(imsCallProfile, new String[]{charSequence}, listener);
            this.mPendingUssd = message;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("pending ussd updated, ");
            ((StringBuilder)charSequence).append((Object)this.mPendingUssd);
            this.log(((StringBuilder)charSequence).toString());
        }
        catch (ImsException imsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendUSSD : ");
            stringBuilder.append((Object)imsException);
            this.loge(stringBuilder.toString());
            this.mPhone.sendErrorResponse(message, imsException);
            this.retryGetImsService();
        }
    }

    @VisibleForTesting
    public void setAlwaysPlayRemoteHoldTone(boolean bl) {
        this.mAlwaysPlayRemoteHoldTone = bl;
    }

    @VisibleForTesting
    public void setDataEnabled(boolean bl) {
        this.mIsDataEnabled = bl;
    }

    public void setMute(boolean bl) {
        this.mDesiredMute = bl;
        this.mForegroundCall.setMute(bl);
    }

    @VisibleForTesting
    public void setPhoneNumberUtilsProxy(PhoneNumberUtilsProxy phoneNumberUtilsProxy) {
        this.mPhoneNumberUtilsProxy = phoneNumberUtilsProxy;
    }

    @VisibleForTesting
    public void setRetryTimeout(ImsManager.Connector.RetryTimeout retryTimeout) {
        this.mImsManagerConnector.mRetryTimeout = retryTimeout;
    }

    @VisibleForTesting
    public void setSharedPreferenceProxy(SharedPreferenceProxy sharedPreferenceProxy) {
        this.mSharedPreferenceProxy = sharedPreferenceProxy;
    }

    public void setTtyMode(int n) {
        ImsManager imsManager = this.mImsManager;
        if (imsManager == null) {
            Log.w((String)LOG_TAG, (String)"ImsManager is null when setting TTY mode");
            return;
        }
        try {
            imsManager.setTtyMode(n);
        }
        catch (ImsException imsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setTtyMode : ");
            stringBuilder.append((Object)imsException);
            this.loge(stringBuilder.toString());
            this.retryGetImsService();
        }
    }

    public void setUiTTYMode(int n, Message message) {
        ImsManager imsManager = this.mImsManager;
        if (imsManager == null) {
            this.mPhone.sendErrorResponse(message, this.getImsManagerIsNullException());
            return;
        }
        try {
            imsManager.setUiTTYMode(this.mPhone.getContext(), n, message);
        }
        catch (ImsException imsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setUITTYMode : ");
            stringBuilder.append((Object)imsException);
            this.loge(stringBuilder.toString());
            this.mPhone.sendErrorResponse(message, imsException);
            this.retryGetImsService();
        }
    }

    public void startDtmf(char c) {
        this.log("startDtmf");
        ImsCall imsCall = this.mForegroundCall.getImsCall();
        if (imsCall != null) {
            imsCall.startDtmf(c);
        } else {
            this.loge("startDtmf : no foreground call");
        }
    }

    public void stopDtmf() {
        this.log("stopDtmf");
        ImsCall imsCall = this.mForegroundCall.getImsCall();
        if (imsCall != null) {
            imsCall.stopDtmf();
        } else {
            this.loge("stopDtmf : no foreground call");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void unholdHeldCall() throws CallStateException {
        try {
            ImsCall imsCall = this.mBackgroundCall.getImsCall();
            if (this.mHoldSwitchingState != HoldSwapState.PENDING_SINGLE_CALL_UNHOLD && this.mHoldSwitchingState != HoldSwapState.SWAPPING_ACTIVE_AND_HELD) {
                if (imsCall != null) {
                    this.mCallExpectedToResume = imsCall;
                    this.mHoldSwitchingState = HoldSwapState.PENDING_SINGLE_CALL_UNHOLD;
                    this.mForegroundCall.switchWith(this.mBackgroundCall);
                    this.logHoldSwapState("unholdCurrentCall");
                    imsCall.resume();
                    this.mMetrics.writeOnImsCommand(this.mPhone.getPhoneId(), imsCall.getSession(), 6);
                }
                return;
            }
            this.logi("Ignoring unhold request while already unholding or swapping");
            return;
        }
        catch (ImsException imsException) {
            throw new CallStateException(imsException.getMessage());
        }
    }

    @Override
    public void unregisterForVoiceCallEnded(Handler handler) {
        this.mVoiceCallEndedRegistrants.remove(handler);
    }

    @Override
    public void unregisterForVoiceCallStarted(Handler handler) {
        this.mVoiceCallStartedRegistrants.remove(handler);
    }

    public void unregisterPhoneStateListener(PhoneStateListener phoneStateListener) {
        this.mPhoneStateListeners.remove(phoneStateListener);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void updateCarrierConfigCache(PersistableBundle object) {
        this.mAllowEmergencyVideoCalls = object.getBoolean("allow_emergency_video_calls_bool");
        this.mTreatDowngradedVideoCallsAsVideoCalls = object.getBoolean("treat_downgraded_video_calls_as_video_calls_bool");
        this.mDropVideoCallWhenAnsweringAudioCall = object.getBoolean("drop_video_call_when_answering_audio_call_bool");
        this.mAllowAddCallDuringVideoCall = object.getBoolean("allow_add_call_during_video_call");
        this.mNotifyVtHandoverToWifiFail = object.getBoolean("notify_vt_handover_to_wifi_failure_bool");
        this.mSupportDowngradeVtToAudio = object.getBoolean("support_downgrade_vt_to_audio_bool");
        this.mNotifyHandoverVideoFromWifiToLTE = object.getBoolean("notify_handover_video_from_wifi_to_lte_bool");
        this.mNotifyHandoverVideoFromLTEToWifi = object.getBoolean("notify_handover_video_from_lte_to_wifi_bool");
        this.mIgnoreDataEnabledChangedForVideoCalls = object.getBoolean("ignore_data_enabled_changed_for_video_calls");
        this.mIsViLteDataMetered = object.getBoolean("vilte_data_is_metered_bool");
        this.mSupportPauseVideo = object.getBoolean("support_pause_ims_video_calls_bool");
        this.mAlwaysPlayRemoteHoldTone = object.getBoolean("always_play_remote_hold_tone_bool");
        this.mAutoRetryFailedWifiEmergencyCall = object.getBoolean("auto_retry_failed_wifi_emergency_call");
        String[] arrstring = object.getStringArray("ims_reasoninfo_mapping_string_array");
        if (arrstring == null || arrstring.length <= 0) {
            this.log("No carrier ImsReasonInfo mappings defined.");
            return;
        } else {
            for (String string : arrstring) {
                String[] arrstring2 = string.split(Pattern.quote("|"));
                if (arrstring2.length != 3) continue;
                try {
                    object = arrstring2[0].equals("*") ? null : Integer.valueOf(Integer.parseInt(arrstring2[0]));
                    CharSequence charSequence = arrstring2[1];
                    String string2 = charSequence;
                    if (charSequence == null) {
                        string2 = "";
                    }
                    int n = Integer.parseInt(arrstring2[2]);
                    this.addReasonCodeRemapping((Integer)object, string2, n);
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Loaded ImsReasonInfo mapping : fromCode = ");
                    ((StringBuilder)charSequence).append(object);
                    if (((StringBuilder)charSequence).toString() == null) {
                        object = "any";
                    } else {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(object);
                        ((StringBuilder)charSequence).append(" ; message = ");
                        ((StringBuilder)charSequence).append(string2);
                        ((StringBuilder)charSequence).append(" ; toCode = ");
                        ((StringBuilder)charSequence).append(n);
                        object = ((StringBuilder)charSequence).toString();
                    }
                    this.log((String)object);
                }
                catch (NumberFormatException numberFormatException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid ImsReasonInfo mapping found: ");
                    ((StringBuilder)object).append(string);
                    this.loge(((StringBuilder)object).toString());
                }
            }
        }
    }

    private static class CacheEntry {
        private long mCachedTime;
        private int mCallDirection;
        private long mConnectElapsedTime;
        private long mConnectTime;

        CacheEntry(long l, long l2, long l3, int n) {
            this.mCachedTime = l;
            this.mConnectTime = l2;
            this.mConnectElapsedTime = l3;
            this.mCallDirection = n;
        }
    }

    private static enum HoldSwapState {
        INACTIVE,
        PENDING_SINGLE_CALL_HOLD,
        PENDING_SINGLE_CALL_UNHOLD,
        SWAPPING_ACTIVE_AND_HELD,
        HOLDING_TO_ANSWER_INCOMING,
        PENDING_RESUME_FOREGROUND_AFTER_FAILURE,
        HOLDING_TO_DIAL_OUTGOING;
        
    }

    private class MmTelFeatureListener
    extends MmTelFeature.Listener {
        private MmTelFeatureListener() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onIncomingCall(IImsCallSession object, Bundle object2) {
            ImsPhoneCallTracker.this.log("onReceive : incoming call intent");
            if (ImsPhoneCallTracker.this.mImsManager == null) {
                return;
            }
            try {
                if (object2.getBoolean("android:ussd", false)) {
                    ImsPhoneCallTracker.this.log("onReceive : USSD");
                    ImsPhoneCallTracker.this.mUssdSession = ImsPhoneCallTracker.this.mImsManager.takeCall((IImsCallSession)object, (Bundle)object2, ImsPhoneCallTracker.this.mImsUssdListener);
                    if (ImsPhoneCallTracker.this.mUssdSession == null) return;
                    ImsPhoneCallTracker.this.mUssdSession.accept(2);
                    return;
                }
                boolean bl = object2.getBoolean("android:isUnknown", false);
                ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onReceive : isUnknown = ");
                stringBuilder.append(bl);
                stringBuilder.append(" fg = ");
                stringBuilder.append((Object)ImsPhoneCallTracker.this.mForegroundCall.getState());
                stringBuilder.append(" bg = ");
                stringBuilder.append((Object)ImsPhoneCallTracker.this.mBackgroundCall.getState());
                imsPhoneCallTracker.log(stringBuilder.toString());
                stringBuilder = ImsPhoneCallTracker.this.mImsManager.takeCall((IImsCallSession)object, (Bundle)object2, ImsPhoneCallTracker.this.mImsCallListener);
                ImsPhone imsPhone = ImsPhoneCallTracker.this.mPhone;
                imsPhoneCallTracker = ImsPhoneCallTracker.this;
                object = bl ? ImsPhoneCallTracker.this.mForegroundCall : ImsPhoneCallTracker.this.mRingingCall;
                object2 = new ImsPhoneConnection((Phone)imsPhone, (ImsCall)stringBuilder, imsPhoneCallTracker, (ImsPhoneCall)object, bl);
                if (ImsPhoneCallTracker.this.mForegroundCall.hasConnections() && (object = ImsPhoneCallTracker.this.mForegroundCall.getFirstConnection().getImsCall()) != null && stringBuilder != null) {
                    ((Connection)object2).setActiveCallDisconnectedOnAnswer(ImsPhoneCallTracker.this.shouldDisconnectActiveCallOnAnswer((ImsCall)object, (ImsCall)stringBuilder));
                }
                ((Connection)object2).setAllowAddCallDuringVideoCall(ImsPhoneCallTracker.this.mAllowAddCallDuringVideoCall);
                ImsPhoneCallTracker.this.addConnection((ImsPhoneConnection)object2);
                ImsPhoneCallTracker.this.setVideoCallProvider((ImsPhoneConnection)object2, (ImsCall)stringBuilder);
                TelephonyMetrics.getInstance().writeOnImsCallReceive(ImsPhoneCallTracker.this.mPhone.getPhoneId(), stringBuilder.getSession());
                if (bl) {
                    ImsPhoneCallTracker.this.mPhone.notifyUnknownConnection((Connection)object2);
                } else {
                    if (ImsPhoneCallTracker.this.mForegroundCall.getState() != Call.State.IDLE || ImsPhoneCallTracker.this.mBackgroundCall.getState() != Call.State.IDLE) {
                        ((ImsPhoneConnection)object2).update((ImsCall)stringBuilder, Call.State.WAITING);
                    }
                    ImsPhoneCallTracker.this.mPhone.notifyNewRingingConnection((Connection)object2);
                    ImsPhoneCallTracker.this.mPhone.notifyIncomingRing();
                }
                ImsPhoneCallTracker.this.updatePhoneState();
                ImsPhoneCallTracker.this.mPhone.notifyPreciseCallStateChanged();
                return;
            }
            catch (RemoteException remoteException) {
                return;
            }
            catch (ImsException imsException) {
                object = ImsPhoneCallTracker.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("onReceive : exception ");
                ((StringBuilder)object2).append((Object)imsException);
                ((ImsPhoneCallTracker)object).loge(((StringBuilder)object2).toString());
            }
        }

        public void onVoiceMessageCountUpdate(int n) {
            if (ImsPhoneCallTracker.this.mPhone != null && ImsPhoneCallTracker.this.mPhone.mDefaultPhone != null) {
                ImsPhoneCallTracker imsPhoneCallTracker = ImsPhoneCallTracker.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onVoiceMessageCountChanged :: count=");
                stringBuilder.append(n);
                imsPhoneCallTracker.log(stringBuilder.toString());
                ImsPhoneCallTracker.this.mPhone.mDefaultPhone.setVoiceMessageCount(n);
            } else {
                ImsPhoneCallTracker.this.loge("onVoiceMessageCountUpdate: null phone");
            }
        }
    }

    public static interface PhoneNumberUtilsProxy {
        public boolean isEmergencyNumber(String var1);
    }

    public static interface PhoneStateListener {
        public void onPhoneStateChanged(PhoneConstants.State var1, PhoneConstants.State var2);
    }

    public static interface SharedPreferenceProxy {
        public SharedPreferences getDefaultSharedPreferences(Context var1);
    }

}

