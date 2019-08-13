/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import org.apache.xml.utils.PrefixResolver;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class PrefixResolverDefault
implements PrefixResolver {
    Node m_context;

    public PrefixResolverDefault(Node node) {
        this.m_context = node;
    }

    @Override
    public String getBaseIdentifier() {
        return null;
    }

    @Override
    public String getNamespaceForPrefix(String string) {
        return this.getNamespaceForPrefix(string, this.m_context);
    }

    @Override
    public String getNamespaceForPrefix(String string, Node object) {
        Object object2 = null;
        Node node = object;
        object = object2;
        if (string.equals("xml")) {
            object2 = "http://www.w3.org/XML/1998/namespace";
        } else {
            do {
                object2 = object;
                if (node == null) break;
                object2 = object;
                if (object != null) break;
                int n = node.getNodeType();
                if (n != 1) {
                    object2 = object;
                    if (n != 5) break;
                }
                object2 = object;
                if (n == 1) {
                    Object object3 = node.getNodeName();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(string);
                    ((StringBuilder)object2).append(":");
                    if (((String)object3).indexOf(((StringBuilder)object2).toString()) == 0) {
                        return node.getNamespaceURI();
                    }
                    object3 = node.getAttributes();
                    n = 0;
                    do {
                        object2 = object;
                        if (n >= object3.getLength()) break;
                        Node node2 = object3.item(n);
                        object2 = node2.getNodeName();
                        boolean bl = ((String)object2).startsWith("xmlns:");
                        if (bl || ((String)object2).equals("xmlns")) {
                            int n2 = ((String)object2).indexOf(58);
                            object2 = bl ? ((String)object2).substring(n2 + 1) : "";
                            if (((String)object2).equals(string)) {
                                object2 = node2.getNodeValue();
                                break;
                            }
                        }
                        ++n;
                    } while (true);
                }
                node = node.getParentNode();
                object = object2;
            } while (true);
        }
        return object2;
    }

    @Override
    public boolean handlesNullPrefixes() {
        return false;
    }
}

