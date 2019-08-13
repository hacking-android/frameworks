/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform.dom;

import javax.xml.transform.Result;
import org.w3c.dom.Node;

public class DOMResult
implements Result {
    public static final String FEATURE = "http://javax.xml.transform.dom.DOMResult/feature";
    private Node nextSibling = null;
    private Node node = null;
    private String systemId = null;

    public DOMResult() {
        this.setNode(null);
        this.setNextSibling(null);
        this.setSystemId(null);
    }

    public DOMResult(Node node) {
        this.setNode(node);
        this.setNextSibling(null);
        this.setSystemId(null);
    }

    public DOMResult(Node node, String string) {
        this.setNode(node);
        this.setNextSibling(null);
        this.setSystemId(string);
    }

    public DOMResult(Node node, Node node2) {
        if (node2 != null) {
            if (node != null) {
                if ((node.compareDocumentPosition(node2) & 16) == 0) {
                    throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
                }
            } else {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }
        }
        this.setNode(node);
        this.setNextSibling(node2);
        this.setSystemId(null);
    }

    public DOMResult(Node node, Node node2, String string) {
        if (node2 != null) {
            if (node != null) {
                if ((node.compareDocumentPosition(node2) & 16) == 0) {
                    throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
                }
            } else {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }
        }
        this.setNode(node);
        this.setNextSibling(node2);
        this.setSystemId(string);
    }

    public Node getNextSibling() {
        return this.nextSibling;
    }

    public Node getNode() {
        return this.node;
    }

    @Override
    public String getSystemId() {
        return this.systemId;
    }

    public void setNextSibling(Node node) {
        if (node != null) {
            Node node2 = this.node;
            if (node2 != null) {
                if ((node2.compareDocumentPosition(node) & 16) == 0) {
                    throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
                }
            } else {
                throw new IllegalStateException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }
        }
        this.nextSibling = node;
    }

    public void setNode(Node node) {
        Node node2 = this.nextSibling;
        if (node2 != null) {
            if (node != null) {
                if ((node.compareDocumentPosition(node2) & 16) == 0) {
                    throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
                }
            } else {
                throw new IllegalStateException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }
        }
        this.node = node;
    }

    @Override
    public void setSystemId(String string) {
        this.systemId = string;
    }
}

