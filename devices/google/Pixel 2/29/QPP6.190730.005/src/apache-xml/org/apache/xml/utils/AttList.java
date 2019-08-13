/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import org.apache.xml.utils.DOMHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;

public class AttList
implements Attributes {
    NamedNodeMap m_attrs;
    DOMHelper m_dh;
    int m_lastIndex;

    public AttList(NamedNodeMap namedNodeMap, DOMHelper dOMHelper) {
        this.m_attrs = namedNodeMap;
        this.m_lastIndex = this.m_attrs.getLength() - 1;
        this.m_dh = dOMHelper;
    }

    @Override
    public int getIndex(String string) {
        for (int i = this.m_attrs.getLength() - 1; i >= 0; --i) {
            if (!this.m_attrs.item(i).getNodeName().equals(string)) continue;
            return i;
        }
        return -1;
    }

    @Override
    public int getIndex(String string, String string2) {
        for (int i = this.m_attrs.getLength() - 1; i >= 0; --i) {
            Node node = this.m_attrs.item(i);
            String string3 = node.getNamespaceURI();
            if (!(string3 == null ? string == null : string3.equals(string)) || !node.getLocalName().equals(string2)) continue;
            return i;
        }
        return -1;
    }

    @Override
    public int getLength() {
        return this.m_attrs.getLength();
    }

    @Override
    public String getLocalName(int n) {
        return this.m_dh.getLocalNameOfNode((Attr)this.m_attrs.item(n));
    }

    @Override
    public String getQName(int n) {
        return ((Attr)this.m_attrs.item(n)).getName();
    }

    @Override
    public String getType(int n) {
        return "CDATA";
    }

    @Override
    public String getType(String string) {
        return "CDATA";
    }

    @Override
    public String getType(String string, String string2) {
        return "CDATA";
    }

    @Override
    public String getURI(int n) {
        String string;
        String string2 = string = this.m_dh.getNamespaceOfNode((Attr)this.m_attrs.item(n));
        if (string == null) {
            string2 = "";
        }
        return string2;
    }

    @Override
    public String getValue(int n) {
        return ((Attr)this.m_attrs.item(n)).getValue();
    }

    @Override
    public String getValue(String object) {
        object = (object = (Attr)this.m_attrs.getNamedItem((String)object)) != null ? object.getValue() : null;
        return object;
    }

    @Override
    public String getValue(String object, String string) {
        object = (object = this.m_attrs.getNamedItemNS((String)object, string)) == null ? null : object.getNodeValue();
        return object;
    }
}

