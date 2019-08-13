/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.dom3;

import org.apache.xml.serializer.dom3.DOMLocatorImpl;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMLocator;

public final class DOMErrorImpl
implements DOMError {
    private Exception fException = null;
    private DOMLocatorImpl fLocation = new DOMLocatorImpl();
    private String fMessage = null;
    private Object fRelatedData;
    private short fSeverity = (short)(true ? 1 : 0);
    private String fType;

    DOMErrorImpl() {
    }

    public DOMErrorImpl(short s, String string, String string2) {
        this.fSeverity = s;
        this.fMessage = string;
        this.fType = string2;
    }

    public DOMErrorImpl(short s, String string, String string2, Exception exception) {
        this.fSeverity = s;
        this.fMessage = string;
        this.fType = string2;
        this.fException = exception;
    }

    public DOMErrorImpl(short s, String string, String string2, Exception exception, Object object, DOMLocatorImpl dOMLocatorImpl) {
        this.fSeverity = s;
        this.fMessage = string;
        this.fType = string2;
        this.fException = exception;
        this.fRelatedData = object;
        this.fLocation = dOMLocatorImpl;
    }

    @Override
    public DOMLocator getLocation() {
        return this.fLocation;
    }

    @Override
    public String getMessage() {
        return this.fMessage;
    }

    @Override
    public Object getRelatedData() {
        return this.fRelatedData;
    }

    @Override
    public Object getRelatedException() {
        return this.fException;
    }

    @Override
    public short getSeverity() {
        return this.fSeverity;
    }

    @Override
    public String getType() {
        return this.fType;
    }

    public void reset() {
        this.fSeverity = (short)(true ? 1 : 0);
        this.fException = null;
        this.fMessage = null;
        this.fType = null;
        this.fRelatedData = null;
        this.fLocation = null;
    }
}

