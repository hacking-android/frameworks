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

public class Operation
extends Expression
implements ExpressionOwner {
    static final long serialVersionUID = -3037139537171050430L;
    protected Expression m_left;
    protected Expression m_right;

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        if (xPathVisitor.visitBinaryOperation(expressionOwner, this)) {
            this.m_left.callVisitors(new LeftExprOwner(), xPathVisitor);
            this.m_right.callVisitors(this, xPathVisitor);
        }
    }

    @Override
    public boolean canTraverseOutsideSubtree() {
        Expression expression = this.m_left;
        if (expression != null && expression.canTraverseOutsideSubtree()) {
            return true;
        }
        expression = this.m_right;
        return expression != null && expression.canTraverseOutsideSubtree();
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!this.isSameClass(expression)) {
            return false;
        }
        if (!this.m_left.deepEquals(((Operation)expression).m_left)) {
            return false;
        }
        return this.m_right.deepEquals(((Operation)expression).m_right);
    }

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        XObject xObject = this.m_left.execute((XPathContext)object, true);
        XObject xObject2 = this.m_right.execute((XPathContext)object, true);
        object = this.operate(xObject, xObject2);
        xObject.detach();
        xObject2.detach();
        return object;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        this.m_left.fixupVariables(vector, n);
        this.m_right.fixupVariables(vector, n);
    }

    @Override
    public Expression getExpression() {
        return this.m_right;
    }

    public Expression getLeftOperand() {
        return this.m_left;
    }

    public Expression getRightOperand() {
        return this.m_right;
    }

    public XObject operate(XObject xObject, XObject xObject2) throws TransformerException {
        return null;
    }

    @Override
    public void setExpression(Expression expression) {
        expression.exprSetParent(this);
        this.m_right = expression;
    }

    public void setLeftRight(Expression expression, Expression expression2) {
        this.m_left = expression;
        this.m_right = expression2;
        expression.exprSetParent(this);
        expression2.exprSetParent(this);
    }

    class LeftExprOwner
    implements ExpressionOwner {
        LeftExprOwner() {
        }

        @Override
        public Expression getExpression() {
            return Operation.this.m_left;
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(Operation.this);
            Operation.this.m_left = expression;
        }
    }

}

