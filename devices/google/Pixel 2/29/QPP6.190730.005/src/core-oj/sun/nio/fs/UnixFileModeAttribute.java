/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Iterator;
import java.util.Set;
import sun.nio.fs.UnixConstants;

class UnixFileModeAttribute {
    static final int ALL_PERMISSIONS = UnixConstants.S_IRUSR | UnixConstants.S_IWUSR | UnixConstants.S_IXUSR | UnixConstants.S_IRGRP | UnixConstants.S_IWGRP | UnixConstants.S_IXGRP | UnixConstants.S_IROTH | UnixConstants.S_IWOTH | UnixConstants.S_IXOTH;
    static final int ALL_READWRITE = UnixConstants.S_IRUSR | UnixConstants.S_IWUSR | UnixConstants.S_IRGRP | UnixConstants.S_IWGRP | UnixConstants.S_IROTH | UnixConstants.S_IWOTH;
    static final int TEMPFILE_PERMISSIONS = UnixConstants.S_IRUSR | UnixConstants.S_IWUSR | UnixConstants.S_IXUSR;

    private UnixFileModeAttribute() {
    }

    static int toUnixMode(int n, FileAttribute<?> ... object) {
        int n2 = n;
        int n3 = ((FileAttribute<?>[])object).length;
        for (n = 0; n < n3; ++n) {
            FileAttribute<?> fileAttribute = object[n];
            String string = fileAttribute.name();
            if (!string.equals("posix:permissions") && !string.equals("unix:permissions")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("'");
                ((StringBuilder)object).append(fileAttribute.name());
                ((StringBuilder)object).append("' not supported as initial attribute");
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
            n2 = UnixFileModeAttribute.toUnixMode((Set)fileAttribute.value());
        }
        return n2;
    }

    static int toUnixMode(Set<PosixFilePermission> object2) {
        int n = 0;
        Iterator iterator = object2.iterator();
        block11 : while (iterator.hasNext()) {
            PosixFilePermission posixFilePermission = (PosixFilePermission)((Object)iterator.next());
            if (posixFilePermission != null) {
                switch (posixFilePermission) {
                    default: {
                        continue block11;
                    }
                    case OTHERS_EXECUTE: {
                        n |= UnixConstants.S_IXOTH;
                        continue block11;
                    }
                    case OTHERS_WRITE: {
                        n |= UnixConstants.S_IWOTH;
                        continue block11;
                    }
                    case OTHERS_READ: {
                        n |= UnixConstants.S_IROTH;
                        continue block11;
                    }
                    case GROUP_EXECUTE: {
                        n |= UnixConstants.S_IXGRP;
                        continue block11;
                    }
                    case GROUP_WRITE: {
                        n |= UnixConstants.S_IWGRP;
                        continue block11;
                    }
                    case GROUP_READ: {
                        n |= UnixConstants.S_IRGRP;
                        continue block11;
                    }
                    case OWNER_EXECUTE: {
                        n |= UnixConstants.S_IXUSR;
                        continue block11;
                    }
                    case OWNER_WRITE: {
                        n |= UnixConstants.S_IWUSR;
                        continue block11;
                    }
                    case OWNER_READ: 
                }
                n |= UnixConstants.S_IRUSR;
                continue;
            }
            throw new NullPointerException();
        }
        return n;
    }

}

