/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import org.xml.sax.SAXException;

public interface ScanHandler {
    public void adup(char[] var1, int var2, int var3) throws SAXException;

    public void aname(char[] var1, int var2, int var3) throws SAXException;

    public void aval(char[] var1, int var2, int var3) throws SAXException;

    public void cdsect(char[] var1, int var2, int var3) throws SAXException;

    public void cmnt(char[] var1, int var2, int var3) throws SAXException;

    public void decl(char[] var1, int var2, int var3) throws SAXException;

    public void entity(char[] var1, int var2, int var3) throws SAXException;

    public void eof(char[] var1, int var2, int var3) throws SAXException;

    public void etag(char[] var1, int var2, int var3) throws SAXException;

    public int getEntity();

    public void gi(char[] var1, int var2, int var3) throws SAXException;

    public void pcdata(char[] var1, int var2, int var3) throws SAXException;

    public void pi(char[] var1, int var2, int var3) throws SAXException;

    public void pitarget(char[] var1, int var2, int var3) throws SAXException;

    public void stagc(char[] var1, int var2, int var3) throws SAXException;

    public void stage(char[] var1, int var2, int var3) throws SAXException;
}

