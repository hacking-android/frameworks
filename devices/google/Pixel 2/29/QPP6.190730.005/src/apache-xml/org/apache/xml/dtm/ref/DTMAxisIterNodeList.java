/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.dtm.ref.DTMNodeListBase;
import org.apache.xml.utils.IntVector;
import org.w3c.dom.Node;

public class DTMAxisIterNodeList
extends DTMNodeListBase {
    private IntVector m_cachedNodes;
    private DTM m_dtm;
    private DTMAxisIterator m_iter;
    private int m_last = -1;

    private DTMAxisIterNodeList() {
    }

    public DTMAxisIterNodeList(DTM dTM, DTMAxisIterator dTMAxisIterator) {
        if (dTMAxisIterator == null) {
            this.m_last = 0;
        } else {
            this.m_cachedNodes = new IntVector();
            this.m_dtm = dTM;
        }
        this.m_iter = dTMAxisIterator;
    }

    public DTMAxisIterator getDTMAxisIterator() {
        return this.m_iter;
    }

    @Override
    public int getLength() {
        if (this.m_last == -1) {
            int n;
            while ((n = this.m_iter.next()) != -1) {
                this.m_cachedNodes.addElement(n);
            }
            this.m_last = this.m_cachedNodes.size();
        }
        return this.m_last;
    }

    @Override
    public Node item(int n) {
        if (this.m_iter != null) {
            int n2 = this.m_cachedNodes.size();
            if (n2 > n) {
                n = this.m_cachedNodes.elementAt(n);
                return this.m_dtm.getNode(n);
            }
            if (this.m_last == -1) {
                int n3;
                while ((n3 = this.m_iter.next()) != -1 && n2 <= n) {
                    this.m_cachedNodes.addElement(n3);
                    ++n2;
                }
                if (n3 == -1) {
                    this.m_last = n2;
                } else {
                    return this.m_dtm.getNode(n3);
                }
            }
        }
        return null;
    }
}

