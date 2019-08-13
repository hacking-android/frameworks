/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.compiler.Compiler;

public class ChildIterator
extends LocPathIterator {
    static final long serialVersionUID = -6935428015142993583L;

    ChildIterator(Compiler compiler, int n, int n2) throws TransformerException {
        super(compiler, n, n2, false);
        this.initNodeTest(-1);
    }

    @Override
    public int asNode(XPathContext xPathContext) throws TransformerException {
        int n = xPathContext.getCurrentNode();
        return xPathContext.getDTM(n).getFirstChild(n);
    }

    @Override
    public int getAxis() {
        return 3;
    }

    @Override
    public int nextNode() {
        if (this.m_foundLast) {
            return -1;
        }
        int n = -1 == this.m_lastFetched ? this.m_cdtm.getFirstChild(this.m_context) : this.m_cdtm.getNextSibling(this.m_lastFetched);
        this.m_lastFetched = n;
        if (-1 != n) {
            ++this.m_pos;
            return n;
        }
        this.m_foundLast = true;
        return -1;
    }
}

