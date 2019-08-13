/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.app.backup.FullBackup;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;

public class BackupUtils {
    private BackupUtils() {
    }

    public static boolean isFileSpecifiedInPathList(File file, Collection<FullBackup.BackupScheme.PathWithRequiredFlags> object) throws IOException {
        Iterator<FullBackup.BackupScheme.PathWithRequiredFlags> iterator = object.iterator();
        while (iterator.hasNext()) {
            String string2 = iterator.next().getPath();
            object = new File(string2);
            if (!(((File)object).isDirectory() ? (file.isDirectory() ? file.equals(object) : file.toPath().startsWith(string2)) : file.equals(object))) continue;
            return true;
        }
        return false;
    }
}

