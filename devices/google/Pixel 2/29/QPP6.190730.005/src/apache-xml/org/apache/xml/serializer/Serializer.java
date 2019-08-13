/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;
import org.apache.xml.serializer.DOMSerializer;
import org.xml.sax.ContentHandler;

public interface Serializer {
    public ContentHandler asContentHandler() throws IOException;

    public Object asDOM3Serializer() throws IOException;

    public DOMSerializer asDOMSerializer() throws IOException;

    public Properties getOutputFormat();

    public OutputStream getOutputStream();

    public Writer getWriter();

    public boolean reset();

    public void setOutputFormat(Properties var1);

    public void setOutputStream(OutputStream var1);

    public void setWriter(Writer var1);
}

