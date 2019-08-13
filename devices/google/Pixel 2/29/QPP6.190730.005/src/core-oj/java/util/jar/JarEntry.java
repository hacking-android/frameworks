/*
 * Decompiled with CFR 0.145.
 */
package java.util.jar;

import java.io.IOException;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.util.jar.Attributes;
import java.util.zip.ZipEntry;

public class JarEntry
extends ZipEntry {
    Attributes attr;
    Certificate[] certs;
    CodeSigner[] signers;

    public JarEntry(String string) {
        super(string);
    }

    public JarEntry(JarEntry jarEntry) {
        this((ZipEntry)jarEntry);
        this.attr = jarEntry.attr;
        this.certs = jarEntry.certs;
        this.signers = jarEntry.signers;
    }

    public JarEntry(ZipEntry zipEntry) {
        super(zipEntry);
    }

    public Attributes getAttributes() throws IOException {
        return this.attr;
    }

    public Certificate[] getCertificates() {
        Object object = this.certs;
        object = object == null ? null : (Certificate[])object.clone();
        return object;
    }

    public CodeSigner[] getCodeSigners() {
        Object object = this.signers;
        object = object == null ? null : (CodeSigner[])object.clone();
        return object;
    }
}

