/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.GsmAlphabet
 *  com.android.internal.telephony.GsmAlphabet$TextEncodingDetails
 *  com.android.internal.telephony.SmsHeader
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.telephony.SmsMessageBase$SubmitPduBase
 *  com.android.internal.telephony.cdma.SmsMessage
 *  com.android.internal.telephony.gsm.SmsMessage
 */
package com.android.internal.telephony.util;

import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.cdma.SmsMessage;

public final class SMSDispatcherUtil {
    private SMSDispatcherUtil() {
    }

    public static GsmAlphabet.TextEncodingDetails calculateLength(boolean bl, CharSequence charSequence, boolean bl2) {
        if (bl) {
            return SMSDispatcherUtil.calculateLengthCdma(charSequence, bl2);
        }
        return SMSDispatcherUtil.calculateLengthGsm(charSequence, bl2);
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthCdma(CharSequence charSequence, boolean bl) {
        return SmsMessage.calculateLength((CharSequence)charSequence, (boolean)bl, (boolean)false);
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthGsm(CharSequence charSequence, boolean bl) {
        return com.android.internal.telephony.gsm.SmsMessage.calculateLength((CharSequence)charSequence, (boolean)bl);
    }

    public static SmsMessageBase.SubmitPduBase getSubmitPdu(boolean bl, String string, String string2, int n, byte[] arrby, boolean bl2) {
        if (bl) {
            return SMSDispatcherUtil.getSubmitPduCdma(string, string2, n, arrby, bl2);
        }
        return SMSDispatcherUtil.getSubmitPduGsm(string, string2, n, arrby, bl2);
    }

    public static SmsMessageBase.SubmitPduBase getSubmitPdu(boolean bl, String string, String string2, String string3, boolean bl2, SmsHeader smsHeader) {
        if (bl) {
            return SMSDispatcherUtil.getSubmitPduCdma(string, string2, string3, bl2, smsHeader);
        }
        return SMSDispatcherUtil.getSubmitPduGsm(string, string2, string3, bl2);
    }

    public static SmsMessageBase.SubmitPduBase getSubmitPdu(boolean bl, String string, String string2, String string3, boolean bl2, SmsHeader smsHeader, int n, int n2) {
        if (bl) {
            return SMSDispatcherUtil.getSubmitPduCdma(string, string2, string3, bl2, smsHeader, n);
        }
        return SMSDispatcherUtil.getSubmitPduGsm(string, string2, string3, bl2, n2);
    }

    public static SmsMessageBase.SubmitPduBase getSubmitPduCdma(String string, String string2, int n, byte[] arrby, boolean bl) {
        return SmsMessage.getSubmitPdu((String)string, (String)string2, (int)n, (byte[])arrby, (boolean)bl);
    }

    public static SmsMessageBase.SubmitPduBase getSubmitPduCdma(String string, String string2, String string3, boolean bl, SmsHeader smsHeader) {
        return SmsMessage.getSubmitPdu((String)string, (String)string2, (String)string3, (boolean)bl, (SmsHeader)smsHeader);
    }

    public static SmsMessageBase.SubmitPduBase getSubmitPduCdma(String string, String string2, String string3, boolean bl, SmsHeader smsHeader, int n) {
        return SmsMessage.getSubmitPdu((String)string, (String)string2, (String)string3, (boolean)bl, (SmsHeader)smsHeader, (int)n);
    }

    public static SmsMessageBase.SubmitPduBase getSubmitPduGsm(String string, String string2, int n, byte[] arrby, boolean bl) {
        return com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)string, (String)string2, (int)n, (byte[])arrby, (boolean)bl);
    }

    public static SmsMessageBase.SubmitPduBase getSubmitPduGsm(String string, String string2, String string3, boolean bl) {
        return com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)string, (String)string2, (String)string3, (boolean)bl);
    }

    public static SmsMessageBase.SubmitPduBase getSubmitPduGsm(String string, String string2, String string3, boolean bl, int n) {
        return com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)string, (String)string2, (String)string3, (boolean)bl, (int)n);
    }
}

