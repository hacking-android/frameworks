/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public interface CharSequence {
    public static /* synthetic */ Spliterator.OfInt lambda$chars$0(CharSequence charSequence) {
        return Spliterators.spliterator(charSequence.new 1CharIterator(), (long)charSequence.length(), 16);
    }

    public static /* synthetic */ Spliterator.OfInt lambda$codePoints$1(CharSequence charSequence) {
        return Spliterators.spliteratorUnknownSize(charSequence.new 1CodePointIterator(), 16);
    }

    public char charAt(int var1);

    default public IntStream chars() {
        return StreamSupport.intStream(new _$$Lambda$CharSequence$lS6BYp9KMNOi2HcboXLiOooqoX8(this), 16464, false);
    }

    default public IntStream codePoints() {
        return StreamSupport.intStream(new _$$Lambda$CharSequence$lnrrVTEPDeRteHnQDz8kEht4CY8(this), 16, false);
    }

    public int length();

    public CharSequence subSequence(int var1, int var2);

    public String toString();

    class 1CharIterator
    implements PrimitiveIterator.OfInt {
        int cur = 0;

        1CharIterator() {
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            while (this.cur < CharSequence.this.length()) {
                intConsumer.accept(CharSequence.this.charAt(this.cur));
                ++this.cur;
            }
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.cur < CharSequence.this.length();
            return bl;
        }

        @Override
        public int nextInt() {
            if (this.hasNext()) {
                CharSequence charSequence = CharSequence.this;
                int n = this.cur;
                this.cur = n + 1;
                return charSequence.charAt(n);
            }
            throw new NoSuchElementException();
        }
    }

    class 1CodePointIterator
    implements PrimitiveIterator.OfInt {
        int cur = 0;

        1CodePointIterator() {
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            void var1_4;
            int n3;
            block9 : {
                int n = CharSequence.this.length();
                int n2 = this.cur;
                do {
                    int n4;
                    if (n2 >= n) {
                        this.cur = n2;
                        return;
                    }
                    CharSequence charSequence = CharSequence.this;
                    n3 = n4 = n2 + 1;
                    try {
                        char c = charSequence.charAt(n2);
                        n3 = n4;
                        if (Character.isHighSurrogate(c) && n4 < n) {
                            n3 = n4;
                            char c2 = CharSequence.this.charAt(n4);
                            n3 = n4;
                            if (Character.isLowSurrogate(c2)) {
                                n3 = n2 = n4 + 1;
                                intConsumer.accept(Character.toCodePoint(c, c2));
                                continue;
                            }
                            n3 = n4;
                            intConsumer.accept(c);
                        } else {
                            n3 = n4;
                            intConsumer.accept(c);
                        }
                        n2 = n4;
                    }
                    catch (Throwable throwable) {
                        break block9;
                    }
                } while (true);
                catch (Throwable throwable) {
                    n3 = n2;
                }
            }
            this.cur = n3;
            throw var1_4;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.cur < CharSequence.this.length();
            return bl;
        }

        @Override
        public int nextInt() {
            int n = this.cur;
            int n2 = CharSequence.this.length();
            if (n < n2) {
                char c;
                CharSequence charSequence = CharSequence.this;
                this.cur = n + 1;
                char c2 = charSequence.charAt(n);
                if (Character.isHighSurrogate(c2) && (n = this.cur++) < n2 && Character.isLowSurrogate(c = CharSequence.this.charAt(n))) {
                    return Character.toCodePoint(c2, c);
                }
                return c2;
            }
            throw new NoSuchElementException();
        }
    }

}

