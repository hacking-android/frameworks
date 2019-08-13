/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class ClassUtil {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Class loadClass(Class privilegedAction, final String string) {
        try {
            privilegedAction = ((Class)((Object)privilegedAction)).getClassLoader();
            if (privilegedAction != null) {
                return ((ClassLoader)((Object)privilegedAction)).loadClass(string);
            }
            privilegedAction = new PrivilegedAction(){

                public Object run() {
                    try {
                        Class<?> class_ = Class.forName(string);
                        return class_;
                    }
                    catch (Exception exception) {
                        return null;
                    }
                }
            };
            return (Class)AccessController.doPrivileged(privilegedAction);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

}

