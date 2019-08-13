/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.ref.DTMNodeIterator;
import org.apache.xml.dtm.ref.DTMNodeList;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.RTFIterator;
import org.apache.xpath.objects.DTMXRTreeFrag;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;
import org.w3c.dom.NodeList;

public class XRTreeFrag
extends XObject
implements Cloneable {
    static final long serialVersionUID = -3201553822254911567L;
    private DTMXRTreeFrag m_DTMXRTreeFrag;
    protected boolean m_allowRelease = false;
    private int m_dtmRoot = -1;
    private XMLString m_xmlStr = null;

    public XRTreeFrag(int n, XPathContext xPathContext) {
        super(null);
        this.initDTM(n, xPathContext);
    }

    public XRTreeFrag(int n, XPathContext xPathContext, ExpressionNode expressionNode) {
        super(null);
        this.exprSetParent(expressionNode);
        this.initDTM(n, xPathContext);
    }

    public XRTreeFrag(Expression expression) {
        super(expression);
    }

    private final void initDTM(int n, XPathContext xPathContext) {
        this.m_dtmRoot = n;
        DTM dTM = xPathContext.getDTM(n);
        if (dTM != null) {
            this.m_DTMXRTreeFrag = xPathContext.getDTMXRTreeFrag(xPathContext.getDTMIdentity(dTM));
        }
    }

    @Override
    public void allowDetachToRelease(boolean bl) {
        this.m_allowRelease = bl;
    }

    @Override
    public void appendToFsb(FastStringBuffer fastStringBuffer) {
        ((XString)this.xstr()).appendToFsb(fastStringBuffer);
    }

    public DTMIterator asNodeIterator() {
        return new RTFIterator(this.m_dtmRoot, this.m_DTMXRTreeFrag.getXPathContext().getDTMManager());
    }

    @Override
    public boolean bool() {
        return true;
    }

    public NodeList convertToNodeset() {
        if (this.m_obj instanceof NodeList) {
            return (NodeList)this.m_obj;
        }
        return new DTMNodeList(this.asNodeIterator());
    }

    @Override
    public void detach() {
        if (this.m_allowRelease) {
            this.m_DTMXRTreeFrag.destruct();
            this.setObject(null);
        }
    }

    @Override
    public boolean equals(XObject xObject) {
        boolean bl;
        block13 : {
            boolean bl2;
            block12 : {
                try {
                    if (4 == xObject.getType()) {
                        return xObject.equals(this);
                    }
                    int n = xObject.getType();
                    bl2 = false;
                    bl = false;
                    if (1 != n) break block12;
                }
                catch (TransformerException transformerException) {
                    throw new WrappedRuntimeException(transformerException);
                }
                if (this.bool() == xObject.bool()) {
                    bl = true;
                }
                return bl;
            }
            if (2 != xObject.getType()) break block13;
            bl = bl2;
            if (this.num() == xObject.num()) {
                bl = true;
            }
            return bl;
        }
        if (4 == xObject.getType()) {
            return this.xstr().equals(xObject.xstr());
        }
        if (3 == xObject.getType()) {
            return this.xstr().equals(xObject.xstr());
        }
        if (5 == xObject.getType()) {
            return this.xstr().equals(xObject.xstr());
        }
        bl = super.equals(xObject);
        return bl;
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public String getTypeString() {
        return "#RTREEFRAG";
    }

    @Override
    public double num() throws TransformerException {
        return this.xstr().toDouble();
    }

    @Override
    public Object object() {
        if (this.m_DTMXRTreeFrag.getXPathContext() != null) {
            return new DTMNodeIterator(new NodeSetDTM(this.m_dtmRoot, this.m_DTMXRTreeFrag.getXPathContext().getDTMManager()));
        }
        return super.object();
    }

    @Override
    public int rtf() {
        return this.m_dtmRoot;
    }

    @Override
    public String str() {
        String string;
        block0 : {
            string = this.m_DTMXRTreeFrag.getDTM().getStringValue(this.m_dtmRoot).toString();
            if (string != null) break block0;
            string = "";
        }
        return string;
    }

    @Override
    public XMLString xstr() {
        if (this.m_xmlStr == null) {
            this.m_xmlStr = this.m_DTMXRTreeFrag.getDTM().getStringValue(this.m_dtmRoot);
        }
        return this.m_xmlStr;
    }
}

