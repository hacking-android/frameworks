/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

public final class ExtendedType {
    private int hash;
    private String localName;
    private String namespace;
    private int nodetype;

    public ExtendedType(int n, String string, String string2) {
        this.nodetype = n;
        this.namespace = string;
        this.localName = string2;
        this.hash = string.hashCode() + n + string2.hashCode();
    }

    public ExtendedType(int n, String string, String string2, int n2) {
        this.nodetype = n;
        this.namespace = string;
        this.localName = string2;
        this.hash = n2;
    }

    public boolean equals(ExtendedType extendedType) {
        boolean bl = false;
        try {
            boolean bl2;
            if (extendedType.nodetype == this.nodetype && extendedType.localName.equals(this.localName) && (bl2 = extendedType.namespace.equals(this.namespace))) {
                bl = true;
            }
            return bl;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
    }

    public String getLocalName() {
        return this.localName;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public int getNodeType() {
        return this.nodetype;
    }

    public int hashCode() {
        return this.hash;
    }

    protected void redefine(int n, String string, String string2) {
        this.nodetype = n;
        this.namespace = string;
        this.localName = string2;
        this.hash = string.hashCode() + n + string2.hashCode();
    }

    protected void redefine(int n, String string, String string2, int n2) {
        this.nodetype = n;
        this.namespace = string;
        this.localName = string2;
        this.hash = n2;
    }
}

