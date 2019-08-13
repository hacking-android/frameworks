/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CharacterIteratorWrapper;
import android.icu.impl.ReplaceableUCharacterIterator;
import android.icu.impl.UCharArrayIterator;
import android.icu.impl.UCharacterIteratorWrapper;
import android.icu.text.Replaceable;
import android.icu.text.UForwardCharacterIterator;
import android.icu.text.UTF16;
import java.text.CharacterIterator;

public abstract class UCharacterIterator
implements Cloneable,
UForwardCharacterIterator {
    protected UCharacterIterator() {
    }

    public static final UCharacterIterator getInstance(Replaceable replaceable) {
        return new ReplaceableUCharacterIterator(replaceable);
    }

    public static final UCharacterIterator getInstance(String string) {
        return new ReplaceableUCharacterIterator(string);
    }

    public static final UCharacterIterator getInstance(StringBuffer stringBuffer) {
        return new ReplaceableUCharacterIterator(stringBuffer);
    }

    public static final UCharacterIterator getInstance(CharacterIterator characterIterator) {
        return new CharacterIteratorWrapper(characterIterator);
    }

    public static final UCharacterIterator getInstance(char[] arrc) {
        return UCharacterIterator.getInstance(arrc, 0, arrc.length);
    }

    public static final UCharacterIterator getInstance(char[] arrc, int n, int n2) {
        return new UCharArrayIterator(arrc, n, n2);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public abstract int current();

    public int currentCodePoint() {
        int n = this.current();
        if (UTF16.isLeadSurrogate((char)n)) {
            this.next();
            int n2 = this.current();
            this.previous();
            if (UTF16.isTrailSurrogate((char)n2)) {
                return Character.toCodePoint((char)n, (char)n2);
            }
        }
        return n;
    }

    public CharacterIterator getCharacterIterator() {
        return new UCharacterIteratorWrapper(this);
    }

    public abstract int getIndex();

    public abstract int getLength();

    public final int getText(char[] arrc) {
        return this.getText(arrc, 0);
    }

    public abstract int getText(char[] var1, int var2);

    public String getText() {
        char[] arrc = new char[this.getLength()];
        this.getText(arrc);
        return new String(arrc);
    }

    public int moveCodePointIndex(int n) {
        int n2;
        int n3 = n;
        if (n > 0) {
            do {
                n2 = n;
                if (n > 0) {
                    n2 = n--;
                    if (this.nextCodePoint() != -1) {
                        continue;
                    }
                }
                break;
            } while (true);
        } else {
            do {
                n2 = n3;
                if (n3 >= 0) break;
                n2 = n3++;
            } while (this.previousCodePoint() != -1);
        }
        if (n2 == 0) {
            return this.getIndex();
        }
        throw new IndexOutOfBoundsException();
    }

    public int moveIndex(int n) {
        n = Math.max(0, Math.min(this.getIndex() + n, this.getLength()));
        this.setIndex(n);
        return n;
    }

    @Override
    public abstract int next();

    @Override
    public int nextCodePoint() {
        int n = this.next();
        if (UTF16.isLeadSurrogate((char)n)) {
            int n2 = this.next();
            if (UTF16.isTrailSurrogate((char)n2)) {
                return Character.toCodePoint((char)n, (char)n2);
            }
            if (n2 != -1) {
                this.previous();
            }
        }
        return n;
    }

    public abstract int previous();

    public int previousCodePoint() {
        int n = this.previous();
        if (UTF16.isTrailSurrogate((char)n)) {
            int n2 = this.previous();
            if (UTF16.isLeadSurrogate((char)n2)) {
                return Character.toCodePoint((char)n2, (char)n);
            }
            if (n2 != -1) {
                this.next();
            }
        }
        return n;
    }

    public abstract void setIndex(int var1);

    public void setToLimit() {
        this.setIndex(this.getLength());
    }

    public void setToStart() {
        this.setIndex(0);
    }
}

