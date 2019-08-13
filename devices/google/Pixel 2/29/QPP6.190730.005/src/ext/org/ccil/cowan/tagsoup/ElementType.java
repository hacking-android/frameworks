/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import org.ccil.cowan.tagsoup.AttributesImpl;
import org.ccil.cowan.tagsoup.Schema;

public class ElementType {
    private AttributesImpl theAtts;
    private int theFlags;
    private String theLocalName;
    private int theMemberOf;
    private int theModel;
    private String theName;
    private String theNamespace;
    private ElementType theParent;
    private Schema theSchema;

    public ElementType(String string, int n, int n2, int n3, Schema schema) {
        this.theName = string;
        this.theModel = n;
        this.theMemberOf = n2;
        this.theFlags = n3;
        this.theAtts = new AttributesImpl();
        this.theSchema = schema;
        this.theNamespace = this.namespace(string, false);
        this.theLocalName = this.localName(string);
    }

    public static String normalize(String string) {
        if (string == null) {
            return string;
        }
        if ((string = string.trim()).indexOf("  ") == -1) {
            return string;
        }
        boolean bl = false;
        int n = string.length();
        StringBuffer stringBuffer = new StringBuffer(n);
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == ' ') {
                if (!bl) {
                    stringBuffer.append(c);
                }
                bl = true;
                continue;
            }
            stringBuffer.append(c);
            bl = false;
        }
        return stringBuffer.toString();
    }

    public AttributesImpl atts() {
        return this.theAtts;
    }

    public boolean canContain(ElementType elementType) {
        boolean bl = (this.theModel & elementType.theMemberOf) != 0;
        return bl;
    }

    public int flags() {
        return this.theFlags;
    }

    public String localName() {
        return this.theLocalName;
    }

    public String localName(String string) {
        int n = string.indexOf(58);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1).intern();
    }

    public int memberOf() {
        return this.theMemberOf;
    }

    public int model() {
        return this.theModel;
    }

    public String name() {
        return this.theName;
    }

    public String namespace() {
        return this.theNamespace;
    }

    public String namespace(String charSequence, boolean bl) {
        int n = ((String)charSequence).indexOf(58);
        if (n == -1) {
            charSequence = bl ? "" : this.theSchema.getURI();
            return charSequence;
        }
        String string = ((String)charSequence).substring(0, n);
        if (string.equals("xml")) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("urn:x-prefix:");
        ((StringBuilder)charSequence).append(string);
        return ((StringBuilder)charSequence).toString().intern();
    }

    public ElementType parent() {
        return this.theParent;
    }

    public Schema schema() {
        return this.theSchema;
    }

    public void setAttribute(String string, String string2, String string3) {
        this.setAttribute(this.theAtts, string, string2, string3);
    }

    public void setAttribute(AttributesImpl attributesImpl, String string, String string2, String string3) {
        if (!string.equals("xmlns") && !string.startsWith("xmlns:")) {
            String string4 = this.namespace(string, true);
            String string5 = this.localName(string);
            int n = attributesImpl.getIndex(string);
            if (n == -1) {
                String string6 = string.intern();
                string = string2;
                if (string2 == null) {
                    string = "CDATA";
                }
                string2 = string3;
                if (!string.equals("CDATA")) {
                    string2 = ElementType.normalize(string3);
                }
                attributesImpl.addAttribute(string4, string5, string6, string, string2);
            } else {
                String string7 = string2;
                if (string2 == null) {
                    string7 = attributesImpl.getType(n);
                }
                string2 = string3;
                if (!string7.equals("CDATA")) {
                    string2 = ElementType.normalize(string3);
                }
                attributesImpl.setAttribute(n, string4, string5, string, string7, string2);
            }
            return;
        }
    }

    public void setFlags(int n) {
        this.theFlags = n;
    }

    public void setMemberOf(int n) {
        this.theMemberOf = n;
    }

    public void setModel(int n) {
        this.theModel = n;
    }

    public void setParent(ElementType elementType) {
        this.theParent = elementType;
    }
}

