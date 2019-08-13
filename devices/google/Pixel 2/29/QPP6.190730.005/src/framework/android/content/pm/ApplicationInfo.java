/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.SharedLibraryInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.util.ArraySet;
import android.util.Printer;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.ArrayUtils;
import com.android.server.SystemConfig;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ApplicationInfo
extends PackageItemInfo
implements Parcelable {
    public static final int CATEGORY_AUDIO = 1;
    public static final int CATEGORY_GAME = 0;
    public static final int CATEGORY_IMAGE = 3;
    public static final int CATEGORY_MAPS = 6;
    public static final int CATEGORY_NEWS = 5;
    public static final int CATEGORY_PRODUCTIVITY = 7;
    public static final int CATEGORY_SOCIAL = 4;
    public static final int CATEGORY_UNDEFINED = -1;
    public static final int CATEGORY_VIDEO = 2;
    public static final Parcelable.Creator<ApplicationInfo> CREATOR = new Parcelable.Creator<ApplicationInfo>(){

        @Override
        public ApplicationInfo createFromParcel(Parcel parcel) {
            return new ApplicationInfo(parcel);
        }

        public ApplicationInfo[] newArray(int n) {
            return new ApplicationInfo[n];
        }
    };
    public static final int FLAG_ALLOW_BACKUP = 32768;
    public static final int FLAG_ALLOW_CLEAR_USER_DATA = 64;
    public static final int FLAG_ALLOW_TASK_REPARENTING = 32;
    public static final int FLAG_DEBUGGABLE = 2;
    public static final int FLAG_EXTERNAL_STORAGE = 262144;
    public static final int FLAG_EXTRACT_NATIVE_LIBS = 268435456;
    public static final int FLAG_FACTORY_TEST = 16;
    public static final int FLAG_FULL_BACKUP_ONLY = 67108864;
    public static final int FLAG_HARDWARE_ACCELERATED = 536870912;
    public static final int FLAG_HAS_CODE = 4;
    public static final int FLAG_INSTALLED = 8388608;
    public static final int FLAG_IS_DATA_ONLY = 16777216;
    @Deprecated
    public static final int FLAG_IS_GAME = 33554432;
    public static final int FLAG_KILL_AFTER_RESTORE = 65536;
    public static final int FLAG_LARGE_HEAP = 1048576;
    public static final int FLAG_MULTIARCH = Integer.MIN_VALUE;
    public static final int FLAG_PERSISTENT = 8;
    public static final int FLAG_RESIZEABLE_FOR_SCREENS = 4096;
    public static final int FLAG_RESTORE_ANY_VERSION = 131072;
    public static final int FLAG_STOPPED = 2097152;
    public static final int FLAG_SUPPORTS_LARGE_SCREENS = 2048;
    public static final int FLAG_SUPPORTS_NORMAL_SCREENS = 1024;
    public static final int FLAG_SUPPORTS_RTL = 4194304;
    public static final int FLAG_SUPPORTS_SCREEN_DENSITIES = 8192;
    public static final int FLAG_SUPPORTS_SMALL_SCREENS = 512;
    public static final int FLAG_SUPPORTS_XLARGE_SCREENS = 524288;
    public static final int FLAG_SUSPENDED = 1073741824;
    public static final int FLAG_SYSTEM = 1;
    public static final int FLAG_TEST_ONLY = 256;
    public static final int FLAG_UPDATED_SYSTEM_APP = 128;
    public static final int FLAG_USES_CLEARTEXT_TRAFFIC = 134217728;
    public static final int FLAG_VM_SAFE_MODE = 16384;
    public static final int HIDDEN_API_ENFORCEMENT_DEFAULT = -1;
    public static final int HIDDEN_API_ENFORCEMENT_DISABLED = 0;
    public static final int HIDDEN_API_ENFORCEMENT_ENABLED = 2;
    public static final int HIDDEN_API_ENFORCEMENT_JUST_WARN = 1;
    private static final int HIDDEN_API_ENFORCEMENT_MAX = 2;
    private static final int HIDDEN_API_ENFORCEMENT_MIN = -1;
    public static final String METADATA_PRELOADED_FONTS = "preloaded_fonts";
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE = 1024;
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION = 4096;
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_UNRESIZEABLE = 2048;
    public static final int PRIVATE_FLAG_ALLOW_AUDIO_PLAYBACK_CAPTURE = 134217728;
    public static final int PRIVATE_FLAG_ALLOW_CLEAR_USER_DATA_ON_FAILED_RESTORE = 67108864;
    public static final int PRIVATE_FLAG_BACKUP_IN_FOREGROUND = 8192;
    public static final int PRIVATE_FLAG_CANT_SAVE_STATE = 2;
    public static final int PRIVATE_FLAG_DEFAULT_TO_DEVICE_PROTECTED_STORAGE = 32;
    public static final int PRIVATE_FLAG_DIRECT_BOOT_AWARE = 64;
    public static final int PRIVATE_FLAG_HAS_DOMAIN_URLS = 16;
    public static final int PRIVATE_FLAG_HAS_FRAGILE_USER_DATA = 16777216;
    public static final int PRIVATE_FLAG_HIDDEN = 1;
    public static final int PRIVATE_FLAG_INSTANT = 128;
    public static final int PRIVATE_FLAG_ISOLATED_SPLIT_LOADING = 32768;
    public static final int PRIVATE_FLAG_IS_RESOURCE_OVERLAY = 268435456;
    public static final int PRIVATE_FLAG_ODM = 1073741824;
    public static final int PRIVATE_FLAG_OEM = 131072;
    public static final int PRIVATE_FLAG_PARTIALLY_DIRECT_BOOT_AWARE = 256;
    public static final int PRIVATE_FLAG_PRIVILEGED = 8;
    public static final int PRIVATE_FLAG_PRODUCT = 524288;
    public static final int PRIVATE_FLAG_PRODUCT_SERVICES = 2097152;
    public static final int PRIVATE_FLAG_PROFILEABLE_BY_SHELL = 8388608;
    public static final int PRIVATE_FLAG_REQUEST_LEGACY_EXTERNAL_STORAGE = 536870912;
    public static final int PRIVATE_FLAG_REQUIRED_FOR_SYSTEM_USER = 512;
    public static final int PRIVATE_FLAG_SIGNED_WITH_PLATFORM_KEY = 1048576;
    public static final int PRIVATE_FLAG_STATIC_SHARED_LIBRARY = 16384;
    public static final int PRIVATE_FLAG_USES_NON_SDK_API = 4194304;
    public static final int PRIVATE_FLAG_USE_EMBEDDED_DEX = 33554432;
    public static final int PRIVATE_FLAG_VENDOR = 262144;
    public static final int PRIVATE_FLAG_VIRTUAL_PRELOAD = 65536;
    public String appComponentFactory;
    public String backupAgentName;
    public int category;
    public String classLoaderName;
    public String className;
    public int compatibleWidthLimitDp;
    public int compileSdkVersion;
    public String compileSdkVersionCodename;
    @SystemApi
    public String credentialProtectedDataDir;
    public String dataDir;
    public int descriptionRes;
    public String deviceProtectedDataDir;
    public boolean enabled;
    @UnsupportedAppUsage
    public int enabledSetting;
    public int flags;
    @UnsupportedAppUsage
    public int fullBackupContent;
    public boolean hiddenUntilInstalled;
    public int iconRes;
    @UnsupportedAppUsage
    public int installLocation;
    public int largestWidthLimitDp;
    public long longVersionCode;
    private int mHiddenApiPolicy;
    public String manageSpaceActivityName;
    public float maxAspectRatio;
    public float minAspectRatio;
    public int minSdkVersion;
    public String nativeLibraryDir;
    @UnsupportedAppUsage
    public String nativeLibraryRootDir;
    public boolean nativeLibraryRootRequiresIsa;
    public int networkSecurityConfigRes;
    public String permission;
    @UnsupportedAppUsage
    public String primaryCpuAbi;
    public int privateFlags;
    public String processName;
    public String publicSourceDir;
    public int requiresSmallestWidthDp;
    @UnsupportedAppUsage
    public String[] resourceDirs;
    public int roundIconRes;
    @UnsupportedAppUsage
    public String scanPublicSourceDir;
    @UnsupportedAppUsage
    public String scanSourceDir;
    public String seInfo;
    public String seInfoUser;
    @UnsupportedAppUsage
    public String secondaryCpuAbi;
    @UnsupportedAppUsage
    public String secondaryNativeLibraryDir;
    public String[] sharedLibraryFiles;
    public List<SharedLibraryInfo> sharedLibraryInfos;
    public String sourceDir;
    public String[] splitClassLoaderNames;
    public SparseArray<int[]> splitDependencies;
    public String[] splitNames;
    public String[] splitPublicSourceDirs;
    public String[] splitSourceDirs;
    public UUID storageUuid;
    @SystemApi
    public int targetSandboxVersion;
    public int targetSdkVersion;
    public String taskAffinity;
    public int theme;
    public int uiOptions;
    public int uid;
    @Deprecated
    @UnsupportedAppUsage
    public int versionCode;
    @Deprecated
    public String volumeUuid;
    public String zygotePreloadName;

    public ApplicationInfo() {
        this.fullBackupContent = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.mHiddenApiPolicy = -1;
    }

    public ApplicationInfo(ApplicationInfo applicationInfo) {
        super(applicationInfo);
        this.fullBackupContent = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.mHiddenApiPolicy = -1;
        this.taskAffinity = applicationInfo.taskAffinity;
        this.permission = applicationInfo.permission;
        this.processName = applicationInfo.processName;
        this.className = applicationInfo.className;
        this.theme = applicationInfo.theme;
        this.flags = applicationInfo.flags;
        this.privateFlags = applicationInfo.privateFlags;
        this.requiresSmallestWidthDp = applicationInfo.requiresSmallestWidthDp;
        this.compatibleWidthLimitDp = applicationInfo.compatibleWidthLimitDp;
        this.largestWidthLimitDp = applicationInfo.largestWidthLimitDp;
        this.volumeUuid = applicationInfo.volumeUuid;
        this.storageUuid = applicationInfo.storageUuid;
        this.scanSourceDir = applicationInfo.scanSourceDir;
        this.scanPublicSourceDir = applicationInfo.scanPublicSourceDir;
        this.sourceDir = applicationInfo.sourceDir;
        this.publicSourceDir = applicationInfo.publicSourceDir;
        this.splitNames = applicationInfo.splitNames;
        this.splitSourceDirs = applicationInfo.splitSourceDirs;
        this.splitPublicSourceDirs = applicationInfo.splitPublicSourceDirs;
        this.splitDependencies = applicationInfo.splitDependencies;
        this.nativeLibraryDir = applicationInfo.nativeLibraryDir;
        this.secondaryNativeLibraryDir = applicationInfo.secondaryNativeLibraryDir;
        this.nativeLibraryRootDir = applicationInfo.nativeLibraryRootDir;
        this.nativeLibraryRootRequiresIsa = applicationInfo.nativeLibraryRootRequiresIsa;
        this.primaryCpuAbi = applicationInfo.primaryCpuAbi;
        this.secondaryCpuAbi = applicationInfo.secondaryCpuAbi;
        this.resourceDirs = applicationInfo.resourceDirs;
        this.seInfo = applicationInfo.seInfo;
        this.seInfoUser = applicationInfo.seInfoUser;
        this.sharedLibraryFiles = applicationInfo.sharedLibraryFiles;
        this.sharedLibraryInfos = applicationInfo.sharedLibraryInfos;
        this.dataDir = applicationInfo.dataDir;
        this.deviceProtectedDataDir = applicationInfo.deviceProtectedDataDir;
        this.credentialProtectedDataDir = applicationInfo.credentialProtectedDataDir;
        this.uid = applicationInfo.uid;
        this.minSdkVersion = applicationInfo.minSdkVersion;
        this.targetSdkVersion = applicationInfo.targetSdkVersion;
        this.setVersionCode(applicationInfo.longVersionCode);
        this.enabled = applicationInfo.enabled;
        this.enabledSetting = applicationInfo.enabledSetting;
        this.installLocation = applicationInfo.installLocation;
        this.manageSpaceActivityName = applicationInfo.manageSpaceActivityName;
        this.descriptionRes = applicationInfo.descriptionRes;
        this.uiOptions = applicationInfo.uiOptions;
        this.backupAgentName = applicationInfo.backupAgentName;
        this.fullBackupContent = applicationInfo.fullBackupContent;
        this.networkSecurityConfigRes = applicationInfo.networkSecurityConfigRes;
        this.category = applicationInfo.category;
        this.targetSandboxVersion = applicationInfo.targetSandboxVersion;
        this.classLoaderName = applicationInfo.classLoaderName;
        this.splitClassLoaderNames = applicationInfo.splitClassLoaderNames;
        this.appComponentFactory = applicationInfo.appComponentFactory;
        this.iconRes = applicationInfo.iconRes;
        this.roundIconRes = applicationInfo.roundIconRes;
        this.compileSdkVersion = applicationInfo.compileSdkVersion;
        this.compileSdkVersionCodename = applicationInfo.compileSdkVersionCodename;
        this.mHiddenApiPolicy = applicationInfo.mHiddenApiPolicy;
        this.hiddenUntilInstalled = applicationInfo.hiddenUntilInstalled;
        this.zygotePreloadName = applicationInfo.zygotePreloadName;
    }

    private ApplicationInfo(Parcel parcel) {
        super(parcel);
        boolean bl = false;
        this.fullBackupContent = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.mHiddenApiPolicy = -1;
        this.taskAffinity = parcel.readString();
        this.permission = parcel.readString();
        this.processName = parcel.readString();
        this.className = parcel.readString();
        this.theme = parcel.readInt();
        this.flags = parcel.readInt();
        this.privateFlags = parcel.readInt();
        this.requiresSmallestWidthDp = parcel.readInt();
        this.compatibleWidthLimitDp = parcel.readInt();
        this.largestWidthLimitDp = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.storageUuid = new UUID(parcel.readLong(), parcel.readLong());
            this.volumeUuid = StorageManager.convert(this.storageUuid);
        }
        this.scanSourceDir = parcel.readString();
        this.scanPublicSourceDir = parcel.readString();
        this.sourceDir = parcel.readString();
        this.publicSourceDir = parcel.readString();
        this.splitNames = parcel.readStringArray();
        this.splitSourceDirs = parcel.readStringArray();
        this.splitPublicSourceDirs = parcel.readStringArray();
        this.splitDependencies = parcel.readSparseArray(null);
        this.nativeLibraryDir = parcel.readString();
        this.secondaryNativeLibraryDir = parcel.readString();
        this.nativeLibraryRootDir = parcel.readString();
        boolean bl2 = parcel.readInt() != 0;
        this.nativeLibraryRootRequiresIsa = bl2;
        this.primaryCpuAbi = parcel.readString();
        this.secondaryCpuAbi = parcel.readString();
        this.resourceDirs = parcel.readStringArray();
        this.seInfo = parcel.readString();
        this.seInfoUser = parcel.readString();
        this.sharedLibraryFiles = parcel.readStringArray();
        this.sharedLibraryInfos = parcel.createTypedArrayList(SharedLibraryInfo.CREATOR);
        this.dataDir = parcel.readString();
        this.deviceProtectedDataDir = parcel.readString();
        this.credentialProtectedDataDir = parcel.readString();
        this.uid = parcel.readInt();
        this.minSdkVersion = parcel.readInt();
        this.targetSdkVersion = parcel.readInt();
        this.setVersionCode(parcel.readLong());
        bl2 = parcel.readInt() != 0;
        this.enabled = bl2;
        this.enabledSetting = parcel.readInt();
        this.installLocation = parcel.readInt();
        this.manageSpaceActivityName = parcel.readString();
        this.backupAgentName = parcel.readString();
        this.descriptionRes = parcel.readInt();
        this.uiOptions = parcel.readInt();
        this.fullBackupContent = parcel.readInt();
        this.networkSecurityConfigRes = parcel.readInt();
        this.category = parcel.readInt();
        this.targetSandboxVersion = parcel.readInt();
        this.classLoaderName = parcel.readString();
        this.splitClassLoaderNames = parcel.readStringArray();
        this.compileSdkVersion = parcel.readInt();
        this.compileSdkVersionCodename = parcel.readString();
        this.appComponentFactory = parcel.readString();
        this.iconRes = parcel.readInt();
        this.roundIconRes = parcel.readInt();
        this.mHiddenApiPolicy = parcel.readInt();
        bl2 = bl;
        if (parcel.readInt() != 0) {
            bl2 = true;
        }
        this.hiddenUntilInstalled = bl2;
        this.zygotePreloadName = parcel.readString();
    }

    public static CharSequence getCategoryTitle(Context context, int n) {
        switch (n) {
            default: {
                return null;
            }
            case 7: {
                return context.getText(17039507);
            }
            case 6: {
                return context.getText(17039505);
            }
            case 5: {
                return context.getText(17039506);
            }
            case 4: {
                return context.getText(17039508);
            }
            case 3: {
                return context.getText(17039504);
            }
            case 2: {
                return context.getText(17039509);
            }
            case 1: {
                return context.getText(17039502);
            }
            case 0: 
        }
        return context.getText(17039503);
    }

    private boolean isAllowedToUseHiddenApis() {
        boolean bl = this.isSignedWithPlatformKey();
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (!this.isSystemApp() && !this.isUpdatedSystemApp()) {
            return false;
        }
        bl = bl2;
        if (!this.usesNonSdkApi()) {
            bl = this.isPackageWhitelistedForHiddenApis() ? bl2 : false;
        }
        return bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private boolean isPackageUnavailable(PackageManager object) {
        boolean bl = true;
        try {
            object = ((PackageManager)object).getPackageInfo(this.packageName, 0);
            if (object != null) {
                bl = false;
            }
            return bl;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return true;
        }
    }

    private boolean isPackageWhitelistedForHiddenApis() {
        return SystemConfig.getInstance().getHiddenApiWhitelistedApps().contains(this.packageName);
    }

    public static boolean isValidHiddenApiEnforcementPolicy(int n) {
        boolean bl = n >= -1 && n <= 2;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void disableCompatibilityMode() {
        this.flags |= 540160;
    }

    public void dump(Printer printer, String string2) {
        this.dump(printer, string2, 3);
    }

    public void dump(Printer printer, String string2, int n) {
        CharSequence charSequence;
        super.dumpFront(printer, string2);
        if ((n & 1) != 0 && this.className != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("className=");
            ((StringBuilder)charSequence).append(this.className);
            printer.println(((StringBuilder)charSequence).toString());
        }
        if (this.permission != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("permission=");
            ((StringBuilder)charSequence).append(this.permission);
            printer.println(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("processName=");
        ((StringBuilder)charSequence).append(this.processName);
        printer.println(((StringBuilder)charSequence).toString());
        if ((n & 1) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("taskAffinity=");
            ((StringBuilder)charSequence).append(this.taskAffinity);
            printer.println(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("uid=");
        ((StringBuilder)charSequence).append(this.uid);
        ((StringBuilder)charSequence).append(" flags=0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(this.flags));
        ((StringBuilder)charSequence).append(" privateFlags=0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(this.privateFlags));
        ((StringBuilder)charSequence).append(" theme=0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(this.theme));
        printer.println(((StringBuilder)charSequence).toString());
        if ((n & 1) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("requiresSmallestWidthDp=");
            ((StringBuilder)charSequence).append(this.requiresSmallestWidthDp);
            ((StringBuilder)charSequence).append(" compatibleWidthLimitDp=");
            ((StringBuilder)charSequence).append(this.compatibleWidthLimitDp);
            ((StringBuilder)charSequence).append(" largestWidthLimitDp=");
            ((StringBuilder)charSequence).append(this.largestWidthLimitDp);
            printer.println(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("sourceDir=");
        ((StringBuilder)charSequence).append(this.sourceDir);
        printer.println(((StringBuilder)charSequence).toString());
        if (!Objects.equals(this.sourceDir, this.publicSourceDir)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("publicSourceDir=");
            ((StringBuilder)charSequence).append(this.publicSourceDir);
            printer.println(((StringBuilder)charSequence).toString());
        }
        if (!ArrayUtils.isEmpty(this.splitSourceDirs)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("splitSourceDirs=");
            ((StringBuilder)charSequence).append(Arrays.toString(this.splitSourceDirs));
            printer.println(((StringBuilder)charSequence).toString());
        }
        if (!ArrayUtils.isEmpty(this.splitPublicSourceDirs) && !Arrays.equals(this.splitSourceDirs, this.splitPublicSourceDirs)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("splitPublicSourceDirs=");
            ((StringBuilder)charSequence).append(Arrays.toString(this.splitPublicSourceDirs));
            printer.println(((StringBuilder)charSequence).toString());
        }
        if (this.resourceDirs != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("resourceDirs=");
            ((StringBuilder)charSequence).append(Arrays.toString(this.resourceDirs));
            printer.println(((StringBuilder)charSequence).toString());
        }
        if ((n & 1) != 0 && this.seInfo != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("seinfo=");
            ((StringBuilder)charSequence).append(this.seInfo);
            printer.println(((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("seinfoUser=");
            ((StringBuilder)charSequence).append(this.seInfoUser);
            printer.println(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("dataDir=");
        ((StringBuilder)charSequence).append(this.dataDir);
        printer.println(((StringBuilder)charSequence).toString());
        if ((n & 1) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("deviceProtectedDataDir=");
            ((StringBuilder)charSequence).append(this.deviceProtectedDataDir);
            printer.println(((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("credentialProtectedDataDir=");
            ((StringBuilder)charSequence).append(this.credentialProtectedDataDir);
            printer.println(((StringBuilder)charSequence).toString());
            if (this.sharedLibraryFiles != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("sharedLibraryFiles=");
                ((StringBuilder)charSequence).append(Arrays.toString(this.sharedLibraryFiles));
                printer.println(((StringBuilder)charSequence).toString());
            }
        }
        if (this.classLoaderName != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("classLoaderName=");
            ((StringBuilder)charSequence).append(this.classLoaderName);
            printer.println(((StringBuilder)charSequence).toString());
        }
        if (!ArrayUtils.isEmpty(this.splitClassLoaderNames)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("splitClassLoaderNames=");
            ((StringBuilder)charSequence).append(Arrays.toString(this.splitClassLoaderNames));
            printer.println(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("enabled=");
        ((StringBuilder)charSequence).append(this.enabled);
        ((StringBuilder)charSequence).append(" minSdkVersion=");
        ((StringBuilder)charSequence).append(this.minSdkVersion);
        ((StringBuilder)charSequence).append(" targetSdkVersion=");
        ((StringBuilder)charSequence).append(this.targetSdkVersion);
        ((StringBuilder)charSequence).append(" versionCode=");
        ((StringBuilder)charSequence).append(this.longVersionCode);
        ((StringBuilder)charSequence).append(" targetSandboxVersion=");
        ((StringBuilder)charSequence).append(this.targetSandboxVersion);
        printer.println(((StringBuilder)charSequence).toString());
        if ((n & 1) != 0) {
            if (this.manageSpaceActivityName != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("manageSpaceActivityName=");
                ((StringBuilder)charSequence).append(this.manageSpaceActivityName);
                printer.println(((StringBuilder)charSequence).toString());
            }
            if (this.descriptionRes != 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("description=0x");
                ((StringBuilder)charSequence).append(Integer.toHexString(this.descriptionRes));
                printer.println(((StringBuilder)charSequence).toString());
            }
            if (this.uiOptions != 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("uiOptions=0x");
                ((StringBuilder)charSequence).append(Integer.toHexString(this.uiOptions));
                printer.println(((StringBuilder)charSequence).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("supportsRtl=");
            boolean bl = this.hasRtlSupport();
            String string3 = "true";
            charSequence = bl ? "true" : "false";
            stringBuilder.append((String)charSequence);
            printer.println(stringBuilder.toString());
            if (this.fullBackupContent > 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("fullBackupContent=@xml/");
                ((StringBuilder)charSequence).append(this.fullBackupContent);
                printer.println(((StringBuilder)charSequence).toString());
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append("fullBackupContent=");
                charSequence = this.fullBackupContent < 0 ? "false" : "true";
                stringBuilder.append((String)charSequence);
                printer.println(stringBuilder.toString());
            }
            if (this.networkSecurityConfigRes != 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("networkSecurityConfigRes=0x");
                ((StringBuilder)charSequence).append(Integer.toHexString(this.networkSecurityConfigRes));
                printer.println(((StringBuilder)charSequence).toString());
            }
            if (this.category != -1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("category=");
                ((StringBuilder)charSequence).append(this.category);
                printer.println(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("HiddenApiEnforcementPolicy=");
            ((StringBuilder)charSequence).append(this.getHiddenApiEnforcementPolicy());
            printer.println(((StringBuilder)charSequence).toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("usesNonSdkApi=");
            ((StringBuilder)charSequence).append(this.usesNonSdkApi());
            printer.println(((StringBuilder)charSequence).toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("allowsPlaybackCapture=");
            charSequence = this.isAudioPlaybackCaptureAllowed() ? string3 : "false";
            stringBuilder.append((String)charSequence);
            printer.println(stringBuilder.toString());
        }
        super.dumpBack(printer, string2);
    }

    public String[] getAllApkPaths() {
        String[][] arrarrstring = new String[][]{this.splitSourceDirs, this.sharedLibraryFiles, this.resourceDirs};
        ArrayList<Object> arrayList = new ArrayList<Object>(10);
        String[] arrstring2 = this.sourceDir;
        if (arrstring2 != null) {
            arrayList.add(arrstring2);
        }
        for (String[] arrstring2 : arrarrstring) {
            if (arrstring2 == null) continue;
            int n = arrstring2.length;
            for (int i = 0; i < n; ++i) {
                arrayList.add(arrstring2[i]);
            }
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    @Override
    protected ApplicationInfo getApplicationInfo() {
        return this;
    }

    public String getBaseCodePath() {
        return this.sourceDir;
    }

    @UnsupportedAppUsage
    public String getBaseResourcePath() {
        return this.publicSourceDir;
    }

    @UnsupportedAppUsage
    public String getCodePath() {
        return this.scanSourceDir;
    }

    public int getHiddenApiEnforcementPolicy() {
        if (this.isAllowedToUseHiddenApis()) {
            return 0;
        }
        int n = this.mHiddenApiPolicy;
        if (n != -1) {
            return n;
        }
        return 2;
    }

    public String getResourcePath() {
        return this.scanPublicSourceDir;
    }

    public String[] getSplitCodePaths() {
        return this.splitSourceDirs;
    }

    public String[] getSplitResourcePaths() {
        return this.splitPublicSourceDirs;
    }

    public boolean hasCode() {
        boolean bl = (this.flags & 4) != 0;
        return bl;
    }

    public boolean hasFragileUserData() {
        boolean bl = (this.privateFlags & 16777216) != 0;
        return bl;
    }

    public boolean hasRequestedLegacyExternalStorage() {
        boolean bl = (this.privateFlags & 536870912) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean hasRtlSupport() {
        boolean bl = (this.flags & 4194304) == 4194304;
        return bl;
    }

    public void initForUser(int n) {
        this.uid = UserHandle.getUid(n, UserHandle.getAppId(this.uid));
        if ("android".equals(this.packageName)) {
            this.dataDir = Environment.getDataSystemDirectory().getAbsolutePath();
            return;
        }
        this.deviceProtectedDataDir = Environment.getDataUserDePackageDirectory(this.volumeUuid, n, this.packageName).getAbsolutePath();
        this.credentialProtectedDataDir = Environment.getDataUserCePackageDirectory(this.volumeUuid, n, this.packageName).getAbsolutePath();
        this.dataDir = (this.privateFlags & 32) != 0 ? this.deviceProtectedDataDir : this.credentialProtectedDataDir;
    }

    public boolean isAudioPlaybackCaptureAllowed() {
        boolean bl = (this.privateFlags & 134217728) != 0;
        return bl;
    }

    public boolean isDefaultToDeviceProtectedStorage() {
        boolean bl = (this.privateFlags & 32) != 0;
        return bl;
    }

    public boolean isDirectBootAware() {
        boolean bl = (this.privateFlags & 64) != 0;
        return bl;
    }

    public boolean isEmbeddedDexUsed() {
        boolean bl = (this.privateFlags & 33554432) != 0;
        return bl;
    }

    @SystemApi
    public boolean isEncryptionAware() {
        boolean bl = this.isDirectBootAware() || this.isPartiallyDirectBootAware();
        return bl;
    }

    public boolean isExternal() {
        boolean bl = (this.flags & 262144) != 0;
        return bl;
    }

    @SystemApi
    public boolean isInstantApp() {
        boolean bl = (this.privateFlags & 128) != 0;
        return bl;
    }

    public boolean isInternal() {
        boolean bl = (this.flags & 262144) == 0;
        return bl;
    }

    public boolean isOdm() {
        boolean bl = (this.privateFlags & 1073741824) != 0;
        return bl;
    }

    public boolean isOem() {
        boolean bl = (this.privateFlags & 131072) != 0;
        return bl;
    }

    public boolean isPartiallyDirectBootAware() {
        boolean bl = (this.privateFlags & 256) != 0;
        return bl;
    }

    public boolean isPrivilegedApp() {
        boolean bl = (this.privateFlags & 8) != 0;
        return bl;
    }

    public boolean isProduct() {
        boolean bl = (this.privateFlags & 524288) != 0;
        return bl;
    }

    public boolean isProductServices() {
        boolean bl = (this.privateFlags & 2097152) != 0;
        return bl;
    }

    public boolean isProfileableByShell() {
        boolean bl = (this.privateFlags & 8388608) != 0;
        return bl;
    }

    public boolean isRequiredForSystemUser() {
        boolean bl = (this.privateFlags & 512) != 0;
        return bl;
    }

    public boolean isResourceOverlay() {
        boolean bl = (this.privateFlags & 268435456) != 0;
        return bl;
    }

    public boolean isSignedWithPlatformKey() {
        boolean bl = (this.privateFlags & 1048576) != 0;
        return bl;
    }

    public boolean isStaticSharedLibrary() {
        boolean bl = (this.privateFlags & 16384) != 0;
        return bl;
    }

    public boolean isSystemApp() {
        int n = this.flags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isUpdatedSystemApp() {
        boolean bl = (this.flags & 128) != 0;
        return bl;
    }

    public boolean isVendor() {
        boolean bl = (this.privateFlags & 262144) != 0;
        return bl;
    }

    public boolean isVirtualPreload() {
        boolean bl = (this.privateFlags & 65536) != 0;
        return bl;
    }

    @Override
    public Drawable loadDefaultIcon(PackageManager packageManager) {
        if ((this.flags & 262144) != 0 && this.isPackageUnavailable(packageManager)) {
            return Resources.getSystem().getDrawable(17303627);
        }
        return packageManager.getDefaultActivityIcon();
    }

    public CharSequence loadDescription(PackageManager object) {
        if (this.descriptionRes != 0 && (object = ((PackageManager)object).getText(this.packageName, this.descriptionRes, this)) != null) {
            return object;
        }
        return null;
    }

    public void maybeUpdateHiddenApiEnforcementPolicy(int n) {
        if (this.isPackageWhitelistedForHiddenApis()) {
            return;
        }
        this.setHiddenApiEnforcementPolicy(n);
    }

    public boolean requestsIsolatedSplitLoading() {
        boolean bl = (this.privateFlags & 32768) != 0;
        return bl;
    }

    public void setBaseCodePath(String string2) {
        this.sourceDir = string2;
    }

    public void setBaseResourcePath(String string2) {
        this.publicSourceDir = string2;
    }

    public void setCodePath(String string2) {
        this.scanSourceDir = string2;
    }

    public void setHiddenApiEnforcementPolicy(int n) {
        if (ApplicationInfo.isValidHiddenApiEnforcementPolicy(n)) {
            this.mHiddenApiPolicy = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid API enforcement policy: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setResourcePath(String string2) {
        this.scanPublicSourceDir = string2;
    }

    public void setSplitCodePaths(String[] arrstring) {
        this.splitSourceDirs = arrstring;
    }

    public void setSplitResourcePaths(String[] arrstring) {
        this.splitPublicSourceDirs = arrstring;
    }

    public void setVersionCode(long l) {
        this.longVersionCode = l;
        this.versionCode = (int)l;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ApplicationInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append(this.packageName);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean usesCompatibilityMode() {
        boolean bl = this.targetSdkVersion < 4 || (this.flags & 540160) == 0;
        return bl;
    }

    public boolean usesNonSdkApi() {
        boolean bl = (this.privateFlags & 4194304) != 0;
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.taskAffinity);
        parcel.writeString(this.permission);
        parcel.writeString(this.processName);
        parcel.writeString(this.className);
        parcel.writeInt(this.theme);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.privateFlags);
        parcel.writeInt(this.requiresSmallestWidthDp);
        parcel.writeInt(this.compatibleWidthLimitDp);
        parcel.writeInt(this.largestWidthLimitDp);
        if (this.storageUuid != null) {
            parcel.writeInt(1);
            parcel.writeLong(this.storageUuid.getMostSignificantBits());
            parcel.writeLong(this.storageUuid.getLeastSignificantBits());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.scanSourceDir);
        parcel.writeString(this.scanPublicSourceDir);
        parcel.writeString(this.sourceDir);
        parcel.writeString(this.publicSourceDir);
        parcel.writeStringArray(this.splitNames);
        parcel.writeStringArray(this.splitSourceDirs);
        parcel.writeStringArray(this.splitPublicSourceDirs);
        parcel.writeSparseArray(this.splitDependencies);
        parcel.writeString(this.nativeLibraryDir);
        parcel.writeString(this.secondaryNativeLibraryDir);
        parcel.writeString(this.nativeLibraryRootDir);
        parcel.writeInt((int)this.nativeLibraryRootRequiresIsa);
        parcel.writeString(this.primaryCpuAbi);
        parcel.writeString(this.secondaryCpuAbi);
        parcel.writeStringArray(this.resourceDirs);
        parcel.writeString(this.seInfo);
        parcel.writeString(this.seInfoUser);
        parcel.writeStringArray(this.sharedLibraryFiles);
        parcel.writeTypedList(this.sharedLibraryInfos);
        parcel.writeString(this.dataDir);
        parcel.writeString(this.deviceProtectedDataDir);
        parcel.writeString(this.credentialProtectedDataDir);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.minSdkVersion);
        parcel.writeInt(this.targetSdkVersion);
        parcel.writeLong(this.longVersionCode);
        parcel.writeInt((int)this.enabled);
        parcel.writeInt(this.enabledSetting);
        parcel.writeInt(this.installLocation);
        parcel.writeString(this.manageSpaceActivityName);
        parcel.writeString(this.backupAgentName);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.uiOptions);
        parcel.writeInt(this.fullBackupContent);
        parcel.writeInt(this.networkSecurityConfigRes);
        parcel.writeInt(this.category);
        parcel.writeInt(this.targetSandboxVersion);
        parcel.writeString(this.classLoaderName);
        parcel.writeStringArray(this.splitClassLoaderNames);
        parcel.writeInt(this.compileSdkVersion);
        parcel.writeString(this.compileSdkVersionCodename);
        parcel.writeString(this.appComponentFactory);
        parcel.writeInt(this.iconRes);
        parcel.writeInt(this.roundIconRes);
        parcel.writeInt(this.mHiddenApiPolicy);
        parcel.writeInt((int)this.hiddenUntilInstalled);
        parcel.writeString(this.zygotePreloadName);
    }

    @Override
    public void writeToProto(ProtoOutputStream protoOutputStream, long l, int n) {
        int n2;
        Object object;
        int n3;
        l = protoOutputStream.start(l);
        super.writeToProto(protoOutputStream, 1146756268033L, n);
        protoOutputStream.write(1138166333442L, this.permission);
        protoOutputStream.write(1138166333443L, this.processName);
        protoOutputStream.write(1120986464260L, this.uid);
        protoOutputStream.write(1120986464261L, this.flags);
        protoOutputStream.write(1120986464262L, this.privateFlags);
        protoOutputStream.write(1120986464263L, this.theme);
        protoOutputStream.write(1138166333448L, this.sourceDir);
        if (!Objects.equals(this.sourceDir, this.publicSourceDir)) {
            protoOutputStream.write(1138166333449L, this.publicSourceDir);
        }
        boolean bl = ArrayUtils.isEmpty(this.splitSourceDirs);
        boolean bl2 = false;
        if (!bl) {
            object = this.splitSourceDirs;
            n2 = ((String[])object).length;
            for (n3 = 0; n3 < n2; ++n3) {
                protoOutputStream.write(2237677961226L, object[n3]);
            }
        }
        if (!ArrayUtils.isEmpty(this.splitPublicSourceDirs) && !Arrays.equals(this.splitSourceDirs, this.splitPublicSourceDirs)) {
            object = this.splitPublicSourceDirs;
            n2 = ((String[])object).length;
            for (n3 = 0; n3 < n2; ++n3) {
                protoOutputStream.write(2237677961227L, object[n3]);
            }
        }
        if ((object = this.resourceDirs) != null) {
            n2 = ((String[])object).length;
            for (n3 = 0; n3 < n2; ++n3) {
                protoOutputStream.write(2237677961228L, object[n3]);
            }
        }
        protoOutputStream.write(1138166333453L, this.dataDir);
        protoOutputStream.write(1138166333454L, this.classLoaderName);
        if (!ArrayUtils.isEmpty(this.splitClassLoaderNames)) {
            object = this.splitClassLoaderNames;
            n2 = ((String[])object).length;
            for (n3 = 0; n3 < n2; ++n3) {
                protoOutputStream.write(2237677961231L, object[n3]);
            }
        }
        long l2 = protoOutputStream.start(1146756268048L);
        protoOutputStream.write(1133871366145L, this.enabled);
        protoOutputStream.write(1120986464258L, this.minSdkVersion);
        protoOutputStream.write(1120986464259L, this.targetSdkVersion);
        protoOutputStream.write(1120986464260L, this.longVersionCode);
        protoOutputStream.write(1120986464261L, this.targetSandboxVersion);
        protoOutputStream.end(l2);
        if ((n & 1) != 0) {
            l2 = protoOutputStream.start(1146756268049L);
            object = this.className;
            if (object != null) {
                protoOutputStream.write(1138166333441L, (String)object);
            }
            protoOutputStream.write(1138166333442L, this.taskAffinity);
            protoOutputStream.write(1120986464259L, this.requiresSmallestWidthDp);
            protoOutputStream.write(1120986464260L, this.compatibleWidthLimitDp);
            protoOutputStream.write(1120986464261L, this.largestWidthLimitDp);
            object = this.seInfo;
            if (object != null) {
                protoOutputStream.write(1138166333446L, (String)object);
                protoOutputStream.write(1138166333447L, this.seInfoUser);
            }
            protoOutputStream.write(1138166333448L, this.deviceProtectedDataDir);
            protoOutputStream.write(1138166333449L, this.credentialProtectedDataDir);
            object = this.sharedLibraryFiles;
            if (object != null) {
                n3 = ((String[])object).length;
                for (n = 0; n < n3; ++n) {
                    protoOutputStream.write(2237677961226L, object[n]);
                }
            }
            if ((object = this.manageSpaceActivityName) != null) {
                protoOutputStream.write(1138166333451L, (String)object);
            }
            if ((n = this.descriptionRes) != 0) {
                protoOutputStream.write(1120986464268L, n);
            }
            if ((n = this.uiOptions) != 0) {
                protoOutputStream.write(1120986464269L, n);
            }
            protoOutputStream.write(1133871366158L, this.hasRtlSupport());
            n = this.fullBackupContent;
            if (n > 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("@xml/");
                ((StringBuilder)object).append(this.fullBackupContent);
                protoOutputStream.write(1138166333455L, ((StringBuilder)object).toString());
            } else {
                if (n == 0) {
                    bl2 = true;
                }
                protoOutputStream.write(1133871366160L, bl2);
            }
            n = this.networkSecurityConfigRes;
            if (n != 0) {
                protoOutputStream.write(1120986464273L, n);
            }
            if ((n = this.category) != -1) {
                protoOutputStream.write(1120986464274L, n);
            }
            protoOutputStream.end(l2);
        }
        protoOutputStream.end(l);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ApplicationInfoPrivateFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Category {
    }

    public static class DisplayNameComparator
    implements Comparator<ApplicationInfo> {
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        private PackageManager mPM;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        private final Collator sCollator = Collator.getInstance();

        public DisplayNameComparator(PackageManager packageManager) {
            this.mPM = packageManager;
        }

        @Override
        public final int compare(ApplicationInfo object, ApplicationInfo applicationInfo) {
            CharSequence charSequence;
            CharSequence charSequence2 = charSequence = this.mPM.getApplicationLabel((ApplicationInfo)object);
            if (charSequence == null) {
                charSequence2 = ((ApplicationInfo)object).packageName;
            }
            charSequence = this.mPM.getApplicationLabel(applicationInfo);
            object = charSequence;
            if (charSequence == null) {
                object = applicationInfo.packageName;
            }
            return this.sCollator.compare(charSequence2.toString(), object.toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HiddenApiEnforcementPolicy {
    }

}

