/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ActivityInfo;
import android.content.res.ApkAssets;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.XmlBlock;
import android.content.res.XmlResourceParser;
import android.os.ParcelFileDescriptor;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import libcore.io.IoUtils;

public final class AssetManager
implements AutoCloseable {
    public static final int ACCESS_BUFFER = 3;
    public static final int ACCESS_RANDOM = 1;
    public static final int ACCESS_STREAMING = 2;
    public static final int ACCESS_UNKNOWN = 0;
    private static final boolean DEBUG_REFS = false;
    private static final boolean FEATURE_FLAG_IDMAP2 = true;
    private static final String FRAMEWORK_APK_PATH = "/system/framework/framework-res.apk";
    private static final String TAG = "AssetManager";
    private static final ApkAssets[] sEmptyApkAssets;
    private static final Object sSync;
    @UnsupportedAppUsage
    @GuardedBy(value={"sSync"})
    static AssetManager sSystem;
    @GuardedBy(value={"sSync"})
    private static ApkAssets[] sSystemApkAssets;
    @GuardedBy(value={"sSync"})
    private static ArraySet<ApkAssets> sSystemApkAssetsSet;
    @GuardedBy(value={"this"})
    private ApkAssets[] mApkAssets;
    @GuardedBy(value={"this"})
    private int mNumRefs = 1;
    @UnsupportedAppUsage
    @GuardedBy(value={"this"})
    private long mObject;
    @GuardedBy(value={"this"})
    private final long[] mOffsets = new long[2];
    @GuardedBy(value={"this"})
    private boolean mOpen = true;
    @GuardedBy(value={"this"})
    private HashMap<Long, RuntimeException> mRefStacks;
    @GuardedBy(value={"this"})
    private final TypedValue mValue = new TypedValue();

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        sSync = new Object();
        sEmptyApkAssets = new ApkAssets[0];
        sSystem = null;
        sSystemApkAssets = new ApkAssets[0];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public AssetManager() {
        ApkAssets[] arrapkAssets;
        Object object = sSync;
        synchronized (object) {
            AssetManager.createSystemAssetsInZygoteLocked();
            arrapkAssets = sSystemApkAssets;
        }
        this.mObject = AssetManager.nativeCreate();
        this.setApkAssets(arrapkAssets, false);
    }

    private AssetManager(boolean bl) {
        this.mObject = AssetManager.nativeCreate();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private int addAssetPathInternal(String var1_1, boolean var2_3, boolean var3_4) {
        Preconditions.checkNotNull(var1_1, "path");
        synchronized (this) {
            block7 : {
                this.ensureOpenLocked();
                var4_6 = this.mApkAssets.length;
                for (var5_7 = 0; var5_7 < var4_6; ++var5_7) {
                    if (!this.mApkAssets[var5_7].getAssetPath().equals(var1_1)) continue;
                }
                {
                    catch (Throwable var1_3) {}
                }
                {
                    throw var1_3;
                }
                return var5_7 + 1;
                if (var2_4 == false) ** GOTO lbl24
                try {
                    var6_8 = new StringBuilder();
                    var6_8.append("/data/resource-cache/");
                    var6_8.append(var1_1.substring(1).replace('/', '@'));
                    var6_8.append("@idmap");
                    var1_1 = ApkAssets.loadOverlayFromPath(var6_8.toString(), false);
                    break block7;
lbl24: // 1 sources:
                    var1_1 = ApkAssets.loadFromPath((String)var1_1, false, (boolean)var3_5);
                }
                catch (IOException var1_2) {
                    return 0;
                }
            }
            this.mApkAssets = Arrays.copyOf(this.mApkAssets, var4_6 + 1);
            this.mApkAssets[var4_6] = var1_1;
            AssetManager.nativeSetApkAssets(this.mObject, this.mApkAssets, true);
            this.invalidateCachesLocked(-1);
            return var4_6 + 1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @GuardedBy(value={"sSync"})
    private static void createSystemAssetsInZygoteLocked() {
        if (sSystem != null) {
            return;
        }
        try {
            Object object = new ArrayList();
            ((ArrayList)object).add(ApkAssets.loadFromPath(FRAMEWORK_APK_PATH, true));
            String[] arrstring = AssetManager.nativeCreateIdmapsForStaticOverlaysTargetingAndroid();
            if (arrstring != null) {
                int n = arrstring.length;
                for (int i = 0; i < n; ++i) {
                    ((ArrayList)object).add(ApkAssets.loadOverlayFromPath(arrstring[i], true));
                }
            } else {
                Log.w(TAG, "'idmap2 --scan' failed: no static=\"true\" overlays targeting \"android\" will be loaded");
            }
            ArraySet arraySet = new ArraySet(object);
            sSystemApkAssetsSet = arraySet;
            sSystemApkAssets = ((ArrayList)object).toArray(new ApkAssets[((ArrayList)object).size()]);
            sSystem = object = new AssetManager(true);
            sSystem.setApkAssets(sSystemApkAssets, false);
            return;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Failed to create system AssetManager", iOException);
        }
    }

    @GuardedBy(value={"this"})
    private void decRefsLocked(long l) {
        --this.mNumRefs;
        if (this.mNumRefs == 0 && (l = this.mObject) != 0L) {
            AssetManager.nativeDestroy(l);
            this.mObject = 0L;
            this.mApkAssets = sEmptyApkAssets;
        }
    }

    @GuardedBy(value={"this"})
    private void ensureOpenLocked() {
        if (this.mOpen) {
            return;
        }
        throw new RuntimeException("AssetManager has been closed");
    }

    @GuardedBy(value={"this"})
    private void ensureValidLocked() {
        if (this.mObject != 0L) {
            return;
        }
        throw new RuntimeException("AssetManager has been destroyed");
    }

    public static native String getAssetAllocations();

    @UnsupportedAppUsage
    public static native int getGlobalAssetCount();

    @UnsupportedAppUsage
    public static native int getGlobalAssetManagerCount();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static AssetManager getSystem() {
        Object object = sSync;
        synchronized (object) {
            AssetManager.createSystemAssetsInZygoteLocked();
            return sSystem;
        }
    }

    @GuardedBy(value={"this"})
    private void incRefsLocked(long l) {
        ++this.mNumRefs;
    }

    private void invalidateCachesLocked(int n) {
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void loadStaticRuntimeOverlays(ArrayList<ApkAssets> arrayList) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/data/resource-cache/overlays.list");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        FileLock fileLock = fileInputStream.getChannel().lock(0L, Long.MAX_VALUE, true);
        do {
            String string2 = bufferedReader.readLine();
            if (string2 == null) break;
            arrayList.add(ApkAssets.loadOverlayFromPath(string2.split(" ")[1], true));
            continue;
            break;
        } while (true);
        if (fileLock != null) {
            AssetManager.$closeResource(null, fileLock);
        }
        AssetManager.$closeResource(null, bufferedReader);
        return;
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (fileLock == null) throw throwable2;
                try {
                    AssetManager.$closeResource(throwable, fileLock);
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        throw throwable3;
                    }
                    catch (Throwable throwable4) {
                        AssetManager.$closeResource(throwable3, bufferedReader);
                        throw throwable4;
                    }
                }
            }
        }
        finally {
            IoUtils.closeQuietly((AutoCloseable)fileInputStream);
        }
        catch (FileNotFoundException fileNotFoundException) {
            Log.i("AssetManager", "no overlays.list file found");
            return;
        }
    }

    private static native void nativeApplyStyle(long var0, long var2, int var4, int var5, long var6, int[] var8, long var9, long var11);

    private static native void nativeAssetDestroy(long var0);

    private static native long nativeAssetGetLength(long var0);

    private static native long nativeAssetGetRemainingLength(long var0);

    private static native int nativeAssetRead(long var0, byte[] var2, int var3, int var4);

    private static native int nativeAssetReadChar(long var0);

    private static native long nativeAssetSeek(long var0, long var2, int var4);

    private static native int[] nativeAttributeResolutionStack(long var0, long var2, int var4, int var5, int var6);

    private static native long nativeCreate();

    private static native String[] nativeCreateIdmapsForStaticOverlaysTargetingAndroid();

    private static native void nativeDestroy(long var0);

    private static native SparseArray<String> nativeGetAssignedPackageIdentifiers(long var0);

    private static native String nativeGetLastResourceResolution(long var0);

    private static native String[] nativeGetLocales(long var0, boolean var2);

    private static native Map nativeGetOverlayableMap(long var0, String var2);

    private static native int nativeGetResourceArray(long var0, int var2, int[] var3);

    private static native int nativeGetResourceArraySize(long var0, int var2);

    private static native int nativeGetResourceBagValue(long var0, int var2, int var3, TypedValue var4);

    private static native String nativeGetResourceEntryName(long var0, int var2);

    private static native int nativeGetResourceIdentifier(long var0, String var2, String var3, String var4);

    private static native int[] nativeGetResourceIntArray(long var0, int var2);

    private static native String nativeGetResourceName(long var0, int var2);

    private static native String nativeGetResourcePackageName(long var0, int var2);

    private static native String[] nativeGetResourceStringArray(long var0, int var2);

    private static native int[] nativeGetResourceStringArrayInfo(long var0, int var2);

    private static native String nativeGetResourceTypeName(long var0, int var2);

    private static native int nativeGetResourceValue(long var0, int var2, short var3, TypedValue var4, boolean var5);

    private static native Configuration[] nativeGetSizeConfigurations(long var0);

    private static native int[] nativeGetStyleAttributes(long var0, int var2);

    private static native String[] nativeList(long var0, String var2) throws IOException;

    private static native long nativeOpenAsset(long var0, String var2, int var3);

    private static native ParcelFileDescriptor nativeOpenAssetFd(long var0, String var2, long[] var3) throws IOException;

    private static native long nativeOpenNonAsset(long var0, int var2, String var3, int var4);

    private static native ParcelFileDescriptor nativeOpenNonAssetFd(long var0, int var2, String var3, long[] var4) throws IOException;

    private static native long nativeOpenXmlAsset(long var0, int var2, String var3);

    private static native boolean nativeResolveAttrs(long var0, long var2, int var4, int var5, int[] var6, int[] var7, int[] var8, int[] var9);

    private static native boolean nativeRetrieveAttributes(long var0, long var2, int[] var4, int[] var5, int[] var6);

    private static native void nativeSetApkAssets(long var0, ApkAssets[] var2, boolean var3);

    private static native void nativeSetConfiguration(long var0, int var2, int var3, String var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19);

    private static native void nativeSetResourceResolutionLoggingEnabled(long var0, boolean var2);

    private static native void nativeThemeApplyStyle(long var0, long var2, int var4, boolean var5);

    static native void nativeThemeClear(long var0);

    private static native void nativeThemeCopy(long var0, long var2, long var4, long var6);

    private static native long nativeThemeCreate(long var0);

    private static native void nativeThemeDestroy(long var0);

    private static native void nativeThemeDump(long var0, long var2, int var4, String var5, String var6);

    private static native int nativeThemeGetAttributeValue(long var0, long var2, int var4, TypedValue var5, boolean var6);

    static native int nativeThemeGetChangingConfigurations(long var0);

    private static native void nativeVerifySystemIdmaps();

    @Deprecated
    @UnsupportedAppUsage
    public int addAssetPath(String string2) {
        return this.addAssetPathInternal(string2, false, false);
    }

    @Deprecated
    @UnsupportedAppUsage
    public int addAssetPathAsSharedLibrary(String string2) {
        return this.addAssetPathInternal(string2, false, true);
    }

    @Deprecated
    @UnsupportedAppUsage
    public int addOverlayPath(String string2) {
        return this.addAssetPathInternal(string2, true, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void applyStyle(long l, int n, int n2, XmlBlock.Parser parser, int[] arrn, long l2, long l3) {
        Preconditions.checkNotNull(arrn, "inAttrs");
        synchronized (this) {
            this.ensureValidLocked();
            long l4 = this.mObject;
            long l5 = parser != null ? parser.mParseState : 0L;
            AssetManager.nativeApplyStyle(l4, l, n, n2, l5, arrn, l2, l3);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void applyStyleToTheme(long l, int n, boolean bl) {
        synchronized (this) {
            this.ensureValidLocked();
            AssetManager.nativeThemeApplyStyle(this.mObject, l, n, bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        synchronized (this) {
            if (!this.mOpen) {
                return;
            }
            this.mOpen = false;
            this.decRefsLocked(this.hashCode());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    long createTheme() {
        synchronized (this) {
            this.ensureValidLocked();
            long l = AssetManager.nativeThemeCreate(this.mObject);
            this.incRefsLocked(l);
            return l;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dumpTheme(long l, int n, String string2, String string3) {
        synchronized (this) {
            this.ensureValidLocked();
            AssetManager.nativeThemeDump(this.mObject, l, n, string2, string3);
            return;
        }
    }

    protected void finalize() throws Throwable {
        long l = this.mObject;
        if (l != 0L) {
            AssetManager.nativeDestroy(l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int findCookieForPath(String string2) {
        Preconditions.checkNotNull(string2, "path");
        synchronized (this) {
            this.ensureValidLocked();
            int n = this.mApkAssets.length;
            int n2 = 0;
            while (n2 < n) {
                if (string2.equals(this.mApkAssets[n2].getAssetPath())) {
                    return n2 + 1;
                }
                ++n2;
            }
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public ApkAssets[] getApkAssets() {
        synchronized (this) {
            if (!this.mOpen) return sEmptyApkAssets;
            return this.mApkAssets;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String[] getApkPaths() {
        synchronized (this) {
            if (!this.mOpen) {
                return new String[0];
            }
            String[] arrstring = new String[this.mApkAssets.length];
            int n = this.mApkAssets.length;
            int n2 = 0;
            while (n2 < n) {
                arrstring[n2] = this.mApkAssets[n2].getAssetPath();
                ++n2;
            }
            return arrstring;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public SparseArray<String> getAssignedPackageIdentifiers() {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetAssignedPackageIdentifiers(this.mObject);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int[] getAttributeResolutionStack(long l, int n, int n2, int n3) {
        synchronized (this) {
            return AssetManager.nativeAttributeResolutionStack(this.mObject, l, n3, n, n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getLastResourceResolution() {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetLastResourceResolution(this.mObject);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String[] getLocales() {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetLocales(this.mObject, false);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String[] getNonSystemLocales() {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetLocales(this.mObject, true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @GuardedBy(value={"this"})
    public Map<String, String> getOverlayableMap(String object) {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetOverlayableMap(this.mObject, (String)object);
        }
    }

    CharSequence getPooledStringForCookie(int n, int n2) {
        return this.getApkAssets()[n - 1].getStringFromPool(n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int getResourceArray(int n, int[] arrn) {
        Preconditions.checkNotNull(arrn, "outData");
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetResourceArray(this.mObject, n, arrn);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int getResourceArraySize(int n) {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetResourceArraySize(this.mObject, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    CharSequence getResourceBagText(int n, int n2) {
        synchronized (this) {
            this.ensureValidLocked();
            TypedValue typedValue = this.mValue;
            n = AssetManager.nativeGetResourceBagValue(this.mObject, n, n2, typedValue);
            if (n <= 0) {
                return null;
            }
            typedValue.changingConfigurations = ActivityInfo.activityInfoConfigNativeToJava(typedValue.changingConfigurations);
            if (typedValue.type != 3) return typedValue.coerceToString();
            return this.mApkAssets[n - 1].getStringFromPool(typedValue.data);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    String getResourceEntryName(int n) {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetResourceEntryName(this.mObject, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    int getResourceIdentifier(String string2, String string3, String string4) {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetResourceIdentifier(this.mObject, string2, string3, string4);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int[] getResourceIntArray(int n) {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetResourceIntArray(this.mObject, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    String getResourceName(int n) {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetResourceName(this.mObject, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    String getResourcePackageName(int n) {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetResourcePackageName(this.mObject, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    String[] getResourceStringArray(int n) {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetResourceStringArray(this.mObject, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    CharSequence getResourceText(int n) {
        synchronized (this) {
            TypedValue typedValue = this.mValue;
            if (!this.getResourceValue(n, 0, typedValue, true)) return null;
            return typedValue.coerceToString();
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    CharSequence[] getResourceTextArray(int n) {
        synchronized (this) {
            this.ensureValidLocked();
            int[] arrn = AssetManager.nativeGetResourceStringArrayInfo(this.mObject, n);
            if (arrn == null) {
                return null;
            }
            int n2 = arrn.length;
            CharSequence[] arrcharSequence = new CharSequence[n2 / 2];
            int n3 = 0;
            n = 0;
            while (n3 < n2) {
                int n4 = arrn[n3];
                int n5 = arrn[n3 + 1];
                CharSequence charSequence = n5 >= 0 && n4 > 0 ? this.mApkAssets[n4 - 1].getStringFromPool(n5) : null;
                arrcharSequence[n] = charSequence;
                n3 += 2;
                ++n;
            }
            return arrcharSequence;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    String getResourceTypeName(int n) {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetResourceTypeName(this.mObject, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    boolean getResourceValue(int n, int n2, TypedValue typedValue, boolean bl) {
        Preconditions.checkNotNull(typedValue, "outValue");
        synchronized (this) {
            this.ensureValidLocked();
            n = AssetManager.nativeGetResourceValue(this.mObject, n, (short)n2, typedValue, bl);
            if (n <= 0) {
                return false;
            }
            typedValue.changingConfigurations = ActivityInfo.activityInfoConfigNativeToJava(typedValue.changingConfigurations);
            if (typedValue.type == 3) {
                typedValue.string = this.mApkAssets[n - 1].getStringFromPool(typedValue.data);
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Configuration[] getSizeConfigurations() {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetSizeConfigurations(this.mObject);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int[] getStyleAttributes(int n) {
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeGetStyleAttributes(this.mObject, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean getThemeValue(long l, int n, TypedValue typedValue, boolean bl) {
        Preconditions.checkNotNull(typedValue, "outValue");
        synchronized (this) {
            this.ensureValidLocked();
            n = AssetManager.nativeThemeGetAttributeValue(this.mObject, l, n, typedValue, bl);
            if (n <= 0) {
                return false;
            }
            typedValue.changingConfigurations = ActivityInfo.activityInfoConfigNativeToJava(typedValue.changingConfigurations);
            if (typedValue.type == 3) {
                typedValue.string = this.mApkAssets[n - 1].getStringFromPool(typedValue.data);
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public boolean isUpToDate() {
        synchronized (this) {
            if (!this.mOpen) {
                return false;
            }
            ApkAssets[] arrapkAssets = this.mApkAssets;
            int n = arrapkAssets.length;
            int n2 = 0;
            while (n2 < n) {
                if (!arrapkAssets[n2].isUpToDate()) {
                    return false;
                }
                ++n2;
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String[] list(String arrstring) throws IOException {
        Preconditions.checkNotNull(arrstring, "path");
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeList(this.mObject, (String)arrstring);
        }
    }

    public InputStream open(String string2) throws IOException {
        return this.open(string2, 2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputStream open(String object, int n) throws IOException {
        Preconditions.checkNotNull(object, "fileName");
        synchronized (this) {
            this.ensureOpenLocked();
            long l = AssetManager.nativeOpenAsset(this.mObject, (String)object, n);
            if (l != 0L) {
                object = new AssetInputStream(l);
                this.incRefsLocked(object.hashCode());
                return object;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Asset file: ");
            stringBuilder.append((String)object);
            FileNotFoundException fileNotFoundException = new FileNotFoundException(stringBuilder.toString());
            throw fileNotFoundException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AssetFileDescriptor openFd(String object) throws IOException {
        Preconditions.checkNotNull(object, "fileName");
        synchronized (this) {
            this.ensureOpenLocked();
            Object object2 = AssetManager.nativeOpenAssetFd(this.mObject, (String)object, this.mOffsets);
            if (object2 != null) {
                return new AssetFileDescriptor((ParcelFileDescriptor)object2, this.mOffsets[0], this.mOffsets[1]);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Asset file: ");
            ((StringBuilder)object2).append((String)object);
            FileNotFoundException fileNotFoundException = new FileNotFoundException(((StringBuilder)object2).toString());
            throw fileNotFoundException;
        }
    }

    @UnsupportedAppUsage
    public InputStream openNonAsset(int n, String string2) throws IOException {
        return this.openNonAsset(n, string2, 2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public InputStream openNonAsset(int n, String object, int n2) throws IOException {
        Preconditions.checkNotNull(object, "fileName");
        synchronized (this) {
            this.ensureOpenLocked();
            long l = AssetManager.nativeOpenNonAsset(this.mObject, n, (String)object, n2);
            if (l != 0L) {
                object = new AssetInputStream(l);
                this.incRefsLocked(object.hashCode());
                return object;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Asset absolute file: ");
            stringBuilder.append((String)object);
            FileNotFoundException fileNotFoundException = new FileNotFoundException(stringBuilder.toString());
            throw fileNotFoundException;
        }
    }

    @UnsupportedAppUsage
    public InputStream openNonAsset(String string2) throws IOException {
        return this.openNonAsset(0, string2, 2);
    }

    @UnsupportedAppUsage
    public InputStream openNonAsset(String string2, int n) throws IOException {
        return this.openNonAsset(0, string2, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AssetFileDescriptor openNonAssetFd(int n, String object) throws IOException {
        Preconditions.checkNotNull(object, "fileName");
        synchronized (this) {
            this.ensureOpenLocked();
            Object object2 = AssetManager.nativeOpenNonAssetFd(this.mObject, n, (String)object, this.mOffsets);
            if (object2 != null) {
                return new AssetFileDescriptor((ParcelFileDescriptor)object2, this.mOffsets[0], this.mOffsets[1]);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Asset absolute file: ");
            ((StringBuilder)object2).append((String)object);
            FileNotFoundException fileNotFoundException = new FileNotFoundException(((StringBuilder)object2).toString());
            throw fileNotFoundException;
        }
    }

    public AssetFileDescriptor openNonAssetFd(String string2) throws IOException {
        return this.openNonAssetFd(0, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    XmlBlock openXmlBlockAsset(int n, String object) throws IOException {
        Preconditions.checkNotNull(object, "fileName");
        synchronized (this) {
            this.ensureOpenLocked();
            long l = AssetManager.nativeOpenXmlAsset(this.mObject, n, (String)object);
            if (l != 0L) {
                object = new XmlBlock(this, l);
                this.incRefsLocked(object.hashCode());
                return object;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Asset XML file: ");
            stringBuilder.append((String)object);
            FileNotFoundException fileNotFoundException = new FileNotFoundException(stringBuilder.toString());
            throw fileNotFoundException;
        }
    }

    XmlBlock openXmlBlockAsset(String string2) throws IOException {
        return this.openXmlBlockAsset(0, string2);
    }

    public XmlResourceParser openXmlResourceParser(int n, String object) throws IOException {
        Object object2;
        block6 : {
            object = this.openXmlBlockAsset(n, (String)object);
            try {
                object2 = ((XmlBlock)object).newParser();
                if (object2 == null) break block6;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (object != null) {
                        AssetManager.$closeResource(throwable, (AutoCloseable)object);
                    }
                    throw throwable2;
                }
            }
            AssetManager.$closeResource(null, (AutoCloseable)object);
            return object2;
        }
        object2 = new AssertionError((Object)"block.newParser() returned a null parser");
        throw object2;
    }

    public XmlResourceParser openXmlResourceParser(String string2) throws IOException {
        return this.openXmlResourceParser(0, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void releaseTheme(long l) {
        synchronized (this) {
            AssetManager.nativeThemeDestroy(l);
            this.decRefsLocked(l);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    boolean resolveAttrs(long l, int n, int n2, int[] arrn, int[] arrn2, int[] arrn3, int[] arrn4) {
        Preconditions.checkNotNull(arrn2, "inAttrs");
        Preconditions.checkNotNull(arrn3, "outValues");
        Preconditions.checkNotNull(arrn4, "outIndices");
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeResolveAttrs(this.mObject, l, n, n2, arrn, arrn2, arrn3, arrn4);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    boolean retrieveAttributes(XmlBlock.Parser parser, int[] arrn, int[] arrn2, int[] arrn3) {
        Preconditions.checkNotNull(parser, "parser");
        Preconditions.checkNotNull(arrn, "inAttrs");
        Preconditions.checkNotNull(arrn2, "outValues");
        Preconditions.checkNotNull(arrn3, "outIndices");
        synchronized (this) {
            this.ensureValidLocked();
            return AssetManager.nativeRetrieveAttributes(this.mObject, parser.mParseState, arrn, arrn2, arrn3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setApkAssets(ApkAssets[] arrapkAssets, boolean bl) {
        Preconditions.checkNotNull(arrapkAssets, "apkAssets");
        Object object2 = sSystemApkAssets;
        ApkAssets[] arrapkAssets2 = new ApkAssets[((ApkAssets[])object2).length + arrapkAssets.length];
        int n = ((ApkAssets[])object2).length;
        System.arraycopy(object2, 0, arrapkAssets2, 0, n);
        n = sSystemApkAssets.length;
        for (Object object2 : arrapkAssets) {
            int n2 = n;
            if (!sSystemApkAssetsSet.contains(object2)) {
                arrapkAssets2[n] = object2;
                n2 = n + 1;
            }
            n = n2;
        }
        arrapkAssets = arrapkAssets2;
        if (n != arrapkAssets2.length) {
            arrapkAssets = Arrays.copyOf(arrapkAssets2, n);
        }
        synchronized (this) {
            this.ensureOpenLocked();
            this.mApkAssets = arrapkAssets;
            AssetManager.nativeSetApkAssets(this.mObject, this.mApkAssets, bl);
            if (bl) {
                this.invalidateCachesLocked(-1);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setConfiguration(int n, int n2, String string2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12, int n13, int n14, int n15, int n16, int n17) {
        synchronized (this) {
            this.ensureValidLocked();
            AssetManager.nativeSetConfiguration(this.mObject, n, n2, string2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setResourceResolutionLoggingEnabled(boolean bl) {
        synchronized (this) {
            this.ensureValidLocked();
            AssetManager.nativeSetResourceResolutionLoggingEnabled(this.mObject, bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void setThemeTo(long l, AssetManager assetManager, long l2) {
        synchronized (this) {
            this.ensureValidLocked();
            synchronized (assetManager) {
                assetManager.ensureValidLocked();
                AssetManager.nativeThemeCopy(this.mObject, l, assetManager.mObject, l2);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void xmlBlockGone(int n) {
        synchronized (this) {
            long l = n;
            this.decRefsLocked(l);
            return;
        }
    }

    public final class AssetInputStream
    extends InputStream {
        private long mAssetNativePtr;
        private long mLength;
        private long mMarkPos;

        private AssetInputStream(long l) {
            this.mAssetNativePtr = l;
            this.mLength = AssetManager.nativeAssetGetLength(l);
        }

        private void ensureOpen() {
            if (this.mAssetNativePtr != 0L) {
                return;
            }
            throw new IllegalStateException("AssetInputStream is closed");
        }

        @Override
        public final int available() throws IOException {
            this.ensureOpen();
            long l = AssetManager.nativeAssetGetRemainingLength(this.mAssetNativePtr);
            int n = l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
            return n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final void close() throws IOException {
            long l = this.mAssetNativePtr;
            if (l == 0L) return;
            AssetManager.nativeAssetDestroy(l);
            this.mAssetNativePtr = 0L;
            AssetManager assetManager = AssetManager.this;
            synchronized (assetManager) {
                AssetManager.this.decRefsLocked(this.hashCode());
                return;
            }
        }

        protected void finalize() throws Throwable {
            this.close();
        }

        @UnsupportedAppUsage
        public final int getAssetInt() {
            throw new UnsupportedOperationException();
        }

        @UnsupportedAppUsage
        public final long getNativeAsset() {
            return this.mAssetNativePtr;
        }

        @Override
        public final void mark(int n) {
            this.ensureOpen();
            this.mMarkPos = AssetManager.nativeAssetSeek(this.mAssetNativePtr, 0L, 0);
        }

        @Override
        public final boolean markSupported() {
            return true;
        }

        @Override
        public final int read() throws IOException {
            this.ensureOpen();
            return AssetManager.nativeAssetReadChar(this.mAssetNativePtr);
        }

        @Override
        public final int read(byte[] arrby) throws IOException {
            this.ensureOpen();
            Preconditions.checkNotNull(arrby, "b");
            return AssetManager.nativeAssetRead(this.mAssetNativePtr, arrby, 0, arrby.length);
        }

        @Override
        public final int read(byte[] arrby, int n, int n2) throws IOException {
            this.ensureOpen();
            Preconditions.checkNotNull(arrby, "b");
            return AssetManager.nativeAssetRead(this.mAssetNativePtr, arrby, n, n2);
        }

        @Override
        public final void reset() throws IOException {
            this.ensureOpen();
            AssetManager.nativeAssetSeek(this.mAssetNativePtr, this.mMarkPos, -1);
        }

        @Override
        public final long skip(long l) throws IOException {
            this.ensureOpen();
            long l2 = AssetManager.nativeAssetSeek(this.mAssetNativePtr, 0L, 0);
            long l3 = this.mLength;
            long l4 = l;
            if (l2 + l > l3) {
                l4 = l3 - l2;
            }
            if (l4 > 0L) {
                AssetManager.nativeAssetSeek(this.mAssetNativePtr, l4, 0);
            }
            return l4;
        }
    }

    public static class Builder {
        private ArrayList<ApkAssets> mUserApkAssets = new ArrayList();

        public Builder addApkAssets(ApkAssets apkAssets) {
            this.mUserApkAssets.add(apkAssets);
            return this;
        }

        public AssetManager build() {
            Object object = AssetManager.getSystem().getApkAssets();
            ApkAssets[] arrapkAssets = new ApkAssets[((ApkAssets[])object).length + this.mUserApkAssets.size()];
            System.arraycopy(object, 0, arrapkAssets, 0, ((ApkAssets[])object).length);
            int n = this.mUserApkAssets.size();
            for (int i = 0; i < n; ++i) {
                arrapkAssets[((ApkAssets[])object).length + i] = this.mUserApkAssets.get(i);
            }
            object = new AssetManager(false);
            ((AssetManager)object).mApkAssets = arrapkAssets;
            AssetManager.nativeSetApkAssets(((AssetManager)object).mObject, arrapkAssets, false);
            return object;
        }
    }

}

