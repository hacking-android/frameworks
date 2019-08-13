/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import java.util.Locale;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xml.utils.XMLCharacterRecognizer;
import org.apache.xml.utils.XMLString;
import org.apache.xml.utils.XMLStringFactory;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XMLStringFactoryImpl;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class XString
extends XObject
implements XMLString {
    public static final XString EMPTYSTRING = new XString("");
    static final long serialVersionUID = 2020470518395094525L;

    protected XString(Object object) {
        super(object);
    }

    public XString(String string) {
        super(string);
    }

    private static boolean isSpace(char c) {
        return XMLCharacterRecognizer.isWhiteSpace(c);
    }

    @Override
    public boolean bool() {
        boolean bl = this.str().length() > 0;
        return bl;
    }

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        xPathVisitor.visitStringLiteral(expressionOwner, this);
    }

    @Override
    public char charAt(int n) {
        return this.str().charAt(n);
    }

    @Override
    public int compareTo(XMLString xMLString) {
        int n = this.length();
        int n2 = xMLString.length();
        int n3 = 0;
        int n4 = 0;
        for (int i = java.lang.Math.min((int)n, (int)n2); i != 0; --i) {
            char c;
            char c2 = this.charAt(n3);
            if (c2 != (c = xMLString.charAt(n4))) {
                return c2 - c;
            }
            ++n3;
            ++n4;
        }
        return n - n2;
    }

    @Override
    public int compareToIgnoreCase(XMLString xMLString) {
        throw new WrappedRuntimeException(new NoSuchMethodException("Java 1.2 method, not yet implemented"));
    }

    @Override
    public XMLString concat(String string) {
        return new XString(this.str().concat(string));
    }

    @Override
    public void dispatchAsComment(LexicalHandler lexicalHandler) throws SAXException {
        String string = this.str();
        lexicalHandler.comment(string.toCharArray(), 0, string.length());
    }

    @Override
    public void dispatchCharactersEvents(ContentHandler contentHandler) throws SAXException {
        String string = this.str();
        contentHandler.characters(string.toCharArray(), 0, string.length());
    }

    @Override
    public boolean endsWith(String string) {
        return this.str().endsWith(string);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof XNodeSet) {
            return object.equals(this);
        }
        if (object instanceof XNumber) {
            return object.equals(this);
        }
        return this.str().equals(object.toString());
    }

    @Override
    public boolean equals(String string) {
        return this.str().equals(string);
    }

    @Override
    public boolean equals(XMLString xMLString) {
        if (xMLString != null) {
            if (!xMLString.hasString()) {
                return xMLString.equals(this.str());
            }
            return this.str().equals(xMLString.toString());
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean equals(XObject xObject) {
        int n = xObject.getType();
        if (4 == n) {
            try {
                return xObject.equals(this);
            }
            catch (TransformerException transformerException) {
                throw new WrappedRuntimeException(transformerException);
            }
        }
        boolean bl = false;
        boolean bl2 = false;
        if (1 == n) {
            if (xObject.bool() != this.bool()) return bl2;
            return true;
        }
        if (2 != n) return this.xstr().equals(xObject.xstr());
        double d = xObject.num();
        double d2 = this.num();
        bl2 = bl;
        if (d != d2) return bl2;
        return true;
    }

    @Override
    public boolean equalsIgnoreCase(String string) {
        return this.str().equalsIgnoreCase(string);
    }

    @Override
    public XMLString fixWhiteSpace(boolean bl, boolean bl2, boolean bl3) {
        int n;
        int n2;
        int n3 = this.length();
        char[] arrc = new char[n3];
        this.getChars(0, n3, arrc, 0);
        int n4 = 0;
        for (n = 0; n < n3 && !XString.isSpace(arrc[n]); ++n) {
        }
        int n5 = n;
        int n6 = 0;
        for (n2 = n; n2 < n3; ++n2) {
            char c = arrc[n2];
            if (XString.isSpace(c)) {
                if (n6 == 0) {
                    if (' ' != c) {
                        n4 = 1;
                    }
                    int n7 = n5 + 1;
                    arrc[n5] = (char)32;
                    if (bl3 && n2 != 0) {
                        n5 = arrc[n2 - 1];
                        n = n6;
                        if (n5 != 46) {
                            n = n6;
                            if (n5 != 33) {
                                n = n6;
                                if (n5 != 63) {
                                    n = 1;
                                }
                            }
                        }
                        n5 = n7;
                    } else {
                        n = 1;
                        n5 = n7;
                    }
                } else {
                    n4 = 1;
                    n = 1;
                }
            } else {
                arrc[n5] = c;
                n = 0;
                ++n5;
            }
            n6 = n;
        }
        n = n4;
        n6 = n5;
        if (bl2) {
            n = n4;
            n6 = n5;
            if (1 <= n5) {
                n = n4;
                n6 = n5;
                if (' ' == arrc[n5 - 1]) {
                    n = 1;
                    n6 = n5 - 1;
                }
            }
        }
        n2 = 0;
        n5 = n;
        n4 = n2;
        if (bl) {
            n5 = n;
            n4 = n2;
            if (n6 > 0) {
                n5 = n;
                n4 = n2;
                if (' ' == arrc[0]) {
                    n5 = 1;
                    n4 = 0 + 1;
                }
            }
        }
        Object object = XMLStringFactoryImpl.getFactory();
        object = n5 != 0 ? ((XMLStringFactory)object).newstr(new String(arrc, n4, n6 - n4)) : this;
        return object;
    }

    @Override
    public void getChars(int n, int n2, char[] arrc, int n3) {
        this.str().getChars(n, n2, arrc, n3);
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public String getTypeString() {
        return "#STRING";
    }

    @Override
    public boolean hasString() {
        return true;
    }

    @Override
    public int hashCode() {
        return this.str().hashCode();
    }

    @Override
    public int indexOf(int n) {
        return this.str().indexOf(n);
    }

    @Override
    public int indexOf(int n, int n2) {
        return this.str().indexOf(n, n2);
    }

    @Override
    public int indexOf(String string) {
        return this.str().indexOf(string);
    }

    @Override
    public int indexOf(String string, int n) {
        return this.str().indexOf(string, n);
    }

    @Override
    public int indexOf(XMLString xMLString) {
        return this.str().indexOf(xMLString.toString());
    }

    @Override
    public int lastIndexOf(int n) {
        return this.str().lastIndexOf(n);
    }

    @Override
    public int lastIndexOf(int n, int n2) {
        return this.str().lastIndexOf(n, n2);
    }

    @Override
    public int lastIndexOf(String string) {
        return this.str().lastIndexOf(string);
    }

    @Override
    public int lastIndexOf(String string, int n) {
        return this.str().lastIndexOf(string, n);
    }

    @Override
    public int length() {
        return this.str().length();
    }

    @Override
    public double num() {
        return this.toDouble();
    }

    @Override
    public int rtf(XPathContext object) {
        object = ((XPathContext)object).createDocumentFragment();
        object.appendTextChild(this.str());
        return object.getDocument();
    }

    @Override
    public boolean startsWith(String string) {
        return this.startsWith(string, 0);
    }

    @Override
    public boolean startsWith(String string, int n) {
        return this.str().startsWith(string, n);
    }

    @Override
    public boolean startsWith(XMLString xMLString) {
        return this.startsWith(xMLString, 0);
    }

    @Override
    public boolean startsWith(XMLString xMLString, int n) {
        int n2 = n;
        int n3 = this.length();
        int n4 = 0;
        int n5 = xMLString.length();
        if (n >= 0) {
            int n6 = n5;
            if (n <= n3 - n5) {
                while (--n6 >= 0) {
                    if (this.charAt(n2) != xMLString.charAt(n4)) {
                        return false;
                    }
                    ++n2;
                    ++n4;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String str() {
        String string = this.m_obj != null ? (String)this.m_obj : "";
        return string;
    }

    @Override
    public XMLString substring(int n) {
        return new XString(this.str().substring(n));
    }

    @Override
    public XMLString substring(int n, int n2) {
        return new XString(this.str().substring(n, n2));
    }

    @Override
    public double toDouble() {
        double d;
        XMLString xMLString = this.trim();
        double d2 = Double.NaN;
        for (int i = 0; i < xMLString.length(); ++i) {
            char c = xMLString.charAt(i);
            if (c == '-' || c == '.' || c >= '0' && c <= '9') continue;
            return Double.NaN;
        }
        try {
            d = Double.parseDouble(xMLString.toString());
        }
        catch (NumberFormatException numberFormatException) {
            d = d2;
        }
        return d;
    }

    @Override
    public XMLString toLowerCase() {
        return new XString(this.str().toLowerCase());
    }

    @Override
    public XMLString toLowerCase(Locale locale) {
        return new XString(this.str().toLowerCase(locale));
    }

    @Override
    public XMLString toUpperCase() {
        return new XString(this.str().toUpperCase());
    }

    @Override
    public XMLString toUpperCase(Locale locale) {
        return new XString(this.str().toUpperCase(locale));
    }

    @Override
    public XMLString trim() {
        return new XString(this.str().trim());
    }

    @Override
    public XMLString xstr() {
        return this;
    }
}

