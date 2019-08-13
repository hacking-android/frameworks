/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLBIOInputStream;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLKeyHolder;
import com.android.org.conscrypt.OpenSSLProvider;
import com.android.org.conscrypt.OpenSSLX509CertificateFactory;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.X509PublicKey;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.crypto.BadPaddingException;
import javax.security.auth.x500.X500Principal;

public final class OpenSSLX509Certificate
extends X509Certificate {
    private static final long serialVersionUID = 1992239142393372128L;
    @UnsupportedAppUsage
    private final transient long mContext;
    private transient Integer mHashCode;
    private final Date notAfter;
    private final Date notBefore;

    OpenSSLX509Certificate(long l) throws OpenSSLX509CertificateFactory.ParsingException {
        this.mContext = l;
        this.notBefore = OpenSSLX509Certificate.toDate(NativeCrypto.X509_get_notBefore(this.mContext, this));
        this.notAfter = OpenSSLX509Certificate.toDate(NativeCrypto.X509_get_notAfter(this.mContext, this));
    }

    private OpenSSLX509Certificate(long l, Date date, Date date2) {
        this.mContext = l;
        this.notBefore = date;
        this.notAfter = date2;
    }

    private static Collection<List<?>> alternativeNameArrayToList(Object[][] arrobject) {
        if (arrobject == null) {
            return null;
        }
        ArrayList<List<Object>> arrayList = new ArrayList<List<Object>>(arrobject.length);
        for (int i = 0; i < arrobject.length; ++i) {
            arrayList.add(Collections.unmodifiableList(Arrays.asList(arrobject[i])));
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    public static OpenSSLX509Certificate fromCertificate(Certificate certificate) throws CertificateEncodingException {
        if (certificate instanceof OpenSSLX509Certificate) {
            return (OpenSSLX509Certificate)certificate;
        }
        if (certificate instanceof X509Certificate) {
            return OpenSSLX509Certificate.fromX509Der(certificate.getEncoded());
        }
        throw new CertificateEncodingException("Only X.509 certificates are supported");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static List<OpenSSLX509Certificate> fromPkcs7DerInputStream(InputStream object) throws OpenSSLX509CertificateFactory.ParsingException {
        Throwable throwable2222;
        long[] arrl;
        block6 : {
            object = new OpenSSLBIOInputStream((InputStream)((Object)object), true);
            arrl = NativeCrypto.d2i_PKCS7_bio(((OpenSSLBIOInputStream)((Object)object)).getBioContext(), 1);
            ((OpenSSLBIOInputStream)((Object)object)).release();
            if (arrl != null) break block6;
            return Collections.emptyList();
        }
        object = new ArrayList<OpenSSLX509Certificate>(arrl.length);
        int n = 0;
        while (n < arrl.length) {
            if (arrl[n] != 0L) {
                object.add(new OpenSSLX509Certificate(arrl[n]));
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
    public static List<OpenSSLX509Certificate> fromPkcs7PemInputStream(InputStream object) throws OpenSSLX509CertificateFactory.ParsingException {
        Throwable throwable2222;
        object = new OpenSSLBIOInputStream((InputStream)((Object)object), true);
        long[] arrl = NativeCrypto.PEM_read_bio_PKCS7(((OpenSSLBIOInputStream)((Object)object)).getBioContext(), 1);
        ((OpenSSLBIOInputStream)((Object)object)).release();
        object = new ArrayList<OpenSSLX509Certificate>(arrl.length);
        int n = 0;
        while (n < arrl.length) {
            if (arrl[n] != 0L) {
                object.add(new OpenSSLX509Certificate(arrl[n]));
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

    public static OpenSSLX509Certificate fromX509Der(byte[] object) throws CertificateEncodingException {
        try {
            object = new OpenSSLX509Certificate(NativeCrypto.d2i_X509(object));
            return object;
        }
        catch (OpenSSLX509CertificateFactory.ParsingException parsingException) {
            throw new CertificateEncodingException(parsingException);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static OpenSSLX509Certificate fromX509DerInputStream(InputStream inputStream) throws OpenSSLX509CertificateFactory.ParsingException {
        Throwable throwable2222;
        inputStream = new OpenSSLBIOInputStream(inputStream, true);
        long l = NativeCrypto.d2i_X509_bio(((OpenSSLBIOInputStream)inputStream).getBioContext());
        if (l == 0L) {
            ((OpenSSLBIOInputStream)inputStream).release();
            return null;
        }
        OpenSSLX509Certificate openSSLX509Certificate = new OpenSSLX509Certificate(l);
        ((OpenSSLBIOInputStream)inputStream).release();
        return openSSLX509Certificate;
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
    @UnsupportedAppUsage
    public static OpenSSLX509Certificate fromX509PemInputStream(InputStream inputStream) throws OpenSSLX509CertificateFactory.ParsingException {
        Throwable throwable2222;
        inputStream = new OpenSSLBIOInputStream(inputStream, true);
        long l = NativeCrypto.PEM_read_bio_X509(((OpenSSLBIOInputStream)inputStream).getBioContext());
        if (l == 0L) {
            ((OpenSSLBIOInputStream)inputStream).release();
            return null;
        }
        OpenSSLX509Certificate openSSLX509Certificate = new OpenSSLX509Certificate(l);
        ((OpenSSLBIOInputStream)inputStream).release();
        return openSSLX509Certificate;
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

    private static Date toDate(long l) throws OpenSSLX509CertificateFactory.ParsingException {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(14, 0);
        NativeCrypto.ASN1_TIME_to_Calendar(l, calendar);
        return calendar.getTime();
    }

    private void verifyInternal(PublicKey publicKey, String object) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        object = object == null ? Signature.getInstance(this.getSigAlgName()) : Signature.getInstance(this.getSigAlgName(), (String)object);
        ((Signature)object).initVerify(publicKey);
        ((Signature)object).update(this.getTBSCertificate());
        if (((Signature)object).verify(this.getSignature())) {
            return;
        }
        throw new SignatureException("signature did not verify");
    }

    private void verifyOpenSSL(OpenSSLKey openSSLKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        try {
            NativeCrypto.X509_verify(this.mContext, this, openSSLKey.getNativeRef());
            return;
        }
        catch (BadPaddingException badPaddingException) {
            throw new SignatureException();
        }
        catch (RuntimeException runtimeException) {
            throw new CertificateException(runtimeException);
        }
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.checkValidity(new Date());
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        if (this.getNotBefore().compareTo(date) <= 0) {
            if (this.getNotAfter().compareTo(date) >= 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Certificate expired at ");
            stringBuilder.append(this.getNotAfter().toString());
            stringBuilder.append(" (compared to ");
            stringBuilder.append(date.toString());
            stringBuilder.append(")");
            throw new CertificateExpiredException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Certificate not valid until ");
        stringBuilder.append(this.getNotBefore().toString());
        stringBuilder.append(" (compared to ");
        stringBuilder.append(date.toString());
        stringBuilder.append(")");
        throw new CertificateNotYetValidException(stringBuilder.toString());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof OpenSSLX509Certificate) {
            boolean bl = NativeCrypto.X509_cmp(this.mContext, this, ((OpenSSLX509Certificate)object).mContext, (OpenSSLX509Certificate)(object = (OpenSSLX509Certificate)object)) == 0;
            return bl;
        }
        return super.equals(object);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mContext != 0L) {
                NativeCrypto.X509_free(this.mContext, this);
            }
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    @Override
    public int getBasicConstraints() {
        if ((NativeCrypto.get_X509_ex_flags(this.mContext, this) & 16) == 0) {
            return -1;
        }
        int n = NativeCrypto.get_X509_ex_pathlen(this.mContext, this);
        if (n == -1) {
            return Integer.MAX_VALUE;
        }
        return n;
    }

    public long getContext() {
        return this.mContext;
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        String[] arrstring = NativeCrypto.get_X509_ext_oids(this.mContext, this, 1);
        if (arrstring.length == 0 && NativeCrypto.get_X509_ext_oids(this.mContext, this, 0).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(arrstring));
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return NativeCrypto.i2d_X509(this.mContext, this);
    }

    @Override
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        String[] arrstring = NativeCrypto.get_X509_ex_xkusage(this.mContext, this);
        if (arrstring == null) {
            return null;
        }
        return Arrays.asList(arrstring);
    }

    @Override
    public byte[] getExtensionValue(String string) {
        return NativeCrypto.X509_get_ext_oid(this.mContext, this, string);
    }

    @Override
    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        return OpenSSLX509Certificate.alternativeNameArrayToList(NativeCrypto.get_X509_GENERAL_NAME_stack(this.mContext, this, 2));
    }

    @Override
    public Principal getIssuerDN() {
        return this.getIssuerX500Principal();
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        return NativeCrypto.get_X509_issuerUID(this.mContext, this);
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        return new X500Principal(NativeCrypto.X509_get_issuer_name(this.mContext, this));
    }

    @Override
    public boolean[] getKeyUsage() {
        boolean[] arrbl = NativeCrypto.get_X509_ex_kusage(this.mContext, this);
        if (arrbl == null) {
            return null;
        }
        if (arrbl.length >= 9) {
            return arrbl;
        }
        boolean[] arrbl2 = new boolean[9];
        System.arraycopy(arrbl, 0, arrbl2, 0, arrbl.length);
        return arrbl2;
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        String[] arrstring = NativeCrypto.get_X509_ext_oids(this.mContext, this, 0);
        if (arrstring.length == 0 && NativeCrypto.get_X509_ext_oids(this.mContext, this, 1).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(arrstring));
    }

    @Override
    public Date getNotAfter() {
        return (Date)this.notAfter.clone();
    }

    @Override
    public Date getNotBefore() {
        return (Date)this.notBefore.clone();
    }

    @Override
    public PublicKey getPublicKey() {
        try {
            Object object = new OpenSSLKey(NativeCrypto.X509_get_pubkey(this.mContext, this));
            object = ((OpenSSLKey)object).getPublicKey();
            return object;
        }
        catch (InvalidKeyException invalidKeyException) {
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        String string = NativeCrypto.get_X509_pubkey_oid(this.mContext, this);
        byte[] arrby = NativeCrypto.i2d_X509_PUBKEY(this.mContext, this);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(string);
            Object object = new X509EncodedKeySpec(arrby);
            object = keyFactory.generatePublic((KeySpec)object);
            return object;
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        return new X509PublicKey(string, arrby);
    }

    @Override
    public BigInteger getSerialNumber() {
        return new BigInteger(NativeCrypto.X509_get_serialNumber(this.mContext, this));
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
        return NativeCrypto.get_X509_sig_alg_oid(this.mContext, this);
    }

    @Override
    public byte[] getSigAlgParams() {
        return NativeCrypto.get_X509_sig_alg_parameter(this.mContext, this);
    }

    @Override
    public byte[] getSignature() {
        return NativeCrypto.get_X509_signature(this.mContext, this);
    }

    @Override
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        return OpenSSLX509Certificate.alternativeNameArrayToList(NativeCrypto.get_X509_GENERAL_NAME_stack(this.mContext, this, 1));
    }

    @Override
    public Principal getSubjectDN() {
        return this.getSubjectX500Principal();
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        return NativeCrypto.get_X509_subjectUID(this.mContext, this);
    }

    @Override
    public X500Principal getSubjectX500Principal() {
        return new X500Principal(NativeCrypto.X509_get_subject_name(this.mContext, this));
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        return NativeCrypto.get_X509_cert_info_enc(this.mContext, this);
    }

    @Override
    public int getVersion() {
        return (int)NativeCrypto.X509_get_version(this.mContext, this) + 1;
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        boolean bl = (NativeCrypto.get_X509_ex_flags(this.mContext, this) & 512) != 0;
        return bl;
    }

    @Override
    public int hashCode() {
        Integer n = this.mHashCode;
        if (n != null) {
            return n;
        }
        this.mHashCode = super.hashCode();
        return this.mHashCode;
    }

    @Override
    public String toString() {
        Object object = new ByteArrayOutputStream();
        long l = NativeCrypto.create_BIO_OutputStream((OutputStream)object);
        try {
            NativeCrypto.X509_print_ex(l, this.mContext, this, 0L, 0L);
            object = ((ByteArrayOutputStream)object).toString();
            return object;
        }
        finally {
            NativeCrypto.BIO_free_all(l);
        }
    }

    @Override
    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        if (publicKey instanceof OpenSSLKeyHolder) {
            this.verifyOpenSSL(((OpenSSLKeyHolder)((Object)publicKey)).getOpenSSLKey());
            return;
        }
        this.verifyInternal(publicKey, null);
    }

    @Override
    public void verify(PublicKey publicKey, String string) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.verifyInternal(publicKey, string);
    }

    @Override
    public void verify(PublicKey publicKey, Provider object) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (publicKey instanceof OpenSSLKeyHolder && object instanceof OpenSSLProvider) {
            this.verifyOpenSSL(((OpenSSLKeyHolder)((Object)publicKey)).getOpenSSLKey());
            return;
        }
        object = object == null ? Signature.getInstance(this.getSigAlgName()) : Signature.getInstance(this.getSigAlgName(), (Provider)object);
        ((Signature)object).initVerify(publicKey);
        ((Signature)object).update(this.getTBSCertificate());
        if (((Signature)object).verify(this.getSignature())) {
            return;
        }
        throw new SignatureException("signature did not verify");
    }

    public OpenSSLX509Certificate withDeletedExtension(String string) {
        OpenSSLX509Certificate openSSLX509Certificate = new OpenSSLX509Certificate(NativeCrypto.X509_dup(this.mContext, this), this.notBefore, this.notAfter);
        NativeCrypto.X509_delete_ext(openSSLX509Certificate.getContext(), openSSLX509Certificate, string);
        return openSSLX509Certificate;
    }
}

