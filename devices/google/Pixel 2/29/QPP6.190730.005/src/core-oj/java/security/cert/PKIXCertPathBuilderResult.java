/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;

public class PKIXCertPathBuilderResult
extends PKIXCertPathValidatorResult
implements CertPathBuilderResult {
    private CertPath certPath;

    public PKIXCertPathBuilderResult(CertPath certPath, TrustAnchor trustAnchor, PolicyNode policyNode, PublicKey publicKey) {
        super(trustAnchor, policyNode, publicKey);
        if (certPath != null) {
            this.certPath = certPath;
            return;
        }
        throw new NullPointerException("certPath must be non-null");
    }

    @Override
    public CertPath getCertPath() {
        return this.certPath;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PKIXCertPathBuilderResult: [\n");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  Certification Path: ");
        stringBuilder.append(this.certPath);
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  Trust Anchor: ");
        stringBuilder.append(this.getTrustAnchor().toString());
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  Policy Tree: ");
        stringBuilder.append(String.valueOf(this.getPolicyTree()));
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  Subject Public Key: ");
        stringBuilder.append(this.getPublicKey());
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

