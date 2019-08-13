/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.ChildTestIterator;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.OpMap;

public abstract class BasicTestIterator
extends LocPathIterator {
    static final long serialVersionUID = 3505378079378096623L;

    protected BasicTestIterator() {
    }

    protected BasicTestIterator(PrefixResolver prefixResolver) {
        super(prefixResolver);
    }

    protected BasicTestIterator(Compiler compiler, int n, int n2) throws TransformerException {
        super(compiler, n, n2, false);
        n2 = OpMap.getFirstChildPos(n);
        n = compiler.getWhatToShow(n2);
        if ((n & 4163) != 0 && n != -1) {
            this.initNodeTest(n, compiler.getStepNS(n2), compiler.getStepLocalName(n2));
        } else {
            this.initNodeTest(n);
        }
        this.initPredicateInfo(compiler, n2);
    }

    protected BasicTestIterator(Compiler compiler, int n, int n2, boolean bl) throws TransformerException {
        super(compiler, n, n2, bl);
    }

    @Override
    public DTMIterator cloneWithReset() throws CloneNotSupportedException {
        ChildTestIterator childTestIterator = (ChildTestIterator)super.cloneWithReset();
        childTestIterator.resetProximityPositions();
        return childTestIterator;
    }

    protected abstract int getNextNode();

    @Override
    public int nextNode() {
        int n;
        int n2;
        VariableStack variableStack;
        if (this.m_foundLast) {
            this.m_lastFetched = -1;
            return -1;
        }
        if (-1 == this.m_lastFetched) {
            this.resetProximityPositions();
        }
        if (-1 != this.m_stackFrame) {
            variableStack = this.m_execContext.getVarStack();
            n2 = variableStack.getStackFrame();
            variableStack.setStackFrame(this.m_stackFrame);
        } else {
            variableStack = null;
            n2 = 0;
        }
        do {
            n = this.getNextNode();
            if (-1 == n) break;
            if (1 != this.acceptNode(n) && n != -1) continue;
            break;
        } while (true);
        if (-1 != n) {
            ++this.m_pos;
            return n;
        }
        try {
            this.m_foundLast = true;
            if (-1 != this.m_stackFrame) {
                variableStack.setStackFrame(n2);
            }
            return -1;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            if (-1 != this.m_stackFrame) {
                variableStack.setStackFrame(n2);
            }
        }
    }
}

