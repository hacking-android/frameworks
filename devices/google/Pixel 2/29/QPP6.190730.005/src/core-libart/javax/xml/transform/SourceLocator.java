/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform;

public interface SourceLocator {
    public int getColumnNumber();

    public int getLineNumber();

    public String getPublicId();

    public String getSystemId();
}

