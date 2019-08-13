/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.UnsupportedAppUsage;
import android.app.QueuedWork;
import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.app.backup.FileBackupHelperBase;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import java.io.File;

public class SharedPreferencesBackupHelper
extends FileBackupHelperBase
implements BackupHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "SharedPreferencesBackupHelper";
    private Context mContext;
    private String[] mPrefGroups;

    public SharedPreferencesBackupHelper(Context context, String ... arrstring) {
        super(context);
        this.mContext = context;
        this.mPrefGroups = arrstring;
    }

    @Override
    public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        Context context = this.mContext;
        QueuedWork.waitToFinish();
        String[] arrstring = this.mPrefGroups;
        int n = arrstring.length;
        String[] arrstring2 = new String[n];
        for (int i = 0; i < n; ++i) {
            arrstring2[i] = context.getSharedPrefsFile(arrstring[i]).getAbsolutePath();
        }
        SharedPreferencesBackupHelper.performBackup_checked(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, arrstring2, arrstring);
    }

    @Override
    public void restoreEntity(BackupDataInputStream backupDataInputStream) {
        Context context = this.mContext;
        String string2 = backupDataInputStream.getKey();
        if (this.isKeyInList(string2, this.mPrefGroups)) {
            this.writeFile(context.getSharedPrefsFile(string2).getAbsoluteFile(), backupDataInputStream);
        }
    }
}

