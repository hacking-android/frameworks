/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.telephony.CallQuality
 *  android.telephony.CellSignalStrengthLte
 *  android.telephony.Rlog
 *  android.telephony.SignalStrength
 *  android.util.Pair
 */
package com.android.internal.telephony.metrics;

import android.os.Build;
import android.telephony.CallQuality;
import android.telephony.CellSignalStrengthLte;
import android.telephony.Rlog;
import android.telephony.SignalStrength;
import android.util.Pair;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.nano.TelephonyProto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CallQualityMetrics {
    private static final int BAD_QUALITY = 1;
    private static final int GOOD_QUALITY = 0;
    private static final int MAX_SNAPSHOTS = 5;
    private static final String TAG = CallQualityMetrics.class.getSimpleName();
    private static final boolean USERDEBUG_MODE = Build.IS_USERDEBUG;
    private Pair<CallQuality, Integer> mBestSsWithBadDlQuality;
    private Pair<CallQuality, Integer> mBestSsWithBadUlQuality;
    private Pair<CallQuality, Integer> mBestSsWithGoodDlQuality;
    private Pair<CallQuality, Integer> mBestSsWithGoodUlQuality;
    private int mDlCallQualityState = 0;
    private ArrayList<Pair<CallQuality, Integer>> mDlSnapshots = new ArrayList();
    private CallQuality mLastCallQuality;
    private Phone mPhone;
    private int mTotalDlBadQualityTimeMs = 0;
    private int mTotalDlGoodQualityTimeMs = 0;
    private int mTotalUlBadQualityTimeMs = 0;
    private int mTotalUlGoodQualityTimeMs = 0;
    private int mUlCallQualityState = 0;
    private ArrayList<Pair<CallQuality, Integer>> mUlSnapshots = new ArrayList();
    private Pair<CallQuality, Integer> mWorstSsWithBadDlQuality;
    private Pair<CallQuality, Integer> mWorstSsWithBadUlQuality;
    private Pair<CallQuality, Integer> mWorstSsWithGoodDlQuality;
    private Pair<CallQuality, Integer> mWorstSsWithGoodUlQuality;

    public CallQualityMetrics(Phone phone) {
        this.mPhone = phone;
        this.mLastCallQuality = new CallQuality();
    }

    private ArrayList<Pair<CallQuality, Integer>> addSnapshot(CallQuality callQuality, ArrayList<Pair<CallQuality, Integer>> arrayList) {
        if (arrayList.size() < 5) {
            arrayList.add((Pair<CallQuality, Integer>)Pair.create((Object)callQuality, (Object)this.getLteSnr()));
        }
        return arrayList;
    }

    private Integer getLteSnr() {
        Object object = this.mPhone.getDefaultPhone().getServiceStateTracker();
        Integer n = Integer.MAX_VALUE;
        if (object == null) {
            object = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getLteSnr: unable to get SST for phone ");
            stringBuilder.append(this.mPhone.getPhoneId());
            Rlog.e((String)object, (String)stringBuilder.toString());
            return n;
        }
        if ((object = ((ServiceStateTracker)((Object)object)).getSignalStrength()) == null) {
            object = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getLteSnr: unable to get SignalStrength for phone ");
            stringBuilder.append(this.mPhone.getPhoneId());
            Rlog.e((String)object, (String)stringBuilder.toString());
            return n;
        }
        object = object.getCellSignalStrengths(CellSignalStrengthLte.class).iterator();
        while (object.hasNext()) {
            int n2 = ((CellSignalStrengthLte)object.next()).getRssnr();
            if (n2 == Integer.MAX_VALUE) continue;
            return n2;
        }
        return n;
    }

    private static boolean isGoodQuality(int n) {
        boolean bl = n < 4;
        return bl;
    }

    private static TelephonyProto.TelephonyCallSession.Event.SignalStrength toProto(int n) {
        TelephonyProto.TelephonyCallSession.Event.SignalStrength signalStrength = new TelephonyProto.TelephonyCallSession.Event.SignalStrength();
        signalStrength.lteSnr = n;
        return signalStrength;
    }

    private void updateMinAndMaxSignalStrengthSnapshots(int n, int n2, CallQuality callQuality) {
        Integer n3 = this.getLteSnr();
        if (n3.equals(Integer.MAX_VALUE)) {
            return;
        }
        if (n == 0) {
            if (this.mWorstSsWithGoodDlQuality == null || n3 < (Integer)this.mWorstSsWithGoodDlQuality.second) {
                this.mWorstSsWithGoodDlQuality = Pair.create((Object)callQuality, (Object)n3);
            }
            if (this.mBestSsWithGoodDlQuality == null || n3 > (Integer)this.mBestSsWithGoodDlQuality.second) {
                this.mBestSsWithGoodDlQuality = Pair.create((Object)callQuality, (Object)n3);
            }
        } else {
            if (this.mWorstSsWithBadDlQuality == null || n3 < (Integer)this.mWorstSsWithBadDlQuality.second) {
                this.mWorstSsWithBadDlQuality = Pair.create((Object)callQuality, (Object)n3);
            }
            if (this.mBestSsWithBadDlQuality == null || n3 > (Integer)this.mBestSsWithBadDlQuality.second) {
                this.mBestSsWithBadDlQuality = Pair.create((Object)callQuality, (Object)n3);
            }
        }
        if (n2 == 0) {
            if (this.mWorstSsWithGoodUlQuality == null || n3 < (Integer)this.mWorstSsWithGoodUlQuality.second) {
                this.mWorstSsWithGoodUlQuality = Pair.create((Object)callQuality, (Object)n3);
            }
            if (this.mBestSsWithGoodUlQuality == null || n3 > (Integer)this.mBestSsWithGoodUlQuality.second) {
                this.mBestSsWithGoodUlQuality = Pair.create((Object)callQuality, (Object)n3);
            }
        } else {
            if (this.mWorstSsWithBadUlQuality == null || n3 < (Integer)this.mWorstSsWithBadUlQuality.second) {
                this.mWorstSsWithBadUlQuality = Pair.create((Object)callQuality, (Object)n3);
            }
            if (this.mBestSsWithBadUlQuality == null || n3 > (Integer)this.mBestSsWithBadUlQuality.second) {
                this.mBestSsWithBadUlQuality = Pair.create((Object)callQuality, (Object)n3);
            }
        }
    }

    private void updateTotalDurations(int n, int n2, CallQuality callQuality) {
        int n3 = callQuality.getCallDuration() - this.mLastCallQuality.getCallDuration();
        if (n == 0) {
            this.mTotalDlGoodQualityTimeMs += n3;
        } else {
            this.mTotalDlBadQualityTimeMs += n3;
        }
        if (n2 == 0) {
            this.mTotalUlGoodQualityTimeMs += n3;
        } else {
            this.mTotalUlBadQualityTimeMs += n3;
        }
    }

    public TelephonyProto.TelephonyCallSession.Event.CallQualitySummary getCallQualitySummaryDl() {
        TelephonyProto.TelephonyCallSession.Event.CallQualitySummary callQualitySummary = new TelephonyProto.TelephonyCallSession.Event.CallQualitySummary();
        callQualitySummary.totalGoodQualityDurationInSeconds = this.mTotalDlGoodQualityTimeMs / 1000;
        callQualitySummary.totalBadQualityDurationInSeconds = this.mTotalDlBadQualityTimeMs / 1000;
        callQualitySummary.totalDurationWithQualityInformationInSeconds = this.mLastCallQuality.getCallDuration() / 1000;
        Pair<CallQuality, Integer> pair = this.mWorstSsWithGoodDlQuality;
        if (pair != null) {
            callQualitySummary.snapshotOfWorstSsWithGoodQuality = TelephonyMetrics.toCallQualityProto((CallQuality)pair.first);
            callQualitySummary.worstSsWithGoodQuality = CallQualityMetrics.toProto((Integer)this.mWorstSsWithGoodDlQuality.second);
        }
        if ((pair = this.mBestSsWithGoodDlQuality) != null) {
            callQualitySummary.snapshotOfBestSsWithGoodQuality = TelephonyMetrics.toCallQualityProto((CallQuality)pair.first);
            callQualitySummary.bestSsWithGoodQuality = CallQualityMetrics.toProto((Integer)this.mBestSsWithGoodDlQuality.second);
        }
        if ((pair = this.mWorstSsWithBadDlQuality) != null) {
            callQualitySummary.snapshotOfWorstSsWithBadQuality = TelephonyMetrics.toCallQualityProto((CallQuality)pair.first);
            callQualitySummary.worstSsWithBadQuality = CallQualityMetrics.toProto((Integer)this.mWorstSsWithBadDlQuality.second);
        }
        if ((pair = this.mBestSsWithBadDlQuality) != null) {
            callQualitySummary.snapshotOfBestSsWithBadQuality = TelephonyMetrics.toCallQualityProto((CallQuality)pair.first);
            callQualitySummary.bestSsWithBadQuality = CallQualityMetrics.toProto((Integer)this.mBestSsWithBadDlQuality.second);
        }
        callQualitySummary.snapshotOfEnd = TelephonyMetrics.toCallQualityProto(this.mLastCallQuality);
        return callQualitySummary;
    }

    public TelephonyProto.TelephonyCallSession.Event.CallQualitySummary getCallQualitySummaryUl() {
        TelephonyProto.TelephonyCallSession.Event.CallQualitySummary callQualitySummary = new TelephonyProto.TelephonyCallSession.Event.CallQualitySummary();
        callQualitySummary.totalGoodQualityDurationInSeconds = this.mTotalUlGoodQualityTimeMs / 1000;
        callQualitySummary.totalBadQualityDurationInSeconds = this.mTotalUlBadQualityTimeMs / 1000;
        callQualitySummary.totalDurationWithQualityInformationInSeconds = this.mLastCallQuality.getCallDuration() / 1000;
        Pair<CallQuality, Integer> pair = this.mWorstSsWithGoodUlQuality;
        if (pair != null) {
            callQualitySummary.snapshotOfWorstSsWithGoodQuality = TelephonyMetrics.toCallQualityProto((CallQuality)pair.first);
            callQualitySummary.worstSsWithGoodQuality = CallQualityMetrics.toProto((Integer)this.mWorstSsWithGoodUlQuality.second);
        }
        if ((pair = this.mBestSsWithGoodUlQuality) != null) {
            callQualitySummary.snapshotOfBestSsWithGoodQuality = TelephonyMetrics.toCallQualityProto((CallQuality)pair.first);
            callQualitySummary.bestSsWithGoodQuality = CallQualityMetrics.toProto((Integer)this.mBestSsWithGoodUlQuality.second);
        }
        if ((pair = this.mWorstSsWithBadUlQuality) != null) {
            callQualitySummary.snapshotOfWorstSsWithBadQuality = TelephonyMetrics.toCallQualityProto((CallQuality)pair.first);
            callQualitySummary.worstSsWithBadQuality = CallQualityMetrics.toProto((Integer)this.mWorstSsWithBadUlQuality.second);
        }
        if ((pair = this.mBestSsWithBadUlQuality) != null) {
            callQualitySummary.snapshotOfBestSsWithBadQuality = TelephonyMetrics.toCallQualityProto((CallQuality)pair.first);
            callQualitySummary.bestSsWithBadQuality = CallQualityMetrics.toProto((Integer)this.mBestSsWithBadUlQuality.second);
        }
        callQualitySummary.snapshotOfEnd = TelephonyMetrics.toCallQualityProto(this.mLastCallQuality);
        return callQualitySummary;
    }

    public void saveCallQuality(CallQuality callQuality) {
        if (callQuality.getUplinkCallQualityLevel() != 5 && callQuality.getDownlinkCallQualityLevel() != 5) {
            int n = 1;
            int n2 = 1;
            if (CallQualityMetrics.isGoodQuality(callQuality.getUplinkCallQualityLevel())) {
                n = 0;
            }
            if (CallQualityMetrics.isGoodQuality(callQuality.getDownlinkCallQualityLevel())) {
                n2 = 0;
            }
            if (USERDEBUG_MODE) {
                if (n != this.mUlCallQualityState) {
                    this.mUlSnapshots = this.addSnapshot(callQuality, this.mUlSnapshots);
                }
                if (n2 != this.mDlCallQualityState) {
                    this.mDlSnapshots = this.addSnapshot(callQuality, this.mDlSnapshots);
                }
            }
            this.updateTotalDurations(n2, n, callQuality);
            this.updateMinAndMaxSignalStrengthSnapshots(n2, n, callQuality);
            this.mUlCallQualityState = n;
            this.mDlCallQualityState = n2;
            this.mLastCallQuality = callQuality;
            return;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[CallQualityMetrics phone ");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append(" mUlSnapshots: {");
        for (Pair<CallQuality, Integer> pair : this.mUlSnapshots) {
            stringBuilder.append(" {cq=");
            stringBuilder.append(pair.first);
            stringBuilder.append(" ss=");
            stringBuilder.append(pair.second);
            stringBuilder.append("}");
        }
        stringBuilder.append("}");
        stringBuilder.append(" mDlSnapshots:{");
        for (Pair<CallQuality, Integer> pair : this.mDlSnapshots) {
            stringBuilder.append(" {cq=");
            stringBuilder.append(pair.first);
            stringBuilder.append(" ss=");
            stringBuilder.append(pair.second);
            stringBuilder.append("}");
        }
        stringBuilder.append("}");
        stringBuilder.append(" ");
        stringBuilder.append(" mTotalDlGoodQualityTimeMs: ");
        stringBuilder.append(this.mTotalDlGoodQualityTimeMs);
        stringBuilder.append(" mTotalDlBadQualityTimeMs: ");
        stringBuilder.append(this.mTotalDlBadQualityTimeMs);
        stringBuilder.append(" mTotalUlGoodQualityTimeMs: ");
        stringBuilder.append(this.mTotalUlGoodQualityTimeMs);
        stringBuilder.append(" mTotalUlBadQualityTimeMs: ");
        stringBuilder.append(this.mTotalUlBadQualityTimeMs);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

