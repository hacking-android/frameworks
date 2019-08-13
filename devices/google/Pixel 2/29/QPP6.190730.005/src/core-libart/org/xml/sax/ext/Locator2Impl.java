/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.ext;

import dalvik.annotation.compat.UnsupportedAppUsage;
import org.xml.sax.Locator;
import org.xml.sax.ext.Locator2;
import org.xml.sax.helpers.LocatorImpl;

public class Locator2Impl
extends LocatorImpl
implements Locator2 {
    @UnsupportedAppUsage
    private String encoding;
    @UnsupportedAppUsage
    private String version;

    public Locator2Impl() {
    }

    public Locator2Impl(Locator locator) {
        super(locator);
        if (locator instanceof Locator2) {
            locator = (Locator2)locator;
            this.version = locator.getXMLVersion();
            this.encoding = locator.getEncoding();
        }
    }

    @Override
    public String getEncoding() {
        return this.encoding;
    }

    @Override
    public String getXMLVersion() {
        return this.version;
    }

    public void setEncoding(String string) {
        this.encoding = string;
    }

    public void setXMLVersion(String string) {
        this.version = string;
    }
}

