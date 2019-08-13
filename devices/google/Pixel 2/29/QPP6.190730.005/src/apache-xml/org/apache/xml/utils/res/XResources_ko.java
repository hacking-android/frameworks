/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

import org.apache.xml.utils.res.CharArrayWrapper;
import org.apache.xml.utils.res.IntArrayWrapper;
import org.apache.xml.utils.res.LongArrayWrapper;
import org.apache.xml.utils.res.StringArrayWrapper;
import org.apache.xml.utils.res.XResourceBundle;

public class XResources_ko
extends XResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"ui_language", "ko"};
        Object[] arrobject2 = new Object[]{"alphabet", new CharArrayWrapper(new char[]{'\u3131', '\u3134', '\u3137', '\u3139', '\u3141', '\u3142', '\u3145', '\u3147', '\u3148', '\u314a', '\u314b', '\u314c', '\u314d', '\u314e', '\u314f', '\u3151', '\u3153', '\u3155', '\u3157', '\u315b', '\u315c', '\u3160', '\u3161', '\u3163'})};
        Object[] arrobject3 = new Object[]{"tradAlphabet", new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'})};
        Object[] arrobject4 = new Object[]{"orientation", "LeftToRight"};
        Object[] arrobject5 = new Object[]{"multiplierOrder", "follows"};
        IntArrayWrapper intArrayWrapper = new IntArrayWrapper(new int[]{1});
        Object[] arrobject6 = new Object[]{"zero", new CharArrayWrapper(new char[0])};
        Object[] arrobject7 = new Object[]{"multiplier", new LongArrayWrapper(new long[]{100000000L, 10000L, 1000L, 100L, 10L})};
        CharArrayWrapper charArrayWrapper = new CharArrayWrapper(new char[]{'\uffffc5b5', '\uffffb9cc', '\uffffcc9c', '\uffffbc31', '\uffffc2ed'});
        Object[] arrobject8 = new Object[]{"digits", new CharArrayWrapper(new char[]{'\uffffc77c', '\uffffc774', '\uffffc0bc', '\uffffc0ac', '\uffffc624', '\uffffc721', '\uffffce60', '\uffffd314', '\uffffad6c'})};
        Object[] arrobject9 = new Object[]{"tables", new StringArrayWrapper(new String[]{"digits"})};
        return new Object[][]{arrobject, {"help_language", "ko"}, {"language", "ko"}, arrobject2, arrobject3, arrobject4, {"numbering", "multiplicative-additive"}, arrobject5, {"numberGroups", intArrayWrapper}, arrobject6, arrobject7, {"multiplierChar", charArrayWrapper}, arrobject8, arrobject9};
    }
}

