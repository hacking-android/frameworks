/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.TelephonyManager
 *  com.android.internal.telephony.GsmAlphabet
 *  com.android.internal.telephony.GsmAlphabet$TextEncodingDetails
 *  com.android.internal.telephony.SmsConstants
 *  com.android.internal.telephony.SmsConstants$MessageClass
 *  com.android.internal.telephony.SmsHeader
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.telephony.SmsMessageBase$SubmitPduBase
 *  com.android.internal.telephony.cdma.SmsMessage
 *  com.android.internal.telephony.gsm.SmsMessage
 */
package android.telephony.gsm;

import android.telephony.TelephonyManager;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import java.util.Arrays;

@Deprecated
public class SmsMessage {
    @Deprecated
    public static final int ENCODING_16BIT = 3;
    @Deprecated
    public static final int ENCODING_7BIT = 1;
    @Deprecated
    public static final int ENCODING_8BIT = 2;
    @Deprecated
    public static final int ENCODING_UNKNOWN = 0;
    @Deprecated
    public static final int MAX_USER_DATA_BYTES = 140;
    @Deprecated
    public static final int MAX_USER_DATA_BYTES_WITH_HEADER = 134;
    @Deprecated
    public static final int MAX_USER_DATA_SEPTETS = 160;
    @Deprecated
    public static final int MAX_USER_DATA_SEPTETS_WITH_HEADER = 153;
    @Deprecated
    public SmsMessageBase mWrappedSmsMessage;

    @Deprecated
    public SmsMessage() {
        this(SmsMessage.getSmsFacility());
    }

    private SmsMessage(SmsMessageBase smsMessageBase) {
        this.mWrappedSmsMessage = smsMessageBase;
    }

    @Deprecated
    public static int[] calculateLength(CharSequence charSequence, boolean bl) {
        charSequence = com.android.internal.telephony.gsm.SmsMessage.calculateLength((CharSequence)charSequence, (boolean)bl);
        return new int[]{((GsmAlphabet.TextEncodingDetails)charSequence).msgCount, ((GsmAlphabet.TextEncodingDetails)charSequence).codeUnitCount, ((GsmAlphabet.TextEncodingDetails)charSequence).codeUnitsRemaining, ((GsmAlphabet.TextEncodingDetails)charSequence).codeUnitSize};
    }

    @Deprecated
    public static int[] calculateLength(String string, boolean bl) {
        return SmsMessage.calculateLength((CharSequence)string, bl);
    }

    @Deprecated
    public static SmsMessage createFromPdu(byte[] object) {
        object = 2 == TelephonyManager.getDefault().getCurrentPhoneType() ? com.android.internal.telephony.cdma.SmsMessage.createFromPdu((byte[])object) : com.android.internal.telephony.gsm.SmsMessage.createFromPdu((byte[])object);
        return new SmsMessage((SmsMessageBase)object);
    }

    @Deprecated
    private static final SmsMessageBase getSmsFacility() {
        if (2 == TelephonyManager.getDefault().getCurrentPhoneType()) {
            return new com.android.internal.telephony.cdma.SmsMessage();
        }
        return new com.android.internal.telephony.gsm.SmsMessage();
    }

    @Deprecated
    public static SubmitPdu getSubmitPdu(String string, String string2, String string3, boolean bl) {
        string = 2 == TelephonyManager.getDefault().getCurrentPhoneType() ? com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu((String)string, (String)string2, (String)string3, (boolean)bl, null) : com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)string, (String)string2, (String)string3, (boolean)bl);
        return new SubmitPdu((SmsMessageBase.SubmitPduBase)string);
    }

    @Deprecated
    public static SubmitPdu getSubmitPdu(String string, String string2, String string3, boolean bl, byte[] arrby) {
        string = 2 == TelephonyManager.getDefault().getCurrentPhoneType() ? com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu((String)string, (String)string2, (String)string3, (boolean)bl, (SmsHeader)SmsHeader.fromByteArray((byte[])arrby)) : com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)string, (String)string2, (String)string3, (boolean)bl, (byte[])arrby);
        return new SubmitPdu((SmsMessageBase.SubmitPduBase)string);
    }

    @Deprecated
    public static SubmitPdu getSubmitPdu(String string, String string2, short s, byte[] arrby, boolean bl) {
        string = 2 == TelephonyManager.getDefault().getCurrentPhoneType() ? com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu((String)string, (String)string2, (int)s, (byte[])arrby, (boolean)bl) : com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)string, (String)string2, (int)s, (byte[])arrby, (boolean)bl);
        return new SubmitPdu((SmsMessageBase.SubmitPduBase)string);
    }

    @Deprecated
    public static int getTPLayerLengthForPDU(String string) {
        if (2 == TelephonyManager.getDefault().getCurrentPhoneType()) {
            return com.android.internal.telephony.cdma.SmsMessage.getTPLayerLengthForPDU((String)string);
        }
        return com.android.internal.telephony.gsm.SmsMessage.getTPLayerLengthForPDU((String)string);
    }

    @Deprecated
    public String getDisplayMessageBody() {
        return this.mWrappedSmsMessage.getDisplayMessageBody();
    }

    @Deprecated
    public String getDisplayOriginatingAddress() {
        return this.mWrappedSmsMessage.getDisplayOriginatingAddress();
    }

    @Deprecated
    public String getEmailBody() {
        return this.mWrappedSmsMessage.getEmailBody();
    }

    @Deprecated
    public String getEmailFrom() {
        return this.mWrappedSmsMessage.getEmailFrom();
    }

    @Deprecated
    public int getIndexOnIcc() {
        return this.mWrappedSmsMessage.getIndexOnIcc();
    }

    @Deprecated
    public int getIndexOnSim() {
        return this.mWrappedSmsMessage.getIndexOnIcc();
    }

    @Deprecated
    public String getMessageBody() {
        return this.mWrappedSmsMessage.getMessageBody();
    }

    @Deprecated
    public MessageClass getMessageClass() {
        int n = this.mWrappedSmsMessage.getMessageClass().ordinal();
        return MessageClass.values()[n];
    }

    @Deprecated
    public String getOriginatingAddress() {
        return this.mWrappedSmsMessage.getOriginatingAddress();
    }

    @Deprecated
    public byte[] getPdu() {
        return this.mWrappedSmsMessage.getPdu();
    }

    @Deprecated
    public int getProtocolIdentifier() {
        return this.mWrappedSmsMessage.getProtocolIdentifier();
    }

    @Deprecated
    public String getPseudoSubject() {
        return this.mWrappedSmsMessage.getPseudoSubject();
    }

    @Deprecated
    public String getServiceCenterAddress() {
        return this.mWrappedSmsMessage.getServiceCenterAddress();
    }

    @Deprecated
    public int getStatus() {
        return this.mWrappedSmsMessage.getStatus();
    }

    @Deprecated
    public int getStatusOnIcc() {
        return this.mWrappedSmsMessage.getStatusOnIcc();
    }

    @Deprecated
    public int getStatusOnSim() {
        return this.mWrappedSmsMessage.getStatusOnIcc();
    }

    @Deprecated
    public long getTimestampMillis() {
        return this.mWrappedSmsMessage.getTimestampMillis();
    }

    @Deprecated
    public byte[] getUserData() {
        return this.mWrappedSmsMessage.getUserData();
    }

    @Deprecated
    public boolean isCphsMwiMessage() {
        return this.mWrappedSmsMessage.isCphsMwiMessage();
    }

    @Deprecated
    public boolean isEmail() {
        return this.mWrappedSmsMessage.isEmail();
    }

    @Deprecated
    public boolean isMWIClearMessage() {
        return this.mWrappedSmsMessage.isMWIClearMessage();
    }

    @Deprecated
    public boolean isMWISetMessage() {
        return this.mWrappedSmsMessage.isMWISetMessage();
    }

    @Deprecated
    public boolean isMwiDontStore() {
        return this.mWrappedSmsMessage.isMwiDontStore();
    }

    @Deprecated
    public boolean isReplace() {
        return this.mWrappedSmsMessage.isReplace();
    }

    @Deprecated
    public boolean isReplyPathPresent() {
        return this.mWrappedSmsMessage.isReplyPathPresent();
    }

    @Deprecated
    public boolean isStatusReportMessage() {
        return this.mWrappedSmsMessage.isStatusReportMessage();
    }

    @Deprecated
    public static enum MessageClass {
        UNKNOWN,
        CLASS_0,
        CLASS_1,
        CLASS_2,
        CLASS_3;
        
    }

    @Deprecated
    public static class SubmitPdu {
        @Deprecated
        public byte[] encodedMessage;
        @Deprecated
        public byte[] encodedScAddress;

        @Deprecated
        public SubmitPdu() {
        }

        @Deprecated
        protected SubmitPdu(SmsMessageBase.SubmitPduBase submitPduBase) {
            this.encodedMessage = submitPduBase.encodedMessage;
            this.encodedScAddress = submitPduBase.encodedScAddress;
        }

        @Deprecated
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SubmitPdu: encodedScAddress = ");
            stringBuilder.append(Arrays.toString(this.encodedScAddress));
            stringBuilder.append(", encodedMessage = ");
            stringBuilder.append(Arrays.toString(this.encodedMessage));
            return stringBuilder.toString();
        }
    }

}

