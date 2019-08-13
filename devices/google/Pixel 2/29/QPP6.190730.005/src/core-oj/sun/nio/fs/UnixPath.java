/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemException;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Objects;
import sun.nio.fs.AbstractPath;
import sun.nio.fs.AbstractWatchService;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixFileSystem;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixUriUtils;
import sun.nio.fs.Util;

class UnixPath
extends AbstractPath {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static ThreadLocal<SoftReference<CharsetEncoder>> encoder = new ThreadLocal();
    private final UnixFileSystem fs;
    private int hash;
    private volatile int[] offsets;
    private final byte[] path;
    private volatile String stringValue;

    UnixPath(UnixFileSystem unixFileSystem, String string) {
        this(unixFileSystem, UnixPath.encode(unixFileSystem, UnixPath.normalizeAndCheck(string)));
    }

    UnixPath(UnixFileSystem unixFileSystem, byte[] arrby) {
        this.fs = unixFileSystem;
        this.path = arrby;
    }

    private static void checkNotNul(String string, char c) {
        if (c != '\u0000') {
            return;
        }
        throw new InvalidPathException(string, "Nul character not allowed");
    }

    private UnixPath emptyPath() {
        return new UnixPath((UnixFileSystem)this.getFileSystem(), new byte[0]);
    }

    private static byte[] encode(UnixFileSystem arrby, String string) {
        Object object = encoder.get();
        object = object != null ? object.get() : null;
        Object object2 = object;
        if (object == null) {
            object2 = Util.jnuEncoding().newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
            encoder.set(new SoftReference<Object>(object2));
        }
        Object object3 = arrby.normalizeNativePath(string.toCharArray());
        object = new byte[(int)((double)((char[])object3).length * (double)((CharsetEncoder)object2).maxBytesPerChar())];
        arrby = ByteBuffer.wrap(object);
        object3 = CharBuffer.wrap(object3);
        ((CharsetEncoder)object2).reset();
        int n = !((CharsetEncoder)object2).encode((CharBuffer)object3, (ByteBuffer)arrby, true).isUnderflow() ? 1 : true ^ ((CharsetEncoder)object2).flush((ByteBuffer)arrby).isUnderflow();
        if (n == 0) {
            n = arrby.position();
            arrby = object;
            if (n != ((byte[])object).length) {
                arrby = Arrays.copyOf(object, n);
            }
            return arrby;
        }
        throw new InvalidPathException(string, "Malformed input or input contains unmappable characters");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initOffsets() {
        byte[] arrby;
        int n;
        if (this.offsets != null) return;
        int n2 = 0;
        int n3 = 0;
        if (this.isEmpty()) {
            n = 1;
        } else {
            do {
                arrby = this.path;
                n = n2;
                if (n3 >= arrby.length) break;
                n = n3 + 1;
                if (arrby[n3] != 47) {
                    n3 = n2 + 1;
                    for (n2 = n; n2 < (arrby = this.path).length && arrby[n2] != 47; ++n2) {
                    }
                    n = n3;
                    n3 = n2;
                } else {
                    n3 = n;
                    n = n2;
                }
                n2 = n;
            } while (true);
        }
        arrby = new int[n];
        n3 = 0;
        n2 = 0;
        do {
            byte[] arrby2;
            if (n2 >= (arrby2 = this.path).length) {
                synchronized (this) {
                    if (this.offsets != null) return;
                    this.offsets = arrby;
                    return;
                }
            }
            if (arrby2[n2] == 47) {
                ++n2;
                continue;
            }
            n = n2 + 1;
            arrby[n3] = n2;
            for (n2 = n; n2 < (arrby2 = this.path).length && arrby2[n2] != 47; ++n2) {
            }
            ++n3;
        } while (true);
    }

    private boolean isEmpty() {
        boolean bl = this.path.length == 0;
        return bl;
    }

    private static String normalize(String string, int n, int n2) {
        if (n == 0) {
            return string;
        }
        while (n > 0 && string.charAt(n - 1) == '/') {
            --n;
        }
        if (n == 0) {
            return "/";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        if (n2 > 0) {
            stringBuilder.append(string.substring(0, n2));
        }
        int n3 = 0;
        while (n2 < n) {
            char c = string.charAt(n2);
            if (c != '/' || n3 != 47) {
                UnixPath.checkNotNul(string, c);
                stringBuilder.append(c);
                n3 = c;
            }
            ++n2;
        }
        return stringBuilder.toString();
    }

    static String normalizeAndCheck(String string) {
        int n = string.length();
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == '/' && n2 == 47) {
                return UnixPath.normalize(string, n, i - 1);
            }
            UnixPath.checkNotNul(string, c);
            n2 = c;
        }
        if (n2 == 47) {
            return UnixPath.normalize(string, n, n - 1);
        }
        return string;
    }

    private static byte[] resolve(byte[] arrby, byte[] arrby2) {
        int n = arrby.length;
        int n2 = arrby2.length;
        if (n2 == 0) {
            return arrby;
        }
        if (n != 0 && arrby2[0] != 47) {
            if (n == 1 && arrby[0] == 47) {
                arrby = new byte[n2 + 1];
                arrby[0] = (byte)47;
                System.arraycopy(arrby2, 0, arrby, 1, n2);
            } else {
                byte[] arrby3 = new byte[n + 1 + n2];
                System.arraycopy(arrby, 0, arrby3, 0, n);
                arrby3[arrby.length] = (byte)47;
                System.arraycopy(arrby2, 0, arrby3, n + 1, n2);
                arrby = arrby3;
            }
            return arrby;
        }
        return arrby2;
    }

    static UnixPath toUnixPath(Path path) {
        if (path != null) {
            if (path instanceof UnixPath) {
                return (UnixPath)path;
            }
            throw new ProviderMismatchException();
        }
        throw new NullPointerException();
    }

    byte[] asByteArray() {
        return this.path;
    }

    void checkDelete() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkDelete(this.getPathForPermissionCheck());
        }
    }

    void checkRead() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.getPathForPermissionCheck());
        }
    }

    void checkWrite() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.getPathForPermissionCheck());
        }
    }

    @Override
    public int compareTo(Path arrby) {
        int n = this.path.length;
        int n2 = ((UnixPath)arrby).path.length;
        int n3 = Math.min(n, n2);
        byte[] arrby2 = this.path;
        arrby = ((UnixPath)arrby).path;
        for (int i = 0; i < n3; ++i) {
            int n4 = arrby2[i] & 255;
            int n5 = arrby[i] & 255;
            if (n4 == n5) continue;
            return n4 - n5;
        }
        return n - n2;
    }

    @Override
    public boolean endsWith(Path path) {
        int n;
        int n2;
        if (!(Objects.requireNonNull(path) instanceof UnixPath)) {
            return false;
        }
        path = (UnixPath)path;
        int n3 = ((UnixPath)path).path.length;
        int n4 = this.path.length;
        if (n3 > n4) {
            return false;
        }
        if (n4 > 0 && n3 == 0) {
            return false;
        }
        if (((UnixPath)path).isAbsolute() && !this.isAbsolute()) {
            return false;
        }
        int n5 = this.getNameCount();
        int n6 = ((UnixPath)path).getNameCount();
        if (n6 > n5) {
            return false;
        }
        if (n6 == n5) {
            if (n5 == 0) {
                return true;
            }
            n2 = n = n4;
            if (this.isAbsolute()) {
                n2 = n;
                if (!((UnixPath)path).isAbsolute()) {
                    n2 = n - 1;
                }
            }
            if (n3 != n2) {
                return false;
            }
        } else if (((UnixPath)path).isAbsolute()) {
            return false;
        }
        n6 = this.offsets[n5 - n6];
        n5 = ((UnixPath)path).offsets[0];
        n2 = n6;
        if (n3 - n5 != n4 - n6) {
            return false;
        }
        for (n = n5; n < n3; ++n) {
            if (this.path[n2] != ((UnixPath)path).path[n]) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof UnixPath) {
            if (this.compareTo((Path)object) == 0) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    byte[] getByteArrayForSysCalls() {
        if (((UnixFileSystem)this.getFileSystem()).needToResolveAgainstDefaultDirectory()) {
            return UnixPath.resolve(((UnixFileSystem)this.getFileSystem()).defaultDirectory(), this.path);
        }
        if (!this.isEmpty()) {
            return this.path;
        }
        return new byte[]{46};
    }

    @Override
    public UnixPath getFileName() {
        byte[] arrby;
        this.initOffsets();
        int n = this.offsets.length;
        if (n == 0) {
            return null;
        }
        if (n == 1 && (arrby = this.path).length > 0 && arrby[0] != 47) {
            return this;
        }
        int n2 = this.offsets[n - 1];
        byte[] arrby2 = this.path;
        n = arrby2.length - n2;
        arrby = new byte[n];
        System.arraycopy(arrby2, n2, arrby, 0, n);
        return new UnixPath((UnixFileSystem)this.getFileSystem(), arrby);
    }

    @Override
    public UnixFileSystem getFileSystem() {
        return this.fs;
    }

    @Override
    public UnixPath getName(int n) {
        this.initOffsets();
        if (n >= 0) {
            if (n < this.offsets.length) {
                int n2 = this.offsets[n];
                n = n == this.offsets.length - 1 ? this.path.length - n2 : this.offsets[n + 1] - n2 - 1;
                byte[] arrby = new byte[n];
                System.arraycopy(this.path, n2, arrby, 0, n);
                return new UnixPath((UnixFileSystem)this.getFileSystem(), arrby);
            }
            throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int getNameCount() {
        this.initOffsets();
        return this.offsets.length;
    }

    @Override
    public UnixPath getParent() {
        this.initOffsets();
        int n = this.offsets.length;
        if (n == 0) {
            return null;
        }
        if ((n = this.offsets[n - 1] - 1) <= 0) {
            return this.getRoot();
        }
        byte[] arrby = new byte[n];
        System.arraycopy(this.path, 0, arrby, 0, n);
        return new UnixPath((UnixFileSystem)this.getFileSystem(), arrby);
    }

    String getPathForExceptionMessage() {
        return this.toString();
    }

    String getPathForPermissionCheck() {
        if (((UnixFileSystem)this.getFileSystem()).needToResolveAgainstDefaultDirectory()) {
            return Util.toString(this.getByteArrayForSysCalls());
        }
        return this.toString();
    }

    @Override
    public UnixPath getRoot() {
        byte[] arrby = this.path;
        if (arrby.length > 0 && arrby[0] == 47) {
            return ((UnixFileSystem)this.getFileSystem()).rootDirectory();
        }
        return null;
    }

    @Override
    public int hashCode() {
        int n;
        int n2 = n = this.hash;
        if (n == 0) {
            byte[] arrby;
            for (n2 = 0; n2 < (arrby = this.path).length; ++n2) {
                n = n * 31 + (arrby[n2] & 255);
            }
            this.hash = n;
            n2 = n;
        }
        return n2;
    }

    @Override
    public boolean isAbsolute() {
        boolean bl;
        byte[] arrby = this.path;
        int n = arrby.length;
        boolean bl2 = bl = false;
        if (n > 0) {
            bl2 = bl;
            if (arrby[0] == 47) {
                bl2 = true;
            }
        }
        return bl2;
    }

    @Override
    public Path normalize() {
        int n = this.getNameCount();
        if (n != 0 && !this.isEmpty()) {
            int n2;
            byte[] arrby;
            int n3;
            int n4;
            int n5;
            int n6;
            boolean[] arrbl = new boolean[n];
            Object object = new int[n];
            int n7 = n;
            int n8 = 0;
            boolean bl = this.isAbsolute();
            for (n6 = 0; n6 < n; ++n6) {
                n2 = this.offsets[n6];
                n4 = n6 == this.offsets.length - 1 ? this.path.length - n2 : this.offsets[n6 + 1] - n2 - 1;
                object[n6] = n4;
                arrby = this.path;
                n5 = n7;
                n3 = n8;
                if (arrby[n2] == 46) {
                    if (n4 == 1) {
                        arrbl[n6] = true;
                        n5 = n7 - 1;
                        n3 = n8;
                    } else {
                        n5 = n7;
                        n3 = n8;
                        if (arrby[n2 + 1] == 46) {
                            n3 = 1;
                            n5 = n7;
                        }
                    }
                }
                n7 = n5;
                n8 = n3;
            }
            n6 = n7;
            if (n8 != 0) {
                do {
                    n3 = n7;
                    n4 = -1;
                    n6 = n3;
                    for (n7 = 0; n7 < n; ++n7) {
                        if (arrbl[n7]) {
                            n8 = n6;
                            n5 = n4;
                        } else if (object[n7] != 2) {
                            n5 = n7;
                            n8 = n6;
                        } else {
                            arrby = this.path;
                            n8 = this.offsets[n7];
                            if (arrby[n8] == 46 && arrby[n8 + 1] == 46) {
                                if (n4 >= 0) {
                                    arrbl[n4] = true;
                                    arrbl[n7] = true;
                                    n8 = n6 - 2;
                                    n5 = -1;
                                } else {
                                    n8 = n6;
                                    n5 = n4;
                                    if (bl) {
                                        n5 = 0;
                                        n8 = 0;
                                        do {
                                            n2 = n5;
                                            if (n8 >= n7) break;
                                            if (!arrbl[n8]) {
                                                n2 = 1;
                                                break;
                                            }
                                            ++n8;
                                        } while (true);
                                        n8 = n6;
                                        n5 = n4;
                                        if (n2 == 0) {
                                            arrbl[n7] = true;
                                            n8 = n6 - 1;
                                            n5 = n4;
                                        }
                                    }
                                }
                            } else {
                                n5 = n7;
                                n8 = n6;
                            }
                        }
                        n6 = n8;
                        n4 = n5;
                    }
                    n7 = n6;
                } while (n3 > n6);
            }
            if (n6 == n) {
                return this;
            }
            if (n6 == 0) {
                object = bl ? ((UnixFileSystem)this.getFileSystem()).rootDirectory() : this.emptyPath();
                return object;
            }
            n7 = n8 = n6 - 1;
            if (bl) {
                n7 = n8 + 1;
            }
            n5 = n7;
            for (n8 = 0; n8 < n; ++n8) {
                n7 = n5;
                if (!arrbl[n8]) {
                    n7 = n5 + object[n8];
                }
                n5 = n7;
            }
            arrby = new byte[n5];
            n7 = 0;
            if (bl) {
                arrby[0] = (byte)47;
                n7 = 0 + 1;
            }
            n5 = n7;
            n3 = n6;
            for (n8 = 0; n8 < n; ++n8) {
                n6 = n3--;
                n7 = n5;
                if (!arrbl[n8]) {
                    System.arraycopy(this.path, this.offsets[n8], arrby, n5, object[n8]);
                    n6 = n3;
                    n7 = n5 += object[n8];
                    if (n3 > 0) {
                        arrby[n5] = (byte)47;
                        n7 = n5 + 1;
                        n6 = n3;
                    }
                }
                n3 = n6;
                n5 = n7;
            }
            return new UnixPath((UnixFileSystem)this.getFileSystem(), arrby);
        }
        return this;
    }

    int openForAttributeAccess(boolean bl) throws IOException {
        int n;
        int n2 = n = UnixConstants.O_RDONLY;
        if (!bl) {
            if (UnixConstants.O_NOFOLLOW != 0) {
                n2 = n | UnixConstants.O_NOFOLLOW;
            } else {
                throw new IOException("NOFOLLOW_LINKS is not supported on this platform");
            }
        }
        try {
            n2 = UnixNativeDispatcher.open(this, n2, 0);
            return n2;
        }
        catch (UnixException unixException) {
            if (((UnixFileSystem)this.getFileSystem()).isSolaris() && unixException.errno() == UnixConstants.EINVAL) {
                unixException.setError(UnixConstants.ELOOP);
            }
            if (unixException.errno() != UnixConstants.ELOOP) {
                unixException.rethrowAsIOException(this);
                return -1;
            }
            String string = this.getPathForExceptionMessage();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(unixException.getMessage());
            stringBuilder.append(" or unable to access attributes of symbolic link");
            throw new FileSystemException(string, null, stringBuilder.toString());
        }
    }

    @Override
    public WatchKey register(WatchService watchService, WatchEvent.Kind<?>[] arrkind, WatchEvent.Modifier ... arrmodifier) throws IOException {
        if (watchService != null) {
            if (watchService instanceof AbstractWatchService) {
                this.checkRead();
                return ((AbstractWatchService)watchService).register(this, arrkind, arrmodifier);
            }
            throw new ProviderMismatchException();
        }
        throw new NullPointerException();
    }

    @Override
    public UnixPath relativize(Path arrby) {
        byte[] arrby2 = UnixPath.toUnixPath((Path)arrby);
        if (arrby2.equals(this)) {
            return this.emptyPath();
        }
        if (this.isAbsolute() == arrby2.isAbsolute()) {
            int n;
            int n2;
            if (this.isEmpty()) {
                return arrby2;
            }
            int n3 = this.getNameCount();
            int n4 = n3 > (n = arrby2.getNameCount()) ? n : n3;
            for (n2 = 0; n2 < n4 && ((UnixPath)this.getName(n2)).equals(arrby2.getName(n2)); ++n2) {
            }
            n4 = n3 - n2;
            if (n2 < n) {
                arrby = arrby2.subpath(n2, n);
                if (n4 == 0) {
                    return arrby;
                }
                boolean bl = arrby2.isEmpty();
                n2 = n = n4 * 3 + arrby.path.length;
                if (bl) {
                    n2 = n - 1;
                }
                arrby2 = new byte[n2];
                n = 0;
                for (n2 = n4; n2 > 0; --n2) {
                    n4 = n + 1;
                    arrby2[n] = (byte)46;
                    n = n4 + 1;
                    arrby2[n4] = (byte)46;
                    if (bl) {
                        n4 = n;
                        if (n2 > 1) {
                            arrby2[n] = (byte)47;
                            n4 = n + 1;
                        }
                    } else {
                        arrby2[n] = (byte)47;
                        n4 = n + 1;
                    }
                    n = n4;
                }
                arrby = arrby.path;
                System.arraycopy(arrby, 0, arrby2, n, arrby.length);
                return new UnixPath((UnixFileSystem)this.getFileSystem(), arrby2);
            }
            arrby = new byte[n4 * 3 - 1];
            n = 0;
            for (n2 = n4; n2 > 0; --n2) {
                n4 = n + 1;
                arrby[n] = (byte)46;
                n = n4 + 1;
                arrby[n4] = (byte)46;
                n4 = n;
                if (n2 > 1) {
                    arrby[n] = (byte)47;
                    n4 = n + 1;
                }
                n = n4;
            }
            return new UnixPath((UnixFileSystem)this.getFileSystem(), arrby);
        }
        throw new IllegalArgumentException("'other' is different type of Path");
    }

    @Override
    public UnixPath resolve(Path arrby) {
        byte[] arrby2 = UnixPath.toUnixPath((Path)arrby).path;
        if (arrby2.length > 0 && arrby2[0] == 47) {
            return (UnixPath)arrby;
        }
        arrby = UnixPath.resolve(this.path, arrby2);
        return new UnixPath((UnixFileSystem)this.getFileSystem(), arrby);
    }

    UnixPath resolve(byte[] arrby) {
        return this.resolve(new UnixPath((UnixFileSystem)this.getFileSystem(), arrby));
    }

    @Override
    public boolean startsWith(Path arrby) {
        if (!(Objects.requireNonNull(arrby) instanceof UnixPath)) {
            return false;
        }
        UnixPath unixPath = (UnixPath)arrby;
        if (unixPath.path.length > this.path.length) {
            return false;
        }
        int n = this.getNameCount();
        int n2 = unixPath.getNameCount();
        if (n2 == 0 && this.isAbsolute()) {
            return unixPath.isEmpty() ^ true;
        }
        if (n2 > n) {
            return false;
        }
        if (n2 == n && this.path.length != unixPath.path.length) {
            return false;
        }
        for (n = 0; n < n2; ++n) {
            if (Integer.valueOf(this.offsets[n]).equals(unixPath.offsets[n])) continue;
            return false;
        }
        for (n = 0; n < (arrby = unixPath.path).length; ++n) {
            if (this.path[n] == arrby[n]) continue;
            return false;
        }
        arrby = this.path;
        return n >= arrby.length || arrby[n] == 47;
    }

    @Override
    public UnixPath subpath(int n, int n2) {
        this.initOffsets();
        if (n >= 0) {
            if (n < this.offsets.length) {
                if (n2 <= this.offsets.length) {
                    if (n < n2) {
                        int n3 = this.offsets[n];
                        n = n2 == this.offsets.length ? this.path.length - n3 : this.offsets[n2] - n3 - 1;
                        byte[] arrby = new byte[n];
                        System.arraycopy(this.path, n3, arrby, 0, n);
                        return new UnixPath((UnixFileSystem)this.getFileSystem(), arrby);
                    }
                    throw new IllegalArgumentException();
                }
                throw new IllegalArgumentException();
            }
            throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public UnixPath toAbsolutePath() {
        if (this.isAbsolute()) {
            return this;
        }
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPropertyAccess("user.dir");
        }
        return new UnixPath((UnixFileSystem)this.getFileSystem(), UnixPath.resolve(((UnixFileSystem)this.getFileSystem()).defaultDirectory(), this.path));
    }

    @Override
    public Path toRealPath(LinkOption ... object) throws IOException {
        this.checkRead();
        Path path = this.toAbsolutePath();
        if (Util.followLinks((LinkOption[])object)) {
            try {
                object = UnixNativeDispatcher.realpath((UnixPath)path);
                object = new UnixPath((UnixFileSystem)this.getFileSystem(), (byte[])object);
                return object;
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException(this);
            }
        }
        object = this.fs.rootDirectory();
        for (int i = 0; i < ((UnixPath)path).getNameCount(); ++i) {
            Path path2 = ((UnixPath)path).getName(i);
            if (((UnixPath)path2).asByteArray().length == 1 && ((UnixPath)path2).asByteArray()[0] == 46) continue;
            if (((UnixPath)path2).asByteArray().length == 2 && ((UnixPath)path2).asByteArray()[0] == 46 && ((UnixPath)path2).asByteArray()[1] == 46) {
                Object object2 = null;
                try {
                    UnixFileAttributes unixFileAttributes = UnixFileAttributes.get((UnixPath)object, false);
                    object2 = unixFileAttributes;
                }
                catch (UnixException unixException) {
                    unixException.rethrowAsIOException((UnixPath)object);
                }
                if (!((UnixFileAttributes)object2).isSymbolicLink()) {
                    object = object2 = ((UnixPath)object).getParent();
                    if (object2 != null) continue;
                    object = this.fs.rootDirectory();
                    continue;
                }
            }
            object = ((UnixPath)object).resolve(path2);
        }
        try {
            UnixFileAttributes.get((UnixPath)object, false);
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException((UnixPath)object);
        }
        return object;
    }

    @Override
    public String toString() {
        if (this.stringValue == null) {
            this.stringValue = this.fs.normalizeJavaPath(Util.toString(this.path));
        }
        return this.stringValue;
    }

    @Override
    public URI toUri() {
        return UnixUriUtils.toUri(this);
    }
}

