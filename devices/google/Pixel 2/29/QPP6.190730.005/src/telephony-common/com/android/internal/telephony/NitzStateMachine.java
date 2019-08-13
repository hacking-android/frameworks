/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.os.SystemProperties
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.telephony.TelephonyManager
 *  android.util.TimestampedValue
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.TimestampedValue;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.NitzData;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public interface NitzStateMachine {
    public void dumpLogs(FileDescriptor var1, IndentingPrintWriter var2, String[] var3);

    public void dumpState(PrintWriter var1);

    public NitzData getCachedNitzData();

    public String getSavedTimeZoneId();

    public void handleNetworkAvailable();

    public void handleNetworkCountryCodeSet(boolean var1);

    public void handleNetworkCountryCodeUnavailable();

    public void handleNitzReceived(TimestampedValue<NitzData> var1);

    public static class DeviceState {
        private static final int NITZ_UPDATE_DIFF_DEFAULT = 2000;
        private static final int NITZ_UPDATE_SPACING_DEFAULT = 600000;
        private final ContentResolver mCr;
        private final int mNitzUpdateDiff;
        private final int mNitzUpdateSpacing;
        private final GsmCdmaPhone mPhone;
        private final TelephonyManager mTelephonyManager;

        public DeviceState(GsmCdmaPhone gsmCdmaPhone) {
            this.mPhone = gsmCdmaPhone;
            gsmCdmaPhone = gsmCdmaPhone.getContext();
            this.mTelephonyManager = (TelephonyManager)gsmCdmaPhone.getSystemService("phone");
            this.mCr = gsmCdmaPhone.getContentResolver();
            this.mNitzUpdateSpacing = SystemProperties.getInt((String)"ro.nitz_update_spacing", (int)600000);
            this.mNitzUpdateDiff = SystemProperties.getInt((String)"ro.nitz_update_diff", (int)2000);
        }

        public boolean getIgnoreNitz() {
            String string = SystemProperties.get((String)"gsm.ignore-nitz");
            boolean bl = string != null && string.equals("yes");
            return bl;
        }

        public String getNetworkCountryIsoForPhone() {
            return this.mTelephonyManager.getNetworkCountryIsoForPhone(this.mPhone.getPhoneId());
        }

        public int getNitzUpdateDiffMillis() {
            return Settings.Global.getInt((ContentResolver)this.mCr, (String)"nitz_update_diff", (int)this.mNitzUpdateDiff);
        }

        public int getNitzUpdateSpacingMillis() {
            return Settings.Global.getInt((ContentResolver)this.mCr, (String)"nitz_update_spacing", (int)this.mNitzUpdateSpacing);
        }
    }

}

