/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public final class StringTokenizer
implements Enumeration<Object> {
    private static final UnicodeSet DEFAULT_DELIMITERS_ = new UnicodeSet(9, 10, 12, 13, 32, 32);
    private static final UnicodeSet EMPTY_DELIMITER_ = UnicodeSet.EMPTY;
    private static final int TOKEN_SIZE_ = 100;
    private boolean[] delims;
    private boolean m_coalesceDelimiters_;
    private UnicodeSet m_delimiters_;
    private int m_length_;
    private int m_nextOffset_;
    private boolean m_returnDelimiters_;
    private String m_source_;
    private int[] m_tokenLimit_;
    private int m_tokenOffset_;
    private int m_tokenSize_;
    private int[] m_tokenStart_;

    public StringTokenizer(String string) {
        this(string, DEFAULT_DELIMITERS_, false, false);
    }

    public StringTokenizer(String string, UnicodeSet unicodeSet) {
        this(string, unicodeSet, false, false);
    }

    public StringTokenizer(String string, UnicodeSet unicodeSet, boolean bl) {
        this(string, unicodeSet, bl, false);
    }

    @Deprecated
    public StringTokenizer(String string, UnicodeSet unicodeSet, boolean bl, boolean bl2) {
        this.m_source_ = string;
        this.m_length_ = string.length();
        this.m_delimiters_ = unicodeSet == null ? EMPTY_DELIMITER_ : unicodeSet;
        this.m_returnDelimiters_ = bl;
        this.m_coalesceDelimiters_ = bl2;
        this.m_tokenOffset_ = -1;
        this.m_tokenSize_ = -1;
        if (this.m_length_ == 0) {
            this.m_nextOffset_ = -1;
        } else {
            this.m_nextOffset_ = 0;
            if (!bl) {
                this.m_nextOffset_ = this.getNextNonDelimiter(0);
            }
        }
    }

    public StringTokenizer(String string, String string2) {
        this(string, string2, false, false);
    }

    public StringTokenizer(String string, String string2, boolean bl) {
        this(string, string2, bl, false);
    }

    @Deprecated
    public StringTokenizer(String string, String string2, boolean bl, boolean bl2) {
        this.m_delimiters_ = EMPTY_DELIMITER_;
        if (string2 != null && string2.length() > 0) {
            this.m_delimiters_ = new UnicodeSet();
            this.m_delimiters_.addAll(string2);
            this.checkDelimiters();
        }
        this.m_coalesceDelimiters_ = bl2;
        this.m_source_ = string;
        this.m_length_ = string.length();
        this.m_returnDelimiters_ = bl;
        this.m_tokenOffset_ = -1;
        this.m_tokenSize_ = -1;
        if (this.m_length_ == 0) {
            this.m_nextOffset_ = -1;
        } else {
            this.m_nextOffset_ = 0;
            if (!bl) {
                this.m_nextOffset_ = this.getNextNonDelimiter(0);
            }
        }
    }

    private int getNextDelimiter(int n) {
        if (n >= 0) {
            int n2;
            n = n2 = n;
            if (this.delims == null) {
                n = n2;
                while (!this.m_delimiters_.contains(n2 = UTF16.charAt(this.m_source_, n))) {
                    n = n2 = n + 1;
                    if (n2 < this.m_length_) continue;
                    n = n2;
                    break;
                }
            } else {
                boolean[] arrbl;
                while ((n2 = UTF16.charAt(this.m_source_, n)) >= (arrbl = this.delims).length || !arrbl[n2]) {
                    n = n2 = n + 1;
                    if (n2 < this.m_length_) continue;
                    n = n2;
                    break;
                }
            }
            if (n < this.m_length_) {
                return n;
            }
        }
        return -1 - this.m_length_;
    }

    private int getNextNonDelimiter(int n) {
        if (n >= 0) {
            int n2 = n;
            if (this.delims == null) {
                while (this.m_delimiters_.contains(n2 = UTF16.charAt(this.m_source_, n))) {
                    n = n2 = n + 1;
                    if (n2 < this.m_length_) continue;
                    n = n2;
                    break;
                }
            } else {
                do {
                    int n3 = UTF16.charAt(this.m_source_, n2);
                    boolean[] arrbl = this.delims;
                    n = n2;
                    if (n3 >= arrbl.length) break;
                    if (!arrbl[n3]) {
                        n = n2;
                        break;
                    }
                    n2 = n = n2 + 1;
                } while (n < this.m_length_);
            }
            if (n < this.m_length_) {
                return n;
            }
        }
        return -1 - this.m_length_;
    }

    void checkDelimiters() {
        UnicodeSet unicodeSet = this.m_delimiters_;
        if (unicodeSet != null && unicodeSet.size() != 0) {
            unicodeSet = this.m_delimiters_;
            int n = unicodeSet.getRangeEnd(unicodeSet.getRangeCount() - 1);
            if (n < 127) {
                int n2;
                this.delims = new boolean[n + 1];
                n = 0;
                while (-1 != (n2 = this.m_delimiters_.charAt(n))) {
                    this.delims[n2] = true;
                    ++n;
                }
            } else {
                this.delims = null;
            }
        } else {
            this.delims = new boolean[0];
        }
    }

    public int countTokens() {
        int n = 0;
        int n2 = 0;
        if (this.hasMoreTokens()) {
            n = this.m_tokenOffset_;
            if (n >= 0) {
                return this.m_tokenSize_ - n;
            }
            n = n2;
            if (this.m_tokenStart_ == null) {
                this.m_tokenStart_ = new int[100];
                this.m_tokenLimit_ = new int[100];
                n = n2;
            }
            do {
                int n3;
                Object[] arrobject;
                if (this.m_tokenStart_.length == n) {
                    arrobject = this.m_tokenStart_;
                    int[] arrn = this.m_tokenLimit_;
                    n3 = arrobject.length;
                    n2 = n3 + 100;
                    this.m_tokenStart_ = new int[n2];
                    this.m_tokenLimit_ = new int[n2];
                    System.arraycopy(arrobject, 0, this.m_tokenStart_, 0, n3);
                    System.arraycopy(arrn, 0, this.m_tokenLimit_, 0, n3);
                }
                arrobject = this.m_tokenStart_;
                arrobject[n] = n2 = this.m_nextOffset_;
                if (this.m_returnDelimiters_) {
                    n2 = UTF16.charAt(this.m_source_, n2);
                    arrobject = this.delims;
                    boolean bl = arrobject == null ? this.m_delimiters_.contains(n2) : n2 < arrobject.length && arrobject[n2] != 0;
                    if (bl) {
                        if (this.m_coalesceDelimiters_) {
                            this.m_tokenLimit_[n] = this.getNextNonDelimiter(this.m_nextOffset_);
                        } else {
                            n2 = n3 = this.m_nextOffset_ + 1;
                            if (n3 == this.m_length_) {
                                n2 = -1;
                            }
                            this.m_tokenLimit_[n] = n2;
                        }
                    } else {
                        this.m_tokenLimit_[n] = this.getNextDelimiter(this.m_nextOffset_);
                    }
                    this.m_nextOffset_ = this.m_tokenLimit_[n];
                } else {
                    this.m_tokenLimit_[n] = this.getNextDelimiter(n2);
                    this.m_nextOffset_ = this.getNextNonDelimiter(this.m_tokenLimit_[n]);
                }
                n = n2 = n + 1;
            } while (this.m_nextOffset_ >= 0);
            this.m_tokenOffset_ = 0;
            this.m_tokenSize_ = n2;
            this.m_nextOffset_ = this.m_tokenStart_[0];
            n = n2;
        }
        return n;
    }

    @Override
    public boolean hasMoreElements() {
        return this.hasMoreTokens();
    }

    public boolean hasMoreTokens() {
        boolean bl = this.m_nextOffset_ >= 0;
        return bl;
    }

    @Override
    public Object nextElement() {
        return this.nextToken();
    }

    public String nextToken() {
        int n = this.m_tokenOffset_;
        boolean bl = true;
        if (n < 0) {
            n = this.m_nextOffset_;
            if (n >= 0) {
                String string;
                if (this.m_returnDelimiters_) {
                    n = UTF16.charAt(this.m_source_, n);
                    Object object = this.delims;
                    if (object == null) {
                        bl = this.m_delimiters_.contains(n);
                    } else if (n >= ((boolean[])object).length || !object[n]) {
                        bl = false;
                    }
                    if (bl) {
                        if (this.m_coalesceDelimiters_) {
                            n = this.getNextNonDelimiter(this.m_nextOffset_);
                        } else {
                            int n2;
                            n = n2 = this.m_nextOffset_ + UTF16.getCharCount(n);
                            if (n2 == this.m_length_) {
                                n = -1;
                            }
                        }
                    } else {
                        n = this.getNextDelimiter(this.m_nextOffset_);
                    }
                    object = n < 0 ? this.m_source_.substring(this.m_nextOffset_) : this.m_source_.substring(this.m_nextOffset_, n);
                    this.m_nextOffset_ = n;
                    return object;
                }
                if ((n = this.getNextDelimiter(n)) < 0) {
                    string = this.m_source_.substring(this.m_nextOffset_);
                    this.m_nextOffset_ = n;
                } else {
                    string = this.m_source_.substring(this.m_nextOffset_, n);
                    this.m_nextOffset_ = this.getNextNonDelimiter(n);
                }
                return string;
            }
            throw new NoSuchElementException("No more tokens in String");
        }
        if (n < this.m_tokenSize_) {
            Object object = this.m_tokenLimit_;
            object = object[n] >= 0 ? this.m_source_.substring(this.m_tokenStart_[n], object[n]) : this.m_source_.substring(this.m_tokenStart_[n]);
            ++this.m_tokenOffset_;
            this.m_nextOffset_ = -1;
            n = this.m_tokenOffset_;
            if (n < this.m_tokenSize_) {
                this.m_nextOffset_ = this.m_tokenStart_[n];
            }
            return object;
        }
        throw new NoSuchElementException("No more tokens in String");
    }

    public String nextToken(UnicodeSet unicodeSet) {
        this.m_delimiters_ = unicodeSet;
        this.checkDelimiters();
        this.m_tokenOffset_ = -1;
        this.m_tokenSize_ = -1;
        if (!this.m_returnDelimiters_) {
            this.m_nextOffset_ = this.getNextNonDelimiter(this.m_nextOffset_);
        }
        return this.nextToken();
    }

    public String nextToken(String string) {
        this.m_delimiters_ = EMPTY_DELIMITER_;
        if (string != null && string.length() > 0) {
            this.m_delimiters_ = new UnicodeSet();
            this.m_delimiters_.addAll(string);
        }
        return this.nextToken(this.m_delimiters_);
    }
}

