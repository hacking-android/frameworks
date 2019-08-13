/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.RawCollationKey;

public final class CollationKey
implements Comparable<CollationKey> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MERGE_SEPERATOR_ = 2;
    private int m_hashCode_;
    private byte[] m_key_;
    private int m_length_;
    private String m_source_;

    public CollationKey(String string, RawCollationKey rawCollationKey) {
        this.m_source_ = string;
        this.m_length_ = rawCollationKey.size - 1;
        this.m_key_ = rawCollationKey.releaseBytes();
        this.m_hashCode_ = 0;
    }

    public CollationKey(String string, byte[] arrby) {
        this(string, arrby, -1);
    }

    private CollationKey(String string, byte[] arrby, int n) {
        this.m_source_ = string;
        this.m_key_ = arrby;
        this.m_hashCode_ = 0;
        this.m_length_ = n;
    }

    private int getLength() {
        int n;
        int n2 = this.m_length_;
        if (n2 >= 0) {
            return n2;
        }
        int n3 = this.m_key_.length;
        n2 = 0;
        do {
            n = n3;
            if (n2 >= n3) break;
            if (this.m_key_[n2] == 0) {
                n = n2;
                break;
            }
            ++n2;
        } while (true);
        this.m_length_ = n;
        return this.m_length_;
    }

    @Override
    public int compareTo(CollationKey collationKey) {
        int n = 0;
        int n2;
        int n3;
        while ((n3 = this.m_key_[n] & 255) >= (n2 = collationKey.m_key_[n] & 255)) {
            if (n3 > n2) {
                return 1;
            }
            if (n3 == 0) {
                return 0;
            }
            ++n;
        }
        return -1;
    }

    public boolean equals(CollationKey collationKey) {
        if (this == collationKey) {
            return true;
        }
        if (collationKey == null) {
            return false;
        }
        int n = 0;
        byte[] arrby;
        while ((arrby = this.m_key_)[n] == collationKey.m_key_[n]) {
            if (arrby[n] == 0) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public boolean equals(Object object) {
        if (!(object instanceof CollationKey)) {
            return false;
        }
        return this.equals((CollationKey)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public CollationKey getBound(int n, int n2) {
        byte[] arrby;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = n2;
        if (n2 > 0) {
            int n8 = n2;
            n2 = n4;
            do {
                arrby = this.m_key_;
                n3 = n2;
                n5 = n6;
                n7 = n8;
                if (n2 >= arrby.length) break;
                n3 = n2;
                n5 = n6;
                n7 = n8--;
                if (arrby[n2] == 0) break;
                n3 = n2 + 1;
                if (arrby[n2] == 1) {
                    n5 = n6 + 1;
                    if (n8 != 0 && n3 != arrby.length && arrby[n3] != 0) {
                        n2 = n3;
                        n6 = n5;
                        continue;
                    }
                    --n3;
                    n7 = n8;
                    break;
                }
                n2 = n3;
            } while (true);
        }
        if (n7 <= 0) {
            arrby = new byte[n3 + n + 1];
            System.arraycopy((byte[])this.m_key_, (int)0, (byte[])arrby, (int)0, (int)n3);
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) throw new IllegalArgumentException("Illegal boundType argument");
                    n = n3 + 1;
                    arrby[n3] = (byte)-1;
                    n3 = n + 1;
                    arrby[n] = (byte)-1;
                } else {
                    arrby[n3] = (byte)2;
                    ++n3;
                }
            }
            arrby[n3] = (byte)(false ? 1 : 0);
            return new CollationKey(null, arrby, n3);
        }
        arrby = new StringBuilder();
        arrby.append("Source collation key has only ");
        arrby.append(n5);
        arrby.append(" strength level. Call getBound() again  with noOfLevels < ");
        arrby.append(n5);
        throw new IllegalArgumentException(arrby.toString());
    }

    public String getSourceString() {
        return this.m_source_;
    }

    public int hashCode() {
        if (this.m_hashCode_ == 0) {
            Object object = this.m_key_;
            if (object == null) {
                this.m_hashCode_ = 1;
            } else {
                byte[] arrby;
                object = new StringBuilder(((byte[])object).length >> 1);
                int n = 0;
                while ((arrby = this.m_key_)[n] != 0 && arrby[n + 1] != 0) {
                    byte by = arrby[n];
                    ((StringBuilder)object).append((char)(arrby[n + 1] & 255 | by << 8));
                    n += 2;
                }
                arrby = this.m_key_;
                if (arrby[n] != 0) {
                    ((StringBuilder)object).append((char)(arrby[n] << 8));
                }
                this.m_hashCode_ = ((StringBuilder)object).toString().hashCode();
            }
        }
        return this.m_hashCode_;
    }

    public CollationKey merge(CollationKey collationKey) {
        if (collationKey != null && collationKey.getLength() != 0) {
            byte[] arrby = new byte[this.getLength() + collationKey.getLength() + 2];
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            block0 : do {
                byte[] arrby2;
                if ((arrby2 = this.m_key_)[n2] >= 0 && arrby2[n2] < 2) {
                    int n4 = n + 1;
                    arrby[n] = (byte)2;
                    n = n4;
                    do {
                        if ((arrby2 = collationKey.m_key_)[n3] >= 0 && arrby2[n3] < 2) {
                            if (this.m_key_[n2] == 1 && arrby2[n3] == 1) {
                                ++n2;
                                ++n3;
                                n4 = n + 1;
                                arrby[n] = (byte)(true ? 1 : 0);
                                n = n4;
                                continue block0;
                            }
                            n4 = this.m_length_ - n2;
                            if (n4 > 0) {
                                System.arraycopy((byte[])this.m_key_, (int)n2, (byte[])arrby, (int)n, (int)n4);
                                n2 = n + n4;
                            } else {
                                n4 = collationKey.m_length_ - n3;
                                n2 = n;
                                if (n4 > 0) {
                                    System.arraycopy((byte[])collationKey.m_key_, (int)n3, (byte[])arrby, (int)n, (int)n4);
                                    n2 = n + n4;
                                }
                            }
                            arrby[n2] = (byte)(false ? 1 : 0);
                            return new CollationKey(null, arrby, n2);
                        }
                        arrby[n] = collationKey.m_key_[n3];
                        ++n;
                        ++n3;
                    } while (true);
                }
                arrby[n] = this.m_key_[n2];
                ++n;
                ++n2;
            } while (true);
        }
        throw new IllegalArgumentException("CollationKey argument can not be null or of 0 length");
    }

    public byte[] toByteArray() {
        int n = this.getLength() + 1;
        byte[] arrby = new byte[n];
        System.arraycopy((byte[])this.m_key_, (int)0, (byte[])arrby, (int)0, (int)n);
        return arrby;
    }

    public static final class BoundMode {
        @Deprecated
        public static final int COUNT = 3;
        public static final int LOWER = 0;
        public static final int UPPER = 1;
        public static final int UPPER_LONG = 2;

        private BoundMode() {
        }
    }

}

