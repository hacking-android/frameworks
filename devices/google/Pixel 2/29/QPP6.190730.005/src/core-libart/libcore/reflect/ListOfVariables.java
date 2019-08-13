/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

final class ListOfVariables {
    final ArrayList<TypeVariable<?>> array = new ArrayList();

    ListOfVariables() {
    }

    void add(TypeVariable<?> typeVariable) {
        this.array.add(typeVariable);
    }

    TypeVariable<?>[] getArray() {
        TypeVariable[] arrtypeVariable = new TypeVariable[this.array.size()];
        return this.array.toArray(arrtypeVariable);
    }
}

