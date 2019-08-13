/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.AxesWalker;
import org.apache.xpath.axes.FilterExprIteratorSimple;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.PathComponent;
import org.apache.xpath.axes.WalkingIterator;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.operations.Variable;

public class FilterExprWalker
extends AxesWalker {
    static final long serialVersionUID = 5457182471424488375L;
    private boolean m_canDetachNodeset = true;
    private Expression m_expr;
    private transient XNodeSet m_exprObj;
    private boolean m_mustHardReset = false;

    public FilterExprWalker(WalkingIterator walkingIterator) {
        super(walkingIterator, 20);
    }

    @Override
    public short acceptNode(int n) {
        try {
            if (this.getPredicateCount() > 0) {
                this.countProximityPosition(0);
                boolean bl = this.executePredicates(n, this.m_lpi.getXPathContext());
                if (!bl) {
                    return 3;
                }
            }
            return 1;
        }
        catch (TransformerException transformerException) {
            throw new RuntimeException(transformerException.getMessage());
        }
    }

    @Override
    public void callPredicateVisitors(XPathVisitor xPathVisitor) {
        this.m_expr.callVisitors(new filterExprOwner(), xPathVisitor);
        super.callPredicateVisitors(xPathVisitor);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FilterExprWalker filterExprWalker = (FilterExprWalker)super.clone();
        XNodeSet xNodeSet = this.m_exprObj;
        if (xNodeSet != null) {
            filterExprWalker.m_exprObj = (XNodeSet)xNodeSet.clone();
        }
        return filterExprWalker;
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        expression = (FilterExprWalker)expression;
        return this.m_expr.deepEquals(((FilterExprWalker)expression).m_expr);
    }

    @Override
    public void detach() {
        super.detach();
        if (this.m_canDetachNodeset) {
            this.m_exprObj.detach();
        }
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

    @Override
    public int getAxis() {
        return this.m_exprObj.getAxis();
    }

    public Expression getInnerExpression() {
        return this.m_expr;
    }

    @Override
    public int getLastPos(XPathContext xPathContext) {
        return this.m_exprObj.getLength();
    }

    @Override
    public int getNextNode() {
        XNodeSet xNodeSet = this.m_exprObj;
        if (xNodeSet != null) {
            return xNodeSet.nextNode();
        }
        return -1;
    }

    @Override
    public void init(Compiler compiler, int n, int n2) throws TransformerException {
        super.init(compiler, n, n2);
        switch (n2) {
            default: {
                this.m_expr = compiler.compile(n + 2);
                this.m_expr.exprSetParent(this);
                break;
            }
            case 24: 
            case 25: {
                this.m_mustHardReset = true;
            }
            case 22: 
            case 23: {
                this.m_expr = compiler.compile(n);
                this.m_expr.exprSetParent(this);
                if (!(this.m_expr instanceof Variable)) break;
                this.m_canDetachNodeset = false;
            }
        }
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
    public void setRoot(int n) {
        super.setRoot(n);
        this.m_exprObj = FilterExprIteratorSimple.executeFilterExpr(n, this.m_lpi.getXPathContext(), this.m_lpi.getPrefixResolver(), this.m_lpi.getIsTopLevel(), this.m_lpi.m_stackFrame, this.m_expr);
    }

    class filterExprOwner
    implements ExpressionOwner {
        filterExprOwner() {
        }

        @Override
        public Expression getExpression() {
            return FilterExprWalker.this.m_expr;
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(FilterExprWalker.this);
            FilterExprWalker.this.m_expr = expression;
        }
    }

}

