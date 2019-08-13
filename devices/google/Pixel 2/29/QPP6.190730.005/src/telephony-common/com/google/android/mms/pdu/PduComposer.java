/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.google.android.mms.pdu;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.mms.pdu.EncodedStringValue;
import com.google.android.mms.pdu.GenericPdu;
import com.google.android.mms.pdu.PduBody;
import com.google.android.mms.pdu.PduContentTypes;
import com.google.android.mms.pdu.PduHeaders;
import com.google.android.mms.pdu.PduPart;
import com.google.android.mms.pdu.RetrieveConf;
import com.google.android.mms.pdu.SendReq;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

public class PduComposer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int END_STRING_FLAG = 0;
    private static final int LENGTH_QUOTE = 31;
    private static final int LONG_INTEGER_LENGTH_MAX = 8;
    private static final int PDU_COMPOSER_BLOCK_SIZE = 1024;
    private static final int PDU_COMPOSE_CONTENT_ERROR = 1;
    private static final int PDU_COMPOSE_FIELD_NOT_SET = 2;
    private static final int PDU_COMPOSE_FIELD_NOT_SUPPORTED = 3;
    private static final int PDU_COMPOSE_SUCCESS = 0;
    private static final int PDU_EMAIL_ADDRESS_TYPE = 2;
    private static final int PDU_IPV4_ADDRESS_TYPE = 3;
    private static final int PDU_IPV6_ADDRESS_TYPE = 4;
    private static final int PDU_PHONE_NUMBER_ADDRESS_TYPE = 1;
    private static final int PDU_UNKNOWN_ADDRESS_TYPE = 5;
    private static final int QUOTED_STRING_FLAG = 34;
    static final String REGEXP_EMAIL_ADDRESS_TYPE = "[a-zA-Z| ]*\\<{0,1}[a-zA-Z| ]+@{1}[a-zA-Z| ]+\\.{1}[a-zA-Z| ]+\\>{0,1}";
    static final String REGEXP_IPV4_ADDRESS_TYPE = "[0-9]{1,3}\\.{1}[0-9]{1,3}\\.{1}[0-9]{1,3}\\.{1}[0-9]{1,3}";
    static final String REGEXP_IPV6_ADDRESS_TYPE = "[a-fA-F]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}\\:{1}[a-fA-F0-9]{4}";
    static final String REGEXP_PHONE_NUMBER_ADDRESS_TYPE = "\\+?[0-9|\\.|\\-]+";
    private static final int SHORT_INTEGER_MAX = 127;
    static final String STRING_IPV4_ADDRESS_TYPE = "/TYPE=IPV4";
    static final String STRING_IPV6_ADDRESS_TYPE = "/TYPE=IPV6";
    static final String STRING_PHONE_NUMBER_ADDRESS_TYPE = "/TYPE=PLMN";
    private static final int TEXT_MAX = 127;
    private static HashMap<String, Integer> mContentTypeMap = null;
    protected ByteArrayOutputStream mMessage = null;
    private GenericPdu mPdu = null;
    private PduHeaders mPduHeader = null;
    protected int mPosition = 0;
    private final ContentResolver mResolver;
    private BufferStack mStack = null;

    static {
        mContentTypeMap = new HashMap();
        for (int i = 0; i < PduContentTypes.contentTypes.length; ++i) {
            mContentTypeMap.put(PduContentTypes.contentTypes[i], i);
        }
    }

    public PduComposer(Context context, GenericPdu genericPdu) {
        this.mPdu = genericPdu;
        this.mResolver = context.getContentResolver();
        this.mPduHeader = genericPdu.getPduHeaders();
        this.mStack = new BufferStack();
        this.mMessage = new ByteArrayOutputStream();
        this.mPosition = 0;
    }

    static /* synthetic */ BufferStack access$100(PduComposer pduComposer) {
        return pduComposer.mStack;
    }

    private EncodedStringValue appendAddressType(EncodedStringValue encodedStringValue) {
        block9 : {
            int n;
            block8 : {
                try {
                    n = PduComposer.checkAddressType(encodedStringValue.getString());
                    encodedStringValue = EncodedStringValue.copy(encodedStringValue);
                    if (1 != n) break block8;
                }
                catch (NullPointerException nullPointerException) {
                    return null;
                }
                encodedStringValue.appendTextString(STRING_PHONE_NUMBER_ADDRESS_TYPE.getBytes());
                break block9;
            }
            if (3 == n) {
                encodedStringValue.appendTextString(STRING_IPV4_ADDRESS_TYPE.getBytes());
            } else if (4 == n) {
                encodedStringValue.appendTextString(STRING_IPV6_ADDRESS_TYPE.getBytes());
            }
        }
        return encodedStringValue;
    }

    private int appendHeader(int n) {
        switch (n) {
            default: {
                return 3;
            }
            case 150: 
            case 154: {
                EncodedStringValue encodedStringValue = this.mPduHeader.getEncodedStringValue(n);
                if (encodedStringValue == null) {
                    return 2;
                }
                this.appendOctet(n);
                this.appendEncodedString(encodedStringValue);
                break;
            }
            case 141: {
                this.appendOctet(n);
                n = this.mPduHeader.getOctet(n);
                if (n == 0) {
                    this.appendShortInteger(18);
                    break;
                }
                this.appendShortInteger(n);
                break;
            }
            case 139: 
            case 152: {
                byte[] arrby = this.mPduHeader.getTextString(n);
                if (arrby == null) {
                    return 2;
                }
                this.appendOctet(n);
                this.appendTextString(arrby);
                break;
            }
            case 138: {
                byte[] arrby = this.mPduHeader.getTextString(n);
                if (arrby == null) {
                    return 2;
                }
                this.appendOctet(n);
                if (Arrays.equals(arrby, "advertisement".getBytes())) {
                    this.appendOctet(129);
                    break;
                }
                if (Arrays.equals(arrby, "auto".getBytes())) {
                    this.appendOctet(131);
                    break;
                }
                if (Arrays.equals(arrby, "personal".getBytes())) {
                    this.appendOctet(128);
                    break;
                }
                if (Arrays.equals(arrby, "informational".getBytes())) {
                    this.appendOctet(130);
                    break;
                }
                this.appendTextString(arrby);
                break;
            }
            case 137: {
                this.appendOctet(n);
                EncodedStringValue encodedStringValue = this.mPduHeader.getEncodedStringValue(n);
                if (encodedStringValue != null && !TextUtils.isEmpty((CharSequence)encodedStringValue.getString()) && !new String(encodedStringValue.getTextString()).equals("insert-address-token")) {
                    this.mStack.newbuf();
                    PositionMarker positionMarker = this.mStack.mark();
                    this.append(128);
                    encodedStringValue = this.appendAddressType(encodedStringValue);
                    if (encodedStringValue == null) {
                        return 1;
                    }
                    this.appendEncodedString(encodedStringValue);
                    n = positionMarker.getLength();
                    this.mStack.pop();
                    this.appendValueLength(n);
                    this.mStack.copy();
                    break;
                }
                this.append(1);
                this.append(129);
                break;
            }
            case 136: {
                long l = this.mPduHeader.getLongInteger(n);
                if (-1L == l) {
                    return 2;
                }
                this.appendOctet(n);
                this.mStack.newbuf();
                PositionMarker positionMarker = this.mStack.mark();
                this.append(129);
                this.appendLongInteger(l);
                n = positionMarker.getLength();
                this.mStack.pop();
                this.appendValueLength(n);
                this.mStack.copy();
                break;
            }
            case 134: 
            case 143: 
            case 144: 
            case 145: 
            case 149: 
            case 153: 
            case 155: {
                int n2 = this.mPduHeader.getOctet(n);
                if (n2 == 0) {
                    return 2;
                }
                this.appendOctet(n);
                this.appendOctet(n2);
                break;
            }
            case 133: {
                long l = this.mPduHeader.getLongInteger(n);
                if (-1L == l) {
                    return 2;
                }
                this.appendOctet(n);
                this.appendDateValue(l);
                break;
            }
            case 129: 
            case 130: 
            case 151: {
                EncodedStringValue[] arrencodedStringValue = this.mPduHeader.getEncodedStringValues(n);
                if (arrencodedStringValue == null) {
                    return 2;
                }
                for (int i = 0; i < arrencodedStringValue.length; ++i) {
                    EncodedStringValue encodedStringValue = this.appendAddressType(arrencodedStringValue[i]);
                    if (encodedStringValue == null) {
                        return 1;
                    }
                    this.appendOctet(n);
                    this.appendEncodedString(encodedStringValue);
                }
            }
        }
        return 0;
    }

    protected static int checkAddressType(String string) {
        if (string == null) {
            return 5;
        }
        if (string.matches(REGEXP_IPV4_ADDRESS_TYPE)) {
            return 3;
        }
        if (string.matches(REGEXP_PHONE_NUMBER_ADDRESS_TYPE)) {
            return 1;
        }
        if (string.matches(REGEXP_EMAIL_ADDRESS_TYPE)) {
            return 2;
        }
        if (string.matches(REGEXP_IPV6_ADDRESS_TYPE)) {
            return 4;
        }
        return 5;
    }

    private int makeAckInd() {
        if (this.mMessage == null) {
            this.mMessage = new ByteArrayOutputStream();
            this.mPosition = 0;
        }
        this.appendOctet(140);
        this.appendOctet(133);
        if (this.appendHeader(152) != 0) {
            return 1;
        }
        if (this.appendHeader(141) != 0) {
            return 1;
        }
        this.appendHeader(145);
        return 0;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int makeMessageBody(int var1_1) {
        block66 : {
            block65 : {
                block64 : {
                    block63 : {
                        block61 : {
                            block68 : {
                                var2_2 = ">";
                                var3_3 = "<";
                                this.mStack.newbuf();
                                var4_4 = this.mStack.mark();
                                var5_5 = new String(this.mPduHeader.getTextString(132));
                                var6_6 = PduComposer.mContentTypeMap.get(var5_5);
                                if (var6_6 == null) {
                                    return 1;
                                }
                                this.appendShortInteger(var6_6);
                                if (var1_1 == 132) {
                                    var6_7 = ((RetrieveConf)this.mPdu).getBody();
                                } else {
                                    var6_8 = ((SendReq)this.mPdu).getBody();
                                }
                                if (var6_9 == null || var6_9.getPartsNum() == 0) break block68;
                                var7_47 = var6_9.getPart(0);
                                var8_50 = var7_47.getContentId();
                                if (var8_50 == null) ** GOTO lbl34
                                this.appendOctet(138);
                                if (60 != var8_50[0]) ** GOTO lbl-1000
                                try {
                                    if (62 == var8_50[((byte[])var8_50).length - 1]) {
                                        this.appendTextString((byte[])var8_50);
                                    } else lbl-1000: // 2 sources:
                                    {
                                        var9_51 = new StringBuilder();
                                        var9_51.append("<");
                                        var10_52 = new String((byte[])var8_50);
                                        var9_51.append((String)var10_52);
                                        var9_51.append(">");
                                        this.appendTextString(var9_51.toString());
                                    }
lbl34: // 3 sources:
                                    this.appendOctet(137);
                                    this.appendTextString(var7_47.getContentType());
                                    break block61;
                                }
                                catch (ArrayIndexOutOfBoundsException var7_48) {
                                    var7_48.printStackTrace();
                                }
                                break block61;
                            }
                            this.appendUintvarInteger(0L);
                            this.mStack.pop();
                            this.mStack.copy();
                            return 0;
                        }
                        var1_1 = var4_4.getLength();
                        this.mStack.pop();
                        this.appendValueLength(var1_1);
                        this.mStack.copy();
                        var11_53 = var6_9.getPartsNum();
                        this.appendUintvarInteger(var11_53);
                        var12_54 = 0;
                        var9_51 = var6_9;
                        while (var12_54 < var11_53) {
                            var13_55 = var9_51.getPart(var12_54);
                            this.mStack.newbuf();
                            var14_56 = this.mStack.mark();
                            this.mStack.newbuf();
                            var7_47 = this.mStack.mark();
                            var6_11 = var13_55.getContentType();
                            if (var6_11 == null) {
                                return 1;
                            }
                            var8_50 = PduComposer.mContentTypeMap.get(new String(var6_11));
                            if (var8_50 == null) {
                                this.appendTextString(var6_11);
                            } else {
                                this.appendShortInteger(var8_50.intValue());
                            }
                            var6_12 = var13_55.getName();
                            if (var6_12 == null && (var6_13 = var13_55.getFilename()) == null && (var6_14 = var13_55.getContentLocation()) == null) {
                                return 1;
                            }
                            this.appendOctet(133);
                            this.appendTextString((byte[])var6_15);
                            var1_1 = var13_55.getCharset();
                            if (var1_1 != 0) {
                                this.appendOctet(129);
                                this.appendShortInteger(var1_1);
                            }
                            var1_1 = var7_47.getLength();
                            this.mStack.pop();
                            this.appendValueLength(var1_1);
                            this.mStack.copy();
                            var6_16 = var13_55.getContentId();
                            if (var6_16 != null) {
                                this.appendOctet(192);
                                if (60 == var6_16[0] && 62 == var6_16[var6_16.length - 1]) {
                                    this.appendQuotedString(var6_16);
                                } else {
                                    var7_47 = new StringBuilder();
                                    var7_47.append(var3_3);
                                    var7_47.append(new String(var6_16));
                                    var7_47.append(var2_2);
                                    this.appendQuotedString(var7_47.toString());
                                }
                            }
                            if ((var15_57 = var13_55.getContentLocation()) != null) {
                                this.appendOctet(142);
                                this.appendTextString(var15_57);
                            }
                            var16_58 = var14_56.getLength();
                            var1_1 = 0;
                            var7_47 = var13_55.getData();
                            if (var7_47 != null) {
                                this.arraycopy((byte[])var7_47, 0, ((byte[])var7_47).length);
                                var1_1 = ((byte[])var7_47).length;
                            } else {
                                block67 : {
                                    block62 : {
                                        var8_50 = null;
                                        var10_52 = null;
                                        var17_59 = null;
                                        var7_47 = null;
                                        var18_60 = new byte[1024];
                                        var19_61 = this.mResolver;
                                        try {
                                            var7_47 = var19_61.openInputStream(var13_55.getDataUri());
                                            var8_50 = var15_57;
                                            break block62;
                                        }
                                        catch (Throwable var6_31) {
                                            var7_47 = null;
                                            break block63;
                                        }
                                        catch (RuntimeException var6_32) {
                                            var7_47 = null;
                                            break block64;
                                        }
                                        catch (IOException var6_33) {
                                            var7_47 = null;
                                            break block65;
                                        }
                                        catch (FileNotFoundException var6_34) {
                                            var7_47 = null;
                                            break block66;
                                        }
                                        catch (Throwable var6_35) {
                                            break block63;
                                        }
                                        catch (RuntimeException var6_36) {
                                            var7_47 = var8_50;
                                            break block64;
                                        }
                                        catch (IOException var6_37) {
                                            var7_47 = var10_52;
                                            break block65;
                                        }
                                        catch (FileNotFoundException var6_38) {
                                            var7_47 = var17_59;
                                            break block66;
                                        }
                                        catch (Throwable var6_39) {
                                            // empty catch block
                                            break block63;
                                        }
                                        catch (RuntimeException var6_41) {
                                            var7_47 = var8_50;
                                            break block64;
                                        }
                                        catch (IOException var6_43) {
                                            var7_47 = var10_52;
                                            break block65;
                                        }
                                        catch (FileNotFoundException var6_45) {
                                            var7_47 = var17_59;
                                            break block66;
                                        }
                                    }
                                    do {
                                        var20_62 = var7_47.read(var18_60);
                                        if (var20_62 == -1) break block67;
                                        var10_52 = this.mMessage;
                                        try {
                                            var10_52.write(var18_60, 0, var20_62);
                                            this.mPosition += var20_62;
                                            var1_1 += var20_62;
                                        }
                                        catch (Throwable var6_17) {
                                            break block63;
                                        }
                                        catch (RuntimeException var6_18) {
                                            break block64;
                                        }
                                        catch (IOException var6_19) {
                                            break block65;
                                        }
                                        catch (FileNotFoundException var6_20) {
                                            break block66;
                                        }
                                    } while (true);
                                    catch (Throwable var6_21) {
                                        break block63;
                                    }
                                    catch (RuntimeException var6_22) {
                                        break block64;
                                    }
                                    catch (IOException var6_23) {
                                        break block65;
                                    }
                                    catch (FileNotFoundException var6_24) {
                                        break block66;
                                    }
                                }
                                try {
                                    var7_47.close();
                                }
                                catch (IOException var6_26) {}
                            }
                            if (var1_1 != var14_56.getLength() - var16_58) throw new RuntimeException("BUG: Length sanity check failed");
                            this.mStack.pop();
                            this.appendUintvarInteger(var16_58);
                            this.appendUintvarInteger(var1_1);
                            this.mStack.copy();
                            ++var12_54;
                        }
                        return 0;
                        catch (Throwable var6_27) {
                        }
                        catch (RuntimeException var6_28) {
                            break block64;
                        }
                        catch (IOException var6_29) {
                            break block65;
                        }
                        catch (FileNotFoundException var6_30) {
                            break block66;
                        }
                    }
                    if (var7_47 == null) throw var6_40;
                    try {
                        var7_47.close();
                        throw var6_40;
                    }
                    catch (IOException var7_49) {
                        // empty catch block
                    }
                    throw var6_40;
                }
                if (var7_47 == null) return 1;
                try {
                    var7_47.close();
                    return 1;
                }
                catch (IOException var6_42) {
                    // empty catch block
                }
                return 1;
            }
            if (var7_47 == null) return 1;
            try {
                var7_47.close();
                return 1;
            }
            catch (IOException var6_44) {
                // empty catch block
            }
            return 1;
        }
        if (var7_47 == null) return 1;
        try {
            var7_47.close();
            return 1;
        }
        catch (IOException var6_46) {
            // empty catch block
        }
        return 1;
    }

    private int makeNotifyResp() {
        if (this.mMessage == null) {
            this.mMessage = new ByteArrayOutputStream();
            this.mPosition = 0;
        }
        this.appendOctet(140);
        this.appendOctet(131);
        if (this.appendHeader(152) != 0) {
            return 1;
        }
        if (this.appendHeader(141) != 0) {
            return 1;
        }
        return this.appendHeader(149) != 0;
    }

    private int makeReadRecInd() {
        if (this.mMessage == null) {
            this.mMessage = new ByteArrayOutputStream();
            this.mPosition = 0;
        }
        this.appendOctet(140);
        this.appendOctet(135);
        if (this.appendHeader(141) != 0) {
            return 1;
        }
        if (this.appendHeader(139) != 0) {
            return 1;
        }
        if (this.appendHeader(151) != 0) {
            return 1;
        }
        if (this.appendHeader(137) != 0) {
            return 1;
        }
        this.appendHeader(133);
        return this.appendHeader(155) != 0;
    }

    private int makeSendRetrievePdu(int n) {
        if (this.mMessage == null) {
            this.mMessage = new ByteArrayOutputStream();
            this.mPosition = 0;
        }
        this.appendOctet(140);
        this.appendOctet(n);
        this.appendOctet(152);
        byte[] arrby = this.mPduHeader.getTextString(152);
        if (arrby != null) {
            this.appendTextString(arrby);
            if (this.appendHeader(141) != 0) {
                return 1;
            }
            this.appendHeader(133);
            if (this.appendHeader(137) != 0) {
                return 1;
            }
            boolean bl = false;
            if (this.appendHeader(151) != 1) {
                bl = true;
            }
            if (this.appendHeader(130) != 1) {
                bl = true;
            }
            if (this.appendHeader(129) != 1) {
                bl = true;
            }
            if (!bl) {
                return 1;
            }
            this.appendHeader(150);
            this.appendHeader(138);
            this.appendHeader(136);
            this.appendHeader(143);
            this.appendHeader(134);
            this.appendHeader(144);
            if (n == 132) {
                this.appendHeader(153);
                this.appendHeader(154);
            }
            this.appendOctet(132);
            return this.makeMessageBody(n);
        }
        throw new IllegalArgumentException("Transaction-ID is null.");
    }

    protected void append(int n) {
        this.mMessage.write(n);
        ++this.mPosition;
    }

    protected void appendDateValue(long l) {
        this.appendLongInteger(l);
    }

    protected void appendEncodedString(EncodedStringValue arrby) {
        int n = arrby.getCharacterSet();
        if ((arrby = arrby.getTextString()) == null) {
            return;
        }
        this.mStack.newbuf();
        PositionMarker positionMarker = this.mStack.mark();
        this.appendShortInteger(n);
        this.appendTextString(arrby);
        n = positionMarker.getLength();
        this.mStack.pop();
        this.appendValueLength(n);
        this.mStack.copy();
    }

    protected void appendLongInteger(long l) {
        int n;
        long l2 = l;
        for (n = 0; l2 != 0L && n < 8; l2 >>>= 8, ++n) {
        }
        this.appendShortLength(n);
        int n2 = (n - 1) * 8;
        for (int i = 0; i < n; ++i) {
            this.append((int)(l >>> n2 & 255L));
            n2 -= 8;
        }
    }

    protected void appendOctet(int n) {
        this.append(n);
    }

    protected void appendQuotedString(String string) {
        this.appendQuotedString(string.getBytes());
    }

    protected void appendQuotedString(byte[] arrby) {
        this.append(34);
        this.arraycopy(arrby, 0, arrby.length);
        this.append(0);
    }

    protected void appendShortInteger(int n) {
        this.append((n | 128) & 255);
    }

    protected void appendShortLength(int n) {
        this.append(n);
    }

    protected void appendTextString(String string) {
        this.appendTextString(string.getBytes());
    }

    protected void appendTextString(byte[] arrby) {
        if ((arrby[0] & 255) > 127) {
            this.append(127);
        }
        this.arraycopy(arrby, 0, arrby.length);
        this.append(0);
    }

    protected void appendUintvarInteger(long l) {
        int n;
        long l2 = 127L;
        int n2 = 0;
        do {
            n = ++n2;
            if (n2 >= 5) break;
            if (l < l2) {
                n = n2;
                break;
            }
            l2 = l2 << 7 | 127L;
        } while (true);
        while (n > 0) {
            this.append((int)((128L | l >>> n * 7 & 127L) & 255L));
            --n;
        }
        this.append((int)(l & 127L));
    }

    protected void appendValueLength(long l) {
        if (l < 31L) {
            this.appendShortLength((int)l);
            return;
        }
        this.append(31);
        this.appendUintvarInteger(l);
    }

    protected void arraycopy(byte[] arrby, int n, int n2) {
        this.mMessage.write(arrby, n, n2);
        this.mPosition += n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     */
    public byte[] make() {
        var1_1 = this.mPdu.getMessageType();
        if (var1_1 == 128) ** GOTO lbl-1000
        if (var1_1 == 135) ** GOTO lbl13
        switch (var1_1) {
            default: {
                return null;
            }
            case 133: {
                if (this.makeAckInd() == 0) break;
                return null;
            }
            case 131: {
                if (this.makeNotifyResp() == 0) break;
                return null;
            }
lbl13: // 1 sources:
            if (this.makeReadRecInd() == 0) break;
            return null;
            case 132: lbl-1000: // 2 sources:
            {
                if (this.makeSendRetrievePdu(var1_1) == 0) break;
                return null;
            }
        }
        return this.mMessage.toByteArray();
    }

    private class BufferStack {
        private LengthRecordNode stack = null;
        int stackSize = 0;
        private LengthRecordNode toCopy = null;

        private BufferStack() {
        }

        void copy() {
            PduComposer.this.arraycopy(this.toCopy.currentMessage.toByteArray(), 0, this.toCopy.currentPosition);
            this.toCopy = null;
        }

        PositionMarker mark() {
            PositionMarker positionMarker = new PositionMarker();
            positionMarker.c_pos = PduComposer.this.mPosition;
            positionMarker.currentStackSize = this.stackSize;
            return positionMarker;
        }

        void newbuf() {
            if (this.toCopy == null) {
                LengthRecordNode lengthRecordNode = new LengthRecordNode();
                lengthRecordNode.currentMessage = PduComposer.this.mMessage;
                lengthRecordNode.currentPosition = PduComposer.this.mPosition;
                lengthRecordNode.next = this.stack;
                this.stack = lengthRecordNode;
                ++this.stackSize;
                PduComposer.this.mMessage = new ByteArrayOutputStream();
                PduComposer.this.mPosition = 0;
                return;
            }
            throw new RuntimeException("BUG: Invalid newbuf() before copy()");
        }

        void pop() {
            LengthRecordNode lengthRecordNode;
            ByteArrayOutputStream byteArrayOutputStream = PduComposer.this.mMessage;
            int n = PduComposer.this.mPosition;
            PduComposer.this.mMessage = this.stack.currentMessage;
            PduComposer.this.mPosition = this.stack.currentPosition;
            this.toCopy = lengthRecordNode = this.stack;
            this.stack = lengthRecordNode.next;
            --this.stackSize;
            lengthRecordNode = this.toCopy;
            lengthRecordNode.currentMessage = byteArrayOutputStream;
            lengthRecordNode.currentPosition = n;
        }
    }

    private static class LengthRecordNode {
        ByteArrayOutputStream currentMessage = null;
        public int currentPosition = 0;
        public LengthRecordNode next = null;

        private LengthRecordNode() {
        }
    }

    private class PositionMarker {
        private int c_pos;
        private int currentStackSize;

        private PositionMarker() {
        }

        int getLength() {
            if (this.currentStackSize == PduComposer.access$100((PduComposer)PduComposer.this).stackSize) {
                return PduComposer.this.mPosition - this.c_pos;
            }
            throw new RuntimeException("BUG: Invalid call to getLength()");
        }
    }

}

