/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;
import java.net.URL;
import java.security.CodeSigner;
import java.security.cert.Certificate;

public class CodeSource
implements Serializable {
    private URL location;

    public CodeSource(URL uRL, CodeSigner[] arrcodeSigner) {
        this.location = uRL;
    }

    public CodeSource(URL uRL, Certificate[] arrcertificate) {
        this.location = uRL;
    }

    public final Certificate[] getCertificates() {
        return null;
    }

    public final CodeSigner[] getCodeSigners() {
        return null;
    }

    public final URL getLocation() {
        return this.location;
    }

    public boolean implies(CodeSource codeSource) {
        return true;
    }
}

