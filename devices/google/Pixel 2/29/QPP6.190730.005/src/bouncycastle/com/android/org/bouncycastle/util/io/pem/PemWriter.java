/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.io.pem;

import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.Base64;
import com.android.org.bouncycastle.util.io.pem.PemHeader;
import com.android.org.bouncycastle.util.io.pem.PemObject;
import com.android.org.bouncycastle.util.io.pem.PemObjectGenerator;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class PemWriter
extends BufferedWriter {
    private static final int LINE_LENGTH = 64;
    private char[] buf = new char[64];
    private final int nlLength;

    public PemWriter(Writer object) {
        super((Writer)object);
        object = Strings.lineSeparator();
        this.nlLength = object != null ? ((String)object).length() : 2;
    }

    private void writeEncoded(byte[] arrobject) throws IOException {
        byte[] arrby = Base64.encode(arrobject);
        for (int i = 0; i < arrby.length; i += this.buf.length) {
            int n;
            for (n = 0; n != (arrobject = this.buf).length && i + n < arrby.length; ++n) {
                arrobject[n] = (char)arrby[i + n];
            }
            this.write(this.buf, 0, n);
            this.newLine();
        }
    }

    private void writePostEncapsulationBoundary(String string) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-----END ");
        stringBuilder.append(string);
        stringBuilder.append("-----");
        this.write(stringBuilder.toString());
        this.newLine();
    }

    private void writePreEncapsulationBoundary(String string) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-----BEGIN ");
        stringBuilder.append(string);
        stringBuilder.append("-----");
        this.write(stringBuilder.toString());
        this.newLine();
    }

    public int getOutputSize(PemObject pemObject) {
        int n;
        int n2 = n = (pemObject.getType().length() + 10 + this.nlLength) * 2 + 6 + 4;
        if (!pemObject.getHeaders().isEmpty()) {
            for (PemHeader pemHeader : pemObject.getHeaders()) {
                n += pemHeader.getName().length() + ": ".length() + pemHeader.getValue().length() + this.nlLength;
            }
            n2 = n + this.nlLength;
        }
        n = (pemObject.getContent().length + 2) / 3 * 4;
        return n2 + ((n + 64 - 1) / 64 * this.nlLength + n);
    }

    public void writeObject(PemObjectGenerator object2) throws IOException {
        PemObject pemObject = object2.generate();
        this.writePreEncapsulationBoundary(pemObject.getType());
        if (!pemObject.getHeaders().isEmpty()) {
            for (Object object2 : pemObject.getHeaders()) {
                this.write(((PemHeader)object2).getName());
                this.write(": ");
                this.write(((PemHeader)object2).getValue());
                this.newLine();
            }
            this.newLine();
        }
        this.writeEncoded(pemObject.getContent());
        this.writePostEncapsulationBoundary(pemObject.getType());
    }
}

