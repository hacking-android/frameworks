/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.gsm;

import android.content.res.Resources;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.text.TextUtils;
import android.text.format.Time;
import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Sms7BitEncodingTranslator;
import com.android.internal.telephony.SmsAddress;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.gsm.GsmSmsAddress;
import com.android.internal.telephony.uicc.IccUtils;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;

public class SmsMessage
extends SmsMessageBase {
    private static final int INVALID_VALIDITY_PERIOD = -1;
    static final String LOG_TAG = "SmsMessage";
    private static final int VALIDITY_PERIOD_FORMAT_ABSOLUTE = 3;
    private static final int VALIDITY_PERIOD_FORMAT_ENHANCED = 1;
    private static final int VALIDITY_PERIOD_FORMAT_NONE = 0;
    private static final int VALIDITY_PERIOD_FORMAT_RELATIVE = 2;
    private static final int VALIDITY_PERIOD_MAX = 635040;
    private static final int VALIDITY_PERIOD_MIN = 5;
    private static final boolean VDBG = false;
    private int mDataCodingScheme;
    private boolean mIsStatusReportMessage = false;
    private int mMti;
    private int mProtocolIdentifier;
    private boolean mReplyPathPresent = false;
    private int mStatus;
    private int mVoiceMailCount = 0;
    private SmsConstants.MessageClass messageClass;

    public static GsmAlphabet.TextEncodingDetails calculateLength(CharSequence object, boolean bl) {
        String string2 = null;
        if (Resources.getSystem().getBoolean(17891523)) {
            string2 = Sms7BitEncodingTranslator.translate((CharSequence)object, false);
        }
        CharSequence charSequence = string2;
        if (TextUtils.isEmpty(string2)) {
            charSequence = object;
        }
        if ((object = GsmAlphabet.countGsmSeptets(charSequence, bl)) == null) {
            return SmsMessageBase.calcUnicodeEncodingDetails(charSequence);
        }
        return object;
    }

    public static SmsMessage createFromEfRecord(int n, byte[] arrby) {
        SmsMessage smsMessage;
        try {
            smsMessage = new SmsMessage();
            smsMessage.mIndexOnIcc = n;
        }
        catch (RuntimeException runtimeException) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", runtimeException);
            return null;
        }
        if ((arrby[0] & 1) == 0) {
            Rlog.w(LOG_TAG, "SMS parsing failed: Trying to parse a free record");
            return null;
        }
        smsMessage.mStatusOnIcc = arrby[0] & 7;
        n = arrby.length - 1;
        byte[] arrby2 = new byte[n];
        System.arraycopy(arrby, 1, arrby2, 0, n);
        smsMessage.parsePdu(arrby2);
        return smsMessage;
    }

    public static SmsMessage createFromPdu(byte[] arrby) {
        try {
            SmsMessage smsMessage = new SmsMessage();
            smsMessage.parsePdu(arrby);
            return smsMessage;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed with out of memory: ", outOfMemoryError);
            return null;
        }
        catch (RuntimeException runtimeException) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", runtimeException);
            return null;
        }
    }

    private static byte[] encodeUCS2(String arrby, byte[] arrby2) throws UnsupportedEncodingException, EncodeException {
        arrby = arrby.getBytes("utf-16be");
        if (arrby2 != null) {
            byte[] arrby3 = new byte[arrby2.length + arrby.length + 1];
            arrby3[0] = (byte)arrby2.length;
            System.arraycopy(arrby2, 0, arrby3, 1, arrby2.length);
            System.arraycopy(arrby, 0, arrby3, arrby2.length + 1, arrby.length);
            arrby = arrby3;
        }
        if (arrby.length <= 255) {
            arrby2 = new byte[arrby.length + 1];
            arrby2[0] = (byte)(255 & arrby.length);
            System.arraycopy(arrby, 0, arrby2, 1, arrby.length);
            return arrby2;
        }
        throw new EncodeException("Payload cannot exceed 255 bytes", 1);
    }

    public static int getRelativeValidityPeriod(int n) {
        int n2 = -1;
        if (n >= 5 && n <= 635040) {
            if (n <= 720) {
                n2 = n / 5 - 1;
            } else if (n <= 1440) {
                n2 = (n - 720) / 30 + 143;
            } else if (n <= 43200) {
                n2 = n / 1440 + 166;
            } else if (n <= 635040) {
                n2 = n / 10080 + 192;
            }
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Validity Period");
        stringBuilder.append(n);
        Rlog.e(LOG_TAG, stringBuilder.toString());
        return -1;
    }

    public static SubmitPdu getSubmitPdu(String object, String string2, int n, byte[] arrby, boolean bl) {
        byte[] arrby2 = new SmsHeader.PortAddrs();
        arrby2.destPort = n;
        arrby2.origPort = 0;
        arrby2.areEightBits = false;
        Object object2 = new SmsHeader();
        ((SmsHeader)object2).portAddrs = arrby2;
        arrby2 = SmsHeader.toByteArray((SmsHeader)object2);
        if (arrby.length + arrby2.length + 1 > 140) {
            object = new StringBuilder();
            ((StringBuilder)object).append("SMS data message may only contain ");
            ((StringBuilder)object).append(140 - arrby2.length - 1);
            ((StringBuilder)object).append(" bytes");
            Rlog.e(LOG_TAG, ((StringBuilder)object).toString());
            return null;
        }
        object2 = new SubmitPdu();
        if ((object = SmsMessage.getSubmitPduHead((String)object, string2, (byte)65, bl, (SubmitPdu)object2)) == null) {
            return object2;
        }
        ((ByteArrayOutputStream)object).write(4);
        ((ByteArrayOutputStream)object).write(arrby.length + arrby2.length + 1);
        ((ByteArrayOutputStream)object).write(arrby2.length);
        ((ByteArrayOutputStream)object).write(arrby2, 0, arrby2.length);
        ((ByteArrayOutputStream)object).write(arrby, 0, arrby.length);
        ((SubmitPdu)object2).encodedMessage = ((ByteArrayOutputStream)object).toByteArray();
        return object2;
    }

    public static SubmitPdu getSubmitPdu(String string2, String string3, String string4, boolean bl) {
        return SmsMessage.getSubmitPdu(string2, string3, string4, bl, null);
    }

    public static SubmitPdu getSubmitPdu(String string2, String string3, String string4, boolean bl, int n) {
        return SmsMessage.getSubmitPdu(string2, string3, string4, bl, null, 0, 0, 0, n);
    }

    public static SubmitPdu getSubmitPdu(String string2, String string3, String string4, boolean bl, byte[] arrby) {
        return SmsMessage.getSubmitPdu(string2, string3, string4, bl, arrby, 0, 0, 0);
    }

    public static SubmitPdu getSubmitPdu(String string2, String string3, String string4, boolean bl, byte[] arrby, int n, int n2, int n3) {
        return SmsMessage.getSubmitPdu(string2, string3, string4, bl, arrby, n, n2, n3, -1);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static SubmitPdu getSubmitPdu(String var0, String var1_5, String var2_6, boolean var3_7, byte[] var4_8, int var5_9, int var6_10, int var7_11, int var8_12) {
        block21 : {
            block22 : {
                if (var2_6 == null) return null;
                if (var1_5 == null) {
                    return null;
                }
                if (var5_9 == 0) {
                    var9_13 = SmsMessage.calculateLength(var2_6, false);
                    var5_9 = var9_13.codeUnitSize;
                    var7_11 = var9_13.languageTable;
                    var6_10 = var9_13.languageShiftTable;
                    if (var5_9 == 1 && (var7_11 != 0 || var6_10 != 0)) {
                        if (var4_8 != null) {
                            var9_13 = SmsHeader.fromByteArray((byte[])var4_8);
                            if (var9_13.languageTable != var7_11 || var9_13.languageShiftTable != var6_10) {
                                var4_8 = new StringBuilder();
                                var4_8.append("Updating language table in SMS header: ");
                                var4_8.append(var9_13.languageTable);
                                var4_8.append(" -> ");
                                var4_8.append(var7_11);
                                var4_8.append(", ");
                                var4_8.append(var9_13.languageShiftTable);
                                var4_8.append(" -> ");
                                var4_8.append(var6_10);
                                Rlog.w("SmsMessage", var4_8.toString());
                                var9_13.languageTable = var7_11;
                                var9_13.languageShiftTable = var6_10;
                                var4_8 = SmsHeader.toByteArray((SmsHeader)var9_13);
                            }
                        } else {
                            var4_8 = new SmsHeader();
                            var4_8.languageTable = var7_11;
                            var4_8.languageShiftTable = var6_10;
                            var4_8 = SmsHeader.toByteArray((SmsHeader)var4_8);
                        }
                    }
                } else {
                    var10_14 = var6_10;
                    var6_10 = var7_11;
                    var7_11 = var10_14;
                }
                var9_13 = new SubmitPdu();
                var11_15 = SmsMessage.getRelativeValidityPeriod(var8_12);
                var8_12 = var11_15 >= 0 ? 2 : 0;
                var10_14 = var4_8 != null ? 64 : 0;
                var1_5 = SmsMessage.getSubmitPduHead((String)var0, (String)var1_5, (byte)(var8_12 << 3 | 1 | var10_14), var3_7, (SubmitPdu)var9_13);
                if (var1_5 == null) {
                    return var9_13;
                }
                if (var5_9 != 1) ** GOTO lbl56
                try {
                    var0 = GsmAlphabet.stringToGsm7BitPackedWithHeader(var2_6, (byte[])var4_8, var7_11, var6_10);
                    break block21;
lbl56: // 2 sources:
                    var0 = SmsMessage.encodeUCS2(var2_6, (byte[])var4_8);
                    break block21;
                }
                catch (EncodeException var0_1) {
                    break block22;
                }
                catch (UnsupportedEncodingException var0_2) {
                    Rlog.e("SmsMessage", "Implausible UnsupportedEncodingException ", var0_2);
                    return null;
                }
            }
            if (var0_1.getError() == 1) {
                Rlog.e("SmsMessage", "Exceed size limitation EncodeException", var0_1);
                return null;
            }
            try {
                var0 = SmsMessage.encodeUCS2(var2_6, (byte[])var4_8);
                var5_9 = 3;
            }
            catch (UnsupportedEncodingException var0_3) {
                Rlog.e("SmsMessage", "Implausible UnsupportedEncodingException ", var0_3);
                return null;
            }
            catch (EncodeException var0_4) {
                Rlog.e("SmsMessage", "Exceed size limitation EncodeException", var0_4);
                return null;
            }
        }
        if (var5_9 == 1) {
            if ((var0[0] & 255) > 160) {
                var1_5 = new StringBuilder();
                var1_5.append("Message too long (");
                var1_5.append(var0[0] & 255);
                var1_5.append(" septets)");
                Rlog.e("SmsMessage", var1_5.toString());
                return null;
            }
            var1_5.write(0);
        } else {
            if ((var0[0] & 255) > 140) {
                var1_5 = new StringBuilder();
                var1_5.append("Message too long (");
                var1_5.append(var0[0] & 255);
                var1_5.append(" bytes)");
                Rlog.e("SmsMessage", var1_5.toString());
                return null;
            }
            var1_5.write(8);
        }
        if (var8_12 == 2) {
            var1_5.write(var11_15);
        }
        var1_5.write(var0, 0, var0.length);
        var9_13.encodedMessage = var1_5.toByteArray();
        return var9_13;
    }

    private static ByteArrayOutputStream getSubmitPduHead(String arrby, String string2, byte n, boolean bl, SubmitPdu submitPdu) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(180);
        submitPdu.encodedScAddress = arrby == null ? null : PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength((String)arrby);
        int n2 = n;
        if (bl) {
            n2 = (byte)(n | 32);
        }
        byteArrayOutputStream.write(n2);
        byteArrayOutputStream.write(0);
        arrby = PhoneNumberUtils.networkPortionToCalledPartyBCD(string2);
        if (arrby == null) {
            return null;
        }
        n2 = arrby.length;
        n = 1;
        if ((arrby[arrby.length - 1] & 240) != 240) {
            n = 0;
        }
        byteArrayOutputStream.write((n2 - 1) * 2 - n);
        byteArrayOutputStream.write(arrby, 0, arrby.length);
        byteArrayOutputStream.write(0);
        return byteArrayOutputStream;
    }

    public static int getTPLayerLengthForPDU(String string2) {
        return string2.length() / 2 - Integer.parseInt(string2.substring(0, 2), 16) - 1;
    }

    public static SmsMessage newFromCDS(byte[] arrby) {
        try {
            SmsMessage smsMessage = new SmsMessage();
            smsMessage.parsePdu(arrby);
            return smsMessage;
        }
        catch (RuntimeException runtimeException) {
            Rlog.e(LOG_TAG, "CDS SMS PDU parsing failed: ", runtimeException);
            return null;
        }
    }

    public static SmsMessage newFromCMT(byte[] arrby) {
        try {
            SmsMessage smsMessage = new SmsMessage();
            smsMessage.parsePdu(arrby);
            return smsMessage;
        }
        catch (RuntimeException runtimeException) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", runtimeException);
            return null;
        }
    }

    private void parsePdu(byte[] object) {
        block4 : {
            PduParser pduParser;
            int n;
            block1 : {
                block2 : {
                    block3 : {
                        this.mPdu = object;
                        pduParser = new PduParser((byte[])object);
                        this.mScAddress = pduParser.getSCAddress();
                        object = this.mScAddress;
                        n = pduParser.getByte();
                        this.mMti = n & 3;
                        int n2 = this.mMti;
                        if (n2 == 0) break block1;
                        if (n2 == 1) break block2;
                        if (n2 == 2) break block3;
                        if (n2 != 3) {
                            throw new RuntimeException("Unsupported message type");
                        }
                        break block1;
                    }
                    this.parseSmsStatusReport(pduParser, n);
                    break block4;
                }
                this.parseSmsSubmit(pduParser, n);
                break block4;
            }
            this.parseSmsDeliver(pduParser, n);
        }
    }

    private void parseSmsDeliver(PduParser pduParser, int n) {
        boolean bl = true;
        boolean bl2 = (n & 128) == 128;
        this.mReplyPathPresent = bl2;
        SmsAddress smsAddress = this.mOriginatingAddress = pduParser.getAddress();
        this.mProtocolIdentifier = pduParser.getByte();
        this.mDataCodingScheme = pduParser.getByte();
        this.mScTimeMillis = pduParser.getSCTimestampMillis();
        bl2 = (n & 64) == 64 ? bl : false;
        this.parseUserData(pduParser, bl2);
    }

    private void parseSmsStatusReport(PduParser pduParser, int n) {
        boolean bl = true;
        this.mIsStatusReportMessage = true;
        this.mMessageRef = pduParser.getByte();
        this.mRecipientAddress = pduParser.getAddress();
        this.mScTimeMillis = pduParser.getSCTimestampMillis();
        pduParser.getSCTimestampMillis();
        this.mStatus = pduParser.getByte();
        if (pduParser.moreDataPresent()) {
            int n2;
            int n3 = n2 = pduParser.getByte();
            while ((n3 & 128) != 0) {
                n3 = pduParser.getByte();
            }
            if ((n2 & 120) == 0) {
                if ((n2 & 1) != 0) {
                    this.mProtocolIdentifier = pduParser.getByte();
                }
                if ((n2 & 2) != 0) {
                    this.mDataCodingScheme = pduParser.getByte();
                }
                if ((n2 & 4) != 0) {
                    if ((n & 64) != 64) {
                        bl = false;
                    }
                    this.parseUserData(pduParser, bl);
                }
            }
        }
    }

    private void parseSmsSubmit(PduParser pduParser, int n) {
        boolean bl = true;
        boolean bl2 = (n & 128) == 128;
        this.mReplyPathPresent = bl2;
        this.mMessageRef = pduParser.getByte();
        SmsAddress smsAddress = this.mRecipientAddress = pduParser.getAddress();
        this.mProtocolIdentifier = pduParser.getByte();
        this.mDataCodingScheme = pduParser.getByte();
        for (int i = (i = n >> 3 & 3) == 0 ? 0 : (2 == i ? 1 : 7); i > 0; --i) {
            pduParser.getByte();
        }
        bl2 = (n & 64) == 64 ? bl : false;
        this.parseUserData(pduParser, bl2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void parseUserData(PduParser var1_1, boolean var2_2) {
        block35 : {
            block33 : {
                block36 : {
                    block37 : {
                        block38 : {
                            block34 : {
                                var3_3 = 0;
                                var4_4 = 0;
                                var5_5 = 0;
                                var6_6 = this.mDataCodingScheme;
                                if ((var6_6 & 128) != 0) break block33;
                                var7_7 = (var6_6 & 32) != 0 ? 1 : 0;
                                var6_6 = (this.mDataCodingScheme & 16) != 0 ? 1 : 0;
                                var3_3 = var6_6;
                                if (var7_7 == 0) break block34;
                                var8_8 = new StringBuilder();
                                var8_8.append("4 - Unsupported SMS data coding scheme (compression) ");
                                var8_8.append(this.mDataCodingScheme & 255);
                                Rlog.w("SmsMessage", var8_8.toString());
                                var6_6 = var4_4;
                                break block35;
                            }
                            var6_6 = this.mDataCodingScheme >> 2 & 3;
                            if (var6_6 == 0) break block36;
                            if (var6_6 == 1) break block37;
                            if (var6_6 == 2) break block38;
                            if (var6_6 == 3) ** GOTO lbl-1000
                            var6_6 = var5_5;
                            break block35;
                        }
                        var6_6 = 3;
                        break block35;
                    }
                    if (Resources.getSystem().getBoolean(17891522)) {
                        var6_6 = 2;
                    } else lbl-1000: // 2 sources:
                    {
                        var8_8 = new StringBuilder();
                        var8_8.append("1 - Unsupported SMS data coding scheme ");
                        var8_8.append(this.mDataCodingScheme & 255);
                        Rlog.w("SmsMessage", var8_8.toString());
                        var6_6 = 2;
                    }
                    break block35;
                }
                var6_6 = 1;
                break block35;
            }
            if ((var6_6 & 240) == 240) {
                var3_3 = 1;
                var6_6 = (var6_6 & 4) == 0 ? 1 : 2;
            } else if ((var6_6 & 240) != 192 && (var6_6 & 240) != 208 && (var6_6 & 240) != 224) {
                if ((var6_6 & 192) == 128) {
                    if (var6_6 == 132) {
                        var6_6 = 4;
                    } else {
                        var8_8 = new StringBuilder();
                        var8_8.append("5 - Unsupported SMS data coding scheme ");
                        var8_8.append(this.mDataCodingScheme & 255);
                        Rlog.w("SmsMessage", var8_8.toString());
                        var6_6 = var4_4;
                    }
                } else {
                    var8_8 = new StringBuilder();
                    var8_8.append("3 - Unsupported SMS data coding scheme ");
                    var8_8.append(this.mDataCodingScheme & 255);
                    Rlog.w("SmsMessage", var8_8.toString());
                    var6_6 = var4_4;
                }
            } else {
                var6_6 = (this.mDataCodingScheme & 240) == 224 ? 3 : 1;
                var9_9 = (this.mDataCodingScheme & 8) == 8;
                var7_7 = this.mDataCodingScheme;
                if ((var7_7 & 3) == 0) {
                    this.mIsMwi = true;
                    this.mMwiSense = var9_9;
                    var10_10 = (var7_7 & 240) == 192;
                    this.mMwiDontStore = var10_10;
                    this.mVoiceMailCount = var9_9 == true ? -1 : 0;
                    var8_8 = new StringBuilder();
                    var8_8.append("MWI in DCS for Vmail. DCS = ");
                    var8_8.append(this.mDataCodingScheme & 255);
                    var8_8.append(" Dont store = ");
                    var8_8.append(this.mMwiDontStore);
                    var8_8.append(" vmail count = ");
                    var8_8.append(this.mVoiceMailCount);
                    Rlog.w("SmsMessage", var8_8.toString());
                } else {
                    this.mIsMwi = false;
                    var8_8 = new StringBuilder();
                    var8_8.append("MWI in DCS for fax/email/other: ");
                    var8_8.append(this.mDataCodingScheme & 255);
                    Rlog.w("SmsMessage", var8_8.toString());
                }
            }
        }
        var9_9 = var6_6 == 1;
        var5_5 = var1_1.constructUserData(var2_2, var9_9);
        this.mUserData = var1_1.getUserData();
        this.mUserDataHeader = var1_1.getUserDataHeader();
        if (var2_2 && this.mUserDataHeader.specialSmsMsgList.size() != 0) {
            for (SmsHeader.SpecialSmsMsg var11_12 : this.mUserDataHeader.specialSmsMsgList) {
                var4_4 = var11_12.msgIndType & 255;
                if (var4_4 != 0 && var4_4 != 128) {
                    var11_13 = new StringBuilder();
                    var11_13.append("TP_UDH fax/email/extended msg/multisubscriber profile. Msg Ind = ");
                    var11_13.append(var4_4);
                    Rlog.w("SmsMessage", var11_13.toString());
                    continue;
                }
                this.mIsMwi = true;
                if (var4_4 == 128) {
                    this.mMwiDontStore = false;
                } else if (!this.mMwiDontStore && (((var7_7 = this.mDataCodingScheme) & 240) != 208 && (var7_7 & 240) != 224 || (this.mDataCodingScheme & 3) != 0)) {
                    this.mMwiDontStore = true;
                }
                this.mVoiceMailCount = var11_12.msgCount & 255;
                this.mMwiSense = this.mVoiceMailCount > 0;
                var11_14 = new StringBuilder();
                var11_14.append("MWI in TP-UDH for Vmail. Msg Ind = ");
                var11_14.append(var4_4);
                var11_14.append(" Dont store = ");
                var11_14.append(this.mMwiDontStore);
                var11_14.append(" Vmail count = ");
                var11_14.append(this.mVoiceMailCount);
                Rlog.w("SmsMessage", var11_14.toString());
            }
        }
        if (var6_6 != 0) {
            if (var6_6 != 1) {
                if (var6_6 != 2) {
                    if (var6_6 != 3) {
                        if (var6_6 == 4) {
                            this.mMessageBody = var1_1.getUserDataKSC5601(var5_5);
                        }
                    } else {
                        this.mMessageBody = var1_1.getUserDataUCS2(var5_5);
                    }
                } else {
                    this.mMessageBody = Resources.getSystem().getBoolean(17891522) ? var1_1.getUserDataGSM8bit(var5_5) : null;
                }
            } else {
                var6_6 = var2_2 != false ? this.mUserDataHeader.languageTable : 0;
                var7_7 = var2_2 != false ? this.mUserDataHeader.languageShiftTable : 0;
                this.mMessageBody = var1_1.getUserDataGSM7Bit(var5_5, var6_6, var7_7);
            }
        } else {
            this.mMessageBody = null;
        }
        if (this.mMessageBody != null) {
            this.parseMessageBody();
        }
        if (var3_3 == 0) {
            this.messageClass = SmsConstants.MessageClass.UNKNOWN;
            return;
        }
        var6_6 = this.mDataCodingScheme & 3;
        if (var6_6 == 0) {
            this.messageClass = SmsConstants.MessageClass.CLASS_0;
            return;
        }
        if (var6_6 == 1) {
            this.messageClass = SmsConstants.MessageClass.CLASS_1;
            return;
        }
        if (var6_6 == 2) {
            this.messageClass = SmsConstants.MessageClass.CLASS_2;
            return;
        }
        if (var6_6 != 3) {
            return;
        }
        this.messageClass = SmsConstants.MessageClass.CLASS_3;
    }

    int getDataCodingScheme() {
        return this.mDataCodingScheme;
    }

    @Override
    public SmsConstants.MessageClass getMessageClass() {
        return this.messageClass;
    }

    public int getNumOfVoicemails() {
        if (!this.mIsMwi && this.isCphsMwiMessage()) {
            this.mVoiceMailCount = this.mOriginatingAddress != null && ((GsmSmsAddress)this.mOriginatingAddress).isCphsVoiceMessageSet() ? 255 : 0;
            Rlog.v(LOG_TAG, "CPHS voice mail message");
        }
        return this.mVoiceMailCount;
    }

    @Override
    public int getProtocolIdentifier() {
        return this.mProtocolIdentifier;
    }

    @Override
    public int getStatus() {
        return this.mStatus;
    }

    @Override
    public boolean isCphsMwiMessage() {
        boolean bl = ((GsmSmsAddress)this.mOriginatingAddress).isCphsVoiceMessageClear() || ((GsmSmsAddress)this.mOriginatingAddress).isCphsVoiceMessageSet();
        return bl;
    }

    @Override
    public boolean isMWIClearMessage() {
        boolean bl = this.mIsMwi;
        boolean bl2 = true;
        if (bl && !this.mMwiSense) {
            return true;
        }
        if (this.mOriginatingAddress == null || !((GsmSmsAddress)this.mOriginatingAddress).isCphsVoiceMessageClear()) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public boolean isMWISetMessage() {
        boolean bl = this.mIsMwi;
        boolean bl2 = true;
        if (bl && this.mMwiSense) {
            return true;
        }
        if (this.mOriginatingAddress == null || !((GsmSmsAddress)this.mOriginatingAddress).isCphsVoiceMessageSet()) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public boolean isMwiDontStore() {
        if (this.mIsMwi && this.mMwiDontStore) {
            return true;
        }
        return this.isCphsMwiMessage() && " ".equals(this.getMessageBody());
    }

    @Override
    public boolean isReplace() {
        int n = this.mProtocolIdentifier;
        boolean bl = (n & 192) == 64 && (n & 63) > 0 && (n & 63) < 8;
        return bl;
    }

    @Override
    public boolean isReplyPathPresent() {
        return this.mReplyPathPresent;
    }

    @Override
    public boolean isStatusReportMessage() {
        return this.mIsStatusReportMessage;
    }

    public boolean isTypeZero() {
        boolean bl = this.mProtocolIdentifier == 64;
        return bl;
    }

    boolean isUsimDataDownload() {
        int n;
        boolean bl = this.messageClass == SmsConstants.MessageClass.CLASS_2 && ((n = this.mProtocolIdentifier) == 127 || n == 124);
        return bl;
    }

    private static class PduParser {
        int mCur;
        byte[] mPdu;
        byte[] mUserData;
        SmsHeader mUserDataHeader;
        int mUserDataSeptetPadding;

        PduParser(byte[] arrby) {
            this.mPdu = arrby;
            this.mCur = 0;
            this.mUserDataSeptetPadding = 0;
        }

        int constructUserData(boolean bl, boolean bl2) {
            int n;
            byte[] arrby;
            int n2 = this.mCur;
            byte[] arrby2 = this.mPdu;
            int n3 = n2 + 1;
            int n4 = arrby2[n2] & 255;
            int n5 = 0;
            n2 = 0;
            int n6 = 0;
            if (bl) {
                n2 = n3 + 1;
                n = arrby2[n3] & 255;
                arrby = new byte[n];
                System.arraycopy(arrby2, n2, arrby, 0, n);
                this.mUserDataHeader = SmsHeader.fromByteArray(arrby);
                n3 = n2 + n;
                int n7 = (n + 1) * 8;
                n5 = n7 / 7;
                n2 = n7 % 7 > 0 ? 1 : 0;
                this.mUserDataSeptetPadding = (n5 += n2) * 7 - n7;
                n2 = n;
            }
            if (bl2) {
                n2 = this.mPdu.length - n3;
            } else {
                n2 = bl ? ++n2 : 0;
                n2 = n = n4 - n2;
                if (n < 0) {
                    n2 = 0;
                }
            }
            this.mUserData = new byte[n2];
            arrby = this.mPdu;
            arrby2 = this.mUserData;
            System.arraycopy(arrby, n3, arrby2, 0, arrby2.length);
            this.mCur = n3;
            if (bl2) {
                n2 = n4 - n5;
                if (n2 < 0) {
                    n2 = n6;
                }
                return n2;
            }
            return this.mUserData.length;
        }

        GsmSmsAddress getAddress() {
            Object object = this.mPdu;
            int n = this.mCur;
            int n2 = ((object[n] & 255) + 1) / 2 + 2;
            try {
                object = new GsmSmsAddress((byte[])object, n, n2);
                this.mCur += n2;
                return object;
            }
            catch (ParseException parseException) {
                throw new RuntimeException(parseException.getMessage());
            }
        }

        int getByte() {
            byte[] arrby = this.mPdu;
            int n = this.mCur;
            this.mCur = n + 1;
            return arrby[n] & 255;
        }

        String getSCAddress() {
            String string2;
            int n = this.getByte();
            if (n == 0) {
                string2 = null;
            } else {
                try {
                    string2 = PhoneNumberUtils.calledPartyBCDToString(this.mPdu, this.mCur, n, 2);
                }
                catch (RuntimeException runtimeException) {
                    Rlog.d(SmsMessage.LOG_TAG, "invalid SC address: ", runtimeException);
                    string2 = null;
                }
            }
            this.mCur += n;
            return string2;
        }

        long getSCTimestampMillis() {
            Object object = this.mPdu;
            int n = this.mCur;
            this.mCur = n + 1;
            int n2 = IccUtils.gsmBcdByteToInt(object[n]);
            object = this.mPdu;
            n = this.mCur;
            this.mCur = n + 1;
            int n3 = IccUtils.gsmBcdByteToInt(object[n]);
            object = this.mPdu;
            n = this.mCur;
            this.mCur = n + 1;
            int n4 = IccUtils.gsmBcdByteToInt(object[n]);
            object = this.mPdu;
            n = this.mCur;
            this.mCur = n + 1;
            int n5 = IccUtils.gsmBcdByteToInt(object[n]);
            object = this.mPdu;
            n = this.mCur;
            this.mCur = n + 1;
            int n6 = IccUtils.gsmBcdByteToInt(object[n]);
            object = this.mPdu;
            n = this.mCur;
            this.mCur = n + 1;
            int n7 = IccUtils.gsmBcdByteToInt(object[n]);
            object = this.mPdu;
            n = this.mCur;
            this.mCur = n + 1;
            byte by = object[n];
            n = IccUtils.gsmBcdByteToInt((byte)(by & -9));
            if ((by & 8) != 0) {
                n = -n;
            }
            object = new Time("UTC");
            n2 = n2 >= 90 ? (n2 += 1900) : (n2 += 2000);
            ((Time)object).year = n2;
            ((Time)object).month = n3 - 1;
            ((Time)object).monthDay = n4;
            ((Time)object).hour = n5;
            ((Time)object).minute = n6;
            ((Time)object).second = n7;
            return ((Time)object).toMillis(true) - (long)(n * 15 * 60 * 1000);
        }

        byte[] getUserData() {
            return this.mUserData;
        }

        String getUserDataGSM7Bit(int n, int n2, int n3) {
            String string2 = GsmAlphabet.gsm7BitPackedToString(this.mPdu, this.mCur, n, this.mUserDataSeptetPadding, n2, n3);
            this.mCur += n * 7 / 8;
            return string2;
        }

        String getUserDataGSM8bit(int n) {
            String string2 = GsmAlphabet.gsm8BitUnpackedToString(this.mPdu, this.mCur, n);
            this.mCur += n;
            return string2;
        }

        SmsHeader getUserDataHeader() {
            return this.mUserDataHeader;
        }

        String getUserDataKSC5601(int n) {
            String string2;
            try {
                string2 = new String(this.mPdu, this.mCur, n, "KSC5601");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                Rlog.e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", unsupportedEncodingException);
                string2 = "";
            }
            this.mCur += n;
            return string2;
        }

        String getUserDataUCS2(int n) {
            String string2;
            try {
                string2 = new String(this.mPdu, this.mCur, n, "utf-16");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                Rlog.e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", unsupportedEncodingException);
                string2 = "";
            }
            this.mCur += n;
            return string2;
        }

        boolean moreDataPresent() {
            boolean bl = this.mPdu.length > this.mCur;
            return bl;
        }
    }

    public static class SubmitPdu
    extends SmsMessageBase.SubmitPduBase {
    }

}

