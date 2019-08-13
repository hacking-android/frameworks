/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

public class DigestOutputStream
extends FilterOutputStream {
    protected MessageDigest digest;
    private boolean on = true;

    public DigestOutputStream(OutputStream outputStream, MessageDigest messageDigest) {
        super(outputStream);
        this.setMessageDigest(messageDigest);
    }

    public MessageDigest getMessageDigest() {
        return this.digest;
    }

    public void on(boolean bl) {
        this.on = bl;
    }

    public void setMessageDigest(MessageDigest messageDigest) {
        this.digest = messageDigest;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Digest Output Stream] ");
        stringBuilder.append(this.digest.toString());
        return stringBuilder.toString();
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
        if (this.on) {
            this.digest.update((byte)n);
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (arrby != null && n + n2 <= arrby.length) {
            if (n >= 0 && n2 >= 0) {
                this.out.write(arrby, n, n2);
                if (this.on) {
                    this.digest.update(arrby, n, n2);
                }
                return;
            }
            throw new IndexOutOfBoundsException("wrong index for write");
        }
        throw new IllegalArgumentException("wrong parameters for write");
    }
}

