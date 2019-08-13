/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.telephony.Rlog
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  android.util.TimestampedValue
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.content.Context;
import android.os.PowerManager;
import android.telephony.Rlog;
import android.text.TextUtils;
import android.util.LocalLog;
import android.util.TimestampedValue;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.NitzData;
import com.android.internal.telephony.NitzStateMachine;
import com.android.internal.telephony.OldTimeServiceHelper;
import com.android.internal.telephony.TimeZoneLookupHelper;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.TimeZone;

public final class OldNitzStateMachine
implements NitzStateMachine {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "SST";
    private static final String WAKELOCK_TAG = "NitzStateMachine";
    private final NitzStateMachine.DeviceState mDeviceState;
    private boolean mGotCountryCode = false;
    private TimestampedValue<NitzData> mLatestNitzSignal;
    private boolean mNitzTimeZoneDetectionSuccessful = false;
    private final GsmCdmaPhone mPhone;
    private TimestampedValue<Long> mSavedNitzTime;
    private String mSavedTimeZoneId;
    private final LocalLog mTimeLog = new LocalLog(15);
    private final OldTimeServiceHelper mTimeServiceHelper;
    private final LocalLog mTimeZoneLog = new LocalLog(15);
    private final TimeZoneLookupHelper mTimeZoneLookupHelper;
    private final PowerManager.WakeLock mWakeLock;

    public OldNitzStateMachine(GsmCdmaPhone gsmCdmaPhone) {
        this(gsmCdmaPhone, new OldTimeServiceHelper(gsmCdmaPhone.getContext()), new NitzStateMachine.DeviceState(gsmCdmaPhone), new TimeZoneLookupHelper());
    }

    @VisibleForTesting
    public OldNitzStateMachine(GsmCdmaPhone gsmCdmaPhone, OldTimeServiceHelper oldTimeServiceHelper, NitzStateMachine.DeviceState deviceState, TimeZoneLookupHelper timeZoneLookupHelper) {
        this.mPhone = gsmCdmaPhone;
        gsmCdmaPhone = gsmCdmaPhone.getContext();
        this.mWakeLock = ((PowerManager)gsmCdmaPhone.getSystemService("power")).newWakeLock(1, WAKELOCK_TAG);
        this.mDeviceState = deviceState;
        this.mTimeZoneLookupHelper = timeZoneLookupHelper;
        this.mTimeServiceHelper = oldTimeServiceHelper;
        this.mTimeServiceHelper.setListener(new OldTimeServiceHelper.Listener(){

            @Override
            public void onTimeDetectionChange(boolean bl) {
                if (bl) {
                    OldNitzStateMachine.this.handleAutoTimeEnabled();
                }
            }

            @Override
            public void onTimeZoneDetectionChange(boolean bl) {
                if (bl) {
                    OldNitzStateMachine.this.handleAutoTimeZoneEnabled();
                }
            }
        });
    }

    private boolean countryUsesUtc(String string, TimestampedValue<NitzData> timestampedValue) {
        return this.mTimeZoneLookupHelper.countryUsesUtc(string, ((NitzData)timestampedValue.getValue()).getCurrentTimeInMillis());
    }

    private void handleAutoTimeEnabled() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleAutoTimeEnabled: Reverting to NITZ Time: mSavedNitzTime=");
        stringBuilder.append(this.mSavedNitzTime);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        if (this.mSavedNitzTime != null) {
            try {
                this.mWakeLock.acquire();
                long l = this.mTimeServiceHelper.elapsedRealtime();
                stringBuilder = new StringBuilder();
                stringBuilder.append("mSavedNitzTime: Reverting to NITZ time elapsedRealtime=");
                stringBuilder.append(l);
                stringBuilder.append(" mSavedNitzTime=");
                stringBuilder.append(this.mSavedNitzTime);
                this.setAndBroadcastNetworkSetTime(stringBuilder.toString(), (Long)this.mSavedNitzTime.getValue() + (l - this.mSavedNitzTime.getReferenceTimeMillis()));
            }
            finally {
                this.mWakeLock.release();
            }
        }
    }

    private void handleAutoTimeZoneEnabled() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("handleAutoTimeZoneEnabled: Reverting to NITZ TimeZone: mSavedTimeZoneId=");
        charSequence.append(this.mSavedTimeZoneId);
        charSequence = charSequence.toString();
        Rlog.d((String)LOG_TAG, (String)charSequence);
        this.mTimeZoneLog.log((String)charSequence);
        charSequence = this.mSavedTimeZoneId;
        if (charSequence != null) {
            this.setAndBroadcastNetworkSetTimeZone((String)charSequence);
        }
    }

    private boolean isNitzSignalOffsetInfoBogus(TimestampedValue<NitzData> timestampedValue, String string) {
        boolean bl = TextUtils.isEmpty((CharSequence)string);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        NitzData nitzData = (NitzData)timestampedValue.getValue();
        boolean bl3 = nitzData.getLocalOffsetMillis() == 0 && !nitzData.isDst();
        bl = bl2;
        if (bl3) {
            bl = bl2;
            if (!this.countryUsesUtc(string, timestampedValue)) {
                bl = true;
            }
        }
        return bl;
    }

    private void setAndBroadcastNetworkSetTime(String string, long l) {
        StringBuilder stringBuilder;
        if (!this.mWakeLock.isHeld()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("setAndBroadcastNetworkSetTime: Wake lock not held while setting device time (msg=");
            stringBuilder.append(string);
            stringBuilder.append(")");
            Rlog.w((String)LOG_TAG, (String)stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("setAndBroadcastNetworkSetTime: [Setting time to time=");
        stringBuilder.append(l);
        stringBuilder.append("]:");
        stringBuilder.append(string);
        string = stringBuilder.toString();
        Rlog.d((String)LOG_TAG, (String)string);
        this.mTimeLog.log(string);
        this.mTimeServiceHelper.setDeviceTime(l);
        TelephonyMetrics.getInstance().writeNITZEvent(this.mPhone.getPhoneId(), l);
    }

    private void setAndBroadcastNetworkSetTimeZone(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setAndBroadcastNetworkSetTimeZone: zoneId=");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        this.mTimeServiceHelper.setDeviceTimeZone(string);
        stringBuilder = new StringBuilder();
        stringBuilder.append("setAndBroadcastNetworkSetTimeZone: called setDeviceTimeZone() zoneId=");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void updateTimeFromNitz() {
        block17 : {
            block19 : {
                block18 : {
                    block16 : {
                        var1_1 = this.mLatestNitzSignal;
                        if (!this.mDeviceState.getIgnoreNitz()) break block16;
                        Rlog.d((String)"SST", (String)"updateTimeFromNitz: Not setting clock because gsm.ignore-nitz is set");
                        return;
                    }
                    this.mWakeLock.acquire();
                    var2_2 = this.mTimeServiceHelper.elapsedRealtime();
                    var4_3 = var2_2 - var1_1.getReferenceTimeMillis();
                    if (var4_3 < 0L || var4_3 > Integer.MAX_VALUE) break block17;
                    var2_2 = ((NitzData)var1_1.getValue()).getCurrentTimeInMillis() + var4_3;
                    var6_4 = var2_2 - this.mTimeServiceHelper.currentTimeMillis();
                    if (!this.mTimeServiceHelper.isTimeDetectionEnabled()) ** GOTO lbl76
                    var8_5 = new StringBuilder();
                    var8_5.append("updateTimeFromNitz: nitzSignal=");
                    var8_5.append(var1_1);
                    var8_5.append(" adjustedCurrentTimeMillis=");
                    var8_5.append(var2_2);
                    var8_5.append(" millisSinceNitzReceived= ");
                    var8_5.append(var4_3);
                    var8_5.append(" gained=");
                    var8_5.append(var6_4);
                    var8_6 = var8_5.toString();
                    var9_15 = this.mSavedNitzTime;
                    if (var9_15 != null) break block18;
                    try {
                        var9_15 = new StringBuilder();
                        var9_15.append(var8_6);
                        var9_15.append(": First update received.");
                        this.setAndBroadcastNetworkSetTime(var9_15.toString(), var2_2);
                        ** GOTO lbl76
                    }
                    catch (Throwable var8_7) {
                        ** GOTO lbl98
                    }
                }
                var10_16 = this.mTimeServiceHelper.elapsedRealtime();
                var4_3 = this.mSavedNitzTime.getReferenceTimeMillis();
                var12_17 = this.mDeviceState.getNitzUpdateSpacingMillis();
                var13_18 = this.mDeviceState.getNitzUpdateDiffMillis();
                {
                    catch (Throwable var8_10) {
                        break block20;
                    }
                }
                if (var10_16 - var4_3 <= (long)var12_17) {
                    if (Math.abs(var6_4) > (long)var13_18) break block19;
                    var9_15 = new StringBuilder();
                    var9_15.append(var8_6);
                    var9_15.append(": Update throttled.");
                    Rlog.d((String)"SST", (String)var9_15.toString());
                    this.mWakeLock.release();
                    return;
                }
            }
            var9_15 = new StringBuilder();
            var9_15.append(var8_6);
            var9_15.append(": New update received.");
            this.setAndBroadcastNetworkSetTime(var9_15.toString(), var2_2);
lbl76: // 3 sources:
            this.mSavedNitzTime = var8_8 = new TimestampedValue(var1_1.getReferenceTimeMillis(), (Object)((NitzData)var1_1.getValue()).getCurrentTimeInMillis());
            this.mWakeLock.release();
            return;
        }
        var8_9 = new StringBuilder();
        var8_9.append("updateTimeFromNitz: not setting time, unexpected elapsedRealtime=");
        var8_9.append(var2_2);
        var8_9.append(" nitzSignal=");
        var8_9.append(var1_1);
        Rlog.d((String)"SST", (String)var8_9.toString());
        try {
            block20 : {
                this.mWakeLock.release();
                return;
                catch (Throwable var8_11) {
                    // empty catch block
                }
            }
            this.mWakeLock.release();
            throw var8_12;
        }
        catch (RuntimeException var8_13) {
            var9_15 = new TimestampedValue<Long>();
            var9_15.append("updateTimeFromNitz: Processing NITZ data nitzSignal=");
            var9_15.append(var1_1);
            var9_15.append(" ex=");
            var9_15.append(var8_13);
            Rlog.e((String)"SST", (String)var9_15.toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateTimeZoneFromCountryAndNitz() {
        String string = this.mDeviceState.getNetworkCountryIsoForPhone();
        TimestampedValue<NitzData> timestampedValue = this.mLatestNitzSignal;
        boolean bl = this.mTimeServiceHelper.isTimeZoneSettingInitialized();
        Object object = new StringBuilder();
        ((StringBuilder)object).append("updateTimeZoneFromCountryAndNitz: isTimeZoneSettingInitialized=");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(" nitzSignal=");
        ((StringBuilder)object).append(timestampedValue);
        ((StringBuilder)object).append(" isoCountryCode=");
        ((StringBuilder)object).append(string);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        try {
            Object object2;
            NitzData nitzData = (NitzData)timestampedValue.getValue();
            if (nitzData.getEmulatorHostTimeZone() != null) {
                object = nitzData.getEmulatorHostTimeZone().getID();
            } else if (!this.mGotCountryCode) {
                object = null;
            } else {
                boolean bl2 = TextUtils.isEmpty((CharSequence)string);
                object2 = null;
                object = null;
                if (bl2) {
                    object2 = this.mTimeZoneLookupHelper.lookupByNitz(nitzData);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("updateTimeZoneFromCountryAndNitz: lookupByNitz returned lookupResult=");
                    stringBuilder.append(object2);
                    String string2 = stringBuilder.toString();
                    Rlog.d((String)LOG_TAG, (String)string2);
                    this.mTimeZoneLog.log(string2);
                    if (object2 != null) {
                        object = ((TimeZoneLookupHelper.OffsetResult)object2).zoneId;
                    }
                } else if (this.mLatestNitzSignal == null) {
                    Rlog.d((String)LOG_TAG, (String)"updateTimeZoneFromCountryAndNitz: No cached NITZ data available, not setting zone");
                    object = null;
                } else if (this.isNitzSignalOffsetInfoBogus(timestampedValue, string)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("updateTimeZoneFromCountryAndNitz: Received NITZ looks bogus,  isoCountryCode=");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(" nitzSignal=");
                    ((StringBuilder)object).append(timestampedValue);
                    object = ((StringBuilder)object).toString();
                    Rlog.d((String)LOG_TAG, (String)object);
                    this.mTimeZoneLog.log((String)object);
                    object = null;
                } else {
                    TimeZoneLookupHelper.OffsetResult offsetResult = this.mTimeZoneLookupHelper.lookupByNitzCountry(nitzData, string);
                    object = new StringBuilder();
                    ((StringBuilder)object).append("updateTimeZoneFromCountryAndNitz: using lookupByNitzCountry(nitzData, isoCountryCode), nitzData=");
                    ((StringBuilder)object).append(nitzData);
                    ((StringBuilder)object).append(" isoCountryCode=");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(" lookupResult=");
                    ((StringBuilder)object).append(offsetResult);
                    Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
                    object = object2;
                    if (offsetResult != null) {
                        object = offsetResult.zoneId;
                    }
                }
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("updateTimeZoneFromCountryAndNitz: isTimeZoneSettingInitialized=");
            ((StringBuilder)object2).append(bl);
            ((StringBuilder)object2).append(" isoCountryCode=");
            ((StringBuilder)object2).append(string);
            ((StringBuilder)object2).append(" nitzSignal=");
            ((StringBuilder)object2).append(timestampedValue);
            ((StringBuilder)object2).append(" zoneId=");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(" isTimeZoneDetectionEnabled()=");
            ((StringBuilder)object2).append(this.mTimeServiceHelper.isTimeZoneDetectionEnabled());
            object2 = ((StringBuilder)object2).toString();
            this.mTimeZoneLog.log((String)object2);
            if (object == null) {
                Rlog.d((String)LOG_TAG, (String)"updateTimeZoneFromCountryAndNitz: zoneId == null, do nothing");
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("updateTimeZoneFromCountryAndNitz: zoneId=");
            ((StringBuilder)object2).append((String)object);
            Rlog.d((String)LOG_TAG, (String)((StringBuilder)object2).toString());
            if (this.mTimeServiceHelper.isTimeZoneDetectionEnabled()) {
                this.setAndBroadcastNetworkSetTimeZone((String)object);
            } else {
                Rlog.d((String)LOG_TAG, (String)"updateTimeZoneFromCountryAndNitz: skip changing zone as isTimeZoneDetectionEnabled() is false");
            }
            this.mSavedTimeZoneId = object;
            this.mNitzTimeZoneDetectionSuccessful = true;
            return;
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateTimeZoneFromCountryAndNitz: Processing NITZ data nitzSignal=");
            stringBuilder.append(timestampedValue);
            stringBuilder.append(" isoCountryCode=");
            stringBuilder.append(string);
            stringBuilder.append(" isTimeZoneSettingInitialized=");
            stringBuilder.append(bl);
            stringBuilder.append(" ex=");
            stringBuilder.append(runtimeException);
            Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
        }
    }

    private void updateTimeZoneFromNetworkCountryCode(String string) {
        TimeZoneLookupHelper.CountryResult countryResult = this.mTimeZoneLookupHelper.lookupByCountry(string, this.mTimeServiceHelper.currentTimeMillis());
        if (countryResult != null && countryResult.allZonesHaveSameOffset) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateTimeZoneFromNetworkCountryCode: tz result found iso=");
            stringBuilder.append(string);
            stringBuilder.append(" lookupResult=");
            stringBuilder.append(countryResult);
            string = stringBuilder.toString();
            Rlog.d((String)LOG_TAG, (String)string);
            this.mTimeZoneLog.log(string);
            string = countryResult.zoneId;
            if (this.mTimeServiceHelper.isTimeZoneDetectionEnabled()) {
                this.setAndBroadcastNetworkSetTimeZone(string);
            }
            this.mSavedTimeZoneId = string;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateTimeZoneFromNetworkCountryCode: no good zone for iso=");
            stringBuilder.append(string);
            stringBuilder.append(" lookupResult=");
            stringBuilder.append(countryResult);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        }
    }

    @Override
    public void dumpLogs(FileDescriptor fileDescriptor, IndentingPrintWriter indentingPrintWriter, String[] arrstring) {
        indentingPrintWriter.println(" Time Logs:");
        indentingPrintWriter.increaseIndent();
        this.mTimeLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println(" Time zone Logs:");
        indentingPrintWriter.increaseIndent();
        this.mTimeZoneLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
    }

    @Override
    public void dumpState(PrintWriter printWriter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" mSavedTime=");
        stringBuilder.append(this.mSavedNitzTime);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mLatestNitzSignal=");
        stringBuilder.append(this.mLatestNitzSignal);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mGotCountryCode=");
        stringBuilder.append(this.mGotCountryCode);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mSavedTimeZoneId=");
        stringBuilder.append(this.mSavedTimeZoneId);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mNitzTimeZoneDetectionSuccessful=");
        stringBuilder.append(this.mNitzTimeZoneDetectionSuccessful);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" mWakeLock=");
        stringBuilder.append((Object)this.mWakeLock);
        printWriter.println(stringBuilder.toString());
        printWriter.flush();
    }

    @Override
    public NitzData getCachedNitzData() {
        Object object = this.mLatestNitzSignal;
        object = object != null ? (NitzData)object.getValue() : null;
        return object;
    }

    public boolean getNitzTimeZoneDetectionSuccessful() {
        return this.mNitzTimeZoneDetectionSuccessful;
    }

    @Override
    public String getSavedTimeZoneId() {
        return this.mSavedTimeZoneId;
    }

    @Override
    public void handleNetworkAvailable() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleNetworkAvailable: mNitzTimeZoneDetectionSuccessful=");
        stringBuilder.append(this.mNitzTimeZoneDetectionSuccessful);
        stringBuilder.append(", Setting mNitzTimeZoneDetectionSuccessful=false");
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        this.mNitzTimeZoneDetectionSuccessful = false;
    }

    @Override
    public void handleNetworkCountryCodeSet(boolean bl) {
        boolean bl2 = this.mGotCountryCode;
        this.mGotCountryCode = true;
        String string = this.mDeviceState.getNetworkCountryIsoForPhone();
        if (!TextUtils.isEmpty((CharSequence)string) && !this.mNitzTimeZoneDetectionSuccessful) {
            this.updateTimeZoneFromNetworkCountryCode(string);
        }
        if (this.mLatestNitzSignal != null && (bl || !bl2)) {
            this.updateTimeZoneFromCountryAndNitz();
        }
    }

    @Override
    public void handleNetworkCountryCodeUnavailable() {
        Rlog.d((String)LOG_TAG, (String)"handleNetworkCountryCodeUnavailable");
        this.mGotCountryCode = false;
        this.mNitzTimeZoneDetectionSuccessful = false;
    }

    @Override
    public void handleNitzReceived(TimestampedValue<NitzData> timestampedValue) {
        this.mLatestNitzSignal = timestampedValue;
        this.updateTimeZoneFromCountryAndNitz();
        this.updateTimeFromNitz();
    }

}

