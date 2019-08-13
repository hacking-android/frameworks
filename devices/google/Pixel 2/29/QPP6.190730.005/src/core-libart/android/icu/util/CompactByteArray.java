/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.Utility;
import android.icu.util.ICUCloneNotSupportedException;

@Deprecated
public final class CompactByteArray
implements Cloneable {
    private static final int BLOCKCOUNT = 128;
    private static final int BLOCKMASK = 127;
    private static final int BLOCKSHIFT = 7;
    private static final int INDEXCOUNT = 512;
    private static final int INDEXSHIFT = 9;
    @Deprecated
    public static final int UNICODECOUNT = 65536;
    byte defaultValue;
    private int[] hashes;
    private char[] indices;
    private boolean isCompact;
    private byte[] values;

    @Deprecated
    public CompactByteArray() {
        this(0);
    }

    @Deprecated
    public CompactByteArray(byte by) {
        int n;
        this.values = new byte[65536];
        this.indices = new char[512];
        this.hashes = new int[512];
        for (n = 0; n < 65536; ++n) {
            this.values[n] = by;
        }
        for (n = 0; n < 512; ++n) {
            this.indices[n] = (char)(n << 7);
            this.hashes[n] = 0;
        }
        this.isCompact = false;
        this.defaultValue = by;
    }

    @Deprecated
    public CompactByteArray(String string, String string2) {
        this(Utility.RLEStringToCharArray(string), Utility.RLEStringToByteArray(string2));
    }

    @Deprecated
    public CompactByteArray(char[] arrc, byte[] arrby) {
        if (arrc.length == 512) {
            for (int i = 0; i < 512; ++i) {
                if (arrc[i] < arrby.length + 128) {
                    continue;
                }
                throw new IllegalArgumentException("Index out of bounds.");
            }
            this.indices = arrc;
            this.values = arrby;
            this.isCompact = true;
            return;
        }
        throw new IllegalArgumentException("Index out of bounds.");
    }

    static final boolean arrayRegionMatches(byte[] arrby, int n, byte[] arrby2, int n2, int n3) {
        for (int i = n; i < n + n3; ++i) {
            if (arrby[i] == arrby2[i + (n2 - n)]) continue;
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
            this.hashes = new int[512];
            byte[] arrby = new byte[65536];
            for (n = 0; n < 65536; ++n) {
                byte by = this.elementAt((char)n);
                arrby[n] = by;
                this.touchBlock(n >> 7, by);
            }
            for (n = 0; n < 512; ++n) {
                this.indices[n] = (char)(n << 7);
            }
            this.values = null;
            this.values = arrby;
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
            CompactByteArray compactByteArray = (CompactByteArray)super.clone();
            compactByteArray.values = (byte[])this.values.clone();
            compactByteArray.indices = (char[])this.indices.clone();
            if (this.hashes != null) {
                compactByteArray.hashes = (int[])this.hashes.clone();
            }
            return compactByteArray;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    @Deprecated
    public void compact() {
        this.compact(false);
    }

    @Deprecated
    public void compact(boolean bl) {
        if (!this.isCompact) {
            Object[] arrobject;
            int n = 0;
            int n2 = 0;
            int n3 = 65535;
            int n4 = 0;
            while (n4 < (arrobject = this.indices).length) {
                int n5;
                int n6;
                arrobject[n4] = (char)65535;
                bl = this.blockTouched(n4);
                if (!bl && n3 != 65535) {
                    this.indices[n4] = (char)n3;
                    n6 = n;
                    n5 = n3;
                } else {
                    int n7 = 0;
                    int n8 = 0;
                    while (n8 < n) {
                        arrobject = this.hashes;
                        if (arrobject[n4] == arrobject[n8] && CompactByteArray.arrayRegionMatches((byte[])(arrobject = this.values), n2, (byte[])arrobject, n7, 128)) {
                            this.indices[n4] = (char)n7;
                            break;
                        }
                        ++n8;
                        n7 += 128;
                    }
                    n6 = n++;
                    n5 = n3;
                    if (this.indices[n4] == '\uffff') {
                        arrobject = this.values;
                        System.arraycopy((byte[])arrobject, (int)n2, (byte[])arrobject, (int)n7, (int)128);
                        this.indices[n4] = (char)n7;
                        arrobject = this.hashes;
                        arrobject[n8] = arrobject[n4];
                        n6 = n;
                        n5 = n3;
                        if (!bl) {
                            n5 = (char)n7;
                            n6 = n;
                        }
                    }
                }
                ++n4;
                n2 += 128;
                n = n6;
                n3 = n5;
            }
            n4 = n * 128;
            arrobject = new byte[n4];
            System.arraycopy((byte[])this.values, (int)0, (byte[])arrobject, (int)0, (int)n4);
            this.values = arrobject;
            this.isCompact = true;
            this.hashes = null;
        }
    }

    @Deprecated
    public byte elementAt(char c) {
        return this.values[(this.indices[c >> 7] & 65535) + (c & 127)];
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
        object = (CompactByteArray)object;
        for (int i = 0; i < 65536; ++i) {
            if (this.elementAt((char)i) == ((CompactByteArray)object).elementAt((char)i)) continue;
            return false;
        }
        return true;
    }

    @Deprecated
    public char[] getIndexArray() {
        return this.indices;
    }

    @Deprecated
    public byte[] getValueArray() {
        return this.values;
    }

    @Deprecated
    public int hashCode() {
        byte[] arrby;
        int n = 0;
        int n2 = Math.min(3, this.values.length / 16);
        for (int i = 0; i < (arrby = this.values).length; i += n2) {
            n = n * 37 + arrby[i];
        }
        return n;
    }

    @Deprecated
    public void setElementAt(char c, byte by) {
        if (this.isCompact) {
            this.expand();
        }
        this.values[c] = by;
        this.touchBlock(c >> 7, by);
    }

    @Deprecated
    public void setElementAt(char c, char c2, byte by) {
        if (this.isCompact) {
            this.expand();
        }
        while (c <= c2) {
            this.values[c] = by;
            this.touchBlock(c >> 7, by);
            c = (char)(c + 1);
        }
    }
}

