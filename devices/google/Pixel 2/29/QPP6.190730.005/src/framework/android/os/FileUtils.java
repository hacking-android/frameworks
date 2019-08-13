/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.-$
 *  android.os.-$$Lambda
 *  android.os.-$$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  libcore.io.IoUtils
 *  libcore.util.EmptyArray
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.-$;
import android.os.CancellationSignal;
import android.os.SystemClock;
import android.os._$$Lambda$FileUtils$0SBPRWOXcbR9EMG_p_55sUuxJ_0;
import android.os._$$Lambda$FileUtils$QtbHtI8Y1rifwydngi6coGK5l2A;
import android.os._$$Lambda$FileUtils$RlOy_0MlKMWkkCC1mk_jzWcLTKs;
import android.os._$$Lambda$FileUtils$TJeD9NeX5giO_5vlBrurGI_g4IY;
import android.os._$$Lambda$FileUtils$XQaJiyjsC2_MFNDbZFQcIhqPnNA;
import android.os._$$Lambda$FileUtils$e0JoE_HjVf9vMX679eNxZixyUZ0;
import android.os._$$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.webkit.MimeTypeMap;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.SizedInputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;

public final class FileUtils {
    private static final long COPY_CHECKPOINT_BYTES = 524288L;
    public static final int S_IRGRP = 32;
    public static final int S_IROTH = 4;
    public static final int S_IRUSR = 256;
    public static final int S_IRWXG = 56;
    public static final int S_IRWXO = 7;
    public static final int S_IRWXU = 448;
    public static final int S_IWGRP = 16;
    public static final int S_IWOTH = 2;
    public static final int S_IWUSR = 128;
    public static final int S_IXGRP = 8;
    public static final int S_IXOTH = 1;
    public static final int S_IXUSR = 64;
    private static final String TAG = "FileUtils";
    private static boolean sEnableCopyOptimizations = true;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
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

    @UnsupportedAppUsage
    private FileUtils() {
    }

    private static File buildFile(File file, String string2, String string3) {
        if (TextUtils.isEmpty(string3)) {
            return new File(file, string2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(".");
        stringBuilder.append(string3);
        return new File(file, stringBuilder.toString());
    }

    public static File buildNonUniqueFile(File file, String arrstring, String string2) {
        arrstring = FileUtils.splitFileName((String)arrstring, string2);
        return FileUtils.buildFile(file, arrstring[0], arrstring[1]);
    }

    public static File buildUniqueFile(File file, String string2) throws FileNotFoundException {
        String string3;
        int n = string2.lastIndexOf(46);
        if (n >= 0) {
            String string4 = string2.substring(0, n);
            string3 = string2.substring(n + 1);
            string2 = string4;
        } else {
            string3 = null;
        }
        return FileUtils.buildUniqueFileWithExtension(file, string2, string3);
    }

    public static File buildUniqueFile(File file, String arrstring, String string2) throws FileNotFoundException {
        arrstring = FileUtils.splitFileName((String)arrstring, string2);
        return FileUtils.buildUniqueFileWithExtension(file, arrstring[0], arrstring[1]);
    }

    private static File buildUniqueFileWithExtension(File file, String string2, String string3) throws FileNotFoundException {
        Serializable serializable = FileUtils.buildFile(file, string2, string3);
        int n = 0;
        while (((File)serializable).exists()) {
            int n2 = n + 1;
            if (n < 32) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append(string2);
                ((StringBuilder)serializable).append(" (");
                ((StringBuilder)serializable).append(n2);
                ((StringBuilder)serializable).append(")");
                serializable = FileUtils.buildFile(file, ((StringBuilder)serializable).toString(), string3);
                n = n2;
                continue;
            }
            throw new FileNotFoundException("Failed to create unique file");
        }
        return serializable;
    }

    public static String buildValidExtFilename(String string2) {
        if (!(TextUtils.isEmpty(string2) || ".".equals(string2) || "..".equals(string2))) {
            StringBuilder stringBuilder = new StringBuilder(string2.length());
            for (int i = 0; i < string2.length(); ++i) {
                char c = string2.charAt(i);
                if (FileUtils.isValidExtFilenameChar(c)) {
                    stringBuilder.append(c);
                    continue;
                }
                stringBuilder.append('_');
            }
            FileUtils.trimFilename(stringBuilder, 255);
            return stringBuilder.toString();
        }
        return "(invalid)";
    }

    public static String buildValidFatFilename(String string2) {
        if (!(TextUtils.isEmpty(string2) || ".".equals(string2) || "..".equals(string2))) {
            StringBuilder stringBuilder = new StringBuilder(string2.length());
            for (int i = 0; i < string2.length(); ++i) {
                char c = string2.charAt(i);
                if (FileUtils.isValidFatFilenameChar(c)) {
                    stringBuilder.append(c);
                    continue;
                }
                stringBuilder.append('_');
            }
            FileUtils.trimFilename(stringBuilder, 255);
            return stringBuilder.toString();
        }
        return "(invalid)";
    }

    /*
     * Exception decompiling
     */
    public static void bytesToFile(String var0, byte[] var1_3) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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
    @Deprecated
    @UnsupportedAppUsage
    public static long checksumCrc32(File object) throws FileNotFoundException, IOException {
        long l;
        CRC32 cRC32 = new CRC32();
        byte[] arrby = null;
        Object object2 = arrby;
        try {
            object2 = arrby;
            object2 = arrby;
            FileInputStream fileInputStream = new FileInputStream((File)object);
            object2 = arrby;
            CheckedInputStream checkedInputStream = new CheckedInputStream(fileInputStream, cRC32);
            object2 = object = checkedInputStream;
            arrby = new byte[128];
            do {
                object2 = object;
            } while (((FilterInputStream)object).read(arrby) >= 0);
            object2 = object;
            l = cRC32.getValue();
        }
        catch (Throwable throwable) {
            if (object2 == null) throw throwable;
            try {
                ((FilterInputStream)object2).close();
                throw throwable;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            throw throwable;
        }
        try {
            ((FilterInputStream)object).close();
            return l;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return l;
    }

    public static void closeQuietly(FileDescriptor fileDescriptor) {
        IoUtils.closeQuietly((FileDescriptor)fileDescriptor);
    }

    public static void closeQuietly(AutoCloseable autoCloseable) {
        IoUtils.closeQuietly((AutoCloseable)autoCloseable);
    }

    public static boolean contains(File file, File file2) {
        if (file != null && file2 != null) {
            return FileUtils.contains(file.getAbsolutePath(), file2.getAbsolutePath());
        }
        return false;
    }

    public static boolean contains(String string2, String string3) {
        if (string2.equals(string3)) {
            return true;
        }
        CharSequence charSequence = string2;
        if (!string2.endsWith("/")) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("/");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return string3.startsWith((String)charSequence);
    }

    public static boolean contains(Collection<File> object, File file) {
        object = object.iterator();
        while (object.hasNext()) {
            if (!FileUtils.contains((File)object.next(), file)) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(File[] arrfile, File file) {
        int n = arrfile.length;
        for (int i = 0; i < n; ++i) {
            if (!FileUtils.contains(arrfile[i], file)) continue;
            return true;
        }
        return false;
    }

    public static long copy(File file, File file2) throws IOException {
        return FileUtils.copy(file, file2, null, null, null);
    }

    /*
     * Exception decompiling
     */
    public static long copy(File var0, File var1_1, CancellationSignal var2_4, Executor var3_7, ProgressListener var4_8) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    public static long copy(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2) throws IOException {
        return FileUtils.copy(fileDescriptor, fileDescriptor2, null, null, null);
    }

    public static long copy(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, long l, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws IOException {
        if (sEnableCopyOptimizations) {
            try {
                StructStat structStat = Os.fstat((FileDescriptor)fileDescriptor);
                StructStat structStat2 = Os.fstat((FileDescriptor)fileDescriptor2);
                if (OsConstants.S_ISREG((int)structStat.st_mode) && OsConstants.S_ISREG((int)structStat2.st_mode)) {
                    return FileUtils.copyInternalSendfile(fileDescriptor, fileDescriptor2, l, cancellationSignal, executor, progressListener);
                }
                if (OsConstants.S_ISFIFO((int)structStat.st_mode) || OsConstants.S_ISFIFO((int)structStat2.st_mode)) {
                    l = FileUtils.copyInternalSplice(fileDescriptor, fileDescriptor2, l, cancellationSignal, executor, progressListener);
                    return l;
                }
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }
        return FileUtils.copyInternalUserspace(fileDescriptor, fileDescriptor2, l, cancellationSignal, executor, progressListener);
    }

    public static long copy(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws IOException {
        return FileUtils.copy(fileDescriptor, fileDescriptor2, Long.MAX_VALUE, cancellationSignal, executor, progressListener);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        return FileUtils.copy(inputStream, outputStream, null, null, null);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws IOException {
        if (sEnableCopyOptimizations && inputStream instanceof FileInputStream && outputStream instanceof FileOutputStream) {
            return FileUtils.copy(((FileInputStream)inputStream).getFD(), ((FileOutputStream)outputStream).getFD(), cancellationSignal, executor, progressListener);
        }
        return FileUtils.copyInternalUserspace(inputStream, outputStream, cancellationSignal, executor, progressListener);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean copyFile(File file, File file2) {
        try {
            FileUtils.copyFileOrThrow(file, file2);
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Deprecated
    public static void copyFileOrThrow(File object, File file) throws IOException {
        object = new FileInputStream((File)object);
        try {
            FileUtils.copyToFileOrThrow((InputStream)object, file);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                FileUtils.$closeResource(throwable, (AutoCloseable)object);
                throw throwable2;
            }
        }
        FileUtils.$closeResource(null, (AutoCloseable)object);
    }

    @VisibleForTesting
    public static long copyInternalSendfile(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, long l, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws ErrnoException {
        long l2;
        long l3 = 0L;
        long l4 = 0L;
        long l5 = l;
        l = l3;
        while ((l2 = Os.sendfile((FileDescriptor)fileDescriptor2, (FileDescriptor)fileDescriptor, null, (long)Math.min(l5, 524288L))) != 0L) {
            l3 = l4 + l2;
            long l6 = l + l2;
            l5 = l2 = l5 - l2;
            l4 = l3;
            l = l6;
            if (l6 < 524288L) continue;
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            if (executor != null && progressListener != null) {
                executor.execute(new _$$Lambda$FileUtils$QtbHtI8Y1rifwydngi6coGK5l2A(progressListener, l3));
            }
            l = 0L;
            l5 = l2;
            l4 = l3;
        }
        if (executor != null && progressListener != null) {
            executor.execute(new _$$Lambda$FileUtils$XQaJiyjsC2_MFNDbZFQcIhqPnNA(progressListener, l4));
        }
        return l4;
    }

    @VisibleForTesting
    public static long copyInternalSplice(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, long l, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws ErrnoException {
        long l2;
        long l3 = 0L;
        long l4 = 0L;
        long l5 = l;
        l = l3;
        while ((l2 = Os.splice((FileDescriptor)fileDescriptor, null, (FileDescriptor)fileDescriptor2, null, (long)Math.min(l5, 524288L), (int)(OsConstants.SPLICE_F_MOVE | OsConstants.SPLICE_F_MORE))) != 0L) {
            l3 = l4 + l2;
            long l6 = l + l2;
            l5 = l2 = l5 - l2;
            l4 = l3;
            l = l6;
            if (l6 < 524288L) continue;
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            if (executor != null && progressListener != null) {
                executor.execute(new _$$Lambda$FileUtils$RlOy_0MlKMWkkCC1mk_jzWcLTKs(progressListener, l3));
            }
            l = 0L;
            l5 = l2;
            l4 = l3;
        }
        if (executor != null && progressListener != null) {
            executor.execute(new _$$Lambda$FileUtils$e0JoE_HjVf9vMX679eNxZixyUZ0(progressListener, l4));
        }
        return l4;
    }

    @VisibleForTesting
    public static long copyInternalUserspace(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, long l, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws IOException {
        if (l != Long.MAX_VALUE) {
            return FileUtils.copyInternalUserspace(new SizedInputStream(new FileInputStream(fileDescriptor), l), new FileOutputStream(fileDescriptor2), cancellationSignal, executor, progressListener);
        }
        return FileUtils.copyInternalUserspace(new FileInputStream(fileDescriptor), new FileOutputStream(fileDescriptor2), cancellationSignal, executor, progressListener);
    }

    @Deprecated
    @VisibleForTesting
    public static long copyInternalUserspace(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, ProgressListener progressListener, CancellationSignal cancellationSignal, long l) throws IOException {
        return FileUtils.copyInternalUserspace(fileDescriptor, fileDescriptor2, l, cancellationSignal, (Executor)_$$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo.INSTANCE, progressListener);
    }

    @VisibleForTesting
    public static long copyInternalUserspace(InputStream inputStream, OutputStream outputStream, CancellationSignal cancellationSignal, Executor executor, ProgressListener progressListener) throws IOException {
        int n;
        long l = 0L;
        long l2 = 0L;
        byte[] arrby = new byte[8192];
        while ((n = inputStream.read(arrby)) != -1) {
            outputStream.write(arrby, 0, n);
            long l3 = l + (long)n;
            long l4 = l2 + (long)n;
            l = l3;
            l2 = l4;
            if (l4 < 524288L) continue;
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            if (executor != null && progressListener != null) {
                executor.execute(new _$$Lambda$FileUtils$TJeD9NeX5giO_5vlBrurGI_g4IY(progressListener, l3));
            }
            l2 = 0L;
            l = l3;
        }
        if (executor != null && progressListener != null) {
            executor.execute(new _$$Lambda$FileUtils$0SBPRWOXcbR9EMG_p_55sUuxJ_0(progressListener, l));
        }
        return l;
    }

    public static void copyPermissions(File file, File file2) throws IOException {
        try {
            file = Os.stat((String)file.getAbsolutePath());
            Os.chmod((String)file2.getAbsolutePath(), (int)((StructStat)file).st_mode);
            Os.chown((String)file2.getAbsolutePath(), (int)((StructStat)file).st_uid, (int)((StructStat)file).st_gid);
            return;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean copyToFile(InputStream inputStream, File file) {
        try {
            FileUtils.copyToFileOrThrow(inputStream, file);
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public static void copyToFileOrThrow(InputStream inputStream, File object) throws IOException {
        if (((File)object).exists()) {
            ((File)object).delete();
        }
        object = new FileOutputStream((File)object);
        FileUtils.copy(inputStream, (OutputStream)object);
        Os.fsync((FileDescriptor)((FileOutputStream)object).getFD());
        FileUtils.$closeResource(null, (AutoCloseable)object);
        return;
        {
            catch (ErrnoException errnoException) {
                try {
                    throw errnoException.rethrowAsIOException();
                }
                catch (Throwable throwable) {
                    try {
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        FileUtils.$closeResource(throwable, (AutoCloseable)object);
                        throw throwable2;
                    }
                }
            }
        }
    }

    public static File createDir(File file, String string2) {
        if (!FileUtils.createDir(file = new File(file, string2))) {
            file = null;
        }
        return file;
    }

    public static boolean createDir(File file) {
        if (file.exists()) {
            return file.isDirectory();
        }
        return file.mkdir();
    }

    @UnsupportedAppUsage
    public static boolean deleteContents(File arrfile) {
        arrfile = arrfile.listFiles();
        boolean bl = true;
        boolean bl2 = true;
        if (arrfile != null) {
            int n = arrfile.length;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                File file = arrfile[n2];
                bl = bl2;
                if (file.isDirectory()) {
                    bl = bl2 & FileUtils.deleteContents(file);
                }
                bl2 = bl;
                if (!file.delete()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to delete ");
                    stringBuilder.append(file);
                    Log.w(TAG, stringBuilder.toString());
                    bl2 = false;
                }
                ++n2;
            } while (true);
        }
        return bl;
    }

    public static boolean deleteContentsAndDir(File file) {
        if (FileUtils.deleteContents(file)) {
            return file.delete();
        }
        return false;
    }

    @UnsupportedAppUsage
    public static boolean deleteOlderFiles(File serializable, int n, long l) {
        if (n >= 0 && l >= 0L) {
            File[] arrfile = ((File)serializable).listFiles();
            if (arrfile == null) {
                return false;
            }
            Arrays.sort(arrfile, new Comparator<File>(){

                @Override
                public int compare(File file, File file2) {
                    return Long.compare(file2.lastModified(), file.lastModified());
                }
            });
            boolean bl = false;
            while (n < arrfile.length) {
                File file = arrfile[n];
                boolean bl2 = bl;
                if (System.currentTimeMillis() - file.lastModified() > l) {
                    bl2 = bl;
                    if (file.delete()) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Deleted old file ");
                        ((StringBuilder)serializable).append(file);
                        Log.d(TAG, ((StringBuilder)serializable).toString());
                        bl2 = true;
                    }
                }
                ++n;
                bl = bl2;
            }
            return bl;
        }
        throw new IllegalArgumentException("Constraints must be positive or 0");
    }

    public static byte[] digest(File object, String arrby) throws IOException, NoSuchAlgorithmException {
        object = new FileInputStream((File)object);
        try {
            arrby = FileUtils.digest((InputStream)object, (String)arrby);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                FileUtils.$closeResource(throwable, (AutoCloseable)object);
                throw throwable2;
            }
        }
        FileUtils.$closeResource(null, (AutoCloseable)object);
        return arrby;
    }

    public static byte[] digest(FileDescriptor fileDescriptor, String string2) throws IOException, NoSuchAlgorithmException {
        return FileUtils.digestInternalUserspace(new FileInputStream(fileDescriptor), string2);
    }

    public static byte[] digest(InputStream inputStream, String string2) throws IOException, NoSuchAlgorithmException {
        return FileUtils.digestInternalUserspace(inputStream, string2);
    }

    private static byte[] digestInternalUserspace(InputStream inputStream, String object) throws IOException, NoSuchAlgorithmException {
        object = MessageDigest.getInstance((String)object);
        inputStream = new DigestInputStream(inputStream, (MessageDigest)object);
        try {
            int n;
            byte[] arrby = new byte[8192];
            while ((n = ((FilterInputStream)inputStream).read(arrby)) != -1) {
            }
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                FileUtils.$closeResource(throwable, inputStream);
                throw throwable2;
            }
        }
        FileUtils.$closeResource(null, inputStream);
        return ((MessageDigest)object).digest();
    }

    @Deprecated
    public static int getUid(String string2) {
        try {
            int n = Os.stat((String)string2).st_uid;
            return n;
        }
        catch (ErrnoException errnoException) {
            return -1;
        }
    }

    @UnsupportedAppUsage
    public static boolean isFilenameSafe(File file) {
        return NoImagePreloadHolder.SAFE_FILENAME_PATTERN.matcher(file.getPath()).matches();
    }

    public static boolean isValidExtFilename(String string2) {
        boolean bl = string2 != null && string2.equals(FileUtils.buildValidExtFilename(string2));
        return bl;
    }

    private static boolean isValidExtFilenameChar(char c) {
        return c != '\u0000' && c != '/';
    }

    public static boolean isValidFatFilename(String string2) {
        boolean bl = string2 != null && string2.equals(FileUtils.buildValidFatFilename(string2));
        return bl;
    }

    private static boolean isValidFatFilenameChar(char c) {
        if (c >= '\u0000' && c <= '\u001f') {
            return false;
        }
        return c != '\"' && c != '*' && c != '/' && c != ':' && c != '<' && c != '\\' && c != '|' && c != '' && c != '>' && c != '?';
    }

    static /* synthetic */ void lambda$copyInternalSendfile$2(ProgressListener progressListener, long l) {
        progressListener.onProgress(l);
    }

    static /* synthetic */ void lambda$copyInternalSendfile$3(ProgressListener progressListener, long l) {
        progressListener.onProgress(l);
    }

    static /* synthetic */ void lambda$copyInternalSplice$0(ProgressListener progressListener, long l) {
        progressListener.onProgress(l);
    }

    static /* synthetic */ void lambda$copyInternalSplice$1(ProgressListener progressListener, long l) {
        progressListener.onProgress(l);
    }

    static /* synthetic */ void lambda$copyInternalUserspace$4(ProgressListener progressListener, long l) {
        progressListener.onProgress(l);
    }

    static /* synthetic */ void lambda$copyInternalUserspace$5(ProgressListener progressListener, long l) {
        progressListener.onProgress(l);
    }

    public static File[] listFilesOrEmpty(File arrfile) {
        arrfile = arrfile != null ? ArrayUtils.defeatNullable(arrfile.listFiles()) : ArrayUtils.EMPTY_FILE;
        return arrfile;
    }

    public static File[] listFilesOrEmpty(File arrfile, FilenameFilter filenameFilter) {
        arrfile = arrfile != null ? ArrayUtils.defeatNullable(arrfile.listFiles(filenameFilter)) : ArrayUtils.EMPTY_FILE;
        return arrfile;
    }

    public static String[] listOrEmpty(File arrstring) {
        arrstring = arrstring != null ? ArrayUtils.defeatNullable(arrstring.list()) : EmptyArray.STRING;
        return arrstring;
    }

    public static File newFileOrNull(String object) {
        object = object != null ? new File((String)object) : null;
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public static String readTextFile(File var0, int var1_2, String var2_3) throws IOException {
        block25 : {
            block26 : {
                block27 : {
                    block21 : {
                        block22 : {
                            block24 : {
                                block23 : {
                                    var3_4 = new FileInputStream((File)var0);
                                    var4_5 = new BufferedInputStream(var3_4);
                                    var5_6 = var0.length();
                                    if (var1_2 > 0 || var5_6 > 0L && var1_2 == 0) break block21;
                                    if (var1_2 >= 0) break block22;
                                    var7_7 = 0;
                                    var8_8 = null;
                                    var0 = null;
                                    do {
                                        var9_10 = var7_7;
                                        if (var8_8 != null) {
                                            var9_10 = 1;
                                        }
                                        var10_12 = var0;
                                        var0 = var8_8;
                                        var11_13 = var0;
                                        if (var0 == null) {
                                            var7_7 = -var1_2;
                                            var11_13 = new byte[var7_7];
                                        }
                                        var12_15 = var4_5.read(var11_13);
                                        var13_16 = var11_13.length;
                                        var7_7 = var9_10;
                                        var8_8 = var10_12;
                                        var0 = var11_13;
                                    } while (var12_15 == var13_16);
                                    if (var10_12 != null || var12_15 > 0) break block23;
                                    var4_5.close();
                                    var3_4.close();
                                    return "";
                                }
                                if (var10_12 == null) {
                                    var0 = new String(var11_13, 0, var12_15);
                                    return var0;
                                }
                                if (var12_15 <= 0) ** GOTO lbl40
                                var9_10 = 1;
                                System.arraycopy(var10_12, var12_15, var10_12, 0, ((Object)var10_12).length - var12_15);
                                System.arraycopy(var11_13, 0, var10_12, ((Object)var10_12).length - var12_15, var12_15);
lbl40: // 2 sources:
                                if (var2_3 == null || var9_10 == 0) break block24;
                                var0 = new StringBuilder();
                                var0.append((String)var2_3);
                                var2_3 = new String((byte[])var10_12);
                                var0.append((String)var2_3);
                                var0 = var0.toString();
                                var4_5.close();
                                var3_4.close();
                                return var0;
                            }
                            var0 = new String((byte[])var10_12);
                            var4_5.close();
                            var3_4.close();
                            return var0;
                        }
                        var2_3 = new ByteArrayOutputStream();
                        var0 = new byte[1024];
                        do {
                            if ((var1_2 = var4_5.read((byte[])var0)) <= 0) continue;
                            var2_3.write((byte[])var0, 0, var1_2);
                        } while (var1_2 == ((Object)var0).length);
                        var0 = var2_3.toString();
                        var4_5.close();
                        var3_4.close();
                        return var0;
                    }
                    var9_11 = var1_2;
                    if (var5_6 <= 0L) break block26;
                    if (var1_2 == 0) break block27;
                    var9_11 = var1_2;
                    if (var5_6 >= (long)var1_2) break block26;
                }
                var9_11 = (int)var5_6;
            }
            var8_9 = new byte[var9_11 + 1];
            var1_2 = var4_5.read(var8_9);
            if (var1_2 > 0) break block25;
            var4_5.close();
            var3_4.close();
            return "";
        }
        if (var1_2 <= var9_11) {
            var0 = new String(var8_9, 0, var1_2);
            var4_5.close();
            var3_4.close();
            return var0;
        }
        if (var2_3 == null) {
            var0 = new String(var8_9, 0, var9_11);
            var4_5.close();
            var3_4.close();
            return var0;
        }
        var0 = new StringBuilder();
        var11_14 = new String(var8_9, 0, var9_11);
        var0.append(var11_14);
        var0.append((String)var2_3);
        var0 = var0.toString();
        var4_5.close();
        var3_4.close();
        return var0;
        finally {
            var4_5.close();
            var3_4.close();
        }
    }

    public static File rewriteAfterRename(File file, File file2, File file3) {
        if (file3 != null && file != null && file2 != null) {
            if (FileUtils.contains(file, file3)) {
                return new File(file2, file3.getAbsolutePath().substring(file.getAbsolutePath().length()));
            }
            return null;
        }
        return null;
    }

    public static String rewriteAfterRename(File object, File file, String string2) {
        Object var3_3 = null;
        if (string2 == null) {
            return null;
        }
        file = FileUtils.rewriteAfterRename((File)object, file, new File(string2));
        object = var3_3;
        if (file != null) {
            object = file.getAbsolutePath();
        }
        return object;
    }

    public static String[] rewriteAfterRename(File file, File file2, String[] arrstring) {
        if (arrstring == null) {
            return null;
        }
        String[] arrstring2 = new String[arrstring.length];
        for (int i = 0; i < arrstring.length; ++i) {
            arrstring2[i] = FileUtils.rewriteAfterRename(file, file2, arrstring[i]);
        }
        return arrstring2;
    }

    public static long roundStorageSize(long l) {
        long l2 = 1L;
        long l3 = 1L;
        while (l2 * l3 < l) {
            long l4;
            l2 = l4 = l2 << 1;
            if (l4 <= 512L) continue;
            l2 = 1L;
            l3 *= 1000L;
        }
        return l2 * l3;
    }

    @UnsupportedAppUsage
    public static int setPermissions(File file, int n, int n2, int n3) {
        return FileUtils.setPermissions(file.getAbsolutePath(), n, n2, n3);
    }

    @UnsupportedAppUsage
    public static int setPermissions(FileDescriptor object, int n, int n2, int n3) {
        block4 : {
            try {
                Os.fchmod((FileDescriptor)object, (int)n);
                if (n2 < 0 && n3 < 0) break block4;
            }
            catch (ErrnoException errnoException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to fchmod(): ");
                ((StringBuilder)object).append((Object)errnoException);
                Slog.w(TAG, ((StringBuilder)object).toString());
                return errnoException.errno;
            }
            try {
                Os.fchown((FileDescriptor)object, (int)n2, (int)n3);
            }
            catch (ErrnoException errnoException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to fchown(): ");
                stringBuilder.append((Object)errnoException);
                Slog.w(TAG, stringBuilder.toString());
                return errnoException.errno;
            }
        }
        return 0;
    }

    @UnsupportedAppUsage
    public static int setPermissions(String string2, int n, int n2, int n3) {
        block4 : {
            try {
                Os.chmod((String)string2, (int)n);
                if (n2 < 0 && n3 < 0) break block4;
            }
            catch (ErrnoException errnoException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to chmod(");
                stringBuilder.append(string2);
                stringBuilder.append("): ");
                stringBuilder.append((Object)errnoException);
                Slog.w(TAG, stringBuilder.toString());
                return errnoException.errno;
            }
            try {
                Os.chown((String)string2, (int)n2, (int)n3);
            }
            catch (ErrnoException errnoException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to chown(");
                stringBuilder.append(string2);
                stringBuilder.append("): ");
                stringBuilder.append((Object)errnoException);
                Slog.w(TAG, stringBuilder.toString());
                return errnoException.errno;
            }
        }
        return 0;
    }

    public static String[] splitFileName(String string2, String string3) {
        String string4;
        String string5;
        if ("vnd.android.document/directory".equals(string2)) {
            string5 = null;
            string4 = string3;
        } else {
            String string6;
            String string7;
            int n = string3.lastIndexOf(46);
            if (n >= 0) {
                string7 = string3.substring(0, n);
                string6 = string3.substring(n + 1);
                string5 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(string6.toLowerCase());
            } else {
                string5 = null;
                string7 = string3;
                string6 = null;
            }
            String string8 = string5;
            if (string5 == null) {
                string8 = "application/octet-stream";
            }
            String string9 = "application/octet-stream".equals(string2) ? null : MimeTypeMap.getSingleton().getExtensionFromMimeType(string2);
            string5 = string6;
            string4 = string7;
            if (!Objects.equals(string2, string8)) {
                if (Objects.equals(string6, string9)) {
                    string5 = string6;
                    string4 = string7;
                } else {
                    string5 = string9;
                    string4 = string3;
                }
            }
        }
        string2 = string5;
        if (string5 == null) {
            string2 = "";
        }
        return new String[]{string4, string2};
    }

    @UnsupportedAppUsage
    public static void stringToFile(File file, String string2) throws IOException {
        FileUtils.stringToFile(file.getAbsolutePath(), string2);
    }

    @UnsupportedAppUsage
    public static void stringToFile(String string2, String string3) throws IOException {
        FileUtils.bytesToFile(string2, string3.getBytes(StandardCharsets.UTF_8));
    }

    @UnsupportedAppUsage
    public static boolean sync(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.getFD().sync();
            }
            catch (IOException iOException) {
                return false;
            }
        }
        return true;
    }

    public static int translateModeAccessToPosix(int n) {
        if (n == OsConstants.F_OK) {
            return OsConstants.O_RDONLY;
        }
        if (((OsConstants.R_OK | OsConstants.W_OK) & n) == (OsConstants.R_OK | OsConstants.W_OK)) {
            return OsConstants.O_RDWR;
        }
        if ((OsConstants.R_OK & n) == OsConstants.R_OK) {
            return OsConstants.O_RDONLY;
        }
        if ((OsConstants.W_OK & n) == OsConstants.W_OK) {
            return OsConstants.O_WRONLY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad mode: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static int translateModePfdToPosix(int n) {
        block9 : {
            int n2;
            block7 : {
                block8 : {
                    block6 : {
                        if ((n & 805306368) != 805306368) break block6;
                        n2 = OsConstants.O_RDWR;
                        break block7;
                    }
                    if ((n & 536870912) != 536870912) break block8;
                    n2 = OsConstants.O_WRONLY;
                    break block7;
                }
                if ((n & 268435456) != 268435456) break block9;
                n2 = OsConstants.O_RDONLY;
            }
            int n3 = n2;
            if ((n & 134217728) == 134217728) {
                n3 = n2 | OsConstants.O_CREAT;
            }
            n2 = n3;
            if ((n & 67108864) == 67108864) {
                n2 = n3 | OsConstants.O_TRUNC;
            }
            n3 = n2;
            if ((n & 33554432) == 33554432) {
                n3 = n2 | OsConstants.O_APPEND;
            }
            return n3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad mode: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static int translateModePosixToPfd(int n) {
        block9 : {
            int n2;
            block7 : {
                block8 : {
                    block6 : {
                        if ((OsConstants.O_ACCMODE & n) != OsConstants.O_RDWR) break block6;
                        n2 = 805306368;
                        break block7;
                    }
                    if ((OsConstants.O_ACCMODE & n) != OsConstants.O_WRONLY) break block8;
                    n2 = 536870912;
                    break block7;
                }
                if ((OsConstants.O_ACCMODE & n) != OsConstants.O_RDONLY) break block9;
                n2 = 268435456;
            }
            int n3 = n2;
            if ((OsConstants.O_CREAT & n) == OsConstants.O_CREAT) {
                n3 = n2 | 134217728;
            }
            n2 = n3;
            if ((OsConstants.O_TRUNC & n) == OsConstants.O_TRUNC) {
                n2 = n3 | 67108864;
            }
            n3 = n2;
            if ((OsConstants.O_APPEND & n) == OsConstants.O_APPEND) {
                n3 = n2 | 33554432;
            }
            return n3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad mode: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static String translateModePosixToString(int n) {
        block8 : {
            CharSequence charSequence;
            block6 : {
                block7 : {
                    block5 : {
                        if ((OsConstants.O_ACCMODE & n) != OsConstants.O_RDWR) break block5;
                        charSequence = "rw";
                        break block6;
                    }
                    if ((OsConstants.O_ACCMODE & n) != OsConstants.O_WRONLY) break block7;
                    charSequence = "w";
                    break block6;
                }
                if ((OsConstants.O_ACCMODE & n) != OsConstants.O_RDONLY) break block8;
                charSequence = "r";
            }
            CharSequence charSequence2 = charSequence;
            if ((OsConstants.O_TRUNC & n) == OsConstants.O_TRUNC) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("t");
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = charSequence2;
            if ((OsConstants.O_APPEND & n) == OsConstants.O_APPEND) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("a");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            return charSequence;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad mode: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static int translateModeStringToPosix(String string2) {
        block9 : {
            int n;
            int n2;
            block7 : {
                block8 : {
                    block6 : {
                        for (n2 = 0; n2 < string2.length(); ++n2) {
                            n = string2.charAt(n2);
                            if (n == 97 || n == 114 || n == 116 || n == 119) continue;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Bad mode: ");
                            stringBuilder.append(string2);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        if (!string2.startsWith("rw")) break block6;
                        n2 = OsConstants.O_RDWR | OsConstants.O_CREAT;
                        break block7;
                    }
                    if (!string2.startsWith("w")) break block8;
                    n2 = OsConstants.O_WRONLY | OsConstants.O_CREAT;
                    break block7;
                }
                if (!string2.startsWith("r")) break block9;
                n2 = OsConstants.O_RDONLY;
            }
            n = n2;
            if (string2.indexOf(116) != -1) {
                n = n2 | OsConstants.O_TRUNC;
            }
            n2 = n;
            if (string2.indexOf(97) != -1) {
                n2 = n | OsConstants.O_APPEND;
            }
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad mode: ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @VisibleForTesting
    public static String trimFilename(String charSequence, int n) {
        charSequence = new StringBuilder((String)charSequence);
        FileUtils.trimFilename((StringBuilder)charSequence, n);
        return ((StringBuilder)charSequence).toString();
    }

    private static void trimFilename(StringBuilder stringBuilder, int n) {
        byte[] arrby = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        if (arrby.length > n) {
            while (arrby.length > n - 3) {
                stringBuilder.deleteCharAt(stringBuilder.length() / 2);
                arrby = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
            }
            stringBuilder.insert(stringBuilder.length() / 2, "...");
        }
    }

    @VisibleForTesting
    public static class MemoryPipe
    extends Thread
    implements AutoCloseable {
        private final byte[] data;
        private final FileDescriptor[] pipe;
        private final boolean sink;

        private MemoryPipe(byte[] arrby, boolean bl) throws IOException {
            try {
                this.pipe = Os.pipe();
                this.data = arrby;
                this.sink = bl;
                return;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }

        public static MemoryPipe createSink(byte[] arrby) throws IOException {
            return new MemoryPipe(arrby, true).startInternal();
        }

        public static MemoryPipe createSource(byte[] arrby) throws IOException {
            return new MemoryPipe(arrby, false).startInternal();
        }

        private MemoryPipe startInternal() {
            super.start();
            return this;
        }

        @Override
        public void close() throws Exception {
            IoUtils.closeQuietly((FileDescriptor)this.getFD());
        }

        public FileDescriptor getFD() {
            FileDescriptor fileDescriptor = this.sink ? this.pipe[1] : this.pipe[0];
            return fileDescriptor;
        }

        public FileDescriptor getInternalFD() {
            FileDescriptor fileDescriptor = this.sink ? this.pipe[0] : this.pipe[1];
            return fileDescriptor;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            FileDescriptor fileDescriptor;
            block6 : {
                fileDescriptor = this.getInternalFD();
                int n = 0;
                try {
                    while (n < this.data.length) {
                        if (this.sink) {
                            n += Os.read((FileDescriptor)fileDescriptor, (byte[])this.data, (int)n, (int)(this.data.length - n));
                            continue;
                        }
                        int n2 = Os.write((FileDescriptor)fileDescriptor, (byte[])this.data, (int)n, (int)(this.data.length - n));
                        n += n2;
                    }
                    if (!this.sink) break block6;
                }
                catch (Throwable throwable) {
                    if (this.sink) {
                        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1L));
                    }
                    IoUtils.closeQuietly((FileDescriptor)fileDescriptor);
                    throw throwable;
                }
                catch (ErrnoException | IOException throwable) {
                    if (!this.sink) break block6;
                }
                SystemClock.sleep(TimeUnit.SECONDS.toMillis(1L));
            }
            IoUtils.closeQuietly((FileDescriptor)fileDescriptor);
        }
    }

    private static class NoImagePreloadHolder {
        public static final Pattern SAFE_FILENAME_PATTERN = Pattern.compile("[\\w%+,./=_-]+");

        private NoImagePreloadHolder() {
        }
    }

    public static interface ProgressListener {
        public void onProgress(long var1);
    }

}

