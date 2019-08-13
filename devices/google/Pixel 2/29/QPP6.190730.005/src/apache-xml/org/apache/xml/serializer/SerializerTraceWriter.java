/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.WriterChain;

final class SerializerTraceWriter
extends Writer
implements WriterChain {
    private byte[] buf;
    private int buf_length;
    private int count;
    private final SerializerTrace m_tracer;
    private final Writer m_writer;

    public SerializerTraceWriter(Writer writer, SerializerTrace serializerTrace) {
        this.m_writer = writer;
        this.m_tracer = serializerTrace;
        this.setBufferSize(1024);
    }

    private void flushBuffer() throws IOException {
        int n = this.count;
        if (n > 0) {
            char[] arrc = new char[n];
            for (n = 0; n < this.count; ++n) {
                arrc[n] = (char)this.buf[n];
            }
            SerializerTrace serializerTrace = this.m_tracer;
            if (serializerTrace != null) {
                serializerTrace.fireGenerateEvent(12, arrc, 0, arrc.length);
            }
            this.count = 0;
        }
    }

    private void setBufferSize(int n) {
        this.buf = new byte[n + 3];
        this.buf_length = n;
        this.count = 0;
    }

    @Override
    public void close() throws IOException {
        Writer writer = this.m_writer;
        if (writer != null) {
            writer.close();
        }
        this.flushBuffer();
    }

    @Override
    public void flush() throws IOException {
        Writer writer = this.m_writer;
        if (writer != null) {
            writer.flush();
        }
        this.flushBuffer();
    }

    @Override
    public OutputStream getOutputStream() {
        OutputStream outputStream = null;
        Writer writer = this.m_writer;
        if (writer instanceof WriterChain) {
            outputStream = ((WriterChain)((Object)writer)).getOutputStream();
        }
        return outputStream;
    }

    @Override
    public Writer getWriter() {
        return this.m_writer;
    }

    @Override
    public void write(int n) throws IOException {
        byte[] arrby = this.m_writer;
        if (arrby != null) {
            arrby.write(n);
        }
        if (this.count >= this.buf_length) {
            this.flushBuffer();
        }
        if (n < 128) {
            arrby = this.buf;
            int n2 = this.count;
            this.count = n2 + 1;
            arrby[n2] = (byte)n;
        } else if (n < 2048) {
            arrby = this.buf;
            int n3 = this.count;
            this.count = n3 + 1;
            arrby[n3] = (byte)((n >> 6) + 192);
            n3 = this.count;
            this.count = n3 + 1;
            arrby[n3] = (byte)((n & 63) + 128);
        } else {
            arrby = this.buf;
            int n4 = this.count;
            this.count = n4 + 1;
            arrby[n4] = (byte)((n >> 12) + 224);
            n4 = this.count;
            this.count = n4 + 1;
            arrby[n4] = (byte)((n >> 6 & 63) + 128);
            n4 = this.count;
            this.count = n4 + 1;
            arrby[n4] = (byte)((n & 63) + 128);
        }
    }

    @Override
    public void write(String string) throws IOException {
        int n;
        int n2;
        byte[] arrby = this.m_writer;
        if (arrby != null) {
            arrby.write(string);
        }
        if ((n2 = ((n = string.length()) << 1) + n) >= this.buf_length) {
            this.flushBuffer();
            this.setBufferSize(n2 * 2);
        }
        if (n2 > this.buf_length - this.count) {
            this.flushBuffer();
        }
        for (n2 = 0; n2 < n; ++n2) {
            int n3;
            char c = string.charAt(n2);
            if (c < '') {
                arrby = this.buf;
                n3 = this.count;
                this.count = n3 + 1;
                arrby[n3] = (byte)c;
                continue;
            }
            if (c < '\u0800') {
                arrby = this.buf;
                n3 = this.count;
                this.count = n3 + 1;
                arrby[n3] = (byte)((c >> 6) + 192);
                n3 = this.count;
                this.count = n3 + 1;
                arrby[n3] = (byte)((c & 63) + 128);
                continue;
            }
            arrby = this.buf;
            n3 = this.count;
            this.count = n3 + 1;
            arrby[n3] = (byte)((c >> 12) + 224);
            n3 = this.count;
            this.count = n3 + 1;
            arrby[n3] = (byte)((c >> 6 & 63) + 128);
            n3 = this.count;
            this.count = n3 + 1;
            arrby[n3] = (byte)((c & 63) + 128);
        }
    }

    @Override
    public void write(char[] arrc, int n, int n2) throws IOException {
        int n3;
        byte[] arrby = this.m_writer;
        if (arrby != null) {
            arrby.write(arrc, n, n2);
        }
        if ((n3 = (n2 << 1) + n2) >= this.buf_length) {
            this.flushBuffer();
            this.setBufferSize(n3 * 2);
        }
        if (n3 > this.buf_length - this.count) {
            this.flushBuffer();
        }
        for (n3 = n; n3 < n2 + n; ++n3) {
            int n4;
            char c = arrc[n3];
            if (c < '') {
                arrby = this.buf;
                n4 = this.count;
                this.count = n4 + 1;
                arrby[n4] = (byte)c;
                continue;
            }
            if (c < '\u0800') {
                arrby = this.buf;
                n4 = this.count;
                this.count = n4 + 1;
                arrby[n4] = (byte)((c >> 6) + 192);
                n4 = this.count;
                this.count = n4 + 1;
                arrby[n4] = (byte)((c & 63) + 128);
                continue;
            }
            arrby = this.buf;
            n4 = this.count;
            this.count = n4 + 1;
            arrby[n4] = (byte)((c >> 12) + 224);
            n4 = this.count;
            this.count = n4 + 1;
            arrby[n4] = (byte)((c >> 6 & 63) + 128);
            n4 = this.count;
            this.count = n4 + 1;
            arrby[n4] = (byte)((c & 63) + 128);
        }
    }
}

