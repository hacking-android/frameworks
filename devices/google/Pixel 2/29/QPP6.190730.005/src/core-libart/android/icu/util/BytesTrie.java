/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public final class BytesTrie
implements Cloneable,
Iterable<Entry> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int kFiveByteDeltaLead = 255;
    static final int kFiveByteValueLead = 127;
    static final int kFourByteDeltaLead = 254;
    static final int kFourByteValueLead = 126;
    static final int kMaxBranchLinearSubNodeLength = 5;
    static final int kMaxLinearMatchLength = 16;
    static final int kMaxOneByteDelta = 191;
    static final int kMaxOneByteValue = 64;
    static final int kMaxThreeByteDelta = 917503;
    static final int kMaxThreeByteValue = 1179647;
    static final int kMaxTwoByteDelta = 12287;
    static final int kMaxTwoByteValue = 6911;
    static final int kMinLinearMatch = 16;
    static final int kMinOneByteValueLead = 16;
    static final int kMinThreeByteDeltaLead = 240;
    static final int kMinThreeByteValueLead = 108;
    static final int kMinTwoByteDeltaLead = 192;
    static final int kMinTwoByteValueLead = 81;
    static final int kMinValueLead = 32;
    private static final int kValueIsFinal = 1;
    private static Result[] valueResults_ = new Result[]{Result.INTERMEDIATE_VALUE, Result.FINAL_VALUE};
    private byte[] bytes_;
    private int pos_;
    private int remainingMatchLength_;
    private int root_;

    public BytesTrie(byte[] arrby, int n) {
        this.bytes_ = arrby;
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

    private Result branchNext(int n, int n2, int n3) {
        Object object;
        int n4 = n;
        int n5 = n2;
        if (n2 == 0) {
            n5 = this.bytes_[n] & 255;
            n4 = n + 1;
        }
        n2 = n5 + 1;
        n = n4;
        do {
            n4 = n;
            n5 = n2;
            if (n2 <= 5) break;
            object = this.bytes_;
            n4 = n + 1;
            if (n3 < (object[n] & 255)) {
                n2 >>= 1;
                n = BytesTrie.jumpByDelta(object, n4);
                continue;
            }
            n2 -= n2 >> 1;
            n = BytesTrie.skipDelta(object, n4);
        } while (true);
        do {
            object = this.bytes_;
            n = n4 + 1;
            if (n3 == (object[n4] & 255)) {
                n3 = object[n] & 255;
                if ((n3 & 1) != 0) {
                    object = Result.FINAL_VALUE;
                } else {
                    n2 = n + 1;
                    n = n3 >> 1;
                    if (n < 81) {
                        n -= 16;
                    } else if (n < 108) {
                        n = n - 81 << 8 | object[n2] & 255;
                        ++n2;
                    } else if (n < 126) {
                        n = n - 108 << 16 | (object[n2] & 255) << 8 | object[n2 + 1] & 255;
                        n2 += 2;
                    } else if (n == 126) {
                        n = (object[n2] & 255) << 16 | (object[n2 + 1] & 255) << 8 | object[n2 + 2] & 255;
                        n2 += 3;
                    } else {
                        n = object[n2] << 24 | (object[n2 + 1] & 255) << 16 | (object[n2 + 2] & 255) << 8 | object[n2 + 3] & 255;
                        n2 += 4;
                    }
                    n = n2 + n;
                    n2 = this.bytes_[n] & 255;
                    object = n2 >= 32 ? valueResults_[n2 & 1] : Result.NO_VALUE;
                }
                this.pos_ = n;
                return object;
            }
            n4 = BytesTrie.skipValue(object, n);
        } while (--n5 > 1);
        object = this.bytes_;
        n = n4 + 1;
        if (n3 == (object[n4] & 255)) {
            this.pos_ = n;
            object = (n = object[n] & 255) >= 32 ? valueResults_[n & 1] : Result.NO_VALUE;
            return object;
        }
        this.stop();
        return Result.NO_MATCH;
    }

    private static long findUniqueValue(byte[] arrby, int n, long l) {
        long l2 = l;
        do {
            int n2;
            int n3 = n + 1;
            int n4 = arrby[n] & 255;
            if (n4 < 16) {
                n2 = n3;
                n = n4;
                if (n4 == 0) {
                    n = arrby[n3] & 255;
                    n2 = n3 + 1;
                }
                if ((l = BytesTrie.findUniqueValueFromBranch(arrby, n2, n + 1, l2)) == 0L) {
                    return 0L;
                }
                n = (int)(l >>> 33);
            } else if (n4 < 32) {
                n = n3 + (n4 - 16 + 1);
                l = l2;
            } else {
                n = (n4 & 1) != 0 ? 1 : 0;
                n2 = BytesTrie.readValue(arrby, n3, n4 >> 1);
                if (l2 != 0L) {
                    l = l2;
                    if (n2 != (int)(l2 >> 1)) {
                        return 0L;
                    }
                } else {
                    l = (long)n2 << 1 | 1L;
                }
                if (n != 0) {
                    return l;
                }
                n = BytesTrie.skipValue(n3, n4);
            }
            l2 = l;
        } while (true);
    }

    private static long findUniqueValueFromBranch(byte[] arrby, int n, int n2, long l) {
        long l2;
        int n3;
        int n4;
        do {
            n4 = n++;
            n3 = n2;
            l2 = l;
            if (n2 <= 5) break;
            l = BytesTrie.findUniqueValueFromBranch(arrby, BytesTrie.jumpByDelta(arrby, n), n2 >> 1, l);
            if (l == 0L) {
                return 0L;
            }
            n2 -= n2 >> 1;
            n = BytesTrie.skipDelta(arrby, n);
        } while (true);
        do {
            n = n4 + 1;
            n2 = n + 1;
            n4 = arrby[n] & 255;
            n = (n4 & 1) != 0 ? 1 : 0;
            int n5 = BytesTrie.readValue(arrby, n2, n4 >> 1);
            n4 = BytesTrie.skipValue(n2, n4);
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
                l = l2 = BytesTrie.findUniqueValue(arrby, n4 + n5, l2);
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

    private static void getNextBranchBytes(byte[] arrby, int n, int n2, Appendable appendable) {
        int n3;
        int n4;
        do {
            n3 = n++;
            n4 = n2;
            if (n2 <= 5) break;
            BytesTrie.getNextBranchBytes(arrby, BytesTrie.jumpByDelta(arrby, n), n2 >> 1, appendable);
            n2 -= n2 >> 1;
            n = BytesTrie.skipDelta(arrby, n);
        } while (true);
        do {
            BytesTrie.append(appendable, arrby[n3] & 255);
            n3 = BytesTrie.skipValue(arrby, n3 + 1);
        } while (--n4 > 1);
        BytesTrie.append(appendable, arrby[n3] & 255);
    }

    public static Iterator iterator(byte[] arrby, int n, int n2) {
        return new Iterator(arrby, n, -1, n2);
    }

    private static int jumpByDelta(byte[] arrby, int n) {
        int n2 = n + 1;
        int n3 = arrby[n] & 255;
        if (n3 < 192) {
            n = n2;
            n2 = n3;
        } else if (n3 < 240) {
            n3 = n3 - 192 << 8 | arrby[n2] & 255;
            n = n2 + 1;
            n2 = n3;
        } else if (n3 < 254) {
            n3 = n3 - 240 << 16 | (arrby[n2] & 255) << 8 | arrby[n2 + 1] & 255;
            n = n2 + 2;
            n2 = n3;
        } else if (n3 == 254) {
            n3 = (arrby[n2] & 255) << 16 | (arrby[n2 + 1] & 255) << 8 | arrby[n2 + 2] & 255;
            n = n2 + 3;
            n2 = n3;
        } else {
            n3 = arrby[n2] << 24 | (arrby[n2 + 1] & 255) << 16 | (arrby[n2 + 2] & 255) << 8 | arrby[n2 + 3] & 255;
            n = n2 + 4;
            n2 = n3;
        }
        return n + n2;
    }

    private Result nextImpl(int n, int n2) {
        do {
            int n3;
            int n4;
            block9 : {
                block8 : {
                    block7 : {
                        Object object = this.bytes_;
                        n3 = n + 1;
                        n4 = object[n] & 255;
                        if (n4 < 16) {
                            return this.branchNext(n3, n4, n2);
                        }
                        if (n4 >= 32) break block7;
                        n = n3 + 1;
                        if (n2 == (object[n3] & 255)) {
                            this.remainingMatchLength_ = n2 = n4 - 16 - 1;
                            this.pos_ = n;
                            object = n2 < 0 && (n = object[n] & 255) >= 32 ? valueResults_[n & 1] : Result.NO_VALUE;
                            return object;
                        }
                        break block8;
                    }
                    if ((n4 & 1) == 0) break block9;
                }
                this.stop();
                return Result.NO_MATCH;
            }
            n = BytesTrie.skipValue(n3, n4);
        } while (true);
    }

    private static int readValue(byte[] arrby, int n, int n2) {
        n = n2 < 81 ? n2 - 16 : (n2 < 108 ? n2 - 81 << 8 | arrby[n] & 255 : (n2 < 126 ? n2 - 108 << 16 | (arrby[n] & 255) << 8 | arrby[n + 1] & 255 : (n2 == 126 ? (arrby[n] & 255) << 16 | (arrby[n + 1] & 255) << 8 | arrby[n + 2] & 255 : arrby[n] << 24 | (arrby[n + 1] & 255) << 16 | (arrby[n + 2] & 255) << 8 | arrby[n + 3] & 255)));
        return n;
    }

    private static int skipDelta(byte[] arrby, int n) {
        int n2 = n + 1;
        int n3 = arrby[n] & 255;
        n = n2;
        if (n3 >= 192) {
            n = n3 < 240 ? n2 + 1 : (n3 < 254 ? n2 + 2 : n2 + ((n3 & 1) + 3));
        }
        return n;
    }

    private static int skipValue(int n, int n2) {
        int n3 = n;
        if (n2 >= 162) {
            n3 = n2 < 216 ? n + 1 : (n2 < 252 ? n + 2 : n + ((n2 >> 1 & 1) + 3));
        }
        return n3;
    }

    private static int skipValue(byte[] arrby, int n) {
        return BytesTrie.skipValue(n + 1, arrby[n] & 255);
    }

    private void stop() {
        this.pos_ = -1;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Result current() {
        int n = this.pos_;
        if (n < 0) {
            return Result.NO_MATCH;
        }
        Result result = this.remainingMatchLength_ < 0 && (n = this.bytes_[n] & 255) >= 32 ? valueResults_[n & 1] : Result.NO_VALUE;
        return result;
    }

    public Result first(int n) {
        this.remainingMatchLength_ = -1;
        int n2 = n;
        if (n < 0) {
            n2 = n + 256;
        }
        return this.nextImpl(this.root_, n2);
    }

    public int getNextBytes(Appendable appendable) {
        int n;
        int n2 = this.pos_;
        if (n2 < 0) {
            return 0;
        }
        if (this.remainingMatchLength_ >= 0) {
            BytesTrie.append(appendable, this.bytes_[n2] & 255);
            return 1;
        }
        byte[] arrby = this.bytes_;
        int n3 = n2 + 1;
        int n4 = n = arrby[n2] & 255;
        n2 = n3;
        if (n >= 32) {
            if ((n & 1) != 0) {
                return 0;
            }
            n4 = BytesTrie.skipValue(n3, n);
            arrby = this.bytes_;
            n2 = n4 + 1;
            n4 = arrby[n4] & 255;
        }
        if (n4 < 16) {
            if (n4 == 0) {
                arrby = this.bytes_;
                n3 = n2 + 1;
                n4 = arrby[n2] & 255;
                n2 = n3;
            }
            arrby = this.bytes_;
            BytesTrie.getNextBranchBytes(arrby, n2, ++n4, appendable);
            return n4;
        }
        BytesTrie.append(appendable, this.bytes_[n2] & 255);
        return 1;
    }

    public long getUniqueValue() {
        int n = this.pos_;
        if (n < 0) {
            return 0L;
        }
        return BytesTrie.findUniqueValue(this.bytes_, this.remainingMatchLength_ + n + 1, 0L) << 31 >> 31;
    }

    public int getValue() {
        int n = this.pos_;
        byte[] arrby = this.bytes_;
        byte by = arrby[n];
        return BytesTrie.readValue(arrby, n + 1, (by & 255) >> 1);
    }

    public Iterator iterator() {
        return new Iterator(this.bytes_, this.pos_, this.remainingMatchLength_, 0);
    }

    public Iterator iterator(int n) {
        return new Iterator(this.bytes_, this.pos_, this.remainingMatchLength_, n);
    }

    public Result next(int n) {
        int n2;
        int n3 = this.pos_;
        if (n3 < 0) {
            return Result.NO_MATCH;
        }
        int n4 = n;
        if (n < 0) {
            n4 = n + 256;
        }
        if ((n2 = this.remainingMatchLength_) >= 0) {
            Object object = this.bytes_;
            n = n3 + 1;
            if (n4 == (object[n3] & 255)) {
                this.remainingMatchLength_ = n4 = n2 - 1;
                this.pos_ = n;
                object = n4 < 0 && (n = object[n] & 255) >= 32 ? valueResults_[n & 1] : Result.NO_VALUE;
                return object;
            }
            this.stop();
            return Result.NO_MATCH;
        }
        return this.nextImpl(n3, n4);
    }

    public Result next(byte[] object, int n, int n2) {
        if (n >= n2) {
            return this.current();
        }
        int n3 = this.pos_;
        if (n3 < 0) {
            return Result.NO_MATCH;
        }
        int n4 = this.remainingMatchLength_;
        int n5 = n;
        n = n3;
        block0 : do {
            if (n5 == n2) {
                this.remainingMatchLength_ = n4;
                this.pos_ = n;
                object = n4 < 0 && (n = this.bytes_[n] & 255) >= 32 ? valueResults_[n & 1] : Result.NO_VALUE;
                return object;
            }
            n3 = n5 + 1;
            n5 = object[n5];
            if (n4 < 0) {
                this.remainingMatchLength_ = n4;
                n4 = n3;
                do {
                    Object object2 = this.bytes_;
                    int n6 = n + 1;
                    if ((n = object2[n] & 255) < 16) {
                        object2 = this.branchNext(n6, n, n5 & 255);
                        if (object2 == Result.NO_MATCH) {
                            return Result.NO_MATCH;
                        }
                        if (n4 == n2) {
                            return object2;
                        }
                        if (object2 == Result.FINAL_VALUE) {
                            this.stop();
                            return Result.NO_MATCH;
                        }
                        n5 = object[n4];
                        n = this.pos_;
                        ++n4;
                        continue;
                    }
                    if (n < 32) {
                        if (n5 != object2[n6]) {
                            this.stop();
                            return Result.NO_MATCH;
                        }
                        n3 = n - 16 - 1;
                        n = n6 + 1;
                        n5 = n4;
                        n4 = n3;
                        continue block0;
                    }
                    if ((n & 1) != 0) {
                        this.stop();
                        return Result.NO_MATCH;
                    }
                    n = BytesTrie.skipValue(n6, n);
                } while (true);
            }
            if (n5 != this.bytes_[n]) {
                this.stop();
                return Result.NO_MATCH;
            }
            ++n;
            --n4;
            n5 = n3;
        } while (true);
    }

    public BytesTrie reset() {
        this.pos_ = this.root_;
        this.remainingMatchLength_ = -1;
        return this;
    }

    public BytesTrie resetToState(State state) {
        if (this.bytes_ == state.bytes && this.bytes_ != null && this.root_ == state.root) {
            this.pos_ = state.pos;
            this.remainingMatchLength_ = state.remainingMatchLength;
            return this;
        }
        throw new IllegalArgumentException("incompatible trie state");
    }

    public BytesTrie saveState(State state) {
        state.bytes = this.bytes_;
        state.root = this.root_;
        state.pos = this.pos_;
        state.remainingMatchLength = this.remainingMatchLength_;
        return this;
    }

    public static final class Entry {
        private byte[] bytes;
        private int length;
        public int value;

        private Entry(int n) {
            this.bytes = new byte[n];
        }

        private void append(byte by) {
            this.ensureCapacity(this.length + 1);
            byte[] arrby = this.bytes;
            int n = this.length;
            this.length = n + 1;
            arrby[n] = by;
        }

        private void append(byte[] arrby, int n, int n2) {
            this.ensureCapacity(this.length + n2);
            System.arraycopy((byte[])arrby, (int)n, (byte[])this.bytes, (int)this.length, (int)n2);
            this.length += n2;
        }

        private void ensureCapacity(int n) {
            byte[] arrby = this.bytes;
            if (arrby.length < n) {
                arrby = new byte[Math.min(arrby.length * 2, n * 2)];
                System.arraycopy((byte[])this.bytes, (int)0, (byte[])arrby, (int)0, (int)this.length);
                this.bytes = arrby;
            }
        }

        private void truncateString(int n) {
            this.length = n;
        }

        public byte byteAt(int n) {
            return this.bytes[n];
        }

        public ByteBuffer bytesAsByteBuffer() {
            return ByteBuffer.wrap(this.bytes, 0, this.length).asReadOnlyBuffer();
        }

        public int bytesLength() {
            return this.length;
        }

        public void copyBytesTo(byte[] arrby, int n) {
            System.arraycopy((byte[])this.bytes, (int)0, (byte[])arrby, (int)n, (int)this.length);
        }
    }

    public static final class Iterator
    implements java.util.Iterator<Entry> {
        private byte[] bytes_;
        private Entry entry_;
        private int initialPos_;
        private int initialRemainingMatchLength_;
        private int maxLength_;
        private int pos_;
        private int remainingMatchLength_;
        private ArrayList<Long> stack_ = new ArrayList();

        private Iterator(byte[] arrby, int n, int n2, int n3) {
            this.bytes_ = arrby;
            this.initialPos_ = n;
            this.pos_ = n;
            this.initialRemainingMatchLength_ = n2;
            this.remainingMatchLength_ = n2;
            n = this.maxLength_ = n3;
            if (n == 0) {
                n = 32;
            }
            this.entry_ = new Entry(n);
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
                this.entry_.append(this.bytes_, this.pos_, n);
                this.pos_ += n;
                this.remainingMatchLength_ -= n;
            }
        }

        private int branchNext(int n, int n2) {
            while (n2 > 5) {
                this.stack_.add((long)BytesTrie.skipDelta(this.bytes_, ++n) << 32 | (long)(n2 - (n2 >> 1) << 16) | (long)this.entry_.length);
                n2 >>= 1;
                n = BytesTrie.jumpByDelta(this.bytes_, n);
            }
            byte[] arrby = this.bytes_;
            int n3 = n + 1;
            byte by = arrby[n];
            int n4 = n3 + 1;
            n = ((n3 = arrby[n3] & 255) & 1) != 0 ? 1 : 0;
            int n5 = BytesTrie.readValue(this.bytes_, n4, n3 >> 1);
            n4 = BytesTrie.skipValue(n4, n3);
            this.stack_.add((long)n4 << 32 | (long)(n2 - 1 << 16) | (long)this.entry_.length);
            this.entry_.append(by);
            if (n != 0) {
                this.pos_ = -1;
                this.entry_.value = n5;
                return -1;
            }
            return n4 + n5;
        }

        private Entry truncateAndStop() {
            this.pos_ = -1;
            Entry entry = this.entry_;
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
            Object object;
            int n = this.pos_;
            int n2 = 1;
            int n3 = n;
            if (n < 0) {
                if (!this.stack_.isEmpty()) {
                    object = this.stack_;
                    long l = (Long)((ArrayList)object).remove(((ArrayList)object).size() - 1);
                    n = (int)l;
                    n3 = (int)(l >> 32);
                    this.entry_.truncateString(65535 & n);
                    if ((n >>>= 16) > 1) {
                        n3 = n = this.branchNext(n3, n);
                        if (n < 0) {
                            return this.entry_;
                        }
                    } else {
                        this.entry_.append(this.bytes_[n3]);
                        ++n3;
                    }
                } else {
                    throw new NoSuchElementException();
                }
            }
            if (this.remainingMatchLength_ >= 0) {
                return this.truncateAndStop();
            }
            do {
                object = this.bytes_;
                int n4 = n3 + 1;
                int n5 = object[n3] & 255;
                if (n5 >= 32) {
                    n3 = (n5 & 1) != 0 ? n2 : 0;
                    this.entry_.value = BytesTrie.readValue(this.bytes_, n4, n5 >> 1);
                    this.pos_ = n3 == 0 && (this.maxLength_ <= 0 || this.entry_.length != this.maxLength_) ? BytesTrie.skipValue(n4, n5) : -1;
                    return this.entry_;
                }
                if (this.maxLength_ > 0 && this.entry_.length == this.maxLength_) {
                    return this.truncateAndStop();
                }
                if (n5 < 16) {
                    n = n5;
                    n3 = n4;
                    if (n5 == 0) {
                        n = this.bytes_[n4] & 255;
                        n3 = n4 + 1;
                    }
                    if ((n3 = this.branchNext(n3, n + 1)) >= 0) continue;
                    return this.entry_;
                }
                n = n5 - 16 + 1;
                if (this.maxLength_ > 0 && (n5 = this.entry_.length) + n > (n3 = this.maxLength_)) {
                    object = this.entry_;
                    ((Entry)object).append(this.bytes_, n4, n3 - ((Entry)object).length);
                    return this.truncateAndStop();
                }
                this.entry_.append(this.bytes_, n4, n);
                n3 = n4 + n;
            } while (true);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Iterator reset() {
            this.pos_ = this.initialPos_;
            this.remainingMatchLength_ = this.initialRemainingMatchLength_;
            int n = this.remainingMatchLength_ + 1;
            int n2 = this.maxLength_;
            int n3 = n;
            if (n2 > 0) {
                n3 = n;
                if (n > n2) {
                    n3 = this.maxLength_;
                }
            }
            this.entry_.truncateString(n3);
            this.pos_ += n3;
            this.remainingMatchLength_ -= n3;
            this.stack_.clear();
            return this;
        }
    }

    public static enum Result {
        NO_MATCH,
        NO_VALUE,
        FINAL_VALUE,
        INTERMEDIATE_VALUE;
        

        public boolean hasNext() {
            int n = this.ordinal();
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            return bl;
        }

        public boolean hasValue() {
            boolean bl = this.ordinal() >= 2;
            return bl;
        }

        public boolean matches() {
            boolean bl = this != NO_MATCH;
            return bl;
        }
    }

    public static final class State {
        private byte[] bytes;
        private int pos;
        private int remainingMatchLength;
        private int root;
    }

}

