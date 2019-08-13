/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputQueue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.policy.DecorContext;
import com.android.internal.policy.DecorView;
import com.android.internal.view.menu.ContextMenuBuilder;
import com.android.internal.view.menu.IconMenuPresenter;
import com.android.internal.view.menu.ListMenuPresenter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuDialogHelper;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.widget.DecorContentParent;
import com.android.internal.widget.SwipeDismissLayout;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PhoneWindow
extends Window
implements MenuBuilder.Callback {
    private static final String ACTION_BAR_TAG = "android:ActionBar";
    private static final int CUSTOM_TITLE_COMPATIBLE_FEATURES = 13505;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_BACKGROUND_FADE_DURATION_MS = 300;
    static final int FLAG_RESOURCE_SET_ICON = 1;
    static final int FLAG_RESOURCE_SET_ICON_FALLBACK = 4;
    static final int FLAG_RESOURCE_SET_LOGO = 2;
    private static final String FOCUSED_ID_TAG = "android:focusedViewId";
    private static final String PANELS_TAG = "android:Panels";
    private static final String TAG = "PhoneWindow";
    private static final Transition USE_DEFAULT_TRANSITION = new TransitionSet();
    private static final String VIEWS_TAG = "android:views";
    static final RotationWatcher sRotationWatcher = new RotationWatcher();
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    private ViewRootImpl.ActivityConfigCallback mActivityConfigCallback;
    private Boolean mAllowEnterTransitionOverlap;
    private Boolean mAllowReturnTransitionOverlap;
    private boolean mAlwaysReadCloseOnTouchAttr = false;
    private AudioManager mAudioManager;
    Drawable mBackgroundDrawable = null;
    private long mBackgroundFadeDurationMillis;
    Drawable mBackgroundFallbackDrawable = null;
    private ProgressBar mCircularProgressBar;
    private boolean mClipToOutline;
    private boolean mClosingActionMenu;
    ViewGroup mContentParent;
    private boolean mContentParentExplicitlySet = false;
    private Scene mContentScene;
    ContextMenuBuilder mContextMenu;
    final PhoneWindowMenuCallback mContextMenuCallback = new PhoneWindowMenuCallback(this);
    MenuHelper mContextMenuHelper;
    private DecorView mDecor;
    private int mDecorCaptionShade;
    DecorContentParent mDecorContentParent;
    private DrawableFeatureState[] mDrawables;
    private float mElevation;
    boolean mEnsureNavigationBarContrastWhenTransparent;
    boolean mEnsureStatusBarContrastWhenTransparent;
    private Transition mEnterTransition = null;
    private Transition mExitTransition;
    TypedValue mFixedHeightMajor;
    TypedValue mFixedHeightMinor;
    TypedValue mFixedWidthMajor;
    TypedValue mFixedWidthMinor;
    private boolean mForceDecorInstall = false;
    private boolean mForcedNavigationBarColor = false;
    private boolean mForcedStatusBarColor = false;
    private int mFrameResource = 0;
    private ProgressBar mHorizontalProgressBar;
    int mIconRes;
    private int mInvalidatePanelMenuFeatures;
    private boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable = new Runnable(){

        @Override
        public void run() {
            for (int i = 0; i <= 13; ++i) {
                if ((PhoneWindow.this.mInvalidatePanelMenuFeatures & 1 << i) == 0) continue;
                PhoneWindow.this.doInvalidatePanelMenu(i);
            }
            PhoneWindow.this.mInvalidatePanelMenuPosted = false;
            PhoneWindow.this.mInvalidatePanelMenuFeatures = 0;
        }
    };
    boolean mIsFloating;
    private boolean mIsStartingWindow;
    private boolean mIsTranslucent;
    private KeyguardManager mKeyguardManager;
    private LayoutInflater mLayoutInflater;
    private ImageView mLeftIconView;
    private boolean mLoadElevation = true;
    int mLogoRes;
    private MediaController mMediaController;
    private MediaSessionManager mMediaSessionManager;
    final TypedValue mMinWidthMajor = new TypedValue();
    final TypedValue mMinWidthMinor = new TypedValue();
    int mNavigationBarColor = 0;
    int mNavigationBarDividerColor = 0;
    int mPanelChordingKey;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    PanelFeatureState mPreparedPanel;
    private Transition mReenterTransition;
    int mResourcesSetFlags;
    private Transition mReturnTransition;
    private ImageView mRightIconView;
    private Transition mSharedElementEnterTransition;
    private Transition mSharedElementExitTransition;
    private Transition mSharedElementReenterTransition;
    private Transition mSharedElementReturnTransition;
    private Boolean mSharedElementsUseOverlay;
    int mStatusBarColor = 0;
    private boolean mSupportsPictureInPicture;
    InputQueue.Callback mTakeInputQueueCallback;
    SurfaceHolder.Callback2 mTakeSurfaceCallback;
    private int mTextColor = 0;
    private int mTheme;
    @UnsupportedAppUsage
    private CharSequence mTitle = null;
    private int mTitleColor = 0;
    private TextView mTitleView;
    private TransitionManager mTransitionManager;
    private int mUiOptions = 0;
    private boolean mUseDecorContext;
    private int mVolumeControlStreamType = Integer.MIN_VALUE;

    @UnsupportedAppUsage
    public PhoneWindow(Context context) {
        super(context);
        Transition transition2;
        this.mReturnTransition = transition2 = USE_DEFAULT_TRANSITION;
        this.mExitTransition = null;
        this.mReenterTransition = transition2;
        this.mSharedElementEnterTransition = null;
        this.mSharedElementReturnTransition = transition2;
        this.mSharedElementExitTransition = null;
        this.mSharedElementReenterTransition = transition2;
        this.mBackgroundFadeDurationMillis = -1L;
        this.mTheme = -1;
        this.mDecorCaptionShade = 0;
        this.mUseDecorContext = false;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public PhoneWindow(Context context, Window window, ViewRootImpl.ActivityConfigCallback activityConfigCallback) {
        this(context);
        boolean bl = true;
        this.mUseDecorContext = true;
        if (window != null) {
            this.mDecor = (DecorView)window.getDecorView();
            this.mElevation = window.getElevation();
            this.mLoadElevation = false;
            this.mForceDecorInstall = true;
            this.getAttributes().token = window.getAttributes().token;
        }
        boolean bl2 = Settings.Global.getInt(context.getContentResolver(), "force_resizable_activities", 0) != 0;
        boolean bl3 = bl;
        if (!bl2) {
            bl3 = context.getPackageManager().hasSystemFeature("android.software.picture_in_picture") ? bl : false;
        }
        this.mSupportsPictureInPicture = bl3;
        this.mActivityConfigCallback = activityConfigCallback;
    }

    private void callOnPanelClosed(int n, PanelFeatureState panelFeatureState, Menu arrpanelFeatureState) {
        Window.Callback callback = this.getCallback();
        if (callback == null) {
            return;
        }
        PanelFeatureState panelFeatureState2 = panelFeatureState;
        Object object = arrpanelFeatureState;
        if (arrpanelFeatureState == null) {
            PanelFeatureState panelFeatureState3 = panelFeatureState;
            if (panelFeatureState == null) {
                panelFeatureState3 = panelFeatureState;
                if (n >= 0) {
                    object = this.mPanels;
                    panelFeatureState3 = panelFeatureState;
                    if (n < ((PanelFeatureState[])object).length) {
                        panelFeatureState3 = object[n];
                    }
                }
            }
            panelFeatureState2 = panelFeatureState3;
            object = arrpanelFeatureState;
            if (panelFeatureState3 != null) {
                object = panelFeatureState3.menu;
                panelFeatureState2 = panelFeatureState3;
            }
        }
        if (panelFeatureState2 != null && !panelFeatureState2.isOpen) {
            return;
        }
        if (!this.isDestroyed()) {
            callback.onPanelClosed(n, (Menu)object);
        }
    }

    private static void clearMenuViews(PanelFeatureState panelFeatureState) {
        panelFeatureState.createdPanelView = null;
        panelFeatureState.refreshDecorView = true;
        panelFeatureState.clearMenuPresenters();
    }

    private void closeContextMenu() {
        synchronized (this) {
            if (this.mContextMenu != null) {
                this.mContextMenu.close();
                this.dismissContextMenu();
            }
            return;
        }
    }

    private void dismissContextMenu() {
        synchronized (this) {
            this.mContextMenu = null;
            if (this.mContextMenuHelper != null) {
                this.mContextMenuHelper.dismiss();
                this.mContextMenuHelper = null;
            }
            return;
        }
    }

    private ProgressBar getCircularProgressBar(boolean bl) {
        ProgressBar progressBar = this.mCircularProgressBar;
        if (progressBar != null) {
            return progressBar;
        }
        if (this.mContentParent == null && bl) {
            this.installDecor();
        }
        if ((progressBar = (this.mCircularProgressBar = (ProgressBar)this.findViewById(16909260))) != null) {
            progressBar.setVisibility(4);
        }
        return this.mCircularProgressBar;
    }

    private DrawableFeatureState getDrawableState(int n, boolean bl) {
        DrawableFeatureState[] arrdrawableFeatureState;
        Object object;
        DrawableFeatureState[] arrdrawableFeatureState2;
        block8 : {
            block7 : {
                if ((this.getFeatures() & 1 << n) == 0) {
                    if (!bl) {
                        return null;
                    }
                    throw new RuntimeException("The feature has not been requested");
                }
                arrdrawableFeatureState = arrdrawableFeatureState2 = this.mDrawables;
                if (arrdrawableFeatureState2 == null) break block7;
                arrdrawableFeatureState2 = arrdrawableFeatureState;
                if (arrdrawableFeatureState.length > n) break block8;
            }
            object = new DrawableFeatureState[n + 1];
            if (arrdrawableFeatureState != null) {
                System.arraycopy(arrdrawableFeatureState, 0, object, 0, arrdrawableFeatureState.length);
            }
            arrdrawableFeatureState2 = object;
            this.mDrawables = object;
        }
        object = arrdrawableFeatureState2[n];
        arrdrawableFeatureState = object;
        if (object == null) {
            object = new DrawableFeatureState(n);
            arrdrawableFeatureState = object;
            arrdrawableFeatureState2[n] = object;
        }
        return arrdrawableFeatureState;
    }

    private ProgressBar getHorizontalProgressBar(boolean bl) {
        ProgressBar progressBar = this.mHorizontalProgressBar;
        if (progressBar != null) {
            return progressBar;
        }
        if (this.mContentParent == null && bl) {
            this.installDecor();
        }
        if ((progressBar = (this.mHorizontalProgressBar = (ProgressBar)this.findViewById(16909261))) != null) {
            progressBar.setVisibility(4);
        }
        return this.mHorizontalProgressBar;
    }

    private KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager)this.getContext().getSystemService("keyguard");
        }
        return this.mKeyguardManager;
    }

    private ImageView getLeftIconView() {
        ImageView imageView = this.mLeftIconView;
        if (imageView != null) {
            return imageView;
        }
        if (this.mContentParent == null) {
            this.installDecor();
        }
        this.mLeftIconView = imageView = (ImageView)this.findViewById(16909065);
        return imageView;
    }

    private MediaSessionManager getMediaSessionManager() {
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager = (MediaSessionManager)this.getContext().getSystemService("media_session");
        }
        return this.mMediaSessionManager;
    }

    private int getOptionsPanelGravity() {
        try {
            int n = WindowManagerHolder.sWindowManager.getPreferredOptionsPanelGravity(this.getContext().getDisplayId());
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Couldn't getOptionsPanelGravity; using default", remoteException);
            return 81;
        }
    }

    private PanelFeatureState getPanelState(int n, boolean bl, PanelFeatureState panelFeatureState) {
        PanelFeatureState[] arrpanelFeatureState;
        Object object;
        Object object2;
        block9 : {
            block8 : {
                if ((this.getFeatures() & 1 << n) == 0) {
                    if (!bl) {
                        return null;
                    }
                    throw new RuntimeException("The feature has not been requested");
                }
                object2 = arrpanelFeatureState = this.mPanels;
                if (arrpanelFeatureState == null) break block8;
                arrpanelFeatureState = object2;
                if (((PanelFeatureState[])object2).length > n) break block9;
            }
            object = new PanelFeatureState[n + 1];
            if (object2 != null) {
                System.arraycopy(object2, 0, object, 0, ((PanelFeatureState[])object2).length);
            }
            arrpanelFeatureState = object;
            this.mPanels = object;
        }
        object = arrpanelFeatureState[n];
        object2 = object;
        if (object == null) {
            if (panelFeatureState == null) {
                panelFeatureState = new PanelFeatureState(n);
            }
            object2 = panelFeatureState;
            arrpanelFeatureState[n] = panelFeatureState;
        }
        return object2;
    }

    private ImageView getRightIconView() {
        ImageView imageView = this.mRightIconView;
        if (imageView != null) {
            return imageView;
        }
        if (this.mContentParent == null) {
            this.installDecor();
        }
        this.mRightIconView = imageView = (ImageView)this.findViewById(16909294);
        return imageView;
    }

    private Transition getTransition(Transition transition2, Transition transition3, int n) {
        if (transition2 != transition3) {
            return transition2;
        }
        n = this.getWindowStyle().getResourceId(n, -1);
        transition2 = transition3;
        if (n != -1) {
            transition2 = transition3;
            if (n != 17760256) {
                transition2 = transition3 = TransitionInflater.from(this.getContext()).inflateTransition(n);
                if (transition3 instanceof TransitionSet) {
                    transition2 = transition3;
                    if (((TransitionSet)transition3).getTransitionCount() == 0) {
                        transition2 = null;
                    }
                }
            }
        }
        return transition2;
    }

    private ViewRootImpl getViewRootImpl() {
        ViewParent viewParent = this.mDecor;
        if (viewParent != null && (viewParent = viewParent.getViewRootImpl()) != null) {
            return viewParent;
        }
        throw new IllegalStateException("view not added");
    }

    private void hideProgressBars(ProgressBar progressBar, ProgressBar progressBar2) {
        int n = this.getLocalFeatures();
        Animation animation = AnimationUtils.loadAnimation(this.getContext(), 17432577);
        animation.setDuration(1000L);
        if ((n & 32) != 0 && progressBar2 != null && progressBar2.getVisibility() == 0) {
            progressBar2.startAnimation(animation);
            progressBar2.setVisibility(4);
        }
        if ((n & 4) != 0 && progressBar != null && progressBar.getVisibility() == 0) {
            progressBar.startAnimation(animation);
            progressBar.setVisibility(4);
        }
    }

    private void installDecor() {
        this.mForceDecorInstall = false;
        Object object = this.mDecor;
        if (object == null) {
            this.mDecor = this.generateDecor(-1);
            this.mDecor.setDescendantFocusability(262144);
            this.mDecor.setIsRootNamespace(true);
            if (!this.mInvalidatePanelMenuPosted && this.mInvalidatePanelMenuFeatures != 0) {
                this.mDecor.postOnAnimation(this.mInvalidatePanelMenuRunnable);
            }
        } else {
            ((DecorView)object).setWindow(this);
        }
        if (this.mContentParent == null) {
            int n;
            this.mContentParent = this.generateLayout(this.mDecor);
            this.mDecor.makeOptionalFitsSystemWindows();
            object = (DecorContentParent)this.mDecor.findViewById(16908869);
            if (object != null) {
                this.mDecorContentParent = object;
                this.mDecorContentParent.setWindowCallback(this.getCallback());
                if (this.mDecorContentParent.getTitle() == null) {
                    this.mDecorContentParent.setWindowTitle(this.mTitle);
                }
                int n2 = this.getLocalFeatures();
                for (n = 0; n < 13; ++n) {
                    if ((1 << n & n2) == 0) continue;
                    this.mDecorContentParent.initFeature(n);
                }
                this.mDecorContentParent.setUiOptions(this.mUiOptions);
                if ((this.mResourcesSetFlags & 1) == 0 && (this.mIconRes == 0 || this.mDecorContentParent.hasIcon())) {
                    if ((this.mResourcesSetFlags & 1) == 0 && this.mIconRes == 0 && !this.mDecorContentParent.hasIcon()) {
                        this.mDecorContentParent.setIcon(this.getContext().getPackageManager().getDefaultActivityIcon());
                        this.mResourcesSetFlags |= 4;
                    }
                } else {
                    this.mDecorContentParent.setIcon(this.mIconRes);
                }
                if ((this.mResourcesSetFlags & 2) != 0 || this.mLogoRes != 0 && !this.mDecorContentParent.hasLogo()) {
                    this.mDecorContentParent.setLogo(this.mLogoRes);
                }
                object = this.getPanelState(0, false);
                if (!(this.isDestroyed() || object != null && ((PanelFeatureState)object).menu != null || this.mIsStartingWindow)) {
                    this.invalidatePanelMenu(8);
                }
            } else {
                this.mTitleView = (TextView)this.findViewById(16908310);
                if (this.mTitleView != null) {
                    if ((this.getLocalFeatures() & 2) != 0) {
                        object = this.findViewById(16909470);
                        if (object != null) {
                            ((View)object).setVisibility(8);
                        } else {
                            this.mTitleView.setVisibility(8);
                        }
                        this.mContentParent.setForeground(null);
                    } else {
                        this.mTitleView.setText(this.mTitle);
                    }
                }
            }
            if (this.mDecor.getBackground() == null && (object = this.mBackgroundFallbackDrawable) != null) {
                this.mDecor.setBackgroundFallback((Drawable)object);
            }
            if (this.hasFeature(13)) {
                if (this.mTransitionManager == null) {
                    n = this.getWindowStyle().getResourceId(27, 0);
                    this.mTransitionManager = n != 0 ? TransitionInflater.from(this.getContext()).inflateTransitionManager(n, this.mContentParent) : new TransitionManager();
                }
                this.mEnterTransition = this.getTransition(this.mEnterTransition, null, 28);
                this.mReturnTransition = this.getTransition(this.mReturnTransition, USE_DEFAULT_TRANSITION, 40);
                this.mExitTransition = this.getTransition(this.mExitTransition, null, 29);
                this.mReenterTransition = this.getTransition(this.mReenterTransition, USE_DEFAULT_TRANSITION, 41);
                this.mSharedElementEnterTransition = this.getTransition(this.mSharedElementEnterTransition, null, 30);
                this.mSharedElementReturnTransition = this.getTransition(this.mSharedElementReturnTransition, USE_DEFAULT_TRANSITION, 42);
                this.mSharedElementExitTransition = this.getTransition(this.mSharedElementExitTransition, null, 31);
                this.mSharedElementReenterTransition = this.getTransition(this.mSharedElementReenterTransition, USE_DEFAULT_TRANSITION, 43);
                if (this.mAllowEnterTransitionOverlap == null) {
                    this.mAllowEnterTransitionOverlap = this.getWindowStyle().getBoolean(33, true);
                }
                if (this.mAllowReturnTransitionOverlap == null) {
                    this.mAllowReturnTransitionOverlap = this.getWindowStyle().getBoolean(32, true);
                }
                if (this.mBackgroundFadeDurationMillis < 0L) {
                    this.mBackgroundFadeDurationMillis = this.getWindowStyle().getInteger(37, 300);
                }
                if (this.mSharedElementsUseOverlay == null) {
                    this.mSharedElementsUseOverlay = this.getWindowStyle().getBoolean(44, true);
                }
            }
        }
    }

    private boolean isNotInstantAppAndKeyguardRestricted() {
        boolean bl = !this.getContext().getPackageManager().isInstantApp() && this.getKeyguardManager().inKeyguardRestrictedInputMode();
        return bl;
    }

    private boolean isTvUserSetupComplete() {
        ContentResolver contentResolver = this.getContext().getContentResolver();
        boolean bl = false;
        boolean bl2 = Settings.Secure.getInt(contentResolver, "user_setup_complete", 0) != 0;
        if (Settings.Secure.getInt(this.getContext().getContentResolver(), "tv_user_setup_complete", 0) != 0) {
            bl = true;
        }
        return bl2 & bl;
    }

    private boolean launchDefaultSearch(KeyEvent keyEvent) {
        Object object;
        boolean bl;
        if (this.getContext().getPackageManager().hasSystemFeature("android.software.leanback") && !this.isTvUserSetupComplete()) {
            return false;
        }
        Window.Callback callback = this.getCallback();
        if (callback != null && !this.isDestroyed()) {
            this.sendCloseSystemWindows("search");
            int n = keyEvent.getDeviceId();
            object = null;
            if (n != 0) {
                object = new SearchEvent(InputDevice.getDevice(n));
            }
            try {
                bl = callback.onSearchRequested((SearchEvent)object);
            }
            catch (AbstractMethodError abstractMethodError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("WindowCallback ");
                stringBuilder.append(callback.getClass().getName());
                stringBuilder.append(" does not implement method onSearchRequested(SearchEvent); fa");
                Log.e(TAG, stringBuilder.toString(), abstractMethodError);
                bl = callback.onSearchRequested();
            }
        } else {
            bl = false;
        }
        if (!bl && (this.getContext().getResources().getConfiguration().uiMode & 15) == 4) {
            object = new Bundle();
            ((BaseBundle)object).putInt("android.intent.extra.ASSIST_INPUT_DEVICE_ID", keyEvent.getDeviceId());
            return ((SearchManager)this.getContext().getSystemService("search")).launchLegacyAssist(null, this.getContext().getUserId(), (Bundle)object);
        }
        return bl;
    }

    private Drawable loadImageURI(Uri uri) {
        try {
            Drawable drawable2 = Drawable.createFromStream(this.getContext().getContentResolver().openInputStream(uri), null);
            return drawable2;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to open content: ");
            stringBuilder.append(uri);
            Log.w(TAG, stringBuilder.toString());
            return null;
        }
    }

    private void openPanel(PanelFeatureState panelFeatureState, KeyEvent object) {
        block27 : {
            block30 : {
                WindowManager windowManager;
                int n;
                block29 : {
                    int n2;
                    int n3;
                    Object object2;
                    block28 : {
                        if (panelFeatureState.isOpen || this.isDestroyed()) break block27;
                        if (panelFeatureState.featureId == 0) {
                            object2 = this.getContext();
                            n = (object2.getResources().getConfiguration().screenLayout & 15) == 4 ? 1 : 0;
                            n2 = object2.getApplicationInfo().targetSdkVersion >= 11 ? 1 : 0;
                            if (n != 0 && n2 != 0) {
                                return;
                            }
                        }
                        if ((object2 = this.getCallback()) != null && !object2.onMenuOpened(panelFeatureState.featureId, panelFeatureState.menu)) {
                            this.closePanel(panelFeatureState, true);
                            return;
                        }
                        windowManager = this.getWindowManager();
                        if (windowManager == null) {
                            return;
                        }
                        if (!this.preparePanel(panelFeatureState, (KeyEvent)object)) {
                            return;
                        }
                        n2 = -2;
                        if (panelFeatureState.decorView == null || panelFeatureState.refreshDecorView) break block28;
                        if (!panelFeatureState.isInListMode()) {
                            n = -1;
                        } else {
                            n = n2;
                            if (panelFeatureState.createdPanelView != null) {
                                object = panelFeatureState.createdPanelView.getLayoutParams();
                                n = n2;
                                if (object != null) {
                                    n = n2;
                                    if (((ViewGroup.LayoutParams)object).width == -1) {
                                        n = -1;
                                    }
                                }
                            }
                        }
                        break block29;
                    }
                    if (panelFeatureState.decorView == null) {
                        if (!this.initializePanelDecor(panelFeatureState) || panelFeatureState.decorView == null) {
                            return;
                        }
                    } else if (panelFeatureState.refreshDecorView && panelFeatureState.decorView.getChildCount() > 0) {
                        panelFeatureState.decorView.removeAllViews();
                    }
                    if (!this.initializePanelContent(panelFeatureState) || !panelFeatureState.hasPanelItems()) break block30;
                    object = object2 = panelFeatureState.shownPanelView.getLayoutParams();
                    if (object2 == null) {
                        object = new ViewGroup.LayoutParams(-2, -2);
                    }
                    if (((ViewGroup.LayoutParams)object).width == -1) {
                        n3 = panelFeatureState.fullBackground;
                        n = -1;
                    } else {
                        n3 = panelFeatureState.background;
                        n = n2;
                    }
                    panelFeatureState.decorView.setWindowBackground(this.getContext().getDrawable(n3));
                    object2 = panelFeatureState.shownPanelView.getParent();
                    if (object2 != null && object2 instanceof ViewGroup) {
                        ((ViewGroup)object2).removeView(panelFeatureState.shownPanelView);
                    }
                    panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, (ViewGroup.LayoutParams)object);
                    if (!panelFeatureState.shownPanelView.hasFocus()) {
                        panelFeatureState.shownPanelView.requestFocus();
                    }
                }
                panelFeatureState.isHandled = false;
                object = new WindowManager.LayoutParams(n, -2, panelFeatureState.x, panelFeatureState.y, 1003, 8519680, panelFeatureState.decorView.mDefaultOpacity);
                if (panelFeatureState.isCompact) {
                    ((WindowManager.LayoutParams)object).gravity = this.getOptionsPanelGravity();
                    sRotationWatcher.addWindow(this);
                } else {
                    ((WindowManager.LayoutParams)object).gravity = panelFeatureState.gravity;
                }
                ((WindowManager.LayoutParams)object).windowAnimations = panelFeatureState.windowAnimations;
                windowManager.addView(panelFeatureState.decorView, (ViewGroup.LayoutParams)object);
                panelFeatureState.isOpen = true;
                return;
            }
            return;
        }
    }

    private void registerSwipeCallbacks(ViewGroup viewGroup) {
        if (!(viewGroup instanceof SwipeDismissLayout)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("contentParent is not a SwipeDismissLayout: ");
            stringBuilder.append(viewGroup);
            Log.w(TAG, stringBuilder.toString());
            return;
        }
        viewGroup = (SwipeDismissLayout)viewGroup;
        ((SwipeDismissLayout)viewGroup).setOnDismissedListener(new SwipeDismissLayout.OnDismissedListener(){

            @Override
            public void onDismissed(SwipeDismissLayout swipeDismissLayout) {
                PhoneWindow.this.dispatchOnWindowSwipeDismissed();
                PhoneWindow.this.dispatchOnWindowDismissed(false, true);
            }
        });
        ((SwipeDismissLayout)viewGroup).setOnSwipeProgressChangedListener(new SwipeDismissLayout.OnSwipeProgressChangedListener(){

            @Override
            public void onSwipeCancelled(SwipeDismissLayout object) {
                object = PhoneWindow.this.getAttributes();
                if (((WindowManager.LayoutParams)object).x != 0 || ((WindowManager.LayoutParams)object).alpha != 1.0f) {
                    ((WindowManager.LayoutParams)object).x = 0;
                    ((WindowManager.LayoutParams)object).alpha = 1.0f;
                    PhoneWindow.this.setAttributes((WindowManager.LayoutParams)object);
                    PhoneWindow.this.setFlags(1024, 1536);
                }
            }

            @Override
            public void onSwipeProgressChanged(SwipeDismissLayout object, float f, float f2) {
                object = PhoneWindow.this.getAttributes();
                ((WindowManager.LayoutParams)object).x = (int)f2;
                ((WindowManager.LayoutParams)object).alpha = f;
                PhoneWindow.this.setAttributes((WindowManager.LayoutParams)object);
                int n = ((WindowManager.LayoutParams)object).x == 0 ? 1024 : 512;
                PhoneWindow.this.setFlags(n, 1536);
            }
        });
    }

    private void reopenMenu(boolean bl) {
        boolean bl2;
        Object object = this.mDecorContentParent;
        if (object != null && object.canShowOverflowMenu() && (!ViewConfiguration.get(this.getContext()).hasPermanentMenuKey() || this.mDecorContentParent.isOverflowMenuShowPending())) {
            object = this.getCallback();
            if (this.mDecorContentParent.isOverflowMenuShowing() && bl) {
                this.mDecorContentParent.hideOverflowMenu();
                PanelFeatureState panelFeatureState = this.getPanelState(0, false);
                if (panelFeatureState != null && object != null && !this.isDestroyed()) {
                    object.onPanelClosed(8, panelFeatureState.menu);
                }
            } else if (object != null && !this.isDestroyed()) {
                PanelFeatureState panelFeatureState;
                if (this.mInvalidatePanelMenuPosted && (1 & this.mInvalidatePanelMenuFeatures) != 0) {
                    this.mDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
                    this.mInvalidatePanelMenuRunnable.run();
                }
                if ((panelFeatureState = this.getPanelState(0, false)) != null && panelFeatureState.menu != null && !panelFeatureState.refreshMenuContent && object.onPreparePanel(0, panelFeatureState.createdPanelView, panelFeatureState.menu)) {
                    object.onMenuOpened(8, panelFeatureState.menu);
                    this.mDecorContentParent.showOverflowMenu();
                }
            }
            return;
        }
        object = this.getPanelState(0, false);
        if (object == null) {
            return;
        }
        boolean bl3 = bl2 = ((PanelFeatureState)object).isInExpandedMode;
        if (bl) {
            bl3 = !bl2;
        }
        ((PanelFeatureState)object).refreshDecorView = true;
        this.closePanel((PanelFeatureState)object, false);
        ((PanelFeatureState)object).isInExpandedMode = bl3;
        this.openPanel((PanelFeatureState)object, null);
    }

    private void restorePanelState(SparseArray<Parcelable> sparseArray) {
        for (int i = sparseArray.size() - 1; i >= 0; --i) {
            int n = sparseArray.keyAt(i);
            PanelFeatureState panelFeatureState = this.getPanelState(n, false);
            if (panelFeatureState == null) continue;
            panelFeatureState.onRestoreInstanceState(sparseArray.get(n));
            this.invalidatePanelMenu(n);
        }
    }

    private void savePanelState(SparseArray<Parcelable> sparseArray) {
        PanelFeatureState[] arrpanelFeatureState = this.mPanels;
        if (arrpanelFeatureState == null) {
            return;
        }
        for (int i = arrpanelFeatureState.length - 1; i >= 0; --i) {
            if (arrpanelFeatureState[i] == null) continue;
            sparseArray.put(i, arrpanelFeatureState[i].onSaveInstanceState());
        }
    }

    public static void sendCloseSystemWindows(Context context, String string2) {
        if (ActivityManager.isSystemReady()) {
            try {
                ActivityManager.getService().closeSystemDialogs(string2);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    private void showProgressBars(ProgressBar progressBar, ProgressBar progressBar2) {
        int n = this.getLocalFeatures();
        if ((n & 32) != 0 && progressBar2 != null && progressBar2.getVisibility() == 4) {
            progressBar2.setVisibility(0);
        }
        if ((n & 4) != 0 && progressBar != null && progressBar.getProgress() < 10000) {
            progressBar.setVisibility(0);
        }
    }

    private void transitionTo(Scene scene) {
        if (this.mContentScene == null) {
            scene.enter();
        } else {
            this.mTransitionManager.transitionTo(scene);
        }
        this.mContentScene = scene;
    }

    private void updateDrawable(int n, DrawableFeatureState drawableFeatureState, boolean bl) {
        if (this.mContentParent == null) {
            return;
        }
        int n2 = 1 << n;
        if ((this.getFeatures() & n2) == 0 && !bl) {
            return;
        }
        Drawable drawable2 = null;
        if (drawableFeatureState != null) {
            Drawable drawable3 = drawable2 = drawableFeatureState.child;
            if (drawable2 == null) {
                drawable3 = drawableFeatureState.local;
            }
            drawable2 = drawable3;
            if (drawable3 == null) {
                drawable2 = drawableFeatureState.def;
            }
        }
        if ((this.getLocalFeatures() & n2) == 0) {
            if (this.getContainer() != null && (this.isActive() || bl)) {
                this.getContainer().setChildDrawable(n, drawable2);
            }
        } else if (drawableFeatureState != null && (drawableFeatureState.cur != drawable2 || drawableFeatureState.curAlpha != drawableFeatureState.alpha)) {
            drawableFeatureState.cur = drawable2;
            drawableFeatureState.curAlpha = drawableFeatureState.alpha;
            this.onDrawableChanged(n, drawable2, drawableFeatureState.alpha);
        }
    }

    private void updateInt(int n, int n2, boolean bl) {
        if (this.mContentParent == null) {
            return;
        }
        int n3 = 1 << n;
        if ((this.getFeatures() & n3) == 0 && !bl) {
            return;
        }
        if ((this.getLocalFeatures() & n3) == 0) {
            if (this.getContainer() != null) {
                this.getContainer().setChildInt(n, n2);
            }
        } else {
            this.onIntChanged(n, n2);
        }
    }

    private void updateProgressBars(int n) {
        ProgressBar progressBar = this.getCircularProgressBar(true);
        ProgressBar progressBar2 = this.getHorizontalProgressBar(true);
        int n2 = this.getLocalFeatures();
        if (n == -1) {
            if ((n2 & 4) != 0) {
                if (progressBar2 != null) {
                    n = progressBar2.getProgress();
                    n = !progressBar2.isIndeterminate() && n >= 10000 ? 4 : 0;
                    progressBar2.setVisibility(n);
                } else {
                    Log.e(TAG, "Horizontal progress bar not located in current window decor");
                }
            }
            if ((n2 & 32) != 0) {
                if (progressBar != null) {
                    progressBar.setVisibility(0);
                } else {
                    Log.e(TAG, "Circular progress bar not located in current window decor");
                }
            }
        } else if (n == -2) {
            if ((n2 & 4) != 0) {
                if (progressBar2 != null) {
                    progressBar2.setVisibility(8);
                } else {
                    Log.e(TAG, "Horizontal progress bar not located in current window decor");
                }
            }
            if ((n2 & 32) != 0) {
                if (progressBar != null) {
                    progressBar.setVisibility(8);
                } else {
                    Log.e(TAG, "Circular progress bar not located in current window decor");
                }
            }
        } else if (n == -3) {
            if (progressBar2 != null) {
                progressBar2.setIndeterminate(true);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
        } else if (n == -4) {
            if (progressBar2 != null) {
                progressBar2.setIndeterminate(false);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
        } else if (n >= 0 && n <= 10000) {
            if (progressBar2 != null) {
                progressBar2.setProgress(n + 0);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
            if (n < 10000) {
                this.showProgressBars(progressBar2, progressBar);
            } else {
                this.hideProgressBars(progressBar2, progressBar);
            }
        } else if (20000 <= n && n <= 30000) {
            if (progressBar2 != null) {
                progressBar2.setSecondaryProgress(n - 20000);
            } else {
                Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
            this.showProgressBars(progressBar2, progressBar);
        }
    }

    @Override
    public void addContentView(View object, ViewGroup.LayoutParams layoutParams) {
        if (this.mContentParent == null) {
            this.installDecor();
        }
        if (this.hasFeature(12)) {
            Log.v(TAG, "addContentView does not support content transitions");
        }
        this.mContentParent.addView((View)object, layoutParams);
        this.mContentParent.requestApplyInsets();
        object = this.getCallback();
        if (object != null && !this.isDestroyed()) {
            object.onContentChanged();
        }
    }

    @Override
    public void alwaysReadCloseOnTouchAttr() {
        this.mAlwaysReadCloseOnTouchAttr = true;
    }

    void checkCloseActionMenu(Menu menu2) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        this.mDecorContentParent.dismissPopups();
        Window.Callback callback = this.getCallback();
        if (callback != null && !this.isDestroyed()) {
            callback.onPanelClosed(8, menu2);
        }
        this.mClosingActionMenu = false;
    }

    @Override
    public void clearContentView() {
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.clearContentView();
        }
    }

    @Override
    public final void closeAllPanels() {
        if (this.getWindowManager() == null) {
            return;
        }
        PanelFeatureState[] arrpanelFeatureState = this.mPanels;
        int n = arrpanelFeatureState != null ? arrpanelFeatureState.length : 0;
        for (int i = 0; i < n; ++i) {
            PanelFeatureState panelFeatureState = arrpanelFeatureState[i];
            if (panelFeatureState == null) continue;
            this.closePanel(panelFeatureState, true);
        }
        this.closeContextMenu();
    }

    @Override
    public final void closePanel(int n) {
        DecorContentParent decorContentParent;
        if (n == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.canShowOverflowMenu() && !ViewConfiguration.get(this.getContext()).hasPermanentMenuKey()) {
            this.mDecorContentParent.hideOverflowMenu();
        } else if (n == 6) {
            this.closeContextMenu();
        } else {
            this.closePanel(this.getPanelState(n, true), true);
        }
    }

    public final void closePanel(PanelFeatureState panelFeatureState, boolean bl) {
        Object object;
        if (bl && panelFeatureState.featureId == 0 && (object = this.mDecorContentParent) != null && object.isOverflowMenuShowing()) {
            this.checkCloseActionMenu(panelFeatureState.menu);
            return;
        }
        object = this.getWindowManager();
        if (object != null && panelFeatureState.isOpen) {
            if (panelFeatureState.decorView != null) {
                object.removeView(panelFeatureState.decorView);
                if (panelFeatureState.isCompact) {
                    sRotationWatcher.removeWindow(this);
                }
            }
            if (bl) {
                this.callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, null);
            }
        }
        panelFeatureState.isPrepared = false;
        panelFeatureState.isHandled = false;
        panelFeatureState.isOpen = false;
        panelFeatureState.shownPanelView = null;
        if (panelFeatureState.isInExpandedMode) {
            panelFeatureState.refreshDecorView = true;
            panelFeatureState.isInExpandedMode = false;
        }
        if (this.mPreparedPanel == panelFeatureState) {
            this.mPreparedPanel = null;
            this.mPanelChordingKey = 0;
        }
    }

    @Override
    protected void dispatchWindowAttributesChanged(WindowManager.LayoutParams object) {
        super.dispatchWindowAttributesChanged((WindowManager.LayoutParams)object);
        object = this.mDecor;
        if (object != null) {
            ((DecorView)object).updateColorViews(null, true);
        }
    }

    void doInvalidatePanelMenu(int n) {
        PanelFeatureState panelFeatureState = this.getPanelState(n, false);
        if (panelFeatureState == null) {
            return;
        }
        if (panelFeatureState.menu != null) {
            Bundle bundle = new Bundle();
            panelFeatureState.menu.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelFeatureState.frozenActionViewState = bundle;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            panelFeatureState.menu.clear();
        }
        panelFeatureState.refreshMenuContent = true;
        panelFeatureState.refreshDecorView = true;
        if ((n == 8 || n == 0) && this.mDecorContentParent != null && (panelFeatureState = this.getPanelState(0, false)) != null) {
            panelFeatureState.isPrepared = false;
            this.preparePanel(panelFeatureState, null);
        }
    }

    void doPendingInvalidatePanelMenu() {
        if (this.mInvalidatePanelMenuPosted) {
            this.mDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
        }
    }

    public PanelFeatureState findMenuPanel(Menu menu2) {
        PanelFeatureState[] arrpanelFeatureState = this.mPanels;
        int n = arrpanelFeatureState != null ? arrpanelFeatureState.length : 0;
        for (int i = 0; i < n; ++i) {
            PanelFeatureState panelFeatureState = arrpanelFeatureState[i];
            if (panelFeatureState == null || panelFeatureState.menu != menu2) continue;
            return panelFeatureState;
        }
        return null;
    }

    protected DecorView generateDecor(int n) {
        Context context;
        if (this.mUseDecorContext) {
            context = this.getContext().getApplicationContext();
            if (context == null) {
                context = this.getContext();
            } else {
                DecorContext decorContext = new DecorContext(context, this.getContext());
                int n2 = this.mTheme;
                context = decorContext;
                if (n2 != -1) {
                    ((Context)decorContext).setTheme(n2);
                    context = decorContext;
                }
            }
        } else {
            context = this.getContext();
        }
        return new DecorView(context, n, this, this.getAttributes());
    }

    protected ViewGroup generateLayout(DecorView object) {
        boolean bl;
        Object object2 = this.getWindowStyle();
        this.mIsFloating = ((TypedArray)object2).getBoolean(4, false);
        int n = this.getForcedWindowFlags() & 65792;
        if (this.mIsFloating) {
            this.setLayout(-2, -2);
            this.setFlags(0, n);
        } else {
            this.setFlags(65792, n);
        }
        if (((TypedArray)object2).getBoolean(3, false)) {
            this.requestFeature(1);
        } else if (((TypedArray)object2).getBoolean(15, false)) {
            this.requestFeature(8);
        }
        if (((TypedArray)object2).getBoolean(17, false)) {
            this.requestFeature(9);
        }
        if (((TypedArray)object2).getBoolean(16, false)) {
            this.requestFeature(10);
        }
        if (((TypedArray)object2).getBoolean(25, false)) {
            this.requestFeature(11);
        }
        if (((TypedArray)object2).getBoolean(9, false)) {
            this.setFlags(1024, this.getForcedWindowFlags() & 1024);
        }
        if (((TypedArray)object2).getBoolean(23, false)) {
            this.setFlags(67108864, this.getForcedWindowFlags() & 67108864);
        }
        if (((TypedArray)object2).getBoolean(24, false)) {
            this.setFlags(134217728, this.getForcedWindowFlags() & 134217728);
        }
        if (((TypedArray)object2).getBoolean(22, false)) {
            this.setFlags(33554432, 33554432 & this.getForcedWindowFlags());
        }
        if (((TypedArray)object2).getBoolean(14, false)) {
            this.setFlags(1048576, 1048576 & this.getForcedWindowFlags());
        }
        if (((TypedArray)object2).getBoolean(18, bl = this.getContext().getApplicationInfo().targetSdkVersion >= 11)) {
            this.setFlags(8388608, 8388608 & this.getForcedWindowFlags());
        }
        ((TypedArray)object2).getValue(19, this.mMinWidthMajor);
        ((TypedArray)object2).getValue(20, this.mMinWidthMinor);
        if (((TypedArray)object2).hasValue(57)) {
            if (this.mFixedWidthMajor == null) {
                this.mFixedWidthMajor = new TypedValue();
            }
            ((TypedArray)object2).getValue(57, this.mFixedWidthMajor);
        }
        if (((TypedArray)object2).hasValue(58)) {
            if (this.mFixedWidthMinor == null) {
                this.mFixedWidthMinor = new TypedValue();
            }
            ((TypedArray)object2).getValue(58, this.mFixedWidthMinor);
        }
        if (((TypedArray)object2).hasValue(55)) {
            if (this.mFixedHeightMajor == null) {
                this.mFixedHeightMajor = new TypedValue();
            }
            ((TypedArray)object2).getValue(55, this.mFixedHeightMajor);
        }
        if (((TypedArray)object2).hasValue(56)) {
            if (this.mFixedHeightMinor == null) {
                this.mFixedHeightMinor = new TypedValue();
            }
            ((TypedArray)object2).getValue(56, this.mFixedHeightMinor);
        }
        if (((TypedArray)object2).getBoolean(26, false)) {
            this.requestFeature(12);
        }
        if (((TypedArray)object2).getBoolean(45, false)) {
            this.requestFeature(13);
        }
        this.mIsTranslucent = ((TypedArray)object2).getBoolean(5, false);
        Object object3 = this.getContext();
        int n2 = object3.getApplicationInfo().targetSdkVersion;
        n = n2 < 11 ? 1 : 0;
        int n3 = n2 < 14 ? 1 : 0;
        boolean bl2 = n2 < 21;
        n2 = n2 < 29 ? 1 : 0;
        bl = ((Context)object3).getResources().getBoolean(17891617);
        boolean bl3 = !this.hasFeature(8) || this.hasFeature(1);
        if (!(n != 0 || n3 != 0 && bl && bl3)) {
            this.setNeedsMenuKey(2);
        } else {
            this.setNeedsMenuKey(1);
        }
        if (!this.mForcedStatusBarColor) {
            this.mStatusBarColor = ((TypedArray)object2).getColor(35, -16777216);
        }
        if (!this.mForcedNavigationBarColor) {
            this.mNavigationBarColor = ((TypedArray)object2).getColor(36, -16777216);
            this.mNavigationBarDividerColor = ((TypedArray)object2).getColor(50, 0);
        }
        if (n2 == 0) {
            this.mEnsureStatusBarContrastWhenTransparent = ((TypedArray)object2).getBoolean(52, false);
            this.mEnsureNavigationBarContrastWhenTransparent = ((TypedArray)object2).getBoolean(53, true);
        }
        object3 = this.getAttributes();
        if (!this.mIsFloating) {
            if (!bl2 && ((TypedArray)object2).getBoolean(34, false)) {
                this.setFlags(Integer.MIN_VALUE, this.getForcedWindowFlags() & Integer.MIN_VALUE);
            }
            if (this.mDecor.mForceWindowDrawsBarBackgrounds) {
                ((WindowManager.LayoutParams)object3).privateFlags |= 131072;
            }
        }
        if (((TypedArray)object2).getBoolean(46, false)) {
            ((View)object).setSystemUiVisibility(((View)object).getSystemUiVisibility() | 8192);
        }
        if (((TypedArray)object2).getBoolean(49, false)) {
            ((View)object).setSystemUiVisibility(((View)object).getSystemUiVisibility() | 16);
        }
        if (((TypedArray)object2).hasValue(51)) {
            n = ((TypedArray)object2).getInt(51, -1);
            if (n >= 0 && n <= 2) {
                ((WindowManager.LayoutParams)object3).layoutInDisplayCutoutMode = n;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown windowLayoutInDisplayCutoutMode: ");
                ((StringBuilder)object).append(((TypedArray)object2).getString(51));
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
        }
        if ((this.mAlwaysReadCloseOnTouchAttr || this.getContext().getApplicationInfo().targetSdkVersion >= 11) && ((TypedArray)object2).getBoolean(21, false)) {
            this.setCloseOnTouchOutsideIfNotSet(true);
        }
        if (!this.hasSoftInputMode()) {
            ((WindowManager.LayoutParams)object3).softInputMode = ((TypedArray)object2).getInt(13, ((WindowManager.LayoutParams)object3).softInputMode);
        }
        if (((TypedArray)object2).getBoolean(11, this.mIsFloating)) {
            if ((this.getForcedWindowFlags() & 2) == 0) {
                ((WindowManager.LayoutParams)object3).flags |= 2;
            }
            if (!this.haveDimAmount()) {
                ((WindowManager.LayoutParams)object3).dimAmount = ((TypedArray)object2).getFloat(0, 0.5f);
            }
        }
        if (((WindowManager.LayoutParams)object3).windowAnimations == 0) {
            ((WindowManager.LayoutParams)object3).windowAnimations = ((TypedArray)object2).getResourceId(8, 0);
        }
        if (this.getContainer() == null) {
            if (this.mBackgroundDrawable == null) {
                if (this.mFrameResource == 0) {
                    this.mFrameResource = ((TypedArray)object2).getResourceId(2, 0);
                }
                if (((TypedArray)object2).hasValue(1)) {
                    this.mBackgroundDrawable = ((TypedArray)object2).getDrawable(1);
                }
            }
            if (((TypedArray)object2).hasValue(47)) {
                this.mBackgroundFallbackDrawable = ((TypedArray)object2).getDrawable(47);
            }
            if (this.mLoadElevation) {
                this.mElevation = ((TypedArray)object2).getDimension(38, 0.0f);
            }
            this.mClipToOutline = ((TypedArray)object2).getBoolean(39, false);
            this.mTextColor = ((TypedArray)object2).getColor(7, 0);
        }
        if (((n3 = this.getLocalFeatures()) & 2048) != 0) {
            n = 17367272;
            this.setCloseOnSwipeEnabled(true);
        } else if ((n3 & 24) != 0) {
            if (this.mIsFloating) {
                object = new TypedValue();
                this.getContext().getTheme().resolveAttribute(17956910, (TypedValue)object, true);
                n = ((TypedValue)object).resourceId;
            } else {
                n = 17367274;
            }
            this.removeFeature(8);
        } else if ((n3 & 36) != 0 && (n3 & 256) == 0) {
            n = 17367269;
        } else if ((n3 & 128) != 0) {
            if (this.mIsFloating) {
                object = new TypedValue();
                this.getContext().getTheme().resolveAttribute(17956907, (TypedValue)object, true);
                n = ((TypedValue)object).resourceId;
            } else {
                n = 17367268;
            }
            this.removeFeature(8);
        } else if ((n3 & 2) == 0) {
            if (this.mIsFloating) {
                object = new TypedValue();
                this.getContext().getTheme().resolveAttribute(17956909, (TypedValue)object, true);
                n = ((TypedValue)object).resourceId;
            } else {
                n = (n3 & 256) != 0 ? ((TypedArray)object2).getResourceId(54, 17367267) : 17367273;
            }
        } else {
            n = (n3 & 1024) != 0 ? 17367271 : 17367270;
        }
        this.mDecor.startChanging();
        this.mDecor.onResourcesLoaded(this.mLayoutInflater, n);
        object2 = (ViewGroup)this.findViewById(16908290);
        if (object2 != null) {
            if ((n3 & 32) != 0 && (object = this.getCircularProgressBar(false)) != null) {
                ((ProgressBar)object).setIndeterminate(true);
            }
            if ((n3 & 2048) != 0) {
                this.registerSwipeCallbacks((ViewGroup)object2);
            }
            if (this.getContainer() == null) {
                this.mDecor.setWindowBackground(this.mBackgroundDrawable);
                object = this.mFrameResource != 0 ? this.getContext().getDrawable(this.mFrameResource) : null;
                this.mDecor.setWindowFrame((Drawable)object);
                this.mDecor.setElevation(this.mElevation);
                this.mDecor.setClipToOutline(this.mClipToOutline);
                object = this.mTitle;
                if (object != null) {
                    this.setTitle((CharSequence)object);
                }
                if (this.mTitleColor == 0) {
                    this.mTitleColor = this.mTextColor;
                }
                this.setTitleColor(this.mTitleColor);
            }
            this.mDecor.finishChanging();
            return object2;
        }
        throw new RuntimeException("Window couldn't find content container view");
    }

    @Override
    public boolean getAllowEnterTransitionOverlap() {
        Boolean bl = this.mAllowEnterTransitionOverlap;
        boolean bl2 = bl == null ? true : bl;
        return bl2;
    }

    @Override
    public boolean getAllowReturnTransitionOverlap() {
        Boolean bl = this.mAllowReturnTransitionOverlap;
        boolean bl2 = bl == null ? true : bl;
        return bl2;
    }

    AudioManager getAudioManager() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager)this.getContext().getSystemService("audio");
        }
        return this.mAudioManager;
    }

    @Override
    public Scene getContentScene() {
        return this.mContentScene;
    }

    @Override
    public View getCurrentFocus() {
        View view = this.mDecor;
        view = view != null ? view.findFocus() : null;
        return view;
    }

    int getDecorCaptionShade() {
        return this.mDecorCaptionShade;
    }

    @Override
    public final View getDecorView() {
        if (this.mDecor == null || this.mForceDecorInstall) {
            this.installDecor();
        }
        return this.mDecor;
    }

    @Override
    public float getElevation() {
        return this.mElevation;
    }

    @Override
    public Transition getEnterTransition() {
        return this.mEnterTransition;
    }

    @Override
    public Transition getExitTransition() {
        return this.mExitTransition;
    }

    @Override
    public WindowInsetsController getInsetsController() {
        return this.mDecor.getWindowInsetsController();
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return this.mLayoutInflater;
    }

    int getLocalFeaturesPrivate() {
        return super.getLocalFeatures();
    }

    @Override
    public MediaController getMediaController() {
        return this.mMediaController;
    }

    @Override
    public int getNavigationBarColor() {
        return this.mNavigationBarColor;
    }

    @Override
    public int getNavigationBarDividerColor() {
        return this.mNavigationBarDividerColor;
    }

    PanelFeatureState getPanelState(int n, boolean bl) {
        return this.getPanelState(n, bl, null);
    }

    @Override
    public Transition getReenterTransition() {
        Transition transition2;
        block0 : {
            transition2 = this.mReenterTransition;
            if (transition2 != USE_DEFAULT_TRANSITION) break block0;
            transition2 = this.getExitTransition();
        }
        return transition2;
    }

    @Override
    public Transition getReturnTransition() {
        Transition transition2;
        block0 : {
            transition2 = this.mReturnTransition;
            if (transition2 != USE_DEFAULT_TRANSITION) break block0;
            transition2 = this.getEnterTransition();
        }
        return transition2;
    }

    @Override
    public Transition getSharedElementEnterTransition() {
        return this.mSharedElementEnterTransition;
    }

    @Override
    public Transition getSharedElementExitTransition() {
        return this.mSharedElementExitTransition;
    }

    @Override
    public Transition getSharedElementReenterTransition() {
        Transition transition2;
        block0 : {
            transition2 = this.mSharedElementReenterTransition;
            if (transition2 != USE_DEFAULT_TRANSITION) break block0;
            transition2 = this.getSharedElementExitTransition();
        }
        return transition2;
    }

    @Override
    public Transition getSharedElementReturnTransition() {
        Transition transition2;
        block0 : {
            transition2 = this.mSharedElementReturnTransition;
            if (transition2 != USE_DEFAULT_TRANSITION) break block0;
            transition2 = this.getSharedElementEnterTransition();
        }
        return transition2;
    }

    @Override
    public boolean getSharedElementsUseOverlay() {
        Boolean bl = this.mSharedElementsUseOverlay;
        boolean bl2 = bl == null ? true : bl;
        return bl2;
    }

    @Override
    public int getStatusBarColor() {
        return this.mStatusBarColor;
    }

    @Override
    public List<Rect> getSystemGestureExclusionRects() {
        return this.getViewRootImpl().getRootSystemGestureExclusionRects();
    }

    @Override
    public long getTransitionBackgroundFadeDuration() {
        long l;
        block0 : {
            l = this.mBackgroundFadeDurationMillis;
            if (l >= 0L) break block0;
            l = 300L;
        }
        return l;
    }

    @Override
    public TransitionManager getTransitionManager() {
        return this.mTransitionManager;
    }

    @Override
    public int getVolumeControlStream() {
        return this.mVolumeControlStreamType;
    }

    protected boolean initializePanelContent(PanelFeatureState panelFeatureState) {
        if (panelFeatureState.createdPanelView != null) {
            panelFeatureState.shownPanelView = panelFeatureState.createdPanelView;
            return true;
        }
        if (panelFeatureState.menu == null) {
            return false;
        }
        if (this.mPanelMenuPresenterCallback == null) {
            this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
        }
        MenuView menuView = panelFeatureState.isInListMode() ? panelFeatureState.getListMenuView(this.getContext(), this.mPanelMenuPresenterCallback) : panelFeatureState.getIconMenuView(this.getContext(), this.mPanelMenuPresenterCallback);
        panelFeatureState.shownPanelView = (View)((Object)menuView);
        if (panelFeatureState.shownPanelView != null) {
            int n = menuView.getWindowAnimations();
            if (n != 0) {
                panelFeatureState.windowAnimations = n;
            }
            return true;
        }
        return false;
    }

    protected boolean initializePanelDecor(PanelFeatureState panelFeatureState) {
        panelFeatureState.decorView = this.generateDecor(panelFeatureState.featureId);
        panelFeatureState.gravity = 81;
        panelFeatureState.setStyle(this.getContext());
        TypedArray typedArray = this.getContext().obtainStyledAttributes(null, R.styleable.Window, 0, panelFeatureState.listPresenterTheme);
        float f = typedArray.getDimension(38, 0.0f);
        if (f != 0.0f) {
            panelFeatureState.decorView.setElevation(f);
        }
        typedArray.recycle();
        return true;
    }

    protected boolean initializePanelMenu(PanelFeatureState panelFeatureState) {
        Object object;
        block10 : {
            Context context;
            block9 : {
                context = this.getContext();
                if (panelFeatureState.featureId == 0) break block9;
                object = context;
                if (panelFeatureState.featureId != 8) break block10;
            }
            object = context;
            if (this.mDecorContentParent != null) {
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = context.getTheme();
                theme.resolveAttribute(16843825, typedValue, true);
                object = null;
                if (typedValue.resourceId != 0) {
                    object = context.getResources().newTheme();
                    ((Resources.Theme)object).setTo(theme);
                    ((Resources.Theme)object).applyStyle(typedValue.resourceId, true);
                    ((Resources.Theme)object).resolveAttribute(16843671, typedValue, true);
                } else {
                    theme.resolveAttribute(16843671, typedValue, true);
                }
                Object object2 = object;
                if (typedValue.resourceId != 0) {
                    object2 = object;
                    if (object == null) {
                        object2 = context.getResources().newTheme();
                        ((Resources.Theme)object2).setTo(theme);
                    }
                    ((Resources.Theme)object2).applyStyle(typedValue.resourceId, true);
                }
                object = context;
                if (object2 != null) {
                    object = new ContextThemeWrapper(context, 0);
                    ((Context)object).getTheme().setTo((Resources.Theme)object2);
                }
            }
        }
        object = new MenuBuilder((Context)object);
        ((MenuBuilder)object).setCallback(this);
        panelFeatureState.setMenu((MenuBuilder)object);
        return true;
    }

    @Override
    public void injectInputEvent(InputEvent inputEvent) {
        this.getViewRootImpl().dispatchInputEvent(inputEvent);
    }

    @Override
    public void invalidatePanelMenu(int n) {
        DecorView decorView;
        this.mInvalidatePanelMenuFeatures |= 1 << n;
        if (!this.mInvalidatePanelMenuPosted && (decorView = this.mDecor) != null) {
            decorView.postOnAnimation(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    @Override
    public boolean isFloating() {
        return this.mIsFloating;
    }

    @Override
    public boolean isNavigationBarContrastEnforced() {
        return this.mEnsureNavigationBarContrastWhenTransparent;
    }

    @Override
    public boolean isShortcutKey(int n, KeyEvent keyEvent) {
        boolean bl = false;
        PanelFeatureState panelFeatureState = this.getPanelState(0, false);
        boolean bl2 = bl;
        if (panelFeatureState != null) {
            bl2 = bl;
            if (panelFeatureState.menu != null) {
                bl2 = bl;
                if (panelFeatureState.menu.isShortcutKey(n, keyEvent)) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    boolean isShowingWallpaper() {
        boolean bl = (this.getAttributes().flags & 1048576) != 0;
        return bl;
    }

    @Override
    public boolean isStatusBarContrastEnforced() {
        return this.mEnsureStatusBarContrastWhenTransparent;
    }

    public boolean isTranslucent() {
        return this.mIsTranslucent;
    }

    @Override
    protected void onActive() {
    }

    @Override
    public void onConfigurationChanged(Configuration parcelable) {
        PanelFeatureState panelFeatureState;
        if (this.mDecorContentParent == null && (panelFeatureState = this.getPanelState(0, false)) != null && panelFeatureState.menu != null) {
            if (panelFeatureState.isOpen) {
                parcelable = new Bundle();
                if (panelFeatureState.iconMenuPresenter != null) {
                    panelFeatureState.iconMenuPresenter.saveHierarchyState((Bundle)parcelable);
                }
                if (panelFeatureState.listMenuPresenter != null) {
                    panelFeatureState.listMenuPresenter.saveHierarchyState((Bundle)parcelable);
                }
                PhoneWindow.clearMenuViews(panelFeatureState);
                this.reopenMenu(false);
                if (panelFeatureState.iconMenuPresenter != null) {
                    panelFeatureState.iconMenuPresenter.restoreHierarchyState((Bundle)parcelable);
                }
                if (panelFeatureState.listMenuPresenter != null) {
                    panelFeatureState.listMenuPresenter.restoreHierarchyState((Bundle)parcelable);
                }
            } else {
                PhoneWindow.clearMenuViews(panelFeatureState);
            }
        }
    }

    protected void onDrawableChanged(int n, Drawable drawable2, int n2) {
        block7 : {
            ImageView imageView;
            block6 : {
                block5 : {
                    if (n != 3) break block5;
                    imageView = this.getLeftIconView();
                    break block6;
                }
                if (n != 4) break block7;
                imageView = this.getRightIconView();
            }
            if (drawable2 != null) {
                drawable2.setAlpha(n2);
                imageView.setImageDrawable(drawable2);
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
            return;
        }
    }

    protected void onIntChanged(int n, int n2) {
        if (n != 2 && n != 5) {
            FrameLayout frameLayout;
            if (n == 7 && (frameLayout = (FrameLayout)this.findViewById(16909470)) != null) {
                this.mLayoutInflater.inflate(n2, (ViewGroup)frameLayout);
            }
        } else {
            this.updateProgressBars(n2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean onKeyDown(int var1_1, int var2_2, KeyEvent var3_3) {
        block9 : {
            var4_4 = this.mDecor;
            var4_4 = var4_4 != null ? var4_4.getKeyDispatcherState() : null;
            var5_5 = 0;
            if (var2_2 == 4) break block9;
            if (var2_2 == 79) return this.mMediaController != null && this.getMediaSessionManager().dispatchMediaKeyEventAsSystemService(this.mMediaController.getSessionToken(), var3_3) != false;
            if (var2_2 == 82) ** GOTO lbl18
            if (var2_2 == 130) return this.mMediaController != null && this.getMediaSessionManager().dispatchMediaKeyEventAsSystemService(this.mMediaController.getSessionToken(), var3_3) != false;
            if (var2_2 == 164 || var2_2 == 24 || var2_2 == 25) ** GOTO lbl13
            if (var2_2 == 126 || var2_2 == 127) return this.mMediaController != null && this.getMediaSessionManager().dispatchMediaKeyEventAsSystemService(this.mMediaController.getSessionToken(), var3_3) != false;
            switch (var2_2) {
                default: {
                    return false;
                }
lbl13: // 1 sources:
                if (this.mMediaController != null) {
                    this.getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(this.mMediaController.getSessionToken(), var3_3);
                    return true;
                } else {
                    this.getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(var3_3, this.mVolumeControlStreamType);
                }
                return true;
lbl18: // 1 sources:
                if (var1_1 < 0) {
                    var1_1 = var5_5;
                }
                this.onKeyDownPanel(var1_1, var3_3);
                return true;
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 89: 
                case 90: 
                case 91: {
                    return this.mMediaController != null && this.getMediaSessionManager().dispatchMediaKeyEventAsSystemService(this.mMediaController.getSessionToken(), var3_3) != false;
                }
            }
        }
        if (var3_3.getRepeatCount() > 0 || var1_1 < 0) {
            return false;
        }
        if (var4_4 == null) return true;
        var4_4.startTracking(var3_3, this);
        return true;
    }

    public final boolean onKeyDownPanel(int n, KeyEvent keyEvent) {
        int n2 = keyEvent.getKeyCode();
        if (keyEvent.getRepeatCount() == 0) {
            this.mPanelChordingKey = n2;
            PanelFeatureState panelFeatureState = this.getPanelState(n, false);
            if (panelFeatureState != null && !panelFeatureState.isOpen) {
                return this.preparePanel(panelFeatureState, keyEvent);
            }
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean onKeyUp(int var1_1, int var2_2, KeyEvent var3_3) {
        block9 : {
            var4_4 = this.mDecor;
            var4_4 = var4_4 != null ? var4_4.getKeyDispatcherState() : null;
            if (var4_4 != null) {
                var4_4.handleUpEvent((KeyEvent)var3_3);
            }
            var5_5 = 0;
            if (var2_2 == 4) break block9;
            if (var2_2 == 79) return this.mMediaController != null && this.getMediaSessionManager().dispatchMediaKeyEventAsSystemService(this.mMediaController.getSessionToken(), (KeyEvent)var3_3) != false;
            if (var2_2 == 82) ** GOTO lbl33
            if (var2_2 == 130) return this.mMediaController != null && this.getMediaSessionManager().dispatchMediaKeyEventAsSystemService(this.mMediaController.getSessionToken(), (KeyEvent)var3_3) != false;
            if (var2_2 == 164) ** GOTO lbl31
            if (var2_2 == 171) ** GOTO lbl28
            if (var2_2 == 24 || var2_2 == 25) ** GOTO lbl23
            if (var2_2 == 126 || var2_2 == 127) return this.mMediaController != null && this.getMediaSessionManager().dispatchMediaKeyEventAsSystemService(this.mMediaController.getSessionToken(), (KeyEvent)var3_3) != false;
            switch (var2_2) {
                default: {
                    return false;
                }
                case 84: {
                    if (this.isNotInstantAppAndKeyguardRestricted() || (this.getContext().getResources().getConfiguration().uiMode & 15) == 6) return false;
                    if (!var3_3.isTracking() || var3_3.isCanceled()) return true;
                    this.launchDefaultSearch((KeyEvent)var3_3);
                    return true;
                }
lbl23: // 1 sources:
                if (this.mMediaController != null) {
                    this.getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(this.mMediaController.getSessionToken(), (KeyEvent)var3_3);
                    return true;
                } else {
                    this.getMediaSessionManager().dispatchVolumeKeyEventAsSystemService((KeyEvent)var3_3, this.mVolumeControlStreamType);
                }
                return true;
lbl28: // 1 sources:
                if (!this.mSupportsPictureInPicture || var3_3.isCanceled()) return true;
                this.getWindowControllerCallback().enterPictureInPictureModeIfPossible();
                return true;
lbl31: // 1 sources:
                this.getMediaSessionManager().dispatchVolumeKeyEventAsSystemService((KeyEvent)var3_3, Integer.MIN_VALUE);
                return true;
lbl33: // 1 sources:
                if (var1_1 < 0) {
                    var1_1 = var5_5;
                }
                this.onKeyUpPanel(var1_1, (KeyEvent)var3_3);
                return true;
                case 85: 
                case 86: 
                case 87: 
                case 88: 
                case 89: 
                case 90: 
                case 91: {
                    return this.mMediaController != null && this.getMediaSessionManager().dispatchMediaKeyEventAsSystemService(this.mMediaController.getSessionToken(), (KeyEvent)var3_3) != false;
                }
            }
        }
        if (var1_1 < 0 || !var3_3.isTracking() || var3_3.isCanceled()) return false;
        if (var1_1 == 0 && (var3_3 = this.getPanelState(var1_1, false)) != null && var3_3.isInExpandedMode) {
            this.reopenMenu(true);
            return true;
        }
        this.closePanel(var1_1);
        return true;
    }

    public final void onKeyUpPanel(int n, KeyEvent object) {
        if (this.mPanelChordingKey != 0) {
            Object object2;
            this.mPanelChordingKey = 0;
            PanelFeatureState panelFeatureState = this.getPanelState(n, false);
            if (!(((KeyEvent)object).isCanceled() || (object2 = this.mDecor) != null && ((DecorView)object2).mPrimaryActionMode != null || panelFeatureState == null)) {
                boolean bl;
                boolean bl2 = false;
                if (n == 0 && (object2 = this.mDecorContentParent) != null && object2.canShowOverflowMenu() && !ViewConfiguration.get(this.getContext()).hasPermanentMenuKey()) {
                    if (!this.mDecorContentParent.isOverflowMenuShowing()) {
                        bl = bl2;
                        if (!this.isDestroyed()) {
                            bl = bl2;
                            if (this.preparePanel(panelFeatureState, (KeyEvent)object)) {
                                bl = this.mDecorContentParent.showOverflowMenu();
                            }
                        }
                    } else {
                        bl = this.mDecorContentParent.hideOverflowMenu();
                    }
                } else if (!panelFeatureState.isOpen && !panelFeatureState.isHandled) {
                    bl = bl2;
                    if (panelFeatureState.isPrepared) {
                        boolean bl3 = true;
                        if (panelFeatureState.refreshMenuContent) {
                            panelFeatureState.isPrepared = false;
                            bl3 = this.preparePanel(panelFeatureState, (KeyEvent)object);
                        }
                        bl = bl2;
                        if (bl3) {
                            EventLog.writeEvent(50001, 0);
                            this.openPanel(panelFeatureState, (KeyEvent)object);
                            bl = true;
                        }
                    }
                } else {
                    bl = panelFeatureState.isOpen;
                    this.closePanel(panelFeatureState, true);
                }
                if (bl) {
                    object = (AudioManager)this.getContext().getSystemService("audio");
                    if (object != null) {
                        ((AudioManager)object).playSoundEffect(0);
                    } else {
                        Log.w(TAG, "Couldn't get audio manager");
                    }
                }
            } else {
                return;
            }
        }
    }

    @Override
    public boolean onMenuItemSelected(MenuBuilder object, MenuItem menuItem) {
        Window.Callback callback = this.getCallback();
        if (callback != null && !this.isDestroyed() && (object = this.findMenuPanel(((MenuBuilder)object).getRootMenu())) != null) {
            return callback.onMenuItemSelected(((PanelFeatureState)object).featureId, menuItem);
        }
        return false;
    }

    @Override
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        this.reopenMenu(true);
    }

    @Override
    public void onMultiWindowModeChanged() {
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.onConfigurationChanged(this.getContext().getResources().getConfiguration());
        }
    }

    void onOptionsPanelRotationChanged() {
        PanelFeatureState panelFeatureState = this.getPanelState(0, false);
        if (panelFeatureState == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = panelFeatureState.decorView != null ? (WindowManager.LayoutParams)panelFeatureState.decorView.getLayoutParams() : null;
        if (layoutParams != null) {
            layoutParams.gravity = this.getOptionsPanelGravity();
            WindowManager windowManager = this.getWindowManager();
            if (windowManager != null) {
                windowManager.updateViewLayout(panelFeatureState.decorView, layoutParams);
            }
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean bl) {
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updatePictureInPictureOutlineProvider(bl);
        }
    }

    void onViewRootImplSet(ViewRootImpl viewRootImpl) {
        viewRootImpl.setActivityConfigCallback(this.mActivityConfigCallback);
    }

    @Override
    public final void openPanel(int n, KeyEvent keyEvent) {
        DecorContentParent decorContentParent;
        if (n == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.canShowOverflowMenu() && !ViewConfiguration.get(this.getContext()).hasPermanentMenuKey()) {
            this.mDecorContentParent.showOverflowMenu();
        } else {
            this.openPanel(this.getPanelState(n, true), keyEvent);
        }
    }

    void openPanelsAfterRestore() {
        PanelFeatureState[] arrpanelFeatureState = this.mPanels;
        if (arrpanelFeatureState == null) {
            return;
        }
        for (int i = arrpanelFeatureState.length - 1; i >= 0; --i) {
            PanelFeatureState panelFeatureState = arrpanelFeatureState[i];
            if (panelFeatureState == null) continue;
            panelFeatureState.applyFrozenState();
            if (panelFeatureState.isOpen || !panelFeatureState.wasLastOpen) continue;
            panelFeatureState.isInExpandedMode = panelFeatureState.wasLastExpanded;
            this.openPanel(panelFeatureState, null);
        }
    }

    @Override
    public final View peekDecorView() {
        return this.mDecor;
    }

    @Override
    public boolean performContextMenuIdentifierAction(int n, int n2) {
        ContextMenuBuilder contextMenuBuilder = this.mContextMenu;
        boolean bl = contextMenuBuilder != null ? contextMenuBuilder.performIdentifierAction(n, n2) : false;
        return bl;
    }

    @Override
    public boolean performPanelIdentifierAction(int n, int n2, int n3) {
        PanelFeatureState panelFeatureState = this.getPanelState(n, true);
        if (!this.preparePanel(panelFeatureState, new KeyEvent(0, 82))) {
            return false;
        }
        if (panelFeatureState.menu == null) {
            return false;
        }
        boolean bl = panelFeatureState.menu.performIdentifierAction(n2, n3);
        if (this.mDecorContentParent == null) {
            this.closePanel(panelFeatureState, true);
        }
        return bl;
    }

    @Override
    public boolean performPanelShortcut(int n, int n2, KeyEvent keyEvent, int n3) {
        return this.performPanelShortcut(this.getPanelState(n, false), n2, keyEvent, n3);
    }

    boolean performPanelShortcut(PanelFeatureState panelFeatureState, int n, KeyEvent keyEvent, int n2) {
        block6 : {
            boolean bl;
            block8 : {
                boolean bl2;
                block7 : {
                    if (keyEvent.isSystem() || panelFeatureState == null) break block6;
                    bl2 = false;
                    if (panelFeatureState.isPrepared) break block7;
                    bl = bl2;
                    if (!this.preparePanel(panelFeatureState, keyEvent)) break block8;
                }
                bl = bl2;
                if (panelFeatureState.menu != null) {
                    bl = panelFeatureState.menu.performShortcut(n, keyEvent, n2);
                }
            }
            if (bl) {
                panelFeatureState.isHandled = true;
                if ((n2 & 1) == 0 && this.mDecorContentParent == null) {
                    this.closePanel(panelFeatureState, true);
                }
            }
            return bl;
        }
        return false;
    }

    public final boolean preparePanel(PanelFeatureState object, KeyEvent object2) {
        DecorContentParent decorContentParent;
        if (this.isDestroyed()) {
            return false;
        }
        if (((PanelFeatureState)object).isPrepared) {
            return true;
        }
        Object object3 = this.mPreparedPanel;
        if (object3 != null && object3 != object) {
            this.closePanel((PanelFeatureState)object3, false);
        }
        if ((object3 = this.getCallback()) != null) {
            ((PanelFeatureState)object).createdPanelView = object3.onCreatePanelView(((PanelFeatureState)object).featureId);
        }
        int n = ((PanelFeatureState)object).featureId != 0 && ((PanelFeatureState)object).featureId != 8 ? 0 : 1;
        if (n != 0 && (decorContentParent = this.mDecorContentParent) != null) {
            decorContentParent.setMenuPrepared();
        }
        if (((PanelFeatureState)object).createdPanelView == null) {
            if (((PanelFeatureState)object).menu == null || ((PanelFeatureState)object).refreshMenuContent) {
                if (!(((PanelFeatureState)object).menu != null || this.initializePanelMenu((PanelFeatureState)object) && ((PanelFeatureState)object).menu != null)) {
                    return false;
                }
                if (n != 0 && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(((PanelFeatureState)object).menu, this.mActionMenuPresenterCallback);
                }
                ((PanelFeatureState)object).menu.stopDispatchingItemsChanged();
                if (object3 == null || !object3.onCreatePanelMenu(((PanelFeatureState)object).featureId, ((PanelFeatureState)object).menu)) {
                    ((PanelFeatureState)object).setMenu(null);
                    if (n != 0 && (object = this.mDecorContentParent) != null) {
                        object.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                ((PanelFeatureState)object).refreshMenuContent = false;
            }
            ((PanelFeatureState)object).menu.stopDispatchingItemsChanged();
            if (((PanelFeatureState)object).frozenActionViewState != null) {
                ((PanelFeatureState)object).menu.restoreActionViewStates(((PanelFeatureState)object).frozenActionViewState);
                ((PanelFeatureState)object).frozenActionViewState = null;
            }
            if (!object3.onPreparePanel(((PanelFeatureState)object).featureId, ((PanelFeatureState)object).createdPanelView, ((PanelFeatureState)object).menu)) {
                if (n != 0 && (object2 = this.mDecorContentParent) != null) {
                    object2.setMenu(null, this.mActionMenuPresenterCallback);
                }
                ((PanelFeatureState)object).menu.startDispatchingItemsChanged();
                return false;
            }
            n = object2 != null ? ((KeyEvent)object2).getDeviceId() : -1;
            boolean bl = KeyCharacterMap.load(n).getKeyboardType() != 1;
            ((PanelFeatureState)object).qwertyMode = bl;
            ((PanelFeatureState)object).menu.setQwertyMode(((PanelFeatureState)object).qwertyMode);
            ((PanelFeatureState)object).menu.startDispatchingItemsChanged();
        }
        ((PanelFeatureState)object).isPrepared = true;
        ((PanelFeatureState)object).isHandled = false;
        this.mPreparedPanel = object;
        return true;
    }

    @Override
    public void reportActivityRelaunched() {
        DecorView decorView = this.mDecor;
        if (decorView != null && decorView.getViewRootImpl() != null) {
            this.mDecor.getViewRootImpl().reportActivityRelaunched();
        }
    }

    @Override
    public boolean requestFeature(int n) {
        if (!this.mContentParentExplicitlySet) {
            int n2 = this.getFeatures();
            int n3 = 1 << n | n2;
            if ((n3 & 128) != 0 && (n3 & -13506) != 0) {
                throw new AndroidRuntimeException("You cannot combine custom titles with other title features");
            }
            if ((n2 & 2) != 0 && n == 8) {
                return false;
            }
            if ((n2 & 256) != 0 && n == 1) {
                this.removeFeature(8);
            }
            if ((n2 & 256) != 0 && n == 11) {
                throw new AndroidRuntimeException("You cannot combine swipe dismissal and the action bar.");
            }
            if ((n2 & 2048) != 0 && n == 8) {
                throw new AndroidRuntimeException("You cannot combine swipe dismissal and the action bar.");
            }
            if (n == 5 && this.getContext().getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
                throw new AndroidRuntimeException("You cannot use indeterminate progress on a watch.");
            }
            return super.requestFeature(n);
        }
        throw new AndroidRuntimeException("requestFeature() must be called before adding content");
    }

    @Override
    public void restoreHierarchyState(Bundle cloneable) {
        int n;
        if (this.mContentParent == null) {
            return;
        }
        SparseArray<Parcelable> sparseArray = cloneable.getSparseParcelableArray(VIEWS_TAG);
        if (sparseArray != null) {
            this.mContentParent.restoreHierarchyState(sparseArray);
        }
        if ((n = cloneable.getInt(FOCUSED_ID_TAG, -1)) != -1) {
            sparseArray = this.mContentParent.findViewById(n);
            if (sparseArray != null) {
                ((View)((Object)sparseArray)).requestFocus();
            } else {
                sparseArray = new StringBuilder();
                ((StringBuilder)((Object)sparseArray)).append("Previously focused view reported id ");
                ((StringBuilder)((Object)sparseArray)).append(n);
                ((StringBuilder)((Object)sparseArray)).append(" during save, but can't be found during restore.");
                Log.w(TAG, ((StringBuilder)((Object)sparseArray)).toString());
            }
        }
        if ((sparseArray = cloneable.getSparseParcelableArray(PANELS_TAG)) != null) {
            this.restorePanelState(sparseArray);
        }
        if (this.mDecorContentParent != null) {
            if ((cloneable = cloneable.getSparseParcelableArray(ACTION_BAR_TAG)) != null) {
                this.doPendingInvalidatePanelMenu();
                this.mDecorContentParent.restoreToolbarHierarchyState((SparseArray<Parcelable>)cloneable);
            } else {
                Log.w(TAG, "Missing saved instance states for action bar views! State will not be restored.");
            }
        }
    }

    @Override
    public Bundle saveHierarchyState() {
        Bundle bundle = new Bundle();
        if (this.mContentParent == null) {
            return bundle;
        }
        SparseArray<Parcelable> sparseArray = new SparseArray<Parcelable>();
        this.mContentParent.saveHierarchyState(sparseArray);
        bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray);
        sparseArray = this.mContentParent.findFocus();
        if (sparseArray != null && ((View)((Object)sparseArray)).getId() != -1) {
            bundle.putInt(FOCUSED_ID_TAG, ((View)((Object)sparseArray)).getId());
        }
        sparseArray = new SparseArray<Parcelable>();
        this.savePanelState(sparseArray);
        if (sparseArray.size() > 0) {
            bundle.putSparseParcelableArray(PANELS_TAG, sparseArray);
        }
        if (this.mDecorContentParent != null) {
            sparseArray = new SparseArray();
            this.mDecorContentParent.saveToolbarHierarchyState(sparseArray);
            bundle.putSparseParcelableArray(ACTION_BAR_TAG, sparseArray);
        }
        return bundle;
    }

    void sendCloseSystemWindows() {
        PhoneWindow.sendCloseSystemWindows(this.getContext(), null);
    }

    void sendCloseSystemWindows(String string2) {
        PhoneWindow.sendCloseSystemWindows(this.getContext(), string2);
    }

    @Override
    public void setAllowEnterTransitionOverlap(boolean bl) {
        this.mAllowEnterTransitionOverlap = bl;
    }

    @Override
    public void setAllowReturnTransitionOverlap(boolean bl) {
        this.mAllowReturnTransitionOverlap = bl;
    }

    @Override
    public void setAttributes(WindowManager.LayoutParams layoutParams) {
        super.setAttributes(layoutParams);
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateLogTag(layoutParams);
        }
    }

    @Override
    public final void setBackgroundDrawable(Drawable object) {
        if (object != this.mBackgroundDrawable) {
            this.mBackgroundDrawable = object;
            Object object2 = this.mDecor;
            if (object2 != null) {
                ((DecorView)object2).setWindowBackground((Drawable)object);
                object2 = this.mBackgroundFallbackDrawable;
                if (object2 != null) {
                    DecorView decorView = this.mDecor;
                    object = object != null ? null : object2;
                    decorView.setBackgroundFallback((Drawable)object);
                }
            }
        }
    }

    @Override
    public final void setChildDrawable(int n, Drawable drawable2) {
        DrawableFeatureState drawableFeatureState = this.getDrawableState(n, true);
        drawableFeatureState.child = drawable2;
        this.updateDrawable(n, drawableFeatureState, false);
    }

    @Override
    public final void setChildInt(int n, int n2) {
        this.updateInt(n, n2, false);
    }

    @Override
    public final void setClipToOutline(boolean bl) {
        this.mClipToOutline = bl;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.setClipToOutline(bl);
        }
    }

    @Override
    public void setCloseOnSwipeEnabled(boolean bl) {
        ViewGroup viewGroup;
        if (this.hasFeature(11) && (viewGroup = this.mContentParent) instanceof SwipeDismissLayout) {
            ((SwipeDismissLayout)viewGroup).setDismissable(bl);
        }
        super.setCloseOnSwipeEnabled(bl);
    }

    @Override
    public final void setContainer(Window window) {
        super.setContainer(window);
    }

    @Override
    public void setContentView(int n) {
        if (this.mContentParent == null) {
            this.installDecor();
        } else if (!this.hasFeature(12)) {
            this.mContentParent.removeAllViews();
        }
        if (this.hasFeature(12)) {
            this.transitionTo(Scene.getSceneForLayout(this.mContentParent, n, this.getContext()));
        } else {
            this.mLayoutInflater.inflate(n, this.mContentParent);
        }
        this.mContentParent.requestApplyInsets();
        Window.Callback callback = this.getCallback();
        if (callback != null && !this.isDestroyed()) {
            callback.onContentChanged();
        }
        this.mContentParentExplicitlySet = true;
    }

    @Override
    public void setContentView(View view) {
        this.setContentView(view, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override
    public void setContentView(View object, ViewGroup.LayoutParams layoutParams) {
        if (this.mContentParent == null) {
            this.installDecor();
        } else if (!this.hasFeature(12)) {
            this.mContentParent.removeAllViews();
        }
        if (this.hasFeature(12)) {
            ((View)object).setLayoutParams(layoutParams);
            this.transitionTo(new Scene(this.mContentParent, (View)object));
        } else {
            this.mContentParent.addView((View)object, layoutParams);
        }
        this.mContentParent.requestApplyInsets();
        object = this.getCallback();
        if (object != null && !this.isDestroyed()) {
            object.onContentChanged();
        }
        this.mContentParentExplicitlySet = true;
    }

    @Override
    public void setDecorCaptionShade(int n) {
        this.mDecorCaptionShade = n;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateDecorCaptionShade();
        }
    }

    @Override
    public void setDefaultIcon(int n) {
        if ((this.mResourcesSetFlags & 1) != 0) {
            return;
        }
        this.mIconRes = n;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (!(decorContentParent == null || decorContentParent.hasIcon() && (this.mResourcesSetFlags & 4) == 0)) {
            if (n != 0) {
                this.mDecorContentParent.setIcon(n);
                this.mResourcesSetFlags &= -5;
            } else {
                this.mDecorContentParent.setIcon(this.getContext().getPackageManager().getDefaultActivityIcon());
                this.mResourcesSetFlags |= 4;
            }
        }
    }

    @Override
    public void setDefaultLogo(int n) {
        if ((this.mResourcesSetFlags & 2) != 0) {
            return;
        }
        this.mLogoRes = n;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null && !decorContentParent.hasLogo()) {
            this.mDecorContentParent.setLogo(n);
        }
    }

    @Override
    protected void setDefaultWindowFormat(int n) {
        super.setDefaultWindowFormat(n);
    }

    @Override
    public final void setElevation(float f) {
        this.mElevation = f;
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.setElevation(f);
            layoutParams.setSurfaceInsets(this.mDecor, true, false);
        }
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    @Override
    public void setEnterTransition(Transition transition2) {
        this.mEnterTransition = transition2;
    }

    @Override
    public void setExitTransition(Transition transition2) {
        this.mExitTransition = transition2;
    }

    protected final void setFeatureDefaultDrawable(int n, Drawable drawable2) {
        DrawableFeatureState drawableFeatureState = this.getDrawableState(n, true);
        if (drawableFeatureState.def != drawable2) {
            drawableFeatureState.def = drawable2;
            this.updateDrawable(n, drawableFeatureState, false);
        }
    }

    @Override
    public final void setFeatureDrawable(int n, Drawable drawable2) {
        DrawableFeatureState drawableFeatureState = this.getDrawableState(n, true);
        drawableFeatureState.resid = 0;
        drawableFeatureState.uri = null;
        if (drawableFeatureState.local != drawable2) {
            drawableFeatureState.local = drawable2;
            this.updateDrawable(n, drawableFeatureState, false);
        }
    }

    @Override
    public void setFeatureDrawableAlpha(int n, int n2) {
        DrawableFeatureState drawableFeatureState = this.getDrawableState(n, true);
        if (drawableFeatureState.alpha != n2) {
            drawableFeatureState.alpha = n2;
            this.updateDrawable(n, drawableFeatureState, false);
        }
    }

    @Override
    public final void setFeatureDrawableResource(int n, int n2) {
        if (n2 != 0) {
            DrawableFeatureState drawableFeatureState = this.getDrawableState(n, true);
            if (drawableFeatureState.resid != n2) {
                drawableFeatureState.resid = n2;
                drawableFeatureState.uri = null;
                drawableFeatureState.local = this.getContext().getDrawable(n2);
                this.updateDrawable(n, drawableFeatureState, false);
            }
        } else {
            this.setFeatureDrawable(n, null);
        }
    }

    @Override
    public final void setFeatureDrawableUri(int n, Uri uri) {
        if (uri != null) {
            DrawableFeatureState drawableFeatureState = this.getDrawableState(n, true);
            if (drawableFeatureState.uri == null || !drawableFeatureState.uri.equals(uri)) {
                drawableFeatureState.resid = 0;
                drawableFeatureState.uri = uri;
                drawableFeatureState.local = this.loadImageURI(uri);
                this.updateDrawable(n, drawableFeatureState, false);
            }
        } else {
            this.setFeatureDrawable(n, null);
        }
    }

    @Override
    public final void setFeatureInt(int n, int n2) {
        this.updateInt(n, n2, false);
    }

    @Override
    public void setIcon(int n) {
        this.mIconRes = n;
        this.mResourcesSetFlags |= 1;
        this.mResourcesSetFlags &= -5;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setIcon(n);
        }
    }

    public void setIsStartingWindow(boolean bl) {
        this.mIsStartingWindow = bl;
    }

    @Override
    public void setLocalFocus(boolean bl, boolean bl2) {
        this.getViewRootImpl().windowFocusChanged(bl, bl2);
    }

    @Override
    public void setLogo(int n) {
        this.mLogoRes = n;
        this.mResourcesSetFlags |= 2;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setLogo(n);
        }
    }

    @Override
    public void setMediaController(MediaController mediaController) {
        this.mMediaController = mediaController;
    }

    @Override
    public void setNavigationBarColor(int n) {
        this.mNavigationBarColor = n;
        this.mForcedNavigationBarColor = true;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateColorViews(null, false);
        }
    }

    @Override
    public void setNavigationBarContrastEnforced(boolean bl) {
        this.mEnsureNavigationBarContrastWhenTransparent = bl;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateColorViews(null, false);
        }
    }

    @Override
    public void setNavigationBarDividerColor(int n) {
        this.mNavigationBarDividerColor = n;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateColorViews(null, false);
        }
    }

    @Override
    public void setReenterTransition(Transition transition2) {
        this.mReenterTransition = transition2;
    }

    @Override
    public void setResizingCaptionDrawable(Drawable drawable2) {
        this.mDecor.setUserCaptionBackgroundDrawable(drawable2);
    }

    @Override
    public void setReturnTransition(Transition transition2) {
        this.mReturnTransition = transition2;
    }

    @Override
    public void setSharedElementEnterTransition(Transition transition2) {
        this.mSharedElementEnterTransition = transition2;
    }

    @Override
    public void setSharedElementExitTransition(Transition transition2) {
        this.mSharedElementExitTransition = transition2;
    }

    @Override
    public void setSharedElementReenterTransition(Transition transition2) {
        this.mSharedElementReenterTransition = transition2;
    }

    @Override
    public void setSharedElementReturnTransition(Transition transition2) {
        this.mSharedElementReturnTransition = transition2;
    }

    @Override
    public void setSharedElementsUseOverlay(boolean bl) {
        this.mSharedElementsUseOverlay = bl;
    }

    @Override
    public void setStatusBarColor(int n) {
        this.mStatusBarColor = n;
        this.mForcedStatusBarColor = true;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateColorViews(null, false);
        }
    }

    @Override
    public void setStatusBarContrastEnforced(boolean bl) {
        this.mEnsureStatusBarContrastWhenTransparent = bl;
        DecorView decorView = this.mDecor;
        if (decorView != null) {
            decorView.updateColorViews(null, false);
        }
    }

    @Override
    public void setSystemGestureExclusionRects(List<Rect> list) {
        this.getViewRootImpl().setRootSystemGestureExclusionRects(list);
    }

    @Override
    public void setTheme(int n) {
        this.mTheme = n;
        Object object = this.mDecor;
        if (object != null && (object = ((View)object).getContext()) instanceof DecorContext) {
            ((Context)object).setTheme(n);
        }
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.setTitle(charSequence, true);
    }

    public void setTitle(CharSequence object, boolean bl) {
        Object object2 = this.mTitleView;
        if (object2 != null) {
            ((TextView)object2).setText((CharSequence)object);
        } else {
            object2 = this.mDecorContentParent;
            if (object2 != null) {
                object2.setWindowTitle((CharSequence)object);
            }
        }
        this.mTitle = object;
        if (bl) {
            object2 = this.getAttributes();
            if (!TextUtils.equals((CharSequence)object, ((WindowManager.LayoutParams)object2).accessibilityTitle)) {
                ((WindowManager.LayoutParams)object2).accessibilityTitle = TextUtils.stringOrSpannedString((CharSequence)object);
                object = this.mDecor;
                if (object != null && (object = ((View)object).getViewRootImpl()) != null) {
                    ((ViewRootImpl)object).onWindowTitleChanged();
                }
                this.dispatchWindowAttributesChanged(this.getAttributes());
            }
        }
    }

    @Deprecated
    @Override
    public void setTitleColor(int n) {
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setTextColor(n);
        }
        this.mTitleColor = n;
    }

    @Override
    public void setTransitionBackgroundFadeDuration(long l) {
        if (l >= 0L) {
            this.mBackgroundFadeDurationMillis = l;
            return;
        }
        throw new IllegalArgumentException("negative durations are not allowed");
    }

    @Override
    public void setTransitionManager(TransitionManager transitionManager) {
        this.mTransitionManager = transitionManager;
    }

    @Override
    public void setUiOptions(int n) {
        this.mUiOptions = n;
    }

    @Override
    public void setUiOptions(int n, int n2) {
        this.mUiOptions = this.mUiOptions & n2 | n & n2;
    }

    @Override
    public void setVolumeControlStream(int n) {
        this.mVolumeControlStreamType = n;
    }

    @Override
    public boolean superDispatchGenericMotionEvent(MotionEvent motionEvent) {
        return this.mDecor.superDispatchGenericMotionEvent(motionEvent);
    }

    @Override
    public boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        return this.mDecor.superDispatchKeyEvent(keyEvent);
    }

    @Override
    public boolean superDispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return this.mDecor.superDispatchKeyShortcutEvent(keyEvent);
    }

    @Override
    public boolean superDispatchTouchEvent(MotionEvent motionEvent) {
        return this.mDecor.superDispatchTouchEvent(motionEvent);
    }

    @Override
    public boolean superDispatchTrackballEvent(MotionEvent motionEvent) {
        return this.mDecor.superDispatchTrackballEvent(motionEvent);
    }

    @Override
    public void takeInputQueue(InputQueue.Callback callback) {
        this.mTakeInputQueueCallback = callback;
    }

    @Override
    public void takeKeyEvents(boolean bl) {
        this.mDecor.setFocusable(bl);
    }

    @Override
    public void takeSurface(SurfaceHolder.Callback2 callback2) {
        this.mTakeSurfaceCallback = callback2;
    }

    @Override
    public final void togglePanel(int n, KeyEvent keyEvent) {
        PanelFeatureState panelFeatureState = this.getPanelState(n, true);
        if (panelFeatureState.isOpen) {
            this.closePanel(panelFeatureState, true);
        } else {
            this.openPanel(panelFeatureState, keyEvent);
        }
    }

    protected final void updateDrawable(int n, boolean bl) {
        DrawableFeatureState drawableFeatureState = this.getDrawableState(n, false);
        if (drawableFeatureState != null) {
            this.updateDrawable(n, drawableFeatureState, bl);
        }
    }

    private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback {
        private ActionMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
            PhoneWindow.this.checkCloseActionMenu(menuBuilder);
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback = PhoneWindow.this.getCallback();
            if (callback != null) {
                callback.onMenuOpened(8, menuBuilder);
                return true;
            }
            return false;
        }
    }

    private static final class DrawableFeatureState {
        int alpha = 255;
        Drawable child;
        Drawable cur;
        int curAlpha = 255;
        Drawable def;
        final int featureId;
        Drawable local;
        int resid;
        Uri uri;

        DrawableFeatureState(int n) {
            this.featureId = n;
        }
    }

    static final class PanelFeatureState {
        int background;
        View createdPanelView;
        DecorView decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int fullBackground;
        int gravity;
        IconMenuPresenter iconMenuPresenter;
        boolean isCompact;
        boolean isHandled;
        boolean isInExpandedMode;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        int listPresenterTheme;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastExpanded;
        boolean wasLastOpen;
        int windowAnimations;
        int x;
        int y;

        PanelFeatureState(int n) {
            this.featureId = n;
            this.refreshDecorView = false;
        }

        void applyFrozenState() {
            Bundle bundle;
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder != null && (bundle = this.frozenMenuState) != null) {
                menuBuilder.restorePresenterStates(bundle);
                this.frozenMenuState = null;
            }
        }

        public void clearMenuPresenters() {
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder != null) {
                menuBuilder.removeMenuPresenter(this.iconMenuPresenter);
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.iconMenuPresenter = null;
            this.listMenuPresenter = null;
        }

        MenuView getIconMenuView(Context context, MenuPresenter.Callback callback) {
            if (this.menu == null) {
                return null;
            }
            if (this.iconMenuPresenter == null) {
                this.iconMenuPresenter = new IconMenuPresenter(context);
                this.iconMenuPresenter.setCallback(callback);
                this.iconMenuPresenter.setId(16908996);
                this.menu.addMenuPresenter(this.iconMenuPresenter);
            }
            return this.iconMenuPresenter.getMenuView(this.decorView);
        }

        MenuView getListMenuView(Context object, MenuPresenter.Callback callback) {
            if (this.menu == null) {
                return null;
            }
            if (!this.isCompact) {
                this.getIconMenuView((Context)object, callback);
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter = new ListMenuPresenter(17367181, this.listPresenterTheme);
                this.listMenuPresenter.setCallback(callback);
                this.listMenuPresenter.setId(16909074);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            if ((object = this.iconMenuPresenter) != null) {
                this.listMenuPresenter.setItemIndexOffset(((IconMenuPresenter)object).getNumActualItemsShown());
            }
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        public boolean hasPanelItems() {
            View view = this.shownPanelView;
            boolean bl = false;
            boolean bl2 = false;
            if (view == null) {
                return false;
            }
            if (this.createdPanelView != null) {
                return true;
            }
            if (!this.isCompact && !this.isInExpandedMode) {
                if (((ViewGroup)view).getChildCount() > 0) {
                    bl2 = true;
                }
                return bl2;
            }
            bl2 = bl;
            if (this.listMenuPresenter.getAdapter().getCount() > 0) {
                bl2 = true;
            }
            return bl2;
        }

        public boolean isInListMode() {
            boolean bl = this.isInExpandedMode || this.isCompact;
            return bl;
        }

        void onRestoreInstanceState(Parcelable parcelable) {
            parcelable = (SavedState)parcelable;
            this.featureId = ((SavedState)parcelable).featureId;
            this.wasLastOpen = ((SavedState)parcelable).isOpen;
            this.wasLastExpanded = ((SavedState)parcelable).isInExpandedMode;
            this.frozenMenuState = ((SavedState)parcelable).menuState;
            this.createdPanelView = null;
            this.shownPanelView = null;
            this.decorView = null;
        }

        Parcelable onSaveInstanceState() {
            SavedState savedState = new SavedState();
            savedState.featureId = this.featureId;
            savedState.isOpen = this.isOpen;
            savedState.isInExpandedMode = this.isInExpandedMode;
            if (this.menu != null) {
                savedState.menuState = new Bundle();
                this.menu.savePresenterStates(savedState.menuState);
            }
            return savedState;
        }

        void setMenu(MenuBuilder menuBuilder) {
            Object object = this.menu;
            if (menuBuilder == object) {
                return;
            }
            if (object != null) {
                ((MenuBuilder)object).removeMenuPresenter(this.iconMenuPresenter);
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.menu = menuBuilder;
            if (menuBuilder != null) {
                object = this.iconMenuPresenter;
                if (object != null) {
                    menuBuilder.addMenuPresenter((MenuPresenter)object);
                }
                if ((object = this.listMenuPresenter) != null) {
                    menuBuilder.addMenuPresenter((MenuPresenter)object);
                }
            }
        }

        void setStyle(Context object) {
            object = ((Context)object).obtainStyledAttributes(R.styleable.Theme);
            this.background = ((TypedArray)object).getResourceId(46, 0);
            this.fullBackground = ((TypedArray)object).getResourceId(47, 0);
            this.windowAnimations = ((TypedArray)object).getResourceId(93, 0);
            this.isCompact = ((TypedArray)object).getBoolean(314, false);
            this.listPresenterTheme = ((TypedArray)object).getResourceId(315, 16974854);
            ((TypedArray)object).recycle();
        }

        private static class SavedState
        implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

                @Override
                public SavedState createFromParcel(Parcel parcel) {
                    return SavedState.readFromParcel(parcel);
                }

                public SavedState[] newArray(int n) {
                    return new SavedState[n];
                }
            };
            int featureId;
            boolean isInExpandedMode;
            boolean isOpen;
            Bundle menuState;

            private SavedState() {
            }

            private static SavedState readFromParcel(Parcel parcel) {
                SavedState savedState = new SavedState();
                savedState.featureId = parcel.readInt();
                int n = parcel.readInt();
                boolean bl = false;
                boolean bl2 = n == 1;
                savedState.isOpen = bl2;
                bl2 = bl;
                if (parcel.readInt() == 1) {
                    bl2 = true;
                }
                savedState.isInExpandedMode = bl2;
                if (savedState.isOpen) {
                    savedState.menuState = parcel.readBundle();
                }
                return savedState;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int n) {
                parcel.writeInt(this.featureId);
                parcel.writeInt((int)this.isOpen);
                parcel.writeInt((int)this.isInExpandedMode);
                if (this.isOpen) {
                    parcel.writeBundle(this.menuState);
                }
            }

        }

    }

    private class PanelMenuPresenterCallback
    implements MenuPresenter.Callback {
        private PanelMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder object, boolean bl) {
            MenuBuilder menuBuilder = ((MenuBuilder)object).getRootMenu();
            boolean bl2 = menuBuilder != object;
            PhoneWindow phoneWindow = PhoneWindow.this;
            if (bl2) {
                object = menuBuilder;
            }
            if ((object = phoneWindow.findMenuPanel((Menu)object)) != null) {
                if (bl2) {
                    PhoneWindow.this.callOnPanelClosed(((PanelFeatureState)object).featureId, (PanelFeatureState)object, menuBuilder);
                    PhoneWindow.this.closePanel((PanelFeatureState)object, true);
                } else {
                    PhoneWindow.this.closePanel((PanelFeatureState)object, bl);
                }
            }
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback;
            if (menuBuilder == null && PhoneWindow.this.hasFeature(8) && (callback = PhoneWindow.this.getCallback()) != null && !PhoneWindow.this.isDestroyed()) {
                callback.onMenuOpened(8, menuBuilder);
            }
            return true;
        }
    }

    public static final class PhoneWindowMenuCallback
    implements MenuBuilder.Callback,
    MenuPresenter.Callback {
        private static final int FEATURE_ID = 6;
        private boolean mShowDialogForSubmenu;
        private MenuDialogHelper mSubMenuHelper;
        private final PhoneWindow mWindow;

        public PhoneWindowMenuCallback(PhoneWindow phoneWindow) {
            this.mWindow = phoneWindow;
        }

        private void onCloseSubMenu(MenuBuilder menuBuilder) {
            Window.Callback callback = this.mWindow.getCallback();
            if (callback != null && !this.mWindow.isDestroyed()) {
                callback.onPanelClosed(6, menuBuilder.getRootMenu());
            }
        }

        @Override
        public void onCloseMenu(MenuBuilder object, boolean bl) {
            if (((MenuBuilder)object).getRootMenu() != object) {
                this.onCloseSubMenu((MenuBuilder)object);
            }
            if (bl) {
                Window.Callback callback = this.mWindow.getCallback();
                if (callback != null && !this.mWindow.isDestroyed()) {
                    callback.onPanelClosed(6, (Menu)object);
                }
                if (object == this.mWindow.mContextMenu) {
                    this.mWindow.dismissContextMenu();
                }
                if ((object = this.mSubMenuHelper) != null) {
                    ((MenuDialogHelper)object).dismiss();
                    this.mSubMenuHelper = null;
                }
            }
        }

        @Override
        public boolean onMenuItemSelected(MenuBuilder object, MenuItem menuItem) {
            object = this.mWindow.getCallback();
            boolean bl = object != null && !this.mWindow.isDestroyed() && object.onMenuItemSelected(6, menuItem);
            return bl;
        }

        @Override
        public void onMenuModeChange(MenuBuilder menuBuilder) {
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            menuBuilder.setCallback(this);
            if (this.mShowDialogForSubmenu) {
                this.mSubMenuHelper = new MenuDialogHelper(menuBuilder);
                this.mSubMenuHelper.show(null);
                return true;
            }
            return false;
        }

        public void setShowDialogForSubmenu(boolean bl) {
            this.mShowDialogForSubmenu = bl;
        }
    }

    static class RotationWatcher
    extends IRotationWatcher.Stub {
        private Handler mHandler;
        private boolean mIsWatching;
        private final Runnable mRotationChanged = new Runnable(){

            @Override
            public void run() {
                RotationWatcher.this.dispatchRotationChanged();
            }
        };
        private final ArrayList<WeakReference<PhoneWindow>> mWindows = new ArrayList();

        RotationWatcher() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void addWindow(PhoneWindow phoneWindow) {
            ArrayList<WeakReference<PhoneWindow>> arrayList = this.mWindows;
            synchronized (arrayList) {
                Object object;
                boolean bl = this.mIsWatching;
                if (!bl) {
                    try {
                        WindowManagerHolder.sWindowManager.watchRotation(this, phoneWindow.getContext().getDisplayId());
                        this.mHandler = object = new Handler();
                        this.mIsWatching = true;
                    }
                    catch (RemoteException remoteException) {
                        Log.e(PhoneWindow.TAG, "Couldn't start watching for device rotation", remoteException);
                    }
                }
                object = this.mWindows;
                WeakReference<PhoneWindow> weakReference = new WeakReference<PhoneWindow>(phoneWindow);
                ((ArrayList)object).add(weakReference);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void dispatchRotationChanged() {
            ArrayList<WeakReference<PhoneWindow>> arrayList = this.mWindows;
            synchronized (arrayList) {
                int n = 0;
                while (n < this.mWindows.size()) {
                    PhoneWindow phoneWindow = (PhoneWindow)this.mWindows.get(n).get();
                    if (phoneWindow != null) {
                        phoneWindow.onOptionsPanelRotationChanged();
                        ++n;
                        continue;
                    }
                    this.mWindows.remove(n);
                }
                return;
            }
        }

        @Override
        public void onRotationChanged(int n) throws RemoteException {
            this.mHandler.post(this.mRotationChanged);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void removeWindow(PhoneWindow phoneWindow) {
            ArrayList<WeakReference<PhoneWindow>> arrayList = this.mWindows;
            synchronized (arrayList) {
                int n = 0;
                while (n < this.mWindows.size()) {
                    PhoneWindow phoneWindow2 = (PhoneWindow)this.mWindows.get(n).get();
                    if (phoneWindow2 != null && phoneWindow2 != phoneWindow) {
                        ++n;
                        continue;
                    }
                    this.mWindows.remove(n);
                }
                return;
            }
        }

    }

    static class WindowManagerHolder {
        static final IWindowManager sWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));

        WindowManagerHolder() {
        }
    }

}

