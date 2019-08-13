/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.xml.dtm.ref.SecuritySupport12;

class SecuritySupport {
    private static final Object securitySupport;

    static {
        SecuritySupport securitySupport;
        block4 : {
            SecuritySupport securitySupport2 = null;
            securitySupport = null;
            try {
                Class.forName("java.security.AccessController");
                SecuritySupport12 securitySupport12 = new SecuritySupport12();
                securitySupport = securitySupport12;
            }
            catch (Throwable throwable) {
                if (!false) {
                    securitySupport = new SecuritySupport();
                }
                SecuritySupport.securitySupport = securitySupport;
                throw throwable;
            }
            catch (Exception exception) {
                securitySupport = securitySupport2;
                if (false) break block4;
                securitySupport = new SecuritySupport();
            }
        }
        SecuritySupport.securitySupport = securitySupport;
    }

    SecuritySupport() {
    }

    static SecuritySupport getInstance() {
        return (SecuritySupport)securitySupport;
    }

    ClassLoader getContextClassLoader() {
        return null;
    }

    boolean getFileExists(File file) {
        return file.exists();
    }

    FileInputStream getFileInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    long getLastModified(File file) {
        return file.lastModified();
    }

    ClassLoader getParentClassLoader(ClassLoader classLoader) {
        return null;
    }

    InputStream getResourceAsStream(ClassLoader object, String string) {
        object = object == null ? ClassLoader.getSystemResourceAsStream(string) : ((ClassLoader)object).getResourceAsStream(string);
        return object;
    }

    ClassLoader getSystemClassLoader() {
        return null;
    }

    String getSystemProperty(String string) {
        return System.getProperty(string);
    }
}

