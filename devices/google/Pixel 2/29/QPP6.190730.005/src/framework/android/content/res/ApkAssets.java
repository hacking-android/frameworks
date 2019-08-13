/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.content.res.StringBlock;
import android.content.res.XmlBlock;
import android.content.res.XmlResourceParser;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.IOException;

public final class ApkAssets {
    @GuardedBy(value={"this"})
    private final long mNativePtr;
    @GuardedBy(value={"this"})
    private boolean mOpen = true;
    @GuardedBy(value={"this"})
    private final StringBlock mStringBlock;

    private ApkAssets(FileDescriptor fileDescriptor, String string2, boolean bl, boolean bl2) throws IOException {
        Preconditions.checkNotNull(fileDescriptor, "fd");
        Preconditions.checkNotNull(string2, "friendlyName");
        this.mNativePtr = ApkAssets.nativeLoadFromFd(fileDescriptor, string2, bl, bl2);
        this.mStringBlock = new StringBlock(ApkAssets.nativeGetStringBlock(this.mNativePtr), true);
    }

    private ApkAssets(String string2, boolean bl, boolean bl2, boolean bl3) throws IOException {
        Preconditions.checkNotNull(string2, "path");
        this.mNativePtr = ApkAssets.nativeLoad(string2, bl, bl2, bl3);
        this.mStringBlock = new StringBlock(ApkAssets.nativeGetStringBlock(this.mNativePtr), true);
    }

    public static ApkAssets loadFromFd(FileDescriptor fileDescriptor, String string2, boolean bl, boolean bl2) throws IOException {
        return new ApkAssets(fileDescriptor, string2, bl, bl2);
    }

    public static ApkAssets loadFromPath(String string2) throws IOException {
        return new ApkAssets(string2, false, false, false);
    }

    public static ApkAssets loadFromPath(String string2, boolean bl) throws IOException {
        return new ApkAssets(string2, bl, false, false);
    }

    public static ApkAssets loadFromPath(String string2, boolean bl, boolean bl2) throws IOException {
        return new ApkAssets(string2, bl, bl2, false);
    }

    public static ApkAssets loadOverlayFromPath(String string2, boolean bl) throws IOException {
        return new ApkAssets(string2, bl, false, true);
    }

    private static native void nativeDestroy(long var0);

    private static native String nativeGetAssetPath(long var0);

    private static native long nativeGetStringBlock(long var0);

    private static native boolean nativeIsUpToDate(long var0);

    private static native long nativeLoad(String var0, boolean var1, boolean var2, boolean var3) throws IOException;

    private static native long nativeLoadFromFd(FileDescriptor var0, String var1, boolean var2, boolean var3) throws IOException;

    private static native long nativeOpenXml(long var0, String var2) throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() throws Throwable {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                this.mStringBlock.close();
                ApkAssets.nativeDestroy(this.mNativePtr);
            }
            return;
        }
    }

    protected void finalize() throws Throwable {
        this.close();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public String getAssetPath() {
        synchronized (this) {
            return ApkAssets.nativeGetAssetPath(this.mNativePtr);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    CharSequence getStringFromPool(int n) {
        synchronized (this) {
            return this.mStringBlock.get(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isUpToDate() {
        synchronized (this) {
            return ApkAssets.nativeIsUpToDate(this.mNativePtr);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public XmlResourceParser openXml(String object) throws IOException {
        Preconditions.checkNotNull(object, "fileName");
        synchronized (this) {
            Object object2;
            block11 : {
                long l = ApkAssets.nativeOpenXml(this.mNativePtr, (String)object);
                object = new XmlBlock(null, l);
                try {
                    object2 = ((XmlBlock)object).newParser();
                    if (object2 == null) break block11;
                    ((XmlBlock)object).close();
                }
                catch (Throwable throwable) {
                    try {
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        try {
                            ((XmlBlock)object).close();
                            throw throwable2;
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        throw throwable2;
                    }
                }
                return object2;
            }
            object2 = new AssertionError((Object)"block.newParser() returned a null parser");
            throw object2;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ApkAssets{path=");
        stringBuilder.append(this.getAssetPath());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

