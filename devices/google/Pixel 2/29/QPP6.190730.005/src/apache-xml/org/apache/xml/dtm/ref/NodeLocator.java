/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import javax.xml.transform.SourceLocator;

public class NodeLocator
implements SourceLocator {
    protected int m_columnNumber;
    protected int m_lineNumber;
    protected String m_publicId;
    protected String m_systemId;

    public NodeLocator(String string, String string2, int n, int n2) {
        this.m_publicId = string;
        this.m_systemId = string2;
        this.m_lineNumber = n;
        this.m_columnNumber = n2;
    }

    @Override
    public int getColumnNumber() {
        return this.m_columnNumber;
    }

    @Override
    public int getLineNumber() {
        return this.m_lineNumber;
    }

    @Override
    public String getPublicId() {
        return this.m_publicId;
    }

    @Override
    public String getSystemId() {
        return this.m_systemId;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("file '");
        stringBuilder.append(this.m_systemId);
        stringBuilder.append("', line #");
        stringBuilder.append(this.m_lineNumber);
        stringBuilder.append(", column #");
        stringBuilder.append(this.m_columnNumber);
        return stringBuilder.toString();
    }
}

