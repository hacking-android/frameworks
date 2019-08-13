/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Enumerated;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.Holder;
import com.android.org.bouncycastle.asn1.x509.IssuerSerial;
import com.android.org.bouncycastle.asn1.x509.ObjectDigestInfo;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.jce.PrincipalUtil;
import com.android.org.bouncycastle.jce.X509Principal;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Selector;
import com.android.org.bouncycastle.x509.X509Util;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.security.auth.x500.X500Principal;

public class AttributeCertificateHolder
implements CertSelector,
Selector {
    final Holder holder;

    public AttributeCertificateHolder(int n, String string, String string2, byte[] arrby) {
        this.holder = new Holder(new ObjectDigestInfo(n, new ASN1ObjectIdentifier(string2), new AlgorithmIdentifier(new ASN1ObjectIdentifier(string)), Arrays.clone(arrby)));
    }

    AttributeCertificateHolder(ASN1Sequence aSN1Sequence) {
        this.holder = Holder.getInstance(aSN1Sequence);
    }

    public AttributeCertificateHolder(X509Principal x509Principal) {
        this.holder = new Holder(this.generateGeneralNames(x509Principal));
    }

    public AttributeCertificateHolder(X509Principal x509Principal, BigInteger bigInteger) {
        this.holder = new Holder(new IssuerSerial(GeneralNames.getInstance(new DERSequence(new GeneralName(x509Principal))), new ASN1Integer(bigInteger)));
    }

    public AttributeCertificateHolder(X509Certificate x509Certificate) throws CertificateParsingException {
        try {
            X509Principal x509Principal = PrincipalUtil.getIssuerX509Principal(x509Certificate);
            this.holder = new Holder(new IssuerSerial(this.generateGeneralNames(x509Principal), new ASN1Integer(x509Certificate.getSerialNumber())));
            return;
        }
        catch (Exception exception) {
            throw new CertificateParsingException(exception.getMessage());
        }
    }

    public AttributeCertificateHolder(X500Principal x500Principal) {
        this(X509Util.convertPrincipal(x500Principal));
    }

    public AttributeCertificateHolder(X500Principal x500Principal, BigInteger bigInteger) {
        this(X509Util.convertPrincipal(x500Principal), bigInteger);
    }

    private GeneralNames generateGeneralNames(X509Principal x509Principal) {
        return GeneralNames.getInstance(new DERSequence(new GeneralName(x509Principal)));
    }

    private Object[] getNames(GeneralName[] arrgeneralName) {
        ArrayList<X500Principal> arrayList = new ArrayList<X500Principal>(arrgeneralName.length);
        for (int i = 0; i != arrgeneralName.length; ++i) {
            if (arrgeneralName[i].getTagNo() != 4) continue;
            try {
                X500Principal x500Principal = new X500Principal(arrgeneralName[i].getName().toASN1Primitive().getEncoded());
                arrayList.add(x500Principal);
                continue;
            }
            catch (IOException iOException) {
                throw new RuntimeException("badly formed Name object");
            }
        }
        return arrayList.toArray(new Object[arrayList.size()]);
    }

    private Principal[] getPrincipals(GeneralNames object) {
        Object[] arrobject = this.getNames(((GeneralNames)object).getNames());
        object = new ArrayList();
        for (int i = 0; i != arrobject.length; ++i) {
            if (!(arrobject[i] instanceof Principal)) continue;
            object.add(arrobject[i]);
        }
        return object.toArray(new Principal[object.size()]);
    }

    private boolean matchesDN(X509Principal x509Principal, GeneralNames arrgeneralName) {
        arrgeneralName = arrgeneralName.getNames();
        for (int i = 0; i != arrgeneralName.length; ++i) {
            GeneralName generalName = arrgeneralName[i];
            if (generalName.getTagNo() != 4) continue;
            try {
                X509Principal x509Principal2 = new X509Principal(generalName.getName().toASN1Primitive().getEncoded());
                boolean bl = x509Principal2.equals(x509Principal);
                if (!bl) continue;
                return true;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return false;
    }

    @Override
    public Object clone() {
        return new AttributeCertificateHolder((ASN1Sequence)this.holder.toASN1Primitive());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AttributeCertificateHolder)) {
            return false;
        }
        object = (AttributeCertificateHolder)object;
        return this.holder.equals(((AttributeCertificateHolder)object).holder);
    }

    public String getDigestAlgorithm() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getDigestAlgorithm().getAlgorithm().getId();
        }
        return null;
    }

    public int getDigestedObjectType() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getDigestedObjectType().getValue().intValue();
        }
        return -1;
    }

    public Principal[] getEntityNames() {
        if (this.holder.getEntityName() != null) {
            return this.getPrincipals(this.holder.getEntityName());
        }
        return null;
    }

    public Principal[] getIssuer() {
        if (this.holder.getBaseCertificateID() != null) {
            return this.getPrincipals(this.holder.getBaseCertificateID().getIssuer());
        }
        return null;
    }

    public byte[] getObjectDigest() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getObjectDigest().getBytes();
        }
        return null;
    }

    public String getOtherObjectTypeID() {
        if (this.holder.getObjectDigestInfo() != null) {
            this.holder.getObjectDigestInfo().getOtherObjectTypeID().getId();
        }
        return null;
    }

    public BigInteger getSerialNumber() {
        if (this.holder.getBaseCertificateID() != null) {
            return this.holder.getBaseCertificateID().getSerial().getValue();
        }
        return null;
    }

    public int hashCode() {
        return this.holder.hashCode();
    }

    public boolean match(Object object) {
        if (!(object instanceof X509Certificate)) {
            return false;
        }
        return this.match((Certificate)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean match(Certificate certificate) {
        boolean bl = certificate instanceof X509Certificate;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        Object object = (X509Certificate)certificate;
        try {
            block9 : {
                if (this.holder.getBaseCertificateID() != null) {
                    if (!this.holder.getBaseCertificateID().getSerial().getValue().equals(((X509Certificate)object).getSerialNumber())) return bl2;
                    if (!this.matchesDN(PrincipalUtil.getIssuerX509Principal((X509Certificate)object), this.holder.getBaseCertificateID().getIssuer())) return bl2;
                    return true;
                }
                if (this.holder.getEntityName() != null && this.matchesDN(PrincipalUtil.getSubjectX509Principal((X509Certificate)object), this.holder.getEntityName())) {
                    return true;
                }
                object = this.holder.getObjectDigestInfo();
                if (object == null) return false;
                try {
                    object = MessageDigest.getInstance(this.getDigestAlgorithm(), "BC");
                    int n = this.getDigestedObjectType();
                    if (n != 0) {
                        if (n == 1) {
                            ((MessageDigest)object).update(certificate.getEncoded());
                        }
                        break block9;
                    }
                    ((MessageDigest)object).update(certificate.getPublicKey().getEncoded());
                }
                catch (Exception exception) {
                    return false;
                }
            }
            bl2 = Arrays.areEqual(((MessageDigest)object).digest(), this.getObjectDigest());
            if (bl2) return false;
            return false;
        }
        catch (CertificateEncodingException certificateEncodingException) {
            return false;
        }
    }
}

