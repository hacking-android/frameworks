/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.NodeSequence;
import org.apache.xpath.axes.PathComponent;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;

public class FilterExprIteratorSimple
extends LocPathIterator {
    static final long serialVersionUID = -6978977187025375579L;
    private boolean m_canDetachNodeset = true;
    private Expression m_expr;
    private transient XNodeSet m_exprObj;
    private boolean m_mustHardReset = false;

    public FilterExprIteratorSimple() {
        super(null);
    }

    public FilterExprIteratorSimple(Expression expression) {
        super(null);
        this.m_expr = expression;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static XNodeSet executeFilterExpr(int n, XPathContext xPathContext, PrefixResolver object, boolean bl, int n2, Expression serializable) throws WrappedRuntimeException {
        Throwable throwable2222;
        PrefixResolver prefixResolver = xPathContext.getNamespaceContext();
        xPathContext.pushCurrentNode(n);
        xPathContext.setNamespaceContext((PrefixResolver)object);
        if (bl) {
            VariableStack variableStack = xPathContext.getVarStack();
            n = variableStack.getStackFrame();
            variableStack.setStackFrame(n2);
            object = (XNodeSet)((Expression)serializable).execute(xPathContext);
            ((NodeSequence)object).setShouldCacheNodes(true);
            variableStack.setStackFrame(n);
        } else {
            object = (XNodeSet)((Expression)serializable).execute(xPathContext);
        }
        xPathContext.popCurrentNode();
        xPathContext.setNamespaceContext(prefixResolver);
        return object;
        {
            catch (Throwable throwable2222) {
            }
            catch (TransformerException transformerException) {}
            {
                serializable = new WrappedRuntimeException(transformerException);
                throw serializable;
            }
        }
        xPathContext.popCurrentNode();
        xPathContext.setNamespaceContext(prefixResolver);
        throw throwable2222;
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
        expression = (FilterExprIteratorSimple)expression;
        return this.m_expr.deepEquals(((FilterExprIteratorSimple)expression).m_expr);
    }

    @Override
    public void detach() {
        if (this.m_allowDetach) {
            super.detach();
            this.m_exprObj.detach();
            this.m_exprObj = null;
        }
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
        XNodeSet xNodeSet = this.m_exprObj;
        if (xNodeSet != null) {
            return xNodeSet.getAxis();
        }
        return 20;
    }

    public Expression getInnerExpression() {
        return this.m_expr;
    }

    @Override
    public boolean isDocOrdered() {
        return this.m_exprObj.isDocOrdered();
    }

    @Override
    public int nextNode() {
        int n;
        if (this.m_foundLast) {
            return -1;
        }
        XNodeSet xNodeSet = this.m_exprObj;
        if (xNodeSet != null) {
            int n2;
            n = n2 = xNodeSet.nextNode();
            this.m_lastFetched = n2;
        } else {
            n = -1;
            this.m_lastFetched = -1;
        }
        if (-1 != n) {
            ++this.m_pos;
            return n;
        }
        this.m_foundLast = true;
        return -1;
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
            return FilterExprIteratorSimple.this.m_expr;
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(FilterExprIteratorSimple.this);
            FilterExprIteratorSimple.this.m_expr = expression;
        }
    }

}

