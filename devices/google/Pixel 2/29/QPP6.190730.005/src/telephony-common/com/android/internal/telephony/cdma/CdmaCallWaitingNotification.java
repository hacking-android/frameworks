/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.cdma;

import android.annotation.UnsupportedAppUsage;
import android.telephony.Rlog;

public class CdmaCallWaitingNotification {
    static final String LOG_TAG = "CdmaCallWaitingNotification";
    public int alertPitch = 0;
    public int isPresent = 0;
    public String name = null;
    public int namePresentation = 0;
    @UnsupportedAppUsage
    public String number = null;
    public int numberPlan = 0;
    public int numberPresentation = 0;
    public int numberType = 0;
    public int signal = 0;
    public int signalType = 0;

    public static int presentationFromCLIP(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected presentation ");
                    stringBuilder.append(n);
                    Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
                    return 3;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("Call Waiting Notification   number: ");
        stringBuilder.append(this.number);
        stringBuilder.append(" numberPresentation: ");
        stringBuilder.append(this.numberPresentation);
        stringBuilder.append(" name: ");
        stringBuilder.append(this.name);
        stringBuilder.append(" namePresentation: ");
        stringBuilder.append(this.namePresentation);
        stringBuilder.append(" numberType: ");
        stringBuilder.append(this.numberType);
        stringBuilder.append(" numberPlan: ");
        stringBuilder.append(this.numberPlan);
        stringBuilder.append(" isPresent: ");
        stringBuilder.append(this.isPresent);
        stringBuilder.append(" signalType: ");
        stringBuilder.append(this.signalType);
        stringBuilder.append(" alertPitch: ");
        stringBuilder.append(this.alertPitch);
        stringBuilder.append(" signal: ");
        stringBuilder.append(this.signal);
        return stringBuilder.toString();
    }
}

