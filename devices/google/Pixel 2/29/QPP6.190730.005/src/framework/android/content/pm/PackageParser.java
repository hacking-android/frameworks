/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.pm.-$
 *  android.content.pm.-$$Lambda
 *  android.content.pm.-$$Lambda$PackageParser
 *  android.content.pm.-$$Lambda$PackageParser$0DZRgzfgaIMpCOhJqjw6PUiU5vw
 *  android.content.pm.-$$Lambda$PackageParser$0aobsT7Zf7WVZCqMZ5z2clAuQf4
 *  android.content.pm.-$$Lambda$PackageParser$M-9fHqS_eEp1oYkuKJhRHOGUxf8
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  libcore.io.IoUtils
 *  libcore.util.EmptyArray
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.apex.ApexInfo;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.ResourcesManager;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.-$;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FallbackCategoryProvider;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageParserCacheHelper;
import android.content.pm.PackageUserState;
import android.content.pm.PathPermission;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.SELinuxUtil;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.content.pm.VerifierInfo;
import android.content.pm._$$Lambda$PackageParser$0DZRgzfgaIMpCOhJqjw6PUiU5vw;
import android.content.pm._$$Lambda$PackageParser$0aobsT7Zf7WVZCqMZ5z2clAuQf4;
import android.content.pm._$$Lambda$PackageParser$M_9fHqS_eEp1oYkuKJhRHOGUxf8;
import android.content.pm.split.DefaultSplitAssetLoader;
import android.content.pm.split.SplitAssetDependencyLoader;
import android.content.pm.split.SplitAssetLoader;
import android.content.pm.split.SplitDependencyLoader;
import android.content.res.AssetManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.BaseBundle;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.permission.PermissionManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.ByteStringUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.PackageUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.apk.ApkSignatureVerifier;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.ClassLoaderFactory;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class PackageParser {
    public static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    private static final String ANDROID_RESOURCES = "http://schemas.android.com/apk/res/android";
    public static final String APK_FILE_EXTENSION = ".apk";
    private static final Set<String> CHILD_PACKAGE_TAGS;
    private static final boolean DEBUG_BACKUP = false;
    private static final boolean DEBUG_JAR = false;
    private static final boolean DEBUG_PARSER = false;
    private static final int DEFAULT_MIN_SDK_VERSION = 1;
    private static final float DEFAULT_PRE_O_MAX_ASPECT_RATIO = 1.86f;
    private static final float DEFAULT_PRE_Q_MIN_ASPECT_RATIO = 1.333f;
    private static final float DEFAULT_PRE_Q_MIN_ASPECT_RATIO_WATCH = 1.0f;
    private static final int DEFAULT_TARGET_SDK_VERSION = 0;
    private static final boolean LOG_PARSE_TIMINGS;
    private static final int LOG_PARSE_TIMINGS_THRESHOLD_MS = 100;
    private static final boolean LOG_UNSAFE_BROADCASTS = false;
    private static final String METADATA_MAX_ASPECT_RATIO = "android.max_aspect";
    private static final String MNT_EXPAND = "/mnt/expand/";
    private static final boolean MULTI_PACKAGE_APK_ENABLED;
    @UnsupportedAppUsage
    public static final NewPermissionInfo[] NEW_PERMISSIONS;
    public static final int PARSE_CHATTY = Integer.MIN_VALUE;
    public static final int PARSE_COLLECT_CERTIFICATES = 32;
    private static final int PARSE_DEFAULT_INSTALL_LOCATION = -1;
    private static final int PARSE_DEFAULT_TARGET_SANDBOX = 1;
    public static final int PARSE_ENFORCE_CODE = 64;
    public static final int PARSE_EXTERNAL_STORAGE = 8;
    public static final int PARSE_IGNORE_PROCESSES = 2;
    public static final int PARSE_IS_SYSTEM_DIR = 16;
    public static final int PARSE_MUST_BE_APK = 1;
    private static final String PROPERTY_CHILD_PACKAGES_ENABLED = "persist.sys.child_packages_enabled";
    private static final int RECREATE_ON_CONFIG_CHANGES_MASK = 3;
    private static final boolean RIGID_PARSER = false;
    private static final Set<String> SAFE_BROADCASTS;
    private static final String[] SDK_CODENAMES;
    private static final int SDK_VERSION;
    private static final String TAG = "PackageParser";
    private static final String TAG_ADOPT_PERMISSIONS = "adopt-permissions";
    private static final String TAG_APPLICATION = "application";
    private static final String TAG_COMPATIBLE_SCREENS = "compatible-screens";
    private static final String TAG_EAT_COMMENT = "eat-comment";
    private static final String TAG_FEATURE_GROUP = "feature-group";
    private static final String TAG_INSTRUMENTATION = "instrumentation";
    private static final String TAG_KEY_SETS = "key-sets";
    private static final String TAG_MANIFEST = "manifest";
    private static final String TAG_ORIGINAL_PACKAGE = "original-package";
    private static final String TAG_OVERLAY = "overlay";
    private static final String TAG_PACKAGE = "package";
    private static final String TAG_PACKAGE_VERIFIER = "package-verifier";
    private static final String TAG_PERMISSION = "permission";
    private static final String TAG_PERMISSION_GROUP = "permission-group";
    private static final String TAG_PERMISSION_TREE = "permission-tree";
    private static final String TAG_PROTECTED_BROADCAST = "protected-broadcast";
    private static final String TAG_RESTRICT_UPDATE = "restrict-update";
    private static final String TAG_SUPPORTS_INPUT = "supports-input";
    private static final String TAG_SUPPORT_SCREENS = "supports-screens";
    private static final String TAG_USES_CONFIGURATION = "uses-configuration";
    private static final String TAG_USES_FEATURE = "uses-feature";
    private static final String TAG_USES_GL_TEXTURE = "uses-gl-texture";
    private static final String TAG_USES_PERMISSION = "uses-permission";
    private static final String TAG_USES_PERMISSION_SDK_23 = "uses-permission-sdk-23";
    private static final String TAG_USES_PERMISSION_SDK_M = "uses-permission-sdk-m";
    private static final String TAG_USES_SDK = "uses-sdk";
    private static final String TAG_USES_SPLIT = "uses-split";
    public static final AtomicInteger sCachedPackageReadCount;
    private static boolean sCompatibilityModeEnabled;
    private static final Comparator<String> sSplitNameComparator;
    private static boolean sUseRoundIcon;
    @Deprecated
    private String mArchiveSourcePath;
    private File mCacheDir;
    @UnsupportedAppUsage
    private Callback mCallback;
    private DisplayMetrics mMetrics = new DisplayMetrics();
    private boolean mOnlyCoreApps;
    private int mParseError = 1;
    private ParsePackageItemArgs mParseInstrumentationArgs;
    private String[] mSeparateProcesses;

    static {
        LOG_PARSE_TIMINGS = Build.IS_DEBUGGABLE;
        boolean bl = Build.IS_DEBUGGABLE && SystemProperties.getBoolean(PROPERTY_CHILD_PACKAGES_ENABLED, false);
        MULTI_PACKAGE_APK_ENABLED = bl;
        CHILD_PACKAGE_TAGS = new ArraySet<String>();
        CHILD_PACKAGE_TAGS.add(TAG_APPLICATION);
        CHILD_PACKAGE_TAGS.add(TAG_USES_PERMISSION);
        CHILD_PACKAGE_TAGS.add(TAG_USES_PERMISSION_SDK_M);
        CHILD_PACKAGE_TAGS.add(TAG_USES_PERMISSION_SDK_23);
        CHILD_PACKAGE_TAGS.add(TAG_USES_CONFIGURATION);
        CHILD_PACKAGE_TAGS.add(TAG_USES_FEATURE);
        CHILD_PACKAGE_TAGS.add(TAG_FEATURE_GROUP);
        CHILD_PACKAGE_TAGS.add(TAG_USES_SDK);
        CHILD_PACKAGE_TAGS.add(TAG_SUPPORT_SCREENS);
        CHILD_PACKAGE_TAGS.add(TAG_INSTRUMENTATION);
        CHILD_PACKAGE_TAGS.add(TAG_USES_GL_TEXTURE);
        CHILD_PACKAGE_TAGS.add(TAG_COMPATIBLE_SCREENS);
        CHILD_PACKAGE_TAGS.add(TAG_SUPPORTS_INPUT);
        CHILD_PACKAGE_TAGS.add(TAG_EAT_COMMENT);
        sCachedPackageReadCount = new AtomicInteger();
        SAFE_BROADCASTS = new ArraySet<String>();
        SAFE_BROADCASTS.add("android.intent.action.BOOT_COMPLETED");
        NEW_PERMISSIONS = new NewPermissionInfo[]{new NewPermissionInfo("android.permission.WRITE_EXTERNAL_STORAGE", 4, 0), new NewPermissionInfo("android.permission.READ_PHONE_STATE", 4, 0)};
        SDK_VERSION = Build.VERSION.SDK_INT;
        SDK_CODENAMES = Build.VERSION.ACTIVE_CODENAMES;
        sCompatibilityModeEnabled = true;
        sUseRoundIcon = false;
        sSplitNameComparator = new SplitNameComparator();
    }

    @UnsupportedAppUsage
    public PackageParser() {
        this.mMetrics.setToDefaults();
    }

    private void adjustPackageToBeUnresizeableAndUnpipable(Package object) {
        for (Parcelable parcelable : ((Package)object).activities) {
            parcelable.info.resizeMode = 0;
            parcelable = parcelable.info;
            ((ActivityInfo)parcelable).flags &= -4194305;
        }
    }

    private static String buildClassName(String charSequence, CharSequence charSequence2, String[] object) {
        if (charSequence2 != null && charSequence2.length() > 0) {
            if (((String)(charSequence2 = charSequence2.toString())).charAt(0) == '.') {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append((String)charSequence2);
                return ((StringBuilder)object).toString();
            }
            if (((String)charSequence2).indexOf(46) < 0) {
                charSequence = new StringBuilder((String)charSequence);
                ((StringBuilder)charSequence).append('.');
                ((StringBuilder)charSequence).append((String)charSequence2);
                return ((StringBuilder)charSequence).toString();
            }
            return charSequence2;
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("Empty class name in package ");
        ((StringBuilder)charSequence2).append((String)charSequence);
        object[0] = ((StringBuilder)charSequence2).toString();
        return null;
    }

    private static String buildCompoundName(String string2, CharSequence charSequence, String charSequence2, String[] arrstring) {
        charSequence = charSequence.toString();
        char c = ((String)charSequence).charAt(0);
        if (string2 != null && c == ':') {
            if (((String)charSequence).length() < 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bad ");
                stringBuilder.append((String)charSequence2);
                stringBuilder.append(" name ");
                stringBuilder.append((String)charSequence);
                stringBuilder.append(" in package ");
                stringBuilder.append(string2);
                stringBuilder.append(": must be at least two characters");
                arrstring[0] = stringBuilder.toString();
                return null;
            }
            String string3 = PackageParser.validateName(((String)charSequence).substring(1), false, false);
            if (string3 != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid ");
                stringBuilder.append((String)charSequence2);
                stringBuilder.append(" name ");
                stringBuilder.append((String)charSequence);
                stringBuilder.append(" in package ");
                stringBuilder.append(string2);
                stringBuilder.append(": ");
                stringBuilder.append(string3);
                arrstring[0] = stringBuilder.toString();
                return null;
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append(string2);
            ((StringBuilder)charSequence2).append((String)charSequence);
            return ((StringBuilder)charSequence2).toString();
        }
        String string4 = PackageParser.validateName((String)charSequence, true, false);
        if (string4 != null && !"system".equals(charSequence)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid ");
            stringBuilder.append((String)charSequence2);
            stringBuilder.append(" name ");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(" in package ");
            stringBuilder.append(string2);
            stringBuilder.append(": ");
            stringBuilder.append(string4);
            arrstring[0] = stringBuilder.toString();
            return null;
        }
        return charSequence;
    }

    private static String buildProcessName(String string2, String string3, CharSequence charSequence, int n, String[] arrstring, String[] arrstring2) {
        if ((n & 2) != 0 && !"system".equals(charSequence)) {
            if (string3 != null) {
                string2 = string3;
            }
            return string2;
        }
        if (arrstring != null) {
            for (n = arrstring.length - 1; n >= 0; --n) {
                String string4 = arrstring[n];
                if (!(string4.equals(string2) || string4.equals(string3) || string4.equals(charSequence))) {
                    continue;
                }
                return string2;
            }
        }
        if (charSequence != null && charSequence.length() > 0) {
            return TextUtils.safeIntern(PackageParser.buildCompoundName(string2, charSequence, "process", arrstring2));
        }
        return string3;
    }

    private static String buildTaskAffinityName(String string2, String string3, CharSequence charSequence, String[] arrstring) {
        if (charSequence == null) {
            return string3;
        }
        if (charSequence.length() <= 0) {
            return null;
        }
        return PackageParser.buildCompoundName(string2, charSequence, "taskAffinity", arrstring);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private void cacheResult(File file, int n, Package arrby) {
        if (this.mCacheDir == null) {
            return;
        }
        charSequence = this.getCacheKey(file, n);
        file = new File(this.mCacheDir, (String)charSequence);
        if (file.exists() && !file.delete()) {
            charSequence = new StringBuilder();
            charSequence.append("Unable to delete cache file: ");
            charSequence.append(file);
            Slog.e("PackageParser", charSequence.toString());
        }
        if ((arrby = this.toCacheEntry((Package)arrby)) == null) {
            return;
        }
        fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(arrby);
        fileOutputStream.close();
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    fileOutputStream.close();
                    ** GOTO lbl34
                }
                catch (Throwable throwable3) {
                    try {
                        throwable.addSuppressed(throwable3);
lbl34: // 2 sources:
                        throw throwable2;
                        {
                            catch (IOException iOException) {
                                Slog.w("PackageParser", "Error writing cache entry.", iOException);
                                file.delete();
                            }
                        }
                    }
                    catch (Throwable throwable4) {
                        Slog.w("PackageParser", "Error saving package cache.", throwable4);
                    }
                }
            }
        }
    }

    private boolean checkOverlayRequiredSystemProperty(String string2, String string3) {
        boolean bl = TextUtils.isEmpty(string2);
        boolean bl2 = true;
        if (!bl && !TextUtils.isEmpty(string3)) {
            if ((string2 = SystemProperties.get(string2)) == null || !string2.equals(string3)) {
                bl2 = false;
            }
            return bl2;
        }
        if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Disabling overlay - incomplete property :'");
        stringBuilder.append(string2);
        stringBuilder.append("=");
        stringBuilder.append(string3);
        stringBuilder.append("' - require both requiredSystemPropertyName AND requiredSystemPropertyValue to be specified.");
        Slog.w(TAG, stringBuilder.toString());
        return false;
    }

    private static boolean checkUseInstalledOrHidden(int n, PackageUserState packageUserState, ApplicationInfo applicationInfo) {
        boolean bl = false;
        if ((n & 536870912) == 0 && !packageUserState.installed && applicationInfo != null && applicationInfo.hiddenUntilInstalled) {
            return false;
        }
        if (packageUserState.isAvailable(n) || applicationInfo != null && applicationInfo.isSystemApp() && ((4202496 & n) != 0 || (536870912 & n) != 0)) {
            bl = true;
        }
        return bl;
    }

    @UnsupportedAppUsage
    private static void collectCertificates(Package object, File object2, boolean bl) throws PackageParserException {
        String string2;
        block6 : {
            block5 : {
                block4 : {
                    string2 = ((File)object2).getAbsolutePath();
                    int n = 1;
                    if (((Package)object).applicationInfo.isStaticSharedLibrary()) {
                        n = 2;
                    }
                    object2 = bl ? ApkSignatureVerifier.unsafeGetCertsWithoutVerification(string2, n) : ApkSignatureVerifier.verify(string2, n);
                    if (((Package)object).mSigningDetails != SigningDetails.UNKNOWN) break block4;
                    ((Package)object).mSigningDetails = object2;
                    break block5;
                }
                if (!Signature.areExactMatch(object.mSigningDetails.signatures, ((SigningDetails)object2).signatures)) break block6;
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" has mismatched certificates");
        throw new PackageParserException(-104, ((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public static void collectCertificates(Package package_, boolean bl) throws PackageParserException {
        PackageParser.collectCertificatesInternal(package_, bl);
        int n = package_.childPackages != null ? package_.childPackages.size() : 0;
        for (int i = 0; i < n; ++i) {
            package_.childPackages.get((int)i).mSigningDetails = package_.mSigningDetails;
        }
    }

    private static void collectCertificatesInternal(Package package_, boolean bl) throws PackageParserException {
        block4 : {
            File file;
            int n;
            package_.mSigningDetails = SigningDetails.UNKNOWN;
            Trace.traceBegin(262144L, "collectCertificates");
            try {
                file = new File(package_.baseCodePath);
                PackageParser.collectCertificates(package_, file, bl);
                if (ArrayUtils.isEmpty(package_.splitCodePaths)) break block4;
                n = 0;
            }
            catch (Throwable throwable) {
                Trace.traceEnd(262144L);
                throw throwable;
            }
            do {
                if (n >= package_.splitCodePaths.length) break;
                file = new File(package_.splitCodePaths[n]);
                PackageParser.collectCertificates(package_, file, bl);
                ++n;
            } while (true);
        }
        Trace.traceEnd(262144L);
        return;
    }

    public static int computeMinSdkVersion(int n, String charSequence, int n2, String[] object, String[] arrstring) {
        if (charSequence == null) {
            if (n <= n2) {
                return n;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Requires newer sdk version #");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" (current version is #");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append(")");
            arrstring[0] = ((StringBuilder)charSequence).toString();
            return -1;
        }
        if (PackageParser.matchTargetCode((String[])object, (String)charSequence)) {
            return 10000;
        }
        if (((String[])object).length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requires development platform ");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(" (current platform is any of ");
            stringBuilder.append(Arrays.toString((Object[])object));
            stringBuilder.append(")");
            arrstring[0] = stringBuilder.toString();
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("Requires development platform ");
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append(" but this is a release platform.");
            arrstring[0] = ((StringBuilder)object).toString();
        }
        return -1;
    }

    public static int computeTargetSdkVersion(int n, String string2, String[] object, String[] arrstring) {
        if (string2 == null) {
            return n;
        }
        if (PackageParser.matchTargetCode((String[])object, string2)) {
            return 10000;
        }
        if (((String[])object).length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requires development platform ");
            stringBuilder.append(string2);
            stringBuilder.append(" (current platform is any of ");
            stringBuilder.append(Arrays.toString((Object[])object));
            stringBuilder.append(")");
            arrstring[0] = stringBuilder.toString();
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("Requires development platform ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" but this is a release platform.");
            arrstring[0] = ((StringBuilder)object).toString();
        }
        return -1;
    }

    private static boolean copyNeeded(int n, Package package_, PackageUserState packageUserState, Bundle bundle, int n2) {
        boolean bl;
        if (n2 != 0) {
            return true;
        }
        if (packageUserState.enabled != 0 && package_.applicationInfo.enabled != (bl = packageUserState.enabled == 1)) {
            return true;
        }
        bl = (package_.applicationInfo.flags & 1073741824) != 0;
        if (packageUserState.suspended != bl) {
            return true;
        }
        if (packageUserState.installed && !packageUserState.hidden) {
            if (packageUserState.stopped) {
                return true;
            }
            if (packageUserState.instantApp != package_.applicationInfo.isInstantApp()) {
                return true;
            }
            if ((n & 128) != 0 && (bundle != null || package_.mAppMetaData != null)) {
                return true;
            }
            if ((n & 1024) != 0 && package_.usesLibraryFiles != null) {
                return true;
            }
            if ((n & 1024) != 0 && package_.usesLibraryInfos != null) {
                return true;
            }
            return package_.staticSharedLibName != null;
        }
        return true;
    }

    @VisibleForTesting
    public static Package fromCacheEntryStatic(byte[] object) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall((byte[])object, 0, ((byte[])object).length);
        parcel.setDataPosition(0);
        new PackageParserCacheHelper.ReadHelper(parcel).startAndInstall();
        object = new Package(parcel);
        parcel.recycle();
        sCachedPackageReadCount.incrementAndGet();
        return object;
    }

    public static final ActivityInfo generateActivityInfo(ActivityInfo activityInfo, int n, PackageUserState packageUserState, int n2) {
        if (activityInfo == null) {
            return null;
        }
        if (!PackageParser.checkUseInstalledOrHidden(n, packageUserState, activityInfo.applicationInfo)) {
            return null;
        }
        activityInfo = new ActivityInfo(activityInfo);
        activityInfo.applicationInfo = PackageParser.generateApplicationInfo(activityInfo.applicationInfo, n, packageUserState, n2);
        return activityInfo;
    }

    @UnsupportedAppUsage
    public static final ActivityInfo generateActivityInfo(Activity activity, int n, PackageUserState packageUserState, int n2) {
        if (activity == null) {
            return null;
        }
        if (!PackageParser.checkUseInstalledOrHidden(n, packageUserState, activity.owner.applicationInfo)) {
            return null;
        }
        if (!PackageParser.copyNeeded(n, activity.owner, packageUserState, activity.metaData, n2)) {
            PackageParser.updateApplicationInfo(activity.info.applicationInfo, n, packageUserState);
            return activity.info;
        }
        ActivityInfo activityInfo = new ActivityInfo(activity.info);
        activityInfo.metaData = activity.metaData;
        activityInfo.applicationInfo = PackageParser.generateApplicationInfo(activity.owner, n, packageUserState, n2);
        return activityInfo;
    }

    private Activity generateAppDetailsHiddenActivity(Package parcelable, int n, String[] arrstring, boolean bl) {
        Activity activity = new Activity((Package)parcelable, PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME, new ActivityInfo());
        activity.owner = parcelable;
        activity.setPackageName(parcelable.packageName);
        activity.info.theme = 16973909;
        activity.info.exported = true;
        activity.info.name = PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME;
        activity.info.processName = parcelable.applicationInfo.processName;
        activity.info.uiOptions = activity.info.applicationInfo.uiOptions;
        activity.info.taskAffinity = PackageParser.buildTaskAffinityName(parcelable.packageName, parcelable.packageName, ":app_details", arrstring);
        activity.info.enabled = true;
        activity.info.launchMode = 0;
        activity.info.documentLaunchMode = 0;
        activity.info.maxRecents = ActivityTaskManager.getDefaultAppRecentsLimitStatic();
        activity.info.configChanges = PackageParser.getActivityConfigChanges(0, 0);
        activity.info.softInputMode = 0;
        activity.info.persistableMode = 1;
        activity.info.screenOrientation = -1;
        activity.info.resizeMode = 4;
        activity.info.lockTaskLaunchMode = 0;
        parcelable = activity.info;
        activity.info.directBootAware = false;
        ((ActivityInfo)parcelable).encryptionAware = false;
        activity.info.rotationAnimation = -1;
        activity.info.colorMode = 0;
        if (bl) {
            parcelable = activity.info;
            ((ActivityInfo)parcelable).flags |= 512;
        }
        return activity;
    }

    public static ApplicationInfo generateApplicationInfo(ApplicationInfo applicationInfo, int n, PackageUserState packageUserState, int n2) {
        if (applicationInfo == null) {
            return null;
        }
        if (!PackageParser.checkUseInstalledOrHidden(n, packageUserState, applicationInfo)) {
            return null;
        }
        applicationInfo = new ApplicationInfo(applicationInfo);
        applicationInfo.initForUser(n2);
        applicationInfo.flags = packageUserState.stopped ? (applicationInfo.flags |= 2097152) : (applicationInfo.flags &= -2097153);
        PackageParser.updateApplicationInfo(applicationInfo, n, packageUserState);
        return applicationInfo;
    }

    @UnsupportedAppUsage
    public static ApplicationInfo generateApplicationInfo(Package package_, int n, PackageUserState packageUserState) {
        return PackageParser.generateApplicationInfo(package_, n, packageUserState, UserHandle.getCallingUserId());
    }

    @UnsupportedAppUsage
    public static ApplicationInfo generateApplicationInfo(Package package_, int n, PackageUserState packageUserState, int n2) {
        if (package_ == null) {
            return null;
        }
        if (PackageParser.checkUseInstalledOrHidden(n, packageUserState, package_.applicationInfo) && package_.isMatch(n)) {
            if (!(PackageParser.copyNeeded(n, package_, packageUserState, null, n2) || (32768 & n) != 0 && packageUserState.enabled == 4)) {
                PackageParser.updateApplicationInfo(package_.applicationInfo, n, packageUserState);
                return package_.applicationInfo;
            }
            ApplicationInfo applicationInfo = new ApplicationInfo(package_.applicationInfo);
            applicationInfo.initForUser(n2);
            if ((n & 128) != 0) {
                applicationInfo.metaData = package_.mAppMetaData;
            }
            if ((n & 1024) != 0) {
                applicationInfo.sharedLibraryFiles = package_.usesLibraryFiles;
                applicationInfo.sharedLibraryInfos = package_.usesLibraryInfos;
            }
            applicationInfo.flags = packageUserState.stopped ? (applicationInfo.flags |= 2097152) : (applicationInfo.flags &= -2097153);
            PackageParser.updateApplicationInfo(applicationInfo, n, packageUserState);
            return applicationInfo;
        }
        return null;
    }

    @UnsupportedAppUsage
    public static final InstrumentationInfo generateInstrumentationInfo(Instrumentation instrumentation, int n) {
        if (instrumentation == null) {
            return null;
        }
        if ((n & 128) == 0) {
            return instrumentation.info;
        }
        InstrumentationInfo instrumentationInfo = new InstrumentationInfo(instrumentation.info);
        instrumentationInfo.metaData = instrumentation.metaData;
        return instrumentationInfo;
    }

    @UnsupportedAppUsage
    public static PackageInfo generatePackageInfo(Package package_, int[] arrn, int n, long l, long l2, Set<String> set, PackageUserState packageUserState) {
        return PackageParser.generatePackageInfo(package_, arrn, n, l, l2, set, packageUserState, UserHandle.getCallingUserId());
    }

    @UnsupportedAppUsage
    public static PackageInfo generatePackageInfo(Package package_, int[] object, int n, long l, long l2, Set<String> set, PackageUserState object2, int n2) {
        if (PackageParser.checkUseInstalledOrHidden(n, (PackageUserState)object2, package_.applicationInfo) && package_.isMatch(n)) {
            int n3;
            int n4;
            Object object3;
            int n5;
            int n6;
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = package_.packageName;
            packageInfo.splitNames = package_.splitNames;
            packageInfo.versionCode = package_.mVersionCode;
            packageInfo.versionCodeMajor = package_.mVersionCodeMajor;
            packageInfo.baseRevisionCode = package_.baseRevisionCode;
            packageInfo.splitRevisionCodes = package_.splitRevisionCodes;
            packageInfo.versionName = package_.mVersionName;
            packageInfo.sharedUserId = package_.mSharedUserId;
            packageInfo.sharedUserLabel = package_.mSharedUserLabel;
            packageInfo.applicationInfo = PackageParser.generateApplicationInfo(package_, n, (PackageUserState)object2, n2);
            packageInfo.installLocation = package_.installLocation;
            packageInfo.isStub = package_.isStub;
            packageInfo.coreApp = package_.coreApp;
            if ((packageInfo.applicationInfo.flags & 1) != 0 || (packageInfo.applicationInfo.flags & 128) != 0) {
                packageInfo.requiredForAllUsers = package_.mRequiredForAllUsers;
            }
            packageInfo.restrictedAccountType = package_.mRestrictedAccountType;
            packageInfo.requiredAccountType = package_.mRequiredAccountType;
            packageInfo.overlayTarget = package_.mOverlayTarget;
            packageInfo.targetOverlayableName = package_.mOverlayTargetName;
            packageInfo.overlayCategory = package_.mOverlayCategory;
            packageInfo.overlayPriority = package_.mOverlayPriority;
            packageInfo.mOverlayIsStatic = package_.mOverlayIsStatic;
            packageInfo.compileSdkVersion = package_.mCompileSdkVersion;
            packageInfo.compileSdkVersionCodename = package_.mCompileSdkVersionCodename;
            packageInfo.firstInstallTime = l;
            packageInfo.lastUpdateTime = l2;
            if ((n & 256) != 0) {
                packageInfo.gids = object;
            }
            if ((n & 16384) != 0) {
                n4 = package_.configPreferences != null ? package_.configPreferences.size() : 0;
                if (n4 > 0) {
                    packageInfo.configPreferences = new ConfigurationInfo[n4];
                    package_.configPreferences.toArray(packageInfo.configPreferences);
                }
                if ((n4 = package_.reqFeatures != null ? package_.reqFeatures.size() : 0) > 0) {
                    packageInfo.reqFeatures = new FeatureInfo[n4];
                    package_.reqFeatures.toArray(packageInfo.reqFeatures);
                }
                if ((n4 = package_.featureGroups != null ? package_.featureGroups.size() : 0) > 0) {
                    packageInfo.featureGroups = new FeatureGroupInfo[n4];
                    package_.featureGroups.toArray(packageInfo.featureGroups);
                }
            }
            if ((n & 1) != 0 && (n3 = package_.activities.size()) > 0) {
                object = new ActivityInfo[n3];
                n6 = 0;
                for (n5 = 0; n5 < n3; ++n5) {
                    object3 = package_.activities.get(n5);
                    n4 = n6;
                    if (((PackageUserState)object2).isMatch(object3.info, n)) {
                        if (PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(object3.className)) {
                            n4 = n6;
                        } else {
                            object[n6] = (int)PackageParser.generateActivityInfo((Activity)object3, n, (PackageUserState)object2, n2);
                            n4 = n6 + 1;
                        }
                    }
                    n6 = n4;
                }
                packageInfo.activities = (ActivityInfo[])ArrayUtils.trimToSize(object, n6);
            }
            if ((n & 2) != 0 && (n3 = package_.receivers.size()) > 0) {
                n5 = 0;
                object3 = new ActivityInfo[n3];
                for (n4 = 0; n4 < n3; ++n4) {
                    object = package_.receivers.get(n4);
                    n6 = n5;
                    if (((PackageUserState)object2).isMatch(object.info, n)) {
                        object3[n5] = PackageParser.generateActivityInfo((Activity)object, n, (PackageUserState)object2, n2);
                        n6 = n5 + 1;
                    }
                    n5 = n6;
                }
                packageInfo.receivers = ArrayUtils.trimToSize(object3, n5);
            }
            if ((n & 4) != 0 && (n3 = package_.services.size()) > 0) {
                n6 = 0;
                object = new ServiceInfo[n3];
                for (n4 = 0; n4 < n3; ++n4) {
                    object3 = package_.services.get(n4);
                    n5 = n6;
                    if (((PackageUserState)object2).isMatch(object3.info, n)) {
                        object[n6] = (int)PackageParser.generateServiceInfo((Service)object3, n, (PackageUserState)object2, n2);
                        n5 = n6 + 1;
                    }
                    n6 = n5;
                }
                packageInfo.services = (ServiceInfo[])ArrayUtils.trimToSize(object, n6);
            }
            if ((n & 8) != 0 && (n3 = package_.providers.size()) > 0) {
                n6 = 0;
                object3 = new ProviderInfo[n3];
                for (n4 = 0; n4 < n3; ++n4) {
                    object = package_.providers.get(n4);
                    n5 = n6;
                    if (((PackageUserState)object2).isMatch(object.info, n)) {
                        object3[n6] = PackageParser.generateProviderInfo((Provider)object, n, (PackageUserState)object2, n2);
                        n5 = n6 + 1;
                    }
                    n6 = n5;
                }
                packageInfo.providers = (ProviderInfo[])ArrayUtils.trimToSize(object3, n6);
            }
            if ((n & 16) != 0 && (n4 = package_.instrumentation.size()) > 0) {
                packageInfo.instrumentation = new InstrumentationInfo[n4];
                for (n2 = 0; n2 < n4; ++n2) {
                    packageInfo.instrumentation[n2] = PackageParser.generateInstrumentationInfo(package_.instrumentation.get(n2), n);
                }
            }
            if ((n & 4096) != 0) {
                n4 = package_.permissions.size();
                if (n4 > 0) {
                    packageInfo.permissions = new PermissionInfo[n4];
                    for (n2 = 0; n2 < n4; ++n2) {
                        packageInfo.permissions[n2] = PackageParser.generatePermissionInfo(package_.permissions.get(n2), n);
                    }
                }
                if ((n4 = package_.requestedPermissions.size()) > 0) {
                    packageInfo.requestedPermissions = new String[n4];
                    packageInfo.requestedPermissionsFlags = new int[n4];
                    for (n2 = 0; n2 < n4; ++n2) {
                        packageInfo.requestedPermissions[n2] = object2 = package_.requestedPermissions.get(n2);
                        object = packageInfo.requestedPermissionsFlags;
                        object[n2] = object[n2] | 1;
                        if (set == null || !set.contains(object2)) continue;
                        object = packageInfo.requestedPermissionsFlags;
                        object[n2] = object[n2] | 2;
                    }
                }
            }
            if ((n & 64) != 0) {
                if (package_.mSigningDetails.hasPastSigningCertificates()) {
                    packageInfo.signatures = new Signature[1];
                    packageInfo.signatures[0] = package_.mSigningDetails.pastSigningCertificates[0];
                } else if (package_.mSigningDetails.hasSignatures()) {
                    n2 = package_.mSigningDetails.signatures.length;
                    packageInfo.signatures = new Signature[n2];
                    System.arraycopy(package_.mSigningDetails.signatures, 0, packageInfo.signatures, 0, n2);
                }
            }
            if ((134217728 & n) != 0) {
                packageInfo.signingInfo = package_.mSigningDetails != SigningDetails.UNKNOWN ? new SigningInfo(package_.mSigningDetails) : null;
            }
            return packageInfo;
        }
        return null;
    }

    public static PackageInfo generatePackageInfoFromApex(ApexInfo parcelable, int n) throws PackageParserException {
        Object object = new PackageParser();
        File file = new File(parcelable.packagePath);
        object = ((PackageParser)object).parsePackage(file, n, false);
        Object object2 = new PackageUserState();
        object2 = PackageParser.generatePackageInfo((Package)object, EmptyArray.INT, n, 0L, 0L, Collections.<String>emptySet(), (PackageUserState)object2);
        object2.applicationInfo.sourceDir = file.getPath();
        object2.applicationInfo.publicSourceDir = file.getPath();
        if (parcelable.isFactory) {
            ApplicationInfo applicationInfo = ((PackageInfo)object2).applicationInfo;
            applicationInfo.flags |= 1;
        } else {
            ApplicationInfo applicationInfo = ((PackageInfo)object2).applicationInfo;
            applicationInfo.flags &= -2;
        }
        if (parcelable.isActive) {
            parcelable = ((PackageInfo)object2).applicationInfo;
            ((ApplicationInfo)parcelable).flags |= 8388608;
        } else {
            parcelable = ((PackageInfo)object2).applicationInfo;
            ((ApplicationInfo)parcelable).flags &= -8388609;
        }
        ((PackageInfo)object2).isApex = true;
        if ((134217728 & n) != 0) {
            PackageParser.collectCertificates((Package)object, file, false);
            if (((Package)object).mSigningDetails.hasPastSigningCertificates()) {
                ((PackageInfo)object2).signatures = new Signature[1];
                object2.signatures[0] = object.mSigningDetails.pastSigningCertificates[0];
            } else if (((Package)object).mSigningDetails.hasSignatures()) {
                n = object.mSigningDetails.signatures.length;
                ((PackageInfo)object2).signatures = new Signature[n];
                System.arraycopy(object.mSigningDetails.signatures, 0, ((PackageInfo)object2).signatures, 0, n);
            }
            ((PackageInfo)object2).signingInfo = ((Package)object).mSigningDetails != SigningDetails.UNKNOWN ? new SigningInfo(((Package)object).mSigningDetails) : null;
        }
        return object2;
    }

    @UnsupportedAppUsage
    public static final PermissionGroupInfo generatePermissionGroupInfo(PermissionGroup permissionGroup, int n) {
        if (permissionGroup == null) {
            return null;
        }
        if ((n & 128) == 0) {
            return permissionGroup.info;
        }
        PermissionGroupInfo permissionGroupInfo = new PermissionGroupInfo(permissionGroup.info);
        permissionGroupInfo.metaData = permissionGroup.metaData;
        return permissionGroupInfo;
    }

    @UnsupportedAppUsage
    public static final PermissionInfo generatePermissionInfo(Permission permission2, int n) {
        if (permission2 == null) {
            return null;
        }
        if ((n & 128) == 0) {
            return permission2.info;
        }
        PermissionInfo permissionInfo = new PermissionInfo(permission2.info);
        permissionInfo.metaData = permission2.metaData;
        return permissionInfo;
    }

    @UnsupportedAppUsage
    public static final ProviderInfo generateProviderInfo(Provider provider, int n, PackageUserState packageUserState, int n2) {
        if (provider == null) {
            return null;
        }
        if (!PackageParser.checkUseInstalledOrHidden(n, packageUserState, provider.owner.applicationInfo)) {
            return null;
        }
        if (!(PackageParser.copyNeeded(n, provider.owner, packageUserState, provider.metaData, n2) || (n & 2048) == 0 && provider.info.uriPermissionPatterns != null)) {
            PackageParser.updateApplicationInfo(provider.info.applicationInfo, n, packageUserState);
            return provider.info;
        }
        ProviderInfo providerInfo = new ProviderInfo(provider.info);
        providerInfo.metaData = provider.metaData;
        if ((n & 2048) == 0) {
            providerInfo.uriPermissionPatterns = null;
        }
        providerInfo.applicationInfo = PackageParser.generateApplicationInfo(provider.owner, n, packageUserState, n2);
        return providerInfo;
    }

    @UnsupportedAppUsage
    public static final ServiceInfo generateServiceInfo(Service service, int n, PackageUserState packageUserState, int n2) {
        if (service == null) {
            return null;
        }
        if (!PackageParser.checkUseInstalledOrHidden(n, packageUserState, service.owner.applicationInfo)) {
            return null;
        }
        if (!PackageParser.copyNeeded(n, service.owner, packageUserState, service.metaData, n2)) {
            PackageParser.updateApplicationInfo(service.info.applicationInfo, n, packageUserState);
            return service.info;
        }
        ServiceInfo serviceInfo = new ServiceInfo(service.info);
        serviceInfo.metaData = service.metaData;
        serviceInfo.applicationInfo = PackageParser.generateApplicationInfo(service.owner, n, packageUserState, n2);
        return serviceInfo;
    }

    public static int getActivityConfigChanges(int n, int n2) {
        return n2 & 3 | n;
    }

    private String getCacheKey(File serializable, int n) {
        serializable = new StringBuilder(((File)serializable).getName());
        ((StringBuilder)serializable).append('-');
        ((StringBuilder)serializable).append(n);
        return ((StringBuilder)serializable).toString();
    }

    private Package getCachedResult(File arrstring, int n) {
        Package package_;
        block8 : {
            Object object;
            block7 : {
                if (this.mCacheDir == null) {
                    return null;
                }
                object = this.getCacheKey((File)arrstring, n);
                object = new File(this.mCacheDir, (String)object);
                if (PackageParser.isCacheUpToDate((File)arrstring, (File)object)) break block7;
                return null;
            }
            package_ = this.fromCacheEntry(IoUtils.readFileAsByteArray((String)((File)object).getAbsolutePath()));
            if (this.mCallback == null || (arrstring = this.mCallback.getOverlayApks(package_.packageName)) == null) break block8;
            try {
                if (arrstring.length <= 0) break block8;
            }
            catch (Throwable throwable) {
                Slog.w(TAG, "Error reading package cache: ", throwable);
                ((File)object).delete();
                return null;
            }
            for (String string2 : arrstring) {
                File file = new File(string2);
                boolean bl = PackageParser.isCacheUpToDate(file, (File)object);
                if (bl) continue;
                return null;
            }
        }
        return package_;
    }

    private static boolean hasDomainURLs(Package object) {
        if (object != null && ((Package)object).activities != null) {
            ArrayList<Activity> arrayList = ((Package)object).activities;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                object = arrayList.get((int)i).intents;
                if (object == null) continue;
                int n2 = ((ArrayList)object).size();
                for (int j = 0; j < n2; ++j) {
                    ActivityIntentInfo activityIntentInfo = (ActivityIntentInfo)((ArrayList)object).get(j);
                    if (!activityIntentInfo.hasAction("android.intent.action.VIEW") || !activityIntentInfo.hasAction("android.intent.action.VIEW") || !activityIntentInfo.hasDataScheme("http") && !activityIntentInfo.hasDataScheme("https")) {
                        continue;
                    }
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static final boolean isApkFile(File file) {
        return PackageParser.isApkPath(file.getName());
    }

    public static boolean isApkPath(String string2) {
        return string2.endsWith(APK_FILE_EXTENSION);
    }

    public static boolean isAvailable(PackageUserState packageUserState) {
        return PackageParser.checkUseInstalledOrHidden(0, packageUserState, null);
    }

    private static boolean isCacheUpToDate(File file, File file2) {
        boolean bl = false;
        try {
            file = Os.stat((String)file.getAbsolutePath());
            file2 = Os.stat((String)file2.getAbsolutePath());
            long l = ((StructStat)file).st_mtime;
            long l2 = ((StructStat)file2).st_mtime;
            if (l < l2) {
                bl = true;
            }
            return bl;
        }
        catch (ErrnoException errnoException) {
            if (errnoException.errno != OsConstants.ENOENT) {
                Slog.w("Error while stating package cache : ", errnoException);
            }
            return false;
        }
    }

    private boolean isImplicitlyExposedIntent(IntentInfo intentInfo) {
        boolean bl = intentInfo.hasCategory("android.intent.category.BROWSABLE") || intentInfo.hasAction("android.intent.action.SEND") || intentInfo.hasAction("android.intent.action.SENDTO") || intentInfo.hasAction("android.intent.action.SEND_MULTIPLE");
        return bl;
    }

    static /* synthetic */ int lambda$parseBaseApplication$0(Activity activity, Activity activity2) {
        return Integer.compare(activity2.order, activity.order);
    }

    static /* synthetic */ int lambda$parseBaseApplication$1(Activity activity, Activity activity2) {
        return Integer.compare(activity2.order, activity.order);
    }

    static /* synthetic */ int lambda$parseBaseApplication$2(Service service, Service service2) {
        return Integer.compare(service2.order, service.order);
    }

    private static boolean matchTargetCode(String[] arrstring, String string2) {
        int n = string2.indexOf(46);
        if (n != -1) {
            string2 = string2.substring(0, n);
        }
        return ArrayUtils.contains(arrstring, string2);
    }

    private static AssetManager newConfiguredAssetManager() {
        AssetManager assetManager = new AssetManager();
        assetManager.setConfiguration(0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
        return assetManager;
    }

    private Activity parseActivity(Package object, Resources resources, XmlResourceParser xmlResourceParser, int n, String[] arrstring, CachedComponentArgs object2, boolean bl, boolean bl2) throws XmlPullParserException, IOException {
        int n2;
        boolean bl3;
        Package package_ = object;
        Object object3 = xmlResourceParser;
        String[] arrstring2 = arrstring;
        TypedArray typedArray = resources.obtainAttributes((AttributeSet)object3, R.styleable.AndroidManifestActivity);
        if (((CachedComponentArgs)object2).mActivityArgs == null) {
            ((CachedComponentArgs)object2).mActivityArgs = new ParseComponentArgs((Package)object, arrstring, 3, 1, 2, 44, 23, 30, this.mSeparateProcesses, 7, 17, 5);
        }
        Object object4 = ((CachedComponentArgs)object2).mActivityArgs;
        object = bl ? "<receiver>" : "<activity>";
        ((ParseComponentArgs)object4).tag = object;
        object2.mActivityArgs.sa = typedArray;
        object2.mActivityArgs.flags = n;
        object4 = new Activity(((CachedComponentArgs)object2).mActivityArgs, new ActivityInfo());
        if (arrstring2[0] != null) {
            typedArray.recycle();
            return null;
        }
        boolean bl4 = typedArray.hasValue(6);
        if (bl4) {
            object4.info.exported = typedArray.getBoolean(6, false);
        }
        object4.info.theme = typedArray.getResourceId(0, 0);
        object4.info.uiOptions = typedArray.getInt(26, object4.info.applicationInfo.uiOptions);
        object = typedArray.getNonConfigurationString(27, 1024);
        if (object != null) {
            object2 = PackageParser.buildClassName(object4.info.packageName, (CharSequence)object, arrstring2);
            if (arrstring2[0] == null) {
                object4.info.parentActivityName = object2;
            } else {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Activity ");
                ((StringBuilder)object2).append(object4.info.name);
                ((StringBuilder)object2).append(" specified invalid parentActivityName ");
                ((StringBuilder)object2).append((String)object);
                Log.e(TAG, ((StringBuilder)object2).toString());
                arrstring2[0] = null;
            }
        }
        if ((object = typedArray.getNonConfigurationString(4, 0)) == null) {
            object4.info.permission = package_.applicationInfo.permission;
        } else {
            object2 = ((Activity)object4).info;
            object = ((String)object).length() > 0 ? ((String)object).toString().intern() : null;
            ((ActivityInfo)object2).permission = object;
        }
        object = typedArray.getNonConfigurationString(8, 1024);
        object4.info.taskAffinity = PackageParser.buildTaskAffinityName(package_.applicationInfo.packageName, package_.applicationInfo.taskAffinity, (CharSequence)object, arrstring2);
        object4.info.splitName = typedArray.getNonConfigurationString(48, 0);
        object4.info.flags = 0;
        if (typedArray.getBoolean(9, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 1;
        }
        if (typedArray.getBoolean(10, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 2;
        }
        if (typedArray.getBoolean(11, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 4;
        }
        if (typedArray.getBoolean(21, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 128;
        }
        if (typedArray.getBoolean(18, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 8;
        }
        if (typedArray.getBoolean(12, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 16;
        }
        if (typedArray.getBoolean(13, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 32;
        }
        if (typedArray.getBoolean(19, bl3 = (package_.applicationInfo.flags & 32) != 0)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 64;
        }
        if (typedArray.getBoolean(22, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 256;
        }
        if (typedArray.getBoolean(29, false) || typedArray.getBoolean(39, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 1024;
        }
        if (typedArray.getBoolean(24, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 2048;
        }
        if (typedArray.getBoolean(56, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 536870912;
        }
        if (!bl) {
            if (typedArray.getBoolean(25, bl2)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).flags |= 512;
            }
            object4.info.launchMode = typedArray.getInt(14, 0);
            object4.info.documentLaunchMode = typedArray.getInt(33, 0);
            object4.info.maxRecents = typedArray.getInt(34, ActivityTaskManager.getDefaultAppRecentsLimitStatic());
            object4.info.configChanges = PackageParser.getActivityConfigChanges(typedArray.getInt(16, 0), typedArray.getInt(47, 0));
            object4.info.softInputMode = typedArray.getInt(20, 0);
            object4.info.persistableMode = typedArray.getInteger(32, 0);
            if (typedArray.getBoolean(31, false)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).flags |= Integer.MIN_VALUE;
            }
            if (typedArray.getBoolean(35, false)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).flags |= 8192;
            }
            if (typedArray.getBoolean(36, false)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).flags |= 4096;
            }
            if (typedArray.getBoolean(37, false)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).flags |= 16384;
            }
            object4.info.screenOrientation = typedArray.getInt(15, -1);
            this.setActivityResizeMode(((Activity)object4).info, typedArray, package_);
            if (typedArray.getBoolean(41, false)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).flags |= 4194304;
            }
            if (typedArray.getBoolean(55, false)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).flags |= 262144;
            }
            if (typedArray.hasValue(50) && typedArray.getType(50) == 4) {
                ((Activity)object4).setMaxAspectRatio(typedArray.getFloat(50, 0.0f));
            }
            if (typedArray.hasValue(53) && typedArray.getType(53) == 4) {
                ((Activity)object4).setMinAspectRatio(typedArray.getFloat(53, 0.0f));
            }
            object4.info.lockTaskLaunchMode = typedArray.getInt(38, 0);
            object = ((Activity)object4).info;
            object2 = ((Activity)object4).info;
            ((ActivityInfo)object2).directBootAware = bl2 = typedArray.getBoolean(42, false);
            ((ActivityInfo)object).encryptionAware = bl2;
            object4.info.requestedVrComponent = typedArray.getString(43);
            object4.info.rotationAnimation = typedArray.getInt(46, -1);
            object4.info.colorMode = typedArray.getInt(49, 0);
            if (typedArray.getBoolean(51, false)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).flags |= 8388608;
            }
            if (typedArray.getBoolean(52, false)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).flags |= 16777216;
            }
            if (typedArray.getBoolean(54, false)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).privateFlags |= 1;
            }
        } else {
            object4.info.launchMode = 0;
            object4.info.configChanges = 0;
            if (typedArray.getBoolean(28, false)) {
                object = ((Activity)object4).info;
                ((ActivityInfo)object).flags |= 1073741824;
            }
            object = ((Activity)object4).info;
            object2 = ((Activity)object4).info;
            ((ActivityInfo)object2).directBootAware = bl2 = typedArray.getBoolean(42, false);
            ((ActivityInfo)object).encryptionAware = bl2;
        }
        if (object4.info.directBootAware) {
            object = package_.applicationInfo;
            ((ApplicationInfo)object).privateFlags |= 256;
        }
        if (bl2 = typedArray.getBoolean(45, false)) {
            object = ((Activity)object4).info;
            ((ActivityInfo)object).flags |= 1048576;
            package_.visibleToInstantApps = true;
        }
        typedArray.recycle();
        if (bl && (package_.applicationInfo.privateFlags & 2) != 0 && object4.info.processName == package_.packageName) {
            arrstring2[0] = "Heavy-weight applications can not have receivers in main process";
        }
        if (arrstring2[0] != null) {
            return null;
        }
        n = xmlResourceParser.getDepth();
        object2 = object3;
        object = typedArray;
        while ((n2 = xmlResourceParser.next()) != 1 && (n2 != 3 || xmlResourceParser.getDepth() > n)) {
            if (n2 == 3 || n2 == 4) continue;
            if (xmlResourceParser.getName().equals("intent-filter")) {
                object2 = new ActivityIntentInfo((Activity)object4);
                if (!this.parseIntent(resources, xmlResourceParser, true, true, (IntentInfo)object2, arrstring)) {
                    return null;
                }
                if (((IntentFilter)object2).countActions() == 0) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("No actions in intent filter at ");
                    ((StringBuilder)object3).append(this.mArchiveSourcePath);
                    ((StringBuilder)object3).append(" ");
                    ((StringBuilder)object3).append(xmlResourceParser.getPositionDescription());
                    Slog.w(TAG, ((StringBuilder)object3).toString());
                } else {
                    ((Activity)object4).order = Math.max(((IntentFilter)object2).getOrder(), ((Activity)object4).order);
                    ((Activity)object4).intents.add(object2);
                }
                n2 = bl2 ? 1 : (!bl && this.isImplicitlyExposedIntent((IntentInfo)object2) ? 2 : 0);
                ((IntentFilter)object2).setVisibilityToInstantApp(n2);
                if (((IntentFilter)object2).isVisibleToInstantApp()) {
                    object3 = ((Activity)object4).info;
                    ((ActivityInfo)object3).flags |= 1048576;
                }
                if (((IntentFilter)object2).isImplicitlyVisibleToInstantApp()) {
                    object2 = ((Activity)object4).info;
                    ((ActivityInfo)object2).flags |= 2097152;
                }
                object2 = xmlResourceParser;
                continue;
            }
            if (!bl && xmlResourceParser.getName().equals("preferred")) {
                object2 = new ActivityIntentInfo((Activity)object4);
                if (!this.parseIntent(resources, xmlResourceParser, false, false, (IntentInfo)object2, arrstring)) {
                    return null;
                }
                if (((IntentFilter)object2).countActions() == 0) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("No actions in preferred at ");
                    ((StringBuilder)object3).append(this.mArchiveSourcePath);
                    ((StringBuilder)object3).append(" ");
                    ((StringBuilder)object3).append(xmlResourceParser.getPositionDescription());
                    Slog.w(TAG, ((StringBuilder)object3).toString());
                } else {
                    if (package_.preferredActivityFilters == null) {
                        package_.preferredActivityFilters = new ArrayList();
                    }
                    package_.preferredActivityFilters.add((ActivityIntentInfo)object2);
                }
                n2 = bl2 ? 1 : (!bl && this.isImplicitlyExposedIntent((IntentInfo)object2) ? 2 : 0);
                ((IntentFilter)object2).setVisibilityToInstantApp(n2);
                if (((IntentFilter)object2).isVisibleToInstantApp()) {
                    object3 = ((Activity)object4).info;
                    ((ActivityInfo)object3).flags |= 1048576;
                }
                if (((IntentFilter)object2).isImplicitlyVisibleToInstantApp()) {
                    object2 = ((Activity)object4).info;
                    ((ActivityInfo)object2).flags |= 2097152;
                }
                object2 = xmlResourceParser;
                continue;
            }
            if (xmlResourceParser.getName().equals("meta-data")) {
                ((Activity)object4).metaData = object2 = this.parseMetaData(resources, xmlResourceParser, ((Activity)object4).metaData, arrstring2);
                if (object2 == null) {
                    return null;
                }
                object2 = xmlResourceParser;
                continue;
            }
            if (!bl && xmlResourceParser.getName().equals("layout")) {
                this.parseLayout(resources, xmlResourceParser, (Activity)object4);
                object2 = xmlResourceParser;
                continue;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Problem in package ");
            ((StringBuilder)object2).append(this.mArchiveSourcePath);
            ((StringBuilder)object2).append(":");
            Slog.w(TAG, ((StringBuilder)object2).toString());
            if (bl) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unknown element under <receiver>: ");
                ((StringBuilder)object2).append(xmlResourceParser.getName());
                ((StringBuilder)object2).append(" at ");
                ((StringBuilder)object2).append(this.mArchiveSourcePath);
                ((StringBuilder)object2).append(" ");
                ((StringBuilder)object2).append(xmlResourceParser.getPositionDescription());
                Slog.w(TAG, ((StringBuilder)object2).toString());
            } else {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unknown element under <activity>: ");
                ((StringBuilder)object2).append(xmlResourceParser.getName());
                ((StringBuilder)object2).append(" at ");
                ((StringBuilder)object2).append(this.mArchiveSourcePath);
                ((StringBuilder)object2).append(" ");
                ((StringBuilder)object2).append(xmlResourceParser.getPositionDescription());
                Slog.w(TAG, ((StringBuilder)object2).toString());
            }
            XmlUtils.skipCurrentTag(xmlResourceParser);
            object2 = xmlResourceParser;
        }
        if (!bl4) {
            object = ((Activity)object4).info;
            bl = ((Activity)object4).intents.size() > 0;
            ((ActivityInfo)object).exported = bl;
        }
        return object4;
    }

    private Activity parseActivityAlias(Package object, Resources resources, XmlResourceParser xmlResourceParser, int n, String[] arrstring, CachedComponentArgs object2) throws XmlPullParserException, IOException {
        boolean bl;
        Activity activity;
        boolean bl2;
        block26 : {
            Object object3;
            Object object4;
            Object object5;
            int n2;
            Object object6;
            Object object7;
            block25 : {
                object5 = resources;
                object7 = arrstring;
                object6 = ((Resources)object5).obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivityAlias);
                object4 = ((TypedArray)object6).getNonConfigurationString(7, 1024);
                if (object4 == null) {
                    object7[0] = "<activity-alias> does not specify android:targetActivity";
                    ((TypedArray)object6).recycle();
                    return null;
                }
                if ((object4 = PackageParser.buildClassName(object.applicationInfo.packageName, (CharSequence)object4, object7)) == null) {
                    ((TypedArray)object6).recycle();
                    return null;
                }
                if (((CachedComponentArgs)object2).mActivityAliasArgs == null) {
                    ((CachedComponentArgs)object2).mActivityAliasArgs = new ParseComponentArgs((Package)object, arrstring, 2, 0, 1, 11, 8, 10, this.mSeparateProcesses, 0, 6, 4);
                    object2.mActivityAliasArgs.tag = "<activity-alias>";
                }
                object2.mActivityAliasArgs.sa = object6;
                object2.mActivityAliasArgs.flags = n;
                n2 = object.activities.size();
                for (n = 0; n < n2; ++n) {
                    object3 = object.activities.get(n);
                    if (!((String)object4).equals(object3.info.name)) continue;
                    object = object3;
                    break block25;
                }
                object = null;
            }
            if (object == null) {
                object = new StringBuilder();
                object.append("<activity-alias> target activity ");
                object.append((String)object4);
                object.append(" not found in manifest");
                object7[0] = object.toString();
                ((TypedArray)object6).recycle();
                return null;
            }
            object3 = new ActivityInfo();
            ((ActivityInfo)object3).targetActivity = object4;
            ((ActivityInfo)object3).configChanges = object.info.configChanges;
            ((ActivityInfo)object3).flags = object.info.flags;
            ((ActivityInfo)object3).privateFlags = object.info.privateFlags;
            ((ActivityInfo)object3).icon = object.info.icon;
            ((ActivityInfo)object3).logo = object.info.logo;
            ((ActivityInfo)object3).banner = object.info.banner;
            ((ActivityInfo)object3).labelRes = object.info.labelRes;
            ((ActivityInfo)object3).nonLocalizedLabel = object.info.nonLocalizedLabel;
            ((ActivityInfo)object3).launchMode = object.info.launchMode;
            ((ActivityInfo)object3).lockTaskLaunchMode = object.info.lockTaskLaunchMode;
            ((ActivityInfo)object3).processName = object.info.processName;
            if (((ActivityInfo)object3).descriptionRes == 0) {
                ((ActivityInfo)object3).descriptionRes = object.info.descriptionRes;
            }
            ((ActivityInfo)object3).screenOrientation = object.info.screenOrientation;
            ((ActivityInfo)object3).taskAffinity = object.info.taskAffinity;
            ((ActivityInfo)object3).theme = object.info.theme;
            ((ActivityInfo)object3).softInputMode = object.info.softInputMode;
            ((ActivityInfo)object3).uiOptions = object.info.uiOptions;
            ((ActivityInfo)object3).parentActivityName = object.info.parentActivityName;
            ((ActivityInfo)object3).maxRecents = object.info.maxRecents;
            ((ActivityInfo)object3).windowLayout = object.info.windowLayout;
            ((ActivityInfo)object3).resizeMode = object.info.resizeMode;
            ((ActivityInfo)object3).maxAspectRatio = object.info.maxAspectRatio;
            ((ActivityInfo)object3).minAspectRatio = object.info.minAspectRatio;
            ((ActivityInfo)object3).requestedVrComponent = object.info.requestedVrComponent;
            ((ActivityInfo)object3).directBootAware = bl = object.info.directBootAware;
            ((ActivityInfo)object3).encryptionAware = bl;
            activity = new Activity(((CachedComponentArgs)object2).mActivityAliasArgs, (ActivityInfo)object3);
            if (object7[0] != null) {
                ((TypedArray)object6).recycle();
                return null;
            }
            bl2 = ((TypedArray)object6).hasValue(5);
            if (bl2) {
                activity.info.exported = ((TypedArray)object6).getBoolean(5, false);
            }
            if ((object = ((TypedArray)object6).getNonConfigurationString(3, 0)) != null) {
                object2 = activity.info;
                object = object.length() > 0 ? object.toString().intern() : null;
                ((ActivityInfo)object2).permission = object;
            }
            object = ((TypedArray)object6).getNonConfigurationString(9, 1024);
            object3 = TAG;
            if (object != null) {
                object2 = PackageParser.buildClassName(activity.info.packageName, (CharSequence)object, object7);
                if (object7[0] == null) {
                    activity.info.parentActivityName = object2;
                } else {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Activity alias ");
                    ((StringBuilder)object2).append(activity.info.name);
                    ((StringBuilder)object2).append(" specified invalid parentActivityName ");
                    ((StringBuilder)object2).append((String)object);
                    Log.e(TAG, ((StringBuilder)object2).toString());
                    object7[0] = null;
                }
            }
            object2 = object4;
            boolean bl3 = (activity.info.flags & 1048576) != 0;
            ((TypedArray)object6).recycle();
            if (object7[0] != null) {
                return null;
            }
            n = xmlResourceParser.getDepth();
            object = object7;
            object7 = object6;
            object4 = object3;
            while ((n2 = xmlResourceParser.next()) != 1) {
                if (n2 == 3 && xmlResourceParser.getDepth() <= n) {
                    bl = true;
                    break block26;
                }
                if (n2 == 3 || n2 == 4) continue;
                if (xmlResourceParser.getName().equals("intent-filter")) {
                    object5 = new ActivityIntentInfo(activity);
                    if (!this.parseIntent(resources, xmlResourceParser, true, true, (IntentInfo)object5, arrstring)) {
                        return null;
                    }
                    if (((IntentFilter)object5).countActions() == 0) {
                        object6 = new StringBuilder();
                        ((StringBuilder)object6).append("No actions in intent filter at ");
                        ((StringBuilder)object6).append(this.mArchiveSourcePath);
                        ((StringBuilder)object6).append(" ");
                        ((StringBuilder)object6).append(xmlResourceParser.getPositionDescription());
                        Slog.w((String)object4, ((StringBuilder)object6).toString());
                    } else {
                        activity.order = Math.max(((IntentFilter)object5).getOrder(), activity.order);
                        activity.intents.add(object5);
                    }
                    n2 = bl3 ? 1 : (this.isImplicitlyExposedIntent((IntentInfo)object5) ? 2 : 0);
                    ((IntentFilter)object5).setVisibilityToInstantApp(n2);
                    if (((IntentFilter)object5).isVisibleToInstantApp()) {
                        object6 = activity.info;
                        ((ActivityInfo)object6).flags |= 1048576;
                    }
                    if (((IntentFilter)object5).isImplicitlyVisibleToInstantApp()) {
                        object5 = activity.info;
                        ((ActivityInfo)object5).flags |= 2097152;
                    }
                    object5 = resources;
                    continue;
                }
                if (xmlResourceParser.getName().equals("meta-data")) {
                    activity.metaData = object5 = this.parseMetaData(resources, xmlResourceParser, activity.metaData, (String[])object);
                    if (object5 == null) {
                        return null;
                    }
                    object5 = resources;
                    continue;
                }
                object5 = new StringBuilder();
                ((StringBuilder)object5).append("Unknown element under <activity-alias>: ");
                ((StringBuilder)object5).append(xmlResourceParser.getName());
                ((StringBuilder)object5).append(" at ");
                ((StringBuilder)object5).append(this.mArchiveSourcePath);
                ((StringBuilder)object5).append(" ");
                ((StringBuilder)object5).append(xmlResourceParser.getPositionDescription());
                Slog.w((String)object4, ((StringBuilder)object5).toString());
                XmlUtils.skipCurrentTag(xmlResourceParser);
                object5 = resources;
            }
            bl = true;
        }
        if (!bl2) {
            object = activity.info;
            if (activity.intents.size() <= 0) {
                bl = false;
            }
            object.exported = bl;
        }
        return activity;
    }

    private String[] parseAdditionalCertificates(Resources object, XmlResourceParser xmlResourceParser, String[] arrstring) throws XmlPullParserException, IOException {
        int n;
        String[] arrstring2 = EmptyArray.STRING;
        int n2 = xmlResourceParser.getDepth();
        while ((n = xmlResourceParser.next()) != 1 && (n != 3 || xmlResourceParser.getDepth() > n2)) {
            if (n == 3 || n == 4) continue;
            if (xmlResourceParser.getName().equals("additional-certificate")) {
                TypedArray typedArray = ((Resources)object).obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestAdditionalCertificate);
                String string2 = typedArray.getNonResourceString(0);
                typedArray.recycle();
                if (TextUtils.isEmpty(string2)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Bad additional-certificate declaration with empty certDigest:");
                    ((StringBuilder)object).append(string2);
                    arrstring[0] = ((StringBuilder)object).toString();
                    this.mParseError = -108;
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                    typedArray.recycle();
                    return null;
                }
                arrstring2 = ArrayUtils.appendElement(String.class, arrstring2, string2.replace(":", "").toLowerCase());
                continue;
            }
            XmlUtils.skipCurrentTag(xmlResourceParser);
        }
        return arrstring2;
    }

    private boolean parseAllMetaData(Resources resources, XmlResourceParser xmlResourceParser, String string2, Component<?> component, String[] arrstring) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlResourceParser.getDepth();
        while ((n = xmlResourceParser.next()) != 1 && (n != 3 || xmlResourceParser.getDepth() > n2)) {
            Object object;
            if (n == 3 || n == 4) continue;
            if (xmlResourceParser.getName().equals("meta-data")) {
                component.metaData = object = this.parseMetaData(resources, xmlResourceParser, component.metaData, arrstring);
                if (object != null) continue;
                return false;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown element under ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(xmlResourceParser.getName());
            ((StringBuilder)object).append(" at ");
            ((StringBuilder)object).append(this.mArchiveSourcePath);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(xmlResourceParser.getPositionDescription());
            Slog.w(TAG, ((StringBuilder)object).toString());
            XmlUtils.skipCurrentTag(xmlResourceParser);
        }
        return true;
    }

    public static ApkLite parseApkLite(File file, int n) throws PackageParserException {
        return PackageParser.parseApkLiteInner(file, null, null, n);
    }

    public static ApkLite parseApkLite(FileDescriptor fileDescriptor, String string2, int n) throws PackageParserException {
        return PackageParser.parseApkLiteInner(null, fileDescriptor, string2, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static ApkLite parseApkLite(String var0, XmlPullParser var1_1, AttributeSet var2_2, SigningDetails var3_3) throws IOException, XmlPullParserException, PackageParserException {
        var4_4 = PackageParser.parsePackageSplitNames(var1_1, var2_2);
        var5_5 = 0;
        var6_6 = 1;
        var7_7 = null;
        var8_8 = null;
        var9_9 = false;
        var10_10 = false;
        var11_11 = false;
        var12_12 = false;
        var13_13 = 0;
        var14_14 = 0;
        var15_15 = 0;
        var16_16 = -1;
        for (var17_17 = 0; var17_17 < var2_2.getAttributeCount(); ++var17_17) {
            var18_18 = var2_2.getAttributeName(var17_17);
            if (var18_18.equals("installLocation")) {
                var19_19 = var2_2.getAttributeIntValue(var17_17, -1);
                var20_20 = var15_15;
                var21_21 = var14_14;
                var22_22 = var13_13;
                var23_23 = var12_12;
                var24_24 = var11_11;
                var25_25 = var10_10;
                var26_26 = var7_7;
            } else if (var18_18.equals("versionCode")) {
                var20_20 = var2_2.getAttributeIntValue(var17_17, 0);
                var19_19 = var16_16;
                var21_21 = var14_14;
                var22_22 = var13_13;
                var23_23 = var12_12;
                var24_24 = var11_11;
                var25_25 = var10_10;
                var26_26 = var7_7;
            } else if (var18_18.equals("versionCodeMajor")) {
                var21_21 = var2_2.getAttributeIntValue(var17_17, 0);
                var19_19 = var16_16;
                var20_20 = var15_15;
                var22_22 = var13_13;
                var23_23 = var12_12;
                var24_24 = var11_11;
                var25_25 = var10_10;
                var26_26 = var7_7;
            } else if (var18_18.equals("revisionCode")) {
                var22_22 = var2_2.getAttributeIntValue(var17_17, 0);
                var19_19 = var16_16;
                var20_20 = var15_15;
                var21_21 = var14_14;
                var23_23 = var12_12;
                var24_24 = var11_11;
                var25_25 = var10_10;
                var26_26 = var7_7;
            } else if (var18_18.equals("coreApp")) {
                var23_23 = var2_2.getAttributeBooleanValue(var17_17, false);
                var19_19 = var16_16;
                var20_20 = var15_15;
                var21_21 = var14_14;
                var22_22 = var13_13;
                var24_24 = var11_11;
                var25_25 = var10_10;
                var26_26 = var7_7;
            } else if (var18_18.equals("isolatedSplits")) {
                var24_24 = var2_2.getAttributeBooleanValue(var17_17, false);
                var19_19 = var16_16;
                var20_20 = var15_15;
                var21_21 = var14_14;
                var22_22 = var13_13;
                var23_23 = var12_12;
                var25_25 = var10_10;
                var26_26 = var7_7;
            } else if (var18_18.equals("configForSplit")) {
                var26_26 = var2_2.getAttributeValue(var17_17);
                var19_19 = var16_16;
                var20_20 = var15_15;
                var21_21 = var14_14;
                var22_22 = var13_13;
                var23_23 = var12_12;
                var24_24 = var11_11;
                var25_25 = var10_10;
            } else if (var18_18.equals("isFeatureSplit")) {
                var25_25 = var2_2.getAttributeBooleanValue(var17_17, false);
                var19_19 = var16_16;
                var20_20 = var15_15;
                var21_21 = var14_14;
                var22_22 = var13_13;
                var23_23 = var12_12;
                var24_24 = var11_11;
                var26_26 = var7_7;
            } else {
                var19_19 = var16_16;
                var20_20 = var15_15;
                var21_21 = var14_14;
                var22_22 = var13_13;
                var23_23 = var12_12;
                var24_24 = var11_11;
                var25_25 = var10_10;
                var26_26 = var7_7;
                if (var18_18.equals("isSplitRequired")) {
                    var9_9 = var2_2.getAttributeBooleanValue(var17_17, false);
                    var26_26 = var7_7;
                    var25_25 = var10_10;
                    var24_24 = var11_11;
                    var23_23 = var12_12;
                    var22_22 = var13_13;
                    var21_21 = var14_14;
                    var20_20 = var15_15;
                    var19_19 = var16_16;
                }
            }
            var16_16 = var19_19;
            var15_15 = var20_20;
            var14_14 = var21_21;
            var13_13 = var22_22;
            var12_12 = var23_23;
            var11_11 = var24_24;
            var10_10 = var25_25;
            var7_7 = var26_26;
        }
        var17_17 = var1_1.getDepth();
        var19_19 = 1;
        var20_20 = var17_17 + 1;
        var18_18 = new ArrayList<E>();
        var24_24 = true;
        var23_23 = false;
        var27_27 = false;
        var28_28 = false;
        var25_25 = false;
        var26_26 = var8_8;
        var17_17 = var6_6;
        var6_6 = var20_20;
        while ((var20_20 = var1_1.next()) != var19_19) {
            block32 : {
                block33 : {
                    if (var20_20 == 3) {
                        if (var1_1.getDepth() < var6_6) return new ApkLite(var0, (String)var4_4.first, (String)var4_4.second, var10_10, var7_7, var26_26, var9_9, var15_15, var14_14, var13_13, var16_16, (List<VerifierInfo>)var18_18, var3_3, var12_12, var25_25, var27_27, var28_28, var23_23, var24_24, var11_11, var17_17, var5_5);
                    }
                    if (var20_20 == 3 || var20_20 == 4 || var1_1.getDepth() != var6_6) break block32;
                    var8_8 = var1_1.getName();
                    var19_19 = var6_6;
                    if (!"package-verifier".equals(var8_8)) break block33;
                    var8_8 = PackageParser.parseVerifier(var2_2);
                    if (var8_8 != null) {
                        var18_18.add(var8_8);
                    }
                    break block32;
                }
                if ("application".equals(var1_1.getName())) {
                    var21_21 = 0;
                    var6_6 = var20_20;
                    for (var20_20 = var21_21; var20_20 < var2_2.getAttributeCount(); ++var20_20) {
                        var8_8 = var2_2.getAttributeName(var20_20);
                        if ("debuggable".equals(var8_8)) {
                            var25_25 = var2_2.getAttributeBooleanValue(var20_20, false);
                        }
                        if ("multiArch".equals(var8_8)) {
                            var27_27 = var2_2.getAttributeBooleanValue(var20_20, false);
                        }
                        if ("use32bitAbi".equals(var8_8)) {
                            var28_28 = var2_2.getAttributeBooleanValue(var20_20, false);
                        }
                        if ("extractNativeLibs".equals(var8_8)) {
                            var24_24 = var2_2.getAttributeBooleanValue(var20_20, true);
                        }
                        if (!"useEmbeddedDex".equals(var8_8)) continue;
                        var23_23 = var2_2.getAttributeBooleanValue(var20_20, false);
                    }
                    var6_6 = var19_19;
                    var19_19 = 1;
                    continue;
                }
                if (!"uses-split".equals(var1_1.getName())) ** GOTO lbl177
                if (var26_26 != null) {
                    Slog.w("PackageParser", "Only one <uses-split> permitted. Ignoring others.");
                } else {
                    var26_26 = var2_2.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                    if (var26_26 == null) throw new PackageParserException(-108, "<uses-split> tag requires 'android:name' attribute");
                    var6_6 = var19_19;
                    var19_19 = 1;
                    continue;
lbl177: // 1 sources:
                    if ("uses-sdk".equals(var1_1.getName())) {
                        for (var6_6 = 0; var6_6 < var2_2.getAttributeCount(); ++var6_6) {
                            var8_8 = var2_2.getAttributeName(var6_6);
                            if ("targetSdkVersion".equals(var8_8)) {
                                var5_5 = var2_2.getAttributeIntValue(var6_6, 0);
                            }
                            if (!"minSdkVersion".equals(var8_8)) continue;
                            var17_17 = var2_2.getAttributeIntValue(var6_6, 1);
                        }
                        var20_20 = 1;
                        var6_6 = var19_19;
                        var19_19 = var20_20;
                        continue;
                    }
                }
            }
            var19_19 = 1;
        }
        return new ApkLite(var0, (String)var4_4.first, (String)var4_4.second, var10_10, var7_7, var26_26, var9_9, var15_15, var14_14, var13_13, var16_16, (List<VerifierInfo>)var18_18, var3_3, var12_12, var25_25, var27_27, var28_28, var23_23, var24_24, var11_11, var17_17, var5_5);
    }

    /*
     * Exception decompiling
     */
    private static ApkLite parseApkLiteInner(File var0, FileDescriptor var1_6, String var2_9, int var3_10) throws PackageParserException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 13[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Package parseBaseApk(File var1_1, AssetManager var2_4, int var3_9) throws PackageParserException {
        block16 : {
            block14 : {
                block15 : {
                    var4_10 = var1_1.getAbsolutePath();
                    if (var4_10.startsWith("/mnt/expand/")) {
                        var5_11 = var4_10.indexOf(47, "/mnt/expand/".length());
                        var6_12 = var4_10.substring("/mnt/expand/".length(), var5_11);
                    } else {
                        var6_12 = null;
                    }
                    this.mParseError = 1;
                    this.mArchiveSourcePath = var1_1.getAbsolutePath();
                    var7_13 = null;
                    var8_14 = null;
                    var1_1 = var9_15 = null;
                    var5_11 = var2_4.findCookieForPath(var4_10);
                    if (var5_11 == 0) break block14;
                    var1_1 = var9_15;
                    var9_15 = var2_4.openXmlResourceParser(var5_11, "AndroidManifest.xml");
                    var7_13 = new Resources((AssetManager)var2_4, this.mMetrics, null);
                    var1_1 = new String[1];
                    var2_4 = this.parseBaseApk(var4_10, (Resources)var7_13, (XmlResourceParser)var9_15, var3_9, (String[])var1_1);
                    if (var2_4 == null) break block15;
                    var2_4.setVolumeUuid((String)var6_12);
                    var2_4.setApplicationVolumeUuid((String)var6_12);
                    var2_4.setBaseCodePath(var4_10);
                    var2_4.setSigningDetails(SigningDetails.UNKNOWN);
                    IoUtils.closeQuietly((AutoCloseable)var9_15);
                    return var2_4;
                }
                try {
                    var3_9 = this.mParseError;
                    var6_12 = new StringBuilder();
                    var6_12.append(var4_10);
                    var6_12.append(" (at ");
                    var6_12.append(var9_15.getPositionDescription());
                    var6_12.append("): ");
                    var6_12.append((String)var1_1[0]);
                    var2_4 = new PackageParserException(var3_9, var6_12.toString());
                    throw var2_4;
                }
                catch (Throwable var1_2) {}
                catch (Exception var1_3) {
                    var2_4 = var9_15;
                    var9_15 = var1_3;
                    return var9_15;
                }
                catch (PackageParserException var2_5) {
                    var1_1 = var9_15;
                    return var1_1;
                }
                ** finally { 
lbl53: // 1 sources:
                break block16;
            }
            var1_1 = var9_15;
            try {
                var1_1 = var9_15;
                var1_1 = var9_15;
                var6_12 = new StringBuilder();
                var1_1 = var9_15;
                var6_12.append("Failed adding asset path: ");
                var1_1 = var9_15;
                var6_12.append(var4_10);
                var1_1 = var9_15;
                var2_4 = new PackageParserException(-101, var6_12.toString());
                var1_1 = var9_15;
                throw var2_4;
            }
            catch (Throwable var2_6) {
                var9_15 = var1_1;
                var1_1 = var2_6;
                break block16;
            }
            catch (Exception var9_16) {}
            var1_1 = var2_4 = var7_13;
            var1_1 = var2_4;
            var1_1 = var2_4;
            var7_13 = new StringBuilder();
            var1_1 = var2_4;
            var7_13.append("Failed to read manifest from ");
            var1_1 = var2_4;
            var7_13.append(var4_10);
            var1_1 = var2_4;
            var6_12 = new PackageParserException(-102, var7_13.toString(), (Throwable)var9_15);
            var1_1 = var2_4;
            throw var6_12;
            catch (PackageParserException var2_7) {
                var1_1 = var8_14;
            }
            throw var2_8;
        }
        IoUtils.closeQuietly((AutoCloseable)var9_15);
        throw var1_1;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Package parseBaseApk(String object, Resources resources, XmlResourceParser xmlResourceParser, int n, String[] arrstring) throws XmlPullParserException, IOException {
        Object object22;
        Object object3;
        block6 : {
            try {
                object22 = PackageParser.parsePackageSplitNames(xmlResourceParser, xmlResourceParser);
                object3 = (String)((Pair)object22).first;
                object22 = (String)((Pair)object22).second;
                if (TextUtils.isEmpty((CharSequence)object22)) break block6;
                object = new StringBuilder();
                ((StringBuilder)object).append("Expected base APK, but found split ");
                ((StringBuilder)object).append((String)object22);
                arrstring[0] = ((StringBuilder)object).toString();
                this.mParseError = -106;
                return null;
            }
            catch (PackageParserException packageParserException) {
                this.mParseError = -106;
                return null;
            }
        }
        object22 = this.mCallback;
        if (object22 != null && (object = object22.getOverlayPaths((String)object3, (String)object)) != null && ((CharSequence)object).length > 0) {
            for (Object object22 : object) {
                resources.getAssets().addOverlayPath((String)object22);
            }
        }
        object3 = new Package((String)object3);
        object = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifest);
        ((Package)object3).mVersionCode = ((TypedArray)object).getInteger(1, 0);
        ((Package)object3).mVersionCodeMajor = ((TypedArray)object).getInteger(11, 0);
        ((Package)object3).applicationInfo.setVersionCode(((Package)object3).getLongVersionCode());
        ((Package)object3).baseRevisionCode = ((TypedArray)object).getInteger(5, 0);
        ((Package)object3).mVersionName = ((TypedArray)object).getNonConfigurationString(2, 0);
        if (((Package)object3).mVersionName != null) {
            ((Package)object3).mVersionName = ((Package)object3).mVersionName.intern();
        }
        ((Package)object3).coreApp = xmlResourceParser.getAttributeBooleanValue(null, "coreApp", false);
        object3.applicationInfo.compileSdkVersion = ((Package)object3).mCompileSdkVersion = ((TypedArray)object).getInteger(9, 0);
        ((Package)object3).mCompileSdkVersionCodename = ((TypedArray)object).getNonConfigurationString(10, 0);
        if (((Package)object3).mCompileSdkVersionCodename != null) {
            ((Package)object3).mCompileSdkVersionCodename = ((Package)object3).mCompileSdkVersionCodename.intern();
        }
        object3.applicationInfo.compileSdkVersionCodename = ((Package)object3).mCompileSdkVersionCodename;
        ((TypedArray)object).recycle();
        return this.parseBaseApkCommon((Package)object3, null, resources, xmlResourceParser, n, arrstring);
    }

    private boolean parseBaseApkChild(Package object, Resources object2, XmlResourceParser xmlResourceParser, int n, String[] arrstring) throws XmlPullParserException, IOException {
        Object object3 = xmlResourceParser.getAttributeValue(null, TAG_PACKAGE);
        if (PackageParser.validateName((String)object3, true, false) != null) {
            this.mParseError = -106;
            return false;
        }
        if (((String)object3).equals(((Package)object).packageName)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Child package name cannot be equal to parent package name: ");
            ((StringBuilder)object2).append(((Package)object).packageName);
            object = ((StringBuilder)object2).toString();
            Slog.w(TAG, (String)object);
            arrstring[0] = object;
            this.mParseError = -108;
            return false;
        }
        if (((Package)object).hasChildPackage((String)object3)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Duplicate child package:");
            ((StringBuilder)object).append((String)object3);
            object = ((StringBuilder)object).toString();
            Slog.w(TAG, (String)object);
            arrstring[0] = object;
            this.mParseError = -108;
            return false;
        }
        object3 = new Package((String)object3);
        ((Package)object3).mVersionCode = ((Package)object).mVersionCode;
        ((Package)object3).baseRevisionCode = ((Package)object).baseRevisionCode;
        ((Package)object3).mVersionName = ((Package)object).mVersionName;
        object3.applicationInfo.targetSdkVersion = object.applicationInfo.targetSdkVersion;
        object3.applicationInfo.minSdkVersion = object.applicationInfo.minSdkVersion;
        if ((object2 = this.parseBaseApkCommon((Package)object3, CHILD_PACKAGE_TAGS, (Resources)object2, xmlResourceParser, n, arrstring)) == null) {
            return false;
        }
        if (((Package)object).childPackages == null) {
            ((Package)object).childPackages = new ArrayList();
        }
        ((Package)object).childPackages.add((Package)object2);
        ((Package)object2).parentPackage = object;
        return true;
    }

    private Package parseBaseApkCommon(Package object, Set<String> object2, Resources object3, XmlResourceParser object4, int n, String[] arrstring) throws XmlPullParserException, IOException {
        int n2;
        Object object5;
        int n3;
        this.mParseInstrumentationArgs = null;
        Object object6 = ((Resources)object3).obtainAttributes((AttributeSet)object4, R.styleable.AndroidManifest);
        String string2 = ((TypedArray)object6).getNonConfigurationString(0, 0);
        int n4 = 1;
        if (string2 != null && string2.length() > 0) {
            object5 = PackageParser.validateName(string2, true, true);
            if (object5 != null && !"android".equals(((Package)object).packageName)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("<manifest> specifies bad sharedUserId name \"");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("\": ");
                ((StringBuilder)object).append((String)object5);
                arrstring[0] = ((StringBuilder)object).toString();
                this.mParseError = -107;
                return null;
            }
            ((Package)object).mSharedUserId = string2.intern();
            ((Package)object).mSharedUserLabel = ((TypedArray)object6).getResourceId(3, 0);
        }
        object.applicationInfo.installLocation = ((Package)object).installLocation = ((TypedArray)object6).getInteger(4, -1);
        object.applicationInfo.targetSandboxVersion = n3 = ((TypedArray)object6).getInteger(7, 1);
        if ((n & 8) != 0) {
            object5 = ((Package)object).applicationInfo;
            object5.flags |= 262144;
        }
        if (((TypedArray)object6).getBoolean(6, false)) {
            object5 = ((Package)object).applicationInfo;
            object5.privateFlags |= 32768;
        }
        int n5 = 1;
        int n6 = object4.getDepth();
        int n7 = 1;
        int n8 = 1;
        int n9 = 1;
        int n10 = 0;
        int n11 = 1;
        int n12 = 1;
        do {
            block59 : {
                int n13;
                int n14;
                block63 : {
                    block67 : {
                        block72 : {
                            block88 : {
                                int n15;
                                int n16;
                                int n17;
                                Object object7;
                                block86 : {
                                    block87 : {
                                        block85 : {
                                            block84 : {
                                                block83 : {
                                                    block82 : {
                                                        block81 : {
                                                            block80 : {
                                                                block79 : {
                                                                    block78 : {
                                                                        block77 : {
                                                                            block76 : {
                                                                                Object object8;
                                                                                Object object9;
                                                                                block75 : {
                                                                                    block74 : {
                                                                                        block73 : {
                                                                                            block71 : {
                                                                                                block70 : {
                                                                                                    block69 : {
                                                                                                        block68 : {
                                                                                                            block66 : {
                                                                                                                block64 : {
                                                                                                                    block65 : {
                                                                                                                        block61 : {
                                                                                                                            block62 : {
                                                                                                                                block60 : {
                                                                                                                                    object5 = object2;
                                                                                                                                    n2 = object4.next();
                                                                                                                                    if (n2 == n4 || n2 == 3 && object4.getDepth() <= n6) break;
                                                                                                                                    if (n2 == 3 || n2 == 4) break block59;
                                                                                                                                    object7 = object4.getName();
                                                                                                                                    if (object5 == null || object5.contains(object7)) break block60;
                                                                                                                                    object5 = new StringBuilder();
                                                                                                                                    object5.append("Skipping unsupported element under <manifest>: ");
                                                                                                                                    object5.append((String)object7);
                                                                                                                                    object5.append(" at ");
                                                                                                                                    object5.append(this.mArchiveSourcePath);
                                                                                                                                    object5.append(" ");
                                                                                                                                    object5.append(object4.getPositionDescription());
                                                                                                                                    Slog.w(TAG, object5.toString());
                                                                                                                                    XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                                                                                                    break block59;
                                                                                                                                }
                                                                                                                                if (!((String)object7).equals(TAG_APPLICATION)) break block61;
                                                                                                                                if (n10 == 0) break block62;
                                                                                                                                Slog.w(TAG, "<manifest> has more than one <application>");
                                                                                                                                XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                                                                                                break block59;
                                                                                                                            }
                                                                                                                            n2 = 1;
                                                                                                                            if (!this.parseBaseApplication((Package)object, (Resources)object3, (XmlResourceParser)object4, n, arrstring)) {
                                                                                                                                return null;
                                                                                                                            }
                                                                                                                            n10 = n8;
                                                                                                                            n4 = n9;
                                                                                                                            n9 = n12;
                                                                                                                            n8 = n5;
                                                                                                                            n12 = n10;
                                                                                                                            break block63;
                                                                                                                        }
                                                                                                                        n4 = n5;
                                                                                                                        if (!((String)object7).equals(TAG_OVERLAY)) break block64;
                                                                                                                        object6 = ((Resources)object3).obtainAttributes((AttributeSet)object4, R.styleable.AndroidManifestResourceOverlay);
                                                                                                                        ((Package)object).mOverlayTarget = ((TypedArray)object6).getString(1);
                                                                                                                        ((Package)object).mOverlayTargetName = ((TypedArray)object6).getString(3);
                                                                                                                        ((Package)object).mOverlayCategory = ((TypedArray)object6).getString(2);
                                                                                                                        ((Package)object).mOverlayPriority = ((TypedArray)object6).getInt(0, 0);
                                                                                                                        ((Package)object).mOverlayIsStatic = ((TypedArray)object6).getBoolean(4, false);
                                                                                                                        object7 = ((TypedArray)object6).getString(5);
                                                                                                                        object5 = ((TypedArray)object6).getString(6);
                                                                                                                        ((TypedArray)object6).recycle();
                                                                                                                        if (((Package)object).mOverlayTarget == null) {
                                                                                                                            arrstring[0] = "<overlay> does not specify a target package";
                                                                                                                            this.mParseError = -108;
                                                                                                                            return null;
                                                                                                                        }
                                                                                                                        if (((Package)object).mOverlayPriority < 0 || ((Package)object).mOverlayPriority > 9999) break block65;
                                                                                                                        if (!this.checkOverlayRequiredSystemProperty((String)object7, (String)object5)) {
                                                                                                                            object2 = new StringBuilder();
                                                                                                                            ((StringBuilder)object2).append("Skipping target and overlay pair ");
                                                                                                                            ((StringBuilder)object2).append(((Package)object).mOverlayTarget);
                                                                                                                            ((StringBuilder)object2).append(" and ");
                                                                                                                            ((StringBuilder)object2).append(((Package)object).baseCodePath);
                                                                                                                            ((StringBuilder)object2).append(": overlay ignored due to required system property: ");
                                                                                                                            ((StringBuilder)object2).append((String)object7);
                                                                                                                            ((StringBuilder)object2).append(" with value: ");
                                                                                                                            ((StringBuilder)object2).append((String)object5);
                                                                                                                            Slog.i(TAG, ((StringBuilder)object2).toString());
                                                                                                                            return null;
                                                                                                                        }
                                                                                                                        object5 = ((Package)object).applicationInfo;
                                                                                                                        object5.privateFlags |= 268435456;
                                                                                                                        XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                                                                                        n5 = n8;
                                                                                                                        n2 = n9;
                                                                                                                        n9 = n12;
                                                                                                                        n8 = n4;
                                                                                                                        n4 = n2;
                                                                                                                        n2 = n10;
                                                                                                                        n12 = n5;
                                                                                                                        break block63;
                                                                                                                    }
                                                                                                                    arrstring[0] = "<overlay> priority must be between 0 and 9999";
                                                                                                                    this.mParseError = -108;
                                                                                                                    return null;
                                                                                                                }
                                                                                                                if (!((String)object7).equals(TAG_KEY_SETS)) break block66;
                                                                                                                if (!this.parseKeySets((Package)object, (Resources)object3, (XmlResourceParser)object4, arrstring)) {
                                                                                                                    return null;
                                                                                                                }
                                                                                                                break block67;
                                                                                                            }
                                                                                                            if (!((String)object7).equals(TAG_PERMISSION_GROUP)) break block68;
                                                                                                            if (!this.parsePermissionGroup((Package)object, n, (Resources)object3, (XmlResourceParser)object4, arrstring)) {
                                                                                                                return null;
                                                                                                            }
                                                                                                            break block67;
                                                                                                        }
                                                                                                        n16 = n11;
                                                                                                        if (!((String)object7).equals(TAG_PERMISSION)) break block69;
                                                                                                        if (!this.parsePermission((Package)object, (Resources)object3, (XmlResourceParser)object4, arrstring)) {
                                                                                                            return null;
                                                                                                        }
                                                                                                        break block67;
                                                                                                    }
                                                                                                    if (!((String)object7).equals(TAG_PERMISSION_TREE)) break block70;
                                                                                                    if (!this.parsePermissionTree((Package)object, (Resources)object3, (XmlResourceParser)object4, arrstring)) {
                                                                                                        return null;
                                                                                                    }
                                                                                                    break block67;
                                                                                                }
                                                                                                if (!((String)object7).equals(TAG_USES_PERMISSION)) break block71;
                                                                                                if (!this.parseUsesPermission((Package)object, (Resources)object3, (XmlResourceParser)object4)) {
                                                                                                    return null;
                                                                                                }
                                                                                                break block67;
                                                                                            }
                                                                                            if (((String)object7).equals(TAG_USES_PERMISSION_SDK_M) || ((String)object7).equals(TAG_USES_PERMISSION_SDK_23)) break block72;
                                                                                            if (!((String)object7).equals(TAG_USES_CONFIGURATION)) break block73;
                                                                                            object5 = new ConfigurationInfo();
                                                                                            object6 = ((Resources)object3).obtainAttributes((AttributeSet)object4, R.styleable.AndroidManifestUsesConfiguration);
                                                                                            object5.reqTouchScreen = ((TypedArray)object6).getInt(0, 0);
                                                                                            object5.reqKeyboardType = ((TypedArray)object6).getInt(1, 0);
                                                                                            if (((TypedArray)object6).getBoolean(2, false)) {
                                                                                                object5.reqInputFeatures |= 1;
                                                                                            }
                                                                                            object5.reqNavigation = ((TypedArray)object6).getInt(3, 0);
                                                                                            if (((TypedArray)object6).getBoolean(4, false)) {
                                                                                                object5.reqInputFeatures |= 2;
                                                                                            }
                                                                                            ((TypedArray)object6).recycle();
                                                                                            ((Package)object).configPreferences = ArrayUtils.add(((Package)object).configPreferences, object5);
                                                                                            XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                                                            n5 = n8;
                                                                                            n2 = n9;
                                                                                            n9 = n12;
                                                                                            n8 = n4;
                                                                                            n4 = n2;
                                                                                            n2 = n10;
                                                                                            n11 = n16;
                                                                                            n12 = n5;
                                                                                            break block63;
                                                                                        }
                                                                                        if (!((String)object7).equals(TAG_USES_FEATURE)) break block74;
                                                                                        object5 = this.parseUsesFeature((Resources)object3, (AttributeSet)object4);
                                                                                        ((Package)object).reqFeatures = ArrayUtils.add(((Package)object).reqFeatures, object5);
                                                                                        if (object5.name == null) {
                                                                                            object7 = new ConfigurationInfo();
                                                                                            ((ConfigurationInfo)object7).reqGlEsVersion = object5.reqGlEsVersion;
                                                                                            ((Package)object).configPreferences = ArrayUtils.add(((Package)object).configPreferences, object7);
                                                                                        }
                                                                                        XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                                                        break block67;
                                                                                    }
                                                                                    if (!((String)object7).equals(TAG_FEATURE_GROUP)) break block75;
                                                                                    object7 = new FeatureGroupInfo();
                                                                                    object5 = null;
                                                                                    n2 = object4.getDepth();
                                                                                    while ((n11 = object4.next()) != 1 && (n11 != 3 || object4.getDepth() > n2)) {
                                                                                        if (n11 == 3 || n11 == 4) continue;
                                                                                        object8 = object4.getName();
                                                                                        if (((String)object8).equals(TAG_USES_FEATURE)) {
                                                                                            object9 = this.parseUsesFeature((Resources)object3, (AttributeSet)object4);
                                                                                            ((FeatureInfo)object9).flags |= 1;
                                                                                            object5 = ArrayUtils.add(object5, object9);
                                                                                        } else {
                                                                                            object9 = new StringBuilder();
                                                                                            ((StringBuilder)object9).append("Unknown element under <feature-group>: ");
                                                                                            ((StringBuilder)object9).append((String)object8);
                                                                                            ((StringBuilder)object9).append(" at ");
                                                                                            ((StringBuilder)object9).append(this.mArchiveSourcePath);
                                                                                            ((StringBuilder)object9).append(" ");
                                                                                            ((StringBuilder)object9).append(object4.getPositionDescription());
                                                                                            Slog.w(TAG, ((StringBuilder)object9).toString());
                                                                                        }
                                                                                        XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                                                    }
                                                                                    if (object5 != null) {
                                                                                        ((FeatureGroupInfo)object7).features = new FeatureInfo[object5.size()];
                                                                                        ((FeatureGroupInfo)object7).features = object5.toArray(((FeatureGroupInfo)object7).features);
                                                                                    }
                                                                                    ((Package)object).featureGroups = ArrayUtils.add(((Package)object).featureGroups, object7);
                                                                                    n5 = n8;
                                                                                    n2 = n9;
                                                                                    n9 = n12;
                                                                                    n8 = n4;
                                                                                    n4 = n2;
                                                                                    n2 = n10;
                                                                                    n11 = n16;
                                                                                    n12 = n5;
                                                                                    break block63;
                                                                                }
                                                                                n2 = n10;
                                                                                if (!((String)object7).equals(TAG_USES_SDK)) break block76;
                                                                                if (SDK_VERSION > 0) {
                                                                                    object8 = ((Resources)object3).obtainAttributes((AttributeSet)object4, R.styleable.AndroidManifestUsesSdk);
                                                                                    n10 = 1;
                                                                                    object7 = null;
                                                                                    n5 = 0;
                                                                                    object5 = null;
                                                                                    object9 = ((TypedArray)object8).peekValue(0);
                                                                                    n11 = n10;
                                                                                    object6 = object7;
                                                                                    if (object9 != null) {
                                                                                        if (((TypedValue)object9).type == 3 && ((TypedValue)object9).string != null) {
                                                                                            object6 = ((TypedValue)object9).string.toString();
                                                                                            n11 = n10;
                                                                                        } else {
                                                                                            n11 = ((TypedValue)object9).data;
                                                                                            object6 = object7;
                                                                                        }
                                                                                    }
                                                                                    if ((object7 = ((TypedArray)object8).peekValue(1)) != null) {
                                                                                        if (((TypedValue)object7).type == 3 && ((TypedValue)object7).string != null) {
                                                                                            object9 = ((TypedValue)object7).string.toString();
                                                                                            object7 = object6;
                                                                                            n10 = n5;
                                                                                            object5 = object9;
                                                                                            if (object6 == null) {
                                                                                                object7 = object9;
                                                                                                n10 = n5;
                                                                                                object5 = object9;
                                                                                            }
                                                                                        } else {
                                                                                            n10 = ((TypedValue)object7).data;
                                                                                            object7 = object6;
                                                                                        }
                                                                                    } else {
                                                                                        n10 = n11;
                                                                                        object5 = object6;
                                                                                        object7 = object6;
                                                                                    }
                                                                                    ((TypedArray)object8).recycle();
                                                                                    n11 = PackageParser.computeMinSdkVersion(n11, (String)object7, SDK_VERSION, SDK_CODENAMES, arrstring);
                                                                                    if (n11 < 0) {
                                                                                        this.mParseError = -12;
                                                                                        return null;
                                                                                    }
                                                                                    if ((n10 = PackageParser.computeTargetSdkVersion(n10, (String)object5, SDK_CODENAMES, arrstring)) < 0) {
                                                                                        this.mParseError = -12;
                                                                                        return null;
                                                                                    }
                                                                                    object6 = object8;
                                                                                    object.applicationInfo.minSdkVersion = n11;
                                                                                    object.applicationInfo.targetSdkVersion = n10;
                                                                                }
                                                                                XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                                                n10 = n8;
                                                                                n11 = n9;
                                                                                n9 = n12;
                                                                                n8 = n4;
                                                                                n4 = n11;
                                                                                n11 = n16;
                                                                                n12 = n10;
                                                                                break block63;
                                                                            }
                                                                            if (!((String)object7).equals(TAG_SUPPORT_SCREENS)) break block77;
                                                                            object6 = ((Resources)object3).obtainAttributes((AttributeSet)object4, R.styleable.AndroidManifestSupportsScreens);
                                                                            object.applicationInfo.requiresSmallestWidthDp = ((TypedArray)object6).getInteger(6, 0);
                                                                            object.applicationInfo.compatibleWidthLimitDp = ((TypedArray)object6).getInteger(7, 0);
                                                                            object.applicationInfo.largestWidthLimitDp = ((TypedArray)object6).getInteger(8, 0);
                                                                            n10 = ((TypedArray)object6).getInteger(1, n4);
                                                                            n4 = ((TypedArray)object6).getInteger(2, n12);
                                                                            n11 = ((TypedArray)object6).getInteger(3, n16);
                                                                            n7 = ((TypedArray)object6).getInteger(5, n7);
                                                                            n12 = ((TypedArray)object6).getInteger(4, n8);
                                                                            n5 = ((TypedArray)object6).getInteger(0, n9);
                                                                            ((TypedArray)object6).recycle();
                                                                            XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                                            n8 = n10;
                                                                            n9 = n4;
                                                                            n4 = n5;
                                                                            break block63;
                                                                        }
                                                                        n14 = n7;
                                                                        n13 = n8;
                                                                        n17 = n9;
                                                                        n15 = n12;
                                                                        if (!((String)object7).equals(TAG_PROTECTED_BROADCAST)) break block78;
                                                                        object6 = ((Resources)object3).obtainAttributes((AttributeSet)object4, R.styleable.AndroidManifestProtectedBroadcast);
                                                                        object5 = ((TypedArray)object6).getNonResourceString(0);
                                                                        ((TypedArray)object6).recycle();
                                                                        if (object5 != null) {
                                                                            if (((Package)object).protectedBroadcasts == null) {
                                                                                ((Package)object).protectedBroadcasts = new ArrayList();
                                                                            }
                                                                            if (!((Package)object).protectedBroadcasts.contains(object5)) {
                                                                                ((Package)object).protectedBroadcasts.add(object5.intern());
                                                                            }
                                                                        }
                                                                        XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                                        n8 = n4;
                                                                        n9 = n15;
                                                                        n12 = n13;
                                                                        n7 = n14;
                                                                        n4 = n17;
                                                                        n11 = n16;
                                                                        break block63;
                                                                    }
                                                                    if (!((String)object7).equals(TAG_INSTRUMENTATION)) break block79;
                                                                    if (this.parseInstrumentation((Package)object, (Resources)object3, (XmlResourceParser)object4, arrstring) == null) {
                                                                        return null;
                                                                    }
                                                                    break block67;
                                                                }
                                                                if (!((String)object7).equals(TAG_ORIGINAL_PACKAGE)) break block80;
                                                                object6 = ((Resources)object3).obtainAttributes((AttributeSet)object4, R.styleable.AndroidManifestOriginalPackage);
                                                                object5 = ((TypedArray)object6).getNonConfigurationString(0, 0);
                                                                if (!((Package)object).packageName.equals(object5)) {
                                                                    if (((Package)object).mOriginalPackages == null) {
                                                                        ((Package)object).mOriginalPackages = new ArrayList();
                                                                        ((Package)object).mRealPackage = ((Package)object).packageName;
                                                                    }
                                                                    ((Package)object).mOriginalPackages.add((String)object5);
                                                                }
                                                                ((TypedArray)object6).recycle();
                                                                XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                                n8 = n4;
                                                                n9 = n15;
                                                                n12 = n13;
                                                                n7 = n14;
                                                                n4 = n17;
                                                                n11 = n16;
                                                                break block63;
                                                            }
                                                            if (!((String)object7).equals(TAG_ADOPT_PERMISSIONS)) break block81;
                                                            object6 = ((Resources)object3).obtainAttributes((AttributeSet)object4, R.styleable.AndroidManifestOriginalPackage);
                                                            object5 = ((TypedArray)object6).getNonConfigurationString(0, 0);
                                                            ((TypedArray)object6).recycle();
                                                            if (object5 != null) {
                                                                if (((Package)object).mAdoptPermissions == null) {
                                                                    ((Package)object).mAdoptPermissions = new ArrayList();
                                                                }
                                                                ((Package)object).mAdoptPermissions.add((String)object5);
                                                            }
                                                            XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                            n8 = n4;
                                                            n9 = n15;
                                                            n12 = n13;
                                                            n7 = n14;
                                                            n4 = n17;
                                                            n11 = n16;
                                                            break block63;
                                                        }
                                                        if (!((String)object7).equals(TAG_USES_GL_TEXTURE)) break block82;
                                                        XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                        break block59;
                                                    }
                                                    if (!((String)object7).equals(TAG_COMPATIBLE_SCREENS)) break block83;
                                                    XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                    break block59;
                                                }
                                                if (!((String)object7).equals(TAG_SUPPORTS_INPUT)) break block84;
                                                XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                                break block59;
                                            }
                                            if (!((String)object7).equals(TAG_EAT_COMMENT)) break block85;
                                            XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                            break block59;
                                        }
                                        if (!((String)object7).equals(TAG_PACKAGE)) break block86;
                                        if (MULTI_PACKAGE_APK_ENABLED) break block87;
                                        XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                        break block59;
                                    }
                                    if (!this.parseBaseApkChild((Package)object, (Resources)object3, (XmlResourceParser)object4, n, arrstring)) {
                                        return null;
                                    }
                                    break block67;
                                }
                                if (!((String)object7).equals(TAG_RESTRICT_UPDATE)) break block88;
                                if ((n & 16) != 0) {
                                    object6 = ((Resources)object3).obtainAttributes((AttributeSet)object4, R.styleable.AndroidManifestRestrictUpdate);
                                    object7 = ((TypedArray)object6).getNonConfigurationString(0, 0);
                                    ((TypedArray)object6).recycle();
                                    ((Package)object).restrictUpdateHash = null;
                                    if (object7 != null) {
                                        n8 = ((String)object7).length();
                                        object5 = new byte[n8 / 2];
                                        for (n9 = 0; n9 < n8; n9 += 2) {
                                            object5[n9 / 2] = (byte)((Character.digit(((String)object7).charAt(n9), 16) << 4) + Character.digit(((String)object7).charAt(n9 + 1), 16));
                                        }
                                        ((Package)object).restrictUpdateHash = object5;
                                    }
                                }
                                XmlUtils.skipCurrentTag((XmlPullParser)object4);
                                n8 = n4;
                                n9 = n15;
                                n12 = n13;
                                n4 = n17;
                                n7 = n14;
                                n11 = n16;
                                break block63;
                            }
                            object5 = new StringBuilder();
                            object5.append("Unknown element under <manifest>: ");
                            object5.append(object4.getName());
                            object5.append(" at ");
                            object5.append(this.mArchiveSourcePath);
                            object5.append(" ");
                            object5.append(object4.getPositionDescription());
                            Slog.w(TAG, object5.toString());
                            XmlUtils.skipCurrentTag((XmlPullParser)object4);
                            break block59;
                        }
                        if (!this.parseUsesPermission((Package)object, (Resources)object3, (XmlResourceParser)object4)) {
                            return null;
                        }
                    }
                    n5 = n4;
                    n14 = n12;
                    n2 = n10;
                    n12 = n8;
                    n4 = n9;
                    n8 = n5;
                    n9 = n14;
                }
                n14 = n12;
                n10 = 1;
                n13 = n4;
                n12 = n9;
                n5 = n8;
                n4 = n10;
                n10 = n2;
                n8 = n14;
                n9 = n13;
                continue;
            }
            n4 = 1;
        } while (true);
        n4 = n9;
        if (n10 == 0 && ((Package)object).instrumentation.size() == 0) {
            arrstring[0] = "<manifest> does not contain an <application> or <instrumentation>";
            this.mParseError = -109;
        }
        n9 = NEW_PERMISSIONS.length;
        object2 = null;
        for (n = 0; n < n9; ++n) {
            object4 = NEW_PERMISSIONS[n];
            if (object.applicationInfo.targetSdkVersion >= ((NewPermissionInfo)object4).sdkVersion) break;
            object3 = object2;
            if (!((Package)object).requestedPermissions.contains(((NewPermissionInfo)object4).name)) {
                if (object2 == null) {
                    object2 = new StringBuilder(128);
                    ((StringBuilder)object2).append(((Package)object).packageName);
                    ((StringBuilder)object2).append(": compat added ");
                } else {
                    ((StringBuilder)object2).append(' ');
                }
                ((StringBuilder)object2).append(((NewPermissionInfo)object4).name);
                ((Package)object).requestedPermissions.add(((NewPermissionInfo)object4).name);
                ((Package)object).implicitPermissions.add(((NewPermissionInfo)object4).name);
                object3 = object2;
            }
            object2 = object3;
        }
        if (object2 != null) {
            Slog.i(TAG, ((StringBuilder)object2).toString());
        }
        n2 = PermissionManager.SPLIT_PERMISSIONS.size();
        for (n = 0; n < n2; ++n) {
            object2 = PermissionManager.SPLIT_PERMISSIONS.get(n);
            if (object.applicationInfo.targetSdkVersion >= ((PermissionManager.SplitPermissionInfo)object2).getTargetSdk() || !((Package)object).requestedPermissions.contains(((PermissionManager.SplitPermissionInfo)object2).getSplitPermission())) continue;
            object2 = ((PermissionManager.SplitPermissionInfo)object2).getNewPermissions();
            for (n9 = 0; n9 < object2.size(); ++n9) {
                object3 = (String)object2.get(n9);
                if (((Package)object).requestedPermissions.contains(object3)) continue;
                ((Package)object).requestedPermissions.add((String)object3);
                ((Package)object).implicitPermissions.add((String)object3);
            }
        }
        if (n5 < 0 || n5 > 0 && object.applicationInfo.targetSdkVersion >= 4) {
            object2 = ((Package)object).applicationInfo;
            ((ApplicationInfo)object2).flags |= 512;
        }
        if (n12 != 0) {
            object2 = ((Package)object).applicationInfo;
            ((ApplicationInfo)object2).flags |= 1024;
        }
        if (n11 < 0 || n11 > 0 && object.applicationInfo.targetSdkVersion >= 4) {
            object2 = ((Package)object).applicationInfo;
            ((ApplicationInfo)object2).flags |= 2048;
        }
        if (n7 < 0 || n7 > 0 && object.applicationInfo.targetSdkVersion >= 9) {
            object2 = ((Package)object).applicationInfo;
            ((ApplicationInfo)object2).flags |= 524288;
        }
        if (n8 < 0 || n8 > 0 && object.applicationInfo.targetSdkVersion >= 4) {
            object2 = ((Package)object).applicationInfo;
            ((ApplicationInfo)object2).flags |= 4096;
        }
        if (n4 < 0 || n4 > 0 && object.applicationInfo.targetSdkVersion >= 4) {
            object2 = ((Package)object).applicationInfo;
            ((ApplicationInfo)object2).flags |= 8192;
        }
        if (((Package)object).applicationInfo.usesCompatibilityMode()) {
            this.adjustPackageToBeUnresizeableAndUnpipable((Package)object);
        }
        return object;
    }

    @UnsupportedAppUsage
    private boolean parseBaseApplication(Package object, Resources object2, XmlResourceParser xmlResourceParser, int n, String[] arrstring) throws XmlPullParserException, IOException {
        Object object3;
        int n2;
        int n3;
        PackageParser packageParser = this;
        Object object4 = object;
        Object object5 = xmlResourceParser;
        ApplicationInfo applicationInfo = ((Package)object4).applicationInfo;
        String string2 = object4.applicationInfo.packageName;
        Object object6 = ((Resources)object2).obtainAttributes((AttributeSet)object5, R.styleable.AndroidManifestApplication);
        applicationInfo.iconRes = ((TypedArray)object6).getResourceId(2, 0);
        applicationInfo.roundIconRes = ((TypedArray)object6).getResourceId(42, 0);
        Object object7 = arrstring;
        if (!PackageParser.parsePackageItemInfo((Package)object, applicationInfo, arrstring, "<application>", (TypedArray)object6, false, 3, 1, 2, 42, 22, 30)) {
            ((TypedArray)object6).recycle();
            packageParser.mParseError = -108;
            return false;
        }
        if (applicationInfo.name != null) {
            applicationInfo.className = applicationInfo.name;
        }
        if ((object3 = ((TypedArray)object6).getNonConfigurationString(4, 1024)) != null) {
            applicationInfo.manageSpaceActivityName = PackageParser.buildClassName(string2, (CharSequence)object3, (String[])object7);
        }
        if (((TypedArray)object6).getBoolean(17, true)) {
            applicationInfo.flags |= 32768;
            object3 = ((TypedArray)object6).getNonConfigurationString(16, 1024);
            if (object3 != null) {
                applicationInfo.backupAgentName = PackageParser.buildClassName(string2, (CharSequence)object3, (String[])object7);
                if (((TypedArray)object6).getBoolean(18, true)) {
                    applicationInfo.flags |= 65536;
                }
                if (((TypedArray)object6).getBoolean(21, false)) {
                    applicationInfo.flags |= 131072;
                }
                if (((TypedArray)object6).getBoolean(32, false)) {
                    applicationInfo.flags |= 67108864;
                }
                if (((TypedArray)object6).getBoolean(40, false)) {
                    applicationInfo.privateFlags |= 8192;
                }
            }
            if ((object3 = ((TypedArray)object6).peekValue(35)) != null) {
                applicationInfo.fullBackupContent = n3 = ((TypedValue)object3).resourceId;
                if (n3 == 0) {
                    n3 = ((TypedValue)object3).data == 0 ? -1 : 0;
                    applicationInfo.fullBackupContent = n3;
                }
            }
        }
        applicationInfo.theme = ((TypedArray)object6).getResourceId(0, 0);
        applicationInfo.descriptionRes = ((TypedArray)object6).getResourceId(13, 0);
        if (((TypedArray)object6).getBoolean(8, false) && ((object3 = ((TypedArray)object6).getNonResourceString(45)) == null || packageParser.mCallback.hasFeature((String)object3))) {
            applicationInfo.flags |= 8;
        }
        if (((TypedArray)object6).getBoolean(27, false)) {
            ((Package)object4).mRequiredForAllUsers = true;
        }
        if ((object3 = ((TypedArray)object6).getString(28)) != null && ((String)object3).length() > 0) {
            ((Package)object4).mRestrictedAccountType = object3;
        }
        if ((object3 = ((TypedArray)object6).getString(29)) != null && ((String)object3).length() > 0) {
            ((Package)object4).mRequiredAccountType = object3;
        }
        if (((TypedArray)object6).getBoolean(10, false)) {
            applicationInfo.flags |= 2;
            applicationInfo.privateFlags |= 8388608;
        }
        if (((TypedArray)object6).getBoolean(20, false)) {
            applicationInfo.flags |= 16384;
        }
        boolean bl = object4.applicationInfo.targetSdkVersion >= 14;
        ((Package)object4).baseHardwareAccelerated = ((TypedArray)object6).getBoolean(23, bl);
        if (((Package)object4).baseHardwareAccelerated) {
            applicationInfo.flags |= 536870912;
        }
        if (((TypedArray)object6).getBoolean(7, true)) {
            applicationInfo.flags |= 4;
        }
        if (((TypedArray)object6).getBoolean(14, false)) {
            applicationInfo.flags |= 32;
        }
        if (((TypedArray)object6).getBoolean(5, true)) {
            applicationInfo.flags |= 64;
        }
        if (((Package)object4).parentPackage == null && ((TypedArray)object6).getBoolean(15, false)) {
            applicationInfo.flags |= 256;
        }
        if (((TypedArray)object6).getBoolean(24, false)) {
            applicationInfo.flags |= 1048576;
        }
        if (((TypedArray)object6).getBoolean(36, bl = object4.applicationInfo.targetSdkVersion < 28)) {
            applicationInfo.flags |= 134217728;
        }
        if (((TypedArray)object6).getBoolean(26, false)) {
            applicationInfo.flags |= 4194304;
        }
        if (((TypedArray)object6).getBoolean(33, false)) {
            applicationInfo.flags |= Integer.MIN_VALUE;
        }
        if (((TypedArray)object6).getBoolean(34, true)) {
            applicationInfo.flags |= 268435456;
        }
        if (((TypedArray)object6).getBoolean(53, false)) {
            applicationInfo.privateFlags |= 33554432;
        }
        if (((TypedArray)object6).getBoolean(38, false)) {
            applicationInfo.privateFlags |= 32;
        }
        if (((TypedArray)object6).getBoolean(39, false)) {
            applicationInfo.privateFlags |= 64;
        }
        if (((TypedArray)object6).hasValueOrEmpty(37)) {
            applicationInfo.privateFlags = ((TypedArray)object6).getBoolean(37, true) ? (applicationInfo.privateFlags |= 1024) : (applicationInfo.privateFlags |= 2048);
        } else if (object4.applicationInfo.targetSdkVersion >= 24) {
            applicationInfo.privateFlags |= 4096;
        }
        if (((TypedArray)object6).getBoolean(54, true)) {
            applicationInfo.privateFlags |= 67108864;
        }
        if (((TypedArray)object6).getBoolean(55, bl = object4.applicationInfo.targetSdkVersion >= 29)) {
            applicationInfo.privateFlags |= 134217728;
        }
        if (((TypedArray)object6).getBoolean(56, bl = object4.applicationInfo.targetSdkVersion < 29)) {
            applicationInfo.privateFlags |= 536870912;
        }
        applicationInfo.maxAspectRatio = ((TypedArray)object6).getFloat(44, 0.0f);
        applicationInfo.minAspectRatio = ((TypedArray)object6).getFloat(51, 0.0f);
        applicationInfo.networkSecurityConfigRes = ((TypedArray)object6).getResourceId(41, 0);
        applicationInfo.category = ((TypedArray)object6).getInt(43, -1);
        object3 = ((TypedArray)object6).getNonConfigurationString(6, 0);
        object3 = object3 != null && ((String)object3).length() > 0 ? ((String)object3).intern() : null;
        applicationInfo.permission = object3;
        object3 = object4.applicationInfo.targetSdkVersion >= 8 ? ((TypedArray)object6).getNonConfigurationString(12, 1024) : ((TypedArray)object6).getNonResourceString(12);
        applicationInfo.taskAffinity = PackageParser.buildTaskAffinityName(applicationInfo.packageName, applicationInfo.packageName, (CharSequence)object3, (String[])object7);
        Object object8 = ((TypedArray)object6).getNonResourceString(48);
        if (object8 != null) {
            applicationInfo.appComponentFactory = PackageParser.buildClassName(applicationInfo.packageName, (CharSequence)object8, (String[])object7);
        }
        if (((TypedArray)object6).getBoolean(49, false)) {
            applicationInfo.privateFlags |= 4194304;
        }
        if (((TypedArray)object6).getBoolean(50, false)) {
            applicationInfo.privateFlags |= 16777216;
        }
        if (object7[0] == null) {
            object8 = object4.applicationInfo.targetSdkVersion >= 8 ? ((TypedArray)object6).getNonConfigurationString(11, 1024) : ((TypedArray)object6).getNonResourceString(11);
            applicationInfo.processName = PackageParser.buildProcessName(applicationInfo.packageName, null, (CharSequence)object8, n, packageParser.mSeparateProcesses, arrstring);
            applicationInfo.enabled = ((TypedArray)object6).getBoolean(9, true);
            if (((TypedArray)object6).getBoolean(31, false)) {
                applicationInfo.flags |= 33554432;
            }
            if (((TypedArray)object6).getBoolean(47, false)) {
                applicationInfo.privateFlags |= 2;
                if (applicationInfo.processName != null && !applicationInfo.processName.equals(applicationInfo.packageName)) {
                    object7[0] = "cantSaveState applications can not use custom processes";
                }
            }
        }
        applicationInfo.uiOptions = ((TypedArray)object6).getInt(25, 0);
        applicationInfo.classLoaderName = ((TypedArray)object6).getString(46);
        if (applicationInfo.classLoaderName != null && !ClassLoaderFactory.isValidClassLoaderName(applicationInfo.classLoaderName)) {
            object8 = new StringBuilder();
            object8.append("Invalid class loader name: ");
            object8.append(applicationInfo.classLoaderName);
            object7[0] = object8.toString();
        }
        applicationInfo.zygotePreloadName = ((TypedArray)object6).getString(52);
        ((TypedArray)object6).recycle();
        if (object7[0] != null) {
            packageParser.mParseError = -108;
            return false;
        }
        n3 = xmlResourceParser.getDepth();
        CachedComponentArgs cachedComponentArgs = new CachedComponentArgs();
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        while ((n2 = xmlResourceParser.next()) != 1 && (n2 != 3 || xmlResourceParser.getDepth() > n3)) {
            Object object9;
            PackageParser packageParser2;
            block83 : {
                block71 : {
                    block82 : {
                        block81 : {
                            block80 : {
                                block79 : {
                                    block77 : {
                                        block78 : {
                                            block76 : {
                                                block75 : {
                                                    block74 : {
                                                        block73 : {
                                                            block72 : {
                                                                block70 : {
                                                                    if (n2 == 3 || n2 == 4) continue;
                                                                    object9 = xmlResourceParser.getName();
                                                                    if (!((String)object9).equals("activity")) break block70;
                                                                    object8 = this.parseActivity((Package)object, (Resources)object2, xmlResourceParser, n, arrstring, cachedComponentArgs, false, ((Package)object4).baseHardwareAccelerated);
                                                                    if (object8 == null) {
                                                                        packageParser.mParseError = -108;
                                                                        return false;
                                                                    }
                                                                    n2 = object8.order != 0 ? 1 : 0;
                                                                    ((Package)object4).activities.add((Activity)object8);
                                                                    n4 |= n2;
                                                                    object9 = object4;
                                                                    break block71;
                                                                }
                                                                if (!((String)object9).equals("receiver")) break block72;
                                                                object5 = this.parseActivity((Package)object, (Resources)object2, xmlResourceParser, n, arrstring, cachedComponentArgs, true, false);
                                                                if (object5 == null) {
                                                                    packageParser.mParseError = -108;
                                                                    return false;
                                                                }
                                                                n2 = ((Activity)object5).order != 0 ? 1 : 0;
                                                                object9 = object;
                                                                ((Package)object9).receivers.add((Activity)object5);
                                                                object5 = xmlResourceParser;
                                                                object7 = arrstring;
                                                                n5 |= n2;
                                                                break block71;
                                                            }
                                                            object5 = applicationInfo;
                                                            packageParser2 = packageParser;
                                                            if (!((String)object9).equals("service")) break block73;
                                                            object5 = this.parseService((Package)object, (Resources)object2, xmlResourceParser, n, arrstring, cachedComponentArgs);
                                                            if (object5 == null) {
                                                                packageParser2.mParseError = -108;
                                                                return false;
                                                            }
                                                            n2 = ((Service)object5).order != 0 ? 1 : 0;
                                                            ((Package)object4).services.add((Service)object5);
                                                            object5 = xmlResourceParser;
                                                            object7 = arrstring;
                                                            n6 |= n2;
                                                            object9 = object4;
                                                            break block71;
                                                        }
                                                        if (!((String)object9).equals("provider")) break block74;
                                                        object5 = this.parseProvider((Package)object, (Resources)object2, xmlResourceParser, n, arrstring, cachedComponentArgs);
                                                        if (object5 == null) {
                                                            packageParser2.mParseError = -108;
                                                            return false;
                                                        }
                                                        ((Package)object4).providers.add((Provider)object5);
                                                        object5 = xmlResourceParser;
                                                        object7 = arrstring;
                                                        object9 = object4;
                                                        break block71;
                                                    }
                                                    if (!((String)object9).equals("activity-alias")) break block75;
                                                    object5 = this.parseActivityAlias((Package)object, (Resources)object2, xmlResourceParser, n, arrstring, cachedComponentArgs);
                                                    if (object5 == null) {
                                                        packageParser2.mParseError = -108;
                                                        return false;
                                                    }
                                                    n2 = ((Activity)object5).order != 0 ? 1 : 0;
                                                    ((Package)object4).activities.add((Activity)object5);
                                                    object5 = xmlResourceParser;
                                                    object7 = arrstring;
                                                    n4 |= n2;
                                                    object9 = object4;
                                                    break block71;
                                                }
                                                if (!xmlResourceParser.getName().equals("meta-data")) break block76;
                                                object8 = ((Package)object4).mAppMetaData;
                                                object5 = xmlResourceParser;
                                                object7 = arrstring;
                                                ((Package)object4).mAppMetaData = object8 = packageParser2.parseMetaData((Resources)object2, (XmlResourceParser)object5, (Bundle)object8, (String[])object7);
                                                if (object8 == null) {
                                                    packageParser2.mParseError = -108;
                                                    return false;
                                                }
                                                object9 = object4;
                                                break block71;
                                            }
                                            object6 = xmlResourceParser;
                                            object8 = arrstring;
                                            if (!((String)object9).equals("static-library")) break block77;
                                            object9 = ((Resources)object2).obtainAttributes((AttributeSet)object6, R.styleable.AndroidManifestStaticLibrary);
                                            object7 = ((TypedArray)object9).getNonResourceString(0);
                                            n2 = ((TypedArray)object9).getInt(1, -1);
                                            int n7 = ((TypedArray)object9).getInt(2, 0);
                                            ((TypedArray)object9).recycle();
                                            if (object7 == null || n2 < 0) break block78;
                                            if (((Package)object4).mSharedUserId != null) {
                                                object8[0] = "sharedUserId not allowed in static shared library";
                                                packageParser2.mParseError = -107;
                                                XmlUtils.skipCurrentTag(xmlResourceParser);
                                                return false;
                                            }
                                            if (((Package)object4).staticSharedLibName != null) {
                                                object = new StringBuilder();
                                                ((StringBuilder)object).append("Multiple static-shared libs for package ");
                                                ((StringBuilder)object).append(string2);
                                                object8[0] = ((StringBuilder)object).toString();
                                                packageParser2.mParseError = -108;
                                                XmlUtils.skipCurrentTag(xmlResourceParser);
                                                return false;
                                            }
                                            ((Package)object4).staticSharedLibName = ((String)object7).intern();
                                            ((Package)object4).staticSharedLibVersion = n2 >= 0 ? PackageInfo.composeLongVersionCode(n7, n2) : (long)n2;
                                            ((ApplicationInfo)object5).privateFlags |= 16384;
                                            XmlUtils.skipCurrentTag(xmlResourceParser);
                                            object5 = object6;
                                            object7 = object8;
                                            object9 = object4;
                                            break block71;
                                        }
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("Bad static-library declaration name: ");
                                        ((StringBuilder)object).append((String)object7);
                                        ((StringBuilder)object).append(" version: ");
                                        ((StringBuilder)object).append(n2);
                                        object8[0] = ((StringBuilder)object).toString();
                                        packageParser2.mParseError = -108;
                                        XmlUtils.skipCurrentTag(xmlResourceParser);
                                        return false;
                                    }
                                    if (!((String)object9).equals("library")) break block79;
                                    object5 = ((Resources)object2).obtainAttributes((AttributeSet)object6, R.styleable.AndroidManifestLibrary);
                                    object7 = ((TypedArray)object5).getNonResourceString(0);
                                    ((TypedArray)object5).recycle();
                                    if (object7 != null && !ArrayUtils.contains(((Package)object4).libraryNames, object5 = ((String)object7).intern())) {
                                        ((Package)object4).libraryNames = ArrayUtils.add(((Package)object4).libraryNames, object5);
                                    }
                                    XmlUtils.skipCurrentTag(xmlResourceParser);
                                    object5 = object6;
                                    object7 = object8;
                                    object9 = object4;
                                    break block71;
                                }
                                if (!((String)object9).equals("uses-static-library")) break block80;
                                object5 = object6;
                                object7 = object8;
                                object9 = object4;
                                if (!packageParser2.parseUsesStaticLibrary((Package)object4, (Resources)object2, (XmlResourceParser)object6, (String[])object8)) {
                                    return false;
                                }
                                break block71;
                            }
                            if (!((String)object9).equals("uses-library")) break block81;
                            object7 = ((Resources)object2).obtainAttributes((AttributeSet)object6, R.styleable.AndroidManifestUsesLibrary);
                            object5 = ((TypedArray)object7).getNonResourceString(0);
                            bl = ((TypedArray)object7).getBoolean(1, true);
                            ((TypedArray)object7).recycle();
                            if (object5 != null) {
                                object5 = ((String)object5).intern();
                                if (bl) {
                                    ((Package)object4).usesLibraries = ArrayUtils.add(((Package)object4).usesLibraries, object5);
                                } else {
                                    ((Package)object4).usesOptionalLibraries = ArrayUtils.add(((Package)object4).usesOptionalLibraries, object5);
                                }
                            }
                            XmlUtils.skipCurrentTag(xmlResourceParser);
                            object5 = object6;
                            object7 = object8;
                            object9 = object4;
                            break block71;
                        }
                        if (!((String)object9).equals("uses-package")) break block82;
                        XmlUtils.skipCurrentTag(xmlResourceParser);
                        object5 = object6;
                        object7 = object8;
                        object9 = object4;
                        break block71;
                    }
                    if (!((String)object9).equals("profileable")) break block83;
                    if (((Resources)object2).obtainAttributes((AttributeSet)object6, R.styleable.AndroidManifestProfileable).getBoolean(0, false)) {
                        ((ApplicationInfo)object5).privateFlags |= 8388608;
                    }
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                    object9 = object4;
                    object7 = object8;
                    object5 = object6;
                }
                object4 = object9;
                continue;
            }
            object5 = new StringBuilder();
            ((StringBuilder)object5).append("Unknown element under <application>: ");
            ((StringBuilder)object5).append((String)object9);
            ((StringBuilder)object5).append(" at ");
            ((StringBuilder)object5).append(packageParser2.mArchiveSourcePath);
            ((StringBuilder)object5).append(" ");
            ((StringBuilder)object5).append(xmlResourceParser.getPositionDescription());
            Slog.w(TAG, ((StringBuilder)object5).toString());
            XmlUtils.skipCurrentTag(xmlResourceParser);
            object5 = object6;
            object7 = object8;
        }
        if (TextUtils.isEmpty(((Package)object4).staticSharedLibName)) {
            object2 = packageParser.generateAppDetailsHiddenActivity((Package)object4, n, (String[])object7, ((Package)object4).baseHardwareAccelerated);
            ((Package)object4).activities.add((Activity)object2);
        }
        if (n4 != 0) {
            Collections.sort(((Package)object4).activities, _$$Lambda$PackageParser$0aobsT7Zf7WVZCqMZ5z2clAuQf4.INSTANCE);
        }
        if (n5 != 0) {
            Collections.sort(((Package)object4).receivers, _$$Lambda$PackageParser$0DZRgzfgaIMpCOhJqjw6PUiU5vw.INSTANCE);
        }
        if (n6 != 0) {
            Collections.sort(((Package)object4).services, _$$Lambda$PackageParser$M_9fHqS_eEp1oYkuKJhRHOGUxf8.INSTANCE);
        }
        this.setMaxAspectRatio((Package)object);
        this.setMinAspectRatio((Package)object);
        if (PackageParser.hasDomainURLs((Package)object)) {
            object = ((Package)object4).applicationInfo;
            ((ApplicationInfo)object).privateFlags |= 16;
        } else {
            object = ((Package)object4).applicationInfo;
            ((ApplicationInfo)object).privateFlags &= -17;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Package parseClusterPackage(File serializable, int n) throws PackageParserException {
        SplitAssetLoader splitAssetLoader;
        Object object;
        Serializable serializable2;
        PackageLite packageLite = PackageParser.parseClusterPackageLite((File)serializable, 0);
        if (this.mOnlyCoreApps && !packageLite.coreApp) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not a coreApp: ");
            stringBuilder.append(serializable);
            throw new PackageParserException(-108, stringBuilder.toString());
        }
        Object object2 = null;
        if (packageLite.isolatedSplits && !ArrayUtils.isEmpty(packageLite.splitNames)) {
            try {
                object2 = SplitAssetDependencyLoader.createDependenciesFromPackage(packageLite);
                splitAssetLoader = new SplitAssetDependencyLoader(packageLite, (SparseArray<int[]>)object2, n);
            }
            catch (SplitDependencyLoader.IllegalDependencyException illegalDependencyException) {
                throw new PackageParserException(-101, illegalDependencyException.getMessage());
            }
        } else {
            splitAssetLoader = new DefaultSplitAssetLoader(packageLite, n);
        }
        try {
            block13 : {
                int n2;
                block14 : {
                    block12 : {
                        object = splitAssetLoader.getBaseAssetManager();
                        serializable2 = new File(packageLite.baseCodePath);
                        object = this.parseBaseApk((File)serializable2, (AssetManager)object, n);
                        if (object == null) break block12;
                        if (ArrayUtils.isEmpty(packageLite.splitNames)) break block13;
                        n2 = packageLite.splitNames.length;
                        ((Package)object).splitNames = packageLite.splitNames;
                        ((Package)object).splitCodePaths = packageLite.splitCodePaths;
                        ((Package)object).splitRevisionCodes = packageLite.splitRevisionCodes;
                        ((Package)object).splitFlags = new int[n2];
                        ((Package)object).splitPrivateFlags = new int[n2];
                        object.applicationInfo.splitNames = ((Package)object).splitNames;
                        object.applicationInfo.splitDependencies = object2;
                        object.applicationInfo.splitClassLoaderNames = new String[n2];
                        break block14;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Failed to parse base APK: ");
                    ((StringBuilder)object2).append(serializable2);
                    serializable = new PackageParserException(-100, ((StringBuilder)object2).toString());
                    throw serializable;
                }
                for (int i = 0; i < n2; ++i) {
                    this.parseSplitApk((Package)object, i, splitAssetLoader.getSplitAssetManager(i), n);
                }
            }
            ((Package)object).setCodePath(((File)serializable).getCanonicalPath());
            ((Package)object).setUse32bitAbi(packageLite.use32bitAbi);
        }
        catch (Throwable throwable) {
        }
        catch (IOException iOException) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Failed to get path: ");
            ((StringBuilder)serializable).append(packageLite.baseCodePath);
            serializable2 = new PackageParserException(-102, ((StringBuilder)serializable).toString(), iOException);
            throw serializable2;
        }
        IoUtils.closeQuietly((AutoCloseable)splitAssetLoader);
        return object;
        IoUtils.closeQuietly((AutoCloseable)splitAssetLoader);
        throw throwable;
    }

    /*
     * WARNING - void declaration
     */
    static PackageLite parseClusterPackageLite(File serializable, int n) throws PackageParserException {
        block8 : {
            int[] arrn;
            Object[] arrobject = ((File)serializable).listFiles();
            if (ArrayUtils.isEmpty(arrobject)) break block8;
            CharSequence charSequence = null;
            int n2 = 0;
            Trace.traceBegin(262144L, "parseApkLite");
            ArrayMap<String, int[]> arrayMap = new ArrayMap<String, int[]>();
            for (File arrstring : arrobject) {
                block11 : {
                    block12 : {
                        block10 : {
                            block9 : {
                                if (!PackageParser.isApkFile(arrstring)) continue;
                                arrn = PackageParser.parseApkLite(arrstring, n);
                                if (charSequence != null) break block9;
                                charSequence = arrn.packageName;
                                n2 = arrn.versionCode;
                                break block10;
                            }
                            if (!((String)charSequence).equals(arrn.packageName)) break block11;
                            if (n2 != arrn.versionCode) break block12;
                        }
                        if (arrayMap.put(arrn.splitName, arrn) == null) continue;
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Split name ");
                        ((StringBuilder)serializable).append(arrn.splitName);
                        ((StringBuilder)serializable).append(" defined more than once; most recent was ");
                        ((StringBuilder)serializable).append(arrstring);
                        throw new PackageParserException(-101, ((StringBuilder)serializable).toString());
                    }
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Inconsistent version ");
                    ((StringBuilder)serializable).append(arrn.versionCode);
                    ((StringBuilder)serializable).append(" in ");
                    ((StringBuilder)serializable).append(arrstring);
                    ((StringBuilder)serializable).append("; expected ");
                    ((StringBuilder)serializable).append(n2);
                    throw new PackageParserException(-101, ((StringBuilder)serializable).toString());
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Inconsistent package ");
                ((StringBuilder)serializable).append(arrn.packageName);
                ((StringBuilder)serializable).append(" in ");
                ((StringBuilder)serializable).append(arrstring);
                ((StringBuilder)serializable).append("; expected ");
                ((StringBuilder)serializable).append((String)charSequence);
                throw new PackageParserException(-101, ((StringBuilder)serializable).toString());
            }
            Trace.traceEnd(262144L);
            ApkLite apkLite = (ApkLite)arrayMap.remove(null);
            if (apkLite != null) {
                void var8_11;
                n2 = arrayMap.size();
                arrobject = null;
                boolean[] arrbl = null;
                charSequence = null;
                String[] arrstring = null;
                if (n2 > 0) {
                    arrobject = new String[n2];
                    arrbl = new boolean[n2];
                    charSequence = new String[n2];
                    arrstring = new String[n2];
                    String[] arrstring2 = new String[n2];
                    arrn = new int[n2];
                    arrobject = (String[])arrayMap.keySet().toArray(arrobject);
                    Arrays.sort(arrobject, sSplitNameComparator);
                    for (n = 0; n < n2; ++n) {
                        ApkLite apkLite2 = (ApkLite)arrayMap.get(arrobject[n]);
                        charSequence[n] = apkLite2.usesSplitName;
                        arrbl[n] = apkLite2.isFeatureSplit;
                        arrstring[n] = apkLite2.configForSplit;
                        arrstring2[n] = apkLite2.codePath;
                        arrn[n] = apkLite2.revisionCode;
                    }
                } else {
                    Object var8_10 = null;
                    arrn = null;
                }
                return new PackageLite(((File)serializable).getAbsolutePath(), apkLite, (String[])arrobject, arrbl, (String[])charSequence, arrstring, (String[])var8_11, arrn);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Missing base APK in ");
            ((StringBuilder)charSequence).append(serializable);
            throw new PackageParserException(-101, ((StringBuilder)charSequence).toString());
        }
        throw new PackageParserException(-100, "No packages found in split");
    }

    private Instrumentation parseInstrumentation(Package package_, Resources resources, XmlResourceParser xmlResourceParser, String[] arrstring) throws XmlPullParserException, IOException {
        TypedArray typedArray = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestInstrumentation);
        if (this.mParseInstrumentationArgs == null) {
            this.mParseInstrumentationArgs = new ParsePackageItemArgs(package_, arrstring, 2, 0, 1, 8, 6, 7);
            this.mParseInstrumentationArgs.tag = "<instrumentation>";
        }
        Object object = this.mParseInstrumentationArgs;
        ((ParsePackageItemArgs)object).sa = typedArray;
        Instrumentation instrumentation = new Instrumentation((ParsePackageItemArgs)object, new InstrumentationInfo());
        if (arrstring[0] != null) {
            typedArray.recycle();
            this.mParseError = -108;
            return null;
        }
        object = typedArray.getNonResourceString(3);
        InstrumentationInfo instrumentationInfo = instrumentation.info;
        object = object != null ? ((String)object).intern() : null;
        instrumentationInfo.targetPackage = object;
        object = typedArray.getNonResourceString(9);
        instrumentationInfo = instrumentation.info;
        object = object != null ? ((String)object).intern() : null;
        instrumentationInfo.targetProcesses = object;
        instrumentation.info.handleProfiling = typedArray.getBoolean(4, false);
        instrumentation.info.functionalTest = typedArray.getBoolean(5, false);
        typedArray.recycle();
        if (instrumentation.info.targetPackage == null) {
            arrstring[0] = "<instrumentation> does not specify targetPackage";
            this.mParseError = -108;
            return null;
        }
        if (!this.parseAllMetaData(resources, xmlResourceParser, "<instrumentation>", instrumentation, arrstring)) {
            this.mParseError = -108;
            return null;
        }
        package_.instrumentation.add(instrumentation);
        return instrumentation;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean parseIntent(Resources var1_1, XmlResourceParser var2_3, boolean var3_4, boolean var4_5, IntentInfo var5_6, String[] var6_7) throws XmlPullParserException, IOException {
        var7_8 = var1_1.obtainAttributes(var2_3, R.styleable.AndroidManifestIntentFilter);
        var5_6.setPriority(var7_8.getInt(2, 0));
        var8_9 = 3;
        var5_6.setOrder(var7_8.getInt(3, 0));
        var9_10 = var7_8.peekValue(0);
        if (var9_10 != null) {
            var5_6.labelRes = var10_11 = var9_10.resourceId;
            if (var10_11 == 0) {
                var5_6.nonLocalizedLabel = var9_10.coerceToString();
            }
        }
        var10_11 = PackageParser.sUseRoundIcon != false ? var7_8.getResourceId(7, 0) : 0;
        var5_6.icon = var10_11 != 0 ? var10_11 : var7_8.getResourceId(1, 0);
        var5_6.logo = var7_8.getResourceId(4, 0);
        var5_6.banner = var7_8.getResourceId(5, 0);
        if (var4_5) {
            var5_6.setAutoVerify(var7_8.getBoolean(6, false));
        }
        var7_8.recycle();
        var11_12 = var2_3.getDepth();
        var10_11 = var8_9;
        while ((var8_9 = var2_3.next()) != 1 && (var8_9 != var10_11 || var2_3.getDepth() > var11_12)) {
            block25 : {
                block23 : {
                    block24 : {
                        if (var8_9 == var10_11 || var8_9 == 4) continue;
                        var9_10 = var2_3.getName();
                        if (!var9_10.equals("action")) break block23;
                        var9_10 = var2_3.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                        if (var9_10 == null || var9_10 == "") break block24;
                        XmlUtils.skipCurrentTag(var2_3);
                        var5_6.addAction((String)var9_10);
                        break block25;
                    }
                    var6_7[0] = "No value supplied for <android:name>";
                    return false;
                }
                if (!var9_10.equals("category")) ** GOTO lbl40
                var9_10 = var2_3.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                if (var9_10 != null && var9_10 != "") {
                    XmlUtils.skipCurrentTag(var2_3);
                    var5_6.addCategory((String)var9_10);
                } else {
                    var6_7[0] = "No value supplied for <android:name>";
                    return false;
lbl40: // 1 sources:
                    if (var9_10.equals("data")) {
                        var9_10 = var1_1.obtainAttributes(var2_3, R.styleable.AndroidManifestData);
                        var7_8 = var9_10.getNonConfigurationString(0, 0);
                        if (var7_8 != null) {
                            try {
                                var5_6.addDataType((String)var7_8);
                            }
                            catch (IntentFilter.MalformedMimeTypeException var1_2) {
                                var6_7[0] = var1_2.toString();
                                var9_10.recycle();
                                return false;
                            }
                        }
                        if ((var7_8 = var9_10.getNonConfigurationString(1, 0)) != null) {
                            var5_6.addDataScheme((String)var7_8);
                        }
                        if ((var7_8 = var9_10.getNonConfigurationString(7, 0)) != null) {
                            var5_6.addDataSchemeSpecificPart((String)var7_8, 0);
                        }
                        if ((var7_8 = var9_10.getNonConfigurationString(8, 0)) != null) {
                            var5_6.addDataSchemeSpecificPart((String)var7_8, 1);
                        }
                        if ((var7_8 = var9_10.getNonConfigurationString(9, 0)) != null) {
                            if (!var3_4) {
                                var6_7[0] = "sspPattern not allowed here; ssp must be literal";
                                return false;
                            }
                            var5_6.addDataSchemeSpecificPart((String)var7_8, 2);
                        }
                        var12_13 = var9_10.getNonConfigurationString(2, 0);
                        var7_8 = var9_10.getNonConfigurationString(3, 0);
                        if (var12_13 != null) {
                            var5_6.addDataAuthority(var12_13, (String)var7_8);
                        }
                        if ((var7_8 = var9_10.getNonConfigurationString(4, 0)) != null) {
                            var5_6.addDataPath((String)var7_8, 0);
                        }
                        if ((var7_8 = var9_10.getNonConfigurationString(5, 0)) != null) {
                            var5_6.addDataPath((String)var7_8, 1);
                        }
                        if ((var7_8 = var9_10.getNonConfigurationString(6, 0)) != null) {
                            if (!var3_4) {
                                var6_7[0] = "pathPattern not allowed here; path must be literal";
                                return false;
                            }
                            var5_6.addDataPath((String)var7_8, 2);
                        }
                        if ((var7_8 = var9_10.getNonConfigurationString(10, 0)) != null) {
                            if (!var3_4) {
                                var6_7[0] = "pathAdvancedPattern not allowed here; path must be literal";
                                return false;
                            }
                            var5_6.addDataPath((String)var7_8, 3);
                        }
                        var9_10.recycle();
                        XmlUtils.skipCurrentTag(var2_3);
                    } else {
                        var9_10 = new StringBuilder();
                        var9_10.append("Unknown element under <intent-filter>: ");
                        var9_10.append(var2_3.getName());
                        var9_10.append(" at ");
                        var9_10.append(this.mArchiveSourcePath);
                        var9_10.append(" ");
                        var9_10.append(var2_3.getPositionDescription());
                        Slog.w("PackageParser", var9_10.toString());
                        XmlUtils.skipCurrentTag(var2_3);
                    }
                }
            }
            var10_11 = 3;
        }
        var5_6.hasDefault = var5_6.hasCategory("android.intent.category.DEFAULT");
        return true;
    }

    private boolean parseKeySets(Package object, Resources iterator2, XmlResourceParser object2, String[] arrstring) throws XmlPullParserException, IOException {
        int n;
        String string2;
        int n2 = string2.getDepth();
        int n3 = -1;
        Object object3 = null;
        ArrayMap<CharSequence, Object> arrayMap = new ArrayMap<CharSequence, Object>();
        ArraySet<String> arraySet = new ArraySet<String>();
        Object object42 = new ArrayMap();
        ArraySet<Object> arraySet2 = new ArraySet<Object>();
        while ((n = string2.next()) != 1 && (n != 3 || string2.getDepth() > n2)) {
            if (n == 3) {
                if (string2.getDepth() != n3) continue;
                object3 = null;
                n3 = -1;
                continue;
            }
            Object object5 = string2.getName();
            if (((String)object5).equals("key-set")) {
                if (object3 != null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Improperly nested 'key-set' tag at ");
                    ((StringBuilder)object).append(string2.getPositionDescription());
                    var4_11[0] = ((StringBuilder)object).toString();
                    this.mParseError = -108;
                    return false;
                }
                object5 = ((Resources)((Object)iterator2)).obtainAttributes((AttributeSet)((Object)string2), R.styleable.AndroidManifestKeySet);
                object3 = ((TypedArray)object5).getNonResourceString(0);
                ((ArrayMap)object42).put(object3, new ArraySet());
                n3 = string2.getDepth();
                ((TypedArray)object5).recycle();
                continue;
            }
            if (((String)object5).equals("public-key")) {
                if (object3 == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Improperly nested 'key-set' tag at ");
                    ((StringBuilder)object).append(string2.getPositionDescription());
                    var4_11[0] = ((StringBuilder)object).toString();
                    this.mParseError = -108;
                    return false;
                }
                object5 = ((Resources)((Object)iterator2)).obtainAttributes((AttributeSet)((Object)string2), R.styleable.AndroidManifestPublicKey);
                CharSequence charSequence = ((TypedArray)object5).getNonResourceString(0);
                Object object6 = ((TypedArray)object5).getNonResourceString(1);
                if (object6 == null && arrayMap.get(charSequence) == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("'public-key' ");
                    ((StringBuilder)object).append((String)charSequence);
                    ((StringBuilder)object).append(" must define a public-key value on first use at ");
                    ((StringBuilder)object).append(string2.getPositionDescription());
                    var4_11[0] = ((StringBuilder)object).toString();
                    this.mParseError = -108;
                    ((TypedArray)object5).recycle();
                    return false;
                }
                if (object6 != null) {
                    if ((object6 = PackageParser.parsePublicKey((String)object6)) == null) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("No recognized valid key in 'public-key' tag at ");
                        ((StringBuilder)charSequence).append(string2.getPositionDescription());
                        ((StringBuilder)charSequence).append(" key-set ");
                        ((StringBuilder)charSequence).append((String)object3);
                        ((StringBuilder)charSequence).append(" will not be added to the package's defined key-sets.");
                        Slog.w(TAG, ((StringBuilder)charSequence).toString());
                        ((TypedArray)object5).recycle();
                        arraySet2.add(object3);
                        XmlUtils.skipCurrentTag((XmlPullParser)string2);
                        continue;
                    }
                    if (arrayMap.get(charSequence) != null && !((PublicKey)arrayMap.get(charSequence)).equals(object6)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Value of 'public-key' ");
                        ((StringBuilder)object).append((String)charSequence);
                        ((StringBuilder)object).append(" conflicts with previously defined value at ");
                        ((StringBuilder)object).append(string2.getPositionDescription());
                        var4_11[0] = ((StringBuilder)object).toString();
                        this.mParseError = -108;
                        ((TypedArray)object5).recycle();
                        return false;
                    }
                    arrayMap.put(charSequence, object6);
                }
                ((ArraySet)((ArrayMap)object42).get(object3)).add(charSequence);
                ((TypedArray)object5).recycle();
                XmlUtils.skipCurrentTag((XmlPullParser)string2);
                continue;
            }
            if (((String)object5).equals("upgrade-key-set")) {
                object5 = ((Resources)((Object)iterator2)).obtainAttributes((AttributeSet)((Object)string2), R.styleable.AndroidManifestUpgradeKeySet);
                arraySet.add(((TypedArray)object5).getNonResourceString(0));
                ((TypedArray)object5).recycle();
                XmlUtils.skipCurrentTag((XmlPullParser)string2);
                continue;
            }
            object5 = new StringBuilder();
            ((StringBuilder)object5).append("Unknown element under <key-sets>: ");
            ((StringBuilder)object5).append(string2.getName());
            ((StringBuilder)object5).append(" at ");
            ((StringBuilder)object5).append(this.mArchiveSourcePath);
            ((StringBuilder)object5).append(" ");
            ((StringBuilder)object5).append(string2.getPositionDescription());
            Slog.w(TAG, ((StringBuilder)object5).toString());
            XmlUtils.skipCurrentTag((XmlPullParser)string2);
        }
        if (arrayMap.keySet().removeAll(((ArrayMap)object42).keySet())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Package");
            stringBuilder.append(((Package)object).packageName);
            stringBuilder.append(" AndroidManifext.xml 'key-set' and 'public-key' names must be distinct.");
            var4_11[0] = stringBuilder.toString();
            this.mParseError = -108;
            return false;
        }
        ((Package)object).mKeySetMapping = new ArrayMap();
        for (Map.Entry entry : ((ArrayMap)object42).entrySet()) {
            string2 = (String)entry.getKey();
            if (((ArraySet)entry.getValue()).size() == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Package");
                stringBuilder.append(((Package)object).packageName);
                stringBuilder.append(" AndroidManifext.xml 'key-set' ");
                stringBuilder.append(string2);
                stringBuilder.append(" has no valid associated 'public-key'. Not including in package's defined key-sets.");
                Slog.w(TAG, stringBuilder.toString());
                continue;
            }
            if (arraySet2.contains(string2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Package");
                stringBuilder.append(((Package)object).packageName);
                stringBuilder.append(" AndroidManifext.xml 'key-set' ");
                stringBuilder.append(string2);
                stringBuilder.append(" contained improper 'public-key' tags. Not including in package's defined key-sets.");
                Slog.w(TAG, stringBuilder.toString());
                continue;
            }
            ((Package)object).mKeySetMapping.put(string2, new ArraySet());
            for (Object object42 : (ArraySet)entry.getValue()) {
                ((Package)object).mKeySetMapping.get(string2).add((PublicKey)arrayMap.get(object42));
            }
        }
        if (((Package)object).mKeySetMapping.keySet().containsAll(arraySet)) {
            ((Package)object).mUpgradeKeySets = arraySet;
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Package");
        stringBuilder.append(((Package)object).packageName);
        stringBuilder.append(" AndroidManifext.xml does not define all 'upgrade-key-set's .");
        var4_11[0] = stringBuilder.toString();
        this.mParseError = -108;
        return false;
    }

    private void parseLayout(Resources object, AttributeSet attributeSet, Activity activity) {
        float f;
        object = ((Resources)object).obtainAttributes(attributeSet, R.styleable.AndroidManifestLayout);
        int n = -1;
        float f2 = -1.0f;
        int n2 = -1;
        float f3 = -1.0f;
        int n3 = ((TypedArray)object).getType(3);
        if (n3 == 6) {
            f = ((TypedArray)object).getFraction(3, 1, 1, -1.0f);
        } else {
            f = f2;
            if (n3 == 5) {
                n = ((TypedArray)object).getDimensionPixelSize(3, -1);
                f = f2;
            }
        }
        n3 = ((TypedArray)object).getType(4);
        if (n3 == 6) {
            f2 = ((TypedArray)object).getFraction(4, 1, 1, -1.0f);
        } else {
            f2 = f3;
            if (n3 == 5) {
                n2 = ((TypedArray)object).getDimensionPixelSize(4, -1);
                f2 = f3;
            }
        }
        int n4 = ((TypedArray)object).getInt(0, 17);
        int n5 = ((TypedArray)object).getDimensionPixelSize(1, -1);
        n3 = ((TypedArray)object).getDimensionPixelSize(2, -1);
        ((TypedArray)object).recycle();
        activity.info.windowLayout = new ActivityInfo.WindowLayout(n, f, n2, f2, n4, n5, n3);
    }

    private Bundle parseMetaData(Resources object, XmlResourceParser xmlResourceParser, Bundle object2, String[] object3) throws XmlPullParserException, IOException {
        TypedArray typedArray = ((Resources)object).obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestMetaData);
        object = object2;
        if (object2 == null) {
            object = new Bundle();
        }
        boolean bl = false;
        String string2 = typedArray.getNonConfigurationString(0, 0);
        object2 = null;
        if (string2 == null) {
            object3[0] = "<meta-data> requires an android:name attribute";
            typedArray.recycle();
            return null;
        }
        string2 = string2.intern();
        TypedValue typedValue = typedArray.peekValue(2);
        if (typedValue != null && typedValue.resourceId != 0) {
            ((BaseBundle)object).putInt(string2, typedValue.resourceId);
        } else {
            typedValue = typedArray.peekValue(1);
            if (typedValue != null) {
                if (typedValue.type == 3) {
                    object3 = typedValue.coerceToString();
                    if (object3 != null) {
                        object2 = object3.toString();
                    }
                    ((BaseBundle)object).putString(string2, (String)object2);
                } else if (typedValue.type == 18) {
                    if (typedValue.data != 0) {
                        bl = true;
                    }
                    ((BaseBundle)object).putBoolean(string2, bl);
                } else if (typedValue.type >= 16 && typedValue.type <= 31) {
                    ((BaseBundle)object).putInt(string2, typedValue.data);
                } else if (typedValue.type == 4) {
                    ((Bundle)object).putFloat(string2, typedValue.getFloat());
                } else {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("<meta-data> only supports string, integer, float, color, boolean, and resource reference types: ");
                    ((StringBuilder)object2).append(xmlResourceParser.getName());
                    ((StringBuilder)object2).append(" at ");
                    ((StringBuilder)object2).append(this.mArchiveSourcePath);
                    ((StringBuilder)object2).append(" ");
                    ((StringBuilder)object2).append(xmlResourceParser.getPositionDescription());
                    Slog.w(TAG, ((StringBuilder)object2).toString());
                }
            } else {
                object3[0] = "<meta-data> requires an android:value or android:resource attribute";
                object = null;
            }
        }
        typedArray.recycle();
        XmlUtils.skipCurrentTag(xmlResourceParser);
        return object;
    }

    private static PackageLite parseMonolithicPackageLite(File object, int n) throws PackageParserException {
        Trace.traceBegin(262144L, "parseApkLite");
        ApkLite apkLite = PackageParser.parseApkLite((File)object, n);
        object = ((File)object).getAbsolutePath();
        Trace.traceEnd(262144L);
        return new PackageLite((String)object, apkLite, null, null, null, null, null, null);
    }

    private static boolean parsePackageItemInfo(Package object, PackageItemInfo packageItemInfo, String[] object2, String string2, TypedArray typedArray, boolean bl, int n, int n2, int n3, int n4, int n5, int n6) {
        if (typedArray == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" does not contain any attributes");
            object2[0] = ((StringBuilder)object).toString();
            return false;
        }
        String string3 = typedArray.getNonConfigurationString(n, 0);
        if (string3 == null) {
            if (bl) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" does not specify android:name");
                object2[0] = ((StringBuilder)object).toString();
                return false;
            }
        } else {
            if (PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(string3 = PackageParser.buildClassName(object.applicationInfo.packageName, string3, (String[])object2))) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" invalid android:name");
                object2[0] = ((StringBuilder)object).toString();
                return false;
            }
            packageItemInfo.name = string3;
            if (string3 == null) {
                return false;
            }
        }
        if ((n = sUseRoundIcon ? typedArray.getResourceId(n4, 0) : 0) != 0) {
            packageItemInfo.icon = n;
            packageItemInfo.nonLocalizedLabel = null;
        } else {
            n = typedArray.getResourceId(n3, 0);
            if (n != 0) {
                packageItemInfo.icon = n;
                packageItemInfo.nonLocalizedLabel = null;
            }
        }
        n = typedArray.getResourceId(n5, 0);
        if (n != 0) {
            packageItemInfo.logo = n;
        }
        if ((n = typedArray.getResourceId(n6, 0)) != 0) {
            packageItemInfo.banner = n;
        }
        if ((object2 = typedArray.peekValue(n2)) != null) {
            packageItemInfo.labelRes = n = ((TypedValue)object2).resourceId;
            if (n == 0) {
                packageItemInfo.nonLocalizedLabel = ((TypedValue)object2).coerceToString();
            }
        }
        packageItemInfo.packageName = ((Package)object).packageName;
        return true;
    }

    @UnsupportedAppUsage
    public static PackageLite parsePackageLite(File file, int n) throws PackageParserException {
        if (file.isDirectory()) {
            return PackageParser.parseClusterPackageLite(file, n);
        }
        return PackageParser.parseMonolithicPackageLite(file, n);
    }

    private static Pair<String, String> parsePackageSplitNames(XmlPullParser object, AttributeSet object2) throws IOException, XmlPullParserException, PackageParserException {
        int n;
        while ((n = object.next()) != 2 && n != 1) {
        }
        if (n == 2) {
            if (object.getName().equals(TAG_MANIFEST)) {
                String string2 = object2.getAttributeValue(null, TAG_PACKAGE);
                if (!"android".equals(string2) && (object = PackageParser.validateName(string2, true, true)) != null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Invalid manifest package: ");
                    ((StringBuilder)object2).append((String)object);
                    throw new PackageParserException(-106, ((StringBuilder)object2).toString());
                }
                object = object2 = object2.getAttributeValue(null, "split");
                if (object2 != null) {
                    if (((String)object2).length() == 0) {
                        object = null;
                    } else {
                        object = PackageParser.validateName((String)object2, false, false);
                        if (object == null) {
                            object = object2;
                        } else {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Invalid manifest split: ");
                            ((StringBuilder)object2).append((String)object);
                            throw new PackageParserException(-106, ((StringBuilder)object2).toString());
                        }
                    }
                }
                object2 = string2.intern();
                if (object != null) {
                    object = ((String)object).intern();
                }
                return Pair.create(object2, object);
            }
            throw new PackageParserException(-108, "No <manifest> tag");
        }
        throw new PackageParserException(-108, "No start tag found");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean parsePermission(Package var1_1, Resources var2_2, XmlResourceParser var3_3, String[] var4_4) throws XmlPullParserException, IOException {
        var5_5 = var2_2.obtainAttributes(var3_3, R.styleable.AndroidManifestPermission);
        if (!var5_5.hasValue(10)) ** GOTO lbl13
        if ("android".equals(var1_1.packageName)) {
            var6_6 = var5_5.getNonResourceString(10);
        } else {
            var6_6 = new StringBuilder();
            var6_6.append(var1_1.packageName);
            var6_6.append(" defines a background permission. Only the 'android' package can do that.");
            Slog.w("PackageParser", var6_6.toString());
lbl13: // 2 sources:
            var6_6 = null;
        }
        var6_6 = new Permission((Package)var1_1, (String)var6_6);
        if (!PackageParser.parsePackageItemInfo((Package)var1_1, var6_6.info, var4_4, "<permission>", var5_5, true, 2, 0, 1, 9, 6, 8)) {
            var5_5.recycle();
            this.mParseError = -108;
            return false;
        }
        var6_6.info.group = var5_5.getNonResourceString(4);
        if (var6_6.info.group != null) {
            var6_6.info.group = var6_6.info.group.intern();
        }
        var6_6.info.descriptionRes = var5_5.getResourceId(5, 0);
        var6_6.info.requestRes = var5_5.getResourceId(11, 0);
        var6_6.info.protectionLevel = var5_5.getInt(3, 0);
        var6_6.info.flags = var5_5.getInt(7, 0);
        if (var6_6.info.isRuntime() && "android".equals(var6_6.info.packageName)) {
            if ((var6_6.info.flags & 4) != 0 && (var6_6.info.flags & 8) != 0) {
                var1_1 = new StringBuilder();
                var1_1.append("Permission cannot be both soft and hard restricted: ");
                var1_1.append(var6_6.info.name);
                throw new IllegalStateException(var1_1.toString());
            }
        } else {
            var7_7 = var6_6.info;
            var7_7.flags &= -5;
            var7_7 = var6_6.info;
            var7_7.flags &= -9;
        }
        var5_5.recycle();
        if (var6_6.info.protectionLevel == -1) {
            var4_4[0] = "<permission> does not specify protectionLevel";
            this.mParseError = -108;
            return false;
        }
        var6_6.info.protectionLevel = PermissionInfo.fixProtectionLevel(var6_6.info.protectionLevel);
        if (var6_6.info.getProtectionFlags() != 0 && (var6_6.info.protectionLevel & 4096) == 0 && (var6_6.info.protectionLevel & 8192) == 0 && (var6_6.info.protectionLevel & 15) != 2) {
            var4_4[0] = "<permission>  protectionLevel specifies a non-instant flag but is not based on signature type";
            this.mParseError = -108;
            return false;
        }
        if (!this.parseAllMetaData(var2_2, var3_3, "<permission>", (Component<?>)var6_6, var4_4)) {
            this.mParseError = -108;
            return false;
        }
        var1_1.permissions.add((Permission)var6_6);
        return true;
    }

    private boolean parsePermissionGroup(Package package_, int n, Resources resources, XmlResourceParser xmlResourceParser, String[] arrstring) throws XmlPullParserException, IOException {
        TypedArray typedArray = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPermissionGroup);
        PermissionGroup permissionGroup = new PermissionGroup(package_, typedArray.getResourceId(12, 0), typedArray.getResourceId(9, 0), typedArray.getResourceId(10, 0));
        if (!PackageParser.parsePackageItemInfo(package_, permissionGroup.info, arrstring, "<permission-group>", typedArray, true, 2, 0, 1, 8, 5, 7)) {
            typedArray.recycle();
            this.mParseError = -108;
            return false;
        }
        permissionGroup.info.descriptionRes = typedArray.getResourceId(4, 0);
        permissionGroup.info.requestRes = typedArray.getResourceId(11, 0);
        permissionGroup.info.flags = typedArray.getInt(6, 0);
        permissionGroup.info.priority = typedArray.getInt(3, 0);
        typedArray.recycle();
        if (!this.parseAllMetaData(resources, xmlResourceParser, "<permission-group>", permissionGroup, arrstring)) {
            this.mParseError = -108;
            return false;
        }
        package_.permissionGroups.add(permissionGroup);
        return true;
    }

    private boolean parsePermissionTree(Package object, Resources resources, XmlResourceParser xmlResourceParser, String[] arrstring) throws XmlPullParserException, IOException {
        Permission permission2 = new Permission((Package)object, (String)null);
        TypedArray typedArray = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPermissionTree);
        if (!PackageParser.parsePackageItemInfo((Package)object, permission2.info, arrstring, "<permission-tree>", typedArray, true, 2, 0, 1, 5, 3, 4)) {
            typedArray.recycle();
            this.mParseError = -108;
            return false;
        }
        typedArray.recycle();
        int n = permission2.info.name.indexOf(46);
        if (n > 0) {
            n = permission2.info.name.indexOf(46, n + 1);
        }
        if (n < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("<permission-tree> name has less than three segments: ");
            ((StringBuilder)object).append(permission2.info.name);
            arrstring[0] = ((StringBuilder)object).toString();
            this.mParseError = -108;
            return false;
        }
        permission2.info.descriptionRes = 0;
        permission2.info.requestRes = 0;
        permission2.info.protectionLevel = 0;
        permission2.tree = true;
        if (!this.parseAllMetaData(resources, xmlResourceParser, "<permission-tree>", permission2, arrstring)) {
            this.mParseError = -108;
            return false;
        }
        ((Package)object).permissions.add(permission2);
        return true;
    }

    private Provider parseProvider(Package package_, Resources resources, XmlResourceParser xmlResourceParser, int n, String[] arrstring, CachedComponentArgs object) throws XmlPullParserException, IOException {
        Object object2;
        TypedArray typedArray = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestProvider);
        if (((CachedComponentArgs)object).mProviderArgs == null) {
            ((CachedComponentArgs)object).mProviderArgs = new ParseComponentArgs(package_, arrstring, 2, 0, 1, 19, 15, 17, this.mSeparateProcesses, 8, 14, 6);
            object.mProviderArgs.tag = "<provider>";
        }
        object.mProviderArgs.sa = typedArray;
        object.mProviderArgs.flags = n;
        Provider provider = new Provider(((CachedComponentArgs)object).mProviderArgs, new ProviderInfo());
        if (arrstring[0] != null) {
            typedArray.recycle();
            return null;
        }
        boolean bl = package_.applicationInfo.targetSdkVersion < 17;
        provider.info.exported = typedArray.getBoolean(7, bl);
        String string2 = typedArray.getNonConfigurationString(10, 0);
        provider.info.isSyncable = typedArray.getBoolean(11, false);
        object = typedArray.getNonConfigurationString(3, 0);
        Object object3 = object2 = typedArray.getNonConfigurationString(4, 0);
        if (object2 == null) {
            object3 = object;
        }
        if (object3 == null) {
            provider.info.readPermission = package_.applicationInfo.permission;
        } else {
            object2 = provider.info;
            object3 = ((String)object3).length() > 0 ? ((String)object3).toString().intern() : null;
            ((ProviderInfo)object2).readPermission = object3;
        }
        object3 = typedArray.getNonConfigurationString(5, 0);
        if (object3 != null) {
            object = object3;
        }
        if (object == null) {
            provider.info.writePermission = package_.applicationInfo.permission;
        } else {
            object3 = provider.info;
            object = ((String)object).length() > 0 ? ((String)object).toString().intern() : null;
            ((ProviderInfo)object3).writePermission = object;
        }
        provider.info.grantUriPermissions = typedArray.getBoolean(13, false);
        provider.info.forceUriPermissions = typedArray.getBoolean(22, false);
        provider.info.multiprocess = typedArray.getBoolean(9, false);
        provider.info.initOrder = typedArray.getInt(12, 0);
        provider.info.splitName = typedArray.getNonConfigurationString(21, 0);
        provider.info.flags = 0;
        if (typedArray.getBoolean(16, false)) {
            object = provider.info;
            ((ProviderInfo)object).flags |= 1073741824;
        }
        object = provider.info;
        object3 = provider.info;
        ((ProviderInfo)object3).directBootAware = bl = typedArray.getBoolean(18, false);
        ((ProviderInfo)object).encryptionAware = bl;
        if (provider.info.directBootAware) {
            object = package_.applicationInfo;
            ((ApplicationInfo)object).privateFlags |= 256;
        }
        if (bl = typedArray.getBoolean(20, false)) {
            object = provider.info;
            ((ProviderInfo)object).flags |= 1048576;
            package_.visibleToInstantApps = true;
        }
        typedArray.recycle();
        if ((package_.applicationInfo.privateFlags & 2) != 0 && provider.info.processName == package_.packageName) {
            arrstring[0] = "Heavy-weight applications can not have providers in main process";
            return null;
        }
        if (string2 == null) {
            arrstring[0] = "<provider> does not include authorities attribute";
            return null;
        }
        if (string2.length() <= 0) {
            arrstring[0] = "<provider> has empty authorities attribute";
            return null;
        }
        provider.info.authority = string2.intern();
        if (!this.parseProviderTags(resources, xmlResourceParser, bl, provider, arrstring)) {
            return null;
        }
        return provider;
    }

    private boolean parseProviderTags(Resources resources, XmlResourceParser xmlResourceParser, boolean bl, Provider provider, String[] arrstring) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlResourceParser.getDepth();
        while ((n = xmlResourceParser.next()) != 1 && (n != 3 || xmlResourceParser.getDepth() > n2)) {
            Object object;
            Object object2;
            Object object3;
            if (n == 3 || n == 4) continue;
            if (xmlResourceParser.getName().equals("intent-filter")) {
                object3 = new ProviderIntentInfo(provider);
                if (!this.parseIntent(resources, xmlResourceParser, true, false, (IntentInfo)object3, arrstring)) {
                    return false;
                }
                if (bl) {
                    object3.setVisibilityToInstantApp(1);
                    object2 = provider.info;
                    ((ProviderInfo)object2).flags |= 1048576;
                }
                provider.order = Math.max(object3.getOrder(), provider.order);
                provider.intents.add(object3);
                continue;
            }
            if (xmlResourceParser.getName().equals("meta-data")) {
                object2 = this.parseMetaData(resources, xmlResourceParser, provider.metaData, arrstring);
                provider.metaData = object2;
                if (object2 != null) continue;
                return false;
            }
            if (xmlResourceParser.getName().equals("grant-uri-permission")) {
                object3 = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestGrantUriPermission);
                object2 = null;
                object = object3.getNonConfigurationString(0, 0);
                if (object != null) {
                    object2 = new PatternMatcher((String)object, 0);
                }
                if ((object = object3.getNonConfigurationString(1, 0)) != null) {
                    object2 = new PatternMatcher((String)object, 1);
                }
                if ((object = object3.getNonConfigurationString(2, 0)) != null) {
                    object2 = new PatternMatcher((String)object, 2);
                }
                object3.recycle();
                if (object2 != null) {
                    if (provider.info.uriPermissionPatterns == null) {
                        provider.info.uriPermissionPatterns = new PatternMatcher[1];
                        provider.info.uriPermissionPatterns[0] = object2;
                    } else {
                        n = provider.info.uriPermissionPatterns.length;
                        object3 = new PatternMatcher[n + 1];
                        System.arraycopy(provider.info.uriPermissionPatterns, 0, object3, 0, n);
                        object3[n] = object2;
                        provider.info.uriPermissionPatterns = object3;
                    }
                    provider.info.grantUriPermissions = true;
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unknown element under <path-permission>: ");
                ((StringBuilder)object2).append(xmlResourceParser.getName());
                ((StringBuilder)object2).append(" at ");
                ((StringBuilder)object2).append(this.mArchiveSourcePath);
                ((StringBuilder)object2).append(" ");
                ((StringBuilder)object2).append(xmlResourceParser.getPositionDescription());
                Slog.w(TAG, ((StringBuilder)object2).toString());
                XmlUtils.skipCurrentTag(xmlResourceParser);
                continue;
            }
            if (xmlResourceParser.getName().equals("path-permission")) {
                TypedArray typedArray = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPathPermission);
                object = typedArray.getNonConfigurationString(0, 0);
                object3 = typedArray.getNonConfigurationString(1, 0);
                if (object3 == null) {
                    object3 = object;
                }
                String string2 = typedArray.getNonConfigurationString(2, 0);
                object2 = string2;
                if (string2 == null) {
                    object2 = object;
                }
                n = 0;
                if (object3 != null) {
                    object3 = object3.intern();
                    n = 1;
                }
                if (object2 != null) {
                    object = ((String)object2).intern();
                    n = 1;
                } else {
                    object = object2;
                }
                if (n == 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("No readPermission or writePermssion for <path-permission>: ");
                    ((StringBuilder)object2).append(xmlResourceParser.getName());
                    ((StringBuilder)object2).append(" at ");
                    ((StringBuilder)object2).append(this.mArchiveSourcePath);
                    ((StringBuilder)object2).append(" ");
                    ((StringBuilder)object2).append(xmlResourceParser.getPositionDescription());
                    Slog.w(TAG, ((StringBuilder)object2).toString());
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                    continue;
                }
                object2 = typedArray.getNonConfigurationString(3, 0);
                object2 = object2 != null ? new PathPermission((String)object2, 0, (String)object3, (String)object) : null;
                string2 = typedArray.getNonConfigurationString(4, 0);
                if (string2 != null) {
                    object2 = new PathPermission(string2, 1, (String)object3, (String)object);
                }
                if ((string2 = typedArray.getNonConfigurationString(5, 0)) != null) {
                    object2 = new PathPermission(string2, 2, (String)object3, (String)object);
                }
                if ((string2 = typedArray.getNonConfigurationString(6, 0)) != null) {
                    object2 = new PathPermission(string2, 3, (String)object3, (String)object);
                }
                typedArray.recycle();
                if (object2 != null) {
                    if (provider.info.pathPermissions == null) {
                        provider.info.pathPermissions = new PathPermission[1];
                        provider.info.pathPermissions[0] = object2;
                    } else {
                        n = provider.info.pathPermissions.length;
                        object3 = new PathPermission[n + 1];
                        System.arraycopy(provider.info.pathPermissions, 0, object3, 0, n);
                        object3[n] = object2;
                        provider.info.pathPermissions = object3;
                    }
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("No path, pathPrefix, or pathPattern for <path-permission>: ");
                ((StringBuilder)object2).append(xmlResourceParser.getName());
                ((StringBuilder)object2).append(" at ");
                ((StringBuilder)object2).append(this.mArchiveSourcePath);
                ((StringBuilder)object2).append(" ");
                ((StringBuilder)object2).append(xmlResourceParser.getPositionDescription());
                Slog.w(TAG, ((StringBuilder)object2).toString());
                XmlUtils.skipCurrentTag(xmlResourceParser);
                continue;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unknown element under <provider>: ");
            ((StringBuilder)object2).append(xmlResourceParser.getName());
            ((StringBuilder)object2).append(" at ");
            ((StringBuilder)object2).append(this.mArchiveSourcePath);
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(xmlResourceParser.getPositionDescription());
            Slog.w(TAG, ((StringBuilder)object2).toString());
            XmlUtils.skipCurrentTag(xmlResourceParser);
        }
        return true;
    }

    public static final PublicKey parsePublicKey(String object) {
        if (object == null) {
            Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            object = new X509EncodedKeySpec(Base64.decode((String)object, 0));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
        try {
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic((KeySpec)object);
            return publicKey;
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Slog.wtf(TAG, "Could not parse public key: RSA KeyFactory not included in build");
        }
        try {
            PublicKey publicKey = KeyFactory.getInstance("EC").generatePublic((KeySpec)object);
            return publicKey;
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Slog.wtf(TAG, "Could not parse public key: EC KeyFactory not included in build");
        }
        try {
            object = KeyFactory.getInstance("DSA").generatePublic((KeySpec)object);
            return object;
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Slog.wtf(TAG, "Could not parse public key: DSA KeyFactory not included in build");
        }
        return null;
    }

    private Service parseService(Package object, Resources resources, XmlResourceParser xmlResourceParser, int n, String[] arrstring, CachedComponentArgs object2) throws XmlPullParserException, IOException {
        int n2;
        ServiceInfo serviceInfo;
        XmlResourceParser xmlResourceParser2 = xmlResourceParser;
        Object object3 = arrstring;
        Object object4 = resources.obtainAttributes(xmlResourceParser2, R.styleable.AndroidManifestService);
        if (object2.mServiceArgs == null) {
            object2.mServiceArgs = new ParseComponentArgs((Package)object, arrstring, 2, 0, 1, 15, 8, 12, this.mSeparateProcesses, 6, 7, 4);
            object2.mServiceArgs.tag = "<service>";
        }
        object2.mServiceArgs.sa = object4;
        object2.mServiceArgs.flags = n;
        Service service = new Service(object2.mServiceArgs, new ServiceInfo());
        if (object3[0] != null) {
            ((TypedArray)object4).recycle();
            return null;
        }
        boolean bl = ((TypedArray)object4).hasValue(5);
        if (bl) {
            service.info.exported = ((TypedArray)object4).getBoolean(5, false);
        }
        if ((object2 = ((TypedArray)object4).getNonConfigurationString(3, 0)) == null) {
            service.info.permission = object.applicationInfo.permission;
        } else {
            serviceInfo = service.info;
            object2 = object2.length() > 0 ? object2.toString().intern() : null;
            serviceInfo.permission = object2;
        }
        service.info.splitName = ((TypedArray)object4).getNonConfigurationString(17, 0);
        service.info.mForegroundServiceType = ((TypedArray)object4).getInt(19, 0);
        service.info.flags = 0;
        boolean bl2 = ((TypedArray)object4).getBoolean(9, false);
        n = 1;
        if (bl2) {
            object2 = service.info;
            object2.flags |= 1;
        }
        if (((TypedArray)object4).getBoolean(10, false)) {
            object2 = service.info;
            object2.flags |= 2;
        }
        if (((TypedArray)object4).getBoolean(14, false)) {
            object2 = service.info;
            object2.flags |= 4;
        }
        if (((TypedArray)object4).getBoolean(18, false)) {
            object2 = service.info;
            object2.flags |= 8;
        }
        if (((TypedArray)object4).getBoolean(11, false)) {
            object2 = service.info;
            object2.flags |= 1073741824;
        }
        object2 = service.info;
        serviceInfo = service.info;
        serviceInfo.directBootAware = bl2 = ((TypedArray)object4).getBoolean(13, false);
        object2.encryptionAware = bl2;
        if (service.info.directBootAware) {
            object2 = ((Package)object).applicationInfo;
            object2.privateFlags |= 256;
        }
        if (bl2 = ((TypedArray)object4).getBoolean(16, false)) {
            object2 = service.info;
            object2.flags |= 1048576;
            ((Package)object).visibleToInstantApps = true;
        }
        ((TypedArray)object4).recycle();
        if ((object.applicationInfo.privateFlags & 2) != 0 && service.info.processName == ((Package)object).packageName) {
            object3[0] = "Heavy-weight applications can not have services in main process";
            return null;
        }
        int n3 = xmlResourceParser.getDepth();
        object2 = object3;
        object = object4;
        while ((n2 = xmlResourceParser.next()) != n) {
            if (n2 == 3 && xmlResourceParser.getDepth() <= n3) {
                n = 1;
                break;
            }
            if (n2 != 3) {
                if (n2 == 4) {
                    n = 1;
                    continue;
                }
                if (xmlResourceParser.getName().equals("intent-filter")) {
                    object4 = new ServiceIntentInfo(service);
                    if (!this.parseIntent(resources, xmlResourceParser, true, false, (IntentInfo)object4, arrstring)) {
                        return null;
                    }
                    if (bl2) {
                        ((IntentFilter)object4).setVisibilityToInstantApp(1);
                        object3 = service.info;
                        ((ServiceInfo)object3).flags |= 1048576;
                    }
                    service.order = Math.max(((IntentFilter)object4).getOrder(), service.order);
                    service.intents.add(object4);
                    n = 1;
                    continue;
                }
                if (xmlResourceParser.getName().equals("meta-data")) {
                    service.metaData = object3 = this.parseMetaData(resources, xmlResourceParser2, service.metaData, (String[])object2);
                    if (object3 == null) {
                        return null;
                    }
                    n = 1;
                    continue;
                }
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Unknown element under <service>: ");
                ((StringBuilder)object3).append(xmlResourceParser.getName());
                ((StringBuilder)object3).append(" at ");
                ((StringBuilder)object3).append(this.mArchiveSourcePath);
                ((StringBuilder)object3).append(" ");
                ((StringBuilder)object3).append(xmlResourceParser.getPositionDescription());
                Slog.w(TAG, ((StringBuilder)object3).toString());
                XmlUtils.skipCurrentTag(xmlResourceParser);
                n = 1;
                continue;
            }
            n = 1;
        }
        if (!bl) {
            object = service.info;
            if (service.intents.size() <= 0) {
                n = 0;
            }
            ((ServiceInfo)object).exported = n;
        }
        return service;
    }

    private Package parseSplitApk(Package package_, Resources resources, XmlResourceParser xmlResourceParser, int n, int n2, String[] arrstring) throws XmlPullParserException, IOException, PackageParserException {
        int n3;
        PackageParser.parsePackageSplitNames(xmlResourceParser, xmlResourceParser);
        this.mParseInstrumentationArgs = null;
        boolean bl = false;
        int n4 = xmlResourceParser.getDepth();
        while ((n3 = xmlResourceParser.next()) != 1 && (n3 != 3 || xmlResourceParser.getDepth() > n4)) {
            if (n3 == 3 || n3 == 4) continue;
            if (xmlResourceParser.getName().equals(TAG_APPLICATION)) {
                if (bl) {
                    Slog.w(TAG, "<manifest> has more than one <application>");
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                    continue;
                }
                bl = true;
                if (this.parseSplitApplication(package_, resources, xmlResourceParser, n, n2, arrstring)) continue;
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown element under <manifest>: ");
            stringBuilder.append(xmlResourceParser.getName());
            stringBuilder.append(" at ");
            stringBuilder.append(this.mArchiveSourcePath);
            stringBuilder.append(" ");
            stringBuilder.append(xmlResourceParser.getPositionDescription());
            Slog.w(TAG, stringBuilder.toString());
            XmlUtils.skipCurrentTag(xmlResourceParser);
        }
        if (!bl) {
            arrstring[0] = "<manifest> does not contain an <application>";
            this.mParseError = -109;
        }
        return package_;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void parseSplitApk(Package object, int n, AssetManager object2, int n2) throws PackageParserException {
        block17 : {
            block18 : {
                block15 : {
                    block16 : {
                        string2 = object.splitCodePaths[n];
                        this.mParseError = 1;
                        this.mArchiveSourcePath = string2;
                        var6_15 = null;
                        object4 = null;
                        packageParserException = null;
                        n3 = object2.findCookieForPath(string2);
                        if (n3 == 0) break block15;
                        object3 = object2.openXmlResourceParser(n3, "AndroidManifest.xml");
                        object4 = new Resources((AssetManager)object2, this.mMetrics, null);
                        object2 = new String[1];
                        object = this.parseSplitApk((Package)object, (Resources)object4, (XmlResourceParser)object3, n2, n, (String[])object2);
                        if (object == null) break block16;
                        IoUtils.closeQuietly((AutoCloseable)object3);
                        return;
                    }
                    try {
                        n = this.mParseError;
                        object4 = new StringBuilder();
                        object4.append(string2);
                        object4.append(" (at ");
                        object4.append(object3.getPositionDescription());
                        object4.append("): ");
                        object4.append((String)object2[0]);
                        object = new PackageParserException(n, object4.toString());
                        throw object;
                    }
                    catch (Throwable throwable) {
                        object2 = object3;
                        break block17;
                    }
                    catch (Exception exception) {
                        object = object3;
                        object3 = exception;
                        break block18;
                    }
                    catch (PackageParserException packageParserException2) {
                        object = object3;
                        object3 = packageParserException2;
                        ** GOTO lbl89
                    }
                    catch (Throwable throwable) {
                        object2 = object3;
                        break block17;
                    }
                    catch (Exception exception) {
                        object = object3;
                        object3 = exception;
                        break block18;
                    }
                    catch (PackageParserException packageParserException3) {
                        object2 = object3;
                        object3 = packageParserException3;
                        object = object2;
                        ** GOTO lbl89
                    }
                }
                try {
                    object2 = new StringBuilder();
                    object2.append("Failed adding asset path: ");
                    object2.append(string2);
                    object = new PackageParserException(-101, object2.toString());
                    throw object;
                }
                catch (Throwable throwable) {
                    object2 = object4;
                    break block17;
                }
                catch (Exception exception) {
                    object = packageParserException;
                }
            }
            object2 = object;
            try {
                object2 = object;
                object2 = object;
                object4 = new StringBuilder();
                object2 = object;
                object4.append("Failed to read manifest from ");
                object2 = object;
                object4.append(string2);
                object2 = object;
                packageParserException = new PackageParserException(-102, object4.toString(), (Throwable)object3);
                object2 = object;
                throw packageParserException;
                catch (PackageParserException packageParserException4) {
                    object = var6_15;
                }
lbl89: // 3 sources:
                object2 = object;
                throw object3;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        IoUtils.closeQuietly((AutoCloseable)object2);
        throw var1_7;
    }

    private boolean parseSplitApplication(Package object, Resources resources, XmlResourceParser xmlResourceParser, int n, int n2, String[] arrstring) throws XmlPullParserException, IOException {
        int n3;
        Object object2;
        PackageParser packageParser = this;
        Package package_ = object;
        Object object3 = resources;
        XmlResourceParser xmlResourceParser2 = xmlResourceParser;
        Object object4 = arrstring;
        Object object5 = ((Resources)object3).obtainAttributes(xmlResourceParser2, R.styleable.AndroidManifestApplication);
        if (((TypedArray)object5).getBoolean(7, true)) {
            object2 = package_.splitFlags;
            object2[n2] = object2[n2] | 4;
        }
        object2 = ((TypedArray)object5).getString(46);
        int n4 = -108;
        int n5 = 0;
        if (object2 != null && !ClassLoaderFactory.isValidClassLoaderName((String)object2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid class loader name: ");
            ((StringBuilder)object).append((String)object2);
            object4[0] = ((StringBuilder)object).toString();
            packageParser.mParseError = -108;
            return false;
        }
        package_.applicationInfo.splitClassLoaderNames[n2] = object2;
        int n6 = xmlResourceParser.getDepth();
        while ((n3 = xmlResourceParser.next()) != 1 && (n3 != 3 || xmlResourceParser.getDepth() > n6)) {
            Object object6;
            String string2;
            block29 : {
                block20 : {
                    int n7;
                    block26 : {
                        block28 : {
                            XmlResourceParser xmlResourceParser3;
                            block27 : {
                                block25 : {
                                    block24 : {
                                        CachedComponentArgs cachedComponentArgs;
                                        block23 : {
                                            block22 : {
                                                block21 : {
                                                    block19 : {
                                                        if (n3 == 3 || n3 == 4) continue;
                                                        cachedComponentArgs = new CachedComponentArgs();
                                                        string2 = xmlResourceParser.getName();
                                                        if (!string2.equals("activity")) break block19;
                                                        object3 = this.parseActivity((Package)object, resources, xmlResourceParser, n, arrstring, cachedComponentArgs, false, package_.baseHardwareAccelerated);
                                                        if (object3 == null) {
                                                            packageParser.mParseError = n4;
                                                            return false;
                                                        }
                                                        package_.activities.add((Activity)object3);
                                                        object3 = ((Activity)object3).info;
                                                        n5 = 0;
                                                        break block20;
                                                    }
                                                    if (!string2.equals("receiver")) break block21;
                                                    object4 = this.parseActivity((Package)object, resources, xmlResourceParser, n, arrstring, cachedComponentArgs, true, false);
                                                    if (object4 == null) {
                                                        packageParser.mParseError = -108;
                                                        return false;
                                                    }
                                                    n4 = -108;
                                                    n5 = 0;
                                                    package_.receivers.add((Activity)object4);
                                                    object3 = object4.info;
                                                    object4 = arrstring;
                                                    break block20;
                                                }
                                                object3 = resources;
                                                xmlResourceParser3 = xmlResourceParser2;
                                                object6 = packageParser;
                                                n7 = n5;
                                                object5 = package_;
                                                n3 = n4;
                                                if (!string2.equals("service")) break block22;
                                                object4 = this.parseService((Package)object, resources, xmlResourceParser, n, arrstring, cachedComponentArgs);
                                                if (object4 == null) {
                                                    ((PackageParser)object6).mParseError = n3;
                                                    return n7 != 0;
                                                }
                                                ((Package)object5).services.add((Service)object4);
                                                object3 = object4.info;
                                                object4 = arrstring;
                                                n4 = n3;
                                                n5 = n7;
                                                break block20;
                                            }
                                            if (!string2.equals("provider")) break block23;
                                            object4 = this.parseProvider((Package)object, resources, xmlResourceParser, n, arrstring, cachedComponentArgs);
                                            if (object4 == null) {
                                                ((PackageParser)object6).mParseError = n3;
                                                return n7 != 0;
                                            }
                                            ((Package)object5).providers.add((Provider)object4);
                                            object3 = object4.info;
                                            object4 = arrstring;
                                            n4 = n3;
                                            n5 = n7;
                                            break block20;
                                        }
                                        if (!string2.equals("activity-alias")) break block24;
                                        object4 = this.parseActivityAlias((Package)object, resources, xmlResourceParser, n, arrstring, cachedComponentArgs);
                                        if (object4 == null) {
                                            ((PackageParser)object6).mParseError = n3;
                                            return n7 != 0;
                                        }
                                        ((Package)object5).activities.add((Activity)object4);
                                        object3 = object4.info;
                                        object4 = arrstring;
                                        n4 = n3;
                                        n5 = n7;
                                        break block20;
                                    }
                                    if (!xmlResourceParser.getName().equals("meta-data")) break block25;
                                    ((Package)object5).mAppMetaData = object4 = ((PackageParser)object6).parseMetaData((Resources)object3, xmlResourceParser3, ((Package)object5).mAppMetaData, arrstring);
                                    if (object4 == null) {
                                        ((PackageParser)object6).mParseError = n3;
                                        return n7 != 0;
                                    }
                                    break block26;
                                }
                                object4 = arrstring;
                                if (!string2.equals("uses-static-library")) break block27;
                                if (!((PackageParser)object6).parseUsesStaticLibrary((Package)object5, (Resources)object3, xmlResourceParser3, (String[])object4)) {
                                    return n7 != 0;
                                }
                                break block26;
                            }
                            if (!string2.equals("uses-library")) break block28;
                            object6 = ((Resources)object3).obtainAttributes(xmlResourceParser3, R.styleable.AndroidManifestUsesLibrary);
                            object3 = ((TypedArray)object6).getNonResourceString(n7);
                            boolean bl = ((TypedArray)object6).getBoolean(1, true);
                            ((TypedArray)object6).recycle();
                            if (object3 != null) {
                                object3 = ((String)object3).intern();
                                if (bl) {
                                    ((Package)object5).usesLibraries = ArrayUtils.add(((Package)object5).usesLibraries, object3);
                                    ((Package)object5).usesOptionalLibraries = ArrayUtils.remove(((Package)object5).usesOptionalLibraries, object3);
                                } else if (!ArrayUtils.contains(((Package)object5).usesLibraries, object3)) {
                                    ((Package)object5).usesOptionalLibraries = ArrayUtils.add(((Package)object5).usesOptionalLibraries, object3);
                                }
                            }
                            XmlUtils.skipCurrentTag(xmlResourceParser);
                            object3 = null;
                            n4 = n3;
                            n5 = n7;
                            break block20;
                        }
                        if (!string2.equals("uses-package")) break block29;
                        XmlUtils.skipCurrentTag(xmlResourceParser);
                    }
                    object4 = arrstring;
                    object3 = null;
                    n5 = n7;
                    n4 = n3;
                }
                if (object3 != null && ((ComponentInfo)object3).splitName == null) {
                    ((ComponentInfo)object3).splitName = package_.splitNames[n2];
                }
                object3 = resources;
                continue;
            }
            object5 = new StringBuilder();
            ((StringBuilder)object5).append("Unknown element under <application>: ");
            ((StringBuilder)object5).append(string2);
            ((StringBuilder)object5).append(" at ");
            ((StringBuilder)object5).append(((PackageParser)object6).mArchiveSourcePath);
            ((StringBuilder)object5).append(" ");
            ((StringBuilder)object5).append(xmlResourceParser.getPositionDescription());
            Slog.w(TAG, ((StringBuilder)object5).toString());
            XmlUtils.skipCurrentTag(xmlResourceParser);
        }
        return true;
    }

    private FeatureInfo parseUsesFeature(Resources object, AttributeSet attributeSet) {
        FeatureInfo featureInfo = new FeatureInfo();
        object = ((Resources)object).obtainAttributes(attributeSet, R.styleable.AndroidManifestUsesFeature);
        featureInfo.name = ((TypedArray)object).getNonResourceString(0);
        featureInfo.version = ((TypedArray)object).getInt(3, 0);
        if (featureInfo.name == null) {
            featureInfo.reqGlEsVersion = ((TypedArray)object).getInt(1, 0);
        }
        if (((TypedArray)object).getBoolean(2, true)) {
            featureInfo.flags |= 1;
        }
        ((TypedArray)object).recycle();
        return featureInfo;
    }

    private boolean parseUsesPermission(Package package_, Resources object, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        Object object2 = ((Resources)object).obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUsesPermission);
        object = ((TypedArray)object2).getNonResourceString(0);
        int n = 0;
        Object object3 = ((TypedArray)object2).peekValue(1);
        int n2 = n;
        if (object3 != null) {
            n2 = n;
            if (((TypedValue)object3).type >= 16) {
                n2 = n;
                if (((TypedValue)object3).type <= 31) {
                    n2 = ((TypedValue)object3).data;
                }
            }
        }
        String string2 = ((TypedArray)object2).getNonConfigurationString(2, 0);
        object3 = ((TypedArray)object2).getNonConfigurationString(3, 0);
        ((TypedArray)object2).recycle();
        XmlUtils.skipCurrentTag(xmlResourceParser);
        if (object == null) {
            return true;
        }
        if (n2 != 0 && n2 < Build.VERSION.RESOURCES_SDK_INT) {
            return true;
        }
        if (string2 != null && (object2 = this.mCallback) != null && !object2.hasFeature(string2)) {
            return true;
        }
        if (object3 != null && (object2 = this.mCallback) != null && object2.hasFeature((String)object3)) {
            return true;
        }
        if (package_.requestedPermissions.indexOf(object) == -1) {
            package_.requestedPermissions.add(((String)object).intern());
        } else {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Ignoring duplicate uses-permissions/uses-permissions-sdk-m: ");
            ((StringBuilder)object3).append((String)object);
            ((StringBuilder)object3).append(" in package: ");
            ((StringBuilder)object3).append(package_.packageName);
            ((StringBuilder)object3).append(" at: ");
            ((StringBuilder)object3).append(xmlResourceParser.getPositionDescription());
            Slog.w(TAG, ((StringBuilder)object3).toString());
        }
        return true;
    }

    private boolean parseUsesStaticLibrary(Package object, Resources arrstring, XmlResourceParser arrstring2, String[] arrstring3) throws XmlPullParserException, IOException {
        Object object2 = arrstring.obtainAttributes((AttributeSet)arrstring2, R.styleable.AndroidManifestUsesStaticLibrary);
        String string2 = ((TypedArray)object2).getNonResourceString(0);
        int n = ((TypedArray)object2).getInt(1, -1);
        String[] arrstring4 = ((TypedArray)object2).getNonResourceString(2);
        ((TypedArray)object2).recycle();
        if (string2 != null && n >= 0 && arrstring4 != null) {
            if (((Package)object).usesStaticLibraries != null && ((Package)object).usesStaticLibraries.contains(string2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Depending on multiple versions of static library ");
                ((StringBuilder)object).append(string2);
                arrstring3[0] = ((StringBuilder)object).toString();
                this.mParseError = -108;
                XmlUtils.skipCurrentTag((XmlPullParser)arrstring2);
                return false;
            }
            string2 = string2.intern();
            object2 = arrstring4.replace(":", "").toLowerCase();
            arrstring4 = EmptyArray.STRING;
            if (object.applicationInfo.targetSdkVersion >= 27) {
                arrstring2 = this.parseAdditionalCertificates((Resources)arrstring, (XmlResourceParser)arrstring2, arrstring3);
                arrstring = arrstring2;
                if (arrstring2 == null) {
                    return false;
                }
            } else {
                XmlUtils.skipCurrentTag((XmlPullParser)arrstring2);
                arrstring = arrstring4;
            }
            arrstring2 = new String[arrstring.length + 1];
            arrstring2[0] = object2;
            System.arraycopy(arrstring, 0, arrstring2, 1, arrstring.length);
            ((Package)object).usesStaticLibraries = ArrayUtils.add(((Package)object).usesStaticLibraries, string2);
            ((Package)object).usesStaticLibrariesVersions = ArrayUtils.appendLong(((Package)object).usesStaticLibrariesVersions, n, true);
            ((Package)object).usesStaticLibrariesCertDigests = ArrayUtils.appendElement(String[].class, ((Package)object).usesStaticLibrariesCertDigests, arrstring2, true);
            return true;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Bad uses-static-library declaration name: ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" version: ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" certDigest");
        ((StringBuilder)object).append((String)arrstring4);
        arrstring3[0] = ((StringBuilder)object).toString();
        this.mParseError = -108;
        XmlUtils.skipCurrentTag((XmlPullParser)arrstring2);
        return false;
    }

    private static VerifierInfo parseVerifier(AttributeSet object) {
        String string2 = null;
        String string3 = null;
        int n = object.getAttributeCount();
        for (int i = 0; i < n; ++i) {
            int n2 = object.getAttributeNameResource(i);
            if (n2 != 16842755) {
                if (n2 != 16843686) continue;
                string3 = object.getAttributeValue(i);
                continue;
            }
            string2 = object.getAttributeValue(i);
        }
        if (string2 != null && string2.length() != 0) {
            object = PackageParser.parsePublicKey(string3);
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to parse verifier public key for ");
                ((StringBuilder)object).append(string2);
                Slog.i(TAG, ((StringBuilder)object).toString());
                return null;
            }
            return new VerifierInfo(string2, (PublicKey)object);
        }
        Slog.i(TAG, "verifier package name was null; skipping");
        return null;
    }

    public static void readConfigUseRoundIcon(Resources object) {
        if (object != null) {
            sUseRoundIcon = ((Resources)object).getBoolean(17891559);
            return;
        }
        try {
            object = ActivityThread.getPackageManager().getApplicationInfo("android", 0, UserHandle.myUserId());
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        Resources resources = Resources.getSystem();
        sUseRoundIcon = ResourcesManager.getInstance().getResources(null, null, null, ((ApplicationInfo)object).resourceDirs, ((ApplicationInfo)object).sharedLibraryFiles, 0, null, resources.getCompatibilityInfo(), resources.getClassLoader()).getBoolean(17891559);
    }

    private void setActivityResizeMode(ActivityInfo activityInfo, TypedArray typedArray, Package package_) {
        int n = package_.applicationInfo.privateFlags;
        boolean bl = true;
        n = (n & 3072) != 0 ? 1 : 0;
        if (!typedArray.hasValue(40) && n == 0) {
            if ((package_.applicationInfo.privateFlags & 4096) != 0) {
                activityInfo.resizeMode = 1;
                return;
            }
            activityInfo.resizeMode = activityInfo.isFixedOrientationPortrait() ? 6 : (activityInfo.isFixedOrientationLandscape() ? 5 : (activityInfo.isFixedOrientation() ? 7 : 4));
            return;
        }
        if ((package_.applicationInfo.privateFlags & 1024) == 0) {
            bl = false;
        }
        activityInfo.resizeMode = typedArray.getBoolean(40, bl) ? 2 : 0;
    }

    @UnsupportedAppUsage
    public static void setCompatibilityModeEnabled(boolean bl) {
        sCompatibilityModeEnabled = bl;
    }

    private void setMaxAspectRatio(Package parcelable2) {
        float f;
        float f2 = parcelable2.applicationInfo.targetSdkVersion < 26 ? 1.86f : 0.0f;
        if (parcelable2.applicationInfo.maxAspectRatio != 0.0f) {
            f = parcelable2.applicationInfo.maxAspectRatio;
        } else {
            f = f2;
            if (((Package)parcelable2).mAppMetaData != null) {
                f = f2;
                if (((Package)parcelable2).mAppMetaData.containsKey(METADATA_MAX_ASPECT_RATIO)) {
                    f = ((Package)parcelable2).mAppMetaData.getFloat(METADATA_MAX_ASPECT_RATIO, f2);
                }
            }
        }
        for (Activity activity : ((Package)parcelable2).activities) {
            if (activity.hasMaxAspectRatio()) continue;
            f2 = activity.metaData != null ? activity.metaData.getFloat(METADATA_MAX_ASPECT_RATIO, f) : f;
            activity.setMaxAspectRatio(f2);
        }
    }

    private void setMinAspectRatio(Package parcelable2) {
        float f = parcelable2.applicationInfo.minAspectRatio;
        float f2 = 0.0f;
        if (f != 0.0f) {
            f2 = parcelable2.applicationInfo.minAspectRatio;
        } else if (parcelable2.applicationInfo.targetSdkVersion < 29) {
            Callback callback = this.mCallback;
            f2 = callback != null && callback.hasFeature("android.hardware.type.watch") ? 1.0f : 1.333f;
        }
        for (Activity activity : ((Package)parcelable2).activities) {
            if (activity.hasMinAspectRatio()) continue;
            activity.setMinAspectRatio(f2);
        }
    }

    @VisibleForTesting
    public static byte[] toCacheEntryStatic(Package arrby) {
        Parcel parcel = Parcel.obtain();
        PackageParserCacheHelper.WriteHelper writeHelper = new PackageParserCacheHelper.WriteHelper(parcel);
        arrby.writeToParcel(parcel, 0);
        writeHelper.finishAndUninstall();
        arrby = parcel.marshall();
        parcel.recycle();
        return arrby;
    }

    public static ArraySet<PublicKey> toSigningKeys(Signature[] arrsignature) throws CertificateException {
        ArraySet<PublicKey> arraySet = new ArraySet<PublicKey>(arrsignature.length);
        for (int i = 0; i < arrsignature.length; ++i) {
            arraySet.add(arrsignature[i].getPublicKey());
        }
        return arraySet;
    }

    private static void updateApplicationInfo(ApplicationInfo applicationInfo, int n, PackageUserState packageUserState) {
        if (!sCompatibilityModeEnabled) {
            applicationInfo.disableCompatibilityMode();
        }
        applicationInfo.flags = packageUserState.installed ? (applicationInfo.flags |= 8388608) : (applicationInfo.flags &= -8388609);
        applicationInfo.flags = packageUserState.suspended ? (applicationInfo.flags |= 1073741824) : (applicationInfo.flags &= -1073741825);
        applicationInfo.privateFlags = packageUserState.instantApp ? (applicationInfo.privateFlags |= 128) : (applicationInfo.privateFlags &= -129);
        applicationInfo.privateFlags = packageUserState.virtualPreload ? (applicationInfo.privateFlags |= 65536) : (applicationInfo.privateFlags &= -65537);
        boolean bl = packageUserState.hidden;
        boolean bl2 = true;
        applicationInfo.privateFlags = bl ? (applicationInfo.privateFlags |= 1) : (applicationInfo.privateFlags &= -2);
        if (packageUserState.enabled == 1) {
            applicationInfo.enabled = true;
        } else if (packageUserState.enabled == 4) {
            if ((32768 & n) == 0) {
                bl2 = false;
            }
            applicationInfo.enabled = bl2;
        } else if (packageUserState.enabled == 2 || packageUserState.enabled == 3) {
            applicationInfo.enabled = false;
        }
        applicationInfo.enabledSetting = packageUserState.enabled;
        if (applicationInfo.category == -1) {
            applicationInfo.category = packageUserState.categoryHint;
        }
        if (applicationInfo.category == -1) {
            applicationInfo.category = FallbackCategoryProvider.getFallbackCategory(applicationInfo.packageName);
        }
        applicationInfo.seInfoUser = SELinuxUtil.assignSeinfoUser(packageUserState);
        applicationInfo.resourceDirs = packageUserState.overlayPaths;
        n = sUseRoundIcon && applicationInfo.roundIconRes != 0 ? applicationInfo.roundIconRes : applicationInfo.iconRes;
        applicationInfo.icon = n;
    }

    private static String validateName(String charSequence, boolean bl, boolean bl2) {
        int n = ((String)charSequence).length();
        boolean bl3 = false;
        boolean bl4 = true;
        for (int i = 0; i < n; ++i) {
            char c;
            block9 : {
                boolean bl5;
                boolean bl6;
                block6 : {
                    block7 : {
                        block8 : {
                            block5 : {
                                c = ((String)charSequence).charAt(i);
                                if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) break block5;
                                bl6 = false;
                                bl5 = bl3;
                                break block6;
                            }
                            if (bl4) break block7;
                            if (c < '0') break block8;
                            bl5 = bl3;
                            bl6 = bl4;
                            if (c <= '9') break block6;
                        }
                        if (c != '_') break block7;
                        bl5 = bl3;
                        bl6 = bl4;
                        break block6;
                    }
                    if (c != '.') break block9;
                    bl5 = true;
                    bl6 = true;
                }
                bl3 = bl5;
                bl4 = bl6;
                continue;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("bad character '");
            ((StringBuilder)charSequence).append(c);
            ((StringBuilder)charSequence).append("'");
            return ((StringBuilder)charSequence).toString();
        }
        if (bl2 && !FileUtils.isValidExtFilename((String)charSequence)) {
            return "Invalid filename";
        }
        charSequence = !bl3 && bl ? "must have at least one '.' separator" : null;
        return charSequence;
    }

    @VisibleForTesting
    protected Package fromCacheEntry(byte[] arrby) {
        return PackageParser.fromCacheEntryStatic(arrby);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Deprecated
    @UnsupportedAppUsage
    public Package parseMonolithicPackage(File file, int n) throws PackageParserException {
        Throwable throwable2222;
        PackageLite packageLite = PackageParser.parseMonolithicPackageLite(file, n);
        if (this.mOnlyCoreApps && !packageLite.coreApp) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not a coreApp: ");
            stringBuilder.append(file);
            throw new PackageParserException(-108, stringBuilder.toString());
        }
        DefaultSplitAssetLoader defaultSplitAssetLoader = new DefaultSplitAssetLoader(packageLite, n);
        Package package_ = this.parseBaseApk(file, defaultSplitAssetLoader.getBaseAssetManager(), n);
        package_.setCodePath(file.getCanonicalPath());
        package_.setUse32bitAbi(packageLite.use32bitAbi);
        IoUtils.closeQuietly((AutoCloseable)defaultSplitAssetLoader);
        return package_;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to get path: ");
                stringBuilder.append(file);
                PackageParserException packageParserException = new PackageParserException(-102, stringBuilder.toString(), iOException);
                throw packageParserException;
            }
        }
        IoUtils.closeQuietly((AutoCloseable)defaultSplitAssetLoader);
        throw throwable2222;
    }

    @UnsupportedAppUsage
    public Package parsePackage(File file, int n) throws PackageParserException {
        return this.parsePackage(file, n, false);
    }

    @UnsupportedAppUsage
    public Package parsePackage(File file, int n, boolean bl) throws PackageParserException {
        Package package_ = bl ? this.getCachedResult(file, n) : null;
        if (package_ != null) {
            return package_;
        }
        bl = LOG_PARSE_TIMINGS;
        long l = 0L;
        long l2 = bl ? SystemClock.uptimeMillis() : 0L;
        package_ = file.isDirectory() ? this.parseClusterPackage(file, n) : this.parseMonolithicPackage(file, n);
        if (LOG_PARSE_TIMINGS) {
            l = SystemClock.uptimeMillis();
        }
        this.cacheResult(file, n, package_);
        if (LOG_PARSE_TIMINGS) {
            l2 = l - l2;
            l = SystemClock.uptimeMillis() - l;
            if (l2 + l > 100L) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Parse times for '");
                stringBuilder.append(file);
                stringBuilder.append("': parse=");
                stringBuilder.append(l2);
                stringBuilder.append("ms, update_cache=");
                stringBuilder.append(l);
                stringBuilder.append(" ms");
                Slog.i(TAG, stringBuilder.toString());
            }
        }
        return package_;
    }

    public void setCacheDir(File file) {
        this.mCacheDir = file;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.mMetrics = displayMetrics;
    }

    public void setOnlyCoreApps(boolean bl) {
        this.mOnlyCoreApps = bl;
    }

    @UnsupportedAppUsage
    public void setSeparateProcesses(String[] arrstring) {
        this.mSeparateProcesses = arrstring;
    }

    @VisibleForTesting
    protected byte[] toCacheEntry(Package package_) {
        return PackageParser.toCacheEntryStatic(package_);
    }

    public static final class Activity
    extends Component<ActivityIntentInfo>
    implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Activity>(){

            @Override
            public Activity createFromParcel(Parcel parcel) {
                return new Activity(parcel);
            }

            public Activity[] newArray(int n) {
                return new Activity[n];
            }
        };
        @UnsupportedAppUsage
        public final ActivityInfo info;
        private boolean mHasMaxAspectRatio;
        private boolean mHasMinAspectRatio;

        Activity(Package package_, String string2, ActivityInfo activityInfo) {
            super(package_, new ArrayList(0), string2);
            this.info = activityInfo;
            this.info.applicationInfo = package_.applicationInfo;
        }

        public Activity(ParseComponentArgs parseComponentArgs, ActivityInfo activityInfo) {
            super(parseComponentArgs, activityInfo);
            this.info = activityInfo;
            this.info.applicationInfo = parseComponentArgs.owner.applicationInfo;
        }

        private Activity(Parcel object) {
            super((Parcel)object);
            this.info = (ActivityInfo)((Parcel)object).readParcelable(Object.class.getClassLoader());
            this.mHasMaxAspectRatio = ((Parcel)object).readBoolean();
            this.mHasMinAspectRatio = ((Parcel)object).readBoolean();
            for (ActivityIntentInfo activityIntentInfo : this.intents) {
                activityIntentInfo.activity = this;
                this.order = Math.max(activityIntentInfo.getOrder(), this.order);
            }
            if (this.info.permission != null) {
                object = this.info;
                ((ActivityInfo)object).permission = ((ActivityInfo)object).permission.intern();
            }
        }

        private boolean hasMaxAspectRatio() {
            return this.mHasMaxAspectRatio;
        }

        private boolean hasMinAspectRatio() {
            return this.mHasMinAspectRatio;
        }

        private void setMaxAspectRatio(float f) {
            if (this.info.resizeMode != 2 && this.info.resizeMode != 1) {
                if (f < 1.0f && f != 0.0f) {
                    return;
                }
                this.info.maxAspectRatio = f;
                this.mHasMaxAspectRatio = true;
                return;
            }
        }

        private void setMinAspectRatio(float f) {
            if (this.info.resizeMode != 2 && this.info.resizeMode != 1) {
                if (f < 1.0f && f != 0.0f) {
                    return;
                }
                this.info.minAspectRatio = f;
                this.mHasMinAspectRatio = true;
                return;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void setPackageName(String string2) {
            super.setPackageName(string2);
            this.info.packageName = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("Activity{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(' ');
            this.appendComponentShortName(stringBuilder);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeParcelable(this.info, n | 2);
            parcel.writeBoolean(this.mHasMaxAspectRatio);
            parcel.writeBoolean(this.mHasMinAspectRatio);
        }

    }

    public static final class ActivityIntentInfo
    extends IntentInfo {
        @UnsupportedAppUsage
        public Activity activity;

        public ActivityIntentInfo(Activity activity) {
            this.activity = activity;
        }

        public ActivityIntentInfo(Parcel parcel) {
            super(parcel);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("ActivityIntentInfo{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(' ');
            this.activity.appendComponentShortName(stringBuilder);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    public static class ApkLite {
        public final String codePath;
        public final String configForSplit;
        public final boolean coreApp;
        public final boolean debuggable;
        public final boolean extractNativeLibs;
        public final int installLocation;
        public boolean isFeatureSplit;
        public final boolean isSplitRequired;
        public final boolean isolatedSplits;
        public final int minSdkVersion;
        public final boolean multiArch;
        public final String packageName;
        public final int revisionCode;
        public final SigningDetails signingDetails;
        public final String splitName;
        public final int targetSdkVersion;
        public final boolean use32bitAbi;
        public final boolean useEmbeddedDex;
        public final String usesSplitName;
        public final VerifierInfo[] verifiers;
        public final int versionCode;
        public final int versionCodeMajor;

        public ApkLite(String string2, String string3, String string4, boolean bl, String string5, String string6, boolean bl2, int n, int n2, int n3, int n4, List<VerifierInfo> list, SigningDetails signingDetails, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, boolean bl8, boolean bl9, int n5, int n6) {
            this.codePath = string2;
            this.packageName = string3;
            this.splitName = string4;
            this.isFeatureSplit = bl;
            this.configForSplit = string5;
            this.usesSplitName = string6;
            this.versionCode = n;
            this.versionCodeMajor = n2;
            this.revisionCode = n3;
            this.installLocation = n4;
            this.signingDetails = signingDetails;
            this.verifiers = list.toArray(new VerifierInfo[list.size()]);
            this.coreApp = bl3;
            this.debuggable = bl4;
            this.multiArch = bl5;
            this.use32bitAbi = bl6;
            this.useEmbeddedDex = bl7;
            this.extractNativeLibs = bl8;
            this.isolatedSplits = bl9;
            this.isSplitRequired = bl2;
            this.minSdkVersion = n5;
            this.targetSdkVersion = n6;
        }

        public long getLongVersionCode() {
            return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
        }
    }

    private static class CachedComponentArgs {
        ParseComponentArgs mActivityAliasArgs;
        ParseComponentArgs mActivityArgs;
        ParseComponentArgs mProviderArgs;
        ParseComponentArgs mServiceArgs;

        private CachedComponentArgs() {
        }
    }

    public static interface Callback {
        public String[] getOverlayApks(String var1);

        public String[] getOverlayPaths(String var1, String var2);

        public boolean hasFeature(String var1);
    }

    public static final class CallbackImpl
    implements Callback {
        private final PackageManager mPm;

        public CallbackImpl(PackageManager packageManager) {
            this.mPm = packageManager;
        }

        @Override
        public String[] getOverlayApks(String string2) {
            return null;
        }

        @Override
        public String[] getOverlayPaths(String string2, String string3) {
            return null;
        }

        @Override
        public boolean hasFeature(String string2) {
            return this.mPm.hasSystemFeature(string2);
        }
    }

    public static abstract class Component<II extends IntentInfo> {
        @UnsupportedAppUsage
        public final String className;
        ComponentName componentName;
        String componentShortName;
        @UnsupportedAppUsage
        public final ArrayList<II> intents;
        @UnsupportedAppUsage
        public Bundle metaData;
        public int order;
        @UnsupportedAppUsage
        public Package owner;

        public Component(Component<II> component) {
            this.owner = component.owner;
            this.intents = component.intents;
            this.className = component.className;
            this.componentName = component.componentName;
            this.componentShortName = component.componentShortName;
        }

        public Component(Package package_) {
            this.owner = package_;
            this.intents = null;
            this.className = null;
        }

        public Component(Package package_, ArrayList<II> arrayList, String string2) {
            this.owner = package_;
            this.intents = arrayList;
            this.className = string2;
        }

        public Component(ParseComponentArgs parseComponentArgs, ComponentInfo componentInfo) {
            this((ParsePackageItemArgs)parseComponentArgs, (PackageItemInfo)componentInfo);
            if (parseComponentArgs.outError[0] != null) {
                return;
            }
            if (parseComponentArgs.processRes != 0) {
                String string2 = this.owner.applicationInfo.targetSdkVersion >= 8 ? parseComponentArgs.sa.getNonConfigurationString(parseComponentArgs.processRes, 1024) : parseComponentArgs.sa.getNonResourceString(parseComponentArgs.processRes);
                componentInfo.processName = PackageParser.buildProcessName(this.owner.applicationInfo.packageName, this.owner.applicationInfo.processName, string2, parseComponentArgs.flags, parseComponentArgs.sepProcesses, parseComponentArgs.outError);
            }
            if (parseComponentArgs.descriptionRes != 0) {
                componentInfo.descriptionRes = parseComponentArgs.sa.getResourceId(parseComponentArgs.descriptionRes, 0);
            }
            componentInfo.enabled = parseComponentArgs.sa.getBoolean(parseComponentArgs.enabledRes, true);
        }

        public Component(ParsePackageItemArgs parsePackageItemArgs, PackageItemInfo packageItemInfo) {
            this.owner = parsePackageItemArgs.owner;
            this.intents = new ArrayList(0);
            this.className = PackageParser.parsePackageItemInfo(parsePackageItemArgs.owner, packageItemInfo, parsePackageItemArgs.outError, parsePackageItemArgs.tag, parsePackageItemArgs.sa, true, parsePackageItemArgs.nameRes, parsePackageItemArgs.labelRes, parsePackageItemArgs.iconRes, parsePackageItemArgs.roundIconRes, parsePackageItemArgs.logoRes, parsePackageItemArgs.bannerRes) ? packageItemInfo.name : null;
        }

        protected Component(Parcel parcel) {
            this.className = parcel.readString();
            this.metaData = parcel.readBundle();
            this.intents = Component.createIntentsList(parcel);
            this.owner = null;
        }

        private static <T extends IntentInfo> ArrayList<T> createIntentsList(Parcel parcel) {
            ArrayList<IntentInfo> arrayList;
            Constructor<?> constructor;
            int n = parcel.readInt();
            if (n == -1) {
                return null;
            }
            if (n == 0) {
                return new ArrayList(0);
            }
            String string2 = parcel.readString();
            try {
                constructor = Class.forName(string2).getConstructor(Parcel.class);
                arrayList = new ArrayList<IntentInfo>(n);
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to construct intent list for: ");
                stringBuilder.append(string2);
                throw new AssertionError((Object)stringBuilder.toString());
            }
            for (int i = 0; i < n; ++i) {
                arrayList.add((IntentInfo)constructor.newInstance(parcel));
                continue;
            }
            return arrayList;
        }

        private static void writeIntentsList(ArrayList<? extends IntentInfo> arrayList, Parcel parcel, int n) {
            if (arrayList == null) {
                parcel.writeInt(-1);
                return;
            }
            int n2 = arrayList.size();
            parcel.writeInt(n2);
            if (n2 > 0) {
                parcel.writeString(arrayList.get(0).getClass().getName());
                for (int i = 0; i < n2; ++i) {
                    arrayList.get(i).writeIntentInfoToParcel(parcel, n);
                }
            }
        }

        public void appendComponentShortName(StringBuilder stringBuilder) {
            ComponentName.appendShortString(stringBuilder, this.owner.applicationInfo.packageName, this.className);
        }

        @UnsupportedAppUsage
        public ComponentName getComponentName() {
            ComponentName componentName = this.componentName;
            if (componentName != null) {
                return componentName;
            }
            if (this.className != null) {
                this.componentName = new ComponentName(this.owner.applicationInfo.packageName, this.className);
            }
            return this.componentName;
        }

        public void printComponentShortName(PrintWriter printWriter) {
            ComponentName.printShortString(printWriter, this.owner.applicationInfo.packageName, this.className);
        }

        public void setPackageName(String string2) {
            this.componentName = null;
            this.componentShortName = null;
        }

        protected void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.className);
            parcel.writeBundle(this.metaData);
            Component.writeIntentsList(this.intents, parcel, n);
        }
    }

    public static final class Instrumentation
    extends Component<IntentInfo>
    implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Instrumentation>(){

            @Override
            public Instrumentation createFromParcel(Parcel parcel) {
                return new Instrumentation(parcel);
            }

            public Instrumentation[] newArray(int n) {
                return new Instrumentation[n];
            }
        };
        @UnsupportedAppUsage
        public final InstrumentationInfo info;

        public Instrumentation(ParsePackageItemArgs parsePackageItemArgs, InstrumentationInfo instrumentationInfo) {
            super(parsePackageItemArgs, instrumentationInfo);
            this.info = instrumentationInfo;
        }

        private Instrumentation(Parcel object) {
            super((Parcel)object);
            this.info = (InstrumentationInfo)((Parcel)object).readParcelable(Object.class.getClassLoader());
            if (this.info.targetPackage != null) {
                object = this.info;
                ((InstrumentationInfo)object).targetPackage = ((InstrumentationInfo)object).targetPackage.intern();
            }
            if (this.info.targetProcesses != null) {
                object = this.info;
                ((InstrumentationInfo)object).targetProcesses = ((InstrumentationInfo)object).targetProcesses.intern();
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void setPackageName(String string2) {
            super.setPackageName(string2);
            this.info.packageName = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("Instrumentation{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(' ');
            this.appendComponentShortName(stringBuilder);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeParcelable(this.info, n);
        }

    }

    public static abstract class IntentInfo
    extends IntentFilter {
        @UnsupportedAppUsage
        public int banner;
        @UnsupportedAppUsage
        public boolean hasDefault;
        @UnsupportedAppUsage
        public int icon;
        @UnsupportedAppUsage
        public int labelRes;
        @UnsupportedAppUsage
        public int logo;
        @UnsupportedAppUsage
        public CharSequence nonLocalizedLabel;
        public int preferred;

        @UnsupportedAppUsage
        protected IntentInfo() {
        }

        protected IntentInfo(Parcel parcel) {
            super(parcel);
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.hasDefault = bl;
            this.labelRes = parcel.readInt();
            this.nonLocalizedLabel = parcel.readCharSequence();
            this.icon = parcel.readInt();
            this.logo = parcel.readInt();
            this.banner = parcel.readInt();
            this.preferred = parcel.readInt();
        }

        public void writeIntentInfoToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt((int)this.hasDefault);
            parcel.writeInt(this.labelRes);
            parcel.writeCharSequence(this.nonLocalizedLabel);
            parcel.writeInt(this.icon);
            parcel.writeInt(this.logo);
            parcel.writeInt(this.banner);
            parcel.writeInt(this.preferred);
        }
    }

    public static class NewPermissionInfo {
        public final int fileVersion;
        @UnsupportedAppUsage
        public final String name;
        @UnsupportedAppUsage
        public final int sdkVersion;

        public NewPermissionInfo(String string2, int n, int n2) {
            this.name = string2;
            this.sdkVersion = n;
            this.fileVersion = n2;
        }
    }

    public static final class Package
    implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Package>(){

            @Override
            public Package createFromParcel(Parcel parcel) {
                return new Package(parcel);
            }

            public Package[] newArray(int n) {
                return new Package[n];
            }
        };
        @UnsupportedAppUsage
        public final ArrayList<Activity> activities;
        @UnsupportedAppUsage
        public ApplicationInfo applicationInfo = new ApplicationInfo();
        public String baseCodePath;
        public boolean baseHardwareAccelerated;
        public int baseRevisionCode;
        public ArrayList<Package> childPackages;
        public String codePath;
        @UnsupportedAppUsage
        public ArrayList<ConfigurationInfo> configPreferences;
        public boolean coreApp;
        public String cpuAbiOverride;
        public ArrayList<FeatureGroupInfo> featureGroups;
        public final ArrayList<String> implicitPermissions;
        @UnsupportedAppUsage
        public int installLocation;
        @UnsupportedAppUsage
        public final ArrayList<Instrumentation> instrumentation;
        public boolean isStub;
        public ArrayList<String> libraryNames;
        public ArrayList<String> mAdoptPermissions;
        @UnsupportedAppUsage
        public Bundle mAppMetaData;
        public int mCompileSdkVersion;
        public String mCompileSdkVersionCodename;
        @UnsupportedAppUsage
        public Object mExtras;
        @UnsupportedAppUsage
        public ArrayMap<String, ArraySet<PublicKey>> mKeySetMapping;
        public long[] mLastPackageUsageTimeInMills;
        public ArrayList<String> mOriginalPackages;
        public String mOverlayCategory;
        public boolean mOverlayIsStatic;
        public int mOverlayPriority;
        public String mOverlayTarget;
        public String mOverlayTargetName;
        @UnsupportedAppUsage
        public int mPreferredOrder;
        public String mRealPackage;
        public String mRequiredAccountType;
        public boolean mRequiredForAllUsers;
        public String mRestrictedAccountType;
        @UnsupportedAppUsage
        public String mSharedUserId;
        @UnsupportedAppUsage
        public int mSharedUserLabel;
        @UnsupportedAppUsage
        public SigningDetails mSigningDetails;
        @UnsupportedAppUsage
        public ArraySet<String> mUpgradeKeySets;
        @UnsupportedAppUsage
        public int mVersionCode;
        public int mVersionCodeMajor;
        @UnsupportedAppUsage
        public String mVersionName;
        public String manifestPackageName;
        @UnsupportedAppUsage
        public String packageName;
        public Package parentPackage;
        @UnsupportedAppUsage
        public final ArrayList<PermissionGroup> permissionGroups;
        @UnsupportedAppUsage
        public final ArrayList<Permission> permissions;
        public ArrayList<ActivityIntentInfo> preferredActivityFilters;
        @UnsupportedAppUsage
        public ArrayList<String> protectedBroadcasts;
        @UnsupportedAppUsage
        public final ArrayList<Provider> providers;
        @UnsupportedAppUsage
        public final ArrayList<Activity> receivers;
        @UnsupportedAppUsage
        public ArrayList<FeatureInfo> reqFeatures;
        @UnsupportedAppUsage
        public final ArrayList<String> requestedPermissions;
        public byte[] restrictUpdateHash;
        @UnsupportedAppUsage
        public final ArrayList<Service> services;
        public String[] splitCodePaths;
        public int[] splitFlags;
        public String[] splitNames;
        public int[] splitPrivateFlags;
        public int[] splitRevisionCodes;
        public String staticSharedLibName;
        public long staticSharedLibVersion;
        public boolean use32bitAbi;
        @UnsupportedAppUsage
        public ArrayList<String> usesLibraries;
        @UnsupportedAppUsage
        public String[] usesLibraryFiles;
        public ArrayList<SharedLibraryInfo> usesLibraryInfos;
        @UnsupportedAppUsage
        public ArrayList<String> usesOptionalLibraries;
        public ArrayList<String> usesStaticLibraries;
        public String[][] usesStaticLibrariesCertDigests;
        public long[] usesStaticLibrariesVersions;
        public boolean visibleToInstantApps;
        public String volumeUuid;

        public Package(Parcel parcel) {
            Object object;
            boolean bl = false;
            this.permissions = new ArrayList(0);
            this.permissionGroups = new ArrayList(0);
            this.activities = new ArrayList(0);
            this.receivers = new ArrayList(0);
            this.providers = new ArrayList(0);
            this.services = new ArrayList(0);
            this.instrumentation = new ArrayList(0);
            this.requestedPermissions = new ArrayList();
            this.implicitPermissions = new ArrayList();
            this.staticSharedLibName = null;
            this.staticSharedLibVersion = 0L;
            this.libraryNames = null;
            this.usesLibraries = null;
            this.usesStaticLibraries = null;
            this.usesStaticLibrariesVersions = null;
            this.usesStaticLibrariesCertDigests = null;
            this.usesOptionalLibraries = null;
            this.usesLibraryFiles = null;
            this.usesLibraryInfos = null;
            this.preferredActivityFilters = null;
            this.mOriginalPackages = null;
            this.mRealPackage = null;
            this.mAdoptPermissions = null;
            this.mAppMetaData = null;
            this.mSigningDetails = SigningDetails.UNKNOWN;
            this.mPreferredOrder = 0;
            this.mLastPackageUsageTimeInMills = new long[8];
            this.configPreferences = null;
            this.reqFeatures = null;
            this.featureGroups = null;
            ClassLoader classLoader = Object.class.getClassLoader();
            this.packageName = parcel.readString().intern();
            this.manifestPackageName = parcel.readString();
            this.splitNames = parcel.readStringArray();
            this.volumeUuid = parcel.readString();
            this.codePath = parcel.readString();
            this.baseCodePath = parcel.readString();
            this.splitCodePaths = parcel.readStringArray();
            this.baseRevisionCode = parcel.readInt();
            this.splitRevisionCodes = parcel.createIntArray();
            this.splitFlags = parcel.createIntArray();
            this.splitPrivateFlags = parcel.createIntArray();
            boolean bl2 = parcel.readInt() == 1;
            this.baseHardwareAccelerated = bl2;
            this.applicationInfo = (ApplicationInfo)parcel.readParcelable(classLoader);
            if (this.applicationInfo.permission != null) {
                object = this.applicationInfo;
                ((ApplicationInfo)object).permission = ((ApplicationInfo)object).permission.intern();
            }
            parcel.readParcelableList(this.permissions, classLoader);
            this.fixupOwner(this.permissions);
            parcel.readParcelableList(this.permissionGroups, classLoader);
            this.fixupOwner(this.permissionGroups);
            parcel.readParcelableList(this.activities, classLoader);
            this.fixupOwner(this.activities);
            parcel.readParcelableList(this.receivers, classLoader);
            this.fixupOwner(this.receivers);
            parcel.readParcelableList(this.providers, classLoader);
            this.fixupOwner(this.providers);
            parcel.readParcelableList(this.services, classLoader);
            this.fixupOwner(this.services);
            parcel.readParcelableList(this.instrumentation, classLoader);
            this.fixupOwner(this.instrumentation);
            parcel.readStringList(this.requestedPermissions);
            Package.internStringArrayList(this.requestedPermissions);
            parcel.readStringList(this.implicitPermissions);
            Package.internStringArrayList(this.implicitPermissions);
            this.protectedBroadcasts = parcel.createStringArrayList();
            Package.internStringArrayList(this.protectedBroadcasts);
            this.parentPackage = (Package)parcel.readParcelable(classLoader);
            this.childPackages = new ArrayList();
            parcel.readParcelableList(this.childPackages, classLoader);
            if (this.childPackages.size() == 0) {
                this.childPackages = null;
            }
            this.staticSharedLibName = parcel.readString();
            object = this.staticSharedLibName;
            if (object != null) {
                this.staticSharedLibName = ((String)object).intern();
            }
            this.staticSharedLibVersion = parcel.readLong();
            this.libraryNames = parcel.createStringArrayList();
            Package.internStringArrayList(this.libraryNames);
            this.usesLibraries = parcel.createStringArrayList();
            Package.internStringArrayList(this.usesLibraries);
            this.usesOptionalLibraries = parcel.createStringArrayList();
            Package.internStringArrayList(this.usesOptionalLibraries);
            this.usesLibraryFiles = parcel.readStringArray();
            this.usesLibraryInfos = parcel.createTypedArrayList(SharedLibraryInfo.CREATOR);
            int n = parcel.readInt();
            if (n > 0) {
                this.usesStaticLibraries = new ArrayList(n);
                parcel.readStringList(this.usesStaticLibraries);
                Package.internStringArrayList(this.usesStaticLibraries);
                this.usesStaticLibrariesVersions = new long[n];
                parcel.readLongArray(this.usesStaticLibrariesVersions);
                this.usesStaticLibrariesCertDigests = new String[n][];
                for (int i = 0; i < n; ++i) {
                    this.usesStaticLibrariesCertDigests[i] = parcel.createStringArray();
                }
            }
            this.preferredActivityFilters = new ArrayList();
            parcel.readParcelableList(this.preferredActivityFilters, classLoader);
            if (this.preferredActivityFilters.size() == 0) {
                this.preferredActivityFilters = null;
            }
            this.mOriginalPackages = parcel.createStringArrayList();
            this.mRealPackage = parcel.readString();
            this.mAdoptPermissions = parcel.createStringArrayList();
            this.mAppMetaData = parcel.readBundle();
            this.mVersionCode = parcel.readInt();
            this.mVersionCodeMajor = parcel.readInt();
            this.mVersionName = parcel.readString();
            object = this.mVersionName;
            if (object != null) {
                this.mVersionName = ((String)object).intern();
            }
            this.mSharedUserId = parcel.readString();
            object = this.mSharedUserId;
            if (object != null) {
                this.mSharedUserId = ((String)object).intern();
            }
            this.mSharedUserLabel = parcel.readInt();
            this.mSigningDetails = (SigningDetails)parcel.readParcelable(classLoader);
            this.mPreferredOrder = parcel.readInt();
            this.configPreferences = new ArrayList();
            parcel.readParcelableList(this.configPreferences, classLoader);
            if (this.configPreferences.size() == 0) {
                this.configPreferences = null;
            }
            this.reqFeatures = new ArrayList();
            parcel.readParcelableList(this.reqFeatures, classLoader);
            if (this.reqFeatures.size() == 0) {
                this.reqFeatures = null;
            }
            this.featureGroups = new ArrayList();
            parcel.readParcelableList(this.featureGroups, classLoader);
            if (this.featureGroups.size() == 0) {
                this.featureGroups = null;
            }
            this.installLocation = parcel.readInt();
            bl2 = parcel.readInt() == 1;
            this.coreApp = bl2;
            bl2 = parcel.readInt() == 1;
            this.mRequiredForAllUsers = bl2;
            this.mRestrictedAccountType = parcel.readString();
            this.mRequiredAccountType = parcel.readString();
            this.mOverlayTarget = parcel.readString();
            this.mOverlayTargetName = parcel.readString();
            this.mOverlayCategory = parcel.readString();
            this.mOverlayPriority = parcel.readInt();
            bl2 = parcel.readInt() == 1;
            this.mOverlayIsStatic = bl2;
            this.mCompileSdkVersion = parcel.readInt();
            this.mCompileSdkVersionCodename = parcel.readString();
            this.mUpgradeKeySets = parcel.readArraySet(classLoader);
            this.mKeySetMapping = Package.readKeySetMapping(parcel);
            this.cpuAbiOverride = parcel.readString();
            bl2 = parcel.readInt() == 1;
            this.use32bitAbi = bl2;
            this.restrictUpdateHash = parcel.createByteArray();
            bl2 = bl;
            if (parcel.readInt() == 1) {
                bl2 = true;
            }
            this.visibleToInstantApps = bl2;
        }

        @UnsupportedAppUsage
        public Package(String string2) {
            this.permissions = new ArrayList(0);
            this.permissionGroups = new ArrayList(0);
            this.activities = new ArrayList(0);
            this.receivers = new ArrayList(0);
            this.providers = new ArrayList(0);
            this.services = new ArrayList(0);
            this.instrumentation = new ArrayList(0);
            this.requestedPermissions = new ArrayList();
            this.implicitPermissions = new ArrayList();
            this.staticSharedLibName = null;
            this.staticSharedLibVersion = 0L;
            this.libraryNames = null;
            this.usesLibraries = null;
            this.usesStaticLibraries = null;
            this.usesStaticLibrariesVersions = null;
            this.usesStaticLibrariesCertDigests = null;
            this.usesOptionalLibraries = null;
            this.usesLibraryFiles = null;
            this.usesLibraryInfos = null;
            this.preferredActivityFilters = null;
            this.mOriginalPackages = null;
            this.mRealPackage = null;
            this.mAdoptPermissions = null;
            this.mAppMetaData = null;
            this.mSigningDetails = SigningDetails.UNKNOWN;
            this.mPreferredOrder = 0;
            this.mLastPackageUsageTimeInMills = new long[8];
            this.configPreferences = null;
            this.reqFeatures = null;
            this.featureGroups = null;
            this.packageName = string2;
            this.manifestPackageName = string2;
            ApplicationInfo applicationInfo = this.applicationInfo;
            applicationInfo.packageName = string2;
            applicationInfo.uid = -1;
        }

        private void fixupOwner(List<? extends Component<?>> object) {
            if (object != null) {
                Iterator<Component<?>> iterator = object.iterator();
                while (iterator.hasNext()) {
                    object = iterator.next();
                    ((Component)object).owner = this;
                    if (object instanceof Activity) {
                        ((Activity)object).info.applicationInfo = this.applicationInfo;
                        continue;
                    }
                    if (object instanceof Service) {
                        ((Service)object).info.applicationInfo = this.applicationInfo;
                        continue;
                    }
                    if (!(object instanceof Provider)) continue;
                    ((Provider)object).info.applicationInfo = this.applicationInfo;
                }
            }
        }

        private static void internStringArrayList(List<String> list) {
            if (list != null) {
                int n = list.size();
                for (int i = 0; i < n; ++i) {
                    list.set(i, list.get(i).intern());
                }
            }
        }

        private static ArrayMap<String, ArraySet<PublicKey>> readKeySetMapping(Parcel parcel) {
            int n = parcel.readInt();
            if (n == -1) {
                return null;
            }
            ArrayMap<String, ArraySet<PublicKey>> arrayMap = new ArrayMap<String, ArraySet<PublicKey>>();
            for (int i = 0; i < n; ++i) {
                String string2 = parcel.readString();
                int n2 = parcel.readInt();
                if (n2 == -1) {
                    arrayMap.put(string2, null);
                    continue;
                }
                ArraySet<PublicKey> arraySet = new ArraySet<PublicKey>(n2);
                for (int j = 0; j < n2; ++j) {
                    arraySet.add((PublicKey)parcel.readSerializable());
                }
                arrayMap.put(string2, arraySet);
            }
            return arrayMap;
        }

        private static void writeKeySetMapping(Parcel parcel, ArrayMap<String, ArraySet<PublicKey>> arrayMap) {
            if (arrayMap == null) {
                parcel.writeInt(-1);
                return;
            }
            int n = arrayMap.size();
            parcel.writeInt(n);
            for (int i = 0; i < n; ++i) {
                parcel.writeString(arrayMap.keyAt(i));
                ArraySet<PublicKey> arraySet = arrayMap.valueAt(i);
                if (arraySet == null) {
                    parcel.writeInt(-1);
                    continue;
                }
                int n2 = arraySet.size();
                parcel.writeInt(n2);
                for (int j = 0; j < n2; ++j) {
                    parcel.writeSerializable(arraySet.valueAt(j));
                }
            }
        }

        public boolean canHaveOatDir() {
            boolean bl = !this.isSystem() || this.isUpdatedSystemApp();
            return bl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public List<String> getAllCodePaths() {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(this.baseCodePath);
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                Collections.addAll(arrayList, this.splitCodePaths);
            }
            return arrayList;
        }

        public List<String> getAllCodePathsExcludingResourceOnly() {
            ArrayList<String> arrayList = new ArrayList<String>();
            if ((this.applicationInfo.flags & 4) != 0) {
                arrayList.add(this.baseCodePath);
            }
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                String[] arrstring;
                for (int i = 0; i < (arrstring = this.splitCodePaths).length; ++i) {
                    if ((this.splitFlags[i] & 4) == 0) continue;
                    arrayList.add(arrstring[i]);
                }
            }
            return arrayList;
        }

        public List<String> getChildPackageNames() {
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList == null) {
                return null;
            }
            int n = arrayList.size();
            arrayList = new ArrayList(n);
            for (int i = 0; i < n; ++i) {
                arrayList.add((Package)((Object)this.childPackages.get((int)i).packageName));
            }
            return arrayList;
        }

        public long getLatestForegroundPackageUseTimeInMills() {
            int[] arrn;
            int[] arrn2 = arrn = new int[2];
            arrn2[0] = 0;
            arrn2[1] = 2;
            long l = 0L;
            for (int n : arrn) {
                l = Math.max(l, this.mLastPackageUsageTimeInMills[n]);
            }
            return l;
        }

        public long getLatestPackageUseTimeInMills() {
            long l = 0L;
            long[] arrl = this.mLastPackageUsageTimeInMills;
            int n = arrl.length;
            for (int i = 0; i < n; ++i) {
                l = Math.max(l, arrl[i]);
            }
            return l;
        }

        public long getLongVersionCode() {
            return PackageInfo.composeLongVersionCode(this.mVersionCodeMajor, this.mVersionCode);
        }

        public boolean hasChildPackage(String string2) {
            ArrayList<Package> arrayList = this.childPackages;
            int n = arrayList != null ? arrayList.size() : 0;
            for (int i = 0; i < n; ++i) {
                if (!this.childPackages.get((int)i).packageName.equals(string2)) continue;
                return true;
            }
            return false;
        }

        public boolean hasComponentClassName(String string2) {
            int n;
            for (n = this.activities.size() - 1; n >= 0; --n) {
                if (!string2.equals(this.activities.get((int)n).className)) continue;
                return true;
            }
            for (n = this.receivers.size() - 1; n >= 0; --n) {
                if (!string2.equals(this.receivers.get((int)n).className)) continue;
                return true;
            }
            for (n = this.providers.size() - 1; n >= 0; --n) {
                if (!string2.equals(this.providers.get((int)n).className)) continue;
                return true;
            }
            for (n = this.services.size() - 1; n >= 0; --n) {
                if (!string2.equals(this.services.get((int)n).className)) continue;
                return true;
            }
            for (n = this.instrumentation.size() - 1; n >= 0; --n) {
                if (!string2.equals(this.instrumentation.get((int)n).className)) continue;
                return true;
            }
            return false;
        }

        public boolean isExternal() {
            return this.applicationInfo.isExternal();
        }

        public boolean isForwardLocked() {
            return false;
        }

        public boolean isLibrary() {
            boolean bl = this.staticSharedLibName != null || !ArrayUtils.isEmpty(this.libraryNames);
            return bl;
        }

        public boolean isMatch(int n) {
            if ((1048576 & n) != 0) {
                return this.isSystem();
            }
            return true;
        }

        public boolean isOdm() {
            return this.applicationInfo.isOdm();
        }

        public boolean isOem() {
            return this.applicationInfo.isOem();
        }

        public boolean isPrivileged() {
            return this.applicationInfo.isPrivilegedApp();
        }

        public boolean isProduct() {
            return this.applicationInfo.isProduct();
        }

        public boolean isProductServices() {
            return this.applicationInfo.isProductServices();
        }

        public boolean isSystem() {
            return this.applicationInfo.isSystemApp();
        }

        public boolean isUpdatedSystemApp() {
            return this.applicationInfo.isUpdatedSystemApp();
        }

        public boolean isVendor() {
            return this.applicationInfo.isVendor();
        }

        public void setApplicationInfoBaseCodePath(String string2) {
            this.applicationInfo.setBaseCodePath(string2);
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.childPackages.get((int)i).applicationInfo.setBaseCodePath(string2);
                }
            }
        }

        @Deprecated
        public void setApplicationInfoBaseResourcePath(String string2) {
            this.applicationInfo.setBaseResourcePath(string2);
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.childPackages.get((int)i).applicationInfo.setBaseResourcePath(string2);
                }
            }
        }

        public void setApplicationInfoCodePath(String string2) {
            this.applicationInfo.setCodePath(string2);
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.childPackages.get((int)i).applicationInfo.setCodePath(string2);
                }
            }
        }

        public void setApplicationInfoFlags(int n, int n2) {
            ArrayList<Package> arrayList = this.applicationInfo;
            ((ApplicationInfo)arrayList).flags = ((ApplicationInfo)arrayList).flags & n | n & n2;
            arrayList = this.childPackages;
            if (arrayList != null) {
                int n3 = arrayList.size();
                for (int i = 0; i < n3; ++i) {
                    this.childPackages.get((int)i).applicationInfo.flags = this.applicationInfo.flags & n | n & n2;
                }
            }
        }

        @Deprecated
        public void setApplicationInfoResourcePath(String string2) {
            this.applicationInfo.setResourcePath(string2);
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.childPackages.get((int)i).applicationInfo.setResourcePath(string2);
                }
            }
        }

        public void setApplicationInfoSplitCodePaths(String[] arrstring) {
            this.applicationInfo.setSplitCodePaths(arrstring);
        }

        @Deprecated
        public void setApplicationInfoSplitResourcePaths(String[] arrstring) {
            this.applicationInfo.setSplitResourcePaths(arrstring);
        }

        public void setApplicationVolumeUuid(String string2) {
            UUID uUID = StorageManager.convert(string2);
            ArrayList<Package> arrayList = this.applicationInfo;
            ((ApplicationInfo)arrayList).volumeUuid = string2;
            ((ApplicationInfo)arrayList).storageUuid = uUID;
            arrayList = this.childPackages;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.childPackages.get((int)i).applicationInfo.volumeUuid = string2;
                    this.childPackages.get((int)i).applicationInfo.storageUuid = uUID;
                }
            }
        }

        public void setBaseCodePath(String string2) {
            this.baseCodePath = string2;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.childPackages.get((int)i).baseCodePath = string2;
                }
            }
        }

        public void setCodePath(String string2) {
            this.codePath = string2;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.childPackages.get((int)i).codePath = string2;
                }
            }
        }

        @UnsupportedAppUsage
        public void setPackageName(String string2) {
            int n;
            this.packageName = string2;
            this.applicationInfo.packageName = string2;
            for (n = this.permissions.size() - 1; n >= 0; --n) {
                this.permissions.get(n).setPackageName(string2);
            }
            for (n = this.permissionGroups.size() - 1; n >= 0; --n) {
                this.permissionGroups.get(n).setPackageName(string2);
            }
            for (n = this.activities.size() - 1; n >= 0; --n) {
                this.activities.get(n).setPackageName(string2);
            }
            for (n = this.receivers.size() - 1; n >= 0; --n) {
                this.receivers.get(n).setPackageName(string2);
            }
            for (n = this.providers.size() - 1; n >= 0; --n) {
                this.providers.get(n).setPackageName(string2);
            }
            for (n = this.services.size() - 1; n >= 0; --n) {
                this.services.get(n).setPackageName(string2);
            }
            for (n = this.instrumentation.size() - 1; n >= 0; --n) {
                this.instrumentation.get(n).setPackageName(string2);
            }
        }

        public void setSigningDetails(SigningDetails signingDetails) {
            this.mSigningDetails = signingDetails;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.childPackages.get((int)i).mSigningDetails = signingDetails;
                }
            }
        }

        public void setSplitCodePaths(String[] arrstring) {
            this.splitCodePaths = arrstring;
        }

        public void setUse32bitAbi(boolean bl) {
            this.use32bitAbi = bl;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.childPackages.get((int)i).use32bitAbi = bl;
                }
            }
        }

        public void setVolumeUuid(String string2) {
            this.volumeUuid = string2;
            ArrayList<Package> arrayList = this.childPackages;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.childPackages.get((int)i).volumeUuid = string2;
                }
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Package{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" ");
            stringBuilder.append(this.packageName);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.packageName);
            parcel.writeString(this.manifestPackageName);
            parcel.writeStringArray(this.splitNames);
            parcel.writeString(this.volumeUuid);
            parcel.writeString(this.codePath);
            parcel.writeString(this.baseCodePath);
            parcel.writeStringArray(this.splitCodePaths);
            parcel.writeInt(this.baseRevisionCode);
            parcel.writeIntArray(this.splitRevisionCodes);
            parcel.writeIntArray(this.splitFlags);
            parcel.writeIntArray(this.splitPrivateFlags);
            parcel.writeInt((int)this.baseHardwareAccelerated);
            parcel.writeParcelable(this.applicationInfo, n);
            parcel.writeParcelableList(this.permissions, n);
            parcel.writeParcelableList(this.permissionGroups, n);
            parcel.writeParcelableList(this.activities, n);
            parcel.writeParcelableList(this.receivers, n);
            parcel.writeParcelableList(this.providers, n);
            parcel.writeParcelableList(this.services, n);
            parcel.writeParcelableList(this.instrumentation, n);
            parcel.writeStringList(this.requestedPermissions);
            parcel.writeStringList(this.implicitPermissions);
            parcel.writeStringList(this.protectedBroadcasts);
            parcel.writeParcelable(this.parentPackage, n);
            parcel.writeParcelableList(this.childPackages, n);
            parcel.writeString(this.staticSharedLibName);
            parcel.writeLong(this.staticSharedLibVersion);
            parcel.writeStringList(this.libraryNames);
            parcel.writeStringList(this.usesLibraries);
            parcel.writeStringList(this.usesOptionalLibraries);
            parcel.writeStringArray(this.usesLibraryFiles);
            parcel.writeTypedList(this.usesLibraryInfos);
            if (ArrayUtils.isEmpty(this.usesStaticLibraries)) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(this.usesStaticLibraries.size());
                parcel.writeStringList(this.usesStaticLibraries);
                parcel.writeLongArray(this.usesStaticLibrariesVersions);
                String[][] arrstring = this.usesStaticLibrariesCertDigests;
                int n2 = arrstring.length;
                for (int i = 0; i < n2; ++i) {
                    parcel.writeStringArray(arrstring[i]);
                }
            }
            parcel.writeParcelableList(this.preferredActivityFilters, n);
            parcel.writeStringList(this.mOriginalPackages);
            parcel.writeString(this.mRealPackage);
            parcel.writeStringList(this.mAdoptPermissions);
            parcel.writeBundle(this.mAppMetaData);
            parcel.writeInt(this.mVersionCode);
            parcel.writeInt(this.mVersionCodeMajor);
            parcel.writeString(this.mVersionName);
            parcel.writeString(this.mSharedUserId);
            parcel.writeInt(this.mSharedUserLabel);
            parcel.writeParcelable(this.mSigningDetails, n);
            parcel.writeInt(this.mPreferredOrder);
            parcel.writeParcelableList(this.configPreferences, n);
            parcel.writeParcelableList(this.reqFeatures, n);
            parcel.writeParcelableList(this.featureGroups, n);
            parcel.writeInt(this.installLocation);
            parcel.writeInt((int)this.coreApp);
            parcel.writeInt((int)this.mRequiredForAllUsers);
            parcel.writeString(this.mRestrictedAccountType);
            parcel.writeString(this.mRequiredAccountType);
            parcel.writeString(this.mOverlayTarget);
            parcel.writeString(this.mOverlayTargetName);
            parcel.writeString(this.mOverlayCategory);
            parcel.writeInt(this.mOverlayPriority);
            parcel.writeInt((int)this.mOverlayIsStatic);
            parcel.writeInt(this.mCompileSdkVersion);
            parcel.writeString(this.mCompileSdkVersionCodename);
            parcel.writeArraySet(this.mUpgradeKeySets);
            Package.writeKeySetMapping(parcel, this.mKeySetMapping);
            parcel.writeString(this.cpuAbiOverride);
            parcel.writeInt((int)this.use32bitAbi);
            parcel.writeByteArray(this.restrictUpdateHash);
            parcel.writeInt((int)this.visibleToInstantApps);
        }

    }

    public static class PackageLite {
        public final String baseCodePath;
        public final int baseRevisionCode;
        public final String codePath;
        public final String[] configForSplit;
        public final boolean coreApp;
        public final boolean debuggable;
        public final boolean extractNativeLibs;
        @UnsupportedAppUsage
        public final int installLocation;
        public final boolean[] isFeatureSplits;
        public final boolean isolatedSplits;
        public final boolean multiArch;
        @UnsupportedAppUsage
        public final String packageName;
        public final String[] splitCodePaths;
        public final String[] splitNames;
        public final int[] splitRevisionCodes;
        public final boolean use32bitAbi;
        public final String[] usesSplitNames;
        public final VerifierInfo[] verifiers;
        public final int versionCode;
        public final int versionCodeMajor;

        public PackageLite(String string2, ApkLite apkLite, String[] arrstring, boolean[] arrbl, String[] arrstring2, String[] arrstring3, String[] arrstring4, int[] arrn) {
            this.packageName = apkLite.packageName;
            this.versionCode = apkLite.versionCode;
            this.versionCodeMajor = apkLite.versionCodeMajor;
            this.installLocation = apkLite.installLocation;
            this.verifiers = apkLite.verifiers;
            this.splitNames = arrstring;
            this.isFeatureSplits = arrbl;
            this.usesSplitNames = arrstring2;
            this.configForSplit = arrstring3;
            this.codePath = string2;
            this.baseCodePath = apkLite.codePath;
            this.splitCodePaths = arrstring4;
            this.baseRevisionCode = apkLite.revisionCode;
            this.splitRevisionCodes = arrn;
            this.coreApp = apkLite.coreApp;
            this.debuggable = apkLite.debuggable;
            this.multiArch = apkLite.multiArch;
            this.use32bitAbi = apkLite.use32bitAbi;
            this.extractNativeLibs = apkLite.extractNativeLibs;
            this.isolatedSplits = apkLite.isolatedSplits;
        }

        public List<String> getAllCodePaths() {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(this.baseCodePath);
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                Collections.addAll(arrayList, this.splitCodePaths);
            }
            return arrayList;
        }
    }

    public static class PackageParserException
    extends Exception {
        public final int error;

        public PackageParserException(int n, String string2) {
            super(string2);
            this.error = n;
        }

        public PackageParserException(int n, String string2, Throwable throwable) {
            super(string2, throwable);
            this.error = n;
        }
    }

    @VisibleForTesting
    public static class ParseComponentArgs
    extends ParsePackageItemArgs {
        final int descriptionRes;
        final int enabledRes;
        int flags;
        final int processRes;
        final String[] sepProcesses;

        public ParseComponentArgs(Package package_, String[] arrstring, int n, int n2, int n3, int n4, int n5, int n6, String[] arrstring2, int n7, int n8, int n9) {
            super(package_, arrstring, n, n2, n3, n4, n5, n6);
            this.sepProcesses = arrstring2;
            this.processRes = n7;
            this.descriptionRes = n8;
            this.enabledRes = n9;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ParseFlags {
    }

    static class ParsePackageItemArgs {
        final int bannerRes;
        final int iconRes;
        final int labelRes;
        final int logoRes;
        final int nameRes;
        final String[] outError;
        final Package owner;
        final int roundIconRes;
        TypedArray sa;
        String tag;

        ParsePackageItemArgs(Package package_, String[] arrstring, int n, int n2, int n3, int n4, int n5, int n6) {
            this.owner = package_;
            this.outError = arrstring;
            this.nameRes = n;
            this.labelRes = n2;
            this.iconRes = n3;
            this.logoRes = n5;
            this.bannerRes = n6;
            this.roundIconRes = n4;
        }
    }

    public static final class Permission
    extends Component<IntentInfo>
    implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Permission>(){

            @Override
            public Permission createFromParcel(Parcel parcel) {
                return new Permission(parcel);
            }

            public Permission[] newArray(int n) {
                return new Permission[n];
            }
        };
        @UnsupportedAppUsage
        public PermissionGroup group;
        @UnsupportedAppUsage
        public final PermissionInfo info;
        @UnsupportedAppUsage
        public boolean tree;

        @UnsupportedAppUsage
        public Permission(Package package_, PermissionInfo permissionInfo) {
            super(package_);
            this.info = permissionInfo;
        }

        public Permission(Package package_, String string2) {
            super(package_);
            this.info = new PermissionInfo(string2);
        }

        private Permission(Parcel parcel) {
            super(parcel);
            ClassLoader classLoader = Object.class.getClassLoader();
            this.info = (PermissionInfo)parcel.readParcelable(classLoader);
            if (this.info.group != null) {
                PermissionInfo permissionInfo = this.info;
                permissionInfo.group = permissionInfo.group.intern();
            }
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.tree = bl;
            this.group = (PermissionGroup)parcel.readParcelable(classLoader);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean isAppOp() {
            return this.info.isAppOp();
        }

        @Override
        public void setPackageName(String string2) {
            super.setPackageName(string2);
            this.info.packageName = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Permission{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" ");
            stringBuilder.append(this.info.name);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeParcelable(this.info, n);
            parcel.writeInt((int)this.tree);
            parcel.writeParcelable(this.group, n);
        }

    }

    public static final class PermissionGroup
    extends Component<IntentInfo>
    implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<PermissionGroup>(){

            @Override
            public PermissionGroup createFromParcel(Parcel parcel) {
                return new PermissionGroup(parcel);
            }

            public PermissionGroup[] newArray(int n) {
                return new PermissionGroup[n];
            }
        };
        @UnsupportedAppUsage
        public final PermissionGroupInfo info;

        public PermissionGroup(Package package_, int n, int n2, int n3) {
            super(package_);
            this.info = new PermissionGroupInfo(n, n2, n3);
        }

        public PermissionGroup(Package package_, PermissionGroupInfo permissionGroupInfo) {
            super(package_);
            this.info = permissionGroupInfo;
        }

        private PermissionGroup(Parcel parcel) {
            super(parcel);
            this.info = (PermissionGroupInfo)parcel.readParcelable(Object.class.getClassLoader());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void setPackageName(String string2) {
            super.setPackageName(string2);
            this.info.packageName = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PermissionGroup{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" ");
            stringBuilder.append(this.info.name);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeParcelable(this.info, n);
        }

    }

    public static final class Provider
    extends Component<ProviderIntentInfo>
    implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Provider>(){

            @Override
            public Provider createFromParcel(Parcel parcel) {
                return new Provider(parcel);
            }

            public Provider[] newArray(int n) {
                return new Provider[n];
            }
        };
        @UnsupportedAppUsage
        public final ProviderInfo info;
        @UnsupportedAppUsage
        public boolean syncable;

        public Provider(ParseComponentArgs parseComponentArgs, ProviderInfo providerInfo) {
            super(parseComponentArgs, providerInfo);
            this.info = providerInfo;
            this.info.applicationInfo = parseComponentArgs.owner.applicationInfo;
            this.syncable = false;
        }

        @UnsupportedAppUsage
        public Provider(Provider provider) {
            super(provider);
            this.info = provider.info;
            this.syncable = provider.syncable;
        }

        private Provider(Parcel object) {
            super((Parcel)object);
            this.info = (ProviderInfo)((Parcel)object).readParcelable(Object.class.getClassLoader());
            int n = ((Parcel)object).readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.syncable = bl;
            object = this.intents.iterator();
            while (object.hasNext()) {
                ((ProviderIntentInfo)object.next()).provider = this;
            }
            if (this.info.readPermission != null) {
                object = this.info;
                ((ProviderInfo)object).readPermission = ((ProviderInfo)object).readPermission.intern();
            }
            if (this.info.writePermission != null) {
                object = this.info;
                ((ProviderInfo)object).writePermission = ((ProviderInfo)object).writePermission.intern();
            }
            if (this.info.authority != null) {
                object = this.info;
                ((ProviderInfo)object).authority = ((ProviderInfo)object).authority.intern();
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void setPackageName(String string2) {
            super.setPackageName(string2);
            this.info.packageName = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("Provider{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(' ');
            this.appendComponentShortName(stringBuilder);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeParcelable(this.info, n | 2);
            parcel.writeInt((int)this.syncable);
        }

    }

    public static final class ProviderIntentInfo
    extends IntentInfo {
        @UnsupportedAppUsage
        public Provider provider;

        public ProviderIntentInfo(Provider provider) {
            this.provider = provider;
        }

        public ProviderIntentInfo(Parcel parcel) {
            super(parcel);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("ProviderIntentInfo{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(' ');
            this.provider.appendComponentShortName(stringBuilder);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    public static final class Service
    extends Component<ServiceIntentInfo>
    implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Service>(){

            @Override
            public Service createFromParcel(Parcel parcel) {
                return new Service(parcel);
            }

            public Service[] newArray(int n) {
                return new Service[n];
            }
        };
        @UnsupportedAppUsage
        public final ServiceInfo info;

        public Service(ParseComponentArgs parseComponentArgs, ServiceInfo serviceInfo) {
            super(parseComponentArgs, serviceInfo);
            this.info = serviceInfo;
            this.info.applicationInfo = parseComponentArgs.owner.applicationInfo;
        }

        private Service(Parcel object2) {
            super((Parcel)object2);
            this.info = (ServiceInfo)((Parcel)object2).readParcelable(Object.class.getClassLoader());
            for (Object object2 : this.intents) {
                ((ServiceIntentInfo)object2).service = this;
                this.order = Math.max(((IntentFilter)object2).getOrder(), this.order);
            }
            if (this.info.permission != null) {
                object2 = this.info;
                ((ServiceInfo)object2).permission = ((ServiceInfo)object2).permission.intern();
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void setPackageName(String string2) {
            super.setPackageName(string2);
            this.info.packageName = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("Service{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(' ');
            this.appendComponentShortName(stringBuilder);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeParcelable(this.info, n | 2);
        }

    }

    public static final class ServiceIntentInfo
    extends IntentInfo {
        @UnsupportedAppUsage
        public Service service;

        public ServiceIntentInfo(Service service) {
            this.service = service;
        }

        public ServiceIntentInfo(Parcel parcel) {
            super(parcel);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("ServiceIntentInfo{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(' ');
            this.service.appendComponentShortName(stringBuilder);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    public static final class SigningDetails
    implements Parcelable {
        public static final Parcelable.Creator<SigningDetails> CREATOR;
        private static final int PAST_CERT_EXISTS = 0;
        public static final SigningDetails UNKNOWN;
        public final Signature[] pastSigningCertificates;
        public final ArraySet<PublicKey> publicKeys;
        @SignatureSchemeVersion
        public final int signatureSchemeVersion;
        @UnsupportedAppUsage
        public final Signature[] signatures;

        static {
            UNKNOWN = new SigningDetails(null, 0, null, null);
            CREATOR = new Parcelable.Creator<SigningDetails>(){

                @Override
                public SigningDetails createFromParcel(Parcel parcel) {
                    if (parcel.readBoolean()) {
                        return UNKNOWN;
                    }
                    return new SigningDetails(parcel);
                }

                public SigningDetails[] newArray(int n) {
                    return new SigningDetails[n];
                }
            };
        }

        public SigningDetails(SigningDetails arrsignature) {
            if (arrsignature != null) {
                Signature[] arrsignature2 = arrsignature.signatures;
                this.signatures = arrsignature2 != null ? (Signature[])arrsignature2.clone() : null;
                this.signatureSchemeVersion = arrsignature.signatureSchemeVersion;
                this.publicKeys = new ArraySet<PublicKey>(arrsignature.publicKeys);
                arrsignature = arrsignature.pastSigningCertificates;
                this.pastSigningCertificates = arrsignature != null ? (Signature[])arrsignature.clone() : null;
            } else {
                this.signatures = null;
                this.signatureSchemeVersion = 0;
                this.publicKeys = null;
                this.pastSigningCertificates = null;
            }
        }

        protected SigningDetails(Parcel parcel) {
            ClassLoader classLoader = Object.class.getClassLoader();
            this.signatures = parcel.createTypedArray(Signature.CREATOR);
            this.signatureSchemeVersion = parcel.readInt();
            this.publicKeys = parcel.readArraySet(classLoader);
            this.pastSigningCertificates = parcel.createTypedArray(Signature.CREATOR);
        }

        public SigningDetails(Signature[] arrsignature, @SignatureSchemeVersion int n) throws CertificateException {
            this(arrsignature, n, null);
        }

        @VisibleForTesting
        public SigningDetails(Signature[] arrsignature, @SignatureSchemeVersion int n, ArraySet<PublicKey> arraySet, Signature[] arrsignature2) {
            this.signatures = arrsignature;
            this.signatureSchemeVersion = n;
            this.publicKeys = arraySet;
            this.pastSigningCertificates = arrsignature2;
        }

        public SigningDetails(Signature[] arrsignature, @SignatureSchemeVersion int n, Signature[] arrsignature2) throws CertificateException {
            this(arrsignature, n, PackageParser.toSigningKeys(arrsignature), arrsignature2);
        }

        private boolean hasCertificateInternal(Signature signature, int n) {
            Signature[] arrsignature = UNKNOWN;
            boolean bl = false;
            if (this == arrsignature) {
                return false;
            }
            if (this.hasPastSigningCertificates()) {
                for (int i = 0; i < (arrsignature = this.pastSigningCertificates).length - 1; ++i) {
                    if (!arrsignature[i].equals(signature) || n != 0 && (this.pastSigningCertificates[i].getFlags() & n) != n) continue;
                    return true;
                }
            }
            arrsignature = this.signatures;
            boolean bl2 = bl;
            if (arrsignature.length == 1) {
                bl2 = bl;
                if (arrsignature[0].equals(signature)) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        private boolean hasSha256CertificateInternal(byte[] arrby, int n) {
            Signature[] arrsignature;
            if (this == UNKNOWN) {
                return false;
            }
            if (this.hasPastSigningCertificates()) {
                for (int i = 0; i < (arrsignature = this.pastSigningCertificates).length - 1; ++i) {
                    if (!Arrays.equals(arrby, PackageUtils.computeSha256DigestBytes(arrsignature[i].toByteArray())) || n != 0 && (this.pastSigningCertificates[i].getFlags() & n) != n) continue;
                    return true;
                }
            }
            if ((arrsignature = this.signatures).length == 1) {
                return Arrays.equals(arrby, PackageUtils.computeSha256DigestBytes(arrsignature[0].toByteArray()));
            }
            return false;
        }

        public boolean checkCapability(SigningDetails signingDetails, @CertCapabilities int n) {
            Signature[] arrsignature = UNKNOWN;
            if (this != arrsignature && signingDetails != arrsignature) {
                arrsignature = signingDetails.signatures;
                if (arrsignature.length > 1) {
                    return this.signaturesMatchExactly(signingDetails);
                }
                return this.hasCertificate(arrsignature[0], n);
            }
            return false;
        }

        public boolean checkCapability(String string2, @CertCapabilities int n) {
            if (this == UNKNOWN) {
                return false;
            }
            if (this.hasSha256Certificate(ByteStringUtils.fromHexToByteArray(string2), n)) {
                return true;
            }
            String[] arrstring = PackageUtils.computeSignaturesSha256Digests(this.signatures);
            return PackageUtils.computeSignaturesSha256Digest(arrstring).equals(string2);
        }

        public boolean checkCapabilityRecover(SigningDetails signingDetails, @CertCapabilities int n) throws CertificateException {
            Signature[] arrsignature = UNKNOWN;
            if (signingDetails != arrsignature && this != arrsignature) {
                if (this.hasPastSigningCertificates() && signingDetails.signatures.length == 1) {
                    for (int i = 0; i < (arrsignature = this.pastSigningCertificates).length; ++i) {
                        if (!Signature.areEffectiveMatch(signingDetails.signatures[0], arrsignature[i]) || this.pastSigningCertificates[i].getFlags() != n) continue;
                        return true;
                    }
                    return false;
                }
                return Signature.areEffectiveMatch(signingDetails.signatures, this.signatures);
            }
            return false;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof SigningDetails)) {
                return false;
            }
            object = (SigningDetails)object;
            if (this.signatureSchemeVersion != ((SigningDetails)object).signatureSchemeVersion) {
                return false;
            }
            if (!Signature.areExactMatch(this.signatures, ((SigningDetails)object).signatures)) {
                return false;
            }
            ArraySet<PublicKey> arraySet = this.publicKeys;
            if (arraySet != null ? !arraySet.equals(((SigningDetails)object).publicKeys) : ((SigningDetails)object).publicKeys != null) {
                return false;
            }
            return Arrays.equals(this.pastSigningCertificates, ((SigningDetails)object).pastSigningCertificates);
        }

        public boolean hasAncestor(SigningDetails signingDetails) {
            Signature[] arrsignature = UNKNOWN;
            if (this != arrsignature && signingDetails != arrsignature) {
                if (this.hasPastSigningCertificates() && signingDetails.signatures.length == 1) {
                    for (int i = 0; i < (arrsignature = this.pastSigningCertificates).length - 1; ++i) {
                        if (!arrsignature[i].equals(signingDetails.signatures[i])) continue;
                        return true;
                    }
                }
                return false;
            }
            return false;
        }

        public boolean hasAncestorOrSelf(SigningDetails signingDetails) {
            Signature[] arrsignature = UNKNOWN;
            if (this != arrsignature && signingDetails != arrsignature) {
                arrsignature = signingDetails.signatures;
                if (arrsignature.length > 1) {
                    return this.signaturesMatchExactly(signingDetails);
                }
                return this.hasCertificate(arrsignature[0]);
            }
            return false;
        }

        public boolean hasCertificate(Signature signature) {
            return this.hasCertificateInternal(signature, 0);
        }

        public boolean hasCertificate(Signature signature, @CertCapabilities int n) {
            return this.hasCertificateInternal(signature, n);
        }

        public boolean hasCertificate(byte[] arrby) {
            return this.hasCertificate(new Signature(arrby));
        }

        public boolean hasPastSigningCertificates() {
            Signature[] arrsignature = this.pastSigningCertificates;
            boolean bl = arrsignature != null && arrsignature.length > 0;
            return bl;
        }

        public boolean hasSha256Certificate(byte[] arrby) {
            return this.hasSha256CertificateInternal(arrby, 0);
        }

        public boolean hasSha256Certificate(byte[] arrby, @CertCapabilities int n) {
            return this.hasSha256CertificateInternal(arrby, n);
        }

        public boolean hasSignatures() {
            Signature[] arrsignature = this.signatures;
            boolean bl = arrsignature != null && arrsignature.length > 0;
            return bl;
        }

        public int hashCode() {
            int n = Arrays.hashCode(this.signatures);
            int n2 = this.signatureSchemeVersion;
            ArraySet<PublicKey> arraySet = this.publicKeys;
            int n3 = arraySet != null ? arraySet.hashCode() : 0;
            return ((n * 31 + n2) * 31 + n3) * 31 + Arrays.hashCode(this.pastSigningCertificates);
        }

        public boolean signaturesMatchExactly(SigningDetails signingDetails) {
            return Signature.areExactMatch(this.signatures, signingDetails.signatures);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            boolean bl = UNKNOWN == this;
            parcel.writeBoolean(bl);
            if (bl) {
                return;
            }
            parcel.writeTypedArray((Parcelable[])this.signatures, n);
            parcel.writeInt(this.signatureSchemeVersion);
            parcel.writeArraySet(this.publicKeys);
            parcel.writeTypedArray((Parcelable[])this.pastSigningCertificates, n);
        }

        public static class Builder {
            private Signature[] mPastSigningCertificates;
            private int mSignatureSchemeVersion = 0;
            private Signature[] mSignatures;

            private void checkInvariants() {
                if (this.mSignatures != null) {
                    return;
                }
                throw new IllegalStateException("SigningDetails requires the current signing certificates.");
            }

            @UnsupportedAppUsage
            public SigningDetails build() throws CertificateException {
                this.checkInvariants();
                return new SigningDetails(this.mSignatures, this.mSignatureSchemeVersion, this.mPastSigningCertificates);
            }

            @UnsupportedAppUsage
            public Builder setPastSigningCertificates(Signature[] arrsignature) {
                this.mPastSigningCertificates = arrsignature;
                return this;
            }

            @UnsupportedAppUsage
            public Builder setSignatureSchemeVersion(int n) {
                this.mSignatureSchemeVersion = n;
                return this;
            }

            @UnsupportedAppUsage
            public Builder setSignatures(Signature[] arrsignature) {
                this.mSignatures = arrsignature;
                return this;
            }
        }

        public static @interface CertCapabilities {
            public static final int AUTH = 16;
            public static final int INSTALLED_DATA = 1;
            public static final int PERMISSION = 4;
            public static final int ROLLBACK = 8;
            public static final int SHARED_USER_ID = 2;
        }

        public static @interface SignatureSchemeVersion {
            public static final int JAR = 1;
            public static final int SIGNING_BLOCK_V2 = 2;
            public static final int SIGNING_BLOCK_V3 = 3;
            public static final int UNKNOWN = 0;
        }

    }

    private static class SplitNameComparator
    implements Comparator<String> {
        private SplitNameComparator() {
        }

        @Override
        public int compare(String string2, String string3) {
            if (string2 == null) {
                return -1;
            }
            if (string3 == null) {
                return 1;
            }
            return string2.compareTo(string3);
        }
    }

}

