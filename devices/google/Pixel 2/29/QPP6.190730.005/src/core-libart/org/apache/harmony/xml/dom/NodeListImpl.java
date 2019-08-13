/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import java.util.ArrayList;
import java.util.List;
import org.apache.harmony.xml.dom.NodeImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeListImpl
implements NodeList {
    private List<NodeImpl> children;

    NodeListImpl() {
        this.children = new ArrayList<NodeImpl>();
    }

    NodeListImpl(List<NodeImpl> list) {
        this.children = list;
    }

    void add(NodeImpl nodeImpl) {
        this.children.add(nodeImpl);
    }

    @Override
    public int getLength() {
        return this.children.size();
    }

    @Override
    public Node item(int n) {
        if (n >= this.children.size()) {
            return null;
        }
        return this.children.get(n);
    }
}

