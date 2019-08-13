/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

import org.apache.xml.utils.res.CharArrayWrapper;
import org.apache.xml.utils.res.IntArrayWrapper;
import org.apache.xml.utils.res.LongArrayWrapper;
import org.apache.xml.utils.res.StringArrayWrapper;
import org.apache.xml.utils.res.XResourceBundle;

public class XResources_ja_JP_HA
extends XResourceBundle {
    @Override
    public Object[][] getContents() {
        CharArrayWrapper charArrayWrapper = new CharArrayWrapper(new char[]{'\u3042', '\u3044', '\u3046', '\u3048', '\u304a', '\u304b', '\u304d', '\u304f', '\u3051', '\u3053', '\u3055', '\u3057', '\u3059', '\u305b', '\u305d', '\u305f', '\u3061', '\u3064', '\u3066', '\u3068', '\u306a', '\u306b', '\u306c', '\u306d', '\u306e', '\u306f', '\u3072', '\u3075', '\u3078', '\u307b', '\u307e', '\u307f', '\u3080', '\u3081', '\u3082', '\u3084', '\u3086', '\u3088', '\u3089', '\u308a', '\u308b', '\u308c', '\u308d', '\u308f', '\u3090', '\u3091', '\u3092', '\u3093'});
        Object[] arrobject = new Object[]{"tradAlphabet", new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'})};
        Object[] arrobject2 = new Object[]{"numbering", "multiplicative-additive"};
        IntArrayWrapper intArrayWrapper = new IntArrayWrapper(new int[]{1});
        LongArrayWrapper longArrayWrapper = new LongArrayWrapper(new long[]{Long.MAX_VALUE, Long.MAX_VALUE, 100000000L, 10000L, 1000L, 100L, 10L});
        CharArrayWrapper charArrayWrapper2 = new CharArrayWrapper(new char[]{'\u4eac', '\u5146', '\u5104', '\u4e07', '\u5343', '\u767e', '\u5341'});
        CharArrayWrapper charArrayWrapper3 = new CharArrayWrapper(new char[0]);
        CharArrayWrapper charArrayWrapper4 = new CharArrayWrapper(new char[]{'\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d', '\u4e03', '\u516b', '\u4e5d'});
        StringArrayWrapper stringArrayWrapper = new StringArrayWrapper(new String[]{"digits"});
        return new Object[][]{{"ui_language", "ja"}, {"help_language", "ja"}, {"language", "ja"}, {"alphabet", charArrayWrapper}, arrobject, {"orientation", "LeftToRight"}, arrobject2, {"multiplierOrder", "follows"}, {"numberGroups", intArrayWrapper}, {"multiplier", longArrayWrapper}, {"multiplierChar", charArrayWrapper2}, {"zero", charArrayWrapper3}, {"digits", charArrayWrapper4}, {"tables", stringArrayWrapper}};
    }
}

