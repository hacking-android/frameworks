/*
 * Decompiled with CFR 0.145.
 */
package java.lang.annotation;

import java.lang.reflect.Method;

public class AnnotationTypeMismatchException
extends RuntimeException {
    private static final long serialVersionUID = 8125925355765570191L;
    private final Method element;
    private final String foundType;

    public AnnotationTypeMismatchException(Method method, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Incorrectly typed data found for annotation element ");
        stringBuilder.append(method);
        stringBuilder.append(" (Found data of type ");
        stringBuilder.append(string);
        stringBuilder.append(")");
        super(stringBuilder.toString());
        this.element = method;
        this.foundType = string;
    }

    public Method element() {
        return this.element;
    }

    public String foundType() {
        return this.foundType;
    }
}

