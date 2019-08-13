/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import libcore.reflect.ParameterizedTypeImpl;
import libcore.util.EmptyArray;

public final class ListOfTypes {
    public static final ListOfTypes EMPTY = new ListOfTypes(0);
    private Type[] resolvedTypes;
    private final ArrayList<Type> types;

    ListOfTypes(int n) {
        this.types = new ArrayList(n);
    }

    ListOfTypes(Type[] arrtype) {
        this.types = new ArrayList(arrtype.length);
        for (Type type : arrtype) {
            this.types.add(type);
        }
    }

    private Type[] resolveTypes(List<Type> list) {
        int n = list.size();
        if (n == 0) {
            return EmptyArray.TYPE;
        }
        Type[] arrtype = new Type[n];
        for (int i = 0; i < n; ++i) {
            Type type = list.get(i);
            arrtype[i] = type instanceof ParameterizedTypeImpl ? ((ParameterizedTypeImpl)type).getResolvedType() : type;
        }
        return arrtype;
    }

    void add(Type type) {
        if (type != null) {
            this.types.add(type);
            return;
        }
        throw new NullPointerException("type == null");
    }

    public Type[] getResolvedTypes() {
        Type[] arrtype;
        Type[] arrtype2 = arrtype = this.resolvedTypes;
        if (arrtype == null) {
            arrtype2 = this.resolveTypes(this.types);
            this.resolvedTypes = arrtype2;
        }
        return arrtype2;
    }

    int length() {
        return this.types.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.types.size(); ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.types.get(i));
        }
        return stringBuilder.toString();
    }
}

