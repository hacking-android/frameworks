/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cdma.sms;

import android.content.res.Resources;
import android.telephony.Rlog;
import android.telephony.SmsCbCmasInfo;
import android.telephony.cdma.CdmaSmsCbProgramData;
import android.telephony.cdma.CdmaSmsCbProgramResults;
import android.text.format.Time;
import android.util.SparseIntArray;
import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.cdma.sms.CdmaSmsAddress;
import com.android.internal.telephony.cdma.sms.UserData;
import com.android.internal.telephony.gsm.SmsMessage;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.util.BitwiseInputStream;
import com.android.internal.util.BitwiseOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimeZone;

public final class BearerData {
    public static final int ALERT_DEFAULT = 0;
    public static final int ALERT_HIGH_PRIO = 3;
    public static final int ALERT_LOW_PRIO = 1;
    public static final int ALERT_MEDIUM_PRIO = 2;
    public static final int DISPLAY_MODE_DEFAULT = 1;
    public static final int DISPLAY_MODE_IMMEDIATE = 0;
    public static final int DISPLAY_MODE_USER = 2;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_PERMANENT = 3;
    public static final int ERROR_TEMPORARY = 2;
    public static final int ERROR_UNDEFINED = 255;
    public static final int LANGUAGE_CHINESE = 6;
    public static final int LANGUAGE_ENGLISH = 1;
    public static final int LANGUAGE_FRENCH = 2;
    public static final int LANGUAGE_HEBREW = 7;
    public static final int LANGUAGE_JAPANESE = 4;
    public static final int LANGUAGE_KOREAN = 5;
    public static final int LANGUAGE_SPANISH = 3;
    public static final int LANGUAGE_UNKNOWN = 0;
    private static final String LOG_TAG = "BearerData";
    public static final int MESSAGE_TYPE_CANCELLATION = 3;
    public static final int MESSAGE_TYPE_DELIVER = 1;
    public static final int MESSAGE_TYPE_DELIVERY_ACK = 4;
    public static final int MESSAGE_TYPE_DELIVER_REPORT = 7;
    public static final int MESSAGE_TYPE_READ_ACK = 6;
    public static final int MESSAGE_TYPE_SUBMIT = 2;
    public static final int MESSAGE_TYPE_SUBMIT_REPORT = 8;
    public static final int MESSAGE_TYPE_USER_ACK = 5;
    public static final int PRIORITY_EMERGENCY = 3;
    public static final int PRIORITY_INTERACTIVE = 1;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_URGENT = 2;
    public static final int PRIVACY_CONFIDENTIAL = 2;
    public static final int PRIVACY_NOT_RESTRICTED = 0;
    public static final int PRIVACY_RESTRICTED = 1;
    public static final int PRIVACY_SECRET = 3;
    public static final int RELATIVE_TIME_DAYS_LIMIT = 196;
    public static final int RELATIVE_TIME_HOURS_LIMIT = 167;
    public static final int RELATIVE_TIME_INDEFINITE = 245;
    public static final int RELATIVE_TIME_MINS_LIMIT = 143;
    public static final int RELATIVE_TIME_MOBILE_INACTIVE = 247;
    public static final int RELATIVE_TIME_NOW = 246;
    public static final int RELATIVE_TIME_RESERVED = 248;
    public static final int RELATIVE_TIME_WEEKS_LIMIT = 244;
    public static final int STATUS_ACCEPTED = 0;
    public static final int STATUS_BLOCKED_DESTINATION = 7;
    public static final int STATUS_CANCELLED = 3;
    public static final int STATUS_CANCEL_FAILED = 6;
    public static final int STATUS_DELIVERED = 2;
    public static final int STATUS_DEPOSITED_TO_INTERNET = 1;
    public static final int STATUS_DUPLICATE_MESSAGE = 9;
    public static final int STATUS_INVALID_DESTINATION = 10;
    public static final int STATUS_MESSAGE_EXPIRED = 13;
    public static final int STATUS_NETWORK_CONGESTION = 4;
    public static final int STATUS_NETWORK_ERROR = 5;
    public static final int STATUS_TEXT_TOO_LONG = 8;
    public static final int STATUS_UNDEFINED = 255;
    public static final int STATUS_UNKNOWN_ERROR = 31;
    private static final byte SUBPARAM_ALERT_ON_MESSAGE_DELIVERY = 12;
    private static final byte SUBPARAM_CALLBACK_NUMBER = 14;
    private static final byte SUBPARAM_DEFERRED_DELIVERY_TIME_ABSOLUTE = 6;
    private static final byte SUBPARAM_DEFERRED_DELIVERY_TIME_RELATIVE = 7;
    private static final byte SUBPARAM_ID_LAST_DEFINED = 23;
    private static final byte SUBPARAM_LANGUAGE_INDICATOR = 13;
    private static final byte SUBPARAM_MESSAGE_CENTER_TIME_STAMP = 3;
    private static final byte SUBPARAM_MESSAGE_DEPOSIT_INDEX = 17;
    private static final byte SUBPARAM_MESSAGE_DISPLAY_MODE = 15;
    private static final byte SUBPARAM_MESSAGE_IDENTIFIER = 0;
    private static final byte SUBPARAM_MESSAGE_STATUS = 20;
    private static final byte SUBPARAM_NUMBER_OF_MESSAGES = 11;
    private static final byte SUBPARAM_PRIORITY_INDICATOR = 8;
    private static final byte SUBPARAM_PRIVACY_INDICATOR = 9;
    private static final byte SUBPARAM_REPLY_OPTION = 10;
    private static final byte SUBPARAM_SERVICE_CATEGORY_PROGRAM_DATA = 18;
    private static final byte SUBPARAM_SERVICE_CATEGORY_PROGRAM_RESULTS = 19;
    private static final byte SUBPARAM_USER_DATA = 1;
    private static final byte SUBPARAM_USER_RESPONSE_CODE = 2;
    private static final byte SUBPARAM_VALIDITY_PERIOD_ABSOLUTE = 4;
    private static final byte SUBPARAM_VALIDITY_PERIOD_RELATIVE = 5;
    public int alert = 0;
    public boolean alertIndicatorSet = false;
    public CdmaSmsAddress callbackNumber;
    public SmsCbCmasInfo cmasWarningInfo;
    public TimeStamp deferredDeliveryTimeAbsolute;
    public int deferredDeliveryTimeRelative;
    public boolean deferredDeliveryTimeRelativeSet;
    public boolean deliveryAckReq;
    public int depositIndex;
    public int displayMode = 1;
    public boolean displayModeSet = false;
    public int errorClass = 255;
    public boolean hasUserDataHeader;
    public int language = 0;
    public boolean languageIndicatorSet = false;
    public int messageId;
    public int messageStatus = 255;
    public boolean messageStatusSet = false;
    public int messageType;
    public TimeStamp msgCenterTimeStamp;
    public int numberOfMessages;
    public int priority = 0;
    public boolean priorityIndicatorSet = false;
    public int privacy = 0;
    public boolean privacyIndicatorSet = false;
    public boolean readAckReq;
    public boolean reportReq;
    public ArrayList<CdmaSmsCbProgramData> serviceCategoryProgramData;
    public ArrayList<CdmaSmsCbProgramResults> serviceCategoryProgramResults;
    public boolean userAckReq;
    public UserData userData;
    public int userResponseCode;
    public boolean userResponseCodeSet = false;
    public TimeStamp validityPeriodAbsolute;
    public int validityPeriodRelative;
    public boolean validityPeriodRelativeSet;

    public static GsmAlphabet.TextEncodingDetails calcTextEncodingDetails(CharSequence charSequence, boolean bl, boolean bl2) {
        GsmAlphabet.TextEncodingDetails textEncodingDetails;
        int n = BearerData.countAsciiSeptets(charSequence, bl);
        if (n != -1 && n <= 160) {
            textEncodingDetails = new GsmAlphabet.TextEncodingDetails();
            textEncodingDetails.msgCount = 1;
            textEncodingDetails.codeUnitCount = n;
            textEncodingDetails.codeUnitsRemaining = 160 - n;
            textEncodingDetails.codeUnitSize = 1;
        } else {
            GsmAlphabet.TextEncodingDetails textEncodingDetails2;
            textEncodingDetails = textEncodingDetails2 = SmsMessage.calculateLength(charSequence, bl);
            if (textEncodingDetails2.msgCount == 1) {
                textEncodingDetails = textEncodingDetails2;
                if (textEncodingDetails2.codeUnitSize == 1) {
                    textEncodingDetails = textEncodingDetails2;
                    if (bl2) {
                        return SmsMessageBase.calcUnicodeEncodingDetails(charSequence);
                    }
                }
            }
        }
        return textEncodingDetails;
    }

    private static int countAsciiSeptets(CharSequence charSequence, boolean bl) {
        int n = charSequence.length();
        if (bl) {
            return n;
        }
        for (int i = 0; i < n; ++i) {
            if (UserData.charToAscii.get(charSequence.charAt(i), -1) != -1) continue;
            return -1;
        }
        return n;
    }

    public static BearerData decode(byte[] arrby) {
        return BearerData.decode(arrby, 0);
    }

    /*
     * Exception decompiling
     */
    public static BearerData decode(byte[] var0, int var1_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 26[WHILELOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String decode7bitAscii(byte[] var0, int var1_2, int var2_3) throws CodingException {
        try {
            var1_2 = (var1_2 * 8 + 6) / 7;
            var3_4 = new StringBuffer(var2_3 -= var1_2);
            var4_5 = new BitwiseInputStream((byte[])var0);
            var5_6 = var1_2 * 7 + var2_3 * 7;
            if (var4_5.available() < var5_6) ** GOTO lbl-1000
            var4_5.skip(var1_2 * 7);
            ** GOTO lbl22
lbl-1000: // 1 sources:
            {
                var0 = new StringBuilder();
                var0.append("insufficient data (wanted ");
                var0.append(var5_6);
                var0.append(" bits, but only have ");
                var0.append(var4_5.available());
                var0.append(")");
                var3_4 = new CodingException(var0.toString());
                throw var3_4;
lbl22: // 5 sources:
                for (var1_2 = 0; var1_2 < var2_3; ++var1_2) {
                    var5_6 = var4_5.read(7);
                    if (var5_6 >= 32 && var5_6 <= UserData.ASCII_MAP_MAX_INDEX) {
                        var3_4.append(UserData.ASCII_MAP[var5_6 - 32]);
                        continue;
                    }
                    if (var5_6 == 10) {
                        var3_4.append('\n');
                        continue;
                    }
                    if (var5_6 == 13) {
                        var3_4.append('\r');
                        continue;
                    }
                    var3_4.append(' ');
                }
            }
        }
lbl39: // 2 sources:
        catch (BitwiseInputStream.AccessException var0_1) {
            var4_5 = new StringBuilder();
            var4_5.append("7bit ASCII decode failed: ");
            var4_5.append(var0_1);
            throw new CodingException(var4_5.toString());
        }
        {
            ** try [egrp 3[TRYBLOCK] [8 : 160->228)] { 
lbl47: // 1 sources:
            return var3_4.toString();
        }
    }

    private static String decode7bitGsm(byte[] object, int n, int n2) throws CodingException {
        int n3 = n * 8;
        int n4 = (n3 + 6) / 7;
        if ((object = GsmAlphabet.gsm7BitPackedToString(object, n, n2 - n4, n4 * 7 - n3, 0, 0)) != null) {
            return object;
        }
        throw new CodingException("7bit GSM decoding failed");
    }

    private static boolean decodeCallbackNumber(BearerData object, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException, CodingException {
        int n = bitwiseInputStream.read(8) * 8;
        if (n < 8) {
            bitwiseInputStream.skip(n);
            return false;
        }
        CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
        cdmaSmsAddress.digitMode = bitwiseInputStream.read(1);
        int n2 = 4;
        int n3 = 1;
        if (cdmaSmsAddress.digitMode == 1) {
            cdmaSmsAddress.ton = bitwiseInputStream.read(3);
            cdmaSmsAddress.numberPlan = bitwiseInputStream.read(4);
            n2 = 8;
            n3 = (byte)(1 + 7);
        }
        cdmaSmsAddress.numberOfDigits = bitwiseInputStream.read(8);
        n3 = n - (byte)(n3 + 8);
        n2 = cdmaSmsAddress.numberOfDigits * n2;
        n = n3 - n2;
        if (n3 >= n2) {
            cdmaSmsAddress.origBytes = bitwiseInputStream.readByteArray(n2);
            bitwiseInputStream.skip(n);
            BearerData.decodeSmsAddress(cdmaSmsAddress);
            ((BearerData)object).callbackNumber = cdmaSmsAddress;
            return true;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("CALLBACK_NUMBER subparam encoding size error (remainingBits + ");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(", dataBits + ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(", paddingBits + ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(")");
        throw new CodingException(((StringBuilder)object).toString());
    }

    private static String decodeCharset(byte[] object, int n, int n2, int n3, String string2) throws CodingException {
        block6 : {
            int n4;
            StringBuilder stringBuilder;
            block5 : {
                block4 : {
                    if (n2 < 0) break block4;
                    n4 = n2;
                    if (n2 * n3 + n <= ((byte[])object).length) break block5;
                }
                if ((n4 = (((byte[])object).length - n - n % n3) / n3) < 0) break block6;
                stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(" decode error: offset = ");
                stringBuilder.append(n);
                stringBuilder.append(" numFields = ");
                stringBuilder.append(n2);
                stringBuilder.append(" data.length = ");
                stringBuilder.append(((Object)object).length);
                stringBuilder.append(" maxNumFields = ");
                stringBuilder.append(n4);
                Rlog.e(LOG_TAG, stringBuilder.toString());
            }
            try {
                object = new String((byte[])object, n, n4 * n3, string2);
                return object;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(" decode failed: ");
                stringBuilder.append(unsupportedEncodingException);
                throw new CodingException(stringBuilder.toString());
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" decode failed: offset out of range");
        throw new CodingException(((StringBuilder)object).toString());
    }

    private static void decodeCmasUserData(BearerData object, int n) throws BitwiseInputStream.AccessException, CodingException {
        block3 : {
            int n2;
            block4 : {
                BitwiseInputStream bitwiseInputStream = new BitwiseInputStream(object.userData.payload);
                if (bitwiseInputStream.available() < 8) break block3;
                n2 = bitwiseInputStream.read(8);
                if (n2 != 0) break block4;
                int n3 = BearerData.serviceCategoryToCmasMessageClass(n);
                int n4 = -1;
                int n5 = -1;
                int n6 = -1;
                int n7 = -1;
                n2 = -1;
                while (bitwiseInputStream.available() >= 16) {
                    Object object2;
                    int n8;
                    block8 : {
                        block5 : {
                            block6 : {
                                block7 : {
                                    n = bitwiseInputStream.read(8);
                                    n8 = bitwiseInputStream.read(8);
                                    if (n != 0) {
                                        if (n != 1) {
                                            object2 = new StringBuilder();
                                            ((StringBuilder)object2).append("skipping unsupported CMAS record type ");
                                            ((StringBuilder)object2).append(n);
                                            Rlog.w(LOG_TAG, ((StringBuilder)object2).toString());
                                            bitwiseInputStream.skip(n8 * 8);
                                            continue;
                                        }
                                        n4 = bitwiseInputStream.read(8);
                                        n5 = bitwiseInputStream.read(8);
                                        n6 = bitwiseInputStream.read(4);
                                        n7 = bitwiseInputStream.read(4);
                                        n2 = bitwiseInputStream.read(4);
                                        bitwiseInputStream.skip(n8 * 8 - 28);
                                        continue;
                                    }
                                    object2 = new UserData();
                                    ((UserData)object2).msgEncoding = bitwiseInputStream.read(5);
                                    ((UserData)object2).msgEncodingSet = true;
                                    ((UserData)object2).msgType = 0;
                                    n = ((UserData)object2).msgEncoding;
                                    if (n == 0) break block5;
                                    if (n == 2 || n == 3) break block6;
                                    if (n == 4) break block7;
                                    if (n == 8) break block5;
                                    if (n == 9) break block6;
                                    n = 0;
                                    break block8;
                                }
                                n = (n8 - 1) / 2;
                                break block8;
                            }
                            n = (n8 * 8 - 5) / 7;
                            break block8;
                        }
                        n = n8 - 1;
                    }
                    ((UserData)object2).numFields = n;
                    ((UserData)object2).payload = bitwiseInputStream.readByteArray(n8 * 8 - 5);
                    BearerData.decodeUserDataPayload((UserData)object2, false);
                    ((BearerData)object).userData = object2;
                }
                ((BearerData)object).cmasWarningInfo = new SmsCbCmasInfo(n3, n4, n5, n6, n7, n2);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unsupported CMAE_protocol_version ");
            ((StringBuilder)object).append(n2);
            throw new CodingException(((StringBuilder)object).toString());
        }
        throw new CodingException("emergency CB with no CMAE_protocol_version");
    }

    private static boolean decodeDeferredDeliveryAbs(BearerData object, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 48) {
            n2 = n - 48;
            bl = true;
            ((BearerData)object).deferredDeliveryTimeAbsolute = TimeStamp.fromByteArray(bitwiseInputStream.readByteArray(48));
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DEFERRED_DELIVERY_TIME_ABSOLUTE decode ");
            object = bl ? "succeeded" : "failed";
            stringBuilder.append((String)object);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        return bl;
    }

    private static boolean decodeDeferredDeliveryRel(BearerData bearerData, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            bl = true;
            bearerData.validityPeriodRelative = bitwiseInputStream.read(8);
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DEFERRED_DELIVERY_TIME_RELATIVE decode ");
            String string2 = bl ? "succeeded" : "failed";
            stringBuilder.append(string2);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        bearerData.validityPeriodRelativeSet = bl;
        return bl;
    }

    private static boolean decodeDepositIndex(BearerData object, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 16) {
            n2 = n - 16;
            bl = true;
            n = bitwiseInputStream.read(8);
            ((BearerData)object).depositIndex = bitwiseInputStream.read(8) | n << 8;
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MESSAGE_DEPOSIT_INDEX decode ");
            object = bl ? "succeeded" : "failed";
            stringBuilder.append((String)object);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        return bl;
    }

    private static boolean decodeDisplayMode(BearerData bearerData, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            bl = true;
            bearerData.displayMode = bitwiseInputStream.read(2);
            bitwiseInputStream.skip(6);
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DISPLAY_MODE decode ");
            String string2 = bl ? "succeeded" : "failed";
            stringBuilder.append(string2);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        bearerData.displayModeSet = bl;
        return bl;
    }

    private static String decodeDtmfSmsAddress(byte[] object, int n) throws CodingException {
        StringBuffer stringBuffer = new StringBuffer(n);
        for (int i = 0; i < n; ++i) {
            int n2 = object[i / 2] >>> 4 - i % 2 * 4 & 15;
            if (n2 >= 1 && n2 <= 9) {
                stringBuffer.append(Integer.toString(n2, 10));
                continue;
            }
            if (n2 == 10) {
                stringBuffer.append('0');
                continue;
            }
            if (n2 == 11) {
                stringBuffer.append('*');
                continue;
            }
            if (n2 == 12) {
                stringBuffer.append('#');
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("invalid SMS address DTMF code (");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(")");
            throw new CodingException(((StringBuilder)object).toString());
        }
        return stringBuffer.toString();
    }

    private static String decodeGsmDcs(byte[] object, int n, int n2, int n3) throws CodingException {
        if ((n3 & 192) == 0) {
            int n4 = n3 >> 2 & 3;
            if (n4 != 0) {
                if (n4 != 1) {
                    if (n4 == 2) {
                        return BearerData.decodeUtf16((byte[])object, n, n2);
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("unsupported user msgType encoding (");
                    ((StringBuilder)object).append(n3);
                    ((StringBuilder)object).append(")");
                    throw new CodingException(((StringBuilder)object).toString());
                }
                return BearerData.decodeUtf8((byte[])object, n, n2);
            }
            return BearerData.decode7bitGsm((byte[])object, n, n2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("unsupported coding group (");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(")");
        throw new CodingException(((StringBuilder)object).toString());
    }

    private static void decodeIs91(BearerData bearerData) throws BitwiseInputStream.AccessException, CodingException {
        switch (bearerData.userData.msgType) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unsupported IS-91 message type (");
                stringBuilder.append(bearerData.userData.msgType);
                stringBuilder.append(")");
                throw new CodingException(stringBuilder.toString());
            }
            case 132: {
                BearerData.decodeIs91Cli(bearerData);
                break;
            }
            case 131: 
            case 133: {
                BearerData.decodeIs91ShortMessage(bearerData);
                break;
            }
            case 130: {
                BearerData.decodeIs91VoicemailStatus(bearerData);
            }
        }
    }

    private static void decodeIs91Cli(BearerData bearerData) throws CodingException {
        int n = new BitwiseInputStream(bearerData.userData.payload).available() / 4;
        int n2 = bearerData.userData.numFields;
        if (n <= 14 && n >= 3 && n >= n2) {
            CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
            cdmaSmsAddress.digitMode = 0;
            cdmaSmsAddress.origBytes = bearerData.userData.payload;
            cdmaSmsAddress.numberOfDigits = (byte)n2;
            BearerData.decodeSmsAddress(cdmaSmsAddress);
            bearerData.callbackNumber = cdmaSmsAddress;
            return;
        }
        throw new CodingException("IS-91 voicemail status decoding failed");
    }

    private static void decodeIs91ShortMessage(BearerData bearerData) throws BitwiseInputStream.AccessException, CodingException {
        BitwiseInputStream bitwiseInputStream = new BitwiseInputStream(bearerData.userData.payload);
        int n = bitwiseInputStream.available() / 6;
        int n2 = bearerData.userData.numFields;
        if (n2 <= 14 && n >= n2) {
            StringBuffer stringBuffer = new StringBuffer(n);
            for (n = 0; n < n2; ++n) {
                stringBuffer.append(UserData.ASCII_MAP[bitwiseInputStream.read(6)]);
            }
            bearerData.userData.payloadStr = stringBuffer.toString();
            return;
        }
        throw new CodingException("IS-91 short message decoding failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void decodeIs91VoicemailStatus(BearerData object) throws BitwiseInputStream.AccessException, CodingException {
        BitwiseInputStream bitwiseInputStream = new BitwiseInputStream(object.userData.payload);
        int n = bitwiseInputStream.available() / 6;
        int n2 = object.userData.numFields;
        if (n <= 14 && n >= 3 && n >= n2) {
            try {
                Object object2 = new StringBuffer(n);
                while (bitwiseInputStream.available() >= 6) {
                    ((StringBuffer)object2).append(UserData.ASCII_MAP[bitwiseInputStream.read(6)]);
                }
                object2 = ((StringBuffer)object2).toString();
                ((BearerData)object).numberOfMessages = Integer.parseInt(((String)object2).substring(0, 2));
                char c = ((String)object2).charAt(2);
                if (c == ' ') {
                    ((BearerData)object).priority = 0;
                } else {
                    if (c != '!') {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("IS-91 voicemail status decoding failed: illegal priority setting (");
                        ((StringBuilder)object).append(c);
                        ((StringBuilder)object).append(")");
                        object2 = new CodingException(((StringBuilder)object).toString());
                        throw object2;
                    }
                    ((BearerData)object).priority = 2;
                }
                ((BearerData)object).priorityIndicatorSet = true;
                object.userData.payloadStr = ((String)object2).substring(3, n2 - 3);
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("IS-91 voicemail status decoding failed: ");
                stringBuilder.append(indexOutOfBoundsException);
                throw new CodingException(stringBuilder.toString());
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("IS-91 voicemail status decoding failed: ");
                stringBuilder.append(numberFormatException);
                throw new CodingException(stringBuilder.toString());
            }
        }
        throw new CodingException("IS-91 voicemail status decoding failed");
    }

    private static boolean decodeLanguageIndicator(BearerData bearerData, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            bl = true;
            bearerData.language = bitwiseInputStream.read(8);
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("LANGUAGE_INDICATOR decode ");
            String string2 = bl ? "succeeded" : "failed";
            stringBuilder.append(string2);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        bearerData.languageIndicatorSet = bl;
        return bl;
    }

    private static String decodeLatin(byte[] arrby, int n, int n2) throws CodingException {
        return BearerData.decodeCharset(arrby, n, n2, 1, "ISO-8859-1");
    }

    private static boolean decodeMessageId(BearerData object, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 24) {
            n2 = n - 24;
            boolean bl2 = true;
            ((BearerData)object).messageType = bitwiseInputStream.read(4);
            n = ((BearerData)object).messageId = bitwiseInputStream.read(8) << 8;
            ((BearerData)object).messageId = bitwiseInputStream.read(8) | n;
            bl = true;
            if (bitwiseInputStream.read(1) != 1) {
                bl = false;
            }
            ((BearerData)object).hasUserDataHeader = bl;
            bitwiseInputStream.skip(3);
            bl = bl2;
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MESSAGE_IDENTIFIER decode ");
            object = bl ? "succeeded" : "failed";
            stringBuilder.append((String)object);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        return bl;
    }

    private static boolean decodeMsgCenterTimeStamp(BearerData object, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 48) {
            n2 = n - 48;
            bl = true;
            ((BearerData)object).msgCenterTimeStamp = TimeStamp.fromByteArray(bitwiseInputStream.readByteArray(48));
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MESSAGE_CENTER_TIME_STAMP decode ");
            object = bl ? "succeeded" : "failed";
            stringBuilder.append((String)object);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        return bl;
    }

    private static boolean decodeMsgCount(BearerData object, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            bl = true;
            ((BearerData)object).numberOfMessages = IccUtils.cdmaBcdByteToInt((byte)bitwiseInputStream.read(8));
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NUMBER_OF_MESSAGES decode ");
            object = bl ? "succeeded" : "failed";
            stringBuilder.append((String)object);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        return bl;
    }

    private static boolean decodeMsgDeliveryAlert(BearerData bearerData, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            bl = true;
            bearerData.alert = bitwiseInputStream.read(2);
            bitwiseInputStream.skip(6);
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ALERT_ON_MESSAGE_DELIVERY decode ");
            String string2 = bl ? "succeeded" : "failed";
            stringBuilder.append(string2);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        bearerData.alertIndicatorSet = bl;
        return bl;
    }

    private static boolean decodeMsgStatus(BearerData bearerData, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            bl = true;
            bearerData.errorClass = bitwiseInputStream.read(2);
            bearerData.messageStatus = bitwiseInputStream.read(6);
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MESSAGE_STATUS decode ");
            String string2 = bl ? "succeeded" : "failed";
            stringBuilder.append(string2);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        bearerData.messageStatusSet = bl;
        return bl;
    }

    private static boolean decodePriorityIndicator(BearerData bearerData, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            bl = true;
            bearerData.priority = bitwiseInputStream.read(2);
            bitwiseInputStream.skip(6);
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PRIORITY_INDICATOR decode ");
            String string2 = bl ? "succeeded" : "failed";
            stringBuilder.append(string2);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        bearerData.priorityIndicatorSet = bl;
        return bl;
    }

    private static boolean decodePrivacyIndicator(BearerData bearerData, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            bl = true;
            bearerData.privacy = bitwiseInputStream.read(2);
            bitwiseInputStream.skip(6);
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PRIVACY_INDICATOR decode ");
            String string2 = bl ? "succeeded" : "failed";
            stringBuilder.append(string2);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        bearerData.privacyIndicatorSet = bl;
        return bl;
    }

    private static boolean decodeReplyOption(BearerData object, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            boolean bl2 = true;
            boolean bl3 = true;
            bl = bitwiseInputStream.read(1) == 1;
            ((BearerData)object).userAckReq = bl;
            bl = bitwiseInputStream.read(1) == 1;
            ((BearerData)object).deliveryAckReq = bl;
            bl = bitwiseInputStream.read(1) == 1;
            ((BearerData)object).readAckReq = bl;
            bl = bitwiseInputStream.read(1) == 1 ? bl3 : false;
            ((BearerData)object).reportReq = bl;
            bitwiseInputStream.skip(4);
            bl = bl2;
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("REPLY_OPTION decode ");
            object = bl ? "succeeded" : "failed";
            stringBuilder.append((String)object);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        return bl;
    }

    private static boolean decodeReserved(BearerData object, BitwiseInputStream object2, int n) throws BitwiseInputStream.AccessException, CodingException {
        boolean bl = false;
        int n2 = ((BitwiseInputStream)object2).read(8);
        int n3 = n2 * 8;
        if (n3 <= ((BitwiseInputStream)object2).available()) {
            bl = true;
            ((BitwiseInputStream)object2).skip(n3);
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("RESERVED bearer data subparameter ");
        ((StringBuilder)object2).append(n);
        ((StringBuilder)object2).append(" decode ");
        object = bl ? "succeeded" : "failed";
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(" (param bits = ");
        ((StringBuilder)object2).append(n3);
        ((StringBuilder)object2).append(")");
        Rlog.d(LOG_TAG, ((StringBuilder)object2).toString());
        if (bl) {
            return bl;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("RESERVED bearer data subparameter ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" had invalid SUBPARAM_LEN ");
        ((StringBuilder)object).append(n2);
        throw new CodingException(((StringBuilder)object).toString());
    }

    private static boolean decodeServiceCategoryProgramData(BearerData object, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException, CodingException {
        if (bitwiseInputStream.available() >= 13) {
            int n = bitwiseInputStream.read(8);
            int n2 = bitwiseInputStream.read(5);
            if (bitwiseInputStream.available() >= n) {
                int n3;
                Object object2;
                ArrayList<CdmaSmsCbProgramData> arrayList = new ArrayList<CdmaSmsCbProgramData>();
                boolean bl = false;
                for (n = n * 8 - 5; n >= 48; n -= n3) {
                    int n4 = bitwiseInputStream.read(4);
                    int n5 = bitwiseInputStream.read(8);
                    int n6 = bitwiseInputStream.read(8);
                    int n7 = bitwiseInputStream.read(8);
                    int n8 = bitwiseInputStream.read(8);
                    int n9 = bitwiseInputStream.read(4);
                    int n10 = bitwiseInputStream.read(8);
                    n3 = BearerData.getBitsForNumFields(n2, n10);
                    if ((n -= 48) >= n3) {
                        object2 = new UserData();
                        ((UserData)object2).msgEncoding = n2;
                        ((UserData)object2).msgEncodingSet = true;
                        ((UserData)object2).numFields = n10;
                        ((UserData)object2).payload = bitwiseInputStream.readByteArray(n3);
                        BearerData.decodeUserDataPayload((UserData)object2, false);
                        arrayList.add(new CdmaSmsCbProgramData(n4, n5 << 8 | n6, n7, n8, n9, ((UserData)object2).payloadStr));
                        bl = true;
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("category name is ");
                    ((StringBuilder)object).append(n3);
                    ((StringBuilder)object).append(" bits in length, but there are only ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" bits available");
                    throw new CodingException(((StringBuilder)object).toString());
                }
                if (!bl || n > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("SERVICE_CATEGORY_PROGRAM_DATA decode ");
                    object2 = bl ? "succeeded" : "failed";
                    stringBuilder.append((String)object2);
                    stringBuilder.append(" (extra bits = ");
                    stringBuilder.append(n);
                    stringBuilder.append(')');
                    Rlog.d(LOG_TAG, stringBuilder.toString());
                }
                bitwiseInputStream.skip(n);
                ((BearerData)object).serviceCategoryProgramData = arrayList;
                return bl;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("SERVICE_CATEGORY_PROGRAM_DATA decode failed: only ");
            ((StringBuilder)object).append(bitwiseInputStream.available());
            ((StringBuilder)object).append(" bits available (");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" bits expected)");
            throw new CodingException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("SERVICE_CATEGORY_PROGRAM_DATA decode failed: only ");
        ((StringBuilder)object).append(bitwiseInputStream.available());
        ((StringBuilder)object).append(" bits available");
        throw new CodingException(((StringBuilder)object).toString());
    }

    private static String decodeShiftJis(byte[] arrby, int n, int n2) throws CodingException {
        return BearerData.decodeCharset(arrby, n, n2, 1, "Shift_JIS");
    }

    private static void decodeSmsAddress(CdmaSmsAddress cdmaSmsAddress) throws CodingException {
        if (cdmaSmsAddress.digitMode == 1) {
            try {
                String string2;
                cdmaSmsAddress.address = string2 = new String(cdmaSmsAddress.origBytes, 0, cdmaSmsAddress.origBytes.length, "US-ASCII");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new CodingException("invalid SMS address ASCII code");
            }
        } else {
            cdmaSmsAddress.address = BearerData.decodeDtmfSmsAddress(cdmaSmsAddress.origBytes, cdmaSmsAddress.numberOfDigits);
        }
    }

    private static boolean decodeUserData(BearerData bearerData, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n = bitwiseInputStream.read(8);
        bearerData.userData = new UserData();
        bearerData.userData.msgEncoding = bitwiseInputStream.read(5);
        UserData userData = bearerData.userData;
        userData.msgEncodingSet = true;
        userData.msgType = 0;
        int n2 = 5;
        if (userData.msgEncoding == 1 || bearerData.userData.msgEncoding == 10) {
            bearerData.userData.msgType = bitwiseInputStream.read(8);
            n2 = 5 + 8;
        }
        bearerData.userData.numFields = bitwiseInputStream.read(8);
        bearerData.userData.payload = bitwiseInputStream.readByteArray(n * 8 - (n2 + 8));
        return true;
    }

    private static void decodeUserDataPayload(UserData userData, boolean bl) throws CodingException {
        Object object;
        int n;
        int n2 = 0;
        if (bl) {
            n = userData.payload[0] & 255;
            n2 = 0 + (n + 1);
            object = new byte[n];
            System.arraycopy(userData.payload, 1, object, 0, n);
            userData.userDataHeader = SmsHeader.fromByteArray((byte[])object);
        }
        if ((n = userData.msgEncoding) != 0) {
            if (n != 2 && n != 3) {
                if (n != 4) {
                    if (n != 5) {
                        switch (n) {
                            default: {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("unsupported user data encoding (");
                                ((StringBuilder)object).append(userData.msgEncoding);
                                ((StringBuilder)object).append(")");
                                throw new CodingException(((StringBuilder)object).toString());
                            }
                            case 10: {
                                userData.payloadStr = BearerData.decodeGsmDcs(userData.payload, n2, userData.numFields, userData.msgType);
                                break;
                            }
                            case 9: {
                                userData.payloadStr = BearerData.decode7bitGsm(userData.payload, n2, userData.numFields);
                                break;
                            }
                            case 8: {
                                userData.payloadStr = BearerData.decodeLatin(userData.payload, n2, userData.numFields);
                                break;
                            }
                        }
                    } else {
                        userData.payloadStr = BearerData.decodeShiftJis(userData.payload, n2, userData.numFields);
                    }
                } else {
                    userData.payloadStr = BearerData.decodeUtf16(userData.payload, n2, userData.numFields);
                }
            } else {
                userData.payloadStr = BearerData.decode7bitAscii(userData.payload, n2, userData.numFields);
            }
        } else {
            bl = Resources.getSystem().getBoolean(17891524);
            object = new byte[userData.numFields];
            n = userData.numFields < userData.payload.length ? userData.numFields : userData.payload.length;
            System.arraycopy(userData.payload, 0, object, 0, n);
            userData.payload = object;
            userData.payloadStr = !bl ? BearerData.decodeLatin(userData.payload, n2, userData.numFields) : BearerData.decodeUtf8(userData.payload, n2, userData.numFields);
        }
    }

    private static boolean decodeUserResponseCode(BearerData bearerData, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            bl = true;
            bearerData.userResponseCode = bitwiseInputStream.read(8);
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("USER_RESPONSE_CODE decode ");
            String string2 = bl ? "succeeded" : "failed";
            stringBuilder.append(string2);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        bearerData.userResponseCodeSet = bl;
        return bl;
    }

    private static String decodeUtf16(byte[] arrby, int n, int n2) throws CodingException {
        return BearerData.decodeCharset(arrby, n, n2 - (n + n % 2) / 2, 2, "utf-16be");
    }

    private static String decodeUtf8(byte[] arrby, int n, int n2) throws CodingException {
        return BearerData.decodeCharset(arrby, n, n2, 1, "UTF-8");
    }

    private static boolean decodeValidityAbs(BearerData object, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 48) {
            n2 = n - 48;
            bl = true;
            ((BearerData)object).validityPeriodAbsolute = TimeStamp.fromByteArray(bitwiseInputStream.readByteArray(48));
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("VALIDITY_PERIOD_ABSOLUTE decode ");
            object = bl ? "succeeded" : "failed";
            stringBuilder.append((String)object);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        return bl;
    }

    private static boolean decodeValidityRel(BearerData bearerData, BitwiseInputStream bitwiseInputStream) throws BitwiseInputStream.AccessException {
        int n;
        boolean bl = false;
        int n2 = n = bitwiseInputStream.read(8) * 8;
        if (n >= 8) {
            n2 = n - 8;
            bl = true;
            bearerData.deferredDeliveryTimeRelative = bitwiseInputStream.read(8);
        }
        if (!bl || n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("VALIDITY_PERIOD_RELATIVE decode ");
            String string2 = bl ? "succeeded" : "failed";
            stringBuilder.append(string2);
            stringBuilder.append(" (extra bits = ");
            stringBuilder.append(n2);
            stringBuilder.append(")");
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        bitwiseInputStream.skip(n2);
        bearerData.deferredDeliveryTimeRelativeSet = bl;
        return bl;
    }

    public static byte[] encode(BearerData arrby) {
        Object object = arrby.userData;
        boolean bl = object != null && ((UserData)object).userDataHeader != null;
        arrby.hasUserDataHeader = bl;
        try {
            object = new BitwiseOutputStream(200);
            ((BitwiseOutputStream)object).write(8, 0);
            BearerData.encodeMessageId((BearerData)arrby, (BitwiseOutputStream)object);
            if (arrby.userData != null) {
                ((BitwiseOutputStream)object).write(8, 1);
                BearerData.encodeUserData((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.callbackNumber != null) {
                ((BitwiseOutputStream)object).write(8, 14);
                BearerData.encodeCallbackNumber((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.userAckReq || arrby.deliveryAckReq || arrby.readAckReq || arrby.reportReq) {
                ((BitwiseOutputStream)object).write(8, 10);
                BearerData.encodeReplyOption((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.numberOfMessages != 0) {
                ((BitwiseOutputStream)object).write(8, 11);
                BearerData.encodeMsgCount((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.validityPeriodRelativeSet) {
                ((BitwiseOutputStream)object).write(8, 5);
                BearerData.encodeValidityPeriodRel((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.privacyIndicatorSet) {
                ((BitwiseOutputStream)object).write(8, 9);
                BearerData.encodePrivacyIndicator((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.languageIndicatorSet) {
                ((BitwiseOutputStream)object).write(8, 13);
                BearerData.encodeLanguageIndicator((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.displayModeSet) {
                ((BitwiseOutputStream)object).write(8, 15);
                BearerData.encodeDisplayMode((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.priorityIndicatorSet) {
                ((BitwiseOutputStream)object).write(8, 8);
                BearerData.encodePriorityIndicator((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.alertIndicatorSet) {
                ((BitwiseOutputStream)object).write(8, 12);
                BearerData.encodeMsgDeliveryAlert((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.messageStatusSet) {
                ((BitwiseOutputStream)object).write(8, 20);
                BearerData.encodeMsgStatus((BearerData)arrby, (BitwiseOutputStream)object);
            }
            if (arrby.serviceCategoryProgramResults != null) {
                ((BitwiseOutputStream)object).write(8, 19);
                BearerData.encodeScpResults((BearerData)arrby, (BitwiseOutputStream)object);
            }
            arrby = ((BitwiseOutputStream)object).toByteArray();
            return arrby;
        }
        catch (CodingException codingException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("BearerData encode failed: ");
            ((StringBuilder)object).append(codingException);
            Rlog.e(LOG_TAG, ((StringBuilder)object).toString());
        }
        catch (BitwiseOutputStream.AccessException accessException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("BearerData encode failed: ");
            ((StringBuilder)object).append(accessException);
            Rlog.e(LOG_TAG, ((StringBuilder)object).toString());
        }
        return null;
    }

    private static void encode16bitEms(UserData userData, byte[] arrby) throws CodingException {
        byte[] arrby2 = BearerData.encodeUtf16(userData.payloadStr);
        int n = arrby.length + 1;
        int n2 = (n + 1) / 2;
        int n3 = arrby2.length / 2;
        userData.msgEncoding = 4;
        userData.msgEncodingSet = true;
        userData.numFields = n2 + n3;
        userData.payload = new byte[userData.numFields * 2];
        userData.payload[0] = (byte)arrby.length;
        System.arraycopy(arrby, 0, userData.payload, 1, arrby.length);
        System.arraycopy(arrby2, 0, userData.payload, n, arrby2.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static byte[] encode7bitAscii(String arrby, boolean bl) throws CodingException {
        Object object;
        try {
            object = new BitwiseOutputStream(arrby.length());
            int n = arrby.length();
            int n2 = 0;
            while (n2 < n) {
                int n3 = UserData.charToAscii.get(arrby.charAt(n2), -1);
                if (n3 == -1) {
                    if (!bl) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("cannot ASCII encode (");
                        ((StringBuilder)object).append(arrby.charAt(n2));
                        ((StringBuilder)object).append(")");
                        CodingException codingException = new CodingException(((StringBuilder)object).toString());
                        throw codingException;
                    }
                    ((BitwiseOutputStream)object).write(7, 32);
                } else {
                    ((BitwiseOutputStream)object).write(7, n3);
                }
                ++n2;
            }
            return ((BitwiseOutputStream)object).toByteArray();
        }
        catch (BitwiseOutputStream.AccessException accessException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("7bit ASCII encode failed: ");
            ((StringBuilder)object).append(accessException);
            throw new CodingException(((StringBuilder)object).toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void encode7bitAsciiEms(UserData object, byte[] object2, boolean bl) throws CodingException {
        try {
            Rlog.d(LOG_TAG, "encode7bitAsciiEms");
            int n = ((Object)object2).length + 1;
            int n2 = (n * 8 + 6) / 7;
            int n3 = n2 * 7 - n * 8;
            byte[] arrby = ((UserData)object).payloadStr;
            int n4 = arrby.length();
            int n5 = n3 > 0 ? 1 : 0;
            BitwiseOutputStream bitwiseOutputStream = new BitwiseOutputStream(n5 + n4);
            bitwiseOutputStream.write(n3, 0);
            for (n5 = 0; n5 < n4; ++n5) {
                n3 = UserData.charToAscii.get(arrby.charAt(n5), -1);
                if (n3 == -1) {
                    if (!bl) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("cannot ASCII encode (");
                        ((StringBuilder)object2).append(arrby.charAt(n5));
                        ((StringBuilder)object2).append(")");
                        object = new CodingException(((StringBuilder)object2).toString());
                        throw object;
                    }
                    bitwiseOutputStream.write(7, 32);
                    continue;
                }
                bitwiseOutputStream.write(7, n3);
            }
            arrby = bitwiseOutputStream.toByteArray();
            ((UserData)object).msgEncoding = 2;
            ((UserData)object).msgEncodingSet = true;
            ((UserData)object).numFields = ((UserData)object).payloadStr.length() + n2;
            ((UserData)object).payload = new byte[arrby.length + n];
            object.payload[0] = (byte)((Object)object2).length;
            System.arraycopy(object2, 0, ((UserData)object).payload, 1, ((Object)object2).length);
            System.arraycopy(arrby, 0, ((UserData)object).payload, n, arrby.length);
            return;
        }
        catch (BitwiseOutputStream.AccessException accessException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("7bit ASCII encode failed: ");
            ((StringBuilder)object2).append(accessException);
            throw new CodingException(((StringBuilder)object2).toString());
        }
    }

    private static void encode7bitEms(UserData userData, byte[] arrby, boolean bl) throws CodingException {
        int n = ((arrby.length + 1) * 8 + 6) / 7;
        Gsm7bitCodingResult gsm7bitCodingResult = BearerData.encode7bitGsm(userData.payloadStr, n, bl);
        userData.msgEncoding = 9;
        userData.msgEncodingSet = true;
        userData.numFields = gsm7bitCodingResult.septets;
        userData.payload = gsm7bitCodingResult.data;
        userData.payload[0] = (byte)arrby.length;
        System.arraycopy(arrby, 0, userData.payload, 1, arrby.length);
    }

    private static Gsm7bitCodingResult encode7bitGsm(String object, int n, boolean bl) throws CodingException {
        bl = !bl;
        try {
            byte[] arrby = GsmAlphabet.stringToGsm7BitPacked((String)object, n, bl, 0, 0);
            object = new Gsm7bitCodingResult();
            ((Gsm7bitCodingResult)object).data = new byte[arrby.length - 1];
            System.arraycopy(arrby, 1, ((Gsm7bitCodingResult)object).data, 0, arrby.length - 1);
            ((Gsm7bitCodingResult)object).septets = arrby[0] & 255;
            return object;
        }
        catch (EncodeException encodeException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("7bit GSM encode failed: ");
            ((StringBuilder)object).append(encodeException);
            throw new CodingException(((StringBuilder)object).toString());
        }
    }

    private static void encodeCallbackNumber(BearerData object, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException, CodingException {
        int n;
        object = ((BearerData)object).callbackNumber;
        BearerData.encodeCdmaSmsAddress((CdmaSmsAddress)object);
        int n2 = 9;
        if (((CdmaSmsAddress)object).digitMode == 1) {
            n2 = 9 + 7;
            n = ((CdmaSmsAddress)object).numberOfDigits * 8;
        } else {
            n = ((CdmaSmsAddress)object).numberOfDigits * 4;
        }
        int n3 = n2 + n;
        int n4 = n3 / 8;
        n2 = n3 % 8 > 0 ? 1 : 0;
        n2 = n4 + n2;
        n3 = n2 * 8 - n3;
        bitwiseOutputStream.write(8, n2);
        bitwiseOutputStream.write(1, ((CdmaSmsAddress)object).digitMode);
        if (((CdmaSmsAddress)object).digitMode == 1) {
            bitwiseOutputStream.write(3, ((CdmaSmsAddress)object).ton);
            bitwiseOutputStream.write(4, ((CdmaSmsAddress)object).numberPlan);
        }
        bitwiseOutputStream.write(8, ((CdmaSmsAddress)object).numberOfDigits);
        bitwiseOutputStream.writeByteArray(n, ((CdmaSmsAddress)object).origBytes);
        if (n3 > 0) {
            bitwiseOutputStream.write(n3, 0);
        }
    }

    private static void encodeCdmaSmsAddress(CdmaSmsAddress cdmaSmsAddress) throws CodingException {
        if (cdmaSmsAddress.digitMode == 1) {
            try {
                cdmaSmsAddress.origBytes = cdmaSmsAddress.address.getBytes("US-ASCII");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new CodingException("invalid SMS address, cannot convert to ASCII");
            }
        } else {
            cdmaSmsAddress.origBytes = BearerData.encodeDtmfSmsAddress(cdmaSmsAddress.address);
        }
    }

    private static void encodeDisplayMode(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(2, bearerData.displayMode);
        bitwiseOutputStream.skip(6);
    }

    private static byte[] encodeDtmfSmsAddress(String string2) {
        int n = string2.length();
        int n2 = n * 4;
        int n3 = n2 / 8;
        n2 = n2 % 8 > 0 ? 1 : 0;
        byte[] arrby = new byte[n3 + n2];
        for (n3 = 0; n3 < n; ++n3) {
            block7 : {
                block4 : {
                    block6 : {
                        block5 : {
                            block3 : {
                                n2 = string2.charAt(n3);
                                if (n2 < 49 || n2 > 57) break block3;
                                n2 -= 48;
                                break block4;
                            }
                            if (n2 != 48) break block5;
                            n2 = 10;
                            break block4;
                        }
                        if (n2 != 42) break block6;
                        n2 = 11;
                        break block4;
                    }
                    if (n2 != 35) break block7;
                    n2 = 12;
                }
                int n4 = n3 / 2;
                arrby[n4] = (byte)(arrby[n4] | n2 << 4 - n3 % 2 * 4);
                continue;
            }
            return null;
        }
        return arrby;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void encodeEmsUserDataPayload(UserData userData) throws CodingException {
        byte[] arrby = SmsHeader.toByteArray(userData.userDataHeader);
        if (userData.msgEncodingSet) {
            if (userData.msgEncoding == 9) {
                BearerData.encode7bitEms(userData, arrby, true);
                return;
            }
            if (userData.msgEncoding == 4) {
                BearerData.encode16bitEms(userData, arrby);
                return;
            }
            if (userData.msgEncoding == 2) {
                BearerData.encode7bitAsciiEms(userData, arrby, true);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unsupported EMS user data encoding (");
            stringBuilder.append(userData.msgEncoding);
            stringBuilder.append(")");
            throw new CodingException(stringBuilder.toString());
        }
        try {
            BearerData.encode7bitEms(userData, arrby, false);
            return;
        }
        catch (CodingException codingException) {
            BearerData.encode16bitEms(userData, arrby);
        }
    }

    private static void encodeLanguageIndicator(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(8, bearerData.language);
    }

    private static void encodeMessageId(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 3);
        bitwiseOutputStream.write(4, bearerData.messageType);
        bitwiseOutputStream.write(8, bearerData.messageId >> 8);
        bitwiseOutputStream.write(8, bearerData.messageId);
        bitwiseOutputStream.write(1, (int)bearerData.hasUserDataHeader);
        bitwiseOutputStream.skip(3);
    }

    private static void encodeMsgCount(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(8, bearerData.numberOfMessages);
    }

    private static void encodeMsgDeliveryAlert(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(2, bearerData.alert);
        bitwiseOutputStream.skip(6);
    }

    private static void encodeMsgStatus(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(2, bearerData.errorClass);
        bitwiseOutputStream.write(6, bearerData.messageStatus);
    }

    private static void encodePriorityIndicator(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(2, bearerData.priority);
        bitwiseOutputStream.skip(6);
    }

    private static void encodePrivacyIndicator(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(2, bearerData.privacy);
        bitwiseOutputStream.skip(6);
    }

    private static void encodeReplyOption(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(1, (int)bearerData.userAckReq);
        bitwiseOutputStream.write(1, (int)bearerData.deliveryAckReq);
        bitwiseOutputStream.write(1, (int)bearerData.readAckReq);
        bitwiseOutputStream.write(1, (int)bearerData.reportReq);
        bitwiseOutputStream.write(4, 0);
    }

    private static void encodeScpResults(BearerData iterator, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        iterator = ((BearerData)iterator).serviceCategoryProgramResults;
        bitwiseOutputStream.write(8, ((ArrayList)((Object)iterator)).size() * 4);
        iterator = ((ArrayList)((Object)iterator)).iterator();
        while (iterator.hasNext()) {
            CdmaSmsCbProgramResults cdmaSmsCbProgramResults = (CdmaSmsCbProgramResults)iterator.next();
            int n = cdmaSmsCbProgramResults.getCategory();
            bitwiseOutputStream.write(8, n >> 8);
            bitwiseOutputStream.write(8, n);
            bitwiseOutputStream.write(8, cdmaSmsCbProgramResults.getLanguage());
            bitwiseOutputStream.write(4, cdmaSmsCbProgramResults.getCategoryResult());
            bitwiseOutputStream.skip(4);
        }
    }

    private static byte[] encodeShiftJis(String object) throws CodingException {
        try {
            object = ((String)object).getBytes("Shift_JIS");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Shift-JIS encode failed: ");
            ((StringBuilder)object).append(unsupportedEncodingException);
            throw new CodingException(((StringBuilder)object).toString());
        }
    }

    private static void encodeUserData(BearerData bearerData, BitwiseOutputStream object) throws BitwiseOutputStream.AccessException, CodingException {
        block5 : {
            int n;
            int n2;
            int n3;
            block7 : {
                block6 : {
                    BearerData.encodeUserDataPayload(bearerData.userData);
                    boolean bl = bearerData.userData.userDataHeader != null;
                    bearerData.hasUserDataHeader = bl;
                    if (bearerData.userData.payload.length > 140) break block5;
                    n2 = bearerData.userData.payload.length * 8 - bearerData.userData.paddingBits;
                    n3 = n2 + 13;
                    if (bearerData.userData.msgEncoding == 1) break block6;
                    n = n3;
                    if (bearerData.userData.msgEncoding != 10) break block7;
                }
                n = n3 + 8;
            }
            int n4 = n / 8;
            n3 = n % 8 > 0 ? 1 : 0;
            n3 = n4 + n3;
            n = n3 * 8 - n;
            ((BitwiseOutputStream)object).write(8, n3);
            ((BitwiseOutputStream)object).write(5, bearerData.userData.msgEncoding);
            if (bearerData.userData.msgEncoding == 1 || bearerData.userData.msgEncoding == 10) {
                ((BitwiseOutputStream)object).write(8, bearerData.userData.msgType);
            }
            ((BitwiseOutputStream)object).write(8, bearerData.userData.numFields);
            ((BitwiseOutputStream)object).writeByteArray(n2, bearerData.userData.payload);
            if (n > 0) {
                ((BitwiseOutputStream)object).write(n, 0);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("encoded user data too large (");
        ((StringBuilder)object).append(bearerData.userData.payload.length);
        ((StringBuilder)object).append(" > ");
        ((StringBuilder)object).append(140);
        ((StringBuilder)object).append(" bytes)");
        throw new CodingException(((StringBuilder)object).toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void encodeUserDataPayload(UserData userData) throws CodingException {
        if (userData.payloadStr == null && userData.msgEncoding != 0) {
            Rlog.e(LOG_TAG, "user data with null payloadStr");
            userData.payloadStr = "";
        }
        if (userData.userDataHeader != null) {
            BearerData.encodeEmsUserDataPayload(userData);
            return;
        }
        if (userData.msgEncodingSet) {
            if (userData.msgEncoding == 0) {
                if (userData.payload == null) {
                    Rlog.e(LOG_TAG, "user data with octet encoding but null payload");
                    userData.payload = new byte[0];
                    userData.numFields = 0;
                    return;
                } else {
                    userData.numFields = userData.payload.length;
                }
                return;
            }
            if (userData.payloadStr == null) {
                Rlog.e(LOG_TAG, "non-octet user data with null payloadStr");
                userData.payloadStr = "";
            }
            if (userData.msgEncoding == 9) {
                Gsm7bitCodingResult gsm7bitCodingResult = BearerData.encode7bitGsm(userData.payloadStr, 0, true);
                userData.payload = gsm7bitCodingResult.data;
                userData.numFields = gsm7bitCodingResult.septets;
                return;
            }
            if (userData.msgEncoding == 2) {
                userData.payload = BearerData.encode7bitAscii(userData.payloadStr, true);
                userData.numFields = userData.payloadStr.length();
                return;
            }
            if (userData.msgEncoding == 4) {
                userData.payload = BearerData.encodeUtf16(userData.payloadStr);
                userData.numFields = userData.payloadStr.length();
                return;
            }
            if (userData.msgEncoding == 5) {
                userData.payload = BearerData.encodeShiftJis(userData.payloadStr);
                userData.numFields = userData.payload.length;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unsupported user data encoding (");
            stringBuilder.append(userData.msgEncoding);
            stringBuilder.append(")");
            throw new CodingException(stringBuilder.toString());
        }
        try {
            userData.payload = BearerData.encode7bitAscii(userData.payloadStr, false);
            userData.msgEncoding = 2;
        }
        catch (CodingException codingException) {
            userData.payload = BearerData.encodeUtf16(userData.payloadStr);
            userData.msgEncoding = 4;
        }
        userData.numFields = userData.payloadStr.length();
        userData.msgEncodingSet = true;
    }

    private static byte[] encodeUtf16(String arrby) throws CodingException {
        try {
            arrby = arrby.getBytes("utf-16be");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UTF-16 encode failed: ");
            stringBuilder.append(unsupportedEncodingException);
            throw new CodingException(stringBuilder.toString());
        }
    }

    private static void encodeValidityPeriodRel(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(8, bearerData.validityPeriodRelative);
    }

    private static int getBitsForNumFields(int n, int n2) throws CodingException {
        if (n != 0) {
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("unsupported message encoding (");
                    stringBuilder.append(n);
                    stringBuilder.append(')');
                    throw new CodingException(stringBuilder.toString());
                }
                case 4: {
                    return n2 * 16;
                }
                case 2: 
                case 3: 
                case 9: {
                    return n2 * 7;
                }
                case 5: 
                case 6: 
                case 7: 
                case 8: 
            }
        }
        return n2 * 8;
    }

    private static String getLanguageCodeForValue(int n) {
        switch (n) {
            default: {
                return null;
            }
            case 7: {
                return "he";
            }
            case 6: {
                return "zh";
            }
            case 5: {
                return "ko";
            }
            case 4: {
                return "ja";
            }
            case 3: {
                return "es";
            }
            case 2: {
                return "fr";
            }
            case 1: 
        }
        return "en";
    }

    private static boolean isCmasAlertCategory(int n) {
        boolean bl = n >= 4096 && n <= 4351;
        return bl;
    }

    private static int serviceCategoryToCmasMessageClass(int n) {
        switch (n) {
            default: {
                return -1;
            }
            case 4100: {
                return 4;
            }
            case 4099: {
                return 3;
            }
            case 4098: {
                return 2;
            }
            case 4097: {
                return 1;
            }
            case 4096: 
        }
        return 0;
    }

    public String getLanguage() {
        return BearerData.getLanguageCodeForValue(this.language);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BearerData ");
        Object object = new StringBuilder();
        ((StringBuilder)object).append("{ messageType=");
        ((StringBuilder)object).append(this.messageType);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", messageId=");
        ((StringBuilder)object).append(this.messageId);
        stringBuilder.append(((StringBuilder)object).toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", priority=");
        boolean bl = this.priorityIndicatorSet;
        String string2 = "unset";
        object = bl ? Integer.valueOf(this.priority) : "unset";
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", privacy=");
        object = this.privacyIndicatorSet ? Integer.valueOf(this.privacy) : "unset";
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", alert=");
        object = this.alertIndicatorSet ? Integer.valueOf(this.alert) : "unset";
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", displayMode=");
        object = this.displayModeSet ? Integer.valueOf(this.displayMode) : "unset";
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", language=");
        object = this.languageIndicatorSet ? Integer.valueOf(this.language) : "unset";
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", errorClass=");
        object = this.messageStatusSet ? Integer.valueOf(this.errorClass) : "unset";
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", msgStatus=");
        object = this.messageStatusSet ? Integer.valueOf(this.messageStatus) : "unset";
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", msgCenterTimeStamp=");
        object = this.msgCenterTimeStamp;
        if (object == null) {
            object = "unset";
        }
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", validityPeriodAbsolute=");
        object = this.validityPeriodAbsolute;
        if (object == null) {
            object = "unset";
        }
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", validityPeriodRelative=");
        object = this.validityPeriodRelativeSet ? Integer.valueOf(this.validityPeriodRelative) : "unset";
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", deferredDeliveryTimeAbsolute=");
        object = this.deferredDeliveryTimeAbsolute;
        if (object == null) {
            object = "unset";
        }
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", deferredDeliveryTimeRelative=");
        object = string2;
        if (this.deferredDeliveryTimeRelativeSet) {
            object = this.deferredDeliveryTimeRelative;
        }
        stringBuilder2.append(object);
        stringBuilder.append(stringBuilder2.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", userAckReq=");
        ((StringBuilder)object).append(this.userAckReq);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", deliveryAckReq=");
        ((StringBuilder)object).append(this.deliveryAckReq);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", readAckReq=");
        ((StringBuilder)object).append(this.readAckReq);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", reportReq=");
        ((StringBuilder)object).append(this.reportReq);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", numberOfMessages=");
        ((StringBuilder)object).append(this.numberOfMessages);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", callbackNumber=");
        ((StringBuilder)object).append(Rlog.pii(LOG_TAG, (Object)this.callbackNumber));
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", depositIndex=");
        ((StringBuilder)object).append(this.depositIndex);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", hasUserDataHeader=");
        ((StringBuilder)object).append(this.hasUserDataHeader);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", userData=");
        ((StringBuilder)object).append(this.userData);
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    private static class CodingException
    extends Exception {
        public CodingException(String string2) {
            super(string2);
        }
    }

    private static class Gsm7bitCodingResult {
        byte[] data;
        int septets;

        private Gsm7bitCodingResult() {
        }
    }

    public static class TimeStamp
    extends Time {
        public TimeStamp() {
            super(TimeZone.getDefault().getID());
        }

        public static TimeStamp fromByteArray(byte[] arrby) {
            TimeStamp timeStamp = new TimeStamp();
            int n = IccUtils.cdmaBcdByteToInt(arrby[0]);
            if (n <= 99 && n >= 0) {
                n = n >= 96 ? (n += 1900) : (n += 2000);
                timeStamp.year = n;
                n = IccUtils.cdmaBcdByteToInt(arrby[1]);
                if (n >= 1 && n <= 12) {
                    timeStamp.month = n - 1;
                    n = IccUtils.cdmaBcdByteToInt(arrby[2]);
                    if (n >= 1 && n <= 31) {
                        timeStamp.monthDay = n;
                        n = IccUtils.cdmaBcdByteToInt(arrby[3]);
                        if (n >= 0 && n <= 23) {
                            timeStamp.hour = n;
                            n = IccUtils.cdmaBcdByteToInt(arrby[4]);
                            if (n >= 0 && n <= 59) {
                                timeStamp.minute = n;
                                n = IccUtils.cdmaBcdByteToInt(arrby[5]);
                                if (n >= 0 && n <= 59) {
                                    timeStamp.second = n;
                                    return timeStamp;
                                }
                                return null;
                            }
                            return null;
                        }
                        return null;
                    }
                    return null;
                }
                return null;
            }
            return null;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TimeStamp ");
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("{ year=");
            stringBuilder2.append(this.year);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", month=");
            stringBuilder2.append(this.month);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", day=");
            stringBuilder2.append(this.monthDay);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", hour=");
            stringBuilder2.append(this.hour);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", minute=");
            stringBuilder2.append(this.minute);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", second=");
            stringBuilder2.append(this.second);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

}

