/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.File;
import java.io.IOException;

abstract class FileSystem {
    public static final int ACCESS_EXECUTE = 1;
    public static final int ACCESS_OK = 8;
    public static final int ACCESS_READ = 4;
    public static final int ACCESS_WRITE = 2;
    public static final int BA_DIRECTORY = 4;
    public static final int BA_EXISTS = 1;
    public static final int BA_HIDDEN = 8;
    public static final int BA_REGULAR = 2;
    public static final int SPACE_FREE = 1;
    public static final int SPACE_TOTAL = 0;
    public static final int SPACE_USABLE = 2;
    static boolean useCanonCaches = false;
    static boolean useCanonPrefixCache = false;

    static {
        useCanonCaches = FileSystem.getBooleanProperty("sun.io.useCanonCaches", useCanonCaches);
        useCanonPrefixCache = FileSystem.getBooleanProperty("sun.io.useCanonPrefixCache", useCanonPrefixCache);
    }

    FileSystem() {
    }

    private static boolean getBooleanProperty(String string, boolean bl) {
        if ((string = System.getProperty(string)) == null) {
            return bl;
        }
        return string.equalsIgnoreCase("true");
    }

    public abstract String canonicalize(String var1) throws IOException;

    public abstract boolean checkAccess(File var1, int var2);

    public abstract int compare(File var1, File var2);

    public abstract boolean createDirectory(File var1);

    public abstract boolean createFileExclusively(String var1) throws IOException;

    public abstract boolean delete(File var1);

    public abstract String fromURIPath(String var1);

    public abstract int getBooleanAttributes(File var1);

    public abstract String getDefaultParent();

    public abstract long getLastModifiedTime(File var1);

    public abstract long getLength(File var1);

    public abstract char getPathSeparator();

    public abstract char getSeparator();

    public abstract long getSpace(File var1, int var2);

    public abstract int hashCode(File var1);

    public abstract boolean isAbsolute(File var1);

    public abstract String[] list(File var1);

    public abstract File[] listRoots();

    public abstract String normalize(String var1);

    public abstract int prefixLength(String var1);

    public abstract boolean rename(File var1, File var2);

    public abstract String resolve(File var1);

    public abstract String resolve(String var1, String var2);

    public abstract boolean setLastModifiedTime(File var1, long var2);

    public abstract boolean setPermission(File var1, int var2, boolean var3, boolean var4);

    public abstract boolean setReadOnly(File var1);
}

