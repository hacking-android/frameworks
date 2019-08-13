/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLECGroupContext;
import com.android.org.conscrypt.OpenSSLECPrivateKey;
import com.android.org.conscrypt.OpenSSLECPublicKey;
import com.android.org.conscrypt.OpenSSLKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class OpenSSLECKeyPairGenerator
extends KeyPairGenerator {
    private static final String ALGORITHM = "EC";
    private static final int DEFAULT_KEY_SIZE = 256;
    private static final Map<Integer, String> SIZE_TO_CURVE_NAME = new HashMap<Integer, String>();
    private OpenSSLECGroupContext group;

    static {
        SIZE_TO_CURVE_NAME.put(224, "secp224r1");
        SIZE_TO_CURVE_NAME.put(256, "prime256v1");
        SIZE_TO_CURVE_NAME.put(384, "secp384r1");
        SIZE_TO_CURVE_NAME.put(521, "secp521r1");
    }

    public OpenSSLECKeyPairGenerator() {
        super(ALGORITHM);
    }

    public static void assertCurvesAreValid() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string : SIZE_TO_CURVE_NAME.values()) {
            if (OpenSSLECGroupContext.getCurveByName(string) != null) continue;
            arrayList.add(string);
        }
        if (arrayList.size() <= 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid curve names: ");
        stringBuilder.append(Arrays.toString(arrayList.toArray()));
        throw new AssertionError((Object)stringBuilder.toString());
    }

    @Override
    public KeyPair generateKeyPair() {
        Object object;
        if (this.group == null) {
            object = SIZE_TO_CURVE_NAME.get(256);
            this.group = OpenSSLECGroupContext.getCurveByName((String)object);
            if (this.group == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Curve not recognized: ");
                stringBuilder.append((String)object);
                throw new RuntimeException(stringBuilder.toString());
            }
        }
        object = new OpenSSLKey(NativeCrypto.EC_KEY_generate_key(this.group.getNativeRef()));
        return new KeyPair(new OpenSSLECPublicKey(this.group, (OpenSSLKey)object), new OpenSSLECPrivateKey(this.group, (OpenSSLKey)object));
    }

    @Override
    public void initialize(int n, SecureRandom object) {
        object = SIZE_TO_CURVE_NAME.get(n);
        if (object != null) {
            Object object2 = OpenSSLECGroupContext.getCurveByName((String)object);
            if (object2 != null) {
                this.group = object2;
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("unknown curve ");
            ((StringBuilder)object2).append((String)object);
            throw new InvalidParameterException(((StringBuilder)object2).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("unknown key size ");
        ((StringBuilder)object).append(n);
        throw new InvalidParameterException(((StringBuilder)object).toString());
    }

    @Override
    public void initialize(AlgorithmParameterSpec object, SecureRandom object2) throws InvalidAlgorithmParameterException {
        block4 : {
            block5 : {
                block3 : {
                    block2 : {
                        if (!(object instanceof ECParameterSpec)) break block2;
                        this.group = OpenSSLECGroupContext.getInstance((ECParameterSpec)object);
                        break block3;
                    }
                    if (!(object instanceof ECGenParameterSpec)) break block4;
                    object2 = OpenSSLECGroupContext.getCurveByName((String)(object = ((ECGenParameterSpec)object).getName()));
                    if (object2 == null) break block5;
                    this.group = object2;
                }
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("unknown curve name: ");
            ((StringBuilder)object2).append((String)object);
            throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
        }
        throw new InvalidAlgorithmParameterException("parameter must be ECParameterSpec or ECGenParameterSpec");
    }
}

