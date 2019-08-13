/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.UnsupportedAppUsage;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BackupHelperDispatcher {
    private static final String TAG = "BackupHelperDispatcher";
    TreeMap<String, BackupHelper> mHelpers = new TreeMap();

    private static native int allocateHeader_native(Header var0, FileDescriptor var1);

    private void doOneBackup(ParcelFileDescriptor object, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor, Header header, BackupHelper backupHelper) throws IOException {
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        int n = BackupHelperDispatcher.allocateHeader_native(header, fileDescriptor);
        if (n >= 0) {
            backupDataOutput.setKeyPrefix(header.keyPrefix);
            backupHelper.performBackup((ParcelFileDescriptor)object, backupDataOutput, parcelFileDescriptor);
            n = BackupHelperDispatcher.writeHeader_native(header, fileDescriptor, n);
            if (n == 0) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("writeHeader_native failed (error ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(")");
            throw new IOException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("allocateHeader_native failed (error ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(")");
        throw new IOException(((StringBuilder)object).toString());
    }

    private static native int readHeader_native(Header var0, FileDescriptor var1);

    private static native int skipChunk_native(FileDescriptor var0, int var1);

    private static native int writeHeader_native(Header var0, FileDescriptor var1, int var2);

    public void addHelper(String string2, BackupHelper backupHelper) {
        this.mHelpers.put(string2, backupHelper);
    }

    public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) throws IOException {
        StringBuilder stringBuilder;
        Header header = new Header();
        TreeMap object2 = (TreeMap)this.mHelpers.clone();
        if (parcelFileDescriptor != null) {
            int n;
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            while ((n = BackupHelperDispatcher.readHeader_native(header, fileDescriptor)) >= 0) {
                if (n != 0) continue;
                BackupHelper backupHelper = (BackupHelper)object2.get(header.keyPrefix);
                stringBuilder = new StringBuilder();
                stringBuilder.append("handling existing helper '");
                stringBuilder.append(header.keyPrefix);
                stringBuilder.append("' ");
                stringBuilder.append(backupHelper);
                Log.d("BackupHelperDispatcher", stringBuilder.toString());
                if (backupHelper != null) {
                    this.doOneBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, header, backupHelper);
                    object2.remove(header.keyPrefix);
                    continue;
                }
                BackupHelperDispatcher.skipChunk_native(fileDescriptor, header.chunkSize);
            }
        }
        for (Map.Entry entry : object2.entrySet()) {
            header.keyPrefix = (String)entry.getKey();
            stringBuilder = new StringBuilder();
            stringBuilder.append("handling new helper '");
            stringBuilder.append(header.keyPrefix);
            stringBuilder.append("'");
            Log.d("BackupHelperDispatcher", stringBuilder.toString());
            this.doOneBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, header, (BackupHelper)entry.getValue());
        }
    }

    public void performRestore(BackupDataInput object, int n, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        n = 0;
        BackupDataInputStream backupDataInputStream = new BackupDataInputStream((BackupDataInput)object);
        while (((BackupDataInput)object).readNextHeader()) {
            Object object2;
            String string2 = ((BackupDataInput)object).getKey();
            int n2 = string2.indexOf(58);
            if (n2 > 0) {
                object2 = string2.substring(0, n2);
                if ((object2 = this.mHelpers.get(object2)) != null) {
                    backupDataInputStream.dataSize = ((BackupDataInput)object).getDataSize();
                    backupDataInputStream.key = string2.substring(n2 + 1);
                    object2.restoreEntity(backupDataInputStream);
                    n2 = n;
                } else {
                    n2 = n;
                    if (n == 0) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Couldn't find helper for: '");
                        ((StringBuilder)object2).append(string2);
                        ((StringBuilder)object2).append("'");
                        Log.w("BackupHelperDispatcher", ((StringBuilder)object2).toString());
                        n2 = 1;
                    }
                }
            } else {
                n2 = n;
                if (n == 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Entity with no prefix: '");
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append("'");
                    Log.w("BackupHelperDispatcher", ((StringBuilder)object2).toString());
                    n2 = 1;
                }
            }
            ((BackupDataInput)object).skipEntityData();
            n = n2;
        }
        object = this.mHelpers.values().iterator();
        while (object.hasNext()) {
            ((BackupHelper)object.next()).writeNewStateDescription(parcelFileDescriptor);
        }
    }

    private static class Header {
        @UnsupportedAppUsage
        int chunkSize;
        @UnsupportedAppUsage
        String keyPrefix;

        private Header() {
        }
    }

}

