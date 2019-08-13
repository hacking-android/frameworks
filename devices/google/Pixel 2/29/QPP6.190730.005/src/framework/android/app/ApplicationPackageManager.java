/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  dalvik.system.VMRuntime
 *  libcore.util.EmptyArray
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.ContextImpl;
import android.app.LoadedApk;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ChangedPackages;
import android.content.pm.ComponentInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.IDexModuleRegisterCallback;
import android.content.pm.IOnPermissionsChangeListener;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstaller;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.InstantAppInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.IntentFilterVerificationInfo;
import android.content.pm.KeySet;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.VerifierDeviceIdentity;
import android.content.pm.VersionedPackage;
import android.content.pm.dex.ArtManager;
import android.content.pm.dex.IArtManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.provider.Settings;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.IconDrawableFactory;
import android.util.LauncherIcons;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import com.android.internal.util.UserIcons;
import dalvik.system.VMRuntime;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import libcore.util.EmptyArray;

public class ApplicationPackageManager
extends PackageManager {
    @VisibleForTesting
    public static final int[] CORP_BADGE_LABEL_RES_ID = new int[]{17040300, 17040301, 17040302};
    private static final boolean DEBUG_ICONS = false;
    private static final int DEFAULT_EPHEMERAL_COOKIE_MAX_SIZE_BYTES = 16384;
    private static final String TAG = "ApplicationPackageManager";
    private static final int sDefaultFlags = 1024;
    private static ArrayMap<ResourceName, WeakReference<Drawable.ConstantState>> sIconCache;
    private static ArrayMap<ResourceName, WeakReference<CharSequence>> sStringCache;
    private static final Object sSync;
    @GuardedBy(value={"mLock"})
    private ArtManager mArtManager;
    volatile int mCachedSafeMode = -1;
    private final ContextImpl mContext;
    @GuardedBy(value={"mDelegates"})
    private final ArrayList<MoveCallbackDelegate> mDelegates = new ArrayList();
    @GuardedBy(value={"mLock"})
    private PackageInstaller mInstaller;
    private final Object mLock = new Object();
    @UnsupportedAppUsage
    private final IPackageManager mPM;
    private final Map<PackageManager.OnPermissionsChangedListener, IOnPermissionsChangeListener> mPermissionListeners = new ArrayMap<PackageManager.OnPermissionsChangedListener, IOnPermissionsChangeListener>();
    @GuardedBy(value={"mLock"})
    private String mPermissionsControllerPackageName;
    @GuardedBy(value={"mLock"})
    private UserManager mUserManager;
    private volatile boolean mUserUnlocked = false;

    static {
        sSync = new Object();
        sIconCache = new ArrayMap();
        sStringCache = new ArrayMap();
    }

    @UnsupportedAppUsage
    protected ApplicationPackageManager(ContextImpl contextImpl, IPackageManager iPackageManager) {
        this.mContext = contextImpl;
        this.mPM = iPackageManager;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    static void configurationChanged() {
        Object object = sSync;
        synchronized (object) {
            sIconCache.clear();
            sStringCache.clear();
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Drawable getBadgedDrawable(Drawable var1_1, Drawable var2_2, Rect var3_3, boolean var4_4) {
        var5_5 = var1_1.getIntrinsicWidth();
        var6_6 = var1_1.getIntrinsicHeight();
        var7_7 = var4_4 != false && var1_1 instanceof BitmapDrawable != false && ((BitmapDrawable)var1_1).getBitmap().isMutable() != false;
        var8_8 = var7_7 != false ? ((BitmapDrawable)var1_1).getBitmap() : Bitmap.createBitmap(var5_5, var6_6, Bitmap.Config.ARGB_8888);
        var9_9 = new Canvas(var8_8);
        if (!var7_7) {
            var1_1.setBounds(0, 0, var5_5, var6_6);
            var1_1.draw(var9_9);
        }
        if (var3_3 == null) ** GOTO lbl28
        if (var3_3.left >= 0 && var3_3.top >= 0 && var3_3.width() <= var5_5 && var3_3.height() <= var6_6) {
            var2_2.setBounds(0, 0, var3_3.width(), var3_3.height());
            var9_9.save();
            var9_9.translate(var3_3.left, var3_3.top);
            var2_2.draw(var9_9);
            var9_9.restore();
        } else {
            var1_1 = new StringBuilder();
            var1_1.append("Badge location ");
            var1_1.append(var3_3);
            var1_1.append(" not in badged drawable bounds ");
            var1_1.append(new Rect(0, 0, var5_5, var6_6));
            throw new IllegalArgumentException(var1_1.toString());
lbl28: // 1 sources:
            var2_2.setBounds(0, 0, var5_5, var6_6);
            var2_2.draw(var9_9);
        }
        if (var7_7 != false) return var1_1;
        var2_2 = new BitmapDrawable(this.mContext.getResources(), var8_8);
        if (var1_1 instanceof BitmapDrawable == false) return var2_2;
        var2_2.setTargetDensity(((BitmapDrawable)var1_1).getBitmap().getDensity());
        return var2_2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Drawable getCachedIcon(ResourceName object) {
        Object object2 = sSync;
        synchronized (object2) {
            Object object3 = sIconCache.get(object);
            if (object3 == null) return null;
            if ((object3 = (Drawable.ConstantState)((Reference)object3).get()) != null) {
                return ((Drawable.ConstantState)object3).newDrawable();
            }
            sIconCache.remove(object);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private CharSequence getCachedString(ResourceName resourceName) {
        Object object = sSync;
        synchronized (object) {
            Object object2 = sStringCache.get(resourceName);
            if (object2 != null) {
                if ((object2 = (CharSequence)((Reference)object2).get()) != null) {
                    return object2;
                }
                sStringCache.remove(resourceName);
            }
            return null;
        }
    }

    private Drawable getDrawableForDensity(int n, int n2) {
        int n3 = n2;
        if (n2 <= 0) {
            n3 = this.mContext.getResources().getDisplayMetrics().densityDpi;
        }
        return this.mContext.getResources().getDrawableForDensity(n, n3);
    }

    private Intent getLaunchIntentForPackageAndCategory(String object, String object2) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory((String)object2);
        intent.setPackage((String)object);
        object = this.queryIntentActivities(intent, 0);
        if (object != null && object.size() > 0) {
            object2 = new Intent(intent);
            ((Intent)object2).setFlags(268435456);
            ((Intent)object2).setClassName(((ResolveInfo)object.get((int)0)).activityInfo.packageName, ((ResolveInfo)object.get((int)0)).activityInfo.name);
            return object2;
        }
        return null;
    }

    private Drawable getManagedProfileIconForDensity(UserHandle userHandle, int n, int n2) {
        if (this.isManagedProfile(userHandle.getIdentifier())) {
            return this.getDrawableForDensity(n, n2);
        }
        return null;
    }

    private int getUserBadgeColor(UserHandle userHandle) {
        return IconDrawableFactory.getUserBadgeColor(this.getUserManager(), userHandle.getIdentifier());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void handlePackageBroadcast(int n, String[] arrstring, boolean bl) {
        boolean bl2 = false;
        if (n == 1) {
            bl2 = true;
        }
        if (arrstring == null) return;
        if (arrstring.length <= 0) return;
        n = 0;
        for (String string2 : arrstring) {
            Object object = sSync;
            synchronized (object) {
                int n2;
                for (n2 = ApplicationPackageManager.sIconCache.size() - 1; n2 >= 0; --n2) {
                    if (!ApplicationPackageManager.sIconCache.keyAt((int)n2).packageName.equals(string2)) continue;
                    sIconCache.removeAt(n2);
                    n = 1;
                }
                for (n2 = ApplicationPackageManager.sStringCache.size() - 1; n2 >= 0; --n2) {
                    if (!ApplicationPackageManager.sStringCache.keyAt((int)n2).packageName.equals(string2)) continue;
                    sStringCache.removeAt(n2);
                    n = 1;
                }
            }
        }
        if (n == 0) {
            if (!bl) return;
        }
        if (bl2) {
            Runtime.getRuntime().gc();
            return;
        }
        ActivityThread.currentActivityThread().scheduleGcIdler();
    }

    private int installExistingPackageAsUser(String string2, int n, int n2) throws PackageManager.NameNotFoundException {
        block3 : {
            try {
                n = this.mPM.installExistingPackageAsUser(string2, n2, 4194304, n, null);
                if (n == -3) break block3;
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Package ");
        stringBuilder.append(string2);
        stringBuilder.append(" doesn't exist");
        PackageManager.NameNotFoundException nameNotFoundException = new PackageManager.NameNotFoundException(stringBuilder.toString());
        throw nameNotFoundException;
    }

    private boolean isManagedProfile(int n) {
        return this.getUserManager().isManagedProfile(n);
    }

    private boolean isPackageCandidateVolume(ContextImpl contextImpl, ApplicationInfo applicationInfo, VolumeInfo volumeInfo, IPackageManager iPackageManager) {
        boolean bl = this.isForceAllowOnExternal(contextImpl);
        boolean bl2 = "private".equals(volumeInfo.getId());
        boolean bl3 = true;
        boolean bl4 = true;
        if (bl2) {
            bl3 = bl4;
            if (!applicationInfo.isSystemApp()) {
                bl3 = this.isAllow3rdPartyOnInternal(contextImpl) ? bl4 : false;
            }
            return bl3;
        }
        if (applicationInfo.isSystemApp()) {
            return false;
        }
        if (!(bl || applicationInfo.installLocation != 1 && applicationInfo.installLocation != -1)) {
            return false;
        }
        if (!volumeInfo.isMountedWritable()) {
            return false;
        }
        if (volumeInfo.isPrimaryPhysical()) {
            return applicationInfo.isInternal();
        }
        try {
            bl4 = iPackageManager.isPackageDeviceAdminOnAnyUser(applicationInfo.packageName);
            if (bl4) {
                return false;
            }
            if (volumeInfo.getType() != 1) {
                bl3 = false;
            }
            return bl3;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private static boolean isPrimaryStorageCandidateVolume(VolumeInfo volumeInfo) {
        boolean bl = "private".equals(volumeInfo.getId());
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (!volumeInfo.isMountedWritable()) {
            return false;
        }
        if (volumeInfo.getType() != 1) {
            bl2 = false;
        }
        return bl2;
    }

    private static ApplicationInfo maybeAdjustApplicationInfo(ApplicationInfo applicationInfo) {
        if (applicationInfo.primaryCpuAbi != null && applicationInfo.secondaryCpuAbi != null) {
            String string2 = VMRuntime.getRuntime().vmInstructionSet();
            Object object = VMRuntime.getInstructionSet((String)applicationInfo.secondaryCpuAbi);
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("ro.dalvik.vm.isa.");
            ((StringBuilder)charSequence).append((String)object);
            charSequence = SystemProperties.get(((StringBuilder)charSequence).toString());
            if (!((String)charSequence).isEmpty()) {
                object = charSequence;
            }
            if (string2.equals(object)) {
                object = new ApplicationInfo(applicationInfo);
                ((ApplicationInfo)object).nativeLibraryDir = applicationInfo.secondaryNativeLibraryDir;
                return object;
            }
        }
        return applicationInfo;
    }

    private void onImplicitDirectBoot(int n) {
        if (StrictMode.vmImplicitDirectBootEnabled()) {
            if (n == UserHandle.myUserId()) {
                if (this.mUserUnlocked) {
                    return;
                }
                if (this.mContext.getSystemService(UserManager.class).isUserUnlockingOrUnlocked(n)) {
                    this.mUserUnlocked = true;
                } else {
                    StrictMode.onImplicitDirectBoot();
                }
            } else if (!this.mContext.getSystemService(UserManager.class).isUserUnlockingOrUnlocked(n)) {
                StrictMode.onImplicitDirectBoot();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void putCachedIcon(ResourceName resourceName, Drawable drawable2) {
        Object object = sSync;
        synchronized (object) {
            ArrayMap<ResourceName, WeakReference<Drawable.ConstantState>> arrayMap = sIconCache;
            WeakReference<Drawable.ConstantState> weakReference = new WeakReference<Drawable.ConstantState>(drawable2.getConstantState());
            arrayMap.put(resourceName, weakReference);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void putCachedString(ResourceName resourceName, CharSequence charSequence) {
        Object object = sSync;
        synchronized (object) {
            ArrayMap<ResourceName, WeakReference<CharSequence>> arrayMap = sStringCache;
            WeakReference<CharSequence> weakReference = new WeakReference<CharSequence>(charSequence);
            arrayMap.put(resourceName, weakReference);
            return;
        }
    }

    private int updateFlagsForApplication(int n, int n2) {
        return this.updateFlagsForPackage(n, n2);
    }

    private int updateFlagsForComponent(int n, int n2, Intent intent) {
        int n3 = n;
        if (intent != null) {
            n3 = n;
            if ((intent.getFlags() & 256) != 0) {
                n3 = n | 268435456;
            }
        }
        if ((269221888 & n3) == 0) {
            this.onImplicitDirectBoot(n2);
        }
        return n3;
    }

    private int updateFlagsForPackage(int n, int n2) {
        if ((n & 15) != 0 && (269221888 & n) == 0) {
            this.onImplicitDirectBoot(n2);
        }
        return n;
    }

    @Override
    public void addCrossProfileIntentFilter(IntentFilter intentFilter, int n, int n2, int n3) {
        try {
            this.mPM.addCrossProfileIntentFilter(intentFilter, this.mContext.getOpPackageName(), n, n2, n3);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addOnPermissionsChangeListener(PackageManager.OnPermissionsChangedListener onPermissionsChangedListener) {
        Map<PackageManager.OnPermissionsChangedListener, IOnPermissionsChangeListener> map = this.mPermissionListeners;
        synchronized (map) {
            if (this.mPermissionListeners.get(onPermissionsChangedListener) != null) {
                return;
            }
            OnPermissionsChangeListenerDelegate onPermissionsChangeListenerDelegate = new OnPermissionsChangeListenerDelegate(onPermissionsChangedListener, Looper.getMainLooper());
            try {
                this.mPM.addOnPermissionsChangeListener(onPermissionsChangeListenerDelegate);
                this.mPermissionListeners.put(onPermissionsChangedListener, onPermissionsChangeListenerDelegate);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Override
    public void addPackageToPreferred(String string2) {
        Log.w(TAG, "addPackageToPreferred() is a no-op");
    }

    @Override
    public boolean addPermission(PermissionInfo permissionInfo) {
        try {
            boolean bl = this.mPM.addPermission(permissionInfo);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean addPermissionAsync(PermissionInfo permissionInfo) {
        try {
            boolean bl = this.mPM.addPermissionAsync(permissionInfo);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void addPreferredActivity(IntentFilter intentFilter, int n, ComponentName[] arrcomponentName, ComponentName componentName) {
        try {
            this.mPM.addPreferredActivity(intentFilter, n, arrcomponentName, componentName, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void addPreferredActivityAsUser(IntentFilter intentFilter, int n, ComponentName[] arrcomponentName, ComponentName componentName, int n2) {
        try {
            this.mPM.addPreferredActivity(intentFilter, n, arrcomponentName, componentName, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean addWhitelistedRestrictedPermission(String string2, String string3, int n) {
        try {
            boolean bl = this.mPM.addWhitelistedRestrictedPermission(string2, string3, n, this.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean arePermissionsIndividuallyControlled() {
        return this.mContext.getResources().getBoolean(17891491);
    }

    @Override
    public boolean canRequestPackageInstalls() {
        try {
            boolean bl = this.mPM.canRequestPackageInstalls(this.mContext.getPackageName(), this.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public String[] canonicalToCurrentPackageNames(String[] arrstring) {
        try {
            arrstring = this.mPM.canonicalToCurrentPackageNames(arrstring);
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public int checkPermission(String string2, String string3) {
        try {
            int n = this.mPM.checkPermission(string2, string3, this.getUserId());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public int checkSignatures(int n, int n2) {
        try {
            n = this.mPM.checkUidSignatures(n, n2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public int checkSignatures(String string2, String string3) {
        try {
            int n = this.mPM.checkSignatures(string2, string3);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void clearApplicationUserData(String string2, IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.clearApplicationUserData(string2, iPackageDataObserver, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void clearCrossProfileIntentFilters(int n) {
        try {
            this.mPM.clearCrossProfileIntentFilters(n, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void clearInstantAppCookie() {
        this.updateInstantAppCookie(null);
    }

    @Override
    public void clearPackagePreferredActivities(String string2) {
        try {
            this.mPM.clearPackagePreferredActivities(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String[] currentToCanonicalPackageNames(String[] arrstring) {
        try {
            arrstring = this.mPM.currentToCanonicalPackageNames(arrstring);
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void deleteApplicationCacheFiles(String string2, IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.deleteApplicationCacheFiles(string2, iPackageDataObserver);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void deleteApplicationCacheFilesAsUser(String string2, int n, IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.deleteApplicationCacheFilesAsUser(string2, n, iPackageDataObserver);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    @Override
    public void deletePackage(String string2, IPackageDeleteObserver iPackageDeleteObserver, int n) {
        this.deletePackageAsUser(string2, iPackageDeleteObserver, n, this.getUserId());
    }

    @Override
    public void deletePackageAsUser(String string2, IPackageDeleteObserver iPackageDeleteObserver, int n, int n2) {
        try {
            this.mPM.deletePackageAsUser(string2, -1, iPackageDeleteObserver, n2, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void extendVerificationTimeout(int n, int n2, long l) {
        try {
            this.mPM.extendVerificationTimeout(n, n2, l);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void flushPackageRestrictionsAsUser(int n) {
        try {
            this.mPM.flushPackageRestrictionsAsUser(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void freeStorage(String string2, long l, IntentSender intentSender) {
        try {
            this.mPM.freeStorage(string2, l, 0, intentSender);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void freeStorageAndNotify(String string2, long l, IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.freeStorageAndNotify(string2, l, 0, iPackageDataObserver);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public Drawable getActivityBanner(ComponentName componentName) throws PackageManager.NameNotFoundException {
        return this.getActivityInfo(componentName, 1024).loadBanner(this);
    }

    @Override
    public Drawable getActivityBanner(Intent intent) throws PackageManager.NameNotFoundException {
        if (intent.getComponent() != null) {
            return this.getActivityBanner(intent.getComponent());
        }
        ResolveInfo resolveInfo = this.resolveActivity(intent, 65536);
        if (resolveInfo != null) {
            return resolveInfo.activityInfo.loadBanner(this);
        }
        throw new PackageManager.NameNotFoundException(intent.toUri(0));
    }

    @Override
    public Drawable getActivityIcon(ComponentName componentName) throws PackageManager.NameNotFoundException {
        return this.getActivityInfo(componentName, 1024).loadIcon(this);
    }

    @Override
    public Drawable getActivityIcon(Intent intent) throws PackageManager.NameNotFoundException {
        if (intent.getComponent() != null) {
            return this.getActivityIcon(intent.getComponent());
        }
        ResolveInfo resolveInfo = this.resolveActivity(intent, 65536);
        if (resolveInfo != null) {
            return resolveInfo.activityInfo.loadIcon(this);
        }
        throw new PackageManager.NameNotFoundException(intent.toUri(0));
    }

    @Override
    public ActivityInfo getActivityInfo(ComponentName componentName, int n) throws PackageManager.NameNotFoundException {
        int n2 = this.getUserId();
        try {
            ActivityInfo activityInfo = this.mPM.getActivityInfo(componentName, this.updateFlagsForComponent(n, n2, null), n2);
            if (activityInfo != null) {
                return activityInfo;
            }
            throw new PackageManager.NameNotFoundException(componentName.toString());
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public Drawable getActivityLogo(ComponentName componentName) throws PackageManager.NameNotFoundException {
        return this.getActivityInfo(componentName, 1024).loadLogo(this);
    }

    @Override
    public Drawable getActivityLogo(Intent intent) throws PackageManager.NameNotFoundException {
        if (intent.getComponent() != null) {
            return this.getActivityLogo(intent.getComponent());
        }
        ResolveInfo resolveInfo = this.resolveActivity(intent, 65536);
        if (resolveInfo != null) {
            return resolveInfo.activityInfo.loadLogo(this);
        }
        throw new PackageManager.NameNotFoundException(intent.toUri(0));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<IntentFilter> getAllIntentFilters(String object) {
        try {
            object = this.mPM.getAllIntentFilters((String)object);
            if (object != null) return ((ParceledListSlice)object).getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<PermissionGroupInfo> getAllPermissionGroups(int n) {
        try {
            ParceledListSlice parceledListSlice = this.mPM.getAllPermissionGroups(n);
            if (parceledListSlice != null) return parceledListSlice.getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    @Override
    public String getAppPredictionServicePackageName() {
        try {
            String string2 = this.mPM.getAppPredictionServicePackageName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public Drawable getApplicationBanner(ApplicationInfo applicationInfo) {
        return applicationInfo.loadBanner(this);
    }

    @Override
    public Drawable getApplicationBanner(String string2) throws PackageManager.NameNotFoundException {
        return this.getApplicationBanner(this.getApplicationInfo(string2, 1024));
    }

    @Override
    public int getApplicationEnabledSetting(String string2) {
        try {
            int n = this.mPM.getApplicationEnabledSetting(string2, this.getUserId());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean getApplicationHiddenSettingAsUser(String string2, UserHandle userHandle) {
        try {
            boolean bl = this.mPM.getApplicationHiddenSettingAsUser(string2, userHandle.getIdentifier());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public Drawable getApplicationIcon(ApplicationInfo applicationInfo) {
        return applicationInfo.loadIcon(this);
    }

    @Override
    public Drawable getApplicationIcon(String string2) throws PackageManager.NameNotFoundException {
        return this.getApplicationIcon(this.getApplicationInfo(string2, 1024));
    }

    @Override
    public ApplicationInfo getApplicationInfo(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.getApplicationInfoAsUser(string2, n, this.getUserId());
    }

    @Override
    public ApplicationInfo getApplicationInfoAsUser(String object, int n, int n2) throws PackageManager.NameNotFoundException {
        block3 : {
            ApplicationInfo applicationInfo;
            try {
                applicationInfo = this.mPM.getApplicationInfo((String)object, this.updateFlagsForApplication(n, n2), n2);
                if (applicationInfo == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            object = ApplicationPackageManager.maybeAdjustApplicationInfo(applicationInfo);
            return object;
        }
        throw new PackageManager.NameNotFoundException((String)object);
    }

    @Override
    public CharSequence getApplicationLabel(ApplicationInfo applicationInfo) {
        return applicationInfo.loadLabel(this);
    }

    @Override
    public Drawable getApplicationLogo(ApplicationInfo applicationInfo) {
        return applicationInfo.loadLogo(this);
    }

    @Override
    public Drawable getApplicationLogo(String string2) throws PackageManager.NameNotFoundException {
        return this.getApplicationLogo(this.getApplicationInfo(string2, 1024));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ArtManager getArtManager() {
        Object object = this.mLock;
        synchronized (object) {
            ArtManager artManager = this.mArtManager;
            if (artManager != null) return this.mArtManager;
            try {
                this.mArtManager = artManager = new ArtManager(this.mContext, this.mPM.getArtManager());
                return this.mArtManager;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Override
    public String getAttentionServicePackageName() {
        try {
            String string2 = this.mPM.getAttentionServicePackageName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public Intent getCarLaunchIntentForPackage(String string2) {
        return this.getLaunchIntentForPackageAndCategory(string2, "android.intent.category.CAR_LAUNCHER");
    }

    @Override
    public ChangedPackages getChangedPackages(int n) {
        try {
            ChangedPackages changedPackages = this.mPM.getChangedPackages(n, this.getUserId());
            return changedPackages;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public int getComponentEnabledSetting(ComponentName componentName) {
        try {
            int n = this.mPM.getComponentEnabledSetting(componentName, this.getUserId());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<SharedLibraryInfo> getDeclaredSharedLibraries(String list, int n) {
        try {
            list = this.mPM.getDeclaredSharedLibraries((String)((Object)list), n, this.mContext.getUserId());
            if (list == null) return Collections.emptyList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return ((ParceledListSlice)((Object)list)).getList();
    }

    @Override
    public Drawable getDefaultActivityIcon() {
        return this.mContext.getDrawable(17301651);
    }

    @Override
    public String getDefaultBrowserPackageNameAsUser(int n) {
        try {
            String string2 = this.mPM.getDefaultBrowserPackageName(n);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public Drawable getDrawable(String string2, int n, ApplicationInfo object) {
        block11 : {
            ResourceName resourceName = new ResourceName(string2, n);
            Object object2 = this.getCachedIcon(resourceName);
            if (object2 != null) {
                return object2;
            }
            object2 = object;
            if (object == null) {
                try {
                    object2 = this.getApplicationInfo(string2, 1024);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    return null;
                }
            }
            if (n != 0) {
                block10 : {
                    object = this.getResourcesForApplication((ApplicationInfo)object2).getDrawable(n, null);
                    if (object == null) break block10;
                    try {
                        this.putCachedIcon(resourceName, (Drawable)object);
                    }
                    catch (Exception exception) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Failure retrieving icon 0x");
                        ((StringBuilder)object2).append(Integer.toHexString(n));
                        ((StringBuilder)object2).append(" in package ");
                        ((StringBuilder)object2).append(string2);
                        Log.w("PackageManager", ((StringBuilder)object2).toString(), exception);
                        break block11;
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Failure retrieving resources for ");
                        ((StringBuilder)object).append(((ApplicationInfo)object2).packageName);
                        ((StringBuilder)object).append(": ");
                        ((StringBuilder)object).append(notFoundException.getMessage());
                        Log.w("PackageManager", ((StringBuilder)object).toString());
                        break block11;
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Failure retrieving resources for ");
                        stringBuilder.append(((ApplicationInfo)object2).packageName);
                        Log.w("PackageManager", stringBuilder.toString());
                    }
                }
                return object;
            }
        }
        return null;
    }

    @Override
    public CharSequence getHarmfulAppWarning(String charSequence) {
        try {
            charSequence = this.mPM.getHarmfulAppWarning((String)charSequence, this.getUserId());
            return charSequence;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public ComponentName getHomeActivities(List<ResolveInfo> object) {
        try {
            object = this.mPM.getHomeActivities((List<ResolveInfo>)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String getIncidentReportApproverPackageName() {
        try {
            String string2 = this.mPM.getIncidentReportApproverPackageName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public int getInstallReason(String string2, UserHandle userHandle) {
        try {
            int n = this.mPM.getInstallReason(string2, userHandle.getIdentifier());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public List<ApplicationInfo> getInstalledApplications(int n) {
        return this.getInstalledApplicationsAsUser(n, this.getUserId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<ApplicationInfo> getInstalledApplicationsAsUser(int n, int n2) {
        try {
            ParceledListSlice parceledListSlice = this.mPM.getInstalledApplications(this.updateFlagsForApplication(n, n2), n2);
            if (parceledListSlice != null) return parceledListSlice.getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    @Override
    public List<ModuleInfo> getInstalledModules(int n) {
        try {
            List<ModuleInfo> list = this.mPM.getInstalledModules(n);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public List<PackageInfo> getInstalledPackages(int n) {
        return this.getInstalledPackagesAsUser(n, this.getUserId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<PackageInfo> getInstalledPackagesAsUser(int n, int n2) {
        try {
            ParceledListSlice parceledListSlice = this.mPM.getInstalledPackages(this.updateFlagsForPackage(n, n2), n2);
            if (parceledListSlice != null) return parceledListSlice.getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    @Override
    public String getInstallerPackageName(String string2) {
        try {
            string2 = this.mPM.getInstallerPackageName(string2);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String getInstantAppAndroidId(String string2, UserHandle userHandle) {
        try {
            string2 = this.mPM.getInstantAppAndroidId(string2, userHandle.getIdentifier());
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public byte[] getInstantAppCookie() {
        byte[] arrby;
        block3 : {
            try {
                arrby = this.mPM.getInstantAppCookie(this.mContext.getPackageName(), this.getUserId());
                if (arrby == null) break block3;
                return arrby;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        arrby = EmptyArray.BYTE;
        return arrby;
    }

    @Override
    public int getInstantAppCookieMaxBytes() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "ephemeral_cookie_max_size_bytes", 16384);
    }

    @Override
    public int getInstantAppCookieMaxSize() {
        return this.getInstantAppCookieMaxBytes();
    }

    @Override
    public Drawable getInstantAppIcon(String object) {
        block3 : {
            try {
                object = this.mPM.getInstantAppIcon((String)object, this.getUserId());
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            object = new BitmapDrawable(null, (Bitmap)object);
            return object;
        }
        return null;
    }

    @Override
    public ComponentName getInstantAppInstallerComponent() {
        try {
            ComponentName componentName = this.mPM.getInstantAppInstallerComponent();
            return componentName;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public ComponentName getInstantAppResolverSettingsComponent() {
        try {
            ComponentName componentName = this.mPM.getInstantAppResolverSettingsComponent();
            return componentName;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<InstantAppInfo> getInstantApps() {
        ParceledListSlice parceledListSlice;
        try {
            parceledListSlice = this.mPM.getInstantApps(this.getUserId());
            if (parceledListSlice == null) return Collections.emptyList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return parceledListSlice.getList();
    }

    @Override
    public InstrumentationInfo getInstrumentationInfo(ComponentName componentName, int n) throws PackageManager.NameNotFoundException {
        try {
            InstrumentationInfo instrumentationInfo = this.mPM.getInstrumentationInfo(componentName, n);
            if (instrumentationInfo != null) {
                return instrumentationInfo;
            }
            throw new PackageManager.NameNotFoundException(componentName.toString());
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<IntentFilterVerificationInfo> getIntentFilterVerifications(String object) {
        try {
            object = this.mPM.getIntentFilterVerifications((String)object);
            if (object != null) return ((ParceledListSlice)object).getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    @Override
    public int getIntentVerificationStatusAsUser(String string2, int n) {
        try {
            n = this.mPM.getIntentVerificationStatus(string2, n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public KeySet getKeySetByAlias(String object, String string2) {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(string2);
        try {
            object = this.mPM.getKeySetByAlias((String)object, string2);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public Intent getLaunchIntentForPackage(String object) {
        Intent intent;
        List<ResolveInfo> list;
        block5 : {
            block4 : {
                intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.INFO");
                intent.setPackage((String)object);
                List<ResolveInfo> list2 = this.queryIntentActivities(intent, 0);
                if (list2 == null) break block4;
                list = list2;
                if (list2.size() > 0) break block5;
            }
            intent.removeCategory("android.intent.category.INFO");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage((String)object);
            list = this.queryIntentActivities(intent, 0);
        }
        if (list != null && list.size() > 0) {
            object = new Intent(intent);
            ((Intent)object).setFlags(268435456);
            ((Intent)object).setClassName(list.get((int)0).activityInfo.packageName, list.get((int)0).activityInfo.name);
            return object;
        }
        return null;
    }

    @Override
    public Intent getLeanbackLaunchIntentForPackage(String string2) {
        return this.getLaunchIntentForPackageAndCategory(string2, "android.intent.category.LEANBACK_LAUNCHER");
    }

    @Override
    public ModuleInfo getModuleInfo(String string2, int n) throws PackageManager.NameNotFoundException {
        Object object;
        try {
            object = this.mPM.getModuleInfo(string2, n);
            if (object != null) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("No module info for package: ");
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        ((StringBuilder)object).append(string2);
        throw new PackageManager.NameNotFoundException(((StringBuilder)object).toString());
    }

    @Override
    public int getMoveStatus(int n) {
        try {
            n = this.mPM.getMoveStatus(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String getNameForUid(int n) {
        try {
            String string2 = this.mPM.getNameForUid(n);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String[] getNamesForUids(int[] arrobject) {
        try {
            arrobject = this.mPM.getNamesForUids((int[])arrobject);
            return arrobject;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public List<VolumeInfo> getPackageCandidateVolumes(ApplicationInfo applicationInfo) {
        return this.getPackageCandidateVolumes(applicationInfo, this.mContext.getSystemService(StorageManager.class), this.mPM);
    }

    @VisibleForTesting
    protected List<VolumeInfo> getPackageCandidateVolumes(ApplicationInfo applicationInfo, StorageManager object, IPackageManager iPackageManager) {
        VolumeInfo volumeInfo = this.getPackageCurrentVolume(applicationInfo, (StorageManager)object);
        Object object2 = ((StorageManager)object).getVolumes();
        object = new ArrayList();
        Iterator<VolumeInfo> iterator = object2.iterator();
        while (iterator.hasNext()) {
            object2 = iterator.next();
            if (!Objects.equals(object2, volumeInfo) && !this.isPackageCandidateVolume(this.mContext, applicationInfo, (VolumeInfo)object2, iPackageManager)) continue;
            object.add(object2);
        }
        return object;
    }

    @UnsupportedAppUsage
    @Override
    public VolumeInfo getPackageCurrentVolume(ApplicationInfo applicationInfo) {
        return this.getPackageCurrentVolume(applicationInfo, this.mContext.getSystemService(StorageManager.class));
    }

    @VisibleForTesting
    protected VolumeInfo getPackageCurrentVolume(ApplicationInfo applicationInfo, StorageManager storageManager) {
        if (applicationInfo.isInternal()) {
            return storageManager.findVolumeById("private");
        }
        return storageManager.findVolumeByUuid(applicationInfo.volumeUuid);
    }

    @Override
    public int[] getPackageGids(String string2) throws PackageManager.NameNotFoundException {
        return this.getPackageGids(string2, 0);
    }

    @Override
    public int[] getPackageGids(String string2, int n) throws PackageManager.NameNotFoundException {
        int n2 = this.getUserId();
        try {
            int[] arrn = this.mPM.getPackageGids(string2, this.updateFlagsForPackage(n, n2), n2);
            if (arrn != null) {
                return arrn;
            }
            throw new PackageManager.NameNotFoundException(string2);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public PackageInfo getPackageInfo(VersionedPackage versionedPackage, int n) throws PackageManager.NameNotFoundException {
        int n2 = this.getUserId();
        try {
            PackageInfo packageInfo = this.mPM.getPackageInfoVersioned(versionedPackage, this.updateFlagsForPackage(n, n2), n2);
            if (packageInfo != null) {
                return packageInfo;
            }
            throw new PackageManager.NameNotFoundException(versionedPackage.toString());
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public PackageInfo getPackageInfo(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.getPackageInfoAsUser(string2, n, this.getUserId());
    }

    @Override
    public PackageInfo getPackageInfoAsUser(String string2, int n, int n2) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = this.mPM.getPackageInfo(string2, this.updateFlagsForPackage(n, n2), n2);
            if (packageInfo != null) {
                return packageInfo;
            }
            throw new PackageManager.NameNotFoundException(string2);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public PackageInstaller getPackageInstaller() {
        Object object = this.mLock;
        synchronized (object) {
            PackageInstaller packageInstaller = this.mInstaller;
            if (packageInstaller != null) return this.mInstaller;
            try {
                this.mInstaller = packageInstaller = new PackageInstaller(this.mPM.getPackageInstaller(), this.mContext.getPackageName(), this.getUserId());
                return this.mInstaller;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @UnsupportedAppUsage
    @Override
    public void getPackageSizeInfoAsUser(String string2, int n, IPackageStatsObserver iPackageStatsObserver) {
        if (this.mContext.getApplicationInfo().targetSdkVersion < 26) {
            if (iPackageStatsObserver != null) {
                Log.d(TAG, "Shame on you for calling the hidden API getPackageSizeInfoAsUser(). Shame!");
                try {
                    iPackageStatsObserver.onGetStatsCompleted(null, false);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return;
        }
        throw new UnsupportedOperationException("Shame on you for calling the hidden API getPackageSizeInfoAsUser(). Shame!");
    }

    @Override
    public int getPackageUid(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.getPackageUidAsUser(string2, n, this.getUserId());
    }

    @Override
    public int getPackageUidAsUser(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.getPackageUidAsUser(string2, 0, n);
    }

    @Override
    public int getPackageUidAsUser(String string2, int n, int n2) throws PackageManager.NameNotFoundException {
        try {
            n = this.mPM.getPackageUid(string2, this.updateFlagsForPackage(n, n2), n2);
            if (n >= 0) {
                return n;
            }
            throw new PackageManager.NameNotFoundException(string2);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String[] getPackagesForUid(int n) {
        try {
            String[] arrstring = this.mPM.getPackagesForUid(n);
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<PackageInfo> getPackagesHoldingPermissions(String[] object, int n) {
        int n2 = this.getUserId();
        try {
            object = this.mPM.getPackagesHoldingPermissions((String[])object, this.updateFlagsForPackage(n, n2), n2);
            if (object != null) return ((ParceledListSlice)object).getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String getPermissionControllerPackageName() {
        Object object = this.mLock;
        synchronized (object) {
            String string2 = this.mPermissionsControllerPackageName;
            if (string2 != null) return this.mPermissionsControllerPackageName;
            try {
                this.mPermissionsControllerPackageName = this.mPM.getPermissionControllerPackageName();
                return this.mPermissionsControllerPackageName;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Override
    public int getPermissionFlags(String string2, String string3, UserHandle userHandle) {
        try {
            int n = this.mPM.getPermissionFlags(string2, string3, userHandle.getIdentifier());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public PermissionGroupInfo getPermissionGroupInfo(String string2, int n) throws PackageManager.NameNotFoundException {
        try {
            PermissionGroupInfo permissionGroupInfo = this.mPM.getPermissionGroupInfo(string2, n);
            if (permissionGroupInfo != null) {
                return permissionGroupInfo;
            }
            throw new PackageManager.NameNotFoundException(string2);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public PermissionInfo getPermissionInfo(String string2, int n) throws PackageManager.NameNotFoundException {
        try {
            PermissionInfo permissionInfo = this.mPM.getPermissionInfo(string2, this.mContext.getOpPackageName(), n);
            if (permissionInfo != null) {
                return permissionInfo;
            }
            throw new PackageManager.NameNotFoundException(string2);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String string2) {
        try {
            int n = this.mPM.getPreferredActivities(list, list2, string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public List<PackageInfo> getPreferredPackages(int n) {
        Log.w(TAG, "getPreferredPackages() is a no-op");
        return Collections.emptyList();
    }

    @Override
    public List<VolumeInfo> getPrimaryStorageCandidateVolumes() {
        Object object = this.mContext.getSystemService(StorageManager.class);
        VolumeInfo volumeInfo = this.getPrimaryStorageCurrentVolume();
        Object object2 = ((StorageManager)object).getVolumes();
        ArrayList<VolumeInfo> arrayList = new ArrayList<VolumeInfo>();
        if (Objects.equals("primary_physical", ((StorageManager)object).getPrimaryStorageUuid()) && volumeInfo != null) {
            arrayList.add(volumeInfo);
        } else {
            object = object2.iterator();
            while (object.hasNext()) {
                object2 = (VolumeInfo)object.next();
                if (!Objects.equals(object2, volumeInfo) && !ApplicationPackageManager.isPrimaryStorageCandidateVolume((VolumeInfo)object2)) continue;
                arrayList.add((VolumeInfo)object2);
            }
        }
        return arrayList;
    }

    @Override
    public VolumeInfo getPrimaryStorageCurrentVolume() {
        StorageManager storageManager = this.mContext.getSystemService(StorageManager.class);
        return storageManager.findVolumeByQualifiedUuid(storageManager.getPrimaryStorageUuid());
    }

    @Override
    public ProviderInfo getProviderInfo(ComponentName componentName, int n) throws PackageManager.NameNotFoundException {
        int n2 = this.getUserId();
        try {
            ProviderInfo providerInfo = this.mPM.getProviderInfo(componentName, this.updateFlagsForComponent(n, n2, null), n2);
            if (providerInfo != null) {
                return providerInfo;
            }
            throw new PackageManager.NameNotFoundException(componentName.toString());
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public ActivityInfo getReceiverInfo(ComponentName componentName, int n) throws PackageManager.NameNotFoundException {
        int n2 = this.getUserId();
        try {
            ActivityInfo activityInfo = this.mPM.getReceiverInfo(componentName, this.updateFlagsForComponent(n, n2, null), n2);
            if (activityInfo != null) {
                return activityInfo;
            }
            throw new PackageManager.NameNotFoundException(componentName.toString());
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public Resources getResourcesForActivity(ComponentName componentName) throws PackageManager.NameNotFoundException {
        return this.getResourcesForApplication(this.getActivityInfo((ComponentName)componentName, (int)1024).applicationInfo);
    }

    @Override
    public Resources getResourcesForApplication(ApplicationInfo applicationInfo) throws PackageManager.NameNotFoundException {
        if (applicationInfo.packageName.equals("system")) {
            return this.mContext.mMainThread.getSystemUiContext().getResources();
        }
        boolean bl = applicationInfo.uid == Process.myUid();
        ActivityThread activityThread = this.mContext.mMainThread;
        Object object = bl ? applicationInfo.sourceDir : applicationInfo.publicSourceDir;
        String[] arrstring = bl ? applicationInfo.splitSourceDirs : applicationInfo.splitPublicSourceDirs;
        if ((object = activityThread.getTopLevelResources((String)object, arrstring, applicationInfo.resourceDirs, applicationInfo.sharedLibraryFiles, 0, this.mContext.mPackageInfo)) != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to open ");
        ((StringBuilder)object).append(applicationInfo.publicSourceDir);
        throw new PackageManager.NameNotFoundException(((StringBuilder)object).toString());
    }

    @Override
    public Resources getResourcesForApplication(String string2) throws PackageManager.NameNotFoundException {
        return this.getResourcesForApplication(this.getApplicationInfo(string2, 1024));
    }

    @Override
    public Resources getResourcesForApplicationAsUser(String object, int n) throws PackageManager.NameNotFoundException {
        if (n >= 0) {
            Object object2;
            block5 : {
                if ("system".equals(object)) {
                    return this.mContext.mMainThread.getSystemUiContext().getResources();
                }
                try {
                    object2 = this.mPM.getApplicationInfo((String)object, 1024, n);
                    if (object2 == null) break block5;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                object = this.getResourcesForApplication((ApplicationInfo)object2);
                return object;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Package ");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(" doesn't exist");
            throw new PackageManager.NameNotFoundException(((StringBuilder)object2).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Call does not support special user #");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public ServiceInfo getServiceInfo(ComponentName componentName, int n) throws PackageManager.NameNotFoundException {
        int n2 = this.getUserId();
        try {
            ServiceInfo serviceInfo = this.mPM.getServiceInfo(componentName, this.updateFlagsForComponent(n, n2, null), n2);
            if (serviceInfo != null) {
                return serviceInfo;
            }
            throw new PackageManager.NameNotFoundException(componentName.toString());
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String getServicesSystemSharedLibraryPackageName() {
        try {
            String string2 = this.mPM.getServicesSystemSharedLibraryPackageName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public List<SharedLibraryInfo> getSharedLibraries(int n) {
        return this.getSharedLibrariesAsUser(n, this.getUserId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<SharedLibraryInfo> getSharedLibrariesAsUser(int n, int n2) {
        try {
            ParceledListSlice parceledListSlice = this.mPM.getSharedLibraries(this.mContext.getOpPackageName(), n, n2);
            if (parceledListSlice != null) return parceledListSlice.getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    @Override
    public String getSharedSystemSharedLibraryPackageName() {
        try {
            String string2 = this.mPM.getSharedSystemSharedLibraryPackageName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public KeySet getSigningKeySet(String object) {
        Preconditions.checkNotNull(object);
        try {
            object = this.mPM.getSigningKeySet((String)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public Bundle getSuspendedPackageAppExtras() {
        try {
            BaseBundle baseBundle = this.mPM.getSuspendedPackageAppExtras(this.mContext.getOpPackageName(), this.getUserId());
            baseBundle = baseBundle != null ? new Bundle(baseBundle.deepCopy()) : null;
            return baseBundle;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean getSyntheticAppDetailsActivityEnabled(String string2) {
        try {
            boolean bl;
            ComponentName componentName = new ComponentName(string2, PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME);
            int n = this.mPM.getComponentEnabledSetting(componentName, this.getUserId());
            boolean bl2 = bl = true;
            if (n != 1) {
                bl2 = n == 0 ? bl : false;
            }
            return bl2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public FeatureInfo[] getSystemAvailableFeatures() {
        try {
            FeatureInfo[] arrfeatureInfo = this.mPM.getSystemAvailableFeatures();
            int n = 0;
            if (arrfeatureInfo == null) {
                return new FeatureInfo[0];
            }
            List list = arrfeatureInfo.getList();
            arrfeatureInfo = new FeatureInfo[list.size()];
            do {
                if (n >= arrfeatureInfo.length) {
                    return arrfeatureInfo;
                }
                arrfeatureInfo[n] = (FeatureInfo)list.get(n);
                ++n;
            } while (true);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String getSystemCaptionsServicePackageName() {
        try {
            String string2 = this.mPM.getSystemCaptionsServicePackageName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public String[] getSystemSharedLibraryNames() {
        try {
            String[] arrstring = this.mPM.getSystemSharedLibraryNames();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String getSystemTextClassifierPackageName() {
        try {
            String string2 = this.mPM.getSystemTextClassifierPackageName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public CharSequence getText(String string2, int n, ApplicationInfo object) {
        ResourceName resourceName = new ResourceName(string2, n);
        Object object2 = this.getCachedString(resourceName);
        if (object2 != null) {
            return object2;
        }
        object2 = object;
        if (object == null) {
            try {
                object2 = this.getApplicationInfo(string2, 1024);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return null;
            }
        }
        try {
            object = this.getResourcesForApplication((ApplicationInfo)object2).getText(n);
            this.putCachedString(resourceName, (CharSequence)object);
            return object;
        }
        catch (RuntimeException runtimeException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Failure retrieving text 0x");
            ((StringBuilder)object2).append(Integer.toHexString(n));
            ((StringBuilder)object2).append(" in package ");
            ((StringBuilder)object2).append(string2);
            Log.w("PackageManager", ((StringBuilder)object2).toString(), runtimeException);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failure retrieving resources for ");
            stringBuilder.append(((ApplicationInfo)object2).packageName);
            Log.w("PackageManager", stringBuilder.toString());
        }
        return null;
    }

    @Override
    public int getUidForSharedUser(String string2) throws PackageManager.NameNotFoundException {
        StringBuilder stringBuilder;
        try {
            int n = this.mPM.getUidForSharedUser(string2);
            if (n != -1) {
                return n;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("No shared userid for user:");
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        stringBuilder.append(string2);
        throw new PackageManager.NameNotFoundException(stringBuilder.toString());
    }

    @Override
    public String[] getUnsuspendablePackages(String[] arrstring) {
        try {
            arrstring = this.mPM.getUnsuspendablePackagesForUser(arrstring, this.mContext.getUserId());
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public Drawable getUserBadgeForDensity(UserHandle userHandle, int n) {
        Drawable drawable2 = this.getManagedProfileIconForDensity(userHandle, 17302362, n);
        if (drawable2 == null) {
            return null;
        }
        Drawable drawable3 = this.getDrawableForDensity(17302361, n);
        drawable3.setTint(this.getUserBadgeColor(userHandle));
        return new LayerDrawable(new Drawable[]{drawable2, drawable3});
    }

    @Override
    public Drawable getUserBadgeForDensityNoBackground(UserHandle userHandle, int n) {
        Drawable drawable2 = this.getManagedProfileIconForDensity(userHandle, 17302363, n);
        if (drawable2 != null) {
            drawable2.setTint(this.getUserBadgeColor(userHandle));
        }
        return drawable2;
    }

    @Override
    public Drawable getUserBadgedDrawableForDensity(Drawable drawable2, UserHandle object, Rect rect, int n) {
        if ((object = this.getUserBadgeForDensity((UserHandle)object, n)) == null) {
            return drawable2;
        }
        return this.getBadgedDrawable(drawable2, (Drawable)object, rect, true);
    }

    @Override
    public Drawable getUserBadgedIcon(Drawable drawable2, UserHandle userHandle) {
        if (!this.isManagedProfile(userHandle.getIdentifier())) {
            return drawable2;
        }
        return this.getBadgedDrawable(drawable2, new LauncherIcons(this.mContext).getBadgeDrawable(17302366, this.getUserBadgeColor(userHandle)), null, true);
    }

    @Override
    public CharSequence getUserBadgedLabel(CharSequence charSequence, UserHandle arrn) {
        if (this.isManagedProfile(arrn.getIdentifier())) {
            int n = this.getUserManager().getManagedProfileBadge(arrn.getIdentifier());
            arrn = CORP_BADGE_LABEL_RES_ID;
            n = arrn[n % arrn.length];
            return Resources.getSystem().getString(n, charSequence);
        }
        return charSequence;
    }

    @Override
    public int getUserId() {
        return this.mContext.getUserId();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    UserManager getUserManager() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mUserManager != null) return this.mUserManager;
            this.mUserManager = UserManager.get(this.mContext);
            return this.mUserManager;
        }
    }

    @Override
    public VerifierDeviceIdentity getVerifierDeviceIdentity() {
        try {
            VerifierDeviceIdentity verifierDeviceIdentity = this.mPM.getVerifierDeviceIdentity();
            return verifierDeviceIdentity;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String getWellbeingPackageName() {
        try {
            String string2 = this.mPM.getWellbeingPackageName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Set<String> getWhitelistedRestrictedPermissions(String collection, int n) {
        try {
            collection = this.mPM.getWhitelistedRestrictedPermissions((String)((Object)collection), n, this.getUserId());
            if (collection == null) return Collections.emptySet();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return new ArraySet<String>(collection);
    }

    @Override
    public XmlResourceParser getXml(String string2, int n, ApplicationInfo object) {
        ApplicationInfo applicationInfo = object;
        if (object == null) {
            try {
                applicationInfo = this.getApplicationInfo(string2, 1024);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return null;
            }
        }
        try {
            object = this.getResourcesForApplication(applicationInfo).getXml(n);
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failure retrieving resources for ");
            stringBuilder.append(applicationInfo.packageName);
            Log.w("PackageManager", stringBuilder.toString());
        }
        catch (RuntimeException runtimeException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failure retrieving xml 0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            ((StringBuilder)object).append(" in package ");
            ((StringBuilder)object).append(string2);
            Log.w("PackageManager", ((StringBuilder)object).toString(), runtimeException);
        }
        return null;
    }

    @Override
    public void grantRuntimePermission(String string2, String string3, UserHandle userHandle) {
        try {
            this.mPM.grantRuntimePermission(string2, string3, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean hasSigningCertificate(int n, byte[] arrby, int n2) {
        try {
            boolean bl = this.mPM.hasUidSigningCertificate(n, arrby, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean hasSigningCertificate(String string2, byte[] arrby, int n) {
        try {
            boolean bl = this.mPM.hasSigningCertificate(string2, arrby, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean hasSystemFeature(String string2) {
        return this.hasSystemFeature(string2, 0);
    }

    @Override
    public boolean hasSystemFeature(String string2, int n) {
        try {
            boolean bl = this.mPM.hasSystemFeature(string2, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public int installExistingPackage(String string2) throws PackageManager.NameNotFoundException {
        return this.installExistingPackage(string2, 0);
    }

    @Override
    public int installExistingPackage(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.installExistingPackageAsUser(string2, n, this.getUserId());
    }

    @Override
    public int installExistingPackageAsUser(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.installExistingPackageAsUser(string2, 0, n);
    }

    @VisibleForTesting
    protected boolean isAllow3rdPartyOnInternal(Context context) {
        return context.getResources().getBoolean(17891339);
    }

    @Override
    public boolean isDeviceUpgrading() {
        try {
            boolean bl = this.mPM.isDeviceUpgrading();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @VisibleForTesting
    protected boolean isForceAllowOnExternal(Context object) {
        object = ((Context)object).getContentResolver();
        boolean bl = false;
        if (Settings.Global.getInt((ContentResolver)object, "force_allow_on_external", 0) != 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean isInstantApp() {
        return this.isInstantApp(this.mContext.getPackageName());
    }

    @Override
    public boolean isInstantApp(String string2) {
        try {
            boolean bl = this.mPM.isInstantApp(string2, this.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean isPackageAvailable(String string2) {
        try {
            boolean bl = this.mPM.isPackageAvailable(string2, this.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean isPackageStateProtected(String string2, int n) {
        try {
            boolean bl = this.mPM.isPackageStateProtected(string2, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public boolean isPackageSuspended() {
        return this.isPackageSuspendedForUser(this.mContext.getOpPackageName(), this.getUserId());
    }

    @Override
    public boolean isPackageSuspended(String string2) throws PackageManager.NameNotFoundException {
        try {
            boolean bl = this.isPackageSuspendedForUser(string2, this.getUserId());
            return bl;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new PackageManager.NameNotFoundException(string2);
        }
    }

    @Override
    public boolean isPackageSuspendedForUser(String string2, int n) {
        try {
            boolean bl = this.mPM.isPackageSuspendedForUser(string2, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean isPermissionRevokedByPolicy(String string2, String string3) {
        try {
            boolean bl = this.mPM.isPermissionRevokedByPolicy(string2, string3, this.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isSafeMode() {
        try {
            int n = this.mCachedSafeMode;
            boolean bl = true;
            if (n < 0) {
                n = this.mPM.isSafeMode() ? 1 : 0;
                this.mCachedSafeMode = n;
            }
            if ((n = this.mCachedSafeMode) == 0) return false;
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean isSignedBy(String string2, KeySet keySet) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(keySet);
        try {
            boolean bl = this.mPM.isPackageSignedByKeySet(string2, keySet);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean isSignedByExactly(String string2, KeySet keySet) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(keySet);
        try {
            boolean bl = this.mPM.isPackageSignedByKeySetExactly(string2, keySet);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean isUpgrade() {
        return this.isDeviceUpgrading();
    }

    @Override
    public boolean isWirelessConsentModeEnabled() {
        return this.mContext.getResources().getBoolean(17891602);
    }

    @Override
    public Drawable loadItemIcon(PackageItemInfo packageItemInfo, ApplicationInfo object) {
        object = this.loadUnbadgedItemIcon(packageItemInfo, (ApplicationInfo)object);
        if (packageItemInfo.showUserIcon != -10000) {
            return object;
        }
        return this.getUserBadgedIcon((Drawable)object, new UserHandle(this.getUserId()));
    }

    @Override
    public Drawable loadUnbadgedItemIcon(PackageItemInfo packageItemInfo, ApplicationInfo object) {
        if (packageItemInfo.showUserIcon != -10000) {
            int n = packageItemInfo.showUserIcon;
            return UserIcons.getDefaultUserIcon(this.mContext.getResources(), n, false);
        }
        Drawable drawable2 = null;
        if (packageItemInfo.packageName != null) {
            drawable2 = this.getDrawable(packageItemInfo.packageName, packageItemInfo.icon, (ApplicationInfo)object);
        }
        Drawable drawable3 = drawable2;
        if (drawable2 == null) {
            drawable3 = drawable2;
            if (packageItemInfo != object) {
                drawable3 = drawable2;
                if (object != null) {
                    drawable3 = this.loadUnbadgedItemIcon((PackageItemInfo)object, (ApplicationInfo)object);
                }
            }
        }
        object = drawable3;
        if (drawable3 == null) {
            object = packageItemInfo.loadDefaultIcon(this);
        }
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int movePackage(String string2, VolumeInfo object) {
        block4 : {
            try {
                if ("private".equals(((VolumeInfo)object).id)) {
                    object = StorageManager.UUID_PRIVATE_INTERNAL;
                    return this.mPM.movePackage(string2, (String)object);
                }
                if (!((VolumeInfo)object).isPrimaryPhysical()) break block4;
                object = "primary_physical";
                return this.mPM.movePackage(string2, (String)object);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        object = Preconditions.checkNotNull(((VolumeInfo)object).fsUuid);
        return this.mPM.movePackage(string2, (String)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int movePrimaryStorage(VolumeInfo object) {
        block4 : {
            try {
                if ("private".equals(((VolumeInfo)object).id)) {
                    object = StorageManager.UUID_PRIVATE_INTERNAL;
                    return this.mPM.movePrimaryStorage((String)object);
                }
                if (!((VolumeInfo)object).isPrimaryPhysical()) break block4;
                object = "primary_physical";
                return this.mPM.movePrimaryStorage((String)object);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        object = Preconditions.checkNotNull(((VolumeInfo)object).fsUuid);
        return this.mPM.movePrimaryStorage((String)object);
    }

    @Override
    public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int n) {
        return this.queryBroadcastReceiversAsUser(intent, n, this.getUserId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<ResolveInfo> queryBroadcastReceiversAsUser(Intent object, int n, int n2) {
        try {
            object = this.mPM.queryIntentReceivers((Intent)object, ((Intent)object).resolveTypeIfNeeded(this.mContext.getContentResolver()), this.updateFlagsForComponent(n, n2, (Intent)object), n2);
            if (object != null) return ((ParceledListSlice)object).getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    @Override
    public List<ProviderInfo> queryContentProviders(String string2, int n, int n2) {
        return this.queryContentProviders(string2, n, n2, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<ProviderInfo> queryContentProviders(String list, int n, int n2, String string2) {
        try {
            list = this.mPM.queryContentProviders((String)((Object)list), n, this.updateFlagsForComponent(n2, UserHandle.getUserId(n), null), string2);
            if (list == null) return Collections.emptyList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return ((ParceledListSlice)((Object)list)).getList();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<InstrumentationInfo> queryInstrumentation(String object, int n) {
        try {
            object = this.mPM.queryInstrumentation((String)object, n);
            if (object != null) return ((ParceledListSlice)object).getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    @Override
    public List<ResolveInfo> queryIntentActivities(Intent intent, int n) {
        return this.queryIntentActivitiesAsUser(intent, n, this.getUserId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<ResolveInfo> queryIntentActivitiesAsUser(Intent object, int n, int n2) {
        try {
            object = this.mPM.queryIntentActivities((Intent)object, ((Intent)object).resolveTypeIfNeeded(this.mContext.getContentResolver()), this.updateFlagsForComponent(n, n2, (Intent)object), n2);
            if (object != null) return ((ParceledListSlice)object).getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<ResolveInfo> queryIntentActivityOptions(ComponentName object, Intent[] arrintent, Intent intent, int n) {
        int n2 = this.getUserId();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String[] arrstring = null;
        if (arrintent != null) {
            int n3 = arrintent.length;
            for (int i = 0; i < n3; ++i) {
                Object object2 = arrintent[i];
                String[] arrstring2 = arrstring;
                if (object2 != null) {
                    object2 = ((Intent)object2).resolveTypeIfNeeded(contentResolver);
                    arrstring2 = arrstring;
                    if (object2 != null) {
                        arrstring2 = arrstring;
                        if (arrstring == null) {
                            arrstring2 = new String[n3];
                        }
                        arrstring2[i] = object2;
                    }
                }
                arrstring = arrstring2;
            }
        } else {
            arrstring = null;
        }
        try {
            object = this.mPM.queryIntentActivityOptions((ComponentName)object, arrintent, arrstring, intent, intent.resolveTypeIfNeeded(contentResolver), this.updateFlagsForComponent(n, n2, intent), n2);
            if (object != null) return ((ParceledListSlice)object).getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    @Override
    public List<ResolveInfo> queryIntentContentProviders(Intent intent, int n) {
        return this.queryIntentContentProvidersAsUser(intent, n, this.getUserId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<ResolveInfo> queryIntentContentProvidersAsUser(Intent object, int n, int n2) {
        try {
            object = this.mPM.queryIntentContentProviders((Intent)object, ((Intent)object).resolveTypeIfNeeded(this.mContext.getContentResolver()), this.updateFlagsForComponent(n, n2, (Intent)object), n2);
            if (object != null) return ((ParceledListSlice)object).getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    @Override
    public List<ResolveInfo> queryIntentServices(Intent intent, int n) {
        return this.queryIntentServicesAsUser(intent, n, this.getUserId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<ResolveInfo> queryIntentServicesAsUser(Intent object, int n, int n2) {
        try {
            object = this.mPM.queryIntentServices((Intent)object, ((Intent)object).resolveTypeIfNeeded(this.mContext.getContentResolver()), this.updateFlagsForComponent(n, n2, (Intent)object), n2);
            if (object != null) return ((ParceledListSlice)object).getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    @Override
    public List<PermissionInfo> queryPermissionsByGroup(String string2, int n) throws PackageManager.NameNotFoundException {
        block3 : {
            Object object;
            try {
                object = this.mPM.queryPermissionsByGroup(string2, n);
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            object = ((ParceledListSlice)object).getList();
            if (object == null) break block3;
            return object;
        }
        throw new PackageManager.NameNotFoundException(string2);
    }

    @Override
    public void registerDexModule(String string2, PackageManager.DexModuleRegisterCallback dexModuleRegisterCallback) {
        Object object;
        boolean bl;
        block5 : {
            bl = false;
            try {
                object = Os.stat((String)string2);
                int n = OsConstants.S_IROTH;
                int n2 = object.st_mode;
                if ((n & n2) != 0) {
                    bl = true;
                }
                object = null;
                if (dexModuleRegisterCallback == null) break block5;
                object = new DexModuleRegisterCallbackDelegate(dexModuleRegisterCallback);
            }
            catch (ErrnoException errnoException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not get stat the module file: ");
                stringBuilder.append(errnoException.getMessage());
                dexModuleRegisterCallback.onDexModuleRegistered(string2, false, stringBuilder.toString());
                return;
            }
        }
        try {
            this.mPM.registerDexModule(this.mContext.getPackageName(), string2, bl, (IDexModuleRegisterCallback)object);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerMoveCallback(PackageManager.MoveCallback moveCallback, Handler handler) {
        ArrayList<MoveCallbackDelegate> arrayList = this.mDelegates;
        synchronized (arrayList) {
            MoveCallbackDelegate moveCallbackDelegate = new MoveCallbackDelegate(moveCallback, handler.getLooper());
            try {
                this.mPM.registerMoveCallback(moveCallbackDelegate);
                this.mDelegates.add(moveCallbackDelegate);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void removeOnPermissionsChangeListener(PackageManager.OnPermissionsChangedListener onPermissionsChangedListener) {
        Map<PackageManager.OnPermissionsChangedListener, IOnPermissionsChangeListener> map = this.mPermissionListeners;
        synchronized (map) {
            IOnPermissionsChangeListener iOnPermissionsChangeListener = this.mPermissionListeners.get(onPermissionsChangedListener);
            if (iOnPermissionsChangeListener != null) {
                try {
                    this.mPM.removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
                    this.mPermissionListeners.remove(onPermissionsChangedListener);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    @Override
    public void removePackageFromPreferred(String string2) {
        Log.w(TAG, "removePackageFromPreferred() is a no-op");
    }

    @Override
    public void removePermission(String string2) {
        try {
            this.mPM.removePermission(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean removeWhitelistedRestrictedPermission(String string2, String string3, int n) {
        try {
            boolean bl = this.mPM.removeWhitelistedRestrictedPermission(string2, string3, n, this.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void replacePreferredActivity(IntentFilter intentFilter, int n, ComponentName[] arrcomponentName, ComponentName componentName) {
        try {
            this.mPM.replacePreferredActivity(intentFilter, n, arrcomponentName, componentName, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void replacePreferredActivityAsUser(IntentFilter intentFilter, int n, ComponentName[] arrcomponentName, ComponentName componentName, int n2) {
        try {
            this.mPM.replacePreferredActivity(intentFilter, n, arrcomponentName, componentName, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public ResolveInfo resolveActivity(Intent intent, int n) {
        return this.resolveActivityAsUser(intent, n, this.getUserId());
    }

    @Override
    public ResolveInfo resolveActivityAsUser(Intent parcelable, int n, int n2) {
        try {
            parcelable = this.mPM.resolveIntent((Intent)parcelable, parcelable.resolveTypeIfNeeded(this.mContext.getContentResolver()), this.updateFlagsForComponent(n, n2, (Intent)parcelable), n2);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public ProviderInfo resolveContentProvider(String string2, int n) {
        return this.resolveContentProviderAsUser(string2, n, this.getUserId());
    }

    @Override
    public ProviderInfo resolveContentProviderAsUser(String object, int n, int n2) {
        try {
            object = this.mPM.resolveContentProvider((String)object, this.updateFlagsForComponent(n, n2, null), n2);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public ResolveInfo resolveService(Intent intent, int n) {
        return this.resolveServiceAsUser(intent, n, this.getUserId());
    }

    @Override
    public ResolveInfo resolveServiceAsUser(Intent parcelable, int n, int n2) {
        try {
            parcelable = this.mPM.resolveService((Intent)parcelable, parcelable.resolveTypeIfNeeded(this.mContext.getContentResolver()), this.updateFlagsForComponent(n, n2, (Intent)parcelable), n2);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void revokeRuntimePermission(String string2, String string3, UserHandle userHandle) {
        try {
            this.mPM.revokeRuntimePermission(string2, string3, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendDeviceCustomizationReadyBroadcast() {
        try {
            this.mPM.sendDeviceCustomizationReadyBroadcast();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public void setApplicationCategoryHint(String string2, int n) {
        try {
            this.mPM.setApplicationCategoryHint(string2, n, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void setApplicationEnabledSetting(String string2, int n, int n2) {
        try {
            this.mPM.setApplicationEnabledSetting(string2, n, n2, this.getUserId(), this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean setApplicationHiddenSettingAsUser(String string2, boolean bl, UserHandle userHandle) {
        try {
            bl = this.mPM.setApplicationHiddenSettingAsUser(string2, bl, userHandle.getIdentifier());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void setComponentEnabledSetting(ComponentName componentName, int n, int n2) {
        try {
            this.mPM.setComponentEnabledSetting(componentName, n, n2, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean setDefaultBrowserPackageNameAsUser(String string2, int n) {
        try {
            boolean bl = this.mPM.setDefaultBrowserPackageName(string2, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String[] setDistractingPackageRestrictions(String[] arrstring, int n) {
        try {
            arrstring = this.mPM.setDistractingPackageRestrictionsAsUser(arrstring, n, this.mContext.getUserId());
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void setHarmfulAppWarning(String string2, CharSequence charSequence) {
        try {
            this.mPM.setHarmfulAppWarning(string2, charSequence, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    @Override
    public void setInstallerPackageName(String string2, String string3) {
        try {
            this.mPM.setInstallerPackageName(string2, string3);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    @Override
    public boolean setInstantAppCookie(byte[] arrby) {
        try {
            boolean bl = this.mPM.setInstantAppCookie(this.mContext.getPackageName(), arrby, this.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String[] setPackagesSuspended(String[] arrstring, boolean bl, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo) {
        try {
            arrstring = this.mPM.setPackagesSuspendedAsUser(arrstring, bl, persistableBundle, persistableBundle2, suspendDialogInfo, this.mContext.getOpPackageName(), this.getUserId());
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public String[] setPackagesSuspended(String[] arrstring, boolean bl, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, String object) {
        object = !TextUtils.isEmpty((CharSequence)object) ? new SuspendDialogInfo.Builder().setMessage((String)object).build() : null;
        return this.setPackagesSuspended(arrstring, bl, persistableBundle, persistableBundle2, (SuspendDialogInfo)object);
    }

    @Override
    public void setSyntheticAppDetailsActivityEnabled(String object, boolean bl) {
        ComponentName componentName;
        int n;
        try {
            componentName = new ComponentName((String)object, PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME);
            object = this.mPM;
            n = bl ? 0 : 2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        object.setComponentEnabledSetting(componentName, n, 1, this.getUserId());
    }

    @Override
    public void setUpdateAvailable(String string2, boolean bl) {
        try {
            this.mPM.setUpdateAvailable(string2, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    @Override
    public boolean shouldShowRequestPermissionRationale(String string2) {
        try {
            boolean bl = this.mPM.shouldShowRequestPermissionRationale(string2, this.mContext.getPackageName(), this.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterMoveCallback(PackageManager.MoveCallback moveCallback) {
        ArrayList<MoveCallbackDelegate> arrayList = this.mDelegates;
        synchronized (arrayList) {
            Iterator<MoveCallbackDelegate> iterator = this.mDelegates.iterator();
            while (iterator.hasNext()) {
                MoveCallbackDelegate moveCallbackDelegate = iterator.next();
                PackageManager.MoveCallback moveCallback2 = moveCallbackDelegate.mCallback;
                if (moveCallback2 != moveCallback) continue;
                try {
                    this.mPM.unregisterMoveCallback(moveCallbackDelegate);
                    iterator.remove();
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    @Override
    public void updateInstantAppCookie(byte[] object) {
        if (object != null && ((byte[])object).length > this.getInstantAppCookieMaxBytes()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("instant cookie longer than ");
            ((StringBuilder)object).append(this.getInstantAppCookieMaxBytes());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        try {
            this.mPM.setInstantAppCookie(this.mContext.getPackageName(), (byte[])object, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public boolean updateIntentVerificationStatusAsUser(String string2, int n, int n2) {
        try {
            boolean bl = this.mPM.updateIntentVerificationStatus(string2, n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void updatePermissionFlags(String string2, String string3, int n, int n2, UserHandle userHandle) {
        boolean bl;
        IPackageManager iPackageManager;
        try {
            iPackageManager = this.mPM;
            bl = this.mContext.getApplicationInfo().targetSdkVersion >= 29;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        iPackageManager.updatePermissionFlags(string2, string3, n, n2, bl, userHandle.getIdentifier());
    }

    @Override
    public void verifyIntentFilter(int n, int n2, List<String> list) {
        try {
            this.mPM.verifyIntentFilter(n, n2, list);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void verifyPendingInstall(int n, int n2) {
        try {
            this.mPM.verifyPendingInstall(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private static class DexModuleRegisterCallbackDelegate
    extends IDexModuleRegisterCallback.Stub
    implements Handler.Callback {
        private static final int MSG_DEX_MODULE_REGISTERED = 1;
        private final PackageManager.DexModuleRegisterCallback callback;
        private final Handler mHandler;

        DexModuleRegisterCallbackDelegate(PackageManager.DexModuleRegisterCallback dexModuleRegisterCallback) {
            this.callback = dexModuleRegisterCallback;
            this.mHandler = new Handler(Looper.getMainLooper(), this);
        }

        @Override
        public boolean handleMessage(Message object) {
            if (((Message)object).what != 1) {
                return false;
            }
            object = (DexModuleRegisterResult)((Message)object).obj;
            this.callback.onDexModuleRegistered(((DexModuleRegisterResult)object).dexModulePath, ((DexModuleRegisterResult)object).success, ((DexModuleRegisterResult)object).message);
            return true;
        }

        @Override
        public void onDexModuleRegistered(String string2, boolean bl, String string3) throws RemoteException {
            this.mHandler.obtainMessage(1, new DexModuleRegisterResult(string2, bl, string3)).sendToTarget();
        }
    }

    private static class DexModuleRegisterResult {
        final String dexModulePath;
        final String message;
        final boolean success;

        private DexModuleRegisterResult(String string2, boolean bl, String string3) {
            this.dexModulePath = string2;
            this.success = bl;
            this.message = string3;
        }
    }

    private static class MoveCallbackDelegate
    extends IPackageMoveObserver.Stub
    implements Handler.Callback {
        private static final int MSG_CREATED = 1;
        private static final int MSG_STATUS_CHANGED = 2;
        final PackageManager.MoveCallback mCallback;
        final Handler mHandler;

        public MoveCallbackDelegate(PackageManager.MoveCallback moveCallback, Looper looper) {
            this.mCallback = moveCallback;
            this.mHandler = new Handler(looper, this);
        }

        @Override
        public boolean handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n != 1) {
                if (n != 2) {
                    return false;
                }
                object = (SomeArgs)((Message)object).obj;
                this.mCallback.onStatusChanged(((SomeArgs)object).argi1, ((SomeArgs)object).argi2, (Long)((SomeArgs)object).arg3);
                ((SomeArgs)object).recycle();
                return true;
            }
            object = (SomeArgs)((Message)object).obj;
            this.mCallback.onCreated(((SomeArgs)object).argi1, (Bundle)((SomeArgs)object).arg2);
            ((SomeArgs)object).recycle();
            return true;
        }

        @Override
        public void onCreated(int n, Bundle bundle) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.argi1 = n;
            someArgs.arg2 = bundle;
            this.mHandler.obtainMessage(1, someArgs).sendToTarget();
        }

        @Override
        public void onStatusChanged(int n, int n2, long l) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.argi1 = n;
            someArgs.argi2 = n2;
            someArgs.arg3 = l;
            this.mHandler.obtainMessage(2, someArgs).sendToTarget();
        }
    }

    public class OnPermissionsChangeListenerDelegate
    extends IOnPermissionsChangeListener.Stub
    implements Handler.Callback {
        private static final int MSG_PERMISSIONS_CHANGED = 1;
        private final Handler mHandler;
        private final PackageManager.OnPermissionsChangedListener mListener;

        public OnPermissionsChangeListenerDelegate(PackageManager.OnPermissionsChangedListener onPermissionsChangedListener, Looper looper) {
            this.mListener = onPermissionsChangedListener;
            this.mHandler = new Handler(looper, this);
        }

        @Override
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            int n = message.arg1;
            this.mListener.onPermissionsChanged(n);
            return true;
        }

        @Override
        public void onPermissionsChanged(int n) {
            this.mHandler.obtainMessage(1, n, 0).sendToTarget();
        }
    }

    private static final class ResourceName {
        final int iconId;
        final String packageName;

        ResourceName(ApplicationInfo applicationInfo, int n) {
            this(applicationInfo.packageName, n);
        }

        ResourceName(ComponentInfo componentInfo, int n) {
            this(componentInfo.applicationInfo.packageName, n);
        }

        ResourceName(ResolveInfo resolveInfo, int n) {
            this(resolveInfo.activityInfo.applicationInfo.packageName, n);
        }

        ResourceName(String string2, int n) {
            this.packageName = string2;
            this.iconId = n;
        }

        public boolean equals(Object object) {
            block5 : {
                boolean bl;
                block7 : {
                    block8 : {
                        block6 : {
                            bl = true;
                            if (this == object) {
                                return true;
                            }
                            if (object == null || this.getClass() != object.getClass()) break block5;
                            object = (ResourceName)object;
                            if (this.iconId != ((ResourceName)object).iconId) {
                                return false;
                            }
                            String string2 = this.packageName;
                            if (string2 == null) break block6;
                            if (string2.equals(((ResourceName)object).packageName)) break block7;
                            break block8;
                        }
                        if (((ResourceName)object).packageName == null) break block7;
                    }
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return this.packageName.hashCode() * 31 + this.iconId;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ResourceName ");
            stringBuilder.append(this.packageName);
            stringBuilder.append(" / ");
            stringBuilder.append(this.iconId);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

