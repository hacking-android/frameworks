/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Trie;
import android.icu.text.UTF16;
import android.icu.util.RangeValueIterator;

public class TrieIterator
implements RangeValueIterator {
    private static final int BMP_INDEX_LENGTH_ = 2048;
    private static final int DATA_BLOCK_LENGTH_ = 32;
    private static final int LEAD_SURROGATE_MIN_VALUE_ = 55296;
    private static final int TRAIL_SURROGATE_COUNT_ = 1024;
    private static final int TRAIL_SURROGATE_INDEX_BLOCK_LENGTH_ = 32;
    private static final int TRAIL_SURROGATE_MIN_VALUE_ = 56320;
    private int m_currentCodepoint_;
    private int m_initialValue_;
    private int m_nextBlockIndex_;
    private int m_nextBlock_;
    private int m_nextCodepoint_;
    private int m_nextIndex_;
    private int m_nextTrailIndexOffset_;
    private int m_nextValue_;
    private Trie m_trie_;

    public TrieIterator(Trie trie) {
        if (trie != null) {
            this.m_trie_ = trie;
            this.m_initialValue_ = this.extract(this.m_trie_.getInitialValue());
            this.reset();
            return;
        }
        throw new IllegalArgumentException("Argument trie cannot be null");
    }

    private final boolean calculateNextBMPElement(RangeValueIterator.Element element) {
        int n;
        int n2 = this.m_nextValue_;
        this.m_currentCodepoint_ = n = this.m_nextCodepoint_;
        this.m_nextCodepoint_ = n + 1;
        ++this.m_nextBlockIndex_;
        if (!this.checkBlockDetail(n2)) {
            this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n2);
            return true;
        }
        while ((n = this.m_nextCodepoint_) < 65536) {
            this.m_nextIndex_ = n == 55296 ? 2048 : (n == 56320 ? n >> 5 : ++this.m_nextIndex_);
            this.m_nextBlockIndex_ = 0;
            if (this.checkBlock(n2)) continue;
            this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n2);
            return true;
        }
        this.m_nextCodepoint_ = n - 1;
        --this.m_nextBlockIndex_;
        return false;
    }

    private final void calculateNextSupplementaryElement(RangeValueIterator.Element element) {
        int n = this.m_nextValue_;
        ++this.m_nextCodepoint_;
        ++this.m_nextBlockIndex_;
        if (UTF16.getTrailSurrogate(this.m_nextCodepoint_) != '\udc00') {
            if (!this.checkNullNextTrailIndex() && !this.checkBlockDetail(n)) {
                this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
                this.m_currentCodepoint_ = this.m_nextCodepoint_;
                return;
            }
            ++this.m_nextIndex_;
            ++this.m_nextTrailIndexOffset_;
            if (!this.checkTrailBlock(n)) {
                this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
                this.m_currentCodepoint_ = this.m_nextCodepoint_;
                return;
            }
        }
        int n2 = UTF16.getLeadSurrogate(this.m_nextCodepoint_);
        while (n2 < 56320) {
            int n3 = this.m_trie_.m_index_[n2 >> 5] << 2;
            if (n3 == this.m_trie_.m_dataOffset_) {
                int n4 = this.m_initialValue_;
                if (n != n4) {
                    this.m_nextValue_ = n4;
                    this.m_nextBlock_ = n3;
                    this.m_nextBlockIndex_ = 0;
                    this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
                    this.m_currentCodepoint_ = this.m_nextCodepoint_;
                    return;
                }
                this.m_nextCodepoint_ = Character.toCodePoint((char)(n2 += 32), '\udc00');
                continue;
            }
            if (this.m_trie_.m_dataManipulate_ != null) {
                this.m_nextIndex_ = this.m_trie_.m_dataManipulate_.getFoldingOffset(this.m_trie_.getValue((n2 & 31) + n3));
                if (this.m_nextIndex_ <= 0) {
                    n3 = this.m_initialValue_;
                    if (n != n3) {
                        this.m_nextValue_ = n3;
                        this.m_nextBlock_ = this.m_trie_.m_dataOffset_;
                        this.m_nextBlockIndex_ = 0;
                        this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
                        this.m_currentCodepoint_ = this.m_nextCodepoint_;
                        return;
                    }
                    this.m_nextCodepoint_ += 1024;
                } else {
                    this.m_nextTrailIndexOffset_ = 0;
                    if (!this.checkTrailBlock(n)) {
                        this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
                        this.m_currentCodepoint_ = this.m_nextCodepoint_;
                        return;
                    }
                }
                ++n2;
                continue;
            }
            throw new NullPointerException("The field DataManipulate in this Trie is null");
        }
        this.setResult(element, this.m_currentCodepoint_, 1114112, n);
    }

    private final boolean checkBlock(int n) {
        int n2 = this.m_nextBlock_;
        this.m_nextBlock_ = this.m_trie_.m_index_[this.m_nextIndex_] << 2;
        if (this.m_nextBlock_ == n2 && (n2 = this.m_nextCodepoint_) - this.m_currentCodepoint_ >= 32) {
            this.m_nextCodepoint_ = n2 + 32;
        } else if (this.m_nextBlock_ == this.m_trie_.m_dataOffset_) {
            n2 = this.m_initialValue_;
            if (n != n2) {
                this.m_nextValue_ = n2;
                this.m_nextBlockIndex_ = 0;
                return false;
            }
            this.m_nextCodepoint_ += 32;
        } else if (!this.checkBlockDetail(n)) {
            return false;
        }
        return true;
    }

    private final boolean checkBlockDetail(int n) {
        int n2;
        while ((n2 = this.m_nextBlockIndex_++) < 32) {
            this.m_nextValue_ = this.extract(this.m_trie_.getValue(this.m_nextBlock_ + n2));
            if (this.m_nextValue_ != n) {
                return false;
            }
            ++this.m_nextCodepoint_;
        }
        return true;
    }

    private final boolean checkNullNextTrailIndex() {
        if (this.m_nextIndex_ <= 0) {
            this.m_nextCodepoint_ += 1023;
            char c = UTF16.getLeadSurrogate(this.m_nextCodepoint_);
            char c2 = this.m_trie_.m_index_[c >> 5];
            if (this.m_trie_.m_dataManipulate_ != null) {
                this.m_nextIndex_ = this.m_trie_.m_dataManipulate_.getFoldingOffset(this.m_trie_.getValue((c & 31) + (c2 << 2)));
                --this.m_nextIndex_;
                this.m_nextBlockIndex_ = 32;
                return true;
            }
            throw new NullPointerException("The field DataManipulate in this Trie is null");
        }
        return false;
    }

    private final boolean checkTrailBlock(int n) {
        while (this.m_nextTrailIndexOffset_ < 32) {
            this.m_nextBlockIndex_ = 0;
            if (!this.checkBlock(n)) {
                return false;
            }
            ++this.m_nextTrailIndexOffset_;
            ++this.m_nextIndex_;
        }
        return true;
    }

    private final void setResult(RangeValueIterator.Element element, int n, int n2, int n3) {
        element.start = n;
        element.limit = n2;
        element.value = n3;
    }

    protected int extract(int n) {
        return n;
    }

    @Override
    public final boolean next(RangeValueIterator.Element element) {
        int n = this.m_nextCodepoint_;
        if (n > 1114111) {
            return false;
        }
        if (n < 65536 && this.calculateNextBMPElement(element)) {
            return true;
        }
        this.calculateNextSupplementaryElement(element);
        return true;
    }

    @Override
    public final void reset() {
        this.m_currentCodepoint_ = 0;
        this.m_nextCodepoint_ = 0;
        this.m_nextIndex_ = 0;
        this.m_nextBlock_ = this.m_trie_.m_index_[0] << 2;
        this.m_nextValue_ = this.m_nextBlock_ == this.m_trie_.m_dataOffset_ ? this.m_initialValue_ : this.extract(this.m_trie_.getValue(this.m_nextBlock_));
        this.m_nextBlockIndex_ = 0;
        this.m_nextTrailIndexOffset_ = 32;
    }
}

