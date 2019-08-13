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

public class AbsoluteFileBackupHelper
extends FileBackupHelperBase
implements BackupHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "AbsoluteFileBackupHelper";
    Context mContext;
    String[] mFiles;

    public AbsoluteFileBackupHelper(Context context, String ... arrstring) {
        super(context);
        this.mContext = context;
        this.mFiles = arrstring;
    }

    @Override
    public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        String[] arrstring = this.mFiles;
        AbsoluteFileBackupHelper.performBackup_checked(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, arrstring, arrstring);
    }

    @Override
    public void restoreEntity(BackupDataInputStream backupDataInputStream) {
        String string2 = backupDataInputStream.getKey();
        if (this.isKeyInList(string2, this.mFiles)) {
            this.writeFile(new File(string2), backupDataInputStream);
        }
    }
}

