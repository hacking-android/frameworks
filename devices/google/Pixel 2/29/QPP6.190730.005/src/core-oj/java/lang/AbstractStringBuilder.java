/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.IOException;
import java.util.Arrays;
import sun.misc.FloatingDecimal;

abstract class AbstractStringBuilder
implements Appendable,
CharSequence {
    private static final int MAX_ARRAY_SIZE = 2147483639;
    int count;
    char[] value;

    AbstractStringBuilder() {
    }

    AbstractStringBuilder(int n) {
        this.value = new char[n];
    }

    private AbstractStringBuilder appendNull() {
        int n = this.count;
        this.ensureCapacityInternal(n + 4);
        char[] arrc = this.value;
        int n2 = n + 1;
        arrc[n] = (char)110;
        n = n2 + 1;
        arrc[n2] = (char)117;
        n2 = n + 1;
        arrc[n] = (char)108;
        arrc[n2] = (char)108;
        this.count = n2 + 1;
        return this;
    }

    private void ensureCapacityInternal(int n) {
        char[] arrc = this.value;
        if (n - arrc.length > 0) {
            this.value = Arrays.copyOf(arrc, this.newCapacity(n));
        }
    }

    private int hugeCapacity(int n) {
        if (Integer.MAX_VALUE - n >= 0) {
            int n2 = 2147483639;
            if (n <= 2147483639) {
                n = n2;
            }
            return n;
        }
        throw new OutOfMemoryError();
    }

    private int newCapacity(int n) {
        int n2;
        int n3 = n2 = (this.value.length << 1) + 2;
        if (n2 - n < 0) {
            n3 = n;
        }
        n = n3 > 0 && 2147483639 - n3 >= 0 ? n3 : this.hugeCapacity(n);
        return n;
    }

    private void reverseAllValidSurrogatePairs() {
        int n = 0;
        while (n < this.count - 1) {
            char c = this.value[n];
            int n2 = n;
            if (Character.isLowSurrogate(c)) {
                char c2 = this.value[n + 1];
                n2 = n;
                if (Character.isHighSurrogate(c2)) {
                    char[] arrc = this.value;
                    n2 = n + 1;
                    arrc[n] = c2;
                    arrc[n2] = c;
                }
            }
            n = n2 + 1;
        }
    }

    @Override
    public AbstractStringBuilder append(char c) {
        this.ensureCapacityInternal(this.count + 1);
        char[] arrc = this.value;
        int n = this.count;
        this.count = n + 1;
        arrc[n] = c;
        return this;
    }

    public AbstractStringBuilder append(double d) {
        FloatingDecimal.appendTo(d, (Appendable)this);
        return this;
    }

    public AbstractStringBuilder append(float f) {
        FloatingDecimal.appendTo(f, (Appendable)this);
        return this;
    }

    public AbstractStringBuilder append(int n) {
        if (n == Integer.MIN_VALUE) {
            this.append("-2147483648");
            return this;
        }
        int n2 = n < 0 ? Integer.stringSize(-n) + 1 : Integer.stringSize(n);
        n2 = this.count + n2;
        this.ensureCapacityInternal(n2);
        Integer.getChars(n, n2, this.value);
        this.count = n2;
        return this;
    }

    public AbstractStringBuilder append(long l) {
        if (l == Long.MIN_VALUE) {
            this.append("-9223372036854775808");
            return this;
        }
        int n = l < 0L ? Long.stringSize(-l) + 1 : Long.stringSize(l);
        n = this.count + n;
        this.ensureCapacityInternal(n);
        Long.getChars(l, n, this.value);
        this.count = n;
        return this;
    }

    AbstractStringBuilder append(AbstractStringBuilder abstractStringBuilder) {
        if (abstractStringBuilder == null) {
            return this.appendNull();
        }
        int n = abstractStringBuilder.length();
        this.ensureCapacityInternal(this.count + n);
        abstractStringBuilder.getChars(0, n, this.value, this.count);
        this.count += n;
        return this;
    }

    @Override
    public AbstractStringBuilder append(CharSequence charSequence) {
        if (charSequence == null) {
            return this.appendNull();
        }
        if (charSequence instanceof String) {
            return this.append((String)charSequence);
        }
        if (charSequence instanceof AbstractStringBuilder) {
            return this.append((AbstractStringBuilder)charSequence);
        }
        return this.append(charSequence, 0, charSequence.length());
    }

    @Override
    public AbstractStringBuilder append(CharSequence charSequence, int n, int n2) {
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "null";
        }
        if (n >= 0 && n <= n2 && n2 <= charSequence2.length()) {
            int n3 = n2 - n;
            this.ensureCapacityInternal(this.count + n3);
            int n4 = n;
            n = this.count;
            while (n4 < n2) {
                this.value[n] = charSequence2.charAt(n4);
                ++n4;
                ++n;
            }
            this.count += n3;
            return this;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("start ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(", end ");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append(", s.length() ");
        ((StringBuilder)charSequence).append(charSequence2.length());
        throw new IndexOutOfBoundsException(((StringBuilder)charSequence).toString());
    }

    public AbstractStringBuilder append(Object object) {
        return this.append(String.valueOf(object));
    }

    public AbstractStringBuilder append(String string) {
        if (string == null) {
            return this.appendNull();
        }
        int n = string.length();
        this.ensureCapacityInternal(this.count + n);
        string.getChars(0, n, this.value, this.count);
        this.count += n;
        return this;
    }

    public AbstractStringBuilder append(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return this.appendNull();
        }
        int n = stringBuffer.length();
        this.ensureCapacityInternal(this.count + n);
        stringBuffer.getChars(0, n, this.value, this.count);
        this.count += n;
        return this;
    }

    public AbstractStringBuilder append(boolean bl) {
        if (bl) {
            this.ensureCapacityInternal(this.count + 4);
            char[] arrc = this.value;
            int n = this.count;
            this.count = n + 1;
            arrc[n] = (char)116;
            n = this.count;
            this.count = n + 1;
            arrc[n] = (char)114;
            n = this.count;
            this.count = n + 1;
            arrc[n] = (char)117;
            n = this.count;
            this.count = n + 1;
            arrc[n] = (char)101;
        } else {
            this.ensureCapacityInternal(this.count + 5);
            char[] arrc = this.value;
            int n = this.count;
            this.count = n + 1;
            arrc[n] = (char)102;
            n = this.count;
            this.count = n + 1;
            arrc[n] = (char)97;
            n = this.count;
            this.count = n + 1;
            arrc[n] = (char)108;
            n = this.count;
            this.count = n + 1;
            arrc[n] = (char)115;
            n = this.count;
            this.count = n + 1;
            arrc[n] = (char)101;
        }
        return this;
    }

    public AbstractStringBuilder append(char[] arrc) {
        int n = arrc.length;
        this.ensureCapacityInternal(this.count + n);
        System.arraycopy((Object)arrc, 0, (Object)this.value, this.count, n);
        this.count += n;
        return this;
    }

    public AbstractStringBuilder append(char[] arrc, int n, int n2) {
        if (n2 > 0) {
            this.ensureCapacityInternal(this.count + n2);
        }
        System.arraycopy((Object)arrc, n, (Object)this.value, this.count, n2);
        this.count += n2;
        return this;
    }

    public AbstractStringBuilder appendCodePoint(int n) {
        block4 : {
            block3 : {
                int n2;
                block2 : {
                    n2 = this.count;
                    if (!Character.isBmpCodePoint(n)) break block2;
                    this.ensureCapacityInternal(n2 + 1);
                    this.value[n2] = (char)n;
                    this.count = n2 + 1;
                    break block3;
                }
                if (!Character.isValidCodePoint(n)) break block4;
                this.ensureCapacityInternal(n2 + 2);
                Character.toSurrogates(n, this.value, n2);
                this.count = n2 + 2;
            }
            return this;
        }
        throw new IllegalArgumentException();
    }

    public int capacity() {
        return this.value.length;
    }

    @Override
    public char charAt(int n) {
        if (n >= 0 && n < this.count) {
            return this.value[n];
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public int codePointAt(int n) {
        int n2;
        if (n >= 0 && n < (n2 = this.count)) {
            return Character.codePointAtImpl(this.value, n, n2);
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public int codePointBefore(int n) {
        int n2 = n - 1;
        if (n2 >= 0 && n2 < this.count) {
            return Character.codePointBeforeImpl(this.value, n, 0);
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public int codePointCount(int n, int n2) {
        if (n >= 0 && n2 <= this.count && n <= n2) {
            return Character.codePointCountImpl(this.value, n, n2 - n);
        }
        throw new IndexOutOfBoundsException();
    }

    public AbstractStringBuilder delete(int n, int n2) {
        if (n >= 0) {
            int n3 = n2;
            if (n2 > this.count) {
                n3 = this.count;
            }
            if (n <= n3) {
                n2 = n3 - n;
                if (n2 > 0) {
                    char[] arrc = this.value;
                    System.arraycopy((Object)arrc, n + n2, (Object)arrc, n, this.count - n3);
                    this.count -= n2;
                }
                return this;
            }
            throw new StringIndexOutOfBoundsException();
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public AbstractStringBuilder deleteCharAt(int n) {
        int n2;
        if (n >= 0 && n < (n2 = this.count--)) {
            char[] arrc = this.value;
            System.arraycopy((Object)arrc, n + 1, (Object)arrc, n, n2 - n - 1);
            return this;
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public void ensureCapacity(int n) {
        if (n > 0) {
            this.ensureCapacityInternal(n);
        }
    }

    public void getChars(int n, int n2, char[] arrc, int n3) {
        if (n >= 0) {
            if (n2 >= 0 && n2 <= this.count) {
                if (n <= n2) {
                    System.arraycopy((Object)this.value, n, (Object)arrc, n3, n2 - n);
                    return;
                }
                throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
            }
            throw new StringIndexOutOfBoundsException(n2);
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    final char[] getValue() {
        return this.value;
    }

    public int indexOf(String string) {
        return this.indexOf(string, 0);
    }

    public int indexOf(String string, int n) {
        return String.indexOf(this.value, 0, this.count, string, n);
    }

    public AbstractStringBuilder insert(int n, char c) {
        this.ensureCapacityInternal(this.count + 1);
        char[] arrc = this.value;
        System.arraycopy((Object)arrc, n, (Object)arrc, n + 1, this.count - n);
        this.value[n] = c;
        ++this.count;
        return this;
    }

    public AbstractStringBuilder insert(int n, double d) {
        return this.insert(n, String.valueOf(d));
    }

    public AbstractStringBuilder insert(int n, float f) {
        return this.insert(n, String.valueOf(f));
    }

    public AbstractStringBuilder insert(int n, int n2) {
        return this.insert(n, String.valueOf(n2));
    }

    public AbstractStringBuilder insert(int n, long l) {
        return this.insert(n, String.valueOf(l));
    }

    public AbstractStringBuilder insert(int n, CharSequence charSequence) {
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "null";
        }
        if (charSequence2 instanceof String) {
            return this.insert(n, (String)charSequence2);
        }
        return this.insert(n, charSequence2, 0, charSequence2.length());
    }

    public AbstractStringBuilder insert(int n, CharSequence arrc, int n2, int n3) {
        char[] arrc2 = arrc;
        if (arrc == null) {
            arrc2 = "null";
        }
        if (n >= 0 && n <= this.length()) {
            if (n2 >= 0 && n3 >= 0 && n2 <= n3 && n3 <= arrc2.length()) {
                int n4 = n3 - n2;
                this.ensureCapacityInternal(this.count + n4);
                arrc = this.value;
                System.arraycopy((Object)arrc, n, (Object)arrc, n + n4, this.count - n);
                while (n2 < n3) {
                    this.value[n] = arrc2.charAt(n2);
                    ++n2;
                    ++n;
                }
                this.count += n4;
                return this;
            }
            arrc = new StringBuilder();
            arrc.append("start ");
            arrc.append(n2);
            arrc.append(", end ");
            arrc.append(n3);
            arrc.append(", s.length() ");
            arrc.append(arrc2.length());
            throw new IndexOutOfBoundsException(arrc.toString());
        }
        arrc = new StringBuilder();
        arrc.append("dstOffset ");
        arrc.append(n);
        throw new IndexOutOfBoundsException(arrc.toString());
    }

    public AbstractStringBuilder insert(int n, Object object) {
        return this.insert(n, String.valueOf(object));
    }

    public AbstractStringBuilder insert(int n, String arrc) {
        if (n >= 0 && n <= this.length()) {
            char[] arrc2 = arrc;
            if (arrc == null) {
                arrc2 = "null";
            }
            int n2 = arrc2.length();
            this.ensureCapacityInternal(this.count + n2);
            arrc = this.value;
            System.arraycopy((Object)arrc, n, (Object)arrc, n + n2, this.count - n);
            arrc2.getChars(this.value, n);
            this.count += n2;
            return this;
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public AbstractStringBuilder insert(int n, boolean bl) {
        return this.insert(n, String.valueOf(bl));
    }

    public AbstractStringBuilder insert(int n, char[] arrc) {
        if (n >= 0 && n <= this.length()) {
            int n2 = arrc.length;
            this.ensureCapacityInternal(this.count + n2);
            char[] arrc2 = this.value;
            System.arraycopy((Object)arrc2, n, (Object)arrc2, n + n2, this.count - n);
            System.arraycopy((Object)arrc, 0, (Object)this.value, n, n2);
            this.count += n2;
            return this;
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public AbstractStringBuilder insert(int n, char[] arrc, int n2, int n3) {
        if (n >= 0 && n <= this.length()) {
            if (n2 >= 0 && n3 >= 0 && n2 <= arrc.length - n3) {
                this.ensureCapacityInternal(this.count + n3);
                char[] arrc2 = this.value;
                System.arraycopy((Object)arrc2, n, (Object)arrc2, n + n3, this.count - n);
                System.arraycopy((Object)arrc, n2, (Object)this.value, n, n3);
                this.count += n3;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("offset ");
            stringBuilder.append(n2);
            stringBuilder.append(", len ");
            stringBuilder.append(n3);
            stringBuilder.append(", str.length ");
            stringBuilder.append(arrc.length);
            throw new StringIndexOutOfBoundsException(stringBuilder.toString());
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public int lastIndexOf(String string) {
        return this.lastIndexOf(string, this.count);
    }

    public int lastIndexOf(String string, int n) {
        return String.lastIndexOf(this.value, 0, this.count, string, n);
    }

    @Override
    public int length() {
        return this.count;
    }

    public int offsetByCodePoints(int n, int n2) {
        int n3;
        if (n >= 0 && n <= (n3 = this.count)) {
            return Character.offsetByCodePointsImpl(this.value, 0, n3, n, n2);
        }
        throw new IndexOutOfBoundsException();
    }

    public AbstractStringBuilder replace(int n, int n2, String string) {
        if (n >= 0) {
            int n3 = this.count;
            if (n <= n3) {
                if (n <= n2) {
                    int n4 = n2;
                    if (n2 > n3) {
                        n4 = this.count;
                    }
                    n2 = string.length();
                    n3 = this.count + n2 - (n4 - n);
                    this.ensureCapacityInternal(n3);
                    char[] arrc = this.value;
                    System.arraycopy((Object)arrc, n4, (Object)arrc, n + n2, this.count - n4);
                    string.getChars(this.value, n);
                    this.count = n3;
                    return this;
                }
                throw new StringIndexOutOfBoundsException("start > end");
            }
            throw new StringIndexOutOfBoundsException("start > length()");
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public AbstractStringBuilder reverse() {
        boolean bl = false;
        int n = this.count - 1;
        for (int i = n - 1 >> 1; i >= 0; --i) {
            char c;
            int n2 = n - i;
            char[] arrc = this.value;
            char c2 = arrc[i];
            arrc[i] = c = arrc[n2];
            arrc[n2] = c2;
            if (!Character.isSurrogate(c2) && !Character.isSurrogate(c)) continue;
            bl = true;
        }
        if (bl) {
            this.reverseAllValidSurrogatePairs();
        }
        return this;
    }

    public void setCharAt(int n, char c) {
        if (n >= 0 && n < this.count) {
            this.value[n] = c;
            return;
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public void setLength(int n) {
        if (n >= 0) {
            this.ensureCapacityInternal(n);
            int n2 = this.count;
            if (n2 < n) {
                Arrays.fill(this.value, n2, n, '\u0000');
            }
            this.count = n;
            return;
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return this.substring(n, n2);
    }

    public String substring(int n) {
        return this.substring(n, this.count);
    }

    public String substring(int n, int n2) {
        if (n >= 0) {
            if (n2 <= this.count) {
                if (n <= n2) {
                    return new String(this.value, n, n2 - n);
                }
                throw new StringIndexOutOfBoundsException(n2 - n);
            }
            throw new StringIndexOutOfBoundsException(n2);
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    @Override
    public abstract String toString();

    public void trimToSize() {
        int n = this.count;
        char[] arrc = this.value;
        if (n < arrc.length) {
            this.value = Arrays.copyOf(arrc, n);
        }
    }
}

