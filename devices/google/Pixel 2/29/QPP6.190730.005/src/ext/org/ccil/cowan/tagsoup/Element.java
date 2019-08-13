/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import org.ccil.cowan.tagsoup.AttributesImpl;
import org.ccil.cowan.tagsoup.ElementType;
import org.xml.sax.Attributes;

public class Element {
    private boolean preclosed;
    private AttributesImpl theAtts;
    private Element theNext;
    private ElementType theType;

    public Element(ElementType elementType, boolean bl) {
        this.theType = elementType;
        this.theAtts = bl ? new AttributesImpl(elementType.atts()) : new AttributesImpl();
        this.theNext = null;
        this.preclosed = false;
    }

    public void anonymize() {
        for (int i = this.theAtts.getLength() - 1; i >= 0; --i) {
            if (!this.theAtts.getType(i).equals("ID") && !this.theAtts.getQName(i).equals("name")) continue;
            this.theAtts.removeAttribute(i);
        }
    }

    public AttributesImpl atts() {
        return this.theAtts;
    }

    public boolean canContain(Element element) {
        return this.theType.canContain(element.theType);
    }

    public void clean() {
        for (int i = this.theAtts.getLength() - 1; i >= 0; --i) {
            String string = this.theAtts.getLocalName(i);
            if (this.theAtts.getValue(i) != null && string != null && string.length() != 0) continue;
            this.theAtts.removeAttribute(i);
        }
    }

    public int flags() {
        return this.theType.flags();
    }

    public boolean isPreclosed() {
        return this.preclosed;
    }

    public String localName() {
        return this.theType.localName();
    }

    public int memberOf() {
        return this.theType.memberOf();
    }

    public int model() {
        return this.theType.model();
    }

    public String name() {
        return this.theType.name();
    }

    public String namespace() {
        return this.theType.namespace();
    }

    public Element next() {
        return this.theNext;
    }

    public ElementType parent() {
        return this.theType.parent();
    }

    public void preclose() {
        this.preclosed = true;
    }

    public void setAttribute(String string, String string2, String string3) {
        this.theType.setAttribute(this.theAtts, string, string2, string3);
    }

    public void setNext(Element element) {
        this.theNext = element;
    }

    public ElementType type() {
        return this.theType;
    }
}

