/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.dom3;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class NamespaceSupport {
    static final String PREFIX_XML = "xml".intern();
    static final String PREFIX_XMLNS = "xmlns".intern();
    public static final String XMLNS_URI;
    public static final String XML_URI;
    protected int[] fContext = new int[8];
    protected int fCurrentContext;
    protected String[] fNamespace = new String[32];
    protected int fNamespaceSize;
    protected String[] fPrefixes = new String[16];

    static {
        XML_URI = "http://www.w3.org/XML/1998/namespace".intern();
        XMLNS_URI = "http://www.w3.org/2000/xmlns/".intern();
    }

    public boolean declarePrefix(String string, String string2) {
        if (string != PREFIX_XML && string != PREFIX_XMLNS) {
            int n;
            String[] arrstring;
            for (n = this.fNamespaceSize; n > this.fContext[this.fCurrentContext]; n -= 2) {
                if (!this.fNamespace[n - 2].equals(string)) continue;
                this.fNamespace[n - 1] = string2;
                return true;
            }
            n = this.fNamespaceSize;
            String[] arrstring2 = this.fNamespace;
            if (n == arrstring2.length) {
                arrstring = new String[n * 2];
                System.arraycopy(arrstring2, 0, arrstring, 0, n);
                this.fNamespace = arrstring;
            }
            arrstring = this.fNamespace;
            n = this.fNamespaceSize;
            this.fNamespaceSize = n + 1;
            arrstring[n] = string;
            n = this.fNamespaceSize;
            this.fNamespaceSize = n + 1;
            arrstring[n] = string2;
            return true;
        }
        return false;
    }

    public Enumeration getAllPrefixes() {
        int n = 0;
        if (this.fPrefixes.length < this.fNamespace.length / 2) {
            this.fPrefixes = new String[this.fNamespaceSize];
        }
        int n2 = 2;
        do {
            boolean bl;
            boolean bl2 = true;
            if (n2 >= this.fNamespaceSize - 2) break;
            String string = this.fNamespace[n2 + 2];
            int n3 = 0;
            do {
                bl = bl2;
                if (n3 >= n) break;
                if (this.fPrefixes[n3] == string) {
                    bl = false;
                    break;
                }
                ++n3;
            } while (true);
            n3 = n;
            if (bl) {
                this.fPrefixes[n] = string;
                n3 = n + 1;
            }
            n2 += 2;
            n = n3;
        } while (true);
        return new Prefixes(this.fPrefixes, n);
    }

    public String getDeclaredPrefixAt(int n) {
        return this.fNamespace[this.fContext[this.fCurrentContext] + n * 2];
    }

    public int getDeclaredPrefixCount() {
        return (this.fNamespaceSize - this.fContext[this.fCurrentContext]) / 2;
    }

    public String getPrefix(String string) {
        for (int i = this.fNamespaceSize; i > 0; i -= 2) {
            if (!this.fNamespace[i - 1].equals(string) || !this.getURI(this.fNamespace[i - 2]).equals(string)) continue;
            return this.fNamespace[i - 2];
        }
        return null;
    }

    public String getURI(String string) {
        for (int i = this.fNamespaceSize; i > 0; i -= 2) {
            if (!this.fNamespace[i - 2].equals(string)) continue;
            return this.fNamespace[i - 1];
        }
        return null;
    }

    public void popContext() {
        int[] arrn = this.fContext;
        int n = this.fCurrentContext;
        this.fCurrentContext = n - 1;
        this.fNamespaceSize = arrn[n];
    }

    public void pushContext() {
        int[] arrn;
        int n = this.fCurrentContext;
        int[] arrn2 = this.fContext;
        if (n + 1 == arrn2.length) {
            arrn = new int[arrn2.length * 2];
            System.arraycopy(arrn2, 0, arrn, 0, arrn2.length);
            this.fContext = arrn;
        }
        arrn = this.fContext;
        this.fCurrentContext = n = this.fCurrentContext + 1;
        arrn[n] = this.fNamespaceSize;
    }

    public void reset() {
        int n;
        this.fNamespaceSize = 0;
        this.fCurrentContext = 0;
        Object[] arrobject = this.fContext;
        int n2 = this.fCurrentContext;
        arrobject[n2] = n = this.fNamespaceSize;
        arrobject = this.fNamespace;
        this.fNamespaceSize = n + 1;
        arrobject[n] = (int)PREFIX_XML;
        n = this.fNamespaceSize;
        this.fNamespaceSize = n + 1;
        arrobject[n] = (int)XML_URI;
        n = this.fNamespaceSize;
        this.fNamespaceSize = n + 1;
        arrobject[n] = (int)PREFIX_XMLNS;
        n = this.fNamespaceSize;
        this.fNamespaceSize = n + 1;
        arrobject[n] = (int)XMLNS_URI;
        this.fCurrentContext = n2 + 1;
    }

    protected final class Prefixes
    implements Enumeration {
        private int counter = 0;
        private String[] prefixes;
        private int size = 0;

        public Prefixes(String[] arrstring, int n) {
            this.prefixes = arrstring;
            this.size = n;
        }

        @Override
        public boolean hasMoreElements() {
            boolean bl = this.counter < this.size;
            return bl;
        }

        public Object nextElement() {
            if (this.counter < this.size) {
                String[] arrstring = NamespaceSupport.this.fPrefixes;
                int n = this.counter;
                this.counter = n + 1;
                return arrstring[n];
            }
            throw new NoSuchElementException("Illegal access to Namespace prefixes enumeration.");
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < this.size; ++i) {
                stringBuffer.append(this.prefixes[i]);
                stringBuffer.append(" ");
            }
            return stringBuffer.toString();
        }
    }

}

