/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  libcore.reflect.Types
 *  libcore.util.EmptyArray
 */
package java.lang.reflect;

import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Comparator;
import libcore.reflect.Types;
import libcore.util.EmptyArray;
import sun.reflect.CallerSensitive;

public final class Method
extends Executable {
    public static final Comparator<Method> ORDER_BY_SIGNATURE = new Comparator<Method>(){

        @Override
        public int compare(Method genericDeclaration, Method genericDeclaration2) {
            int n;
            if (genericDeclaration == genericDeclaration2) {
                return 0;
            }
            int n2 = n = ((Method)genericDeclaration).getName().compareTo(((Method)genericDeclaration2).getName());
            if (n == 0) {
                n2 = n = ((Executable)genericDeclaration).compareMethodParametersInternal((Method)genericDeclaration2);
                if (n == 0) {
                    n2 = (genericDeclaration = ((Method)genericDeclaration).getReturnType()) == (genericDeclaration2 = ((Method)genericDeclaration2).getReturnType()) ? 0 : ((Class)genericDeclaration).getName().compareTo(((Class)genericDeclaration2).getName());
                }
            }
            return n2;
        }
    };

    private Method() {
    }

    boolean equalNameAndParameters(Method method) {
        return this.equalNameAndParametersInternal(method);
    }

    public boolean equals(Object object) {
        if (object != null && object instanceof Method) {
            object = (Method)object;
            if (this.getDeclaringClass() == ((Method)object).getDeclaringClass() && this.getName() == ((Method)object).getName()) {
                if (!this.getReturnType().equals(((Method)object).getReturnType())) {
                    return false;
                }
                return this.equalParamTypes(this.getParameterTypes(), ((Method)object).getParameterTypes());
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

    @Override
    public Class<?> getDeclaringClass() {
        return super.getDeclaringClassInternal();
    }

    @FastNative
    public native Object getDefaultValue();

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

    public Type getGenericReturnType() {
        return Types.getType((Type)this.getMethodOrConstructorGenericInfoInternal().genericReturnType);
    }

    @Override
    public int getModifiers() {
        return super.getModifiersInternal();
    }

    @Override
    public String getName() {
        return this.getMethodNameInternal();
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

    public Class<?> getReturnType() {
        return this.getMethodReturnTypeInternal();
    }

    public TypeVariable<Method>[] getTypeParameters() {
        return (TypeVariable[])this.getMethodOrConstructorGenericInfoInternal().formalTypeParameters.clone();
    }

    @Override
    boolean hasGenericInformation() {
        return super.hasGenericInformationInternal();
    }

    public int hashCode() {
        return this.getDeclaringClass().getName().hashCode() ^ this.getName().hashCode();
    }

    @CallerSensitive
    @FastNative
    public native Object invoke(Object var1, Object ... var2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    public boolean isBridge() {
        return super.isBridgeMethodInternal();
    }

    public boolean isDefault() {
        return super.isDefaultMethodInternal();
    }

    @Override
    public boolean isSynthetic() {
        return super.isSynthetic();
    }

    @Override
    public boolean isVarArgs() {
        return super.isVarArgs();
    }

    @Override
    void specificToGenericStringHeader(StringBuilder stringBuilder) {
        stringBuilder.append(this.getGenericReturnType().getTypeName());
        stringBuilder.append(' ');
        stringBuilder.append(this.getDeclaringClass().getTypeName());
        stringBuilder.append('.');
        stringBuilder.append(this.getName());
    }

    @Override
    void specificToStringHeader(StringBuilder stringBuilder) {
        stringBuilder.append(this.getReturnType().getTypeName());
        stringBuilder.append(' ');
        stringBuilder.append(this.getDeclaringClass().getTypeName());
        stringBuilder.append('.');
        stringBuilder.append(this.getName());
    }

    @Override
    public String toGenericString() {
        return this.sharedToGenericString(Modifier.methodModifiers(), this.isDefault());
    }

    public String toString() {
        return this.sharedToString(Modifier.methodModifiers(), this.isDefault(), this.getParameterTypes(), this.getExceptionTypes());
    }

}

