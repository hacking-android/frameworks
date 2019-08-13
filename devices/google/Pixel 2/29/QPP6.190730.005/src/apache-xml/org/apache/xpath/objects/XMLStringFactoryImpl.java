/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.XMLString;
import org.apache.xml.utils.XMLStringFactory;
import org.apache.xpath.objects.XString;
import org.apache.xpath.objects.XStringForChars;
import org.apache.xpath.objects.XStringForFSB;

public class XMLStringFactoryImpl
extends XMLStringFactory {
    private static XMLStringFactory m_xstringfactory = new XMLStringFactoryImpl();

    public static XMLStringFactory getFactory() {
        return m_xstringfactory;
    }

    @Override
    public XMLString emptystr() {
        return XString.EMPTYSTRING;
    }

    @Override
    public XMLString newstr(String string) {
        return new XString(string);
    }

    @Override
    public XMLString newstr(FastStringBuffer fastStringBuffer, int n, int n2) {
        return new XStringForFSB(fastStringBuffer, n, n2);
    }

    @Override
    public XMLString newstr(char[] arrc, int n, int n2) {
        return new XStringForChars(arrc, n, n2);
    }
}

