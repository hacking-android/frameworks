/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CoderMalfunctionError;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public abstract class CharsetDecoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_CODING = 1;
    private static final int ST_END = 2;
    private static final int ST_FLUSHED = 3;
    private static final int ST_RESET = 0;
    private static String[] stateNames = new String[]{"RESET", "CODING", "CODING_END", "FLUSHED"};
    private final float averageCharsPerByte;
    private final Charset charset;
    private CodingErrorAction malformedInputAction = CodingErrorAction.REPORT;
    private final float maxCharsPerByte;
    private String replacement;
    private int state = 0;
    private CodingErrorAction unmappableCharacterAction = CodingErrorAction.REPORT;

    protected CharsetDecoder(Charset charset, float f, float f2) {
        this(charset, f, f2, "\ufffd");
    }

    private CharsetDecoder(Charset charset, float f, float f2, String string) {
        this.charset = charset;
        if (!(f <= 0.0f)) {
            if (!(f2 <= 0.0f)) {
                if (!Charset.atBugLevel("1.4") && f > f2) {
                    throw new IllegalArgumentException("averageCharsPerByte exceeds maxCharsPerByte");
                }
                this.replacement = string;
                this.averageCharsPerByte = f;
                this.maxCharsPerByte = f2;
                return;
            }
            throw new IllegalArgumentException("Non-positive maxCharsPerByte");
        }
        throw new IllegalArgumentException("Non-positive averageCharsPerByte");
    }

    private void throwIllegalStateException(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current state = ");
        stringBuilder.append(stateNames[n]);
        stringBuilder.append(", new state = ");
        stringBuilder.append(stateNames[n2]);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public final float averageCharsPerByte() {
        return this.averageCharsPerByte;
    }

    public final Charset charset() {
        return this.charset;
    }

    public final CharBuffer decode(ByteBuffer byteBuffer) throws CharacterCodingException {
        int n = (int)((float)byteBuffer.remaining() * this.averageCharsPerByte());
        Object object = CharBuffer.allocate(n);
        if (n == 0 && byteBuffer.remaining() == 0) {
            return object;
        }
        this.reset();
        do {
            Object object2 = byteBuffer.hasRemaining() ? this.decode(byteBuffer, (CharBuffer)object, true) : CoderResult.UNDERFLOW;
            CoderResult coderResult = object2;
            if (((CoderResult)object2).isUnderflow()) {
                coderResult = this.flush((CharBuffer)object);
            }
            if (coderResult.isUnderflow()) {
                ((CharBuffer)object).flip();
                return object;
            }
            if (coderResult.isOverflow()) {
                n = n * 2 + 1;
                object2 = CharBuffer.allocate(n);
                ((CharBuffer)object).flip();
                ((CharBuffer)object2).put((CharBuffer)object);
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
    public final CoderResult decode(ByteBuffer byteBuffer, CharBuffer charBuffer, boolean bl) {
        int n = bl ? 2 : 1;
        int n2 = this.state;
        if (!(n2 == 0 || n2 == 1 || bl && n2 == 2)) {
            this.throwIllegalStateException(this.state, n);
        }
        this.state = n;
        do {
            Object object = this.decodeLoop(byteBuffer, charBuffer);
            if (((CoderResult)object).isOverflow()) {
                return object;
            }
            CoderResult coderResult = object;
            if (((CoderResult)object).isUnderflow()) {
                if (!bl) return object;
                if (!byteBuffer.hasRemaining()) return object;
                coderResult = CoderResult.malformedForLength(byteBuffer.remaining());
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
                if (charBuffer.remaining() < this.replacement.length()) {
                    return CoderResult.OVERFLOW;
                }
                charBuffer.put(this.replacement);
            }
            if (object != CodingErrorAction.IGNORE && object != CodingErrorAction.REPLACE) continue;
            byteBuffer.position(byteBuffer.position() + coderResult.length());
        } while (true);
        catch (BufferOverflowException bufferOverflowException) {
            throw new CoderMalfunctionError(bufferOverflowException);
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            throw new CoderMalfunctionError(bufferUnderflowException);
        }
    }

    protected abstract CoderResult decodeLoop(ByteBuffer var1, CharBuffer var2);

    public Charset detectedCharset() {
        throw new UnsupportedOperationException();
    }

    public final CoderResult flush(CharBuffer object) {
        int n = this.state;
        if (n == 2) {
            if (((CoderResult)(object = this.implFlush((CharBuffer)object))).isUnderflow()) {
                this.state = 3;
            }
            return object;
        }
        if (n != 3) {
            this.throwIllegalStateException(n, 3);
        }
        return CoderResult.UNDERFLOW;
    }

    protected CoderResult implFlush(CharBuffer charBuffer) {
        return CoderResult.UNDERFLOW;
    }

    protected void implOnMalformedInput(CodingErrorAction codingErrorAction) {
    }

    protected void implOnUnmappableCharacter(CodingErrorAction codingErrorAction) {
    }

    protected void implReplaceWith(String string) {
    }

    protected void implReset() {
    }

    public boolean isAutoDetecting() {
        return false;
    }

    public boolean isCharsetDetected() {
        throw new UnsupportedOperationException();
    }

    public CodingErrorAction malformedInputAction() {
        return this.malformedInputAction;
    }

    public final float maxCharsPerByte() {
        return this.maxCharsPerByte;
    }

    public final CharsetDecoder onMalformedInput(CodingErrorAction codingErrorAction) {
        if (codingErrorAction != null) {
            this.malformedInputAction = codingErrorAction;
            this.implOnMalformedInput(codingErrorAction);
            return this;
        }
        throw new IllegalArgumentException("Null action");
    }

    public final CharsetDecoder onUnmappableCharacter(CodingErrorAction codingErrorAction) {
        if (codingErrorAction != null) {
            this.unmappableCharacterAction = codingErrorAction;
            this.implOnUnmappableCharacter(codingErrorAction);
            return this;
        }
        throw new IllegalArgumentException("Null action");
    }

    public final CharsetDecoder replaceWith(String string) {
        if (string != null) {
            int n = string.length();
            if (n != 0) {
                if (!((float)n > this.maxCharsPerByte)) {
                    this.replacement = string;
                    this.implReplaceWith(this.replacement);
                    return this;
                }
                throw new IllegalArgumentException("Replacement too long");
            }
            throw new IllegalArgumentException("Empty replacement");
        }
        throw new IllegalArgumentException("Null replacement");
    }

    public final String replacement() {
        return this.replacement;
    }

    public final CharsetDecoder reset() {
        this.implReset();
        this.state = 0;
        return this;
    }

    public CodingErrorAction unmappableCharacterAction() {
        return this.unmappableCharacterAction;
    }
}

