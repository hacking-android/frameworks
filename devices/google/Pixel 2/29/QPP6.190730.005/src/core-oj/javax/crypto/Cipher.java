/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.ExemptionMechanism;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.JceSecurity;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.NullCipher;
import javax.crypto.NullCipherSpi;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import sun.security.jca.Providers;

public class Cipher {
    private static final String ATTRIBUTE_MODES = "SupportedModes";
    private static final String ATTRIBUTE_PADDINGS = "SupportedPaddings";
    public static final int DECRYPT_MODE = 2;
    public static final int ENCRYPT_MODE = 1;
    private static final String KEY_USAGE_EXTENSION_OID = "2.5.29.15";
    public static final int PRIVATE_KEY = 2;
    public static final int PUBLIC_KEY = 1;
    public static final int SECRET_KEY = 3;
    public static final int UNWRAP_MODE = 4;
    public static final int WRAP_MODE = 3;
    private ExemptionMechanism exmech;
    private boolean initialized = false;
    private int opmode = 0;
    private Provider provider;
    private CipherSpi spi;
    private final SpiAndProviderUpdater spiAndProviderUpdater;
    private final String[] tokenizedTransformation;
    private final String transformation;

    protected Cipher(CipherSpi cipherSpi, Provider provider, String string) {
        if (cipherSpi != null) {
            if (!(cipherSpi instanceof NullCipherSpi) && provider == null) {
                throw new NullPointerException("provider == null");
            }
            this.spi = cipherSpi;
            this.provider = provider;
            this.transformation = string;
            this.tokenizedTransformation = null;
            this.spiAndProviderUpdater = new SpiAndProviderUpdater(provider, cipherSpi);
            return;
        }
        throw new NullPointerException("cipherSpi == null");
    }

    private Cipher(CipherSpi cipherSpi, Provider provider, String string, String[] arrstring) {
        this.spi = cipherSpi;
        this.provider = provider;
        this.transformation = string;
        this.tokenizedTransformation = arrstring;
        this.spiAndProviderUpdater = new SpiAndProviderUpdater(provider, cipherSpi);
    }

    private void checkCipherState() {
        if (!(this instanceof NullCipher)) {
            if (this.initialized) {
                int n = this.opmode;
                if (n != 1 && n != 2) {
                    throw new IllegalStateException("Cipher not initialized for encryption/decryption");
                }
            } else {
                throw new IllegalStateException("Cipher not initialized");
            }
        }
    }

    private static void checkOpmode(int n) {
        if (n >= 1 && n <= 4) {
            return;
        }
        throw new InvalidParameterException("Invalid operation mode");
    }

    private void chooseProvider(InitType object, int n, Key serializable, AlgorithmParameterSpec algorithmParameterSpec, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            InitParams initParams = new InitParams((InitType)((Object)object), n, (Key)serializable, secureRandom, algorithmParameterSpec, algorithmParameters);
            this.spiAndProviderUpdater.updateAndGetSpiAndProvider(initParams, this.spi, this.provider);
            return;
        }
        catch (Exception exception) {
            if (!(exception instanceof InvalidKeyException)) {
                if (!(exception instanceof InvalidAlgorithmParameterException)) {
                    if (!(exception instanceof RuntimeException)) {
                        object = serializable != null ? serializable.getClass().getName() : "(null)";
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("No installed provider supports this key: ");
                        ((StringBuilder)serializable).append((String)object);
                        throw new InvalidKeyException(((StringBuilder)serializable).toString(), exception);
                    }
                    throw (RuntimeException)exception;
                }
                throw (InvalidAlgorithmParameterException)exception;
            }
            throw (InvalidKeyException)exception;
        }
    }

    static final Cipher createCipher(String string, Provider serializable) throws NoSuchAlgorithmException, NoSuchPaddingException {
        String[] arrstring;
        block2 : {
            block3 : {
                Providers.checkBouncyCastleDeprecation((Provider)serializable, "Cipher", string);
                arrstring = Cipher.tokenizeTransformation(string);
                try {
                    CipherSpiAndProvider cipherSpiAndProvider = Cipher.tryCombinations(null, (Provider)serializable, arrstring);
                    if (cipherSpiAndProvider != null) break block2;
                    if (serializable != null) break block3;
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("No provider found for ");
                }
                catch (InvalidAlgorithmParameterException | InvalidKeyException generalSecurityException) {
                    throw new IllegalStateException("Key/Algorithm excepton despite not passing one", generalSecurityException);
                }
                ((StringBuilder)serializable).append(string);
                throw new NoSuchAlgorithmException(((StringBuilder)serializable).toString());
            }
            arrstring = new StringBuilder();
            arrstring.append("Provider ");
            arrstring.append(((Provider)serializable).getName());
            arrstring.append(" does not provide ");
            arrstring.append(string);
            throw new NoSuchAlgorithmException(arrstring.toString());
        }
        return new Cipher(null, (Provider)serializable, string, arrstring);
    }

    private AlgorithmParameterSpec getAlgorithmParameterSpec(AlgorithmParameters algorithmParameters) throws InvalidParameterSpecException {
        if (algorithmParameters == null) {
            return null;
        }
        String string = algorithmParameters.getAlgorithm().toUpperCase(Locale.ENGLISH);
        if (string.equalsIgnoreCase("RC2")) {
            return algorithmParameters.getParameterSpec(RC2ParameterSpec.class);
        }
        if (string.equalsIgnoreCase("RC5")) {
            return algorithmParameters.getParameterSpec(RC5ParameterSpec.class);
        }
        if (string.startsWith("PBE")) {
            return algorithmParameters.getParameterSpec(PBEParameterSpec.class);
        }
        if (string.startsWith("DES")) {
            return algorithmParameters.getParameterSpec(IvParameterSpec.class);
        }
        return null;
    }

    public static final Cipher getInstance(String string) throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.createCipher(string, null);
    }

    public static final Cipher getInstance(String charSequence, String string) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
        if (string != null && string.length() != 0) {
            Provider provider = Security.getProvider(string);
            if (provider != null) {
                return Cipher.getInstance((String)charSequence, provider);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("No such provider: ");
            ((StringBuilder)charSequence).append(string);
            throw new NoSuchProviderException(((StringBuilder)charSequence).toString());
        }
        throw new IllegalArgumentException("Missing provider");
    }

    public static final Cipher getInstance(String string, Provider provider) throws NoSuchAlgorithmException, NoSuchPaddingException {
        if (provider != null) {
            return Cipher.createCipher(string, provider);
        }
        throw new IllegalArgumentException("Missing provider");
    }

    public static final int getMaxAllowedKeyLength(String string) throws NoSuchAlgorithmException {
        if (string != null) {
            Cipher.tokenizeTransformation(string);
            return Integer.MAX_VALUE;
        }
        throw new NullPointerException("transformation == null");
    }

    public static final AlgorithmParameterSpec getMaxAllowedParameterSpec(String string) throws NoSuchAlgorithmException {
        if (string != null) {
            Cipher.tokenizeTransformation(string);
            return null;
        }
        throw new NullPointerException("transformation == null");
    }

    private static String getOpmodeString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return "";
                    }
                    return "key unwrapping";
                }
                return "key wrapping";
            }
            return "decryption";
        }
        return "encryption";
    }

    static boolean matchAttribute(Provider.Service object, String string, String string2) {
        if (string2 == null) {
            return true;
        }
        if ((object = ((Provider.Service)object).getAttribute(string)) == null) {
            return true;
        }
        return string2.toUpperCase(Locale.US).matches(((String)object).toUpperCase(Locale.US));
    }

    private static String[] tokenizeTransformation(String string) throws NoSuchAlgorithmException {
        if (string != null && !string.isEmpty()) {
            block10 : {
                Object object;
                Object object2;
                block11 : {
                    boolean bl;
                    object = new String[3];
                    int n = 0;
                    object2 = new StringTokenizer(string, "/");
                    do {
                        bl = ((StringTokenizer)object2).hasMoreTokens();
                        if (!bl || n >= 3) break;
                        try {
                            object[n] = ((StringTokenizer)object2).nextToken().trim();
                            ++n;
                        }
                        catch (NoSuchElementException noSuchElementException) {
                            break block10;
                        }
                    } while (true);
                    if (n != 0 && n != 2) {
                        bl = ((StringTokenizer)object2).hasMoreTokens();
                        if (bl) break block11;
                        if (object[0] != null && ((String)object[0]).length() != 0) {
                            return object;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid transformation:algorithm not specified-");
                        ((StringBuilder)object).append(string);
                        throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
                    }
                }
                try {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid transformation format:");
                    ((StringBuilder)object).append(string);
                    object2 = new NoSuchAlgorithmException(((StringBuilder)object).toString());
                    throw object2;
                }
                catch (NoSuchElementException noSuchElementException) {
                    // empty catch block
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid transformation format:");
            stringBuilder.append(string);
            throw new NoSuchAlgorithmException(stringBuilder.toString());
        }
        throw new NoSuchAlgorithmException("No transformation given");
    }

    static CipherSpiAndProvider tryCombinations(InitParams initParams, Provider object, String[] arrstring) throws InvalidKeyException, InvalidAlgorithmParameterException {
        Object object2;
        block17 : {
            int n;
            Object object3;
            Object object4;
            block16 : {
                object4 = new ArrayList<Transform>();
                object2 = arrstring[1];
                n = 0;
                if (object2 != null && arrstring[2] != null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(arrstring[0]);
                    ((StringBuilder)object2).append("/");
                    ((StringBuilder)object2).append(arrstring[1]);
                    ((StringBuilder)object2).append("/");
                    ((StringBuilder)object2).append(arrstring[2]);
                    ((ArrayList)object4).add(new Transform(((StringBuilder)object2).toString(), NeedToSet.NONE));
                }
                if (arrstring[1] != null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(arrstring[0]);
                    ((StringBuilder)object2).append("/");
                    ((StringBuilder)object2).append(arrstring[1]);
                    ((ArrayList)object4).add(new Transform(((StringBuilder)object2).toString(), NeedToSet.PADDING));
                }
                if (arrstring[2] != null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(arrstring[0]);
                    ((StringBuilder)object2).append("//");
                    ((StringBuilder)object2).append(arrstring[2]);
                    ((ArrayList)object4).add(new Transform(((StringBuilder)object2).toString(), NeedToSet.MODE));
                }
                ((ArrayList)object4).add(new Transform(arrstring[0], NeedToSet.BOTH));
                object2 = null;
                object3 = null;
                if (object == null) break block16;
                Iterator iterator = ((ArrayList)object4).iterator();
                while (iterator.hasNext()) {
                    object4 = (Transform)iterator.next();
                    object3 = ((Provider)object).getService("Cipher", ((Transform)object4).name);
                    if (object3 == null) continue;
                    return Cipher.tryTransformWithProvider(initParams, arrstring, ((Transform)object4).needToSet, (Provider.Service)object3);
                }
                break block17;
            }
            Provider[] arrprovider = Security.getProviders();
            int n2 = arrprovider.length;
            object = object3;
            do {
                object2 = object;
                if (n >= n2) break;
                Provider provider = arrprovider[n];
                Iterator iterator = ((ArrayList)object4).iterator();
                while (iterator.hasNext()) {
                    block15 : {
                        Provider.Service service;
                        block18 : {
                            object3 = (Transform)iterator.next();
                            service = provider.getService("Cipher", ((Transform)object3).name);
                            if (service == null) continue;
                            if (initParams == null || initParams.key == null) break block18;
                            object2 = object;
                            if (!service.supportsParameter(initParams.key)) break block15;
                        }
                        try {
                            object2 = Cipher.tryTransformWithProvider(initParams, arrstring, ((Transform)object3).needToSet, service);
                            if (object2 != null) {
                                return object2;
                            }
                            object2 = object;
                        }
                        catch (Exception exception) {
                            object2 = object;
                            if (object != null) break block15;
                            object2 = exception;
                        }
                    }
                    object = object2;
                }
                ++n;
            } while (true);
        }
        if (!(object2 instanceof InvalidKeyException)) {
            if (!(object2 instanceof InvalidAlgorithmParameterException)) {
                if (!(object2 instanceof RuntimeException)) {
                    if (object2 == null) {
                        if (initParams != null && initParams.key != null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("No provider offers ");
                            ((StringBuilder)object).append(Arrays.toString(arrstring));
                            ((StringBuilder)object).append(" for ");
                            ((StringBuilder)object).append(initParams.key.getAlgorithm());
                            ((StringBuilder)object).append(" key of class ");
                            ((StringBuilder)object).append(initParams.key.getClass().getName());
                            ((StringBuilder)object).append(" and export format ");
                            ((StringBuilder)object).append(initParams.key.getFormat());
                            throw new InvalidKeyException(((StringBuilder)object).toString());
                        }
                        return null;
                    }
                    throw new InvalidKeyException("No provider can be initialized with given key", (Throwable)object2);
                }
                throw (RuntimeException)object2;
            }
            throw (InvalidAlgorithmParameterException)object2;
        }
        throw (InvalidKeyException)object2;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static CipherSpiAndProvider tryTransformWithProvider(InitParams object, String[] arrstring, NeedToSet needToSet, Provider.Service object2) throws InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            if (!Cipher.matchAttribute((Provider.Service)object2, ATTRIBUTE_MODES, arrstring[1])) return null;
            if (!Cipher.matchAttribute((Provider.Service)object2, ATTRIBUTE_PADDINGS, arrstring[2])) {
                return null;
            }
            CipherSpiAndProvider cipherSpiAndProvider = new CipherSpiAndProvider((CipherSpi)((Provider.Service)object2).newInstance(null), ((Provider.Service)object2).getProvider());
            if (cipherSpiAndProvider.cipherSpi == null) return null;
            if (cipherSpiAndProvider.provider == null) {
                return null;
            }
            object2 = cipherSpiAndProvider.cipherSpi;
            if ((needToSet == NeedToSet.MODE || needToSet == NeedToSet.BOTH) && arrstring[1] != null) {
                ((CipherSpi)object2).engineSetMode(arrstring[1]);
            }
            if ((needToSet == NeedToSet.PADDING || needToSet == NeedToSet.BOTH) && arrstring[2] != null) {
                ((CipherSpi)object2).engineSetPadding(arrstring[2]);
            }
            if (object == null) return new CipherSpiAndProvider((CipherSpi)object2, cipherSpiAndProvider.provider);
            int n = 1.$SwitchMap$javax$crypto$Cipher$InitType[((InitParams)object).initType.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        object = new AssertionError((Object)"This should never be reached");
                        throw object;
                    }
                    ((CipherSpi)object2).engineInit(((InitParams)object).opmode, ((InitParams)object).key, ((InitParams)object).random);
                    return new CipherSpiAndProvider((CipherSpi)object2, cipherSpiAndProvider.provider);
                } else {
                    ((CipherSpi)object2).engineInit(((InitParams)object).opmode, ((InitParams)object).key, ((InitParams)object).spec, ((InitParams)object).random);
                }
                return new CipherSpiAndProvider((CipherSpi)object2, cipherSpiAndProvider.provider);
            } else {
                ((CipherSpi)object2).engineInit(((InitParams)object).opmode, ((InitParams)object).key, ((InitParams)object).params, ((InitParams)object).random);
            }
            return new CipherSpiAndProvider((CipherSpi)object2, cipherSpiAndProvider.provider);
        }
        catch (NoSuchPaddingException noSuchPaddingException) {
            return null;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        return null;
    }

    public final int doFinal(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        if (byteBuffer != null && byteBuffer2 != null) {
            if (byteBuffer != byteBuffer2) {
                if (!byteBuffer2.isReadOnly()) {
                    this.updateProviderIfNeeded();
                    return this.spi.engineDoFinal(byteBuffer, byteBuffer2);
                }
                throw new ReadOnlyBufferException();
            }
            throw new IllegalArgumentException("Input and output buffers must not be the same object, consider using buffer.duplicate()");
        }
        throw new IllegalArgumentException("Buffers must not be null");
    }

    public final int doFinal(byte[] arrby, int n) throws IllegalBlockSizeException, ShortBufferException, BadPaddingException {
        this.checkCipherState();
        if (arrby != null && n >= 0) {
            this.updateProviderIfNeeded();
            return this.spi.engineDoFinal(null, 0, 0, arrby, n);
        }
        throw new IllegalArgumentException("Bad arguments");
    }

    public final int doFinal(byte[] arrby, int n, int n2, byte[] arrby2) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        if (arrby != null && n >= 0 && n2 <= arrby.length - n && n2 >= 0) {
            this.updateProviderIfNeeded();
            return this.spi.engineDoFinal(arrby, n, n2, arrby2, 0);
        }
        throw new IllegalArgumentException("Bad arguments");
    }

    public final int doFinal(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        if (arrby != null && n >= 0 && n2 <= arrby.length - n && n2 >= 0 && n3 >= 0) {
            this.updateProviderIfNeeded();
            return this.spi.engineDoFinal(arrby, n, n2, arrby2, n3);
        }
        throw new IllegalArgumentException("Bad arguments");
    }

    public final byte[] doFinal() throws IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        this.updateProviderIfNeeded();
        return this.spi.engineDoFinal(null, 0, 0);
    }

    public final byte[] doFinal(byte[] arrby) throws IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        if (arrby != null) {
            this.updateProviderIfNeeded();
            return this.spi.engineDoFinal(arrby, 0, arrby.length);
        }
        throw new IllegalArgumentException("Null input buffer");
    }

    public final byte[] doFinal(byte[] arrby, int n, int n2) throws IllegalBlockSizeException, BadPaddingException {
        this.checkCipherState();
        if (arrby != null && n >= 0 && n2 <= arrby.length - n && n2 >= 0) {
            this.updateProviderIfNeeded();
            return this.spi.engineDoFinal(arrby, n, n2);
        }
        throw new IllegalArgumentException("Bad arguments");
    }

    public final String getAlgorithm() {
        return this.transformation;
    }

    public final int getBlockSize() {
        this.updateProviderIfNeeded();
        return this.spi.engineGetBlockSize();
    }

    public CipherSpi getCurrentSpi() {
        return this.spi;
    }

    public final ExemptionMechanism getExemptionMechanism() {
        this.updateProviderIfNeeded();
        return this.exmech;
    }

    public final byte[] getIV() {
        this.updateProviderIfNeeded();
        return this.spi.engineGetIV();
    }

    public final int getOutputSize(int n) {
        if (!this.initialized && !(this instanceof NullCipher)) {
            throw new IllegalStateException("Cipher not initialized");
        }
        if (n >= 0) {
            this.updateProviderIfNeeded();
            return this.spi.engineGetOutputSize(n);
        }
        throw new IllegalArgumentException("Input size must be equal to or greater than zero");
    }

    public final AlgorithmParameters getParameters() {
        this.updateProviderIfNeeded();
        return this.spi.engineGetParameters();
    }

    public final Provider getProvider() {
        this.updateProviderIfNeeded();
        return this.provider;
    }

    public final void init(int n, Key key) throws InvalidKeyException {
        this.init(n, key, JceSecurity.RANDOM);
    }

    public final void init(int n, Key key, AlgorithmParameters algorithmParameters) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.init(n, key, algorithmParameters, JceSecurity.RANDOM);
    }

    public final void init(int n, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.initialized = false;
        Cipher.checkOpmode(n);
        this.chooseProvider(InitType.ALGORITHM_PARAMS, n, key, null, algorithmParameters, secureRandom);
        this.initialized = true;
        this.opmode = n;
    }

    public final void init(int n, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        this.initialized = false;
        Cipher.checkOpmode(n);
        try {
            this.chooseProvider(InitType.KEY, n, key, null, null, secureRandom);
            this.initialized = true;
            this.opmode = n;
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidKeyException(invalidAlgorithmParameterException);
        }
    }

    public final void init(int n, Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.init(n, key, algorithmParameterSpec, JceSecurity.RANDOM);
    }

    public final void init(int n, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.initialized = false;
        Cipher.checkOpmode(n);
        this.chooseProvider(InitType.ALGORITHM_PARAM_SPEC, n, key, algorithmParameterSpec, null, secureRandom);
        this.initialized = true;
        this.opmode = n;
    }

    public final void init(int n, Certificate certificate) throws InvalidKeyException {
        this.init(n, certificate, JceSecurity.RANDOM);
    }

    public final void init(int n, Certificate serializable, SecureRandom secureRandom) throws InvalidKeyException {
        Set<String> set;
        boolean[] arrbl;
        this.initialized = false;
        Cipher.checkOpmode(n);
        if (serializable instanceof X509Certificate && (set = (arrbl = (boolean[])serializable).getCriticalExtensionOIDs()) != null && !set.isEmpty() && set.contains(KEY_USAGE_EXTENSION_OID) && (arrbl = arrbl.getKeyUsage()) != null && (n == 1 && arrbl.length > 3 && !arrbl[3] || n == 3 && arrbl.length > 2 && !arrbl[2])) {
            throw new InvalidKeyException("Wrong key usage");
        }
        serializable = serializable == null ? null : serializable.getPublicKey();
        try {
            this.chooseProvider(InitType.KEY, n, (Key)serializable, null, null, secureRandom);
            this.initialized = true;
            this.opmode = n;
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidKeyException(invalidAlgorithmParameterException);
        }
    }

    public final Key unwrap(byte[] arrby, String string, int n) throws InvalidKeyException, NoSuchAlgorithmException {
        if (!(this instanceof NullCipher)) {
            if (this.initialized) {
                if (this.opmode != 4) {
                    throw new IllegalStateException("Cipher not initialized for unwrapping keys");
                }
            } else {
                throw new IllegalStateException("Cipher not initialized");
            }
        }
        if (n != 3 && n != 2 && n != 1) {
            throw new InvalidParameterException("Invalid key type");
        }
        this.updateProviderIfNeeded();
        return this.spi.engineUnwrap(arrby, string, n);
    }

    public final int update(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws ShortBufferException {
        this.checkCipherState();
        if (byteBuffer != null && byteBuffer2 != null) {
            if (byteBuffer != byteBuffer2) {
                if (!byteBuffer2.isReadOnly()) {
                    this.updateProviderIfNeeded();
                    return this.spi.engineUpdate(byteBuffer, byteBuffer2);
                }
                throw new ReadOnlyBufferException();
            }
            throw new IllegalArgumentException("Input and output buffers must not be the same object, consider using buffer.duplicate()");
        }
        throw new IllegalArgumentException("Buffers must not be null");
    }

    public final int update(byte[] arrby, int n, int n2, byte[] arrby2) throws ShortBufferException {
        this.checkCipherState();
        if (arrby != null && n >= 0 && n2 <= arrby.length - n && n2 >= 0) {
            this.updateProviderIfNeeded();
            if (n2 == 0) {
                return 0;
            }
            return this.spi.engineUpdate(arrby, n, n2, arrby2, 0);
        }
        throw new IllegalArgumentException("Bad arguments");
    }

    public final int update(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws ShortBufferException {
        this.checkCipherState();
        if (arrby != null && n >= 0 && n2 <= arrby.length - n && n2 >= 0 && n3 >= 0) {
            this.updateProviderIfNeeded();
            if (n2 == 0) {
                return 0;
            }
            return this.spi.engineUpdate(arrby, n, n2, arrby2, n3);
        }
        throw new IllegalArgumentException("Bad arguments");
    }

    public final byte[] update(byte[] arrby) {
        this.checkCipherState();
        if (arrby != null) {
            this.updateProviderIfNeeded();
            if (arrby.length == 0) {
                return null;
            }
            return this.spi.engineUpdate(arrby, 0, arrby.length);
        }
        throw new IllegalArgumentException("Null input buffer");
    }

    public final byte[] update(byte[] arrby, int n, int n2) {
        this.checkCipherState();
        if (arrby != null && n >= 0 && n2 <= arrby.length - n && n2 >= 0) {
            this.updateProviderIfNeeded();
            if (n2 == 0) {
                return null;
            }
            return this.spi.engineUpdate(arrby, n, n2);
        }
        throw new IllegalArgumentException("Bad arguments");
    }

    public final void updateAAD(ByteBuffer byteBuffer) {
        this.checkCipherState();
        if (byteBuffer != null) {
            this.updateProviderIfNeeded();
            if (byteBuffer.remaining() == 0) {
                return;
            }
            this.spi.engineUpdateAAD(byteBuffer);
            return;
        }
        throw new IllegalArgumentException("src ByteBuffer is null");
    }

    public final void updateAAD(byte[] arrby) {
        if (arrby != null) {
            this.updateAAD(arrby, 0, arrby.length);
            return;
        }
        throw new IllegalArgumentException("src buffer is null");
    }

    public final void updateAAD(byte[] arrby, int n, int n2) {
        this.checkCipherState();
        if (arrby != null && n >= 0 && n2 >= 0 && n2 + n <= arrby.length) {
            this.updateProviderIfNeeded();
            if (n2 == 0) {
                return;
            }
            this.spi.engineUpdateAAD(arrby, n, n2);
            return;
        }
        throw new IllegalArgumentException("Bad arguments");
    }

    void updateProviderIfNeeded() {
        try {
            this.spiAndProviderUpdater.updateAndGetSpiAndProvider(null, this.spi, this.provider);
            return;
        }
        catch (Exception exception) {
            ProviderException providerException = new ProviderException("Could not construct CipherSpi instance");
            providerException.initCause(exception);
            throw providerException;
        }
    }

    public final byte[] wrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        if (!(this instanceof NullCipher)) {
            if (this.initialized) {
                if (this.opmode != 3) {
                    throw new IllegalStateException("Cipher not initialized for wrapping keys");
                }
            } else {
                throw new IllegalStateException("Cipher not initialized");
            }
        }
        this.updateProviderIfNeeded();
        return this.spi.engineWrap(key);
    }

    static class CipherSpiAndProvider {
        CipherSpi cipherSpi;
        Provider provider;

        CipherSpiAndProvider(CipherSpi cipherSpi, Provider provider) {
            this.cipherSpi = cipherSpi;
            this.provider = provider;
        }
    }

    static class InitParams {
        final InitType initType;
        final Key key;
        final int opmode;
        final AlgorithmParameters params;
        final SecureRandom random;
        final AlgorithmParameterSpec spec;

        InitParams(InitType initType, int n, Key key, SecureRandom secureRandom, AlgorithmParameterSpec algorithmParameterSpec, AlgorithmParameters algorithmParameters) {
            this.initType = initType;
            this.opmode = n;
            this.key = key;
            this.random = secureRandom;
            this.spec = algorithmParameterSpec;
            this.params = algorithmParameters;
        }
    }

    static enum InitType {
        KEY,
        ALGORITHM_PARAMS,
        ALGORITHM_PARAM_SPEC;
        
    }

    static enum NeedToSet {
        NONE,
        MODE,
        PADDING,
        BOTH;
        
    }

    class SpiAndProviderUpdater {
        private final Object initSpiLock = new Object();
        private final Provider specifiedProvider;
        private final CipherSpi specifiedSpi;

        SpiAndProviderUpdater(Provider provider, CipherSpi cipherSpi) {
            this.specifiedProvider = provider;
            this.specifiedSpi = cipherSpi;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        CipherSpi getCurrentSpi(CipherSpi cipherSpi) {
            Object object = this.specifiedSpi;
            if (object != null) {
                return object;
            }
            object = this.initSpiLock;
            // MONITORENTER : object
            // MONITOREXIT : object
            return cipherSpi;
        }

        void setCipherSpiImplAndProvider(CipherSpi cipherSpi, Provider provider) {
            Cipher.this.spi = cipherSpi;
            Cipher.this.provider = provider;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        CipherSpiAndProvider updateAndGetSpiAndProvider(InitParams object, CipherSpi object2, Provider provider) throws InvalidKeyException, InvalidAlgorithmParameterException {
            Object object3 = this.specifiedSpi;
            if (object3 != null) {
                return new CipherSpiAndProvider((CipherSpi)object3, provider);
            }
            object3 = this.initSpiLock;
            synchronized (object3) {
                if (object2 != null && object == null) {
                    return new CipherSpiAndProvider((CipherSpi)object2, provider);
                }
                object2 = Cipher.tryCombinations((InitParams)object, this.specifiedProvider, Cipher.this.tokenizedTransformation);
                if (object2 != null) {
                    this.setCipherSpiImplAndProvider(((CipherSpiAndProvider)object2).cipherSpi, ((CipherSpiAndProvider)object2).provider);
                    return new CipherSpiAndProvider(((CipherSpiAndProvider)object2).cipherSpi, ((CipherSpiAndProvider)object2).provider);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("No provider found for ");
                ((StringBuilder)object).append(Arrays.toString(Cipher.this.tokenizedTransformation));
                object2 = new ProviderException(((StringBuilder)object).toString());
                throw object2;
            }
        }

        CipherSpiAndProvider updateAndGetSpiAndProvider(CipherSpi object, Provider provider) {
            try {
                object = this.updateAndGetSpiAndProvider(null, (CipherSpi)object, provider);
                return object;
            }
            catch (InvalidAlgorithmParameterException | InvalidKeyException generalSecurityException) {
                throw new ProviderException("Exception thrown when params == null", generalSecurityException);
            }
        }
    }

    static class Transform {
        private final String name;
        private final NeedToSet needToSet;

        public Transform(String string, NeedToSet needToSet) {
            this.name = string;
            this.needToSet = needToSet;
        }
    }

}

