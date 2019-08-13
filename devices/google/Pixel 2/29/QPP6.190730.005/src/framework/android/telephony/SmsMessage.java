/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.os.Binder;
import android.telephony.Rlog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Sms7BitEncodingTranslator;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsMessageBase;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;

public class SmsMessage {
    public static final int ENCODING_16BIT = 3;
    public static final int ENCODING_7BIT = 1;
    public static final int ENCODING_8BIT = 2;
    public static final int ENCODING_KSC5601 = 4;
    public static final int ENCODING_UNKNOWN = 0;
    public static final String FORMAT_3GPP = "3gpp";
    public static final String FORMAT_3GPP2 = "3gpp2";
    private static final String LOG_TAG = "SmsMessage";
    public static final int MAX_USER_DATA_BYTES = 140;
    public static final int MAX_USER_DATA_BYTES_WITH_HEADER = 134;
    public static final int MAX_USER_DATA_SEPTETS = 160;
    public static final int MAX_USER_DATA_SEPTETS_WITH_HEADER = 153;
    private static boolean mIsNoEmsSupportConfigListLoaded;
    private static NoEmsSupportConfig[] mNoEmsSupportConfigList;
    @UnsupportedAppUsage
    private int mSubId = 0;
    @UnsupportedAppUsage
    public SmsMessageBase mWrappedSmsMessage;

    static {
        mNoEmsSupportConfigList = null;
        mIsNoEmsSupportConfigListLoaded = false;
    }

    public SmsMessage(SmsMessageBase smsMessageBase) {
        this.mWrappedSmsMessage = smsMessageBase;
    }

    public static int[] calculateLength(CharSequence charSequence, boolean bl) {
        return SmsMessage.calculateLength(charSequence, bl, SmsManager.getDefaultSmsSubscriptionId());
    }

    public static int[] calculateLength(CharSequence object, boolean bl, int n) {
        object = SmsMessage.useCdmaFormatForMoSms(n) ? com.android.internal.telephony.cdma.SmsMessage.calculateLength((CharSequence)object, bl, true) : com.android.internal.telephony.gsm.SmsMessage.calculateLength((CharSequence)object, bl);
        return new int[]{((GsmAlphabet.TextEncodingDetails)object).msgCount, ((GsmAlphabet.TextEncodingDetails)object).codeUnitCount, ((GsmAlphabet.TextEncodingDetails)object).codeUnitsRemaining, ((GsmAlphabet.TextEncodingDetails)object).codeUnitSize};
    }

    public static int[] calculateLength(String string2, boolean bl) {
        return SmsMessage.calculateLength((CharSequence)string2, bl);
    }

    public static int[] calculateLength(String string2, boolean bl, int n) {
        return SmsMessage.calculateLength((CharSequence)string2, bl, n);
    }

    public static SmsMessage createFromEfRecord(int n, byte[] object) {
        object = SmsMessage.isCdmaVoice() ? com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(n, object) : com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(n, object);
        if (object != null) {
            return new SmsMessage((SmsMessageBase)object);
        }
        Rlog.e(LOG_TAG, "createFromEfRecord(): wrappedMessage is null");
        return null;
    }

    public static SmsMessage createFromEfRecord(int n, byte[] object, int n2) {
        object = SmsMessage.isCdmaVoice(n2) ? com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(n, object) : com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(n, object);
        object = object != null ? new SmsMessage((SmsMessageBase)object) : null;
        return object;
    }

    @Deprecated
    public static SmsMessage createFromPdu(byte[] arrby) {
        String string2 = 2 == TelephonyManager.getDefault().getCurrentPhoneType() ? FORMAT_3GPP2 : FORMAT_3GPP;
        return SmsMessage.createFromPdu(arrby, string2);
    }

    public static SmsMessage createFromPdu(byte[] arrby, String string2) {
        return SmsMessage.createFromPdu(arrby, string2, true);
    }

    private static SmsMessage createFromPdu(byte[] object, String object2, boolean bl) {
        block8 : {
            String string2;
            block7 : {
                block6 : {
                    if (object == null) {
                        Rlog.i(LOG_TAG, "createFromPdu(): pdu is null");
                        return null;
                    }
                    string2 = FORMAT_3GPP2.equals(object2) ? FORMAT_3GPP : FORMAT_3GPP2;
                    if (!FORMAT_3GPP2.equals(object2)) break block6;
                    object2 = com.android.internal.telephony.cdma.SmsMessage.createFromPdu((byte[])object);
                    break block7;
                }
                if (!FORMAT_3GPP.equals(object2)) break block8;
                object2 = com.android.internal.telephony.gsm.SmsMessage.createFromPdu((byte[])object);
            }
            if (object2 != null) {
                return new SmsMessage((SmsMessageBase)object2);
            }
            if (bl) {
                return SmsMessage.createFromPdu((byte[])object, string2, false);
            }
            Rlog.e(LOG_TAG, "createFromPdu(): wrappedMessage is null");
            return null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("createFromPdu(): unsupported message format ");
        ((StringBuilder)object).append((String)object2);
        Rlog.e(LOG_TAG, ((StringBuilder)object).toString());
        return null;
    }

    @UnsupportedAppUsage
    public static ArrayList<String> fragmentText(String string2) {
        return SmsMessage.fragmentText(string2, SmsManager.getDefaultSmsSubscriptionId());
    }

    public static ArrayList<String> fragmentText(String object, int n) {
        int n2;
        boolean bl = SmsMessage.useCdmaFormatForMoSms(n);
        GsmAlphabet.TextEncodingDetails textEncodingDetails = bl ? com.android.internal.telephony.cdma.SmsMessage.calculateLength((CharSequence)object, false, true) : com.android.internal.telephony.gsm.SmsMessage.calculateLength((CharSequence)object, false);
        if (textEncodingDetails.codeUnitSize == 1) {
            n = textEncodingDetails.languageTable != 0 && textEncodingDetails.languageShiftTable != 0 ? 7 : (textEncodingDetails.languageTable == 0 && textEncodingDetails.languageShiftTable == 0 ? 0 : 4);
            n2 = n;
            if (textEncodingDetails.msgCount > 1) {
                n2 = n + 6;
            }
            n = n2;
            if (n2 != 0) {
                n = n2 + 1;
            }
            n = 160 - n;
        } else if (textEncodingDetails.msgCount > 1) {
            n = n2 = 134;
            if (!SmsMessage.hasEmsSupport()) {
                n = n2;
                if (textEncodingDetails.msgCount < 10) {
                    n = 134 - 2;
                }
            }
        } else {
            n = 140;
        }
        CharSequence charSequence = null;
        if (Resources.getSystem().getBoolean(17891523)) {
            charSequence = Sms7BitEncodingTranslator.translate((CharSequence)object, bl);
        }
        String string2 = charSequence;
        if (TextUtils.isEmpty(charSequence)) {
            string2 = object;
        }
        int n3 = 0;
        int n4 = string2.length();
        object = new ArrayList(textEncodingDetails.msgCount);
        while (n3 < n4) {
            n2 = textEncodingDetails.codeUnitSize == 1 ? (bl && textEncodingDetails.msgCount == 1 ? Math.min(n, n4 - n3) + n3 : GsmAlphabet.findGsmSeptetLimitIndex(string2, n3, n, textEncodingDetails.languageTable, textEncodingDetails.languageShiftTable)) : SmsMessageBase.findNextUnicodePosition(n3, n, string2);
            if (n2 > n3 && n2 <= n4) {
                ((ArrayList)object).add(string2.substring(n3, n2));
                n3 = n2;
                continue;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("fragmentText failed (");
            ((StringBuilder)charSequence).append(n3);
            ((StringBuilder)charSequence).append(" >= ");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append(" or ");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append(" >= ");
            ((StringBuilder)charSequence).append(n4);
            ((StringBuilder)charSequence).append(")");
            Rlog.e(LOG_TAG, ((StringBuilder)charSequence).toString());
            break;
        }
        return object;
    }

    public static SubmitPdu getSubmitPdu(String string2, String string3, String string4, boolean bl) {
        return SmsMessage.getSubmitPdu(string2, string3, string4, bl, SmsManager.getDefaultSmsSubscriptionId());
    }

    public static SubmitPdu getSubmitPdu(String object, String string2, String string3, boolean bl, int n) {
        object = SmsMessage.useCdmaFormatForMoSms(n) ? com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu((String)object, string2, string3, bl, null) : com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)object, string2, string3, bl);
        return new SubmitPdu((SmsMessageBase.SubmitPduBase)object);
    }

    public static SubmitPdu getSubmitPdu(String object, String string2, short s, byte[] arrby, boolean bl) {
        object = SmsMessage.useCdmaFormatForMoSms() ? com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu((String)object, string2, s, arrby, bl) : com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)object, string2, s, arrby, bl);
        return new SubmitPdu((SmsMessageBase.SubmitPduBase)object);
    }

    public static int getTPLayerLengthForPDU(String string2) {
        if (SmsMessage.isCdmaVoice()) {
            return com.android.internal.telephony.cdma.SmsMessage.getTPLayerLengthForPDU(string2);
        }
        return com.android.internal.telephony.gsm.SmsMessage.getTPLayerLengthForPDU(string2);
    }

    public static boolean hasEmsSupport() {
        long l;
        block5 : {
            if (!SmsMessage.isNoEmsSupportConfigListExisted()) {
                return true;
            }
            l = Binder.clearCallingIdentity();
            String string2 = TelephonyManager.getDefault().getSimOperatorNumeric();
            String string3 = TelephonyManager.getDefault().getGroupIdLevel1();
            if (TextUtils.isEmpty(string2)) break block5;
            for (NoEmsSupportConfig noEmsSupportConfig : mNoEmsSupportConfigList) {
                if (!string2.startsWith(noEmsSupportConfig.mOperatorNumber) || !TextUtils.isEmpty(noEmsSupportConfig.mGid1) && (TextUtils.isEmpty(noEmsSupportConfig.mGid1) || !noEmsSupportConfig.mGid1.equalsIgnoreCase(string3))) continue;
                return false;
            }
        }
        return true;
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    private static boolean isCdmaVoice() {
        return SmsMessage.isCdmaVoice(SmsManager.getDefaultSmsSubscriptionId());
    }

    private static boolean isCdmaVoice(int n) {
        boolean bl = 2 == TelephonyManager.getDefault().getCurrentPhoneType(n);
        return bl;
    }

    private static boolean isNoEmsSupportConfigListExisted() {
        Object[] arrobject;
        if (!mIsNoEmsSupportConfigListLoaded && (arrobject = Resources.getSystem()) != null) {
            if ((arrobject = arrobject.getStringArray(17236093)) != null && arrobject.length > 0) {
                mNoEmsSupportConfigList = new NoEmsSupportConfig[arrobject.length];
                for (int i = 0; i < arrobject.length; ++i) {
                    SmsMessage.mNoEmsSupportConfigList[i] = new NoEmsSupportConfig(((String)arrobject[i]).split(";"));
                }
            }
            mIsNoEmsSupportConfigListLoaded = true;
        }
        return (arrobject = mNoEmsSupportConfigList) != null && arrobject.length != 0;
    }

    public static SmsMessage newFromCMT(byte[] object) {
        if ((object = com.android.internal.telephony.gsm.SmsMessage.newFromCMT(object)) != null) {
            return new SmsMessage((SmsMessageBase)object);
        }
        Rlog.e(LOG_TAG, "newFromCMT(): wrappedMessage is null");
        return null;
    }

    public static boolean shouldAppendPageNumberAsPrefix() {
        if (!SmsMessage.isNoEmsSupportConfigListExisted()) {
            return false;
        }
        long l = Binder.clearCallingIdentity();
        String string2 = TelephonyManager.getDefault().getSimOperatorNumeric();
        String string3 = TelephonyManager.getDefault().getGroupIdLevel1();
        for (NoEmsSupportConfig noEmsSupportConfig : mNoEmsSupportConfigList) {
            if (!string2.startsWith(noEmsSupportConfig.mOperatorNumber) || !TextUtils.isEmpty(noEmsSupportConfig.mGid1) && (TextUtils.isEmpty(noEmsSupportConfig.mGid1) || !noEmsSupportConfig.mGid1.equalsIgnoreCase(string3))) continue;
            return noEmsSupportConfig.mIsPrefix;
        }
        return false;
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    @UnsupportedAppUsage
    private static boolean useCdmaFormatForMoSms() {
        return SmsMessage.useCdmaFormatForMoSms(SmsManager.getDefaultSmsSubscriptionId());
    }

    @UnsupportedAppUsage
    private static boolean useCdmaFormatForMoSms(int n) {
        SmsManager smsManager = SmsManager.getSmsManagerForSubscriptionId(n);
        if (!smsManager.isImsSmsSupported()) {
            return SmsMessage.isCdmaVoice(n);
        }
        return FORMAT_3GPP2.equals(smsManager.getImsSmsFormat());
    }

    public String getDisplayMessageBody() {
        return this.mWrappedSmsMessage.getDisplayMessageBody();
    }

    public String getDisplayOriginatingAddress() {
        return this.mWrappedSmsMessage.getDisplayOriginatingAddress();
    }

    public String getEmailBody() {
        return this.mWrappedSmsMessage.getEmailBody();
    }

    public String getEmailFrom() {
        return this.mWrappedSmsMessage.getEmailFrom();
    }

    public int getIndexOnIcc() {
        return this.mWrappedSmsMessage.getIndexOnIcc();
    }

    @Deprecated
    public int getIndexOnSim() {
        return this.mWrappedSmsMessage.getIndexOnIcc();
    }

    public String getMessageBody() {
        return this.mWrappedSmsMessage.getMessageBody();
    }

    public MessageClass getMessageClass() {
        int n = 1.$SwitchMap$com$android$internal$telephony$SmsConstants$MessageClass[this.mWrappedSmsMessage.getMessageClass().ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return MessageClass.UNKNOWN;
                    }
                    return MessageClass.CLASS_3;
                }
                return MessageClass.CLASS_2;
            }
            return MessageClass.CLASS_1;
        }
        return MessageClass.CLASS_0;
    }

    public String getOriginatingAddress() {
        return this.mWrappedSmsMessage.getOriginatingAddress();
    }

    public byte[] getPdu() {
        return this.mWrappedSmsMessage.getPdu();
    }

    public int getProtocolIdentifier() {
        return this.mWrappedSmsMessage.getProtocolIdentifier();
    }

    public String getPseudoSubject() {
        return this.mWrappedSmsMessage.getPseudoSubject();
    }

    public String getRecipientAddress() {
        return this.mWrappedSmsMessage.getRecipientAddress();
    }

    public String getServiceCenterAddress() {
        return this.mWrappedSmsMessage.getServiceCenterAddress();
    }

    public int getStatus() {
        return this.mWrappedSmsMessage.getStatus();
    }

    public int getStatusOnIcc() {
        return this.mWrappedSmsMessage.getStatusOnIcc();
    }

    @Deprecated
    public int getStatusOnSim() {
        return this.mWrappedSmsMessage.getStatusOnIcc();
    }

    @UnsupportedAppUsage
    public int getSubId() {
        return this.mSubId;
    }

    public long getTimestampMillis() {
        return this.mWrappedSmsMessage.getTimestampMillis();
    }

    public byte[] getUserData() {
        return this.mWrappedSmsMessage.getUserData();
    }

    public boolean isCphsMwiMessage() {
        return this.mWrappedSmsMessage.isCphsMwiMessage();
    }

    public boolean isEmail() {
        return this.mWrappedSmsMessage.isEmail();
    }

    public boolean isMWIClearMessage() {
        return this.mWrappedSmsMessage.isMWIClearMessage();
    }

    public boolean isMWISetMessage() {
        return this.mWrappedSmsMessage.isMWISetMessage();
    }

    public boolean isMwiDontStore() {
        return this.mWrappedSmsMessage.isMwiDontStore();
    }

    public boolean isReplace() {
        return this.mWrappedSmsMessage.isReplace();
    }

    public boolean isReplyPathPresent() {
        return this.mWrappedSmsMessage.isReplyPathPresent();
    }

    public boolean isStatusReportMessage() {
        return this.mWrappedSmsMessage.isStatusReportMessage();
    }

    @UnsupportedAppUsage
    public void setSubId(int n) {
        this.mSubId = n;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Format {
    }

    public static enum MessageClass {
        UNKNOWN,
        CLASS_0,
        CLASS_1,
        CLASS_2,
        CLASS_3;
        
    }

    private static class NoEmsSupportConfig {
        String mGid1;
        boolean mIsPrefix;
        String mOperatorNumber;

        public NoEmsSupportConfig(String[] object) {
            this.mOperatorNumber = object[0];
            this.mIsPrefix = "prefix".equals(object[1]);
            object = ((String[])object).length > 2 ? object[2] : null;
            this.mGid1 = object;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NoEmsSupportConfig { mOperatorNumber = ");
            stringBuilder.append(this.mOperatorNumber);
            stringBuilder.append(", mIsPrefix = ");
            stringBuilder.append(this.mIsPrefix);
            stringBuilder.append(", mGid1 = ");
            stringBuilder.append(this.mGid1);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class SubmitPdu {
        public byte[] encodedMessage;
        public byte[] encodedScAddress;

        protected SubmitPdu(SmsMessageBase.SubmitPduBase submitPduBase) {
            this.encodedMessage = submitPduBase.encodedMessage;
            this.encodedScAddress = submitPduBase.encodedScAddress;
        }

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

