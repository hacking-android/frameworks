/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.backup;

import android.app.IWallpaperManager;
import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupHelper;
import android.app.backup.FullBackup;
import android.app.backup.FullBackupDataOutput;
import android.app.backup.WallpaperBackupHelper;
import android.content.Context;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.backup.AccountManagerBackupHelper;
import com.android.server.backup.AccountSyncSettingsBackupHelper;
import com.android.server.backup.NotificationBackupHelper;
import com.android.server.backup.PermissionBackupHelper;
import com.android.server.backup.PreferredActivityBackupHelper;
import com.android.server.backup.ShortcutBackupHelper;
import com.android.server.backup.SliceBackupHelper;
import com.android.server.backup.UsageStatsBackupHelper;
import com.google.android.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class SystemBackupAgent
extends BackupAgentHelper {
    private static final String ACCOUNT_MANAGER_HELPER = "account_manager";
    private static final String NOTIFICATION_HELPER = "notifications";
    private static final String PERMISSION_HELPER = "permissions";
    private static final String PREFERRED_HELPER = "preferred_activities";
    private static final String SHORTCUT_MANAGER_HELPER = "shortcut_manager";
    private static final String SLICES_HELPER = "slices";
    private static final String SYNC_SETTINGS_HELPER = "account_sync_settings";
    private static final String TAG = "SystemBackupAgent";
    private static final String USAGE_STATS_HELPER = "usage_stats";
    private static final String WALLPAPER_HELPER = "wallpaper";
    public static final String WALLPAPER_IMAGE;
    private static final String WALLPAPER_IMAGE_DIR;
    private static final String WALLPAPER_IMAGE_FILENAME = "wallpaper";
    private static final String WALLPAPER_IMAGE_KEY = "/data/data/com.android.settings/files/wallpaper";
    public static final String WALLPAPER_INFO;
    private static final String WALLPAPER_INFO_DIR;
    private static final String WALLPAPER_INFO_FILENAME = "wallpaper_info.xml";
    private static final Set<String> sEligibleForMultiUser;
    private int mUserId = 0;

    static {
        WALLPAPER_IMAGE_DIR = Environment.getUserSystemDirectory(0).getAbsolutePath();
        WALLPAPER_IMAGE = new File(Environment.getUserSystemDirectory(0), "wallpaper").getAbsolutePath();
        WALLPAPER_INFO_DIR = Environment.getUserSystemDirectory(0).getAbsolutePath();
        WALLPAPER_INFO = new File(Environment.getUserSystemDirectory(0), WALLPAPER_INFO_FILENAME).getAbsolutePath();
        sEligibleForMultiUser = Sets.newArraySet(PERMISSION_HELPER, NOTIFICATION_HELPER, SYNC_SETTINGS_HELPER);
    }

    @Override
    public void addHelper(String string2, BackupHelper backupHelper) {
        if (this.mUserId != 0 && !sEligibleForMultiUser.contains(string2)) {
            return;
        }
        super.addHelper(string2, backupHelper);
    }

    @Override
    public void onCreate(UserHandle userHandle) {
        super.onCreate(userHandle);
        this.mUserId = userHandle.getIdentifier();
        this.addHelper(SYNC_SETTINGS_HELPER, new AccountSyncSettingsBackupHelper(this, this.mUserId));
        this.addHelper(PREFERRED_HELPER, new PreferredActivityBackupHelper());
        this.addHelper(NOTIFICATION_HELPER, new NotificationBackupHelper(this.mUserId));
        this.addHelper(PERMISSION_HELPER, new PermissionBackupHelper(this.mUserId));
        this.addHelper(USAGE_STATS_HELPER, new UsageStatsBackupHelper(this));
        this.addHelper(SHORTCUT_MANAGER_HELPER, new ShortcutBackupHelper());
        this.addHelper(ACCOUNT_MANAGER_HELPER, new AccountManagerBackupHelper());
        this.addHelper(SLICES_HELPER, new SliceBackupHelper(this));
    }

    @Override
    public void onFullBackup(FullBackupDataOutput fullBackupDataOutput) throws IOException {
    }

    @Override
    public void onRestore(BackupDataInput backupDataInput, int n, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        this.addHelper("wallpaper", new WallpaperBackupHelper(this, new String[]{WALLPAPER_IMAGE_KEY}));
        this.addHelper("system_files", new WallpaperBackupHelper(this, new String[]{WALLPAPER_IMAGE_KEY}));
        super.onRestore(backupDataInput, n, parcelFileDescriptor);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void onRestoreFile(ParcelFileDescriptor var1_1, long var2_3, int var4_4, String var5_5, String var6_7, long var7_8, long var9_9) throws IOException {
        var11_10 = new StringBuilder();
        var11_10.append("Restoring file domain=");
        var11_10.append(var5_5);
        var11_10.append(" path=");
        var11_10.append(var6_7);
        Slog.i("SystemBackupAgent", var11_10.toString());
        var11_11 = null;
        if (!var5_5.equals("r")) ** GOTO lbl-1000
        if (var6_7.equals("wallpaper_info.xml")) {
            var11_12 = new File(SystemBackupAgent.WALLPAPER_INFO);
            var12_15 = true;
        } else if (var6_7.equals("wallpaper")) {
            var11_13 = new File(SystemBackupAgent.WALLPAPER_IMAGE);
            var12_15 = true;
        } else lbl-1000: // 2 sources:
        {
            var12_15 = false;
        }
        if (var11_14 != null) ** GOTO lbl38
        try {
            var13_16 = new StringBuilder();
            var13_16.append("Skipping unrecognized system file: [ ");
            var13_16.append(var5_5);
            var13_16.append(" : ");
            var13_16.append(var6_7);
            var13_16.append(" ]");
            Slog.w("SystemBackupAgent", var13_16.toString());
lbl38: // 2 sources:
            FullBackup.restoreFile((ParcelFileDescriptor)var1_1, var2_3, var4_4, var7_8, var9_9, (File)var11_14);
            if (var12_15 == false) return;
            var1_1 = (IWallpaperManager)ServiceManager.getService("wallpaper");
            if (var1_1 == null) return;
            try {
                var1_1.settingsRestored();
                return;
            }
            catch (RemoteException var5_6) {
                var1_1 = new StringBuilder();
                var1_1.append("Couldn't restore settings\n");
                var1_1.append(var5_6);
                Slog.e("SystemBackupAgent", var1_1.toString());
                return;
            }
        }
        catch (IOException var1_2) {
            if (var12_15 == false) return;
            new File(SystemBackupAgent.WALLPAPER_IMAGE).delete();
            new File(SystemBackupAgent.WALLPAPER_INFO).delete();
        }
    }
}

