/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import org.w3c.dom.Node;

public final class DOM2Helper {
    private String getLocalNameOfNodeFallback(Node object) {
        int n = ((String)(object = object.getNodeName())).indexOf(58);
        if (n >= 0) {
            object = ((String)object).substring(n + 1);
        }
        return object;
    }

    public String getLocalNameOfNode(Node object) {
        String string = object.getLocalName();
        object = string == null ? this.getLocalNameOfNodeFallback((Node)object) : string;
        return object;
    }

    public String getNamespaceOfNode(Node node) {
        return node.getNamespaceURI();
    }
}

