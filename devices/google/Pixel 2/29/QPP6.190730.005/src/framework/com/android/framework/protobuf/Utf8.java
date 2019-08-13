/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class Utf8 {
    private static final long ASCII_MASK_LONG = -9187201950435737472L;
    public static final int COMPLETE = 0;
    public static final int MALFORMED = -1;
    static final int MAX_BYTES_PER_CHAR = 3;
    private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
    private static final Logger logger = Logger.getLogger(Utf8.class.getName());
    private static final Processor processor;

    static {
        Processor processor = UnsafeProcessor.isAvailable() ? new UnsafeProcessor() : new SafeProcessor();
        Utf8.processor = processor;
    }

    private Utf8() {
    }

    static int encode(CharSequence charSequence, byte[] arrby, int n, int n2) {
        return processor.encodeUtf8(charSequence, arrby, n, n2);
    }

    static void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer) {
        processor.encodeUtf8(charSequence, byteBuffer);
    }

    static int encodedLength(CharSequence charSequence) {
        int n;
        int n2;
        block3 : {
            int n3;
            int n4;
            int n5 = n2 = charSequence.length();
            n = 0;
            do {
                n4 = n5;
                n3 = n;
                if (n >= n2) break;
                n4 = n5;
                n3 = n;
                if (charSequence.charAt(n) >= '') break;
                ++n;
            } while (true);
            do {
                n = n4;
                if (n3 >= n2) break block3;
                n = charSequence.charAt(n3);
                if (n >= 2048) break;
                n4 += 127 - n >>> 31;
                ++n3;
            } while (true);
            n = n4 + Utf8.encodedLengthGeneral(charSequence, n3);
        }
        if (n >= n2) {
            return n;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("UTF-8 length does not fit in int: ");
        ((StringBuilder)charSequence).append((long)n + 0x100000000L);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static int encodedLengthGeneral(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        int n3 = 0;
        while (n < n2) {
            int n4;
            char c = charSequence.charAt(n);
            if (c < '\u0800') {
                n3 += 127 - c >>> 31;
                n4 = n;
            } else {
                int n5;
                n3 = n5 = n3 + 2;
                n4 = n;
                if ('\ud800' <= c) {
                    n3 = n5;
                    n4 = n;
                    if (c <= '\udfff') {
                        if (Character.codePointAt(charSequence, n) >= 65536) {
                            n4 = n + 1;
                            n3 = n5;
                        } else {
                            throw new UnpairedSurrogateException(n, n2);
                        }
                    }
                }
            }
            n = n4 + 1;
        }
        return n3;
    }

    private static int estimateConsecutiveAscii(ByteBuffer byteBuffer, int n, int n2) {
        int n3;
        for (n3 = n; n3 < n2 - 7 && (byteBuffer.getLong(n3) & -9187201950435737472L) == 0L; n3 += 8) {
        }
        return n3 - n;
    }

    private static int incompleteStateFor(int n) {
        block0 : {
            if (n <= -12) break block0;
            n = -1;
        }
        return n;
    }

    private static int incompleteStateFor(int n, int n2) {
        n = n <= -12 && n2 <= -65 ? n2 << 8 ^ n : -1;
        return n;
    }

    private static int incompleteStateFor(int n, int n2, int n3) {
        n = n <= -12 && n2 <= -65 && n3 <= -65 ? n2 << 8 ^ n ^ n3 << 16 : -1;
        return n;
    }

    private static int incompleteStateFor(ByteBuffer byteBuffer, int n, int n2, int n3) {
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 == 2) {
                    return Utf8.incompleteStateFor(n, (int)byteBuffer.get(n2), (int)byteBuffer.get(n2 + 1));
                }
                throw new AssertionError();
            }
            return Utf8.incompleteStateFor(n, byteBuffer.get(n2));
        }
        return Utf8.incompleteStateFor(n);
    }

    private static int incompleteStateFor(byte[] arrby, int n, int n2) {
        byte by = arrby[n - 1];
        if ((n2 -= n) != 0) {
            if (n2 != 1) {
                if (n2 == 2) {
                    return Utf8.incompleteStateFor(by, (int)arrby[n], (int)arrby[n + 1]);
                }
                throw new AssertionError();
            }
            return Utf8.incompleteStateFor(by, arrby[n]);
        }
        return Utf8.incompleteStateFor(by);
    }

    static boolean isValidUtf8(ByteBuffer byteBuffer) {
        return processor.isValidUtf8(byteBuffer, byteBuffer.position(), byteBuffer.remaining());
    }

    public static boolean isValidUtf8(byte[] arrby) {
        return processor.isValidUtf8(arrby, 0, arrby.length);
    }

    public static boolean isValidUtf8(byte[] arrby, int n, int n2) {
        return processor.isValidUtf8(arrby, n, n2);
    }

    static int partialIsValidUtf8(int n, ByteBuffer byteBuffer, int n2, int n3) {
        return processor.partialIsValidUtf8(n, byteBuffer, n2, n3);
    }

    public static int partialIsValidUtf8(int n, byte[] arrby, int n2, int n3) {
        return processor.partialIsValidUtf8(n, arrby, n2, n3);
    }

    static abstract class Processor {
        Processor() {
        }

        private static int partialIsValidUtf8(ByteBuffer byteBuffer, int n, int n2) {
            n += Utf8.estimateConsecutiveAscii(byteBuffer, n, n2);
            while (n < n2) {
                int n3 = n + 1;
                if ((n = (int)byteBuffer.get(n)) < 0) {
                    int n4;
                    if (n < -32) {
                        if (n3 >= n2) {
                            return n;
                        }
                        if (n >= -62 && byteBuffer.get(n3) <= -65) {
                            n = n3 + 1;
                            continue;
                        }
                        return -1;
                    }
                    if (n < -16) {
                        if (n3 >= n2 - 1) {
                            return Utf8.incompleteStateFor(byteBuffer, n, n3, n2 - n3);
                        }
                        n4 = n3 + 1;
                        if (!((n3 = (int)byteBuffer.get(n3)) > -65 || n == -32 && n3 < -96 || n == -19 && n3 >= -96 || byteBuffer.get(n4) > -65)) {
                            n = n4 + 1;
                            continue;
                        }
                        return -1;
                    }
                    if (n3 >= n2 - 2) {
                        return Utf8.incompleteStateFor(byteBuffer, n, n3, n2 - n3);
                    }
                    n4 = n3 + 1;
                    if ((n3 = (int)byteBuffer.get(n3)) <= -65 && (n << 28) + (n3 + 112) >> 30 == 0) {
                        n3 = n4 + 1;
                        if (byteBuffer.get(n4) <= -65) {
                            n = n3 + 1;
                            if (byteBuffer.get(n3) <= -65) continue;
                        }
                    }
                    return -1;
                }
                n = n3;
            }
            return 0;
        }

        abstract int encodeUtf8(CharSequence var1, byte[] var2, int var3, int var4);

        final void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer) {
            if (byteBuffer.hasArray()) {
                int n = byteBuffer.arrayOffset();
                byteBuffer.position(Utf8.encode(charSequence, byteBuffer.array(), byteBuffer.position() + n, byteBuffer.remaining()) - n);
            } else if (byteBuffer.isDirect()) {
                this.encodeUtf8Direct(charSequence, byteBuffer);
            } else {
                this.encodeUtf8Default(charSequence, byteBuffer);
            }
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        final void encodeUtf8Default(CharSequence var1_1, ByteBuffer var2_2) {
            block21 : {
                var3_3 = var1_1.length();
                var4_4 = var2_2.position();
                for (var5_5 = 0; var5_5 < var3_3; ++var5_5) {
                    var6_6 = var4_4;
                    var7_7 = var5_5;
                    var8_8 = var1_1.charAt(var5_5);
                    if (var8_8 >= 128) break;
                    var6_6 = var4_4;
                    var7_7 = var5_5;
                    var2_2.put(var4_4 + var5_5, (byte)var8_8);
                    continue;
                }
                if (var5_5 != var3_3) ** GOTO lbl21
                var6_6 = var4_4;
                var7_7 = var5_5;
                try {
                    var2_2.position(var4_4 + var5_5);
                    return;
lbl21: // 1 sources:
                    var4_4 += var5_5;
                }
                catch (IndexOutOfBoundsException var9_9) {
                    var5_5 = var7_7;
                    break block21;
                }
                while (var5_5 < var3_3) {
                    block22 : {
                        var6_6 = var4_4;
                        var7_7 = var5_5;
                        var10_13 = var1_1.charAt(var5_5);
                        if (var10_13 < '') {
                            var6_6 = var4_4;
                            var7_7 = var5_5;
                            var2_2.put(var4_4, (byte)var10_13);
                        } else {
                            if (var10_13 < '\u0800') {
                                var6_6 = var4_4 + 1;
                                var11_14 = (byte)(var10_13 >>> 6 | 192);
                                var7_7 = var6_6;
                                var2_2.put(var4_4, var11_14);
                                var7_7 = var6_6;
                                var2_2.put(var6_6, (byte)(var10_13 & 63 | 128));
                                var4_4 = var6_6;
                                break block22;
                            }
                            if (var10_13 >= '\ud800' && '\udfff' >= var10_13) {
                                block23 : {
                                    var8_8 = var5_5;
                                    if (var5_5 + 1 != var3_3) {
                                        var6_6 = var4_4;
                                        var7_7 = ++var5_5;
                                        {
                                            var12_15 = var1_1.charAt(var5_5);
                                            var6_6 = var4_4;
                                            var7_7 = var5_5;
                                            var8_8 = var5_5;
                                            if (!Character.isSurrogatePair(var10_13, var12_15)) break block23;
                                            var6_6 = var4_4;
                                            var7_7 = var5_5;
                                            var13_16 = Character.toCodePoint(var10_13, var12_15);
                                            var8_8 = var4_4 + 1;
                                        }
                                        var11_14 = (byte)(var13_16 >>> 18 | 240);
                                        var7_7 = var8_8;
                                        try {
                                            var2_2.put(var4_4, var11_14);
                                            var4_4 = var8_8 + 1;
                                        }
                                        catch (IndexOutOfBoundsException var9_11) {
                                            var6_6 = var7_7;
                                            break block21;
                                        }
                                        var11_14 = (byte)(var13_16 >>> 12 & 63 | 128);
                                        var6_6 = var4_4;
                                        var7_7 = var5_5;
                                        {
                                            var2_2.put(var8_8, var11_14);
                                            var6_6 = var4_4 + 1;
                                        }
                                        var11_14 = (byte)(var13_16 >>> 6 & 63 | 128);
                                        var7_7 = var6_6;
                                        var2_2.put(var4_4, var11_14);
                                        var7_7 = var6_6;
                                        var2_2.put(var6_6, (byte)(var13_16 & 63 | 128));
                                        var4_4 = var6_6;
                                        break block22;
                                    }
                                }
                                var6_6 = var4_4;
                                var7_7 = var8_8;
                                {
                                    var6_6 = var4_4;
                                    var7_7 = var8_8;
                                    var9_12 = new UnpairedSurrogateException(var8_8, var3_3);
                                    var6_6 = var4_4;
                                    var7_7 = var8_8;
                                    throw var9_12;
                                }
                            }
                            var8_8 = var4_4 + 1;
                            var11_14 = (byte)(var10_13 >>> 12 | 224);
                            var7_7 = var8_8;
                            try {
                                var2_2.put(var4_4, var11_14);
                                var4_4 = var8_8 + 1;
                            }
                            catch (IndexOutOfBoundsException var9_10) {
                                var6_6 = var7_7;
                                break block21;
                            }
                            var11_14 = (byte)(var10_13 >>> 6 & 63 | 128);
                            var6_6 = var4_4;
                            var7_7 = var5_5;
                            {
                                var2_2.put(var8_8, var11_14);
                                var6_6 = var4_4;
                                var7_7 = var5_5;
                                var2_2.put(var4_4, (byte)(var10_13 & 63 | 128));
                            }
                        }
                    }
                    ++var5_5;
                    ++var4_4;
                }
                var6_6 = var4_4;
                var7_7 = var5_5;
                {
                    var2_2.position(var4_4);
                    return;
                }
            }
            var7_7 = var2_2.position();
            var6_6 = Math.max(var5_5, var6_6 - var2_2.position() + 1);
            var2_2 = new StringBuilder();
            var2_2.append("Failed writing ");
            var2_2.append(var1_1.charAt(var5_5));
            var2_2.append(" at index ");
            var2_2.append(var7_7 + var6_6);
            throw new ArrayIndexOutOfBoundsException(var2_2.toString());
        }

        abstract void encodeUtf8Direct(CharSequence var1, ByteBuffer var2);

        final boolean isValidUtf8(ByteBuffer byteBuffer, int n, int n2) {
            boolean bl = false;
            if (this.partialIsValidUtf8(0, byteBuffer, n, n2) == 0) {
                bl = true;
            }
            return bl;
        }

        final boolean isValidUtf8(byte[] arrby, int n, int n2) {
            boolean bl = false;
            if (this.partialIsValidUtf8(0, arrby, n, n2) == 0) {
                bl = true;
            }
            return bl;
        }

        final int partialIsValidUtf8(int n, ByteBuffer byteBuffer, int n2, int n3) {
            if (byteBuffer.hasArray()) {
                int n4 = byteBuffer.arrayOffset();
                return this.partialIsValidUtf8(n, byteBuffer.array(), n4 + n2, n4 + n3);
            }
            if (byteBuffer.isDirect()) {
                return this.partialIsValidUtf8Direct(n, byteBuffer, n2, n3);
            }
            return this.partialIsValidUtf8Default(n, byteBuffer, n2, n3);
        }

        abstract int partialIsValidUtf8(int var1, byte[] var2, int var3, int var4);

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        final int partialIsValidUtf8Default(int n, ByteBuffer byteBuffer, int n2, int n3) {
            if (n != 0) {
                if (n2 >= n3) {
                    return n;
                }
                byte by = (byte)n;
                if (by < -32) {
                    if (by < -62) return -1;
                    n = n2 + 1;
                    if (byteBuffer.get(n2) <= -65) return Processor.partialIsValidUtf8(byteBuffer, n, n3);
                    return -1;
                }
                if (by < -16) {
                    byte by2;
                    byte by3 = by2 = (byte)(n >> 8);
                    n = n2;
                    if (by2 == 0) {
                        n = n2 + 1;
                        by3 = byteBuffer.get(n2);
                        if (n >= n3) {
                            return Utf8.incompleteStateFor(by, by3);
                        }
                    }
                    if (by3 > -65 || by == -32 && by3 < -96 || by == -19 && by3 >= -96) return -1;
                    n2 = n + 1;
                    if (byteBuffer.get(n) > -65) return -1;
                    n = n2;
                    return Processor.partialIsValidUtf8(byteBuffer, n, n3);
                } else {
                    byte by4 = (byte)(n >> 8);
                    int n4 = 0;
                    if (by4 == 0) {
                        n = n2 + 1;
                        by4 = byteBuffer.get(n2);
                        if (n >= n3) {
                            return Utf8.incompleteStateFor(by, by4);
                        }
                        n2 = n;
                        n = n4;
                    } else {
                        n = (byte)(n >> 16);
                    }
                    int n5 = n;
                    n4 = n2;
                    if (n == 0) {
                        n4 = n2 + 1;
                        n5 = byteBuffer.get(n2);
                        if (n4 >= n3) {
                            return Utf8.incompleteStateFor(by, by4, n5);
                        }
                    }
                    if (by4 > -65 || (by << 28) + (by4 + 112) >> 30 != 0 || n5 > -65 || byteBuffer.get(n4) > -65) return -1;
                    n = n4 + 1;
                }
                return Processor.partialIsValidUtf8(byteBuffer, n, n3);
            } else {
                n = n2;
            }
            return Processor.partialIsValidUtf8(byteBuffer, n, n3);
        }

        abstract int partialIsValidUtf8Direct(int var1, ByteBuffer var2, int var3, int var4);
    }

    static final class SafeProcessor
    extends Processor {
        SafeProcessor() {
        }

        private static int partialIsValidUtf8(byte[] arrby, int n, int n2) {
            while (n < n2 && arrby[n] >= 0) {
                ++n;
            }
            n = n >= n2 ? 0 : SafeProcessor.partialIsValidUtf8NonAscii(arrby, n, n2);
            return n;
        }

        private static int partialIsValidUtf8NonAscii(byte[] arrby, int n, int n2) {
            while (n < n2) {
                int n3 = n + 1;
                if ((n = arrby[n]) < 0) {
                    int n4;
                    if (n < -32) {
                        if (n3 >= n2) {
                            return n;
                        }
                        if (n >= -62) {
                            n = n3 + 1;
                            if (arrby[n3] <= -65) continue;
                        }
                        return -1;
                    }
                    if (n < -16) {
                        if (n3 >= n2 - 1) {
                            return Utf8.incompleteStateFor(arrby, n3, n2);
                        }
                        n4 = n3 + 1;
                        if (!((n3 = arrby[n3]) > -65 || n == -32 && n3 < -96 || n == -19 && n3 >= -96)) {
                            n = n4 + 1;
                            if (arrby[n4] <= -65) continue;
                        }
                        return -1;
                    }
                    if (n3 >= n2 - 2) {
                        return Utf8.incompleteStateFor(arrby, n3, n2);
                    }
                    n4 = n3 + 1;
                    if ((n3 = arrby[n3]) <= -65 && (n << 28) + (n3 + 112) >> 30 == 0) {
                        n3 = n4 + 1;
                        if (arrby[n4] <= -65) {
                            n = n3 + 1;
                            if (arrby[n3] <= -65) continue;
                        }
                    }
                    return -1;
                }
                n = n3;
            }
            return 0;
        }

        @Override
        int encodeUtf8(CharSequence charSequence, byte[] arrby, int n, int n2) {
            int n3 = charSequence.length();
            int n4 = 0;
            int n5 = n + n2;
            n2 = n4;
            while (n2 < n3 && n2 + n < n5 && (n4 = (int)charSequence.charAt(n2)) < 128) {
                arrby[n + n2] = (byte)n4;
                ++n2;
            }
            if (n2 == n3) {
                return n + n3;
            }
            n += n2;
            while (n2 < n3) {
                char c;
                block11 : {
                    block12 : {
                        block8 : {
                            block10 : {
                                block9 : {
                                    block7 : {
                                        c = charSequence.charAt(n2);
                                        if (c >= '' || n >= n5) break block7;
                                        arrby[n] = (byte)c;
                                        ++n;
                                        break block8;
                                    }
                                    if (c >= '\u0800' || n > n5 - 2) break block9;
                                    n4 = n + 1;
                                    arrby[n] = (byte)(c >>> 6 | 960);
                                    n = n4 + 1;
                                    arrby[n4] = (byte)(c & 63 | 128);
                                    break block8;
                                }
                                if (c >= '\ud800' && '\udfff' >= c || n > n5 - 3) break block10;
                                n4 = n + 1;
                                arrby[n] = (byte)(c >>> 12 | 480);
                                n = n4 + 1;
                                arrby[n4] = (byte)(c >>> 6 & 63 | 128);
                                arrby[n] = (byte)(c & 63 | 128);
                                ++n;
                                break block8;
                            }
                            if (n > n5 - 4) break block11;
                            n4 = n2;
                            if (n2 + 1 == charSequence.length()) break block12;
                            char c2 = charSequence.charAt(++n2);
                            n4 = n2;
                            if (!Character.isSurrogatePair(c, c2)) break block12;
                            n4 = Character.toCodePoint(c, c2);
                            int n6 = n + 1;
                            arrby[n] = (byte)(n4 >>> 18 | 240);
                            n = n6 + 1;
                            arrby[n6] = (byte)(n4 >>> 12 & 63 | 128);
                            n6 = n + 1;
                            arrby[n] = (byte)(n4 >>> 6 & 63 | 128);
                            n = n6 + 1;
                            arrby[n6] = (byte)(n4 & 63 | 128);
                        }
                        ++n2;
                        continue;
                    }
                    throw new UnpairedSurrogateException(n4 - 1, n3);
                }
                if (!('\ud800' > c || c > '\udfff' || n2 + 1 != charSequence.length() && Character.isSurrogatePair(c, charSequence.charAt(n2 + 1)))) {
                    throw new UnpairedSurrogateException(n2, n3);
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Failed writing ");
                ((StringBuilder)charSequence).append(c);
                ((StringBuilder)charSequence).append(" at index ");
                ((StringBuilder)charSequence).append(n);
                throw new ArrayIndexOutOfBoundsException(((StringBuilder)charSequence).toString());
            }
            return n;
        }

        @Override
        void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer) {
            this.encodeUtf8Default(charSequence, byteBuffer);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        int partialIsValidUtf8(int n, byte[] arrby, int n2, int n3) {
            if (n != 0) {
                if (n2 >= n3) {
                    return n;
                }
                byte by = (byte)n;
                if (by < -32) {
                    if (by < -62) return -1;
                    n = n2 + 1;
                    if (arrby[n2] <= -65) return SafeProcessor.partialIsValidUtf8(arrby, n, n3);
                    return -1;
                }
                if (by < -16) {
                    byte by2;
                    byte by3 = by2 = (byte)(n >> 8);
                    n = n2;
                    if (by2 == 0) {
                        n = n2 + 1;
                        by3 = arrby[n2];
                        if (n >= n3) {
                            return Utf8.incompleteStateFor(by, by3);
                        }
                    }
                    if (by3 > -65 || by == -32 && by3 < -96 || by == -19 && by3 >= -96) return -1;
                    n2 = n + 1;
                    if (arrby[n] > -65) return -1;
                    n = n2;
                    return SafeProcessor.partialIsValidUtf8(arrby, n, n3);
                } else {
                    byte by4 = (byte)(n >> 8);
                    int n4 = 0;
                    if (by4 == 0) {
                        n = n2 + 1;
                        by4 = arrby[n2];
                        if (n >= n3) {
                            return Utf8.incompleteStateFor(by, by4);
                        }
                        n2 = n;
                        n = n4;
                    } else {
                        n = (byte)(n >> 16);
                    }
                    int n5 = n;
                    n4 = n2;
                    if (n == 0) {
                        n4 = n2 + 1;
                        n5 = arrby[n2];
                        if (n4 >= n3) {
                            return Utf8.incompleteStateFor(by, by4, n5);
                        }
                    }
                    if (by4 > -65 || (by << 28) + (by4 + 112) >> 30 != 0 || n5 > -65 || arrby[n4] > -65) return -1;
                    n = n4 + 1;
                }
                return SafeProcessor.partialIsValidUtf8(arrby, n, n3);
            } else {
                n = n2;
            }
            return SafeProcessor.partialIsValidUtf8(arrby, n, n3);
        }

        @Override
        int partialIsValidUtf8Direct(int n, ByteBuffer byteBuffer, int n2, int n3) {
            return this.partialIsValidUtf8Default(n, byteBuffer, n2, n3);
        }
    }

    static class UnpairedSurrogateException
    extends IllegalArgumentException {
        private UnpairedSurrogateException(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unpaired surrogate at index ");
            stringBuilder.append(n);
            stringBuilder.append(" of ");
            stringBuilder.append(n2);
            super(stringBuilder.toString());
        }
    }

    static final class UnsafeProcessor
    extends Processor {
        private static final int ARRAY_BASE_OFFSET;
        private static final boolean AVAILABLE;
        private static final long BUFFER_ADDRESS_OFFSET;
        private static final Unsafe UNSAFE;

        static {
            UNSAFE = UnsafeProcessor.getUnsafe();
            BUFFER_ADDRESS_OFFSET = UnsafeProcessor.fieldOffset(UnsafeProcessor.field(Buffer.class, "address"));
            ARRAY_BASE_OFFSET = UnsafeProcessor.byteArrayBaseOffset();
            boolean bl = BUFFER_ADDRESS_OFFSET != -1L && ARRAY_BASE_OFFSET % 8 == 0;
            AVAILABLE = bl;
        }

        UnsafeProcessor() {
        }

        private static long addressOffset(ByteBuffer byteBuffer) {
            return UNSAFE.getLong((Object)byteBuffer, BUFFER_ADDRESS_OFFSET);
        }

        private static <T> int byteArrayBaseOffset() {
            Unsafe unsafe = UNSAFE;
            int n = unsafe == null ? -1 : unsafe.arrayBaseOffset(byte[].class);
            return n;
        }

        private static void checkRequiredMethods(Class<Unsafe> class_) throws NoSuchMethodException, SecurityException {
            class_.getMethod("arrayBaseOffset", Class.class);
            class_.getMethod("getByte", Object.class, Long.TYPE);
            class_.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            class_.getMethod("getLong", Object.class, Long.TYPE);
            class_.getMethod("objectFieldOffset", Field.class);
            class_.getMethod("getByte", Long.TYPE);
            class_.getMethod("getLong", Object.class, Long.TYPE);
            class_.getMethod("putByte", Long.TYPE, Byte.TYPE);
            class_.getMethod("getLong", Long.TYPE);
        }

        private static Field field(Class<?> object, String string2) {
            Field field;
            try {
                field = ((Class)object).getDeclaredField(string2);
                field.setAccessible(true);
            }
            catch (Throwable throwable) {
                field = null;
            }
            Logger logger = logger;
            Level level = Level.FINEST;
            String string3 = ((Class)object).getName();
            object = field != null ? "available" : "unavailable";
            logger.log(level, "{0}.{1}: {2}", new Object[]{string3, string2, object});
            return field;
        }

        private static long fieldOffset(Field field) {
            Unsafe unsafe;
            long l = field != null && (unsafe = UNSAFE) != null ? unsafe.objectFieldOffset(field) : -1L;
            return l;
        }

        private static Unsafe getUnsafe() {
            Object object;
            PrivilegedExceptionAction<Unsafe> privilegedExceptionAction = null;
            try {
                object = new PrivilegedExceptionAction<Unsafe>(){

                    @Override
                    public Unsafe run() throws Exception {
                        UnsafeProcessor.checkRequiredMethods(Unsafe.class);
                        for (Field field : Unsafe.class.getDeclaredFields()) {
                            field.setAccessible(true);
                            Object object = field.get(null);
                            if (!Unsafe.class.isInstance(object)) continue;
                            return (Unsafe)Unsafe.class.cast(object);
                        }
                        return null;
                    }
                };
                privilegedExceptionAction = object = AccessController.doPrivileged(object);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            Logger logger = logger;
            Level level = Level.FINEST;
            object = privilegedExceptionAction != null ? "available" : "unavailable";
            logger.log(level, "sun.misc.Unsafe: {}", object);
            return privilegedExceptionAction;
        }

        static boolean isAvailable() {
            return AVAILABLE;
        }

        private static int partialIsValidUtf8(long l, int n) {
            int n2 = UnsafeProcessor.unsafeEstimateConsecutiveAscii(l, n);
            l += (long)n2;
            n -= n2;
            do {
                Unsafe unsafe;
                long l2;
                int n3 = 0;
                n2 = n;
                n = n3;
                do {
                    l2 = l;
                    if (n2 <= 0) break;
                    unsafe = UNSAFE;
                    l2 = l + 1L;
                    n = n3 = (int)unsafe.getByte(l);
                    if (n3 < 0) break;
                    --n2;
                    l = l2;
                } while (true);
                if (n2 == 0) {
                    return 0;
                }
                --n2;
                if (n < -32) {
                    if (n2 == 0) {
                        return n;
                    }
                    --n2;
                    if (n >= -62 && UNSAFE.getByte(l2) <= -65) {
                        l = 1L + l2;
                        n = n2;
                        continue;
                    }
                    return -1;
                }
                if (n < -16) {
                    if (n2 < 2) {
                        return UnsafeProcessor.unsafeIncompleteStateFor(l2, n, n2);
                    }
                    n2 -= 2;
                    unsafe = UNSAFE;
                    l = l2 + 1L;
                    n3 = unsafe.getByte(l2);
                    if (!(n3 > -65 || n == -32 && n3 < -96 || n == -19 && n3 >= -96 || UNSAFE.getByte(l) > -65)) {
                        l = 1L + l;
                        n = n2;
                        continue;
                    }
                    return -1;
                }
                if (n2 < 3) {
                    return UnsafeProcessor.unsafeIncompleteStateFor(l2, n, n2);
                }
                n2 -= 3;
                unsafe = UNSAFE;
                l = l2 + 1L;
                n3 = unsafe.getByte(l2);
                if (n3 > -65 || (n << 28) + (n3 + 112) >> 30 != 0) break;
                unsafe = UNSAFE;
                l2 = l + 1L;
                if (unsafe.getByte(l) > -65 || UNSAFE.getByte(l2) > -65) break;
                l = l2 + 1L;
                n = n2;
            } while (true);
            return -1;
        }

        private static int partialIsValidUtf8(byte[] arrby, long l, int n) {
            int n2 = UnsafeProcessor.unsafeEstimateConsecutiveAscii(arrby, l, n);
            n -= n2;
            l += (long)n2;
            do {
                long l2;
                Unsafe unsafe;
                int n3 = 0;
                n2 = n;
                n = n3;
                do {
                    l2 = l;
                    if (n2 <= 0) break;
                    unsafe = UNSAFE;
                    l2 = l + 1L;
                    n = n3 = (int)unsafe.getByte((Object)arrby, l);
                    if (n3 < 0) break;
                    --n2;
                    l = l2;
                } while (true);
                if (n2 == 0) {
                    return 0;
                }
                --n2;
                if (n < -32) {
                    if (n2 == 0) {
                        return n;
                    }
                    --n2;
                    if (n >= -62 && UNSAFE.getByte((Object)arrby, l2) <= -65) {
                        l = 1L + l2;
                        n = n2;
                        continue;
                    }
                    return -1;
                }
                if (n < -16) {
                    if (n2 < 2) {
                        return UnsafeProcessor.unsafeIncompleteStateFor(arrby, n, l2, n2);
                    }
                    n2 -= 2;
                    unsafe = UNSAFE;
                    l = l2 + 1L;
                    n3 = unsafe.getByte((Object)arrby, l2);
                    if (!(n3 > -65 || n == -32 && n3 < -96 || n == -19 && n3 >= -96 || UNSAFE.getByte((Object)arrby, l) > -65)) {
                        l = 1L + l;
                        n = n2;
                        continue;
                    }
                    return -1;
                }
                if (n2 < 3) {
                    return UnsafeProcessor.unsafeIncompleteStateFor(arrby, n, l2, n2);
                }
                n2 -= 3;
                unsafe = UNSAFE;
                l = l2 + 1L;
                n3 = unsafe.getByte((Object)arrby, l2);
                if (n3 > -65 || (n << 28) + (n3 + 112) >> 30 != 0) break;
                unsafe = UNSAFE;
                l2 = l + 1L;
                if (unsafe.getByte((Object)arrby, l) > -65 || UNSAFE.getByte((Object)arrby, l2) > -65) break;
                l = l2 + 1L;
                n = n2;
            } while (true);
            return -1;
        }

        private static int unsafeEstimateConsecutiveAscii(long l, int n) {
            int n2;
            if (n < 16) {
                return 0;
            }
            int n3 = n2 = (int)l & 7;
            while (n3 > 0) {
                if (UNSAFE.getByte(l) < 0) {
                    return n2 - n3;
                }
                --n3;
                l = 1L + l;
            }
            for (n3 = n - n2; n3 >= 8 && (UNSAFE.getLong(l) & -9187201950435737472L) == 0L; n3 -= 8) {
                l += 8L;
            }
            return n - n3;
        }

        private static int unsafeEstimateConsecutiveAscii(byte[] arrby, long l, int n) {
            int n2;
            if (n < 16) {
                return 0;
            }
            int n3 = n2 = (int)l & 7;
            while (n3 > 0) {
                if (UNSAFE.getByte((Object)arrby, l) < 0) {
                    return n2 - n3;
                }
                --n3;
                l = 1L + l;
            }
            for (n3 = n - n2; n3 >= 8 && (UNSAFE.getLong((Object)arrby, l) & -9187201950435737472L) == 0L; n3 -= 8) {
                l += 8L;
            }
            return n - n3;
        }

        private static int unsafeIncompleteStateFor(long l, int n, int n2) {
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 == 2) {
                        return Utf8.incompleteStateFor(n, UnsafeProcessor.UNSAFE.getByte(l), UnsafeProcessor.UNSAFE.getByte(1L + l));
                    }
                    throw new AssertionError();
                }
                return Utf8.incompleteStateFor(n, UnsafeProcessor.UNSAFE.getByte(l));
            }
            return Utf8.incompleteStateFor(n);
        }

        private static int unsafeIncompleteStateFor(byte[] arrby, int n, long l, int n2) {
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 == 2) {
                        return Utf8.incompleteStateFor(n, UnsafeProcessor.UNSAFE.getByte((Object)arrby, l), UnsafeProcessor.UNSAFE.getByte((Object)arrby, 1L + l));
                    }
                    throw new AssertionError();
                }
                return Utf8.incompleteStateFor(n, UnsafeProcessor.UNSAFE.getByte((Object)arrby, l));
            }
            return Utf8.incompleteStateFor(n);
        }

        @Override
        int encodeUtf8(CharSequence charSequence, byte[] object, int n, int n2) {
            int n3;
            block7 : {
                int n4;
                long l;
                long l2 = ARRAY_BASE_OFFSET + n;
                long l3 = (long)n2 + l2;
                n3 = charSequence.length();
                String string2 = " at index ";
                String string3 = "Failed writing ";
                if (n3 > n2 || ((byte[])object).length - n2 < n) break block7;
                n2 = 0;
                do {
                    n4 = 128;
                    l = 1L;
                    if (n2 >= n3 || (n = (int)charSequence.charAt(n2)) >= 128) break;
                    UNSAFE.putByte(object, l2, (byte)n);
                    ++n2;
                    l2 = 1L + l2;
                } while (true);
                long l4 = l2;
                if (n2 == n3) {
                    return (int)(l2 - (long)ARRAY_BASE_OFFSET);
                }
                for (n = n2; n < n3; ++n) {
                    int n5;
                    block12 : {
                        block13 : {
                            block9 : {
                                char c;
                                Unsafe unsafe;
                                block11 : {
                                    block10 : {
                                        block8 : {
                                            n5 = charSequence.charAt(n);
                                            if (n5 >= n4 || l4 >= l3) break block8;
                                            UNSAFE.putByte(object, l4, (byte)n5);
                                            l2 = l4 + l;
                                            break block9;
                                        }
                                        if (n5 >= 2048 || l4 > l3 - 2L) break block10;
                                        unsafe = UNSAFE;
                                        l2 = l4 + 1L;
                                        unsafe.putByte(object, l4, (byte)(n5 >>> 6 | 960));
                                        UNSAFE.putByte(object, l2, (byte)(n5 & 63 | 128));
                                        break block9;
                                    }
                                    if (n5 >= 55296 && 57343 >= n5) break block11;
                                    if (l4 > l3 - 3L) break block11;
                                    unsafe = UNSAFE;
                                    l2 = l4 + 1L;
                                    unsafe.putByte(object, l4, (byte)(n5 >>> 12 | 480));
                                    unsafe = UNSAFE;
                                    l4 = l2 + 1L;
                                    unsafe.putByte(object, l2, (byte)(n5 >>> 6 & 63 | 128));
                                    UNSAFE.putByte(object, l4, (byte)(n5 & 63 | 128));
                                    l2 = l4 + 1L;
                                    break block9;
                                }
                                if (l4 > l3 - 4L) break block12;
                                if (n + 1 == n3 || !Character.isSurrogatePair((char)n5, c = charSequence.charAt(++n))) break block13;
                                n2 = Character.toCodePoint((char)n5, c);
                                unsafe = UNSAFE;
                                l = l4 + 1L;
                                unsafe.putByte(object, l4, (byte)(n2 >>> 18 | 240));
                                unsafe = UNSAFE;
                                l2 = l + 1L;
                                unsafe.putByte(object, l, (byte)(n2 >>> 12 & 63 | 128));
                                unsafe = UNSAFE;
                                l4 = l2 + 1L;
                                unsafe.putByte(object, l2, (byte)(n2 >>> 6 & 63 | 128));
                                UNSAFE.putByte(object, l4, (byte)(n2 & 63 | 128));
                                l2 = l4 + 1L;
                            }
                            n4 = 128;
                            l = 1L;
                            l4 = ++l2;
                            continue;
                        }
                        throw new UnpairedSurrogateException(n - 1, n3);
                    }
                    if (!(55296 > n5 || n5 > 57343 || n + 1 != n3 && Character.isSurrogatePair((char)n5, charSequence.charAt(n + 1)))) {
                        throw new UnpairedSurrogateException(n, n3);
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string3);
                    ((StringBuilder)charSequence).append((char)n5);
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append(l4);
                    throw new ArrayIndexOutOfBoundsException(((StringBuilder)charSequence).toString());
                }
                return (int)(l4 - (long)ARRAY_BASE_OFFSET);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed writing ");
            ((StringBuilder)object).append(charSequence.charAt(n3 - 1));
            ((StringBuilder)object).append(" at index ");
            ((StringBuilder)object).append(n + n2);
            throw new ArrayIndexOutOfBoundsException(((StringBuilder)object).toString());
        }

        @Override
        void encodeUtf8Direct(CharSequence charSequence, ByteBuffer byteBuffer) {
            int n;
            block7 : {
                int n2;
                int n3;
                long l;
                long l2 = UnsafeProcessor.addressOffset(byteBuffer);
                long l3 = (long)byteBuffer.position() + l2;
                long l4 = (long)byteBuffer.limit() + l2;
                n = charSequence.length();
                if ((long)n > l4 - l3) break block7;
                int n4 = 0;
                do {
                    n2 = 128;
                    l = 1L;
                    if (n4 >= n || (n3 = charSequence.charAt(n4)) >= 128) break;
                    UNSAFE.putByte(l3, (byte)n3);
                    ++n4;
                    l3 = 1L + l3;
                } while (true);
                long l5 = l2;
                long l6 = l3;
                if (n4 == n) {
                    byteBuffer.position((int)(l3 - l2));
                    return;
                }
                for (n3 = n4; n3 < n; ++n3) {
                    int n5;
                    block12 : {
                        block13 : {
                            block9 : {
                                char c;
                                Unsafe unsafe;
                                block11 : {
                                    block10 : {
                                        block8 : {
                                            n5 = charSequence.charAt(n3);
                                            if (n5 >= n2 || l6 >= l4) break block8;
                                            UNSAFE.putByte(l6, (byte)n5);
                                            l3 = l6 + l;
                                            break block9;
                                        }
                                        if (n5 >= 2048 || l6 > l4 - 2L) break block10;
                                        unsafe = UNSAFE;
                                        l = l6 + 1L;
                                        unsafe.putByte(l6, (byte)(n5 >>> 6 | 960));
                                        unsafe = UNSAFE;
                                        l3 = l + 1L;
                                        unsafe.putByte(l, (byte)(n5 & 63 | 128));
                                        break block9;
                                    }
                                    if (n5 >= 55296 && 57343 >= n5 || l6 > l4 - 3L) break block11;
                                    unsafe = UNSAFE;
                                    l3 = l6 + 1L;
                                    unsafe.putByte(l6, (byte)(n5 >>> 12 | 480));
                                    unsafe = UNSAFE;
                                    l6 = l3 + 1L;
                                    unsafe.putByte(l3, (byte)(n5 >>> 6 & 63 | 128));
                                    UNSAFE.putByte(l6, (byte)(n5 & 63 | 128));
                                    l3 = l6 + 1L;
                                    break block9;
                                }
                                if (l6 > l4 - 4L) break block12;
                                if (n3 + 1 == n || !Character.isSurrogatePair((char)n5, c = charSequence.charAt(++n3))) break block13;
                                n4 = Character.toCodePoint((char)n5, c);
                                unsafe = UNSAFE;
                                l = l6 + 1L;
                                unsafe.putByte(l6, (byte)(n4 >>> 18 | 240));
                                unsafe = UNSAFE;
                                l3 = l + 1L;
                                unsafe.putByte(l, (byte)(n4 >>> 12 & 63 | 128));
                                unsafe = UNSAFE;
                                l6 = l3 + 1L;
                                unsafe.putByte(l3, (byte)(n4 >>> 6 & 63 | 128));
                                unsafe = UNSAFE;
                                l3 = l6 + 1L;
                                unsafe.putByte(l6, (byte)(n4 & 63 | 128));
                            }
                            n2 = 128;
                            l = 1L;
                            l6 = l3;
                            continue;
                        }
                        throw new UnpairedSurrogateException(n3 - 1, n);
                    }
                    if (!(55296 > n5 || n5 > 57343 || n3 + 1 != n && Character.isSurrogatePair((char)n5, charSequence.charAt(n3 + 1)))) {
                        throw new UnpairedSurrogateException(n3, n);
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Failed writing ");
                    ((StringBuilder)charSequence).append((char)n5);
                    ((StringBuilder)charSequence).append(" at index ");
                    ((StringBuilder)charSequence).append(l6);
                    throw new ArrayIndexOutOfBoundsException(((StringBuilder)charSequence).toString());
                }
                byteBuffer.position((int)(l6 - l5));
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed writing ");
            stringBuilder.append(charSequence.charAt(n - 1));
            stringBuilder.append(" at index ");
            stringBuilder.append(byteBuffer.limit());
            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
        }

        @Override
        int partialIsValidUtf8(int n, byte[] arrby, int n2, int n3) {
            block15 : {
                long l;
                long l2;
                block19 : {
                    long l3;
                    block16 : {
                        block22 : {
                            int n4;
                            Unsafe unsafe;
                            byte by;
                            block20 : {
                                block21 : {
                                    Unsafe unsafe2;
                                    block17 : {
                                        block18 : {
                                            if ((n2 | n3 | arrby.length - n3) < 0) break block15;
                                            n4 = ARRAY_BASE_OFFSET;
                                            l3 = n4 + n2;
                                            l2 = n4 + n3;
                                            if (n == 0) break block16;
                                            if (l3 >= l2) {
                                                return n;
                                            }
                                            by = (byte)n;
                                            if (by >= -32) break block17;
                                            if (by < -62) break block18;
                                            Unsafe unsafe3 = UNSAFE;
                                            l = 1L + l3;
                                            if (unsafe3.getByte((Object)arrby, l3) <= -65) break block19;
                                        }
                                        return -1;
                                    }
                                    if (by >= -16) break block20;
                                    if ((n = (int)((byte)(n >> 8))) == 0) {
                                        unsafe2 = UNSAFE;
                                        long l4 = l3 + 1L;
                                        n = n2 = (int)unsafe2.getByte((Object)arrby, l3);
                                        l = l4;
                                        if (l4 >= l2) {
                                            return Utf8.incompleteStateFor(by, n2);
                                        }
                                    } else {
                                        l = l3;
                                    }
                                    if (n > -65 || by == -32 && n < -96 || by == -19 && n >= -96) break block21;
                                    unsafe2 = UNSAFE;
                                    l3 = 1L + l;
                                    if (unsafe2.getByte((Object)arrby, l) > -65) break block21;
                                    l = l3;
                                    break block19;
                                }
                                return -1;
                            }
                            n2 = (byte)(n >> 8);
                            n3 = 0;
                            if (n2 == 0) {
                                unsafe = UNSAFE;
                                long l5 = l3 + 1L;
                                n2 = n4 = (int)unsafe.getByte((Object)arrby, l3);
                                n = n3;
                                l = l5;
                                if (l5 >= l2) {
                                    return Utf8.incompleteStateFor(by, n4);
                                }
                            } else {
                                n = (byte)(n >> 16);
                                l = l3;
                            }
                            n3 = n;
                            l3 = l;
                            if (n == 0) {
                                unsafe = UNSAFE;
                                l3 = l + 1L;
                                n3 = unsafe.getByte((Object)arrby, l);
                                if (l3 >= l2) {
                                    return Utf8.incompleteStateFor(by, n2, n3);
                                }
                            }
                            if (n2 > -65 || (by << 28) + (n2 + 112) >> 30 != 0 || n3 > -65) break block22;
                            unsafe = UNSAFE;
                            l = 1L + l3;
                            if (unsafe.getByte((Object)arrby, l3) <= -65) break block19;
                        }
                        return -1;
                    }
                    l = l3;
                }
                return UnsafeProcessor.partialIsValidUtf8(arrby, l, (int)(l2 - l));
            }
            throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", arrby.length, n2, n3));
        }

        @Override
        int partialIsValidUtf8Direct(int n, ByteBuffer object, int n2, int n3) {
            block15 : {
                long l;
                long l2;
                block19 : {
                    long l3;
                    block16 : {
                        block22 : {
                            byte by;
                            block20 : {
                                block21 : {
                                    block17 : {
                                        block18 : {
                                            if ((n2 | n3 | ((Buffer)object).limit() - n3) < 0) break block15;
                                            l3 = UnsafeProcessor.addressOffset((ByteBuffer)object) + (long)n2;
                                            l = (long)(n3 - n2) + l3;
                                            if (n == 0) break block16;
                                            if (l3 >= l) {
                                                return n;
                                            }
                                            by = (byte)n;
                                            if (by >= -32) break block17;
                                            if (by < -62) break block18;
                                            object = UNSAFE;
                                            l2 = 1L + l3;
                                            if (((Unsafe)object).getByte(l3) <= -65) break block19;
                                        }
                                        return -1;
                                    }
                                    if (by >= -16) break block20;
                                    if ((n = (int)((byte)(n >> 8))) == 0) {
                                        object = UNSAFE;
                                        long l4 = l3 + 1L;
                                        n = n2 = (int)((Unsafe)object).getByte(l3);
                                        l2 = l4;
                                        if (l4 >= l) {
                                            return Utf8.incompleteStateFor(by, n2);
                                        }
                                    } else {
                                        l2 = l3;
                                    }
                                    if (n > -65 || by == -32 && n < -96 || by == -19 && n >= -96) break block21;
                                    object = UNSAFE;
                                    l3 = 1L + l2;
                                    if (((Unsafe)object).getByte(l2) > -65) break block21;
                                    l2 = l3;
                                    break block19;
                                }
                                return -1;
                            }
                            n2 = (byte)(n >> 8);
                            n3 = 0;
                            if (n2 == 0) {
                                object = UNSAFE;
                                long l5 = l3 + 1L;
                                byte by2 = ((Unsafe)object).getByte(l3);
                                n2 = by2;
                                n = n3;
                                l2 = l5;
                                if (l5 >= l) {
                                    return Utf8.incompleteStateFor(by, by2);
                                }
                            } else {
                                n = (byte)(n >> 16);
                                l2 = l3;
                            }
                            n3 = n;
                            l3 = l2;
                            if (n == 0) {
                                object = UNSAFE;
                                l3 = l2 + 1L;
                                n3 = ((Unsafe)object).getByte(l2);
                                if (l3 >= l) {
                                    return Utf8.incompleteStateFor(by, n2, n3);
                                }
                            }
                            if (n2 > -65 || (by << 28) + (n2 + 112) >> 30 != 0 || n3 > -65) break block22;
                            object = UNSAFE;
                            l2 = 1L + l3;
                            if (((Unsafe)object).getByte(l3) <= -65) break block19;
                        }
                        return -1;
                    }
                    l2 = l3;
                }
                return UnsafeProcessor.partialIsValidUtf8(l2, (int)(l - l2));
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", ((Buffer)object).limit(), n2, n3));
        }

    }

}

