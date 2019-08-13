/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.EmptyArray;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLX509Certificate;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.auth.x500.X500Principal;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

final class SSLUtils {
    private static final String KEY_TYPE_EC = "EC";
    private static final String KEY_TYPE_RSA = "RSA";
    private static final int MAX_ENCRYPTION_OVERHEAD_DIFF = 2147483561;
    private static final int MAX_ENCRYPTION_OVERHEAD_LENGTH = 86;
    private static final int MAX_PROTOCOL_LENGTH = 255;
    static final boolean USE_ENGINE_SOCKET_BY_DEFAULT = Boolean.parseBoolean(System.getProperty("com.android.org.conscrypt.useEngineSocketByDefault", "false"));
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    private SSLUtils() {
    }

    static int calculateOutNetBufSize(int n) {
        return Math.min(16709, Math.min(2147483561, n) + 86);
    }

    static String[] concat(String[] ... arrstring) {
        int n;
        int n2 = arrstring.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            n3 += arrstring[n].length;
        }
        String[] arrstring2 = new String[n3];
        n2 = arrstring.length;
        n = 0;
        for (n3 = 0; n3 < n2; ++n3) {
            String[] arrstring3 = arrstring[n3];
            System.arraycopy(arrstring3, 0, arrstring2, n, arrstring3.length);
            n += arrstring3.length;
        }
        return arrstring2;
    }

    static String[] decodeProtocols(byte[] object) {
        int n;
        byte by;
        if (((byte[])object).length == 0) {
            return EmptyArray.STRING;
        }
        int n2 = 0;
        for (n = 0; n < ((byte[])object).length; n += by + 1) {
            by = object[n];
            if (by >= 0 && by <= ((byte[])object).length - n) {
                ++n2;
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Protocol has invalid length (");
            stringBuilder.append(by);
            stringBuilder.append(" at position ");
            stringBuilder.append(n);
            stringBuilder.append("): ");
            if (((byte[])object).length < 50) {
                object = Arrays.toString(object);
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(((byte[])object).length);
                stringBuilder2.append(" byte array");
                object = stringBuilder2.toString();
            }
            stringBuilder.append((String)object);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        String[] arrstring = new String[n2];
        n2 = 0;
        n = 0;
        while (n2 < ((byte[])object).length) {
            by = object[n2];
            String string = by > 0 ? new String((byte[])object, n2 + 1, (int)by, US_ASCII) : "";
            arrstring[n] = string;
            n2 += by + 1;
            ++n;
        }
        return arrstring;
    }

    private static java.security.cert.X509Certificate decodeX509Certificate(CertificateFactory certificateFactory, byte[] arrby) throws java.security.cert.CertificateException {
        if (certificateFactory != null) {
            return (java.security.cert.X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(arrby));
        }
        return OpenSSLX509Certificate.fromX509Der(arrby);
    }

    static java.security.cert.X509Certificate[] decodeX509CertificateChain(byte[][] arrby) throws java.security.cert.CertificateException {
        CertificateFactory certificateFactory = SSLUtils.getCertificateFactory();
        int n = arrby.length;
        java.security.cert.X509Certificate[] arrx509Certificate = new java.security.cert.X509Certificate[n];
        for (int i = 0; i < n; ++i) {
            arrx509Certificate[i] = SSLUtils.decodeX509Certificate(certificateFactory, arrby[i]);
        }
        return arrx509Certificate;
    }

    static byte[] encodeProtocols(String[] object) {
        if (object != null) {
            int n;
            int n2;
            if (((String[])object).length == 0) {
                return EmptyArray.BYTE;
            }
            int n3 = 0;
            for (n2 = 0; n2 < ((String[])object).length; ++n2) {
                if (object[n2] != null) {
                    n = object[n2].length();
                    if (n != 0 && n <= 255) {
                        n3 += n + 1;
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("protocol[");
                    ((StringBuilder)object).append(n2);
                    ((StringBuilder)object).append("] has invalid length: ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("protocol[");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append("] is null");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            byte[] arrby = new byte[n3];
            n2 = 0;
            for (n3 = 0; n3 < ((String[])object).length; ++n3) {
                Object object2 = object[n3];
                int n4 = ((String)object2).length();
                n = n2 + 1;
                arrby[n2] = (byte)n4;
                int n5 = 0;
                n2 = n;
                n = n5;
                while (n < n4) {
                    char c = ((String)object2).charAt(n);
                    if (c <= '') {
                        arrby[n2] = (byte)c;
                        ++n;
                        ++n2;
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Protocol contains invalid character: ");
                    ((StringBuilder)object).append(c);
                    ((StringBuilder)object).append("(protocol=");
                    ((StringBuilder)object).append((String)object2);
                    ((StringBuilder)object).append(")");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            return arrby;
        }
        throw new IllegalArgumentException("protocols array must be non-null");
    }

    static byte[][] encodeSubjectX509Principals(java.security.cert.X509Certificate[] arrx509Certificate) throws CertificateEncodingException {
        byte[][] arrarrby = new byte[arrx509Certificate.length][];
        for (int i = 0; i < arrx509Certificate.length; ++i) {
            arrarrby[i] = arrx509Certificate[i].getSubjectX500Principal().getEncoded();
        }
        return arrarrby;
    }

    private static CertificateFactory getCertificateFactory() {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            return certificateFactory;
        }
        catch (java.security.cert.CertificateException certificateException) {
            return null;
        }
    }

    static String getClientKeyType(byte by) {
        if (by != 1) {
            if (by != 64) {
                return null;
            }
            return KEY_TYPE_EC;
        }
        return KEY_TYPE_RSA;
    }

    static String getClientKeyTypeFromSignatureAlg(int n) {
        if ((n = NativeCrypto.SSL_get_signature_algorithm_key_type(n)) != 6) {
            if (n != 408) {
                return null;
            }
            return KEY_TYPE_EC;
        }
        return KEY_TYPE_RSA;
    }

    private static int getEncryptedPacketLength(ByteBuffer byteBuffer) {
        int n = byteBuffer.position();
        switch (SSLUtils.unsignedByte(byteBuffer.get(n))) {
            default: {
                return -1;
            }
            case 20: 
            case 21: 
            case 22: 
            case 23: 
        }
        if (SSLUtils.unsignedByte(byteBuffer.get(n + 1)) != 3) {
            return -1;
        }
        if ((n = SSLUtils.unsignedShort(byteBuffer.getShort(n + 3)) + 5) <= 5) {
            return -1;
        }
        return n;
    }

    static int getEncryptedPacketLength(ByteBuffer[] arrbyteBuffer, int n) {
        int n2;
        int n3;
        ByteBuffer byteBuffer = arrbyteBuffer[n];
        if (byteBuffer.remaining() >= 5) {
            return SSLUtils.getEncryptedPacketLength(byteBuffer);
        }
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(5);
        do {
            block6 : {
                byteBuffer = arrbyteBuffer[n];
                n3 = byteBuffer.position();
                n2 = byteBuffer.limit();
                if (byteBuffer.remaining() > byteBuffer2.remaining()) {
                    byteBuffer.limit(byteBuffer2.remaining() + n3);
                }
                byteBuffer2.put(byteBuffer);
                if (byteBuffer2.hasRemaining()) break block6;
                byteBuffer2.flip();
                return SSLUtils.getEncryptedPacketLength(byteBuffer2);
            }
            ++n;
        } while (true);
        finally {
            byteBuffer.limit(n2);
            byteBuffer.position(n3);
        }
    }

    static String getServerX509KeyType(long l) throws SSLException {
        String string = NativeCrypto.SSL_CIPHER_get_kx_name(l);
        if (!(string.equals(KEY_TYPE_RSA) || string.equals("DHE_RSA") || string.equals("ECDHE_RSA"))) {
            if (string.equals("ECDHE_ECDSA")) {
                return KEY_TYPE_EC;
            }
            return null;
        }
        return KEY_TYPE_RSA;
    }

    static Set<String> getSupportedClientKeyTypes(byte[] arrby, int[] arrn) {
        String string;
        int n;
        HashSet<String> hashSet = new HashSet<String>(arrby.length);
        int n2 = arrby.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            string = SSLUtils.getClientKeyType(arrby[n]);
            if (string == null) continue;
            hashSet.add(string);
        }
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>(arrn.length);
        n2 = arrn.length;
        for (n = n3; n < n2; ++n) {
            string = SSLUtils.getClientKeyTypeFromSignatureAlg(arrn[n]);
            if (string == null) continue;
            linkedHashSet.add(string);
        }
        if (arrby.length > 0 && arrn.length > 0) {
            linkedHashSet.retainAll(hashSet);
            return linkedHashSet;
        }
        if (arrn.length > 0) {
            return linkedHashSet;
        }
        return hashSet;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static X509Certificate[] toCertificateChain(java.security.cert.X509Certificate[] arrx509Certificate) throws SSLPeerUnverifiedException {
        X509Certificate[] arrx509Certificate2;
        int n;
        try {
            arrx509Certificate2 = new X509Certificate[arrx509Certificate.length];
            n = 0;
        }
        catch (CertificateException certificateException) {
            SSLPeerUnverifiedException sSLPeerUnverifiedException = new SSLPeerUnverifiedException(certificateException.getMessage());
            sSLPeerUnverifiedException.initCause(sSLPeerUnverifiedException);
            throw sSLPeerUnverifiedException;
        }
        catch (CertificateEncodingException certificateEncodingException) {
            SSLPeerUnverifiedException sSLPeerUnverifiedException = new SSLPeerUnverifiedException(certificateEncodingException.getMessage());
            sSLPeerUnverifiedException.initCause(sSLPeerUnverifiedException);
            throw sSLPeerUnverifiedException;
        }
        do {
            if (n >= arrx509Certificate.length) return arrx509Certificate2;
            arrx509Certificate2[n] = X509Certificate.getInstance(arrx509Certificate[n].getEncoded());
            ++n;
            continue;
            break;
        } while (true);
    }

    static byte[] toProtocolBytes(String string) {
        if (string == null) {
            return null;
        }
        return string.getBytes(US_ASCII);
    }

    static String toProtocolString(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        return new String(arrby, US_ASCII);
    }

    static SSLException toSSLException(Throwable throwable) {
        if (throwable instanceof SSLException) {
            return (SSLException)throwable;
        }
        return new SSLException(throwable);
    }

    static SSLHandshakeException toSSLHandshakeException(Throwable throwable) {
        if (throwable instanceof SSLHandshakeException) {
            return (SSLHandshakeException)throwable;
        }
        return (SSLHandshakeException)new SSLHandshakeException(throwable.getMessage()).initCause(throwable);
    }

    private static short unsignedByte(byte by) {
        return (short)(by & 255);
    }

    private static int unsignedShort(short s) {
        return 65535 & s;
    }

    static final class EngineStates {
        static final int STATE_CLOSED = 8;
        static final int STATE_CLOSED_INBOUND = 6;
        static final int STATE_CLOSED_OUTBOUND = 7;
        static final int STATE_HANDSHAKE_COMPLETED = 3;
        static final int STATE_HANDSHAKE_STARTED = 2;
        static final int STATE_MODE_SET = 1;
        static final int STATE_NEW = 0;
        static final int STATE_READY = 5;
        static final int STATE_READY_HANDSHAKE_CUT_THROUGH = 4;

        private EngineStates() {
        }
    }

    static enum SessionType {
        OPEN_SSL(1),
        OPEN_SSL_WITH_OCSP(2),
        OPEN_SSL_WITH_TLS_SCT(3);
        
        final int value;

        private SessionType(int n2) {
            this.value = n2;
        }

        static boolean isSupportedType(int n) {
            boolean bl = n == SessionType.OPEN_SSL.value || n == SessionType.OPEN_SSL_WITH_OCSP.value || n == SessionType.OPEN_SSL_WITH_TLS_SCT.value;
            return bl;
        }
    }

}

