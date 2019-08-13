/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.encoders;

import com.android.org.bouncycastle.util.encoders.Encoder;
import java.io.IOException;
import java.io.OutputStream;

public class Base64Encoder
implements Encoder {
    protected final byte[] decodingTable = new byte[128];
    protected final byte[] encodingTable = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    protected byte padding = (byte)61;

    public Base64Encoder() {
        this.initialiseDecodingTable();
    }

    private int decodeLastBlock(OutputStream outputStream, char c, char c2, char c3, char c4) throws IOException {
        byte by = this.padding;
        if (c3 == by) {
            if (c4 == by) {
                byte[] arrby = this.decodingTable;
                if (((c = arrby[c]) | (c2 = arrby[c2])) >= 0) {
                    outputStream.write(c << 2 | c2 >> 4);
                    return 1;
                }
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            throw new IOException("invalid characters encountered at end of base64 data");
        }
        if (c4 == by) {
            byte[] arrby = this.decodingTable;
            if (((c = arrby[c]) | (c2 = arrby[c2]) | (c3 = arrby[c3])) >= 0) {
                outputStream.write(c << 2 | c2 >> 4);
                outputStream.write(c2 << 4 | c3 >> 2);
                return 2;
            }
            throw new IOException("invalid characters encountered at end of base64 data");
        }
        byte[] arrby = this.decodingTable;
        if (((c = arrby[c]) | (c2 = arrby[c2]) | (c3 = arrby[c3]) | (c4 = arrby[c4])) >= 0) {
            outputStream.write(c << 2 | c2 >> 4);
            outputStream.write(c2 << 4 | c3 >> 2);
            outputStream.write(c3 << 6 | c4);
            return 3;
        }
        throw new IOException("invalid characters encountered at end of base64 data");
    }

    private boolean ignore(char c) {
        boolean bl = c == '\n' || c == '\r' || c == '\t' || c == ' ';
        return bl;
    }

    private int nextI(String string, int n, int n2) {
        while (n < n2 && this.ignore(string.charAt(n))) {
            ++n;
        }
        return n;
    }

    private int nextI(byte[] arrby, int n, int n2) {
        while (n < n2 && this.ignore((char)arrby[n])) {
            ++n;
        }
        return n;
    }

    @Override
    public int decode(String string, OutputStream outputStream) throws IOException {
        int n;
        int n2;
        int n3;
        int n4;
        for (n2 = string.length(); n2 > 0 && this.ignore(string.charAt(n2 - 1)); --n2) {
        }
        if (n2 == 0) {
            return 0;
        }
        int n5 = 0;
        for (n4 = n2; n4 > 0 && n5 != 4; --n4) {
            n = n5;
            if (!this.ignore(string.charAt(n4 - 1))) {
                n = n5 + 1;
            }
            n5 = n;
        }
        n5 = this.nextI(string, 0, n4);
        n = 0;
        while (n5 < n4) {
            n3 = this.decodingTable[string.charAt(n5)];
            int n6 = this.nextI(string, n5 + 1, n4);
            n5 = this.decodingTable[string.charAt(n6)];
            int n7 = this.nextI(string, n6 + 1, n4);
            n6 = this.decodingTable[string.charAt(n7)];
            byte by = this.decodingTable[string.charAt(n7 = this.nextI(string, n7 + 1, n4))];
            if ((n3 | n5 | n6 | by) >= 0) {
                outputStream.write(n3 << 2 | n5 >> 4);
                outputStream.write(n5 << 4 | n6 >> 2);
                outputStream.write(n6 << 6 | by);
                n += 3;
                n5 = this.nextI(string, n7 + 1, n4);
                continue;
            }
            throw new IOException("invalid characters encountered in base64 data");
        }
        n5 = this.nextI(string, n5, n2);
        n3 = this.nextI(string, n5 + 1, n2);
        n4 = this.nextI(string, n3 + 1, n2);
        n2 = this.nextI(string, n4 + 1, n2);
        return n + this.decodeLastBlock(outputStream, string.charAt(n5), string.charAt(n3), string.charAt(n4), string.charAt(n2));
    }

    @Override
    public int decode(byte[] arrby, int n, int n2, OutputStream outputStream) throws IOException {
        int n3;
        int n4;
        for (n2 = n + n2; n2 > n && this.ignore((char)arrby[n2 - 1]); --n2) {
        }
        if (n2 == 0) {
            return 0;
        }
        int n5 = 0;
        for (n3 = n2; n3 > n && n5 != 4; --n3) {
            n4 = n5;
            if (!this.ignore((char)arrby[n3 - 1])) {
                n4 = n5 + 1;
            }
            n5 = n4;
        }
        n5 = this.nextI(arrby, n, n3);
        n = 0;
        while (n5 < n3) {
            n4 = this.decodingTable[arrby[n5]];
            int n6 = this.nextI(arrby, n5 + 1, n3);
            n5 = this.decodingTable[arrby[n6]];
            n6 = this.nextI(arrby, n6 + 1, n3);
            byte by = this.decodingTable[arrby[n6]];
            byte by2 = this.decodingTable[arrby[n6 = this.nextI(arrby, n6 + 1, n3)]];
            if ((n4 | n5 | by | by2) >= 0) {
                outputStream.write(n4 << 2 | n5 >> 4);
                outputStream.write(n5 << 4 | by >> 2);
                outputStream.write(by << 6 | by2);
                n += 3;
                n5 = this.nextI(arrby, n6 + 1, n3);
                continue;
            }
            throw new IOException("invalid characters encountered in base64 data");
        }
        n3 = this.nextI(arrby, n5, n2);
        n4 = this.nextI(arrby, n3 + 1, n2);
        n5 = this.nextI(arrby, n4 + 1, n2);
        n2 = this.nextI(arrby, n5 + 1, n2);
        return n + this.decodeLastBlock(outputStream, (char)arrby[n3], (char)arrby[n4], (char)arrby[n5], (char)arrby[n2]);
    }

    @Override
    public int encode(byte[] arrby, int n, int n2, OutputStream outputStream) throws IOException {
        int n3 = n2 % 3;
        int n4 = n2 - n3;
        for (n2 = n; n2 < n + n4; n2 += 3) {
            int n5 = arrby[n2] & 255;
            int n6 = arrby[n2 + 1] & 255;
            int n7 = arrby[n2 + 2] & 255;
            outputStream.write(this.encodingTable[n5 >>> 2 & 63]);
            outputStream.write(this.encodingTable[(n5 << 4 | n6 >>> 4) & 63]);
            outputStream.write(this.encodingTable[(n6 << 2 | n7 >>> 6) & 63]);
            outputStream.write(this.encodingTable[n7 & 63]);
        }
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 == 2) {
                    n2 = arrby[n + n4] & 255;
                    n = arrby[n + n4 + 1] & 255;
                    outputStream.write(this.encodingTable[n2 >>> 2 & 63]);
                    outputStream.write(this.encodingTable[(n2 << 4 | n >>> 4) & 63]);
                    outputStream.write(this.encodingTable[n << 2 & 63]);
                    outputStream.write(this.padding);
                }
            } else {
                n = arrby[n + n4] & 255;
                outputStream.write(this.encodingTable[n >>> 2 & 63]);
                outputStream.write(this.encodingTable[n << 4 & 63]);
                outputStream.write(this.padding);
                outputStream.write(this.padding);
            }
        }
        n2 = n4 / 3;
        n = 4;
        if (n3 == 0) {
            n = 0;
        }
        return n2 * 4 + n;
    }

    protected void initialiseDecodingTable() {
        int n;
        byte[] arrby;
        for (n = 0; n < (arrby = this.decodingTable).length; ++n) {
            arrby[n] = (byte)-1;
        }
        for (n = 0; n < (arrby = this.encodingTable).length; ++n) {
            this.decodingTable[arrby[n]] = (byte)n;
        }
    }
}

