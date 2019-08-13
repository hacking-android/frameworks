/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitable;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public abstract class Expression
implements Serializable,
ExpressionNode,
XPathVisitable {
    static final long serialVersionUID = 565665869777906902L;
    private ExpressionNode m_parent;

    public DTMIterator asIterator(XPathContext xPathContext, int n) throws TransformerException {
        try {
            xPathContext.pushCurrentNodeAndExpression(n, n);
            DTMIterator dTMIterator = this.execute(xPathContext).iter();
            return dTMIterator;
        }
        finally {
            xPathContext.popCurrentNodeAndExpression();
        }
    }

    public DTMIterator asIteratorRaw(XPathContext xPathContext, int n) throws TransformerException {
        try {
            xPathContext.pushCurrentNodeAndExpression(n, n);
            DTMIterator dTMIterator = ((XNodeSet)this.execute(xPathContext)).iterRaw();
            return dTMIterator;
        }
        finally {
            xPathContext.popCurrentNodeAndExpression();
        }
    }

    public int asNode(XPathContext xPathContext) throws TransformerException {
        return this.execute(xPathContext).iter().nextNode();
    }

    public void assertion(boolean bl, String string) {
        if (bl) {
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[]{string}));
    }

    public boolean bool(XPathContext xPathContext) throws TransformerException {
        return this.execute(xPathContext).bool();
    }

    public boolean canTraverseOutsideSubtree() {
        return false;
    }

    public abstract boolean deepEquals(Expression var1);

    public void error(XPathContext xPathContext, String string, Object[] arrobject) throws TransformerException {
        string = XSLMessages.createXPATHMessage(string, arrobject);
        if (xPathContext != null) {
            xPathContext.getErrorListener().fatalError(new TransformerException(string, this));
        }
    }

    public abstract XObject execute(XPathContext var1) throws TransformerException;

    public XObject execute(XPathContext xPathContext, int n) throws TransformerException {
        return this.execute(xPathContext);
    }

    public XObject execute(XPathContext xPathContext, int n, DTM dTM, int n2) throws TransformerException {
        return this.execute(xPathContext);
    }

    public XObject execute(XPathContext xPathContext, boolean bl) throws TransformerException {
        return this.execute(xPathContext);
    }

    public void executeCharsToContentHandler(XPathContext object, ContentHandler contentHandler) throws TransformerException, SAXException {
        object = this.execute((XPathContext)object);
        ((XObject)object).dispatchCharactersEvents(contentHandler);
        ((XObject)object).detach();
    }

    @Override
    public void exprAddChild(ExpressionNode expressionNode, int n) {
        this.assertion(false, "exprAddChild method not implemented!");
    }

    @Override
    public ExpressionNode exprGetChild(int n) {
        return null;
    }

    @Override
    public int exprGetNumChildren() {
        return 0;
    }

    @Override
    public ExpressionNode exprGetParent() {
        return this.m_parent;
    }

    @Override
    public void exprSetParent(ExpressionNode expressionNode) {
        boolean bl = expressionNode != this;
        this.assertion(bl, "Can not parent an expression to itself!");
        this.m_parent = expressionNode;
    }

    public abstract void fixupVariables(Vector var1, int var2);

    @Override
    public int getColumnNumber() {
        ExpressionNode expressionNode = this.m_parent;
        if (expressionNode == null) {
            return 0;
        }
        return expressionNode.getColumnNumber();
    }

    public ExpressionNode getExpressionOwner() {
        ExpressionNode expressionNode;
        for (expressionNode = this.exprGetParent(); expressionNode != null && expressionNode instanceof Expression; expressionNode = expressionNode.exprGetParent()) {
        }
        return expressionNode;
    }

    @Override
    public int getLineNumber() {
        ExpressionNode expressionNode = this.m_parent;
        if (expressionNode == null) {
            return 0;
        }
        return expressionNode.getLineNumber();
    }

    @Override
    public String getPublicId() {
        ExpressionNode expressionNode = this.m_parent;
        if (expressionNode == null) {
            return null;
        }
        return expressionNode.getPublicId();
    }

    @Override
    public String getSystemId() {
        ExpressionNode expressionNode = this.m_parent;
        if (expressionNode == null) {
            return null;
        }
        return expressionNode.getSystemId();
    }

    public boolean isNodesetExpr() {
        return false;
    }

    protected final boolean isSameClass(Expression expression) {
        boolean bl = false;
        if (expression == null) {
            return false;
        }
        if (this.getClass() == expression.getClass()) {
            bl = true;
        }
        return bl;
    }

    public boolean isStableNumber() {
        return false;
    }

    public double num(XPathContext xPathContext) throws TransformerException {
        return this.execute(xPathContext).num();
    }

    public void warn(XPathContext xPathContext, String string, Object[] arrobject) throws TransformerException {
        string = XSLMessages.createXPATHWarning(string, arrobject);
        if (xPathContext != null) {
            xPathContext.getErrorListener().warning(new TransformerException(string, xPathContext.getSAXLocator()));
        }
    }

    public XMLString xstr(XPathContext xPathContext) throws TransformerException {
        return this.execute(xPathContext).xstr();
    }
}

