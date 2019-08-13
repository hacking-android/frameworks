/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform.stream;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import javax.xml.transform.Result;
import javax.xml.transform.stream.FilePathToURI;

public class StreamResult
implements Result {
    public static final String FEATURE = "http://javax.xml.transform.stream.StreamResult/feature";
    private OutputStream outputStream;
    private String systemId;
    private Writer writer;

    public StreamResult() {
    }

    public StreamResult(File file) {
        this.setSystemId(file);
    }

    public StreamResult(OutputStream outputStream) {
        this.setOutputStream(outputStream);
    }

    public StreamResult(Writer writer) {
        this.setWriter(writer);
    }

    public StreamResult(String string) {
        this.systemId = string;
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    @Override
    public String getSystemId() {
        return this.systemId;
    }

    public Writer getWriter() {
        return this.writer;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setSystemId(File file) {
        this.systemId = FilePathToURI.filepath2URI(file.getAbsolutePath());
    }

    @Override
    public void setSystemId(String string) {
        this.systemId = string;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}

