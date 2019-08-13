/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.StructStat
 *  android.system.StructTimespec
 *  dalvik.system.BlockGuard
 *  libcore.io.IoUtils
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.QueuedWork;
import android.app._$$Lambda$SharedPreferencesImpl$EditorImpl$3CAjkhzA131V3V_sLfP2uy0FWZ0;
import android.content.SharedPreferences;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStat;
import android.system.StructTimespec;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.ExponentiallyBucketedHistogram;
import com.android.internal.util.XmlUtils;
import dalvik.system.BlockGuard;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CountDownLatch;
import libcore.io.IoUtils;

final class SharedPreferencesImpl
implements SharedPreferences {
    private static final Object CONTENT = new Object();
    private static final boolean DEBUG = false;
    private static final long MAX_FSYNC_DURATION_MILLIS = 256L;
    private static final String TAG = "SharedPreferencesImpl";
    private final File mBackupFile;
    @GuardedBy(value={"this"})
    private long mCurrentMemoryStateGeneration;
    @GuardedBy(value={"mWritingToDiskLock"})
    private long mDiskStateGeneration;
    @GuardedBy(value={"mLock"})
    private int mDiskWritesInFlight = 0;
    @UnsupportedAppUsage
    private final File mFile;
    @GuardedBy(value={"mLock"})
    private final WeakHashMap<SharedPreferences.OnSharedPreferenceChangeListener, Object> mListeners = new WeakHashMap();
    @GuardedBy(value={"mLock"})
    private boolean mLoaded = false;
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private Map<String, Object> mMap;
    private final int mMode;
    private int mNumSync = 0;
    @GuardedBy(value={"mLock"})
    private long mStatSize;
    @GuardedBy(value={"mLock"})
    private StructTimespec mStatTimestamp;
    @GuardedBy(value={"mWritingToDiskLock"})
    private final ExponentiallyBucketedHistogram mSyncTimes = new ExponentiallyBucketedHistogram(16);
    @GuardedBy(value={"mLock"})
    private Throwable mThrowable;
    private final Object mWritingToDiskLock = new Object();

    @UnsupportedAppUsage
    SharedPreferencesImpl(File file, int n) {
        this.mFile = file;
        this.mBackupFile = SharedPreferencesImpl.makeBackupFile(file);
        this.mMode = n;
        this.mLoaded = false;
        this.mMap = null;
        this.mThrowable = null;
        this.startLoadFromDisk();
    }

    static /* synthetic */ int access$308(SharedPreferencesImpl sharedPreferencesImpl) {
        int n = sharedPreferencesImpl.mDiskWritesInFlight;
        sharedPreferencesImpl.mDiskWritesInFlight = n + 1;
        return n;
    }

    static /* synthetic */ int access$310(SharedPreferencesImpl sharedPreferencesImpl) {
        int n = sharedPreferencesImpl.mDiskWritesInFlight;
        sharedPreferencesImpl.mDiskWritesInFlight = n - 1;
        return n;
    }

    static /* synthetic */ long access$608(SharedPreferencesImpl sharedPreferencesImpl) {
        long l = sharedPreferencesImpl.mCurrentMemoryStateGeneration;
        sharedPreferencesImpl.mCurrentMemoryStateGeneration = 1L + l;
        return l;
    }

    @GuardedBy(value={"mLock"})
    private void awaitLoadedLocked() {
        if (!this.mLoaded) {
            BlockGuard.getThreadPolicy().onReadFromDisk();
        }
        while (!this.mLoaded) {
            try {
                this.mLock.wait();
            }
            catch (InterruptedException interruptedException) {}
        }
        Throwable throwable = this.mThrowable;
        if (throwable == null) {
            return;
        }
        throw new IllegalStateException(throwable);
    }

    private static FileOutputStream createFileOutputStream(File object) {
        StringBuilder stringBuilder = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream((File)object);
            object = fileOutputStream;
        }
        catch (FileNotFoundException fileNotFoundException) {
            Object object2 = ((File)object).getParentFile();
            if (!((File)object2).mkdir()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't create directory for SharedPreferences file ");
                stringBuilder.append(object);
                Log.e(TAG, stringBuilder.toString());
                return null;
            }
            FileUtils.setPermissions(((File)object2).getPath(), 505, -1, -1);
            try {
                object = object2 = new FileOutputStream((File)object);
            }
            catch (FileNotFoundException fileNotFoundException2) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Couldn't create SharedPreferences file ");
                ((StringBuilder)object2).append(object);
                Log.e(TAG, ((StringBuilder)object2).toString(), fileNotFoundException2);
                object = stringBuilder;
            }
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void enqueueDiskWrite(MemoryCommitResult object, Runnable runnable) {
        boolean bl = false;
        boolean bl2 = runnable == null;
        runnable = new Runnable((MemoryCommitResult)object, bl2, runnable){
            final /* synthetic */ boolean val$isFromSyncCommit;
            final /* synthetic */ MemoryCommitResult val$mcr;
            final /* synthetic */ Runnable val$postWriteRunnable;
            {
                this.val$mcr = memoryCommitResult;
                this.val$isFromSyncCommit = bl;
                this.val$postWriteRunnable = runnable;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object = SharedPreferencesImpl.this.mWritingToDiskLock;
                synchronized (object) {
                    SharedPreferencesImpl.this.writeToFile(this.val$mcr, this.val$isFromSyncCommit);
                }
                Object object2 = SharedPreferencesImpl.this.mLock;
                synchronized (object2) {
                    SharedPreferencesImpl.access$310(SharedPreferencesImpl.this);
                }
                object2 = this.val$postWriteRunnable;
                if (object2 != null) {
                    object2.run();
                }
            }
        };
        if (bl2) {
            object = this.mLock;
            // MONITORENTER : object
            boolean bl3 = this.mDiskWritesInFlight == 1;
            // MONITOREXIT : object
            if (bl3) {
                runnable.run();
                return;
            }
        }
        if (!bl2) {
            bl = true;
        }
        QueuedWork.queue(runnable, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean hasFileChangedUnexpectedly() {
        Object object;
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mDiskWritesInFlight > 0) {
                return false;
            }
        }
        boolean bl = true;
        try {
            BlockGuard.getThreadPolicy().onReadFromDisk();
            object2 = Os.stat((String)this.mFile.getPath());
            object = this.mLock;
        }
        catch (ErrnoException errnoException) {
            return true;
        }
        synchronized (object) {
            boolean bl2 = bl;
            if (!((StructStat)object2).st_mtim.equals((Object)this.mStatTimestamp)) return bl2;
            if (this.mStatSize == ((StructStat)object2).st_size) return false;
            return bl;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void loadFromDisk() {
        block26 : {
            block25 : {
                block24 : {
                    block23 : {
                        var1_1 /* !! */  = this.mLock;
                        // MONITORENTER : var1_1 /* !! */ 
                        if (this.mLoaded) {
                            // MONITOREXIT : var1_1 /* !! */ 
                            return;
                        }
                        if (this.mBackupFile.exists()) {
                            this.mFile.delete();
                            this.mBackupFile.renameTo(this.mFile);
                        }
                        // MONITOREXIT : var1_1 /* !! */ 
                        if (this.mFile.exists() && !this.mFile.canRead()) {
                            var1_1 /* !! */  = new StringBuilder();
                            var1_1 /* !! */ .append("Attempt to read preferences file ");
                            var1_1 /* !! */ .append(this.mFile);
                            var1_1 /* !! */ .append(" without permission");
                            Log.w("SharedPreferencesImpl", var1_1 /* !! */ .toString());
                        }
                        var2_6 = null;
                        var3_7 = null;
                        var1_1 /* !! */  = null;
                        var4_8 = null;
                        var5_9 = null;
                        var6_10 = null;
                        var7_12 = null;
                        var8_13 = var2_6;
                        var9_14 = var3_7;
                        var10_15 /* !! */  = Os.stat((String)this.mFile.getPath());
                        var8_13 = var2_6;
                        var6_10 = var10_15 /* !! */ ;
                        var9_14 = var3_7;
                        var5_9 = var10_15 /* !! */ ;
                        var11_16 = this.mFile.canRead();
                        var5_9 = var10_15 /* !! */ ;
                        if (!var11_16) break block23;
                        var6_10 = null;
                        var8_13 = null;
                        var1_1 /* !! */  = var8_13;
                        var12_17 /* !! */  = var6_10;
                        var1_1 /* !! */  = var8_13;
                        var12_17 /* !! */  = var6_10;
                        var1_1 /* !! */  = var8_13;
                        var12_17 /* !! */  = var6_10;
                        var9_14 = new FileInputStream(this.mFile);
                        var1_1 /* !! */  = var8_13;
                        var12_17 /* !! */  = var6_10;
                        var13_19 = new BufferedInputStream(var9_14, 16384);
                        var1_1 /* !! */  = var13_19;
                        var12_17 /* !! */  = var13_19;
                        var8_13 = XmlUtils.readMapXml(var13_19);
                        var1_1 /* !! */  = var8_13;
                        var8_13 = var1_1 /* !! */ ;
                        var6_10 = var10_15 /* !! */ ;
                        var9_14 = var1_1 /* !! */ ;
                        var5_9 = var10_15 /* !! */ ;
                        IoUtils.closeQuietly((AutoCloseable)var13_19);
lbl63: // 2 sources:
                        do {
                            var5_9 = var10_15 /* !! */ ;
                            break block23;
                            break;
                        } while (true);
                        {
                            catch (Throwable var12_18) {
                                ** GOTO lbl89
                            }
                            catch (Exception var6_11) {}
                            var1_1 /* !! */  = var12_17 /* !! */ ;
                            {
                                var1_1 /* !! */  = var12_17 /* !! */ ;
                                var8_13 = new StringBuilder();
                                var1_1 /* !! */  = var12_17 /* !! */ ;
                                var8_13.append("Cannot read ");
                                var1_1 /* !! */  = var12_17 /* !! */ ;
                                var8_13.append(this.mFile.getAbsolutePath());
                                var1_1 /* !! */  = var12_17 /* !! */ ;
                                Log.w("SharedPreferencesImpl", var8_13.toString(), var6_11);
                                var8_13 = var2_6;
                                var6_10 = var10_15 /* !! */ ;
                                var9_14 = var3_7;
                                var5_9 = var10_15 /* !! */ ;
                            }
                            try {
                                IoUtils.closeQuietly((AutoCloseable)var12_17 /* !! */ );
                                var1_1 /* !! */  = var4_8;
                                ** continue;
lbl89: // 1 sources:
                                var8_13 = var2_6;
                                var6_10 = var10_15 /* !! */ ;
                                var9_14 = var3_7;
                                var5_9 = var10_15 /* !! */ ;
                                IoUtils.closeQuietly((AutoCloseable)var1_1 /* !! */ );
                                var8_13 = var2_6;
                                var6_10 = var10_15 /* !! */ ;
                                var9_14 = var3_7;
                                var5_9 = var10_15 /* !! */ ;
                                throw var12_18;
                            }
                            catch (Throwable var1_2) {
                                var10_15 /* !! */  = var8_13;
                                break block24;
                            }
                            catch (ErrnoException var1_3) {
                                var1_1 /* !! */  = var9_14;
                            }
                        }
                    }
                    var6_10 = var5_9;
                    var10_15 /* !! */  = var1_1 /* !! */ ;
                    var1_1 /* !! */  = var7_12;
                }
                var8_13 = this.mLock;
                // MONITORENTER : var8_13
                this.mLoaded = true;
                this.mThrowable = var1_1 /* !! */ ;
                if (var1_1 /* !! */  != null) break block25;
                if (var10_15 /* !! */  == null) ** GOTO lbl120
                try {
                    this.mMap = var10_15 /* !! */ ;
                    this.mStatTimestamp = var6_10.st_mtim;
                    this.mStatSize = var6_10.st_size;
                    break block25;
lbl120: // 1 sources:
                    this.mMap = var1_1 /* !! */  = new HashMap();
                }
                catch (Throwable var1_4) {
                    try {
                        this.mThrowable = var1_4;
                        var1_1 /* !! */  = this.mLock;
                        break block26;
                    }
                    catch (Throwable var1_5) {
                        this.mLock.notifyAll();
                        throw var1_5;
                    }
                }
            }
            var1_1 /* !! */  = this.mLock;
        }
        var1_1 /* !! */ .notifyAll();
        // MONITOREXIT : var8_13
    }

    static File makeBackupFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getPath());
        stringBuilder.append(".bak");
        return new File(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void startLoadFromDisk() {
        Object object = this.mLock;
        synchronized (object) {
            this.mLoaded = false;
        }
        new Thread("SharedPreferencesImpl-load"){

            @Override
            public void run() {
                SharedPreferencesImpl.this.loadFromDisk();
            }
        }.start();
    }

    /*
     * Exception decompiling
     */
    @GuardedBy(value={"mWritingToDiskLock"})
    private void writeToFile(MemoryCommitResult var1_1, boolean var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 5[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean contains(String string2) {
        Object object = this.mLock;
        synchronized (object) {
            this.awaitLoadedLocked();
            return this.mMap.containsKey(string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SharedPreferences.Editor edit() {
        Object object = this.mLock;
        synchronized (object) {
            this.awaitLoadedLocked();
            return new EditorImpl();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Map<String, ?> getAll() {
        Object object = this.mLock;
        synchronized (object) {
            this.awaitLoadedLocked();
            return new HashMap<String, Object>(this.mMap);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean getBoolean(String object, boolean bl) {
        Object object2 = this.mLock;
        synchronized (object2) {
            this.awaitLoadedLocked();
            object = (Boolean)this.mMap.get(object);
            if (object == null) return bl;
            return (Boolean)object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public float getFloat(String object, float f) {
        Object object2 = this.mLock;
        synchronized (object2) {
            this.awaitLoadedLocked();
            object = (Float)this.mMap.get(object);
            if (object == null) return f;
            return ((Float)object).floatValue();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getInt(String object, int n) {
        Object object2 = this.mLock;
        synchronized (object2) {
            this.awaitLoadedLocked();
            object = (Integer)this.mMap.get(object);
            if (object == null) return n;
            return (Integer)object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long getLong(String object, long l) {
        Object object2 = this.mLock;
        synchronized (object2) {
            this.awaitLoadedLocked();
            object = (Long)this.mMap.get(object);
            if (object == null) return l;
            return (Long)object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String getString(String string2, String string3) {
        Object object = this.mLock;
        synchronized (object) {
            this.awaitLoadedLocked();
            string2 = (String)this.mMap.get(string2);
            if (string2 == null) return string3;
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Set<String> getStringSet(String set, Set<String> set2) {
        Object object = this.mLock;
        synchronized (object) {
            this.awaitLoadedLocked();
            set = (Set)this.mMap.get(set);
            if (set == null) return set2;
            return set;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        Object object = this.mLock;
        synchronized (object) {
            this.mListeners.put(onSharedPreferenceChangeListener, CONTENT);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void startReloadIfChangedUnexpectedly() {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.hasFileChangedUnexpectedly()) {
                return;
            }
            this.startLoadFromDisk();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        Object object = this.mLock;
        synchronized (object) {
            this.mListeners.remove(onSharedPreferenceChangeListener);
            return;
        }
    }

    public final class EditorImpl
    implements SharedPreferences.Editor {
        @GuardedBy(value={"mEditorLock"})
        private boolean mClear = false;
        private final Object mEditorLock = new Object();
        @GuardedBy(value={"mEditorLock"})
        private final Map<String, Object> mModified = new HashMap<String, Object>();

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private MemoryCommitResult commitToMemory() {
            ArrayList<String> arrayList = null;
            HashSet hashSet = null;
            Object object = SharedPreferencesImpl.this.mLock;
            synchronized (object) {
                Object object2;
                Object object3;
                if (SharedPreferencesImpl.this.mDiskWritesInFlight > 0) {
                    object2 = SharedPreferencesImpl.this;
                    object3 = new HashMap(SharedPreferencesImpl.this.mMap);
                    ((SharedPreferencesImpl)object2).mMap = object3;
                }
                object2 = SharedPreferencesImpl.this.mMap;
                SharedPreferencesImpl.access$308(SharedPreferencesImpl.this);
                boolean bl = SharedPreferencesImpl.this.mListeners.size() > 0;
                if (bl) {
                    arrayList = new ArrayList<String>();
                    hashSet = new HashSet(SharedPreferencesImpl.this.mListeners.keySet());
                }
                object3 = this.mEditorLock;
                synchronized (object3) {
                    boolean bl2 = false;
                    boolean bl3 = false;
                    if (this.mClear) {
                        bl2 = bl3;
                        if (!object2.isEmpty()) {
                            bl2 = true;
                            object2.clear();
                        }
                        this.mClear = false;
                    }
                    for (Object object4 : this.mModified.entrySet()) {
                        String string2 = object4.getKey();
                        if ((object4 = object4.getValue()) != this && object4 != null) {
                            Object v;
                            if (object2.containsKey(string2) && (v = object2.get(string2)) != null && v.equals(object4)) continue;
                            object2.put(string2, object4);
                        } else {
                            if (!object2.containsKey(string2)) continue;
                            object2.remove(string2);
                        }
                        bl2 = true;
                        if (!bl) continue;
                        arrayList.add(string2);
                    }
                    this.mModified.clear();
                    if (bl2) {
                        SharedPreferencesImpl.access$608(SharedPreferencesImpl.this);
                    }
                    long l = SharedPreferencesImpl.this.mCurrentMemoryStateGeneration;
                    return new MemoryCommitResult(l, arrayList, hashSet, (Map)object2);
                }
            }
        }

        private void notifyListeners(MemoryCommitResult memoryCommitResult) {
            if (memoryCommitResult.listeners != null && memoryCommitResult.keysModified != null && memoryCommitResult.keysModified.size() != 0) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    for (int i = memoryCommitResult.keysModified.size() - 1; i >= 0; --i) {
                        String string2 = memoryCommitResult.keysModified.get(i);
                        for (SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener : memoryCommitResult.listeners) {
                            if (onSharedPreferenceChangeListener == null) continue;
                            onSharedPreferenceChangeListener.onSharedPreferenceChanged(SharedPreferencesImpl.this, string2);
                        }
                    }
                } else {
                    ActivityThread.sMainThreadHandler.post(new _$$Lambda$SharedPreferencesImpl$EditorImpl$3CAjkhzA131V3V_sLfP2uy0FWZ0(this, memoryCommitResult));
                }
                return;
            }
        }

        @Override
        public void apply() {
            final long l = System.currentTimeMillis();
            final MemoryCommitResult memoryCommitResult = this.commitToMemory();
            final Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    try {
                        memoryCommitResult.writtenToDiskLatch.await();
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
            };
            QueuedWork.addFinisher(runnable);
            runnable = new Runnable(){

                @Override
                public void run() {
                    runnable.run();
                    QueuedWork.removeFinisher(runnable);
                }
            };
            SharedPreferencesImpl.this.enqueueDiskWrite(memoryCommitResult, runnable);
            this.notifyListeners(memoryCommitResult);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SharedPreferences.Editor clear() {
            Object object = this.mEditorLock;
            synchronized (object) {
                this.mClear = true;
                return this;
            }
        }

        @Override
        public boolean commit() {
            MemoryCommitResult memoryCommitResult = this.commitToMemory();
            SharedPreferencesImpl.this.enqueueDiskWrite(memoryCommitResult, null);
            try {
                memoryCommitResult.writtenToDiskLatch.await();
                this.notifyListeners(memoryCommitResult);
                return memoryCommitResult.writeToDiskResult;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            catch (InterruptedException interruptedException) {
                return false;
            }
        }

        public /* synthetic */ void lambda$notifyListeners$0$SharedPreferencesImpl$EditorImpl(MemoryCommitResult memoryCommitResult) {
            this.notifyListeners(memoryCommitResult);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SharedPreferences.Editor putBoolean(String string2, boolean bl) {
            Object object = this.mEditorLock;
            synchronized (object) {
                this.mModified.put(string2, bl);
                return this;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SharedPreferences.Editor putFloat(String string2, float f) {
            Object object = this.mEditorLock;
            synchronized (object) {
                this.mModified.put(string2, Float.valueOf(f));
                return this;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SharedPreferences.Editor putInt(String string2, int n) {
            Object object = this.mEditorLock;
            synchronized (object) {
                this.mModified.put(string2, n);
                return this;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SharedPreferences.Editor putLong(String string2, long l) {
            Object object = this.mEditorLock;
            synchronized (object) {
                this.mModified.put(string2, l);
                return this;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SharedPreferences.Editor putString(String string2, String string3) {
            Object object = this.mEditorLock;
            synchronized (object) {
                this.mModified.put(string2, string3);
                return this;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SharedPreferences.Editor putStringSet(String string2, Set<String> set) {
            Object object = this.mEditorLock;
            synchronized (object) {
                Map<String, Object> map = this.mModified;
                set = set == null ? null : new HashSet<String>(set);
                map.put(string2, set);
                return this;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public SharedPreferences.Editor remove(String string2) {
            Object object = this.mEditorLock;
            synchronized (object) {
                this.mModified.put(string2, this);
                return this;
            }
        }

    }

    private static class MemoryCommitResult {
        final List<String> keysModified;
        final Set<SharedPreferences.OnSharedPreferenceChangeListener> listeners;
        final Map<String, Object> mapToWriteToDisk;
        final long memoryStateGeneration;
        boolean wasWritten = false;
        @GuardedBy(value={"mWritingToDiskLock"})
        volatile boolean writeToDiskResult = false;
        final CountDownLatch writtenToDiskLatch = new CountDownLatch(1);

        private MemoryCommitResult(long l, List<String> list, Set<SharedPreferences.OnSharedPreferenceChangeListener> set, Map<String, Object> map) {
            this.memoryStateGeneration = l;
            this.keysModified = list;
            this.listeners = set;
            this.mapToWriteToDisk = map;
        }

        void setDiskWriteResult(boolean bl, boolean bl2) {
            this.wasWritten = bl;
            this.writeToDiskResult = bl2;
            this.writtenToDiskLatch.countDown();
        }
    }

}

