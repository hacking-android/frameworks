/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLX509Certificate;
import com.android.org.conscrypt.ct.CTLogInfo;
import com.android.org.conscrypt.ct.CTLogStore;
import com.android.org.conscrypt.ct.CTVerificationResult;
import com.android.org.conscrypt.ct.CertificateEntry;
import com.android.org.conscrypt.ct.Serialization;
import com.android.org.conscrypt.ct.SerializationException;
import com.android.org.conscrypt.ct.SignedCertificateTimestamp;
import com.android.org.conscrypt.ct.VerifiedSCT;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CTVerifier {
    private final CTLogStore store;

    public CTVerifier(CTLogStore cTLogStore) {
        this.store = cTLogStore;
    }

    private List<SignedCertificateTimestamp> getSCTsFromOCSPResponse(byte[] object, OpenSSLX509Certificate[] arropenSSLX509Certificate) {
        if (object != null && arropenSSLX509Certificate.length >= 2) {
            if ((object = NativeCrypto.get_ocsp_single_extension(object, "1.3.6.1.4.1.11129.2.4.5", arropenSSLX509Certificate[0].getContext(), arropenSSLX509Certificate[0], arropenSSLX509Certificate[1].getContext(), arropenSSLX509Certificate[1])) == null) {
                return Collections.emptyList();
            }
            try {
                object = this.getSCTsFromSCTList(Serialization.readDEROctetString(Serialization.readDEROctetString(object)), SignedCertificateTimestamp.Origin.OCSP_RESPONSE);
                return object;
            }
            catch (SerializationException serializationException) {
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }

    private List<SignedCertificateTimestamp> getSCTsFromSCTList(byte[] arrby, SignedCertificateTimestamp.Origin origin) {
        ArrayList<SignedCertificateTimestamp> arrayList;
        if (arrby == null) {
            return Collections.emptyList();
        }
        try {
            arrby = Serialization.readList(arrby, 2, 2);
            arrayList = new ArrayList<SignedCertificateTimestamp>();
        }
        catch (SerializationException serializationException) {
            return Collections.emptyList();
        }
        for (byte by : arrby) {
            try {
                arrayList.add(SignedCertificateTimestamp.decode((byte[])by, origin));
            }
            catch (SerializationException serializationException) {
                // empty catch block
            }
        }
        return arrayList;
    }

    private List<SignedCertificateTimestamp> getSCTsFromTLSExtension(byte[] arrby) {
        return this.getSCTsFromSCTList(arrby, SignedCertificateTimestamp.Origin.TLS_EXTENSION);
    }

    private List<SignedCertificateTimestamp> getSCTsFromX509Extension(OpenSSLX509Certificate object) {
        if ((object = object.getExtensionValue("1.3.6.1.4.1.11129.2.4.2")) == null) {
            return Collections.emptyList();
        }
        try {
            object = this.getSCTsFromSCTList(Serialization.readDEROctetString(Serialization.readDEROctetString(object)), SignedCertificateTimestamp.Origin.EMBEDDED);
            return object;
        }
        catch (SerializationException serializationException) {
            return Collections.emptyList();
        }
    }

    private void markSCTsAsInvalid(List<SignedCertificateTimestamp> object, CTVerificationResult cTVerificationResult) {
        object = object.iterator();
        while (object.hasNext()) {
            cTVerificationResult.add(new VerifiedSCT((SignedCertificateTimestamp)object.next(), VerifiedSCT.Status.INVALID_SCT));
        }
    }

    private void verifyEmbeddedSCTs(List<SignedCertificateTimestamp> object, OpenSSLX509Certificate[] object2, CTVerificationResult cTVerificationResult) {
        if (object.isEmpty()) {
            return;
        }
        OpenSSLX509Certificate openSSLX509Certificate = null;
        Object object3 = openSSLX509Certificate;
        if (((OpenSSLX509Certificate[])object2).length >= 2) {
            object3 = object2[0];
            object2 = object2[1];
            try {
                object3 = CertificateEntry.createForPrecertificate((OpenSSLX509Certificate)object3, (OpenSSLX509Certificate)object2);
            }
            catch (CertificateException certificateException) {
                object3 = openSSLX509Certificate;
            }
        }
        if (object3 == null) {
            this.markSCTsAsInvalid((List<SignedCertificateTimestamp>)object, cTVerificationResult);
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            object2 = (SignedCertificateTimestamp)object.next();
            cTVerificationResult.add(new VerifiedSCT((SignedCertificateTimestamp)object2, this.verifySingleSCT((SignedCertificateTimestamp)object2, (CertificateEntry)object3)));
        }
    }

    private void verifyExternalSCTs(List<SignedCertificateTimestamp> object, OpenSSLX509Certificate object2, CTVerificationResult cTVerificationResult) {
        if (object.isEmpty()) {
            return;
        }
        try {
            object2 = CertificateEntry.createForX509Certificate((X509Certificate)object2);
            object = object.iterator();
        }
        catch (CertificateException certificateException) {
            this.markSCTsAsInvalid((List<SignedCertificateTimestamp>)object, cTVerificationResult);
            return;
        }
        while (object.hasNext()) {
            SignedCertificateTimestamp signedCertificateTimestamp = (SignedCertificateTimestamp)object.next();
            cTVerificationResult.add(new VerifiedSCT(signedCertificateTimestamp, this.verifySingleSCT(signedCertificateTimestamp, (CertificateEntry)object2)));
        }
        return;
    }

    private VerifiedSCT.Status verifySingleSCT(SignedCertificateTimestamp signedCertificateTimestamp, CertificateEntry certificateEntry) {
        CTLogInfo cTLogInfo = this.store.getKnownLog(signedCertificateTimestamp.getLogID());
        if (cTLogInfo == null) {
            return VerifiedSCT.Status.UNKNOWN_LOG;
        }
        return cTLogInfo.verifySingleSCT(signedCertificateTimestamp, certificateEntry);
    }

    public CTVerificationResult verifySignedCertificateTimestamps(List<X509Certificate> object, byte[] arrby, byte[] arrby2) throws CertificateEncodingException {
        OpenSSLX509Certificate[] arropenSSLX509Certificate = new OpenSSLX509Certificate[object.size()];
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arropenSSLX509Certificate[n] = OpenSSLX509Certificate.fromCertificate((X509Certificate)object.next());
            ++n;
        }
        return this.verifySignedCertificateTimestamps(arropenSSLX509Certificate, arrby, arrby2);
    }

    public CTVerificationResult verifySignedCertificateTimestamps(OpenSSLX509Certificate[] arropenSSLX509Certificate, byte[] arrby, byte[] arrby2) throws CertificateEncodingException {
        if (arropenSSLX509Certificate.length != 0) {
            OpenSSLX509Certificate openSSLX509Certificate = arropenSSLX509Certificate[0];
            CTVerificationResult cTVerificationResult = new CTVerificationResult();
            this.verifyExternalSCTs(this.getSCTsFromTLSExtension(arrby), openSSLX509Certificate, cTVerificationResult);
            this.verifyExternalSCTs(this.getSCTsFromOCSPResponse(arrby2, arropenSSLX509Certificate), openSSLX509Certificate, cTVerificationResult);
            this.verifyEmbeddedSCTs(this.getSCTsFromX509Extension(arropenSSLX509Certificate[0]), arropenSSLX509Certificate, cTVerificationResult);
            return cTVerificationResult;
        }
        throw new IllegalArgumentException("Chain of certificates mustn't be empty.");
    }
}

