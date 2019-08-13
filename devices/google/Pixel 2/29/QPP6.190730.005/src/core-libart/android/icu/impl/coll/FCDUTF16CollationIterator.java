/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Normalizer2Impl;
import android.icu.impl.Trie2_32;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationFCD;
import android.icu.impl.coll.CollationIterator;
import android.icu.impl.coll.UTF16CollationIterator;

public final class FCDUTF16CollationIterator
extends UTF16CollationIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int rawStart = 0;
    private int checkDir;
    private final Normalizer2Impl nfcImpl;
    private StringBuilder normalized;
    private int rawLimit;
    private CharSequence rawSeq;
    private int segmentLimit;
    private int segmentStart;

    public FCDUTF16CollationIterator(CollationData collationData) {
        super(collationData);
        this.nfcImpl = collationData.nfcImpl;
    }

    public FCDUTF16CollationIterator(CollationData collationData, boolean bl, CharSequence charSequence, int n) {
        super(collationData, bl, charSequence, n);
        this.rawSeq = charSequence;
        this.segmentStart = n;
        this.rawLimit = charSequence.length();
        this.nfcImpl = collationData.nfcImpl;
        this.checkDir = 1;
    }

    private void nextSegment() {
        block5 : {
            int n;
            int n2 = this.pos;
            int n3 = 0;
            do {
                int n4;
                block6 : {
                    block7 : {
                        int n5 = Character.codePointAt(this.seq, n2);
                        n = n2 + Character.charCount(n5);
                        n4 = this.nfcImpl.getFCD16(n5);
                        n5 = n4 >> 8;
                        if (n5 == 0 && n2 != this.pos) {
                            this.segmentLimit = n2;
                            this.limit = n2;
                            break block5;
                        }
                        if (n5 == 0) break block6;
                        n2 = n;
                        if (n3 > n5) break block7;
                        if (!CollationFCD.isFCD16OfTibetanCompositeVowel(n4)) break block6;
                        n2 = n;
                    }
                    while ((n3 = n2) != this.rawLimit) {
                        n = Character.codePointAt(this.seq, n3);
                        n2 = n3 + Character.charCount(n);
                        if (this.nfcImpl.getFCD16(n) > 255) continue;
                    }
                    this.normalize(this.pos, n3);
                    this.pos = this.start;
                    break block5;
                }
                n3 = n4 & 255;
                if (n == this.rawLimit || n3 == 0) break;
                n2 = n;
            } while (true);
            this.segmentLimit = n;
            this.limit = n;
        }
        this.checkDir = 0;
    }

    private void normalize(int n, int n2) {
        if (this.normalized == null) {
            this.normalized = new StringBuilder();
        }
        this.nfcImpl.decompose(this.rawSeq, n, n2, this.normalized, n2 - n);
        this.segmentStart = n;
        this.segmentLimit = n2;
        this.seq = this.normalized;
        this.start = 0;
        this.limit = this.start + this.normalized.length();
    }

    private void previousSegment() {
        block5 : {
            int n;
            int n2 = this.pos;
            int n3 = 0;
            do {
                int n4;
                block6 : {
                    int n5;
                    block8 : {
                        block7 : {
                            n5 = Character.codePointBefore(this.seq, n2);
                            n = n2 - Character.charCount(n5);
                            n4 = this.nfcImpl.getFCD16(n5);
                            int n6 = n4 & 255;
                            if (n6 == 0 && n2 != this.pos) {
                                this.segmentStart = n2;
                                this.start = n2;
                                break block5;
                            }
                            if (n6 == 0) break block6;
                            if (n3 == 0) break block7;
                            n2 = n;
                            n5 = n4;
                            if (n6 > n3) break block8;
                        }
                        if (!CollationFCD.isFCD16OfTibetanCompositeVowel(n4)) break block6;
                        n5 = n4;
                        n2 = n;
                    }
                    do {
                        n3 = n2;
                        if (n5 <= 255 || n3 == 0) break;
                        n = Character.codePointBefore(this.seq, n3);
                        n2 = n3 - Character.charCount(n);
                        n5 = n = this.nfcImpl.getFCD16(n);
                    } while (n != 0);
                    this.normalize(n3, this.pos);
                    this.pos = this.limit;
                    break block5;
                }
                n3 = n4 >> 8;
                if (n == 0 || n3 == 0) break;
                n2 = n;
            } while (true);
            this.segmentStart = n;
            this.start = n;
        }
        this.checkDir = 0;
    }

    private void switchToBackward() {
        if (this.checkDir > 0) {
            int n;
            this.segmentLimit = n = this.pos;
            this.limit = n;
            if (this.pos == this.segmentStart) {
                this.start = 0;
                this.checkDir = -1;
            } else {
                this.checkDir = 0;
            }
        } else {
            CharSequence charSequence = this.seq;
            CharSequence charSequence2 = this.rawSeq;
            if (charSequence != charSequence2) {
                int n;
                this.seq = charSequence2;
                this.segmentLimit = n = this.segmentStart;
                this.limit = n;
                this.pos = n;
            }
            this.start = 0;
            this.checkDir = -1;
        }
    }

    private void switchToForward() {
        if (this.checkDir < 0) {
            int n;
            this.segmentStart = n = this.pos;
            this.start = n;
            if (this.pos == this.segmentLimit) {
                this.limit = this.rawLimit;
                this.checkDir = 1;
            } else {
                this.checkDir = 0;
            }
        } else {
            CharSequence charSequence = this.seq;
            CharSequence charSequence2 = this.rawSeq;
            if (charSequence != charSequence2) {
                int n;
                this.seq = charSequence2;
                this.segmentStart = n = this.segmentLimit;
                this.start = n;
                this.pos = n;
            }
            this.limit = this.rawLimit;
            this.checkDir = 1;
        }
    }

    @Override
    protected void backwardNumCodePoints(int n) {
        while (n > 0 && this.previousCodePoint() >= 0) {
            --n;
        }
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof CollationIterator;
        boolean bl2 = false;
        boolean bl3 = false;
        if (bl && ((CollationIterator)this).equals(object) && object instanceof FCDUTF16CollationIterator) {
            int n;
            object = (FCDUTF16CollationIterator)object;
            int n2 = this.checkDir;
            if (n2 != ((FCDUTF16CollationIterator)object).checkDir) {
                return false;
            }
            if (n2 == 0 && (n2 = this.seq == this.rawSeq ? 1 : 0) != (n = ((FCDUTF16CollationIterator)object).seq == ((FCDUTF16CollationIterator)object).rawSeq ? 1 : 0)) {
                return false;
            }
            if (this.checkDir == 0 && this.seq != this.rawSeq) {
                bl2 = bl3;
                if (this.segmentStart - 0 == ((FCDUTF16CollationIterator)object).segmentStart - 0) {
                    bl2 = bl3;
                    if (this.pos - this.start == ((FCDUTF16CollationIterator)object).pos - ((FCDUTF16CollationIterator)object).start) {
                        bl2 = true;
                    }
                }
                return bl2;
            }
            if (this.pos - 0 == ((FCDUTF16CollationIterator)object).pos - 0) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @Override
    protected void forwardNumCodePoints(int n) {
        while (n > 0 && this.nextCodePoint() >= 0) {
            --n;
        }
    }

    @Override
    public int getOffset() {
        if (this.checkDir == 0 && this.seq != this.rawSeq) {
            if (this.pos == this.start) {
                return this.segmentStart + 0;
            }
            return this.segmentLimit + 0;
        }
        return this.pos + 0;
    }

    @Override
    protected long handleNextCE32() {
        do {
            block11 : {
                int n;
                block9 : {
                    int n2;
                    block8 : {
                        CharSequence charSequence;
                        block10 : {
                            if ((n2 = this.checkDir) <= 0) break block8;
                            if (this.pos == this.limit) {
                                return -4294967104L;
                            }
                            charSequence = this.seq;
                            n2 = this.pos;
                            this.pos = n2 + 1;
                            n = n2 = (int)charSequence.charAt(n2);
                            if (!CollationFCD.hasTccc(n2)) break block9;
                            if (CollationFCD.maybeTibetanCompositeVowel(n2)) break block10;
                            n = n2;
                            if (this.pos == this.limit) break block9;
                            n = n2;
                            if (!CollationFCD.hasLccc(this.seq.charAt(this.pos))) break block9;
                        }
                        --this.pos;
                        this.nextSegment();
                        charSequence = this.seq;
                        n2 = this.pos;
                        this.pos = n2 + 1;
                        n = n2 = (int)charSequence.charAt(n2);
                        break block9;
                    }
                    if (n2 != 0 || this.pos == this.limit) break block11;
                    CharSequence charSequence = this.seq;
                    n2 = this.pos;
                    this.pos = n2 + 1;
                    n = n2 = (int)charSequence.charAt(n2);
                }
                return this.makeCodePointAndCE32Pair(n, this.trie.getFromU16SingleLead((char)n));
            }
            this.switchToForward();
        } while (true);
    }

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    public int nextCodePoint() {
        do {
            block12 : {
                char c;
                char c2;
                block10 : {
                    char c3;
                    block9 : {
                        CharSequence charSequence;
                        block11 : {
                            if ((c3 = this.checkDir) <= '\u0000') break block9;
                            if (this.pos == this.limit) {
                                return -1;
                            }
                            charSequence = this.seq;
                            c3 = this.pos;
                            this.pos = c3 + 1;
                            c = c3 = (char)charSequence.charAt(c3);
                            if (!CollationFCD.hasTccc(c3)) break block10;
                            if (CollationFCD.maybeTibetanCompositeVowel(c3)) break block11;
                            c = c3;
                            if (this.pos == this.limit) break block10;
                            c = c3;
                            if (!CollationFCD.hasLccc(this.seq.charAt(this.pos))) break block10;
                        }
                        --this.pos;
                        this.nextSegment();
                        charSequence = this.seq;
                        c3 = this.pos;
                        this.pos = c3 + 1;
                        c = c3 = charSequence.charAt(c3);
                        break block10;
                    }
                    if (c3 != '\u0000' || this.pos == this.limit) break block12;
                    CharSequence charSequence = this.seq;
                    c3 = this.pos;
                    this.pos = c3 + 1;
                    c = c3 = (char)charSequence.charAt(c3);
                }
                if (Character.isHighSurrogate(c) && this.pos != this.limit && Character.isLowSurrogate(c2 = this.seq.charAt(this.pos))) {
                    ++this.pos;
                    return Character.toCodePoint(c, c2);
                }
                return c;
            }
            this.switchToForward();
        } while (true);
    }

    @Override
    public int previousCodePoint() {
        do {
            block12 : {
                char c;
                char c2;
                block10 : {
                    char c3;
                    block9 : {
                        CharSequence charSequence;
                        block11 : {
                            if ((c3 = this.checkDir) >= '\u0000') break block9;
                            if (this.pos == this.start) {
                                return -1;
                            }
                            charSequence = this.seq;
                            c3 = this.pos - 1;
                            this.pos = c3;
                            c = c3 = (char)charSequence.charAt(c3);
                            if (!CollationFCD.hasLccc(c3)) break block10;
                            if (CollationFCD.maybeTibetanCompositeVowel(c3)) break block11;
                            c = c3;
                            if (this.pos == this.start) break block10;
                            c = c3;
                            if (!CollationFCD.hasTccc(this.seq.charAt(this.pos - 1))) break block10;
                        }
                        ++this.pos;
                        this.previousSegment();
                        charSequence = this.seq;
                        c3 = this.pos - 1;
                        this.pos = c3;
                        c = c3 = (char)charSequence.charAt(c3);
                        break block10;
                    }
                    if (c3 != '\u0000' || this.pos == this.start) break block12;
                    CharSequence charSequence = this.seq;
                    c3 = this.pos - 1;
                    this.pos = c3;
                    c = c3 = (char)charSequence.charAt(c3);
                }
                if (Character.isLowSurrogate(c) && this.pos != this.start && Character.isHighSurrogate(c2 = this.seq.charAt(this.pos - 1))) {
                    --this.pos;
                    return Character.toCodePoint(c2, c);
                }
                return c;
            }
            this.switchToBackward();
        } while (true);
    }

    @Override
    public void resetToOffset(int n) {
        this.reset();
        this.seq = this.rawSeq;
        this.pos = n += 0;
        this.segmentStart = n;
        this.start = n;
        this.limit = this.rawLimit;
        this.checkDir = 1;
    }

    @Override
    public void setText(boolean bl, CharSequence charSequence, int n) {
        super.setText(bl, charSequence, n);
        this.rawSeq = charSequence;
        this.segmentStart = n;
        this.limit = n = charSequence.length();
        this.rawLimit = n;
        this.checkDir = 1;
    }
}

