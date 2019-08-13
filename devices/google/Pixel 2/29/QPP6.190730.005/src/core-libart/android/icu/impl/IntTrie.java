/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.impl.Trie;
import android.icu.text.UTF16;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class IntTrie
extends Trie {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int[] m_data_;
    private int m_initialValue_;

    public IntTrie(int n, int n2, Trie.DataManipulate dataManipulate) {
        super(new char[2080], 512, dataManipulate);
        int n3 = 256;
        if (n2 != n) {
            n3 = 256 + 32;
        }
        this.m_data_ = new int[n3];
        this.m_dataLength_ = n3;
        this.m_initialValue_ = n;
        for (n3 = 0; n3 < 256; ++n3) {
            this.m_data_[n3] = n;
        }
        if (n2 != n) {
            n3 = (char)(256 >> 2);
            for (n = 1728; n < 1760; ++n) {
                this.m_index_[n] = (char)n3;
            }
            for (n = 256; n < 256 + 32; ++n) {
                this.m_data_[n] = n2;
            }
        }
    }

    public IntTrie(ByteBuffer byteBuffer, Trie.DataManipulate dataManipulate) throws IOException {
        super(byteBuffer, dataManipulate);
        if (this.isIntTrie()) {
            return;
        }
        throw new IllegalArgumentException("Data given does not belong to a int trie.");
    }

    IntTrie(char[] arrc, int[] arrn, int n, int n2, Trie.DataManipulate dataManipulate) {
        super(arrc, n2, dataManipulate);
        this.m_data_ = arrn;
        this.m_dataLength_ = this.m_data_.length;
        this.m_initialValue_ = n;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object) && object instanceof IntTrie) {
            object = (IntTrie)object;
            return this.m_initialValue_ == ((IntTrie)object).m_initialValue_ && Arrays.equals(this.m_data_, ((IntTrie)object).m_data_);
            {
            }
        }
        return false;
    }

    public final int getBMPValue(char c) {
        return this.m_data_[this.getBMPOffset(c)];
    }

    public final int getCodePointValue(int n) {
        if (n >= 0 && n < 55296) {
            char c = this.m_index_[n >> 5];
            return this.m_data_[(c << 2) + (n & 31)];
        }
        n = (n = this.getCodePointOffset(n)) >= 0 ? this.m_data_[n] : this.m_initialValue_;
        return n;
    }

    @Override
    protected final int getInitialValue() {
        return this.m_initialValue_;
    }

    public final int getLatin1LinearValue(char c) {
        return this.m_data_[c + 32];
    }

    public final int getLeadValue(char c) {
        return this.m_data_[this.getLeadOffset(c)];
    }

    @Override
    protected final int getSurrogateOffset(char c, char c2) {
        if (this.m_dataManipulate_ != null) {
            int n = this.m_dataManipulate_.getFoldingOffset(this.getLeadValue(c));
            if (n > 0) {
                return this.getRawOffset(n, (char)(c2 & 1023));
            }
            return -1;
        }
        throw new NullPointerException("The field DataManipulate in this Trie is null");
    }

    public final int getSurrogateValue(char c, char c2) {
        if (UTF16.isLeadSurrogate(c) && UTF16.isTrailSurrogate(c2)) {
            int n = this.getSurrogateOffset(c, c2);
            if (n > 0) {
                return this.m_data_[n];
            }
            return this.m_initialValue_;
        }
        throw new IllegalArgumentException("Argument characters do not form a supplementary character");
    }

    public final int getTrailValue(int n, char c) {
        if (this.m_dataManipulate_ != null) {
            if ((n = this.m_dataManipulate_.getFoldingOffset(n)) > 0) {
                return this.m_data_[this.getRawOffset(n, (char)(c & 1023))];
            }
            return this.m_initialValue_;
        }
        throw new NullPointerException("The field DataManipulate in this Trie is null");
    }

    @Override
    protected final int getValue(int n) {
        return this.m_data_[n];
    }

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    protected final void unserialize(ByteBuffer byteBuffer) {
        super.unserialize(byteBuffer);
        this.m_data_ = ICUBinary.getInts(byteBuffer, this.m_dataLength_, 0);
        this.m_initialValue_ = this.m_data_[0];
    }
}

