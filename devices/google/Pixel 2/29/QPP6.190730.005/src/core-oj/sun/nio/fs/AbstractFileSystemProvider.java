/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.Map;
import sun.nio.fs.DynamicFileAttributeView;

abstract class AbstractFileSystemProvider
extends FileSystemProvider {
    protected AbstractFileSystemProvider() {
    }

    private static String[] split(String string) {
        String[] arrstring = new String[2];
        int n = string.indexOf(58);
        if (n == -1) {
            arrstring[0] = "basic";
            arrstring[1] = string;
        } else {
            int n2 = n + 1;
            arrstring[0] = string.substring(0, n);
            string = n2 == string.length() ? "" : string.substring(n2);
            arrstring[1] = string;
        }
        return arrstring;
    }

    @Override
    public final void delete(Path path) throws IOException {
        this.implDelete(path, true);
    }

    @Override
    public final boolean deleteIfExists(Path path) throws IOException {
        return this.implDelete(path, false);
    }

    abstract DynamicFileAttributeView getFileAttributeView(Path var1, String var2, LinkOption ... var3);

    abstract boolean implDelete(Path var1, boolean var2) throws IOException;

    @Override
    public final Map<String, Object> readAttributes(Path object, String string, LinkOption ... arrlinkOption) throws IOException {
        String[] arrstring = AbstractFileSystemProvider.split(string);
        if (arrstring[0].length() != 0) {
            if ((object = this.getFileAttributeView((Path)object, arrstring[0], arrlinkOption)) != null) {
                return object.readAttributes(arrstring[1].split(","));
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("View '");
            ((StringBuilder)object).append(arrstring[0]);
            ((StringBuilder)object).append("' not available");
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException(string);
    }

    @Override
    public final void setAttribute(Path object, String string, Object object2, LinkOption ... arrlinkOption) throws IOException {
        String[] arrstring = AbstractFileSystemProvider.split(string);
        if (arrstring[0].length() != 0) {
            if ((object = this.getFileAttributeView((Path)object, arrstring[0], arrlinkOption)) != null) {
                object.setAttribute(arrstring[1], object2);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("View '");
            ((StringBuilder)object).append(arrstring[0]);
            ((StringBuilder)object).append("' not available");
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException(string);
    }
}

