/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.UnsupportedAppUsage;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;

class FileBackupHelperBase {
    private static final String TAG = "FileBackupHelperBase";
    Context mContext;
    boolean mExceptionLogged;
    long mPtr = FileBackupHelperBase.ctor();

    FileBackupHelperBase(Context context) {
        this.mContext = context;
    }

    private static native long ctor();

    private static native void dtor(long var0);

    static void performBackup_checked(ParcelFileDescriptor object, BackupDataOutput backupDataOutput, ParcelFileDescriptor object2, String[] arrstring, String[] arrstring2) {
        int n;
        if (arrstring.length == 0) {
            return;
        }
        int n2 = arrstring.length;
        for (n = 0; n < n2; ++n) {
            String string2 = arrstring[n];
            if (string2.charAt(0) == '/') {
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("files must have all absolute paths: ");
            ((StringBuilder)object).append(string2);
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        if (arrstring.length == arrstring2.length) {
            object = object != null ? ((ParcelFileDescriptor)object).getFileDescriptor() : null;
            if ((object2 = ((ParcelFileDescriptor)object2).getFileDescriptor()) != null) {
                n = FileBackupHelperBase.performBackup_native((FileDescriptor)object, backupDataOutput.mBackupWriter, (FileDescriptor)object2, arrstring, arrstring2);
                if (n == 0) {
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Backup failed 0x");
                ((StringBuilder)object).append(Integer.toHexString(n));
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            throw new NullPointerException();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("files.length=");
        ((StringBuilder)object).append(arrstring.length);
        ((StringBuilder)object).append(" keys.length=");
        ((StringBuilder)object).append(arrstring2.length);
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    private static native int performBackup_native(FileDescriptor var0, long var1, FileDescriptor var3, String[] var4, String[] var5);

    private static native int writeFile_native(long var0, String var2, long var3);

    private static native int writeSnapshot_native(long var0, FileDescriptor var2);

    protected void finalize() throws Throwable {
        try {
            FileBackupHelperBase.dtor(this.mPtr);
            return;
        }
        finally {
            super.finalize();
        }
    }

    boolean isKeyInList(String string2, String[] arrstring) {
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!arrstring[i].equals(string2)) continue;
            return true;
        }
        return false;
    }

    boolean writeFile(File file, BackupDataInputStream object) {
        file.getParentFile().mkdirs();
        int n = FileBackupHelperBase.writeFile_native(this.mPtr, file.getAbsolutePath(), object.mData.mBackupReader);
        boolean bl = true;
        if (n != 0 && !this.mExceptionLogged) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed restoring file '");
            ((StringBuilder)object).append(file);
            ((StringBuilder)object).append("' for app '");
            ((StringBuilder)object).append(this.mContext.getPackageName());
            ((StringBuilder)object).append("' result=0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            Log.e("FileBackupHelperBase", ((StringBuilder)object).toString());
            this.mExceptionLogged = true;
        }
        if (n != 0) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
        FileBackupHelperBase.writeSnapshot_native(this.mPtr, parcelFileDescriptor.getFileDescriptor());
    }
}

