/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.ICUBinary;
import android.icu.impl.Normalizer2Impl;
import android.icu.impl.Trie2_32;
import android.icu.impl.USerializedSet;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationFastLatin;
import android.icu.impl.coll.CollationSettings;
import android.icu.impl.coll.CollationTailoring;
import android.icu.impl.coll.SharedObject;
import android.icu.text.UnicodeSet;
import android.icu.util.ICUException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

final class CollationDataReader {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DATA_FORMAT = 1430482796;
    private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
    static final int IX_CE32S_OFFSET = 11;
    static final int IX_CES_OFFSET = 9;
    static final int IX_COMPRESSIBLE_BYTES_OFFSET = 17;
    static final int IX_CONTEXTS_OFFSET = 13;
    static final int IX_FAST_LATIN_TABLE_OFFSET = 15;
    static final int IX_INDEXES_LENGTH = 0;
    static final int IX_JAMO_CE32S_START = 4;
    static final int IX_OPTIONS = 1;
    static final int IX_REORDER_CODES_OFFSET = 5;
    static final int IX_REORDER_TABLE_OFFSET = 6;
    static final int IX_RESERVED10_OFFSET = 10;
    static final int IX_RESERVED18_OFFSET = 18;
    static final int IX_RESERVED2 = 2;
    static final int IX_RESERVED3 = 3;
    static final int IX_RESERVED8_OFFSET = 8;
    static final int IX_ROOT_ELEMENTS_OFFSET = 12;
    static final int IX_SCRIPTS_OFFSET = 16;
    static final int IX_TOTAL_SIZE = 19;
    static final int IX_TRIE_OFFSET = 7;
    static final int IX_UNSAFE_BWD_OFFSET = 14;

    private CollationDataReader() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void read(CollationTailoring object, ByteBuffer object2, CollationTailoring collationTailoring) throws IOException {
        int[] arrn;
        int n;
        char[] arrc;
        int n2;
        boolean[] arrbl;
        collationTailoring.version = ICUBinary.readHeader((ByteBuffer)object2, 1430482796, IS_ACCEPTABLE);
        if (object != null && ((CollationTailoring)object).getUCAVersion() != collationTailoring.getUCAVersion()) {
            throw new ICUException("Tailoring UCA version differs from base data UCA version");
        }
        int n3 = ((Buffer)object2).remaining();
        if (n3 < 8) throw new ICUException("not enough bytes");
        int n4 = ((ByteBuffer)object2).getInt();
        if (n4 < 2 || n3 < n4 * 4) throw new ICUException("not enough indexes");
        int[] arrn2 = new int[20];
        arrn2[0] = n4;
        for (n2 = 1; n2 < n4 && n2 < arrn2.length; ++n2) {
            arrn2[n2] = ((ByteBuffer)object2).getInt();
        }
        for (n2 = n4; n2 < arrn2.length; ++n2) {
            arrn2[n2] = -1;
        }
        if (n4 > arrn2.length) {
            ICUBinary.skipBytes((ByteBuffer)object2, (n4 - arrn2.length) * 4);
        }
        if (n3 < (n2 = n4 > 19 ? arrn2[19] : (n4 > 5 ? arrn2[n4 - 1] : 0))) throw new ICUException("not enough bytes");
        object = object == null ? null : ((CollationTailoring)object).data;
        n2 = arrn2[5];
        int n5 = arrn2[5 + 1] - n2;
        if (n5 >= 4) {
            if (object == null) throw new ICUException("Collation base data must not reorder scripts");
            n4 = n5 / 4;
            arrn = ICUBinary.getInts((ByteBuffer)object2, n4, n5 & 3);
            for (n2 = 0; n2 < n4 && (arrn[n4 - n2 - 1] & -65536) != 0; ++n2) {
            }
            n2 = n4 - n2;
        } else {
            arrn = new int[]{};
            n2 = 0;
            ICUBinary.skipBytes((ByteBuffer)object2, n5);
        }
        byte[] arrby = null;
        n4 = arrn2[6];
        n4 = n5 = arrn2[6 + 1] - n4;
        if (n5 >= 256) {
            if (n2 == 0) throw new ICUException("Reordering table without reordering codes");
            arrby = new byte[256];
            ((ByteBuffer)object2).get(arrby);
            n4 = n5 - 256;
        }
        ICUBinary.skipBytes((ByteBuffer)object2, n4);
        if (object != null && ((CollationData)object).numericPrimary != ((long)arrn2[1] & 0xFF000000L)) {
            throw new ICUException("Tailoring numeric primary weight differs from base data");
        }
        char[] arrc2 = null;
        n4 = arrn2[7];
        if ((n4 = arrn2[7 + 1] - n4) >= 8) {
            collationTailoring.ensureOwnedData();
            arrc2 = collationTailoring.ownedData;
            arrc2.base = object;
            arrc2.numericPrimary = (long)arrn2[1] & 0xFF000000L;
            arrbl = Trie2_32.createFromSerialized((ByteBuffer)object2);
            collationTailoring.trie = arrbl;
            arrc2.trie = arrbl;
            n5 = arrc2.trie.getSerializedLength();
            if (n5 > n4) throw new ICUException("Not enough bytes for the mappings trie");
            n4 -= n5;
        } else {
            if (object == null) throw new ICUException("Missing collation data mappings");
            collationTailoring.data = object;
        }
        ICUBinary.skipBytes((ByteBuffer)object2, n4);
        n4 = arrn2[8];
        ICUBinary.skipBytes((ByteBuffer)object2, arrn2[8 + 1] - n4);
        n4 = arrn2[9];
        n4 = arrn2[9 + 1] - n4;
        if (n4 >= 8) {
            if (arrc2 == null) throw new ICUException("Tailored ces without tailored trie");
            arrc2.ces = ICUBinary.getLongs((ByteBuffer)object2, n4 / 8, n4 & 7);
        } else {
            ICUBinary.skipBytes((ByteBuffer)object2, n4);
        }
        n4 = arrn2[10];
        ICUBinary.skipBytes((ByteBuffer)object2, arrn2[10 + 1] - n4);
        n4 = arrn2[11];
        n4 = arrn2[11 + 1] - n4;
        if (n4 >= 4) {
            if (arrc2 == null) throw new ICUException("Tailored ce32s without tailored trie");
            arrc2.ce32s = ICUBinary.getInts((ByteBuffer)object2, n4 / 4, n4 & 3);
        } else {
            ICUBinary.skipBytes((ByteBuffer)object2, n4);
        }
        n4 = arrn2[4];
        if (n4 >= 0) {
            if (arrc2 == null || arrc2.ce32s == null) throw new ICUException("JamoCE32sStart index into non-existent ce32s[]");
            arrc2.jamoCE32s = new int[67];
            System.arraycopy(arrc2.ce32s, n4, arrc2.jamoCE32s, 0, 67);
        } else if (arrc2 != null) {
            if (object == null) throw new ICUException("Missing Jamo CE32s for Hangul processing");
            arrc2.jamoCE32s = ((CollationData)object).jamoCE32s;
        }
        n4 = arrn2[12];
        n5 = arrn2[12 + 1] - n4;
        if (n5 >= 4) {
            n = n5 / 4;
            if (arrc2 == null) throw new ICUException("Root elements but no mappings");
            if (n <= 4) throw new ICUException("Root elements array too short");
            arrc2.rootElements = new long[n];
            for (n4 = 0; n4 < n; ++n4) {
                arrc2.rootElements[n4] = (long)((ByteBuffer)object2).getInt() & 0xFFFFFFFFL;
            }
            if (arrc2.rootElements[3] != 0x5000500L) throw new ICUException("Common sec/ter weights in base data differ from the hardcoded value");
            if (arrc2.rootElements[4] >>> 24 < 69L) throw new ICUException("[fixed last secondary common byte] is too low");
            n3 = n5 & 3;
            n4 = n2;
        } else {
            n4 = n2;
            n3 = n5;
        }
        ICUBinary.skipBytes((ByteBuffer)object2, n3);
        n2 = arrn2[13];
        n2 = arrn2[13 + 1] - n2;
        if (n2 >= 2) {
            if (arrc2 == null) throw new ICUException("Tailored contexts without tailored trie");
            arrc2.contexts = ICUBinary.getString((ByteBuffer)object2, n2 / 2, n2 & 1);
        } else {
            ICUBinary.skipBytes((ByteBuffer)object2, n2);
        }
        n3 = 14;
        int n6 = arrn2[14];
        n2 = arrn2[14 + 1] - n6;
        if (n2 >= 2) {
            if (arrc2 == null) throw new ICUException("Unsafe-backward-set but no mappings");
            if (object == null) {
                collationTailoring.unsafeBackwardSet = new UnicodeSet(56320, 57343);
                arrc2.nfcImpl.addLcccChars(collationTailoring.unsafeBackwardSet);
            } else {
                collationTailoring.unsafeBackwardSet = ((CollationData)object).unsafeBackwardSet.cloneAsThawed();
            }
            arrbl = new USerializedSet();
            arrc = ICUBinary.getChars((ByteBuffer)object2, n2 / 2, n2 & 1);
            n = 0;
            arrbl.getSet(arrc, 0);
            int n7 = arrbl.countRanges();
            arrc = new int[2];
            n2 = n6;
            for (n5 = 0; n5 < n7; ++n5) {
                arrbl.getRange(n5, arrc);
                collationTailoring.unsafeBackwardSet.add(arrc[0], arrc[1]);
            }
            n2 = 65536;
            n3 = 55296;
            while (n3 < 56320) {
                if (!collationTailoring.unsafeBackwardSet.containsNone(n2, n2 + 1023)) {
                    collationTailoring.unsafeBackwardSet.add(n3);
                }
                ++n3;
                n2 += 1024;
            }
            collationTailoring.unsafeBackwardSet.freeze();
            arrc2.unsafeBackwardSet = collationTailoring.unsafeBackwardSet;
            n2 = n;
        } else if (arrc2 != null) {
            if (object == null) throw new ICUException("Missing unsafe-backward-set");
            arrc2.unsafeBackwardSet = ((CollationData)object).unsafeBackwardSet;
        }
        ICUBinary.skipBytes((ByteBuffer)object2, n2);
        n2 = arrn2[15];
        n2 = n3 = arrn2[15 + 1] - n2;
        if (arrc2 != null) {
            arrc2.fastLatinTable = null;
            arrc2.fastLatinTableHeader = null;
            n2 = n3;
            if ((arrn2[1] >> 16 & 255) == 2) {
                if (n3 >= 2) {
                    n5 = ((ByteBuffer)object2).getChar();
                    n = n5 & 255;
                    arrc2.fastLatinTableHeader = new char[n];
                    arrc2.fastLatinTableHeader[0] = (char)n5;
                    for (n2 = 1; n2 < n; ++n2) {
                        arrc2.fastLatinTableHeader[n2] = ((ByteBuffer)object2).getChar();
                    }
                    arrc2.fastLatinTable = ICUBinary.getChars((ByteBuffer)object2, n3 / 2 - n, n3 & 1);
                    n2 = 0;
                    if (n5 >> 8 != 2) {
                        throw new ICUException("Fast-Latin table version differs from version in data header");
                    }
                } else {
                    n2 = n3;
                    if (object != null) {
                        arrc2.fastLatinTable = ((CollationData)object).fastLatinTable;
                        arrc2.fastLatinTableHeader = ((CollationData)object).fastLatinTableHeader;
                        n2 = n3;
                    }
                }
            }
        }
        ICUBinary.skipBytes((ByteBuffer)object2, n2);
        n2 = arrn2[16];
        n2 = arrn2[16 + 1] - n2;
        if (n2 >= 2) {
            if (arrc2 == null) throw new ICUException("Script order data but no mappings");
            n3 = n2 / 2;
            arrbl = ((ByteBuffer)object2).asCharBuffer();
            arrc2.numScripts = arrbl.get();
            if ((n3 -= arrc2.numScripts + 1 + 16) <= 2) throw new ICUException("Script order data too short");
            arrc = new char[arrc2.numScripts + 16];
            arrc2.scriptsIndex = arrc;
            arrbl.get(arrc);
            arrc = new char[n3];
            arrc2.scriptStarts = arrc;
            arrbl.get(arrc);
            if (arrc2.scriptStarts[0] != '\u0000' || arrc2.scriptStarts[1] != '\u0300' || arrc2.scriptStarts[n3 - 1] != '\uff00') {
                throw new ICUException("Script order data not valid");
            }
        } else if (arrc2 != null && object != null) {
            arrc2.numScripts = ((CollationData)object).numScripts;
            arrc2.scriptsIndex = ((CollationData)object).scriptsIndex;
            arrc2.scriptStarts = ((CollationData)object).scriptStarts;
        }
        ICUBinary.skipBytes((ByteBuffer)object2, n2);
        n2 = arrn2[17];
        n3 = arrn2[17 + 1] - n2;
        if (n3 >= 256) {
            if (arrc2 == null) throw new ICUException("Data for compressible primary lead bytes but no mappings");
            arrc2.compressibleBytes = new boolean[256];
            for (n2 = 0; n2 < 256; ++n2) {
                arrbl = arrc2.compressibleBytes;
                boolean bl = ((ByteBuffer)object2).get() != 0;
                arrbl[n2] = bl;
            }
            n2 = n3 - 256;
        } else if (arrc2 == null) {
            n2 = n3;
        } else {
            if (object == null) throw new ICUException("Missing data for compressible primary lead bytes");
            arrc2.compressibleBytes = ((CollationData)object).compressibleBytes;
            n2 = n3;
        }
        ICUBinary.skipBytes((ByteBuffer)object2, n2);
        n2 = arrn2[18];
        ICUBinary.skipBytes((ByteBuffer)object2, arrn2[18 + 1] - n2);
        object2 = collationTailoring.settings.readOnly();
        n3 = arrn2[1] & 65535;
        arrc2 = new char[384];
        n2 = CollationFastLatin.getOptions(collationTailoring.data, (CollationSettings)object2, arrc2);
        if (n3 == ((CollationSettings)object2).options && ((CollationSettings)object2).variableTop != 0L && Arrays.equals(arrn, ((CollationSettings)object2).reorderCodes) && n2 == ((CollationSettings)object2).fastLatinOptions && (n2 < 0 || Arrays.equals(arrc2, ((CollationSettings)object2).fastLatinPrimaries))) {
            return;
        }
        object2 = collationTailoring.settings.copyOnWrite();
        ((CollationSettings)object2).options = n3;
        ((CollationSettings)object2).variableTop = collationTailoring.data.getLastPrimaryForGroup(((CollationSettings)object2).getMaxVariable() + 4096);
        if (((CollationSettings)object2).variableTop == 0L) throw new ICUException("The maxVariable could not be mapped to a variableTop");
        if (n4 != 0) {
            ((CollationSettings)object2).aliasReordering((CollationData)object, arrn, n4, arrby);
        }
        ((CollationSettings)object2).fastLatinOptions = CollationFastLatin.getOptions(collationTailoring.data, (CollationSettings)object2, ((CollationSettings)object2).fastLatinPrimaries);
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] arrby) {
            boolean bl = false;
            if (arrby[0] == 5) {
                bl = true;
            }
            return bl;
        }
    }

}

