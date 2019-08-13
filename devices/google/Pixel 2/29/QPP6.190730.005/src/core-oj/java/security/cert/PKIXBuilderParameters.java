/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertSelector;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.util.Set;

public class PKIXBuilderParameters
extends PKIXParameters {
    private int maxPathLength = 5;

    public PKIXBuilderParameters(KeyStore keyStore, CertSelector certSelector) throws KeyStoreException, InvalidAlgorithmParameterException {
        super(keyStore);
        this.setTargetCertConstraints(certSelector);
    }

    public PKIXBuilderParameters(Set<TrustAnchor> set, CertSelector certSelector) throws InvalidAlgorithmParameterException {
        super(set);
        this.setTargetCertConstraints(certSelector);
    }

    public int getMaxPathLength() {
        return this.maxPathLength;
    }

    public void setMaxPathLength(int n) {
        if (n >= -1) {
            this.maxPathLength = n;
            return;
        }
        throw new InvalidParameterException("the maximum path length parameter can not be less than -1");
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[\n");
        stringBuffer.append(super.toString());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  Maximum Path Length: ");
        stringBuilder.append(this.maxPathLength);
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuffer.append("]\n");
        return stringBuffer.toString();
    }
}

