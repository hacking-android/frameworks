/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

import dalvik.annotation.optimization.ReachabilitySensitive;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import libcore.icu.ICU;
import libcore.icu.NativeConverter;
import libcore.util.EmptyArray;

final class CharsetDecoderICU
extends CharsetDecoder {
    private static final int INPUT_OFFSET = 0;
    private static final int INVALID_BYTE_COUNT = 2;
    private static final int MAX_CHARS_PER_BYTE = 2;
    private static final int OUTPUT_OFFSET = 1;
    private byte[] allocatedInput = null;
    private char[] allocatedOutput = null;
    @ReachabilitySensitive
    private long converterHandle = 0L;
    private final int[] data = new int[3];
    private int inEnd;
    private byte[] input = null;
    private int outEnd;
    private char[] output = null;

    private CharsetDecoderICU(Charset charset, float f, long l) {
        super(charset, f, 2.0f);
        this.converterHandle = l;
    }

    private int getArray(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            this.input = byteBuffer.array();
            this.inEnd = byteBuffer.arrayOffset() + byteBuffer.limit();
            return byteBuffer.arrayOffset() + byteBuffer.position();
        }
        this.inEnd = byteBuffer.remaining();
        byte[] arrby = this.allocatedInput;
        if (arrby == null || this.inEnd > arrby.length) {
            this.allocatedInput = new byte[this.inEnd];
        }
        int n = byteBuffer.position();
        byteBuffer.get(this.allocatedInput, 0, this.inEnd);
        byteBuffer.position(n);
        this.input = this.allocatedInput;
        return 0;
    }

    private int getArray(CharBuffer arrc) {
        if (arrc.hasArray()) {
            this.output = arrc.array();
            this.outEnd = arrc.arrayOffset() + arrc.limit();
            return arrc.arrayOffset() + arrc.position();
        }
        this.outEnd = arrc.remaining();
        arrc = this.allocatedOutput;
        if (arrc == null || this.outEnd > arrc.length) {
            this.allocatedOutput = new char[this.outEnd];
        }
        this.output = this.allocatedOutput;
        return 0;
    }

    public static CharsetDecoderICU newInstance(Charset object, String string) {
        long l;
        long l2 = 0L;
        try {
            l2 = l = NativeConverter.openConverter(string);
        }
        catch (Throwable throwable) {
            if (l2 != 0L) {
                NativeConverter.closeConverter(l2);
            }
            throw throwable;
        }
        object = new CharsetDecoderICU((Charset)object, NativeConverter.getAveCharsPerByte(l), l);
        NativeConverter.registerConverter(object, l);
        CharsetDecoderICU.super.updateCallback();
        return object;
    }

    private void setPosition(ByteBuffer byteBuffer) {
        byteBuffer.position(byteBuffer.position() + this.data[0]);
        this.input = null;
    }

    private void setPosition(CharBuffer charBuffer) {
        if (charBuffer.hasArray()) {
            charBuffer.position(charBuffer.position() + this.data[1]);
        } else {
            charBuffer.put(this.output, 0, this.data[1]);
        }
        this.output = null;
    }

    private void updateCallback() {
        NativeConverter.setCallbackDecode(this.converterHandle, this);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected CoderResult decodeLoop(ByteBuffer var1_1, CharBuffer var2_2) {
        block9 : {
            if (!var1_1.hasRemaining()) {
                return CoderResult.UNDERFLOW;
            }
            this.data[0] = this.getArray(var1_1);
            this.data[1] = this.getArray(var2_2);
            var3_3 = NativeConverter.decode(this.converterHandle, this.input, this.inEnd, this.output, this.outEnd, this.data, false);
            if (!ICU.U_FAILURE(var3_3)) ** GOTO lbl26
            if (var3_3 == 15) {
                var4_4 = CoderResult.OVERFLOW;
                this.setPosition(var1_1);
                this.setPosition(var2_2);
                return var4_4;
            }
            if (var3_3 == 10) {
                return CoderResult.unmappableForLength(this.data[2]);
            }
            if (var3_3 != 12) break block9;
            var4_6 = CoderResult.malformedForLength(this.data[2]);
            this.setPosition(var1_1);
            this.setPosition(var2_2);
            return var4_6;
        }
        var4_7 = new AssertionError(var3_3);
        throw var4_7;
lbl26: // 1 sources:
        var4_8 = CoderResult.UNDERFLOW;
        this.setPosition(var1_1);
        this.setPosition(var2_2);
        return var4_8;
    }

    @Override
    protected final CoderResult implFlush(CharBuffer charBuffer) {
        block7 : {
            int n;
            block8 : {
                this.input = EmptyArray.BYTE;
                this.inEnd = 0;
                this.data[0] = 0;
                this.data[1] = this.getArray(charBuffer);
                this.data[2] = 0;
                n = NativeConverter.decode(this.converterHandle, this.input, this.inEnd, this.output, this.outEnd, this.data, true);
                if (!ICU.U_FAILURE(n)) break block7;
                if (n != 15) break block8;
                CoderResult coderResult = CoderResult.OVERFLOW;
                return coderResult;
            }
            if (n == 11) {
                if (this.data[2] <= 0) break block7;
                CoderResult coderResult = CoderResult.malformedForLength(this.data[2]);
                this.setPosition(charBuffer);
                this.implReset();
                return coderResult;
            }
        }
        try {
            CoderResult coderResult = CoderResult.UNDERFLOW;
            this.setPosition(charBuffer);
            this.implReset();
            return coderResult;
        }
        finally {
            this.setPosition(charBuffer);
            this.implReset();
        }
    }

    @Override
    protected final void implOnMalformedInput(CodingErrorAction codingErrorAction) {
        this.updateCallback();
    }

    @Override
    protected final void implOnUnmappableCharacter(CodingErrorAction codingErrorAction) {
        this.updateCallback();
    }

    @Override
    protected void implReplaceWith(String string) {
        this.updateCallback();
    }

    @Override
    protected void implReset() {
        NativeConverter.resetByteToChar(this.converterHandle);
        int[] arrn = this.data;
        arrn[0] = 0;
        arrn[1] = 0;
        arrn[2] = 0;
        this.output = null;
        this.input = null;
        this.allocatedInput = null;
        this.allocatedOutput = null;
        this.inEnd = 0;
        this.outEnd = 0;
    }
}

