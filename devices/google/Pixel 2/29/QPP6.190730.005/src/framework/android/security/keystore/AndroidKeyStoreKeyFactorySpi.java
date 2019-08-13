/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.KeyStore;
import android.security.keystore.AndroidKeyStoreECPublicKey;
import android.security.keystore.AndroidKeyStorePrivateKey;
import android.security.keystore.AndroidKeyStorePublicKey;
import android.security.keystore.AndroidKeyStoreRSAPublicKey;
import android.security.keystore.AndroidKeyStoreSecretKeyFactorySpi;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class AndroidKeyStoreKeyFactorySpi
extends KeyFactorySpi {
    private final KeyStore mKeyStore = KeyStore.getInstance();

    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec object) throws InvalidKeySpecException {
        object = new StringBuilder();
        ((StringBuilder)object).append("To generate a key pair in Android Keystore, use KeyPairGenerator initialized with ");
        ((StringBuilder)object).append(KeyGenParameterSpec.class.getName());
        throw new InvalidKeySpecException(((StringBuilder)object).toString());
    }

    @Override
    protected PublicKey engineGeneratePublic(KeySpec object) throws InvalidKeySpecException {
        object = new StringBuilder();
        ((StringBuilder)object).append("To generate a key pair in Android Keystore, use KeyPairGenerator initialized with ");
        ((StringBuilder)object).append(KeyGenParameterSpec.class.getName());
        throw new InvalidKeySpecException(((StringBuilder)object).toString());
    }

    @Override
    protected <T extends KeySpec> T engineGetKeySpec(Key object, Class<T> object2) throws InvalidKeySpecException {
        if (object != null) {
            if (!(object instanceof AndroidKeyStorePrivateKey) && !(object instanceof AndroidKeyStorePublicKey)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unsupported key type: ");
                ((StringBuilder)object2).append(object.getClass().getName());
                ((StringBuilder)object2).append(". This KeyFactory supports only Android Keystore asymmetric keys");
                throw new InvalidKeySpecException(((StringBuilder)object2).toString());
            }
            if (object2 != null) {
                if (KeyInfo.class.equals(object2)) {
                    if (object instanceof AndroidKeyStorePrivateKey) {
                        AndroidKeyStorePrivateKey androidKeyStorePrivateKey = (AndroidKeyStorePrivateKey)object;
                        object = androidKeyStorePrivateKey.getAlias();
                        if (((String)object).startsWith("USRPKEY_")) {
                            object2 = ((String)object).substring("USRPKEY_".length());
                            return (T)AndroidKeyStoreSecretKeyFactorySpi.getKeyInfo(this.mKeyStore, (String)object2, (String)object, androidKeyStorePrivateKey.getUid());
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Invalid key alias: ");
                        ((StringBuilder)object2).append((String)object);
                        throw new InvalidKeySpecException(((StringBuilder)object2).toString());
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unsupported key type: ");
                    ((StringBuilder)object2).append(object.getClass().getName());
                    ((StringBuilder)object2).append(". KeyInfo can be obtained only for Android Keystore private keys");
                    throw new InvalidKeySpecException(((StringBuilder)object2).toString());
                }
                if (X509EncodedKeySpec.class.equals(object2)) {
                    if (object instanceof AndroidKeyStorePublicKey) {
                        return (T)new X509EncodedKeySpec(((AndroidKeyStorePublicKey)object).getEncoded());
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unsupported key type: ");
                    ((StringBuilder)object2).append(object.getClass().getName());
                    ((StringBuilder)object2).append(". X509EncodedKeySpec can be obtained only for Android Keystore public keys");
                    throw new InvalidKeySpecException(((StringBuilder)object2).toString());
                }
                if (PKCS8EncodedKeySpec.class.equals(object2)) {
                    if (object instanceof AndroidKeyStorePrivateKey) {
                        throw new InvalidKeySpecException("Key material export of Android Keystore private keys is not supported");
                    }
                    throw new InvalidKeySpecException("Cannot export key material of public key in PKCS#8 format. Only X.509 format (X509EncodedKeySpec) supported for public keys.");
                }
                boolean bl = RSAPublicKeySpec.class.equals(object2);
                String string2 = "private";
                if (bl) {
                    if (object instanceof AndroidKeyStoreRSAPublicKey) {
                        object = (AndroidKeyStoreRSAPublicKey)object;
                        return (T)new RSAPublicKeySpec(((AndroidKeyStoreRSAPublicKey)object).getModulus(), ((AndroidKeyStoreRSAPublicKey)object).getPublicExponent());
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Obtaining RSAPublicKeySpec not supported for ");
                    ((StringBuilder)object2).append(object.getAlgorithm());
                    ((StringBuilder)object2).append(" ");
                    if (!(object instanceof AndroidKeyStorePrivateKey)) {
                        string2 = "public";
                    }
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append(" key");
                    throw new InvalidKeySpecException(((StringBuilder)object2).toString());
                }
                if (ECPublicKeySpec.class.equals(object2)) {
                    if (object instanceof AndroidKeyStoreECPublicKey) {
                        object = (AndroidKeyStoreECPublicKey)object;
                        return (T)new ECPublicKeySpec(((AndroidKeyStoreECPublicKey)object).getW(), ((AndroidKeyStoreECPublicKey)object).getParams());
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Obtaining ECPublicKeySpec not supported for ");
                    ((StringBuilder)object2).append(object.getAlgorithm());
                    ((StringBuilder)object2).append(" ");
                    if (!(object instanceof AndroidKeyStorePrivateKey)) {
                        string2 = "public";
                    }
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append(" key");
                    throw new InvalidKeySpecException(((StringBuilder)object2).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported key spec: ");
                ((StringBuilder)object).append(((Class)object2).getName());
                throw new InvalidKeySpecException(((StringBuilder)object).toString());
            }
            throw new InvalidKeySpecException("keySpecClass == null");
        }
        throw new InvalidKeySpecException("key == null");
    }

    @Override
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key != null) {
            if (!(key instanceof AndroidKeyStorePrivateKey) && !(key instanceof AndroidKeyStorePublicKey)) {
                throw new InvalidKeyException("To import a key into Android Keystore, use KeyStore.setEntry");
            }
            return key;
        }
        throw new InvalidKeyException("key == null");
    }
}

