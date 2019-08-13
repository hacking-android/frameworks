/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSSerializerFilter;

public interface DOM3Serializer {
    public DOMErrorHandler getErrorHandler();

    public LSSerializerFilter getNodeFilter();

    public void serializeDOM3(Node var1) throws IOException;

    public void setErrorHandler(DOMErrorHandler var1);

    public void setNewLine(char[] var1);

    public void setNodeFilter(LSSerializerFilter var1);
}

