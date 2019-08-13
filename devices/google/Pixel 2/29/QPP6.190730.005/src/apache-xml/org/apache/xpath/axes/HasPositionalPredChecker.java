/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.functions.FuncLast;
import org.apache.xpath.functions.FuncPosition;
import org.apache.xpath.functions.Function;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.operations.Div;
import org.apache.xpath.operations.Minus;
import org.apache.xpath.operations.Mod;
import org.apache.xpath.operations.Mult;
import org.apache.xpath.operations.Number;
import org.apache.xpath.operations.Plus;
import org.apache.xpath.operations.Quo;
import org.apache.xpath.operations.Variable;

public class HasPositionalPredChecker
extends XPathVisitor {
    private boolean m_hasPositionalPred = false;
    private int m_predDepth = 0;

    public static boolean check(LocPathIterator locPathIterator) {
        HasPositionalPredChecker hasPositionalPredChecker = new HasPositionalPredChecker();
        locPathIterator.callVisitors(null, hasPositionalPredChecker);
        return hasPositionalPredChecker.m_hasPositionalPred;
    }

    @Override
    public boolean visitFunction(ExpressionOwner expressionOwner, Function function) {
        if (function instanceof FuncPosition || function instanceof FuncLast) {
            this.m_hasPositionalPred = true;
        }
        return true;
    }

    @Override
    public boolean visitPredicate(ExpressionOwner expressionOwner, Expression expression) {
        ++this.m_predDepth;
        if (this.m_predDepth == 1) {
            if (!(expression instanceof Variable || expression instanceof XNumber || expression instanceof Div || expression instanceof Plus || expression instanceof Minus || expression instanceof Mod || expression instanceof Quo || expression instanceof Mult || expression instanceof Number || expression instanceof Function)) {
                expression.callVisitors(expressionOwner, this);
            } else {
                this.m_hasPositionalPred = true;
            }
        }
        --this.m_predDepth;
        return false;
    }
}

