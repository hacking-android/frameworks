/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;

public class KeyStore {
    private static final String KEYSTORE_TYPE = "keystore.type";
    private boolean initialized = false;
    private KeyStoreSpi keyStoreSpi;
    private Provider provider;
    private String type;

    protected KeyStore(KeyStoreSpi keyStoreSpi, Provider provider, String string) {
        this.keyStoreSpi = keyStoreSpi;
        this.provider = provider;
        this.type = string;
    }

    public static final String getDefaultType() {
        String string;
        String string2 = string = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return Security.getProperty(KeyStore.KEYSTORE_TYPE);
            }
        });
        if (string == null) {
            string2 = "jks";
        }
        return string2;
    }

    public static KeyStore getInstance(String string) throws KeyStoreException {
        try {
            Object object = Security.getImpl(string, "KeyStore", (String)null);
            object = new KeyStore((KeyStoreSpi)object[0], (Provider)object[1], string);
            return object;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" not found");
            throw new KeyStoreException(stringBuilder.toString(), noSuchProviderException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" not found");
            throw new KeyStoreException(stringBuilder.toString(), noSuchAlgorithmException);
        }
    }

    public static KeyStore getInstance(String string, String object) throws KeyStoreException, NoSuchProviderException {
        if (object != null && ((String)object).length() != 0) {
            try {
                object = Security.getImpl(string, "KeyStore", (String)object);
                object = new KeyStore((KeyStoreSpi)object[0], (Provider)object[1], string);
                return object;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(" not found");
                throw new KeyStoreException(((StringBuilder)object).toString(), noSuchAlgorithmException);
            }
        }
        throw new IllegalArgumentException("missing provider");
    }

    public static KeyStore getInstance(String string, Provider object) throws KeyStoreException {
        if (object != null) {
            try {
                object = Security.getImpl(string, "KeyStore", (Provider)object);
                object = new KeyStore((KeyStoreSpi)object[0], (Provider)object[1], string);
                return object;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(" not found");
                throw new KeyStoreException(((StringBuilder)object).toString(), noSuchAlgorithmException);
            }
        }
        throw new IllegalArgumentException("missing provider");
    }

    public final Enumeration<String> aliases() throws KeyStoreException {
        if (this.initialized) {
            return this.keyStoreSpi.engineAliases();
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final boolean containsAlias(String string) throws KeyStoreException {
        if (this.initialized) {
            return this.keyStoreSpi.engineContainsAlias(string);
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final void deleteEntry(String string) throws KeyStoreException {
        if (this.initialized) {
            this.keyStoreSpi.engineDeleteEntry(string);
            return;
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final boolean entryInstanceOf(String string, Class<? extends Entry> class_) throws KeyStoreException {
        if (string != null && class_ != null) {
            if (this.initialized) {
                return this.keyStoreSpi.engineEntryInstanceOf(string, class_);
            }
            throw new KeyStoreException("Uninitialized keystore");
        }
        throw new NullPointerException("invalid null input");
    }

    public final Certificate getCertificate(String string) throws KeyStoreException {
        if (this.initialized) {
            return this.keyStoreSpi.engineGetCertificate(string);
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final String getCertificateAlias(Certificate certificate) throws KeyStoreException {
        if (this.initialized) {
            return this.keyStoreSpi.engineGetCertificateAlias(certificate);
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final Certificate[] getCertificateChain(String string) throws KeyStoreException {
        if (this.initialized) {
            return this.keyStoreSpi.engineGetCertificateChain(string);
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final Date getCreationDate(String string) throws KeyStoreException {
        if (this.initialized) {
            return this.keyStoreSpi.engineGetCreationDate(string);
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final Entry getEntry(String string, ProtectionParameter protectionParameter) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
        if (string != null) {
            if (this.initialized) {
                return this.keyStoreSpi.engineGetEntry(string, protectionParameter);
            }
            throw new KeyStoreException("Uninitialized keystore");
        }
        throw new NullPointerException("invalid null input");
    }

    public final Key getKey(String string, char[] arrc) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        if (this.initialized) {
            return this.keyStoreSpi.engineGetKey(string, arrc);
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final String getType() {
        return this.type;
    }

    public final boolean isCertificateEntry(String string) throws KeyStoreException {
        if (this.initialized) {
            return this.keyStoreSpi.engineIsCertificateEntry(string);
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final boolean isKeyEntry(String string) throws KeyStoreException {
        if (this.initialized) {
            return this.keyStoreSpi.engineIsKeyEntry(string);
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final void load(InputStream inputStream, char[] arrc) throws IOException, NoSuchAlgorithmException, CertificateException {
        this.keyStoreSpi.engineLoad(inputStream, arrc);
        this.initialized = true;
    }

    public final void load(LoadStoreParameter loadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
        this.keyStoreSpi.engineLoad(loadStoreParameter);
        this.initialized = true;
    }

    public final void setCertificateEntry(String string, Certificate certificate) throws KeyStoreException {
        if (this.initialized) {
            this.keyStoreSpi.engineSetCertificateEntry(string, certificate);
            return;
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final void setEntry(String string, Entry entry, ProtectionParameter protectionParameter) throws KeyStoreException {
        if (string != null && entry != null) {
            if (this.initialized) {
                this.keyStoreSpi.engineSetEntry(string, entry, protectionParameter);
                return;
            }
            throw new KeyStoreException("Uninitialized keystore");
        }
        throw new NullPointerException("invalid null input");
    }

    public final void setKeyEntry(String string, Key key, char[] arrc, Certificate[] arrcertificate) throws KeyStoreException {
        if (this.initialized) {
            if (key instanceof PrivateKey && (arrcertificate == null || arrcertificate.length == 0)) {
                throw new IllegalArgumentException("Private key must be accompanied by certificate chain");
            }
            this.keyStoreSpi.engineSetKeyEntry(string, key, arrc, arrcertificate);
            return;
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final void setKeyEntry(String string, byte[] arrby, Certificate[] arrcertificate) throws KeyStoreException {
        if (this.initialized) {
            this.keyStoreSpi.engineSetKeyEntry(string, arrby, arrcertificate);
            return;
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final int size() throws KeyStoreException {
        if (this.initialized) {
            return this.keyStoreSpi.engineSize();
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final void store(OutputStream outputStream, char[] arrc) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        if (this.initialized) {
            this.keyStoreSpi.engineStore(outputStream, arrc);
            return;
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public final void store(LoadStoreParameter loadStoreParameter) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        if (this.initialized) {
            this.keyStoreSpi.engineStore(loadStoreParameter);
            return;
        }
        throw new KeyStoreException("Uninitialized keystore");
    }

    public static abstract class Builder {
        static final int MAX_CALLBACK_TRIES = 3;

        protected Builder() {
        }

        public static Builder newInstance(String charSequence, Provider provider, File file, ProtectionParameter protectionParameter) {
            if (charSequence != null && file != null && protectionParameter != null) {
                if (!(protectionParameter instanceof PasswordProtection) && !(protectionParameter instanceof CallbackHandlerProtection)) {
                    throw new IllegalArgumentException("Protection must be PasswordProtection or CallbackHandlerProtection");
                }
                if (file.isFile()) {
                    return new FileBuilder((String)charSequence, provider, file, protectionParameter, AccessController.getContext());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("File does not exist or it does not refer to a normal file: ");
                ((StringBuilder)charSequence).append(file);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            throw new NullPointerException();
        }

        public static Builder newInstance(final String string, final Provider provider, final ProtectionParameter protectionParameter) {
            if (string != null && protectionParameter != null) {
                return new Builder(AccessController.getContext()){
                    private final PrivilegedExceptionAction<KeyStore> action = new PrivilegedExceptionAction<KeyStore>(){

                        /*
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        @Override
                        public KeyStore run() throws Exception {
                            KeyStore keyStore = provider == null ? KeyStore.getInstance(string) : KeyStore.getInstance(string, provider);
                            SimpleLoadStoreParameter simpleLoadStoreParameter = new SimpleLoadStoreParameter(protectionParameter);
                            if (!(protectionParameter instanceof CallbackHandlerProtection)) {
                                keyStore.load(simpleLoadStoreParameter);
                            } else {
                                int n = 0;
                                do {
                                    ++n;
                                    try {
                                        keyStore.load(simpleLoadStoreParameter);
                                    }
                                    catch (IOException iOException) {
                                        if (iOException.getCause() instanceof UnrecoverableKeyException) {
                                            if (n < 3) continue;
                                            oldException = iOException;
                                        }
                                        throw iOException;
                                    }
                                    break;
                                } while (true);
                            }
                            getCalled = true;
                            return keyStore;
                        }
                    };
                    private volatile boolean getCalled;
                    private IOException oldException;
                    final /* synthetic */ AccessControlContext val$context;
                    {
                        this.val$context = accessControlContext;
                    }

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public KeyStore getKeyStore() throws KeyStoreException {
                        synchronized (this) {
                            Object object = this.oldException;
                            if (object != null) {
                                object = new KeyStoreException("Previous KeyStore instantiation failed", this.oldException);
                                throw object;
                            }
                            try {
                                return AccessController.doPrivileged(this.action, this.val$context);
                            }
                            catch (PrivilegedActionException privilegedActionException) {
                                Throwable throwable = privilegedActionException.getCause();
                                KeyStoreException keyStoreException = new KeyStoreException("KeyStore instantiation failed", throwable);
                                throw keyStoreException;
                            }
                        }
                    }

                    @Override
                    public ProtectionParameter getProtectionParameter(String string2) {
                        if (string2 != null) {
                            if (this.getCalled) {
                                return protectionParameter;
                            }
                            throw new IllegalStateException("getKeyStore() must be called first");
                        }
                        throw new NullPointerException();
                    }

                };
            }
            throw new NullPointerException();
        }

        public static Builder newInstance(final KeyStore keyStore, final ProtectionParameter protectionParameter) {
            if (keyStore != null && protectionParameter != null) {
                if (keyStore.initialized) {
                    return new Builder(){
                        private volatile boolean getCalled;

                        @Override
                        public KeyStore getKeyStore() {
                            this.getCalled = true;
                            return keyStore;
                        }

                        @Override
                        public ProtectionParameter getProtectionParameter(String string) {
                            if (string != null) {
                                if (this.getCalled) {
                                    return protectionParameter;
                                }
                                throw new IllegalStateException("getKeyStore() must be called first");
                            }
                            throw new NullPointerException();
                        }
                    };
                }
                throw new IllegalArgumentException("KeyStore not initialized");
            }
            throw new NullPointerException();
        }

        public abstract KeyStore getKeyStore() throws KeyStoreException;

        public abstract ProtectionParameter getProtectionParameter(String var1) throws KeyStoreException;

        private static final class FileBuilder
        extends Builder {
            private final AccessControlContext context;
            private final File file;
            private ProtectionParameter keyProtection;
            private KeyStore keyStore;
            private Throwable oldException;
            private ProtectionParameter protection;
            private final Provider provider;
            private final String type;

            FileBuilder(String string, Provider provider, File file, ProtectionParameter protectionParameter, AccessControlContext accessControlContext) {
                this.type = string;
                this.provider = provider;
                this.file = file;
                this.protection = protectionParameter;
                this.context = accessControlContext;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public KeyStore getKeyStore() throws KeyStoreException {
                synchronized (this) {
                    if (this.keyStore != null) {
                        return this.keyStore;
                    }
                    if (this.oldException != null) {
                        KeyStoreException keyStoreException = new KeyStoreException("Previous KeyStore instantiation failed", this.oldException);
                        throw keyStoreException;
                    }
                    PrivilegedExceptionAction<KeyStore> privilegedExceptionAction = new PrivilegedExceptionAction<KeyStore>(){

                        @Override
                        public KeyStore run() throws Exception {
                            if (!(protection instanceof CallbackHandlerProtection)) {
                                return this.run0();
                            }
                            int n = 0;
                            do {
                                ++n;
                                try {
                                    KeyStore keyStore = this.run0();
                                    return keyStore;
                                }
                                catch (IOException iOException) {
                                    if (n < 3 && iOException.getCause() instanceof UnrecoverableKeyException) continue;
                                    throw iOException;
                                }
                                break;
                            } while (true);
                        }

                        public KeyStore run0() throws Exception {
                            Object object;
                            InputStream inputStream;
                            InputStream inputStream2;
                            block27 : {
                                Object object2;
                                block26 : {
                                    block25 : {
                                        object = provider == null ? KeyStore.getInstance(type) : KeyStore.getInstance(type, provider);
                                        inputStream2 = inputStream = null;
                                        inputStream2 = inputStream;
                                        try {
                                            object2 = new FileInputStream(file);
                                            inputStream2 = inputStream = object2;
                                        }
                                        catch (Throwable throwable) {
                                            if (inputStream2 != null) {
                                                inputStream2.close();
                                            }
                                            throw throwable;
                                        }
                                        if (!(protection instanceof PasswordProtection)) break block25;
                                        inputStream2 = inputStream;
                                        object2 = ((PasswordProtection)protection).getPassword();
                                        inputStream2 = inputStream;
                                        keyProtection = protection;
                                        break block26;
                                    }
                                    inputStream2 = inputStream;
                                    object2 = ((CallbackHandlerProtection)protection).getCallbackHandler();
                                    inputStream2 = inputStream;
                                    inputStream2 = inputStream;
                                    inputStream2 = inputStream;
                                    Object object3 = new StringBuilder();
                                    inputStream2 = inputStream;
                                    ((StringBuilder)object3).append("Password for keystore ");
                                    inputStream2 = inputStream;
                                    ((StringBuilder)object3).append(file.getName());
                                    inputStream2 = inputStream;
                                    Object object4 = new PasswordCallback(((StringBuilder)object3).toString(), false);
                                    inputStream2 = inputStream;
                                    object2.handle(new Callback[]{object4});
                                    inputStream2 = inputStream;
                                    object2 = ((PasswordCallback)object4).getPassword();
                                    if (object2 == null) break block27;
                                    inputStream2 = inputStream;
                                    ((PasswordCallback)object4).clearPassword();
                                    inputStream2 = inputStream;
                                    object4 = this;
                                    inputStream2 = inputStream;
                                    inputStream2 = inputStream;
                                    object3 = new PasswordProtection((char[])object2);
                                    inputStream2 = inputStream;
                                    ((FileBuilder)object4).keyProtection = (ProtectionParameter)object3;
                                }
                                inputStream2 = inputStream;
                                ((KeyStore)object).load(inputStream, (char[])object2);
                                inputStream.close();
                                return object;
                            }
                            inputStream2 = inputStream;
                            inputStream2 = inputStream;
                            object = new KeyStoreException("No password provided");
                            inputStream2 = inputStream;
                            throw object;
                        }
                    };
                    try {
                        this.keyStore = AccessController.doPrivileged(privilegedExceptionAction, this.context);
                        return this.keyStore;
                    }
                    catch (PrivilegedActionException privilegedActionException) {
                        this.oldException = privilegedActionException.getCause();
                        KeyStoreException keyStoreException = new KeyStoreException("KeyStore instantiation failed", this.oldException);
                        throw keyStoreException;
                    }
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ProtectionParameter getProtectionParameter(String object) {
                synchronized (this) {
                    Throwable throwable2;
                    if (object != null) {
                        try {
                            if (this.keyStore != null) {
                                return this.keyProtection;
                            }
                            object = new IllegalStateException("getKeyStore() must be called first");
                            throw object;
                        }
                        catch (Throwable throwable2) {}
                    } else {
                        object = new NullPointerException();
                        throw object;
                    }
                    throw throwable2;
                }
            }

        }

    }

    public static class CallbackHandlerProtection
    implements ProtectionParameter {
        private final CallbackHandler handler;

        public CallbackHandlerProtection(CallbackHandler callbackHandler) {
            if (callbackHandler != null) {
                this.handler = callbackHandler;
                return;
            }
            throw new NullPointerException("handler must not be null");
        }

        public CallbackHandler getCallbackHandler() {
            return this.handler;
        }
    }

    public static interface Entry {
        default public Set<Attribute> getAttributes() {
            return Collections.emptySet();
        }

        public static interface Attribute {
            public String getName();

            public String getValue();
        }

    }

    public static interface LoadStoreParameter {
        public ProtectionParameter getProtectionParameter();
    }

    public static class PasswordProtection
    implements ProtectionParameter,
    Destroyable {
        private volatile boolean destroyed = false;
        private final char[] password;
        private final String protectionAlgorithm;
        private final AlgorithmParameterSpec protectionParameters;

        public PasswordProtection(char[] object) {
            object = object == null ? null : (char[])object.clone();
            this.password = object;
            this.protectionAlgorithm = null;
            this.protectionParameters = null;
        }

        public PasswordProtection(char[] object, String string, AlgorithmParameterSpec algorithmParameterSpec) {
            if (string != null) {
                object = object == null ? null : (char[])object.clone();
                this.password = object;
                this.protectionAlgorithm = string;
                this.protectionParameters = algorithmParameterSpec;
                return;
            }
            throw new NullPointerException("invalid null input");
        }

        @Override
        public void destroy() throws DestroyFailedException {
            synchronized (this) {
                this.destroyed = true;
                if (this.password != null) {
                    Arrays.fill(this.password, ' ');
                }
                return;
            }
        }

        public char[] getPassword() {
            synchronized (this) {
                block4 : {
                    if (this.destroyed) break block4;
                    char[] arrc = this.password;
                    return arrc;
                }
                IllegalStateException illegalStateException = new IllegalStateException("password has been cleared");
                throw illegalStateException;
            }
        }

        public String getProtectionAlgorithm() {
            return this.protectionAlgorithm;
        }

        public AlgorithmParameterSpec getProtectionParameters() {
            return this.protectionParameters;
        }

        @Override
        public boolean isDestroyed() {
            synchronized (this) {
                boolean bl = this.destroyed;
                return bl;
            }
        }
    }

    public static final class PrivateKeyEntry
    implements Entry {
        private final Set<Entry.Attribute> attributes;
        private final Certificate[] chain;
        private final PrivateKey privKey;

        public PrivateKeyEntry(PrivateKey privateKey, Certificate[] arrcertificate) {
            this(privateKey, arrcertificate, Collections.emptySet());
        }

        public PrivateKeyEntry(PrivateKey privateKey, Certificate[] object, Set<Entry.Attribute> set) {
            if (privateKey != null && object != null && set != null) {
                if (((Certificate[])object).length != 0) {
                    Certificate[] arrcertificate = (Certificate[])object.clone();
                    object = arrcertificate[0].getType();
                    for (int i = 1; i < arrcertificate.length; ++i) {
                        if (((String)object).equals(arrcertificate[i].getType())) {
                            continue;
                        }
                        throw new IllegalArgumentException("chain does not contain certificates of the same type");
                    }
                    if (privateKey.getAlgorithm().equals(arrcertificate[0].getPublicKey().getAlgorithm())) {
                        this.privKey = privateKey;
                        if (arrcertificate[0] instanceof X509Certificate && !(arrcertificate instanceof X509Certificate[])) {
                            this.chain = new X509Certificate[arrcertificate.length];
                            System.arraycopy(arrcertificate, 0, this.chain, 0, arrcertificate.length);
                        } else {
                            this.chain = arrcertificate;
                        }
                        this.attributes = Collections.unmodifiableSet(new HashSet<Entry.Attribute>(set));
                        return;
                    }
                    throw new IllegalArgumentException("private key algorithm does not match algorithm of public key in end entity certificate (at index 0)");
                }
                throw new IllegalArgumentException("invalid zero-length input chain");
            }
            throw new NullPointerException("invalid null input");
        }

        @Override
        public Set<Entry.Attribute> getAttributes() {
            return this.attributes;
        }

        public Certificate getCertificate() {
            return this.chain[0];
        }

        public Certificate[] getCertificateChain() {
            return (Certificate[])this.chain.clone();
        }

        public PrivateKey getPrivateKey() {
            return this.privKey;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            Certificate[] arrcertificate = new StringBuilder();
            arrcertificate.append("Private key entry and certificate chain with ");
            arrcertificate.append(this.chain.length);
            arrcertificate.append(" elements:\r\n");
            stringBuilder.append(arrcertificate.toString());
            arrcertificate = this.chain;
            int n = arrcertificate.length;
            for (int i = 0; i < n; ++i) {
                stringBuilder.append(arrcertificate[i]);
                stringBuilder.append("\r\n");
            }
            return stringBuilder.toString();
        }
    }

    public static interface ProtectionParameter {
    }

    public static final class SecretKeyEntry
    implements Entry {
        private final Set<Entry.Attribute> attributes;
        private final SecretKey sKey;

        public SecretKeyEntry(SecretKey secretKey) {
            if (secretKey != null) {
                this.sKey = secretKey;
                this.attributes = Collections.emptySet();
                return;
            }
            throw new NullPointerException("invalid null input");
        }

        public SecretKeyEntry(SecretKey secretKey, Set<Entry.Attribute> set) {
            if (secretKey != null && set != null) {
                this.sKey = secretKey;
                this.attributes = Collections.unmodifiableSet(new HashSet<Entry.Attribute>(set));
                return;
            }
            throw new NullPointerException("invalid null input");
        }

        @Override
        public Set<Entry.Attribute> getAttributes() {
            return this.attributes;
        }

        public SecretKey getSecretKey() {
            return this.sKey;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Secret key entry with algorithm ");
            stringBuilder.append(this.sKey.getAlgorithm());
            return stringBuilder.toString();
        }
    }

    static class SimpleLoadStoreParameter
    implements LoadStoreParameter {
        private final ProtectionParameter protection;

        SimpleLoadStoreParameter(ProtectionParameter protectionParameter) {
            this.protection = protectionParameter;
        }

        @Override
        public ProtectionParameter getProtectionParameter() {
            return this.protection;
        }
    }

    public static final class TrustedCertificateEntry
    implements Entry {
        private final Set<Entry.Attribute> attributes;
        private final Certificate cert;

        public TrustedCertificateEntry(Certificate certificate) {
            if (certificate != null) {
                this.cert = certificate;
                this.attributes = Collections.emptySet();
                return;
            }
            throw new NullPointerException("invalid null input");
        }

        public TrustedCertificateEntry(Certificate certificate, Set<Entry.Attribute> set) {
            if (certificate != null && set != null) {
                this.cert = certificate;
                this.attributes = Collections.unmodifiableSet(new HashSet<Entry.Attribute>(set));
                return;
            }
            throw new NullPointerException("invalid null input");
        }

        @Override
        public Set<Entry.Attribute> getAttributes() {
            return this.attributes;
        }

        public Certificate getTrustedCertificate() {
            return this.cert;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Trusted certificate entry:\r\n");
            stringBuilder.append(this.cert.toString());
            return stringBuilder.toString();
        }
    }

}

