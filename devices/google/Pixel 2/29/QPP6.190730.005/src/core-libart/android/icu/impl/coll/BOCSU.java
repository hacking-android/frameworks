/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.util.ByteArrayWrapper;

public class BOCSU {
    private static final int SLOPE_LEAD_2_ = 42;
    private static final int SLOPE_LEAD_3_ = 3;
    private static final int SLOPE_MAX_ = 255;
    private static final int SLOPE_MAX_BYTES_ = 4;
    private static final int SLOPE_MIDDLE_ = 129;
    private static final int SLOPE_MIN_ = 3;
    private static final int SLOPE_REACH_NEG_1_ = -80;
    private static final int SLOPE_REACH_NEG_2_ = -10668;
    private static final int SLOPE_REACH_NEG_3_ = -192786;
    private static final int SLOPE_REACH_POS_1_ = 80;
    private static final int SLOPE_REACH_POS_2_ = 10667;
    private static final int SLOPE_REACH_POS_3_ = 192785;
    private static final int SLOPE_SINGLE_ = 80;
    private static final int SLOPE_START_NEG_2_ = 49;
    private static final int SLOPE_START_NEG_3_ = 7;
    private static final int SLOPE_START_POS_2_ = 210;
    private static final int SLOPE_START_POS_3_ = 252;
    private static final int SLOPE_TAIL_COUNT_ = 253;

    private BOCSU() {
    }

    private static void ensureAppendCapacity(ByteArrayWrapper byteArrayWrapper, int n, int n2) {
        if (byteArrayWrapper.bytes.length - byteArrayWrapper.size >= n) {
            return;
        }
        int n3 = n2;
        if (n2 < n) {
            n3 = n;
        }
        byteArrayWrapper.ensureCapacity(byteArrayWrapper.size + n3);
    }

    private static final long getNegDivMod(int n, int n2) {
        int n3 = n % n2;
        long l = n / n2;
        n = n3;
        long l2 = l;
        if (n3 < 0) {
            l2 = l - 1L;
            n = n3 + n2;
        }
        return l2 << 32 | (long)n;
    }

    private static final int writeDiff(int n, byte[] arrby, int n2) {
        if (n >= -80) {
            if (n <= 80) {
                arrby[n2] = (byte)(n + 129);
                n = n2 + 1;
            } else if (n <= 10667) {
                int n3 = n2 + 1;
                arrby[n2] = (byte)(n / 253 + 210);
                n2 = n3 + 1;
                arrby[n3] = (byte)(n % 253 + 3);
                n = n2;
            } else if (n <= 192785) {
                arrby[n2 + 2] = (byte)(n % 253 + 3);
                arrby[n2 + 1] = (byte)((n /= 253) % 253 + 3);
                arrby[n2] = (byte)(n / 253 + 252);
                n = n2 + 3;
            } else {
                arrby[n2 + 3] = (byte)(n % 253 + 3);
                arrby[n2 + 2] = (byte)((n /= 253) % 253 + 3);
                arrby[n2 + 1] = (byte)(n / 253 % 253 + 3);
                arrby[n2] = (byte)-1;
                n = n2 + 4;
            }
        } else {
            long l = BOCSU.getNegDivMod(n, 253);
            int n4 = (int)l;
            if (n >= -10668) {
                n = (int)(l >> 32);
                int n5 = n2 + 1;
                arrby[n2] = (byte)(n + 49);
                n = n5 + 1;
                arrby[n5] = (byte)(n4 + 3);
            } else if (n >= -192786) {
                arrby[n2 + 2] = (byte)(n4 + 3);
                l = BOCSU.getNegDivMod((int)(l >> 32), 253);
                n4 = (int)l;
                n = (int)(l >> 32);
                arrby[n2 + 1] = (byte)(n4 + 3);
                arrby[n2] = (byte)(n + 7);
                n = n2 + 3;
            } else {
                arrby[n2 + 3] = (byte)(n4 + 3);
                l = BOCSU.getNegDivMod((int)(l >> 32), 253);
                n = (int)l;
                n4 = (int)(l >> 32);
                arrby[n2 + 2] = (byte)(n + 3);
                arrby[n2 + 1] = (byte)((int)BOCSU.getNegDivMod(n4, 253) + 3);
                arrby[n2] = (byte)3;
                n = n2 + 4;
            }
        }
        return n;
    }

    public static int writeIdenticalLevelRun(int n, CharSequence charSequence, int n2, int n3, ByteArrayWrapper byteArrayWrapper) {
        int n4 = n;
        while (n2 < n3) {
            BOCSU.ensureAppendCapacity(byteArrayWrapper, 16, charSequence.length() * 2);
            byte[] arrby = byteArrayWrapper.bytes;
            int n5 = arrby.length;
            int n6 = byteArrayWrapper.size;
            n = n2;
            n2 = n6;
            while (n < n3 && n2 <= n5 - 4) {
                n4 = n4 >= 19968 && n4 < 40960 ? 30292 : (n4 & -128) + 80;
                int n7 = Character.codePointAt(charSequence, n);
                n6 = n + Character.charCount(n7);
                if (n7 == 65534) {
                    arrby[n2] = (byte)2;
                    n = 0;
                    ++n2;
                } else {
                    n2 = BOCSU.writeDiff(n7 - n4, arrby, n2);
                    n = n7;
                }
                n4 = n;
                n = n6;
            }
            byteArrayWrapper.size = n2;
            n2 = n;
        }
        return n4;
    }
}

