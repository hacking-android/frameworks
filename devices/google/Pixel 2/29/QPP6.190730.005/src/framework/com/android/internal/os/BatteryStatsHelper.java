/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.BatteryStats;
import android.os.Bundle;
import android.os.MemoryFile;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseLongArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.IBatteryStats;
import com.android.internal.os.BatterySipper;
import com.android.internal.os.BatteryStatsImpl;
import com.android.internal.os.BluetoothPowerCalculator;
import com.android.internal.os.CameraPowerCalculator;
import com.android.internal.os.CpuPowerCalculator;
import com.android.internal.os.FlashlightPowerCalculator;
import com.android.internal.os.MediaPowerCalculator;
import com.android.internal.os.MemoryPowerCalculator;
import com.android.internal.os.MobileRadioPowerCalculator;
import com.android.internal.os.PowerCalculator;
import com.android.internal.os.PowerProfile;
import com.android.internal.os.SensorPowerCalculator;
import com.android.internal.os.WakelockPowerCalculator;
import com.android.internal.os.WifiPowerCalculator;
import com.android.internal.os.WifiPowerEstimator;
import com.android.internal.util.ArrayUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class BatteryStatsHelper {
    static final boolean DEBUG = false;
    private static final String TAG = BatteryStatsHelper.class.getSimpleName();
    private static Intent sBatteryBroadcastXfer;
    private static ArrayMap<File, BatteryStats> sFileXfer;
    private static BatteryStats sStatsXfer;
    private Intent mBatteryBroadcast;
    @UnsupportedAppUsage
    private IBatteryStats mBatteryInfo;
    long mBatteryRealtimeUs;
    long mBatteryTimeRemainingUs;
    long mBatteryUptimeUs;
    PowerCalculator mBluetoothPowerCalculator;
    private final List<BatterySipper> mBluetoothSippers = new ArrayList<BatterySipper>();
    PowerCalculator mCameraPowerCalculator;
    long mChargeTimeRemainingUs;
    private final boolean mCollectBatteryBroadcast;
    private double mComputedPower;
    private final Context mContext;
    PowerCalculator mCpuPowerCalculator;
    PowerCalculator mFlashlightPowerCalculator;
    boolean mHasBluetoothPowerReporting = false;
    boolean mHasWifiPowerReporting = false;
    private double mMaxDrainedPower;
    private double mMaxPower = 1.0;
    private double mMaxRealPower = 1.0;
    PowerCalculator mMediaPowerCalculator;
    PowerCalculator mMemoryPowerCalculator;
    private double mMinDrainedPower;
    MobileRadioPowerCalculator mMobileRadioPowerCalculator;
    private final List<BatterySipper> mMobilemsppList = new ArrayList<BatterySipper>();
    private PackageManager mPackageManager;
    @UnsupportedAppUsage
    private PowerProfile mPowerProfile;
    long mRawRealtimeUs;
    long mRawUptimeUs;
    PowerCalculator mSensorPowerCalculator;
    private String[] mServicepackageArray;
    private BatteryStats mStats;
    private long mStatsPeriod = 0L;
    private int mStatsType = 0;
    private String[] mSystemPackageArray;
    private double mTotalPower;
    long mTypeBatteryRealtimeUs;
    long mTypeBatteryUptimeUs;
    @UnsupportedAppUsage
    private final List<BatterySipper> mUsageList = new ArrayList<BatterySipper>();
    private final SparseArray<List<BatterySipper>> mUserSippers = new SparseArray();
    PowerCalculator mWakelockPowerCalculator;
    private final boolean mWifiOnly;
    PowerCalculator mWifiPowerCalculator;
    private final List<BatterySipper> mWifiSippers = new ArrayList<BatterySipper>();

    static {
        sFileXfer = new ArrayMap();
    }

    @UnsupportedAppUsage
    public BatteryStatsHelper(Context context) {
        this(context, true);
    }

    @UnsupportedAppUsage
    public BatteryStatsHelper(Context context, boolean bl) {
        this(context, bl, BatteryStatsHelper.checkWifiOnly(context));
    }

    @UnsupportedAppUsage
    public BatteryStatsHelper(Context object, boolean bl, boolean bl2) {
        this.mContext = object;
        this.mCollectBatteryBroadcast = bl;
        this.mWifiOnly = bl2;
        this.mPackageManager = ((Context)object).getPackageManager();
        object = ((Context)object).getResources();
        this.mSystemPackageArray = ((Resources)object).getStringArray(17235993);
        this.mServicepackageArray = ((Resources)object).getStringArray(17235992);
    }

    private void addAmbientDisplayUsage() {
        long l = this.mStats.getScreenDozeTime(this.mRawRealtimeUs, this.mStatsType) / 1000L;
        double d = this.mPowerProfile.getAveragePower("ambient.on") * (double)l / 3600000.0;
        if (d > 0.0) {
            this.addEntry(BatterySipper.DrainType.AMBIENT_DISPLAY, l, d);
        }
    }

    private void addBluetoothUsage() {
        BatterySipper batterySipper = new BatterySipper(BatterySipper.DrainType.BLUETOOTH, null, 0.0);
        this.mBluetoothPowerCalculator.calculateRemaining(batterySipper, this.mStats, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
        this.aggregateSippers(batterySipper, this.mBluetoothSippers, "Bluetooth");
        if (batterySipper.totalPowerMah > 0.0) {
            this.mUsageList.add(batterySipper);
        }
    }

    private BatterySipper addEntry(BatterySipper.DrainType object, long l, double d) {
        object = new BatterySipper((BatterySipper.DrainType)((Object)object), null, 0.0);
        ((BatterySipper)object).usagePowerMah = d;
        ((BatterySipper)object).usageTimeMs = l;
        ((BatterySipper)object).sumPower();
        this.mUsageList.add((BatterySipper)object);
        return object;
    }

    private void addIdleUsage() {
        double d = ((double)(this.mTypeBatteryRealtimeUs / 1000L) * this.mPowerProfile.getAveragePower("cpu.suspend") + (double)(this.mTypeBatteryUptimeUs / 1000L) * this.mPowerProfile.getAveragePower("cpu.idle")) / 3600000.0;
        if (d != 0.0) {
            this.addEntry(BatterySipper.DrainType.IDLE, this.mTypeBatteryRealtimeUs / 1000L, d);
        }
    }

    private void addMemoryUsage() {
        BatterySipper batterySipper = new BatterySipper(BatterySipper.DrainType.MEMORY, null, 0.0);
        this.mMemoryPowerCalculator.calculateRemaining(batterySipper, this.mStats, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
        batterySipper.sumPower();
        if (batterySipper.totalPowerMah > 0.0) {
            this.mUsageList.add(batterySipper);
        }
    }

    private void addPhoneUsage() {
        long l = this.mStats.getPhoneOnTime(this.mRawRealtimeUs, this.mStatsType) / 1000L;
        double d = this.mPowerProfile.getAveragePower("radio.active") * (double)l / 3600000.0;
        if (d != 0.0) {
            this.addEntry(BatterySipper.DrainType.PHONE, l, d);
        }
    }

    private void addRadioUsage() {
        BatterySipper batterySipper = new BatterySipper(BatterySipper.DrainType.CELL, null, 0.0);
        this.mMobileRadioPowerCalculator.calculateRemaining(batterySipper, this.mStats, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
        batterySipper.sumPower();
        if (batterySipper.totalPowerMah > 0.0) {
            this.mUsageList.add(batterySipper);
        }
    }

    private void addScreenUsage() {
        long l = this.mStats.getScreenOnTime(this.mRawRealtimeUs, this.mStatsType) / 1000L;
        double d = 0.0 + (double)l * this.mPowerProfile.getAveragePower("screen.on");
        double d2 = this.mPowerProfile.getAveragePower("screen.full");
        for (int i = 0; i < 5; ++i) {
            double d3 = (double)((float)i + 0.5f) * d2 / 5.0;
            d += (double)(this.mStats.getScreenBrightnessTime(i, this.mRawRealtimeUs, this.mStatsType) / 1000L) * d3;
        }
        if ((d /= 3600000.0) != 0.0) {
            this.addEntry(BatterySipper.DrainType.SCREEN, l, d);
        }
    }

    private void addUserUsage() {
        for (int i = 0; i < this.mUserSippers.size(); ++i) {
            int n = this.mUserSippers.keyAt(i);
            BatterySipper batterySipper = new BatterySipper(BatterySipper.DrainType.USER, null, 0.0);
            batterySipper.userId = n;
            this.aggregateSippers(batterySipper, this.mUserSippers.valueAt(i), "User");
            this.mUsageList.add(batterySipper);
        }
    }

    private void addWiFiUsage() {
        BatterySipper batterySipper = new BatterySipper(BatterySipper.DrainType.WIFI, null, 0.0);
        this.mWifiPowerCalculator.calculateRemaining(batterySipper, this.mStats, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
        this.aggregateSippers(batterySipper, this.mWifiSippers, "WIFI");
        if (batterySipper.totalPowerMah > 0.0) {
            this.mUsageList.add(batterySipper);
        }
    }

    private void aggregateSippers(BatterySipper batterySipper, List<BatterySipper> list, String string2) {
        for (int i = 0; i < list.size(); ++i) {
            batterySipper.add(list.get(i));
        }
        batterySipper.computeMobilemspp();
        batterySipper.sumPower();
    }

    public static boolean checkHasBluetoothPowerReporting(BatteryStats batteryStats, PowerProfile powerProfile) {
        boolean bl = batteryStats.hasBluetoothActivityReporting() && powerProfile.getAveragePower("bluetooth.controller.idle") != 0.0 && powerProfile.getAveragePower("bluetooth.controller.rx") != 0.0 && powerProfile.getAveragePower("bluetooth.controller.tx") != 0.0;
        return bl;
    }

    public static boolean checkHasWifiPowerReporting(BatteryStats batteryStats, PowerProfile powerProfile) {
        boolean bl = batteryStats.hasWifiActivityReporting() && powerProfile.getAveragePower("wifi.controller.idle") != 0.0 && powerProfile.getAveragePower("wifi.controller.rx") != 0.0 && powerProfile.getAveragePower("wifi.controller.tx") != 0.0;
        return bl;
    }

    public static boolean checkWifiOnly(Context object) {
        if ((object = (ConnectivityManager)((Context)object).getSystemService("connectivity")) == null) {
            return false;
        }
        return ((ConnectivityManager)object).isNetworkSupported(0) ^ true;
    }

    @UnsupportedAppUsage
    public static void dropFile(Context context, String string2) {
        BatteryStatsHelper.makeFilePath(context, string2).delete();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private static BatteryStatsImpl getStats(IBatteryStats object) {
        block13 : {
            object2 = object.getStatisticsStream();
            if (object2 == null) break block13;
            object = new ParcelFileDescriptor.AutoCloseInputStream((ParcelFileDescriptor)object2);
            arrby = BatteryStatsHelper.readFully((FileInputStream)object, MemoryFile.getSize(object2.getFileDescriptor()));
            object2 = Parcel.obtain();
            object2.unmarshall(arrby, 0, arrby.length);
            object2.setDataPosition(0);
            object2 = BatteryStatsImpl.CREATOR.createFromParcel((Parcel)object2);
            object.close();
            return object2;
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    try {
                        object.close();
                        ** GOTO lbl26
                    }
                    catch (Throwable throwable3) {
                        try {
                            throwable.addSuppressed(throwable3);
lbl26: // 2 sources:
                            throw throwable2;
                            {
                                catch (IOException iOException) {
                                    Log.w(BatteryStatsHelper.TAG, "Unable to read statistics stream", iOException);
                                }
                            }
                        }
                        catch (RemoteException remoteException) {
                            Log.w(BatteryStatsHelper.TAG, "RemoteException:", remoteException);
                        }
                    }
                }
            }
        }
        return new BatteryStatsImpl();
    }

    @UnsupportedAppUsage
    private void load() {
        IBatteryStats iBatteryStats = this.mBatteryInfo;
        if (iBatteryStats == null) {
            return;
        }
        this.mStats = BatteryStatsHelper.getStats(iBatteryStats);
        if (this.mCollectBatteryBroadcast) {
            this.mBatteryBroadcast = this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        }
    }

    private static File makeFilePath(Context context, String string2) {
        return new File(context.getFilesDir(), string2);
    }

    public static String makemAh(double d) {
        if (d == 0.0) {
            return "0";
        }
        String string2 = d < 1.0E-5 ? "%.8f" : (d < 1.0E-4 ? "%.7f" : (d < 0.001 ? "%.6f" : (d < 0.01 ? "%.5f" : (d < 0.1 ? "%.4f" : (d < 1.0 ? "%.3f" : (d < 10.0 ? "%.2f" : (d < 100.0 ? "%.1f" : "%.0f")))))));
        return String.format(Locale.ENGLISH, string2, d);
    }

    private void processAppUsage(SparseArray<UserHandle> sparseArray) {
        boolean bl = sparseArray.get(-1) != null;
        this.mStatsPeriod = this.mTypeBatteryRealtimeUs;
        BatterySipper batterySipper = null;
        SparseArray<? extends BatteryStats.Uid> sparseArray2 = this.mStats.getUidStats();
        int n = sparseArray2.size();
        for (int i = 0; i < n; ++i) {
            ArrayList<Object> arrayList;
            block14 : {
                Object object;
                BatterySipper batterySipper2;
                block13 : {
                    object = sparseArray2.valueAt(i);
                    batterySipper2 = new BatterySipper(BatterySipper.DrainType.APP, (BatteryStats.Uid)object, 0.0);
                    this.mCpuPowerCalculator.calculateApp(batterySipper2, (BatteryStats.Uid)object, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
                    this.mWakelockPowerCalculator.calculateApp(batterySipper2, (BatteryStats.Uid)object, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
                    this.mMobileRadioPowerCalculator.calculateApp(batterySipper2, (BatteryStats.Uid)object, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
                    this.mWifiPowerCalculator.calculateApp(batterySipper2, (BatteryStats.Uid)object, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
                    this.mBluetoothPowerCalculator.calculateApp(batterySipper2, (BatteryStats.Uid)object, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
                    this.mSensorPowerCalculator.calculateApp(batterySipper2, (BatteryStats.Uid)object, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
                    this.mCameraPowerCalculator.calculateApp(batterySipper2, (BatteryStats.Uid)object, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
                    this.mFlashlightPowerCalculator.calculateApp(batterySipper2, (BatteryStats.Uid)object, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
                    this.mMediaPowerCalculator.calculateApp(batterySipper2, (BatteryStats.Uid)object, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
                    if (batterySipper2.sumPower() != 0.0) break block13;
                    arrayList = batterySipper;
                    if (((BatteryStats.Uid)object).getUid() != 0) break block14;
                }
                int n2 = batterySipper2.getUid();
                int n3 = UserHandle.getUserId(n2);
                if (n2 == 1010) {
                    this.mWifiSippers.add(batterySipper2);
                } else {
                    object = batterySipper2;
                    if (n2 == 1002) {
                        this.mBluetoothSippers.add((BatterySipper)object);
                    } else if (!bl && sparseArray.get(n3) == null && UserHandle.getAppId(n2) >= 10000) {
                        List<BatterySipper> list = this.mUserSippers.get(n3);
                        arrayList = list;
                        if (list == null) {
                            arrayList = new ArrayList<Object>();
                            this.mUserSippers.put(n3, arrayList);
                        }
                        arrayList.add(object);
                    } else {
                        this.mUsageList.add((BatterySipper)object);
                    }
                }
                arrayList = batterySipper;
                if (n2 == 0) {
                    arrayList = batterySipper2;
                }
            }
            batterySipper = arrayList;
        }
        if (batterySipper != null) {
            this.mWakelockPowerCalculator.calculateRemaining(batterySipper, this.mStats, this.mRawRealtimeUs, this.mRawUptimeUs, this.mStatsType);
            batterySipper.sumPower();
        }
    }

    private void processMiscUsage() {
        this.addUserUsage();
        this.addPhoneUsage();
        this.addScreenUsage();
        this.addAmbientDisplayUsage();
        this.addWiFiUsage();
        this.addBluetoothUsage();
        this.addMemoryUsage();
        this.addIdleUsage();
        if (!this.mWifiOnly) {
            this.addRadioUsage();
        }
    }

    public static byte[] readFully(FileInputStream fileInputStream) throws IOException {
        return BatteryStatsHelper.readFully(fileInputStream, fileInputStream.available());
    }

    public static byte[] readFully(FileInputStream fileInputStream, int n) throws IOException {
        int n2 = 0;
        byte[] arrby = new byte[n];
        n = n2;
        while ((n2 = fileInputStream.read(arrby, n, arrby.length - n)) > 0) {
            n += n2;
            n2 = fileInputStream.available();
            byte[] arrby2 = arrby;
            if (n2 > arrby.length - n) {
                arrby2 = new byte[n + n2];
                System.arraycopy(arrby, 0, arrby2, 0, n);
            }
            arrby = arrby2;
        }
        return arrby;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static BatteryStats statsFromFile(Context object, String object2) {
        Throwable throwable2222;
        ArrayMap<File, BatteryStats> arrayMap = sFileXfer;
        // MONITORENTER : arrayMap
        File file = BatteryStatsHelper.makeFilePath((Context)object, (String)object2);
        object = sFileXfer.get(file);
        if (object != null) {
            // MONITOREXIT : arrayMap
            return object;
        }
        Parcel parcel = null;
        Object object3 = null;
        object = object3;
        object2 = parcel;
        object = object3;
        object2 = parcel;
        FileInputStream fileInputStream = new FileInputStream(file);
        object = fileInputStream;
        object2 = fileInputStream;
        object3 = BatteryStatsHelper.readFully(fileInputStream);
        object = fileInputStream;
        object2 = fileInputStream;
        parcel = Parcel.obtain();
        object = fileInputStream;
        object2 = fileInputStream;
        parcel.unmarshall((byte[])object3, 0, ((byte[])object3).length);
        object = fileInputStream;
        object2 = fileInputStream;
        parcel.setDataPosition(0);
        object = fileInputStream;
        object2 = fileInputStream;
        object3 = BatteryStatsImpl.CREATOR.createFromParcel(parcel);
        try {
            fileInputStream.close();
            return object3;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return object3;
        {
            block16 : {
                catch (Throwable throwable2222) {
                }
                catch (IOException iOException) {}
                object = object2;
                {
                    Log.w(TAG, "Unable to read history to file", iOException);
                    if (object2 == null) break block16;
                }
                try {
                    ((FileInputStream)object2).close();
                    return BatteryStatsHelper.getStats(IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats")));
                }
                catch (IOException iOException) {
                    return BatteryStatsHelper.getStats(IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats")));
                }
            }
            // MONITOREXIT : arrayMap
            return BatteryStatsHelper.getStats(IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats")));
        }
        if (object == null) throw throwable2222;
        ((FileInputStream)object).close();
        throw throwable2222;
        catch (IOException iOException) {
            // empty catch block
        }
        throw throwable2222;
    }

    @UnsupportedAppUsage
    public void clearStats() {
        this.mStats = null;
    }

    public long convertMsToUs(long l) {
        return 1000L * l;
    }

    public long convertUsToMs(long l) {
        return l / 1000L;
    }

    public void create(BatteryStats batteryStats) {
        this.mPowerProfile = new PowerProfile(this.mContext);
        this.mStats = batteryStats;
    }

    @UnsupportedAppUsage
    public void create(Bundle bundle) {
        if (bundle != null) {
            this.mStats = sStatsXfer;
            this.mBatteryBroadcast = sBatteryBroadcastXfer;
        }
        this.mBatteryInfo = IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
        this.mPowerProfile = new PowerProfile(this.mContext);
    }

    @UnsupportedAppUsage
    public Intent getBatteryBroadcast() {
        if (this.mBatteryBroadcast == null && this.mCollectBatteryBroadcast) {
            this.load();
        }
        return this.mBatteryBroadcast;
    }

    public double getComputedPower() {
        return this.mComputedPower;
    }

    @VisibleForTesting
    public long getForegroundActivityTotalTimeUs(BatteryStats.Uid object, long l) {
        if ((object = ((BatteryStats.Uid)object).getForegroundActivityTimer()) != null) {
            return ((BatteryStats.Timer)object).getTotalTimeLocked(l, 0);
        }
        return 0L;
    }

    public double getMaxDrainedPower() {
        return this.mMaxDrainedPower;
    }

    @UnsupportedAppUsage
    public double getMaxPower() {
        return this.mMaxPower;
    }

    public double getMaxRealPower() {
        return this.mMaxRealPower;
    }

    public double getMinDrainedPower() {
        return this.mMinDrainedPower;
    }

    public List<BatterySipper> getMobilemsppList() {
        return this.mMobilemsppList;
    }

    public PowerProfile getPowerProfile() {
        return this.mPowerProfile;
    }

    @VisibleForTesting
    public long getProcessForegroundTimeMs(BatteryStats.Uid uid, int n) {
        long l = this.convertMsToUs(SystemClock.elapsedRealtime());
        int[] arrn = new int[1];
        arrn[0] = 0;
        long l2 = 0L;
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            l2 += uid.getProcessStateTime(arrn[i], l, n);
        }
        return this.convertUsToMs(Math.min(l2, this.getForegroundActivityTotalTimeUs(uid, l)));
    }

    @UnsupportedAppUsage
    public BatteryStats getStats() {
        if (this.mStats == null) {
            this.load();
        }
        return this.mStats;
    }

    public long getStatsPeriod() {
        return this.mStatsPeriod;
    }

    public int getStatsType() {
        return this.mStatsType;
    }

    @UnsupportedAppUsage
    public double getTotalPower() {
        return this.mTotalPower;
    }

    @UnsupportedAppUsage
    public List<BatterySipper> getUsageList() {
        return this.mUsageList;
    }

    public boolean isTypeService(BatterySipper object2) {
        String[] arrstring = this.mPackageManager.getPackagesForUid(((BatterySipper)object2).getUid());
        if (arrstring == null) {
            return false;
        }
        for (String string2 : arrstring) {
            if (!ArrayUtils.contains(this.mServicepackageArray, string2)) continue;
            return true;
        }
        return false;
    }

    public boolean isTypeSystem(BatterySipper object2) {
        int n = ((BatterySipper)object2).uidObj == null ? -1 : ((BatterySipper)object2).getUid();
        ((BatterySipper)object2).mPackages = this.mPackageManager.getPackagesForUid(n);
        if (n >= 0 && n < 10000) {
            return true;
        }
        if (((BatterySipper)object2).mPackages != null) {
            for (String string2 : ((BatterySipper)object2).mPackages) {
                if (!ArrayUtils.contains(this.mSystemPackageArray, string2)) continue;
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public void refreshStats(int n, int n2) {
        SparseArray<UserHandle> sparseArray = new SparseArray<UserHandle>(1);
        sparseArray.put(n2, new UserHandle(n2));
        this.refreshStats(n, sparseArray);
    }

    @UnsupportedAppUsage
    public void refreshStats(int n, SparseArray<UserHandle> sparseArray) {
        this.refreshStats(n, sparseArray, SystemClock.elapsedRealtime() * 1000L, SystemClock.uptimeMillis() * 1000L);
    }

    public void refreshStats(int n, SparseArray<UserHandle> object, long l, long l2) {
        double d;
        double d2;
        int n2;
        Object object2;
        BatteryStatsHelper batteryStatsHelper = this;
        if (n != 0) {
            String string2 = TAG;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("refreshStats called for statsType ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(" but only STATS_SINCE_CHARGED is supported. Using STATS_SINCE_CHARGED instead.");
            Log.w(string2, ((StringBuilder)object2).toString());
        }
        this.getStats();
        batteryStatsHelper.mMaxPower = 0.0;
        batteryStatsHelper.mMaxRealPower = 0.0;
        batteryStatsHelper.mComputedPower = 0.0;
        batteryStatsHelper.mTotalPower = 0.0;
        batteryStatsHelper.mUsageList.clear();
        batteryStatsHelper.mWifiSippers.clear();
        batteryStatsHelper.mBluetoothSippers.clear();
        batteryStatsHelper.mUserSippers.clear();
        batteryStatsHelper.mMobilemsppList.clear();
        if (batteryStatsHelper.mStats == null) {
            return;
        }
        if (batteryStatsHelper.mCpuPowerCalculator == null) {
            batteryStatsHelper.mCpuPowerCalculator = new CpuPowerCalculator(batteryStatsHelper.mPowerProfile);
        }
        batteryStatsHelper.mCpuPowerCalculator.reset();
        if (batteryStatsHelper.mMemoryPowerCalculator == null) {
            batteryStatsHelper.mMemoryPowerCalculator = new MemoryPowerCalculator(batteryStatsHelper.mPowerProfile);
        }
        batteryStatsHelper.mMemoryPowerCalculator.reset();
        if (batteryStatsHelper.mWakelockPowerCalculator == null) {
            batteryStatsHelper.mWakelockPowerCalculator = new WakelockPowerCalculator(batteryStatsHelper.mPowerProfile);
        }
        batteryStatsHelper.mWakelockPowerCalculator.reset();
        if (batteryStatsHelper.mMobileRadioPowerCalculator == null) {
            batteryStatsHelper.mMobileRadioPowerCalculator = new MobileRadioPowerCalculator(batteryStatsHelper.mPowerProfile, batteryStatsHelper.mStats);
        }
        batteryStatsHelper.mMobileRadioPowerCalculator.reset(batteryStatsHelper.mStats);
        boolean bl = BatteryStatsHelper.checkHasWifiPowerReporting(batteryStatsHelper.mStats, batteryStatsHelper.mPowerProfile);
        if (batteryStatsHelper.mWifiPowerCalculator == null || bl != batteryStatsHelper.mHasWifiPowerReporting) {
            object2 = bl ? new WifiPowerCalculator(batteryStatsHelper.mPowerProfile) : new WifiPowerEstimator(batteryStatsHelper.mPowerProfile);
            batteryStatsHelper.mWifiPowerCalculator = object2;
            batteryStatsHelper.mHasWifiPowerReporting = bl;
        }
        batteryStatsHelper.mWifiPowerCalculator.reset();
        bl = BatteryStatsHelper.checkHasBluetoothPowerReporting(batteryStatsHelper.mStats, batteryStatsHelper.mPowerProfile);
        if (batteryStatsHelper.mBluetoothPowerCalculator == null || bl != batteryStatsHelper.mHasBluetoothPowerReporting) {
            batteryStatsHelper.mBluetoothPowerCalculator = new BluetoothPowerCalculator(batteryStatsHelper.mPowerProfile);
            batteryStatsHelper.mHasBluetoothPowerReporting = bl;
        }
        batteryStatsHelper.mBluetoothPowerCalculator.reset();
        batteryStatsHelper.mSensorPowerCalculator = new SensorPowerCalculator(batteryStatsHelper.mPowerProfile, (SensorManager)batteryStatsHelper.mContext.getSystemService("sensor"), batteryStatsHelper.mStats, l, n);
        batteryStatsHelper.mSensorPowerCalculator.reset();
        if (batteryStatsHelper.mCameraPowerCalculator == null) {
            batteryStatsHelper.mCameraPowerCalculator = new CameraPowerCalculator(batteryStatsHelper.mPowerProfile);
        }
        batteryStatsHelper.mCameraPowerCalculator.reset();
        if (batteryStatsHelper.mFlashlightPowerCalculator == null) {
            batteryStatsHelper.mFlashlightPowerCalculator = new FlashlightPowerCalculator(batteryStatsHelper.mPowerProfile);
        }
        batteryStatsHelper.mFlashlightPowerCalculator.reset();
        if (batteryStatsHelper.mMediaPowerCalculator == null) {
            batteryStatsHelper.mMediaPowerCalculator = new MediaPowerCalculator(batteryStatsHelper.mPowerProfile);
        }
        batteryStatsHelper.mMediaPowerCalculator.reset();
        batteryStatsHelper.mStatsType = n;
        batteryStatsHelper.mRawUptimeUs = l2;
        batteryStatsHelper.mRawRealtimeUs = l;
        batteryStatsHelper.mBatteryUptimeUs = batteryStatsHelper.mStats.getBatteryUptime(l2);
        batteryStatsHelper.mBatteryRealtimeUs = batteryStatsHelper.mStats.getBatteryRealtime(l);
        batteryStatsHelper.mTypeBatteryUptimeUs = batteryStatsHelper.mStats.computeBatteryUptime(l2, batteryStatsHelper.mStatsType);
        batteryStatsHelper.mTypeBatteryRealtimeUs = batteryStatsHelper.mStats.computeBatteryRealtime(l, batteryStatsHelper.mStatsType);
        batteryStatsHelper.mBatteryTimeRemainingUs = batteryStatsHelper.mStats.computeBatteryTimeRemaining(l);
        batteryStatsHelper.mChargeTimeRemainingUs = batteryStatsHelper.mStats.computeChargeTimeRemaining(l);
        batteryStatsHelper.mMinDrainedPower = (double)batteryStatsHelper.mStats.getLowDischargeAmountSinceCharge() * batteryStatsHelper.mPowerProfile.getBatteryCapacity() / 100.0;
        batteryStatsHelper.mMaxDrainedPower = (double)batteryStatsHelper.mStats.getHighDischargeAmountSinceCharge() * batteryStatsHelper.mPowerProfile.getBatteryCapacity() / 100.0;
        batteryStatsHelper.processAppUsage((SparseArray<UserHandle>)object);
        for (n = 0; n < batteryStatsHelper.mUsageList.size(); ++n) {
            object = batteryStatsHelper.mUsageList.get(n);
            ((BatterySipper)object).computeMobilemspp();
            if (((BatterySipper)object).mobilemspp == 0.0) continue;
            batteryStatsHelper.mMobilemsppList.add((BatterySipper)object);
        }
        for (n = 0; n < batteryStatsHelper.mUserSippers.size(); ++n) {
            object2 = batteryStatsHelper.mUserSippers.valueAt(n);
            for (n2 = 0; n2 < object2.size(); ++n2) {
                object = object2.get(n2);
                ((BatterySipper)object).computeMobilemspp();
                if (((BatterySipper)object).mobilemspp == 0.0) continue;
                batteryStatsHelper.mMobilemsppList.add((BatterySipper)object);
            }
        }
        Collections.sort(batteryStatsHelper.mMobilemsppList, new Comparator<BatterySipper>(){

            @Override
            public int compare(BatterySipper batterySipper, BatterySipper batterySipper2) {
                return Double.compare(batterySipper2.mobilemspp, batterySipper.mobilemspp);
            }
        });
        this.processMiscUsage();
        Collections.sort(batteryStatsHelper.mUsageList);
        if (!batteryStatsHelper.mUsageList.isEmpty()) {
            batteryStatsHelper.mMaxPower = d2 = batteryStatsHelper.mUsageList.get((int)0).totalPowerMah;
            batteryStatsHelper.mMaxRealPower = d2;
            n2 = batteryStatsHelper.mUsageList.size();
            for (n = 0; n < n2; ++n) {
                batteryStatsHelper.mComputedPower += batteryStatsHelper.mUsageList.get((int)n).totalPowerMah;
            }
        }
        batteryStatsHelper.mTotalPower = batteryStatsHelper.mComputedPower;
        if (batteryStatsHelper.mStats.getLowDischargeAmountSinceCharge() > 1) {
            d = batteryStatsHelper.mMinDrainedPower;
            d2 = batteryStatsHelper.mComputedPower;
            if (d > d2) {
                d2 = d - d2;
                batteryStatsHelper.mTotalPower = d;
                object = new BatterySipper(BatterySipper.DrainType.UNACCOUNTED, null, d2);
                n = n2 = Collections.binarySearch(batteryStatsHelper.mUsageList, object);
                if (n2 < 0) {
                    n = -(n2 + 1);
                }
                batteryStatsHelper.mUsageList.add(n, (BatterySipper)object);
                batteryStatsHelper.mMaxPower = Math.max(batteryStatsHelper.mMaxPower, d2);
            } else {
                d = batteryStatsHelper.mMaxDrainedPower;
                if (d < d2) {
                    object = new BatterySipper(BatterySipper.DrainType.OVERCOUNTED, null, d2 -= d);
                    n = n2 = Collections.binarySearch(batteryStatsHelper.mUsageList, object);
                    if (n2 < 0) {
                        n = -(n2 + 1);
                    }
                    batteryStatsHelper.mUsageList.add(n, (BatterySipper)object);
                    batteryStatsHelper.mMaxPower = Math.max(batteryStatsHelper.mMaxPower, d2);
                }
            }
        }
        d = batteryStatsHelper.removeHiddenBatterySippers(batteryStatsHelper.mUsageList);
        d2 = this.getTotalPower() - d;
        if (Math.abs(d2) > 0.001) {
            n = batteryStatsHelper.mUsageList.size();
            for (n2 = 0; n2 < n; ++n2) {
                object = this.mUsageList.get(n2);
                if (((BatterySipper)object).shouldHide) continue;
                ((BatterySipper)object).proportionalSmearMah = (((BatterySipper)object).totalPowerMah + ((BatterySipper)object).screenPowerMah) / d2 * d;
                ((BatterySipper)object).sumPower();
            }
        }
    }

    @UnsupportedAppUsage
    public void refreshStats(int n, List<UserHandle> list) {
        int n2 = list.size();
        SparseArray<UserHandle> sparseArray = new SparseArray<UserHandle>(n2);
        for (int i = 0; i < n2; ++i) {
            UserHandle userHandle = list.get(i);
            sparseArray.put(userHandle.getIdentifier(), userHandle);
        }
        this.refreshStats(n, sparseArray);
    }

    public double removeHiddenBatterySippers(List<BatterySipper> list) {
        double d = 0.0;
        BatterySipper batterySipper = null;
        for (int i = list.size() - 1; i >= 0; --i) {
            BatterySipper batterySipper2 = list.get(i);
            batterySipper2.shouldHide = this.shouldHideSipper(batterySipper2);
            double d2 = d;
            if (batterySipper2.shouldHide) {
                d2 = d;
                if (batterySipper2.drainType != BatterySipper.DrainType.OVERCOUNTED) {
                    d2 = d;
                    if (batterySipper2.drainType != BatterySipper.DrainType.SCREEN) {
                        d2 = d;
                        if (batterySipper2.drainType != BatterySipper.DrainType.AMBIENT_DISPLAY) {
                            d2 = d;
                            if (batterySipper2.drainType != BatterySipper.DrainType.UNACCOUNTED) {
                                d2 = d;
                                if (batterySipper2.drainType != BatterySipper.DrainType.BLUETOOTH) {
                                    d2 = d;
                                    if (batterySipper2.drainType != BatterySipper.DrainType.WIFI) {
                                        d2 = d;
                                        if (batterySipper2.drainType != BatterySipper.DrainType.IDLE) {
                                            d2 = d + batterySipper2.totalPowerMah;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (batterySipper2.drainType == BatterySipper.DrainType.SCREEN) {
                batterySipper = batterySipper2;
            }
            d = d2;
        }
        this.smearScreenBatterySipper(list, batterySipper);
        return d;
    }

    @VisibleForTesting
    public void setPackageManager(PackageManager packageManager) {
        this.mPackageManager = packageManager;
    }

    @VisibleForTesting
    public void setServicePackageArray(String[] arrstring) {
        this.mServicepackageArray = arrstring;
    }

    @VisibleForTesting
    public void setSystemPackageArray(String[] arrstring) {
        this.mSystemPackageArray = arrstring;
    }

    public boolean shouldHideSipper(BatterySipper batterySipper) {
        BatterySipper.DrainType drainType = batterySipper.drainType;
        boolean bl = drainType == BatterySipper.DrainType.IDLE || drainType == BatterySipper.DrainType.CELL || drainType == BatterySipper.DrainType.SCREEN || drainType == BatterySipper.DrainType.AMBIENT_DISPLAY || drainType == BatterySipper.DrainType.UNACCOUNTED || drainType == BatterySipper.DrainType.OVERCOUNTED || this.isTypeService(batterySipper) || this.isTypeSystem(batterySipper);
        return bl;
    }

    public void smearScreenBatterySipper(List<BatterySipper> list, BatterySipper batterySipper) {
        int n;
        long l = 0L;
        SparseLongArray sparseLongArray = new SparseLongArray();
        int n2 = list.size();
        for (n = 0; n < n2; ++n) {
            BatteryStats.Uid uid = list.get((int)n).uidObj;
            long l2 = l;
            if (uid != null) {
                l2 = this.getProcessForegroundTimeMs(uid, 0);
                sparseLongArray.put(uid.getUid(), l2);
                l2 = l + l2;
            }
            l = l2;
        }
        if (batterySipper != null && l >= 600000L) {
            double d = batterySipper.totalPowerMah;
            n2 = list.size();
            for (n = 0; n < n2; ++n) {
                batterySipper = list.get(n);
                batterySipper.screenPowerMah = (double)sparseLongArray.get(batterySipper.getUid(), 0L) * d / (double)l;
            }
        }
    }

    @UnsupportedAppUsage
    public void storeState() {
        sStatsXfer = this.mStats;
        sBatteryBroadcastXfer = this.mBatteryBroadcast;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void storeStatsHistoryInFile(String object) {
        Throwable throwable2222;
        block12 : {
            block13 : {
                ArrayMap<File, BatteryStats> arrayMap = sFileXfer;
                // MONITORENTER : arrayMap
                File file = BatteryStatsHelper.makeFilePath(this.mContext, (String)object);
                sFileXfer.put(file, this.getStats());
                FileOutputStream fileOutputStream = null;
                FileOutputStream fileOutputStream2 = null;
                object = fileOutputStream2;
                FileOutputStream fileOutputStream3 = fileOutputStream;
                object = fileOutputStream2;
                fileOutputStream3 = fileOutputStream;
                Object object2 = new FileOutputStream(file);
                fileOutputStream2 = object2;
                object = fileOutputStream2;
                fileOutputStream3 = fileOutputStream2;
                object2 = Parcel.obtain();
                object = fileOutputStream2;
                fileOutputStream3 = fileOutputStream2;
                this.getStats().writeToParcelWithoutUids((Parcel)object2, 0);
                object = fileOutputStream2;
                fileOutputStream3 = fileOutputStream2;
                fileOutputStream2.write(((Parcel)object2).marshall());
                fileOutputStream2.close();
                return;
                {
                    catch (IOException iOException) {
                        return;
                    }
                    catch (Throwable throwable2222) {
                        break block12;
                    }
                    catch (IOException iOException) {}
                    object = fileOutputStream3;
                    {
                        Log.w(TAG, "Unable to write history to file", iOException);
                        if (fileOutputStream3 == null) break block13;
                    }
                    fileOutputStream3.close();
                    return;
                }
            }
            // MONITOREXIT : arrayMap
            return;
        }
        if (object == null) throw throwable2222;
        try {
            ((FileOutputStream)object).close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        throw throwable2222;
    }

}

