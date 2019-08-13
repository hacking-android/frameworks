/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xpath.axes.ChildTestIterator;
import org.apache.xpath.compiler.Compiler;

public class AttributeIterator
extends ChildTestIterator {
    static final long serialVersionUID = -8417986700712229686L;

    AttributeIterator(Compiler compiler, int n, int n2) throws TransformerException {
        super(compiler, n, n2);
    }

    @Override
    public int getAxis() {
        return 2;
    }

    @Override
    protected int getNextNode() {
        int n = -1 == this.m_lastFetched ? this.m_cdtm.getFirstAttribute(this.m_context) : this.m_cdtm.getNextAttribute(this.m_lastFetched);
        this.m_lastFetched = n;
        return this.m_lastFetched;
    }
}

