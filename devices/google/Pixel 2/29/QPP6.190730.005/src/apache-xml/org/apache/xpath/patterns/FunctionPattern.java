/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.patterns;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.patterns.StepPattern;

public class FunctionPattern
extends StepPattern {
    static final long serialVersionUID = -5426793413091209944L;
    Expression m_functionExpr;

    public FunctionPattern(Expression expression, int n, int n2) {
        super(0, null, null, n, n2);
        this.m_functionExpr = expression;
    }

    @Override
    public final void calcScore() {
        this.m_score = SCORE_OTHER;
        if (this.m_targetString == null) {
            this.calcTargetString();
        }
    }

    @Override
    protected void callSubtreeVisitors(XPathVisitor xPathVisitor) {
        this.m_functionExpr.callVisitors(new FunctionOwner(), xPathVisitor);
        super.callSubtreeVisitors(xPathVisitor);
    }

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        Object object2;
        int n = ((XPathContext)object).getCurrentNode();
        DTMIterator dTMIterator = this.m_functionExpr.asIterator((XPathContext)object, n);
        object = object2 = SCORE_NONE;
        if (dTMIterator != null) {
            do {
                int n2 = dTMIterator.nextNode();
                object = object2;
                if (-1 == n2) break;
                object = n2 == n ? SCORE_OTHER : SCORE_NONE;
                object2 = object;
            } while (object != SCORE_OTHER);
            dTMIterator.detach();
        }
        return object;
    }

    @Override
    public XObject execute(XPathContext object, int n) throws TransformerException {
        DTMIterator dTMIterator = this.m_functionExpr.asIterator((XPathContext)object, n);
        XNumber xNumber = SCORE_NONE;
        object = xNumber;
        if (dTMIterator != null) {
            int n2;
            object = xNumber;
            while (-1 != (n2 = dTMIterator.nextNode())) {
                xNumber = n2 == n ? SCORE_OTHER : SCORE_NONE;
                object = xNumber;
                if (xNumber != SCORE_OTHER) continue;
                object = xNumber;
                break;
            }
        }
        dTMIterator.detach();
        return object;
    }

    @Override
    public XObject execute(XPathContext object, int n, DTM object2, int n2) throws TransformerException {
        DTMIterator dTMIterator = this.m_functionExpr.asIterator((XPathContext)object, n);
        object = object2 = SCORE_NONE;
        if (dTMIterator != null) {
            do {
                n2 = dTMIterator.nextNode();
                object = object2;
                if (-1 == n2) break;
                object = n2 == n ? SCORE_OTHER : SCORE_NONE;
                object2 = object;
            } while (object != SCORE_OTHER);
            dTMIterator.detach();
        }
        return object;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        super.fixupVariables(vector, n);
        this.m_functionExpr.fixupVariables(vector, n);
    }

    class FunctionOwner
    implements ExpressionOwner {
        FunctionOwner() {
        }

        @Override
        public Expression getExpression() {
            return FunctionPattern.this.m_functionExpr;
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(FunctionPattern.this);
            FunctionPattern.this.m_functionExpr = expression;
        }
    }

}

