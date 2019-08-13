/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemNumber;
import org.apache.xalan.transformer.Counter;
import org.apache.xml.dtm.DTMManager;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.XPathContext;

public class CountersTable
extends Hashtable {
    static final long serialVersionUID = 2159100770924179875L;
    transient int m_countersMade = 0;
    private transient NodeSetDTM m_newFound;

    void appendBtoFList(NodeSetDTM nodeSetDTM, NodeSetDTM nodeSetDTM2) {
        for (int i = nodeSetDTM2.size() - 1; i >= 0; --i) {
            nodeSetDTM.addElement(nodeSetDTM2.item(i));
        }
    }

    public int countNode(XPathContext object, ElemNumber elemNumber, int n) throws TransformerException {
        int n2 = 0;
        Vector vector = this.getCounters(elemNumber);
        int n3 = vector.size();
        int n4 = elemNumber.getTargetNode((XPathContext)object, n);
        n = n2;
        if (-1 != n4) {
            int n5;
            for (n = 0; n < n3; ++n) {
                n2 = ((Counter)vector.elementAt(n)).getPreviouslyCounted((XPathContext)object, n4);
                if (n2 <= 0) continue;
                return n2;
            }
            n = n5 = 0;
            n2 = n4;
            if (this.m_newFound == null) {
                this.m_newFound = new NodeSetDTM(((XPathContext)object).getDTMManager());
                n2 = n4;
                n = n5;
            }
            while (-1 != n2) {
                if (n != 0) {
                    for (n4 = 0; n4 < n3; ++n4) {
                        Counter counter = (Counter)vector.elementAt(n4);
                        n5 = counter.m_countNodes.size();
                        if (n5 <= 0 || counter.m_countNodes.elementAt(n5 - 1) != n2) continue;
                        n2 = counter.m_countNodesStartCount;
                        if (n5 > 0) {
                            this.appendBtoFList(counter.m_countNodes, this.m_newFound);
                        }
                        this.m_newFound.removeAllElements();
                        return n + (n2 + n5);
                    }
                }
                this.m_newFound.addElement(n2);
                ++n;
                n2 = elemNumber.getPreviousNode((XPathContext)object, n2);
            }
            object = new Counter(elemNumber, new NodeSetDTM(((XPathContext)object).getDTMManager()));
            ++this.m_countersMade;
            this.appendBtoFList(((Counter)object).m_countNodes, this.m_newFound);
            this.m_newFound.removeAllElements();
            vector.addElement(object);
        }
        return n;
    }

    Vector getCounters(ElemNumber serializable) {
        Vector vector = (Vector)this.get(serializable);
        serializable = vector == null ? this.putElemNumber((ElemNumber)serializable) : vector;
        return serializable;
    }

    Vector putElemNumber(ElemNumber elemNumber) {
        Vector vector = new Vector();
        this.put(elemNumber, vector);
        return vector;
    }
}

