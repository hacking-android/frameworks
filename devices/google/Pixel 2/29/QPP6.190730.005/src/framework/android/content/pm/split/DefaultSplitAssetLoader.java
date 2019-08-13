/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.content.pm.split;

import android.content.pm.PackageParser;
import android.content.pm.split.SplitAssetLoader;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.os.Build;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import libcore.io.IoUtils;

public class DefaultSplitAssetLoader
implements SplitAssetLoader {
    private final String mBaseCodePath;
    private AssetManager mCachedAssetManager;
    private final int mFlags;
    private final String[] mSplitCodePaths;

    public DefaultSplitAssetLoader(PackageParser.PackageLite packageLite, int n) {
        this.mBaseCodePath = packageLite.baseCodePath;
        this.mSplitCodePaths = packageLite.splitCodePaths;
        this.mFlags = n;
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
        IoUtils.closeQuietly((AutoCloseable)this.mCachedAssetManager);
    }

    @Override
    public AssetManager getBaseAssetManager() throws PackageParser.PackageParserException {
        Object object;
        Object[] arrobject = this.mCachedAssetManager;
        if (arrobject != null) {
            return arrobject;
        }
        arrobject = this.mSplitCodePaths;
        int n = arrobject != null ? arrobject.length : 0;
        arrobject = new ApkAssets[n + 1];
        arrobject[0] = DefaultSplitAssetLoader.loadApkAssets(this.mBaseCodePath, this.mFlags);
        if (!ArrayUtils.isEmpty(this.mSplitCodePaths)) {
            object = this.mSplitCodePaths;
            int n2 = ((String[])object).length;
            n = 0 + 1;
            int n3 = 0;
            while (n3 < n2) {
                arrobject[n] = DefaultSplitAssetLoader.loadApkAssets(object[n3], this.mFlags);
                ++n3;
                ++n;
            }
        }
        object = new AssetManager();
        ((AssetManager)object).setConfiguration(0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
        ((AssetManager)object).setApkAssets((ApkAssets[])arrobject, false);
        this.mCachedAssetManager = object;
        return this.mCachedAssetManager;
    }

    @Override
    public AssetManager getSplitAssetManager(int n) throws PackageParser.PackageParserException {
        return this.getBaseAssetManager();
    }
}

