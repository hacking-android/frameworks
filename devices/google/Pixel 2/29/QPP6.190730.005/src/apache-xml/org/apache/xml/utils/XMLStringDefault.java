/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.util.Locale;
import org.apache.xml.utils.XMLString;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class XMLStringDefault
implements XMLString {
    private String m_str;

    public XMLStringDefault(String string) {
        this.m_str = string;
    }

    @Override
    public char charAt(int n) {
        return this.m_str.charAt(n);
    }

    @Override
    public int compareTo(XMLString xMLString) {
        return this.m_str.compareTo(xMLString.toString());
    }

    @Override
    public int compareToIgnoreCase(XMLString xMLString) {
        return this.m_str.compareToIgnoreCase(xMLString.toString());
    }

    @Override
    public XMLString concat(String string) {
        return new XMLStringDefault(this.m_str.concat(string));
    }

    @Override
    public void dispatchAsComment(LexicalHandler lexicalHandler) throws SAXException {
    }

    @Override
    public void dispatchCharactersEvents(ContentHandler contentHandler) throws SAXException {
    }

    @Override
    public boolean endsWith(String string) {
        return this.m_str.endsWith(string);
    }

    @Override
    public boolean equals(Object object) {
        return this.m_str.equals(object);
    }

    @Override
    public boolean equals(String string) {
        return this.m_str.equals(string);
    }

    @Override
    public boolean equals(XMLString xMLString) {
        return this.m_str.equals(xMLString.toString());
    }

    @Override
    public boolean equalsIgnoreCase(String string) {
        return this.m_str.equalsIgnoreCase(string);
    }

    @Override
    public XMLString fixWhiteSpace(boolean bl, boolean bl2, boolean bl3) {
        return new XMLStringDefault(this.m_str.trim());
    }

    @Override
    public void getChars(int n, int n2, char[] arrc, int n3) {
        while (n < n2) {
            arrc[n3] = this.m_str.charAt(n);
            ++n;
            ++n3;
        }
    }

    @Override
    public boolean hasString() {
        return true;
    }

    @Override
    public int hashCode() {
        return this.m_str.hashCode();
    }

    @Override
    public int indexOf(int n) {
        return this.m_str.indexOf(n);
    }

    @Override
    public int indexOf(int n, int n2) {
        return this.m_str.indexOf(n, n2);
    }

    @Override
    public int indexOf(String string) {
        return this.m_str.indexOf(string);
    }

    @Override
    public int indexOf(String string, int n) {
        return this.m_str.indexOf(string, n);
    }

    @Override
    public int indexOf(XMLString xMLString) {
        return this.m_str.indexOf(xMLString.toString());
    }

    @Override
    public int lastIndexOf(int n) {
        return this.m_str.lastIndexOf(n);
    }

    @Override
    public int lastIndexOf(int n, int n2) {
        return this.m_str.lastIndexOf(n, n2);
    }

    @Override
    public int lastIndexOf(String string) {
        return this.m_str.lastIndexOf(string);
    }

    @Override
    public int lastIndexOf(String string, int n) {
        return this.m_str.lastIndexOf(string, n);
    }

    @Override
    public int length() {
        return this.m_str.length();
    }

    @Override
    public boolean startsWith(String string) {
        return this.m_str.startsWith(string);
    }

    @Override
    public boolean startsWith(String string, int n) {
        return this.m_str.startsWith(string, n);
    }

    @Override
    public boolean startsWith(XMLString xMLString) {
        return this.m_str.startsWith(xMLString.toString());
    }

    @Override
    public boolean startsWith(XMLString xMLString, int n) {
        return this.m_str.startsWith(xMLString.toString(), n);
    }

    @Override
    public XMLString substring(int n) {
        return new XMLStringDefault(this.m_str.substring(n));
    }

    @Override
    public XMLString substring(int n, int n2) {
        return new XMLStringDefault(this.m_str.substring(n, n2));
    }

    @Override
    public double toDouble() {
        try {
            double d = Double.valueOf(this.m_str);
            return d;
        }
        catch (NumberFormatException numberFormatException) {
            return Double.NaN;
        }
    }

    @Override
    public XMLString toLowerCase() {
        return new XMLStringDefault(this.m_str.toLowerCase());
    }

    @Override
    public XMLString toLowerCase(Locale locale) {
        return new XMLStringDefault(this.m_str.toLowerCase(locale));
    }

    @Override
    public String toString() {
        return this.m_str;
    }

    @Override
    public XMLString toUpperCase() {
        return new XMLStringDefault(this.m_str.toUpperCase());
    }

    @Override
    public XMLString toUpperCase(Locale locale) {
        return new XMLStringDefault(this.m_str.toUpperCase(locale));
    }

    @Override
    public XMLString trim() {
        return new XMLStringDefault(this.m_str.trim());
    }
}

