/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

import org.apache.xml.utils.res.CharArrayWrapper;
import org.apache.xml.utils.res.IntArrayWrapper;
import org.apache.xml.utils.res.LongArrayWrapper;
import org.apache.xml.utils.res.StringArrayWrapper;
import org.apache.xml.utils.res.XResourceBundle;

public class XResources_zh_TW
extends XResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"help_language", "zh"};
        Object[] arrobject2 = new Object[]{"alphabet", new CharArrayWrapper(new char[]{'\uffffff21', '\uffffff22', '\uffffff23', '\uffffff24', '\uffffff25', '\uffffff26', '\uffffff27', '\uffffff28', '\uffffff29', '\uffffff2a', '\uffffff2b', '\uffffff2c', '\uffffff2d', '\uffffff2e', '\uffffff2f', '\uffffff30', '\uffffff31', '\uffffff32', '\uffffff33', '\uffffff34', '\uffffff35', '\uffffff36', '\uffffff37', '\uffffff38', '\uffffff39', '\uffffff3a'})};
        CharArrayWrapper charArrayWrapper = new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'});
        IntArrayWrapper intArrayWrapper = new IntArrayWrapper(new int[]{1});
        CharArrayWrapper charArrayWrapper2 = new CharArrayWrapper(new char[]{'\u96f6'});
        LongArrayWrapper longArrayWrapper = new LongArrayWrapper(new long[]{100000000L, 10000L, 1000L, 100L, 10L});
        CharArrayWrapper charArrayWrapper3 = new CharArrayWrapper(new char[]{'\u5104', '\uffff842c', '\u4edf', '\u4f70', '\u62fe'});
        CharArrayWrapper charArrayWrapper4 = new CharArrayWrapper(new char[]{'\u58f9', '\uffff8cb3', '\u53c3', '\uffff8086', '\u4f0d', '\uffff9678', '\u67d2', '\u634c', '\u7396'});
        Object[] arrobject3 = new Object[]{"tables", new StringArrayWrapper(new String[]{"digits"})};
        return new Object[][]{{"ui_language", "zh"}, arrobject, {"language", "zh"}, arrobject2, {"tradAlphabet", charArrayWrapper}, {"orientation", "LeftToRight"}, {"numbering", "multiplicative-additive"}, {"multiplierOrder", "follows"}, {"numberGroups", intArrayWrapper}, {"zero", charArrayWrapper2}, {"multiplier", longArrayWrapper}, {"multiplierChar", charArrayWrapper3}, {"digits", charArrayWrapper4}, arrobject3};
    }
}

