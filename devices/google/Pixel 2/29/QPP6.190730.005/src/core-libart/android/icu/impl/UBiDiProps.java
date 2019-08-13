/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.impl.Trie2;
import android.icu.impl.Trie2_16;
import android.icu.text.UnicodeSet;
import android.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class UBiDiProps {
    private static final int BIDI_CONTROL_SHIFT = 11;
    private static final int BPT_MASK = 768;
    private static final int BPT_SHIFT = 8;
    private static final int CLASS_MASK = 31;
    private static final String DATA_FILE_NAME = "ubidi.icu";
    private static final String DATA_NAME = "ubidi";
    private static final String DATA_TYPE = "icu";
    private static final int ESC_MIRROR_DELTA = -4;
    private static final int FMT = 1114195049;
    public static final UBiDiProps INSTANCE;
    private static final int IS_MIRRORED_SHIFT = 12;
    private static final int IX_JG_LIMIT = 5;
    private static final int IX_JG_LIMIT2 = 7;
    private static final int IX_JG_START = 4;
    private static final int IX_JG_START2 = 6;
    private static final int IX_MAX_VALUES = 15;
    private static final int IX_MIRROR_LENGTH = 3;
    private static final int IX_TOP = 16;
    private static final int IX_TRIE_SIZE = 2;
    private static final int JOIN_CONTROL_SHIFT = 10;
    private static final int JT_MASK = 224;
    private static final int JT_SHIFT = 5;
    private static final int MAX_JG_MASK = 16711680;
    private static final int MAX_JG_SHIFT = 16;
    private static final int MIRROR_DELTA_SHIFT = 13;
    private static final int MIRROR_INDEX_SHIFT = 21;
    private int[] indexes;
    private byte[] jgArray;
    private byte[] jgArray2;
    private int[] mirrors;
    private Trie2_16 trie;

    static {
        try {
            UBiDiProps uBiDiProps;
            INSTANCE = uBiDiProps = new UBiDiProps();
            return;
        }
        catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    private UBiDiProps() throws IOException {
        this.readData(ICUBinary.getData(DATA_FILE_NAME));
    }

    private static final int getClassFromProps(int n) {
        return n & 31;
    }

    private static final boolean getFlagFromProps(int n, int n2) {
        boolean bl = true;
        if ((n >> n2 & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    private final int getMirror(int n, int n2) {
        if ((n2 = UBiDiProps.getMirrorDeltaFromProps(n2)) != -4) {
            return n + n2;
        }
        int n3 = this.indexes[3];
        for (n2 = 0; n2 < n3; ++n2) {
            int n4 = this.mirrors[n2];
            int n5 = UBiDiProps.getMirrorCodePoint(n4);
            if (n == n5) {
                return UBiDiProps.getMirrorCodePoint(this.mirrors[UBiDiProps.getMirrorIndex(n4)]);
            }
            if (n < n5) break;
        }
        return n;
    }

    private static final int getMirrorCodePoint(int n) {
        return 2097151 & n;
    }

    private static final int getMirrorDeltaFromProps(int n) {
        return (short)n >> 13;
    }

    private static final int getMirrorIndex(int n) {
        return n >>> 21;
    }

    private void readData(ByteBuffer byteBuffer) throws IOException {
        ICUBinary.readHeader(byteBuffer, 1114195049, new IsAcceptable());
        int n = byteBuffer.getInt();
        if (n >= 16) {
            int n2;
            this.indexes = new int[n];
            this.indexes[0] = n;
            for (n2 = 1; n2 < n; ++n2) {
                this.indexes[n2] = byteBuffer.getInt();
            }
            this.trie = Trie2_16.createFromSerialized(byteBuffer);
            n = this.indexes[2];
            n2 = this.trie.getSerializedLength();
            if (n2 <= n) {
                ICUBinary.skipBytes(byteBuffer, n - n2);
                n2 = this.indexes[3];
                if (n2 > 0) {
                    this.mirrors = ICUBinary.getInts(byteBuffer, n2, 0);
                }
                int[] arrn = this.indexes;
                this.jgArray = new byte[arrn[5] - arrn[4]];
                byteBuffer.get(this.jgArray);
                arrn = this.indexes;
                this.jgArray2 = new byte[arrn[7] - arrn[6]];
                byteBuffer.get(this.jgArray2);
                return;
            }
            throw new IOException("ubidi.icu: not enough bytes for the trie");
        }
        throw new IOException("indexes[0] too small in ubidi.icu");
    }

    public final void addPropertyStarts(UnicodeSet unicodeSet) {
        int n;
        int n2;
        for (Trie2.Range range : this.trie) {
            if (range.leadSurrogate) break;
            unicodeSet.add(range.startCodePoint);
        }
        int n3 = this.indexes[3];
        for (n2 = 0; n2 < n3; ++n2) {
            n = UBiDiProps.getMirrorCodePoint(this.mirrors[n2]);
            unicodeSet.add(n, n + 1);
        }
        int[] arrn = this.indexes;
        n2 = arrn[4];
        n = arrn[5];
        arrn = this.jgArray;
        do {
            int n4 = 0;
            n3 = n2;
            for (int i = 0; i < n - n2; ++i) {
                int n5 = arrn[i];
                int n6 = n4;
                if (n5 != n4) {
                    unicodeSet.add(n3);
                    n6 = n5;
                }
                ++n3;
                n4 = n6;
            }
            if (n4 != 0) {
                unicodeSet.add(n);
            }
            if (n != (arrn = this.indexes)[5]) break;
            n2 = arrn[6];
            n = arrn[7];
            arrn = this.jgArray2;
        } while (true);
    }

    public final int getClass(int n) {
        return UBiDiProps.getClassFromProps(this.trie.get(n));
    }

    public final int getJoiningGroup(int n) {
        int[] arrn = this.indexes;
        int n2 = arrn[4];
        int n3 = arrn[5];
        if (n2 <= n && n < n3) {
            return this.jgArray[n - n2] & 255;
        }
        arrn = this.indexes;
        n2 = arrn[6];
        n3 = arrn[7];
        if (n2 <= n && n < n3) {
            return this.jgArray2[n - n2] & 255;
        }
        return 0;
    }

    public final int getJoiningType(int n) {
        return (this.trie.get(n) & 224) >> 5;
    }

    public final int getMaxValue(int n) {
        int n2 = this.indexes[15];
        if (n != 4096) {
            if (n != 4117) {
                if (n != 4102) {
                    if (n != 4103) {
                        return -1;
                    }
                    return (n2 & 224) >> 5;
                }
                return (16711680 & n2) >> 16;
            }
            return (n2 & 768) >> 8;
        }
        return n2 & 31;
    }

    public final int getMirror(int n) {
        return this.getMirror(n, this.trie.get(n));
    }

    public final int getPairedBracket(int n) {
        int n2 = this.trie.get(n);
        if ((n2 & 768) == 0) {
            return n;
        }
        return this.getMirror(n, n2);
    }

    public final int getPairedBracketType(int n) {
        return (this.trie.get(n) & 768) >> 8;
    }

    public final boolean isBidiControl(int n) {
        return UBiDiProps.getFlagFromProps(this.trie.get(n), 11);
    }

    public final boolean isJoinControl(int n) {
        return UBiDiProps.getFlagFromProps(this.trie.get(n), 10);
    }

    public final boolean isMirrored(int n) {
        return UBiDiProps.getFlagFromProps(this.trie.get(n), 12);
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] arrby) {
            boolean bl = false;
            if (arrby[0] == 2) {
                bl = true;
            }
            return bl;
        }
    }

}

