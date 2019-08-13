/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

import dalvik.annotation.optimization.FastNative;

public final class CharsetUtils {
    private CharsetUtils() {
    }

    @FastNative
    public static native void asciiBytesToChars(byte[] var0, int var1, int var2, char[] var3);

    @FastNative
    public static native void isoLatin1BytesToChars(byte[] var0, int var1, int var2, char[] var3);

    @FastNative
    public static native byte[] toAsciiBytes(String var0, int var1, int var2);

    public static byte[] toBigEndianUtf16Bytes(String string, int n, int n2) {
        byte[] arrby = new byte[n2 * 2];
        int n3 = 0;
        for (int i = n; i < n + n2; ++i) {
            char c = string.charAt(i);
            int n4 = n3 + 1;
            arrby[n3] = (byte)(c >> 8);
            n3 = n4 + 1;
            arrby[n4] = (byte)c;
        }
        return arrby;
    }

    @FastNative
    public static native byte[] toIsoLatin1Bytes(String var0, int var1, int var2);

    @FastNative
    public static native byte[] toUtf8Bytes(String var0, int var1, int var2);
}

