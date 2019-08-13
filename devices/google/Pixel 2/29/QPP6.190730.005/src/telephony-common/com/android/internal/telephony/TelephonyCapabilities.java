/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.telephony.Rlog;
import com.android.internal.telephony.Phone;

public class TelephonyCapabilities {
    private static final String LOG_TAG = "TelephonyCapabilities";

    private TelephonyCapabilities() {
    }

    public static boolean canDistinguishDialingAndConnected(int n) {
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public static int getDeviceIdLabel(Phone phone) {
        if (phone.getPhoneType() == 1) {
            return 17040128;
        }
        if (phone.getPhoneType() == 2) {
            return 17040402;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getDeviceIdLabel: no known label for phone ");
        stringBuilder.append(phone.getPhoneName());
        Rlog.w((String)LOG_TAG, (String)stringBuilder.toString());
        return 0;
    }

    @UnsupportedAppUsage
    public static boolean supportsAdn(int n) {
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public static boolean supportsAnswerAndHold(Phone phone) {
        boolean bl;
        block0 : {
            int n = phone.getPhoneType();
            bl = true;
            if (n == 1 || phone.getPhoneType() == 3) break block0;
            bl = false;
        }
        return bl;
    }

    public static boolean supportsConferenceCallManagement(Phone phone) {
        boolean bl;
        block0 : {
            int n = phone.getPhoneType();
            bl = true;
            if (n == 1 || phone.getPhoneType() == 3) break block0;
            bl = false;
        }
        return bl;
    }

    public static boolean supportsEcm(Phone phone) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("supportsEcm: Phone type = ");
        stringBuilder.append(phone.getPhoneType());
        stringBuilder.append(" Ims Phone = ");
        stringBuilder.append(phone.getImsPhone());
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        boolean bl = phone.getPhoneType() == 2 || phone.getImsPhone() != null;
        return bl;
    }

    public static boolean supportsHoldAndUnhold(Phone phone) {
        boolean bl;
        block0 : {
            int n = phone.getPhoneType();
            bl = true;
            if (n == 1 || phone.getPhoneType() == 3 || phone.getPhoneType() == 5) break block0;
            bl = false;
        }
        return bl;
    }

    public static boolean supportsNetworkSelection(Phone phone) {
        int n = phone.getPhoneType();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public static boolean supportsOtasp(Phone phone) {
        boolean bl = phone.getPhoneType() == 2;
        return bl;
    }

    public static boolean supportsVoiceMessageCount(Phone phone) {
        boolean bl = phone.getVoiceMessageCount() != -1;
        return bl;
    }
}

