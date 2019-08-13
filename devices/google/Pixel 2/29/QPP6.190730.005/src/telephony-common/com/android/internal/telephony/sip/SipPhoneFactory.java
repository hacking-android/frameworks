/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.sip.SipProfile
 *  android.net.sip.SipProfile$Builder
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.sip;

import android.content.Context;
import android.net.sip.SipProfile;
import android.telephony.Rlog;
import com.android.internal.telephony.PhoneNotifier;
import com.android.internal.telephony.sip.SipPhone;
import java.text.ParseException;

public class SipPhoneFactory {
    public static SipPhone makePhone(String object, Context context, PhoneNotifier phoneNotifier) {
        try {
            SipProfile.Builder builder = new SipProfile.Builder((String)object);
            object = new SipPhone(context, phoneNotifier, builder.build());
            return object;
        }
        catch (ParseException parseException) {
            Rlog.w((String)"SipPhoneFactory", (String)"makePhone", (Throwable)parseException);
            return null;
        }
    }
}

