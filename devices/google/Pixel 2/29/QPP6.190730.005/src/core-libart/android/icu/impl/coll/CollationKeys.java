/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.coll.CollationIterator;
import android.icu.impl.coll.CollationSettings;

public final class CollationKeys {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CASE_LOWER_FIRST_COMMON_HIGH = 13;
    private static final int CASE_LOWER_FIRST_COMMON_LOW = 1;
    private static final int CASE_LOWER_FIRST_COMMON_MAX_COUNT = 7;
    private static final int CASE_LOWER_FIRST_COMMON_MIDDLE = 7;
    private static final int CASE_UPPER_FIRST_COMMON_HIGH = 15;
    private static final int CASE_UPPER_FIRST_COMMON_LOW = 3;
    private static final int CASE_UPPER_FIRST_COMMON_MAX_COUNT = 13;
    private static final int QUAT_COMMON_HIGH = 252;
    private static final int QUAT_COMMON_LOW = 28;
    private static final int QUAT_COMMON_MAX_COUNT = 113;
    private static final int QUAT_COMMON_MIDDLE = 140;
    private static final int QUAT_SHIFTED_LIMIT_BYTE = 27;
    static final int SEC_COMMON_HIGH = 69;
    private static final int SEC_COMMON_LOW = 5;
    private static final int SEC_COMMON_MAX_COUNT = 33;
    private static final int SEC_COMMON_MIDDLE = 37;
    public static final LevelCallback SIMPLE_LEVEL_FALLBACK = new LevelCallback();
    private static final int TER_LOWER_FIRST_COMMON_HIGH = 69;
    private static final int TER_LOWER_FIRST_COMMON_LOW = 5;
    private static final int TER_LOWER_FIRST_COMMON_MAX_COUNT = 33;
    private static final int TER_LOWER_FIRST_COMMON_MIDDLE = 37;
    private static final int TER_ONLY_COMMON_HIGH = 197;
    private static final int TER_ONLY_COMMON_LOW = 5;
    private static final int TER_ONLY_COMMON_MAX_COUNT = 97;
    private static final int TER_ONLY_COMMON_MIDDLE = 101;
    private static final int TER_UPPER_FIRST_COMMON_HIGH = 197;
    private static final int TER_UPPER_FIRST_COMMON_LOW = 133;
    private static final int TER_UPPER_FIRST_COMMON_MAX_COUNT = 33;
    private static final int TER_UPPER_FIRST_COMMON_MIDDLE = 165;
    private static final int[] levelMasks = new int[]{2, 6, 22, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 54};

    private CollationKeys() {
    }

    private static SortKeyLevel getSortKeyLevel(int n, int n2) {
        SortKeyLevel sortKeyLevel = (n & n2) != 0 ? new SortKeyLevel() : null;
        return sortKeyLevel;
    }

    public static void writeSortKeyUpToQuaternary(CollationIterator collationIterator, boolean[] arrbl, CollationSettings collationSettings, SortKeyByteSink sortKeyByteSink, int n, LevelCallback levelCallback, boolean bl) {
        int n2;
        int n3;
        Object object = collationSettings;
        Object object2 = sortKeyByteSink;
        int n4 = ((CollationSettings)object).options;
        int n5 = n3 = levelMasks[CollationSettings.getStrength(n4)];
        if ((n4 & 1024) != 0) {
            n5 = n3 | 8;
        }
        if ((n2 = n5 & (1 << n) - 1) == 0) {
            return;
        }
        long l = (n4 & 12) == 0 ? 0L : ((CollationSettings)object).variableTop + 1L;
        int n6 = CollationSettings.getTertiaryMask(n4);
        byte[] arrby = new byte[3];
        SortKeyLevel sortKeyLevel = CollationKeys.getSortKeyLevel(n2, 8);
        SortKeyLevel sortKeyLevel2 = CollationKeys.getSortKeyLevel(n2, 4);
        object = CollationKeys.getSortKeyLevel(n2, 16);
        SortKeyLevel sortKeyLevel3 = CollationKeys.getSortKeyLevel(n2, 32);
        int n7 = 0;
        int n8 = 0;
        n5 = 0;
        n3 = 0;
        int n9 = 0;
        n = 0;
        long l2 = 0L;
        do {
            int n10;
            int n11;
            byte[] arrby2;
            long l3;
            int n12;
            long l4;
            block96 : {
                long l5;
                block93 : {
                    long l6;
                    block95 : {
                        boolean bl2;
                        block94 : {
                            CollationSettings collationSettings2 = collationSettings;
                            collationIterator.clearCEsIfNoneRemaining();
                            l3 = collationIterator.nextCE();
                            l6 = l3 >>> 32;
                            if (l6 < l && l6 > 0x2000000L) {
                                if (n3 != 0) {
                                    --n3;
                                    while (n3 >= 113) {
                                        sortKeyLevel3.appendByte(140);
                                        n3 -= 113;
                                    }
                                    sortKeyLevel3.appendByte(n3 + 28);
                                    n3 = 0;
                                }
                                do {
                                    if ((n2 & 32) != 0) {
                                        l5 = l6;
                                        if (collationSettings.hasReordering()) {
                                            l5 = collationSettings2.reorder(l6);
                                        }
                                        if ((int)l5 >>> 24 >= 27) {
                                            sortKeyLevel3.appendByte(27);
                                        }
                                        sortKeyLevel3.appendWeight32(l5);
                                    }
                                    do {
                                        arrby2 = object;
                                    } while ((l6 = (l3 = collationIterator.nextCE()) >>> 32) == 0L);
                                    if (l6 >= l || l6 <= 0x2000000L) break;
                                    object = arrby2;
                                } while (true);
                                l5 = l6;
                                object = arrby2;
                            } else {
                                l5 = l6;
                            }
                            if (l5 <= 1L || (n2 & 2) == 0) break block93;
                            bl2 = arrbl[(int)l5 >>> 24];
                            l6 = l5;
                            if (collationSettings.hasReordering()) {
                                l6 = collationSettings2.reorder(l5);
                            }
                            n11 = (int)l6 >>> 24;
                            if (!bl2) break block94;
                            l5 = l2;
                            if (n11 == (int)l2 >>> 24) break block95;
                        }
                        if (l2 != 0L) {
                            if (l6 < l2) {
                                if (n11 > 2) {
                                    ((SortKeyByteSink)object2).Append(3);
                                }
                            } else {
                                ((SortKeyByteSink)object2).Append(255);
                            }
                        }
                        ((SortKeyByteSink)object2).Append(n11);
                        l5 = bl2 ? l6 : 0L;
                    }
                    if ((n11 = (int)((byte)(l6 >>> 16))) != 0) {
                        arrby[0] = (byte)n11;
                        arrby[1] = (byte)(l6 >>> 8);
                        arrby[2] = (byte)l6;
                        n11 = arrby[1] == 0 ? 1 : (arrby[2] == 0 ? 2 : 3);
                        object2 = sortKeyByteSink;
                        ((SortKeyByteSink)object2).Append(arrby, n11);
                    }
                    arrby2 = object2;
                    l2 = l5;
                    l4 = l6;
                    if (!bl) {
                        arrby2 = object2;
                        l2 = l5;
                        l4 = l6;
                        if (sortKeyByteSink.Overflowed()) {
                            return;
                        }
                    }
                    break block96;
                }
                l4 = l5;
                arrby2 = object2;
            }
            if ((n10 = (int)l3) == 0) {
                object2 = arrby2;
                continue;
            }
            if ((n2 & 4) != 0 && (n12 = n10 >>> 16) != 0) {
                if (n12 == 1280 && ((n4 & 2048) == 0 || l4 != 0x2000000L)) {
                    ++n8;
                    n12 = n9;
                } else if ((n4 & 2048) == 0) {
                    n11 = n8;
                    if (n8 != 0) {
                        --n8;
                        while (n8 >= 33) {
                            sortKeyLevel2.appendByte(37);
                            n8 -= 33;
                        }
                        n8 = n12 < 1280 ? (n8 += 5) : 69 - n8;
                        sortKeyLevel2.appendByte(n8);
                        n11 = 0;
                    }
                    sortKeyLevel2.appendWeight16(n12);
                    n8 = n11;
                    n12 = n9;
                } else {
                    if (n8 != 0) {
                        n11 = --n8 % 33;
                        n = n < 1280 ? n11 + 5 : 69 - n11;
                        sortKeyLevel2.appendByte(n);
                        n8 -= n11;
                        n = n11;
                        while (n8 > 0) {
                            sortKeyLevel2.appendByte(37);
                            n8 -= 33;
                        }
                    }
                    if (0L < l4 && l4 <= 0x2000000L) {
                        object2 = sortKeyLevel2.data();
                        n11 = sortKeyLevel2.length() - 1;
                        n = n9;
                        for (n9 = n11; n < n9; --n9, ++n) {
                            n11 = object2[n];
                            object2[n] = object2[n9];
                            object2[n9] = (byte)n11;
                        }
                        n = l4 == 1L ? 1 : 2;
                        sortKeyLevel2.appendByte(n);
                        n = 0;
                        n12 = sortKeyLevel2.length();
                    } else {
                        sortKeyLevel2.appendReverseWeight16(n12);
                        n = n12;
                        n12 = n9;
                    }
                }
            } else {
                n12 = n9;
            }
            if ((n2 & 8) != 0) {
                if (CollationSettings.getStrength(n4) == 0 ? l4 == 0L : n10 >>> 16 == 0) {
                    n11 = n7;
                } else {
                    int n13 = n10 >>> 8 & 255;
                    if ((n13 & 192) == 0 && n13 > 1) {
                        n11 = n7 + 1;
                    } else {
                        if ((n4 & 256) == 0) {
                            if (n7 != 0) {
                                if (n13 <= 1 && sortKeyLevel.isEmpty()) {
                                    n9 = n7;
                                } else {
                                    --n7;
                                    while (n7 >= 7) {
                                        sortKeyLevel.appendByte(112);
                                        n7 -= 7;
                                    }
                                    n7 = n13 <= 1 ? ++n7 : 13 - n7;
                                    sortKeyLevel.appendByte(n7 << 4);
                                    n9 = 0;
                                }
                            } else {
                                n9 = n7;
                            }
                            n7 = n13;
                            n11 = n9;
                            if (n13 > 1) {
                                n7 = (n13 >>> 6) + 13 << 4;
                                n11 = n9;
                            }
                        } else {
                            n9 = n7;
                            if (n7 != 0) {
                                --n7;
                                while (n7 >= 13) {
                                    sortKeyLevel.appendByte(48);
                                    n7 -= 13;
                                }
                                sortKeyLevel.appendByte(n7 + 3 << 4);
                                n9 = 0;
                            }
                            n7 = n13;
                            n11 = n9;
                            if (n13 > 1) {
                                n7 = 3 - (n13 >>> 6) << 4;
                                n11 = n9;
                            }
                        }
                        sortKeyLevel.appendByte(n7);
                    }
                }
            } else {
                n11 = n7;
            }
            if ((n2 & 16) != 0) {
                n7 = n10 & n6;
                if (n7 == 1280) {
                    ++n5;
                } else if ((32768 & n6) == 0) {
                    if (n5 != 0) {
                        --n5;
                        while (n5 >= 97) {
                            ((SortKeyLevel)object).appendByte(101);
                            n5 -= 97;
                        }
                        n5 = n7 < 1280 ? (n5 += 5) : 197 - n5;
                        ((SortKeyLevel)object).appendByte(n5);
                        n5 = 0;
                    }
                    n9 = n7;
                    if (n7 > 1280) {
                        n9 = n7 + 49152;
                    }
                    ((SortKeyLevel)object).appendWeight16(n9);
                } else {
                    object2 = object;
                    if ((n4 & 256) == 0) {
                        n9 = n5;
                        if (n5 != 0) {
                            --n5;
                            while (n5 >= 33) {
                                ((SortKeyLevel)object2).appendByte(37);
                                n5 -= 33;
                            }
                            n5 = n7 < 1280 ? (n5 += 5) : 69 - n5;
                            ((SortKeyLevel)object2).appendByte(n5);
                            n9 = 0;
                        }
                        n5 = n7;
                        if (n7 > 1280) {
                            n5 = n7 + 16384;
                        }
                        ((SortKeyLevel)object2).appendWeight16(n5);
                        n5 = n9;
                    } else {
                        if (n7 > 256) {
                            if (n10 >>> 16 != 0) {
                                n7 = n9 = n7 ^ 49152;
                                if (n9 < 50432) {
                                    n7 = n9 - 16384;
                                }
                            } else {
                                n7 += 16384;
                            }
                        }
                        n9 = n5;
                        if (n5 != 0) {
                            --n5;
                            while (n5 >= 33) {
                                ((SortKeyLevel)object2).appendByte(165);
                                n5 -= 33;
                            }
                            n5 = n7 < 34048 ? (n5 += 133) : 197 - n5;
                            ((SortKeyLevel)object2).appendByte(n5);
                            n9 = 0;
                        }
                        ((SortKeyLevel)object2).appendWeight16(n7);
                        n5 = n9;
                    }
                }
            }
            n9 = n3;
            if ((n2 & 32) != 0) {
                n7 = 65535 & n10;
                if ((n7 & 192) == 0 && n7 > 256) {
                    n9 = n3 + 1;
                } else if (n7 == 256 && (n4 & 12) == 0 && sortKeyLevel3.isEmpty()) {
                    sortKeyLevel3.appendByte(1);
                    n9 = n3;
                } else {
                    n7 = n7 == 256 ? 1 : (n7 >>> 6 & 3) + 252;
                    n9 = n3;
                    if (n3 != 0) {
                        --n3;
                        while (n3 >= 113) {
                            sortKeyLevel3.appendByte(140);
                            n3 -= 113;
                        }
                        n3 = n7 < 28 ? (n3 += 28) : 252 - n3;
                        sortKeyLevel3.appendByte(n3);
                        n9 = 0;
                    }
                    sortKeyLevel3.appendByte(n7);
                }
            }
            if (n10 >>> 24 == 1) {
                if ((n2 & 4) != 0) {
                    if (!levelCallback.needToWrite(2)) {
                        return;
                    }
                    arrby2.Append(1);
                    sortKeyLevel2.appendTo((SortKeyByteSink)arrby2);
                }
                if ((n2 & 8) != 0) {
                    if (!levelCallback.needToWrite(3)) {
                        return;
                    }
                    arrby2.Append(1);
                    n8 = sortKeyLevel.length();
                    n = 0;
                    for (n5 = 0; n5 < n8 - 1; ++n5) {
                        n3 = sortKeyLevel.getAt(n5);
                        if (n == 0) {
                            n = n3;
                            continue;
                        }
                        arrby2.Append(n3 >> 4 & 15 | n);
                        n = 0;
                    }
                    if (n != 0) {
                        arrby2.Append(n);
                    }
                }
                if ((n2 & 16) != 0) {
                    if (!levelCallback.needToWrite(4)) {
                        return;
                    }
                    arrby2.Append(1);
                    ((SortKeyLevel)object).appendTo((SortKeyByteSink)arrby2);
                }
                if ((n2 & 32) != 0) {
                    if (!levelCallback.needToWrite(5)) {
                        return;
                    }
                    arrby2.Append(1);
                    sortKeyLevel3.appendTo((SortKeyByteSink)arrby2);
                }
                return;
            }
            object2 = arrby2;
            n7 = n11;
            n3 = n9;
            n9 = n12;
        } while (true);
    }

    public static class LevelCallback {
        boolean needToWrite(int n) {
            return true;
        }
    }

    public static abstract class SortKeyByteSink {
        private int appended_ = 0;
        protected byte[] buffer_;

        public SortKeyByteSink(byte[] arrby) {
            this.buffer_ = arrby;
        }

        public void Append(int n) {
            int n2 = this.appended_;
            if (n2 < this.buffer_.length || this.Resize(1, n2)) {
                this.buffer_[this.appended_] = (byte)n;
            }
            ++this.appended_;
        }

        public void Append(byte[] arrby, int n) {
            if (n > 0 && arrby != null) {
                int n2 = this.appended_;
                this.appended_ += n;
                byte[] arrby2 = this.buffer_;
                if (n <= arrby2.length - n2) {
                    System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)n2, (int)n);
                } else {
                    this.AppendBeyondCapacity(arrby, 0, n, n2);
                }
                return;
            }
        }

        protected abstract void AppendBeyondCapacity(byte[] var1, int var2, int var3, int var4);

        public int GetRemainingCapacity() {
            return this.buffer_.length - this.appended_;
        }

        public int NumberOfBytesAppended() {
            return this.appended_;
        }

        public boolean Overflowed() {
            boolean bl = this.appended_ > this.buffer_.length;
            return bl;
        }

        protected abstract boolean Resize(int var1, int var2);

        public void setBufferAndAppended(byte[] arrby, int n) {
            this.buffer_ = arrby;
            this.appended_ = n;
        }
    }

    private static final class SortKeyLevel {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int INITIAL_CAPACITY = 40;
        byte[] buffer = new byte[40];
        int len = 0;

        SortKeyLevel() {
        }

        private boolean ensureCapacity(int n) {
            int n2 = this.buffer.length * 2;
            int n3 = this.len + n * 2;
            n = n2;
            if (n2 < n3) {
                n = n3;
            }
            n2 = n;
            if (n < 200) {
                n2 = 200;
            }
            byte[] arrby = new byte[n2];
            System.arraycopy((byte[])this.buffer, (int)0, (byte[])arrby, (int)0, (int)this.len);
            this.buffer = arrby;
            return true;
        }

        void appendByte(int n) {
            if (this.len < this.buffer.length || this.ensureCapacity(1)) {
                byte[] arrby = this.buffer;
                int n2 = this.len;
                this.len = n2 + 1;
                arrby[n2] = (byte)n;
            }
        }

        void appendReverseWeight16(int n) {
            byte by = (byte)(n >>> 8);
            byte by2 = (byte)n;
            if (this.len + (n = by2 == 0 ? 1 : 2) <= this.buffer.length || this.ensureCapacity(n)) {
                if (by2 == 0) {
                    byte[] arrby = this.buffer;
                    n = this.len;
                    this.len = n + 1;
                    arrby[n] = by;
                } else {
                    byte[] arrby = this.buffer;
                    n = this.len;
                    arrby[n] = by2;
                    arrby[n + 1] = by;
                    this.len = n + 2;
                }
            }
        }

        void appendTo(SortKeyByteSink sortKeyByteSink) {
            sortKeyByteSink.Append(this.buffer, this.len - 1);
        }

        void appendWeight16(int n) {
            byte by = (byte)(n >>> 8);
            byte by2 = (byte)n;
            if (this.len + (n = by2 == 0 ? 1 : 2) <= this.buffer.length || this.ensureCapacity(n)) {
                byte[] arrby = this.buffer;
                n = this.len;
                this.len = n + 1;
                arrby[n] = by;
                if (by2 != 0) {
                    n = this.len;
                    this.len = n + 1;
                    arrby[n] = by2;
                }
            }
        }

        void appendWeight32(long l) {
            int n = 4;
            byte[] arrby = new byte[]{(byte)(l >>> 24), (byte)(l >>> 16), (byte)(l >>> 8), (byte)l};
            if (arrby[1] == 0) {
                n = 1;
            } else if (arrby[2] == 0) {
                n = 2;
            } else if (arrby[3] == 0) {
                n = 3;
            }
            if (this.len + n <= this.buffer.length || this.ensureCapacity(n)) {
                byte[] arrby2 = this.buffer;
                n = this.len;
                this.len = n + 1;
                arrby2[n] = arrby[0];
                if (arrby[1] != 0) {
                    n = this.len;
                    this.len = n + 1;
                    arrby2[n] = arrby[1];
                    if (arrby[2] != 0) {
                        n = this.len;
                        this.len = n + 1;
                        arrby2[n] = arrby[2];
                        if (arrby[3] != 0) {
                            n = this.len;
                            this.len = n + 1;
                            arrby2[n] = arrby[3];
                        }
                    }
                }
            }
        }

        byte[] data() {
            return this.buffer;
        }

        byte getAt(int n) {
            return this.buffer[n];
        }

        boolean isEmpty() {
            boolean bl = this.len == 0;
            return bl;
        }

        int length() {
            return this.len;
        }
    }

}

