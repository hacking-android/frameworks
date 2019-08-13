/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.util.Selector;
import com.android.org.bouncycastle.x509.ExtendedPKIXParameters;
import com.android.org.bouncycastle.x509.X509CertStoreSelector;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.cert.CertSelector;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ExtendedPKIXBuilderParameters
extends ExtendedPKIXParameters {
    private Set excludedCerts = Collections.EMPTY_SET;
    private int maxPathLength = 5;

    public ExtendedPKIXBuilderParameters(Set set, Selector selector) throws InvalidAlgorithmParameterException {
        super(set);
        this.setTargetConstraints(selector);
    }

    public static ExtendedPKIXParameters getInstance(PKIXParameters pKIXParameters) {
        try {
            ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = new ExtendedPKIXBuilderParameters(pKIXParameters.getTrustAnchors(), X509CertStoreSelector.getInstance((X509CertSelector)pKIXParameters.getTargetCertConstraints()));
            extendedPKIXBuilderParameters.setParams(pKIXParameters);
            return extendedPKIXBuilderParameters;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Object clone() {
        try {
            ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = new ExtendedPKIXBuilderParameters(this.getTrustAnchors(), this.getTargetConstraints());
            extendedPKIXBuilderParameters.setParams(this);
            return extendedPKIXBuilderParameters;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public Set getExcludedCerts() {
        return Collections.unmodifiableSet(this.excludedCerts);
    }

    public int getMaxPathLength() {
        return this.maxPathLength;
    }

    public void setExcludedCerts(Set set) {
        if (set == null) {
            set = Collections.EMPTY_SET;
        } else {
            this.excludedCerts = new HashSet(set);
        }
    }

    public void setMaxPathLength(int n) {
        if (n >= -1) {
            this.maxPathLength = n;
            return;
        }
        throw new InvalidParameterException("The maximum path length parameter can not be less than -1.");
    }

    @Override
    protected void setParams(PKIXParameters pKIXParameters) {
        super.setParams(pKIXParameters);
        if (pKIXParameters instanceof ExtendedPKIXBuilderParameters) {
            ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = (ExtendedPKIXBuilderParameters)pKIXParameters;
            this.maxPathLength = extendedPKIXBuilderParameters.maxPathLength;
            this.excludedCerts = new HashSet(extendedPKIXBuilderParameters.excludedCerts);
        }
        if (pKIXParameters instanceof PKIXBuilderParameters) {
            this.maxPathLength = ((PKIXBuilderParameters)pKIXParameters).getMaxPathLength();
        }
    }
}

