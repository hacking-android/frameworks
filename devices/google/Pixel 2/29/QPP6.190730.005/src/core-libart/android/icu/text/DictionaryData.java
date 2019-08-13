/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Assert;
import android.icu.impl.ICUBinary;
import android.icu.impl.ICUResourceBundle;
import android.icu.text.BytesDictionaryMatcher;
import android.icu.text.CharsDictionaryMatcher;
import android.icu.text.DictionaryMatcher;
import android.icu.util.UResourceBundle;
import java.io.IOException;
import java.nio.ByteBuffer;

final class DictionaryData {
    private static final int DATA_FORMAT_ID = 1147757428;
    public static final int IX_COUNT = 8;
    public static final int IX_RESERVED1_OFFSET = 1;
    public static final int IX_RESERVED2_OFFSET = 2;
    public static final int IX_RESERVED6 = 6;
    public static final int IX_RESERVED7 = 7;
    public static final int IX_STRING_TRIE_OFFSET = 0;
    public static final int IX_TOTAL_SIZE = 3;
    public static final int IX_TRANSFORM = 5;
    public static final int IX_TRIE_TYPE = 4;
    public static final int TRANSFORM_NONE = 0;
    public static final int TRANSFORM_OFFSET_MASK = 2097151;
    public static final int TRANSFORM_TYPE_MASK = 2130706432;
    public static final int TRANSFORM_TYPE_OFFSET = 16777216;
    public static final int TRIE_HAS_VALUES = 8;
    public static final int TRIE_TYPE_BYTES = 0;
    public static final int TRIE_TYPE_MASK = 7;
    public static final int TRIE_TYPE_UCHARS = 1;

    private DictionaryData() {
    }

    public static DictionaryMatcher loadDictionaryFor(String object) throws IOException {
        int n;
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/brkitr");
        int[] arrn = new StringBuilder();
        arrn.append("dictionaries/");
        arrn.append((String)object);
        object = iCUResourceBundle.getStringWithFallback(arrn.toString());
        arrn = new StringBuilder();
        arrn.append("brkitr/");
        arrn.append((String)object);
        object = ICUBinary.getRequiredData(arrn.toString());
        ICUBinary.readHeader((ByteBuffer)object, 1147757428, null);
        arrn = new int[8];
        for (n = 0; n < 8; ++n) {
            arrn[n] = ((ByteBuffer)object).getInt();
        }
        boolean bl = false;
        int n2 = arrn[0];
        boolean bl2 = n2 >= 32;
        Assert.assrt(bl2);
        if (n2 > 32) {
            ICUBinary.skipBytes((ByteBuffer)object, n2 - 32);
        }
        n = arrn[4] & 7;
        n2 = arrn[3] - n2;
        if (n == 0) {
            n = arrn[5];
            arrn = new byte[n2];
            ((ByteBuffer)object).get((byte[])arrn);
            object = new BytesDictionaryMatcher((byte[])arrn, n);
        } else if (n == 1) {
            bl2 = bl;
            if (n2 % 2 == 0) {
                bl2 = true;
            }
            Assert.assrt(bl2);
            object = new CharsDictionaryMatcher(ICUBinary.getString((ByteBuffer)object, n2 / 2, n2 & 1));
        } else {
            object = null;
        }
        return object;
    }
}

