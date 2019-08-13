/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class NewInstance {
    NewInstance() {
    }

    static ClassLoader getClassLoader() {
        Object object;
        try {
            object = Thread.class.getMethod("getContextClassLoader", new Class[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return NewInstance.class.getClassLoader();
        }
        try {
            object = (ClassLoader)((Method)object).invoke(Thread.currentThread(), new Object[0]);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new UnknownError(invocationTargetException.getMessage());
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new UnknownError(illegalAccessException.getMessage());
        }
    }

    static Object newInstance(ClassLoader class_, String string) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        class_ = class_ == null ? Class.forName(string) : ((ClassLoader)((Object)class_)).loadClass(string);
        return class_.newInstance();
    }
}

