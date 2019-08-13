/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.V1_0.CdmaSmsAddress
 *  android.hardware.radio.V1_0.CdmaSmsMessage
 *  android.hardware.radio.V1_0.CdmaSmsSubaddress
 *  android.telephony.SmsMessage
 *  com.android.internal.telephony.SmsAddress
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.telephony.cdma.SmsMessage
 *  com.android.internal.telephony.cdma.sms.CdmaSmsAddress
 *  com.android.internal.telephony.cdma.sms.CdmaSmsSubaddress
 *  com.android.internal.telephony.cdma.sms.SmsEnvelope
 */
package com.android.internal.telephony.cdma;

import android.hardware.radio.V1_0.CdmaSmsMessage;
import com.android.internal.telephony.SmsAddress;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.cdma.SmsMessage;
import com.android.internal.telephony.cdma.sms.CdmaSmsAddress;
import com.android.internal.telephony.cdma.sms.CdmaSmsSubaddress;
import com.android.internal.telephony.cdma.sms.SmsEnvelope;
import java.util.ArrayList;

public class SmsMessageConverter {
    private static final String LOGGABLE_TAG = "CDMA:SMS";
    static final String LOG_TAG = "SmsMessageConverter";
    private static final boolean VDBG = false;

    public static SmsMessage newCdmaSmsMessageFromRil(CdmaSmsMessage cdmaSmsMessage) {
        int n;
        SmsEnvelope smsEnvelope = new SmsEnvelope();
        CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
        CdmaSmsSubaddress cdmaSmsSubaddress = new CdmaSmsSubaddress();
        smsEnvelope.teleService = cdmaSmsMessage.teleserviceId;
        smsEnvelope.messageType = cdmaSmsMessage.isServicePresent ? 1 : (smsEnvelope.teleService == 0 ? 2 : 0);
        smsEnvelope.serviceCategory = cdmaSmsMessage.serviceCategory;
        int n2 = cdmaSmsMessage.address.digitMode;
        cdmaSmsAddress.digitMode = (byte)(n2 & 255);
        cdmaSmsAddress.numberMode = (byte)(cdmaSmsMessage.address.numberMode & 255);
        cdmaSmsAddress.ton = cdmaSmsMessage.address.numberType;
        cdmaSmsAddress.numberPlan = (byte)(cdmaSmsMessage.address.numberPlan & 255);
        int n3 = cdmaSmsMessage.address.digits.size();
        cdmaSmsAddress.numberOfDigits = n3;
        byte[] arrby = new byte[n3];
        for (n = 0; n < n3; ++n) {
            arrby[n] = (Byte)cdmaSmsMessage.address.digits.get(n);
            if (n2 != 0) continue;
            arrby[n] = SmsMessage.convertDtmfToAscii((byte)arrby[n]);
        }
        cdmaSmsAddress.origBytes = arrby;
        cdmaSmsSubaddress.type = cdmaSmsMessage.subAddress.subaddressType;
        cdmaSmsSubaddress.odd = (byte)(cdmaSmsMessage.subAddress.odd ? 1 : 0);
        n = n2 = (int)((byte)cdmaSmsMessage.subAddress.digits.size());
        if (n2 < 0) {
            n = 0;
        }
        arrby = new byte[n];
        for (n2 = 0; n2 < n; ++n2) {
            arrby[n2] = (Byte)cdmaSmsMessage.subAddress.digits.get(n2);
        }
        cdmaSmsSubaddress.origBytes = arrby;
        n = n2 = cdmaSmsMessage.bearerData.size();
        if (n2 < 0) {
            n = 0;
        }
        arrby = new byte[n];
        for (n2 = 0; n2 < n; ++n2) {
            arrby[n2] = (Byte)cdmaSmsMessage.bearerData.get(n2);
        }
        smsEnvelope.bearerData = arrby;
        smsEnvelope.origAddress = cdmaSmsAddress;
        smsEnvelope.origSubaddress = cdmaSmsSubaddress;
        return new SmsMessage((SmsAddress)cdmaSmsAddress, smsEnvelope);
    }

    public static android.telephony.SmsMessage newSmsMessageFromCdmaSmsMessage(CdmaSmsMessage cdmaSmsMessage) {
        return new android.telephony.SmsMessage((SmsMessageBase)SmsMessageConverter.newCdmaSmsMessageFromRil(cdmaSmsMessage));
    }
}

