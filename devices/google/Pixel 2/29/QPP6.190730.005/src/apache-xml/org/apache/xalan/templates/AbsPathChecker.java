/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.functions.FuncCurrent;
import org.apache.xpath.functions.FuncExtFunction;
import org.apache.xpath.functions.Function;
import org.apache.xpath.operations.Variable;

public class AbsPathChecker
extends XPathVisitor {
    private boolean m_isAbs = true;

    public boolean checkAbsolute(LocPathIterator locPathIterator) {
        this.m_isAbs = true;
        locPathIterator.callVisitors(null, this);
        return this.m_isAbs;
    }

    @Override
    public boolean visitFunction(ExpressionOwner expressionOwner, Function function) {
        if (function instanceof FuncCurrent || function instanceof FuncExtFunction) {
            this.m_isAbs = false;
        }
        return true;
    }

    @Override
    public boolean visitVariableRef(ExpressionOwner expressionOwner, Variable variable) {
        this.m_isAbs = false;
        return true;
    }
}

