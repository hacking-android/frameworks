/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;

class CopyMoveHelper {
    private CopyMoveHelper() {
    }

    private static CopyOption[] convertMoveToCopyOptions(CopyOption ... arrcopyOption) throws AtomicMoveNotSupportedException {
        int n = arrcopyOption.length;
        CopyOption[] arrcopyOption2 = new CopyOption[n + 2];
        for (int i = 0; i < n; ++i) {
            CopyOption copyOption = arrcopyOption[i];
            if (copyOption != StandardCopyOption.ATOMIC_MOVE) {
                arrcopyOption2[i] = copyOption;
                continue;
            }
            throw new AtomicMoveNotSupportedException(null, null, "Atomic move between providers is not supported");
        }
        arrcopyOption2[n] = LinkOption.NOFOLLOW_LINKS;
        arrcopyOption2[n + 1] = StandardCopyOption.COPY_ATTRIBUTES;
        return arrcopyOption2;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static void copyToForeignTarget(Path object, Path path, CopyOption ... object2) throws IOException {
        CopyOptions copyOptions;
        block14 : {
            copyOptions = CopyOptions.parse((CopyOption[])object2);
            object2 = copyOptions.followLinks ? new LinkOption[]{} : new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
            object2 = Files.readAttributes((Path)object, BasicFileAttributes.class, (LinkOption[])object2);
            if (object2.isSymbolicLink()) throw new IOException("Copying of symbolic links not supported");
            if (copyOptions.replaceExisting) {
                Files.deleteIfExists(path);
            } else if (Files.exists(path, new LinkOption[0])) throw new FileAlreadyExistsException(path.toString());
            if (object2.isDirectory()) {
                Files.createDirectory(path, new FileAttribute[0]);
            } else {
                object = Files.newInputStream((Path)object, new OpenOption[0]);
                Files.copy((InputStream)object, path, new CopyOption[0]);
                if (object == null) break block14;
                ((InputStream)object).close();
            }
        }
        if (!copyOptions.copyAttributes) return;
        object = Files.getFileAttributeView(path, BasicFileAttributeView.class, new LinkOption[0]);
        try {
            object.setTimes(object2.lastModifiedTime(), object2.lastAccessTime(), object2.creationTime());
            return;
        }
        catch (Throwable throwable) {
            try {
                Files.delete(path);
                throw throwable;
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable3) {
                if (object == null) throw throwable3;
                try {
                    ((InputStream)object).close();
                    throw throwable3;
                }
                catch (Throwable throwable4) {
                    throwable.addSuppressed(throwable4);
                }
                throw throwable3;
            }
        }
    }

    static void moveToForeignTarget(Path path, Path path2, CopyOption ... arrcopyOption) throws IOException {
        CopyMoveHelper.copyToForeignTarget(path, path2, CopyMoveHelper.convertMoveToCopyOptions(arrcopyOption));
        Files.delete(path);
    }

    private static class CopyOptions {
        boolean copyAttributes = false;
        boolean followLinks = true;
        boolean replaceExisting = false;

        private CopyOptions() {
        }

        static CopyOptions parse(CopyOption ... object) {
            CopyOptions copyOptions = new CopyOptions();
            int n = ((CopyOption[])object).length;
            for (int i = 0; i < n; ++i) {
                CopyOption copyOption = object[i];
                if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
                    copyOptions.replaceExisting = true;
                    continue;
                }
                if (copyOption == LinkOption.NOFOLLOW_LINKS) {
                    copyOptions.followLinks = false;
                    continue;
                }
                if (copyOption == StandardCopyOption.COPY_ATTRIBUTES) {
                    copyOptions.copyAttributes = true;
                    continue;
                }
                if (copyOption == null) {
                    throw new NullPointerException();
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("'");
                ((StringBuilder)object).append(copyOption);
                ((StringBuilder)object).append("' is not a recognized copy option");
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
            return copyOptions;
        }
    }

}

