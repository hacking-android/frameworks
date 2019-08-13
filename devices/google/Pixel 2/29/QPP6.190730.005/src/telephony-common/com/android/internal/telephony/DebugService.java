/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.Rlog
 *  android.text.TextUtils
 */
package com.android.internal.telephony;

import android.telephony.Rlog;
import android.text.TextUtils;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class DebugService {
    private static String TAG = "DebugService";

    public DebugService() {
        DebugService.log("DebugService:");
    }

    private static void log(String string) {
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DebugService ");
        stringBuilder.append(string);
        Rlog.d((String)string2, (String)stringBuilder.toString());
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        if (arrstring != null && arrstring.length > 0 && (TextUtils.equals((CharSequence)arrstring[0], (CharSequence)"--metrics") || TextUtils.equals((CharSequence)arrstring[0], (CharSequence)"--metricsproto") || TextUtils.equals((CharSequence)arrstring[0], (CharSequence)"--metricsprototext"))) {
            DebugService.log("Collecting telephony metrics..");
            TelephonyMetrics.getInstance().dump(fileDescriptor, printWriter, arrstring);
            return;
        }
        DebugService.log("Dump telephony.");
        PhoneFactory.dump(fileDescriptor, printWriter, arrstring);
    }
}

