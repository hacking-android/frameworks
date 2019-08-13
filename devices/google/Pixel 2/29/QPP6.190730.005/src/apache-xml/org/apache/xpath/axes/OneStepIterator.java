/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.ChildTestIterator;
import org.apache.xpath.axes.SubContextList;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.OpMap;

public class OneStepIterator
extends ChildTestIterator {
    static final long serialVersionUID = 4623710779664998283L;
    protected int m_axis = -1;
    protected DTMAxisIterator m_iterator;

    public OneStepIterator(DTMAxisIterator dTMAxisIterator, int n) throws TransformerException {
        super(null);
        this.m_iterator = dTMAxisIterator;
        this.m_axis = n;
        this.initNodeTest(-1);
    }

    OneStepIterator(Compiler compiler, int n, int n2) throws TransformerException {
        super(compiler, n, n2);
        this.m_axis = WalkerFactory.getAxisFromStep(compiler, OpMap.getFirstChildPos(n));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        OneStepIterator oneStepIterator = (OneStepIterator)super.clone();
        DTMAxisIterator dTMAxisIterator = this.m_iterator;
        if (dTMAxisIterator != null) {
            oneStepIterator.m_iterator = dTMAxisIterator.cloneIterator();
        }
        return oneStepIterator;
    }

    @Override
    public DTMIterator cloneWithReset() throws CloneNotSupportedException {
        OneStepIterator oneStepIterator = (OneStepIterator)super.cloneWithReset();
        oneStepIterator.m_iterator = this.m_iterator;
        return oneStepIterator;
    }

    @Override
    protected void countProximityPosition(int n) {
        if (!this.isReverseAxes()) {
            super.countProximityPosition(n);
        } else if (n < this.m_proximityPositions.length) {
            int[] arrn = this.m_proximityPositions;
            arrn[n] = arrn[n] - 1;
        }
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        return this.m_axis == ((OneStepIterator)expression).m_axis;
    }

    @Override
    public void detach() {
        if (this.m_allowDetach) {
            if (this.m_axis > -1) {
                this.m_iterator = null;
            }
            super.detach();
        }
    }

    @Override
    public int getAxis() {
        return this.m_axis;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getLength() {
        int n;
        XPathContext xPathContext;
        boolean bl;
        block7 : {
            OneStepIterator oneStepIterator;
            if (!this.isReverseAxes()) {
                return super.getLength();
            }
            bl = this == this.m_execContext.getSubContextList();
            this.getPredicateCount();
            if (-1 != this.m_length && bl && this.m_predicateIndex < 1) {
                return this.m_length;
            }
            int n2 = 0;
            n = 0;
            xPathContext = this.getXPathContext();
            int n3 = n2;
            try {
                oneStepIterator = (OneStepIterator)this.cloneWithReset();
                n3 = n2;
                int n4 = this.getRoot();
                n3 = n2;
                xPathContext.pushCurrentNode(n4);
                n3 = n2;
                oneStepIterator.setRoot(n4, xPathContext);
                n3 = n2;
                oneStepIterator.m_predCount = this.m_predicateIndex;
            }
            catch (Throwable throwable) {
                xPathContext.popCurrentNode();
                throw throwable;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                n = n3;
                break block7;
            }
            do {
                n3 = n++;
            } while (-1 != (n2 = oneStepIterator.nextNode()));
        }
        xPathContext.popCurrentNode();
        if (bl && this.m_predicateIndex < 1) {
            this.m_length = n;
        }
        return n;
    }

    @Override
    protected int getNextNode() {
        int n;
        this.m_lastFetched = n = this.m_iterator.next();
        return n;
    }

    @Override
    protected int getProximityPosition(int n) {
        block8 : {
            if (!this.isReverseAxes()) {
                return super.getProximityPosition(n);
            }
            if (n < 0) {
                return -1;
            }
            if (this.m_proximityPositions[n] > 0) break block8;
            XPathContext xPathContext = this.getXPathContext();
            int[] arrn = (int[])this.clone();
            int n2 = this.getRoot();
            xPathContext.pushCurrentNode(n2);
            arrn.setRoot(n2, xPathContext);
            arrn.m_predCount = n;
            n2 = 1;
            do {
                if (-1 == arrn.nextNode()) break;
                ++n2;
            } while (true);
            try {
                arrn = this.m_proximityPositions;
            }
            catch (Throwable throwable) {
                xPathContext.popCurrentNode();
                throw throwable;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                // empty catch block
            }
            arrn[n] = arrn[n] + n2;
            xPathContext.popCurrentNode();
        }
        return this.m_proximityPositions[n];
    }

    @Override
    public boolean isReverseAxes() {
        return this.m_iterator.isReverse();
    }

    @Override
    public void reset() {
        super.reset();
        DTMAxisIterator dTMAxisIterator = this.m_iterator;
        if (dTMAxisIterator != null) {
            dTMAxisIterator.reset();
        }
    }

    @Override
    public void setRoot(int n, Object object) {
        super.setRoot(n, object);
        if (this.m_axis > -1) {
            this.m_iterator = this.m_cdtm.getAxisIterator(this.m_axis);
        }
        this.m_iterator.setStartNode(this.m_context);
    }
}

