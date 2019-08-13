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
import org.apache.xpath.functions.Function2Args;
import org.apache.xpath.functions.WrongNumberArgsException;

public class Function3Args
extends Function2Args {
    static final long serialVersionUID = 7915240747161506646L;
    Expression m_arg2;

    @Override
    public void callArgVisitors(XPathVisitor xPathVisitor) {
        super.callArgVisitors(xPathVisitor);
        Expression expression = this.m_arg2;
        if (expression != null) {
            expression.callVisitors(new Arg2Owner(), xPathVisitor);
        }
    }

    @Override
    public boolean canTraverseOutsideSubtree() {
        boolean bl = super.canTraverseOutsideSubtree() ? true : this.m_arg2.canTraverseOutsideSubtree();
        return bl;
    }

    @Override
    public void checkNumberArgs(int n) throws WrongNumberArgsException {
        if (n != 3) {
            this.reportWrongNumberArgs();
        }
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        Expression expression2 = this.m_arg2;
        if (expression2 != null) {
            if (((Function3Args)expression).m_arg2 == null) {
                return false;
            }
            if (!expression2.deepEquals(((Function3Args)expression).m_arg2)) {
                return false;
            }
        } else if (((Function3Args)expression).m_arg2 != null) {
            return false;
        }
        return true;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        super.fixupVariables(vector, n);
        Expression expression = this.m_arg2;
        if (expression != null) {
            expression.fixupVariables(vector, n);
        }
    }

    public Expression getArg2() {
        return this.m_arg2;
    }

    @Override
    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("three", null));
    }

    @Override
    public void setArg(Expression expression, int n) throws WrongNumberArgsException {
        if (n < 2) {
            super.setArg(expression, n);
        } else if (2 == n) {
            this.m_arg2 = expression;
            expression.exprSetParent(this);
        } else {
            this.reportWrongNumberArgs();
        }
    }

    class Arg2Owner
    implements ExpressionOwner {
        Arg2Owner() {
        }

        @Override
        public Expression getExpression() {
            return Function3Args.this.m_arg2;
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(Function3Args.this);
            Function3Args.this.m_arg2 = expression;
        }
    }

}

