/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

import org.apache.xml.utils.res.CharArrayWrapper;
import org.apache.xml.utils.res.IntArrayWrapper;
import org.apache.xml.utils.res.StringArrayWrapper;
import org.apache.xml.utils.res.XResourceBundle;

public class XResources_hy
extends XResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"alphabet", new CharArrayWrapper(new char[]{'\u0561', '\u0562', '\u0563', '\u0564', '\u0565', '\u0566', '\u0567', '\u0568', '\u0569', '\u056a', '\u056b', '\u056c', '\u056d', '\u056e', '\u056f', '\u0567', '\u0568', '\u0572', '\u0573', '\u0574', '\u0575', '\u0576', '\u0577', '\u0578', '\u0579', '\u057a', '\u057b', '\u057c', '\u057d', '\u057e', '\u057f', '\u0580', '\u0581', '\u0582', '\u0583', '\u0584'})};
        Object[] arrobject2 = new Object[]{"tradAlphabet", new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'})};
        Object[] arrobject3 = new Object[]{"numbering", "additive"};
        Object[] arrobject4 = new Object[]{"numberGroups", new IntArrayWrapper(new int[]{1000, 100, 10, 1})};
        Object[] arrobject5 = new Object[]{"digits", new CharArrayWrapper(new char[]{'\u0561', '\u0562', '\u0563', '\u0564', '\u0565', '\u0566', '\u0567', '\u0568', '\u0569'})};
        Object[] arrobject6 = new Object[]{"tens", new CharArrayWrapper(new char[]{'\u056a', '\u056b', '\u056c', '\u056d', '\u056e', '\u056f', '\u0567', '\u0568', '\u0572'})};
        Object[] arrobject7 = new Object[]{"hundreds", new CharArrayWrapper(new char[]{'\u0573', '\u0574', '\u0575', '\u0576', '\u0577', '\u0578', '\u0579', '\u057a', '\u057b'})};
        CharArrayWrapper charArrayWrapper = new CharArrayWrapper(new char[]{'\u057c', '\u057d', '\u057e', '\u057f', '\u0580', '\u0581', '\u0582', '\u0583', '\u0584'});
        Object[] arrobject8 = new Object[]{"tables", new StringArrayWrapper(new String[]{"thousands", "hundreds", "tens", "digits"})};
        return new Object[][]{{"ui_language", "hy"}, {"help_language", "hy"}, {"language", "hy"}, arrobject, arrobject2, {"orientation", "LeftToRight"}, arrobject3, arrobject4, arrobject5, arrobject6, arrobject7, {"thousands", charArrayWrapper}, arrobject8};
    }
}

