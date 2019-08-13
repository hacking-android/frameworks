/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.-$
 *  android.app.-$$Lambda
 *  android.app.-$$Lambda$ResourcesManager
 *  android.app.-$$Lambda$ResourcesManager$QJ7UiVk_XS90KuXAsIjIEym1DnM
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.-$;
import android.app.ApplicationPackageManager;
import android.app._$$Lambda$ResourcesManager$QJ7UiVk_XS90KuXAsIjIEym1DnM;
import android.content.pm.ApplicationInfo;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.content.res.CompatResources;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.ResourcesImpl;
import android.content.res.ResourcesKey;
import android.hardware.display.DisplayManagerGlobal;
import android.os.IBinder;
import android.os.Process;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.util.Pair;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayAdjustments;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IndentingPrintWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.Predicate;

public class ResourcesManager {
    private static final boolean DEBUG = false;
    private static final boolean ENABLE_APK_ASSETS_CACHE = false;
    static final String TAG = "ResourcesManager";
    private static final Predicate<WeakReference<Resources>> sEmptyReferencePredicate = _$$Lambda$ResourcesManager$QJ7UiVk_XS90KuXAsIjIEym1DnM.INSTANCE;
    private static ResourcesManager sResourcesManager;
    @UnsupportedAppUsage
    private final WeakHashMap<IBinder, ActivityResources> mActivityResourceReferences = new WeakHashMap();
    private final ArrayMap<Pair<Integer, DisplayAdjustments>, WeakReference<Display>> mAdjustedDisplays = new ArrayMap();
    private final ArrayMap<ApkKey, WeakReference<ApkAssets>> mCachedApkAssets = new ArrayMap();
    private final LruCache<ApkKey, ApkAssets> mLoadedApkAssets = null;
    private CompatibilityInfo mResCompatibilityInfo;
    @UnsupportedAppUsage
    private final Configuration mResConfiguration = new Configuration();
    @UnsupportedAppUsage
    private final ArrayMap<ResourcesKey, WeakReference<ResourcesImpl>> mResourceImpls = new ArrayMap();
    @UnsupportedAppUsage
    private final ArrayList<WeakReference<Resources>> mResourceReferences = new ArrayList();

    private static void applyNonDefaultDisplayMetricsToConfiguration(DisplayMetrics displayMetrics, Configuration configuration) {
        configuration.touchscreen = 1;
        configuration.densityDpi = displayMetrics.densityDpi;
        configuration.screenWidthDp = (int)((float)displayMetrics.widthPixels / displayMetrics.density);
        configuration.screenHeightDp = (int)((float)displayMetrics.heightPixels / displayMetrics.density);
        int n = Configuration.resetScreenLayout(configuration.screenLayout);
        if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
            configuration.orientation = 2;
            configuration.screenLayout = Configuration.reduceScreenLayout(n, configuration.screenWidthDp, configuration.screenHeightDp);
        } else {
            configuration.orientation = 1;
            configuration.screenLayout = Configuration.reduceScreenLayout(n, configuration.screenHeightDp, configuration.screenWidthDp);
        }
        configuration.smallestScreenWidthDp = Math.min(configuration.screenWidthDp, configuration.screenHeightDp);
        configuration.compatScreenWidthDp = configuration.screenWidthDp;
        configuration.compatScreenHeightDp = configuration.screenHeightDp;
        configuration.compatSmallestScreenWidthDp = configuration.smallestScreenWidthDp;
    }

    private void cleanupResourceImpl(ResourcesKey object) {
        if ((object = (ResourcesImpl)this.mResourceImpls.remove(object).get()) != null) {
            ((ResourcesImpl)object).flushLayoutCache();
        }
    }

    private static <T> int countLiveReferences(Collection<WeakReference<T>> weakReference) {
        int n = 0;
        Iterator<WeakReference<T>> iterator = weakReference.iterator();
        while (iterator.hasNext()) {
            weakReference = iterator.next();
            weakReference = weakReference != null ? weakReference.get() : null;
            int n2 = n;
            if (weakReference != null) {
                n2 = n + 1;
            }
            n = n2;
        }
        return n;
    }

    private ResourcesImpl createResourcesImpl(ResourcesKey resourcesKey) {
        DisplayAdjustments displayAdjustments = new DisplayAdjustments(resourcesKey.mOverrideConfiguration);
        displayAdjustments.setCompatibilityInfo(resourcesKey.mCompatInfo);
        AssetManager assetManager = this.createAssetManager(resourcesKey);
        if (assetManager == null) {
            return null;
        }
        DisplayMetrics displayMetrics = this.getDisplayMetrics(resourcesKey.mDisplayId, displayAdjustments);
        return new ResourcesImpl(assetManager, displayMetrics, this.generateConfig(resourcesKey, displayMetrics), displayAdjustments);
    }

    private ResourcesKey findKeyForResourceImplLocked(ResourcesImpl resourcesImpl) {
        int n = this.mResourceImpls.size();
        int n2 = 0;
        do {
            ResourcesImpl resourcesImpl2 = null;
            if (n2 >= n) break;
            WeakReference<ResourcesImpl> weakReference = this.mResourceImpls.valueAt(n2);
            if (weakReference != null) {
                resourcesImpl2 = (ResourcesImpl)weakReference.get();
            }
            if (resourcesImpl2 != null && resourcesImpl == resourcesImpl2) {
                return this.mResourceImpls.keyAt(n2);
            }
            ++n2;
        } while (true);
        return null;
    }

    private ResourcesImpl findOrCreateResourcesImplForKeyLocked(ResourcesKey resourcesKey) {
        ResourcesImpl resourcesImpl;
        ResourcesImpl resourcesImpl2 = resourcesImpl = this.findResourcesImplForKeyLocked(resourcesKey);
        if (resourcesImpl == null) {
            resourcesImpl2 = resourcesImpl = this.createResourcesImpl(resourcesKey);
            if (resourcesImpl != null) {
                this.mResourceImpls.put(resourcesKey, new WeakReference<ResourcesImpl>(resourcesImpl));
                resourcesImpl2 = resourcesImpl;
            }
        }
        return resourcesImpl2;
    }

    private ResourcesImpl findResourcesImplForKeyLocked(ResourcesKey object) {
        if ((object = (object = this.mResourceImpls.get(object)) != null ? (ResourcesImpl)((Reference)object).get() : null) != null && ((ResourcesImpl)object).getAssets().isUpToDate()) {
            return object;
        }
        return null;
    }

    private Configuration generateConfig(ResourcesKey resourcesKey, DisplayMetrics object) {
        boolean bl = resourcesKey.mDisplayId == 0;
        boolean bl2 = resourcesKey.hasOverrideConfiguration();
        if (bl && !bl2) {
            object = this.getConfiguration();
        } else {
            Configuration configuration = new Configuration(this.getConfiguration());
            if (!bl) {
                ResourcesManager.applyNonDefaultDisplayMetricsToConfiguration((DisplayMetrics)object, configuration);
            }
            object = configuration;
            if (bl2) {
                configuration.updateFrom(resourcesKey.mOverrideConfiguration);
                object = configuration;
            }
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Display getAdjustedDisplay(int n, DisplayAdjustments object) {
        object = object != null ? new DisplayAdjustments((DisplayAdjustments)object) : new DisplayAdjustments();
        object = Pair.create(n, object);
        synchronized (this) {
            Object object2 = this.mAdjustedDisplays.get(object);
            if (object2 != null && (object2 = (Display)((Reference)object2).get()) != null) {
                return object2;
            }
            object2 = DisplayManagerGlobal.getInstance();
            if (object2 == null) {
                return null;
            }
            Display display = ((DisplayManagerGlobal)object2).getCompatibleDisplay(n, (DisplayAdjustments)((Pair)object).second);
            if (display != null) {
                object2 = this.mAdjustedDisplays;
                WeakReference<Display> weakReference = new WeakReference<Display>(display);
                ((ArrayMap)object2).put((Pair<Integer, DisplayAdjustments>)object, weakReference);
            }
            return display;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static ResourcesManager getInstance() {
        synchronized (ResourcesManager.class) {
            ResourcesManager resourcesManager;
            if (sResourcesManager != null) return sResourcesManager;
            sResourcesManager = resourcesManager = new ResourcesManager();
            return sResourcesManager;
        }
    }

    private ActivityResources getOrCreateActivityResourcesStructLocked(IBinder iBinder) {
        ActivityResources activityResources;
        ActivityResources activityResources2 = activityResources = this.mActivityResourceReferences.get(iBinder);
        if (activityResources == null) {
            activityResources2 = new ActivityResources();
            this.mActivityResourceReferences.put(iBinder, activityResources2);
        }
        return activityResources2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Resources getOrCreateResources(IBinder object, ResourcesKey resourcesKey, ClassLoader classLoader) {
        synchronized (this) {
            Object object2;
            ArrayMap<ResourcesKey, WeakReference<ResourcesImpl>> arrayMap;
            if (object != null) {
                arrayMap = this.getOrCreateActivityResourcesStructLocked((IBinder)object);
                ArrayUtils.unstableRemoveIf(((ActivityResources)arrayMap).activityResources, sEmptyReferencePredicate);
                if (resourcesKey.hasOverrideConfiguration() && !((ActivityResources)arrayMap).overrideConfig.equals(Configuration.EMPTY)) {
                    object2 = new Configuration(((ActivityResources)arrayMap).overrideConfig);
                    ((Configuration)object2).updateFrom(resourcesKey.mOverrideConfiguration);
                    resourcesKey.mOverrideConfiguration.setTo((Configuration)object2);
                }
                if ((arrayMap = this.findResourcesImplForKeyLocked(resourcesKey)) != null) {
                    return this.getOrCreateResourcesForActivityLocked((IBinder)object, classLoader, (ResourcesImpl)((Object)arrayMap), resourcesKey.mCompatInfo);
                }
            } else {
                ArrayUtils.unstableRemoveIf(this.mResourceReferences, sEmptyReferencePredicate);
                arrayMap = this.findResourcesImplForKeyLocked(resourcesKey);
                if (arrayMap != null) {
                    return this.getOrCreateResourcesLocked(classLoader, (ResourcesImpl)((Object)arrayMap), resourcesKey.mCompatInfo);
                }
            }
            if ((object2 = this.createResourcesImpl(resourcesKey)) == null) {
                return null;
            }
            arrayMap = this.mResourceImpls;
            WeakReference<Object> weakReference = new WeakReference<Object>(object2);
            arrayMap.put(resourcesKey, weakReference);
            if (object == null) return this.getOrCreateResourcesLocked(classLoader, (ResourcesImpl)object2, resourcesKey.mCompatInfo);
            return this.getOrCreateResourcesForActivityLocked((IBinder)object, classLoader, (ResourcesImpl)object2, resourcesKey.mCompatInfo);
        }
    }

    private Resources getOrCreateResourcesForActivityLocked(IBinder object, ClassLoader classLoader, ResourcesImpl resourcesImpl, CompatibilityInfo compatibilityInfo) {
        ActivityResources activityResources = this.getOrCreateActivityResourcesStructLocked((IBinder)object);
        int n = activityResources.activityResources.size();
        for (int i = 0; i < n; ++i) {
            object = (Resources)activityResources.activityResources.get(i).get();
            if (object == null || !Objects.equals(((Resources)object).getClassLoader(), classLoader) || ((Resources)object).getImpl() != resourcesImpl) continue;
            return object;
        }
        object = compatibilityInfo.needsCompatResources() ? new CompatResources(classLoader) : new Resources(classLoader);
        ((Resources)object).setImpl(resourcesImpl);
        activityResources.activityResources.add(new WeakReference<Object>(object));
        return object;
    }

    private Resources getOrCreateResourcesLocked(ClassLoader object, ResourcesImpl resourcesImpl, CompatibilityInfo compatibilityInfo) {
        int n = this.mResourceReferences.size();
        for (int i = 0; i < n; ++i) {
            Resources resources = (Resources)this.mResourceReferences.get(i).get();
            if (resources == null || !Objects.equals(resources.getClassLoader(), object) || resources.getImpl() != resourcesImpl) continue;
            return resources;
        }
        object = compatibilityInfo.needsCompatResources() ? new CompatResources((ClassLoader)object) : new Resources((ClassLoader)object);
        ((Resources)object).setImpl(resourcesImpl);
        this.mResourceReferences.add(new WeakReference<Object>(object));
        return object;
    }

    static /* synthetic */ boolean lambda$static$0(WeakReference weakReference) {
        boolean bl = weakReference == null || weakReference.get() == null;
        return bl;
    }

    private ApkAssets loadApkAssets(String lruCache, boolean bl, boolean bl2) throws IOException {
        ApkKey apkKey = new ApkKey((String)((Object)lruCache), bl, bl2);
        LruCache<ApkKey, ApkAssets> lruCache2 = this.mLoadedApkAssets;
        if (lruCache2 != null && (lruCache2 = lruCache2.get(apkKey)) != null) {
            return lruCache2;
        }
        lruCache2 = this.mCachedApkAssets.get(apkKey);
        if (lruCache2 != null) {
            if ((lruCache2 = (ApkAssets)((Reference)((Object)lruCache2)).get()) != null) {
                lruCache = this.mLoadedApkAssets;
                if (lruCache != null) {
                    lruCache.put(apkKey, (ApkAssets)((Object)lruCache2));
                }
                return lruCache2;
            }
            this.mCachedApkAssets.remove(apkKey);
        }
        lruCache = bl2 ? ApkAssets.loadOverlayFromPath(ResourcesManager.overlayPathToIdmapPath((String)((Object)lruCache)), false) : ApkAssets.loadFromPath((String)((Object)lruCache), false, bl);
        lruCache2 = this.mLoadedApkAssets;
        if (lruCache2 != null) {
            lruCache2.put(apkKey, (ApkAssets)((Object)lruCache));
        }
        this.mCachedApkAssets.put(apkKey, new WeakReference<LruCache<ApkKey, ApkAssets>>(lruCache));
        return lruCache;
    }

    private static String overlayPathToIdmapPath(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/data/resource-cache/");
        stringBuilder.append(string2.substring(1).replace('/', '@'));
        stringBuilder.append("@idmap");
        return stringBuilder.toString();
    }

    private void redirectResourcesToNewImplLocked(ArrayMap<ResourcesImpl, ResourcesKey> arrayMap) {
        WeakReference<Resources> weakReference;
        if (arrayMap.isEmpty()) {
            return;
        }
        int n = this.mResourceReferences.size();
        int n2 = 0;
        do {
            ResourcesKey resourcesKey;
            weakReference = null;
            if (n2 >= n) break;
            WeakReference<Resources> weakReference2 = this.mResourceReferences.get(n2);
            if (weakReference2 != null) {
                weakReference = (Resources)weakReference2.get();
            }
            if (weakReference != null && (resourcesKey = arrayMap.get(((Resources)((Object)weakReference)).getImpl())) != null) {
                ResourcesImpl resourcesImpl = this.findOrCreateResourcesImplForKeyLocked(resourcesKey);
                if (resourcesImpl != null) {
                    ((Resources)((Object)weakReference)).setImpl(resourcesImpl);
                } else {
                    throw new Resources.NotFoundException("failed to redirect ResourcesImpl");
                }
            }
            ++n2;
        } while (true);
        for (ActivityResources activityResources : this.mActivityResourceReferences.values()) {
            n = activityResources.activityResources.size();
            for (n2 = 0; n2 < n; ++n2) {
                Object object;
                weakReference = activityResources.activityResources.get(n2);
                weakReference = weakReference != null ? (Resources)weakReference.get() : null;
                if (weakReference == null || (object = arrayMap.get(((Resources)((Object)weakReference)).getImpl())) == null) continue;
                if ((object = this.findOrCreateResourcesImplForKeyLocked((ResourcesKey)object)) != null) {
                    ((Resources)((Object)weakReference)).setImpl((ResourcesImpl)object);
                    continue;
                }
                throw new Resources.NotFoundException("failed to redirect ResourcesImpl");
            }
        }
    }

    @UnsupportedAppUsage
    public void appendLibAssetForMainAssetPath(String string2, String string3) {
        this.appendLibAssetsForMainAssetPath(string2, new String[]{string3});
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void appendLibAssetsForMainAssetPath(String var1_1, String[] var2_4) {
        // MONITORENTER : this
        var3_4 = new ArrayMap<ResourcesImpl, ResourcesKey>();
        var4_5 = this.mResourceImpls.size();
        var5_6 = 0;
        do {
            block8 : {
                block9 : {
                    var6_7 = var2_3;
                    if (var5_6 >= var4_5) ** GOTO lbl18
                    var7_8 = this.mResourceImpls.keyAt(var5_6);
                    var8_9 = this.mResourceImpls.valueAt(var5_6);
                    var8_9 = var8_9 != null ? (ResourcesImpl)var8_9.get() : null;
                    if (var8_9 == null) break block8;
                    var9_10 = var7_8.mResDir;
                    if (!Objects.equals(var9_10, var1_1)) break block8;
                    var9_10 = var7_8.mLibDirs;
                    var10_11 = ((void)var6_7).length;
                    break block9;
lbl18: // 1 sources:
                    this.redirectResourcesToNewImplLocked(var3_4);
                    // MONITOREXIT : this
                    return;
                }
                for (var11_12 = 0; var11_12 < var10_11; ++var11_12) {
                    var9_10 = (String[])ArrayUtils.appendElement(String.class, var9_10, var6_7[var11_12]);
                }
                if (var9_10 == var7_8.mLibDirs) break block8;
                var6_7 = new ResourcesKey(var7_8.mResDir, var7_8.mSplitResDirs, var7_8.mOverlayDirs, var9_10, var7_8.mDisplayId, var7_8.mOverrideConfiguration, var7_8.mCompatInfo);
                var3_4.put((ResourcesImpl)var8_9, var6_7);
            }
            ++var5_6;
        } while (true);
        catch (Throwable var1_2) {
            throw var1_2;
        }
    }

    public boolean applyCompatConfigurationLocked(int n, Configuration configuration) {
        CompatibilityInfo compatibilityInfo = this.mResCompatibilityInfo;
        if (compatibilityInfo != null && !compatibilityInfo.supportsScreen()) {
            this.mResCompatibilityInfo.applyToConfiguration(n, configuration);
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean applyConfigurationToResourcesLocked(Configuration configuration, CompatibilityInfo compatibilityInfo) {
        DisplayMetrics displayMetrics;
        int n;
        int n2;
        boolean bl;
        block15 : {
            block16 : {
                block14 : {
                    try {
                        Trace.traceBegin(8192L, "ResourcesManager#applyConfigurationToResourcesLocked");
                        bl = this.mResConfiguration.isOtherSeqNewer(configuration);
                        if (bl || compatibilityInfo != null) break block14;
                    }
                    catch (Throwable throwable) {
                        Trace.traceEnd(8192L);
                        throw throwable;
                    }
                    Trace.traceEnd(8192L);
                    return false;
                }
                n = this.mResConfiguration.updateFrom(configuration);
                this.mAdjustedDisplays.clear();
                displayMetrics = this.getDisplayMetrics();
                n2 = n;
                if (compatibilityInfo == null) break block15;
                if (this.mResCompatibilityInfo == null) break block16;
                n2 = n;
                if (this.mResCompatibilityInfo.equals(compatibilityInfo)) break block15;
            }
            this.mResCompatibilityInfo = compatibilityInfo;
            n2 = n | 3328;
        }
        Resources.updateSystemConfiguration(configuration, displayMetrics, compatibilityInfo);
        ApplicationPackageManager.configurationChanged();
        Object object = null;
        for (n = this.mResourceImpls.size() - 1; n >= 0; --n) {
            ResourcesKey resourcesKey = this.mResourceImpls.keyAt(n);
            WeakReference<ResourcesImpl> weakReference = this.mResourceImpls.valueAt(n);
            ResourcesImpl resourcesImpl = weakReference != null ? (ResourcesImpl)weakReference.get() : null;
            if (resourcesImpl != null) {
                int n3 = resourcesKey.mDisplayId;
                boolean bl2 = n3 == 0;
                bl = resourcesKey.hasOverrideConfiguration();
                if (bl2 && !bl) {
                    resourcesImpl.updateConfiguration(configuration, displayMetrics, compatibilityInfo);
                    continue;
                }
                weakReference = object;
                if (object == null) {
                    weakReference = new Configuration();
                }
                ((Configuration)((Object)weakReference)).setTo(configuration);
                DisplayAdjustments displayAdjustments = resourcesImpl.getDisplayAdjustments();
                if (compatibilityInfo != null) {
                    object = new DisplayAdjustments(displayAdjustments);
                    ((DisplayAdjustments)object).setCompatibilityInfo(compatibilityInfo);
                } else {
                    object = displayAdjustments;
                }
                object = this.getDisplayMetrics(n3, (DisplayAdjustments)object);
                if (!bl2) {
                    ResourcesManager.applyNonDefaultDisplayMetricsToConfiguration((DisplayMetrics)object, (Configuration)((Object)weakReference));
                }
                if (bl) {
                    ((Configuration)((Object)weakReference)).updateFrom(resourcesKey.mOverrideConfiguration);
                }
                resourcesImpl.updateConfiguration((Configuration)((Object)weakReference), (DisplayMetrics)object, compatibilityInfo);
                object = weakReference;
                continue;
            }
            this.mResourceImpls.removeAt(n);
        }
        bl = n2 != 0;
        Trace.traceEnd(8192L);
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    final void applyNewResourceDirsLocked(ApplicationInfo object, String[] arrstring) {
        void var1_4;
        block14 : {
            Trace.traceBegin(8192L, "ResourcesManager#applyNewResourceDirsLocked");
            Object object2 = ((ApplicationInfo)object).getBaseCodePath();
            int n = Process.myUid();
            Object object3 = ((ApplicationInfo)object).uid == n ? ((ApplicationInfo)object).splitSourceDirs : ((ApplicationInfo)object).splitPublicSourceDirs;
            String[] arrstring2 = ArrayUtils.cloneOrNull(object3);
            String[] arrstring3 = ArrayUtils.cloneOrNull(((ApplicationInfo)object).resourceDirs);
            ArrayMap<ResourcesImpl, ResourcesKey> arrayMap = new ArrayMap<ResourcesImpl, ResourcesKey>();
            n = this.mResourceImpls.size();
            object = object2;
            for (int i = 0; i < n; ++i) {
                Object object4;
                block13 : {
                    block12 : {
                        block11 : {
                            object2 = this.mResourceImpls.keyAt(i);
                            object3 = this.mResourceImpls.valueAt(i);
                            if (object3 == null) break block11;
                            object3 = (ResourcesImpl)((Reference)object3).get();
                            break block12;
                        }
                        object3 = null;
                    }
                    if (object3 == null) continue;
                    object4 = ((ResourcesKey)object2).mResDir;
                    if (object4 == null) break block13;
                    try {
                        if (((ResourcesKey)object2).mResDir.equals(object)) break block13;
                        object4 = ((ResourcesKey)object2).mResDir;
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    if (!ArrayUtils.contains(arrstring, object4)) continue;
                }
                object4 = new ResourcesKey((String)object, arrstring2, arrstring3, ((ResourcesKey)object2).mLibDirs, ((ResourcesKey)object2).mDisplayId, ((ResourcesKey)object2).mOverrideConfiguration, ((ResourcesKey)object2).mCompatInfo);
                arrayMap.put((ResourcesImpl)object3, (ResourcesKey)object4);
            }
            try {
                this.redirectResourcesToNewImplLocked(arrayMap);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            Trace.traceEnd(8192L);
            return;
        }
        Trace.traceEnd(8192L);
        throw var1_4;
    }

    @UnsupportedAppUsage
    @VisibleForTesting
    protected AssetManager createAssetManager(ResourcesKey object3) {
        AssetManager.Builder stringBuilder = new AssetManager.Builder();
        if (((ResourcesKey)object3).mResDir != null) {
            try {
                stringBuilder.addApkAssets(this.loadApkAssets(((ResourcesKey)object3).mResDir, false, false));
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("failed to add asset path ");
                stringBuilder2.append(((ResourcesKey)object3).mResDir);
                Log.e(TAG, stringBuilder2.toString());
                return null;
            }
        }
        if (((ResourcesKey)object3).mSplitResDirs != null) {
            for (String string2 : ((ResourcesKey)object3).mSplitResDirs) {
                try {
                    stringBuilder.addApkAssets(this.loadApkAssets(string2, false, false));
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("failed to add split asset path ");
                    stringBuilder3.append(string2);
                    Log.e(TAG, stringBuilder3.toString());
                    return null;
                }
            }
        }
        if (((ResourcesKey)object3).mOverlayDirs != null) {
            for (String string3 : ((ResourcesKey)object3).mOverlayDirs) {
                try {
                    stringBuilder.addApkAssets(this.loadApkAssets(string3, false, true));
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append("failed to add overlay path ");
                    stringBuilder4.append(string3);
                    Log.w(TAG, stringBuilder4.toString());
                }
            }
        }
        if (((ResourcesKey)object3).mLibDirs != null) {
            for (String string4 : ((ResourcesKey)object3).mLibDirs) {
                if (!string4.endsWith(".apk")) continue;
                try {
                    stringBuilder.addApkAssets(this.loadApkAssets(string4, true, false));
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder5 = new StringBuilder();
                    stringBuilder5.append("Asset path '");
                    stringBuilder5.append(string4);
                    stringBuilder5.append("' does not exist or contains no resources.");
                    Log.w(TAG, stringBuilder5.toString());
                }
            }
        }
        return stringBuilder.build();
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Resources createBaseActivityResources(IBinder object, String object2, String[] arrstring, String[] arrstring2, String[] arrstring3, int n, Configuration configuration, CompatibilityInfo compatibilityInfo, ClassLoader classLoader) {
        void var1_5;
        block10 : {
            void var8_12;
            void var5_9;
            void var4_8;
            void var7_11;
            Object object3;
            void var3_7;
            void var6_10;
            void var9_13;
            Trace.traceBegin(8192L, "ResourcesManager#createBaseActivityResources");
            Configuration configuration2 = var7_11 != null ? new Configuration((Configuration)var7_11) : null;
            ResourcesKey resourcesKey = new ResourcesKey((String)object3, (String[])var3_7, (String[])var4_8, (String[])var5_9, (int)var6_10, configuration2, (CompatibilityInfo)var8_12);
            object3 = var9_13 != null ? var9_13 : ClassLoader.getSystemClassLoader();
            this.getOrCreateActivityResourcesStructLocked((IBinder)object);
            // MONITOREXIT : this
            try {
                this.updateResourcesForActivity((IBinder)object, (Configuration)var7_11, (int)var6_10, false);
                object = this.getOrCreateResources((IBinder)object, resourcesKey, (ClassLoader)object3);
            }
            catch (Throwable throwable) {}
            Trace.traceEnd(8192L);
            return object;
            break block10;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        Trace.traceEnd(8192L);
        throw var1_5;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(String object, PrintWriter printWriter) {
        synchronized (this) {
            int n;
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            for (n = 0; n < ((String)object).length() / 2; ++n) {
                indentingPrintWriter.increaseIndent();
            }
            indentingPrintWriter.println("ResourcesManager:");
            indentingPrintWriter.increaseIndent();
            if (this.mLoadedApkAssets != null) {
                indentingPrintWriter.print("cached apks: total=");
                indentingPrintWriter.print(this.mLoadedApkAssets.size());
                indentingPrintWriter.print(" created=");
                indentingPrintWriter.print(this.mLoadedApkAssets.createCount());
                indentingPrintWriter.print(" evicted=");
                indentingPrintWriter.print(this.mLoadedApkAssets.evictionCount());
                indentingPrintWriter.print(" hit=");
                indentingPrintWriter.print(this.mLoadedApkAssets.hitCount());
                indentingPrintWriter.print(" miss=");
                indentingPrintWriter.print(this.mLoadedApkAssets.missCount());
                indentingPrintWriter.print(" max=");
                indentingPrintWriter.print(this.mLoadedApkAssets.maxSize());
            } else {
                indentingPrintWriter.print("cached apks: 0 [cache disabled]");
            }
            indentingPrintWriter.println();
            indentingPrintWriter.print("total apks: ");
            indentingPrintWriter.println(ResourcesManager.countLiveReferences(this.mCachedApkAssets.values()));
            indentingPrintWriter.print("resources: ");
            n = ResourcesManager.countLiveReferences(this.mResourceReferences);
            object = this.mActivityResourceReferences.values().iterator();
            do {
                if (!object.hasNext()) {
                    indentingPrintWriter.println(n);
                    indentingPrintWriter.print("resource impls: ");
                    indentingPrintWriter.println(ResourcesManager.countLiveReferences(this.mResourceImpls.values()));
                    return;
                }
                n += ResourcesManager.countLiveReferences(((ActivityResources)object.next()).activityResources);
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Display getAdjustedDisplay(int n, Resources object) {
        synchronized (this) {
            DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
            if (displayManagerGlobal != null) return displayManagerGlobal.getCompatibleDisplay(n, (Resources)object);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Configuration getConfiguration() {
        synchronized (this) {
            return this.mResConfiguration;
        }
    }

    DisplayMetrics getDisplayMetrics() {
        return this.getDisplayMetrics(0, DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS);
    }

    @VisibleForTesting
    protected DisplayMetrics getDisplayMetrics(int n, DisplayAdjustments object) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if ((object = this.getAdjustedDisplay(n, (DisplayAdjustments)object)) != null) {
            ((Display)object).getMetrics(displayMetrics);
        } else {
            displayMetrics.setToDefaults();
        }
        return displayMetrics;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Resources getResources(IBinder object, String object2, String[] arrstring, String[] arrstring2, String[] arrstring3, int n, Configuration configuration, CompatibilityInfo compatibilityInfo, ClassLoader classLoader) {
        void var1_4;
        block4 : {
            Configuration configuration2;
            Trace.traceBegin(8192L, "ResourcesManager#getResources");
            configuration = configuration != null ? (configuration2 = new Configuration(configuration)) : null;
            ResourcesKey resourcesKey = new ResourcesKey((String)object2, arrstring, arrstring2, arrstring3, n, configuration, compatibilityInfo);
            object2 = classLoader != null ? classLoader : ClassLoader.getSystemClassLoader();
            try {
                object = this.getOrCreateResources((IBinder)object, resourcesKey, (ClassLoader)object2);
            }
            catch (Throwable throwable) {
                break block4;
            }
            Trace.traceEnd(8192L);
            return object;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        Trace.traceEnd(8192L);
        throw var1_4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void invalidatePath(String string2) {
        synchronized (this) {
            int n = 0;
            int n2 = 0;
            do {
                Object object;
                if (n2 >= this.mResourceImpls.size()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalidated ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" asset managers that referenced ");
                    ((StringBuilder)object).append(string2);
                    Log.i(TAG, ((StringBuilder)object).toString());
                    return;
                }
                object = this.mResourceImpls.keyAt(n2);
                if (((ResourcesKey)object).isPathReferenced(string2)) {
                    this.cleanupResourceImpl((ResourcesKey)object);
                    ++n;
                    continue;
                }
                ++n2;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean isSameResourcesOverrideConfig(IBinder object, Configuration configuration) {
        synchronized (this) {
            Throwable throwable2;
            block6 : {
                block5 : {
                    if (object != null) {
                        try {
                            object = this.mActivityResourceReferences.get(object);
                            break block5;
                        }
                        catch (Throwable throwable2) {
                            break block6;
                        }
                    }
                    object = null;
                }
                boolean bl = true;
                boolean bl2 = true;
                if (object == null) {
                    if (configuration != null) return false;
                    return bl2;
                }
                if (Objects.equals(((ActivityResources)object).overrideConfig, configuration)) return bl;
                if (configuration == null) return false;
                if (((ActivityResources)object).overrideConfig == null) return false;
                if (configuration.diffPublicOnly(((ActivityResources)object).overrideConfig) != 0) return false;
                return bl;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateResourcesForActivity(IBinder object, Configuration configuration, int n, boolean bl) {
        ActivityResources activityResources;
        try {
            Trace.traceBegin(8192L, "ResourcesManager#updateResourcesForActivity");
            synchronized (this) {
                activityResources = this.getOrCreateActivityResourcesStructLocked((IBinder)object);
            }
        }
        catch (Throwable throwable) {
            Trace.traceEnd(8192L);
            throw throwable;
        }
        {
            if (Objects.equals(activityResources.overrideConfig, configuration) && !bl) {
                // MONITOREXIT [20, 6] lbl6 : MonitorExitStatement: MONITOREXIT : this
                Trace.traceEnd(8192L);
                return;
            }
            Configuration configuration2 = new Configuration(activityResources.overrideConfig);
            if (configuration != null) {
                activityResources.overrideConfig.setTo(configuration);
            } else {
                activityResources.overrideConfig.unset();
            }
            boolean bl2 = !activityResources.overrideConfig.equals(Configuration.EMPTY);
            int n2 = activityResources.activityResources.size();
            int n3 = 0;
            do {
                if (n3 >= n2) {
                    // MONITOREXIT [17, 6, 8] lbl23 : MonitorExitStatement: MONITOREXIT : this
                    Trace.traceEnd(8192L);
                    return;
                }
                Resources resources = (Resources)activityResources.activityResources.get(n3).get();
                if (resources != null) {
                    object = this.findKeyForResourceImplLocked(resources.getImpl());
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("can't find ResourcesKey for resources impl=");
                        ((StringBuilder)object).append(resources.getImpl());
                        Slog.e(TAG, ((StringBuilder)object).toString());
                    } else {
                        Object object2 = new Configuration();
                        if (configuration != null) {
                            ((Configuration)object2).setTo(configuration);
                        }
                        if (bl2 && ((ResourcesKey)object).hasOverrideConfiguration()) {
                            ((Configuration)object2).updateFrom(Configuration.generateDelta(configuration2, ((ResourcesKey)object).mOverrideConfiguration));
                        }
                        ResourcesKey resourcesKey = new ResourcesKey(((ResourcesKey)object).mResDir, ((ResourcesKey)object).mSplitResDirs, ((ResourcesKey)object).mOverlayDirs, ((ResourcesKey)object).mLibDirs, n, (Configuration)object2, ((ResourcesKey)object).mCompatInfo);
                        object = object2 = this.findResourcesImplForKeyLocked(resourcesKey);
                        if (object2 == null) {
                            object = object2 = this.createResourcesImpl(resourcesKey);
                            if (object2 != null) {
                                object = this.mResourceImpls;
                                WeakReference<Object> weakReference = new WeakReference<Object>(object2);
                                ((ArrayMap)object).put((ResourcesKey)resourcesKey, weakReference);
                                object = object2;
                            }
                        }
                        if (object != null && object != resources.getImpl()) {
                            resources.setImpl((ResourcesImpl)object);
                        }
                    }
                }
                ++n3;
            } while (true);
        }
    }

    private static class ActivityResources {
        public final ArrayList<WeakReference<Resources>> activityResources = new ArrayList();
        public final Configuration overrideConfig = new Configuration();

        private ActivityResources() {
        }
    }

    private static class ApkKey {
        public final boolean overlay;
        public final String path;
        public final boolean sharedLib;

        ApkKey(String string2, boolean bl, boolean bl2) {
            this.path = string2;
            this.sharedLib = bl;
            this.overlay = bl2;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof ApkKey;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (ApkKey)object;
            bl = bl2;
            if (this.path.equals(((ApkKey)object).path)) {
                bl = bl2;
                if (this.sharedLib == ((ApkKey)object).sharedLib) {
                    bl = bl2;
                    if (this.overlay == ((ApkKey)object).overlay) {
                        bl = true;
                    }
                }
            }
            return bl;
        }

        public int hashCode() {
            return ((1 * 31 + this.path.hashCode()) * 31 + Boolean.hashCode(this.sharedLib)) * 31 + Boolean.hashCode(this.overlay);
        }
    }

}

