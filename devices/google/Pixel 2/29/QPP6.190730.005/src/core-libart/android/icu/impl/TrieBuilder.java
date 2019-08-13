/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.util.Arrays;

public class TrieBuilder {
    protected static final int BMP_INDEX_LENGTH_ = 2048;
    public static final int DATA_BLOCK_LENGTH = 32;
    protected static final int DATA_GRANULARITY_ = 4;
    protected static final int INDEX_SHIFT_ = 2;
    protected static final int MASK_ = 31;
    private static final int MAX_BUILD_TIME_DATA_LENGTH_ = 1115168;
    protected static final int MAX_DATA_LENGTH_ = 262144;
    protected static final int MAX_INDEX_LENGTH_ = 34816;
    protected static final int OPTIONS_DATA_IS_32_BIT_ = 256;
    protected static final int OPTIONS_INDEX_SHIFT_ = 4;
    protected static final int OPTIONS_LATIN1_IS_LINEAR_ = 512;
    protected static final int SHIFT_ = 5;
    protected static final int SURROGATE_BLOCK_COUNT_ = 32;
    protected int m_dataCapacity_;
    protected int m_dataLength_;
    protected int m_indexLength_;
    protected int[] m_index_ = new int[34816];
    protected boolean m_isCompacted_;
    protected boolean m_isLatin1Linear_;
    protected int[] m_map_;

    protected TrieBuilder() {
        this.m_map_ = new int[34849];
        this.m_isLatin1Linear_ = false;
        this.m_isCompacted_ = false;
        this.m_indexLength_ = 34816;
    }

    protected TrieBuilder(TrieBuilder trieBuilder) {
        this.m_indexLength_ = trieBuilder.m_indexLength_;
        System.arraycopy(trieBuilder.m_index_, 0, this.m_index_, 0, this.m_indexLength_);
        this.m_dataCapacity_ = trieBuilder.m_dataCapacity_;
        this.m_dataLength_ = trieBuilder.m_dataLength_;
        this.m_map_ = new int[trieBuilder.m_map_.length];
        int[] arrn = trieBuilder.m_map_;
        int[] arrn2 = this.m_map_;
        System.arraycopy(arrn, 0, arrn2, 0, arrn2.length);
        this.m_isLatin1Linear_ = trieBuilder.m_isLatin1Linear_;
        this.m_isCompacted_ = trieBuilder.m_isCompacted_;
    }

    protected static final boolean equal_int(int[] arrn, int n, int n2, int n3) {
        while (n3 > 0 && arrn[n] == arrn[n2]) {
            ++n;
            ++n2;
            --n3;
        }
        boolean bl = n3 == 0;
        return bl;
    }

    protected static final int findSameIndexBlock(int[] arrn, int n, int n2) {
        for (int i = 2048; i < n; i += 32) {
            if (!TrieBuilder.equal_int(arrn, i, n2, 32)) continue;
            return i;
        }
        return n;
    }

    protected void findUnusedBlocks() {
        Arrays.fill(this.m_map_, 255);
        for (int i = 0; i < this.m_indexLength_; ++i) {
            this.m_map_[Math.abs((int)this.m_index_[i]) >> 5] = 0;
        }
        this.m_map_[0] = 0;
    }

    public boolean isInZeroBlock(int n) {
        boolean bl = this.m_isCompacted_;
        boolean bl2 = true;
        if (!bl && n <= 1114111 && n >= 0) {
            if (this.m_index_[n >> 5] != 0) {
                bl2 = false;
            }
            return bl2;
        }
        return true;
    }

    public static interface DataManipulate {
        public int getFoldedValue(int var1, int var2);
    }

}

