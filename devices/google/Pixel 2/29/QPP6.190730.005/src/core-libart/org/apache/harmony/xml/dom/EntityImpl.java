/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.NodeImpl;
import org.w3c.dom.Entity;

public class EntityImpl
extends NodeImpl
implements Entity {
    private String notationName;
    private String publicID;
    private String systemID;

    EntityImpl(DocumentImpl documentImpl, String string, String string2, String string3) {
        super(documentImpl);
        this.notationName = string;
        this.publicID = string2;
        this.systemID = string3;
    }

    @Override
    public String getInputEncoding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNodeName() {
        return this.getNotationName();
    }

    @Override
    public short getNodeType() {
        return 6;
    }

    @Override
    public String getNotationName() {
        return this.notationName;
    }

    @Override
    public String getPublicId() {
        return this.publicID;
    }

    @Override
    public String getSystemId() {
        return this.systemID;
    }

    @Override
    public String getXmlEncoding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getXmlVersion() {
        throw new UnsupportedOperationException();
    }
}

