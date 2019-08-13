/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

import org.apache.xml.utils.res.CharArrayWrapper;
import org.apache.xml.utils.res.XResourceBundle;

public class XResources_en
extends XResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"help_language", "en"};
        Object[] arrobject2 = new Object[]{"alphabet", new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'})};
        CharArrayWrapper charArrayWrapper = new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'});
        return new Object[][]{{"ui_language", "en"}, arrobject, {"language", "en"}, arrobject2, {"tradAlphabet", charArrayWrapper}, {"orientation", "LeftToRight"}, {"numbering", "additive"}};
    }
}

