/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.apache.xml.serializer.SecuritySupport;

class SecuritySupport12
extends SecuritySupport {
    SecuritySupport12() {
    }

    @Override
    ClassLoader getContextClassLoader() {
        return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                ClassLoader classLoader = null;
                try {
                    ClassLoader classLoader2;
                    classLoader = classLoader2 = Thread.currentThread().getContextClassLoader();
                }
                catch (SecurityException securityException) {
                    // empty catch block
                }
                return classLoader;
            }
        });
    }

    @Override
    boolean getFileExists(final File file) {
        return (Boolean)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                return new Boolean(file.exists());
            }
        });
    }

    @Override
    FileInputStream getFileInputStream(File object) throws FileNotFoundException {
        try {
            PrivilegedExceptionAction privilegedExceptionAction = new PrivilegedExceptionAction((File)object){
                final /* synthetic */ File val$file;
                {
                    this.val$file = file;
                }

                public Object run() throws FileNotFoundException {
                    return new FileInputStream(this.val$file);
                }
            };
            object = (FileInputStream)AccessController.doPrivileged(privilegedExceptionAction);
            return object;
        }
        catch (PrivilegedActionException privilegedActionException) {
            throw (FileNotFoundException)privilegedActionException.getException();
        }
    }

    @Override
    long getLastModified(final File file) {
        return (Long)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                return new Long(file.lastModified());
            }
        });
    }

    @Override
    ClassLoader getParentClassLoader(final ClassLoader classLoader) {
        return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                ClassLoader classLoader3;
                block2 : {
                    classLoader3 = null;
                    try {
                        ClassLoader classLoader2;
                        classLoader3 = classLoader2 = classLoader.getParent();
                    }
                    catch (SecurityException securityException) {
                        // empty catch block
                    }
                    if (classLoader3 != classLoader) break block2;
                    classLoader3 = null;
                }
                return classLoader3;
            }
        });
    }

    @Override
    InputStream getResourceAsStream(final ClassLoader classLoader, final String string) {
        return (InputStream)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                Object object = classLoader;
                object = object == null ? ClassLoader.getSystemResourceAsStream(string) : ((ClassLoader)object).getResourceAsStream(string);
                return object;
            }
        });
    }

    @Override
    ClassLoader getSystemClassLoader() {
        return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                ClassLoader classLoader = null;
                try {
                    ClassLoader classLoader2;
                    classLoader = classLoader2 = ClassLoader.getSystemClassLoader();
                }
                catch (SecurityException securityException) {}
                return classLoader;
            }
        });
    }

    @Override
    String getSystemProperty(final String string) {
        return (String)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                return System.getProperty(string);
            }
        });
    }

}

