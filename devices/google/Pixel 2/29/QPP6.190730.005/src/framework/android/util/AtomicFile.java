/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.util;

import android.os.FileUtils;
import android.os.SystemClock;
import android.util.ExceptionUtils;
import android.util.Log;
import com.android.internal.logging.EventLogTags;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;
import libcore.io.IoUtils;

public class AtomicFile {
    private final File mBackupName;
    private final File mBaseName;
    private final String mCommitTag;
    private long mStartTime;

    public AtomicFile(File file) {
        this(file, null);
    }

    public AtomicFile(File file, String string2) {
        this.mBaseName = file;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getPath());
        stringBuilder.append(".bak");
        this.mBackupName = new File(stringBuilder.toString());
        this.mCommitTag = string2;
    }

    public void delete() {
        this.mBaseName.delete();
        this.mBackupName.delete();
    }

    public boolean exists() {
        boolean bl = this.mBaseName.exists() || this.mBackupName.exists();
        return bl;
    }

    public void failWrite(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            FileUtils.sync(fileOutputStream);
            try {
                fileOutputStream.close();
                this.mBaseName.delete();
                this.mBackupName.renameTo(this.mBaseName);
            }
            catch (IOException iOException) {
                Log.w("AtomicFile", "failWrite: Got exception:", iOException);
            }
        }
    }

    public void finishWrite(FileOutputStream object) {
        if (object != null) {
            FileUtils.sync((FileOutputStream)object);
            try {
                ((FileOutputStream)object).close();
                this.mBackupName.delete();
            }
            catch (IOException iOException) {
                Log.w("AtomicFile", "finishWrite: Got exception:", iOException);
            }
            object = this.mCommitTag;
            if (object != null) {
                EventLogTags.writeCommitSysConfigFile((String)object, SystemClock.uptimeMillis() - this.mStartTime);
            }
        }
    }

    public File getBaseFile() {
        return this.mBaseName;
    }

    public long getLastModifiedTime() {
        if (this.mBackupName.exists()) {
            return this.mBackupName.lastModified();
        }
        return this.mBaseName.lastModified();
    }

    @Deprecated
    public FileOutputStream openAppend() throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.mBaseName, true);
            return fileOutputStream;
        }
        catch (FileNotFoundException fileNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't append ");
            stringBuilder.append(this.mBaseName);
            throw new IOException(stringBuilder.toString());
        }
    }

    public FileInputStream openRead() throws FileNotFoundException {
        if (this.mBackupName.exists()) {
            this.mBaseName.delete();
            this.mBackupName.renameTo(this.mBaseName);
        }
        return new FileInputStream(this.mBaseName);
    }

    public byte[] readFully() throws IOException {
        int n;
        byte[] arrby;
        FileInputStream fileInputStream = this.openRead();
        int n2 = 0;
        try {
            arrby = new byte[fileInputStream.available()];
            do {
                if ((n = fileInputStream.read(arrby, n2, arrby.length - n2)) > 0) break block6;
                break;
            } while (true);
        }
        catch (Throwable throwable) {
            fileInputStream.close();
            throw throwable;
        }
        {
            block6 : {
                fileInputStream.close();
                return arrby;
            }
            n2 += n;
            n = fileInputStream.available();
            byte[] arrby2 = arrby;
            if (n > arrby.length - n2) {
                arrby2 = new byte[n2 + n];
                System.arraycopy(arrby, 0, arrby2, 0, n2);
            }
            arrby = arrby2;
            continue;
        }
    }

    public FileOutputStream startWrite() throws IOException {
        long l = this.mCommitTag != null ? SystemClock.uptimeMillis() : 0L;
        return this.startWrite(l);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public FileOutputStream startWrite(long l) throws IOException {
        Object object;
        block8 : {
            this.mStartTime = l;
            if (this.mBaseName.exists()) {
                if (!this.mBackupName.exists()) {
                    if (!this.mBaseName.renameTo(this.mBackupName)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Couldn't rename file ");
                        ((StringBuilder)object).append(this.mBaseName);
                        ((StringBuilder)object).append(" to backup file ");
                        ((StringBuilder)object).append(this.mBackupName);
                        Log.w("AtomicFile", ((StringBuilder)object).toString());
                    }
                } else {
                    this.mBaseName.delete();
                }
            }
            try {
                return new FileOutputStream(this.mBaseName);
            }
            catch (FileNotFoundException fileNotFoundException) {
                object = this.mBaseName.getParentFile();
                if (!((File)object).mkdirs()) break block8;
                FileUtils.setPermissions(((File)object).getPath(), 505, -1, -1);
                try {
                    return new FileOutputStream(this.mBaseName);
                }
                catch (FileNotFoundException fileNotFoundException2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't create ");
                    stringBuilder.append(this.mBaseName);
                    throw new IOException(stringBuilder.toString());
                }
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Couldn't create directory ");
        ((StringBuilder)object).append(this.mBaseName);
        throw new IOException(((StringBuilder)object).toString());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Deprecated
    public void truncate() throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.mBaseName);
            FileUtils.sync(fileOutputStream);
            fileOutputStream.close();
            return;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return;
        catch (FileNotFoundException fileNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't append ");
            stringBuilder.append(this.mBaseName);
            throw new IOException(stringBuilder.toString());
        }
    }

    public void write(Consumer<FileOutputStream> consumer) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream2 = fileOutputStream = this.startWrite();
        }
        catch (Throwable throwable) {
            try {
                this.failWrite(fileOutputStream2);
                throw ExceptionUtils.propagate(throwable);
            }
            catch (Throwable throwable2) {
                IoUtils.closeQuietly((AutoCloseable)fileOutputStream2);
                throw throwable2;
            }
        }
        consumer.accept(fileOutputStream);
        fileOutputStream2 = fileOutputStream;
        this.finishWrite(fileOutputStream);
        IoUtils.closeQuietly((AutoCloseable)fileOutputStream);
    }
}

