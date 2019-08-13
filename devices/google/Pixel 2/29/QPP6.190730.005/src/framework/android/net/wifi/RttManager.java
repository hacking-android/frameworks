/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.MacAddress;
import android.net.wifi.ScanResult;
import android.net.wifi.rtt.RangingRequest;
import android.net.wifi.rtt.RangingResult;
import android.net.wifi.rtt.RangingResultCallback;
import android.net.wifi.rtt.WifiRttManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
@Deprecated
public class RttManager {
    public static final int BASE = 160256;
    public static final int CMD_OP_ABORTED = 160260;
    public static final int CMD_OP_DISABLE_RESPONDER = 160262;
    public static final int CMD_OP_ENABLE_RESPONDER = 160261;
    public static final int CMD_OP_ENALBE_RESPONDER_FAILED = 160264;
    public static final int CMD_OP_ENALBE_RESPONDER_SUCCEEDED = 160263;
    public static final int CMD_OP_FAILED = 160258;
    public static final int CMD_OP_REG_BINDER = 160265;
    public static final int CMD_OP_START_RANGING = 160256;
    public static final int CMD_OP_STOP_RANGING = 160257;
    public static final int CMD_OP_SUCCEEDED = 160259;
    private static final boolean DBG = false;
    public static final String DESCRIPTION_KEY = "android.net.wifi.RttManager.Description";
    public static final int PREAMBLE_HT = 2;
    public static final int PREAMBLE_LEGACY = 1;
    public static final int PREAMBLE_VHT = 4;
    public static final int REASON_INITIATOR_NOT_ALLOWED_WHEN_RESPONDER_ON = -6;
    public static final int REASON_INVALID_LISTENER = -3;
    public static final int REASON_INVALID_REQUEST = -4;
    public static final int REASON_NOT_AVAILABLE = -2;
    public static final int REASON_PERMISSION_DENIED = -5;
    public static final int REASON_UNSPECIFIED = -1;
    public static final int RTT_BW_10_SUPPORT = 2;
    public static final int RTT_BW_160_SUPPORT = 32;
    public static final int RTT_BW_20_SUPPORT = 4;
    public static final int RTT_BW_40_SUPPORT = 8;
    public static final int RTT_BW_5_SUPPORT = 1;
    public static final int RTT_BW_80_SUPPORT = 16;
    @Deprecated
    public static final int RTT_CHANNEL_WIDTH_10 = 6;
    @Deprecated
    public static final int RTT_CHANNEL_WIDTH_160 = 3;
    @Deprecated
    public static final int RTT_CHANNEL_WIDTH_20 = 0;
    @Deprecated
    public static final int RTT_CHANNEL_WIDTH_40 = 1;
    @Deprecated
    public static final int RTT_CHANNEL_WIDTH_5 = 5;
    @Deprecated
    public static final int RTT_CHANNEL_WIDTH_80 = 2;
    @Deprecated
    public static final int RTT_CHANNEL_WIDTH_80P80 = 4;
    @Deprecated
    public static final int RTT_CHANNEL_WIDTH_UNSPECIFIED = -1;
    public static final int RTT_PEER_NAN = 5;
    public static final int RTT_PEER_P2P_CLIENT = 4;
    public static final int RTT_PEER_P2P_GO = 3;
    public static final int RTT_PEER_TYPE_AP = 1;
    public static final int RTT_PEER_TYPE_STA = 2;
    @Deprecated
    public static final int RTT_PEER_TYPE_UNSPECIFIED = 0;
    public static final int RTT_STATUS_ABORTED = 8;
    public static final int RTT_STATUS_FAILURE = 1;
    public static final int RTT_STATUS_FAIL_AP_ON_DIFF_CHANNEL = 6;
    public static final int RTT_STATUS_FAIL_BUSY_TRY_LATER = 12;
    public static final int RTT_STATUS_FAIL_FTM_PARAM_OVERRIDE = 15;
    public static final int RTT_STATUS_FAIL_INVALID_TS = 9;
    public static final int RTT_STATUS_FAIL_NOT_SCHEDULED_YET = 4;
    public static final int RTT_STATUS_FAIL_NO_CAPABILITY = 7;
    public static final int RTT_STATUS_FAIL_NO_RSP = 2;
    public static final int RTT_STATUS_FAIL_PROTOCOL = 10;
    public static final int RTT_STATUS_FAIL_REJECTED = 3;
    public static final int RTT_STATUS_FAIL_SCHEDULE = 11;
    public static final int RTT_STATUS_FAIL_TM_TIMEOUT = 5;
    public static final int RTT_STATUS_INVALID_REQ = 13;
    public static final int RTT_STATUS_NO_WIFI = 14;
    public static final int RTT_STATUS_SUCCESS = 0;
    @Deprecated
    public static final int RTT_TYPE_11_MC = 4;
    @Deprecated
    public static final int RTT_TYPE_11_V = 2;
    public static final int RTT_TYPE_ONE_SIDED = 1;
    public static final int RTT_TYPE_TWO_SIDED = 2;
    @Deprecated
    public static final int RTT_TYPE_UNSPECIFIED = 0;
    private static final String TAG = "RttManager";
    private final Context mContext;
    private final WifiRttManager mNewService;
    private RttCapabilities mRttCapabilities;

    public RttManager(Context object, WifiRttManager wifiRttManager) {
        this.mNewService = wifiRttManager;
        this.mContext = object;
        boolean bl = ((Context)object).getPackageManager().hasSystemFeature("android.hardware.wifi.rtt");
        this.mRttCapabilities = new RttCapabilities();
        object = this.mRttCapabilities;
        ((RttCapabilities)object).oneSidedRttSupported = bl;
        ((RttCapabilities)object).twoSided11McRttSupported = bl;
        ((RttCapabilities)object).lciSupported = false;
        ((RttCapabilities)object).lcrSupported = false;
        ((RttCapabilities)object).preambleSupported = 6;
        ((RttCapabilities)object).bwSupported = 24;
        ((RttCapabilities)object).responderSupported = false;
        ((RttCapabilities)object).secureRttSupported = false;
    }

    public void disableResponder(ResponderCallback responderCallback) {
        throw new UnsupportedOperationException("disableResponder is not supported in the adaptation layer");
    }

    public void enableResponder(ResponderCallback responderCallback) {
        throw new UnsupportedOperationException("enableResponder is not supported in the adaptation layer");
    }

    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public Capabilities getCapabilities() {
        throw new UnsupportedOperationException("getCapabilities is not supported in the adaptation layer");
    }

    public RttCapabilities getRttCapabilities() {
        return this.mRttCapabilities;
    }

    public void startRanging(RttParams[] object, final RttListener rttListener) {
        Parcelable parcelable;
        Log.i(TAG, "Send RTT request to RTT Service");
        if (!this.mNewService.isAvailable()) {
            rttListener.onFailure(-2, "");
            return;
        }
        RangingResultCallback rangingResultCallback = new RangingRequest.Builder();
        for (Object object2 : object) {
            if (((RttParams)object2).deviceType != 1) {
                rttListener.onFailure(-4, "Only AP peers are supported");
                return;
            }
            parcelable = new ScanResult();
            ((ScanResult)parcelable).BSSID = ((RttParams)object2).bssid;
            if (((RttParams)object2).requestType == 2) {
                ((ScanResult)parcelable).setFlag(2L);
            }
            ((ScanResult)parcelable).channelWidth = ((RttParams)object2).channelWidth;
            ((ScanResult)parcelable).frequency = ((RttParams)object2).frequency;
            ((ScanResult)parcelable).centerFreq0 = ((RttParams)object2).centerFreq0;
            ((ScanResult)parcelable).centerFreq1 = ((RttParams)object2).centerFreq1;
            ((RangingRequest.Builder)((Object)rangingResultCallback)).addResponder(android.net.wifi.rtt.ResponderConfig.fromScanResult((ScanResult)parcelable));
        }
        try {
            Object object2;
            object = this.mNewService;
            parcelable = ((RangingRequest.Builder)((Object)rangingResultCallback)).build();
            object2 = this.mContext.getMainExecutor();
            rangingResultCallback = new RangingResultCallback(){

                @Override
                public void onRangingFailure(int n) {
                    int n2 = -1;
                    if (n == 2) {
                        n2 = -2;
                    }
                    rttListener.onFailure(n2, "");
                }

                @Override
                public void onRangingResults(List<RangingResult> object) {
                    RttResult[] arrrttResult = new RttResult[object.size()];
                    int n = 0;
                    object = object.iterator();
                    while (object.hasNext()) {
                        RangingResult rangingResult = (RangingResult)object.next();
                        arrrttResult[n] = new RttResult();
                        arrrttResult[n].status = rangingResult.getStatus();
                        arrrttResult[n].bssid = rangingResult.getMacAddress().toString();
                        if (rangingResult.getStatus() == 0) {
                            arrrttResult[n].distance = rangingResult.getDistanceMm() / 10;
                            arrrttResult[n].distanceStandardDeviation = rangingResult.getDistanceStdDevMm() / 10;
                            arrrttResult[n].rssi = rangingResult.getRssi() * -2;
                            arrrttResult[n].ts = rangingResult.getRangingTimestampMillis() * 1000L;
                            arrrttResult[n].measurementFrameNumber = rangingResult.getNumAttemptedMeasurements();
                            arrrttResult[n].successMeasurementFrameNumber = rangingResult.getNumSuccessfulMeasurements();
                        } else {
                            arrrttResult[n].ts = SystemClock.elapsedRealtime() * 1000L;
                        }
                        ++n;
                    }
                    rttListener.onSuccess(arrrttResult);
                }
            };
            ((WifiRttManager)object).startRanging((RangingRequest)parcelable, (Executor)object2, rangingResultCallback);
        }
        catch (SecurityException securityException) {
            rangingResultCallback = new StringBuilder();
            ((StringBuilder)((Object)rangingResultCallback)).append("startRanging: security exception - ");
            ((StringBuilder)((Object)rangingResultCallback)).append(securityException);
            Log.e(TAG, ((StringBuilder)((Object)rangingResultCallback)).toString());
            rttListener.onFailure(-5, securityException.getMessage());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            rangingResultCallback = new StringBuilder();
            ((StringBuilder)((Object)rangingResultCallback)).append("startRanging: invalid arguments - ");
            ((StringBuilder)((Object)rangingResultCallback)).append(illegalArgumentException);
            Log.e(TAG, ((StringBuilder)((Object)rangingResultCallback)).toString());
            rttListener.onFailure(-4, illegalArgumentException.getMessage());
        }
    }

    public void stopRanging(RttListener rttListener) {
        Log.e(TAG, "stopRanging: unsupported operation - nop");
    }

    @Deprecated
    public class Capabilities {
        public int supportedPeerType;
        public int supportedType;
    }

    @Deprecated
    public static class ParcelableRttParams
    implements Parcelable {
        public static final Parcelable.Creator<ParcelableRttParams> CREATOR = new Parcelable.Creator<ParcelableRttParams>(){

            @Override
            public ParcelableRttParams createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                RttParams[] arrrttParams = new RttParams[n];
                for (int i = 0; i < n; ++i) {
                    arrrttParams[i] = new RttParams();
                    arrrttParams[i].deviceType = parcel.readInt();
                    arrrttParams[i].requestType = parcel.readInt();
                    RttParams rttParams = arrrttParams[i];
                    byte by = parcel.readByte();
                    boolean bl = false;
                    boolean bl2 = by != 0;
                    rttParams.secure = bl2;
                    arrrttParams[i].bssid = parcel.readString();
                    arrrttParams[i].channelWidth = parcel.readInt();
                    arrrttParams[i].frequency = parcel.readInt();
                    arrrttParams[i].centerFreq0 = parcel.readInt();
                    arrrttParams[i].centerFreq1 = parcel.readInt();
                    arrrttParams[i].numberBurst = parcel.readInt();
                    arrrttParams[i].interval = parcel.readInt();
                    arrrttParams[i].numSamplesPerBurst = parcel.readInt();
                    arrrttParams[i].numRetriesPerMeasurementFrame = parcel.readInt();
                    arrrttParams[i].numRetriesPerFTMR = parcel.readInt();
                    rttParams = arrrttParams[i];
                    bl2 = parcel.readInt() == 1;
                    rttParams.LCIRequest = bl2;
                    rttParams = arrrttParams[i];
                    bl2 = bl;
                    if (parcel.readInt() == 1) {
                        bl2 = true;
                    }
                    rttParams.LCRRequest = bl2;
                    arrrttParams[i].burstTimeout = parcel.readInt();
                    arrrttParams[i].preamble = parcel.readInt();
                    arrrttParams[i].bandwidth = parcel.readInt();
                }
                return new ParcelableRttParams(arrrttParams);
            }

            public ParcelableRttParams[] newArray(int n) {
                return new ParcelableRttParams[n];
            }
        };
        public RttParams[] mParams;

        @VisibleForTesting
        public ParcelableRttParams(RttParams[] arrrttParams) {
            if (arrrttParams == null) {
                arrrttParams = new RttParams[]{};
            }
            this.mParams = arrrttParams;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mParams.length);
            for (RttParams rttParams : this.mParams) {
                parcel.writeInt(rttParams.deviceType);
                parcel.writeInt(rttParams.requestType);
                parcel.writeByte((byte)rttParams.secure);
                parcel.writeString(rttParams.bssid);
                parcel.writeInt(rttParams.channelWidth);
                parcel.writeInt(rttParams.frequency);
                parcel.writeInt(rttParams.centerFreq0);
                parcel.writeInt(rttParams.centerFreq1);
                parcel.writeInt(rttParams.numberBurst);
                parcel.writeInt(rttParams.interval);
                parcel.writeInt(rttParams.numSamplesPerBurst);
                parcel.writeInt(rttParams.numRetriesPerMeasurementFrame);
                parcel.writeInt(rttParams.numRetriesPerFTMR);
                parcel.writeInt((int)rttParams.LCIRequest);
                parcel.writeInt((int)rttParams.LCRRequest);
                parcel.writeInt(rttParams.burstTimeout);
                parcel.writeInt(rttParams.preamble);
                parcel.writeInt(rttParams.bandwidth);
            }
        }

    }

    @Deprecated
    public static class ParcelableRttResults
    implements Parcelable {
        public static final Parcelable.Creator<ParcelableRttResults> CREATOR = new Parcelable.Creator<ParcelableRttResults>(){

            @Override
            public ParcelableRttResults createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                if (n == 0) {
                    return new ParcelableRttResults(null);
                }
                RttResult[] arrrttResult = new RttResult[n];
                for (int i = 0; i < n; ++i) {
                    byte by;
                    arrrttResult[i] = new RttResult();
                    arrrttResult[i].bssid = parcel.readString();
                    arrrttResult[i].burstNumber = parcel.readInt();
                    arrrttResult[i].measurementFrameNumber = parcel.readInt();
                    arrrttResult[i].successMeasurementFrameNumber = parcel.readInt();
                    arrrttResult[i].frameNumberPerBurstPeer = parcel.readInt();
                    arrrttResult[i].status = parcel.readInt();
                    arrrttResult[i].measurementType = parcel.readInt();
                    arrrttResult[i].retryAfterDuration = parcel.readInt();
                    arrrttResult[i].ts = parcel.readLong();
                    arrrttResult[i].rssi = parcel.readInt();
                    arrrttResult[i].rssiSpread = parcel.readInt();
                    arrrttResult[i].txRate = parcel.readInt();
                    arrrttResult[i].rtt = parcel.readLong();
                    arrrttResult[i].rttStandardDeviation = parcel.readLong();
                    arrrttResult[i].rttSpread = parcel.readLong();
                    arrrttResult[i].distance = parcel.readInt();
                    arrrttResult[i].distanceStandardDeviation = parcel.readInt();
                    arrrttResult[i].distanceSpread = parcel.readInt();
                    arrrttResult[i].burstDuration = parcel.readInt();
                    arrrttResult[i].negotiatedBurstNum = parcel.readInt();
                    arrrttResult[i].LCI = new WifiInformationElement();
                    arrrttResult[i].LCI.id = parcel.readByte();
                    if (arrrttResult[i].LCI.id != -1) {
                        by = parcel.readByte();
                        arrrttResult[i].LCI.data = new byte[by];
                        parcel.readByteArray(arrrttResult[i].LCI.data);
                    }
                    arrrttResult[i].LCR = new WifiInformationElement();
                    arrrttResult[i].LCR.id = parcel.readByte();
                    if (arrrttResult[i].LCR.id != -1) {
                        by = parcel.readByte();
                        arrrttResult[i].LCR.data = new byte[by];
                        parcel.readByteArray(arrrttResult[i].LCR.data);
                    }
                    RttResult rttResult = arrrttResult[i];
                    boolean bl = parcel.readByte() != 0;
                    rttResult.secure = bl;
                }
                return new ParcelableRttResults(arrrttResult);
            }

            public ParcelableRttResults[] newArray(int n) {
                return new ParcelableRttResults[n];
            }
        };
        public RttResult[] mResults;

        public ParcelableRttResults(RttResult[] arrrttResult) {
            this.mResults = arrrttResult;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < this.mResults.length; ++i) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("[");
                stringBuilder2.append(i);
                stringBuilder2.append("]: ");
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("bssid=");
                stringBuilder2.append(this.mResults[i].bssid);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", burstNumber=");
                stringBuilder2.append(this.mResults[i].burstNumber);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", measurementFrameNumber=");
                stringBuilder2.append(this.mResults[i].measurementFrameNumber);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", successMeasurementFrameNumber=");
                stringBuilder2.append(this.mResults[i].successMeasurementFrameNumber);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", frameNumberPerBurstPeer=");
                stringBuilder2.append(this.mResults[i].frameNumberPerBurstPeer);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", status=");
                stringBuilder2.append(this.mResults[i].status);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", requestType=");
                stringBuilder2.append(this.mResults[i].requestType);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", measurementType=");
                stringBuilder2.append(this.mResults[i].measurementType);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", retryAfterDuration=");
                stringBuilder2.append(this.mResults[i].retryAfterDuration);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", ts=");
                stringBuilder2.append(this.mResults[i].ts);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", rssi=");
                stringBuilder2.append(this.mResults[i].rssi);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", rssi_spread=");
                stringBuilder2.append(this.mResults[i].rssi_spread);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", rssiSpread=");
                stringBuilder2.append(this.mResults[i].rssiSpread);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", tx_rate=");
                stringBuilder2.append(this.mResults[i].tx_rate);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", txRate=");
                stringBuilder2.append(this.mResults[i].txRate);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", rxRate=");
                stringBuilder2.append(this.mResults[i].rxRate);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", rtt_ns=");
                stringBuilder2.append(this.mResults[i].rtt_ns);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", rtt=");
                stringBuilder2.append(this.mResults[i].rtt);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", rtt_sd_ns=");
                stringBuilder2.append(this.mResults[i].rtt_sd_ns);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", rttStandardDeviation=");
                stringBuilder2.append(this.mResults[i].rttStandardDeviation);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", rtt_spread_ns=");
                stringBuilder2.append(this.mResults[i].rtt_spread_ns);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", rttSpread=");
                stringBuilder2.append(this.mResults[i].rttSpread);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", distance_cm=");
                stringBuilder2.append(this.mResults[i].distance_cm);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", distance=");
                stringBuilder2.append(this.mResults[i].distance);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", distance_sd_cm=");
                stringBuilder2.append(this.mResults[i].distance_sd_cm);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", distanceStandardDeviation=");
                stringBuilder2.append(this.mResults[i].distanceStandardDeviation);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", distance_spread_cm=");
                stringBuilder2.append(this.mResults[i].distance_spread_cm);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", distanceSpread=");
                stringBuilder2.append(this.mResults[i].distanceSpread);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", burstDuration=");
                stringBuilder2.append(this.mResults[i].burstDuration);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", negotiatedBurstNum=");
                stringBuilder2.append(this.mResults[i].negotiatedBurstNum);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", LCI=");
                stringBuilder2.append(this.mResults[i].LCI);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", LCR=");
                stringBuilder2.append(this.mResults[i].LCR);
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", secure=");
                stringBuilder2.append(this.mResults[i].secure);
                stringBuilder.append(stringBuilder2.toString());
            }
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            RttResult[] arrrttResult = this.mResults;
            if (arrrttResult != null) {
                parcel.writeInt(arrrttResult.length);
                for (RttResult rttResult : this.mResults) {
                    parcel.writeString(rttResult.bssid);
                    parcel.writeInt(rttResult.burstNumber);
                    parcel.writeInt(rttResult.measurementFrameNumber);
                    parcel.writeInt(rttResult.successMeasurementFrameNumber);
                    parcel.writeInt(rttResult.frameNumberPerBurstPeer);
                    parcel.writeInt(rttResult.status);
                    parcel.writeInt(rttResult.measurementType);
                    parcel.writeInt(rttResult.retryAfterDuration);
                    parcel.writeLong(rttResult.ts);
                    parcel.writeInt(rttResult.rssi);
                    parcel.writeInt(rttResult.rssiSpread);
                    parcel.writeInt(rttResult.txRate);
                    parcel.writeLong(rttResult.rtt);
                    parcel.writeLong(rttResult.rttStandardDeviation);
                    parcel.writeLong(rttResult.rttSpread);
                    parcel.writeInt(rttResult.distance);
                    parcel.writeInt(rttResult.distanceStandardDeviation);
                    parcel.writeInt(rttResult.distanceSpread);
                    parcel.writeInt(rttResult.burstDuration);
                    parcel.writeInt(rttResult.negotiatedBurstNum);
                    parcel.writeByte(rttResult.LCI.id);
                    if (rttResult.LCI.id != -1) {
                        parcel.writeByte((byte)rttResult.LCI.data.length);
                        parcel.writeByteArray(rttResult.LCI.data);
                    }
                    parcel.writeByte(rttResult.LCR.id);
                    if (rttResult.LCR.id != -1) {
                        parcel.writeByte((byte)rttResult.LCR.data.length);
                        parcel.writeByteArray(rttResult.LCR.data);
                    }
                    parcel.writeByte((byte)rttResult.secure);
                }
            } else {
                parcel.writeInt(0);
            }
        }

    }

    @Deprecated
    public static abstract class ResponderCallback {
        public abstract void onResponderEnableFailure(int var1);

        public abstract void onResponderEnabled(ResponderConfig var1);
    }

    @Deprecated
    public static class ResponderConfig
    implements Parcelable {
        public static final Parcelable.Creator<ResponderConfig> CREATOR = new Parcelable.Creator<ResponderConfig>(){

            @Override
            public ResponderConfig createFromParcel(Parcel parcel) {
                ResponderConfig responderConfig = new ResponderConfig();
                responderConfig.macAddress = parcel.readString();
                responderConfig.frequency = parcel.readInt();
                responderConfig.centerFreq0 = parcel.readInt();
                responderConfig.centerFreq1 = parcel.readInt();
                responderConfig.channelWidth = parcel.readInt();
                responderConfig.preamble = parcel.readInt();
                return responderConfig;
            }

            public ResponderConfig[] newArray(int n) {
                return new ResponderConfig[n];
            }
        };
        public int centerFreq0;
        public int centerFreq1;
        public int channelWidth;
        public int frequency;
        public String macAddress = "";
        public int preamble;

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("macAddress = ");
            stringBuilder.append(this.macAddress);
            stringBuilder.append(" frequency = ");
            stringBuilder.append(this.frequency);
            stringBuilder.append(" centerFreq0 = ");
            stringBuilder.append(this.centerFreq0);
            stringBuilder.append(" centerFreq1 = ");
            stringBuilder.append(this.centerFreq1);
            stringBuilder.append(" channelWidth = ");
            stringBuilder.append(this.channelWidth);
            stringBuilder.append(" preamble = ");
            stringBuilder.append(this.preamble);
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.macAddress);
            parcel.writeInt(this.frequency);
            parcel.writeInt(this.centerFreq0);
            parcel.writeInt(this.centerFreq1);
            parcel.writeInt(this.channelWidth);
            parcel.writeInt(this.preamble);
        }

    }

    @Deprecated
    public static class RttCapabilities
    implements Parcelable {
        public static final Parcelable.Creator<RttCapabilities> CREATOR = new Parcelable.Creator<RttCapabilities>(){

            @Override
            public RttCapabilities createFromParcel(Parcel parcel) {
                RttCapabilities rttCapabilities = new RttCapabilities();
                int n = parcel.readInt();
                boolean bl = false;
                boolean bl2 = n == 1;
                rttCapabilities.oneSidedRttSupported = bl2;
                bl2 = parcel.readInt() == 1;
                rttCapabilities.twoSided11McRttSupported = bl2;
                bl2 = parcel.readInt() == 1;
                rttCapabilities.lciSupported = bl2;
                bl2 = parcel.readInt() == 1;
                rttCapabilities.lcrSupported = bl2;
                rttCapabilities.preambleSupported = parcel.readInt();
                rttCapabilities.bwSupported = parcel.readInt();
                bl2 = parcel.readInt() == 1;
                rttCapabilities.responderSupported = bl2;
                bl2 = bl;
                if (parcel.readInt() == 1) {
                    bl2 = true;
                }
                rttCapabilities.secureRttSupported = bl2;
                rttCapabilities.mcVersion = parcel.readInt();
                return rttCapabilities;
            }

            public RttCapabilities[] newArray(int n) {
                return new RttCapabilities[n];
            }
        };
        public int bwSupported;
        public boolean lciSupported;
        public boolean lcrSupported;
        public int mcVersion;
        public boolean oneSidedRttSupported;
        public int preambleSupported;
        public boolean responderSupported;
        public boolean secureRttSupported;
        @Deprecated
        public boolean supportedPeerType;
        @Deprecated
        public boolean supportedType;
        public boolean twoSided11McRttSupported;

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("oneSidedRtt ");
            boolean bl = this.oneSidedRttSupported;
            String string2 = "is Supported. ";
            CharSequence charSequence = bl ? "is Supported. " : "is not supported. ";
            stringBuffer.append((String)charSequence);
            stringBuffer.append("twoSided11McRtt ");
            charSequence = this.twoSided11McRttSupported ? "is Supported. " : "is not supported. ";
            stringBuffer.append((String)charSequence);
            stringBuffer.append("lci ");
            charSequence = this.lciSupported ? "is Supported. " : "is not supported. ";
            stringBuffer.append((String)charSequence);
            stringBuffer.append("lcr ");
            charSequence = this.lcrSupported ? string2 : "is not supported. ";
            stringBuffer.append((String)charSequence);
            if ((this.preambleSupported & 1) != 0) {
                stringBuffer.append("Legacy ");
            }
            if ((this.preambleSupported & 2) != 0) {
                stringBuffer.append("HT ");
            }
            if ((this.preambleSupported & 4) != 0) {
                stringBuffer.append("VHT ");
            }
            stringBuffer.append("is supported. ");
            if ((this.bwSupported & 1) != 0) {
                stringBuffer.append("5 MHz ");
            }
            if ((this.bwSupported & 2) != 0) {
                stringBuffer.append("10 MHz ");
            }
            if ((this.bwSupported & 4) != 0) {
                stringBuffer.append("20 MHz ");
            }
            if ((this.bwSupported & 8) != 0) {
                stringBuffer.append("40 MHz ");
            }
            if ((this.bwSupported & 16) != 0) {
                stringBuffer.append("80 MHz ");
            }
            if ((this.bwSupported & 32) != 0) {
                stringBuffer.append("160 MHz ");
            }
            stringBuffer.append("is supported.");
            stringBuffer.append(" STA responder role is ");
            bl = this.responderSupported;
            string2 = "supported";
            charSequence = bl ? "supported" : "not supported";
            stringBuffer.append((String)charSequence);
            stringBuffer.append(" Secure RTT protocol is ");
            charSequence = this.secureRttSupported ? string2 : "not supported";
            stringBuffer.append((String)charSequence);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" 11mc version is ");
            ((StringBuilder)charSequence).append(this.mcVersion);
            stringBuffer.append(((StringBuilder)charSequence).toString());
            return stringBuffer.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt((int)this.oneSidedRttSupported);
            parcel.writeInt((int)this.twoSided11McRttSupported);
            parcel.writeInt((int)this.lciSupported);
            parcel.writeInt((int)this.lcrSupported);
            parcel.writeInt(this.preambleSupported);
            parcel.writeInt(this.bwSupported);
            parcel.writeInt((int)this.responderSupported);
            parcel.writeInt((int)this.secureRttSupported);
            parcel.writeInt(this.mcVersion);
        }

    }

    @Deprecated
    public static interface RttListener {
        public void onAborted();

        public void onFailure(int var1, String var2);

        public void onSuccess(RttResult[] var1);
    }

    @Deprecated
    public static class RttParams {
        public boolean LCIRequest;
        public boolean LCRRequest;
        public int bandwidth = 4;
        public String bssid;
        public int burstTimeout = 15;
        public int centerFreq0;
        public int centerFreq1;
        public int channelWidth;
        public int deviceType = 1;
        public int frequency;
        public int interval;
        public int numRetriesPerFTMR = 0;
        public int numRetriesPerMeasurementFrame = 0;
        public int numSamplesPerBurst = 8;
        @Deprecated
        public int num_retries;
        @Deprecated
        public int num_samples;
        public int numberBurst = 0;
        public int preamble = 2;
        public int requestType = 1;
        public boolean secure;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("deviceType=");
            stringBuilder2.append(this.deviceType);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", requestType=");
            stringBuilder2.append(this.requestType);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", secure=");
            stringBuilder2.append(this.secure);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", bssid=");
            stringBuilder2.append(this.bssid);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", frequency=");
            stringBuilder2.append(this.frequency);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", channelWidth=");
            stringBuilder2.append(this.channelWidth);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", centerFreq0=");
            stringBuilder2.append(this.centerFreq0);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", centerFreq1=");
            stringBuilder2.append(this.centerFreq1);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", num_samples=");
            stringBuilder2.append(this.num_samples);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", num_retries=");
            stringBuilder2.append(this.num_retries);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", numberBurst=");
            stringBuilder2.append(this.numberBurst);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", interval=");
            stringBuilder2.append(this.interval);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", numSamplesPerBurst=");
            stringBuilder2.append(this.numSamplesPerBurst);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", numRetriesPerMeasurementFrame=");
            stringBuilder2.append(this.numRetriesPerMeasurementFrame);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", numRetriesPerFTMR=");
            stringBuilder2.append(this.numRetriesPerFTMR);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", LCIRequest=");
            stringBuilder2.append(this.LCIRequest);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", LCRRequest=");
            stringBuilder2.append(this.LCRRequest);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", burstTimeout=");
            stringBuilder2.append(this.burstTimeout);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", preamble=");
            stringBuilder2.append(this.preamble);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", bandwidth=");
            stringBuilder2.append(this.bandwidth);
            stringBuilder.append(stringBuilder2.toString());
            return stringBuilder.toString();
        }
    }

    @Deprecated
    public static class RttResult {
        public WifiInformationElement LCI;
        public WifiInformationElement LCR;
        public String bssid;
        public int burstDuration;
        public int burstNumber;
        public int distance;
        public int distanceSpread;
        public int distanceStandardDeviation;
        @Deprecated
        public int distance_cm;
        @Deprecated
        public int distance_sd_cm;
        @Deprecated
        public int distance_spread_cm;
        public int frameNumberPerBurstPeer;
        public int measurementFrameNumber;
        public int measurementType;
        public int negotiatedBurstNum;
        @Deprecated
        public int requestType;
        public int retryAfterDuration;
        public int rssi;
        public int rssiSpread;
        @Deprecated
        public int rssi_spread;
        public long rtt;
        public long rttSpread;
        public long rttStandardDeviation;
        @Deprecated
        public long rtt_ns;
        @Deprecated
        public long rtt_sd_ns;
        @Deprecated
        public long rtt_spread_ns;
        public int rxRate;
        public boolean secure;
        public int status;
        public int successMeasurementFrameNumber;
        public long ts;
        public int txRate;
        @Deprecated
        public int tx_rate;
    }

    @Deprecated
    public static class WifiInformationElement {
        public byte[] data;
        public byte id;
    }

}

