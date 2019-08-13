/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.jaxp;

import javax.xml.namespace.NamespaceContext;
import org.apache.xml.utils.PrefixResolver;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class JAXPPrefixResolver
implements PrefixResolver {
    public static final String S_XMLNAMESPACEURI = "http://www.w3.org/XML/1998/namespace";
    private NamespaceContext namespaceContext;

    public JAXPPrefixResolver(NamespaceContext namespaceContext) {
        this.namespaceContext = namespaceContext;
    }

    @Override
    public String getBaseIdentifier() {
        return null;
    }

    @Override
    public String getNamespaceForPrefix(String string) {
        return this.namespaceContext.getNamespaceURI(string);
    }

    @Override
    public String getNamespaceForPrefix(String string, Node object) {
        Object object2 = null;
        Node node = object;
        object = object2;
        if (string.equals("xml")) {
            object2 = S_XMLNAMESPACEURI;
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
                    NamedNodeMap namedNodeMap = node.getAttributes();
                    n = 0;
                    do {
                        object2 = object;
                        if (n >= namedNodeMap.getLength()) break;
                        Node node2 = namedNodeMap.item(n);
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

