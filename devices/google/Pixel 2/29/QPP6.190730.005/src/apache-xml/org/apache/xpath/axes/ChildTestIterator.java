/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.axes.BasicTestIterator;
import org.apache.xpath.compiler.Compiler;

public class ChildTestIterator
extends BasicTestIterator {
    static final long serialVersionUID = -7936835957960705722L;
    protected transient DTMAxisTraverser m_traverser;

    public ChildTestIterator(DTMAxisTraverser dTMAxisTraverser) {
        super(null);
        this.m_traverser = dTMAxisTraverser;
    }

    ChildTestIterator(Compiler compiler, int n, int n2) throws TransformerException {
        super(compiler, n, n2);
    }

    @Override
    public DTMIterator cloneWithReset() throws CloneNotSupportedException {
        ChildTestIterator childTestIterator = (ChildTestIterator)super.cloneWithReset();
        childTestIterator.m_traverser = this.m_traverser;
        return childTestIterator;
    }

    @Override
    public void detach() {
        if (this.m_allowDetach) {
            this.m_traverser = null;
            super.detach();
        }
    }

    @Override
    public int getAxis() {
        return 3;
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
        this.m_traverser = this.m_cdtm.getAxisTraverser(3);
    }
}

