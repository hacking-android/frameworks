/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomAnalytics;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telecom.ITelecomService;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TelecomManager {
    public static final String ACTION_CHANGE_DEFAULT_DIALER = "android.telecom.action.CHANGE_DEFAULT_DIALER";
    public static final String ACTION_CHANGE_PHONE_ACCOUNTS = "android.telecom.action.CHANGE_PHONE_ACCOUNTS";
    public static final String ACTION_CONFIGURE_PHONE_ACCOUNT = "android.telecom.action.CONFIGURE_PHONE_ACCOUNT";
    public static final String ACTION_CURRENT_TTY_MODE_CHANGED = "android.telecom.action.CURRENT_TTY_MODE_CHANGED";
    public static final String ACTION_DEFAULT_CALL_SCREENING_APP_CHANGED = "android.telecom.action.DEFAULT_CALL_SCREENING_APP_CHANGED";
    public static final String ACTION_DEFAULT_DIALER_CHANGED = "android.telecom.action.DEFAULT_DIALER_CHANGED";
    public static final String ACTION_INCOMING_CALL = "android.telecom.action.INCOMING_CALL";
    public static final String ACTION_NEW_UNKNOWN_CALL = "android.telecom.action.NEW_UNKNOWN_CALL";
    public static final String ACTION_PHONE_ACCOUNT_REGISTERED = "android.telecom.action.PHONE_ACCOUNT_REGISTERED";
    public static final String ACTION_PHONE_ACCOUNT_UNREGISTERED = "android.telecom.action.PHONE_ACCOUNT_UNREGISTERED";
    public static final String ACTION_SHOW_CALL_ACCESSIBILITY_SETTINGS = "android.telecom.action.SHOW_CALL_ACCESSIBILITY_SETTINGS";
    public static final String ACTION_SHOW_CALL_SETTINGS = "android.telecom.action.SHOW_CALL_SETTINGS";
    public static final String ACTION_SHOW_MISSED_CALLS_NOTIFICATION = "android.telecom.action.SHOW_MISSED_CALLS_NOTIFICATION";
    public static final String ACTION_SHOW_RESPOND_VIA_SMS_SETTINGS = "android.telecom.action.SHOW_RESPOND_VIA_SMS_SETTINGS";
    public static final String ACTION_TTY_PREFERRED_MODE_CHANGED = "android.telecom.action.TTY_PREFERRED_MODE_CHANGED";
    public static final char DTMF_CHARACTER_PAUSE = ',';
    public static final char DTMF_CHARACTER_WAIT = ';';
    public static final ComponentName EMERGENCY_DIALER_COMPONENT = ComponentName.createRelative("com.android.phone", ".EmergencyDialer");
    public static final String EXTRA_CALL_AUDIO_STATE = "android.telecom.extra.CALL_AUDIO_STATE";
    @SystemApi
    public static final String EXTRA_CALL_BACK_INTENT = "android.telecom.extra.CALL_BACK_INTENT";
    public static final String EXTRA_CALL_BACK_NUMBER = "android.telecom.extra.CALL_BACK_NUMBER";
    public static final String EXTRA_CALL_CREATED_TIME_MILLIS = "android.telecom.extra.CALL_CREATED_TIME_MILLIS";
    public static final String EXTRA_CALL_DISCONNECT_CAUSE = "android.telecom.extra.CALL_DISCONNECT_CAUSE";
    public static final String EXTRA_CALL_DISCONNECT_MESSAGE = "android.telecom.extra.CALL_DISCONNECT_MESSAGE";
    public static final String EXTRA_CALL_EXTERNAL_RINGER = "android.telecom.extra.CALL_EXTERNAL_RINGER";
    public static final String EXTRA_CALL_NETWORK_TYPE = "android.telecom.extra.CALL_NETWORK_TYPE";
    public static final String EXTRA_CALL_SOURCE = "android.telecom.extra.CALL_SOURCE";
    public static final String EXTRA_CALL_SUBJECT = "android.telecom.extra.CALL_SUBJECT";
    public static final String EXTRA_CALL_TECHNOLOGY_TYPE = "android.telecom.extra.CALL_TECHNOLOGY_TYPE";
    public static final String EXTRA_CALL_TELECOM_ROUTING_END_TIME_MILLIS = "android.telecom.extra.CALL_TELECOM_ROUTING_END_TIME_MILLIS";
    public static final String EXTRA_CALL_TELECOM_ROUTING_START_TIME_MILLIS = "android.telecom.extra.CALL_TELECOM_ROUTING_START_TIME_MILLIS";
    public static final String EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME = "android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME";
    @SystemApi
    public static final String EXTRA_CLEAR_MISSED_CALLS_INTENT = "android.telecom.extra.CLEAR_MISSED_CALLS_INTENT";
    @SystemApi
    public static final String EXTRA_CONNECTION_SERVICE = "android.telecom.extra.CONNECTION_SERVICE";
    public static final String EXTRA_CURRENT_TTY_MODE = "android.telecom.intent.extra.CURRENT_TTY_MODE";
    public static final String EXTRA_DEFAULT_CALL_SCREENING_APP_COMPONENT_NAME = "android.telecom.extra.DEFAULT_CALL_SCREENING_APP_COMPONENT_NAME";
    public static final String EXTRA_HANDOVER_FROM_PHONE_ACCOUNT = "android.telecom.extra.HANDOVER_FROM_PHONE_ACCOUNT";
    public static final String EXTRA_INCOMING_CALL_ADDRESS = "android.telecom.extra.INCOMING_CALL_ADDRESS";
    public static final String EXTRA_INCOMING_CALL_EXTRAS = "android.telecom.extra.INCOMING_CALL_EXTRAS";
    public static final String EXTRA_INCOMING_VIDEO_STATE = "android.telecom.extra.INCOMING_VIDEO_STATE";
    public static final String EXTRA_IS_DEFAULT_CALL_SCREENING_APP = "android.telecom.extra.IS_DEFAULT_CALL_SCREENING_APP";
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=119305590L)
    public static final String EXTRA_IS_HANDOVER = "android.telecom.extra.IS_HANDOVER";
    public static final String EXTRA_IS_HANDOVER_CONNECTION = "android.telecom.extra.IS_HANDOVER_CONNECTION";
    @SystemApi
    public static final String EXTRA_IS_USER_INTENT_EMERGENCY_CALL = "android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL";
    public static final String EXTRA_NEW_OUTGOING_CALL_CANCEL_TIMEOUT = "android.telecom.extra.NEW_OUTGOING_CALL_CANCEL_TIMEOUT";
    public static final String EXTRA_NOTIFICATION_COUNT = "android.telecom.extra.NOTIFICATION_COUNT";
    public static final String EXTRA_NOTIFICATION_PHONE_NUMBER = "android.telecom.extra.NOTIFICATION_PHONE_NUMBER";
    public static final String EXTRA_OUTGOING_CALL_EXTRAS = "android.telecom.extra.OUTGOING_CALL_EXTRAS";
    public static final String EXTRA_PHONE_ACCOUNT_HANDLE = "android.telecom.extra.PHONE_ACCOUNT_HANDLE";
    public static final String EXTRA_START_CALL_WITH_RTT = "android.telecom.extra.START_CALL_WITH_RTT";
    public static final String EXTRA_START_CALL_WITH_SPEAKERPHONE = "android.telecom.extra.START_CALL_WITH_SPEAKERPHONE";
    public static final String EXTRA_START_CALL_WITH_VIDEO_STATE = "android.telecom.extra.START_CALL_WITH_VIDEO_STATE";
    public static final String EXTRA_TTY_PREFERRED_MODE = "android.telecom.intent.extra.TTY_PREFERRED";
    public static final String EXTRA_UNKNOWN_CALL_HANDLE = "android.telecom.extra.UNKNOWN_CALL_HANDLE";
    public static final String EXTRA_USE_ASSISTED_DIALING = "android.telecom.extra.USE_ASSISTED_DIALING";
    public static final String GATEWAY_ORIGINAL_ADDRESS = "android.telecom.extra.GATEWAY_ORIGINAL_ADDRESS";
    public static final String GATEWAY_PROVIDER_PACKAGE = "android.telecom.extra.GATEWAY_PROVIDER_PACKAGE";
    public static final String METADATA_INCLUDE_EXTERNAL_CALLS = "android.telecom.INCLUDE_EXTERNAL_CALLS";
    public static final String METADATA_INCLUDE_SELF_MANAGED_CALLS = "android.telecom.INCLUDE_SELF_MANAGED_CALLS";
    public static final String METADATA_IN_CALL_SERVICE_CAR_MODE_UI = "android.telecom.IN_CALL_SERVICE_CAR_MODE_UI";
    public static final String METADATA_IN_CALL_SERVICE_RINGING = "android.telecom.IN_CALL_SERVICE_RINGING";
    public static final String METADATA_IN_CALL_SERVICE_UI = "android.telecom.IN_CALL_SERVICE_UI";
    public static final int PRESENTATION_ALLOWED = 1;
    public static final int PRESENTATION_PAYPHONE = 4;
    public static final int PRESENTATION_RESTRICTED = 2;
    public static final int PRESENTATION_UNKNOWN = 3;
    private static final String TAG = "TelecomManager";
    @SystemApi
    public static final int TTY_MODE_FULL = 1;
    @SystemApi
    public static final int TTY_MODE_HCO = 2;
    @SystemApi
    public static final int TTY_MODE_OFF = 0;
    @SystemApi
    public static final int TTY_MODE_VCO = 3;
    private final Context mContext;
    private final ITelecomService mTelecomServiceOverride;

    public TelecomManager(Context context) {
        this(context, null);
    }

    public TelecomManager(Context context, ITelecomService iTelecomService) {
        Context context2 = context.getApplicationContext();
        this.mContext = context2 != null ? context2 : context;
        this.mTelecomServiceOverride = iTelecomService;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public static TelecomManager from(Context context) {
        return (TelecomManager)context.getSystemService("telecom");
    }

    private ITelecomService getTelecomService() {
        ITelecomService iTelecomService = this.mTelecomServiceOverride;
        if (iTelecomService != null) {
            return iTelecomService;
        }
        return ITelecomService.Stub.asInterface(ServiceManager.getService("telecom"));
    }

    private boolean isServiceConnected() {
        boolean bl = this.getTelecomService() != null;
        if (!bl) {
            Log.w(TAG, "Telecom Service not found.");
        }
        return bl;
    }

    public void acceptHandover(Uri uri, int n, PhoneAccountHandle object) {
        try {
            if (this.isServiceConnected()) {
                this.getTelecomService().acceptHandover(uri, n, (PhoneAccountHandle)object);
            }
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("RemoteException acceptHandover: ");
            ((StringBuilder)object).append(remoteException);
            Log.e(TAG, ((StringBuilder)object).toString());
        }
    }

    @Deprecated
    public void acceptRingingCall() {
        try {
            if (this.isServiceConnected()) {
                this.getTelecomService().acceptRingingCall(this.mContext.getPackageName());
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#acceptRingingCall", remoteException);
        }
    }

    @Deprecated
    public void acceptRingingCall(int n) {
        try {
            if (this.isServiceConnected()) {
                this.getTelecomService().acceptRingingCallWithVideoState(this.mContext.getPackageName(), n);
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#acceptRingingCallWithVideoState", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addNewIncomingCall(PhoneAccountHandle phoneAccountHandle, Bundle object) {
        try {
            if (!this.isServiceConnected()) return;
            if (object != null && ((BaseBundle)object).getBoolean(EXTRA_IS_HANDOVER) && this.mContext.getApplicationContext().getApplicationInfo().targetSdkVersion > 27) {
                Log.e("TAG", "addNewIncomingCall failed. Use public api acceptHandover for API > O-MR1");
                return;
            }
            ITelecomService iTelecomService = this.getTelecomService();
            if (object == null) {
                object = new Bundle();
            }
            iTelecomService.addNewIncomingCall(phoneAccountHandle, (Bundle)object);
            return;
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("RemoteException adding a new incoming call: ");
            ((StringBuilder)object).append(phoneAccountHandle);
            Log.e(TAG, ((StringBuilder)object).toString(), remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public void addNewUnknownCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) {
        try {
            if (!this.isServiceConnected()) return;
            ITelecomService iTelecomService = this.getTelecomService();
            if (bundle == null) {
                bundle = new Bundle();
            }
            iTelecomService.addNewUnknownCall(phoneAccountHandle, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RemoteException adding a new unknown call: ");
            stringBuilder.append(phoneAccountHandle);
            Log.e(TAG, stringBuilder.toString(), remoteException);
        }
    }

    public void cancelMissedCallsNotification() {
        ITelecomService iTelecomService = this.getTelecomService();
        if (iTelecomService != null) {
            try {
                iTelecomService.cancelMissedCallsNotification(this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelecomService#cancelMissedCallsNotification", remoteException);
            }
        }
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public void clearAccounts() {
        try {
            if (this.isServiceConnected()) {
                this.getTelecomService().clearAccounts(this.mContext.getPackageName());
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#clearAccounts", remoteException);
        }
    }

    public void clearAccountsForPackage(String string2) {
        try {
            if (this.isServiceConnected() && !TextUtils.isEmpty(string2)) {
                this.getTelecomService().clearAccounts(string2);
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#clearAccountsForPackage", remoteException);
        }
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public void clearPhoneAccounts() {
        this.clearAccounts();
    }

    public Intent createManageBlockedNumbersIntent() {
        Intent intent;
        ITelecomService iTelecomService = this.getTelecomService();
        Intent intent2 = intent = null;
        if (iTelecomService != null) {
            try {
                intent2 = iTelecomService.createManageBlockedNumbersIntent();
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelecomService#createManageBlockedNumbersIntent", remoteException);
                intent2 = intent;
            }
        }
        return intent2;
    }

    @SystemApi
    public TelecomAnalytics dumpAnalytics() {
        TelecomAnalytics telecomAnalytics;
        ITelecomService iTelecomService = this.getTelecomService();
        TelecomAnalytics telecomAnalytics2 = telecomAnalytics = null;
        if (iTelecomService != null) {
            try {
                telecomAnalytics2 = iTelecomService.dumpCallAnalytics();
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error dumping call analytics", remoteException);
                telecomAnalytics2 = telecomAnalytics;
            }
        }
        return telecomAnalytics2;
    }

    @SystemApi
    public void enablePhoneAccount(PhoneAccountHandle phoneAccountHandle, boolean bl) {
        ITelecomService iTelecomService = this.getTelecomService();
        if (iTelecomService != null) {
            try {
                iTelecomService.enablePhoneAccount(phoneAccountHandle, bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error enablePhoneAbbount", remoteException);
            }
        }
    }

    @Deprecated
    public boolean endCall() {
        try {
            if (this.isServiceConnected()) {
                boolean bl = this.getTelecomService().endCall(this.mContext.getPackageName());
                return bl;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#endCall", remoteException);
        }
        return false;
    }

    public Uri getAdnUriForPhoneAccount(PhoneAccountHandle parcelable) {
        ITelecomService iTelecomService = this.getTelecomService();
        if (iTelecomService != null && parcelable != null) {
            try {
                parcelable = iTelecomService.getAdnUriForPhoneAccount((PhoneAccountHandle)parcelable, this.mContext.getOpPackageName());
                return parcelable;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelecomService#getAdnUriForPhoneAccount", remoteException);
            }
        }
        return Uri.parse("content://icc/adn");
    }

    @SystemApi
    public List<PhoneAccountHandle> getAllPhoneAccountHandles() {
        try {
            if (this.isServiceConnected()) {
                List<PhoneAccountHandle> list = this.getTelecomService().getAllPhoneAccountHandles();
                return list;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getAllPhoneAccountHandles", remoteException);
        }
        return Collections.EMPTY_LIST;
    }

    @SystemApi
    public List<PhoneAccount> getAllPhoneAccounts() {
        try {
            if (this.isServiceConnected()) {
                List<PhoneAccount> list = this.getTelecomService().getAllPhoneAccounts();
                return list;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getAllPhoneAccounts", remoteException);
        }
        return Collections.EMPTY_LIST;
    }

    @SystemApi
    public int getAllPhoneAccountsCount() {
        try {
            if (this.isServiceConnected()) {
                int n = this.getTelecomService().getAllPhoneAccountsCount();
                return n;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getAllPhoneAccountsCount", remoteException);
        }
        return 0;
    }

    public List<PhoneAccountHandle> getCallCapablePhoneAccounts() {
        return this.getCallCapablePhoneAccounts(false);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=119305590L)
    public List<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean bl) {
        try {
            if (this.isServiceConnected()) {
                List<PhoneAccountHandle> list = this.getTelecomService().getCallCapablePhoneAccounts(bl, this.mContext.getOpPackageName());
                return list;
            }
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error calling ITelecomService#getCallCapablePhoneAccounts(");
            stringBuilder.append(bl);
            stringBuilder.append(")");
            Log.e(TAG, stringBuilder.toString(), remoteException);
        }
        return new ArrayList<PhoneAccountHandle>();
    }

    @SystemApi
    public int getCallState() {
        try {
            if (this.isServiceConnected()) {
                int n = this.getTelecomService().getCallState();
                return n;
            }
        }
        catch (RemoteException remoteException) {
            Log.d(TAG, "RemoteException calling getCallState().", remoteException);
        }
        return 0;
    }

    @SystemApi
    public PhoneAccountHandle getConnectionManager() {
        return this.getSimCallManager();
    }

    @SystemApi
    public int getCurrentTtyMode() {
        try {
            if (this.isServiceConnected()) {
                int n = this.getTelecomService().getCurrentTtyMode(this.mContext.getOpPackageName());
                return n;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException attempting to get the current TTY mode.", remoteException);
        }
        return 0;
    }

    public String getDefaultDialerPackage() {
        try {
            if (this.isServiceConnected()) {
                String string2 = this.getTelecomService().getDefaultDialerPackage();
                return string2;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException attempting to get the default dialer package name.", remoteException);
        }
        return null;
    }

    public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String object) {
        try {
            if (this.isServiceConnected()) {
                object = this.getTelecomService().getDefaultOutgoingPhoneAccount((String)object, this.mContext.getOpPackageName());
                return object;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getDefaultOutgoingPhoneAccount", remoteException);
        }
        return null;
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public ComponentName getDefaultPhoneApp() {
        try {
            if (this.isServiceConnected()) {
                ComponentName componentName = this.getTelecomService().getDefaultPhoneApp();
                return componentName;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException attempting to get the default phone app.", remoteException);
        }
        return null;
    }

    public String getLine1Number(PhoneAccountHandle object) {
        try {
            if (this.isServiceConnected()) {
                object = this.getTelecomService().getLine1Number((PhoneAccountHandle)object, this.mContext.getOpPackageName());
                return object;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException calling ITelecomService#getLine1Number.", remoteException);
        }
        return null;
    }

    public PhoneAccount getPhoneAccount(PhoneAccountHandle parcelable) {
        try {
            if (this.isServiceConnected()) {
                parcelable = this.getTelecomService().getPhoneAccount((PhoneAccountHandle)parcelable);
                return parcelable;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getPhoneAccount", remoteException);
        }
        return null;
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public List<PhoneAccountHandle> getPhoneAccountsForPackage() {
        try {
            if (this.isServiceConnected()) {
                List<PhoneAccountHandle> list = this.getTelecomService().getPhoneAccountsForPackage(this.mContext.getPackageName());
                return list;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getPhoneAccountsForPackage", remoteException);
        }
        return null;
    }

    @SystemApi
    public List<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String object) {
        try {
            if (this.isServiceConnected()) {
                object = this.getTelecomService().getPhoneAccountsSupportingScheme((String)object, this.mContext.getOpPackageName());
                return object;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getPhoneAccountsSupportingScheme", remoteException);
        }
        return new ArrayList<PhoneAccountHandle>();
    }

    public List<PhoneAccountHandle> getSelfManagedPhoneAccounts() {
        try {
            if (this.isServiceConnected()) {
                List<PhoneAccountHandle> list = this.getTelecomService().getSelfManagedPhoneAccounts(this.mContext.getOpPackageName());
                return list;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getSelfManagedPhoneAccounts()", remoteException);
        }
        return new ArrayList<PhoneAccountHandle>();
    }

    public PhoneAccountHandle getSimCallManager() {
        try {
            if (this.isServiceConnected()) {
                PhoneAccountHandle phoneAccountHandle = this.getTelecomService().getSimCallManager(SubscriptionManager.getDefaultSubscriptionId());
                return phoneAccountHandle;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getSimCallManager");
        }
        return null;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=119305590L)
    public PhoneAccountHandle getSimCallManager(int n) {
        try {
            if (this.isServiceConnected()) {
                PhoneAccountHandle phoneAccountHandle = this.getTelecomService().getSimCallManagerForUser(n);
                return phoneAccountHandle;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getSimCallManagerForUser");
        }
        return null;
    }

    public PhoneAccountHandle getSimCallManagerForSubscription(int n) {
        try {
            if (this.isServiceConnected()) {
                PhoneAccountHandle phoneAccountHandle = this.getTelecomService().getSimCallManager(n);
                return phoneAccountHandle;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getSimCallManager");
        }
        return null;
    }

    public String getSystemDialerPackage() {
        try {
            if (this.isServiceConnected()) {
                String string2 = this.getTelecomService().getSystemDialerPackage();
                return string2;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException attempting to get the system dialer package name.", remoteException);
        }
        return null;
    }

    public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount() {
        try {
            if (this.isServiceConnected()) {
                PhoneAccountHandle phoneAccountHandle = this.getTelecomService().getUserSelectedOutgoingPhoneAccount(this.mContext.getOpPackageName());
                return phoneAccountHandle;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#getUserSelectedOutgoingPhoneAccount", remoteException);
        }
        return null;
    }

    public String getVoiceMailNumber(PhoneAccountHandle object) {
        try {
            if (this.isServiceConnected()) {
                object = this.getTelecomService().getVoiceMailNumber((PhoneAccountHandle)object, this.mContext.getOpPackageName());
                return object;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException calling ITelecomService#hasVoiceMailNumber.", remoteException);
        }
        return null;
    }

    public void handleCallIntent(Intent intent) {
        try {
            if (this.isServiceConnected()) {
                this.getTelecomService().handleCallIntent(intent);
            }
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RemoteException handleCallIntent: ");
            stringBuilder.append(remoteException);
            Log.e(TAG, stringBuilder.toString());
        }
    }

    public boolean handleMmi(String string2) {
        ITelecomService iTelecomService = this.getTelecomService();
        if (iTelecomService != null) {
            try {
                boolean bl = iTelecomService.handlePinMmi(string2, this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelecomService#handlePinMmi", remoteException);
            }
        }
        return false;
    }

    public boolean handleMmi(String string2, PhoneAccountHandle phoneAccountHandle) {
        ITelecomService iTelecomService = this.getTelecomService();
        if (iTelecomService != null) {
            try {
                boolean bl = iTelecomService.handlePinMmiForPhoneAccount(phoneAccountHandle, string2, this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelecomService#handlePinMmi", remoteException);
            }
        }
        return false;
    }

    public boolean isInCall() {
        try {
            if (this.isServiceConnected()) {
                boolean bl = this.getTelecomService().isInCall(this.mContext.getOpPackageName());
                return bl;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException calling isInCall().", remoteException);
        }
        return false;
    }

    @SystemApi
    public boolean isInEmergencyCall() {
        try {
            if (this.isServiceConnected()) {
                boolean bl = this.getTelecomService().isInEmergencyCall();
                return bl;
            }
            return false;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RemoteException isInEmergencyCall: ");
            stringBuilder.append(remoteException);
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
    }

    public boolean isInManagedCall() {
        try {
            if (this.isServiceConnected()) {
                boolean bl = this.getTelecomService().isInManagedCall(this.mContext.getOpPackageName());
                return bl;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException calling isInManagedCall().", remoteException);
        }
        return false;
    }

    public boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle) {
        if (phoneAccountHandle == null) {
            return false;
        }
        ITelecomService iTelecomService = this.getTelecomService();
        if (iTelecomService != null) {
            try {
                boolean bl = iTelecomService.isIncomingCallPermitted(phoneAccountHandle);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error isIncomingCallPermitted", remoteException);
            }
        }
        return false;
    }

    public boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle) {
        ITelecomService iTelecomService = this.getTelecomService();
        if (iTelecomService != null) {
            try {
                boolean bl = iTelecomService.isOutgoingCallPermitted(phoneAccountHandle);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error isOutgoingCallPermitted", remoteException);
            }
        }
        return false;
    }

    @SystemApi
    public boolean isRinging() {
        try {
            if (this.isServiceConnected()) {
                boolean bl = this.getTelecomService().isRinging(this.mContext.getOpPackageName());
                return bl;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException attempting to get ringing state of phone app.", remoteException);
        }
        return false;
    }

    public boolean isTtySupported() {
        try {
            if (this.isServiceConnected()) {
                boolean bl = this.getTelecomService().isTtySupported(this.mContext.getOpPackageName());
                return bl;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException attempting to get TTY supported state.", remoteException);
        }
        return false;
    }

    public boolean isVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String string2) {
        try {
            if (this.isServiceConnected()) {
                boolean bl = this.getTelecomService().isVoiceMailNumber(phoneAccountHandle, string2, this.mContext.getOpPackageName());
                return bl;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException calling ITelecomService#isVoiceMailNumber.", remoteException);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void placeCall(Uri uri, Bundle bundle) {
        RemoteException remoteException2;
        block4 : {
            ITelecomService iTelecomService = this.getTelecomService();
            if (iTelecomService == null) return;
            if (uri == null) {
                Log.w(TAG, "Cannot place call to empty address.");
            }
            if (bundle == null) {
                try {
                    bundle = new Bundle();
                }
                catch (RemoteException remoteException2) {
                    break block4;
                }
            }
            iTelecomService.placeCall(uri, bundle, this.mContext.getOpPackageName());
            return;
        }
        Log.e(TAG, "Error calling ITelecomService#placeCall", remoteException2);
    }

    public void registerPhoneAccount(PhoneAccount phoneAccount) {
        try {
            if (this.isServiceConnected()) {
                this.getTelecomService().registerPhoneAccount(phoneAccount);
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#registerPhoneAccount", remoteException);
        }
    }

    @SystemApi
    @Deprecated
    public boolean setDefaultDialer(String string2) {
        try {
            if (this.isServiceConnected()) {
                boolean bl = this.getTelecomService().setDefaultDialer(string2);
                return bl;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException attempting to set the default dialer.", remoteException);
        }
        return false;
    }

    @SystemApi
    public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
        try {
            if (this.isServiceConnected()) {
                this.getTelecomService().setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#setUserSelectedOutgoingPhoneAccount");
        }
    }

    public void showInCallScreen(boolean bl) {
        ITelecomService iTelecomService = this.getTelecomService();
        if (iTelecomService != null) {
            try {
                iTelecomService.showInCallScreen(bl, this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error calling ITelecomService#showCallScreen", remoteException);
            }
        }
    }

    public void silenceRinger() {
        try {
            if (this.isServiceConnected()) {
                this.getTelecomService().silenceRinger(this.mContext.getOpPackageName());
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#silenceRinger", remoteException);
        }
    }

    public void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
        try {
            if (this.isServiceConnected()) {
                this.getTelecomService().unregisterPhoneAccount(phoneAccountHandle);
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling ITelecomService#unregisterPhoneAccount", remoteException);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TtyMode {
    }

}

