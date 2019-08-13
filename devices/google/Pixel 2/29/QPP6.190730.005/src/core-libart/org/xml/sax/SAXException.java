/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

import dalvik.annotation.compat.UnsupportedAppUsage;

public class SAXException
extends Exception {
    @UnsupportedAppUsage
    private Exception exception;

    public SAXException() {
        this.exception = null;
    }

    public SAXException(Exception exception) {
        this.exception = exception;
    }

    public SAXException(String string) {
        super(string);
        this.exception = null;
    }

    public SAXException(String string, Exception exception) {
        super(string);
        this.exception = exception;
    }

    public Exception getException() {
        return this.exception;
    }

    @Override
    public String getMessage() {
        Exception exception;
        String string = super.getMessage();
        if (string == null && (exception = this.exception) != null) {
            return exception.getMessage();
        }
        return string;
    }

    @Override
    public String toString() {
        Exception exception = this.exception;
        if (exception != null) {
            return exception.toString();
        }
        return super.toString();
    }
}

