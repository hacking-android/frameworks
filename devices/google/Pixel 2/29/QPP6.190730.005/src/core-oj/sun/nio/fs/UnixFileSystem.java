/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.security.AccessController;
import java.security.Permission;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.nio.fs.Globs;
import sun.nio.fs.UnixFileSystemProvider;
import sun.nio.fs.UnixMountEntry;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;
import sun.nio.fs.UnixUserPrincipals;
import sun.nio.fs.Util;
import sun.security.action.GetPropertyAction;

abstract class UnixFileSystem
extends FileSystem {
    private static final String GLOB_SYNTAX = "glob";
    private static final String REGEX_SYNTAX = "regex";
    private final byte[] defaultDirectory;
    private final boolean needToResolveAgainstDefaultDirectory;
    private final UnixFileSystemProvider provider;
    private final UnixPath rootDirectory;

    UnixFileSystem(UnixFileSystemProvider object, String string) {
        this.provider = object;
        this.defaultDirectory = Util.toBytes(UnixPath.normalizeAndCheck(string));
        object = this.defaultDirectory;
        boolean bl = false;
        if (object[0] == 47) {
            object = AccessController.doPrivileged(new GetPropertyAction("sun.nio.fs.chdirAllowed", "false"));
            boolean bl2 = ((String)object).length() == 0 ? true : Boolean.valueOf((String)object);
            if (bl2) {
                this.needToResolveAgainstDefaultDirectory = true;
            } else {
                object = UnixNativeDispatcher.getcwd();
                boolean bl3 = ((Object)object).length == this.defaultDirectory.length;
                boolean bl4 = bl3;
                if (bl3) {
                    int n = 0;
                    do {
                        bl4 = bl3;
                        if (n >= ((Object)object).length) break;
                        if (object[n] != this.defaultDirectory[n]) {
                            bl4 = false;
                            break;
                        }
                        ++n;
                    } while (true);
                }
                bl2 = bl;
                if (!bl4) {
                    bl2 = true;
                }
                this.needToResolveAgainstDefaultDirectory = bl2;
            }
            this.rootDirectory = new UnixPath(this, "/");
            return;
        }
        throw new RuntimeException("default directory must be absolute");
    }

    static List<String> standardFileAttributeViews() {
        return Arrays.asList("basic", "posix", "unix", "owner");
    }

    @Override
    public final void close() throws IOException {
        throw new UnsupportedOperationException();
    }

    Pattern compilePathMatchPattern(String string) {
        return Pattern.compile(string);
    }

    void copyNonPosixAttributes(int n, int n2) {
    }

    byte[] defaultDirectory() {
        return this.defaultDirectory;
    }

    abstract FileStore getFileStore(UnixMountEntry var1) throws IOException;

    @Override
    public final Iterable<FileStore> getFileStores() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            try {
                RuntimePermission runtimePermission = new RuntimePermission("getFileStoreAttributes");
                securityManager.checkPermission(runtimePermission);
            }
            catch (SecurityException securityException) {
                return Collections.emptyList();
            }
        }
        return new Iterable<FileStore>(){

            @Override
            public Iterator<FileStore> iterator() {
                return new FileStoreIterator();
            }
        };
    }

    abstract Iterable<UnixMountEntry> getMountEntries();

    @Override
    public final Path getPath(String string2, String ... arrstring) {
        if (arrstring.length != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            for (String string2 : arrstring) {
                if (string2.length() <= 0) continue;
                if (stringBuilder.length() > 0) {
                    stringBuilder.append('/');
                }
                stringBuilder.append(string2);
            }
            string2 = stringBuilder.toString();
        }
        return new UnixPath(this, string2);
    }

    @Override
    public PathMatcher getPathMatcher(String charSequence) {
        block2 : {
            String string;
            block5 : {
                block4 : {
                    block3 : {
                        int n = ((String)charSequence).indexOf(58);
                        if (n <= 0 || n == ((String)charSequence).length()) break block2;
                        string = ((String)charSequence).substring(0, n);
                        charSequence = ((String)charSequence).substring(n + 1);
                        if (!string.equals("glob")) break block3;
                        charSequence = Globs.toUnixRegexPattern((String)charSequence);
                        break block4;
                    }
                    if (!string.equals("regex")) break block5;
                }
                return new PathMatcher(this.compilePathMatchPattern((String)charSequence)){
                    final /* synthetic */ Pattern val$pattern;
                    {
                        this.val$pattern = pattern;
                    }

                    @Override
                    public boolean matches(Path path) {
                        return this.val$pattern.matcher(path.toString()).matches();
                    }
                };
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Syntax '");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("' not recognized");
            throw new UnsupportedOperationException(((StringBuilder)charSequence).toString());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public final Iterable<Path> getRootDirectories() {
        return new Iterable<Path>(Collections.unmodifiableList(Arrays.asList(this.rootDirectory))){
            final /* synthetic */ List val$allowedList;
            {
                this.val$allowedList = list;
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public Iterator<Path> iterator() {
                SecurityManager securityManager;
                try {
                    securityManager = System.getSecurityManager();
                    if (securityManager == null) return this.val$allowedList.iterator();
                }
                catch (SecurityException securityException) {
                    return Collections.emptyList().iterator();
                }
                securityManager.checkRead(UnixFileSystem.this.rootDirectory.toString());
                return this.val$allowedList.iterator();
            }
        };
    }

    @Override
    public final String getSeparator() {
        return "/";
    }

    @Override
    public final UserPrincipalLookupService getUserPrincipalLookupService() {
        return LookupService.instance;
    }

    @Override
    public final boolean isOpen() {
        return true;
    }

    @Override
    public final boolean isReadOnly() {
        return false;
    }

    boolean isSolaris() {
        return false;
    }

    boolean needToResolveAgainstDefaultDirectory() {
        return this.needToResolveAgainstDefaultDirectory;
    }

    String normalizeJavaPath(String string) {
        return string;
    }

    char[] normalizeNativePath(char[] arrc) {
        return arrc;
    }

    @Override
    public final FileSystemProvider provider() {
        return this.provider;
    }

    UnixPath rootDirectory() {
        return this.rootDirectory;
    }

    private class FileStoreIterator
    implements Iterator<FileStore> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Iterator<UnixMountEntry> entries;
        private FileStore next;

        FileStoreIterator() {
            this.entries = UnixFileSystem.this.getMountEntries().iterator();
        }

        private FileStore readNext() {
            while (this.entries.hasNext()) {
                UnixMountEntry unixMountEntry = this.entries.next();
                if (unixMountEntry.isIgnored()) continue;
                Object object = System.getSecurityManager();
                if (object != null) {
                    try {
                        ((SecurityManager)object).checkRead(Util.toString(unixMountEntry.dir()));
                    }
                    catch (SecurityException securityException) {
                        continue;
                    }
                }
                try {
                    object = UnixFileSystem.this.getFileStore(unixMountEntry);
                    return object;
                }
                catch (IOException iOException) {
                    continue;
                }
                break;
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            synchronized (this) {
                boolean bl;
                block5 : {
                    FileStore fileStore;
                    block4 : {
                        fileStore = this.next;
                        bl = true;
                        if (fileStore == null) break block4;
                        return true;
                    }
                    fileStore = this.next = this.readNext();
                    if (fileStore != null) break block5;
                    bl = false;
                }
                return bl;
            }
        }

        @Override
        public FileStore next() {
            synchronized (this) {
                block5 : {
                    if (this.next == null) {
                        this.next = this.readNext();
                    }
                    if (this.next == null) break block5;
                    FileStore fileStore = this.next;
                    this.next = null;
                    return fileStore;
                }
                NoSuchElementException noSuchElementException = new NoSuchElementException();
                throw noSuchElementException;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class LookupService {
        static final UserPrincipalLookupService instance = new UserPrincipalLookupService(){

            @Override
            public GroupPrincipal lookupPrincipalByGroupName(String string) throws IOException {
                return UnixUserPrincipals.lookupGroup(string);
            }

            @Override
            public UserPrincipal lookupPrincipalByName(String string) throws IOException {
                return UnixUserPrincipals.lookupUser(string);
            }
        };

        private LookupService() {
        }

    }

}

