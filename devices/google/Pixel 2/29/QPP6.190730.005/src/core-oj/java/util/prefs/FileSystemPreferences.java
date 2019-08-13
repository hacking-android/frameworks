/*
 * Decompiled with CFR 0.145.
 */
package java.util.prefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Base64;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import java.util.prefs.XmlSupport;
import sun.util.logging.PlatformLogger;

public class FileSystemPreferences
extends AbstractPreferences {
    private static final int EACCES = 13;
    private static final int EAGAIN = 11;
    private static final String[] EMPTY_STRING_ARRAY;
    private static final int ERROR_CODE = 1;
    private static int INIT_SLEEP_TIME = 0;
    private static final int LOCK_HANDLE = 0;
    private static int MAX_ATTEMPTS = 0;
    private static final int USER_READ_WRITE = 384;
    private static final int USER_RWX = 448;
    private static final int USER_RWX_ALL_RX = 493;
    private static final int USER_RW_ALL_READ = 420;
    private static boolean isSystemRootModified;
    private static boolean isSystemRootWritable;
    private static boolean isUserRootModified;
    private static boolean isUserRootWritable;
    static File systemLockFile;
    static Preferences systemRoot;
    private static File systemRootDir;
    private static int systemRootLockHandle;
    private static File systemRootModFile;
    private static long systemRootModTime;
    static File userLockFile;
    static Preferences userRoot;
    private static File userRootDir;
    private static int userRootLockHandle;
    private static File userRootModFile;
    private static long userRootModTime;
    final List<Change> changeLog = new ArrayList<Change>();
    private final File dir;
    private final boolean isUserNode;
    private long lastSyncTime = 0L;
    NodeCreate nodeCreate = null;
    private Map<String, String> prefsCache = null;
    private final File prefsFile;
    private final File tmpFile;

    private static /* synthetic */ /* end resource */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        userRoot = null;
        userRootLockHandle = 0;
        systemRootLockHandle = 0;
        isUserRootModified = false;
        isSystemRootModified = false;
        Runtime.getRuntime().addShutdownHook(new Thread(){

            @Override
            public void run() {
                FileSystemPreferences.syncWorld();
            }
        });
        EMPTY_STRING_ARRAY = new String[0];
        INIT_SLEEP_TIME = 50;
        MAX_ATTEMPTS = 5;
    }

    public FileSystemPreferences(String object, File file, boolean bl) {
        super(null, "");
        this.isUserNode = bl;
        this.dir = new File((String)object);
        this.prefsFile = new File(this.dir, "prefs.xml");
        this.tmpFile = new File(this.dir, "prefs.tmp");
        this.newNode = this.dir.exists() ^ true;
        if (this.newNode) {
            this.prefsCache = new TreeMap<String, String>();
            this.nodeCreate = new NodeCreate();
            this.changeLog.add(this.nodeCreate);
        }
        if (bl) {
            userLockFile = file;
            File file2 = file.getParentFile();
            object = new StringBuilder();
            ((StringBuilder)object).append(file.getName());
            ((StringBuilder)object).append(".rootmod");
            userRootModFile = new File(file2, ((StringBuilder)object).toString());
        } else {
            systemLockFile = file;
            object = file.getParentFile();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(file.getName());
            stringBuilder.append(".rootmod");
            systemRootModFile = new File((File)object, stringBuilder.toString());
        }
    }

    private FileSystemPreferences(FileSystemPreferences fileSystemPreferences, String string) {
        super(fileSystemPreferences, string);
        this.isUserNode = fileSystemPreferences.isUserNode;
        this.dir = new File(fileSystemPreferences.dir, FileSystemPreferences.dirName(string));
        this.prefsFile = new File(this.dir, "prefs.xml");
        this.tmpFile = new File(this.dir, "prefs.tmp");
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            @Override
            public Void run() {
                FileSystemPreferences fileSystemPreferences = FileSystemPreferences.this;
                fileSystemPreferences.newNode = fileSystemPreferences.dir.exists() ^ true;
                return null;
            }
        });
        if (this.newNode) {
            this.prefsCache = new TreeMap<String, String>();
            this.nodeCreate = new NodeCreate();
            this.changeLog.add(this.nodeCreate);
        }
    }

    private FileSystemPreferences(boolean bl) {
        super(null, "");
        this.isUserNode = bl;
        File file = bl ? userRootDir : systemRootDir;
        this.dir = file;
        this.prefsFile = new File(this.dir, "prefs.xml");
        this.tmpFile = new File(this.dir, "prefs.tmp");
    }

    private static byte[] byteArray(String string) {
        int n = string.length();
        byte[] arrby = new byte[n * 2];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            int n3 = n2 + 1;
            arrby[n2] = (byte)(c >> 8);
            n2 = n3 + 1;
            arrby[n3] = (byte)c;
        }
        return arrby;
    }

    private void checkLockFile0ErrorCode(int n) throws SecurityException {
        String string = "System prefs.";
        if (n == 13) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not lock ");
            if (this.isUserNode()) {
                string = "User prefs.";
            }
            stringBuilder.append(string);
            stringBuilder.append(" Lock file access denied.");
            throw new SecurityException(stringBuilder.toString());
        }
        if (n != 11) {
            PlatformLogger platformLogger = FileSystemPreferences.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not lock ");
            if (this.isUserNode()) {
                string = "User prefs. ";
            }
            stringBuilder.append(string);
            stringBuilder.append(" Unix error code ");
            stringBuilder.append(n);
            stringBuilder.append(".");
            platformLogger.warning(stringBuilder.toString());
        }
    }

    private static native int chmod(String var0, int var1);

    private static String dirName(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (FileSystemPreferences.isDirChar(string.charAt(i))) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("_");
            stringBuilder.append(Base64.byteArrayToAltBase64(FileSystemPreferences.byteArray(string)));
            return stringBuilder.toString();
        }
        return string;
    }

    private static PlatformLogger getLogger() {
        return PlatformLogger.getLogger("java.util.prefs");
    }

    static Preferences getSystemRoot() {
        synchronized (FileSystemPreferences.class) {
            Preferences preferences;
            if (systemRoot == null) {
                FileSystemPreferences.setupSystemRoot();
                systemRoot = preferences = new FileSystemPreferences(false);
            }
            preferences = systemRoot;
            return preferences;
        }
    }

    static Preferences getUserRoot() {
        synchronized (FileSystemPreferences.class) {
            Preferences preferences;
            if (userRoot == null) {
                FileSystemPreferences.setupUserRoot();
                userRoot = preferences = new FileSystemPreferences(true);
            }
            preferences = userRoot;
            return preferences;
        }
    }

    private void initCacheIfNecessary() {
        if (this.prefsCache != null) {
            return;
        }
        try {
            this.loadCache();
        }
        catch (Exception exception) {
            this.prefsCache = new TreeMap<String, String>();
        }
    }

    private static boolean isDirChar(char c) {
        boolean bl = c > '\u001f' && c < '' && c != '/' && c != '.' && c != '_';
        return bl;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void loadCache() throws BackingStoreException {
        TreeMap<K, V> treeMap;
        Exception exception22222222;
        Object object;
        block10 : {
            long l;
            block9 : {
                long l2;
                treeMap = new TreeMap<K, V>();
                l = 0L;
                l = l2 = this.prefsFile.lastModified();
                l = l2;
                object = new FileInputStream(this.prefsFile);
                XmlSupport.importMap((InputStream)object, (Map<String, String>)treeMap);
                l = l2;
                FileSystemPreferences.$closeResource(null, (AutoCloseable)object);
                l = l2;
                catch (Throwable throwable) {
                    try {
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        l = l2;
                        try {
                            FileSystemPreferences.$closeResource(throwable, (AutoCloseable)object);
                            l = l2;
                            throw throwable2;
                        }
                        catch (Exception exception22222222) {
                            if (exception22222222 instanceof InvalidPreferencesFormatException) {
                                treeMap = FileSystemPreferences.getLogger();
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Invalid preferences format in ");
                                stringBuilder.append(this.prefsFile.getPath());
                                ((PlatformLogger)((Object)treeMap)).warning(stringBuilder.toString());
                                treeMap = this.prefsFile;
                                ((File)((Object)treeMap)).renameTo(new File(((File)((Object)treeMap)).getParentFile(), "IncorrectFormatPrefs.xml"));
                                treeMap = new TreeMap<K, V>();
                                break block9;
                            }
                            if (!(exception22222222 instanceof FileNotFoundException)) break block10;
                            object = FileSystemPreferences.getLogger();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Prefs file removed in background ");
                            stringBuilder.append(this.prefsFile.getPath());
                            ((PlatformLogger)object).warning(stringBuilder.toString());
                        }
                    }
                }
            }
            this.prefsCache = treeMap;
            this.lastSyncTime = l;
            return;
        }
        object = FileSystemPreferences.getLogger();
        treeMap = new StringBuilder();
        ((StringBuilder)((Object)treeMap)).append("Exception while reading cache: ");
        ((StringBuilder)((Object)treeMap)).append(exception22222222.getMessage());
        ((PlatformLogger)object).warning(((StringBuilder)((Object)treeMap)).toString());
        throw new BackingStoreException(exception22222222);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean lockFile(boolean bl) throws SecurityException {
        boolean bl2 = this.isUserNode();
        int n = 0;
        File file = bl2 ? userLockFile : systemLockFile;
        long l = INIT_SLEEP_TIME;
        int n2 = 0;
        do {
            if (n2 >= MAX_ATTEMPTS) {
                this.checkLockFile0ErrorCode(n);
                return false;
            }
            int n3 = bl2 ? 384 : 420;
            try {
                int[] arrn = FileSystemPreferences.lockFile0(file.getCanonicalPath(), n3, bl);
                n = arrn[1];
                if (arrn[0] != 0) {
                    if (bl2) {
                        userRootLockHandle = arrn[0];
                        return true;
                    }
                    systemRootLockHandle = arrn[0];
                    return true;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
            try {
                Thread.sleep(l);
                l *= 2L;
                ++n2;
            }
            catch (InterruptedException interruptedException) {
                this.checkLockFile0ErrorCode(n);
                return false;
            }
        } while (true);
    }

    private static native int[] lockFile0(String var0, int var1, boolean var2);

    private static String nodeName(String arrby) {
        if (arrby.charAt(0) != '_') {
            return arrby;
        }
        arrby = Base64.altBase64ToByteArray(arrby.substring(1));
        StringBuffer stringBuffer = new StringBuffer(arrby.length / 2);
        int n = 0;
        while (n < arrby.length) {
            int n2 = n + 1;
            stringBuffer.append((char)((arrby[n] & 255) << 8 | arrby[n2] & 255));
            n = n2 + 1;
        }
        return stringBuffer.toString();
    }

    private void replayChanges() {
        int n = this.changeLog.size();
        for (int i = 0; i < n; ++i) {
            this.changeLog.get(i).replay();
        }
    }

    private static void setupSystemRoot() {
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            @Override
            public Void run() {
                block10 : {
                    systemRootDir = new File(System.getProperty("java.util.prefs.systemRoot", "/etc/.java"), ".systemPrefs");
                    if (!systemRootDir.exists()) {
                        systemRootDir = new File(System.getProperty("java.home"), ".systemPrefs");
                        if (!systemRootDir.exists()) {
                            if (systemRootDir.mkdirs()) {
                                FileSystemPreferences.getLogger().info("Created system preferences directory in java.home.");
                                try {
                                    FileSystemPreferences.chmod(systemRootDir.getCanonicalPath(), 493);
                                }
                                catch (IOException iOException) {}
                            } else {
                                FileSystemPreferences.getLogger().warning("Could not create system preferences directory. System preferences are unusable.");
                            }
                        }
                    }
                    isSystemRootWritable = systemRootDir.canWrite();
                    systemLockFile = new File(systemRootDir, ".system.lock");
                    systemRootModFile = new File(systemRootDir, ".systemRootModFile");
                    if (!systemRootModFile.exists() && isSystemRootWritable) {
                        systemRootModFile.createNewFile();
                        int n = FileSystemPreferences.chmod(systemRootModFile.getCanonicalPath(), 420);
                        if (n == 0) break block10;
                        try {
                            PlatformLogger platformLogger = FileSystemPreferences.getLogger();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Chmod failed on ");
                            stringBuilder.append(systemRootModFile.getCanonicalPath());
                            stringBuilder.append(" Unix error code ");
                            stringBuilder.append(n);
                            platformLogger.warning(stringBuilder.toString());
                        }
                        catch (IOException iOException) {
                            FileSystemPreferences.getLogger().warning(iOException.toString());
                        }
                    }
                }
                systemRootModTime = systemRootModFile.lastModified();
                return null;
            }
        });
    }

    private static void setupUserRoot() {
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            @Override
            public Void run() {
                block9 : {
                    userRootDir = new File(System.getProperty("java.util.prefs.userRoot", System.getProperty("user.home")), ".java/.userPrefs");
                    if (!userRootDir.exists()) {
                        if (userRootDir.mkdirs()) {
                            try {
                                FileSystemPreferences.chmod(userRootDir.getCanonicalPath(), 448);
                            }
                            catch (IOException iOException) {
                                FileSystemPreferences.getLogger().warning("Could not change permissions on userRoot directory. ");
                            }
                            FileSystemPreferences.getLogger().info("Created user preferences directory.");
                        } else {
                            FileSystemPreferences.getLogger().warning("Couldn't create user preferences directory. User preferences are unusable.");
                        }
                    }
                    isUserRootWritable = userRootDir.canWrite();
                    CharSequence charSequence = System.getProperty("user.name");
                    File file = userRootDir;
                    Object object = new StringBuilder();
                    ((StringBuilder)object).append(".user.lock.");
                    ((StringBuilder)object).append((String)charSequence);
                    userLockFile = new File(file, ((StringBuilder)object).toString());
                    file = userRootDir;
                    object = new StringBuilder();
                    ((StringBuilder)object).append(".userRootModFile.");
                    ((StringBuilder)object).append((String)charSequence);
                    userRootModFile = new File(file, ((StringBuilder)object).toString());
                    if (!userRootModFile.exists()) {
                        userRootModFile.createNewFile();
                        int n = FileSystemPreferences.chmod(userRootModFile.getCanonicalPath(), 384);
                        if (n == 0) break block9;
                        try {
                            object = FileSystemPreferences.getLogger();
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("Problem creating userRoot mod file. Chmod failed on ");
                            ((StringBuilder)charSequence).append(userRootModFile.getCanonicalPath());
                            ((StringBuilder)charSequence).append(" Unix error code ");
                            ((StringBuilder)charSequence).append(n);
                            ((PlatformLogger)object).warning(((StringBuilder)charSequence).toString());
                        }
                        catch (IOException iOException) {
                            FileSystemPreferences.getLogger().warning(iOException.toString());
                        }
                    }
                }
                userRootModTime = userRootModFile.lastModified();
                return null;
            }
        });
    }

    private void syncSpiPrivileged() throws BackingStoreException {
        if (!this.isRemoved()) {
            long l;
            if (this.prefsCache == null) {
                return;
            }
            if (this.isUserNode() ? isUserRootModified : isSystemRootModified) {
                l = this.prefsFile.lastModified();
                if (l != this.lastSyncTime) {
                    this.loadCache();
                    this.replayChanges();
                    this.lastSyncTime = l;
                }
            } else if (this.lastSyncTime != 0L && !this.dir.exists()) {
                this.prefsCache = new TreeMap<String, String>();
                this.replayChanges();
            }
            if (!this.changeLog.isEmpty()) {
                this.writeBackCache();
                l = this.prefsFile.lastModified();
                if (this.lastSyncTime <= l) {
                    this.lastSyncTime = 1000L + l;
                    this.prefsFile.setLastModified(this.lastSyncTime);
                }
                this.changeLog.clear();
            }
            return;
        }
        throw new IllegalStateException("Node has been removed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void syncWorld() {
        // MONITORENTER : java.util.prefs.FileSystemPreferences.class
        Object object = userRoot;
        Object object2 = systemRoot;
        // MONITOREXIT : java.util.prefs.FileSystemPreferences.class
        if (object != null) {
            try {
                ((Preferences)object).flush();
            }
            catch (BackingStoreException backingStoreException) {
                object = FileSystemPreferences.getLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't flush user prefs: ");
                stringBuilder.append(backingStoreException);
                ((PlatformLogger)object).warning(stringBuilder.toString());
            }
        }
        if (object2 == null) return;
        try {
            ((Preferences)object2).flush();
            return;
        }
        catch (BackingStoreException backingStoreException) {
            PlatformLogger platformLogger = FileSystemPreferences.getLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Couldn't flush system prefs: ");
            ((StringBuilder)object2).append(backingStoreException);
            platformLogger.warning(((StringBuilder)object2).toString());
            return;
        }
    }

    private void unlockFile() {
        boolean bl = this.isUserNode();
        Object object = bl ? userLockFile : systemLockFile;
        int n = bl ? userRootLockHandle : systemRootLockHandle;
        object = "user";
        if (n == 0) {
            PlatformLogger platformLogger = FileSystemPreferences.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unlock: zero lockHandle for ");
            if (!bl) {
                object = "system";
            }
            stringBuilder.append((String)object);
            stringBuilder.append(" preferences.)");
            platformLogger.warning(stringBuilder.toString());
            return;
        }
        if ((n = FileSystemPreferences.unlockFile0(n)) != 0) {
            PlatformLogger platformLogger = FileSystemPreferences.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not drop file-lock on ");
            if (!this.isUserNode()) {
                object = "system";
            }
            stringBuilder.append((String)object);
            stringBuilder.append(" preferences. Unix error code ");
            stringBuilder.append(n);
            stringBuilder.append(".");
            platformLogger.warning(stringBuilder.toString());
            if (n == 13) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Could not unlock");
                object = this.isUserNode() ? "User prefs." : "System prefs.";
                stringBuilder.append((String)object);
                stringBuilder.append(" Lock file access denied.");
                throw new SecurityException(stringBuilder.toString());
            }
        }
        if (this.isUserNode()) {
            userRootLockHandle = 0;
        } else {
            systemRootLockHandle = 0;
        }
    }

    private static native int unlockFile0(int var0);

    private void writeBackCache() throws BackingStoreException {
        try {
            PrivilegedExceptionAction<Void> privilegedExceptionAction = new PrivilegedExceptionAction<Void>(){

                /*
                 * Exception decompiling
                 */
                @Override
                public Void run() throws BackingStoreException {
                    // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                    // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
                    // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                    // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
                    // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
                    // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
                    // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
                    // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
                    // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
                    // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
                    // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                    // org.benf.cfr.reader.Main.main(Main.java:48)
                    throw new IllegalStateException("Decompilation failed");
                }
            };
            AccessController.doPrivileged(privilegedExceptionAction);
            return;
        }
        catch (PrivilegedActionException privilegedActionException) {
            throw (BackingStoreException)privilegedActionException.getException();
        }
    }

    @Override
    protected AbstractPreferences childSpi(String string) {
        return new FileSystemPreferences(this, string);
    }

    @Override
    protected String[] childrenNamesSpi() {
        return AccessController.doPrivileged(new PrivilegedAction<String[]>(){

            @Override
            public String[] run() {
                ArrayList<String> arrayList = new ArrayList<String>();
                File[] arrfile = FileSystemPreferences.this.dir.listFiles();
                if (arrfile != null) {
                    for (int i = 0; i < arrfile.length; ++i) {
                        if (!arrfile[i].isDirectory()) continue;
                        arrayList.add(FileSystemPreferences.nodeName(arrfile[i].getName()));
                    }
                }
                return arrayList.toArray(EMPTY_STRING_ARRAY);
            }
        });
    }

    @Override
    public void flush() throws BackingStoreException {
        if (this.isRemoved()) {
            return;
        }
        this.sync();
    }

    @Override
    protected void flushSpi() throws BackingStoreException {
    }

    @Override
    protected String getSpi(String string) {
        this.initCacheIfNecessary();
        return this.prefsCache.get(string);
    }

    @Override
    public boolean isUserNode() {
        return this.isUserNode;
    }

    @Override
    protected String[] keysSpi() {
        this.initCacheIfNecessary();
        return this.prefsCache.keySet().toArray(new String[this.prefsCache.size()]);
    }

    @Override
    protected void putSpi(String string, String string2) {
        this.initCacheIfNecessary();
        this.changeLog.add(new Put(string, string2));
        this.prefsCache.put(string, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void removeNode() throws BackingStoreException {
        File file = this.isUserNode() ? userLockFile : systemLockFile;
        synchronized (file) {
            boolean bl = this.lockFile(false);
            if (!bl) {
                BackingStoreException backingStoreException = new BackingStoreException("Couldn't get file lock.");
                throw backingStoreException;
            }
            try {
                super.removeNode();
                return;
            }
            finally {
                this.unlockFile();
            }
        }
    }

    @Override
    protected void removeNodeSpi() throws BackingStoreException {
        try {
            PrivilegedExceptionAction<Void> privilegedExceptionAction = new PrivilegedExceptionAction<Void>(){

                @Override
                public Void run() throws BackingStoreException {
                    if (FileSystemPreferences.this.changeLog.contains(FileSystemPreferences.this.nodeCreate)) {
                        FileSystemPreferences.this.changeLog.remove(FileSystemPreferences.this.nodeCreate);
                        FileSystemPreferences.this.nodeCreate = null;
                        return null;
                    }
                    if (!FileSystemPreferences.this.dir.exists()) {
                        return null;
                    }
                    FileSystemPreferences.this.prefsFile.delete();
                    FileSystemPreferences.this.tmpFile.delete();
                    Object object = FileSystemPreferences.this.dir.listFiles();
                    if (((File[])object).length != 0) {
                        PlatformLogger platformLogger = FileSystemPreferences.getLogger();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Found extraneous files when removing node: ");
                        stringBuilder.append(Arrays.asList(object));
                        platformLogger.warning(stringBuilder.toString());
                        for (int i = 0; i < ((Object)object).length; ++i) {
                            ((File)object[i]).delete();
                        }
                    }
                    if (FileSystemPreferences.this.dir.delete()) {
                        return null;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Couldn't delete dir: ");
                    ((StringBuilder)object).append(FileSystemPreferences.this.dir);
                    throw new BackingStoreException(((StringBuilder)object).toString());
                }
            };
            AccessController.doPrivileged(privilegedExceptionAction);
            return;
        }
        catch (PrivilegedActionException privilegedActionException) {
            throw (BackingStoreException)privilegedActionException.getException();
        }
    }

    @Override
    protected void removeSpi(String string) {
        this.initCacheIfNecessary();
        this.changeLog.add(new Remove(string));
        this.prefsCache.remove(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void sync() throws BackingStoreException {
        synchronized (this) {
            boolean bl = this.isUserNode() ? false : isSystemRootWritable ^ true;
            File file = this.isUserNode() ? userLockFile : systemLockFile;
            synchronized (file) {
                if (!this.lockFile(bl)) {
                    BackingStoreException backingStoreException = new BackingStoreException("Couldn't get file lock.");
                    throw backingStoreException;
                }
                PrivilegedAction<Long> privilegedAction = new PrivilegedAction<Long>(){

                    @Override
                    public Long run() {
                        long l;
                        boolean bl = FileSystemPreferences.this.isUserNode();
                        boolean bl2 = true;
                        boolean bl3 = true;
                        if (bl) {
                            l = userRootModFile.lastModified();
                            if (userRootModTime != l) {
                                bl3 = false;
                            }
                            isUserRootModified = bl3;
                        } else {
                            l = systemRootModFile.lastModified();
                            bl3 = systemRootModTime == l ? bl2 : false;
                            isSystemRootModified = bl3;
                        }
                        return new Long(l);
                    }
                };
                final Long l = AccessController.doPrivileged(privilegedAction);
                super.sync();
                PrivilegedAction<Void> privilegedAction2 = new PrivilegedAction<Void>(){

                    @Override
                    public Void run() {
                        if (FileSystemPreferences.this.isUserNode()) {
                            userRootModTime = l + 1000L;
                            userRootModFile.setLastModified(userRootModTime);
                        } else {
                            systemRootModTime = l + 1000L;
                            systemRootModFile.setLastModified(systemRootModTime);
                        }
                        return null;
                    }
                };
                AccessController.doPrivileged(privilegedAction2);
                return;
                finally {
                    this.unlockFile();
                }
            }
        }
    }

    @Override
    protected void syncSpi() throws BackingStoreException {
        this.syncSpiPrivileged();
    }

    private abstract class Change {
        private Change() {
        }

        abstract void replay();
    }

    private class NodeCreate
    extends Change {
        private NodeCreate() {
        }

        @Override
        void replay() {
        }
    }

    private class Put
    extends Change {
        String key;
        String value;

        Put(String string, String string2) {
            this.key = string;
            this.value = string2;
        }

        @Override
        void replay() {
            FileSystemPreferences.this.prefsCache.put(this.key, this.value);
        }
    }

    private class Remove
    extends Change {
        String key;

        Remove(String string) {
            this.key = string;
        }

        @Override
        void replay() {
            FileSystemPreferences.this.prefsCache.remove(this.key);
        }
    }

}

