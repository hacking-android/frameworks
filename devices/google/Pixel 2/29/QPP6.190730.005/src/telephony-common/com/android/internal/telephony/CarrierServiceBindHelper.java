/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.ServiceConnection
 *  android.content.pm.ComponentInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Process
 *  android.os.SystemClock
 *  android.os.UserHandle
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.Log
 *  com.android.internal.content.PackageMonitor
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.os.UserHandle;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.content.PackageMonitor;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class CarrierServiceBindHelper {
    private static final int EVENT_PERFORM_IMMEDIATE_UNBIND = 1;
    private static final int EVENT_REBIND = 0;
    private static final String LOG_TAG = "CarrierSvcBindHelper";
    private static final int UNBIND_DELAY_MILLIS = 30000;
    private AppBinding[] mBindings;
    @UnsupportedAppUsage
    private Context mContext;
    @UnsupportedAppUsage
    private Handler mHandler = new Handler(){

        public void handleMessage(Message object) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("mHandler: ");
            ((StringBuilder)object2).append(((Message)object).what);
            CarrierServiceBindHelper.log(((StringBuilder)object2).toString());
            int n = ((Message)object).what;
            if (n != 0) {
                if (n == 1) {
                    ((AppBinding)((Message)object).obj).performImmediateUnbind();
                }
            } else {
                object2 = (AppBinding)((Message)object).obj;
                object = new StringBuilder();
                ((StringBuilder)object).append("Rebinding if necessary for phoneId: ");
                ((StringBuilder)object).append(((AppBinding)object2).getPhoneId());
                CarrierServiceBindHelper.log(((StringBuilder)object).toString());
                ((AppBinding)object2).rebind();
            }
        }
    };
    private String[] mLastSimState;
    private final PackageMonitor mPackageMonitor = new CarrierServicePackageMonitor();
    private BroadcastReceiver mUserUnlockedReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent object2) {
            object2 = object2.getAction();
            object = new StringBuilder();
            ((StringBuilder)object).append("Received ");
            ((StringBuilder)object).append((String)object2);
            CarrierServiceBindHelper.log(((StringBuilder)object).toString());
            if ("android.intent.action.USER_UNLOCKED".equals(object2)) {
                for (int i = 0; i < CarrierServiceBindHelper.this.mBindings.length; ++i) {
                    CarrierServiceBindHelper.this.mBindings[i].rebind();
                }
            }
        }
    };

    public CarrierServiceBindHelper(Context context) {
        this.mContext = context;
        int n = TelephonyManager.from((Context)context).getPhoneCount();
        this.mBindings = new AppBinding[n];
        this.mLastSimState = new String[n];
        for (int i = 0; i < n; ++i) {
            this.mBindings[i] = new AppBinding(i);
        }
        this.mPackageMonitor.register(context, this.mHandler.getLooper(), UserHandle.ALL, false);
        this.mContext.registerReceiverAsUser(this.mUserUnlockedReceiver, UserHandle.SYSTEM, new IntentFilter("android.intent.action.USER_UNLOCKED"), null, this.mHandler);
    }

    private static void log(String string) {
        Log.d((String)LOG_TAG, (String)string);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("CarrierServiceBindHelper:");
        AppBinding[] arrappBinding = this.mBindings;
        int n = arrappBinding.length;
        for (int i = 0; i < n; ++i) {
            arrappBinding[i].dump(fileDescriptor, printWriter, arrstring);
        }
    }

    void updateForPhoneId(int n, String string) {
        String[] arrstring = new StringBuilder();
        arrstring.append("update binding for phoneId: ");
        arrstring.append(n);
        arrstring.append(" simState: ");
        arrstring.append(string);
        CarrierServiceBindHelper.log(arrstring.toString());
        if (!SubscriptionManager.isValidPhoneId((int)n)) {
            return;
        }
        if (!TextUtils.isEmpty((CharSequence)string) && n < (arrstring = this.mLastSimState).length) {
            if (string.equals(arrstring[n])) {
                return;
            }
            this.mLastSimState[n] = string;
            string = this.mHandler;
            string.sendMessage(string.obtainMessage(0, (Object)this.mBindings[n]));
            return;
        }
    }

    private class AppBinding {
        private int bindCount;
        private String carrierPackage;
        private String carrierServiceClass;
        private CarrierServiceConnection connection;
        private long lastBindStartMillis;
        private long lastUnbindMillis;
        private long mUnbindScheduledUptimeMillis = -1L;
        private int phoneId;
        private int unbindCount;

        public AppBinding(int n) {
            this.phoneId = n;
        }

        private void cancelScheduledUnbind() {
            CarrierServiceBindHelper.this.mHandler.removeMessages(1);
            this.mUnbindScheduledUptimeMillis = -1L;
        }

        private void performImmediateUnbind() {
            ++this.unbindCount;
            this.lastUnbindMillis = System.currentTimeMillis();
            this.carrierPackage = null;
            this.carrierServiceClass = null;
            CarrierServiceBindHelper.log("Unbinding from carrier app");
            CarrierServiceBindHelper.this.mContext.unbindService((ServiceConnection)this.connection);
            this.connection = null;
            this.mUnbindScheduledUptimeMillis = -1L;
        }

        public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Carrier app binding for phone ");
            ((StringBuilder)object).append(this.phoneId);
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("  connection: ");
            ((StringBuilder)object).append(this.connection);
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("  bindCount: ");
            ((StringBuilder)object).append(this.bindCount);
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("  lastBindStartMillis: ");
            ((StringBuilder)object).append(this.lastBindStartMillis);
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("  unbindCount: ");
            ((StringBuilder)object).append(this.unbindCount);
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("  lastUnbindMillis: ");
            ((StringBuilder)object).append(this.lastUnbindMillis);
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("  mUnbindScheduledUptimeMillis: ");
            ((StringBuilder)object).append(this.mUnbindScheduledUptimeMillis);
            printWriter.println(((StringBuilder)object).toString());
            printWriter.println();
        }

        public String getPackage() {
            return this.carrierPackage;
        }

        public int getPhoneId() {
            return this.phoneId;
        }

        void rebind() {
            Object object = TelephonyManager.from((Context)CarrierServiceBindHelper.this.mContext).getCarrierPackageNamesForIntentAndPhone(new Intent("android.service.carrier.CarrierService"), this.phoneId);
            if (object != null && object.size() > 0) {
                CharSequence charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Found carrier app: ");
                ((StringBuilder)charSequence).append(object);
                CarrierServiceBindHelper.log(((StringBuilder)charSequence).toString());
                String string = (String)object.get(0);
                if (!TextUtils.equals((CharSequence)this.carrierPackage, (CharSequence)string)) {
                    this.unbind(true);
                }
                Intent intent = new Intent("android.service.carrier.CarrierService");
                intent.setPackage(string);
                charSequence = CarrierServiceBindHelper.this.mContext.getPackageManager().resolveService(intent, 128);
                if (charSequence != null) {
                    object = charSequence.serviceInfo.metaData;
                    charSequence = charSequence.getComponentInfo().getComponentName().getClassName();
                } else {
                    object = null;
                    charSequence = null;
                }
                if (object != null && object.getBoolean("android.service.carrier.LONG_LIVED_BINDING", false)) {
                    if (!TextUtils.equals((CharSequence)this.carrierServiceClass, (CharSequence)charSequence)) {
                        this.unbind(true);
                    } else if (this.connection != null) {
                        this.cancelScheduledUnbind();
                        return;
                    }
                    this.carrierPackage = string;
                    this.carrierServiceClass = charSequence;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Binding to ");
                    ((StringBuilder)charSequence).append(this.carrierPackage);
                    ((StringBuilder)charSequence).append(" for phone ");
                    ((StringBuilder)charSequence).append(this.phoneId);
                    CarrierServiceBindHelper.log(((StringBuilder)charSequence).toString());
                    ++this.bindCount;
                    this.lastBindStartMillis = System.currentTimeMillis();
                    this.connection = new CarrierServiceConnection();
                    try {
                        if (CarrierServiceBindHelper.this.mContext.bindServiceAsUser(intent, (ServiceConnection)this.connection, 67108865, CarrierServiceBindHelper.this.mHandler, Process.myUserHandle())) {
                            return;
                        }
                        charSequence = "bindService returned false";
                    }
                    catch (SecurityException securityException) {
                        charSequence = securityException.getMessage();
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unable to bind to ");
                    ((StringBuilder)object).append(this.carrierPackage);
                    ((StringBuilder)object).append(" for phone ");
                    ((StringBuilder)object).append(this.phoneId);
                    ((StringBuilder)object).append(". Error: ");
                    ((StringBuilder)object).append((String)charSequence);
                    CarrierServiceBindHelper.log(((StringBuilder)object).toString());
                    this.unbind(true);
                    return;
                }
                CarrierServiceBindHelper.log("Carrier app does not want a long lived binding");
                this.unbind(true);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No carrier app for: ");
            stringBuilder.append(this.phoneId);
            CarrierServiceBindHelper.log(stringBuilder.toString());
            this.unbind(false);
        }

        void unbind(boolean bl) {
            CarrierServiceConnection carrierServiceConnection = this.connection;
            if (carrierServiceConnection == null) {
                return;
            }
            if (!bl && carrierServiceConnection.connected) {
                if (this.mUnbindScheduledUptimeMillis == -1L) {
                    this.mUnbindScheduledUptimeMillis = 30000L + SystemClock.uptimeMillis();
                    CarrierServiceBindHelper.log("Scheduling unbind in 30000 millis");
                    CarrierServiceBindHelper.this.mHandler.sendMessageAtTime(CarrierServiceBindHelper.this.mHandler.obtainMessage(1, (Object)this), this.mUnbindScheduledUptimeMillis);
                }
            } else {
                this.cancelScheduledUnbind();
                this.performImmediateUnbind();
            }
        }
    }

    private class CarrierServiceConnection
    implements ServiceConnection {
        private boolean connected;

        private CarrierServiceConnection() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder object) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Connected to carrier app: ");
            ((StringBuilder)object).append(componentName.flattenToString());
            CarrierServiceBindHelper.log(((StringBuilder)object).toString());
            this.connected = true;
        }

        public void onServiceDisconnected(ComponentName componentName) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Disconnected from carrier app: ");
            stringBuilder.append(componentName.flattenToString());
            CarrierServiceBindHelper.log(stringBuilder.toString());
            this.connected = false;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CarrierServiceConnection[connected=");
            stringBuilder.append(this.connected);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private class CarrierServicePackageMonitor
    extends PackageMonitor {
        private CarrierServicePackageMonitor() {
        }

        private void evaluateBinding(String string, boolean bl) {
            for (AppBinding appBinding : CarrierServiceBindHelper.this.mBindings) {
                String string2 = appBinding.getPackage();
                boolean bl2 = string.equals(string2);
                if (bl2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string);
                    stringBuilder.append(" changed and corresponds to a phone. Rebinding.");
                    CarrierServiceBindHelper.log(stringBuilder.toString());
                }
                if (string2 != null && !bl2) continue;
                if (bl) {
                    appBinding.unbind(true);
                }
                appBinding.rebind();
            }
        }

        public boolean onHandleForceStop(Intent intent, String[] arrstring, int n, boolean bl) {
            if (bl) {
                int n2 = arrstring.length;
                for (int i = 0; i < n2; ++i) {
                    this.evaluateBinding(arrstring[i], true);
                }
            }
            return super.onHandleForceStop(intent, arrstring, n, bl);
        }

        public void onPackageAdded(String string, int n) {
            this.evaluateBinding(string, true);
        }

        public void onPackageModified(String string) {
            this.evaluateBinding(string, false);
        }

        public void onPackageRemoved(String string, int n) {
            this.evaluateBinding(string, true);
        }

        public void onPackageUpdateFinished(String string, int n) {
            this.evaluateBinding(string, true);
        }
    }

}

