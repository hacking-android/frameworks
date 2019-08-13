/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.reflect.AnnotatedElements
 */
package java.lang.reflect;

import java.lang.annotation.Annotation;
import java.util.Objects;
import libcore.reflect.AnnotatedElements;

public interface AnnotatedElement {
    public <T extends Annotation> T getAnnotation(Class<T> var1);

    public Annotation[] getAnnotations();

    default public <T extends Annotation> T[] getAnnotationsByType(Class<T> class_) {
        return AnnotatedElements.getDirectOrIndirectAnnotationsByType((AnnotatedElement)this, class_);
    }

    default public <T extends Annotation> T getDeclaredAnnotation(Class<T> class_) {
        Objects.requireNonNull(class_);
        for (Annotation annotation : this.getDeclaredAnnotations()) {
            if (!class_.equals(annotation.annotationType())) continue;
            return (T)((Annotation)class_.cast(annotation));
        }
        return null;
    }

    public Annotation[] getDeclaredAnnotations();

    default public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> class_) {
        return AnnotatedElements.getDirectOrIndirectAnnotationsByType((AnnotatedElement)this, class_);
    }

    default public boolean isAnnotationPresent(Class<? extends Annotation> class_) {
        boolean bl = this.getAnnotation(class_) != null;
        return bl;
    }
}

