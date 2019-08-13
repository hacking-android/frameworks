/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.LeafNodeImpl;
import org.w3c.dom.EntityReference;

public class EntityReferenceImpl
extends LeafNodeImpl
implements EntityReference {
    private String name;

    EntityReferenceImpl(DocumentImpl documentImpl, String string) {
        super(documentImpl);
        this.name = string;
    }

    @Override
    public String getNodeName() {
        return this.name;
    }

    @Override
    public short getNodeType() {
        return 5;
    }
}

