/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NullCipher;
import javax.crypto.ShortBufferException;

public class CipherInputStream
extends FilterInputStream {
    private Cipher cipher;
    private boolean closed = false;
    private boolean done = false;
    private byte[] ibuffer = new byte[512];
    private InputStream input;
    private byte[] obuffer;
    private int ofinish = 0;
    private int ostart = 0;

    protected CipherInputStream(InputStream inputStream) {
        super(inputStream);
        this.input = inputStream;
        this.cipher = new NullCipher();
    }

    public CipherInputStream(InputStream inputStream, Cipher cipher) {
        super(inputStream);
        this.input = inputStream;
        this.cipher = cipher;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int getMoreData() throws IOException {
        if (this.done) {
            return -1;
        }
        this.ofinish = 0;
        this.ostart = 0;
        int n = this.cipher.getOutputSize(this.ibuffer.length);
        byte[] arrby = this.obuffer;
        if (arrby == null || n > arrby.length) {
            this.obuffer = new byte[n];
        }
        if ((n = this.input.read(this.ibuffer)) == -1) {
            this.done = true;
            try {
                this.ofinish = this.cipher.doFinal(this.obuffer, 0);
                return this.ofinish;
            }
            catch (ShortBufferException shortBufferException) {
                this.obuffer = null;
                throw new IllegalStateException("ShortBufferException is not expected", shortBufferException);
            }
            catch (BadPaddingException | IllegalBlockSizeException generalSecurityException) {
                this.obuffer = null;
                throw new IOException(generalSecurityException);
            }
        }
        try {
            this.ofinish = this.cipher.update(this.ibuffer, 0, n, this.obuffer, 0);
            return this.ofinish;
        }
        catch (ShortBufferException shortBufferException) {
            this.obuffer = null;
            throw new IllegalStateException("ShortBufferException is not expected", shortBufferException);
        }
        catch (IllegalStateException illegalStateException) {
            this.obuffer = null;
            throw illegalStateException;
        }
    }

    @Override
    public int available() throws IOException {
        return this.ofinish - this.ostart;
    }

    @Override
    public void close() throws IOException {
        block4 : {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.input.close();
            if (!this.done) {
                try {
                    this.cipher.doFinal();
                }
                catch (BadPaddingException | IllegalBlockSizeException generalSecurityException) {
                    if (!(generalSecurityException instanceof AEADBadTagException)) break block4;
                    throw new IOException(generalSecurityException);
                }
            }
        }
        this.ostart = 0;
        this.ofinish = 0;
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        int n;
        if (this.ostart >= this.ofinish) {
            n = 0;
            while (n == 0) {
                n = this.getMoreData();
            }
            if (n == -1) {
                return -1;
            }
        }
        byte[] arrby = this.obuffer;
        n = this.ostart;
        this.ostart = n + 1;
        return arrby[n] & 255;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        int n4;
        if (this.ostart >= this.ofinish) {
            n4 = 0;
            while (n4 == 0) {
                n4 = this.getMoreData();
            }
            if (n4 == -1) {
                return -1;
            }
        }
        if (n2 <= 0) {
            return 0;
        }
        n4 = n3 = this.ofinish - this.ostart;
        if (n2 < n3) {
            n4 = n2;
        }
        if (arrby != null) {
            System.arraycopy(this.obuffer, this.ostart, arrby, n, n4);
        }
        this.ostart += n4;
        return n4;
    }

    @Override
    public long skip(long l) throws IOException {
        int n = this.ofinish - this.ostart;
        long l2 = l;
        if (l > (long)n) {
            l2 = n;
        }
        if (l2 < 0L) {
            return 0L;
        }
        this.ostart = (int)((long)this.ostart + l2);
        return l2;
    }
}

