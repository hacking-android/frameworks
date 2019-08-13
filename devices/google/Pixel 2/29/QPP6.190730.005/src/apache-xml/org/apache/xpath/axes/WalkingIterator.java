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
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.AxesWalker;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.OpMap;

public class WalkingIterator
extends LocPathIterator
implements ExpressionOwner {
    static final long serialVersionUID = 9110225941815665906L;
    protected AxesWalker m_firstWalker;
    protected AxesWalker m_lastUsedWalker;

    public WalkingIterator(PrefixResolver prefixResolver) {
        super(prefixResolver);
    }

    WalkingIterator(Compiler compiler, int n, int n2, boolean bl) throws TransformerException {
        super(compiler, n, n2, bl);
        n = OpMap.getFirstChildPos(n);
        if (bl) {
            this.m_lastUsedWalker = this.m_firstWalker = WalkerFactory.loadWalkers(this, compiler, n, 0);
        }
    }

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        if (xPathVisitor.visitLocationPath(expressionOwner, this) && (expressionOwner = this.m_firstWalker) != null) {
            ((AxesWalker)expressionOwner).callVisitors(this, xPathVisitor);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        WalkingIterator walkingIterator = (WalkingIterator)super.clone();
        AxesWalker axesWalker = this.m_firstWalker;
        if (axesWalker != null) {
            walkingIterator.m_firstWalker = axesWalker.cloneDeep(walkingIterator, null);
        }
        return walkingIterator;
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        AxesWalker axesWalker = this.m_firstWalker;
        for (expression = ((WalkingIterator)expression).m_firstWalker; axesWalker != null && expression != null; axesWalker = axesWalker.getNextWalker(), expression = expression.getNextWalker()) {
            if (axesWalker.deepEquals(expression)) continue;
            return false;
        }
        return axesWalker == null && expression == null;
        {
        }
    }

    @Override
    public void detach() {
        if (this.m_allowDetach) {
            for (AxesWalker axesWalker = this.m_firstWalker; axesWalker != null; axesWalker = axesWalker.getNextWalker()) {
                axesWalker.detach();
            }
            this.m_lastUsedWalker = null;
            super.detach();
        }
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        this.m_predicateIndex = -1;
        for (AxesWalker axesWalker = this.m_firstWalker; axesWalker != null; axesWalker = axesWalker.getNextWalker()) {
            axesWalker.fixupVariables(vector, n);
        }
    }

    @Override
    public int getAnalysisBits() {
        int n = 0;
        int n2 = 0;
        if (this.m_firstWalker != null) {
            AxesWalker axesWalker = this.m_firstWalker;
            do {
                n = n2;
                if (axesWalker == null) break;
                n2 |= axesWalker.getAnalysisBits();
                axesWalker = axesWalker.getNextWalker();
            } while (true);
        }
        return n;
    }

    @Override
    public Expression getExpression() {
        return this.m_firstWalker;
    }

    public final AxesWalker getFirstWalker() {
        return this.m_firstWalker;
    }

    public final AxesWalker getLastUsedWalker() {
        return this.m_lastUsedWalker;
    }

    @Override
    public int nextNode() {
        if (this.m_foundLast) {
            return -1;
        }
        if (-1 == this.m_stackFrame) {
            return this.returnNextNode(this.m_firstWalker.nextNode());
        }
        VariableStack variableStack = this.m_execContext.getVarStack();
        int n = variableStack.getStackFrame();
        variableStack.setStackFrame(this.m_stackFrame);
        int n2 = this.returnNextNode(this.m_firstWalker.nextNode());
        variableStack.setStackFrame(n);
        return n2;
    }

    @Override
    public void reset() {
        super.reset();
        AxesWalker axesWalker = this.m_firstWalker;
        if (axesWalker != null) {
            this.m_lastUsedWalker = axesWalker;
            axesWalker.setRoot(this.m_context);
        }
    }

    @Override
    public void setExpression(Expression expression) {
        expression.exprSetParent(this);
        this.m_firstWalker = (AxesWalker)expression;
    }

    public final void setFirstWalker(AxesWalker axesWalker) {
        this.m_firstWalker = axesWalker;
    }

    public final void setLastUsedWalker(AxesWalker axesWalker) {
        this.m_lastUsedWalker = axesWalker;
    }

    @Override
    public void setRoot(int n, Object object) {
        super.setRoot(n, object);
        object = this.m_firstWalker;
        if (object != null) {
            ((AxesWalker)object).setRoot(n);
            this.m_lastUsedWalker = this.m_firstWalker;
        }
    }
}

