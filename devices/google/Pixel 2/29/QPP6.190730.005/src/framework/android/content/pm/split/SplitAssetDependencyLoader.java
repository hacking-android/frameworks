/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.content.pm.split;

import android.content.pm.PackageParser;
import android.content.pm.split.SplitAssetLoader;
import android.content.pm.split.SplitDependencyLoader;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.SparseArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import libcore.io.IoUtils;

public class SplitAssetDependencyLoader
extends SplitDependencyLoader<PackageParser.PackageParserException>
implements SplitAssetLoader {
    private final AssetManager[] mCachedAssetManagers;
    private final ApkAssets[][] mCachedSplitApks;
    private final int mFlags;
    private final String[] mSplitPaths;

    public SplitAssetDependencyLoader(PackageParser.PackageLite arrstring, SparseArray<int[]> sparseArray, int n) {
        super(sparseArray);
        this.mSplitPaths = new String[arrstring.splitCodePaths.length + 1];
        this.mSplitPaths[0] = arrstring.baseCodePath;
        System.arraycopy(arrstring.splitCodePaths, 0, this.mSplitPaths, 1, arrstring.splitCodePaths.length);
        this.mFlags = n;
        arrstring = this.mSplitPaths;
        this.mCachedSplitApks = new ApkAssets[arrstring.length][];
        this.mCachedAssetManagers = new AssetManager[arrstring.length];
    }

    private static AssetManager createAssetManagerWithAssets(ApkAssets[] arrapkAssets) {
        AssetManager assetManager = new AssetManager();
        assetManager.setConfiguration(0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
        assetManager.setApkAssets(arrapkAssets, false);
        return assetManager;
    }

    private static ApkAssets loadApkAssets(String string2, int n) throws PackageParser.PackageParserException {
        if ((n & 1) != 0 && !PackageParser.isApkPath(string2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid package file: ");
            stringBuilder.append(string2);
            throw new PackageParser.PackageParserException(-100, stringBuilder.toString());
        }
        try {
            ApkAssets apkAssets = ApkAssets.loadFromPath(string2);
            return apkAssets;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to load APK at path ");
            stringBuilder.append(string2);
            throw new PackageParser.PackageParserException(-2, stringBuilder.toString(), iOException);
        }
    }

    @Override
    public void close() throws Exception {
        AssetManager[] arrassetManager = this.mCachedAssetManagers;
        int n = arrassetManager.length;
        for (int i = 0; i < n; ++i) {
            IoUtils.closeQuietly((AutoCloseable)arrassetManager[i]);
        }
    }

    @Override
    protected void constructSplit(int n, int[] arrn, int n2) throws PackageParser.PackageParserException {
        ArrayList<ApkAssets> arrayList = new ArrayList<ApkAssets>();
        if (n2 >= 0) {
            Collections.addAll(arrayList, this.mCachedSplitApks[n2]);
        }
        arrayList.add(SplitAssetDependencyLoader.loadApkAssets(this.mSplitPaths[n], this.mFlags));
        for (int n3 : arrn) {
            arrayList.add(SplitAssetDependencyLoader.loadApkAssets(this.mSplitPaths[n3], this.mFlags));
        }
        this.mCachedSplitApks[n] = arrayList.toArray(new ApkAssets[arrayList.size()]);
        this.mCachedAssetManagers[n] = SplitAssetDependencyLoader.createAssetManagerWithAssets(this.mCachedSplitApks[n]);
    }

    @Override
    public AssetManager getBaseAssetManager() throws PackageParser.PackageParserException {
        this.loadDependenciesForSplit(0);
        return this.mCachedAssetManagers[0];
    }

    @Override
    public AssetManager getSplitAssetManager(int n) throws PackageParser.PackageParserException {
        this.loadDependenciesForSplit(n + 1);
        return this.mCachedAssetManagers[n + 1];
    }

    @Override
    protected boolean isSplitCached(int n) {
        boolean bl = this.mCachedAssetManagers[n] != null;
        return bl;
    }
}

