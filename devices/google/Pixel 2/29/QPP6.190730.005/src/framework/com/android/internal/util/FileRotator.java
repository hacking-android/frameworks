/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package com.android.internal.util;

import android.os.FileUtils;
import com.android.internal.util.Preconditions;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import libcore.io.IoUtils;

public class FileRotator {
    private static final boolean LOGD = false;
    private static final String SUFFIX_BACKUP = ".backup";
    private static final String SUFFIX_NO_BACKUP = ".no_backup";
    private static final String TAG = "FileRotator";
    private final File mBasePath;
    private final long mDeleteAgeMillis;
    private final String mPrefix;
    private final long mRotateAgeMillis;

    public FileRotator(File arrstring, String object, long l, long l2) {
        this.mBasePath = (File)Preconditions.checkNotNull(arrstring);
        this.mPrefix = (String)Preconditions.checkNotNull(object);
        this.mRotateAgeMillis = l;
        this.mDeleteAgeMillis = l2;
        this.mBasePath.mkdirs();
        for (String string2 : this.mBasePath.list()) {
            if (!string2.startsWith(this.mPrefix)) continue;
            if (string2.endsWith(SUFFIX_BACKUP)) {
                new File(this.mBasePath, string2).renameTo(new File(this.mBasePath, string2.substring(0, string2.length() - SUFFIX_BACKUP.length())));
                continue;
            }
            if (!string2.endsWith(SUFFIX_NO_BACKUP)) continue;
            object = new File(this.mBasePath, string2);
            File file = new File(this.mBasePath, string2.substring(0, string2.length() - SUFFIX_NO_BACKUP.length()));
            ((File)object).delete();
            file.delete();
        }
    }

    private String getActiveName(long l) {
        String string2 = null;
        long l2 = Long.MAX_VALUE;
        FileInfo fileInfo = new FileInfo(this.mPrefix);
        for (String string3 : this.mBasePath.list()) {
            long l3;
            String string4;
            if (!fileInfo.parse(string3)) {
                string4 = string2;
                l3 = l2;
            } else {
                string4 = string2;
                l3 = l2;
                if (fileInfo.isActive()) {
                    string4 = string2;
                    l3 = l2;
                    if (fileInfo.startMillis < l) {
                        string4 = string2;
                        l3 = l2;
                        if (fileInfo.startMillis < l2) {
                            string4 = string3;
                            l3 = fileInfo.startMillis;
                        }
                    }
                }
            }
            string2 = string4;
            l2 = l3;
        }
        if (string2 != null) {
            return string2;
        }
        fileInfo.startMillis = l;
        fileInfo.endMillis = Long.MAX_VALUE;
        return fileInfo.build();
    }

    private static void readFile(File object, Reader reader) throws IOException {
        object = new BufferedInputStream(new FileInputStream((File)object));
        try {
            reader.read((InputStream)object);
            return;
        }
        finally {
            IoUtils.closeQuietly((AutoCloseable)object);
        }
    }

    private static IOException rethrowAsIoException(Throwable throwable) throws IOException {
        if (throwable instanceof IOException) {
            throw (IOException)throwable;
        }
        throw new IOException(throwable.getMessage(), throwable);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void rewriteSingle(Rewriter rewriter, String object) throws IOException {
        File file = new File(this.mBasePath, (String)object);
        rewriter.reset();
        if (file.exists()) {
            FileRotator.readFile(file, rewriter);
            if (!rewriter.shouldWrite()) {
                return;
            }
            File file2 = this.mBasePath;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append(SUFFIX_BACKUP);
            object = new File(file2, stringBuilder.toString());
            file.renameTo((File)object);
            try {
                FileRotator.writeFile(file, rewriter);
                ((File)object).delete();
                return;
            }
            catch (Throwable throwable) {
                file.delete();
                ((File)object).renameTo(file);
                throw FileRotator.rethrowAsIoException(throwable);
            }
        }
        File file3 = this.mBasePath;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append(SUFFIX_NO_BACKUP);
        object = new File(file3, stringBuilder.toString());
        ((File)object).createNewFile();
        try {
            FileRotator.writeFile(file, rewriter);
            ((File)object).delete();
            return;
        }
        catch (Throwable throwable) {
            file.delete();
            ((File)object).delete();
            throw FileRotator.rethrowAsIoException(throwable);
        }
    }

    private static void writeFile(File object, Writer writer) throws IOException {
        object = new FileOutputStream((File)object);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream((OutputStream)object);
        try {
            writer.write(bufferedOutputStream);
            bufferedOutputStream.flush();
            return;
        }
        finally {
            FileUtils.sync((FileOutputStream)object);
            IoUtils.closeQuietly((AutoCloseable)bufferedOutputStream);
        }
    }

    @Deprecated
    public void combineActive(final Reader reader, final Writer writer, long l) throws IOException {
        this.rewriteActive(new Rewriter(){

            @Override
            public void read(InputStream inputStream) throws IOException {
                reader.read(inputStream);
            }

            @Override
            public void reset() {
            }

            @Override
            public boolean shouldWrite() {
                return true;
            }

            @Override
            public void write(OutputStream outputStream) throws IOException {
                writer.write(outputStream);
            }
        }, l);
    }

    public void deleteAll() {
        FileInfo fileInfo = new FileInfo(this.mPrefix);
        for (String string2 : this.mBasePath.list()) {
            if (!fileInfo.parse(string2)) continue;
            new File(this.mBasePath, string2).delete();
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void dumpAll(OutputStream outputStream) throws IOException {
        FileInputStream fileInputStream;
        outputStream = new ZipOutputStream(outputStream);
        try {
            FileInfo fileInfo = new FileInfo(this.mPrefix);
            for (String string2 : this.mBasePath.list()) {
                if (!fileInfo.parse(string2)) continue;
                Object object = new ZipEntry(string2);
                ((ZipOutputStream)outputStream).putNextEntry((ZipEntry)object);
                object = new File(this.mBasePath, string2);
                fileInputStream = new FileInputStream((File)object);
            }
        }
        catch (Throwable throwable) {
            IoUtils.closeQuietly((AutoCloseable)outputStream);
            throw throwable;
        }
        {
            FileUtils.copy(fileInputStream, outputStream);
            {
                catch (Throwable throwable) {
                    IoUtils.closeQuietly((AutoCloseable)fileInputStream);
                    throw throwable;
                }
            }
            IoUtils.closeQuietly((AutoCloseable)fileInputStream);
            ((ZipOutputStream)outputStream).closeEntry();
            continue;
        }
        IoUtils.closeQuietly((AutoCloseable)outputStream);
    }

    public void maybeRotate(long l) {
        long l2 = this.mRotateAgeMillis;
        long l3 = this.mDeleteAgeMillis;
        FileInfo fileInfo = new FileInfo(this.mPrefix);
        String[] arrstring = this.mBasePath.list();
        if (arrstring == null) {
            return;
        }
        for (String string2 : arrstring) {
            if (!fileInfo.parse(string2)) continue;
            if (fileInfo.isActive()) {
                if (fileInfo.startMillis > l - l2) continue;
                fileInfo.endMillis = l;
                new File(this.mBasePath, string2).renameTo(new File(this.mBasePath, fileInfo.build()));
                continue;
            }
            if (fileInfo.endMillis > l - l3) continue;
            new File(this.mBasePath, string2).delete();
        }
    }

    public void readMatching(Reader reader, long l, long l2) throws IOException {
        FileInfo fileInfo = new FileInfo(this.mPrefix);
        for (String string2 : this.mBasePath.list()) {
            if (!fileInfo.parse(string2) || fileInfo.startMillis > l2 || l > fileInfo.endMillis) continue;
            FileRotator.readFile(new File(this.mBasePath, string2), reader);
        }
    }

    public void rewriteActive(Rewriter rewriter, long l) throws IOException {
        this.rewriteSingle(rewriter, this.getActiveName(l));
    }

    public void rewriteAll(Rewriter rewriter) throws IOException {
        FileInfo fileInfo = new FileInfo(this.mPrefix);
        for (String string2 : this.mBasePath.list()) {
            if (!fileInfo.parse(string2)) continue;
            this.rewriteSingle(rewriter, string2);
        }
    }

    private static class FileInfo {
        public long endMillis;
        public final String prefix;
        public long startMillis;

        public FileInfo(String string2) {
            this.prefix = Preconditions.checkNotNull(string2);
        }

        public String build() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.prefix);
            stringBuilder.append('.');
            stringBuilder.append(this.startMillis);
            stringBuilder.append('-');
            long l = this.endMillis;
            if (l != Long.MAX_VALUE) {
                stringBuilder.append(l);
            }
            return stringBuilder.toString();
        }

        public boolean isActive() {
            boolean bl = this.endMillis == Long.MAX_VALUE;
            return bl;
        }

        public boolean parse(String string2) {
            this.endMillis = -1L;
            this.startMillis = -1L;
            int n = string2.lastIndexOf(46);
            int n2 = string2.lastIndexOf(45);
            if (n != -1 && n2 != -1) {
                if (!this.prefix.equals(string2.substring(0, n))) {
                    return false;
                }
                try {
                    this.startMillis = Long.parseLong(string2.substring(n + 1, n2));
                    this.endMillis = string2.length() - n2 == 1 ? Long.MAX_VALUE : Long.parseLong(string2.substring(n2 + 1));
                    return true;
                }
                catch (NumberFormatException numberFormatException) {
                    return false;
                }
            }
            return false;
        }
    }

    public static interface Reader {
        public void read(InputStream var1) throws IOException;
    }

    public static interface Rewriter
    extends Reader,
    Writer {
        public void reset();

        public boolean shouldWrite();
    }

    public static interface Writer {
        public void write(OutputStream var1) throws IOException;
    }

}

