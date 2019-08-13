/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm.split;

import android.content.pm.PackageParser;
import android.content.res.AssetManager;

public interface SplitAssetLoader
extends AutoCloseable {
    public AssetManager getBaseAssetManager() throws PackageParser.PackageParserException;

    public AssetManager getSplitAssetManager(int var1) throws PackageParser.PackageParserException;
}

