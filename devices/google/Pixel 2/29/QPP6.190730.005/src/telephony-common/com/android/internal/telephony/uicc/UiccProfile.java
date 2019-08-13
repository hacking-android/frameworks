/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.usage.UsageStatsManager
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.database.ContentObserver
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.telephony.UiccAccessRule
 *  android.text.TextUtils
 *  android.util.ArrayMap
 *  android.util.ArraySet
 *  android.util.LocalLog
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.annotations.VisibleForTesting$Visibility
 *  com.android.internal.telephony.IccCardConstants
 *  com.android.internal.telephony.IccCardConstants$State
 */
package com.android.internal.telephony.uicc;

import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.Registrant;
import android.os.RegistrantList;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccAccessRule;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LocalLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.telephony.MccTable;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.cat.CatService;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.InstallCarrierAppTrampolineActivity;
import com.android.internal.telephony.uicc.InstallCarrierAppUtils;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccCarrierPrivilegeRules;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.uicc.euicc.EuiccCard;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UiccProfile
extends IccCard {
    protected static final boolean DBG = true;
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public static final int EVENT_APP_READY = 3;
    private static final int EVENT_CARRIER_CONFIG_CHANGED = 14;
    private static final int EVENT_CARRIER_PRIVILEGES_LOADED = 13;
    private static final int EVENT_CLOSE_LOGICAL_CHANNEL_DONE = 9;
    private static final int EVENT_EID_READY = 6;
    private static final int EVENT_ICC_LOCKED = 2;
    private static final int EVENT_ICC_RECORD_EVENTS = 7;
    private static final int EVENT_NETWORK_LOCKED = 5;
    private static final int EVENT_OPEN_LOGICAL_CHANNEL_DONE = 8;
    private static final int EVENT_RADIO_OFF_OR_UNAVAILABLE = 1;
    private static final int EVENT_RECORDS_LOADED = 4;
    private static final int EVENT_SIM_IO_DONE = 12;
    private static final int EVENT_TRANSMIT_APDU_BASIC_CHANNEL_DONE = 11;
    private static final int EVENT_TRANSMIT_APDU_LOGICAL_CHANNEL_DONE = 10;
    protected static final String LOG_TAG = "UiccProfile";
    private static final String OPERATOR_BRAND_OVERRIDE_PREFIX = "operator_branding_";
    private static final boolean VDBG = false;
    private RegistrantList mCarrierPrivilegeRegistrants;
    private UiccCarrierPrivilegeRules mCarrierPrivilegeRules;
    private CatService mCatService;
    private int mCdmaSubscriptionAppIndex;
    private CommandsInterface mCi;
    private Context mContext;
    private int mCurrentAppType;
    private boolean mDisposed;
    private IccCardConstants.State mExternalState;
    private int mGsmUmtsSubscriptionAppIndex;
    @VisibleForTesting
    public final Handler mHandler;
    private IccRecords mIccRecords;
    private int mImsSubscriptionAppIndex;
    private final Object mLock;
    private RegistrantList mNetworkLockedRegistrants;
    private RegistrantList mOperatorBrandOverrideRegistrants;
    private final int mPhoneId;
    private final ContentObserver mProvisionCompleteContentObserver;
    private final BroadcastReceiver mReceiver;
    private TelephonyManager mTelephonyManager;
    private UiccCardApplication mUiccApplication;
    private UiccCardApplication[] mUiccApplications = new UiccCardApplication[8];
    private final UiccCard mUiccCard;
    private IccCardStatus.PinState mUniversalPinState;

    public UiccProfile(Context context, CommandsInterface commandsInterface, IccCardStatus iccCardStatus, int n, UiccCard object, Object object2) {
        boolean bl = false;
        this.mDisposed = false;
        this.mCarrierPrivilegeRegistrants = new RegistrantList();
        this.mOperatorBrandOverrideRegistrants = new RegistrantList();
        this.mNetworkLockedRegistrants = new RegistrantList();
        this.mCurrentAppType = 1;
        this.mUiccApplication = null;
        this.mIccRecords = null;
        this.mExternalState = IccCardConstants.State.UNKNOWN;
        this.mProvisionCompleteContentObserver = new ContentObserver(new Handler()){

            public void onChange(boolean bl) {
                UiccProfile.this.mContext.getContentResolver().unregisterContentObserver((ContentObserver)this);
                for (String string : UiccProfile.this.getUninstalledCarrierPackages()) {
                    InstallCarrierAppUtils.showNotification(UiccProfile.this.mContext, string);
                    InstallCarrierAppUtils.registerPackageInstallReceiver(UiccProfile.this.mContext);
                }
            }
        };
        this.mReceiver = new BroadcastReceiver(){

            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                    UiccProfile.this.mHandler.sendMessage(UiccProfile.this.mHandler.obtainMessage(14));
                }
            }
        };
        this.mHandler = new Handler(){

            public void handleMessage(Message message) {
                if (UiccProfile.this.mDisposed && message.what != 8 && message.what != 9 && message.what != 10 && message.what != 11 && message.what != 12) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("handleMessage: Received ");
                    stringBuilder.append(message.what);
                    stringBuilder.append(" after dispose(); ignoring the message");
                    UiccProfile.loge(stringBuilder.toString());
                    return;
                }
                Object object = UiccProfile.this;
                Object object2 = new StringBuilder();
                ((StringBuilder)object2).append("handleMessage: Received ");
                ((StringBuilder)object2).append(message.what);
                ((StringBuilder)object2).append(" for phoneId ");
                ((StringBuilder)object2).append(UiccProfile.this.mPhoneId);
                ((UiccProfile)object).loglocal(((StringBuilder)object2).toString());
                switch (message.what) {
                    default: {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("handleMessage: Unhandled message with number: ");
                        ((StringBuilder)object2).append(message.what);
                        UiccProfile.loge(((StringBuilder)object2).toString());
                        break;
                    }
                    case 14: {
                        UiccProfile.this.handleCarrierNameOverride();
                        UiccProfile.this.handleSimCountryIsoOverride();
                        break;
                    }
                    case 13: {
                        UiccProfile.this.onCarrierPrivilegesLoadedMessage();
                        UiccProfile.this.updateExternalState();
                        break;
                    }
                    case 8: 
                    case 9: 
                    case 10: 
                    case 11: 
                    case 12: {
                        message = (AsyncResult)message.obj;
                        if (message.exception != null) {
                            object2 = UiccProfile.this;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("handleMessage: Exception ");
                            ((StringBuilder)object).append(message.exception);
                            ((UiccProfile)object2).loglocal(((StringBuilder)object).toString());
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("handleMessage: Error in SIM access with exception");
                            ((StringBuilder)object2).append(message.exception);
                            UiccProfile.log(((StringBuilder)object2).toString());
                        }
                        AsyncResult.forMessage((Message)((Message)message.userObj), (Object)message.result, (Throwable)message.exception);
                        ((Message)message.userObj).sendToTarget();
                        break;
                    }
                    case 7: {
                        if (UiccProfile.this.mCurrentAppType != 1 || UiccProfile.this.mIccRecords == null || (Integer)((AsyncResult)message.obj).result != 2) break;
                        UiccProfile.this.mTelephonyManager.setSimOperatorNameForPhone(UiccProfile.this.mPhoneId, UiccProfile.this.mIccRecords.getServiceProviderName());
                        break;
                    }
                    case 5: {
                        UiccProfile.this.mNetworkLockedRegistrants.notifyRegistrants();
                    }
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 6: {
                        UiccProfile.this.updateExternalState();
                    }
                }
            }
        };
        UiccProfile.log("Creating profile");
        this.mLock = object2;
        this.mUiccCard = object;
        this.mPhoneId = n;
        object = PhoneFactory.getPhone(n);
        if (object != null) {
            if (((Phone)object).getPhoneType() == 1) {
                bl = true;
            }
            this.setCurrentAppType(bl);
        }
        if ((object = this.mUiccCard) instanceof EuiccCard) {
            ((EuiccCard)object).registerForEidReady(this.mHandler, 6, null);
        }
        this.update(context, commandsInterface, iccCardStatus);
        commandsInterface.registerForOffOrNotAvailable(this.mHandler, 1, null);
        this.resetProperties();
        commandsInterface = new IntentFilter();
        commandsInterface.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        context.registerReceiver(this.mReceiver, (IntentFilter)commandsInterface);
    }

    private boolean areAllApplicationsReady() {
        UiccCardApplication[] arruiccCardApplication = this.mUiccApplications;
        int n = arruiccCardApplication.length;
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            UiccCardApplication uiccCardApplication = arruiccCardApplication[i];
            if (uiccCardApplication == null || !this.isSupportedApplication(uiccCardApplication) || uiccCardApplication.isReady() || uiccCardApplication.isAppIgnored()) continue;
            return false;
        }
        if (this.mUiccApplication != null) {
            bl = true;
        }
        return bl;
    }

    private boolean areAllRecordsLoaded() {
        UiccCardApplication[] arruiccCardApplication = this.mUiccApplications;
        int n = arruiccCardApplication.length;
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            Object object = arruiccCardApplication[i];
            if (object == null || !this.isSupportedApplication((UiccCardApplication)object) || ((UiccCardApplication)object).isAppIgnored() || (object = ((UiccCardApplication)object).getIccRecords()) != null && ((IccRecords)object).isLoaded()) continue;
            return false;
        }
        if (this.mUiccApplication != null) {
            bl = true;
        }
        return bl;
    }

    private void checkAndUpdateIfAnyAppToBeIgnored() {
        int n;
        Object object;
        boolean[] arrbl = new boolean[IccCardApplicationStatus.AppType.APPTYPE_ISIM.ordinal() + 1];
        Object object2 = this.mUiccApplications;
        int n2 = ((UiccCardApplication[])object2).length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            object = object2[n];
            if (object == null || !this.isSupportedApplication((UiccCardApplication)object) || !((UiccCardApplication)object).isReady()) continue;
            arrbl[object.getType().ordinal()] = true;
        }
        object = this.mUiccApplications;
        n2 = ((UiccCardApplication[])object).length;
        for (n = n3; n < n2; ++n) {
            object2 = object[n];
            if (object2 == null || !this.isSupportedApplication((UiccCardApplication)object2) || ((UiccCardApplication)object2).isReady() || !arrbl[((UiccCardApplication)object2).getType().ordinal()]) continue;
            ((UiccCardApplication)object2).setAppIgnoreState(true);
        }
    }

    private int checkIndexLocked(int n, IccCardApplicationStatus.AppType object, IccCardApplicationStatus.AppType appType) {
        Object object2 = this.mUiccApplications;
        if (object2 != null && n < ((UiccCardApplication[])object2).length) {
            if (n < 0) {
                return -1;
            }
            if (object2[n].getType() != object && this.mUiccApplications[n].getType() != appType) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("App index ");
                ((StringBuilder)object2).append(n);
                ((StringBuilder)object2).append(" is invalid since it's not ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(" and not ");
                ((StringBuilder)object2).append((Object)appType);
                UiccProfile.loge(((StringBuilder)object2).toString());
                return -1;
            }
            return n;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("App index ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" is invalid since there are no applications");
        UiccProfile.loge(((StringBuilder)object).toString());
        return -1;
    }

    private void createAndUpdateCatServiceLocked() {
        Object object = this.mUiccApplications;
        if (((UiccCardApplication[])object).length > 0 && object[0] != null) {
            object = this.mCatService;
            if (object == null) {
                this.mCatService = CatService.getInstance(this.mCi, this.mContext, this, this.mPhoneId);
            } else {
                ((CatService)object).update(this.mCi, this.mContext, this);
            }
        } else {
            object = this.mCatService;
            if (object != null) {
                ((CatService)object).dispose();
            }
            this.mCatService = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private UiccCarrierPrivilegeRules getCarrierPrivilegeRules() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mCarrierPrivilegeRules;
        }
    }

    private String getIccStateReason(IccCardConstants.State state) {
        switch (state) {
            default: {
                return null;
            }
            case CARD_RESTRICTED: {
                return "CARD_RESTRICTED";
            }
            case CARD_IO_ERROR: {
                return "CARD_IO_ERROR";
            }
            case PERM_DISABLED: {
                return "PERM_DISABLED";
            }
            case NETWORK_LOCKED: {
                return "NETWORK";
            }
            case PUK_REQUIRED: {
                return "PUK";
            }
            case PIN_REQUIRED: 
        }
        return "PIN";
    }

    private Set<String> getUninstalledCarrierPackages() {
        Object object = Settings.Global.getString((ContentResolver)this.mContext.getContentResolver(), (String)"carrier_app_whitelist");
        if (TextUtils.isEmpty((CharSequence)object)) {
            return Collections.emptySet();
        }
        Map<String, String> map = UiccProfile.parseToCertificateToPackageMap((String)object);
        if (map.isEmpty()) {
            return Collections.emptySet();
        }
        if (this.mCarrierPrivilegeRules == null) {
            return Collections.emptySet();
        }
        ArraySet arraySet = new ArraySet();
        object = this.mCarrierPrivilegeRules.getAccessRules().iterator();
        while (object.hasNext()) {
            String string = map.get(((UiccAccessRule)object.next()).getCertificateHexString().toUpperCase());
            if (TextUtils.isEmpty((CharSequence)string) || UiccProfile.isPackageInstalled(this.mContext, string)) continue;
            arraySet.add(string);
        }
        return arraySet;
    }

    private void handleCarrierNameOverride() {
        int n;
        SubscriptionController subscriptionController = SubscriptionController.getInstance();
        int n2 = subscriptionController.getSubIdUsingPhoneId(this.mPhoneId);
        if (n2 == -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("subId not valid for Phone ");
            stringBuilder.append(this.mPhoneId);
            UiccProfile.loge(stringBuilder.toString());
            return;
        }
        Object object = (CarrierConfigManager)this.mContext.getSystemService("carrier_config");
        if (object == null) {
            UiccProfile.loge("Failed to load a Carrier Config");
            return;
        }
        object = object.getConfigForSubId(n2);
        boolean bl = object.getBoolean("carrier_name_override_bool", false);
        object = object.getString("carrier_name_string");
        Object var5_6 = null;
        Object object2 = this.getServiceProviderName();
        int n3 = 1;
        if (!(bl || TextUtils.isEmpty((CharSequence)object2) && !TextUtils.isEmpty((CharSequence)object))) {
            object = var5_6;
            n = n3;
            if (TextUtils.isEmpty((CharSequence)object2)) {
                object2 = PhoneFactory.getPhone(this.mPhoneId);
                object = var5_6;
                n = n3;
                if (object2 != null) {
                    object = ((Phone)object2).getCarrierName();
                    n = n3;
                }
            }
        } else {
            n = 3;
        }
        if (!TextUtils.isEmpty((CharSequence)object)) {
            this.mTelephonyManager.setSimOperatorNameForPhone(this.mPhoneId, (String)object);
            this.mOperatorBrandOverrideRegistrants.notifyRegistrants();
        }
        this.updateCarrierNameForSubscription(subscriptionController, n2, n);
    }

    private void handleSimCountryIsoOverride() {
        Object object = SubscriptionController.getInstance();
        int n = object.getSubIdUsingPhoneId(this.mPhoneId);
        if (n == -1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("subId not valid for Phone ");
            ((StringBuilder)object).append(this.mPhoneId);
            UiccProfile.loge(((StringBuilder)object).toString());
            return;
        }
        Object object2 = (CarrierConfigManager)this.mContext.getSystemService("carrier_config");
        if (object2 == null) {
            UiccProfile.loge("Failed to load a Carrier Config");
            return;
        }
        if (!TextUtils.isEmpty((CharSequence)(object2 = object2.getConfigForSubId(n).getString("sim_country_iso_override_string"))) && !((String)object2).equals(this.mTelephonyManager.getSimCountryIsoForPhone(this.mPhoneId))) {
            this.mTelephonyManager.setSimCountryIsoForPhone(this.mPhoneId, (String)object2);
            object.setCountryIso((String)object2, n);
        }
    }

    static boolean isPackageInstalled(Context object, String string) {
        object = object.getPackageManager();
        try {
            object.getPackageInfo(string, 1);
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" is installed.");
            UiccProfile.log(((StringBuilder)object).toString());
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" is not installed.");
            UiccProfile.log(stringBuilder.toString());
            return false;
        }
    }

    private boolean isSupportedApplication(UiccCardApplication uiccCardApplication) {
        return uiccCardApplication.getType() == IccCardApplicationStatus.AppType.APPTYPE_USIM || uiccCardApplication.getType() == IccCardApplicationStatus.AppType.APPTYPE_SIM || UiccController.isCdmaSupported(this.mContext) && (uiccCardApplication.getType() == IccCardApplicationStatus.AppType.APPTYPE_CSIM || uiccCardApplication.getType() == IccCardApplicationStatus.AppType.APPTYPE_RUIM);
        {
        }
    }

    private static void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private static void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    private void loglocal(String string) {
        LocalLog localLog = UiccController.sLocalLog;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UiccProfile[");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append("]: ");
        stringBuilder.append(string);
        localLog.log(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onCarrierPrivilegesLoadedMessage() {
        Object object = (UsageStatsManager)this.mContext.getSystemService("usagestats");
        if (object != null) {
            object.onCarrierPrivilegedAppsChanged();
        }
        InstallCarrierAppUtils.hideAllNotifications(this.mContext);
        InstallCarrierAppUtils.unregisterPackageInstallReceiver(this.mContext);
        object = this.mLock;
        synchronized (object) {
            this.mCarrierPrivilegeRegistrants.notifyRegistrants();
            Object object2 = this.mContext.getContentResolver();
            boolean bl = true;
            if (Settings.Global.getInt((ContentResolver)object2, (String)"device_provisioned", (int)1) != 1) {
                bl = false;
            }
            if (bl) {
                object2 = this.getUninstalledCarrierPackages().iterator();
                while (object2.hasNext()) {
                    this.promptInstallCarrierApp((String)object2.next());
                }
            } else {
                object2 = Settings.Global.getUriFor((String)"device_provisioned");
                this.mContext.getContentResolver().registerContentObserver((Uri)object2, false, this.mProvisionCompleteContentObserver);
            }
            return;
        }
    }

    @VisibleForTesting
    public static Map<String, String> parseToCertificateToPackageMap(String string) {
        String[] arrstring = Arrays.asList(string.split("\\s*;\\s*"));
        if (arrstring.isEmpty()) {
            return Collections.emptyMap();
        }
        string = new ArrayMap(arrstring.size());
        Iterator<String> iterator = arrstring.iterator();
        while (iterator.hasNext()) {
            arrstring = iterator.next().split("\\s*:\\s*");
            if (arrstring.length == 2) {
                string.put(arrstring[0].toUpperCase(), arrstring[1]);
                continue;
            }
            UiccProfile.loge("Incorrect length of key-value pair in carrier app whitelist map.  Length should be exactly 2");
        }
        return string;
    }

    private void promptInstallCarrierApp(String string) {
        string = InstallCarrierAppTrampolineActivity.get(this.mContext, string);
        string.addFlags(268435456);
        this.mContext.startActivity((Intent)string);
    }

    private void registerAllAppEvents() {
        for (UiccCardApplication uiccCardApplication : this.mUiccApplications) {
            if (uiccCardApplication == null) continue;
            uiccCardApplication.registerForReady(this.mHandler, 3, null);
            IccRecords iccRecords = uiccCardApplication.getIccRecords();
            if (iccRecords == null) continue;
            iccRecords.registerForRecordsLoaded(this.mHandler, 4, null);
            iccRecords.registerForRecordsEvents(this.mHandler, 7, null);
        }
    }

    private void registerCurrAppEvents() {
        IccRecords iccRecords = this.mIccRecords;
        if (iccRecords != null) {
            iccRecords.registerForLockedRecordsLoaded(this.mHandler, 2, null);
            this.mIccRecords.registerForNetworkLockedRecordsLoaded(this.mHandler, 5, null);
        }
    }

    private void sanitizeApplicationIndexesLocked() {
        this.mGsmUmtsSubscriptionAppIndex = this.checkIndexLocked(this.mGsmUmtsSubscriptionAppIndex, IccCardApplicationStatus.AppType.APPTYPE_SIM, IccCardApplicationStatus.AppType.APPTYPE_USIM);
        this.mCdmaSubscriptionAppIndex = this.checkIndexLocked(this.mCdmaSubscriptionAppIndex, IccCardApplicationStatus.AppType.APPTYPE_RUIM, IccCardApplicationStatus.AppType.APPTYPE_CSIM);
        this.mImsSubscriptionAppIndex = this.checkIndexLocked(this.mImsSubscriptionAppIndex, IccCardApplicationStatus.AppType.APPTYPE_ISIM, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setCurrentAppType(boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            this.mCurrentAppType = bl ? 1 : 2;
            return;
        }
    }

    private void setExternalState(IccCardConstants.State state) {
        this.setExternalState(state, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setExternalState(IccCardConstants.State object, boolean bl) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (!SubscriptionManager.isValidSlotIndex((int)this.mPhoneId)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("setExternalState: mPhoneId=");
                ((StringBuilder)object).append(this.mPhoneId);
                ((StringBuilder)object).append(" is invalid; Return!!");
                UiccProfile.loge(((StringBuilder)object).toString());
                return;
            }
            if (!bl && object == this.mExternalState) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setExternalState: !override and newstate unchanged from ");
                stringBuilder.append(object);
                UiccProfile.log(stringBuilder.toString());
                return;
            }
            this.mExternalState = object;
            if (this.mExternalState == IccCardConstants.State.LOADED && this.mIccRecords != null) {
                object = this.mIccRecords.getOperatorNumeric();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setExternalState: operator=");
                stringBuilder.append((String)object);
                stringBuilder.append(" mPhoneId=");
                stringBuilder.append(this.mPhoneId);
                UiccProfile.log(stringBuilder.toString());
                if (!TextUtils.isEmpty((CharSequence)object)) {
                    this.mTelephonyManager.setSimOperatorNumericForPhone(this.mPhoneId, (String)object);
                    object = ((String)object).substring(0, 3);
                    if (object != null) {
                        this.mTelephonyManager.setSimCountryIsoForPhone(this.mPhoneId, MccTable.countryCodeForMcc((String)object));
                    } else {
                        UiccProfile.loge("setExternalState: state LOADED; Country code is null");
                    }
                } else {
                    UiccProfile.loge("setExternalState: state LOADED; Operator name is null");
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("setExternalState: set mPhoneId=");
            ((StringBuilder)object).append(this.mPhoneId);
            ((StringBuilder)object).append(" mExternalState=");
            ((StringBuilder)object).append((Object)this.mExternalState);
            UiccProfile.log(((StringBuilder)object).toString());
            UiccController.updateInternalIccState(this.mContext, this.mExternalState, this.getIccStateReason(this.mExternalState), this.mPhoneId);
            return;
        }
    }

    private void unregisterAllAppEvents() {
        for (UiccCardApplication uiccCardApplication : this.mUiccApplications) {
            if (uiccCardApplication == null) continue;
            uiccCardApplication.unregisterForReady(this.mHandler);
            IccRecords iccRecords = uiccCardApplication.getIccRecords();
            if (iccRecords == null) continue;
            iccRecords.unregisterForRecordsLoaded(this.mHandler);
            iccRecords.unregisterForRecordsEvents(this.mHandler);
        }
    }

    private void unregisterCurrAppEvents() {
        IccRecords iccRecords = this.mIccRecords;
        if (iccRecords != null) {
            iccRecords.unregisterForLockedRecordsLoaded(this.mHandler);
            this.mIccRecords.unregisterForNetworkLockedRecordsLoaded(this.mHandler);
        }
    }

    private void updateCarrierNameForSubscription(SubscriptionController subscriptionController, int n, int n2) {
        Object object = subscriptionController.getActiveSubscriptionInfo(n, this.mContext.getOpPackageName());
        if (object == null) {
            return;
        }
        CharSequence charSequence = object.getDisplayName();
        object = this.mTelephonyManager.getSimOperatorName(n);
        if (!TextUtils.isEmpty((CharSequence)object) && !((String)object).equals(charSequence)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sim name[");
            ((StringBuilder)charSequence).append(this.mPhoneId);
            ((StringBuilder)charSequence).append("] = ");
            ((StringBuilder)charSequence).append((String)object);
            UiccProfile.log(((StringBuilder)charSequence).toString());
            subscriptionController.setDisplayNameUsingSrc((String)object, n, n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateIccAvailability(boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            IccRecords iccRecords = null;
            UiccCardApplication uiccCardApplication = this.getApplication(this.mCurrentAppType);
            if (uiccCardApplication != null) {
                iccRecords = uiccCardApplication.getIccRecords();
            }
            if (bl) {
                this.unregisterAllAppEvents();
                this.registerAllAppEvents();
            }
            if (this.mIccRecords != iccRecords || this.mUiccApplication != uiccCardApplication) {
                UiccProfile.log("Icc changed. Reregistering.");
                this.unregisterCurrAppEvents();
                this.mUiccApplication = uiccCardApplication;
                this.mIccRecords = iccRecords;
                this.registerCurrAppEvents();
            }
            this.updateExternalState();
            return;
        }
    }

    public boolean areCarrierPriviligeRulesLoaded() {
        UiccCarrierPrivilegeRules uiccCarrierPrivilegeRules = this.getCarrierPrivilegeRules();
        boolean bl = uiccCarrierPrivilegeRules == null || uiccCarrierPrivilegeRules.areCarrierPriviligeRulesLoaded();
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void changeIccFdnPassword(String object, String string, Message message) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mUiccApplication != null) {
                this.mUiccApplication.changeIccFdnPassword((String)object, string, message);
            } else if (message != null) {
                AsyncResult.forMessage((Message)message).exception = object = new RuntimeException("ICC card is absent.");
                message.sendToTarget();
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void changeIccLockPassword(String object, String string, Message message) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mUiccApplication != null) {
                this.mUiccApplication.changeIccLockPassword((String)object, string, message);
            } else if (message != null) {
                AsyncResult.forMessage((Message)message).exception = object = new RuntimeException("ICC card is absent.");
                message.sendToTarget();
                return;
            }
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dispose() {
        UiccProfile.log("Disposing profile");
        Object object = this.mUiccCard;
        if (object instanceof EuiccCard) {
            ((EuiccCard)object).unregisterForEidReady(this.mHandler);
        }
        object = this.mLock;
        synchronized (object) {
            this.unregisterAllAppEvents();
            this.unregisterCurrAppEvents();
            InstallCarrierAppUtils.hideAllNotifications(this.mContext);
            InstallCarrierAppUtils.unregisterPackageInstallReceiver(this.mContext);
            this.mCi.unregisterForOffOrNotAvailable(this.mHandler);
            this.mContext.unregisterReceiver(this.mReceiver);
            if (this.mCatService != null) {
                this.mCatService.dispose();
            }
            UiccCardApplication[] arruiccCardApplication = this.mUiccApplications;
            int n = arruiccCardApplication.length;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.mCatService = null;
                    this.mUiccApplications = null;
                    this.mCarrierPrivilegeRules = null;
                    this.mContext.getContentResolver().unregisterContentObserver(this.mProvisionCompleteContentObserver);
                    this.mDisposed = true;
                    return;
                }
                UiccCardApplication uiccCardApplication = arruiccCardApplication[n2];
                if (uiccCardApplication != null) {
                    uiccCardApplication.dispose();
                }
                ++n2;
            } while (true);
        }
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        int n;
        printWriter.println("UiccProfile:");
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mCi=");
        ((StringBuilder)object2).append(this.mCi);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mCatService=");
        ((StringBuilder)object2).append(this.mCatService);
        printWriter.println(((StringBuilder)object2).toString());
        for (n = 0; n < this.mCarrierPrivilegeRegistrants.size(); ++n) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  mCarrierPrivilegeRegistrants[");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append("]=");
            ((StringBuilder)object2).append((Object)((Registrant)this.mCarrierPrivilegeRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object2).toString());
        }
        for (n = 0; n < this.mOperatorBrandOverrideRegistrants.size(); ++n) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  mOperatorBrandOverrideRegistrants[");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append("]=");
            ((StringBuilder)object2).append((Object)((Registrant)this.mOperatorBrandOverrideRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object2).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mUniversalPinState=");
        ((StringBuilder)object2).append((Object)this.mUniversalPinState);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mGsmUmtsSubscriptionAppIndex=");
        ((StringBuilder)object2).append(this.mGsmUmtsSubscriptionAppIndex);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mCdmaSubscriptionAppIndex=");
        ((StringBuilder)object2).append(this.mCdmaSubscriptionAppIndex);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mImsSubscriptionAppIndex=");
        ((StringBuilder)object2).append(this.mImsSubscriptionAppIndex);
        printWriter.println(((StringBuilder)object2).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mUiccApplications: length=");
        ((StringBuilder)object2).append(this.mUiccApplications.length);
        printWriter.println(((StringBuilder)object2).toString());
        for (n = 0; n < ((UiccCardApplication[])(object2 = this.mUiccApplications)).length; ++n) {
            if (object2[n] == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("  mUiccApplications[");
                ((StringBuilder)object2).append(n);
                ((StringBuilder)object2).append("]=");
                ((StringBuilder)object2).append((Object)null);
                printWriter.println(((StringBuilder)object2).toString());
                continue;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  mUiccApplications[");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append("]=");
            ((StringBuilder)object2).append((Object)this.mUiccApplications[n].getType());
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(this.mUiccApplications[n]);
            printWriter.println(((StringBuilder)object2).toString());
        }
        printWriter.println();
        Object object3 = this.mUiccApplications;
        int n2 = ((UiccCardApplication[])object3).length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            object2 = object3[n];
            if (object2 == null) continue;
            ((UiccCardApplication)object2).dump((FileDescriptor)object, printWriter, arrstring);
            printWriter.println();
        }
        object2 = this.mUiccApplications;
        n2 = ((Object)object2).length;
        for (n = n3; n < n2; ++n) {
            object3 = object2[n];
            if (object3 == null || (object3 = ((UiccCardApplication)object3).getIccRecords()) == null) continue;
            ((IccRecords)object3).dump((FileDescriptor)object, printWriter, arrstring);
            printWriter.println();
        }
        if (this.mCarrierPrivilegeRules == null) {
            printWriter.println(" mCarrierPrivilegeRules: null");
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" mCarrierPrivilegeRules: ");
            ((StringBuilder)object2).append((Object)this.mCarrierPrivilegeRules);
            printWriter.println(((StringBuilder)object2).toString());
            this.mCarrierPrivilegeRules.dump((FileDescriptor)object, printWriter, arrstring);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCarrierPrivilegeRegistrants: size=");
        ((StringBuilder)object).append(this.mCarrierPrivilegeRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mCarrierPrivilegeRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mCarrierPrivilegeRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mCarrierPrivilegeRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        printWriter.flush();
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNetworkLockedRegistrants: size=");
        ((StringBuilder)object).append(this.mNetworkLockedRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mNetworkLockedRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mNetworkLockedRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mNetworkLockedRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCurrentAppType=");
        ((StringBuilder)object).append(this.mCurrentAppType);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mUiccCard=");
        ((StringBuilder)object).append(this.mUiccCard);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mUiccApplication=");
        ((StringBuilder)object).append(this.mUiccApplication);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIccRecords=");
        ((StringBuilder)object).append(this.mIccRecords);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mExternalState=");
        ((StringBuilder)object).append((Object)this.mExternalState);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
    }

    protected void finalize() {
        UiccProfile.log("UiccProfile finalized");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UiccCardApplication getApplication(int n) {
        Object object = this.mLock;
        synchronized (object) {
            int n2 = 8;
            n = n != 1 ? (n != 2 ? (n != 3 ? n2 : this.mImsSubscriptionAppIndex) : this.mCdmaSubscriptionAppIndex) : this.mGsmUmtsSubscriptionAppIndex;
            if (n < 0) return null;
            if (n >= this.mUiccApplications.length) return null;
            return this.mUiccApplications[n];
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UiccCardApplication getApplicationByType(int n) {
        Object object = this.mLock;
        synchronized (object) {
            int n2 = 0;
            while (n2 < this.mUiccApplications.length) {
                if (this.mUiccApplications[n2] != null && this.mUiccApplications[n2].getType().ordinal() == n) {
                    return this.mUiccApplications[n2];
                }
                ++n2;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public UiccCardApplication getApplicationIndex(int n) {
        Object object = this.mLock;
        synchronized (object) {
            if (n < 0) return null;
            if (n >= this.mUiccApplications.length) return null;
            return this.mUiccApplications[n];
        }
    }

    public List<String> getCarrierPackageNamesForIntent(PackageManager object, Intent intent) {
        UiccCarrierPrivilegeRules uiccCarrierPrivilegeRules = this.getCarrierPrivilegeRules();
        object = uiccCarrierPrivilegeRules == null ? null : uiccCarrierPrivilegeRules.getCarrierPackageNamesForIntent((PackageManager)object, intent);
        return object;
    }

    public int getCarrierPrivilegeStatus(PackageInfo packageInfo) {
        UiccCarrierPrivilegeRules uiccCarrierPrivilegeRules = this.getCarrierPrivilegeRules();
        int n = uiccCarrierPrivilegeRules == null ? -1 : uiccCarrierPrivilegeRules.getCarrierPrivilegeStatus(packageInfo);
        return n;
    }

    public int getCarrierPrivilegeStatus(PackageManager packageManager, String string) {
        UiccCarrierPrivilegeRules uiccCarrierPrivilegeRules = this.getCarrierPrivilegeRules();
        int n = uiccCarrierPrivilegeRules == null ? -1 : uiccCarrierPrivilegeRules.getCarrierPrivilegeStatus(packageManager, string);
        return n;
    }

    public int getCarrierPrivilegeStatus(Signature signature, String string) {
        UiccCarrierPrivilegeRules uiccCarrierPrivilegeRules = this.getCarrierPrivilegeRules();
        int n = uiccCarrierPrivilegeRules == null ? -1 : uiccCarrierPrivilegeRules.getCarrierPrivilegeStatus(signature, string);
        return n;
    }

    public int getCarrierPrivilegeStatusForCurrentTransaction(PackageManager packageManager) {
        UiccCarrierPrivilegeRules uiccCarrierPrivilegeRules = this.getCarrierPrivilegeRules();
        int n = uiccCarrierPrivilegeRules == null ? -1 : uiccCarrierPrivilegeRules.getCarrierPrivilegeStatusForCurrentTransaction(packageManager);
        return n;
    }

    public int getCarrierPrivilegeStatusForUid(PackageManager packageManager, int n) {
        UiccCarrierPrivilegeRules uiccCarrierPrivilegeRules = this.getCarrierPrivilegeRules();
        n = uiccCarrierPrivilegeRules == null ? -1 : uiccCarrierPrivilegeRules.getCarrierPrivilegeStatusForUid(packageManager, n);
        return n;
    }

    public List<String> getCertsFromCarrierPrivilegeAccessRules() {
        ArrayList<String> arrayList;
        block2 : {
            arrayList = new ArrayList<String>();
            Object object = this.getCarrierPrivilegeRules();
            if (object != null) {
                object = object.getAccessRules().iterator();
                while (object.hasNext()) {
                    arrayList.add(((UiccAccessRule)object.next()).getCertificateHexString());
                }
            }
            if (!arrayList.isEmpty()) break block2;
            arrayList = null;
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean getIccFdnAvailable() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUiccApplication == null) return false;
            if (!this.mUiccApplication.getIccFdnAvailable()) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean getIccFdnEnabled() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUiccApplication == null) return false;
            if (!this.mUiccApplication.getIccFdnEnabled()) return false;
            return true;
        }
    }

    public String getIccId() {
        for (UiccCardApplication uiccCardApplication : this.mUiccApplications) {
            IccRecords iccRecords;
            if (uiccCardApplication == null || (iccRecords = uiccCardApplication.getIccRecords()) == null || iccRecords.getIccId() == null) continue;
            return iccRecords.getIccId();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean getIccLockEnabled() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUiccApplication == null) return false;
            if (!this.mUiccApplication.getIccLockEnabled()) return false;
            return true;
        }
    }

    @Override
    public boolean getIccPin2Blocked() {
        UiccCardApplication uiccCardApplication = this.mUiccApplication;
        boolean bl = uiccCardApplication != null && uiccCardApplication.getIccPin2Blocked();
        return bl;
    }

    @Override
    public boolean getIccPuk2Blocked() {
        UiccCardApplication uiccCardApplication = this.mUiccApplication;
        boolean bl = uiccCardApplication != null && uiccCardApplication.getIccPuk2Blocked();
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public IccRecords getIccRecords() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mIccRecords;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getIccRecordsLoaded() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mIccRecords == null) return false;
            return this.mIccRecords.getRecordsLoaded();
        }
    }

    public int getNumApplications() {
        int n = 0;
        UiccCardApplication[] arruiccCardApplication = this.mUiccApplications;
        int n2 = arruiccCardApplication.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = n;
            if (arruiccCardApplication[i] != null) {
                n3 = n + 1;
            }
            n = n3;
        }
        return n;
    }

    public String getOperatorBrandOverride() {
        String string = this.getIccId();
        if (TextUtils.isEmpty((CharSequence)string)) {
            return null;
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(OPERATOR_BRAND_OVERRIDE_PREFIX);
        stringBuilder.append(string);
        return sharedPreferences.getString(stringBuilder.toString(), null);
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String getServiceProviderName() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mIccRecords == null) return null;
            return this.mIccRecords.getServiceProviderName();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public IccCardConstants.State getState() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mExternalState;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IccCardStatus.PinState getUniversalPinState() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mUniversalPinState;
        }
    }

    public boolean hasCarrierPrivilegeRules() {
        UiccCarrierPrivilegeRules uiccCarrierPrivilegeRules = this.getCarrierPrivilegeRules();
        boolean bl = uiccCarrierPrivilegeRules != null && uiccCarrierPrivilegeRules.hasCarrierPrivilegeRules();
        return bl;
    }

    @Override
    public boolean hasIccCard() {
        if (this.mUiccCard.getCardState() != IccCardStatus.CardState.CARDSTATE_ABSENT) {
            return true;
        }
        UiccProfile.loge("hasIccCard: UiccProfile is not null but UiccCard is null or card state is ABSENT");
        return false;
    }

    public void iccCloseLogicalChannel(int n, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("iccCloseLogicalChannel: ");
        stringBuilder.append(n);
        this.loglocal(stringBuilder.toString());
        this.mCi.iccCloseLogicalChannel(n, this.mHandler.obtainMessage(9, (Object)message));
    }

    public void iccExchangeSimIO(int n, int n2, int n3, int n4, int n5, String string, Message message) {
        this.mCi.iccIO(n2, n, string, n3, n4, n5, null, null, this.mHandler.obtainMessage(12, (Object)message));
    }

    public void iccOpenLogicalChannel(String string, int n, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("iccOpenLogicalChannel: ");
        stringBuilder.append(string);
        stringBuilder.append(" , ");
        stringBuilder.append(n);
        stringBuilder.append(" by pid:");
        stringBuilder.append(Binder.getCallingPid());
        stringBuilder.append(" uid:");
        stringBuilder.append(Binder.getCallingUid());
        this.loglocal(stringBuilder.toString());
        this.mCi.iccOpenLogicalChannel(string, n, this.mHandler.obtainMessage(8, (Object)message));
    }

    public void iccTransmitApduBasicChannel(int n, int n2, int n3, int n4, int n5, String string, Message message) {
        this.mCi.iccTransmitApduBasicChannel(n, n2, n3, n4, n5, string, this.mHandler.obtainMessage(11, (Object)message));
    }

    public void iccTransmitApduLogicalChannel(int n, int n2, int n3, int n4, int n5, int n6, String string, Message message) {
        this.mCi.iccTransmitApduLogicalChannel(n, n2, n3, n4, n5, n6, string, this.mHandler.obtainMessage(10, (Object)message));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isApplicationOnIcc(IccCardApplicationStatus.AppType appType) {
        Object object = this.mLock;
        synchronized (object) {
            int n = 0;
            while (n < this.mUiccApplications.length) {
                if (this.mUiccApplications[n] != null && this.mUiccApplications[n].getType() == appType) {
                    return true;
                }
                ++n;
            }
            return false;
        }
    }

    @Override
    public boolean isEmptyProfile() {
        UiccCardApplication[] arruiccCardApplication = this.mUiccApplications;
        int n = arruiccCardApplication.length;
        for (int i = 0; i < n; ++i) {
            if (arruiccCardApplication[i] == null) continue;
            return false;
        }
        return true;
    }

    @VisibleForTesting
    public void refresh() {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(13));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerForCarrierPrivilegeRulesLoaded(Handler handler, int n, Object object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            Registrant registrant = new Registrant(handler, n, object);
            this.mCarrierPrivilegeRegistrants.add(registrant);
            if (this.areCarrierPriviligeRulesLoaded()) {
                registrant.notifyRegistrant();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerForNetworkLocked(Handler handler, int n, Object object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            Registrant registrant = new Registrant(handler, n, object);
            this.mNetworkLockedRegistrants.add(registrant);
            if (this.getState() == IccCardConstants.State.NETWORK_LOCKED) {
                registrant.notifyRegistrant();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerForOpertorBrandOverride(Handler handler, int n, Object object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            Registrant registrant = new Registrant(handler, n, object);
            this.mOperatorBrandOverrideRegistrants.add(registrant);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean resetAppWithAid(String string, boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            boolean bl2;
            boolean bl3 = false;
            for (int i = 0; i < this.mUiccApplications.length; ++i) {
                block8 : {
                    block9 : {
                        bl2 = bl3;
                        if (this.mUiccApplications[i] == null) break block8;
                        if (TextUtils.isEmpty((CharSequence)string)) break block9;
                        bl2 = bl3;
                        if (!string.equals(this.mUiccApplications[i].getAid())) break block8;
                    }
                    this.mUiccApplications[i].dispose();
                    this.mUiccApplications[i] = null;
                    bl2 = true;
                }
                bl3 = bl2;
            }
            bl2 = bl3;
            if (!bl) return bl2;
            bl2 = bl3;
            if (!TextUtils.isEmpty((CharSequence)string)) return bl2;
            if (this.mCarrierPrivilegeRules != null) {
                this.mCarrierPrivilegeRules = null;
                this.mContext.getContentResolver().unregisterContentObserver(this.mProvisionCompleteContentObserver);
                bl3 = true;
            }
            bl2 = bl3;
            if (this.mCatService == null) return bl2;
            this.mCatService.dispose();
            this.mCatService = null;
            return true;
        }
    }

    void resetProperties() {
        if (this.mCurrentAppType == 1) {
            UiccProfile.log("update icc_operator_numeric=");
            this.mTelephonyManager.setSimOperatorNumericForPhone(this.mPhoneId, "");
            this.mTelephonyManager.setSimCountryIsoForPhone(this.mPhoneId, "");
            this.mTelephonyManager.setSimOperatorNameForPhone(this.mPhoneId, "");
        }
    }

    public void sendEnvelopeWithStatus(String string, Message message) {
        this.mCi.sendEnvelopeWithStatus(string, message);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setIccFdnEnabled(boolean bl, String object, Message message) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mUiccApplication != null) {
                this.mUiccApplication.setIccFdnEnabled(bl, (String)object, message);
            } else if (message != null) {
                AsyncResult.forMessage((Message)message).exception = object = new RuntimeException("ICC card is absent.");
                message.sendToTarget();
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setIccLockEnabled(boolean bl, String object, Message message) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mUiccApplication != null) {
                this.mUiccApplication.setIccLockEnabled(bl, (String)object, message);
            } else if (message != null) {
                AsyncResult.forMessage((Message)message).exception = object = new RuntimeException("ICC card is absent.");
                message.sendToTarget();
                return;
            }
            return;
        }
    }

    public boolean setOperatorBrandOverride(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setOperatorBrandOverride: ");
        stringBuilder.append(string);
        UiccProfile.log(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("current iccId: ");
        stringBuilder.append(SubscriptionInfo.givePrintableIccid((String)this.getIccId()));
        UiccProfile.log(stringBuilder.toString());
        String string2 = this.getIccId();
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return false;
        }
        stringBuilder = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext).edit();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(OPERATOR_BRAND_OVERRIDE_PREFIX);
        stringBuilder2.append(string2);
        string2 = stringBuilder2.toString();
        if (string == null) {
            stringBuilder.remove(string2).commit();
        } else {
            stringBuilder.putString(string2, string).commit();
        }
        this.mOperatorBrandOverrideRegistrants.notifyRegistrants();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setVoiceRadioTech(int n) {
        Object object = this.mLock;
        synchronized (object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Setting radio tech ");
            stringBuilder.append(ServiceState.rilRadioTechnologyToString((int)n));
            UiccProfile.log(stringBuilder.toString());
            this.setCurrentAppType(ServiceState.isGsm((int)n));
            this.updateIccAvailability(false);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void supplyNetworkDepersonalization(String object, Message message) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mUiccApplication != null) {
                this.mUiccApplication.supplyNetworkDepersonalization((String)object, message);
            } else if (message != null) {
                AsyncResult.forMessage((Message)message).exception = object = new RuntimeException("CommandsInterface is not set.");
                message.sendToTarget();
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void supplyPin(String object, Message message) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mUiccApplication != null) {
                this.mUiccApplication.supplyPin((String)object, message);
            } else if (message != null) {
                AsyncResult.forMessage((Message)message).exception = object = new RuntimeException("ICC card is absent.");
                message.sendToTarget();
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void supplyPin2(String object, Message message) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mUiccApplication != null) {
                this.mUiccApplication.supplyPin2((String)object, message);
            } else if (message != null) {
                AsyncResult.forMessage((Message)message).exception = object = new RuntimeException("ICC card is absent.");
                message.sendToTarget();
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void supplyPuk(String object, String string, Message message) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mUiccApplication != null) {
                this.mUiccApplication.supplyPuk((String)object, string, message);
            } else if (message != null) {
                AsyncResult.forMessage((Message)message).exception = object = new RuntimeException("ICC card is absent.");
                message.sendToTarget();
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void supplyPuk2(String object, String string, Message message) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mUiccApplication != null) {
                this.mUiccApplication.supplyPuk2((String)object, string, message);
            } else if (message != null) {
                AsyncResult.forMessage((Message)message).exception = object = new RuntimeException("ICC card is absent.");
                message.sendToTarget();
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterForCarrierPrivilegeRulesLoaded(Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            this.mCarrierPrivilegeRegistrants.remove(handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterForNetworkLocked(Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            this.mNetworkLockedRegistrants.remove(handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterForOperatorBrandOverride(Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            this.mOperatorBrandOverrideRegistrants.remove(handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void update(Context object, CommandsInterface commandsInterface, IccCardStatus iccCardStatus) {
        Object object2 = this.mLock;
        synchronized (object2) {
            this.mUniversalPinState = iccCardStatus.mUniversalPinState;
            this.mGsmUmtsSubscriptionAppIndex = iccCardStatus.mGsmUmtsSubscriptionAppIndex;
            this.mCdmaSubscriptionAppIndex = iccCardStatus.mCdmaSubscriptionAppIndex;
            this.mImsSubscriptionAppIndex = iccCardStatus.mImsSubscriptionAppIndex;
            this.mContext = object;
            this.mCi = commandsInterface;
            this.mTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
            object = new StringBuilder();
            object.append(iccCardStatus.mApplications.length);
            object.append(" applications");
            UiccProfile.log(object.toString());
            for (int i = 0; i < this.mUiccApplications.length; ++i) {
                if (this.mUiccApplications[i] == null) {
                    if (i >= iccCardStatus.mApplications.length) continue;
                    this.mUiccApplications[i] = new UiccCardApplication(this, iccCardStatus.mApplications[i], this.mContext, this.mCi);
                    continue;
                }
                if (i >= iccCardStatus.mApplications.length) {
                    this.mUiccApplications[i].dispose();
                    this.mUiccApplications[i] = null;
                    continue;
                }
                this.mUiccApplications[i].update(iccCardStatus.mApplications[i], this.mContext, this.mCi);
            }
            this.createAndUpdateCatServiceLocked();
            object = new StringBuilder();
            object.append("Before privilege rules: ");
            object.append((Object)this.mCarrierPrivilegeRules);
            object.append(" : ");
            object.append((Object)iccCardStatus.mCardState);
            UiccProfile.log(object.toString());
            if (this.mCarrierPrivilegeRules == null && iccCardStatus.mCardState == IccCardStatus.CardState.CARDSTATE_PRESENT) {
                object = new UiccCarrierPrivilegeRules(this, this.mHandler.obtainMessage(13));
                this.mCarrierPrivilegeRules = object;
            } else if (this.mCarrierPrivilegeRules != null && iccCardStatus.mCardState != IccCardStatus.CardState.CARDSTATE_PRESENT) {
                this.mCarrierPrivilegeRules = null;
                this.mContext.getContentResolver().unregisterContentObserver(this.mProvisionCompleteContentObserver);
            }
            this.sanitizeApplicationIndexesLocked();
            this.updateIccAvailability(true);
            return;
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public void updateExternalState() {
        int n;
        if (this.mUiccCard.getCardState() == IccCardStatus.CardState.CARDSTATE_ERROR) {
            this.setExternalState(IccCardConstants.State.CARD_IO_ERROR);
            return;
        }
        if (this.mUiccCard.getCardState() == IccCardStatus.CardState.CARDSTATE_RESTRICTED) {
            this.setExternalState(IccCardConstants.State.CARD_RESTRICTED);
            return;
        }
        Object object = this.mUiccCard;
        if (object instanceof EuiccCard && ((EuiccCard)object).getEid() == null) {
            UiccProfile.log("EID is not ready yet.");
            return;
        }
        object = this.mUiccApplication;
        if (object == null) {
            UiccProfile.loge("updateExternalState: setting state to NOT_READY because mUiccApplication is null");
            this.setExternalState(IccCardConstants.State.NOT_READY);
            return;
        }
        int n2 = 0;
        IccRecords iccRecords = null;
        IccCardApplicationStatus.AppState appState = ((UiccCardApplication)object).getState();
        if (this.mUiccApplication.getPin1State() == IccCardStatus.PinState.PINSTATE_ENABLED_PERM_BLOCKED) {
            n = 1;
            object = IccCardConstants.State.PERM_DISABLED;
        } else if (appState == IccCardApplicationStatus.AppState.APPSTATE_PIN) {
            n = 1;
            object = IccCardConstants.State.PIN_REQUIRED;
        } else if (appState == IccCardApplicationStatus.AppState.APPSTATE_PUK) {
            n = 1;
            object = IccCardConstants.State.PUK_REQUIRED;
        } else {
            n = n2;
            object = iccRecords;
            if (appState == IccCardApplicationStatus.AppState.APPSTATE_SUBSCRIPTION_PERSO) {
                n = n2;
                object = iccRecords;
                if (this.mUiccApplication.getPersoSubState() == IccCardApplicationStatus.PersoSubState.PERSOSUBSTATE_SIM_NETWORK) {
                    n = 1;
                    object = IccCardConstants.State.NETWORK_LOCKED;
                }
            }
        }
        if (n != 0) {
            iccRecords = this.mIccRecords;
            if (iccRecords != null && (iccRecords.getLockedRecordsLoaded() || this.mIccRecords.getNetworkLockedRecordsLoaded())) {
                this.setExternalState((IccCardConstants.State)object);
            } else {
                this.setExternalState(IccCardConstants.State.NOT_READY);
            }
            return;
        }
        n = 4.$SwitchMap$com$android$internal$telephony$uicc$IccCardApplicationStatus$AppState[appState.ordinal()];
        if (n != 1) {
            if (n == 2) {
                this.checkAndUpdateIfAnyAppToBeIgnored();
                if (this.areAllApplicationsReady()) {
                    if (this.areAllRecordsLoaded() && this.areCarrierPriviligeRulesLoaded()) {
                        this.setExternalState(IccCardConstants.State.LOADED);
                    } else {
                        this.setExternalState(IccCardConstants.State.READY);
                    }
                } else {
                    this.setExternalState(IccCardConstants.State.NOT_READY);
                }
            }
        } else {
            this.setExternalState(IccCardConstants.State.NOT_READY);
        }
    }

}

