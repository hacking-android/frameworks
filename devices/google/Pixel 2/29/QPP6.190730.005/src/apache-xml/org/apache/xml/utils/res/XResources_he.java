/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

import org.apache.xml.utils.res.CharArrayWrapper;
import org.apache.xml.utils.res.IntArrayWrapper;
import org.apache.xml.utils.res.StringArrayWrapper;
import org.apache.xml.utils.res.XResourceBundle;

public class XResources_he
extends XResourceBundle {
    @Override
    public Object[][] getContents() {
        CharArrayWrapper charArrayWrapper = new CharArrayWrapper(new char[]{'\u05d0', '\u05d1', '\u05d2', '\u05d3', '\u05d4', '\u05d5', '\u05d6', '\u05d7', '\u05d8', '\u05d9', '\u05da', '\u05db', '\u05dc', '\u05dd', '\u05de', '\u05df', '\u05e0', '\u05e1'});
        CharArrayWrapper charArrayWrapper2 = new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'});
        IntArrayWrapper intArrayWrapper = new IntArrayWrapper(new int[]{10, 1});
        CharArrayWrapper charArrayWrapper3 = new CharArrayWrapper(new char[]{'\u05d0', '\u05d1', '\u05d2', '\u05d3', '\u05d4', '\u05d5', '\u05d6', '\u05d7', '\u05d8'});
        CharArrayWrapper charArrayWrapper4 = new CharArrayWrapper(new char[]{'\u05d9', '\u05da', '\u05db', '\u05dc', '\u05dd', '\u05de', '\u05df', '\u05e0', '\u05e1'});
        StringArrayWrapper stringArrayWrapper = new StringArrayWrapper(new String[]{"tens", "digits"});
        return new Object[][]{{"ui_language", "he"}, {"help_language", "he"}, {"language", "he"}, {"alphabet", charArrayWrapper}, {"tradAlphabet", charArrayWrapper2}, {"orientation", "RightToLeft"}, {"numbering", "additive"}, {"numberGroups", intArrayWrapper}, {"digits", charArrayWrapper3}, {"tens", charArrayWrapper4}, {"tables", stringArrayWrapper}};
    }
}

