/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.gsm;

import android.content.Context;
import android.content.res.Resources;
import android.telephony.SmsCbCmasInfo;
import android.telephony.SmsCbEtwsInfo;
import android.telephony.SmsCbLocation;
import android.telephony.SmsCbMessage;
import android.util.Pair;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.gsm.SmsCbHeader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class GsmSmsCbMessage {
    private static final char CARRIAGE_RETURN = '\r';
    private static final String[] LANGUAGE_CODES_GROUP_0 = new String[]{Locale.GERMAN.getLanguage(), Locale.ENGLISH.getLanguage(), Locale.ITALIAN.getLanguage(), Locale.FRENCH.getLanguage(), new Locale("es").getLanguage(), new Locale("nl").getLanguage(), new Locale("sv").getLanguage(), new Locale("da").getLanguage(), new Locale("pt").getLanguage(), new Locale("fi").getLanguage(), new Locale("nb").getLanguage(), new Locale("el").getLanguage(), new Locale("tr").getLanguage(), new Locale("hu").getLanguage(), new Locale("pl").getLanguage(), null};
    private static final String[] LANGUAGE_CODES_GROUP_2 = new String[]{new Locale("cs").getLanguage(), new Locale("he").getLanguage(), new Locale("ar").getLanguage(), new Locale("ru").getLanguage(), new Locale("is").getLanguage(), null, null, null, null, null, null, null, null, null, null, null};
    private static final int PDU_BODY_PAGE_LENGTH = 82;

    private GsmSmsCbMessage() {
    }

    public static SmsCbMessage createSmsCbMessage(Context object, SmsCbHeader smsCbHeader, SmsCbLocation smsCbLocation, byte[][] arrby) throws IllegalArgumentException {
        int n;
        if (smsCbHeader.isEtwsPrimaryNotification()) {
            return new SmsCbMessage(1, smsCbHeader.getGeographicalScope(), smsCbHeader.getSerialNumber(), smsCbLocation, smsCbHeader.getServiceCategory(), null, GsmSmsCbMessage.getEtwsPrimaryMessage((Context)object, smsCbHeader.getEtwsInfo().getWarningType()), 3, smsCbHeader.getEtwsInfo(), smsCbHeader.getCmasInfo());
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = arrby.length;
        object = null;
        for (n = 0; n < n2; ++n) {
            Pair<String, String> pair = GsmSmsCbMessage.parseBody(smsCbHeader, arrby[n]);
            object = (String)pair.first;
            stringBuilder.append((String)pair.second);
        }
        n = smsCbHeader.isEmergencyMessage() ? 3 : 0;
        return new SmsCbMessage(1, smsCbHeader.getGeographicalScope(), smsCbHeader.getSerialNumber(), smsCbLocation, smsCbHeader.getServiceCategory(), (String)object, stringBuilder.toString(), n, smsCbHeader.getEtwsInfo(), smsCbHeader.getCmasInfo());
    }

    private static String getEtwsPrimaryMessage(Context object, int n) {
        object = ((Context)object).getResources();
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return "";
                        }
                        return ((Resources)object).getString(17039904);
                    }
                    return ((Resources)object).getString(17039905);
                }
                return ((Resources)object).getString(17039903);
            }
            return ((Resources)object).getString(17039906);
        }
        return ((Resources)object).getString(17039902);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Pair<String, String> parseBody(SmsCbHeader var0, byte[] var1_1) {
        block15 : {
            var2_2 = var0.getDataCodingScheme();
            var3_3 = (var2_2 & 240) >> 4;
            if (var3_3 == 9 || var3_3 == 14) break block15;
            if (var3_3 == 15) ** GOTO lbl52
            switch (var3_3) {
                default: {
                    var4_4 = null;
                    var5_5 = false;
                    var3_3 = 1;
                    break;
                }
                case 4: 
                case 5: {
                    var3_3 = (var2_2 & 12) >> 2;
                    if (var3_3 != 1) {
                        if (var3_3 != 2) {
                            var4_4 = null;
                            var5_5 = false;
                            var3_3 = 1;
                            break;
                        }
                        var4_4 = null;
                        var5_5 = false;
                        var3_3 = 3;
                        break;
                    }
                    var4_4 = null;
                    var5_5 = false;
                    var3_3 = 2;
                    break;
                }
                case 3: {
                    var4_4 = null;
                    var5_5 = false;
                    var3_3 = 1;
                    break;
                }
                case 2: {
                    var4_4 = GsmSmsCbMessage.LANGUAGE_CODES_GROUP_2[var2_2 & 15];
                    var5_5 = false;
                    var3_3 = 1;
                    break;
                }
                case 1: {
                    if ((var2_2 & 15) == 1) {
                        var4_4 = null;
                        var5_5 = true;
                        var3_3 = 3;
                        break;
                    }
                    var4_4 = null;
                    var5_5 = true;
                    var3_3 = 1;
                    break;
                }
                case 0: {
                    var4_4 = GsmSmsCbMessage.LANGUAGE_CODES_GROUP_0[var2_2 & 15];
                    var5_5 = false;
                    var3_3 = 1;
                    break;
                }
lbl52: // 1 sources:
                if ((var2_2 & 4) >> 2 == 1) {
                    var4_4 = null;
                    var5_5 = false;
                    var3_3 = 2;
                    break;
                }
                var4_4 = null;
                var5_5 = false;
                var3_3 = 1;
            }
            if (!var0.isUmtsFormat()) return GsmSmsCbMessage.unpackBody(var1_1, var3_3, 6, var1_1.length - 6, var5_5, var4_4);
            var6_6 = var1_1[6];
            if (var1_1.length >= var6_6 * 83 + 7) {
                var0 = new StringBuilder();
                for (var2_2 = 0; var2_2 < var6_6; ++var2_2) {
                    var7_7 = var2_2 * 83 + 7;
                    var8_8 = var1_1[var7_7 + 82];
                    if (var8_8 <= 82) {
                        var9_9 = GsmSmsCbMessage.unpackBody(var1_1, var3_3, var7_7, var8_8, var5_5, var4_4);
                        var4_4 = (String)var9_9.first;
                        var0.append((String)var9_9.second);
                        continue;
                    }
                    var0 = new StringBuilder();
                    var0.append("Page length ");
                    var0.append(var8_8);
                    var0.append(" exceeds maximum value ");
                    var0.append(82);
                    throw new IllegalArgumentException(var0.toString());
                }
                return new Pair<String, String>(var4_4, var0.toString());
            }
            var0 = new StringBuilder();
            var0.append("Pdu length ");
            var0.append(var1_1.length);
            var0.append(" does not match ");
            var0.append(var6_6);
            var0.append(" pages");
            throw new IllegalArgumentException(var0.toString());
            {
                ** case 6:
                ** case 7:
            }
        }
        var0 = new StringBuilder();
        var0.append("Unsupported GSM dataCodingScheme ");
        var0.append(var2_2);
        throw new IllegalArgumentException(var0.toString());
    }

    private static Pair<String, String> unpackBody(byte[] object, int n, int n2, int n3, boolean bl, String object2) {
        Object object3;
        block14 : {
            object3 = null;
            if (n != 1) {
                if (n != 3) {
                    object = object3;
                    object3 = object2;
                } else {
                    int n4 = n2;
                    n = n3;
                    object3 = object2;
                    if (bl) {
                        n4 = n2;
                        n = n3;
                        object3 = object2;
                        if (((byte[])object).length >= n2 + 2) {
                            object3 = GsmAlphabet.gsm7BitPackedToString((byte[])object, n2, 2);
                            n4 = n2 + 2;
                            n = n3 - 2;
                        }
                    }
                    try {
                        object = new String((byte[])object, n4, 65534 & n, "utf-16");
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        throw new IllegalArgumentException("Error decoding UTF-16 message", unsupportedEncodingException);
                    }
                }
            } else {
                String string2 = GsmAlphabet.gsm7BitPackedToString((byte[])object, n2, n3 * 8 / 7);
                object = string2;
                object3 = object2;
                if (bl) {
                    object = string2;
                    object3 = object2;
                    if (string2 != null) {
                        object = string2;
                        object3 = object2;
                        if (string2.length() > 2) {
                            object3 = string2.substring(0, 2);
                            object = string2.substring(3);
                        }
                    }
                }
            }
            if (object != null) {
                n = ((String)object).length() - 1;
                do {
                    object2 = object;
                    if (n < 0) break block14;
                    if (((String)object).charAt(n) != '\r') {
                        object2 = ((String)object).substring(0, n + 1);
                        break block14;
                    }
                    --n;
                } while (true);
            }
            object2 = "";
        }
        return new Pair<byte[], byte[]>((byte[])object3, (byte[])object2);
    }
}

