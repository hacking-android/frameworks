/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NullCipher;

public class CipherOutputStream
extends FilterOutputStream {
    private Cipher cipher;
    private boolean closed = false;
    private byte[] ibuffer = new byte[1];
    private byte[] obuffer;
    private OutputStream output;

    protected CipherOutputStream(OutputStream outputStream) {
        super(outputStream);
        this.output = outputStream;
        this.cipher = new NullCipher();
    }

    public CipherOutputStream(OutputStream outputStream, Cipher cipher) {
        super(outputStream);
        this.output = outputStream;
        this.cipher = cipher;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.obuffer = this.cipher.doFinal();
        try {
            this.flush();
        }
        catch (IOException iOException) {}
        this.out.close();
        return;
        catch (BadPaddingException | IllegalBlockSizeException generalSecurityException) {
            this.obuffer = null;
            throw new IOException(generalSecurityException);
        }
    }

    @Override
    public void flush() throws IOException {
        byte[] arrby = this.obuffer;
        if (arrby != null) {
            this.output.write(arrby);
            this.obuffer = null;
        }
        this.output.flush();
    }

    @Override
    public void write(int n) throws IOException {
        byte[] arrby = this.ibuffer;
        arrby[0] = (byte)n;
        this.obuffer = this.cipher.update(arrby, 0, 1);
        arrby = this.obuffer;
        if (arrby != null) {
            this.output.write(arrby);
            this.obuffer = null;
        }
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.obuffer = this.cipher.update(arrby, n, n2);
        arrby = this.obuffer;
        if (arrby != null) {
            this.output.write(arrby);
            this.obuffer = null;
        }
    }
}

