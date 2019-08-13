/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.AccessController;
import java.security.SecureRandom;
import java.util.EnumSet;
import java.util.Set;
import sun.security.action.GetPropertyAction;

class TempFileHelper {
    private static final boolean isPosix;
    private static final SecureRandom random;
    private static final Path tmpdir;

    static {
        tmpdir = Paths.get(AccessController.doPrivileged(new GetPropertyAction("java.io.tmpdir")), new String[0]);
        isPosix = FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
        random = new SecureRandom();
    }

    private TempFileHelper() {
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Path create(Path object, String string, String object2, boolean bl, FileAttribute<?>[] object3) throws IOException {
        void var4_15;
        String string2;
        void var3_14;
        Object object4;
        String string3 = string2;
        if (string2 == null) {
            string3 = "";
        }
        string2 = object4;
        if (object4 == null) {
            string2 = var3_14 != false ? "" : ".tmp";
        }
        object4 = object;
        if (object == null) {
            object4 = tmpdir;
        }
        void var0_1 = var4_15;
        if (isPosix) {
            void var0_2 = var4_15;
            if (object4.getFileSystem() == FileSystems.getDefault()) {
                if (((void)var4_15).length == 0) {
                    void var0_5;
                    FileAttribute[] arrfileAttribute = new FileAttribute[1];
                    if (var3_14 != false) {
                        FileAttribute<Set<PosixFilePermission>> fileAttribute = PosixPermissions.dirPermissions;
                    } else {
                        FileAttribute<Set<PosixFilePermission>> fileAttribute = PosixPermissions.filePermissions;
                    }
                    arrfileAttribute[0] = var0_5;
                    FileAttribute[] arrfileAttribute2 = arrfileAttribute;
                } else {
                    int n;
                    int n2 = 0;
                    int n3 = 0;
                    do {
                        n = n2;
                        if (n3 >= ((void)var4_15).length) break;
                        if (var4_15[n3].name().equals("posix:permissions")) {
                            n = 1;
                            break;
                        }
                        ++n3;
                    } while (true);
                    void var0_7 = var4_15;
                    if (n == 0) {
                        void var4_19;
                        FileAttribute[] arrfileAttribute = new FileAttribute[((void)var4_15).length + 1];
                        System.arraycopy(var4_15, 0, arrfileAttribute, 0, ((void)var4_15).length);
                        n = arrfileAttribute.length;
                        if (var3_14 != false) {
                            FileAttribute<Set<PosixFilePermission>> fileAttribute = PosixPermissions.dirPermissions;
                        } else {
                            FileAttribute<Set<PosixFilePermission>> fileAttribute = PosixPermissions.filePermissions;
                        }
                        arrfileAttribute[n - 1] = var4_19;
                    }
                }
            }
        }
        SecurityManager securityManager = System.getSecurityManager();
        do {
            void var0_9;
            Path path = TempFileHelper.generatePath(string3, string2, (Path)object4);
            if (var3_14 == false) return Files.createFile(path, var0_9);
            try {
                return Files.createDirectory(path, var0_9);
            }
            catch (FileAlreadyExistsException fileAlreadyExistsException) {
                continue;
            }
            break;
        } while (true);
        catch (SecurityException securityException) {
            if (object4 != tmpdir) throw securityException;
            if (securityManager == null) throw securityException;
            throw new SecurityException("Unable to create temporary file or directory");
        }
        catch (InvalidPathException invalidPathException) {
            if (securityManager == null) throw invalidPathException;
            throw new IllegalArgumentException("Invalid prefix or suffix");
        }
    }

    static Path createTempDirectory(Path path, String string, FileAttribute<?>[] arrfileAttribute) throws IOException {
        return TempFileHelper.create(path, string, null, true, arrfileAttribute);
    }

    static Path createTempFile(Path path, String string, String string2, FileAttribute<?>[] arrfileAttribute) throws IOException {
        return TempFileHelper.create(path, string, string2, false, arrfileAttribute);
    }

    private static Path generatePath(String object, String string, Path path) {
        long l = random.nextLong();
        l = l == Long.MIN_VALUE ? 0L : Math.abs(l);
        FileSystem fileSystem = path.getFileSystem();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append(Long.toString(l));
        stringBuilder.append(string);
        object = fileSystem.getPath(stringBuilder.toString(), new String[0]);
        if (object.getParent() == null) {
            return path.resolve((Path)object);
        }
        throw new IllegalArgumentException("Invalid prefix or suffix");
    }

    private static class PosixPermissions {
        static final FileAttribute<Set<PosixFilePermission>> dirPermissions;
        static final FileAttribute<Set<PosixFilePermission>> filePermissions;

        static {
            filePermissions = PosixFilePermissions.asFileAttribute(EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE));
            dirPermissions = PosixFilePermissions.asFileAttribute(EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE));
        }

        private PosixPermissions() {
        }
    }

}

