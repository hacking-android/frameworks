/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import org.apache.xml.serializer.WriterChain;

class WriterToASCI
extends Writer
implements WriterChain {
    private final OutputStream m_os;

    public WriterToASCI(OutputStream outputStream) {
        this.m_os = outputStream;
    }

    @Override
    public void close() throws IOException {
        this.m_os.close();
    }

    @Override
    public void flush() throws IOException {
        this.m_os.flush();
    }

    @Override
    public OutputStream getOutputStream() {
        return this.m_os;
    }

    @Override
    public Writer getWriter() {
        return null;
    }

    @Override
    public void write(int n) throws IOException {
        this.m_os.write(n);
    }

    @Override
    public void write(String string) throws IOException {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            this.m_os.write(string.charAt(i));
        }
    }

    @Override
    public void write(char[] arrc, int n, int n2) throws IOException {
        for (int i = n; i < n2 + n; ++i) {
            this.m_os.write(arrc[i]);
        }
    }
}

