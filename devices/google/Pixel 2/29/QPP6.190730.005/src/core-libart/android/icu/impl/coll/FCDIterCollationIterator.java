/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Normalizer2Impl;
import android.icu.impl.Trie2_32;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationFCD;
import android.icu.impl.coll.IterCollationIterator;
import android.icu.text.UCharacterIterator;

public final class FCDIterCollationIterator
extends IterCollationIterator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int limit;
    private final Normalizer2Impl nfcImpl;
    private StringBuilder normalized;
    private int pos;
    private StringBuilder s;
    private int start;
    private State state = State.ITER_CHECK_FWD;

    public FCDIterCollationIterator(CollationData collationData, boolean bl, UCharacterIterator uCharacterIterator, int n) {
        super(collationData, bl, uCharacterIterator);
        this.start = n;
        this.nfcImpl = collationData.nfcImpl;
    }

    private boolean nextSegment() {
        int n;
        this.pos = this.iter.getIndex();
        StringBuilder stringBuilder = this.s;
        if (stringBuilder == null) {
            this.s = new StringBuilder();
        } else {
            stringBuilder.setLength(0);
        }
        int n2 = 0;
        while ((n = this.iter.nextCodePoint()) >= 0) {
            int n3;
            block8 : {
                n3 = this.nfcImpl.getFCD16(n);
                int n4 = n3 >> 8;
                if (n4 == 0 && this.s.length() != 0) {
                    this.iter.previousCodePoint();
                    break;
                }
                this.s.appendCodePoint(n);
                if (n4 == 0 || n2 <= n4 && !CollationFCD.isFCD16OfTibetanCompositeVowel(n3)) break block8;
                do {
                    block10 : {
                        block9 : {
                            if ((n2 = this.iter.nextCodePoint()) < 0) break block9;
                            if (this.nfcImpl.getFCD16(n2) > 255) break block10;
                            this.iter.previousCodePoint();
                        }
                        this.normalize(this.s);
                        this.start = n2 = this.pos;
                        this.limit = n2 + this.s.length();
                        this.state = State.IN_NORM_ITER_AT_LIMIT;
                        this.pos = 0;
                        return true;
                    }
                    this.s.appendCodePoint(n2);
                } while (true);
            }
            n2 = n3 & 255;
            if (n2 != 0) continue;
        }
        this.limit = this.pos + this.s.length();
        this.iter.moveIndex(-this.s.length());
        this.state = State.ITER_IN_FCD_SEGMENT;
        return true;
    }

    private void normalize(CharSequence charSequence) {
        if (this.normalized == null) {
            this.normalized = new StringBuilder();
        }
        this.nfcImpl.decompose(charSequence, this.normalized);
    }

    private boolean previousSegment() {
        int n;
        this.pos = this.iter.getIndex();
        StringBuilder stringBuilder = this.s;
        if (stringBuilder == null) {
            this.s = new StringBuilder();
        } else {
            stringBuilder.setLength(0);
        }
        int n2 = 0;
        while ((n = this.iter.previousCodePoint()) >= 0) {
            int n3;
            block9 : {
                block11 : {
                    block10 : {
                        n3 = this.nfcImpl.getFCD16(n);
                        int n4 = n3 & 255;
                        if (n4 == 0 && this.s.length() != 0) {
                            this.iter.nextCodePoint();
                            break;
                        }
                        this.s.appendCodePoint(n);
                        if (n4 == 0) break block9;
                        if (n2 == 0) break block10;
                        n = n3;
                        if (n4 > n2) break block11;
                    }
                    if (!CollationFCD.isFCD16OfTibetanCompositeVowel(n3)) break block9;
                    n = n3;
                }
                while (n > 255 && (n2 = this.iter.previousCodePoint()) >= 0) {
                    n = this.nfcImpl.getFCD16(n2);
                    if (n == 0) {
                        this.iter.nextCodePoint();
                        break;
                    }
                    this.s.appendCodePoint(n2);
                }
                this.s.reverse();
                this.normalize(this.s);
                this.limit = n2 = this.pos;
                this.start = n2 - this.s.length();
                this.state = State.IN_NORM_ITER_AT_START;
                this.pos = this.normalized.length();
                return true;
            }
            if ((n2 = n3 >> 8) != 0) continue;
        }
        this.start = this.pos - this.s.length();
        this.iter.moveIndex(this.s.length());
        this.state = State.ITER_IN_FCD_SEGMENT;
        return true;
    }

    private void switchToBackward() {
        if (this.state == State.ITER_CHECK_FWD) {
            int n;
            this.pos = n = this.iter.getIndex();
            this.limit = n;
            this.state = this.pos == this.start ? State.ITER_CHECK_BWD : State.ITER_IN_FCD_SEGMENT;
        } else {
            if (this.state != State.ITER_IN_FCD_SEGMENT) {
                if (this.state == State.IN_NORM_ITER_AT_LIMIT) {
                    this.iter.moveIndex(this.start - this.limit);
                }
                this.limit = this.start;
            }
            this.state = State.ITER_CHECK_BWD;
        }
    }

    private void switchToForward() {
        if (this.state == State.ITER_CHECK_BWD) {
            int n;
            this.pos = n = this.iter.getIndex();
            this.start = n;
            this.state = this.pos == this.limit ? State.ITER_CHECK_FWD : State.ITER_IN_FCD_SEGMENT;
        } else {
            if (this.state != State.ITER_IN_FCD_SEGMENT) {
                if (this.state == State.IN_NORM_ITER_AT_START) {
                    this.iter.moveIndex(this.limit - this.start);
                }
                this.start = this.limit;
            }
            this.state = State.ITER_CHECK_FWD;
        }
    }

    @Override
    protected void backwardNumCodePoints(int n) {
        while (n > 0 && this.previousCodePoint() >= 0) {
            --n;
        }
    }

    @Override
    protected void forwardNumCodePoints(int n) {
        while (n > 0 && this.nextCodePoint() >= 0) {
            --n;
        }
    }

    @Override
    public int getOffset() {
        if (this.state.compareTo(State.ITER_CHECK_BWD) <= 0) {
            return this.iter.getIndex();
        }
        if (this.state == State.ITER_IN_FCD_SEGMENT) {
            return this.pos;
        }
        if (this.pos == 0) {
            return this.start;
        }
        return this.limit;
    }

    @Override
    protected char handleGetTrailSurrogate() {
        if (this.state.compareTo(State.ITER_IN_FCD_SEGMENT) <= 0) {
            int n = this.iter.next();
            if (FCDIterCollationIterator.isTrailSurrogate(n)) {
                if (this.state == State.ITER_IN_FCD_SEGMENT) {
                    ++this.pos;
                }
            } else if (n >= 0) {
                this.iter.previous();
            }
            return (char)n;
        }
        char c = this.normalized.charAt(this.pos);
        if (Character.isLowSurrogate(c)) {
            ++this.pos;
        }
        return c;
    }

    @Override
    protected long handleNextCE32() {
        do {
            block12 : {
                int n;
                block9 : {
                    block11 : {
                        block8 : {
                            block10 : {
                                if (this.state != State.ITER_CHECK_FWD) break block8;
                                int n2 = this.iter.next();
                                if (n2 < 0) {
                                    return -4294967104L;
                                }
                                n = n2;
                                if (!CollationFCD.hasTccc(n2)) break block9;
                                if (CollationFCD.maybeTibetanCompositeVowel(n2)) break block10;
                                n = n2;
                                if (!CollationFCD.hasLccc(this.iter.current())) break block9;
                            }
                            this.iter.previous();
                            if (this.nextSegment()) continue;
                            return 192L;
                            break block9;
                        }
                        if (this.state != State.ITER_IN_FCD_SEGMENT || this.pos == this.limit) break block11;
                        n = this.iter.next();
                        ++this.pos;
                        break block9;
                    }
                    if (this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) < 0 || this.pos == this.normalized.length()) break block12;
                    StringBuilder stringBuilder = this.normalized;
                    n = this.pos;
                    this.pos = n + 1;
                    n = stringBuilder.charAt(n);
                }
                return this.makeCodePointAndCE32Pair(n, this.trie.getFromU16SingleLead((char)n));
            }
            this.switchToForward();
        } while (true);
    }

    @Override
    public int nextCodePoint() {
        do {
            if (this.state == State.ITER_CHECK_FWD) {
                int n = this.iter.next();
                if (n < 0) {
                    return n;
                }
                if (CollationFCD.hasTccc(n) && (CollationFCD.maybeTibetanCompositeVowel(n) || CollationFCD.hasLccc(this.iter.current()))) {
                    this.iter.previous();
                    if (this.nextSegment()) continue;
                    return -1;
                }
                if (FCDIterCollationIterator.isLeadSurrogate(n)) {
                    int n2 = this.iter.next();
                    if (FCDIterCollationIterator.isTrailSurrogate(n2)) {
                        return Character.toCodePoint((char)n, (char)n2);
                    }
                    if (n2 >= 0) {
                        this.iter.previous();
                    }
                }
                return n;
            }
            if (this.state == State.ITER_IN_FCD_SEGMENT && this.pos != this.limit) {
                int n = this.iter.nextCodePoint();
                this.pos += Character.charCount(n);
                return n;
            }
            if (this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != this.normalized.length()) {
                int n = this.normalized.codePointAt(this.pos);
                this.pos += Character.charCount(n);
                return n;
            }
            this.switchToForward();
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int previousCodePoint() {
        do {
            int n;
            if (this.state == State.ITER_CHECK_BWD) {
                int n2 = this.iter.previous();
                if (n2 < 0) {
                    this.pos = 0;
                    this.start = 0;
                    this.state = State.ITER_IN_FCD_SEGMENT;
                    return -1;
                }
                if (!CollationFCD.hasLccc(n2)) return n2;
                n = -1;
                if (!CollationFCD.maybeTibetanCompositeVowel(n2)) {
                    int n3;
                    n = n3 = this.iter.previous();
                    if (!CollationFCD.hasTccc(n3)) {
                        int n4 = n;
                        if (FCDIterCollationIterator.isTrailSurrogate(n2)) {
                            n3 = n;
                            if (n < 0) {
                                n3 = this.iter.previous();
                            }
                            n4 = n3;
                            if (FCDIterCollationIterator.isLeadSurrogate(n3)) {
                                return Character.toCodePoint((char)n3, (char)n2);
                            }
                        }
                        if (n4 < 0) return n2;
                        this.iter.next();
                        return n2;
                    }
                }
                this.iter.next();
                if (n >= 0) {
                    this.iter.next();
                }
                if (this.previousSegment()) continue;
                return -1;
            }
            if (this.state == State.ITER_IN_FCD_SEGMENT && this.pos != this.start) {
                n = this.iter.previousCodePoint();
                this.pos -= Character.charCount(n);
                return n;
            }
            if (this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && (n = this.pos) != 0) {
                n = this.normalized.codePointBefore(n);
                this.pos -= Character.charCount(n);
                return n;
            }
            this.switchToBackward();
        } while (true);
    }

    @Override
    public void resetToOffset(int n) {
        super.resetToOffset(n);
        this.start = n;
        this.state = State.ITER_CHECK_FWD;
    }

    private static enum State {
        ITER_CHECK_FWD,
        ITER_CHECK_BWD,
        ITER_IN_FCD_SEGMENT,
        IN_NORM_ITER_AT_LIMIT,
        IN_NORM_ITER_AT_START;
        
    }

}

