/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.jcajce.PKIXCertStore;
import com.android.org.bouncycastle.jcajce.PKIXCertStoreSelector;
import com.android.org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import com.android.org.bouncycastle.jcajce.PKIXExtendedParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import com.android.org.bouncycastle.jce.exception.ExtCertPathBuilderException;
import com.android.org.bouncycastle.jce.provider.AnnotatedException;
import com.android.org.bouncycastle.jce.provider.CertPathValidatorUtilities;
import com.android.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi;
import com.android.org.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import com.android.org.bouncycastle.x509.ExtendedPKIXParameters;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertStore;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PKIXCertPathBuilderSpi
extends CertPathBuilderSpi {
    private Exception certPathException;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected CertPathBuilderResult build(X509Certificate x509Certificate, PKIXExtendedBuilderParameters object, List list) {
        Object object2;
        block20 : {
            if (list.contains(x509Certificate)) {
                return null;
            }
            if (((PKIXExtendedBuilderParameters)object).getExcludedCerts().contains(x509Certificate)) {
                return null;
            }
            if (((PKIXExtendedBuilderParameters)object).getMaxPathLength() != -1 && list.size() - 1 > ((PKIXExtendedBuilderParameters)object).getMaxPathLength()) {
                return null;
            }
            list.add(x509Certificate);
            object2 = null;
            Serializable serializable = null;
            ArrayList<PKIXCertStore> arrayList = new ArrayList<PKIXCertStore>();
            Object object3 = new PKIXCertPathValidatorSpi();
            Serializable serializable2 = object2;
            try {
                boolean bl = CertPathValidatorUtilities.isIssuerTrustAnchor(x509Certificate, ((PKIXExtendedBuilderParameters)object).getBaseParameters().getTrustAnchors(), ((PKIXExtendedBuilderParameters)object).getBaseParameters().getSigProvider());
                if (bl) {
                    serializable2 = object2;
                    try {
                        serializable = ((CertificateFactory)((Object)arrayList)).engineGenerateCertPath(list);
                        serializable2 = object2;
                    }
                    catch (Exception exception) {
                        serializable2 = object2;
                        serializable2 = object2;
                        object = new AnnotatedException("Certification path could not be constructed from certificate list.", exception);
                        serializable2 = object2;
                        throw object;
                    }
                    try {
                        object = (PKIXCertPathValidatorResult)((PKIXCertPathValidatorSpi)object3).engineValidate((CertPath)serializable, (CertPathParameters)object);
                        serializable2 = object2;
                        return new PKIXCertPathBuilderResult((CertPath)serializable, ((PKIXCertPathValidatorResult)object).getTrustAnchor(), ((PKIXCertPathValidatorResult)object).getPolicyTree(), ((PKIXCertPathValidatorResult)object).getPublicKey());
                    }
                    catch (Exception exception) {
                        serializable2 = object2;
                        serializable2 = object2;
                        object = new AnnotatedException("Certification path could not be validated.", exception);
                        serializable2 = object2;
                        throw object;
                    }
                }
                serializable2 = object2;
                serializable2 = object2;
                arrayList = new ArrayList<PKIXCertStore>();
                serializable2 = object2;
                arrayList.addAll(((PKIXExtendedBuilderParameters)object).getBaseParameters().getCertificateStores());
                serializable2 = object2;
                arrayList.addAll(CertPathValidatorUtilities.getAdditionalStoresFromAltNames(x509Certificate.getExtensionValue(Extension.issuerAlternativeName.getId()), ((PKIXExtendedBuilderParameters)object).getBaseParameters().getNamedCertificateStoreMap()));
                serializable2 = object2;
                serializable2 = object2;
                object3 = new HashSet();
                object3.addAll(CertPathValidatorUtilities.findIssuerCerts(x509Certificate, ((PKIXExtendedBuilderParameters)object).getBaseParameters().getCertStores(), arrayList));
                serializable2 = object2;
                if (object3.isEmpty()) {
                    serializable2 = object2;
                    serializable2 = object2;
                    object = new AnnotatedException("No issuer certificate for certificate in certification path found.");
                    serializable2 = object2;
                    throw object;
                }
                serializable2 = object2;
                object3 = object3.iterator();
                object2 = serializable;
                do {
                    serializable2 = object2;
                    if (object3.hasNext() && object2 == null) {
                        serializable2 = object2;
                        object2 = this.build((X509Certificate)object3.next(), (PKIXExtendedBuilderParameters)object, list);
                        continue;
                    }
                    break block20;
                    break;
                } while (true);
                catch (AnnotatedException annotatedException) {
                    serializable2 = object2;
                    serializable2 = object2;
                    serializable = new AnnotatedException("Cannot find issuer certificate for certificate in certification path.", annotatedException);
                    serializable2 = object2;
                    throw serializable;
                }
                catch (CertificateParsingException certificateParsingException) {
                    serializable2 = object2;
                    serializable2 = object2;
                    object = new AnnotatedException("No additional X.509 stores can be added from certificate locations.", certificateParsingException);
                    serializable2 = object2;
                    throw object;
                }
            }
            catch (AnnotatedException annotatedException) {
                this.certPathException = annotatedException;
                object2 = serializable2;
            }
        }
        if (object2 != null) return object2;
        list.remove(x509Certificate);
        return object2;
        catch (Exception exception) {
            throw new RuntimeException("Exception creating support classes.");
        }
    }

    @Override
    public CertPathBuilderResult engineBuild(CertPathParameters object) throws CertPathBuilderException, InvalidAlgorithmParameterException {
        block13 : {
            block10 : {
                Cloneable cloneable;
                Object object2;
                Object object3;
                block12 : {
                    block11 : {
                        if (!(object instanceof PKIXBuilderParameters)) break block11;
                        object3 = new PKIXExtendedParameters.Builder((PKIXBuilderParameters)object);
                        if (object instanceof ExtendedPKIXParameters) {
                            cloneable = (ExtendedPKIXBuilderParameters)object;
                            object = ((ExtendedPKIXParameters)cloneable).getAdditionalStores().iterator();
                            while (object.hasNext()) {
                                ((PKIXExtendedParameters.Builder)object3).addCertificateStore((PKIXCertStore)object.next());
                            }
                            object = new PKIXExtendedBuilderParameters.Builder(((PKIXExtendedParameters.Builder)object3).build());
                            ((PKIXExtendedBuilderParameters.Builder)object).addExcludedCerts(((ExtendedPKIXBuilderParameters)cloneable).getExcludedCerts());
                            ((PKIXExtendedBuilderParameters.Builder)object).setMaxPathLength(((ExtendedPKIXBuilderParameters)cloneable).getMaxPathLength());
                        } else {
                            object = new PKIXExtendedBuilderParameters.Builder((PKIXBuilderParameters)object);
                        }
                        object = ((PKIXExtendedBuilderParameters.Builder)object).build();
                        break block12;
                    }
                    if (!(object instanceof PKIXExtendedBuilderParameters)) break block13;
                    object = (PKIXExtendedBuilderParameters)object;
                }
                object3 = new ArrayList();
                cloneable = ((PKIXExtendedBuilderParameters)object).getBaseParameters().getTargetConstraints();
                try {
                    object2 = CertPathValidatorUtilities.findCertificates((PKIXCertStoreSelector)cloneable, ((PKIXExtendedBuilderParameters)object).getBaseParameters().getCertificateStores());
                    object2.addAll(CertPathValidatorUtilities.findCertificates((PKIXCertStoreSelector)cloneable, ((PKIXExtendedBuilderParameters)object).getBaseParameters().getCertStores()));
                    if (object2.isEmpty()) break block10;
                    cloneable = null;
                    object2 = object2.iterator();
                }
                catch (AnnotatedException annotatedException) {
                    throw new ExtCertPathBuilderException("Error finding target certificate.", annotatedException);
                }
                while (object2.hasNext() && cloneable == null) {
                    cloneable = this.build((X509Certificate)object2.next(), (PKIXExtendedBuilderParameters)object, (List)object3);
                }
                if (cloneable == null && (object = this.certPathException) != null) {
                    if (object instanceof AnnotatedException) {
                        throw new CertPathBuilderException(((Throwable)object).getMessage(), this.certPathException.getCause());
                    }
                    throw new CertPathBuilderException("Possible certificate chain could not be validated.", (Throwable)object);
                }
                if (cloneable == null && this.certPathException == null) {
                    throw new CertPathBuilderException("Unable to find certificate chain.");
                }
                return cloneable;
            }
            throw new CertPathBuilderException("No certificate found matching targetContraints.");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Parameters must be an instance of ");
        ((StringBuilder)object).append(PKIXBuilderParameters.class.getName());
        ((StringBuilder)object).append(" or ");
        ((StringBuilder)object).append(PKIXExtendedBuilderParameters.class.getName());
        ((StringBuilder)object).append(".");
        throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
    }
}

