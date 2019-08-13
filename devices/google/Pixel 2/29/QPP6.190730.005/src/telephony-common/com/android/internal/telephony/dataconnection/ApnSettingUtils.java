/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.PersistableBundle
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.data.ApnSetting
 *  android.util.Log
 */
package com.android.internal.telephony.dataconnection;

import android.content.Context;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.data.ApnSetting;
import android.util.Log;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.uicc.IccRecords;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ApnSettingUtils {
    private static final boolean DBG = false;
    static final String LOG_TAG = "ApnSetting";

    private static boolean iccidMatches(String arrstring, String string) {
        arrstring = arrstring.split(",");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!string.startsWith(arrstring[i])) continue;
            Log.d((String)LOG_TAG, (String)"mvno icc id match found");
            return true;
        }
        return false;
    }

    private static boolean imsiMatches(String string, String string2) {
        int n = string.length();
        if (n <= 0) {
            return false;
        }
        if (n > string2.length()) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == 'x' || c == 'X' || c == string2.charAt(i)) continue;
            return false;
        }
        return true;
    }

    public static boolean isMetered(ApnSetting object, Phone phone) {
        if (phone != null && object != null) {
            object = object.getApnTypes().iterator();
            while (object.hasNext()) {
                if (!ApnSettingUtils.isMeteredApnType((Integer)object.next(), phone)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public static boolean isMeteredApnType(int n, Phone object) {
        if (object == null) {
            return true;
        }
        boolean bl = object.getServiceState().getDataRoaming();
        int n2 = ((Phone)object).getSubId();
        String string = bl ? "carrier_metered_roaming_apn_types_strings" : "carrier_metered_apn_types_strings";
        if ((object = (CarrierConfigManager)((Phone)object).getContext().getSystemService("carrier_config")) == null) {
            Rlog.e((String)LOG_TAG, (String)"Carrier config service is not available");
            return true;
        }
        if ((object = object.getConfigForSubId(n2)) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't get the config. subId = ");
            ((StringBuilder)object).append(n2);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
            return true;
        }
        if ((object = object.getStringArray(string)) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" is not available. subId = ");
            ((StringBuilder)object).append(n2);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
            return true;
        }
        if (((HashSet)(object = new HashSet<String>(Arrays.asList(object)))).contains(ApnSetting.getApnTypeString((int)n))) {
            return true;
        }
        return n == 255 && ((HashSet)object).size() > 0;
    }

    public static boolean mvnoMatches(IccRecords object, int n, String string) {
        if (n == 0) {
            if (((IccRecords)object).getServiceProviderName() != null && ((IccRecords)object).getServiceProviderName().equalsIgnoreCase(string)) {
                return true;
            }
        } else if (n == 1) {
            if ((object = ((IccRecords)object).getIMSI()) != null && ApnSettingUtils.imsiMatches(string, (String)object)) {
                return true;
            }
        } else if (n == 2) {
            object = ((IccRecords)object).getGid1();
            n = string.length();
            if (object != null && ((String)object).length() >= n && ((String)object).substring(0, n).equalsIgnoreCase(string)) {
                return true;
            }
        } else if (n == 3 && (object = ((IccRecords)object).getIccId()) != null && ApnSettingUtils.iccidMatches(string, (String)object)) {
            return true;
        }
        return false;
    }
}

