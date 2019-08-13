/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XRTreeFrag;
import org.apache.xpath.objects.XString;

public class ElemWithParam
extends ElemTemplateElement {
    static final long serialVersionUID = -1070355175864326257L;
    int m_index;
    private QName m_qname = null;
    int m_qnameID;
    private XPath m_selectPattern = null;

    @Override
    public ElemTemplateElement appendChild(ElemTemplateElement serializable) {
        if (this.m_selectPattern != null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("xsl:");
            ((StringBuilder)serializable).append(this.getNodeName());
            this.error("ER_CANT_HAVE_CONTENT_AND_SELECT", new Object[]{((StringBuilder)serializable).toString()});
            return null;
        }
        return super.appendChild((ElemTemplateElement)serializable);
    }

    @Override
    protected void callChildVisitors(XSLTVisitor xSLTVisitor, boolean bl) {
        XPath xPath;
        if (bl && (xPath = this.m_selectPattern) != null) {
            xPath.getExpression().callVisitors(this.m_selectPattern, xSLTVisitor);
        }
        super.callChildVisitors(xSLTVisitor, bl);
    }

    @Override
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        Serializable serializable;
        if (this.m_selectPattern == null && stylesheetRoot.getOptimizer() && (serializable = ElemVariable.rewriteChildToExpression(this)) != null) {
            this.m_selectPattern = serializable;
        }
        this.m_qnameID = stylesheetRoot.getComposeState().getQNameID(this.m_qname);
        super.compose(stylesheetRoot);
        serializable = stylesheetRoot.getComposeState().getVariableNames();
        XPath xPath = this.m_selectPattern;
        if (xPath != null) {
            xPath.fixupVariables((Vector)serializable, stylesheetRoot.getComposeState().getGlobalsSize());
        }
    }

    public QName getName() {
        return this.m_qname;
    }

    @Override
    public String getNodeName() {
        return "with-param";
    }

    public XPath getSelect() {
        return this.m_selectPattern;
    }

    public XObject getValue(TransformerImpl object, int n) throws TransformerException {
        XPathContext xPathContext = ((TransformerImpl)object).getXPathContext();
        xPathContext.pushCurrentNode(n);
        try {
            if (this.m_selectPattern != null) {
                object = this.m_selectPattern.execute(xPathContext, n, (PrefixResolver)this);
                ((XObject)object).allowDetachToRelease(false);
            } else {
                object = this.getFirstChildElem() == null ? XString.EMPTYSTRING : new XRTreeFrag(((TransformerImpl)object).transformToRTF(this), xPathContext, this);
            }
            return object;
        }
        finally {
            xPathContext.popCurrentNode();
        }
    }

    @Override
    public int getXSLToken() {
        return 2;
    }

    public void setName(QName qName) {
        this.m_qname = qName;
    }

    @Override
    public void setParentElem(ElemTemplateElement elemTemplateElement) {
        super.setParentElem(elemTemplateElement);
        elemTemplateElement.m_hasVariableDecl = true;
    }

    public void setSelect(XPath xPath) {
        this.m_selectPattern = xPath;
    }
}

