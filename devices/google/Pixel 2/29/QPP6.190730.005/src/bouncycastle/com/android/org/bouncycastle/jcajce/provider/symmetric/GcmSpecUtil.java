/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric;

import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.cms.GCMParameters;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil;
import com.android.org.bouncycastle.util.Integers;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

class GcmSpecUtil {
    static final Class gcmSpecClass = ClassUtil.loadClass(GcmSpecUtil.class, "javax.crypto.spec.GCMParameterSpec");

    GcmSpecUtil() {
    }

    static GCMParameters extractGcmParameters(AlgorithmParameterSpec object) throws InvalidParameterSpecException {
        try {
            Method method = gcmSpecClass.getDeclaredMethod("getTLen", new Class[0]);
            object = new GCMParameters((byte[])gcmSpecClass.getDeclaredMethod("getIV", new Class[0]).invoke(object, new Object[0]), (Integer)method.invoke(object, new Object[0]) / 8);
            return object;
        }
        catch (Exception exception) {
            throw new InvalidParameterSpecException("Cannot process GCMParameterSpec");
        }
    }

    static AlgorithmParameterSpec extractGcmSpec(ASN1Primitive object) throws InvalidParameterSpecException {
        try {
            object = GCMParameters.getInstance(object);
            object = (AlgorithmParameterSpec)gcmSpecClass.getConstructor(Integer.TYPE, byte[].class).newInstance(Integers.valueOf(((GCMParameters)object).getIcvLen() * 8), ((GCMParameters)object).getNonce());
            return object;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Construction failed: ");
            ((StringBuilder)object).append(exception.getMessage());
            throw new InvalidParameterSpecException(((StringBuilder)object).toString());
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new InvalidParameterSpecException("No constructor found!");
        }
    }

    static boolean gcmSpecExists() {
        boolean bl = gcmSpecClass != null;
        return bl;
    }

    static boolean isGcmSpec(Class class_) {
        boolean bl = gcmSpecClass == class_;
        return bl;
    }

    static boolean isGcmSpec(AlgorithmParameterSpec algorithmParameterSpec) {
        Class class_ = gcmSpecClass;
        boolean bl = class_ != null && class_.isInstance(algorithmParameterSpec);
        return bl;
    }
}

