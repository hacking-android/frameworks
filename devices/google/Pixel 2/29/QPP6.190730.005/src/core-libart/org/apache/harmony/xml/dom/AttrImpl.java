/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.ElementImpl;
import org.apache.harmony.xml.dom.NodeImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.TypeInfo;

public final class AttrImpl
extends NodeImpl
implements Attr {
    boolean isId;
    String localName;
    boolean namespaceAware;
    String namespaceURI;
    ElementImpl ownerElement;
    String prefix;
    private String value = "";

    AttrImpl(DocumentImpl documentImpl, String string) {
        super(documentImpl);
        AttrImpl.setName(this, string);
    }

    AttrImpl(DocumentImpl documentImpl, String string, String string2) {
        super(documentImpl);
        AttrImpl.setNameNS(this, string, string2);
    }

    @Override
    public String getLocalName() {
        String string = this.namespaceAware ? this.localName : null;
        return string;
    }

    @Override
    public String getName() {
        CharSequence charSequence;
        if (this.prefix != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.prefix);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(this.localName);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = this.localName;
        }
        return charSequence;
    }

    @Override
    public String getNamespaceURI() {
        return this.namespaceURI;
    }

    @Override
    public String getNodeName() {
        return this.getName();
    }

    @Override
    public short getNodeType() {
        return 2;
    }

    @Override
    public String getNodeValue() {
        return this.getValue();
    }

    @Override
    public Element getOwnerElement() {
        return this.ownerElement;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return NULL_TYPE_INFO;
    }

    @Override
    public boolean getSpecified() {
        boolean bl = this.value != null;
        return bl;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean isId() {
        return this.isId;
    }

    @Override
    public void setPrefix(String string) {
        this.prefix = AttrImpl.validatePrefix(string, this.namespaceAware, this.namespaceURI);
    }

    @Override
    public void setValue(String string) throws DOMException {
        this.value = string;
    }
}

