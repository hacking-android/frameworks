/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import java.util.Iterator;
import java.util.SortedSet;

public class UnicodeSetIterator {
    public static int IS_STRING = -1;
    public int codepoint;
    public int codepointEnd;
    @Deprecated
    protected int endElement;
    private int endRange = 0;
    @Deprecated
    protected int nextElement;
    private int range = 0;
    private UnicodeSet set;
    public String string;
    private Iterator<String> stringIterator = null;

    public UnicodeSetIterator() {
        this.reset(new UnicodeSet());
    }

    public UnicodeSetIterator(UnicodeSet unicodeSet) {
        this.reset(unicodeSet);
    }

    @Deprecated
    public UnicodeSet getSet() {
        return this.set;
    }

    public String getString() {
        int n = this.codepoint;
        if (n != IS_STRING) {
            return UTF16.valueOf(n);
        }
        return this.string;
    }

    @Deprecated
    protected void loadRange(int n) {
        this.nextElement = this.set.getRangeStart(n);
        this.endElement = this.set.getRangeEnd(n);
    }

    public boolean next() {
        int n = this.nextElement;
        if (n <= this.endElement) {
            this.nextElement = n + 1;
            this.codepointEnd = n;
            this.codepoint = n;
            return true;
        }
        n = this.range;
        if (n < this.endRange) {
            this.range = ++n;
            this.loadRange(n);
            n = this.nextElement;
            this.nextElement = n + 1;
            this.codepointEnd = n;
            this.codepoint = n;
            return true;
        }
        Iterator<String> iterator = this.stringIterator;
        if (iterator == null) {
            return false;
        }
        this.codepoint = IS_STRING;
        this.string = iterator.next();
        if (!this.stringIterator.hasNext()) {
            this.stringIterator = null;
        }
        return true;
    }

    public boolean nextRange() {
        int n = this.nextElement;
        int n2 = this.endElement;
        if (n <= n2) {
            this.codepointEnd = n2;
            this.codepoint = n;
            this.nextElement = n2 + 1;
            return true;
        }
        n2 = this.range;
        if (n2 < this.endRange) {
            this.range = ++n2;
            this.loadRange(n2);
            this.codepointEnd = n2 = this.endElement;
            this.codepoint = this.nextElement;
            this.nextElement = n2 + 1;
            return true;
        }
        Iterator<String> iterator = this.stringIterator;
        if (iterator == null) {
            return false;
        }
        this.codepoint = IS_STRING;
        this.string = iterator.next();
        if (!this.stringIterator.hasNext()) {
            this.stringIterator = null;
        }
        return true;
    }

    public void reset() {
        this.endRange = this.set.getRangeCount() - 1;
        this.range = 0;
        this.endElement = -1;
        this.nextElement = 0;
        if (this.endRange >= 0) {
            this.loadRange(this.range);
        }
        this.stringIterator = this.set.hasStrings() ? this.set.strings.iterator() : null;
    }

    public void reset(UnicodeSet unicodeSet) {
        this.set = unicodeSet;
        this.reset();
    }
}

