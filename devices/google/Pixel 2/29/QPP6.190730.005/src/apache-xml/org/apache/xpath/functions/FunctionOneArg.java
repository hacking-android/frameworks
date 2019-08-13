/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import java.util.Vector;
import org.apache.xalan.res.XSLMessages;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.functions.Function;
import org.apache.xpath.functions.WrongNumberArgsException;

public class FunctionOneArg
extends Function
implements ExpressionOwner {
    static final long serialVersionUID = -5180174180765609758L;
    Expression m_arg0;

    @Override
    public void callArgVisitors(XPathVisitor xPathVisitor) {
        Expression expression = this.m_arg0;
        if (expression != null) {
            expression.callVisitors(this, xPathVisitor);
        }
    }

    @Override
    public boolean canTraverseOutsideSubtree() {
        return this.m_arg0.canTraverseOutsideSubtree();
    }

    @Override
    public void checkNumberArgs(int n) throws WrongNumberArgsException {
        if (n != 1) {
            this.reportWrongNumberArgs();
        }
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        Expression expression2 = this.m_arg0;
        if (expression2 != null) {
            if (((FunctionOneArg)expression).m_arg0 == null) {
                return false;
            }
            if (!expression2.deepEquals(((FunctionOneArg)expression).m_arg0)) {
                return false;
            }
        } else if (((FunctionOneArg)expression).m_arg0 != null) {
            return false;
        }
        return true;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        Expression expression = this.m_arg0;
        if (expression != null) {
            expression.fixupVariables(vector, n);
        }
    }

    public Expression getArg0() {
        return this.m_arg0;
    }

    @Override
    public Expression getExpression() {
        return this.m_arg0;
    }

    @Override
    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("one", null));
    }

    @Override
    public void setArg(Expression expression, int n) throws WrongNumberArgsException {
        if (n == 0) {
            this.m_arg0 = expression;
            expression.exprSetParent(this);
        } else {
            this.reportWrongNumberArgs();
        }
    }

    @Override
    public void setExpression(Expression expression) {
        expression.exprSetParent(this);
        this.m_arg0 = expression;
    }
}

