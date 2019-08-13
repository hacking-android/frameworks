/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.Objects;
import libcore.reflect.ParameterizedTypeImpl;

public final class GenericArrayTypeImpl
implements GenericArrayType {
    private final Type componentType;

    public GenericArrayTypeImpl(Type type) {
        this.componentType = type;
    }

    public boolean equals(Object object) {
        if (!(object instanceof GenericArrayType)) {
            return false;
        }
        object = (GenericArrayType)object;
        return Objects.equals(this.getGenericComponentType(), object.getGenericComponentType());
    }

    @Override
    public Type getGenericComponentType() {
        try {
            Type type = ((ParameterizedTypeImpl)this.componentType).getResolvedType();
            return type;
        }
        catch (ClassCastException classCastException) {
            return this.componentType;
        }
    }

    public int hashCode() {
        return Objects.hashCode(this.getGenericComponentType());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.componentType.toString());
        stringBuilder.append("[]");
        return stringBuilder.toString();
    }
}

