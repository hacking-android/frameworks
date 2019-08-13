/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.telephony.Rlog;
import com.android.internal.telephony.GsmAlphabet;
import java.io.UnsupportedEncodingException;

public class IccUtils {
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    static final String LOG_TAG = "IccUtils";

    public static String adnStringFieldToString(byte[] arrby, int n, int n2) {
        Object object;
        char c;
        int n3;
        int n4;
        boolean bl;
        Object object2;
        if (n2 == 0) {
            return "";
        }
        if (n2 >= 1 && arrby[n] == -128) {
            n4 = (n2 - 1) / 2;
            object = null;
            try {
                object = object2 = new String(arrby, n + 1, n4 * 2, "utf-16be");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                Rlog.e(LOG_TAG, "implausible UnsupportedEncodingException", unsupportedEncodingException);
            }
            if (object != null) {
                for (n = object.length(); n > 0 && ((String)object).charAt(n - 1) == '\uffff'; --n) {
                }
                return ((String)object).substring(0, n);
            }
        }
        boolean bl2 = false;
        char c2 = '\u0000';
        int n5 = 0;
        if (n2 >= 3 && arrby[n] == -127) {
            n4 = n3 = arrby[n + 1] & 255;
            if (n3 > n2 - 3) {
                n4 = n2 - 3;
            }
            c = (char)((arrby[n + 2] & 255) << 7);
            n3 = n + 3;
            bl = true;
        } else {
            bl = bl2;
            c = c2;
            n4 = n5;
            n3 = n;
            if (n2 >= 4) {
                bl = bl2;
                c = c2;
                n4 = n5;
                n3 = n;
                if (arrby[n] == -126) {
                    n4 = n3 = arrby[n + 1] & 255;
                    if (n3 > n2 - 4) {
                        n4 = n2 - 4;
                    }
                    c = (char)((arrby[n + 2] & 255) << 8 | arrby[n + 3] & 255);
                    n3 = n + 4;
                    bl = true;
                }
            }
        }
        if (bl) {
            object = new StringBuilder();
            while (n4 > 0) {
                n = n4;
                n2 = n3;
                if (arrby[n3] < 0) {
                    ((StringBuilder)object).append((char)((arrby[n3] & 127) + c));
                    n2 = n3 + 1;
                    n = n4 - 1;
                }
                for (n4 = 0; n4 < n && arrby[n2 + n4] >= 0; ++n4) {
                }
                ((StringBuilder)object).append(GsmAlphabet.gsm8BitUnpackedToString(arrby, n2, n4));
                n3 = n2 + n4;
                n4 = n - n4;
            }
            return ((StringBuilder)object).toString();
        }
        object2 = Resources.getSystem();
        object = "";
        try {
            object = object2 = ((Resources)object2).getString(17040079);
        }
        catch (Resources.NotFoundException notFoundException) {
            // empty catch block
        }
        return GsmAlphabet.gsm8BitUnpackedToString(arrby, n3, n2, ((String)object).trim());
    }

    public static String bcdPlmnToString(byte[] object, int n) {
        if (n + 3 > ((byte[])object).length) {
            return null;
        }
        String string2 = IccUtils.bytesToHexString(new byte[]{(byte)(object[n + 0] << 4 | object[n + 0] >> 4 & 15), (byte)(object[n + 1] << 4 | object[n + 2] & 15), (byte)(object[n + 2] & 240 | object[n + 1] >> 4 & 15)});
        object = string2;
        if (string2.contains("F")) {
            object = string2.replaceAll("F", "");
        }
        return object;
    }

    public static void bcdToBytes(String string2, byte[] arrby) {
        IccUtils.bcdToBytes(string2, arrby, 0);
    }

    public static void bcdToBytes(String string2, byte[] arrby, int n) {
        CharSequence charSequence = string2;
        if (string2.length() % 2 != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("0");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        int n2 = Math.min((arrby.length - n) * 2, ((String)charSequence).length());
        int n3 = 0;
        while (n3 + 1 < n2) {
            arrby[n] = (byte)(IccUtils.charToByte(((String)charSequence).charAt(n3 + 1)) << 4 | IccUtils.charToByte(((String)charSequence).charAt(n3)));
            n3 += 2;
            ++n;
        }
    }

    public static byte[] bcdToBytes(String string2) {
        byte[] arrby = new byte[(string2.length() + 1) / 2];
        IccUtils.bcdToBytes(string2, arrby);
        return arrby;
    }

    public static String bcdToString(byte[] arrby) {
        return IccUtils.bcdToString(arrby, 0, arrby.length);
    }

    public static String bcdToString(byte[] arrby, int n, int n2) {
        int n3;
        StringBuilder stringBuilder = new StringBuilder(n2 * 2);
        for (int i = n; i < n + n2 && (n3 = arrby[i] & 15) <= 9; ++i) {
            stringBuilder.append((char)(n3 + 48));
            n3 = arrby[i] >> 4 & 15;
            if (n3 == 15) continue;
            if (n3 > 9) break;
            stringBuilder.append((char)(n3 + 48));
        }
        return stringBuilder.toString();
    }

    public static String bchToString(byte[] arrby, int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(n2 * 2);
        for (int i = n; i < n + n2; ++i) {
            byte by = arrby[i];
            stringBuilder.append(HEX_CHARS[by & 15]);
            by = arrby[i];
            stringBuilder.append(HEX_CHARS[by >> 4 & 15]);
        }
        return stringBuilder.toString();
    }

    private static int bitToRGB(int n) {
        if (n == 1) {
            return -1;
        }
        return -16777216;
    }

    private static int byteNumForInt(int n, boolean bl) {
        if (n >= 0) {
            if (bl) {
                if (n <= 127) {
                    return 1;
                }
                if (n <= 32767) {
                    return 2;
                }
                if (n <= 8388607) {
                    return 3;
                }
            } else {
                if (n <= 255) {
                    return 1;
                }
                if (n <= 65535) {
                    return 2;
                }
                if (n <= 16777215) {
                    return 3;
                }
            }
            return 4;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("value must be 0 or positive: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static int byteNumForSignedInt(int n) {
        return IccUtils.byteNumForInt(n, true);
    }

    public static int byteNumForUnsignedInt(int n) {
        return IccUtils.byteNumForInt(n, false);
    }

    public static String byteToHex(byte by) {
        char[] arrc = HEX_CHARS;
        return new String(new char[]{arrc[(by & 255) >>> 4], arrc[by & 15]});
    }

    public static String bytesToHexString(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(arrby.length * 2);
        for (int i = 0; i < arrby.length; ++i) {
            byte by = arrby[i];
            stringBuilder.append(HEX_CHARS[by >> 4 & 15]);
            by = arrby[i];
            stringBuilder.append(HEX_CHARS[by & 15]);
        }
        return stringBuilder.toString();
    }

    public static int bytesToInt(byte[] object, int n, int n2) {
        if (n2 <= 4) {
            if (n >= 0 && n2 >= 0 && n + n2 <= ((Object)object).length) {
                int n3 = 0;
                for (int i = 0; i < n2; ++i) {
                    n3 = n3 << 8 | object[n + i] & 255;
                }
                if (n3 >= 0) {
                    return n3;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("src cannot be parsed as a positive integer: ");
                ((StringBuilder)object).append(n3);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Out of the bounds: src=[");
            stringBuilder.append(((Object)object).length);
            stringBuilder.append("], offset=");
            stringBuilder.append(n);
            stringBuilder.append(", length=");
            stringBuilder.append(n2);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("length must be <= 4 (only 32-bit integer supported): ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static long bytesToRawLong(byte[] object, int n, int n2) {
        if (n2 <= 8) {
            if (n >= 0 && n2 >= 0 && n + n2 <= ((Object)object).length) {
                long l = 0L;
                for (int i = 0; i < n2; ++i) {
                    l = l << 8 | (long)(object[n + i] & 255);
                }
                return l;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Out of the bounds: src=[");
            stringBuilder.append(((Object)object).length);
            stringBuilder.append("], offset=");
            stringBuilder.append(n);
            stringBuilder.append(", length=");
            stringBuilder.append(n2);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("length must be <= 8 (only 64-bit long supported): ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static int cdmaBcdByteToInt(byte by) {
        int n = 0;
        if ((by & 240) <= 144) {
            n = (by >> 4 & 15) * 10;
        }
        int n2 = n;
        if ((by & 15) <= 9) {
            n2 = n + (by & 15);
        }
        return n2;
    }

    public static String cdmaBcdToString(byte[] arrby, int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(n2);
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            int n5 = n4 = arrby[n] & 15;
            if (n4 > 9) {
                n5 = 0;
            }
            stringBuilder.append((char)(n5 + 48));
            n4 = n3 + 1;
            if (n4 == n2) break;
            n3 = n5 = arrby[n] >> 4 & 15;
            if (n5 > 9) {
                n3 = 0;
            }
            stringBuilder.append((char)(n3 + 48));
            n3 = n4 + 1;
            ++n;
        }
        return stringBuilder.toString();
    }

    private static byte charToByte(char c) {
        if (c >= '0' && c <= '9') {
            return (byte)(c - 48);
        }
        if (c >= 'A' && c <= 'F') {
            return (byte)(c - 55);
        }
        if (c >= 'a' && c <= 'f') {
            return (byte)(c - 87);
        }
        return 0;
    }

    public static byte countTrailingZeros(byte by) {
        if (by == 0) {
            return 8;
        }
        int n = by & 255;
        by = (byte)7;
        if ((n & 15) != 0) {
            by = (byte)(7 - 4);
        }
        byte by2 = by;
        if ((n & 51) != 0) {
            by2 = (byte)(by - 2);
        }
        byte by3 = by2;
        if ((n & 85) != 0) {
            by3 = by = (byte)(by2 - 1);
        }
        return by3;
    }

    private static int[] getCLUT(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            return null;
        }
        int[] arrn = new int[n2];
        int n3 = n;
        int n4 = 0;
        do {
            int n5 = n3 + 1;
            byte by = arrby[n3];
            int n6 = n5 + 1;
            n5 = arrby[n5];
            n3 = n6 + 1;
            arrn[n4] = (by & 255) << 16 | -16777216 | (n5 & 255) << 8 | arrby[n6] & 255;
            if (n3 >= n2 * 3 + n) {
                return arrn;
            }
            ++n4;
        } while (true);
    }

    public static String getDecimalSubstring(String string2) {
        int n;
        for (n = 0; n < string2.length() && Character.isDigit(string2.charAt(n)); ++n) {
        }
        return string2.substring(0, n);
    }

    public static int gsmBcdByteToInt(byte by) {
        int n = 0;
        if ((by & 240) <= 144) {
            n = by >> 4 & 15;
        }
        int n2 = n;
        if ((by & 15) <= 9) {
            n2 = n + (by & 15) * 10;
        }
        return n2;
    }

    public static int hexCharToInt(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 65 + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid hex char '");
        stringBuilder.append(c);
        stringBuilder.append("'");
        throw new RuntimeException(stringBuilder.toString());
    }

    public static byte[] hexStringToBytes(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = string2.length();
        byte[] arrby = new byte[n / 2];
        for (int i = 0; i < n; i += 2) {
            arrby[i / 2] = (byte)(IccUtils.hexCharToInt(string2.charAt(i)) << 4 | IccUtils.hexCharToInt(string2.charAt(i + 1)));
        }
        return arrby;
    }

    private static int intToBytes(int n, byte[] object, int n2, boolean bl) {
        int n3 = IccUtils.byteNumForInt(n, bl);
        if (n2 >= 0 && n2 + n3 <= ((byte[])object).length) {
            int n4 = n3 - 1;
            while (n4 >= 0) {
                object[n2 + n4] = (byte)(n & 255);
                --n4;
                n >>>= 8;
            }
            return n3;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Not enough space to write. Required bytes: ");
        ((StringBuilder)object).append(n3);
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    private static int[] mapTo2OrderBitColor(byte[] arrby, int n, int n2, int[] arrn, int n3) {
        if (8 % n3 != 0) {
            Rlog.e(LOG_TAG, "not event number of color");
            return IccUtils.mapToNon2OrderBitColor(arrby, n, n2, arrn, n3);
        }
        int n4 = 1;
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 4) {
                    if (n3 == 8) {
                        n4 = 255;
                    }
                } else {
                    n4 = 15;
                }
            } else {
                n4 = 3;
            }
        } else {
            n4 = 1;
        }
        int[] arrn2 = new int[n2];
        int n5 = 0;
        int n6 = 8 / n3;
        while (n5 < n2) {
            byte by = arrby[n];
            int n7 = 0;
            while (n7 < n6) {
                arrn2[n5] = arrn[by >> (n6 - n7 - 1) * n3 & n4];
                ++n7;
                ++n5;
            }
            ++n;
        }
        return arrn2;
    }

    private static int[] mapToNon2OrderBitColor(byte[] arrby, int n, int n2, int[] arrn, int n3) {
        if (8 % n3 == 0) {
            Rlog.e(LOG_TAG, "not odd number of color");
            return IccUtils.mapTo2OrderBitColor(arrby, n, n2, arrn, n3);
        }
        return new int[n2];
    }

    public static String networkNameToString(byte[] arrby, int n, int n2) {
        if ((arrby[n] & 128) == 128 && n2 >= 1) {
            String string2;
            int n3 = arrby[n] >>> 4 & 7;
            if (n3 != 0) {
                if (n3 != 1) {
                    string2 = "";
                } else {
                    try {
                        string2 = new String(arrby, n + 1, n2 - 1, "utf-16");
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        Rlog.e(LOG_TAG, "implausible UnsupportedEncodingException", unsupportedEncodingException);
                        string2 = "";
                    }
                }
            } else {
                string2 = GsmAlphabet.gsm7BitPackedToString(arrby, n + 1, ((n2 - 1) * 8 - (arrby[n] & 7)) / 7);
            }
            n = arrby[n];
            return string2;
        }
        return "";
    }

    public static Bitmap parseToBnW(byte[] arrby, int n) {
        n = 0 + 1;
        int n2 = arrby[0] & 255;
        int n3 = n + 1;
        int n4 = arrby[n] & 255;
        int n5 = n2 * n4;
        int[] arrn = new int[n5];
        int n6 = 0;
        n = 7;
        byte by = 0;
        while (n6 < n5) {
            int n7 = n3;
            if (n6 % 8 == 0) {
                by = arrby[n3];
                n = 7;
                n7 = n3 + 1;
            }
            arrn[n6] = IccUtils.bitToRGB(by >> n & 1);
            ++n6;
            --n;
            n3 = n7;
        }
        if (n6 != n5) {
            Rlog.e(LOG_TAG, "parse end and size error");
        }
        return Bitmap.createBitmap(arrn, n2, n4, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap parseToRGB(byte[] arrby, int n, boolean bl) {
        int n2 = 0 + 1;
        n = arrby[0] & 255;
        int n3 = n2 + 1;
        n2 = arrby[n2] & 255;
        int n4 = n3 + 1;
        n3 = arrby[n3] & 255;
        int n5 = n4 + 1;
        int n6 = arrby[n4] & 255;
        n4 = n5 + 1;
        byte by = arrby[n5];
        n5 = n4 + 1;
        int[] arrn = IccUtils.getCLUT(arrby, (by & 255) << 8 | arrby[n4] & 255, n6);
        if (bl) {
            arrn[n6 - 1] = 0;
        }
        arrby = 8 % n3 == 0 ? IccUtils.mapTo2OrderBitColor(arrby, n5, n * n2, arrn, n3) : IccUtils.mapToNon2OrderBitColor(arrby, n5, n * n2, arrn, n3);
        return Bitmap.createBitmap(arrby, n, n2, Bitmap.Config.RGB_565);
    }

    public static int signedIntToBytes(int n, byte[] arrby, int n2) {
        return IccUtils.intToBytes(n, arrby, n2, true);
    }

    public static byte[] signedIntToBytes(int n) {
        if (n >= 0) {
            byte[] arrby = new byte[IccUtils.byteNumForSignedInt(n)];
            IccUtils.signedIntToBytes(n, arrby, 0);
            return arrby;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("value must be 0 or positive: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static void stringToBcdPlmn(String string2, byte[] arrby, int n) {
        char c;
        if (string2.length() > 5) {
            char c2 = string2.charAt(5);
            c = c2;
        } else {
            char c3;
            c = c3 = 'F';
        }
        arrby[n] = (byte)(IccUtils.charToByte(string2.charAt(1)) << 4 | IccUtils.charToByte(string2.charAt(0)));
        arrby[n + 1] = (byte)(IccUtils.charToByte(c) << 4 | IccUtils.charToByte(string2.charAt(2)));
        arrby[n + 2] = (byte)(IccUtils.charToByte(string2.charAt(4)) << 4 | IccUtils.charToByte(string2.charAt(3)));
    }

    public static String stripTrailingFs(String string2) {
        string2 = string2 == null ? null : string2.replaceAll("(?i)f*$", "");
        return string2;
    }

    public static int unsignedIntToBytes(int n, byte[] arrby, int n2) {
        return IccUtils.intToBytes(n, arrby, n2, false);
    }

    public static byte[] unsignedIntToBytes(int n) {
        if (n >= 0) {
            byte[] arrby = new byte[IccUtils.byteNumForUnsignedInt(n)];
            IccUtils.unsignedIntToBytes(n, arrby, 0);
            return arrby;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("value must be 0 or positive: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}

