/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.dom3;

import java.io.IOException;
import org.apache.xml.serializer.DOM3Serializer;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.serializer.dom3.DOM3TreeWalker;
import org.apache.xml.serializer.utils.WrappedRuntimeException;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSSerializerFilter;
import org.xml.sax.SAXException;

public final class DOM3SerializerImpl
implements DOM3Serializer {
    private DOMErrorHandler fErrorHandler;
    private String fNewLine;
    private SerializationHandler fSerializationHandler;
    private LSSerializerFilter fSerializerFilter;

    public DOM3SerializerImpl(SerializationHandler serializationHandler) {
        this.fSerializationHandler = serializationHandler;
    }

    @Override
    public DOMErrorHandler getErrorHandler() {
        return this.fErrorHandler;
    }

    public char[] getNewLine() {
        Object object = this.fNewLine;
        object = object != null ? object.toCharArray() : null;
        return object;
    }

    @Override
    public LSSerializerFilter getNodeFilter() {
        return this.fSerializerFilter;
    }

    @Override
    public void serializeDOM3(Node node) throws IOException {
        try {
            DOM3TreeWalker dOM3TreeWalker = new DOM3TreeWalker(this.fSerializationHandler, this.fErrorHandler, this.fSerializerFilter, this.fNewLine);
            dOM3TreeWalker.traverse(node);
            return;
        }
        catch (SAXException sAXException) {
            throw new WrappedRuntimeException(sAXException);
        }
    }

    @Override
    public void setErrorHandler(DOMErrorHandler dOMErrorHandler) {
        this.fErrorHandler = dOMErrorHandler;
    }

    @Override
    public void setNewLine(char[] object) {
        object = object != null ? new String((char[])object) : null;
        this.fNewLine = object;
    }

    @Override
    public void setNodeFilter(LSSerializerFilter lSSerializerFilter) {
        this.fSerializerFilter = lSSerializerFilter;
    }

    public void setSerializationHandler(SerializationHandler serializationHandler) {
        this.fSerializationHandler = serializationHandler;
    }
}

