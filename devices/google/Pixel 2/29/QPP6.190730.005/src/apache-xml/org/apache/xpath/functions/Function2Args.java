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
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.functions.WrongNumberArgsException;

public class Function2Args
extends FunctionOneArg {
    static final long serialVersionUID = 5574294996842710641L;
    Expression m_arg1;

    @Override
    public void callArgVisitors(XPathVisitor xPathVisitor) {
        super.callArgVisitors(xPathVisitor);
        Expression expression = this.m_arg1;
        if (expression != null) {
            expression.callVisitors(new Arg1Owner(), xPathVisitor);
        }
    }

    @Override
    public boolean canTraverseOutsideSubtree() {
        boolean bl = super.canTraverseOutsideSubtree() ? true : this.m_arg1.canTraverseOutsideSubtree();
        return bl;
    }

    @Override
    public void checkNumberArgs(int n) throws WrongNumberArgsException {
        if (n != 2) {
            this.reportWrongNumberArgs();
        }
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        Expression expression2 = this.m_arg1;
        if (expression2 != null) {
            if (((Function2Args)expression).m_arg1 == null) {
                return false;
            }
            if (!expression2.deepEquals(((Function2Args)expression).m_arg1)) {
                return false;
            }
        } else if (((Function2Args)expression).m_arg1 != null) {
            return false;
        }
        return true;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        super.fixupVariables(vector, n);
        Expression expression = this.m_arg1;
        if (expression != null) {
            expression.fixupVariables(vector, n);
        }
    }

    public Expression getArg1() {
        return this.m_arg1;
    }

    @Override
    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("two", null));
    }

    @Override
    public void setArg(Expression expression, int n) throws WrongNumberArgsException {
        if (n == 0) {
            super.setArg(expression, n);
        } else if (1 == n) {
            this.m_arg1 = expression;
            expression.exprSetParent(this);
        } else {
            this.reportWrongNumberArgs();
        }
    }

    class Arg1Owner
    implements ExpressionOwner {
        Arg1Owner() {
        }

        @Override
        public Expression getExpression() {
            return Function2Args.this.m_arg1;
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(Function2Args.this);
            Function2Args.this.m_arg1 = expression;
        }
    }

}

