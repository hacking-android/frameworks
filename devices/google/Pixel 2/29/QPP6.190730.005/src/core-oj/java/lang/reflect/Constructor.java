/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  libcore.util.EmptyArray
 */
package java.lang.reflect;

import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Comparator;
import libcore.util.EmptyArray;
import sun.reflect.CallerSensitive;

public final class Constructor<T>
extends Executable {
    private static final Comparator<Method> ORDER_BY_SIGNATURE = null;
    private final Class<?> serializationClass;
    private final Class<?> serializationCtor;

    private Constructor() {
        this(null, null);
    }

    private Constructor(Class<?> class_, Class<?> class_2) {
        this.serializationCtor = class_;
        this.serializationClass = class_2;
    }

    @FastNative
    private native T newInstance0(Object ... var1) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    @FastNative
    private static native Object newInstanceFromSerialization(Class<?> var0, Class<?> var1) throws InstantiationException, IllegalArgumentException, InvocationTargetException;

    public boolean equals(Object object) {
        if (object != null && object instanceof Constructor) {
            object = (Constructor)object;
            if (this.getDeclaringClass() == ((Constructor)object).getDeclaringClass()) {
                return this.equalParamTypes(this.getParameterTypes(), ((Constructor)object).getParameterTypes());
            }
        }
        return false;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> class_) {
        return super.getAnnotation(class_);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return super.getDeclaredAnnotations();
    }

    public Class<T> getDeclaringClass() {
        return super.getDeclaringClassInternal();
    }

    @FastNative
    @Override
    public native Class<?>[] getExceptionTypes();

    @Override
    public Type[] getGenericExceptionTypes() {
        return super.getGenericExceptionTypes();
    }

    @Override
    public Type[] getGenericParameterTypes() {
        return super.getGenericParameterTypes();
    }

    @Override
    public int getModifiers() {
        return super.getModifiersInternal();
    }

    @Override
    public String getName() {
        return this.getDeclaringClass().getName();
    }

    @Override
    public Annotation[][] getParameterAnnotations() {
        return super.getParameterAnnotationsInternal();
    }

    @Override
    public int getParameterCount() {
        return super.getParameterCountInternal();
    }

    @Override
    public Class<?>[] getParameterTypes() {
        Class<?>[] arrclass = super.getParameterTypesInternal();
        if (arrclass == null) {
            return EmptyArray.CLASS;
        }
        return arrclass;
    }

    public TypeVariable<Constructor<T>>[] getTypeParameters() {
        return (TypeVariable[])this.getMethodOrConstructorGenericInfoInternal().formalTypeParameters.clone();
    }

    @Override
    boolean hasGenericInformation() {
        return super.hasGenericInformationInternal();
    }

    public int hashCode() {
        return this.getDeclaringClass().getName().hashCode();
    }

    @Override
    public boolean isSynthetic() {
        return super.isSynthetic();
    }

    @Override
    public boolean isVarArgs() {
        return super.isVarArgs();
    }

    @CallerSensitive
    public T newInstance(Object ... arrobject) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> class_ = this.serializationClass;
        if (class_ == null) {
            return this.newInstance0(arrobject);
        }
        return (T)Constructor.newInstanceFromSerialization(this.serializationCtor, class_);
    }

    public Constructor<T> serializationCopy(Class<?> class_, Class<?> class_2) {
        return new Constructor<T>(class_, class_2);
    }

    @Override
    void specificToGenericStringHeader(StringBuilder stringBuilder) {
        this.specificToStringHeader(stringBuilder);
    }

    @Override
    void specificToStringHeader(StringBuilder stringBuilder) {
        stringBuilder.append(this.getDeclaringClass().getTypeName());
    }

    @Override
    public String toGenericString() {
        return this.sharedToGenericString(Modifier.constructorModifiers(), false);
    }

    public String toString() {
        return this.sharedToString(Modifier.constructorModifiers(), false, this.getParameterTypes(), this.getExceptionTypes());
    }
}

