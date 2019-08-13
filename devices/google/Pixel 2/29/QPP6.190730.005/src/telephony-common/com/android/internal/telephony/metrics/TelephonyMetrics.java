/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.SystemClock
 *  android.os.SystemProperties
 *  android.telephony.CallQuality
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyHistogram
 *  android.telephony.TelephonyManager
 *  android.telephony.data.DataCallResponse
 *  android.telephony.emergency.EmergencyNumber
 *  android.telephony.ims.ImsCallProfile
 *  android.telephony.ims.ImsCallSession
 *  android.telephony.ims.ImsReasonInfo
 *  android.telephony.ims.ImsStreamMediaProfile
 *  android.telephony.ims.feature.MmTelFeature
 *  android.telephony.ims.feature.MmTelFeature$MmTelCapabilities
 *  android.text.TextUtils
 *  android.util.Base64
 *  android.util.SparseArray
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 *  com.android.internal.telephony.metrics.-$
 *  com.android.internal.telephony.metrics.-$$Lambda
 *  com.android.internal.telephony.metrics.-$$Lambda$TelephonyMetrics
 *  com.android.internal.telephony.metrics.-$$Lambda$TelephonyMetrics$fLmZDbNadlr6LF7zSJ6jCR1AAsk
 *  com.android.internal.telephony.metrics.-$$Lambda$TelephonyMetrics$x2dJi76S2YQdpSTfY8RZ8qC_K6g
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony.metrics;

import android.os.Build;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.telephony.CallQuality;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyHistogram;
import android.telephony.TelephonyManager;
import android.telephony.data.DataCallResponse;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsCallSession;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.feature.MmTelFeature;
import android.text.TextUtils;
import android.util.Base64;
import android.util.SparseArray;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CarrierResolver;
import com.android.internal.telephony.GsmCdmaConnection;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony.SmsResponse;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.metrics.-$;
import com.android.internal.telephony.metrics.CallQualityMetrics;
import com.android.internal.telephony.metrics.CallSessionEventBuilder;
import com.android.internal.telephony.metrics.InProgressCallSession;
import com.android.internal.telephony.metrics.InProgressSmsSession;
import com.android.internal.telephony.metrics.ModemPowerMetrics;
import com.android.internal.telephony.metrics.SmsSessionEventBuilder;
import com.android.internal.telephony.metrics.TelephonyEventBuilder;
import com.android.internal.telephony.metrics._$$Lambda$TelephonyMetrics$fLmZDbNadlr6LF7zSJ6jCR1AAsk;
import com.android.internal.telephony.metrics._$$Lambda$TelephonyMetrics$tQOsX1lKb2eTuPp_1rpkeIAEOoY;
import com.android.internal.telephony.metrics._$$Lambda$TelephonyMetrics$x2dJi76S2YQdpSTfY8RZ8qC_K6g;
import com.android.internal.telephony.nano.TelephonyProto;
import com.android.internal.telephony.protobuf.nano.ExtendableMessageNano;
import com.android.internal.telephony.protobuf.nano.MessageNano;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TelephonyMetrics {
    private static final boolean DBG = true;
    private static final int MAX_COMPLETED_CALL_SESSIONS = 50;
    private static final int MAX_COMPLETED_SMS_SESSIONS = 500;
    private static final int MAX_TELEPHONY_EVENTS = 1000;
    private static final int SESSION_START_PRECISION_MINUTES = 5;
    private static final String TAG = TelephonyMetrics.class.getSimpleName();
    private static final boolean VDBG = false;
    private static TelephonyMetrics sInstance;
    private final Deque<TelephonyProto.TelephonyCallSession> mCompletedCallSessions = new ArrayDeque<TelephonyProto.TelephonyCallSession>();
    private final Deque<TelephonyProto.SmsSession> mCompletedSmsSessions = new ArrayDeque<TelephonyProto.SmsSession>();
    private final SparseArray<InProgressCallSession> mInProgressCallSessions = new SparseArray();
    private final SparseArray<InProgressSmsSession> mInProgressSmsSessions = new SparseArray();
    private final SparseArray<TelephonyProto.ActiveSubscriptionInfo> mLastActiveSubscriptionInfos = new SparseArray();
    private final SparseArray<TelephonyProto.TelephonyEvent.CarrierIdMatching> mLastCarrierId = new SparseArray();
    private int mLastEnabledModemBitmap = (1 << TelephonyManager.getDefault().getPhoneCount()) - 1;
    private final SparseArray<TelephonyProto.ImsCapabilities> mLastImsCapabilities = new SparseArray();
    private final SparseArray<TelephonyProto.ImsConnectionState> mLastImsConnectionState = new SparseArray();
    private final SparseArray<SparseArray<TelephonyProto.RilDataCall>> mLastRilDataCallEvents = new SparseArray();
    private final SparseArray<TelephonyProto.TelephonyServiceState> mLastServiceState = new SparseArray();
    private final SparseArray<TelephonyProto.TelephonySettings> mLastSettings = new SparseArray();
    private final SparseArray<Integer> mLastSimState = new SparseArray();
    private long mStartElapsedTimeMs = SystemClock.elapsedRealtime();
    private long mStartSystemTimeMs = System.currentTimeMillis();
    private final Deque<TelephonyProto.TelephonyEvent> mTelephonyEvents = new ArrayDeque<TelephonyProto.TelephonyEvent>();
    private boolean mTelephonyEventsDropped = false;

    private void addTelephonyEvent(TelephonyProto.TelephonyEvent telephonyEvent) {
        synchronized (this) {
            if (this.mTelephonyEvents.size() >= 1000) {
                this.mTelephonyEvents.removeFirst();
                this.mTelephonyEventsDropped = true;
            }
            this.mTelephonyEvents.add(telephonyEvent);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void annotateInProgressCallSession(long l, int n, CallSessionEventBuilder callSessionEventBuilder) {
        synchronized (this) {
            InProgressCallSession inProgressCallSession = (InProgressCallSession)this.mInProgressCallSessions.get(n);
            if (inProgressCallSession != null) {
                inProgressCallSession.addEvent(l, callSessionEventBuilder);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void annotateInProgressSmsSession(long l, int n, SmsSessionEventBuilder smsSessionEventBuilder) {
        synchronized (this) {
            InProgressSmsSession inProgressSmsSession = (InProgressSmsSession)this.mInProgressSmsSessions.get(n);
            if (inProgressSmsSession != null) {
                inProgressSmsSession.addEvent(l, smsSessionEventBuilder);
            }
            return;
        }
    }

    private TelephonyProto.TelephonyLog buildProto() {
        synchronized (this) {
            TelephonyProto.TelephonyLog telephonyLog = new TelephonyProto.TelephonyLog();
            telephonyLog.events = new TelephonyProto.TelephonyEvent[this.mTelephonyEvents.size()];
            this.mTelephonyEvents.toArray(telephonyLog.events);
            telephonyLog.eventsDropped = this.mTelephonyEventsDropped;
            telephonyLog.callSessions = new TelephonyProto.TelephonyCallSession[this.mCompletedCallSessions.size()];
            this.mCompletedCallSessions.toArray(telephonyLog.callSessions);
            telephonyLog.smsSessions = new TelephonyProto.SmsSession[this.mCompletedSmsSessions.size()];
            this.mCompletedSmsSessions.toArray(telephonyLog.smsSessions);
            TelephonyProto.ActiveSubscriptionInfo[] arractiveSubscriptionInfo = RIL.getTelephonyRILTimingHistograms();
            telephonyLog.histograms = new TelephonyProto.TelephonyHistogram[arractiveSubscriptionInfo.size()];
            int n = 0;
            int n2 = 0;
            do {
                TelephonyProto.TelephonyHistogram telephonyHistogram;
                if (n2 >= arractiveSubscriptionInfo.size()) break;
                Object object = telephonyLog.histograms;
                object[n2] = telephonyHistogram = new TelephonyProto.TelephonyHistogram();
                telephonyHistogram = (TelephonyHistogram)arractiveSubscriptionInfo.get(n2);
                object = telephonyLog.histograms[n2];
                object.category = telephonyHistogram.getCategory();
                object.id = telephonyHistogram.getId();
                object.minTimeMillis = telephonyHistogram.getMinTime();
                object.maxTimeMillis = telephonyHistogram.getMaxTime();
                object.avgTimeMillis = telephonyHistogram.getAverageTime();
                object.count = telephonyHistogram.getSampleCount();
                object.bucketCount = telephonyHistogram.getBucketCount();
                object.bucketEndPoints = telephonyHistogram.getBucketEndPoints();
                object.bucketCounters = telephonyHistogram.getBucketCounters();
                ++n2;
            } while (true);
            arractiveSubscriptionInfo = new ModemPowerMetrics();
            telephonyLog.modemPowerStats = arractiveSubscriptionInfo.buildProto();
            telephonyLog.hardwareRevision = SystemProperties.get((String)"ro.boot.revision", (String)"");
            arractiveSubscriptionInfo = new TelephonyProto.Time();
            telephonyLog.startTime = arractiveSubscriptionInfo;
            telephonyLog.startTime.systemTimestampMillis = this.mStartSystemTimeMs;
            telephonyLog.startTime.elapsedTimestampMillis = this.mStartElapsedTimeMs;
            arractiveSubscriptionInfo = new TelephonyProto.Time();
            telephonyLog.endTime = arractiveSubscriptionInfo;
            telephonyLog.endTime.systemTimestampMillis = System.currentTimeMillis();
            telephonyLog.endTime.elapsedTimestampMillis = SystemClock.elapsedRealtime();
            int n3 = TelephonyManager.getDefault().getPhoneCount();
            arractiveSubscriptionInfo = new TelephonyProto.ActiveSubscriptionInfo[n3];
            n2 = n;
            do {
                if (n2 >= this.mLastActiveSubscriptionInfos.size()) break;
                n = this.mLastActiveSubscriptionInfos.keyAt(n2);
                arractiveSubscriptionInfo[n] = (TelephonyProto.ActiveSubscriptionInfo)this.mLastActiveSubscriptionInfos.get(n);
                ++n2;
            } while (true);
            for (n2 = 0; n2 < n3; ++n2) {
                if (arractiveSubscriptionInfo[n2] != null) continue;
                arractiveSubscriptionInfo[n2] = TelephonyMetrics.makeInvalidSubscriptionInfo(n2);
            }
            telephonyLog.lastActiveSubscriptionInfo = arractiveSubscriptionInfo;
            return telephonyLog;
        }
    }

    private static int callQualityLevelToProtoEnum(int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 2;
        }
        if (n == 2) {
            return 3;
        }
        if (n == 3) {
            return 4;
        }
        if (n == 4) {
            return 5;
        }
        if (n == 5) {
            return 6;
        }
        return 0;
    }

    private static String callSessionEventToString(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 22: {
                return "AUDIO_CODEC";
            }
            case 21: {
                return "NITZ_TIME";
            }
            case 20: {
                return "PHONE_STATE_CHANGED";
            }
            case 19: {
                return "IMS_CALL_HANDOVER_FAILED";
            }
            case 18: {
                return "IMS_CALL_HANDOVER";
            }
            case 17: {
                return "IMS_CALL_TERMINATED";
            }
            case 16: {
                return "IMS_CALL_STATE_CHANGED";
            }
            case 15: {
                return "IMS_CALL_RECEIVE";
            }
            case 14: {
                return "IMS_COMMAND_COMPLETE";
            }
            case 13: {
                return "IMS_COMMAND_FAILED";
            }
            case 12: {
                return "IMS_COMMAND_RECEIVED";
            }
            case 11: {
                return "IMS_COMMAND";
            }
            case 10: {
                return "RIL_CALL_LIST_CHANGED";
            }
            case 9: {
                return "RIL_CALL_SRVCC";
            }
            case 8: {
                return "RIL_CALL_RING";
            }
            case 7: {
                return "RIL_RESPONSE";
            }
            case 6: {
                return "RIL_REQUEST";
            }
            case 5: {
                return "DATA_CALL_LIST_CHANGED";
            }
            case 4: {
                return "IMS_CAPABILITIES_CHANGED";
            }
            case 3: {
                return "IMS_CONNECTION_STATE_CHANGED";
            }
            case 2: {
                return "RIL_SERVICE_STATE_CHANGED";
            }
            case 1: {
                return "SETTINGS_CHANGED";
            }
            case 0: 
        }
        return "EVENT_UNKNOWN";
    }

    private void convertConnectionToRilCall(GsmCdmaConnection gsmCdmaConnection, TelephonyProto.TelephonyCallSession.Event.RilCall rilCall, String string) {
        rilCall.type = gsmCdmaConnection.isIncoming() ? 2 : 1;
        switch (gsmCdmaConnection.getState()) {
            default: {
                rilCall.state = 0;
                break;
            }
            case DISCONNECTING: {
                rilCall.state = 9;
                break;
            }
            case DISCONNECTED: {
                rilCall.state = 8;
                break;
            }
            case WAITING: {
                rilCall.state = 7;
                break;
            }
            case INCOMING: {
                rilCall.state = 6;
                break;
            }
            case ALERTING: {
                rilCall.state = 5;
                break;
            }
            case DIALING: {
                rilCall.state = 4;
                break;
            }
            case HOLDING: {
                rilCall.state = 3;
                break;
            }
            case ACTIVE: {
                rilCall.state = 2;
                break;
            }
            case IDLE: {
                rilCall.state = 1;
            }
        }
        rilCall.callEndReason = gsmCdmaConnection.getDisconnectCause();
        rilCall.isMultiparty = gsmCdmaConnection.isMultiparty();
        rilCall.preciseDisconnectCause = gsmCdmaConnection.getPreciseDisconnectCause();
        if (gsmCdmaConnection.getDisconnectCause() != 0 && gsmCdmaConnection.isEmergencyCall() && gsmCdmaConnection.getEmergencyNumberInfo() != null && ThreadLocalRandom.current().nextDouble(0.0, 100.0) < this.getSamplePercentageForEmergencyCall(string)) {
            rilCall.isEmergencyCall = gsmCdmaConnection.isEmergencyCall();
            rilCall.emergencyNumberInfo = this.convertEmergencyNumberToEmergencyNumberInfo(gsmCdmaConnection.getEmergencyNumberInfo());
        }
    }

    private TelephonyProto.TelephonyCallSession.Event.RilCall[] convertConnectionsToRilCalls(ArrayList<GsmCdmaConnection> arrayList, String string) {
        TelephonyProto.TelephonyCallSession.Event.RilCall[] arrrilCall = new TelephonyProto.TelephonyCallSession.Event.RilCall[arrayList.size()];
        for (int i = 0; i < arrayList.size(); ++i) {
            arrrilCall[i] = new TelephonyProto.TelephonyCallSession.Event.RilCall();
            arrrilCall[i].index = i;
            this.convertConnectionToRilCall(arrayList.get(i), arrrilCall[i], string);
        }
        return arrrilCall;
    }

    private TelephonyProto.EmergencyNumberInfo convertEmergencyNumberToEmergencyNumberInfo(EmergencyNumber emergencyNumber) {
        TelephonyProto.EmergencyNumberInfo emergencyNumberInfo = new TelephonyProto.EmergencyNumberInfo();
        emergencyNumberInfo.address = emergencyNumber.getNumber();
        emergencyNumberInfo.countryIso = emergencyNumber.getCountryIso();
        emergencyNumberInfo.mnc = emergencyNumber.getMnc();
        emergencyNumberInfo.serviceCategoriesBitmask = emergencyNumber.getEmergencyServiceCategoryBitmask();
        emergencyNumberInfo.urns = (String[])emergencyNumber.getEmergencyUrns().stream().toArray((IntFunction<A[]>)_$$Lambda$TelephonyMetrics$fLmZDbNadlr6LF7zSJ6jCR1AAsk.INSTANCE);
        emergencyNumberInfo.numberSourcesBitmask = emergencyNumber.getEmergencyNumberSourceBitmask();
        emergencyNumberInfo.routing = emergencyNumber.getEmergencyCallRouting();
        return emergencyNumberInfo;
    }

    private int convertGsmCdmaCodec(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 9: {
                return 7;
            }
            case 8: {
                return 6;
            }
            case 7: {
                return 5;
            }
            case 6: {
                return 4;
            }
            case 5: {
                return 10;
            }
            case 4: {
                return 9;
            }
            case 3: {
                return 8;
            }
            case 2: {
                return 2;
            }
            case 1: 
        }
        return 1;
    }

    private static int convertImsCodec(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 20: {
                return 20;
            }
            case 19: {
                return 19;
            }
            case 18: {
                return 18;
            }
            case 17: {
                return 17;
            }
            case 16: {
                return 16;
            }
            case 15: {
                return 15;
            }
            case 14: {
                return 14;
            }
            case 13: {
                return 13;
            }
            case 12: {
                return 12;
            }
            case 11: {
                return 11;
            }
            case 10: {
                return 10;
            }
            case 9: {
                return 9;
            }
            case 8: {
                return 8;
            }
            case 7: {
                return 7;
            }
            case 6: {
                return 6;
            }
            case 5: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 3: {
                return 3;
            }
            case 2: {
                return 2;
            }
            case 1: 
        }
        return 1;
    }

    private static String convertProtoToBase64String(TelephonyProto.TelephonyLog telephonyLog) {
        return Base64.encodeToString((byte[])TelephonyProto.TelephonyLog.toByteArray(telephonyLog), (int)0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int convertSmsFormat(String string) {
        int n = 0;
        int n2 = string.hashCode();
        if (n2 != 1621908) {
            if (n2 == 50279198 && string.equals("3gpp2")) {
                return 2;
            }
        } else if (string.equals("3gpp")) {
            return 1;
        }
        n2 = -1;
        if (n2 == 0) return 1;
        if (n2 == 1) return 2;
        return n;
    }

    private boolean disconnectReasonsKnown(TelephonyProto.TelephonyCallSession.Event.RilCall[] arrrilCall) {
        int n = arrrilCall.length;
        for (int i = 0; i < n; ++i) {
            if (arrrilCall[i].callEndReason != 0) continue;
            return false;
        }
        return true;
    }

    private void finishCallSession(InProgressCallSession inProgressCallSession) {
        synchronized (this) {
            TelephonyProto.TelephonyCallSession telephonyCallSession = new TelephonyProto.TelephonyCallSession();
            telephonyCallSession.events = new TelephonyProto.TelephonyCallSession.Event[inProgressCallSession.events.size()];
            inProgressCallSession.events.toArray(telephonyCallSession.events);
            telephonyCallSession.startTimeMinutes = inProgressCallSession.startSystemTimeMin;
            telephonyCallSession.phoneId = inProgressCallSession.phoneId;
            telephonyCallSession.eventsDropped = inProgressCallSession.isEventsDropped();
            if (this.mCompletedCallSessions.size() >= 50) {
                this.mCompletedCallSessions.removeFirst();
            }
            this.mCompletedCallSessions.add(telephonyCallSession);
            this.mInProgressCallSessions.remove(inProgressCallSession.phoneId);
            this.logv("Call session finished");
            return;
        }
    }

    private TelephonyProto.SmsSession finishSmsSession(InProgressSmsSession inProgressSmsSession) {
        TelephonyProto.SmsSession smsSession = new TelephonyProto.SmsSession();
        smsSession.events = new TelephonyProto.SmsSession.Event[inProgressSmsSession.events.size()];
        inProgressSmsSession.events.toArray(smsSession.events);
        smsSession.startTimeMinutes = inProgressSmsSession.startSystemTimeMin;
        smsSession.phoneId = inProgressSmsSession.phoneId;
        smsSession.eventsDropped = inProgressSmsSession.isEventsDropped();
        if (this.mCompletedSmsSessions.size() >= 500) {
            this.mCompletedSmsSessions.removeFirst();
        }
        this.mCompletedSmsSessions.add(smsSession);
        return smsSession;
    }

    private void finishSmsSessionIfNeeded(InProgressSmsSession inProgressSmsSession) {
        synchronized (this) {
            if (inProgressSmsSession.getNumExpectedResponses() == 0) {
                this.finishSmsSession(inProgressSmsSession);
                this.mInProgressSmsSessions.remove(inProgressSmsSession.phoneId);
                this.logv("SMS session finished");
            }
            return;
        }
    }

    private int getCallId(ImsCallSession imsCallSession) {
        if (imsCallSession == null) {
            return -1;
        }
        try {
            int n = Integer.parseInt(imsCallSession.getCallId());
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }

    public static TelephonyMetrics getInstance() {
        synchronized (TelephonyMetrics.class) {
            TelephonyMetrics telephonyMetrics;
            if (sInstance == null) {
                sInstance = telephonyMetrics = new TelephonyMetrics();
            }
            telephonyMetrics = sInstance;
            return telephonyMetrics;
        }
    }

    private double getSamplePercentageForEmergencyCall(String string) {
        if ("cn,in".contains(string)) {
            return 1.0;
        }
        if ("us,id,br,pk,ng,bd,ru,mx,jp,et,ph,eg,vn,cd,tr,ir,de".contains(string)) {
            return 5.0;
        }
        if ("th,gb,fr,tz,it,za,mm,ke,kr,co,es,ug,ar,ua,dz,sd,iq".contains(string)) {
            return 15.0;
        }
        if ("pl,ca,af,ma,sa,pe,uz,ve,my,ao,mz,gh,np,ye,mg,kp,cm".contains(string)) {
            return 25.0;
        }
        if ("au,tw,ne,lk,bf,mw,ml,ro,kz,sy,cl,zm,gt,zw,nl,ec,sn".contains(string)) {
            return 35.0;
        }
        if ("kh,td,so,gn,ss,rw,bj,tn,bi,be,cu,bo,ht,gr,do,cz,pt".contains(string)) {
            return 45.0;
        }
        return 50.0;
    }

    static /* synthetic */ String[] lambda$convertEmergencyNumberToEmergencyNumberInfo$1(int n) {
        return new String[n];
    }

    static /* synthetic */ boolean lambda$updateActiveSubscriptionInfoList$0(int n, Integer n2) {
        return n2.equals(n);
    }

    static /* synthetic */ String[] lambda$writeCarrierIdMatchingEvent$2(int n) {
        return new String[n];
    }

    private void logv(String string) {
    }

    private static TelephonyProto.ActiveSubscriptionInfo makeInvalidSubscriptionInfo(int n) {
        TelephonyProto.ActiveSubscriptionInfo activeSubscriptionInfo = new TelephonyProto.ActiveSubscriptionInfo();
        activeSubscriptionInfo.slotIndex = n;
        activeSubscriptionInfo.carrierId = -1;
        activeSubscriptionInfo.isOpportunistic = -1;
        return activeSubscriptionInfo;
    }

    private static int mapSimStateToProto(int n) {
        if (n != 1) {
            if (n != 10) {
                return 0;
            }
            return 2;
        }
        return 1;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void printAllMetrics(PrintWriter object) {
        synchronized (this) {
            Object object2;
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)object, "  ");
            indentingPrintWriter.println("Telephony metrics proto:");
            indentingPrintWriter.println("------------------------------------------");
            indentingPrintWriter.println("Telephony events:");
            indentingPrintWriter.increaseIndent();
            for (TelephonyProto.TelephonyEvent telephonyEvent : this.mTelephonyEvents) {
                indentingPrintWriter.print(telephonyEvent.timestampMillis);
                indentingPrintWriter.print(" [");
                indentingPrintWriter.print(telephonyEvent.phoneId);
                indentingPrintWriter.print("] ");
                indentingPrintWriter.print("T=");
                if (telephonyEvent.type == 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(TelephonyMetrics.telephonyEventToString(telephonyEvent.type));
                    stringBuilder.append("(Data RAT ");
                    stringBuilder.append(telephonyEvent.serviceState.dataRat);
                    stringBuilder.append(" Voice RAT ");
                    stringBuilder.append(telephonyEvent.serviceState.voiceRat);
                    stringBuilder.append(" Channel Number ");
                    stringBuilder.append(telephonyEvent.serviceState.channelNumber);
                    stringBuilder.append(")");
                    indentingPrintWriter.print(stringBuilder.toString());
                } else {
                    indentingPrintWriter.print(TelephonyMetrics.telephonyEventToString(telephonyEvent.type));
                }
                indentingPrintWriter.println("");
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Call sessions:");
            indentingPrintWriter.increaseIndent();
            object = this.mCompletedCallSessions.iterator();
            do {
                if (!object.hasNext()) break;
                TelephonyProto.TelephonyCallSession telephonyCallSession = (TelephonyProto.TelephonyCallSession)object.next();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Start time in minutes: ");
                stringBuilder.append(telephonyCallSession.startTimeMinutes);
                indentingPrintWriter.print(stringBuilder.toString());
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(", phone: ");
                stringBuilder2.append(telephonyCallSession.phoneId);
                indentingPrintWriter.print(stringBuilder2.toString());
                if (telephonyCallSession.eventsDropped) {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(" Events dropped: ");
                    stringBuilder3.append(telephonyCallSession.eventsDropped);
                    indentingPrintWriter.println(stringBuilder3.toString());
                }
                indentingPrintWriter.println(" Events: ");
                indentingPrintWriter.increaseIndent();
                for (TelephonyProto.TelephonyCallSession.Event event : telephonyCallSession.events) {
                    indentingPrintWriter.print(event.delay);
                    indentingPrintWriter.print(" T=");
                    if (event.type == 2) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(TelephonyMetrics.callSessionEventToString(event.type));
                        ((StringBuilder)object2).append("(Data RAT ");
                        ((StringBuilder)object2).append(event.serviceState.dataRat);
                        ((StringBuilder)object2).append(" Voice RAT ");
                        ((StringBuilder)object2).append(event.serviceState.voiceRat);
                        ((StringBuilder)object2).append(" Channel Number ");
                        ((StringBuilder)object2).append(event.serviceState.channelNumber);
                        ((StringBuilder)object2).append(")");
                        indentingPrintWriter.println(((StringBuilder)object2).toString());
                        continue;
                    }
                    if (event.type == 10) {
                        indentingPrintWriter.println(TelephonyMetrics.callSessionEventToString(event.type));
                        indentingPrintWriter.increaseIndent();
                        for (TelephonyProto.TelephonyCallSession.Event.RilCall rilCall : event.calls) {
                            StringBuilder stringBuilder4 = new StringBuilder();
                            stringBuilder4.append(rilCall.index);
                            stringBuilder4.append(". Type = ");
                            stringBuilder4.append(rilCall.type);
                            stringBuilder4.append(" State = ");
                            stringBuilder4.append(rilCall.state);
                            stringBuilder4.append(" End Reason ");
                            stringBuilder4.append(rilCall.callEndReason);
                            stringBuilder4.append(" Precise Disconnect Cause ");
                            stringBuilder4.append(rilCall.preciseDisconnectCause);
                            stringBuilder4.append(" isMultiparty = ");
                            stringBuilder4.append(rilCall.isMultiparty);
                            indentingPrintWriter.println(stringBuilder4.toString());
                        }
                        indentingPrintWriter.decreaseIndent();
                        continue;
                    }
                    if (event.type == 22) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(TelephonyMetrics.callSessionEventToString(event.type));
                        ((StringBuilder)object2).append("(");
                        ((StringBuilder)object2).append(event.audioCodec);
                        ((StringBuilder)object2).append(")");
                        indentingPrintWriter.println(((StringBuilder)object2).toString());
                        continue;
                    }
                    indentingPrintWriter.println(TelephonyMetrics.callSessionEventToString(event.type));
                }
                indentingPrintWriter.decreaseIndent();
            } while (true);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Sms sessions:");
            indentingPrintWriter.increaseIndent();
            int n = 0;
            object = this.mCompletedSmsSessions.iterator();
            do {
                if (!object.hasNext()) {
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("Modem power stats:");
                    indentingPrintWriter.increaseIndent();
                    object = new ModemPowerMetrics();
                    object = ((ModemPowerMetrics)object).buildProto();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Power log duration (battery time) (ms): ");
                    stringBuilder.append(((TelephonyProto.ModemPowerStats)object).loggingDurationMs);
                    indentingPrintWriter.println(stringBuilder.toString());
                    StringBuilder stringBuilder5 = new StringBuilder();
                    stringBuilder5.append("Energy consumed by modem (mAh): ");
                    stringBuilder5.append(((TelephonyProto.ModemPowerStats)object).energyConsumedMah);
                    indentingPrintWriter.println(stringBuilder5.toString());
                    StringBuilder stringBuilder6 = new StringBuilder();
                    stringBuilder6.append("Number of packets sent (tx): ");
                    stringBuilder6.append(((TelephonyProto.ModemPowerStats)object).numPacketsTx);
                    indentingPrintWriter.println(stringBuilder6.toString());
                    StringBuilder stringBuilder7 = new StringBuilder();
                    stringBuilder7.append("Number of bytes sent (tx): ");
                    stringBuilder7.append(((TelephonyProto.ModemPowerStats)object).numBytesTx);
                    indentingPrintWriter.println(stringBuilder7.toString());
                    StringBuilder stringBuilder8 = new StringBuilder();
                    stringBuilder8.append("Number of packets received (rx): ");
                    stringBuilder8.append(((TelephonyProto.ModemPowerStats)object).numPacketsRx);
                    indentingPrintWriter.println(stringBuilder8.toString());
                    StringBuilder stringBuilder9 = new StringBuilder();
                    stringBuilder9.append("Number of bytes received (rx): ");
                    stringBuilder9.append(((TelephonyProto.ModemPowerStats)object).numBytesRx);
                    indentingPrintWriter.println(stringBuilder9.toString());
                    StringBuilder stringBuilder10 = new StringBuilder();
                    stringBuilder10.append("Amount of time kernel is active because of cellular data (ms): ");
                    stringBuilder10.append(((TelephonyProto.ModemPowerStats)object).cellularKernelActiveTimeMs);
                    indentingPrintWriter.println(stringBuilder10.toString());
                    StringBuilder stringBuilder11 = new StringBuilder();
                    stringBuilder11.append("Amount of time spent in very poor rx signal level (ms): ");
                    stringBuilder11.append(((TelephonyProto.ModemPowerStats)object).timeInVeryPoorRxSignalLevelMs);
                    indentingPrintWriter.println(stringBuilder11.toString());
                    StringBuilder stringBuilder12 = new StringBuilder();
                    stringBuilder12.append("Amount of time modem is in sleep (ms): ");
                    stringBuilder12.append(((TelephonyProto.ModemPowerStats)object).sleepTimeMs);
                    indentingPrintWriter.println(stringBuilder12.toString());
                    StringBuilder stringBuilder13 = new StringBuilder();
                    stringBuilder13.append("Amount of time modem is in idle (ms): ");
                    stringBuilder13.append(((TelephonyProto.ModemPowerStats)object).idleTimeMs);
                    indentingPrintWriter.println(stringBuilder13.toString());
                    StringBuilder stringBuilder14 = new StringBuilder();
                    stringBuilder14.append("Amount of time modem is in rx (ms): ");
                    stringBuilder14.append(((TelephonyProto.ModemPowerStats)object).rxTimeMs);
                    indentingPrintWriter.println(stringBuilder14.toString());
                    StringBuilder stringBuilder15 = new StringBuilder();
                    stringBuilder15.append("Amount of time modem is in tx (ms): ");
                    stringBuilder15.append(Arrays.toString(((TelephonyProto.ModemPowerStats)object).txTimeMs));
                    indentingPrintWriter.println(stringBuilder15.toString());
                    StringBuilder stringBuilder16 = new StringBuilder();
                    stringBuilder16.append("Amount of time phone spent in various Radio Access Technologies (ms): ");
                    stringBuilder16.append(Arrays.toString(((TelephonyProto.ModemPowerStats)object).timeInRatMs));
                    indentingPrintWriter.println(stringBuilder16.toString());
                    StringBuilder stringBuilder17 = new StringBuilder();
                    stringBuilder17.append("Amount of time phone spent in various cellular rx signal strength levels (ms): ");
                    stringBuilder17.append(Arrays.toString(((TelephonyProto.ModemPowerStats)object).timeInRxSignalStrengthLevelMs));
                    indentingPrintWriter.println(stringBuilder17.toString());
                    StringBuilder stringBuilder18 = new StringBuilder();
                    stringBuilder18.append("Energy consumed across measured modem rails (mAh): ");
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    stringBuilder18.append(decimalFormat.format(((TelephonyProto.ModemPowerStats)object).monitoredRailEnergyConsumedMah));
                    indentingPrintWriter.println(stringBuilder18.toString());
                    indentingPrintWriter.decreaseIndent();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Hardware Version: ");
                    ((StringBuilder)object).append(SystemProperties.get((String)"ro.boot.revision", (String)""));
                    indentingPrintWriter.println(((StringBuilder)object).toString());
                    return;
                }
                TelephonyProto.SmsSession smsSession = (TelephonyProto.SmsSession)object.next();
                int n2 = n + 1;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[");
                stringBuilder.append(n2);
                stringBuilder.append("] Start time in minutes: ");
                stringBuilder.append(smsSession.startTimeMinutes);
                indentingPrintWriter.print(stringBuilder.toString());
                StringBuilder stringBuilder19 = new StringBuilder();
                stringBuilder19.append(", phone: ");
                stringBuilder19.append(smsSession.phoneId);
                indentingPrintWriter.print(stringBuilder19.toString());
                if (smsSession.eventsDropped) {
                    StringBuilder stringBuilder20 = new StringBuilder();
                    stringBuilder20.append(", events dropped: ");
                    stringBuilder20.append(smsSession.eventsDropped);
                    indentingPrintWriter.println(stringBuilder20.toString());
                } else {
                    indentingPrintWriter.println("");
                }
                indentingPrintWriter.println("Events: ");
                indentingPrintWriter.increaseIndent();
                for (TelephonyProto.SmsSession.Event event : smsSession.events) {
                    indentingPrintWriter.print(event.delay);
                    indentingPrintWriter.print(" T=");
                    indentingPrintWriter.println(TelephonyMetrics.smsSessionEventToString(event.type));
                    if (event.type == 8) {
                        indentingPrintWriter.increaseIndent();
                        int n3 = event.smsType;
                        if (n3 != 1) {
                            if (n3 != 2) {
                                if (n3 != 3) {
                                    if (n3 == 4) {
                                        indentingPrintWriter.println("Type: WAP PUSH");
                                    }
                                } else {
                                    indentingPrintWriter.println("Type: zero");
                                }
                            } else {
                                indentingPrintWriter.println("Type: Voicemail indication");
                            }
                        } else {
                            indentingPrintWriter.println("Type: SMS-PP");
                        }
                        if (event.errorCode != 0) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("E=");
                            ((StringBuilder)object2).append(event.errorCode);
                            indentingPrintWriter.println(((StringBuilder)object2).toString());
                        }
                        indentingPrintWriter.decreaseIndent();
                        continue;
                    }
                    if (event.type != 6 && event.type != 7) {
                        if (event.type != 10) continue;
                        indentingPrintWriter.increaseIndent();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Received: ");
                        ((StringBuilder)object2).append(event.incompleteSms.receivedParts);
                        ((StringBuilder)object2).append("/");
                        ((StringBuilder)object2).append(event.incompleteSms.totalParts);
                        indentingPrintWriter.println(((StringBuilder)object2).toString());
                        indentingPrintWriter.decreaseIndent();
                        continue;
                    }
                    indentingPrintWriter.increaseIndent();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("ReqId=");
                    ((StringBuilder)object2).append(event.rilRequestId);
                    indentingPrintWriter.println(((StringBuilder)object2).toString());
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("E=");
                    ((StringBuilder)object2).append(event.errorCode);
                    indentingPrintWriter.println(((StringBuilder)object2).toString());
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("RilE=");
                    ((StringBuilder)object2).append(event.error);
                    indentingPrintWriter.println(((StringBuilder)object2).toString());
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("ImsE=");
                    ((StringBuilder)object2).append(event.imsError);
                    indentingPrintWriter.println(((StringBuilder)object2).toString());
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
                n = n2;
            } while (true);
        }
    }

    private void reset() {
        synchronized (this) {
            int n;
            this.mTelephonyEvents.clear();
            this.mCompletedCallSessions.clear();
            this.mCompletedSmsSessions.clear();
            this.mTelephonyEventsDropped = false;
            this.mStartSystemTimeMs = System.currentTimeMillis();
            this.mStartElapsedTimeMs = SystemClock.elapsedRealtime();
            TelephonyEventBuilder telephonyEventBuilder = new TelephonyEventBuilder(this.mStartElapsedTimeMs, -1);
            this.addTelephonyEvent(telephonyEventBuilder.setSimStateChange(this.mLastSimState).build());
            telephonyEventBuilder = new TelephonyEventBuilder(this.mStartElapsedTimeMs, -1);
            this.addTelephonyEvent(telephonyEventBuilder.setEnabledModemBitmap(this.mLastEnabledModemBitmap).build());
            int n2 = 0;
            do {
                if (n2 >= this.mLastActiveSubscriptionInfos.size()) break;
                n = this.mLastActiveSubscriptionInfos.keyAt(n2);
                telephonyEventBuilder = new TelephonyEventBuilder(this.mStartElapsedTimeMs, n);
                this.addTelephonyEvent(telephonyEventBuilder.setActiveSubscriptionInfoChange((TelephonyProto.ActiveSubscriptionInfo)this.mLastActiveSubscriptionInfos.get(n)).build());
                ++n2;
            } while (true);
            n2 = 0;
            do {
                if (n2 >= this.mLastServiceState.size()) break;
                n = this.mLastServiceState.keyAt(n2);
                telephonyEventBuilder = new TelephonyEventBuilder(this.mStartElapsedTimeMs, n);
                this.addTelephonyEvent(telephonyEventBuilder.setServiceState((TelephonyProto.TelephonyServiceState)this.mLastServiceState.get(n)).build());
                ++n2;
            } while (true);
            n2 = 0;
            do {
                if (n2 >= this.mLastImsCapabilities.size()) break;
                n = this.mLastImsCapabilities.keyAt(n2);
                telephonyEventBuilder = new TelephonyEventBuilder(this.mStartElapsedTimeMs, n);
                this.addTelephonyEvent(telephonyEventBuilder.setImsCapabilities((TelephonyProto.ImsCapabilities)this.mLastImsCapabilities.get(n)).build());
                ++n2;
            } while (true);
            n2 = 0;
            do {
                if (n2 >= this.mLastImsConnectionState.size()) break;
                n = this.mLastImsConnectionState.keyAt(n2);
                telephonyEventBuilder = new TelephonyEventBuilder(this.mStartElapsedTimeMs, n);
                this.addTelephonyEvent(telephonyEventBuilder.setImsConnectionState((TelephonyProto.ImsConnectionState)this.mLastImsConnectionState.get(n)).build());
                ++n2;
            } while (true);
            n2 = 0;
            do {
                if (n2 >= this.mLastCarrierId.size()) break;
                n = this.mLastCarrierId.keyAt(n2);
                telephonyEventBuilder = new TelephonyEventBuilder(this.mStartElapsedTimeMs, n);
                this.addTelephonyEvent(telephonyEventBuilder.setCarrierIdMatching((TelephonyProto.TelephonyEvent.CarrierIdMatching)this.mLastCarrierId.get(n)).build());
                ++n2;
            } while (true);
            n2 = 0;
            do {
                if (n2 >= this.mLastRilDataCallEvents.size()) break;
                int n3 = this.mLastRilDataCallEvents.keyAt(n2);
                n = 0;
                do {
                    if (n >= ((SparseArray)this.mLastRilDataCallEvents.get(n3)).size()) break;
                    int n4 = ((SparseArray)this.mLastRilDataCallEvents.get(n3)).keyAt(n);
                    TelephonyProto.RilDataCall rilDataCall = (TelephonyProto.RilDataCall)((SparseArray)this.mLastRilDataCallEvents.get(n3)).get(n4);
                    telephonyEventBuilder = new TelephonyEventBuilder(this.mStartElapsedTimeMs, n3);
                    this.addTelephonyEvent(telephonyEventBuilder.setDataCalls(new TelephonyProto.RilDataCall[]{rilDataCall}).build());
                    ++n;
                } while (true);
                ++n2;
            } while (true);
            return;
        }
    }

    static int roundSessionStart(long l) {
        return (int)(l / 300000L * 5L);
    }

    private static String smsSessionEventToString(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 10: {
                return "INCOMPLETE_SMS_RECEIVED";
            }
            case 8: {
                return "SMS_RECEIVED";
            }
            case 7: {
                return "SMS_SEND_RESULT";
            }
            case 6: {
                return "SMS_SEND";
            }
            case 5: {
                return "DATA_CALL_LIST_CHANGED";
            }
            case 4: {
                return "IMS_CAPABILITIES_CHANGED";
            }
            case 3: {
                return "IMS_CONNECTION_STATE_CHANGED";
            }
            case 2: {
                return "RIL_SERVICE_STATE_CHANGED";
            }
            case 1: {
                return "SETTINGS_CHANGED";
            }
            case 0: 
        }
        return "EVENT_UNKNOWN";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private InProgressCallSession startNewCallSessionIfNeeded(int n) {
        synchronized (this) {
            long l;
            Object object;
            Object object2;
            Object object3 = object = (InProgressCallSession)this.mInProgressCallSessions.get(n);
            if (object != null) return object3;
            object = new StringBuilder();
            ((StringBuilder)object).append("Starting a new call session on phone ");
            ((StringBuilder)object).append(n);
            this.logv(((StringBuilder)object).toString());
            object = new InProgressCallSession(n);
            this.mInProgressCallSessions.append(n, object);
            object3 = (TelephonyProto.TelephonyServiceState)this.mLastServiceState.get(n);
            if (object3 != null) {
                l = ((InProgressCallSession)object).startElapsedTimeMs;
                object2 = new CallSessionEventBuilder(2);
                ((InProgressCallSession)object).addEvent(l, ((CallSessionEventBuilder)object2).setServiceState((TelephonyProto.TelephonyServiceState)object3));
            }
            if ((object3 = (TelephonyProto.ImsCapabilities)this.mLastImsCapabilities.get(n)) != null) {
                l = ((InProgressCallSession)object).startElapsedTimeMs;
                object2 = new CallSessionEventBuilder(4);
                ((InProgressCallSession)object).addEvent(l, ((CallSessionEventBuilder)object2).setImsCapabilities((TelephonyProto.ImsCapabilities)object3));
            }
            object2 = (TelephonyProto.ImsConnectionState)this.mLastImsConnectionState.get(n);
            object3 = object;
            if (object2 == null) return object3;
            l = ((InProgressCallSession)object).startElapsedTimeMs;
            object3 = new CallSessionEventBuilder(3);
            ((InProgressCallSession)object).addEvent(l, ((CallSessionEventBuilder)object3).setImsConnectionState((TelephonyProto.ImsConnectionState)object2));
            return object;
        }
    }

    private InProgressSmsSession startNewSmsSession(int n) {
        InProgressSmsSession inProgressSmsSession = new InProgressSmsSession(n);
        ExtendableMessageNano extendableMessageNano = (TelephonyProto.TelephonyServiceState)this.mLastServiceState.get(n);
        if (extendableMessageNano != null) {
            inProgressSmsSession.addEvent(inProgressSmsSession.startElapsedTimeMs, new SmsSessionEventBuilder(2).setServiceState((TelephonyProto.TelephonyServiceState)extendableMessageNano));
        }
        if ((extendableMessageNano = (TelephonyProto.ImsCapabilities)this.mLastImsCapabilities.get(n)) != null) {
            inProgressSmsSession.addEvent(inProgressSmsSession.startElapsedTimeMs, new SmsSessionEventBuilder(4).setImsCapabilities((TelephonyProto.ImsCapabilities)extendableMessageNano));
        }
        if ((extendableMessageNano = (TelephonyProto.ImsConnectionState)this.mLastImsConnectionState.get(n)) != null) {
            inProgressSmsSession.addEvent(inProgressSmsSession.startElapsedTimeMs, new SmsSessionEventBuilder(3).setImsConnectionState((TelephonyProto.ImsConnectionState)extendableMessageNano));
        }
        return inProgressSmsSession;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private InProgressSmsSession startNewSmsSessionIfNeeded(int n) {
        synchronized (this) {
            InProgressSmsSession inProgressSmsSession = (InProgressSmsSession)this.mInProgressSmsSessions.get(n);
            Object object = inProgressSmsSession;
            if (inProgressSmsSession == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Starting a new sms session on phone ");
                ((StringBuilder)object).append(n);
                this.logv(((StringBuilder)object).toString());
                object = this.startNewSmsSession(n);
                this.mInProgressSmsSessions.append(n, object);
            }
            return object;
        }
    }

    private static String telephonyEventToString(int n) {
        if (n != 21) {
            switch (n) {
                default: {
                    return Integer.toString(n);
                }
                case 13: {
                    return "CARRIER_ID_MATCHING";
                }
                case 12: {
                    return "NITZ_TIME";
                }
                case 11: {
                    return "MODEM_RESTART";
                }
                case 10: {
                    return "DATA_STALL_ACTION";
                }
                case 9: {
                    return "DATA_CALL_DEACTIVATE_RESPONSE";
                }
                case 8: {
                    return "DATA_CALL_DEACTIVATE";
                }
                case 7: {
                    return "DATA_CALL_LIST_CHANGED";
                }
                case 6: {
                    return "DATA_CALL_SETUP_RESPONSE";
                }
                case 5: {
                    return "DATA_CALL_SETUP";
                }
                case 4: {
                    return "IMS_CAPABILITIES_CHANGED";
                }
                case 3: {
                    return "IMS_CONNECTION_STATE_CHANGED";
                }
                case 2: {
                    return "RIL_SERVICE_STATE_CHANGED";
                }
                case 1: {
                    return "SETTINGS_CHANGED";
                }
                case 0: 
            }
            return "UNKNOWN";
        }
        return "EMERGENCY_NUMBER_REPORT";
    }

    public static TelephonyProto.TelephonyCallSession.Event.CallQuality toCallQualityProto(CallQuality callQuality) {
        TelephonyProto.TelephonyCallSession.Event.CallQuality callQuality2 = new TelephonyProto.TelephonyCallSession.Event.CallQuality();
        if (callQuality != null) {
            callQuality2.downlinkLevel = TelephonyMetrics.callQualityLevelToProtoEnum(callQuality.getDownlinkCallQualityLevel());
            callQuality2.uplinkLevel = TelephonyMetrics.callQualityLevelToProtoEnum(callQuality.getUplinkCallQualityLevel());
            callQuality2.durationInSeconds = callQuality.getCallDuration() / 1000;
            callQuality2.rtpPacketsTransmitted = callQuality.getNumRtpPacketsTransmitted();
            callQuality2.rtpPacketsReceived = callQuality.getNumRtpPacketsReceived();
            callQuality2.rtpPacketsTransmittedLost = callQuality.getNumRtpPacketsTransmittedLost();
            callQuality2.rtpPacketsNotReceived = callQuality.getNumRtpPacketsNotReceived();
            callQuality2.averageRelativeJitterMillis = callQuality.getAverageRelativeJitter();
            callQuality2.maxRelativeJitterMillis = callQuality.getMaxRelativeJitter();
            callQuality2.codecType = TelephonyMetrics.convertImsCodec(callQuality.getCodecType());
        }
        return callQuality2;
    }

    private int toCallSessionRilRequest(int n) {
        if (n != 10) {
            if (n != 36) {
                if (n != 40) {
                    if (n != 84) {
                        switch (n) {
                            default: {
                                String string = TAG;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unknown RIL request: ");
                                stringBuilder.append(n);
                                Rlog.e((String)string, (String)stringBuilder.toString());
                                return 0;
                            }
                            case 16: {
                                return 7;
                            }
                            case 15: {
                                return 5;
                            }
                            case 12: 
                            case 13: 
                            case 14: 
                        }
                        return 3;
                    }
                    return 6;
                }
                return 2;
            }
            return 4;
        }
        return 1;
    }

    private TelephonyProto.ImsReasonInfo toImsReasonInfoProto(ImsReasonInfo object) {
        TelephonyProto.ImsReasonInfo imsReasonInfo = new TelephonyProto.ImsReasonInfo();
        if (object != null) {
            imsReasonInfo.reasonCode = object.getCode();
            imsReasonInfo.extraCode = object.getExtraCode();
            if ((object = object.getExtraMessage()) != null) {
                imsReasonInfo.extraMessage = object;
            }
        }
        return imsReasonInfo;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int toPdpType(String var1_1) {
        block8 : {
            switch (var1_1.hashCode()) {
                case 329043114: {
                    if (!var1_1.equals("UNSTRUCTURED")) break;
                    var2_2 = 5;
                    break block8;
                }
                case 2254343: {
                    if (!var1_1.equals("IPV6")) break;
                    var2_2 = 1;
                    break block8;
                }
                case 79440: {
                    if (!var1_1.equals("PPP")) break;
                    var2_2 = 3;
                    break block8;
                }
                case 2343: {
                    if (!var1_1.equals("IP")) break;
                    var2_2 = 0;
                    break block8;
                }
                case -1986566073: {
                    if (!var1_1.equals("NON-IP")) break;
                    var2_2 = 4;
                    break block8;
                }
                case -2128542875: {
                    if (!var1_1.equals("IPV4V6")) break;
                    var2_2 = 2;
                    break block8;
                }
            }
            ** break;
lbl27: // 1 sources:
            var2_2 = -1;
        }
        if (var2_2 == 0) return 1;
        if (var2_2 == 1) return 2;
        if (var2_2 == 2) return 3;
        if (var2_2 == 3) return 4;
        if (var2_2 == 4) return 5;
        if (var2_2 == 5) return 6;
        var3_3 = TelephonyMetrics.TAG;
        var4_4 = new StringBuilder();
        var4_4.append("Unknown type: ");
        var4_4.append(var1_1);
        Rlog.e((String)var3_3, (String)var4_4.toString());
        return 0;
    }

    static int toPrivacyFuzzedTimeInterval(long l, long l2) {
        if ((l = l2 - l) < 0L) {
            return 0;
        }
        if (l <= 10L) {
            return 1;
        }
        if (l <= 20L) {
            return 2;
        }
        if (l <= 50L) {
            return 3;
        }
        if (l <= 100L) {
            return 4;
        }
        if (l <= 200L) {
            return 5;
        }
        if (l <= 500L) {
            return 6;
        }
        if (l <= 1000L) {
            return 7;
        }
        if (l <= 2000L) {
            return 8;
        }
        if (l <= 5000L) {
            return 9;
        }
        if (l <= 10000L) {
            return 10;
        }
        if (l <= 30000L) {
            return 11;
        }
        if (l <= 60000L) {
            return 12;
        }
        if (l <= 180000L) {
            return 13;
        }
        if (l <= 600000L) {
            return 14;
        }
        if (l <= 1800000L) {
            return 15;
        }
        if (l <= 3600000L) {
            return 16;
        }
        if (l <= 7200000L) {
            return 17;
        }
        if (l <= 14400000L) {
            return 18;
        }
        return 19;
    }

    private TelephonyProto.TelephonyServiceState toServiceStateProto(ServiceState serviceState) {
        TelephonyProto.TelephonyServiceState telephonyServiceState = new TelephonyProto.TelephonyServiceState();
        telephonyServiceState.voiceRoamingType = serviceState.getVoiceRoamingType();
        telephonyServiceState.dataRoamingType = serviceState.getDataRoamingType();
        telephonyServiceState.voiceOperator = new TelephonyProto.TelephonyServiceState.TelephonyOperator();
        if (serviceState.getVoiceOperatorAlphaLong() != null) {
            telephonyServiceState.voiceOperator.alphaLong = serviceState.getVoiceOperatorAlphaLong();
        }
        if (serviceState.getVoiceOperatorAlphaShort() != null) {
            telephonyServiceState.voiceOperator.alphaShort = serviceState.getVoiceOperatorAlphaShort();
        }
        if (serviceState.getVoiceOperatorNumeric() != null) {
            telephonyServiceState.voiceOperator.numeric = serviceState.getVoiceOperatorNumeric();
        }
        telephonyServiceState.dataOperator = new TelephonyProto.TelephonyServiceState.TelephonyOperator();
        if (serviceState.getDataOperatorAlphaLong() != null) {
            telephonyServiceState.dataOperator.alphaLong = serviceState.getDataOperatorAlphaLong();
        }
        if (serviceState.getDataOperatorAlphaShort() != null) {
            telephonyServiceState.dataOperator.alphaShort = serviceState.getDataOperatorAlphaShort();
        }
        if (serviceState.getDataOperatorNumeric() != null) {
            telephonyServiceState.dataOperator.numeric = serviceState.getDataOperatorNumeric();
        }
        telephonyServiceState.voiceRat = serviceState.getRilVoiceRadioTechnology();
        telephonyServiceState.dataRat = serviceState.getRilDataRadioTechnology();
        telephonyServiceState.channelNumber = serviceState.getChannelNumber();
        return telephonyServiceState;
    }

    private void writeIncomingSmsSessionWithType(int n, int n2, boolean bl, String string, long[] arrl, boolean bl2, boolean bl3) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Logged SMS session consisting of ");
        ((StringBuilder)object).append(arrl.length);
        ((StringBuilder)object).append(" parts, over IMS = ");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(" blocked = ");
        ((StringBuilder)object).append(bl2);
        ((StringBuilder)object).append(" type = ");
        ((StringBuilder)object).append(n2);
        this.logv(((StringBuilder)object).toString());
        object = this.startNewSmsSession(n);
        for (long l : arrl) {
            SmsSessionEventBuilder smsSessionEventBuilder = new SmsSessionEventBuilder(8).setFormat(this.convertSmsFormat(string));
            int n3 = 1;
            int n4 = bl ? 3 : 1;
            smsSessionEventBuilder = smsSessionEventBuilder.setTech(n4);
            n4 = bl3 ? 0 : n3;
            ((InProgressSmsSession)object).addEvent(l, smsSessionEventBuilder.setErrorCode(n4).setSmsType(n2).setBlocked(bl2));
        }
        this.finishSmsSession((InProgressSmsSession)object);
    }

    private void writeIncomingSmsWithType(int n, int n2, String object, boolean bl) {
        InProgressSmsSession inProgressSmsSession = this.startNewSmsSession(n);
        object = new SmsSessionEventBuilder(8).setFormat(this.convertSmsFormat((String)object)).setSmsType(n2);
        n = bl ? 0 : 1;
        inProgressSmsSession.addEvent(((SmsSessionEventBuilder)object).setErrorCode(n));
        this.finishSmsSession(inProgressSmsSession);
    }

    private void writeOnCallSolicitedResponse(int n, int n2, int n3, int n4) {
        InProgressCallSession inProgressCallSession = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (inProgressCallSession == null) {
            Rlog.e((String)TAG, (String)"writeOnCallSolicitedResponse: Call session is missing");
        } else {
            inProgressCallSession.addEvent(new CallSessionEventBuilder(7).setRilRequest(this.toCallSessionRilRequest(n4)).setRilRequestId(n2).setRilError(n3 + 1));
        }
    }

    private void writeOnDeactivateDataCallResponse(int n, int n2) {
        this.addTelephonyEvent(new TelephonyEventBuilder(n).setDeactivateDataCallResponse(n2 + 1).build());
    }

    private void writeOnSetupDataCallResponse(int n, int n2, int n3, int n4, DataCallResponse dataCallResponse) {
        TelephonyProto.TelephonyEvent.RilSetupDataCallResponse rilSetupDataCallResponse = new TelephonyProto.TelephonyEvent.RilSetupDataCallResponse();
        TelephonyProto.RilDataCall rilDataCall = new TelephonyProto.RilDataCall();
        if (dataCallResponse != null) {
            n2 = dataCallResponse.getCause() == 0 ? 1 : dataCallResponse.getCause();
            rilSetupDataCallResponse.status = n2;
            rilSetupDataCallResponse.suggestedRetryTimeMillis = dataCallResponse.getSuggestedRetryTime();
            rilDataCall.cid = dataCallResponse.getId();
            rilDataCall.type = dataCallResponse.getProtocolType() + 1;
            if (!TextUtils.isEmpty((CharSequence)dataCallResponse.getInterfaceName())) {
                rilDataCall.iframe = dataCallResponse.getInterfaceName();
            }
        }
        rilSetupDataCallResponse.call = rilDataCall;
        this.addTelephonyEvent(new TelephonyEventBuilder(n).setSetupDataCallResponse(rilSetupDataCallResponse).build());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeOnSmsSolicitedResponse(int n, int n2, int n3, SmsResponse object) {
        synchronized (this) {
            InProgressSmsSession inProgressSmsSession = (InProgressSmsSession)this.mInProgressSmsSessions.get(n);
            if (inProgressSmsSession == null) {
                Rlog.e((String)TAG, (String)"SMS session is missing");
            } else {
                n = 0;
                if (object != null) {
                    n = ((SmsResponse)object).mErrorCode;
                }
                object = new SmsSessionEventBuilder(7);
                inProgressSmsSession.addEvent(((SmsSessionEventBuilder)object).setErrorCode(n).setRilErrno(n3 + 1).setRilRequestId(n2));
                inProgressSmsSession.decreaseExpectedResponse();
                this.finishSmsSessionIfNeeded(inProgressSmsSession);
            }
            return;
        }
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        block12 : {
            int n;
            int n2;
            block16 : {
                block15 : {
                    block13 : {
                        block14 : {
                            if (arrstring == null || arrstring.length <= 0) break block12;
                            n = n2 = 1;
                            if (arrstring.length > 1) {
                                n = n2;
                                if ("--keep".equals(arrstring[1])) {
                                    n = 0;
                                }
                            }
                            n2 = 0;
                            object = arrstring[0];
                            int n3 = ((String)object).hashCode();
                            if (n3 == -1953159389) break block13;
                            if (n3 == 513805138) break block14;
                            if (n3 != 950313125 || !((String)object).equals("--metricsproto")) break block15;
                            n2 = 1;
                            break block16;
                        }
                        if (!((String)object).equals("--metricsprototext")) break block15;
                        n2 = 2;
                        break block16;
                    }
                    if (((String)object).equals("--metrics")) break block16;
                }
                n2 = -1;
            }
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 == 2) {
                        printWriter.println(this.buildProto().toString());
                    }
                } else {
                    printWriter.println(TelephonyMetrics.convertProtoToBase64String(this.buildProto()));
                    if (n != 0) {
                        this.reset();
                    }
                }
            } else {
                this.printAllMetrics(printWriter);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateActiveSubscriptionInfoList(List<SubscriptionInfo> object) {
        synchronized (this) {
            int n;
            Object object2 = new ArrayList();
            for (n = 0; n < this.mLastActiveSubscriptionInfos.size(); ++n) {
                object2.add(this.mLastActiveSubscriptionInfos.keyAt(n));
            }
            object = object.iterator();
            while (object.hasNext()) {
                Object object3 = (SubscriptionInfo)object.next();
                int n2 = object3.getSimSlotIndex();
                Object object4 = new _$$Lambda$TelephonyMetrics$tQOsX1lKb2eTuPp_1rpkeIAEOoY(n2);
                object2.removeIf(object4);
                object4 = new TelephonyProto.ActiveSubscriptionInfo();
                ((TelephonyProto.ActiveSubscriptionInfo)object4).slotIndex = n2;
                n = object3.isOpportunistic() ? 1 : 0;
                ((TelephonyProto.ActiveSubscriptionInfo)object4).isOpportunistic = n;
                ((TelephonyProto.ActiveSubscriptionInfo)object4).carrierId = object3.getCarrierId();
                if (MessageNano.messageNanoEquals((MessageNano)this.mLastActiveSubscriptionInfos.get(n2), (MessageNano)object4)) continue;
                object3 = new TelephonyEventBuilder(n2);
                this.addTelephonyEvent(((TelephonyEventBuilder)object3).setActiveSubscriptionInfoChange((TelephonyProto.ActiveSubscriptionInfo)object4).build());
                this.mLastActiveSubscriptionInfos.put(n2, object4);
            }
            object2 = object2.iterator();
            while (object2.hasNext()) {
                n = (Integer)object2.next();
                this.mLastActiveSubscriptionInfos.remove(n);
                object = new TelephonyEventBuilder(n);
                this.addTelephonyEvent(((TelephonyEventBuilder)object).setActiveSubscriptionInfoChange(TelephonyMetrics.makeInvalidSubscriptionInfo(n)).build());
            }
            return;
        }
    }

    public void updateEnabledModemBitmap(int n) {
        if (this.mLastEnabledModemBitmap == n) {
            return;
        }
        this.mLastEnabledModemBitmap = n;
        this.addTelephonyEvent(new TelephonyEventBuilder().setEnabledModemBitmap(this.mLastEnabledModemBitmap).build());
    }

    public void updateSimState(int n, int n2) {
        n2 = TelephonyMetrics.mapSimStateToProto(n2);
        Integer n3 = (Integer)this.mLastSimState.get(n);
        if (n3 == null || !n3.equals(n2)) {
            this.mLastSimState.put(n, (Object)n2);
            this.addTelephonyEvent(new TelephonyEventBuilder().setSimStateChange(this.mLastSimState).build());
        }
    }

    public void writeAudioCodecGsmCdma(int n, int n2) {
        Object object = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (object == null) {
            Rlog.e((String)TAG, (String)"Call session is missing");
            return;
        }
        n = this.convertGsmCdmaCodec(n2);
        ((InProgressCallSession)object).addEvent(new CallSessionEventBuilder(22).setAudioCodec(n));
        object = new StringBuilder();
        ((StringBuilder)object).append("Logged Audio Codec event. Value: ");
        ((StringBuilder)object).append(n);
        this.logv(((StringBuilder)object).toString());
    }

    public void writeAudioCodecIms(int n, ImsCallSession object) {
        InProgressCallSession inProgressCallSession = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (inProgressCallSession == null) {
            Rlog.e((String)TAG, (String)"Call session is missing");
            return;
        }
        ImsCallProfile imsCallProfile = object.getLocalCallProfile();
        if (imsCallProfile != null) {
            n = TelephonyMetrics.convertImsCodec(imsCallProfile.mMediaProfile.mAudioQuality);
            inProgressCallSession.addEvent(new CallSessionEventBuilder(22).setCallIndex(this.getCallId((ImsCallSession)object)).setAudioCodec(n));
            object = new StringBuilder();
            ((StringBuilder)object).append("Logged Audio Codec event. Value: ");
            ((StringBuilder)object).append(n);
            this.logv(((StringBuilder)object).toString());
        }
    }

    public void writeCarrierIdMatchingEvent(int n, int n2, int n3, String object, String string, CarrierResolver.CarrierMatchingRule carrierMatchingRule) {
        TelephonyProto.TelephonyEvent.CarrierIdMatching carrierIdMatching = new TelephonyProto.TelephonyEvent.CarrierIdMatching();
        TelephonyProto.TelephonyEvent.CarrierIdMatchingResult carrierIdMatchingResult = new TelephonyProto.TelephonyEvent.CarrierIdMatchingResult();
        if (n3 != -1) {
            carrierIdMatchingResult.carrierId = n3;
            if (string != null) {
                carrierIdMatchingResult.unknownMccmnc = object;
                carrierIdMatchingResult.unknownGid1 = string;
            }
        } else if (object != null) {
            carrierIdMatchingResult.unknownMccmnc = object;
        }
        carrierIdMatchingResult.mccmnc = TextUtils.emptyIfNull((String)carrierMatchingRule.mccMnc);
        carrierIdMatchingResult.spn = TextUtils.emptyIfNull((String)carrierMatchingRule.spn);
        carrierIdMatchingResult.pnn = TextUtils.emptyIfNull((String)carrierMatchingRule.plmn);
        carrierIdMatchingResult.gid1 = TextUtils.emptyIfNull((String)carrierMatchingRule.gid1);
        carrierIdMatchingResult.gid2 = TextUtils.emptyIfNull((String)carrierMatchingRule.gid2);
        carrierIdMatchingResult.imsiPrefix = TextUtils.emptyIfNull((String)carrierMatchingRule.imsiPrefixPattern);
        carrierIdMatchingResult.iccidPrefix = TextUtils.emptyIfNull((String)carrierMatchingRule.iccidPrefix);
        carrierIdMatchingResult.preferApn = TextUtils.emptyIfNull((String)carrierMatchingRule.apn);
        if (carrierMatchingRule.privilegeAccessRule != null) {
            carrierIdMatchingResult.privilegeAccessRule = (String[])carrierMatchingRule.privilegeAccessRule.stream().toArray((IntFunction<A[]>)_$$Lambda$TelephonyMetrics$x2dJi76S2YQdpSTfY8RZ8qC_K6g.INSTANCE);
        }
        carrierIdMatching.cidTableVersion = n2;
        carrierIdMatching.result = carrierIdMatchingResult;
        object = new TelephonyEventBuilder(n).setCarrierIdMatching(carrierIdMatching).build();
        this.mLastCarrierId.put(n, (Object)carrierIdMatching);
        this.addTelephonyEvent((TelephonyProto.TelephonyEvent)object);
    }

    public void writeCarrierKeyEvent(int n, int n2, boolean bl) {
        TelephonyProto.TelephonyEvent.CarrierKeyChange carrierKeyChange = new TelephonyProto.TelephonyEvent.CarrierKeyChange();
        carrierKeyChange.keyType = n2;
        carrierKeyChange.isDownloadSuccessful = bl;
        this.addTelephonyEvent(new TelephonyEventBuilder(n).setCarrierKeyChange(carrierKeyChange).build());
    }

    public void writeDataStallEvent(int n, int n2) {
        this.addTelephonyEvent(new TelephonyEventBuilder(n).setDataStallRecoveryAction(n2).build());
    }

    public void writeDataSwitch(int n, TelephonyProto.TelephonyEvent.DataSwitch dataSwitch) {
        this.addTelephonyEvent(new TelephonyEventBuilder(SubscriptionManager.getPhoneId((int)n)).setDataSwitch(dataSwitch).build());
    }

    public void writeDroppedIncomingMultipartSms(int n, String string, int n2, int n3) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Logged dropped multipart SMS: received ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" out of ");
        ((StringBuilder)object).append(n3);
        this.logv(((StringBuilder)object).toString());
        object = new TelephonyProto.SmsSession.Event.IncompleteSms();
        ((TelephonyProto.SmsSession.Event.IncompleteSms)object).receivedParts = n2;
        ((TelephonyProto.SmsSession.Event.IncompleteSms)object).totalParts = n3;
        InProgressSmsSession inProgressSmsSession = this.startNewSmsSession(n);
        inProgressSmsSession.addEvent(new SmsSessionEventBuilder(10).setFormat(this.convertSmsFormat(string)).setIncompleteSms((TelephonyProto.SmsSession.Event.IncompleteSms)object));
        this.finishSmsSession(inProgressSmsSession);
    }

    public void writeEmergencyNumberUpdateEvent(int n, EmergencyNumber object) {
        if (object == null) {
            return;
        }
        object = this.convertEmergencyNumberToEmergencyNumberInfo((EmergencyNumber)object);
        this.addTelephonyEvent(new TelephonyEventBuilder(n).setUpdatedEmergencyNumber((TelephonyProto.EmergencyNumberInfo)object).build());
    }

    public void writeImsCallState(int n, ImsCallSession imsCallSession, Call.State object) {
        int n2;
        switch (1.$SwitchMap$com$android$internal$telephony$Call$State[((Enum)object).ordinal()]) {
            default: {
                n2 = 0;
                break;
            }
            case 9: {
                n2 = 9;
                break;
            }
            case 8: {
                n2 = 8;
                break;
            }
            case 7: {
                n2 = 7;
                break;
            }
            case 6: {
                n2 = 6;
                break;
            }
            case 5: {
                n2 = 5;
                break;
            }
            case 4: {
                n2 = 4;
                break;
            }
            case 3: {
                n2 = 3;
                break;
            }
            case 2: {
                n2 = 2;
                break;
            }
            case 1: {
                n2 = 1;
            }
        }
        object = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (object == null) {
            Rlog.e((String)TAG, (String)"Call session is missing");
        } else {
            ((InProgressCallSession)object).addEvent(new CallSessionEventBuilder(16).setCallIndex(this.getCallId(imsCallSession)).setCallState(n2));
        }
    }

    public void writeImsServiceSendSms(int n, String string, int n2) {
        synchronized (this) {
            InProgressSmsSession inProgressSmsSession = this.startNewSmsSessionIfNeeded(n);
            SmsSessionEventBuilder smsSessionEventBuilder = new SmsSessionEventBuilder(6);
            inProgressSmsSession.addEvent(smsSessionEventBuilder.setTech(3).setImsServiceErrno(n2).setFormat(this.convertSmsFormat(string)));
            inProgressSmsSession.increaseExpectedResponse();
            return;
        }
    }

    public void writeImsSetFeatureValue(int n, int n2, int n3, int n4) {
        TelephonyProto.TelephonySettings telephonySettings = new TelephonyProto.TelephonySettings();
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        if (n3 == 0) {
            if (n2 != 1) {
                if (n2 == 2) {
                    if (n4 != 0) {
                        bl4 = true;
                    }
                    telephonySettings.isVtOverLteEnabled = bl4;
                }
            } else {
                bl4 = bl;
                if (n4 != 0) {
                    bl4 = true;
                }
                telephonySettings.isEnhanced4GLteModeEnabled = bl4;
            }
        } else if (n3 == 1) {
            if (n2 != 1) {
                if (n2 == 2) {
                    bl4 = bl2;
                    if (n4 != 0) {
                        bl4 = true;
                    }
                    telephonySettings.isVtOverWifiEnabled = bl4;
                }
            } else {
                bl4 = bl3;
                if (n4 != 0) {
                    bl4 = true;
                }
                telephonySettings.isWifiCallingEnabled = bl4;
            }
        }
        if (this.mLastSettings.get(n) != null && Arrays.equals(TelephonyProto.TelephonySettings.toByteArray((MessageNano)this.mLastSettings.get(n)), TelephonyProto.TelephonySettings.toByteArray(telephonySettings))) {
            return;
        }
        this.mLastSettings.put(n, (Object)telephonySettings);
        TelephonyProto.TelephonyEvent telephonyEvent = new TelephonyEventBuilder(n).setSettings(telephonySettings).build();
        this.addTelephonyEvent(telephonyEvent);
        this.annotateInProgressCallSession(telephonyEvent.timestampMillis, n, new CallSessionEventBuilder(1).setSettings(telephonySettings));
        this.annotateInProgressSmsSession(telephonyEvent.timestampMillis, n, new SmsSessionEventBuilder(1).setSettings(telephonySettings));
    }

    public void writeIncomingSMSPP(int n, String string, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Logged SMS-PP session. Result = ");
        stringBuilder.append(bl);
        this.logv(stringBuilder.toString());
        this.writeIncomingSmsWithType(n, 1, string, bl);
    }

    public void writeIncomingSmsError(int n, boolean bl, int n2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Incoming SMS error = ");
        ((StringBuilder)object).append(n2);
        this.logv(((StringBuilder)object).toString());
        int n3 = 1;
        if (n2 != 1) {
            n2 = n2 != 3 ? (n2 != 4 ? 1 : 24) : 13;
            InProgressSmsSession inProgressSmsSession = this.startNewSmsSession(n);
            object = new SmsSessionEventBuilder(8).setErrorCode(n2);
            n = bl ? 3 : n3;
            inProgressSmsSession.addEvent(((SmsSessionEventBuilder)object).setTech(n));
            this.finishSmsSession(inProgressSmsSession);
            return;
        }
    }

    public void writeIncomingSmsSession(int n, boolean bl, String string, long[] arrl, boolean bl2) {
        this.writeIncomingSmsSessionWithType(n, 0, bl, string, arrl, bl2, true);
    }

    public void writeIncomingSmsTypeZero(int n, String string) {
        this.logv("Logged Type-0 SMS message.");
        this.writeIncomingSmsWithType(n, 3, string, true);
    }

    public void writeIncomingVoiceMailSms(int n, String string) {
        this.logv("Logged VoiceMail message.");
        this.writeIncomingSmsWithType(n, 2, string, true);
    }

    public void writeIncomingWapPush(int n, boolean bl, String string, long[] arrl, boolean bl2) {
        this.writeIncomingSmsSessionWithType(n, 4, bl, string, arrl, false, bl2);
    }

    public void writeModemRestartEvent(int n, String string) {
        TelephonyProto.TelephonyEvent.ModemRestart modemRestart = new TelephonyProto.TelephonyEvent.ModemRestart();
        String string2 = Build.getRadioVersion();
        if (string2 != null) {
            modemRestart.basebandVersion = string2;
        }
        if (string != null) {
            modemRestart.reason = string;
        }
        this.addTelephonyEvent(new TelephonyEventBuilder(n).setModemRestart(modemRestart).build());
    }

    public void writeNITZEvent(int n, long l) {
        TelephonyProto.TelephonyEvent telephonyEvent = new TelephonyEventBuilder(n).setNITZ(l).build();
        this.addTelephonyEvent(telephonyEvent);
        this.annotateInProgressCallSession(telephonyEvent.timestampMillis, n, new CallSessionEventBuilder(21).setNITZ(l));
    }

    public void writeNetworkValidate(int n) {
        this.addTelephonyEvent(new TelephonyEventBuilder().setNetworkValidate(n).build());
    }

    public void writeNewCBSms(int n, int n2, int n3, boolean bl, boolean bl2, int n4, int n5, long l) {
        synchronized (this) {
            InProgressSmsSession inProgressSmsSession = this.startNewSmsSessionIfNeeded(n);
            n = bl ? 2 : (bl2 ? 1 : 3);
            TelephonyProto.SmsSession.Event.CBMessage cBMessage = new TelephonyProto.SmsSession.Event.CBMessage();
            cBMessage.msgFormat = n2;
            cBMessage.msgPriority = n3 + 1;
            cBMessage.msgType = n;
            cBMessage.serviceCategory = n4;
            cBMessage.serialNumber = n5;
            cBMessage.deliveredTimestampMillis = l;
            SmsSessionEventBuilder smsSessionEventBuilder = new SmsSessionEventBuilder(9);
            inProgressSmsSession.addEvent(smsSessionEventBuilder.setCellBroadcastMessage(cBMessage));
            this.finishSmsSessionIfNeeded(inProgressSmsSession);
            return;
        }
    }

    public void writeOnDemandDataSwitch(TelephonyProto.TelephonyEvent.OnDemandDataSwitch onDemandDataSwitch) {
        this.addTelephonyEvent(new TelephonyEventBuilder().setOnDemandDataSwitch(onDemandDataSwitch).build());
    }

    public void writeOnImsCallHandoverEvent(int n, int n2, ImsCallSession imsCallSession, int n3, int n4, ImsReasonInfo imsReasonInfo) {
        InProgressCallSession inProgressCallSession = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (inProgressCallSession == null) {
            Rlog.e((String)TAG, (String)"Call session is missing");
        } else {
            inProgressCallSession.addEvent(new CallSessionEventBuilder(n2).setCallIndex(this.getCallId(imsCallSession)).setSrcAccessTech(n3).setTargetAccessTech(n4).setImsReasonInfo(this.toImsReasonInfoProto(imsReasonInfo)));
        }
    }

    public void writeOnImsCallHeld(int n, ImsCallSession imsCallSession) {
    }

    public void writeOnImsCallHoldFailed(int n, ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
    }

    public void writeOnImsCallHoldReceived(int n, ImsCallSession imsCallSession) {
    }

    public void writeOnImsCallProgressing(int n, ImsCallSession imsCallSession) {
    }

    public void writeOnImsCallReceive(int n, ImsCallSession imsCallSession) {
        this.startNewCallSessionIfNeeded(n).addEvent(new CallSessionEventBuilder(15).setCallIndex(this.getCallId(imsCallSession)));
    }

    public void writeOnImsCallResumeFailed(int n, ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
    }

    public void writeOnImsCallResumeReceived(int n, ImsCallSession imsCallSession) {
    }

    public void writeOnImsCallResumed(int n, ImsCallSession imsCallSession) {
    }

    public void writeOnImsCallStart(int n, ImsCallSession imsCallSession) {
        this.startNewCallSessionIfNeeded(n).addEvent(new CallSessionEventBuilder(11).setCallIndex(this.getCallId(imsCallSession)).setImsCommand(1));
    }

    public void writeOnImsCallStartFailed(int n, ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
    }

    public void writeOnImsCallStarted(int n, ImsCallSession imsCallSession) {
    }

    public void writeOnImsCallTerminated(int n, ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo, CallQualityMetrics callQualityMetrics, EmergencyNumber emergencyNumber, String string) {
        InProgressCallSession inProgressCallSession = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (inProgressCallSession == null) {
            Rlog.e((String)TAG, (String)"Call session is missing");
        } else {
            CallSessionEventBuilder callSessionEventBuilder = new CallSessionEventBuilder(17);
            callSessionEventBuilder.setCallIndex(this.getCallId(imsCallSession));
            callSessionEventBuilder.setImsReasonInfo(this.toImsReasonInfoProto(imsReasonInfo));
            if (callQualityMetrics != null) {
                callSessionEventBuilder.setCallQualitySummaryDl(callQualityMetrics.getCallQualitySummaryDl()).setCallQualitySummaryUl(callQualityMetrics.getCallQualitySummaryUl());
            }
            if (emergencyNumber != null && ThreadLocalRandom.current().nextDouble(0.0, 100.0) < this.getSamplePercentageForEmergencyCall(string)) {
                callSessionEventBuilder.setIsImsEmergencyCall(true);
                callSessionEventBuilder.setImsEmergencyNumberInfo(this.convertEmergencyNumberToEmergencyNumberInfo(emergencyNumber));
            }
            inProgressCallSession.addEvent(callSessionEventBuilder);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeOnImsCapabilities(int n, int n2, MmTelFeature.MmTelCapabilities object) {
        synchronized (this) {
            boolean bl;
            Object object2 = new TelephonyProto.ImsCapabilities();
            if (n2 == 0) {
                ((TelephonyProto.ImsCapabilities)object2).voiceOverLte = object.isCapable(1);
                ((TelephonyProto.ImsCapabilities)object2).videoOverLte = object.isCapable(2);
                ((TelephonyProto.ImsCapabilities)object2).utOverLte = object.isCapable(4);
            } else if (n2 == 1) {
                ((TelephonyProto.ImsCapabilities)object2).voiceOverWifi = object.isCapable(1);
                ((TelephonyProto.ImsCapabilities)object2).videoOverWifi = object.isCapable(2);
                ((TelephonyProto.ImsCapabilities)object2).utOverWifi = object.isCapable(4);
            }
            object = new TelephonyEventBuilder(n);
            object = ((TelephonyEventBuilder)object).setImsCapabilities((TelephonyProto.ImsCapabilities)object2).build();
            if (this.mLastImsCapabilities.get(n) != null && (bl = Arrays.equals(TelephonyProto.ImsCapabilities.toByteArray((MessageNano)this.mLastImsCapabilities.get(n)), TelephonyProto.ImsCapabilities.toByteArray((MessageNano)object2)))) {
                return;
            }
            this.mLastImsCapabilities.put(n, object2);
            this.addTelephonyEvent((TelephonyProto.TelephonyEvent)object);
            long l = ((TelephonyProto.TelephonyEvent)object).timestampMillis;
            object2 = new CallSessionEventBuilder(4);
            this.annotateInProgressCallSession(l, n, ((CallSessionEventBuilder)object2).setImsCapabilities(((TelephonyProto.TelephonyEvent)object).imsCapabilities));
            l = ((TelephonyProto.TelephonyEvent)object).timestampMillis;
            object2 = new SmsSessionEventBuilder(4);
            this.annotateInProgressSmsSession(l, n, ((SmsSessionEventBuilder)object2).setImsCapabilities(((TelephonyProto.TelephonyEvent)object).imsCapabilities));
            return;
        }
    }

    public void writeOnImsCommand(int n, ImsCallSession imsCallSession, int n2) {
        InProgressCallSession inProgressCallSession = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (inProgressCallSession == null) {
            Rlog.e((String)TAG, (String)"Call session is missing");
        } else {
            inProgressCallSession.addEvent(new CallSessionEventBuilder(11).setCallIndex(this.getCallId(imsCallSession)).setImsCommand(n2));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeOnImsConnectionState(int n, int n2, ImsReasonInfo object) {
        synchronized (this) {
            boolean bl;
            Object object2 = new TelephonyProto.ImsConnectionState();
            ((TelephonyProto.ImsConnectionState)object2).state = n2;
            if (object != null) {
                TelephonyProto.ImsReasonInfo imsReasonInfo = new TelephonyProto.ImsReasonInfo();
                imsReasonInfo.reasonCode = object.getCode();
                imsReasonInfo.extraCode = object.getExtraCode();
                if ((object = object.getExtraMessage()) != null) {
                    imsReasonInfo.extraMessage = object;
                }
                ((TelephonyProto.ImsConnectionState)object2).reasonInfo = imsReasonInfo;
            }
            if (this.mLastImsConnectionState.get(n) != null && (bl = Arrays.equals(TelephonyProto.ImsConnectionState.toByteArray((MessageNano)this.mLastImsConnectionState.get(n)), TelephonyProto.ImsConnectionState.toByteArray((MessageNano)object2)))) {
                return;
            }
            this.mLastImsConnectionState.put(n, object2);
            object = new TelephonyEventBuilder(n);
            object = ((TelephonyEventBuilder)object).setImsConnectionState((TelephonyProto.ImsConnectionState)object2).build();
            this.addTelephonyEvent((TelephonyProto.TelephonyEvent)object);
            long l = ((TelephonyProto.TelephonyEvent)object).timestampMillis;
            object2 = new CallSessionEventBuilder(3);
            this.annotateInProgressCallSession(l, n, ((CallSessionEventBuilder)object2).setImsConnectionState(((TelephonyProto.TelephonyEvent)object).imsConnectionState));
            l = ((TelephonyProto.TelephonyEvent)object).timestampMillis;
            object2 = new SmsSessionEventBuilder(3);
            this.annotateInProgressSmsSession(l, n, ((SmsSessionEventBuilder)object2).setImsConnectionState(((TelephonyProto.TelephonyEvent)object).imsConnectionState));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeOnImsServiceSmsSolicitedResponse(int n, int n2, int n3) {
        synchronized (this) {
            InProgressSmsSession inProgressSmsSession = (InProgressSmsSession)this.mInProgressSmsSessions.get(n);
            if (inProgressSmsSession == null) {
                Rlog.e((String)TAG, (String)"SMS session is missing");
            } else {
                SmsSessionEventBuilder smsSessionEventBuilder = new SmsSessionEventBuilder(7);
                inProgressSmsSession.addEvent(smsSessionEventBuilder.setImsServiceErrno(n2).setErrorCode(n3));
                inProgressSmsSession.decreaseExpectedResponse();
                this.finishSmsSessionIfNeeded(inProgressSmsSession);
            }
            return;
        }
    }

    /*
     * Exception decompiling
     */
    public void writeOnRilSolicitedResponse(int var1_1, int var2_2, int var3_3, int var4_4, Object var5_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    public void writeOnRilTimeoutResponse(int n, int n2, int n3) {
    }

    public void writePhoneState(int n, PhoneConstants.State object) {
        int n2 = 1.$SwitchMap$com$android$internal$telephony$PhoneConstants$State[object.ordinal()];
        n2 = n2 != 1 ? (n2 != 2 ? (n2 != 3 ? 0 : 3) : 2) : 1;
        object = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (object == null) {
            Rlog.e((String)TAG, (String)"writePhoneState: Call session is missing");
        } else {
            ((InProgressCallSession)object).setLastKnownPhoneState(n2);
            if (n2 == 1 && !((InProgressCallSession)object).containsCsCalls()) {
                this.finishCallSession((InProgressCallSession)object);
            }
            ((InProgressCallSession)object).addEvent(new CallSessionEventBuilder(20).setPhoneState(n2));
        }
    }

    public void writeRilAnswer(int n, int n2) {
        InProgressCallSession inProgressCallSession = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (inProgressCallSession == null) {
            Rlog.e((String)TAG, (String)"writeRilAnswer: Call session is missing");
        } else {
            inProgressCallSession.addEvent(new CallSessionEventBuilder(6).setRilRequest(2).setRilRequestId(n2));
        }
    }

    public void writeRilCallList(int n, ArrayList<GsmCdmaConnection> arrrilCall, String string) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Logging CallList Changed Connections Size = ");
        ((StringBuilder)object).append(arrrilCall.size());
        this.logv(((StringBuilder)object).toString());
        object = this.startNewCallSessionIfNeeded(n);
        if (object == null) {
            Rlog.e((String)TAG, (String)"writeRilCallList: Call session is missing");
        } else {
            arrrilCall = this.convertConnectionsToRilCalls((ArrayList<GsmCdmaConnection>)arrrilCall, string);
            ((InProgressCallSession)object).addEvent(new CallSessionEventBuilder(10).setRilCalls(arrrilCall));
            this.logv("Logged Call list changed");
            if (((InProgressCallSession)object).isPhoneIdle() && this.disconnectReasonsKnown(arrrilCall)) {
                this.finishCallSession((InProgressCallSession)object);
            }
        }
    }

    public void writeRilCallRing(int n, char[] object) {
        object = this.startNewCallSessionIfNeeded(n);
        ((InProgressCallSession)object).addEvent(((InProgressCallSession)object).startElapsedTimeMs, new CallSessionEventBuilder(8));
    }

    public void writeRilDataCallEvent(int n, int n2, int n3, int n4) {
        SparseArray sparseArray;
        TelephonyProto.RilDataCall[] arrrilDataCall = new TelephonyProto.RilDataCall[]{new TelephonyProto.RilDataCall()};
        arrrilDataCall[0].cid = n2;
        arrrilDataCall[0].apnTypeBitmask = n3;
        arrrilDataCall[0].state = n4;
        if (this.mLastRilDataCallEvents.get(n) != null) {
            if (((SparseArray)this.mLastRilDataCallEvents.get(n)).get(n2) != null && Arrays.equals(TelephonyProto.RilDataCall.toByteArray((MessageNano)((SparseArray)this.mLastRilDataCallEvents.get(n)).get(n2)), TelephonyProto.RilDataCall.toByteArray(arrrilDataCall[0]))) {
                return;
            }
            sparseArray = (SparseArray)this.mLastRilDataCallEvents.get(n);
        } else {
            sparseArray = new SparseArray();
        }
        sparseArray.put(n2, (Object)arrrilDataCall[0]);
        this.mLastRilDataCallEvents.put(n, (Object)sparseArray);
        this.addTelephonyEvent(new TelephonyEventBuilder(n).setDataCalls(arrrilDataCall).build());
    }

    public void writeRilDeactivateDataCall(int n, int n2, int n3, int n4) {
        TelephonyProto.TelephonyEvent.RilDeactivateDataCall rilDeactivateDataCall = new TelephonyProto.TelephonyEvent.RilDeactivateDataCall();
        rilDeactivateDataCall.cid = n3;
        rilDeactivateDataCall.reason = n4 != 1 ? (n4 != 2 ? (n4 != 3 ? 0 : 4) : 2) : 1;
        this.addTelephonyEvent(new TelephonyEventBuilder(n).setDeactivateDataCall(rilDeactivateDataCall).build());
    }

    public void writeRilDial(int n, GsmCdmaConnection gsmCdmaConnection, int n2, UUSInfo object) {
        object = this.startNewCallSessionIfNeeded(n);
        TelephonyProto.TelephonyCallSession.Event.RilCall[] arrrilCall = new StringBuilder();
        arrrilCall.append("Logging Dial Connection = ");
        arrrilCall.append(gsmCdmaConnection);
        this.logv(arrrilCall.toString());
        if (object == null) {
            Rlog.e((String)TAG, (String)"writeRilDial: Call session is missing");
        } else {
            arrrilCall = new TelephonyProto.TelephonyCallSession.Event.RilCall[]{new TelephonyProto.TelephonyCallSession.Event.RilCall()};
            arrrilCall[0].index = -1;
            this.convertConnectionToRilCall(gsmCdmaConnection, arrrilCall[0], "");
            ((InProgressCallSession)object).addEvent(((InProgressCallSession)object).startElapsedTimeMs, new CallSessionEventBuilder(6).setRilRequest(1).setRilCalls(arrrilCall));
            this.logv("Logged Dial event");
        }
    }

    public void writeRilHangup(int n, GsmCdmaConnection gsmCdmaConnection, int n2, String string) {
        InProgressCallSession inProgressCallSession = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (inProgressCallSession == null) {
            Rlog.e((String)TAG, (String)"writeRilHangup: Call session is missing");
        } else {
            TelephonyProto.TelephonyCallSession.Event.RilCall[] arrrilCall = new TelephonyProto.TelephonyCallSession.Event.RilCall[]{new TelephonyProto.TelephonyCallSession.Event.RilCall()};
            arrrilCall[0].index = n2;
            this.convertConnectionToRilCall(gsmCdmaConnection, arrrilCall[0], string);
            inProgressCallSession.addEvent(new CallSessionEventBuilder(6).setRilRequest(3).setRilCalls(arrrilCall));
            this.logv("Logged Hangup event");
        }
    }

    public void writeRilSendSms(int n, int n2, int n3, int n4) {
        synchronized (this) {
            InProgressSmsSession inProgressSmsSession = this.startNewSmsSessionIfNeeded(n);
            SmsSessionEventBuilder smsSessionEventBuilder = new SmsSessionEventBuilder(6);
            inProgressSmsSession.addEvent(smsSessionEventBuilder.setTech(n3).setRilRequestId(n2).setFormat(n4));
            inProgressSmsSession.increaseExpectedResponse();
            return;
        }
    }

    public void writeRilSrvcc(int n, int n2) {
        InProgressCallSession inProgressCallSession = (InProgressCallSession)this.mInProgressCallSessions.get(n);
        if (inProgressCallSession == null) {
            Rlog.e((String)TAG, (String)"writeRilSrvcc: Call session is missing");
        } else {
            inProgressCallSession.addEvent(new CallSessionEventBuilder(9).setSrvccState(n2 + 1));
        }
    }

    public void writeServiceStateChanged(int n, ServiceState object) {
        synchronized (this) {
            Object object2;
            block4 : {
                boolean bl;
                object2 = new TelephonyEventBuilder(n);
                object = ((TelephonyEventBuilder)object2).setServiceState(this.toServiceStateProto((ServiceState)object)).build();
                if (this.mLastServiceState.get(n) == null || !(bl = Arrays.equals(TelephonyProto.TelephonyServiceState.toByteArray((MessageNano)this.mLastServiceState.get(n)), TelephonyProto.TelephonyServiceState.toByteArray(object.serviceState)))) break block4;
                return;
            }
            this.mLastServiceState.put(n, (Object)object.serviceState);
            this.addTelephonyEvent((TelephonyProto.TelephonyEvent)object);
            long l = object.timestampMillis;
            object2 = new CallSessionEventBuilder(2);
            this.annotateInProgressCallSession(l, n, ((CallSessionEventBuilder)object2).setServiceState(object.serviceState));
            l = object.timestampMillis;
            object2 = new SmsSessionEventBuilder(2);
            this.annotateInProgressSmsSession(l, n, ((SmsSessionEventBuilder)object2).setServiceState(object.serviceState));
            return;
        }
    }

    public void writeSetPreferredNetworkType(int n, int n2) {
        TelephonyProto.TelephonySettings telephonySettings = new TelephonyProto.TelephonySettings();
        telephonySettings.preferredNetworkMode = n2 + 1;
        if (this.mLastSettings.get(n) != null && Arrays.equals(TelephonyProto.TelephonySettings.toByteArray((MessageNano)this.mLastSettings.get(n)), TelephonyProto.TelephonySettings.toByteArray(telephonySettings))) {
            return;
        }
        this.mLastSettings.put(n, (Object)telephonySettings);
        this.addTelephonyEvent(new TelephonyEventBuilder(n).setSettings(telephonySettings).build());
    }

    public void writeSetupDataCall(int n, int n2, int n3, String string, int n4) {
        TelephonyProto.TelephonyEvent.RilSetupDataCall rilSetupDataCall = new TelephonyProto.TelephonyEvent.RilSetupDataCall();
        rilSetupDataCall.rat = n2;
        rilSetupDataCall.dataProfile = n3 + 1;
        if (string != null) {
            rilSetupDataCall.apn = string;
        }
        rilSetupDataCall.type = n4 + 1;
        this.addTelephonyEvent(new TelephonyEventBuilder(n).setSetupDataCall(rilSetupDataCall).build());
    }

}

