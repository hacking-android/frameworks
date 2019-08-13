/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.InnerNodeImpl;
import org.w3c.dom.DocumentFragment;

public class DocumentFragmentImpl
extends InnerNodeImpl
implements DocumentFragment {
    DocumentFragmentImpl(DocumentImpl documentImpl) {
        super(documentImpl);
    }

    @Override
    public String getNodeName() {
        return "#document-fragment";
    }

    @Override
    public short getNodeType() {
        return 11;
    }
}

