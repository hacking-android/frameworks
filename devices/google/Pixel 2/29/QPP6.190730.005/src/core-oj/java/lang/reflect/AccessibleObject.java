/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AccessibleObject
implements AnnotatedElement {
    boolean override;

    protected AccessibleObject() {
    }

    public static void setAccessible(AccessibleObject[] arraccessibleObject, boolean bl) throws SecurityException {
        for (int i = 0; i < arraccessibleObject.length; ++i) {
            AccessibleObject.setAccessible0(arraccessibleObject[i], bl);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void setAccessible0(AccessibleObject accessibleObject, boolean bl) throws SecurityException {
        if (accessibleObject instanceof Constructor && bl) {
            Constructor constructor = (Constructor)accessibleObject;
            Class class_ = constructor.getDeclaringClass();
            if (constructor.getDeclaringClass() == Class.class) throw new SecurityException("Can not make a java.lang.Class constructor accessible");
            if (class_ == Method.class) throw new SecurityException("Can not make a java.lang.reflect.Method constructor accessible");
            if (class_ == Field.class) {
                throw new SecurityException("Can not make a java.lang.reflect.Field constructor accessible");
            }
        }
        accessibleObject.override = bl;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> class_) {
        throw new AssertionError((Object)"All subclasses should override this method");
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.getDeclaredAnnotations();
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> class_) {
        throw new AssertionError((Object)"All subclasses should override this method");
    }

    @Override
    public <T extends Annotation> T getDeclaredAnnotation(Class<T> class_) {
        return this.getAnnotation(class_);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        throw new AssertionError((Object)"All subclasses should override this method");
    }

    @Override
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> class_) {
        return this.getAnnotationsByType(class_);
    }

    public boolean isAccessible() {
        return this.override;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> class_) {
        return AnnotatedElement.super.isAnnotationPresent(class_);
    }

    public void setAccessible(boolean bl) throws SecurityException {
        AccessibleObject.setAccessible0(this, bl);
    }
}

