/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.patterns;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.patterns.NodeTest;
import org.apache.xpath.patterns.StepPattern;

public class UnionPattern
extends Expression {
    static final long serialVersionUID = -6670449967116905820L;
    private StepPattern[] m_patterns;

    @Override
    public void callVisitors(ExpressionOwner arrstepPattern, XPathVisitor xPathVisitor) {
        xPathVisitor.visitUnionPattern((ExpressionOwner)arrstepPattern, this);
        arrstepPattern = this.m_patterns;
        if (arrstepPattern != null) {
            int n = arrstepPattern.length;
            for (int i = 0; i < n; ++i) {
                this.m_patterns[i].callVisitors(new UnionPathPartOwner(i), xPathVisitor);
            }
        }
    }

    @Override
    public boolean canTraverseOutsideSubtree() {
        StepPattern[] arrstepPattern = this.m_patterns;
        if (arrstepPattern != null) {
            int n = arrstepPattern.length;
            for (int i = 0; i < n; ++i) {
                if (!this.m_patterns[i].canTraverseOutsideSubtree()) continue;
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean deepEquals(Expression expression) {
        if (!this.isSameClass(expression)) {
            return false;
        }
        expression = (UnionPattern)expression;
        StepPattern[] arrstepPattern = this.m_patterns;
        if (arrstepPattern != null) {
            int n = arrstepPattern.length;
            arrstepPattern = ((UnionPattern)expression).m_patterns;
            if (arrstepPattern == null || arrstepPattern.length != n) return false;
            for (int i = 0; i < n; ++i) {
                if (this.m_patterns[i].deepEquals(((UnionPattern)expression).m_patterns[i])) continue;
                return false;
            }
            return true;
        } else {
            if (((UnionPattern)expression).m_patterns == null) return true;
            return false;
        }
    }

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        XObject xObject = null;
        int n = this.m_patterns.length;
        for (int i = 0; i < n; ++i) {
            XObject xObject2 = this.m_patterns[i].execute((XPathContext)object);
            XObject xObject3 = xObject;
            if (xObject2 != NodeTest.SCORE_NONE) {
                if (xObject == null) {
                    xObject3 = xObject2;
                } else {
                    xObject3 = xObject;
                    if (xObject2.num() > xObject.num()) {
                        xObject3 = xObject2;
                    }
                }
            }
            xObject = xObject3;
        }
        object = xObject;
        if (xObject == null) {
            object = NodeTest.SCORE_NONE;
        }
        return object;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        StepPattern[] arrstepPattern;
        for (int i = 0; i < (arrstepPattern = this.m_patterns).length; ++i) {
            arrstepPattern[i].fixupVariables(vector, n);
        }
    }

    public StepPattern[] getPatterns() {
        return this.m_patterns;
    }

    public void setPatterns(StepPattern[] arrstepPattern) {
        this.m_patterns = arrstepPattern;
        if (arrstepPattern != null) {
            for (int i = 0; i < arrstepPattern.length; ++i) {
                arrstepPattern[i].exprSetParent(this);
            }
        }
    }

    class UnionPathPartOwner
    implements ExpressionOwner {
        int m_index;

        UnionPathPartOwner(int n) {
            this.m_index = n;
        }

        @Override
        public Expression getExpression() {
            return UnionPattern.this.m_patterns[this.m_index];
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(UnionPattern.this);
            UnionPattern.access$000((UnionPattern)UnionPattern.this)[this.m_index] = (StepPattern)expression;
        }
    }

}

