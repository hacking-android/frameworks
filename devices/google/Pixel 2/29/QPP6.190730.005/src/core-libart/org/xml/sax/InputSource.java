/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.InputStream;
import java.io.Reader;

public class InputSource {
    @UnsupportedAppUsage
    private InputStream byteStream;
    @UnsupportedAppUsage
    private Reader characterStream;
    @UnsupportedAppUsage
    private String encoding;
    @UnsupportedAppUsage
    private String publicId;
    @UnsupportedAppUsage
    private String systemId;

    public InputSource() {
    }

    public InputSource(InputStream inputStream) {
        this.setByteStream(inputStream);
    }

    public InputSource(Reader reader) {
        this.setCharacterStream(reader);
    }

    public InputSource(String string) {
        this.setSystemId(string);
    }

    public InputStream getByteStream() {
        return this.byteStream;
    }

    public Reader getCharacterStream() {
        return this.characterStream;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public String getPublicId() {
        return this.publicId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setByteStream(InputStream inputStream) {
        this.byteStream = inputStream;
    }

    public void setCharacterStream(Reader reader) {
        this.characterStream = reader;
    }

    public void setEncoding(String string) {
        this.encoding = string;
    }

    public void setPublicId(String string) {
        this.publicId = string;
    }

    public void setSystemId(String string) {
        this.systemId = string;
    }
}

