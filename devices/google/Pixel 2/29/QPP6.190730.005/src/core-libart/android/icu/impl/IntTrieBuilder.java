/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.IntTrie;
import android.icu.impl.Trie;
import android.icu.impl.TrieBuilder;
import android.icu.text.UTF16;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class IntTrieBuilder
extends TrieBuilder {
    protected int[] m_data_;
    protected int m_initialValue_;
    private int m_leadUnitValue_;

    public IntTrieBuilder(IntTrieBuilder intTrieBuilder) {
        super(intTrieBuilder);
        this.m_data_ = new int[this.m_dataCapacity_];
        System.arraycopy(intTrieBuilder.m_data_, 0, this.m_data_, 0, this.m_dataLength_);
        this.m_initialValue_ = intTrieBuilder.m_initialValue_;
        this.m_leadUnitValue_ = intTrieBuilder.m_leadUnitValue_;
    }

    public IntTrieBuilder(int[] arrn, int n, int n2, int n3, boolean bl) {
        if (!(n < 32 || bl && n < 1024)) {
            int n4;
            this.m_data_ = arrn != null ? arrn : new int[n];
            int n5 = n4 = 32;
            if (bl) {
                n5 = 0;
                do {
                    arrn = this.m_index_;
                    int n6 = n5 + 1;
                    arrn[n5] = n4;
                    n4 += 32;
                    if (n6 >= 8) {
                        n5 = n4;
                        break;
                    }
                    n5 = n6;
                } while (true);
            }
            this.m_dataLength_ = n5;
            Arrays.fill(this.m_data_, 0, this.m_dataLength_, n2);
            this.m_initialValue_ = n2;
            this.m_leadUnitValue_ = n3;
            this.m_dataCapacity_ = n;
            this.m_isLatin1Linear_ = bl;
            this.m_isCompacted_ = false;
            return;
        }
        throw new IllegalArgumentException("Argument maxdatalength is too small");
    }

    private int allocDataBlock() {
        int n = this.m_dataLength_;
        int n2 = n + 32;
        if (n2 > this.m_dataCapacity_) {
            return -1;
        }
        this.m_dataLength_ = n2;
        return n;
    }

    private void compact(boolean bl) {
        if (this.m_isCompacted_) {
            return;
        }
        this.findUnusedBlocks();
        int n = 32;
        if (this.m_isLatin1Linear_) {
            n = 32 + 256;
        }
        int n2 = 32;
        int n3 = 32;
        block0 : while (n3 < this.m_dataLength_) {
            int n4;
            int n5;
            int n6;
            int[] arrn;
            if (this.m_map_[n3 >>> 5] < 0) {
                n3 += 32;
                continue;
            }
            if (n3 >= n) {
                arrn = this.m_data_;
                n6 = bl ? 4 : 32;
                if ((n6 = IntTrieBuilder.findSameDataBlock(arrn, n2, n3, n6)) >= 0) {
                    this.m_map_[n3 >>> 5] = n6;
                    n3 += 32;
                    continue;
                }
            }
            if (bl && n3 >= n) {
                n6 = 28;
                do {
                    n5 = n6;
                    if (n6 > 0) {
                        n5 = n6;
                        if (!IntTrieBuilder.equal_int(this.m_data_, n2 - n6, n3, n6)) {
                            n6 -= 4;
                            continue;
                        }
                    }
                    break;
                } while (true);
            } else {
                n5 = 0;
            }
            if (n5 > 0) {
                this.m_map_[n3 >>> 5] = n2 - n5;
                n6 = n3 + n5;
                n4 = 32 - n5;
                n5 = n2;
                do {
                    n2 = n5++;
                    n3 = ++n6;
                    if (n4 <= 0) continue block0;
                    arrn = this.m_data_;
                    arrn[n5] = arrn[n6];
                    --n4;
                } while (true);
            }
            if (n2 < n3) {
                this.m_map_[n3 >>> 5] = n2;
                n4 = 32;
                n5 = n3;
                n6 = n2;
                do {
                    n2 = n6++;
                    n3 = ++n5;
                    if (n4 <= 0) continue block0;
                    arrn = this.m_data_;
                    arrn[n6] = arrn[n5];
                    --n4;
                } while (true);
            }
            this.m_map_[n3 >>> 5] = n3;
            n3 = n2 += 32;
        }
        for (n3 = 0; n3 < this.m_indexLength_; ++n3) {
            this.m_index_[n3] = this.m_map_[Math.abs(this.m_index_[n3]) >>> 5];
        }
        this.m_dataLength_ = n2;
    }

    private void fillBlock(int n, int n2, int n3, int n4, boolean bl) {
        n3 += n;
        n += n2;
        if (bl) {
            while (n < n3) {
                this.m_data_[n] = n4;
                ++n;
            }
        } else {
            for (n2 = n; n2 < n3; ++n2) {
                int[] arrn = this.m_data_;
                if (arrn[n2] != this.m_initialValue_) continue;
                arrn[n2] = n4;
            }
        }
    }

    private static final int findSameDataBlock(int[] arrn, int n, int n2, int n3) {
        for (int i = 0; i <= n - 32; i += n3) {
            if (!IntTrieBuilder.equal_int(arrn, i, n2, 32)) continue;
            return i;
        }
        return -1;
    }

    private final void fold(TrieBuilder.DataManipulate dataManipulate) {
        block12 : {
            int n;
            int[] arrn;
            int[] arrn2;
            int n2;
            block11 : {
                arrn2 = new int[32];
                arrn = this.m_index_;
                System.arraycopy(arrn, 1728, arrn2, 0, 32);
                n = 0;
                if (this.m_leadUnitValue_ == this.m_initialValue_) break block11;
                n = this.allocDataBlock();
                if (n < 0) break block12;
                this.fillBlock(n, 0, 32, this.m_leadUnitValue_, true);
                n = -n;
            }
            for (n2 = 1728; n2 < 1760; ++n2) {
                this.m_index_[n2] = n;
            }
            n2 = 2048;
            n = 65536;
            while (n < 1114112) {
                if (arrn[n >> 5] != 0) {
                    int n3 = n & -1024;
                    int n4 = IntTrieBuilder.findSameIndexBlock(arrn, n2, n3 >> 5);
                    int n5 = dataManipulate.getFoldedValue(n3, n4 + 32);
                    n = n2;
                    if (n5 != this.getValue(UTF16.getLeadSurrogate(n3))) {
                        if (this.setValue(UTF16.getLeadSurrogate(n3), n5)) {
                            n = n2;
                            if (n4 == n2) {
                                System.arraycopy(arrn, n3 >> 5, arrn, n2, 32);
                                n = n2 + 32;
                            }
                        } else {
                            throw new ArrayIndexOutOfBoundsException("Data table overflow");
                        }
                    }
                    n2 = n;
                    n = n3 += 1024;
                    continue;
                }
                n += 32;
            }
            if (n2 < 34816) {
                System.arraycopy(arrn, 2048, arrn, 2080, n2 - 2048);
                System.arraycopy(arrn2, 0, arrn, 2048, 32);
                this.m_indexLength_ = n2 + 32;
                return;
            }
            throw new ArrayIndexOutOfBoundsException("Index table overflow");
        }
        throw new IllegalStateException("Internal error: Out of memory space");
    }

    private int getDataBlock(int n) {
        int n2 = this.m_index_[n >>= 5];
        if (n2 > 0) {
            return n2;
        }
        int n3 = this.allocDataBlock();
        if (n3 < 0) {
            return -1;
        }
        this.m_index_[n] = n3;
        System.arraycopy(this.m_data_, Math.abs(n2), this.m_data_, n3, 128);
        return n3;
    }

    public int getValue(int n) {
        if (!this.m_isCompacted_ && n <= 1114111 && n >= 0) {
            int n2 = this.m_index_[n >> 5];
            return this.m_data_[Math.abs(n2) + (n & 31)];
        }
        return 0;
    }

    public int getValue(int n, boolean[] arrbl) {
        boolean bl = this.m_isCompacted_;
        boolean bl2 = true;
        if (!bl && n <= 1114111 && n >= 0) {
            int n2 = this.m_index_[n >> 5];
            if (arrbl != null) {
                if (n2 != 0) {
                    bl2 = false;
                }
                arrbl[0] = bl2;
            }
            return this.m_data_[Math.abs(n2) + (n & 31)];
        }
        if (arrbl != null) {
            arrbl[0] = true;
        }
        return 0;
    }

    public int serialize(OutputStream outputStream, boolean bl, TrieBuilder.DataManipulate dataManipulate) throws IOException {
        if (dataManipulate != null) {
            int n;
            if (!this.m_isCompacted_) {
                this.compact(false);
                this.fold(dataManipulate);
                this.compact(true);
                this.m_isCompacted_ = true;
            }
            if ((n = bl ? this.m_dataLength_ + this.m_indexLength_ : this.m_dataLength_) < 262144) {
                n = this.m_indexLength_ * 2 + 16;
                int n2 = bl ? n + this.m_dataLength_ * 2 : n + this.m_dataLength_ * 4;
                if (outputStream == null) {
                    return n2;
                }
                outputStream = new DataOutputStream(outputStream);
                ((DataOutputStream)outputStream).writeInt(1416784229);
                n = 37;
                if (!bl) {
                    n = 37 | 256;
                }
                int n3 = n;
                if (this.m_isLatin1Linear_) {
                    n3 = n | 512;
                }
                ((DataOutputStream)outputStream).writeInt(n3);
                ((DataOutputStream)outputStream).writeInt(this.m_indexLength_);
                ((DataOutputStream)outputStream).writeInt(this.m_dataLength_);
                if (bl) {
                    for (n = 0; n < this.m_indexLength_; ++n) {
                        ((DataOutputStream)outputStream).writeChar(this.m_index_[n] + this.m_indexLength_ >>> 2);
                    }
                    for (n = 0; n < this.m_dataLength_; ++n) {
                        ((DataOutputStream)outputStream).writeChar(this.m_data_[n] & 65535);
                    }
                } else {
                    for (n = 0; n < this.m_indexLength_; ++n) {
                        ((DataOutputStream)outputStream).writeChar(this.m_index_[n] >>> 2);
                    }
                    for (n = 0; n < this.m_dataLength_; ++n) {
                        ((DataOutputStream)outputStream).writeInt(this.m_data_[n]);
                    }
                }
                return n2;
            }
            throw new ArrayIndexOutOfBoundsException("Data length too small");
        }
        throw new IllegalArgumentException("Parameters can not be null");
    }

    public IntTrie serialize(TrieBuilder.DataManipulate arrc, Trie.DataManipulate dataManipulate) {
        if (arrc != null) {
            if (!this.m_isCompacted_) {
                this.compact(false);
                this.fold((TrieBuilder.DataManipulate)arrc);
                this.compact(true);
                this.m_isCompacted_ = true;
            }
            if (this.m_dataLength_ < 262144) {
                int n;
                int n2;
                arrc = new char[this.m_indexLength_];
                int[] arrn = new int[this.m_dataLength_];
                for (n = 0; n < this.m_indexLength_; ++n) {
                    arrc[n] = (char)(this.m_index_[n] >>> 2);
                }
                System.arraycopy(this.m_data_, 0, arrn, 0, this.m_dataLength_);
                n = n2 = 37 | 256;
                if (this.m_isLatin1Linear_) {
                    n = n2 | 512;
                }
                return new IntTrie(arrc, arrn, this.m_initialValue_, n, dataManipulate);
            }
            throw new ArrayIndexOutOfBoundsException("Data length too small");
        }
        throw new IllegalArgumentException("Parameters can not be null");
    }

    public boolean setRange(int n, int n2, int n3, boolean bl) {
        block14 : {
            int n4;
            int n5;
            if (this.m_isCompacted_ || n < 0 || n > 1114111 || n2 < 0 || n2 > 1114112 || n > n2) break block14;
            if (n == n2) {
                return true;
            }
            if ((n & 31) != 0) {
                n5 = this.getDataBlock(n);
                if (n5 < 0) {
                    return false;
                }
                n4 = n + 32 & -32;
                if (n4 <= n2) {
                    this.fillBlock(n5, n & 31, 32, n3, bl);
                    n = n4;
                } else {
                    this.fillBlock(n5, n & 31, n2 & 31, n3, bl);
                    return true;
                }
            }
            int n6 = n2 & 31;
            n4 = n3 == this.m_initialValue_ ? 0 : -1;
            while (n < (n2 & -32)) {
                block16 : {
                    block17 : {
                        int n7;
                        block15 : {
                            n7 = this.m_index_[n >> 5];
                            if (n7 <= 0) break block15;
                            this.fillBlock(n7, 0, 32, n3, bl);
                            n5 = n4;
                            break block16;
                        }
                        n5 = n4;
                        if (this.m_data_[-n7] == n3) break block16;
                        if (n7 == 0) break block17;
                        n5 = n4;
                        if (!bl) break block16;
                    }
                    if (n4 >= 0) {
                        this.m_index_[n >> 5] = -n4;
                        n5 = n4;
                    } else {
                        n5 = this.getDataBlock(n);
                        if (n5 < 0) {
                            return false;
                        }
                        this.m_index_[n >> 5] = -n5;
                        this.fillBlock(n5, 0, 32, n3, true);
                    }
                }
                n += 32;
                n4 = n5;
            }
            if (n6 > 0) {
                if ((n = this.getDataBlock(n)) < 0) {
                    return false;
                }
                this.fillBlock(n, 0, n6, n3, bl);
            }
            return true;
        }
        return false;
    }

    public boolean setValue(int n, int n2) {
        if (!this.m_isCompacted_ && n <= 1114111 && n >= 0) {
            int n3 = this.getDataBlock(n);
            if (n3 < 0) {
                return false;
            }
            this.m_data_[(n & 31) + n3] = n2;
            return true;
        }
        return false;
    }
}

