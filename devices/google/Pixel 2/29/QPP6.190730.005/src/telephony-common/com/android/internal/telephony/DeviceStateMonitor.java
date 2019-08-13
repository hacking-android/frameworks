/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.hardware.display.DisplayManager
 *  android.hardware.display.DisplayManager$DisplayListener
 *  android.net.ConnectivityManager
 *  android.net.ConnectivityManager$NetworkCallback
 *  android.net.Network
 *  android.net.NetworkRequest
 *  android.net.NetworkRequest$Builder
 *  android.os.BatteryManager
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PowerManager
 *  android.util.LocalLog
 *  android.util.SparseIntArray
 *  android.view.Display
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.LocalLog;
import android.util.SparseIntArray;
import android.view.Display;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DeviceStateMonitor
extends Handler {
    @VisibleForTesting
    static final int CELL_INFO_INTERVAL_LONG_MS = 10000;
    @VisibleForTesting
    static final int CELL_INFO_INTERVAL_SHORT_MS = 2000;
    protected static final boolean DBG = false;
    @VisibleForTesting
    static final int EVENT_CHARGING_STATE_CHANGED = 4;
    static final int EVENT_POWER_SAVE_MODE_CHANGED = 3;
    static final int EVENT_RADIO_AVAILABLE = 6;
    static final int EVENT_RIL_CONNECTED = 0;
    @VisibleForTesting
    static final int EVENT_SCREEN_STATE_CHANGED = 2;
    static final int EVENT_TETHERING_STATE_CHANGED = 5;
    static final int EVENT_UPDATE_MODE_CHANGED = 1;
    @VisibleForTesting
    static final int EVENT_WIFI_CONNECTION_CHANGED = 7;
    private static final int HYSTERESIS_KBPS = 50;
    private static final int[] LINK_CAPACITY_DOWNLINK_THRESHOLDS;
    private static final int[] LINK_CAPACITY_UPLINK_THRESHOLDS;
    protected static final String TAG;
    private static final int WIFI_AVAILABLE = 1;
    private static final int WIFI_UNAVAILABLE = 0;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public void onReceive(Context var1_1, Intent var2_2) {
            block15 : {
                var1_1 = DeviceStateMonitor.this;
                var3_3 = new StringBuilder();
                var3_3.append("received: ");
                var3_3.append(var2_2);
                var3_3 = var3_3.toString();
                var4_4 = 1;
                DeviceStateMonitor.access$000((DeviceStateMonitor)var1_1, (String)var3_3, true);
                var1_1 = var2_2.getAction();
                switch (var1_1.hashCode()) {
                    case 1779291251: {
                        if (!var1_1.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) break;
                        var5_5 = 0;
                        break block15;
                    }
                    case 948344062: {
                        if (!var1_1.equals("android.os.action.CHARGING")) break;
                        var5_5 = 1;
                        break block15;
                    }
                    case -54942926: {
                        if (!var1_1.equals("android.os.action.DISCHARGING")) break;
                        var5_5 = 2;
                        break block15;
                    }
                    case -1754841973: {
                        if (!var1_1.equals("android.net.conn.TETHER_STATE_CHANGED")) break;
                        var5_5 = 3;
                        break block15;
                    }
                }
                ** break;
lbl29: // 1 sources:
                var5_5 = -1;
            }
            var1_1 = "on";
            if (var5_5 != 0) {
                if (var5_5 != 1) {
                    if (var5_5 != 2) {
                        if (var5_5 != 3) {
                            var3_3 = DeviceStateMonitor.this;
                            var1_1 = new StringBuilder();
                            var1_1.append("Unexpected broadcast intent: ");
                            var1_1.append(var2_2);
                            DeviceStateMonitor.access$000((DeviceStateMonitor)var3_3, var1_1.toString(), false);
                            return;
                        }
                        var5_5 = (var2_2 = var2_2.getStringArrayListExtra("tetherArray")) != null && var2_2.size() > 0 ? 1 : 0;
                        var3_3 = DeviceStateMonitor.this;
                        var2_2 = new StringBuilder();
                        var2_2.append("Tethering ");
                        if (var5_5 == 0) {
                            var1_1 = "off";
                        }
                        var2_2.append((String)var1_1);
                        DeviceStateMonitor.access$000((DeviceStateMonitor)var3_3, var2_2.toString(), true);
                        var1_1 = DeviceStateMonitor.this.obtainMessage(5);
                        var5_5 = var5_5 != 0 ? var4_4 : 0;
                        var1_1.arg1 = var5_5;
                    } else {
                        var1_1 = DeviceStateMonitor.this.obtainMessage(4);
                        var1_1.arg1 = 0;
                    }
                } else {
                    var1_1 = DeviceStateMonitor.this.obtainMessage(4);
                    var1_1.arg1 = 1;
                }
            } else {
                var2_2 = DeviceStateMonitor.this.obtainMessage(3);
                var2_2.arg1 = DeviceStateMonitor.access$200(DeviceStateMonitor.this) ? 1 : 0;
                var6_6 = DeviceStateMonitor.this;
                var3_3 = new StringBuilder();
                var3_3.append("Power Save mode ");
                if (var2_2.arg1 != 1) {
                    var1_1 = "off";
                }
                var3_3.append((String)var1_1);
                DeviceStateMonitor.access$000(var6_6, var3_3.toString(), true);
                var1_1 = var2_2;
            }
            DeviceStateMonitor.this.sendMessage((Message)var1_1);
        }
    };
    private int mCellInfoMinInterval = 2000;
    private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener(){

        public void onDisplayAdded(int n) {
        }

        public void onDisplayChanged(int n) {
            n = DeviceStateMonitor.this.isScreenOn() ? 1 : 0;
            Message message = DeviceStateMonitor.this.obtainMessage(2);
            message.arg1 = n;
            DeviceStateMonitor.this.sendMessage(message);
        }

        public void onDisplayRemoved(int n) {
        }
    };
    private boolean mIsCharging;
    private boolean mIsLowDataExpected;
    private boolean mIsPowerSaveOn;
    private boolean mIsScreenOn;
    private boolean mIsTetheringOn;
    private boolean mIsWifiConnected;
    private final LocalLog mLocalLog = new LocalLog(100);
    private final ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback(){
        Set<Network> mWifiNetworks = new HashSet<Network>();

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onAvailable(Network network) {
            Set<Network> set = this.mWifiNetworks;
            synchronized (set) {
                if (this.mWifiNetworks.size() == 0) {
                    DeviceStateMonitor.this.obtainMessage(7, 1, 0).sendToTarget();
                    DeviceStateMonitor.this.log("Wifi (default) connected", true);
                }
                this.mWifiNetworks.add(network);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onLost(Network network) {
            Set<Network> set = this.mWifiNetworks;
            synchronized (set) {
                this.mWifiNetworks.remove((Object)network);
                if (this.mWifiNetworks.size() == 0) {
                    DeviceStateMonitor.this.obtainMessage(7, 0, 0).sendToTarget();
                    DeviceStateMonitor.this.log("Wifi (default) disconnected", true);
                }
                return;
            }
        }
    };
    private final Phone mPhone;
    private int mUnsolicitedResponseFilter = -1;
    private SparseIntArray mUpdateModes = new SparseIntArray();
    private final NetworkRequest mWifiNetworkRequest = new NetworkRequest.Builder().addTransportType(1).addCapability(12).removeCapability(13).build();

    static {
        TAG = DeviceStateMonitor.class.getSimpleName();
        LINK_CAPACITY_DOWNLINK_THRESHOLDS = new int[]{100, 500, 1000, 5000, 10000, 20000, 50000, 100000, 200000, 500000, 1000000};
        LINK_CAPACITY_UPLINK_THRESHOLDS = new int[]{100, 500, 1000, 5000, 10000, 20000, 50000, 100000, 200000};
    }

    public DeviceStateMonitor(Phone phone) {
        this.mPhone = phone;
        ((DisplayManager)phone.getContext().getSystemService("display")).registerDisplayListener(this.mDisplayListener, null);
        this.mIsPowerSaveOn = this.isPowerSaveModeOn();
        this.mIsCharging = this.isDeviceCharging();
        this.mIsScreenOn = this.isScreenOn();
        this.mIsTetheringOn = false;
        this.mIsLowDataExpected = false;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DeviceStateMonitor mIsPowerSaveOn=");
        stringBuilder.append(this.mIsPowerSaveOn);
        stringBuilder.append(",mIsScreenOn=");
        stringBuilder.append(this.mIsScreenOn);
        stringBuilder.append(",mIsCharging=");
        stringBuilder.append(this.mIsCharging);
        this.log(stringBuilder.toString(), false);
        stringBuilder = new IntentFilter();
        stringBuilder.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        stringBuilder.addAction("android.os.action.CHARGING");
        stringBuilder.addAction("android.os.action.DISCHARGING");
        stringBuilder.addAction("android.net.conn.TETHER_STATE_CHANGED");
        this.mPhone.getContext().registerReceiver(this.mBroadcastReceiver, (IntentFilter)stringBuilder, null, (Handler)this.mPhone);
        this.mPhone.mCi.registerForRilConnected(this, 0, null);
        this.mPhone.mCi.registerForAvailable(this, 6, null);
        ((ConnectivityManager)phone.getContext().getSystemService("connectivity")).registerNetworkCallback(this.mWifiNetworkRequest, this.mNetworkCallback);
    }

    static /* synthetic */ boolean access$200(DeviceStateMonitor deviceStateMonitor) {
        return deviceStateMonitor.isPowerSaveModeOn();
    }

    private String deviceTypeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return "UNKNOWN";
                }
                return "LOW_DATA_EXPECTED";
            }
            return "CHARGING_STATE";
        }
        return "POWER_SAVE_MODE";
    }

    private boolean isDeviceCharging() {
        return ((BatteryManager)this.mPhone.getContext().getSystemService("batterymanager")).isCharging();
    }

    private boolean isLowDataExpected() {
        boolean bl = !this.mIsCharging && !this.mIsTetheringOn && !this.mIsScreenOn;
        return bl;
    }

    private boolean isPowerSaveModeOn() {
        return ((PowerManager)this.mPhone.getContext().getSystemService("power")).isPowerSaveMode();
    }

    private boolean isScreenOn() {
        Object object = ((DisplayManager)this.mPhone.getContext().getSystemService("display")).getDisplays();
        if (object != null) {
            int n = ((Display[])object).length;
            for (int i = 0; i < n; ++i) {
                Display display = object[i];
                if (display.getState() != 2) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("Screen ");
                ((StringBuilder)object).append(Display.typeToString((int)display.getType()));
                ((StringBuilder)object).append(" on");
                this.log(((StringBuilder)object).toString(), true);
                return true;
            }
            this.log("Screens all off", true);
            return false;
        }
        this.log("No displays found", true);
        return false;
    }

    private void log(String string, boolean bl) {
        if (bl) {
            this.mLocalLog.log(string);
        }
    }

    private void onReset() {
        this.log("onReset.", true);
        this.sendDeviceState(1, this.mIsCharging);
        this.sendDeviceState(2, this.mIsLowDataExpected);
        this.sendDeviceState(0, this.mIsPowerSaveOn);
        this.setUnsolResponseFilter(this.mUnsolicitedResponseFilter, true);
        this.setSignalStrengthReportingCriteria();
        this.setLinkCapacityReportingCriteria();
        this.setCellInfoMinInterval(this.mCellInfoMinInterval);
    }

    private void onSetIndicationUpdateMode(int n, int n2) {
        if ((n & 1) != 0) {
            this.mUpdateModes.put(1, n2);
        }
        if ((n & 2) != 0) {
            this.mUpdateModes.put(2, n2);
        }
        if ((n & 4) != 0) {
            this.mUpdateModes.put(4, n2);
        }
        if ((n & 8) != 0) {
            this.mUpdateModes.put(8, n2);
        }
        if ((n & 16) != 0) {
            this.mUpdateModes.put(16, n2);
        }
    }

    private void onUpdateDeviceState(int n, boolean bl) {
        if (n != 2) {
            if (n != 3) {
                if (n != 4) {
                    if (n != 5) {
                        if (n != 7) {
                            return;
                        }
                        if (this.mIsWifiConnected == bl) {
                            return;
                        }
                        this.mIsWifiConnected = bl;
                    } else {
                        if (this.mIsTetheringOn == bl) {
                            return;
                        }
                        this.mIsTetheringOn = bl;
                    }
                } else {
                    if (this.mIsCharging == bl) {
                        return;
                    }
                    this.mIsCharging = bl;
                    this.sendDeviceState(1, this.mIsCharging);
                }
            } else {
                if (this.mIsPowerSaveOn == bl) {
                    return;
                }
                this.mIsPowerSaveOn = bl;
                this.sendDeviceState(0, this.mIsPowerSaveOn);
            }
        } else {
            if (this.mIsScreenOn == bl) {
                return;
            }
            this.mIsScreenOn = bl;
        }
        n = this.computeCellInfoMinInterval();
        if (this.mCellInfoMinInterval != n) {
            this.mCellInfoMinInterval = n;
            this.setCellInfoMinInterval(this.mCellInfoMinInterval);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CellInfo Min Interval Updated to ");
            stringBuilder.append(n);
            this.log(stringBuilder.toString(), true);
        }
        if (this.mIsLowDataExpected != this.isLowDataExpected()) {
            this.mIsLowDataExpected = true ^ this.mIsLowDataExpected;
            this.sendDeviceState(2, this.mIsLowDataExpected);
        }
        int n2 = 0;
        if (!this.shouldTurnOffSignalStrength()) {
            n2 = false | true;
        }
        n = n2;
        if (!this.shouldTurnOffFullNetworkUpdate()) {
            n = n2 | 2;
        }
        n2 = n;
        if (!this.shouldTurnOffDormancyUpdate()) {
            n2 = n | 4;
        }
        n = n2;
        if (!this.shouldTurnOffLinkCapacityEstimate()) {
            n = n2 | 8;
        }
        n2 = n;
        if (!this.shouldTurnOffPhysicalChannelConfig()) {
            n2 = n | 16;
        }
        this.setUnsolResponseFilter(n2, false);
    }

    private void sendDeviceState(int n, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("send type: ");
        stringBuilder.append(this.deviceTypeToString(n));
        stringBuilder.append(", state=");
        stringBuilder.append(bl);
        this.log(stringBuilder.toString(), true);
        this.mPhone.mCi.sendDeviceState(n, bl, null);
    }

    private void setCellInfoMinInterval(int n) {
        this.mPhone.setCellInfoMinInterval(n);
    }

    private void setLinkCapacityReportingCriteria() {
        this.mPhone.setLinkCapacityReportingCriteria(LINK_CAPACITY_DOWNLINK_THRESHOLDS, LINK_CAPACITY_UPLINK_THRESHOLDS, 1);
        this.mPhone.setLinkCapacityReportingCriteria(LINK_CAPACITY_DOWNLINK_THRESHOLDS, LINK_CAPACITY_UPLINK_THRESHOLDS, 2);
        this.mPhone.setLinkCapacityReportingCriteria(LINK_CAPACITY_DOWNLINK_THRESHOLDS, LINK_CAPACITY_UPLINK_THRESHOLDS, 3);
        this.mPhone.setLinkCapacityReportingCriteria(LINK_CAPACITY_DOWNLINK_THRESHOLDS, LINK_CAPACITY_UPLINK_THRESHOLDS, 4);
    }

    private void setSignalStrengthReportingCriteria() {
        this.mPhone.setSignalStrengthReportingCriteria(AccessNetworkThresholds.GERAN, 1);
        this.mPhone.setSignalStrengthReportingCriteria(AccessNetworkThresholds.UTRAN, 2);
        this.mPhone.setSignalStrengthReportingCriteria(AccessNetworkThresholds.EUTRAN, 3);
        this.mPhone.setSignalStrengthReportingCriteria(AccessNetworkThresholds.CDMA2000, 4);
    }

    private void setUnsolResponseFilter(int n, boolean bl) {
        if (bl || n != this.mUnsolicitedResponseFilter) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("old filter: ");
            stringBuilder.append(this.mUnsolicitedResponseFilter);
            stringBuilder.append(", new filter: ");
            stringBuilder.append(n);
            this.log(stringBuilder.toString(), true);
            this.mPhone.mCi.setUnsolResponseFilter(n, null);
            this.mUnsolicitedResponseFilter = n;
        }
    }

    private boolean shouldTurnOffDormancyUpdate() {
        return !(this.mIsCharging || this.mIsScreenOn || this.mIsTetheringOn || this.mUpdateModes.get(4) == 2);
        {
        }
    }

    private boolean shouldTurnOffFullNetworkUpdate() {
        return !(this.mIsCharging || this.mIsScreenOn || this.mIsTetheringOn || this.mUpdateModes.get(2) == 2);
        {
        }
    }

    private boolean shouldTurnOffLinkCapacityEstimate() {
        return !(this.mIsCharging || this.mIsScreenOn || this.mIsTetheringOn || this.mUpdateModes.get(8) == 2);
        {
        }
    }

    private boolean shouldTurnOffPhysicalChannelConfig() {
        return !(this.mIsCharging || this.mIsScreenOn || this.mIsTetheringOn || this.mUpdateModes.get(16) == 2);
        {
        }
    }

    private boolean shouldTurnOffSignalStrength() {
        return !this.mIsCharging && !this.mIsScreenOn && this.mUpdateModes.get(1) != 2;
        {
        }
    }

    @VisibleForTesting
    public int computeCellInfoMinInterval() {
        if (this.mIsScreenOn && !this.mIsWifiConnected) {
            return 2000;
        }
        if (this.mIsScreenOn && this.mIsCharging) {
            return 2000;
        }
        return 10000;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter = new IndentingPrintWriter((Writer)printWriter, "  ");
        printWriter.increaseIndent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mIsTetheringOn=");
        stringBuilder.append(this.mIsTetheringOn);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("mIsScreenOn=");
        stringBuilder.append(this.mIsScreenOn);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("mIsCharging=");
        stringBuilder.append(this.mIsCharging);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("mIsPowerSaveOn=");
        stringBuilder.append(this.mIsPowerSaveOn);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("mIsLowDataExpected=");
        stringBuilder.append(this.mIsLowDataExpected);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("mUnsolicitedResponseFilter=");
        stringBuilder.append(this.mUnsolicitedResponseFilter);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("mIsWifiConnected=");
        stringBuilder.append(this.mIsWifiConnected);
        printWriter.println(stringBuilder.toString());
        printWriter.println("Local logs:");
        printWriter.increaseIndent();
        this.mLocalLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.decreaseIndent();
        printWriter.decreaseIndent();
        printWriter.flush();
    }

    public void handleMessage(Message message) {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("handleMessage msg=");
        ((StringBuilder)charSequence).append((Object)message);
        charSequence = ((StringBuilder)charSequence).toString();
        boolean bl = false;
        boolean bl2 = false;
        this.log((String)charSequence, false);
        switch (message.what) {
            default: {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unexpected message arrives. msg = ");
                ((StringBuilder)charSequence).append(message.what);
                throw new IllegalStateException(((StringBuilder)charSequence).toString());
            }
            case 7: {
                int n = message.what;
                if (message.arg1 != 0) {
                    bl2 = true;
                }
                this.onUpdateDeviceState(n, bl2);
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                int n = message.what;
                bl2 = bl;
                if (message.arg1 != 0) {
                    bl2 = true;
                }
                this.onUpdateDeviceState(n, bl2);
                break;
            }
            case 1: {
                this.onSetIndicationUpdateMode(message.arg1, message.arg2);
                break;
            }
            case 0: 
            case 6: {
                this.onReset();
            }
        }
    }

    public void setIndicationUpdateMode(int n, int n2) {
        this.sendMessage(this.obtainMessage(1, n, n2));
    }

    private static final class AccessNetworkThresholds {
        public static final int[] CDMA2000;
        public static final int[] EUTRAN;
        public static final int[] GERAN;
        public static final int[] UTRAN;

        static {
            GERAN = new int[]{-109, -103, -97, -89};
            UTRAN = new int[]{-114, -104, -94, -84};
            EUTRAN = new int[]{-128, -118, -108, -98};
            CDMA2000 = new int[]{-105, -90, -75, -65};
        }

        private AccessNetworkThresholds() {
        }
    }

}

