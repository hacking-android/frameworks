/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.text.UTF16;
import java.nio.ByteBuffer;
import java.util.Arrays;

public abstract class Trie {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final int BMP_INDEX_LENGTH = 2048;
    protected static final int DATA_BLOCK_LENGTH = 32;
    protected static final int HEADER_LENGTH_ = 16;
    protected static final int HEADER_OPTIONS_DATA_IS_32_BIT_ = 256;
    protected static final int HEADER_OPTIONS_INDEX_SHIFT_ = 4;
    protected static final int HEADER_OPTIONS_LATIN1_IS_LINEAR_MASK_ = 512;
    private static final int HEADER_OPTIONS_SHIFT_MASK_ = 15;
    protected static final int HEADER_SIGNATURE_ = 1416784229;
    protected static final int INDEX_STAGE_1_SHIFT_ = 5;
    protected static final int INDEX_STAGE_2_SHIFT_ = 2;
    protected static final int INDEX_STAGE_3_MASK_ = 31;
    protected static final int LEAD_INDEX_OFFSET_ = 320;
    protected static final int SURROGATE_BLOCK_BITS = 5;
    protected static final int SURROGATE_BLOCK_COUNT = 32;
    protected static final int SURROGATE_MASK_ = 1023;
    protected int m_dataLength_;
    protected DataManipulate m_dataManipulate_;
    protected int m_dataOffset_;
    protected char[] m_index_;
    private boolean m_isLatin1Linear_;
    private int m_options_;

    protected Trie(ByteBuffer byteBuffer, DataManipulate dataManipulate) {
        int n = byteBuffer.getInt();
        this.m_options_ = byteBuffer.getInt();
        if (this.checkHeader(n)) {
            this.m_dataManipulate_ = dataManipulate != null ? dataManipulate : new DefaultGetFoldingOffset();
            boolean bl = (this.m_options_ & 512) != 0;
            this.m_isLatin1Linear_ = bl;
            this.m_dataOffset_ = byteBuffer.getInt();
            this.m_dataLength_ = byteBuffer.getInt();
            this.unserialize(byteBuffer);
            return;
        }
        throw new IllegalArgumentException("ICU data file error: Trie header authentication failed, please check if you have the most updated ICU data file");
    }

    protected Trie(char[] arrc, int n, DataManipulate dataManipulate) {
        this.m_options_ = n;
        this.m_dataManipulate_ = dataManipulate != null ? dataManipulate : new DefaultGetFoldingOffset();
        boolean bl = (this.m_options_ & 512) != 0;
        this.m_isLatin1Linear_ = bl;
        this.m_index_ = arrc;
        this.m_dataOffset_ = this.m_index_.length;
    }

    private final boolean checkHeader(int n) {
        if (n != 1416784229) {
            return false;
        }
        n = this.m_options_;
        return (n & 15) == 5 && (n >> 4 & 15) == 2;
        {
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Trie)) {
            return false;
        }
        object = (Trie)object;
        if (this.m_isLatin1Linear_ != ((Trie)object).m_isLatin1Linear_ || this.m_options_ != ((Trie)object).m_options_ || this.m_dataLength_ != ((Trie)object).m_dataLength_ || !Arrays.equals(this.m_index_, ((Trie)object).m_index_)) {
            bl = false;
        }
        return bl;
    }

    protected final int getBMPOffset(char c) {
        int n = c >= '\ud800' && c <= '\udbff' ? this.getRawOffset(320, c) : this.getRawOffset(0, c);
        return n;
    }

    protected final int getCodePointOffset(int n) {
        if (n < 0) {
            return -1;
        }
        if (n < 55296) {
            return this.getRawOffset(0, (char)n);
        }
        if (n < 65536) {
            return this.getBMPOffset((char)n);
        }
        if (n <= 1114111) {
            return this.getSurrogateOffset(UTF16.getLeadSurrogate(n), (char)(n & 1023));
        }
        return -1;
    }

    protected abstract int getInitialValue();

    protected final int getLeadOffset(char c) {
        return this.getRawOffset(0, c);
    }

    protected final int getRawOffset(int n, char c) {
        return (this.m_index_[(c >> 5) + n] << 2) + (c & 31);
    }

    public int getSerializedDataSize() {
        int n;
        int n2 = 16 + (this.m_dataOffset_ << 1);
        if (this.isCharTrie()) {
            n = n2 + (this.m_dataLength_ << 1);
        } else {
            n = n2;
            if (this.isIntTrie()) {
                n = n2 + (this.m_dataLength_ << 2);
            }
        }
        return n;
    }

    protected abstract int getSurrogateOffset(char var1, char var2);

    protected abstract int getValue(int var1);

    public int hashCode() {
        return 42;
    }

    protected final boolean isCharTrie() {
        boolean bl = (this.m_options_ & 256) == 0;
        return bl;
    }

    protected final boolean isIntTrie() {
        boolean bl = (this.m_options_ & 256) != 0;
        return bl;
    }

    public final boolean isLatin1Linear() {
        return this.m_isLatin1Linear_;
    }

    protected void unserialize(ByteBuffer byteBuffer) {
        this.m_index_ = ICUBinary.getChars(byteBuffer, this.m_dataOffset_, 0);
    }

    public static interface DataManipulate {
        public int getFoldingOffset(int var1);
    }

    private static class DefaultGetFoldingOffset
    implements DataManipulate {
        private DefaultGetFoldingOffset() {
        }

        @Override
        public int getFoldingOffset(int n) {
            return n;
        }
    }

}

