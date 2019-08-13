/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public final class AnnotatedElements {
    private AnnotatedElements() {
    }

    public static <T extends Annotation> T[] getDirectOrIndirectAnnotationsByType(AnnotatedElement arrannotation, Class<T> class_) {
        if (class_ != null) {
            arrannotation = arrannotation.getDeclaredAnnotations();
            ArrayList<Annotation> arrayList = new ArrayList<Annotation>();
            Class<Annotation> class_2 = AnnotatedElements.getRepeatableAnnotationContainerClassFor(class_);
            for (int i = 0; i < arrannotation.length; ++i) {
                if (class_.isInstance(arrannotation[i])) {
                    arrayList.add(arrannotation[i]);
                    continue;
                }
                if (class_2 == null || !class_2.isInstance(arrannotation[i])) continue;
                AnnotatedElements.insertAnnotationValues(arrannotation[i], class_, arrayList);
            }
            return arrayList.toArray((Annotation[])Array.newInstance(class_, 0));
        }
        throw new NullPointerException("annotationClass");
    }

    private static <T extends Annotation> Class<? extends Annotation> getRepeatableAnnotationContainerClassFor(Class<T> class_) {
        class_ = (class_ = class_.getDeclaredAnnotation(Repeatable.class)) == null ? null : class_.value();
        return class_;
    }

    private static <T extends Annotation> void insertAnnotationValues(Annotation arrannotation, Class<T> class_, ArrayList<T> serializable) {
        Method method;
        ((Annotation[])Array.newInstance(class_, 0)).getClass();
        try {
            method = arrannotation.getClass().getDeclaredMethod("value", new Class[0]);
        }
        catch (SecurityException securityException) {
            throw new IncompleteAnnotationException(arrannotation.getClass(), "value");
        }
        catch (NoSuchMethodException noSuchMethodException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("annotation container = ");
            stringBuilder.append(arrannotation);
            stringBuilder.append("annotation element class = ");
            stringBuilder.append(class_);
            stringBuilder.append("; missing value() method");
            throw new AssertionError((Object)stringBuilder.toString());
        }
        if (method.getReturnType().isArray()) {
            if (class_.equals(method.getReturnType().getComponentType())) {
                try {
                    arrannotation = (Annotation[])method.invoke(arrannotation, new Object[0]);
                }
                catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
                    throw new AssertionError(reflectiveOperationException);
                }
                for (int i = 0; i < arrannotation.length; ++i) {
                    ((ArrayList)serializable).add(arrannotation[i]);
                }
                return;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("annotation container = ");
            ((StringBuilder)serializable).append(arrannotation);
            ((StringBuilder)serializable).append("annotation element class = ");
            ((StringBuilder)serializable).append(class_);
            ((StringBuilder)serializable).append("; value() returns incorrect type");
            throw new AssertionError((Object)((StringBuilder)serializable).toString());
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("annotation container = ");
        ((StringBuilder)serializable).append(arrannotation);
        ((StringBuilder)serializable).append("annotation element class = ");
        ((StringBuilder)serializable).append(class_);
        ((StringBuilder)serializable).append("; value() doesn't return array");
        throw new AssertionError((Object)((StringBuilder)serializable).toString());
    }
}

