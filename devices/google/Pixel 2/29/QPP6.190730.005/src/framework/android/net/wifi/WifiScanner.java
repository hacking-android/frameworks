/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.content.Context;
import android.net.wifi.IWifiScanner;
import android.net.wifi.ScanResult;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.WorkSource;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.util.AsyncChannel;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SystemApi
public class WifiScanner {
    private static final int BASE = 159744;
    public static final int CMD_DEREGISTER_SCAN_LISTENER = 159772;
    public static final int CMD_DISABLE = 159775;
    public static final int CMD_ENABLE = 159774;
    public static final int CMD_FULL_SCAN_RESULT = 159764;
    public static final int CMD_GET_SCAN_RESULTS = 159748;
    public static final int CMD_GET_SINGLE_SCAN_RESULTS = 159773;
    public static final int CMD_OP_FAILED = 159762;
    public static final int CMD_OP_SUCCEEDED = 159761;
    public static final int CMD_PNO_NETWORK_FOUND = 159770;
    public static final int CMD_REGISTER_SCAN_LISTENER = 159771;
    public static final int CMD_SCAN_RESULT = 159749;
    public static final int CMD_SINGLE_SCAN_COMPLETED = 159767;
    public static final int CMD_START_BACKGROUND_SCAN = 159746;
    public static final int CMD_START_PNO_SCAN = 159768;
    public static final int CMD_START_SINGLE_SCAN = 159765;
    public static final int CMD_STOP_BACKGROUND_SCAN = 159747;
    public static final int CMD_STOP_PNO_SCAN = 159769;
    public static final int CMD_STOP_SINGLE_SCAN = 159766;
    private static final boolean DBG = false;
    public static final String GET_AVAILABLE_CHANNELS_EXTRA = "Channels";
    private static final int INVALID_KEY = 0;
    public static final int MAX_SCAN_PERIOD_MS = 1024000;
    public static final int MIN_SCAN_PERIOD_MS = 1000;
    public static final String PNO_PARAMS_PNO_SETTINGS_KEY = "PnoSettings";
    public static final String PNO_PARAMS_SCAN_SETTINGS_KEY = "ScanSettings";
    public static final int REASON_DUPLICATE_REQEUST = -5;
    public static final int REASON_INVALID_LISTENER = -2;
    public static final int REASON_INVALID_REQUEST = -3;
    public static final int REASON_NOT_AUTHORIZED = -4;
    public static final int REASON_SUCCEEDED = 0;
    public static final int REASON_UNSPECIFIED = -1;
    @Deprecated
    public static final int REPORT_EVENT_AFTER_BUFFER_FULL = 0;
    public static final int REPORT_EVENT_AFTER_EACH_SCAN = 1;
    public static final int REPORT_EVENT_FULL_SCAN_RESULT = 2;
    public static final int REPORT_EVENT_NO_BATCH = 4;
    public static final String REQUEST_PACKAGE_NAME_KEY = "PackageName";
    public static final String SCAN_PARAMS_SCAN_SETTINGS_KEY = "ScanSettings";
    public static final String SCAN_PARAMS_WORK_SOURCE_KEY = "WorkSource";
    private static final String TAG = "WifiScanner";
    public static final int TYPE_HIGH_ACCURACY = 2;
    public static final int TYPE_LOW_LATENCY = 0;
    public static final int TYPE_LOW_POWER = 1;
    public static final int WIFI_BAND_24_GHZ = 1;
    public static final int WIFI_BAND_5_GHZ = 2;
    public static final int WIFI_BAND_5_GHZ_DFS_ONLY = 4;
    public static final int WIFI_BAND_5_GHZ_WITH_DFS = 6;
    public static final int WIFI_BAND_BOTH = 3;
    public static final int WIFI_BAND_BOTH_WITH_DFS = 7;
    public static final int WIFI_BAND_UNSPECIFIED = 0;
    private AsyncChannel mAsyncChannel;
    private Context mContext;
    private final Handler mInternalHandler;
    private int mListenerKey;
    private final SparseArray mListenerMap;
    private final Object mListenerMapLock;
    private IWifiScanner mService;

    public WifiScanner(Context object, IWifiScanner iWifiScanner, Looper looper) {
        block2 : {
            this.mListenerKey = 1;
            this.mListenerMap = new SparseArray();
            this.mListenerMapLock = new Object();
            this.mContext = object;
            this.mService = iWifiScanner;
            try {
                object = this.mService.getMessenger();
                if (object == null) break block2;
                this.mAsyncChannel = new AsyncChannel();
                this.mInternalHandler = new ServiceHandler(looper);
                this.mAsyncChannel.connectSync(this.mContext, this.mInternalHandler, (Messenger)object);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            this.mAsyncChannel.sendMessage(69633);
            return;
        }
        throw new IllegalStateException("getMessenger() returned null!  This is invalid.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int addListener(ActionListener object) {
        Object object2 = this.mListenerMapLock;
        synchronized (object2) {
            boolean bl = this.getListenerKey(object) != 0;
            int n = this.putListener(object);
            if (bl) {
                object = new OperationResult(-5, "Outstanding request with same key not stopped yet");
                Message.obtain(this.mInternalHandler, 159762, 0, n, object).sendToTarget();
                return 0;
            }
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object getListener(int n) {
        if (n == 0) {
            return null;
        }
        Object object = this.mListenerMapLock;
        synchronized (object) {
            return this.mListenerMap.get(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int getListenerKey(Object object) {
        if (object == null) {
            return 0;
        }
        Object object2 = this.mListenerMapLock;
        synchronized (object2) {
            int n = this.mListenerMap.indexOfValue(object);
            if (n != -1) return this.mListenerMap.keyAt(n);
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int putListener(Object object) {
        if (object == null) {
            return 0;
        }
        Object object2 = this.mListenerMapLock;
        synchronized (object2) {
            int n;
            do {
                n = this.mListenerKey;
                this.mListenerKey = n + 1;
            } while (n == 0);
            this.mListenerMap.put(n, object);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int removeListener(Object object) {
        int n = this.getListenerKey(object);
        if (n == 0) {
            Log.e(TAG, "listener cannot be found");
            return n;
        }
        Object object2 = this.mListenerMapLock;
        synchronized (object2) {
            this.mListenerMap.remove(n);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object removeListener(int n) {
        if (n == 0) {
            return null;
        }
        Object object = this.mListenerMapLock;
        synchronized (object) {
            Object e = this.mListenerMap.get(n);
            this.mListenerMap.remove(n);
            return e;
        }
    }

    private void startPnoScan(ScanSettings scanSettings, PnoSettings pnoSettings, int n) {
        Bundle bundle = new Bundle();
        scanSettings.isPnoScan = true;
        bundle.putParcelable("ScanSettings", scanSettings);
        bundle.putParcelable(PNO_PARAMS_PNO_SETTINGS_KEY, pnoSettings);
        this.mAsyncChannel.sendMessage(159768, 0, n, bundle);
    }

    private void validateChannel() {
        if (this.mAsyncChannel != null) {
            return;
        }
        throw new IllegalStateException("No permission to access and change wifi or a bad initialization");
    }

    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public void configureWifiChange(int n, int n2, int n3, int n4, int n5, BssidInfo[] arrbssidInfo) {
        throw new UnsupportedOperationException();
    }

    @SystemApi
    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public void configureWifiChange(WifiChangeSettings wifiChangeSettings) {
        throw new UnsupportedOperationException();
    }

    public void deregisterScanListener(ScanListener scanListener) {
        Preconditions.checkNotNull(scanListener, "listener cannot be null");
        int n = this.removeListener(scanListener);
        if (n == 0) {
            return;
        }
        this.validateChannel();
        this.mAsyncChannel.sendMessage(159772, 0, n);
    }

    public List<Integer> getAvailableChannels(int n) {
        try {
            ArrayList<Integer> arrayList = this.mService.getAvailableChannels(n).getIntegerArrayList(GET_AVAILABLE_CHANNELS_EXTRA);
            return arrayList;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public boolean getScanResults() {
        this.validateChannel();
        Bundle bundle = new Bundle();
        bundle.putString(REQUEST_PACKAGE_NAME_KEY, this.mContext.getOpPackageName());
        AsyncChannel asyncChannel = this.mAsyncChannel;
        boolean bl = false;
        if (asyncChannel.sendMessageSynchronously((int)159748, (int)0, (int)0, (Object)bundle).what == 159761) {
            bl = true;
        }
        return bl;
    }

    public List<ScanResult> getSingleScanResults() {
        this.validateChannel();
        Parcelable parcelable = new Bundle();
        parcelable.putString(REQUEST_PACKAGE_NAME_KEY, this.mContext.getOpPackageName());
        parcelable = this.mAsyncChannel.sendMessageSynchronously(159773, 0, 0, parcelable);
        if (((Message)parcelable).what == 159761) {
            return Arrays.asList(((ParcelableScanResults)((Message)parcelable).obj).getResults());
        }
        parcelable = (OperationResult)((Message)parcelable).obj;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error retrieving SingleScan results reason: ");
        stringBuilder.append(((OperationResult)parcelable).reason);
        stringBuilder.append(" description: ");
        stringBuilder.append(((OperationResult)parcelable).description);
        Log.e(TAG, stringBuilder.toString());
        return new ArrayList<ScanResult>();
    }

    public void registerScanListener(ScanListener scanListener) {
        Preconditions.checkNotNull(scanListener, "listener cannot be null");
        int n = this.addListener(scanListener);
        if (n == 0) {
            return;
        }
        this.validateChannel();
        this.mAsyncChannel.sendMessage(159771, 0, n);
    }

    public void setScanningEnabled(boolean bl) {
        this.validateChannel();
        AsyncChannel asyncChannel = this.mAsyncChannel;
        int n = bl ? 159774 : 159775;
        asyncChannel.sendMessage(n);
    }

    public void startBackgroundScan(ScanSettings scanSettings, ScanListener scanListener) {
        this.startBackgroundScan(scanSettings, scanListener, null);
    }

    public void startBackgroundScan(ScanSettings scanSettings, ScanListener object, WorkSource workSource) {
        Preconditions.checkNotNull(object, "listener cannot be null");
        int n = this.addListener((ActionListener)object);
        if (n == 0) {
            return;
        }
        this.validateChannel();
        object = new Bundle();
        ((Bundle)object).putParcelable("ScanSettings", scanSettings);
        ((Bundle)object).putParcelable(SCAN_PARAMS_WORK_SOURCE_KEY, workSource);
        ((BaseBundle)object).putString(REQUEST_PACKAGE_NAME_KEY, this.mContext.getOpPackageName());
        this.mAsyncChannel.sendMessage(159746, 0, n, object);
    }

    public void startConnectedPnoScan(ScanSettings scanSettings, PnoSettings pnoSettings, PnoScanListener pnoScanListener) {
        Preconditions.checkNotNull(pnoScanListener, "listener cannot be null");
        Preconditions.checkNotNull(pnoSettings, "pnoSettings cannot be null");
        int n = this.addListener(pnoScanListener);
        if (n == 0) {
            return;
        }
        this.validateChannel();
        pnoSettings.isConnected = true;
        this.startPnoScan(scanSettings, pnoSettings, n);
    }

    public void startDisconnectedPnoScan(ScanSettings scanSettings, PnoSettings pnoSettings, PnoScanListener pnoScanListener) {
        Preconditions.checkNotNull(pnoScanListener, "listener cannot be null");
        Preconditions.checkNotNull(pnoSettings, "pnoSettings cannot be null");
        int n = this.addListener(pnoScanListener);
        if (n == 0) {
            return;
        }
        this.validateChannel();
        pnoSettings.isConnected = false;
        this.startPnoScan(scanSettings, pnoSettings, n);
    }

    public void startScan(ScanSettings scanSettings, ScanListener scanListener) {
        this.startScan(scanSettings, scanListener, null);
    }

    public void startScan(ScanSettings scanSettings, ScanListener object, WorkSource workSource) {
        Preconditions.checkNotNull(object, "listener cannot be null");
        int n = this.addListener((ActionListener)object);
        if (n == 0) {
            return;
        }
        this.validateChannel();
        object = new Bundle();
        ((Bundle)object).putParcelable("ScanSettings", scanSettings);
        ((Bundle)object).putParcelable(SCAN_PARAMS_WORK_SOURCE_KEY, workSource);
        ((BaseBundle)object).putString(REQUEST_PACKAGE_NAME_KEY, this.mContext.getOpPackageName());
        this.mAsyncChannel.sendMessage(159765, 0, n, object);
    }

    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public void startTrackingBssids(BssidInfo[] arrbssidInfo, int n, BssidListener bssidListener) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public void startTrackingWifiChange(WifiChangeListener wifiChangeListener) {
        throw new UnsupportedOperationException();
    }

    public void stopBackgroundScan(ScanListener object) {
        Preconditions.checkNotNull(object, "listener cannot be null");
        int n = this.removeListener(object);
        if (n == 0) {
            return;
        }
        this.validateChannel();
        object = new Bundle();
        ((BaseBundle)object).putString(REQUEST_PACKAGE_NAME_KEY, this.mContext.getOpPackageName());
        this.mAsyncChannel.sendMessage(159747, 0, n, object);
    }

    public void stopPnoScan(ScanListener scanListener) {
        Preconditions.checkNotNull(scanListener, "listener cannot be null");
        int n = this.removeListener(scanListener);
        if (n == 0) {
            return;
        }
        this.validateChannel();
        this.mAsyncChannel.sendMessage(159769, 0, n);
    }

    public void stopScan(ScanListener object) {
        Preconditions.checkNotNull(object, "listener cannot be null");
        int n = this.removeListener(object);
        if (n == 0) {
            return;
        }
        this.validateChannel();
        object = new Bundle();
        ((BaseBundle)object).putString(REQUEST_PACKAGE_NAME_KEY, this.mContext.getOpPackageName());
        this.mAsyncChannel.sendMessage(159766, 0, n, object);
    }

    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public void stopTrackingBssids(BssidListener bssidListener) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public void stopTrackingWifiChange(WifiChangeListener wifiChangeListener) {
        throw new UnsupportedOperationException();
    }

    @SystemApi
    public static interface ActionListener {
        public void onFailure(int var1, String var2);

        public void onSuccess();
    }

    @Deprecated
    public static class BssidInfo {
        public String bssid;
        public int frequencyHint;
        public int high;
        public int low;
    }

    @Deprecated
    public static interface BssidListener
    extends ActionListener {
        public void onFound(ScanResult[] var1);

        public void onLost(ScanResult[] var1);
    }

    public static class ChannelSpec {
        public int dwellTimeMS;
        public int frequency;
        public boolean passive;

        public ChannelSpec(int n) {
            this.frequency = n;
            this.passive = false;
            this.dwellTimeMS = 0;
        }
    }

    @SystemApi
    @Deprecated
    public static class HotlistSettings
    implements Parcelable {
        public static final Parcelable.Creator<HotlistSettings> CREATOR = new Parcelable.Creator<HotlistSettings>(){

            @Override
            public HotlistSettings createFromParcel(Parcel parcel) {
                return new HotlistSettings();
            }

            public HotlistSettings[] newArray(int n) {
                return new HotlistSettings[n];
            }
        };
        public int apLostThreshold;
        public BssidInfo[] bssidInfos;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
        }

    }

    public static class OperationResult
    implements Parcelable {
        public static final Parcelable.Creator<OperationResult> CREATOR = new Parcelable.Creator<OperationResult>(){

            @Override
            public OperationResult createFromParcel(Parcel parcel) {
                return new OperationResult(parcel.readInt(), parcel.readString());
            }

            public OperationResult[] newArray(int n) {
                return new OperationResult[n];
            }
        };
        public String description;
        public int reason;

        public OperationResult(int n, String string2) {
            this.reason = n;
            this.description = string2;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.reason);
            parcel.writeString(this.description);
        }

    }

    public static class ParcelableScanData
    implements Parcelable {
        public static final Parcelable.Creator<ParcelableScanData> CREATOR = new Parcelable.Creator<ParcelableScanData>(){

            @Override
            public ParcelableScanData createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                ScanData[] arrscanData = new ScanData[n];
                for (int i = 0; i < n; ++i) {
                    arrscanData[i] = ScanData.CREATOR.createFromParcel(parcel);
                }
                return new ParcelableScanData(arrscanData);
            }

            public ParcelableScanData[] newArray(int n) {
                return new ParcelableScanData[n];
            }
        };
        public ScanData[] mResults;

        public ParcelableScanData(ScanData[] arrscanData) {
            this.mResults = arrscanData;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public ScanData[] getResults() {
            return this.mResults;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            ScanData[] arrscanData = this.mResults;
            if (arrscanData != null) {
                parcel.writeInt(arrscanData.length);
                for (int i = 0; i < (arrscanData = this.mResults).length; ++i) {
                    arrscanData[i].writeToParcel(parcel, n);
                }
            } else {
                parcel.writeInt(0);
            }
        }

    }

    public static class ParcelableScanResults
    implements Parcelable {
        public static final Parcelable.Creator<ParcelableScanResults> CREATOR = new Parcelable.Creator<ParcelableScanResults>(){

            @Override
            public ParcelableScanResults createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                ScanResult[] arrscanResult = new ScanResult[n];
                for (int i = 0; i < n; ++i) {
                    arrscanResult[i] = ScanResult.CREATOR.createFromParcel(parcel);
                }
                return new ParcelableScanResults(arrscanResult);
            }

            public ParcelableScanResults[] newArray(int n) {
                return new ParcelableScanResults[n];
            }
        };
        public ScanResult[] mResults;

        public ParcelableScanResults(ScanResult[] arrscanResult) {
            this.mResults = arrscanResult;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public ScanResult[] getResults() {
            return this.mResults;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            ScanResult[] arrscanResult = this.mResults;
            if (arrscanResult != null) {
                parcel.writeInt(arrscanResult.length);
                for (int i = 0; i < (arrscanResult = this.mResults).length; ++i) {
                    arrscanResult[i].writeToParcel(parcel, n);
                }
            } else {
                parcel.writeInt(0);
            }
        }

    }

    public static interface PnoScanListener
    extends ScanListener {
        public void onPnoNetworkFound(ScanResult[] var1);
    }

    public static class PnoSettings
    implements Parcelable {
        public static final Parcelable.Creator<PnoSettings> CREATOR = new Parcelable.Creator<PnoSettings>(){

            @Override
            public PnoSettings createFromParcel(Parcel parcel) {
                PnoSettings pnoSettings = new PnoSettings();
                int n = parcel.readInt();
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                pnoSettings.isConnected = bl;
                pnoSettings.min5GHzRssi = parcel.readInt();
                pnoSettings.min24GHzRssi = parcel.readInt();
                pnoSettings.initialScoreMax = parcel.readInt();
                pnoSettings.currentConnectionBonus = parcel.readInt();
                pnoSettings.sameNetworkBonus = parcel.readInt();
                pnoSettings.secureBonus = parcel.readInt();
                pnoSettings.band5GHzBonus = parcel.readInt();
                int n2 = parcel.readInt();
                pnoSettings.networkList = new PnoNetwork[n2];
                for (n = 0; n < n2; ++n) {
                    PnoNetwork pnoNetwork = new PnoNetwork(parcel.readString());
                    pnoNetwork.flags = parcel.readByte();
                    pnoNetwork.authBitField = parcel.readByte();
                    pnoNetwork.frequencies = parcel.createIntArray();
                    pnoSettings.networkList[n] = pnoNetwork;
                }
                return pnoSettings;
            }

            public PnoSettings[] newArray(int n) {
                return new PnoSettings[n];
            }
        };
        public int band5GHzBonus;
        public int currentConnectionBonus;
        public int initialScoreMax;
        public boolean isConnected;
        public int min24GHzRssi;
        public int min5GHzRssi;
        public PnoNetwork[] networkList;
        public int sameNetworkBonus;
        public int secureBonus;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt((int)this.isConnected);
            parcel.writeInt(this.min5GHzRssi);
            parcel.writeInt(this.min24GHzRssi);
            parcel.writeInt(this.initialScoreMax);
            parcel.writeInt(this.currentConnectionBonus);
            parcel.writeInt(this.sameNetworkBonus);
            parcel.writeInt(this.secureBonus);
            parcel.writeInt(this.band5GHzBonus);
            PnoNetwork[] arrpnoNetwork = this.networkList;
            if (arrpnoNetwork != null) {
                parcel.writeInt(arrpnoNetwork.length);
                for (n = 0; n < (arrpnoNetwork = this.networkList).length; ++n) {
                    parcel.writeString(arrpnoNetwork[n].ssid);
                    parcel.writeByte(this.networkList[n].flags);
                    parcel.writeByte(this.networkList[n].authBitField);
                    parcel.writeIntArray(this.networkList[n].frequencies);
                }
            } else {
                parcel.writeInt(0);
            }
        }

        public static class PnoNetwork {
            public static final byte AUTH_CODE_EAPOL = 4;
            public static final byte AUTH_CODE_OPEN = 1;
            public static final byte AUTH_CODE_PSK = 2;
            public static final byte FLAG_A_BAND = 2;
            public static final byte FLAG_DIRECTED_SCAN = 1;
            public static final byte FLAG_G_BAND = 4;
            public static final byte FLAG_SAME_NETWORK = 16;
            public static final byte FLAG_STRICT_MATCH = 8;
            public byte authBitField = (byte)(false ? 1 : 0);
            public byte flags = (byte)(false ? 1 : 0);
            public int[] frequencies = new int[0];
            public String ssid;

            public PnoNetwork(String string2) {
                this.ssid = string2;
            }
        }

    }

    public static class ScanData
    implements Parcelable {
        public static final Parcelable.Creator<ScanData> CREATOR = new Parcelable.Creator<ScanData>(){

            @Override
            public ScanData createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                int n2 = parcel.readInt();
                int n3 = parcel.readInt();
                int n4 = parcel.readInt();
                int n5 = parcel.readInt();
                ScanResult[] arrscanResult = new ScanResult[n5];
                for (int i = 0; i < n5; ++i) {
                    arrscanResult[i] = ScanResult.CREATOR.createFromParcel(parcel);
                }
                return new ScanData(n, n2, n3, n4, arrscanResult);
            }

            public ScanData[] newArray(int n) {
                return new ScanData[n];
            }
        };
        private int mBandScanned;
        private int mBucketsScanned;
        private int mFlags;
        private int mId;
        private ScanResult[] mResults;

        ScanData() {
        }

        public ScanData(int n, int n2, int n3, int n4, ScanResult[] arrscanResult) {
            this.mId = n;
            this.mFlags = n2;
            this.mBucketsScanned = n3;
            this.mBandScanned = n4;
            this.mResults = arrscanResult;
        }

        public ScanData(int n, int n2, ScanResult[] arrscanResult) {
            this.mId = n;
            this.mFlags = n2;
            this.mResults = arrscanResult;
        }

        public ScanData(ScanData scanData) {
            Object object;
            this.mId = scanData.mId;
            this.mFlags = scanData.mFlags;
            this.mBucketsScanned = scanData.mBucketsScanned;
            this.mBandScanned = scanData.mBandScanned;
            this.mResults = new ScanResult[scanData.mResults.length];
            for (int i = 0; i < ((ScanResult[])(object = scanData.mResults)).length; ++i) {
                this.mResults[i] = object = new ScanResult(object[i]);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getBandScanned() {
            return this.mBandScanned;
        }

        public int getBucketsScanned() {
            return this.mBucketsScanned;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public int getId() {
            return this.mId;
        }

        public ScanResult[] getResults() {
            return this.mResults;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            if (this.mResults != null) {
                ScanResult[] arrscanResult;
                parcel.writeInt(this.mId);
                parcel.writeInt(this.mFlags);
                parcel.writeInt(this.mBucketsScanned);
                parcel.writeInt(this.mBandScanned);
                parcel.writeInt(this.mResults.length);
                for (int i = 0; i < (arrscanResult = this.mResults).length; ++i) {
                    arrscanResult[i].writeToParcel(parcel, n);
                }
            } else {
                parcel.writeInt(0);
            }
        }

    }

    public static interface ScanListener
    extends ActionListener {
        public void onFullResult(ScanResult var1);

        public void onPeriodChanged(int var1);

        public void onResults(ScanData[] var1);
    }

    public static class ScanSettings
    implements Parcelable {
        public static final Parcelable.Creator<ScanSettings> CREATOR = new Parcelable.Creator<ScanSettings>(){

            @Override
            public ScanSettings createFromParcel(Parcel parcel) {
                int n;
                Object object;
                ScanSettings scanSettings = new ScanSettings();
                scanSettings.band = parcel.readInt();
                scanSettings.periodInMs = parcel.readInt();
                scanSettings.reportEvents = parcel.readInt();
                scanSettings.numBssidsPerScan = parcel.readInt();
                scanSettings.maxScansToCache = parcel.readInt();
                scanSettings.maxPeriodInMs = parcel.readInt();
                scanSettings.stepCount = parcel.readInt();
                boolean bl = parcel.readInt() == 1;
                scanSettings.isPnoScan = bl;
                scanSettings.type = parcel.readInt();
                bl = parcel.readInt() == 1;
                scanSettings.ignoreLocationSettings = bl;
                bl = parcel.readInt() == 1;
                scanSettings.hideFromAppOps = bl;
                int n2 = parcel.readInt();
                scanSettings.channels = new ChannelSpec[n2];
                for (n = 0; n < n2; ++n) {
                    object = new ChannelSpec(parcel.readInt());
                    ((ChannelSpec)object).dwellTimeMS = parcel.readInt();
                    bl = parcel.readInt() == 1;
                    ((ChannelSpec)object).passive = bl;
                    scanSettings.channels[n] = object;
                }
                n2 = parcel.readInt();
                scanSettings.hiddenNetworks = new HiddenNetwork[n2];
                for (n = 0; n < n2; ++n) {
                    object = parcel.readString();
                    scanSettings.hiddenNetworks[n] = new HiddenNetwork((String)object);
                }
                return scanSettings;
            }

            public ScanSettings[] newArray(int n) {
                return new ScanSettings[n];
            }
        };
        public int band;
        public ChannelSpec[] channels;
        public HiddenNetwork[] hiddenNetworks;
        @SystemApi
        public boolean hideFromAppOps;
        @SystemApi
        public boolean ignoreLocationSettings;
        public boolean isPnoScan;
        public int maxPeriodInMs;
        public int maxScansToCache;
        public int numBssidsPerScan;
        public int periodInMs;
        public int reportEvents;
        public int stepCount;
        public int type = 0;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.band);
            parcel.writeInt(this.periodInMs);
            parcel.writeInt(this.reportEvents);
            parcel.writeInt(this.numBssidsPerScan);
            parcel.writeInt(this.maxScansToCache);
            parcel.writeInt(this.maxPeriodInMs);
            parcel.writeInt(this.stepCount);
            parcel.writeInt((int)this.isPnoScan);
            parcel.writeInt(this.type);
            parcel.writeInt((int)this.ignoreLocationSettings);
            parcel.writeInt((int)this.hideFromAppOps);
            Object[] arrobject = this.channels;
            if (arrobject != null) {
                parcel.writeInt(arrobject.length);
                for (n = 0; n < (arrobject = this.channels).length; ++n) {
                    parcel.writeInt(((ChannelSpec)arrobject[n]).frequency);
                    parcel.writeInt(this.channels[n].dwellTimeMS);
                    parcel.writeInt((int)this.channels[n].passive);
                }
            } else {
                parcel.writeInt(0);
            }
            arrobject = this.hiddenNetworks;
            if (arrobject != null) {
                parcel.writeInt(arrobject.length);
                for (n = 0; n < (arrobject = this.hiddenNetworks).length; ++n) {
                    parcel.writeString(((HiddenNetwork)arrobject[n]).ssid);
                }
            } else {
                parcel.writeInt(0);
            }
        }

        public static class HiddenNetwork {
            public String ssid;

            public HiddenNetwork(String string2) {
                this.ssid = string2;
            }
        }

    }

    private class ServiceHandler
    extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void handleMessage(Message parcelable) {
            int n = parcelable.what;
            if (n == 69634) return;
            if (n != 69636) {
                Object object = WifiScanner.this.getListener(parcelable.arg2);
                if (object == null) {
                    return;
                }
                switch (parcelable.what) {
                    default: {
                        return;
                    }
                    case 159770: {
                        ((PnoScanListener)object).onPnoNetworkFound(((ParcelableScanResults)parcelable.obj).getResults());
                        return;
                    }
                    case 159767: {
                        WifiScanner.this.removeListener(parcelable.arg2);
                        return;
                    }
                    case 159764: {
                        ScanResult scanResult = (ScanResult)parcelable.obj;
                        ((ScanListener)object).onFullResult(scanResult);
                        return;
                    }
                    case 159762: {
                        OperationResult operationResult = (OperationResult)parcelable.obj;
                        ((ActionListener)object).onFailure(operationResult.reason, operationResult.description);
                        WifiScanner.this.removeListener(parcelable.arg2);
                        return;
                    }
                    case 159761: {
                        ((ActionListener)object).onSuccess();
                        return;
                    }
                    case 159749: 
                }
                ((ScanListener)object).onResults(((ParcelableScanData)parcelable.obj).getResults());
                return;
            }
            Log.e(WifiScanner.TAG, "Channel connection lost");
            WifiScanner.this.mAsyncChannel = null;
            this.getLooper().quit();
        }
    }

    @Deprecated
    public static interface WifiChangeListener
    extends ActionListener {
        public void onChanging(ScanResult[] var1);

        public void onQuiescence(ScanResult[] var1);
    }

    @SystemApi
    @Deprecated
    public static class WifiChangeSettings
    implements Parcelable {
        public static final Parcelable.Creator<WifiChangeSettings> CREATOR = new Parcelable.Creator<WifiChangeSettings>(){

            @Override
            public WifiChangeSettings createFromParcel(Parcel parcel) {
                return new WifiChangeSettings();
            }

            public WifiChangeSettings[] newArray(int n) {
                return new WifiChangeSettings[n];
            }
        };
        public BssidInfo[] bssidInfos;
        public int lostApSampleSize;
        public int minApsBreachingThreshold;
        public int periodInMs;
        public int rssiSampleSize;
        public int unchangedSampleSize;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
        }

    }

}

