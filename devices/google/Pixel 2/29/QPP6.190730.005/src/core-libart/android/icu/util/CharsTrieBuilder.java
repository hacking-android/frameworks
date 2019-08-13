/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.CharsTrie;
import android.icu.util.StringTrieBuilder;
import java.nio.CharBuffer;

public final class CharsTrieBuilder
extends StringTrieBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private char[] chars;
    private int charsLength;
    private final char[] intUnits = new char[3];

    private void buildChars(StringTrieBuilder.Option option) {
        if (this.chars == null) {
            this.chars = new char[1024];
        }
        this.buildImpl(option);
    }

    private void ensureCapacity(int n) {
        char[] arrc = this.chars;
        if (n > arrc.length) {
            int n2;
            int n3 = arrc.length;
            do {
                n3 = n2 = n3 * 2;
            } while (n2 <= n);
            char[] arrc2 = new char[n2];
            arrc = this.chars;
            n3 = arrc.length;
            n = this.charsLength;
            System.arraycopy(arrc, n3 - n, arrc2, arrc2.length - n, n);
            this.chars = arrc2;
        }
    }

    private int write(char[] arrc, int n) {
        int n2 = this.charsLength + n;
        this.ensureCapacity(n2);
        this.charsLength = n2;
        char[] arrc2 = this.chars;
        System.arraycopy(arrc, 0, arrc2, arrc2.length - this.charsLength, n);
        return this.charsLength;
    }

    public CharsTrieBuilder add(CharSequence charSequence, int n) {
        this.addImpl(charSequence, n);
        return this;
    }

    public CharsTrie build(StringTrieBuilder.Option option) {
        return new CharsTrie(this.buildCharSequence(option), 0);
    }

    public CharSequence buildCharSequence(StringTrieBuilder.Option arrc) {
        this.buildChars((StringTrieBuilder.Option)arrc);
        arrc = this.chars;
        int n = arrc.length;
        int n2 = this.charsLength;
        return CharBuffer.wrap(arrc, n - n2, n2);
    }

    public CharsTrieBuilder clear() {
        this.clearImpl();
        this.chars = null;
        this.charsLength = 0;
        return this;
    }

    @Deprecated
    @Override
    protected int getMaxBranchLinearSubNodeLength() {
        return 5;
    }

    @Deprecated
    @Override
    protected int getMaxLinearMatchLength() {
        return 16;
    }

    @Deprecated
    @Override
    protected int getMinLinearMatch() {
        return 48;
    }

    @Deprecated
    @Override
    protected boolean matchNodesCanHaveValues() {
        return true;
    }

    @Deprecated
    @Override
    protected int write(int n) {
        int n2 = this.charsLength + 1;
        this.ensureCapacity(n2);
        this.charsLength = n2;
        char[] arrc = this.chars;
        int n3 = arrc.length;
        n2 = this.charsLength;
        arrc[n3 - n2] = (char)n;
        return n2;
    }

    @Deprecated
    @Override
    protected int write(int n, int n2) {
        int n3 = this.charsLength + n2;
        this.ensureCapacity(n3);
        this.charsLength = n3;
        int n4 = this.chars.length - this.charsLength;
        n3 = n2;
        n2 = n4;
        while (n3 > 0) {
            this.chars[n2] = this.strings.charAt(n);
            --n3;
            ++n2;
            ++n;
        }
        return this.charsLength;
    }

    @Deprecated
    @Override
    protected int writeDeltaTo(int n) {
        char[] arrc;
        int n2 = this.charsLength - n;
        if (n2 <= 64511) {
            return this.write(n2);
        }
        if (n2 <= 67043327) {
            this.intUnits[0] = (char)((n2 >> 16) + 64512);
            n = 1;
        } else {
            arrc = this.intUnits;
            arrc[0] = (char)65535;
            arrc[1] = (char)(n2 >> 16);
            n = 2;
        }
        arrc = this.intUnits;
        arrc[n] = (char)n2;
        return this.write(arrc, n + 1);
    }

    @Deprecated
    @Override
    protected int writeValueAndFinal(int n, boolean bl) {
        char[] arrc;
        int n2 = 32768;
        if (n >= 0 && n <= 16383) {
            if (!bl) {
                n2 = 0;
            }
            return this.write(n2 | n);
        }
        if (n >= 0 && n <= 1073676287) {
            arrc = this.intUnits;
            arrc[0] = (char)((n >> 16) + 16384);
            arrc[1] = (char)n;
            n = 2;
        } else {
            arrc = this.intUnits;
            arrc[0] = (char)32767;
            arrc[1] = (char)(n >> 16);
            arrc[2] = (char)n;
            n = 3;
        }
        arrc = this.intUnits;
        char c = arrc[0];
        if (!bl) {
            n2 = 0;
        }
        arrc[0] = (char)(n2 | c);
        return this.write(this.intUnits, n);
    }

    @Deprecated
    @Override
    protected int writeValueAndType(boolean bl, int n, int n2) {
        char[] arrc;
        if (!bl) {
            return this.write(n2);
        }
        if (n >= 0 && n <= 16646143) {
            if (n <= 255) {
                this.intUnits[0] = (char)(n + 1 << 6);
                n = 1;
            } else {
                arrc = this.intUnits;
                arrc[0] = (char)((32704 & n >> 10) + 16448);
                arrc[1] = (char)n;
                n = 2;
            }
        } else {
            arrc = this.intUnits;
            arrc[0] = (char)32704;
            arrc[1] = (char)(n >> 16);
            arrc[2] = (char)n;
            n = 3;
        }
        arrc = this.intUnits;
        arrc[0] = (char)(arrc[0] | (char)n2);
        return this.write(arrc, n);
    }
}

