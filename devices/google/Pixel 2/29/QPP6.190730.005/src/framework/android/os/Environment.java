/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.FileUtils;
import android.os.Process;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.util.LinkedList;

public class Environment {
    public static String DIRECTORY_ALARMS;
    @Deprecated
    public static final String DIRECTORY_ANDROID = "Android";
    public static String DIRECTORY_AUDIOBOOKS;
    public static String DIRECTORY_DCIM;
    public static String DIRECTORY_DOCUMENTS;
    public static String DIRECTORY_DOWNLOADS;
    public static String DIRECTORY_MOVIES;
    public static String DIRECTORY_MUSIC;
    public static String DIRECTORY_NOTIFICATIONS;
    public static String DIRECTORY_PICTURES;
    public static String DIRECTORY_PODCASTS;
    public static String DIRECTORY_RINGTONES;
    public static String DIRECTORY_SCREENSHOTS;
    public static final String DIR_ANDROID = "Android";
    private static final File DIR_ANDROID_DATA;
    private static final File DIR_ANDROID_EXPAND;
    private static final File DIR_ANDROID_ROOT;
    private static final File DIR_ANDROID_STORAGE;
    private static final String DIR_CACHE = "cache";
    private static final String DIR_DATA = "data";
    private static final File DIR_DOWNLOAD_CACHE;
    private static final String DIR_FILES = "files";
    private static final String DIR_MEDIA = "media";
    private static final String DIR_OBB = "obb";
    private static final File DIR_ODM_ROOT;
    private static final File DIR_OEM_ROOT;
    private static final File DIR_PRODUCT_ROOT;
    private static final File DIR_PRODUCT_SERVICES_ROOT;
    private static final File DIR_VENDOR_ROOT;
    private static final String ENV_ANDROID_DATA = "ANDROID_DATA";
    private static final String ENV_ANDROID_EXPAND = "ANDROID_EXPAND";
    private static final String ENV_ANDROID_ROOT = "ANDROID_ROOT";
    private static final String ENV_ANDROID_STORAGE = "ANDROID_STORAGE";
    private static final String ENV_DOWNLOAD_CACHE = "DOWNLOAD_CACHE";
    private static final String ENV_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";
    private static final String ENV_ODM_ROOT = "ODM_ROOT";
    private static final String ENV_OEM_ROOT = "OEM_ROOT";
    private static final String ENV_PRODUCT_ROOT = "PRODUCT_ROOT";
    private static final String ENV_PRODUCT_SERVICES_ROOT = "PRODUCT_SERVICES_ROOT";
    private static final String ENV_VENDOR_ROOT = "VENDOR_ROOT";
    public static final int HAS_ALARMS = 8;
    public static final int HAS_ANDROID = 65536;
    public static final int HAS_AUDIOBOOKS = 1024;
    public static final int HAS_DCIM = 256;
    public static final int HAS_DOCUMENTS = 512;
    public static final int HAS_DOWNLOADS = 128;
    public static final int HAS_MOVIES = 64;
    public static final int HAS_MUSIC = 1;
    public static final int HAS_NOTIFICATIONS = 16;
    public static final int HAS_OTHER = 131072;
    public static final int HAS_PICTURES = 32;
    public static final int HAS_PODCASTS = 2;
    public static final int HAS_RINGTONES = 4;
    public static final String MEDIA_BAD_REMOVAL = "bad_removal";
    public static final String MEDIA_CHECKING = "checking";
    public static final String MEDIA_EJECTING = "ejecting";
    public static final String MEDIA_MOUNTED = "mounted";
    public static final String MEDIA_MOUNTED_READ_ONLY = "mounted_ro";
    public static final String MEDIA_NOFS = "nofs";
    public static final String MEDIA_REMOVED = "removed";
    public static final String MEDIA_SHARED = "shared";
    public static final String MEDIA_UNKNOWN = "unknown";
    public static final String MEDIA_UNMOUNTABLE = "unmountable";
    public static final String MEDIA_UNMOUNTED = "unmounted";
    public static final String[] STANDARD_DIRECTORIES;
    private static final String TAG = "Environment";
    @UnsupportedAppUsage
    private static UserEnvironment sCurrentUser;
    private static boolean sUserRequired;

    static {
        DIR_ANDROID_ROOT = Environment.getDirectory(ENV_ANDROID_ROOT, "/system");
        DIR_ANDROID_DATA = Environment.getDirectory(ENV_ANDROID_DATA, "/data");
        DIR_ANDROID_EXPAND = Environment.getDirectory(ENV_ANDROID_EXPAND, "/mnt/expand");
        DIR_ANDROID_STORAGE = Environment.getDirectory(ENV_ANDROID_STORAGE, "/storage");
        DIR_DOWNLOAD_CACHE = Environment.getDirectory(ENV_DOWNLOAD_CACHE, "/cache");
        DIR_OEM_ROOT = Environment.getDirectory(ENV_OEM_ROOT, "/oem");
        DIR_ODM_ROOT = Environment.getDirectory(ENV_ODM_ROOT, "/odm");
        DIR_VENDOR_ROOT = Environment.getDirectory(ENV_VENDOR_ROOT, "/vendor");
        DIR_PRODUCT_ROOT = Environment.getDirectory(ENV_PRODUCT_ROOT, "/product");
        DIR_PRODUCT_SERVICES_ROOT = Environment.getDirectory(ENV_PRODUCT_SERVICES_ROOT, "/product_services");
        Environment.initForCurrentUser();
        DIRECTORY_MUSIC = "Music";
        DIRECTORY_PODCASTS = "Podcasts";
        DIRECTORY_RINGTONES = "Ringtones";
        DIRECTORY_ALARMS = "Alarms";
        DIRECTORY_NOTIFICATIONS = "Notifications";
        DIRECTORY_PICTURES = "Pictures";
        DIRECTORY_MOVIES = "Movies";
        DIRECTORY_DOWNLOADS = "Download";
        DIRECTORY_DCIM = "DCIM";
        DIRECTORY_DOCUMENTS = "Documents";
        DIRECTORY_SCREENSHOTS = "Screenshots";
        DIRECTORY_AUDIOBOOKS = "Audiobooks";
        STANDARD_DIRECTORIES = new String[]{DIRECTORY_MUSIC, DIRECTORY_PODCASTS, DIRECTORY_RINGTONES, DIRECTORY_ALARMS, DIRECTORY_NOTIFICATIONS, DIRECTORY_PICTURES, DIRECTORY_MOVIES, DIRECTORY_DOWNLOADS, DIRECTORY_DCIM, DIRECTORY_DOCUMENTS, DIRECTORY_AUDIOBOOKS};
    }

    @UnsupportedAppUsage
    public static File[] buildExternalStorageAndroidDataDirs() {
        Environment.throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAndroidDataDirs();
    }

    @UnsupportedAppUsage
    public static File[] buildExternalStorageAppCacheDirs(String string2) {
        Environment.throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppCacheDirs(string2);
    }

    @UnsupportedAppUsage
    public static File[] buildExternalStorageAppDataDirs(String string2) {
        Environment.throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppDataDirs(string2);
    }

    @UnsupportedAppUsage
    public static File[] buildExternalStorageAppFilesDirs(String string2) {
        Environment.throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppFilesDirs(string2);
    }

    @UnsupportedAppUsage
    public static File[] buildExternalStorageAppMediaDirs(String string2) {
        Environment.throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppMediaDirs(string2);
    }

    @UnsupportedAppUsage
    public static File[] buildExternalStorageAppObbDirs(String string2) {
        Environment.throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppObbDirs(string2);
    }

    public static File[] buildExternalStoragePublicDirs(String string2) {
        Environment.throwIfUserRequired();
        return sCurrentUser.buildExternalStoragePublicDirs(string2);
    }

    public static File buildPath(File file, String ... arrstring) {
        for (String string2 : arrstring) {
            file = file == null ? new File(string2) : new File(file, string2);
        }
        return file;
    }

    @UnsupportedAppUsage
    public static File[] buildPaths(File[] arrfile, String ... arrstring) {
        File[] arrfile2 = new File[arrfile.length];
        for (int i = 0; i < arrfile.length; ++i) {
            arrfile2[i] = Environment.buildPath(arrfile[i], arrstring);
        }
        return arrfile2;
    }

    public static int classifyExternalStorageDirectory(File arrfile) {
        int n = 0;
        for (File file : FileUtils.listFilesOrEmpty((File)arrfile)) {
            int n2;
            if (file.isFile() && Environment.isInterestingFile(file)) {
                n2 = n | 131072;
            } else {
                n2 = n;
                if (file.isDirectory()) {
                    n2 = n;
                    if (Environment.hasInterestingFiles(file)) {
                        String string2 = file.getName();
                        n2 = DIRECTORY_MUSIC.equals(string2) ? n | 1 : (DIRECTORY_PODCASTS.equals(string2) ? n | 2 : (DIRECTORY_RINGTONES.equals(string2) ? n | 4 : (DIRECTORY_ALARMS.equals(string2) ? n | 8 : (DIRECTORY_NOTIFICATIONS.equals(string2) ? n | 16 : (DIRECTORY_PICTURES.equals(string2) ? n | 32 : (DIRECTORY_MOVIES.equals(string2) ? n | 64 : (DIRECTORY_DOWNLOADS.equals(string2) ? n | 128 : (DIRECTORY_DCIM.equals(string2) ? n | 256 : (DIRECTORY_DOCUMENTS.equals(string2) ? n | 512 : (DIRECTORY_AUDIOBOOKS.equals(string2) ? n | 1024 : ("Android".equals(string2) ? n | 65536 : n | 131072)))))))))));
                    }
                }
            }
            n = n2;
        }
        return n;
    }

    public static File getDataAppDirectory(String string2) {
        return new File(Environment.getDataDirectory(string2), "app");
    }

    public static File getDataDirectory() {
        return DIR_ANDROID_DATA;
    }

    public static File getDataDirectory(String string2) {
        if (TextUtils.isEmpty(string2)) {
            return DIR_ANDROID_DATA;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/mnt/expand/");
        stringBuilder.append(string2);
        return new File(stringBuilder.toString());
    }

    public static File getDataMiscCeDirectory() {
        return Environment.buildPath(Environment.getDataDirectory(), "misc_ce");
    }

    public static File getDataMiscCeDirectory(int n) {
        return Environment.buildPath(Environment.getDataDirectory(), "misc_ce", String.valueOf(n));
    }

    public static File getDataMiscDeDirectory(int n) {
        return Environment.buildPath(Environment.getDataDirectory(), "misc_de", String.valueOf(n));
    }

    public static File getDataMiscDirectory() {
        return new File(Environment.getDataDirectory(), "misc");
    }

    public static File getDataPreloadsAppsDirectory() {
        return new File(Environment.getDataPreloadsDirectory(), "apps");
    }

    public static File getDataPreloadsDemoDirectory() {
        return new File(Environment.getDataPreloadsDirectory(), "demo");
    }

    public static File getDataPreloadsDirectory() {
        return new File(Environment.getDataDirectory(), "preloads");
    }

    public static File getDataPreloadsFileCacheDirectory() {
        return new File(Environment.getDataPreloadsDirectory(), "file_cache");
    }

    public static File getDataPreloadsFileCacheDirectory(String string2) {
        return new File(Environment.getDataPreloadsFileCacheDirectory(), string2);
    }

    public static File getDataPreloadsMediaDirectory() {
        return new File(Environment.getDataPreloadsDirectory(), DIR_MEDIA);
    }

    private static File getDataProfilesDeDirectory(int n) {
        return Environment.buildPath(Environment.getDataDirectory(), "misc", "profiles", "cur", String.valueOf(n));
    }

    public static File getDataProfilesDePackageDirectory(int n, String string2) {
        return Environment.buildPath(Environment.getDataProfilesDeDirectory(n), string2);
    }

    public static File getDataRefProfilesDePackageDirectory(String string2) {
        return Environment.buildPath(Environment.getDataDirectory(), "misc", "profiles", "ref", string2);
    }

    public static File getDataStagingDirectory(String string2) {
        return new File(Environment.getDataDirectory(string2), "app-staging");
    }

    public static File getDataSystemCeDirectory() {
        return Environment.buildPath(Environment.getDataDirectory(), "system_ce");
    }

    public static File getDataSystemCeDirectory(int n) {
        return Environment.buildPath(Environment.getDataDirectory(), "system_ce", String.valueOf(n));
    }

    public static File getDataSystemDeDirectory() {
        return Environment.buildPath(Environment.getDataDirectory(), "system_de");
    }

    public static File getDataSystemDeDirectory(int n) {
        return Environment.buildPath(Environment.getDataDirectory(), "system_de", String.valueOf(n));
    }

    @UnsupportedAppUsage
    public static File getDataSystemDirectory() {
        return new File(Environment.getDataDirectory(), "system");
    }

    public static File getDataUserCeDirectory(String string2) {
        return new File(Environment.getDataDirectory(string2), "user");
    }

    public static File getDataUserCeDirectory(String string2, int n) {
        return new File(Environment.getDataUserCeDirectory(string2), String.valueOf(n));
    }

    public static File getDataUserCePackageDirectory(String string2, int n, String string3) {
        return new File(Environment.getDataUserCeDirectory(string2, n), string3);
    }

    public static File getDataUserDeDirectory(String string2) {
        return new File(Environment.getDataDirectory(string2), "user_de");
    }

    public static File getDataUserDeDirectory(String string2, int n) {
        return new File(Environment.getDataUserDeDirectory(string2), String.valueOf(n));
    }

    public static File getDataUserDePackageDirectory(String string2, int n, String string3) {
        return new File(Environment.getDataUserDeDirectory(string2, n), string3);
    }

    public static File getDataVendorCeDirectory(int n) {
        return Environment.buildPath(Environment.getDataDirectory(), "vendor_ce", String.valueOf(n));
    }

    public static File getDataVendorDeDirectory(int n) {
        return Environment.buildPath(Environment.getDataDirectory(), "vendor_de", String.valueOf(n));
    }

    static File getDirectory(String object, String string2) {
        String string3 = System.getenv((String)object);
        object = string3 == null ? new File(string2) : new File(string3);
        return object;
    }

    public static File getDownloadCacheDirectory() {
        return DIR_DOWNLOAD_CACHE;
    }

    public static File getExpandDirectory() {
        return DIR_ANDROID_EXPAND;
    }

    @Deprecated
    public static File getExternalStorageDirectory() {
        Environment.throwIfUserRequired();
        return sCurrentUser.getExternalDirs()[0];
    }

    @Deprecated
    public static File getExternalStoragePublicDirectory(String string2) {
        Environment.throwIfUserRequired();
        return sCurrentUser.buildExternalStoragePublicDirs(string2)[0];
    }

    public static String getExternalStorageState() {
        return Environment.getExternalStorageState(sCurrentUser.getExternalDirs()[0]);
    }

    public static String getExternalStorageState(File object) {
        if ((object = StorageManager.getStorageVolume((File)object, UserHandle.myUserId())) != null) {
            return ((StorageVolume)object).getState();
        }
        return MEDIA_UNKNOWN;
    }

    @UnsupportedAppUsage
    public static File getLegacyExternalStorageDirectory() {
        return new File(System.getenv(ENV_EXTERNAL_STORAGE));
    }

    @UnsupportedAppUsage
    public static File getLegacyExternalStorageObbDirectory() {
        return Environment.buildPath(Environment.getLegacyExternalStorageDirectory(), "Android", DIR_OBB);
    }

    @SystemApi
    public static File getOdmDirectory() {
        return DIR_ODM_ROOT;
    }

    @SystemApi
    public static File getOemDirectory() {
        return DIR_OEM_ROOT;
    }

    public static File getPackageCacheDirectory() {
        return new File(Environment.getDataSystemDirectory(), "package_cache");
    }

    @SystemApi
    public static File getProductDirectory() {
        return DIR_PRODUCT_ROOT;
    }

    @SystemApi
    public static File getProductServicesDirectory() {
        return DIR_PRODUCT_SERVICES_ROOT;
    }

    public static File getRootDirectory() {
        return DIR_ANDROID_ROOT;
    }

    public static File getStorageDirectory() {
        return DIR_ANDROID_STORAGE;
    }

    @Deprecated
    public static String getStorageState(File file) {
        return Environment.getExternalStorageState(file);
    }

    @Deprecated
    public static File getUserConfigDirectory(int n) {
        return new File(new File(new File(Environment.getDataDirectory(), "misc"), "user"), Integer.toString(n));
    }

    @Deprecated
    public static File getUserSystemDirectory(int n) {
        return new File(new File(Environment.getDataSystemDirectory(), "users"), Integer.toString(n));
    }

    @SystemApi
    public static File getVendorDirectory() {
        return DIR_VENDOR_ROOT;
    }

    private static boolean hasInterestingFiles(File arrfile) {
        LinkedList<Object> linkedList = new LinkedList<Object>();
        linkedList.add(arrfile);
        block0 : do {
            boolean bl = linkedList.isEmpty();
            int n = 0;
            if (bl) break;
            arrfile = FileUtils.listFilesOrEmpty((File)linkedList.pop());
            int n2 = arrfile.length;
            do {
                if (n >= n2) continue block0;
                File file = arrfile[n];
                if (Environment.isInterestingFile(file)) {
                    return true;
                }
                if (file.isDirectory()) {
                    linkedList.add(file);
                }
                ++n;
            } while (true);
            break;
        } while (true);
        return false;
    }

    @UnsupportedAppUsage
    public static void initForCurrentUser() {
        sCurrentUser = new UserEnvironment(UserHandle.myUserId());
    }

    public static boolean isExternalStorageEmulated() {
        return Environment.isExternalStorageEmulated(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageEmulated(File file) {
        Object object = StorageManager.getStorageVolume(file, UserHandle.myUserId());
        if (object != null) {
            return ((StorageVolume)object).isEmulated();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to find storage device at ");
        ((StringBuilder)object).append(file);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static boolean isExternalStorageLegacy() {
        return Environment.isExternalStorageLegacy(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageLegacy(File object) {
        object = AppGlobals.getInitialApplication();
        int n = object.getApplicationInfo().uid;
        boolean bl = Process.isIsolated(n);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        String[] arrstring = ((Context)object).getPackageManager();
        if (arrstring.isInstantApp()) {
            return false;
        }
        if (arrstring.checkPermission("android.permission.WRITE_MEDIA_STORAGE", ((Context)object).getPackageName()) == 0) {
            return true;
        }
        if (arrstring.checkPermission("android.permission.INSTALL_PACKAGES", ((Context)object).getPackageName()) == 0) {
            return true;
        }
        AppOpsManager appOpsManager = ((Context)object).getSystemService(AppOpsManager.class);
        arrstring = arrstring.getPackagesForUid(n);
        int n2 = arrstring.length;
        for (int i = 0; i < n2; ++i) {
            if (appOpsManager.checkOpNoThrow(66, n, arrstring[i]) != 0) continue;
            return true;
        }
        if (appOpsManager.checkOpNoThrow(87, n, ((Context)object).getOpPackageName()) == 0) {
            bl2 = true;
        }
        return bl2;
    }

    public static boolean isExternalStorageRemovable() {
        return Environment.isExternalStorageRemovable(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageRemovable(File file) {
        Object object = StorageManager.getStorageVolume(file, UserHandle.myUserId());
        if (object != null) {
            return ((StorageVolume)object).isRemovable();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to find storage device at ");
        ((StringBuilder)object).append(file);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static boolean isInterestingFile(File object) {
        if (((File)object).isFile()) {
            return !(((String)(object = ((File)object).getName().toLowerCase())).endsWith(".exe") || ((String)object).equals("autorun.inf") || ((String)object).equals("launchpad.zip") || ((String)object).equals(".nomedia"));
            {
            }
        }
        return false;
    }

    public static boolean isStandardDirectory(String string2) {
        String[] arrstring = STANDARD_DIRECTORIES;
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!arrstring[i].equals(string2)) continue;
            return true;
        }
        return false;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static File maybeTranslateEmulatedPathToInternal(File file) {
        return StorageManager.maybeTranslateEmulatedPathToInternal(file);
    }

    public static void setUserRequired(boolean bl) {
        sUserRequired = bl;
    }

    private static void throwIfUserRequired() {
        if (sUserRequired) {
            Log.wtf(TAG, "Path requests must specify a user by using UserEnvironment", new Throwable());
        }
    }

    public static class UserEnvironment {
        private final int mUserId;

        @UnsupportedAppUsage
        public UserEnvironment(int n) {
            this.mUserId = n;
        }

        public File[] buildExternalStorageAndroidDataDirs() {
            return Environment.buildPaths(this.getExternalDirs(), "Android", Environment.DIR_DATA);
        }

        public File[] buildExternalStorageAndroidObbDirs() {
            return Environment.buildPaths(this.getExternalDirs(), "Android", Environment.DIR_OBB);
        }

        public File[] buildExternalStorageAppCacheDirs(String string2) {
            return Environment.buildPaths(this.getExternalDirs(), "Android", Environment.DIR_DATA, string2, Environment.DIR_CACHE);
        }

        public File[] buildExternalStorageAppDataDirs(String string2) {
            return Environment.buildPaths(this.getExternalDirs(), "Android", Environment.DIR_DATA, string2);
        }

        public File[] buildExternalStorageAppFilesDirs(String string2) {
            return Environment.buildPaths(this.getExternalDirs(), "Android", Environment.DIR_DATA, string2, Environment.DIR_FILES);
        }

        public File[] buildExternalStorageAppMediaDirs(String string2) {
            return Environment.buildPaths(this.getExternalDirs(), "Android", Environment.DIR_MEDIA, string2);
        }

        public File[] buildExternalStorageAppObbDirs(String string2) {
            return Environment.buildPaths(this.getExternalDirs(), "Android", Environment.DIR_OBB, string2);
        }

        public File[] buildExternalStoragePublicDirs(String string2) {
            return Environment.buildPaths(this.getExternalDirs(), string2);
        }

        @UnsupportedAppUsage
        public File[] getExternalDirs() {
            StorageVolume[] arrstorageVolume = StorageManager.getVolumeList(this.mUserId, 256);
            File[] arrfile = new File[arrstorageVolume.length];
            for (int i = 0; i < arrstorageVolume.length; ++i) {
                arrfile[i] = arrstorageVolume[i].getPathFile();
            }
            return arrfile;
        }

        @Deprecated
        @UnsupportedAppUsage
        public File getExternalStorageDirectory() {
            return this.getExternalDirs()[0];
        }

        @Deprecated
        @UnsupportedAppUsage
        public File getExternalStoragePublicDirectory(String string2) {
            return this.buildExternalStoragePublicDirs(string2)[0];
        }
    }

}

