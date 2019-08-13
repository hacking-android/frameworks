/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.dom3;

import org.w3c.dom.DOMLocator;
import org.w3c.dom.Node;

final class DOMLocatorImpl
implements DOMLocator {
    private final int fByteOffset;
    private final int fColumnNumber;
    private final int fLineNumber;
    private final Node fRelatedNode;
    private final String fUri;
    private final int fUtf16Offset;

    DOMLocatorImpl() {
        this.fColumnNumber = -1;
        this.fLineNumber = -1;
        this.fRelatedNode = null;
        this.fUri = null;
        this.fByteOffset = -1;
        this.fUtf16Offset = -1;
    }

    DOMLocatorImpl(int n, int n2, int n3, String string) {
        this.fLineNumber = n;
        this.fColumnNumber = n2;
        this.fUri = string;
        this.fUtf16Offset = n3;
        this.fRelatedNode = null;
        this.fByteOffset = -1;
    }

    DOMLocatorImpl(int n, int n2, int n3, Node node, String string) {
        this.fLineNumber = n;
        this.fColumnNumber = n2;
        this.fByteOffset = n3;
        this.fRelatedNode = node;
        this.fUri = string;
        this.fUtf16Offset = -1;
    }

    DOMLocatorImpl(int n, int n2, int n3, Node node, String string, int n4) {
        this.fLineNumber = n;
        this.fColumnNumber = n2;
        this.fByteOffset = n3;
        this.fRelatedNode = node;
        this.fUri = string;
        this.fUtf16Offset = n4;
    }

    DOMLocatorImpl(int n, int n2, String string) {
        this.fLineNumber = n;
        this.fColumnNumber = n2;
        this.fUri = string;
        this.fRelatedNode = null;
        this.fByteOffset = -1;
        this.fUtf16Offset = -1;
    }

    @Override
    public int getByteOffset() {
        return this.fByteOffset;
    }

    @Override
    public int getColumnNumber() {
        return this.fColumnNumber;
    }

    @Override
    public int getLineNumber() {
        return this.fLineNumber;
    }

    @Override
    public Node getRelatedNode() {
        return this.fRelatedNode;
    }

    @Override
    public String getUri() {
        return this.fUri;
    }

    @Override
    public int getUtf16Offset() {
        return this.fUtf16Offset;
    }
}

