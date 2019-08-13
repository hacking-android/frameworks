/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class Base64 {
    private Base64() {
    }

    public static Decoder getDecoder() {
        return Decoder.RFC4648;
    }

    public static Encoder getEncoder() {
        return Encoder.RFC4648;
    }

    public static Decoder getMimeDecoder() {
        return Decoder.RFC2045;
    }

    public static Encoder getMimeEncoder() {
        return Encoder.RFC2045;
    }

    public static Encoder getMimeEncoder(int n, byte[] object) {
        Objects.requireNonNull(object);
        int[] arrn = Decoder.fromBase64;
        int n2 = ((byte[])object).length;
        for (int i = 0; i < n2; ++i) {
            byte by = object[i];
            if (arrn[by & 255] == -1) {
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal base64 line separator character 0x");
            ((StringBuilder)object).append(Integer.toString(by, 16));
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (n <= 0) {
            return Encoder.RFC4648;
        }
        return new Encoder(false, (byte[])object, n >> 2 << 2, true);
    }

    public static Decoder getUrlDecoder() {
        return Decoder.RFC4648_URLSAFE;
    }

    public static Encoder getUrlEncoder() {
        return Encoder.RFC4648_URLSAFE;
    }

    private static class DecInputStream
    extends InputStream {
        private final int[] base64;
        private int bits = 0;
        private boolean closed = false;
        private boolean eof = false;
        private final InputStream is;
        private final boolean isMIME;
        private int nextin = 18;
        private int nextout = -8;
        private byte[] sbBuf = new byte[1];

        DecInputStream(InputStream inputStream, int[] arrn, boolean bl) {
            this.is = inputStream;
            this.base64 = arrn;
            this.isMIME = bl;
        }

        @Override
        public int available() throws IOException {
            if (!this.closed) {
                return this.is.available();
            }
            throw new IOException("Stream is closed");
        }

        @Override
        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                this.is.close();
            }
        }

        @Override
        public int read() throws IOException {
            int n = this.read(this.sbBuf, 0, 1);
            int n2 = -1;
            if (n != -1) {
                n2 = this.sbBuf[0] & 255;
            }
            return n2;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public int read(byte[] var1_1, int var2_2, int var3_3) throws IOException {
            if (this.closed != false) throw new IOException("Stream is closed");
            if (this.eof && this.nextout < 0) {
                return -1;
            }
            if (var2_2 < 0) throw new IndexOutOfBoundsException();
            if (var3_3 < 0) throw new IndexOutOfBoundsException();
            if (var3_3 > ((Object)var1_1).length - var2_2) throw new IndexOutOfBoundsException();
            var4_4 = var2_2;
            var5_5 = var3_3;
            if (this.nextout >= 0) {
                var5_5 = var3_3;
                var3_3 = var2_2;
                do {
                    var4_4 = var3_3;
                    if (var5_5 == 0) {
                        return var4_4 - var2_2;
                    }
                    var3_3 = var4_4 + 1;
                    var6_6 = this.bits;
                    var7_7 = this.nextout;
                    var1_1[var4_4] = (byte)(var6_6 >> var7_7);
                    --var5_5;
                    this.nextout = var7_7 - 8;
                } while (this.nextout >= 0);
                this.bits = 0;
                var4_4 = var3_3;
            }
            do {
                block18 : {
                    block19 : {
                        var4_4 = var3_3 = var4_4;
                        if (var5_5 <= 0) return var4_4 - var2_2;
                        var4_4 = this.is.read();
                        if (var4_4 == -1) {
                            this.eof = true;
                            var6_6 = this.nextin;
                            var4_4 = var3_3;
                            if (var6_6 != 18) {
                                if (var6_6 == 12) throw new IOException("Base64 stream has one un-decoded dangling byte.");
                                var4_4 = var3_3 + 1;
                                var7_7 = this.bits;
                                var1_1[var3_3] = (byte)(var7_7 >> 16);
                                if (var6_6 == 0) {
                                    if (var5_5 - 1 == 0) {
                                        this.bits = var7_7 >> 8;
                                        this.nextout = 0;
                                    } else {
                                        var3_3 = var4_4 + 1;
                                        var1_1[var4_4] = (byte)(var7_7 >> 8);
                                        var4_4 = var3_3;
                                    }
                                }
                            }
                            if (var4_4 != var2_2) return var4_4 - var2_2;
                            return -1;
                        }
                        if (var4_4 != 61) break block18;
                        var4_4 = this.nextin;
                        if (var4_4 == 18 || var4_4 == 12 || var4_4 == 6 && this.is.read() != 61) break block19;
                        var4_4 = var3_3 + 1;
                        var7_7 = this.bits;
                        var1_1[var3_3] = (byte)(var7_7 >> 16);
                        if (this.nextin != 0) ** GOTO lbl61
                        if (var5_5 - 1 != 0) {
                            var3_3 = var4_4 + 1;
                            var1_1[var4_4] = (byte)(var7_7 >> 8);
                        } else {
                            this.bits = var7_7 >> 8;
                            this.nextout = 0;
lbl61: // 2 sources:
                            var3_3 = var4_4;
                        }
                        this.eof = true;
                        var4_4 = var3_3;
                        return var4_4 - var2_2;
                    }
                    var1_1 = new StringBuilder();
                    var1_1.append("Illegal base64 ending sequence:");
                    var1_1.append(this.nextin);
                    throw new IOException(var1_1.toString());
                }
                var7_7 = this.base64[var4_4];
                if (var7_7 == -1) {
                    if (!this.isMIME) {
                        var1_1 = new StringBuilder();
                        var1_1.append("Illegal base64 character ");
                        var1_1.append(Integer.toString(var7_7, 16));
                        throw new IOException(var1_1.toString());
                    }
                    var4_4 = var3_3;
                    continue;
                }
                var4_4 = this.bits;
                var6_6 = this.nextin;
                this.bits = var4_4 | var7_7 << var6_6;
                if (var6_6 != 0) {
                    this.nextin = var6_6 - 6;
                } else {
                    this.nextin = 18;
                    this.nextout = 16;
                    while ((var7_7 = this.nextout) >= 0) {
                        var4_4 = var3_3 + 1;
                        var1_1[var3_3] = (byte)(this.bits >> var7_7);
                        this.nextout = var7_7 - 8;
                        if (--var5_5 == 0 && this.nextout >= 0) {
                            return var4_4 - var2_2;
                        }
                        var3_3 = var4_4;
                    }
                    this.bits = 0;
                }
                var4_4 = var3_3;
            } while (true);
        }
    }

    public static class Decoder {
        static final Decoder RFC2045;
        static final Decoder RFC4648;
        static final Decoder RFC4648_URLSAFE;
        private static final int[] fromBase64;
        private static final int[] fromBase64URL;
        private final boolean isMIME;
        private final boolean isURL;

        static {
            int n;
            fromBase64 = new int[256];
            Arrays.fill(fromBase64, -1);
            for (n = 0; n < Encoder.toBase64.length; ++n) {
                Decoder.fromBase64[Encoder.access$200()[n]] = n;
            }
            Decoder.fromBase64[61] = -2;
            fromBase64URL = new int[256];
            Arrays.fill(fromBase64URL, -1);
            for (n = 0; n < Encoder.toBase64URL.length; ++n) {
                Decoder.fromBase64URL[Encoder.access$300()[n]] = n;
            }
            Decoder.fromBase64URL[61] = -2;
            RFC4648 = new Decoder(false, false);
            RFC4648_URLSAFE = new Decoder(true, false);
            RFC2045 = new Decoder(false, true);
        }

        private Decoder(boolean bl, boolean bl2) {
            this.isURL = bl;
            this.isMIME = bl2;
        }

        private int decode0(byte[] object, int n, int n2, byte[] object2) {
            block20 : {
                int[] arrn;
                int n3;
                int n4;
                block18 : {
                    int n5;
                    block19 : {
                        int n6;
                        block17 : {
                            arrn = this.isURL ? fromBase64URL : fromBase64;
                            n3 = 0;
                            n6 = 0;
                            n5 = 18;
                            do {
                                block13 : {
                                    block14 : {
                                        block16 : {
                                            block15 : {
                                                n4 = n;
                                                if (n >= n2) break;
                                                n4 = n + 1;
                                                if ((n = arrn[object[n] & 255]) >= 0) break block13;
                                                if (n != -2) break block14;
                                                n = n4;
                                                if (n5 != 6) break block15;
                                                if (n4 == n2 || object[n4] != 61) break block16;
                                                n = n4 + 1;
                                            }
                                            if (n5 != 18) {
                                                n4 = n;
                                                break;
                                            }
                                        }
                                        throw new IllegalArgumentException("Input byte array has wrong 4-byte ending unit");
                                    }
                                    if (this.isMIME) {
                                        n = n4;
                                        continue;
                                    }
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append("Illegal base64 character ");
                                    ((StringBuilder)object2).append(Integer.toString((int)object[n4 - 1], 16));
                                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                                }
                                int n7 = n6 | n << n5;
                                int n8 = n5 - 6;
                                n5 = n3;
                                n6 = n7;
                                n = n8;
                                if (n8 < 0) {
                                    n = n3 + 1;
                                    object2[n3] = (byte)(n7 >> 16);
                                    n3 = n + 1;
                                    object2[n] = (byte)(n7 >> 8);
                                    object2[n3] = (byte)n7;
                                    n6 = 0;
                                    n = 18;
                                    n5 = n3 + 1;
                                }
                                n3 = n5;
                                n5 = n;
                                n = n4;
                            } while (true);
                            if (n5 != 6) break block17;
                            object2[n3] = (byte)(n6 >> 16);
                            ++n3;
                            break block18;
                        }
                        if (n5 != 0) break block19;
                        n = n3 + 1;
                        object2[n3] = (byte)(n6 >> 16);
                        n3 = n + 1;
                        object2[n] = (byte)(n6 >> 8);
                        break block18;
                    }
                    if (n5 == 12) break block20;
                }
                while (n4 < n2) {
                    n = n4;
                    if (this.isMIME) {
                        n = n4 + 1;
                        if (arrn[object[n4]] < 0) {
                            n4 = n;
                            continue;
                        }
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Input byte array has incorrect ending byte at ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                return n3;
            }
            throw new IllegalArgumentException("Last unit does not have enough valid bits");
        }

        private int outLength(byte[] arrby, int n, int n2) {
            int n3;
            int[] arrn = this.isURL ? fromBase64URL : fromBase64;
            int n4 = 0;
            int n5 = n2 - n;
            if (n5 == 0) {
                return 0;
            }
            if (n5 < 2) {
                if (this.isMIME && arrn[0] == -1) {
                    return 0;
                }
                throw new IllegalArgumentException("Input byte[] should at least have 2 bytes for base64 bytes");
            }
            if (this.isMIME) {
                int n6 = 0;
                n3 = n;
                n = n6;
                do {
                    n6 = n5;
                    if (n3 >= n2) break;
                    n6 = n3 + 1;
                    int n7 = arrby[n3] & 255;
                    if (n7 == 61) {
                        n6 = n5 - (n2 - n6 + 1);
                        break;
                    }
                    n3 = n;
                    if (arrn[n7] == -1) {
                        n3 = n + 1;
                    }
                    n = n3;
                    n3 = n6;
                } while (true);
                n3 = n6 - n;
                n = n4;
            } else {
                n = n4;
                n3 = n5;
                if (arrby[n2 - 1] == 61) {
                    int n8;
                    n = n8 = 0 + 1;
                    n3 = n5;
                    if (arrby[n2 - 2] == 61) {
                        n = n8 + 1;
                        n3 = n5;
                    }
                }
            }
            n2 = n;
            if (n == 0) {
                n2 = n;
                if ((n3 & 3) != 0) {
                    n2 = 4 - (n3 & 3);
                }
            }
            return (n3 + 3) / 4 * 3 - n2;
        }

        public int decode(byte[] arrby, byte[] arrby2) {
            int n = this.outLength(arrby, 0, arrby.length);
            if (arrby2.length >= n) {
                return this.decode0(arrby, 0, arrby.length, arrby2);
            }
            throw new IllegalArgumentException("Output byte array is too small for decoding all input bytes");
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public ByteBuffer decode(ByteBuffer byteBuffer) {
            int n = byteBuffer.position();
            try {
                int n2;
                void var3_5;
                int n3;
                if (byteBuffer.hasArray()) {
                    byte[] arrby = byteBuffer.array();
                    n2 = byteBuffer.arrayOffset() + byteBuffer.position();
                    n3 = byteBuffer.arrayOffset() + byteBuffer.limit();
                    byteBuffer.position(byteBuffer.limit());
                } else {
                    byte[] arrby = new byte[byteBuffer.remaining()];
                    byteBuffer.get(arrby);
                    n2 = 0;
                    n3 = arrby.length;
                }
                byte[] arrby = new byte[this.outLength((byte[])var3_5, n2, n3)];
                return ByteBuffer.wrap(arrby, 0, this.decode0((byte[])var3_5, n2, n3, arrby));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                byteBuffer.position(n);
                throw illegalArgumentException;
            }
        }

        public byte[] decode(String string) {
            return this.decode(string.getBytes(StandardCharsets.ISO_8859_1));
        }

        public byte[] decode(byte[] arrby) {
            byte[] arrby2 = new byte[this.outLength(arrby, 0, arrby.length)];
            int n = this.decode0(arrby, 0, arrby.length, arrby2);
            arrby = arrby2;
            if (n != arrby2.length) {
                arrby = Arrays.copyOf(arrby2, n);
            }
            return arrby;
        }

        public InputStream wrap(InputStream inputStream) {
            Objects.requireNonNull(inputStream);
            int[] arrn = this.isURL ? fromBase64URL : fromBase64;
            return new DecInputStream(inputStream, arrn, this.isMIME);
        }
    }

    private static class EncOutputStream
    extends FilterOutputStream {
        private int b0;
        private int b1;
        private int b2;
        private final char[] base64;
        private boolean closed = false;
        private final boolean doPadding;
        private int leftover = 0;
        private final int linemax;
        private int linepos = 0;
        private final byte[] newline;

        EncOutputStream(OutputStream outputStream, char[] arrc, byte[] arrby, int n, boolean bl) {
            super(outputStream);
            this.base64 = arrc;
            this.newline = arrby;
            this.linemax = n;
            this.doPadding = bl;
        }

        private void checkNewline() throws IOException {
            if (this.linepos == this.linemax) {
                this.out.write(this.newline);
                this.linepos = 0;
            }
        }

        @Override
        public void close() throws IOException {
            if (!this.closed) {
                this.closed = true;
                int n = this.leftover;
                if (n == 1) {
                    this.checkNewline();
                    this.out.write(this.base64[this.b0 >> 2]);
                    this.out.write(this.base64[this.b0 << 4 & 63]);
                    if (this.doPadding) {
                        this.out.write(61);
                        this.out.write(61);
                    }
                } else if (n == 2) {
                    this.checkNewline();
                    this.out.write(this.base64[this.b0 >> 2]);
                    this.out.write(this.base64[this.b0 << 4 & 63 | this.b1 >> 4]);
                    this.out.write(this.base64[this.b1 << 2 & 63]);
                    if (this.doPadding) {
                        this.out.write(61);
                    }
                }
                this.leftover = 0;
                this.out.close();
            }
        }

        @Override
        public void write(int n) throws IOException {
            this.write(new byte[]{(byte)(n & 255)}, 0, 1);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            if (!this.closed) {
                if (n >= 0 && n2 >= 0 && n2 <= arrby.length - n) {
                    if (n2 == 0) {
                        return;
                    }
                    int n3 = this.leftover;
                    int n4 = n;
                    int n5 = n2;
                    if (n3 != 0) {
                        n5 = n;
                        n4 = n2;
                        if (n3 == 1) {
                            this.b1 = arrby[n] & 255;
                            n4 = n2 - 1;
                            if (n4 == 0) {
                                this.leftover = n3 + 1;
                                return;
                            }
                            n5 = n + 1;
                        }
                        this.b2 = arrby[n5] & 255;
                        n = n4 - 1;
                        this.checkNewline();
                        this.out.write(this.base64[this.b0 >> 2]);
                        this.out.write(this.base64[this.b0 << 4 & 63 | this.b1 >> 4]);
                        this.out.write(this.base64[this.b1 << 2 & 63 | this.b2 >> 6]);
                        this.out.write(this.base64[this.b2 & 63]);
                        this.linepos += 4;
                        n4 = n5 + 1;
                        n5 = n;
                    }
                    this.leftover = n5 - n * 3;
                    for (n = n5 / 3; n > 0; --n) {
                        this.checkNewline();
                        n2 = n4 + 1;
                        n5 = arrby[n4];
                        n4 = n2 + 1;
                        n2 = (n5 & 255) << 16 | (arrby[n2] & 255) << 8 | arrby[n4] & 255;
                        this.out.write(this.base64[n2 >>> 18 & 63]);
                        this.out.write(this.base64[n2 >>> 12 & 63]);
                        this.out.write(this.base64[n2 >>> 6 & 63]);
                        this.out.write(this.base64[n2 & 63]);
                        this.linepos += 4;
                        ++n4;
                    }
                    n = this.leftover;
                    if (n == 1) {
                        this.b0 = arrby[n4] & 255;
                    } else if (n == 2) {
                        n = n4 + 1;
                        this.b0 = arrby[n4] & 255;
                        this.b1 = arrby[n] & 255;
                    }
                    return;
                }
                throw new ArrayIndexOutOfBoundsException();
            }
            throw new IOException("Stream is closed");
        }
    }

    public static class Encoder {
        private static final byte[] CRLF;
        private static final int MIMELINEMAX = 76;
        static final Encoder RFC2045;
        static final Encoder RFC4648;
        static final Encoder RFC4648_URLSAFE;
        private static final char[] toBase64;
        private static final char[] toBase64URL;
        private final boolean doPadding;
        private final boolean isURL;
        private final int linemax;
        private final byte[] newline;

        static {
            toBase64 = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
            toBase64URL = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
            CRLF = new byte[]{13, 10};
            RFC4648 = new Encoder(false, null, -1, true);
            RFC4648_URLSAFE = new Encoder(true, null, -1, true);
            RFC2045 = new Encoder(false, CRLF, 76, true);
        }

        private Encoder(boolean bl, byte[] arrby, int n, boolean bl2) {
            this.isURL = bl;
            this.newline = arrby;
            this.linemax = n;
            this.doPadding = bl2;
        }

        private int encode0(byte[] arrby, int n, int n2, byte[] arrby2) {
            block7 : {
                char[] arrc;
                int n3;
                int n4;
                int n5;
                block8 : {
                    arrc = this.isURL ? toBase64URL : toBase64;
                    n3 = n;
                    n5 = (n2 - n) / 3 * 3;
                    int n6 = n + n5;
                    n = this.linemax;
                    n4 = n5;
                    if (n > 0) {
                        n4 = n5;
                        if (n5 > n / 4 * 3) {
                            n4 = n / 4 * 3;
                        }
                    }
                    n = 0;
                    while (n3 < n6) {
                        int n7;
                        int n8 = Math.min(n3 + n4, n6);
                        n5 = n;
                        for (n7 = n3; n7 < n8; ++n7) {
                            int n9 = n7 + 1;
                            int n10 = arrby[n7];
                            n7 = n9 + 1;
                            n9 = (n10 & 255) << 16 | (arrby[n9] & 255) << 8 | arrby[n7] & 255;
                            n10 = n5 + 1;
                            arrby2[n5] = (byte)arrc[n9 >>> 18 & 63];
                            n5 = n10 + 1;
                            arrby2[n10] = (byte)arrc[n9 >>> 12 & 63];
                            n10 = n5 + 1;
                            arrby2[n5] = (byte)arrc[n9 >>> 6 & 63];
                            n5 = n10 + 1;
                            arrby2[n10] = (byte)arrc[n9 & 63];
                        }
                        n7 = (n8 - n3) / 3 * 4;
                        n3 = n + n7;
                        n5 = n8;
                        n = n3;
                        if (n7 == this.linemax) {
                            n = n3;
                            if (n5 < n2) {
                                byte[] arrby3 = this.newline;
                                n8 = arrby3.length;
                                n7 = 0;
                                do {
                                    n = n3++;
                                    if (n7 >= n8) break;
                                    arrby2[n3] = arrby3[n7];
                                    ++n7;
                                } while (true);
                            }
                        }
                        n3 = n5;
                    }
                    if (n3 >= n2) break block7;
                    n5 = n3 + 1;
                    n3 = arrby[n3] & 255;
                    n4 = n + 1;
                    arrby2[n] = (byte)arrc[n3 >> 2];
                    if (n5 != n2) break block8;
                    n2 = n4 + 1;
                    arrby2[n4] = (byte)arrc[n3 << 4 & 63];
                    n = n2;
                    if (!this.doPadding) break block7;
                    n3 = n2 + 1;
                    arrby2[n2] = (byte)61;
                    n = n3 + 1;
                    arrby2[n3] = (byte)61;
                    break block7;
                }
                n2 = arrby[n5] & 255;
                n5 = n4 + 1;
                arrby2[n4] = (byte)arrc[n3 << 4 & 63 | n2 >> 4];
                n = n5 + 1;
                arrby2[n5] = (byte)arrc[n2 << 2 & 63];
                if (!this.doPadding) break block7;
                arrby2[n] = (byte)61;
                ++n;
            }
            return n;
        }

        private final int outLength(int n) {
            int n2;
            int n3;
            if (this.doPadding) {
                n = (n + 2) / 3 * 4;
            } else {
                n3 = n % 3;
                n2 = n / 3;
                n = n3 == 0 ? 0 : n3 + 1;
                n = n2 * 4 + n;
            }
            n3 = this.linemax;
            n2 = n;
            if (n3 > 0) {
                n2 = n + (n - 1) / n3 * this.newline.length;
            }
            return n2;
        }

        public int encode(byte[] arrby, byte[] arrby2) {
            int n = this.outLength(arrby.length);
            if (arrby2.length >= n) {
                return this.encode0(arrby, 0, arrby.length, arrby2);
            }
            throw new IllegalArgumentException("Output byte array is too small for encoding all input bytes");
        }

        public ByteBuffer encode(ByteBuffer arrby) {
            int n;
            byte[] arrby2 = new byte[this.outLength(arrby.remaining())];
            if (arrby.hasArray()) {
                n = this.encode0(arrby.array(), arrby.arrayOffset() + arrby.position(), arrby.arrayOffset() + arrby.limit(), arrby2);
                arrby.position(arrby.limit());
            } else {
                byte[] arrby3 = new byte[arrby.remaining()];
                arrby.get(arrby3);
                n = this.encode0(arrby3, 0, arrby3.length, arrby2);
            }
            arrby = arrby2;
            if (n != arrby2.length) {
                arrby = Arrays.copyOf(arrby2, n);
            }
            return ByteBuffer.wrap(arrby);
        }

        public byte[] encode(byte[] arrby) {
            byte[] arrby2 = new byte[this.outLength(arrby.length)];
            int n = this.encode0(arrby, 0, arrby.length, arrby2);
            if (n != arrby2.length) {
                return Arrays.copyOf(arrby2, n);
            }
            return arrby2;
        }

        public String encodeToString(byte[] arrby) {
            arrby = this.encode(arrby);
            return new String(arrby, 0, 0, arrby.length);
        }

        public Encoder withoutPadding() {
            if (!this.doPadding) {
                return this;
            }
            return new Encoder(this.isURL, this.newline, this.linemax, false);
        }

        public OutputStream wrap(OutputStream outputStream) {
            Objects.requireNonNull(outputStream);
            char[] arrc = this.isURL ? toBase64URL : toBase64;
            return new EncOutputStream(outputStream, arrc, this.newline, this.linemax, this.doPadding);
        }
    }

}

