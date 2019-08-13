/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml;

import org.xml.sax.Attributes;

abstract class ExpatAttributes
implements Attributes {
    private static final String CDATA = "CDATA";

    private static native int getIndex(long var0, String var2, String var3);

    private static native int getIndexForQName(long var0, String var2);

    private static native String getLocalName(long var0, long var2, int var4);

    private static native String getQName(long var0, long var2, int var4);

    private static native String getURI(long var0, long var2, int var4);

    private static native String getValue(long var0, String var2, String var3);

    private static native String getValueByIndex(long var0, int var2);

    private static native String getValueForQName(long var0, String var2);

    protected native void freeAttributes(long var1);

    @Override
    public int getIndex(String string) {
        if (string != null) {
            long l = this.getPointer();
            if (l == 0L) {
                return -1;
            }
            return ExpatAttributes.getIndexForQName(l, string);
        }
        throw new NullPointerException("qName == null");
    }

    @Override
    public int getIndex(String string, String string2) {
        if (string != null) {
            if (string2 != null) {
                long l = this.getPointer();
                if (l == 0L) {
                    return -1;
                }
                return ExpatAttributes.getIndex(l, string, string2);
            }
            throw new NullPointerException("localName == null");
        }
        throw new NullPointerException("uri == null");
    }

    @Override
    public abstract int getLength();

    @Override
    public String getLocalName(int n) {
        String string = n >= 0 && n < this.getLength() ? ExpatAttributes.getLocalName(this.getParserPointer(), this.getPointer(), n) : null;
        return string;
    }

    abstract long getParserPointer();

    public abstract long getPointer();

    @Override
    public String getQName(int n) {
        String string = n >= 0 && n < this.getLength() ? ExpatAttributes.getQName(this.getParserPointer(), this.getPointer(), n) : null;
        return string;
    }

    @Override
    public String getType(int n) {
        String string = n >= 0 && n < this.getLength() ? "CDATA" : null;
        return string;
    }

    @Override
    public String getType(String string) {
        string = this.getIndex(string) == -1 ? null : "CDATA";
        return string;
    }

    @Override
    public String getType(String string, String string2) {
        if (string != null) {
            if (string2 != null) {
                string = this.getIndex(string, string2) == -1 ? null : "CDATA";
                return string;
            }
            throw new NullPointerException("localName == null");
        }
        throw new NullPointerException("uri == null");
    }

    @Override
    public String getURI(int n) {
        if (n >= 0 && n < this.getLength()) {
            return ExpatAttributes.getURI(this.getParserPointer(), this.getPointer(), n);
        }
        return null;
    }

    @Override
    public String getValue(int n) {
        String string = n >= 0 && n < this.getLength() ? ExpatAttributes.getValueByIndex(this.getPointer(), n) : null;
        return string;
    }

    @Override
    public String getValue(String string) {
        if (string != null) {
            long l = this.getPointer();
            if (l == 0L) {
                return null;
            }
            return ExpatAttributes.getValueForQName(l, string);
        }
        throw new NullPointerException("qName == null");
    }

    @Override
    public String getValue(String string, String string2) {
        if (string != null) {
            if (string2 != null) {
                long l = this.getPointer();
                if (l == 0L) {
                    return null;
                }
                return ExpatAttributes.getValue(l, string, string2);
            }
            throw new NullPointerException("localName == null");
        }
        throw new NullPointerException("uri == null");
    }
}

