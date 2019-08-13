/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;

public interface DOMImplementationSource {
    public DOMImplementation getDOMImplementation(String var1);

    public DOMImplementationList getDOMImplementationList(String var1);
}

