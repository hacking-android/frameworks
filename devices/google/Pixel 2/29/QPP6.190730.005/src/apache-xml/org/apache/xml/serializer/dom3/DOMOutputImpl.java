/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.dom3;

import java.io.OutputStream;
import java.io.Writer;
import org.w3c.dom.ls.LSOutput;

final class DOMOutputImpl
implements LSOutput {
    private OutputStream fByteStream = null;
    private Writer fCharStream = null;
    private String fEncoding = null;
    private String fSystemId = null;

    DOMOutputImpl() {
    }

    @Override
    public OutputStream getByteStream() {
        return this.fByteStream;
    }

    @Override
    public Writer getCharacterStream() {
        return this.fCharStream;
    }

    @Override
    public String getEncoding() {
        return this.fEncoding;
    }

    @Override
    public String getSystemId() {
        return this.fSystemId;
    }

    @Override
    public void setByteStream(OutputStream outputStream) {
        this.fByteStream = outputStream;
    }

    @Override
    public void setCharacterStream(Writer writer) {
        this.fCharStream = writer;
    }

    @Override
    public void setEncoding(String string) {
        this.fEncoding = string;
    }

    @Override
    public void setSystemId(String string) {
        this.fSystemId = string;
    }
}

