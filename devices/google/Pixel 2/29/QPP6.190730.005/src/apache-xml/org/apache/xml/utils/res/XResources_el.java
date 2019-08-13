/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

import org.apache.xml.utils.res.CharArrayWrapper;
import org.apache.xml.utils.res.IntArrayWrapper;
import org.apache.xml.utils.res.LongArrayWrapper;
import org.apache.xml.utils.res.StringArrayWrapper;
import org.apache.xml.utils.res.XResourceBundle;

public class XResources_el
extends XResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"ui_language", "el"};
        Object[] arrobject2 = new Object[]{"language", "el"};
        Object[] arrobject3 = new Object[]{"alphabet", new CharArrayWrapper(new char[]{'\u03b1', '\u03b2', '\u03b3', '\u03b4', '\u03b5', '\u03b6', '\u03b7', '\u03b8', '\u03b9', '\u03ba', '\u03bb', '\u03bc', '\u03bd', '\u03be', '\u03bf', '\u03c0', '\u03c1', '\u03c2', '\u03c3', '\u03c4', '\u03c5', '\u03c6', '\u03c7', '\u03c8', '\u03c9'})};
        Object[] arrobject4 = new Object[]{"tradAlphabet", new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'})};
        Object[] arrobject5 = new Object[]{"orientation", "LeftToRight"};
        Object[] arrobject6 = new Object[]{"numbering", "multiplicative-additive"};
        Object[] arrobject7 = new Object[]{"multiplierOrder", "precedes"};
        Object[] arrobject8 = new Object[]{"numberGroups", new IntArrayWrapper(new int[]{100, 10, 1})};
        Object[] arrobject9 = new Object[]{"multiplier", new LongArrayWrapper(new long[]{1000L})};
        Object[] arrobject10 = new Object[]{"multiplierChar", new CharArrayWrapper(new char[]{'\u03d9'})};
        Object[] arrobject11 = new Object[]{"zero", new CharArrayWrapper(new char[0])};
        Object[] arrobject12 = new Object[]{"digits", new CharArrayWrapper(new char[]{'\u03b1', '\u03b2', '\u03b3', '\u03b4', '\u03b5', '\u03db', '\u03b6', '\u03b7', '\u03b8'})};
        Object[] arrobject13 = new Object[]{"tens", new CharArrayWrapper(new char[]{'\u03b9', '\u03ba', '\u03bb', '\u03bc', '\u03bd', '\u03be', '\u03bf', '\u03c0', '\u03df'})};
        Object[] arrobject14 = new Object[]{"hundreds", new CharArrayWrapper(new char[]{'\u03c1', '\u03c2', '\u03c4', '\u03c5', '\u03c6', '\u03c7', '\u03c8', '\u03c9', '\u03e1'})};
        Object[] arrobject15 = new Object[]{"tables", new StringArrayWrapper(new String[]{"hundreds", "tens", "digits"})};
        return new Object[][]{arrobject, {"help_language", "el"}, arrobject2, arrobject3, arrobject4, arrobject5, arrobject6, arrobject7, arrobject8, arrobject9, arrobject10, arrobject11, arrobject12, arrobject13, arrobject14, arrobject15};
    }
}

