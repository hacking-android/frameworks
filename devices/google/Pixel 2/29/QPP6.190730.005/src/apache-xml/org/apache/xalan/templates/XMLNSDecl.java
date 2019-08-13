/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.Serializable;

public class XMLNSDecl
implements Serializable {
    static final long serialVersionUID = 6710237366877605097L;
    private boolean m_isExcluded;
    private String m_prefix;
    private String m_uri;

    public XMLNSDecl(String string, String string2, boolean bl) {
        this.m_prefix = string;
        this.m_uri = string2;
        this.m_isExcluded = bl;
    }

    public boolean getIsExcluded() {
        return this.m_isExcluded;
    }

    public String getPrefix() {
        return this.m_prefix;
    }

    public String getURI() {
        return this.m_uri;
    }
}

