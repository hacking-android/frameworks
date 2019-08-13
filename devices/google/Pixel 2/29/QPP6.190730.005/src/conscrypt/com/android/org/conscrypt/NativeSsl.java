/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractSessionContext;
import com.android.org.conscrypt.AddressUtils;
import com.android.org.conscrypt.ApplicationProtocolSelectorAdapter;
import com.android.org.conscrypt.EmptyArray;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.PSKKeyManager;
import com.android.org.conscrypt.SSLParametersImpl;
import com.android.org.conscrypt.SSLUtils;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

final class NativeSsl {
    private final SSLParametersImpl.AliasChooser aliasChooser;
    private final NativeCrypto.SSLHandshakeCallbacks handshakeCallbacks;
    private X509Certificate[] localCertificates;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final SSLParametersImpl parameters;
    private final SSLParametersImpl.PSKCallbacks pskCallbacks;
    private volatile long ssl;

    private NativeSsl(long l, SSLParametersImpl sSLParametersImpl, NativeCrypto.SSLHandshakeCallbacks sSLHandshakeCallbacks, SSLParametersImpl.AliasChooser aliasChooser, SSLParametersImpl.PSKCallbacks pSKCallbacks) {
        this.ssl = l;
        this.parameters = sSLParametersImpl;
        this.handshakeCallbacks = sSLHandshakeCallbacks;
        this.aliasChooser = aliasChooser;
        this.pskCallbacks = pSKCallbacks;
    }

    private void enablePSKKeyManagerIfRequested() throws SSLException {
        Object object = this.parameters.getPSKKeyManager();
        if (object != null) {
            boolean bl;
            boolean bl2 = false;
            String[] arrstring = this.parameters.enabledCipherSuites;
            int n = arrstring.length;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                String string = arrstring[n2];
                if (string != null && string.contains("PSK")) {
                    bl = true;
                    break;
                }
                ++n2;
            } while (true);
            if (bl) {
                if (this.isClient()) {
                    NativeCrypto.set_SSL_psk_client_callback_enabled(this.ssl, this, true);
                } else {
                    NativeCrypto.set_SSL_psk_server_callback_enabled(this.ssl, this, true);
                    object = this.pskCallbacks.chooseServerPSKIdentityHint((PSKKeyManager)object);
                    NativeCrypto.SSL_use_psk_identity_hint(this.ssl, this, (String)object);
                }
            }
        }
    }

    private boolean isClient() {
        return this.parameters.getUseClientMode();
    }

    static NativeSsl newInstance(SSLParametersImpl sSLParametersImpl, NativeCrypto.SSLHandshakeCallbacks sSLHandshakeCallbacks, SSLParametersImpl.AliasChooser aliasChooser, SSLParametersImpl.PSKCallbacks pSKCallbacks) throws SSLException {
        AbstractSessionContext abstractSessionContext = sSLParametersImpl.getSessionContext();
        return new NativeSsl(NativeCrypto.SSL_new(abstractSessionContext.sslCtxNativePointer, abstractSessionContext), sSLParametersImpl, sSLHandshakeCallbacks, aliasChooser, pSKCallbacks);
    }

    private void setCertificateValidation() throws SSLException {
        if (!this.isClient()) {
            X509Certificate[] arrx509Certificate;
            boolean bl;
            if (this.parameters.getNeedClientAuth()) {
                NativeCrypto.SSL_set_verify(this.ssl, this, 3);
                bl = true;
            } else if (this.parameters.getWantClientAuth()) {
                NativeCrypto.SSL_set_verify(this.ssl, this, 1);
                bl = true;
            } else {
                NativeCrypto.SSL_set_verify(this.ssl, this, 0);
                bl = false;
            }
            if (bl && (arrx509Certificate = this.parameters.getX509TrustManager().getAcceptedIssuers()) != null && arrx509Certificate.length != 0) {
                try {
                    arrx509Certificate = SSLUtils.encodeSubjectX509Principals(arrx509Certificate);
                }
                catch (CertificateEncodingException certificateEncodingException) {
                    throw new SSLException("Problem encoding principals", certificateEncodingException);
                }
                NativeCrypto.SSL_set_client_CA_list(this.ssl, this, (byte[][])arrx509Certificate);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void setTlsChannelId(OpenSSLKey openSSLKey) throws SSLException {
        if (!this.parameters.channelIdEnabled) {
            return;
        }
        if (this.parameters.getUseClientMode()) {
            if (openSSLKey == null) throw new SSLHandshakeException("Invalid TLS channel ID key specified");
            NativeCrypto.SSL_set1_tls_channel_id(this.ssl, this, openSSLKey.getNativeRef());
            return;
        } else {
            NativeCrypto.SSL_enable_tls_channel_id(this.ssl, this);
        }
    }

    void chooseClientCertificate(byte[] object, int[] object2, byte[][] arrby) throws SSLException, CertificateEncodingException {
        object = SSLUtils.getSupportedClientKeyTypes((byte[])object, object2);
        String[] arrstring = object.toArray(new String[object.size()]);
        if (arrby == null) {
            object = null;
        } else {
            object2 = new X500Principal[arrby.length];
            int n = 0;
            do {
                object = object2;
                if (n >= arrby.length) break;
                object2[n] = (int)new X500Principal(arrby[n]);
                ++n;
            } while (true);
        }
        object2 = this.parameters.getX509KeyManager();
        object = object2 != null ? this.aliasChooser.chooseClientAlias((X509KeyManager)object2, (X500Principal[])object, arrstring) : null;
        this.setCertificate((String)object);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    int clientPSKKeyRequested(String arrby, byte[] arrby2, byte[] arrby3) {
        byte[] arrby4;
        PSKKeyManager pSKKeyManager = this.parameters.getPSKKeyManager();
        if (pSKKeyManager == null) {
            return 0;
        }
        String string = this.pskCallbacks.chooseClientPSKIdentity(pSKKeyManager, (String)arrby);
        if (string == null) {
            string = "";
            arrby4 = EmptyArray.BYTE;
        } else if (string.isEmpty()) {
            arrby4 = EmptyArray.BYTE;
        } else {
            arrby4 = string.getBytes("UTF-8");
        }
        if (arrby4.length + 1 > arrby2.length) {
            return 0;
        }
        if (arrby4.length > 0) {
            System.arraycopy(arrby4, 0, arrby2, 0, arrby4.length);
        }
        arrby2[arrby4.length] = (byte)(false ? 1 : 0);
        if ((arrby = this.pskCallbacks.getPSKKey(pSKKeyManager, (String)arrby, string).getEncoded()) == null) {
            return 0;
        }
        if (arrby.length > arrby3.length) {
            return 0;
        }
        System.arraycopy(arrby, 0, arrby3, 0, arrby.length);
        return arrby.length;
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("UTF-8 encoding not supported", unsupportedEncodingException);
        }
    }

    void close() {
        this.lock.writeLock().lock();
        try {
            if (!this.isClosed()) {
                long l = this.ssl;
                this.ssl = 0L;
                NativeCrypto.SSL_free(l, this);
            }
            return;
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    int doHandshake() throws IOException {
        this.lock.readLock().lock();
        try {
            int n = NativeCrypto.ENGINE_SSL_do_handshake(this.ssl, this, this.handshakeCallbacks);
            return n;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    void doHandshake(FileDescriptor object, int n) throws CertificateException, IOException {
        this.lock.readLock().lock();
        try {
            if (!this.isClosed() && object != null && ((FileDescriptor)object).valid()) {
                NativeCrypto.SSL_do_handshake(this.ssl, this, (FileDescriptor)object, this.handshakeCallbacks, n);
                return;
            }
            object = new SocketException("Socket is closed");
            throw object;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    byte[] exportKeyingMaterial(String arrby, byte[] arrby2, int n) throws SSLException {
        if (arrby != null) {
            arrby = arrby.getBytes(Charset.forName("US-ASCII"));
            return NativeCrypto.SSL_export_keying_material(this.ssl, this, arrby, arrby2, n);
        }
        throw new NullPointerException("Label is null");
    }

    protected final void finalize() throws Throwable {
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    void forceRead() throws IOException {
        this.lock.readLock().lock();
        try {
            NativeCrypto.ENGINE_SSL_force_read(this.ssl, this, this.handshakeCallbacks);
            return;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    byte[] getApplicationProtocol() {
        return NativeCrypto.getApplicationProtocol(this.ssl, this);
    }

    String getCipherSuite() {
        return NativeCrypto.cipherSuiteToJava(NativeCrypto.SSL_get_current_cipher(this.ssl, this));
    }

    int getError(int n) {
        return NativeCrypto.SSL_get_error(this.ssl, this, n);
    }

    X509Certificate[] getLocalCertificates() {
        return this.localCertificates;
    }

    int getMaxSealOverhead() {
        return NativeCrypto.SSL_max_seal_overhead(this.ssl, this);
    }

    byte[] getPeerCertificateOcspData() {
        return NativeCrypto.SSL_get_ocsp_response(this.ssl, this);
    }

    X509Certificate[] getPeerCertificates() throws CertificateException {
        Object object = NativeCrypto.SSL_get0_peer_certificates(this.ssl, this);
        object = object == null ? null : SSLUtils.decodeX509CertificateChain(object);
        return object;
    }

    byte[] getPeerTlsSctData() {
        return NativeCrypto.SSL_get_signed_cert_timestamp_list(this.ssl, this);
    }

    int getPendingReadableBytes() {
        return NativeCrypto.SSL_pending_readable_bytes(this.ssl, this);
    }

    String getRequestedServerName() {
        return NativeCrypto.SSL_get_servername(this.ssl, this);
    }

    byte[] getSessionId() {
        return NativeCrypto.SSL_session_id(this.ssl, this);
    }

    long getTime() {
        return NativeCrypto.SSL_get_time(this.ssl, this);
    }

    long getTimeout() {
        return NativeCrypto.SSL_get_timeout(this.ssl, this);
    }

    byte[] getTlsChannelId() throws SSLException {
        return NativeCrypto.SSL_get_tls_channel_id(this.ssl, this);
    }

    byte[] getTlsUnique() {
        return NativeCrypto.SSL_get_tls_unique(this.ssl, this);
    }

    String getVersion() {
        return NativeCrypto.SSL_get_version(this.ssl, this);
    }

    void initialize(String string, OpenSSLKey openSSLKey) throws IOException {
        boolean bl = this.parameters.getEnableSessionCreation();
        int n = 0;
        if (!bl) {
            NativeCrypto.SSL_set_session_creation_enabled(this.ssl, this, false);
        }
        NativeCrypto.SSL_accept_renegotiations(this.ssl, this);
        if (this.isClient()) {
            NativeCrypto.SSL_set_connect_state(this.ssl, this);
            NativeCrypto.SSL_enable_ocsp_stapling(this.ssl, this);
            if (this.parameters.isCTVerificationEnabled(string)) {
                NativeCrypto.SSL_enable_signed_cert_timestamps(this.ssl, this);
            }
        } else {
            NativeCrypto.SSL_set_accept_state(this.ssl, this);
            if (this.parameters.getOCSPResponse() != null) {
                NativeCrypto.SSL_enable_ocsp_stapling(this.ssl, this);
            }
        }
        if (this.parameters.getEnabledProtocols().length == 0 && this.parameters.isEnabledProtocolsFiltered) {
            throw new SSLHandshakeException("No enabled protocols; SSLv3 is no longer supported and was filtered from the list");
        }
        NativeCrypto.setEnabledProtocols(this.ssl, this, this.parameters.enabledProtocols);
        NativeCrypto.setEnabledCipherSuites(this.ssl, this, this.parameters.enabledCipherSuites, this.parameters.enabledProtocols);
        if (this.parameters.applicationProtocols.length > 0) {
            NativeCrypto.setApplicationProtocols(this.ssl, this, this.isClient(), this.parameters.applicationProtocols);
        }
        if (!this.isClient() && this.parameters.applicationProtocolSelector != null) {
            NativeCrypto.setApplicationProtocolSelector(this.ssl, this, this.parameters.applicationProtocolSelector);
        }
        if (!this.isClient()) {
            Object object;
            Object object2 = new HashSet<Object>();
            Object object3 = NativeCrypto.SSL_get_ciphers(this.ssl, this);
            int n2 = ((long[])object3).length;
            while (n < n2) {
                object = SSLUtils.getServerX509KeyType(object3[n]);
                if (object != null) {
                    object2.add(object);
                }
                ++n;
            }
            object = this.parameters.getX509KeyManager();
            if (object != null) {
                object2 = object2.iterator();
                while (object2.hasNext()) {
                    object3 = (String)object2.next();
                    try {
                        this.setCertificate(this.aliasChooser.chooseServerAlias((X509KeyManager)object, (String)object3));
                    }
                    catch (CertificateEncodingException certificateEncodingException) {
                        throw new IOException(certificateEncodingException);
                    }
                }
            }
            NativeCrypto.SSL_set_options(this.ssl, this, 0x400000L);
            if (this.parameters.sctExtension != null) {
                NativeCrypto.SSL_set_signed_cert_timestamp_list(this.ssl, this, this.parameters.sctExtension);
            }
            if (this.parameters.ocspResponse != null) {
                NativeCrypto.SSL_set_ocsp_response(this.ssl, this, this.parameters.ocspResponse);
            }
        }
        this.enablePSKKeyManagerIfRequested();
        if (this.parameters.useSessionTickets) {
            NativeCrypto.SSL_clear_options(this.ssl, this, 16384L);
        } else {
            NativeCrypto.SSL_set_options(this.ssl, this, NativeCrypto.SSL_get_options(this.ssl, this) | 16384L);
        }
        if (this.parameters.getUseSni() && AddressUtils.isValidSniHostname(string)) {
            NativeCrypto.SSL_set_tlsext_host_name(this.ssl, this, string);
        }
        NativeCrypto.SSL_set_mode(this.ssl, this, 256L);
        this.setCertificateValidation();
        this.setTlsChannelId(openSSLKey);
    }

    void interrupt() {
        NativeCrypto.SSL_interrupt(this.ssl, this);
    }

    boolean isClosed() {
        boolean bl = this.ssl == 0L;
        return bl;
    }

    BioWrapper newBio() {
        try {
            BioWrapper bioWrapper = new BioWrapper();
            return bioWrapper;
        }
        catch (SSLException sSLException) {
            throw new RuntimeException(sSLException);
        }
    }

    void offerToResumeSession(long l) throws SSLException {
        NativeCrypto.SSL_set_session(this.ssl, this, l);
    }

    int read(FileDescriptor object, byte[] arrby, int n, int n2, int n3) throws IOException {
        this.lock.readLock().lock();
        try {
            if (!this.isClosed() && object != null && ((FileDescriptor)object).valid()) {
                n = NativeCrypto.SSL_read(this.ssl, this, (FileDescriptor)object, this.handshakeCallbacks, arrby, n, n2, n3);
                return n;
            }
            object = new SocketException("Socket is closed");
            throw object;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    int readDirectByteBuffer(long l, int n) throws IOException, CertificateException {
        this.lock.readLock().lock();
        try {
            n = NativeCrypto.ENGINE_SSL_read_direct(this.ssl, this, l, n, this.handshakeCallbacks);
            return n;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    int serverPSKKeyRequested(String arrby, String string, byte[] arrby2) {
        PSKKeyManager pSKKeyManager = this.parameters.getPSKKeyManager();
        if (pSKKeyManager == null) {
            return 0;
        }
        if ((arrby = this.pskCallbacks.getPSKKey(pSKKeyManager, (String)arrby, string).getEncoded()) == null) {
            return 0;
        }
        if (arrby.length > arrby2.length) {
            return 0;
        }
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        return arrby.length;
    }

    void setCertificate(String object) throws CertificateEncodingException, SSLException {
        if (object == null) {
            return;
        }
        byte[][] arrarrby = this.parameters.getX509KeyManager();
        if (arrarrby == null) {
            return;
        }
        PrivateKey privateKey = arrarrby.getPrivateKey((String)object);
        if (privateKey == null) {
            return;
        }
        this.localCertificates = arrarrby.getCertificateChain((String)object);
        object = this.localCertificates;
        if (object == null) {
            return;
        }
        int n = ((X509Certificate[])object).length;
        object = n > 0 ? object[0].getPublicKey() : null;
        arrarrby = new byte[n][];
        for (int i = 0; i < n; ++i) {
            arrarrby[i] = this.localCertificates[i].getEncoded();
        }
        try {
            object = OpenSSLKey.fromPrivateKeyForTLSStackOnly(privateKey, (PublicKey)object);
        }
        catch (InvalidKeyException invalidKeyException) {
            throw new SSLException(invalidKeyException);
        }
        NativeCrypto.setLocalCertsAndPrivateKey(this.ssl, this, arrarrby, ((OpenSSLKey)object).getNativeRef());
    }

    void setTimeout(long l) {
        NativeCrypto.SSL_set_timeout(this.ssl, this, l);
    }

    void shutdown() throws IOException {
        NativeCrypto.ENGINE_SSL_shutdown(this.ssl, this, this.handshakeCallbacks);
    }

    void shutdown(FileDescriptor fileDescriptor) throws IOException {
        NativeCrypto.SSL_shutdown(this.ssl, this, fileDescriptor, this.handshakeCallbacks);
    }

    boolean wasShutdownReceived() {
        boolean bl = (NativeCrypto.SSL_get_shutdown(this.ssl, this) & 2) != 0;
        return bl;
    }

    boolean wasShutdownSent() {
        int n = NativeCrypto.SSL_get_shutdown(this.ssl, this);
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    void write(FileDescriptor object, byte[] arrby, int n, int n2, int n3) throws IOException {
        this.lock.readLock().lock();
        try {
            if (!this.isClosed() && object != null && ((FileDescriptor)object).valid()) {
                NativeCrypto.SSL_write(this.ssl, this, (FileDescriptor)object, this.handshakeCallbacks, arrby, n, n2, n3);
                return;
            }
            object = new SocketException("Socket is closed");
            throw object;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    int writeDirectByteBuffer(long l, int n) throws IOException {
        this.lock.readLock().lock();
        try {
            n = NativeCrypto.ENGINE_SSL_write_direct(this.ssl, this, l, n, this.handshakeCallbacks);
            return n;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    final class BioWrapper {
        private volatile long bio;

        private BioWrapper() throws SSLException {
            this.bio = NativeCrypto.SSL_BIO_new(NativeSsl.this.ssl, NativeSsl.this);
        }

        void close() {
            long l = this.bio;
            this.bio = 0L;
            NativeCrypto.BIO_free_all(l);
        }

        int getPendingWrittenBytes() {
            if (this.bio != 0L) {
                return NativeCrypto.SSL_pending_written_bytes_in_BIO(this.bio);
            }
            return 0;
        }

        int readDirectByteBuffer(long l, int n) throws IOException {
            return NativeCrypto.ENGINE_SSL_read_BIO_direct(NativeSsl.this.ssl, NativeSsl.this, this.bio, l, n, NativeSsl.this.handshakeCallbacks);
        }

        int writeDirectByteBuffer(long l, int n) throws IOException {
            return NativeCrypto.ENGINE_SSL_write_BIO_direct(NativeSsl.this.ssl, NativeSsl.this, this.bio, l, n, NativeSsl.this.handshakeCallbacks);
        }
    }

}

