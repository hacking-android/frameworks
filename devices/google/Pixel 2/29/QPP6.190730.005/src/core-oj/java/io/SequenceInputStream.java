/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

public class SequenceInputStream
extends InputStream {
    Enumeration<? extends InputStream> e;
    InputStream in;

    public SequenceInputStream(InputStream inputStream, InputStream inputStream2) {
        Vector<InputStream> vector = new Vector<InputStream>(2);
        vector.addElement(inputStream);
        vector.addElement(inputStream2);
        this.e = vector.elements();
        try {
            this.nextStream();
            return;
        }
        catch (IOException iOException) {
            throw new Error("panic");
        }
    }

    public SequenceInputStream(Enumeration<? extends InputStream> enumeration) {
        this.e = enumeration;
        try {
            this.nextStream();
            return;
        }
        catch (IOException iOException) {
            throw new Error("panic");
        }
    }

    @Override
    public int available() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream == null) {
            return 0;
        }
        return inputStream.available();
    }

    @Override
    public void close() throws IOException {
        do {
            this.nextStream();
        } while (this.in != null);
    }

    final void nextStream() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream != null) {
            inputStream.close();
        }
        if (this.e.hasMoreElements()) {
            this.in = this.e.nextElement();
            if (this.in == null) {
                throw new NullPointerException();
            }
        } else {
            this.in = null;
        }
    }

    @Override
    public int read() throws IOException {
        InputStream inputStream;
        while ((inputStream = this.in) != null) {
            int n = inputStream.read();
            if (n != -1) {
                return n;
            }
            this.nextStream();
        }
        return -1;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (this.in == null) {
            return -1;
        }
        if (arrby != null) {
            if (n >= 0 && n2 >= 0 && n2 <= arrby.length - n) {
                if (n2 == 0) {
                    return 0;
                }
                do {
                    int n3;
                    if ((n3 = this.in.read(arrby, n, n2)) > 0) {
                        return n3;
                    }
                    this.nextStream();
                } while (this.in != null);
                return -1;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException();
    }
}

