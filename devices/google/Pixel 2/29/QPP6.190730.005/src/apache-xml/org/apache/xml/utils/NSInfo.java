/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

public class NSInfo {
    public static final int ANCESTORHASXMLNS = 1;
    public static final int ANCESTORNOXMLNS = 2;
    public static final int ANCESTORXMLNSUNPROCESSED = 0;
    public int m_ancestorHasXMLNSAttrs;
    public boolean m_hasProcessedNS;
    public boolean m_hasXMLNSAttrs;
    public String m_namespace;

    public NSInfo(String string, boolean bl) {
        this.m_hasProcessedNS = true;
        this.m_hasXMLNSAttrs = bl;
        this.m_namespace = string;
        this.m_ancestorHasXMLNSAttrs = 0;
    }

    public NSInfo(boolean bl, boolean bl2) {
        this.m_hasProcessedNS = bl;
        this.m_hasXMLNSAttrs = bl2;
        this.m_namespace = null;
        this.m_ancestorHasXMLNSAttrs = 0;
    }

    public NSInfo(boolean bl, boolean bl2, int n) {
        this.m_hasProcessedNS = bl;
        this.m_hasXMLNSAttrs = bl2;
        this.m_ancestorHasXMLNSAttrs = n;
        this.m_namespace = null;
    }
}

