/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import sun.nio.cs.ArrayDecoder;
import sun.nio.cs.ArrayEncoder;

final class ZipCoder {
    private Charset cs;
    private CharsetDecoder dec;
    private CharsetEncoder enc;
    private boolean isUTF8;
    private ZipCoder utf8;

    private ZipCoder(Charset charset) {
        this.cs = charset;
        this.isUTF8 = charset.name().equals(StandardCharsets.UTF_8.name());
    }

    private CharsetDecoder decoder() {
        if (this.dec == null) {
            this.dec = this.cs.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        }
        return this.dec;
    }

    private CharsetEncoder encoder() {
        if (this.enc == null) {
            this.enc = this.cs.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        }
        return this.enc;
    }

    static ZipCoder get(Charset charset) {
        return new ZipCoder(charset);
    }

    byte[] getBytes(String arrby) {
        Object object = this.encoder().reset();
        Object object2 = arrby.toCharArray();
        int n = (int)((float)((char[])object2).length * ((CharsetEncoder)object).maxBytesPerChar());
        arrby = new byte[n];
        if (n == 0) {
            return arrby;
        }
        if (this.isUTF8 && object instanceof ArrayEncoder) {
            n = ((ArrayEncoder)object).encode((char[])object2, 0, ((char[])object2).length, arrby);
            if (n != -1) {
                return Arrays.copyOf(arrby, n);
            }
            throw new IllegalArgumentException("MALFORMED");
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(arrby);
        if (((CoderResult)(object2 = ((CharsetEncoder)object).encode(CharBuffer.wrap((char[])object2), byteBuffer, true))).isUnderflow()) {
            if (((CoderResult)(object = ((CharsetEncoder)object).flush(byteBuffer))).isUnderflow()) {
                if (byteBuffer.position() == arrby.length) {
                    return arrby;
                }
                return Arrays.copyOf(arrby, byteBuffer.position());
            }
            throw new IllegalArgumentException(((CoderResult)object).toString());
        }
        throw new IllegalArgumentException(((CoderResult)object2).toString());
    }

    byte[] getBytesUTF8(String string) {
        if (this.isUTF8) {
            return this.getBytes(string);
        }
        if (this.utf8 == null) {
            this.utf8 = new ZipCoder(StandardCharsets.UTF_8);
        }
        return this.utf8.getBytes(string);
    }

    boolean isUTF8() {
        return this.isUTF8;
    }

    String toString(byte[] arrby) {
        return this.toString(arrby, arrby.length);
    }

    String toString(byte[] object, int n) {
        Object object2 = this.decoder().reset();
        int n2 = (int)((float)n * ((CharsetDecoder)object2).maxCharsPerByte());
        char[] arrc = new char[n2];
        if (n2 == 0) {
            return new String(arrc);
        }
        if (this.isUTF8 && object2 instanceof ArrayDecoder) {
            if ((n = ((ArrayDecoder)object2).decode((byte[])object, 0, n, arrc)) != -1) {
                return new String(arrc, 0, n);
            }
            throw new IllegalArgumentException("MALFORMED");
        }
        Object object3 = ByteBuffer.wrap((byte[])object, 0, n);
        object = CharBuffer.wrap(arrc);
        if (((CoderResult)(object3 = ((CharsetDecoder)object2).decode((ByteBuffer)object3, (CharBuffer)object, true))).isUnderflow()) {
            if (((CoderResult)(object2 = ((CharsetDecoder)object2).flush((CharBuffer)object))).isUnderflow()) {
                return new String(arrc, 0, ((Buffer)object).position());
            }
            throw new IllegalArgumentException(((CoderResult)object2).toString());
        }
        throw new IllegalArgumentException(((CoderResult)object3).toString());
    }

    String toStringUTF8(byte[] arrby, int n) {
        if (this.isUTF8) {
            return this.toString(arrby, n);
        }
        if (this.utf8 == null) {
            this.utf8 = new ZipCoder(StandardCharsets.UTF_8);
        }
        return this.utf8.toString(arrby, n);
    }
}

