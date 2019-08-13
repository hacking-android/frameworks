/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.LeafNodeImpl;
import org.w3c.dom.Notation;

public class NotationImpl
extends LeafNodeImpl
implements Notation {
    private String notationName;
    private String publicID;
    private String systemID;

    NotationImpl(DocumentImpl documentImpl, String string, String string2, String string3) {
        super(documentImpl);
    }

    @Override
    public String getNodeName() {
        return this.notationName;
    }

    @Override
    public short getNodeType() {
        return 12;
    }

    @Override
    public String getPublicId() {
        return this.publicID;
    }

    @Override
    public String getSystemId() {
        return this.systemID;
    }
}

