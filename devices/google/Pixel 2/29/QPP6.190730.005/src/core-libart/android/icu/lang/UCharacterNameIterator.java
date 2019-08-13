/*
 * Decompiled with CFR 0.145.
 */
package android.icu.lang;

import android.icu.impl.UCharacterName;
import android.icu.util.ValueIterator;

class UCharacterNameIterator
implements ValueIterator {
    private static char[] GROUP_LENGTHS_;
    private static char[] GROUP_OFFSETS_;
    private int m_algorithmIndex_ = -1;
    private int m_choice_;
    private int m_current_;
    private int m_groupIndex_ = -1;
    private int m_limit_;
    private UCharacterName m_name_;
    private int m_start_;

    static {
        GROUP_OFFSETS_ = new char[33];
        GROUP_LENGTHS_ = new char[33];
    }

    protected UCharacterNameIterator(UCharacterName uCharacterName, int n) {
        if (uCharacterName != null) {
            this.m_name_ = uCharacterName;
            this.m_choice_ = n;
            this.m_start_ = 0;
            this.m_limit_ = 1114112;
            this.m_current_ = this.m_start_;
            return;
        }
        throw new IllegalArgumentException("UCharacterName name argument cannot be null. Missing unames.icu?");
    }

    private boolean iterateExtended(ValueIterator.Element element, int n) {
        int n2;
        while ((n2 = ++this.m_current_) < n) {
            String string = this.m_name_.getExtendedOr10Name(n2);
            if (string == null || string.length() <= 0) continue;
            element.integer = this.m_current_;
            element.value = string;
            return false;
        }
        return true;
    }

    private boolean iterateGroup(ValueIterator.Element element, int n) {
        int n2;
        if (this.m_groupIndex_ < 0) {
            this.m_groupIndex_ = this.m_name_.getGroup(this.m_current_);
        }
        while (this.m_groupIndex_ < this.m_name_.m_groupcount_ && (n2 = this.m_current_) < n) {
            int n3;
            if ((n2 = UCharacterName.getCodepointMSB(n2)) == (n3 = this.m_name_.getGroupMSB(this.m_groupIndex_))) {
                if (n2 == UCharacterName.getCodepointMSB(n - 1)) {
                    return this.iterateSingleGroup(element, n);
                }
                if (!this.iterateSingleGroup(element, UCharacterName.getGroupLimit(n3))) {
                    return false;
                }
                ++this.m_groupIndex_;
                continue;
            }
            if (n2 > n3) {
                ++this.m_groupIndex_;
                continue;
            }
            n2 = n3 = UCharacterName.getGroupMin(n3);
            if (n3 > n) {
                n2 = n;
            }
            if (this.m_choice_ == 2 && !this.iterateExtended(element, n2)) {
                return false;
            }
            this.m_current_ = n2;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean iterateSingleGroup(ValueIterator.Element element, int n) {
        char[] arrc = GROUP_OFFSETS_;
        synchronized (arrc) {
            char[] arrc2 = GROUP_LENGTHS_;
            synchronized (arrc2) {
                int n2 = this.m_name_.getGroupLengths(this.m_groupIndex_, GROUP_OFFSETS_, GROUP_LENGTHS_);
                do {
                    String string;
                    block13 : {
                        String string2;
                        block12 : {
                            if (this.m_current_ >= n) {
                                return true;
                            }
                            int n3 = UCharacterName.getGroupOffset(this.m_current_);
                            string2 = this.m_name_.getGroupName(GROUP_OFFSETS_[n3] + n2, GROUP_LENGTHS_[n3], this.m_choice_);
                            if (string2 == null) break block12;
                            string = string2;
                            if (string2.length() != 0) break block13;
                        }
                        string = string2;
                        if (this.m_choice_ == 2) {
                            string = this.m_name_.getExtendedName(this.m_current_);
                        }
                    }
                    if (string != null && string.length() > 0) {
                        element.integer = this.m_current_;
                        element.value = string;
                        return false;
                    }
                    ++this.m_current_;
                } while (true);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean next(ValueIterator.Element element) {
        if (this.m_current_ >= this.m_limit_) {
            return false;
        }
        int n = this.m_choice_;
        if ((n == 0 || n == 2) && this.m_algorithmIndex_ < (n = this.m_name_.getAlgorithmLength())) {
            int n2;
            while ((n2 = ++this.m_algorithmIndex_) < n && (n2 < 0 || this.m_name_.getAlgorithmEnd(n2) < this.m_current_)) {
            }
            n2 = this.m_algorithmIndex_;
            if (n2 < n) {
                if (this.m_current_ < (n2 = this.m_name_.getAlgorithmStart(n2))) {
                    n = n2;
                    if (this.m_limit_ <= n2) {
                        n = this.m_limit_;
                    }
                    if (!this.iterateGroup(element, n)) {
                        ++this.m_current_;
                        return true;
                    }
                }
                if ((n = this.m_current_++) >= this.m_limit_) {
                    return false;
                }
                element.integer = n;
                element.value = this.m_name_.getAlgorithmName(this.m_algorithmIndex_, n);
                this.m_groupIndex_ = -1;
                return true;
            }
        }
        if (!this.iterateGroup(element, this.m_limit_)) {
            ++this.m_current_;
            return true;
        }
        if (this.m_choice_ == 2 && !this.iterateExtended(element, this.m_limit_)) {
            ++this.m_current_;
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        this.m_current_ = this.m_start_;
        this.m_groupIndex_ = -1;
        this.m_algorithmIndex_ = -1;
    }

    @Override
    public void setRange(int n, int n2) {
        if (n < n2) {
            this.m_start_ = n < 0 ? 0 : n;
            this.m_limit_ = n2 > 1114112 ? 1114112 : n2;
            this.m_current_ = this.m_start_;
            return;
        }
        throw new IllegalArgumentException("start or limit has to be valid Unicode codepoints and start < limit");
    }
}

