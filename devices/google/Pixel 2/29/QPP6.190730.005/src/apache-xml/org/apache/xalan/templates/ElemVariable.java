/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemTextLiteral;
import org.apache.xalan.templates.ElemValueOf;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XRTreeFrag;
import org.apache.xpath.objects.XRTreeFragSelectWrapper;
import org.apache.xpath.objects.XString;
import org.w3c.dom.Node;

public class ElemVariable
extends ElemTemplateElement {
    static final long serialVersionUID = 9111131075322790061L;
    int m_frameSize = -1;
    protected int m_index;
    private boolean m_isTopLevel = false;
    protected QName m_qname;
    private XPath m_selectPattern;

    public ElemVariable() {
    }

    public ElemVariable(ElemVariable elemVariable) throws TransformerException {
        this.m_selectPattern = elemVariable.m_selectPattern;
        this.m_qname = elemVariable.m_qname;
        this.m_isTopLevel = elemVariable.m_isTopLevel;
    }

    static XPath rewriteChildToExpression(ElemTemplateElement elemTemplateElement) throws TransformerException {
        ExpressionNode expressionNode = elemTemplateElement.getFirstChildElem();
        if (expressionNode != null && expressionNode.getNextSiblingElem() == null) {
            int n = expressionNode.getXSLToken();
            if (30 == n) {
                if (!((ElemValueOf)(expressionNode = (ElemValueOf)expressionNode)).getDisableOutputEscaping() && expressionNode.getDOMBackPointer() == null) {
                    elemTemplateElement.m_firstChild = null;
                    return new XPath(new XRTreeFragSelectWrapper(((ElemValueOf)expressionNode).getSelect().getExpression()));
                }
            } else if (78 == n && !((ElemTextLiteral)(expressionNode = (ElemTextLiteral)expressionNode)).getDisableOutputEscaping() && expressionNode.getDOMBackPointer() == null) {
                expressionNode = new XString(((ElemTextLiteral)expressionNode).getNodeValue());
                elemTemplateElement.m_firstChild = null;
                return new XPath(new XRTreeFragSelectWrapper((Expression)expressionNode));
            }
        }
        return null;
    }

    @Override
    protected boolean accept(XSLTVisitor xSLTVisitor) {
        return xSLTVisitor.visitVariableOrParamDecl(this);
    }

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
        XPath xPath = this.m_selectPattern;
        if (xPath != null) {
            xPath.getExpression().callVisitors(this.m_selectPattern, xSLTVisitor);
        }
        super.callChildVisitors(xSLTVisitor, bl);
    }

    @Override
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        Object object;
        if (this.m_selectPattern == null && stylesheetRoot.getOptimizer() && (object = ElemVariable.rewriteChildToExpression(this)) != null) {
            this.m_selectPattern = object;
        }
        object = stylesheetRoot.getComposeState();
        Vector vector = ((StylesheetRoot.ComposeState)object).getVariableNames();
        Serializable serializable = this.m_selectPattern;
        if (serializable != null) {
            serializable.fixupVariables(vector, ((StylesheetRoot.ComposeState)object).getGlobalsSize());
        }
        if (!(this.m_parentNode instanceof Stylesheet) && (serializable = this.m_qname) != null) {
            this.m_index = ((StylesheetRoot.ComposeState)object).addVariableName((QName)serializable) - ((StylesheetRoot.ComposeState)object).getGlobalsSize();
        } else if (this.m_parentNode instanceof Stylesheet) {
            ((StylesheetRoot.ComposeState)object).resetStackFrameSize();
        }
        super.compose(stylesheetRoot);
    }

    @Override
    public void endCompose(StylesheetRoot object) throws TransformerException {
        super.endCompose((StylesheetRoot)object);
        if (this.m_parentNode instanceof Stylesheet) {
            object = ((StylesheetRoot)object).getComposeState();
            this.m_frameSize = ((StylesheetRoot.ComposeState)object).getFrameSize();
            ((StylesheetRoot.ComposeState)object).resetStackFrameSize();
        }
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        XObject xObject = this.getValue(transformerImpl, transformerImpl.getXPathContext().getCurrentNode());
        transformerImpl.getXPathContext().getVarStack().setLocalVariable(this.m_index, xObject);
    }

    public int getIndex() {
        return this.m_index;
    }

    public boolean getIsTopLevel() {
        return this.m_isTopLevel;
    }

    public QName getName() {
        return this.m_qname;
    }

    @Override
    public String getNodeName() {
        return "variable";
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
            } else if (this.getFirstChildElem() == null) {
                object = XString.EMPTYSTRING;
            } else {
                n = this.m_parentNode instanceof Stylesheet ? ((TransformerImpl)object).transformToGlobalRTF(this) : ((TransformerImpl)object).transformToRTF(this);
                object = new XRTreeFrag(n, xPathContext, this);
            }
            return object;
        }
        finally {
            xPathContext.popCurrentNode();
        }
    }

    @Override
    public int getXSLToken() {
        return 73;
    }

    public boolean isPsuedoVar() {
        String string = this.m_qname.getNamespaceURI();
        return string != null && string.equals("http://xml.apache.org/xalan/psuedovar") && this.m_qname.getLocalName().startsWith("#");
    }

    @Override
    public void recompose(StylesheetRoot stylesheetRoot) {
        stylesheetRoot.recomposeVariables(this);
    }

    public void setIndex(int n) {
        this.m_index = n;
    }

    public void setIsTopLevel(boolean bl) {
        this.m_isTopLevel = bl;
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

