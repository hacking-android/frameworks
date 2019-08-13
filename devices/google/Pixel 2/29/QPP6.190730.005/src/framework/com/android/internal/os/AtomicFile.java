/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.os.FileUtils;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class AtomicFile {
    private final File mBackupName;
    private final File mBaseName;

    @UnsupportedAppUsage
    public AtomicFile(File file) {
        this.mBaseName = file;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getPath());
        stringBuilder.append(".bak");
        this.mBackupName = new File(stringBuilder.toString());
    }

    public void delete() {
        this.mBaseName.delete();
        this.mBackupName.delete();
    }

    public boolean exists() {
        boolean bl = this.mBaseName.exists() || this.mBackupName.exists();
        return bl;
    }

    @UnsupportedAppUsage
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

    @UnsupportedAppUsage
    public void finishWrite(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            FileUtils.sync(fileOutputStream);
            try {
                fileOutputStream.close();
                this.mBackupName.delete();
            }
            catch (IOException iOException) {
                Log.w("AtomicFile", "finishWrite: Got exception:", iOException);
            }
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public File getBaseFile() {
        return this.mBaseName;
    }

    @UnsupportedAppUsage
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

    @UnsupportedAppUsage
    public FileInputStream openRead() throws FileNotFoundException {
        if (this.mBackupName.exists()) {
            this.mBaseName.delete();
            this.mBackupName.renameTo(this.mBaseName);
        }
        return new FileInputStream(this.mBaseName);
    }

    @UnsupportedAppUsage
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public FileOutputStream startWrite() throws IOException {
        Object object;
        block8 : {
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
                if (!((File)object).mkdir()) break block8;
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
    @UnsupportedAppUsage
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
}

