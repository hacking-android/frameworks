/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.util.Locale;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public interface XMLString {
    public char charAt(int var1);

    public int compareTo(XMLString var1);

    public int compareToIgnoreCase(XMLString var1);

    public XMLString concat(String var1);

    public void dispatchAsComment(LexicalHandler var1) throws SAXException;

    public void dispatchCharactersEvents(ContentHandler var1) throws SAXException;

    public boolean endsWith(String var1);

    public boolean equals(Object var1);

    public boolean equals(String var1);

    public boolean equals(XMLString var1);

    public boolean equalsIgnoreCase(String var1);

    public XMLString fixWhiteSpace(boolean var1, boolean var2, boolean var3);

    public void getChars(int var1, int var2, char[] var3, int var4);

    public boolean hasString();

    public int hashCode();

    public int indexOf(int var1);

    public int indexOf(int var1, int var2);

    public int indexOf(String var1);

    public int indexOf(String var1, int var2);

    public int indexOf(XMLString var1);

    public int lastIndexOf(int var1);

    public int lastIndexOf(int var1, int var2);

    public int lastIndexOf(String var1);

    public int lastIndexOf(String var1, int var2);

    public int length();

    public boolean startsWith(String var1);

    public boolean startsWith(String var1, int var2);

    public boolean startsWith(XMLString var1);

    public boolean startsWith(XMLString var1, int var2);

    public XMLString substring(int var1);

    public XMLString substring(int var1, int var2);

    public double toDouble();

    public XMLString toLowerCase();

    public XMLString toLowerCase(Locale var1);

    public String toString();

    public XMLString toUpperCase();

    public XMLString toUpperCase(Locale var1);

    public XMLString trim();
}

