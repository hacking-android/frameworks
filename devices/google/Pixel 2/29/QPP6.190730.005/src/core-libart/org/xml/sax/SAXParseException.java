/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

import dalvik.annotation.compat.UnsupportedAppUsage;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class SAXParseException
extends SAXException {
    @UnsupportedAppUsage
    private int columnNumber;
    @UnsupportedAppUsage
    private int lineNumber;
    @UnsupportedAppUsage
    private String publicId;
    @UnsupportedAppUsage
    private String systemId;

    public SAXParseException(String string, String string2, String string3, int n, int n2) {
        super(string);
        this.init(string2, string3, n, n2);
    }

    public SAXParseException(String string, String string2, String string3, int n, int n2, Exception exception) {
        super(string, exception);
        this.init(string2, string3, n, n2);
    }

    public SAXParseException(String string, Locator locator) {
        super(string);
        if (locator != null) {
            this.init(locator.getPublicId(), locator.getSystemId(), locator.getLineNumber(), locator.getColumnNumber());
        } else {
            this.init(null, null, -1, -1);
        }
    }

    public SAXParseException(String string, Locator locator, Exception exception) {
        super(string, exception);
        if (locator != null) {
            this.init(locator.getPublicId(), locator.getSystemId(), locator.getLineNumber(), locator.getColumnNumber());
        } else {
            this.init(null, null, -1, -1);
        }
    }

    @UnsupportedAppUsage
    private void init(String string, String string2, int n, int n2) {
        this.publicId = string;
        this.systemId = string2;
        this.lineNumber = n;
        this.columnNumber = n2;
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public String getPublicId() {
        return this.publicId;
    }

    public String getSystemId() {
        return this.systemId;
    }
}

