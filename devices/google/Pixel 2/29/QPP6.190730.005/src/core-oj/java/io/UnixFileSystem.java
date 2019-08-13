/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.OsConstants
 *  android.system.StructStat
 *  dalvik.system.BlockGuard
 *  libcore.io.Libcore
 *  libcore.io.Os
 */
package java.io;

import android.system.ErrnoException;
import android.system.OsConstants;
import android.system.StructStat;
import dalvik.system.BlockGuard;
import java.io.ExpiringCache;
import java.io.File;
import java.io.FileSystem;
import java.io.IOException;
import java.io.Serializable;
import java.security.AccessController;
import libcore.io.Libcore;
import libcore.io.Os;
import sun.security.action.GetPropertyAction;

class UnixFileSystem
extends FileSystem {
    private ExpiringCache cache = new ExpiringCache();
    private final char colon = AccessController.doPrivileged(new GetPropertyAction("path.separator")).charAt(0);
    private final String javaHome = AccessController.doPrivileged(new GetPropertyAction("java.home"));
    private ExpiringCache javaHomePrefixCache = new ExpiringCache();
    private final char slash = AccessController.doPrivileged(new GetPropertyAction("file.separator")).charAt(0);

    static {
        UnixFileSystem.initIDs();
    }

    private native String canonicalize0(String var1) throws IOException;

    private native boolean createDirectory0(File var1);

    private native boolean createFileExclusively0(String var1) throws IOException;

    private native int getBooleanAttributes0(String var1);

    private native long getLastModifiedTime0(File var1);

    private native long getSpace0(File var1, int var2);

    private static native void initIDs();

    private native String[] list0(File var1);

    static String parentOrNull(String string) {
        int n;
        if (string == null) {
            return null;
        }
        char c = File.separatorChar;
        char c2 = '\u0000';
        int n2 = 0;
        for (int i = n = string.length() - 1; i > 0; --i) {
            char c3 = string.charAt(i);
            if (c3 == '.') {
                c2 = c3 = c2 + 1;
                if (c3 < 2) continue;
                return null;
            }
            if (c3 == c) {
                if (c2 == '\u0001' && n2 == 0) {
                    return null;
                }
                if (i != 0 && i < n - 1 && string.charAt(i - 1) != c) {
                    return string.substring(0, i);
                }
                return null;
            }
            ++n2;
            c2 = '\u0000';
        }
        return null;
    }

    private native boolean setLastModifiedTime0(File var1, long var2);

    private native boolean setPermission0(File var1, int var2, boolean var3, boolean var4);

    private native boolean setReadOnly0(File var1);

    @Override
    public String canonicalize(String object) throws IOException {
        if (!useCanonCaches) {
            return this.canonicalize0((String)object);
        }
        Object object2 = this.cache.get((String)object);
        CharSequence charSequence = object2;
        if (object2 == null) {
            String string = null;
            CharSequence charSequence2 = object2;
            if (useCanonPrefixCache) {
                charSequence = UnixFileSystem.parentOrNull((String)object);
                charSequence2 = object2;
                string = charSequence;
                if (charSequence != null) {
                    CharSequence charSequence3 = this.javaHomePrefixCache.get((String)charSequence);
                    charSequence2 = object2;
                    string = charSequence;
                    if (charSequence3 != null) {
                        string = ((String)object).substring(charSequence.length() + 1);
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append((String)charSequence3);
                        ((StringBuilder)charSequence2).append(this.slash);
                        ((StringBuilder)charSequence2).append(string);
                        charSequence2 = ((StringBuilder)charSequence2).toString();
                        object2 = this.cache;
                        charSequence3 = new StringBuilder();
                        ((StringBuilder)charSequence3).append((String)charSequence);
                        ((StringBuilder)charSequence3).append(this.slash);
                        ((StringBuilder)charSequence3).append(string);
                        ((ExpiringCache)object2).put(((StringBuilder)charSequence3).toString(), (String)charSequence2);
                        string = charSequence;
                    }
                }
            }
            charSequence = charSequence2;
            if (charSequence2 == null) {
                BlockGuard.getThreadPolicy().onReadFromDisk();
                BlockGuard.getVmPolicy().onPathAccess((String)object);
                charSequence2 = this.canonicalize0((String)object);
                this.cache.put((String)object, (String)charSequence2);
                charSequence = charSequence2;
                if (useCanonPrefixCache) {
                    charSequence = charSequence2;
                    if (string != null) {
                        charSequence = charSequence2;
                        if (string.startsWith(this.javaHome)) {
                            object2 = UnixFileSystem.parentOrNull((String)charSequence2);
                            charSequence = charSequence2;
                            if (object2 != null) {
                                charSequence = charSequence2;
                                if (((String)object2).equals(string)) {
                                    object = new File((String)charSequence2);
                                    charSequence = charSequence2;
                                    if (((File)object).exists()) {
                                        charSequence = charSequence2;
                                        if (!((File)object).isDirectory()) {
                                            this.javaHomePrefixCache.put(string, (String)object2);
                                            charSequence = charSequence2;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return charSequence;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean checkAccess(File serializable, int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Bad access mode: ");
                        ((StringBuilder)serializable).append(n);
                        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
                    }
                    n = OsConstants.F_OK;
                } else {
                    n = OsConstants.R_OK;
                }
            } else {
                n = OsConstants.W_OK;
            }
        } else {
            n = OsConstants.X_OK;
        }
        try {
            return Libcore.os.access(((File)serializable).getPath(), n);
        }
        catch (ErrnoException errnoException) {
            return false;
        }
    }

    @Override
    public int compare(File file, File file2) {
        return file.getPath().compareTo(file2.getPath());
    }

    @Override
    public boolean createDirectory(File file) {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(file.getPath());
        return this.createDirectory0(file);
    }

    @Override
    public boolean createFileExclusively(String string) throws IOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        return this.createFileExclusively0(string);
    }

    @Override
    public boolean delete(File file) {
        this.cache.clear();
        this.javaHomePrefixCache.clear();
        try {
            Libcore.os.remove(file.getPath());
            return true;
        }
        catch (ErrnoException errnoException) {
            return false;
        }
    }

    @Override
    public String fromURIPath(String string) {
        String string2;
        string = string2 = string;
        if (string2.endsWith("/")) {
            string = string2;
            if (string2.length() > 1) {
                string = string2.substring(0, string2.length() - 1);
            }
        }
        return string;
    }

    @Override
    public int getBooleanAttributes(File object) {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(((File)object).getPath());
        int n = this.getBooleanAttributes0(((File)object).getPath());
        object = ((File)object).getName();
        int n2 = ((String)object).length();
        int n3 = 0;
        n2 = n2 > 0 && ((String)object).charAt(0) == '.' ? 1 : 0;
        if (n2 != 0) {
            n3 = 8;
        }
        return n3 | n;
    }

    @Override
    public String getDefaultParent() {
        return "/";
    }

    @Override
    public long getLastModifiedTime(File file) {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(file.getPath());
        return this.getLastModifiedTime0(file);
    }

    @Override
    public long getLength(File file) {
        try {
            long l = Libcore.os.stat((String)file.getPath()).st_size;
            return l;
        }
        catch (ErrnoException errnoException) {
            return 0L;
        }
    }

    @Override
    public char getPathSeparator() {
        return this.colon;
    }

    @Override
    public char getSeparator() {
        return this.slash;
    }

    @Override
    public long getSpace(File file, int n) {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(file.getPath());
        return this.getSpace0(file, n);
    }

    @Override
    public int hashCode(File file) {
        return file.getPath().hashCode() ^ 1234321;
    }

    @Override
    public boolean isAbsolute(File file) {
        boolean bl = file.getPrefixLength() != 0;
        return bl;
    }

    @Override
    public String[] list(File file) {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(file.getPath());
        return this.list0(file);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File[] listRoots() {
        Object object;
        try {
            object = System.getSecurityManager();
            if (object != null) {
                ((SecurityManager)object).checkRead("/");
            }
            object = new File("/");
        }
        catch (SecurityException securityException) {
            return new File[0];
        }
        return new File[]{object};
    }

    @Override
    public String normalize(String string) {
        block8 : {
            int n;
            int n2 = string.length();
            char[] arrc = string.toCharArray();
            int n3 = 0;
            int n4 = 0;
            for (n = 0; n < n2; ++n) {
                int n5;
                int n6;
                block7 : {
                    block6 : {
                        n6 = arrc[n];
                        if (n6 != 47) break block6;
                        n5 = n3;
                        if (n4 == 47) break block7;
                    }
                    arrc[n3] = (char)n6;
                    n5 = n3 + 1;
                }
                n4 = n6;
                n3 = n5;
            }
            n = n3;
            if (n4 == 47) {
                n = n3;
                if (n2 > 1) {
                    n = n3 - 1;
                }
            }
            if (n == n2) break block8;
            string = new String(arrc, 0, n);
        }
        return string;
    }

    @Override
    public int prefixLength(String string) {
        int n = string.length();
        int n2 = 0;
        if (n == 0) {
            return 0;
        }
        if (string.charAt(0) == '/') {
            n2 = 1;
        }
        return n2;
    }

    @Override
    public boolean rename(File file, File file2) {
        this.cache.clear();
        this.javaHomePrefixCache.clear();
        try {
            Libcore.os.rename(file.getPath(), file2.getPath());
            return true;
        }
        catch (ErrnoException errnoException) {
            return false;
        }
    }

    @Override
    public String resolve(File file) {
        if (this.isAbsolute(file)) {
            return file.getPath();
        }
        return this.resolve(System.getProperty("user.dir"), file.getPath());
    }

    @Override
    public String resolve(String string, String string2) {
        if (!string2.isEmpty() && !string2.equals("/")) {
            if (string2.charAt(0) == '/') {
                if (string.equals("/")) {
                    return string2;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(string2);
                return stringBuilder.toString();
            }
            if (string.equals("/")) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(string2);
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append('/');
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }
        return string;
    }

    @Override
    public boolean setLastModifiedTime(File file, long l) {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(file.getPath());
        return this.setLastModifiedTime0(file, l);
    }

    @Override
    public boolean setPermission(File file, int n, boolean bl, boolean bl2) {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(file.getPath());
        return this.setPermission0(file, n, bl, bl2);
    }

    @Override
    public boolean setReadOnly(File file) {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(file.getPath());
        return this.setReadOnly0(file);
    }
}

