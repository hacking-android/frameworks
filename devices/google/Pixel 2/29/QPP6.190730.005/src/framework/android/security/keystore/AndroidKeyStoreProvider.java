/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.annotation.UnsupportedAppUsage;
import android.os.SystemProperties;
import android.security.KeyStore;
import android.security.keymaster.ExportResult;
import android.security.keymaster.KeyCharacteristics;
import android.security.keymaster.KeymasterBlob;
import android.security.keystore.AndroidKeyStoreBCWorkaroundProvider;
import android.security.keystore.AndroidKeyStoreECPrivateKey;
import android.security.keystore.AndroidKeyStoreECPublicKey;
import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.AndroidKeyStoreLoadStoreParameter;
import android.security.keystore.AndroidKeyStorePrivateKey;
import android.security.keystore.AndroidKeyStorePublicKey;
import android.security.keystore.AndroidKeyStoreRSAPrivateKey;
import android.security.keystore.AndroidKeyStoreRSAPublicKey;
import android.security.keystore.AndroidKeyStoreSecretKey;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyStoreCryptoOperation;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureSpi;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.ECKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import javax.crypto.Mac;
import javax.crypto.MacSpi;

public class AndroidKeyStoreProvider
extends Provider {
    private static final String DESEDE_SYSTEM_PROPERTY = "ro.hardware.keystore_desede";
    private static final String PACKAGE_NAME = "android.security.keystore";
    public static final String PROVIDER_NAME = "AndroidKeyStore";

    public AndroidKeyStoreProvider() {
        super(PROVIDER_NAME, 1.0, "Android KeyStore security provider");
        boolean bl = "true".equals(SystemProperties.get(DESEDE_SYSTEM_PROPERTY));
        this.put("KeyStore.AndroidKeyStore", "android.security.keystore.AndroidKeyStoreSpi");
        this.put("KeyPairGenerator.EC", "android.security.keystore.AndroidKeyStoreKeyPairGeneratorSpi$EC");
        this.put("KeyPairGenerator.RSA", "android.security.keystore.AndroidKeyStoreKeyPairGeneratorSpi$RSA");
        this.putKeyFactoryImpl("EC");
        this.putKeyFactoryImpl("RSA");
        this.put("KeyGenerator.AES", "android.security.keystore.AndroidKeyStoreKeyGeneratorSpi$AES");
        this.put("KeyGenerator.HmacSHA1", "android.security.keystore.AndroidKeyStoreKeyGeneratorSpi$HmacSHA1");
        this.put("KeyGenerator.HmacSHA224", "android.security.keystore.AndroidKeyStoreKeyGeneratorSpi$HmacSHA224");
        this.put("KeyGenerator.HmacSHA256", "android.security.keystore.AndroidKeyStoreKeyGeneratorSpi$HmacSHA256");
        this.put("KeyGenerator.HmacSHA384", "android.security.keystore.AndroidKeyStoreKeyGeneratorSpi$HmacSHA384");
        this.put("KeyGenerator.HmacSHA512", "android.security.keystore.AndroidKeyStoreKeyGeneratorSpi$HmacSHA512");
        if (bl) {
            this.put("KeyGenerator.DESede", "android.security.keystore.AndroidKeyStoreKeyGeneratorSpi$DESede");
        }
        this.putSecretKeyFactoryImpl("AES");
        if (bl) {
            this.putSecretKeyFactoryImpl("DESede");
        }
        this.putSecretKeyFactoryImpl("HmacSHA1");
        this.putSecretKeyFactoryImpl("HmacSHA224");
        this.putSecretKeyFactoryImpl("HmacSHA256");
        this.putSecretKeyFactoryImpl("HmacSHA384");
        this.putSecretKeyFactoryImpl("HmacSHA512");
    }

    private static AndroidKeyStorePrivateKey getAndroidKeyStorePrivateKey(AndroidKeyStorePublicKey serializable) {
        String string2 = ((AndroidKeyStoreKey)serializable).getAlgorithm();
        if ("EC".equalsIgnoreCase(string2)) {
            return new AndroidKeyStoreECPrivateKey(((AndroidKeyStoreKey)serializable).getAlias(), ((AndroidKeyStoreKey)serializable).getUid(), ((ECKey)((Object)serializable)).getParams());
        }
        if ("RSA".equalsIgnoreCase(string2)) {
            return new AndroidKeyStoreRSAPrivateKey(((AndroidKeyStoreKey)serializable).getAlias(), ((AndroidKeyStoreKey)serializable).getUid(), ((RSAKey)((Object)serializable)).getModulus());
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unsupported Android Keystore public key algorithm: ");
        ((StringBuilder)serializable).append(string2);
        throw new ProviderException(((StringBuilder)serializable).toString());
    }

    public static AndroidKeyStorePublicKey getAndroidKeyStorePublicKey(String charSequence, int n, String string2, byte[] object) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(string2);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec((byte[])object);
            object = keyFactory.generatePublic(x509EncodedKeySpec);
            if ("EC".equalsIgnoreCase(string2)) {
                return new AndroidKeyStoreECPublicKey((String)charSequence, n, (ECPublicKey)object);
            }
            if ("RSA".equalsIgnoreCase(string2)) {
                return new AndroidKeyStoreRSAPublicKey((String)charSequence, n, (RSAPublicKey)object);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unsupported Android Keystore public key algorithm: ");
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            throw new ProviderException("Invalid X.509 encoding of public key", invalidKeySpecException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to obtain ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" KeyFactory");
            throw new ProviderException(((StringBuilder)object).toString(), noSuchAlgorithmException);
        }
        ((StringBuilder)charSequence).append(string2);
        throw new ProviderException(((StringBuilder)charSequence).toString());
    }

    private static KeyCharacteristics getKeyCharacteristics(KeyStore keyStore, String string2, int n) throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        KeyCharacteristics keyCharacteristics = new KeyCharacteristics();
        if ((n = keyStore.getKeyCharacteristics(string2, null, null, n, keyCharacteristics)) != 17) {
            if (n == 1) {
                return keyCharacteristics;
            }
            throw (UnrecoverableKeyException)new UnrecoverableKeyException("Failed to obtain information about key").initCause(KeyStore.getKeyStoreException(n));
        }
        throw new KeyPermanentlyInvalidatedException("User changed or deleted their auth credentials", KeyStore.getKeyStoreException(n));
    }

    public static java.security.KeyStore getKeyStoreForUid(int n) throws KeyStoreException, NoSuchProviderException {
        java.security.KeyStore keyStore = java.security.KeyStore.getInstance(PROVIDER_NAME, PROVIDER_NAME);
        try {
            AndroidKeyStoreLoadStoreParameter androidKeyStoreLoadStoreParameter = new AndroidKeyStoreLoadStoreParameter(n);
            keyStore.load(androidKeyStoreLoadStoreParameter);
            return keyStore;
        }
        catch (IOException | NoSuchAlgorithmException | CertificateException exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to load AndroidKeyStore KeyStore for UID ");
            stringBuilder.append(n);
            throw new KeyStoreException(stringBuilder.toString(), exception);
        }
    }

    @UnsupportedAppUsage
    public static long getKeyStoreOperationHandle(Object object) {
        block5 : {
            block9 : {
                Object object2;
                block7 : {
                    block8 : {
                        block6 : {
                            if (object == null) break block5;
                            if (!(object instanceof Signature)) break block6;
                            object2 = ((Signature)object).getCurrentSpi();
                            break block7;
                        }
                        if (!(object instanceof Mac)) break block8;
                        object2 = ((Mac)object).getCurrentSpi();
                        break block7;
                    }
                    if (!(object instanceof Cipher)) break block9;
                    object2 = ((Cipher)object).getCurrentSpi();
                }
                if (object2 != null) {
                    if (object2 instanceof KeyStoreCryptoOperation) {
                        return ((KeyStoreCryptoOperation)object2).getOperationHandle();
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Crypto primitive not backed by AndroidKeyStore provider: ");
                    stringBuilder.append(object);
                    stringBuilder.append(", spi: ");
                    stringBuilder.append(object2);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                throw new IllegalStateException("Crypto primitive not initialized");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported crypto primitive: ");
            stringBuilder.append(object);
            stringBuilder.append(". Supported: Signature, Mac, Cipher");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        throw new NullPointerException();
    }

    public static void install() {
        int n;
        Object object = Security.getProviders();
        int n2 = -1;
        int n3 = 0;
        do {
            n = n2;
            if (n3 >= ((Provider[])object).length) break;
            if ("BC".equals(object[n3].getName())) {
                n = n3;
                break;
            }
            ++n3;
        } while (true);
        Security.addProvider(new AndroidKeyStoreProvider());
        object = new AndroidKeyStoreBCWorkaroundProvider();
        if (n != -1) {
            Security.insertProviderAt((Provider)object, n + 1);
        } else {
            Security.addProvider((Provider)object);
        }
    }

    public static AndroidKeyStoreKey loadAndroidKeyStoreKeyFromKeystore(KeyStore keyStore, String string2, int n) throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        KeyCharacteristics keyCharacteristics = AndroidKeyStoreProvider.getKeyCharacteristics(keyStore, string2, n);
        Integer n2 = keyCharacteristics.getEnum(268435458);
        if (n2 != null) {
            if (n2 != 128 && n2 != 32 && n2 != 33) {
                if (n2 != 1 && n2 != 3) {
                    throw new UnrecoverableKeyException("Key algorithm unknown");
                }
                return AndroidKeyStoreProvider.loadAndroidKeyStorePrivateKeyFromKeystore(keyStore, string2, n, keyCharacteristics);
            }
            return AndroidKeyStoreProvider.loadAndroidKeyStoreSecretKeyFromKeystore(string2, n, keyCharacteristics);
        }
        throw new UnrecoverableKeyException("Key algorithm unknown");
    }

    public static KeyPair loadAndroidKeyStoreKeyPairFromKeystore(KeyStore keyStore, String string2, int n) throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        return AndroidKeyStoreProvider.loadAndroidKeyStoreKeyPairFromKeystore(keyStore, string2, n, AndroidKeyStoreProvider.getKeyCharacteristics(keyStore, string2, n));
    }

    private static KeyPair loadAndroidKeyStoreKeyPairFromKeystore(KeyStore object, String string2, int n, KeyCharacteristics keyCharacteristics) throws UnrecoverableKeyException {
        object = AndroidKeyStoreProvider.loadAndroidKeyStorePublicKeyFromKeystore((KeyStore)object, string2, n, keyCharacteristics);
        return new KeyPair((PublicKey)object, AndroidKeyStoreProvider.getAndroidKeyStorePrivateKey((AndroidKeyStorePublicKey)object));
    }

    public static AndroidKeyStorePrivateKey loadAndroidKeyStorePrivateKeyFromKeystore(KeyStore keyStore, String string2, int n) throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        return AndroidKeyStoreProvider.loadAndroidKeyStorePrivateKeyFromKeystore(keyStore, string2, n, AndroidKeyStoreProvider.getKeyCharacteristics(keyStore, string2, n));
    }

    private static AndroidKeyStorePrivateKey loadAndroidKeyStorePrivateKeyFromKeystore(KeyStore keyStore, String string2, int n, KeyCharacteristics keyCharacteristics) throws UnrecoverableKeyException {
        return (AndroidKeyStorePrivateKey)AndroidKeyStoreProvider.loadAndroidKeyStoreKeyPairFromKeystore(keyStore, string2, n, keyCharacteristics).getPrivate();
    }

    public static AndroidKeyStorePublicKey loadAndroidKeyStorePublicKeyFromKeystore(KeyStore keyStore, String string2, int n) throws UnrecoverableKeyException, KeyPermanentlyInvalidatedException {
        return AndroidKeyStoreProvider.loadAndroidKeyStorePublicKeyFromKeystore(keyStore, string2, n, AndroidKeyStoreProvider.getKeyCharacteristics(keyStore, string2, n));
    }

    private static AndroidKeyStorePublicKey loadAndroidKeyStorePublicKeyFromKeystore(KeyStore arrby, String string2, int n, KeyCharacteristics object) throws UnrecoverableKeyException {
        arrby = arrby.exportKey(string2, 0, null, null, n);
        if (arrby.resultCode == 1) {
            arrby = arrby.exportData;
            if ((object = ((KeyCharacteristics)object).getEnum(268435458)) != null) {
                try {
                    object = KeyProperties.KeyAlgorithm.fromKeymasterAsymmetricKeyAlgorithm((Integer)object);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw (UnrecoverableKeyException)new UnrecoverableKeyException("Failed to load private key").initCause(illegalArgumentException);
                }
                return AndroidKeyStoreProvider.getAndroidKeyStorePublicKey(string2, n, (String)object, arrby);
            }
            throw new UnrecoverableKeyException("Key algorithm unknown");
        }
        throw (UnrecoverableKeyException)new UnrecoverableKeyException("Failed to obtain X.509 form of public key").initCause(KeyStore.getKeyStoreException(arrby.resultCode));
    }

    private static AndroidKeyStoreSecretKey loadAndroidKeyStoreSecretKeyFromKeystore(String string2, int n, KeyCharacteristics object) throws UnrecoverableKeyException {
        Integer n2 = ((KeyCharacteristics)object).getEnum(268435458);
        if (n2 != null) {
            int n3 = (object = ((KeyCharacteristics)object).getEnums(536870917)).isEmpty() ? -1 : (Integer)object.get(0);
            try {
                object = KeyProperties.KeyAlgorithm.fromKeymasterSecretKeyAlgorithm(n2, n3);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw (UnrecoverableKeyException)new UnrecoverableKeyException("Unsupported secret key type").initCause(illegalArgumentException);
            }
            return new AndroidKeyStoreSecretKey(string2, n, (String)object);
        }
        throw new UnrecoverableKeyException("Key algorithm unknown");
    }

    private void putKeyFactoryImpl(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("KeyFactory.");
        stringBuilder.append(string2);
        this.put(stringBuilder.toString(), "android.security.keystore.AndroidKeyStoreKeyFactorySpi");
    }

    private void putSecretKeyFactoryImpl(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SecretKeyFactory.");
        stringBuilder.append(string2);
        this.put(stringBuilder.toString(), "android.security.keystore.AndroidKeyStoreSecretKeyFactorySpi");
    }
}

