/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.IOException;
import java.io.OutputStream;
import sun.misc.CharacterEncoder;

public class BASE64Encoder
extends CharacterEncoder {
    private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    @Override
    protected int bytesPerAtom() {
        return 3;
    }

    @Override
    protected int bytesPerLine() {
        return 57;
    }

    @Override
    protected void encodeAtom(OutputStream outputStream, byte[] arrby, int n, int n2) throws IOException {
        if (n2 == 1) {
            n = arrby[n];
            outputStream.write(pem_array[n >>> 2 & 63]);
            outputStream.write(pem_array[(n << 4 & 48) + (0 >>> 4 & 15)]);
            outputStream.write(61);
            outputStream.write(61);
        } else if (n2 == 2) {
            n2 = arrby[n];
            n = arrby[n + 1];
            outputStream.write(pem_array[n2 >>> 2 & 63]);
            outputStream.write(pem_array[(n2 << 4 & 48) + (n >>> 4 & 15)]);
            outputStream.write(pem_array[(n << 2 & 60) + (0 >>> 6 & 3)]);
            outputStream.write(61);
        } else {
            n2 = arrby[n];
            byte by = arrby[n + 1];
            n = arrby[n + 2];
            outputStream.write(pem_array[n2 >>> 2 & 63]);
            outputStream.write(pem_array[(n2 << 4 & 48) + (by >>> 4 & 15)]);
            outputStream.write(pem_array[(by << 2 & 60) + (n >>> 6 & 3)]);
            outputStream.write(pem_array[n & 63]);
        }
    }
}

