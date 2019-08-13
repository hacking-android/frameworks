/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class CodePointMap
implements Iterable<Range> {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    protected CodePointMap() {
    }

    public abstract int get(int var1);

    public boolean getRange(int n, RangeOption rangeOption, int n2, ValueFilter valueFilter, Range range) {
        if (!this.getRange(n, valueFilter, range)) {
            return false;
        }
        if (rangeOption == RangeOption.NORMAL) {
            return true;
        }
        int n3 = rangeOption == RangeOption.FIXED_ALL_SURROGATES ? 57343 : 56319;
        int n4 = range.end;
        if (n4 >= 55295 && n <= n3) {
            if (range.value == n2) {
                if (n4 >= n3) {
                    return true;
                }
            } else {
                if (n <= 55295) {
                    range.end = 55295;
                    return true;
                }
                range.value = n2;
                if (n4 > n3) {
                    range.end = n3;
                    return true;
                }
            }
            if (this.getRange(n3 + 1, valueFilter, range) && range.value == n2) {
                range.start = n;
                return true;
            }
            range.start = n;
            range.end = n3;
            range.value = n2;
            return true;
        }
        return true;
    }

    public abstract boolean getRange(int var1, ValueFilter var2, Range var3);

    @Override
    public Iterator<Range> iterator() {
        return new RangeIterator();
    }

    public StringIterator stringIterator(CharSequence charSequence, int n) {
        return new StringIterator(charSequence, n);
    }

    public static final class Range {
        private int end = -1;
        private int start = -1;
        private int value = 0;

        public int getEnd() {
            return this.end;
        }

        public int getStart() {
            return this.start;
        }

        public int getValue() {
            return this.value;
        }

        public void set(int n, int n2, int n3) {
            this.start = n;
            this.end = n2;
            this.value = n3;
        }
    }

    private final class RangeIterator
    implements Iterator<Range> {
        private Range range = new Range();

        private RangeIterator() {
        }

        @Override
        public boolean hasNext() {
            boolean bl = -1 <= this.range.end && this.range.end < 1114111;
            return bl;
        }

        @Override
        public Range next() {
            if (CodePointMap.this.getRange(this.range.end + 1, null, this.range)) {
                return this.range;
            }
            throw new NoSuchElementException();
        }

        @Override
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static enum RangeOption {
        NORMAL,
        FIXED_LEAD_SURROGATES,
        FIXED_ALL_SURROGATES;
        
    }

    public class StringIterator {
        @Deprecated
        protected int c;
        @Deprecated
        protected CharSequence s;
        @Deprecated
        protected int sIndex;
        @Deprecated
        protected int value;

        @Deprecated
        protected StringIterator(CharSequence charSequence, int n) {
            this.s = charSequence;
            this.sIndex = n;
            this.c = -1;
            this.value = 0;
        }

        public final int getCodePoint() {
            return this.c;
        }

        public final int getIndex() {
            return this.sIndex;
        }

        public final int getValue() {
            return this.value;
        }

        public boolean next() {
            if (this.sIndex >= this.s.length()) {
                return false;
            }
            this.c = Character.codePointAt(this.s, this.sIndex);
            this.sIndex += Character.charCount(this.c);
            this.value = CodePointMap.this.get(this.c);
            return true;
        }

        public boolean previous() {
            int n = this.sIndex;
            if (n <= 0) {
                return false;
            }
            this.c = Character.codePointBefore(this.s, n);
            this.sIndex -= Character.charCount(this.c);
            this.value = CodePointMap.this.get(this.c);
            return true;
        }

        public void reset(CharSequence charSequence, int n) {
            this.s = charSequence;
            this.sIndex = n;
            this.c = -1;
            this.value = 0;
        }
    }

    public static interface ValueFilter {
        public int apply(int var1);
    }

}

