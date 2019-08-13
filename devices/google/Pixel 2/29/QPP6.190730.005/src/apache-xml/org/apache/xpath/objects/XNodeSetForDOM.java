/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTMManager;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XNodeSet;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

public class XNodeSetForDOM
extends XNodeSet {
    static final long serialVersionUID = -8396190713754624640L;
    Object m_origObj;

    public XNodeSetForDOM(XNodeSet xNodeSet) {
        super(xNodeSet);
        if (xNodeSet instanceof XNodeSetForDOM) {
            this.m_origObj = ((XNodeSetForDOM)xNodeSet).m_origObj;
        }
    }

    public XNodeSetForDOM(Node node, DTMManager dTMManager) {
        this.m_dtmMgr = dTMManager;
        this.m_origObj = node;
        int n = dTMManager.getDTMHandleFromNode(node);
        this.setObject(new NodeSetDTM(dTMManager));
        ((NodeSetDTM)this.m_obj).addNode(n);
    }

    public XNodeSetForDOM(NodeList object, XPathContext xPathContext) {
        this.m_dtmMgr = xPathContext.getDTMManager();
        this.m_origObj = object;
        object = new NodeSetDTM((NodeList)object, xPathContext);
        this.m_last = ((NodeSetDTM)object).getLength();
        this.setObject(object);
    }

    public XNodeSetForDOM(NodeIterator object, XPathContext xPathContext) {
        this.m_dtmMgr = xPathContext.getDTMManager();
        this.m_origObj = object;
        object = new NodeSetDTM((NodeIterator)object, xPathContext);
        this.m_last = ((NodeSetDTM)object).getLength();
        this.setObject(object);
    }

    @Override
    public NodeList nodelist() throws TransformerException {
        Object object = this.m_origObj;
        object = object instanceof NodeList ? (NodeList)object : super.nodelist();
        return object;
    }

    @Override
    public NodeIterator nodeset() throws TransformerException {
        Object object = this.m_origObj;
        object = object instanceof NodeIterator ? (NodeIterator)object : super.nodeset();
        return object;
    }

    @Override
    public Object object() {
        return this.m_origObj;
    }
}

