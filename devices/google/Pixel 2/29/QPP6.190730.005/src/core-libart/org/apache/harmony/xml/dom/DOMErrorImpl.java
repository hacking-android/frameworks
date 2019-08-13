/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.w3c.dom.DOMError;
import org.w3c.dom.DOMLocator;
import org.w3c.dom.Node;

public final class DOMErrorImpl
implements DOMError {
    private static final DOMLocator NULL_DOM_LOCATOR = new DOMLocator(){

        @Override
        public int getByteOffset() {
            return -1;
        }

        @Override
        public int getColumnNumber() {
            return -1;
        }

        @Override
        public int getLineNumber() {
            return -1;
        }

        @Override
        public Node getRelatedNode() {
            return null;
        }

        @Override
        public String getUri() {
            return null;
        }

        @Override
        public int getUtf16Offset() {
            return -1;
        }
    };
    private final short severity;
    private final String type;

    public DOMErrorImpl(short s, String string) {
        this.severity = s;
        this.type = string;
    }

    @Override
    public DOMLocator getLocation() {
        return NULL_DOM_LOCATOR;
    }

    @Override
    public String getMessage() {
        return this.type;
    }

    @Override
    public Object getRelatedData() {
        return null;
    }

    @Override
    public Object getRelatedException() {
        return null;
    }

    @Override
    public short getSeverity() {
        return this.severity;
    }

    @Override
    public String getType() {
        return this.type;
    }

}

