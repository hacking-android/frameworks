/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.AttCertIssuer;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.IssuerSerial;
import com.android.org.bouncycastle.asn1.x509.V2Form;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.jce.X509Principal;
import com.android.org.bouncycastle.util.Selector;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.security.auth.x500.X500Principal;

public class AttributeCertificateIssuer
implements CertSelector,
Selector {
    final ASN1Encodable form;

    public AttributeCertificateIssuer(AttCertIssuer attCertIssuer) {
        this.form = attCertIssuer.getIssuer();
    }

    public AttributeCertificateIssuer(X509Principal x509Principal) {
        this.form = new V2Form(GeneralNames.getInstance(new DERSequence(new GeneralName(x509Principal))));
    }

    public AttributeCertificateIssuer(X500Principal x500Principal) throws IOException {
        this(new X509Principal(x500Principal.getEncoded()));
    }

    private Object[] getNames() {
        Object object = this.form;
        object = object instanceof V2Form ? ((V2Form)object).getIssuerName() : (GeneralNames)object;
        object = object.getNames();
        ArrayList<X500Principal> arrayList = new ArrayList<X500Principal>(((GeneralName[])object).length);
        for (int i = 0; i != ((GeneralName[])object).length; ++i) {
            if (object[i].getTagNo() != 4) continue;
            try {
                X500Principal x500Principal = new X500Principal(object[i].getName().toASN1Primitive().getEncoded());
                arrayList.add(x500Principal);
                continue;
            }
            catch (IOException iOException) {
                throw new RuntimeException("badly formed Name object");
            }
        }
        return arrayList.toArray(new Object[arrayList.size()]);
    }

    private boolean matchesDN(X500Principal x500Principal, GeneralNames arrgeneralName) {
        arrgeneralName = arrgeneralName.getNames();
        for (int i = 0; i != arrgeneralName.length; ++i) {
            GeneralName generalName = arrgeneralName[i];
            if (generalName.getTagNo() != 4) continue;
            try {
                X500Principal x500Principal2 = new X500Principal(generalName.getName().toASN1Primitive().getEncoded());
                boolean bl = x500Principal2.equals(x500Principal);
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
        return new AttributeCertificateIssuer(AttCertIssuer.getInstance(this.form));
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AttributeCertificateIssuer)) {
            return false;
        }
        object = (AttributeCertificateIssuer)object;
        return this.form.equals(((AttributeCertificateIssuer)object).form);
    }

    public Principal[] getPrincipals() {
        Object[] arrobject = this.getNames();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (int i = 0; i != arrobject.length; ++i) {
            if (!(arrobject[i] instanceof Principal)) continue;
            arrayList.add(arrobject[i]);
        }
        return arrayList.toArray(new Principal[arrayList.size()]);
    }

    public int hashCode() {
        return this.form.hashCode();
    }

    public boolean match(Object object) {
        if (!(object instanceof X509Certificate)) {
            return false;
        }
        return this.match((Certificate)object);
    }

    @Override
    public boolean match(Certificate certificate) {
        boolean bl = certificate instanceof X509Certificate;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        certificate = (X509Certificate)certificate;
        ASN1Encodable aSN1Encodable = this.form;
        if (aSN1Encodable instanceof V2Form) {
            if (((V2Form)(aSN1Encodable = (V2Form)aSN1Encodable)).getBaseCertificateID() != null) {
                if (((V2Form)aSN1Encodable).getBaseCertificateID().getSerial().getValue().equals(((X509Certificate)certificate).getSerialNumber()) && this.matchesDN(((X509Certificate)certificate).getIssuerX500Principal(), ((V2Form)aSN1Encodable).getBaseCertificateID().getIssuer())) {
                    bl2 = true;
                }
                return bl2;
            }
            aSN1Encodable = ((V2Form)aSN1Encodable).getIssuerName();
            if (this.matchesDN(((X509Certificate)certificate).getSubjectX500Principal(), (GeneralNames)aSN1Encodable)) {
                return true;
            }
        } else {
            aSN1Encodable = (GeneralNames)aSN1Encodable;
            if (this.matchesDN(((X509Certificate)certificate).getSubjectX500Principal(), (GeneralNames)aSN1Encodable)) {
                return true;
            }
        }
        return false;
    }
}

