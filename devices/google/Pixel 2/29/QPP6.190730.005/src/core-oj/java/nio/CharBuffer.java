/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.CharBufferSpliterator;
import java.nio.HeapCharBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.StringCharBuffer;
import java.nio._$$Lambda$CharBuffer$Nm0aKlKPFJdqsfIc00Gd0NEZ8I0;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public abstract class CharBuffer
extends Buffer
implements Comparable<CharBuffer>,
Appendable,
CharSequence,
Readable {
    final char[] hb;
    boolean isReadOnly;
    final int offset;

    CharBuffer(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, null, 0);
    }

    CharBuffer(int n, int n2, int n3, int n4, char[] arrc, int n5) {
        super(n, n2, n3, n4, 1);
        this.hb = arrc;
        this.offset = n5;
    }

    public static CharBuffer allocate(int n) {
        if (n >= 0) {
            return new HeapCharBuffer(n, n);
        }
        throw new IllegalArgumentException();
    }

    private static int compare(char c, char c2) {
        return Character.compare(c, c2);
    }

    private static boolean equals(char c, char c2) {
        boolean bl = c == c2;
        return bl;
    }

    public static CharBuffer wrap(CharSequence charSequence) {
        return CharBuffer.wrap(charSequence, 0, charSequence.length());
    }

    public static CharBuffer wrap(CharSequence charSequence, int n, int n2) {
        try {
            charSequence = new StringCharBuffer(charSequence, n, n2);
            return charSequence;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static CharBuffer wrap(char[] arrc) {
        return CharBuffer.wrap(arrc, 0, arrc.length);
    }

    public static CharBuffer wrap(char[] object, int n, int n2) {
        try {
            object = new HeapCharBuffer((char[])object, n, n2);
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public CharBuffer append(char c) {
        return this.put(c);
    }

    @Override
    public CharBuffer append(CharSequence charSequence) {
        if (charSequence == null) {
            return this.put("null");
        }
        return this.put(charSequence.toString());
    }

    @Override
    public CharBuffer append(CharSequence charSequence, int n, int n2) {
        block0 : {
            if (charSequence != null) break block0;
            charSequence = "null";
        }
        return this.put(charSequence.subSequence(n, n2).toString());
    }

    public final char[] array() {
        char[] arrc = this.hb;
        if (arrc != null) {
            if (!this.isReadOnly) {
                return arrc;
            }
            throw new ReadOnlyBufferException();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public final int arrayOffset() {
        if (this.hb != null) {
            if (!this.isReadOnly) {
                return this.offset;
            }
            throw new ReadOnlyBufferException();
        }
        throw new UnsupportedOperationException();
    }

    public abstract CharBuffer asReadOnlyBuffer();

    @Override
    public final char charAt(int n) {
        return this.get(this.position() + this.checkIndex(n, 1));
    }

    @Override
    public IntStream chars() {
        return StreamSupport.intStream(new _$$Lambda$CharBuffer$Nm0aKlKPFJdqsfIc00Gd0NEZ8I0(this), 16464, false);
    }

    @Override
    public Buffer clear() {
        return super.clear();
    }

    public abstract CharBuffer compact();

    @Override
    public int compareTo(CharBuffer charBuffer) {
        int n = this.position();
        int n2 = Math.min(this.remaining(), charBuffer.remaining());
        int n3 = this.position();
        int n4 = charBuffer.position();
        while (n3 < n + n2) {
            int n5 = CharBuffer.compare(this.get(n3), charBuffer.get(n4));
            if (n5 != 0) {
                return n5;
            }
            ++n3;
            ++n4;
        }
        return this.remaining() - charBuffer.remaining();
    }

    public abstract CharBuffer duplicate();

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CharBuffer)) {
            return false;
        }
        object = (CharBuffer)object;
        if (this.remaining() != ((Buffer)object).remaining()) {
            return false;
        }
        int n = this.position();
        int n2 = this.limit() - 1;
        int n3 = ((Buffer)object).limit() - 1;
        while (n2 >= n) {
            if (!CharBuffer.equals(this.get(n2), ((CharBuffer)object).get(n3))) {
                return false;
            }
            --n2;
            --n3;
        }
        return true;
    }

    @Override
    public Buffer flip() {
        return super.flip();
    }

    public abstract char get();

    public abstract char get(int var1);

    public CharBuffer get(char[] arrc) {
        return this.get(arrc, 0, arrc.length);
    }

    public CharBuffer get(char[] arrc, int n, int n2) {
        CharBuffer.checkBounds(n, n2, arrc.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                arrc[i] = this.get();
            }
            return this;
        }
        throw new BufferUnderflowException();
    }

    abstract char getUnchecked(int var1);

    @Override
    public final boolean hasArray() {
        boolean bl = this.hb != null && !this.isReadOnly;
        return bl;
    }

    public int hashCode() {
        int n = 1;
        int n2 = this.position();
        for (int i = this.limit() - 1; i >= n2; --i) {
            n = n * 31 + this.get(i);
        }
        return n;
    }

    @Override
    public abstract boolean isDirect();

    public /* synthetic */ Spliterator.OfInt lambda$chars$0$CharBuffer() {
        return new CharBufferSpliterator(this);
    }

    @Override
    public final int length() {
        return this.remaining();
    }

    @Override
    public Buffer limit(int n) {
        return super.limit(n);
    }

    @Override
    public Buffer mark() {
        return super.mark();
    }

    public abstract ByteOrder order();

    @Override
    public Buffer position(int n) {
        return super.position(n);
    }

    public abstract CharBuffer put(char var1);

    public abstract CharBuffer put(int var1, char var2);

    public final CharBuffer put(String string) {
        return this.put(string, 0, string.length());
    }

    public CharBuffer put(String string, int n, int n2) {
        CharBuffer.checkBounds(n, n2 - n, string.length());
        if (n == n2) {
            return this;
        }
        if (!this.isReadOnly()) {
            if (n2 - n <= this.remaining()) {
                while (n < n2) {
                    this.put(string.charAt(n));
                    ++n;
                }
                return this;
            }
            throw new BufferOverflowException();
        }
        throw new ReadOnlyBufferException();
    }

    public CharBuffer put(CharBuffer charBuffer) {
        if (charBuffer != this) {
            if (!this.isReadOnly()) {
                int n = charBuffer.remaining();
                if (n <= this.remaining()) {
                    for (int i = 0; i < n; ++i) {
                        this.put(charBuffer.get());
                    }
                    return this;
                }
                throw new BufferOverflowException();
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalArgumentException();
    }

    public final CharBuffer put(char[] arrc) {
        return this.put(arrc, 0, arrc.length);
    }

    public CharBuffer put(char[] arrc, int n, int n2) {
        CharBuffer.checkBounds(n, n2, arrc.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                this.put(arrc[i]);
            }
            return this;
        }
        throw new BufferOverflowException();
    }

    @Override
    public int read(CharBuffer charBuffer) throws IOException {
        int n = charBuffer.remaining();
        int n2 = this.remaining();
        if (n2 == 0) {
            return -1;
        }
        int n3 = Math.min(n2, n);
        int n4 = this.limit();
        if (n < n2) {
            this.limit(this.position() + n3);
        }
        if (n3 > 0) {
            try {
                charBuffer.put(this);
            }
            finally {
                this.limit(n4);
            }
        }
        return n3;
    }

    @Override
    public Buffer reset() {
        return super.reset();
    }

    @Override
    public Buffer rewind() {
        return super.rewind();
    }

    public abstract CharBuffer slice();

    @Override
    public abstract CharBuffer subSequence(int var1, int var2);

    @Override
    public String toString() {
        return this.toString(this.position(), this.limit());
    }

    abstract String toString(int var1, int var2);
}

