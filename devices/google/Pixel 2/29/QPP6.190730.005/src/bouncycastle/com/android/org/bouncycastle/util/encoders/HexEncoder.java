/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.encoders;

import com.android.org.bouncycastle.util.encoders.Encoder;
import java.io.IOException;
import java.io.OutputStream;

public class HexEncoder
implements Encoder {
    protected final byte[] decodingTable = new byte[128];
    protected final byte[] encodingTable = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

    public HexEncoder() {
        this.initialiseDecodingTable();
    }

    private static boolean ignore(char c) {
        boolean bl = c == '\n' || c == '\r' || c == '\t' || c == ' ';
        return bl;
    }

    @Override
    public int decode(String string, OutputStream outputStream) throws IOException {
        int n;
        int n2 = 0;
        for (n = string.length(); n > 0 && HexEncoder.ignore(string.charAt(n - 1)); --n) {
        }
        for (int i = 0; i < n; ++i) {
            while (i < n && HexEncoder.ignore(string.charAt(i))) {
                ++i;
            }
            byte[] arrby = this.decodingTable;
            int n3 = i + 1;
            byte by = arrby[string.charAt(i)];
            for (i = n3; i < n && HexEncoder.ignore(string.charAt(i)); ++i) {
            }
            n3 = this.decodingTable[string.charAt(i)];
            if ((by | n3) >= 0) {
                outputStream.write(by << 4 | n3);
                ++n2;
                continue;
            }
            throw new IOException("invalid characters encountered in Hex string");
        }
        return n2;
    }

    @Override
    public int decode(byte[] arrby, int n, int n2, OutputStream outputStream) throws IOException {
        int n3 = 0;
        for (n2 = n + n2; n2 > n && HexEncoder.ignore((char)arrby[n2 - 1]); --n2) {
        }
        while (n < n2) {
            while (n < n2 && HexEncoder.ignore((char)arrby[n])) {
                ++n;
            }
            byte[] arrby2 = this.decodingTable;
            int n4 = n + 1;
            byte by = arrby2[arrby[n]];
            for (n = n4; n < n2 && HexEncoder.ignore((char)arrby[n]); ++n) {
            }
            n4 = this.decodingTable[arrby[n]];
            if ((by | n4) >= 0) {
                outputStream.write(by << 4 | n4);
                ++n3;
                ++n;
                continue;
            }
            throw new IOException("invalid characters encountered in Hex data");
        }
        return n3;
    }

    @Override
    public int encode(byte[] arrby, int n, int n2, OutputStream outputStream) throws IOException {
        for (int i = n; i < n + n2; ++i) {
            int n3 = arrby[i] & 255;
            outputStream.write(this.encodingTable[n3 >>> 4]);
            outputStream.write(this.encodingTable[n3 & 15]);
        }
        return n2 * 2;
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
        arrby = this.decodingTable;
        arrby[65] = arrby[97];
        arrby[66] = arrby[98];
        arrby[67] = arrby[99];
        arrby[68] = arrby[100];
        arrby[69] = arrby[101];
        arrby[70] = arrby[102];
    }
}

