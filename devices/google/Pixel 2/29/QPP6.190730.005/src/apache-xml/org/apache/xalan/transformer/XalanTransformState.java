/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import javax.xml.transform.Transformer;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.transformer.TransformState;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.ref.DTMNodeIterator;
import org.apache.xpath.XPathContext;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public class XalanTransformState
implements TransformState {
    DTMIterator m_contextNodeList = null;
    ElemTemplateElement m_currentElement = null;
    Node m_currentNode = null;
    int m_currentNodeHandle = -1;
    ElemTemplate m_currentTemplate = null;
    boolean m_elemPending = false;
    int m_matchedNode = -1;
    ElemTemplate m_matchedTemplate = null;
    Node m_node = null;
    TransformerImpl m_transformer = null;

    @Override
    public NodeIterator getContextNodeList() {
        if (this.m_elemPending) {
            return new DTMNodeIterator(this.m_contextNodeList);
        }
        return new DTMNodeIterator(this.m_transformer.getContextNodeList());
    }

    @Override
    public ElemTemplateElement getCurrentElement() {
        if (this.m_elemPending) {
            return this.m_currentElement;
        }
        return this.m_transformer.getCurrentElement();
    }

    @Override
    public Node getCurrentNode() {
        Node node = this.m_currentNode;
        if (node != null) {
            return node;
        }
        return this.m_transformer.getXPathContext().getDTM(this.m_transformer.getCurrentNode()).getNode(this.m_transformer.getCurrentNode());
    }

    @Override
    public ElemTemplate getCurrentTemplate() {
        if (this.m_elemPending) {
            return this.m_currentTemplate;
        }
        return this.m_transformer.getCurrentTemplate();
    }

    @Override
    public Node getMatchedNode() {
        if (this.m_elemPending) {
            return this.m_transformer.getXPathContext().getDTM(this.m_matchedNode).getNode(this.m_matchedNode);
        }
        return this.m_transformer.getXPathContext().getDTM(this.m_transformer.getMatchedNode()).getNode(this.m_transformer.getMatchedNode());
    }

    @Override
    public ElemTemplate getMatchedTemplate() {
        if (this.m_elemPending) {
            return this.m_matchedTemplate;
        }
        return this.m_transformer.getMatchedTemplate();
    }

    @Override
    public Transformer getTransformer() {
        return this.m_transformer;
    }

    @Override
    public void resetState(Transformer transformer) {
        if (transformer != null && transformer instanceof TransformerImpl) {
            this.m_transformer = (TransformerImpl)transformer;
            this.m_currentElement = this.m_transformer.getCurrentElement();
            this.m_currentTemplate = this.m_transformer.getCurrentTemplate();
            this.m_matchedTemplate = this.m_transformer.getMatchedTemplate();
            int n = this.m_transformer.getCurrentNode();
            this.m_currentNode = this.m_transformer.getXPathContext().getDTM(n).getNode(n);
            this.m_matchedNode = this.m_transformer.getMatchedNode();
            this.m_contextNodeList = this.m_transformer.getContextNodeList();
        }
    }

    @Override
    public void setCurrentNode(Node node) {
        this.m_node = node;
    }
}

