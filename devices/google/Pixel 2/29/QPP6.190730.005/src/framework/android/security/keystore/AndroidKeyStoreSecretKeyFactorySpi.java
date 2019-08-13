/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.GateKeeper;
import android.security.KeyStore;
import android.security.keymaster.KeyCharacteristics;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterBlob;
import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.AndroidKeyStoreSecretKey;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyProperties;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.ProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.SecretKeySpec;

public class AndroidKeyStoreSecretKeyFactorySpi
extends SecretKeyFactorySpi {
    private final KeyStore mKeyStore = KeyStore.getInstance();

    private static BigInteger getGateKeeperSecureUserId() throws ProviderException {
        try {
            BigInteger bigInteger = BigInteger.valueOf(GateKeeper.getSecureUserId());
            return bigInteger;
        }
        catch (IllegalStateException illegalStateException) {
            throw new ProviderException("Failed to get GateKeeper secure user ID", illegalStateException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static KeyInfo getKeyInfo(KeyStore var0, String var1_4, String var2_5, int var3_6) {
        block17 : {
            block16 : {
                var4_7 = new KeyCharacteristics();
                var3_6 = var0.getKeyCharacteristics((String)var2_5, null, null, var3_6, var4_7);
                var5_8 = true;
                if (var3_6 != 1) {
                    var0 = new StringBuilder();
                    var0.append("Failed to obtain information about key. Keystore error: ");
                    var0.append(var3_6);
                    throw new ProviderException(var0.toString());
                }
                if (!var4_7.hwEnforced.containsTag(268436158)) break block16;
                var3_6 = KeyProperties.Origin.fromKeymaster(var4_7.hwEnforced.getEnum(268436158, -1));
                var6_9 = true;
                break block17;
            }
            if (!var4_7.swEnforced.containsTag(268436158)) ** GOTO lbl41
            var3_6 = KeyProperties.Origin.fromKeymaster(var4_7.swEnforced.getEnum(268436158, -1));
            var6_9 = false;
        }
        var7_10 = var4_7.getUnsignedInt(805306371, -1L);
        if (var7_10 == -1L) ** GOTO lbl39
        if (var7_10 <= Integer.MAX_VALUE) {
            var9_11 = (int)var7_10;
            var10_12 = KeyProperties.Purpose.allFromKeymaster(var4_7.getEnums(536870913));
            var0 = new ArrayList();
            var2_5 = new List<Object>();
            var11_13 = var4_7.getEnums(536870918).iterator();
        } else {
            var1_4 = new StringBuilder();
            var1_4.append("Key too large: ");
            var1_4.append(var7_10);
            var1_4.append(" bits");
            var0 = new ProviderException(var1_4.toString());
            throw var0;
lbl39: // 1 sources:
            var0 = new ProviderException("Key size not available");
            throw var0;
lbl41: // 1 sources:
            var0 = new ProviderException("Key origin not available");
            throw var0;
        }
        while (var11_13.hasNext()) {
            var12_14 = var11_13.next();
        }
        ** GOTO lbl64
        {
            try {
                var0.add(KeyProperties.EncryptionPadding.fromKeymaster(var12_14));
                continue;
            }
            catch (IllegalArgumentException var13_15) {
                try {
                    var2_5.add(KeyProperties.SignaturePadding.fromKeymaster(var12_14));
                    continue;
                }
                catch (IllegalArgumentException var0_1) {
                    try {
                        var1_4 = new StringBuilder();
                        var1_4.append("Unsupported encryption padding: ");
                        var1_4.append(var12_14);
                        var0_2 = new ProviderException(var1_4.toString());
                        throw var0_2;
lbl64: // 1 sources:
                        var0 = var0.toArray(new String[var0.size()]);
                        var14_17 = var2_5.toArray(new String[var2_5.size()]);
                        var15_18 = KeyProperties.Digest.allFromKeymaster(var4_7.getEnums(536870917));
                        var11_13 = KeyProperties.BlockMode.allFromKeymaster(var4_7.getEnums(536870916));
                        var16_19 = var4_7.swEnforced.getEnum(268435960, 0);
                        var12_14 = var4_7.hwEnforced.getEnum(268435960, 0);
                        var2_5 = var4_7.getUnsignedLongs(-1610612234);
                    }
                    catch (IllegalArgumentException var0_3) {
                        throw new ProviderException("Unsupported key characteristic", var0_3);
                        break;
                    }
                    var17_20 = var4_7.getDate(1610613136);
                    var13_16 = var4_7.getDate(1610613137);
                    var18_21 = var4_7.getDate(1610613138);
                    var19_22 = var4_7.getBoolean(1879048695) ^ true;
                    var7_10 = var4_7.getUnsignedInt(805306873, -1L);
                    if (var7_10 > Integer.MAX_VALUE) {
                        var0 = new StringBuilder();
                        var0.append("User authentication timeout validity too long: ");
                        var0.append(var7_10);
                        var0.append(" seconds");
                        throw new ProviderException(var0.toString());
                    }
                    var20_23 = var19_22 != false && var12_14 != 0 && var16_19 == 0;
                    var21_24 = var4_7.hwEnforced.getBoolean(1879048698);
                    var22_25 = var4_7.hwEnforced.getBoolean(1879048699);
                    if (var16_19 != 2 && var12_14 != 2) {
                        var5_8 = false;
                    } else if (var2_5 == null || var2_5.isEmpty() || var2_5.contains(AndroidKeyStoreSecretKeyFactorySpi.getGateKeeperSecureUserId())) {
                        var5_8 = false;
                    }
                    var23_26 = var4_7.hwEnforced.getBoolean(1879048700);
                    return new KeyInfo((String)var1_4, var6_9, var3_6, var9_11, var17_20, var13_16, var18_21, var10_12, (String[])var0, var14_17, var15_18, var11_13, var19_22, (int)var7_10, var20_23, var21_24, var22_25, var5_8, var23_26);
                }
            }
        }
    }

    @Override
    protected SecretKey engineGenerateSecret(KeySpec object) throws InvalidKeySpecException {
        object = new StringBuilder();
        ((StringBuilder)object).append("To generate secret key in Android Keystore, use KeyGenerator initialized with ");
        ((StringBuilder)object).append(KeyGenParameterSpec.class.getName());
        throw new InvalidKeySpecException(((StringBuilder)object).toString());
    }

    protected KeySpec engineGetKeySpec(SecretKey object, Class object2) throws InvalidKeySpecException {
        block4 : {
            block5 : {
                block6 : {
                    block9 : {
                        AndroidKeyStoreKey androidKeyStoreKey;
                        block8 : {
                            block7 : {
                                if (object2 == null) break block4;
                                if (!(object instanceof AndroidKeyStoreSecretKey)) {
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append("Only Android KeyStore secret keys supported: ");
                                    object = object != null ? object.getClass().getName() : "null";
                                    ((StringBuilder)object2).append((String)object);
                                    throw new InvalidKeySpecException(((StringBuilder)object2).toString());
                                }
                                if (SecretKeySpec.class.isAssignableFrom((Class<?>)object2)) break block5;
                                if (!KeyInfo.class.equals(object2)) break block6;
                                androidKeyStoreKey = (AndroidKeyStoreKey)object;
                                object2 = androidKeyStoreKey.getAlias();
                                if (!((String)object2).startsWith("USRPKEY_")) break block7;
                                object = ((String)object2).substring("USRPKEY_".length());
                                break block8;
                            }
                            if (!((String)object2).startsWith("USRSKEY_")) break block9;
                            object = ((String)object2).substring("USRSKEY_".length());
                        }
                        return AndroidKeyStoreSecretKeyFactorySpi.getKeyInfo(this.mKeyStore, (String)object, (String)object2, androidKeyStoreKey.getUid());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid key alias: ");
                    ((StringBuilder)object).append((String)object2);
                    throw new InvalidKeySpecException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported key spec: ");
                ((StringBuilder)object).append(((Class)object2).getName());
                throw new InvalidKeySpecException(((StringBuilder)object).toString());
            }
            throw new InvalidKeySpecException("Key material export of Android KeyStore keys is not supported");
        }
        throw new InvalidKeySpecException("keySpecClass == null");
    }

    @Override
    protected SecretKey engineTranslateKey(SecretKey secretKey) throws InvalidKeyException {
        if (secretKey != null) {
            if (secretKey instanceof AndroidKeyStoreSecretKey) {
                return secretKey;
            }
            throw new InvalidKeyException("To import a secret key into Android Keystore, use KeyStore.setEntry");
        }
        throw new InvalidKeyException("key == null");
    }
}

