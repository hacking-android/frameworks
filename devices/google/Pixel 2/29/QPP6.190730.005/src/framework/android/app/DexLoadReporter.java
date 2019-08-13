/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.BaseDexClassLoader
 *  dalvik.system.BaseDexClassLoader$Reporter
 *  dalvik.system.VMRuntime
 */
package android.app;

import android.app.ActivityThread;
import android.os.FileUtils;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.internal.annotations.GuardedBy;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class DexLoadReporter
implements BaseDexClassLoader.Reporter {
    private static final boolean DEBUG = false;
    private static final DexLoadReporter INSTANCE = new DexLoadReporter();
    private static final String TAG = "DexLoadReporter";
    @GuardedBy(value={"mDataDirs"})
    private final Set<String> mDataDirs = new HashSet<String>();

    private DexLoadReporter() {
    }

    static DexLoadReporter getInstance() {
        return INSTANCE;
    }

    private boolean isSecondaryDexFile(String string2, String[] arrstring) {
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!FileUtils.contains(arrstring[i], string2)) continue;
            return true;
        }
        return false;
    }

    private void notifyPackageManager(List<ClassLoader> object, List<String> object2) {
        ArrayList<String> arrayList = new ArrayList<String>(object2.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(((ClassLoader)object.next()).getClass().getName());
        }
        object = ActivityThread.currentPackageName();
        try {
            ActivityThread.getPackageManager().notifyDexLoad((String)object, (List<String>)arrayList, (List<String>)object2, VMRuntime.getRuntime().vmInstructionSet());
        }
        catch (RemoteException remoteException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Failed to notify PM about dex load for package ");
            ((StringBuilder)object2).append((String)object);
            Slog.e(TAG, ((StringBuilder)object2).toString(), remoteException);
        }
    }

    private void registerSecondaryDexForProfiling(String charSequence, String[] object) {
        if (!this.isSecondaryDexFile((String)charSequence, (String[])object)) {
            return;
        }
        File file = new File((String)charSequence);
        object = new File(file.getParent(), "oat");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getName());
        stringBuilder.append(".cur.prof");
        file = new File((File)object, stringBuilder.toString());
        if (!((File)object).exists() && !((File)object).mkdir()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Could not create the profile directory: ");
            ((StringBuilder)charSequence).append(file);
            Slog.e(TAG, ((StringBuilder)charSequence).toString());
            return;
        }
        try {
            file.createNewFile();
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to create profile for secondary dex ");
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append(":");
            ((StringBuilder)object).append(iOException.getMessage());
            Slog.e(TAG, ((StringBuilder)object).toString());
            return;
        }
        VMRuntime.registerAppInfo((String)file.getPath(), (String[])new String[]{charSequence});
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void registerSecondaryDexForProfiling(String[] arrstring) {
        String[] arrstring2;
        int n = 0;
        if (!SystemProperties.getBoolean("dalvik.vm.dexopt.secondary", false)) {
            return;
        }
        Set<String> set = this.mDataDirs;
        synchronized (set) {
            arrstring2 = this.mDataDirs.toArray(new String[0]);
        }
        int n2 = arrstring.length;
        while (n < n2) {
            this.registerSecondaryDexForProfiling(arrstring[n], arrstring2);
            ++n;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void registerAppDataDir(String object, String string2) {
        if (string2 == null) return;
        object = this.mDataDirs;
        synchronized (object) {
            this.mDataDirs.add(string2);
            return;
        }
    }

    public void report(List<ClassLoader> list, List<String> list2) {
        if (list.size() != list2.size()) {
            Slog.wtf(TAG, "Bad call to DexLoadReporter: argument size mismatch");
            return;
        }
        if (list2.isEmpty()) {
            Slog.wtf(TAG, "Bad call to DexLoadReporter: empty dex paths");
            return;
        }
        String[] arrstring = list2.get(0).split(File.pathSeparator);
        if (arrstring.length == 0) {
            return;
        }
        this.notifyPackageManager(list, list2);
        this.registerSecondaryDexForProfiling(arrstring);
    }
}

