/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.util.Vector;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.BasicTestIterator;
import org.apache.xpath.axes.FilterExprIteratorSimple;
import org.apache.xpath.axes.PathComponent;
import org.apache.xpath.objects.XNodeSet;

public class FilterExprIterator
extends BasicTestIterator {
    static final long serialVersionUID = 2552176105165737614L;
    private boolean m_canDetachNodeset = true;
    private Expression m_expr;
    private transient XNodeSet m_exprObj;
    private boolean m_mustHardReset = false;

    public FilterExprIterator() {
        super(null);
    }

    public FilterExprIterator(Expression expression) {
        super(null);
        this.m_expr = expression;
    }

    @Override
    public void callPredicateVisitors(XPathVisitor xPathVisitor) {
        this.m_expr.callVisitors(new filterExprOwner(), xPathVisitor);
        super.callPredicateVisitors(xPathVisitor);
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        expression = (FilterExprIterator)expression;
        return this.m_expr.deepEquals(((FilterExprIterator)expression).m_expr);
    }

    @Override
    public void detach() {
        super.detach();
        this.m_exprObj.detach();
        this.m_exprObj = null;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        super.fixupVariables(vector, n);
        this.m_expr.fixupVariables(vector, n);
    }

    @Override
    public int getAnalysisBits() {
        Expression expression = this.m_expr;
        if (expression != null && expression instanceof PathComponent) {
            return ((PathComponent)((Object)expression)).getAnalysisBits();
        }
        return 67108864;
    }

    public Expression getInnerExpression() {
        return this.m_expr;
    }

    @Override
    protected int getNextNode() {
        XNodeSet xNodeSet = this.m_exprObj;
        this.m_lastFetched = xNodeSet != null ? xNodeSet.nextNode() : -1;
        return this.m_lastFetched;
    }

    @Override
    public boolean isDocOrdered() {
        return this.m_exprObj.isDocOrdered();
    }

    public void setInnerExpression(Expression expression) {
        expression.exprSetParent(this);
        this.m_expr = expression;
    }

    @Override
    public void setRoot(int n, Object object) {
        super.setRoot(n, object);
        this.m_exprObj = FilterExprIteratorSimple.executeFilterExpr(n, this.m_execContext, this.getPrefixResolver(), this.getIsTopLevel(), this.m_stackFrame, this.m_expr);
    }

    class filterExprOwner
    implements ExpressionOwner {
        filterExprOwner() {
        }

        @Override
        public Expression getExpression() {
            return FilterExprIterator.this.m_expr;
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(FilterExprIterator.this);
            FilterExprIterator.this.m_expr = expression;
        }
    }

}

