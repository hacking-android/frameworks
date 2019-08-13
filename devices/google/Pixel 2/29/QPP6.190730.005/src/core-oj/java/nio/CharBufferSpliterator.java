/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.CharBuffer;
import java.util.Spliterator;
import java.util.function.IntConsumer;

class CharBufferSpliterator
implements Spliterator.OfInt {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final CharBuffer buffer;
    private int index;
    private final int limit;

    CharBufferSpliterator(CharBuffer charBuffer) {
        this(charBuffer, charBuffer.position(), charBuffer.limit());
    }

    CharBufferSpliterator(CharBuffer charBuffer, int n, int n2) {
        this.buffer = charBuffer;
        if (n > n2) {
            n = n2;
        }
        this.index = n;
        this.limit = n2;
    }

    @Override
    public int characteristics() {
        return 16464;
    }

    @Override
    public long estimateSize() {
        return this.limit - this.index;
    }

    @Override
    public void forEachRemaining(IntConsumer intConsumer) {
        if (intConsumer != null) {
            int n;
            CharBuffer charBuffer = this.buffer;
            this.index = n = this.limit;
            for (int i = this.index; i < n; ++i) {
                intConsumer.accept(charBuffer.getUnchecked(i));
            }
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public boolean tryAdvance(IntConsumer intConsumer) {
        if (intConsumer != null) {
            int n = this.index;
            if (n >= 0 && n < this.limit) {
                CharBuffer charBuffer = this.buffer;
                this.index = n + 1;
                intConsumer.accept(charBuffer.getUnchecked(n));
                return true;
            }
            return false;
        }
        throw new NullPointerException();
    }

    @Override
    public Spliterator.OfInt trySplit() {
        Object object;
        int n = this.index;
        int n2 = this.limit + n >>> 1;
        if (n >= n2) {
            object = null;
        } else {
            object = this.buffer;
            this.index = n2;
            object = new CharBufferSpliterator((CharBuffer)object, n, n2);
        }
        return object;
    }
}

