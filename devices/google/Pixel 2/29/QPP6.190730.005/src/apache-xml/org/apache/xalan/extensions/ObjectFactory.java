/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.extensions;

class ObjectFactory {
    ObjectFactory() {
    }

    static ClassLoader findClassLoader() throws ConfigurationError {
        return Thread.currentThread().getContextClassLoader();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Class findProviderClass(String class_, ClassLoader classLoader, boolean bl) throws ClassNotFoundException, ConfigurationError {
        Object object;
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            int n = ((String)((Object)class_)).lastIndexOf(".");
            object = class_;
            if (n != -1) {
                object = ((String)((Object)class_)).substring(0, n);
            }
            securityManager.checkPackageAccess((String)object);
        }
        if (classLoader == null) {
            return Class.forName(class_);
        }
        try {
            object = classLoader.loadClass((String)((Object)class_));
            return object;
        }
        catch (ClassNotFoundException classNotFoundException) {
            if (!bl) throw classNotFoundException;
            object = ObjectFactory.class.getClassLoader();
            if (object == null) {
                return Class.forName(class_);
            }
            if (classLoader == object) throw classNotFoundException;
            return ((ClassLoader)object).loadClass((String)((Object)class_));
        }
    }

    static class ConfigurationError
    extends Error {
        static final long serialVersionUID = 8564305128443551853L;
        private Exception exception;

        ConfigurationError(String string, Exception exception) {
            super(string);
            this.exception = exception;
        }

        Exception getException() {
            return this.exception;
        }
    }

}

