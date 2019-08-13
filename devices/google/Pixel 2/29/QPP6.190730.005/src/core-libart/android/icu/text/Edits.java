/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import java.nio.BufferOverflowException;
import java.util.Arrays;

public final class Edits {
    private static final int LENGTH_IN_1TRAIL = 61;
    private static final int LENGTH_IN_2TRAIL = 62;
    private static final int MAX_SHORT_CHANGE = 28671;
    private static final int MAX_SHORT_CHANGE_NEW_LENGTH = 7;
    private static final int MAX_SHORT_CHANGE_OLD_LENGTH = 6;
    private static final int MAX_UNCHANGED = 4095;
    private static final int MAX_UNCHANGED_LENGTH = 4096;
    private static final int SHORT_CHANGE_NUM_MASK = 511;
    private static final int STACK_CAPACITY = 100;
    private char[] array = new char[100];
    private int delta;
    private int length;
    private int numChanges;

    private void append(int n) {
        if (this.length < this.array.length || this.growArray()) {
            char[] arrc = this.array;
            int n2 = this.length;
            this.length = n2 + 1;
            arrc[n2] = (char)n;
        }
    }

    private boolean growArray() {
        block6 : {
            char[] arrc;
            int n;
            block5 : {
                block4 : {
                    arrc = this.array;
                    if (arrc.length != 100) break block4;
                    n = 2000;
                    break block5;
                }
                if (arrc.length == Integer.MAX_VALUE) break block6;
                n = arrc.length >= 1073741823 ? Integer.MAX_VALUE : arrc.length * 2;
            }
            arrc = this.array;
            if (n - arrc.length >= 5) {
                this.array = Arrays.copyOf(arrc, n);
                return true;
            }
            throw new BufferOverflowException();
        }
        throw new BufferOverflowException();
    }

    private int lastUnit() {
        int n = this.length;
        n = n > 0 ? this.array[n - 1] : 65535;
        return n;
    }

    private void setLastUnit(int n) {
        this.array[this.length - 1] = (char)n;
    }

    public void addReplace(int n, int n2) {
        if (n >= 0 && n2 >= 0) {
            int n3;
            if (n == 0 && n2 == 0) {
                return;
            }
            ++this.numChanges;
            int n4 = n2 - n;
            if (n4 != 0) {
                if (n4 > 0 && (n3 = this.delta) >= 0 && n4 > Integer.MAX_VALUE - n3 || n4 < 0 && (n3 = this.delta) < 0 && n4 < Integer.MIN_VALUE - n3) {
                    throw new IndexOutOfBoundsException();
                }
                this.delta += n4;
            }
            if (n > 0 && n <= 6 && n2 <= 7) {
                n2 = n << 12 | n2 << 9;
                n = this.lastUnit();
                if (4095 < n && n < 28671 && (n & -512) == n2 && (n & 511) < 511) {
                    this.setLastUnit(n + 1);
                    return;
                }
                this.append(n2);
                return;
            }
            if (n < 61 && n2 < 61) {
                this.append(n << 6 | 28672 | n2);
            } else if (this.array.length - this.length >= 5 || this.growArray()) {
                char[] arrc;
                n3 = this.length + 1;
                if (n < 61) {
                    n4 = n << 6 | 28672;
                    n = n3;
                } else if (n <= 32767) {
                    n4 = 28672 | 3904;
                    this.array[n3] = (char)(n | 32768);
                    n = n3 + 1;
                } else {
                    n4 = (n >> 30) + 62 << 6 | 28672;
                    arrc = this.array;
                    int n5 = n3 + 1;
                    arrc[n3] = (char)(n >> 15 | 32768);
                    n3 = n5 + 1;
                    arrc[n5] = (char)(n | 32768);
                    n = n3;
                }
                if (n2 < 61) {
                    n2 = n4 | n2;
                } else if (n2 <= 32767) {
                    this.array[n] = (char)(n2 | 32768);
                    n2 = n4 | 61;
                    ++n;
                } else {
                    arrc = this.array;
                    n3 = n + 1;
                    arrc[n] = (char)(n2 >> 15 | 32768);
                    n = n3 + 1;
                    arrc[n3] = (char)(n2 | 32768);
                    n2 = n4 | (n2 >> 30) + 62;
                }
                this.array[this.length] = (char)n2;
                this.length = n;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("addReplace(");
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        stringBuilder.append("): both lengths must be non-negative");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void addUnchanged(int n) {
        if (n >= 0) {
            int n2 = this.lastUnit();
            int n3 = n;
            if (n2 < 4095) {
                n3 = 4095 - n2;
                if (n3 >= n) {
                    this.setLastUnit(n2 + n);
                    return;
                }
                this.setLastUnit(4095);
                n3 = n - n3;
            }
            while (n3 >= 4096) {
                this.append(4095);
                n3 -= 4096;
            }
            if (n3 > 0) {
                this.append(n3 - 1);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("addUnchanged(");
        stringBuilder.append(n);
        stringBuilder.append("): length must not be negative");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Iterator getCoarseChangesIterator() {
        return new Iterator(this.array, this.length, true, true);
    }

    public Iterator getCoarseIterator() {
        return new Iterator(this.array, this.length, false, true);
    }

    public Iterator getFineChangesIterator() {
        return new Iterator(this.array, this.length, true, false);
    }

    public Iterator getFineIterator() {
        return new Iterator(this.array, this.length, false, false);
    }

    public boolean hasChanges() {
        boolean bl = this.numChanges != 0;
        return bl;
    }

    public int lengthDelta() {
        return this.delta;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Edits mergeAndAppend(Edits var1_1, Edits var2_2) {
        var1_1 = var1_1.getFineIterator();
        var2_2 = var2_2.getFineIterator();
        var3_3 = true;
        var4_4 = true;
        var5_5 = 0;
        var6_6 = 0;
        var7_7 = 0;
        var8_8 = 0;
        var9_9 = 0;
        var10_10 = 0;
        do {
            block24 : {
                block26 : {
                    block25 : {
                        block23 : {
                            var11_11 = var4_4;
                            var12_12 = var7_7;
                            var13_13 = var8_8;
                            if (var7_7 == 0) {
                                var11_11 = var4_4;
                                var12_12 = var7_7;
                                var13_13 = var8_8;
                                if (var4_4) {
                                    var11_11 = var4_4 = (var14_14 = var2_2.next());
                                    var12_12 = var7_7;
                                    var13_13 = var8_8;
                                    if (var14_14) {
                                        var7_7 = var2_2.oldLength();
                                        var8_8 = var2_2.newLength();
                                        var11_11 = var4_4;
                                        var12_12 = var7_7;
                                        var13_13 = var8_8;
                                        if (var7_7 == 0) {
                                            if (var6_6 != 0 && var1_1.hasChange()) {
                                                var10_10 += var8_8;
                                                continue;
                                            }
                                            this.addReplace(var9_9, var10_10 + var8_8);
                                            var10_10 = 0;
                                            var9_9 = 0;
                                            continue;
                                        }
                                    }
                                }
                            }
                            var4_4 = var3_3;
                            var7_7 = var6_6;
                            if (var6_6 != 0) break block23;
                            if (!var3_3) ** GOTO lbl-1000
                            var3_3 = var4_4 = var1_1.next();
                            if (var4_4) {
                                var8_8 = var1_1.oldLength();
                                var6_6 = var1_1.newLength();
                                var4_4 = var3_3;
                                var5_5 = var8_8;
                                var7_7 = var6_6;
                                if (var6_6 == 0) {
                                    if (var12_12 != var2_2.oldLength() && var2_2.hasChange()) {
                                        var9_9 += var8_8;
                                        var4_4 = var11_11;
                                        var5_5 = var8_8;
                                        var7_7 = var12_12;
                                        var8_8 = var13_13;
                                        continue;
                                    }
                                    this.addReplace(var9_9 + var8_8, var10_10);
                                    var10_10 = 0;
                                    var9_9 = 0;
                                    var4_4 = var11_11;
                                    var5_5 = var8_8;
                                    var7_7 = var12_12;
                                    var8_8 = var13_13;
                                    continue;
                                }
                            } else lbl-1000: // 2 sources:
                            {
                                if (var12_12 != 0) throw new IllegalArgumentException("The ab output string is shorter than the bc input string.");
                                if (var9_9 == 0) {
                                    if (var10_10 == 0) return this;
                                }
                                this.addReplace(var9_9, var10_10);
                                return this;
                            }
                        }
                        if (var12_12 == 0) throw new IllegalArgumentException("The bc input string is shorter than the ab output string.");
                        if (var1_1.hasChange() || var2_2.hasChange()) break block24;
                        if (var9_9 != 0) break block25;
                        var15_15 = var9_9;
                        var12_12 = var10_10;
                        if (var10_10 == 0) break block26;
                    }
                    this.addReplace(var9_9, var10_10);
                    var12_12 = 0;
                    var15_15 = 0;
                }
                var7_7 = var5_5 <= var13_13 ? var5_5 : var13_13;
                this.addUnchanged(var7_7);
                var5_5 = var6_6 = var5_5 - var7_7;
                var8_8 = var7_7 = var13_13 - var7_7;
                var3_3 = var4_4;
                var4_4 = var11_11;
                var9_9 = var15_15;
                var10_10 = var12_12;
                continue;
            }
            if (!var1_1.hasChange() && var2_2.hasChange()) {
                if (var7_7 >= var12_12) {
                    this.addReplace(var9_9 + var12_12, var10_10 + var13_13);
                    var10_10 = 0;
                    var9_9 = 0;
                    var6_6 = var5_5 = var7_7 - var12_12;
                    var7_7 = 0;
                    var3_3 = var4_4;
                    var4_4 = var11_11;
                    var8_8 = var13_13;
                    continue;
                }
            } else if (var1_1.hasChange() && !var2_2.hasChange()) {
                if (var7_7 <= var12_12) {
                    this.addReplace(var9_9 + var5_5, var10_10 + var7_7);
                    var10_10 = 0;
                    var9_9 = 0;
                    var7_7 = var8_8 = var12_12 - var7_7;
                    var6_6 = 0;
                    var3_3 = var4_4;
                    var4_4 = var11_11;
                    continue;
                }
            } else if (var7_7 == var12_12) {
                this.addReplace(var9_9 + var5_5, var10_10 + var13_13);
                var10_10 = 0;
                var9_9 = 0;
                var7_7 = 0;
                var6_6 = 0;
                var3_3 = var4_4;
                var4_4 = var11_11;
                var8_8 = var13_13;
                continue;
            }
            var9_9 += var5_5;
            var10_10 += var13_13;
            if (var7_7 < var12_12) {
                var7_7 = var12_12 - var7_7;
                var6_6 = 0;
                var8_8 = 0;
                var3_3 = var4_4;
                var4_4 = var11_11;
                continue;
            }
            var6_6 = var7_7 - var12_12;
            var7_7 = 0;
            var5_5 = 0;
            var3_3 = var4_4;
            var4_4 = var11_11;
            var8_8 = var13_13;
        } while (true);
    }

    public int numberOfChanges() {
        return this.numChanges;
    }

    public void reset() {
        this.numChanges = 0;
        this.delta = 0;
        this.length = 0;
    }

    public static final class Iterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final char[] array;
        private boolean changed;
        private final boolean coarse;
        private int destIndex;
        private int dir;
        private int index;
        private final int length;
        private int newLength_;
        private int oldLength_;
        private final boolean onlyChanges_;
        private int remaining;
        private int replIndex;
        private int srcIndex;

        private Iterator(char[] arrc, int n, boolean bl, boolean bl2) {
            this.array = arrc;
            this.length = n;
            this.onlyChanges_ = bl;
            this.coarse = bl2;
        }

        private int findIndex(int n, boolean bl) {
            int n2;
            int n3;
            if (n < 0) {
                return -1;
            }
            if (bl) {
                n3 = this.srcIndex;
                n2 = this.oldLength_;
            } else {
                n3 = this.destIndex;
                n2 = this.newLength_;
            }
            if (n < n3) {
                if (n >= n3 / 2) {
                    do {
                        this.previous();
                        n3 = bl ? this.srcIndex : this.destIndex;
                        if (n >= n3) {
                            return 0;
                        }
                        if (this.remaining <= 0) continue;
                        n2 = bl ? this.oldLength_ : this.newLength_;
                        int n4 = this.array[this.index];
                        int n5 = this.remaining;
                        if (n >= n3 - (n4 = (n4 & 511) + 1 - n5) * n2) {
                            n3 = (n3 - n - 1) / n2 + 1;
                            this.srcIndex -= this.oldLength_ * n3;
                            n = this.replIndex;
                            n2 = this.newLength_;
                            this.replIndex = n - n3 * n2;
                            this.destIndex -= n2 * n3;
                            this.remaining = n5 + n3;
                            return 0;
                        }
                        this.srcIndex -= this.oldLength_ * n4;
                        n3 = this.replIndex;
                        n2 = this.newLength_;
                        this.replIndex = n3 - n4 * n2;
                        this.destIndex -= n2 * n4;
                        this.remaining = 0;
                    } while (true);
                }
                this.dir = 0;
                this.destIndex = 0;
                this.replIndex = 0;
                this.srcIndex = 0;
                this.newLength_ = 0;
                this.oldLength_ = 0;
                this.remaining = 0;
                this.index = 0;
            } else if (n < n3 + n2) {
                return 0;
            }
            while (this.next(false)) {
                if (bl) {
                    n2 = this.srcIndex;
                    n3 = this.oldLength_;
                } else {
                    n2 = this.destIndex;
                    n3 = this.newLength_;
                }
                if (n < n2 + n3) {
                    return 0;
                }
                int n6 = this.remaining;
                if (n6 <= 1) continue;
                if (n < n2 + n6 * n3) {
                    n2 = (n - n2) / n3;
                    this.srcIndex += this.oldLength_ * n2;
                    n3 = this.replIndex;
                    n = this.newLength_;
                    this.replIndex = n3 + n2 * n;
                    this.destIndex += n * n2;
                    this.remaining = n6 - n2;
                    return 0;
                }
                this.oldLength_ *= n6;
                this.newLength_ *= n6;
                this.remaining = 0;
            }
            return 1;
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private boolean next(boolean bl) {
            int n;
            int n2;
            int n3 = this.dir;
            if (n3 > 0) {
                this.updateNextIndexes();
            } else {
                if (n3 < 0 && this.remaining > 0) {
                    ++this.index;
                    this.dir = 1;
                    return true;
                }
                this.dir = 1;
            }
            n3 = this.remaining;
            if (n3 >= 1) {
                if (n3 > 1) {
                    this.remaining = n3 - 1;
                    return true;
                }
                this.remaining = 0;
            }
            if ((n3 = this.index) >= this.length) {
                return this.noNext();
            }
            char[] arrc = this.array;
            this.index = n3 + 1;
            n3 = n = arrc[n3];
            if (n <= 4095) {
                int n4;
                this.changed = false;
                this.oldLength_ = n + 1;
                n3 = n;
                while ((n4 = this.index) < this.length) {
                    n3 = n = (n2 = this.array[n4]);
                    if (n2 > 4095) break;
                    this.index = n4 + 1;
                    this.oldLength_ += n + 1;
                    n3 = n;
                }
                this.newLength_ = this.oldLength_;
                if (!bl) return true;
                this.updateNextIndexes();
                n = this.index;
                if (n >= this.length) {
                    return this.noNext();
                }
                this.index = n + 1;
            }
            this.changed = true;
            if (n3 <= 28671) {
                n2 = n3 >> 12;
                n = n3 >> 9 & 7;
                n3 = (n3 & 511) + 1;
                if (!this.coarse) {
                    this.oldLength_ = n2;
                    this.newLength_ = n;
                    if (n3 <= 1) return true;
                    this.remaining = n3;
                    return true;
                }
                this.oldLength_ = n3 * n2;
                this.newLength_ = n3 * n;
            } else {
                this.oldLength_ = this.readLength(n3 >> 6 & 63);
                this.newLength_ = this.readLength(n3 & 63);
                if (!this.coarse) {
                    return true;
                }
            }
            while ((n = this.index) < this.length) {
                n3 = this.array[n];
                if (n3 <= 4095) return true;
                this.index = n + 1;
                if (n3 <= 28671) {
                    n = (n3 & 511) + 1;
                    this.oldLength_ += (n3 >> 12) * n;
                    this.newLength_ += (n3 >> 9 & 7) * n;
                    continue;
                }
                this.oldLength_ += this.readLength(n3 >> 6 & 63);
                this.newLength_ += this.readLength(n3 & 63);
            }
            return true;
        }

        private boolean noNext() {
            this.dir = 0;
            this.changed = false;
            this.newLength_ = 0;
            this.oldLength_ = 0;
            return false;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private boolean previous() {
            var1_1 = this.dir;
            if (var1_1 >= 0) {
                if (var1_1 > 0) {
                    if (this.remaining > 0) {
                        --this.index;
                        this.dir = -1;
                        return true;
                    }
                    this.updateNextIndexes();
                }
                this.dir = -1;
            }
            if ((var2_2 = this.remaining) > 0) {
                var1_1 = this.array[this.index];
                if (var2_2 <= (var1_1 & 511)) {
                    this.remaining = var2_2 + 1;
                    this.updatePreviousIndexes();
                    return true;
                }
                this.remaining = 0;
            }
            if ((var1_1 = this.index) <= 0) {
                return this.noNext();
            }
            var3_3 = this.array;
            this.index = --var1_1;
            var4_4 = var3_3[var1_1];
            if (var4_4 <= 4095) {
                this.changed = false;
                this.oldLength_ = var4_4 + '\u0001';
                while ((var2_2 = this.index) > 0 && (var1_1 = this.array[var2_2 - 1]) <= 4095) {
                    this.index = var2_2 - 1;
                    this.oldLength_ += var1_1 + 1;
                }
                this.newLength_ = this.oldLength_;
                this.updatePreviousIndexes();
                return true;
            }
            this.changed = true;
            if (var4_4 > 28671) ** GOTO lbl47
            var2_2 = var4_4 >> 12;
            var1_1 = var4_4 >> 9 & 7;
            var4_4 = (var4_4 & 511) + 1;
            if (this.coarse) {
                this.oldLength_ = var4_4 * var2_2;
                this.newLength_ = var4_4 * var1_1;
            } else {
                this.oldLength_ = var2_2;
                this.newLength_ = var1_1;
                if (var4_4 > 1) {
                    this.remaining = 1;
                }
                this.updatePreviousIndexes();
                return true;
lbl47: // 1 sources:
                if (var4_4 <= 32767) {
                    this.oldLength_ = this.readLength(var4_4 >> 6 & 63);
                    this.newLength_ = this.readLength(var4_4 & 63);
                } else {
                    do {
                        var3_3 = this.array;
                        this.index = var1_1 = this.index - 1;
                    } while ((var2_2 = var3_3[var1_1]) > 32767);
                    var1_1 = this.index;
                    this.index = var1_1 + 1;
                    this.oldLength_ = this.readLength(var2_2 >> 6 & 63);
                    this.newLength_ = this.readLength(var2_2 & 63);
                    this.index = var1_1;
                }
                if (!this.coarse) {
                    this.updatePreviousIndexes();
                    return true;
                }
            }
            while ((var2_2 = this.index) > 0 && (var1_1 = this.array[var2_2 - 1]) > 4095) {
                this.index = var2_2 - 1;
                if (var1_1 <= 28671) {
                    var2_2 = (var1_1 & 511) + 1;
                    this.oldLength_ += (var1_1 >> 12) * var2_2;
                    this.newLength_ += (var1_1 >> 9 & 7) * var2_2;
                    continue;
                }
                if (var1_1 > 32767) continue;
                var2_2 = this.index;
                this.index = var2_2 + 1;
                this.oldLength_ += this.readLength(var1_1 >> 6 & 63);
                this.newLength_ += this.readLength(var1_1 & 63);
                this.index = var2_2;
            }
            this.updatePreviousIndexes();
            return true;
        }

        private int readLength(int n) {
            if (n < 61) {
                return n;
            }
            if (n < 62) {
                char[] arrc = this.array;
                n = this.index;
                this.index = n + 1;
                return arrc[n] & 32767;
            }
            char[] arrc = this.array;
            int n2 = this.index;
            char c = arrc[n2];
            char c2 = arrc[n2 + 1];
            this.index = n2 + 2;
            return (n & 1) << 30 | (c & 32767) << 15 | c2 & 32767;
        }

        private void updateNextIndexes() {
            this.srcIndex += this.oldLength_;
            if (this.changed) {
                this.replIndex += this.newLength_;
            }
            this.destIndex += this.newLength_;
        }

        private void updatePreviousIndexes() {
            this.srcIndex -= this.oldLength_;
            if (this.changed) {
                this.replIndex -= this.newLength_;
            }
            this.destIndex -= this.newLength_;
        }

        public int destinationIndex() {
            return this.destIndex;
        }

        public int destinationIndexFromSourceIndex(int n) {
            int n2 = this.findIndex(n, true);
            if (n2 < 0) {
                return 0;
            }
            if (n2 <= 0 && n != (n2 = this.srcIndex)) {
                if (this.changed) {
                    return this.destIndex + this.newLength_;
                }
                return this.destIndex + (n - n2);
            }
            return this.destIndex;
        }

        public boolean findDestinationIndex(int n) {
            boolean bl = false;
            if (this.findIndex(n, false) == 0) {
                bl = true;
            }
            return bl;
        }

        public boolean findSourceIndex(int n) {
            boolean bl = true;
            if (this.findIndex(n, true) != 0) {
                bl = false;
            }
            return bl;
        }

        public boolean hasChange() {
            return this.changed;
        }

        public int newLength() {
            return this.newLength_;
        }

        public boolean next() {
            return this.next(this.onlyChanges_);
        }

        public int oldLength() {
            return this.oldLength_;
        }

        public int replacementIndex() {
            return this.replIndex;
        }

        public int sourceIndex() {
            return this.srcIndex;
        }

        public int sourceIndexFromDestinationIndex(int n) {
            int n2 = this.findIndex(n, false);
            if (n2 < 0) {
                return 0;
            }
            if (n2 <= 0 && n != (n2 = this.destIndex)) {
                if (this.changed) {
                    return this.srcIndex + this.oldLength_;
                }
                return this.srcIndex + (n - n2);
            }
            return this.srcIndex;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append("{ src[");
            stringBuilder.append(this.srcIndex);
            stringBuilder.append("..");
            stringBuilder.append(this.srcIndex + this.oldLength_);
            if (this.changed) {
                stringBuilder.append("] \u21dd dest[");
            } else {
                stringBuilder.append("] \u2261 dest[");
            }
            stringBuilder.append(this.destIndex);
            stringBuilder.append("..");
            stringBuilder.append(this.destIndex + this.newLength_);
            if (this.changed) {
                stringBuilder.append("], repl[");
                stringBuilder.append(this.replIndex);
                stringBuilder.append("..");
                stringBuilder.append(this.replIndex + this.newLength_);
                stringBuilder.append("] }");
            } else {
                stringBuilder.append("] (no-change) }");
            }
            return stringBuilder.toString();
        }
    }

}

