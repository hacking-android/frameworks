/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.IWallpaperManager;
import android.app.IWallpaperManagerCallback;
import android.app.WallpaperColors;
import android.app.WallpaperInfo;
import android.app._$$Lambda$WallpaperManager$Globals$1AcnQUORvPlCjJoNqdxfQT4o4Nw;
import android.app._$$Lambda$WallpaperManager$Globals$2yG7V1sbMECCnlFTLyjKWKqNoYI;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadSystemException;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.WindowManagerGlobal;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import libcore.io.IoUtils;

public class WallpaperManager {
    public static final String ACTION_CHANGE_LIVE_WALLPAPER = "android.service.wallpaper.CHANGE_LIVE_WALLPAPER";
    public static final String ACTION_CROP_AND_SET_WALLPAPER = "android.service.wallpaper.CROP_AND_SET_WALLPAPER";
    public static final String ACTION_LIVE_WALLPAPER_CHOOSER = "android.service.wallpaper.LIVE_WALLPAPER_CHOOSER";
    public static final String COMMAND_DROP = "android.home.drop";
    public static final String COMMAND_SECONDARY_TAP = "android.wallpaper.secondaryTap";
    public static final String COMMAND_TAP = "android.wallpaper.tap";
    private static boolean DEBUG = false;
    public static final String EXTRA_LIVE_WALLPAPER_COMPONENT = "android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT";
    public static final String EXTRA_NEW_WALLPAPER_ID = "android.service.wallpaper.extra.ID";
    public static final int FLAG_LOCK = 2;
    public static final int FLAG_SYSTEM = 1;
    private static final String PROP_LOCK_WALLPAPER = "ro.config.lock_wallpaper";
    private static final String PROP_WALLPAPER = "ro.config.wallpaper";
    private static final String PROP_WALLPAPER_COMPONENT = "ro.config.wallpaper_component";
    private static String TAG = "WallpaperManager";
    public static final String WALLPAPER_PREVIEW_META_DATA = "android.wallpaper.preview";
    @UnsupportedAppUsage
    private static Globals sGlobals;
    private static final Object sSync;
    private final Context mContext;
    private float mWallpaperXStep = -1.0f;
    private float mWallpaperYStep = -1.0f;

    static {
        DEBUG = false;
        sSync = new Object[0];
    }

    WallpaperManager(IWallpaperManager iWallpaperManager, Context context, Handler handler) {
        this.mContext = context;
        WallpaperManager.initGlobals(iWallpaperManager, context.getMainLooper());
    }

    private void copyStreamToWallpaperFile(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        FileUtils.copy(inputStream, fileOutputStream);
    }

    public static ComponentName getDefaultWallpaperComponent(Context context) {
        Object object = null;
        Object object2 = SystemProperties.get(PROP_WALLPAPER_COMPONENT);
        if (!TextUtils.isEmpty((CharSequence)object2)) {
            object = ComponentName.unflattenFromString((String)object2);
        }
        object2 = object;
        if (object == null) {
            String string2 = context.getString(17039848);
            object2 = object;
            if (!TextUtils.isEmpty(string2)) {
                object2 = ComponentName.unflattenFromString(string2);
            }
        }
        object = object2;
        if (object2 != null) {
            try {
                context.getPackageManager().getPackageInfo(((ComponentName)object2).getPackageName(), 786432);
                object = object2;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                object = null;
            }
        }
        return object;
    }

    public static WallpaperManager getInstance(Context context) {
        return (WallpaperManager)context.getSystemService("wallpaper");
    }

    private static RectF getMaxCropRect(int n, int n2, int n3, int n4, float f, float f2) {
        RectF rectF = new RectF();
        if ((float)n / (float)n2 > (float)n3 / (float)n4) {
            rectF.top = 0.0f;
            rectF.bottom = n2;
            f2 = (float)n3 * ((float)n2 / (float)n4);
            rectF.left = ((float)n - f2) * f;
            rectF.right = rectF.left + f2;
        } else {
            rectF.left = 0.0f;
            rectF.right = n;
            f = (float)n4 * ((float)n / (float)n3);
            rectF.top = ((float)n2 - f) * f2;
            rectF.bottom = rectF.top + f;
        }
        return rectF;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void initGlobals(IWallpaperManager iWallpaperManager, Looper looper) {
        Object object = sSync;
        synchronized (object) {
            if (sGlobals == null) {
                Globals globals;
                sGlobals = globals = new Globals(iWallpaperManager, looper);
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public static InputStream openDefaultWallpaper(Context object, int n) {
        if (n == 2) {
            return null;
        }
        Object object2 = SystemProperties.get(PROP_WALLPAPER);
        if (!TextUtils.isEmpty((CharSequence)object2) && ((File)(object2 = new File((String)object2))).exists()) {
            try {
                object2 = new FileInputStream((File)object2);
                return object2;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        try {
            object = ((Context)object).getResources().openRawResource(17302138);
            return object;
        }
        catch (Resources.NotFoundException notFoundException) {
            return null;
        }
    }

    private final void validateRect(Rect rect) {
        if (rect != null && rect.isEmpty()) {
            throw new IllegalArgumentException("visibleCrop rectangle must be valid and non-empty");
        }
    }

    public void addOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener, Handler handler) {
        this.addOnColorsChangedListener(onColorsChangedListener, handler, this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public void addOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener, Handler handler, int n) {
        sGlobals.addOnColorsChangedListener(onColorsChangedListener, handler, n, this.mContext.getDisplayId());
    }

    public void clear() throws IOException {
        this.setStream(WallpaperManager.openDefaultWallpaper(this.mContext, 1), null, false);
    }

    public void clear(int n) throws IOException {
        if ((n & 1) != 0) {
            this.clear();
        }
        if ((n & 2) != 0) {
            this.clearWallpaper(2, this.mContext.getUserId());
        }
    }

    public void clearWallpaper() {
        this.clearWallpaper(2, this.mContext.getUserId());
        this.clearWallpaper(1, this.mContext.getUserId());
    }

    @SystemApi
    public void clearWallpaper(int n, int n2) {
        if (sGlobals.mService != null) {
            try {
                sGlobals.mService.clearWallpaper(this.mContext.getOpPackageName(), n, n2);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    public void clearWallpaperOffsets(IBinder iBinder) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperPosition(iBinder, -1.0f, -1.0f, -1.0f, -1.0f);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void forgetLoadedWallpaper() {
        sGlobals.forgetLoadedWallpaper();
    }

    @UnsupportedAppUsage
    public Bitmap getBitmap() {
        return this.getBitmap(false);
    }

    @UnsupportedAppUsage
    public Bitmap getBitmap(boolean bl) {
        return this.getBitmapAsUser(this.mContext.getUserId(), bl);
    }

    public Bitmap getBitmapAsUser(int n, boolean bl) {
        return sGlobals.peekWallpaperBitmap(this.mContext, true, 1, n, bl);
    }

    public Drawable getBuiltInDrawable() {
        return this.getBuiltInDrawable(0, 0, false, 0.0f, 0.0f, 1);
    }

    public Drawable getBuiltInDrawable(int n) {
        return this.getBuiltInDrawable(0, 0, false, 0.0f, 0.0f, n);
    }

    public Drawable getBuiltInDrawable(int n, int n2, boolean bl, float f, float f2) {
        return this.getBuiltInDrawable(n, n2, bl, f, f2, 1);
    }

    public Drawable getBuiltInDrawable(int n, int n2, boolean bl, float f, float f2, int n3) {
        if (sGlobals.mService != null) {
            if (n3 != 1 && n3 != 2) {
                throw new IllegalArgumentException("Must request exactly one kind of wallpaper");
            }
            Resources resources = this.mContext.getResources();
            float f3 = Math.max(0.0f, Math.min(1.0f, f));
            f = Math.max(0.0f, Math.min(1.0f, f2));
            Object object = WallpaperManager.openDefaultWallpaper(this.mContext, n3);
            if (object == null) {
                if (DEBUG) {
                    object = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("default wallpaper stream ");
                    stringBuilder.append(n3);
                    stringBuilder.append(" is null");
                    Log.w((String)object, stringBuilder.toString());
                }
                return null;
            }
            Object object2 = new BufferedInputStream((InputStream)object);
            if (n > 0 && n2 > 0) {
                object = new BitmapFactory.Options();
                ((BitmapFactory.Options)object).inJustDecodeBounds = true;
                BitmapFactory.decodeStream((InputStream)object2, null, (BitmapFactory.Options)object);
                if (((BitmapFactory.Options)object).outWidth != 0 && ((BitmapFactory.Options)object).outHeight != 0) {
                    int n4 = ((BitmapFactory.Options)object).outWidth;
                    int n5 = ((BitmapFactory.Options)object).outHeight;
                    Object object3 = new BufferedInputStream(WallpaperManager.openDefaultWallpaper(this.mContext, n3));
                    n = Math.min(n4, n);
                    int n6 = Math.min(n5, n2);
                    if (bl) {
                        object = WallpaperManager.getMaxCropRect(n4, n5, n, n6, f3, f);
                    } else {
                        n2 = n;
                        f3 = (float)(n4 - n2) * f3;
                        f2 = n2;
                        f = (float)(n5 - n6) * f;
                        object = new RectF(f3, f, f2 + f3, (float)n6 + f);
                    }
                    Object object4 = new Rect();
                    ((RectF)object).roundOut((Rect)object4);
                    if (((Rect)object4).width() > 0 && ((Rect)object4).height() > 0) {
                        n2 = Math.min(((Rect)object4).width() / n, ((Rect)object4).height() / n6);
                        object2 = null;
                        try {
                            object2 = object = BitmapRegionDecoder.newInstance((InputStream)object3, true);
                        }
                        catch (IOException iOException) {
                            Log.w(TAG, "cannot open region decoder for default wallpaper");
                        }
                        object = null;
                        if (object2 != null) {
                            object = new BitmapFactory.Options();
                            if (n2 > 1) {
                                ((BitmapFactory.Options)object).inSampleSize = n2;
                            }
                            object = ((BitmapRegionDecoder)object2).decodeRegion((Rect)object4, (BitmapFactory.Options)object);
                            ((BitmapRegionDecoder)object2).recycle();
                        }
                        if (object == null) {
                            object2 = new BufferedInputStream(WallpaperManager.openDefaultWallpaper(this.mContext, n3));
                            object3 = new BitmapFactory.Options();
                            if (n2 > 1) {
                                ((BitmapFactory.Options)object3).inSampleSize = n2;
                            }
                            if ((object2 = BitmapFactory.decodeStream((InputStream)object2, null, (BitmapFactory.Options)object3)) != null) {
                                object = Bitmap.createBitmap((Bitmap)object2, ((Rect)object4).left, ((Rect)object4).top, ((Rect)object4).width(), ((Rect)object4).height());
                            }
                        }
                        if (object == null) {
                            Log.w(TAG, "cannot decode default wallpaper");
                            return null;
                        }
                        if (n > 0 && n6 > 0 && (((Bitmap)object).getWidth() != n || ((Bitmap)object).getHeight() != n6)) {
                            object4 = new Matrix();
                            object3 = new RectF(0.0f, 0.0f, ((Bitmap)object).getWidth(), ((Bitmap)object).getHeight());
                            object2 = new RectF(0.0f, 0.0f, n, n6);
                            ((Matrix)object4).setRectToRect((RectF)object3, (RectF)object2, Matrix.ScaleToFit.FILL);
                            object2 = Bitmap.createBitmap((int)((RectF)object2).width(), (int)((RectF)object2).height(), Bitmap.Config.ARGB_8888);
                            if (object2 != null) {
                                Canvas canvas = new Canvas((Bitmap)object2);
                                object3 = new Paint();
                                ((Paint)object3).setFilterBitmap(true);
                                canvas.drawBitmap((Bitmap)object, (Matrix)object4, (Paint)object3);
                                object = object2;
                            }
                        }
                        return new BitmapDrawable(resources, (Bitmap)object);
                    }
                    Log.w(TAG, "crop has bad values for full size image");
                    return null;
                }
                Log.e(TAG, "default wallpaper dimensions are 0");
                return null;
            }
            return new BitmapDrawable(resources, BitmapFactory.decodeStream((InputStream)object2, null, null));
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    public Intent getCropAndSetWallpaperIntent(Uri parcelable) {
        if (parcelable != null) {
            if ("content".equals(parcelable.getScheme())) {
                PackageManager packageManager = this.mContext.getPackageManager();
                Intent intent = new Intent(ACTION_CROP_AND_SET_WALLPAPER, (Uri)parcelable);
                intent.addFlags(1);
                parcelable = packageManager.resolveActivity(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 65536);
                if (parcelable != null) {
                    intent.setPackage(parcelable.activityInfo.packageName);
                    if (packageManager.queryIntentActivities(intent, 0).size() > 0) {
                        return intent;
                    }
                }
                intent.setPackage(this.mContext.getString(17039773));
                if (packageManager.queryIntentActivities(intent, 0).size() > 0) {
                    return intent;
                }
                throw new IllegalArgumentException("Cannot use passed URI to set wallpaper; check that the type returned by ContentProvider matches image/*");
            }
            throw new IllegalArgumentException("Image URI must be of the content scheme type");
        }
        throw new IllegalArgumentException("Image URI must not be null");
    }

    public int getDesiredMinimumHeight() {
        if (sGlobals.mService != null) {
            try {
                int n = sGlobals.mService.getHeightHint(this.mContext.getDisplayId());
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    public int getDesiredMinimumWidth() {
        if (sGlobals.mService != null) {
            try {
                int n = sGlobals.mService.getWidthHint(this.mContext.getDisplayId());
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    public Drawable getDrawable() {
        Object object = sGlobals.peekWallpaperBitmap(this.mContext, true, 1);
        if (object != null) {
            object = new BitmapDrawable(this.mContext.getResources(), (Bitmap)object);
            ((Drawable)object).setDither(false);
            return object;
        }
        return null;
    }

    public Drawable getFastDrawable() {
        Bitmap bitmap = sGlobals.peekWallpaperBitmap(this.mContext, true, 1);
        if (bitmap != null) {
            return new FastBitmapDrawable(bitmap);
        }
        return null;
    }

    @UnsupportedAppUsage
    public IWallpaperManager getIWallpaperManager() {
        return sGlobals.mService;
    }

    public WallpaperColors getWallpaperColors(int n) {
        return this.getWallpaperColors(n, this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public WallpaperColors getWallpaperColors(int n, int n2) {
        return sGlobals.getWallpaperColors(n, n2, this.mContext.getDisplayId());
    }

    public ParcelFileDescriptor getWallpaperFile(int n) {
        return this.getWallpaperFile(n, this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public ParcelFileDescriptor getWallpaperFile(int n, int n2) {
        if (n != 1 && n != 2) {
            throw new IllegalArgumentException("Must request exactly one kind of wallpaper");
        }
        if (sGlobals.mService != null) {
            try {
                Parcelable parcelable = new Bundle();
                parcelable = sGlobals.mService.getWallpaper(this.mContext.getOpPackageName(), null, n, (Bundle)parcelable, n2);
                return parcelable;
            }
            catch (SecurityException securityException) {
                if (this.mContext.getApplicationInfo().targetSdkVersion < 27) {
                    Log.w(TAG, "No permission to access wallpaper, suppressing exception to avoid crashing legacy app.");
                    return null;
                }
                throw securityException;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    public int getWallpaperId(int n) {
        return this.getWallpaperIdForUser(n, this.mContext.getUserId());
    }

    public int getWallpaperIdForUser(int n, int n2) {
        try {
            if (sGlobals.mService != null) {
                return sGlobals.mService.getWallpaperIdForUser(n, n2);
            }
            Log.w(TAG, "WallpaperService not running");
            DeadSystemException deadSystemException = new DeadSystemException();
            RuntimeException runtimeException = new RuntimeException(deadSystemException);
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public WallpaperInfo getWallpaperInfo() {
        return this.getWallpaperInfo(this.mContext.getUserId());
    }

    public WallpaperInfo getWallpaperInfo(int n) {
        try {
            if (sGlobals.mService != null) {
                return sGlobals.mService.getWallpaperInfo(n);
            }
            Log.w(TAG, "WallpaperService not running");
            DeadSystemException deadSystemException = new DeadSystemException();
            RuntimeException runtimeException = new RuntimeException(deadSystemException);
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean hasResourceWallpaper(int n) {
        if (sGlobals.mService != null) {
            try {
                Object object = this.mContext.getResources();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("res:");
                stringBuilder.append(((Resources)object).getResourceName(n));
                object = stringBuilder.toString();
                boolean bl = sGlobals.mService.hasNamedWallpaper((String)object);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    public boolean isSetWallpaperAllowed() {
        if (sGlobals.mService != null) {
            try {
                boolean bl = sGlobals.mService.isSetWallpaperAllowed(this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    public boolean isWallpaperBackupEligible(int n) {
        if (sGlobals.mService != null) {
            try {
                boolean bl = sGlobals.mService.isWallpaperBackupEligible(n, this.mContext.getUserId());
                return bl;
            }
            catch (RemoteException remoteException) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception querying wallpaper backup eligibility: ");
                stringBuilder.append(remoteException.getMessage());
                Log.e(string2, stringBuilder.toString());
                return false;
            }
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    public boolean isWallpaperSupported() {
        if (sGlobals.mService != null) {
            try {
                boolean bl = sGlobals.mService.isWallpaperSupported(this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    public Drawable peekDrawable() {
        Object object = sGlobals.peekWallpaperBitmap(this.mContext, false, 1);
        if (object != null) {
            object = new BitmapDrawable(this.mContext.getResources(), (Bitmap)object);
            ((Drawable)object).setDither(false);
            return object;
        }
        return null;
    }

    public Drawable peekFastDrawable() {
        Bitmap bitmap = sGlobals.peekWallpaperBitmap(this.mContext, false, 1);
        if (bitmap != null) {
            return new FastBitmapDrawable(bitmap);
        }
        return null;
    }

    public void removeOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener) {
        this.removeOnColorsChangedListener(onColorsChangedListener, this.mContext.getUserId());
    }

    public void removeOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener, int n) {
        sGlobals.removeOnColorsChangedListener(onColorsChangedListener, n, this.mContext.getDisplayId());
    }

    public void sendWallpaperCommand(IBinder iBinder, String string2, int n, int n2, int n3, Bundle bundle) {
        try {
            WindowManagerGlobal.getWindowSession().sendWallpaperCommand(iBinder, string2, n, n2, n3, bundle, false);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int setBitmap(Bitmap bitmap, Rect rect, boolean bl) throws IOException {
        return this.setBitmap(bitmap, rect, bl, 3);
    }

    public int setBitmap(Bitmap bitmap, Rect rect, boolean bl, int n) throws IOException {
        return this.setBitmap(bitmap, rect, bl, n, this.mContext.getUserId());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int setBitmap(Bitmap bitmap, Rect object, boolean bl, int n, int n2) throws IOException {
        Object var9_11;
        ParcelFileDescriptor parcelFileDescriptor;
        this.validateRect((Rect)object);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        Bundle bundle = new Bundle();
        WallpaperSetCompletion wallpaperSetCompletion = new WallpaperSetCompletion();
        try {
            parcelFileDescriptor = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), (Rect)object, bl, bundle, n, wallpaperSetCompletion, n2);
            if (parcelFileDescriptor == null) return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
            var9_11 = null;
            object = var9_11;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        object = var9_11;
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
        object = autoCloseOutputStream;
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, autoCloseOutputStream);
        object = autoCloseOutputStream;
        ((FileOutputStream)autoCloseOutputStream).close();
        object = autoCloseOutputStream;
        wallpaperSetCompletion.waitForCompletion();
        {
            catch (Throwable throwable) {
                IoUtils.closeQuietly((AutoCloseable)object);
                throw throwable;
            }
        }
        IoUtils.closeQuietly((AutoCloseable)autoCloseOutputStream);
        return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
    }

    public void setBitmap(Bitmap bitmap) throws IOException {
        this.setBitmap(bitmap, null, true);
    }

    @SystemApi
    public void setDisplayOffset(IBinder iBinder, int n, int n2) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperDisplayOffset(iBinder, n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setDisplayPadding(Rect object) {
        try {
            if (sGlobals.mService != null) {
                sGlobals.mService.setDisplayPadding((Rect)object, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
                return;
            }
            Log.w(TAG, "WallpaperService not running");
            object = new DeadSystemException();
            RuntimeException runtimeException = new RuntimeException((Throwable)object);
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) {
        if (sGlobals.mService != null) {
            try {
                boolean bl = sGlobals.mService.setLockWallpaperCallback(iWallpaperManagerCallback);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int setResource(int n, int n2) throws IOException {
        Object object2;
        Object object;
        ParcelFileDescriptor parcelFileDescriptor;
        Resources resources;
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        Bundle bundle = new Bundle();
        WallpaperSetCompletion wallpaperSetCompletion = new WallpaperSetCompletion();
        try {
            resources = this.mContext.getResources();
            object = sGlobals.mService;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("res:");
            ((StringBuilder)object2).append(resources.getResourceName(n));
            parcelFileDescriptor = object.setWallpaper(((StringBuilder)object2).toString(), this.mContext.getOpPackageName(), null, false, bundle, n2, wallpaperSetCompletion, this.mContext.getUserId());
            if (parcelFileDescriptor == null) return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
            object = object2 = null;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        object = object2;
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
        object = object2 = autoCloseOutputStream;
        this.copyStreamToWallpaperFile(resources.openRawResource(n), (FileOutputStream)object2);
        object = object2;
        ((FileOutputStream)object2).close();
        object = object2;
        wallpaperSetCompletion.waitForCompletion();
        {
            catch (Throwable throwable) {
                IoUtils.closeQuietly((AutoCloseable)object);
                throw throwable;
            }
        }
        IoUtils.closeQuietly((AutoCloseable)object2);
        return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
    }

    public void setResource(int n) throws IOException {
        this.setResource(n, 3);
    }

    public int setStream(InputStream inputStream, Rect rect, boolean bl) throws IOException {
        return this.setStream(inputStream, rect, bl, 3);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int setStream(InputStream inputStream, Rect object, boolean bl, int n) throws IOException {
        Object var8_10;
        ParcelFileDescriptor parcelFileDescriptor;
        this.validateRect((Rect)object);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        Bundle bundle = new Bundle();
        WallpaperSetCompletion wallpaperSetCompletion = new WallpaperSetCompletion();
        try {
            parcelFileDescriptor = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), (Rect)object, bl, bundle, n, wallpaperSetCompletion, this.mContext.getUserId());
            if (parcelFileDescriptor == null) return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
            var8_10 = null;
            object = var8_10;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        object = var8_10;
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
        object = autoCloseOutputStream;
        this.copyStreamToWallpaperFile(inputStream, autoCloseOutputStream);
        object = autoCloseOutputStream;
        ((FileOutputStream)autoCloseOutputStream).close();
        object = autoCloseOutputStream;
        wallpaperSetCompletion.waitForCompletion();
        {
            catch (Throwable throwable) {
                IoUtils.closeQuietly((AutoCloseable)object);
                throw throwable;
            }
        }
        IoUtils.closeQuietly((AutoCloseable)autoCloseOutputStream);
        return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
    }

    public void setStream(InputStream inputStream) throws IOException {
        this.setStream(inputStream, null, true);
    }

    @SystemApi
    public boolean setWallpaperComponent(ComponentName componentName) {
        return this.setWallpaperComponent(componentName, this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public boolean setWallpaperComponent(ComponentName componentName, int n) {
        if (sGlobals.mService != null) {
            try {
                sGlobals.mService.setWallpaperComponentChecked(componentName, this.mContext.getOpPackageName(), n);
                return true;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "WallpaperService not running");
        throw new RuntimeException(new DeadSystemException());
    }

    public void setWallpaperOffsetSteps(float f, float f2) {
        this.mWallpaperXStep = f;
        this.mWallpaperYStep = f2;
    }

    public void setWallpaperOffsets(IBinder iBinder, float f, float f2) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperPosition(iBinder, f, f2, this.mWallpaperXStep, this.mWallpaperYStep);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void suggestDesiredDimensions(int var1_1, int var2_2) {
        block6 : {
            block5 : {
                try {
                    var3_3 = SystemProperties.getInt("sys.max_texture_size", 0);
                }
                catch (RemoteException var4_4) {
                    throw var4_4.rethrowFromSystemServer();
                }
                catch (Exception var4_5) {
                    var3_3 = 0;
                }
                var5_7 = var1_1;
                var6_8 = var2_2;
                if (var3_3 <= 0) ** GOTO lbl-1000
                if (var1_1 > var3_3) break block5;
                var5_7 = var1_1;
                var6_8 = var2_2;
                if (var2_2 <= var3_3) ** GOTO lbl-1000
            }
            var7_9 = (float)var2_2 / (float)var1_1;
            if (var1_1 <= var2_2) break block6;
            var6_8 = (int)((double)((float)var3_3 * var7_9) + 0.5);
            var5_7 = var3_3;
            ** GOTO lbl-1000
        }
        var5_7 = (int)((double)((float)var3_3 / var7_9) + 0.5);
        var6_8 = var3_3;
lbl-1000: // 4 sources:
        {
            if (Globals.access$200(WallpaperManager.sGlobals) != null) {
                Globals.access$200(WallpaperManager.sGlobals).setDimensionHints(var5_7, var6_8, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
                return;
            }
            Log.w(WallpaperManager.TAG, "WallpaperService not running");
            var8_10 = new DeadSystemException();
            var4_6 = new RuntimeException(var8_10);
            throw var4_6;
        }
    }

    static class FastBitmapDrawable
    extends Drawable {
        private final Bitmap mBitmap;
        private int mDrawLeft;
        private int mDrawTop;
        private final int mHeight;
        private final Paint mPaint;
        private final int mWidth;

        private FastBitmapDrawable(Bitmap bitmap) {
            this.mBitmap = bitmap;
            this.mWidth = bitmap.getWidth();
            this.mHeight = bitmap.getHeight();
            this.setBounds(0, 0, this.mWidth, this.mHeight);
            this.mPaint = new Paint();
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(this.mBitmap, this.mDrawLeft, this.mDrawTop, this.mPaint);
        }

        @Override
        public int getIntrinsicHeight() {
            return this.mHeight;
        }

        @Override
        public int getIntrinsicWidth() {
            return this.mWidth;
        }

        @Override
        public int getMinimumHeight() {
            return this.mHeight;
        }

        @Override
        public int getMinimumWidth() {
            return this.mWidth;
        }

        @Override
        public int getOpacity() {
            return -1;
        }

        @Override
        public void setAlpha(int n) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        @Override
        public void setBounds(int n, int n2, int n3, int n4) {
            this.mDrawLeft = (n3 - n - this.mWidth) / 2 + n;
            this.mDrawTop = (n4 - n2 - this.mHeight) / 2 + n2;
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        @Override
        public void setDither(boolean bl) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        @Override
        public void setFilterBitmap(boolean bl) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }
    }

    private static class Globals
    extends IWallpaperManagerCallback.Stub {
        private Bitmap mCachedWallpaper;
        private int mCachedWallpaperUserId;
        private boolean mColorCallbackRegistered;
        private final ArrayList<Pair<OnColorsChangedListener, Handler>> mColorListeners = new ArrayList();
        private Bitmap mDefaultWallpaper;
        private Handler mMainLooperHandler;
        private final IWallpaperManager mService;

        Globals(IWallpaperManager iWallpaperManager, Looper looper) {
            this.mService = iWallpaperManager;
            this.mMainLooperHandler = new Handler(looper);
            this.forgetLoadedWallpaper();
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private Bitmap getCurrentWallpaperLocked(Context var1_1, int var2_3, boolean var3_4) {
            if (this.mService == null) {
                Log.w(WallpaperManager.access$000(), "WallpaperService not running");
                return null;
            }
            var4_5 = new Bundle();
            var1_1 = this.mService.getWallpaper(var1_1.getOpPackageName(), this, 1, (Bundle)var4_5, var2_3);
            if (var1_1 == null) return null;
            {
                catch (RemoteException var1_2) {
                    throw var1_2.rethrowFromSystemServer();
                }
            }
            var4_5 = new BitmapFactory.Options();
            if (var3_4) {
                var4_5.inPreferredConfig = Bitmap.Config.HARDWARE;
            }
            var4_5 = BitmapFactory.decodeFileDescriptor(var1_1.getFileDescriptor(), null, (BitmapFactory.Options)var4_5);
            IoUtils.closeQuietly((AutoCloseable)var1_1);
            return var4_5;
            {
                catch (Throwable var4_6) {
                    ** GOTO lbl27
                }
                catch (OutOfMemoryError var4_7) {}
                {
                    Log.w(WallpaperManager.access$000(), "Can't decode file", var4_7);
                }
                IoUtils.closeQuietly((AutoCloseable)var1_1);
                return null;
lbl27: // 1 sources:
                IoUtils.closeQuietly((AutoCloseable)var1_1);
                throw var4_6;
            }
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private Bitmap getDefaultWallpaper(Context object, int n) {
            Throwable throwable2222;
            if ((object = WallpaperManager.openDefaultWallpaper((Context)object, n)) == null) return null;
            Object object2 = new BitmapFactory.Options();
            object2 = BitmapFactory.decodeStream((InputStream)object, null, (BitmapFactory.Options)object2);
            IoUtils.closeQuietly((AutoCloseable)object);
            return object2;
            {
                catch (Throwable throwable2222) {
                }
                catch (OutOfMemoryError outOfMemoryError) {}
                {
                    Log.w(TAG, "Can't decode stream", outOfMemoryError);
                }
                IoUtils.closeQuietly((AutoCloseable)object);
                return null;
            }
            IoUtils.closeQuietly((AutoCloseable)object);
            throw throwable2222;
        }

        static /* synthetic */ boolean lambda$removeOnColorsChangedListener$0(OnColorsChangedListener onColorsChangedListener, Pair pair) {
            boolean bl = pair.first == onColorsChangedListener;
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void addOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener, Handler handler, int n, int n2) {
            synchronized (this) {
                boolean bl = this.mColorCallbackRegistered;
                if (!bl) {
                    try {
                        this.mService.registerWallpaperColorsCallback(this, n, n2);
                        this.mColorCallbackRegistered = true;
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TAG, "Can't register for color updates", remoteException);
                    }
                }
                ArrayList<Pair<OnColorsChangedListener, Handler>> arrayList = this.mColorListeners;
                Pair<OnColorsChangedListener, Handler> pair = new Pair<OnColorsChangedListener, Handler>(onColorsChangedListener, handler);
                arrayList.add(pair);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void forgetLoadedWallpaper() {
            synchronized (this) {
                this.mCachedWallpaper = null;
                this.mCachedWallpaperUserId = 0;
                this.mDefaultWallpaper = null;
                return;
            }
        }

        WallpaperColors getWallpaperColors(int n, int n2, int n3) {
            if (n != 2 && n != 1) {
                throw new IllegalArgumentException("Must request colors for exactly one kind of wallpaper");
            }
            try {
                WallpaperColors wallpaperColors = this.mService.getWallpaperColors(n, n2, n3);
                return wallpaperColors;
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public /* synthetic */ void lambda$onWallpaperColorsChanged$1$WallpaperManager$Globals(Pair pair, WallpaperColors wallpaperColors, int n, int n2) {
            Globals globals = sGlobals;
            // MONITORENTER : globals
            boolean bl = this.mColorListeners.contains(pair);
            // MONITOREXIT : globals
            if (!bl) return;
            ((OnColorsChangedListener)pair.first).onColorsChanged(wallpaperColors, n, n2);
        }

        @Override
        public void onWallpaperChanged() {
            this.forgetLoadedWallpaper();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int n, int n2) {
            synchronized (this) {
                Iterator<Pair<OnColorsChangedListener, Handler>> iterator = this.mColorListeners.iterator();
                while (iterator.hasNext()) {
                    Pair<OnColorsChangedListener, Handler> pair = iterator.next();
                    Handler handler = (Handler)pair.second;
                    if (pair.second == null) {
                        handler = this.mMainLooperHandler;
                    }
                    _$$Lambda$WallpaperManager$Globals$1AcnQUORvPlCjJoNqdxfQT4o4Nw _$$Lambda$WallpaperManager$Globals$1AcnQUORvPlCjJoNqdxfQT4o4Nw = new _$$Lambda$WallpaperManager$Globals$1AcnQUORvPlCjJoNqdxfQT4o4Nw(this, pair, wallpaperColors, n, n2);
                    handler.post(_$$Lambda$WallpaperManager$Globals$1AcnQUORvPlCjJoNqdxfQT4o4Nw);
                }
                return;
            }
        }

        public Bitmap peekWallpaperBitmap(Context context, boolean bl, int n) {
            return this.peekWallpaperBitmap(context, bl, n, context.getUserId(), false);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public Bitmap peekWallpaperBitmap(Context object, boolean bl, int n, int n2, boolean bl2) {
            Object object2 = this.mService;
            if (object2 != null) {
                try {
                    boolean bl3 = object2.isWallpaperSupported(((Context)object).getOpPackageName());
                    if (!bl3) {
                        return null;
                    }
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            // MONITORENTER : this
            if (this.mCachedWallpaper != null && this.mCachedWallpaperUserId == n2 && !this.mCachedWallpaper.isRecycled()) {
                object = this.mCachedWallpaper;
                // MONITOREXIT : this
                return object;
            }
            this.mCachedWallpaper = null;
            this.mCachedWallpaperUserId = 0;
            try {
                this.mCachedWallpaper = this.getCurrentWallpaperLocked((Context)object, n2, bl2);
                this.mCachedWallpaperUserId = n2;
            }
            catch (SecurityException securityException) {
                if (object.getApplicationInfo().targetSdkVersion >= 27) throw securityException;
                Log.w(TAG, "No permission to access wallpaper, suppressing exception to avoid crashing legacy app.");
            }
            catch (OutOfMemoryError outOfMemoryError) {
                object2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Out of memory loading the current wallpaper: ");
                stringBuilder.append(outOfMemoryError);
                Log.w((String)object2, stringBuilder.toString());
            }
            if (this.mCachedWallpaper != null) {
                object = this.mCachedWallpaper;
                // MONITOREXIT : this
                return object;
            }
            // MONITOREXIT : this
            if (!bl) return null;
            Bitmap bitmap = this.mDefaultWallpaper;
            object2 = bitmap;
            if (bitmap != null) return object2;
            object2 = this.getDefaultWallpaper((Context)object, n);
            // MONITORENTER : this
            this.mDefaultWallpaper = object2;
            // MONITOREXIT : this
            return object2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void removeOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener, int n, int n2) {
            synchronized (this) {
                ArrayList<Pair<OnColorsChangedListener, Handler>> arrayList = this.mColorListeners;
                _$$Lambda$WallpaperManager$Globals$2yG7V1sbMECCnlFTLyjKWKqNoYI _$$Lambda$WallpaperManager$Globals$2yG7V1sbMECCnlFTLyjKWKqNoYI = new _$$Lambda$WallpaperManager$Globals$2yG7V1sbMECCnlFTLyjKWKqNoYI(onColorsChangedListener);
                arrayList.removeIf(_$$Lambda$WallpaperManager$Globals$2yG7V1sbMECCnlFTLyjKWKqNoYI);
                if (this.mColorListeners.size() == 0 && this.mColorCallbackRegistered) {
                    this.mColorCallbackRegistered = false;
                    try {
                        this.mService.unregisterWallpaperColorsCallback(this, n, n2);
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TAG, "Can't unregister color updates", remoteException);
                    }
                }
                return;
            }
        }
    }

    public static interface OnColorsChangedListener {
        public void onColorsChanged(WallpaperColors var1, int var2);

        default public void onColorsChanged(WallpaperColors wallpaperColors, int n, int n2) {
            this.onColorsChanged(wallpaperColors, n);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SetWallpaperFlags {
    }

    private class WallpaperSetCompletion
    extends IWallpaperManagerCallback.Stub {
        final CountDownLatch mLatch = new CountDownLatch(1);

        @Override
        public void onWallpaperChanged() throws RemoteException {
            this.mLatch.countDown();
        }

        @Override
        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int n, int n2) throws RemoteException {
            sGlobals.onWallpaperColorsChanged(wallpaperColors, n, n2);
        }

        public void waitForCompletion() {
            try {
                this.mLatch.await(30L, TimeUnit.SECONDS);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

}

