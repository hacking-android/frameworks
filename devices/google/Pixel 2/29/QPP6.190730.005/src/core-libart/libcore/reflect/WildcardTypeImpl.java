/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import libcore.reflect.ListOfTypes;

public final class WildcardTypeImpl
implements WildcardType {
    private final ListOfTypes extendsBound;
    private final ListOfTypes superBound;

    public WildcardTypeImpl(ListOfTypes listOfTypes, ListOfTypes listOfTypes2) {
        this.extendsBound = listOfTypes;
        this.superBound = listOfTypes2;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof WildcardType;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (WildcardType)object;
            if (!Arrays.equals(this.getLowerBounds(), object.getLowerBounds()) || !Arrays.equals(this.getUpperBounds(), object.getUpperBounds())) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public Type[] getLowerBounds() throws TypeNotPresentException, MalformedParameterizedTypeException {
        return (Type[])this.superBound.getResolvedTypes().clone();
    }

    @Override
    public Type[] getUpperBounds() throws TypeNotPresentException, MalformedParameterizedTypeException {
        return (Type[])this.extendsBound.getResolvedTypes().clone();
    }

    public int hashCode() {
        return Arrays.hashCode(this.getLowerBounds()) * 31 + Arrays.hashCode(this.getUpperBounds());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("?");
        if (this.extendsBound.length() == 1 && this.extendsBound.getResolvedTypes()[0] != Object.class || this.extendsBound.length() > 1) {
            stringBuilder.append(" extends ");
            stringBuilder.append(this.extendsBound);
        } else if (this.superBound.length() > 0) {
            stringBuilder.append(" super ");
            stringBuilder.append(this.superBound);
        }
        return stringBuilder.toString();
    }
}

