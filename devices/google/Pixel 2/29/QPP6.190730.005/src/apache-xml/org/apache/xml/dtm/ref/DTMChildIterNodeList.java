/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.ref.DTMNodeListBase;
import org.w3c.dom.Node;

public class DTMChildIterNodeList
extends DTMNodeListBase {
    private int m_firstChild;
    private DTM m_parentDTM;

    private DTMChildIterNodeList() {
    }

    public DTMChildIterNodeList(DTM dTM, int n) {
        this.m_parentDTM = dTM;
        this.m_firstChild = dTM.getFirstChild(n);
    }

    @Override
    public int getLength() {
        int n = 0;
        int n2 = this.m_firstChild;
        while (n2 != -1) {
            ++n;
            n2 = this.m_parentDTM.getNextSibling(n2);
        }
        return n;
    }

    @Override
    public Node item(int n) {
        int n2 = this.m_firstChild;
        int n3 = n;
        n = n2;
        while (--n3 >= 0 && n != -1) {
            n = this.m_parentDTM.getNextSibling(n);
        }
        if (n == -1) {
            return null;
        }
        return this.m_parentDTM.getNode(n);
    }
}

