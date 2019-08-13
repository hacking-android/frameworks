/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.bluetooth.BluetoothActivityEnergyInfo;
import android.bluetooth.UidTraffic;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.INetworkStatsService;
import android.net.NetworkStats;
import android.net.Uri;
import android.net.wifi.WifiActivityEnergyInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryStats;
import android.os.Build;
import android.os.Handler;
import android.os.IBatteryPropertiesRegistrar;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.WorkSource;
import android.os.connectivity.CellularBatteryStats;
import android.os.connectivity.GpsBatteryStats;
import android.os.connectivity.WifiBatteryStats;
import android.provider.Settings;
import android.telephony.ModemActivityInfo;
import android.telephony.SignalStrength;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.KeyValueListParser;
import android.util.Log;
import android.util.LogWriter;
import android.util.LongSparseArray;
import android.util.LongSparseLongArray;
import android.util.MutableInt;
import android.util.Pools;
import android.util.Printer;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.StatsLog;
import android.util.TimeUtils;
import android.util.Xml;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.EventLogTags;
import com.android.internal.os.AtomicFile;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.BatteryStatsHistory;
import com.android.internal.os.KernelCpuSpeedReader;
import com.android.internal.os.KernelCpuUidTimeReader;
import com.android.internal.os.KernelMemoryBandwidthStats;
import com.android.internal.os.KernelSingleUidTimeReader;
import com.android.internal.os.KernelWakelockReader;
import com.android.internal.os.KernelWakelockStats;
import com.android.internal.os.PowerProfile;
import com.android.internal.os.RailStats;
import com.android.internal.os.RpmStats;
import com.android.internal.os._$$Lambda$BatteryStatsImpl$7bfIWpn8X2h_hSzLD6dcuK4Ljuw;
import com.android.internal.os._$$Lambda$BatteryStatsImpl$B_TmZhQb712ePnuJTxvMe7P_YwQ;
import com.android.internal.os._$$Lambda$BatteryStatsImpl$Xvt9xdVPtevMWGIjcbxXf0_mr_c;
import com.android.internal.os._$$Lambda$BatteryStatsImpl$_l2oiaRDRhjCXI_PwXPsAhrgegI;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import libcore.util.EmptyArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class BatteryStatsImpl
extends BatteryStats {
    static final int BATTERY_DELTA_LEVEL_FLAG = 1;
    public static final int BATTERY_PLUGGED_NONE = 0;
    @UnsupportedAppUsage
    public static final Parcelable.Creator<BatteryStatsImpl> CREATOR;
    private static final boolean DEBUG = false;
    public static final boolean DEBUG_ENERGY = false;
    private static final boolean DEBUG_ENERGY_CPU = false;
    private static final boolean DEBUG_HISTORY = false;
    private static final boolean DEBUG_MEMORY = false;
    static final long DELAY_UPDATE_WAKELOCKS = 5000L;
    static final int DELTA_BATTERY_CHARGE_FLAG = 16777216;
    static final int DELTA_BATTERY_LEVEL_FLAG = 524288;
    static final int DELTA_EVENT_FLAG = 8388608;
    static final int DELTA_STATE2_FLAG = 2097152;
    static final int DELTA_STATE_FLAG = 1048576;
    static final int DELTA_STATE_MASK = -33554432;
    static final int DELTA_TIME_ABS = 524285;
    static final int DELTA_TIME_INT = 524286;
    static final int DELTA_TIME_LONG = 524287;
    static final int DELTA_TIME_MASK = 524287;
    static final int DELTA_WAKELOCK_FLAG = 4194304;
    private static final int MAGIC = -1166707595;
    static final int MAX_DAILY_ITEMS = 10;
    static final int MAX_LEVEL_STEPS = 200;
    private static final int MAX_WAKELOCKS_PER_UID;
    private static final double MILLISECONDS_IN_HOUR = 3600000.0;
    private static final long MILLISECONDS_IN_YEAR = 31536000000L;
    static final int MSG_REPORT_CHARGING = 3;
    static final int MSG_REPORT_CPU_UPDATE_NEEDED = 1;
    static final int MSG_REPORT_POWER_CHANGE = 2;
    static final int MSG_REPORT_RESET_STATS = 4;
    private static final int NUM_BT_TX_LEVELS = 1;
    private static final int NUM_WIFI_TX_LEVELS = 1;
    private static final long RPM_STATS_UPDATE_FREQ_MS = 1000L;
    static final int STATE_BATTERY_HEALTH_MASK = 7;
    static final int STATE_BATTERY_HEALTH_SHIFT = 26;
    static final int STATE_BATTERY_MASK = -16777216;
    static final int STATE_BATTERY_PLUG_MASK = 3;
    static final int STATE_BATTERY_PLUG_SHIFT = 24;
    static final int STATE_BATTERY_STATUS_MASK = 7;
    static final int STATE_BATTERY_STATUS_SHIFT = 29;
    private static final String TAG = "BatteryStatsImpl";
    private static final int USB_DATA_CONNECTED = 2;
    private static final int USB_DATA_DISCONNECTED = 1;
    private static final int USB_DATA_UNKNOWN = 0;
    private static final boolean USE_OLD_HISTORY = false;
    static final int VERSION = 186;
    @VisibleForTesting
    public static final int WAKE_LOCK_WEIGHT = 50;
    final BatteryStats.HistoryEventTracker mActiveEvents = new BatteryStats.HistoryEventTracker();
    int mActiveHistoryStates = -1;
    int mActiveHistoryStates2 = -1;
    int mAudioOnNesting;
    StopwatchTimer mAudioOnTimer;
    final ArrayList<StopwatchTimer> mAudioTurnedOnTimers = new ArrayList();
    final BatteryStatsHistory mBatteryStatsHistory;
    ControllerActivityCounterImpl mBluetoothActivity;
    int mBluetoothScanNesting;
    final ArrayList<StopwatchTimer> mBluetoothScanOnTimers = new ArrayList();
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    protected StopwatchTimer mBluetoothScanTimer;
    private BatteryCallback mCallback;
    int mCameraOnNesting;
    StopwatchTimer mCameraOnTimer;
    final ArrayList<StopwatchTimer> mCameraTurnedOnTimers = new ArrayList();
    int mChangedStates = 0;
    int mChangedStates2 = 0;
    final BatteryStats.LevelStepTracker mChargeStepTracker = new BatteryStats.LevelStepTracker(200);
    boolean mCharging = true;
    public final AtomicFile mCheckinFile;
    protected Clocks mClocks;
    @GuardedBy(value={"this"})
    final Constants mConstants;
    private long[] mCpuFreqs;
    @GuardedBy(value={"this"})
    private long mCpuTimeReadsTrackingStartTime = SystemClock.uptimeMillis();
    @VisibleForTesting
    protected KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader mCpuUidActiveTimeReader = new KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader(true);
    @VisibleForTesting
    protected KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader mCpuUidClusterTimeReader = new KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader(true);
    @VisibleForTesting
    protected KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader mCpuUidFreqTimeReader = new KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader(true);
    @VisibleForTesting
    protected KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader mCpuUidUserSysTimeReader = new KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader(true);
    final BatteryStats.HistoryStepDetails mCurHistoryStepDetails = new BatteryStats.HistoryStepDetails();
    long mCurStepCpuSystemTime;
    long mCurStepCpuUserTime;
    int mCurStepMode = 0;
    long mCurStepStatIOWaitTime;
    long mCurStepStatIdleTime;
    long mCurStepStatIrqTime;
    long mCurStepStatSoftIrqTime;
    long mCurStepStatSystemTime;
    long mCurStepStatUserTime;
    int mCurrentBatteryLevel;
    final BatteryStats.LevelStepTracker mDailyChargeStepTracker = new BatteryStats.LevelStepTracker(400);
    final BatteryStats.LevelStepTracker mDailyDischargeStepTracker = new BatteryStats.LevelStepTracker(400);
    public final AtomicFile mDailyFile;
    final ArrayList<BatteryStats.DailyItem> mDailyItems = new ArrayList();
    ArrayList<BatteryStats.PackageChange> mDailyPackageChanges;
    long mDailyStartTime = 0L;
    private final Runnable mDeferSetCharging = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
            synchronized (batteryStatsImpl) {
                if (BatteryStatsImpl.this.mOnBattery) {
                    return;
                }
                if (BatteryStatsImpl.this.setChargingLocked(true)) {
                    long l = BatteryStatsImpl.this.mClocks.uptimeMillis();
                    long l2 = BatteryStatsImpl.this.mClocks.elapsedRealtime();
                    BatteryStatsImpl.this.addHistoryRecordLocked(l2, l);
                }
                return;
            }
        }
    };
    int mDeviceIdleMode;
    StopwatchTimer mDeviceIdleModeFullTimer;
    StopwatchTimer mDeviceIdleModeLightTimer;
    boolean mDeviceIdling;
    StopwatchTimer mDeviceIdlingTimer;
    boolean mDeviceLightIdling;
    StopwatchTimer mDeviceLightIdlingTimer;
    int mDischargeAmountScreenDoze;
    int mDischargeAmountScreenDozeSinceCharge;
    int mDischargeAmountScreenOff;
    int mDischargeAmountScreenOffSinceCharge;
    int mDischargeAmountScreenOn;
    int mDischargeAmountScreenOnSinceCharge;
    private LongSamplingCounter mDischargeCounter;
    int mDischargeCurrentLevel;
    private LongSamplingCounter mDischargeDeepDozeCounter;
    private LongSamplingCounter mDischargeLightDozeCounter;
    int mDischargePlugLevel;
    private LongSamplingCounter mDischargeScreenDozeCounter;
    int mDischargeScreenDozeUnplugLevel;
    private LongSamplingCounter mDischargeScreenOffCounter;
    int mDischargeScreenOffUnplugLevel;
    int mDischargeScreenOnUnplugLevel;
    int mDischargeStartLevel;
    final BatteryStats.LevelStepTracker mDischargeStepTracker = new BatteryStats.LevelStepTracker(200);
    int mDischargeUnplugLevel;
    boolean mDistributeWakelockCpu;
    final ArrayList<StopwatchTimer> mDrawTimers = new ArrayList();
    String mEndPlatformVersion;
    private int mEstimatedBatteryCapacity = -1;
    private ExternalStatsSync mExternalSync = null;
    int mFlashlightOnNesting;
    StopwatchTimer mFlashlightOnTimer;
    final ArrayList<StopwatchTimer> mFlashlightTurnedOnTimers = new ArrayList();
    @UnsupportedAppUsage
    final ArrayList<StopwatchTimer> mFullTimers = new ArrayList();
    final ArrayList<StopwatchTimer> mFullWifiLockTimers = new ArrayList();
    boolean mGlobalWifiRunning;
    StopwatchTimer mGlobalWifiRunningTimer;
    int mGpsNesting;
    int mGpsSignalQualityBin = -1;
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    protected final StopwatchTimer[] mGpsSignalQualityTimer = new StopwatchTimer[2];
    public Handler mHandler;
    boolean mHasBluetoothReporting = false;
    boolean mHasModemReporting = false;
    boolean mHasWifiReporting = false;
    protected boolean mHaveBatteryLevel = false;
    int mHighDischargeAmountSinceCharge;
    BatteryStats.HistoryItem mHistory;
    final BatteryStats.HistoryItem mHistoryAddTmp = new BatteryStats.HistoryItem();
    long mHistoryBaseTime;
    final Parcel mHistoryBuffer = Parcel.obtain();
    int mHistoryBufferLastPos = -1;
    BatteryStats.HistoryItem mHistoryCache;
    final BatteryStats.HistoryItem mHistoryCur = new BatteryStats.HistoryItem();
    BatteryStats.HistoryItem mHistoryEnd;
    private BatteryStats.HistoryItem mHistoryIterator;
    BatteryStats.HistoryItem mHistoryLastEnd;
    final BatteryStats.HistoryItem mHistoryLastLastWritten = new BatteryStats.HistoryItem();
    final BatteryStats.HistoryItem mHistoryLastWritten = new BatteryStats.HistoryItem();
    final BatteryStats.HistoryItem mHistoryReadTmp = new BatteryStats.HistoryItem();
    final HashMap<BatteryStats.HistoryTag, Integer> mHistoryTagPool = new HashMap();
    int mInitStepMode = 0;
    private String mInitialAcquireWakeName;
    private int mInitialAcquireWakeUid = -1;
    boolean mInteractive;
    StopwatchTimer mInteractiveTimer;
    boolean mIsCellularTxPowerHigh = false;
    @GuardedBy(value={"this"})
    private boolean mIsPerProcessStateCpuDataStale;
    final SparseIntArray mIsolatedUids = new SparseIntArray();
    private boolean mIteratingHistory;
    @VisibleForTesting
    protected KernelCpuSpeedReader[] mKernelCpuSpeedReaders;
    private final KernelMemoryBandwidthStats mKernelMemoryBandwidthStats = new KernelMemoryBandwidthStats();
    private final LongSparseArray<SamplingTimer> mKernelMemoryStats = new LongSparseArray();
    @VisibleForTesting
    protected KernelSingleUidTimeReader mKernelSingleUidTimeReader;
    private final KernelWakelockReader mKernelWakelockReader = new KernelWakelockReader();
    private final HashMap<String, SamplingTimer> mKernelWakelockStats = new HashMap();
    private final BluetoothActivityInfoCache mLastBluetoothActivityInfo = new BluetoothActivityInfoCache();
    int mLastChargeStepLevel;
    int mLastChargingStateLevel;
    int mLastDischargeStepLevel;
    long mLastHistoryElapsedRealtime = 0L;
    BatteryStats.HistoryStepDetails mLastHistoryStepDetails = null;
    byte mLastHistoryStepLevel = (byte)(false ? 1 : 0);
    long mLastIdleTimeStart;
    private ModemActivityInfo mLastModemActivityInfo = new ModemActivityInfo(0L, 0, 0, new int[0], 0, 0);
    @GuardedBy(value={"mModemNetworkLock"})
    private NetworkStats mLastModemNetworkStats = new NetworkStats(0L, -1);
    @VisibleForTesting
    protected ArrayList<StopwatchTimer> mLastPartialTimers = new ArrayList();
    private long mLastRpmStatsUpdateTimeMs = -1000L;
    long mLastStepCpuSystemTime;
    long mLastStepCpuUserTime;
    long mLastStepStatIOWaitTime;
    long mLastStepStatIdleTime;
    long mLastStepStatIrqTime;
    long mLastStepStatSoftIrqTime;
    long mLastStepStatSystemTime;
    long mLastStepStatUserTime;
    String mLastWakeupReason = null;
    long mLastWakeupUptimeMs = 0L;
    @GuardedBy(value={"mWifiNetworkLock"})
    private NetworkStats mLastWifiNetworkStats = new NetworkStats(0L, -1);
    long mLastWriteTime = 0L;
    long mLongestFullIdleTime;
    long mLongestLightIdleTime;
    int mLowDischargeAmountSinceCharge;
    int mMaxChargeStepLevel;
    private int mMaxLearnedBatteryCapacity = -1;
    int mMinDischargeStepLevel;
    private int mMinLearnedBatteryCapacity = -1;
    LongSamplingCounter mMobileRadioActiveAdjustedTime;
    StopwatchTimer mMobileRadioActivePerAppTimer;
    long mMobileRadioActiveStartTime;
    StopwatchTimer mMobileRadioActiveTimer;
    LongSamplingCounter mMobileRadioActiveUnknownCount;
    LongSamplingCounter mMobileRadioActiveUnknownTime;
    int mMobileRadioPowerState = 1;
    int mModStepMode = 0;
    ControllerActivityCounterImpl mModemActivity;
    @GuardedBy(value={"mModemNetworkLock"})
    private String[] mModemIfaces = EmptyArray.STRING;
    private final Object mModemNetworkLock = new Object();
    final LongSamplingCounter[] mNetworkByteActivityCounters = new LongSamplingCounter[10];
    final LongSamplingCounter[] mNetworkPacketActivityCounters = new LongSamplingCounter[10];
    private final Pools.Pool<NetworkStats> mNetworkStatsPool = new Pools.SynchronizedPool<NetworkStats>(6);
    int mNextHistoryTagIdx = 0;
    long mNextMaxDailyDeadline = 0L;
    long mNextMinDailyDeadline = 0L;
    boolean mNoAutoReset;
    @GuardedBy(value={"this"})
    private int mNumAllUidCpuTimeReads;
    @GuardedBy(value={"this"})
    private long mNumBatchedSingleUidCpuTimeReads;
    private int mNumConnectivityChange;
    int mNumHistoryItems;
    int mNumHistoryTagChars = 0;
    @GuardedBy(value={"this"})
    private long mNumSingleUidCpuTimeReads;
    @GuardedBy(value={"this"})
    private int mNumUidsRemoved;
    boolean mOnBattery;
    @VisibleForTesting
    protected boolean mOnBatteryInternal;
    protected final TimeBase mOnBatteryScreenOffTimeBase = new TimeBase(true);
    protected final TimeBase mOnBatteryTimeBase = new TimeBase(true);
    @UnsupportedAppUsage
    @VisibleForTesting
    protected ArrayList<StopwatchTimer> mPartialTimers = new ArrayList();
    @GuardedBy(value={"this"})
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    protected Queue<UidToRemove> mPendingRemovedUids = new LinkedList<UidToRemove>();
    @GuardedBy(value={"this"})
    @VisibleForTesting
    protected final SparseIntArray mPendingUids = new SparseIntArray();
    @GuardedBy(value={"this"})
    public boolean mPerProcStateCpuTimesAvailable = true;
    int mPhoneDataConnectionType = -1;
    final StopwatchTimer[] mPhoneDataConnectionsTimer = new StopwatchTimer[22];
    boolean mPhoneOn;
    StopwatchTimer mPhoneOnTimer;
    private int mPhoneServiceState = -1;
    private int mPhoneServiceStateRaw = -1;
    StopwatchTimer mPhoneSignalScanningTimer;
    int mPhoneSignalStrengthBin = -1;
    int mPhoneSignalStrengthBinRaw = -1;
    final StopwatchTimer[] mPhoneSignalStrengthsTimer = new StopwatchTimer[5];
    private int mPhoneSimStateRaw = -1;
    private final PlatformIdleStateCallback mPlatformIdleStateCallback;
    @VisibleForTesting
    protected PowerProfile mPowerProfile;
    boolean mPowerSaveModeEnabled;
    StopwatchTimer mPowerSaveModeEnabledTimer;
    boolean mPretendScreenOff;
    public final RailEnergyDataCallback mRailEnergyDataCallback;
    int mReadHistoryChars;
    final BatteryStats.HistoryStepDetails mReadHistoryStepDetails = new BatteryStats.HistoryStepDetails();
    String[] mReadHistoryStrings;
    int[] mReadHistoryUids;
    private boolean mReadOverflow;
    long mRealtime;
    long mRealtimeStart;
    public boolean mRecordAllHistory;
    protected boolean mRecordingHistory = false;
    private final HashMap<String, SamplingTimer> mRpmStats = new HashMap();
    int mScreenBrightnessBin = -1;
    final StopwatchTimer[] mScreenBrightnessTimer = new StopwatchTimer[5];
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    protected StopwatchTimer mScreenDozeTimer;
    private final HashMap<String, SamplingTimer> mScreenOffRpmStats = new HashMap();
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    protected StopwatchTimer mScreenOnTimer;
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    protected int mScreenState = 0;
    int mSensorNesting;
    final SparseArray<ArrayList<StopwatchTimer>> mSensorTimers = new SparseArray();
    boolean mShuttingDown;
    long mStartClockTime;
    int mStartCount;
    String mStartPlatformVersion;
    private final AtomicFile mStatsFile;
    long mTempTotalCpuSystemTimeUs;
    long mTempTotalCpuUserTimeUs;
    final BatteryStats.HistoryStepDetails mTmpHistoryStepDetails = new BatteryStats.HistoryStepDetails();
    private final RailStats mTmpRailStats = new RailStats();
    private final RpmStats mTmpRpmStats = new RpmStats();
    private final KernelWakelockStats mTmpWakelockStats = new KernelWakelockStats();
    long mTrackRunningHistoryElapsedRealtime = 0L;
    long mTrackRunningHistoryUptime = 0L;
    final SparseArray<Uid> mUidStats = new SparseArray();
    long mUptime;
    long mUptimeStart;
    int mUsbDataState = 0;
    @VisibleForTesting
    protected UserInfoProvider mUserInfoProvider = null;
    int mVideoOnNesting;
    StopwatchTimer mVideoOnTimer;
    final ArrayList<StopwatchTimer> mVideoTurnedOnTimers = new ArrayList();
    long[][] mWakeLockAllocationsUs;
    boolean mWakeLockImportant;
    int mWakeLockNesting;
    private final HashMap<String, SamplingTimer> mWakeupReasonStats = new HashMap();
    StopwatchTimer mWifiActiveTimer;
    ControllerActivityCounterImpl mWifiActivity;
    final SparseArray<ArrayList<StopwatchTimer>> mWifiBatchedScanTimers = new SparseArray();
    int mWifiFullLockNesting = 0;
    @GuardedBy(value={"mWifiNetworkLock"})
    private String[] mWifiIfaces = EmptyArray.STRING;
    int mWifiMulticastNesting = 0;
    final ArrayList<StopwatchTimer> mWifiMulticastTimers = new ArrayList();
    StopwatchTimer mWifiMulticastWakelockTimer;
    private final Object mWifiNetworkLock = new Object();
    boolean mWifiOn;
    StopwatchTimer mWifiOnTimer;
    int mWifiRadioPowerState = 1;
    final ArrayList<StopwatchTimer> mWifiRunningTimers = new ArrayList();
    int mWifiScanNesting = 0;
    final ArrayList<StopwatchTimer> mWifiScanTimers = new ArrayList();
    int mWifiSignalStrengthBin = -1;
    final StopwatchTimer[] mWifiSignalStrengthsTimer = new StopwatchTimer[5];
    int mWifiState = -1;
    final StopwatchTimer[] mWifiStateTimer = new StopwatchTimer[8];
    int mWifiSupplState = -1;
    final StopwatchTimer[] mWifiSupplStateTimer = new StopwatchTimer[13];
    @UnsupportedAppUsage
    final ArrayList<StopwatchTimer> mWindowTimers = new ArrayList();
    final ReentrantLock mWriteLock = new ReentrantLock();

    static {
        MAX_WAKELOCKS_PER_UID = ActivityManager.isLowRamDeviceStatic() ? 40 : 200;
        CREATOR = new Parcelable.Creator<BatteryStatsImpl>(){

            @Override
            public BatteryStatsImpl createFromParcel(Parcel parcel) {
                return new BatteryStatsImpl(parcel);
            }

            public BatteryStatsImpl[] newArray(int n) {
                return new BatteryStatsImpl[n];
            }
        };
    }

    public BatteryStatsImpl() {
        this(new SystemClocks());
    }

    @UnsupportedAppUsage
    public BatteryStatsImpl(Parcel parcel) {
        this(new SystemClocks(), parcel);
    }

    public BatteryStatsImpl(Clocks clocks) {
        this.init(clocks);
        this.mStatsFile = null;
        this.mCheckinFile = null;
        this.mDailyFile = null;
        this.mBatteryStatsHistory = null;
        this.mHandler = null;
        this.mPlatformIdleStateCallback = null;
        this.mRailEnergyDataCallback = null;
        this.mUserInfoProvider = null;
        this.mConstants = new Constants(this.mHandler);
        this.clearHistoryLocked();
    }

    public BatteryStatsImpl(Clocks clocks, Parcel parcel) {
        this.init(clocks);
        this.mStatsFile = null;
        this.mCheckinFile = null;
        this.mDailyFile = null;
        this.mHandler = null;
        this.mExternalSync = null;
        this.mConstants = new Constants(this.mHandler);
        this.clearHistoryLocked();
        this.mBatteryStatsHistory = new BatteryStatsHistory(this, this.mHistoryBuffer);
        this.readFromParcel(parcel);
        this.mPlatformIdleStateCallback = null;
        this.mRailEnergyDataCallback = null;
    }

    private BatteryStatsImpl(Clocks object, File file, Handler handler, PlatformIdleStateCallback platformIdleStateCallback, RailEnergyDataCallback railEnergyDataCallback, UserInfoProvider userInfoProvider) {
        int n;
        this.init((Clocks)object);
        if (file == null) {
            this.mStatsFile = null;
            this.mBatteryStatsHistory = new BatteryStatsHistory(this, this.mHistoryBuffer);
        } else {
            this.mStatsFile = new AtomicFile(new File(file, "batterystats.bin"));
            this.mBatteryStatsHistory = new BatteryStatsHistory(this, file, this.mHistoryBuffer);
        }
        this.mCheckinFile = new AtomicFile(new File(file, "batterystats-checkin.bin"));
        this.mDailyFile = new AtomicFile(new File(file, "batterystats-daily.xml"));
        this.mHandler = new MyHandler(handler.getLooper());
        this.mConstants = new Constants(this.mHandler);
        ++this.mStartCount;
        this.mScreenOnTimer = new StopwatchTimer(this.mClocks, null, -1, null, this.mOnBatteryTimeBase);
        this.mScreenDozeTimer = new StopwatchTimer(this.mClocks, null, -1, null, this.mOnBatteryTimeBase);
        for (n = 0; n < 5; ++n) {
            this.mScreenBrightnessTimer[n] = new StopwatchTimer(this.mClocks, null, -100 - n, null, this.mOnBatteryTimeBase);
        }
        this.mInteractiveTimer = new StopwatchTimer(this.mClocks, null, -10, null, this.mOnBatteryTimeBase);
        this.mPowerSaveModeEnabledTimer = new StopwatchTimer(this.mClocks, null, -2, null, this.mOnBatteryTimeBase);
        this.mDeviceIdleModeLightTimer = new StopwatchTimer(this.mClocks, null, -11, null, this.mOnBatteryTimeBase);
        this.mDeviceIdleModeFullTimer = new StopwatchTimer(this.mClocks, null, -14, null, this.mOnBatteryTimeBase);
        this.mDeviceLightIdlingTimer = new StopwatchTimer(this.mClocks, null, -15, null, this.mOnBatteryTimeBase);
        this.mDeviceIdlingTimer = new StopwatchTimer(this.mClocks, null, -12, null, this.mOnBatteryTimeBase);
        this.mPhoneOnTimer = new StopwatchTimer(this.mClocks, null, -3, null, this.mOnBatteryTimeBase);
        for (n = 0; n < 5; ++n) {
            this.mPhoneSignalStrengthsTimer[n] = new StopwatchTimer(this.mClocks, null, -200 - n, null, this.mOnBatteryTimeBase);
        }
        this.mPhoneSignalScanningTimer = new StopwatchTimer(this.mClocks, null, -199, null, this.mOnBatteryTimeBase);
        for (n = 0; n < 22; ++n) {
            this.mPhoneDataConnectionsTimer[n] = new StopwatchTimer(this.mClocks, null, -300 - n, null, this.mOnBatteryTimeBase);
        }
        for (n = 0; n < 10; ++n) {
            this.mNetworkByteActivityCounters[n] = new LongSamplingCounter(this.mOnBatteryTimeBase);
            this.mNetworkPacketActivityCounters[n] = new LongSamplingCounter(this.mOnBatteryTimeBase);
        }
        this.mWifiActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 1);
        this.mBluetoothActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 1);
        this.mModemActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 5);
        this.mMobileRadioActiveTimer = new StopwatchTimer(this.mClocks, null, -400, null, this.mOnBatteryTimeBase);
        this.mMobileRadioActivePerAppTimer = new StopwatchTimer(this.mClocks, null, -401, null, this.mOnBatteryTimeBase);
        this.mMobileRadioActiveAdjustedTime = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mMobileRadioActiveUnknownTime = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mMobileRadioActiveUnknownCount = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mWifiMulticastWakelockTimer = new StopwatchTimer(this.mClocks, null, 23, null, this.mOnBatteryTimeBase);
        this.mWifiOnTimer = new StopwatchTimer(this.mClocks, null, -4, null, this.mOnBatteryTimeBase);
        this.mGlobalWifiRunningTimer = new StopwatchTimer(this.mClocks, null, -5, null, this.mOnBatteryTimeBase);
        for (n = 0; n < 8; ++n) {
            this.mWifiStateTimer[n] = new StopwatchTimer(this.mClocks, null, -600 - n, null, this.mOnBatteryTimeBase);
        }
        for (n = 0; n < 13; ++n) {
            this.mWifiSupplStateTimer[n] = new StopwatchTimer(this.mClocks, null, -700 - n, null, this.mOnBatteryTimeBase);
        }
        for (n = 0; n < 5; ++n) {
            this.mWifiSignalStrengthsTimer[n] = new StopwatchTimer(this.mClocks, null, -800 - n, null, this.mOnBatteryTimeBase);
        }
        this.mWifiActiveTimer = new StopwatchTimer(this.mClocks, null, -900, null, this.mOnBatteryTimeBase);
        for (n = 0; n < 2; ++n) {
            this.mGpsSignalQualityTimer[n] = new StopwatchTimer(this.mClocks, null, -1000 - n, null, this.mOnBatteryTimeBase);
        }
        this.mAudioOnTimer = new StopwatchTimer(this.mClocks, null, -7, null, this.mOnBatteryTimeBase);
        this.mVideoOnTimer = new StopwatchTimer(this.mClocks, null, -8, null, this.mOnBatteryTimeBase);
        this.mFlashlightOnTimer = new StopwatchTimer(this.mClocks, null, -9, null, this.mOnBatteryTimeBase);
        this.mCameraOnTimer = new StopwatchTimer(this.mClocks, null, -13, null, this.mOnBatteryTimeBase);
        this.mBluetoothScanTimer = new StopwatchTimer(this.mClocks, null, -14, null, this.mOnBatteryTimeBase);
        this.mDischargeScreenOffCounter = new LongSamplingCounter(this.mOnBatteryScreenOffTimeBase);
        this.mDischargeScreenDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mDischargeLightDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mDischargeDeepDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mDischargeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mOnBatteryInternal = false;
        this.mOnBattery = false;
        this.initTimes(this.mClocks.uptimeMillis() * 1000L, this.mClocks.elapsedRealtime() * 1000L);
        this.mEndPlatformVersion = object = Build.ID;
        this.mStartPlatformVersion = object;
        this.mDischargeStartLevel = 0;
        this.mDischargeUnplugLevel = 0;
        this.mDischargePlugLevel = -1;
        this.mDischargeCurrentLevel = 0;
        this.mCurrentBatteryLevel = 0;
        this.initDischarge();
        this.clearHistoryLocked();
        this.updateDailyDeadlineLocked();
        this.mPlatformIdleStateCallback = platformIdleStateCallback;
        this.mRailEnergyDataCallback = railEnergyDataCallback;
        this.mUserInfoProvider = userInfoProvider;
    }

    public BatteryStatsImpl(File file, Handler handler, PlatformIdleStateCallback platformIdleStateCallback, RailEnergyDataCallback railEnergyDataCallback, UserInfoProvider userInfoProvider) {
        this(new SystemClocks(), file, handler, platformIdleStateCallback, railEnergyDataCallback, userInfoProvider);
    }

    static /* synthetic */ int access$008(BatteryStatsImpl batteryStatsImpl) {
        int n = batteryStatsImpl.mNumUidsRemoved;
        batteryStatsImpl.mNumUidsRemoved = n + 1;
        return n;
    }

    static /* synthetic */ long access$1708(BatteryStatsImpl batteryStatsImpl) {
        long l = batteryStatsImpl.mNumSingleUidCpuTimeReads;
        batteryStatsImpl.mNumSingleUidCpuTimeReads = 1L + l;
        return l;
    }

    static /* synthetic */ long access$1808(BatteryStatsImpl batteryStatsImpl) {
        long l = batteryStatsImpl.mNumBatchedSingleUidCpuTimeReads;
        batteryStatsImpl.mNumBatchedSingleUidCpuTimeReads = 1L + l;
        return l;
    }

    private void addHistoryBufferLocked(long l, byte by, BatteryStats.HistoryItem historyItem) {
        if (!this.mIteratingHistory) {
            this.mHistoryBufferLastPos = this.mHistoryBuffer.dataPosition();
            this.mHistoryLastLastWritten.setTo(this.mHistoryLastWritten);
            this.mHistoryLastWritten.setTo(this.mHistoryBaseTime + l, by, historyItem);
            BatteryStats.HistoryItem historyItem2 = this.mHistoryLastWritten;
            historyItem2.states &= this.mActiveHistoryStates;
            historyItem2 = this.mHistoryLastWritten;
            historyItem2.states2 &= this.mActiveHistoryStates2;
            this.writeHistoryDelta(this.mHistoryBuffer, this.mHistoryLastWritten, this.mHistoryLastLastWritten);
            this.mLastHistoryElapsedRealtime = l;
            historyItem.wakelockTag = null;
            historyItem.wakeReasonTag = null;
            historyItem.eventCode = 0;
            historyItem.eventTag = null;
            return;
        }
        throw new IllegalStateException("Can't do this while iterating history!");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void addModemTxPowerToHistory(ModemActivityInfo object) {
        synchronized (this) {
            if (object == null) {
                return;
            }
            int[] arrn = ((ModemActivityInfo)object).getTxTimeMillis();
            if (arrn != null && arrn.length == 5) {
                long l = this.mClocks.elapsedRealtime();
                long l2 = this.mClocks.uptimeMillis();
                int n = 0;
                for (int i = 1; i < arrn.length; ++i) {
                    int n2 = n;
                    if (arrn[i] > arrn[n]) {
                        n2 = i;
                    }
                    n = n2;
                }
                if (n == 4) {
                    if (!this.mIsCellularTxPowerHigh) {
                        BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                        historyItem.states2 |= 524288;
                        this.addHistoryRecordLocked(l, l2);
                        this.mIsCellularTxPowerHigh = true;
                    }
                    return;
                }
                if (this.mIsCellularTxPowerHigh) {
                    BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                    historyItem.states2 &= -524289;
                    this.addHistoryRecordLocked(l, l2);
                    this.mIsCellularTxPowerHigh = false;
                }
                return;
            }
            return;
        }
    }

    private void addPackageChange(BatteryStats.PackageChange packageChange) {
        if (this.mDailyPackageChanges == null) {
            this.mDailyPackageChanges = new ArrayList();
        }
        this.mDailyPackageChanges.add(packageChange);
    }

    private int buildBatteryLevelInt(BatteryStats.HistoryItem historyItem) {
        return historyItem.batteryLevel << 25 & -33554432 | historyItem.batteryTemperature << 15 & 33521664 | historyItem.batteryVoltage << 1 & 32766;
    }

    private int buildStateInt(BatteryStats.HistoryItem historyItem) {
        int n = 0;
        if ((historyItem.batteryPlugType & 1) != 0) {
            n = 1;
        } else if ((historyItem.batteryPlugType & 2) != 0) {
            n = 2;
        } else if ((historyItem.batteryPlugType & 4) != 0) {
            n = 3;
        }
        return (historyItem.batteryStatus & 7) << 29 | (historyItem.batteryHealth & 7) << 26 | (n & 3) << 24 | historyItem.states & 16777215;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void commitPendingDataToDisk(Parcel parcel, AtomicFile atomicFile) {
        FileOutputStream fileOutputStream;
        this.mWriteLock.lock();
        FileOutputStream fileOutputStream2 = fileOutputStream = null;
        try {
            try {
                long l = SystemClock.uptimeMillis();
                fileOutputStream2 = fileOutputStream;
                fileOutputStream2 = fileOutputStream = atomicFile.startWrite();
                fileOutputStream.write(parcel.marshall());
                fileOutputStream2 = fileOutputStream;
                fileOutputStream.flush();
                fileOutputStream2 = fileOutputStream;
                atomicFile.finishWrite(fileOutputStream);
                fileOutputStream2 = fileOutputStream;
                EventLogTags.writeCommitSysConfigFile("batterystats", SystemClock.uptimeMillis() - l);
            }
            catch (IOException iOException) {
                Slog.w(TAG, "Error writing battery statistics", iOException);
                atomicFile.failWrite(fileOutputStream2);
            }
            parcel.recycle();
            this.mWriteLock.unlock();
            return;
        }
        catch (Throwable throwable2) {}
        parcel.recycle();
        this.mWriteLock.unlock();
        throw throwable2;
    }

    private void computeHistoryStepDetails(BatteryStats.HistoryStepDetails object, BatteryStats.HistoryStepDetails object2) {
        Object object3 = object2 != null ? this.mTmpHistoryStepDetails : object;
        this.requestImmediateCpuUpdate();
        if (object2 == null) {
            int n = this.mUidStats.size();
            for (int i = 0; i < n; ++i) {
                object = this.mUidStats.valueAt(i);
                ((Uid)object).mLastStepUserTime = ((Uid)object).mCurStepUserTime;
                ((Uid)object).mLastStepSystemTime = ((Uid)object).mCurStepSystemTime;
            }
            this.mLastStepCpuUserTime = this.mCurStepCpuUserTime;
            this.mLastStepCpuSystemTime = this.mCurStepCpuSystemTime;
            this.mLastStepStatUserTime = this.mCurStepStatUserTime;
            this.mLastStepStatSystemTime = this.mCurStepStatSystemTime;
            this.mLastStepStatIOWaitTime = this.mCurStepStatIOWaitTime;
            this.mLastStepStatIrqTime = this.mCurStepStatIrqTime;
            this.mLastStepStatSoftIrqTime = this.mCurStepStatSoftIrqTime;
            this.mLastStepStatIdleTime = this.mCurStepStatIdleTime;
            ((BatteryStats.HistoryStepDetails)object3).clear();
            return;
        }
        ((BatteryStats.HistoryStepDetails)object).userTime = (int)(this.mCurStepCpuUserTime - this.mLastStepCpuUserTime);
        ((BatteryStats.HistoryStepDetails)object).systemTime = (int)(this.mCurStepCpuSystemTime - this.mLastStepCpuSystemTime);
        ((BatteryStats.HistoryStepDetails)object).statUserTime = (int)(this.mCurStepStatUserTime - this.mLastStepStatUserTime);
        ((BatteryStats.HistoryStepDetails)object).statSystemTime = (int)(this.mCurStepStatSystemTime - this.mLastStepStatSystemTime);
        ((BatteryStats.HistoryStepDetails)object).statIOWaitTime = (int)(this.mCurStepStatIOWaitTime - this.mLastStepStatIOWaitTime);
        ((BatteryStats.HistoryStepDetails)object).statIrqTime = (int)(this.mCurStepStatIrqTime - this.mLastStepStatIrqTime);
        ((BatteryStats.HistoryStepDetails)object).statSoftIrqTime = (int)(this.mCurStepStatSoftIrqTime - this.mLastStepStatSoftIrqTime);
        ((BatteryStats.HistoryStepDetails)object).statIdlTime = (int)(this.mCurStepStatIdleTime - this.mLastStepStatIdleTime);
        ((BatteryStats.HistoryStepDetails)object).appCpuUid3 = -1;
        ((BatteryStats.HistoryStepDetails)object).appCpuUid2 = -1;
        ((BatteryStats.HistoryStepDetails)object).appCpuUid1 = -1;
        ((BatteryStats.HistoryStepDetails)object).appCpuUTime3 = 0;
        ((BatteryStats.HistoryStepDetails)object).appCpuUTime2 = 0;
        ((BatteryStats.HistoryStepDetails)object).appCpuUTime1 = 0;
        ((BatteryStats.HistoryStepDetails)object).appCpuSTime3 = 0;
        ((BatteryStats.HistoryStepDetails)object).appCpuSTime2 = 0;
        ((BatteryStats.HistoryStepDetails)object).appCpuSTime1 = 0;
        int n = this.mUidStats.size();
        for (int i = 0; i < n; ++i) {
            object2 = this.mUidStats.valueAt(i);
            int n2 = (int)(((Uid)object2).mCurStepUserTime - ((Uid)object2).mLastStepUserTime);
            int n3 = (int)(((Uid)object2).mCurStepSystemTime - ((Uid)object2).mLastStepSystemTime);
            int n4 = n2 + n3;
            ((Uid)object2).mLastStepUserTime = ((Uid)object2).mCurStepUserTime;
            ((Uid)object2).mLastStepSystemTime = ((Uid)object2).mCurStepSystemTime;
            if (n4 <= ((BatteryStats.HistoryStepDetails)object).appCpuUTime3 + ((BatteryStats.HistoryStepDetails)object).appCpuSTime3) continue;
            if (n4 <= ((BatteryStats.HistoryStepDetails)object).appCpuUTime2 + ((BatteryStats.HistoryStepDetails)object).appCpuSTime2) {
                ((BatteryStats.HistoryStepDetails)object).appCpuUid3 = ((Uid)object2).mUid;
                ((BatteryStats.HistoryStepDetails)object).appCpuUTime3 = n2;
                ((BatteryStats.HistoryStepDetails)object).appCpuSTime3 = n3;
                continue;
            }
            ((BatteryStats.HistoryStepDetails)object).appCpuUid3 = ((BatteryStats.HistoryStepDetails)object).appCpuUid2;
            ((BatteryStats.HistoryStepDetails)object).appCpuUTime3 = ((BatteryStats.HistoryStepDetails)object).appCpuUTime2;
            ((BatteryStats.HistoryStepDetails)object).appCpuSTime3 = ((BatteryStats.HistoryStepDetails)object).appCpuSTime2;
            if (n4 <= ((BatteryStats.HistoryStepDetails)object).appCpuUTime1 + ((BatteryStats.HistoryStepDetails)object).appCpuSTime1) {
                ((BatteryStats.HistoryStepDetails)object).appCpuUid2 = ((Uid)object2).mUid;
                ((BatteryStats.HistoryStepDetails)object).appCpuUTime2 = n2;
                ((BatteryStats.HistoryStepDetails)object).appCpuSTime2 = n3;
                continue;
            }
            ((BatteryStats.HistoryStepDetails)object).appCpuUid2 = ((BatteryStats.HistoryStepDetails)object).appCpuUid1;
            ((BatteryStats.HistoryStepDetails)object).appCpuUTime2 = ((BatteryStats.HistoryStepDetails)object).appCpuUTime1;
            ((BatteryStats.HistoryStepDetails)object).appCpuSTime2 = ((BatteryStats.HistoryStepDetails)object).appCpuSTime1;
            ((BatteryStats.HistoryStepDetails)object).appCpuUid1 = ((Uid)object2).mUid;
            ((BatteryStats.HistoryStepDetails)object).appCpuUTime1 = n2;
            ((BatteryStats.HistoryStepDetails)object).appCpuSTime1 = n3;
        }
        this.mLastStepCpuUserTime = this.mCurStepCpuUserTime;
        this.mLastStepCpuSystemTime = this.mCurStepCpuSystemTime;
        this.mLastStepStatUserTime = this.mCurStepStatUserTime;
        this.mLastStepStatSystemTime = this.mCurStepStatSystemTime;
        this.mLastStepStatIOWaitTime = this.mCurStepStatIOWaitTime;
        this.mLastStepStatIrqTime = this.mCurStepStatIrqTime;
        this.mLastStepStatSoftIrqTime = this.mCurStepStatSoftIrqTime;
        this.mLastStepStatIdleTime = this.mCurStepStatIdleTime;
    }

    private long computeTimePerLevel(long[] arrl, int n) {
        if (n <= 0) {
            return -1L;
        }
        long l = 0L;
        for (int i = 0; i < n; ++i) {
            l += arrl[i] & 0xFFFFFFFFFFL;
        }
        return l / (long)n;
    }

    private static void detachIfNotNull(ControllerActivityCounterImpl controllerActivityCounterImpl) {
        if (controllerActivityCounterImpl != null) {
            controllerActivityCounterImpl.detach();
        }
    }

    private static <T extends TimeBaseObs> void detachIfNotNull(T t) {
        if (t != null) {
            t.detach();
        }
    }

    private static <T extends TimeBaseObs> void detachIfNotNull(T[] arrT) {
        if (arrT != null) {
            for (int i = 0; i < arrT.length; ++i) {
                BatteryStatsImpl.detachIfNotNull(arrT[i]);
            }
        }
    }

    private static <T extends TimeBaseObs> void detachIfNotNull(T[][] arrT) {
        if (arrT != null) {
            for (int i = 0; i < arrT.length; ++i) {
                BatteryStatsImpl.detachIfNotNull(arrT[i]);
            }
        }
    }

    private static String[] excludeFromStringArray(String[] arrstring, String arrstring2) {
        int n = ArrayUtils.indexOf(arrstring, arrstring2);
        if (n >= 0) {
            arrstring2 = new String[arrstring.length - 1];
            if (n > 0) {
                System.arraycopy(arrstring, 0, arrstring2, 0, n);
            }
            if (n < arrstring.length - 1) {
                System.arraycopy(arrstring, n + 1, arrstring2, n, arrstring.length - n - 1);
            }
            return arrstring2;
        }
        return arrstring;
    }

    private int fixPhoneServiceState(int n, int n2) {
        int n3 = n;
        if (this.mPhoneSimStateRaw == 1) {
            n3 = n;
            if (n == 1) {
                n3 = n;
                if (n2 > 0) {
                    n3 = 0;
                }
            }
        }
        return n3;
    }

    private int getAttributionUid(int n, WorkSource.WorkChain workChain) {
        if (workChain != null) {
            return this.mapUid(workChain.getAttributionUid());
        }
        return this.mapUid(n);
    }

    private ModemActivityInfo getDeltaModemActivityInfo(ModemActivityInfo modemActivityInfo) {
        if (modemActivityInfo == null) {
            return null;
        }
        Object object = new int[5];
        for (int i = 0; i < 5; ++i) {
            object[i] = modemActivityInfo.getTxTimeMillis()[i] - this.mLastModemActivityInfo.getTxTimeMillis()[i];
        }
        object = new ModemActivityInfo(modemActivityInfo.getTimestamp(), modemActivityInfo.getSleepTimeMillis() - this.mLastModemActivityInfo.getSleepTimeMillis(), modemActivityInfo.getIdleTimeMillis() - this.mLastModemActivityInfo.getIdleTimeMillis(), (int[])object, modemActivityInfo.getRxTimeMillis() - this.mLastModemActivityInfo.getRxTimeMillis(), modemActivityInfo.getEnergyUsed() - this.mLastModemActivityInfo.getEnergyUsed());
        this.mLastModemActivityInfo = modemActivityInfo;
        return object;
    }

    private int getPowerManagerWakeLockLevel(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 18) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Illegal wakelock type in batterystats: ");
                        stringBuilder.append(n);
                        Slog.e(TAG, stringBuilder.toString());
                        return -1;
                    }
                    return 128;
                }
                Slog.e(TAG, "Illegal window wakelock type observed in batterystats.");
                return -1;
            }
            return 26;
        }
        return 1;
    }

    private static String[] includeInStringArray(String[] arrstring, String string2) {
        if (ArrayUtils.indexOf(arrstring, string2) >= 0) {
            return arrstring;
        }
        String[] arrstring2 = new String[arrstring.length + 1];
        System.arraycopy(arrstring, 0, arrstring2, 0, arrstring.length);
        arrstring2[arrstring.length] = string2;
        return arrstring2;
    }

    private void init(Clocks clocks) {
        this.mClocks = clocks;
    }

    private void initActiveHistoryEventsLocked(long l, long l2) {
        for (int i = 0; i < 22; ++i) {
            HashMap<String, SparseIntArray> hashMap;
            if (!this.mRecordAllHistory && i == 1 || (hashMap = this.mActiveEvents.getStateForEvent(i)) == null) continue;
            for (Map.Entry<String, SparseIntArray> entry : hashMap.entrySet()) {
                SparseIntArray sparseIntArray = entry.getValue();
                for (int j = 0; j < sparseIntArray.size(); ++j) {
                    this.addHistoryEventLocked(l, l2, i, entry.getKey(), sparseIntArray.keyAt(j));
                }
            }
        }
    }

    @GuardedBy(value={"this"})
    private boolean initKernelSingleUidTimeReaderLocked() {
        Object object = this.mKernelSingleUidTimeReader;
        boolean bl = false;
        if (object == null) {
            object = this.mPowerProfile;
            if (object == null) {
                return false;
            }
            if (this.mCpuFreqs == null) {
                this.mCpuFreqs = this.mCpuUidFreqTimeReader.readFreqs((PowerProfile)object);
            }
            if ((object = this.mCpuFreqs) != null) {
                this.mKernelSingleUidTimeReader = new KernelSingleUidTimeReader(((long[])object).length);
            } else {
                this.mPerProcStateCpuTimesAvailable = this.mCpuUidFreqTimeReader.allUidTimesAvailable();
                return false;
            }
        }
        if (this.mCpuUidFreqTimeReader.allUidTimesAvailable() && this.mKernelSingleUidTimeReader.singleUidCpuTimesAvailable()) {
            bl = true;
        }
        this.mPerProcStateCpuTimesAvailable = bl;
        return true;
    }

    public static boolean isOnBattery(int n, int n2) {
        boolean bl = true;
        if (n != 0 || n2 == 1) {
            bl = false;
        }
        return bl;
    }

    private void noteAlarmStartOrFinishLocked(int n, String string2, WorkSource object, int n2) {
        block4 : {
            long l;
            long l2;
            block3 : {
                int n3;
                if (!this.mRecordAllHistory) {
                    return;
                }
                l2 = this.mClocks.elapsedRealtime();
                l = this.mClocks.uptimeMillis();
                int n4 = 0;
                if (object == null) break block3;
                for (n3 = 0; n3 < ((WorkSource)object).size(); ++n3) {
                    n2 = this.mapUid(((WorkSource)object).get(n3));
                    if (!this.mActiveEvents.updateState(n, string2, n2, n4)) continue;
                    this.addHistoryEventLocked(l2, l, n, string2, n2);
                }
                if ((object = ((WorkSource)object).getWorkChains()) == null) break block4;
                for (n3 = 0; n3 < object.size(); ++n3) {
                    n2 = this.mapUid(((WorkSource.WorkChain)object.get(n3)).getAttributionUid());
                    if (!this.mActiveEvents.updateState(n, string2, n2, n4)) continue;
                    this.addHistoryEventLocked(l2, l, n, string2, n2);
                }
                break block4;
            }
            if (!this.mActiveEvents.updateState(n, string2, n2 = this.mapUid(n2), 0)) break block4;
            this.addHistoryEventLocked(l2, l, n, string2, n2);
        }
    }

    private void noteBluetoothScanStartedLocked(WorkSource.WorkChain parcelable, int n, boolean bl) {
        n = this.getAttributionUid(n, (WorkSource.WorkChain)parcelable);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (this.mBluetoothScanNesting == 0) {
            parcelable = this.mHistoryCur;
            ((BatteryStats.HistoryItem)parcelable).states2 |= 1048576;
            this.addHistoryRecordLocked(l, l2);
            this.mBluetoothScanTimer.startRunningLocked(l);
        }
        ++this.mBluetoothScanNesting;
        this.getUidStatsLocked(n).noteBluetoothScanStartedLocked(l, bl);
    }

    private void noteBluetoothScanStoppedLocked(WorkSource.WorkChain parcelable, int n, boolean bl) {
        n = this.getAttributionUid(n, (WorkSource.WorkChain)parcelable);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        --this.mBluetoothScanNesting;
        if (this.mBluetoothScanNesting == 0) {
            parcelable = this.mHistoryCur;
            ((BatteryStats.HistoryItem)parcelable).states2 &= -1048577;
            this.addHistoryRecordLocked(l, l2);
            this.mBluetoothScanTimer.stopRunningLocked(l);
        }
        this.getUidStatsLocked(n).noteBluetoothScanStoppedLocked(l, bl);
    }

    private void noteLongPartialWakeLockFinishInternal(String string2, String string3, int n) {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (string3 != null) {
            string2 = string3;
        }
        if (!this.mActiveEvents.updateState(16404, string2, n, 0)) {
            return;
        }
        this.addHistoryEventLocked(l, l2, 16404, string2, n);
    }

    private void noteLongPartialWakeLockStartInternal(String string2, String string3, int n) {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (string3 != null) {
            string2 = string3;
        }
        if (!this.mActiveEvents.updateState(32788, string2, n, 0)) {
            return;
        }
        this.addHistoryEventLocked(l, l2, 32788, string2, n);
    }

    private void noteMobileRadioApWakeupLocked(long l, long l2, int n) {
        n = this.mapUid(n);
        this.addHistoryEventLocked(l, l2, 19, "", n);
        this.getUidStatsLocked(n).noteMobileRadioApWakeupLocked();
    }

    private void noteStartGpsLocked(int n, WorkSource.WorkChain workChain) {
        n = this.getAttributionUid(n, workChain);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (this.mGpsNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states |= 536870912;
            this.addHistoryRecordLocked(l, l2);
        }
        ++this.mGpsNesting;
        if (workChain == null) {
            StatsLog.write_non_chained(6, n, null, 1);
        } else {
            StatsLog.write(6, workChain.getUids(), workChain.getTags(), 1);
        }
        this.getUidStatsLocked(n).noteStartGps(l);
    }

    private void noteStopGpsLocked(int n, WorkSource.WorkChain workChain) {
        n = this.getAttributionUid(n, workChain);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        --this.mGpsNesting;
        if (this.mGpsNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states &= -536870913;
            this.addHistoryRecordLocked(l, l2);
            this.stopAllGpsSignalQualityTimersLocked(-1);
            this.mGpsSignalQualityBin = -1;
        }
        if (workChain == null) {
            StatsLog.write_non_chained(6, n, null, 0);
        } else {
            StatsLog.write(6, workChain.getUids(), workChain.getTags(), 0);
        }
        this.getUidStatsLocked(n).noteStopGps(l);
    }

    private void noteUsbConnectionStateLocked(boolean bl) {
        int n = bl ? 2 : 1;
        if (this.mUsbDataState != n) {
            this.mUsbDataState = n;
            if (bl) {
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states2 |= 262144;
            } else {
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states2 &= -262145;
            }
            this.addHistoryRecordLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis());
        }
    }

    private void noteWifiRadioApWakeupLocked(long l, long l2, int n) {
        n = this.mapUid(n);
        this.addHistoryEventLocked(l, l2, 19, "", n);
        this.getUidStatsLocked(n).noteWifiRadioApWakeupLocked();
    }

    private void readBatteryLevelInt(int n, BatteryStats.HistoryItem historyItem) {
        historyItem.batteryLevel = (byte)((-33554432 & n) >>> 25);
        historyItem.batteryTemperature = (short)((33521664 & n) >>> 15);
        historyItem.batteryVoltage = (char)((n & 32766) >>> 1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readDailyItemsLocked(XmlPullParser object) {
        try {
            int n;
            int n2;
            while ((n = object.next()) != 2 && n != 1) {
            }
            if (n != 2) {
                object = new IllegalStateException("no start tag found");
                throw object;
            }
            n = object.getDepth();
            while ((n2 = object.next()) != 1) {
                if (n2 == 3) {
                    if (object.getDepth() <= n) return;
                }
                if (n2 == 3 || n2 == 4) continue;
                if (object.getName().equals("item")) {
                    this.readDailyItemTagLocked((XmlPullParser)object);
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown element under <daily-items>: ");
                stringBuilder.append(object.getName());
                Slog.w(TAG, stringBuilder.toString());
                XmlUtils.skipCurrentTag((XmlPullParser)object);
            }
            return;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed parsing daily ");
            ((StringBuilder)object).append(indexOutOfBoundsException);
            Slog.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed parsing daily ");
            stringBuilder.append(iOException);
            Slog.w(TAG, stringBuilder.toString());
            return;
        }
        catch (XmlPullParserException xmlPullParserException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed parsing daily ");
            ((StringBuilder)object).append((Object)xmlPullParserException);
            Slog.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed parsing daily ");
            stringBuilder.append(numberFormatException);
            Slog.w(TAG, stringBuilder.toString());
            return;
        }
        catch (NullPointerException nullPointerException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed parsing daily ");
            ((StringBuilder)object).append(nullPointerException);
            Slog.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        catch (IllegalStateException illegalStateException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed parsing daily ");
            ((StringBuilder)object).append(illegalStateException);
            Slog.w(TAG, ((StringBuilder)object).toString());
        }
    }

    private void readHistoryTag(int n, BatteryStats.HistoryTag historyTag) {
        String[] arrstring = this.mReadHistoryStrings;
        if (n < arrstring.length) {
            historyTag.string = arrstring[n];
            historyTag.uid = this.mReadHistoryUids[n];
        } else {
            historyTag.string = null;
            historyTag.uid = 0;
        }
        historyTag.poolIdx = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private NetworkStats readNetworkStatsLocked(String[] arrstring) {
        try {
            if (ArrayUtils.isEmpty(arrstring)) return null;
            INetworkStatsService iNetworkStatsService = INetworkStatsService.Stub.asInterface(ServiceManager.getService("netstats"));
            if (iNetworkStatsService != null) {
                return iNetworkStatsService.getDetailedUidStats(arrstring);
            }
            Slog.e(TAG, "Failed to get networkStatsService ");
            return null;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("failed to read network stats for ifaces: ");
            stringBuilder.append(Arrays.toString(arrstring));
            stringBuilder.append(remoteException);
            Slog.e(TAG, stringBuilder.toString());
        }
        return null;
    }

    private void recordCurrentTimeChangeLocked(long l, long l2, long l3) {
        if (this.mRecordingHistory) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.currentTime = l;
            this.addHistoryBufferLocked(l2, (byte)5, historyItem);
            this.mHistoryCur.currentTime = 0L;
        }
    }

    private void recordShutdownLocked(long l, long l2) {
        if (this.mRecordingHistory) {
            this.mHistoryCur.currentTime = System.currentTimeMillis();
            this.addHistoryBufferLocked(l, (byte)8, this.mHistoryCur);
            this.mHistoryCur.currentTime = 0L;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void registerUsbStateReceiver(Context object) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_STATE");
        ((Context)object).registerReceiver(new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onReceive(Context context, Intent parcelable) {
                boolean bl = parcelable.getBooleanExtra("connected", false);
                parcelable = BatteryStatsImpl.this;
                synchronized (parcelable) {
                    BatteryStatsImpl.this.noteUsbConnectionStateLocked(bl);
                    return;
                }
            }
        }, intentFilter);
        synchronized (this) {
            if (this.mUsbDataState == 0) {
                boolean bl;
                object = ((Context)object).registerReceiver(null, intentFilter);
                boolean bl2 = bl = false;
                if (object != null) {
                    bl2 = bl;
                    if (((Intent)object).getBooleanExtra("connected", false)) {
                        bl2 = true;
                    }
                }
                this.noteUsbConnectionStateLocked(bl2);
            }
            return;
        }
    }

    private void reportChangesToStatsLog(BatteryStats.HistoryItem historyItem, int n, int n2, int n3) {
        if (historyItem == null || historyItem.batteryStatus != n) {
            StatsLog.write(31, n);
        }
        if (historyItem == null || historyItem.batteryPlugType != n2) {
            StatsLog.write(32, n2);
        }
        if (historyItem == null || historyItem.batteryLevel != n3) {
            StatsLog.write(30, n3);
        }
    }

    private void requestImmediateCpuUpdate() {
        this.mExternalSync.scheduleCpuSyncDueToWakelockChange(0L);
    }

    private void requestWakelockCpuUpdate() {
        this.mExternalSync.scheduleCpuSyncDueToWakelockChange(5000L);
    }

    private void resetAllStatsLocked() {
        int n;
        long l = this.mClocks.uptimeMillis();
        long l2 = this.mClocks.elapsedRealtime();
        this.mStartCount = 0;
        this.initTimes(l * 1000L, l2 * 1000L);
        this.mScreenOnTimer.reset(false);
        this.mScreenDozeTimer.reset(false);
        for (n = 0; n < 5; ++n) {
            this.mScreenBrightnessTimer[n].reset(false);
        }
        PowerProfile object2 = this.mPowerProfile;
        this.mEstimatedBatteryCapacity = object2 != null ? (int)object2.getBatteryCapacity() : -1;
        this.mMinLearnedBatteryCapacity = -1;
        this.mMaxLearnedBatteryCapacity = -1;
        this.mInteractiveTimer.reset(false);
        this.mPowerSaveModeEnabledTimer.reset(false);
        this.mLastIdleTimeStart = l2;
        this.mLongestLightIdleTime = 0L;
        this.mLongestFullIdleTime = 0L;
        this.mDeviceIdleModeLightTimer.reset(false);
        this.mDeviceIdleModeFullTimer.reset(false);
        this.mDeviceLightIdlingTimer.reset(false);
        this.mDeviceIdlingTimer.reset(false);
        this.mPhoneOnTimer.reset(false);
        this.mAudioOnTimer.reset(false);
        this.mVideoOnTimer.reset(false);
        this.mFlashlightOnTimer.reset(false);
        this.mCameraOnTimer.reset(false);
        this.mBluetoothScanTimer.reset(false);
        for (n = 0; n < 5; ++n) {
            this.mPhoneSignalStrengthsTimer[n].reset(false);
        }
        this.mPhoneSignalScanningTimer.reset(false);
        for (n = 0; n < 22; ++n) {
            this.mPhoneDataConnectionsTimer[n].reset(false);
        }
        for (n = 0; n < 10; ++n) {
            this.mNetworkByteActivityCounters[n].reset(false);
            this.mNetworkPacketActivityCounters[n].reset(false);
        }
        this.mMobileRadioActiveTimer.reset(false);
        this.mMobileRadioActivePerAppTimer.reset(false);
        this.mMobileRadioActiveAdjustedTime.reset(false);
        this.mMobileRadioActiveUnknownTime.reset(false);
        this.mMobileRadioActiveUnknownCount.reset(false);
        this.mWifiOnTimer.reset(false);
        this.mGlobalWifiRunningTimer.reset(false);
        for (n = 0; n < 8; ++n) {
            this.mWifiStateTimer[n].reset(false);
        }
        for (n = 0; n < 13; ++n) {
            this.mWifiSupplStateTimer[n].reset(false);
        }
        for (n = 0; n < 5; ++n) {
            this.mWifiSignalStrengthsTimer[n].reset(false);
        }
        this.mWifiMulticastWakelockTimer.reset(false);
        this.mWifiActiveTimer.reset(false);
        this.mWifiActivity.reset(false);
        for (n = 0; n < 2; ++n) {
            this.mGpsSignalQualityTimer[n].reset(false);
        }
        this.mBluetoothActivity.reset(false);
        this.mModemActivity.reset(false);
        this.mNumConnectivityChange = 0;
        n = 0;
        while (n < this.mUidStats.size()) {
            int n2 = n;
            if (this.mUidStats.valueAt(n).reset(l * 1000L, l2 * 1000L)) {
                this.mUidStats.valueAt(n).detachFromTimeBase();
                SparseArray<Uid> sparseArray = this.mUidStats;
                sparseArray.remove(sparseArray.keyAt(n));
                n2 = n - 1;
            }
            n = n2 + 1;
        }
        if (this.mRpmStats.size() > 0) {
            for (SamplingTimer samplingTimer : this.mRpmStats.values()) {
                this.mOnBatteryTimeBase.remove(samplingTimer);
            }
            this.mRpmStats.clear();
        }
        if (this.mScreenOffRpmStats.size() > 0) {
            for (SamplingTimer samplingTimer : this.mScreenOffRpmStats.values()) {
                this.mOnBatteryScreenOffTimeBase.remove(samplingTimer);
            }
            this.mScreenOffRpmStats.clear();
        }
        if (this.mKernelWakelockStats.size() > 0) {
            for (SamplingTimer samplingTimer : this.mKernelWakelockStats.values()) {
                this.mOnBatteryScreenOffTimeBase.remove(samplingTimer);
            }
            this.mKernelWakelockStats.clear();
        }
        if (this.mKernelMemoryStats.size() > 0) {
            for (n = 0; n < this.mKernelMemoryStats.size(); ++n) {
                this.mOnBatteryTimeBase.remove(this.mKernelMemoryStats.valueAt(n));
            }
            this.mKernelMemoryStats.clear();
        }
        if (this.mWakeupReasonStats.size() > 0) {
            for (SamplingTimer samplingTimer : this.mWakeupReasonStats.values()) {
                this.mOnBatteryTimeBase.remove(samplingTimer);
            }
            this.mWakeupReasonStats.clear();
        }
        this.mTmpRailStats.reset();
        this.mLastHistoryStepDetails = null;
        this.mLastStepCpuSystemTime = 0L;
        this.mLastStepCpuUserTime = 0L;
        this.mCurStepCpuSystemTime = 0L;
        this.mCurStepCpuUserTime = 0L;
        this.mCurStepCpuUserTime = 0L;
        this.mLastStepCpuUserTime = 0L;
        this.mCurStepCpuSystemTime = 0L;
        this.mLastStepCpuSystemTime = 0L;
        this.mCurStepStatUserTime = 0L;
        this.mLastStepStatUserTime = 0L;
        this.mCurStepStatSystemTime = 0L;
        this.mLastStepStatSystemTime = 0L;
        this.mCurStepStatIOWaitTime = 0L;
        this.mLastStepStatIOWaitTime = 0L;
        this.mCurStepStatIrqTime = 0L;
        this.mLastStepStatIrqTime = 0L;
        this.mCurStepStatSoftIrqTime = 0L;
        this.mLastStepStatSoftIrqTime = 0L;
        this.mCurStepStatIdleTime = 0L;
        this.mLastStepStatIdleTime = 0L;
        this.mNumAllUidCpuTimeReads = 0;
        this.mNumUidsRemoved = 0;
        this.initDischarge();
        this.clearHistoryLocked();
        this.mBatteryStatsHistory.resetAllFiles();
        this.mHandler.sendEmptyMessage(4);
    }

    private static boolean resetIfNotNull(ControllerActivityCounterImpl controllerActivityCounterImpl, boolean bl) {
        if (controllerActivityCounterImpl != null) {
            controllerActivityCounterImpl.reset(bl);
        }
        return true;
    }

    private static <T extends TimeBaseObs> boolean resetIfNotNull(T t, boolean bl) {
        if (t != null) {
            return t.reset(bl);
        }
        return true;
    }

    private static <T extends TimeBaseObs> boolean resetIfNotNull(T[] arrT, boolean bl) {
        if (arrT != null) {
            boolean bl2 = true;
            for (int i = 0; i < arrT.length; ++i) {
                bl2 &= BatteryStatsImpl.resetIfNotNull(arrT[i], bl);
            }
            return bl2;
        }
        return true;
    }

    private static <T extends TimeBaseObs> boolean resetIfNotNull(T[][] arrT, boolean bl) {
        if (arrT != null) {
            boolean bl2 = true;
            for (int i = 0; i < arrT.length; ++i) {
                bl2 &= BatteryStatsImpl.resetIfNotNull(arrT[i], bl);
            }
            return bl2;
        }
        return true;
    }

    private void scheduleSyncExternalStatsLocked(String string2, int n) {
        ExternalStatsSync externalStatsSync = this.mExternalSync;
        if (externalStatsSync != null) {
            externalStatsSync.scheduleSync(string2, n);
        }
    }

    private void startRecordingHistory(long l, long l2, boolean bl) {
        byte by;
        byte by2;
        this.mRecordingHistory = true;
        this.mHistoryCur.currentTime = System.currentTimeMillis();
        byte by3 = bl ? (by = 7) : (by2 = 5);
        this.addHistoryBufferLocked(l, by3, this.mHistoryCur);
        this.mHistoryCur.currentTime = 0L;
        if (bl) {
            this.initActiveHistoryEventsLocked(l, l2);
        }
    }

    private void updateAllPhoneStateLocked(int n, int n2, int n3) {
        BatteryStats.HistoryItem historyItem;
        boolean bl = false;
        int n4 = 0;
        this.mPhoneServiceStateRaw = n;
        this.mPhoneSimStateRaw = n2;
        this.mPhoneSignalStrengthBinRaw = n3;
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        int n5 = n;
        if (n2 == 1) {
            n5 = n;
            if (n == 1) {
                n5 = n;
                if (n3 > 0) {
                    n5 = 0;
                }
            }
        }
        if (n5 == 3) {
            n3 = -1;
            n = n4;
        } else if (n5 == 0) {
            n = n4;
        } else {
            n = n4;
            if (n5 == 1) {
                boolean bl2 = true;
                int n6 = 0;
                bl = bl2;
                n = n4;
                n3 = n6;
                if (!this.mPhoneSignalScanningTimer.isRunningLocked()) {
                    historyItem = this.mHistoryCur;
                    historyItem.states |= 2097152;
                    n = 1;
                    this.mPhoneSignalScanningTimer.startRunningLocked(l);
                    StatsLog.write(94, n5, n2, 0);
                    n3 = n6;
                    bl = bl2;
                }
            }
        }
        n4 = n;
        if (!bl) {
            n4 = n;
            if (this.mPhoneSignalScanningTimer.isRunningLocked()) {
                historyItem = this.mHistoryCur;
                historyItem.states &= -2097153;
                n4 = 1;
                this.mPhoneSignalScanningTimer.stopRunningLocked(l);
                StatsLog.write(94, n5, n2, n3);
            }
        }
        n = n4;
        if (this.mPhoneServiceState != n5) {
            historyItem = this.mHistoryCur;
            historyItem.states = historyItem.states & -449 | n5 << 6;
            n = 1;
            this.mPhoneServiceState = n5;
        }
        n4 = this.mPhoneSignalStrengthBin;
        n2 = n;
        if (n4 != n3) {
            if (n4 >= 0) {
                this.mPhoneSignalStrengthsTimer[n4].stopRunningLocked(l);
            }
            if (n3 >= 0) {
                if (!this.mPhoneSignalStrengthsTimer[n3].isRunningLocked()) {
                    this.mPhoneSignalStrengthsTimer[n3].startRunningLocked(l);
                }
                historyItem = this.mHistoryCur;
                historyItem.states = historyItem.states & -57 | n3 << 3;
                n = 1;
                StatsLog.write(40, n3);
            } else {
                this.stopAllPhoneSignalStrengthTimersLocked(-1);
            }
            this.mPhoneSignalStrengthBin = n3;
            n2 = n;
        }
        if (n2 != 0) {
            this.addHistoryRecordLocked(l, l2);
        }
    }

    private void updateBatteryPropertiesLocked() {
        block3 : {
            IBatteryPropertiesRegistrar iBatteryPropertiesRegistrar = IBatteryPropertiesRegistrar.Stub.asInterface(ServiceManager.getService("batteryproperties"));
            if (iBatteryPropertiesRegistrar == null) break block3;
            try {
                iBatteryPropertiesRegistrar.scheduleUpdate();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    private void updateNewDischargeScreenLevelLocked(int n) {
        if (this.isScreenOn(n)) {
            this.mDischargeScreenOnUnplugLevel = this.mDischargeCurrentLevel;
            this.mDischargeScreenOffUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = 0;
        } else if (this.isScreenDoze(n)) {
            this.mDischargeScreenOnUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = this.mDischargeCurrentLevel;
            this.mDischargeScreenOffUnplugLevel = 0;
        } else if (this.isScreenOff(n)) {
            this.mDischargeScreenOnUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = 0;
            this.mDischargeScreenOffUnplugLevel = this.mDischargeCurrentLevel;
        }
    }

    private void updateOldDischargeScreenLevelLocked(int n) {
        block1 : {
            block2 : {
                block0 : {
                    if (!this.isScreenOn(n)) break block0;
                    n = this.mDischargeScreenOnUnplugLevel - this.mDischargeCurrentLevel;
                    if (n <= 0) break block1;
                    this.mDischargeAmountScreenOn += n;
                    this.mDischargeAmountScreenOnSinceCharge += n;
                    break block1;
                }
                if (!this.isScreenDoze(n)) break block2;
                n = this.mDischargeScreenDozeUnplugLevel - this.mDischargeCurrentLevel;
                if (n <= 0) break block1;
                this.mDischargeAmountScreenDoze += n;
                this.mDischargeAmountScreenDozeSinceCharge += n;
                break block1;
            }
            if (!this.isScreenOff(n) || (n = this.mDischargeScreenOffUnplugLevel - this.mDischargeCurrentLevel) <= 0) break block1;
            this.mDischargeAmountScreenOff += n;
            this.mDischargeAmountScreenOffSinceCharge += n;
        }
    }

    private void writeDailyItemsLocked(XmlSerializer xmlSerializer) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(64);
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
        xmlSerializer.startTag(null, "daily-items");
        for (int i = 0; i < this.mDailyItems.size(); ++i) {
            BatteryStats.DailyItem dailyItem = this.mDailyItems.get(i);
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "start", Long.toString(dailyItem.mStartTime));
            xmlSerializer.attribute(null, "end", Long.toString(dailyItem.mEndTime));
            this.writeDailyLevelSteps(xmlSerializer, "dis", dailyItem.mDischargeSteps, stringBuilder);
            this.writeDailyLevelSteps(xmlSerializer, "chg", dailyItem.mChargeSteps, stringBuilder);
            if (dailyItem.mPackageChanges != null) {
                for (int j = 0; j < dailyItem.mPackageChanges.size(); ++j) {
                    BatteryStats.PackageChange packageChange = dailyItem.mPackageChanges.get(j);
                    if (packageChange.mUpdate) {
                        xmlSerializer.startTag(null, "upd");
                        xmlSerializer.attribute(null, "pkg", packageChange.mPackageName);
                        xmlSerializer.attribute(null, "ver", Long.toString(packageChange.mVersionCode));
                        xmlSerializer.endTag(null, "upd");
                        continue;
                    }
                    xmlSerializer.startTag(null, "rem");
                    xmlSerializer.attribute(null, "pkg", packageChange.mPackageName);
                    xmlSerializer.endTag(null, "rem");
                }
            }
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "daily-items");
        xmlSerializer.endDocument();
    }

    private void writeDailyLevelSteps(XmlSerializer xmlSerializer, String string2, BatteryStats.LevelStepTracker levelStepTracker, StringBuilder stringBuilder) throws IOException {
        if (levelStepTracker != null) {
            xmlSerializer.startTag(null, string2);
            xmlSerializer.attribute(null, "n", Integer.toString(levelStepTracker.mNumStepDurations));
            for (int i = 0; i < levelStepTracker.mNumStepDurations; ++i) {
                xmlSerializer.startTag(null, "s");
                stringBuilder.setLength(0);
                levelStepTracker.encodeEntryAt(i, stringBuilder);
                xmlSerializer.attribute(null, "v", stringBuilder.toString());
                xmlSerializer.endTag(null, "s");
            }
            xmlSerializer.endTag(null, string2);
        }
    }

    private int writeHistoryTag(BatteryStats.HistoryTag historyTag) {
        int n;
        Object object = this.mHistoryTagPool.get(historyTag);
        if (object != null) {
            n = (Integer)object;
        } else {
            n = this.mNextHistoryTagIdx++;
            object = new BatteryStats.HistoryTag();
            ((BatteryStats.HistoryTag)object).setTo(historyTag);
            historyTag.poolIdx = n;
            this.mHistoryTagPool.put((BatteryStats.HistoryTag)object, n);
            this.mNumHistoryTagChars += ((BatteryStats.HistoryTag)object).string.length() + 1;
        }
        return n;
    }

    @VisibleForTesting
    public long[] addCpuTimes(long[] object, long[] arrl) {
        block2 : {
            if (object != null && arrl != null) {
                for (int i = ((long[])object).length - 1; i >= 0; --i) {
                    object[i] = object[i] + arrl[i];
                }
                return object;
            }
            if (object != null) break block2;
            object = arrl == null ? null : arrl;
        }
        return object;
    }

    void addHistoryBufferLocked(long l, BatteryStats.HistoryItem historyItem) {
        if (this.mHaveBatteryLevel && this.mRecordingHistory) {
            long l2 = this.mHistoryBaseTime;
            long l3 = this.mHistoryLastWritten.time;
            int n = this.mHistoryLastWritten.states;
            int n2 = historyItem.states;
            int n3 = this.mActiveHistoryStates;
            int n4 = this.mHistoryLastWritten.states2;
            int n5 = historyItem.states2;
            int n6 = this.mActiveHistoryStates2;
            int n7 = this.mHistoryLastWritten.states;
            int n8 = this.mHistoryLastLastWritten.states;
            int n9 = this.mHistoryLastWritten.states2;
            int n10 = this.mHistoryLastLastWritten.states2;
            if (!(this.mHistoryBufferLastPos < 0 || this.mHistoryLastWritten.cmd != 0 || l2 + l - l3 >= 1000L || ((n ^ n2 & n3) & (n7 ^ n8)) != 0 || ((n4 ^ n5 & n6) & (n9 ^ n10)) != 0 || this.mHistoryLastWritten.wakelockTag != null && historyItem.wakelockTag != null || this.mHistoryLastWritten.wakeReasonTag != null && historyItem.wakeReasonTag != null || this.mHistoryLastWritten.stepDetails != null || this.mHistoryLastWritten.eventCode != 0 && historyItem.eventCode != 0 || this.mHistoryLastWritten.batteryLevel != historyItem.batteryLevel || this.mHistoryLastWritten.batteryStatus != historyItem.batteryStatus || this.mHistoryLastWritten.batteryHealth != historyItem.batteryHealth || this.mHistoryLastWritten.batteryPlugType != historyItem.batteryPlugType || this.mHistoryLastWritten.batteryTemperature != historyItem.batteryTemperature || this.mHistoryLastWritten.batteryVoltage != historyItem.batteryVoltage)) {
                this.mHistoryBuffer.setDataSize(this.mHistoryBufferLastPos);
                this.mHistoryBuffer.setDataPosition(this.mHistoryBufferLastPos);
                this.mHistoryBufferLastPos = -1;
                l3 = this.mHistoryLastWritten.time;
                l = this.mHistoryBaseTime;
                if (this.mHistoryLastWritten.wakelockTag != null) {
                    historyItem.wakelockTag = historyItem.localWakelockTag;
                    historyItem.wakelockTag.setTo(this.mHistoryLastWritten.wakelockTag);
                }
                if (this.mHistoryLastWritten.wakeReasonTag != null) {
                    historyItem.wakeReasonTag = historyItem.localWakeReasonTag;
                    historyItem.wakeReasonTag.setTo(this.mHistoryLastWritten.wakeReasonTag);
                }
                if (this.mHistoryLastWritten.eventCode != 0) {
                    historyItem.eventCode = this.mHistoryLastWritten.eventCode;
                    historyItem.eventTag = historyItem.localEventTag;
                    historyItem.eventTag.setTo(this.mHistoryLastWritten.eventTag);
                }
                this.mHistoryLastWritten.setTo(this.mHistoryLastLastWritten);
                l = l3 - l;
            }
            if ((n9 = this.mHistoryBuffer.dataSize()) >= this.mConstants.MAX_HISTORY_BUFFER) {
                SystemClock.uptimeMillis();
                this.writeHistoryLocked(true);
                this.mBatteryStatsHistory.startNextFile();
                this.mHistoryBuffer.setDataSize(0);
                this.mHistoryBuffer.setDataPosition(0);
                this.mHistoryBuffer.setDataCapacity(this.mConstants.MAX_HISTORY_BUFFER / 2);
                this.mHistoryBufferLastPos = -1;
                l3 = this.mClocks.elapsedRealtime();
                l2 = this.mClocks.uptimeMillis();
                BatteryStats.HistoryItem historyItem2 = new BatteryStats.HistoryItem();
                historyItem2.setTo(historyItem);
                this.startRecordingHistory(l3, l2, false);
                this.addHistoryBufferLocked(l, (byte)0, historyItem2);
                return;
            }
            if (n9 == 0) {
                historyItem.currentTime = System.currentTimeMillis();
                this.addHistoryBufferLocked(l, (byte)7, historyItem);
            }
            this.addHistoryBufferLocked(l, (byte)0, historyItem);
            return;
        }
    }

    public void addHistoryEventLocked(long l, long l2, int n, String string2, int n2) {
        BatteryStats.HistoryItem historyItem = this.mHistoryCur;
        historyItem.eventCode = n;
        historyItem.eventTag = historyItem.localEventTag;
        this.mHistoryCur.eventTag.string = string2;
        this.mHistoryCur.eventTag.uid = n2;
        this.addHistoryRecordLocked(l, l2);
    }

    void addHistoryRecordInnerLocked(long l, BatteryStats.HistoryItem historyItem) {
        this.addHistoryBufferLocked(l, historyItem);
    }

    void addHistoryRecordLocked(long l, long l2) {
        BatteryStats.HistoryItem historyItem;
        long l3;
        long l4 = this.mTrackRunningHistoryElapsedRealtime;
        if (l4 != 0L && (l3 = l2 - this.mTrackRunningHistoryUptime) < (l4 = l - l4) - 20L) {
            this.mHistoryAddTmp.setTo(this.mHistoryLastWritten);
            historyItem = this.mHistoryAddTmp;
            historyItem.wakelockTag = null;
            historyItem.wakeReasonTag = null;
            historyItem.eventCode = 0;
            historyItem.states &= Integer.MAX_VALUE;
            this.addHistoryRecordInnerLocked(l - (l4 - l3), this.mHistoryAddTmp);
        }
        historyItem = this.mHistoryCur;
        historyItem.states |= Integer.MIN_VALUE;
        this.mTrackRunningHistoryElapsedRealtime = l;
        this.mTrackRunningHistoryUptime = l2;
        this.addHistoryRecordInnerLocked(l, this.mHistoryCur);
    }

    void addHistoryRecordLocked(long l, long l2, byte by, BatteryStats.HistoryItem historyItem) {
        BatteryStats.HistoryItem historyItem2 = this.mHistoryCache;
        if (historyItem2 != null) {
            this.mHistoryCache = historyItem2.next;
        } else {
            historyItem2 = new BatteryStats.HistoryItem();
        }
        historyItem2.setTo(this.mHistoryBaseTime + l, by, historyItem);
        this.addHistoryRecordLocked(historyItem2);
    }

    void addHistoryRecordLocked(BatteryStats.HistoryItem historyItem) {
        BatteryStats.HistoryItem historyItem2;
        ++this.mNumHistoryItems;
        historyItem.next = null;
        this.mHistoryLastEnd = historyItem2 = this.mHistoryEnd;
        if (historyItem2 != null) {
            historyItem2.next = historyItem;
            this.mHistoryEnd = historyItem;
        } else {
            this.mHistoryEnd = historyItem;
            this.mHistory = historyItem;
        }
    }

    public void addIsolatedUidLocked(int n, int n2) {
        this.mIsolatedUids.put(n, n2);
        this.getUidStatsLocked(n2).addIsolatedUid(n);
    }

    void aggregateLastWakeupUptimeLocked(long l) {
        String string2 = this.mLastWakeupReason;
        if (string2 != null) {
            this.getWakeupReasonTimerLocked(string2).add((l -= this.mLastWakeupUptimeMs) * 1000L, 1);
            StatsLog.write(36, this.mLastWakeupReason, 1000L * l);
            this.mLastWakeupReason = null;
        }
    }

    void clearHistoryLocked() {
        this.mHistoryBaseTime = 0L;
        this.mLastHistoryElapsedRealtime = 0L;
        this.mTrackRunningHistoryElapsedRealtime = 0L;
        this.mTrackRunningHistoryUptime = 0L;
        this.mHistoryBuffer.setDataSize(0);
        this.mHistoryBuffer.setDataPosition(0);
        this.mHistoryBuffer.setDataCapacity(this.mConstants.MAX_HISTORY_BUFFER / 2);
        this.mHistoryLastLastWritten.clear();
        this.mHistoryLastWritten.clear();
        this.mHistoryTagPool.clear();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        this.mHistoryBufferLastPos = -1;
        this.mActiveHistoryStates = -1;
        this.mActiveHistoryStates2 = -1;
    }

    public void clearPendingRemovedUids() {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mConstants.UID_REMOVE_DELAY_MS;
        while (!this.mPendingRemovedUids.isEmpty() && this.mPendingRemovedUids.peek().timeAddedInQueue < l - l2) {
            this.mPendingRemovedUids.poll().remove();
        }
    }

    @Override
    public void commitCurrentHistoryBatchLocked() {
        this.mHistoryLastWritten.cmd = (byte)-1;
    }

    @UnsupportedAppUsage
    @Override
    public long computeBatteryRealtime(long l, int n) {
        return this.mOnBatteryTimeBase.computeRealtime(l, n);
    }

    @Override
    public long computeBatteryScreenOffRealtime(long l, int n) {
        return this.mOnBatteryScreenOffTimeBase.computeRealtime(l, n);
    }

    @Override
    public long computeBatteryScreenOffUptime(long l, int n) {
        return this.mOnBatteryScreenOffTimeBase.computeUptime(l, n);
    }

    @UnsupportedAppUsage
    @Override
    public long computeBatteryTimeRemaining(long l) {
        if (!this.mOnBattery) {
            return -1L;
        }
        if (this.mDischargeStepTracker.mNumStepDurations < 1) {
            return -1L;
        }
        l = this.mDischargeStepTracker.computeTimePerLevel();
        if (l <= 0L) {
            return -1L;
        }
        return (long)this.mCurrentBatteryLevel * l * 1000L;
    }

    @UnsupportedAppUsage
    @Override
    public long computeBatteryUptime(long l, int n) {
        return this.mOnBatteryTimeBase.computeUptime(l, n);
    }

    @Override
    public long computeChargeTimeRemaining(long l) {
        if (this.mOnBattery) {
            return -1L;
        }
        if (this.mChargeStepTracker.mNumStepDurations < 1) {
            return -1L;
        }
        l = this.mChargeStepTracker.computeTimePerLevel();
        if (l <= 0L) {
            return -1L;
        }
        return (long)(100 - this.mCurrentBatteryLevel) * l * 1000L;
    }

    @Override
    public long computeRealtime(long l, int n) {
        return this.mRealtime + (l - this.mRealtimeStart);
    }

    @Override
    public long computeUptime(long l, int n) {
        return this.mUptime + (l - this.mUptimeStart);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void copyFromAllUidsCpuTimes() {
        synchronized (this) {
            this.copyFromAllUidsCpuTimes(this.mOnBatteryTimeBase.isRunning(), this.mOnBatteryScreenOffTimeBase.isRunning());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void copyFromAllUidsCpuTimes(boolean bl, boolean bl2) {
        synchronized (this) {
            if (!this.mConstants.TRACK_CPU_TIMES_BY_PROC_STATE) {
                return;
            }
            if (!this.initKernelSingleUidTimeReaderLocked()) {
                return;
            }
            SparseArray<long[]> sparseArray = this.mCpuUidFreqTimeReader.getAllUidCpuFreqTimeMs();
            if (this.mIsPerProcessStateCpuDataStale) {
                this.mKernelSingleUidTimeReader.setAllUidsCpuTimesMs(sparseArray);
                this.mIsPerProcessStateCpuDataStale = false;
                this.mPendingUids.clear();
                return;
            }
            int n = sparseArray.size() - 1;
            while (n >= 0) {
                long[] arrl;
                int n2 = sparseArray.keyAt(n);
                Uid uid = this.getAvailableUidStatsLocked(this.mapUid(n2));
                if (uid != null && (arrl = sparseArray.valueAt(n)) != null) {
                    arrl = this.mKernelSingleUidTimeReader.computeDelta(n2, (long[])arrl.clone());
                    if (bl && arrl != null) {
                        int n3 = this.mPendingUids.indexOfKey(n2);
                        if (n3 >= 0) {
                            n2 = this.mPendingUids.valueAt(n3);
                            this.mPendingUids.removeAt(n3);
                        } else {
                            n2 = uid.mProcessState;
                        }
                        if (n2 >= 0 && n2 < 7) {
                            uid.addProcStateTimesMs(n2, arrl, bl);
                            uid.addProcStateScreenOffTimesMs(n2, arrl, bl2);
                        }
                    }
                }
                --n;
            }
            return;
        }
    }

    public void createFakeHistoryEvents(long l) {
        for (long i = 0L; i < l; ++i) {
            this.noteLongPartialWakelockStart("name1", "historyName1", 1000);
            this.noteLongPartialWakelockFinish("name1", "historyName1", 1000);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @GuardedBy(value={"this"})
    public void dumpConstantsLocked(PrintWriter printWriter) {
        this.mConstants.dumpLocked(printWriter);
    }

    @GuardedBy(value={"this"})
    public void dumpCpuStatsLocked(PrintWriter printWriter) {
        int n;
        Object object;
        int n2;
        int n3 = this.mUidStats.size();
        printWriter.println("Per UID CPU user & system time in ms:");
        for (n = 0; n < n3; ++n) {
            n2 = this.mUidStats.keyAt(n);
            object = this.mUidStats.get(n2);
            printWriter.print("  ");
            printWriter.print(n2);
            printWriter.print(": ");
            printWriter.print(object.getUserCpuTimeUs(0) / 1000L);
            printWriter.print(" ");
            printWriter.println(object.getSystemCpuTimeUs(0) / 1000L);
        }
        printWriter.println("Per UID CPU active time in ms:");
        for (n = 0; n < n3; ++n) {
            n2 = this.mUidStats.keyAt(n);
            object = this.mUidStats.get(n2);
            if (object.getCpuActiveTime() <= 0L) continue;
            printWriter.print("  ");
            printWriter.print(n2);
            printWriter.print(": ");
            printWriter.println(object.getCpuActiveTime());
        }
        printWriter.println("Per UID CPU cluster time in ms:");
        for (n = 0; n < n3; ++n) {
            n2 = this.mUidStats.keyAt(n);
            object = this.mUidStats.get(n2).getCpuClusterTimes();
            if (object == null) continue;
            printWriter.print("  ");
            printWriter.print(n2);
            printWriter.print(": ");
            printWriter.println(Arrays.toString(object));
        }
        printWriter.println("Per UID CPU frequency time in ms:");
        for (n = 0; n < n3; ++n) {
            n2 = this.mUidStats.keyAt(n);
            object = this.mUidStats.get(n2).getCpuFreqTimes(0);
            if (object == null) continue;
            printWriter.print("  ");
            printWriter.print(n2);
            printWriter.print(": ");
            printWriter.println(Arrays.toString(object));
        }
    }

    @Override
    public void dumpLocked(Context context, PrintWriter printWriter, int n, int n2, long l) {
        super.dumpLocked(context, printWriter, n, n2, l);
        printWriter.print("Total cpu time reads: ");
        printWriter.println(this.mNumSingleUidCpuTimeReads);
        printWriter.print("Batched cpu time reads: ");
        printWriter.println(this.mNumBatchedSingleUidCpuTimeReads);
        printWriter.print("Batching Duration (min): ");
        printWriter.println((this.mClocks.uptimeMillis() - this.mCpuTimeReadsTrackingStartTime) / 60000L);
        printWriter.print("All UID cpu time reads since the later of device start or stats reset: ");
        printWriter.println(this.mNumAllUidCpuTimeReads);
        printWriter.print("UIDs removed since the later of device start or stats reset: ");
        printWriter.println(this.mNumUidsRemoved);
    }

    public void finishAddingCpuLocked(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.mCurStepCpuUserTime += (long)n;
        this.mCurStepCpuSystemTime += (long)n2;
        this.mCurStepStatUserTime += (long)n3;
        this.mCurStepStatSystemTime += (long)n4;
        this.mCurStepStatIOWaitTime += (long)n5;
        this.mCurStepStatIrqTime += (long)n6;
        this.mCurStepStatSoftIrqTime += (long)n7;
        this.mCurStepStatIdleTime += (long)n8;
    }

    @Override
    public void finishIteratingHistoryLocked() {
        this.mBatteryStatsHistory.finishIteratingHistory();
        this.mIteratingHistory = false;
        this.mReadHistoryStrings = null;
        this.mReadHistoryUids = null;
    }

    @Override
    public void finishIteratingOldHistoryLocked() {
        this.mIteratingHistory = false;
        Parcel parcel = this.mHistoryBuffer;
        parcel.setDataPosition(parcel.dataSize());
        this.mHistoryIterator = null;
    }

    public Uid getAvailableUidStatsLocked(int n) {
        return this.mUidStats.get(n);
    }

    @UnsupportedAppUsage
    public long getAwakeTimeBattery() {
        return this.getBatteryUptimeLocked();
    }

    @UnsupportedAppUsage
    public long getAwakeTimePlugged() {
        return this.mClocks.uptimeMillis() * 1000L - this.getAwakeTimeBattery();
    }

    @UnsupportedAppUsage
    @Override
    public long getBatteryRealtime(long l) {
        return this.mOnBatteryTimeBase.getRealtime(l);
    }

    @Override
    public long getBatteryUptime(long l) {
        return this.mOnBatteryTimeBase.getUptime(l);
    }

    protected long getBatteryUptimeLocked() {
        return this.mOnBatteryTimeBase.getUptime(this.mClocks.uptimeMillis() * 1000L);
    }

    @Override
    public BatteryStats.ControllerActivityCounter getBluetoothControllerActivity() {
        return this.mBluetoothActivity;
    }

    @Override
    public long getBluetoothScanTime(long l, int n) {
        return this.mBluetoothScanTimer.getTotalTimeLocked(l, n);
    }

    @Override
    public long getCameraOnTime(long l, int n) {
        return this.mCameraOnTimer.getTotalTimeLocked(l, n);
    }

    public CellularBatteryStats getCellularBatteryStats() {
        int n;
        CellularBatteryStats cellularBatteryStats = new CellularBatteryStats();
        boolean bl = false;
        long l = SystemClock.elapsedRealtime() * 1000L;
        BatteryStats.ControllerActivityCounter controllerActivityCounter = this.getModemControllerActivity();
        long l2 = controllerActivityCounter.getSleepTimeCounter().getCountLocked(0);
        long l3 = controllerActivityCounter.getIdleTimeCounter().getCountLocked(0);
        long l4 = controllerActivityCounter.getRxTimeCounter().getCountLocked(0);
        long l5 = controllerActivityCounter.getPowerCounter().getCountLocked(0);
        long l6 = controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(0);
        long[] arrl = new long[22];
        for (n = 0; n < arrl.length; ++n) {
            arrl[n] = this.getPhoneDataConnectionTime(n, l, 0) / 1000L;
        }
        long[] arrl2 = new long[5];
        for (n = 0; n < arrl2.length; ++n) {
            arrl2[n] = this.getPhoneSignalStrengthTime(n, l, 0) / 1000L;
        }
        long[] arrl3 = new long[Math.min(5, controllerActivityCounter.getTxTimeCounters().length)];
        long l7 = 0L;
        for (n = 0; n < arrl3.length; ++n) {
            arrl3[n] = controllerActivityCounter.getTxTimeCounters()[n].getCountLocked(0);
            l7 += arrl3[n];
        }
        cellularBatteryStats.setLoggingDurationMs(this.computeBatteryRealtime(l, 0) / 1000L);
        cellularBatteryStats.setKernelActiveTimeMs(this.getMobileRadioActiveTime(l, 0) / 1000L);
        cellularBatteryStats.setNumPacketsTx(this.getNetworkActivityPackets(1, 0));
        cellularBatteryStats.setNumBytesTx(this.getNetworkActivityBytes(1, 0));
        cellularBatteryStats.setNumPacketsRx(this.getNetworkActivityPackets(0, 0));
        cellularBatteryStats.setNumBytesRx(this.getNetworkActivityBytes(0, 0));
        cellularBatteryStats.setSleepTimeMs(l2);
        cellularBatteryStats.setIdleTimeMs(l3);
        cellularBatteryStats.setRxTimeMs(l4);
        cellularBatteryStats.setEnergyConsumedMaMs(l5);
        cellularBatteryStats.setTimeInRatMs(arrl);
        cellularBatteryStats.setTimeInRxSignalStrengthLevelMs(arrl2);
        cellularBatteryStats.setTxTimeMs(arrl3);
        cellularBatteryStats.setMonitoredRailChargeConsumedMaMs(l6);
        return cellularBatteryStats;
    }

    @Override
    public BatteryStats.LevelStepTracker getChargeLevelStepTracker() {
        return this.mChargeStepTracker;
    }

    @Override
    public long[] getCpuFreqs() {
        return this.mCpuFreqs;
    }

    @Override
    public long getCurrentDailyStartTime() {
        return this.mDailyStartTime;
    }

    @Override
    public BatteryStats.LevelStepTracker getDailyChargeLevelStepTracker() {
        return this.mDailyChargeStepTracker;
    }

    @Override
    public BatteryStats.LevelStepTracker getDailyDischargeLevelStepTracker() {
        return this.mDailyDischargeStepTracker;
    }

    @Override
    public BatteryStats.DailyItem getDailyItemLocked(int n) {
        n = this.mDailyItems.size() - 1 - n;
        BatteryStats.DailyItem dailyItem = n >= 0 ? this.mDailyItems.get(n) : null;
        return dailyItem;
    }

    @Override
    public ArrayList<BatteryStats.PackageChange> getDailyPackageChanges() {
        return this.mDailyPackageChanges;
    }

    @Override
    public int getDeviceIdleModeCount(int n, int n2) {
        if (n != 1) {
            if (n != 2) {
                return 0;
            }
            return this.mDeviceIdleModeFullTimer.getCountLocked(n2);
        }
        return this.mDeviceIdleModeLightTimer.getCountLocked(n2);
    }

    @Override
    public long getDeviceIdleModeTime(int n, long l, int n2) {
        if (n != 1) {
            if (n != 2) {
                return 0L;
            }
            return this.mDeviceIdleModeFullTimer.getTotalTimeLocked(l, n2);
        }
        return this.mDeviceIdleModeLightTimer.getTotalTimeLocked(l, n2);
    }

    @Override
    public int getDeviceIdlingCount(int n, int n2) {
        if (n != 1) {
            if (n != 2) {
                return 0;
            }
            return this.mDeviceIdlingTimer.getCountLocked(n2);
        }
        return this.mDeviceLightIdlingTimer.getCountLocked(n2);
    }

    @Override
    public long getDeviceIdlingTime(int n, long l, int n2) {
        if (n != 1) {
            if (n != 2) {
                return 0L;
            }
            return this.mDeviceIdlingTimer.getTotalTimeLocked(l, n2);
        }
        return this.mDeviceLightIdlingTimer.getTotalTimeLocked(l, n2);
    }

    @UnsupportedAppUsage
    @Override
    public int getDischargeAmount(int n) {
        n = n == 0 ? this.getHighDischargeAmountSinceCharge() : this.getDischargeStartLevel() - this.getDischargeCurrentLevel();
        int n2 = n;
        if (n < 0) {
            n2 = 0;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getDischargeAmountScreenDoze() {
        synchronized (this) {
            int n;
            int n2 = n = this.mDischargeAmountScreenDoze;
            if (!this.mOnBattery) return n2;
            n2 = n;
            if (!this.isScreenDoze(this.mScreenState)) return n2;
            n2 = n;
            if (this.mDischargeCurrentLevel >= this.mDischargeScreenDozeUnplugLevel) return n2;
            return n + (this.mDischargeScreenDozeUnplugLevel - this.mDischargeCurrentLevel);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getDischargeAmountScreenDozeSinceCharge() {
        synchronized (this) {
            int n;
            int n2 = n = this.mDischargeAmountScreenDozeSinceCharge;
            if (!this.mOnBattery) return n2;
            n2 = n;
            if (!this.isScreenDoze(this.mScreenState)) return n2;
            n2 = n;
            if (this.mDischargeCurrentLevel >= this.mDischargeScreenDozeUnplugLevel) return n2;
            return n + (this.mDischargeScreenDozeUnplugLevel - this.mDischargeCurrentLevel);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    @Override
    public int getDischargeAmountScreenOff() {
        synchronized (this) {
            int n;
            int n2 = n = this.mDischargeAmountScreenOff;
            if (this.mOnBattery) {
                n2 = n;
                if (this.isScreenOff(this.mScreenState)) {
                    n2 = n;
                    if (this.mDischargeCurrentLevel < this.mDischargeScreenOffUnplugLevel) {
                        n2 = n + (this.mDischargeScreenOffUnplugLevel - this.mDischargeCurrentLevel);
                    }
                }
            }
            n = this.getDischargeAmountScreenDoze();
            return n + n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getDischargeAmountScreenOffSinceCharge() {
        synchronized (this) {
            int n;
            int n2 = n = this.mDischargeAmountScreenOffSinceCharge;
            if (this.mOnBattery) {
                n2 = n;
                if (this.isScreenOff(this.mScreenState)) {
                    n2 = n;
                    if (this.mDischargeCurrentLevel < this.mDischargeScreenOffUnplugLevel) {
                        n2 = n + (this.mDischargeScreenOffUnplugLevel - this.mDischargeCurrentLevel);
                    }
                }
            }
            n = this.getDischargeAmountScreenDozeSinceCharge();
            return n + n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    @Override
    public int getDischargeAmountScreenOn() {
        synchronized (this) {
            int n;
            int n2 = n = this.mDischargeAmountScreenOn;
            if (!this.mOnBattery) return n2;
            n2 = n;
            if (!this.isScreenOn(this.mScreenState)) return n2;
            n2 = n;
            if (this.mDischargeCurrentLevel >= this.mDischargeScreenOnUnplugLevel) return n2;
            return n + (this.mDischargeScreenOnUnplugLevel - this.mDischargeCurrentLevel);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getDischargeAmountScreenOnSinceCharge() {
        synchronized (this) {
            int n;
            int n2 = n = this.mDischargeAmountScreenOnSinceCharge;
            if (!this.mOnBattery) return n2;
            n2 = n;
            if (!this.isScreenOn(this.mScreenState)) return n2;
            n2 = n;
            if (this.mDischargeCurrentLevel >= this.mDischargeScreenOnUnplugLevel) return n2;
            return n + (this.mDischargeScreenOnUnplugLevel - this.mDischargeCurrentLevel);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    @Override
    public int getDischargeCurrentLevel() {
        synchronized (this) {
            return this.getDischargeCurrentLevelLocked();
        }
    }

    public int getDischargeCurrentLevelLocked() {
        return this.mDischargeCurrentLevel;
    }

    @Override
    public BatteryStats.LevelStepTracker getDischargeLevelStepTracker() {
        return this.mDischargeStepTracker;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    @Override
    public int getDischargeStartLevel() {
        synchronized (this) {
            return this.getDischargeStartLevelLocked();
        }
    }

    public int getDischargeStartLevelLocked() {
        return this.mDischargeUnplugLevel;
    }

    @Override
    public String getEndPlatformVersion() {
        return this.mEndPlatformVersion;
    }

    @Override
    public int getEstimatedBatteryCapacity() {
        return this.mEstimatedBatteryCapacity;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getExternalStatsCollectionRateLimitMs() {
        synchronized (this) {
            return this.mConstants.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS;
        }
    }

    @Override
    public long getFlashlightOnCount(int n) {
        return this.mFlashlightOnTimer.getCountLocked(n);
    }

    @Override
    public long getFlashlightOnTime(long l, int n) {
        return this.mFlashlightOnTimer.getTotalTimeLocked(l, n);
    }

    @UnsupportedAppUsage
    @Override
    public long getGlobalWifiRunningTime(long l, int n) {
        return this.mGlobalWifiRunningTimer.getTotalTimeLocked(l, n);
    }

    @Override
    public long getGpsBatteryDrainMaMs() {
        if (this.mPowerProfile.getAveragePower("gps.voltage") / 1000.0 == 0.0) {
            return 0L;
        }
        double d = 0.0;
        long l = SystemClock.elapsedRealtime();
        for (int i = 0; i < 2; ++i) {
            d += this.mPowerProfile.getAveragePower("gps.signalqualitybased", i) * (double)(this.getGpsSignalQualityTime(i, l * 1000L, 0) / 1000L);
        }
        return (long)d;
    }

    public GpsBatteryStats getGpsBatteryStats() {
        GpsBatteryStats gpsBatteryStats = new GpsBatteryStats();
        long l = SystemClock.elapsedRealtime() * 1000L;
        gpsBatteryStats.setLoggingDurationMs(this.computeBatteryRealtime(l, 0) / 1000L);
        gpsBatteryStats.setEnergyConsumedMaMs(this.getGpsBatteryDrainMaMs());
        long[] arrl = new long[2];
        for (int i = 0; i < arrl.length; ++i) {
            arrl[i] = this.getGpsSignalQualityTime(i, l, 0) / 1000L;
        }
        gpsBatteryStats.setTimeInGpsSignalQualityLevel(arrl);
        return gpsBatteryStats;
    }

    @Override
    public long getGpsSignalQualityTime(int n, long l, int n2) {
        if (n >= 0 && n < 2) {
            return this.mGpsSignalQualityTimer[n].getTotalTimeLocked(l, n2);
        }
        return 0L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getHighDischargeAmountSinceCharge() {
        synchronized (this) {
            int n;
            int n2 = n = this.mHighDischargeAmountSinceCharge;
            if (!this.mOnBattery) return n2;
            n2 = n;
            if (this.mDischargeCurrentLevel >= this.mDischargeUnplugLevel) return n2;
            return n + (this.mDischargeUnplugLevel - this.mDischargeCurrentLevel);
        }
    }

    @Override
    public long getHistoryBaseTime() {
        return this.mHistoryBaseTime;
    }

    @Override
    public int getHistoryStringPoolBytes() {
        return this.mReadHistoryStrings.length * 12 + this.mReadHistoryChars * 2;
    }

    @Override
    public int getHistoryStringPoolSize() {
        return this.mReadHistoryStrings.length;
    }

    @Override
    public String getHistoryTagPoolString(int n) {
        return this.mReadHistoryStrings[n];
    }

    @Override
    public int getHistoryTagPoolUid(int n) {
        return this.mReadHistoryUids[n];
    }

    @Override
    public int getHistoryTotalSize() {
        return this.mConstants.MAX_HISTORY_BUFFER * this.mConstants.MAX_HISTORY_FILES;
    }

    @Override
    public int getHistoryUsedSize() {
        return this.mBatteryStatsHistory.getHistoryUsedSize();
    }

    @Override
    public long getInteractiveTime(long l, int n) {
        return this.mInteractiveTimer.getTotalTimeLocked(l, n);
    }

    @Override
    public boolean getIsOnBattery() {
        return this.mOnBattery;
    }

    public LongSparseArray<SamplingTimer> getKernelMemoryStats() {
        return this.mKernelMemoryStats;
    }

    public SamplingTimer getKernelMemoryTimerLocked(long l) {
        SamplingTimer samplingTimer;
        SamplingTimer samplingTimer2 = samplingTimer = this.mKernelMemoryStats.get(l);
        if (samplingTimer == null) {
            samplingTimer2 = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase);
            this.mKernelMemoryStats.put(l, samplingTimer2);
        }
        return samplingTimer2;
    }

    @UnsupportedAppUsage
    public Map<String, ? extends Timer> getKernelWakelockStats() {
        return this.mKernelWakelockStats;
    }

    public SamplingTimer getKernelWakelockTimerLocked(String string2) {
        SamplingTimer samplingTimer;
        SamplingTimer samplingTimer2 = samplingTimer = this.mKernelWakelockStats.get(string2);
        if (samplingTimer == null) {
            samplingTimer2 = new SamplingTimer(this.mClocks, this.mOnBatteryScreenOffTimeBase);
            this.mKernelWakelockStats.put(string2, samplingTimer2);
        }
        return samplingTimer2;
    }

    @Override
    public long getLongestDeviceIdleModeTime(int n) {
        if (n != 1) {
            if (n != 2) {
                return 0L;
            }
            return this.mLongestFullIdleTime;
        }
        return this.mLongestLightIdleTime;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getLowDischargeAmountSinceCharge() {
        synchronized (this) {
            int n;
            int n2 = n = this.mLowDischargeAmountSinceCharge;
            if (!this.mOnBattery) return n2;
            n2 = n;
            if (this.mDischargeCurrentLevel >= this.mDischargeUnplugLevel) return n2;
            return n + (this.mDischargeUnplugLevel - this.mDischargeCurrentLevel - 1);
        }
    }

    @Override
    public int getMaxLearnedBatteryCapacity() {
        return this.mMaxLearnedBatteryCapacity;
    }

    @Override
    public int getMinLearnedBatteryCapacity() {
        return this.mMinLearnedBatteryCapacity;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String[] getMobileIfaces() {
        Object object = this.mModemNetworkLock;
        synchronized (object) {
            return this.mModemIfaces;
        }
    }

    @Override
    public long getMobileRadioActiveAdjustedTime(int n) {
        return this.mMobileRadioActiveAdjustedTime.getCountLocked(n);
    }

    @Override
    public int getMobileRadioActiveCount(int n) {
        return this.mMobileRadioActiveTimer.getCountLocked(n);
    }

    @UnsupportedAppUsage
    @Override
    public long getMobileRadioActiveTime(long l, int n) {
        return this.mMobileRadioActiveTimer.getTotalTimeLocked(l, n);
    }

    @Override
    public int getMobileRadioActiveUnknownCount(int n) {
        return (int)this.mMobileRadioActiveUnknownCount.getCountLocked(n);
    }

    @Override
    public long getMobileRadioActiveUnknownTime(int n) {
        return this.mMobileRadioActiveUnknownTime.getCountLocked(n);
    }

    @Override
    public BatteryStats.ControllerActivityCounter getModemControllerActivity() {
        return this.mModemActivity;
    }

    @UnsupportedAppUsage
    @Override
    public long getNetworkActivityBytes(int n, int n2) {
        LongSamplingCounter[] arrlongSamplingCounter;
        if (n >= 0 && n < (arrlongSamplingCounter = this.mNetworkByteActivityCounters).length) {
            return arrlongSamplingCounter[n].getCountLocked(n2);
        }
        return 0L;
    }

    @Override
    public long getNetworkActivityPackets(int n, int n2) {
        LongSamplingCounter[] arrlongSamplingCounter;
        if (n >= 0 && n < (arrlongSamplingCounter = this.mNetworkPacketActivityCounters).length) {
            return arrlongSamplingCounter[n].getCountLocked(n2);
        }
        return 0L;
    }

    @UnsupportedAppUsage
    @Override
    public boolean getNextHistoryLocked(BatteryStats.HistoryItem historyItem) {
        Parcel parcel = this.mBatteryStatsHistory.getNextParcel(historyItem);
        if (parcel == null) {
            return false;
        }
        long l = historyItem.time;
        long l2 = historyItem.currentTime;
        this.readHistoryDelta(parcel, historyItem);
        if (historyItem.cmd != 5 && historyItem.cmd != 7 && l2 != 0L) {
            historyItem.currentTime = historyItem.time - l + l2;
        }
        return true;
    }

    @Override
    public long getNextMaxDailyDeadline() {
        return this.mNextMaxDailyDeadline;
    }

    @Override
    public long getNextMinDailyDeadline() {
        return this.mNextMinDailyDeadline;
    }

    @Override
    public boolean getNextOldHistoryLocked(BatteryStats.HistoryItem historyItem) {
        Object object;
        boolean bl = this.mHistoryBuffer.dataPosition() >= this.mHistoryBuffer.dataSize();
        if (!bl) {
            this.readHistoryDelta(this.mHistoryBuffer, this.mHistoryReadTmp);
            boolean bl2 = this.mReadOverflow;
            boolean bl3 = this.mHistoryReadTmp.cmd == 6;
            this.mReadOverflow = bl2 | bl3;
        }
        if ((object = this.mHistoryIterator) == null) {
            if (!this.mReadOverflow && !bl) {
                Slog.w(TAG, "Old history ends before new history!");
            }
            return false;
        }
        historyItem.setTo((BatteryStats.HistoryItem)object);
        this.mHistoryIterator = ((BatteryStats.HistoryItem)object).next;
        if (!this.mReadOverflow) {
            if (bl) {
                Slog.w(TAG, "New history ends before old history!");
            } else if (!historyItem.same(this.mHistoryReadTmp)) {
                object = new FastPrintWriter(new LogWriter(5, TAG));
                ((PrintWriter)object).println("Histories differ!");
                ((PrintWriter)object).println("Old history:");
                new BatteryStats.HistoryPrinter().printNextItem((PrintWriter)object, historyItem, 0L, false, true);
                ((PrintWriter)object).println("New history:");
                new BatteryStats.HistoryPrinter().printNextItem((PrintWriter)object, this.mHistoryReadTmp, 0L, false, true);
                ((PrintWriter)object).flush();
            }
        }
        return true;
    }

    @Override
    public int getNumConnectivityChange(int n) {
        return this.mNumConnectivityChange;
    }

    @UnsupportedAppUsage
    public Uid.Pkg getPackageStatsLocked(int n, String string2) {
        return this.getUidStatsLocked(this.mapUid(n)).getPackageStatsLocked(string2);
    }

    @Override
    public int getParcelVersion() {
        return 186;
    }

    @UnsupportedAppUsage
    @Override
    public int getPhoneDataConnectionCount(int n, int n2) {
        return this.mPhoneDataConnectionsTimer[n].getCountLocked(n2);
    }

    @UnsupportedAppUsage
    @Override
    public long getPhoneDataConnectionTime(int n, long l, int n2) {
        return this.mPhoneDataConnectionsTimer[n].getTotalTimeLocked(l, n2);
    }

    @Override
    public Timer getPhoneDataConnectionTimer(int n) {
        return this.mPhoneDataConnectionsTimer[n];
    }

    @Override
    public int getPhoneOnCount(int n) {
        return this.mPhoneOnTimer.getCountLocked(n);
    }

    @UnsupportedAppUsage
    @Override
    public long getPhoneOnTime(long l, int n) {
        return this.mPhoneOnTimer.getTotalTimeLocked(l, n);
    }

    @UnsupportedAppUsage
    @Override
    public long getPhoneSignalScanningTime(long l, int n) {
        return this.mPhoneSignalScanningTimer.getTotalTimeLocked(l, n);
    }

    @Override
    public Timer getPhoneSignalScanningTimer() {
        return this.mPhoneSignalScanningTimer;
    }

    @UnsupportedAppUsage
    @Override
    public int getPhoneSignalStrengthCount(int n, int n2) {
        return this.mPhoneSignalStrengthsTimer[n].getCountLocked(n2);
    }

    @UnsupportedAppUsage
    @Override
    public long getPhoneSignalStrengthTime(int n, long l, int n2) {
        return this.mPhoneSignalStrengthsTimer[n].getTotalTimeLocked(l, n2);
    }

    @Override
    public Timer getPhoneSignalStrengthTimer(int n) {
        return this.mPhoneSignalStrengthsTimer[n];
    }

    @Override
    public int getPowerSaveModeEnabledCount(int n) {
        return this.mPowerSaveModeEnabledTimer.getCountLocked(n);
    }

    @Override
    public long getPowerSaveModeEnabledTime(long l, int n) {
        return this.mPowerSaveModeEnabledTimer.getTotalTimeLocked(l, n);
    }

    @UnsupportedAppUsage
    public Uid.Proc getProcessStatsLocked(int n, String string2) {
        return this.getUidStatsLocked(this.mapUid(n)).getProcessStatsLocked(string2);
    }

    public long getProcessWakeTime(int n, int n2, long l) {
        n = this.mapUid(n);
        Object object = this.mUidStats.get(n);
        long l2 = 0L;
        if (object != null && (object = ((Uid)object).mPids.get(n2)) != null) {
            long l3 = ((BatteryStats.Uid.Pid)object).mWakeSumMs;
            if (((BatteryStats.Uid.Pid)object).mWakeNesting > 0) {
                l2 = l - ((BatteryStats.Uid.Pid)object).mWakeStartMs;
            }
            return l3 + l2;
        }
        return 0L;
    }

    public Map<String, ? extends Timer> getRpmStats() {
        return this.mRpmStats;
    }

    public SamplingTimer getRpmTimerLocked(String string2) {
        SamplingTimer samplingTimer;
        SamplingTimer samplingTimer2 = samplingTimer = this.mRpmStats.get(string2);
        if (samplingTimer == null) {
            samplingTimer2 = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase);
            this.mRpmStats.put(string2, samplingTimer2);
        }
        return samplingTimer2;
    }

    @UnsupportedAppUsage
    @Override
    public long getScreenBrightnessTime(int n, long l, int n2) {
        return this.mScreenBrightnessTimer[n].getTotalTimeLocked(l, n2);
    }

    @Override
    public Timer getScreenBrightnessTimer(int n) {
        return this.mScreenBrightnessTimer[n];
    }

    @Override
    public int getScreenDozeCount(int n) {
        return this.mScreenDozeTimer.getCountLocked(n);
    }

    @Override
    public long getScreenDozeTime(long l, int n) {
        return this.mScreenDozeTimer.getTotalTimeLocked(l, n);
    }

    public Map<String, ? extends Timer> getScreenOffRpmStats() {
        return this.mScreenOffRpmStats;
    }

    public SamplingTimer getScreenOffRpmTimerLocked(String string2) {
        SamplingTimer samplingTimer;
        SamplingTimer samplingTimer2 = samplingTimer = this.mScreenOffRpmStats.get(string2);
        if (samplingTimer == null) {
            samplingTimer2 = new SamplingTimer(this.mClocks, this.mOnBatteryScreenOffTimeBase);
            this.mScreenOffRpmStats.put(string2, samplingTimer2);
        }
        return samplingTimer2;
    }

    @Override
    public int getScreenOnCount(int n) {
        return this.mScreenOnTimer.getCountLocked(n);
    }

    @UnsupportedAppUsage
    @Override
    public long getScreenOnTime(long l, int n) {
        return this.mScreenOnTimer.getTotalTimeLocked(l, n);
    }

    @UnsupportedAppUsage
    public Uid.Pkg.Serv getServiceStatsLocked(int n, String string2, String string3) {
        return this.getUidStatsLocked(this.mapUid(n)).getServiceStatsLocked(string2, string3);
    }

    @Override
    public long getStartClockTime() {
        long l;
        long l2 = System.currentTimeMillis();
        if (l2 > 31536000000L && this.mStartClockTime < l2 - 31536000000L || (l = this.mStartClockTime) > l2) {
            this.recordCurrentTimeChangeLocked(l2, this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis());
            return l2 - (this.mClocks.elapsedRealtime() - this.mRealtimeStart / 1000L);
        }
        return l;
    }

    @Override
    public int getStartCount() {
        return this.mStartCount;
    }

    @Override
    public String getStartPlatformVersion() {
        return this.mStartPlatformVersion;
    }

    @Override
    public long getUahDischarge(int n) {
        return this.mDischargeCounter.getCountLocked(n);
    }

    @Override
    public long getUahDischargeDeepDoze(int n) {
        return this.mDischargeDeepDozeCounter.getCountLocked(n);
    }

    @Override
    public long getUahDischargeLightDoze(int n) {
        return this.mDischargeLightDozeCounter.getCountLocked(n);
    }

    @Override
    public long getUahDischargeScreenDoze(int n) {
        return this.mDischargeScreenDozeCounter.getCountLocked(n);
    }

    @Override
    public long getUahDischargeScreenOff(int n) {
        return this.mDischargeScreenOffCounter.getCountLocked(n);
    }

    @UnsupportedAppUsage
    @Override
    public SparseArray<? extends BatteryStats.Uid> getUidStats() {
        return this.mUidStats;
    }

    @UnsupportedAppUsage
    public Uid getUidStatsLocked(int n) {
        Uid uid;
        Uid uid2 = uid = this.mUidStats.get(n);
        if (uid == null) {
            uid2 = new Uid(this, n);
            this.mUidStats.put(n, uid2);
        }
        return uid2;
    }

    public Map<String, ? extends Timer> getWakeupReasonStats() {
        return this.mWakeupReasonStats;
    }

    public SamplingTimer getWakeupReasonTimerLocked(String string2) {
        SamplingTimer samplingTimer;
        SamplingTimer samplingTimer2 = samplingTimer = this.mWakeupReasonStats.get(string2);
        if (samplingTimer == null) {
            samplingTimer2 = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase);
            this.mWakeupReasonStats.put(string2, samplingTimer2);
        }
        return samplingTimer2;
    }

    @Override
    public long getWifiActiveTime(long l, int n) {
        return this.mWifiActiveTimer.getTotalTimeLocked(l, n);
    }

    public WifiBatteryStats getWifiBatteryStats() {
        int n;
        WifiBatteryStats wifiBatteryStats = new WifiBatteryStats();
        boolean bl = false;
        long l = SystemClock.elapsedRealtime() * 1000L;
        long[] arrl = this.getWifiControllerActivity();
        long l2 = arrl.getIdleTimeCounter().getCountLocked(0);
        long l3 = arrl.getScanTimeCounter().getCountLocked(0);
        long l4 = arrl.getRxTimeCounter().getCountLocked(0);
        long l5 = arrl.getTxTimeCounters()[0].getCountLocked(0);
        long l6 = this.computeBatteryRealtime(SystemClock.elapsedRealtime() * 1000L, 0) / 1000L;
        long l7 = arrl.getPowerCounter().getCountLocked(0);
        long l8 = arrl.getMonitoredRailChargeConsumedMaMs().getCountLocked(0);
        long l9 = 0L;
        for (n = 0; n < this.mUidStats.size(); ++n) {
            l9 += (long)this.mUidStats.valueAt((int)n).mWifiScanTimer.getCountLocked(0);
        }
        arrl = new long[8];
        for (n = 0; n < 8; ++n) {
            arrl[n] = this.getWifiStateTime(n, l, 0) / 1000L;
        }
        long[] arrl2 = new long[13];
        for (n = 0; n < 13; ++n) {
            arrl2[n] = this.getWifiSupplStateTime(n, l, 0) / 1000L;
        }
        long[] arrl3 = new long[5];
        for (n = 0; n < 5; ++n) {
            arrl3[n] = this.getWifiSignalStrengthTime(n, l, 0) / 1000L;
        }
        wifiBatteryStats.setLoggingDurationMs(this.computeBatteryRealtime(l, 0) / 1000L);
        wifiBatteryStats.setKernelActiveTimeMs(this.getWifiActiveTime(l, 0) / 1000L);
        wifiBatteryStats.setNumPacketsTx(this.getNetworkActivityPackets(3, 0));
        wifiBatteryStats.setNumBytesTx(this.getNetworkActivityBytes(3, 0));
        wifiBatteryStats.setNumPacketsRx(this.getNetworkActivityPackets(2, 0));
        wifiBatteryStats.setNumBytesRx(this.getNetworkActivityBytes(2, 0));
        wifiBatteryStats.setSleepTimeMs(l6 - (l2 + l4 + l5));
        wifiBatteryStats.setIdleTimeMs(l2);
        wifiBatteryStats.setRxTimeMs(l4);
        wifiBatteryStats.setTxTimeMs(l5);
        wifiBatteryStats.setScanTimeMs(l3);
        wifiBatteryStats.setEnergyConsumedMaMs(l7);
        wifiBatteryStats.setNumAppScanRequest(l9);
        wifiBatteryStats.setTimeInStateMs(arrl);
        wifiBatteryStats.setTimeInSupplicantStateMs(arrl2);
        wifiBatteryStats.setTimeInRxSignalStrengthLevelMs(arrl3);
        wifiBatteryStats.setMonitoredRailChargeConsumedMaMs(l8);
        return wifiBatteryStats;
    }

    @Override
    public BatteryStats.ControllerActivityCounter getWifiControllerActivity() {
        return this.mWifiActivity;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String[] getWifiIfaces() {
        Object object = this.mWifiNetworkLock;
        synchronized (object) {
            return this.mWifiIfaces;
        }
    }

    @Override
    public int getWifiMulticastWakelockCount(int n) {
        return this.mWifiMulticastWakelockTimer.getCountLocked(n);
    }

    @Override
    public long getWifiMulticastWakelockTime(long l, int n) {
        return this.mWifiMulticastWakelockTimer.getTotalTimeLocked(l, n);
    }

    @UnsupportedAppUsage
    @Override
    public long getWifiOnTime(long l, int n) {
        return this.mWifiOnTimer.getTotalTimeLocked(l, n);
    }

    @Override
    public int getWifiSignalStrengthCount(int n, int n2) {
        return this.mWifiSignalStrengthsTimer[n].getCountLocked(n2);
    }

    @Override
    public long getWifiSignalStrengthTime(int n, long l, int n2) {
        return this.mWifiSignalStrengthsTimer[n].getTotalTimeLocked(l, n2);
    }

    @Override
    public Timer getWifiSignalStrengthTimer(int n) {
        return this.mWifiSignalStrengthsTimer[n];
    }

    @Override
    public int getWifiStateCount(int n, int n2) {
        return this.mWifiStateTimer[n].getCountLocked(n2);
    }

    @Override
    public long getWifiStateTime(int n, long l, int n2) {
        return this.mWifiStateTimer[n].getTotalTimeLocked(l, n2);
    }

    @Override
    public Timer getWifiStateTimer(int n) {
        return this.mWifiStateTimer[n];
    }

    @Override
    public int getWifiSupplStateCount(int n, int n2) {
        return this.mWifiSupplStateTimer[n].getCountLocked(n2);
    }

    @Override
    public long getWifiSupplStateTime(int n, long l, int n2) {
        return this.mWifiSupplStateTimer[n].getTotalTimeLocked(l, n2);
    }

    @Override
    public Timer getWifiSupplStateTimer(int n) {
        return this.mWifiSupplStateTimer[n];
    }

    @Override
    public boolean hasBluetoothActivityReporting() {
        return this.mHasBluetoothReporting;
    }

    @Override
    public boolean hasModemActivityReporting() {
        return this.mHasModemReporting;
    }

    @Override
    public boolean hasWifiActivityReporting() {
        return this.mHasWifiReporting;
    }

    void initDischarge() {
        this.mLowDischargeAmountSinceCharge = 0;
        this.mHighDischargeAmountSinceCharge = 0;
        this.mDischargeAmountScreenOn = 0;
        this.mDischargeAmountScreenOnSinceCharge = 0;
        this.mDischargeAmountScreenOff = 0;
        this.mDischargeAmountScreenOffSinceCharge = 0;
        this.mDischargeAmountScreenDoze = 0;
        this.mDischargeAmountScreenDozeSinceCharge = 0;
        this.mDischargeStepTracker.init();
        this.mChargeStepTracker.init();
        this.mDischargeScreenOffCounter.reset(false);
        this.mDischargeScreenDozeCounter.reset(false);
        this.mDischargeLightDozeCounter.reset(false);
        this.mDischargeDeepDozeCounter.reset(false);
        this.mDischargeCounter.reset(false);
    }

    void initTimes(long l, long l2) {
        this.mStartClockTime = System.currentTimeMillis();
        this.mOnBatteryTimeBase.init(l, l2);
        this.mOnBatteryScreenOffTimeBase.init(l, l2);
        this.mRealtime = 0L;
        this.mUptime = 0L;
        this.mRealtimeStart = l2;
        this.mUptimeStart = l;
    }

    public boolean isCharging() {
        return this.mCharging;
    }

    @UnsupportedAppUsage
    public boolean isOnBattery() {
        return this.mOnBattery;
    }

    public boolean isOnBatteryLocked() {
        return this.mOnBatteryTimeBase.isRunning();
    }

    public boolean isOnBatteryScreenOffLocked() {
        return this.mOnBatteryScreenOffTimeBase.isRunning();
    }

    public boolean isScreenDoze(int n) {
        boolean bl = n == 3 || n == 4;
        return bl;
    }

    public boolean isScreenOff(int n) {
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isScreenOn(int n) {
        boolean bl = n == 2 || n == 5 || n == 6;
        return bl;
    }

    public /* synthetic */ void lambda$readKernelUidCpuActiveTimesLocked$2$BatteryStatsImpl(boolean bl, int n, Long serializable) {
        if (Process.isIsolated(n = this.mapUid(n))) {
            this.mCpuUidActiveTimeReader.removeUid(n);
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Got active times for an isolated uid with no mapping: ");
            ((StringBuilder)serializable).append(n);
            Slog.w(TAG, ((StringBuilder)serializable).toString());
            return;
        }
        if (!this.mUserInfoProvider.exists(UserHandle.getUserId(n))) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Got active times for an invalid user's uid ");
            ((StringBuilder)serializable).append(n);
            Slog.w(TAG, ((StringBuilder)serializable).toString());
            this.mCpuUidActiveTimeReader.removeUid(n);
            return;
        }
        this.getUidStatsLocked((int)n).mCpuActiveTimeMs.addCountLocked((Long)serializable, bl);
    }

    public /* synthetic */ void lambda$readKernelUidCpuClusterTimesLocked$3$BatteryStatsImpl(boolean bl, int n, long[] object) {
        if (Process.isIsolated(n = this.mapUid(n))) {
            this.mCpuUidClusterTimeReader.removeUid(n);
            object = new StringBuilder();
            ((StringBuilder)object).append("Got cluster times for an isolated uid with no mapping: ");
            ((StringBuilder)object).append(n);
            Slog.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        if (!this.mUserInfoProvider.exists(UserHandle.getUserId(n))) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Got cluster times for an invalid user's uid ");
            ((StringBuilder)object).append(n);
            Slog.w(TAG, ((StringBuilder)object).toString());
            this.mCpuUidClusterTimeReader.removeUid(n);
            return;
        }
        this.getUidStatsLocked((int)n).mCpuClusterTimesMs.addCountLocked((long[])object, bl);
    }

    public /* synthetic */ void lambda$readKernelUidCpuFreqTimesLocked$1$BatteryStatsImpl(boolean bl, boolean bl2, boolean bl3, int n, int n2, int n3, long[] object) {
        if (Process.isIsolated(n3 = this.mapUid(n3))) {
            this.mCpuUidFreqTimeReader.removeUid(n3);
            object = new StringBuilder();
            ((StringBuilder)object).append("Got freq readings for an isolated uid with no mapping: ");
            ((StringBuilder)object).append(n3);
            Slog.d(TAG, ((StringBuilder)object).toString());
            return;
        }
        if (!this.mUserInfoProvider.exists(UserHandle.getUserId(n3))) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Got freq readings for an invalid user's uid ");
            ((StringBuilder)object).append(n3);
            Slog.d(TAG, ((StringBuilder)object).toString());
            this.mCpuUidFreqTimeReader.removeUid(n3);
            return;
        }
        Uid uid = this.getUidStatsLocked(n3);
        if (uid.mCpuFreqTimeMs == null || uid.mCpuFreqTimeMs.getSize() != ((Object)object).length) {
            BatteryStatsImpl.detachIfNotNull(uid.mCpuFreqTimeMs);
            uid.mCpuFreqTimeMs = new LongSamplingCounterArray(this.mOnBatteryTimeBase);
        }
        uid.mCpuFreqTimeMs.addCountLocked((long[])object, bl);
        if (uid.mScreenOffCpuFreqTimeMs == null || uid.mScreenOffCpuFreqTimeMs.getSize() != ((Object)object).length) {
            BatteryStatsImpl.detachIfNotNull(uid.mScreenOffCpuFreqTimeMs);
            uid.mScreenOffCpuFreqTimeMs = new LongSamplingCounterArray(this.mOnBatteryScreenOffTimeBase);
        }
        uid.mScreenOffCpuFreqTimeMs.addCountLocked((long[])object, bl2);
        if (bl3) {
            if (uid.mCpuClusterSpeedTimesUs == null || uid.mCpuClusterSpeedTimesUs.length != n) {
                BatteryStatsImpl.detachIfNotNull(uid.mCpuClusterSpeedTimesUs);
                uid.mCpuClusterSpeedTimesUs = new LongSamplingCounter[n][];
            }
            if (n2 > 0 && this.mWakeLockAllocationsUs == null) {
                this.mWakeLockAllocationsUs = new long[n][];
            }
            int n4 = 0;
            for (n3 = 0; n3 < n; ++n3) {
                long[][] arrl;
                int n5 = this.mPowerProfile.getNumSpeedStepsInCpuCluster(n3);
                if (uid.mCpuClusterSpeedTimesUs[n3] == null || uid.mCpuClusterSpeedTimesUs[n3].length != n5) {
                    BatteryStatsImpl.detachIfNotNull(uid.mCpuClusterSpeedTimesUs[n3]);
                    uid.mCpuClusterSpeedTimesUs[n3] = new LongSamplingCounter[n5];
                }
                if (n2 > 0 && (arrl = this.mWakeLockAllocationsUs)[n3] == null) {
                    arrl[n3] = new long[n5];
                }
                arrl = uid.mCpuClusterSpeedTimesUs[n3];
                for (int i = 0; i < n5; ++i) {
                    reference var14_14;
                    long[][] arrl2;
                    if (arrl[i] == null) {
                        arrl[i] = new LongSamplingCounter(this.mOnBatteryTimeBase);
                    }
                    if ((arrl2 = this.mWakeLockAllocationsUs) != null) {
                        var14_14 = object[n4] * 1000L * 50L / 100L;
                        arrl2 = arrl2[n3];
                        arrl2[i] = arrl2[i] + (object[n4] * 1000L - var14_14);
                    } else {
                        var14_14 = object[n4] * 1000L;
                    }
                    arrl[i].addCountLocked((long)var14_14, bl);
                    ++n4;
                }
            }
        }
    }

    public /* synthetic */ void lambda$readKernelUidCpuTimesLocked$0$BatteryStatsImpl(int n, boolean bl, SparseLongArray object, int n2, long[] object2) {
        long l = object2[0];
        long l2 = object2[1];
        if (Process.isIsolated(n2 = this.mapUid(n2))) {
            this.mCpuUidUserSysTimeReader.removeUid(n2);
            object = new StringBuilder();
            ((StringBuilder)object).append("Got readings for an isolated uid with no mapping: ");
            ((StringBuilder)object).append(n2);
            Slog.d(TAG, ((StringBuilder)object).toString());
            return;
        }
        if (!this.mUserInfoProvider.exists(UserHandle.getUserId(n2))) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Got readings for an invalid user's uid ");
            ((StringBuilder)object).append(n2);
            Slog.d(TAG, ((StringBuilder)object).toString());
            this.mCpuUidUserSysTimeReader.removeUid(n2);
            return;
        }
        object2 = this.getUidStatsLocked(n2);
        this.mTempTotalCpuUserTimeUs += l;
        this.mTempTotalCpuSystemTimeUs += l2;
        long l3 = l;
        long l4 = l2;
        if (n > 0) {
            l3 = l * 50L / 100L;
            l4 = 50L * l2 / 100L;
        }
        if (false) {
            throw new NullPointerException();
        }
        ((Uid)object2).mUserCpuTime.addCountLocked(l3, bl);
        ((Uid)object2).mSystemCpuTime.addCountLocked(l4, bl);
        if (object != null) {
            ((SparseLongArray)object).put(((Uid)object2).getUid(), l3 + l4);
        }
    }

    public int mapUid(int n) {
        block0 : {
            int n2 = this.mIsolatedUids.get(n, -1);
            if (n2 <= 0) break block0;
            n = n2;
        }
        return n;
    }

    @VisibleForTesting
    public void markPartialTimersAsEligible() {
        if (ArrayUtils.referenceEquals(this.mPartialTimers, this.mLastPartialTimers)) {
            for (int i = this.mPartialTimers.size() - 1; i >= 0; --i) {
                this.mPartialTimers.get((int)i).mInList = true;
            }
        } else {
            int n;
            for (n = this.mLastPartialTimers.size() - 1; n >= 0; --n) {
                this.mLastPartialTimers.get((int)n).mInList = false;
            }
            this.mLastPartialTimers.clear();
            int n2 = this.mPartialTimers.size();
            for (n = 0; n < n2; ++n) {
                StopwatchTimer stopwatchTimer = this.mPartialTimers.get(n);
                stopwatchTimer.mInList = true;
                this.mLastPartialTimers.add(stopwatchTimer);
            }
        }
    }

    public void noteActivityPausedLocked(int n) {
        this.getUidStatsLocked(this.mapUid(n)).noteActivityPausedLocked(this.mClocks.elapsedRealtime());
    }

    public void noteActivityResumedLocked(int n) {
        this.getUidStatsLocked(this.mapUid(n)).noteActivityResumedLocked(this.mClocks.elapsedRealtime());
    }

    public void noteAlarmFinishLocked(String string2, WorkSource workSource, int n) {
        this.noteAlarmStartOrFinishLocked(16397, string2, workSource, n);
    }

    public void noteAlarmStartLocked(String string2, WorkSource workSource, int n) {
        this.noteAlarmStartOrFinishLocked(32781, string2, workSource, n);
    }

    @UnsupportedAppUsage
    public void noteAudioOffLocked(int n) {
        if (this.mAudioOnNesting == 0) {
            return;
        }
        int n2 = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        this.mAudioOnNesting = n = this.mAudioOnNesting - 1;
        if (n == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states &= -4194305;
            this.addHistoryRecordLocked(l, l2);
            this.mAudioOnTimer.stopRunningLocked(l);
        }
        this.getUidStatsLocked(n2).noteAudioTurnedOffLocked(l);
    }

    @UnsupportedAppUsage
    public void noteAudioOnLocked(int n) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (this.mAudioOnNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states |= 4194304;
            this.addHistoryRecordLocked(l, l2);
            this.mAudioOnTimer.startRunningLocked(l);
        }
        ++this.mAudioOnNesting;
        this.getUidStatsLocked(n).noteAudioTurnedOnLocked(l);
    }

    public void noteBluetoothScanResultsFromSourceLocked(WorkSource object, int n) {
        int n2;
        int n3 = ((WorkSource)object).size();
        for (n2 = 0; n2 < n3; ++n2) {
            this.getUidStatsLocked(this.mapUid(((WorkSource)object).get(n2))).noteBluetoothScanResultsLocked(n);
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n2 = 0; n2 < object.size(); ++n2) {
                this.getUidStatsLocked(this.mapUid(((WorkSource.WorkChain)object.get(n2)).getAttributionUid())).noteBluetoothScanResultsLocked(n);
            }
        }
    }

    public void noteBluetoothScanStartedFromSourceLocked(WorkSource object, boolean bl) {
        int n;
        int n2 = ((WorkSource)object).size();
        for (n = 0; n < n2; ++n) {
            this.noteBluetoothScanStartedLocked(null, ((WorkSource)object).get(n), bl);
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n = 0; n < object.size(); ++n) {
                this.noteBluetoothScanStartedLocked((WorkSource.WorkChain)object.get(n), -1, bl);
            }
        }
    }

    public void noteBluetoothScanStoppedFromSourceLocked(WorkSource object, boolean bl) {
        int n;
        int n2 = ((WorkSource)object).size();
        for (n = 0; n < n2; ++n) {
            this.noteBluetoothScanStoppedLocked(null, ((WorkSource)object).get(n), bl);
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n = 0; n < object.size(); ++n) {
                this.noteBluetoothScanStoppedLocked((WorkSource.WorkChain)object.get(n), -1, bl);
            }
        }
    }

    public void noteCameraOffLocked(int n) {
        if (this.mCameraOnNesting == 0) {
            return;
        }
        int n2 = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        this.mCameraOnNesting = n = this.mCameraOnNesting - 1;
        if (n == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 &= -2097153;
            this.addHistoryRecordLocked(l, l2);
            this.mCameraOnTimer.stopRunningLocked(l);
        }
        this.getUidStatsLocked(n2).noteCameraTurnedOffLocked(l);
    }

    public void noteCameraOnLocked(int n) {
        int n2 = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        n = this.mCameraOnNesting;
        this.mCameraOnNesting = n + 1;
        if (n == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 |= 2097152;
            this.addHistoryRecordLocked(l, l2);
            this.mCameraOnTimer.startRunningLocked(l);
        }
        this.getUidStatsLocked(n2).noteCameraTurnedOnLocked(l);
    }

    public void noteChangeWakelockFromSourceLocked(WorkSource object, int n, String string2, String string3, int n2, WorkSource object2, int n3, String string4, String string5, int n4, boolean bl) {
        int n5;
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        ArrayList<WorkSource.WorkChain>[] arrarrayList = WorkSource.diffChains((WorkSource)object, (WorkSource)object2);
        int n6 = ((WorkSource)object2).size();
        for (n5 = 0; n5 < n6; ++n5) {
            this.noteStartWakeLocked(((WorkSource)object2).get(n5), n3, null, string4, string5, n4, bl, l, l2);
        }
        if (arrarrayList != null && (object2 = arrarrayList[0]) != null) {
            for (n5 = 0; n5 < object2.size(); ++n5) {
                WorkSource.WorkChain workChain = (WorkSource.WorkChain)object2.get(n5);
                this.noteStartWakeLocked(workChain.getAttributionUid(), n3, workChain, string4, string5, n4, bl, l, l2);
            }
        }
        n4 = ((WorkSource)object).size();
        for (n3 = 0; n3 < n4; ++n3) {
            this.noteStopWakeLocked(((WorkSource)object).get(n3), n, null, string2, string3, n2, l, l2);
        }
        if (arrarrayList != null && (object = arrarrayList[1]) != null) {
            for (n3 = 0; n3 < object.size(); ++n3) {
                object2 = (WorkSource.WorkChain)object.get(n3);
                this.noteStopWakeLocked(((WorkSource.WorkChain)object2).getAttributionUid(), n, (WorkSource.WorkChain)object2, string2, string3, n2, l, l2);
            }
        }
    }

    public void noteConnectivityChangedLocked(int n, String string2) {
        this.addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 9, string2, n);
        ++this.mNumConnectivityChange;
    }

    public void noteCurrentTimeChangedLocked() {
        this.recordCurrentTimeChangeLocked(System.currentTimeMillis(), this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis());
    }

    public void noteDeviceIdleModeLocked(int n, String object, int n2) {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        boolean bl = n == 2;
        if (this.mDeviceIdling && !bl && object == null) {
            bl = true;
        }
        boolean bl2 = n == 1;
        if (this.mDeviceLightIdling && !bl2 && !bl && object == null) {
            bl2 = true;
        }
        if (object != null && (this.mDeviceIdling || this.mDeviceLightIdling)) {
            this.addHistoryEventLocked(l, l2, 10, (String)object, n2);
        }
        if (this.mDeviceIdling != bl || this.mDeviceLightIdling != bl2) {
            n2 = bl ? 2 : (bl2 ? 1 : 0);
            StatsLog.write(22, n2);
        }
        if (this.mDeviceIdling != bl) {
            this.mDeviceIdling = bl;
            n2 = bl ? 8 : 0;
            int n3 = this.mModStepMode;
            int n4 = this.mCurStepMode;
            this.mModStepMode = n3 | n4 & 8 ^ n2;
            this.mCurStepMode = n4 & -9 | n2;
            if (bl) {
                this.mDeviceIdlingTimer.startRunningLocked(l);
            } else {
                this.mDeviceIdlingTimer.stopRunningLocked(l);
            }
        }
        if (this.mDeviceLightIdling != bl2) {
            this.mDeviceLightIdling = bl2;
            if (bl2) {
                this.mDeviceLightIdlingTimer.startRunningLocked(l);
            } else {
                this.mDeviceLightIdlingTimer.stopRunningLocked(l);
            }
        }
        if (this.mDeviceIdleMode != n) {
            object = this.mHistoryCur;
            ((BatteryStats.HistoryItem)object).states2 = ((BatteryStats.HistoryItem)object).states2 & -100663297 | n << 25;
            this.addHistoryRecordLocked(l, l2);
            l2 = l - this.mLastIdleTimeStart;
            this.mLastIdleTimeStart = l;
            n2 = this.mDeviceIdleMode;
            if (n2 == 1) {
                if (l2 > this.mLongestLightIdleTime) {
                    this.mLongestLightIdleTime = l2;
                }
                this.mDeviceIdleModeLightTimer.stopRunningLocked(l);
            } else if (n2 == 2) {
                if (l2 > this.mLongestFullIdleTime) {
                    this.mLongestFullIdleTime = l2;
                }
                this.mDeviceIdleModeFullTimer.stopRunningLocked(l);
            }
            if (n == 1) {
                this.mDeviceIdleModeLightTimer.startRunningLocked(l);
            } else if (n == 2) {
                this.mDeviceIdleModeFullTimer.startRunningLocked(l);
            }
            this.mDeviceIdleMode = n;
            StatsLog.write(21, n);
        }
    }

    public void noteEventLocked(int n, String string2, int n2) {
        if (!this.mActiveEvents.updateState(n, string2, n2 = this.mapUid(n2), 0)) {
            return;
        }
        this.addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), n, string2, n2);
    }

    public void noteFlashlightOffLocked(int n) {
        if (this.mFlashlightOnNesting == 0) {
            return;
        }
        int n2 = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        this.mFlashlightOnNesting = n = this.mFlashlightOnNesting - 1;
        if (n == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 &= -134217729;
            this.addHistoryRecordLocked(l, l2);
            this.mFlashlightOnTimer.stopRunningLocked(l);
        }
        this.getUidStatsLocked(n2).noteFlashlightTurnedOffLocked(l);
    }

    public void noteFlashlightOnLocked(int n) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        int n2 = this.mFlashlightOnNesting;
        this.mFlashlightOnNesting = n2 + 1;
        if (n2 == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 |= 134217728;
            this.addHistoryRecordLocked(l, l2);
            this.mFlashlightOnTimer.startRunningLocked(l);
        }
        this.getUidStatsLocked(n).noteFlashlightTurnedOnLocked(l);
    }

    public void noteFullWifiLockAcquiredFromSourceLocked(WorkSource object) {
        int n;
        int n2 = ((WorkSource)object).size();
        for (n = 0; n < n2; ++n) {
            this.noteFullWifiLockAcquiredLocked(this.mapUid(((WorkSource)object).get(n)));
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n = 0; n < object.size(); ++n) {
                this.noteFullWifiLockAcquiredLocked(this.mapUid(((WorkSource.WorkChain)object.get(n)).getAttributionUid()));
            }
        }
    }

    @UnsupportedAppUsage
    public void noteFullWifiLockAcquiredLocked(int n) {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (this.mWifiFullLockNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states |= 268435456;
            this.addHistoryRecordLocked(l, l2);
        }
        ++this.mWifiFullLockNesting;
        this.getUidStatsLocked(n).noteFullWifiLockAcquiredLocked(l);
    }

    public void noteFullWifiLockReleasedFromSourceLocked(WorkSource object) {
        int n;
        int n2 = ((WorkSource)object).size();
        for (n = 0; n < n2; ++n) {
            this.noteFullWifiLockReleasedLocked(this.mapUid(((WorkSource)object).get(n)));
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n = 0; n < object.size(); ++n) {
                this.noteFullWifiLockReleasedLocked(this.mapUid(((WorkSource.WorkChain)object.get(n)).getAttributionUid()));
            }
        }
    }

    @UnsupportedAppUsage
    public void noteFullWifiLockReleasedLocked(int n) {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        --this.mWifiFullLockNesting;
        if (this.mWifiFullLockNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states &= -268435457;
            this.addHistoryRecordLocked(l, l2);
        }
        this.getUidStatsLocked(n).noteFullWifiLockReleasedLocked(l);
    }

    public void noteGpsChangedLocked(WorkSource arrayList, WorkSource arrarrayList) {
        int n;
        for (n = 0; n < arrarrayList.size(); ++n) {
            this.noteStartGpsLocked(arrarrayList.get(n), null);
        }
        for (n = 0; n < ((WorkSource)((Object)arrayList)).size(); ++n) {
            this.noteStopGpsLocked(((WorkSource)((Object)arrayList)).get(n), null);
        }
        if ((arrarrayList = WorkSource.diffChains((WorkSource)((Object)arrayList), (WorkSource)arrarrayList)) != null) {
            if (arrarrayList[0] != null) {
                arrayList = arrarrayList[0];
                for (n = 0; n < arrayList.size(); ++n) {
                    this.noteStartGpsLocked(-1, (WorkSource.WorkChain)arrayList.get(n));
                }
            }
            if (arrarrayList[1] != null) {
                arrayList = arrarrayList[1];
                for (n = 0; n < arrayList.size(); ++n) {
                    this.noteStopGpsLocked(-1, (WorkSource.WorkChain)arrayList.get(n));
                }
            }
        }
    }

    public void noteGpsSignalQualityLocked(int n) {
        if (this.mGpsNesting == 0) {
            return;
        }
        if (n >= 0 && n < 2) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            int n2 = this.mGpsSignalQualityBin;
            if (n2 != n) {
                if (n2 >= 0) {
                    this.mGpsSignalQualityTimer[n2].stopRunningLocked(l);
                }
                if (!this.mGpsSignalQualityTimer[n].isRunningLocked()) {
                    this.mGpsSignalQualityTimer[n].startRunningLocked(l);
                }
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states2 = historyItem.states2 & -129 | n << 7;
                this.addHistoryRecordLocked(l, l2);
                this.mGpsSignalQualityBin = n;
            }
            return;
        }
        this.stopAllGpsSignalQualityTimersLocked(-1);
    }

    public void noteInteractiveLocked(boolean bl) {
        if (this.mInteractive != bl) {
            long l = this.mClocks.elapsedRealtime();
            this.mInteractive = bl;
            if (bl) {
                this.mInteractiveTimer.startRunningLocked(l);
            } else {
                this.mInteractiveTimer.stopRunningLocked(l);
            }
        }
    }

    public void noteJobFinishLocked(String string2, int n, int n2) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        this.getUidStatsLocked(n).noteStopJobLocked(string2, l, n2);
        if (!this.mActiveEvents.updateState(16390, string2, n, 0)) {
            return;
        }
        this.addHistoryEventLocked(l, l2, 16390, string2, n);
    }

    public void noteJobStartLocked(String string2, int n) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        this.getUidStatsLocked(n).noteStartJobLocked(string2, l);
        if (!this.mActiveEvents.updateState(32774, string2, n, 0)) {
            return;
        }
        this.addHistoryEventLocked(l, l2, 32774, string2, n);
    }

    public void noteJobsDeferredLocked(int n, int n2, long l) {
        this.getUidStatsLocked(this.mapUid(n)).noteJobsDeferredLocked(n2, l);
    }

    public void noteLongPartialWakelockFinish(String string2, String string3, int n) {
        this.noteLongPartialWakeLockFinishInternal(string2, string3, this.mapUid(n));
    }

    public void noteLongPartialWakelockFinishFromSource(String string2, String string3, WorkSource object) {
        int n;
        int n2 = ((WorkSource)object).size();
        for (n = 0; n < n2; ++n) {
            this.noteLongPartialWakeLockFinishInternal(string2, string3, this.mapUid(((WorkSource)object).get(n)));
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n = 0; n < ((ArrayList)object).size(); ++n) {
                this.noteLongPartialWakeLockFinishInternal(string2, string3, ((WorkSource.WorkChain)((ArrayList)object).get(n)).getAttributionUid());
            }
        }
    }

    public void noteLongPartialWakelockStart(String string2, String string3, int n) {
        this.noteLongPartialWakeLockStartInternal(string2, string3, this.mapUid(n));
    }

    public void noteLongPartialWakelockStartFromSource(String string2, String string3, WorkSource object) {
        int n;
        int n2 = ((WorkSource)object).size();
        for (n = 0; n < n2; ++n) {
            this.noteLongPartialWakeLockStartInternal(string2, string3, this.mapUid(((WorkSource)object).get(n)));
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n = 0; n < ((ArrayList)object).size(); ++n) {
                this.noteLongPartialWakeLockStartInternal(string2, string3, ((WorkSource.WorkChain)((ArrayList)object).get(n)).getAttributionUid());
            }
        }
    }

    public boolean noteMobileRadioPowerStateLocked(int n, long l, int n2) {
        long l2 = this.mClocks.elapsedRealtime();
        long l3 = this.mClocks.uptimeMillis();
        if (this.mMobileRadioPowerState != n) {
            boolean bl = n == 2 || n == 3;
            if (bl) {
                if (n2 > 0) {
                    this.noteMobileRadioApWakeupLocked(l2, l3, n2);
                }
                this.mMobileRadioActiveStartTime = l /= 1000000L;
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states |= 33554432;
            } else {
                Object object;
                long l4 = l / 1000000L;
                l = this.mMobileRadioActiveStartTime;
                if (l4 < l) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Data connection inactive timestamp ");
                    ((StringBuilder)object).append(l4);
                    ((StringBuilder)object).append(" is before start time ");
                    ((StringBuilder)object).append(l);
                    Slog.wtf(TAG, ((StringBuilder)object).toString());
                    l = l2;
                } else {
                    l = l4;
                    if (l4 < l2) {
                        this.mMobileRadioActiveAdjustedTime.addCountLocked(l2 - l4);
                        l = l4;
                    }
                }
                object = this.mHistoryCur;
                ((BatteryStats.HistoryItem)object).states &= -33554433;
            }
            this.addHistoryRecordLocked(l2, l3);
            this.mMobileRadioPowerState = n;
            if (bl) {
                this.mMobileRadioActiveTimer.startRunningLocked(l2);
                this.mMobileRadioActivePerAppTimer.startRunningLocked(l2);
            } else {
                this.mMobileRadioActiveTimer.stopRunningLocked(l);
                this.mMobileRadioActivePerAppTimer.stopRunningLocked(l);
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void noteNetworkInterfaceTypeLocked(String string2, int n) {
        if (TextUtils.isEmpty(string2)) {
            return;
        }
        Object object = this.mModemNetworkLock;
        synchronized (object) {
            this.mModemIfaces = ConnectivityManager.isNetworkTypeMobile(n) ? BatteryStatsImpl.includeInStringArray(this.mModemIfaces, string2) : BatteryStatsImpl.excludeFromStringArray(this.mModemIfaces, string2);
        }
        object = this.mWifiNetworkLock;
        synchronized (object) {
            this.mWifiIfaces = ConnectivityManager.isNetworkTypeWifi(n) ? BatteryStatsImpl.includeInStringArray(this.mWifiIfaces, string2) : BatteryStatsImpl.excludeFromStringArray(this.mWifiIfaces, string2);
            return;
        }
    }

    public void notePackageInstalledLocked(String string2, long l) {
        this.addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 11, string2, (int)l);
        BatteryStats.PackageChange packageChange = new BatteryStats.PackageChange();
        packageChange.mPackageName = string2;
        packageChange.mUpdate = true;
        packageChange.mVersionCode = l;
        this.addPackageChange(packageChange);
    }

    public void notePackageUninstalledLocked(String string2) {
        this.addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 12, string2, 0);
        BatteryStats.PackageChange packageChange = new BatteryStats.PackageChange();
        packageChange.mPackageName = string2;
        packageChange.mUpdate = true;
        this.addPackageChange(packageChange);
    }

    @UnsupportedAppUsage
    public void notePhoneDataConnectionStateLocked(int n, boolean bl) {
        int n2 = 0;
        if (bl) {
            n2 = n > 0 && n <= 20 ? n : 21;
        }
        if (this.mPhoneDataConnectionType != n2) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = historyItem.states & -15873 | n2 << 9;
            this.addHistoryRecordLocked(l, l2);
            n = this.mPhoneDataConnectionType;
            if (n >= 0) {
                this.mPhoneDataConnectionsTimer[n].stopRunningLocked(l);
            }
            this.mPhoneDataConnectionType = n2;
            this.mPhoneDataConnectionsTimer[n2].startRunningLocked(l);
        }
    }

    @UnsupportedAppUsage
    public void notePhoneOffLocked() {
        if (this.mPhoneOn) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 &= -8388609;
            this.addHistoryRecordLocked(l, l2);
            this.mPhoneOn = false;
            this.mPhoneOnTimer.stopRunningLocked(l);
        }
    }

    @UnsupportedAppUsage
    public void notePhoneOnLocked() {
        if (!this.mPhoneOn) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 |= 8388608;
            this.addHistoryRecordLocked(l, l2);
            this.mPhoneOn = true;
            this.mPhoneOnTimer.startRunningLocked(l);
        }
    }

    @UnsupportedAppUsage
    public void notePhoneSignalStrengthLocked(SignalStrength signalStrength) {
        int n = signalStrength.getLevel();
        this.updateAllPhoneStateLocked(this.mPhoneServiceStateRaw, this.mPhoneSimStateRaw, n);
    }

    public void notePhoneStateLocked(int n, int n2) {
        this.updateAllPhoneStateLocked(n, n2, this.mPhoneSignalStrengthBinRaw);
    }

    public void notePowerSaveModeLocked(boolean bl) {
        if (this.mPowerSaveModeEnabled != bl) {
            int n = 0;
            int n2 = bl ? 4 : 0;
            int n3 = this.mModStepMode;
            int n4 = this.mCurStepMode;
            this.mModStepMode = n3 | n4 & 4 ^ n2;
            this.mCurStepMode = n4 & -5 | n2;
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            this.mPowerSaveModeEnabled = bl;
            if (bl) {
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states2 |= Integer.MIN_VALUE;
                this.mPowerSaveModeEnabledTimer.startRunningLocked(l);
            } else {
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states2 &= Integer.MAX_VALUE;
                this.mPowerSaveModeEnabledTimer.stopRunningLocked(l);
            }
            this.addHistoryRecordLocked(l, l2);
            n2 = bl ? 1 : n;
            StatsLog.write(20, n2);
        }
    }

    public void noteProcessAnrLocked(String string2, int n) {
        n = this.mapUid(n);
        if (this.isOnBattery()) {
            this.getUidStatsLocked(n).getProcessStatsLocked(string2).incNumAnrsLocked();
        }
    }

    public void noteProcessCrashLocked(String string2, int n) {
        n = this.mapUid(n);
        if (this.isOnBattery()) {
            this.getUidStatsLocked(n).getProcessStatsLocked(string2).incNumCrashesLocked();
        }
    }

    public void noteProcessDiedLocked(int n, int n2) {
        Uid uid = this.mUidStats.get(n = this.mapUid(n));
        if (uid != null) {
            uid.mPids.remove(n2);
        }
    }

    public void noteProcessFinishLocked(String string2, int n) {
        if (!this.mActiveEvents.updateState(16385, string2, n = this.mapUid(n), 0)) {
            return;
        }
        if (!this.mRecordAllHistory) {
            return;
        }
        this.addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 16385, string2, n);
    }

    public void noteProcessStartLocked(String string2, int n) {
        n = this.mapUid(n);
        if (this.isOnBattery()) {
            this.getUidStatsLocked(n).getProcessStatsLocked(string2).incStartsLocked();
        }
        if (!this.mActiveEvents.updateState(32769, string2, n, 0)) {
            return;
        }
        if (!this.mRecordAllHistory) {
            return;
        }
        this.addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 32769, string2, n);
    }

    public void noteResetAudioLocked() {
        if (this.mAudioOnNesting > 0) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            this.mAudioOnNesting = 0;
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states &= -4194305;
            this.addHistoryRecordLocked(l, l2);
            this.mAudioOnTimer.stopAllRunningLocked(l);
            for (int i = 0; i < this.mUidStats.size(); ++i) {
                this.mUidStats.valueAt(i).noteResetAudioLocked(l);
            }
        }
    }

    public void noteResetBluetoothScanLocked() {
        if (this.mBluetoothScanNesting > 0) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            this.mBluetoothScanNesting = 0;
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 &= -1048577;
            this.addHistoryRecordLocked(l, l2);
            this.mBluetoothScanTimer.stopAllRunningLocked(l);
            for (int i = 0; i < this.mUidStats.size(); ++i) {
                this.mUidStats.valueAt(i).noteResetBluetoothScanLocked(l);
            }
        }
    }

    public void noteResetCameraLocked() {
        if (this.mCameraOnNesting > 0) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            this.mCameraOnNesting = 0;
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 &= -2097153;
            this.addHistoryRecordLocked(l, l2);
            this.mCameraOnTimer.stopAllRunningLocked(l);
            for (int i = 0; i < this.mUidStats.size(); ++i) {
                this.mUidStats.valueAt(i).noteResetCameraLocked(l);
            }
        }
    }

    public void noteResetFlashlightLocked() {
        if (this.mFlashlightOnNesting > 0) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            this.mFlashlightOnNesting = 0;
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 &= -134217729;
            this.addHistoryRecordLocked(l, l2);
            this.mFlashlightOnTimer.stopAllRunningLocked(l);
            for (int i = 0; i < this.mUidStats.size(); ++i) {
                this.mUidStats.valueAt(i).noteResetFlashlightLocked(l);
            }
        }
    }

    public void noteResetVideoLocked() {
        if (this.mVideoOnNesting > 0) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            this.mAudioOnNesting = 0;
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 &= -1073741825;
            this.addHistoryRecordLocked(l, l2);
            this.mVideoOnTimer.stopAllRunningLocked(l);
            for (int i = 0; i < this.mUidStats.size(); ++i) {
                this.mUidStats.valueAt(i).noteResetVideoLocked(l);
            }
        }
    }

    @UnsupportedAppUsage
    public void noteScreenBrightnessLocked(int n) {
        int n2 = n / 51;
        if (n2 < 0) {
            n = 0;
        } else {
            n = n2;
            if (n2 >= 5) {
                n = 4;
            }
        }
        if (this.mScreenBrightnessBin != n) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = historyItem.states & -8 | n << 0;
            this.addHistoryRecordLocked(l, l2);
            if (this.mScreenState == 2) {
                n2 = this.mScreenBrightnessBin;
                if (n2 >= 0) {
                    this.mScreenBrightnessTimer[n2].stopRunningLocked(l);
                }
                this.mScreenBrightnessTimer[n].startRunningLocked(l);
            }
            this.mScreenBrightnessBin = n;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @GuardedBy(value={"this"})
    public void noteScreenStateLocked(int var1_1) {
        if (this.mPretendScreenOff) {
            var1_1 = 1;
        }
        if (var1_1 <= 4) ** GOTO lbl14
        if (var1_1 == 5) {
            var3_3 = 2;
        } else {
            var2_2 = new StringBuilder();
            var2_2.append("Unknown screen state (not mapped): ");
            var2_2.append(var1_1);
            Slog.wtf("BatteryStatsImpl", var2_2.toString());
lbl14: // 2 sources:
            var3_3 = var1_1;
        }
        if (this.mScreenState == var3_3) return;
        this.recordDailyStatsIfNeededLocked(true);
        var4_4 = this.mScreenState;
        this.mScreenState = var3_3;
        if (var3_3 != 0) {
            var1_1 = var3_3 - 1;
            if ((var1_1 & 3) == var1_1) {
                var5_5 = this.mModStepMode;
                var6_6 = this.mCurStepMode;
                this.mModStepMode = var5_5 | var6_6 & 3 ^ var1_1;
                this.mCurStepMode = var6_6 & -4 | var1_1;
            } else {
                var2_2 = new StringBuilder();
                var2_2.append("Unexpected screen state: ");
                var2_2.append(var3_3);
                Slog.wtf("BatteryStatsImpl", var2_2.toString());
            }
        }
        var7_7 = this.mClocks.elapsedRealtime();
        var9_8 = this.mClocks.uptimeMillis();
        var1_1 = 0;
        if (this.isScreenDoze(var3_3)) {
            var2_2 = this.mHistoryCur;
            var2_2.states |= 262144;
            this.mScreenDozeTimer.startRunningLocked(var7_7);
            var1_1 = 1;
        } else if (this.isScreenDoze(var4_4)) {
            var2_2 = this.mHistoryCur;
            var2_2.states &= -262145;
            this.mScreenDozeTimer.stopRunningLocked(var7_7);
            var1_1 = 1;
        }
        if (this.isScreenOn(var3_3)) {
            var2_2 = this.mHistoryCur;
            var2_2.states |= 1048576;
            this.mScreenOnTimer.startRunningLocked(var7_7);
            var1_1 = this.mScreenBrightnessBin;
            if (var1_1 >= 0) {
                this.mScreenBrightnessTimer[var1_1].startRunningLocked(var7_7);
            }
            var1_1 = 1;
        } else if (this.isScreenOn(var4_4)) {
            var2_2 = this.mHistoryCur;
            var2_2.states &= -1048577;
            this.mScreenOnTimer.stopRunningLocked(var7_7);
            var1_1 = this.mScreenBrightnessBin;
            if (var1_1 >= 0) {
                this.mScreenBrightnessTimer[var1_1].stopRunningLocked(var7_7);
            }
            var1_1 = 1;
        }
        if (var1_1 != 0) {
            this.addHistoryRecordLocked(var7_7, var9_8);
        }
        this.mExternalSync.scheduleCpuSyncDueToScreenStateChange(this.mOnBatteryTimeBase.isRunning(), this.mOnBatteryScreenOffTimeBase.isRunning());
        if (this.isScreenOn(var3_3)) {
            this.updateTimeBasesLocked(this.mOnBatteryTimeBase.isRunning(), var3_3, this.mClocks.uptimeMillis() * 1000L, var7_7 * 1000L);
            this.noteStartWakeLocked(-1, -1, null, "screen", null, 0, false, var7_7, var9_8);
        } else if (this.isScreenOn(var4_4)) {
            this.noteStopWakeLocked(-1, -1, null, "screen", "screen", 0, var7_7, var9_8);
            this.updateTimeBasesLocked(this.mOnBatteryTimeBase.isRunning(), var3_3, this.mClocks.uptimeMillis() * 1000L, var7_7 * 1000L);
        }
        if (this.mOnBatteryInternal == false) return;
        this.updateDischargeScreenLevelsLocked(var4_4, var3_3);
    }

    public void noteStartSensorLocked(int n, int n2) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (this.mSensorNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states |= 8388608;
            this.addHistoryRecordLocked(l, l2);
        }
        ++this.mSensorNesting;
        this.getUidStatsLocked(n).noteStartSensor(n2, l);
    }

    public void noteStartWakeFromSourceLocked(WorkSource object, int n, String string2, String string3, int n2, boolean bl) {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        int n3 = ((WorkSource)object).size();
        for (int i = 0; i < n3; ++i) {
            this.noteStartWakeLocked(((WorkSource)object).get(i), n, null, string2, string3, n2, bl, l, l2);
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n3 = 0; n3 < object.size(); ++n3) {
                WorkSource.WorkChain workChain = (WorkSource.WorkChain)object.get(n3);
                this.noteStartWakeLocked(workChain.getAttributionUid(), n, workChain, string2, string3, n2, bl, l, l2);
            }
        }
    }

    public void noteStartWakeLocked(int n, int n2, WorkSource.WorkChain workChain, String string2, String object, int n3, boolean bl, long l, long l2) {
        n = this.mapUid(n);
        if (n3 == 0) {
            this.aggregateLastWakeupUptimeLocked(l2);
            if (object == null) {
                object = string2;
            }
            if (this.mRecordAllHistory && this.mActiveEvents.updateState(32773, (String)object, n, 0)) {
                this.addHistoryEventLocked(l, l2, 32773, (String)object, n);
            }
            if (this.mWakeLockNesting == 0) {
                Object object2 = this.mHistoryCur;
                ((BatteryStats.HistoryItem)object2).states |= 1073741824;
                object2 = this.mHistoryCur;
                ((BatteryStats.HistoryItem)object2).wakelockTag = ((BatteryStats.HistoryItem)object2).localWakelockTag;
                object2 = this.mHistoryCur.wakelockTag;
                this.mInitialAcquireWakeName = object;
                ((BatteryStats.HistoryTag)object2).string = object;
                object = this.mHistoryCur.wakelockTag;
                this.mInitialAcquireWakeUid = n;
                ((BatteryStats.HistoryTag)object).uid = n;
                this.mWakeLockImportant = bl ^ true;
                this.addHistoryRecordLocked(l, l2);
            } else if (!this.mWakeLockImportant && !bl && this.mHistoryLastWritten.cmd == 0) {
                if (this.mHistoryLastWritten.wakelockTag != null) {
                    this.mHistoryLastWritten.wakelockTag = null;
                    Object object3 = this.mHistoryCur;
                    ((BatteryStats.HistoryItem)object3).wakelockTag = ((BatteryStats.HistoryItem)object3).localWakelockTag;
                    object3 = this.mHistoryCur.wakelockTag;
                    this.mInitialAcquireWakeName = object;
                    ((BatteryStats.HistoryTag)object3).string = object;
                    object = this.mHistoryCur.wakelockTag;
                    this.mInitialAcquireWakeUid = n;
                    ((BatteryStats.HistoryTag)object).uid = n;
                    this.addHistoryRecordLocked(l, l2);
                }
                this.mWakeLockImportant = true;
            }
            ++this.mWakeLockNesting;
        }
        if (n >= 0) {
            if (this.mOnBatteryScreenOffTimeBase.isRunning()) {
                this.requestWakelockCpuUpdate();
            }
            this.getUidStatsLocked(n).noteStartWakeLocked(n2, string2, n3, l);
            if (workChain != null) {
                StatsLog.write(10, workChain.getUids(), workChain.getTags(), this.getPowerManagerWakeLockLevel(n3), string2, 1);
            } else {
                StatsLog.write_non_chained(10, n, null, this.getPowerManagerWakeLockLevel(n3), string2, 1);
            }
        }
    }

    public void noteStopSensorLocked(int n, int n2) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        --this.mSensorNesting;
        if (this.mSensorNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states &= -8388609;
            this.addHistoryRecordLocked(l, l2);
        }
        this.getUidStatsLocked(n).noteStopSensor(n2, l);
    }

    public void noteStopWakeFromSourceLocked(WorkSource object, int n, String string2, String string3, int n2) {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        int n3 = ((WorkSource)object).size();
        for (int i = 0; i < n3; ++i) {
            this.noteStopWakeLocked(((WorkSource)object).get(i), n, null, string2, string3, n2, l, l2);
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n3 = 0; n3 < object.size(); ++n3) {
                WorkSource.WorkChain workChain = (WorkSource.WorkChain)object.get(n3);
                this.noteStopWakeLocked(workChain.getAttributionUid(), n, workChain, string2, string3, n2, l, l2);
            }
        }
    }

    public void noteStopWakeLocked(int n, int n2, WorkSource.WorkChain workChain, String string2, String object, int n3, long l, long l2) {
        n = this.mapUid(n);
        if (n3 == 0) {
            --this.mWakeLockNesting;
            if (this.mRecordAllHistory) {
                if (object == null) {
                    object = string2;
                }
                if (this.mActiveEvents.updateState(16389, (String)object, n, 0)) {
                    this.addHistoryEventLocked(l, l2, 16389, (String)object, n);
                }
            }
            if (this.mWakeLockNesting == 0) {
                object = this.mHistoryCur;
                ((BatteryStats.HistoryItem)object).states &= -1073741825;
                this.mInitialAcquireWakeName = null;
                this.mInitialAcquireWakeUid = -1;
                this.addHistoryRecordLocked(l, l2);
            }
        }
        if (n >= 0) {
            if (this.mOnBatteryScreenOffTimeBase.isRunning()) {
                this.requestWakelockCpuUpdate();
            }
            this.getUidStatsLocked(n).noteStopWakeLocked(n2, string2, n3, l);
            if (workChain != null) {
                StatsLog.write(10, workChain.getUids(), workChain.getTags(), this.getPowerManagerWakeLockLevel(n3), string2, 0);
            } else {
                StatsLog.write_non_chained(10, n, null, this.getPowerManagerWakeLockLevel(n3), string2, 0);
            }
        }
    }

    public void noteSyncFinishLocked(String string2, int n) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        this.getUidStatsLocked(n).noteStopSyncLocked(string2, l);
        if (!this.mActiveEvents.updateState(16388, string2, n, 0)) {
            return;
        }
        this.addHistoryEventLocked(l, l2, 16388, string2, n);
    }

    public void noteSyncStartLocked(String string2, int n) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        this.getUidStatsLocked(n).noteStartSyncLocked(string2, l);
        if (!this.mActiveEvents.updateState(32772, string2, n, 0)) {
            return;
        }
        this.addHistoryEventLocked(l, l2, 32772, string2, n);
    }

    public void noteUidProcessStateLocked(int n, int n2) {
        if (n != this.mapUid(n)) {
            return;
        }
        this.getUidStatsLocked(n).updateUidProcessStateLocked(n2);
    }

    @UnsupportedAppUsage
    public void noteUserActivityLocked(int n, int n2) {
        if (this.mOnBatteryInternal) {
            this.getUidStatsLocked(this.mapUid(n)).noteUserActivityLocked(n2);
        }
    }

    public void noteVibratorOffLocked(int n) {
        this.getUidStatsLocked(this.mapUid(n)).noteVibratorOffLocked();
    }

    public void noteVibratorOnLocked(int n, long l) {
        this.getUidStatsLocked(this.mapUid(n)).noteVibratorOnLocked(l);
    }

    @UnsupportedAppUsage
    public void noteVideoOffLocked(int n) {
        if (this.mVideoOnNesting == 0) {
            return;
        }
        int n2 = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        this.mVideoOnNesting = n = this.mVideoOnNesting - 1;
        if (n == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 &= -1073741825;
            this.addHistoryRecordLocked(l, l2);
            this.mVideoOnTimer.stopRunningLocked(l);
        }
        this.getUidStatsLocked(n2).noteVideoTurnedOffLocked(l);
    }

    @UnsupportedAppUsage
    public void noteVideoOnLocked(int n) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (this.mVideoOnNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 |= 1073741824;
            this.addHistoryRecordLocked(l, l2);
            this.mVideoOnTimer.startRunningLocked(l);
        }
        ++this.mVideoOnNesting;
        this.getUidStatsLocked(n).noteVideoTurnedOnLocked(l);
    }

    public void noteWakeUpLocked(String string2, int n) {
        this.addHistoryEventLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis(), 18, string2, n);
    }

    public void noteWakeupReasonLocked(String string2) {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        this.aggregateLastWakeupUptimeLocked(l2);
        BatteryStats.HistoryItem historyItem = this.mHistoryCur;
        historyItem.wakeReasonTag = historyItem.localWakeReasonTag;
        this.mHistoryCur.wakeReasonTag.string = string2;
        this.mHistoryCur.wakeReasonTag.uid = 0;
        this.mLastWakeupReason = string2;
        this.mLastWakeupUptimeMs = l2;
        this.addHistoryRecordLocked(l, l2);
    }

    public void noteWakupAlarmLocked(String string2, int n, WorkSource object, String string3) {
        if (object != null) {
            int n2;
            for (n = 0; n < ((WorkSource)object).size(); ++n) {
                n2 = ((WorkSource)object).get(n);
                String string4 = ((WorkSource)object).getName(n);
                if (!this.isOnBattery()) continue;
                if (string4 == null) {
                    string4 = string2;
                }
                this.getPackageStatsLocked(n2, string4).noteWakeupAlarmLocked(string3);
            }
            if ((object = ((WorkSource)object).getWorkChains()) != null) {
                for (n = 0; n < ((ArrayList)object).size(); ++n) {
                    n2 = ((WorkSource.WorkChain)((ArrayList)object).get(n)).getAttributionUid();
                    if (!this.isOnBattery()) continue;
                    this.getPackageStatsLocked(n2, string2).noteWakeupAlarmLocked(string3);
                }
            }
        } else if (this.isOnBattery()) {
            this.getPackageStatsLocked(n, string2).noteWakeupAlarmLocked(string3);
        }
    }

    public void noteWifiBatchedScanStartedFromSourceLocked(WorkSource object, int n) {
        int n2;
        int n3 = ((WorkSource)object).size();
        for (n2 = 0; n2 < n3; ++n2) {
            this.noteWifiBatchedScanStartedLocked(((WorkSource)object).get(n2), n);
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n2 = 0; n2 < object.size(); ++n2) {
                this.noteWifiBatchedScanStartedLocked(((WorkSource.WorkChain)object.get(n2)).getAttributionUid(), n);
            }
        }
    }

    public void noteWifiBatchedScanStartedLocked(int n, int n2) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        this.getUidStatsLocked(n).noteWifiBatchedScanStartedLocked(n2, l);
    }

    public void noteWifiBatchedScanStoppedFromSourceLocked(WorkSource object) {
        int n;
        int n2 = ((WorkSource)object).size();
        for (n = 0; n < n2; ++n) {
            this.noteWifiBatchedScanStoppedLocked(((WorkSource)object).get(n));
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n = 0; n < object.size(); ++n) {
                this.noteWifiBatchedScanStoppedLocked(((WorkSource.WorkChain)object.get(n)).getAttributionUid());
            }
        }
    }

    public void noteWifiBatchedScanStoppedLocked(int n) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        this.getUidStatsLocked(n).noteWifiBatchedScanStoppedLocked(l);
    }

    @UnsupportedAppUsage
    public void noteWifiMulticastDisabledLocked(int n) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        --this.mWifiMulticastNesting;
        if (this.mWifiMulticastNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states &= -65537;
            this.addHistoryRecordLocked(l, l2);
            if (this.mWifiMulticastWakelockTimer.isRunningLocked()) {
                this.mWifiMulticastWakelockTimer.stopRunningLocked(l);
            }
        }
        this.getUidStatsLocked(n).noteWifiMulticastDisabledLocked(l);
    }

    @UnsupportedAppUsage
    public void noteWifiMulticastEnabledLocked(int n) {
        n = this.mapUid(n);
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (this.mWifiMulticastNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states |= 65536;
            this.addHistoryRecordLocked(l, l2);
            if (!this.mWifiMulticastWakelockTimer.isRunningLocked()) {
                this.mWifiMulticastWakelockTimer.startRunningLocked(l);
            }
        }
        ++this.mWifiMulticastNesting;
        this.getUidStatsLocked(n).noteWifiMulticastEnabledLocked(l);
    }

    public void noteWifiOffLocked() {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (this.mWifiOn) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 &= -268435457;
            this.addHistoryRecordLocked(l, l2);
            this.mWifiOn = false;
            this.mWifiOnTimer.stopRunningLocked(l);
            this.scheduleSyncExternalStatsLocked("wifi-on", 2);
        }
    }

    public void noteWifiOnLocked() {
        if (!this.mWifiOn) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 |= 268435456;
            this.addHistoryRecordLocked(l, l2);
            this.mWifiOn = true;
            this.mWifiOnTimer.startRunningLocked(l);
            this.scheduleSyncExternalStatsLocked("wifi-off", 2);
        }
    }

    public void noteWifiRadioPowerState(int n, long l, int n2) {
        long l2 = this.mClocks.elapsedRealtime();
        long l3 = this.mClocks.uptimeMillis();
        if (this.mWifiRadioPowerState != n) {
            boolean bl = n == 2 || n == 3;
            if (bl) {
                if (n2 > 0) {
                    this.noteWifiRadioApWakeupLocked(l2, l3, n2);
                }
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states |= 67108864;
                this.mWifiActiveTimer.startRunningLocked(l2);
            } else {
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states &= -67108865;
                this.mWifiActiveTimer.stopRunningLocked(l / 1000000L);
            }
            this.addHistoryRecordLocked(l2, l3);
            this.mWifiRadioPowerState = n;
        }
    }

    public void noteWifiRssiChangedLocked(int n) {
        int n2 = WifiManager.calculateSignalLevel(n, 5);
        if (this.mWifiSignalStrengthBin != n2) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            n = this.mWifiSignalStrengthBin;
            if (n >= 0) {
                this.mWifiSignalStrengthsTimer[n].stopRunningLocked(l);
            }
            if (n2 >= 0) {
                if (!this.mWifiSignalStrengthsTimer[n2].isRunningLocked()) {
                    this.mWifiSignalStrengthsTimer[n2].startRunningLocked(l);
                }
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states2 = historyItem.states2 & -113 | n2 << 4;
                this.addHistoryRecordLocked(l, l2);
            } else {
                this.stopAllWifiSignalStrengthTimersLocked(-1);
            }
            this.mWifiSignalStrengthBin = n2;
        }
    }

    public void noteWifiRunningChangedLocked(WorkSource arrayList, WorkSource workSource) {
        if (this.mGlobalWifiRunning) {
            int n;
            long l = this.mClocks.elapsedRealtime();
            int n2 = ((WorkSource)((Object)arrayList)).size();
            for (n = 0; n < n2; ++n) {
                this.getUidStatsLocked(this.mapUid(((WorkSource)((Object)arrayList)).get(n))).noteWifiStoppedLocked(l);
            }
            if ((arrayList = ((WorkSource)((Object)arrayList)).getWorkChains()) != null) {
                for (n = 0; n < arrayList.size(); ++n) {
                    this.getUidStatsLocked(this.mapUid(((WorkSource.WorkChain)arrayList.get(n)).getAttributionUid())).noteWifiStoppedLocked(l);
                }
            }
            n2 = workSource.size();
            for (n = 0; n < n2; ++n) {
                this.getUidStatsLocked(this.mapUid(workSource.get(n))).noteWifiRunningLocked(l);
            }
            arrayList = workSource.getWorkChains();
            if (arrayList != null) {
                for (n = 0; n < arrayList.size(); ++n) {
                    this.getUidStatsLocked(this.mapUid(((WorkSource.WorkChain)arrayList.get(n)).getAttributionUid())).noteWifiRunningLocked(l);
                }
            }
        } else {
            Log.w(TAG, "noteWifiRunningChangedLocked -- called while WIFI not running");
        }
    }

    public void noteWifiRunningLocked(WorkSource object) {
        if (!this.mGlobalWifiRunning) {
            int n;
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 |= 536870912;
            this.addHistoryRecordLocked(l, l2);
            this.mGlobalWifiRunning = true;
            this.mGlobalWifiRunningTimer.startRunningLocked(l);
            int n2 = ((WorkSource)object).size();
            for (n = 0; n < n2; ++n) {
                this.getUidStatsLocked(this.mapUid(((WorkSource)object).get(n))).noteWifiRunningLocked(l);
            }
            if ((object = ((WorkSource)object).getWorkChains()) != null) {
                for (n = 0; n < object.size(); ++n) {
                    this.getUidStatsLocked(this.mapUid(((WorkSource.WorkChain)object.get(n)).getAttributionUid())).noteWifiRunningLocked(l);
                }
            }
            this.scheduleSyncExternalStatsLocked("wifi-running", 2);
        } else {
            Log.w(TAG, "noteWifiRunningLocked -- called while WIFI running");
        }
    }

    public void noteWifiScanStartedFromSourceLocked(WorkSource object) {
        int n;
        int n2 = ((WorkSource)object).size();
        for (n = 0; n < n2; ++n) {
            this.noteWifiScanStartedLocked(this.mapUid(((WorkSource)object).get(n)));
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n = 0; n < object.size(); ++n) {
                this.noteWifiScanStartedLocked(this.mapUid(((WorkSource.WorkChain)object.get(n)).getAttributionUid()));
            }
        }
    }

    public void noteWifiScanStartedLocked(int n) {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        if (this.mWifiScanNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states |= 134217728;
            this.addHistoryRecordLocked(l, l2);
        }
        ++this.mWifiScanNesting;
        this.getUidStatsLocked(n).noteWifiScanStartedLocked(l);
    }

    public void noteWifiScanStoppedFromSourceLocked(WorkSource object) {
        int n;
        int n2 = ((WorkSource)object).size();
        for (n = 0; n < n2; ++n) {
            this.noteWifiScanStoppedLocked(this.mapUid(((WorkSource)object).get(n)));
        }
        if ((object = ((WorkSource)object).getWorkChains()) != null) {
            for (n = 0; n < object.size(); ++n) {
                this.noteWifiScanStoppedLocked(this.mapUid(((WorkSource.WorkChain)object.get(n)).getAttributionUid()));
            }
        }
    }

    public void noteWifiScanStoppedLocked(int n) {
        long l = this.mClocks.elapsedRealtime();
        long l2 = this.mClocks.uptimeMillis();
        --this.mWifiScanNesting;
        if (this.mWifiScanNesting == 0) {
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states &= -134217729;
            this.addHistoryRecordLocked(l, l2);
        }
        this.getUidStatsLocked(n).noteWifiScanStoppedLocked(l);
    }

    public void noteWifiStateLocked(int n, String string2) {
        if (this.mWifiState != n) {
            long l = this.mClocks.elapsedRealtime();
            int n2 = this.mWifiState;
            if (n2 >= 0) {
                this.mWifiStateTimer[n2].stopRunningLocked(l);
            }
            this.mWifiState = n;
            this.mWifiStateTimer[n].startRunningLocked(l);
            this.scheduleSyncExternalStatsLocked("wifi-state", 2);
        }
    }

    public void noteWifiStoppedLocked(WorkSource object) {
        if (this.mGlobalWifiRunning) {
            int n;
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 &= -536870913;
            this.addHistoryRecordLocked(l, l2);
            this.mGlobalWifiRunning = false;
            this.mGlobalWifiRunningTimer.stopRunningLocked(l);
            int n2 = ((WorkSource)object).size();
            for (n = 0; n < n2; ++n) {
                this.getUidStatsLocked(this.mapUid(((WorkSource)object).get(n))).noteWifiStoppedLocked(l);
            }
            if ((object = ((WorkSource)object).getWorkChains()) != null) {
                for (n = 0; n < object.size(); ++n) {
                    this.getUidStatsLocked(this.mapUid(((WorkSource.WorkChain)object.get(n)).getAttributionUid())).noteWifiStoppedLocked(l);
                }
            }
            this.scheduleSyncExternalStatsLocked("wifi-stopped", 2);
        } else {
            Log.w(TAG, "noteWifiStoppedLocked -- called while WIFI not running");
        }
    }

    public void noteWifiSupplicantStateChangedLocked(int n, boolean bl) {
        if (this.mWifiSupplState != n) {
            long l = this.mClocks.elapsedRealtime();
            long l2 = this.mClocks.uptimeMillis();
            int n2 = this.mWifiSupplState;
            if (n2 >= 0) {
                this.mWifiSupplStateTimer[n2].stopRunningLocked(l);
            }
            this.mWifiSupplState = n;
            this.mWifiSupplStateTimer[n].startRunningLocked(l);
            BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = historyItem.states2 & -16 | n << 0;
            this.addHistoryRecordLocked(l, l2);
        }
    }

    public void onCleanupUserLocked(int n) {
        int n2 = UserHandle.getUid(n, 0);
        n = UserHandle.getUid(n, 99999);
        this.mPendingRemovedUids.add(new UidToRemove(n2, n, this.mClocks.elapsedRealtime()));
    }

    public void onUserRemovedLocked(int n) {
        int n2 = UserHandle.getUid(n, 0);
        n = UserHandle.getUid(n, 99999);
        this.mUidStats.put(n2, null);
        this.mUidStats.put(n, null);
        n2 = this.mUidStats.indexOfKey(n2);
        int n3 = this.mUidStats.indexOfKey(n);
        for (n = n2; n <= n3; ++n) {
            Uid uid = this.mUidStats.valueAt(n);
            if (uid == null) continue;
            uid.detachFromTimeBase();
        }
        this.mUidStats.removeAtRange(n2, n3 - n2 + 1);
    }

    public void postBatteryNeedsCpuUpdateMsg() {
        this.mHandler.sendEmptyMessage(1);
    }

    @Override
    public void prepareForDumpLocked() {
        this.pullPendingStateUpdatesLocked();
        this.getStartClockTime();
    }

    public void pullPendingStateUpdatesLocked() {
        if (this.mOnBatteryInternal) {
            int n = this.mScreenState;
            this.updateDischargeScreenLevelsLocked(n, n);
        }
    }

    void readDailyItemTagDetailsLocked(XmlPullParser xmlPullParser, BatteryStats.DailyItem object, boolean bl, String string2) throws NumberFormatException, XmlPullParserException, IOException {
        int n;
        Object object2 = xmlPullParser.getAttributeValue(null, "n");
        if (object2 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Missing 'n' attribute at ");
            ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
            Slog.w(TAG, ((StringBuilder)object).toString());
            XmlUtils.skipCurrentTag(xmlPullParser);
            return;
        }
        int n2 = Integer.parseInt((String)object2);
        object2 = new BatteryStats.LevelStepTracker(n2);
        if (bl) {
            ((BatteryStats.DailyItem)object).mChargeSteps = object2;
        } else {
            ((BatteryStats.DailyItem)object).mDischargeSteps = object2;
        }
        int n3 = 0;
        int n4 = xmlPullParser.getDepth();
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n4)) {
            if (n == 3 || n == 4) continue;
            if ("s".equals(xmlPullParser.getName())) {
                n = n3;
                if (n3 < n2) {
                    object = xmlPullParser.getAttributeValue(null, "v");
                    n = n3;
                    if (object != null) {
                        ((BatteryStats.LevelStepTracker)object2).decodeEntryAt(n3, (String)object);
                        n = n3 + 1;
                    }
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown element under <");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(">: ");
                ((StringBuilder)object).append(xmlPullParser.getName());
                Slog.w(TAG, ((StringBuilder)object).toString());
                XmlUtils.skipCurrentTag(xmlPullParser);
                n = n3;
            }
            n3 = n;
        }
        ((BatteryStats.LevelStepTracker)object2).mNumStepDurations = n3;
    }

    void readDailyItemTagLocked(XmlPullParser xmlPullParser) throws NumberFormatException, XmlPullParserException, IOException {
        int n;
        BatteryStats.DailyItem dailyItem = new BatteryStats.DailyItem();
        Object object = xmlPullParser.getAttributeValue(null, "start");
        if (object != null) {
            dailyItem.mStartTime = Long.parseLong((String)object);
        }
        if ((object = xmlPullParser.getAttributeValue(null, "end")) != null) {
            dailyItem.mEndTime = Long.parseLong((String)object);
        }
        int n2 = xmlPullParser.getDepth();
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
            if (n == 3 || n == 4) continue;
            object = xmlPullParser.getName();
            if (((String)object).equals("dis")) {
                this.readDailyItemTagDetailsLocked(xmlPullParser, dailyItem, false, "dis");
                continue;
            }
            if (((String)object).equals("chg")) {
                this.readDailyItemTagDetailsLocked(xmlPullParser, dailyItem, true, "chg");
                continue;
            }
            if (((String)object).equals("upd")) {
                if (dailyItem.mPackageChanges == null) {
                    dailyItem.mPackageChanges = new ArrayList();
                }
                object = new BatteryStats.PackageChange();
                ((BatteryStats.PackageChange)object).mUpdate = true;
                ((BatteryStats.PackageChange)object).mPackageName = xmlPullParser.getAttributeValue(null, "pkg");
                String string2 = xmlPullParser.getAttributeValue(null, "ver");
                long l = string2 != null ? Long.parseLong(string2) : 0L;
                ((BatteryStats.PackageChange)object).mVersionCode = l;
                dailyItem.mPackageChanges.add((BatteryStats.PackageChange)object);
                XmlUtils.skipCurrentTag(xmlPullParser);
                continue;
            }
            if (((String)object).equals("rem")) {
                if (dailyItem.mPackageChanges == null) {
                    dailyItem.mPackageChanges = new ArrayList();
                }
                object = new BatteryStats.PackageChange();
                ((BatteryStats.PackageChange)object).mUpdate = false;
                ((BatteryStats.PackageChange)object).mPackageName = xmlPullParser.getAttributeValue(null, "pkg");
                dailyItem.mPackageChanges.add((BatteryStats.PackageChange)object);
                XmlUtils.skipCurrentTag(xmlPullParser);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown element under <item>: ");
            ((StringBuilder)object).append(xmlPullParser.getName());
            Slog.w(TAG, ((StringBuilder)object).toString());
            XmlUtils.skipCurrentTag(xmlPullParser);
        }
        this.mDailyItems.add(dailyItem);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void readDailyStatsLocked() {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Reading daily items from ");
        ((StringBuilder)object).append(this.mDailyFile.getBaseFile());
        Slog.d(TAG, ((StringBuilder)object).toString());
        this.mDailyItems.clear();
        try {
            object = this.mDailyFile.openRead();
        }
        catch (FileNotFoundException fileNotFoundException) {
            return;
        }
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput((InputStream)object, StandardCharsets.UTF_8.name());
        this.readDailyItemsLocked(xmlPullParser);
        ((FileInputStream)object).close();
        return;
        catch (Throwable throwable) {
            try {
                ((FileInputStream)object).close();
                throw throwable;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            throw throwable;
        }
        catch (XmlPullParserException xmlPullParserException) {
            try {
                ((FileInputStream)object).close();
                return;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.readFromParcelLocked(parcel);
    }

    void readFromParcelLocked(Parcel object) {
        int n = ((Parcel)object).readInt();
        if (n == -1166707595) {
            Object object2;
            Object object3;
            this.readHistoryBuffer((Parcel)object, false);
            this.mBatteryStatsHistory.readFromParcel((Parcel)object);
            this.mStartCount = ((Parcel)object).readInt();
            this.mStartClockTime = ((Parcel)object).readLong();
            this.mStartPlatformVersion = ((Parcel)object).readString();
            this.mEndPlatformVersion = ((Parcel)object).readString();
            this.mUptime = ((Parcel)object).readLong();
            this.mUptimeStart = ((Parcel)object).readLong();
            this.mRealtime = ((Parcel)object).readLong();
            this.mRealtimeStart = ((Parcel)object).readLong();
            n = ((Parcel)object).readInt();
            boolean bl = true;
            boolean bl2 = n != 0;
            this.mOnBattery = bl2;
            this.mEstimatedBatteryCapacity = ((Parcel)object).readInt();
            this.mMinLearnedBatteryCapacity = ((Parcel)object).readInt();
            this.mMaxLearnedBatteryCapacity = ((Parcel)object).readInt();
            this.mOnBatteryInternal = false;
            this.mOnBatteryTimeBase.readFromParcel((Parcel)object);
            this.mOnBatteryScreenOffTimeBase.readFromParcel((Parcel)object);
            this.mScreenState = 0;
            this.mScreenOnTimer = new StopwatchTimer(this.mClocks, null, -1, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mScreenDozeTimer = new StopwatchTimer(this.mClocks, null, -1, null, this.mOnBatteryTimeBase, (Parcel)object);
            for (n = 0; n < 5; ++n) {
                this.mScreenBrightnessTimer[n] = new StopwatchTimer(this.mClocks, null, -100 - n, null, this.mOnBatteryTimeBase, (Parcel)object);
            }
            this.mInteractive = false;
            this.mInteractiveTimer = new StopwatchTimer(this.mClocks, null, -10, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mPhoneOn = false;
            this.mPowerSaveModeEnabledTimer = new StopwatchTimer(this.mClocks, null, -2, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mLongestLightIdleTime = ((Parcel)object).readLong();
            this.mLongestFullIdleTime = ((Parcel)object).readLong();
            this.mDeviceIdleModeLightTimer = new StopwatchTimer(this.mClocks, null, -14, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mDeviceIdleModeFullTimer = new StopwatchTimer(this.mClocks, null, -11, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mDeviceLightIdlingTimer = new StopwatchTimer(this.mClocks, null, -15, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mDeviceIdlingTimer = new StopwatchTimer(this.mClocks, null, -12, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mPhoneOnTimer = new StopwatchTimer(this.mClocks, null, -3, null, this.mOnBatteryTimeBase, (Parcel)object);
            for (n = 0; n < 5; ++n) {
                this.mPhoneSignalStrengthsTimer[n] = new StopwatchTimer(this.mClocks, null, -200 - n, null, this.mOnBatteryTimeBase, (Parcel)object);
            }
            this.mPhoneSignalScanningTimer = new StopwatchTimer(this.mClocks, null, -199, null, this.mOnBatteryTimeBase, (Parcel)object);
            for (n = 0; n < 22; ++n) {
                this.mPhoneDataConnectionsTimer[n] = new StopwatchTimer(this.mClocks, null, -300 - n, null, this.mOnBatteryTimeBase, (Parcel)object);
            }
            for (n = 0; n < 10; ++n) {
                this.mNetworkByteActivityCounters[n] = new LongSamplingCounter(this.mOnBatteryTimeBase, (Parcel)object);
                this.mNetworkPacketActivityCounters[n] = new LongSamplingCounter(this.mOnBatteryTimeBase, (Parcel)object);
            }
            this.mMobileRadioPowerState = 1;
            this.mMobileRadioActiveTimer = new StopwatchTimer(this.mClocks, null, -400, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mMobileRadioActivePerAppTimer = new StopwatchTimer(this.mClocks, null, -401, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mMobileRadioActiveAdjustedTime = new LongSamplingCounter(this.mOnBatteryTimeBase, (Parcel)object);
            this.mMobileRadioActiveUnknownTime = new LongSamplingCounter(this.mOnBatteryTimeBase, (Parcel)object);
            this.mMobileRadioActiveUnknownCount = new LongSamplingCounter(this.mOnBatteryTimeBase, (Parcel)object);
            this.mWifiMulticastWakelockTimer = new StopwatchTimer(this.mClocks, null, -4, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mWifiRadioPowerState = 1;
            this.mWifiOn = false;
            this.mWifiOnTimer = new StopwatchTimer(this.mClocks, null, -4, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mGlobalWifiRunning = false;
            this.mGlobalWifiRunningTimer = new StopwatchTimer(this.mClocks, null, -5, null, this.mOnBatteryTimeBase, (Parcel)object);
            for (n = 0; n < 8; ++n) {
                this.mWifiStateTimer[n] = new StopwatchTimer(this.mClocks, null, -600 - n, null, this.mOnBatteryTimeBase, (Parcel)object);
            }
            for (n = 0; n < 13; ++n) {
                this.mWifiSupplStateTimer[n] = new StopwatchTimer(this.mClocks, null, -700 - n, null, this.mOnBatteryTimeBase, (Parcel)object);
            }
            for (n = 0; n < 5; ++n) {
                this.mWifiSignalStrengthsTimer[n] = new StopwatchTimer(this.mClocks, null, -800 - n, null, this.mOnBatteryTimeBase, (Parcel)object);
            }
            this.mWifiActiveTimer = new StopwatchTimer(this.mClocks, null, -900, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mWifiActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 1, (Parcel)object);
            for (n = 0; n < 2; ++n) {
                this.mGpsSignalQualityTimer[n] = new StopwatchTimer(this.mClocks, null, -1000 - n, null, this.mOnBatteryTimeBase, (Parcel)object);
            }
            this.mBluetoothActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 1, (Parcel)object);
            this.mModemActivity = new ControllerActivityCounterImpl(this.mOnBatteryTimeBase, 5, (Parcel)object);
            bl2 = ((Parcel)object).readInt() != 0;
            this.mHasWifiReporting = bl2;
            bl2 = ((Parcel)object).readInt() != 0;
            this.mHasBluetoothReporting = bl2;
            bl2 = ((Parcel)object).readInt() != 0 ? bl : false;
            this.mHasModemReporting = bl2;
            this.mNumConnectivityChange = ((Parcel)object).readInt();
            this.mAudioOnNesting = 0;
            this.mAudioOnTimer = new StopwatchTimer(this.mClocks, null, -7, null, this.mOnBatteryTimeBase);
            this.mVideoOnNesting = 0;
            this.mVideoOnTimer = new StopwatchTimer(this.mClocks, null, -8, null, this.mOnBatteryTimeBase);
            this.mFlashlightOnNesting = 0;
            this.mFlashlightOnTimer = new StopwatchTimer(this.mClocks, null, -9, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mCameraOnNesting = 0;
            this.mCameraOnTimer = new StopwatchTimer(this.mClocks, null, -13, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mBluetoothScanNesting = 0;
            this.mBluetoothScanTimer = new StopwatchTimer(this.mClocks, null, -14, null, this.mOnBatteryTimeBase, (Parcel)object);
            this.mIsCellularTxPowerHigh = false;
            this.mDischargeUnplugLevel = ((Parcel)object).readInt();
            this.mDischargePlugLevel = ((Parcel)object).readInt();
            this.mDischargeCurrentLevel = ((Parcel)object).readInt();
            this.mCurrentBatteryLevel = ((Parcel)object).readInt();
            this.mLowDischargeAmountSinceCharge = ((Parcel)object).readInt();
            this.mHighDischargeAmountSinceCharge = ((Parcel)object).readInt();
            this.mDischargeAmountScreenOn = ((Parcel)object).readInt();
            this.mDischargeAmountScreenOnSinceCharge = ((Parcel)object).readInt();
            this.mDischargeAmountScreenOff = ((Parcel)object).readInt();
            this.mDischargeAmountScreenOffSinceCharge = ((Parcel)object).readInt();
            this.mDischargeAmountScreenDoze = ((Parcel)object).readInt();
            this.mDischargeAmountScreenDozeSinceCharge = ((Parcel)object).readInt();
            this.mDischargeStepTracker.readFromParcel((Parcel)object);
            this.mChargeStepTracker.readFromParcel((Parcel)object);
            this.mDischargeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase, (Parcel)object);
            this.mDischargeScreenOffCounter = new LongSamplingCounter(this.mOnBatteryScreenOffTimeBase, (Parcel)object);
            this.mDischargeScreenDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase, (Parcel)object);
            this.mDischargeLightDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase, (Parcel)object);
            this.mDischargeDeepDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase, (Parcel)object);
            this.mLastWriteTime = ((Parcel)object).readLong();
            this.mRpmStats.clear();
            int n2 = ((Parcel)object).readInt();
            for (n = 0; n < n2; ++n) {
                if (((Parcel)object).readInt() == 0) continue;
                object3 = ((Parcel)object).readString();
                object2 = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase, (Parcel)object);
                this.mRpmStats.put((String)object3, (SamplingTimer)object2);
            }
            this.mScreenOffRpmStats.clear();
            n2 = ((Parcel)object).readInt();
            for (n = 0; n < n2; ++n) {
                if (((Parcel)object).readInt() == 0) continue;
                object3 = ((Parcel)object).readString();
                object2 = new SamplingTimer(this.mClocks, this.mOnBatteryScreenOffTimeBase, (Parcel)object);
                this.mScreenOffRpmStats.put((String)object3, (SamplingTimer)object2);
            }
            this.mKernelWakelockStats.clear();
            n2 = ((Parcel)object).readInt();
            for (n = 0; n < n2; ++n) {
                if (((Parcel)object).readInt() == 0) continue;
                object3 = ((Parcel)object).readString();
                object2 = new SamplingTimer(this.mClocks, this.mOnBatteryScreenOffTimeBase, (Parcel)object);
                this.mKernelWakelockStats.put((String)object3, (SamplingTimer)object2);
            }
            this.mWakeupReasonStats.clear();
            n2 = ((Parcel)object).readInt();
            for (n = 0; n < n2; ++n) {
                if (((Parcel)object).readInt() == 0) continue;
                object2 = ((Parcel)object).readString();
                object3 = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase, (Parcel)object);
                this.mWakeupReasonStats.put((String)object2, (SamplingTimer)object3);
            }
            this.mKernelMemoryStats.clear();
            n2 = ((Parcel)object).readInt();
            for (n = 0; n < n2; ++n) {
                if (((Parcel)object).readInt() == 0) continue;
                long l = ((Parcel)object).readLong();
                object3 = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase, (Parcel)object);
                this.mKernelMemoryStats.put(l, (SamplingTimer)object3);
            }
            this.mPartialTimers.clear();
            this.mFullTimers.clear();
            this.mWindowTimers.clear();
            this.mWifiRunningTimers.clear();
            this.mFullWifiLockTimers.clear();
            this.mWifiScanTimers.clear();
            this.mWifiBatchedScanTimers.clear();
            this.mWifiMulticastTimers.clear();
            this.mAudioTurnedOnTimers.clear();
            this.mVideoTurnedOnTimers.clear();
            this.mFlashlightTurnedOnTimers.clear();
            this.mCameraTurnedOnTimers.clear();
            n2 = ((Parcel)object).readInt();
            this.mUidStats.clear();
            for (n = 0; n < n2; ++n) {
                int n3 = ((Parcel)object).readInt();
                object3 = new Uid(this, n3);
                ((Uid)object3).readFromParcelLocked(this.mOnBatteryTimeBase, this.mOnBatteryScreenOffTimeBase, (Parcel)object);
                this.mUidStats.append(n3, (Uid)object3);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Bad magic number: #");
        ((StringBuilder)object).append(Integer.toHexString(n));
        throw new ParcelFormatException(((StringBuilder)object).toString());
    }

    void readHistoryBuffer(Parcel object, boolean bl) throws ParcelFormatException {
        int n = ((Parcel)object).readInt();
        if (n != 186) {
            object = new StringBuilder();
            ((StringBuilder)object).append("readHistoryBuffer: version got ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", expected ");
            ((StringBuilder)object).append(186);
            ((StringBuilder)object).append("; erasing old stats");
            Slog.w("BatteryStats", ((StringBuilder)object).toString());
            return;
        }
        long l = ((Parcel)object).readLong();
        this.mHistoryBuffer.setDataSize(0);
        this.mHistoryBuffer.setDataPosition(0);
        int n2 = ((Parcel)object).readInt();
        n = ((Parcel)object).dataPosition();
        if (n2 < this.mConstants.MAX_HISTORY_BUFFER * 100) {
            if ((n2 & -4) == n2) {
                this.mHistoryBuffer.appendFrom((Parcel)object, n, n2);
                ((Parcel)object).setDataPosition(n + n2);
                if (bl) {
                    this.readOldHistory((Parcel)object);
                }
                this.mHistoryBaseTime = l;
                if (this.mHistoryBaseTime > 0L) {
                    l = this.mClocks.elapsedRealtime();
                    this.mHistoryBaseTime = this.mHistoryBaseTime - l + 1L;
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("File corrupt: history data buffer not aligned ");
            ((StringBuilder)object).append(n2);
            throw new ParcelFormatException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("File corrupt: history data buffer too large ");
        ((StringBuilder)object).append(n2);
        throw new ParcelFormatException(((StringBuilder)object).toString());
    }

    public void readHistoryDelta(Parcel parcel, BatteryStats.HistoryItem historyItem) {
        int n;
        int n2 = parcel.readInt();
        int n3 = 524287 & n2;
        historyItem.cmd = (byte)(false ? 1 : 0);
        historyItem.numReadInts = 1;
        if (n3 < 524285) {
            historyItem.time += (long)n3;
        } else {
            if (n3 == 524285) {
                historyItem.time = parcel.readLong();
                historyItem.numReadInts += 2;
                historyItem.readFromParcel(parcel);
                return;
            }
            if (n3 == 524286) {
                n3 = parcel.readInt();
                historyItem.time += (long)n3;
                ++historyItem.numReadInts;
            } else {
                long l = parcel.readLong();
                historyItem.time += l;
                historyItem.numReadInts += 2;
            }
        }
        if ((524288 & n2) != 0) {
            n3 = parcel.readInt();
            this.readBatteryLevelInt(n3, historyItem);
            ++historyItem.numReadInts;
        } else {
            n3 = 0;
        }
        if ((1048576 & n2) != 0) {
            n = parcel.readInt();
            historyItem.states = 16777215 & n | -33554432 & n2;
            historyItem.batteryStatus = (byte)(n >> 29 & 7);
            historyItem.batteryHealth = (byte)(n >> 26 & 7);
            historyItem.batteryPlugType = (byte)(n >> 24 & 3);
            n = historyItem.batteryPlugType;
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        historyItem.batteryPlugType = (byte)4;
                    }
                } else {
                    historyItem.batteryPlugType = (byte)2;
                }
            } else {
                historyItem.batteryPlugType = (byte)(true ? 1 : 0);
            }
            ++historyItem.numReadInts;
        } else {
            historyItem.states = n2 & -33554432 | historyItem.states & 16777215;
        }
        if ((2097152 & n2) != 0) {
            historyItem.states2 = parcel.readInt();
        }
        if ((4194304 & n2) != 0) {
            int n4 = parcel.readInt();
            n = n4 & 65535;
            n4 = n4 >> 16 & 65535;
            if (n != 65535) {
                historyItem.wakelockTag = historyItem.localWakelockTag;
                this.readHistoryTag(n, historyItem.wakelockTag);
            } else {
                historyItem.wakelockTag = null;
            }
            if (n4 != 65535) {
                historyItem.wakeReasonTag = historyItem.localWakeReasonTag;
                this.readHistoryTag(n4, historyItem.wakeReasonTag);
            } else {
                historyItem.wakeReasonTag = null;
            }
            ++historyItem.numReadInts;
        } else {
            historyItem.wakelockTag = null;
            historyItem.wakeReasonTag = null;
        }
        if ((8388608 & n2) != 0) {
            historyItem.eventTag = historyItem.localEventTag;
            n = parcel.readInt();
            historyItem.eventCode = n & 65535;
            this.readHistoryTag(n >> 16 & 65535, historyItem.eventTag);
            ++historyItem.numReadInts;
        } else {
            historyItem.eventCode = 0;
        }
        if ((n3 & 1) != 0) {
            historyItem.stepDetails = this.mReadHistoryStepDetails;
            historyItem.stepDetails.readFromParcel(parcel);
        } else {
            historyItem.stepDetails = null;
        }
        if ((16777216 & n2) != 0) {
            historyItem.batteryChargeUAh = parcel.readInt();
        }
        historyItem.modemRailChargeMah = parcel.readDouble();
        historyItem.wifiRailChargeMah = parcel.readDouble();
    }

    @VisibleForTesting
    public void readKernelUidCpuActiveTimesLocked(boolean bl) {
        long l = this.mClocks.uptimeMillis();
        this.mCpuUidActiveTimeReader.readDelta(new _$$Lambda$BatteryStatsImpl$_l2oiaRDRhjCXI_PwXPsAhrgegI(this, bl));
        l = this.mClocks.uptimeMillis() - l;
        if (l >= 100L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Reading cpu active times took ");
            stringBuilder.append(l);
            stringBuilder.append("ms");
            Slog.d(TAG, stringBuilder.toString());
        }
    }

    @VisibleForTesting
    public void readKernelUidCpuClusterTimesLocked(boolean bl) {
        long l = this.mClocks.uptimeMillis();
        this.mCpuUidClusterTimeReader.readDelta(new _$$Lambda$BatteryStatsImpl$Xvt9xdVPtevMWGIjcbxXf0_mr_c(this, bl));
        l = this.mClocks.uptimeMillis() - l;
        if (l >= 100L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Reading cpu cluster times took ");
            stringBuilder.append(l);
            stringBuilder.append("ms");
            Slog.d(TAG, stringBuilder.toString());
        }
    }

    @VisibleForTesting
    public void readKernelUidCpuFreqTimesLocked(ArrayList<StopwatchTimer> arrayList, boolean bl, boolean bl2) {
        Object object;
        boolean bl3 = this.mCpuUidFreqTimeReader.perClusterTimesAvailable();
        int n = arrayList == null ? 0 : arrayList.size();
        int n2 = this.mPowerProfile.getNumCpuClusters();
        this.mWakeLockAllocationsUs = null;
        long l = this.mClocks.uptimeMillis();
        this.mCpuUidFreqTimeReader.readDelta(new _$$Lambda$BatteryStatsImpl$B_TmZhQb712ePnuJTxvMe7P_YwQ(this, bl, bl2, bl3, n2, n));
        l = this.mClocks.uptimeMillis() - l;
        if (l >= 100L) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Reading cpu freq times took ");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append("ms");
            Slog.d(TAG, ((StringBuilder)object).toString());
        }
        if (this.mWakeLockAllocationsUs != null) {
            bl2 = bl3;
            for (int i = 0; i < n; ++i) {
                object = arrayList.get((int)i).mUid;
                if (((Uid)object).mCpuClusterSpeedTimesUs == null || ((Uid)object).mCpuClusterSpeedTimesUs.length != n2) {
                    BatteryStatsImpl.detachIfNotNull(((Uid)object).mCpuClusterSpeedTimesUs);
                    ((Uid)object).mCpuClusterSpeedTimesUs = new LongSamplingCounter[n2][];
                }
                for (int j = 0; j < n2; ++j) {
                    int n3 = this.mPowerProfile.getNumSpeedStepsInCpuCluster(j);
                    if (((Uid)object).mCpuClusterSpeedTimesUs[j] == null || ((Uid)object).mCpuClusterSpeedTimesUs[j].length != n3) {
                        BatteryStatsImpl.detachIfNotNull(((Uid)object).mCpuClusterSpeedTimesUs[j]);
                        object.mCpuClusterSpeedTimesUs[j] = new LongSamplingCounter[n3];
                    }
                    LongSamplingCounter[] arrlongSamplingCounter = ((Uid)object).mCpuClusterSpeedTimesUs[j];
                    for (int k = 0; k < n3; ++k) {
                        if (arrlongSamplingCounter[k] == null) {
                            arrlongSamplingCounter[k] = new LongSamplingCounter(this.mOnBatteryTimeBase);
                        }
                        long l2 = this.mWakeLockAllocationsUs[j][k] / (long)(n - i);
                        arrlongSamplingCounter[k].addCountLocked(l2, bl);
                        long[] arrl = this.mWakeLockAllocationsUs[j];
                        arrl[k] = arrl[k] - l2;
                    }
                }
            }
        }
    }

    @VisibleForTesting
    public void readKernelUidCpuTimesLocked(ArrayList<StopwatchTimer> arrayList, SparseLongArray sparseLongArray, boolean bl) {
        Object object;
        this.mTempTotalCpuSystemTimeUs = 0L;
        this.mTempTotalCpuUserTimeUs = 0L;
        int n = arrayList == null ? 0 : arrayList.size();
        long l = this.mClocks.uptimeMillis();
        this.mCpuUidUserSysTimeReader.readDelta(new _$$Lambda$BatteryStatsImpl$7bfIWpn8X2h_hSzLD6dcuK4Ljuw(this, n, bl, sparseLongArray));
        l = this.mClocks.uptimeMillis() - l;
        if (l >= 100L) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Reading cpu stats took ");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append("ms");
            Slog.d(TAG, ((StringBuilder)object).toString());
        }
        if (n > 0) {
            this.mTempTotalCpuUserTimeUs = this.mTempTotalCpuUserTimeUs * 50L / 100L;
            this.mTempTotalCpuSystemTimeUs = this.mTempTotalCpuSystemTimeUs * 50L / 100L;
            int n2 = 0;
            do {
                object = sparseLongArray;
                if (n2 >= n) break;
                StopwatchTimer stopwatchTimer = arrayList.get(n2);
                int n3 = (int)(this.mTempTotalCpuUserTimeUs / (long)(n - n2));
                int n4 = (int)(this.mTempTotalCpuSystemTimeUs / (long)(n - n2));
                stopwatchTimer.mUid.mUserCpuTime.addCountLocked(n3, bl);
                stopwatchTimer.mUid.mSystemCpuTime.addCountLocked(n4, bl);
                if (object != null) {
                    int n5 = stopwatchTimer.mUid.getUid();
                    ((SparseLongArray)object).put(n5, ((SparseLongArray)object).get(n5, 0L) + (long)n3 + (long)n4);
                }
                stopwatchTimer.mUid.getProcessStatsLocked("*wakelock*").addCpuTimeLocked(n3 / 1000, n4 / 1000, bl);
                this.mTempTotalCpuUserTimeUs -= (long)n3;
                this.mTempTotalCpuSystemTimeUs -= (long)n4;
                ++n2;
            } while (true);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void readLocked() {
        block15 : {
            block14 : {
                if (this.mDailyFile != null) {
                    this.readDailyStatsLocked();
                }
                if (this.mStatsFile == null) {
                    Slog.w("BatteryStatsImpl", "readLocked: no file associated with this instance");
                    return;
                }
                if (this.mBatteryStatsHistory.getActiveFile() == null) {
                    Slog.w("BatteryStatsImpl", "readLocked: no history file associated with this instance");
                    return;
                }
                this.mUidStats.clear();
                var1_1 = Parcel.obtain();
                try {
                    SystemClock.uptimeMillis();
                    var2_2 = this.mStatsFile.readFully();
                    var1_1.unmarshall(var2_2, 0, var2_2.length);
                    var1_1.setDataPosition(0);
                    this.readSummaryFromParcel(var1_1);
                    break block14;
                }
                catch (Throwable var2_3) {
                    ** GOTO lbl40
                }
                catch (Exception var2_4) {
                    Slog.e("BatteryStatsImpl", "Error reading battery statistics", var2_4);
                    this.resetAllStatsLocked();
                }
            }
            var1_1.recycle();
            var1_1 = Parcel.obtain();
            SystemClock.uptimeMillis();
            var2_2 = this.mBatteryStatsHistory.getActiveFile().readFully();
            if (var2_2.length > 0) {
                var1_1.unmarshall(var2_2, 0, var2_2.length);
                var1_1.setDataPosition(0);
                this.readHistoryBuffer(var1_1, true);
            }
            break block15;
lbl40: // 1 sources:
            var1_1.recycle();
            throw var2_3;
        }
        var1_1.recycle();
        this.mEndPlatformVersion = Build.ID;
        if (this.mHistoryBuffer.dataPosition() > 0 || this.mBatteryStatsHistory.getFilesNumbers().size() > 1) {
            this.mRecordingHistory = true;
            var3_7 = this.mClocks.elapsedRealtime();
            var5_8 = this.mClocks.uptimeMillis();
            this.addHistoryBufferLocked(var3_7, (byte)4, this.mHistoryCur);
            this.startRecordingHistory(var3_7, var5_8, false);
        }
        this.recordDailyStatsIfNeededLocked(false);
        return;
        {
            catch (Exception var2_6) {}
            {
                Slog.e("BatteryStatsImpl", "Error reading battery history", var2_6);
                this.clearHistoryLocked();
                this.mBatteryStatsHistory.resetAllFiles();
            }
        }
        ** finally { 
lbl59: // 1 sources:
        var1_1.recycle();
        throw var2_5;
    }

    void readOldHistory(Parcel parcel) {
    }

    public void readSummaryFromParcel(Parcel object) throws ParcelFormatException {
        int n;
        Object object2;
        boolean bl;
        Object object3;
        int n2;
        int n3;
        Object object4 = this;
        Object object5 = object;
        int n4 = ((Parcel)object).readInt();
        if (n4 != 186) {
            object = new StringBuilder();
            ((StringBuilder)object).append("readFromParcel: version got ");
            ((StringBuilder)object).append(n4);
            ((StringBuilder)object).append(", expected ");
            ((StringBuilder)object).append(186);
            ((StringBuilder)object).append("; erasing old stats");
            Slog.w("BatteryStats", ((StringBuilder)object).toString());
            return;
        }
        boolean bl2 = ((Parcel)object).readBoolean();
        if (bl2) {
            ((BatteryStatsImpl)object4).readHistoryBuffer((Parcel)object5, true);
            ((BatteryStatsImpl)object4).mBatteryStatsHistory.readFromParcel((Parcel)object5);
        }
        ((BatteryStatsImpl)object4).mHistoryTagPool.clear();
        ((BatteryStatsImpl)object4).mNextHistoryTagIdx = 0;
        ((BatteryStatsImpl)object4).mNumHistoryTagChars = 0;
        int n5 = ((Parcel)object).readInt();
        for (n2 = 0; n2 < n5; ++n2) {
            n = ((Parcel)object).readInt();
            object3 = ((Parcel)object).readString();
            if (object3 != null) {
                n3 = ((Parcel)object).readInt();
                object2 = new BatteryStats.HistoryTag();
                ((BatteryStats.HistoryTag)object2).string = object3;
                ((BatteryStats.HistoryTag)object2).uid = n3;
                ((BatteryStats.HistoryTag)object2).poolIdx = n;
                ((BatteryStatsImpl)object4).mHistoryTagPool.put((BatteryStats.HistoryTag)object2, n);
                if (n >= ((BatteryStatsImpl)object4).mNextHistoryTagIdx) {
                    ((BatteryStatsImpl)object4).mNextHistoryTagIdx = n + 1;
                }
                ((BatteryStatsImpl)object4).mNumHistoryTagChars += ((BatteryStats.HistoryTag)object2).string.length() + 1;
                continue;
            }
            throw new ParcelFormatException("null history tag string");
        }
        ((BatteryStatsImpl)object4).mStartCount = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mUptime = ((Parcel)object).readLong();
        ((BatteryStatsImpl)object4).mRealtime = ((Parcel)object).readLong();
        ((BatteryStatsImpl)object4).mStartClockTime = ((Parcel)object).readLong();
        ((BatteryStatsImpl)object4).mStartPlatformVersion = ((Parcel)object).readString();
        ((BatteryStatsImpl)object4).mEndPlatformVersion = ((Parcel)object).readString();
        ((BatteryStatsImpl)object4).mOnBatteryTimeBase.readSummaryFromParcel((Parcel)object5);
        ((BatteryStatsImpl)object4).mOnBatteryScreenOffTimeBase.readSummaryFromParcel((Parcel)object5);
        ((BatteryStatsImpl)object4).mDischargeUnplugLevel = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mDischargePlugLevel = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mDischargeCurrentLevel = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mCurrentBatteryLevel = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mEstimatedBatteryCapacity = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mMinLearnedBatteryCapacity = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mMaxLearnedBatteryCapacity = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mLowDischargeAmountSinceCharge = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mHighDischargeAmountSinceCharge = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mDischargeAmountScreenOnSinceCharge = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mDischargeAmountScreenOffSinceCharge = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mDischargeAmountScreenDozeSinceCharge = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mDischargeStepTracker.readFromParcel((Parcel)object5);
        ((BatteryStatsImpl)object4).mChargeStepTracker.readFromParcel((Parcel)object5);
        ((BatteryStatsImpl)object4).mDailyDischargeStepTracker.readFromParcel((Parcel)object5);
        ((BatteryStatsImpl)object4).mDailyChargeStepTracker.readFromParcel((Parcel)object5);
        ((BatteryStatsImpl)object4).mDischargeCounter.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mDischargeScreenOffCounter.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mDischargeScreenDozeCounter.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mDischargeLightDozeCounter.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mDischargeDeepDozeCounter.readSummaryFromParcelLocked((Parcel)object5);
        n2 = ((Parcel)object).readInt();
        if (n2 > 0) {
            ((BatteryStatsImpl)object4).mDailyPackageChanges = new ArrayList(n2);
            n = n2;
            do {
                n2 = n;
                if (n > 0) {
                    --n;
                    object2 = new BatteryStats.PackageChange();
                    ((BatteryStats.PackageChange)object2).mPackageName = ((Parcel)object).readString();
                    bl = ((Parcel)object).readInt() != 0;
                    ((BatteryStats.PackageChange)object2).mUpdate = bl;
                    ((BatteryStats.PackageChange)object2).mVersionCode = ((Parcel)object).readLong();
                    ((BatteryStatsImpl)object4).mDailyPackageChanges.add((BatteryStats.PackageChange)object2);
                    continue;
                }
                break;
            } while (true);
        } else {
            ((BatteryStatsImpl)object4).mDailyPackageChanges = null;
        }
        ((BatteryStatsImpl)object4).mDailyStartTime = ((Parcel)object).readLong();
        ((BatteryStatsImpl)object4).mNextMinDailyDeadline = ((Parcel)object).readLong();
        ((BatteryStatsImpl)object4).mNextMaxDailyDeadline = ((Parcel)object).readLong();
        ++((BatteryStatsImpl)object4).mStartCount;
        ((BatteryStatsImpl)object4).mScreenState = 0;
        ((BatteryStatsImpl)object4).mScreenOnTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mScreenDozeTimer.readSummaryFromParcelLocked((Parcel)object5);
        for (n = 0; n < 5; ++n) {
            ((BatteryStatsImpl)object4).mScreenBrightnessTimer[n].readSummaryFromParcelLocked((Parcel)object5);
        }
        ((BatteryStatsImpl)object4).mInteractive = false;
        ((BatteryStatsImpl)object4).mInteractiveTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mPhoneOn = false;
        ((BatteryStatsImpl)object4).mPowerSaveModeEnabledTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mLongestLightIdleTime = ((Parcel)object).readLong();
        ((BatteryStatsImpl)object4).mLongestFullIdleTime = ((Parcel)object).readLong();
        ((BatteryStatsImpl)object4).mDeviceIdleModeLightTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mDeviceIdleModeFullTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mDeviceLightIdlingTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mDeviceIdlingTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mPhoneOnTimer.readSummaryFromParcelLocked((Parcel)object5);
        for (n = 0; n < 5; ++n) {
            ((BatteryStatsImpl)object4).mPhoneSignalStrengthsTimer[n].readSummaryFromParcelLocked((Parcel)object5);
        }
        ((BatteryStatsImpl)object4).mPhoneSignalScanningTimer.readSummaryFromParcelLocked((Parcel)object5);
        for (n = 0; n < 22; ++n) {
            ((BatteryStatsImpl)object4).mPhoneDataConnectionsTimer[n].readSummaryFromParcelLocked((Parcel)object5);
        }
        for (n = 0; n < 10; ++n) {
            ((BatteryStatsImpl)object4).mNetworkByteActivityCounters[n].readSummaryFromParcelLocked((Parcel)object5);
            ((BatteryStatsImpl)object4).mNetworkPacketActivityCounters[n].readSummaryFromParcelLocked((Parcel)object5);
        }
        ((BatteryStatsImpl)object4).mMobileRadioPowerState = 1;
        ((BatteryStatsImpl)object4).mMobileRadioActiveTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mMobileRadioActivePerAppTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mMobileRadioActiveAdjustedTime.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mMobileRadioActiveUnknownTime.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mMobileRadioActiveUnknownCount.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mWifiMulticastWakelockTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mWifiRadioPowerState = 1;
        ((BatteryStatsImpl)object4).mWifiOn = false;
        ((BatteryStatsImpl)object4).mWifiOnTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mGlobalWifiRunning = false;
        ((BatteryStatsImpl)object4).mGlobalWifiRunningTimer.readSummaryFromParcelLocked((Parcel)object5);
        for (n = 0; n < 8; ++n) {
            ((BatteryStatsImpl)object4).mWifiStateTimer[n].readSummaryFromParcelLocked((Parcel)object5);
        }
        for (n = 0; n < 13; ++n) {
            ((BatteryStatsImpl)object4).mWifiSupplStateTimer[n].readSummaryFromParcelLocked((Parcel)object5);
        }
        for (n = 0; n < 5; ++n) {
            ((BatteryStatsImpl)object4).mWifiSignalStrengthsTimer[n].readSummaryFromParcelLocked((Parcel)object5);
        }
        ((BatteryStatsImpl)object4).mWifiActiveTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mWifiActivity.readSummaryFromParcel((Parcel)object5);
        for (n = 0; n < 2; ++n) {
            ((BatteryStatsImpl)object4).mGpsSignalQualityTimer[n].readSummaryFromParcelLocked((Parcel)object5);
        }
        ((BatteryStatsImpl)object4).mBluetoothActivity.readSummaryFromParcel((Parcel)object5);
        ((BatteryStatsImpl)object4).mModemActivity.readSummaryFromParcel((Parcel)object5);
        bl = ((Parcel)object).readInt() != 0;
        ((BatteryStatsImpl)object4).mHasWifiReporting = bl;
        bl = ((Parcel)object).readInt() != 0;
        ((BatteryStatsImpl)object4).mHasBluetoothReporting = bl;
        bl = ((Parcel)object).readInt() != 0;
        ((BatteryStatsImpl)object4).mHasModemReporting = bl;
        ((BatteryStatsImpl)object4).mNumConnectivityChange = ((Parcel)object).readInt();
        ((BatteryStatsImpl)object4).mFlashlightOnNesting = 0;
        ((BatteryStatsImpl)object4).mFlashlightOnTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mCameraOnNesting = 0;
        ((BatteryStatsImpl)object4).mCameraOnTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mBluetoothScanNesting = 0;
        ((BatteryStatsImpl)object4).mBluetoothScanTimer.readSummaryFromParcelLocked((Parcel)object5);
        ((BatteryStatsImpl)object4).mIsCellularTxPowerHigh = false;
        int n6 = ((Parcel)object).readInt();
        if (n6 <= 10000) {
            for (n = 0; n < n6; ++n) {
                if (((Parcel)object).readInt() == 0) continue;
                ((BatteryStatsImpl)object4).getRpmTimerLocked(((Parcel)object).readString()).readSummaryFromParcelLocked((Parcel)object5);
            }
            int n7 = ((Parcel)object).readInt();
            if (n7 <= 10000) {
                for (n = 0; n < n7; ++n) {
                    if (((Parcel)object).readInt() == 0) continue;
                    ((BatteryStatsImpl)object4).getScreenOffRpmTimerLocked(((Parcel)object).readString()).readSummaryFromParcelLocked((Parcel)object5);
                }
                int n8 = ((Parcel)object).readInt();
                if (n8 <= 10000) {
                    for (n = 0; n < n8; ++n) {
                        if (((Parcel)object).readInt() == 0) continue;
                        ((BatteryStatsImpl)object4).getKernelWakelockTimerLocked(((Parcel)object).readString()).readSummaryFromParcelLocked((Parcel)object5);
                    }
                    n3 = ((Parcel)object).readInt();
                    if (n3 <= 10000) {
                        for (n = 0; n < n3; ++n) {
                            if (((Parcel)object).readInt() == 0) continue;
                            ((BatteryStatsImpl)object4).getWakeupReasonTimerLocked(((Parcel)object).readString()).readSummaryFromParcelLocked((Parcel)object5);
                        }
                        int n9 = ((Parcel)object).readInt();
                        for (n = 0; n < n9; ++n) {
                            if (((Parcel)object).readInt() == 0) continue;
                            ((BatteryStatsImpl)object4).getKernelMemoryTimerLocked(((Parcel)object).readLong()).readSummaryFromParcelLocked((Parcel)object5);
                        }
                        int n10 = ((Parcel)object).readInt();
                        if (n10 <= 10000) {
                            block80 : {
                                block81 : {
                                    int n11;
                                    block82 : {
                                        block83 : {
                                            int n12;
                                            block84 : {
                                                block85 : {
                                                    int n13;
                                                    int n14 = 0;
                                                    n = n5;
                                                    n3 = n8;
                                                    bl = bl2;
                                                    do {
                                                        object4 = object;
                                                        object5 = this;
                                                        if (n14 >= n10) break block80;
                                                        n13 = ((Parcel)object).readInt();
                                                        object2 = new Uid((BatteryStatsImpl)object5, n13);
                                                        ((BatteryStatsImpl)object5).mUidStats.put(n13, (Uid)object2);
                                                        ((Uid)object2).mOnBatteryBackgroundTimeBase.readSummaryFromParcel((Parcel)object4);
                                                        ((Uid)object2).mOnBatteryScreenOffBackgroundTimeBase.readSummaryFromParcel((Parcel)object4);
                                                        ((Uid)object2).mWifiRunning = false;
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).mWifiRunningTimer.readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        ((Uid)object2).mFullWifiLockOut = false;
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).mFullWifiLockTimer.readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        ((Uid)object2).mWifiScanStarted = false;
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).mWifiScanTimer.readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        ((Uid)object2).mWifiBatchedScanBinStarted = -1;
                                                        for (n5 = 0; n5 < 5; ++n5) {
                                                            if (((Parcel)object).readInt() == 0) continue;
                                                            ((Uid)object2).makeWifiBatchedScanBin(n5, null);
                                                            ((Uid)object2).mWifiBatchedScanTimer[n5].readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        ((Uid)object2).mWifiMulticastWakelockCount = 0;
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).mWifiMulticastTimer.readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createAudioTurnedOnTimerLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createVideoTurnedOnTimerLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createFlashlightTurnedOnTimerLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createCameraTurnedOnTimerLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createForegroundActivityTimerLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createForegroundServiceTimerLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createAggregatedPartialWakelockTimerLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createBluetoothScanTimerLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createBluetoothUnoptimizedScanTimerLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createBluetoothScanResultCounterLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createBluetoothScanResultBgCounterLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        ((Uid)object2).mProcessState = 21;
                                                        for (n5 = 0; n5 < 7; ++n5) {
                                                            if (((Parcel)object).readInt() == 0) continue;
                                                            ((Uid)object2).makeProcessState(n5, null);
                                                            ((Uid)object2).mProcessStateTimer[n5].readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            ((Uid)object2).createVibratorOnTimerLocked().readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            if (((Uid)object2).mUserActivityCounters == null) {
                                                                ((Uid)object2).initUserActivityLocked();
                                                            }
                                                            for (n5 = 0; n5 < Uid.NUM_USER_ACTIVITY_TYPES; ++n5) {
                                                                ((Uid)object2).mUserActivityCounters[n5].readSummaryFromParcelLocked((Parcel)object4);
                                                            }
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            if (((Uid)object2).mNetworkByteActivityCounters == null) {
                                                                ((Uid)object2).initNetworkActivityLocked();
                                                            }
                                                            for (n5 = 0; n5 < 10; ++n5) {
                                                                ((Uid)object2).mNetworkByteActivityCounters[n5].readSummaryFromParcelLocked((Parcel)object4);
                                                                ((Uid)object2).mNetworkPacketActivityCounters[n5].readSummaryFromParcelLocked((Parcel)object4);
                                                            }
                                                            ((Uid)object2).mMobileRadioActiveTime.readSummaryFromParcelLocked((Parcel)object4);
                                                            ((Uid)object2).mMobileRadioActiveCount.readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        ((Uid)object2).mUserCpuTime.readSummaryFromParcelLocked((Parcel)object4);
                                                        ((Uid)object2).mSystemCpuTime.readSummaryFromParcelLocked((Parcel)object4);
                                                        if (((Parcel)object).readInt() != 0) {
                                                            n5 = ((Parcel)object).readInt();
                                                            object3 = ((BatteryStatsImpl)object5).mPowerProfile;
                                                            if (object3 != null && ((PowerProfile)object3).getNumCpuClusters() != n5) {
                                                                throw new ParcelFormatException("Incompatible cpu cluster arrangement");
                                                            }
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mCpuClusterSpeedTimesUs);
                                                            ((Uid)object2).mCpuClusterSpeedTimesUs = new LongSamplingCounter[n5][];
                                                            for (n8 = 0; n8 < n5; ++n8) {
                                                                if (((Parcel)object).readInt() != 0) {
                                                                    n12 = ((Parcel)object).readInt();
                                                                    object3 = ((BatteryStatsImpl)object5).mPowerProfile;
                                                                    if (object3 != null && ((PowerProfile)object3).getNumSpeedStepsInCpuCluster(n8) != n12) {
                                                                        object = new StringBuilder();
                                                                        ((StringBuilder)object).append("File corrupt: too many speed bins ");
                                                                        ((StringBuilder)object).append(n12);
                                                                        throw new ParcelFormatException(((StringBuilder)object).toString());
                                                                    }
                                                                    object2.mCpuClusterSpeedTimesUs[n8] = new LongSamplingCounter[n12];
                                                                    for (n11 = 0; n11 < n12; ++n11) {
                                                                        if (((Parcel)object).readInt() == 0) continue;
                                                                        object2.mCpuClusterSpeedTimesUs[n8][n11] = new LongSamplingCounter(((BatteryStatsImpl)object5).mOnBatteryTimeBase);
                                                                        ((Uid)object2).mCpuClusterSpeedTimesUs[n8][n11].readSummaryFromParcelLocked((Parcel)object4);
                                                                    }
                                                                    continue;
                                                                }
                                                                object2.mCpuClusterSpeedTimesUs[n8] = null;
                                                            }
                                                        } else {
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mCpuClusterSpeedTimesUs);
                                                            ((Uid)object2).mCpuClusterSpeedTimesUs = null;
                                                        }
                                                        BatteryStatsImpl.detachIfNotNull(((Uid)object2).mCpuFreqTimeMs);
                                                        ((Uid)object2).mCpuFreqTimeMs = LongSamplingCounterArray.readSummaryFromParcelLocked((Parcel)object4, ((BatteryStatsImpl)object5).mOnBatteryTimeBase);
                                                        BatteryStatsImpl.detachIfNotNull(((Uid)object2).mScreenOffCpuFreqTimeMs);
                                                        ((Uid)object2).mScreenOffCpuFreqTimeMs = LongSamplingCounterArray.readSummaryFromParcelLocked((Parcel)object4, ((BatteryStatsImpl)object5).mOnBatteryScreenOffTimeBase);
                                                        ((Uid)object2).mCpuActiveTimeMs.readSummaryFromParcelLocked((Parcel)object4);
                                                        ((Uid)object2).mCpuClusterTimesMs.readSummaryFromParcelLocked((Parcel)object4);
                                                        n8 = ((Parcel)object).readInt();
                                                        if (n8 == 7) {
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mProcStateTimeMs);
                                                            ((Uid)object2).mProcStateTimeMs = new LongSamplingCounterArray[n8];
                                                            for (n5 = 0; n5 < n8; ++n5) {
                                                                object2.mProcStateTimeMs[n5] = LongSamplingCounterArray.readSummaryFromParcelLocked((Parcel)object4, ((BatteryStatsImpl)object5).mOnBatteryTimeBase);
                                                            }
                                                        } else {
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mProcStateTimeMs);
                                                            ((Uid)object2).mProcStateTimeMs = null;
                                                        }
                                                        n12 = ((Parcel)object).readInt();
                                                        if (n12 == 7) {
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mProcStateScreenOffTimeMs);
                                                            ((Uid)object2).mProcStateScreenOffTimeMs = new LongSamplingCounterArray[n12];
                                                            for (n5 = 0; n5 < n12; ++n5) {
                                                                object2.mProcStateScreenOffTimeMs[n5] = LongSamplingCounterArray.readSummaryFromParcelLocked((Parcel)object4, ((BatteryStatsImpl)object5).mOnBatteryScreenOffTimeBase);
                                                            }
                                                        } else {
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mProcStateScreenOffTimeMs);
                                                            ((Uid)object2).mProcStateScreenOffTimeMs = null;
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mMobileRadioApWakeupCount);
                                                            ((Uid)object2).mMobileRadioApWakeupCount = new LongSamplingCounter(((BatteryStatsImpl)object5).mOnBatteryTimeBase);
                                                            ((Uid)object2).mMobileRadioApWakeupCount.readSummaryFromParcelLocked((Parcel)object4);
                                                        } else {
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mMobileRadioApWakeupCount);
                                                            ((Uid)object2).mMobileRadioApWakeupCount = null;
                                                        }
                                                        if (((Parcel)object).readInt() != 0) {
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mWifiRadioApWakeupCount);
                                                            ((Uid)object2).mWifiRadioApWakeupCount = new LongSamplingCounter(((BatteryStatsImpl)object5).mOnBatteryTimeBase);
                                                            ((Uid)object2).mWifiRadioApWakeupCount.readSummaryFromParcelLocked((Parcel)object4);
                                                        } else {
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mWifiRadioApWakeupCount);
                                                            ((Uid)object2).mWifiRadioApWakeupCount = null;
                                                        }
                                                        n8 = ((Parcel)object).readInt();
                                                        if (n8 > MAX_WAKELOCKS_PER_UID + 1) break block81;
                                                        for (n5 = 0; n5 < n8; ++n5) {
                                                            ((Uid)object2).readWakeSummaryFromParcelLocked(((Parcel)object).readString(), (Parcel)object4);
                                                        }
                                                        n11 = ((Parcel)object).readInt();
                                                        if (n11 > MAX_WAKELOCKS_PER_UID + 1) break block82;
                                                        for (n5 = 0; n5 < n11; ++n5) {
                                                            ((Uid)object2).readSyncSummaryFromParcelLocked(((Parcel)object).readString(), (Parcel)object4);
                                                        }
                                                        n8 = ((Parcel)object).readInt();
                                                        if (n8 > MAX_WAKELOCKS_PER_UID + 1) break block83;
                                                        for (n5 = 0; n5 < n8; ++n5) {
                                                            ((Uid)object2).readJobSummaryFromParcelLocked(((Parcel)object).readString(), (Parcel)object4);
                                                        }
                                                        ((Uid)object2).readJobCompletionsFromParcelLocked((Parcel)object4);
                                                        ((Uid)object2).mJobsDeferredEventCount.readSummaryFromParcelLocked((Parcel)object4);
                                                        ((Uid)object2).mJobsDeferredCount.readSummaryFromParcelLocked((Parcel)object4);
                                                        ((Uid)object2).mJobsFreshnessTimeMs.readSummaryFromParcelLocked((Parcel)object4);
                                                        BatteryStatsImpl.detachIfNotNull(((Uid)object2).mJobsFreshnessBuckets);
                                                        n5 = n12;
                                                        for (n8 = 0; n8 < JOB_FRESHNESS_BUCKETS.length; ++n8) {
                                                            if (((Parcel)object).readInt() == 0) continue;
                                                            object2.mJobsFreshnessBuckets[n8] = new Counter(object2.mBsi.mOnBatteryTimeBase);
                                                            ((Uid)object2).mJobsFreshnessBuckets[n8].readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        n12 = ((Parcel)object).readInt();
                                                        if (n12 > 1000) break block84;
                                                        n5 = n13;
                                                        for (n8 = 0; n8 < n12; ++n8) {
                                                            n13 = ((Parcel)object).readInt();
                                                            if (((Parcel)object).readInt() == 0) continue;
                                                            ((Uid)object2).getSensorTimerLocked(n13, true).readSummaryFromParcelLocked((Parcel)object4);
                                                        }
                                                        n8 = ((Parcel)object).readInt();
                                                        if (n8 > 1000) break block85;
                                                        for (n5 = 0; n5 < n8; ++n5) {
                                                            object5 = ((Uid)object2).getProcessStatsLocked(((Parcel)object).readString());
                                                            ((Uid.Proc)object5).mUserTime = ((Parcel)object).readLong();
                                                            ((Uid.Proc)object5).mSystemTime = ((Parcel)object).readLong();
                                                            ((Uid.Proc)object5).mForegroundTime = ((Parcel)object).readLong();
                                                            ((Uid.Proc)object5).mStarts = ((Parcel)object).readInt();
                                                            ((Uid.Proc)object5).mNumCrashes = ((Parcel)object).readInt();
                                                            ((Uid.Proc)object5).mNumAnrs = ((Parcel)object).readInt();
                                                            ((Uid.Proc)object5).readExcessivePowerFromParcelLocked((Parcel)object4);
                                                        }
                                                        n13 = ((Parcel)object).readInt();
                                                        if (n13 > 10000) break;
                                                        n5 = n11;
                                                        for (n8 = 0; n8 < n13; ++n8) {
                                                            object4 = ((Parcel)object).readString();
                                                            BatteryStatsImpl.detachIfNotNull(((Uid)object2).mPackageStats.get(object4));
                                                            Uid.Pkg pkg = ((Uid)object2).getPackageStatsLocked((String)object4);
                                                            n12 = ((Parcel)object).readInt();
                                                            if (n12 <= 10000) {
                                                                pkg.mWakeupAlarms.clear();
                                                                for (n11 = 0; n11 < n12; ++n11) {
                                                                    object3 = ((Parcel)object).readString();
                                                                    object5 = new Counter(this.mOnBatteryScreenOffTimeBase);
                                                                    ((Counter)object5).readSummaryFromParcelLocked((Parcel)object);
                                                                    pkg.mWakeupAlarms.put((String)object3, (Counter)object5);
                                                                }
                                                                n11 = ((Parcel)object).readInt();
                                                                if (n11 <= 10000) {
                                                                    for (n5 = 0; n5 < n11; ++n5) {
                                                                        object5 = ((Uid)object2).getServiceStatsLocked((String)object4, ((Parcel)object).readString());
                                                                        ((Uid.Pkg.Serv)object5).mStartTime = ((Parcel)object).readLong();
                                                                        ((Uid.Pkg.Serv)object5).mStarts = ((Parcel)object).readInt();
                                                                        ((Uid.Pkg.Serv)object5).mLaunches = ((Parcel)object).readInt();
                                                                    }
                                                                    n5 = n11;
                                                                    continue;
                                                                }
                                                                object = new StringBuilder();
                                                                ((StringBuilder)object).append("File corrupt: too many services ");
                                                                ((StringBuilder)object).append(n11);
                                                                throw new ParcelFormatException(((StringBuilder)object).toString());
                                                            }
                                                            object = new StringBuilder();
                                                            ((StringBuilder)object).append("File corrupt: too many wakeup alarms ");
                                                            ((StringBuilder)object).append(n12);
                                                            throw new ParcelFormatException(((StringBuilder)object).toString());
                                                        }
                                                        ++n14;
                                                    } while (true);
                                                    object = new StringBuilder();
                                                    ((StringBuilder)object).append("File corrupt: too many packages ");
                                                    ((StringBuilder)object).append(n13);
                                                    throw new ParcelFormatException(((StringBuilder)object).toString());
                                                }
                                                object = new StringBuilder();
                                                ((StringBuilder)object).append("File corrupt: too many processes ");
                                                ((StringBuilder)object).append(n8);
                                                throw new ParcelFormatException(((StringBuilder)object).toString());
                                            }
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("File corrupt: too many sensors ");
                                            ((StringBuilder)object).append(n12);
                                            throw new ParcelFormatException(((StringBuilder)object).toString());
                                        }
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("File corrupt: too many job timers ");
                                        ((StringBuilder)object).append(n8);
                                        throw new ParcelFormatException(((StringBuilder)object).toString());
                                    }
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("File corrupt: too many syncs ");
                                    ((StringBuilder)object).append(n11);
                                    throw new ParcelFormatException(((StringBuilder)object).toString());
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("File corrupt: too many wake locks ");
                                ((StringBuilder)object).append(n8);
                                throw new ParcelFormatException(((StringBuilder)object).toString());
                            }
                            return;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("File corrupt: too many uids ");
                        ((StringBuilder)object).append(n10);
                        throw new ParcelFormatException(((StringBuilder)object).toString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("File corrupt: too many wakeup reasons ");
                    ((StringBuilder)object).append(n3);
                    throw new ParcelFormatException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("File corrupt: too many kernel wake locks ");
                ((StringBuilder)object).append(n8);
                throw new ParcelFormatException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("File corrupt: too many screen-off rpm stats ");
            ((StringBuilder)object).append(n7);
            throw new ParcelFormatException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("File corrupt: too many rpm stats ");
        ((StringBuilder)object).append(n6);
        throw new ParcelFormatException(((StringBuilder)object).toString());
    }

    public void recordDailyStatsIfNeededLocked(boolean bl) {
        long l = System.currentTimeMillis();
        if (l >= this.mNextMaxDailyDeadline) {
            this.recordDailyStatsLocked();
        } else if (bl && l >= this.mNextMinDailyDeadline) {
            this.recordDailyStatsLocked();
        } else if (l < this.mDailyStartTime - 86400000L) {
            this.recordDailyStatsLocked();
        }
    }

    public void recordDailyStatsLocked() {
        Object object;
        Object object2 = new BatteryStats.DailyItem();
        ((BatteryStats.DailyItem)object2).mStartTime = this.mDailyStartTime;
        ((BatteryStats.DailyItem)object2).mEndTime = System.currentTimeMillis();
        boolean bl = false;
        if (this.mDailyDischargeStepTracker.mNumStepDurations > 0) {
            bl = true;
            ((BatteryStats.DailyItem)object2).mDischargeSteps = new BatteryStats.LevelStepTracker(this.mDailyDischargeStepTracker.mNumStepDurations, this.mDailyDischargeStepTracker.mStepDurations);
        }
        if (this.mDailyChargeStepTracker.mNumStepDurations > 0) {
            bl = true;
            ((BatteryStats.DailyItem)object2).mChargeSteps = new BatteryStats.LevelStepTracker(this.mDailyChargeStepTracker.mNumStepDurations, this.mDailyChargeStepTracker.mStepDurations);
        }
        if ((object = this.mDailyPackageChanges) != null) {
            bl = true;
            ((BatteryStats.DailyItem)object2).mPackageChanges = object;
            this.mDailyPackageChanges = null;
        }
        this.mDailyDischargeStepTracker.init();
        this.mDailyChargeStepTracker.init();
        this.updateDailyDeadlineLocked();
        if (bl) {
            long l = SystemClock.uptimeMillis();
            this.mDailyItems.add((BatteryStats.DailyItem)object2);
            while (this.mDailyItems.size() > 10) {
                this.mDailyItems.remove(0);
            }
            object = new ByteArrayOutputStream();
            try {
                object2 = new FastXmlSerializer();
                object2.setOutput((OutputStream)object, StandardCharsets.UTF_8.name());
                this.writeDailyItemsLocked((XmlSerializer)object2);
                long l2 = SystemClock.uptimeMillis();
                object2 = BackgroundThread.getHandler();
                Runnable runnable = new Runnable((ByteArrayOutputStream)object, l2 - l){
                    final /* synthetic */ long val$initialTime;
                    final /* synthetic */ ByteArrayOutputStream val$memStream;
                    {
                        this.val$memStream = byteArrayOutputStream;
                        this.val$initialTime = l;
                    }

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        AtomicFile atomicFile = BatteryStatsImpl.this.mCheckinFile;
                        synchronized (atomicFile) {
                            long l = SystemClock.uptimeMillis();
                            FileOutputStream fileOutputStream = null;
                            try {
                                FileOutputStream fileOutputStream2;
                                fileOutputStream = fileOutputStream2 = BatteryStatsImpl.this.mDailyFile.startWrite();
                                this.val$memStream.writeTo(fileOutputStream2);
                                fileOutputStream = fileOutputStream2;
                                fileOutputStream2.flush();
                                fileOutputStream = fileOutputStream2;
                                BatteryStatsImpl.this.mDailyFile.finishWrite(fileOutputStream2);
                                fileOutputStream = fileOutputStream2;
                                EventLogTags.writeCommitSysConfigFile("batterystats-daily", this.val$initialTime + SystemClock.uptimeMillis() - l);
                            }
                            catch (IOException iOException) {
                                Slog.w("BatteryStats", "Error writing battery daily items", iOException);
                                BatteryStatsImpl.this.mDailyFile.failWrite(fileOutputStream);
                            }
                            return;
                        }
                    }
                };
                ((Handler)object2).post(runnable);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    @GuardedBy(value={"this"})
    public void removeIsolatedUidLocked(int n) {
        int n2 = this.mIsolatedUids.indexOfKey(n);
        if (n2 >= 0) {
            this.getUidStatsLocked(this.mIsolatedUids.valueAt(n2)).removeIsolatedUid(n);
            this.mIsolatedUids.removeAt(n2);
        }
        this.mPendingRemovedUids.add(new UidToRemove(n, this.mClocks.elapsedRealtime()));
    }

    @UnsupportedAppUsage
    public void removeUidStatsLocked(int n) {
        Uid uid = this.mUidStats.get(n);
        if (uid != null) {
            uid.detachFromTimeBase();
        }
        this.mUidStats.remove(n);
        this.mPendingRemovedUids.add(new UidToRemove(n, this.mClocks.elapsedRealtime()));
    }

    public void reportExcessiveCpuLocked(int n, String string2, long l, long l2) {
        Uid uid = this.mUidStats.get(n = this.mapUid(n));
        if (uid != null) {
            uid.reportExcessiveCpuLocked(string2, l, l2);
        }
    }

    public void resetAllStatsCmdLocked() {
        this.resetAllStatsLocked();
        long l = this.mClocks.uptimeMillis();
        long l2 = l * 1000L;
        long l3 = this.mClocks.elapsedRealtime();
        long l4 = 1000L * l3;
        this.mDischargeStartLevel = this.mHistoryCur.batteryLevel;
        this.pullPendingStateUpdatesLocked();
        this.addHistoryRecordLocked(l3, l);
        byte by = this.mHistoryCur.batteryLevel;
        this.mCurrentBatteryLevel = by;
        this.mDischargePlugLevel = by;
        this.mDischargeUnplugLevel = by;
        this.mDischargeCurrentLevel = by;
        this.mOnBatteryTimeBase.reset(l2, l4);
        this.mOnBatteryScreenOffTimeBase.reset(l2, l4);
        if ((this.mHistoryCur.states & 524288) == 0) {
            if (this.isScreenOn(this.mScreenState)) {
                this.mDischargeScreenOnUnplugLevel = this.mHistoryCur.batteryLevel;
                this.mDischargeScreenDozeUnplugLevel = 0;
                this.mDischargeScreenOffUnplugLevel = 0;
            } else if (this.isScreenDoze(this.mScreenState)) {
                this.mDischargeScreenOnUnplugLevel = 0;
                this.mDischargeScreenDozeUnplugLevel = this.mHistoryCur.batteryLevel;
                this.mDischargeScreenOffUnplugLevel = 0;
            } else {
                this.mDischargeScreenOnUnplugLevel = 0;
                this.mDischargeScreenDozeUnplugLevel = 0;
                this.mDischargeScreenOffUnplugLevel = this.mHistoryCur.batteryLevel;
            }
            this.mDischargeAmountScreenOn = 0;
            this.mDischargeAmountScreenOff = 0;
            this.mDischargeAmountScreenDoze = 0;
        }
        this.initActiveHistoryEventsLocked(l3, l);
    }

    public void scheduleRemoveIsolatedUidLocked(int n, int n2) {
        ExternalStatsSync externalStatsSync;
        if (this.mIsolatedUids.get(n, -1) == n2 && (externalStatsSync = this.mExternalSync) != null) {
            externalStatsSync.scheduleCpuSyncDueToRemovedUid(n);
        }
    }

    @GuardedBy(value={"this"})
    public void setBatteryStateLocked(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        boolean bl;
        block48 : {
            long l;
            long l2;
            long l3;
            block50 : {
                block49 : {
                    int n9;
                    block47 : {
                        n9 = Math.max(0, n5);
                        BatteryStats.HistoryItem historyItem = this.mHaveBatteryLevel ? this.mHistoryCur : null;
                        this.reportChangesToStatsLog(historyItem, n, n3, n4);
                        bl = BatteryStatsImpl.isOnBattery(n3, n);
                        l = this.mClocks.uptimeMillis();
                        l2 = this.mClocks.elapsedRealtime();
                        if (!this.mHaveBatteryLevel) {
                            this.mHaveBatteryLevel = true;
                            if (bl == this.mOnBattery) {
                                if (bl) {
                                    historyItem = this.mHistoryCur;
                                    historyItem.states &= -524289;
                                } else {
                                    historyItem = this.mHistoryCur;
                                    historyItem.states |= 524288;
                                }
                            }
                            historyItem = this.mHistoryCur;
                            historyItem.states2 |= 16777216;
                            historyItem = this.mHistoryCur;
                            historyItem.batteryStatus = (byte)n;
                            historyItem.batteryLevel = (byte)n4;
                            historyItem.batteryChargeUAh = n7;
                            this.mLastDischargeStepLevel = n4;
                            this.mLastChargeStepLevel = n4;
                            this.mMinDischargeStepLevel = n4;
                            this.mMaxChargeStepLevel = n4;
                            this.mLastChargingStateLevel = n4;
                        } else if (this.mCurrentBatteryLevel != n4 || this.mOnBattery != bl) {
                            boolean bl2 = n4 >= 100 && bl;
                            this.recordDailyStatsIfNeededLocked(bl2);
                        }
                        n5 = this.mHistoryCur.batteryStatus;
                        if (bl) {
                            this.mDischargeCurrentLevel = n4;
                            if (!this.mRecordingHistory) {
                                this.mRecordingHistory = true;
                                this.startRecordingHistory(l2, l, true);
                            }
                        } else if (n4 < 96 && n != 1 && !this.mRecordingHistory) {
                            this.mRecordingHistory = true;
                            this.startRecordingHistory(l2, l, true);
                        }
                        this.mCurrentBatteryLevel = n4;
                        if (this.mDischargePlugLevel < 0) {
                            this.mDischargePlugLevel = n4;
                        }
                        if (bl == this.mOnBattery) break block47;
                        historyItem = this.mHistoryCur;
                        historyItem.batteryLevel = (byte)n4;
                        historyItem.batteryStatus = (byte)n;
                        historyItem.batteryHealth = (byte)n2;
                        historyItem.batteryPlugType = (byte)n3;
                        historyItem.batteryTemperature = (short)n9;
                        historyItem.batteryVoltage = (char)n6;
                        if (n7 < historyItem.batteryChargeUAh) {
                            long l4 = this.mHistoryCur.batteryChargeUAh - n7;
                            this.mDischargeCounter.addCountLocked(l4);
                            this.mDischargeScreenOffCounter.addCountLocked(l4);
                            if (this.isScreenDoze(this.mScreenState)) {
                                this.mDischargeScreenDozeCounter.addCountLocked(l4);
                            }
                            if ((n2 = this.mDeviceIdleMode) == 1) {
                                this.mDischargeLightDozeCounter.addCountLocked(l4);
                            } else if (n2 == 2) {
                                this.mDischargeDeepDozeCounter.addCountLocked(l4);
                            }
                        }
                        this.mHistoryCur.batteryChargeUAh = n7;
                        this.setOnBatteryLocked(l2, l, bl, n5, n4, n7);
                        break block48;
                    }
                    n5 = 0;
                    if (this.mHistoryCur.batteryLevel != n4) {
                        this.mHistoryCur.batteryLevel = (byte)n4;
                        n5 = 1;
                        this.mExternalSync.scheduleSyncDueToBatteryLevelChange(this.mConstants.BATTERY_LEVEL_COLLECTION_DELAY_MS);
                    }
                    if (this.mHistoryCur.batteryStatus != n) {
                        this.mHistoryCur.batteryStatus = (byte)n;
                        n5 = 1;
                    }
                    if (this.mHistoryCur.batteryHealth != n2) {
                        this.mHistoryCur.batteryHealth = (byte)n2;
                        n5 = 1;
                    }
                    if (this.mHistoryCur.batteryPlugType != n3) {
                        this.mHistoryCur.batteryPlugType = (byte)n3;
                        n5 = 1;
                    }
                    if (n9 >= this.mHistoryCur.batteryTemperature + 10 || n9 <= this.mHistoryCur.batteryTemperature - 10) {
                        this.mHistoryCur.batteryTemperature = (short)n9;
                        n5 = 1;
                    }
                    if (n6 > this.mHistoryCur.batteryVoltage + 20) break block49;
                    n3 = n5;
                    if (n6 >= this.mHistoryCur.batteryVoltage - 20) break block50;
                }
                this.mHistoryCur.batteryVoltage = (char)n6;
                n3 = 1;
            }
            if (n7 >= this.mHistoryCur.batteryChargeUAh + 10 || n7 <= this.mHistoryCur.batteryChargeUAh - 10) {
                if (n7 < this.mHistoryCur.batteryChargeUAh) {
                    l3 = this.mHistoryCur.batteryChargeUAh - n7;
                    this.mDischargeCounter.addCountLocked(l3);
                    this.mDischargeScreenOffCounter.addCountLocked(l3);
                    if (this.isScreenDoze(this.mScreenState)) {
                        this.mDischargeScreenDozeCounter.addCountLocked(l3);
                    }
                    if ((n2 = this.mDeviceIdleMode) == 1) {
                        this.mDischargeLightDozeCounter.addCountLocked(l3);
                    } else if (n2 == 2) {
                        this.mDischargeDeepDozeCounter.addCountLocked(l3);
                    }
                }
                this.mHistoryCur.batteryChargeUAh = n7;
                n3 = 1;
            }
            l3 = (long)this.mInitStepMode << 48 | (long)this.mModStepMode << 56 | (long)(n4 & 255) << 40;
            if (bl) {
                n2 = n3 | this.setChargingLocked(false);
                n3 = this.mLastDischargeStepLevel;
                if (n3 != n4 && this.mMinDischargeStepLevel > n4) {
                    this.mDischargeStepTracker.addLevelSteps(n3 - n4, l3, l2);
                    this.mDailyDischargeStepTracker.addLevelSteps(this.mLastDischargeStepLevel - n4, l3, l2);
                    this.mLastDischargeStepLevel = n4;
                    this.mMinDischargeStepLevel = n4;
                    this.mInitStepMode = this.mCurStepMode;
                    this.mModStepMode = 0;
                }
            } else {
                if (n4 >= 90) {
                    n2 = n3 | this.setChargingLocked(true);
                } else if (!this.mCharging) {
                    n5 = this.mLastChargeStepLevel;
                    if (n5 < n4) {
                        if (!this.mHandler.hasCallbacks(this.mDeferSetCharging)) {
                            this.mHandler.postDelayed(this.mDeferSetCharging, this.mConstants.BATTERY_CHARGED_DELAY_MS);
                            n2 = n3;
                        } else {
                            n2 = n3;
                        }
                    } else {
                        n2 = n3;
                        if (n5 > n4) {
                            this.mHandler.removeCallbacks(this.mDeferSetCharging);
                            n2 = n3;
                        }
                    }
                } else {
                    n2 = n3;
                    if (this.mLastChargeStepLevel > n4) {
                        n2 = n3 | this.setChargingLocked(false);
                    }
                }
                n3 = this.mLastChargeStepLevel;
                if (n3 != n4 && this.mMaxChargeStepLevel < n4) {
                    this.mChargeStepTracker.addLevelSteps(n4 - n3, l3, l2);
                    this.mDailyChargeStepTracker.addLevelSteps(n4 - this.mLastChargeStepLevel, l3, l2);
                    this.mMaxChargeStepLevel = n4;
                    this.mInitStepMode = this.mCurStepMode;
                    this.mModStepMode = 0;
                }
                this.mLastChargeStepLevel = n4;
            }
            if (n2 != 0) {
                this.addHistoryRecordLocked(l2, l);
            }
        }
        if (!(bl || n != 5 && n != 1)) {
            this.mRecordingHistory = false;
        }
        this.mMinLearnedBatteryCapacity = (n = this.mMinLearnedBatteryCapacity) == -1 ? n8 : Math.min(n, n8);
        this.mMaxLearnedBatteryCapacity = Math.max(this.mMaxLearnedBatteryCapacity, n8);
    }

    public void setCallback(BatteryCallback batteryCallback) {
        this.mCallback = batteryCallback;
    }

    boolean setChargingLocked(boolean bl) {
        this.mHandler.removeCallbacks(this.mDeferSetCharging);
        if (this.mCharging != bl) {
            this.mCharging = bl;
            if (bl) {
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states2 |= 16777216;
            } else {
                BatteryStats.HistoryItem historyItem = this.mHistoryCur;
                historyItem.states2 &= -16777217;
            }
            this.mHandler.sendEmptyMessage(3);
            return true;
        }
        return false;
    }

    public void setExternalStatsSyncLocked(ExternalStatsSync externalStatsSync) {
        this.mExternalSync = externalStatsSync;
    }

    public void setNoAutoReset(boolean bl) {
        this.mNoAutoReset = bl;
    }

    @GuardedBy(value={"this"})
    protected void setOnBatteryLocked(long l, long l2, boolean n, int n2, int n3, int n4) {
        int n5 = 0;
        int n6 = 0;
        Object object = this.mHandler.obtainMessage(2);
        ((Message)object).arg1 = n;
        this.mHandler.sendMessage((Message)object);
        long l3 = l2 * 1000L;
        long l4 = l * 1000L;
        int n7 = this.mScreenState;
        if (n != 0) {
            boolean bl;
            if (!this.mNoAutoReset) {
                if (n2 != 5 && n3 < 90 && (this.mDischargeCurrentLevel >= 20 || n3 < 80)) {
                    bl = false;
                    n = n6;
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Resetting battery stats: level=");
                    ((StringBuilder)object).append(n3);
                    ((StringBuilder)object).append(" status=");
                    ((StringBuilder)object).append(n2);
                    ((StringBuilder)object).append(" dischargeLevel=");
                    ((StringBuilder)object).append(this.mDischargeCurrentLevel);
                    ((StringBuilder)object).append(" lowAmount=");
                    ((StringBuilder)object).append(this.getLowDischargeAmountSinceCharge());
                    ((StringBuilder)object).append(" highAmount=");
                    ((StringBuilder)object).append(this.getHighDischargeAmountSinceCharge());
                    Slog.i(TAG, ((StringBuilder)object).toString());
                    if (this.getLowDischargeAmountSinceCharge() >= 20) {
                        long l5 = SystemClock.uptimeMillis();
                        object = Parcel.obtain();
                        this.writeSummaryToParcel((Parcel)object, true);
                        long l6 = SystemClock.uptimeMillis();
                        BackgroundThread.getHandler().post(new Runnable((Parcel)object, l6 - l5){
                            final /* synthetic */ long val$initialTime;
                            final /* synthetic */ Parcel val$parcel;
                            {
                                this.val$parcel = parcel;
                                this.val$initialTime = l;
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
                            @Override
                            public void run() {
                                Throwable throwable2222;
                                block8 : {
                                    AtomicFile atomicFile = BatteryStatsImpl.this.mCheckinFile;
                                    // MONITORENTER : atomicFile
                                    long l = SystemClock.uptimeMillis();
                                    Object object = null;
                                    FileOutputStream fileOutputStream = BatteryStatsImpl.this.mCheckinFile.startWrite();
                                    object = fileOutputStream;
                                    fileOutputStream.write(this.val$parcel.marshall());
                                    object = fileOutputStream;
                                    fileOutputStream.flush();
                                    object = fileOutputStream;
                                    BatteryStatsImpl.this.mCheckinFile.finishWrite(fileOutputStream);
                                    object = fileOutputStream;
                                    EventLogTags.writeCommitSysConfigFile("batterystats-checkin", this.val$initialTime + SystemClock.uptimeMillis() - l);
                                    object = this.val$parcel;
                                    {
                                        catch (Throwable throwable2222) {
                                            break block8;
                                        }
                                        catch (IOException iOException) {}
                                        {
                                            Slog.w("BatteryStats", "Error writing checkin battery statistics", iOException);
                                            BatteryStatsImpl.this.mCheckinFile.failWrite((FileOutputStream)object);
                                            object = this.val$parcel;
                                        }
                                    }
                                    ((Parcel)object).recycle();
                                    // MONITOREXIT : atomicFile
                                    return;
                                }
                                this.val$parcel.recycle();
                                throw throwable2222;
                            }
                        });
                    }
                    this.resetAllStatsLocked();
                    if (n4 > 0 && n3 > 0) {
                        this.mEstimatedBatteryCapacity = (int)((double)(n4 / 1000) / ((double)n3 / 100.0));
                    }
                    this.mDischargeStartLevel = n3;
                    this.mDischargeStepTracker.init();
                    n = 1;
                    bl = true;
                }
            } else {
                bl = false;
                n = n6;
            }
            if (this.mCharging) {
                this.setChargingLocked(false);
            }
            this.mLastChargingStateLevel = n3;
            this.mOnBatteryInternal = true;
            this.mOnBattery = true;
            this.mLastDischargeStepLevel = n3;
            this.mMinDischargeStepLevel = n3;
            this.mDischargeStepTracker.clearTime();
            this.mDailyDischargeStepTracker.clearTime();
            this.mInitStepMode = this.mCurStepMode;
            this.mModStepMode = 0;
            this.pullPendingStateUpdatesLocked();
            object = this.mHistoryCur;
            ((BatteryStats.HistoryItem)object).batteryLevel = (byte)n3;
            ((BatteryStats.HistoryItem)object).states &= -524289;
            if (bl) {
                this.mRecordingHistory = true;
                this.startRecordingHistory(l, l2, bl);
            }
            this.addHistoryRecordLocked(l, l2);
            this.mDischargeUnplugLevel = n3;
            this.mDischargeCurrentLevel = n3;
            if (this.isScreenOn(n7)) {
                this.mDischargeScreenOnUnplugLevel = n3;
                this.mDischargeScreenDozeUnplugLevel = 0;
                this.mDischargeScreenOffUnplugLevel = 0;
            } else if (this.isScreenDoze(n7)) {
                this.mDischargeScreenOnUnplugLevel = 0;
                this.mDischargeScreenDozeUnplugLevel = n3;
                this.mDischargeScreenOffUnplugLevel = 0;
            } else {
                this.mDischargeScreenOnUnplugLevel = 0;
                this.mDischargeScreenDozeUnplugLevel = 0;
                this.mDischargeScreenOffUnplugLevel = n3;
            }
            this.mDischargeAmountScreenOn = 0;
            this.mDischargeAmountScreenDoze = 0;
            this.mDischargeAmountScreenOff = 0;
            this.updateTimeBasesLocked(true, n7, l3, l4);
        } else {
            this.mLastChargingStateLevel = n3;
            this.mOnBatteryInternal = false;
            this.mOnBattery = false;
            this.pullPendingStateUpdatesLocked();
            object = this.mHistoryCur;
            ((BatteryStats.HistoryItem)object).batteryLevel = (byte)n3;
            ((BatteryStats.HistoryItem)object).states |= 524288;
            this.addHistoryRecordLocked(l, l2);
            this.mDischargePlugLevel = n3;
            this.mDischargeCurrentLevel = n3;
            n = this.mDischargeUnplugLevel;
            if (n3 < n) {
                this.mLowDischargeAmountSinceCharge += n - n3 - 1;
                this.mHighDischargeAmountSinceCharge += n - n3;
            }
            this.updateDischargeScreenLevelsLocked(n7, n7);
            this.updateTimeBasesLocked(false, n7, l3, l4);
            this.mChargeStepTracker.init();
            this.mLastChargeStepLevel = n3;
            this.mMaxChargeStepLevel = n3;
            this.mInitStepMode = this.mCurStepMode;
            this.mModStepMode = 0;
            n = n5;
        }
        if ((n != 0 || this.mLastWriteTime + 60000L < l) && this.mStatsFile != null && this.mBatteryStatsHistory.getActiveFile() != null) {
            this.writeAsyncLocked();
        }
    }

    public void setPowerProfileLocked(PowerProfile powerProfile) {
        this.mPowerProfile = powerProfile;
        int n = this.mPowerProfile.getNumCpuClusters();
        this.mKernelCpuSpeedReaders = new KernelCpuSpeedReader[n];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = this.mPowerProfile.getNumSpeedStepsInCpuCluster(i);
            this.mKernelCpuSpeedReaders[i] = new KernelCpuSpeedReader(n2, n3);
            n2 += this.mPowerProfile.getNumCoresInCpuCluster(i);
        }
        if (this.mEstimatedBatteryCapacity == -1) {
            this.mEstimatedBatteryCapacity = (int)this.mPowerProfile.getBatteryCapacity();
        }
    }

    public void setPretendScreenOff(boolean bl) {
        if (this.mPretendScreenOff != bl) {
            this.mPretendScreenOff = bl;
            int n = bl ? 1 : 2;
            this.noteScreenStateLocked(n);
        }
    }

    public void setRadioScanningTimeoutLocked(long l) {
        StopwatchTimer stopwatchTimer = this.mPhoneSignalScanningTimer;
        if (stopwatchTimer != null) {
            stopwatchTimer.setTimeout(l);
        }
    }

    public void setRecordAllHistoryLocked(boolean bl) {
        block6 : {
            block5 : {
                this.mRecordAllHistory = bl;
                if (bl) break block5;
                this.mActiveEvents.removeEvents(5);
                this.mActiveEvents.removeEvents(13);
                Cloneable cloneable = this.mActiveEvents.getStateForEvent(1);
                if (cloneable == null) break block6;
                long l = this.mClocks.elapsedRealtime();
                long l2 = this.mClocks.uptimeMillis();
                for (Map.Entry<String, SparseIntArray> entry : ((HashMap)cloneable).entrySet()) {
                    cloneable = entry.getValue();
                    for (int i = 0; i < ((SparseIntArray)cloneable).size(); ++i) {
                        this.addHistoryEventLocked(l, l2, 16385, entry.getKey(), ((SparseIntArray)cloneable).keyAt(i));
                    }
                }
                break block6;
            }
            Cloneable cloneable = this.mActiveEvents.getStateForEvent(1);
            if (cloneable != null) {
                long l = this.mClocks.elapsedRealtime();
                long l3 = this.mClocks.uptimeMillis();
                for (Map.Entry<String, SparseIntArray> entry : ((HashMap)cloneable).entrySet()) {
                    cloneable = entry.getValue();
                    for (int i = 0; i < ((SparseIntArray)cloneable).size(); ++i) {
                        this.addHistoryEventLocked(l, l3, 32769, entry.getKey(), ((SparseIntArray)cloneable).keyAt(i));
                    }
                }
            }
        }
    }

    public void shutdownLocked() {
        this.recordShutdownLocked(this.mClocks.elapsedRealtime(), this.mClocks.uptimeMillis());
        this.writeSyncLocked();
        this.mShuttingDown = true;
    }

    public boolean startAddingCpuLocked() {
        this.mExternalSync.cancelCpuSyncDueToWakelockChange();
        return this.mOnBatteryInternal;
    }

    @UnsupportedAppUsage
    @Override
    public boolean startIteratingHistoryLocked() {
        this.mBatteryStatsHistory.startIteratingHistory();
        this.mReadOverflow = false;
        this.mIteratingHistory = true;
        this.mReadHistoryStrings = new String[this.mHistoryTagPool.size()];
        this.mReadHistoryUids = new int[this.mHistoryTagPool.size()];
        this.mReadHistoryChars = 0;
        for (Map.Entry<BatteryStats.HistoryTag, Integer> entry : this.mHistoryTagPool.entrySet()) {
            BatteryStats.HistoryTag historyTag = entry.getKey();
            int n = entry.getValue();
            this.mReadHistoryStrings[n] = historyTag.string;
            this.mReadHistoryUids[n] = historyTag.uid;
            this.mReadHistoryChars += historyTag.string.length() + 1;
        }
        return true;
    }

    @Override
    public boolean startIteratingOldHistoryLocked() {
        BatteryStats.HistoryItem historyItem;
        this.mHistoryIterator = historyItem = this.mHistory;
        if (historyItem == null) {
            return false;
        }
        this.mHistoryBuffer.setDataPosition(0);
        this.mHistoryReadTmp.clear();
        this.mReadOverflow = false;
        this.mIteratingHistory = true;
        return true;
    }

    void stopAllGpsSignalQualityTimersLocked(int n) {
        long l = this.mClocks.elapsedRealtime();
        for (int i = 0; i < 2; ++i) {
            if (i == n) continue;
            while (this.mGpsSignalQualityTimer[i].isRunningLocked()) {
                this.mGpsSignalQualityTimer[i].stopRunningLocked(l);
            }
        }
    }

    void stopAllPhoneSignalStrengthTimersLocked(int n) {
        long l = this.mClocks.elapsedRealtime();
        for (int i = 0; i < 5; ++i) {
            if (i == n) continue;
            while (this.mPhoneSignalStrengthsTimer[i].isRunningLocked()) {
                this.mPhoneSignalStrengthsTimer[i].stopRunningLocked(l);
            }
        }
    }

    void stopAllWifiSignalStrengthTimersLocked(int n) {
        long l = this.mClocks.elapsedRealtime();
        for (int i = 0; i < 5; ++i) {
            if (i == n) continue;
            while (this.mWifiSignalStrengthsTimer[i].isRunningLocked()) {
                this.mWifiSignalStrengthsTimer[i].stopRunningLocked(l);
            }
        }
    }

    public void systemServicesReady(Context context) {
        this.mConstants.startObserving(context.getContentResolver());
        this.registerUsbStateReceiver(context);
    }

    public boolean trackPerProcStateCpuTimes() {
        boolean bl = this.mConstants.TRACK_CPU_TIMES_BY_PROC_STATE && this.mPerProcStateCpuTimesAvailable;
        return bl;
    }

    public void updateBluetoothStateLocked(BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) {
        if (bluetoothActivityEnergyInfo != null && this.mOnBatteryInternal) {
            int n;
            Object object;
            long l;
            Object object2;
            long l2;
            this.mHasBluetoothReporting = true;
            long l3 = this.mClocks.elapsedRealtime();
            long l4 = bluetoothActivityEnergyInfo.getControllerRxTimeMillis() - this.mLastBluetoothActivityInfo.rxTimeMs;
            long l5 = bluetoothActivityEnergyInfo.getControllerTxTimeMillis() - this.mLastBluetoothActivityInfo.txTimeMs;
            long l6 = bluetoothActivityEnergyInfo.getControllerIdleTimeMillis() - this.mLastBluetoothActivityInfo.idleTimeMs;
            long l7 = 0L;
            int n2 = this.mUidStats.size();
            for (n = 0; n < n2; ++n) {
                object = this.mUidStats.valueAt(n);
                if (((Uid)object).mBluetoothScanTimer == null) continue;
                l7 += ((Uid)object).mBluetoothScanTimer.getTimeSinceMarkLocked(l3 * 1000L) / 1000L;
            }
            n = l7 > l4 ? 1 : 0;
            int n3 = l7 > l5 ? 1 : 0;
            long l8 = l5;
            long l9 = l4;
            for (int i = 0; i < n2; ++i) {
                object = this.mUidStats.valueAt(i);
                if (((Uid)object).mBluetoothScanTimer == null || (l2 = ((Uid)object).mBluetoothScanTimer.getTimeSinceMarkLocked(l3 * 1000L) / 1000L) <= 0L) continue;
                ((Uid)object).mBluetoothScanTimer.setMark(l3);
                l = n != 0 ? l4 * l2 / l7 : l2;
                if (n3 != 0) {
                    l2 = l5 * l2 / l7;
                }
                object = ((Uid)object).getOrCreateBluetoothControllerActivityLocked();
                ((LongSamplingCounter)((ControllerActivityCounterImpl)object).getRxTimeCounter()).addCountLocked(l);
                ((ControllerActivityCounterImpl)object).getTxTimeCounters()[0].addCountLocked(l2);
                l9 -= l;
                l8 -= l2;
            }
            l3 = 0L;
            l2 = 0L;
            object = bluetoothActivityEnergyInfo.getUidTraffic();
            n = object != null ? ((UidTraffic[])object).length : 0;
            l = l7;
            l7 = l2;
            for (n2 = 0; n2 < n; ++n2) {
                object2 = object[n2];
                long l10 = ((UidTraffic)object2).getRxBytes() - this.mLastBluetoothActivityInfo.uidRxBytes.get(((UidTraffic)object2).getUid());
                l2 = ((UidTraffic)object2).getTxBytes() - this.mLastBluetoothActivityInfo.uidTxBytes.get(((UidTraffic)object2).getUid());
                this.mNetworkByteActivityCounters[4].addCountLocked(l10);
                this.mNetworkByteActivityCounters[5].addCountLocked(l2);
                object2 = this.getUidStatsLocked(this.mapUid(((UidTraffic)object2).getUid()));
                ((Uid)object2).noteNetworkActivityLocked(4, l10, 0L);
                ((Uid)object2).noteNetworkActivityLocked(5, l2, 0L);
                l7 += l10;
                l3 += l2;
            }
            if (l3 != 0L || l7 != 0L) {
                if (l9 != 0L || l8 != 0L) {
                    for (n3 = 0; n3 < n; ++n3) {
                        object2 = object[n3];
                        n2 = ((UidTraffic)object2).getUid();
                        l2 = ((UidTraffic)object2).getRxBytes() - this.mLastBluetoothActivityInfo.uidRxBytes.get(n2);
                        l = ((UidTraffic)object2).getTxBytes() - this.mLastBluetoothActivityInfo.uidTxBytes.get(n2);
                        object2 = this.getUidStatsLocked(this.mapUid(n2));
                        object2 = ((Uid)object2).getOrCreateBluetoothControllerActivityLocked();
                        if (l7 > 0L && l2 > 0L) {
                            l2 = l9 * l2 / l7;
                            ((LongSamplingCounter)((ControllerActivityCounterImpl)object2).getRxTimeCounter()).addCountLocked(l2);
                        }
                        if (l3 <= 0L || l <= 0L) continue;
                        l = l8 * l / l3;
                        ((ControllerActivityCounterImpl)object2).getTxTimeCounters()[0].addCountLocked(l);
                    }
                }
            }
            ((LongSamplingCounter)this.mBluetoothActivity.getRxTimeCounter()).addCountLocked(l4);
            this.mBluetoothActivity.getTxTimeCounters()[0].addCountLocked(l5);
            ((LongSamplingCounter)this.mBluetoothActivity.getIdleTimeCounter()).addCountLocked(l6);
            double d = this.mPowerProfile.getAveragePower("bluetooth.controller.voltage") / 1000.0;
            if (d != 0.0) {
                ((LongSamplingCounter)this.mBluetoothActivity.getPowerCounter()).addCountLocked((long)((double)(bluetoothActivityEnergyInfo.getControllerEnergyUsed() - this.mLastBluetoothActivityInfo.energy) / d));
            }
            this.mLastBluetoothActivityInfo.set(bluetoothActivityEnergyInfo);
            return;
        }
    }

    @VisibleForTesting
    public void updateClusterSpeedTimes(SparseLongArray sparseLongArray, boolean bl) {
        int n;
        long l;
        Object object;
        int n2;
        long l2 = 0L;
        long[][] arrarrl = new long[this.mKernelCpuSpeedReaders.length][];
        for (n2 = 0; n2 < ((KernelCpuSpeedReader[])(object = this.mKernelCpuSpeedReaders)).length; ++n2) {
            arrarrl[n2] = object[n2].readDelta();
            l = l2;
            if (arrarrl[n2] != null) {
                n = arrarrl[n2].length - 1;
                do {
                    l = l2;
                    if (n < 0) break;
                    l2 += arrarrl[n2][n];
                    --n;
                } while (true);
            }
            l2 = l;
        }
        if (l2 != 0L) {
            int n3 = sparseLongArray.size();
            n2 = 0;
            do {
                object = sparseLongArray;
                if (n2 >= n3) break;
                Uid uid = this.getUidStatsLocked(((SparseLongArray)object).keyAt(n2));
                l = ((SparseLongArray)object).valueAt(n2);
                n = this.mPowerProfile.getNumCpuClusters();
                if (uid.mCpuClusterSpeedTimesUs == null || uid.mCpuClusterSpeedTimesUs.length != n) {
                    uid.mCpuClusterSpeedTimesUs = new LongSamplingCounter[n][];
                }
                for (n = 0; n < arrarrl.length; ++n) {
                    int n4 = arrarrl[n].length;
                    if (uid.mCpuClusterSpeedTimesUs[n] == null || n4 != uid.mCpuClusterSpeedTimesUs[n].length) {
                        uid.mCpuClusterSpeedTimesUs[n] = new LongSamplingCounter[n4];
                    }
                    object = uid.mCpuClusterSpeedTimesUs[n];
                    for (int i = 0; i < n4; ++i) {
                        if (object[i] == null) {
                            object[i] = new LongSamplingCounter(this.mOnBatteryTimeBase);
                        }
                        ((LongSamplingCounter)object[i]).addCountLocked(arrarrl[n][i] * l / l2, bl);
                    }
                }
                ++n2;
            } while (true);
        }
    }

    @GuardedBy(value={"this"})
    public void updateCpuTimeLocked(boolean bl, boolean bl2) {
        int n;
        Cloneable cloneable;
        Object object = this.mPowerProfile;
        if (object == null) {
            return;
        }
        if (this.mCpuFreqs == null) {
            this.mCpuFreqs = this.mCpuUidFreqTimeReader.readFreqs((PowerProfile)object);
        }
        object = null;
        if (bl2) {
            cloneable = new ArrayList();
            n = this.mPartialTimers.size() - 1;
            do {
                object = cloneable;
                if (n < 0) break;
                object = this.mPartialTimers.get(n);
                if (((StopwatchTimer)object).mInList && ((StopwatchTimer)object).mUid != null && object.mUid.mUid != 1000) {
                    ((ArrayList)cloneable).add(object);
                }
                --n;
            } while (true);
        }
        this.markPartialTimersAsEligible();
        cloneable = null;
        if (!bl) {
            this.mCpuUidUserSysTimeReader.readDelta(null);
            this.mCpuUidFreqTimeReader.readDelta(null);
            this.mNumAllUidCpuTimeReads += 2;
            if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                this.mCpuUidActiveTimeReader.readDelta(null);
                this.mCpuUidClusterTimeReader.readDelta(null);
                this.mNumAllUidCpuTimeReads += 2;
            }
            for (n = this.mKernelCpuSpeedReaders.length - 1; n >= 0; --n) {
                this.mKernelCpuSpeedReaders[n].readDelta();
            }
            return;
        }
        this.mUserInfoProvider.refreshUserIds();
        if (!this.mCpuUidFreqTimeReader.perClusterTimesAvailable()) {
            cloneable = new SparseLongArray();
        }
        this.readKernelUidCpuTimesLocked((ArrayList<StopwatchTimer>)object, (SparseLongArray)cloneable, bl);
        if (cloneable != null) {
            this.updateClusterSpeedTimes((SparseLongArray)cloneable, bl);
        }
        this.readKernelUidCpuFreqTimesLocked((ArrayList<StopwatchTimer>)object, bl, bl2);
        this.mNumAllUidCpuTimeReads += 2;
        if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
            this.readKernelUidCpuActiveTimesLocked(bl);
            this.readKernelUidCpuClusterTimesLocked(bl);
            this.mNumAllUidCpuTimeReads += 2;
        }
    }

    public void updateDailyDeadlineLocked() {
        long l;
        this.mDailyStartTime = l = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        calendar.set(6, calendar.get(6) + 1);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 1);
        this.mNextMinDailyDeadline = calendar.getTimeInMillis();
        calendar.set(11, 3);
        this.mNextMaxDailyDeadline = calendar.getTimeInMillis();
    }

    void updateDischargeScreenLevelsLocked(int n, int n2) {
        this.updateOldDischargeScreenLevelLocked(n);
        this.updateNewDischargeScreenLevelLocked(n2);
    }

    public void updateKernelMemoryBandwidthLocked() {
        this.mKernelMemoryBandwidthStats.updateStats();
        LongSparseLongArray longSparseLongArray = this.mKernelMemoryBandwidthStats.getBandwidthEntries();
        int n = longSparseLongArray.size();
        for (int i = 0; i < n; ++i) {
            SamplingTimer samplingTimer;
            int n2 = this.mKernelMemoryStats.indexOfKey(longSparseLongArray.keyAt(i));
            if (n2 >= 0) {
                samplingTimer = this.mKernelMemoryStats.valueAt(n2);
            } else {
                samplingTimer = new SamplingTimer(this.mClocks, this.mOnBatteryTimeBase);
                this.mKernelMemoryStats.put(longSparseLongArray.keyAt(i), samplingTimer);
            }
            samplingTimer.update(longSparseLongArray.valueAt(i), 1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void updateKernelWakelocksLocked() {
        Object object;
        KernelWakelockStats kernelWakelockStats = this.mKernelWakelockReader.readKernelWakelockStats(this.mTmpWakelockStats);
        if (kernelWakelockStats == null) {
            Slog.w(TAG, "Couldn't get kernel wake lock stats");
            return;
        }
        for (Map.Entry entry : kernelWakelockStats.entrySet()) {
            void object22;
            String string2 = (String)entry.getKey();
            KernelWakelockStats.Entry entry2 = (KernelWakelockStats.Entry)entry.getValue();
            Object object2 = object = this.mKernelWakelockStats.get(string2);
            if (object == null) {
                SamplingTimer samplingTimer = new SamplingTimer(this.mClocks, this.mOnBatteryScreenOffTimeBase);
                this.mKernelWakelockStats.put(string2, samplingTimer);
            }
            object22.update(entry2.mTotalTime, entry2.mCount);
            object22.setUpdateVersion(entry2.mVersion);
        }
        int n = 0;
        object = this.mKernelWakelockStats.entrySet().iterator();
        while (object.hasNext()) {
            SamplingTimer samplingTimer = object.next().getValue();
            int n2 = n;
            if (samplingTimer.getUpdateVersion() != kernelWakelockStats.kernelWakelockVersion) {
                samplingTimer.endSample();
                n2 = n + 1;
            }
            n = n2;
        }
        if (kernelWakelockStats.isEmpty()) {
            Slog.wtf(TAG, "All kernel wakelocks had time of zero");
        }
        if (n == this.mKernelWakelockStats.size()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("All kernel wakelocks were set stale. new version=");
            stringBuilder.append(kernelWakelockStats.kernelWakelockVersion);
            Slog.wtf(TAG, stringBuilder.toString());
        }
    }

    /*
     * Exception decompiling
     */
    public void updateMobileRadioState(ModemActivityInfo var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [15[TRYBLOCK]], but top level block is 16[TRYBLOCK]
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateProcStateCpuTimes(boolean bl, boolean bl2) {
        SparseIntArray sparseIntArray;
        synchronized (this) {
            if (!this.mConstants.TRACK_CPU_TIMES_BY_PROC_STATE) {
                return;
            }
            if (!this.initKernelSingleUidTimeReaderLocked()) {
                return;
            }
            if (this.mIsPerProcessStateCpuDataStale) {
                this.mPendingUids.clear();
                return;
            }
            if (this.mPendingUids.size() == 0) {
                return;
            }
            sparseIntArray = this.mPendingUids.clone();
            this.mPendingUids.clear();
        }
        int n = sparseIntArray.size() - 1;
        while (n >= 0) {
            int n2 = sparseIntArray.keyAt(n);
            int n3 = sparseIntArray.valueAt(n);
            synchronized (this) {
                Uid uid = this.getAvailableUidStatsLocked(n2);
                if (uid == null) {
                } else {
                    int[] arrn;
                    int[] arrn2;
                    int n4;
                    if (uid.mChildUids == null) {
                        arrn2 = null;
                    } else {
                        arrn = uid.mChildUids.toArray();
                        n4 = arrn.length - 1;
                        do {
                            arrn2 = arrn;
                            if (n4 < 0) break;
                            arrn[n4] = uid.mChildUids.get(n4);
                            --n4;
                        } while (true);
                    }
                    // MONITOREXIT [16, 7, 9] lbl40 : MonitorExitStatement: MONITOREXIT : this
                    int[] arrn3 = arrn = this.mKernelSingleUidTimeReader.readDeltaMs(n2);
                    if (arrn2 != null) {
                        n4 = arrn2.length - 1;
                        do {
                            arrn3 = arrn;
                            if (n4 < 0) break;
                            arrn = this.addCpuTimes(arrn, this.mKernelSingleUidTimeReader.readDeltaMs(arrn2[n4]));
                            --n4;
                        } while (true);
                    }
                    if (bl && arrn3 != null) {
                        synchronized (this) {
                            uid.addProcStateTimesMs(n3, arrn3, bl);
                            uid.addProcStateScreenOffTimesMs(n3, arrn3, bl2);
                        }
                    }
                }
            }
            --n;
        }
        return;
    }

    public void updateRailStatsLocked() {
        if (this.mRailEnergyDataCallback != null && this.mTmpRailStats.isRailStatsAvailable()) {
            this.mRailEnergyDataCallback.fillRailDataStats(this.mTmpRailStats);
            return;
        }
    }

    public void updateRpmStatsLocked() {
        int n;
        String string2;
        CharSequence charSequence;
        if (this.mPlatformIdleStateCallback == null) {
            return;
        }
        long l = SystemClock.elapsedRealtime();
        if (l - this.mLastRpmStatsUpdateTimeMs >= 1000L) {
            this.mPlatformIdleStateCallback.fillLowPowerStats(this.mTmpRpmStats);
            this.mLastRpmStatsUpdateTimeMs = l;
        }
        for (Map.Entry<String, RpmStats.PowerStatePlatformSleepState> entry : this.mTmpRpmStats.mPlatformLowPowerStats.entrySet()) {
            string2 = entry.getKey();
            long l2 = entry.getValue().mTimeMs;
            n = entry.getValue().mCount;
            this.getRpmTimerLocked(string2).update(l2 * 1000L, n);
            for (Map.Entry entry2 : entry.getValue().mVoters.entrySet()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(".");
                ((StringBuilder)charSequence).append((String)entry2.getKey());
                charSequence = ((StringBuilder)charSequence).toString();
                l2 = ((RpmStats.PowerStateElement)entry2.getValue()).mTimeMs;
                n = ((RpmStats.PowerStateElement)entry2.getValue()).mCount;
                this.getRpmTimerLocked((String)charSequence).update(l2 * 1000L, n);
            }
        }
        for (Map.Entry<String, Object> entry : this.mTmpRpmStats.mSubsystemLowPowerStats.entrySet()) {
            string2 = entry.getKey();
            for (Object object : ((RpmStats.PowerStateSubsystem)entry.getValue()).mStates.entrySet()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(".");
                ((StringBuilder)charSequence).append((String)object.getKey());
                charSequence = ((StringBuilder)charSequence).toString();
                l = ((RpmStats.PowerStateElement)object.getValue()).mTimeMs;
                n = ((RpmStats.PowerStateElement)object.getValue()).mCount;
                this.getRpmTimerLocked((String)charSequence).update(l * 1000L, n);
            }
        }
    }

    @GuardedBy(value={"this"})
    public void updateTimeBasesLocked(boolean bl, int n, long l, long l2) {
        boolean bl2 = this.isScreenOn(n) ^ true;
        int n2 = bl != this.mOnBatteryTimeBase.isRunning() ? 1 : 0;
        boolean bl3 = bl && bl2;
        n = bl3 != this.mOnBatteryScreenOffTimeBase.isRunning() ? 1 : 0;
        if (n != 0 || n2 != 0) {
            if (n != 0) {
                this.updateKernelWakelocksLocked();
                this.updateBatteryPropertiesLocked();
            }
            if (n2 != 0) {
                this.updateRpmStatsLocked();
            }
            this.mOnBatteryTimeBase.setRunning(bl, l, l2);
            if (n2 != 0) {
                for (n2 = this.mUidStats.size() - 1; n2 >= 0; --n2) {
                    this.mUidStats.valueAt(n2).updateOnBatteryBgTimeBase(l, l2);
                }
            }
            if (n != 0) {
                TimeBase timeBase = this.mOnBatteryScreenOffTimeBase;
                bl = bl && bl2;
                timeBase.setRunning(bl, l, l2);
                for (n = this.mUidStats.size() - 1; n >= 0; --n) {
                    this.mUidStats.valueAt(n).updateOnBatteryScreenOffBgTimeBase(l, l2);
                }
            }
        }
    }

    /*
     * Exception decompiling
     */
    public void updateWifiState(WifiActivityEnergyInfo var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [20[UNCONDITIONALDOLOOP]], but top level block is 7[TRYBLOCK]
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

    public void writeAsyncLocked() {
        this.writeStatsLocked(false);
        this.writeHistoryLocked(false);
    }

    void writeHistoryBuffer(Parcel parcel, boolean bl, boolean bl2) {
        parcel.writeInt(186);
        parcel.writeLong(this.mHistoryBaseTime + this.mLastHistoryElapsedRealtime);
        if (!bl) {
            parcel.writeInt(0);
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(this.mHistoryBuffer.dataSize());
        Parcel parcel2 = this.mHistoryBuffer;
        parcel.appendFrom(parcel2, 0, parcel2.dataSize());
        if (bl2) {
            this.writeOldHistory(parcel);
        }
    }

    public void writeHistoryDelta(Parcel parcel, BatteryStats.HistoryItem historyItem, BatteryStats.HistoryItem object) {
        block22 : {
            int n;
            int n2;
            int n3;
            int n4;
            boolean bl;
            boolean bl2;
            boolean bl3;
            int n5;
            int n6;
            long l;
            int n7;
            block24 : {
                block23 : {
                    if (object == null || historyItem.cmd != 0) break block22;
                    l = historyItem.time - ((BatteryStats.HistoryItem)object).time;
                    n6 = this.buildBatteryLevelInt((BatteryStats.HistoryItem)object);
                    n5 = this.buildStateInt((BatteryStats.HistoryItem)object);
                    n4 = l >= 0L && l <= Integer.MAX_VALUE ? (l >= 524285L ? 524286 : (int)l) : 524287;
                    n7 = historyItem.states & -33554432 | n4;
                    n = this.mLastHistoryStepLevel > historyItem.batteryLevel ? 1 : 0;
                    bl = n != 0 || this.mLastHistoryStepDetails == null;
                    n3 = this.buildBatteryLevelInt(historyItem) | n;
                    bl3 = n3 != n6;
                    n6 = n7;
                    if (bl3) {
                        n6 = n7 | 524288;
                    }
                    n5 = (n2 = this.buildStateInt(historyItem)) != n5 ? 1 : 0;
                    n7 = n6;
                    if (n5 != 0) {
                        n7 = n6 | 1048576;
                    }
                    bl2 = historyItem.states2 != ((BatteryStats.HistoryItem)object).states2;
                    n6 = n7;
                    if (bl2) {
                        n6 = n7 | 2097152;
                    }
                    if (historyItem.wakelockTag != null) break block23;
                    n7 = n6;
                    if (historyItem.wakeReasonTag == null) break block24;
                }
                n7 = n6 | 4194304;
            }
            n6 = n7;
            if (historyItem.eventCode != 0) {
                n6 = n7 | 8388608;
            }
            n7 = historyItem.batteryChargeUAh != ((BatteryStats.HistoryItem)object).batteryChargeUAh ? 1 : 0;
            int n8 = n6;
            if (n7 != 0) {
                n8 = n6 | 16777216;
            }
            parcel.writeInt(n8);
            if (n4 >= 524286) {
                if (n4 == 524286) {
                    parcel.writeInt((int)l);
                } else {
                    parcel.writeLong(l);
                }
            }
            if (bl3) {
                parcel.writeInt(n3);
            }
            if (n5 != 0) {
                parcel.writeInt(n2);
            }
            if (bl2) {
                parcel.writeInt(historyItem.states2);
            }
            if (historyItem.wakelockTag != null || historyItem.wakeReasonTag != null) {
                n4 = historyItem.wakelockTag != null ? this.writeHistoryTag(historyItem.wakelockTag) : 65535;
                n6 = historyItem.wakeReasonTag != null ? this.writeHistoryTag(historyItem.wakeReasonTag) : 65535;
                parcel.writeInt(n6 << 16 | n4);
            }
            if (historyItem.eventCode != 0) {
                n4 = this.writeHistoryTag(historyItem.eventTag);
                parcel.writeInt(historyItem.eventCode & 65535 | n4 << 16);
            }
            if (bl) {
                object = this.mPlatformIdleStateCallback;
                if (object != null) {
                    this.mCurHistoryStepDetails.statPlatformIdleState = object.getPlatformLowPowerStats();
                    this.mCurHistoryStepDetails.statSubsystemPowerState = this.mPlatformIdleStateCallback.getSubsystemLowPowerStats();
                }
                this.computeHistoryStepDetails(this.mCurHistoryStepDetails, this.mLastHistoryStepDetails);
                if (n != 0) {
                    this.mCurHistoryStepDetails.writeToParcel(parcel);
                }
                historyItem.stepDetails = object = this.mCurHistoryStepDetails;
                this.mLastHistoryStepDetails = object;
            } else {
                historyItem.stepDetails = null;
            }
            if (this.mLastHistoryStepLevel < historyItem.batteryLevel) {
                this.mLastHistoryStepDetails = null;
            }
            this.mLastHistoryStepLevel = historyItem.batteryLevel;
            if (n7 != 0) {
                parcel.writeInt(historyItem.batteryChargeUAh);
            }
            parcel.writeDouble(historyItem.modemRailChargeMah);
            parcel.writeDouble(historyItem.wifiRailChargeMah);
            return;
        }
        parcel.writeInt(524285);
        historyItem.writeToParcel(parcel, 0);
    }

    void writeHistoryLocked(boolean bl) {
        if (this.mBatteryStatsHistory.getActiveFile() == null) {
            Slog.w(TAG, "writeHistoryLocked: no history file associated with this instance");
            return;
        }
        if (this.mShuttingDown) {
            return;
        }
        Parcel parcel = Parcel.obtain();
        SystemClock.uptimeMillis();
        this.writeHistoryBuffer(parcel, true, true);
        this.writeParcelToFileLocked(parcel, this.mBatteryStatsHistory.getActiveFile(), bl);
    }

    void writeOldHistory(Parcel parcel) {
    }

    void writeParcelToFileLocked(final Parcel parcel, final AtomicFile atomicFile, boolean bl) {
        if (bl) {
            this.commitPendingDataToDisk(parcel, atomicFile);
        } else {
            BackgroundThread.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    BatteryStatsImpl.this.commitPendingDataToDisk(parcel, atomicFile);
                }
            });
        }
    }

    void writeStatsLocked(boolean bl) {
        if (this.mStatsFile == null) {
            Slog.w(TAG, "writeStatsLocked: no file associated with this instance");
            return;
        }
        if (this.mShuttingDown) {
            return;
        }
        Parcel parcel = Parcel.obtain();
        SystemClock.uptimeMillis();
        this.writeSummaryToParcel(parcel, false);
        this.mLastWriteTime = this.mClocks.elapsedRealtime();
        this.writeParcelToFileLocked(parcel, this.mStatsFile, bl);
    }

    public void writeSummaryToParcel(Parcel parcel, boolean bl) {
        int n;
        Object object;
        int n2;
        Object object322 = this;
        this.pullPendingStateUpdatesLocked();
        this.getStartClockTime();
        long l = ((BatteryStatsImpl)object322).mClocks.uptimeMillis() * 1000L;
        long l2 = ((BatteryStatsImpl)object322).mClocks.elapsedRealtime() * 1000L;
        parcel.writeInt(186);
        parcel.writeBoolean(bl);
        if (bl) {
            ((BatteryStatsImpl)object322).writeHistoryBuffer(parcel, true, true);
            ((BatteryStatsImpl)object322).mBatteryStatsHistory.writeToParcel(parcel);
        }
        parcel.writeInt(((BatteryStatsImpl)object322).mHistoryTagPool.size());
        for (Map.Entry<BatteryStats.HistoryTag, Integer> entry : ((BatteryStatsImpl)object322).mHistoryTagPool.entrySet()) {
            object = entry.getKey();
            parcel.writeInt(entry.getValue());
            parcel.writeString(((BatteryStats.HistoryTag)object).string);
            parcel.writeInt(((BatteryStats.HistoryTag)object).uid);
        }
        parcel.writeInt(((BatteryStatsImpl)object322).mStartCount);
        parcel.writeLong(((BatteryStatsImpl)object322).computeUptime(l, 0));
        parcel.writeLong(((BatteryStatsImpl)object322).computeRealtime(l2, 0));
        parcel.writeLong(((BatteryStatsImpl)object322).mStartClockTime);
        parcel.writeString(((BatteryStatsImpl)object322).mStartPlatformVersion);
        parcel.writeString(((BatteryStatsImpl)object322).mEndPlatformVersion);
        ((BatteryStatsImpl)object322).mOnBatteryTimeBase.writeSummaryToParcel(parcel, l, l2);
        ((BatteryStatsImpl)object322).mOnBatteryScreenOffTimeBase.writeSummaryToParcel(parcel, l, l2);
        parcel.writeInt(((BatteryStatsImpl)object322).mDischargeUnplugLevel);
        parcel.writeInt(((BatteryStatsImpl)object322).mDischargePlugLevel);
        parcel.writeInt(((BatteryStatsImpl)object322).mDischargeCurrentLevel);
        parcel.writeInt(((BatteryStatsImpl)object322).mCurrentBatteryLevel);
        parcel.writeInt(((BatteryStatsImpl)object322).mEstimatedBatteryCapacity);
        parcel.writeInt(((BatteryStatsImpl)object322).mMinLearnedBatteryCapacity);
        parcel.writeInt(((BatteryStatsImpl)object322).mMaxLearnedBatteryCapacity);
        parcel.writeInt(this.getLowDischargeAmountSinceCharge());
        parcel.writeInt(this.getHighDischargeAmountSinceCharge());
        parcel.writeInt(this.getDischargeAmountScreenOnSinceCharge());
        parcel.writeInt(this.getDischargeAmountScreenOffSinceCharge());
        parcel.writeInt(this.getDischargeAmountScreenDozeSinceCharge());
        ((BatteryStatsImpl)object322).mDischargeStepTracker.writeToParcel(parcel);
        ((BatteryStatsImpl)object322).mChargeStepTracker.writeToParcel(parcel);
        ((BatteryStatsImpl)object322).mDailyDischargeStepTracker.writeToParcel(parcel);
        ((BatteryStatsImpl)object322).mDailyChargeStepTracker.writeToParcel(parcel);
        ((BatteryStatsImpl)object322).mDischargeCounter.writeSummaryFromParcelLocked(parcel);
        ((BatteryStatsImpl)object322).mDischargeScreenOffCounter.writeSummaryFromParcelLocked(parcel);
        ((BatteryStatsImpl)object322).mDischargeScreenDozeCounter.writeSummaryFromParcelLocked(parcel);
        ((BatteryStatsImpl)object322).mDischargeLightDozeCounter.writeSummaryFromParcelLocked(parcel);
        ((BatteryStatsImpl)object322).mDischargeDeepDozeCounter.writeSummaryFromParcelLocked(parcel);
        ArrayList<BatteryStats.PackageChange> arrayList = ((BatteryStatsImpl)object322).mDailyPackageChanges;
        if (arrayList != null) {
            n2 = arrayList.size();
            parcel.writeInt(n2);
            for (n = 0; n < n2; ++n) {
                BatteryStats.PackageChange packageChange = ((BatteryStatsImpl)object322).mDailyPackageChanges.get(n);
                parcel.writeString(packageChange.mPackageName);
                parcel.writeInt((int)packageChange.mUpdate);
                parcel.writeLong(packageChange.mVersionCode);
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(((BatteryStatsImpl)object322).mDailyStartTime);
        parcel.writeLong(((BatteryStatsImpl)object322).mNextMinDailyDeadline);
        parcel.writeLong(((BatteryStatsImpl)object322).mNextMaxDailyDeadline);
        ((BatteryStatsImpl)object322).mScreenOnTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mScreenDozeTimer.writeSummaryFromParcelLocked(parcel, l2);
        for (n = 0; n < 5; ++n) {
            ((BatteryStatsImpl)object322).mScreenBrightnessTimer[n].writeSummaryFromParcelLocked(parcel, l2);
        }
        ((BatteryStatsImpl)object322).mInteractiveTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mPowerSaveModeEnabledTimer.writeSummaryFromParcelLocked(parcel, l2);
        parcel.writeLong(((BatteryStatsImpl)object322).mLongestLightIdleTime);
        parcel.writeLong(((BatteryStatsImpl)object322).mLongestFullIdleTime);
        ((BatteryStatsImpl)object322).mDeviceIdleModeLightTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mDeviceIdleModeFullTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mDeviceLightIdlingTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mDeviceIdlingTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mPhoneOnTimer.writeSummaryFromParcelLocked(parcel, l2);
        for (n = 0; n < 5; ++n) {
            ((BatteryStatsImpl)object322).mPhoneSignalStrengthsTimer[n].writeSummaryFromParcelLocked(parcel, l2);
        }
        ((BatteryStatsImpl)object322).mPhoneSignalScanningTimer.writeSummaryFromParcelLocked(parcel, l2);
        for (n = 0; n < 22; ++n) {
            ((BatteryStatsImpl)object322).mPhoneDataConnectionsTimer[n].writeSummaryFromParcelLocked(parcel, l2);
        }
        for (n = 0; n < 10; ++n) {
            ((BatteryStatsImpl)object322).mNetworkByteActivityCounters[n].writeSummaryFromParcelLocked(parcel);
            ((BatteryStatsImpl)object322).mNetworkPacketActivityCounters[n].writeSummaryFromParcelLocked(parcel);
        }
        ((BatteryStatsImpl)object322).mMobileRadioActiveTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mMobileRadioActivePerAppTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mMobileRadioActiveAdjustedTime.writeSummaryFromParcelLocked(parcel);
        ((BatteryStatsImpl)object322).mMobileRadioActiveUnknownTime.writeSummaryFromParcelLocked(parcel);
        ((BatteryStatsImpl)object322).mMobileRadioActiveUnknownCount.writeSummaryFromParcelLocked(parcel);
        ((BatteryStatsImpl)object322).mWifiMulticastWakelockTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mWifiOnTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mGlobalWifiRunningTimer.writeSummaryFromParcelLocked(parcel, l2);
        for (n = 0; n < 8; ++n) {
            ((BatteryStatsImpl)object322).mWifiStateTimer[n].writeSummaryFromParcelLocked(parcel, l2);
        }
        for (n = 0; n < 13; ++n) {
            ((BatteryStatsImpl)object322).mWifiSupplStateTimer[n].writeSummaryFromParcelLocked(parcel, l2);
        }
        for (n = 0; n < 5; ++n) {
            ((BatteryStatsImpl)object322).mWifiSignalStrengthsTimer[n].writeSummaryFromParcelLocked(parcel, l2);
        }
        ((BatteryStatsImpl)object322).mWifiActiveTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mWifiActivity.writeSummaryToParcel(parcel);
        for (n = 0; n < 2; ++n) {
            ((BatteryStatsImpl)object322).mGpsSignalQualityTimer[n].writeSummaryFromParcelLocked(parcel, l2);
        }
        ((BatteryStatsImpl)object322).mBluetoothActivity.writeSummaryToParcel(parcel);
        ((BatteryStatsImpl)object322).mModemActivity.writeSummaryToParcel(parcel);
        parcel.writeInt((int)((BatteryStatsImpl)object322).mHasWifiReporting);
        parcel.writeInt((int)((BatteryStatsImpl)object322).mHasBluetoothReporting);
        parcel.writeInt((int)((BatteryStatsImpl)object322).mHasModemReporting);
        parcel.writeInt(((BatteryStatsImpl)object322).mNumConnectivityChange);
        ((BatteryStatsImpl)object322).mFlashlightOnTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mCameraOnTimer.writeSummaryFromParcelLocked(parcel, l2);
        ((BatteryStatsImpl)object322).mBluetoothScanTimer.writeSummaryFromParcelLocked(parcel, l2);
        parcel.writeInt(((BatteryStatsImpl)object322).mRpmStats.size());
        for (Map.Entry<String, SamplingTimer> entry : ((BatteryStatsImpl)object322).mRpmStats.entrySet()) {
            object = entry.getValue();
            if (object != null) {
                parcel.writeInt(1);
                parcel.writeString(entry.getKey());
                ((Timer)object).writeSummaryFromParcelLocked(parcel, l2);
                continue;
            }
            parcel.writeInt(0);
        }
        parcel.writeInt(((BatteryStatsImpl)object322).mScreenOffRpmStats.size());
        for (Map.Entry<String, SamplingTimer> entry : ((BatteryStatsImpl)object322).mScreenOffRpmStats.entrySet()) {
            Timer timer = entry.getValue();
            if (timer != null) {
                parcel.writeInt(1);
                parcel.writeString(entry.getKey());
                timer.writeSummaryFromParcelLocked(parcel, l2);
                continue;
            }
            parcel.writeInt(0);
        }
        parcel.writeInt(((BatteryStatsImpl)object322).mKernelWakelockStats.size());
        for (Map.Entry<String, SamplingTimer> entry : ((BatteryStatsImpl)object322).mKernelWakelockStats.entrySet()) {
            object = entry.getValue();
            if (object != null) {
                parcel.writeInt(1);
                parcel.writeString(entry.getKey());
                ((Timer)object).writeSummaryFromParcelLocked(parcel, l2);
                continue;
            }
            parcel.writeInt(0);
        }
        parcel.writeInt(((BatteryStatsImpl)object322).mWakeupReasonStats.size());
        for (Map.Entry<String, SamplingTimer> entry : ((BatteryStatsImpl)object322).mWakeupReasonStats.entrySet()) {
            SamplingTimer samplingTimer = entry.getValue();
            if (samplingTimer != null) {
                parcel.writeInt(1);
                parcel.writeString(entry.getKey());
                samplingTimer.writeSummaryFromParcelLocked(parcel, l2);
                continue;
            }
            parcel.writeInt(0);
        }
        parcel.writeInt(((BatteryStatsImpl)object322).mKernelMemoryStats.size());
        for (n = 0; n < ((BatteryStatsImpl)object322).mKernelMemoryStats.size(); ++n) {
            Timer timer = ((BatteryStatsImpl)object322).mKernelMemoryStats.valueAt(n);
            if (timer != null) {
                parcel.writeInt(1);
                parcel.writeLong(((BatteryStatsImpl)object322).mKernelMemoryStats.keyAt(n));
                timer.writeSummaryFromParcelLocked(parcel, l2);
                continue;
            }
            parcel.writeInt(0);
        }
        n2 = ((BatteryStatsImpl)object322).mUidStats.size();
        parcel.writeInt(n2);
        n = 0;
        do {
            int n3;
            int n4;
            int n5;
            object322 = this;
            if (n >= n2) break;
            parcel.writeInt(((BatteryStatsImpl)object322).mUidStats.keyAt(n));
            object = ((BatteryStatsImpl)object322).mUidStats.valueAt(n);
            ((Uid)object).mOnBatteryBackgroundTimeBase.writeSummaryToParcel(parcel, l, l2);
            ((Uid)object).mOnBatteryScreenOffBackgroundTimeBase.writeSummaryToParcel(parcel, l, l2);
            if (((Uid)object).mWifiRunningTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mWifiRunningTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mFullWifiLockTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mFullWifiLockTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mWifiScanTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mWifiScanTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            for (n4 = 0; n4 < 5; ++n4) {
                if (((Uid)object).mWifiBatchedScanTimer[n4] != null) {
                    parcel.writeInt(1);
                    ((Uid)object).mWifiBatchedScanTimer[n4].writeSummaryFromParcelLocked(parcel, l2);
                    continue;
                }
                parcel.writeInt(0);
            }
            if (((Uid)object).mWifiMulticastTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mWifiMulticastTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mAudioTurnedOnTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mAudioTurnedOnTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mVideoTurnedOnTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mVideoTurnedOnTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mFlashlightTurnedOnTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mFlashlightTurnedOnTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mCameraTurnedOnTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mCameraTurnedOnTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mForegroundActivityTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mForegroundActivityTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mForegroundServiceTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mForegroundServiceTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mAggregatedPartialWakelockTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mAggregatedPartialWakelockTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mBluetoothScanTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mBluetoothScanTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mBluetoothUnoptimizedScanTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mBluetoothUnoptimizedScanTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mBluetoothScanResultCounter != null) {
                parcel.writeInt(1);
                ((Uid)object).mBluetoothScanResultCounter.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mBluetoothScanResultBgCounter != null) {
                parcel.writeInt(1);
                ((Uid)object).mBluetoothScanResultBgCounter.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(0);
            }
            for (n4 = 0; n4 < 7; ++n4) {
                if (((Uid)object).mProcessStateTimer[n4] != null) {
                    parcel.writeInt(1);
                    ((Uid)object).mProcessStateTimer[n4].writeSummaryFromParcelLocked(parcel, l2);
                    continue;
                }
                parcel.writeInt(0);
            }
            if (((Uid)object).mVibratorOnTimer != null) {
                parcel.writeInt(1);
                ((Uid)object).mVibratorOnTimer.writeSummaryFromParcelLocked(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mUserActivityCounters == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                for (n4 = 0; n4 < Uid.NUM_USER_ACTIVITY_TYPES; ++n4) {
                    ((Uid)object).mUserActivityCounters[n4].writeSummaryFromParcelLocked(parcel);
                }
            }
            if (((Uid)object).mNetworkByteActivityCounters == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                for (n4 = 0; n4 < 10; ++n4) {
                    ((Uid)object).mNetworkByteActivityCounters[n4].writeSummaryFromParcelLocked(parcel);
                    ((Uid)object).mNetworkPacketActivityCounters[n4].writeSummaryFromParcelLocked(parcel);
                }
                ((Uid)object).mMobileRadioActiveTime.writeSummaryFromParcelLocked(parcel);
                ((Uid)object).mMobileRadioActiveCount.writeSummaryFromParcelLocked(parcel);
            }
            ((Uid)object).mUserCpuTime.writeSummaryFromParcelLocked(parcel);
            ((Uid)object).mSystemCpuTime.writeSummaryFromParcelLocked(parcel);
            if (((Uid)object).mCpuClusterSpeedTimesUs != null) {
                parcel.writeInt(1);
                parcel.writeInt(((Uid)object).mCpuClusterSpeedTimesUs.length);
                LongSamplingCounter[][] arrlongSamplingCounter = ((Uid)object).mCpuClusterSpeedTimesUs;
                n5 = arrlongSamplingCounter.length;
                for (n4 = 0; n4 < n5; ++n4) {
                    LongSamplingCounter[] arrlongSamplingCounter2 = arrlongSamplingCounter[n4];
                    if (arrlongSamplingCounter2 != null) {
                        parcel.writeInt(1);
                        parcel.writeInt(arrlongSamplingCounter2.length);
                        for (Object object322 : arrlongSamplingCounter2) {
                            if (object322 != null) {
                                parcel.writeInt(1);
                                ((LongSamplingCounter)object322).writeSummaryFromParcelLocked(parcel);
                                continue;
                            }
                            parcel.writeInt(0);
                        }
                        continue;
                    }
                    parcel.writeInt(0);
                }
            } else {
                parcel.writeInt(0);
            }
            LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, ((Uid)object).mCpuFreqTimeMs);
            LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, ((Uid)object).mScreenOffCpuFreqTimeMs);
            ((Uid)object).mCpuActiveTimeMs.writeSummaryFromParcelLocked(parcel);
            ((Uid)object).mCpuClusterTimesMs.writeSummaryToParcelLocked(parcel);
            if (((Uid)object).mProcStateTimeMs != null) {
                parcel.writeInt(((Uid)object).mProcStateTimeMs.length);
                object322 = ((Uid)object).mProcStateTimeMs;
                n3 = ((LongSamplingCounterArray[])object322).length;
                for (n4 = 0; n4 < n3; ++n4) {
                    LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, object322[n4]);
                }
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mProcStateScreenOffTimeMs != null) {
                parcel.writeInt(((Uid)object).mProcStateScreenOffTimeMs.length);
                object322 = ((Uid)object).mProcStateScreenOffTimeMs;
                n3 = ((LongSamplingCounterArray[])object322).length;
                for (n4 = 0; n4 < n3; ++n4) {
                    LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, object322[n4]);
                }
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mMobileRadioApWakeupCount != null) {
                parcel.writeInt(1);
                ((Uid)object).mMobileRadioApWakeupCount.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (((Uid)object).mWifiRadioApWakeupCount != null) {
                parcel.writeInt(1);
                ((Uid)object).mWifiRadioApWakeupCount.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(0);
            }
            object322 = ((Uid)object).mWakelockStats.getMap();
            n3 = ((ArrayMap)object322).size();
            parcel.writeInt(n3);
            for (n4 = 0; n4 < n3; ++n4) {
                parcel.writeString((String)((ArrayMap)object322).keyAt(n4));
                Uid.Wakelock wakelock = (Uid.Wakelock)((ArrayMap)object322).valueAt(n4);
                if (wakelock.mTimerFull != null) {
                    parcel.writeInt(1);
                    wakelock.mTimerFull.writeSummaryFromParcelLocked(parcel, l2);
                } else {
                    parcel.writeInt(0);
                }
                if (wakelock.mTimerPartial != null) {
                    parcel.writeInt(1);
                    wakelock.mTimerPartial.writeSummaryFromParcelLocked(parcel, l2);
                } else {
                    parcel.writeInt(0);
                }
                if (wakelock.mTimerWindow != null) {
                    parcel.writeInt(1);
                    wakelock.mTimerWindow.writeSummaryFromParcelLocked(parcel, l2);
                } else {
                    parcel.writeInt(0);
                }
                if (wakelock.mTimerDraw != null) {
                    parcel.writeInt(1);
                    wakelock.mTimerDraw.writeSummaryFromParcelLocked(parcel, l2);
                    continue;
                }
                parcel.writeInt(0);
            }
            ArrayMap<String, DualTimer> arrayMap = ((Uid)object).mSyncStats.getMap();
            n5 = arrayMap.size();
            parcel.writeInt(n5);
            for (n4 = 0; n4 < n5; ++n4) {
                parcel.writeString(arrayMap.keyAt(n4));
                arrayMap.valueAt(n4).writeSummaryFromParcelLocked(parcel, l2);
            }
            ArrayMap<String, DualTimer> arrayMap2 = ((Uid)object).mJobStats.getMap();
            n5 = arrayMap2.size();
            parcel.writeInt(n5);
            for (n4 = 0; n4 < n5; ++n4) {
                parcel.writeString(arrayMap2.keyAt(n4));
                arrayMap2.valueAt(n4).writeSummaryFromParcelLocked(parcel, l2);
            }
            ((Uid)object).writeJobCompletionsToParcelLocked(parcel);
            ((Uid)object).mJobsDeferredEventCount.writeSummaryFromParcelLocked(parcel);
            ((Uid)object).mJobsDeferredCount.writeSummaryFromParcelLocked(parcel);
            ((Uid)object).mJobsFreshnessTimeMs.writeSummaryFromParcelLocked(parcel);
            for (n4 = 0; n4 < JOB_FRESHNESS_BUCKETS.length; ++n4) {
                if (((Uid)object).mJobsFreshnessBuckets[n4] != null) {
                    parcel.writeInt(1);
                    ((Uid)object).mJobsFreshnessBuckets[n4].writeSummaryFromParcelLocked(parcel);
                    continue;
                }
                parcel.writeInt(0);
            }
            n5 = ((Uid)object).mSensorStats.size();
            parcel.writeInt(n5);
            for (n4 = 0; n4 < n5; ++n4) {
                parcel.writeInt(((Uid)object).mSensorStats.keyAt(n4));
                Uid.Sensor sensor = ((Uid)object).mSensorStats.valueAt(n4);
                if (sensor.mTimer != null) {
                    parcel.writeInt(1);
                    sensor.mTimer.writeSummaryFromParcelLocked(parcel, l2);
                    continue;
                }
                parcel.writeInt(0);
            }
            n5 = ((Uid)object).mProcessStats.size();
            parcel.writeInt(n5);
            object322 = arrayMap;
            for (n4 = 0; n4 < n5; ++n4) {
                parcel.writeString(((Uid)object).mProcessStats.keyAt(n4));
                Uid.Proc proc = ((Uid)object).mProcessStats.valueAt(n4);
                parcel.writeLong(proc.mUserTime);
                parcel.writeLong(proc.mSystemTime);
                parcel.writeLong(proc.mForegroundTime);
                parcel.writeInt(proc.mStarts);
                parcel.writeInt(proc.mNumCrashes);
                parcel.writeInt(proc.mNumAnrs);
                proc.writeExcessivePowerToParcelLocked(parcel);
            }
            n4 = ((Uid)object).mPackageStats.size();
            parcel.writeInt(n4);
            if (n4 > 0) {
                for (Map.Entry entry : ((Uid)object).mPackageStats.entrySet()) {
                    parcel.writeString((String)entry.getKey());
                    object = (Uid.Pkg)entry.getValue();
                    n5 = ((Uid.Pkg)object).mWakeupAlarms.size();
                    parcel.writeInt(n5);
                    for (n3 = 0; n3 < n5; ++n3) {
                        parcel.writeString(((Uid.Pkg)object).mWakeupAlarms.keyAt(n3));
                        ((Uid.Pkg)object).mWakeupAlarms.valueAt(n3).writeSummaryFromParcelLocked(parcel);
                    }
                    n3 = ((Uid.Pkg)object).mServiceStats.size();
                    parcel.writeInt(n3);
                    for (n5 = 0; n5 < n3; ++n5) {
                        parcel.writeString(((Uid.Pkg)object).mServiceStats.keyAt(n5));
                        Uid.Pkg.Serv serv = ((Uid.Pkg)object).mServiceStats.valueAt(n5);
                        parcel.writeLong(serv.getStartTimeToNowLocked(this.mOnBatteryTimeBase.getUptime(l)));
                        parcel.writeInt(serv.mStarts);
                        parcel.writeInt(serv.mLaunches);
                    }
                }
            }
            ++n;
        } while (true);
    }

    public void writeSyncLocked() {
        this.writeStatsLocked(true);
        this.writeHistoryLocked(true);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelLocked(parcel, true, n);
    }

    void writeToParcelLocked(Parcel parcel, boolean bl, int n) {
        this.pullPendingStateUpdatesLocked();
        this.getStartClockTime();
        long l = this.mClocks.uptimeMillis() * 1000L;
        long l2 = this.mClocks.elapsedRealtime() * 1000L;
        this.mOnBatteryTimeBase.getRealtime(l2);
        this.mOnBatteryScreenOffTimeBase.getRealtime(l2);
        parcel.writeInt(-1166707595);
        this.writeHistoryBuffer(parcel, true, false);
        this.mBatteryStatsHistory.writeToParcel(parcel);
        parcel.writeInt(this.mStartCount);
        parcel.writeLong(this.mStartClockTime);
        parcel.writeString(this.mStartPlatformVersion);
        parcel.writeString(this.mEndPlatformVersion);
        parcel.writeLong(this.mUptime);
        parcel.writeLong(this.mUptimeStart);
        parcel.writeLong(this.mRealtime);
        parcel.writeLong(this.mRealtimeStart);
        parcel.writeInt((int)this.mOnBattery);
        parcel.writeInt(this.mEstimatedBatteryCapacity);
        parcel.writeInt(this.mMinLearnedBatteryCapacity);
        parcel.writeInt(this.mMaxLearnedBatteryCapacity);
        this.mOnBatteryTimeBase.writeToParcel(parcel, l, l2);
        this.mOnBatteryScreenOffTimeBase.writeToParcel(parcel, l, l2);
        this.mScreenOnTimer.writeToParcel(parcel, l2);
        this.mScreenDozeTimer.writeToParcel(parcel, l2);
        for (n = 0; n < 5; ++n) {
            this.mScreenBrightnessTimer[n].writeToParcel(parcel, l2);
        }
        this.mInteractiveTimer.writeToParcel(parcel, l2);
        this.mPowerSaveModeEnabledTimer.writeToParcel(parcel, l2);
        parcel.writeLong(this.mLongestLightIdleTime);
        parcel.writeLong(this.mLongestFullIdleTime);
        this.mDeviceIdleModeLightTimer.writeToParcel(parcel, l2);
        this.mDeviceIdleModeFullTimer.writeToParcel(parcel, l2);
        this.mDeviceLightIdlingTimer.writeToParcel(parcel, l2);
        this.mDeviceIdlingTimer.writeToParcel(parcel, l2);
        this.mPhoneOnTimer.writeToParcel(parcel, l2);
        for (n = 0; n < 5; ++n) {
            this.mPhoneSignalStrengthsTimer[n].writeToParcel(parcel, l2);
        }
        this.mPhoneSignalScanningTimer.writeToParcel(parcel, l2);
        for (n = 0; n < 22; ++n) {
            this.mPhoneDataConnectionsTimer[n].writeToParcel(parcel, l2);
        }
        for (n = 0; n < 10; ++n) {
            this.mNetworkByteActivityCounters[n].writeToParcel(parcel);
            this.mNetworkPacketActivityCounters[n].writeToParcel(parcel);
        }
        this.mMobileRadioActiveTimer.writeToParcel(parcel, l2);
        this.mMobileRadioActivePerAppTimer.writeToParcel(parcel, l2);
        this.mMobileRadioActiveAdjustedTime.writeToParcel(parcel);
        this.mMobileRadioActiveUnknownTime.writeToParcel(parcel);
        this.mMobileRadioActiveUnknownCount.writeToParcel(parcel);
        this.mWifiMulticastWakelockTimer.writeToParcel(parcel, l2);
        this.mWifiOnTimer.writeToParcel(parcel, l2);
        this.mGlobalWifiRunningTimer.writeToParcel(parcel, l2);
        for (n = 0; n < 8; ++n) {
            this.mWifiStateTimer[n].writeToParcel(parcel, l2);
        }
        for (n = 0; n < 13; ++n) {
            this.mWifiSupplStateTimer[n].writeToParcel(parcel, l2);
        }
        for (n = 0; n < 5; ++n) {
            this.mWifiSignalStrengthsTimer[n].writeToParcel(parcel, l2);
        }
        this.mWifiActiveTimer.writeToParcel(parcel, l2);
        this.mWifiActivity.writeToParcel(parcel, 0);
        for (n = 0; n < 2; ++n) {
            this.mGpsSignalQualityTimer[n].writeToParcel(parcel, l2);
        }
        this.mBluetoothActivity.writeToParcel(parcel, 0);
        this.mModemActivity.writeToParcel(parcel, 0);
        parcel.writeInt((int)this.mHasWifiReporting);
        parcel.writeInt((int)this.mHasBluetoothReporting);
        parcel.writeInt((int)this.mHasModemReporting);
        parcel.writeInt(this.mNumConnectivityChange);
        this.mFlashlightOnTimer.writeToParcel(parcel, l2);
        this.mCameraOnTimer.writeToParcel(parcel, l2);
        this.mBluetoothScanTimer.writeToParcel(parcel, l2);
        parcel.writeInt(this.mDischargeUnplugLevel);
        parcel.writeInt(this.mDischargePlugLevel);
        parcel.writeInt(this.mDischargeCurrentLevel);
        parcel.writeInt(this.mCurrentBatteryLevel);
        parcel.writeInt(this.mLowDischargeAmountSinceCharge);
        parcel.writeInt(this.mHighDischargeAmountSinceCharge);
        parcel.writeInt(this.mDischargeAmountScreenOn);
        parcel.writeInt(this.mDischargeAmountScreenOnSinceCharge);
        parcel.writeInt(this.mDischargeAmountScreenOff);
        parcel.writeInt(this.mDischargeAmountScreenOffSinceCharge);
        parcel.writeInt(this.mDischargeAmountScreenDoze);
        parcel.writeInt(this.mDischargeAmountScreenDozeSinceCharge);
        this.mDischargeStepTracker.writeToParcel(parcel);
        this.mChargeStepTracker.writeToParcel(parcel);
        this.mDischargeCounter.writeToParcel(parcel);
        this.mDischargeScreenOffCounter.writeToParcel(parcel);
        this.mDischargeScreenDozeCounter.writeToParcel(parcel);
        this.mDischargeLightDozeCounter.writeToParcel(parcel);
        this.mDischargeDeepDozeCounter.writeToParcel(parcel);
        parcel.writeLong(this.mLastWriteTime);
        parcel.writeInt(this.mRpmStats.size());
        for (Map.Entry<String, SamplingTimer> object22 : this.mRpmStats.entrySet()) {
            SamplingTimer samplingTimer = object22.getValue();
            if (samplingTimer != null) {
                parcel.writeInt(1);
                parcel.writeString(object22.getKey());
                samplingTimer.writeToParcel(parcel, l2);
                continue;
            }
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mScreenOffRpmStats.size());
        for (Map.Entry<String, SamplingTimer> entry : this.mScreenOffRpmStats.entrySet()) {
            SamplingTimer samplingTimer = entry.getValue();
            if (samplingTimer != null) {
                parcel.writeInt(1);
                parcel.writeString(entry.getKey());
                samplingTimer.writeToParcel(parcel, l2);
                continue;
            }
            parcel.writeInt(0);
        }
        if (bl) {
            parcel.writeInt(this.mKernelWakelockStats.size());
            for (Map.Entry<String, SamplingTimer> entry : this.mKernelWakelockStats.entrySet()) {
                SamplingTimer samplingTimer = entry.getValue();
                if (samplingTimer != null) {
                    parcel.writeInt(1);
                    parcel.writeString(entry.getKey());
                    samplingTimer.writeToParcel(parcel, l2);
                    continue;
                }
                parcel.writeInt(0);
            }
            parcel.writeInt(this.mWakeupReasonStats.size());
            for (Map.Entry<String, SamplingTimer> entry : this.mWakeupReasonStats.entrySet()) {
                SamplingTimer samplingTimer = entry.getValue();
                if (samplingTimer != null) {
                    parcel.writeInt(1);
                    parcel.writeString(entry.getKey());
                    samplingTimer.writeToParcel(parcel, l2);
                    continue;
                }
                parcel.writeInt(0);
            }
        } else {
            parcel.writeInt(0);
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mKernelMemoryStats.size());
        for (n = 0; n < this.mKernelMemoryStats.size(); ++n) {
            SamplingTimer samplingTimer = this.mKernelMemoryStats.valueAt(n);
            if (samplingTimer != null) {
                parcel.writeInt(1);
                parcel.writeLong(this.mKernelMemoryStats.keyAt(n));
                samplingTimer.writeToParcel(parcel, l2);
                continue;
            }
            parcel.writeInt(0);
        }
        if (bl) {
            int n2 = this.mUidStats.size();
            parcel.writeInt(n2);
            for (n = 0; n < n2; ++n) {
                parcel.writeInt(this.mUidStats.keyAt(n));
                this.mUidStats.valueAt(n).writeToParcelLocked(parcel, l, l2);
            }
        } else {
            parcel.writeInt(0);
        }
    }

    @Override
    public void writeToParcelWithoutUids(Parcel parcel, int n) {
        this.writeToParcelLocked(parcel, false, n);
    }

    public static class BatchTimer
    extends Timer {
        boolean mInDischarge;
        long mLastAddedDuration;
        long mLastAddedTime;
        final Uid mUid;

        BatchTimer(Clocks clocks, Uid uid, int n, TimeBase timeBase) {
            super(clocks, n, timeBase);
            this.mUid = uid;
            this.mInDischarge = timeBase.isRunning();
        }

        BatchTimer(Clocks clocks, Uid uid, int n, TimeBase timeBase, Parcel parcel) {
            super(clocks, n, timeBase, parcel);
            this.mUid = uid;
            this.mLastAddedTime = parcel.readLong();
            this.mLastAddedDuration = parcel.readLong();
            this.mInDischarge = timeBase.isRunning();
        }

        private long computeOverage(long l) {
            if (this.mLastAddedTime > 0L) {
                return this.mLastAddedDuration - l;
            }
            return 0L;
        }

        private void recomputeLastDuration(long l, boolean bl) {
            long l2 = this.computeOverage(l);
            if (l2 > 0L) {
                if (this.mInDischarge) {
                    this.mTotalTime -= l2;
                }
                if (bl) {
                    this.mLastAddedTime = 0L;
                } else {
                    this.mLastAddedTime = l;
                    this.mLastAddedDuration -= l2;
                }
            }
        }

        public void abortLastDuration(BatteryStatsImpl batteryStatsImpl) {
            this.recomputeLastDuration(this.mClocks.elapsedRealtime() * 1000L, true);
        }

        public void addDuration(BatteryStatsImpl batteryStatsImpl, long l) {
            long l2 = this.mClocks.elapsedRealtime() * 1000L;
            this.recomputeLastDuration(l2, true);
            this.mLastAddedTime = l2;
            this.mLastAddedDuration = 1000L * l;
            if (this.mInDischarge) {
                this.mTotalTime += this.mLastAddedDuration;
                ++this.mCount;
            }
        }

        @Override
        protected int computeCurrentCountLocked() {
            return this.mCount;
        }

        @Override
        protected long computeRunTimeLocked(long l) {
            l = this.computeOverage(this.mClocks.elapsedRealtime() * 1000L);
            if (l > 0L) {
                this.mTotalTime = l;
                return l;
            }
            return this.mTotalTime;
        }

        @Override
        public void logState(Printer printer, String string2) {
            super.logState(printer, string2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("mLastAddedTime=");
            stringBuilder.append(this.mLastAddedTime);
            stringBuilder.append(" mLastAddedDuration=");
            stringBuilder.append(this.mLastAddedDuration);
            printer.println(stringBuilder.toString());
        }

        @Override
        public void onTimeStarted(long l, long l2, long l3) {
            this.recomputeLastDuration(l, false);
            this.mInDischarge = true;
            if (this.mLastAddedTime == l) {
                this.mTotalTime += this.mLastAddedDuration;
            }
            super.onTimeStarted(l, l2, l3);
        }

        @Override
        public void onTimeStopped(long l, long l2, long l3) {
            this.recomputeLastDuration(this.mClocks.elapsedRealtime() * 1000L, false);
            this.mInDischarge = false;
            super.onTimeStopped(l, l2, l3);
        }

        @Override
        public boolean reset(boolean bl) {
            long l = this.mClocks.elapsedRealtime() * 1000L;
            boolean bl2 = true;
            this.recomputeLastDuration(l, true);
            boolean bl3 = this.mLastAddedTime == l;
            bl = !bl3 && bl;
            super.reset(bl);
            bl = !bl3 ? bl2 : false;
            return bl;
        }

        @Override
        public void writeToParcel(Parcel parcel, long l) {
            super.writeToParcel(parcel, l);
            parcel.writeLong(this.mLastAddedTime);
            parcel.writeLong(this.mLastAddedDuration);
        }
    }

    public static interface BatteryCallback {
        public void batteryNeedsCpuUpdate();

        public void batteryPowerChanged(boolean var1);

        public void batterySendBroadcast(Intent var1);

        public void batteryStatsReset();
    }

    private final class BluetoothActivityInfoCache {
        long energy;
        long idleTimeMs;
        long rxTimeMs;
        long txTimeMs;
        SparseLongArray uidRxBytes = new SparseLongArray();
        SparseLongArray uidTxBytes = new SparseLongArray();

        private BluetoothActivityInfoCache() {
        }

        void set(BluetoothActivityEnergyInfo arruidTraffic) {
            this.idleTimeMs = arruidTraffic.getControllerIdleTimeMillis();
            this.rxTimeMs = arruidTraffic.getControllerRxTimeMillis();
            this.txTimeMs = arruidTraffic.getControllerTxTimeMillis();
            this.energy = arruidTraffic.getControllerEnergyUsed();
            if (arruidTraffic.getUidTraffic() != null) {
                for (UidTraffic uidTraffic : arruidTraffic.getUidTraffic()) {
                    this.uidRxBytes.put(uidTraffic.getUid(), uidTraffic.getRxBytes());
                    this.uidTxBytes.put(uidTraffic.getUid(), uidTraffic.getTxBytes());
                }
            }
        }
    }

    public static interface Clocks {
        public long elapsedRealtime();

        public long uptimeMillis();
    }

    @VisibleForTesting
    public final class Constants
    extends ContentObserver {
        private static final int DEFAULT_BATTERY_CHARGED_DELAY_MS = 900000;
        private static final long DEFAULT_BATTERY_LEVEL_COLLECTION_DELAY_MS = 300000L;
        private static final long DEFAULT_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = 600000L;
        private static final long DEFAULT_KERNEL_UID_READERS_THROTTLE_TIME = 1000L;
        private static final int DEFAULT_MAX_HISTORY_BUFFER_KB = 128;
        private static final int DEFAULT_MAX_HISTORY_BUFFER_LOW_RAM_DEVICE_KB = 64;
        private static final int DEFAULT_MAX_HISTORY_FILES = 32;
        private static final int DEFAULT_MAX_HISTORY_FILES_LOW_RAM_DEVICE = 64;
        private static final long DEFAULT_PROC_STATE_CPU_TIMES_READ_DELAY_MS = 5000L;
        private static final boolean DEFAULT_TRACK_CPU_ACTIVE_CLUSTER_TIME = true;
        private static final boolean DEFAULT_TRACK_CPU_TIMES_BY_PROC_STATE = false;
        private static final long DEFAULT_UID_REMOVE_DELAY_MS = 300000L;
        public static final String KEY_BATTERY_CHARGED_DELAY_MS = "battery_charged_delay_ms";
        public static final String KEY_BATTERY_LEVEL_COLLECTION_DELAY_MS = "battery_level_collection_delay_ms";
        public static final String KEY_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = "external_stats_collection_rate_limit_ms";
        public static final String KEY_KERNEL_UID_READERS_THROTTLE_TIME = "kernel_uid_readers_throttle_time";
        public static final String KEY_MAX_HISTORY_BUFFER_KB = "max_history_buffer_kb";
        public static final String KEY_MAX_HISTORY_FILES = "max_history_files";
        public static final String KEY_PROC_STATE_CPU_TIMES_READ_DELAY_MS = "proc_state_cpu_times_read_delay_ms";
        public static final String KEY_TRACK_CPU_ACTIVE_CLUSTER_TIME = "track_cpu_active_cluster_time";
        public static final String KEY_TRACK_CPU_TIMES_BY_PROC_STATE = "track_cpu_times_by_proc_state";
        public static final String KEY_UID_REMOVE_DELAY_MS = "uid_remove_delay_ms";
        public int BATTERY_CHARGED_DELAY_MS;
        public long BATTERY_LEVEL_COLLECTION_DELAY_MS;
        public long EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS;
        public long KERNEL_UID_READERS_THROTTLE_TIME;
        public int MAX_HISTORY_BUFFER;
        public int MAX_HISTORY_FILES;
        public long PROC_STATE_CPU_TIMES_READ_DELAY_MS;
        public boolean TRACK_CPU_ACTIVE_CLUSTER_TIME;
        public boolean TRACK_CPU_TIMES_BY_PROC_STATE;
        public long UID_REMOVE_DELAY_MS;
        private final KeyValueListParser mParser;
        private ContentResolver mResolver;

        public Constants(Handler handler) {
            super(handler);
            this.TRACK_CPU_TIMES_BY_PROC_STATE = false;
            this.TRACK_CPU_ACTIVE_CLUSTER_TIME = true;
            this.PROC_STATE_CPU_TIMES_READ_DELAY_MS = 5000L;
            this.UID_REMOVE_DELAY_MS = 300000L;
            this.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = 600000L;
            this.BATTERY_LEVEL_COLLECTION_DELAY_MS = 300000L;
            this.BATTERY_CHARGED_DELAY_MS = 900000;
            this.mParser = new KeyValueListParser(',');
            if (ActivityManager.isLowRamDeviceStatic()) {
                this.MAX_HISTORY_FILES = 64;
                this.MAX_HISTORY_BUFFER = 65536;
            } else {
                this.MAX_HISTORY_FILES = 32;
                this.MAX_HISTORY_BUFFER = 131072;
            }
        }

        private void updateBatteryChargedDelayMsLocked() {
            int n = Settings.Global.getInt(this.mResolver, "battery_charging_state_update_delay", -1);
            if (n < 0) {
                n = this.mParser.getInt(KEY_BATTERY_CHARGED_DELAY_MS, 900000);
            }
            this.BATTERY_CHARGED_DELAY_MS = n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void updateConstants() {
            BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
            synchronized (batteryStatsImpl) {
                try {
                    try {
                        this.mParser.setString(Settings.Global.getString(this.mResolver, "battery_stats_constants"));
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        Slog.e(BatteryStatsImpl.TAG, "Bad batterystats settings", illegalArgumentException);
                    }
                    this.updateTrackCpuTimesByProcStateLocked(this.TRACK_CPU_TIMES_BY_PROC_STATE, this.mParser.getBoolean(KEY_TRACK_CPU_TIMES_BY_PROC_STATE, false));
                    this.TRACK_CPU_ACTIVE_CLUSTER_TIME = this.mParser.getBoolean(KEY_TRACK_CPU_ACTIVE_CLUSTER_TIME, true);
                    this.updateProcStateCpuTimesReadDelayMs(this.PROC_STATE_CPU_TIMES_READ_DELAY_MS, this.mParser.getLong(KEY_PROC_STATE_CPU_TIMES_READ_DELAY_MS, 5000L));
                    this.updateKernelUidReadersThrottleTime(this.KERNEL_UID_READERS_THROTTLE_TIME, this.mParser.getLong(KEY_KERNEL_UID_READERS_THROTTLE_TIME, 1000L));
                    this.updateUidRemoveDelay(this.mParser.getLong(KEY_UID_REMOVE_DELAY_MS, 300000L));
                    this.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = this.mParser.getLong(KEY_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS, 600000L);
                    this.BATTERY_LEVEL_COLLECTION_DELAY_MS = this.mParser.getLong(KEY_BATTERY_LEVEL_COLLECTION_DELAY_MS, 300000L);
                    KeyValueListParser keyValueListParser = this.mParser;
                    boolean bl = ActivityManager.isLowRamDeviceStatic();
                    int n = 64;
                    int n2 = bl ? 64 : 32;
                    this.MAX_HISTORY_FILES = keyValueListParser.getInt(KEY_MAX_HISTORY_FILES, n2);
                    keyValueListParser = this.mParser;
                    n2 = ActivityManager.isLowRamDeviceStatic() ? n : 128;
                    this.MAX_HISTORY_BUFFER = keyValueListParser.getInt(KEY_MAX_HISTORY_BUFFER_KB, n2) * 1024;
                    this.updateBatteryChargedDelayMsLocked();
                    return;
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        private void updateKernelUidReadersThrottleTime(long l, long l2) {
            this.KERNEL_UID_READERS_THROTTLE_TIME = l2;
            if (l != l2) {
                BatteryStatsImpl.this.mCpuUidUserSysTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                BatteryStatsImpl.this.mCpuUidFreqTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                BatteryStatsImpl.this.mCpuUidActiveTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                BatteryStatsImpl.this.mCpuUidClusterTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
            }
        }

        private void updateProcStateCpuTimesReadDelayMs(long l, long l2) {
            this.PROC_STATE_CPU_TIMES_READ_DELAY_MS = l2;
            if (l != l2) {
                BatteryStatsImpl.this.mNumSingleUidCpuTimeReads = 0L;
                BatteryStatsImpl.this.mNumBatchedSingleUidCpuTimeReads = 0L;
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                batteryStatsImpl.mCpuTimeReadsTrackingStartTime = batteryStatsImpl.mClocks.uptimeMillis();
            }
        }

        private void updateTrackCpuTimesByProcStateLocked(boolean bl, boolean bl2) {
            this.TRACK_CPU_TIMES_BY_PROC_STATE = bl2;
            if (bl2 && !bl) {
                BatteryStatsImpl.this.mIsPerProcessStateCpuDataStale = true;
                BatteryStatsImpl.this.mExternalSync.scheduleCpuSyncDueToSettingChange();
                BatteryStatsImpl.this.mNumSingleUidCpuTimeReads = 0L;
                BatteryStatsImpl.this.mNumBatchedSingleUidCpuTimeReads = 0L;
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                batteryStatsImpl.mCpuTimeReadsTrackingStartTime = batteryStatsImpl.mClocks.uptimeMillis();
            }
        }

        private void updateUidRemoveDelay(long l) {
            this.UID_REMOVE_DELAY_MS = l;
            BatteryStatsImpl.this.clearPendingRemovedUids();
        }

        public void dumpLocked(PrintWriter printWriter) {
            printWriter.print(KEY_TRACK_CPU_TIMES_BY_PROC_STATE);
            printWriter.print("=");
            printWriter.println(this.TRACK_CPU_TIMES_BY_PROC_STATE);
            printWriter.print(KEY_TRACK_CPU_ACTIVE_CLUSTER_TIME);
            printWriter.print("=");
            printWriter.println(this.TRACK_CPU_ACTIVE_CLUSTER_TIME);
            printWriter.print(KEY_PROC_STATE_CPU_TIMES_READ_DELAY_MS);
            printWriter.print("=");
            printWriter.println(this.PROC_STATE_CPU_TIMES_READ_DELAY_MS);
            printWriter.print(KEY_KERNEL_UID_READERS_THROTTLE_TIME);
            printWriter.print("=");
            printWriter.println(this.KERNEL_UID_READERS_THROTTLE_TIME);
            printWriter.print(KEY_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS);
            printWriter.print("=");
            printWriter.println(this.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS);
            printWriter.print(KEY_BATTERY_LEVEL_COLLECTION_DELAY_MS);
            printWriter.print("=");
            printWriter.println(this.BATTERY_LEVEL_COLLECTION_DELAY_MS);
            printWriter.print(KEY_MAX_HISTORY_FILES);
            printWriter.print("=");
            printWriter.println(this.MAX_HISTORY_FILES);
            printWriter.print(KEY_MAX_HISTORY_BUFFER_KB);
            printWriter.print("=");
            printWriter.println(this.MAX_HISTORY_BUFFER / 1024);
            printWriter.print(KEY_BATTERY_CHARGED_DELAY_MS);
            printWriter.print("=");
            printWriter.println(this.BATTERY_CHARGED_DELAY_MS);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onChange(boolean bl, Uri parcelable) {
            if (parcelable.equals(Settings.Global.getUriFor("battery_charging_state_update_delay"))) {
                parcelable = BatteryStatsImpl.this;
                synchronized (parcelable) {
                    this.updateBatteryChargedDelayMsLocked();
                    return;
                }
            }
            this.updateConstants();
        }

        public void startObserving(ContentResolver contentResolver) {
            this.mResolver = contentResolver;
            this.mResolver.registerContentObserver(Settings.Global.getUriFor("battery_stats_constants"), false, this);
            this.mResolver.registerContentObserver(Settings.Global.getUriFor("battery_charging_state_update_delay"), false, this);
            this.updateConstants();
        }
    }

    public static class ControllerActivityCounterImpl
    extends BatteryStats.ControllerActivityCounter
    implements Parcelable {
        private final LongSamplingCounter mIdleTimeMillis;
        private final LongSamplingCounter mMonitoredRailChargeConsumedMaMs;
        private final LongSamplingCounter mPowerDrainMaMs;
        private final LongSamplingCounter mRxTimeMillis;
        private final LongSamplingCounter mScanTimeMillis;
        private final LongSamplingCounter mSleepTimeMillis;
        private final LongSamplingCounter[] mTxTimeMillis;

        public ControllerActivityCounterImpl(TimeBase timeBase, int n) {
            this.mIdleTimeMillis = new LongSamplingCounter(timeBase);
            this.mScanTimeMillis = new LongSamplingCounter(timeBase);
            this.mSleepTimeMillis = new LongSamplingCounter(timeBase);
            this.mRxTimeMillis = new LongSamplingCounter(timeBase);
            this.mTxTimeMillis = new LongSamplingCounter[n];
            for (int i = 0; i < n; ++i) {
                this.mTxTimeMillis[i] = new LongSamplingCounter(timeBase);
            }
            this.mPowerDrainMaMs = new LongSamplingCounter(timeBase);
            this.mMonitoredRailChargeConsumedMaMs = new LongSamplingCounter(timeBase);
        }

        public ControllerActivityCounterImpl(TimeBase timeBase, int n, Parcel parcel) {
            this.mIdleTimeMillis = new LongSamplingCounter(timeBase, parcel);
            this.mScanTimeMillis = new LongSamplingCounter(timeBase, parcel);
            this.mSleepTimeMillis = new LongSamplingCounter(timeBase, parcel);
            this.mRxTimeMillis = new LongSamplingCounter(timeBase, parcel);
            if (parcel.readInt() == n) {
                this.mTxTimeMillis = new LongSamplingCounter[n];
                for (int i = 0; i < n; ++i) {
                    this.mTxTimeMillis[i] = new LongSamplingCounter(timeBase, parcel);
                }
                this.mPowerDrainMaMs = new LongSamplingCounter(timeBase, parcel);
                this.mMonitoredRailChargeConsumedMaMs = new LongSamplingCounter(timeBase, parcel);
                return;
            }
            throw new ParcelFormatException("inconsistent tx state lengths");
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void detach() {
            this.mIdleTimeMillis.detach();
            this.mScanTimeMillis.detach();
            this.mSleepTimeMillis.detach();
            this.mRxTimeMillis.detach();
            LongSamplingCounter[] arrlongSamplingCounter = this.mTxTimeMillis;
            int n = arrlongSamplingCounter.length;
            for (int i = 0; i < n; ++i) {
                arrlongSamplingCounter[i].detach();
            }
            this.mPowerDrainMaMs.detach();
            this.mMonitoredRailChargeConsumedMaMs.detach();
        }

        @Override
        public LongSamplingCounter getIdleTimeCounter() {
            return this.mIdleTimeMillis;
        }

        @Override
        public LongSamplingCounter getMonitoredRailChargeConsumedMaMs() {
            return this.mMonitoredRailChargeConsumedMaMs;
        }

        @Override
        public LongSamplingCounter getPowerCounter() {
            return this.mPowerDrainMaMs;
        }

        @Override
        public LongSamplingCounter getRxTimeCounter() {
            return this.mRxTimeMillis;
        }

        @Override
        public LongSamplingCounter getScanTimeCounter() {
            return this.mScanTimeMillis;
        }

        @Override
        public LongSamplingCounter getSleepTimeCounter() {
            return this.mSleepTimeMillis;
        }

        public LongSamplingCounter[] getTxTimeCounters() {
            return this.mTxTimeMillis;
        }

        public void readSummaryFromParcel(Parcel parcel) {
            this.mIdleTimeMillis.readSummaryFromParcelLocked(parcel);
            this.mScanTimeMillis.readSummaryFromParcelLocked(parcel);
            this.mSleepTimeMillis.readSummaryFromParcelLocked(parcel);
            this.mRxTimeMillis.readSummaryFromParcelLocked(parcel);
            int n = parcel.readInt();
            LongSamplingCounter[] arrlongSamplingCounter = this.mTxTimeMillis;
            if (n == arrlongSamplingCounter.length) {
                int n2 = arrlongSamplingCounter.length;
                for (n = 0; n < n2; ++n) {
                    arrlongSamplingCounter[n].readSummaryFromParcelLocked(parcel);
                }
                this.mPowerDrainMaMs.readSummaryFromParcelLocked(parcel);
                this.mMonitoredRailChargeConsumedMaMs.readSummaryFromParcelLocked(parcel);
                return;
            }
            throw new ParcelFormatException("inconsistent tx state lengths");
        }

        public void reset(boolean bl) {
            this.mIdleTimeMillis.reset(bl);
            this.mScanTimeMillis.reset(bl);
            this.mSleepTimeMillis.reset(bl);
            this.mRxTimeMillis.reset(bl);
            LongSamplingCounter[] arrlongSamplingCounter = this.mTxTimeMillis;
            int n = arrlongSamplingCounter.length;
            for (int i = 0; i < n; ++i) {
                arrlongSamplingCounter[i].reset(bl);
            }
            this.mPowerDrainMaMs.reset(bl);
            this.mMonitoredRailChargeConsumedMaMs.reset(bl);
        }

        public void writeSummaryToParcel(Parcel parcel) {
            this.mIdleTimeMillis.writeSummaryFromParcelLocked(parcel);
            this.mScanTimeMillis.writeSummaryFromParcelLocked(parcel);
            this.mSleepTimeMillis.writeSummaryFromParcelLocked(parcel);
            this.mRxTimeMillis.writeSummaryFromParcelLocked(parcel);
            parcel.writeInt(this.mTxTimeMillis.length);
            LongSamplingCounter[] arrlongSamplingCounter = this.mTxTimeMillis;
            int n = arrlongSamplingCounter.length;
            for (int i = 0; i < n; ++i) {
                arrlongSamplingCounter[i].writeSummaryFromParcelLocked(parcel);
            }
            this.mPowerDrainMaMs.writeSummaryFromParcelLocked(parcel);
            this.mMonitoredRailChargeConsumedMaMs.writeSummaryFromParcelLocked(parcel);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            this.mIdleTimeMillis.writeToParcel(parcel);
            this.mScanTimeMillis.writeToParcel(parcel);
            this.mSleepTimeMillis.writeToParcel(parcel);
            this.mRxTimeMillis.writeToParcel(parcel);
            parcel.writeInt(this.mTxTimeMillis.length);
            LongSamplingCounter[] arrlongSamplingCounter = this.mTxTimeMillis;
            int n2 = arrlongSamplingCounter.length;
            for (n = 0; n < n2; ++n) {
                arrlongSamplingCounter[n].writeToParcel(parcel);
            }
            this.mPowerDrainMaMs.writeToParcel(parcel);
            this.mMonitoredRailChargeConsumedMaMs.writeToParcel(parcel);
        }
    }

    public static class Counter
    extends BatteryStats.Counter
    implements TimeBaseObs {
        @UnsupportedAppUsage
        final AtomicInteger mCount = new AtomicInteger();
        final TimeBase mTimeBase;

        public Counter(TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public Counter(TimeBase timeBase, Parcel parcel) {
            this.mTimeBase = timeBase;
            this.mCount.set(parcel.readInt());
            timeBase.add(this);
        }

        public static Counter readCounterFromParcel(TimeBase timeBase, Parcel parcel) {
            if (parcel.readInt() == 0) {
                return null;
            }
            return new Counter(timeBase, parcel);
        }

        public static void writeCounterToParcel(Parcel parcel, Counter counter) {
            if (counter == null) {
                parcel.writeInt(0);
                return;
            }
            parcel.writeInt(1);
            counter.writeToParcel(parcel);
        }

        void addAtomic(int n) {
            if (this.mTimeBase.isRunning()) {
                this.mCount.addAndGet(n);
            }
        }

        @Override
        public void detach() {
            this.mTimeBase.remove(this);
        }

        @Override
        public int getCountLocked(int n) {
            return this.mCount.get();
        }

        @Override
        public void logState(Printer printer, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("mCount=");
            stringBuilder.append(this.mCount.get());
            printer.println(stringBuilder.toString());
        }

        @Override
        public void onTimeStarted(long l, long l2, long l3) {
        }

        @Override
        public void onTimeStopped(long l, long l2, long l3) {
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public void readSummaryFromParcelLocked(Parcel parcel) {
            this.mCount.set(parcel.readInt());
        }

        @Override
        public boolean reset(boolean bl) {
            this.mCount.set(0);
            if (bl) {
                this.detach();
            }
            return true;
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public void stepAtomic() {
            if (this.mTimeBase.isRunning()) {
                this.mCount.incrementAndGet();
            }
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public void writeSummaryFromParcelLocked(Parcel parcel) {
            parcel.writeInt(this.mCount.get());
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.mCount.get());
        }
    }

    public static class DualTimer
    extends DurationTimer {
        private final DurationTimer mSubTimer;

        public DualTimer(Clocks clocks, Uid uid, int n, ArrayList<StopwatchTimer> arrayList, TimeBase timeBase, TimeBase timeBase2) {
            super(clocks, uid, n, arrayList, timeBase);
            this.mSubTimer = new DurationTimer(clocks, uid, n, null, timeBase2);
        }

        public DualTimer(Clocks clocks, Uid uid, int n, ArrayList<StopwatchTimer> arrayList, TimeBase timeBase, TimeBase timeBase2, Parcel parcel) {
            super(clocks, uid, n, arrayList, timeBase, parcel);
            this.mSubTimer = new DurationTimer(clocks, uid, n, null, timeBase2, parcel);
        }

        @Override
        public void detach() {
            this.mSubTimer.detach();
            super.detach();
        }

        @Override
        public DurationTimer getSubTimer() {
            return this.mSubTimer;
        }

        @Override
        public void readSummaryFromParcelLocked(Parcel parcel) {
            super.readSummaryFromParcelLocked(parcel);
            this.mSubTimer.readSummaryFromParcelLocked(parcel);
        }

        @Override
        public boolean reset(boolean bl) {
            DurationTimer durationTimer = this.mSubTimer;
            boolean bl2 = false;
            if (!(false | durationTimer.reset(false) ^ true | super.reset(bl) ^ true)) {
                bl2 = true;
            }
            return bl2;
        }

        @Override
        public void startRunningLocked(long l) {
            super.startRunningLocked(l);
            this.mSubTimer.startRunningLocked(l);
        }

        @Override
        public void stopAllRunningLocked(long l) {
            super.stopAllRunningLocked(l);
            this.mSubTimer.stopAllRunningLocked(l);
        }

        @Override
        public void stopRunningLocked(long l) {
            super.stopRunningLocked(l);
            this.mSubTimer.stopRunningLocked(l);
        }

        @Override
        public void writeSummaryFromParcelLocked(Parcel parcel, long l) {
            super.writeSummaryFromParcelLocked(parcel, l);
            this.mSubTimer.writeSummaryFromParcelLocked(parcel, l);
        }

        @Override
        public void writeToParcel(Parcel parcel, long l) {
            super.writeToParcel(parcel, l);
            this.mSubTimer.writeToParcel(parcel, l);
        }
    }

    public static class DurationTimer
    extends StopwatchTimer {
        long mCurrentDurationMs;
        long mMaxDurationMs;
        long mStartTimeMs = -1L;
        long mTotalDurationMs;

        public DurationTimer(Clocks clocks, Uid uid, int n, ArrayList<StopwatchTimer> arrayList, TimeBase timeBase) {
            super(clocks, uid, n, arrayList, timeBase);
        }

        public DurationTimer(Clocks clocks, Uid uid, int n, ArrayList<StopwatchTimer> arrayList, TimeBase timeBase, Parcel parcel) {
            super(clocks, uid, n, arrayList, timeBase, parcel);
            this.mMaxDurationMs = parcel.readLong();
            this.mTotalDurationMs = parcel.readLong();
            this.mCurrentDurationMs = parcel.readLong();
        }

        @Override
        public long getCurrentDurationMsLocked(long l) {
            long l2;
            long l3 = l2 = this.mCurrentDurationMs;
            if (this.mNesting > 0) {
                l3 = l2;
                if (this.mTimeBase.isRunning()) {
                    l3 = l2 + (this.mTimeBase.getRealtime(l * 1000L) / 1000L - this.mStartTimeMs);
                }
            }
            return l3;
        }

        @Override
        public long getMaxDurationMsLocked(long l) {
            if (this.mNesting > 0 && (l = this.getCurrentDurationMsLocked(l)) > this.mMaxDurationMs) {
                return l;
            }
            return this.mMaxDurationMs;
        }

        @Override
        public long getTotalDurationMsLocked(long l) {
            return this.mTotalDurationMs + this.getCurrentDurationMsLocked(l);
        }

        @Override
        public void logState(Printer printer, String string2) {
            super.logState(printer, string2);
        }

        @Override
        public void onTimeStarted(long l, long l2, long l3) {
            super.onTimeStarted(l, l2, l3);
            if (this.mNesting > 0) {
                this.mStartTimeMs = l3 / 1000L;
            }
        }

        @Override
        public void onTimeStopped(long l, long l2, long l3) {
            super.onTimeStopped(l, l2, l3);
            if (this.mNesting > 0) {
                this.mCurrentDurationMs += l3 / 1000L - this.mStartTimeMs;
            }
            this.mStartTimeMs = -1L;
        }

        @Override
        public void readSummaryFromParcelLocked(Parcel parcel) {
            super.readSummaryFromParcelLocked(parcel);
            this.mMaxDurationMs = parcel.readLong();
            this.mTotalDurationMs = parcel.readLong();
            this.mStartTimeMs = -1L;
            this.mCurrentDurationMs = 0L;
        }

        @Override
        public boolean reset(boolean bl) {
            bl = super.reset(bl);
            this.mMaxDurationMs = 0L;
            this.mTotalDurationMs = 0L;
            this.mCurrentDurationMs = 0L;
            this.mStartTimeMs = this.mNesting > 0 ? this.mTimeBase.getRealtime(this.mClocks.elapsedRealtime() * 1000L) / 1000L : -1L;
            return bl;
        }

        @Override
        public void startRunningLocked(long l) {
            super.startRunningLocked(l);
            if (this.mNesting == 1 && this.mTimeBase.isRunning()) {
                this.mStartTimeMs = this.mTimeBase.getRealtime(l * 1000L) / 1000L;
            }
        }

        @Override
        public void stopRunningLocked(long l) {
            if (this.mNesting == 1) {
                long l2 = this.getCurrentDurationMsLocked(l);
                this.mTotalDurationMs += l2;
                if (l2 > this.mMaxDurationMs) {
                    this.mMaxDurationMs = l2;
                }
                this.mStartTimeMs = -1L;
                this.mCurrentDurationMs = 0L;
            }
            super.stopRunningLocked(l);
        }

        @Override
        public void writeSummaryFromParcelLocked(Parcel parcel, long l) {
            super.writeSummaryFromParcelLocked(parcel, l);
            parcel.writeLong(this.getMaxDurationMsLocked(l / 1000L));
            parcel.writeLong(this.getTotalDurationMsLocked(l / 1000L));
        }

        @Override
        public void writeToParcel(Parcel parcel, long l) {
            super.writeToParcel(parcel, l);
            parcel.writeLong(this.getMaxDurationMsLocked(l / 1000L));
            parcel.writeLong(this.mTotalDurationMs);
            parcel.writeLong(this.getCurrentDurationMsLocked(l / 1000L));
        }
    }

    public static interface ExternalStatsSync {
        public static final int UPDATE_ALL = 31;
        public static final int UPDATE_BT = 8;
        public static final int UPDATE_CPU = 1;
        public static final int UPDATE_RADIO = 4;
        public static final int UPDATE_RPM = 16;
        public static final int UPDATE_WIFI = 2;

        public void cancelCpuSyncDueToWakelockChange();

        public Future<?> scheduleCopyFromAllUidsCpuTimes(boolean var1, boolean var2);

        public Future<?> scheduleCpuSyncDueToRemovedUid(int var1);

        public Future<?> scheduleCpuSyncDueToScreenStateChange(boolean var1, boolean var2);

        public Future<?> scheduleCpuSyncDueToSettingChange();

        public Future<?> scheduleCpuSyncDueToWakelockChange(long var1);

        public Future<?> scheduleReadProcStateCpuTimes(boolean var1, boolean var2, long var3);

        public Future<?> scheduleSync(String var1, int var2);

        public Future<?> scheduleSyncDueToBatteryLevelChange(long var1);
    }

    @VisibleForTesting
    public static class LongSamplingCounter
    extends BatteryStats.LongCounter
    implements TimeBaseObs {
        private long mCount;
        final TimeBase mTimeBase;

        public LongSamplingCounter(TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public LongSamplingCounter(TimeBase timeBase, Parcel parcel) {
            this.mTimeBase = timeBase;
            this.mCount = parcel.readLong();
            timeBase.add(this);
        }

        public void addCountLocked(long l) {
            this.addCountLocked(l, this.mTimeBase.isRunning());
        }

        public void addCountLocked(long l, boolean bl) {
            if (bl) {
                this.mCount += l;
            }
        }

        @Override
        public void detach() {
            this.mTimeBase.remove(this);
        }

        @Override
        public long getCountLocked(int n) {
            return this.mCount;
        }

        @Override
        public void logState(Printer printer, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("mCount=");
            stringBuilder.append(this.mCount);
            printer.println(stringBuilder.toString());
        }

        @Override
        public void onTimeStarted(long l, long l2, long l3) {
        }

        @Override
        public void onTimeStopped(long l, long l2, long l3) {
        }

        public void readSummaryFromParcelLocked(Parcel parcel) {
            this.mCount = parcel.readLong();
        }

        @Override
        public boolean reset(boolean bl) {
            this.mCount = 0L;
            if (bl) {
                this.detach();
            }
            return true;
        }

        public void writeSummaryFromParcelLocked(Parcel parcel) {
            parcel.writeLong(this.mCount);
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeLong(this.mCount);
        }
    }

    @VisibleForTesting
    public static class LongSamplingCounterArray
    extends BatteryStats.LongCounterArray
    implements TimeBaseObs {
        public long[] mCounts;
        final TimeBase mTimeBase;

        public LongSamplingCounterArray(TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        private LongSamplingCounterArray(TimeBase timeBase, Parcel parcel) {
            this.mTimeBase = timeBase;
            this.mCounts = parcel.createLongArray();
            timeBase.add(this);
        }

        public static LongSamplingCounterArray readFromParcel(Parcel parcel, TimeBase timeBase) {
            if (parcel.readInt() != 0) {
                return new LongSamplingCounterArray(timeBase, parcel);
            }
            return null;
        }

        public static LongSamplingCounterArray readSummaryFromParcelLocked(Parcel parcel, TimeBase object) {
            if (parcel.readInt() != 0) {
                object = new LongSamplingCounterArray((TimeBase)object);
                LongSamplingCounterArray.super.readSummaryFromParcelLocked(parcel);
                return object;
            }
            return null;
        }

        private void readSummaryFromParcelLocked(Parcel parcel) {
            this.mCounts = parcel.createLongArray();
        }

        private void writeSummaryToParcelLocked(Parcel parcel) {
            parcel.writeLongArray(this.mCounts);
        }

        public static void writeSummaryToParcelLocked(Parcel parcel, LongSamplingCounterArray longSamplingCounterArray) {
            if (longSamplingCounterArray != null) {
                parcel.writeInt(1);
                longSamplingCounterArray.writeSummaryToParcelLocked(parcel);
            } else {
                parcel.writeInt(0);
            }
        }

        private void writeToParcel(Parcel parcel) {
            parcel.writeLongArray(this.mCounts);
        }

        public static void writeToParcel(Parcel parcel, LongSamplingCounterArray longSamplingCounterArray) {
            if (longSamplingCounterArray != null) {
                parcel.writeInt(1);
                longSamplingCounterArray.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
        }

        public void addCountLocked(long[] arrl) {
            this.addCountLocked(arrl, this.mTimeBase.isRunning());
        }

        public void addCountLocked(long[] arrl, boolean bl) {
            if (arrl == null) {
                return;
            }
            if (bl) {
                if (this.mCounts == null) {
                    this.mCounts = new long[arrl.length];
                }
                for (int i = 0; i < arrl.length; ++i) {
                    long[] arrl2 = this.mCounts;
                    arrl2[i] = arrl2[i] + arrl[i];
                }
            }
        }

        @Override
        public void detach() {
            this.mTimeBase.remove(this);
        }

        @Override
        public long[] getCountsLocked(int n) {
            Object object = this.mCounts;
            object = object == null ? null : Arrays.copyOf(object, ((long[])object).length);
            return object;
        }

        public int getSize() {
            long[] arrl = this.mCounts;
            int n = arrl == null ? 0 : arrl.length;
            return n;
        }

        @Override
        public void logState(Printer printer, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("mCounts=");
            stringBuilder.append(Arrays.toString(this.mCounts));
            printer.println(stringBuilder.toString());
        }

        @Override
        public void onTimeStarted(long l, long l2, long l3) {
        }

        @Override
        public void onTimeStopped(long l, long l2, long l3) {
        }

        @Override
        public boolean reset(boolean bl) {
            long[] arrl = this.mCounts;
            if (arrl != null) {
                Arrays.fill(arrl, 0L);
            }
            if (bl) {
                this.detach();
            }
            return true;
        }
    }

    final class MyHandler
    extends Handler {
        public MyHandler(Looper looper) {
            super(looper, null, true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message object) {
            BatteryCallback batteryCallback = BatteryStatsImpl.this.mCallback;
            int n = ((Message)object).what;
            boolean bl = true;
            if (n == 1) {
                if (batteryCallback == null) return;
                batteryCallback.batteryNeedsCpuUpdate();
                return;
            }
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return;
                    }
                    if (batteryCallback == null) return;
                    batteryCallback.batteryStatsReset();
                    return;
                }
                if (batteryCallback == null) return;
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                synchronized (batteryStatsImpl) {
                    object = BatteryStatsImpl.this.mCharging ? "android.os.action.CHARGING" : "android.os.action.DISCHARGING";
                }
                object = new Intent((String)object);
                ((Intent)object).addFlags(67108864);
                batteryCallback.batterySendBroadcast((Intent)object);
                return;
            }
            if (batteryCallback == null) return;
            if (((Message)object).arg1 == 0) {
                bl = false;
            }
            batteryCallback.batteryPowerChanged(bl);
        }
    }

    public abstract class OverflowArrayMap<T> {
        private static final String OVERFLOW_NAME = "*overflow*";
        ArrayMap<String, MutableInt> mActiveOverflow;
        T mCurOverflow;
        long mLastCleanupTime;
        long mLastClearTime;
        long mLastOverflowFinishTime;
        long mLastOverflowTime;
        final ArrayMap<String, T> mMap = new ArrayMap();
        final int mUid;

        public OverflowArrayMap(int n) {
            this.mUid = n;
        }

        public void add(String string2, T t) {
            String string3 = string2;
            if (string2 == null) {
                string3 = "";
            }
            this.mMap.put(string3, t);
            if (OVERFLOW_NAME.equals(string3)) {
                this.mCurOverflow = t;
            }
        }

        public void cleanup() {
            this.mLastCleanupTime = SystemClock.elapsedRealtime();
            ArrayMap<String, MutableInt> arrayMap = this.mActiveOverflow;
            if (arrayMap != null && arrayMap.size() == 0) {
                this.mActiveOverflow = null;
            }
            if (this.mActiveOverflow == null) {
                if (this.mMap.containsKey(OVERFLOW_NAME)) {
                    arrayMap = new StringBuilder();
                    ((StringBuilder)((Object)arrayMap)).append("Cleaning up with no active overflow, but have overflow entry ");
                    ((StringBuilder)((Object)arrayMap)).append(this.mMap.get(OVERFLOW_NAME));
                    Slog.wtf(BatteryStatsImpl.TAG, ((StringBuilder)((Object)arrayMap)).toString());
                    this.mMap.remove(OVERFLOW_NAME);
                }
                this.mCurOverflow = null;
            } else if (this.mCurOverflow == null || !this.mMap.containsKey(OVERFLOW_NAME)) {
                arrayMap = new StringBuilder();
                ((StringBuilder)((Object)arrayMap)).append("Cleaning up with active overflow, but no overflow entry: cur=");
                ((StringBuilder)((Object)arrayMap)).append(this.mCurOverflow);
                ((StringBuilder)((Object)arrayMap)).append(" map=");
                ((StringBuilder)((Object)arrayMap)).append(this.mMap.get(OVERFLOW_NAME));
                Slog.wtf(BatteryStatsImpl.TAG, ((StringBuilder)((Object)arrayMap)).toString());
            }
        }

        public void clear() {
            this.mLastClearTime = SystemClock.elapsedRealtime();
            this.mMap.clear();
            this.mCurOverflow = null;
            this.mActiveOverflow = null;
        }

        public ArrayMap<String, T> getMap() {
            return this.mMap;
        }

        public abstract T instantiateObject();

        public T startObject(String arrayMap) {
            MutableInt mutableInt;
            String string2 = arrayMap;
            if (arrayMap == null) {
                string2 = "";
            }
            if ((arrayMap = this.mMap.get(string2)) != null) {
                return (T)arrayMap;
            }
            arrayMap = this.mActiveOverflow;
            if (arrayMap != null && (mutableInt = (MutableInt)arrayMap.get(string2)) != null) {
                T t = this.mCurOverflow;
                arrayMap = t;
                if (t == null) {
                    arrayMap = new StringBuilder();
                    ((StringBuilder)((Object)arrayMap)).append("Have active overflow ");
                    ((StringBuilder)((Object)arrayMap)).append(string2);
                    ((StringBuilder)((Object)arrayMap)).append(" but null overflow");
                    Slog.wtf("BatteryStatsImpl", ((StringBuilder)((Object)arrayMap)).toString());
                    arrayMap = this.instantiateObject();
                    this.mCurOverflow = arrayMap;
                    this.mMap.put("*overflow*", arrayMap);
                }
                ++mutableInt.value;
                return (T)arrayMap;
            }
            if (this.mMap.size() >= MAX_WAKELOCKS_PER_UID) {
                T t = this.mCurOverflow;
                arrayMap = t;
                if (t == null) {
                    arrayMap = this.instantiateObject();
                    this.mCurOverflow = arrayMap;
                    this.mMap.put("*overflow*", arrayMap);
                }
                if (this.mActiveOverflow == null) {
                    this.mActiveOverflow = new ArrayMap<K, V>();
                }
                this.mActiveOverflow.put(string2, new MutableInt(1));
                this.mLastOverflowTime = SystemClock.elapsedRealtime();
                return (T)arrayMap;
            }
            arrayMap = this.instantiateObject();
            this.mMap.put(string2, arrayMap);
            return (T)arrayMap;
        }

        public T stopObject(String arrayMap) {
            MutableInt mutableInt;
            String string2 = arrayMap;
            if (arrayMap == null) {
                string2 = "";
            }
            if ((arrayMap = this.mMap.get(string2)) != null) {
                return (T)arrayMap;
            }
            arrayMap = this.mActiveOverflow;
            if (arrayMap != null && (mutableInt = (MutableInt)arrayMap.get(string2)) != null && (arrayMap = this.mCurOverflow) != null) {
                --mutableInt.value;
                if (mutableInt.value <= 0) {
                    this.mActiveOverflow.remove(string2);
                    this.mLastOverflowFinishTime = SystemClock.elapsedRealtime();
                }
                return (T)arrayMap;
            }
            arrayMap = new StringBuilder();
            ((StringBuilder)((Object)arrayMap)).append("Unable to find object for ");
            ((StringBuilder)((Object)arrayMap)).append(string2);
            ((StringBuilder)((Object)arrayMap)).append(" in uid ");
            ((StringBuilder)((Object)arrayMap)).append(this.mUid);
            ((StringBuilder)((Object)arrayMap)).append(" mapsize=");
            ((StringBuilder)((Object)arrayMap)).append(this.mMap.size());
            ((StringBuilder)((Object)arrayMap)).append(" activeoverflow=");
            ((StringBuilder)((Object)arrayMap)).append(this.mActiveOverflow);
            ((StringBuilder)((Object)arrayMap)).append(" curoverflow=");
            ((StringBuilder)((Object)arrayMap)).append(this.mCurOverflow);
            long l = SystemClock.elapsedRealtime();
            if (this.mLastOverflowTime != 0L) {
                ((StringBuilder)((Object)arrayMap)).append(" lastOverflowTime=");
                TimeUtils.formatDuration(this.mLastOverflowTime - l, (StringBuilder)((Object)arrayMap));
            }
            if (this.mLastOverflowFinishTime != 0L) {
                ((StringBuilder)((Object)arrayMap)).append(" lastOverflowFinishTime=");
                TimeUtils.formatDuration(this.mLastOverflowFinishTime - l, (StringBuilder)((Object)arrayMap));
            }
            if (this.mLastClearTime != 0L) {
                ((StringBuilder)((Object)arrayMap)).append(" lastClearTime=");
                TimeUtils.formatDuration(this.mLastClearTime - l, (StringBuilder)((Object)arrayMap));
            }
            if (this.mLastCleanupTime != 0L) {
                ((StringBuilder)((Object)arrayMap)).append(" lastCleanupTime=");
                TimeUtils.formatDuration(this.mLastCleanupTime - l, (StringBuilder)((Object)arrayMap));
            }
            Slog.wtf("BatteryStatsImpl", ((StringBuilder)((Object)arrayMap)).toString());
            return null;
        }
    }

    public static interface PlatformIdleStateCallback {
        public void fillLowPowerStats(RpmStats var1);

        public String getPlatformLowPowerStats();

        public String getSubsystemLowPowerStats();
    }

    public static interface RailEnergyDataCallback {
        public void fillRailDataStats(RailStats var1);
    }

    public static class SamplingTimer
    extends Timer {
        int mCurrentReportedCount;
        long mCurrentReportedTotalTime;
        boolean mTimeBaseRunning;
        boolean mTrackingReportedValues;
        int mUnpluggedReportedCount;
        long mUnpluggedReportedTotalTime;
        int mUpdateVersion;

        @VisibleForTesting
        public SamplingTimer(Clocks clocks, TimeBase timeBase) {
            super(clocks, 0, timeBase);
            this.mTrackingReportedValues = false;
            this.mTimeBaseRunning = timeBase.isRunning();
        }

        @VisibleForTesting
        public SamplingTimer(Clocks clocks, TimeBase timeBase, Parcel parcel) {
            boolean bl = false;
            super(clocks, 0, timeBase, parcel);
            this.mCurrentReportedCount = parcel.readInt();
            this.mUnpluggedReportedCount = parcel.readInt();
            this.mCurrentReportedTotalTime = parcel.readLong();
            this.mUnpluggedReportedTotalTime = parcel.readLong();
            if (parcel.readInt() == 1) {
                bl = true;
            }
            this.mTrackingReportedValues = bl;
            this.mTimeBaseRunning = timeBase.isRunning();
        }

        public void add(long l, int n) {
            this.update(this.mCurrentReportedTotalTime + l, this.mCurrentReportedCount + n);
        }

        @Override
        protected int computeCurrentCountLocked() {
            int n = this.mCount;
            int n2 = this.mTimeBaseRunning && this.mTrackingReportedValues ? this.mCurrentReportedCount - this.mUnpluggedReportedCount : 0;
            return n + n2;
        }

        @Override
        protected long computeRunTimeLocked(long l) {
            long l2 = this.mTotalTime;
            l = this.mTimeBaseRunning && this.mTrackingReportedValues ? this.mCurrentReportedTotalTime - this.mUnpluggedReportedTotalTime : 0L;
            return l2 + l;
        }

        public void endSample() {
            this.mTotalTime = this.computeRunTimeLocked(0L);
            this.mCount = this.computeCurrentCountLocked();
            this.mCurrentReportedTotalTime = 0L;
            this.mUnpluggedReportedTotalTime = 0L;
            this.mCurrentReportedCount = 0;
            this.mUnpluggedReportedCount = 0;
        }

        public int getUpdateVersion() {
            return this.mUpdateVersion;
        }

        @Override
        public void logState(Printer printer, String string2) {
            super.logState(printer, string2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("mCurrentReportedCount=");
            stringBuilder.append(this.mCurrentReportedCount);
            stringBuilder.append(" mUnpluggedReportedCount=");
            stringBuilder.append(this.mUnpluggedReportedCount);
            stringBuilder.append(" mCurrentReportedTotalTime=");
            stringBuilder.append(this.mCurrentReportedTotalTime);
            stringBuilder.append(" mUnpluggedReportedTotalTime=");
            stringBuilder.append(this.mUnpluggedReportedTotalTime);
            printer.println(stringBuilder.toString());
        }

        @Override
        public void onTimeStarted(long l, long l2, long l3) {
            super.onTimeStarted(l, l2, l3);
            if (this.mTrackingReportedValues) {
                this.mUnpluggedReportedTotalTime = this.mCurrentReportedTotalTime;
                this.mUnpluggedReportedCount = this.mCurrentReportedCount;
            }
            this.mTimeBaseRunning = true;
        }

        @Override
        public void onTimeStopped(long l, long l2, long l3) {
            super.onTimeStopped(l, l2, l3);
            this.mTimeBaseRunning = false;
        }

        @Override
        public boolean reset(boolean bl) {
            super.reset(bl);
            this.mTrackingReportedValues = false;
            this.mUnpluggedReportedTotalTime = 0L;
            this.mUnpluggedReportedCount = 0;
            return true;
        }

        public void setUpdateVersion(int n) {
            this.mUpdateVersion = n;
        }

        public void update(long l, int n) {
            if (this.mTimeBaseRunning && !this.mTrackingReportedValues) {
                this.mUnpluggedReportedTotalTime = l;
                this.mUnpluggedReportedCount = n;
            }
            this.mTrackingReportedValues = true;
            if (l < this.mCurrentReportedTotalTime || n < this.mCurrentReportedCount) {
                this.endSample();
            }
            this.mCurrentReportedTotalTime = l;
            this.mCurrentReportedCount = n;
        }

        @Override
        public void writeToParcel(Parcel parcel, long l) {
            super.writeToParcel(parcel, l);
            parcel.writeInt(this.mCurrentReportedCount);
            parcel.writeInt(this.mUnpluggedReportedCount);
            parcel.writeLong(this.mCurrentReportedTotalTime);
            parcel.writeLong(this.mUnpluggedReportedTotalTime);
            parcel.writeInt((int)this.mTrackingReportedValues);
        }
    }

    public static class StopwatchTimer
    extends Timer {
        long mAcquireTime = -1L;
        @VisibleForTesting
        public boolean mInList;
        int mNesting;
        long mTimeout;
        final ArrayList<StopwatchTimer> mTimerPool;
        final Uid mUid;
        long mUpdateTime;

        public StopwatchTimer(Clocks clocks, Uid uid, int n, ArrayList<StopwatchTimer> arrayList, TimeBase timeBase) {
            super(clocks, n, timeBase);
            this.mUid = uid;
            this.mTimerPool = arrayList;
        }

        public StopwatchTimer(Clocks clocks, Uid uid, int n, ArrayList<StopwatchTimer> arrayList, TimeBase timeBase, Parcel parcel) {
            super(clocks, n, timeBase, parcel);
            this.mUid = uid;
            this.mTimerPool = arrayList;
            this.mUpdateTime = parcel.readLong();
        }

        private static long refreshTimersLocked(long l, ArrayList<StopwatchTimer> arrayList, StopwatchTimer stopwatchTimer) {
            long l2 = 0L;
            int n = arrayList.size();
            for (int i = n - 1; i >= 0; --i) {
                StopwatchTimer stopwatchTimer2 = arrayList.get(i);
                long l3 = l - stopwatchTimer2.mUpdateTime;
                long l4 = l2;
                if (l3 > 0L) {
                    l4 = l3 / (long)n;
                    if (stopwatchTimer2 == stopwatchTimer) {
                        l2 = l4;
                    }
                    stopwatchTimer2.mTotalTime += l4;
                    l4 = l2;
                }
                stopwatchTimer2.mUpdateTime = l;
                l2 = l4;
            }
            return l2;
        }

        @Override
        protected int computeCurrentCountLocked() {
            return this.mCount;
        }

        @Override
        protected long computeRunTimeLocked(long l) {
            long l2 = this.mTimeout;
            long l3 = 0L;
            long l4 = l;
            if (l2 > 0L) {
                long l5 = this.mUpdateTime;
                l4 = l;
                if (l > l5 + l2) {
                    l4 = l5 + l2;
                }
            }
            l2 = this.mTotalTime;
            if (this.mNesting > 0) {
                l = this.mUpdateTime;
                ArrayList<StopwatchTimer> arrayList = this.mTimerPool;
                int n = arrayList != null ? arrayList.size() : 1;
                l = (l4 - l) / (long)n;
            } else {
                l = l3;
            }
            return l2 + l;
        }

        @UnsupportedAppUsage
        @Override
        public void detach() {
            super.detach();
            ArrayList<StopwatchTimer> arrayList = this.mTimerPool;
            if (arrayList != null) {
                arrayList.remove(this);
            }
        }

        @Override
        public boolean isRunningLocked() {
            boolean bl = this.mNesting > 0;
            return bl;
        }

        @Override
        public void logState(Printer printer, String string2) {
            super.logState(printer, string2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("mNesting=");
            stringBuilder.append(this.mNesting);
            stringBuilder.append(" mUpdateTime=");
            stringBuilder.append(this.mUpdateTime);
            stringBuilder.append(" mAcquireTime=");
            stringBuilder.append(this.mAcquireTime);
            printer.println(stringBuilder.toString());
        }

        @Override
        public void onTimeStopped(long l, long l2, long l3) {
            if (this.mNesting > 0) {
                super.onTimeStopped(l, l2, l3);
                this.mUpdateTime = l3;
            }
        }

        @Override
        public void readSummaryFromParcelLocked(Parcel parcel) {
            super.readSummaryFromParcelLocked(parcel);
            this.mNesting = 0;
        }

        @Override
        public boolean reset(boolean bl) {
            int n = this.mNesting;
            boolean bl2 = true;
            boolean bl3 = n <= 0;
            bl = bl3 && bl ? bl2 : false;
            super.reset(bl);
            if (this.mNesting > 0) {
                this.mUpdateTime = this.mTimeBase.getRealtime(this.mClocks.elapsedRealtime() * 1000L);
            }
            this.mAcquireTime = -1L;
            return bl3;
        }

        public void setMark(long l) {
            l = this.mTimeBase.getRealtime(1000L * l);
            if (this.mNesting > 0) {
                ArrayList<StopwatchTimer> arrayList = this.mTimerPool;
                if (arrayList != null) {
                    StopwatchTimer.refreshTimersLocked(l, arrayList, this);
                } else {
                    this.mTotalTime += l - this.mUpdateTime;
                    this.mUpdateTime = l;
                }
            }
            this.mTimeBeforeMark = this.mTotalTime;
        }

        public void setTimeout(long l) {
            this.mTimeout = l;
        }

        public void startRunningLocked(long l) {
            int n = this.mNesting;
            this.mNesting = n + 1;
            if (n == 0) {
                this.mUpdateTime = l = this.mTimeBase.getRealtime(1000L * l);
                ArrayList<StopwatchTimer> arrayList = this.mTimerPool;
                if (arrayList != null) {
                    StopwatchTimer.refreshTimersLocked(l, arrayList, null);
                    this.mTimerPool.add(this);
                }
                if (this.mTimeBase.isRunning()) {
                    ++this.mCount;
                    this.mAcquireTime = this.mTotalTime;
                } else {
                    this.mAcquireTime = -1L;
                }
            }
        }

        public void stopAllRunningLocked(long l) {
            if (this.mNesting > 0) {
                this.mNesting = 1;
                this.stopRunningLocked(l);
            }
        }

        public void stopRunningLocked(long l) {
            int n = this.mNesting;
            if (n == 0) {
                return;
            }
            this.mNesting = --n;
            if (n == 0) {
                l = this.mTimeBase.getRealtime(1000L * l);
                ArrayList<StopwatchTimer> arrayList = this.mTimerPool;
                if (arrayList != null) {
                    StopwatchTimer.refreshTimersLocked(l, arrayList, null);
                    this.mTimerPool.remove(this);
                } else {
                    this.mNesting = 1;
                    this.mTotalTime = this.computeRunTimeLocked(l);
                    this.mNesting = 0;
                }
                if (this.mAcquireTime >= 0L && this.mTotalTime == this.mAcquireTime) {
                    --this.mCount;
                }
            }
        }

        @Override
        public void writeToParcel(Parcel parcel, long l) {
            super.writeToParcel(parcel, l);
            parcel.writeLong(this.mUpdateTime);
        }
    }

    public static class SystemClocks
    implements Clocks {
        @Override
        public long elapsedRealtime() {
            return SystemClock.elapsedRealtime();
        }

        @Override
        public long uptimeMillis() {
            return SystemClock.uptimeMillis();
        }
    }

    public static class TimeBase {
        protected final Collection<TimeBaseObs> mObservers;
        protected long mPastRealtime;
        protected long mPastUptime;
        protected long mRealtime;
        protected long mRealtimeStart;
        protected boolean mRunning;
        protected long mUnpluggedRealtime;
        protected long mUnpluggedUptime;
        protected long mUptime;
        protected long mUptimeStart;

        public TimeBase() {
            this(false);
        }

        public TimeBase(boolean bl) {
            AbstractCollection abstractCollection = bl ? new HashSet() : new ArrayList();
            this.mObservers = abstractCollection;
        }

        public void add(TimeBaseObs timeBaseObs) {
            this.mObservers.add(timeBaseObs);
        }

        public long computeRealtime(long l, int n) {
            return this.mRealtime + this.getRealtime(l);
        }

        public long computeUptime(long l, int n) {
            return this.mUptime + this.getUptime(l);
        }

        public void dump(PrintWriter printWriter, String string2) {
            StringBuilder stringBuilder = new StringBuilder(128);
            printWriter.print(string2);
            printWriter.print("mRunning=");
            printWriter.println(this.mRunning);
            stringBuilder.setLength(0);
            stringBuilder.append(string2);
            stringBuilder.append("mUptime=");
            BatteryStats.formatTimeMs(stringBuilder, this.mUptime / 1000L);
            printWriter.println(stringBuilder.toString());
            stringBuilder.setLength(0);
            stringBuilder.append(string2);
            stringBuilder.append("mRealtime=");
            BatteryStats.formatTimeMs(stringBuilder, this.mRealtime / 1000L);
            printWriter.println(stringBuilder.toString());
            stringBuilder.setLength(0);
            stringBuilder.append(string2);
            stringBuilder.append("mPastUptime=");
            BatteryStats.formatTimeMs(stringBuilder, this.mPastUptime / 1000L);
            stringBuilder.append("mUptimeStart=");
            BatteryStats.formatTimeMs(stringBuilder, this.mUptimeStart / 1000L);
            stringBuilder.append("mUnpluggedUptime=");
            BatteryStats.formatTimeMs(stringBuilder, this.mUnpluggedUptime / 1000L);
            printWriter.println(stringBuilder.toString());
            stringBuilder.setLength(0);
            stringBuilder.append(string2);
            stringBuilder.append("mPastRealtime=");
            BatteryStats.formatTimeMs(stringBuilder, this.mPastRealtime / 1000L);
            stringBuilder.append("mRealtimeStart=");
            BatteryStats.formatTimeMs(stringBuilder, this.mRealtimeStart / 1000L);
            stringBuilder.append("mUnpluggedRealtime=");
            BatteryStats.formatTimeMs(stringBuilder, this.mUnpluggedRealtime / 1000L);
            printWriter.println(stringBuilder.toString());
        }

        public long getRealtime(long l) {
            long l2;
            long l3 = l2 = this.mPastRealtime;
            if (this.mRunning) {
                l3 = l2 + (l - this.mRealtimeStart);
            }
            return l3;
        }

        public long getRealtimeStart() {
            return this.mRealtimeStart;
        }

        public long getUptime(long l) {
            long l2;
            long l3 = l2 = this.mPastUptime;
            if (this.mRunning) {
                l3 = l2 + (l - this.mUptimeStart);
            }
            return l3;
        }

        public long getUptimeStart() {
            return this.mUptimeStart;
        }

        public boolean hasObserver(TimeBaseObs timeBaseObs) {
            return this.mObservers.contains(timeBaseObs);
        }

        public void init(long l, long l2) {
            this.mRealtime = 0L;
            this.mUptime = 0L;
            this.mPastUptime = 0L;
            this.mPastRealtime = 0L;
            this.mUptimeStart = l;
            this.mRealtimeStart = l2;
            this.mUnpluggedUptime = this.getUptime(this.mUptimeStart);
            this.mUnpluggedRealtime = this.getRealtime(this.mRealtimeStart);
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        public void readFromParcel(Parcel parcel) {
            this.mRunning = false;
            this.mUptime = parcel.readLong();
            this.mPastUptime = parcel.readLong();
            this.mUptimeStart = parcel.readLong();
            this.mRealtime = parcel.readLong();
            this.mPastRealtime = parcel.readLong();
            this.mRealtimeStart = parcel.readLong();
            this.mUnpluggedUptime = parcel.readLong();
            this.mUnpluggedRealtime = parcel.readLong();
        }

        public void readSummaryFromParcel(Parcel parcel) {
            this.mUptime = parcel.readLong();
            this.mRealtime = parcel.readLong();
        }

        public void remove(TimeBaseObs timeBaseObs) {
            this.mObservers.remove(timeBaseObs);
        }

        public void reset(long l, long l2) {
            if (!this.mRunning) {
                this.mPastUptime = 0L;
                this.mPastRealtime = 0L;
            } else {
                this.mUptimeStart = l;
                this.mRealtimeStart = l2;
                this.mUnpluggedUptime = this.getUptime(l);
                this.mUnpluggedRealtime = this.getRealtime(l2);
            }
        }

        public boolean setRunning(boolean bl, long l, long l2) {
            if (this.mRunning != bl) {
                this.mRunning = bl;
                if (bl) {
                    long l3;
                    this.mUptimeStart = l;
                    this.mRealtimeStart = l2;
                    this.mUnpluggedUptime = l3 = this.getUptime(l);
                    this.mUnpluggedRealtime = l = this.getRealtime(l2);
                    Iterator<TimeBaseObs> iterator = this.mObservers.iterator();
                    while (iterator.hasNext()) {
                        iterator.next().onTimeStarted(l2, l3, l);
                    }
                } else {
                    this.mPastUptime += l - this.mUptimeStart;
                    this.mPastRealtime += l2 - this.mRealtimeStart;
                    long l4 = this.getUptime(l);
                    l = this.getRealtime(l2);
                    Iterator<TimeBaseObs> iterator = this.mObservers.iterator();
                    while (iterator.hasNext()) {
                        iterator.next().onTimeStopped(l2, l4, l);
                    }
                }
                return true;
            }
            return false;
        }

        public void writeSummaryToParcel(Parcel parcel, long l, long l2) {
            parcel.writeLong(this.computeUptime(l, 0));
            parcel.writeLong(this.computeRealtime(l2, 0));
        }

        public void writeToParcel(Parcel parcel, long l, long l2) {
            l = this.getUptime(l);
            l2 = this.getRealtime(l2);
            parcel.writeLong(this.mUptime);
            parcel.writeLong(l);
            parcel.writeLong(this.mUptimeStart);
            parcel.writeLong(this.mRealtime);
            parcel.writeLong(l2);
            parcel.writeLong(this.mRealtimeStart);
            parcel.writeLong(this.mUnpluggedUptime);
            parcel.writeLong(this.mUnpluggedRealtime);
        }
    }

    public static interface TimeBaseObs {
        public void detach();

        public void onTimeStarted(long var1, long var3, long var5);

        public void onTimeStopped(long var1, long var3, long var5);

        public boolean reset(boolean var1);
    }

    public static abstract class Timer
    extends BatteryStats.Timer
    implements TimeBaseObs {
        protected final Clocks mClocks;
        protected int mCount;
        protected final TimeBase mTimeBase;
        protected long mTimeBeforeMark;
        protected long mTotalTime;
        protected final int mType;

        public Timer(Clocks clocks, int n, TimeBase timeBase) {
            this.mClocks = clocks;
            this.mType = n;
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public Timer(Clocks clocks, int n, TimeBase timeBase, Parcel parcel) {
            this.mClocks = clocks;
            this.mType = n;
            this.mTimeBase = timeBase;
            this.mCount = parcel.readInt();
            this.mTotalTime = parcel.readLong();
            this.mTimeBeforeMark = parcel.readLong();
            timeBase.add(this);
        }

        @UnsupportedAppUsage
        public static void writeTimerToParcel(Parcel parcel, Timer timer, long l) {
            if (timer == null) {
                parcel.writeInt(0);
                return;
            }
            parcel.writeInt(1);
            timer.writeToParcel(parcel, l);
        }

        protected abstract int computeCurrentCountLocked();

        protected abstract long computeRunTimeLocked(long var1);

        @Override
        public void detach() {
            this.mTimeBase.remove(this);
        }

        @UnsupportedAppUsage
        @Override
        public int getCountLocked(int n) {
            return this.computeCurrentCountLocked();
        }

        @Override
        public long getTimeSinceMarkLocked(long l) {
            return this.computeRunTimeLocked(this.mTimeBase.getRealtime(l)) - this.mTimeBeforeMark;
        }

        @UnsupportedAppUsage
        @Override
        public long getTotalTimeLocked(long l, int n) {
            return this.computeRunTimeLocked(this.mTimeBase.getRealtime(l));
        }

        @Override
        public void logState(Printer printer, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("mCount=");
            stringBuilder.append(this.mCount);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("mTotalTime=");
            stringBuilder.append(this.mTotalTime);
            printer.println(stringBuilder.toString());
        }

        @Override
        public void onTimeStarted(long l, long l2, long l3) {
        }

        @Override
        public void onTimeStopped(long l, long l2, long l3) {
            this.mTotalTime = this.computeRunTimeLocked(l3);
            this.mCount = this.computeCurrentCountLocked();
        }

        public void readSummaryFromParcelLocked(Parcel parcel) {
            this.mTotalTime = parcel.readLong();
            this.mCount = parcel.readInt();
            this.mTimeBeforeMark = this.mTotalTime;
        }

        @Override
        public boolean reset(boolean bl) {
            this.mTimeBeforeMark = 0L;
            this.mTotalTime = 0L;
            this.mCount = 0;
            if (bl) {
                this.detach();
            }
            return true;
        }

        public void writeSummaryFromParcelLocked(Parcel parcel, long l) {
            parcel.writeLong(this.computeRunTimeLocked(this.mTimeBase.getRealtime(l)));
            parcel.writeInt(this.computeCurrentCountLocked());
        }

        public void writeToParcel(Parcel parcel, long l) {
            parcel.writeInt(this.computeCurrentCountLocked());
            parcel.writeLong(this.computeRunTimeLocked(this.mTimeBase.getRealtime(l)));
            parcel.writeLong(this.mTimeBeforeMark);
        }
    }

    public static class Uid
    extends BatteryStats.Uid {
        static final int NO_BATCHED_SCAN_STARTED = -1;
        DualTimer mAggregatedPartialWakelockTimer;
        StopwatchTimer mAudioTurnedOnTimer;
        private ControllerActivityCounterImpl mBluetoothControllerActivity;
        Counter mBluetoothScanResultBgCounter;
        Counter mBluetoothScanResultCounter;
        DualTimer mBluetoothScanTimer;
        DualTimer mBluetoothUnoptimizedScanTimer;
        protected BatteryStatsImpl mBsi;
        StopwatchTimer mCameraTurnedOnTimer;
        IntArray mChildUids;
        LongSamplingCounter mCpuActiveTimeMs;
        LongSamplingCounter[][] mCpuClusterSpeedTimesUs;
        LongSamplingCounterArray mCpuClusterTimesMs;
        LongSamplingCounterArray mCpuFreqTimeMs;
        long mCurStepSystemTime;
        long mCurStepUserTime;
        StopwatchTimer mFlashlightTurnedOnTimer;
        StopwatchTimer mForegroundActivityTimer;
        StopwatchTimer mForegroundServiceTimer;
        boolean mFullWifiLockOut;
        StopwatchTimer mFullWifiLockTimer;
        boolean mInForegroundService = false;
        final ArrayMap<String, SparseIntArray> mJobCompletions = new ArrayMap();
        final OverflowArrayMap<DualTimer> mJobStats;
        Counter mJobsDeferredCount;
        Counter mJobsDeferredEventCount;
        final Counter[] mJobsFreshnessBuckets;
        LongSamplingCounter mJobsFreshnessTimeMs;
        long mLastStepSystemTime;
        long mLastStepUserTime;
        LongSamplingCounter mMobileRadioActiveCount;
        LongSamplingCounter mMobileRadioActiveTime;
        private LongSamplingCounter mMobileRadioApWakeupCount;
        private ControllerActivityCounterImpl mModemControllerActivity;
        LongSamplingCounter[] mNetworkByteActivityCounters;
        LongSamplingCounter[] mNetworkPacketActivityCounters;
        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public final TimeBase mOnBatteryBackgroundTimeBase;
        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public final TimeBase mOnBatteryScreenOffBackgroundTimeBase;
        final ArrayMap<String, Pkg> mPackageStats = new ArrayMap();
        final SparseArray<BatteryStats.Uid.Pid> mPids = new SparseArray();
        LongSamplingCounterArray[] mProcStateScreenOffTimeMs;
        LongSamplingCounterArray[] mProcStateTimeMs;
        int mProcessState = 21;
        StopwatchTimer[] mProcessStateTimer;
        final ArrayMap<String, Proc> mProcessStats = new ArrayMap();
        LongSamplingCounterArray mScreenOffCpuFreqTimeMs;
        final SparseArray<Sensor> mSensorStats = new SparseArray();
        final OverflowArrayMap<DualTimer> mSyncStats;
        LongSamplingCounter mSystemCpuTime;
        final int mUid;
        Counter[] mUserActivityCounters;
        LongSamplingCounter mUserCpuTime;
        BatchTimer mVibratorOnTimer;
        StopwatchTimer mVideoTurnedOnTimer;
        final OverflowArrayMap<Wakelock> mWakelockStats;
        int mWifiBatchedScanBinStarted = -1;
        StopwatchTimer[] mWifiBatchedScanTimer;
        private ControllerActivityCounterImpl mWifiControllerActivity;
        StopwatchTimer mWifiMulticastTimer;
        int mWifiMulticastWakelockCount;
        private LongSamplingCounter mWifiRadioApWakeupCount;
        boolean mWifiRunning;
        StopwatchTimer mWifiRunningTimer;
        boolean mWifiScanStarted;
        DualTimer mWifiScanTimer;

        public Uid(BatteryStatsImpl batteryStatsImpl, int n) {
            this.mBsi = batteryStatsImpl;
            this.mUid = n;
            this.mOnBatteryBackgroundTimeBase = new TimeBase(false);
            this.mOnBatteryBackgroundTimeBase.init(this.mBsi.mClocks.uptimeMillis() * 1000L, this.mBsi.mClocks.elapsedRealtime() * 1000L);
            this.mOnBatteryScreenOffBackgroundTimeBase = new TimeBase(false);
            this.mOnBatteryScreenOffBackgroundTimeBase.init(this.mBsi.mClocks.uptimeMillis() * 1000L, this.mBsi.mClocks.elapsedRealtime() * 1000L);
            this.mUserCpuTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mSystemCpuTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mCpuActiveTimeMs = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mCpuClusterTimesMs = new LongSamplingCounterArray(this.mBsi.mOnBatteryTimeBase);
            batteryStatsImpl = this.mBsi;
            Objects.requireNonNull(batteryStatsImpl);
            this.mWakelockStats = new OverflowArrayMap<Wakelock>(batteryStatsImpl, n){
                {
                    Objects.requireNonNull(batteryStatsImpl);
                    super(n);
                }

                @Override
                public Wakelock instantiateObject() {
                    return new Wakelock(Uid.this.mBsi, Uid.this);
                }
            };
            batteryStatsImpl = this.mBsi;
            Objects.requireNonNull(batteryStatsImpl);
            this.mSyncStats = new OverflowArrayMap<DualTimer>(batteryStatsImpl, n){
                {
                    Objects.requireNonNull(batteryStatsImpl);
                    super(n);
                }

                @Override
                public DualTimer instantiateObject() {
                    Clocks clocks = Uid.this.mBsi.mClocks;
                    Uid uid = Uid.this;
                    return new DualTimer(clocks, uid, 13, null, uid.mBsi.mOnBatteryTimeBase, Uid.this.mOnBatteryBackgroundTimeBase);
                }
            };
            batteryStatsImpl = this.mBsi;
            Objects.requireNonNull(batteryStatsImpl);
            this.mJobStats = new OverflowArrayMap<DualTimer>(batteryStatsImpl, n){
                {
                    Objects.requireNonNull(batteryStatsImpl);
                    super(n);
                }

                @Override
                public DualTimer instantiateObject() {
                    Clocks clocks = Uid.this.mBsi.mClocks;
                    Uid uid = Uid.this;
                    return new DualTimer(clocks, uid, 14, null, uid.mBsi.mOnBatteryTimeBase, Uid.this.mOnBatteryBackgroundTimeBase);
                }
            };
            this.mWifiRunningTimer = new StopwatchTimer(this.mBsi.mClocks, this, 4, this.mBsi.mWifiRunningTimers, this.mBsi.mOnBatteryTimeBase);
            this.mFullWifiLockTimer = new StopwatchTimer(this.mBsi.mClocks, this, 5, this.mBsi.mFullWifiLockTimers, this.mBsi.mOnBatteryTimeBase);
            this.mWifiScanTimer = new DualTimer(this.mBsi.mClocks, this, 6, this.mBsi.mWifiScanTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            this.mWifiBatchedScanTimer = new StopwatchTimer[5];
            this.mWifiMulticastTimer = new StopwatchTimer(this.mBsi.mClocks, this, 7, this.mBsi.mWifiMulticastTimers, this.mBsi.mOnBatteryTimeBase);
            this.mProcessStateTimer = new StopwatchTimer[7];
            this.mJobsDeferredEventCount = new Counter(this.mBsi.mOnBatteryTimeBase);
            this.mJobsDeferredCount = new Counter(this.mBsi.mOnBatteryTimeBase);
            this.mJobsFreshnessTimeMs = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mJobsFreshnessBuckets = new Counter[BatteryStats.JOB_FRESHNESS_BUCKETS.length];
        }

        private void addProcStateScreenOffTimesMs(int n, long[] arrl, boolean bl) {
            LongSamplingCounterArray[] arrlongSamplingCounterArray;
            if (this.mProcStateScreenOffTimeMs == null) {
                this.mProcStateScreenOffTimeMs = new LongSamplingCounterArray[7];
            }
            if ((arrlongSamplingCounterArray = this.mProcStateScreenOffTimeMs)[n] == null || arrlongSamplingCounterArray[n].getSize() != arrl.length) {
                BatteryStatsImpl.detachIfNotNull(this.mProcStateScreenOffTimeMs[n]);
                this.mProcStateScreenOffTimeMs[n] = new LongSamplingCounterArray(this.mBsi.mOnBatteryScreenOffTimeBase);
            }
            this.mProcStateScreenOffTimeMs[n].addCountLocked(arrl, bl);
        }

        private void addProcStateTimesMs(int n, long[] arrl, boolean bl) {
            LongSamplingCounterArray[] arrlongSamplingCounterArray;
            if (this.mProcStateTimeMs == null) {
                this.mProcStateTimeMs = new LongSamplingCounterArray[7];
            }
            if ((arrlongSamplingCounterArray = this.mProcStateTimeMs)[n] == null || arrlongSamplingCounterArray[n].getSize() != arrl.length) {
                BatteryStatsImpl.detachIfNotNull(this.mProcStateTimeMs[n]);
                this.mProcStateTimeMs[n] = new LongSamplingCounterArray(this.mBsi.mOnBatteryTimeBase);
            }
            this.mProcStateTimeMs[n].addCountLocked(arrl, bl);
        }

        private long[] nullIfAllZeros(LongSamplingCounterArray arrl, int n) {
            if (arrl == null) {
                return null;
            }
            if ((arrl = arrl.getCountsLocked(n)) == null) {
                return null;
            }
            for (n = arrl.length - 1; n >= 0; --n) {
                if (arrl[n] == 0L) continue;
                return arrl;
            }
            return null;
        }

        public void addIsolatedUid(int n) {
            IntArray intArray = this.mChildUids;
            if (intArray == null) {
                this.mChildUids = new IntArray();
            } else if (intArray.indexOf(n) >= 0) {
                return;
            }
            this.mChildUids.add(n);
        }

        public DualTimer createAggregatedPartialWakelockTimerLocked() {
            if (this.mAggregatedPartialWakelockTimer == null) {
                this.mAggregatedPartialWakelockTimer = new DualTimer(this.mBsi.mClocks, this, 20, null, this.mBsi.mOnBatteryScreenOffTimeBase, this.mOnBatteryScreenOffBackgroundTimeBase);
            }
            return this.mAggregatedPartialWakelockTimer;
        }

        public StopwatchTimer createAudioTurnedOnTimerLocked() {
            if (this.mAudioTurnedOnTimer == null) {
                this.mAudioTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 15, this.mBsi.mAudioTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mAudioTurnedOnTimer;
        }

        public Counter createBluetoothScanResultBgCounterLocked() {
            if (this.mBluetoothScanResultBgCounter == null) {
                this.mBluetoothScanResultBgCounter = new Counter(this.mOnBatteryBackgroundTimeBase);
            }
            return this.mBluetoothScanResultBgCounter;
        }

        public Counter createBluetoothScanResultCounterLocked() {
            if (this.mBluetoothScanResultCounter == null) {
                this.mBluetoothScanResultCounter = new Counter(this.mBsi.mOnBatteryTimeBase);
            }
            return this.mBluetoothScanResultCounter;
        }

        public DualTimer createBluetoothScanTimerLocked() {
            if (this.mBluetoothScanTimer == null) {
                this.mBluetoothScanTimer = new DualTimer(this.mBsi.mClocks, this, 19, this.mBsi.mBluetoothScanOnTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            }
            return this.mBluetoothScanTimer;
        }

        public DualTimer createBluetoothUnoptimizedScanTimerLocked() {
            if (this.mBluetoothUnoptimizedScanTimer == null) {
                this.mBluetoothUnoptimizedScanTimer = new DualTimer(this.mBsi.mClocks, this, 21, null, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            }
            return this.mBluetoothUnoptimizedScanTimer;
        }

        public StopwatchTimer createCameraTurnedOnTimerLocked() {
            if (this.mCameraTurnedOnTimer == null) {
                this.mCameraTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 17, this.mBsi.mCameraTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mCameraTurnedOnTimer;
        }

        public StopwatchTimer createFlashlightTurnedOnTimerLocked() {
            if (this.mFlashlightTurnedOnTimer == null) {
                this.mFlashlightTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 16, this.mBsi.mFlashlightTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mFlashlightTurnedOnTimer;
        }

        public StopwatchTimer createForegroundActivityTimerLocked() {
            if (this.mForegroundActivityTimer == null) {
                this.mForegroundActivityTimer = new StopwatchTimer(this.mBsi.mClocks, this, 10, null, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mForegroundActivityTimer;
        }

        public StopwatchTimer createForegroundServiceTimerLocked() {
            if (this.mForegroundServiceTimer == null) {
                this.mForegroundServiceTimer = new StopwatchTimer(this.mBsi.mClocks, this, 22, null, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mForegroundServiceTimer;
        }

        public BatchTimer createVibratorOnTimerLocked() {
            if (this.mVibratorOnTimer == null) {
                this.mVibratorOnTimer = new BatchTimer(this.mBsi.mClocks, this, 9, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mVibratorOnTimer;
        }

        public StopwatchTimer createVideoTurnedOnTimerLocked() {
            if (this.mVideoTurnedOnTimer == null) {
                this.mVideoTurnedOnTimer = new StopwatchTimer(this.mBsi.mClocks, this, 8, this.mBsi.mVideoTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mVideoTurnedOnTimer;
        }

        void detachFromTimeBase() {
            int n;
            BatteryStatsImpl.detachIfNotNull(this.mWifiRunningTimer);
            BatteryStatsImpl.detachIfNotNull(this.mFullWifiLockTimer);
            BatteryStatsImpl.detachIfNotNull(this.mWifiScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mWifiBatchedScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mWifiMulticastTimer);
            BatteryStatsImpl.detachIfNotNull(this.mAudioTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mVideoTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mFlashlightTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mCameraTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mForegroundActivityTimer);
            BatteryStatsImpl.detachIfNotNull(this.mForegroundServiceTimer);
            BatteryStatsImpl.detachIfNotNull(this.mAggregatedPartialWakelockTimer);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothUnoptimizedScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanResultCounter);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanResultBgCounter);
            BatteryStatsImpl.detachIfNotNull(this.mProcessStateTimer);
            BatteryStatsImpl.detachIfNotNull(this.mVibratorOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mUserActivityCounters);
            BatteryStatsImpl.detachIfNotNull(this.mNetworkByteActivityCounters);
            BatteryStatsImpl.detachIfNotNull(this.mNetworkPacketActivityCounters);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveTime);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveCount);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioApWakeupCount);
            BatteryStatsImpl.detachIfNotNull(this.mWifiRadioApWakeupCount);
            BatteryStatsImpl.detachIfNotNull(this.mWifiControllerActivity);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothControllerActivity);
            BatteryStatsImpl.detachIfNotNull(this.mModemControllerActivity);
            this.mPids.clear();
            BatteryStatsImpl.detachIfNotNull(this.mUserCpuTime);
            BatteryStatsImpl.detachIfNotNull(this.mSystemCpuTime);
            BatteryStatsImpl.detachIfNotNull(this.mCpuClusterSpeedTimesUs);
            BatteryStatsImpl.detachIfNotNull(this.mCpuActiveTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mCpuFreqTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mScreenOffCpuFreqTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mCpuClusterTimesMs);
            BatteryStatsImpl.detachIfNotNull(this.mProcStateTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mProcStateScreenOffTimeMs);
            ArrayMap<String, Object> arrayMap = this.mWakelockStats.getMap();
            for (n = arrayMap.size() - 1; n >= 0; --n) {
                arrayMap.valueAt(n).detachFromTimeBase();
            }
            arrayMap = this.mSyncStats.getMap();
            for (n = arrayMap.size() - 1; n >= 0; --n) {
                BatteryStatsImpl.detachIfNotNull((DualTimer)arrayMap.valueAt(n));
            }
            arrayMap = this.mJobStats.getMap();
            for (n = arrayMap.size() - 1; n >= 0; --n) {
                BatteryStatsImpl.detachIfNotNull((DualTimer)arrayMap.valueAt(n));
            }
            BatteryStatsImpl.detachIfNotNull(this.mJobsDeferredEventCount);
            BatteryStatsImpl.detachIfNotNull(this.mJobsDeferredCount);
            BatteryStatsImpl.detachIfNotNull(this.mJobsFreshnessTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mJobsFreshnessBuckets);
            for (n = this.mSensorStats.size() - 1; n >= 0; --n) {
                this.mSensorStats.valueAt(n).detachFromTimeBase();
            }
            for (n = this.mProcessStats.size() - 1; n >= 0; --n) {
                this.mProcessStats.valueAt(n).detach();
            }
            this.mProcessStats.clear();
            for (n = this.mPackageStats.size() - 1; n >= 0; --n) {
                this.mPackageStats.valueAt(n).detach();
            }
            this.mPackageStats.clear();
        }

        @Override
        public Timer getAggregatedPartialWakelockTimer() {
            return this.mAggregatedPartialWakelockTimer;
        }

        @Override
        public Timer getAudioTurnedOnTimer() {
            return this.mAudioTurnedOnTimer;
        }

        public BatteryStatsImpl getBatteryStats() {
            return this.mBsi;
        }

        @Override
        public BatteryStats.ControllerActivityCounter getBluetoothControllerActivity() {
            return this.mBluetoothControllerActivity;
        }

        @Override
        public Timer getBluetoothScanBackgroundTimer() {
            DualTimer dualTimer = this.mBluetoothScanTimer;
            if (dualTimer == null) {
                return null;
            }
            return dualTimer.getSubTimer();
        }

        @Override
        public Counter getBluetoothScanResultBgCounter() {
            return this.mBluetoothScanResultBgCounter;
        }

        @Override
        public Counter getBluetoothScanResultCounter() {
            return this.mBluetoothScanResultCounter;
        }

        @Override
        public Timer getBluetoothScanTimer() {
            return this.mBluetoothScanTimer;
        }

        @Override
        public Timer getBluetoothUnoptimizedScanBackgroundTimer() {
            DualTimer dualTimer = this.mBluetoothUnoptimizedScanTimer;
            if (dualTimer == null) {
                return null;
            }
            return dualTimer.getSubTimer();
        }

        @Override
        public Timer getBluetoothUnoptimizedScanTimer() {
            return this.mBluetoothUnoptimizedScanTimer;
        }

        @Override
        public Timer getCameraTurnedOnTimer() {
            return this.mCameraTurnedOnTimer;
        }

        @Override
        public long getCpuActiveTime() {
            return this.mCpuActiveTimeMs.getCountLocked(0);
        }

        @Override
        public long[] getCpuClusterTimes() {
            return this.nullIfAllZeros(this.mCpuClusterTimesMs, 0);
        }

        @Override
        public long[] getCpuFreqTimes(int n) {
            return this.nullIfAllZeros(this.mCpuFreqTimeMs, n);
        }

        @Override
        public long[] getCpuFreqTimes(int n, int n2) {
            if (n >= 0 && n < 7) {
                if (this.mProcStateTimeMs == null) {
                    return null;
                }
                if (!this.mBsi.mPerProcStateCpuTimesAvailable) {
                    this.mProcStateTimeMs = null;
                    return null;
                }
                return this.nullIfAllZeros(this.mProcStateTimeMs[n2], n);
            }
            return null;
        }

        @Override
        public void getDeferredJobsCheckinLineLocked(StringBuilder stringBuilder, int n) {
            stringBuilder.setLength(0);
            int n2 = this.mJobsDeferredEventCount.getCountLocked(n);
            if (n2 == 0) {
                return;
            }
            int n3 = this.mJobsDeferredCount.getCountLocked(n);
            long l = this.mJobsFreshnessTimeMs.getCountLocked(n);
            stringBuilder.append(n2);
            stringBuilder.append(',');
            stringBuilder.append(n3);
            stringBuilder.append(',');
            stringBuilder.append(l);
            for (n3 = 0; n3 < BatteryStats.JOB_FRESHNESS_BUCKETS.length; ++n3) {
                if (this.mJobsFreshnessBuckets[n3] == null) {
                    stringBuilder.append(",0");
                    continue;
                }
                stringBuilder.append(",");
                stringBuilder.append(this.mJobsFreshnessBuckets[n3].getCountLocked(n));
            }
        }

        @Override
        public void getDeferredJobsLineLocked(StringBuilder stringBuilder, int n) {
            stringBuilder.setLength(0);
            int n2 = this.mJobsDeferredEventCount.getCountLocked(n);
            if (n2 == 0) {
                return;
            }
            int n3 = this.mJobsDeferredCount.getCountLocked(n);
            long l = this.mJobsFreshnessTimeMs.getCountLocked(n);
            stringBuilder.append("times=");
            stringBuilder.append(n2);
            stringBuilder.append(", ");
            stringBuilder.append("count=");
            stringBuilder.append(n3);
            stringBuilder.append(", ");
            stringBuilder.append("totalLatencyMs=");
            stringBuilder.append(l);
            stringBuilder.append(", ");
            for (n3 = 0; n3 < BatteryStats.JOB_FRESHNESS_BUCKETS.length; ++n3) {
                stringBuilder.append("<");
                stringBuilder.append(BatteryStats.JOB_FRESHNESS_BUCKETS[n3]);
                stringBuilder.append("ms=");
                Counter[] arrcounter = this.mJobsFreshnessBuckets;
                if (arrcounter[n3] == null) {
                    stringBuilder.append("0");
                } else {
                    stringBuilder.append(arrcounter[n3].getCountLocked(n));
                }
                stringBuilder.append(" ");
            }
        }

        @Override
        public Timer getFlashlightTurnedOnTimer() {
            return this.mFlashlightTurnedOnTimer;
        }

        @Override
        public Timer getForegroundActivityTimer() {
            return this.mForegroundActivityTimer;
        }

        @Override
        public Timer getForegroundServiceTimer() {
            return this.mForegroundServiceTimer;
        }

        @Override
        public long getFullWifiLockTime(long l, int n) {
            StopwatchTimer stopwatchTimer = this.mFullWifiLockTimer;
            if (stopwatchTimer == null) {
                return 0L;
            }
            return stopwatchTimer.getTotalTimeLocked(l, n);
        }

        @Override
        public ArrayMap<String, SparseIntArray> getJobCompletionStats() {
            return this.mJobCompletions;
        }

        @Override
        public ArrayMap<String, ? extends BatteryStats.Timer> getJobStats() {
            return this.mJobStats.getMap();
        }

        @Override
        public int getMobileRadioActiveCount(int n) {
            LongSamplingCounter longSamplingCounter = this.mMobileRadioActiveCount;
            n = longSamplingCounter != null ? (int)longSamplingCounter.getCountLocked(n) : 0;
            return n;
        }

        @Override
        public long getMobileRadioActiveTime(int n) {
            LongSamplingCounter longSamplingCounter = this.mMobileRadioActiveTime;
            long l = longSamplingCounter != null ? longSamplingCounter.getCountLocked(n) : 0L;
            return l;
        }

        @Override
        public long getMobileRadioApWakeupCount(int n) {
            LongSamplingCounter longSamplingCounter = this.mMobileRadioApWakeupCount;
            if (longSamplingCounter != null) {
                return longSamplingCounter.getCountLocked(n);
            }
            return 0L;
        }

        @Override
        public BatteryStats.ControllerActivityCounter getModemControllerActivity() {
            return this.mModemControllerActivity;
        }

        @Override
        public Timer getMulticastWakelockStats() {
            return this.mWifiMulticastTimer;
        }

        @Override
        public long getNetworkActivityBytes(int n, int n2) {
            LongSamplingCounter[] arrlongSamplingCounter = this.mNetworkByteActivityCounters;
            if (arrlongSamplingCounter != null && n >= 0 && n < arrlongSamplingCounter.length) {
                return arrlongSamplingCounter[n].getCountLocked(n2);
            }
            return 0L;
        }

        @Override
        public long getNetworkActivityPackets(int n, int n2) {
            LongSamplingCounter[] arrlongSamplingCounter = this.mNetworkPacketActivityCounters;
            if (arrlongSamplingCounter != null && n >= 0 && n < arrlongSamplingCounter.length) {
                return arrlongSamplingCounter[n].getCountLocked(n2);
            }
            return 0L;
        }

        public ControllerActivityCounterImpl getOrCreateBluetoothControllerActivityLocked() {
            if (this.mBluetoothControllerActivity == null) {
                this.mBluetoothControllerActivity = new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 1);
            }
            return this.mBluetoothControllerActivity;
        }

        public ControllerActivityCounterImpl getOrCreateModemControllerActivityLocked() {
            if (this.mModemControllerActivity == null) {
                this.mModemControllerActivity = new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 5);
            }
            return this.mModemControllerActivity;
        }

        public ControllerActivityCounterImpl getOrCreateWifiControllerActivityLocked() {
            if (this.mWifiControllerActivity == null) {
                this.mWifiControllerActivity = new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 1);
            }
            return this.mWifiControllerActivity;
        }

        @Override
        public ArrayMap<String, ? extends BatteryStats.Uid.Pkg> getPackageStats() {
            return this.mPackageStats;
        }

        public Pkg getPackageStatsLocked(String string2) {
            Pkg pkg;
            Pkg pkg2 = pkg = this.mPackageStats.get(string2);
            if (pkg == null) {
                pkg2 = new Pkg(this.mBsi);
                this.mPackageStats.put(string2, pkg2);
            }
            return pkg2;
        }

        @Override
        public SparseArray<? extends BatteryStats.Uid.Pid> getPidStats() {
            return this.mPids;
        }

        public BatteryStats.Uid.Pid getPidStatsLocked(int n) {
            BatteryStats.Uid.Pid pid;
            BatteryStats.Uid.Pid pid2 = pid = this.mPids.get(n);
            if (pid == null) {
                pid2 = new BatteryStats.Uid.Pid((BatteryStats.Uid)this);
                this.mPids.put(n, pid2);
            }
            return pid2;
        }

        @Override
        public long getProcessStateTime(int n, long l, int n2) {
            if (n >= 0 && n < 7) {
                StopwatchTimer[] arrstopwatchTimer = this.mProcessStateTimer;
                if (arrstopwatchTimer[n] == null) {
                    return 0L;
                }
                return arrstopwatchTimer[n].getTotalTimeLocked(l, n2);
            }
            return 0L;
        }

        @Override
        public Timer getProcessStateTimer(int n) {
            if (n >= 0 && n < 7) {
                return this.mProcessStateTimer[n];
            }
            return null;
        }

        @UnsupportedAppUsage
        @Override
        public ArrayMap<String, ? extends BatteryStats.Uid.Proc> getProcessStats() {
            return this.mProcessStats;
        }

        public Proc getProcessStatsLocked(String string2) {
            Proc proc;
            Proc proc2 = proc = this.mProcessStats.get(string2);
            if (proc == null) {
                proc2 = new Proc(this.mBsi, string2);
                this.mProcessStats.put(string2, proc2);
            }
            return proc2;
        }

        @Override
        public long[] getScreenOffCpuFreqTimes(int n) {
            return this.nullIfAllZeros(this.mScreenOffCpuFreqTimeMs, n);
        }

        @Override
        public long[] getScreenOffCpuFreqTimes(int n, int n2) {
            if (n >= 0 && n < 7) {
                if (this.mProcStateScreenOffTimeMs == null) {
                    return null;
                }
                if (!this.mBsi.mPerProcStateCpuTimesAvailable) {
                    this.mProcStateScreenOffTimeMs = null;
                    return null;
                }
                return this.nullIfAllZeros(this.mProcStateScreenOffTimeMs[n2], n);
            }
            return null;
        }

        @UnsupportedAppUsage
        @Override
        public SparseArray<? extends BatteryStats.Uid.Sensor> getSensorStats() {
            return this.mSensorStats;
        }

        public DualTimer getSensorTimerLocked(int n, boolean bl) {
            Object object = this.mSensorStats.get(n);
            Sensor sensor = object;
            if (object == null) {
                if (!bl) {
                    return null;
                }
                sensor = new Sensor(this.mBsi, this, n);
                this.mSensorStats.put(n, sensor);
            }
            if ((object = sensor.mTimer) != null) {
                return object;
            }
            ArrayList<StopwatchTimer> arrayList = this.mBsi.mSensorTimers.get(n);
            object = arrayList;
            if (arrayList == null) {
                object = new ArrayList();
                this.mBsi.mSensorTimers.put(n, (ArrayList<StopwatchTimer>)object);
            }
            sensor.mTimer = object = new DualTimer(this.mBsi.mClocks, this, 3, (ArrayList<StopwatchTimer>)object, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            return object;
        }

        public Pkg.Serv getServiceStatsLocked(String object, String string2) {
            Pkg pkg = this.getPackageStatsLocked((String)object);
            Pkg.Serv serv = pkg.mServiceStats.get(string2);
            object = serv;
            if (serv == null) {
                object = pkg.newServiceStatsLocked();
                pkg.mServiceStats.put(string2, (Pkg.Serv)object);
            }
            return object;
        }

        @Override
        public ArrayMap<String, ? extends BatteryStats.Timer> getSyncStats() {
            return this.mSyncStats.getMap();
        }

        @Override
        public long getSystemCpuTimeUs(int n) {
            return this.mSystemCpuTime.getCountLocked(n);
        }

        @Override
        public long getTimeAtCpuSpeed(int n, int n2, int n3) {
            LongSamplingCounter[][] arrlongSamplingCounter = this.mCpuClusterSpeedTimesUs;
            if (arrlongSamplingCounter != null && n >= 0 && n < arrlongSamplingCounter.length && (arrlongSamplingCounter = arrlongSamplingCounter[n]) != null && n2 >= 0 && n2 < arrlongSamplingCounter.length && (arrlongSamplingCounter = arrlongSamplingCounter[n2]) != null) {
                return arrlongSamplingCounter.getCountLocked(n3);
            }
            return 0L;
        }

        @UnsupportedAppUsage
        @Override
        public int getUid() {
            return this.mUid;
        }

        @Override
        public int getUserActivityCount(int n, int n2) {
            Counter[] arrcounter = this.mUserActivityCounters;
            if (arrcounter == null) {
                return 0;
            }
            return arrcounter[n].getCountLocked(n2);
        }

        @Override
        public long getUserCpuTimeUs(int n) {
            return this.mUserCpuTime.getCountLocked(n);
        }

        @Override
        public Timer getVibratorOnTimer() {
            return this.mVibratorOnTimer;
        }

        @Override
        public Timer getVideoTurnedOnTimer() {
            return this.mVideoTurnedOnTimer;
        }

        @UnsupportedAppUsage
        @Override
        public ArrayMap<String, ? extends BatteryStats.Uid.Wakelock> getWakelockStats() {
            return this.mWakelockStats.getMap();
        }

        public StopwatchTimer getWakelockTimerLocked(Wakelock object, int n) {
            DualTimer dualTimer;
            if (object == null) {
                return null;
            }
            if (n != 0) {
                StopwatchTimer stopwatchTimer;
                if (n != 1) {
                    StopwatchTimer stopwatchTimer2;
                    if (n != 2) {
                        if (n == 18) {
                            StopwatchTimer stopwatchTimer3;
                            StopwatchTimer stopwatchTimer4 = stopwatchTimer3 = ((Wakelock)object).mTimerDraw;
                            if (stopwatchTimer3 == null) {
                                ((Wakelock)object).mTimerDraw = stopwatchTimer4 = new StopwatchTimer(this.mBsi.mClocks, this, 18, this.mBsi.mDrawTimers, this.mBsi.mOnBatteryTimeBase);
                            }
                            return stopwatchTimer4;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("type=");
                        ((StringBuilder)object).append(n);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    StopwatchTimer stopwatchTimer5 = stopwatchTimer2 = ((Wakelock)object).mTimerWindow;
                    if (stopwatchTimer2 == null) {
                        ((Wakelock)object).mTimerWindow = stopwatchTimer5 = new StopwatchTimer(this.mBsi.mClocks, this, 2, this.mBsi.mWindowTimers, this.mBsi.mOnBatteryTimeBase);
                    }
                    return stopwatchTimer5;
                }
                StopwatchTimer stopwatchTimer6 = stopwatchTimer = ((Wakelock)object).mTimerFull;
                if (stopwatchTimer == null) {
                    ((Wakelock)object).mTimerFull = stopwatchTimer6 = new StopwatchTimer(this.mBsi.mClocks, this, 1, this.mBsi.mFullTimers, this.mBsi.mOnBatteryTimeBase);
                }
                return stopwatchTimer6;
            }
            DualTimer dualTimer2 = dualTimer = ((Wakelock)object).mTimerPartial;
            if (dualTimer == null) {
                ((Wakelock)object).mTimerPartial = dualTimer2 = new DualTimer(this.mBsi.mClocks, this, 0, this.mBsi.mPartialTimers, this.mBsi.mOnBatteryScreenOffTimeBase, this.mOnBatteryScreenOffBackgroundTimeBase);
            }
            return dualTimer2;
        }

        @Override
        public int getWifiBatchedScanCount(int n, int n2) {
            if (n >= 0 && n < 5) {
                StopwatchTimer[] arrstopwatchTimer = this.mWifiBatchedScanTimer;
                if (arrstopwatchTimer[n] == null) {
                    return 0;
                }
                return arrstopwatchTimer[n].getCountLocked(n2);
            }
            return 0;
        }

        @Override
        public long getWifiBatchedScanTime(int n, long l, int n2) {
            if (n >= 0 && n < 5) {
                StopwatchTimer[] arrstopwatchTimer = this.mWifiBatchedScanTimer;
                if (arrstopwatchTimer[n] == null) {
                    return 0L;
                }
                return arrstopwatchTimer[n].getTotalTimeLocked(l, n2);
            }
            return 0L;
        }

        @Override
        public BatteryStats.ControllerActivityCounter getWifiControllerActivity() {
            return this.mWifiControllerActivity;
        }

        @Override
        public long getWifiMulticastTime(long l, int n) {
            StopwatchTimer stopwatchTimer = this.mWifiMulticastTimer;
            if (stopwatchTimer == null) {
                return 0L;
            }
            return stopwatchTimer.getTotalTimeLocked(l, n);
        }

        @Override
        public long getWifiRadioApWakeupCount(int n) {
            LongSamplingCounter longSamplingCounter = this.mWifiRadioApWakeupCount;
            if (longSamplingCounter != null) {
                return longSamplingCounter.getCountLocked(n);
            }
            return 0L;
        }

        @UnsupportedAppUsage
        @Override
        public long getWifiRunningTime(long l, int n) {
            StopwatchTimer stopwatchTimer = this.mWifiRunningTimer;
            if (stopwatchTimer == null) {
                return 0L;
            }
            return stopwatchTimer.getTotalTimeLocked(l, n);
        }

        @Override
        public long getWifiScanActualTime(long l) {
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer == null) {
                return 0L;
            }
            return dualTimer.getTotalDurationMsLocked((500L + l) / 1000L) * 1000L;
        }

        @Override
        public int getWifiScanBackgroundCount(int n) {
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer != null && dualTimer.getSubTimer() != null) {
                return ((Timer)this.mWifiScanTimer.getSubTimer()).getCountLocked(n);
            }
            return 0;
        }

        @Override
        public long getWifiScanBackgroundTime(long l) {
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer != null && dualTimer.getSubTimer() != null) {
                l = (500L + l) / 1000L;
                return ((DurationTimer)this.mWifiScanTimer.getSubTimer()).getTotalDurationMsLocked(l) * 1000L;
            }
            return 0L;
        }

        @Override
        public Timer getWifiScanBackgroundTimer() {
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer == null) {
                return null;
            }
            return dualTimer.getSubTimer();
        }

        @Override
        public int getWifiScanCount(int n) {
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer == null) {
                return 0;
            }
            return dualTimer.getCountLocked(n);
        }

        @UnsupportedAppUsage
        @Override
        public long getWifiScanTime(long l, int n) {
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer == null) {
                return 0L;
            }
            return dualTimer.getTotalTimeLocked(l, n);
        }

        @Override
        public Timer getWifiScanTimer() {
            return this.mWifiScanTimer;
        }

        @Override
        public boolean hasNetworkActivity() {
            boolean bl = this.mNetworkByteActivityCounters != null;
            return bl;
        }

        @Override
        public boolean hasUserActivity() {
            boolean bl = this.mUserActivityCounters != null;
            return bl;
        }

        void initNetworkActivityLocked() {
            BatteryStatsImpl.detachIfNotNull(this.mNetworkByteActivityCounters);
            this.mNetworkByteActivityCounters = new LongSamplingCounter[10];
            BatteryStatsImpl.detachIfNotNull(this.mNetworkPacketActivityCounters);
            this.mNetworkPacketActivityCounters = new LongSamplingCounter[10];
            for (int i = 0; i < 10; ++i) {
                this.mNetworkByteActivityCounters[i] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
                this.mNetworkPacketActivityCounters[i] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveTime);
            this.mMobileRadioActiveTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveCount);
            this.mMobileRadioActiveCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
        }

        void initUserActivityLocked() {
            BatteryStatsImpl.detachIfNotNull(this.mUserActivityCounters);
            this.mUserActivityCounters = new Counter[NUM_USER_ACTIVITY_TYPES];
            for (int i = 0; i < NUM_USER_ACTIVITY_TYPES; ++i) {
                this.mUserActivityCounters[i] = new Counter(this.mBsi.mOnBatteryTimeBase);
            }
        }

        public boolean isInBackground() {
            boolean bl = this.mProcessState >= 3;
            return bl;
        }

        void makeProcessState(int n, Parcel parcel) {
            if (n >= 0 && n < 7) {
                BatteryStatsImpl.detachIfNotNull(this.mProcessStateTimer[n]);
                this.mProcessStateTimer[n] = parcel == null ? new StopwatchTimer(this.mBsi.mClocks, this, 12, null, this.mBsi.mOnBatteryTimeBase) : new StopwatchTimer(this.mBsi.mClocks, this, 12, null, this.mBsi.mOnBatteryTimeBase, parcel);
                return;
            }
        }

        void makeWifiBatchedScanBin(int n, Parcel parcel) {
            if (n >= 0 && n < 5) {
                ArrayList<StopwatchTimer> arrayList;
                ArrayList<StopwatchTimer> arrayList2 = arrayList = this.mBsi.mWifiBatchedScanTimers.get(n);
                if (arrayList == null) {
                    arrayList2 = new ArrayList();
                    this.mBsi.mWifiBatchedScanTimers.put(n, arrayList2);
                }
                BatteryStatsImpl.detachIfNotNull(this.mWifiBatchedScanTimer[n]);
                this.mWifiBatchedScanTimer[n] = parcel == null ? new StopwatchTimer(this.mBsi.mClocks, this, 11, arrayList2, this.mBsi.mOnBatteryTimeBase) : new StopwatchTimer(this.mBsi.mClocks, this, 11, arrayList2, this.mBsi.mOnBatteryTimeBase, parcel);
                return;
            }
        }

        @Override
        public void noteActivityPausedLocked(long l) {
            StopwatchTimer stopwatchTimer = this.mForegroundActivityTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopRunningLocked(l);
            }
        }

        @Override
        public void noteActivityResumedLocked(long l) {
            this.createForegroundActivityTimerLocked().startRunningLocked(l);
        }

        public void noteAudioTurnedOffLocked(long l) {
            StopwatchTimer stopwatchTimer = this.mAudioTurnedOnTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopRunningLocked(l);
            }
        }

        public void noteAudioTurnedOnLocked(long l) {
            this.createAudioTurnedOnTimerLocked().startRunningLocked(l);
        }

        public void noteBluetoothScanResultsLocked(int n) {
            this.createBluetoothScanResultCounterLocked().addAtomic(n);
            this.createBluetoothScanResultBgCounterLocked().addAtomic(n);
        }

        public void noteBluetoothScanStartedLocked(long l, boolean bl) {
            this.createBluetoothScanTimerLocked().startRunningLocked(l);
            if (bl) {
                this.createBluetoothUnoptimizedScanTimerLocked().startRunningLocked(l);
            }
        }

        public void noteBluetoothScanStoppedLocked(long l, boolean bl) {
            DualTimer dualTimer = this.mBluetoothScanTimer;
            if (dualTimer != null) {
                dualTimer.stopRunningLocked(l);
            }
            if (bl && (dualTimer = this.mBluetoothUnoptimizedScanTimer) != null) {
                dualTimer.stopRunningLocked(l);
            }
        }

        public void noteCameraTurnedOffLocked(long l) {
            StopwatchTimer stopwatchTimer = this.mCameraTurnedOnTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopRunningLocked(l);
            }
        }

        public void noteCameraTurnedOnLocked(long l) {
            this.createCameraTurnedOnTimerLocked().startRunningLocked(l);
        }

        public void noteFlashlightTurnedOffLocked(long l) {
            StopwatchTimer stopwatchTimer = this.mFlashlightTurnedOnTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopRunningLocked(l);
            }
        }

        public void noteFlashlightTurnedOnLocked(long l) {
            this.createFlashlightTurnedOnTimerLocked().startRunningLocked(l);
        }

        public void noteForegroundServicePausedLocked(long l) {
            StopwatchTimer stopwatchTimer = this.mForegroundServiceTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopRunningLocked(l);
            }
        }

        public void noteForegroundServiceResumedLocked(long l) {
            this.createForegroundServiceTimerLocked().startRunningLocked(l);
        }

        @Override
        public void noteFullWifiLockAcquiredLocked(long l) {
            if (!this.mFullWifiLockOut) {
                this.mFullWifiLockOut = true;
                if (this.mFullWifiLockTimer == null) {
                    this.mFullWifiLockTimer = new StopwatchTimer(this.mBsi.mClocks, this, 5, this.mBsi.mFullWifiLockTimers, this.mBsi.mOnBatteryTimeBase);
                }
                this.mFullWifiLockTimer.startRunningLocked(l);
            }
        }

        @Override
        public void noteFullWifiLockReleasedLocked(long l) {
            if (this.mFullWifiLockOut) {
                this.mFullWifiLockOut = false;
                this.mFullWifiLockTimer.stopRunningLocked(l);
            }
        }

        public void noteJobsDeferredLocked(int n, long l) {
            this.mJobsDeferredEventCount.addAtomic(1);
            this.mJobsDeferredCount.addAtomic(n);
            if (l != 0L) {
                this.mJobsFreshnessTimeMs.addCountLocked(l);
                for (n = 0; n < BatteryStats.JOB_FRESHNESS_BUCKETS.length; ++n) {
                    if (l >= BatteryStats.JOB_FRESHNESS_BUCKETS[n]) continue;
                    Counter[] arrcounter = this.mJobsFreshnessBuckets;
                    if (arrcounter[n] == null) {
                        arrcounter[n] = new Counter(this.mBsi.mOnBatteryTimeBase);
                    }
                    this.mJobsFreshnessBuckets[n].addAtomic(1);
                    break;
                }
            }
        }

        void noteMobileRadioActiveTimeLocked(long l) {
            if (this.mNetworkByteActivityCounters == null) {
                this.initNetworkActivityLocked();
            }
            this.mMobileRadioActiveTime.addCountLocked(l);
            this.mMobileRadioActiveCount.addCountLocked(1L);
        }

        public void noteMobileRadioApWakeupLocked() {
            if (this.mMobileRadioApWakeupCount == null) {
                this.mMobileRadioApWakeupCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
            this.mMobileRadioApWakeupCount.addCountLocked(1L);
        }

        void noteNetworkActivityLocked(int n, long l, long l2) {
            if (this.mNetworkByteActivityCounters == null) {
                this.initNetworkActivityLocked();
            }
            if (n >= 0 && n < 10) {
                this.mNetworkByteActivityCounters[n].addCountLocked(l);
                this.mNetworkPacketActivityCounters[n].addCountLocked(l2);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown network activity type ");
                stringBuilder.append(n);
                stringBuilder.append(" was specified.");
                Slog.w(BatteryStatsImpl.TAG, stringBuilder.toString(), new Throwable());
            }
        }

        public void noteResetAudioLocked(long l) {
            StopwatchTimer stopwatchTimer = this.mAudioTurnedOnTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopAllRunningLocked(l);
            }
        }

        public void noteResetBluetoothScanLocked(long l) {
            DualTimer dualTimer = this.mBluetoothScanTimer;
            if (dualTimer != null) {
                dualTimer.stopAllRunningLocked(l);
            }
            if ((dualTimer = this.mBluetoothUnoptimizedScanTimer) != null) {
                dualTimer.stopAllRunningLocked(l);
            }
        }

        public void noteResetCameraLocked(long l) {
            StopwatchTimer stopwatchTimer = this.mCameraTurnedOnTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopAllRunningLocked(l);
            }
        }

        public void noteResetFlashlightLocked(long l) {
            StopwatchTimer stopwatchTimer = this.mFlashlightTurnedOnTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopAllRunningLocked(l);
            }
        }

        public void noteResetVideoLocked(long l) {
            StopwatchTimer stopwatchTimer = this.mVideoTurnedOnTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopAllRunningLocked(l);
            }
        }

        public void noteStartGps(long l) {
            this.noteStartSensor(-10000, l);
        }

        public void noteStartJobLocked(String object, long l) {
            if ((object = this.mJobStats.startObject((String)object)) != null) {
                ((DualTimer)object).startRunningLocked(l);
            }
        }

        public void noteStartSensor(int n, long l) {
            this.getSensorTimerLocked(n, true).startRunningLocked(l);
        }

        public void noteStartSyncLocked(String object, long l) {
            if ((object = this.mSyncStats.startObject((String)object)) != null) {
                ((DualTimer)object).startRunningLocked(l);
            }
        }

        public void noteStartWakeLocked(int n, String object, int n2, long l) {
            if ((object = this.mWakelockStats.startObject((String)object)) != null) {
                this.getWakelockTimerLocked((Wakelock)object, n2).startRunningLocked(l);
            }
            if (n2 == 0) {
                this.createAggregatedPartialWakelockTimerLocked().startRunningLocked(l);
                if (n >= 0) {
                    object = this.getPidStatsLocked(n);
                    n = ((BatteryStats.Uid.Pid)object).mWakeNesting;
                    ((BatteryStats.Uid.Pid)object).mWakeNesting = n + 1;
                    if (n == 0) {
                        ((BatteryStats.Uid.Pid)object).mWakeStartMs = l;
                    }
                }
            }
        }

        public void noteStopGps(long l) {
            this.noteStopSensor(-10000, l);
        }

        public void noteStopJobLocked(String string2, long l, int n) {
            Object object = this.mJobStats.stopObject(string2);
            if (object != null) {
                ((DualTimer)object).stopRunningLocked(l);
            }
            if (this.mBsi.mOnBatteryTimeBase.isRunning()) {
                SparseIntArray sparseIntArray = this.mJobCompletions.get(string2);
                object = sparseIntArray;
                if (sparseIntArray == null) {
                    object = new SparseIntArray();
                    this.mJobCompletions.put(string2, (SparseIntArray)object);
                }
                ((SparseIntArray)object).put(n, ((SparseIntArray)object).get(n, 0) + 1);
            }
        }

        public void noteStopSensor(int n, long l) {
            DualTimer dualTimer = this.getSensorTimerLocked(n, false);
            if (dualTimer != null) {
                dualTimer.stopRunningLocked(l);
            }
        }

        public void noteStopSyncLocked(String object, long l) {
            if ((object = this.mSyncStats.stopObject((String)object)) != null) {
                ((DualTimer)object).stopRunningLocked(l);
            }
        }

        public void noteStopWakeLocked(int n, String object, int n2, long l) {
            if ((object = this.mWakelockStats.stopObject((String)object)) != null) {
                this.getWakelockTimerLocked((Wakelock)object, n2).stopRunningLocked(l);
            }
            if (n2 == 0) {
                object = this.mAggregatedPartialWakelockTimer;
                if (object != null) {
                    ((DualTimer)object).stopRunningLocked(l);
                }
                if (n >= 0 && (object = this.mPids.get(n)) != null && ((BatteryStats.Uid.Pid)object).mWakeNesting > 0) {
                    n = ((BatteryStats.Uid.Pid)object).mWakeNesting;
                    ((BatteryStats.Uid.Pid)object).mWakeNesting = n - 1;
                    if (n == 1) {
                        ((BatteryStats.Uid.Pid)object).mWakeSumMs += l - ((BatteryStats.Uid.Pid)object).mWakeStartMs;
                        ((BatteryStats.Uid.Pid)object).mWakeStartMs = 0L;
                    }
                }
            }
        }

        @Override
        public void noteUserActivityLocked(int n) {
            if (this.mUserActivityCounters == null) {
                this.initUserActivityLocked();
            }
            if (n >= 0 && n < NUM_USER_ACTIVITY_TYPES) {
                this.mUserActivityCounters[n].stepAtomic();
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown user activity type ");
                stringBuilder.append(n);
                stringBuilder.append(" was specified.");
                Slog.w(BatteryStatsImpl.TAG, stringBuilder.toString(), new Throwable());
            }
        }

        public void noteVibratorOffLocked() {
            BatchTimer batchTimer = this.mVibratorOnTimer;
            if (batchTimer != null) {
                batchTimer.abortLastDuration(this.mBsi);
            }
        }

        public void noteVibratorOnLocked(long l) {
            this.createVibratorOnTimerLocked().addDuration(this.mBsi, l);
        }

        public void noteVideoTurnedOffLocked(long l) {
            StopwatchTimer stopwatchTimer = this.mVideoTurnedOnTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopRunningLocked(l);
            }
        }

        public void noteVideoTurnedOnLocked(long l) {
            this.createVideoTurnedOnTimerLocked().startRunningLocked(l);
        }

        @Override
        public void noteWifiBatchedScanStartedLocked(int n, long l) {
            int n2 = 0;
            int n3 = n;
            for (n = n2; n3 > 8 && n < 4; n3 >>= 3, ++n) {
            }
            n3 = this.mWifiBatchedScanBinStarted;
            if (n3 == n) {
                return;
            }
            if (n3 != -1) {
                this.mWifiBatchedScanTimer[n3].stopRunningLocked(l);
            }
            this.mWifiBatchedScanBinStarted = n;
            if (this.mWifiBatchedScanTimer[n] == null) {
                this.makeWifiBatchedScanBin(n, null);
            }
            this.mWifiBatchedScanTimer[n].startRunningLocked(l);
        }

        @Override
        public void noteWifiBatchedScanStoppedLocked(long l) {
            int n = this.mWifiBatchedScanBinStarted;
            if (n != -1) {
                this.mWifiBatchedScanTimer[n].stopRunningLocked(l);
                this.mWifiBatchedScanBinStarted = -1;
            }
        }

        @Override
        public void noteWifiMulticastDisabledLocked(long l) {
            int n = this.mWifiMulticastWakelockCount;
            if (n == 0) {
                return;
            }
            this.mWifiMulticastWakelockCount = n - 1;
            if (this.mWifiMulticastWakelockCount == 0) {
                this.mWifiMulticastTimer.stopRunningLocked(l);
            }
        }

        @Override
        public void noteWifiMulticastEnabledLocked(long l) {
            if (this.mWifiMulticastWakelockCount == 0) {
                if (this.mWifiMulticastTimer == null) {
                    this.mWifiMulticastTimer = new StopwatchTimer(this.mBsi.mClocks, this, 7, this.mBsi.mWifiMulticastTimers, this.mBsi.mOnBatteryTimeBase);
                }
                this.mWifiMulticastTimer.startRunningLocked(l);
            }
            ++this.mWifiMulticastWakelockCount;
        }

        public void noteWifiRadioApWakeupLocked() {
            if (this.mWifiRadioApWakeupCount == null) {
                this.mWifiRadioApWakeupCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
            this.mWifiRadioApWakeupCount.addCountLocked(1L);
        }

        @Override
        public void noteWifiRunningLocked(long l) {
            if (!this.mWifiRunning) {
                this.mWifiRunning = true;
                if (this.mWifiRunningTimer == null) {
                    this.mWifiRunningTimer = new StopwatchTimer(this.mBsi.mClocks, this, 4, this.mBsi.mWifiRunningTimers, this.mBsi.mOnBatteryTimeBase);
                }
                this.mWifiRunningTimer.startRunningLocked(l);
            }
        }

        @Override
        public void noteWifiScanStartedLocked(long l) {
            if (!this.mWifiScanStarted) {
                this.mWifiScanStarted = true;
                if (this.mWifiScanTimer == null) {
                    this.mWifiScanTimer = new DualTimer(this.mBsi.mClocks, this, 6, this.mBsi.mWifiScanTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
                }
                this.mWifiScanTimer.startRunningLocked(l);
            }
        }

        @Override
        public void noteWifiScanStoppedLocked(long l) {
            if (this.mWifiScanStarted) {
                this.mWifiScanStarted = false;
                this.mWifiScanTimer.stopRunningLocked(l);
            }
        }

        @Override
        public void noteWifiStoppedLocked(long l) {
            if (this.mWifiRunning) {
                this.mWifiRunning = false;
                this.mWifiRunningTimer.stopRunningLocked(l);
            }
        }

        void readFromParcelLocked(TimeBase arrlongSamplingCounter, TimeBase object, Parcel parcel) {
            int n;
            int n2;
            this.mOnBatteryBackgroundTimeBase.readFromParcel(parcel);
            this.mOnBatteryScreenOffBackgroundTimeBase.readFromParcel(parcel);
            int n3 = parcel.readInt();
            this.mWakelockStats.clear();
            for (n2 = 0; n2 < n3; ++n2) {
                String string2 = parcel.readString();
                Wakelock wakelock = new Wakelock(this.mBsi, this);
                wakelock.readFromParcelLocked((TimeBase)arrlongSamplingCounter, (TimeBase)object, this.mOnBatteryScreenOffBackgroundTimeBase, parcel);
                this.mWakelockStats.add(string2, wakelock);
            }
            int n4 = parcel.readInt();
            this.mSyncStats.clear();
            n2 = n3;
            for (n = 0; n < n4; ++n) {
                arrlongSamplingCounter = parcel.readString();
                if (parcel.readInt() == 0) continue;
                this.mSyncStats.add((String)arrlongSamplingCounter, new DualTimer(this.mBsi.mClocks, this, 13, null, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, parcel));
            }
            n = parcel.readInt();
            this.mJobStats.clear();
            for (n2 = 0; n2 < n; ++n2) {
                arrlongSamplingCounter = parcel.readString();
                if (parcel.readInt() == 0) continue;
                this.mJobStats.add((String)arrlongSamplingCounter, new DualTimer(this.mBsi.mClocks, this, 14, null, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, parcel));
            }
            this.readJobCompletionsFromParcelLocked(parcel);
            this.mJobsDeferredEventCount = new Counter(this.mBsi.mOnBatteryTimeBase, parcel);
            this.mJobsDeferredCount = new Counter(this.mBsi.mOnBatteryTimeBase, parcel);
            this.mJobsFreshnessTimeMs = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            for (n2 = 0; n2 < BatteryStats.JOB_FRESHNESS_BUCKETS.length; ++n2) {
                this.mJobsFreshnessBuckets[n2] = Counter.readCounterFromParcel(this.mBsi.mOnBatteryTimeBase, parcel);
            }
            n = parcel.readInt();
            this.mSensorStats.clear();
            for (n2 = 0; n2 < n; ++n2) {
                n3 = parcel.readInt();
                arrlongSamplingCounter = new Sensor(this.mBsi, this, n3);
                arrlongSamplingCounter.readFromParcelLocked(this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, parcel);
                this.mSensorStats.put(n3, (Sensor)arrlongSamplingCounter);
            }
            n = parcel.readInt();
            this.mProcessStats.clear();
            for (n2 = 0; n2 < n; ++n2) {
                arrlongSamplingCounter = parcel.readString();
                object = new Proc(this.mBsi, (String)arrlongSamplingCounter);
                ((Proc)object).readFromParcelLocked(parcel);
                this.mProcessStats.put((String)arrlongSamplingCounter, (Proc)object);
            }
            n = parcel.readInt();
            this.mPackageStats.clear();
            for (n2 = 0; n2 < n; ++n2) {
                arrlongSamplingCounter = parcel.readString();
                object = new Pkg(this.mBsi);
                ((Pkg)object).readFromParcelLocked(parcel);
                this.mPackageStats.put((String)arrlongSamplingCounter, (Pkg)object);
            }
            this.mWifiRunning = false;
            this.mWifiRunningTimer = parcel.readInt() != 0 ? new StopwatchTimer(this.mBsi.mClocks, this, 4, this.mBsi.mWifiRunningTimers, this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mFullWifiLockOut = false;
            this.mFullWifiLockTimer = parcel.readInt() != 0 ? new StopwatchTimer(this.mBsi.mClocks, this, 5, this.mBsi.mFullWifiLockTimers, this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mWifiScanStarted = false;
            this.mWifiScanTimer = parcel.readInt() != 0 ? new DualTimer(this.mBsi.mClocks, this, 6, this.mBsi.mWifiScanTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, parcel) : null;
            this.mWifiBatchedScanBinStarted = -1;
            for (n2 = 0; n2 < 5; ++n2) {
                if (parcel.readInt() != 0) {
                    this.makeWifiBatchedScanBin(n2, parcel);
                    continue;
                }
                this.mWifiBatchedScanTimer[n2] = null;
            }
            this.mWifiMulticastWakelockCount = 0;
            this.mWifiMulticastTimer = parcel.readInt() != 0 ? new StopwatchTimer(this.mBsi.mClocks, this, 7, this.mBsi.mWifiMulticastTimers, this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mAudioTurnedOnTimer = parcel.readInt() != 0 ? new StopwatchTimer(this.mBsi.mClocks, this, 15, this.mBsi.mAudioTurnedOnTimers, this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mVideoTurnedOnTimer = parcel.readInt() != 0 ? new StopwatchTimer(this.mBsi.mClocks, this, 8, this.mBsi.mVideoTurnedOnTimers, this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mFlashlightTurnedOnTimer = parcel.readInt() != 0 ? new StopwatchTimer(this.mBsi.mClocks, this, 16, this.mBsi.mFlashlightTurnedOnTimers, this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mCameraTurnedOnTimer = parcel.readInt() != 0 ? new StopwatchTimer(this.mBsi.mClocks, this, 17, this.mBsi.mCameraTurnedOnTimers, this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mForegroundActivityTimer = parcel.readInt() != 0 ? new StopwatchTimer(this.mBsi.mClocks, this, 10, null, this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mForegroundServiceTimer = parcel.readInt() != 0 ? new StopwatchTimer(this.mBsi.mClocks, this, 22, null, this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mAggregatedPartialWakelockTimer = parcel.readInt() != 0 ? new DualTimer(this.mBsi.mClocks, this, 20, null, this.mBsi.mOnBatteryScreenOffTimeBase, this.mOnBatteryScreenOffBackgroundTimeBase, parcel) : null;
            this.mBluetoothScanTimer = parcel.readInt() != 0 ? new DualTimer(this.mBsi.mClocks, this, 19, this.mBsi.mBluetoothScanOnTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, parcel) : null;
            this.mBluetoothUnoptimizedScanTimer = parcel.readInt() != 0 ? new DualTimer(this.mBsi.mClocks, this, 21, null, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase, parcel) : null;
            this.mBluetoothScanResultCounter = parcel.readInt() != 0 ? new Counter(this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mBluetoothScanResultBgCounter = parcel.readInt() != 0 ? new Counter(this.mOnBatteryBackgroundTimeBase, parcel) : null;
            this.mProcessState = 21;
            for (n2 = 0; n2 < 7; ++n2) {
                if (parcel.readInt() != 0) {
                    this.makeProcessState(n2, parcel);
                    continue;
                }
                this.mProcessStateTimer[n2] = null;
            }
            this.mVibratorOnTimer = parcel.readInt() != 0 ? new BatchTimer(this.mBsi.mClocks, this, 9, this.mBsi.mOnBatteryTimeBase, parcel) : null;
            if (parcel.readInt() != 0) {
                this.mUserActivityCounters = new Counter[NUM_USER_ACTIVITY_TYPES];
                for (n2 = 0; n2 < NUM_USER_ACTIVITY_TYPES; ++n2) {
                    this.mUserActivityCounters[n2] = new Counter(this.mBsi.mOnBatteryTimeBase, parcel);
                }
            } else {
                this.mUserActivityCounters = null;
            }
            if (parcel.readInt() != 0) {
                this.mNetworkByteActivityCounters = new LongSamplingCounter[10];
                this.mNetworkPacketActivityCounters = new LongSamplingCounter[10];
                for (n2 = 0; n2 < 10; ++n2) {
                    this.mNetworkByteActivityCounters[n2] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
                    this.mNetworkPacketActivityCounters[n2] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
                }
                this.mMobileRadioActiveTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
                this.mMobileRadioActiveCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            } else {
                this.mNetworkByteActivityCounters = null;
                this.mNetworkPacketActivityCounters = null;
            }
            this.mWifiControllerActivity = parcel.readInt() != 0 ? new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 1, parcel) : null;
            this.mBluetoothControllerActivity = parcel.readInt() != 0 ? new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 1, parcel) : null;
            this.mModemControllerActivity = parcel.readInt() != 0 ? new ControllerActivityCounterImpl(this.mBsi.mOnBatteryTimeBase, 5, parcel) : null;
            this.mUserCpuTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            this.mSystemCpuTime = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            if (parcel.readInt() != 0) {
                n3 = parcel.readInt();
                if (this.mBsi.mPowerProfile != null && this.mBsi.mPowerProfile.getNumCpuClusters() != n3) {
                    throw new ParcelFormatException("Incompatible number of cpu clusters");
                }
                this.mCpuClusterSpeedTimesUs = new LongSamplingCounter[n3][];
                for (n2 = 0; n2 < n3; ++n2) {
                    if (parcel.readInt() != 0) {
                        n4 = parcel.readInt();
                        if (this.mBsi.mPowerProfile != null && this.mBsi.mPowerProfile.getNumSpeedStepsInCpuCluster(n2) != n4) {
                            throw new ParcelFormatException("Incompatible number of cpu speeds");
                        }
                        arrlongSamplingCounter = new LongSamplingCounter[n4];
                        this.mCpuClusterSpeedTimesUs[n2] = arrlongSamplingCounter;
                        for (n = 0; n < n4; ++n) {
                            if (parcel.readInt() == 0) continue;
                            arrlongSamplingCounter[n] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
                        }
                        continue;
                    }
                    this.mCpuClusterSpeedTimesUs[n2] = null;
                }
            } else {
                this.mCpuClusterSpeedTimesUs = null;
            }
            this.mCpuFreqTimeMs = LongSamplingCounterArray.readFromParcel(parcel, this.mBsi.mOnBatteryTimeBase);
            this.mScreenOffCpuFreqTimeMs = LongSamplingCounterArray.readFromParcel(parcel, this.mBsi.mOnBatteryScreenOffTimeBase);
            this.mCpuActiveTimeMs = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel);
            this.mCpuClusterTimesMs = new LongSamplingCounterArray(this.mBsi.mOnBatteryTimeBase, parcel);
            n = parcel.readInt();
            if (n == 7) {
                this.mProcStateTimeMs = new LongSamplingCounterArray[n];
                for (n2 = 0; n2 < n; ++n2) {
                    this.mProcStateTimeMs[n2] = LongSamplingCounterArray.readFromParcel(parcel, this.mBsi.mOnBatteryTimeBase);
                }
            } else {
                this.mProcStateTimeMs = null;
            }
            if ((n = parcel.readInt()) == 7) {
                this.mProcStateScreenOffTimeMs = new LongSamplingCounterArray[n];
                for (n2 = 0; n2 < n; ++n2) {
                    this.mProcStateScreenOffTimeMs[n2] = LongSamplingCounterArray.readFromParcel(parcel, this.mBsi.mOnBatteryScreenOffTimeBase);
                }
            } else {
                this.mProcStateScreenOffTimeMs = null;
            }
            this.mMobileRadioApWakeupCount = parcel.readInt() != 0 ? new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel) : null;
            this.mWifiRadioApWakeupCount = parcel.readInt() != 0 ? new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase, parcel) : null;
        }

        void readJobCompletionsFromParcelLocked(Parcel parcel) {
            int n = parcel.readInt();
            this.mJobCompletions.clear();
            for (int i = 0; i < n; ++i) {
                String string2 = parcel.readString();
                int n2 = parcel.readInt();
                if (n2 <= 0) continue;
                SparseIntArray sparseIntArray = new SparseIntArray();
                for (int j = 0; j < n2; ++j) {
                    sparseIntArray.put(parcel.readInt(), parcel.readInt());
                }
                this.mJobCompletions.put(string2, sparseIntArray);
            }
        }

        public void readJobSummaryFromParcelLocked(String string2, Parcel parcel) {
            DualTimer dualTimer = this.mJobStats.instantiateObject();
            dualTimer.readSummaryFromParcelLocked(parcel);
            this.mJobStats.add(string2, dualTimer);
        }

        public void readSyncSummaryFromParcelLocked(String string2, Parcel parcel) {
            DualTimer dualTimer = this.mSyncStats.instantiateObject();
            dualTimer.readSummaryFromParcelLocked(parcel);
            this.mSyncStats.add(string2, dualTimer);
        }

        public void readWakeSummaryFromParcelLocked(String string2, Parcel parcel) {
            Wakelock wakelock = new Wakelock(this.mBsi, this);
            this.mWakelockStats.add(string2, wakelock);
            if (parcel.readInt() != 0) {
                this.getWakelockTimerLocked(wakelock, 1).readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                this.getWakelockTimerLocked(wakelock, 0).readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                this.getWakelockTimerLocked(wakelock, 2).readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                this.getWakelockTimerLocked(wakelock, 18).readSummaryFromParcelLocked(parcel);
            }
        }

        public void removeIsolatedUid(int n) {
            IntArray intArray = this.mChildUids;
            n = intArray == null ? -1 : intArray.indexOf(n);
            if (n < 0) {
                return;
            }
            this.mChildUids.remove(n);
        }

        public void reportExcessiveCpuLocked(String object, long l, long l2) {
            if ((object = this.getProcessStatsLocked((String)object)) != null) {
                ((Proc)object).addExcessiveCpu(l, l2);
            }
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public boolean reset(long l, long l2) {
            Object object;
            int n = 0;
            this.mOnBatteryBackgroundTimeBase.init(l, l2);
            this.mOnBatteryScreenOffBackgroundTimeBase.init(l, l2);
            Object object2 = this.mWifiRunningTimer;
            boolean bl = true;
            if (object2 != null) {
                n = false | ((StopwatchTimer)object2).reset(false) ^ true | this.mWifiRunning;
            }
            object2 = this.mFullWifiLockTimer;
            int n2 = n;
            if (object2 != null) {
                n2 = n | ((StopwatchTimer)object2).reset(false) ^ true | this.mFullWifiLockOut;
            }
            object2 = this.mWifiScanTimer;
            n = n2;
            if (object2 != null) {
                n = n2 | ((DualTimer)object2).reset(false) ^ true | this.mWifiScanStarted;
            }
            n2 = n;
            if (this.mWifiBatchedScanTimer != null) {
                for (n2 = 0; n2 < 5; ++n2) {
                    object2 = this.mWifiBatchedScanTimer;
                    int n3 = n;
                    if (object2[n2] != null) {
                        n3 = n | object2[n2].reset(false) ^ true;
                    }
                    n = n3;
                }
                n2 = this.mWifiBatchedScanBinStarted != -1 ? 1 : 0;
                n2 = n | n2;
            }
            object2 = this.mWifiMulticastTimer;
            n = n2;
            if (object2 != null) {
                boolean bl2 = ((StopwatchTimer)object2).reset(false);
                n = this.mWifiMulticastWakelockCount > 0 ? 1 : 0;
                n = n2 | bl2 ^ true | n;
            }
            n = n | BatteryStatsImpl.resetIfNotNull(this.mAudioTurnedOnTimer, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mVideoTurnedOnTimer, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mFlashlightTurnedOnTimer, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mCameraTurnedOnTimer, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mForegroundActivityTimer, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mForegroundServiceTimer, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mAggregatedPartialWakelockTimer, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanTimer, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mBluetoothUnoptimizedScanTimer, false) ^ true;
            BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanResultCounter, false);
            BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanResultBgCounter, false);
            n2 = n;
            if (this.mProcessStateTimer != null) {
                for (n2 = 0; n2 < 7; ++n2) {
                    n |= BatteryStatsImpl.resetIfNotNull(this.mProcessStateTimer[n2], false) ^ true;
                }
                n2 = this.mProcessState != 21 ? 1 : 0;
                n2 = n | n2;
            }
            object2 = this.mVibratorOnTimer;
            n = n2;
            if (object2 != null) {
                if (((BatchTimer)object2).reset(false)) {
                    this.mVibratorOnTimer.detach();
                    this.mVibratorOnTimer = null;
                    n = n2;
                } else {
                    n = 1;
                }
            }
            BatteryStatsImpl.resetIfNotNull(this.mUserActivityCounters, false);
            BatteryStatsImpl.resetIfNotNull(this.mNetworkByteActivityCounters, false);
            BatteryStatsImpl.resetIfNotNull(this.mNetworkPacketActivityCounters, false);
            BatteryStatsImpl.resetIfNotNull(this.mMobileRadioActiveTime, false);
            BatteryStatsImpl.resetIfNotNull(this.mMobileRadioActiveCount, false);
            BatteryStatsImpl.resetIfNotNull(this.mWifiControllerActivity, false);
            BatteryStatsImpl.resetIfNotNull(this.mBluetoothControllerActivity, false);
            BatteryStatsImpl.resetIfNotNull(this.mModemControllerActivity, false);
            BatteryStatsImpl.resetIfNotNull(this.mUserCpuTime, false);
            BatteryStatsImpl.resetIfNotNull(this.mSystemCpuTime, false);
            BatteryStatsImpl.resetIfNotNull(this.mCpuClusterSpeedTimesUs, false);
            BatteryStatsImpl.resetIfNotNull(this.mCpuFreqTimeMs, false);
            BatteryStatsImpl.resetIfNotNull(this.mScreenOffCpuFreqTimeMs, false);
            BatteryStatsImpl.resetIfNotNull(this.mCpuActiveTimeMs, false);
            BatteryStatsImpl.resetIfNotNull(this.mCpuClusterTimesMs, false);
            BatteryStatsImpl.resetIfNotNull(this.mProcStateTimeMs, false);
            BatteryStatsImpl.resetIfNotNull(this.mProcStateScreenOffTimeMs, false);
            BatteryStatsImpl.resetIfNotNull(this.mMobileRadioApWakeupCount, false);
            BatteryStatsImpl.resetIfNotNull(this.mWifiRadioApWakeupCount, false);
            object2 = this.mWakelockStats.getMap();
            for (n2 = object2.size() - 1; n2 >= 0; --n2) {
                if (((Wakelock)((ArrayMap)object2).valueAt(n2)).reset()) {
                    ((ArrayMap)object2).removeAt(n2);
                    continue;
                }
                n = 1;
            }
            this.mWakelockStats.cleanup();
            object2 = this.mSyncStats.getMap();
            for (n2 = object2.size() - 1; n2 >= 0; --n2) {
                object = (DualTimer)((ArrayMap)object2).valueAt(n2);
                if (((DualTimer)object).reset(false)) {
                    ((ArrayMap)object2).removeAt(n2);
                    ((DualTimer)object).detach();
                    continue;
                }
                n = 1;
            }
            this.mSyncStats.cleanup();
            object = this.mJobStats.getMap();
            for (n2 = object.size() - 1; n2 >= 0; --n2) {
                object2 = (DualTimer)((ArrayMap)object).valueAt(n2);
                if (((DualTimer)object2).reset(false)) {
                    ((ArrayMap)object).removeAt(n2);
                    ((DualTimer)object2).detach();
                    continue;
                }
                n = 1;
            }
            this.mJobStats.cleanup();
            this.mJobCompletions.clear();
            BatteryStatsImpl.resetIfNotNull(this.mJobsDeferredEventCount, false);
            BatteryStatsImpl.resetIfNotNull(this.mJobsDeferredCount, false);
            BatteryStatsImpl.resetIfNotNull(this.mJobsFreshnessTimeMs, false);
            BatteryStatsImpl.resetIfNotNull(this.mJobsFreshnessBuckets, false);
            for (n2 = this.mSensorStats.size() - 1; n2 >= 0; --n2) {
                if (this.mSensorStats.valueAt(n2).reset()) {
                    this.mSensorStats.removeAt(n2);
                    continue;
                }
                n = 1;
            }
            for (n2 = this.mProcessStats.size() - 1; n2 >= 0; --n2) {
                this.mProcessStats.valueAt(n2).detach();
            }
            this.mProcessStats.clear();
            for (n2 = this.mPids.size() - 1; n2 >= 0; --n2) {
                if (this.mPids.valueAt((int)n2).mWakeNesting > 0) {
                    n = 1;
                    continue;
                }
                this.mPids.removeAt(n2);
            }
            for (n2 = this.mPackageStats.size() - 1; n2 >= 0; --n2) {
                this.mPackageStats.valueAt(n2).detach();
            }
            this.mPackageStats.clear();
            this.mLastStepSystemTime = 0L;
            this.mLastStepUserTime = 0L;
            this.mCurStepSystemTime = 0L;
            this.mCurStepUserTime = 0L;
            if (n != 0) {
                bl = false;
            }
            return bl;
        }

        @VisibleForTesting
        public void setProcessStateForTest(int n) {
            this.mProcessState = n;
        }

        public boolean updateOnBatteryBgTimeBase(long l, long l2) {
            boolean bl = this.mBsi.mOnBatteryTimeBase.isRunning() && this.isInBackground();
            return this.mOnBatteryBackgroundTimeBase.setRunning(bl, l, l2);
        }

        public boolean updateOnBatteryScreenOffBgTimeBase(long l, long l2) {
            boolean bl = this.mBsi.mOnBatteryScreenOffTimeBase.isRunning() && this.isInBackground();
            return this.mOnBatteryScreenOffBackgroundTimeBase.setRunning(bl, l, l2);
        }

        @GuardedBy(value={"mBsi"})
        public void updateUidProcessStateLocked(int n) {
            boolean bl = ActivityManager.isForegroundService(n);
            int n2 = BatteryStats.mapToInternalProcessState(n);
            if (this.mProcessState == n2 && bl == this.mInForegroundService) {
                return;
            }
            long l = this.mBsi.mClocks.elapsedRealtime();
            if (this.mProcessState != n2) {
                long l2 = this.mBsi.mClocks.uptimeMillis();
                n = this.mProcessState;
                if (n != 21) {
                    this.mProcessStateTimer[n].stopRunningLocked(l);
                    if (this.mBsi.trackPerProcStateCpuTimes()) {
                        if (this.mBsi.mPendingUids.size() == 0) {
                            this.mBsi.mExternalSync.scheduleReadProcStateCpuTimes(this.mBsi.mOnBatteryTimeBase.isRunning(), this.mBsi.mOnBatteryScreenOffTimeBase.isRunning(), this.mBsi.mConstants.PROC_STATE_CPU_TIMES_READ_DELAY_MS);
                            BatteryStatsImpl.access$1708(this.mBsi);
                        } else {
                            BatteryStatsImpl.access$1808(this.mBsi);
                        }
                        if (this.mBsi.mPendingUids.indexOfKey(this.mUid) < 0 || ArrayUtils.contains(CRITICAL_PROC_STATES, this.mProcessState)) {
                            this.mBsi.mPendingUids.put(this.mUid, this.mProcessState);
                        }
                    } else {
                        this.mBsi.mPendingUids.clear();
                    }
                }
                this.mProcessState = n2;
                if (n2 != 21) {
                    if (this.mProcessStateTimer[n2] == null) {
                        this.makeProcessState(n2, null);
                    }
                    this.mProcessStateTimer[n2].startRunningLocked(l);
                }
                this.updateOnBatteryBgTimeBase(l2 * 1000L, l * 1000L);
                this.updateOnBatteryScreenOffBgTimeBase(l2 * 1000L, 1000L * l);
            }
            if (bl != this.mInForegroundService) {
                if (bl) {
                    this.noteForegroundServiceResumedLocked(l);
                } else {
                    this.noteForegroundServicePausedLocked(l);
                }
                this.mInForegroundService = bl;
            }
        }

        void writeJobCompletionsToParcelLocked(Parcel parcel) {
            int n = this.mJobCompletions.size();
            parcel.writeInt(n);
            for (int i = 0; i < n; ++i) {
                parcel.writeString(this.mJobCompletions.keyAt(i));
                SparseIntArray sparseIntArray = this.mJobCompletions.valueAt(i);
                int n2 = sparseIntArray.size();
                parcel.writeInt(n2);
                for (int j = 0; j < n2; ++j) {
                    parcel.writeInt(sparseIntArray.keyAt(j));
                    parcel.writeInt(sparseIntArray.valueAt(j));
                }
            }
        }

        void writeToParcelLocked(Parcel parcel, long l, long l2) {
            int n;
            this.mOnBatteryBackgroundTimeBase.writeToParcel(parcel, l, l2);
            this.mOnBatteryScreenOffBackgroundTimeBase.writeToParcel(parcel, l, l2);
            ArrayMap<String, Wakelock> arrayMap = this.mWakelockStats.getMap();
            int n2 = arrayMap.size();
            parcel.writeInt(n2);
            for (n = 0; n < n2; ++n) {
                parcel.writeString(arrayMap.keyAt(n));
                arrayMap.valueAt(n).writeToParcelLocked(parcel, l2);
            }
            LongSamplingCounterArray[] arrlongSamplingCounterArray = this.mSyncStats.getMap();
            int n3 = arrlongSamplingCounterArray.size();
            parcel.writeInt(n3);
            for (n = 0; n < n3; ++n) {
                parcel.writeString(arrlongSamplingCounterArray.keyAt(n));
                Timer.writeTimerToParcel(parcel, arrlongSamplingCounterArray.valueAt(n), l2);
            }
            LongSamplingCounter[][] arrlongSamplingCounter = this.mJobStats.getMap();
            n3 = arrlongSamplingCounter.size();
            parcel.writeInt(n3);
            for (n = 0; n < n3; ++n) {
                parcel.writeString(arrlongSamplingCounter.keyAt(n));
                Timer.writeTimerToParcel(parcel, arrlongSamplingCounter.valueAt(n), l2);
            }
            this.writeJobCompletionsToParcelLocked(parcel);
            this.mJobsDeferredEventCount.writeToParcel(parcel);
            this.mJobsDeferredCount.writeToParcel(parcel);
            this.mJobsFreshnessTimeMs.writeToParcel(parcel);
            for (n = 0; n < BatteryStats.JOB_FRESHNESS_BUCKETS.length; ++n) {
                Counter.writeCounterToParcel(parcel, this.mJobsFreshnessBuckets[n]);
            }
            n3 = this.mSensorStats.size();
            parcel.writeInt(n3);
            for (n = 0; n < n3; ++n) {
                parcel.writeInt(this.mSensorStats.keyAt(n));
                this.mSensorStats.valueAt(n).writeToParcelLocked(parcel, l2);
            }
            n3 = this.mProcessStats.size();
            parcel.writeInt(n3);
            for (n = 0; n < n3; ++n) {
                parcel.writeString(this.mProcessStats.keyAt(n));
                this.mProcessStats.valueAt(n).writeToParcelLocked(parcel);
            }
            parcel.writeInt(this.mPackageStats.size());
            for (Map.Entry arrlongSamplingCounter2 : this.mPackageStats.entrySet()) {
                parcel.writeString((String)arrlongSamplingCounter2.getKey());
                ((Pkg)arrlongSamplingCounter2.getValue()).writeToParcelLocked(parcel);
            }
            arrlongSamplingCounter = this.mWifiRunningTimer;
            n3 = 0;
            if (arrlongSamplingCounter != null) {
                parcel.writeInt(1);
                this.mWifiRunningTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mFullWifiLockTimer != null) {
                parcel.writeInt(1);
                this.mFullWifiLockTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mWifiScanTimer != null) {
                parcel.writeInt(1);
                this.mWifiScanTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            for (n = 0; n < 5; ++n) {
                if (this.mWifiBatchedScanTimer[n] != null) {
                    parcel.writeInt(1);
                    this.mWifiBatchedScanTimer[n].writeToParcel(parcel, l2);
                    continue;
                }
                parcel.writeInt(0);
            }
            if (this.mWifiMulticastTimer != null) {
                parcel.writeInt(1);
                this.mWifiMulticastTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mAudioTurnedOnTimer != null) {
                parcel.writeInt(1);
                this.mAudioTurnedOnTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mVideoTurnedOnTimer != null) {
                parcel.writeInt(1);
                this.mVideoTurnedOnTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mFlashlightTurnedOnTimer != null) {
                parcel.writeInt(1);
                this.mFlashlightTurnedOnTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mCameraTurnedOnTimer != null) {
                parcel.writeInt(1);
                this.mCameraTurnedOnTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mForegroundActivityTimer != null) {
                parcel.writeInt(1);
                this.mForegroundActivityTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mForegroundServiceTimer != null) {
                parcel.writeInt(1);
                this.mForegroundServiceTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mAggregatedPartialWakelockTimer != null) {
                parcel.writeInt(1);
                this.mAggregatedPartialWakelockTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mBluetoothScanTimer != null) {
                parcel.writeInt(1);
                this.mBluetoothScanTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mBluetoothUnoptimizedScanTimer != null) {
                parcel.writeInt(1);
                this.mBluetoothUnoptimizedScanTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mBluetoothScanResultCounter != null) {
                parcel.writeInt(1);
                this.mBluetoothScanResultCounter.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (this.mBluetoothScanResultBgCounter != null) {
                parcel.writeInt(1);
                this.mBluetoothScanResultBgCounter.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            for (n = 0; n < 7; ++n) {
                if (this.mProcessStateTimer[n] != null) {
                    parcel.writeInt(1);
                    this.mProcessStateTimer[n].writeToParcel(parcel, l2);
                    continue;
                }
                parcel.writeInt(0);
            }
            if (this.mVibratorOnTimer != null) {
                parcel.writeInt(1);
                this.mVibratorOnTimer.writeToParcel(parcel, l2);
            } else {
                parcel.writeInt(0);
            }
            if (this.mUserActivityCounters != null) {
                parcel.writeInt(1);
                for (n = 0; n < NUM_USER_ACTIVITY_TYPES; ++n) {
                    this.mUserActivityCounters[n].writeToParcel(parcel);
                }
            } else {
                parcel.writeInt(0);
            }
            if (this.mNetworkByteActivityCounters != null) {
                parcel.writeInt(1);
                for (n = 0; n < 10; ++n) {
                    this.mNetworkByteActivityCounters[n].writeToParcel(parcel);
                    this.mNetworkPacketActivityCounters[n].writeToParcel(parcel);
                }
                this.mMobileRadioActiveTime.writeToParcel(parcel);
                this.mMobileRadioActiveCount.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (this.mWifiControllerActivity != null) {
                parcel.writeInt(1);
                this.mWifiControllerActivity.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (this.mBluetoothControllerActivity != null) {
                parcel.writeInt(1);
                this.mBluetoothControllerActivity.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (this.mModemControllerActivity != null) {
                parcel.writeInt(1);
                this.mModemControllerActivity.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mUserCpuTime.writeToParcel(parcel);
            this.mSystemCpuTime.writeToParcel(parcel);
            if (this.mCpuClusterSpeedTimesUs != null) {
                parcel.writeInt(1);
                parcel.writeInt(this.mCpuClusterSpeedTimesUs.length);
                arrlongSamplingCounter = this.mCpuClusterSpeedTimesUs;
                int n4 = arrlongSamplingCounter.length;
                for (n = n3; n < n4; ++n) {
                    LongSamplingCounter[] arrlongSamplingCounter2 = arrlongSamplingCounter[n];
                    if (arrlongSamplingCounter2 != null) {
                        parcel.writeInt(1);
                        parcel.writeInt(arrlongSamplingCounter2.length);
                        for (LongSamplingCounter longSamplingCounter : arrlongSamplingCounter2) {
                            if (longSamplingCounter != null) {
                                parcel.writeInt(1);
                                longSamplingCounter.writeToParcel(parcel);
                                continue;
                            }
                            parcel.writeInt(0);
                        }
                        continue;
                    }
                    parcel.writeInt(0);
                }
            } else {
                parcel.writeInt(0);
            }
            LongSamplingCounterArray.writeToParcel(parcel, this.mCpuFreqTimeMs);
            LongSamplingCounterArray.writeToParcel(parcel, this.mScreenOffCpuFreqTimeMs);
            this.mCpuActiveTimeMs.writeToParcel(parcel);
            this.mCpuClusterTimesMs.writeToParcel(parcel);
            arrlongSamplingCounterArray = this.mProcStateTimeMs;
            if (arrlongSamplingCounterArray != null) {
                parcel.writeInt(arrlongSamplingCounterArray.length);
                arrlongSamplingCounterArray = this.mProcStateTimeMs;
                n2 = arrlongSamplingCounterArray.length;
                for (n = 0; n < n2; ++n) {
                    LongSamplingCounterArray.writeToParcel(parcel, arrlongSamplingCounterArray[n]);
                }
            } else {
                parcel.writeInt(0);
            }
            if ((arrlongSamplingCounterArray = this.mProcStateScreenOffTimeMs) != null) {
                parcel.writeInt(arrlongSamplingCounterArray.length);
                arrlongSamplingCounterArray = this.mProcStateScreenOffTimeMs;
                n2 = arrlongSamplingCounterArray.length;
                for (n = 0; n < n2; ++n) {
                    LongSamplingCounterArray.writeToParcel(parcel, arrlongSamplingCounterArray[n]);
                }
            } else {
                parcel.writeInt(0);
            }
            if (this.mMobileRadioApWakeupCount != null) {
                parcel.writeInt(1);
                this.mMobileRadioApWakeupCount.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (this.mWifiRadioApWakeupCount != null) {
                parcel.writeInt(1);
                this.mWifiRadioApWakeupCount.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
        }

        public static class Pkg
        extends BatteryStats.Uid.Pkg
        implements TimeBaseObs {
            protected BatteryStatsImpl mBsi;
            final ArrayMap<String, Serv> mServiceStats = new ArrayMap();
            ArrayMap<String, Counter> mWakeupAlarms = new ArrayMap();

            public Pkg(BatteryStatsImpl batteryStatsImpl) {
                this.mBsi = batteryStatsImpl;
                this.mBsi.mOnBatteryScreenOffTimeBase.add(this);
            }

            @Override
            public void detach() {
                int n;
                this.mBsi.mOnBatteryScreenOffTimeBase.remove(this);
                for (n = this.mWakeupAlarms.size() - 1; n >= 0; --n) {
                    BatteryStatsImpl.detachIfNotNull(this.mWakeupAlarms.valueAt(n));
                }
                for (n = this.mServiceStats.size() - 1; n >= 0; --n) {
                    BatteryStatsImpl.detachIfNotNull(this.mServiceStats.valueAt(n));
                }
            }

            @Override
            public ArrayMap<String, ? extends BatteryStats.Uid.Pkg.Serv> getServiceStats() {
                return this.mServiceStats;
            }

            @Override
            public ArrayMap<String, ? extends BatteryStats.Counter> getWakeupAlarmStats() {
                return this.mWakeupAlarms;
            }

            final Serv newServiceStatsLocked() {
                return new Serv(this.mBsi);
            }

            public void noteWakeupAlarmLocked(String string2) {
                Counter counter;
                Counter counter2 = counter = this.mWakeupAlarms.get(string2);
                if (counter == null) {
                    counter2 = new Counter(this.mBsi.mOnBatteryScreenOffTimeBase);
                    this.mWakeupAlarms.put(string2, counter2);
                }
                counter2.stepAtomic();
            }

            @Override
            public void onTimeStarted(long l, long l2, long l3) {
            }

            @Override
            public void onTimeStopped(long l, long l2, long l3) {
            }

            void readFromParcelLocked(Parcel parcel) {
                String string2;
                int n;
                int n2 = parcel.readInt();
                this.mWakeupAlarms.clear();
                for (n = 0; n < n2; ++n) {
                    string2 = parcel.readString();
                    this.mWakeupAlarms.put(string2, new Counter(this.mBsi.mOnBatteryScreenOffTimeBase, parcel));
                }
                n2 = parcel.readInt();
                this.mServiceStats.clear();
                for (n = 0; n < n2; ++n) {
                    string2 = parcel.readString();
                    Serv serv = new Serv(this.mBsi);
                    this.mServiceStats.put(string2, serv);
                    serv.readFromParcelLocked(parcel);
                }
            }

            @Override
            public boolean reset(boolean bl) {
                if (bl) {
                    this.detach();
                }
                return true;
            }

            void writeToParcelLocked(Parcel parcel) {
                int n;
                int n2 = this.mWakeupAlarms.size();
                parcel.writeInt(n2);
                for (n = 0; n < n2; ++n) {
                    parcel.writeString(this.mWakeupAlarms.keyAt(n));
                    this.mWakeupAlarms.valueAt(n).writeToParcel(parcel);
                }
                n2 = this.mServiceStats.size();
                parcel.writeInt(n2);
                for (n = 0; n < n2; ++n) {
                    parcel.writeString(this.mServiceStats.keyAt(n));
                    this.mServiceStats.valueAt(n).writeToParcelLocked(parcel);
                }
            }

            public static class Serv
            extends BatteryStats.Uid.Pkg.Serv
            implements TimeBaseObs {
                protected BatteryStatsImpl mBsi;
                protected boolean mLaunched;
                protected long mLaunchedSince;
                protected long mLaunchedTime;
                protected int mLaunches;
                protected Pkg mPkg;
                protected boolean mRunning;
                protected long mRunningSince;
                protected long mStartTime;
                protected int mStarts;

                public Serv(BatteryStatsImpl batteryStatsImpl) {
                    this.mBsi = batteryStatsImpl;
                    this.mBsi.mOnBatteryTimeBase.add(this);
                }

                @Override
                public void detach() {
                    this.mBsi.mOnBatteryTimeBase.remove(this);
                }

                @UnsupportedAppUsage
                public BatteryStatsImpl getBatteryStats() {
                    return this.mBsi;
                }

                public long getLaunchTimeToNowLocked(long l) {
                    if (!this.mLaunched) {
                        return this.mLaunchedTime;
                    }
                    return this.mLaunchedTime + l - this.mLaunchedSince;
                }

                @Override
                public int getLaunches(int n) {
                    return this.mLaunches;
                }

                @Override
                public long getStartTime(long l, int n) {
                    return this.getStartTimeToNowLocked(l);
                }

                public long getStartTimeToNowLocked(long l) {
                    if (!this.mRunning) {
                        return this.mStartTime;
                    }
                    return this.mStartTime + l - this.mRunningSince;
                }

                @Override
                public int getStarts(int n) {
                    return this.mStarts;
                }

                @Override
                public void onTimeStarted(long l, long l2, long l3) {
                }

                @Override
                public void onTimeStopped(long l, long l2, long l3) {
                }

                public void readFromParcelLocked(Parcel parcel) {
                    this.mStartTime = parcel.readLong();
                    this.mRunningSince = parcel.readLong();
                    int n = parcel.readInt();
                    boolean bl = true;
                    boolean bl2 = n != 0;
                    this.mRunning = bl2;
                    this.mStarts = parcel.readInt();
                    this.mLaunchedTime = parcel.readLong();
                    this.mLaunchedSince = parcel.readLong();
                    bl2 = parcel.readInt() != 0 ? bl : false;
                    this.mLaunched = bl2;
                    this.mLaunches = parcel.readInt();
                }

                @Override
                public boolean reset(boolean bl) {
                    if (bl) {
                        this.detach();
                    }
                    return true;
                }

                @UnsupportedAppUsage
                public void startLaunchedLocked() {
                    if (!this.mLaunched) {
                        ++this.mLaunches;
                        this.mLaunchedSince = this.mBsi.getBatteryUptimeLocked();
                        this.mLaunched = true;
                    }
                }

                @UnsupportedAppUsage
                public void startRunningLocked() {
                    if (!this.mRunning) {
                        ++this.mStarts;
                        this.mRunningSince = this.mBsi.getBatteryUptimeLocked();
                        this.mRunning = true;
                    }
                }

                @UnsupportedAppUsage
                public void stopLaunchedLocked() {
                    if (this.mLaunched) {
                        long l = this.mBsi.getBatteryUptimeLocked() - this.mLaunchedSince;
                        if (l > 0L) {
                            this.mLaunchedTime += l;
                        } else {
                            --this.mLaunches;
                        }
                        this.mLaunched = false;
                    }
                }

                @UnsupportedAppUsage
                public void stopRunningLocked() {
                    if (this.mRunning) {
                        long l = this.mBsi.getBatteryUptimeLocked() - this.mRunningSince;
                        if (l > 0L) {
                            this.mStartTime += l;
                        } else {
                            --this.mStarts;
                        }
                        this.mRunning = false;
                    }
                }

                public void writeToParcelLocked(Parcel parcel) {
                    parcel.writeLong(this.mStartTime);
                    parcel.writeLong(this.mRunningSince);
                    parcel.writeInt((int)this.mRunning);
                    parcel.writeInt(this.mStarts);
                    parcel.writeLong(this.mLaunchedTime);
                    parcel.writeLong(this.mLaunchedSince);
                    parcel.writeInt((int)this.mLaunched);
                    parcel.writeInt(this.mLaunches);
                }
            }

        }

        public static class Proc
        extends BatteryStats.Uid.Proc
        implements TimeBaseObs {
            boolean mActive = true;
            protected BatteryStatsImpl mBsi;
            ArrayList<BatteryStats.Uid.Proc.ExcessivePower> mExcessivePower;
            long mForegroundTime;
            final String mName;
            int mNumAnrs;
            int mNumCrashes;
            int mStarts;
            long mSystemTime;
            long mUserTime;

            public Proc(BatteryStatsImpl batteryStatsImpl, String string2) {
                this.mBsi = batteryStatsImpl;
                this.mName = string2;
                this.mBsi.mOnBatteryTimeBase.add(this);
            }

            @UnsupportedAppUsage
            public void addCpuTimeLocked(int n, int n2) {
                this.addCpuTimeLocked(n, n2, this.mBsi.mOnBatteryTimeBase.isRunning());
            }

            public void addCpuTimeLocked(int n, int n2, boolean bl) {
                if (bl) {
                    this.mUserTime += (long)n;
                    this.mSystemTime += (long)n2;
                }
            }

            public void addExcessiveCpu(long l, long l2) {
                if (this.mExcessivePower == null) {
                    this.mExcessivePower = new ArrayList();
                }
                BatteryStats.Uid.Proc.ExcessivePower excessivePower = new BatteryStats.Uid.Proc.ExcessivePower();
                excessivePower.type = 2;
                excessivePower.overTime = l;
                excessivePower.usedTime = l2;
                this.mExcessivePower.add(excessivePower);
            }

            @UnsupportedAppUsage
            public void addForegroundTimeLocked(long l) {
                this.mForegroundTime += l;
            }

            @Override
            public int countExcessivePowers() {
                ArrayList<BatteryStats.Uid.Proc.ExcessivePower> arrayList = this.mExcessivePower;
                int n = arrayList != null ? arrayList.size() : 0;
                return n;
            }

            @Override
            public void detach() {
                this.mActive = false;
                this.mBsi.mOnBatteryTimeBase.remove(this);
            }

            @Override
            public BatteryStats.Uid.Proc.ExcessivePower getExcessivePower(int n) {
                ArrayList<BatteryStats.Uid.Proc.ExcessivePower> arrayList = this.mExcessivePower;
                if (arrayList != null) {
                    return arrayList.get(n);
                }
                return null;
            }

            @UnsupportedAppUsage
            @Override
            public long getForegroundTime(int n) {
                return this.mForegroundTime;
            }

            @Override
            public int getNumAnrs(int n) {
                return this.mNumAnrs;
            }

            @Override
            public int getNumCrashes(int n) {
                return this.mNumCrashes;
            }

            @UnsupportedAppUsage
            @Override
            public int getStarts(int n) {
                return this.mStarts;
            }

            @UnsupportedAppUsage
            @Override
            public long getSystemTime(int n) {
                return this.mSystemTime;
            }

            @UnsupportedAppUsage
            @Override
            public long getUserTime(int n) {
                return this.mUserTime;
            }

            public void incNumAnrsLocked() {
                ++this.mNumAnrs;
            }

            public void incNumCrashesLocked() {
                ++this.mNumCrashes;
            }

            @UnsupportedAppUsage
            public void incStartsLocked() {
                ++this.mStarts;
            }

            @Override
            public boolean isActive() {
                return this.mActive;
            }

            @Override
            public void onTimeStarted(long l, long l2, long l3) {
            }

            @Override
            public void onTimeStopped(long l, long l2, long l3) {
            }

            void readExcessivePowerFromParcelLocked(Parcel object) {
                int n = ((Parcel)object).readInt();
                if (n == 0) {
                    this.mExcessivePower = null;
                    return;
                }
                if (n <= 10000) {
                    this.mExcessivePower = new ArrayList();
                    for (int i = 0; i < n; ++i) {
                        BatteryStats.Uid.Proc.ExcessivePower excessivePower = new BatteryStats.Uid.Proc.ExcessivePower();
                        excessivePower.type = ((Parcel)object).readInt();
                        excessivePower.overTime = ((Parcel)object).readLong();
                        excessivePower.usedTime = ((Parcel)object).readLong();
                        this.mExcessivePower.add(excessivePower);
                    }
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("File corrupt: too many excessive power entries ");
                ((StringBuilder)object).append(n);
                throw new ParcelFormatException(((StringBuilder)object).toString());
            }

            void readFromParcelLocked(Parcel parcel) {
                this.mUserTime = parcel.readLong();
                this.mSystemTime = parcel.readLong();
                this.mForegroundTime = parcel.readLong();
                this.mStarts = parcel.readInt();
                this.mNumCrashes = parcel.readInt();
                this.mNumAnrs = parcel.readInt();
                this.readExcessivePowerFromParcelLocked(parcel);
            }

            @Override
            public boolean reset(boolean bl) {
                if (bl) {
                    this.detach();
                }
                return true;
            }

            void writeExcessivePowerToParcelLocked(Parcel parcel) {
                ArrayList<BatteryStats.Uid.Proc.ExcessivePower> arrayList = this.mExcessivePower;
                if (arrayList == null) {
                    parcel.writeInt(0);
                    return;
                }
                int n = arrayList.size();
                parcel.writeInt(n);
                for (int i = 0; i < n; ++i) {
                    arrayList = this.mExcessivePower.get(i);
                    parcel.writeInt(((BatteryStats.Uid.Proc.ExcessivePower)arrayList).type);
                    parcel.writeLong(((BatteryStats.Uid.Proc.ExcessivePower)arrayList).overTime);
                    parcel.writeLong(((BatteryStats.Uid.Proc.ExcessivePower)arrayList).usedTime);
                }
            }

            void writeToParcelLocked(Parcel parcel) {
                parcel.writeLong(this.mUserTime);
                parcel.writeLong(this.mSystemTime);
                parcel.writeLong(this.mForegroundTime);
                parcel.writeInt(this.mStarts);
                parcel.writeInt(this.mNumCrashes);
                parcel.writeInt(this.mNumAnrs);
                this.writeExcessivePowerToParcelLocked(parcel);
            }
        }

        public static class Sensor
        extends BatteryStats.Uid.Sensor {
            protected BatteryStatsImpl mBsi;
            final int mHandle;
            DualTimer mTimer;
            protected Uid mUid;

            public Sensor(BatteryStatsImpl batteryStatsImpl, Uid uid, int n) {
                this.mBsi = batteryStatsImpl;
                this.mUid = uid;
                this.mHandle = n;
            }

            private DualTimer readTimersFromParcel(TimeBase timeBase, TimeBase timeBase2, Parcel parcel) {
                ArrayList<StopwatchTimer> arrayList;
                if (parcel.readInt() == 0) {
                    return null;
                }
                ArrayList<StopwatchTimer> arrayList2 = arrayList = this.mBsi.mSensorTimers.get(this.mHandle);
                if (arrayList == null) {
                    arrayList2 = new ArrayList();
                    this.mBsi.mSensorTimers.put(this.mHandle, arrayList2);
                }
                return new DualTimer(this.mBsi.mClocks, this.mUid, 0, arrayList2, timeBase, timeBase2, parcel);
            }

            public void detachFromTimeBase() {
                BatteryStatsImpl.detachIfNotNull(this.mTimer);
            }

            @UnsupportedAppUsage
            @Override
            public int getHandle() {
                return this.mHandle;
            }

            @Override
            public Timer getSensorBackgroundTime() {
                DualTimer dualTimer = this.mTimer;
                if (dualTimer == null) {
                    return null;
                }
                return dualTimer.getSubTimer();
            }

            @UnsupportedAppUsage
            @Override
            public Timer getSensorTime() {
                return this.mTimer;
            }

            void readFromParcelLocked(TimeBase timeBase, TimeBase timeBase2, Parcel parcel) {
                this.mTimer = this.readTimersFromParcel(timeBase, timeBase2, parcel);
            }

            boolean reset() {
                if (this.mTimer.reset(true)) {
                    this.mTimer = null;
                    return true;
                }
                return false;
            }

            void writeToParcelLocked(Parcel parcel, long l) {
                Timer.writeTimerToParcel(parcel, this.mTimer, l);
            }
        }

        public static class Wakelock
        extends BatteryStats.Uid.Wakelock {
            protected BatteryStatsImpl mBsi;
            StopwatchTimer mTimerDraw;
            StopwatchTimer mTimerFull;
            DualTimer mTimerPartial;
            StopwatchTimer mTimerWindow;
            protected Uid mUid;

            public Wakelock(BatteryStatsImpl batteryStatsImpl, Uid uid) {
                this.mBsi = batteryStatsImpl;
                this.mUid = uid;
            }

            private DualTimer readDualTimerFromParcel(int n, ArrayList<StopwatchTimer> arrayList, TimeBase timeBase, TimeBase timeBase2, Parcel parcel) {
                if (parcel.readInt() == 0) {
                    return null;
                }
                return new DualTimer(this.mBsi.mClocks, this.mUid, n, arrayList, timeBase, timeBase2, parcel);
            }

            private StopwatchTimer readStopwatchTimerFromParcel(int n, ArrayList<StopwatchTimer> arrayList, TimeBase timeBase, Parcel parcel) {
                if (parcel.readInt() == 0) {
                    return null;
                }
                return new StopwatchTimer(this.mBsi.mClocks, this.mUid, n, arrayList, timeBase, parcel);
            }

            public void detachFromTimeBase() {
                BatteryStatsImpl.detachIfNotNull(this.mTimerPartial);
                BatteryStatsImpl.detachIfNotNull(this.mTimerFull);
                BatteryStatsImpl.detachIfNotNull(this.mTimerWindow);
                BatteryStatsImpl.detachIfNotNull(this.mTimerDraw);
            }

            @UnsupportedAppUsage
            @Override
            public Timer getWakeTime(int n) {
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            if (n == 18) {
                                return this.mTimerDraw;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("type = ");
                            stringBuilder.append(n);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        return this.mTimerWindow;
                    }
                    return this.mTimerFull;
                }
                return this.mTimerPartial;
            }

            void readFromParcelLocked(TimeBase timeBase, TimeBase timeBase2, TimeBase timeBase3, Parcel parcel) {
                this.mTimerPartial = this.readDualTimerFromParcel(0, this.mBsi.mPartialTimers, timeBase2, timeBase3, parcel);
                this.mTimerFull = this.readStopwatchTimerFromParcel(1, this.mBsi.mFullTimers, timeBase, parcel);
                this.mTimerWindow = this.readStopwatchTimerFromParcel(2, this.mBsi.mWindowTimers, timeBase, parcel);
                this.mTimerDraw = this.readStopwatchTimerFromParcel(18, this.mBsi.mDrawTimers, timeBase, parcel);
            }

            boolean reset() {
                StopwatchTimer stopwatchTimer = this.mTimerFull;
                boolean bl = false;
                boolean bl2 = false | BatteryStatsImpl.resetIfNotNull(stopwatchTimer, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mTimerPartial, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mTimerWindow, false) ^ true | BatteryStatsImpl.resetIfNotNull(this.mTimerDraw, false) ^ true;
                if (!bl2) {
                    BatteryStatsImpl.detachIfNotNull(this.mTimerFull);
                    this.mTimerFull = null;
                    BatteryStatsImpl.detachIfNotNull(this.mTimerPartial);
                    this.mTimerPartial = null;
                    BatteryStatsImpl.detachIfNotNull(this.mTimerWindow);
                    this.mTimerWindow = null;
                    BatteryStatsImpl.detachIfNotNull(this.mTimerDraw);
                    this.mTimerDraw = null;
                }
                if (!bl2) {
                    bl = true;
                }
                return bl;
            }

            void writeToParcelLocked(Parcel parcel, long l) {
                Timer.writeTimerToParcel(parcel, this.mTimerPartial, l);
                Timer.writeTimerToParcel(parcel, this.mTimerFull, l);
                Timer.writeTimerToParcel(parcel, this.mTimerWindow, l);
                Timer.writeTimerToParcel(parcel, this.mTimerDraw, l);
            }
        }

    }

    @VisibleForTesting
    public final class UidToRemove {
        int endUid;
        int startUid;
        long timeAddedInQueue;

        public UidToRemove(int n, int n2, long l) {
            this.startUid = n;
            this.endUid = n2;
            this.timeAddedInQueue = l;
        }

        public UidToRemove(int n, long l) {
            this(n, n, l);
        }

        void remove() {
            int n = this.startUid;
            int n2 = this.endUid;
            if (n == n2) {
                BatteryStatsImpl.this.mCpuUidUserSysTimeReader.removeUid(this.startUid);
                BatteryStatsImpl.this.mCpuUidFreqTimeReader.removeUid(this.startUid);
                if (BatteryStatsImpl.this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                    BatteryStatsImpl.this.mCpuUidActiveTimeReader.removeUid(this.startUid);
                    BatteryStatsImpl.this.mCpuUidClusterTimeReader.removeUid(this.startUid);
                }
                if (BatteryStatsImpl.this.mKernelSingleUidTimeReader != null) {
                    BatteryStatsImpl.this.mKernelSingleUidTimeReader.removeUid(this.startUid);
                }
                BatteryStatsImpl.access$008(BatteryStatsImpl.this);
            } else if (n < n2) {
                BatteryStatsImpl.this.mCpuUidFreqTimeReader.removeUidsInRange(this.startUid, this.endUid);
                BatteryStatsImpl.this.mCpuUidUserSysTimeReader.removeUidsInRange(this.startUid, this.endUid);
                if (BatteryStatsImpl.this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                    BatteryStatsImpl.this.mCpuUidActiveTimeReader.removeUidsInRange(this.startUid, this.endUid);
                    BatteryStatsImpl.this.mCpuUidClusterTimeReader.removeUidsInRange(this.startUid, this.endUid);
                }
                if (BatteryStatsImpl.this.mKernelSingleUidTimeReader != null) {
                    BatteryStatsImpl.this.mKernelSingleUidTimeReader.removeUidsInRange(this.startUid, this.endUid);
                }
                BatteryStatsImpl.access$008(BatteryStatsImpl.this);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("End UID ");
                stringBuilder.append(this.endUid);
                stringBuilder.append(" is smaller than start UID ");
                stringBuilder.append(this.startUid);
                Slog.w(BatteryStatsImpl.TAG, stringBuilder.toString());
            }
        }
    }

    public static abstract class UserInfoProvider {
        private int[] userIds;

        @VisibleForTesting
        public boolean exists(int n) {
            int[] arrn = this.userIds;
            boolean bl = arrn != null ? ArrayUtils.contains(arrn, n) : true;
            return bl;
        }

        protected abstract int[] getUserIds();

        @VisibleForTesting
        public final void refreshUserIds() {
            this.userIds = this.getUserIds();
        }
    }

}

