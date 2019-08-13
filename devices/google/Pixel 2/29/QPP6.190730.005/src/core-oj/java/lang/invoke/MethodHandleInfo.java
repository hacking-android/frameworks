/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import java.lang.invoke.MethodHandleNatives;
import java.lang.invoke.MethodHandleStatics;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Objects;

public interface MethodHandleInfo {
    public static final int REF_getField = 1;
    public static final int REF_getStatic = 2;
    public static final int REF_invokeInterface = 9;
    public static final int REF_invokeSpecial = 7;
    public static final int REF_invokeStatic = 6;
    public static final int REF_invokeVirtual = 5;
    public static final int REF_newInvokeSpecial = 8;
    public static final int REF_putField = 3;
    public static final int REF_putStatic = 4;

    @Deprecated
    public static boolean refKindIsField(int n) {
        return MethodHandleNatives.refKindIsField((byte)n);
    }

    @Deprecated
    public static boolean refKindIsValid(int n) {
        return MethodHandleNatives.refKindIsValid(n);
    }

    @Deprecated
    public static String refKindName(int n) {
        return MethodHandleNatives.refKindName((byte)n);
    }

    public static String referenceKindToString(int n) {
        if (MethodHandleNatives.refKindIsValid(n)) {
            return MethodHandleNatives.refKindName((byte)n);
        }
        throw MethodHandleStatics.newIllegalArgumentException("invalid reference kind", n);
    }

    public static String toString(int n, Class<?> class_, String string, MethodType methodType) {
        Objects.requireNonNull(string);
        Objects.requireNonNull(methodType);
        return String.format("%s %s.%s:%s", MethodHandleInfo.referenceKindToString(n), class_.getName(), string, methodType);
    }

    public Class<?> getDeclaringClass();

    public MethodType getMethodType();

    public int getModifiers();

    public String getName();

    public int getReferenceKind();

    default public boolean isVarArgs() {
        if (MethodHandleNatives.refKindIsField((byte)this.getReferenceKind())) {
            return false;
        }
        return Modifier.isTransient(this.getModifiers());
    }

    public <T extends Member> T reflectAs(Class<T> var1, MethodHandles.Lookup var2);
}

