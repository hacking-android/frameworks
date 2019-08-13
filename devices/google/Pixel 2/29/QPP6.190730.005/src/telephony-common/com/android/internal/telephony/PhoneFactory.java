/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.net.LocalServerSocket
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.ServiceManager
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Settings$SettingNotFoundException
 *  android.telephony.AnomalyReporter
 *  android.telephony.Rlog
 *  android.telephony.TelephonyManager
 *  android.util.LocalLog
 *  com.android.internal.os.BackgroundThread
 *  com.android.internal.telephony.ITelephonyRegistry
 *  com.android.internal.telephony.ITelephonyRegistry$Stub
 *  com.android.internal.telephony.RILConstants
 *  com.android.internal.telephony.SmsApplication
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.LocalServerSocket;
import android.os.IBinder;
import android.os.Looper;
import android.os.ServiceManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.AnomalyReporter;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.util.LocalLog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.telephony.CellularNetworkValidator;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.DefaultPhoneNotifier;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.IntentBroadcaster;
import com.android.internal.telephony.MultiSimSettingController;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConfigurationManager;
import com.android.internal.telephony.PhoneNotifier;
import com.android.internal.telephony.PhoneSwitcher;
import com.android.internal.telephony.ProxyController;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony.RILConstants;
import com.android.internal.telephony.SmsApplication;
import com.android.internal.telephony.SmsController;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.SubscriptionInfoUpdater;
import com.android.internal.telephony.SubscriptionMonitor;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.TelephonyDevController;
import com.android.internal.telephony.cdma.CdmaSubscriptionSourceManager;
import com.android.internal.telephony.dataconnection.TelephonyNetworkFactory;
import com.android.internal.telephony.euicc.EuiccCardController;
import com.android.internal.telephony.euicc.EuiccController;
import com.android.internal.telephony.ims.ImsResolver;
import com.android.internal.telephony.imsphone.ImsPhoneFactory;
import com.android.internal.telephony.sip.SipPhone;
import com.android.internal.telephony.sip.SipPhoneFactory;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.util.NotificationChannelController;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PhoneFactory {
    static final boolean DBG = false;
    static final String LOG_TAG = "PhoneFactory";
    static final int SOCKET_OPEN_MAX_RETRY = 3;
    static final int SOCKET_OPEN_RETRY_MILLIS = 2000;
    private static CellularNetworkValidator sCellularNetworkValidator;
    @UnsupportedAppUsage
    private static CommandsInterface sCommandsInterface;
    private static CommandsInterface[] sCommandsInterfaces;
    @UnsupportedAppUsage
    private static Context sContext;
    private static EuiccCardController sEuiccCardController;
    private static EuiccController sEuiccController;
    private static ImsResolver sImsResolver;
    private static IntentBroadcaster sIntentBroadcaster;
    private static final HashMap<String, LocalLog> sLocalLogs;
    static final Object sLockProxyPhones;
    @UnsupportedAppUsage
    private static boolean sMadeDefaults;
    private static NotificationChannelController sNotificationChannelController;
    private static Phone sPhone;
    private static PhoneConfigurationManager sPhoneConfigurationManager;
    @UnsupportedAppUsage
    private static PhoneNotifier sPhoneNotifier;
    private static PhoneSwitcher sPhoneSwitcher;
    private static Phone[] sPhones;
    private static ProxyController sProxyController;
    private static SubscriptionInfoUpdater sSubInfoRecordUpdater;
    private static SubscriptionMonitor sSubscriptionMonitor;
    private static TelephonyNetworkFactory[] sTelephonyNetworkFactories;
    private static UiccController sUiccController;

    static {
        sLockProxyPhones = new Object();
        sPhones = null;
        sPhone = null;
        sCommandsInterfaces = null;
        sCommandsInterface = null;
        sSubInfoRecordUpdater = null;
        sMadeDefaults = false;
        sLocalLogs = new HashMap();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void addLocalLog(String string, int n) {
        HashMap<String, LocalLog> hashMap = sLocalLogs;
        synchronized (hashMap) {
            if (!sLocalLogs.containsKey(string)) {
                HashMap<String, LocalLog> hashMap2 = sLocalLogs;
                LocalLog localLog = new LocalLog(n);
                hashMap2.put(string, localLog);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("key ");
            stringBuilder.append(string);
            stringBuilder.append(" already present");
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
    }

    @UnsupportedAppUsage
    public static int calculatePreferredNetworkType(Context context, int n) {
        Object object = context.getContentResolver();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("preferred_network_mode");
        stringBuilder.append(n);
        int n2 = Settings.Global.getInt((ContentResolver)object, (String)stringBuilder.toString(), (int)-1);
        object = new StringBuilder();
        ((StringBuilder)object).append("calculatePreferredNetworkType: phoneSubId = ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" networkType = ");
        ((StringBuilder)object).append(n2);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        int n3 = n2;
        if (n2 == -1) {
            n3 = RILConstants.PREFERRED_NETWORK_MODE;
            try {
                n3 = n = TelephonyManager.getIntAtIndex((ContentResolver)context.getContentResolver(), (String)"preferred_network_mode", (int)SubscriptionController.getInstance().getPhoneId(n));
            }
            catch (Settings.SettingNotFoundException settingNotFoundException) {
                Rlog.e((String)LOG_TAG, (String)"Settings Exception Reading Value At Index for Settings.Global.PREFERRED_NETWORK_MODE");
            }
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        Object object3;
        printWriter = new IndentingPrintWriter((Writer)printWriter, "  ");
        printWriter.println("PhoneFactory:");
        Object object22 = new StringBuilder();
        object22.append(" sMadeDefaults=");
        object22.append(sMadeDefaults);
        printWriter.println(object22.toString());
        sPhoneSwitcher.dump(fileDescriptor, printWriter, arrstring);
        printWriter.println();
        object22 = PhoneFactory.getPhones();
        for (int i = 0; i < ((Phone[])object22).length; ++i) {
            printWriter.increaseIndent();
            object3 = object22[i];
            try {
                ((Phone)object3).dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Telephony DebugService: Could not get Phone[");
                ((StringBuilder)object3).append(i);
                ((StringBuilder)object3).append("] e=");
                ((StringBuilder)object3).append(exception);
                printWriter.println(((StringBuilder)object3).toString());
            }
            printWriter.flush();
            printWriter.println("++++++++++++++++++++++++++++++++");
            sTelephonyNetworkFactories[i].dump(fileDescriptor, printWriter, arrstring);
            printWriter.flush();
            printWriter.decreaseIndent();
            printWriter.println("++++++++++++++++++++++++++++++++");
            continue;
        }
        printWriter.println("SubscriptionMonitor:");
        printWriter.increaseIndent();
        try {
            sSubscriptionMonitor.dump(fileDescriptor, printWriter, arrstring);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        printWriter.decreaseIndent();
        printWriter.println("++++++++++++++++++++++++++++++++");
        printWriter.println("UiccController:");
        printWriter.increaseIndent();
        try {
            sUiccController.dump(fileDescriptor, printWriter, arrstring);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        printWriter.flush();
        printWriter.decreaseIndent();
        printWriter.println("++++++++++++++++++++++++++++++++");
        if (sEuiccController != null) {
            printWriter.println("EuiccController:");
            printWriter.increaseIndent();
            try {
                sEuiccController.dump(fileDescriptor, printWriter, arrstring);
                sEuiccCardController.dump(fileDescriptor, printWriter, arrstring);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            printWriter.flush();
            printWriter.decreaseIndent();
            printWriter.println("++++++++++++++++++++++++++++++++");
        }
        printWriter.println("SubscriptionController:");
        printWriter.increaseIndent();
        try {
            SubscriptionController.getInstance().dump(fileDescriptor, printWriter, arrstring);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        printWriter.flush();
        printWriter.decreaseIndent();
        printWriter.println("++++++++++++++++++++++++++++++++");
        printWriter.println("SubInfoRecordUpdater:");
        printWriter.increaseIndent();
        try {
            sSubInfoRecordUpdater.dump(fileDescriptor, printWriter, arrstring);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        printWriter.flush();
        printWriter.decreaseIndent();
        printWriter.println("++++++++++++++++++++++++++++++++");
        printWriter.println("LocalLogs:");
        printWriter.increaseIndent();
        object22 = sLocalLogs;
        synchronized (object22) {
            for (Object object3 : sLocalLogs.keySet()) {
                printWriter.println((String)object3);
                printWriter.increaseIndent();
                sLocalLogs.get(object3).dump(fileDescriptor, printWriter, arrstring);
                printWriter.decreaseIndent();
            }
            printWriter.flush();
        }
        printWriter.decreaseIndent();
        printWriter.println("++++++++++++++++++++++++++++++++");
        printWriter.println("SharedPreferences:");
        printWriter.increaseIndent();
        try {
            if (sContext != null) {
                Map map = PreferenceManager.getDefaultSharedPreferences((Context)sContext).getAll();
                for (Object object22 : map.keySet()) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(object22);
                    ((StringBuilder)object3).append(" : ");
                    ((StringBuilder)object3).append(map.get(object22));
                    printWriter.println(((StringBuilder)object3).toString());
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        printWriter.decreaseIndent();
        printWriter.println("++++++++++++++++++++++++++++++++");
        printWriter.println("DebugEvents:");
        printWriter.increaseIndent();
        try {
            AnomalyReporter.dump((FileDescriptor)fileDescriptor, (PrintWriter)printWriter, (String[])arrstring);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        printWriter.flush();
        printWriter.decreaseIndent();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static Phone getDefaultPhone() {
        Object object = sLockProxyPhones;
        synchronized (object) {
            if (sMadeDefaults) {
                return sPhone;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Default phones haven't been made yet!");
            throw illegalStateException;
        }
    }

    @UnsupportedAppUsage
    public static int getDefaultSubscription() {
        return SubscriptionController.getInstance().getDefaultSubId();
    }

    public static ImsResolver getImsResolver() {
        return sImsResolver;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static TelephonyNetworkFactory getNetworkFactory(int n) {
        Object object = sLockProxyPhones;
        synchronized (object) {
            if (!sMadeDefaults) {
                IllegalStateException illegalStateException = new IllegalStateException("Default phones haven't been made yet!");
                throw illegalStateException;
            }
            if (n == Integer.MAX_VALUE) {
                n = sPhone.getSubId();
            }
            if (sTelephonyNetworkFactories == null) return null;
            if (n < 0) return null;
            if (n >= sTelephonyNetworkFactories.length) return null;
            return sTelephonyNetworkFactories[n];
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static Phone getPhone(int n) {
        Object object = sLockProxyPhones;
        synchronized (object) {
            if (!sMadeDefaults) {
                IllegalStateException illegalStateException = new IllegalStateException("Default phones haven't been made yet!");
                throw illegalStateException;
            }
            if (n == Integer.MAX_VALUE) {
                return sPhone;
            }
            if (n < 0) return null;
            if (n >= sPhones.length) return null;
            return sPhones[n];
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static Phone[] getPhones() {
        Object object = sLockProxyPhones;
        synchronized (object) {
            if (sMadeDefaults) {
                return sPhones;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Default phones haven't been made yet!");
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SmsController getSmsController() {
        Object object = sLockProxyPhones;
        synchronized (object) {
            if (sMadeDefaults) {
                return sProxyController.getSmsController();
            }
            IllegalStateException illegalStateException = new IllegalStateException("Default phones haven't been made yet!");
            throw illegalStateException;
        }
    }

    public static SubscriptionInfoUpdater getSubscriptionInfoUpdater() {
        return sSubInfoRecordUpdater;
    }

    public static boolean isSMSPromptEnabled() {
        int n;
        int n2 = 0;
        try {
            n = Settings.Global.getInt((ContentResolver)sContext.getContentResolver(), (String)"multi_sim_sms_prompt");
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            Rlog.e((String)LOG_TAG, (String)"Settings Exception Reading Dual Sim SMS Prompt Values");
            n = n2;
        }
        boolean bl = n != 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SMS Prompt option:");
        stringBuilder.append(bl);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void localLog(String string, String object) {
        HashMap<String, LocalLog> hashMap = sLocalLogs;
        synchronized (hashMap) {
            if (sLocalLogs.containsKey(string)) {
                sLocalLogs.get(string).log((String)object);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("key ");
            stringBuilder.append(string);
            stringBuilder.append(" not found");
            object = new IllegalArgumentException(stringBuilder.toString());
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void makeDefaultPhone(Context object) {
        Object object2 = sLockProxyPhones;
        synchronized (object2) {
            if (!sMadeDefaults) {
                Object object3;
                int n;
                Object object4;
                Object object5;
                sContext = object;
                TelephonyDevController.create();
                int n2 = 0;
                do {
                    n = 0;
                    int n3 = n2 + 1;
                    try {
                        new android.net.LocalServerSocket("com.android.internal.telephony");
                        n2 = n;
                    }
                    catch (IOException iOException) {
                        n2 = 1;
                    }
                    if (n2 == 0) {
                        object4 = new DefaultPhoneNotifier();
                        sPhoneNotifier = object4;
                        n3 = CdmaSubscriptionSourceManager.getDefault(object);
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("Cdma Subscription set to ");
                        ((StringBuilder)object4).append(n3);
                        Rlog.i((String)LOG_TAG, (String)((StringBuilder)object4).toString());
                        n = ((TelephonyManager)object.getSystemService("phone")).getPhoneCount();
                        object3 = new int[n];
                        sPhones = new Phone[n];
                        sCommandsInterfaces = new RIL[n];
                        sTelephonyNetworkFactories = new TelephonyNetworkFactory[n];
                        for (n2 = 0; n2 < n; ++n2) {
                            object3[n2] = RILConstants.PREFERRED_NETWORK_MODE;
                            object4 = new StringBuilder();
                            ((StringBuilder)object4).append("Network Mode set to ");
                            ((StringBuilder)object4).append(Integer.toString(object3[n2]));
                            Rlog.i((String)LOG_TAG, (String)((StringBuilder)object4).toString());
                            PhoneFactory.sCommandsInterfaces[n2] = new RIL((Context)object, object3[n2], n3, n2);
                        }
                        sUiccController = UiccController.make(object, sCommandsInterfaces);
                        Rlog.i((String)LOG_TAG, (String)"Creating SubscriptionController");
                        SubscriptionController.init(object, sCommandsInterfaces);
                        MultiSimSettingController.init(object, SubscriptionController.getInstance());
                        if (object.getPackageManager().hasSystemFeature("android.hardware.telephony.euicc")) {
                            sEuiccController = EuiccController.init(object);
                            sEuiccCardController = EuiccCardController.init(object);
                        }
                        break;
                    }
                    if (n3 > 3) {
                        object = new RuntimeException("PhoneFactory probably already running");
                        throw object;
                    }
                    try {
                        Thread.sleep(2000L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    n2 = n3;
                } while (true);
                for (n2 = 0; n2 < n; ++n2) {
                    object4 = null;
                    int n4 = TelephonyManager.getPhoneType((int)object3[n2]);
                    if (n4 == 1) {
                        object4 = new GsmCdmaPhone((Context)object, sCommandsInterfaces[n2], sPhoneNotifier, n2, 1, TelephonyComponentFactory.getInstance());
                    } else if (n4 == 2) {
                        object4 = new GsmCdmaPhone((Context)object, sCommandsInterfaces[n2], sPhoneNotifier, n2, 6, TelephonyComponentFactory.getInstance());
                    }
                    object5 = new StringBuilder();
                    object5.append("Creating Phone with type = ");
                    object5.append(n4);
                    object5.append(" sub = ");
                    object5.append(n2);
                    Rlog.i((String)LOG_TAG, (String)object5.toString());
                    PhoneFactory.sPhones[n2] = object4;
                }
                if (n > 0) {
                    sPhone = sPhones[0];
                    sCommandsInterface = sCommandsInterfaces[0];
                }
                object3 = SmsApplication.getDefaultSmsApplication((Context)object, (boolean)true);
                object4 = "NONE";
                if (object3 != null) {
                    object4 = object3.getPackageName();
                }
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("defaultSmsApplication: ");
                ((StringBuilder)object3).append((String)object4);
                Rlog.i((String)LOG_TAG, (String)((StringBuilder)object3).toString());
                SmsApplication.initSmsPackageMonitor((Context)object);
                sMadeDefaults = true;
                Rlog.i((String)LOG_TAG, (String)"Creating SubInfoRecordUpdater ");
                object4 = new SubscriptionInfoUpdater(BackgroundThread.get().getLooper(), (Context)object, sPhones, sCommandsInterfaces);
                sSubInfoRecordUpdater = object4;
                SubscriptionController.getInstance().updatePhonesAvailability(sPhones);
                if (object.getPackageManager().hasSystemFeature("android.hardware.telephony.ims")) {
                    boolean bl = sContext.getResources().getBoolean(17891428);
                    object4 = sContext.getResources().getString(17039742);
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("ImsResolver: defaultImsPackage: ");
                    ((StringBuilder)object3).append((String)object4);
                    Rlog.i((String)LOG_TAG, (String)((StringBuilder)object3).toString());
                    sImsResolver = object3 = new ImsResolver(sContext, (String)object4, n, bl);
                    sImsResolver.initPopulateCacheAndStartBind();
                    for (n2 = 0; n2 < n; ++n2) {
                        sPhones[n2].startMonitoringImsService();
                    }
                } else {
                    Rlog.i((String)LOG_TAG, (String)"IMS is not supported on this device, skipping ImsResolver.");
                }
                object3 = ITelephonyRegistry.Stub.asInterface((IBinder)ServiceManager.getService((String)"telephony.registry"));
                object5 = SubscriptionController.getInstance();
                object4 = new SubscriptionMonitor((ITelephonyRegistry)object3, sContext, (SubscriptionController)((Object)object5), n);
                sSubscriptionMonitor = object4;
                sPhoneConfigurationManager = PhoneConfigurationManager.init(sContext);
                sCellularNetworkValidator = CellularNetworkValidator.make(sContext);
                sPhoneSwitcher = PhoneSwitcher.make(sPhoneConfigurationManager.getNumberOfModemsWithSimultaneousDataConnections(), n, sContext, (SubscriptionController)((Object)object5), Looper.myLooper(), (ITelephonyRegistry)object3, sCommandsInterfaces, sPhones);
                sProxyController = ProxyController.getInstance(object, sPhones, sUiccController, sCommandsInterfaces, sPhoneSwitcher);
                sIntentBroadcaster = IntentBroadcaster.getInstance(object);
                object4 = new NotificationChannelController((Context)object);
                sNotificationChannelController = object4;
                sTelephonyNetworkFactories = new TelephonyNetworkFactory[n];
                for (n2 = 0; n2 < n; ++n2) {
                    PhoneFactory.sTelephonyNetworkFactories[n2] = new TelephonyNetworkFactory(sSubscriptionMonitor, Looper.myLooper(), sPhones[n2]);
                }
            }
            return;
        }
    }

    public static void makeDefaultPhones(Context context) {
        PhoneFactory.makeDefaultPhone(context);
    }

    public static Phone makeImsPhone(PhoneNotifier phoneNotifier, Phone phone) {
        return ImsPhoneFactory.makePhone(sContext, phoneNotifier, phone);
    }

    public static SipPhone makeSipPhone(String string) {
        return SipPhoneFactory.makePhone(string, sContext, sPhoneNotifier);
    }

    public static void requestEmbeddedSubscriptionInfoListRefresh(int n, Runnable runnable) {
        sSubInfoRecordUpdater.requestEmbeddedSubscriptionInfoListRefresh(n, runnable);
    }
}

