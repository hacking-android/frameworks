/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.FileUtils;
import android.util.ArrayMap;
import com.android.internal.util.Preconditions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

public final class AtomicDirectory {
    private final File mBackupDirectory;
    private int mBackupDirectoryFd = -1;
    private final File mBaseDirectory;
    private int mBaseDirectoryFd = -1;
    private final ArrayMap<File, FileOutputStream> mOpenFiles = new ArrayMap();

    public AtomicDirectory(File file) {
        Preconditions.checkNotNull(file, "baseDirectory cannot be null");
        this.mBaseDirectory = file;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getPath());
        stringBuilder.append("_bak");
        this.mBackupDirectory = new File(stringBuilder.toString());
    }

    private void backup() throws IOException {
        if (!this.mBaseDirectory.exists()) {
            return;
        }
        if (this.mBaseDirectoryFd < 0) {
            this.mBaseDirectoryFd = AtomicDirectory.getDirectoryFd(this.mBaseDirectory.getCanonicalPath());
        }
        if (this.mBackupDirectory.exists()) {
            AtomicDirectory.deleteDirectory(this.mBackupDirectory);
        }
        if (this.mBaseDirectory.renameTo(this.mBackupDirectory)) {
            this.mBackupDirectoryFd = this.mBaseDirectoryFd;
            this.mBaseDirectoryFd = -1;
            AtomicDirectory.fsyncDirectoryFd(this.mBackupDirectoryFd);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Couldn't backup ");
        stringBuilder.append(this.mBaseDirectory);
        stringBuilder.append(" to ");
        stringBuilder.append(this.mBackupDirectory);
        throw new IOException(stringBuilder.toString());
    }

    private static void deleteDirectory(File file) {
        File[] arrfile = file.listFiles();
        if (arrfile != null) {
            int n = arrfile.length;
            for (int i = 0; i < n; ++i) {
                AtomicDirectory.deleteDirectory(arrfile[i]);
            }
        }
        file.delete();
    }

    private static native void fsyncDirectoryFd(int var0);

    private static native int getDirectoryFd(String var0);

    private File getOrCreateBaseDirectory() throws IOException {
        if (!this.mBaseDirectory.exists()) {
            if (this.mBaseDirectory.mkdirs()) {
                FileUtils.setPermissions(this.mBaseDirectory.getPath(), 505, -1, -1);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't create directory ");
                stringBuilder.append(this.mBaseDirectory);
                throw new IOException(stringBuilder.toString());
            }
        }
        if (this.mBaseDirectoryFd < 0) {
            this.mBaseDirectoryFd = AtomicDirectory.getDirectoryFd(this.mBaseDirectory.getCanonicalPath());
        }
        return this.mBaseDirectory;
    }

    private void restore() throws IOException {
        if (!this.mBackupDirectory.exists()) {
            return;
        }
        if (this.mBackupDirectoryFd == -1) {
            this.mBackupDirectoryFd = AtomicDirectory.getDirectoryFd(this.mBackupDirectory.getCanonicalPath());
        }
        if (this.mBaseDirectory.exists()) {
            AtomicDirectory.deleteDirectory(this.mBaseDirectory);
        }
        if (this.mBackupDirectory.renameTo(this.mBaseDirectory)) {
            this.mBaseDirectoryFd = this.mBackupDirectoryFd;
            this.mBackupDirectoryFd = -1;
            AtomicDirectory.fsyncDirectoryFd(this.mBaseDirectoryFd);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Couldn't restore ");
        stringBuilder.append(this.mBackupDirectory);
        stringBuilder.append(" to ");
        stringBuilder.append(this.mBaseDirectory);
        throw new IOException(stringBuilder.toString());
    }

    private void throwIfSomeFilesOpen() {
        if (this.mOpenFiles.isEmpty()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unclosed files: ");
        stringBuilder.append(Arrays.toString(this.mOpenFiles.keySet().toArray()));
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void closeWrite(FileOutputStream fileOutputStream) {
        int n = this.mOpenFiles.indexOfValue(fileOutputStream);
        if (this.mOpenFiles.removeAt(n) != null) {
            FileUtils.sync(fileOutputStream);
            try {
                fileOutputStream.close();
            }
            catch (IOException iOException) {}
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown file stream ");
        stringBuilder.append(fileOutputStream);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void delete() {
        if (this.mBaseDirectory.exists()) {
            AtomicDirectory.deleteDirectory(this.mBaseDirectory);
            AtomicDirectory.fsyncDirectoryFd(this.mBaseDirectoryFd);
        }
        if (this.mBackupDirectory.exists()) {
            AtomicDirectory.deleteDirectory(this.mBackupDirectory);
            AtomicDirectory.fsyncDirectoryFd(this.mBackupDirectoryFd);
        }
    }

    public boolean exists() {
        boolean bl = this.mBaseDirectory.exists() || this.mBackupDirectory.exists();
        return bl;
    }

    public void failWrite() {
        this.throwIfSomeFilesOpen();
        try {
            this.restore();
        }
        catch (IOException iOException) {}
        this.mBaseDirectoryFd = -1;
        this.mBackupDirectoryFd = -1;
    }

    public void failWrite(FileOutputStream fileOutputStream) {
        int n = this.mOpenFiles.indexOfValue(fileOutputStream);
        if (n >= 0) {
            this.mOpenFiles.removeAt(n);
        }
    }

    public void finishRead() {
        this.mBaseDirectoryFd = -1;
        this.mBackupDirectoryFd = -1;
    }

    public void finishWrite() {
        this.throwIfSomeFilesOpen();
        AtomicDirectory.fsyncDirectoryFd(this.mBaseDirectoryFd);
        AtomicDirectory.deleteDirectory(this.mBackupDirectory);
        AtomicDirectory.fsyncDirectoryFd(this.mBackupDirectoryFd);
        this.mBaseDirectoryFd = -1;
        this.mBackupDirectoryFd = -1;
    }

    public File getBackupDirectory() {
        return this.mBackupDirectory;
    }

    public FileOutputStream openWrite(File serializable) throws IOException {
        if (!((File)serializable).isDirectory() && ((File)serializable).getParentFile().equals(this.getOrCreateBaseDirectory())) {
            Object object = new FileOutputStream((File)serializable);
            if (this.mOpenFiles.put((File)serializable, (FileOutputStream)object) == null) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Already open file");
            ((StringBuilder)object).append(((File)serializable).getCanonicalPath());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Must be a file in ");
        ((StringBuilder)serializable).append(this.getOrCreateBaseDirectory());
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public File startRead() throws IOException {
        this.restore();
        return this.getOrCreateBaseDirectory();
    }

    public File startWrite() throws IOException {
        this.backup();
        return this.getOrCreateBaseDirectory();
    }
}

