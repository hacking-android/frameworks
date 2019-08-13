/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.OpenSSLX509CRL;
import com.android.org.conscrypt.OpenSSLX509CertPath;
import com.android.org.conscrypt.OpenSSLX509Certificate;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class OpenSSLX509CertificateFactory
extends CertificateFactorySpi {
    private static final byte[] PKCS7_MARKER = new byte[]{45, 45, 45, 45, 45, 66, 69, 71, 73, 78, 32, 80, 75, 67, 83, 55};
    private static final int PUSHBACK_SIZE = 64;
    private Parser<OpenSSLX509Certificate> certificateParser = new Parser<OpenSSLX509Certificate>(){

        @Override
        public List<? extends OpenSSLX509Certificate> fromPkcs7DerInputStream(InputStream inputStream) throws ParsingException {
            return OpenSSLX509Certificate.fromPkcs7DerInputStream(inputStream);
        }

        @Override
        public List<? extends OpenSSLX509Certificate> fromPkcs7PemInputStream(InputStream inputStream) throws ParsingException {
            return OpenSSLX509Certificate.fromPkcs7PemInputStream(inputStream);
        }

        @Override
        public OpenSSLX509Certificate fromX509DerInputStream(InputStream inputStream) throws ParsingException {
            return OpenSSLX509Certificate.fromX509DerInputStream(inputStream);
        }

        @Override
        public OpenSSLX509Certificate fromX509PemInputStream(InputStream inputStream) throws ParsingException {
            return OpenSSLX509Certificate.fromX509PemInputStream(inputStream);
        }
    };
    private Parser<OpenSSLX509CRL> crlParser = new Parser<OpenSSLX509CRL>(){

        @Override
        public List<? extends OpenSSLX509CRL> fromPkcs7DerInputStream(InputStream inputStream) throws ParsingException {
            return OpenSSLX509CRL.fromPkcs7DerInputStream(inputStream);
        }

        @Override
        public List<? extends OpenSSLX509CRL> fromPkcs7PemInputStream(InputStream inputStream) throws ParsingException {
            return OpenSSLX509CRL.fromPkcs7PemInputStream(inputStream);
        }

        @Override
        public OpenSSLX509CRL fromX509DerInputStream(InputStream inputStream) throws ParsingException {
            return OpenSSLX509CRL.fromX509DerInputStream(inputStream);
        }

        @Override
        public OpenSSLX509CRL fromX509PemInputStream(InputStream inputStream) throws ParsingException {
            return OpenSSLX509CRL.fromX509PemInputStream(inputStream);
        }
    };

    @Override
    public CRL engineGenerateCRL(InputStream object) throws CRLException {
        try {
            object = this.crlParser.generateItem((InputStream)object);
            return object;
        }
        catch (ParsingException parsingException) {
            throw new CRLException(parsingException);
        }
    }

    @Override
    public Collection<? extends CRL> engineGenerateCRLs(InputStream object) throws CRLException {
        if (object == null) {
            return Collections.emptyList();
        }
        try {
            object = this.crlParser.generateItems((InputStream)object);
            return object;
        }
        catch (ParsingException parsingException) {
            throw new CRLException(parsingException);
        }
    }

    @Override
    public CertPath engineGenerateCertPath(InputStream inputStream) throws CertificateException {
        return OpenSSLX509CertPath.fromEncoding(inputStream);
    }

    @Override
    public CertPath engineGenerateCertPath(InputStream inputStream, String string) throws CertificateException {
        return OpenSSLX509CertPath.fromEncoding(inputStream, string);
    }

    @Override
    public CertPath engineGenerateCertPath(List<? extends Certificate> object) throws CertificateException {
        ArrayList<X509Certificate> arrayList = new ArrayList<X509Certificate>(object.size());
        for (int i = 0; i < object.size(); ++i) {
            Certificate certificate = object.get(i);
            if (certificate instanceof X509Certificate) {
                arrayList.add((X509Certificate)certificate);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Certificate not X.509 type at index ");
            ((StringBuilder)object).append(i);
            throw new CertificateException(((StringBuilder)object).toString());
        }
        return new OpenSSLX509CertPath(arrayList);
    }

    @Override
    public Certificate engineGenerateCertificate(InputStream object) throws CertificateException {
        try {
            object = this.certificateParser.generateItem((InputStream)object);
            return object;
        }
        catch (ParsingException parsingException) {
            throw new CertificateException(parsingException);
        }
    }

    @Override
    public Collection<? extends Certificate> engineGenerateCertificates(InputStream object) throws CertificateException {
        try {
            object = this.certificateParser.generateItems((InputStream)object);
            return object;
        }
        catch (ParsingException parsingException) {
            throw new CertificateException(parsingException);
        }
    }

    @Override
    public Iterator<String> engineGetCertPathEncodings() {
        return OpenSSLX509CertPath.getEncodingsIterator();
    }

    private static abstract class Parser<T> {
        private Parser() {
        }

        protected abstract List<? extends T> fromPkcs7DerInputStream(InputStream var1) throws ParsingException;

        protected abstract List<? extends T> fromPkcs7PemInputStream(InputStream var1) throws ParsingException;

        protected abstract T fromX509DerInputStream(InputStream var1) throws ParsingException;

        protected abstract T fromX509PemInputStream(InputStream var1) throws ParsingException;

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        T generateItem(InputStream var1_1) throws ParsingException {
            if (var1_1 == null) throw new ParsingException("inStream == null");
            var2_3 = var1_1.markSupported();
            if (var2_3) {
                var1_1.mark(OpenSSLX509CertificateFactory.access$000().length);
            }
            var3_4 = new PushbackInputStream(var1_1, 64);
            try {
                var4_5 = new byte[OpenSSLX509CertificateFactory.access$000().length];
                var5_7 = var3_4.read((byte[])var4_5);
                if (var5_7 < 0) ** GOTO lbl33
                var3_4.unread((byte[])var4_5, 0, var5_7);
            }
            catch (Exception var4_6) {
                if (var2_3 == false) throw new ParsingException(var4_6);
                try {
                    var1_1.reset();
                    throw new ParsingException(var4_6);
                }
                catch (IOException var1_2) {
                    // empty catch block
                }
                throw new ParsingException(var4_6);
            }
            if (var4_5[0] == 45) {
                if (var5_7 != OpenSSLX509CertificateFactory.access$000().length) return this.fromX509PemInputStream(var3_4);
                if (Arrays.equals(OpenSSLX509CertificateFactory.access$000(), (byte[])var4_5) == false) return this.fromX509PemInputStream(var3_4);
                var6_8 = this.fromPkcs7PemInputStream(var3_4);
                if (var6_8.size() == 0) {
                    return null;
                }
                var6_8.get(0);
            }
            if (var4_5[4] != 6) return this.fromX509DerInputStream(var3_4);
            var4_5 = this.fromPkcs7DerInputStream(var3_4);
            if (var4_5.size() != 0) return (T)var4_5.get(0);
            return null;
lbl33: // 1 sources:
            var4_5 = new ParsingException("inStream is empty");
            throw var4_5;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        Collection<? extends T> generateItems(InputStream object) throws ParsingException {
            Object object2;
            boolean bl;
            PushbackInputStream pushbackInputStream;
            block19 : {
                block17 : {
                    block18 : {
                        if (object == null) throw new ParsingException("inStream == null");
                        if (((InputStream)((Object)object)).available() == 0) {
                            return Collections.emptyList();
                        }
                        bl = ((InputStream)((Object)object)).markSupported();
                        if (bl) {
                            ((InputStream)((Object)object)).mark(64);
                        }
                        pushbackInputStream = new PushbackInputStream((InputStream)((Object)object), 64);
                        try {
                            object2 = new byte[PKCS7_MARKER.length];
                            int n = pushbackInputStream.read((byte[])object2);
                            if (n < 0) break block17;
                            pushbackInputStream.unread((byte[])object2, 0, n);
                            if (n != PKCS7_MARKER.length || !Arrays.equals(PKCS7_MARKER, object2)) break block18;
                            return this.fromPkcs7PemInputStream(pushbackInputStream);
                        }
                        catch (Exception exception) {
                            if (!bl) throw new ParsingException(exception);
                            try {
                                ((InputStream)((Object)object)).reset();
                                throw new ParsingException(exception);
                            }
                            catch (IOException iOException) {
                                // empty catch block
                            }
                            throw new ParsingException(exception);
                        }
                    }
                    if (object2[4] != 6) break block19;
                    return this.fromPkcs7DerInputStream(pushbackInputStream);
                }
                object2 = new ParsingException("inStream is empty");
                throw object2;
                catch (IOException iOException) {
                    throw new ParsingException("Problem reading input stream", iOException);
                }
            }
            ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
            do {
                if (bl) {
                    ((InputStream)((Object)object)).mark(64);
                }
                try {
                    object2 = this.generateItem(pushbackInputStream);
                    arrayList.add((byte[])object2);
                }
                catch (ParsingException parsingException) {
                    if (bl) {
                        try {
                            ((InputStream)((Object)object)).reset();
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                    }
                    object2 = null;
                }
            } while (object2 != null);
            return arrayList;
        }
    }

    static class ParsingException
    extends Exception {
        private static final long serialVersionUID = 8390802697728301325L;

        ParsingException(Exception exception) {
            super(exception);
        }

        ParsingException(String string) {
            super(string);
        }

        ParsingException(String string, Exception exception) {
            super(string, exception);
        }
    }

}

