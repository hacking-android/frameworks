/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import java.util.HashMap;
import java.util.Locale;
import org.ccil.cowan.tagsoup.ElementType;

public abstract class Schema {
    public static final int F_CDATA = 2;
    public static final int F_NOFORCE = 4;
    public static final int F_RESTART = 1;
    public static final int M_ANY = -1;
    public static final int M_EMPTY = 0;
    public static final int M_PCDATA = 1073741824;
    public static final int M_ROOT = Integer.MIN_VALUE;
    private HashMap theElementTypes = new HashMap();
    private HashMap theEntities = new HashMap();
    private String thePrefix = "";
    private ElementType theRoot = null;
    private String theURI = "";

    public void attribute(String string, String string2, String charSequence, String string3) {
        ElementType elementType = this.getElementType(string);
        if (elementType != null) {
            elementType.setAttribute(string2, (String)charSequence, string3);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Attribute ");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" specified for unknown element type ");
        ((StringBuilder)charSequence).append(string);
        throw new Error(((StringBuilder)charSequence).toString());
    }

    public void elementType(String string, int n, int n2, int n3) {
        ElementType elementType = new ElementType(string, n, n2, n3, this);
        this.theElementTypes.put(string.toLowerCase(Locale.ROOT), elementType);
        if (n2 == Integer.MIN_VALUE) {
            this.theRoot = elementType;
        }
    }

    public void entity(String string, int n) {
        this.theEntities.put(string, new Integer(n));
    }

    public ElementType getElementType(String string) {
        return (ElementType)this.theElementTypes.get(string.toLowerCase(Locale.ROOT));
    }

    public int getEntity(String object) {
        if ((object = (Integer)this.theEntities.get(object)) == null) {
            return 0;
        }
        return (Integer)object;
    }

    public String getPrefix() {
        return this.thePrefix;
    }

    public String getURI() {
        return this.theURI;
    }

    public void parent(String string, String string2) {
        Object object = this.getElementType(string);
        ElementType elementType = this.getElementType(string2);
        if (object != null) {
            if (elementType != null) {
                ((ElementType)object).setParent(elementType);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("No parent ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" for child ");
            ((StringBuilder)object).append(string);
            throw new Error(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No child ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" for parent ");
        ((StringBuilder)object).append(string2);
        throw new Error(((StringBuilder)object).toString());
    }

    public ElementType rootElementType() {
        return this.theRoot;
    }

    public void setPrefix(String string) {
        this.thePrefix = string;
    }

    public void setURI(String string) {
        this.theURI = string;
    }
}

