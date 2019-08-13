/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.Utility;
import android.icu.util.ICUCloneNotSupportedException;

@Deprecated
public final class CompactCharArray
implements Cloneable {
    static final int BLOCKCOUNT = 32;
    static final int BLOCKMASK = 31;
    @Deprecated
    public static final int BLOCKSHIFT = 5;
    static final int INDEXCOUNT = 2048;
    static final int INDEXSHIFT = 11;
    @Deprecated
    public static final int UNICODECOUNT = 65536;
    char defaultValue;
    private int[] hashes;
    private char[] indices;
    private boolean isCompact;
    private char[] values;

    @Deprecated
    public CompactCharArray() {
        this('\u0000');
    }

    @Deprecated
    public CompactCharArray(char c) {
        int n;
        this.values = new char[65536];
        this.indices = new char[2048];
        this.hashes = new int[2048];
        for (n = 0; n < 65536; ++n) {
            this.values[n] = c;
        }
        for (n = 0; n < 2048; ++n) {
            this.indices[n] = (char)(n << 5);
            this.hashes[n] = 0;
        }
        this.isCompact = false;
        this.defaultValue = c;
    }

    @Deprecated
    public CompactCharArray(String string, String string2) {
        this(Utility.RLEStringToCharArray(string), Utility.RLEStringToCharArray(string2));
    }

    @Deprecated
    public CompactCharArray(char[] arrc, char[] arrc2) {
        if (arrc.length == 2048) {
            for (int i = 0; i < 2048; ++i) {
                if (arrc[i] < arrc2.length + 32) {
                    continue;
                }
                throw new IllegalArgumentException("Index out of bounds.");
            }
            this.indices = arrc;
            this.values = arrc2;
            this.isCompact = true;
            return;
        }
        throw new IllegalArgumentException("Index out of bounds.");
    }

    private int FindOverlappingPosition(int n, char[] arrc, int n2) {
        for (int i = 0; i < n2; ++i) {
            int n3 = 32;
            if (i + 32 > n2) {
                n3 = n2 - i;
            }
            if (!CompactCharArray.arrayRegionMatches(this.values, n, arrc, i, n3)) continue;
            return i;
        }
        return n2;
    }

    static final boolean arrayRegionMatches(char[] arrc, int n, char[] arrc2, int n2, int n3) {
        for (int i = n; i < n + n3; ++i) {
            if (arrc[i] == arrc2[i + (n2 - n)]) continue;
            return false;
        }
        return true;
    }

    private final boolean blockTouched(int n) {
        boolean bl = this.hashes[n] != 0;
        return bl;
    }

    private void expand() {
        if (this.isCompact) {
            int n;
            this.hashes = new int[2048];
            char[] arrc = new char[65536];
            for (n = 0; n < 65536; ++n) {
                arrc[n] = this.elementAt((char)n);
            }
            for (n = 0; n < 2048; ++n) {
                this.indices[n] = (char)(n << 5);
            }
            this.values = null;
            this.values = arrc;
            this.isCompact = false;
        }
    }

    private final void touchBlock(int n, int n2) {
        int[] arrn = this.hashes;
        arrn[n] = arrn[n] + (n2 << 1) | 1;
    }

    @Deprecated
    public Object clone() {
        try {
            CompactCharArray compactCharArray = (CompactCharArray)super.clone();
            compactCharArray.values = (char[])this.values.clone();
            compactCharArray.indices = (char[])this.indices.clone();
            if (this.hashes != null) {
                compactCharArray.hashes = (int[])this.hashes.clone();
            }
            return compactCharArray;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    @Deprecated
    public void compact() {
        this.compact(true);
    }

    @Deprecated
    public void compact(boolean bl) {
        if (!this.isCompact) {
            char[] arrc;
            int n = 0;
            int n2 = 65535;
            int n3 = 0;
            char[] arrc2 = bl ? new char[65536] : this.values;
            int n4 = 0;
            while (n4 < (arrc = this.indices).length) {
                int n5;
                int n6;
                arrc[n4] = (char)65535;
                boolean bl2 = this.blockTouched(n4);
                if (!bl2 && n2 != 65535) {
                    this.indices[n4] = (char)n2;
                    n6 = n2;
                    n5 = n3;
                } else {
                    int n7 = 0;
                    int n8 = 0;
                    while (n8 < n4) {
                        arrc = this.hashes;
                        if (arrc[n4] == arrc[n8] && CompactCharArray.arrayRegionMatches(arrc = this.values, n, arrc, n7, 32)) {
                            arrc = this.indices;
                            arrc[n4] = arrc[n8];
                        }
                        ++n8;
                        n7 += 32;
                    }
                    n6 = n2;
                    n5 = n3;
                    if (this.indices[n4] == '\uffff') {
                        n5 = bl ? this.FindOverlappingPosition(n, arrc2, n3) : n3;
                        n6 = n5 + 32;
                        n8 = n3;
                        if (n6 > n3) {
                            while (n3 < n6) {
                                arrc2[n3] = this.values[n + n3 - n5];
                                ++n3;
                            }
                            n8 = n6;
                        }
                        this.indices[n4] = (char)n5;
                        n6 = n2;
                        n5 = n8;
                        if (!bl2) {
                            n6 = (char)n7;
                            n5 = n8;
                        }
                    }
                }
                ++n4;
                n += 32;
                n2 = n6;
                n3 = n5;
            }
            arrc = new char[n3];
            System.arraycopy(arrc2, 0, arrc, 0, n3);
            this.values = arrc;
            this.isCompact = true;
            this.hashes = null;
        }
    }

    @Deprecated
    public char elementAt(char c) {
        char[] arrc = this.values;
        char c2 = (c = (char)((this.indices[c >> 5] & 65535) + (c & 31))) >= arrc.length ? (c = this.defaultValue) : (c = arrc[c]);
        return c2;
    }

    @Deprecated
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (CompactCharArray)object;
        for (int i = 0; i < 65536; ++i) {
            if (this.elementAt((char)i) == ((CompactCharArray)object).elementAt((char)i)) continue;
            return false;
        }
        return true;
    }

    @Deprecated
    public char[] getIndexArray() {
        return this.indices;
    }

    @Deprecated
    public char[] getValueArray() {
        return this.values;
    }

    @Deprecated
    public int hashCode() {
        char[] arrc;
        int n = 0;
        int n2 = Math.min(3, this.values.length / 16);
        for (int i = 0; i < (arrc = this.values).length; i += n2) {
            n = n * 37 + arrc[i];
        }
        return n;
    }

    @Deprecated
    public void setElementAt(char c, char c2) {
        if (this.isCompact) {
            this.expand();
        }
        this.values[c] = c2;
        this.touchBlock(c >> 5, c2);
    }

    @Deprecated
    public void setElementAt(char c, char c2, char c3) {
        if (this.isCompact) {
            this.expand();
        }
        while (c <= c2) {
            this.values[c] = c3;
            this.touchBlock(c >> 5, c3);
            c = (char)(c + 1);
        }
    }
}

