/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERSet;
import com.android.org.bouncycastle.asn1.pkcs.ContentInfo;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.SignedData;
import com.android.org.bouncycastle.jcajce.util.BCJcaJceHelper;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.security.auth.x500.X500Principal;

public class PKIXCertPath
extends CertPath {
    static final List certPathEncodings;
    private List certificates;
    private final JcaJceHelper helper = new BCJcaJceHelper();

    static {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("PkiPath");
        arrayList.add("PKCS7");
        certPathEncodings = Collections.unmodifiableList(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    PKIXCertPath(InputStream object, String list) throws CertificateException {
        super("X.509");
        try {
            if (((String)((Object)list)).equalsIgnoreCase("PkiPath")) {
                list = new List<Certificate>((InputStream)object);
                object = ((ASN1InputStream)((Object)list)).readObject();
                if (!(object instanceof ASN1Sequence)) {
                    super("input stream does not contain a ASN1 SEQUENCE while reading PkiPath encoded data to load CertPath");
                    throw object;
                }
                object = ((ASN1Sequence)object).getObjects();
                list = new List<Certificate>();
                this.certificates = list;
                CertificateFactory certificateFactory = this.helper.createCertificateFactory("X.509");
                while (object.hasMoreElements()) {
                    byte[] arrby = ((ASN1Encodable)object.nextElement()).toASN1Primitive().getEncoded("DER");
                    list = this.certificates;
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrby);
                    list.add(0, certificateFactory.generateCertificate(byteArrayInputStream));
                }
            } else {
                if (!((String)((Object)list)).equalsIgnoreCase("PKCS7") && !((String)((Object)list)).equalsIgnoreCase("PEM")) {
                    super();
                    ((StringBuilder)object).append("unsupported encoding: ");
                    ((StringBuilder)object).append((String)((Object)list));
                    CertificateException certificateException = new CertificateException(((StringBuilder)object).toString());
                    throw certificateException;
                }
                list = new List<Certificate>((InputStream)object);
                this.certificates = object = new ArrayList();
                CertificateFactory certificateFactory = this.helper.createCertificateFactory("X.509");
                while ((object = certificateFactory.generateCertificate((InputStream)((Object)list))) != null) {
                    this.certificates.add(object);
                }
            }
            this.certificates = this.sortCerts(this.certificates);
            return;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            list = new StringBuilder();
            ((StringBuilder)((Object)list)).append("BouncyCastle provider not found while trying to get a CertificateFactory:\n");
            ((StringBuilder)((Object)list)).append(noSuchProviderException.toString());
            throw new CertificateException(((StringBuilder)((Object)list)).toString());
        }
        catch (IOException iOException) {
            list = new StringBuilder();
            ((StringBuilder)((Object)list)).append("IOException throw while decoding CertPath:\n");
            ((StringBuilder)((Object)list)).append(iOException.toString());
            throw new CertificateException(((StringBuilder)((Object)list)).toString());
        }
    }

    PKIXCertPath(List list) {
        super("X.509");
        this.certificates = this.sortCerts(new ArrayList(list));
    }

    private List sortCerts(List list) {
        Serializable serializable;
        int n;
        Serializable serializable2;
        int n2;
        Serializable serializable3;
        int n3;
        block10 : {
            if (list.size() < 2) {
                return list;
            }
            serializable3 = ((X509Certificate)list.get(0)).getIssuerX500Principal();
            n2 = 1;
            n = 1;
            do {
                n3 = n2;
                if (n == list.size()) break block10;
                if (!((X500Principal)serializable3).equals(((X509Certificate)list.get(n)).getSubjectX500Principal())) break;
                serializable3 = ((X509Certificate)list.get(n)).getIssuerX500Principal();
                ++n;
            } while (true);
            n3 = 0;
        }
        if (n3 != 0) {
            return list;
        }
        serializable3 = new ArrayList(list.size());
        ArrayList arrayList = new ArrayList(list);
        for (n = 0; n < list.size(); ++n) {
            serializable = (X509Certificate)list.get(n);
            int n4 = 0;
            serializable2 = ((X509Certificate)serializable).getSubjectX500Principal();
            n2 = 0;
            do {
                n3 = n4;
                if (n2 == list.size()) break;
                if (((X509Certificate)list.get(n2)).getIssuerX500Principal().equals(serializable2)) {
                    n3 = 1;
                    break;
                }
                ++n2;
            } while (true);
            if (n3 != 0) continue;
            serializable3.add(serializable);
            list.remove(n);
        }
        if (serializable3.size() > 1) {
            return arrayList;
        }
        block3 : for (n = 0; n != serializable3.size(); ++n) {
            serializable = ((X509Certificate)serializable3.get(n)).getIssuerX500Principal();
            for (n3 = 0; n3 < list.size(); ++n3) {
                serializable2 = (X509Certificate)list.get(n3);
                if (!((X500Principal)serializable).equals(((X509Certificate)serializable2).getSubjectX500Principal())) continue;
                serializable3.add(serializable2);
                list.remove(n3);
                continue block3;
            }
        }
        if (list.size() > 0) {
            return arrayList;
        }
        return serializable3;
    }

    private ASN1Primitive toASN1Object(X509Certificate object) throws CertificateEncodingException {
        try {
            ASN1InputStream aSN1InputStream = new ASN1InputStream(((Certificate)object).getEncoded());
            object = aSN1InputStream.readObject();
            return object;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception while encoding certificate: ");
            stringBuilder.append(exception.toString());
            throw new CertificateEncodingException(stringBuilder.toString());
        }
    }

    private byte[] toDEREncoded(ASN1Encodable object) throws CertificateEncodingException {
        try {
            object = object.toASN1Primitive().getEncoded("DER");
            return object;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Exception thrown: ");
            ((StringBuilder)object).append(iOException);
            throw new CertificateEncodingException(((StringBuilder)object).toString());
        }
    }

    public List getCertificates() {
        return Collections.unmodifiableList(new ArrayList(this.certificates));
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        Iterator iterator = this.getEncodings();
        if (iterator.hasNext() && (iterator = iterator.next()) instanceof String) {
            return this.getEncoded((String)((Object)iterator));
        }
        return null;
    }

    @Override
    public byte[] getEncoded(String object) throws CertificateEncodingException {
        if (((String)object).equalsIgnoreCase("PkiPath")) {
            object = new ASN1EncodableVector();
            Object object2 = this.certificates;
            object2 = object2.listIterator(object2.size());
            while (object2.hasPrevious()) {
                ((ASN1EncodableVector)object).add(this.toASN1Object((X509Certificate)object2.previous()));
            }
            return this.toDEREncoded(new DERSequence((ASN1EncodableVector)object));
        }
        if (((String)object).equalsIgnoreCase("PKCS7")) {
            object = new ContentInfo(PKCSObjectIdentifiers.data, null);
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            for (int i = 0; i != this.certificates.size(); ++i) {
                aSN1EncodableVector.add(this.toASN1Object((X509Certificate)this.certificates.get(i)));
            }
            object = new SignedData(new ASN1Integer(1L), new DERSet(), (ContentInfo)object, new DERSet(aSN1EncodableVector), null, new DERSet());
            return this.toDEREncoded(new ContentInfo(PKCSObjectIdentifiers.signedData, (ASN1Encodable)object));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unsupported encoding: ");
        stringBuilder.append((String)object);
        throw new CertificateEncodingException(stringBuilder.toString());
    }

    public Iterator getEncodings() {
        return certPathEncodings.iterator();
    }
}

