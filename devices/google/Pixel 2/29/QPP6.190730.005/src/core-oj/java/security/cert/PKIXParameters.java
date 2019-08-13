/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertPathParameters;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PKIXParameters
implements CertPathParameters {
    private boolean anyPolicyInhibited = false;
    private List<PKIXCertPathChecker> certPathCheckers;
    private CertSelector certSelector;
    private List<CertStore> certStores;
    private Date date;
    private boolean explicitPolicyRequired = false;
    private boolean policyMappingInhibited = false;
    private boolean policyQualifiersRejected = true;
    private boolean revocationEnabled = true;
    private String sigProvider;
    private Set<String> unmodInitialPolicies;
    private Set<TrustAnchor> unmodTrustAnchors;

    public PKIXParameters(KeyStore keyStore) throws KeyStoreException, InvalidAlgorithmParameterException {
        if (keyStore != null) {
            HashSet<TrustAnchor> hashSet = new HashSet<TrustAnchor>();
            Enumeration<String> enumeration = keyStore.aliases();
            while (enumeration.hasMoreElements()) {
                Object object = enumeration.nextElement();
                if (!keyStore.isCertificateEntry((String)object) || !((object = keyStore.getCertificate((String)object)) instanceof X509Certificate)) continue;
                hashSet.add(new TrustAnchor((X509Certificate)object, null));
            }
            this.setTrustAnchors(hashSet);
            this.unmodInitialPolicies = Collections.emptySet();
            this.certPathCheckers = new ArrayList<PKIXCertPathChecker>();
            this.certStores = new ArrayList<CertStore>();
            return;
        }
        throw new NullPointerException("the keystore parameter must be non-null");
    }

    public PKIXParameters(Set<TrustAnchor> set) throws InvalidAlgorithmParameterException {
        this.setTrustAnchors(set);
        this.unmodInitialPolicies = Collections.emptySet();
        this.certPathCheckers = new ArrayList<PKIXCertPathChecker>();
        this.certStores = new ArrayList<CertStore>();
    }

    public void addCertPathChecker(PKIXCertPathChecker pKIXCertPathChecker) {
        if (pKIXCertPathChecker != null) {
            this.certPathCheckers.add((PKIXCertPathChecker)pKIXCertPathChecker.clone());
        }
    }

    public void addCertStore(CertStore certStore) {
        if (certStore != null) {
            this.certStores.add(certStore);
        }
    }

    @Override
    public Object clone() {
        try {
            Object object;
            PKIXParameters pKIXParameters = (PKIXParameters)super.clone();
            if (this.certStores != null) {
                object = new ArrayList(this.certStores);
                pKIXParameters.certStores = object;
            }
            if (this.certPathCheckers != null) {
                object = new ArrayList(this.certPathCheckers.size());
                pKIXParameters.certPathCheckers = object;
                for (PKIXCertPathChecker pKIXCertPathChecker : this.certPathCheckers) {
                    pKIXParameters.certPathCheckers.add((PKIXCertPathChecker)pKIXCertPathChecker.clone());
                }
            }
            return pKIXParameters;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
        }
    }

    public List<PKIXCertPathChecker> getCertPathCheckers() {
        ArrayList<PKIXCertPathChecker> arrayList = new ArrayList<PKIXCertPathChecker>();
        Iterator<PKIXCertPathChecker> iterator = this.certPathCheckers.iterator();
        while (iterator.hasNext()) {
            arrayList.add((PKIXCertPathChecker)iterator.next().clone());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public List<CertStore> getCertStores() {
        return Collections.unmodifiableList(new ArrayList<CertStore>(this.certStores));
    }

    public Date getDate() {
        Date date = this.date;
        if (date == null) {
            return null;
        }
        return (Date)date.clone();
    }

    public Set<String> getInitialPolicies() {
        return this.unmodInitialPolicies;
    }

    public boolean getPolicyQualifiersRejected() {
        return this.policyQualifiersRejected;
    }

    public String getSigProvider() {
        return this.sigProvider;
    }

    public CertSelector getTargetCertConstraints() {
        CertSelector certSelector = this.certSelector;
        if (certSelector != null) {
            return (CertSelector)certSelector.clone();
        }
        return null;
    }

    public Set<TrustAnchor> getTrustAnchors() {
        return this.unmodTrustAnchors;
    }

    public boolean isAnyPolicyInhibited() {
        return this.anyPolicyInhibited;
    }

    public boolean isExplicitPolicyRequired() {
        return this.explicitPolicyRequired;
    }

    public boolean isPolicyMappingInhibited() {
        return this.policyMappingInhibited;
    }

    public boolean isRevocationEnabled() {
        return this.revocationEnabled;
    }

    public void setAnyPolicyInhibited(boolean bl) {
        this.anyPolicyInhibited = bl;
    }

    public void setCertPathCheckers(List<PKIXCertPathChecker> object) {
        if (object != null) {
            ArrayList<PKIXCertPathChecker> arrayList = new ArrayList<PKIXCertPathChecker>();
            object = object.iterator();
            while (object.hasNext()) {
                arrayList.add((PKIXCertPathChecker)((PKIXCertPathChecker)object.next()).clone());
            }
            this.certPathCheckers = arrayList;
        } else {
            this.certPathCheckers = new ArrayList<PKIXCertPathChecker>();
        }
    }

    public void setCertStores(List<CertStore> list) {
        if (list == null) {
            this.certStores = new ArrayList<CertStore>();
        } else {
            Iterator<CertStore> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() instanceof CertStore) continue;
                throw new ClassCastException("all elements of list must be of type java.security.cert.CertStore");
            }
            this.certStores = new ArrayList<CertStore>(list);
        }
    }

    public void setDate(Date date) {
        block0 : {
            if (date == null) break block0;
            this.date = (Date)date.clone();
        }
    }

    public void setExplicitPolicyRequired(boolean bl) {
        this.explicitPolicyRequired = bl;
    }

    public void setInitialPolicies(Set<String> set) {
        if (set != null) {
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() instanceof String) continue;
                throw new ClassCastException("all elements of set must be of type java.lang.String");
            }
            this.unmodInitialPolicies = Collections.unmodifiableSet(new HashSet<String>(set));
        } else {
            this.unmodInitialPolicies = Collections.emptySet();
        }
    }

    public void setPolicyMappingInhibited(boolean bl) {
        this.policyMappingInhibited = bl;
    }

    public void setPolicyQualifiersRejected(boolean bl) {
        this.policyQualifiersRejected = bl;
    }

    public void setRevocationEnabled(boolean bl) {
        this.revocationEnabled = bl;
    }

    public void setSigProvider(String string) {
        this.sigProvider = string;
    }

    public void setTargetCertConstraints(CertSelector certSelector) {
        this.certSelector = certSelector != null ? (CertSelector)certSelector.clone() : null;
    }

    public void setTrustAnchors(Set<TrustAnchor> set) throws InvalidAlgorithmParameterException {
        if (set != null) {
            if (!set.isEmpty()) {
                Iterator<TrustAnchor> iterator = set.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() instanceof TrustAnchor) continue;
                    throw new ClassCastException("all elements of set must be of type java.security.cert.TrustAnchor");
                }
                this.unmodTrustAnchors = Collections.unmodifiableSet(new HashSet<TrustAnchor>(set));
                return;
            }
            throw new InvalidAlgorithmParameterException("the trustAnchors parameter must be non-empty");
        }
        throw new NullPointerException("the trustAnchors parameters must be non-null");
    }

    public String toString() {
        Object object;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[\n");
        if (this.unmodTrustAnchors != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  Trust Anchors: ");
            ((StringBuilder)object).append(this.unmodTrustAnchors.toString());
            ((StringBuilder)object).append("\n");
            stringBuffer.append(((StringBuilder)object).toString());
        }
        if ((object = this.unmodInitialPolicies) != null) {
            if (object.isEmpty()) {
                stringBuffer.append("  Initial Policy OIDs: any\n");
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("  Initial Policy OIDs: [");
                ((StringBuilder)object).append(this.unmodInitialPolicies.toString());
                ((StringBuilder)object).append("]\n");
                stringBuffer.append(((StringBuilder)object).toString());
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("  Validity Date: ");
        ((StringBuilder)object).append(String.valueOf(this.date));
        ((StringBuilder)object).append("\n");
        stringBuffer.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  Signature Provider: ");
        ((StringBuilder)object).append(String.valueOf(this.sigProvider));
        ((StringBuilder)object).append("\n");
        stringBuffer.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  Default Revocation Enabled: ");
        ((StringBuilder)object).append(this.revocationEnabled);
        ((StringBuilder)object).append("\n");
        stringBuffer.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  Explicit Policy Required: ");
        ((StringBuilder)object).append(this.explicitPolicyRequired);
        ((StringBuilder)object).append("\n");
        stringBuffer.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  Policy Mapping Inhibited: ");
        ((StringBuilder)object).append(this.policyMappingInhibited);
        ((StringBuilder)object).append("\n");
        stringBuffer.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  Any Policy Inhibited: ");
        ((StringBuilder)object).append(this.anyPolicyInhibited);
        ((StringBuilder)object).append("\n");
        stringBuffer.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  Policy Qualifiers Rejected: ");
        ((StringBuilder)object).append(this.policyQualifiersRejected);
        ((StringBuilder)object).append("\n");
        stringBuffer.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("  Target Cert Constraints: ");
        ((StringBuilder)object).append(String.valueOf(this.certSelector));
        ((StringBuilder)object).append("\n");
        stringBuffer.append(((StringBuilder)object).toString());
        if (this.certPathCheckers != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  Certification Path Checkers: [");
            ((StringBuilder)object).append(this.certPathCheckers.toString());
            ((StringBuilder)object).append("]\n");
            stringBuffer.append(((StringBuilder)object).toString());
        }
        if (this.certStores != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  CertStores: [");
            ((StringBuilder)object).append(this.certStores.toString());
            ((StringBuilder)object).append("]\n");
            stringBuffer.append(((StringBuilder)object).toString());
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

