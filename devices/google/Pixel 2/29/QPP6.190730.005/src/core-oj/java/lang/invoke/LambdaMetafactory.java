/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaConversionException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class LambdaMetafactory {
    public static final int FLAG_BRIDGES = 4;
    public static final int FLAG_MARKERS = 2;
    public static final int FLAG_SERIALIZABLE = 1;

    public static CallSite altMetafactory(MethodHandles.Lookup lookup, String string, MethodType methodType, Object ... arrobject) throws LambdaConversionException {
        return null;
    }

    public static CallSite metafactory(MethodHandles.Lookup lookup, String string, MethodType methodType, MethodType methodType2, MethodHandle methodHandle, MethodType methodType3) throws LambdaConversionException {
        return null;
    }
}

