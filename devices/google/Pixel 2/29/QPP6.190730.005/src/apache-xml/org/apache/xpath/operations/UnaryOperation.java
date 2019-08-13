/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XObject;

public abstract class UnaryOperation
extends Expression
implements ExpressionOwner {
    static final long serialVersionUID = 6536083808424286166L;
    protected Expression m_right;

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        if (xPathVisitor.visitUnaryOperation(expressionOwner, this)) {
            this.m_right.callVisitors(this, xPathVisitor);
        }
    }

    @Override
    public boolean canTraverseOutsideSubtree() {
        Expression expression = this.m_right;
        return expression != null && expression.canTraverseOutsideSubtree();
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!this.isSameClass(expression)) {
            return false;
        }
        return this.m_right.deepEquals(((UnaryOperation)expression).m_right);
    }

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return this.operate(this.m_right.execute(xPathContext));
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        this.m_right.fixupVariables(vector, n);
    }

    @Override
    public Expression getExpression() {
        return this.m_right;
    }

    public Expression getOperand() {
        return this.m_right;
    }

    public abstract XObject operate(XObject var1) throws TransformerException;

    @Override
    public void setExpression(Expression expression) {
        expression.exprSetParent(this);
        this.m_right = expression;
    }

    public void setRight(Expression expression) {
        this.m_right = expression;
        expression.exprSetParent(this);
    }
}

