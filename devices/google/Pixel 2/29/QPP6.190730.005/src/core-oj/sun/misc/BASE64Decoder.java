/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import sun.misc.CEFormatException;
import sun.misc.CEStreamExhausted;
import sun.misc.CharacterDecoder;

public class BASE64Decoder
extends CharacterDecoder {
    private static final char[] pem_array;
    private static final byte[] pem_convert_array;
    byte[] decode_buffer = new byte[4];

    static {
        char[] arrc;
        int n;
        pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
        pem_convert_array = new byte[256];
        for (n = 0; n < 255; ++n) {
            BASE64Decoder.pem_convert_array[n] = (byte)-1;
        }
        for (n = 0; n < (arrc = pem_array).length; ++n) {
            BASE64Decoder.pem_convert_array[arrc[n]] = (byte)n;
        }
    }

    @Override
    protected int bytesPerAtom() {
        return 4;
    }

    @Override
    protected int bytesPerLine() {
        return 72;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected void decodeAtom(PushbackInputStream var1_1, OutputStream var2_2, int var3_3) throws IOException {
        var4_4 = -1;
        var5_5 = -1;
        var6_6 = -1;
        var7_7 = -1;
        if (var3_3 < 2) throw new CEFormatException("BASE64Decoder: Not enough bytes for an atom.");
        do {
            if ((var8_8 = var1_1.read()) == -1) throw new CEStreamExhausted();
        } while (var8_8 == 10 || var8_8 == 13);
        var9_9 = this.decode_buffer;
        var9_9[0] = (byte)var8_8;
        if (this.readFully((InputStream)var1_1, var9_9, 1, var3_3 - 1) == -1) throw new CEStreamExhausted();
        var8_8 = var3_3;
        if (var3_3 > 3) {
            var8_8 = var3_3;
            if (this.decode_buffer[3] == 61) {
                var8_8 = 3;
            }
        }
        var10_10 = var8_8;
        if (var8_8 > 2) {
            var10_10 = var8_8;
            if (this.decode_buffer[2] == 61) {
                var10_10 = 2;
            }
        }
        var8_8 = var6_6;
        var3_3 = var7_7;
        if (var10_10 == 2) ** GOTO lbl33
        var3_3 = var7_7;
        if (var10_10 == 3) ** GOTO lbl32
        if (var10_10 != 4) {
            var8_8 = var6_6;
            var3_3 = var7_7;
        } else {
            var3_3 = BASE64Decoder.pem_convert_array[this.decode_buffer[3] & 255];
lbl32: // 2 sources:
            var8_8 = BASE64Decoder.pem_convert_array[this.decode_buffer[2] & 255];
lbl33: // 2 sources:
            var9_9 = BASE64Decoder.pem_convert_array;
            var1_1 = this.decode_buffer;
            var5_5 = var9_9[var1_1[1] & 255];
            var4_4 = var9_9[var1_1[0] & 255];
        }
        if (var10_10 == 2) {
            var2_2.write((byte)(var4_4 << 2 & 252 | var5_5 >>> 4 & 3));
            return;
        }
        if (var10_10 == 3) {
            var2_2.write((byte)(var4_4 << 2 & 252 | var5_5 >>> 4 & 3));
            var2_2.write((byte)(var5_5 << 4 & 240 | var8_8 >>> 2 & 15));
            return;
        }
        if (var10_10 != 4) {
            return;
        }
        var2_2.write((byte)(var4_4 << 2 & 252 | var5_5 >>> 4 & 3));
        var2_2.write((byte)(var5_5 << 4 & 240 | var8_8 >>> 2 & 15));
        var2_2.write((byte)(var8_8 << 6 & 192 | var3_3 & 63));
    }
}

