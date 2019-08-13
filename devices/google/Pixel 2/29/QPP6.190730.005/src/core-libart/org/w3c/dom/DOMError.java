/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.DOMLocator;

public interface DOMError {
    public static final short SEVERITY_ERROR = 2;
    public static final short SEVERITY_FATAL_ERROR = 3;
    public static final short SEVERITY_WARNING = 1;

    public DOMLocator getLocation();

    public String getMessage();

    public Object getRelatedData();

    public Object getRelatedException();

    public short getSeverity();

    public String getType();
}

