/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.SSLClientSessionCache;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSession;

public final class FileClientSessionCache {
    public static final int MAX_SIZE = 12;
    static final Map<File, Impl> caches;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(FileClientSessionCache.class.getName());
        caches = new HashMap<File, Impl>();
    }

    private FileClientSessionCache() {
    }

    static void reset() {
        synchronized (FileClientSessionCache.class) {
            caches.clear();
            return;
        }
    }

    @UnsupportedAppUsage
    public static SSLClientSessionCache usingDirectory(File file) throws IOException {
        synchronized (FileClientSessionCache.class) {
            Impl impl;
            block5 : {
                Impl impl2;
                impl = impl2 = caches.get(file);
                if (impl2 != null) break block5;
                impl = new Impl(file);
                caches.put(file, impl);
            }
            return impl;
            finally {
            }
        }
    }

    static class CacheFile
    extends File {
        long lastModified = -1L;
        final String name;

        CacheFile(File file, String string) {
            super(file, string);
            this.name = string;
        }

        @Override
        public int compareTo(File file) {
            long l = this.lastModified() - file.lastModified();
            if (l == 0L) {
                return super.compareTo(file);
            }
            int n = l < 0L ? -1 : 1;
            return n;
        }

        @Override
        public long lastModified() {
            long l;
            long l2 = l = this.lastModified;
            if (l == -1L) {
                this.lastModified = l2 = super.lastModified();
            }
            return l2;
        }
    }

    static class Impl
    implements SSLClientSessionCache {
        Map<String, File> accessOrder;
        final File directory;
        String[] initialFiles;
        int size;

        Impl(File file) throws IOException {
            block7 : {
                block6 : {
                    block4 : {
                        Object object;
                        block5 : {
                            this.accessOrder = Impl.newAccessOrder();
                            boolean bl = file.exists();
                            if (bl && !file.isDirectory()) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(file);
                                stringBuilder.append(" exists but is not a directory.");
                                throw new IOException(stringBuilder.toString());
                            }
                            if (!bl) break block4;
                            this.initialFiles = file.list();
                            object = this.initialFiles;
                            if (object == null) break block5;
                            Arrays.sort((Object[])object);
                            this.size = this.initialFiles.length;
                            break block6;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append(file);
                        ((StringBuilder)object).append(" exists but cannot list contents.");
                        throw new IOException(((StringBuilder)object).toString());
                    }
                    if (!file.mkdirs()) break block7;
                    this.size = 0;
                }
                this.directory = file;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Creation of ");
            stringBuilder.append(file);
            stringBuilder.append(" directory failed.");
            throw new IOException(stringBuilder.toString());
        }

        private void delete(File serializable) {
            if (!((File)serializable).delete()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("FileClientSessionCache: Failed to delete ");
                stringBuilder.append(serializable);
                stringBuilder.append(".");
                serializable = new IOException(stringBuilder.toString());
                logger.log(Level.WARNING, ((Throwable)serializable).getMessage(), (Throwable)serializable);
            }
            --this.size;
        }

        private static String fileName(String string, int n) {
            if (string != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(".");
                stringBuilder.append(n);
                return stringBuilder.toString();
            }
            throw new NullPointerException("host == null");
        }

        private void indexFiles() {
            Object object = this.initialFiles;
            if (object != null) {
                this.initialFiles = null;
                Object object2 = new TreeSet();
                for (Object object3 : object) {
                    if (this.accessOrder.containsKey(object3)) continue;
                    object2.add(new CacheFile(this.directory, (String)object3));
                }
                if (!object2.isEmpty()) {
                    object = Impl.newAccessOrder();
                    object2 = object2.iterator();
                    while (object2.hasNext()) {
                        Object object3;
                        object3 = (CacheFile)object2.next();
                        object.put(((CacheFile)object3).name, object3);
                    }
                    object.putAll(this.accessOrder);
                    this.accessOrder = object;
                }
            }
        }

        static void logReadError(String string, File file, Throwable throwable) {
            Logger logger = logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FileClientSessionCache: Error reading session data for ");
            stringBuilder.append(string);
            stringBuilder.append(" from ");
            stringBuilder.append(file);
            stringBuilder.append(".");
            logger.log(level, stringBuilder.toString(), throwable);
        }

        static void logWriteError(String string, File file, Throwable throwable) {
            Logger logger = logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FileClientSessionCache: Error writing session data for ");
            stringBuilder.append(string);
            stringBuilder.append(" to ");
            stringBuilder.append(file);
            stringBuilder.append(".");
            logger.log(level, stringBuilder.toString(), throwable);
        }

        private void makeRoom() {
            if (this.size <= 12) {
                return;
            }
            this.indexFiles();
            int n = this.size - 12;
            Iterator<File> iterator = this.accessOrder.values().iterator();
            do {
                this.delete(iterator.next());
                iterator.remove();
            } while (--n > 0);
        }

        private static Map<String, File> newAccessOrder() {
            return new LinkedHashMap<String, File>(12, 0.75f, true);
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @UnsupportedAppUsage
        @Override
        public byte[] getSessionData(String string, int n) {
            Throwable throwable2222;
            // MONITORENTER : this
            byte[] arrby = Impl.fileName(string, n);
            Object object = this.accessOrder.get(arrby);
            Object object2 = object;
            if (object == null) {
                object2 = this.initialFiles;
                if (object2 == null) {
                    // MONITOREXIT : this
                    return null;
                }
                n = Arrays.binarySearch(this.initialFiles, arrby);
                if (n < 0) {
                    // MONITOREXIT : this
                    return null;
                }
                object2 = new File(this.directory, (String)arrby);
                this.accessOrder.put((String)arrby, (File)object2);
            }
            try {
                object = new FileInputStream((File)object2);
            }
            catch (FileNotFoundException fileNotFoundException) {
                Impl.logReadError(string, (File)object2, fileNotFoundException);
                // MONITOREXIT : this
                return null;
            }
            arrby = new byte[(int)object2.length()];
            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
            dataInputStream.readFully(arrby);
            try {
                ((FileInputStream)object).close();
                return arrby;
            }
            catch (Exception exception) {
                // empty catch block
            }
            return arrby;
            {
                catch (Throwable throwable2222) {
                }
                catch (IOException iOException) {}
                {
                    Impl.logReadError(string, (File)object2, iOException);
                }
                try {
                    ((FileInputStream)object).close();
                    return null;
                }
                catch (Exception exception) {
                    // empty catch block
                }
                return null;
            }
            ((FileInputStream)object).close();
            throw throwable2222;
            catch (Exception exception) {
                // empty catch block
            }
            throw throwable2222;
        }

        /*
         * Exception decompiling
         */
        @Override
        public void putSessionData(SSLSession var1_1, byte[] var2_10) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 26[UNCONDITIONALDOLOOP]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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
    }

}

