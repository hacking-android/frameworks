/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.text.UTF16;
import android.icu.util.BytesTrie;
import android.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public final class CharsTrie
implements Cloneable,
Iterable<Entry> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int kMaxBranchLinearSubNodeLength = 5;
    static final int kMaxLinearMatchLength = 16;
    static final int kMaxOneUnitDelta = 64511;
    static final int kMaxOneUnitNodeValue = 255;
    static final int kMaxOneUnitValue = 16383;
    static final int kMaxTwoUnitDelta = 67043327;
    static final int kMaxTwoUnitNodeValue = 16646143;
    static final int kMaxTwoUnitValue = 1073676287;
    static final int kMinLinearMatch = 48;
    static final int kMinTwoUnitDeltaLead = 64512;
    static final int kMinTwoUnitNodeValueLead = 16448;
    static final int kMinTwoUnitValueLead = 16384;
    static final int kMinValueLead = 64;
    static final int kNodeTypeMask = 63;
    static final int kThreeUnitDeltaLead = 65535;
    static final int kThreeUnitNodeValueLead = 32704;
    static final int kThreeUnitValueLead = 32767;
    static final int kValueIsFinal = 32768;
    private static BytesTrie.Result[] valueResults_ = new BytesTrie.Result[]{BytesTrie.Result.INTERMEDIATE_VALUE, BytesTrie.Result.FINAL_VALUE};
    private CharSequence chars_;
    private int pos_;
    private int remainingMatchLength_;
    private int root_;

    public CharsTrie(CharSequence charSequence, int n) {
        this.chars_ = charSequence;
        this.root_ = n;
        this.pos_ = n;
        this.remainingMatchLength_ = -1;
    }

    private static void append(Appendable appendable, int n) {
        char c = (char)n;
        try {
            appendable.append(c);
            return;
        }
        catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    private BytesTrie.Result branchNext(int n, int n2, int n3) {
        Object object;
        int n4 = n;
        int n5 = n2;
        if (n2 == 0) {
            n5 = this.chars_.charAt(n);
            n4 = n + 1;
        }
        n2 = n5 + 1;
        n = n4;
        do {
            n4 = n;
            n5 = n2;
            if (n2 <= 5) break;
            object = this.chars_;
            n4 = n + 1;
            if (n3 < object.charAt(n)) {
                n2 >>= 1;
                n = CharsTrie.jumpByDelta(this.chars_, n4);
                continue;
            }
            n2 -= n2 >> 1;
            n = CharsTrie.skipDelta(this.chars_, n4);
        } while (true);
        do {
            object = this.chars_;
            n = n4 + 1;
            if (n3 == object.charAt(n4)) {
                n3 = this.chars_.charAt(n);
                if ((32768 & n3) != 0) {
                    object = BytesTrie.Result.FINAL_VALUE;
                } else {
                    n2 = n + 1;
                    if (n3 < 16384) {
                        n = n3;
                    } else if (n3 < 32767) {
                        n = n3 - 16384 << 16 | this.chars_.charAt(n2);
                        ++n2;
                    } else {
                        n = this.chars_.charAt(n2) << 16 | this.chars_.charAt(n2 + 1);
                        n2 += 2;
                    }
                    n = n2 + n;
                    n2 = this.chars_.charAt(n);
                    object = n2 >= 64 ? valueResults_[n2 >> 15] : BytesTrie.Result.NO_VALUE;
                }
                this.pos_ = n;
                return object;
            }
            n4 = CharsTrie.skipValue(this.chars_, n);
        } while (--n5 > 1);
        object = this.chars_;
        n = n4 + 1;
        if (n3 == object.charAt(n4)) {
            this.pos_ = n;
            object = (n = (int)this.chars_.charAt(n)) >= 64 ? valueResults_[n >> 15] : BytesTrie.Result.NO_VALUE;
            return object;
        }
        this.stop();
        return BytesTrie.Result.NO_MATCH;
    }

    private static long findUniqueValue(CharSequence charSequence, int n, long l) {
        int n2 = n + 1;
        int n3 = charSequence.charAt(n);
        n = n2;
        do {
            long l2;
            int n4;
            if (n3 < 48) {
                n4 = n;
                n2 = n3;
                if (n3 == 0) {
                    n2 = charSequence.charAt(n);
                    n4 = n + 1;
                }
                if ((l = CharsTrie.findUniqueValueFromBranch(charSequence, n4, n2 + 1, l)) == 0L) {
                    return 0L;
                }
                n = (int)(l >>> 33);
                n3 = charSequence.charAt(n);
                ++n;
                continue;
            }
            if (n3 < 64) {
                n3 = charSequence.charAt(n += n3 - 48 + 1);
                ++n;
                continue;
            }
            n2 = (32768 & n3) != 0 ? 1 : 0;
            n4 = n2 != 0 ? CharsTrie.readValue(charSequence, n, n3 & 32767) : CharsTrie.readNodeValue(charSequence, n, n3);
            if (l != 0L) {
                l2 = l;
                if (n4 != (int)(l >> 1)) {
                    return 0L;
                }
            } else {
                l2 = (long)n4 << 1 | 1L;
            }
            if (n2 != 0) {
                return l2;
            }
            n = CharsTrie.skipNodeValue(n, n3);
            n3 &= 63;
            l = l2;
        } while (true);
    }

    private static long findUniqueValueFromBranch(CharSequence charSequence, int n, int n2, long l) {
        long l2;
        int n3;
        int n4;
        do {
            n4 = n++;
            n3 = n2;
            l2 = l;
            if (n2 <= 5) break;
            l = CharsTrie.findUniqueValueFromBranch(charSequence, CharsTrie.jumpByDelta(charSequence, n), n2 >> 1, l);
            if (l == 0L) {
                return 0L;
            }
            n2 -= n2 >> 1;
            n = CharsTrie.skipDelta(charSequence, n);
        } while (true);
        do {
            n = n4 + 1;
            n2 = n + 1;
            n4 = charSequence.charAt(n);
            n = (32768 & n4) != 0 ? 1 : 0;
            int n5 = CharsTrie.readValue(charSequence, n2, n4 &= 32767);
            n4 = CharsTrie.skipValue(n2, n4);
            if (n != 0) {
                if (l2 != 0L) {
                    l = l2;
                    if (n5 != (int)(l2 >> 1)) {
                        return 0L;
                    }
                } else {
                    l = (long)n5 << 1 | 1L;
                }
            } else {
                l = l2 = CharsTrie.findUniqueValue(charSequence, n4 + n5, l2);
                if (l2 == 0L) {
                    return 0L;
                }
            }
            if (--n3 <= 1) {
                return (long)(n4 + 1) << 33 | 0x1FFFFFFFFL & l;
            }
            l2 = l;
        } while (true);
    }

    private static void getNextBranchChars(CharSequence charSequence, int n, int n2, Appendable appendable) {
        int n3;
        int n4;
        do {
            n3 = n++;
            n4 = n2;
            if (n2 <= 5) break;
            CharsTrie.getNextBranchChars(charSequence, CharsTrie.jumpByDelta(charSequence, n), n2 >> 1, appendable);
            n2 -= n2 >> 1;
            n = CharsTrie.skipDelta(charSequence, n);
        } while (true);
        do {
            CharsTrie.append(appendable, charSequence.charAt(n3));
            n3 = CharsTrie.skipValue(charSequence, n3 + 1);
        } while (--n4 > 1);
        CharsTrie.append(appendable, charSequence.charAt(n3));
    }

    public static Iterator iterator(CharSequence charSequence, int n, int n2) {
        return new Iterator(charSequence, n, -1, n2);
    }

    private static int jumpByDelta(CharSequence charSequence, int n) {
        int n2 = n + 1;
        char c = charSequence.charAt(n);
        int n3 = n2;
        n = c;
        if (c >= '\ufc00') {
            if (c == '\uffff') {
                n = charSequence.charAt(n2) << 16 | charSequence.charAt(n2 + 1);
                n3 = n2 + 2;
            } else {
                n = c - 64512 << 16 | charSequence.charAt(n2);
                n3 = n2 + 1;
            }
        }
        return n3 + n;
    }

    private BytesTrie.Result nextImpl(int n, int n2) {
        Object object = this.chars_;
        int n3 = n + 1;
        n = object.charAt(n);
        do {
            block9 : {
                block8 : {
                    block7 : {
                        if (n < 48) {
                            return this.branchNext(n3, n, n2);
                        }
                        if (n >= 64) break block7;
                        object = this.chars_;
                        int n4 = n3 + 1;
                        if (n2 == object.charAt(n3)) {
                            this.remainingMatchLength_ = n = n - 48 - 1;
                            this.pos_ = n4;
                            object = n < 0 && (n = (int)this.chars_.charAt(n4)) >= 64 ? valueResults_[n >> 15] : BytesTrie.Result.NO_VALUE;
                            return object;
                        }
                        break block8;
                    }
                    if ((32768 & n) == 0) break block9;
                }
                this.stop();
                return BytesTrie.Result.NO_MATCH;
            }
            n3 = CharsTrie.skipNodeValue(n3, n);
            n &= 63;
        } while (true);
    }

    private static int readNodeValue(CharSequence charSequence, int n, int n2) {
        n = n2 < 16448 ? (n2 >> 6) - 1 : (n2 < 32704 ? (n2 & 32704) - 16448 << 10 | charSequence.charAt(n) : charSequence.charAt(n) << 16 | charSequence.charAt(n + 1));
        return n;
    }

    private static int readValue(CharSequence charSequence, int n, int n2) {
        n = n2 < 16384 ? n2 : (n2 < 32767 ? n2 - 16384 << 16 | charSequence.charAt(n) : charSequence.charAt(n) << 16 | charSequence.charAt(n + 1));
        return n;
    }

    private static int skipDelta(CharSequence charSequence, int n) {
        int n2 = n + 1;
        char c = charSequence.charAt(n);
        n = n2;
        if (c >= '\ufc00') {
            n = c == '\uffff' ? n2 + 2 : n2 + 1;
        }
        return n;
    }

    private static int skipNodeValue(int n, int n2) {
        int n3 = n;
        if (n2 >= 16448) {
            n3 = n2 < 32704 ? n + 1 : n + 2;
        }
        return n3;
    }

    private static int skipValue(int n, int n2) {
        int n3 = n;
        if (n2 >= 16384) {
            n3 = n2 < 32767 ? n + 1 : n + 2;
        }
        return n3;
    }

    private static int skipValue(CharSequence charSequence, int n) {
        return CharsTrie.skipValue(n + 1, charSequence.charAt(n) & 32767);
    }

    private void stop() {
        this.pos_ = -1;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public BytesTrie.Result current() {
        int n = this.pos_;
        if (n < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        BytesTrie.Result result = this.remainingMatchLength_ < 0 && (n = (int)this.chars_.charAt(n)) >= 64 ? valueResults_[n >> 15] : BytesTrie.Result.NO_VALUE;
        return result;
    }

    public BytesTrie.Result first(int n) {
        this.remainingMatchLength_ = -1;
        return this.nextImpl(this.root_, n);
    }

    public BytesTrie.Result firstForCodePoint(int n) {
        BytesTrie.Result result = n <= 65535 ? this.first(n) : (this.first(UTF16.getLeadSurrogate(n)).hasNext() ? this.next(UTF16.getTrailSurrogate(n)) : BytesTrie.Result.NO_MATCH);
        return result;
    }

    public int getNextChars(Appendable appendable) {
        int n;
        int n2 = this.pos_;
        if (n2 < 0) {
            return 0;
        }
        if (this.remainingMatchLength_ >= 0) {
            CharsTrie.append(appendable, this.chars_.charAt(n2));
            return 1;
        }
        CharSequence charSequence = this.chars_;
        int n3 = n2 + 1;
        int n4 = n = charSequence.charAt(n2);
        n2 = n3;
        if (n >= 64) {
            if ((32768 & n) != 0) {
                return 0;
            }
            n2 = CharsTrie.skipNodeValue(n3, n);
            n4 = n & 63;
        }
        if (n4 < 48) {
            if (n4 == 0) {
                charSequence = this.chars_;
                n = n2 + 1;
                n4 = charSequence.charAt(n2);
                n2 = n;
            }
            charSequence = this.chars_;
            CharsTrie.getNextBranchChars(charSequence, n2, ++n4, appendable);
            return n4;
        }
        CharsTrie.append(appendable, this.chars_.charAt(n2));
        return 1;
    }

    public long getUniqueValue() {
        int n = this.pos_;
        if (n < 0) {
            return 0L;
        }
        return CharsTrie.findUniqueValue(this.chars_, this.remainingMatchLength_ + n + 1, 0L) << 31 >> 31;
    }

    public int getValue() {
        int n = this.pos_;
        CharSequence charSequence = this.chars_;
        int n2 = n + 1;
        n2 = (32768 & (n = (int)charSequence.charAt(n))) != 0 ? CharsTrie.readValue(this.chars_, n2, n & 32767) : CharsTrie.readNodeValue(this.chars_, n2, n);
        return n2;
    }

    public Iterator iterator() {
        return new Iterator(this.chars_, this.pos_, this.remainingMatchLength_, 0);
    }

    public Iterator iterator(int n) {
        return new Iterator(this.chars_, this.pos_, this.remainingMatchLength_, n);
    }

    public BytesTrie.Result next(int n) {
        int n2 = this.pos_;
        if (n2 < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        int n3 = this.remainingMatchLength_;
        if (n3 >= 0) {
            Object object = this.chars_;
            int n4 = n2 + 1;
            if (n == object.charAt(n2)) {
                this.remainingMatchLength_ = n = n3 - 1;
                this.pos_ = n4;
                object = n < 0 && (n = (int)this.chars_.charAt(n4)) >= 64 ? valueResults_[n >> 15] : BytesTrie.Result.NO_VALUE;
                return object;
            }
            this.stop();
            return BytesTrie.Result.NO_MATCH;
        }
        return this.nextImpl(n2, n);
    }

    public BytesTrie.Result next(CharSequence object, int n, int n2) {
        if (n >= n2) {
            return this.current();
        }
        int n3 = this.pos_;
        if (n3 < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        int n4 = this.remainingMatchLength_;
        int n5 = n;
        n = n3;
        block0 : do {
            if (n5 == n2) {
                this.remainingMatchLength_ = n4;
                this.pos_ = n;
                object = n4 < 0 && (n = (int)this.chars_.charAt(n)) >= 64 ? valueResults_[n >> 15] : BytesTrie.Result.NO_VALUE;
                return object;
            }
            n3 = n5 + 1;
            char c = object.charAt(n5);
            if (n4 < 0) {
                this.remainingMatchLength_ = n4;
                Object object2 = this.chars_;
                n4 = n + 1;
                n5 = object2.charAt(n);
                n = n3;
                do {
                    if (n5 < 48) {
                        object2 = this.branchNext(n4, n5, c);
                        if (object2 == BytesTrie.Result.NO_MATCH) {
                            return BytesTrie.Result.NO_MATCH;
                        }
                        if (n == n2) {
                            return object2;
                        }
                        if (object2 == BytesTrie.Result.FINAL_VALUE) {
                            this.stop();
                            return BytesTrie.Result.NO_MATCH;
                        }
                        c = object.charAt(n);
                        n4 = this.pos_;
                        n5 = this.chars_.charAt(n4);
                        ++n;
                        ++n4;
                        continue;
                    }
                    if (n5 < 64) {
                        if (c != this.chars_.charAt(n4)) {
                            this.stop();
                            return BytesTrie.Result.NO_MATCH;
                        }
                        n3 = n5 - 48 - 1;
                        n5 = n;
                        n = ++n4;
                        n4 = n3;
                        continue block0;
                    }
                    if ((32768 & n5) != 0) {
                        this.stop();
                        return BytesTrie.Result.NO_MATCH;
                    }
                    n4 = CharsTrie.skipNodeValue(n4, n5);
                    n5 &= 63;
                } while (true);
            }
            if (c != this.chars_.charAt(n)) {
                this.stop();
                return BytesTrie.Result.NO_MATCH;
            }
            ++n;
            --n4;
            n5 = n3;
        } while (true);
    }

    public BytesTrie.Result nextForCodePoint(int n) {
        BytesTrie.Result result = n <= 65535 ? this.next(n) : (this.next(UTF16.getLeadSurrogate(n)).hasNext() ? this.next(UTF16.getTrailSurrogate(n)) : BytesTrie.Result.NO_MATCH);
        return result;
    }

    public CharsTrie reset() {
        this.pos_ = this.root_;
        this.remainingMatchLength_ = -1;
        return this;
    }

    public CharsTrie resetToState(State state) {
        if (this.chars_ == state.chars && this.chars_ != null && this.root_ == state.root) {
            this.pos_ = state.pos;
            this.remainingMatchLength_ = state.remainingMatchLength;
            return this;
        }
        throw new IllegalArgumentException("incompatible trie state");
    }

    public CharsTrie saveState(State state) {
        state.chars = this.chars_;
        state.root = this.root_;
        state.pos = this.pos_;
        state.remainingMatchLength = this.remainingMatchLength_;
        return this;
    }

    public static final class Entry {
        public CharSequence chars;
        public int value;

        private Entry() {
        }
    }

    public static final class Iterator
    implements java.util.Iterator<Entry> {
        private CharSequence chars_;
        private Entry entry_ = new Entry();
        private int initialPos_;
        private int initialRemainingMatchLength_;
        private int maxLength_;
        private int pos_;
        private int remainingMatchLength_;
        private boolean skipValue_;
        private ArrayList<Long> stack_ = new ArrayList();
        private StringBuilder str_ = new StringBuilder();

        private Iterator(CharSequence charSequence, int n, int n2, int n3) {
            this.chars_ = charSequence;
            this.initialPos_ = n;
            this.pos_ = n;
            this.initialRemainingMatchLength_ = n2;
            this.remainingMatchLength_ = n2;
            this.maxLength_ = n3;
            n = this.remainingMatchLength_;
            if (n >= 0) {
                n2 = n + 1;
                n3 = this.maxLength_;
                n = n2;
                if (n3 > 0) {
                    n = n2;
                    if (n2 > n3) {
                        n = this.maxLength_;
                    }
                }
                charSequence = this.str_;
                CharSequence charSequence2 = this.chars_;
                n2 = this.pos_;
                ((StringBuilder)charSequence).append(charSequence2, n2, n2 + n);
                this.pos_ += n;
                this.remainingMatchLength_ -= n;
            }
        }

        private int branchNext(int n, int n2) {
            while (n2 > 5) {
                this.stack_.add((long)CharsTrie.skipDelta(this.chars_, ++n) << 32 | (long)(n2 - (n2 >> 1) << 16) | (long)this.str_.length());
                n2 >>= 1;
                n = CharsTrie.jumpByDelta(this.chars_, n);
            }
            Object object = this.chars_;
            int n3 = n + 1;
            char c = object.charAt(n);
            object = this.chars_;
            int n4 = n3 + 1;
            n = (32768 & (n3 = (int)object.charAt(n3))) != 0 ? 1 : 0;
            object = this.chars_;
            int n5 = n3 & 32767;
            n3 = CharsTrie.readValue((CharSequence)object, n4, n5);
            n4 = CharsTrie.skipValue(n4, n5);
            this.stack_.add((long)n4 << 32 | (long)(n2 - 1 << 16) | (long)this.str_.length());
            this.str_.append(c);
            if (n != 0) {
                this.pos_ = -1;
                object = this.entry_;
                ((Entry)object).chars = this.str_;
                ((Entry)object).value = n3;
                return -1;
            }
            return n4 + n3;
        }

        private Entry truncateAndStop() {
            this.pos_ = -1;
            Entry entry = this.entry_;
            entry.chars = this.str_;
            entry.value = -1;
            return entry;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.pos_ >= 0 || !this.stack_.isEmpty();
            return bl;
        }

        @Override
        public Entry next() {
            int n;
            Object object;
            int n2 = n = this.pos_;
            if (n < 0) {
                if (!this.stack_.isEmpty()) {
                    object = this.stack_;
                    long l = (Long)((ArrayList)object).remove(((ArrayList)object).size() - 1);
                    n = (int)l;
                    n2 = (int)(l >> 32);
                    this.str_.setLength(65535 & n);
                    if ((n >>>= 16) > 1) {
                        n2 = n = this.branchNext(n2, n);
                        if (n < 0) {
                            return this.entry_;
                        }
                    } else {
                        this.str_.append(this.chars_.charAt(n2));
                        ++n2;
                    }
                } else {
                    throw new NoSuchElementException();
                }
            }
            if (this.remainingMatchLength_ >= 0) {
                return this.truncateAndStop();
            }
            do {
                object = this.chars_;
                int n3 = n2 + 1;
                int n4 = object.charAt(n2);
                n = n4;
                n2 = n3;
                if (n4 >= 64) {
                    boolean bl = this.skipValue_;
                    n2 = 0;
                    if (bl) {
                        n2 = CharsTrie.skipNodeValue(n3, n4);
                        n = n4 & 63;
                        this.skipValue_ = false;
                    } else {
                        if ((32768 & n4) != 0) {
                            n2 = 1;
                        }
                        this.entry_.value = n2 != 0 ? CharsTrie.readValue(this.chars_, n3, n4 & 32767) : CharsTrie.readNodeValue(this.chars_, n3, n4);
                        if (n2 == 0 && (this.maxLength_ <= 0 || this.str_.length() != this.maxLength_)) {
                            this.pos_ = n3 - 1;
                            this.skipValue_ = true;
                        } else {
                            this.pos_ = -1;
                        }
                        object = this.entry_;
                        ((Entry)object).chars = this.str_;
                        return object;
                    }
                }
                if (this.maxLength_ > 0 && this.str_.length() == this.maxLength_) {
                    return this.truncateAndStop();
                }
                if (n < 48) {
                    n4 = n;
                    n3 = n2;
                    if (n == 0) {
                        n4 = this.chars_.charAt(n2);
                        n3 = n2 + 1;
                    }
                    if ((n2 = this.branchNext(n3, n4 + 1)) >= 0) continue;
                    return this.entry_;
                }
                n4 = n - 48 + 1;
                if (this.maxLength_ > 0 && (n3 = this.str_.length()) + n4 > (n = this.maxLength_)) {
                    object = this.str_;
                    ((StringBuilder)object).append(this.chars_, n2, n + n2 - ((StringBuilder)object).length());
                    return this.truncateAndStop();
                }
                this.str_.append(this.chars_, n2, n2 + n4);
                n2 += n4;
            } while (true);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Iterator reset() {
            this.pos_ = this.initialPos_;
            this.remainingMatchLength_ = this.initialRemainingMatchLength_;
            this.skipValue_ = false;
            int n = this.remainingMatchLength_ + 1;
            int n2 = this.maxLength_;
            int n3 = n;
            if (n2 > 0) {
                n3 = n;
                if (n > n2) {
                    n3 = this.maxLength_;
                }
            }
            this.str_.setLength(n3);
            this.pos_ += n3;
            this.remainingMatchLength_ -= n3;
            this.stack_.clear();
            return this;
        }
    }

    public static final class State {
        private CharSequence chars;
        private int pos;
        private int remainingMatchLength;
        private int root;
    }

}

