/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import javax.xml.transform.TransformerException;
import org.apache.xalan.serialize.SerializerUtils;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.ref.DTMTreeWalker;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xpath.XPathContext;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TreeWalker2Result
extends DTMTreeWalker {
    SerializationHandler m_handler;
    int m_startNode;
    TransformerImpl m_transformer;

    public TreeWalker2Result(TransformerImpl transformerImpl, SerializationHandler serializationHandler) {
        super(serializationHandler, null);
        this.m_transformer = transformerImpl;
        this.m_handler = serializationHandler;
    }

    @Override
    protected void endNode(int n) throws SAXException {
        super.endNode(n);
        if (1 == this.m_dtm.getNodeType(n)) {
            this.m_transformer.getXPathContext().popCurrentNode();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void startNode(int n) throws SAXException {
        Object object = this.m_transformer.getXPathContext();
        try {
            if (1 != this.m_dtm.getNodeType(n)) {
                ((XPathContext)object).pushCurrentNode(n);
                super.startNode(n);
                ((XPathContext)object).popCurrentNode();
                return;
            }
            ((XPathContext)object).pushCurrentNode(n);
            if (this.m_startNode != n) {
                super.startNode(n);
                return;
            }
            String string = this.m_dtm.getNodeName(n);
            String string2 = this.m_dtm.getLocalName(n);
            object = this.m_dtm.getNamespaceURI(n);
            this.m_handler.startElement((String)object, string2, string);
            object = this.m_dtm;
            int n2 = object.getFirstNamespaceNode(n, true);
            while (-1 != n2) {
                SerializerUtils.ensureNamespaceDeclDeclared(this.m_handler, (DTM)object, n2);
                n2 = object.getNextNamespaceNode(n, n2, true);
            }
            n = object.getFirstAttribute(n);
            while (-1 != n) {
                SerializerUtils.addAttribute(this.m_handler, n);
                n = object.getNextAttribute(n);
            }
            return;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }

    @Override
    public void traverse(int n) throws SAXException {
        this.m_dtm = this.m_transformer.getXPathContext().getDTM(n);
        this.m_startNode = n;
        super.traverse(n);
    }
}

