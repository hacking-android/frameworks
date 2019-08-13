/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.mms.pdu;

import android.util.Log;
import com.google.android.mms.InvalidHeaderValueException;
import com.google.android.mms.pdu.AcknowledgeInd;
import com.google.android.mms.pdu.Base64;
import com.google.android.mms.pdu.CharacterSets;
import com.google.android.mms.pdu.DeliveryInd;
import com.google.android.mms.pdu.EncodedStringValue;
import com.google.android.mms.pdu.GenericPdu;
import com.google.android.mms.pdu.NotificationInd;
import com.google.android.mms.pdu.NotifyRespInd;
import com.google.android.mms.pdu.PduBody;
import com.google.android.mms.pdu.PduContentTypes;
import com.google.android.mms.pdu.PduHeaders;
import com.google.android.mms.pdu.PduPart;
import com.google.android.mms.pdu.QuotedPrintable;
import com.google.android.mms.pdu.ReadOrigInd;
import com.google.android.mms.pdu.ReadRecInd;
import com.google.android.mms.pdu.RetrieveConf;
import com.google.android.mms.pdu.SendConf;
import com.google.android.mms.pdu.SendReq;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;

public class PduParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = false;
    private static final int END_STRING_FLAG = 0;
    private static final int LENGTH_QUOTE = 31;
    private static final boolean LOCAL_LOGV = false;
    private static final String LOG_TAG = "PduParser";
    private static final int LONG_INTEGER_LENGTH_MAX = 8;
    private static final int QUOTE = 127;
    private static final int QUOTED_STRING_FLAG = 34;
    private static final int SHORT_INTEGER_MAX = 127;
    private static final int SHORT_LENGTH_MAX = 30;
    private static final int TEXT_MAX = 127;
    private static final int TEXT_MIN = 32;
    private static final int THE_FIRST_PART = 0;
    private static final int THE_LAST_PART = 1;
    private static final int TYPE_QUOTED_STRING = 1;
    private static final int TYPE_TEXT_STRING = 0;
    private static final int TYPE_TOKEN_STRING = 2;
    private static byte[] mStartParam;
    private static byte[] mTypeParam;
    private PduBody mBody = null;
    private PduHeaders mHeaders = null;
    private final boolean mParseContentDisposition;
    private ByteArrayInputStream mPduDataStream = null;

    static {
        mTypeParam = null;
        mStartParam = null;
    }

    public PduParser(byte[] arrby, boolean bl) {
        this.mPduDataStream = new ByteArrayInputStream(arrby);
        this.mParseContentDisposition = bl;
    }

    protected static boolean checkMandatoryHeader(PduHeaders pduHeaders) {
        if (pduHeaders == null) {
            return false;
        }
        int n = pduHeaders.getOctet(140);
        if (pduHeaders.getOctet(141) == 0) {
            return false;
        }
        switch (n) {
            default: {
                return false;
            }
            case 136: {
                if (-1L == pduHeaders.getLongInteger(133)) {
                    return false;
                }
                if (pduHeaders.getEncodedStringValue(137) == null) {
                    return false;
                }
                if (pduHeaders.getTextString(139) == null) {
                    return false;
                }
                if (pduHeaders.getOctet(155) == 0) {
                    return false;
                }
                if (pduHeaders.getEncodedStringValues(151) != null) break;
                return false;
            }
            case 135: {
                if (pduHeaders.getEncodedStringValue(137) == null) {
                    return false;
                }
                if (pduHeaders.getTextString(139) == null) {
                    return false;
                }
                if (pduHeaders.getOctet(155) == 0) {
                    return false;
                }
                if (pduHeaders.getEncodedStringValues(151) != null) break;
                return false;
            }
            case 134: {
                if (-1L == pduHeaders.getLongInteger(133)) {
                    return false;
                }
                if (pduHeaders.getTextString(139) == null) {
                    return false;
                }
                if (pduHeaders.getOctet(149) == 0) {
                    return false;
                }
                if (pduHeaders.getEncodedStringValues(151) != null) break;
                return false;
            }
            case 133: {
                if (pduHeaders.getTextString(152) != null) break;
                return false;
            }
            case 132: {
                if (pduHeaders.getTextString(132) == null) {
                    return false;
                }
                if (-1L != pduHeaders.getLongInteger(133)) break;
                return false;
            }
            case 131: {
                if (pduHeaders.getOctet(149) == 0) {
                    return false;
                }
                if (pduHeaders.getTextString(152) != null) break;
                return false;
            }
            case 130: {
                if (pduHeaders.getTextString(131) == null) {
                    return false;
                }
                if (-1L == pduHeaders.getLongInteger(136)) {
                    return false;
                }
                if (pduHeaders.getTextString(138) == null) {
                    return false;
                }
                if (-1L == pduHeaders.getLongInteger(142)) {
                    return false;
                }
                if (pduHeaders.getTextString(152) != null) break;
                return false;
            }
            case 129: {
                if (pduHeaders.getOctet(146) == 0) {
                    return false;
                }
                if (pduHeaders.getTextString(152) != null) break;
                return false;
            }
            case 128: {
                if (pduHeaders.getTextString(132) == null) {
                    return false;
                }
                if (pduHeaders.getEncodedStringValue(137) == null) {
                    return false;
                }
                if (pduHeaders.getTextString(152) != null) break;
                return false;
            }
        }
        return true;
    }

    private static int checkPartPosition(PduPart arrby) {
        if (mTypeParam == null && mStartParam == null) {
            return 1;
        }
        if (mStartParam != null) {
            return (arrby = arrby.getContentId()) == null || true != Arrays.equals(mStartParam, arrby);
        }
        return mTypeParam == null || (arrby = arrby.getContentType()) == null || true != Arrays.equals(mTypeParam, arrby);
    }

    protected static int extractByteValue(ByteArrayInputStream byteArrayInputStream) {
        int n = byteArrayInputStream.read();
        return n & 255;
    }

    protected static byte[] getWapString(ByteArrayInputStream byteArrayInputStream, int n) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n2 = byteArrayInputStream.read();
        while (-1 != n2 && n2 != 0) {
            if (n == 2) {
                if (PduParser.isTokenCharacter(n2)) {
                    byteArrayOutputStream.write(n2);
                }
            } else if (PduParser.isText(n2)) {
                byteArrayOutputStream.write(n2);
            }
            n2 = byteArrayInputStream.read();
        }
        if (byteArrayOutputStream.size() > 0) {
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    protected static boolean isText(int n) {
        if (n >= 32 && n <= 126 || n >= 128 && n <= 255) {
            return true;
        }
        return n == 9 || n == 10 || n == 13;
    }

    protected static boolean isTokenCharacter(int n) {
        if (n >= 33 && n <= 126) {
            if (n != 34 && n != 44 && n != 47 && n != 123 && n != 125 && n != 40 && n != 41) {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                return true;
                            }
                            case 91: 
                            case 92: 
                            case 93: 
                        }
                    }
                    case 58: 
                    case 59: 
                    case 60: 
                    case 61: 
                    case 62: 
                    case 63: 
                    case 64: 
                }
            }
            return false;
        }
        return false;
    }

    private static void log(String string) {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static byte[] parseContentType(ByteArrayInputStream byteArrayInputStream, HashMap<Integer, Object> hashMap) {
        int n;
        block6 : {
            block9 : {
                int n2;
                byte[] arrby;
                block8 : {
                    int n3;
                    block7 : {
                        byteArrayInputStream.mark(1);
                        n = byteArrayInputStream.read();
                        byteArrayInputStream.reset();
                        if ((n &= 255) >= 32) break block6;
                        n2 = PduParser.parseValueLength(byteArrayInputStream);
                        n = byteArrayInputStream.available();
                        byteArrayInputStream.mark(1);
                        n3 = byteArrayInputStream.read();
                        byteArrayInputStream.reset();
                        if ((n3 &= 255) < 32 || n3 > 127) break block7;
                        arrby = PduParser.parseWapString(byteArrayInputStream, 0);
                        break block8;
                    }
                    if (n3 <= 127) break block9;
                    n3 = PduParser.parseShortInteger(byteArrayInputStream);
                    if (n3 < PduContentTypes.contentTypes.length) {
                        arrby = PduContentTypes.contentTypes[n3].getBytes();
                    } else {
                        byteArrayInputStream.reset();
                        arrby = PduParser.parseWapString(byteArrayInputStream, 0);
                    }
                }
                n = n2 - (n - byteArrayInputStream.available());
                if (n > 0) {
                    PduParser.parseContentTypeParams(byteArrayInputStream, hashMap, n);
                }
                if (n >= 0) return arrby;
                Log.e((String)LOG_TAG, (String)"Corrupt MMS message");
                return PduContentTypes.contentTypes[0].getBytes();
            }
            Log.e((String)LOG_TAG, (String)"Corrupt content-type");
            return PduContentTypes.contentTypes[0].getBytes();
        }
        if (n > 127) return PduContentTypes.contentTypes[PduParser.parseShortInteger(byteArrayInputStream)].getBytes();
        return PduParser.parseWapString(byteArrayInputStream, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static void parseContentTypeParams(ByteArrayInputStream var0, HashMap<Integer, Object> var1_1, Integer var2_2) {
        var3_3 = var0.available();
        var4_4 = var2_2;
        do {
            block18 : {
                block19 : {
                    if (var4_4 <= 0) {
                        if (var4_4 == 0) return;
                        Log.e((String)"PduParser", (String)"Corrupt Content-Type");
                        return;
                    }
                    var5_5 = var0.read();
                    --var4_4;
                    if (var5_5 == 129) break block18;
                    if (var5_5 == 131) break block19;
                    if (var5_5 == 133 || var5_5 == 151) ** GOTO lbl30
                    if (var5_5 != 153) {
                        if (var5_5 != 137) {
                            if (var5_5 != 138) {
                                if (-1 == PduParser.skipWapValue(var0, var4_4)) {
                                    Log.e((String)"PduParser", (String)"Corrupt Content-Type");
                                    continue;
                                }
                                var4_4 = 0;
                                continue;
                            } else {
                                ** GOTO lbl-1000
                            }
                        }
                    } else lbl-1000: // 3 sources:
                    {
                        if ((var6_6 = PduParser.parseWapString(var0, 0)) != null && var1_1 != null) {
                            var1_1.put(153, var6_6);
                        }
                        var4_4 = var0.available();
                        var4_4 = var2_2 - (var3_3 - var4_4);
                        continue;
lbl30: // 1 sources:
                        var6_6 = PduParser.parseWapString(var0, 0);
                        if (var6_6 != null && var1_1 != null) {
                            var1_1.put(151, var6_6);
                        }
                        var4_4 = var0.available();
                        var4_4 = var2_2 - (var3_3 - var4_4);
                        continue;
                    }
                }
                var0.mark(1);
                var4_4 = PduParser.extractByteValue(var0);
                var0.reset();
                if (var4_4 > 127) {
                    var4_4 = PduParser.parseShortInteger(var0);
                    if (var4_4 < PduContentTypes.contentTypes.length) {
                        var1_1.put(131, PduContentTypes.contentTypes[var4_4].getBytes());
                    }
                } else {
                    var6_6 = PduParser.parseWapString(var0, 0);
                    if (var6_6 != null && var1_1 != null) {
                        var1_1.put(131, var6_6);
                    }
                }
                var4_4 = var0.available();
                var4_4 = var2_2 - (var3_3 - var4_4);
                continue;
            }
            var0.mark(1);
            var4_4 = PduParser.extractByteValue(var0);
            var0.reset();
            if (var4_4 > 32 && var4_4 < 127 || var4_4 == 0) {
                var6_6 = PduParser.parseWapString(var0, 0);
                try {
                    var7_7 = new String(var6_6);
                    var1_1.put(129, CharacterSets.getMibEnumValue(var7_7));
                }
                catch (UnsupportedEncodingException var7_8) {
                    Log.e((String)"PduParser", (String)Arrays.toString(var6_6), (Throwable)var7_8);
                    var1_1.put(129, 0);
                }
            } else {
                var4_4 = (int)PduParser.parseIntegerValue(var0);
                if (var1_1 != null) {
                    var1_1.put(129, var4_4);
                }
            }
            var4_4 = var0.available();
            var4_4 = var2_2 - (var3_3 - var4_4);
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static EncodedStringValue parseEncodedStringValue(ByteArrayInputStream var0) {
        var0.mark(1);
        var1_6 = 0;
        var2_7 = var0.read();
        if ((var2_7 &= 255) == 0) {
            return new EncodedStringValue("");
        }
        var0.reset();
        if (var2_7 < 32) {
            PduParser.parseValueLength((ByteArrayInputStream)var0);
            var1_6 = PduParser.parseShortInteger((ByteArrayInputStream)var0);
        }
        var0_1 = PduParser.parseWapString((ByteArrayInputStream)var0, 0);
        if (var1_6 == 0) ** GOTO lbl16
        try {
            var0_2 = new EncodedStringValue(var1_6, var0_1);
            return var0_4;
lbl16: // 1 sources:
            var0_3 = new EncodedStringValue(var0_1);
            return var0_4;
        }
        catch (Exception var0_5) {
            return null;
        }
    }

    protected static long parseIntegerValue(ByteArrayInputStream byteArrayInputStream) {
        byteArrayInputStream.mark(1);
        int n = byteArrayInputStream.read();
        byteArrayInputStream.reset();
        if (n > 127) {
            return PduParser.parseShortInteger(byteArrayInputStream);
        }
        return PduParser.parseLongInteger(byteArrayInputStream);
    }

    protected static long parseLongInteger(ByteArrayInputStream byteArrayInputStream) {
        int n = byteArrayInputStream.read();
        int n2 = n & 255;
        if (n2 <= 8) {
            long l = 0L;
            for (n = 0; n < n2; ++n) {
                int n3 = byteArrayInputStream.read();
                l = (l << 8) + (long)(n3 & 255);
            }
            return l;
        }
        throw new RuntimeException("Octet count greater than 8 and I can't represent that!");
    }

    protected static int parseShortInteger(ByteArrayInputStream byteArrayInputStream) {
        int n = byteArrayInputStream.read();
        return n & 127;
    }

    protected static int parseUnsignedInt(ByteArrayInputStream byteArrayInputStream) {
        int n;
        int n2 = 0;
        int n3 = n = byteArrayInputStream.read();
        if (n == -1) {
            return n;
        }
        while ((n3 & 128) != 0) {
            n2 = n2 << 7 | n3 & 127;
            n3 = n = byteArrayInputStream.read();
            if (n != -1) continue;
            return n;
        }
        return n2 << 7 | n3 & 127;
    }

    protected static int parseValueLength(ByteArrayInputStream byteArrayInputStream) {
        int n = byteArrayInputStream.read();
        if ((n &= 255) <= 30) {
            return n;
        }
        if (n == 31) {
            return PduParser.parseUnsignedInt(byteArrayInputStream);
        }
        throw new RuntimeException("Value length > LENGTH_QUOTE!");
    }

    protected static byte[] parseWapString(ByteArrayInputStream byteArrayInputStream, int n) {
        byteArrayInputStream.mark(1);
        int n2 = byteArrayInputStream.read();
        if (1 == n && 34 == n2) {
            byteArrayInputStream.mark(1);
        } else if (n == 0 && 127 == n2) {
            byteArrayInputStream.mark(1);
        } else {
            byteArrayInputStream.reset();
        }
        return PduParser.getWapString(byteArrayInputStream, n);
    }

    protected static int skipWapValue(ByteArrayInputStream byteArrayInputStream, int n) {
        int n2 = byteArrayInputStream.read(new byte[n], 0, n);
        if (n2 < n) {
            return -1;
        }
        return n2;
    }

    public GenericPdu parse() {
        Object object = this.mPduDataStream;
        if (object == null) {
            return null;
        }
        this.mHeaders = this.parseHeaders((ByteArrayInputStream)object);
        object = this.mHeaders;
        if (object == null) {
            return null;
        }
        int n = ((PduHeaders)object).getOctet(140);
        if (!PduParser.checkMandatoryHeader(this.mHeaders)) {
            PduParser.log("check mandatory headers failed!");
            return null;
        }
        if (128 == n || 132 == n) {
            this.mBody = this.parseParts(this.mPduDataStream);
            if (this.mBody == null) {
                return null;
            }
        }
        switch (n) {
            default: {
                PduParser.log("Parser doesn't support this message type in this version!");
                return null;
            }
            case 136: {
                return new ReadOrigInd(this.mHeaders);
            }
            case 135: {
                return new ReadRecInd(this.mHeaders);
            }
            case 134: {
                return new DeliveryInd(this.mHeaders);
            }
            case 133: {
                return new AcknowledgeInd(this.mHeaders);
            }
            case 132: {
                object = new RetrieveConf(this.mHeaders, this.mBody);
                Object object2 = ((RetrieveConf)object).getContentType();
                if (object2 == null) {
                    return null;
                }
                if (!(((String)(object2 = new String((byte[])object2))).equals("application/vnd.wap.multipart.mixed") || ((String)object2).equals("application/vnd.wap.multipart.related") || ((String)object2).equals("application/vnd.wap.multipart.alternative"))) {
                    if (((String)object2).equals("application/vnd.wap.multipart.alternative")) {
                        object2 = this.mBody.getPart(0);
                        this.mBody.removeAll();
                        this.mBody.addPart(0, (PduPart)object2);
                        return object;
                    }
                    return null;
                }
                return object;
            }
            case 131: {
                return new NotifyRespInd(this.mHeaders);
            }
            case 130: {
                return new NotificationInd(this.mHeaders);
            }
            case 129: {
                return new SendConf(this.mHeaders);
            }
            case 128: 
        }
        return new SendReq(this.mHeaders, this.mBody);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected PduHeaders parseHeaders(ByteArrayInputStream var1_1) {
        if (var1_1 == null) {
            return null;
        }
        var2_50 = new PduHeaders();
        var3_51 = 1;
        while (var3_51 != 0) {
            if (var1_1.available() <= 0) return var2_50;
            var1_1.mark(1);
            var4_52 = PduParser.extractByteValue(var1_1);
            if (var4_52 >= 32 && var4_52 <= 127) {
                var1_1.reset();
                PduParser.parseWapString(var1_1, 0);
                continue;
            }
            switch (var4_52) {
                default: {
                    PduParser.log("Unknown header");
                    var5_53 = var3_51;
                    break;
                }
                case 178: {
                    PduParser.parseContentType(var1_1, null);
                    var5_53 = var3_51;
                    break;
                }
                case 173: 
                case 175: 
                case 179: {
                    try {
                        var2_50.setLongInteger(PduParser.parseIntegerValue(var1_1), var4_52);
                        var5_53 = var3_51;
                        break;
                    }
                    catch (RuntimeException var1_2) {
                        var1_3 = new StringBuilder();
                        var1_3.append(var4_52);
                        var1_3.append("is not Long-Integer header field!");
                        PduParser.log(var1_3.toString());
                        return null;
                    }
                }
                case 170: 
                case 172: {
                    PduParser.parseValueLength(var1_1);
                    PduParser.extractByteValue(var1_1);
                    try {
                        PduParser.parseIntegerValue(var1_1);
                        var5_53 = var3_51;
                        break;
                    }
                    catch (RuntimeException var1_4) {
                        var1_5 = new StringBuilder();
                        var1_5.append(var4_52);
                        var1_5.append(" is not Integer-Value");
                        PduParser.log(var1_5.toString());
                        return null;
                    }
                }
                case 164: {
                    PduParser.parseValueLength(var1_1);
                    PduParser.extractByteValue(var1_1);
                    PduParser.parseEncodedStringValue(var1_1);
                    var5_53 = var3_51;
                    break;
                }
                case 161: {
                    PduParser.parseValueLength(var1_1);
                    try {
                        PduParser.parseIntegerValue(var1_1);
                    }
                    catch (RuntimeException var1_8) {
                        var1_9 = new StringBuilder();
                        var1_9.append(var4_52);
                        var1_9.append(" is not Integer-Value");
                        PduParser.log(var1_9.toString());
                        return null;
                    }
                    try {
                        var2_50.setLongInteger(PduParser.parseLongInteger(var1_1), 161);
                        var5_53 = var3_51;
                        break;
                    }
                    catch (RuntimeException var1_6) {
                        var1_7 = new StringBuilder();
                        var1_7.append(var4_52);
                        var1_7.append("is not Long-Integer header field!");
                        PduParser.log(var1_7.toString());
                        return null;
                    }
                }
                case 160: {
                    PduParser.parseValueLength(var1_1);
                    try {
                        PduParser.parseIntegerValue(var1_1);
                    }
                    catch (RuntimeException var1_12) {
                        var1_13 = new StringBuilder();
                        var1_13.append(var4_52);
                        var1_13.append(" is not Integer-Value");
                        PduParser.log(var1_13.toString());
                        return null;
                    }
                    var6_54 = PduParser.parseEncodedStringValue(var1_1);
                    var5_53 = var3_51;
                    if (var6_54 == null) break;
                    try {
                        var2_50.setEncodedStringValue((EncodedStringValue)var6_54, 160);
                    }
                    catch (RuntimeException var1_10) {
                        var1_11 = new StringBuilder();
                        var1_11.append(var4_52);
                        var1_11.append("is not Encoded-String-Value header field!");
                        PduParser.log(var1_11.toString());
                        return null;
                    }
                    catch (NullPointerException var6_55) {
                        PduParser.log("null pointer error!");
                    }
                    var5_53 = var3_51;
                    break;
                }
                case 147: 
                case 150: 
                case 154: 
                case 166: 
                case 181: 
                case 182: {
                    var6_54 = PduParser.parseEncodedStringValue(var1_1);
                    var5_53 = var3_51;
                    if (var6_54 == null) break;
                    try {
                        var2_50.setEncodedStringValue((EncodedStringValue)var6_54, var4_52);
                    }
                    catch (RuntimeException var1_14) {
                        var1_15 = new StringBuilder();
                        var1_15.append(var4_52);
                        var1_15.append("is not Encoded-String-Value header field!");
                        PduParser.log(var1_15.toString());
                        return null;
                    }
                    catch (NullPointerException var6_56) {
                        PduParser.log("null pointer error!");
                    }
                    var5_53 = var3_51;
                    break;
                }
                case 141: {
                    var5_53 = PduParser.parseShortInteger(var1_1);
                    try {
                        var2_50.setOctet(var5_53, 141);
                        var5_53 = var3_51;
                        break;
                    }
                    catch (RuntimeException var1_16) {
                        var1_17 = new StringBuilder();
                        var1_17.append(var4_52);
                        var1_17.append("is not Octet header field!");
                        PduParser.log(var1_17.toString());
                        return null;
                    }
                    catch (InvalidHeaderValueException var1_18) {
                        var1_19 = new StringBuilder();
                        var1_19.append("Set invalid Octet value: ");
                        var1_19.append(var5_53);
                        var1_19.append(" into the header filed: ");
                        var1_19.append(var4_52);
                        PduParser.log(var1_19.toString());
                        return null;
                    }
                }
                case 140: {
                    var5_53 = PduParser.extractByteValue(var1_1);
                    switch (var5_53) {
                        default: {
                            var2_50.setOctet(var5_53, var4_52);
                            ** break;
                        }
                        case 137: 
                        case 138: 
                        case 139: 
                        case 140: 
                        case 141: 
                        case 142: 
                        case 143: 
                        case 144: 
                        case 145: 
                        case 146: 
                        case 147: 
                        case 148: 
                        case 149: 
                        case 150: 
                        case 151: 
                    }
                    return null;
lbl176: // 1 sources:
                    var5_53 = var3_51;
                    break;
                    catch (RuntimeException var1_20) {
                        var1_21 = new StringBuilder();
                        var1_21.append(var4_52);
                        var1_21.append("is not Octet header field!");
                        PduParser.log(var1_21.toString());
                        return null;
                    }
                    catch (InvalidHeaderValueException var1_22) {
                        var1_23 = new StringBuilder();
                        var1_23.append("Set invalid Octet value: ");
                        var1_23.append(var5_53);
                        var1_23.append(" into the header filed: ");
                        var1_23.append(var4_52);
                        PduParser.log(var1_23.toString());
                        return null;
                    }
                }
                case 138: {
                    var1_1.mark(1);
                    var5_53 = PduParser.extractByteValue(var1_1);
                    if (var5_53 < 128) ** GOTO lbl229
                    if (128 != var5_53) ** GOTO lbl210
                    try {
                        var2_50.setTextString("personal".getBytes(), 138);
                        ** GOTO lbl227
                    }
                    catch (RuntimeException var1_24) {
                        ** GOTO lbl219
                    }
                    catch (NullPointerException var6_57) {
                        ** GOTO lbl226
                    }
lbl210: // 1 sources:
                    if (129 == var5_53) {
                        var2_50.setTextString("advertisement".getBytes(), 138);
                    } else if (130 == var5_53) {
                        var2_50.setTextString("informational".getBytes(), 138);
                    } else if (131 == var5_53) {
                        var2_50.setTextString("auto".getBytes(), 138);
                    }
                    ** GOTO lbl227
lbl219: // 1 sources:
                    var1_25 = new StringBuilder();
                    var1_25.append(var4_52);
                    var1_25.append("is not Text-String header field!");
                    PduParser.log(var1_25.toString());
                    return null;
lbl226: // 1 sources:
                    PduParser.log("null pointer error!");
lbl227: // 5 sources:
                    var5_53 = var3_51;
                    break;
lbl229: // 1 sources:
                    var1_1.reset();
                    var6_54 = PduParser.parseWapString(var1_1, 0);
                    if (var6_54 != null) {
                        try {
                            var2_50.setTextString((byte[])var6_54, 138);
                        }
                        catch (RuntimeException var1_26) {
                            var1_27 = new StringBuilder();
                            var1_27.append(var4_52);
                            var1_27.append("is not Text-String header field!");
                            PduParser.log(var1_27.toString());
                            return null;
                        }
                        catch (NullPointerException var6_58) {
                            PduParser.log("null pointer error!");
                        }
                    }
                    var5_53 = var3_51;
                    break;
                }
                case 137: {
                    PduParser.parseValueLength(var1_1);
                    if (128 == PduParser.extractByteValue(var1_1)) {
                        var6_54 = var7_62 = PduParser.parseEncodedStringValue(var1_1);
                        if (var7_62 != null) {
                            var6_54 = var7_62.getTextString();
                            if (var6_54 != null) {
                                var5_53 = (var6_54 = new String((byte[])var6_54)).indexOf("/");
                                if (var5_53 > 0) {
                                    var6_54 = var6_54.substring(0, var5_53);
                                }
                                try {
                                    var7_62.setTextString(var6_54.getBytes());
                                }
                                catch (NullPointerException var1_28) {
                                    PduParser.log("null pointer error!");
                                    return null;
                                }
                            }
                            var6_54 = var7_62;
                        }
                    } else {
                        var6_54 = new EncodedStringValue("insert-address-token".getBytes());
                    }
                    try {
                        var2_50.setEncodedStringValue((EncodedStringValue)var6_54, 137);
                    }
                    catch (RuntimeException var1_29) {
                        var1_30 = new StringBuilder();
                        var1_30.append(var4_52);
                        var1_30.append("is not Encoded-String-Value header field!");
                        PduParser.log(var1_30.toString());
                        return null;
                    }
                    catch (NullPointerException var6_59) {
                        PduParser.log("null pointer error!");
                    }
                    var5_53 = var3_51;
                    break;
                    catch (NullPointerException var1_31) {
                        var1_32 = new StringBuilder();
                        var1_32.append(var4_52);
                        var1_32.append("is not Encoded-String-Value header field!");
                        PduParser.log(var1_32.toString());
                        return null;
                    }
                }
                case 135: 
                case 136: 
                case 157: {
                    PduParser.parseValueLength(var1_1);
                    var5_53 = PduParser.extractByteValue(var1_1);
                    try {
                        var10_65 = var8_64 = PduParser.parseLongInteger(var1_1);
                        if (129 != var5_53) ** GOTO lbl308
                    }
                    catch (RuntimeException var1_35) {
                        var1_36 = new StringBuilder();
                        var1_36.append(var4_52);
                        var1_36.append("is not Long-Integer header field!");
                        PduParser.log(var1_36.toString());
                        return null;
                    }
                    var10_65 = var8_64 + System.currentTimeMillis() / 1000L;
lbl308: // 2 sources:
                    try {
                        var2_50.setLongInteger(var10_65, var4_52);
                        var5_53 = var3_51;
                        break;
                    }
                    catch (RuntimeException var1_33) {
                        var1_34 = new StringBuilder();
                        var1_34.append(var4_52);
                        var1_34.append("is not Long-Integer header field!");
                        PduParser.log(var1_34.toString());
                        return null;
                    }
                }
                case 134: 
                case 143: 
                case 144: 
                case 145: 
                case 146: 
                case 148: 
                case 149: 
                case 153: 
                case 155: 
                case 156: 
                case 162: 
                case 163: 
                case 165: 
                case 167: 
                case 169: 
                case 171: 
                case 177: 
                case 180: 
                case 186: 
                case 187: 
                case 188: 
                case 191: {
                    var5_53 = PduParser.extractByteValue(var1_1);
                    try {
                        var2_50.setOctet(var5_53, var4_52);
                        var5_53 = var3_51;
                        break;
                    }
                    catch (RuntimeException var1_37) {
                        var1_38 = new StringBuilder();
                        var1_38.append(var4_52);
                        var1_38.append("is not Octet header field!");
                        PduParser.log(var1_38.toString());
                        return null;
                    }
                    catch (InvalidHeaderValueException var1_39) {
                        var1_40 = new StringBuilder();
                        var1_40.append("Set invalid Octet value: ");
                        var1_40.append(var5_53);
                        var1_40.append(" into the header filed: ");
                        var1_40.append(var4_52);
                        PduParser.log(var1_40.toString());
                        return null;
                    }
                }
                case 133: 
                case 142: 
                case 159: {
                    try {
                        var2_50.setLongInteger(PduParser.parseLongInteger(var1_1), var4_52);
                        var5_53 = var3_51;
                        break;
                    }
                    catch (RuntimeException var1_41) {
                        var1_42 = new StringBuilder();
                        var1_42.append(var4_52);
                        var1_42.append("is not Long-Integer header field!");
                        PduParser.log(var1_42.toString());
                        return null;
                    }
                }
                case 132: {
                    var6_54 = new HashMap<K, V>();
                    var7_62 = PduParser.parseContentType(var1_1, (HashMap<Integer, Object>)var6_54);
                    if (var7_62 != null) {
                        try {
                            var2_50.setTextString((byte[])var7_62, 132);
                        }
                        catch (RuntimeException var1_43) {
                            var1_44 = new StringBuilder();
                            var1_44.append(var4_52);
                            var1_44.append("is not Text-String header field!");
                            PduParser.log(var1_44.toString());
                            return null;
                        }
                        catch (NullPointerException var7_63) {
                            PduParser.log("null pointer error!");
                        }
                    }
                    PduParser.mStartParam = (byte[])var6_54.get(153);
                    PduParser.mTypeParam = (byte[])var6_54.get(131);
                    var5_53 = 0;
                    break;
                }
                case 131: 
                case 139: 
                case 152: 
                case 158: 
                case 183: 
                case 184: 
                case 185: 
                case 189: 
                case 190: {
                    var6_54 = PduParser.parseWapString(var1_1, 0);
                    var5_53 = var3_51;
                    if (var6_54 == null) break;
                    try {
                        var2_50.setTextString((byte[])var6_54, var4_52);
                    }
                    catch (RuntimeException var1_45) {
                        var1_46 = new StringBuilder();
                        var1_46.append(var4_52);
                        var1_46.append("is not Text-String header field!");
                        PduParser.log(var1_46.toString());
                        return null;
                    }
                    catch (NullPointerException var6_60) {
                        PduParser.log("null pointer error!");
                    }
                    var5_53 = var3_51;
                    break;
                }
                case 129: 
                case 130: 
                case 151: {
                    var7_62 = PduParser.parseEncodedStringValue(var1_1);
                    var5_53 = var3_51;
                    if (var7_62 == null) break;
                    var6_54 = var7_62.getTextString();
                    if (var6_54 != null) {
                        var5_53 = (var6_54 = new String((byte[])var6_54)).indexOf("/");
                        if (var5_53 > 0) {
                            var6_54 = var6_54.substring(0, var5_53);
                        }
                        try {
                            var7_62.setTextString(var6_54.getBytes());
                        }
                        catch (NullPointerException var1_47) {
                            PduParser.log("null pointer error!");
                            return null;
                        }
                    }
                    try {
                        var2_50.appendEncodedStringValue((EncodedStringValue)var7_62, var4_52);
                    }
                    catch (RuntimeException var1_48) {
                        var1_49 = new StringBuilder();
                        var1_49.append(var4_52);
                        var1_49.append("is not Encoded-String-Value header field!");
                        PduParser.log(var1_49.toString());
                        return null;
                    }
                    catch (NullPointerException var6_61) {
                        PduParser.log("null pointer error!");
                    }
                    var5_53 = var3_51;
                    break;
                }
            }
            var3_51 = var5_53;
        }
        return var2_50;
    }

    protected boolean parsePartHeaders(ByteArrayInputStream byteArrayInputStream, PduPart pduPart, int n) {
        int n2 = byteArrayInputStream.available();
        int n3 = n;
        while (n3 > 0) {
            byte[] arrby;
            int n4 = byteArrayInputStream.read();
            --n3;
            if (n4 > 127) {
                if (n4 != 142) {
                    if (n4 != 174) {
                        if (n4 != 192) {
                            if (n4 != 197) {
                                if (-1 == PduParser.skipWapValue(byteArrayInputStream, n3)) {
                                    Log.e((String)LOG_TAG, (String)"Corrupt Part headers");
                                    return false;
                                }
                                n3 = 0;
                                continue;
                            }
                        } else {
                            arrby = PduParser.parseWapString(byteArrayInputStream, 1);
                            if (arrby != null) {
                                pduPart.setContentId(arrby);
                            }
                            n3 = n - (n2 - byteArrayInputStream.available());
                            continue;
                        }
                    }
                    if (!this.mParseContentDisposition) continue;
                    n3 = PduParser.parseValueLength(byteArrayInputStream);
                    byteArrayInputStream.mark(1);
                    n4 = byteArrayInputStream.available();
                    int n5 = byteArrayInputStream.read();
                    if (n5 == 128) {
                        pduPart.setContentDisposition(PduPart.DISPOSITION_FROM_DATA);
                    } else if (n5 == 129) {
                        pduPart.setContentDisposition(PduPart.DISPOSITION_ATTACHMENT);
                    } else if (n5 == 130) {
                        pduPart.setContentDisposition(PduPart.DISPOSITION_INLINE);
                    } else {
                        byteArrayInputStream.reset();
                        pduPart.setContentDisposition(PduParser.parseWapString(byteArrayInputStream, 0));
                    }
                    if (n4 - byteArrayInputStream.available() < n3) {
                        if (byteArrayInputStream.read() == 152) {
                            pduPart.setFilename(PduParser.parseWapString(byteArrayInputStream, 0));
                        }
                        if (n4 - (n5 = byteArrayInputStream.available()) < n3) {
                            byteArrayInputStream.read(new byte[n3 -= n4 - n5], 0, n3);
                        }
                    }
                    n3 = n - (n2 - byteArrayInputStream.available());
                    continue;
                }
                arrby = PduParser.parseWapString(byteArrayInputStream, 0);
                if (arrby != null) {
                    pduPart.setContentLocation(arrby);
                }
                n3 = n - (n2 - byteArrayInputStream.available());
                continue;
            }
            if (n4 >= 32 && n4 <= 127) {
                arrby = PduParser.parseWapString(byteArrayInputStream, 0);
                byte[] arrby2 = PduParser.parseWapString(byteArrayInputStream, 0);
                if ("Content-Transfer-Encoding".equalsIgnoreCase(new String(arrby))) {
                    pduPart.setContentTransferEncoding(arrby2);
                }
                n3 = n - (n2 - byteArrayInputStream.available());
                continue;
            }
            if (-1 == PduParser.skipWapValue(byteArrayInputStream, n3)) {
                Log.e((String)LOG_TAG, (String)"Corrupt Part headers");
                return false;
            }
            n3 = 0;
        }
        if (n3 != 0) {
            Log.e((String)LOG_TAG, (String)"Corrupt Part headers");
            return false;
        }
        return true;
    }

    protected PduBody parseParts(ByteArrayInputStream byteArrayInputStream) {
        Object object = null;
        if (byteArrayInputStream == null) {
            return null;
        }
        int n = PduParser.parseUnsignedInt(byteArrayInputStream);
        PduBody pduBody = new PduBody();
        int n2 = 0;
        do {
            Object object2 = this;
            if (n2 >= n) break;
            int n3 = PduParser.parseUnsignedInt(byteArrayInputStream);
            int n4 = PduParser.parseUnsignedInt(byteArrayInputStream);
            PduPart pduPart = new PduPart();
            int n5 = byteArrayInputStream.available();
            if (n5 <= 0) {
                return object;
            }
            Serializable serializable = new HashMap<Integer, Object>();
            byte[] arrby = PduParser.parseContentType(byteArrayInputStream, serializable);
            if (arrby != null) {
                pduPart.setContentType(arrby);
            } else {
                pduPart.setContentType(PduContentTypes.contentTypes[0].getBytes());
            }
            arrby = (byte[])((HashMap)serializable).get(151);
            if (arrby != null) {
                pduPart.setName(arrby);
            }
            if ((serializable = (Integer)((HashMap)serializable).get(129)) != null) {
                pduPart.setCharset((Integer)serializable);
            }
            if ((n3 -= n5 - byteArrayInputStream.available()) > 0 ? !((PduParser)object2).parsePartHeaders(byteArrayInputStream, pduPart, n3) : n3 < 0) {
                return object;
            }
            if (pduPart.getContentLocation() == null && pduPart.getName() == null && pduPart.getFilename() == null && pduPart.getContentId() == null) {
                pduPart.setContentLocation(Long.toOctalString(System.currentTimeMillis()).getBytes());
            }
            if (n4 > 0) {
                serializable = new byte[n4];
                object = new String(pduPart.getContentType());
                byteArrayInputStream.read((byte[])serializable, 0, n4);
                if (object.equalsIgnoreCase("application/vnd.wap.multipart.alternative")) {
                    object = ((PduParser)object2).parseParts(new ByteArrayInputStream((byte[])serializable)).getPart(0);
                } else {
                    object = pduPart.getContentTransferEncoding();
                    if (object != null) {
                        object2 = new String((byte[])object);
                        if (((String)object2).equalsIgnoreCase("base64")) {
                            object = Base64.decodeBase64((byte[])serializable);
                        } else {
                            object = serializable;
                            if (((String)object2).equalsIgnoreCase("quoted-printable")) {
                                object = QuotedPrintable.decodeQuotedPrintable((byte[])serializable);
                            }
                        }
                    } else {
                        object = serializable;
                    }
                    if (object == null) {
                        PduParser.log("Decode part data error!");
                        return null;
                    }
                    pduPart.setData((byte[])object);
                    object = pduPart;
                }
            } else {
                object = pduPart;
            }
            if (PduParser.checkPartPosition((PduPart)object) == 0) {
                pduBody.addPart(0, (PduPart)object);
            } else {
                pduBody.addPart((PduPart)object);
            }
            ++n2;
            object = null;
        } while (true);
        return pduBody;
    }
}

