/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import dalvik.annotation.optimization.FastNative;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import libcore.util.CharsetUtils;
import libcore.util.EmptyArray;

public final class StringFactory {
    private static final char REPLACEMENT_CHAR = '\ufffd';
    private static final int[] TABLE_UTF8_NEEDED = new int[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static String newEmptyString() {
        return StringFactory.newStringFromChars(EmptyArray.CHAR, 0, 0);
    }

    public static String newStringFromBytes(byte[] arrby) {
        return StringFactory.newStringFromBytes(arrby, 0, arrby.length);
    }

    public static String newStringFromBytes(byte[] arrby, int n) {
        return StringFactory.newStringFromBytes(arrby, n, 0, arrby.length);
    }

    public static String newStringFromBytes(byte[] arrby, int n, int n2) {
        return StringFactory.newStringFromBytes(arrby, n, n2, Charset.defaultCharset());
    }

    @FastNative
    public static native String newStringFromBytes(byte[] var0, int var1, int var2, int var3);

    public static String newStringFromBytes(byte[] arrby, int n, int n2, String string) throws UnsupportedEncodingException {
        return StringFactory.newStringFromBytes(arrby, n, n2, Charset.forNameUEE((String)string));
    }

    public static String newStringFromBytes(byte[] object, int n, int n2, Charset arrc) {
        block25 : {
            block30 : {
                String string;
                block26 : {
                    if ((n | n2) < 0 || n2 > ((byte[])object).length - n) break block25;
                    string = arrc.name();
                    if (!string.equals("UTF-8")) break block26;
                    arrc = new char[n2];
                    int n3 = n;
                    int n4 = 0;
                    int n5 = 0;
                    int n6 = 0;
                    int n7 = 0;
                    int n8 = 128;
                    int n9 = 191;
                    while (n3 < n + n2) {
                        int n10;
                        block29 : {
                            block28 : {
                                int n11;
                                block27 : {
                                    n10 = n3 + 1;
                                    n11 = object[n3] & 255;
                                    if (n7 != 0) break block27;
                                    if ((n11 & 128) == 0) {
                                        arrc[n4] = (char)n11;
                                        ++n4;
                                        n3 = n10;
                                        continue;
                                    }
                                    if ((n11 & 64) == 0) {
                                        arrc[n4] = (char)65533;
                                        ++n4;
                                        n3 = n10;
                                        continue;
                                    }
                                    n7 = TABLE_UTF8_NEEDED[n11 & 63];
                                    if (n7 == 0) {
                                        arrc[n4] = (char)65533;
                                        n3 = n10;
                                        ++n4;
                                        continue;
                                    }
                                    n5 = n11 & 63 >> n7;
                                    if (n11 == 224) {
                                        n3 = 160;
                                    } else if (n11 == 237) {
                                        n9 = 159;
                                        n3 = n8;
                                    } else if (n11 == 240) {
                                        n3 = 144;
                                    } else {
                                        n3 = n8;
                                        if (n11 == 244) {
                                            n9 = 143;
                                            n3 = n8;
                                        }
                                    }
                                    n8 = n3;
                                    break block28;
                                }
                                if (n11 < n8 || n11 > n9) break block29;
                                n8 = 128;
                                n9 = 191;
                                n5 = n5 << 6 | n11 & 63;
                                if (n7 != ++n6) {
                                    n3 = n10;
                                    continue;
                                }
                                if (n5 < 65536) {
                                    n3 = n4 + 1;
                                    arrc[n4] = (char)n5;
                                    n4 = n3;
                                } else {
                                    n3 = n4 + 1;
                                    arrc[n4] = (char)((n5 >> 10) + 55232);
                                    arrc[n3] = (char)((n5 & 1023) + 56320);
                                    n4 = n3 + 1;
                                }
                                n5 = 0;
                                n7 = 0;
                                n6 = 0;
                            }
                            n3 = n10;
                            continue;
                        }
                        arrc[n4] = (char)65533;
                        n5 = 0;
                        n7 = 0;
                        n6 = 0;
                        n8 = 128;
                        n9 = 191;
                        n3 = n10 - 1;
                        ++n4;
                    }
                    if (n7 != 0) {
                        n = n4 + 1;
                        arrc[n4] = (char)65533;
                    } else {
                        n = n4;
                    }
                    if (n == n2) {
                        object = arrc;
                    } else {
                        object = new char[n];
                        System.arraycopy(arrc, 0, object, 0, n);
                    }
                    break block30;
                }
                if (string.equals("ISO-8859-1")) {
                    arrc = new char[n2];
                    int n12 = n2;
                    CharsetUtils.isoLatin1BytesToChars((byte[])object, n, n2, arrc);
                    object = arrc;
                    n = n12;
                } else if (string.equals("US-ASCII")) {
                    arrc = new char[n2];
                    int n13 = n2;
                    CharsetUtils.asciiBytesToChars((byte[])object, n, n2, arrc);
                    object = arrc;
                    n = n13;
                } else {
                    object = arrc.decode(ByteBuffer.wrap((byte[])object, n, n2));
                    n = ((CharBuffer)object).length();
                    object = ((CharBuffer)object).array();
                }
            }
            return StringFactory.newStringFromChars((char[])object, 0, n);
        }
        throw new StringIndexOutOfBoundsException(((byte[])object).length, n, n2);
    }

    public static String newStringFromBytes(byte[] arrby, String string) throws UnsupportedEncodingException {
        return StringFactory.newStringFromBytes(arrby, 0, arrby.length, Charset.forNameUEE((String)string));
    }

    public static String newStringFromBytes(byte[] arrby, Charset charset) {
        return StringFactory.newStringFromBytes(arrby, 0, arrby.length, charset);
    }

    @FastNative
    static native String newStringFromChars(int var0, int var1, char[] var2);

    public static String newStringFromChars(char[] arrc) {
        return StringFactory.newStringFromChars(arrc, 0, arrc.length);
    }

    public static String newStringFromChars(char[] arrc, int n, int n2) {
        if ((n | n2) >= 0 && n2 <= arrc.length - n) {
            return StringFactory.newStringFromChars(n, n2, arrc);
        }
        throw new StringIndexOutOfBoundsException(arrc.length, n, n2);
    }

    public static String newStringFromCodePoints(int[] arrn, int n, int n2) {
        if (arrn != null) {
            if ((n | n2) >= 0 && n2 <= arrn.length - n) {
                char[] arrc = new char[n2 * 2];
                int n3 = 0;
                for (int i = n; i < n + n2; ++i) {
                    n3 += Character.toChars(arrn[i], arrc, n3);
                }
                return StringFactory.newStringFromChars(arrc, 0, n3);
            }
            throw new StringIndexOutOfBoundsException(arrn.length, n, n2);
        }
        throw new NullPointerException("codePoints == null");
    }

    @FastNative
    public static native String newStringFromString(String var0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String newStringFromStringBuffer(StringBuffer stringBuffer) {
        synchronized (stringBuffer) {
            return StringFactory.newStringFromChars(stringBuffer.getValue(), 0, stringBuffer.length());
        }
    }

    public static String newStringFromStringBuilder(StringBuilder stringBuilder) {
        return StringFactory.newStringFromChars(stringBuilder.getValue(), 0, stringBuilder.length());
    }
}

