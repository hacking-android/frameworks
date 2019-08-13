/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.Serializable;

public class NameSpace
implements Serializable {
    static final long serialVersionUID = 1471232939184881839L;
    public NameSpace m_next = null;
    public String m_prefix;
    public String m_uri;

    public NameSpace(String string, String string2) {
        this.m_prefix = string;
        this.m_uri = string2;
    }
}

