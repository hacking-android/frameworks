/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.XMLCharacterRecognizer;
import org.apache.xml.utils.XMLString;
import org.apache.xml.utils.XMLStringFactory;
import org.apache.xpath.objects.XMLStringFactoryImpl;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class XStringForFSB
extends XString {
    static final long serialVersionUID = -1533039186550674548L;
    protected int m_hash = 0;
    int m_length;
    int m_start;
    protected String m_strCache = null;

    private XStringForFSB(String string) {
        super(string);
        throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_FSB_CANNOT_TAKE_STRING", null));
    }

    public XStringForFSB(FastStringBuffer fastStringBuffer, int n, int n2) {
        super(fastStringBuffer);
        this.m_start = n;
        this.m_length = n2;
        if (fastStringBuffer != null) {
            return;
        }
        throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_FASTSTRINGBUFFER_CANNOT_BE_NULL", null));
    }

    private static boolean isSpace(char c) {
        return XMLCharacterRecognizer.isWhiteSpace(c);
    }

    @Override
    public void appendToFsb(FastStringBuffer fastStringBuffer) {
        fastStringBuffer.append(this.str());
    }

    @Override
    public char charAt(int n) {
        return this.fsb().charAt(this.m_start + n);
    }

    @Override
    public int compareTo(XMLString xMLString) {
        int n = this.m_length;
        int n2 = xMLString.length();
        FastStringBuffer fastStringBuffer = this.fsb();
        int n3 = this.m_start;
        int n4 = 0;
        for (int i = java.lang.Math.min((int)n, (int)n2); i != 0; --i) {
            char c;
            char c2 = fastStringBuffer.charAt(n3);
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
        int n = this.m_length;
        int n2 = xMLString.length();
        FastStringBuffer fastStringBuffer = this.fsb();
        int n3 = this.m_start;
        int n4 = 0;
        for (int i = java.lang.Math.min((int)n, (int)n2); i != 0; --i) {
            char c;
            char c2 = Character.toLowerCase(fastStringBuffer.charAt(n3));
            if (c2 != (c = Character.toLowerCase(xMLString.charAt(n4)))) {
                return c2 - c;
            }
            ++n3;
            ++n4;
        }
        return n - n2;
    }

    @Override
    public XMLString concat(String string) {
        return new XString(this.str().concat(string));
    }

    @Override
    public void dispatchAsComment(LexicalHandler lexicalHandler) throws SAXException {
        this.fsb().sendSAXComment(lexicalHandler, this.m_start, this.m_length);
    }

    @Override
    public void dispatchCharactersEvents(ContentHandler contentHandler) throws SAXException {
        this.fsb().sendSAXcharacters(contentHandler, this.m_start, this.m_length);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof XNumber) {
            return object.equals(this);
        }
        if (object instanceof XNodeSet) {
            return object.equals(this);
        }
        if (object instanceof XStringForFSB) {
            return this.equals((XMLString)object);
        }
        return this.equals(object.toString());
    }

    @Override
    public boolean equals(String string) {
        int n = this.m_length;
        if (n == string.length()) {
            FastStringBuffer fastStringBuffer = this.fsb();
            int n2 = this.m_start;
            int n3 = 0;
            while (n != 0) {
                if (fastStringBuffer.charAt(n2) != string.charAt(n3)) {
                    return false;
                }
                ++n2;
                ++n3;
                --n;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(XMLString xMLString) {
        if (this == xMLString) {
            return true;
        }
        int n = this.m_length;
        if (n == xMLString.length()) {
            FastStringBuffer fastStringBuffer = this.fsb();
            int n2 = this.m_start;
            int n3 = 0;
            while (n != 0) {
                if (fastStringBuffer.charAt(n2) != xMLString.charAt(n3)) {
                    return false;
                }
                ++n2;
                ++n3;
                --n;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(XObject object) {
        if (this == object) {
            return true;
        }
        if (((XObject)object).getType() == 2) {
            return ((XObject)object).equals(this);
        }
        int n = this.m_length;
        String string = ((XObject)object).str();
        if (n == string.length()) {
            object = this.fsb();
            int n2 = this.m_start;
            int n3 = 0;
            while (n != 0) {
                if (((FastStringBuffer)object).charAt(n2) != string.charAt(n3)) {
                    return false;
                }
                ++n2;
                ++n3;
                --n;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean equalsIgnoreCase(String string) {
        boolean bl = this.m_length == string.length() ? this.str().equalsIgnoreCase(string) : false;
        return bl;
    }

    @Override
    public XMLString fixWhiteSpace(boolean bl, boolean bl2, boolean bl3) {
        int n;
        int n2;
        int n3 = this.m_length;
        int n4 = this.m_start;
        Object object = new char[n3];
        Object object2 = this.fsb();
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        for (n2 = this.m_start; n2 < n4 + n3; ++n2) {
            char c = ((FastStringBuffer)object2).charAt(n2);
            if (XStringForFSB.isSpace(c)) {
                if (n7 == 0) {
                    if (' ' != c) {
                        n5 = 1;
                    }
                    int n8 = n6 + 1;
                    object[n6] = (char)32;
                    if (bl3 && n8 != 0) {
                        n6 = object[n8 - 1];
                        n = n7;
                        if (n6 != 46) {
                            n = n7;
                            if (n6 != 33) {
                                n = n7;
                                if (n6 != 63) {
                                    n = 1;
                                }
                            }
                        }
                        n6 = n8;
                        n7 = n;
                        continue;
                    }
                    n7 = 1;
                    n6 = n8;
                    continue;
                }
                n5 = 1;
                n7 = 1;
                continue;
            }
            object[n6] = c;
            n7 = 0;
            ++n6;
        }
        n7 = n5;
        n = n6;
        if (bl2) {
            n7 = n5;
            n = n6;
            if (1 <= n6) {
                n7 = n5;
                n = n6;
                if (' ' == object[n6 - 1]) {
                    n7 = 1;
                    n = n6 - 1;
                }
            }
        }
        n2 = 0;
        n6 = n7;
        n5 = n2;
        if (bl) {
            n6 = n7;
            n5 = n2;
            if (n > 0) {
                n6 = n7;
                n5 = n2;
                if (' ' == object[0]) {
                    n6 = 1;
                    n5 = 0 + 1;
                }
            }
        }
        object2 = XMLStringFactoryImpl.getFactory();
        object = n6 != 0 ? ((XMLStringFactory)object2).newstr((char[])object, n5, n - n5) : this;
        return object;
    }

    public FastStringBuffer fsb() {
        return (FastStringBuffer)this.m_obj;
    }

    @Override
    public void getChars(int n, int n2, char[] arrc, int n3) {
        int n4;
        n2 = n4 = n2 - n;
        if (n4 > this.m_length) {
            n2 = this.m_length;
        }
        n4 = n2;
        if (n2 > arrc.length - n3) {
            n4 = arrc.length - n3;
        }
        int n5 = this.m_start;
        n2 = n3;
        FastStringBuffer fastStringBuffer = this.fsb();
        n3 = this.m_start + n;
        while (n3 < n5 + n + n4) {
            arrc[n2] = fastStringBuffer.charAt(n3);
            ++n3;
            ++n2;
        }
    }

    @Override
    public boolean hasString() {
        boolean bl = this.m_strCache != null;
        return bl;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int indexOf(int n) {
        return this.indexOf(n, 0);
    }

    @Override
    public int indexOf(int n, int n2) {
        int n3;
        int n4 = this.m_start;
        int n5 = this.m_length;
        FastStringBuffer fastStringBuffer = this.fsb();
        if (n2 < 0) {
            n3 = 0;
        } else {
            n3 = n2;
            if (n2 >= this.m_length) {
                return -1;
            }
        }
        for (n2 = this.m_start + n3; n2 < n4 + n5; ++n2) {
            if (fastStringBuffer.charAt(n2) != n) continue;
            return n2 - this.m_start;
        }
        return -1;
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
    public boolean startsWith(XMLString xMLString) {
        return this.startsWith(xMLString, 0);
    }

    @Override
    public boolean startsWith(XMLString xMLString, int n) {
        FastStringBuffer fastStringBuffer = this.fsb();
        int n2 = this.m_start;
        n2 += n;
        int n3 = this.m_length;
        n3 = 0;
        int n4 = xMLString.length();
        if (n >= 0) {
            int n5 = n4;
            if (n <= this.m_length - n4) {
                while (--n5 >= 0) {
                    if (fastStringBuffer.charAt(n2) != xMLString.charAt(n3)) {
                        return false;
                    }
                    ++n2;
                    ++n3;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String str() {
        if (this.m_strCache == null) {
            this.m_strCache = this.fsb().getString(this.m_start, this.m_length);
        }
        return this.m_strCache;
    }

    @Override
    public XMLString substring(int n) {
        int n2 = this.m_length - n;
        if (n2 <= 0) {
            return XString.EMPTYSTRING;
        }
        int n3 = this.m_start;
        return new XStringForFSB(this.fsb(), n3 + n, n2);
    }

    @Override
    public XMLString substring(int n, int n2) {
        int n3;
        n2 = n3 = n2 - n;
        if (n3 > this.m_length) {
            n2 = this.m_length;
        }
        if (n2 <= 0) {
            return XString.EMPTYSTRING;
        }
        n3 = this.m_start;
        return new XStringForFSB(this.fsb(), n3 + n, n2);
    }

    @Override
    public double toDouble() {
        int n;
        if (this.m_length == 0) {
            return Double.NaN;
        }
        String string = this.fsb().getString(this.m_start, this.m_length);
        for (n = 0; n < this.m_length && XMLCharacterRecognizer.isWhiteSpace(string.charAt(n)); ++n) {
        }
        if (n == this.m_length) {
            return Double.NaN;
        }
        int n2 = n;
        if (string.charAt(n) == '-') {
            n2 = n + 1;
        }
        do {
            n = ++n2;
            if (n2 >= this.m_length) break;
            char c = string.charAt(n2);
            if (c == '.') continue;
            n = n2;
            if (c < '0') break;
            if (c <= '9') continue;
            n = n2;
            break;
        } while (true);
        while (n < this.m_length && XMLCharacterRecognizer.isWhiteSpace(string.charAt(n))) {
            ++n;
        }
        if (n != this.m_length) {
            return Double.NaN;
        }
        try {
            Double d = new Double(string);
            double d2 = d;
            return d2;
        }
        catch (NumberFormatException numberFormatException) {
            return Double.NaN;
        }
    }

    @Override
    public XMLString trim() {
        return this.fixWhiteSpace(true, true, false);
    }
}

