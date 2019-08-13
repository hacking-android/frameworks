/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.widget.-$
 *  android.widget.-$$Lambda
 *  android.widget.-$$Lambda$RemoteViews
 *  android.widget.-$$Lambda$RemoteViews$xYCMzfQwRCAW2azHo-bWqQ9R0Wk
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.Application;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.appwidget.AppWidgetHostView;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.-$;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RemoteViewsAdapter;
import android.widget.RemoteViewsListAdapter;
import android.widget.TextView;
import android.widget._$$Lambda$RemoteViews$SetOnClickResponse$9rKnU2QqCzJhBC39ZrKYXob0_MA;
import android.widget._$$Lambda$RemoteViews$xYCMzfQwRCAW2azHo_bWqQ9R0Wk;
import com.android.internal.R;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class RemoteViews
implements Parcelable,
LayoutInflater.Filter {
    private static final Action ACTION_NOOP;
    private static final int BITMAP_REFLECTION_ACTION_TAG = 12;
    public static final Parcelable.Creator<RemoteViews> CREATOR;
    private static final OnClickHandler DEFAULT_ON_CLICK_HANDLER;
    static final String EXTRA_REMOTEADAPTER_APPWIDGET_ID = "remoteAdapterAppWidgetId";
    static final String EXTRA_REMOTEADAPTER_ON_LIGHT_BACKGROUND = "remoteAdapterOnLightBackground";
    public static final String EXTRA_SHARED_ELEMENT_BOUNDS = "android.widget.extra.SHARED_ELEMENT_BOUNDS";
    public static final int FLAG_REAPPLY_DISALLOWED = 1;
    public static final int FLAG_USE_LIGHT_BACKGROUND_LAYOUT = 4;
    public static final int FLAG_WIDGET_IS_COLLECTION_CHILD = 2;
    private static final int LAYOUT_PARAM_ACTION_TAG = 19;
    private static final String LOG_TAG = "RemoteViews";
    private static final int MAX_NESTED_VIEWS = 10;
    private static final int MODE_HAS_LANDSCAPE_AND_PORTRAIT = 1;
    private static final int MODE_NORMAL = 0;
    private static final int OVERRIDE_TEXT_COLORS_TAG = 20;
    private static final int REFLECTION_ACTION_TAG = 2;
    private static final int SET_DRAWABLE_TINT_TAG = 3;
    private static final int SET_EMPTY_VIEW_ACTION_TAG = 6;
    private static final int SET_INT_TAG_TAG = 22;
    private static final int SET_ON_CLICK_RESPONSE_TAG = 1;
    private static final int SET_PENDING_INTENT_TEMPLATE_TAG = 8;
    private static final int SET_REMOTE_INPUTS_ACTION_TAG = 18;
    private static final int SET_REMOTE_VIEW_ADAPTER_INTENT_TAG = 10;
    private static final int SET_REMOTE_VIEW_ADAPTER_LIST_TAG = 15;
    private static final int SET_RIPPLE_DRAWABLE_COLOR_TAG = 21;
    private static final int TEXT_VIEW_DRAWABLE_ACTION_TAG = 11;
    private static final int TEXT_VIEW_SIZE_ACTION_TAG = 13;
    private static final int VIEW_CONTENT_NAVIGATION_TAG = 5;
    private static final int VIEW_GROUP_ACTION_ADD_TAG = 4;
    private static final int VIEW_GROUP_ACTION_REMOVE_TAG = 7;
    private static final int VIEW_PADDING_ACTION_TAG = 14;
    private static final MethodKey sLookupKey;
    private static final ArrayMap<MethodKey, MethodArgs> sMethods;
    @UnsupportedAppUsage
    private ArrayList<Action> mActions;
    @UnsupportedAppUsage
    public ApplicationInfo mApplication;
    private int mApplyFlags = 0;
    @UnsupportedAppUsage
    private BitmapCache mBitmapCache;
    private final Map<Class, Object> mClassCookies;
    private boolean mIsRoot = true;
    private RemoteViews mLandscape = null;
    @UnsupportedAppUsage
    private final int mLayoutId;
    private int mLightBackgroundLayoutId = 0;
    @UnsupportedAppUsage
    private RemoteViews mPortrait = null;

    static {
        DEFAULT_ON_CLICK_HANDLER = _$$Lambda$RemoteViews$xYCMzfQwRCAW2azHo_bWqQ9R0Wk.INSTANCE;
        sMethods = new ArrayMap();
        sLookupKey = new MethodKey();
        ACTION_NOOP = new RuntimeAction(){

            @Override
            public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            }
        };
        CREATOR = new Parcelable.Creator<RemoteViews>(){

            @Override
            public RemoteViews createFromParcel(Parcel parcel) {
                return new RemoteViews(parcel);
            }

            public RemoteViews[] newArray(int n) {
                return new RemoteViews[n];
            }
        };
    }

    protected RemoteViews(ApplicationInfo applicationInfo, int n) {
        this.mApplication = applicationInfo;
        this.mLayoutId = n;
        this.mBitmapCache = new BitmapCache();
        this.mClassCookies = null;
    }

    public RemoteViews(Parcel parcel) {
        this(parcel, null, null, 0, null);
    }

    private RemoteViews(Parcel parcel, BitmapCache object, ApplicationInfo applicationInfo, int n, Map<Class, Object> map) {
        if (n > 10 && UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            throw new IllegalArgumentException("Too many nested views.");
        }
        ++n;
        int n2 = parcel.readInt();
        if (object == null) {
            this.mBitmapCache = new BitmapCache(parcel);
            this.mClassCookies = parcel.copyClassCookies();
        } else {
            this.setBitmapCache((BitmapCache)object);
            this.mClassCookies = map;
            this.setNotRoot();
        }
        if (n2 == 0) {
            object = parcel.readInt() == 0 ? applicationInfo : ApplicationInfo.CREATOR.createFromParcel(parcel);
            this.mApplication = object;
            this.mLayoutId = parcel.readInt();
            this.mLightBackgroundLayoutId = parcel.readInt();
            this.readActionsFromParcel(parcel, n);
        } else {
            this.mLandscape = new RemoteViews(parcel, this.mBitmapCache, applicationInfo, n, this.mClassCookies);
            this.mPortrait = new RemoteViews(parcel, this.mBitmapCache, this.mLandscape.mApplication, n, this.mClassCookies);
            object = this.mPortrait;
            this.mApplication = ((RemoteViews)object).mApplication;
            this.mLayoutId = ((RemoteViews)object).mLayoutId;
            this.mLightBackgroundLayoutId = ((RemoteViews)object).mLightBackgroundLayoutId;
        }
        this.mApplyFlags = parcel.readInt();
    }

    public RemoteViews(RemoteViews remoteViews) {
        this.mBitmapCache = remoteViews.mBitmapCache;
        this.mApplication = remoteViews.mApplication;
        this.mIsRoot = remoteViews.mIsRoot;
        this.mLayoutId = remoteViews.mLayoutId;
        this.mLightBackgroundLayoutId = remoteViews.mLightBackgroundLayoutId;
        this.mApplyFlags = remoteViews.mApplyFlags;
        this.mClassCookies = remoteViews.mClassCookies;
        if (remoteViews.hasLandscapeAndPortraitLayouts()) {
            this.mLandscape = new RemoteViews(remoteViews.mLandscape);
            this.mPortrait = new RemoteViews(remoteViews.mPortrait);
        }
        if (remoteViews.mActions != null) {
            Parcel parcel = Parcel.obtain();
            parcel.putClassCookies(this.mClassCookies);
            remoteViews.writeActionsToParcel(parcel);
            parcel.setDataPosition(0);
            this.readActionsFromParcel(parcel, 0);
            parcel.recycle();
        }
        this.setBitmapCache(new BitmapCache());
    }

    public RemoteViews(RemoteViews object, RemoteViews object2) {
        if (object != null && object2 != null) {
            if (((RemoteViews)object).hasSameAppInfo(((RemoteViews)object2).mApplication)) {
                this.mApplication = ((RemoteViews)object2).mApplication;
                this.mLayoutId = ((RemoteViews)object2).mLayoutId;
                this.mLightBackgroundLayoutId = ((RemoteViews)object2).mLightBackgroundLayoutId;
                this.mLandscape = object;
                this.mPortrait = object2;
                this.mBitmapCache = new BitmapCache();
                this.configureRemoteViewsAsChild((RemoteViews)object);
                this.configureRemoteViewsAsChild((RemoteViews)object2);
                object2 = ((RemoteViews)object2).mClassCookies;
                object = object2 != null ? object2 : ((RemoteViews)object).mClassCookies;
                this.mClassCookies = object;
                return;
            }
            throw new RuntimeException("Both RemoteViews must share the same package and user");
        }
        throw new RuntimeException("Both RemoteViews must be non-null");
    }

    public RemoteViews(String string2, int n) {
        this(RemoteViews.getApplicationInfo(string2, UserHandle.myUserId()), n);
    }

    public RemoteViews(String string2, int n, int n2) {
        this(RemoteViews.getApplicationInfo(string2, n), n2);
    }

    private void addAction(Action action) {
        if (!this.hasLandscapeAndPortraitLayouts()) {
            if (this.mActions == null) {
                this.mActions = new ArrayList();
            }
            this.mActions.add(action);
            return;
        }
        throw new RuntimeException("RemoteViews specifying separate landscape and portrait layouts cannot be modified. Instead, fully configure the landscape and portrait layouts individually before constructing the combined layout.");
    }

    private void configureRemoteViewsAsChild(RemoteViews remoteViews) {
        remoteViews.setBitmapCache(this.mBitmapCache);
        remoteViews.setNotRoot();
    }

    private Action getActionFromParcel(Parcel object, int n) {
        int n2 = ((Parcel)object).readInt();
        switch (n2) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Tag ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" not found");
                throw new ActionException(((StringBuilder)object).toString());
            }
            case 22: {
                return new SetIntTagAction((Parcel)object);
            }
            case 21: {
                return new SetRippleDrawableColor((Parcel)object);
            }
            case 20: {
                return new OverrideTextColorsAction((Parcel)object);
            }
            case 19: {
                return new LayoutParamAction((Parcel)object);
            }
            case 18: {
                return new SetRemoteInputsAction((Parcel)object);
            }
            case 15: {
                return new SetRemoteViewsAdapterList((Parcel)object);
            }
            case 14: {
                return new ViewPaddingAction((Parcel)object);
            }
            case 13: {
                return new TextViewSizeAction((Parcel)object);
            }
            case 12: {
                return new BitmapReflectionAction((Parcel)object);
            }
            case 11: {
                return new TextViewDrawableAction((Parcel)object);
            }
            case 10: {
                return new SetRemoteViewsAdapterIntent((Parcel)object);
            }
            case 8: {
                return new SetPendingIntentTemplate((Parcel)object);
            }
            case 7: {
                return new ViewGroupActionRemove((Parcel)object);
            }
            case 6: {
                return new SetEmptyView((Parcel)object);
            }
            case 5: {
                return new ViewContentNavigation((Parcel)object);
            }
            case 4: {
                return new ViewGroupActionAdd((Parcel)object, this.mBitmapCache, this.mApplication, n, this.mClassCookies);
            }
            case 3: {
                return new SetDrawableTint((Parcel)object);
            }
            case 2: {
                return new ReflectionAction((Parcel)object);
            }
            case 1: 
        }
        return new SetOnClickResponse((Parcel)object);
    }

    private static ApplicationInfo getApplicationInfo(String string2, int n) {
        block6 : {
            Parcelable parcelable;
            block8 : {
                Application application;
                Object object;
                block7 : {
                    if (string2 == null) {
                        return null;
                    }
                    application = ActivityThread.currentApplication();
                    if (application == null) break block6;
                    object = application.getApplicationInfo();
                    if (UserHandle.getUserId(((ApplicationInfo)object).uid) != n) break block7;
                    parcelable = object;
                    if (((ApplicationInfo)object).packageName.equals(string2)) break block8;
                }
                try {
                    object = application.getBaseContext();
                    parcelable = new UserHandle(n);
                    parcelable = ((Context)object).createPackageContextAsUser(string2, 0, (UserHandle)parcelable).getApplicationInfo();
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No such package ");
                    stringBuilder.append(string2);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            return parcelable;
        }
        throw new IllegalStateException("Cannot create remote views out of an aplication.");
    }

    private AsyncApplyTask getAsyncApplyTask(Context context, ViewGroup viewGroup, OnViewAppliedListener onViewAppliedListener, OnClickHandler onClickHandler) {
        return new AsyncApplyTask(this.getRemoteViewsToApply(context), viewGroup, context, onViewAppliedListener, onClickHandler, null);
    }

    private Context getContextForResources(Context context) {
        if (this.mApplication != null) {
            if (context.getUserId() == UserHandle.getUserId(this.mApplication.uid) && context.getPackageName().equals(this.mApplication.packageName)) {
                return context;
            }
            try {
                Context context2 = context.createApplicationContext(this.mApplication, 4);
                return context2;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Package name ");
                stringBuilder.append(this.mApplication.packageName);
                stringBuilder.append(" not found");
                Log.e(LOG_TAG, stringBuilder.toString());
            }
        }
        return context;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private MethodHandle getMethod(View var1_1, String var2_4, Class<?> var3_5, boolean var4_6) {
        block11 : {
            block10 : {
                block12 : {
                    var5_7 = var1_1.getClass();
                    var6_8 = RemoteViews.sMethods;
                    // MONITORENTER : var6_8
                    RemoteViews.sLookupKey.set((Class)var5_7, (Class)var3_5, var2_4);
                    var1_1 = var7_9 = RemoteViews.sMethods.get(RemoteViews.sLookupKey);
                    if (var7_9 != null) break block11;
                    if (var3_5 != null) ** GOTO lbl11
                    var7_9 = var5_7.getMethod(var2_4, new Class[0]);
                    break block12;
lbl11: // 1 sources:
                    var7_9 = var5_7.getMethod(var2_4, new Class[]{var3_5});
                }
                if (!var7_9.isAnnotationPresent(RemotableViewMethod.class)) break block10;
                var1_1 = new MethodArgs();
                var1_1.syncMethod = MethodHandles.publicLookup().unreflect((Method)var7_9);
                var1_1.asyncMethodName = var7_9.getAnnotation(RemotableViewMethod.class).asyncImpl();
                var7_9 = new MethodKey();
                var7_9.set((Class)var5_7, (Class)var3_5, var2_4);
                RemoteViews.sMethods.put((MethodKey)var7_9, (MethodArgs)var1_1);
                break block11;
            }
            try {
                var1_1 = new StringBuilder();
                var1_1.append("view: ");
                var1_1.append(var5_7.getName());
                var1_1.append(" can't use method with RemoteViews: ");
                var1_1.append(var2_4);
                var1_1.append(RemoteViews.getParameters(var3_5));
                var7_9 = new ActionException(var1_1.toString());
                throw var7_9;
            }
            catch (IllegalAccessException | NoSuchMethodException var1_2) {
                var1_3 = new StringBuilder();
                var1_3.append("view: ");
                var1_3.append(var5_7.getName());
                var1_3.append(" doesn't have method: ");
                var1_3.append(var2_4);
                var1_3.append(RemoteViews.getParameters(var3_5));
                var7_9 = new ActionException(var1_3.toString());
                throw var7_9;
            }
        }
        if (!var4_6) {
            var1_1 = var1_1.syncMethod;
            // MONITOREXIT : var6_8
            return var1_1;
        }
        if (var1_1.asyncMethodName.isEmpty()) {
            // MONITOREXIT : var6_8
            return null;
        }
        if (var1_1.asyncMethod == null) {
            var3_5 = var1_1.syncMethod.type().dropParameterTypes(0, 1).changeReturnType(Runnable.class);
            try {
                var1_1.asyncMethod = MethodHandles.publicLookup().findVirtual((Class<?>)var5_7, var1_1.asyncMethodName, (MethodType)var3_5);
            }
            catch (IllegalAccessException | NoSuchMethodException var7_10) {
                var7_11 = new StringBuilder();
                var7_11.append("Async implementation declared as ");
                var7_11.append(var1_1.asyncMethodName);
                var7_11.append(" but not defined for ");
                var7_11.append(var2_4);
                var7_11.append(": public Runnable ");
                var7_11.append(var1_1.asyncMethodName);
                var7_11.append(" (");
                var7_11.append(TextUtils.join((CharSequence)",", var3_5.parameterArray()));
                var7_11.append(")");
                var5_7 = new ActionException(var7_11.toString());
                throw var5_7;
            }
        }
        var1_1 = var1_1.asyncMethod;
        // MONITOREXIT : var6_8
        return var1_1;
    }

    private static String getParameters(Class<?> class_) {
        if (class_ == null) {
            return "()";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(class_);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private RemoteViews getRemoteViewsToApply(Context context) {
        if (this.hasLandscapeAndPortraitLayouts()) {
            if (context.getResources().getConfiguration().orientation == 2) {
                return this.mLandscape;
            }
            return this.mPortrait;
        }
        return this;
    }

    public static Rect getSourceBounds(View view) {
        float f = view.getContext().getResources().getCompatibilityInfo().applicationScale;
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        Rect rect = new Rect();
        rect.left = (int)((float)arrn[0] * f + 0.5f);
        rect.top = (int)((float)arrn[1] * f + 0.5f);
        rect.right = (int)((float)(arrn[0] + view.getWidth()) * f + 0.5f);
        rect.bottom = (int)((float)(arrn[1] + view.getHeight()) * f + 0.5f);
        return rect;
    }

    private boolean hasLandscapeAndPortraitLayouts() {
        boolean bl = this.mLandscape != null && this.mPortrait != null;
        return bl;
    }

    private View inflateView(Context context, RemoteViews remoteViews, ViewGroup viewGroup) {
        return this.inflateView(context, remoteViews, viewGroup, 0);
    }

    private View inflateView(Context object, RemoteViews remoteViews, ViewGroup viewGroup, int n) {
        RemoteViewsContextWrapper remoteViewsContextWrapper;
        ContextWrapper contextWrapper = remoteViewsContextWrapper = new RemoteViewsContextWrapper((Context)object, this.getContextForResources((Context)object));
        if (n != 0) {
            contextWrapper = new ContextThemeWrapper((Context)remoteViewsContextWrapper, n);
        }
        object = ((LayoutInflater)((Context)object).getSystemService("layout_inflater")).cloneInContext(contextWrapper);
        ((LayoutInflater)object).setFilter(this);
        object = ((LayoutInflater)object).inflate(remoteViews.getLayoutId(), viewGroup, false);
        ((View)object).setTagInternal(16908312, remoteViews.getLayoutId());
        return object;
    }

    static /* synthetic */ boolean lambda$static$0(View view, PendingIntent pendingIntent, RemoteResponse remoteResponse) {
        return RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
    }

    private void performApply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
        if (this.mActions != null) {
            if (onClickHandler == null) {
                onClickHandler = DEFAULT_ON_CLICK_HANDLER;
            }
            int n = this.mActions.size();
            for (int i = 0; i < n; ++i) {
                this.mActions.get(i).apply(view, viewGroup, onClickHandler);
            }
        }
    }

    private void readActionsFromParcel(Parcel parcel, int n) {
        int n2 = parcel.readInt();
        if (n2 > 0) {
            this.mActions = new ArrayList(n2);
            for (int i = 0; i < n2; ++i) {
                this.mActions.add(this.getActionFromParcel(parcel, n));
            }
        }
    }

    private void setBitmapCache(BitmapCache bitmapCache) {
        this.mBitmapCache = bitmapCache;
        if (!this.hasLandscapeAndPortraitLayouts()) {
            ArrayList<Action> arrayList = this.mActions;
            if (arrayList != null) {
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    this.mActions.get(i).setBitmapCache(bitmapCache);
                }
            }
        } else {
            this.mLandscape.setBitmapCache(bitmapCache);
            this.mPortrait.setBitmapCache(bitmapCache);
        }
    }

    public static boolean startPendingIntent(View object, PendingIntent pendingIntent, Pair<Intent, ActivityOptions> pair) {
        try {
            object = ((View)object).getContext();
            ((Context)object).startIntentSender(pendingIntent.getIntentSender(), (Intent)pair.first, 0, 0, 0, ((ActivityOptions)pair.second).toBundle());
            return true;
        }
        catch (Exception exception) {
            Log.e(LOG_TAG, "Cannot send pending intent due to unknown exception: ", exception);
            return false;
        }
        catch (IntentSender.SendIntentException sendIntentException) {
            Log.e(LOG_TAG, "Cannot send pending intent: ", sendIntentException);
            return false;
        }
    }

    private CancellationSignal startTaskOnExecutor(AsyncApplyTask asyncApplyTask, Executor executor) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(asyncApplyTask);
        if (executor == null) {
            executor = AsyncTask.THREAD_POOL_EXECUTOR;
        }
        asyncApplyTask.executeOnExecutor(executor, new Void[0]);
        return cancellationSignal;
    }

    private static void visitIconUri(Icon icon, Consumer<Uri> consumer) {
        if (icon != null && icon.getType() == 4) {
            consumer.accept(icon.getUri());
        }
    }

    private void writeActionsToParcel(Parcel parcel) {
        ArrayList<Action> arrayList = this.mActions;
        int n = arrayList != null ? arrayList.size() : 0;
        parcel.writeInt(n);
        for (int i = 0; i < n; ++i) {
            arrayList = this.mActions.get(i);
            parcel.writeInt(((Action)((Object)arrayList)).getActionTag());
            int n2 = ((Action)((Object)arrayList)).hasSameAppInfo(this.mApplication) ? 2 : 0;
            arrayList.writeToParcel(parcel, n2);
        }
    }

    public void addFlags(int n) {
        this.mApplyFlags |= n;
    }

    public void addView(int n, RemoteViews parcelable) {
        parcelable = parcelable == null ? new ViewGroupActionRemove(n) : new ViewGroupActionAdd(n, (RemoteViews)parcelable);
        this.addAction((Action)parcelable);
    }

    @UnsupportedAppUsage
    public void addView(int n, RemoteViews remoteViews, int n2) {
        this.addAction(new ViewGroupActionAdd(n, remoteViews, n2));
    }

    public View apply(Context context, ViewGroup viewGroup) {
        return this.apply(context, viewGroup, null);
    }

    public View apply(Context object, ViewGroup viewGroup, OnClickHandler onClickHandler) {
        RemoteViews remoteViews = this.getRemoteViewsToApply((Context)object);
        object = this.inflateView((Context)object, remoteViews, viewGroup);
        remoteViews.performApply((View)object, viewGroup, onClickHandler);
        return object;
    }

    public CancellationSignal applyAsync(Context context, ViewGroup viewGroup, Executor executor, OnViewAppliedListener onViewAppliedListener) {
        return this.applyAsync(context, viewGroup, executor, onViewAppliedListener, null);
    }

    public CancellationSignal applyAsync(Context context, ViewGroup viewGroup, Executor executor, OnViewAppliedListener onViewAppliedListener, OnClickHandler onClickHandler) {
        return this.startTaskOnExecutor(this.getAsyncApplyTask(context, viewGroup, onViewAppliedListener, onClickHandler), executor);
    }

    public View applyWithTheme(Context object, ViewGroup viewGroup, OnClickHandler onClickHandler, int n) {
        RemoteViews remoteViews = this.getRemoteViewsToApply((Context)object);
        object = this.inflateView((Context)object, remoteViews, viewGroup, n);
        remoteViews.performApply((View)object, viewGroup, onClickHandler);
        return object;
    }

    @Deprecated
    public RemoteViews clone() {
        Preconditions.checkState(this.mIsRoot, "RemoteView has been attached to another RemoteView. May only clone the root of a RemoteView hierarchy.");
        return new RemoteViews(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public int estimateMemoryUsage() {
        return this.mBitmapCache.getBitmapMemory();
    }

    public RemoteViews getDarkTextViews() {
        if (this.hasFlags(4)) {
            return this;
        }
        try {
            this.addFlags(4);
            RemoteViews remoteViews = new RemoteViews(this);
            return remoteViews;
        }
        finally {
            this.mApplyFlags &= -5;
        }
    }

    public int getLayoutId() {
        int n;
        if (!this.hasFlags(4) || (n = this.mLightBackgroundLayoutId) == 0) {
            n = this.mLayoutId;
        }
        return n;
    }

    public String getPackage() {
        Object object = this.mApplication;
        object = object != null ? ((ApplicationInfo)object).packageName : null;
        return object;
    }

    public int getSequenceNumber() {
        ArrayList<Action> arrayList = this.mActions;
        int n = arrayList == null ? 0 : arrayList.size();
        return n;
    }

    public boolean hasFlags(int n) {
        boolean bl = (this.mApplyFlags & n) == n;
        return bl;
    }

    public boolean hasSameAppInfo(ApplicationInfo applicationInfo) {
        boolean bl = this.mApplication.packageName.equals(applicationInfo.packageName) && this.mApplication.uid == applicationInfo.uid;
        return bl;
    }

    @UnsupportedAppUsage
    public void mergeRemoteViews(RemoteViews object) {
        Action action;
        int n;
        if (object == null) {
            return;
        }
        ArrayList<Action> arrayList = new RemoteViews((RemoteViews)object);
        object = new HashMap();
        if (this.mActions == null) {
            this.mActions = new ArrayList();
        }
        int n2 = this.mActions.size();
        for (n = 0; n < n2; ++n) {
            action = this.mActions.get(n);
            ((HashMap)object).put(action.getUniqueKey(), action);
        }
        arrayList = ((RemoteViews)arrayList).mActions;
        if (arrayList == null) {
            return;
        }
        n2 = arrayList.size();
        for (n = 0; n < n2; ++n) {
            action = (Action)arrayList.get(n);
            String string2 = ((Action)arrayList.get(n)).getUniqueKey();
            int n3 = ((Action)arrayList.get(n)).mergeBehavior();
            if (((HashMap)object).containsKey(string2) && n3 == 0) {
                this.mActions.remove(((HashMap)object).get(string2));
                ((HashMap)object).remove(string2);
            }
            if (n3 != 0 && n3 != 1) continue;
            this.mActions.add(action);
        }
        this.mBitmapCache = new BitmapCache();
        this.setBitmapCache(this.mBitmapCache);
    }

    @Override
    public boolean onLoadClass(Class class_) {
        return class_.isAnnotationPresent(RemoteView.class);
    }

    public void overrideTextColors(int n) {
        this.addAction(new OverrideTextColorsAction(n));
    }

    public boolean prefersAsyncApply() {
        ArrayList<Action> arrayList = this.mActions;
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                if (!this.mActions.get(i).prefersAsyncApply()) continue;
                return true;
            }
        }
        return false;
    }

    public void reapply(Context context, View view) {
        this.reapply(context, view, null);
    }

    public void reapply(Context object, View view, OnClickHandler onClickHandler) {
        object = this.getRemoteViewsToApply((Context)object);
        if (this.hasLandscapeAndPortraitLayouts() && ((Integer)view.getTag(16908312)).intValue() != ((RemoteViews)object).getLayoutId()) {
            throw new RuntimeException("Attempting to re-apply RemoteViews to a view that that does not share the same root layout id.");
        }
        RemoteViews.super.performApply(view, (ViewGroup)view.getParent(), onClickHandler);
    }

    public CancellationSignal reapplyAsync(Context context, View view, Executor executor, OnViewAppliedListener onViewAppliedListener) {
        return this.reapplyAsync(context, view, executor, onViewAppliedListener, null);
    }

    public CancellationSignal reapplyAsync(Context context, View view, Executor executor, OnViewAppliedListener onViewAppliedListener, OnClickHandler onClickHandler) {
        RemoteViews remoteViews = this.getRemoteViewsToApply(context);
        if (this.hasLandscapeAndPortraitLayouts() && ((Integer)view.getTag(16908312)).intValue() != remoteViews.getLayoutId()) {
            throw new RuntimeException("Attempting to re-apply RemoteViews to a view that that does not share the same root layout id.");
        }
        return this.startTaskOnExecutor(new AsyncApplyTask(remoteViews, (ViewGroup)view.getParent(), context, onViewAppliedListener, onClickHandler, view), executor);
    }

    public void reduceImageSizes(int n, int n2) {
        ArrayList<Bitmap> arrayList = this.mBitmapCache.mBitmaps;
        for (int i = 0; i < arrayList.size(); ++i) {
            arrayList.set(i, Icon.scaleDownIfNecessary(arrayList.get(i), n, n2));
        }
    }

    public void removeAllViews(int n) {
        this.addAction(new ViewGroupActionRemove(n));
    }

    public void removeAllViewsExceptId(int n, int n2) {
        this.addAction(new ViewGroupActionRemove(n, n2));
    }

    public void setAccessibilityTraversalAfter(int n, int n2) {
        this.setInt(n, "setAccessibilityTraversalAfter", n2);
    }

    public void setAccessibilityTraversalBefore(int n, int n2) {
        this.setInt(n, "setAccessibilityTraversalBefore", n2);
    }

    public void setBitmap(int n, String string2, Bitmap bitmap) {
        this.addAction(new BitmapReflectionAction(n, string2, bitmap));
    }

    public void setBoolean(int n, String string2, boolean bl) {
        this.addAction(new ReflectionAction(n, string2, 1, bl));
    }

    public void setBundle(int n, String string2, Bundle bundle) {
        this.addAction(new ReflectionAction(n, string2, 13, bundle));
    }

    public void setByte(int n, String string2, byte by) {
        this.addAction(new ReflectionAction(n, string2, 2, by));
    }

    public void setChar(int n, String string2, char c) {
        this.addAction(new ReflectionAction(n, string2, 8, Character.valueOf(c)));
    }

    public void setCharSequence(int n, String string2, CharSequence charSequence) {
        this.addAction(new ReflectionAction(n, string2, 10, charSequence));
    }

    public void setChronometer(int n, long l, String string2, boolean bl) {
        this.setLong(n, "setBase", l);
        this.setString(n, "setFormat", string2);
        this.setBoolean(n, "setStarted", bl);
    }

    public void setChronometerCountDown(int n, boolean bl) {
        this.setBoolean(n, "setCountDown", bl);
    }

    public void setColorStateList(int n, String string2, ColorStateList colorStateList) {
        this.addAction(new ReflectionAction(n, string2, 15, colorStateList));
    }

    public void setContentDescription(int n, CharSequence charSequence) {
        this.setCharSequence(n, "setContentDescription", charSequence);
    }

    public void setDisplayedChild(int n, int n2) {
        this.setInt(n, "setDisplayedChild", n2);
    }

    public void setDouble(int n, String string2, double d) {
        this.addAction(new ReflectionAction(n, string2, 7, d));
    }

    public void setDrawableTint(int n, boolean bl, int n2, PorterDuff.Mode mode) {
        this.addAction(new SetDrawableTint(n, bl, n2, mode));
    }

    public void setEmptyView(int n, int n2) {
        this.addAction(new SetEmptyView(n, n2));
    }

    public void setFloat(int n, String string2, float f) {
        this.addAction(new ReflectionAction(n, string2, 6, Float.valueOf(f)));
    }

    public void setIcon(int n, String string2, Icon icon) {
        this.addAction(new ReflectionAction(n, string2, 16, icon));
    }

    public void setImageViewBitmap(int n, Bitmap bitmap) {
        this.setBitmap(n, "setImageBitmap", bitmap);
    }

    public void setImageViewIcon(int n, Icon icon) {
        this.setIcon(n, "setImageIcon", icon);
    }

    public void setImageViewResource(int n, int n2) {
        this.setInt(n, "setImageResource", n2);
    }

    public void setImageViewUri(int n, Uri uri) {
        this.setUri(n, "setImageURI", uri);
    }

    public void setInt(int n, String string2, int n2) {
        this.addAction(new ReflectionAction(n, string2, 4, n2));
    }

    public void setIntTag(int n, int n2, int n3) {
        this.addAction(new SetIntTagAction(n, n2, n3));
    }

    public void setIntent(int n, String string2, Intent intent) {
        this.addAction(new ReflectionAction(n, string2, 14, intent));
    }

    public void setLabelFor(int n, int n2) {
        this.setInt(n, "setLabelFor", n2);
    }

    public void setLightBackgroundLayoutId(int n) {
        this.mLightBackgroundLayoutId = n;
    }

    public void setLong(int n, String string2, long l) {
        this.addAction(new ReflectionAction(n, string2, 5, l));
    }

    void setNotRoot() {
        this.mIsRoot = false;
    }

    public void setOnClickFillInIntent(int n, Intent intent) {
        this.setOnClickResponse(n, RemoteResponse.fromFillInIntent(intent));
    }

    public void setOnClickPendingIntent(int n, PendingIntent pendingIntent) {
        this.setOnClickResponse(n, RemoteResponse.fromPendingIntent(pendingIntent));
    }

    public void setOnClickResponse(int n, RemoteResponse remoteResponse) {
        this.addAction(new SetOnClickResponse(n, remoteResponse));
    }

    public void setPendingIntentTemplate(int n, PendingIntent pendingIntent) {
        this.addAction(new SetPendingIntentTemplate(n, pendingIntent));
    }

    public void setProgressBackgroundTintList(int n, ColorStateList colorStateList) {
        this.addAction(new ReflectionAction(n, "setProgressBackgroundTintList", 15, colorStateList));
    }

    public void setProgressBar(int n, int n2, int n3, boolean bl) {
        this.setBoolean(n, "setIndeterminate", bl);
        if (!bl) {
            this.setInt(n, "setMax", n2);
            this.setInt(n, "setProgress", n3);
        }
    }

    public void setProgressIndeterminateTintList(int n, ColorStateList colorStateList) {
        this.addAction(new ReflectionAction(n, "setIndeterminateTintList", 15, colorStateList));
    }

    public void setProgressTintList(int n, ColorStateList colorStateList) {
        this.addAction(new ReflectionAction(n, "setProgressTintList", 15, colorStateList));
    }

    public void setRelativeScrollPosition(int n, int n2) {
        this.setInt(n, "smoothScrollByOffset", n2);
    }

    @Deprecated
    public void setRemoteAdapter(int n, int n2, Intent intent) {
        this.setRemoteAdapter(n2, intent);
    }

    public void setRemoteAdapter(int n, Intent intent) {
        this.addAction(new SetRemoteViewsAdapterIntent(n, intent));
    }

    @Deprecated
    @UnsupportedAppUsage
    public void setRemoteAdapter(int n, ArrayList<RemoteViews> arrayList, int n2) {
        this.addAction(new SetRemoteViewsAdapterList(n, arrayList, n2));
    }

    public void setRemoteInputs(int n, RemoteInput[] arrremoteInput) {
        this.mActions.add(new SetRemoteInputsAction(n, arrremoteInput));
    }

    public void setRippleDrawableColor(int n, ColorStateList colorStateList) {
        this.addAction(new SetRippleDrawableColor(n, colorStateList));
    }

    public void setScrollPosition(int n, int n2) {
        this.setInt(n, "smoothScrollToPosition", n2);
    }

    public void setShort(int n, String string2, short s) {
        this.addAction(new ReflectionAction(n, string2, 3, s));
    }

    public void setString(int n, String string2, String string3) {
        this.addAction(new ReflectionAction(n, string2, 9, string3));
    }

    public void setTextColor(int n, int n2) {
        this.setInt(n, "setTextColor", n2);
    }

    public void setTextColor(int n, ColorStateList colorStateList) {
        this.addAction(new ReflectionAction(n, "setTextColor", 15, colorStateList));
    }

    public void setTextViewCompoundDrawables(int n, int n2, int n3, int n4, int n5) {
        this.addAction(new TextViewDrawableAction(n, false, n2, n3, n4, n5));
    }

    public void setTextViewCompoundDrawables(int n, Icon icon, Icon icon2, Icon icon3, Icon icon4) {
        this.addAction(new TextViewDrawableAction(n, false, icon, icon2, icon3, icon4));
    }

    public void setTextViewCompoundDrawablesRelative(int n, int n2, int n3, int n4, int n5) {
        this.addAction(new TextViewDrawableAction(n, true, n2, n3, n4, n5));
    }

    public void setTextViewCompoundDrawablesRelative(int n, Icon icon, Icon icon2, Icon icon3, Icon icon4) {
        this.addAction(new TextViewDrawableAction(n, true, icon, icon2, icon3, icon4));
    }

    public void setTextViewText(int n, CharSequence charSequence) {
        this.setCharSequence(n, "setText", charSequence);
    }

    public void setTextViewTextSize(int n, int n2, float f) {
        this.addAction(new TextViewSizeAction(n, n2, f));
    }

    public void setUri(int n, String string2, Uri uri) {
        Uri uri2 = uri;
        if (uri != null) {
            uri2 = uri = uri.getCanonicalUri();
            if (StrictMode.vmFileUriExposureEnabled()) {
                uri.checkFileUriExposed("RemoteViews.setUri()");
                uri2 = uri;
            }
        }
        this.addAction(new ReflectionAction(n, string2, 11, uri2));
    }

    public void setViewLayoutMarginBottomDimen(int n, int n2) {
        this.addAction(new LayoutParamAction(n, 3, n2));
    }

    public void setViewLayoutMarginEnd(int n, int n2) {
        this.addAction(new LayoutParamAction(n, 4, n2));
    }

    public void setViewLayoutMarginEndDimen(int n, int n2) {
        this.addAction(new LayoutParamAction(n, 1, n2));
    }

    public void setViewLayoutWidth(int n, int n2) {
        if (n2 != 0 && n2 != -1 && n2 != -2) {
            throw new IllegalArgumentException("Only supports 0, WRAP_CONTENT and MATCH_PARENT");
        }
        this.mActions.add(new LayoutParamAction(n, 2, n2));
    }

    public void setViewPadding(int n, int n2, int n3, int n4, int n5) {
        this.addAction(new ViewPaddingAction(n, n2, n3, n4, n5));
    }

    public void setViewVisibility(int n, int n2) {
        this.setInt(n, "setVisibility", n2);
    }

    public void showNext(int n) {
        this.addAction(new ViewContentNavigation(n, true));
    }

    public void showPrevious(int n) {
        this.addAction(new ViewContentNavigation(n, false));
    }

    public void visitUris(Consumer<Uri> consumer) {
        if (this.mActions != null) {
            for (int i = 0; i < this.mActions.size(); ++i) {
                this.mActions.get(i).visitUris(consumer);
            }
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (!this.hasLandscapeAndPortraitLayouts()) {
            parcel.writeInt(0);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(parcel, n);
            }
            if (!this.mIsRoot && (n & 2) != 0) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                this.mApplication.writeToParcel(parcel, n);
            }
            parcel.writeInt(this.mLayoutId);
            parcel.writeInt(this.mLightBackgroundLayoutId);
            this.writeActionsToParcel(parcel);
        } else {
            parcel.writeInt(1);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(parcel, n);
            }
            this.mLandscape.writeToParcel(parcel, n);
            this.mPortrait.writeToParcel(parcel, n | 2);
        }
        parcel.writeInt(this.mApplyFlags);
    }

    private static abstract class Action
    implements Parcelable {
        public static final int MERGE_APPEND = 1;
        public static final int MERGE_IGNORE = 2;
        public static final int MERGE_REPLACE = 0;
        @UnsupportedAppUsage
        int viewId;

        private Action() {
        }

        public abstract void apply(View var1, ViewGroup var2, OnClickHandler var3) throws ActionException;

        @Override
        public int describeContents() {
            return 0;
        }

        public abstract int getActionTag();

        public String getUniqueKey() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getActionTag());
            stringBuilder.append("_");
            stringBuilder.append(this.viewId);
            return stringBuilder.toString();
        }

        public boolean hasSameAppInfo(ApplicationInfo applicationInfo) {
            return true;
        }

        public Action initActionAsync(ViewTree viewTree, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            return this;
        }

        @UnsupportedAppUsage
        public int mergeBehavior() {
            return 0;
        }

        public boolean prefersAsyncApply() {
            return false;
        }

        public void setBitmapCache(BitmapCache bitmapCache) {
        }

        public void visitUris(Consumer<Uri> consumer) {
        }
    }

    public static class ActionException
    extends RuntimeException {
        public ActionException(Exception exception) {
            super(exception);
        }

        public ActionException(String string2) {
            super(string2);
        }

        public ActionException(Throwable throwable) {
            super(throwable);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ApplyFlags {
    }

    private class AsyncApplyTask
    extends AsyncTask<Void, Void, ViewTree>
    implements CancellationSignal.OnCancelListener {
        private Action[] mActions;
        final Context mContext;
        private Exception mError;
        final OnClickHandler mHandler;
        final OnViewAppliedListener mListener;
        final ViewGroup mParent;
        final RemoteViews mRV;
        private View mResult;
        private ViewTree mTree;

        private AsyncApplyTask(RemoteViews remoteViews2, ViewGroup viewGroup, Context context, OnViewAppliedListener onViewAppliedListener, OnClickHandler onClickHandler, View view) {
            this.mRV = remoteViews2;
            this.mParent = viewGroup;
            this.mContext = context;
            this.mListener = onViewAppliedListener;
            this.mHandler = onClickHandler;
            this.mResult = view;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected ViewTree doInBackground(Void ... object) {
            block6 : {
                ViewTree viewTree;
                if (this.mResult == null) {
                    this.mResult = RemoteViews.this.inflateView(this.mContext, this.mRV, this.mParent);
                }
                this.mTree = viewTree = new ViewTree(this.mResult);
                if (this.mRV.mActions == null) break block6;
                int n = this.mRV.mActions.size();
                this.mActions = new Action[n];
                int n2 = 0;
                while (n2 < n) {
                    if (this.isCancelled()) return this.mTree;
                    this.mActions[n2] = ((Action)this.mRV.mActions.get(n2)).initActionAsync(this.mTree, this.mParent, this.mHandler);
                    ++n2;
                }
                return this.mTree;
            }
            try {
                this.mActions = null;
                return this.mTree;
            }
            catch (Exception exception) {
                this.mError = exception;
                return null;
            }
        }

        @Override
        public void onCancel() {
            this.cancel(true);
        }

        @Override
        protected void onPostExecute(ViewTree object) {
            Action[] arraction;
            Object object2;
            block12 : {
                if (this.mError == null) {
                    object2 = this.mListener;
                    if (object2 != null) {
                        object2.onViewInflated(((ViewTree)object).mRoot);
                    }
                    if (this.mActions == null) break block12;
                    object2 = this.mHandler == null ? DEFAULT_ON_CLICK_HANDLER : this.mHandler;
                    arraction = this.mActions;
                    int n = arraction.length;
                    for (int i = 0; i < n; ++i) {
                        try {
                            arraction[i].apply(((ViewTree)object).mRoot, this.mParent, (OnClickHandler)object2);
                        }
                        catch (Exception exception) {
                            this.mError = exception;
                            break;
                        }
                        continue;
                    }
                }
            }
            if ((arraction = this.mListener) != null) {
                object2 = this.mError;
                if (object2 != null) {
                    arraction.onError((Exception)object2);
                } else {
                    arraction.onViewApplied(((ViewTree)object).mRoot);
                }
            } else {
                object = this.mError;
                if (object != null) {
                    if (object instanceof ActionException) {
                        throw (ActionException)object;
                    }
                    throw new ActionException((Exception)object);
                }
            }
        }
    }

    private static class BitmapCache {
        int mBitmapMemory = -1;
        @UnsupportedAppUsage
        ArrayList<Bitmap> mBitmaps;

        public BitmapCache() {
            this.mBitmaps = new ArrayList();
        }

        public BitmapCache(Parcel parcel) {
            this.mBitmaps = parcel.createTypedArrayList(Bitmap.CREATOR);
        }

        public Bitmap getBitmapForId(int n) {
            if (n != -1 && n < this.mBitmaps.size()) {
                return this.mBitmaps.get(n);
            }
            return null;
        }

        public int getBitmapId(Bitmap bitmap) {
            if (bitmap == null) {
                return -1;
            }
            if (this.mBitmaps.contains(bitmap)) {
                return this.mBitmaps.indexOf(bitmap);
            }
            this.mBitmaps.add(bitmap);
            this.mBitmapMemory = -1;
            return this.mBitmaps.size() - 1;
        }

        public int getBitmapMemory() {
            if (this.mBitmapMemory < 0) {
                this.mBitmapMemory = 0;
                int n = this.mBitmaps.size();
                for (int i = 0; i < n; ++i) {
                    this.mBitmapMemory += this.mBitmaps.get(i).getAllocationByteCount();
                }
            }
            return this.mBitmapMemory;
        }

        public void writeBitmapsToParcel(Parcel parcel, int n) {
            parcel.writeTypedList(this.mBitmaps, n);
        }
    }

    private class BitmapReflectionAction
    extends Action {
        @UnsupportedAppUsage
        Bitmap bitmap;
        int bitmapId;
        @UnsupportedAppUsage
        String methodName;

        BitmapReflectionAction(int n, String string2, Bitmap bitmap) {
            this.bitmap = bitmap;
            this.viewId = n;
            this.methodName = string2;
            this.bitmapId = RemoteViews.this.mBitmapCache.getBitmapId(bitmap);
        }

        BitmapReflectionAction(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.methodName = parcel.readString();
            this.bitmapId = parcel.readInt();
            this.bitmap = RemoteViews.this.mBitmapCache.getBitmapForId(this.bitmapId);
        }

        @Override
        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) throws ActionException {
            new ReflectionAction(this.viewId, this.methodName, 12, this.bitmap).apply(view, viewGroup, onClickHandler);
        }

        @Override
        public int getActionTag() {
            return 12;
        }

        @Override
        public void setBitmapCache(BitmapCache bitmapCache) {
            this.bitmapId = bitmapCache.getBitmapId(this.bitmap);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeString(this.methodName);
            parcel.writeInt(this.bitmapId);
        }
    }

    private static class LayoutParamAction
    extends Action {
        public static final int LAYOUT_MARGIN_BOTTOM_DIMEN = 3;
        public static final int LAYOUT_MARGIN_END = 4;
        public static final int LAYOUT_MARGIN_END_DIMEN = 1;
        public static final int LAYOUT_WIDTH = 2;
        final int mProperty;
        final int mValue;

        public LayoutParamAction(int n, int n2, int n3) {
            this.viewId = n;
            this.mProperty = n2;
            this.mValue = n3;
        }

        public LayoutParamAction(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.mProperty = parcel.readInt();
            this.mValue = parcel.readInt();
        }

        private static int resolveDimenPixelOffset(View view, int n) {
            if (n == 0) {
                return 0;
            }
            return view.getContext().getResources().getDimensionPixelOffset(n);
        }

        @Override
        public void apply(View object, ViewGroup object2, OnClickHandler onClickHandler) {
            block9 : {
                int n;
                block8 : {
                    block5 : {
                        block6 : {
                            block7 : {
                                if ((object = ((View)object).findViewById(this.viewId)) == null) {
                                    return;
                                }
                                object2 = ((View)object).getLayoutParams();
                                if (object2 == null) {
                                    return;
                                }
                                n = this.mValue;
                                int n2 = this.mProperty;
                                if (n2 == 1) break block5;
                                if (n2 == 2) break block6;
                                if (n2 == 3) break block7;
                                if (n2 != 4) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Unknown property ");
                                    ((StringBuilder)object).append(this.mProperty);
                                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                                }
                                break block8;
                            }
                            if (object2 instanceof ViewGroup.MarginLayoutParams) {
                                ((ViewGroup.MarginLayoutParams)object2).bottomMargin = n = LayoutParamAction.resolveDimenPixelOffset((View)object, this.mValue);
                                ((View)object).setLayoutParams((ViewGroup.LayoutParams)object2);
                            }
                            break block9;
                        }
                        ((ViewGroup.LayoutParams)object2).width = this.mValue;
                        ((View)object).setLayoutParams((ViewGroup.LayoutParams)object2);
                        break block9;
                    }
                    n = LayoutParamAction.resolveDimenPixelOffset((View)object, this.mValue);
                }
                if (object2 instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams)object2).setMarginEnd(n);
                    ((View)object).setLayoutParams((ViewGroup.LayoutParams)object2);
                }
            }
        }

        @Override
        public int getActionTag() {
            return 19;
        }

        @Override
        public String getUniqueKey() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.getUniqueKey());
            stringBuilder.append(this.mProperty);
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeInt(this.mProperty);
            parcel.writeInt(this.mValue);
        }
    }

    static class MethodArgs {
        public MethodHandle asyncMethod;
        public String asyncMethodName;
        public MethodHandle syncMethod;

        MethodArgs() {
        }
    }

    static class MethodKey {
        public String methodName;
        public Class paramClass;
        public Class targetClass;

        MethodKey() {
        }

        public boolean equals(Object object) {
            boolean bl;
            block1 : {
                boolean bl2 = object instanceof MethodKey;
                bl = false;
                if (!bl2) {
                    return false;
                }
                object = (MethodKey)object;
                if (!Objects.equals(((MethodKey)object).targetClass, this.targetClass) || !Objects.equals(((MethodKey)object).paramClass, this.paramClass) || !Objects.equals(((MethodKey)object).methodName, this.methodName)) break block1;
                bl = true;
            }
            return bl;
        }

        public int hashCode() {
            return Objects.hashCode(this.targetClass) ^ Objects.hashCode(this.paramClass) ^ Objects.hashCode(this.methodName);
        }

        public void set(Class class_, Class class_2, String string2) {
            this.targetClass = class_;
            this.paramClass = class_2;
            this.methodName = string2;
        }
    }

    public static interface OnClickHandler {
        public boolean onClickHandler(View var1, PendingIntent var2, RemoteResponse var3);
    }

    public static interface OnViewAppliedListener {
        public void onError(Exception var1);

        public void onViewApplied(View var1);

        default public void onViewInflated(View view) {
        }
    }

    private class OverrideTextColorsAction
    extends Action {
        private final int textColor;

        public OverrideTextColorsAction(int n) {
            this.textColor = n;
        }

        public OverrideTextColorsAction(Parcel parcel) {
            this.textColor = parcel.readInt();
        }

        @Override
        public void apply(View view, ViewGroup object, OnClickHandler object2) {
            object = new Stack();
            ((Vector)object).add(view);
            while (!((Vector)object).isEmpty()) {
                view = (View)((Stack)object).pop();
                if (view instanceof TextView) {
                    object2 = (TextView)view;
                    ((TextView)object2).setText(ContrastColorUtil.clearColorSpans(((TextView)object2).getText()));
                    ((TextView)object2).setTextColor(this.textColor);
                }
                if (!(view instanceof ViewGroup)) continue;
                view = (ViewGroup)view;
                for (int i = 0; i < ((ViewGroup)view).getChildCount(); ++i) {
                    ((Stack)object).push(((ViewGroup)view).getChildAt(i));
                }
            }
        }

        @Override
        public int getActionTag() {
            return 20;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.textColor);
        }
    }

    private final class ReflectionAction
    extends Action {
        static final int BITMAP = 12;
        static final int BOOLEAN = 1;
        static final int BUNDLE = 13;
        static final int BYTE = 2;
        static final int CHAR = 8;
        static final int CHAR_SEQUENCE = 10;
        static final int COLOR_STATE_LIST = 15;
        static final int DOUBLE = 7;
        static final int FLOAT = 6;
        static final int ICON = 16;
        static final int INT = 4;
        static final int INTENT = 14;
        static final int LONG = 5;
        static final int SHORT = 3;
        static final int STRING = 9;
        static final int URI = 11;
        @UnsupportedAppUsage
        String methodName;
        int type;
        @UnsupportedAppUsage
        Object value;

        ReflectionAction(int n, String string2, int n2, Object object) {
            this.viewId = n;
            this.methodName = string2;
            this.type = n2;
            this.value = object;
        }

        ReflectionAction(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.methodName = parcel.readString();
            this.type = parcel.readInt();
            switch (this.type) {
                default: {
                    break;
                }
                case 16: {
                    this.value = parcel.readTypedObject(Icon.CREATOR);
                    break;
                }
                case 15: {
                    this.value = parcel.readTypedObject(ColorStateList.CREATOR);
                    break;
                }
                case 14: {
                    this.value = parcel.readTypedObject(Intent.CREATOR);
                    break;
                }
                case 13: {
                    this.value = parcel.readBundle();
                    break;
                }
                case 12: {
                    this.value = parcel.readTypedObject(Bitmap.CREATOR);
                    break;
                }
                case 11: {
                    this.value = parcel.readTypedObject(Uri.CREATOR);
                    break;
                }
                case 10: {
                    this.value = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
                    break;
                }
                case 9: {
                    this.value = parcel.readString();
                    break;
                }
                case 8: {
                    this.value = Character.valueOf((char)parcel.readInt());
                    break;
                }
                case 7: {
                    this.value = parcel.readDouble();
                    break;
                }
                case 6: {
                    this.value = Float.valueOf(parcel.readFloat());
                    break;
                }
                case 5: {
                    this.value = parcel.readLong();
                    break;
                }
                case 4: {
                    this.value = parcel.readInt();
                    break;
                }
                case 3: {
                    this.value = (short)parcel.readInt();
                    break;
                }
                case 2: {
                    this.value = parcel.readByte();
                    break;
                }
                case 1: {
                    this.value = parcel.readBoolean();
                }
            }
        }

        private Class<?> getParameterType() {
            switch (this.type) {
                default: {
                    return null;
                }
                case 16: {
                    return Icon.class;
                }
                case 15: {
                    return ColorStateList.class;
                }
                case 14: {
                    return Intent.class;
                }
                case 13: {
                    return Bundle.class;
                }
                case 12: {
                    return Bitmap.class;
                }
                case 11: {
                    return Uri.class;
                }
                case 10: {
                    return CharSequence.class;
                }
                case 9: {
                    return String.class;
                }
                case 8: {
                    return Character.TYPE;
                }
                case 7: {
                    return Double.TYPE;
                }
                case 6: {
                    return Float.TYPE;
                }
                case 5: {
                    return Long.TYPE;
                }
                case 4: {
                    return Integer.TYPE;
                }
                case 3: {
                    return Short.TYPE;
                }
                case 2: {
                    return Byte.TYPE;
                }
                case 1: 
            }
            return Boolean.TYPE;
        }

        @Override
        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }

        @Override
        public int getActionTag() {
            return 2;
        }

        @Override
        public String getUniqueKey() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.getUniqueKey());
            stringBuilder.append(this.methodName);
            stringBuilder.append(this.type);
            return stringBuilder.toString();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Action initActionAsync(ViewTree object, ViewGroup object2, OnClickHandler class_) {
            object2 = ((ViewTree)object).findViewById(this.viewId);
            if (object2 == null) {
                return ACTION_NOOP;
            }
            class_ = this.getParameterType();
            if (class_ == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("bad type: ");
                ((StringBuilder)object).append(this.type);
                throw new ActionException(((StringBuilder)object).toString());
            }
            try {
                class_ = RemoteViews.this.getMethod((View)object2, this.methodName, class_, true);
                if (class_ == null) {
                    return this;
                }
                if ((object2 = class_.invoke((View)object2, this.value)) == null) {
                    return ACTION_NOOP;
                }
                if (!(object2 instanceof ViewStub.ViewReplaceRunnable)) return new RunnableAction((Runnable)object2);
                ((ViewTree)object).createTree();
                ((ViewTree)object).findViewTreeById(this.viewId).replaceView(((ViewStub.ViewReplaceRunnable)object2).view);
                return new RunnableAction((Runnable)object2);
            }
            catch (Throwable throwable) {
                throw new ActionException(throwable);
            }
        }

        @Override
        public int mergeBehavior() {
            return this.methodName.equals("smoothScrollBy");
        }

        @Override
        public boolean prefersAsyncApply() {
            int n = this.type;
            boolean bl = n == 11 || n == 16;
            return bl;
        }

        @Override
        public void visitUris(Consumer<Uri> consumer) {
            int n = this.type;
            if (n != 11) {
                if (n == 16) {
                    RemoteViews.visitIconUri((Icon)this.value, consumer);
                }
            } else {
                consumer.accept((Uri)this.value);
            }
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeString(this.methodName);
            parcel.writeInt(this.type);
            switch (this.type) {
                default: {
                    break;
                }
                case 13: {
                    parcel.writeBundle((Bundle)this.value);
                    break;
                }
                case 11: 
                case 12: 
                case 14: 
                case 15: 
                case 16: {
                    parcel.writeTypedObject((Parcelable)this.value, n);
                    break;
                }
                case 10: {
                    TextUtils.writeToParcel((CharSequence)this.value, parcel, n);
                    break;
                }
                case 9: {
                    parcel.writeString((String)this.value);
                    break;
                }
                case 8: {
                    parcel.writeInt(((Character)this.value).charValue());
                    break;
                }
                case 7: {
                    parcel.writeDouble((Double)this.value);
                    break;
                }
                case 6: {
                    parcel.writeFloat(((Float)this.value).floatValue());
                    break;
                }
                case 5: {
                    parcel.writeLong((Long)this.value);
                    break;
                }
                case 4: {
                    parcel.writeInt((Integer)this.value);
                    break;
                }
                case 3: {
                    parcel.writeInt(((Short)this.value).shortValue());
                    break;
                }
                case 2: {
                    parcel.writeByte((Byte)this.value);
                    break;
                }
                case 1: {
                    parcel.writeBoolean((Boolean)this.value);
                }
            }
        }
    }

    public static class RemoteResponse {
        private ArrayList<String> mElementNames;
        private Intent mFillIntent;
        private PendingIntent mPendingIntent;
        private IntArray mViewIds;

        public static RemoteResponse fromFillInIntent(Intent intent) {
            RemoteResponse remoteResponse = new RemoteResponse();
            remoteResponse.mFillIntent = intent;
            return remoteResponse;
        }

        public static RemoteResponse fromPendingIntent(PendingIntent pendingIntent) {
            RemoteResponse remoteResponse = new RemoteResponse();
            remoteResponse.mPendingIntent = pendingIntent;
            return remoteResponse;
        }

        private void handleViewClick(View view, OnClickHandler onClickHandler) {
            block8 : {
                Object object;
                block7 : {
                    block6 : {
                        if (this.mPendingIntent == null) break block6;
                        object = this.mPendingIntent;
                        break block7;
                    }
                    if (this.mFillIntent == null) break block8;
                    for (object = (View)view.getParent(); !(object == null || object instanceof AdapterView || object instanceof AppWidgetHostView && !(object instanceof RemoteViewsAdapter.RemoteViewsFrameLayout)); object = (View)object.getParent()) {
                    }
                    if (!(object instanceof AdapterView)) {
                        Log.e(RemoteViews.LOG_TAG, "Collection item doesn't have AdapterView parent");
                        return;
                    }
                    if (!(((View)object).getTag() instanceof PendingIntent)) {
                        Log.e(RemoteViews.LOG_TAG, "Attempting setOnClickFillInIntent without calling setPendingIntentTemplate on parent.");
                        return;
                    }
                    object = (PendingIntent)((View)object).getTag();
                }
                onClickHandler.onClickHandler(view, (PendingIntent)object, this);
                return;
            }
            Log.e(RemoteViews.LOG_TAG, "Response has neither pendingIntent nor fillInIntent");
        }

        private void readFromParcel(Parcel parcel) {
            Object object;
            this.mPendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
            if (this.mPendingIntent == null) {
                this.mFillIntent = parcel.readTypedObject(Intent.CREATOR);
            }
            object = (object = parcel.createIntArray()) == null ? null : IntArray.wrap(object);
            this.mViewIds = object;
            this.mElementNames = parcel.createStringArrayList();
        }

        private void writeToParcel(Parcel parcel, int n) {
            Object object;
            PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntent, parcel);
            if (this.mPendingIntent == null) {
                parcel.writeTypedObject(this.mFillIntent, n);
            }
            object = (object = this.mViewIds) == null ? null : object.toArray();
            parcel.writeIntArray((int[])object);
            parcel.writeStringList(this.mElementNames);
        }

        public RemoteResponse addSharedElement(int n, String string2) {
            if (this.mViewIds == null) {
                this.mViewIds = new IntArray();
                this.mElementNames = new ArrayList();
            }
            this.mViewIds.add(n);
            this.mElementNames.add(string2);
            return this;
        }

        public Pair<Intent, ActivityOptions> getLaunchOptions(View arrayList) {
            Intent intent = this.mPendingIntent != null ? new Intent() : new Intent(this.mFillIntent);
            intent.setSourceBounds(RemoteViews.getSourceBounds((View)((Object)arrayList)));
            Object object = null;
            Context context = ((View)((Object)arrayList)).getContext();
            int[] arrn = object;
            if (context.getResources().getBoolean(17891490)) {
                arrn = context.getTheme().obtainStyledAttributes(R.styleable.Window);
                TypedArray typedArray = context.obtainStyledAttributes(arrn.getResourceId(8, 0), R.styleable.WindowAnimation);
                int n = typedArray.getResourceId(26, 0);
                arrn.recycle();
                typedArray.recycle();
                arrn = object;
                if (n != 0) {
                    arrn = ActivityOptions.makeCustomAnimation(context, n, 0);
                    arrn.setPendingIntentLaunchFlags(268435456);
                }
            }
            object = arrn;
            if (arrn == null) {
                object = arrn;
                if (this.mViewIds != null) {
                    object = arrn;
                    if (this.mElementNames != null) {
                        for (arrayList = (View)arrayList.getParent(); arrayList != null && !(arrayList instanceof AppWidgetHostView); arrayList = (View)arrayList.getParent()) {
                        }
                        object = arrn;
                        if (arrayList instanceof AppWidgetHostView) {
                            object = (AppWidgetHostView)((Object)arrayList);
                            arrn = this.mViewIds.toArray();
                            arrayList = this.mElementNames;
                            object = ((AppWidgetHostView)object).createSharedElementActivityOptions(arrn, arrayList.toArray(new String[arrayList.size()]), intent);
                        }
                    }
                }
            }
            arrayList = object;
            if (object == null) {
                arrayList = ActivityOptions.makeBasic();
                ((ActivityOptions)((Object)arrayList)).setPendingIntentLaunchFlags(268435456);
            }
            return Pair.create(intent, arrayList);
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface RemoteView {
    }

    private static class RemoteViewsContextWrapper
    extends ContextWrapper {
        private final Context mContextForResources;

        RemoteViewsContextWrapper(Context context, Context context2) {
            super(context);
            this.mContextForResources = context2;
        }

        @Override
        public String getPackageName() {
            return this.mContextForResources.getPackageName();
        }

        @Override
        public Resources getResources() {
            return this.mContextForResources.getResources();
        }

        @Override
        public Resources.Theme getTheme() {
            return this.mContextForResources.getTheme();
        }
    }

    private static final class RunnableAction
    extends RuntimeAction {
        private final Runnable mRunnable;

        RunnableAction(Runnable runnable) {
            this.mRunnable = runnable;
        }

        @Override
        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            this.mRunnable.run();
        }
    }

    private static abstract class RuntimeAction
    extends Action {
        private RuntimeAction() {
        }

        @Override
        public final int getActionTag() {
            return 0;
        }

        @Override
        public final void writeToParcel(Parcel parcel, int n) {
            throw new UnsupportedOperationException();
        }
    }

    private class SetDrawableTint
    extends Action {
        int colorFilter;
        PorterDuff.Mode filterMode;
        boolean targetBackground;

        SetDrawableTint(int n, boolean bl, int n2, PorterDuff.Mode mode) {
            this.viewId = n;
            this.targetBackground = bl;
            this.colorFilter = n2;
            this.filterMode = mode;
        }

        SetDrawableTint(Parcel parcel) {
            this.viewId = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.targetBackground = bl;
            this.colorFilter = parcel.readInt();
            this.filterMode = PorterDuff.intToMode(parcel.readInt());
        }

        @Override
        public void apply(View object, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            viewGroup = ((View)object).findViewById(this.viewId);
            if (viewGroup == null) {
                return;
            }
            object = null;
            if (this.targetBackground) {
                object = viewGroup.getBackground();
            } else if (viewGroup instanceof ImageView) {
                object = ((ImageView)((Object)viewGroup)).getDrawable();
            }
            if (object != null) {
                ((Drawable)object).mutate().setColorFilter(this.colorFilter, this.filterMode);
            }
        }

        @Override
        public int getActionTag() {
            return 3;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeInt((int)this.targetBackground);
            parcel.writeInt(this.colorFilter);
            parcel.writeInt(PorterDuff.modeToInt(this.filterMode));
        }
    }

    private class SetEmptyView
    extends Action {
        int emptyViewId;

        SetEmptyView(int n, int n2) {
            this.viewId = n;
            this.emptyViewId = n2;
        }

        SetEmptyView(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.emptyViewId = parcel.readInt();
        }

        @Override
        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            viewGroup = view.findViewById(this.viewId);
            if (!(viewGroup instanceof AdapterView)) {
                return;
            }
            viewGroup = (AdapterView)viewGroup;
            if ((view = view.findViewById(this.emptyViewId)) == null) {
                return;
            }
            ((AdapterView)viewGroup).setEmptyView(view);
        }

        @Override
        public int getActionTag() {
            return 6;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeInt(this.emptyViewId);
        }
    }

    private class SetIntTagAction
    extends Action {
        private final int mKey;
        private final int mTag;
        private final int mViewId;

        SetIntTagAction(int n, int n2, int n3) {
            this.mViewId = n;
            this.mKey = n2;
            this.mTag = n3;
        }

        SetIntTagAction(Parcel parcel) {
            this.mViewId = parcel.readInt();
            this.mKey = parcel.readInt();
            this.mTag = parcel.readInt();
        }

        @Override
        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            if ((view = view.findViewById(this.mViewId)) == null) {
                return;
            }
            view.setTagInternal(this.mKey, this.mTag);
        }

        @Override
        public int getActionTag() {
            return 22;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mKey);
            parcel.writeInt(this.mTag);
        }
    }

    private class SetOnClickResponse
    extends Action {
        final RemoteResponse mResponse;

        SetOnClickResponse(int n, RemoteResponse remoteResponse) {
            this.viewId = n;
            this.mResponse = remoteResponse;
        }

        SetOnClickResponse(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.mResponse = new RemoteResponse();
            this.mResponse.readFromParcel(parcel);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        @Override
        public void apply(View object, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            void var3_4;
            Object t = ((View)object).findViewById(this.viewId);
            if (t == null) {
                return;
            }
            if (this.mResponse.mPendingIntent != null) {
                if (RemoteViews.this.hasFlags(2)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot SetOnClickResponse for collection item (id: ");
                    stringBuilder.append(this.viewId);
                    stringBuilder.append(")");
                    Log.w(RemoteViews.LOG_TAG, stringBuilder.toString());
                    object = ((View)object).getContext().getApplicationInfo();
                    if (object != null && ((ApplicationInfo)object).targetSdkVersion >= 16) {
                        return;
                    }
                }
                ((View)t).setTagInternal(16909219, this.mResponse.mPendingIntent);
            } else {
                if (this.mResponse.mFillIntent == null) {
                    ((View)t).setOnClickListener(null);
                    return;
                }
                if (!RemoteViews.this.hasFlags(2)) {
                    Log.e(RemoteViews.LOG_TAG, "The method setOnClickFillInIntent is available only from RemoteViewsFactory (ie. on collection items).");
                    return;
                }
                if (t == object) {
                    ((View)t).setTagInternal(16908914, this.mResponse);
                    return;
                }
            }
            ((View)t).setOnClickListener(new _$$Lambda$RemoteViews$SetOnClickResponse$9rKnU2QqCzJhBC39ZrKYXob0_MA(this, (OnClickHandler)var3_4));
        }

        @Override
        public int getActionTag() {
            return 1;
        }

        public /* synthetic */ void lambda$apply$0$RemoteViews$SetOnClickResponse(OnClickHandler onClickHandler, View view) {
            this.mResponse.handleViewClick(view, onClickHandler);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            this.mResponse.writeToParcel(parcel, n);
        }
    }

    private class SetPendingIntentTemplate
    extends Action {
        @UnsupportedAppUsage
        PendingIntent pendingIntentTemplate;

        public SetPendingIntentTemplate(int n, PendingIntent pendingIntent) {
            this.viewId = n;
            this.pendingIntentTemplate = pendingIntent;
        }

        public SetPendingIntentTemplate(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.pendingIntentTemplate = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override
        public void apply(View object, ViewGroup viewGroup, final OnClickHandler onClickHandler) {
            if ((object = ((View)object).findViewById(this.viewId)) == null) {
                return;
            }
            if (object instanceof AdapterView) {
                object = (AdapterView)object;
                ((AdapterView)object).setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> object, View view, int n, long l) {
                        if (view instanceof ViewGroup) {
                            ViewGroup viewGroup;
                            ViewGroup viewGroup2 = viewGroup = (ViewGroup)view;
                            if (object instanceof AdapterViewAnimator) {
                                viewGroup2 = (ViewGroup)viewGroup.getChildAt(0);
                            }
                            if (viewGroup2 == null) {
                                return;
                            }
                            viewGroup = null;
                            int n2 = viewGroup2.getChildCount();
                            n = 0;
                            do {
                                object = viewGroup;
                                if (n >= n2) break;
                                object = viewGroup2.getChildAt(n).getTag(16908914);
                                if (object instanceof RemoteResponse) {
                                    object = (RemoteResponse)object;
                                    break;
                                }
                                ++n;
                            } while (true);
                            if (object == null) {
                                return;
                            }
                            object.handleViewClick(view, onClickHandler);
                        }
                    }
                });
                ((View)object).setTag(this.pendingIntentTemplate);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot setPendingIntentTemplate on a view which is notan AdapterView (id: ");
            ((StringBuilder)object).append(this.viewId);
            ((StringBuilder)object).append(")");
            Log.e(RemoteViews.LOG_TAG, ((StringBuilder)object).toString());
        }

        @Override
        public int getActionTag() {
            return 8;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            PendingIntent.writePendingIntentOrNullToParcel(this.pendingIntentTemplate, parcel);
        }

    }

    private class SetRemoteInputsAction
    extends Action {
        final Parcelable[] remoteInputs;

        public SetRemoteInputsAction(int n, RemoteInput[] arrremoteInput) {
            this.viewId = n;
            this.remoteInputs = arrremoteInput;
        }

        public SetRemoteInputsAction(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.remoteInputs = parcel.createTypedArray(RemoteInput.CREATOR);
        }

        @Override
        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            if ((view = view.findViewById(this.viewId)) == null) {
                return;
            }
            view.setTagInternal(16909278, this.remoteInputs);
        }

        @Override
        public int getActionTag() {
            return 18;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeTypedArray(this.remoteInputs, n);
        }
    }

    private class SetRemoteViewsAdapterIntent
    extends Action {
        Intent intent;
        boolean isAsync = false;

        public SetRemoteViewsAdapterIntent(int n, Intent intent) {
            this.viewId = n;
            this.intent = intent;
        }

        public SetRemoteViewsAdapterIntent(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.intent = parcel.readTypedObject(Intent.CREATOR);
        }

        @Override
        public void apply(View object, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            block4 : {
                block3 : {
                    if ((object = ((View)object).findViewById(this.viewId)) == null) {
                        return;
                    }
                    if (!(viewGroup instanceof AppWidgetHostView)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("SetRemoteViewsAdapterIntent action can only be used for AppWidgets (root id: ");
                        ((StringBuilder)object).append(this.viewId);
                        ((StringBuilder)object).append(")");
                        Log.e(RemoteViews.LOG_TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    if (!(object instanceof AbsListView) && !(object instanceof AdapterViewAnimator)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Cannot setRemoteViewsAdapter on a view which is not an AbsListView or AdapterViewAnimator (id: ");
                        ((StringBuilder)object).append(this.viewId);
                        ((StringBuilder)object).append(")");
                        Log.e(RemoteViews.LOG_TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    viewGroup = (AppWidgetHostView)viewGroup;
                    this.intent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_APPWIDGET_ID, ((AppWidgetHostView)viewGroup).getAppWidgetId()).putExtra(RemoteViews.EXTRA_REMOTEADAPTER_ON_LIGHT_BACKGROUND, RemoteViews.this.hasFlags(4));
                    if (!(object instanceof AbsListView)) break block3;
                    object = (AbsListView)object;
                    ((AbsListView)object).setRemoteViewsAdapter(this.intent, this.isAsync);
                    ((AbsListView)object).setRemoteViewsOnClickHandler(onClickHandler);
                    break block4;
                }
                if (!(object instanceof AdapterViewAnimator)) break block4;
                object = (AdapterViewAnimator)object;
                ((AdapterViewAnimator)object).setRemoteViewsAdapter(this.intent, this.isAsync);
                ((AdapterViewAnimator)object).setRemoteViewsOnClickHandler(onClickHandler);
            }
        }

        @Override
        public int getActionTag() {
            return 10;
        }

        @Override
        public Action initActionAsync(ViewTree object, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            object = new SetRemoteViewsAdapterIntent(this.viewId, this.intent);
            ((SetRemoteViewsAdapterIntent)object).isAsync = true;
            return object;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeTypedObject(this.intent, n);
        }
    }

    private class SetRemoteViewsAdapterList
    extends Action {
        ArrayList<RemoteViews> list;
        int viewTypeCount;

        public SetRemoteViewsAdapterList(int n, ArrayList<RemoteViews> arrayList, int n2) {
            this.viewId = n;
            this.list = arrayList;
            this.viewTypeCount = n2;
        }

        public SetRemoteViewsAdapterList(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.viewTypeCount = parcel.readInt();
            this.list = parcel.createTypedArrayList(CREATOR);
        }

        @Override
        public void apply(View object, ViewGroup object2, OnClickHandler onClickHandler) {
            block8 : {
                block7 : {
                    if ((object = ((View)object).findViewById(this.viewId)) == null) {
                        return;
                    }
                    if (!(object2 instanceof AppWidgetHostView)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("SetRemoteViewsAdapterIntent action can only be used for AppWidgets (root id: ");
                        ((StringBuilder)object).append(this.viewId);
                        ((StringBuilder)object).append(")");
                        Log.e(RemoteViews.LOG_TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    if (!(object instanceof AbsListView) && !(object instanceof AdapterViewAnimator)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Cannot setRemoteViewsAdapter on a view which is not an AbsListView or AdapterViewAnimator (id: ");
                        ((StringBuilder)object).append(this.viewId);
                        ((StringBuilder)object).append(")");
                        Log.e(RemoteViews.LOG_TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    if (!(object instanceof AbsListView)) break block7;
                    object2 = ((AdapterView)(object = (AbsListView)object)).getAdapter();
                    if (object2 instanceof RemoteViewsListAdapter && this.viewTypeCount <= object2.getViewTypeCount()) {
                        ((RemoteViewsListAdapter)object2).setViewsList(this.list);
                    } else {
                        ((AbsListView)object).setAdapter(new RemoteViewsListAdapter(((View)object).getContext(), this.list, this.viewTypeCount));
                    }
                    break block8;
                }
                if (!(object instanceof AdapterViewAnimator)) break block8;
                object2 = ((AdapterViewAnimator)(object = (AdapterViewAnimator)object)).getAdapter();
                if (object2 instanceof RemoteViewsListAdapter && this.viewTypeCount <= object2.getViewTypeCount()) {
                    ((RemoteViewsListAdapter)object2).setViewsList(this.list);
                } else {
                    ((AdapterViewAnimator)object).setAdapter(new RemoteViewsListAdapter(((View)object).getContext(), this.list, this.viewTypeCount));
                }
            }
        }

        @Override
        public int getActionTag() {
            return 15;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeInt(this.viewTypeCount);
            parcel.writeTypedList(this.list, n);
        }
    }

    private class SetRippleDrawableColor
    extends Action {
        ColorStateList mColorStateList;

        SetRippleDrawableColor(int n, ColorStateList colorStateList) {
            this.viewId = n;
            this.mColorStateList = colorStateList;
        }

        SetRippleDrawableColor(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.mColorStateList = (ColorStateList)parcel.readParcelable(null);
        }

        @Override
        public void apply(View object, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            if ((object = ((View)object).findViewById(this.viewId)) == null) {
                return;
            }
            if ((object = ((View)object).getBackground()) instanceof RippleDrawable) {
                ((RippleDrawable)((Drawable)object).mutate()).setColor(this.mColorStateList);
            }
        }

        @Override
        public int getActionTag() {
            return 21;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeParcelable(this.mColorStateList, 0);
        }
    }

    private class TextViewDrawableAction
    extends Action {
        int d1;
        int d2;
        int d3;
        int d4;
        boolean drawablesLoaded;
        Icon i1;
        Icon i2;
        Icon i3;
        Icon i4;
        Drawable id1;
        Drawable id2;
        Drawable id3;
        Drawable id4;
        boolean isRelative;
        boolean useIcons;

        public TextViewDrawableAction(int n, boolean bl, int n2, int n3, int n4, int n5) {
            this.isRelative = false;
            this.useIcons = false;
            this.drawablesLoaded = false;
            this.viewId = n;
            this.isRelative = bl;
            this.useIcons = false;
            this.d1 = n2;
            this.d2 = n3;
            this.d3 = n4;
            this.d4 = n5;
        }

        public TextViewDrawableAction(int n, boolean bl, Icon icon, Icon icon2, Icon icon3, Icon icon4) {
            this.isRelative = false;
            this.useIcons = false;
            this.drawablesLoaded = false;
            this.viewId = n;
            this.isRelative = bl;
            this.useIcons = true;
            this.i1 = icon;
            this.i2 = icon2;
            this.i3 = icon3;
            this.i4 = icon4;
        }

        public TextViewDrawableAction(Parcel parcel) {
            boolean bl = false;
            this.isRelative = false;
            this.useIcons = false;
            this.drawablesLoaded = false;
            this.viewId = parcel.readInt();
            boolean bl2 = parcel.readInt() != 0;
            this.isRelative = bl2;
            bl2 = bl;
            if (parcel.readInt() != 0) {
                bl2 = true;
            }
            this.useIcons = bl2;
            if (this.useIcons) {
                this.i1 = parcel.readTypedObject(Icon.CREATOR);
                this.i2 = parcel.readTypedObject(Icon.CREATOR);
                this.i3 = parcel.readTypedObject(Icon.CREATOR);
                this.i4 = parcel.readTypedObject(Icon.CREATOR);
            } else {
                this.d1 = parcel.readInt();
                this.d2 = parcel.readInt();
                this.d3 = parcel.readInt();
                this.d4 = parcel.readInt();
            }
        }

        @Override
        public void apply(View object, ViewGroup object2, OnClickHandler object3) {
            TextView textView = (TextView)((View)object).findViewById(this.viewId);
            if (textView == null) {
                return;
            }
            if (this.drawablesLoaded) {
                if (this.isRelative) {
                    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(this.id1, this.id2, this.id3, this.id4);
                } else {
                    textView.setCompoundDrawablesWithIntrinsicBounds(this.id1, this.id2, this.id3, this.id4);
                }
            } else if (this.useIcons) {
                Context context = textView.getContext();
                object = this.i1;
                Drawable drawable2 = null;
                object = object == null ? null : ((Icon)object).loadDrawable(context);
                object2 = this.i2;
                object2 = object2 == null ? null : ((Icon)object2).loadDrawable(context);
                object3 = this.i3;
                object3 = object3 == null ? null : ((Icon)object3).loadDrawable(context);
                Icon icon = this.i4;
                if (icon != null) {
                    drawable2 = icon.loadDrawable(context);
                }
                if (this.isRelative) {
                    textView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable)object, (Drawable)object2, (Drawable)object3, drawable2);
                } else {
                    textView.setCompoundDrawablesWithIntrinsicBounds((Drawable)object, (Drawable)object2, (Drawable)object3, drawable2);
                }
            } else if (this.isRelative) {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(this.d1, this.d2, this.d3, this.d4);
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(this.d1, this.d2, this.d3, this.d4);
            }
        }

        @Override
        public int getActionTag() {
            return 11;
        }

        @Override
        public Action initActionAsync(ViewTree object, ViewGroup object2, OnClickHandler onClickHandler) {
            object2 = (TextView)((ViewTree)object).findViewById(this.viewId);
            if (object2 == null) {
                return ACTION_NOOP;
            }
            object = this.useIcons ? new TextViewDrawableAction(this.viewId, this.isRelative, this.i1, this.i2, this.i3, this.i4) : new TextViewDrawableAction(this.viewId, this.isRelative, this.d1, this.d2, this.d3, this.d4);
            ((TextViewDrawableAction)object).drawablesLoaded = true;
            Context context = ((View)object2).getContext();
            boolean bl = this.useIcons;
            Object var6_6 = null;
            onClickHandler = null;
            if (bl) {
                object2 = this.i1;
                object2 = object2 == null ? null : ((Icon)object2).loadDrawable(context);
                ((TextViewDrawableAction)object).id1 = object2;
                object2 = this.i2;
                object2 = object2 == null ? null : ((Icon)object2).loadDrawable(context);
                ((TextViewDrawableAction)object).id2 = object2;
                object2 = this.i3;
                object2 = object2 == null ? null : ((Icon)object2).loadDrawable(context);
                ((TextViewDrawableAction)object).id3 = object2;
                object2 = this.i4;
                object2 = object2 == null ? onClickHandler : ((Icon)object2).loadDrawable(context);
                ((TextViewDrawableAction)object).id4 = object2;
            } else {
                int n = this.d1;
                object2 = n == 0 ? null : context.getDrawable(n);
                ((TextViewDrawableAction)object).id1 = object2;
                n = this.d2;
                object2 = n == 0 ? null : context.getDrawable(n);
                ((TextViewDrawableAction)object).id2 = object2;
                n = this.d3;
                object2 = n == 0 ? null : context.getDrawable(n);
                ((TextViewDrawableAction)object).id3 = object2;
                n = this.d4;
                object2 = n == 0 ? var6_6 : context.getDrawable(n);
                ((TextViewDrawableAction)object).id4 = object2;
            }
            return object;
        }

        @Override
        public boolean prefersAsyncApply() {
            return this.useIcons;
        }

        @Override
        public void visitUris(Consumer<Uri> consumer) {
            if (this.useIcons) {
                RemoteViews.visitIconUri(this.i1, consumer);
                RemoteViews.visitIconUri(this.i2, consumer);
                RemoteViews.visitIconUri(this.i3, consumer);
                RemoteViews.visitIconUri(this.i4, consumer);
            }
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeInt((int)this.isRelative);
            parcel.writeInt((int)this.useIcons);
            if (this.useIcons) {
                parcel.writeTypedObject(this.i1, 0);
                parcel.writeTypedObject(this.i2, 0);
                parcel.writeTypedObject(this.i3, 0);
                parcel.writeTypedObject(this.i4, 0);
            } else {
                parcel.writeInt(this.d1);
                parcel.writeInt(this.d2);
                parcel.writeInt(this.d3);
                parcel.writeInt(this.d4);
            }
        }
    }

    private class TextViewSizeAction
    extends Action {
        float size;
        int units;

        public TextViewSizeAction(int n, int n2, float f) {
            this.viewId = n;
            this.units = n2;
            this.size = f;
        }

        public TextViewSizeAction(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.units = parcel.readInt();
            this.size = parcel.readFloat();
        }

        @Override
        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            if ((view = (TextView)view.findViewById(this.viewId)) == null) {
                return;
            }
            ((TextView)view).setTextSize(this.units, this.size);
        }

        @Override
        public int getActionTag() {
            return 13;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeInt(this.units);
            parcel.writeFloat(this.size);
        }
    }

    private final class ViewContentNavigation
    extends Action {
        final boolean mNext;

        ViewContentNavigation(int n, boolean bl) {
            this.viewId = n;
            this.mNext = bl;
        }

        ViewContentNavigation(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.mNext = parcel.readBoolean();
        }

        @Override
        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            throw new RuntimeException("d2j fail translate: java.lang.ClassCastException: com.googlecode.dex2jar.ir.expr.InvokePolymorphicExpr cannot be cast to com.googlecode.dex2jar.ir.expr.InvokeExpr\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.findInvokeExpr(NewTransformer.java:361)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.replaceAST(NewTransformer.java:98)\n\tat com.googlecode.dex2jar.ir.ts.NewTransformer.transform(NewTransformer.java:68)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:150)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
        }

        @Override
        public int getActionTag() {
            return 5;
        }

        @Override
        public int mergeBehavior() {
            return 2;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeBoolean(this.mNext);
        }
    }

    private class ViewGroupActionAdd
    extends Action {
        private int mIndex;
        @UnsupportedAppUsage
        private RemoteViews mNestedViews;

        ViewGroupActionAdd(int n, RemoteViews remoteViews2) {
            this(n, remoteViews2, -1);
        }

        ViewGroupActionAdd(int n, RemoteViews remoteViews2, int n2) {
            this.viewId = n;
            this.mNestedViews = remoteViews2;
            this.mIndex = n2;
            if (remoteViews2 != null) {
                RemoteViews.this.configureRemoteViewsAsChild(remoteViews2);
            }
        }

        ViewGroupActionAdd(Parcel parcel, BitmapCache bitmapCache, ApplicationInfo applicationInfo, int n, Map<Class, Object> map) {
            this.viewId = parcel.readInt();
            this.mIndex = parcel.readInt();
            this.mNestedViews = new RemoteViews(parcel, bitmapCache, applicationInfo, n, map);
            this.mNestedViews.addFlags(RemoteViews.this.mApplyFlags);
        }

        @Override
        public void apply(View view, ViewGroup object, OnClickHandler onClickHandler) {
            object = view.getContext();
            if ((view = (ViewGroup)view.findViewById(this.viewId)) == null) {
                return;
            }
            ((ViewGroup)view).addView(this.mNestedViews.apply((Context)object, (ViewGroup)view, onClickHandler), this.mIndex);
        }

        @Override
        public int getActionTag() {
            return 4;
        }

        @Override
        public boolean hasSameAppInfo(ApplicationInfo applicationInfo) {
            return this.mNestedViews.hasSameAppInfo(applicationInfo);
        }

        @Override
        public Action initActionAsync(ViewTree object, ViewGroup viewGroup, OnClickHandler object2) {
            ((ViewTree)object).createTree();
            ViewTree viewTree = ((ViewTree)object).findViewTreeById(this.viewId);
            if (viewTree != null && viewTree.mRoot instanceof ViewGroup) {
                viewGroup = (ViewGroup)viewTree.mRoot;
                object = ((ViewTree)object).mRoot.getContext();
                object = this.mNestedViews.getAsyncApplyTask((Context)object, viewGroup, null, (OnClickHandler)object2);
                object2 = ((AsyncApplyTask)object).doInBackground(new Void[0]);
                if (object2 != null) {
                    viewTree.addChild((ViewTree)object2, this.mIndex);
                    return new RuntimeAction((AsyncApplyTask)object, (ViewTree)object2, viewGroup){
                        final /* synthetic */ ViewGroup val$targetVg;
                        final /* synthetic */ AsyncApplyTask val$task;
                        final /* synthetic */ ViewTree val$tree;
                        {
                            this.val$task = asyncApplyTask;
                            this.val$tree = viewTree;
                            this.val$targetVg = viewGroup;
                        }

                        @Override
                        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) throws ActionException {
                            this.val$task.onPostExecute(this.val$tree);
                            this.val$targetVg.addView(this.val$task.mResult, ViewGroupActionAdd.this.mIndex);
                        }
                    };
                }
                throw new ActionException(((AsyncApplyTask)object).mError);
            }
            return ACTION_NOOP;
        }

        @Override
        public int mergeBehavior() {
            return 1;
        }

        @Override
        public boolean prefersAsyncApply() {
            return this.mNestedViews.prefersAsyncApply();
        }

        @Override
        public void setBitmapCache(BitmapCache bitmapCache) {
            this.mNestedViews.setBitmapCache(bitmapCache);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeInt(this.mIndex);
            this.mNestedViews.writeToParcel(parcel, n);
        }

    }

    private class ViewGroupActionRemove
    extends Action {
        private static final int REMOVE_ALL_VIEWS_ID = -2;
        private int mViewIdToKeep;

        ViewGroupActionRemove(int n) {
            this(n, -2);
        }

        ViewGroupActionRemove(int n, int n2) {
            this.viewId = n;
            this.mViewIdToKeep = n2;
        }

        ViewGroupActionRemove(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.mViewIdToKeep = parcel.readInt();
        }

        private void removeAllViewsExceptIdToKeep(ViewGroup viewGroup) {
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                if (viewGroup.getChildAt(i).getId() == this.mViewIdToKeep) continue;
                viewGroup.removeViewAt(i);
            }
        }

        @Override
        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            if ((view = (ViewGroup)view.findViewById(this.viewId)) == null) {
                return;
            }
            if (this.mViewIdToKeep == -2) {
                ((ViewGroup)view).removeAllViews();
                return;
            }
            this.removeAllViewsExceptIdToKeep((ViewGroup)view);
        }

        @Override
        public int getActionTag() {
            return 7;
        }

        @Override
        public Action initActionAsync(ViewTree object, ViewGroup object2, OnClickHandler onClickHandler) {
            ((ViewTree)object).createTree();
            object2 = ((ViewTree)object).findViewTreeById(this.viewId);
            if (object2 != null && ((ViewTree)object2).mRoot instanceof ViewGroup) {
                object = (ViewGroup)((ViewTree)object2).mRoot;
                ((ViewTree)object2).mChildren = null;
                return new RuntimeAction((ViewGroup)object){
                    final /* synthetic */ ViewGroup val$targetVg;
                    {
                        this.val$targetVg = viewGroup;
                    }

                    @Override
                    public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) throws ActionException {
                        if (ViewGroupActionRemove.this.mViewIdToKeep == -2) {
                            this.val$targetVg.removeAllViews();
                            return;
                        }
                        ViewGroupActionRemove.this.removeAllViewsExceptIdToKeep(this.val$targetVg);
                    }
                };
            }
            return ACTION_NOOP;
        }

        @Override
        public int mergeBehavior() {
            return 1;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeInt(this.mViewIdToKeep);
        }

    }

    private class ViewPaddingAction
    extends Action {
        int bottom;
        int left;
        int right;
        int top;

        public ViewPaddingAction(int n, int n2, int n3, int n4, int n5) {
            this.viewId = n;
            this.left = n2;
            this.top = n3;
            this.right = n4;
            this.bottom = n5;
        }

        public ViewPaddingAction(Parcel parcel) {
            this.viewId = parcel.readInt();
            this.left = parcel.readInt();
            this.top = parcel.readInt();
            this.right = parcel.readInt();
            this.bottom = parcel.readInt();
        }

        @Override
        public void apply(View view, ViewGroup viewGroup, OnClickHandler onClickHandler) {
            if ((view = view.findViewById(this.viewId)) == null) {
                return;
            }
            view.setPadding(this.left, this.top, this.right, this.bottom);
        }

        @Override
        public int getActionTag() {
            return 14;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.viewId);
            parcel.writeInt(this.left);
            parcel.writeInt(this.top);
            parcel.writeInt(this.right);
            parcel.writeInt(this.bottom);
        }
    }

    private static class ViewTree {
        private static final int INSERT_AT_END_INDEX = -1;
        private ArrayList<ViewTree> mChildren;
        private View mRoot;

        private ViewTree(View view) {
            this.mRoot = view;
        }

        private void addViewChild(View view) {
            ViewTree viewTree;
            if (view.isRootNamespace()) {
                return;
            }
            if (view.getId() != 0) {
                viewTree = new ViewTree(view);
                this.mChildren.add(viewTree);
            } else {
                viewTree = this;
            }
            if (view instanceof ViewGroup && viewTree.mChildren == null) {
                viewTree.mChildren = new ArrayList();
                view = (ViewGroup)view;
                int n = ((ViewGroup)view).getChildCount();
                for (int i = 0; i < n; ++i) {
                    viewTree.addViewChild(((ViewGroup)view).getChildAt(i));
                }
            }
        }

        public void addChild(ViewTree viewTree) {
            this.addChild(viewTree, -1);
        }

        public void addChild(ViewTree viewTree, int n) {
            if (this.mChildren == null) {
                this.mChildren = new ArrayList();
            }
            viewTree.createTree();
            if (n == -1) {
                this.mChildren.add(viewTree);
                return;
            }
            this.mChildren.add(n, viewTree);
        }

        public void createTree() {
            if (this.mChildren != null) {
                return;
            }
            this.mChildren = new ArrayList();
            View view = this.mRoot;
            if (view instanceof ViewGroup) {
                view = (ViewGroup)view;
                int n = ((ViewGroup)view).getChildCount();
                for (int i = 0; i < n; ++i) {
                    this.addViewChild(((ViewGroup)view).getChildAt(i));
                }
            }
        }

        public <T extends View> T findViewById(int n) {
            if (this.mChildren == null) {
                return this.mRoot.findViewById(n);
            }
            Object object = this.findViewTreeById(n);
            object = object == null ? null : ((ViewTree)object).mRoot;
            return (T)object;
        }

        public ViewTree findViewTreeById(int n) {
            if (this.mRoot.getId() == n) {
                return this;
            }
            Object object = this.mChildren;
            if (object == null) {
                return null;
            }
            Iterator<ViewTree> iterator = ((ArrayList)object).iterator();
            while (iterator.hasNext()) {
                object = iterator.next().findViewTreeById(n);
                if (object == null) continue;
                return object;
            }
            return null;
        }

        public void replaceView(View view) {
            this.mRoot = view;
            this.mChildren = null;
            this.createTree();
        }
    }

}

