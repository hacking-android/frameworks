/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.Node;

public interface DOMLocator {
    public int getByteOffset();

    public int getColumnNumber();

    public int getLineNumber();

    public Node getRelatedNode();

    public String getUri();

    public int getUtf16Offset();
}

