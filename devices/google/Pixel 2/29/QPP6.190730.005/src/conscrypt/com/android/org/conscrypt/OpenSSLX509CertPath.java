/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLBIOInputStream;
import com.android.org.conscrypt.OpenSSLX509Certificate;
import com.android.org.conscrypt.OpenSSLX509CertificateFactory;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class OpenSSLX509CertPath
extends CertPath {
    private static final List<String> ALL_ENCODINGS;
    private static final Encoding DEFAULT_ENCODING;
    private static final byte[] PKCS7_MARKER;
    private static final int PUSHBACK_SIZE = 64;
    private static final long serialVersionUID = -3249106005255170761L;
    private final List<? extends X509Certificate> mCertificates;

    static {
        PKCS7_MARKER = new byte[]{45, 45, 45, 45, 45, 66, 69, 71, 73, 78, 32, 80, 75, 67, 83, 55};
        ALL_ENCODINGS = Collections.unmodifiableList(Arrays.asList(Encoding.PKI_PATH.apiName, Encoding.PKCS7.apiName));
        DEFAULT_ENCODING = Encoding.PKI_PATH;
    }

    OpenSSLX509CertPath(List<? extends X509Certificate> list) {
        super("X.509");
        this.mCertificates = list;
    }

    static CertPath fromEncoding(InputStream inputStream) throws CertificateException {
        if (inputStream != null) {
            return OpenSSLX509CertPath.fromEncoding(inputStream, DEFAULT_ENCODING);
        }
        throw new CertificateException("inStream == null");
    }

    private static CertPath fromEncoding(InputStream inputStream, Encoding encoding) throws CertificateException {
        int n = 1.$SwitchMap$com$android$org$conscrypt$OpenSSLX509CertPath$Encoding[encoding.ordinal()];
        if (n != 1) {
            if (n == 2) {
                return OpenSSLX509CertPath.fromPkcs7Encoding(inputStream);
            }
            throw new CertificateEncodingException("Unknown encoding");
        }
        return OpenSSLX509CertPath.fromPkiPathEncoding(inputStream);
    }

    static CertPath fromEncoding(InputStream object, String string) throws CertificateException {
        if (object != null) {
            Encoding encoding = Encoding.findByApiName(string);
            if (encoding != null) {
                return OpenSSLX509CertPath.fromEncoding((InputStream)object, encoding);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid encoding: ");
            ((StringBuilder)object).append(string);
            throw new CertificateException(((StringBuilder)object).toString());
        }
        throw new CertificateException("inStream == null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static CertPath fromPkcs7Encoding(InputStream object) throws CertificateException {
        block10 : {
            if (object != null) {
                int n = ((InputStream)object).available();
                if (n == 0) break block10;
                boolean bl = ((InputStream)object).markSupported();
                if (bl) {
                    ((InputStream)object).mark(64);
                }
                Object object2 = new PushbackInputStream((InputStream)object, 64);
                try {
                    byte[] arrby = new byte[PKCS7_MARKER.length];
                    n = ((FilterInputStream)object2).read(arrby);
                    if (n >= 0) {
                        ((PushbackInputStream)object2).unread(arrby, 0, n);
                        if (n != PKCS7_MARKER.length) return new OpenSSLX509CertPath(OpenSSLX509Certificate.fromPkcs7DerInputStream((InputStream)object2));
                        if (!Arrays.equals(PKCS7_MARKER, arrby)) return new OpenSSLX509CertPath(OpenSSLX509Certificate.fromPkcs7DerInputStream((InputStream)object2));
                        return new OpenSSLX509CertPath(OpenSSLX509Certificate.fromPkcs7PemInputStream((InputStream)object2));
                    }
                    object2 = new OpenSSLX509CertificateFactory.ParsingException("inStream is empty");
                    throw object2;
                }
                catch (Exception exception) {
                    if (!bl) throw new CertificateException(exception);
                    try {
                        ((InputStream)object).reset();
                        throw new CertificateException(exception);
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    throw new CertificateException(exception);
                }
            }
        }
        try {
            return new OpenSSLX509CertPath(Collections.emptyList());
        }
        catch (IOException iOException) {
            throw new CertificateException("Problem reading input stream", iOException);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static CertPath fromPkiPathEncoding(InputStream var0) throws CertificateException {
        var1_4 = new OpenSSLBIOInputStream((InputStream)var0, true);
        var2_5 = var0.markSupported();
        if (var2_5) {
            var0.mark(64);
        }
        var3_6 = NativeCrypto.ASN1_seq_unpack_X509_bio(var1_4.getBioContext());
        var1_4.release();
        if (var3_6 == null) {
            return new OpenSSLX509CertPath(Collections.emptyList());
        }
        var1_4 = new ArrayList<E>(var3_6.length);
        var4_8 = var3_6.length - 1;
        while (var4_8 >= 0) {
            if (var3_6[var4_8] != 0L) {
                var0 = new OpenSSLX509Certificate(var3_6[var4_8]);
                var1_4.add(var0);
            }
            --var4_8;
        }
        return new OpenSSLX509CertPath((List<? extends X509Certificate>)var1_4);
        catch (OpenSSLX509CertificateFactory.ParsingException var0_1) {
            throw new CertificateParsingException(var0_1);
        }
        {
            catch (Throwable var0_2) {
            }
            catch (Exception var3_7) {}
            if (!var2_5) ** GOTO lbl32
            {
                try {
                    var0.reset();
                }
                catch (IOException var0_3) {
                    // empty catch block
                }
lbl32: // 3 sources:
                var0 = new CertificateException(var3_7);
                throw var0;
            }
        }
        var1_4.release();
        throw var0_2;
    }

    private byte[] getEncoded(Encoding encoding) throws CertificateEncodingException {
        int n;
        OpenSSLX509Certificate[] arropenSSLX509Certificate = new OpenSSLX509Certificate[this.mCertificates.size()];
        long[] arrl = new long[arropenSSLX509Certificate.length];
        int n2 = 0;
        for (n = arropenSSLX509Certificate.length - 1; n >= 0; --n) {
            X509Certificate x509Certificate = this.mCertificates.get(n2);
            arropenSSLX509Certificate[n] = x509Certificate instanceof OpenSSLX509Certificate ? (OpenSSLX509Certificate)x509Certificate : OpenSSLX509Certificate.fromX509Der(x509Certificate.getEncoded());
            arrl[n] = arropenSSLX509Certificate[n].getContext();
            ++n2;
        }
        n = 1.$SwitchMap$com$android$org$conscrypt$OpenSSLX509CertPath$Encoding[encoding.ordinal()];
        if (n != 1) {
            if (n == 2) {
                return NativeCrypto.i2d_PKCS7(arrl);
            }
            throw new CertificateEncodingException("Unknown encoding");
        }
        return NativeCrypto.ASN1_seq_pack_X509(arrl);
    }

    static Iterator<String> getEncodingsIterator() {
        return ALL_ENCODINGS.iterator();
    }

    @Override
    public List<? extends Certificate> getCertificates() {
        return Collections.unmodifiableList(this.mCertificates);
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return this.getEncoded(DEFAULT_ENCODING);
    }

    @Override
    public byte[] getEncoded(String string) throws CertificateEncodingException {
        Object object = Encoding.findByApiName(string);
        if (object != null) {
            return this.getEncoded((Encoding)((Object)object));
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid encoding: ");
        ((StringBuilder)object).append(string);
        throw new CertificateEncodingException(((StringBuilder)object).toString());
    }

    @Override
    public Iterator<String> getEncodings() {
        return OpenSSLX509CertPath.getEncodingsIterator();
    }

    private static enum Encoding {
        PKI_PATH("PkiPath"),
        PKCS7("PKCS7");
        
        private final String apiName;

        private Encoding(String string2) {
            this.apiName = string2;
        }

        static Encoding findByApiName(String string) throws CertificateEncodingException {
            for (Encoding encoding : Encoding.values()) {
                if (!encoding.apiName.equals(string)) continue;
                return encoding;
            }
            return null;
        }
    }

}

