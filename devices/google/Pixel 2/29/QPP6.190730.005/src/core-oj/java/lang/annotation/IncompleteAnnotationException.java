/*
 * Decompiled with CFR 0.145.
 */
package java.lang.annotation;

import java.lang.annotation.Annotation;

public class IncompleteAnnotationException
extends RuntimeException {
    private static final long serialVersionUID = 8445097402741811912L;
    private Class<? extends Annotation> annotationType;
    private String elementName;

    public IncompleteAnnotationException(Class<? extends Annotation> class_, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(class_.getName());
        stringBuilder.append(" missing element ");
        stringBuilder.append(string.toString());
        super(stringBuilder.toString());
        this.annotationType = class_;
        this.elementName = string;
    }

    public Class<? extends Annotation> annotationType() {
        return this.annotationType;
    }

    public String elementName() {
        return this.elementName;
    }
}

