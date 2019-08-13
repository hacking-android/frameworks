/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import libcore.reflect.ListOfTypes;

public final class ParameterizedTypeImpl
implements ParameterizedType {
    private final ListOfTypes args;
    private final ClassLoader loader;
    private final ParameterizedTypeImpl ownerType0;
    private Type ownerTypeRes;
    private Class rawType;
    private final String rawTypeName;

    public ParameterizedTypeImpl(ParameterizedTypeImpl parameterizedTypeImpl, String string, ListOfTypes listOfTypes, ClassLoader classLoader) {
        if (listOfTypes != null) {
            this.ownerType0 = parameterizedTypeImpl;
            this.rawTypeName = string;
            this.args = listOfTypes;
            this.loader = classLoader;
            return;
        }
        throw new NullPointerException();
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof ParameterizedType;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (ParameterizedType)object;
            if (!Objects.equals(this.getRawType(), object.getRawType()) || !Objects.equals(this.getOwnerType(), object.getOwnerType()) || !Arrays.equals(this.args.getResolvedTypes(), object.getActualTypeArguments())) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return (Type[])this.args.getResolvedTypes().clone();
    }

    @Override
    public Type getOwnerType() {
        if (this.ownerTypeRes == null) {
            ParameterizedTypeImpl parameterizedTypeImpl = this.ownerType0;
            this.ownerTypeRes = parameterizedTypeImpl != null ? parameterizedTypeImpl.getResolvedType() : this.getRawType().getDeclaringClass();
        }
        return this.ownerTypeRes;
    }

    @Override
    public Class getRawType() {
        if (this.rawType == null) {
            try {
                this.rawType = Class.forName(this.rawTypeName, false, this.loader);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new TypeNotPresentException(this.rawTypeName, classNotFoundException);
            }
        }
        return this.rawType;
    }

    Type getResolvedType() {
        if (this.args.getResolvedTypes().length == 0) {
            return this.getRawType();
        }
        return this;
    }

    public int hashCode() {
        return (Objects.hashCode(this.getRawType()) * 31 + Objects.hashCode(this.getOwnerType())) * 31 + Arrays.hashCode(this.args.getResolvedTypes());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.rawTypeName);
        if (this.args.length() > 0) {
            stringBuilder.append("<");
            stringBuilder.append(this.args);
            stringBuilder.append(">");
        }
        return stringBuilder.toString();
    }
}

