/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

import java.lang.ref.WeakReference;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderMalfunctionError;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

public abstract class CharsetEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_CODING = 1;
    private static final int ST_END = 2;
    private static final int ST_FLUSHED = 3;
    private static final int ST_RESET = 0;
    private static String[] stateNames = new String[]{"RESET", "CODING", "CODING_END", "FLUSHED"};
    private final float averageBytesPerChar;
    private WeakReference<CharsetDecoder> cachedDecoder = null;
    private final Charset charset;
    private CodingErrorAction malformedInputAction = CodingErrorAction.REPORT;
    private final float maxBytesPerChar;
    private byte[] replacement;
    private int state = 0;
    private CodingErrorAction unmappableCharacterAction = CodingErrorAction.REPORT;

    protected CharsetEncoder(Charset charset, float f, float f2) {
        this(charset, f, f2, new byte[]{63});
    }

    protected CharsetEncoder(Charset charset, float f, float f2, byte[] arrby) {
        this(charset, f, f2, arrby, false);
    }

    CharsetEncoder(Charset charset, float f, float f2, byte[] arrby, boolean bl) {
        this.charset = charset;
        if (!(f <= 0.0f)) {
            if (!(f2 <= 0.0f)) {
                if (!Charset.atBugLevel("1.4") && f > f2) {
                    throw new IllegalArgumentException("averageBytesPerChar exceeds maxBytesPerChar");
                }
                this.replacement = arrby;
                this.averageBytesPerChar = f;
                this.maxBytesPerChar = f2;
                if (!bl) {
                    this.replaceWith(arrby);
                }
                return;
            }
            throw new IllegalArgumentException("Non-positive maxBytesPerChar");
        }
        throw new IllegalArgumentException("Non-positive averageBytesPerChar");
    }

    private boolean canEncode(CharBuffer charBuffer) {
        int n = this.state;
        if (n == 3) {
            this.reset();
        } else if (n != 0) {
            this.throwIllegalStateException(n, 1);
        }
        if (!charBuffer.hasRemaining()) {
            return true;
        }
        CodingErrorAction codingErrorAction = this.malformedInputAction();
        CodingErrorAction codingErrorAction2 = this.unmappableCharacterAction();
        try {
            this.onMalformedInput(CodingErrorAction.REPORT);
            this.onUnmappableCharacter(CodingErrorAction.REPORT);
            this.encode(charBuffer);
            return true;
        }
        catch (CharacterCodingException characterCodingException) {
            return false;
        }
        finally {
            this.onMalformedInput(codingErrorAction);
            this.onUnmappableCharacter(codingErrorAction2);
            this.reset();
        }
    }

    private void throwIllegalStateException(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current state = ");
        stringBuilder.append(stateNames[n]);
        stringBuilder.append(", new state = ");
        stringBuilder.append(stateNames[n2]);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public final float averageBytesPerChar() {
        return this.averageBytesPerChar;
    }

    public boolean canEncode(char c) {
        CharBuffer charBuffer = CharBuffer.allocate(1);
        charBuffer.put(c);
        charBuffer.flip();
        return this.canEncode(charBuffer);
    }

    public boolean canEncode(CharSequence charSequence) {
        charSequence = charSequence instanceof CharBuffer ? ((CharBuffer)charSequence).duplicate() : CharBuffer.wrap(charSequence);
        return this.canEncode((CharBuffer)charSequence);
    }

    public final Charset charset() {
        return this.charset;
    }

    public final ByteBuffer encode(CharBuffer charBuffer) throws CharacterCodingException {
        int n = (int)((float)charBuffer.remaining() * this.averageBytesPerChar());
        Object object = ByteBuffer.allocate(n);
        if (n == 0 && charBuffer.remaining() == 0) {
            return object;
        }
        this.reset();
        do {
            Object object2 = charBuffer.hasRemaining() ? this.encode(charBuffer, (ByteBuffer)object, true) : CoderResult.UNDERFLOW;
            CoderResult coderResult = object2;
            if (((CoderResult)object2).isUnderflow()) {
                coderResult = this.flush((ByteBuffer)object);
            }
            if (coderResult.isUnderflow()) {
                ((ByteBuffer)object).flip();
                return object;
            }
            if (coderResult.isOverflow()) {
                n = n * 2 + 1;
                object2 = ByteBuffer.allocate(n);
                ((ByteBuffer)object).flip();
                ((ByteBuffer)object2).put((ByteBuffer)object);
                object = object2;
                continue;
            }
            coderResult.throwException();
        } while (true);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final CoderResult encode(CharBuffer charBuffer, ByteBuffer byteBuffer, boolean bl) {
        int n = bl ? 2 : 1;
        int n2 = this.state;
        if (!(n2 == 0 || n2 == 1 || bl && n2 == 2)) {
            this.throwIllegalStateException(this.state, n);
        }
        this.state = n;
        do {
            Object object = this.encodeLoop(charBuffer, byteBuffer);
            if (((CoderResult)object).isOverflow()) {
                return object;
            }
            CoderResult coderResult = object;
            if (((CoderResult)object).isUnderflow()) {
                if (!bl) return object;
                if (!charBuffer.hasRemaining()) return object;
                coderResult = CoderResult.malformedForLength(charBuffer.remaining());
            }
            object = null;
            if (coderResult.isMalformed()) {
                object = this.malformedInputAction;
            } else if (coderResult.isUnmappable()) {
                object = this.unmappableCharacterAction;
            }
            if (object == CodingErrorAction.REPORT) {
                return coderResult;
            }
            if (object == CodingErrorAction.REPLACE) {
                byte[] arrby;
                n = byteBuffer.remaining();
                if (n < (arrby = this.replacement).length) {
                    return CoderResult.OVERFLOW;
                }
                byteBuffer.put(arrby);
            }
            if (object != CodingErrorAction.IGNORE && object != CodingErrorAction.REPLACE) continue;
            charBuffer.position(charBuffer.position() + coderResult.length());
        } while (true);
        catch (BufferOverflowException bufferOverflowException) {
            throw new CoderMalfunctionError(bufferOverflowException);
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            throw new CoderMalfunctionError(bufferUnderflowException);
        }
    }

    protected abstract CoderResult encodeLoop(CharBuffer var1, ByteBuffer var2);

    public final CoderResult flush(ByteBuffer object) {
        int n = this.state;
        if (n == 2) {
            if (((CoderResult)(object = this.implFlush((ByteBuffer)object))).isUnderflow()) {
                this.state = 3;
            }
            return object;
        }
        if (n != 3) {
            this.throwIllegalStateException(n, 3);
        }
        return CoderResult.UNDERFLOW;
    }

    protected CoderResult implFlush(ByteBuffer byteBuffer) {
        return CoderResult.UNDERFLOW;
    }

    protected void implOnMalformedInput(CodingErrorAction codingErrorAction) {
    }

    protected void implOnUnmappableCharacter(CodingErrorAction codingErrorAction) {
    }

    protected void implReplaceWith(byte[] arrby) {
    }

    protected void implReset() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean isLegalReplacement(byte[] var1_1) {
        var2_2 = this.cachedDecoder;
        if (var2_2 == null) ** GOTO lbl-1000
        var3_3 = (CharsetDecoder)var2_2.get();
        var2_2 = var3_3;
        if (var3_3 != null) {
            var2_2.reset();
        } else lbl-1000: // 2 sources:
        {
            var2_2 = this.charset().newDecoder();
            var2_2.onMalformedInput(CodingErrorAction.REPORT);
            var2_2.onUnmappableCharacter(CodingErrorAction.REPORT);
            this.cachedDecoder = new WeakReference<Object>(var2_2);
        }
        var1_1 = ByteBuffer.wrap((byte[])var1_1);
        return true ^ var2_2.decode((ByteBuffer)var1_1, CharBuffer.allocate((int)((float)var1_1.remaining() * var2_2.maxCharsPerByte())), true).isError();
    }

    public CodingErrorAction malformedInputAction() {
        return this.malformedInputAction;
    }

    public final float maxBytesPerChar() {
        return this.maxBytesPerChar;
    }

    public final CharsetEncoder onMalformedInput(CodingErrorAction codingErrorAction) {
        if (codingErrorAction != null) {
            this.malformedInputAction = codingErrorAction;
            this.implOnMalformedInput(codingErrorAction);
            return this;
        }
        throw new IllegalArgumentException("Null action");
    }

    public final CharsetEncoder onUnmappableCharacter(CodingErrorAction codingErrorAction) {
        if (codingErrorAction != null) {
            this.unmappableCharacterAction = codingErrorAction;
            this.implOnUnmappableCharacter(codingErrorAction);
            return this;
        }
        throw new IllegalArgumentException("Null action");
    }

    public final CharsetEncoder replaceWith(byte[] arrby) {
        if (arrby != null) {
            int n = arrby.length;
            if (n != 0) {
                if (!((float)n > this.maxBytesPerChar)) {
                    if (this.isLegalReplacement(arrby)) {
                        this.replacement = Arrays.copyOf(arrby, arrby.length);
                        this.implReplaceWith(this.replacement);
                        return this;
                    }
                    throw new IllegalArgumentException("Illegal replacement");
                }
                throw new IllegalArgumentException("Replacement too long");
            }
            throw new IllegalArgumentException("Empty replacement");
        }
        throw new IllegalArgumentException("Null replacement");
    }

    public final byte[] replacement() {
        byte[] arrby = this.replacement;
        return Arrays.copyOf(arrby, arrby.length);
    }

    public final CharsetEncoder reset() {
        this.implReset();
        this.state = 0;
        return this;
    }

    public CodingErrorAction unmappableCharacterAction() {
        return this.unmappableCharacterAction;
    }
}

