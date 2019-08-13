/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

import org.apache.xml.utils.res.CharArrayWrapper;
import org.apache.xml.utils.res.IntArrayWrapper;
import org.apache.xml.utils.res.LongArrayWrapper;
import org.apache.xml.utils.res.StringArrayWrapper;
import org.apache.xml.utils.res.XResourceBundle;

public class XResources_ja_JP_HI
extends XResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"ui_language", "ja"};
        Object[] arrobject2 = new Object[]{"help_language", "ja"};
        Object[] arrobject3 = new Object[]{"alphabet", new CharArrayWrapper(new char[]{'\u3044', '\u308d', '\u306f', '\u306b', '\u307b', '\u3078', '\u3068', '\u3061', '\u308a', '\u306c', '\u308b', '\u3092', '\u308f', '\u304b', '\u3088', '\u305f', '\u308c', '\u305d', '\u3064', '\u306d', '\u306a', '\u3089', '\u3080', '\u3046', '\u3090', '\u306e', '\u304a', '\u304f', '\u3084', '\u307e', '\u3051', '\u3075', '\u3053', '\u3048', '\u3066', '\u3042', '\u3055', '\u304d', '\u3086', '\u3081', '\u307f', '\u3057', '\u3091', '\u3072', '\u3082', '\u305b', '\u3059'})};
        CharArrayWrapper charArrayWrapper = new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'});
        Object[] arrobject4 = new Object[]{"orientation", "LeftToRight"};
        Object[] arrobject5 = new Object[]{"multiplierOrder", "follows"};
        IntArrayWrapper intArrayWrapper = new IntArrayWrapper(new int[]{1});
        LongArrayWrapper longArrayWrapper = new LongArrayWrapper(new long[]{Long.MAX_VALUE, Long.MAX_VALUE, 100000000L, 10000L, 1000L, 100L, 10L});
        CharArrayWrapper charArrayWrapper2 = new CharArrayWrapper(new char[]{'\u4eac', '\u5146', '\u5104', '\u4e07', '\u5343', '\u767e', '\u5341'});
        Object[] arrobject6 = new Object[]{"zero", new CharArrayWrapper(new char[0])};
        Object[] arrobject7 = new Object[]{"digits", new CharArrayWrapper(new char[]{'\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d', '\u4e03', '\u516b', '\u4e5d'})};
        Object[] arrobject8 = new Object[]{"tables", new StringArrayWrapper(new String[]{"digits"})};
        return new Object[][]{arrobject, arrobject2, {"language", "ja"}, arrobject3, {"tradAlphabet", charArrayWrapper}, arrobject4, {"numbering", "multiplicative-additive"}, arrobject5, {"numberGroups", intArrayWrapper}, {"multiplier", longArrayWrapper}, {"multiplierChar", charArrayWrapper2}, arrobject6, arrobject7, arrobject8};
    }
}

