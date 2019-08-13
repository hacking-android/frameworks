/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

import dalvik.annotation.optimization.ReachabilitySensitive;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.HashMap;
import java.util.Map;
import libcore.icu.ICU;
import libcore.icu.NativeConverter;
import libcore.util.EmptyArray;

final class CharsetEncoderICU
extends CharsetEncoder {
    private static final Map<String, byte[]> DEFAULT_REPLACEMENTS = new HashMap<String, byte[]>();
    private static final int INPUT_OFFSET = 0;
    private static final int INVALID_CHAR_COUNT = 2;
    private static final int OUTPUT_OFFSET = 1;
    private char[] allocatedInput = null;
    private byte[] allocatedOutput = null;
    @ReachabilitySensitive
    private final long converterHandle;
    private int[] data = new int[3];
    private int inEnd;
    private char[] input = null;
    private int outEnd;
    private byte[] output = null;

    static {
        byte[] arrby = new byte[]{(byte)63};
        DEFAULT_REPLACEMENTS.put("UTF-8", arrby);
        DEFAULT_REPLACEMENTS.put("ISO-8859-1", arrby);
        DEFAULT_REPLACEMENTS.put("US-ASCII", arrby);
    }

    private CharsetEncoderICU(Charset charset, float f, float f2, byte[] arrby, long l) {
        super(charset, f, f2, arrby, true);
        this.converterHandle = l;
    }

    private int getArray(ByteBuffer arrby) {
        if (arrby.hasArray()) {
            this.output = arrby.array();
            this.outEnd = arrby.arrayOffset() + arrby.limit();
            return arrby.arrayOffset() + arrby.position();
        }
        this.outEnd = arrby.remaining();
        arrby = this.allocatedOutput;
        if (arrby == null || this.outEnd > arrby.length) {
            this.allocatedOutput = new byte[this.outEnd];
        }
        this.output = this.allocatedOutput;
        return 0;
    }

    private int getArray(CharBuffer charBuffer) {
        if (charBuffer.hasArray()) {
            this.input = charBuffer.array();
            this.inEnd = charBuffer.arrayOffset() + charBuffer.limit();
            return charBuffer.arrayOffset() + charBuffer.position();
        }
        this.inEnd = charBuffer.remaining();
        char[] arrc = this.allocatedInput;
        if (arrc == null || this.inEnd > arrc.length) {
            this.allocatedInput = new char[this.inEnd];
        }
        int n = charBuffer.position();
        charBuffer.get(this.allocatedInput, 0, this.inEnd);
        charBuffer.position(n);
        this.input = this.allocatedInput;
        return 0;
    }

    private static byte[] makeReplacement(String arrby, long l) {
        if ((arrby = DEFAULT_REPLACEMENTS.get(arrby)) != null) {
            return (byte[])arrby.clone();
        }
        return NativeConverter.getSubstitutionBytes(l);
    }

    public static CharsetEncoderICU newInstance(Charset object, String string) {
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
        object = new CharsetEncoderICU((Charset)object, NativeConverter.getAveBytesPerChar(l), NativeConverter.getMaxBytesPerChar(l), CharsetEncoderICU.makeReplacement(string, l), l);
        NativeConverter.registerConverter(object, l);
        CharsetEncoderICU.super.updateCallback();
        return object;
    }

    private void setPosition(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            byteBuffer.position(this.data[1] - byteBuffer.arrayOffset());
        } else {
            byteBuffer.put(this.output, 0, this.data[1]);
        }
        this.output = null;
    }

    private void setPosition(CharBuffer charBuffer) {
        int n;
        int n2 = charBuffer.position();
        int[] arrn = this.data;
        n2 = n = n2 + arrn[0] - arrn[2];
        if (n < 0) {
            n2 = 0;
        }
        charBuffer.position(n2);
        this.input = null;
    }

    private void updateCallback() {
        NativeConverter.setCallbackEncode(this.converterHandle, this);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected CoderResult encodeLoop(CharBuffer var1_1, ByteBuffer var2_2) {
        block9 : {
            if (!var1_1.hasRemaining()) {
                return CoderResult.UNDERFLOW;
            }
            this.data[0] = this.getArray(var1_1);
            this.data[1] = this.getArray(var2_2);
            var3_3 = this.data;
            var3_3[2] = 0;
            var4_10 = NativeConverter.encode(this.converterHandle, this.input, this.inEnd, this.output, this.outEnd, var3_3, false);
            if (!ICU.U_FAILURE(var4_10)) ** GOTO lbl28
            if (var4_10 == 15) {
                var3_4 = CoderResult.OVERFLOW;
                this.setPosition(var1_1);
                this.setPosition(var2_2);
                return var3_4;
            }
            if (var4_10 == 10) {
                return CoderResult.unmappableForLength(this.data[2]);
            }
            if (var4_10 != 12) break block9;
            var3_6 = CoderResult.malformedForLength(this.data[2]);
            this.setPosition(var1_1);
            this.setPosition(var2_2);
            return var3_6;
        }
        var3_7 = new AssertionError(var4_10);
        throw var3_7;
lbl28: // 1 sources:
        var3_8 = CoderResult.UNDERFLOW;
        this.setPosition(var1_1);
        this.setPosition(var2_2);
        return var3_8;
    }

    @Override
    protected CoderResult implFlush(ByteBuffer byteBuffer) {
        block7 : {
            int n;
            block8 : {
                this.input = EmptyArray.CHAR;
                this.inEnd = 0;
                this.data[0] = 0;
                this.data[1] = this.getArray(byteBuffer);
                this.data[2] = 0;
                n = NativeConverter.encode(this.converterHandle, this.input, this.inEnd, this.output, this.outEnd, this.data, true);
                if (!ICU.U_FAILURE(n)) break block7;
                if (n != 15) break block8;
                CoderResult coderResult = CoderResult.OVERFLOW;
                return coderResult;
            }
            if (n == 11) {
                if (this.data[2] <= 0) break block7;
                CoderResult coderResult = CoderResult.malformedForLength(this.data[2]);
                this.setPosition(byteBuffer);
                this.implReset();
                return coderResult;
            }
        }
        try {
            CoderResult coderResult = CoderResult.UNDERFLOW;
            this.setPosition(byteBuffer);
            this.implReset();
            return coderResult;
        }
        finally {
            this.setPosition(byteBuffer);
            this.implReset();
        }
    }

    @Override
    protected void implOnMalformedInput(CodingErrorAction codingErrorAction) {
        this.updateCallback();
    }

    @Override
    protected void implOnUnmappableCharacter(CodingErrorAction codingErrorAction) {
        this.updateCallback();
    }

    @Override
    protected void implReplaceWith(byte[] arrby) {
        this.updateCallback();
    }

    @Override
    protected void implReset() {
        NativeConverter.resetCharToByte(this.converterHandle);
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

