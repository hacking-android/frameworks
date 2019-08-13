/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import javax.xml.transform.TransformerException;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.compiler.Compiler;

public class SelfIteratorNoPredicate
extends LocPathIterator {
    static final long serialVersionUID = -4226887905279814201L;

    public SelfIteratorNoPredicate() throws TransformerException {
        super(null);
    }

    SelfIteratorNoPredicate(Compiler compiler, int n, int n2) throws TransformerException {
        super(compiler, n, n2, false);
    }

    @Override
    public int asNode(XPathContext xPathContext) throws TransformerException {
        return xPathContext.getCurrentNode();
    }

    @Override
    public int getLastPos(XPathContext xPathContext) {
        return 1;
    }

    @Override
    public int nextNode() {
        if (this.m_foundLast) {
            return -1;
        }
        int n = -1 == this.m_lastFetched ? this.m_context : -1;
        this.m_lastFetched = n;
        if (-1 != n) {
            ++this.m_pos;
            return n;
        }
        this.m_foundLast = true;
        return -1;
    }
}

