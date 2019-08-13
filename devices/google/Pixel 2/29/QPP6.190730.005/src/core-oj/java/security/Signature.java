/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import sun.security.jca.GetInstance;
import sun.security.jca.Providers;
import sun.security.jca.ServiceId;

public abstract class Signature
extends SignatureSpi {
    private static final String RSA_CIPHER = "RSA/ECB/PKCS1Padding";
    private static final String RSA_SIGNATURE = "NONEwithRSA";
    protected static final int SIGN = 2;
    protected static final int UNINITIALIZED = 0;
    protected static final int VERIFY = 3;
    private static final List<ServiceId> rsaIds = Arrays.asList(new ServiceId("Signature", "NONEwithRSA"), new ServiceId("Cipher", "RSA/ECB/PKCS1Padding"), new ServiceId("Cipher", "RSA/ECB"), new ServiceId("Cipher", "RSA//PKCS1Padding"), new ServiceId("Cipher", "RSA"));
    private static final Map<String, Boolean> signatureInfo = new ConcurrentHashMap<String, Boolean>();
    private String algorithm;
    Provider provider;
    protected int state = 0;

    static {
        Boolean bl = Boolean.TRUE;
        signatureInfo.put("sun.security.provider.DSA$RawDSA", bl);
        signatureInfo.put("sun.security.provider.DSA$SHA1withDSA", bl);
        signatureInfo.put("sun.security.rsa.RSASignature$MD2withRSA", bl);
        signatureInfo.put("sun.security.rsa.RSASignature$MD5withRSA", bl);
        signatureInfo.put("sun.security.rsa.RSASignature$SHA1withRSA", bl);
        signatureInfo.put("sun.security.rsa.RSASignature$SHA256withRSA", bl);
        signatureInfo.put("sun.security.rsa.RSASignature$SHA384withRSA", bl);
        signatureInfo.put("sun.security.rsa.RSASignature$SHA512withRSA", bl);
        signatureInfo.put("com.sun.net.ssl.internal.ssl.RSASignature", bl);
        signatureInfo.put("sun.security.pkcs11.P11Signature", bl);
    }

    protected Signature(String string) {
        this.algorithm = string;
    }

    public static Signature getInstance(String string) throws NoSuchAlgorithmException {
        List<Provider.Service> list = string.equalsIgnoreCase(RSA_SIGNATURE) ? GetInstance.getServices(rsaIds) : GetInstance.getServices("Signature", string);
        if ((list = list.iterator()).hasNext()) {
            do {
                Object object;
                if (Signature.isSpi((Provider.Service)(object = (Provider.Service)list.next()))) {
                    return new Delegate(string);
                }
                try {
                    object = Signature.getInstance(GetInstance.getInstance((Provider.Service)object, SignatureSpi.class), string);
                    return object;
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    if (list.hasNext()) continue;
                    throw noSuchAlgorithmException;
                }
                break;
            } while (true);
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append(string);
        ((StringBuilder)((Object)list)).append(" Signature not available");
        throw new NoSuchAlgorithmException(((StringBuilder)((Object)list)).toString());
    }

    public static Signature getInstance(String object, String string) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (((String)object).equalsIgnoreCase(RSA_SIGNATURE)) {
            if (string != null && string.length() != 0) {
                object = Security.getProvider(string);
                if (object != null) {
                    return Signature.getInstanceRSA((Provider)object);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("no such provider: ");
                ((StringBuilder)object).append(string);
                throw new NoSuchProviderException(((StringBuilder)object).toString());
            }
            throw new IllegalArgumentException("missing provider");
        }
        Providers.checkBouncyCastleDeprecation(string, "Signature", (String)object);
        return Signature.getInstance(GetInstance.getInstance("Signature", SignatureSpi.class, (String)object, string), (String)object);
    }

    public static Signature getInstance(String string, Provider provider) throws NoSuchAlgorithmException {
        if (string.equalsIgnoreCase(RSA_SIGNATURE)) {
            if (provider != null) {
                return Signature.getInstanceRSA(provider);
            }
            throw new IllegalArgumentException("missing provider");
        }
        Providers.checkBouncyCastleDeprecation(provider, "Signature", string);
        return Signature.getInstance(GetInstance.getInstance("Signature", SignatureSpi.class, string, provider), string);
    }

    private static Signature getInstance(GetInstance.Instance instance, String object) {
        if (instance.impl instanceof Signature) {
            Signature signature = (Signature)instance.impl;
            signature.algorithm = object;
            object = signature;
        } else {
            object = new Delegate((SignatureSpi)instance.impl, (String)object);
        }
        ((Signature)object).provider = instance.provider;
        return object;
    }

    private static Signature getInstanceRSA(Provider provider) throws NoSuchAlgorithmException {
        Object object = provider.getService("Signature", RSA_SIGNATURE);
        if (object != null) {
            return Signature.getInstance(GetInstance.getInstance((Provider.Service)object, SignatureSpi.class), RSA_SIGNATURE);
        }
        try {
            object = Cipher.getInstance(RSA_CIPHER, provider);
            CipherAdapter cipherAdapter = new CipherAdapter((Cipher)object);
            object = new Delegate(cipherAdapter, RSA_SIGNATURE);
            return object;
        }
        catch (GeneralSecurityException generalSecurityException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("no such algorithm: NONEwithRSA for provider ");
            ((StringBuilder)object).append(provider.getName());
            throw new NoSuchAlgorithmException(((StringBuilder)object).toString(), generalSecurityException);
        }
    }

    private static boolean isSpi(Provider.Service object) {
        Boolean bl;
        boolean bl2 = ((Provider.Service)object).getType().equals("Cipher");
        boolean bl3 = true;
        if (bl2) {
            return true;
        }
        String string = ((Provider.Service)object).getClassName();
        Boolean bl4 = bl = signatureInfo.get(string);
        if (bl == null) {
            block5 : {
                try {
                    object = ((Provider.Service)object).newInstance(null);
                    if (object instanceof SignatureSpi && !(object instanceof Signature)) break block5;
                    bl3 = false;
                }
                catch (Exception exception) {
                    return false;
                }
            }
            bl4 = bl3;
            signatureInfo.put(string, bl4);
        }
        return bl4;
    }

    void chooseFirstProvider() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public SignatureSpi getCurrentSpi() {
        return null;
    }

    @Deprecated
    public final Object getParameter(String string) throws InvalidParameterException {
        return this.engineGetParameter(string);
    }

    public final AlgorithmParameters getParameters() {
        return this.engineGetParameters();
    }

    public final Provider getProvider() {
        this.chooseFirstProvider();
        return this.provider;
    }

    public final void initSign(PrivateKey privateKey) throws InvalidKeyException {
        this.engineInitSign(privateKey);
        this.state = 2;
    }

    public final void initSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
        this.engineInitSign(privateKey, secureRandom);
        this.state = 2;
    }

    public final void initVerify(PublicKey publicKey) throws InvalidKeyException {
        this.engineInitVerify(publicKey);
        this.state = 3;
    }

    public final void initVerify(Certificate certificate) throws InvalidKeyException {
        Set<String> set;
        boolean[] arrbl;
        if (certificate instanceof X509Certificate && (set = (arrbl = (boolean[])certificate).getCriticalExtensionOIDs()) != null && !set.isEmpty() && set.contains("2.5.29.15") && (arrbl = arrbl.getKeyUsage()) != null && !arrbl[0]) {
            throw new InvalidKeyException("Wrong key usage");
        }
        this.engineInitVerify(certificate.getPublicKey());
        this.state = 3;
    }

    @Deprecated
    public final void setParameter(String string, Object object) throws InvalidParameterException {
        this.engineSetParameter(string, object);
    }

    public final void setParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        this.engineSetParameter(algorithmParameterSpec);
    }

    public final int sign(byte[] arrby, int n, int n2) throws SignatureException {
        if (arrby != null) {
            if (n >= 0 && n2 >= 0) {
                if (arrby.length - n >= n2) {
                    if (this.state == 2) {
                        return this.engineSign(arrby, n, n2);
                    }
                    throw new SignatureException("object not initialized for signing");
                }
                throw new IllegalArgumentException("Output buffer too small for specified offset and length");
            }
            throw new IllegalArgumentException("offset or len is less than 0");
        }
        throw new IllegalArgumentException("No output buffer given");
    }

    public final byte[] sign() throws SignatureException {
        if (this.state == 2) {
            return this.engineSign();
        }
        throw new SignatureException("object not initialized for signing");
    }

    public String toString() {
        String string = "";
        int n = this.state;
        if (n != 0) {
            if (n != 2) {
                if (n == 3) {
                    string = "<initialized for verifying>";
                }
            } else {
                string = "<initialized for signing>";
            }
        } else {
            string = "<not initialized>";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Signature object: ");
        stringBuilder.append(this.getAlgorithm());
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    public final void update(byte by) throws SignatureException {
        int n = this.state;
        if (n != 3 && n != 2) {
            throw new SignatureException("object not initialized for signature or verification");
        }
        this.engineUpdate(by);
    }

    public final void update(ByteBuffer byteBuffer) throws SignatureException {
        int n = this.state;
        if (n != 2 && n != 3) {
            throw new SignatureException("object not initialized for signature or verification");
        }
        if (byteBuffer != null) {
            this.engineUpdate(byteBuffer);
            return;
        }
        throw new NullPointerException();
    }

    public final void update(byte[] arrby) throws SignatureException {
        this.update(arrby, 0, arrby.length);
    }

    public final void update(byte[] arrby, int n, int n2) throws SignatureException {
        int n3 = this.state;
        if (n3 != 2 && n3 != 3) {
            throw new SignatureException("object not initialized for signature or verification");
        }
        if (arrby != null) {
            if (n >= 0 && n2 >= 0) {
                if (arrby.length - n >= n2) {
                    this.engineUpdate(arrby, n, n2);
                    return;
                }
                throw new IllegalArgumentException("data too small for specified offset and length");
            }
            throw new IllegalArgumentException("off or len is less than 0");
        }
        throw new IllegalArgumentException("data is null");
    }

    public final boolean verify(byte[] arrby) throws SignatureException {
        if (this.state == 3) {
            return this.engineVerify(arrby);
        }
        throw new SignatureException("object not initialized for verification");
    }

    public final boolean verify(byte[] arrby, int n, int n2) throws SignatureException {
        if (this.state == 3) {
            if (arrby != null) {
                if (n >= 0 && n2 >= 0) {
                    if (arrby.length - n >= n2) {
                        return this.engineVerify(arrby, n, n2);
                    }
                    throw new IllegalArgumentException("signature too small for specified offset and length");
                }
                throw new IllegalArgumentException("offset or length is less than 0");
            }
            throw new IllegalArgumentException("signature is null");
        }
        throw new SignatureException("object not initialized for verification");
    }

    private static class CipherAdapter
    extends SignatureSpi {
        private final Cipher cipher;
        private ByteArrayOutputStream data;

        CipherAdapter(Cipher cipher) {
            this.cipher = cipher;
        }

        @Override
        protected Object engineGetParameter(String string) throws InvalidParameterException {
            throw new InvalidParameterException("Parameters not supported");
        }

        @Override
        protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
            this.cipher.init(1, privateKey);
            this.data = null;
        }

        @Override
        protected void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
            this.cipher.init(1, (Key)privateKey, secureRandom);
            this.data = null;
        }

        @Override
        protected void engineInitVerify(PublicKey object) throws InvalidKeyException {
            this.cipher.init(2, (Key)object);
            object = this.data;
            if (object == null) {
                this.data = new ByteArrayOutputStream(128);
            } else {
                ((ByteArrayOutputStream)object).reset();
            }
        }

        @Override
        protected void engineSetParameter(String string, Object object) throws InvalidParameterException {
            throw new InvalidParameterException("Parameters not supported");
        }

        @Override
        protected byte[] engineSign() throws SignatureException {
            try {
                byte[] arrby = this.cipher.doFinal();
                return arrby;
            }
            catch (BadPaddingException badPaddingException) {
                throw new SignatureException("doFinal() failed", badPaddingException);
            }
            catch (IllegalBlockSizeException illegalBlockSizeException) {
                throw new SignatureException("doFinal() failed", illegalBlockSizeException);
            }
        }

        @Override
        protected void engineUpdate(byte by) throws SignatureException {
            this.engineUpdate(new byte[]{by}, 0, 1);
        }

        @Override
        protected void engineUpdate(byte[] arrby, int n, int n2) throws SignatureException {
            ByteArrayOutputStream byteArrayOutputStream = this.data;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.write(arrby, n, n2);
                return;
            }
            if ((arrby = this.cipher.update(arrby, n, n2)) != null && arrby.length != 0) {
                throw new SignatureException("Cipher unexpectedly returned data");
            }
        }

        @Override
        protected boolean engineVerify(byte[] arrby) throws SignatureException {
            try {
                arrby = this.cipher.doFinal(arrby);
                byte[] arrby2 = this.data.toByteArray();
                this.data.reset();
                boolean bl = MessageDigest.isEqual(arrby, arrby2);
                return bl;
            }
            catch (IllegalBlockSizeException illegalBlockSizeException) {
                throw new SignatureException("doFinal() failed", illegalBlockSizeException);
            }
            catch (BadPaddingException badPaddingException) {
                return false;
            }
        }
    }

    private static class Delegate
    extends Signature {
        private static final int I_PRIV = 2;
        private static final int I_PRIV_SR = 3;
        private static final int I_PUB = 1;
        private static int warnCount = 10;
        private final Object lock;
        private SignatureSpi sigSpi;

        Delegate(String string) {
            super(string);
            this.lock = new Object();
        }

        Delegate(SignatureSpi signatureSpi, String string) {
            super(string);
            this.sigSpi = signatureSpi;
            this.lock = null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void chooseProvider(int n, Key object, SecureRandom serializable) throws InvalidKeyException {
            Object object2 = this.lock;
            synchronized (object2) {
                if (this.sigSpi != null && object == null) {
                    this.init(this.sigSpi, n, (Key)object, (SecureRandom)serializable);
                    return;
                }
                Object object3 = null;
                List<Provider.Service> list = this.algorithm.equalsIgnoreCase(Signature.RSA_SIGNATURE) ? GetInstance.getServices(rsaIds) : GetInstance.getServices("Signature", this.algorithm);
                Iterator<Provider.Service> iterator = list.iterator();
                list = object3;
                while (iterator.hasNext()) {
                    boolean bl;
                    Provider.Service service = iterator.next();
                    if (!service.supportsParameter(object) || !(bl = Signature.isSpi(service))) continue;
                    try {
                        object3 = Delegate.newInstance(service);
                        this.init((SignatureSpi)object3, n, (Key)object, (SecureRandom)serializable);
                        this.provider = service.getProvider();
                        this.sigSpi = object3;
                        return;
                    }
                    catch (Exception exception) {
                        object3 = list;
                        if (list == null) {
                            object3 = exception;
                        }
                        if (object3 instanceof InvalidKeyException) {
                            throw (InvalidKeyException)object3;
                        }
                        list = object3;
                    }
                }
                if (list instanceof InvalidKeyException) {
                    throw (InvalidKeyException)((Object)list);
                }
                if (list instanceof RuntimeException) {
                    throw (RuntimeException)((Object)list);
                }
                object = object != null ? object.getClass().getName() : "(null)";
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("No installed provider supports this key: ");
                ((StringBuilder)serializable).append((String)object);
                object3 = new InvalidKeyException(((StringBuilder)serializable).toString(), (Throwable)((Object)list));
                throw object3;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void init(SignatureSpi object, int n, Key key, SecureRandom secureRandom) throws InvalidKeyException {
            if (n == 1) {
                ((SignatureSpi)object).engineInitVerify((PublicKey)key);
                return;
            }
            if (n == 2) {
                ((SignatureSpi)object).engineInitSign((PrivateKey)key);
                return;
            }
            if (n == 3) {
                ((SignatureSpi)object).engineInitSign((PrivateKey)key, secureRandom);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Internal error: ");
            ((StringBuilder)object).append(n);
            throw new AssertionError((Object)((StringBuilder)object).toString());
        }

        private static SignatureSpi newInstance(Provider.Service object) throws NoSuchAlgorithmException {
            if (((Provider.Service)object).getType().equals("Cipher")) {
                try {
                    object = new CipherAdapter(Cipher.getInstance(Signature.RSA_CIPHER, ((Provider.Service)object).getProvider()));
                    return object;
                }
                catch (NoSuchPaddingException noSuchPaddingException) {
                    throw new NoSuchAlgorithmException(noSuchPaddingException);
                }
            }
            Object object2 = ((Provider.Service)object).newInstance(null);
            if (object2 instanceof SignatureSpi) {
                return (SignatureSpi)object2;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Not a SignatureSpi: ");
            ((StringBuilder)object).append(object2.getClass().getName());
            throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        void chooseFirstProvider() {
            if (this.sigSpi != null) {
                return;
            }
            Object object = this.lock;
            synchronized (object) {
                if (this.sigSpi != null) {
                    return;
                }
                Object object2 = null;
                List<Provider.Service> list = this.algorithm.equalsIgnoreCase(Signature.RSA_SIGNATURE) ? GetInstance.getServices(rsaIds) : GetInstance.getServices("Signature", this.algorithm);
                Iterator<Provider.Service> iterator = list.iterator();
                list = object2;
                while (iterator.hasNext()) {
                    object2 = iterator.next();
                    boolean bl = Signature.isSpi((Provider.Service)object2);
                    if (!bl) continue;
                    try {
                        this.sigSpi = Delegate.newInstance((Provider.Service)object2);
                        this.provider = ((Provider.Service)object2).getProvider();
                        return;
                    }
                    catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    }
                }
                object2 = new ProviderException("Could not construct SignatureSpi instance");
                if (list != null) {
                    ((Throwable)object2).initCause((Throwable)((Object)list));
                }
                throw object2;
            }
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            this.chooseFirstProvider();
            SignatureSpi signatureSpi = this.sigSpi;
            if (signatureSpi instanceof Cloneable) {
                signatureSpi = new Delegate((SignatureSpi)signatureSpi.clone(), this.algorithm);
                ((Signature)signatureSpi).provider = this.provider;
                return signatureSpi;
            }
            throw new CloneNotSupportedException();
        }

        @Override
        protected Object engineGetParameter(String string) throws InvalidParameterException {
            this.chooseFirstProvider();
            return this.sigSpi.engineGetParameter(string);
        }

        @Override
        protected AlgorithmParameters engineGetParameters() {
            this.chooseFirstProvider();
            return this.sigSpi.engineGetParameters();
        }

        @Override
        protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
            if (this.sigSpi != null && (this.lock == null || privateKey == null)) {
                this.sigSpi.engineInitSign(privateKey);
            } else {
                this.chooseProvider(2, privateKey, null);
            }
        }

        @Override
        protected void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
            if (this.sigSpi != null && (this.lock == null || privateKey == null)) {
                this.sigSpi.engineInitSign(privateKey, secureRandom);
            } else {
                this.chooseProvider(3, privateKey, secureRandom);
            }
        }

        @Override
        protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
            if (this.sigSpi != null && (this.lock == null || publicKey == null)) {
                this.sigSpi.engineInitVerify(publicKey);
            } else {
                this.chooseProvider(1, publicKey, null);
            }
        }

        @Override
        protected void engineSetParameter(String string, Object object) throws InvalidParameterException {
            this.chooseFirstProvider();
            this.sigSpi.engineSetParameter(string, object);
        }

        @Override
        protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
            this.chooseFirstProvider();
            this.sigSpi.engineSetParameter(algorithmParameterSpec);
        }

        @Override
        protected int engineSign(byte[] arrby, int n, int n2) throws SignatureException {
            this.chooseFirstProvider();
            return this.sigSpi.engineSign(arrby, n, n2);
        }

        @Override
        protected byte[] engineSign() throws SignatureException {
            this.chooseFirstProvider();
            return this.sigSpi.engineSign();
        }

        @Override
        protected void engineUpdate(byte by) throws SignatureException {
            this.chooseFirstProvider();
            this.sigSpi.engineUpdate(by);
        }

        @Override
        protected void engineUpdate(ByteBuffer byteBuffer) {
            this.chooseFirstProvider();
            this.sigSpi.engineUpdate(byteBuffer);
        }

        @Override
        protected void engineUpdate(byte[] arrby, int n, int n2) throws SignatureException {
            this.chooseFirstProvider();
            this.sigSpi.engineUpdate(arrby, n, n2);
        }

        @Override
        protected boolean engineVerify(byte[] arrby) throws SignatureException {
            this.chooseFirstProvider();
            return this.sigSpi.engineVerify(arrby);
        }

        @Override
        protected boolean engineVerify(byte[] arrby, int n, int n2) throws SignatureException {
            this.chooseFirstProvider();
            return this.sigSpi.engineVerify(arrby, n, n2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SignatureSpi getCurrentSpi() {
            Object object = this.lock;
            if (object == null) {
                return this.sigSpi;
            }
            synchronized (object) {
                return this.sigSpi;
            }
        }
    }

}

