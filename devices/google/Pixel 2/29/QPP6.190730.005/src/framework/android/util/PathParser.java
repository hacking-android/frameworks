/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Path;
import dalvik.annotation.optimization.FastNative;

public class PathParser {
    static final String LOGTAG = PathParser.class.getSimpleName();

    public static boolean canMorph(PathData pathData, PathData pathData2) {
        return PathParser.nCanMorph(pathData.mNativePathData, pathData2.mNativePathData);
    }

    @UnsupportedAppUsage
    public static Path createPathFromPathData(String string2) {
        if (string2 != null) {
            Path path = new Path();
            PathParser.nParseStringForPath(path.mNativePath, string2, string2.length());
            return path;
        }
        throw new IllegalArgumentException("Path string can not be null.");
    }

    public static void createPathFromPathData(Path path, PathData pathData) {
        PathParser.nCreatePathFromPathData(path.mNativePath, pathData.mNativePathData);
    }

    public static boolean interpolatePathData(PathData pathData, PathData pathData2, PathData pathData3, float f) {
        return PathParser.nInterpolatePathData(pathData.mNativePathData, pathData2.mNativePathData, pathData3.mNativePathData, f);
    }

    @FastNative
    private static native boolean nCanMorph(long var0, long var2);

    @FastNative
    private static native long nCreateEmptyPathData();

    @FastNative
    private static native long nCreatePathData(long var0);

    private static native long nCreatePathDataFromString(String var0, int var1);

    @FastNative
    private static native void nCreatePathFromPathData(long var0, long var2);

    @FastNative
    private static native void nFinalize(long var0);

    @FastNative
    private static native boolean nInterpolatePathData(long var0, long var2, long var4, float var6);

    private static native void nParseStringForPath(long var0, String var2, int var3);

    @FastNative
    private static native void nSetPathData(long var0, long var2);

    public static class PathData {
        long mNativePathData = 0L;

        public PathData() {
            this.mNativePathData = PathParser.nCreateEmptyPathData();
        }

        public PathData(PathData pathData) {
            this.mNativePathData = PathParser.nCreatePathData(pathData.mNativePathData);
        }

        public PathData(String string2) {
            this.mNativePathData = PathParser.nCreatePathDataFromString(string2, string2.length());
            if (this.mNativePathData != 0L) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid pathData: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        protected void finalize() throws Throwable {
            long l = this.mNativePathData;
            if (l != 0L) {
                PathParser.nFinalize(l);
                this.mNativePathData = 0L;
            }
            super.finalize();
        }

        public long getNativePtr() {
            return this.mNativePathData;
        }

        public void setPathData(PathData pathData) {
            PathParser.nSetPathData(this.mNativePathData, pathData.mNativePathData);
        }
    }

}

