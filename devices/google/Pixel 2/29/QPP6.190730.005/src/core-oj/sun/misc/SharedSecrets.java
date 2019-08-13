/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import sun.misc.JavaIOFileDescriptorAccess;

public class SharedSecrets {
    private static JavaIOFileDescriptorAccess javaIOFileDescriptorAccess;

    public static JavaIOFileDescriptorAccess getJavaIOFileDescriptorAccess() {
        if (javaIOFileDescriptorAccess == null) {
            try {
                Class.forName("java.io.FileDescriptor");
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new RuntimeException(classNotFoundException);
            }
        }
        return javaIOFileDescriptorAccess;
    }

    public static void setJavaIOFileDescriptorAccess(JavaIOFileDescriptorAccess javaIOFileDescriptorAccess) {
        SharedSecrets.javaIOFileDescriptorAccess = javaIOFileDescriptorAccess;
    }
}

