/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.util.proto.ProtoOutputStream;
import com.android.internal.annotations.VisibleForTesting;
import java.util.HashMap;

public class PowerProfile {
    private static final String ATTR_NAME = "name";
    private static final String CPU_CLUSTER_POWER_COUNT = "cpu.cluster_power.cluster";
    private static final String CPU_CORE_POWER_PREFIX = "cpu.core_power.cluster";
    private static final String CPU_CORE_SPEED_PREFIX = "cpu.core_speeds.cluster";
    private static final String CPU_PER_CLUSTER_CORE_COUNT = "cpu.clusters.cores";
    public static final String POWER_AMBIENT_DISPLAY = "ambient.on";
    public static final String POWER_AUDIO = "audio";
    public static final String POWER_BATTERY_CAPACITY = "battery.capacity";
    @Deprecated
    public static final String POWER_BLUETOOTH_ACTIVE = "bluetooth.active";
    @Deprecated
    @UnsupportedAppUsage
    public static final String POWER_BLUETOOTH_AT_CMD = "bluetooth.at";
    public static final String POWER_BLUETOOTH_CONTROLLER_IDLE = "bluetooth.controller.idle";
    public static final String POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE = "bluetooth.controller.voltage";
    public static final String POWER_BLUETOOTH_CONTROLLER_RX = "bluetooth.controller.rx";
    public static final String POWER_BLUETOOTH_CONTROLLER_TX = "bluetooth.controller.tx";
    @Deprecated
    @UnsupportedAppUsage
    public static final String POWER_BLUETOOTH_ON = "bluetooth.on";
    public static final String POWER_CAMERA = "camera.avg";
    @UnsupportedAppUsage
    public static final String POWER_CPU_ACTIVE = "cpu.active";
    @UnsupportedAppUsage
    public static final String POWER_CPU_IDLE = "cpu.idle";
    public static final String POWER_CPU_SUSPEND = "cpu.suspend";
    public static final String POWER_FLASHLIGHT = "camera.flashlight";
    @UnsupportedAppUsage
    public static final String POWER_GPS_ON = "gps.on";
    public static final String POWER_GPS_OPERATING_VOLTAGE = "gps.voltage";
    public static final String POWER_GPS_SIGNAL_QUALITY_BASED = "gps.signalqualitybased";
    public static final String POWER_MEMORY = "memory.bandwidths";
    public static final String POWER_MODEM_CONTROLLER_IDLE = "modem.controller.idle";
    public static final String POWER_MODEM_CONTROLLER_OPERATING_VOLTAGE = "modem.controller.voltage";
    public static final String POWER_MODEM_CONTROLLER_RX = "modem.controller.rx";
    public static final String POWER_MODEM_CONTROLLER_SLEEP = "modem.controller.sleep";
    public static final String POWER_MODEM_CONTROLLER_TX = "modem.controller.tx";
    @UnsupportedAppUsage
    public static final String POWER_RADIO_ACTIVE = "radio.active";
    @UnsupportedAppUsage
    public static final String POWER_RADIO_ON = "radio.on";
    @UnsupportedAppUsage
    public static final String POWER_RADIO_SCANNING = "radio.scanning";
    @UnsupportedAppUsage
    public static final String POWER_SCREEN_FULL = "screen.full";
    @UnsupportedAppUsage
    public static final String POWER_SCREEN_ON = "screen.on";
    public static final String POWER_VIDEO = "video";
    @UnsupportedAppUsage
    public static final String POWER_WIFI_ACTIVE = "wifi.active";
    public static final String POWER_WIFI_BATCHED_SCAN = "wifi.batchedscan";
    public static final String POWER_WIFI_CONTROLLER_IDLE = "wifi.controller.idle";
    public static final String POWER_WIFI_CONTROLLER_OPERATING_VOLTAGE = "wifi.controller.voltage";
    public static final String POWER_WIFI_CONTROLLER_RX = "wifi.controller.rx";
    public static final String POWER_WIFI_CONTROLLER_TX = "wifi.controller.tx";
    public static final String POWER_WIFI_CONTROLLER_TX_LEVELS = "wifi.controller.tx_levels";
    @UnsupportedAppUsage
    public static final String POWER_WIFI_ON = "wifi.on";
    @UnsupportedAppUsage
    public static final String POWER_WIFI_SCAN = "wifi.scan";
    private static final String TAG_ARRAY = "array";
    private static final String TAG_ARRAYITEM = "value";
    private static final String TAG_DEVICE = "device";
    private static final String TAG_ITEM = "item";
    private static final Object sLock;
    static final HashMap<String, Double[]> sPowerArrayMap;
    static final HashMap<String, Double> sPowerItemMap;
    private CpuClusterKey[] mCpuClusters;

    static {
        sPowerItemMap = new HashMap();
        sPowerArrayMap = new HashMap();
        sLock = new Object();
    }

    @UnsupportedAppUsage
    @VisibleForTesting
    public PowerProfile(Context context) {
        this(context, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public PowerProfile(Context context, boolean bl) {
        Object object = sLock;
        synchronized (object) {
            if (sPowerItemMap.size() == 0 && sPowerArrayMap.size() == 0) {
                this.readPowerValuesFromXml(context, bl);
            }
            this.initCpuClusters();
            return;
        }
    }

    private void initCpuClusters() {
        if (sPowerArrayMap.containsKey(CPU_PER_CLUSTER_CORE_COUNT)) {
            Double[] arrdouble = sPowerArrayMap.get(CPU_PER_CLUSTER_CORE_COUNT);
            this.mCpuClusters = new CpuClusterKey[arrdouble.length];
            for (int i = 0; i < arrdouble.length; ++i) {
                int n = (int)Math.round(arrdouble[i]);
                CpuClusterKey[] arrcpuClusterKey = this.mCpuClusters;
                CharSequence charSequence = new StringBuilder();
                charSequence.append(CPU_CORE_SPEED_PREFIX);
                charSequence.append(i);
                charSequence = charSequence.toString();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(CPU_CLUSTER_POWER_COUNT);
                stringBuilder.append(i);
                String string2 = stringBuilder.toString();
                stringBuilder = new StringBuilder();
                stringBuilder.append(CPU_CORE_POWER_PREFIX);
                stringBuilder.append(i);
                arrcpuClusterKey[i] = new CpuClusterKey((String)charSequence, string2, stringBuilder.toString(), n);
            }
        } else {
            this.mCpuClusters = new CpuClusterKey[1];
            int n = 1;
            if (sPowerItemMap.containsKey(CPU_PER_CLUSTER_CORE_COUNT)) {
                n = (int)Math.round(sPowerItemMap.get(CPU_PER_CLUSTER_CORE_COUNT));
            }
            this.mCpuClusters[0] = new CpuClusterKey("cpu.core_speeds.cluster0", "cpu.cluster_power.cluster0", "cpu.core_power.cluster0", n);
        }
    }

    /*
     * Exception decompiling
     */
    private void readPowerValuesFromXml(Context var1_1, boolean var2_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 8[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void writePowerConstantArrayToProto(ProtoOutputStream protoOutputStream, String arrdouble, long l) {
        if (sPowerArrayMap.containsKey(arrdouble)) {
            arrdouble = sPowerArrayMap.get(arrdouble);
            int n = arrdouble.length;
            for (int i = 0; i < n; ++i) {
                protoOutputStream.write(l, arrdouble[i]);
            }
        }
    }

    private void writePowerConstantToProto(ProtoOutputStream protoOutputStream, String string2, long l) {
        if (sPowerItemMap.containsKey(string2)) {
            protoOutputStream.write(l, sPowerItemMap.get(string2));
        }
    }

    @UnsupportedAppUsage
    public double getAveragePower(String string2) {
        return this.getAveragePowerOrDefault(string2, 0.0);
    }

    @UnsupportedAppUsage
    public double getAveragePower(String arrdouble, int n) {
        if (sPowerItemMap.containsKey(arrdouble)) {
            return sPowerItemMap.get(arrdouble);
        }
        if (sPowerArrayMap.containsKey(arrdouble)) {
            if ((arrdouble = sPowerArrayMap.get(arrdouble)).length > n && n >= 0) {
                return arrdouble[n];
            }
            if (n >= 0 && arrdouble.length != 0) {
                return arrdouble[arrdouble.length - 1];
            }
            return 0.0;
        }
        return 0.0;
    }

    public double getAveragePowerForCpuCluster(int n) {
        CpuClusterKey[] arrcpuClusterKey;
        if (n >= 0 && n < (arrcpuClusterKey = this.mCpuClusters).length) {
            return this.getAveragePower(arrcpuClusterKey[n].clusterPowerKey);
        }
        return 0.0;
    }

    public double getAveragePowerForCpuCore(int n, int n2) {
        CpuClusterKey[] arrcpuClusterKey;
        if (n >= 0 && n < (arrcpuClusterKey = this.mCpuClusters).length) {
            return this.getAveragePower(arrcpuClusterKey[n].corePowerKey, n2);
        }
        return 0.0;
    }

    public double getAveragePowerOrDefault(String string2, double d) {
        if (sPowerItemMap.containsKey(string2)) {
            return sPowerItemMap.get(string2);
        }
        if (sPowerArrayMap.containsKey(string2)) {
            return sPowerArrayMap.get(string2)[0];
        }
        return d;
    }

    @UnsupportedAppUsage
    public double getBatteryCapacity() {
        return this.getAveragePower(POWER_BATTERY_CAPACITY);
    }

    public int getNumCoresInCpuCluster(int n) {
        return this.mCpuClusters[n].numCpus;
    }

    @UnsupportedAppUsage
    public int getNumCpuClusters() {
        return this.mCpuClusters.length;
    }

    public int getNumElements(String string2) {
        if (sPowerItemMap.containsKey(string2)) {
            return 1;
        }
        if (sPowerArrayMap.containsKey(string2)) {
            return sPowerArrayMap.get(string2).length;
        }
        return 0;
    }

    @UnsupportedAppUsage
    public int getNumSpeedStepsInCpuCluster(int n) {
        CpuClusterKey[] arrcpuClusterKey;
        if (n >= 0 && n < (arrcpuClusterKey = this.mCpuClusters).length) {
            if (sPowerArrayMap.containsKey(arrcpuClusterKey[n].freqKey)) {
                return sPowerArrayMap.get(this.mCpuClusters[n].freqKey).length;
            }
            return 1;
        }
        return 0;
    }

    public void writeToProto(ProtoOutputStream protoOutputStream) {
        this.writePowerConstantToProto(protoOutputStream, POWER_CPU_SUSPEND, 0x10100000001L);
        this.writePowerConstantToProto(protoOutputStream, POWER_CPU_IDLE, 1103806595074L);
        this.writePowerConstantToProto(protoOutputStream, POWER_CPU_ACTIVE, 1103806595075L);
        for (int i = 0; i < this.mCpuClusters.length; ++i) {
            int n;
            long l = protoOutputStream.start(2246267895848L);
            protoOutputStream.write(1120986464257L, i);
            protoOutputStream.write(1103806595074L, sPowerItemMap.get(this.mCpuClusters[i].clusterPowerKey));
            protoOutputStream.write(1120986464259L, this.mCpuClusters[i].numCpus);
            Double[] arrdouble = sPowerArrayMap.get(this.mCpuClusters[i].freqKey);
            int n2 = arrdouble.length;
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                protoOutputStream.write(2211908157444L, arrdouble[n]);
            }
            arrdouble = sPowerArrayMap.get(this.mCpuClusters[i].corePowerKey);
            n2 = arrdouble.length;
            for (n = n3; n < n2; ++n) {
                protoOutputStream.write(2203318222853L, arrdouble[n]);
            }
            protoOutputStream.end(l);
        }
        this.writePowerConstantToProto(protoOutputStream, POWER_WIFI_SCAN, 1103806595076L);
        this.writePowerConstantToProto(protoOutputStream, POWER_WIFI_ON, 1103806595077L);
        this.writePowerConstantToProto(protoOutputStream, POWER_WIFI_ACTIVE, 1103806595078L);
        this.writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_IDLE, 1103806595079L);
        this.writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_RX, 1103806595080L);
        this.writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_TX, 1103806595081L);
        this.writePowerConstantArrayToProto(protoOutputStream, POWER_WIFI_CONTROLLER_TX_LEVELS, 2203318222858L);
        this.writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_OPERATING_VOLTAGE, 1103806595083L);
        this.writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_IDLE, 1103806595084L);
        this.writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_RX, 1103806595085L);
        this.writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_TX, 1103806595086L);
        this.writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE, 1103806595087L);
        this.writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_SLEEP, 0x10100000010L);
        this.writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_IDLE, 0x10100000011L);
        this.writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_RX, 1103806595090L);
        this.writePowerConstantArrayToProto(protoOutputStream, POWER_MODEM_CONTROLLER_TX, 2203318222867L);
        this.writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_OPERATING_VOLTAGE, 1103806595092L);
        this.writePowerConstantToProto(protoOutputStream, POWER_GPS_ON, 1103806595093L);
        this.writePowerConstantArrayToProto(protoOutputStream, POWER_GPS_SIGNAL_QUALITY_BASED, 2203318222870L);
        this.writePowerConstantToProto(protoOutputStream, POWER_GPS_OPERATING_VOLTAGE, 1103806595095L);
        this.writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_ON, 1103806595096L);
        this.writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_ACTIVE, 1103806595097L);
        this.writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_AT_CMD, 1103806595098L);
        this.writePowerConstantToProto(protoOutputStream, POWER_AMBIENT_DISPLAY, 1103806595099L);
        this.writePowerConstantToProto(protoOutputStream, POWER_SCREEN_ON, 1103806595100L);
        this.writePowerConstantToProto(protoOutputStream, POWER_RADIO_ON, 1103806595101L);
        this.writePowerConstantToProto(protoOutputStream, POWER_RADIO_SCANNING, 1103806595102L);
        this.writePowerConstantToProto(protoOutputStream, POWER_RADIO_ACTIVE, 1103806595103L);
        this.writePowerConstantToProto(protoOutputStream, POWER_SCREEN_FULL, 1103806595104L);
        this.writePowerConstantToProto(protoOutputStream, POWER_AUDIO, 1103806595105L);
        this.writePowerConstantToProto(protoOutputStream, POWER_VIDEO, 1103806595106L);
        this.writePowerConstantToProto(protoOutputStream, POWER_FLASHLIGHT, 1103806595107L);
        this.writePowerConstantToProto(protoOutputStream, POWER_MEMORY, 1103806595108L);
        this.writePowerConstantToProto(protoOutputStream, POWER_CAMERA, 1103806595109L);
        this.writePowerConstantToProto(protoOutputStream, POWER_WIFI_BATCHED_SCAN, 1103806595110L);
        this.writePowerConstantToProto(protoOutputStream, POWER_BATTERY_CAPACITY, 1103806595111L);
    }

    public static class CpuClusterKey {
        private final String clusterPowerKey;
        private final String corePowerKey;
        private final String freqKey;
        private final int numCpus;

        private CpuClusterKey(String string2, String string3, String string4, int n) {
            this.freqKey = string2;
            this.clusterPowerKey = string3;
            this.corePowerKey = string4;
            this.numCpus = n;
        }
    }

}

