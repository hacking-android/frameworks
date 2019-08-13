/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import java.io.UnsupportedEncodingException;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    @UnsupportedAppUsage
    private Base64() {
    }

    public static byte[] decode(String string2, int n) {
        return Base64.decode(string2.getBytes(), n);
    }

    public static byte[] decode(byte[] arrby, int n) {
        return Base64.decode(arrby, 0, arrby.length, n);
    }

    public static byte[] decode(byte[] arrby, int n, int n2, int n3) {
        Decoder decoder = new Decoder(n3, new byte[n2 * 3 / 4]);
        if (decoder.process(arrby, n, n2, true)) {
            if (decoder.op == decoder.output.length) {
                return decoder.output;
            }
            arrby = new byte[decoder.op];
            System.arraycopy(decoder.output, 0, arrby, 0, decoder.op);
            return arrby;
        }
        throw new IllegalArgumentException("bad base-64");
    }

    public static byte[] encode(byte[] arrby, int n) {
        return Base64.encode(arrby, 0, arrby.length, n);
    }

    public static byte[] encode(byte[] arrby, int n, int n2, int n3) {
        Encoder encoder = new Encoder(n3, null);
        int n4 = n2 / 3 * 4;
        boolean bl = encoder.do_padding;
        int n5 = 2;
        if (bl) {
            n3 = n4;
            if (n2 % 3 > 0) {
                n3 = n4 + 4;
            }
        } else {
            n3 = n2 % 3;
            n3 = n3 != 0 ? (n3 != 1 ? (n3 != 2 ? n4 : n4 + 3) : n4 + 2) : n4;
        }
        n4 = n3;
        if (encoder.do_newline) {
            n4 = n3;
            if (n2 > 0) {
                int n6 = (n2 - 1) / 57;
                n4 = encoder.do_cr ? n5 : 1;
                n4 = n3 + (n6 + 1) * n4;
            }
        }
        encoder.output = new byte[n4];
        encoder.process(arrby, n, n2, true);
        return encoder.output;
    }

    public static String encodeToString(byte[] object, int n) {
        try {
            object = new String(Base64.encode(object, n), "US-ASCII");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError(unsupportedEncodingException);
        }
    }

    public static String encodeToString(byte[] object, int n, int n2, int n3) {
        try {
            object = new String(Base64.encode(object, n, n2, n3), "US-ASCII");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError(unsupportedEncodingException);
        }
    }

    static abstract class Coder {
        public int op;
        public byte[] output;

        Coder() {
        }

        public abstract int maxOutputSize(int var1);

        public abstract boolean process(byte[] var1, int var2, int var3, boolean var4);
    }

    static class Decoder
    extends Coder {
        private static final int[] DECODE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        public Decoder(int n, byte[] arrby) {
            this.output = arrby;
            arrby = (n & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.alphabet = arrby;
            this.state = 0;
            this.value = 0;
        }

        @Override
        public int maxOutputSize(int n) {
            return n * 3 / 4 + 10;
        }

        @Override
        public boolean process(byte[] arrby, int n, int n2, boolean bl) {
            int n3;
            int n4;
            if (this.state == 6) {
                return false;
            }
            int n5 = n;
            int n6 = n2 + n;
            int n7 = this.state;
            n2 = this.value;
            n = 0;
            byte[] arrby2 = this.output;
            int[] arrn = this.alphabet;
            do {
                n4 = n2;
                n3 = n;
                if (n5 >= n6) break;
                int n8 = n5;
                n4 = n2;
                n3 = n;
                if (n7 == 0) {
                    while (n5 + 4 <= n6) {
                        n2 = n3 = (n4 = arrn[arrby[n5] & 255] << 18 | arrn[arrby[n5 + 1] & 255] << 12 | arrn[arrby[n5 + 2] & 255] << 6 | arrn[arrby[n5 + 3] & 255]);
                        if (n4 < 0) break;
                        arrby2[n + 2] = (byte)n3;
                        arrby2[n + 1] = (byte)(n3 >> 8);
                        arrby2[n] = (byte)(n3 >> 16);
                        n += 3;
                        n5 += 4;
                        n2 = n3;
                    }
                    n8 = n5;
                    n4 = n2;
                    n3 = n;
                    if (n5 >= n6) {
                        n4 = n2;
                        n3 = n;
                        break;
                    }
                }
                n5 = arrn[arrby[n8] & 255];
                if (n7 != 0) {
                    if (n7 != 1) {
                        if (n7 != 2) {
                            if (n7 != 3) {
                                if (n7 != 4) {
                                    if (n7 != 5) {
                                        n = n7;
                                        n2 = n4;
                                    } else {
                                        n = n7;
                                        n2 = n4;
                                        if (n5 != -1) {
                                            this.state = 6;
                                            return false;
                                        }
                                    }
                                } else if (n5 == -2) {
                                    n = n7 + 1;
                                    n2 = n4;
                                } else {
                                    n = n7;
                                    n2 = n4;
                                    if (n5 != -1) {
                                        this.state = 6;
                                        return false;
                                    }
                                }
                            } else if (n5 >= 0) {
                                n2 = n4 << 6 | n5;
                                arrby2[n3 + 2] = (byte)n2;
                                arrby2[n3 + 1] = (byte)(n2 >> 8);
                                arrby2[n3] = (byte)(n2 >> 16);
                                n3 += 3;
                                n = 0;
                            } else if (n5 == -2) {
                                arrby2[n3 + 1] = (byte)(n4 >> 2);
                                arrby2[n3] = (byte)(n4 >> 10);
                                n3 += 2;
                                n = 5;
                                n2 = n4;
                            } else {
                                n = n7;
                                n2 = n4;
                                if (n5 != -1) {
                                    this.state = 6;
                                    return false;
                                }
                            }
                        } else if (n5 >= 0) {
                            n2 = n4 << 6 | n5;
                            n = n7 + 1;
                        } else if (n5 == -2) {
                            arrby2[n3] = (byte)(n4 >> 4);
                            n = 4;
                            ++n3;
                            n2 = n4;
                        } else {
                            n = n7;
                            n2 = n4;
                            if (n5 != -1) {
                                this.state = 6;
                                return false;
                            }
                        }
                    } else if (n5 >= 0) {
                        n2 = n4 << 6 | n5;
                        n = n7 + 1;
                    } else {
                        n = n7;
                        n2 = n4;
                        if (n5 != -1) {
                            this.state = 6;
                            return false;
                        }
                    }
                } else if (n5 >= 0) {
                    n2 = n5;
                    n = n7 + 1;
                } else {
                    n = n7;
                    n2 = n4;
                    if (n5 != -1) {
                        this.state = 6;
                        return false;
                    }
                }
                n5 = n8 + 1;
                n7 = n;
                n = n3;
            } while (true);
            if (!bl) {
                this.state = n7;
                this.value = n4;
                this.op = n3;
                return true;
            }
            if (n7 != 0) {
                if (n7 != 1) {
                    if (n7 != 2) {
                        if (n7 != 3) {
                            if (n7 == 4) {
                                this.state = 6;
                                return false;
                            }
                        } else {
                            n = n3 + 1;
                            arrby2[n3] = (byte)(n4 >> 10);
                            n3 = n + 1;
                            arrby2[n] = (byte)(n4 >> 2);
                        }
                    } else {
                        arrby2[n3] = (byte)(n4 >> 4);
                        ++n3;
                    }
                } else {
                    this.state = 6;
                    return false;
                }
            }
            this.state = n7;
            this.op = n3;
            return true;
        }
    }

    static class Encoder
    extends Coder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final byte[] ENCODE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] ENCODE_WEBSAFE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        public Encoder(int n, byte[] arrby) {
            this.output = arrby;
            boolean bl = true;
            boolean bl2 = (n & 1) == 0;
            this.do_padding = bl2;
            bl2 = (n & 2) == 0;
            this.do_newline = bl2;
            bl2 = (n & 4) != 0 ? bl : false;
            this.do_cr = bl2;
            arrby = (n & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.alphabet = arrby;
            this.tail = new byte[2];
            this.tailLen = 0;
            n = this.do_newline ? 19 : -1;
            this.count = n;
        }

        @Override
        public int maxOutputSize(int n) {
            return n * 8 / 5 + 10;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public boolean process(byte[] var1_1, int var2_2, int var3_3, boolean var4_4) {
            block33 : {
                block32 : {
                    var5_5 = this.alphabet;
                    var6_6 = this.output;
                    var7_7 = 0;
                    var8_8 = this.count;
                    var9_9 = var3_3 + var2_2;
                    var10_10 = -1;
                    var3_3 = this.tailLen;
                    if (var3_3 == 0) ** GOTO lbl-1000
                    if (var3_3 == 1) break block32;
                    if (var3_3 != 2 || var2_2 + 1 > var9_9) ** GOTO lbl-1000
                    var11_11 = this.tail;
                    var3_3 = var11_11[0];
                    var10_10 = var11_11[1];
                    var12_12 = var2_2 + 1;
                    var10_10 = (var10_10 & 255) << 8 | (var3_3 & 255) << 16 | var1_1[var2_2] & 255;
                    this.tailLen = 0;
                    break block33;
                }
                if (var2_2 + 2 <= var9_9) {
                    var12_12 = this.tail[0];
                    var3_3 = var2_2 + 1;
                    var10_10 = (var1_1[var2_2] & 255) << 8 | (var12_12 & 255) << 16 | var1_1[var3_3] & 255;
                    this.tailLen = 0;
                    var12_12 = var3_3 + 1;
                } else lbl-1000: // 3 sources:
                {
                    var12_12 = var2_2;
                }
            }
            var2_2 = var7_7;
            var3_3 = var8_8;
            var7_7 = var12_12;
            if (var10_10 != -1) {
                var2_2 = 0 + 1;
                var6_6[0] = var5_5[var10_10 >> 18 & 63];
                var3_3 = var2_2 + 1;
                var6_6[var2_2] = var5_5[var10_10 >> 12 & 63];
                var2_2 = var3_3 + 1;
                var6_6[var3_3] = var5_5[var10_10 >> 6 & 63];
                var13_13 = var2_2 + 1;
                var6_6[var2_2] = var5_5[var10_10 & 63];
                var10_10 = var8_8 - 1;
                var2_2 = var13_13;
                var3_3 = var10_10;
                var7_7 = var12_12;
                if (var10_10 == 0) {
                    var2_2 = var13_13;
                    if (this.do_cr) {
                        var6_6[var13_13] = (byte)13;
                        var2_2 = var13_13 + 1;
                    }
                    var6_6[var2_2] = (byte)10;
                    var3_3 = 19;
                    ++var2_2;
                    var7_7 = var12_12;
                }
            }
            while (var7_7 + 3 <= var9_9) {
                var12_12 = (var1_1[var7_7] & 255) << 16 | (var1_1[var7_7 + 1] & 255) << 8 | var1_1[var7_7 + 2] & 255;
                var6_6[var2_2] = var5_5[var12_12 >> 18 & 63];
                var6_6[var2_2 + 1] = var5_5[var12_12 >> 12 & 63];
                var6_6[var2_2 + 2] = var5_5[var12_12 >> 6 & 63];
                var6_6[var2_2 + 3] = var5_5[var12_12 & 63];
                var10_10 = var7_7 + 3;
                var12_12 = var2_2 + 4;
                var13_13 = var3_3 - 1;
                var2_2 = var12_12;
                var3_3 = var13_13;
                var7_7 = var10_10;
                if (var13_13 != 0) continue;
                var2_2 = var12_12;
                if (this.do_cr) {
                    var6_6[var12_12] = (byte)13;
                    var2_2 = var12_12 + 1;
                }
                var6_6[var2_2] = (byte)10;
                var3_3 = 19;
                ++var2_2;
                var7_7 = var10_10;
            }
            if (var4_4) {
                var10_10 = this.tailLen;
                if (var7_7 - var10_10 == var9_9 - 1) {
                    if (var10_10 > 0) {
                        var1_1 = this.tail;
                        var13_13 = 0 + 1;
                        var12_12 = var1_1[0];
                        var10_10 = var7_7;
                        var7_7 = var13_13;
                    } else {
                        var10_10 = var7_7 + 1;
                        var12_12 = var1_1[var7_7];
                        var7_7 = 0;
                    }
                    var12_12 = (var12_12 & 255) << 4;
                    this.tailLen -= var7_7;
                    var10_10 = var2_2 + 1;
                    var6_6[var2_2] = var5_5[var12_12 >> 6 & 63];
                    var7_7 = var10_10 + 1;
                    var6_6[var10_10] = var5_5[var12_12 & 63];
                    var2_2 = var7_7;
                    if (this.do_padding) {
                        var12_12 = var7_7 + 1;
                        var6_6[var7_7] = (byte)61;
                        var2_2 = var12_12 + 1;
                        var6_6[var12_12] = (byte)61;
                    }
                    var7_7 = var2_2;
                    if (this.do_newline) {
                        var7_7 = var2_2;
                        if (this.do_cr) {
                            var6_6[var2_2] = (byte)13;
                            var7_7 = var2_2 + 1;
                        }
                        var6_6[var7_7] = (byte)10;
                        ++var7_7;
                    }
                } else if (var7_7 - var10_10 == var9_9 - 2) {
                    var12_12 = 0;
                    if (var10_10 > 1) {
                        var10_10 = this.tail[0];
                        var12_12 = 0 + 1;
                    } else {
                        var13_13 = var7_7 + 1;
                        var10_10 = var1_1[var7_7];
                        var7_7 = var13_13;
                    }
                    if (this.tailLen > 0) {
                        var1_1 = this.tail;
                        var8_8 = var12_12 + 1;
                        var12_12 = var1_1[var12_12];
                        var13_13 = var7_7;
                        var7_7 = var8_8;
                    } else {
                        var13_13 = var7_7 + 1;
                        var8_8 = var1_1[var7_7];
                        var7_7 = var12_12;
                        var12_12 = var8_8;
                    }
                    var12_12 = (var12_12 & 255) << 2 | (var10_10 & 255) << 10;
                    this.tailLen -= var7_7;
                    var10_10 = var2_2 + 1;
                    var6_6[var2_2] = var5_5[var12_12 >> 12 & 63];
                    var7_7 = var10_10 + 1;
                    var6_6[var10_10] = var5_5[var12_12 >> 6 & 63];
                    var2_2 = var7_7 + 1;
                    var6_6[var7_7] = var5_5[var12_12 & 63];
                    if (this.do_padding) {
                        var7_7 = var2_2 + 1;
                        var6_6[var2_2] = (byte)61;
                        var2_2 = var7_7;
                    }
                    var7_7 = var2_2;
                    if (this.do_newline) {
                        var7_7 = var2_2;
                        if (this.do_cr) {
                            var6_6[var2_2] = (byte)13;
                            var7_7 = var2_2 + 1;
                        }
                        var6_6[var7_7] = (byte)10;
                        ++var7_7;
                    }
                } else {
                    var7_7 = var2_2;
                    if (this.do_newline) {
                        var7_7 = var2_2;
                        if (var2_2 > 0) {
                            var7_7 = var2_2;
                            if (var3_3 != 19) {
                                var7_7 = var2_2;
                                if (this.do_cr) {
                                    var6_6[var2_2] = (byte)13;
                                    var7_7 = var2_2 + 1;
                                }
                                var6_6[var7_7] = (byte)10;
                                ++var7_7;
                            }
                        }
                    }
                }
                var12_12 = var7_7;
            } else if (var7_7 == var9_9 - 1) {
                var5_5 = this.tail;
                var12_12 = this.tailLen;
                this.tailLen = var12_12 + 1;
                var5_5[var12_12] = var1_1[var7_7];
                var12_12 = var2_2;
            } else {
                var12_12 = var2_2;
                if (var7_7 == var9_9 - 2) {
                    var5_5 = this.tail;
                    var12_12 = this.tailLen;
                    this.tailLen = var12_12 + 1;
                    var5_5[var12_12] = var1_1[var7_7];
                    var12_12 = this.tailLen;
                    this.tailLen = var12_12 + 1;
                    var5_5[var12_12] = var1_1[var7_7 + 1];
                    var12_12 = var2_2;
                }
            }
            this.op = var12_12;
            this.count = var3_3;
            return true;
        }
    }

}

