/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.nio.file.spi.FileSystemProvider;
import java.security.AccessController;
import sun.security.action.GetPropertyAction;

public class DefaultFileSystemProvider {
    private DefaultFileSystemProvider() {
    }

    public static FileSystemProvider create() {
        String string = AccessController.doPrivileged(new GetPropertyAction("os.name"));
        if (string.equals("SunOS")) {
            return DefaultFileSystemProvider.createProvider("sun.nio.fs.SolarisFileSystemProvider");
        }
        if (!string.equals("Linux") && !string.equals("Fuchsia")) {
            if (string.contains("OS X")) {
                return DefaultFileSystemProvider.createProvider("sun.nio.fs.MacOSXFileSystemProvider");
            }
            if (string.equals("AIX")) {
                return DefaultFileSystemProvider.createProvider("sun.nio.fs.AixFileSystemProvider");
            }
            throw new AssertionError((Object)"Platform not recognized");
        }
        return DefaultFileSystemProvider.createProvider("sun.nio.fs.LinuxFileSystemProvider");
    }

    private static FileSystemProvider createProvider(String object) {
        try {
            object = Class.forName((String)object);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new AssertionError(classNotFoundException);
        }
        try {
            object = (FileSystemProvider)((Class)object).newInstance();
            return object;
        }
        catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
            throw new AssertionError(reflectiveOperationException);
        }
    }
}

