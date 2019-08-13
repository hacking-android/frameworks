/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import java.io.PrintWriter;
import java.io.Writer;
import org.ccil.cowan.tagsoup.ScanHandler;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class PYXWriter
implements ScanHandler,
ContentHandler,
LexicalHandler {
    private static char[] dummy = new char[1];
    private String attrName;
    private PrintWriter theWriter;

    public PYXWriter(Writer writer) {
        this.theWriter = writer instanceof PrintWriter ? (PrintWriter)writer : new PrintWriter(writer);
    }

    @Override
    public void adup(char[] arrc, int n, int n2) throws SAXException {
        this.theWriter.println(this.attrName);
        this.attrName = null;
    }

    @Override
    public void aname(char[] arrc, int n, int n2) throws SAXException {
        this.theWriter.print('A');
        this.theWriter.write(arrc, n, n2);
        this.theWriter.print(' ');
        this.attrName = new String(arrc, n, n2);
    }

    @Override
    public void aval(char[] arrc, int n, int n2) throws SAXException {
        this.theWriter.write(arrc, n, n2);
        this.theWriter.println();
        this.attrName = null;
    }

    @Override
    public void cdsect(char[] arrc, int n, int n2) throws SAXException {
        this.pcdata(arrc, n, n2);
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        this.pcdata(arrc, n, n2);
    }

    @Override
    public void cmnt(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        this.cmnt(arrc, n, n2);
    }

    @Override
    public void decl(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void endCDATA() throws SAXException {
    }

    @Override
    public void endDTD() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
        this.theWriter.close();
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        string = string3;
        if (string3.length() == 0) {
            string = string2;
        }
        this.theWriter.print(')');
        this.theWriter.println(string);
    }

    @Override
    public void endEntity(String string) throws SAXException {
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
    }

    @Override
    public void entity(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void eof(char[] arrc, int n, int n2) throws SAXException {
        this.theWriter.close();
    }

    @Override
    public void etag(char[] arrc, int n, int n2) throws SAXException {
        this.theWriter.print(')');
        this.theWriter.write(arrc, n, n2);
        this.theWriter.println();
    }

    @Override
    public int getEntity() {
        return 0;
    }

    @Override
    public void gi(char[] arrc, int n, int n2) throws SAXException {
        this.theWriter.print('(');
        this.theWriter.write(arrc, n, n2);
        this.theWriter.println();
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        this.characters(arrc, n, n2);
    }

    @Override
    public void pcdata(char[] arrc, int n, int n2) throws SAXException {
        if (n2 == 0) {
            return;
        }
        char c = '\u0000';
        for (int i = n; i < n2 + n; ++i) {
            if (arrc[i] == '\n') {
                if (c != '\u0000') {
                    this.theWriter.println();
                }
                this.theWriter.println("-\\n");
                c = '\u0000';
                continue;
            }
            if (c == '\u0000') {
                this.theWriter.print('-');
            }
            if ((c = arrc[i]) != '\t') {
                if (c != '\\') {
                    this.theWriter.print(arrc[i]);
                } else {
                    this.theWriter.print("\\\\");
                }
            } else {
                this.theWriter.print("\\t");
            }
            c = '\u0001';
        }
        if (c != '\u0000') {
            this.theWriter.println();
        }
    }

    @Override
    public void pi(char[] arrc, int n, int n2) throws SAXException {
        this.theWriter.write(arrc, n, n2);
        this.theWriter.println();
    }

    @Override
    public void pitarget(char[] arrc, int n, int n2) throws SAXException {
        this.theWriter.print('?');
        this.theWriter.write(arrc, n, n2);
        this.theWriter.write(32);
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        this.theWriter.print('?');
        this.theWriter.print(string);
        this.theWriter.print(' ');
        this.theWriter.println(string2);
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
    }

    @Override
    public void stagc(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void stage(char[] arrc, int n, int n2) throws SAXException {
        this.theWriter.println("!");
    }

    @Override
    public void startCDATA() throws SAXException {
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        string = string3;
        if (string3.length() == 0) {
            string = string2;
        }
        this.theWriter.print('(');
        this.theWriter.println(string);
        int n = attributes.getLength();
        for (int i = 0; i < n; ++i) {
            string = string2 = attributes.getQName(i);
            if (string2.length() == 0) {
                string = attributes.getLocalName(i);
            }
            this.theWriter.print('A');
            this.theWriter.print(string);
            this.theWriter.print(' ');
            this.theWriter.println(attributes.getValue(i));
        }
    }

    @Override
    public void startEntity(String string) throws SAXException {
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
    }
}

