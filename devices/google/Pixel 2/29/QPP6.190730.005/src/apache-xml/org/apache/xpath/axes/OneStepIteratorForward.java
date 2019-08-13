/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xpath.Expression;
import org.apache.xpath.axes.ChildTestIterator;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.OpMap;

public class OneStepIteratorForward
extends ChildTestIterator {
    static final long serialVersionUID = -1576936606178190566L;
    protected int m_axis = -1;

    public OneStepIteratorForward(int n) {
        super(null);
        this.m_axis = n;
        this.initNodeTest(-1);
    }

    OneStepIteratorForward(Compiler compiler, int n, int n2) throws TransformerException {
        super(compiler, n, n2);
        this.m_axis = WalkerFactory.getAxisFromStep(compiler, OpMap.getFirstChildPos(n));
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        return this.m_axis == ((OneStepIteratorForward)expression).m_axis;
    }

    @Override
    public int getAxis() {
        return this.m_axis;
    }

    @Override
    protected int getNextNode() {
        int n = -1 == this.m_lastFetched ? this.m_traverser.first(this.m_context) : this.m_traverser.next(this.m_context, this.m_lastFetched);
        this.m_lastFetched = n;
        return this.m_lastFetched;
    }

    @Override
    public void setRoot(int n, Object object) {
        super.setRoot(n, object);
        this.m_traverser = this.m_cdtm.getAxisTraverser(this.m_axis);
    }
}

