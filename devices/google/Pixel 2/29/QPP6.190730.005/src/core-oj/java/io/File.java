/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.DefaultFileSystem;
import java.io.DeleteOnExitHook;
import java.io.FileFilter;
import java.io.FileSystem;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.Permission;
import java.util.ArrayList;
import sun.misc.Unsafe;

public class File
implements Serializable,
Comparable<File> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long PATH_OFFSET;
    private static final long PREFIX_LENGTH_OFFSET;
    private static final Unsafe UNSAFE;
    private static final FileSystem fs;
    public static final String pathSeparator;
    public static final char pathSeparatorChar;
    public static final String separator;
    public static final char separatorChar;
    private static final long serialVersionUID = 301077366599181567L;
    private volatile transient Path filePath;
    private final String path;
    private final transient int prefixLength;
    private transient PathStatus status = null;

    static {
        fs = DefaultFileSystem.getFileSystem();
        separatorChar = fs.getSeparator();
        Object object = new StringBuilder();
        ((StringBuilder)object).append("");
        ((StringBuilder)object).append(separatorChar);
        separator = ((StringBuilder)object).toString();
        pathSeparatorChar = fs.getPathSeparator();
        object = new StringBuilder();
        ((StringBuilder)object).append("");
        ((StringBuilder)object).append(pathSeparatorChar);
        pathSeparator = ((StringBuilder)object).toString();
        try {
            object = Unsafe.getUnsafe();
            PATH_OFFSET = ((Unsafe)object).objectFieldOffset(File.class.getDeclaredField("path"));
            PREFIX_LENGTH_OFFSET = ((Unsafe)object).objectFieldOffset(File.class.getDeclaredField("prefixLength"));
            UNSAFE = object;
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public File(File object, String string) {
        if (string != null) {
            if (object != null) {
                if (((File)object).path.equals("")) {
                    object = fs;
                    this.path = ((FileSystem)object).resolve(((FileSystem)object).getDefaultParent(), fs.normalize(string));
                } else {
                    FileSystem fileSystem = fs;
                    this.path = fileSystem.resolve(((File)object).path, fileSystem.normalize(string));
                }
            } else {
                this.path = fs.normalize(string);
            }
            this.prefixLength = fs.prefixLength(this.path);
            return;
        }
        throw new NullPointerException();
    }

    public File(String string) {
        if (string != null) {
            this.path = fs.normalize(string);
            this.prefixLength = fs.prefixLength(this.path);
            return;
        }
        throw new NullPointerException();
    }

    private File(String string, int n) {
        this.path = string;
        this.prefixLength = n;
    }

    private File(String string, File file) {
        this.path = fs.resolve(file.path, string);
        this.prefixLength = file.prefixLength;
    }

    public File(String string, String string2) {
        if (string2 != null) {
            if (string != null && !string.isEmpty()) {
                FileSystem fileSystem = fs;
                this.path = fileSystem.resolve(fileSystem.normalize(string), fs.normalize(string2));
            } else {
                this.path = fs.normalize(string2);
            }
            this.prefixLength = fs.prefixLength(this.path);
            return;
        }
        throw new NullPointerException();
    }

    public File(URI object) {
        if (((URI)object).isAbsolute()) {
            if (!((URI)object).isOpaque()) {
                String string = ((URI)object).getScheme();
                if (string != null && string.equalsIgnoreCase("file")) {
                    if (((URI)object).getAuthority() == null) {
                        if (((URI)object).getFragment() == null) {
                            if (((URI)object).getQuery() == null) {
                                if (!((String)(object = ((URI)object).getPath())).equals("")) {
                                    string = fs.fromURIPath((String)object);
                                    char c = separatorChar;
                                    object = string;
                                    if (c != '/') {
                                        object = string.replace('/', c);
                                    }
                                    this.path = fs.normalize((String)object);
                                    this.prefixLength = fs.prefixLength(this.path);
                                    return;
                                }
                                throw new IllegalArgumentException("URI path component is empty");
                            }
                            throw new IllegalArgumentException("URI has a query component");
                        }
                        throw new IllegalArgumentException("URI has a fragment component");
                    }
                    throw new IllegalArgumentException("URI has an authority component");
                }
                throw new IllegalArgumentException("URI scheme is not \"file\"");
            }
            throw new IllegalArgumentException("URI is not hierarchical");
        }
        throw new IllegalArgumentException("URI is not absolute");
    }

    public static File createTempFile(String string, String string2) throws IOException {
        return File.createTempFile(string, string2, null);
    }

    public static File createTempFile(String string, String string2, File file) throws IOException {
        if (string.length() >= 3) {
            File file2;
            if (string2 == null) {
                string2 = ".tmp";
            }
            if (file == null) {
                file = new File(System.getProperty("java.io.tmpdir", "."));
            }
            while ((fs.getBooleanAttributes(file2 = TempDirectory.generateFile(string, string2, file)) & 1) != 0) {
            }
            if (fs.createFileExclusively(file2.getPath())) {
                return file2;
            }
            throw new IOException("Unable to create temporary file");
        }
        throw new IllegalArgumentException("Prefix string too short");
    }

    public static File[] listRoots() {
        return fs.listRoots();
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        synchronized (this) {
            String string = (String)((ObjectInputStream)object).readFields().get("path", null);
            char c = ((ObjectInputStream)object).readChar();
            object = string;
            if (c != separatorChar) {
                object = string.replace(c, separatorChar);
            }
            object = fs.normalize((String)object);
            UNSAFE.putObject(this, PATH_OFFSET, object);
            UNSAFE.putIntVolatile(this, PREFIX_LENGTH_OFFSET, fs.prefixLength((String)object));
            return;
        }
    }

    private static String slashify(String charSequence, boolean bl) {
        char c = separatorChar;
        CharSequence charSequence2 = charSequence;
        if (c != '/') {
            charSequence2 = ((String)charSequence).replace(c, '/');
        }
        charSequence = charSequence2;
        if (!((String)charSequence2).startsWith("/")) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("/");
            ((StringBuilder)charSequence).append((String)charSequence2);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (!((String)charSequence).endsWith("/")) {
            charSequence2 = charSequence;
            if (bl) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("/");
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
        }
        return charSequence2;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        synchronized (this) {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeChar(separatorChar);
            return;
        }
    }

    public boolean canExecute() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkExec(this.path);
        }
        if (this.isInvalid()) {
            return false;
        }
        return fs.checkAccess(this, 1);
    }

    public boolean canRead() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return false;
        }
        return fs.checkAccess(this, 4);
    }

    public boolean canWrite() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        if (this.isInvalid()) {
            return false;
        }
        return fs.checkAccess(this, 2);
    }

    @Override
    public int compareTo(File file) {
        return fs.compare(this, file);
    }

    public boolean createNewFile() throws IOException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        if (!this.isInvalid()) {
            return fs.createFileExclusively(this.path);
        }
        throw new IOException("Invalid file path");
    }

    public boolean delete() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkDelete(this.path);
        }
        if (this.isInvalid()) {
            return false;
        }
        return fs.delete(this);
    }

    public void deleteOnExit() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkDelete(this.path);
        }
        if (this.isInvalid()) {
            return;
        }
        DeleteOnExitHook.add(this.path);
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof File) {
            if (this.compareTo((File)object) == 0) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public boolean exists() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return false;
        }
        return fs.checkAccess(this, 8);
    }

    public File getAbsoluteFile() {
        String string = this.getAbsolutePath();
        return new File(string, fs.prefixLength(string));
    }

    public String getAbsolutePath() {
        return fs.resolve(this);
    }

    public File getCanonicalFile() throws IOException {
        String string = this.getCanonicalPath();
        return new File(string, fs.prefixLength(string));
    }

    public String getCanonicalPath() throws IOException {
        if (!this.isInvalid()) {
            FileSystem fileSystem = fs;
            return fileSystem.canonicalize(fileSystem.resolve(this));
        }
        throw new IOException("Invalid file path");
    }

    public long getFreeSpace() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getFileSystemAttributes"));
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return 0L;
        }
        return fs.getSpace(this, 1);
    }

    public String getName() {
        int n;
        int n2 = this.path.lastIndexOf(separatorChar);
        if (n2 < (n = this.prefixLength)) {
            return this.path.substring(n);
        }
        return this.path.substring(n2 + 1);
    }

    public String getParent() {
        int n;
        int n2 = this.path.lastIndexOf(separatorChar);
        if (n2 < (n = this.prefixLength)) {
            if (n > 0 && (n2 = this.path.length()) > (n = this.prefixLength)) {
                return this.path.substring(0, n);
            }
            return null;
        }
        return this.path.substring(0, n2);
    }

    public File getParentFile() {
        String string = this.getParent();
        if (string == null) {
            return null;
        }
        return new File(string, this.prefixLength);
    }

    public String getPath() {
        return this.path;
    }

    int getPrefixLength() {
        return this.prefixLength;
    }

    public long getTotalSpace() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getFileSystemAttributes"));
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return 0L;
        }
        return fs.getSpace(this, 0);
    }

    public long getUsableSpace() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getFileSystemAttributes"));
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return 0L;
        }
        return fs.getSpace(this, 2);
    }

    public int hashCode() {
        return fs.hashCode(this);
    }

    public boolean isAbsolute() {
        return fs.isAbsolute(this);
    }

    public boolean isDirectory() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        boolean bl = this.isInvalid();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if ((fs.getBooleanAttributes(this) & 4) != 0) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean isFile() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        boolean bl = this.isInvalid();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if ((fs.getBooleanAttributes(this) & 2) != 0) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean isHidden() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        boolean bl = this.isInvalid();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if ((fs.getBooleanAttributes(this) & 8) != 0) {
            bl2 = true;
        }
        return bl2;
    }

    final boolean isInvalid() {
        PathStatus pathStatus = this.status;
        boolean bl = false;
        if (pathStatus == null) {
            pathStatus = this.path.indexOf(0) < 0 ? PathStatus.CHECKED : PathStatus.INVALID;
            this.status = pathStatus;
        }
        if (this.status == PathStatus.INVALID) {
            bl = true;
        }
        return bl;
    }

    public long lastModified() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return 0L;
        }
        return fs.getLastModifiedTime(this);
    }

    public long length() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return 0L;
        }
        return fs.getLength(this);
    }

    public String[] list() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return null;
        }
        return fs.list(this);
    }

    public String[] list(FilenameFilter filenameFilter) {
        String[] arrstring = this.list();
        if (arrstring != null && filenameFilter != null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int i = 0; i < arrstring.length; ++i) {
                if (!filenameFilter.accept(this, arrstring[i])) continue;
                arrayList.add(arrstring[i]);
            }
            return arrayList.toArray(new String[arrayList.size()]);
        }
        return arrstring;
    }

    public File[] listFiles() {
        String[] arrstring = this.list();
        if (arrstring == null) {
            return null;
        }
        int n = arrstring.length;
        File[] arrfile = new File[n];
        for (int i = 0; i < n; ++i) {
            arrfile[i] = new File(arrstring[i], this);
        }
        return arrfile;
    }

    public File[] listFiles(FileFilter fileFilter) {
        String[] arrstring = this.list();
        if (arrstring == null) {
            return null;
        }
        ArrayList<File> arrayList = new ArrayList<File>();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            File file = new File(arrstring[i], this);
            if (fileFilter != null && !fileFilter.accept(file)) continue;
            arrayList.add(file);
        }
        return arrayList.toArray(new File[arrayList.size()]);
    }

    public File[] listFiles(FilenameFilter filenameFilter) {
        String[] arrstring = this.list();
        if (arrstring == null) {
            return null;
        }
        ArrayList<File> arrayList = new ArrayList<File>();
        for (String string : arrstring) {
            if (filenameFilter != null && !filenameFilter.accept(this, string)) continue;
            arrayList.add(new File(string, this));
        }
        return arrayList.toArray(new File[arrayList.size()]);
    }

    public boolean mkdir() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        if (this.isInvalid()) {
            return false;
        }
        return fs.createDirectory(this);
    }

    public boolean mkdirs() {
        boolean bl = this.exists();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (this.mkdir()) {
            return true;
        }
        try {
            File file = this.getCanonicalFile();
            File file2 = file.getParentFile();
            if (file2 != null && (file2.mkdirs() || file2.exists()) && file.mkdir()) {
                bl2 = true;
            }
            return bl2;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public boolean renameTo(File file) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
            securityManager.checkWrite(file.path);
        }
        if (file != null) {
            if (!this.isInvalid() && !file.isInvalid()) {
                return fs.rename(this, file);
            }
            return false;
        }
        throw new NullPointerException();
    }

    public boolean setExecutable(boolean bl) {
        return this.setExecutable(bl, true);
    }

    public boolean setExecutable(boolean bl, boolean bl2) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        if (this.isInvalid()) {
            return false;
        }
        return fs.setPermission(this, 1, bl, bl2);
    }

    public boolean setLastModified(long l) {
        if (l >= 0L) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkWrite(this.path);
            }
            if (this.isInvalid()) {
                return false;
            }
            return fs.setLastModifiedTime(this, l);
        }
        throw new IllegalArgumentException("Negative time");
    }

    public boolean setReadOnly() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        if (this.isInvalid()) {
            return false;
        }
        return fs.setReadOnly(this);
    }

    public boolean setReadable(boolean bl) {
        return this.setReadable(bl, true);
    }

    public boolean setReadable(boolean bl, boolean bl2) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        if (this.isInvalid()) {
            return false;
        }
        return fs.setPermission(this, 4, bl, bl2);
    }

    public boolean setWritable(boolean bl) {
        return this.setWritable(bl, true);
    }

    public boolean setWritable(boolean bl, boolean bl2) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        if (this.isInvalid()) {
            return false;
        }
        return fs.setPermission(this, 2, bl, bl2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Path toPath() {
        Path path;
        Path path2 = path = this.filePath;
        if (path != null) return path2;
        synchronized (this) {
            path2 = path = this.filePath;
            if (path != null) return path2;
            this.filePath = path2 = FileSystems.getDefault().getPath(this.path, new String[0]);
            return path2;
        }
    }

    public String toString() {
        return this.getPath();
    }

    public URI toURI() {
        Object object;
        String string;
        try {
            object = this.getAbsoluteFile();
            string = File.slashify(((File)object).getPath(), ((File)object).isDirectory());
            object = string;
        }
        catch (URISyntaxException uRISyntaxException) {
            throw new Error(uRISyntaxException);
        }
        if (string.startsWith("//")) {
            object = new StringBuilder();
            ((StringBuilder)object).append("//");
            ((StringBuilder)object).append(string);
            object = ((StringBuilder)object).toString();
        }
        object = new URI("file", null, (String)object, null);
        return object;
    }

    @Deprecated
    public URL toURL() throws MalformedURLException {
        if (!this.isInvalid()) {
            return new URL("file", "", File.slashify(this.getAbsolutePath(), this.getAbsoluteFile().isDirectory()));
        }
        throw new MalformedURLException("Invalid file path");
    }

    private static enum PathStatus {
        INVALID,
        CHECKED;
        
    }

    private static class TempDirectory {
        private TempDirectory() {
        }

        static File generateFile(String object, String charSequence, File file) throws IOException {
            long l = Math.randomLongInternal();
            l = l == Long.MIN_VALUE ? 0L : Math.abs(l);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append(Long.toString(l));
            stringBuilder.append((String)charSequence);
            charSequence = stringBuilder.toString();
            object = new File(file, (String)charSequence);
            if (((String)charSequence).equals(((File)object).getName()) && !((File)object).isInvalid()) {
                return object;
            }
            if (System.getSecurityManager() != null) {
                throw new IOException("Unable to create temporary file");
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unable to create temporary file, ");
            ((StringBuilder)charSequence).append(object);
            throw new IOException(((StringBuilder)charSequence).toString());
        }
    }

}

