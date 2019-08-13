/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.extensions;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;
import org.apache.xml.utils.QName;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public interface ExpressionContext {
    public Node getContextNode();

    public NodeIterator getContextNodes();

    public ErrorListener getErrorListener();

    public XObject getVariableOrParam(QName var1) throws TransformerException;

    public XPathContext getXPathContext() throws TransformerException;

    public double toNumber(Node var1);

    public String toString(Node var1);
}

