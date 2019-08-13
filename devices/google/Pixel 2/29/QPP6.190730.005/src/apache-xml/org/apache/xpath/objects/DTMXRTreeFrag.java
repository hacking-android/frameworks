/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import org.apache.xml.dtm.DTM;
import org.apache.xpath.XPathContext;

public final class DTMXRTreeFrag {
    private DTM m_dtm;
    private int m_dtmIdentity = -1;
    private XPathContext m_xctxt;

    public DTMXRTreeFrag(int n, XPathContext xPathContext) {
        this.m_xctxt = xPathContext;
        this.m_dtmIdentity = n;
        this.m_dtm = xPathContext.getDTM(n);
    }

    public final void destruct() {
        this.m_dtm = null;
        this.m_xctxt = null;
    }

    public final boolean equals(Object object) {
        boolean bl = object instanceof DTMXRTreeFrag;
        boolean bl2 = false;
        if (bl) {
            if (this.m_dtmIdentity == ((DTMXRTreeFrag)object).getDTMIdentity()) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    final DTM getDTM() {
        return this.m_dtm;
    }

    public final int getDTMIdentity() {
        return this.m_dtmIdentity;
    }

    final XPathContext getXPathContext() {
        return this.m_xctxt;
    }

    public final int hashCode() {
        return this.m_dtmIdentity;
    }
}

