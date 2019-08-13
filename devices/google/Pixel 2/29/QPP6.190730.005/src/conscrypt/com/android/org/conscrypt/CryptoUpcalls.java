/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.Conscrypt;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

final class CryptoUpcalls {
    private static final Logger logger = Logger.getLogger(CryptoUpcalls.class.getName());

    private CryptoUpcalls() {
    }

    static byte[] ecSignDigestWithPrivateKey(PrivateKey privateKey, byte[] object) {
        if ("EC".equals(privateKey.getAlgorithm())) {
            return CryptoUpcalls.signDigestWithPrivateKey(privateKey, (byte[])object, "NONEwithECDSA");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected key type: ");
        ((StringBuilder)object).append(privateKey.toString());
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    private static ArrayList<Provider> getExternalProviders(String string) {
        ArrayList<Provider> arrayList = new ArrayList<Provider>(1);
        for (Provider serializable : Security.getProviders(string)) {
            if (Conscrypt.isConscrypt(serializable)) continue;
            arrayList.add(serializable);
        }
        if (arrayList.isEmpty()) {
            Logger logger = CryptoUpcalls.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find external provider for algorithm: ");
            stringBuilder.append(string);
            logger.warning(stringBuilder.toString());
        }
        return arrayList;
    }

    static byte[] rsaDecryptWithPrivateKey(PrivateKey privateKey, int n, byte[] arrby) {
        return CryptoUpcalls.rsaOpWithPrivateKey(privateKey, n, 2, arrby);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static byte[] rsaOpWithPrivateKey(PrivateKey object, int n, int n2, byte[] object2) {
        block18 : {
            block19 : {
                object32 = object.getAlgorithm();
                if (!"RSA".equals(object32)) {
                    object2 = CryptoUpcalls.logger;
                    object = new StringBuilder();
                    object.append("Unexpected key type: ");
                    object.append(object32);
                    object2.warning(object.toString());
                    return null;
                }
                if (n != 1) {
                    if (n != 3) {
                        if (n != 4) {
                            object = CryptoUpcalls.logger;
                            object2 = new StringBuilder();
                            object2.append("Unsupported OpenSSL/BoringSSL padding: ");
                            object2.append(n);
                            object.warning(object2.toString());
                            return null;
                        }
                        invalidKeyException = "OAEPPadding";
                    } else {
                        noSuchPaddingException = "NoPadding";
                    }
                } else {
                    invalidKeyException = "PKCS1Padding";
                }
                object3 = new StringBuilder();
                object3.append("RSA/ECB/");
                object3.append((String)noSuchAlgorithmException);
                string = object3.toString();
                try {
                    cipher = Cipher.getInstance(string);
                    cipher.init(n2, (Key)object);
                    bl = Conscrypt.isConscrypt(cipher.getProvider());
                    if (!bl) ** GOTO lbl43
                    var4_14 = null;
                }
                catch (InvalidKeyException invalidKeyException) {
                    CryptoUpcalls.logger.log(Level.WARNING, "Preferred provider doesn't support key:", invalidKeyException);
                    var4_17 = null;
                }
lbl43: // 3 sources:
                object3 = var4_18;
                if (var4_18 != null) break block18;
                object3 = new StringBuilder();
                object3.append("Cipher.");
                object3.append(string);
                object3 = CryptoUpcalls.getExternalProviders(object3.toString()).iterator();
                break block19;
                catch (NoSuchPaddingException noSuchPaddingException) {
                    object2 = CryptoUpcalls.logger;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported cipher algorithm: ");
                    stringBuilder.append(string);
                    object2.warning(stringBuilder.toString());
                    return null;
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException2) {
                    logger = CryptoUpcalls.logger;
                    object2 = new StringBuilder();
                    object2.append("Unsupported cipher algorithm: ");
                    object2.append(string);
                    logger.warning(object2.toString());
                    return null;
                }
                ** GOTO lbl43
            }
            while (object3.hasNext()) {
                provider = (Provider)object3.next();
                try {
                    cipher = Cipher.getInstance(string, provider);
                    cipher.init(n2, (Key)object);
                    break;
                }
                catch (NoSuchPaddingException noSuchPaddingException) {
                }
                catch (InvalidKeyException invalidKeyException) {
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException2) {
                    // empty catch block
                }
                var4_26 = null;
            }
            object3 = var4_27;
            if (var4_27 == null) {
                object2 = CryptoUpcalls.logger;
                object = new StringBuilder();
                object.append("Could not find provider for algorithm: ");
                object.append(string);
                object2.warning(object.toString());
                return null;
            }
        }
        try {
            return object3.doFinal((byte[])object2);
        }
        catch (Exception exception) {
            logger = CryptoUpcalls.logger;
            object2 = Level.WARNING;
            object3 = new StringBuilder();
            object3.append("Exception while decrypting message with ");
            object3.append(object.getAlgorithm());
            object3.append(" private key using ");
            object3.append(string);
            object3.append(":");
            logger.log((Level)object2, object3.toString(), exception);
            return null;
        }
    }

    static byte[] rsaSignDigestWithPrivateKey(PrivateKey privateKey, int n, byte[] arrby) {
        return CryptoUpcalls.rsaOpWithPrivateKey(privateKey, n, 1, arrby);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static byte[] signDigestWithPrivateKey(PrivateKey serializable, byte[] object, String string) {
        block10 : {
            block11 : {
                try {
                    object22 = Signature.getInstance(string);
                    object22.initSign((PrivateKey)serializable);
                    bl = Conscrypt.isConscrypt(object22.getProvider());
                    if (!bl) ** GOTO lbl12
                    invalidKeyException = null;
                }
                catch (InvalidKeyException noSuchAlgorithmException) {
                    CryptoUpcalls.logger.warning("Preferred provider doesn't support key:");
                    noSuchAlgorithmException.printStackTrace();
                    var3_10 = null;
                }
lbl12: // 3 sources:
                object2 = var3_11;
                if (var3_11 != null) break block10;
                object2 = new StringBuilder();
                object2.append("Signature.");
                object2.append(string);
                object2 = CryptoUpcalls.getExternalProviders(object2.toString()).iterator();
                break block11;
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    logger = CryptoUpcalls.logger;
                    object = new StringBuilder();
                    object.append("Unsupported signature algorithm: ");
                    object.append(string);
                    logger.warning(object.toString());
                    return null;
                }
                ** GOTO lbl12
            }
            while (object2.hasNext()) {
                provider = (Provider)object2.next();
                try {
                    signature = Signature.getInstance(string, provider);
                    signature.initSign((PrivateKey)serializable);
                    break;
                }
                catch (InvalidKeyException invalidKeyException) {
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    // empty catch block
                }
                var3_18 = null;
            }
            object2 = var3_19;
            if (var3_19 == null) {
                object = CryptoUpcalls.logger;
                serializable = new StringBuilder();
                serializable.append("Could not find provider for algorithm: ");
                serializable.append(string);
                object.warning(serializable.toString());
                return null;
            }
        }
        try {
            object2.update((byte[])object);
            return object2.sign();
        }
        catch (Exception exception) {
            object = CryptoUpcalls.logger;
            level = Level.WARNING;
            object2 = new StringBuilder();
            object2.append("Exception while signing message with ");
            object2.append(serializable.getAlgorithm());
            object2.append(" private key:");
            object.log(level, object2.toString(), exception);
            return null;
        }
    }
}

