/*
 * Decompiled with CFR 0.145.
 */
package android.icu.lang;

import android.icu.impl.CharacterPropertiesImpl;
import android.icu.lang.UCharacter;
import android.icu.text.UnicodeSet;
import android.icu.util.CodePointMap;
import android.icu.util.CodePointTrie;
import android.icu.util.MutableCodePointTrie;

public final class CharacterProperties {
    private static final CodePointMap[] maps;
    private static final UnicodeSet[] sets;

    static {
        sets = new UnicodeSet[65];
        maps = new CodePointMap[25];
    }

    private CharacterProperties() {
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final UnicodeSet getBinaryPropertySet(int n) {
        if (n >= 0 && 65 > n) {
            UnicodeSet[] arrunicodeSet = sets;
            synchronized (arrunicodeSet) {
                UnicodeSet unicodeSet;
                UnicodeSet unicodeSet2 = unicodeSet = sets[n];
                if (unicodeSet == null) {
                    UnicodeSet[] arrunicodeSet2 = sets;
                    unicodeSet2 = unicodeSet = CharacterProperties.makeSet(n);
                    arrunicodeSet2[n] = unicodeSet;
                }
                return unicodeSet2;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n);
        stringBuilder.append(" is not a constant for a UProperty binary property");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final CodePointMap getIntPropertyMap(int n) {
        if (n >= 4096 && 4121 > n) {
            CodePointMap[] arrcodePointMap = maps;
            synchronized (arrcodePointMap) {
                CodePointMap codePointMap;
                CodePointMap codePointMap2 = codePointMap = maps[n - 4096];
                if (codePointMap == null) {
                    CodePointMap[] arrcodePointMap2 = maps;
                    codePointMap2 = codePointMap = CharacterProperties.makeMap(n);
                    arrcodePointMap2[n - 4096] = codePointMap;
                }
                return codePointMap2;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n);
        stringBuilder.append(" is not a constant for a UProperty int property");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static CodePointMap makeMap(int n) {
        int n2 = n == 4106 ? 103 : 0;
        MutableCodePointTrie mutableCodePointTrie = new MutableCodePointTrie(n2, n2);
        Object object = CharacterPropertiesImpl.getInclusionsForProperty(n);
        int n3 = object.getRangeCount();
        int n4 = 0;
        int n5 = n2;
        for (int i = 0; i < n3; ++i) {
            int n6 = object.getRangeEnd(i);
            for (int j = object.getRangeStart((int)i); j <= n6; ++j) {
                int n7 = UCharacter.getIntPropertyValue(j, n);
                int n8 = n4;
                int n9 = n5;
                if (n5 != n7) {
                    if (n5 != n2) {
                        mutableCodePointTrie.setRange(n4, j - 1, n5);
                    }
                    n8 = j;
                    n9 = n7;
                }
                n4 = n8;
                n5 = n9;
            }
        }
        if (n5 != 0) {
            mutableCodePointTrie.setRange(n4, 1114111, n5);
        }
        CodePointTrie.Type type = n != 4096 && n != 4101 ? CodePointTrie.Type.SMALL : CodePointTrie.Type.FAST;
        n = UCharacter.getIntPropertyMaxValue(n);
        object = n <= 255 ? CodePointTrie.ValueWidth.BITS_8 : (n <= 65535 ? CodePointTrie.ValueWidth.BITS_16 : CodePointTrie.ValueWidth.BITS_32);
        return mutableCodePointTrie.buildImmutable(type, (CodePointTrie.ValueWidth)((Object)object));
    }

    private static UnicodeSet makeSet(int n) {
        UnicodeSet unicodeSet = new UnicodeSet();
        UnicodeSet unicodeSet2 = CharacterPropertiesImpl.getInclusionsForProperty(n);
        int n2 = unicodeSet2.getRangeCount();
        int n3 = -1;
        for (int i = 0; i < n2; ++i) {
            int n4 = unicodeSet2.getRangeEnd(i);
            for (int j = unicodeSet2.getRangeStart((int)i); j <= n4; ++j) {
                int n5;
                if (UCharacter.hasBinaryProperty(j, n)) {
                    n5 = n3;
                    if (n3 < 0) {
                        n5 = j;
                    }
                } else {
                    n5 = n3;
                    if (n3 >= 0) {
                        unicodeSet.add(n3, j - 1);
                        n5 = -1;
                    }
                }
                n3 = n5;
            }
        }
        if (n3 >= 0) {
            unicodeSet.add(n3, 1114111);
        }
        return unicodeSet.freeze();
    }
}

