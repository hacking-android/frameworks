/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.impl.Trie;
import java.nio.ByteBuffer;

public class CharTrie
extends Trie {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private char[] m_data_;
    private char m_initialValue_;

    public CharTrie(int n, int n2, Trie.DataManipulate dataManipulate) {
        super(new char[2080], 512, dataManipulate);
        int n3 = 256;
        if (n2 != n) {
            n3 = 256 + 32;
        }
        this.m_data_ = new char[n3];
        this.m_dataLength_ = n3;
        this.m_initialValue_ = (char)n;
        for (n3 = 0; n3 < 256; ++n3) {
            this.m_data_[n3] = (char)n;
        }
        if (n2 != n) {
            n3 = (char)(256 >> 2);
            for (n = 1728; n < 1760; ++n) {
                this.m_index_[n] = (char)n3;
            }
            for (n = 256; n < 256 + 32; ++n) {
                this.m_data_[n] = (char)n2;
            }
        }
    }

    public CharTrie(ByteBuffer byteBuffer, Trie.DataManipulate dataManipulate) {
        super(byteBuffer, dataManipulate);
        if (this.isCharTrie()) {
            return;
        }
        throw new IllegalArgumentException("Data given does not belong to a char trie.");
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = super.equals(object);
        boolean bl2 = false;
        if (bl && object instanceof CharTrie) {
            object = (CharTrie)object;
            if (this.m_initialValue_ == ((CharTrie)object).m_initialValue_) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public final char getBMPValue(char c) {
        return this.m_data_[this.getBMPOffset(c)];
    }

    public final char getCodePointValue(int n) {
        char c;
        if (n >= 0 && n < 55296) {
            char c2 = this.m_index_[n >> 5];
            return this.m_data_[(c2 << 2) + (n & 31)];
        }
        if ((n = this.getCodePointOffset(n)) >= 0) {
            n = this.m_data_[n];
            c = n;
        } else {
            n = this.m_initialValue_;
            c = n;
        }
        return c;
    }

    @Override
    protected final int getInitialValue() {
        return this.m_initialValue_;
    }

    public final char getLatin1LinearValue(char c) {
        return this.m_data_[this.m_dataOffset_ + 32 + c];
    }

    public final char getLeadValue(char c) {
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

    public final char getSurrogateValue(char c, char c2) {
        int n = this.getSurrogateOffset(c, c2);
        if (n > 0) {
            return this.m_data_[n];
        }
        return this.m_initialValue_;
    }

    public final char getTrailValue(int n, char c) {
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
        this.m_index_ = ICUBinary.getChars(byteBuffer, this.m_dataOffset_ + this.m_dataLength_, 0);
        this.m_data_ = this.m_index_;
        this.m_initialValue_ = this.m_data_[this.m_dataOffset_];
    }
}

