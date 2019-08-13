/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform.dom;

import javax.xml.transform.Source;
import org.w3c.dom.Node;

public class DOMSource
implements Source {
    public static final String FEATURE = "http://javax.xml.transform.dom.DOMSource/feature";
    private Node node;
    private String systemID;

    public DOMSource() {
    }

    public DOMSource(Node node) {
        this.setNode(node);
    }

    public DOMSource(Node node, String string) {
        this.setNode(node);
        this.setSystemId(string);
    }

    public Node getNode() {
        return this.node;
    }

    @Override
    public String getSystemId() {
        return this.systemID;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public void setSystemId(String string) {
        this.systemID = string;
    }
}

