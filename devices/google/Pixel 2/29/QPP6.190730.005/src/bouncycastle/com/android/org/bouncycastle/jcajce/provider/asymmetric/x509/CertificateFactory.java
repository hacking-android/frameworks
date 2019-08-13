/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.SignedData;
import com.android.org.bouncycastle.asn1.x509.CertificateList;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.x509.PKIXCertPath;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLObject;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject;
import com.android.org.bouncycastle.jcajce.util.BCJcaJceHelper;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.util.io.Streams;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CertificateFactory
extends CertificateFactorySpi {
    private static final PEMUtil PEM_CERT_PARSER = new PEMUtil("CERTIFICATE");
    private static final PEMUtil PEM_CRL_PARSER = new PEMUtil("CRL");
    private static final PEMUtil PEM_PKCS7_PARSER = new PEMUtil("PKCS7");
    private final JcaJceHelper bcHelper = new BCJcaJceHelper();
    private InputStream currentCrlStream = null;
    private InputStream currentStream = null;
    private ASN1Set sCrlData = null;
    private int sCrlDataObjectCount = 0;
    private ASN1Set sData = null;
    private int sDataObjectCount = 0;

    private CRL getCRL() throws CRLException {
        ASN1Set aSN1Set = this.sCrlData;
        if (aSN1Set != null && this.sCrlDataObjectCount < aSN1Set.size()) {
            aSN1Set = this.sCrlData;
            int n = this.sCrlDataObjectCount;
            this.sCrlDataObjectCount = n + 1;
            return this.createCRL(CertificateList.getInstance(aSN1Set.getObjectAt(n)));
        }
        return null;
    }

    private CRL getCRL(ASN1Sequence aSN1Sequence) throws CRLException {
        if (aSN1Sequence == null) {
            return null;
        }
        if (aSN1Sequence.size() > 1 && aSN1Sequence.getObjectAt(0) instanceof ASN1ObjectIdentifier && aSN1Sequence.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)) {
            this.sCrlData = SignedData.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1Sequence.getObjectAt(1), true)).getCRLs();
            return this.getCRL();
        }
        return this.createCRL(CertificateList.getInstance(aSN1Sequence));
    }

    private Certificate getCertificate() throws CertificateParsingException {
        if (this.sData != null) {
            while (this.sDataObjectCount < this.sData.size()) {
                ASN1Encodable aSN1Encodable = this.sData;
                int n = this.sDataObjectCount;
                this.sDataObjectCount = n + 1;
                if (!((aSN1Encodable = aSN1Encodable.getObjectAt(n)) instanceof ASN1Sequence)) continue;
                return new X509CertificateObject(this.bcHelper, com.android.org.bouncycastle.asn1.x509.Certificate.getInstance(aSN1Encodable));
            }
        }
        return null;
    }

    private Certificate getCertificate(ASN1Sequence aSN1Sequence) throws CertificateParsingException {
        if (aSN1Sequence == null) {
            return null;
        }
        if (aSN1Sequence.size() > 1 && aSN1Sequence.getObjectAt(0) instanceof ASN1ObjectIdentifier && aSN1Sequence.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)) {
            this.sData = SignedData.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1Sequence.getObjectAt(1), true)).getCertificates();
            return this.getCertificate();
        }
        return new X509CertificateObject(this.bcHelper, com.android.org.bouncycastle.asn1.x509.Certificate.getInstance(aSN1Sequence));
    }

    private CRL readDERCRL(ASN1InputStream aSN1InputStream) throws IOException, CRLException {
        return this.getCRL(ASN1Sequence.getInstance(aSN1InputStream.readObject()));
    }

    private Certificate readDERCertificate(ASN1InputStream aSN1InputStream) throws IOException, CertificateParsingException {
        return this.getCertificate(ASN1Sequence.getInstance(aSN1InputStream.readObject()));
    }

    private CRL readPEMCRL(InputStream inputStream) throws IOException, CRLException {
        return this.getCRL(PEM_CRL_PARSER.readPEMObject(inputStream));
    }

    private Certificate readPEMCertificate(InputStream inputStream) throws IOException, CertificateParsingException {
        return this.getCertificate(PEM_CERT_PARSER.readPEMObject(inputStream));
    }

    protected CRL createCRL(CertificateList certificateList) throws CRLException {
        return new X509CRLObject(this.bcHelper, certificateList);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CRL engineGenerateCRL(InputStream object) throws CRLException {
        InputStream inputStream = this.currentCrlStream;
        if (inputStream == null) {
            this.currentCrlStream = object;
            this.sCrlData = null;
            this.sCrlDataObjectCount = 0;
        } else if (inputStream != object) {
            this.currentCrlStream = object;
            this.sCrlData = null;
            this.sCrlDataObjectCount = 0;
        }
        try {
            if (this.sCrlData != null) {
                if (this.sCrlDataObjectCount != this.sCrlData.size()) {
                    return this.getCRL();
                }
                this.sCrlData = null;
                this.sCrlDataObjectCount = 0;
                return null;
            }
            if (!((InputStream)object).markSupported()) {
                object = new ByteArrayInputStream(Streams.readAll((InputStream)object));
            }
            ((InputStream)object).mark(1);
            int n = ((InputStream)object).read();
            if (n == -1) {
                return null;
            }
            ((InputStream)object).reset();
            if (n != 48) {
                return this.readPEMCRL((InputStream)object);
            }
            inputStream = new ASN1InputStream((InputStream)object, true);
            return this.readDERCRL((ASN1InputStream)inputStream);
        }
        catch (Exception exception) {
            throw new CRLException(exception.toString());
        }
        catch (CRLException cRLException) {
            throw cRLException;
        }
    }

    public Collection engineGenerateCRLs(InputStream inputStream) throws CRLException {
        CRL cRL;
        ArrayList<CRL> arrayList = new ArrayList<CRL>();
        inputStream = new BufferedInputStream(inputStream);
        while ((cRL = this.engineGenerateCRL(inputStream)) != null) {
            arrayList.add(cRL);
        }
        return arrayList;
    }

    @Override
    public CertPath engineGenerateCertPath(InputStream inputStream) throws CertificateException {
        return this.engineGenerateCertPath(inputStream, "PkiPath");
    }

    @Override
    public CertPath engineGenerateCertPath(InputStream inputStream, String string) throws CertificateException {
        return new PKIXCertPath(inputStream, string);
    }

    public CertPath engineGenerateCertPath(List object) throws CertificateException {
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            Object e = iterator.next();
            if (e == null || e instanceof X509Certificate) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("list contains non X509Certificate object while creating CertPath\n");
            ((StringBuilder)object).append(e.toString());
            throw new CertificateException(((StringBuilder)object).toString());
        }
        return new PKIXCertPath((List)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Certificate engineGenerateCertificate(InputStream object) throws CertificateException {
        Object object2 = this.currentStream;
        if (object2 == null) {
            this.currentStream = object;
            this.sData = null;
            this.sDataObjectCount = 0;
        } else if (object2 != object) {
            this.currentStream = object;
            this.sData = null;
            this.sDataObjectCount = 0;
        }
        try {
            int n;
            if (this.sData != null) {
                if (this.sDataObjectCount != this.sData.size()) {
                    return this.getCertificate();
                }
                this.sData = null;
                this.sDataObjectCount = 0;
                return null;
            }
            object2 = ((InputStream)object).markSupported() ? object : new PushbackInputStream((InputStream)object);
            if (((InputStream)object).markSupported()) {
                ((InputStream)object2).mark(1);
            }
            if ((n = ((InputStream)object2).read()) == -1) {
                return null;
            }
            if (((InputStream)object).markSupported()) {
                ((InputStream)object2).reset();
            } else {
                ((PushbackInputStream)object2).unread(n);
            }
            if (n != 48) {
                return this.readPEMCertificate((InputStream)object2);
            }
            object = new ASN1InputStream((InputStream)object2);
            return this.readDERCertificate((ASN1InputStream)object);
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("parsing issue: ");
            ((StringBuilder)object).append(exception.getMessage());
            throw new ExCertificateException(((StringBuilder)object).toString(), exception);
        }
    }

    public Collection engineGenerateCertificates(InputStream inputStream) throws CertificateException {
        Certificate certificate;
        ArrayList<Certificate> arrayList = new ArrayList<Certificate>();
        while ((certificate = this.engineGenerateCertificate(inputStream)) != null) {
            arrayList.add(certificate);
        }
        return arrayList;
    }

    public Iterator engineGetCertPathEncodings() {
        return PKIXCertPath.certPathEncodings.iterator();
    }

    private class ExCertificateException
    extends CertificateException {
        private Throwable cause;

        public ExCertificateException(String string, Throwable throwable) {
            super(string);
            this.cause = throwable;
        }

        public ExCertificateException(Throwable throwable) {
            this.cause = throwable;
        }

        @Override
        public Throwable getCause() {
            return this.cause;
        }
    }

}

