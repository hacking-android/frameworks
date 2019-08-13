/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import java.util.List;
import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.InnerNodeImpl;
import org.apache.harmony.xml.dom.NodeImpl;
import org.w3c.dom.Node;

public abstract class LeafNodeImpl
extends NodeImpl {
    int index;
    InnerNodeImpl parent;

    LeafNodeImpl(DocumentImpl documentImpl) {
        super(documentImpl);
    }

    @Override
    public Node getNextSibling() {
        InnerNodeImpl innerNodeImpl = this.parent;
        if (innerNodeImpl != null && this.index + 1 < innerNodeImpl.children.size()) {
            return this.parent.children.get(this.index + 1);
        }
        return null;
    }

    @Override
    public Node getParentNode() {
        return this.parent;
    }

    @Override
    public Node getPreviousSibling() {
        InnerNodeImpl innerNodeImpl = this.parent;
        if (innerNodeImpl != null && this.index != 0) {
            return innerNodeImpl.children.get(this.index - 1);
        }
        return null;
    }

    boolean isParentOf(Node node) {
        return false;
    }
}

