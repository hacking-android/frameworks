/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import org.apache.xml.dtm.DTM;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DTMNamedNodeMap
implements NamedNodeMap {
    DTM dtm;
    int element;
    short m_count = (short)-1;

    public DTMNamedNodeMap(DTM dTM, int n) {
        this.dtm = dTM;
        this.element = n;
    }

    @Override
    public int getLength() {
        if (this.m_count == -1) {
            int n = 0;
            int n2 = this.dtm.getFirstAttribute(this.element);
            while (n2 != -1) {
                n = (short)(n + 1);
                n2 = this.dtm.getNextAttribute(n2);
            }
            this.m_count = (short)n;
        }
        return this.m_count;
    }

    @Override
    public Node getNamedItem(String string) {
        int n = this.dtm.getFirstAttribute(this.element);
        while (n != -1) {
            if (this.dtm.getNodeName(n).equals(string)) {
                return this.dtm.getNode(n);
            }
            n = this.dtm.getNextAttribute(n);
        }
        return null;
    }

    @Override
    public Node getNamedItemNS(String string, String string2) {
        Object object;
        String string3 = null;
        int n = this.dtm.getFirstAttribute(this.element);
        do {
            object = string3;
            if (n == -1) break;
            if (string2.equals(this.dtm.getLocalName(n))) {
                object = this.dtm.getNamespaceURI(n);
                if (string == null && object == null || string != null && string.equals(object)) {
                    object = this.dtm.getNode(n);
                    break;
                }
            }
            n = this.dtm.getNextAttribute(n);
        } while (true);
        return object;
    }

    @Override
    public Node item(int n) {
        int n2 = 0;
        int n3 = this.dtm.getFirstAttribute(this.element);
        while (n3 != -1) {
            if (n2 == n) {
                return this.dtm.getNode(n3);
            }
            ++n2;
            n3 = this.dtm.getNextAttribute(n3);
        }
        return null;
    }

    @Override
    public Node removeNamedItem(String string) {
        throw new DTMException(7);
    }

    @Override
    public Node removeNamedItemNS(String string, String string2) throws DOMException {
        throw new DTMException(7);
    }

    @Override
    public Node setNamedItem(Node node) {
        throw new DTMException(7);
    }

    @Override
    public Node setNamedItemNS(Node node) throws DOMException {
        throw new DTMException(7);
    }

    public class DTMException
    extends DOMException {
        static final long serialVersionUID = -8290238117162437678L;

        public DTMException(short s) {
            super(s, "");
        }

        public DTMException(short s, String string) {
            super(s, string);
        }
    }

}

