/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.Serializable;
import javax.xml.transform.SourceLocator;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.LocatorImpl;

public class SAXSourceLocator
extends LocatorImpl
implements SourceLocator,
Serializable {
    static final long serialVersionUID = 3181680946321164112L;
    Locator m_locator;

    public SAXSourceLocator() {
    }

    public SAXSourceLocator(SourceLocator sourceLocator) {
        this.m_locator = null;
        this.setColumnNumber(sourceLocator.getColumnNumber());
        this.setLineNumber(sourceLocator.getLineNumber());
        this.setPublicId(sourceLocator.getPublicId());
        this.setSystemId(sourceLocator.getSystemId());
    }

    public SAXSourceLocator(Locator locator) {
        this.m_locator = locator;
        this.setColumnNumber(locator.getColumnNumber());
        this.setLineNumber(locator.getLineNumber());
        this.setPublicId(locator.getPublicId());
        this.setSystemId(locator.getSystemId());
    }

    public SAXSourceLocator(SAXParseException sAXParseException) {
        this.setLineNumber(sAXParseException.getLineNumber());
        this.setColumnNumber(sAXParseException.getColumnNumber());
        this.setPublicId(sAXParseException.getPublicId());
        this.setSystemId(sAXParseException.getSystemId());
    }

    @Override
    public int getColumnNumber() {
        Locator locator = this.m_locator;
        int n = locator == null ? super.getColumnNumber() : locator.getColumnNumber();
        return n;
    }

    @Override
    public int getLineNumber() {
        Locator locator = this.m_locator;
        int n = locator == null ? super.getLineNumber() : locator.getLineNumber();
        return n;
    }

    @Override
    public String getPublicId() {
        Object object = this.m_locator;
        object = object == null ? super.getPublicId() : object.getPublicId();
        return object;
    }

    @Override
    public String getSystemId() {
        Object object = this.m_locator;
        object = object == null ? super.getSystemId() : object.getSystemId();
        return object;
    }
}

