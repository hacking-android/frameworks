/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemNumber;
import org.apache.xml.dtm.DTM;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.XPathContext;

public class Counter {
    static final int MAXCOUNTNODES = 500;
    NodeSetDTM m_countNodes;
    int m_countNodesStartCount = 0;
    int m_countResult;
    int m_fromNode = -1;
    ElemNumber m_numberElem;

    Counter(ElemNumber elemNumber, NodeSetDTM nodeSetDTM) throws TransformerException {
        this.m_countNodes = nodeSetDTM;
        this.m_numberElem = elemNumber;
    }

    int getLast() {
        int n = this.m_countNodes.size();
        n = n > 0 ? this.m_countNodes.elementAt(n - 1) : -1;
        return n;
    }

    int getPreviouslyCounted(XPathContext xPathContext, int n) {
        int n2 = this.m_countNodes.size();
        this.m_countResult = 0;
        --n2;
        while (n2 >= 0) {
            int n3 = this.m_countNodes.elementAt(n2);
            if (n == n3) {
                this.m_countResult = n2 + 1 + this.m_countNodesStartCount;
                break;
            }
            if (xPathContext.getDTM(n3).isNodeAfter(n3, n)) break;
            --n2;
        }
        return this.m_countResult;
    }
}

