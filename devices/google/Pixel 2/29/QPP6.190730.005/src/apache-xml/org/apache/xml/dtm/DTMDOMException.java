/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm;

import org.w3c.dom.DOMException;

public class DTMDOMException
extends DOMException {
    static final long serialVersionUID = 1895654266613192414L;

    public DTMDOMException(short s) {
        super(s, "");
    }

    public DTMDOMException(short s, String string) {
        super(s, string);
    }
}

