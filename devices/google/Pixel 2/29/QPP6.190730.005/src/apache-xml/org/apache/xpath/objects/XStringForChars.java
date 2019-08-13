/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xpath.objects.XString;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class XStringForChars
extends XString {
    static final long serialVersionUID = -2235248887220850467L;
    int m_length;
    int m_start;
    protected String m_strCache = null;

    private XStringForChars(String string) {
        super(string);
        throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_XSTRINGFORCHARS_CANNOT_TAKE_STRING", null));
    }

    public XStringForChars(char[] arrc, int n, int n2) {
        super(arrc);
        this.m_start = n;
        this.m_length = n2;
        if (arrc != null) {
            return;
        }
        throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_FASTSTRINGBUFFER_CANNOT_BE_NULL", null));
    }

    @Override
    public void appendToFsb(FastStringBuffer fastStringBuffer) {
        fastStringBuffer.append((char[])this.m_obj, this.m_start, this.m_length);
    }

    @Override
    public char charAt(int n) {
        return ((char[])this.m_obj)[this.m_start + n];
    }

    @Override
    public void dispatchAsComment(LexicalHandler lexicalHandler) throws SAXException {
        lexicalHandler.comment((char[])this.m_obj, this.m_start, this.m_length);
    }

    @Override
    public void dispatchCharactersEvents(ContentHandler contentHandler) throws SAXException {
        contentHandler.characters((char[])this.m_obj, this.m_start, this.m_length);
    }

    public FastStringBuffer fsb() {
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_FSB_NOT_SUPPORTED_XSTRINGFORCHARS", null));
    }

    @Override
    public void getChars(int n, int n2, char[] arrc, int n3) {
        System.arraycopy((char[])this.m_obj, this.m_start + n, arrc, n3, n2);
    }

    @Override
    public boolean hasString() {
        boolean bl = this.m_strCache != null;
        return bl;
    }

    @Override
    public int length() {
        return this.m_length;
    }

    @Override
    public Object object() {
        return this.str();
    }

    @Override
    public String str() {
        if (this.m_strCache == null) {
            this.m_strCache = new String((char[])this.m_obj, this.m_start, this.m_length);
        }
        return this.m_strCache;
    }
}

