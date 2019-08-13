/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class OptionalMethod<T> {
    private final String methodName;
    private final Class[] methodParams;
    private final Class<?> returnType;

    public OptionalMethod(Class<?> class_, String string, Class ... arrclass) {
        this.returnType = class_;
        this.methodName = string;
        this.methodParams = arrclass;
    }

    private Method getMethod(Class<?> genericDeclaration) {
        GenericDeclaration genericDeclaration2 = null;
        Object object = this.methodName;
        if (object != null) {
            genericDeclaration2 = genericDeclaration = OptionalMethod.getPublicMethod(genericDeclaration, (String)object, this.methodParams);
            if (genericDeclaration != null) {
                object = this.returnType;
                genericDeclaration2 = genericDeclaration;
                if (object != null) {
                    genericDeclaration2 = genericDeclaration;
                    if (!((Class)object).isAssignableFrom(((Method)genericDeclaration).getReturnType())) {
                        genericDeclaration2 = null;
                    }
                }
            }
        }
        return genericDeclaration2;
    }

    private static Method getPublicMethod(Class<?> genericDeclaration, String string, Class[] arrclass) {
        GenericDeclaration genericDeclaration2 = null;
        genericDeclaration2 = genericDeclaration = ((Class)genericDeclaration).getMethod(string, arrclass);
        try {
            int n = ((Method)genericDeclaration).getModifiers();
            if ((n & 1) == 0) {
                genericDeclaration = null;
            }
        }
        catch (NoSuchMethodException noSuchMethodException) {
            genericDeclaration = genericDeclaration2;
        }
        return genericDeclaration;
    }

    public Object invoke(T object, Object ... object2) throws InvocationTargetException {
        Method method = this.getMethod(object.getClass());
        if (method != null) {
            try {
                object = method.invoke(object, (Object[])object2);
                return object;
            }
            catch (IllegalAccessException illegalAccessException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unexpectedly could not call: ");
                ((StringBuilder)object2).append(method);
                object2 = new AssertionError((Object)((StringBuilder)object2).toString());
                ((Throwable)object2).initCause(illegalAccessException);
                throw object2;
            }
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Method ");
        ((StringBuilder)object2).append(this.methodName);
        ((StringBuilder)object2).append(" not supported for object ");
        ((StringBuilder)object2).append(object);
        throw new AssertionError((Object)((StringBuilder)object2).toString());
    }

    public Object invokeOptional(T object, Object ... arrobject) throws InvocationTargetException {
        Method method = this.getMethod(object.getClass());
        if (method == null) {
            return null;
        }
        try {
            object = method.invoke(object, arrobject);
            return object;
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
    }

    public Object invokeOptionalWithoutCheckedException(T object, Object ... object2) {
        try {
            object = this.invokeOptional(object, object2);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            object2 = invocationTargetException.getTargetException();
            if (object2 instanceof RuntimeException) {
                throw (RuntimeException)object2;
            }
            AssertionError assertionError = new AssertionError((Object)"Unexpected exception");
            ((Throwable)((Object)assertionError)).initCause((Throwable)object2);
            throw assertionError;
        }
    }

    public Object invokeWithoutCheckedException(T object, Object ... object2) {
        try {
            object = this.invoke(object, (Object[])object2);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getTargetException();
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException)throwable;
            }
            object2 = new AssertionError((Object)"Unexpected exception");
            ((Throwable)object2).initCause(throwable);
            throw object2;
        }
    }

    public boolean isSupported(T t) {
        boolean bl = this.getMethod(t.getClass()) != null;
        return bl;
    }
}

