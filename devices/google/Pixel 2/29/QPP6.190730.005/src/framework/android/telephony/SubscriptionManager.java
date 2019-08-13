/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.-$
 *  android.telephony.-$$Lambda
 *  android.telephony.-$$Lambda$SubscriptionManager
 *  android.telephony.-$$Lambda$SubscriptionManager$D5_PmvQ13e0qLtSnBvNd4R7l2qA
 *  android.telephony.-$$Lambda$SubscriptionManager$EEe2NsDpuDogw8-UijVBhj7Vuhk
 *  android.telephony.-$$Lambda$SubscriptionManager$W41XrJh1c8ZX_i9kWtj1rBU9l8o
 */
package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.INetworkPolicyManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.-$;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionPlan;
import android.telephony.TelephonyManager;
import android.telephony.UiccAccessRule;
import android.telephony._$$Lambda$SubscriptionManager$1$oi86t06gqdSgTtWgRmCc5dJIfEs;
import android.telephony._$$Lambda$SubscriptionManager$1$qFZ_q9KyfPAkHTrQPCRyO6OQ_pc;
import android.telephony._$$Lambda$SubscriptionManager$3ws2BzXOcyDc_7TPZx2HIBCIjbs;
import android.telephony._$$Lambda$SubscriptionManager$D5_PmvQ13e0qLtSnBvNd4R7l2qA;
import android.telephony._$$Lambda$SubscriptionManager$EEe2NsDpuDogw8_UijVBhj7Vuhk;
import android.telephony._$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY__L174e3u4tk;
import android.telephony._$$Lambda$SubscriptionManager$OS3WICha4HbZhTnWrKCxeu6dr6g;
import android.telephony._$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E;
import android.telephony._$$Lambda$SubscriptionManager$R_uORt9bKcmEo6JnjiGP2KgjIOQ;
import android.telephony._$$Lambda$SubscriptionManager$RmtPOPFQV3mOx5HejDzImseJ0Qg;
import android.telephony._$$Lambda$SubscriptionManager$W41XrJh1c8ZX_i9kWtj1rBU9l8o;
import android.telephony._$$Lambda$SubscriptionManager$xw48SQFFAHLgpsIZZWeq63fMykw;
import android.telephony.euicc.EuiccManager;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.internal.telephony.IOnSubscriptionsChangedListener;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.ISub;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SubscriptionManager {
    public static final String ACCESS_RULES = "access_rules";
    public static final String ACTION_DEFAULT_SMS_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED";
    public static final String ACTION_DEFAULT_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED";
    public static final String ACTION_MANAGE_SUBSCRIPTION_PLANS = "android.telephony.action.MANAGE_SUBSCRIPTION_PLANS";
    public static final String ACTION_REFRESH_SUBSCRIPTION_PLANS = "android.telephony.action.REFRESH_SUBSCRIPTION_PLANS";
    public static final String ACTION_SUBSCRIPTION_PLANS_CHANGED = "android.telephony.action.SUBSCRIPTION_PLANS_CHANGED";
    @SystemApi
    public static final Uri ADVANCED_CALLING_ENABLED_CONTENT_URI;
    public static final String CARD_ID = "card_id";
    public static final String CARRIER_ID = "carrier_id";
    public static final String CARRIER_NAME = "carrier_name";
    public static final String CB_ALERT_REMINDER_INTERVAL = "alert_reminder_interval";
    public static final String CB_ALERT_SOUND_DURATION = "alert_sound_duration";
    public static final String CB_ALERT_SPEECH = "enable_alert_speech";
    public static final String CB_ALERT_VIBRATE = "enable_alert_vibrate";
    public static final String CB_AMBER_ALERT = "enable_cmas_amber_alerts";
    public static final String CB_CHANNEL_50_ALERT = "enable_channel_50_alerts";
    public static final String CB_CMAS_TEST_ALERT = "enable_cmas_test_alerts";
    public static final String CB_EMERGENCY_ALERT = "enable_emergency_alerts";
    public static final String CB_ETWS_TEST_ALERT = "enable_etws_test_alerts";
    public static final String CB_EXTREME_THREAT_ALERT = "enable_cmas_extreme_threat_alerts";
    public static final String CB_OPT_OUT_DIALOG = "show_cmas_opt_out_dialog";
    public static final String CB_SEVERE_THREAT_ALERT = "enable_cmas_severe_threat_alerts";
    public static final String COLOR = "color";
    public static final int COLOR_1 = 0;
    public static final int COLOR_2 = 1;
    public static final int COLOR_3 = 2;
    public static final int COLOR_4 = 3;
    public static final int COLOR_DEFAULT = 0;
    @UnsupportedAppUsage
    public static final Uri CONTENT_URI;
    public static final String DATA_ENABLED_OVERRIDE_RULES = "data_enabled_override_rules";
    public static final String DATA_ROAMING = "data_roaming";
    public static final int DATA_ROAMING_DEFAULT = 0;
    public static final int DATA_ROAMING_DISABLE = 0;
    public static final int DATA_ROAMING_ENABLE = 1;
    private static final boolean DBG = false;
    public static final int DEFAULT_NAME_RES = 17039374;
    public static final int DEFAULT_PHONE_INDEX = Integer.MAX_VALUE;
    public static final int DEFAULT_SIM_SLOT_INDEX = Integer.MAX_VALUE;
    public static final int DEFAULT_SUBSCRIPTION_ID = Integer.MAX_VALUE;
    public static final String DISPLAY_NAME = "display_name";
    public static final int DISPLAY_NUMBER_DEFAULT = 1;
    public static final int DISPLAY_NUMBER_FIRST = 1;
    public static final String DISPLAY_NUMBER_FORMAT = "display_number_format";
    public static final int DISPLAY_NUMBER_LAST = 2;
    public static final int DISPLAY_NUMBER_NONE = 0;
    public static final int DUMMY_SUBSCRIPTION_ID_BASE = -2;
    public static final String EHPLMNS = "ehplmns";
    public static final String ENHANCED_4G_MODE_ENABLED = "volte_vt_enabled";
    public static final String EXTRA_SUBSCRIPTION_INDEX = "android.telephony.extra.SUBSCRIPTION_INDEX";
    public static final String GROUP_OWNER = "group_owner";
    public static final String GROUP_UUID = "group_uuid";
    public static final String HPLMNS = "hplmns";
    public static final String ICC_ID = "icc_id";
    public static final String IMSI = "imsi";
    public static final int INVALID_PHONE_INDEX = -1;
    public static final int INVALID_SIM_SLOT_INDEX = -1;
    public static final int INVALID_SUBSCRIPTION_ID = -1;
    public static final String ISO_COUNTRY_CODE = "iso_country_code";
    public static final String IS_EMBEDDED = "is_embedded";
    public static final String IS_METERED = "is_metered";
    public static final String IS_OPPORTUNISTIC = "is_opportunistic";
    public static final String IS_REMOVABLE = "is_removable";
    private static final String LOG_TAG = "SubscriptionManager";
    public static final int MAX_SUBSCRIPTION_ID_VALUE = 2147483646;
    public static final String MCC = "mcc";
    public static final String MCC_STRING = "mcc_string";
    public static final int MIN_SUBSCRIPTION_ID_VALUE = 0;
    public static final String MNC = "mnc";
    public static final String MNC_STRING = "mnc_string";
    public static final String NAME_SOURCE = "name_source";
    public static final int NAME_SOURCE_CARRIER = 3;
    public static final int NAME_SOURCE_DEFAULT_SOURCE = 0;
    public static final int NAME_SOURCE_SIM_SOURCE = 1;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final int NAME_SOURCE_USER_INPUT = 2;
    public static final String NUMBER = "number";
    public static final String PROFILE_CLASS = "profile_class";
    @SystemApi
    public static final int PROFILE_CLASS_DEFAULT = -1;
    @SystemApi
    public static final int PROFILE_CLASS_OPERATIONAL = 2;
    @SystemApi
    public static final int PROFILE_CLASS_PROVISIONING = 1;
    @SystemApi
    public static final int PROFILE_CLASS_TESTING = 0;
    @SystemApi
    public static final int PROFILE_CLASS_UNSET = -1;
    public static final int SIM_NOT_INSERTED = -1;
    public static final int SIM_PROVISIONED = 0;
    public static final String SIM_PROVISIONING_STATUS = "sim_provisioning_status";
    public static final String SIM_SLOT_INDEX = "sim_id";
    public static final int SLOT_INDEX_FOR_REMOTE_SIM_SUB = -1;
    public static final String SUBSCRIPTION_TYPE = "subscription_type";
    public static final int SUBSCRIPTION_TYPE_LOCAL_SIM = 0;
    public static final int SUBSCRIPTION_TYPE_REMOTE_SIM = 1;
    public static final String SUB_DEFAULT_CHANGED_ACTION = "android.intent.action.SUB_DEFAULT_CHANGED";
    public static final String UNIQUE_KEY_SUBSCRIPTION_ID = "_id";
    private static final boolean VDBG = false;
    @SystemApi
    public static final Uri VT_ENABLED_CONTENT_URI;
    public static final String VT_IMS_ENABLED = "vt_ims_enabled";
    @SystemApi
    public static final Uri WFC_ENABLED_CONTENT_URI;
    public static final String WFC_IMS_ENABLED = "wfc_ims_enabled";
    public static final String WFC_IMS_MODE = "wfc_ims_mode";
    public static final String WFC_IMS_ROAMING_ENABLED = "wfc_ims_roaming_enabled";
    public static final String WFC_IMS_ROAMING_MODE = "wfc_ims_roaming_mode";
    @SystemApi
    public static final Uri WFC_MODE_CONTENT_URI;
    @SystemApi
    public static final Uri WFC_ROAMING_ENABLED_CONTENT_URI;
    @SystemApi
    public static final Uri WFC_ROAMING_MODE_CONTENT_URI;
    @Deprecated
    public static final String WHITE_LISTED_APN_DATA = "white_listed_apn_data";
    private final Context mContext;
    private volatile INetworkPolicyManager mNetworkPolicy;

    static {
        CONTENT_URI = Uri.parse("content://telephony/siminfo");
        WFC_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc");
        ADVANCED_CALLING_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "advanced_calling");
        WFC_MODE_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc_mode");
        WFC_ROAMING_MODE_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc_roaming_mode");
        VT_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "vt_enabled");
        WFC_ROAMING_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc_roaming_enabled");
    }

    @UnsupportedAppUsage
    public SubscriptionManager(Context context) {
        this.mContext = context;
    }

    private Intent createRefreshSubscriptionIntent(int n) {
        String string2 = this.getSubscriptionPlansOwner(n);
        if (string2 == null) {
            return null;
        }
        if (this.getSubscriptionPlans(n).isEmpty()) {
            return null;
        }
        Intent intent = new Intent(ACTION_REFRESH_SUBSCRIPTION_PLANS);
        intent.addFlags(268435456);
        intent.setPackage(string2);
        intent.putExtra(EXTRA_SUBSCRIPTION_INDEX, n);
        if (this.mContext.getPackageManager().queryBroadcastReceivers(intent, 0).isEmpty()) {
            return null;
        }
        return intent;
    }

    @Deprecated
    public static SubscriptionManager from(Context context) {
        return (SubscriptionManager)context.getSystemService("telephony_subscription_service");
    }

    public static boolean getBooleanSubscriptionProperty(int n, String string2, boolean bl, Context context) {
        if ((string2 = SubscriptionManager.getSubscriptionProperty(n, string2, context)) != null) {
            try {
                n = Integer.parseInt(string2);
                bl = true;
                if (n != 1) {
                    bl = false;
                }
                return bl;
            }
            catch (NumberFormatException numberFormatException) {
                SubscriptionManager.logd("getBooleanSubscriptionProperty NumberFormat exception");
            }
        }
        return bl;
    }

    public static int getDefaultDataSubscriptionId() {
        int n;
        block3 : {
            int n2 = -1;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            n = n2;
            if (iSub == null) break block3;
            try {
                n = iSub.getDefaultDataSubId();
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    public static int getDefaultSmsSubscriptionId() {
        int n;
        block3 : {
            int n2 = -1;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            n = n2;
            if (iSub == null) break block3;
            try {
                n = iSub.getDefaultSmsSubId();
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    public static int getDefaultSubscriptionId() {
        int n;
        block3 : {
            int n2 = -1;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            n = n2;
            if (iSub == null) break block3;
            try {
                n = iSub.getDefaultSubId();
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    @UnsupportedAppUsage
    public static int getDefaultVoicePhoneId() {
        return SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultVoiceSubscriptionId());
    }

    public static int getDefaultVoiceSubscriptionId() {
        int n;
        block3 : {
            int n2 = -1;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            n = n2;
            if (iSub == null) break block3;
            try {
                n = iSub.getDefaultVoiceSubId();
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    public static int getIntegerSubscriptionProperty(int n, String string2, int n2, Context context) {
        if ((string2 = SubscriptionManager.getSubscriptionProperty(n, string2, context)) != null) {
            try {
                n = Integer.parseInt(string2);
                return n;
            }
            catch (NumberFormatException numberFormatException) {
                SubscriptionManager.logd("getBooleanSubscriptionProperty NumberFormat exception");
            }
        }
        return n2;
    }

    private final INetworkPolicyManager getNetworkPolicy() {
        if (this.mNetworkPolicy == null) {
            this.mNetworkPolicy = INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy"));
        }
        return this.mNetworkPolicy;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public static int getPhoneId(int n) {
        int n2;
        block4 : {
            if (!SubscriptionManager.isValidSubscriptionId(n)) {
                return -1;
            }
            int n3 = -1;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            n2 = n3;
            if (iSub == null) break block4;
            try {
                n2 = iSub.getPhoneId(n);
            }
            catch (RemoteException remoteException) {
                n2 = n3;
            }
        }
        return n2;
    }

    @UnsupportedAppUsage
    public static Resources getResourcesForSubId(Context context, int n) {
        return SubscriptionManager.getResourcesForSubId(context, n, false);
    }

    public static Resources getResourcesForSubId(Context context, int n, boolean bl) {
        Object object = SubscriptionManager.from(context).getActiveSubscriptionInfo(n);
        Object object2 = context.getResources().getConfiguration();
        Configuration configuration = new Configuration();
        configuration.setTo((Configuration)object2);
        if (object != null) {
            configuration.mcc = ((SubscriptionInfo)object).getMcc();
            configuration.mnc = ((SubscriptionInfo)object).getMnc();
            if (configuration.mnc == 0) {
                configuration.mnc = 65535;
            }
        }
        if (bl) {
            configuration.setLocale(Locale.ROOT);
        }
        object2 = context.getResources().getDisplayMetrics();
        object = new DisplayMetrics();
        ((DisplayMetrics)object).setTo((DisplayMetrics)object2);
        return new Resources(context.getResources().getAssets(), (DisplayMetrics)object, configuration);
    }

    public static int getSimStateForSlotIndex(int n) {
        int n2;
        block3 : {
            int n3 = 0;
            n2 = 0;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                n2 = iSub.getSimStateForSlotIndex(n);
            }
            catch (RemoteException remoteException) {
                n2 = n3;
            }
        }
        return n2;
    }

    public static int getSlotIndex(int n) {
        int n2;
        block3 : {
            SubscriptionManager.isValidSubscriptionId(n);
            int n3 = -1;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            n2 = n3;
            if (iSub == null) break block3;
            try {
                n2 = iSub.getSlotIndex(n);
            }
            catch (RemoteException remoteException) {
                n2 = n3;
            }
        }
        return n2;
    }

    @UnsupportedAppUsage
    public static int[] getSubId(int n) {
        int[] arrn;
        block4 : {
            if (!SubscriptionManager.isValidSlotIndex(n)) {
                SubscriptionManager.logd("[getSubId]- fail");
                return null;
            }
            int[] arrn2 = null;
            arrn = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block4;
            try {
                arrn = iSub.getSubId(n);
            }
            catch (RemoteException remoteException) {
                arrn = arrn2;
            }
        }
        return arrn;
    }

    private String getSubscriptionPlansOwner(int n) {
        try {
            String string2 = this.getNetworkPolicy().getSubscriptionPlansOwner(n);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private static String getSubscriptionProperty(int n, String string2, Context context) {
        String string3;
        block3 : {
            String string4 = null;
            string3 = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                string3 = iSub.getSubscriptionProperty(n, string2, context.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                string3 = string4;
            }
        }
        return string3;
    }

    public static Uri getUriForSubscriptionId(int n) {
        return Uri.withAppendedPath(CONTENT_URI, String.valueOf(n));
    }

    private boolean isSubscriptionVisible(SubscriptionInfo subscriptionInfo) {
        block4 : {
            boolean bl;
            block6 : {
                block5 : {
                    boolean bl2 = false;
                    if (subscriptionInfo == null) {
                        return false;
                    }
                    if (subscriptionInfo.getGroupUuid() == null || !subscriptionInfo.isOpportunistic()) break block4;
                    if (TelephonyManager.from(this.mContext).hasCarrierPrivileges(subscriptionInfo.getSubscriptionId())) break block5;
                    bl = bl2;
                    if (!subscriptionInfo.isEmbedded()) break block6;
                    bl = bl2;
                    if (!this.canManageSubscription(subscriptionInfo)) break block6;
                }
                bl = true;
            }
            return bl;
        }
        return true;
    }

    private boolean isSystemProcess() {
        boolean bl = Process.myUid() == 1000;
        return bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public static boolean isUsableSubIdValue(int n) {
        boolean bl = n >= 0 && n <= 2147483646;
        return bl;
    }

    public static boolean isUsableSubscriptionId(int n) {
        return SubscriptionManager.isUsableSubIdValue(n);
    }

    @UnsupportedAppUsage
    public static boolean isValidPhoneId(int n) {
        boolean bl = n >= 0 && n < TelephonyManager.getDefault().getPhoneCount();
        return bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static boolean isValidSlotIndex(int n) {
        boolean bl = n >= 0 && n < TelephonyManager.getDefault().getSimCount();
        return bl;
    }

    public static boolean isValidSubscriptionId(int n) {
        boolean bl = n > -1;
        return bl;
    }

    static /* synthetic */ int lambda$addSubscriptionsIntoGroup$7(Integer n) {
        return n;
    }

    static /* synthetic */ int lambda$createSubscriptionGroup$6(Integer n) {
        return n;
    }

    static /* synthetic */ int lambda$removeSubscriptionsFromGroup$8(Integer n) {
        return n;
    }

    static /* synthetic */ int lambda$setDataRoaming$4(int n, int n2, ISub iSub) throws RemoteException {
        return iSub.setDataRoaming(n, n2);
    }

    static /* synthetic */ int lambda$setDisplayName$2(String string2, int n, int n2, ISub iSub) throws RemoteException {
        return iSub.setDisplayNameUsingSrc(string2, n, n2);
    }

    static /* synthetic */ int lambda$setDisplayNumber$3(String string2, int n, ISub iSub) throws RemoteException {
        return iSub.setDisplayNumber(string2, n);
    }

    static /* synthetic */ int lambda$setIconTint$1(int n, int n2, ISub iSub) throws RemoteException {
        return iSub.setIconTint(n, n2);
    }

    private static void logd(String string2) {
        Rlog.d(LOG_TAG, string2);
    }

    private static void loge(String string2) {
        Rlog.e(LOG_TAG, string2);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public static void putPhoneIdAndSubIdExtra(Intent intent, int n) {
        int[] arrn = SubscriptionManager.getSubId(n);
        if (arrn != null && arrn.length > 0) {
            SubscriptionManager.putPhoneIdAndSubIdExtra(intent, n, arrn[0]);
        } else {
            SubscriptionManager.logd("putPhoneIdAndSubIdExtra: no valid subs");
            intent.putExtra("phone", n);
        }
    }

    @UnsupportedAppUsage
    public static void putPhoneIdAndSubIdExtra(Intent intent, int n, int n2) {
        intent.putExtra("subscription", n2);
        intent.putExtra(EXTRA_SUBSCRIPTION_INDEX, n2);
        intent.putExtra("phone", n);
    }

    public static void setSubscriptionProperty(int n, String string2, String string3) {
        block3 : {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                iSub.setSubscriptionProperty(n, string2, string3);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    private int setSubscriptionPropertyHelper(int n, String object, CallISubMethodHelper object2) {
        block4 : {
            if (!SubscriptionManager.isValidSubscriptionId(n)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("[");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append("]- fail");
                SubscriptionManager.logd(((StringBuilder)object2).toString());
                return -1;
            }
            int n2 = 0;
            n = 0;
            object = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (object == null) break block4;
            try {
                n = object2.callMethod((ISub)object);
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    public void addOnOpportunisticSubscriptionsChangedListener(Executor object, OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener) {
        if (object != null && onOpportunisticSubscriptionsChangedListener != null) {
            block4 : {
                Object object2 = this.mContext;
                object2 = object2 != null ? ((Context)object2).getOpPackageName() : "<unknown>";
                onOpportunisticSubscriptionsChangedListener.setExecutor((Executor)object);
                object = ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
                if (object == null) break block4;
                try {
                    object.addOnOpportunisticSubscriptionsChangedListener((String)object2, onOpportunisticSubscriptionsChangedListener.callback);
                }
                catch (RemoteException remoteException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Remote exception ITelephonyRegistry ");
                    ((StringBuilder)object).append(remoteException);
                    Log.e(LOG_TAG, ((StringBuilder)object).toString());
                }
            }
            return;
        }
    }

    public void addOnSubscriptionsChangedListener(OnSubscriptionsChangedListener object) {
        block3 : {
            Object object2 = this.mContext;
            object2 = object2 != null ? ((Context)object2).getOpPackageName() : "<unknown>";
            ITelephonyRegistry iTelephonyRegistry = ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
            if (iTelephonyRegistry == null) break block3;
            try {
                iTelephonyRegistry.addOnSubscriptionsChangedListener((String)object2, ((OnSubscriptionsChangedListener)object).callback);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Remote exception ITelephonyRegistry ");
                ((StringBuilder)object).append(remoteException);
                Log.e(LOG_TAG, ((StringBuilder)object).toString());
            }
        }
    }

    public Uri addSubscriptionInfoRecord(String string2, int n) {
        if (string2 == null) {
            SubscriptionManager.logd("[addSubscriptionInfoRecord]- null iccId");
        }
        if (!SubscriptionManager.isValidSlotIndex(n)) {
            SubscriptionManager.logd("[addSubscriptionInfoRecord]- invalid slotIndex");
        }
        this.addSubscriptionInfoRecord(string2, null, n, 0);
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addSubscriptionInfoRecord(String charSequence, String string2, int n, int n2) {
        if (charSequence == null) {
            Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- uniqueId is null");
            return;
        }
        try {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) {
                Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- ISub service is null");
                return;
            }
            if ((n = iSub.addSubInfo((String)charSequence, string2, n, n2)) < 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Adding of subscription didn't succeed: error = ");
                ((StringBuilder)charSequence).append(n);
                Log.e(LOG_TAG, ((StringBuilder)charSequence).toString());
                return;
            }
            SubscriptionManager.logd("successfully added new subscription");
            return;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addSubscriptionsIntoGroup(List<Integer> object, ParcelUuid object2) {
        Preconditions.checkNotNull(object, "subIdList can't be null.");
        Preconditions.checkNotNull(object2, "groupUuid can't be null.");
        Object object3 = this.mContext;
        object3 = object3 != null ? ((Context)object3).getOpPackageName() : "<unknown>";
        int[] arrn = object.stream().mapToInt(_$$Lambda$SubscriptionManager$D5_PmvQ13e0qLtSnBvNd4R7l2qA.INSTANCE).toArray();
        try {
            object = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (object != null) {
                object.addSubscriptionsIntoGroup(arrn, (ParcelUuid)object2, (String)object3);
                return;
            }
            if (this.isSystemProcess()) {
                return;
            }
            object = new IllegalStateException("telephony service is null.");
            throw object;
        }
        catch (RemoteException remoteException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("addSubscriptionsIntoGroup RemoteException ");
            ((StringBuilder)object2).append(remoteException);
            SubscriptionManager.loge(((StringBuilder)object2).toString());
            if (this.isSystemProcess()) return;
            remoteException.rethrowAsRuntimeException();
        }
    }

    public boolean allDefaultsSelected() {
        if (!SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getDefaultDataSubscriptionId())) {
            return false;
        }
        if (!SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getDefaultSmsSubscriptionId())) {
            return false;
        }
        return SubscriptionManager.isValidSubscriptionId(SubscriptionManager.getDefaultVoiceSubscriptionId());
    }

    public boolean canManageSubscription(SubscriptionInfo subscriptionInfo) {
        return this.canManageSubscription(subscriptionInfo, this.mContext.getPackageName());
    }

    public boolean canManageSubscription(SubscriptionInfo object, String string2) {
        if (((SubscriptionInfo)object).isEmbedded()) {
            if (((SubscriptionInfo)object).getAccessRules() == null) {
                return false;
            }
            Object object2 = this.mContext.getPackageManager();
            try {
                object2 = ((PackageManager)object2).getPackageInfo(string2, 64);
                object = ((SubscriptionInfo)object).getAccessRules().iterator();
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unknown package: ");
                ((StringBuilder)object2).append(string2);
                throw new IllegalArgumentException(((StringBuilder)object2).toString(), nameNotFoundException);
            }
            while (object.hasNext()) {
                if (((UiccAccessRule)object.next()).getCarrierPrivilegeStatus((PackageInfo)object2) != 1) continue;
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException("Not an embedded subscription");
    }

    public void clearSubscriptionInfo() {
        block3 : {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                iSub.clearSubInfo();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public Intent createManageSubscriptionIntent(int n) {
        String string2 = this.getSubscriptionPlansOwner(n);
        if (string2 == null) {
            return null;
        }
        if (this.getSubscriptionPlans(n).isEmpty()) {
            return null;
        }
        Intent intent = new Intent(ACTION_MANAGE_SUBSCRIPTION_PLANS);
        intent.setPackage(string2);
        intent.putExtra(EXTRA_SUBSCRIPTION_INDEX, n);
        if (this.mContext.getPackageManager().queryIntentActivities(intent, 65536).isEmpty()) {
            return null;
        }
        return intent;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ParcelUuid createSubscriptionGroup(List<Integer> object) {
        Preconditions.checkNotNull(object, "can't create group for null subId list");
        Object object2 = this.mContext;
        object2 = object2 != null ? ((Context)object2).getOpPackageName() : "<unknown>";
        Object var3_4 = null;
        Object var4_5 = null;
        object = object.stream().mapToInt(_$$Lambda$SubscriptionManager$W41XrJh1c8ZX_i9kWtj1rBU9l8o.INSTANCE).toArray();
        try {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub != null) {
                return iSub.createSubscriptionGroup((int[])object, (String)object2);
            }
            if (this.isSystemProcess()) {
                return var4_5;
            }
            object = new IllegalStateException("telephony service is null.");
            throw object;
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("createSubscriptionGroup RemoteException ");
            ((StringBuilder)object).append(remoteException);
            SubscriptionManager.loge(((StringBuilder)object).toString());
            object = var3_4;
            if (this.isSystemProcess()) return object;
            remoteException.rethrowAsRuntimeException();
            return var3_4;
        }
    }

    public List<SubscriptionInfo> getAccessibleSubscriptionInfoList() {
        List<SubscriptionInfo> list;
        block3 : {
            List<SubscriptionInfo> list2 = null;
            list = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                list = iSub.getAccessibleSubscriptionInfoList(this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                list = list2;
            }
        }
        return list;
    }

    @UnsupportedAppUsage
    public int[] getActiveSubscriptionIdList() {
        int[] arrn;
        int[] arrn2;
        block4 : {
            arrn2 = null;
            arrn = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block4;
            try {
                arrn = iSub.getActiveSubIdList(true);
            }
            catch (RemoteException remoteException) {
                arrn = arrn2;
            }
        }
        arrn2 = arrn;
        if (arrn == null) {
            arrn2 = new int[]{};
        }
        return arrn2;
    }

    public SubscriptionInfo getActiveSubscriptionInfo(int n) {
        SubscriptionInfo subscriptionInfo;
        block4 : {
            if (!SubscriptionManager.isValidSubscriptionId(n)) {
                return null;
            }
            SubscriptionInfo subscriptionInfo2 = null;
            subscriptionInfo = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block4;
            try {
                subscriptionInfo = iSub.getActiveSubscriptionInfo(n, this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                subscriptionInfo = subscriptionInfo2;
            }
        }
        return subscriptionInfo;
    }

    public int getActiveSubscriptionInfoCount() {
        int n;
        block3 : {
            int n2 = 0;
            n = 0;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                n = iSub.getActiveSubInfoCount(this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    public int getActiveSubscriptionInfoCountMax() {
        int n;
        block3 : {
            int n2 = 0;
            n = 0;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                n = iSub.getActiveSubInfoCountMax();
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    public SubscriptionInfo getActiveSubscriptionInfoForIccIndex(String string2) {
        SubscriptionInfo subscriptionInfo;
        block4 : {
            if (string2 == null) {
                SubscriptionManager.logd("[getActiveSubscriptionInfoForIccIndex]- null iccid");
                return null;
            }
            SubscriptionInfo subscriptionInfo2 = null;
            subscriptionInfo = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block4;
            try {
                subscriptionInfo = iSub.getActiveSubscriptionInfoForIccId(string2, this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                subscriptionInfo = subscriptionInfo2;
            }
        }
        return subscriptionInfo;
    }

    public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int n) {
        SubscriptionInfo subscriptionInfo;
        block4 : {
            if (!SubscriptionManager.isValidSlotIndex(n)) {
                SubscriptionManager.logd("[getActiveSubscriptionInfoForSimSlotIndex]- invalid slotIndex");
                return null;
            }
            SubscriptionInfo subscriptionInfo2 = null;
            subscriptionInfo = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block4;
            try {
                subscriptionInfo = iSub.getActiveSubscriptionInfoForSimSlotIndex(n, this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                subscriptionInfo = subscriptionInfo2;
            }
        }
        return subscriptionInfo;
    }

    public List<SubscriptionInfo> getActiveSubscriptionInfoList() {
        return this.getActiveSubscriptionInfoList(true);
    }

    public List<SubscriptionInfo> getActiveSubscriptionInfoList(boolean bl) {
        List<SubscriptionInfo> list;
        block4 : {
            List<SubscriptionInfo> list2 = null;
            list = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block4;
            try {
                list = iSub.getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                list = list2;
            }
        }
        if (bl && list != null) {
            return list.stream().filter(new _$$Lambda$SubscriptionManager$R_uORt9bKcmEo6JnjiGP2KgjIOQ(this)).collect(Collectors.toList());
        }
        return list;
    }

    @UnsupportedAppUsage
    public int getAllSubscriptionInfoCount() {
        int n;
        block3 : {
            int n2 = 0;
            n = 0;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                n = iSub.getAllSubInfoCount(this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    @UnsupportedAppUsage
    public List<SubscriptionInfo> getAllSubscriptionInfoList() {
        List<Object> list;
        ArrayList arrayList;
        block4 : {
            arrayList = null;
            list = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block4;
            try {
                list = iSub.getAllSubInfoList(this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                list = arrayList;
            }
        }
        arrayList = list;
        if (list == null) {
            arrayList = new ArrayList();
        }
        return arrayList;
    }

    @SystemApi
    public List<SubscriptionInfo> getAvailableSubscriptionInfoList() {
        List<SubscriptionInfo> list;
        block3 : {
            List<SubscriptionInfo> list2 = null;
            list = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                list = iSub.getAvailableSubscriptionInfoList(this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                list = list2;
            }
        }
        return list;
    }

    @UnsupportedAppUsage
    public int getDefaultDataPhoneId() {
        return SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
    }

    @UnsupportedAppUsage
    public SubscriptionInfo getDefaultDataSubscriptionInfo() {
        return this.getActiveSubscriptionInfo(SubscriptionManager.getDefaultDataSubscriptionId());
    }

    @UnsupportedAppUsage
    public int getDefaultSmsPhoneId() {
        return SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultSmsSubscriptionId());
    }

    public SubscriptionInfo getDefaultSmsSubscriptionInfo() {
        return this.getActiveSubscriptionInfo(SubscriptionManager.getDefaultSmsSubscriptionId());
    }

    @UnsupportedAppUsage
    public SubscriptionInfo getDefaultVoiceSubscriptionInfo() {
        return this.getActiveSubscriptionInfo(SubscriptionManager.getDefaultVoiceSubscriptionId());
    }

    @SystemApi
    public int getEnabledSubscriptionId(int n) {
        int n2;
        block3 : {
            int n3 = -1;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            n2 = n3;
            if (iSub == null) break block3;
            try {
                n2 = iSub.getEnabledSubscriptionId(n);
            }
            catch (RemoteException remoteException) {
                n2 = n3;
            }
        }
        return n2;
    }

    public List<SubscriptionInfo> getOpportunisticSubscriptions() {
        Object object;
        Object object2;
        block4 : {
            object = this.mContext;
            object2 = object != null ? ((Context)object).getOpPackageName() : "<unknown>";
            Object var3_4 = null;
            object = null;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block4;
            try {
                object = iSub.getOpportunisticSubscriptions((String)object2);
            }
            catch (RemoteException remoteException) {
                object = var3_4;
            }
        }
        object2 = object;
        if (object == null) {
            object2 = new ArrayList();
        }
        return object2;
    }

    public int getPreferredDataSubscriptionId() {
        int n;
        block3 : {
            int n2 = Integer.MAX_VALUE;
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            n = n2;
            if (iSub == null) break block3;
            try {
                n = iSub.getPreferredDataSubscriptionId();
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        return n;
    }

    public List<SubscriptionInfo> getSelectableSubscriptionInfoList() {
        List<SubscriptionInfo> list = this.getAvailableSubscriptionInfoList();
        if (list == null) {
            return null;
        }
        ArrayList<SubscriptionInfo> arrayList = new ArrayList<SubscriptionInfo>();
        HashMap<ParcelUuid, SubscriptionInfo> hashMap = new HashMap<ParcelUuid, SubscriptionInfo>();
        for (SubscriptionInfo subscriptionInfo : list) {
            if (!this.isSubscriptionVisible(subscriptionInfo)) continue;
            ParcelUuid parcelUuid = subscriptionInfo.getGroupUuid();
            if (parcelUuid == null) {
                arrayList.add(subscriptionInfo);
                continue;
            }
            if (hashMap.containsKey(parcelUuid) && (((SubscriptionInfo)hashMap.get(parcelUuid)).getSimSlotIndex() != -1 || subscriptionInfo.getSimSlotIndex() == -1)) continue;
            arrayList.remove(hashMap.get(parcelUuid));
            arrayList.add(subscriptionInfo);
            hashMap.put(parcelUuid, subscriptionInfo);
        }
        return arrayList;
    }

    public int[] getSubscriptionIds(int n) {
        return SubscriptionManager.getSubId(n);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<SubscriptionPlan> getSubscriptionPlans(int n) {
        try {
            void var2_5;
            SubscriptionPlan[] arrsubscriptionPlan = this.getNetworkPolicy().getSubscriptionPlans(n, this.mContext.getOpPackageName());
            if (arrsubscriptionPlan == null) {
                List list = Collections.emptyList();
                return var2_5;
            }
            List<SubscriptionPlan> list = Arrays.asList(arrsubscriptionPlan);
            return var2_5;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid object) {
        Preconditions.checkNotNull(object, "groupUuid can't be null");
        Object object2 = this.mContext;
        object2 = object2 != null ? ((Context)object2).getOpPackageName() : "<unknown>";
        Object var3_4 = null;
        Object var4_5 = null;
        try {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub != null) {
                return iSub.getSubscriptionsInGroup((ParcelUuid)object, (String)object2);
            }
            if (this.isSystemProcess()) {
                return var4_5;
            }
            object = new IllegalStateException("telephony service is null.");
            throw object;
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("removeSubscriptionsFromGroup RemoteException ");
            ((StringBuilder)object).append(remoteException);
            SubscriptionManager.loge(((StringBuilder)object).toString());
            object = var3_4;
            if (this.isSystemProcess()) return object;
            remoteException.rethrowAsRuntimeException();
            return var3_4;
        }
    }

    @UnsupportedAppUsage
    public boolean isActiveSubId(int n) {
        block3 : {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                boolean bl = iSub.isActiveSubId(n, this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    public boolean isActiveSubscriptionId(int n) {
        return this.isActiveSubId(n);
    }

    public boolean isNetworkRoaming(int n) {
        if (SubscriptionManager.getPhoneId(n) < 0) {
            return false;
        }
        return TelephonyManager.getDefault().isNetworkRoaming(n);
    }

    @SystemApi
    public boolean isSubscriptionEnabled(int n) {
        block3 : {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                boolean bl = iSub.isSubscriptionEnabled(n);
                return bl;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    public boolean isSubscriptionPlansRefreshSupported(int n) {
        boolean bl = this.createRefreshSubscriptionIntent(n) != null;
        return bl;
    }

    public /* synthetic */ boolean lambda$getActiveSubscriptionInfoList$0$SubscriptionManager(SubscriptionInfo subscriptionInfo) {
        return this.isSubscriptionVisible(subscriptionInfo);
    }

    public /* synthetic */ int lambda$setOpportunistic$5$SubscriptionManager(boolean bl, int n, ISub iSub) throws RemoteException {
        return iSub.setOpportunistic(bl, n, this.mContext.getOpPackageName());
    }

    public void removeOnOpportunisticSubscriptionsChangedListener(OnOpportunisticSubscriptionsChangedListener object) {
        block3 : {
            Preconditions.checkNotNull(object, "listener cannot be null");
            Object object2 = this.mContext;
            object2 = object2 != null ? ((Context)object2).getOpPackageName() : "<unknown>";
            ITelephonyRegistry iTelephonyRegistry = ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
            if (iTelephonyRegistry == null) break block3;
            try {
                iTelephonyRegistry.removeOnSubscriptionsChangedListener((String)object2, ((OnOpportunisticSubscriptionsChangedListener)object).callback);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Remote exception ITelephonyRegistry ");
                ((StringBuilder)object).append(remoteException);
                Log.e(LOG_TAG, ((StringBuilder)object).toString());
            }
        }
    }

    public void removeOnSubscriptionsChangedListener(OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
        block3 : {
            Object object = this.mContext;
            object = object != null ? ((Context)object).getOpPackageName() : "<unknown>";
            ITelephonyRegistry iTelephonyRegistry = ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
            if (iTelephonyRegistry == null) break block3;
            try {
                iTelephonyRegistry.removeOnSubscriptionsChangedListener((String)object, onSubscriptionsChangedListener.callback);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Remote exception ITelephonyRegistry ");
                ((StringBuilder)object).append(remoteException);
                Log.e(LOG_TAG, ((StringBuilder)object).toString());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeSubscriptionInfoRecord(String charSequence, int n) {
        if (charSequence == null) {
            Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- uniqueId is null");
            return;
        }
        try {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) {
                Log.e(LOG_TAG, "[removeSubscriptionInfoRecord]- ISub service is null");
                return;
            }
            if ((n = iSub.removeSubInfo((String)charSequence, n)) < 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Removal of subscription didn't succeed: error = ");
                ((StringBuilder)charSequence).append(n);
                Log.e(LOG_TAG, ((StringBuilder)charSequence).toString());
                return;
            }
            SubscriptionManager.logd("successfully removed subscription");
            return;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeSubscriptionsFromGroup(List<Integer> object, ParcelUuid object2) {
        StringBuilder stringBuilder;
        Preconditions.checkNotNull(object, "subIdList can't be null.");
        Preconditions.checkNotNull(stringBuilder, "groupUuid can't be null.");
        Object object3 = this.mContext;
        object3 = object3 != null ? ((Context)object3).getOpPackageName() : "<unknown>";
        int[] arrn = object.stream().mapToInt(_$$Lambda$SubscriptionManager$EEe2NsDpuDogw8_UijVBhj7Vuhk.INSTANCE).toArray();
        try {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub != null) {
                iSub.removeSubscriptionsFromGroup(arrn, (ParcelUuid)((Object)stringBuilder), (String)object3);
                return;
            }
            if (this.isSystemProcess()) {
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("telephony service is null.");
            throw illegalStateException;
        }
        catch (RemoteException remoteException) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("removeSubscriptionsFromGroup RemoteException ");
            stringBuilder.append(remoteException);
            SubscriptionManager.loge(stringBuilder.toString());
            if (this.isSystemProcess()) return;
            remoteException.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void requestEmbeddedSubscriptionInfoListRefresh() {
        block3 : {
            int n = TelephonyManager.from(this.mContext).getCardIdForDefaultEuicc();
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                iSub.requestEmbeddedSubscriptionInfoListRefresh(n);
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("requestEmbeddedSubscriptionInfoListFresh for card = ");
                stringBuilder.append(n);
                stringBuilder.append(" failed.");
                SubscriptionManager.logd(stringBuilder.toString());
            }
        }
    }

    @SystemApi
    public void requestEmbeddedSubscriptionInfoListRefresh(int n) {
        block3 : {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                iSub.requestEmbeddedSubscriptionInfoListRefresh(n);
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("requestEmbeddedSubscriptionInfoListFresh for card = ");
                stringBuilder.append(n);
                stringBuilder.append(" failed.");
                SubscriptionManager.logd(stringBuilder.toString());
            }
        }
    }

    public void requestSubscriptionPlansRefresh(int n) {
        Intent intent = this.createRefreshSubscriptionIntent(n);
        BroadcastOptions broadcastOptions = BroadcastOptions.makeBasic();
        broadcastOptions.setTemporaryAppWhitelistDuration(TimeUnit.MINUTES.toMillis(1L));
        this.mContext.sendBroadcast(intent, null, broadcastOptions.toBundle());
    }

    public boolean setAlwaysAllowMmsData(int n, boolean bl) {
        block3 : {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                bl = iSub.setAlwaysAllowMmsData(n, bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                if (this.isSystemProcess()) break block3;
                remoteException.rethrowAsRuntimeException();
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public int setDataRoaming(int n, int n2) {
        return this.setSubscriptionPropertyHelper(n2, "setDataRoaming", new _$$Lambda$SubscriptionManager$xw48SQFFAHLgpsIZZWeq63fMykw(n, n2));
    }

    @SystemApi
    public void setDefaultDataSubId(int n) {
        block3 : {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                iSub.setDefaultDataSubId(n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @SystemApi
    public void setDefaultSmsSubId(int n) {
        block3 : {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                iSub.setDefaultSmsSubId(n);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setDefaultVoiceSubId(int n) {
        block3 : {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                iSub.setDefaultVoiceSubId(n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @UnsupportedAppUsage
    public int setDisplayName(String string2, int n, int n2) {
        return this.setSubscriptionPropertyHelper(n, "setDisplayName", new _$$Lambda$SubscriptionManager$OS3WICha4HbZhTnWrKCxeu6dr6g(string2, n, n2));
    }

    @UnsupportedAppUsage
    public int setDisplayNumber(String string2, int n) {
        if (string2 == null) {
            SubscriptionManager.logd("[setDisplayNumber]- fail");
            return -1;
        }
        return this.setSubscriptionPropertyHelper(n, "setDisplayNumber", new _$$Lambda$SubscriptionManager$3ws2BzXOcyDc_7TPZx2HIBCIjbs(string2, n));
    }

    @UnsupportedAppUsage
    public int setIconTint(int n, int n2) {
        return this.setSubscriptionPropertyHelper(n2, "setIconTint", new _$$Lambda$SubscriptionManager$RmtPOPFQV3mOx5HejDzImseJ0Qg(n, n2));
    }

    public boolean setOpportunistic(boolean bl, int n) {
        n = this.setSubscriptionPropertyHelper(n, "setOpportunistic", new _$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY__L174e3u4tk(this, bl, n));
        bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    @SystemApi
    public void setPreferredDataSubscriptionId(int n, boolean bl, final Executor executor, final Consumer<Integer> consumer) {
        ISub iSub;
        block3 : {
            iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub != null) break block3;
            return;
        }
        try {
            ISetOpportunisticDataCallback.Stub stub = new ISetOpportunisticDataCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(Consumer consumer2, int n) {
                    consumer2.accept(n);
                }

                static /* synthetic */ void lambda$onComplete$1(Executor executor2, Consumer consumer2, int n) throws Exception {
                    executor2.execute(new _$$Lambda$SubscriptionManager$1$oi86t06gqdSgTtWgRmCc5dJIfEs(consumer2, n));
                }

                @Override
                public void onComplete(int n) {
                    Consumer consumer2;
                    Executor executor2 = executor;
                    if (executor2 != null && (consumer2 = consumer) != null) {
                        Binder.withCleanCallingIdentity(new _$$Lambda$SubscriptionManager$1$qFZ_q9KyfPAkHTrQPCRyO6OQ_pc(executor2, consumer2, n));
                        return;
                    }
                }
            };
            iSub.setPreferredDataSubscriptionId(n, bl, stub);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @SystemApi
    public boolean setSubscriptionEnabled(int n, boolean bl) {
        block3 : {
            ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
            if (iSub == null) break block3;
            try {
                bl = iSub.setSubscriptionEnabled(bl, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    public void setSubscriptionOverrideCongested(int n, boolean bl, long l) {
        int n2 = bl ? 2 : 0;
        try {
            this.getNetworkPolicy().setSubscriptionOverride(n, 2, n2, l, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setSubscriptionOverrideUnmetered(int n, boolean bl, long l) {
        int n2 = bl ? 1 : 0;
        try {
            this.getNetworkPolicy().setSubscriptionOverride(n, 1, n2, l, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setSubscriptionPlans(int n, List<SubscriptionPlan> list) {
        try {
            this.getNetworkPolicy().setSubscriptionPlans(n, list.toArray(new SubscriptionPlan[list.size()]), this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void switchToSubscription(int n, PendingIntent pendingIntent) {
        Preconditions.checkNotNull(pendingIntent, "callbackIntent cannot be null");
        new EuiccManager(this.mContext).switchToSubscription(n, pendingIntent);
    }

    private static interface CallISubMethodHelper {
        public int callMethod(ISub var1) throws RemoteException;
    }

    public static class OnOpportunisticSubscriptionsChangedListener {
        IOnSubscriptionsChangedListener callback = new IOnSubscriptionsChangedListener.Stub(){

            public /* synthetic */ void lambda$onSubscriptionsChanged$0$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1() {
                OnOpportunisticSubscriptionsChangedListener.this.onOpportunisticSubscriptionsChanged();
            }

            @Override
            public void onSubscriptionsChanged() {
                long l = Binder.clearCallingIdentity();
                try {
                    Executor executor = OnOpportunisticSubscriptionsChangedListener.this.mExecutor;
                    _$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E _$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E = new _$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E(this);
                    executor.execute(_$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E);
                    return;
                }
                finally {
                    Binder.restoreCallingIdentity(l);
                }
            }
        };
        private Executor mExecutor;

        private void log(String string2) {
            Rlog.d(SubscriptionManager.LOG_TAG, string2);
        }

        private void setExecutor(Executor executor) {
            this.mExecutor = executor;
        }

        public void onOpportunisticSubscriptionsChanged() {
        }

    }

    public static class OnSubscriptionsChangedListener {
        IOnSubscriptionsChangedListener callback = new IOnSubscriptionsChangedListener.Stub(){

            @Override
            public void onSubscriptionsChanged() {
                OnSubscriptionsChangedListener.this.mHandler.sendEmptyMessage(0);
            }
        };
        private final Handler mHandler;

        public OnSubscriptionsChangedListener() {
            this.mHandler = new OnSubscriptionsChangedListenerHandler();
        }

        public OnSubscriptionsChangedListener(Looper looper) {
            this.mHandler = new OnSubscriptionsChangedListenerHandler(looper);
        }

        private void log(String string2) {
            Rlog.d(SubscriptionManager.LOG_TAG, string2);
        }

        public void onSubscriptionsChanged() {
        }

        private class OnSubscriptionsChangedListenerHandler
        extends Handler {
            OnSubscriptionsChangedListenerHandler() {
            }

            OnSubscriptionsChangedListenerHandler(Looper looper) {
                super(looper);
            }

            @Override
            public void handleMessage(Message message) {
                OnSubscriptionsChangedListener.this.onSubscriptionsChanged();
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProfileClass {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SubscriptionType {
    }

}

