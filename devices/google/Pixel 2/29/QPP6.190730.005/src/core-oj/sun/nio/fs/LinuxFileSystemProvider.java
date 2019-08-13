/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileTypeDetector;
import sun.nio.fs.DynamicFileAttributeView;
import sun.nio.fs.LinuxFileStore;
import sun.nio.fs.LinuxFileSystem;
import sun.nio.fs.MimeTypesFileTypeDetector;
import sun.nio.fs.UnixFileSystem;
import sun.nio.fs.UnixFileSystemProvider;
import sun.nio.fs.UnixPath;

public class LinuxFileSystemProvider
extends UnixFileSystemProvider {
    @Override
    public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> class_, LinkOption ... arrlinkOption) {
        return super.getFileAttributeView(path, class_, arrlinkOption);
    }

    @Override
    public DynamicFileAttributeView getFileAttributeView(Path path, String string, LinkOption ... arrlinkOption) {
        return super.getFileAttributeView(path, string, arrlinkOption);
    }

    @Override
    LinuxFileStore getFileStore(UnixPath unixPath) throws IOException {
        throw new SecurityException("getFileStore");
    }

    @Override
    FileTypeDetector getFileTypeDetector() {
        return new MimeTypesFileTypeDetector();
    }

    @Override
    LinuxFileSystem newFileSystem(String string) {
        return new LinuxFileSystem(this, string);
    }

    @Override
    public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> class_, LinkOption ... arrlinkOption) throws IOException {
        return super.readAttributes(path, class_, arrlinkOption);
    }
}

