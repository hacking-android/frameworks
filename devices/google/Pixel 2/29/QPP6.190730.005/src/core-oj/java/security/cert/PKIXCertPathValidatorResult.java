/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.PublicKey;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;

public class PKIXCertPathValidatorResult
implements CertPathValidatorResult {
    private PolicyNode policyTree;
    private PublicKey subjectPublicKey;
    private TrustAnchor trustAnchor;

    public PKIXCertPathValidatorResult(TrustAnchor trustAnchor, PolicyNode policyNode, PublicKey publicKey) {
        if (publicKey != null) {
            if (trustAnchor != null) {
                this.trustAnchor = trustAnchor;
                this.policyTree = policyNode;
                this.subjectPublicKey = publicKey;
                return;
            }
            throw new NullPointerException("trustAnchor must be non-null");
        }
        throw new NullPointerException("subjectPublicKey must be non-null");
    }

    @Override
    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
        }
    }

    public PolicyNode getPolicyTree() {
        return this.policyTree;
    }

    public PublicKey getPublicKey() {
        return this.subjectPublicKey;
    }

    public TrustAnchor getTrustAnchor() {
        return this.trustAnchor;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PKIXCertPathValidatorResult: [\n");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  Trust Anchor: ");
        stringBuilder.append(this.trustAnchor.toString());
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  Policy Tree: ");
        stringBuilder.append(String.valueOf(this.policyTree));
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  Subject Public Key: ");
        stringBuilder.append(this.subjectPublicKey);
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

