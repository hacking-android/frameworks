/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.imsphone;

import android.content.Context;
import android.telephony.Rlog;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneNotifier;
import com.android.internal.telephony.imsphone.ImsPhone;

public class ImsPhoneFactory {
    public static ImsPhone makePhone(Context object, PhoneNotifier phoneNotifier, Phone phone) {
        try {
            object = new ImsPhone((Context)object, phoneNotifier, phone);
            return object;
        }
        catch (Exception exception) {
            Rlog.e((String)"VoltePhoneFactory", (String)"makePhone", (Throwable)exception);
            return null;
        }
    }
}

