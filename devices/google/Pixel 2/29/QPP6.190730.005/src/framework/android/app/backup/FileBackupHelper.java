/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.UnsupportedAppUsage;
import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.app.backup.FileBackupHelperBase;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import java.io.File;

public class FileBackupHelper
extends FileBackupHelperBase
implements BackupHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "FileBackupHelper";
    Context mContext;
    String[] mFiles;
    File mFilesDir;

    public FileBackupHelper(Context context, String ... arrstring) {
        super(context);
        this.mContext = context;
        this.mFilesDir = context.getFilesDir();
        this.mFiles = arrstring;
    }

    @Override
    public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        String[] arrstring = this.mFiles;
        File file = this.mContext.getFilesDir();
        int n = arrstring.length;
        String[] arrstring2 = new String[n];
        for (int i = 0; i < n; ++i) {
            arrstring2[i] = new File(file, arrstring[i]).getAbsolutePath();
        }
        FileBackupHelper.performBackup_checked(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, arrstring2, arrstring);
    }

    @Override
    public void restoreEntity(BackupDataInputStream backupDataInputStream) {
        String string2 = backupDataInputStream.getKey();
        if (this.isKeyInList(string2, this.mFiles)) {
            this.writeFile(new File(this.mFilesDir, string2), backupDataInputStream);
        }
    }
}

