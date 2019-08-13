/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.io.Reader;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Properties;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixFileStoreAttributes;
import sun.nio.fs.UnixFileSystem;
import sun.nio.fs.UnixMountEntry;
import sun.nio.fs.UnixPath;
import sun.nio.fs.Util;

abstract class UnixFileStore
extends FileStore {
    private static final Object loadLock = new Object();
    private static volatile Properties props;
    private final long dev;
    private final UnixMountEntry entry;
    private final UnixPath file;

    UnixFileStore(UnixFileSystem unixFileSystem, UnixMountEntry unixMountEntry) throws IOException {
        this.file = new UnixPath(unixFileSystem, unixMountEntry.dir());
        long l = unixMountEntry.dev() == 0L ? UnixFileStore.devFor(this.file) : unixMountEntry.dev();
        this.dev = l;
        this.entry = unixMountEntry;
    }

    UnixFileStore(UnixPath unixPath) throws IOException {
        this.file = unixPath;
        this.dev = UnixFileStore.devFor(unixPath);
        this.entry = this.findMountEntry();
    }

    private static long devFor(UnixPath unixPath) throws IOException {
        try {
            long l = UnixFileAttributes.get(unixPath, true).dev();
            return l;
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException(unixPath);
            return 0L;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Properties loadProperties() {
        Properties properties = new Properties();
        Object object = new StringBuilder();
        ((StringBuilder)object).append(System.getProperty("java.home"));
        ((StringBuilder)object).append("/lib/fstypes.properties");
        object = Paths.get(((StringBuilder)object).toString(), new String[0]);
        SeekableByteChannel seekableByteChannel = Files.newByteChannel((Path)object, new OpenOption[0]);
        properties.load(Channels.newReader(seekableByteChannel, "UTF-8"));
        if (seekableByteChannel == null) return properties;
        seekableByteChannel.close();
        return properties;
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (seekableByteChannel == null) throw throwable2;
                try {
                    seekableByteChannel.close();
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        throwable.addSuppressed(throwable3);
                        throw throwable2;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
            }
        }
        return properties;
    }

    private UnixFileStoreAttributes readAttributes() throws IOException {
        try {
            UnixFileStoreAttributes unixFileStoreAttributes = UnixFileStoreAttributes.get(this.file);
            return unixFileStoreAttributes;
        }
        catch (UnixException unixException) {
            unixException.rethrowAsIOException(this.file);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    FeatureStatus checkIfFeaturePresent(String string) {
        PrivilegedAction<Properties> privilegedAction;
        Object object;
        if (props == null) {
            object = loadLock;
            synchronized (object) {
                if (props == null) {
                    privilegedAction = new PrivilegedAction<Properties>(){

                        @Override
                        public Properties run() {
                            return UnixFileStore.loadProperties();
                        }
                    };
                    props = (Properties)AccessController.doPrivileged(privilegedAction);
                }
            }
        }
        if ((object = props.getProperty(this.type())) != null) {
            privilegedAction = ((String)object).split("\\s");
            int n = ((String[])privilegedAction).length;
            for (int i = 0; i < n; ++i) {
                object = privilegedAction[i].trim().toLowerCase();
                if (((String)object).equals(string)) {
                    return FeatureStatus.PRESENT;
                }
                if (!((String)object).startsWith("no") || !((String)object).substring(2).equals(string)) continue;
                return FeatureStatus.NOT_PRESENT;
            }
        }
        return FeatureStatus.UNKNOWN;
    }

    long dev() {
        return this.dev;
    }

    UnixMountEntry entry() {
        return this.entry;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof UnixFileStore)) {
            return false;
        }
        object = (UnixFileStore)object;
        if (this.dev != ((UnixFileStore)object).dev || !Arrays.equals(this.entry.dir(), ((UnixFileStore)object).entry.dir()) || !this.entry.name().equals(((UnixFileStore)object).entry.name())) {
            bl = false;
        }
        return bl;
    }

    UnixPath file() {
        return this.file;
    }

    abstract UnixMountEntry findMountEntry() throws IOException;

    @Override
    public Object getAttribute(String string) throws IOException {
        if (string.equals("totalSpace")) {
            return this.getTotalSpace();
        }
        if (string.equals("usableSpace")) {
            return this.getUsableSpace();
        }
        if (string.equals("unallocatedSpace")) {
            return this.getUnallocatedSpace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        stringBuilder.append(string);
        stringBuilder.append("' not recognized");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    @Override
    public <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> class_) {
        if (class_ != null) {
            return null;
        }
        throw new NullPointerException();
    }

    @Override
    public long getTotalSpace() throws IOException {
        UnixFileStoreAttributes unixFileStoreAttributes = this.readAttributes();
        return unixFileStoreAttributes.blockSize() * unixFileStoreAttributes.totalBlocks();
    }

    @Override
    public long getUnallocatedSpace() throws IOException {
        UnixFileStoreAttributes unixFileStoreAttributes = this.readAttributes();
        return unixFileStoreAttributes.blockSize() * unixFileStoreAttributes.freeBlocks();
    }

    @Override
    public long getUsableSpace() throws IOException {
        UnixFileStoreAttributes unixFileStoreAttributes = this.readAttributes();
        return unixFileStoreAttributes.blockSize() * unixFileStoreAttributes.availableBlocks();
    }

    public int hashCode() {
        long l = this.dev;
        return (int)(l ^ l >>> 32) ^ Arrays.hashCode(this.entry.dir());
    }

    @Override
    public boolean isReadOnly() {
        return this.entry.isReadOnly();
    }

    @Override
    public String name() {
        return this.entry.name();
    }

    @Override
    public boolean supportsFileAttributeView(Class<? extends FileAttributeView> class_) {
        if (class_ != null) {
            boolean bl = true;
            if (class_ == BasicFileAttributeView.class) {
                return true;
            }
            if (class_ != PosixFileAttributeView.class && class_ != FileOwnerAttributeView.class) {
                return false;
            }
            if (this.checkIfFeaturePresent("posix") == FeatureStatus.NOT_PRESENT) {
                bl = false;
            }
            return bl;
        }
        throw new NullPointerException();
    }

    @Override
    public boolean supportsFileAttributeView(String string) {
        if (!string.equals("basic") && !string.equals("unix")) {
            if (string.equals("posix")) {
                return this.supportsFileAttributeView(PosixFileAttributeView.class);
            }
            if (string.equals("owner")) {
                return this.supportsFileAttributeView(FileOwnerAttributeView.class);
            }
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(Util.toString(this.entry.dir()));
        stringBuilder.append(" (");
        stringBuilder.append(this.entry.name());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public String type() {
        return this.entry.fstype();
    }

    static enum FeatureStatus {
        PRESENT,
        NOT_PRESENT,
        UNKNOWN;
        
    }

}

