/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ErrorManager;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;
import java.util.logging.XMLFormatter;

public class FileHandler
extends StreamHandler {
    private static final int MAX_LOCKS = 100;
    private static final Set<String> locks = new HashSet<String>();
    private boolean append;
    private int count;
    private File[] files;
    private int limit;
    private FileChannel lockFileChannel;
    private String lockFileName;
    private MeteredStream meter;
    private String pattern;

    public FileHandler() throws IOException, SecurityException {
        this.checkPermission();
        this.configure();
        this.openFiles();
    }

    public FileHandler(String string) throws IOException, SecurityException {
        if (string.length() >= 1) {
            this.checkPermission();
            this.configure();
            this.pattern = string;
            this.limit = 0;
            this.count = 1;
            this.openFiles();
            return;
        }
        throw new IllegalArgumentException();
    }

    public FileHandler(String string, int n, int n2) throws IOException, SecurityException {
        if (n >= 0 && n2 >= 1 && string.length() >= 1) {
            this.checkPermission();
            this.configure();
            this.pattern = string;
            this.limit = n;
            this.count = n2;
            this.openFiles();
            return;
        }
        throw new IllegalArgumentException();
    }

    public FileHandler(String string, int n, int n2, boolean bl) throws IOException, SecurityException {
        if (n >= 0 && n2 >= 1 && string.length() >= 1) {
            this.checkPermission();
            this.configure();
            this.pattern = string;
            this.limit = n;
            this.count = n2;
            this.append = bl;
            this.openFiles();
            return;
        }
        throw new IllegalArgumentException();
    }

    public FileHandler(String string, boolean bl) throws IOException, SecurityException {
        if (string.length() >= 1) {
            this.checkPermission();
            this.configure();
            this.pattern = string;
            this.limit = 0;
            this.count = 1;
            this.append = bl;
            this.openFiles();
            return;
        }
        throw new IllegalArgumentException();
    }

    private void configure() {
        LogManager logManager = LogManager.getLogManager();
        String string = this.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".pattern");
        this.pattern = logManager.getStringProperty(stringBuilder.toString(), "%h/java%u.log");
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".limit");
        this.limit = logManager.getIntProperty(stringBuilder.toString(), 0);
        if (this.limit < 0) {
            this.limit = 0;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".count");
        this.count = logManager.getIntProperty(stringBuilder.toString(), 1);
        if (this.count <= 0) {
            this.count = 1;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".append");
        this.append = logManager.getBooleanProperty(stringBuilder.toString(), false);
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".level");
        this.setLevel(logManager.getLevelProperty(stringBuilder.toString(), Level.ALL));
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".filter");
        this.setFilter(logManager.getFilterProperty(stringBuilder.toString(), null));
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".formatter");
        this.setFormatter(logManager.getFormatterProperty(stringBuilder.toString(), new XMLFormatter()));
        try {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(".encoding");
            this.setEncoding(logManager.getStringProperty(stringBuilder.toString(), null));
        }
        catch (Exception exception) {
            try {
                this.setEncoding(null);
            }
            catch (Exception exception2) {
                // empty catch block
            }
        }
    }

    private File generate(String object, int n, int n2) throws IOException {
        Object object2 = null;
        Object object3 = "";
        int n3 = 0;
        boolean bl = false;
        boolean bl2 = false;
        while (n3 < ((String)object).length()) {
            StringBuilder stringBuilder;
            char c = ((String)object).charAt(n3);
            int n4 = n3 + 1;
            n3 = 0;
            if (n4 < ((String)object).length()) {
                n3 = Character.toLowerCase(((String)object).charAt(n4));
            }
            if (c == '/') {
                object2 = object2 == null ? new File((String)object3) : new File((File)object2, (String)object3);
                object3 = "";
                n3 = n4;
                continue;
            }
            if (c == '%') {
                if (n3 == 116) {
                    object3 = object2 = System.getProperty("java.io.tmpdir");
                    if (object2 == null) {
                        object3 = System.getProperty("user.home");
                    }
                    object2 = new File((String)object3);
                    n3 = n4 + 1;
                    object3 = "";
                    continue;
                }
                if (n3 == 104) {
                    object2 = new File(System.getProperty("user.home"));
                    n3 = n4 + 1;
                    object3 = "";
                    continue;
                }
                if (n3 == 103) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append((String)object3);
                    stringBuilder.append(n);
                    object3 = stringBuilder.toString();
                    bl = true;
                    n3 = n4 + 1;
                    continue;
                }
                if (n3 == 117) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append((String)object3);
                    stringBuilder.append(n2);
                    object3 = stringBuilder.toString();
                    bl2 = true;
                    n3 = n4 + 1;
                    continue;
                }
                if (n3 == 37) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append((String)object3);
                    stringBuilder.append("%");
                    object3 = stringBuilder.toString();
                    n3 = n4 + 1;
                    continue;
                }
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)object3);
            stringBuilder.append(c);
            object3 = stringBuilder.toString();
            n3 = n4;
        }
        object = object3;
        if (this.count > 1) {
            object = object3;
            if (!bl) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object3);
                ((StringBuilder)object).append(".");
                ((StringBuilder)object).append(n);
                object = ((StringBuilder)object).toString();
            }
        }
        object3 = object;
        if (n2 > 0) {
            object3 = object;
            if (!bl2) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)object);
                ((StringBuilder)object3).append(".");
                ((StringBuilder)object3).append(n2);
                object3 = ((StringBuilder)object3).toString();
            }
        }
        object = object2;
        if (((String)object3).length() > 0) {
            object = object2 == null ? new File((String)object3) : new File((File)object2, (String)object3);
        }
        return object;
    }

    private boolean isParentWritable(Path path) {
        Path path2;
        Path path3 = path2 = path.getParent();
        if (path2 == null) {
            path3 = path.toAbsolutePath().getParent();
        }
        boolean bl = path3 != null && Files.isWritable(path3);
        return bl;
    }

    private void open(File file, boolean bl) throws IOException {
        int n = 0;
        if (bl) {
            n = (int)file.length();
        }
        this.meter = new MeteredStream(new BufferedOutputStream(new FileOutputStream(file.toString(), bl)), n);
        this.setOutputStream(this.meter);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void openFiles() throws IOException {
        LogManager.getLogManager().checkPermission();
        if (this.count < 1) {
            var4_7 = new StringBuilder();
            var4_7.append("file count = ");
            var4_7.append(this.count);
            throw new IllegalArgumentException(var4_7.toString());
        }
        if (this.limit < 0) {
            this.limit = 0;
        }
        var1_1 = new InitializationErrorManager();
        this.setErrorManager(var1_1);
        var2_2 = -1;
        do {
            if ((var3_3 = var2_2 + 1) > 100) {
                var4_4 = new StringBuilder();
                var4_4.append("Couldn't get lock for ");
                var4_4.append(this.pattern);
                throw new IOException(var4_4.toString());
            }
            var4_4 = new StringBuilder();
            var4_4.append(this.generate(this.pattern, 0, var3_3).toString());
            var4_4.append(".lck");
            this.lockFileName = var4_4.toString();
            var5_8 = FileHandler.locks;
            // MONITORENTER : var5_8
            if (FileHandler.locks.contains(this.lockFileName)) {
                // MONITOREXIT : var5_8
                var2_2 = var3_3;
                continue;
            }
            var6_9 = Paths.get(this.lockFileName, new String[0]);
            var4_4 = null;
            var7_10 = -1;
            var2_2 = 0;
            while (var4_4 == null) {
                var8_11 = var7_10 + 1;
                if (var7_10 >= 1) break;
                try {
                    var4_4 = var9_12 = FileChannel.open(var6_9, new OpenOption[]{StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE});
                    var2_2 = 1;
                    ** GOTO lbl49
                }
                catch (FileAlreadyExistsException var9_13) {
                    if (!Files.isRegularFile(var6_9, new LinkOption[]{LinkOption.NOFOLLOW_LINKS}) || !(var10_16 = this.isParentWritable(var6_9))) break;
                    try {
                        var4_4 = var9_12 = FileChannel.open(var6_9, new OpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.APPEND});
                    }
                    catch (IOException var9_14) {
                        break;
                    }
                    catch (NoSuchFileException var9_15) {
                        var7_10 = var8_11;
                    }
lbl49: // 2 sources:
                    var7_10 = var8_11;
                }
            }
            if (var4_4 == null) {
                // MONITOREXIT : var5_8
                var2_2 = var3_3;
                continue;
            }
            this.lockFileChannel = var4_4;
            try {
                var4_4 = this.lockFileChannel.tryLock();
                var2_2 = var4_4 != null ? 1 : 0;
            }
            catch (OverlappingFileLockException var4_5) {
                var2_2 = 0;
            }
            catch (IOException var4_6) {
                // empty catch block
            }
            if (var2_2 != 0) {
                FileHandler.locks.add(this.lockFileName);
                // MONITOREXIT : var5_8
                this.files = new File[this.count];
                for (var2_2 = 0; var2_2 < this.count; ++var2_2) {
                    this.files[var2_2] = this.generate(this.pattern, var2_2, var3_3);
                }
                if (this.append) {
                    this.open(this.files[0], true);
                } else {
                    this.rotate();
                }
                var4_4 = var1_1.lastException;
                if (var4_4 == null) {
                    this.setErrorManager(new ErrorManager());
                    return;
                }
                if (var4_4 instanceof IOException != false) throw (IOException)var4_4;
                if (var4_4 instanceof SecurityException) {
                    throw (SecurityException)var4_4;
                }
                var9_12 = new StringBuilder();
                var9_12.append("Exception: ");
                var9_12.append(var4_4);
                throw new IOException(var9_12.toString());
            }
            this.lockFileChannel.close();
            // MONITOREXIT : var5_8
            var2_2 = var3_3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void rotate() {
        synchronized (this) {
            Level level = this.getLevel();
            this.setLevel(Level.OFF);
            super.close();
            for (int i = this.count - 2; i >= 0; --i) {
                File file = this.files[i];
                File file2 = this.files[i + 1];
                if (!file.exists()) continue;
                if (file2.exists()) {
                    file2.delete();
                }
                file.renameTo(file2);
            }
            try {
                this.open(this.files[0], false);
            }
            catch (IOException iOException) {
                this.reportError(null, iOException, 4);
            }
            this.setLevel(level);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws SecurityException {
        synchronized (this) {
            super.close();
            Object object = this.lockFileName;
            if (object == null) {
                return;
            }
            try {
                this.lockFileChannel.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            object = locks;
            synchronized (object) {
                locks.remove(this.lockFileName);
            }
            object = new File(this.lockFileName);
            ((File)object).delete();
            this.lockFileName = null;
            this.lockFileChannel = null;
            return;
        }
    }

    @Override
    public void publish(LogRecord privilegedAction) {
        synchronized (this) {
            block5 : {
                boolean bl = this.isLoggable((LogRecord)((Object)privilegedAction));
                if (bl) break block5;
                return;
            }
            super.publish((LogRecord)((Object)privilegedAction));
            this.flush();
            if (this.limit > 0 && this.meter.written >= this.limit) {
                privilegedAction = new PrivilegedAction<Object>(){

                    @Override
                    public Object run() {
                        FileHandler.this.rotate();
                        return null;
                    }
                };
                AccessController.doPrivileged(privilegedAction);
            }
            return;
        }
    }

    private static class InitializationErrorManager
    extends ErrorManager {
        Exception lastException;

        private InitializationErrorManager() {
        }

        @Override
        public void error(String string, Exception exception, int n) {
            this.lastException = exception;
        }
    }

    private class MeteredStream
    extends OutputStream {
        final OutputStream out;
        int written;

        MeteredStream(OutputStream outputStream, int n) {
            this.out = outputStream;
            this.written = n;
        }

        @Override
        public void close() throws IOException {
            this.out.close();
        }

        @Override
        public void flush() throws IOException {
            this.out.flush();
        }

        @Override
        public void write(int n) throws IOException {
            this.out.write(n);
            ++this.written;
        }

        @Override
        public void write(byte[] arrby) throws IOException {
            this.out.write(arrby);
            this.written += arrby.length;
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            this.out.write(arrby, n, n2);
            this.written += n2;
        }
    }

}

