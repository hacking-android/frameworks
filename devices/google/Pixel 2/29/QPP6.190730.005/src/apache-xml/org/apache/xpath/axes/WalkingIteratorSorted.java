/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.axes.AxesWalker;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.axes.WalkingIterator;
import org.apache.xpath.compiler.Compiler;

public class WalkingIteratorSorted
extends WalkingIterator {
    static final long serialVersionUID = -4512512007542368213L;
    protected boolean m_inNaturalOrderStatic = false;

    public WalkingIteratorSorted(PrefixResolver prefixResolver) {
        super(prefixResolver);
    }

    WalkingIteratorSorted(Compiler compiler, int n, int n2, boolean bl) throws TransformerException {
        super(compiler, n, n2, bl);
    }

    boolean canBeWalkedInNaturalDocOrderStatic() {
        if (this.m_firstWalker != null) {
            AxesWalker axesWalker = this.m_firstWalker;
            int n = 0;
            while (axesWalker != null) {
                int n2 = axesWalker.getAxis();
                if (axesWalker.isDocOrdered()) {
                    boolean bl = n2 == 3 || n2 == 13 || n2 == 19;
                    if (!bl && n2 != -1) {
                        n = axesWalker.getNextWalker() == null ? 1 : 0;
                        return n != 0 && (axesWalker.isDocOrdered() && (n2 == 4 || n2 == 5 || n2 == 17 || n2 == 18) || n2 == 2);
                    }
                    axesWalker = axesWalker.getNextWalker();
                    ++n;
                    continue;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        super.fixupVariables(vector, n);
        this.m_inNaturalOrderStatic = WalkerFactory.isNaturalDocOrder(this.getAnalysisBits());
    }

    @Override
    public boolean isDocOrdered() {
        return this.m_inNaturalOrderStatic;
    }
}

