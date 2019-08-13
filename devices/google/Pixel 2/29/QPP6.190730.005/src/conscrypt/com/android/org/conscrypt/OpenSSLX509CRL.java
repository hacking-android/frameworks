/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLBIOInputStream;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLKeyHolder;
import com.android.org.conscrypt.OpenSSLX509CRLEntry;
import com.android.org.conscrypt.OpenSSLX509Certificate;
import com.android.org.conscrypt.OpenSSLX509CertificateFactory;
import com.android.org.conscrypt.Platform;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.security.auth.x500.X500Principal;

final class OpenSSLX509CRL
extends X509CRL {
    private final long mContext;
    private final Date nextUpdate;
    private final Date thisUpdate;

    private OpenSSLX509CRL(long l) throws OpenSSLX509CertificateFactory.ParsingException {
        this.mContext = l;
        this.thisUpdate = OpenSSLX509CRL.toDate(NativeCrypto.X509_CRL_get_lastUpdate(this.mContext, this));
        this.nextUpdate = OpenSSLX509CRL.toDate(NativeCrypto.X509_CRL_get_nextUpdate(this.mContext, this));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static List<OpenSSLX509CRL> fromPkcs7DerInputStream(InputStream object) throws OpenSSLX509CertificateFactory.ParsingException {
        Throwable throwable2222;
        object = new OpenSSLBIOInputStream((InputStream)((Object)object), true);
        long[] arrl = NativeCrypto.d2i_PKCS7_bio(((OpenSSLBIOInputStream)((Object)object)).getBioContext(), 2);
        ((OpenSSLBIOInputStream)((Object)object)).release();
        object = new ArrayList<OpenSSLX509CRL>(arrl.length);
        int n = 0;
        while (n < arrl.length) {
            if (arrl[n] != 0L) {
                object.add(new OpenSSLX509CRL(arrl[n]));
            }
            ++n;
        }
        return object;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                OpenSSLX509CertificateFactory.ParsingException parsingException = new OpenSSLX509CertificateFactory.ParsingException(exception);
                throw parsingException;
            }
        }
        ((OpenSSLBIOInputStream)((Object)object)).release();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static List<OpenSSLX509CRL> fromPkcs7PemInputStream(InputStream object) throws OpenSSLX509CertificateFactory.ParsingException {
        Throwable throwable2222;
        object = new OpenSSLBIOInputStream((InputStream)((Object)object), true);
        long[] arrl = NativeCrypto.PEM_read_bio_PKCS7(((OpenSSLBIOInputStream)((Object)object)).getBioContext(), 2);
        ((OpenSSLBIOInputStream)((Object)object)).release();
        object = new ArrayList<OpenSSLX509CRL>(arrl.length);
        int n = 0;
        while (n < arrl.length) {
            if (arrl[n] != 0L) {
                object.add(new OpenSSLX509CRL(arrl[n]));
            }
            ++n;
        }
        return object;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                OpenSSLX509CertificateFactory.ParsingException parsingException = new OpenSSLX509CertificateFactory.ParsingException(exception);
                throw parsingException;
            }
        }
        ((OpenSSLBIOInputStream)((Object)object)).release();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static OpenSSLX509CRL fromX509DerInputStream(InputStream inputStream) throws OpenSSLX509CertificateFactory.ParsingException {
        Throwable throwable2222;
        inputStream = new OpenSSLBIOInputStream(inputStream, true);
        long l = NativeCrypto.d2i_X509_CRL_bio(((OpenSSLBIOInputStream)inputStream).getBioContext());
        if (l == 0L) {
            ((OpenSSLBIOInputStream)inputStream).release();
            return null;
        }
        OpenSSLX509CRL openSSLX509CRL = new OpenSSLX509CRL(l);
        ((OpenSSLBIOInputStream)inputStream).release();
        return openSSLX509CRL;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                OpenSSLX509CertificateFactory.ParsingException parsingException = new OpenSSLX509CertificateFactory.ParsingException(exception);
                throw parsingException;
            }
        }
        ((OpenSSLBIOInputStream)inputStream).release();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static OpenSSLX509CRL fromX509PemInputStream(InputStream inputStream) throws OpenSSLX509CertificateFactory.ParsingException {
        Throwable throwable2222;
        inputStream = new OpenSSLBIOInputStream(inputStream, true);
        long l = NativeCrypto.PEM_read_bio_X509_CRL(((OpenSSLBIOInputStream)inputStream).getBioContext());
        if (l == 0L) {
            ((OpenSSLBIOInputStream)inputStream).release();
            return null;
        }
        OpenSSLX509CRL openSSLX509CRL = new OpenSSLX509CRL(l);
        ((OpenSSLBIOInputStream)inputStream).release();
        return openSSLX509CRL;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                OpenSSLX509CertificateFactory.ParsingException parsingException = new OpenSSLX509CertificateFactory.ParsingException(exception);
                throw parsingException;
            }
        }
        ((OpenSSLBIOInputStream)inputStream).release();
        throw throwable2222;
    }

    static Date toDate(long l) throws OpenSSLX509CertificateFactory.ParsingException {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(14, 0);
        NativeCrypto.ASN1_TIME_to_Calendar(l, calendar);
        return calendar.getTime();
    }

    private void verifyInternal(PublicKey publicKey, String object) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        String string;
        String string2 = string = this.getSigAlgName();
        if (string == null) {
            string2 = this.getSigAlgOID();
        }
        object = object == null ? Signature.getInstance(string2) : Signature.getInstance(string2, (String)object);
        ((Signature)object).initVerify(publicKey);
        ((Signature)object).update(this.getTBSCertList());
        if (((Signature)object).verify(this.getSignature())) {
            return;
        }
        throw new SignatureException("signature did not verify");
    }

    private void verifyOpenSSL(OpenSSLKey openSSLKey) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        NativeCrypto.X509_CRL_verify(this.mContext, this, openSSLKey.getNativeRef());
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mContext != 0L) {
                NativeCrypto.X509_CRL_free(this.mContext, this);
            }
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        String[] arrstring = NativeCrypto.get_X509_CRL_ext_oids(this.mContext, this, 1);
        if (arrstring.length == 0 && NativeCrypto.get_X509_CRL_ext_oids(this.mContext, this, 0).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(arrstring));
    }

    @Override
    public byte[] getEncoded() throws CRLException {
        return NativeCrypto.i2d_X509_CRL(this.mContext, this);
    }

    @Override
    public byte[] getExtensionValue(String string) {
        return NativeCrypto.X509_CRL_get_ext_oid(this.mContext, this, string);
    }

    @Override
    public Principal getIssuerDN() {
        return this.getIssuerX500Principal();
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        return new X500Principal(NativeCrypto.X509_CRL_get_issuer_name(this.mContext, this));
    }

    @Override
    public Date getNextUpdate() {
        return (Date)this.nextUpdate.clone();
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        String[] arrstring = NativeCrypto.get_X509_CRL_ext_oids(this.mContext, this, 0);
        if (arrstring.length == 0 && NativeCrypto.get_X509_CRL_ext_oids(this.mContext, this, 1).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(arrstring));
    }

    @Override
    public X509CRLEntry getRevokedCertificate(BigInteger object) {
        long l = NativeCrypto.X509_CRL_get0_by_serial(this.mContext, this, ((BigInteger)object).toByteArray());
        if (l == 0L) {
            return null;
        }
        try {
            object = new OpenSSLX509CRLEntry(NativeCrypto.X509_REVOKED_dup(l));
            return object;
        }
        catch (OpenSSLX509CertificateFactory.ParsingException parsingException) {
            return null;
        }
    }

    @Override
    public X509CRLEntry getRevokedCertificate(X509Certificate x509Extension) {
        if (x509Extension instanceof OpenSSLX509Certificate) {
            long l = NativeCrypto.X509_CRL_get0_by_cert(this.mContext, this, ((OpenSSLX509Certificate)(x509Extension = (OpenSSLX509Certificate)x509Extension)).getContext(), (OpenSSLX509Certificate)x509Extension);
            if (l == 0L) {
                return null;
            }
            try {
                x509Extension = new OpenSSLX509CRLEntry(NativeCrypto.X509_REVOKED_dup(l));
                return x509Extension;
            }
            catch (OpenSSLX509CertificateFactory.ParsingException parsingException) {
                return null;
            }
        }
        return this.getRevokedCertificate(x509Extension.getSerialNumber());
    }

    @Override
    public Set<? extends X509CRLEntry> getRevokedCertificates() {
        long[] arrl = NativeCrypto.X509_CRL_get_REVOKED(this.mContext, this);
        if (arrl != null && arrl.length != 0) {
            HashSet<OpenSSLX509CRLEntry> hashSet = new HashSet<OpenSSLX509CRLEntry>();
            for (long l : arrl) {
                try {
                    OpenSSLX509CRLEntry openSSLX509CRLEntry = new OpenSSLX509CRLEntry(l);
                    hashSet.add(openSSLX509CRLEntry);
                }
                catch (OpenSSLX509CertificateFactory.ParsingException parsingException) {
                    // empty catch block
                }
            }
            return hashSet;
        }
        return null;
    }

    @Override
    public String getSigAlgName() {
        String string = this.getSigAlgOID();
        String string2 = Platform.oidToAlgorithmName(string);
        if (string2 != null) {
            return string2;
        }
        return string;
    }

    @Override
    public String getSigAlgOID() {
        return NativeCrypto.get_X509_CRL_sig_alg_oid(this.mContext, this);
    }

    @Override
    public byte[] getSigAlgParams() {
        return NativeCrypto.get_X509_CRL_sig_alg_parameter(this.mContext, this);
    }

    @Override
    public byte[] getSignature() {
        return NativeCrypto.get_X509_CRL_signature(this.mContext, this);
    }

    @Override
    public byte[] getTBSCertList() throws CRLException {
        return NativeCrypto.get_X509_CRL_crl_enc(this.mContext, this);
    }

    @Override
    public Date getThisUpdate() {
        return (Date)this.thisUpdate.clone();
    }

    @Override
    public int getVersion() {
        return (int)NativeCrypto.X509_CRL_get_version(this.mContext, this) + 1;
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        for (String string : NativeCrypto.get_X509_CRL_ext_oids(this.mContext, this, 1)) {
            if (NativeCrypto.X509_supported_extension(NativeCrypto.X509_CRL_get_ext(this.mContext, this, string)) == 1) continue;
            return true;
        }
        return false;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isRevoked(Certificate certificate) {
        boolean bl = certificate instanceof X509Certificate;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (certificate instanceof OpenSSLX509Certificate) {
            certificate = (OpenSSLX509Certificate)certificate;
        } else {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(certificate.getEncoded());
            certificate = OpenSSLX509Certificate.fromX509DerInputStream(byteArrayInputStream);
        }
        if (NativeCrypto.X509_CRL_get0_by_cert(this.mContext, this, ((OpenSSLX509Certificate)certificate).getContext(), (OpenSSLX509Certificate)certificate) == 0L) return bl2;
        return true;
        catch (Exception exception) {
            throw new RuntimeException("cannot convert certificate", exception);
        }
    }

    @Override
    public String toString() {
        Object object = new ByteArrayOutputStream();
        long l = NativeCrypto.create_BIO_OutputStream((OutputStream)object);
        try {
            NativeCrypto.X509_CRL_print(l, this.mContext, this);
            object = ((ByteArrayOutputStream)object).toString();
            return object;
        }
        finally {
            NativeCrypto.BIO_free_all(l);
        }
    }

    @Override
    public void verify(PublicKey publicKey) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        if (publicKey instanceof OpenSSLKeyHolder) {
            this.verifyOpenSSL(((OpenSSLKeyHolder)((Object)publicKey)).getOpenSSLKey());
            return;
        }
        this.verifyInternal(publicKey, null);
    }

    @Override
    public void verify(PublicKey publicKey, String string) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.verifyInternal(publicKey, string);
    }
}

