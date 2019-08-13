/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.UCaseProps;
import android.icu.text.Replaceable;
import android.icu.text.UTF16;

class ReplaceableContextIterator
implements UCaseProps.ContextIterator {
    protected int contextLimit = 0;
    protected int contextStart = 0;
    protected int cpLimit = 0;
    protected int cpStart = 0;
    protected int dir = 0;
    protected int index = 0;
    protected int limit = 0;
    protected boolean reachedLimit = false;
    protected Replaceable rep = null;

    ReplaceableContextIterator() {
    }

    public boolean didReachLimit() {
        return this.reachedLimit;
    }

    public int getCaseMapCPStart() {
        return this.cpStart;
    }

    @Override
    public int next() {
        int n = this.dir;
        if (n > 0) {
            n = this.index;
            if (n < this.contextLimit) {
                n = this.rep.char32At(n);
                this.index += UTF16.getCharCount(n);
                return n;
            }
            this.reachedLimit = true;
        } else if (n < 0 && (n = this.index) > this.contextStart) {
            n = this.rep.char32At(n - 1);
            this.index -= UTF16.getCharCount(n);
            return n;
        }
        return -1;
    }

    public int nextCaseMapCP() {
        int n = this.cpLimit;
        if (n < this.limit) {
            this.cpStart = n;
            n = this.rep.char32At(n);
            this.cpLimit += UTF16.getCharCount(n);
            return n;
        }
        return -1;
    }

    public int replace(String string) {
        int n = string.length();
        int n2 = this.cpLimit;
        int n3 = this.cpStart;
        this.rep.replace(n3, n2, string);
        this.cpLimit += (n -= n2 - n3);
        this.limit += n;
        this.contextLimit += n;
        return n;
    }

    @Override
    public void reset(int n) {
        if (n > 0) {
            this.dir = 1;
            this.index = this.cpLimit;
        } else if (n < 0) {
            this.dir = -1;
            this.index = this.cpStart;
        } else {
            this.dir = 0;
            this.index = 0;
        }
        this.reachedLimit = false;
    }

    public void setContextLimits(int n, int n2) {
        this.contextStart = n < 0 ? 0 : (n <= this.rep.length() ? n : this.rep.length());
        n = this.contextStart;
        this.contextLimit = n2 < n ? n : (n2 <= this.rep.length() ? n2 : this.rep.length());
        this.reachedLimit = false;
    }

    public void setIndex(int n) {
        this.cpLimit = n;
        this.cpStart = n;
        this.index = 0;
        this.dir = 0;
        this.reachedLimit = false;
    }

    public void setLimit(int n) {
        this.limit = n >= 0 && n <= this.rep.length() ? n : this.rep.length();
        this.reachedLimit = false;
    }

    public void setText(Replaceable replaceable) {
        int n;
        this.rep = replaceable;
        this.contextLimit = n = replaceable.length();
        this.limit = n;
        this.contextStart = 0;
        this.index = 0;
        this.cpLimit = 0;
        this.cpStart = 0;
        this.dir = 0;
        this.reachedLimit = false;
    }
}

