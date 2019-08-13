/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TelnetOutputStream
extends BufferedOutputStream {
    public boolean binaryMode = false;
    boolean seenCR = false;
    boolean stickyCRLF = false;

    public TelnetOutputStream(OutputStream outputStream, boolean bl) {
        super(outputStream);
        this.binaryMode = bl;
    }

    public void setStickyCRLF(boolean bl) {
        this.stickyCRLF = bl;
    }

    @Override
    public void write(int n) throws IOException {
        if (this.binaryMode) {
            super.write(n);
            return;
        }
        if (this.seenCR) {
            if (n != 10) {
                super.write(0);
            }
            super.write(n);
            if (n != 13) {
                this.seenCR = false;
            }
        } else {
            if (n == 10) {
                super.write(13);
                super.write(10);
                return;
            }
            int n2 = n;
            if (n == 13) {
                if (this.stickyCRLF) {
                    this.seenCR = true;
                    n2 = n;
                } else {
                    super.write(13);
                    n2 = 0;
                }
            }
            super.write(n2);
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        int n3 = n;
        int n4 = n2;
        if (this.binaryMode) {
            super.write(arrby, n, n2);
            return;
        }
        while (--n4 >= 0) {
            this.write(arrby[n3]);
            ++n3;
        }
    }
}

