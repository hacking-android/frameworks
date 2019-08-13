/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.PathComponent;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;

public class Variable
extends Expression
implements PathComponent {
    static final String PSUEDOVARNAMESPACE = "http://xml.apache.org/xalan/psuedovar";
    static final long serialVersionUID = -4334975375609297049L;
    private boolean m_fixUpWasCalled = false;
    protected int m_index;
    protected boolean m_isGlobal = false;
    protected QName m_qname;

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        xPathVisitor.visitVariableRef(expressionOwner, this);
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!this.isSameClass(expression)) {
            return false;
        }
        if (!this.m_qname.equals(((Variable)expression).m_qname)) {
            return false;
        }
        return this.getElemVariable() == ((Variable)expression).getElemVariable();
    }

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return this.execute(xPathContext, false);
    }

    @Override
    public XObject execute(XPathContext xPathContext, boolean bl) throws TransformerException {
        xPathContext.getNamespaceContext();
        XObject xObject = this.m_fixUpWasCalled ? (this.m_isGlobal ? xPathContext.getVarStack().getGlobalVariable(xPathContext, this.m_index, bl) : xPathContext.getVarStack().getLocalVariable(xPathContext, this.m_index, bl)) : xPathContext.getVarStack().getVariableOrParam(xPathContext, this.m_qname);
        XObject xObject2 = xObject;
        if (xObject == null) {
            this.warn(xPathContext, "WG_ILLEGAL_VARIABLE_REFERENCE", new Object[]{this.m_qname.getLocalPart()});
            xObject2 = new XNodeSet(xPathContext.getDTMManager());
        }
        return xObject2;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        this.m_fixUpWasCalled = true;
        vector.size();
        for (int i = vector.size() - 1; i >= 0; --i) {
            if (!((QName)vector.elementAt(i)).equals(this.m_qname)) continue;
            if (i < n) {
                this.m_isGlobal = true;
                this.m_index = i;
            } else {
                this.m_index = i - n;
            }
            return;
        }
        throw new WrappedRuntimeException(new TransformerException(XSLMessages.createXPATHMessage("ER_COULD_NOT_FIND_VAR", new Object[]{this.m_qname.toString()}), this));
    }

    @Override
    public int getAnalysisBits() {
        Serializable serializable = this.getElemVariable();
        if (serializable != null && (serializable = ((ElemVariable)serializable).getSelect()) != null && (serializable = ((XPath)serializable).getExpression()) != null && serializable instanceof PathComponent) {
            return ((PathComponent)((Object)serializable)).getAnalysisBits();
        }
        return 67108864;
    }

    public ElemVariable getElemVariable() {
        ElemTemplateElement elemTemplateElement = null;
        ElemTemplateElement elemTemplateElement2 = null;
        Object var3_3 = null;
        ExpressionNode expressionNode = this.getExpressionOwner();
        ElemTemplateElement elemTemplateElement3 = elemTemplateElement2;
        if (expressionNode != null) {
            elemTemplateElement3 = elemTemplateElement2;
            if (expressionNode instanceof ElemTemplateElement) {
                elemTemplateElement2 = (ElemTemplateElement)expressionNode;
                elemTemplateElement3 = elemTemplateElement;
                elemTemplateElement = elemTemplateElement2;
                if (!(elemTemplateElement2 instanceof Stylesheet)) {
                    do {
                        elemTemplateElement3 = var3_3;
                        elemTemplateElement = elemTemplateElement2;
                        if (elemTemplateElement2 == null) break;
                        elemTemplateElement3 = var3_3;
                        elemTemplateElement = elemTemplateElement2;
                        if (elemTemplateElement2.getParentNode() instanceof Stylesheet) break;
                        elemTemplateElement3 = elemTemplateElement2;
                        do {
                            elemTemplateElement = elemTemplateElement3 = elemTemplateElement3.getPreviousSiblingElem();
                            if (elemTemplateElement3 == null) break;
                            elemTemplateElement3 = elemTemplateElement;
                            if (!(elemTemplateElement instanceof ElemVariable)) continue;
                            elemTemplateElement3 = (ElemVariable)elemTemplateElement;
                            if (((ElemVariable)elemTemplateElement3).getName().equals(this.m_qname)) {
                                return elemTemplateElement3;
                            }
                            var3_3 = null;
                            elemTemplateElement3 = elemTemplateElement;
                        } while (true);
                        elemTemplateElement2 = elemTemplateElement2.getParentElem();
                    } while (true);
                }
                if (elemTemplateElement != null) {
                    elemTemplateElement3 = elemTemplateElement.getStylesheetRoot().getVariableOrParamComposed(this.m_qname);
                }
            }
        }
        return elemTemplateElement3;
    }

    public boolean getGlobal() {
        return this.m_isGlobal;
    }

    public int getIndex() {
        return this.m_index;
    }

    public QName getQName() {
        return this.m_qname;
    }

    public boolean isPsuedoVarRef() {
        String string = this.m_qname.getNamespaceURI();
        return string != null && string.equals(PSUEDOVARNAMESPACE) && this.m_qname.getLocalName().startsWith("#");
    }

    @Override
    public boolean isStableNumber() {
        return true;
    }

    public void setIndex(int n) {
        this.m_index = n;
    }

    public void setIsGlobal(boolean bl) {
        this.m_isGlobal = bl;
    }

    public void setQName(QName qName) {
        this.m_qname = qName;
    }
}

