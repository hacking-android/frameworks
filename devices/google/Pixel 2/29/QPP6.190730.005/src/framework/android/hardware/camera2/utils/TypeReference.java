/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.util.Preconditions;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

public abstract class TypeReference<T> {
    private final int mHash;
    private final Type mType;

    @UnsupportedAppUsage
    protected TypeReference() {
        this.mType = ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (!TypeReference.containsTypeVariable(this.mType)) {
            this.mHash = this.mType.hashCode();
            return;
        }
        throw new IllegalArgumentException("Including a type variable in a type reference is not allowed");
    }

    private TypeReference(Type type) {
        this.mType = type;
        if (!TypeReference.containsTypeVariable(this.mType)) {
            this.mHash = this.mType.hashCode();
            return;
        }
        throw new IllegalArgumentException("Including a type variable in a type reference is not allowed");
    }

    public static boolean containsTypeVariable(Type object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (object instanceof TypeVariable) {
            return true;
        }
        if (object instanceof Class) {
            if (((Class)(object = (Class)object)).getTypeParameters().length != 0) {
                return true;
            }
            return TypeReference.containsTypeVariable(((Class)object).getDeclaringClass());
        }
        if (object instanceof ParameterizedType) {
            object = ((ParameterizedType)object).getActualTypeArguments();
            int n = ((Type[])object).length;
            for (int i = 0; i < n; ++i) {
                if (!TypeReference.containsTypeVariable((Type)object[i])) continue;
                return true;
            }
            return false;
        }
        if (object instanceof WildcardType) {
            if (TypeReference.containsTypeVariable((object = (WildcardType)object).getLowerBounds()) || TypeReference.containsTypeVariable(object.getUpperBounds())) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private static boolean containsTypeVariable(Type[] arrtype) {
        if (arrtype == null) {
            return false;
        }
        int n = arrtype.length;
        for (int i = 0; i < n; ++i) {
            if (!TypeReference.containsTypeVariable(arrtype[i])) continue;
            return true;
        }
        return false;
    }

    public static <T> TypeReference<T> createSpecializedTypeReference(Class<T> class_) {
        return new SpecializedTypeReference<T>(class_);
    }

    @UnsupportedAppUsage
    public static TypeReference<?> createSpecializedTypeReference(Type type) {
        return new SpecializedBaseTypeReference(type);
    }

    private static final Class<?> getArrayClass(Class<?> class_) {
        return Array.newInstance(class_, 0).getClass();
    }

    private static Type getComponentType(Type type) {
        Preconditions.checkNotNull(type, "type must not be null");
        if (type instanceof Class) {
            return ((Class)type).getComponentType();
        }
        if (type instanceof ParameterizedType) {
            return null;
        }
        if (type instanceof GenericArrayType) {
            return ((GenericArrayType)type).getGenericComponentType();
        }
        if (!(type instanceof WildcardType)) {
            if (type instanceof TypeVariable) {
                throw new AssertionError((Object)"Type variables are not allowed in type references");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unhandled branch to get component type for type ");
            stringBuilder.append(type);
            throw new AssertionError((Object)stringBuilder.toString());
        }
        throw new UnsupportedOperationException("TODO: support wild card components");
    }

    private static final Class<?> getRawType(Type type) {
        if (type != null) {
            if (type instanceof Class) {
                return (Class)type;
            }
            if (type instanceof ParameterizedType) {
                return (Class)((ParameterizedType)type).getRawType();
            }
            if (type instanceof GenericArrayType) {
                return TypeReference.getArrayClass(TypeReference.getRawType(((GenericArrayType)type).getGenericComponentType()));
            }
            if (type instanceof WildcardType) {
                return TypeReference.getRawType(((WildcardType)type).getUpperBounds());
            }
            if (type instanceof TypeVariable) {
                throw new AssertionError((Object)"Type variables are not allowed in type references");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unhandled branch to get raw type for type ");
            stringBuilder.append(type);
            throw new AssertionError((Object)stringBuilder.toString());
        }
        throw new NullPointerException("type must not be null");
    }

    private static final Class<?> getRawType(Type[] arrtype) {
        if (arrtype == null) {
            return null;
        }
        int n = arrtype.length;
        for (int i = 0; i < n; ++i) {
            Class<?> class_ = TypeReference.getRawType(arrtype[i]);
            if (class_ == null) continue;
            return class_;
        }
        return null;
    }

    private static void toString(Type type, StringBuilder stringBuilder) {
        if (type == null) {
            return;
        }
        if (type instanceof TypeVariable) {
            stringBuilder.append(((TypeVariable)type).getName());
        } else if (type instanceof Class) {
            type = (Class)type;
            stringBuilder.append(((Class)type).getName());
            TypeReference.toString(((Class)type).getTypeParameters(), stringBuilder);
        } else if (type instanceof ParameterizedType) {
            type = (ParameterizedType)type;
            stringBuilder.append(((Class)type.getRawType()).getName());
            TypeReference.toString(type.getActualTypeArguments(), stringBuilder);
        } else if (type instanceof GenericArrayType) {
            TypeReference.toString(((GenericArrayType)type).getGenericComponentType(), stringBuilder);
            stringBuilder.append("[]");
        } else {
            stringBuilder.append(type.toString());
        }
    }

    private static void toString(Type[] arrtype, StringBuilder stringBuilder) {
        if (arrtype == null) {
            return;
        }
        if (arrtype.length == 0) {
            return;
        }
        stringBuilder.append("<");
        for (int i = 0; i < arrtype.length; ++i) {
            TypeReference.toString(arrtype[i], stringBuilder);
            if (i == arrtype.length - 1) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append(">");
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof TypeReference && this.mType.equals(((TypeReference)object).mType);
        return bl;
    }

    public TypeReference<?> getComponentType() {
        Object object = TypeReference.getComponentType(this.mType);
        object = object != null ? TypeReference.createSpecializedTypeReference((Type)object) : null;
        return object;
    }

    public final Class<? super T> getRawType() {
        return TypeReference.getRawType(this.mType);
    }

    public Type getType() {
        return this.mType;
    }

    public int hashCode() {
        return this.mHash;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TypeReference<");
        TypeReference.toString(this.getType(), stringBuilder);
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    private static class SpecializedBaseTypeReference
    extends TypeReference {
        public SpecializedBaseTypeReference(Type type) {
            super(type);
        }
    }

    private static class SpecializedTypeReference<T>
    extends TypeReference<T> {
        public SpecializedTypeReference(Class<T> class_) {
            super(class_);
        }
    }

}

