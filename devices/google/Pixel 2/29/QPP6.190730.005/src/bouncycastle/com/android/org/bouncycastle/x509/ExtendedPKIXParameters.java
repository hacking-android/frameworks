/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.util.Selector;
import com.android.org.bouncycastle.util.Store;
import com.android.org.bouncycastle.x509.PKIXAttrCertChecker;
import com.android.org.bouncycastle.x509.X509CertStoreSelector;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ExtendedPKIXParameters
extends PKIXParameters {
    public static final int CHAIN_VALIDITY_MODEL = 1;
    public static final int PKIX_VALIDITY_MODEL = 0;
    private boolean additionalLocationsEnabled;
    private List additionalStores = new ArrayList();
    private Set attrCertCheckers = new HashSet();
    private Set necessaryACAttributes = new HashSet();
    private Set prohibitedACAttributes = new HashSet();
    private Selector selector;
    private List stores = new ArrayList();
    private Set trustedACIssuers = new HashSet();
    private boolean useDeltas = false;
    private int validityModel = 0;

    public ExtendedPKIXParameters(Set set) throws InvalidAlgorithmParameterException {
        super(set);
    }

    public static ExtendedPKIXParameters getInstance(PKIXParameters pKIXParameters) {
        try {
            ExtendedPKIXParameters extendedPKIXParameters = new ExtendedPKIXParameters(pKIXParameters.getTrustAnchors());
            extendedPKIXParameters.setParams(pKIXParameters);
            return extendedPKIXParameters;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void addAddionalStore(Store store) {
        this.addAdditionalStore(store);
    }

    public void addAdditionalStore(Store store) {
        if (store != null) {
            this.additionalStores.add(store);
        }
    }

    public void addStore(Store store) {
        if (store != null) {
            this.stores.add(store);
        }
    }

    @Override
    public Object clone() {
        try {
            ExtendedPKIXParameters extendedPKIXParameters = new ExtendedPKIXParameters(this.getTrustAnchors());
            extendedPKIXParameters.setParams(this);
            return extendedPKIXParameters;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public List getAdditionalStores() {
        return Collections.unmodifiableList(this.additionalStores);
    }

    public Set getAttrCertCheckers() {
        return Collections.unmodifiableSet(this.attrCertCheckers);
    }

    public Set getNecessaryACAttributes() {
        return Collections.unmodifiableSet(this.necessaryACAttributes);
    }

    public Set getProhibitedACAttributes() {
        return Collections.unmodifiableSet(this.prohibitedACAttributes);
    }

    public List getStores() {
        return Collections.unmodifiableList(new ArrayList(this.stores));
    }

    public Selector getTargetConstraints() {
        Selector selector = this.selector;
        if (selector != null) {
            return (Selector)selector.clone();
        }
        return null;
    }

    public Set getTrustedACIssuers() {
        return Collections.unmodifiableSet(this.trustedACIssuers);
    }

    public int getValidityModel() {
        return this.validityModel;
    }

    public boolean isAdditionalLocationsEnabled() {
        return this.additionalLocationsEnabled;
    }

    public boolean isUseDeltasEnabled() {
        return this.useDeltas;
    }

    public void setAdditionalLocationsEnabled(boolean bl) {
        this.additionalLocationsEnabled = bl;
    }

    public void setAttrCertCheckers(Set object) {
        if (object == null) {
            this.attrCertCheckers.clear();
            return;
        }
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof PKIXAttrCertChecker) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("All elements of set must be of type ");
            ((StringBuilder)object).append(PKIXAttrCertChecker.class.getName());
            ((StringBuilder)object).append(".");
            throw new ClassCastException(((StringBuilder)object).toString());
        }
        this.attrCertCheckers.clear();
        this.attrCertCheckers.addAll(object);
    }

    public void setCertStores(List object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                this.addCertStore((CertStore)object.next());
            }
        }
    }

    public void setNecessaryACAttributes(Set set) {
        if (set == null) {
            this.necessaryACAttributes.clear();
            return;
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof String) continue;
            throw new ClassCastException("All elements of set must be of type String.");
        }
        this.necessaryACAttributes.clear();
        this.necessaryACAttributes.addAll(set);
    }

    protected void setParams(PKIXParameters cloneable) {
        this.setDate(cloneable.getDate());
        this.setCertPathCheckers(cloneable.getCertPathCheckers());
        this.setCertStores(cloneable.getCertStores());
        this.setAnyPolicyInhibited(cloneable.isAnyPolicyInhibited());
        this.setExplicitPolicyRequired(cloneable.isExplicitPolicyRequired());
        this.setPolicyMappingInhibited(cloneable.isPolicyMappingInhibited());
        this.setRevocationEnabled(cloneable.isRevocationEnabled());
        this.setInitialPolicies(cloneable.getInitialPolicies());
        this.setPolicyQualifiersRejected(cloneable.getPolicyQualifiersRejected());
        this.setSigProvider(cloneable.getSigProvider());
        this.setTargetCertConstraints(cloneable.getTargetCertConstraints());
        try {
            this.setTrustAnchors(cloneable.getTrustAnchors());
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
        if (cloneable instanceof ExtendedPKIXParameters) {
            ExtendedPKIXParameters extendedPKIXParameters = (ExtendedPKIXParameters)cloneable;
            this.validityModel = extendedPKIXParameters.validityModel;
            this.useDeltas = extendedPKIXParameters.useDeltas;
            this.additionalLocationsEnabled = extendedPKIXParameters.additionalLocationsEnabled;
            cloneable = extendedPKIXParameters.selector;
            cloneable = cloneable == null ? null : (Selector)cloneable.clone();
            this.selector = cloneable;
            this.stores = new ArrayList(extendedPKIXParameters.stores);
            this.additionalStores = new ArrayList(extendedPKIXParameters.additionalStores);
            this.trustedACIssuers = new HashSet(extendedPKIXParameters.trustedACIssuers);
            this.prohibitedACAttributes = new HashSet(extendedPKIXParameters.prohibitedACAttributes);
            this.necessaryACAttributes = new HashSet(extendedPKIXParameters.necessaryACAttributes);
            this.attrCertCheckers = new HashSet(extendedPKIXParameters.attrCertCheckers);
        }
    }

    public void setProhibitedACAttributes(Set set) {
        if (set == null) {
            this.prohibitedACAttributes.clear();
            return;
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof String) continue;
            throw new ClassCastException("All elements of set must be of type String.");
        }
        this.prohibitedACAttributes.clear();
        this.prohibitedACAttributes.addAll(set);
    }

    public void setStores(List list) {
        if (list == null) {
            this.stores = new ArrayList();
        } else {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() instanceof Store) continue;
                throw new ClassCastException("All elements of list must be of type com.android.org.bouncycastle.util.Store.");
            }
            this.stores = new ArrayList(list);
        }
    }

    @Override
    public void setTargetCertConstraints(CertSelector certSelector) {
        super.setTargetCertConstraints(certSelector);
        this.selector = certSelector != null ? X509CertStoreSelector.getInstance((X509CertSelector)certSelector) : null;
    }

    public void setTargetConstraints(Selector selector) {
        this.selector = selector != null ? (Selector)selector.clone() : null;
    }

    public void setTrustedACIssuers(Set object) {
        if (object == null) {
            this.trustedACIssuers.clear();
            return;
        }
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof TrustAnchor) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("All elements of set must be of type ");
            ((StringBuilder)object).append(TrustAnchor.class.getName());
            ((StringBuilder)object).append(".");
            throw new ClassCastException(((StringBuilder)object).toString());
        }
        this.trustedACIssuers.clear();
        this.trustedACIssuers.addAll(object);
    }

    public void setUseDeltasEnabled(boolean bl) {
        this.useDeltas = bl;
    }

    public void setValidityModel(int n) {
        this.validityModel = n;
    }
}

