/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.ref.DTMNodeListBase;
import org.w3c.dom.Node;

public class DTMNodeList
extends DTMNodeListBase {
    private DTMIterator m_iter;

    private DTMNodeList() {
    }

    public DTMNodeList(DTMIterator dTMIterator) {
        if (dTMIterator != null) {
            int n = dTMIterator.getCurrentPos();
            try {
                this.m_iter = dTMIterator.cloneWithReset();
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                this.m_iter = dTMIterator;
            }
            this.m_iter.setShouldCacheNodes(true);
            this.m_iter.runTo(-1);
            this.m_iter.setCurrentPos(n);
        }
    }

    public DTMIterator getDTMIterator() {
        return this.m_iter;
    }

    @Override
    public int getLength() {
        DTMIterator dTMIterator = this.m_iter;
        int n = dTMIterator != null ? dTMIterator.getLength() : 0;
        return n;
    }

    @Override
    public Node item(int n) {
        DTMIterator dTMIterator = this.m_iter;
        if (dTMIterator != null) {
            if ((n = dTMIterator.item(n)) == -1) {
                return null;
            }
            return this.m_iter.getDTM(n).getNode(n);
        }
        return null;
    }
}

