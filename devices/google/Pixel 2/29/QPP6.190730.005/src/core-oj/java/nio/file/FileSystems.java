/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.spi.FileSystemProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import sun.nio.fs.DefaultFileSystemProvider;

public final class FileSystems {
    private FileSystems() {
    }

    public static FileSystem getDefault() {
        return DefaultFileSystemHolder.defaultFileSystem;
    }

    public static FileSystem getFileSystem(URI serializable) {
        String string = ((URI)serializable).getScheme();
        for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
            if (!string.equalsIgnoreCase(fileSystemProvider.getScheme())) continue;
            return fileSystemProvider.getFileSystem((URI)serializable);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Provider \"");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("\" not found");
        throw new ProviderNotFoundException(((StringBuilder)serializable).toString());
    }

    public static FileSystem newFileSystem(URI uRI, Map<String, ?> map) throws IOException {
        return FileSystems.newFileSystem(uRI, map, null);
    }

    public static FileSystem newFileSystem(URI serializable, Map<String, ?> map, ClassLoader object) throws IOException {
        String string = ((URI)serializable).getScheme();
        for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
            if (!string.equalsIgnoreCase(fileSystemProvider.getScheme())) continue;
            return fileSystemProvider.newFileSystem((URI)serializable, map);
        }
        if (object != null) {
            for (FileSystemProvider fileSystemProvider : ServiceLoader.load(FileSystemProvider.class, (ClassLoader)((Object)object))) {
                if (!string.equalsIgnoreCase(fileSystemProvider.getScheme())) continue;
                return fileSystemProvider.newFileSystem((URI)serializable, map);
            }
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Provider \"");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("\" not found");
        throw new ProviderNotFoundException(((StringBuilder)serializable).toString());
    }

    public static FileSystem newFileSystem(Path path, ClassLoader object) throws IOException {
        if (path != null) {
            Map map = Collections.emptyMap();
            for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
                try {
                    FileSystem fileSystem = fileSystemProvider.newFileSystem(path, map);
                    return fileSystem;
                }
                catch (UnsupportedOperationException unsupportedOperationException) {
                }
            }
            if (object != null) {
                for (FileSystemProvider fileSystemProvider : ServiceLoader.load(FileSystemProvider.class, (ClassLoader)((Object)object))) {
                    try {
                        FileSystem fileSystem = fileSystemProvider.newFileSystem(path, map);
                        return fileSystem;
                    }
                    catch (UnsupportedOperationException unsupportedOperationException) {
                    }
                }
            }
            throw new ProviderNotFoundException("Provider not found");
        }
        throw new NullPointerException();
    }

    private static class DefaultFileSystemHolder {
        static final FileSystem defaultFileSystem = DefaultFileSystemHolder.defaultFileSystem();

        private DefaultFileSystemHolder() {
        }

        private static FileSystem defaultFileSystem() {
            return AccessController.doPrivileged(new PrivilegedAction<FileSystemProvider>(){

                @Override
                public FileSystemProvider run() {
                    return DefaultFileSystemHolder.getDefaultProvider();
                }
            }).getFileSystem(URI.create("file:///"));
        }

        private static FileSystemProvider getDefaultProvider() {
            Object object = DefaultFileSystemProvider.create();
            String string2 = System.getProperty("java.nio.file.spi.DefaultFileSystemProvider");
            String[] arrstring = object;
            if (string2 != null) {
                for (String string2 : string2.split(",")) {
                    block5 : {
                        try {
                            object = (FileSystemProvider)Class.forName(string2, true, ClassLoader.getSystemClassLoader()).getDeclaredConstructor(FileSystemProvider.class).newInstance(object);
                            if (!((FileSystemProvider)object).getScheme().equals("file")) break block5;
                        }
                        catch (Exception exception) {
                            throw new Error(exception);
                        }
                        continue;
                    }
                    object = new Error("Default provider must use scheme 'file'");
                    throw object;
                }
                arrstring = object;
            }
            return arrstring;
        }

    }

}

