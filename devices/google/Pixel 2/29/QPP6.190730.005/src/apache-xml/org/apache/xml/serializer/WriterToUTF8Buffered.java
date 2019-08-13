/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import org.apache.xml.serializer.WriterChain;

final class WriterToUTF8Buffered
extends Writer
implements WriterChain {
    private static final int BYTES_MAX = 16384;
    private static final int CHARS_MAX = 5461;
    private int count;
    private final char[] m_inputChars;
    private final OutputStream m_os;
    private final byte[] m_outputBytes;

    public WriterToUTF8Buffered(OutputStream outputStream) {
        this.m_os = outputStream;
        this.m_outputBytes = new byte[16387];
        this.m_inputChars = new char[5463];
        this.count = 0;
    }

    @Override
    public void close() throws IOException {
        this.flushBuffer();
        this.m_os.close();
    }

    @Override
    public void flush() throws IOException {
        this.flushBuffer();
        this.m_os.flush();
    }

    public void flushBuffer() throws IOException {
        int n = this.count;
        if (n > 0) {
            this.m_os.write(this.m_outputBytes, 0, n);
            this.count = 0;
        }
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
        if (this.count >= 16384) {
            this.flushBuffer();
        }
        if (n < 128) {
            byte[] arrby = this.m_outputBytes;
            int n2 = this.count;
            this.count = n2 + 1;
            arrby[n2] = (byte)n;
        } else if (n < 2048) {
            byte[] arrby = this.m_outputBytes;
            int n3 = this.count;
            this.count = n3 + 1;
            arrby[n3] = (byte)((n >> 6) + 192);
            n3 = this.count;
            this.count = n3 + 1;
            arrby[n3] = (byte)((n & 63) + 128);
        } else if (n < 65536) {
            byte[] arrby = this.m_outputBytes;
            int n4 = this.count;
            this.count = n4 + 1;
            arrby[n4] = (byte)((n >> 12) + 224);
            n4 = this.count;
            this.count = n4 + 1;
            arrby[n4] = (byte)((n >> 6 & 63) + 128);
            n4 = this.count;
            this.count = n4 + 1;
            arrby[n4] = (byte)((n & 63) + 128);
        } else {
            byte[] arrby = this.m_outputBytes;
            int n5 = this.count;
            this.count = n5 + 1;
            arrby[n5] = (byte)((n >> 18) + 240);
            n5 = this.count;
            this.count = n5 + 1;
            arrby[n5] = (byte)((n >> 12 & 63) + 128);
            n5 = this.count;
            this.count = n5 + 1;
            arrby[n5] = (byte)((n >> 6 & 63) + 128);
            n5 = this.count;
            this.count = n5 + 1;
            arrby[n5] = (byte)((n & 63) + 128);
        }
    }

    @Override
    public void write(String arrby) throws IOException {
        int n;
        int n2;
        int n3;
        int n4 = arrby.length();
        int n5 = n4 * 3;
        if (n5 >= 16384 - this.count) {
            this.flushBuffer();
            if (n5 > 16384) {
                int n6 = n4 / 5461;
                if (n4 % 5461 > 0) {
                    ++n6;
                }
                n5 = 0;
                for (int i = 1; i <= n6; ++i) {
                    int n7 = (int)((long)n4 * (long)i / (long)n6) + 0;
                    arrby.getChars(n5, n7, this.m_inputChars, 0);
                    int n8 = n7 - n5;
                    char c = this.m_inputChars[n8 - 1];
                    n5 = n7;
                    int n9 = n8;
                    if (c >= '\ud800') {
                        n5 = n7;
                        n9 = n8;
                        if (c <= '\udbff') {
                            n5 = n7 - 1;
                            n9 = n8 - 1;
                        }
                    }
                    this.write(this.m_inputChars, 0, n9);
                }
                return;
            }
        }
        arrby.getChars(0, n4, this.m_inputChars, 0);
        char[] arrc = this.m_inputChars;
        arrby = this.m_outputBytes;
        int n10 = this.count;
        n5 = 0;
        do {
            n = n10;
            n2 = n5;
            if (n5 >= n4) break;
            n3 = arrc[n5];
            n = n10++;
            n2 = n5++;
            if (n3 >= 128) break;
            arrby[n10] = (byte)n3;
        } while (true);
        while (n2 < n4) {
            n10 = arrc[n2];
            if (n10 < 128) {
                arrby[n] = (byte)n10;
                n5 = n + 1;
            } else if (n10 < 2048) {
                n3 = n + 1;
                arrby[n] = (byte)((n10 >> 6) + 192);
                n5 = n3 + 1;
                arrby[n3] = (byte)((n10 & 63) + 128);
            } else if (n10 >= 55296 && n10 <= 56319) {
                n3 = arrc[++n2];
                n5 = n + 1;
                arrby[n] = (byte)(n10 + 64 >> 8 & 240 | 240);
                int n11 = n5 + 1;
                arrby[n5] = (byte)(n10 + 64 >> 2 & 63 | 128);
                n = n11 + 1;
                arrby[n11] = (byte)((n3 >> 6 & 15) + (n10 << 4 & 48) | 128);
                n5 = n + 1;
                arrby[n] = (byte)(n3 & 63 | 128);
            } else {
                n5 = n + 1;
                arrby[n] = (byte)((n10 >> 12) + 224);
                n = n5 + 1;
                arrby[n5] = (byte)((n10 >> 6 & 63) + 128);
                arrby[n] = (byte)((n10 & 63) + 128);
                n5 = n + 1;
            }
            ++n2;
            n = n5;
        }
        this.count = n;
    }

    @Override
    public void write(char[] arrc, int n, int n2) throws IOException {
        int n3;
        int n4;
        int n5 = n2 * 3;
        if (n5 >= 16384 - this.count) {
            this.flushBuffer();
            if (n5 > 16384) {
                int n6 = n2 / 5461;
                if (n2 % 5461 > 0) {
                    ++n6;
                }
                n5 = n;
                int n7 = 1;
                do {
                    int n8 = n5;
                    if (n7 > n6) break;
                    int n9 = n + (int)((long)n2 * (long)n7 / (long)n6);
                    char c = arrc[n9 - 1];
                    n5 = arrc[n9 - 1];
                    n5 = n9;
                    if (c >= '\ud800') {
                        n5 = n9;
                        if (c <= '\udbff') {
                            n5 = n9 < n + n2 ? n9 + 1 : n9 - 1;
                        }
                    }
                    this.write(arrc, n8, n5 - n8);
                    ++n7;
                } while (true);
                return;
            }
        }
        int n10 = n2 + n;
        byte[] arrby = this.m_outputBytes;
        n2 = this.count;
        n5 = n;
        n = n2;
        do {
            n4 = n;
            n2 = n5;
            if (n5 >= n10) break;
            n3 = arrc[n5];
            n4 = n++;
            n2 = n5++;
            if (n3 >= 128) break;
            arrby[n] = (byte)n3;
        } while (true);
        while (n2 < n10) {
            n5 = arrc[n2];
            if (n5 < 128) {
                arrby[n4] = (byte)n5;
                n = n4 + 1;
            } else if (n5 < 2048) {
                n3 = n4 + 1;
                arrby[n4] = (byte)((n5 >> 6) + 192);
                n = n3 + 1;
                arrby[n3] = (byte)((n5 & 63) + 128);
            } else if (n5 >= 55296 && n5 <= 56319) {
                n3 = arrc[++n2];
                n = n4 + 1;
                arrby[n4] = (byte)(n5 + 64 >> 8 & 240 | 240);
                n4 = n + 1;
                arrby[n] = (byte)(n5 + 64 >> 2 & 63 | 128);
                int n11 = n4 + 1;
                arrby[n4] = (byte)((n3 >> 6 & 15) + (n5 << 4 & 48) | 128);
                n = n11 + 1;
                arrby[n11] = (byte)(n3 & 63 | 128);
            } else {
                n = n4 + 1;
                arrby[n4] = (byte)((n5 >> 12) + 224);
                n4 = n + 1;
                arrby[n] = (byte)((n5 >> 6 & 63) + 128);
                arrby[n4] = (byte)((n5 & 63) + 128);
                n = n4 + 1;
            }
            ++n2;
            n4 = n;
        }
        this.count = n4;
    }
}

