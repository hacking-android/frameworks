/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

public class WrappedRuntimeException
extends RuntimeException {
    static final long serialVersionUID = 7140414456714658073L;
    private Exception m_exception;

    public WrappedRuntimeException(Exception exception) {
        super(exception.getMessage());
        this.m_exception = exception;
    }

    public WrappedRuntimeException(String string, Exception exception) {
        super(string);
        this.m_exception = exception;
    }

    public Exception getException() {
        return this.m_exception;
    }
}

