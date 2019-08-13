/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.AxesWalker;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.WalkingIterator;

public class ReverseAxesWalker
extends AxesWalker {
    static final long serialVersionUID = 2847007647832768941L;
    protected DTMAxisIterator m_iterator;

    ReverseAxesWalker(LocPathIterator locPathIterator, int n) {
        super(locPathIterator, n);
    }

    @Override
    protected void countProximityPosition(int n) {
        if (n < this.m_proximityPositions.length) {
            int[] arrn = this.m_proximityPositions;
            arrn[n] = arrn[n] - 1;
        }
    }

    @Override
    public void detach() {
        this.m_iterator = null;
        super.detach();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getLastPos(XPathContext object) {
        int n;
        block4 : {
            ReverseAxesWalker reverseAxesWalker;
            int n2 = 0;
            n = 0;
            object = this.wi().getLastUsedWalker();
            int n3 = n2;
            try {
                reverseAxesWalker = (ReverseAxesWalker)this.clone();
                n3 = n2;
                reverseAxesWalker.setRoot(this.getRoot());
                n3 = n2;
                reverseAxesWalker.setPredicateCount(this.m_predicateIndex);
                n3 = n2;
                reverseAxesWalker.setPrevWalker(null);
                n3 = n2;
                reverseAxesWalker.setNextWalker(null);
                n3 = n2;
                this.wi().setLastUsedWalker(reverseAxesWalker);
            }
            catch (Throwable throwable) {
                this.wi().setLastUsedWalker((AxesWalker)object);
                throw throwable;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                n = n3;
                break block4;
            }
            do {
                n3 = n++;
            } while (-1 != (n2 = reverseAxesWalker.nextNode()));
        }
        this.wi().setLastUsedWalker((AxesWalker)object);
        return n;
    }

    @Override
    protected int getNextNode() {
        if (this.m_foundLast) {
            return -1;
        }
        int n = this.m_iterator.next();
        if (this.m_isFresh) {
            this.m_isFresh = false;
        }
        if (-1 == n) {
            this.m_foundLast = true;
        }
        return n;
    }

    @Override
    protected int getProximityPosition(int n) {
        int n2;
        if (n < 0) {
            return -1;
        }
        int n3 = n2 = this.m_proximityPositions[n];
        if (n2 <= 0) {
            AxesWalker axesWalker = this.wi().getLastUsedWalker();
            n3 = n2;
            ReverseAxesWalker reverseAxesWalker = (ReverseAxesWalker)this.clone();
            n3 = n2;
            reverseAxesWalker.setRoot(this.getRoot());
            n3 = n2;
            reverseAxesWalker.setPredicateCount(n);
            n3 = n2;
            reverseAxesWalker.setPrevWalker(null);
            n3 = n2;
            reverseAxesWalker.setNextWalker(null);
            n3 = n2++;
            this.wi().setLastUsedWalker(reverseAxesWalker);
            do {
                n3 = n2++;
                if (-1 != reverseAxesWalker.nextNode()) continue;
                break;
            } while (true);
            n3 = n2;
            try {
                this.m_proximityPositions[n] = n2;
            }
            catch (Throwable throwable) {
                this.wi().setLastUsedWalker(axesWalker);
                throw throwable;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                n2 = n3;
            }
            this.wi().setLastUsedWalker(axesWalker);
            n3 = n2;
        }
        return n3;
    }

    @Override
    public boolean isDocOrdered() {
        return false;
    }

    @Override
    public boolean isReverseAxes() {
        return true;
    }

    @Override
    public void setRoot(int n) {
        super.setRoot(n);
        this.m_iterator = this.getDTM(n).getAxisIterator(this.m_axis);
        this.m_iterator.setStartNode(n);
    }
}

