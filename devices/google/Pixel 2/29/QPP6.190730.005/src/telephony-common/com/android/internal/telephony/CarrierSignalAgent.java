/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.PackageManager
 *  android.net.ConnectivityManager
 *  android.net.ConnectivityManager$NetworkCallback
 *  android.net.Network
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.UserHandle
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  com.android.internal.util.ArrayUtils
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.text.TextUtils;
import android.util.LocalLog;
import com.android.internal.telephony.CarrierActionAgent;
import com.android.internal.telephony.Phone;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CarrierSignalAgent
extends Handler {
    private static final String CARRIER_SIGNAL_DELIMITER = "\\s*,\\s*";
    private static final String COMPONENT_NAME_DELIMITER = "\\s*:\\s*";
    private static final boolean DBG = true;
    private static final int EVENT_REGISTER_DEFAULT_NETWORK_AVAIL = 0;
    private static final String LOG_TAG = CarrierSignalAgent.class.getSimpleName();
    private static final boolean NO_WAKE = false;
    private static final boolean VDBG = Rlog.isLoggable((String)LOG_TAG, (int)2);
    private static final boolean WAKE = true;
    private Map<String, Set<ComponentName>> mCachedNoWakeSignalConfigs = new HashMap<String, Set<ComponentName>>();
    private Map<String, Set<ComponentName>> mCachedWakeSignalConfigs = new HashMap<String, Set<ComponentName>>();
    private final Set<String> mCarrierSignalList = new HashSet<String>(Arrays.asList("com.android.internal.telephony.CARRIER_SIGNAL_PCO_VALUE", "com.android.internal.telephony.CARRIER_SIGNAL_REDIRECTED", "com.android.internal.telephony.CARRIER_SIGNAL_REQUEST_NETWORK_FAILED", "com.android.internal.telephony.CARRIER_SIGNAL_RESET", "com.android.internal.telephony.CARRIER_SIGNAL_DEFAULT_NETWORK_AVAILABLE"));
    private boolean mDefaultNetworkAvail;
    private final LocalLog mErrorLocalLog = new LocalLog(20);
    private ConnectivityManager.NetworkCallback mNetworkCallback;
    private final Phone mPhone;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent object2) {
            object2 = object2.getAction();
            object = CarrierSignalAgent.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CarrierSignalAgent receiver action: ");
            stringBuilder.append((String)object2);
            ((CarrierSignalAgent)((Object)object)).log(stringBuilder.toString());
            if (((String)object2).equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                CarrierSignalAgent.this.loadCarrierConfig();
            }
        }
    };

    public CarrierSignalAgent(Phone phone) {
        this.mPhone = phone;
        this.loadCarrierConfig();
        this.mPhone.getContext().registerReceiver(this.mReceiver, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"));
        this.mPhone.getCarrierActionAgent().registerForCarrierAction(3, this, 0, null, false);
    }

    private void broadcast(Intent intent, Set<ComponentName> object, boolean bl) {
        PackageManager packageManager = this.mPhone.getContext().getPackageManager();
        Iterator<ComponentName> iterator = object.iterator();
        while (iterator.hasNext()) {
            Object object2;
            block8 : {
                object2 = iterator.next();
                object = new Intent(intent);
                object.setComponent((ComponentName)object2);
                if (bl && packageManager.queryBroadcastReceivers((Intent)object, 65536).isEmpty()) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Carrier signal receivers are configured but unavailable: ");
                    ((StringBuilder)object2).append((Object)object.getComponent());
                    this.loge(((StringBuilder)object2).toString());
                    continue;
                }
                if (!bl && !packageManager.queryBroadcastReceivers((Intent)object, 65536).isEmpty()) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Runtime signals shouldn't be configured in Manifest: ");
                    ((StringBuilder)object2).append((Object)object.getComponent());
                    this.loge(((StringBuilder)object2).toString());
                    continue;
                }
                object.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", this.mPhone.getSubId());
                object.putExtra("subscription", this.mPhone.getSubId());
                object.addFlags(268435456);
                if (!bl) {
                    object.setFlags(16);
                }
                this.mPhone.getContext().sendBroadcastAsUser((Intent)object, UserHandle.ALL);
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Sending signal ");
                ((StringBuilder)object2).append(object.getAction());
                if (object.getComponent() != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" to the carrier signal receiver: ");
                    stringBuilder.append((Object)object.getComponent());
                    object = stringBuilder.toString();
                    break block8;
                }
                object = "";
            }
            try {
                ((StringBuilder)object2).append((String)object);
                this.log(((StringBuilder)object2).toString());
            }
            catch (ActivityNotFoundException activityNotFoundException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Send broadcast failed: ");
                ((StringBuilder)object).append((Object)activityNotFoundException);
                this.loge(((StringBuilder)object).toString());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadCarrierConfig() {
        Object object = (CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config");
        Object object2 = null;
        if (object != null) {
            object2 = object.getConfig();
        }
        if (object2 == null) return;
        object = this.mCachedWakeSignalConfigs;
        synchronized (object) {
            this.log("Loading carrier config: carrier_app_wake_signal_config");
            Map<String, Set<ComponentName>> map = this.parseAndCache(object2.getStringArray("carrier_app_wake_signal_config"));
            if (!this.mCachedWakeSignalConfigs.isEmpty() && !map.equals(this.mCachedWakeSignalConfigs)) {
                if (VDBG) {
                    this.log("carrier config changed, reset receivers from old config");
                }
                this.mPhone.getCarrierActionAgent().sendEmptyMessage(2);
            }
            this.mCachedWakeSignalConfigs = map;
        }
        object = this.mCachedNoWakeSignalConfigs;
        synchronized (object) {
            this.log("Loading carrier config: carrier_app_no_wake_signal_config");
            object2 = this.parseAndCache(object2.getStringArray("carrier_app_no_wake_signal_config"));
            if (!this.mCachedNoWakeSignalConfigs.isEmpty() && !object2.equals(this.mCachedNoWakeSignalConfigs)) {
                if (VDBG) {
                    this.log("carrier config changed, reset receivers from old config");
                }
                this.mPhone.getCarrierActionAgent().sendEmptyMessage(2);
            }
            this.mCachedNoWakeSignalConfigs = object2;
            return;
        }
    }

    private void log(String string) {
        String string2 = LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("]");
        stringBuilder.append(string);
        Rlog.d((String)string2, (String)stringBuilder.toString());
    }

    private void loge(String string) {
        this.mErrorLocalLog.log(string);
        String string2 = LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("]");
        stringBuilder.append(string);
        Rlog.e((String)string2, (String)stringBuilder.toString());
    }

    private void logv(String string) {
        String string2 = LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("]");
        stringBuilder.append(string);
        Rlog.v((String)string2, (String)stringBuilder.toString());
    }

    private Map<String, Set<ComponentName>> parseAndCache(String[] arrstring) {
        HashMap<String, Set<ComponentName>> hashMap = new HashMap<String, Set<ComponentName>>();
        if (!ArrayUtils.isEmpty((Object[])arrstring)) {
            for (String string : arrstring) {
                if (TextUtils.isEmpty((CharSequence)string)) continue;
                Object object = string.trim().split(COMPONENT_NAME_DELIMITER, 2);
                if (((String[])object).length == 2) {
                    ComponentName componentName = ComponentName.unflattenFromString((String)object[0]);
                    if (componentName == null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid component name: ");
                        stringBuilder.append((String)object[0]);
                        this.loge(stringBuilder.toString());
                        continue;
                    }
                    for (String string2 : ((String)object[1]).split(CARRIER_SIGNAL_DELIMITER)) {
                        if (!this.mCarrierSignalList.contains(string2)) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Invalid signal name: ");
                            ((StringBuilder)object).append(string2);
                            this.loge(((StringBuilder)object).toString());
                            continue;
                        }
                        Set set = (Set)hashMap.get(string2);
                        object = set;
                        if (set == null) {
                            object = new HashSet();
                            hashMap.put(string2, (Set<ComponentName>)object);
                        }
                        object.add(componentName);
                        if (!VDBG) continue;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Add config {signal: ");
                        ((StringBuilder)object).append(string2);
                        ((StringBuilder)object).append(" componentName: ");
                        ((StringBuilder)object).append((Object)componentName);
                        ((StringBuilder)object).append("}");
                        this.logv(((StringBuilder)object).toString());
                    }
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("invalid config format: ");
                ((StringBuilder)object).append(string);
                this.loge(((StringBuilder)object).toString());
            }
        }
        return hashMap;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        StringBuilder stringBuilder;
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)printWriter, "  ");
        printWriter.println("mCachedWakeSignalConfigs:");
        indentingPrintWriter.increaseIndent();
        for (Map.Entry<String, Set<ComponentName>> entry : this.mCachedWakeSignalConfigs.entrySet()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("signal: ");
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" componentName list: ");
            stringBuilder.append(entry.getValue());
            printWriter.println(stringBuilder.toString());
        }
        indentingPrintWriter.decreaseIndent();
        printWriter.println("mCachedNoWakeSignalConfigs:");
        indentingPrintWriter.increaseIndent();
        for (Map.Entry<String, Set<ComponentName>> entry : this.mCachedNoWakeSignalConfigs.entrySet()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("signal: ");
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" componentName list: ");
            stringBuilder.append(entry.getValue());
            printWriter.println(stringBuilder.toString());
        }
        indentingPrintWriter.decreaseIndent();
        stringBuilder = new StringBuilder();
        stringBuilder.append("mDefaultNetworkAvail: ");
        stringBuilder.append(this.mDefaultNetworkAvail);
        printWriter.println(stringBuilder.toString());
        printWriter.println("error log:");
        indentingPrintWriter.increaseIndent();
        this.mErrorLocalLog.dump(fileDescriptor, printWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
    }

    public void handleMessage(Message message) {
        if (message.what == 0) {
            message = (AsyncResult)message.obj;
            if (message.exception != null) {
                String string = LOG_TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Register default network exception: ");
                stringBuilder.append(message.exception);
                Rlog.e((String)string, (String)stringBuilder.toString());
                return;
            }
            ConnectivityManager connectivityManager = ConnectivityManager.from((Context)this.mPhone.getContext());
            if (((Boolean)message.result).booleanValue()) {
                this.mNetworkCallback = new ConnectivityManager.NetworkCallback(){

                    public void onAvailable(Network network) {
                        if (!CarrierSignalAgent.this.mDefaultNetworkAvail) {
                            CarrierSignalAgent carrierSignalAgent = CarrierSignalAgent.this;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Default network available: ");
                            stringBuilder.append((Object)network);
                            carrierSignalAgent.log(stringBuilder.toString());
                            network = new Intent("com.android.internal.telephony.CARRIER_SIGNAL_DEFAULT_NETWORK_AVAILABLE");
                            network.putExtra("defaultNetworkAvailable", true);
                            CarrierSignalAgent.this.notifyCarrierSignalReceivers((Intent)network);
                            CarrierSignalAgent.this.mDefaultNetworkAvail = true;
                        }
                    }

                    public void onLost(Network network) {
                        CarrierSignalAgent carrierSignalAgent = CarrierSignalAgent.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Default network lost: ");
                        stringBuilder.append((Object)network);
                        carrierSignalAgent.log(stringBuilder.toString());
                        network = new Intent("com.android.internal.telephony.CARRIER_SIGNAL_DEFAULT_NETWORK_AVAILABLE");
                        network.putExtra("defaultNetworkAvailable", false);
                        CarrierSignalAgent.this.notifyCarrierSignalReceivers((Intent)network);
                        CarrierSignalAgent.this.mDefaultNetworkAvail = false;
                    }
                };
                connectivityManager.registerDefaultNetworkCallback(this.mNetworkCallback, (Handler)this.mPhone);
                this.log("Register default network");
            } else {
                message = this.mNetworkCallback;
                if (message != null) {
                    connectivityManager.unregisterNetworkCallback((ConnectivityManager.NetworkCallback)message);
                    this.mNetworkCallback = null;
                    this.mDefaultNetworkAvail = false;
                    this.log("unregister default network");
                }
            }
        }
    }

    public boolean hasRegisteredReceivers(String string) {
        boolean bl = this.mCachedWakeSignalConfigs.containsKey(string) || this.mCachedNoWakeSignalConfigs.containsKey(string);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyCarrierSignalReceivers(Intent intent) {
        Set<ComponentName> set;
        Map<String, Set<ComponentName>> map = this.mCachedWakeSignalConfigs;
        synchronized (map) {
            set = this.mCachedWakeSignalConfigs.get(intent.getAction());
            if (!ArrayUtils.isEmpty(set)) {
                this.broadcast(intent, set, true);
            }
        }
        map = this.mCachedNoWakeSignalConfigs;
        synchronized (map) {
            set = this.mCachedNoWakeSignalConfigs.get(intent.getAction());
            if (!ArrayUtils.isEmpty(set)) {
                this.broadcast(intent, set, false);
            }
            return;
        }
    }

}

