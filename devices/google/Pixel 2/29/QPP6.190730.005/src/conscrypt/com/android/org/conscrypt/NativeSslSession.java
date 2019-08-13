/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractSessionContext;
import com.android.org.conscrypt.ClientSessionContext;
import com.android.org.conscrypt.ConscryptSession;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.NativeSsl;
import com.android.org.conscrypt.SSLUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

abstract class NativeSslSession {
    private static final Logger logger = Logger.getLogger(NativeSslSession.class.getName());

    NativeSslSession() {
    }

    private static void checkRemaining(ByteBuffer object, int n) throws IOException {
        if (n >= 0) {
            if (n <= ((Buffer)object).remaining()) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Length of blob is longer than available: ");
            stringBuilder.append(n);
            stringBuilder.append(" > ");
            stringBuilder.append(((Buffer)object).remaining());
            throw new IOException(stringBuilder.toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Length is negative: ");
        ((StringBuilder)object).append(n);
        throw new IOException(((StringBuilder)object).toString());
    }

    private static byte[] getOcspResponse(ConscryptSession object) {
        if ((object = object.getStatusResponses()).size() >= 1) {
            return (byte[])object.get(0);
        }
        return null;
    }

    private static void log(Throwable object) {
        Logger logger = NativeSslSession.logger;
        Level level = Level.INFO;
        object = ((Throwable)object).getMessage() != null ? ((Throwable)object).getMessage() : object.getClass().getName();
        logger.log(level, "Error inflating SSL session: {0}", object);
    }

    /*
     * Exception decompiling
     */
    static NativeSslSession newInstance(AbstractSessionContext var0, byte[] var1_5, String var2_6, int var3_7) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    static NativeSslSession newInstance(NativeRef.SSL_SESSION sSL_SESSION, ConscryptSession conscryptSession) throws SSLPeerUnverifiedException {
        AbstractSessionContext abstractSessionContext = (AbstractSessionContext)conscryptSession.getSessionContext();
        if (abstractSessionContext instanceof ClientSessionContext) {
            return new Impl(abstractSessionContext, sSL_SESSION, conscryptSession.getPeerHost(), conscryptSession.getPeerPort(), conscryptSession.getPeerCertificates(), NativeSslSession.getOcspResponse(conscryptSession), conscryptSession.getPeerSignedCertificateTimestamp());
        }
        return new Impl(abstractSessionContext, sSL_SESSION, null, -1, null, null, null);
    }

    abstract String getCipherSuite();

    abstract byte[] getId();

    abstract String getPeerHost();

    abstract byte[] getPeerOcspStapledResponse();

    abstract int getPeerPort();

    abstract byte[] getPeerSignedCertificateTimestamp();

    abstract String getProtocol();

    abstract boolean isSingleUse();

    abstract boolean isValid();

    abstract void offerToResume(NativeSsl var1) throws SSLException;

    abstract byte[] toBytes();

    abstract SSLSession toSSLSession();

    private static final class Impl
    extends NativeSslSession {
        private final String cipherSuite;
        private final AbstractSessionContext context;
        private final String host;
        private final java.security.cert.X509Certificate[] peerCertificates;
        private final byte[] peerOcspStapledResponse;
        private final byte[] peerSignedCertificateTimestamp;
        private final int port;
        private final String protocol;
        private final NativeRef.SSL_SESSION ref;

        private Impl(AbstractSessionContext abstractSessionContext, NativeRef.SSL_SESSION sSL_SESSION, String string, int n, java.security.cert.X509Certificate[] arrx509Certificate, byte[] arrby, byte[] arrby2) {
            this.context = abstractSessionContext;
            this.host = string;
            this.port = n;
            this.peerCertificates = arrx509Certificate;
            this.peerOcspStapledResponse = arrby;
            this.peerSignedCertificateTimestamp = arrby2;
            this.protocol = NativeCrypto.SSL_SESSION_get_version(sSL_SESSION.address);
            this.cipherSuite = NativeCrypto.cipherSuiteToJava(NativeCrypto.SSL_SESSION_cipher(sSL_SESSION.address));
            this.ref = sSL_SESSION;
        }

        private long getCreationTime() {
            return NativeCrypto.SSL_SESSION_get_time(this.ref.address);
        }

        @Override
        String getCipherSuite() {
            return this.cipherSuite;
        }

        @Override
        byte[] getId() {
            return NativeCrypto.SSL_SESSION_session_id(this.ref.address);
        }

        @Override
        String getPeerHost() {
            return this.host;
        }

        @Override
        byte[] getPeerOcspStapledResponse() {
            return this.peerOcspStapledResponse;
        }

        @Override
        int getPeerPort() {
            return this.port;
        }

        @Override
        byte[] getPeerSignedCertificateTimestamp() {
            return this.peerSignedCertificateTimestamp;
        }

        @Override
        String getProtocol() {
            return this.protocol;
        }

        @Override
        boolean isSingleUse() {
            return NativeCrypto.SSL_SESSION_should_be_single_use(this.ref.address);
        }

        @Override
        boolean isValid() {
            long l = this.getCreationTime();
            long l2 = Math.max(0L, Math.min((long)this.context.getSessionTimeout(), NativeCrypto.SSL_SESSION_get_timeout(this.ref.address)));
            boolean bl = System.currentTimeMillis() - l2 * 1000L < l;
            return bl;
        }

        @Override
        void offerToResume(NativeSsl nativeSsl) throws SSLException {
            nativeSsl.offerToResumeSession(this.ref.address);
        }

        @Override
        byte[] toBytes() {
            byte[] arrby = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)arrby);
            dataOutputStream.writeInt(SSLUtils.SessionType.OPEN_SSL_WITH_TLS_SCT.value);
            byte[] arrby2 = NativeCrypto.i2d_SSL_SESSION(this.ref.address);
            dataOutputStream.writeInt(arrby2.length);
            dataOutputStream.write(arrby2);
            dataOutputStream.writeInt(this.peerCertificates.length);
            java.security.cert.X509Certificate[] arrx509Certificate = this.peerCertificates;
            int n = arrx509Certificate.length;
            for (int i = 0; i < n; ++i) {
                arrby2 = arrx509Certificate[i].getEncoded();
                dataOutputStream.writeInt(arrby2.length);
                dataOutputStream.write(arrby2);
                continue;
            }
            try {
                if (this.peerOcspStapledResponse != null) {
                    dataOutputStream.writeInt(1);
                    dataOutputStream.writeInt(this.peerOcspStapledResponse.length);
                    dataOutputStream.write(this.peerOcspStapledResponse);
                } else {
                    dataOutputStream.writeInt(0);
                }
                if (this.peerSignedCertificateTimestamp != null) {
                    dataOutputStream.writeInt(this.peerSignedCertificateTimestamp.length);
                    dataOutputStream.write(this.peerSignedCertificateTimestamp);
                } else {
                    dataOutputStream.writeInt(0);
                }
                arrby = arrby.toByteArray();
                return arrby;
            }
            catch (CertificateEncodingException certificateEncodingException) {
                NativeSslSession.log(certificateEncodingException);
                return null;
            }
            catch (IOException iOException) {
                logger.log(Level.WARNING, "Failed to convert saved SSL Session: ", iOException);
                return null;
            }
        }

        @Override
        SSLSession toSSLSession() {
            return new SSLSession(){

                @Override
                public int getApplicationBufferSize() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String getCipherSuite() {
                    return this.getCipherSuite();
                }

                @Override
                public long getCreationTime() {
                    return this.getCreationTime();
                }

                @Override
                public byte[] getId() {
                    return this.getId();
                }

                @Override
                public long getLastAccessedTime() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Certificate[] getLocalCertificates() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Principal getLocalPrincipal() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int getPacketBufferSize() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String getPeerHost() {
                    return this.getPeerHost();
                }

                @Override
                public int getPeerPort() {
                    return this.getPeerPort();
                }

                @Override
                public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String getProtocol() {
                    return this.getProtocol();
                }

                @Override
                public SSLSessionContext getSessionContext() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Object getValue(String string) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String[] getValueNames() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void invalidate() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public boolean isValid() {
                    return this.isValid();
                }

                @Override
                public void putValue(String string, Object object) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void removeValue(String string) {
                    throw new UnsupportedOperationException();
                }
            };
        }

    }

}

