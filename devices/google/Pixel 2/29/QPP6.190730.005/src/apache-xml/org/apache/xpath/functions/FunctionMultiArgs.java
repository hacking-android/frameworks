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
import org.apache.xpath.functions.Function3Args;
import org.apache.xpath.functions.WrongNumberArgsException;

public class FunctionMultiArgs
extends Function3Args {
    static final long serialVersionUID = 7117257746138417181L;
    Expression[] m_args;

    @Override
    public void callArgVisitors(XPathVisitor xPathVisitor) {
        super.callArgVisitors(xPathVisitor);
        Expression[] arrexpression = this.m_args;
        if (arrexpression != null) {
            int n = arrexpression.length;
            for (int i = 0; i < n; ++i) {
                this.m_args[i].callVisitors(new ArgMultiOwner(i), xPathVisitor);
            }
        }
    }

    @Override
    public boolean canTraverseOutsideSubtree() {
        if (super.canTraverseOutsideSubtree()) {
            return true;
        }
        int n = this.m_args.length;
        for (int i = 0; i < n; ++i) {
            if (!this.m_args[i].canTraverseOutsideSubtree()) continue;
            return true;
        }
        return false;
    }

    @Override
    public void checkNumberArgs(int n) throws WrongNumberArgsException {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        expression = (FunctionMultiArgs)expression;
        Expression[] arrexpression = this.m_args;
        if (arrexpression != null) {
            int n = arrexpression.length;
            if (expression == null || ((FunctionMultiArgs)expression).m_args.length != n) return false;
            for (int i = 0; i < n; ++i) {
                if (this.m_args[i].deepEquals(((FunctionMultiArgs)expression).m_args[i])) continue;
                return false;
            }
            return true;
        } else {
            if (((FunctionMultiArgs)expression).m_args == null) return true;
            return false;
        }
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        super.fixupVariables(vector, n);
        if (this.m_args != null) {
            Expression[] arrexpression;
            for (int i = 0; i < (arrexpression = this.m_args).length; ++i) {
                arrexpression[i].fixupVariables(vector, n);
            }
        }
    }

    public Expression[] getArgs() {
        return this.m_args;
    }

    @Override
    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[]{"Programmer's assertion:  the method FunctionMultiArgs.reportWrongNumberArgs() should never be called."}));
    }

    @Override
    public void setArg(Expression expression, int n) throws WrongNumberArgsException {
        if (n < 3) {
            super.setArg(expression, n);
        } else {
            Expression[] arrexpression = this.m_args;
            if (arrexpression == null) {
                this.m_args = new Expression[1];
                this.m_args[0] = expression;
            } else {
                Expression[] arrexpression2 = new Expression[arrexpression.length + 1];
                System.arraycopy(arrexpression, 0, arrexpression2, 0, arrexpression.length);
                arrexpression2[this.m_args.length] = expression;
                this.m_args = arrexpression2;
            }
            expression.exprSetParent(this);
        }
    }

    class ArgMultiOwner
    implements ExpressionOwner {
        int m_argIndex;

        ArgMultiOwner(int n) {
            this.m_argIndex = n;
        }

        @Override
        public Expression getExpression() {
            return FunctionMultiArgs.this.m_args[this.m_argIndex];
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(FunctionMultiArgs.this);
            FunctionMultiArgs.this.m_args[this.m_argIndex] = expression;
        }
    }

}

