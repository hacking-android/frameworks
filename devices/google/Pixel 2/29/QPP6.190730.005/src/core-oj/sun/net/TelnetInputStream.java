/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.net.TelnetProtocolException;

public class TelnetInputStream
extends FilterInputStream {
    public boolean binaryMode = false;
    boolean seenCR = false;
    boolean stickyCRLF = false;

    public TelnetInputStream(InputStream inputStream, boolean bl) {
        super(inputStream);
        this.binaryMode = bl;
    }

    @Override
    public int read() throws IOException {
        if (this.binaryMode) {
            return super.read();
        }
        if (this.seenCR) {
            this.seenCR = false;
            return 10;
        }
        int n = super.read();
        if (n == 13) {
            n = super.read();
            if (n != 0) {
                if (n == 10) {
                    if (this.stickyCRLF) {
                        this.seenCR = true;
                        return 13;
                    }
                    return 10;
                }
                throw new TelnetProtocolException("misplaced CR in input");
            }
            return 13;
        }
        return n;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        if (this.binaryMode) {
            return super.read(arrby, n, n2);
        }
        int n4 = n2;
        n2 = n;
        do {
            int n5;
            n3 = n2;
            n2 = -1;
            if (--n4 < 0 || (n5 = this.read()) == -1) break;
            arrby[n3] = (byte)n5;
            n2 = n3 + 1;
        } while (true);
        if (n3 > n) {
            n2 = n3 - n;
        }
        return n2;
    }

    public void setStickyCRLF(boolean bl) {
        this.stickyCRLF = bl;
    }
}

