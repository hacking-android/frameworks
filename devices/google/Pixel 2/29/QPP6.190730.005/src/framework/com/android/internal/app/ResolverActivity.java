/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.VoiceInteractor;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Insets;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.ResolverListController;
import com.android.internal.app.SimpleIconFactory;
import com.android.internal.app._$$Lambda$fPZctSH683BQhFNSBKdl6Wz99qg;
import com.android.internal.app._$$Lambda$yRChr_JGmMwuDQFg_BsC_mE_wmc;
import com.android.internal.content.PackageMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.widget.ResolverDrawerLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ResolverActivity
extends Activity {
    private static final boolean DEBUG = false;
    private static final String EXTRA_FRAGMENT_ARG_KEY = ":settings:fragment_args_key";
    private static final String EXTRA_SHOW_FRAGMENT_ARGS = ":settings:show_fragment_args";
    private static final String OPEN_LINKS_COMPONENT_KEY = "app_link_state";
    private static final String TAG = "ResolverActivity";
    @UnsupportedAppUsage
    protected ResolveListAdapter mAdapter;
    protected AbsListView mAdapterView;
    private Button mAlwaysButton;
    private int mDefaultTitleResId;
    boolean mEnableChooserDelegate = true;
    private Space mFooterSpacer = null;
    private int mIconDpi;
    private final ArrayList<Intent> mIntents = new ArrayList();
    private int mLastSelected = -1;
    protected int mLaunchedFromUid;
    private int mLayoutId;
    private Button mOnceButton;
    private final PackageMonitor mPackageMonitor = this.createPackageMonitor();
    private PickTargetOptionRequest mPickOptionRequest;
    @UnsupportedAppUsage
    protected PackageManager mPm;
    private Runnable mPostListReadyRunnable;
    private int mProfileSwitchMessageId = -1;
    protected View mProfileView;
    private String mReferrerPackage;
    private boolean mRegistered;
    protected ResolverDrawerLayout mResolverDrawerLayout;
    private boolean mResolvingHome = false;
    private boolean mRetainInOnStop;
    private boolean mSafeForwardingMode;
    private boolean mSupportsAlwaysUseOption;
    private ColorMatrixColorFilter mSuspendedMatrixColorFilter;
    protected Insets mSystemWindowInsets = null;
    private CharSequence mTitle;
    private boolean mUseLayoutForBrowsables;

    public static int getLabelRes(String string2) {
        return ActionTitle.forAction((String)string2).labelRes;
    }

    private boolean hasManagedProfile() {
        UserManager userManager = (UserManager)this.getSystemService("user");
        if (userManager == null) {
            return false;
        }
        try {
            for (UserInfo userInfo : userManager.getProfiles(this.getUserId())) {
                if (userInfo == null) continue;
            }
        }
        catch (SecurityException securityException) {
            return false;
        }
        {
            UserInfo userInfo;
            boolean bl = userInfo.isManagedProfile();
            if (!bl) continue;
            return true;
        }
        return false;
    }

    private void initSuspendedColorMatrix() {
        ColorMatrix colorMatrix = new ColorMatrix();
        Object object = colorMatrix.getArray();
        object[0] = 0.5f;
        object[6] = 0.5f;
        object[12] = 0.5f;
        object[4] = 127;
        object[9] = 127;
        object[14] = 127;
        object = new ColorMatrix();
        ((ColorMatrix)object).setSaturation(0.0f);
        ((ColorMatrix)object).preConcat(colorMatrix);
        this.mSuspendedMatrixColorFilter = new ColorMatrixColorFilter((ColorMatrix)object);
    }

    private boolean isHttpSchemeAndViewAction(Intent intent) {
        boolean bl = ("http".equals(intent.getScheme()) || "https".equals(intent.getScheme())) && "android.intent.action.VIEW".equals(intent.getAction());
        return bl;
    }

    static final boolean isSpecificUriMatch(int n) {
        boolean bl = (n &= 268369920) >= 3145728 && n <= 5242880;
        return bl;
    }

    private Intent makeMyIntent() {
        Intent intent = new Intent(this.getIntent());
        intent.setComponent(null);
        intent.setFlags(intent.getFlags() & -8388609);
        return intent;
    }

    private void resetAlwaysOrOnceButtonBar() {
        if (this.useLayoutWithDefault() && this.mAdapter.getFilteredPosition() != -1) {
            this.setAlwaysButtonEnabled(true, this.mAdapter.getFilteredPosition(), false);
            this.mOnceButton.setEnabled(true);
            return;
        }
        AbsListView absListView = this.mAdapterView;
        if (absListView != null && absListView.getCheckedItemPosition() != -1) {
            this.setAlwaysButtonEnabled(true, this.mAdapterView.getCheckedItemPosition(), true);
            this.mOnceButton.setEnabled(true);
        }
    }

    private void resetButtonBar() {
        if (!this.mSupportsAlwaysUseOption && !this.mUseLayoutForBrowsables) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup)this.findViewById(16908780);
        if (viewGroup != null) {
            int n = 0;
            viewGroup.setVisibility(0);
            Insets insets = this.mSystemWindowInsets;
            if (insets != null) {
                n = insets.bottom;
            }
            viewGroup.setPadding(viewGroup.getPaddingLeft(), viewGroup.getPaddingTop(), viewGroup.getPaddingRight(), this.getResources().getDimensionPixelSize(17105396) + n);
            this.mOnceButton = (Button)viewGroup.findViewById(16908781);
            this.mAlwaysButton = (Button)viewGroup.findViewById(16908779);
            this.resetAlwaysOrOnceButtonBar();
        } else {
            Log.e(TAG, "Layout unexpectedly does not have a button bar");
        }
    }

    static boolean resolveInfoMatch(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
        boolean bl = true;
        if (resolveInfo == null) {
            if (resolveInfo2 != null) {
                bl = false;
            }
        } else if (resolveInfo.activityInfo == null) {
            if (resolveInfo2.activityInfo != null) {
                bl = false;
            }
        } else if (!Objects.equals(resolveInfo.activityInfo.name, resolveInfo2.activityInfo.name) || !Objects.equals(resolveInfo.activityInfo.packageName, resolveInfo2.activityInfo.packageName)) {
            bl = false;
        }
        return bl;
    }

    private void safelyStartActivityInternal(TargetInfo object) {
        int n = this.mProfileSwitchMessageId;
        if (n != -1) {
            Toast.makeText((Context)this, this.getString(n), 1).show();
        }
        if (!this.mSafeForwardingMode) {
            if (object.start(this, null)) {
                this.onActivityStarted((TargetInfo)object);
            }
            return;
        }
        try {
            if (object.startAsCaller(this, null, -10000)) {
                this.onActivityStarted((TargetInfo)object);
            }
        }
        catch (RuntimeException runtimeException) {
            try {
                object = ActivityTaskManager.getService().getLaunchedFromPackage(this.getActivityToken());
            }
            catch (RemoteException remoteException) {
                object = "??";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to launch as uid ");
            stringBuilder.append(this.mLaunchedFromUid);
            stringBuilder.append(" package ");
            stringBuilder.append((String)object);
            stringBuilder.append(", while running in ");
            stringBuilder.append(ActivityThread.currentProcessName());
            Slog.wtf(TAG, stringBuilder.toString(), runtimeException);
        }
    }

    private void setAlwaysButtonEnabled(boolean bl, int n, boolean bl2) {
        boolean bl3 = false;
        if (bl) {
            ResolveInfo resolveInfo = this.mAdapter.resolveInfoForPosition(n, bl2);
            if (resolveInfo == null) {
                Log.e(TAG, "Invalid position supplied to setAlwaysButtonEnabled");
                return;
            }
            if (resolveInfo.targetUserId != -2) {
                Log.e(TAG, "Attempted to set selection to resolve info for another user");
                return;
            }
            bl3 = true;
            if (this.mUseLayoutForBrowsables && !resolveInfo.handleAllWebDataURI) {
                this.mAlwaysButton.setText(this.getResources().getString(17039454));
            } else {
                this.mAlwaysButton.setText(this.getResources().getString(17039455));
            }
        }
        this.mAlwaysButton.setEnabled(bl3);
    }

    private void setProfileSwitchMessageId(int n) {
        if (n != -2 && n != UserHandle.myUserId()) {
            UserManager userManager = (UserManager)this.getSystemService("user");
            UserInfo userInfo = userManager.getUserInfo(n);
            boolean bl = userInfo != null ? userInfo.isManagedProfile() : false;
            boolean bl2 = userManager.isManagedProfile();
            if (bl && !bl2) {
                this.mProfileSwitchMessageId = 17040045;
            } else if (!bl && bl2) {
                this.mProfileSwitchMessageId = 17040046;
            }
        }
    }

    private void showSettingsForSelected(ResolveInfo parcelable) {
        Intent intent = new Intent();
        String string2 = parcelable.activityInfo.packageName;
        parcelable = new Bundle();
        ((BaseBundle)((Object)parcelable)).putString(EXTRA_FRAGMENT_ARG_KEY, OPEN_LINKS_COMPONENT_KEY);
        ((BaseBundle)((Object)parcelable)).putString("package", string2);
        intent.setAction("com.android.settings.APP_OPEN_BY_DEFAULT_SETTINGS").setData(Uri.fromParts("package", string2, null)).addFlags(524288).putExtra(EXTRA_FRAGMENT_ARG_KEY, OPEN_LINKS_COMPONENT_KEY).putExtra(EXTRA_SHOW_FRAGMENT_ARGS, (Bundle)parcelable);
        this.startActivity(intent);
    }

    private boolean supportsManagedProfiles(ResolveInfo resolveInfo) {
        boolean bl = false;
        try {
            int n = this.getPackageManager().getApplicationInfo((String)resolveInfo.activityInfo.packageName, (int)0).targetSdkVersion;
            if (n >= 21) {
                bl = true;
            }
            return bl;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    private boolean useLayoutWithDefault() {
        boolean bl = this.mSupportsAlwaysUseOption && this.mAdapter.hasFilteredItem();
        return bl;
    }

    protected void bindProfileView() {
        if (this.mProfileView == null) {
            return;
        }
        DisplayResolveInfo displayResolveInfo = this.mAdapter.getOtherProfile();
        if (displayResolveInfo != null) {
            Object t;
            this.mProfileView.setVisibility(0);
            Object t2 = t = this.mProfileView.findViewById(16909258);
            if (!(t instanceof TextView)) {
                t2 = this.mProfileView.findViewById(16908308);
            }
            ((TextView)t2).setText(displayResolveInfo.getDisplayLabel());
        } else {
            this.mProfileView.setVisibility(8);
        }
    }

    public boolean configureContentView(List<Intent> object, Intent[] arrintent, List<ResolveInfo> list) {
        int n = this.mLaunchedFromUid;
        boolean bl = this.mSupportsAlwaysUseOption && !this.isVoiceInteraction();
        this.mAdapter = this.createAdapter(this, (List<Intent>)object, arrintent, list, n, bl);
        bl = this.mAdapter.rebuildList();
        this.mLayoutId = this.useLayoutWithDefault() ? 17367262 : this.getLayoutResource();
        this.setContentView(this.mLayoutId);
        n = this.mAdapter.getUnfilteredCount();
        if (bl && n == 1 && this.mAdapter.getOtherProfile() == null && this.shouldAutoLaunchSingleChoice((TargetInfo)(object = this.mAdapter.targetInfoForPosition(0, false)))) {
            this.safelyStartActivity((TargetInfo)object);
            this.mPackageMonitor.unregister();
            this.mRegistered = false;
            this.finish();
            return true;
        }
        this.mAdapterView = (AbsListView)this.findViewById(16909285);
        if (n == 0 && this.mAdapter.mPlaceholderCount == 0) {
            ((TextView)this.findViewById(16908292)).setVisibility(0);
            this.mAdapterView.setVisibility(8);
        } else {
            this.mAdapterView.setVisibility(0);
            this.onPrepareAdapterView(this.mAdapterView, this.mAdapter);
        }
        return false;
    }

    public ResolveListAdapter createAdapter(Context context, List<Intent> list, Intent[] arrintent, List<ResolveInfo> list2, int n, boolean bl) {
        return new ResolveListAdapter(context, list, arrintent, list2, n, bl, this.createListController());
    }

    @VisibleForTesting
    protected ResolverListController createListController() {
        return new ResolverListController(this, this.mPm, this.getTargetIntent(), this.getReferrerPackageName(), this.mLaunchedFromUid);
    }

    protected PackageMonitor createPackageMonitor() {
        return new PackageMonitor(){

            @Override
            public boolean onPackageChanged(String string2, int n, String[] arrstring) {
                return true;
            }

            @Override
            public void onSomePackagesChanged() {
                ResolverActivity.this.mAdapter.handlePackagesChanged();
                ResolverActivity.this.bindProfileView();
            }
        };
    }

    void dismiss() {
        if (!this.isFinishing()) {
            this.finish();
        }
    }

    List<DisplayResolveInfo> getDisplayList() {
        return this.mAdapter.mDisplayList;
    }

    public int getLayoutResource() {
        return 17367261;
    }

    protected String getReferrerPackageName() {
        Uri uri = this.getReferrer();
        if (uri != null && "android-app".equals(uri.getScheme())) {
            return uri.getHost();
        }
        return null;
    }

    public Intent getReplacementIntent(ActivityInfo activityInfo, Intent intent) {
        return intent;
    }

    public Intent getTargetIntent() {
        Intent intent = this.mIntents.isEmpty() ? null : this.mIntents.get(0);
        return intent;
    }

    protected CharSequence getTitleForAction(Intent object, int n) {
        ActionTitle actionTitle = this.mResolvingHome ? ActionTitle.HOME : ActionTitle.forAction(((Intent)object).getAction());
        boolean bl = this.mAdapter.getFilteredPosition() >= 0;
        if (actionTitle == ActionTitle.DEFAULT && n != 0) {
            return this.getString(n);
        }
        if (this.isHttpSchemeAndViewAction((Intent)object)) {
            object = bl && !this.mUseLayoutForBrowsables ? this.getString(17041256, this.mAdapter.getFilteredItem().getDisplayLabel()) : (bl && this.mUseLayoutForBrowsables ? this.getString(17041254, ((Intent)object).getData().getHost(), this.mAdapter.getFilteredItem().getDisplayLabel()) : (this.mAdapter.areAllTargetsBrowsers() ? this.getString(17041255) : this.getString(17041253, ((Intent)object).getData().getHost())));
            return object;
        }
        object = bl ? this.getString(actionTitle.namedTitleRes, this.mAdapter.getFilteredItem().getDisplayLabel()) : this.getString(actionTitle.titleRes);
        return object;
    }

    Drawable loadIconForResolveInfo(ResolveInfo resolveInfo) {
        return this.makePresentationGetter(resolveInfo).getIcon(Process.myUserHandle());
    }

    protected ActivityInfoPresentationGetter makePresentationGetter(ActivityInfo activityInfo) {
        return new ActivityInfoPresentationGetter(this, this.mIconDpi, activityInfo);
    }

    ResolveInfoPresentationGetter makePresentationGetter(ResolveInfo resolveInfo) {
        return new ResolveInfoPresentationGetter(this, this.mIconDpi, resolveInfo);
    }

    public void onActivityStarted(TargetInfo targetInfo) {
    }

    protected WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        this.mSystemWindowInsets = windowInsets.getSystemWindowInsets();
        this.mResolverDrawerLayout.setPadding(this.mSystemWindowInsets.left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        view = this.findViewById(16908292);
        if (view != null) {
            view.setPadding(0, 0, 0, this.mSystemWindowInsets.bottom + this.getResources().getDimensionPixelSize(17105034) * 2);
        }
        if ((view = this.mFooterSpacer) == null) {
            this.mFooterSpacer = new Space(this.getApplicationContext());
        } else {
            ((ListView)this.mAdapterView).removeFooterView(view);
        }
        this.mFooterSpacer.setLayoutParams(new AbsListView.LayoutParams(-1, this.mSystemWindowInsets.bottom));
        ((ListView)this.mAdapterView).addFooterView(this.mFooterSpacer);
        this.resetButtonBar();
        return windowInsets.consumeSystemWindowInsets();
    }

    public void onButtonClick(View object) {
        int n = ((View)object).getId();
        int n2 = this.mAdapter.hasFilteredItem() ? this.mAdapter.getFilteredPosition() : this.mAdapterView.getCheckedItemPosition();
        boolean bl = this.mAdapter.hasFilteredItem();
        boolean bl2 = true;
        object = this.mAdapter.resolveInfoForPosition(n2, bl ^= true);
        if (this.mUseLayoutForBrowsables && !((ResolveInfo)object).handleAllWebDataURI && n == 16908779) {
            this.showSettingsForSelected((ResolveInfo)object);
        } else {
            if (n != 16908779) {
                bl2 = false;
            }
            this.startSelected(n2, bl2, bl);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration parcelable) {
        super.onConfigurationChanged((Configuration)parcelable);
        this.mAdapter.handlePackagesChanged();
        parcelable = this.mSystemWindowInsets;
        if (parcelable != null) {
            this.mResolverDrawerLayout.setPadding(((Insets)parcelable).left, this.mSystemWindowInsets.top, this.mSystemWindowInsets.right, 0);
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        Intent intent = this.makeMyIntent();
        Set<String> set = intent.getCategories();
        if ("android.intent.action.MAIN".equals(intent.getAction()) && set != null && set.size() == 1 && set.contains("android.intent.category.HOME")) {
            this.mResolvingHome = true;
        }
        this.setSafeForwardingMode(true);
        this.onCreate(bundle, intent, null, 0, null, null, true);
    }

    protected void onCreate(Bundle object, Intent intent, CharSequence charSequence, int n, Intent[] arrintent, List<ResolveInfo> list, boolean bl) {
        this.setTheme(16974832);
        super.onCreate((Bundle)object);
        this.setProfileSwitchMessageId(intent.getContentUserHint());
        try {
            this.mLaunchedFromUid = ActivityTaskManager.getService().getLaunchedFromUid(this.getActivityToken());
        }
        catch (RemoteException remoteException) {
            this.mLaunchedFromUid = -1;
        }
        int n2 = this.mLaunchedFromUid;
        if (n2 >= 0 && !UserHandle.isIsolated(n2)) {
            this.mPm = this.getPackageManager();
            this.mPackageMonitor.register(this, this.getMainLooper(), false);
            this.mRegistered = true;
            this.mReferrerPackage = this.getReferrerPackageName();
            this.mIconDpi = ((ActivityManager)this.getSystemService("activity")).getLauncherLargeIconDensity();
            this.mIntents.add(0, new Intent(intent));
            this.mTitle = charSequence;
            this.mDefaultTitleResId = n;
            boolean bl2 = this.getTargetIntent() == null ? false : this.isHttpSchemeAndViewAction(this.getTargetIntent());
            this.mUseLayoutForBrowsables = bl2;
            this.mSupportsAlwaysUseOption = bl;
            if (this.configureContentView(this.mIntents, arrintent, list)) {
                return;
            }
            object = (ResolverDrawerLayout)this.findViewById(16908829);
            if (object != null) {
                ((ResolverDrawerLayout)object).setOnDismissedListener(new ResolverDrawerLayout.OnDismissedListener(){

                    @Override
                    public void onDismissed() {
                        ResolverActivity.this.finish();
                    }
                });
                if (this.isVoiceInteraction()) {
                    ((ResolverDrawerLayout)object).setCollapsed(false);
                }
                ((View)object).setSystemUiVisibility(768);
                ((View)object).setOnApplyWindowInsetsListener(new _$$Lambda$yRChr_JGmMwuDQFg_BsC_mE_wmc(this));
                this.mResolverDrawerLayout = object;
            }
            this.mProfileView = this.findViewById(16909258);
            object = this.mProfileView;
            if (object != null) {
                ((View)object).setOnClickListener(new _$$Lambda$fPZctSH683BQhFNSBKdl6Wz99qg(this));
                this.bindProfileView();
            }
            this.initSuspendedColorMatrix();
            if (this.isVoiceInteraction()) {
                this.onSetupVoiceInteraction();
            }
            object = intent.getCategories();
            n = this.mAdapter.hasFilteredItem() ? 451 : 453;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(intent.getAction());
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(intent.getType());
            ((StringBuilder)charSequence).append(":");
            object = object != null ? Arrays.toString(object.toArray()) : "";
            ((StringBuilder)charSequence).append((String)object);
            MetricsLogger.action((Context)this, n, ((StringBuilder)charSequence).toString());
            return;
        }
        this.finish();
    }

    @UnsupportedAppUsage
    protected void onCreate(Bundle bundle, Intent intent, CharSequence charSequence, Intent[] arrintent, List<ResolveInfo> list, boolean bl) {
        this.onCreate(bundle, intent, charSequence, 0, arrintent, list, bl);
    }

    @Override
    protected void onDestroy() {
        Object object;
        super.onDestroy();
        if (!this.isChangingConfigurations() && (object = this.mPickOptionRequest) != null) {
            ((VoiceInteractor.Request)object).cancel();
        }
        if (this.mPostListReadyRunnable != null) {
            this.getMainThreadHandler().removeCallbacks(this.mPostListReadyRunnable);
            this.mPostListReadyRunnable = null;
        }
        if ((object = this.mAdapter) != null && ((ResolveListAdapter)object).mResolverListController != null) {
            this.mAdapter.mResolverListController.destroy();
        }
    }

    public void onPrepareAdapterView(AbsListView absListView, ResolveListAdapter object) {
        boolean bl = ((ResolveListAdapter)object).hasFilteredItem();
        object = absListView instanceof ListView ? (ListView)absListView : null;
        absListView.setAdapter(this.mAdapter);
        ItemClickListener itemClickListener = new ItemClickListener();
        absListView.setOnItemClickListener(itemClickListener);
        absListView.setOnItemLongClickListener(itemClickListener);
        if (this.mSupportsAlwaysUseOption || this.mUseLayoutForBrowsables) {
            ((AbsListView)object).setChoiceMode(1);
        }
        if (bl && object != null && ((ListView)object).getHeaderViewsCount() == 0) {
            ((ListView)object).addHeaderView(LayoutInflater.from(this).inflate(17367260, (ViewGroup)object, false));
        }
    }

    protected void onProfileClick(View object) {
        object = this.mAdapter.getOtherProfile();
        if (object == null) {
            return;
        }
        this.mProfileSwitchMessageId = -1;
        this.onTargetSelected((TargetInfo)object, false);
        this.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!this.mRegistered) {
            this.mPackageMonitor.register(this, this.getMainLooper(), false);
            this.mRegistered = true;
        }
        this.mAdapter.handlePackagesChanged();
        this.bindProfileView();
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.resetButtonBar();
    }

    public void onSetupVoiceInteraction() {
        this.sendVoiceChoicesIfNeeded();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (this.mRegistered) {
            this.mPackageMonitor.unregister();
            this.mRegistered = false;
        }
        if (!((this.getIntent().getFlags() & 268435456) == 0 || this.isVoiceInteraction() || this.mResolvingHome || this.mRetainInOnStop || this.isChangingConfigurations())) {
            this.finish();
        }
    }

    protected boolean onTargetSelected(TargetInfo targetInfo, boolean bl) {
        ResolveInfo resolveInfo = targetInfo.getResolveInfo();
        Intent intent = targetInfo.getResolvedIntent();
        if (intent != null && (this.mSupportsAlwaysUseOption || this.mAdapter.hasFilteredItem())) {
            if (this.mAdapter.mUnfilteredResolveList != null) {
                Object object;
                Object object2;
                Set<String> set;
                int n;
                Object object3 = new IntentFilter();
                Object object4 = intent.getSelector() != null ? intent.getSelector() : intent;
                String string2 = ((Intent)object4).getAction();
                if (string2 != null) {
                    ((IntentFilter)object3).addAction(string2);
                }
                if ((set = ((Intent)object4).getCategories()) != null) {
                    object2 = set.iterator();
                    while (object2.hasNext()) {
                        ((IntentFilter)object3).addCategory(object2.next());
                    }
                }
                ((IntentFilter)object3).addCategory("android.intent.category.DEFAULT");
                int n2 = 268369920 & resolveInfo.match;
                Uri uri = ((Intent)object4).getData();
                Object object5 = TAG;
                object2 = object3;
                if (n2 == 6291456) {
                    object = ((Intent)object4).resolveType(this);
                    object2 = object3;
                    if (object != null) {
                        try {
                            ((IntentFilter)object3).addDataType((String)object);
                            object2 = object3;
                        }
                        catch (IntentFilter.MalformedMimeTypeException malformedMimeTypeException) {
                            Log.w(TAG, malformedMimeTypeException);
                            object2 = null;
                        }
                    }
                }
                if (uri != null && uri.getScheme() != null && (n2 != 6291456 || !"file".equals(uri.getScheme()) && !"content".equals(uri.getScheme()))) {
                    ((IntentFilter)object2).addDataScheme(uri.getScheme());
                    Object object6 = resolveInfo.filter.schemeSpecificPartsIterator();
                    if (object6 != null) {
                        object3 = uri.getSchemeSpecificPart();
                        while (object3 != null && object6.hasNext()) {
                            object = object6.next();
                            if (!((PatternMatcher)object).match((String)object3)) continue;
                            ((IntentFilter)object2).addDataSchemeSpecificPart(((PatternMatcher)object).getPath(), ((PatternMatcher)object).getType());
                            break;
                        }
                    }
                    if ((object = resolveInfo.filter.authoritiesIterator()) != null) {
                        while (object.hasNext()) {
                            object3 = (IntentFilter.AuthorityEntry)((Object)object.next());
                            if (((IntentFilter.AuthorityEntry)object3).match(uri) < 0) continue;
                            n = ((IntentFilter.AuthorityEntry)object3).getPort();
                            object = ((IntentFilter.AuthorityEntry)object3).getHost();
                            object3 = n >= 0 ? Integer.toString(n) : null;
                            ((IntentFilter)object2).addDataAuthority((String)object, (String)object3);
                            break;
                        }
                    }
                    if ((object = resolveInfo.filter.pathsIterator()) != null) {
                        object3 = uri.getPath();
                        while (object3 != null && object.hasNext()) {
                            object6 = (PatternMatcher)object.next();
                            if (!((PatternMatcher)object6).match((String)object3)) continue;
                            ((IntentFilter)object2).addDataPath(((PatternMatcher)object6).getPath(), ((PatternMatcher)object6).getType());
                            break;
                        }
                    }
                }
                if (object2 != null) {
                    int n3;
                    int n4 = this.mAdapter.mUnfilteredResolveList.size();
                    int n5 = this.mAdapter.mOtherProfile != null ? 1 : 0;
                    object3 = n5 == 0 ? new ComponentName[n4] : new ComponentName[n4 + 1];
                    n = 0;
                    for (n3 = 0; n3 < n4; ++n3) {
                        object = this.mAdapter.mUnfilteredResolveList.get(n3).getResolveInfoAt(0);
                        object3[n3] = new ComponentName(object.activityInfo.packageName, object.activityInfo.name);
                        int n6 = n;
                        if (((ResolveInfo)object).match > n) {
                            n6 = ((ResolveInfo)object).match;
                        }
                        n = n6;
                    }
                    if (n5 != 0) {
                        object3[n4] = this.mAdapter.mOtherProfile.getResolvedComponentName();
                        n5 = ResolveListAdapter.access$100((ResolveListAdapter)this.mAdapter).getResolveInfo().match;
                        if (n5 > n) {
                            n = n5;
                        }
                    }
                    if (bl) {
                        n2 = this.getUserId();
                        object5 = this.getPackageManager();
                        ((PackageManager)object5).addPreferredActivity((IntentFilter)object2, n, (ComponentName[])object3, intent.getComponent());
                        if (resolveInfo.handleAllWebDataURI) {
                            if (TextUtils.isEmpty(((PackageManager)object5).getDefaultBrowserPackageNameAsUser(n2))) {
                                ((PackageManager)object5).setDefaultBrowserPackageNameAsUser(resolveInfo.activityInfo.packageName, n2);
                            }
                        } else {
                            object2 = intent.getComponent().getPackageName();
                            object4 = uri != null ? uri.getScheme() : null;
                            n = object4 != null && (((String)object4).equals("http") || ((String)object4).equals("https")) ? 1 : 0;
                            n5 = string2 != null && string2.equals("android.intent.action.VIEW") ? 1 : 0;
                            n3 = set != null && set.contains("android.intent.category.BROWSABLE") ? 1 : 0;
                            if (n != 0 && n5 != 0 && n3 != 0) {
                                ((PackageManager)object5).updateIntentVerificationStatusAsUser((String)object2, 2, n2);
                            }
                        }
                    } else {
                        try {
                            this.mAdapter.mResolverListController.setLastChosen(intent, (IntentFilter)object2, n);
                        }
                        catch (RemoteException remoteException) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Error calling setLastChosenActivity\n");
                            ((StringBuilder)object2).append(remoteException);
                            Log.d((String)object5, ((StringBuilder)object2).toString());
                        }
                    }
                }
            }
        }
        this.safelyStartActivity(targetInfo);
        return !targetInfo.isSuspended();
    }

    VoiceInteractor.PickOptionRequest.Option optionForChooserTarget(TargetInfo targetInfo, int n) {
        return new VoiceInteractor.PickOptionRequest.Option(targetInfo.getDisplayLabel(), n);
    }

    public void safelyStartActivity(TargetInfo targetInfo) {
        StrictMode.disableDeathOnFileUriExposure();
        try {
            this.safelyStartActivityInternal(targetInfo);
            return;
        }
        finally {
            StrictMode.enableDeathOnFileUriExposure();
        }
    }

    public void sendVoiceChoicesIfNeeded() {
        if (!this.isVoiceInteraction()) {
            return;
        }
        VoiceInteractor.PickOptionRequest.Option[] arroption = new VoiceInteractor.PickOptionRequest.Option[this.mAdapter.getCount()];
        int n = arroption.length;
        for (int i = 0; i < n; ++i) {
            arroption[i] = this.optionForChooserTarget(this.mAdapter.getItem(i), i);
        }
        this.mPickOptionRequest = new PickTargetOptionRequest(new VoiceInteractor.Prompt(this.getTitle()), arroption, null);
        this.getVoiceInteractor().submitRequest(this.mPickOptionRequest);
    }

    protected final void setAdditionalTargets(Intent[] arrintent) {
        if (arrintent != null) {
            for (Intent intent : arrintent) {
                this.mIntents.add(intent);
            }
        }
    }

    public void setHeader() {
        View view;
        Object object;
        if (this.mAdapter.getCount() == 0 && this.mAdapter.mPlaceholderCount == 0 && (object = (TextView)this.findViewById(16908310)) != null) {
            ((View)object).setVisibility(8);
        }
        if ((object = this.mTitle) == null) {
            object = this.getTitleForAction(this.getTargetIntent(), this.mDefaultTitleResId);
        }
        if (!TextUtils.isEmpty((CharSequence)object)) {
            view = (TextView)this.findViewById(16908310);
            if (view != null) {
                ((TextView)view).setText((CharSequence)object);
            }
            this.setTitle((CharSequence)object);
        }
        view = (ImageView)this.findViewById(16908294);
        object = this.mAdapter.getFilteredItem();
        if (view != null && object != null) {
            new LoadIconTask((DisplayResolveInfo)object, (ImageView)view).execute(new Void[0]);
        }
    }

    protected void setRetainInOnStop(boolean bl) {
        this.mRetainInOnStop = bl;
    }

    public void setSafeForwardingMode(boolean bl) {
        this.mSafeForwardingMode = bl;
    }

    public boolean shouldAutoLaunchSingleChoice(TargetInfo targetInfo) {
        return targetInfo.isSuspended() ^ true;
    }

    public boolean shouldGetActivityMetadata() {
        return false;
    }

    public void showTargetDetails(ResolveInfo resolveInfo) {
        this.startActivity(new Intent().setAction("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", resolveInfo.activityInfo.packageName, null)).addFlags(524288));
    }

    boolean startAsCallerImpl(Intent intent, Bundle bundle, boolean bl, int n) {
        try {
            IBinder iBinder = ActivityTaskManager.getService().requestStartActivityPermissionToken(this.getActivityToken());
            Intent intent2 = new Intent();
            ComponentName componentName = ComponentName.unflattenFromString(Resources.getSystem().getString(17039687));
            intent2.setClassName(componentName.getPackageName(), componentName.getClassName());
            intent2.putExtra("android.app.extra.PERMISSION_TOKEN", iBinder);
            intent2.putExtra("android.intent.extra.INTENT", intent);
            intent2.putExtra("android.app.extra.OPTIONS", bundle);
            intent2.putExtra("android.app.extra.EXTRA_IGNORE_TARGET_SECURITY", bl);
            intent2.putExtra("android.intent.extra.USER_ID", n);
            intent2.addFlags(50331648);
            this.startActivity(intent2);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, remoteException.toString());
        }
        return true;
    }

    public void startSelected(int n, boolean bl, boolean bl2) {
        if (this.isFinishing()) {
            return;
        }
        Object object = this.mAdapter.resolveInfoForPosition(n, bl2);
        if (this.mResolvingHome && this.hasManagedProfile() && !this.supportsManagedProfiles((ResolveInfo)object)) {
            Toast.makeText((Context)this, String.format(this.getResources().getString(17039457), ((ResolveInfo)object).activityInfo.loadLabel(this.getPackageManager()).toString()), 1).show();
            return;
        }
        object = this.mAdapter.targetInfoForPosition(n, bl2);
        if (object == null) {
            return;
        }
        if (this.onTargetSelected((TargetInfo)object, bl)) {
            if (bl && this.mSupportsAlwaysUseOption) {
                MetricsLogger.action(this, 455);
            } else if (this.mSupportsAlwaysUseOption) {
                MetricsLogger.action(this, 456);
            } else {
                MetricsLogger.action(this, 457);
            }
            n = this.mAdapter.hasFilteredItem() ? 452 : 454;
            MetricsLogger.action(this, n);
            this.finish();
        }
    }

    private static enum ActionTitle {
        VIEW("android.intent.action.VIEW", 17041263, 17041265, 17041264),
        EDIT("android.intent.action.EDIT", 17041243, 17041245, 17041244),
        SEND("android.intent.action.SEND", 17041257, 17041259, 17041258),
        SENDTO("android.intent.action.SENDTO", 17041260, 17041262, 17041261),
        SEND_MULTIPLE("android.intent.action.SEND_MULTIPLE", 17041257, 17041259, 17041258),
        CAPTURE_IMAGE("android.media.action.IMAGE_CAPTURE", 17041250, 17041252, 17041251),
        DEFAULT(null, 17041240, 17041242, 17041241),
        HOME("android.intent.action.MAIN", 17041247, 17041249, 17041248);
        
        public static final int BROWSABLE_APP_TITLE_RES = 17041256;
        public static final int BROWSABLE_HOST_APP_TITLE_RES = 17041254;
        public static final int BROWSABLE_HOST_TITLE_RES = 17041253;
        public static final int BROWSABLE_TITLE_RES = 17041255;
        public final String action;
        public final int labelRes;
        public final int namedTitleRes;
        public final int titleRes;

        private ActionTitle(String string3, int n2, int n3, int n4) {
            this.action = string3;
            this.titleRes = n2;
            this.namedTitleRes = n3;
            this.labelRes = n4;
        }

        public static ActionTitle forAction(String string2) {
            for (ActionTitle actionTitle : ActionTitle.values()) {
                if (actionTitle == HOME || string2 == null || !string2.equals(actionTitle.action)) continue;
                return actionTitle;
            }
            return DEFAULT;
        }
    }

    @VisibleForTesting
    public static class ActivityInfoPresentationGetter
    extends TargetPresentationGetter {
        private final ActivityInfo mActivityInfo;

        public ActivityInfoPresentationGetter(Context context, int n, ActivityInfo activityInfo) {
            super(context, n, activityInfo.applicationInfo);
            this.mActivityInfo = activityInfo;
        }

        @Override
        String getAppSubLabelInternal() {
            return (String)this.mActivityInfo.loadLabel(this.mPm);
        }

        @Override
        Drawable getIconSubstituteInternal() {
            Drawable drawable2 = null;
            Drawable drawable3 = null;
            try {
                if (this.mActivityInfo.icon != 0) {
                    drawable3 = this.loadIconFromResource(this.mPm.getResourcesForApplication(this.mActivityInfo.applicationInfo), this.mActivityInfo.icon);
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.e(ResolverActivity.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", nameNotFoundException);
                drawable3 = drawable2;
            }
            return drawable3;
        }
    }

    public final class DisplayResolveInfo
    implements TargetInfo {
        private Drawable mBadge;
        private Drawable mDisplayIcon;
        private final CharSequence mDisplayLabel;
        private final CharSequence mExtendedInfo;
        private boolean mIsSuspended;
        private final ResolveInfo mResolveInfo;
        private final Intent mResolvedIntent;
        private final List<Intent> mSourceIntents;

        public DisplayResolveInfo(Intent parcelable, ResolveInfo resolveInfo, CharSequence charSequence, CharSequence charSequence2, Intent intent) {
            this.mSourceIntents = new ArrayList<Intent>();
            this.mSourceIntents.add((Intent)parcelable);
            this.mResolveInfo = resolveInfo;
            this.mDisplayLabel = charSequence;
            this.mExtendedInfo = charSequence2;
            if (intent == null) {
                intent = ((ResolverActivity)ResolverActivity.this).getReplacementIntent(resolveInfo.activityInfo, ((ResolverActivity)ResolverActivity.this).getTargetIntent());
            }
            ResolverActivity.this = new Intent(intent);
            ((Intent)ResolverActivity.this).addFlags(50331648);
            parcelable = this.mResolveInfo.activityInfo;
            ((Intent)ResolverActivity.this).setComponent(new ComponentName(parcelable.applicationInfo.packageName, ((ActivityInfo)parcelable).name));
            boolean bl = (parcelable.applicationInfo.flags & 1073741824) != 0;
            this.mIsSuspended = bl;
            this.mResolvedIntent = ResolverActivity.this;
        }

        private DisplayResolveInfo(DisplayResolveInfo displayResolveInfo, Intent intent, int n) {
            this.mSourceIntents = new ArrayList<Intent>();
            this.mSourceIntents.addAll(displayResolveInfo.getAllSourceIntents());
            this.mResolveInfo = displayResolveInfo.mResolveInfo;
            this.mDisplayLabel = displayResolveInfo.mDisplayLabel;
            this.mDisplayIcon = displayResolveInfo.mDisplayIcon;
            this.mExtendedInfo = displayResolveInfo.mExtendedInfo;
            this.mResolvedIntent = new Intent(displayResolveInfo.mResolvedIntent);
            this.mResolvedIntent.fillIn(intent, n);
        }

        public void addAlternateSourceIntent(Intent intent) {
            this.mSourceIntents.add(intent);
        }

        @Override
        public TargetInfo cloneFilledIn(Intent intent, int n) {
            return new DisplayResolveInfo(this, intent, n);
        }

        @Override
        public List<Intent> getAllSourceIntents() {
            return this.mSourceIntents;
        }

        @Override
        public Drawable getDisplayIcon() {
            return this.mDisplayIcon;
        }

        @Override
        public CharSequence getDisplayLabel() {
            return this.mDisplayLabel;
        }

        @Override
        public CharSequence getExtendedInfo() {
            return this.mExtendedInfo;
        }

        @Override
        public ResolveInfo getResolveInfo() {
            return this.mResolveInfo;
        }

        @Override
        public ComponentName getResolvedComponentName() {
            return new ComponentName(this.mResolveInfo.activityInfo.packageName, this.mResolveInfo.activityInfo.name);
        }

        @Override
        public Intent getResolvedIntent() {
            return this.mResolvedIntent;
        }

        public boolean hasDisplayIcon() {
            boolean bl = this.mDisplayIcon != null;
            return bl;
        }

        @Override
        public boolean isSuspended() {
            return this.mIsSuspended;
        }

        public void setDisplayIcon(Drawable drawable2) {
            this.mDisplayIcon = drawable2;
        }

        @Override
        public boolean start(Activity activity, Bundle bundle) {
            activity.startActivity(this.mResolvedIntent, bundle);
            return true;
        }

        @Override
        public boolean startAsCaller(ResolverActivity resolverActivity, Bundle bundle, int n) {
            if (ResolverActivity.this.mEnableChooserDelegate) {
                return resolverActivity.startAsCallerImpl(this.mResolvedIntent, bundle, false, n);
            }
            resolverActivity.startActivityAsCaller(this.mResolvedIntent, bundle, null, false, n);
            return true;
        }

        @Override
        public boolean startAsUser(Activity activity, Bundle bundle, UserHandle userHandle) {
            activity.startActivityAsUser(this.mResolvedIntent, bundle, userHandle);
            return false;
        }
    }

    class ItemClickListener
    implements AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener {
        ItemClickListener() {
        }

        @Override
        public void onItemClick(AdapterView<?> listView, View view, int n, long l) {
            listView = listView instanceof ListView ? (ListView)listView : null;
            int n2 = n;
            if (listView != null) {
                n2 = n - listView.getHeaderViewsCount();
            }
            if (n2 < 0) {
                return;
            }
            if (ResolverActivity.this.mAdapter.resolveInfoForPosition(n2, true) == null) {
                return;
            }
            n = ResolverActivity.this.mAdapterView.getCheckedItemPosition();
            boolean bl = n != -1;
            if (!(ResolverActivity.this.useLayoutWithDefault() || bl && ResolverActivity.this.mLastSelected == n || ResolverActivity.this.mAlwaysButton == null)) {
                ResolverActivity.this.setAlwaysButtonEnabled(bl, n, true);
                ResolverActivity.this.mOnceButton.setEnabled(bl);
                if (bl) {
                    ResolverActivity.this.mAdapterView.smoothScrollToPosition(n);
                }
                ResolverActivity.this.mLastSelected = n;
            } else {
                ResolverActivity.this.startSelected(n2, false, true);
            }
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> object, View view, int n, long l) {
            object = object instanceof ListView ? (ListView)object : null;
            int n2 = n;
            if (object != null) {
                n2 = n - ((ListView)object).getHeaderViewsCount();
            }
            if (n2 < 0) {
                return false;
            }
            object = ResolverActivity.this.mAdapter.resolveInfoForPosition(n2, true);
            ResolverActivity.this.showTargetDetails((ResolveInfo)object);
            return true;
        }
    }

    class LoadIconTask
    extends AsyncTask<Void, Void, Drawable> {
        protected final DisplayResolveInfo mDisplayResolveInfo;
        private final ResolveInfo mResolveInfo;
        private final ImageView mTargetView;

        LoadIconTask(DisplayResolveInfo displayResolveInfo, ImageView imageView) {
            this.mDisplayResolveInfo = displayResolveInfo;
            this.mResolveInfo = displayResolveInfo.getResolveInfo();
            this.mTargetView = imageView;
        }

        protected Drawable doInBackground(Void ... arrvoid) {
            return ResolverActivity.this.loadIconForResolveInfo(this.mResolveInfo);
        }

        @Override
        protected void onPostExecute(Drawable drawable2) {
            DisplayResolveInfo displayResolveInfo;
            DisplayResolveInfo displayResolveInfo2 = ResolverActivity.this.mAdapter.getOtherProfile();
            if (displayResolveInfo2 == (displayResolveInfo = this.mDisplayResolveInfo)) {
                ResolverActivity.this.bindProfileView();
            } else {
                displayResolveInfo.setDisplayIcon(drawable2);
                this.mTargetView.setImageDrawable(drawable2);
            }
        }
    }

    static class PickTargetOptionRequest
    extends VoiceInteractor.PickOptionRequest {
        public PickTargetOptionRequest(VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] arroption, Bundle bundle) {
            super(prompt, arroption, bundle);
        }

        @Override
        public void onCancel() {
            super.onCancel();
            ResolverActivity resolverActivity = (ResolverActivity)this.getActivity();
            if (resolverActivity != null) {
                resolverActivity.mPickOptionRequest = null;
                resolverActivity.finish();
            }
        }

        @Override
        public void onPickOptionResult(boolean bl, VoiceInteractor.PickOptionRequest.Option[] arroption, Bundle object) {
            super.onPickOptionResult(bl, arroption, (Bundle)object);
            if (arroption.length != 1) {
                return;
            }
            object = (ResolverActivity)this.getActivity();
            if (object != null && ((ResolverActivity)object).onTargetSelected(((ResolverActivity)object).mAdapter.getItem(arroption[0].getIndex()), false)) {
                ((ResolverActivity)object).mPickOptionRequest = null;
                ((Activity)object).finish();
            }
        }
    }

    @VisibleForTesting
    public static class ResolveInfoPresentationGetter
    extends ActivityInfoPresentationGetter {
        private final ResolveInfo mRi;

        public ResolveInfoPresentationGetter(Context context, int n, ResolveInfo resolveInfo) {
            super(context, n, resolveInfo.activityInfo);
            this.mRi = resolveInfo;
        }

        @Override
        String getAppSubLabelInternal() {
            return (String)this.mRi.loadLabel(this.mPm);
        }

        @Override
        Drawable getIconSubstituteInternal() {
            Drawable drawable2;
            Drawable drawable3;
            block5 : {
                Drawable drawable4;
                drawable3 = null;
                drawable2 = drawable4 = null;
                if (this.mRi.resolvePackageName == null) break block5;
                drawable2 = drawable4;
                try {
                    if (this.mRi.icon != 0) {
                        drawable2 = this.loadIconFromResource(this.mPm.getResourcesForApplication(this.mRi.resolvePackageName), this.mRi.icon);
                    }
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    Log.e(ResolverActivity.TAG, "SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON permission granted but couldn't find resources for package", nameNotFoundException);
                    drawable2 = drawable3;
                }
            }
            drawable3 = drawable2;
            if (drawable2 == null) {
                drawable3 = super.getIconSubstituteInternal();
            }
            return drawable3;
        }
    }

    public class ResolveListAdapter
    extends BaseAdapter {
        private boolean mAllTargetsAreBrowsers = false;
        private final List<ResolveInfo> mBaseResolveList;
        List<DisplayResolveInfo> mDisplayList;
        private boolean mFilterLastUsed;
        protected final LayoutInflater mInflater;
        private final Intent[] mInitialIntents;
        private final List<Intent> mIntents;
        protected ResolveInfo mLastChosen;
        private int mLastChosenPosition = -1;
        private DisplayResolveInfo mOtherProfile;
        private int mPlaceholderCount;
        private ResolverListController mResolverListController;
        List<ResolvedComponentInfo> mUnfilteredResolveList;

        public ResolveListAdapter(Context context, List<Intent> list, Intent[] arrintent, List<ResolveInfo> list2, int n, boolean bl, ResolverListController resolverListController) {
            this.mIntents = list;
            this.mInitialIntents = arrintent;
            this.mBaseResolveList = list2;
            ResolverActivity.this.mLaunchedFromUid = n;
            this.mInflater = LayoutInflater.from(context);
            this.mDisplayList = new ArrayList<DisplayResolveInfo>();
            this.mFilterLastUsed = bl;
            this.mResolverListController = resolverListController;
        }

        private void addResolveInfo(DisplayResolveInfo displayResolveInfo) {
            if (displayResolveInfo != null && displayResolveInfo.mResolveInfo != null && DisplayResolveInfo.access$800((DisplayResolveInfo)displayResolveInfo).targetUserId == -2) {
                for (DisplayResolveInfo displayResolveInfo2 : this.mDisplayList) {
                    if (!ResolverActivity.resolveInfoMatch(displayResolveInfo.mResolveInfo, displayResolveInfo2.mResolveInfo)) continue;
                    return;
                }
                this.mDisplayList.add(displayResolveInfo);
            }
        }

        private void addResolveInfoWithAlternates(ResolvedComponentInfo resolvedComponentInfo, CharSequence object, CharSequence charSequence) {
            int n = resolvedComponentInfo.getCount();
            Intent intent = resolvedComponentInfo.getIntentAt(0);
            ResolveInfo resolveInfo = resolvedComponentInfo.getResolveInfoAt(0);
            Intent intent2 = ResolverActivity.this.getReplacementIntent(resolveInfo.activityInfo, intent);
            object = new DisplayResolveInfo(intent, resolveInfo, charSequence, (CharSequence)object, intent2);
            this.addResolveInfo((DisplayResolveInfo)object);
            if (intent2 == intent) {
                for (int i = 1; i < n; ++i) {
                    ((DisplayResolveInfo)object).addAlternateSourceIntent(resolvedComponentInfo.getIntentAt(i));
                }
            }
            this.updateLastChosenPosition(resolveInfo);
        }

        private void postListReadyRunnable() {
            if (ResolverActivity.this.mPostListReadyRunnable == null) {
                ResolverActivity.this.mPostListReadyRunnable = new Runnable(){

                    @Override
                    public void run() {
                        ResolverActivity.this.setHeader();
                        ResolverActivity.this.resetButtonBar();
                        ResolveListAdapter.this.onListRebuilt();
                        ResolverActivity.this.mPostListReadyRunnable = null;
                    }
                };
                ResolverActivity.this.getMainThreadHandler().post(ResolverActivity.this.mPostListReadyRunnable);
            }
        }

        private void processSortedList(List<ResolvedComponentInfo> object) {
            if (object != null && object.size() != 0) {
                Object object2;
                Object object3;
                this.mAllTargetsAreBrowsers = ResolverActivity.this.mUseLayoutForBrowsables;
                if (this.mInitialIntents != null) {
                    for (int i = 0; i < ((Intent[])(object3 = this.mInitialIntents)).length; ++i) {
                        if ((object3 = object3[i]) == null) continue;
                        Object object4 = ((Intent)object3).resolveActivityInfo(ResolverActivity.this.getPackageManager(), 0);
                        if (object4 == null) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("No activity found for ");
                            ((StringBuilder)object2).append(object3);
                            Log.w(ResolverActivity.TAG, ((StringBuilder)object2).toString());
                            continue;
                        }
                        object2 = new ResolveInfo();
                        ((ResolveInfo)object2).activityInfo = object4;
                        UserManager userManager = (UserManager)ResolverActivity.this.getSystemService("user");
                        if (object3 instanceof LabeledIntent) {
                            object4 = (LabeledIntent)object3;
                            ((ResolveInfo)object2).resolvePackageName = ((LabeledIntent)object4).getSourcePackage();
                            ((ResolveInfo)object2).labelRes = ((LabeledIntent)object4).getLabelResource();
                            ((ResolveInfo)object2).nonLocalizedLabel = ((LabeledIntent)object4).getNonLocalizedLabel();
                            ((ResolveInfo)object2).iconResourceId = ((ResolveInfo)object2).icon = ((LabeledIntent)object4).getIconResource();
                        }
                        if (userManager.isManagedProfile()) {
                            ((ResolveInfo)object2).noResourceId = true;
                            ((ResolveInfo)object2).icon = 0;
                        }
                        this.mAllTargetsAreBrowsers &= ((ResolveInfo)object2).handleAllWebDataURI;
                        object4 = ResolverActivity.this;
                        this.addResolveInfo((ResolverActivity)object4.new DisplayResolveInfo((Intent)object3, (ResolveInfo)object2, ((ResolveInfo)object2).loadLabel(((ContextWrapper)object4).getPackageManager()), null, (Intent)object3));
                    }
                }
                object = object.iterator();
                while (object.hasNext()) {
                    object3 = (ResolvedComponentInfo)object.next();
                    object2 = ((ResolvedComponentInfo)object3).getResolveInfoAt(0);
                    if (object2 == null) continue;
                    this.mAllTargetsAreBrowsers &= ((ResolveInfo)object2).handleAllWebDataURI;
                    object2 = ResolverActivity.this.makePresentationGetter((ResolveInfo)object2);
                    this.addResolveInfoWithAlternates((ResolvedComponentInfo)object3, ((ActivityInfoPresentationGetter)object2).getSubLabel(), ((ActivityInfoPresentationGetter)object2).getLabel());
                }
            }
            this.postListReadyRunnable();
        }

        private void updateLastChosenPosition(ResolveInfo resolveInfo) {
            if (this.mOtherProfile != null) {
                this.mLastChosenPosition = -1;
                return;
            }
            ResolveInfo resolveInfo2 = this.mLastChosen;
            if (resolveInfo2 != null && resolveInfo2.activityInfo.packageName.equals(resolveInfo.activityInfo.packageName) && this.mLastChosen.activityInfo.name.equals(resolveInfo.activityInfo.name)) {
                this.mLastChosenPosition = this.mDisplayList.size() - 1;
            }
        }

        public boolean areAllTargetsBrowsers() {
            return this.mAllTargetsAreBrowsers;
        }

        public final void bindView(int n, View view) {
            this.onBindView(view, this.getItem(n));
        }

        public final View createView(ViewGroup view) {
            view = this.onCreateView((ViewGroup)view);
            view.setTag(new ViewHolder(view));
            return view;
        }

        @Override
        public int getCount() {
            List<DisplayResolveInfo> list = this.mDisplayList;
            int n = list != null && !list.isEmpty() ? this.mDisplayList.size() : this.mPlaceholderCount;
            int n2 = n;
            if (this.mFilterLastUsed) {
                n2 = n;
                if (this.mLastChosenPosition >= 0) {
                    n2 = n - 1;
                }
            }
            return n2;
        }

        public DisplayResolveInfo getDisplayResolveInfo(int n) {
            return this.mDisplayList.get(n);
        }

        public int getDisplayResolveInfoCount() {
            return this.mDisplayList.size();
        }

        public DisplayResolveInfo getFilteredItem() {
            int n;
            if (this.mFilterLastUsed && (n = this.mLastChosenPosition) >= 0) {
                return this.mDisplayList.get(n);
            }
            return null;
        }

        public int getFilteredPosition() {
            int n;
            if (this.mFilterLastUsed && (n = this.mLastChosenPosition) >= 0) {
                return n;
            }
            return -1;
        }

        @Override
        public TargetInfo getItem(int n) {
            int n2 = n;
            if (this.mFilterLastUsed) {
                int n3 = this.mLastChosenPosition;
                n2 = n;
                if (n3 >= 0) {
                    n2 = n;
                    if (n >= n3) {
                        n2 = n + 1;
                    }
                }
            }
            if (this.mDisplayList.size() > n2) {
                return this.mDisplayList.get(n2);
            }
            return null;
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        public DisplayResolveInfo getOtherProfile() {
            return this.mOtherProfile;
        }

        public int getPlaceholderCount() {
            return this.mPlaceholderCount;
        }

        public float getScore(DisplayResolveInfo displayResolveInfo) {
            return this.mResolverListController.getScore(displayResolveInfo);
        }

        public int getUnfilteredCount() {
            return this.mDisplayList.size();
        }

        @Override
        public final View getView(int n, View view, ViewGroup viewGroup) {
            View view2;
            view = view2 = view;
            if (view2 == null) {
                view = this.createView(viewGroup);
            }
            this.onBindView(view, this.getItem(n));
            return view;
        }

        public void handlePackagesChanged() {
            this.rebuildList();
            if (this.getCount() == 0) {
                ResolverActivity.this.finish();
            }
        }

        public boolean hasFilteredItem() {
            boolean bl = this.mFilterLastUsed && this.mLastChosen != null;
            return bl;
        }

        protected void onBindView(View object, TargetInfo targetInfo) {
            ViewHolder viewHolder = (ViewHolder)((View)object).getTag();
            if (targetInfo == null) {
                viewHolder.icon.setImageDrawable(ResolverActivity.this.getDrawable(17303382));
                return;
            }
            CharSequence charSequence = targetInfo.getDisplayLabel();
            if (!TextUtils.equals(viewHolder.text.getText(), charSequence)) {
                viewHolder.text.setText(targetInfo.getDisplayLabel());
            }
            CharSequence charSequence2 = targetInfo.getExtendedInfo();
            object = charSequence2;
            if (TextUtils.equals(charSequence, charSequence2)) {
                object = null;
            }
            if (!TextUtils.equals(viewHolder.text2.getText(), (CharSequence)object)) {
                viewHolder.text2.setText((CharSequence)object);
            }
            if (targetInfo.isSuspended()) {
                viewHolder.icon.setColorFilter(ResolverActivity.this.mSuspendedMatrixColorFilter);
            } else {
                viewHolder.icon.setColorFilter(null);
            }
            if (targetInfo instanceof DisplayResolveInfo && !((DisplayResolveInfo)targetInfo).hasDisplayIcon()) {
                new LoadIconTask((DisplayResolveInfo)targetInfo, viewHolder.icon).execute(new Void[0]);
            } else {
                viewHolder.icon.setImageDrawable(targetInfo.getDisplayIcon());
            }
        }

        public View onCreateView(ViewGroup viewGroup) {
            return this.mInflater.inflate(17367259, viewGroup, false);
        }

        public void onListRebuilt() {
            TargetInfo targetInfo;
            if (this.getUnfilteredCount() == 1 && this.getOtherProfile() == null && ResolverActivity.this.shouldAutoLaunchSingleChoice(targetInfo = this.targetInfoForPosition(0, false))) {
                ResolverActivity.this.safelyStartActivity(targetInfo);
                ResolverActivity.this.finish();
            }
        }

        protected boolean rebuildList() {
            Object object;
            ArrayList<ResolvedComponentInfo> arrayList;
            Object object2;
            this.mOtherProfile = null;
            this.mLastChosen = null;
            this.mLastChosenPosition = -1;
            this.mAllTargetsAreBrowsers = false;
            this.mDisplayList.clear();
            if (this.mBaseResolveList != null) {
                object = new ArrayList<ResolvedComponentInfo>();
                this.mUnfilteredResolveList = object;
                this.mResolverListController.addResolveListDedupe((List<ResolvedComponentInfo>)object, ResolverActivity.this.getTargetIntent(), this.mBaseResolveList);
            } else {
                arrayList = this.mResolverListController.getResolversForIntent(this.shouldGetResolvedFilter(), ResolverActivity.this.shouldGetActivityMetadata(), this.mIntents);
                this.mUnfilteredResolveList = arrayList;
                if (arrayList == null) {
                    this.processSortedList(arrayList);
                    return true;
                }
                object2 = this.mResolverListController.filterIneligibleActivities(arrayList, true);
                object = arrayList;
                if (object2 != null) {
                    this.mUnfilteredResolveList = object2;
                    object = arrayList;
                }
            }
            arrayList = object.iterator();
            while (arrayList.hasNext()) {
                object2 = (ResolvedComponentInfo)arrayList.next();
                if (object2.getResolveInfoAt((int)0).targetUserId == -2) continue;
                this.mOtherProfile = new DisplayResolveInfo(((ResolvedComponentInfo)object2).getIntentAt(0), ((ResolvedComponentInfo)object2).getResolveInfoAt(0), ((ResolvedComponentInfo)object2).getResolveInfoAt(0).loadLabel(ResolverActivity.this.mPm), ((ResolvedComponentInfo)object2).getResolveInfoAt(0).loadLabel(ResolverActivity.this.mPm), ResolverActivity.this.getReplacementIntent(object2.getResolveInfoAt((int)0).activityInfo, ((ResolvedComponentInfo)object2).getIntentAt(0)));
                object.remove(object2);
                break;
            }
            if (this.mOtherProfile == null) {
                try {
                    this.mLastChosen = this.mResolverListController.getLastChosen();
                }
                catch (RemoteException remoteException) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Error calling getLastChosenActivity\n");
                    ((StringBuilder)object2).append(remoteException);
                    Log.d(ResolverActivity.TAG, ((StringBuilder)object2).toString());
                }
            }
            if (object.size() > 0) {
                arrayList = this.mResolverListController;
                boolean bl = this.mUnfilteredResolveList == object;
                if ((arrayList = ((ResolverListController)((Object)arrayList)).filterLowPriority((List<ResolvedComponentInfo>)object, bl)) != null) {
                    this.mUnfilteredResolveList = arrayList;
                }
                if (object.size() > 1) {
                    int n;
                    int n2 = n = object.size();
                    if (ResolverActivity.this.useLayoutWithDefault()) {
                        n2 = n - 1;
                    }
                    this.setPlaceholderCount(n2);
                    new AsyncTask<List<ResolvedComponentInfo>, Void, List<ResolvedComponentInfo>>(){

                        protected List<ResolvedComponentInfo> doInBackground(List<ResolvedComponentInfo> ... arrlist) {
                            ResolveListAdapter.this.mResolverListController.sort(arrlist[0]);
                            return arrlist[0];
                        }

                        @Override
                        protected void onPostExecute(List<ResolvedComponentInfo> list) {
                            ResolveListAdapter.this.processSortedList(list);
                            ResolverActivity.this.bindProfileView();
                            ResolveListAdapter.this.notifyDataSetChanged();
                        }
                    }.execute(object);
                    this.postListReadyRunnable();
                    return false;
                }
                this.processSortedList((List<ResolvedComponentInfo>)object);
                return true;
            }
            this.processSortedList((List<ResolvedComponentInfo>)object);
            return true;
        }

        public ResolveInfo resolveInfoForPosition(int n, boolean bl) {
            TargetInfo targetInfo = this.targetInfoForPosition(n, bl);
            if (targetInfo != null) {
                return targetInfo.getResolveInfo();
            }
            return null;
        }

        public void setPlaceholderCount(int n) {
            this.mPlaceholderCount = n;
        }

        public boolean shouldGetResolvedFilter() {
            return this.mFilterLastUsed;
        }

        public TargetInfo targetInfoForPosition(int n, boolean bl) {
            if (bl) {
                return this.getItem(n);
            }
            if (this.mDisplayList.size() > n) {
                return this.mDisplayList.get(n);
            }
            return null;
        }

        public void updateChooserCounts(String string2, int n, String string3) {
            this.mResolverListController.updateChooserCounts(string2, n, string3);
        }

        public void updateModel(ComponentName componentName) {
            this.mResolverListController.updateModel(componentName);
        }

    }

    @VisibleForTesting
    public static final class ResolvedComponentInfo {
        private final List<Intent> mIntents = new ArrayList<Intent>();
        private final List<ResolveInfo> mResolveInfos = new ArrayList<ResolveInfo>();
        public final ComponentName name;

        public ResolvedComponentInfo(ComponentName componentName, Intent intent, ResolveInfo resolveInfo) {
            this.name = componentName;
            this.add(intent, resolveInfo);
        }

        public void add(Intent intent, ResolveInfo resolveInfo) {
            this.mIntents.add(intent);
            this.mResolveInfos.add(resolveInfo);
        }

        public int findIntent(Intent intent) {
            int n = this.mIntents.size();
            for (int i = 0; i < n; ++i) {
                if (!intent.equals(this.mIntents.get(i))) continue;
                return i;
            }
            return -1;
        }

        public int findResolveInfo(ResolveInfo resolveInfo) {
            int n = this.mResolveInfos.size();
            for (int i = 0; i < n; ++i) {
                if (!resolveInfo.equals(this.mResolveInfos.get(i))) continue;
                return i;
            }
            return -1;
        }

        public int getCount() {
            return this.mIntents.size();
        }

        public Intent getIntentAt(int n) {
            Intent intent = n >= 0 ? this.mIntents.get(n) : null;
            return intent;
        }

        public ResolveInfo getResolveInfoAt(int n) {
            ResolveInfo resolveInfo = n >= 0 ? this.mResolveInfos.get(n) : null;
            return resolveInfo;
        }
    }

    public static interface TargetInfo {
        public TargetInfo cloneFilledIn(Intent var1, int var2);

        public List<Intent> getAllSourceIntents();

        public Drawable getDisplayIcon();

        public CharSequence getDisplayLabel();

        public CharSequence getExtendedInfo();

        public ResolveInfo getResolveInfo();

        public ComponentName getResolvedComponentName();

        public Intent getResolvedIntent();

        public boolean isSuspended();

        public boolean start(Activity var1, Bundle var2);

        public boolean startAsCaller(ResolverActivity var1, Bundle var2, int var3);

        public boolean startAsUser(Activity var1, Bundle var2, UserHandle var3);
    }

    private static abstract class TargetPresentationGetter {
        private final ApplicationInfo mAi;
        private Context mCtx;
        private final boolean mHasSubstitutePermission;
        private final int mIconDpi;
        protected PackageManager mPm;

        TargetPresentationGetter(Context context, int n, ApplicationInfo applicationInfo) {
            this.mCtx = context;
            this.mPm = context.getPackageManager();
            this.mAi = applicationInfo;
            this.mIconDpi = n;
            boolean bl = this.mPm.checkPermission("android.permission.SUBSTITUTE_SHARE_TARGET_APP_NAME_AND_ICON", this.mAi.packageName) == 0;
            this.mHasSubstitutePermission = bl;
        }

        abstract String getAppSubLabelInternal();

        public Drawable getIcon(UserHandle userHandle) {
            return new BitmapDrawable(this.mCtx.getResources(), this.getIconBitmap(userHandle));
        }

        public Bitmap getIconBitmap(UserHandle parcelable) {
            Drawable drawable2 = null;
            if (this.mHasSubstitutePermission) {
                drawable2 = this.getIconSubstituteInternal();
            }
            Object object = drawable2;
            if (drawable2 == null) {
                object = drawable2;
                try {
                    if (this.mAi.icon != 0) {
                        object = this.loadIconFromResource(this.mPm.getResourcesForApplication(this.mAi), this.mAi.icon);
                    }
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    object = drawable2;
                }
            }
            drawable2 = object;
            if (object == null) {
                drawable2 = this.mAi.loadIcon(this.mPm);
            }
            object = SimpleIconFactory.obtain(this.mCtx);
            parcelable = ((SimpleIconFactory)object).createUserBadgedIconBitmap(drawable2, (UserHandle)parcelable);
            ((SimpleIconFactory)object).recycle();
            return parcelable;
        }

        abstract Drawable getIconSubstituteInternal();

        public String getLabel() {
            String string2 = null;
            if (this.mHasSubstitutePermission) {
                string2 = this.getAppSubLabelInternal();
            }
            String string3 = string2;
            if (string2 == null) {
                string3 = (String)this.mAi.loadLabel(this.mPm);
            }
            return string3;
        }

        public String getSubLabel() {
            if (this.mHasSubstitutePermission) {
                return null;
            }
            return this.getAppSubLabelInternal();
        }

        protected Drawable loadIconFromResource(Resources resources, int n) {
            return resources.getDrawableForDensity(n, this.mIconDpi);
        }

        protected String loadLabelFromResource(Resources resources, int n) {
            return resources.getString(n);
        }
    }

    static class ViewHolder {
        public Drawable defaultItemViewBackground;
        public ImageView icon;
        public View itemView;
        public TextView text;
        public TextView text2;

        public ViewHolder(View view) {
            this.itemView = view;
            this.defaultItemViewBackground = view.getBackground();
            this.text = (TextView)view.findViewById(16908308);
            this.text2 = (TextView)view.findViewById(16908309);
            this.icon = (ImageView)view.findViewById(16908294);
        }
    }

}

