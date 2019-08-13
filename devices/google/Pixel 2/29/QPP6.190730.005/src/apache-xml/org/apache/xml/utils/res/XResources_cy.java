/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

import org.apache.xml.utils.res.CharArrayWrapper;
import org.apache.xml.utils.res.IntArrayWrapper;
import org.apache.xml.utils.res.LongArrayWrapper;
import org.apache.xml.utils.res.StringArrayWrapper;
import org.apache.xml.utils.res.XResourceBundle;

public class XResources_cy
extends XResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"help_language", "cy"};
        Object[] arrobject2 = new Object[]{"language", "cy"};
        CharArrayWrapper charArrayWrapper = new CharArrayWrapper(new char[]{'\u0430', '\u0432', '\u0433', '\u0434', '\u0435', '\u0437', '\u0438', '\u0439', '\u04a9', '\u0457', '\u043a', '\u043b', '\u043c', '\u043d', '\u046f', '\u043e', '\u043f', '\u0447', '\u0440', '\u0441', '\u0442', '\u0443', '\u0444', '\u0445', '\u0470', '\u0460', '\u0446'});
        Object[] arrobject3 = new Object[]{"tradAlphabet", new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'})};
        Object[] arrobject4 = new Object[]{"multiplierOrder", "precedes"};
        IntArrayWrapper intArrayWrapper = new IntArrayWrapper(new int[]{100, 10, 1});
        LongArrayWrapper longArrayWrapper = new LongArrayWrapper(new long[]{1000L});
        CharArrayWrapper charArrayWrapper2 = new CharArrayWrapper(new char[]{'\u03d9'});
        Object[] arrobject5 = new Object[]{"zero", new CharArrayWrapper(new char[0])};
        CharArrayWrapper charArrayWrapper3 = new CharArrayWrapper(new char[]{'\u0430', '\u0432', '\u0433', '\u0434', '\u0435', '\u0437', '\u0438', '\u0439', '\u04a9'});
        Object[] arrobject6 = new Object[]{"tens", new CharArrayWrapper(new char[]{'\u0457', '\u043a', '\u043b', '\u043c', '\u043d', '\u046f', '\u043e', '\u043f', '\u0447'})};
        CharArrayWrapper charArrayWrapper4 = new CharArrayWrapper(new char[]{'\u0440', '\u0441', '\u0442', '\u0443', '\u0444', '\u0445', '\u0470', '\u0460', '\u0446'});
        Object[] arrobject7 = new Object[]{"tables", new StringArrayWrapper(new String[]{"hundreds", "tens", "digits"})};
        return new Object[][]{{"ui_language", "cy"}, arrobject, arrobject2, {"alphabet", charArrayWrapper}, arrobject3, {"orientation", "LeftToRight"}, {"numbering", "multiplicative-additive"}, arrobject4, {"numberGroups", intArrayWrapper}, {"multiplier", longArrayWrapper}, {"multiplierChar", charArrayWrapper2}, arrobject5, {"digits", charArrayWrapper3}, arrobject6, {"hundreds", charArrayWrapper4}, arrobject7};
    }
}

