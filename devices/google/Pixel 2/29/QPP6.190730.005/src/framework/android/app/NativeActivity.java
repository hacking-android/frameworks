/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.BaseDexClassLoader
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.AttributeSet;
import android.view.InputQueue;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import dalvik.system.BaseDexClassLoader;
import java.io.File;

public class NativeActivity
extends Activity
implements SurfaceHolder.Callback2,
InputQueue.Callback,
ViewTreeObserver.OnGlobalLayoutListener {
    private static final String KEY_NATIVE_SAVED_STATE = "android:native_state";
    public static final String META_DATA_FUNC_NAME = "android.app.func_name";
    public static final String META_DATA_LIB_NAME = "android.app.lib_name";
    private InputQueue mCurInputQueue;
    private SurfaceHolder mCurSurfaceHolder;
    private boolean mDestroyed;
    private boolean mDispatchingUnhandledKey;
    private InputMethodManager mIMM;
    int mLastContentHeight;
    int mLastContentWidth;
    int mLastContentX;
    int mLastContentY;
    final int[] mLocation = new int[2];
    private NativeContentView mNativeContentView;
    @UnsupportedAppUsage
    private long mNativeHandle;

    private static String getAbsolutePath(File object) {
        object = object != null ? ((File)object).getAbsolutePath() : null;
        return object;
    }

    private native String getDlError();

    @UnsupportedAppUsage
    private native long loadNativeCode(String var1, String var2, MessageQueue var3, String var4, String var5, String var6, int var7, AssetManager var8, byte[] var9, ClassLoader var10, String var11);

    private native void onConfigurationChangedNative(long var1);

    private native void onContentRectChangedNative(long var1, int var3, int var4, int var5, int var6);

    private native void onInputQueueCreatedNative(long var1, long var3);

    private native void onInputQueueDestroyedNative(long var1, long var3);

    private native void onLowMemoryNative(long var1);

    private native void onPauseNative(long var1);

    private native void onResumeNative(long var1);

    private native byte[] onSaveInstanceStateNative(long var1);

    private native void onStartNative(long var1);

    private native void onStopNative(long var1);

    private native void onSurfaceChangedNative(long var1, Surface var3, int var4, int var5, int var6);

    private native void onSurfaceCreatedNative(long var1, Surface var3);

    private native void onSurfaceDestroyedNative(long var1);

    private native void onSurfaceRedrawNeededNative(long var1, Surface var3);

    private native void onWindowFocusChangedNative(long var1, boolean var3);

    private native void unloadNativeCode(long var1);

    @UnsupportedAppUsage
    void hideIme(int n) {
        this.mIMM.hideSoftInputFromWindow(this.mNativeContentView.getWindowToken(), n);
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!this.mDestroyed) {
            this.onConfigurationChangedNative(this.mNativeHandle);
        }
    }

    @Override
    protected void onCreate(Bundle object) {
        Object object2;
        Object object3;
        Object object4;
        ActivityInfo activityInfo;
        block8 : {
            block6 : {
                block7 : {
                    object4 = "main";
                    object3 = "ANativeActivity_onCreate";
                    this.mIMM = this.getSystemService(InputMethodManager.class);
                    this.getWindow().takeSurface(this);
                    this.getWindow().takeInputQueue(this);
                    this.getWindow().setFormat(4);
                    this.getWindow().setSoftInputMode(16);
                    this.mNativeContentView = new NativeContentView(this);
                    object2 = this.mNativeContentView;
                    ((NativeContentView)object2).mActivity = this;
                    this.setContentView((View)object2);
                    this.mNativeContentView.requestFocus();
                    this.mNativeContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
                    try {
                        activityInfo = this.getPackageManager().getActivityInfo(this.getIntent().getComponent(), 128);
                        if (activityInfo.metaData == null) break block6;
                        object2 = activityInfo.metaData.getString("android.app.lib_name");
                        if (object2 == null) break block7;
                        object4 = object2;
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        throw new RuntimeException("Error getting activity info", nameNotFoundException);
                    }
                }
                object2 = activityInfo.metaData.getString("android.app.func_name");
                if (object2 != null) {
                    object3 = object2;
                }
                object2 = object4;
                object4 = object3;
                object3 = object2;
                break block8;
            }
            object3 = "main";
            object4 = "ANativeActivity_onCreate";
        }
        activityInfo = (BaseDexClassLoader)this.getClassLoader();
        object2 = activityInfo.findLibrary((String)object3);
        if (object2 != null) {
            object3 = object != null ? ((Bundle)object).getByteArray("android:native_state") : null;
            this.mNativeHandle = this.loadNativeCode((String)object2, (String)object4, Looper.myQueue(), NativeActivity.getAbsolutePath(this.getFilesDir()), NativeActivity.getAbsolutePath(this.getObbDir()), NativeActivity.getAbsolutePath(this.getExternalFilesDir(null)), Build.VERSION.SDK_INT, this.getAssets(), (byte[])object3, (ClassLoader)((Object)activityInfo), activityInfo.getLdLibraryPath());
            if (this.mNativeHandle != 0L) {
                super.onCreate((Bundle)object);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to load native library \"");
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append("\": ");
            ((StringBuilder)object).append(this.getDlError());
            throw new UnsatisfiedLinkError(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to find native library ");
        ((StringBuilder)object).append((String)object3);
        ((StringBuilder)object).append(" using classloader: ");
        ((StringBuilder)object).append(activityInfo.toString());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    protected void onDestroy() {
        InputQueue inputQueue;
        this.mDestroyed = true;
        if (this.mCurSurfaceHolder != null) {
            this.onSurfaceDestroyedNative(this.mNativeHandle);
            this.mCurSurfaceHolder = null;
        }
        if ((inputQueue = this.mCurInputQueue) != null) {
            this.onInputQueueDestroyedNative(this.mNativeHandle, inputQueue.getNativePtr());
            this.mCurInputQueue = null;
        }
        this.unloadNativeCode(this.mNativeHandle);
        super.onDestroy();
    }

    @Override
    public void onGlobalLayout() {
        this.mNativeContentView.getLocationInWindow(this.mLocation);
        int n = this.mNativeContentView.getWidth();
        int n2 = this.mNativeContentView.getHeight();
        int[] arrn = this.mLocation;
        if (arrn[0] != this.mLastContentX || arrn[1] != this.mLastContentY || n != this.mLastContentWidth || n2 != this.mLastContentHeight) {
            arrn = this.mLocation;
            this.mLastContentX = arrn[0];
            this.mLastContentY = arrn[1];
            this.mLastContentWidth = n;
            this.mLastContentHeight = n2;
            if (!this.mDestroyed) {
                this.onContentRectChangedNative(this.mNativeHandle, this.mLastContentX, this.mLastContentY, this.mLastContentWidth, this.mLastContentHeight);
            }
        }
    }

    @Override
    public void onInputQueueCreated(InputQueue inputQueue) {
        if (!this.mDestroyed) {
            this.mCurInputQueue = inputQueue;
            this.onInputQueueCreatedNative(this.mNativeHandle, inputQueue.getNativePtr());
        }
    }

    @Override
    public void onInputQueueDestroyed(InputQueue inputQueue) {
        if (!this.mDestroyed) {
            this.onInputQueueDestroyedNative(this.mNativeHandle, inputQueue.getNativePtr());
            this.mCurInputQueue = null;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (!this.mDestroyed) {
            this.onLowMemoryNative(this.mNativeHandle);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.onPauseNative(this.mNativeHandle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onResumeNative(this.mNativeHandle);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        byte[] arrby = this.onSaveInstanceStateNative(this.mNativeHandle);
        if (arrby != null) {
            bundle.putByteArray("android:native_state", arrby);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.onStartNative(this.mNativeHandle);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.onStopNative(this.mNativeHandle);
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        if (!this.mDestroyed) {
            this.onWindowFocusChangedNative(this.mNativeHandle, bl);
        }
    }

    @UnsupportedAppUsage
    void setWindowFlags(int n, int n2) {
        this.getWindow().setFlags(n, n2);
    }

    @UnsupportedAppUsage
    void setWindowFormat(int n) {
        this.getWindow().setFormat(n);
    }

    @UnsupportedAppUsage
    void showIme(int n) {
        this.mIMM.showSoftInput(this.mNativeContentView, n);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
        if (!this.mDestroyed) {
            this.mCurSurfaceHolder = surfaceHolder;
            this.onSurfaceChangedNative(this.mNativeHandle, surfaceHolder.getSurface(), n, n2, n3);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!this.mDestroyed) {
            this.mCurSurfaceHolder = surfaceHolder;
            this.onSurfaceCreatedNative(this.mNativeHandle, surfaceHolder.getSurface());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.mCurSurfaceHolder = null;
        if (!this.mDestroyed) {
            this.onSurfaceDestroyedNative(this.mNativeHandle);
        }
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
        if (!this.mDestroyed) {
            this.mCurSurfaceHolder = surfaceHolder;
            this.onSurfaceRedrawNeededNative(this.mNativeHandle, surfaceHolder.getSurface());
        }
    }

    static class NativeContentView
    extends View {
        NativeActivity mActivity;

        public NativeContentView(Context context) {
            super(context);
        }

        public NativeContentView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

}

