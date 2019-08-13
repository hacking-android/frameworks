/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import dalvik.annotation.compat.UnsupportedAppUsage;
import org.xml.sax.Locator;

public class LocatorImpl
implements Locator {
    @UnsupportedAppUsage
    private int columnNumber;
    @UnsupportedAppUsage
    private int lineNumber;
    @UnsupportedAppUsage
    private String publicId;
    @UnsupportedAppUsage
    private String systemId;

    public LocatorImpl() {
    }

    public LocatorImpl(Locator locator) {
        this.setPublicId(locator.getPublicId());
        this.setSystemId(locator.getSystemId());
        this.setLineNumber(locator.getLineNumber());
        this.setColumnNumber(locator.getColumnNumber());
    }

    @Override
    public int getColumnNumber() {
        return this.columnNumber;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override
    public String getPublicId() {
        return this.publicId;
    }

    @Override
    public String getSystemId() {
        return this.systemId;
    }

    public void setColumnNumber(int n) {
        this.columnNumber = n;
    }

    public void setLineNumber(int n) {
        this.lineNumber = n;
    }

    public void setPublicId(String string) {
        this.publicId = string;
    }

    public void setSystemId(String string) {
        this.systemId = string;
    }
}

