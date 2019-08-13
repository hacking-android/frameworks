/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.Writer;

public abstract class FilterWriter
extends Writer {
    protected Writer out;

    protected FilterWriter(Writer writer) {
        super(writer);
        this.out = writer;
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
    }

    @Override
    public void write(String string, int n, int n2) throws IOException {
        this.out.write(string, n, n2);
    }

    @Override
    public void write(char[] arrc, int n, int n2) throws IOException {
        this.out.write(arrc, n, n2);
    }
}

