/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.app.-$
 *  com.android.internal.app.-$$Lambda
 *  com.android.internal.app.-$$Lambda$ChooserActivity
 *  com.android.internal.app.-$$Lambda$ChooserActivity$ChooserListAdapter
 *  com.android.internal.app.-$$Lambda$ChooserActivity$ChooserListAdapter$0o9wjP10lRaguh-ZLgVIZcGRo0w
 */
package com.android.internal.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionManager;
import android.app.prediction.AppPredictor;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.service.chooser.ChooserTarget;
import android.service.chooser.IChooserTargetResult;
import android.service.chooser.IChooserTargetService;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.HashedStringCache;
import android.util.Log;
import android.util.Size;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.-$;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.AppPredictionServiceResolverComparator;
import com.android.internal.app.IntentForwarderActivity;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.ResolverListController;
import com.android.internal.app.ResolverRankerServiceResolverComparator;
import com.android.internal.app.ResolverTargetActionsDialogFragment;
import com.android.internal.app.SimpleIconFactory;
import com.android.internal.app._$$Lambda$ChooserActivity$0_ugWf0NTvnoGiNRGVYJFNRQtsI;
import com.android.internal.app._$$Lambda$ChooserActivity$59FvMzyIg7yJzeX3NNdkiEmiSgI;
import com.android.internal.app._$$Lambda$ChooserActivity$ChooserListAdapter$0o9wjP10lRaguh_ZLgVIZcGRo0w;
import com.android.internal.app._$$Lambda$ChooserActivity$ContentPreviewCoordinator$4EA4_6wC7DBv77gLolqI2_lsDQI;
import com.android.internal.app._$$Lambda$ChooserActivity$DtVuD6Mjmx25X89cZXV33qf48uk;
import com.android.internal.app._$$Lambda$ChooserActivity$XR1YdxuJecnZUtHspyjpRELkDj8;
import com.android.internal.app._$$Lambda$ChooserActivity$mSpb8JdVEdN3REmKTSrORHIDnIo;
import com.android.internal.app._$$Lambda$ChooserActivity$n2FimsQN3_RG5vs7T6aVc1Pt9v0;
import com.android.internal.app._$$Lambda$KV7a09lZoRu37HsBE4cW2uLB7o8;
import com.android.internal.content.PackageMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ImageUtils;
import com.android.internal.widget.ResolverDrawerLayout;
import com.google.android.collect.Lists;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

public class ChooserActivity
extends ResolverActivity {
    public static final String APP_PREDICTION_INTENT_FILTER_KEY = "intent_filter";
    private static final int APP_PREDICTION_SHARE_TARGET_QUERY_PACKAGE_LIMIT = 20;
    private static final String APP_PREDICTION_SHARE_UI_SURFACE = "share";
    private static final float CALLER_TARGET_SCORE_BOOST = 900.0f;
    private static final int CONTENT_PREVIEW_FILE = 2;
    private static final int CONTENT_PREVIEW_IMAGE = 1;
    private static final int CONTENT_PREVIEW_TEXT = 3;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_SALT_EXPIRATION_DAYS = 7;
    private static final float DIRECT_SHARE_EXPANSION_RATE = 0.78f;
    public static final String EXTRA_PRIVATE_RETAIN_IN_ON_STOP = "com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP";
    public static final String LAUNCH_LOCATON_DIRECT_SHARE = "direct_share";
    @VisibleForTesting
    public static final int LIST_VIEW_UPDATE_INTERVAL_IN_MILLIS = 250;
    private static final int MAX_EXTRA_CHOOSER_TARGETS = 2;
    private static final int MAX_EXTRA_INITIAL_INTENTS = 2;
    private static final int MAX_LOG_RANK_POSITION = 12;
    private static final int MAX_RANKED_TARGETS = 4;
    private static final int NO_DIRECT_SHARE_ANIM_IN_MILLIS = 200;
    private static final String PREF_NUM_SHEET_EXPANSIONS = "pref_num_sheet_expansions";
    private static final int QUERY_TARGET_SERVICE_LIMIT = 5;
    private static final int SHARE_TARGET_QUERY_PACKAGE_LIMIT = 20;
    private static final float SHORTCUT_TARGET_SCORE_BOOST = 90.0f;
    private static final String TAG = "ChooserActivity";
    private static final String TARGET_DETAILS_FRAGMENT_TAG = "targetDetailsFragment";
    private static final boolean USE_CHOOSER_TARGET_SERVICE_FOR_DIRECT_TARGETS = true;
    private static final boolean USE_PREDICTION_MANAGER_FOR_DIRECT_TARGETS = true;
    private static final boolean USE_PREDICTION_MANAGER_FOR_SHARE_ACTIVITIES = true;
    private static final boolean USE_SHORTCUT_MANAGER_FOR_DIRECT_TARGETS = true;
    private AppPredictor mAppPredictor;
    private AppPredictor.Callback mAppPredictorCallback;
    private ChooserTarget[] mCallerChooserTargets;
    private final ChooserHandler mChooserHandler = new ChooserHandler();
    private ChooserListAdapter mChooserListAdapter;
    private ChooserRowAdapter mChooserRowAdapter;
    private int mChooserRowServiceSpacing;
    private long mChooserShownTime;
    private IntentSender mChosenComponentSender;
    private int mCurrAvailableWidth = 0;
    private Map<ChooserTarget, AppTarget> mDirectShareAppTargetCache;
    private ComponentName[] mFilteredComponentNames;
    protected boolean mIsSuccessfullySelected;
    private boolean mListViewDataChanged = false;
    private int mMaxHashSaltDays = DeviceConfig.getInt("systemui", "hash_salt_max_days", 7);
    protected MetricsLogger mMetricsLogger;
    private ContentPreviewCoordinator mPreviewCoord;
    private long mQueriedSharingShortcutsTimeMs;
    private long mQueriedTargetServicesTimeMs;
    private Intent mReferrerFillInIntent;
    private IntentSender mRefinementIntentSender;
    private RefinementResultReceiver mRefinementResultReceiver;
    private Bundle mReplacementExtras;
    private final List<ChooserTargetServiceConnection> mServiceConnections = new ArrayList<ChooserTargetServiceConnection>();
    private final Set<ComponentName> mServicesRequested = new HashSet<ComponentName>();
    private List<ResolverActivity.DisplayResolveInfo> mSortedList = new ArrayList<ResolverActivity.DisplayResolveInfo>();

    private void adjustPreviewWidth(int n, View view) {
        int n2 = -1;
        if (this.shouldDisplayLandscape(n)) {
            n2 = this.getResources().getDimensionPixelSize(17105041);
        }
        if (view == null) {
            view = this.getWindow().getDecorView();
        }
        this.updateLayoutWidth(16908842, n2, view);
        this.updateLayoutWidth(16908845, n2, view);
        this.updateLayoutWidth(16908832, n2, view);
    }

    private String convertServiceName(String string2, String string3) {
        if (TextUtils.isEmpty(string3)) {
            return null;
        }
        if (string3.startsWith(".")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(string3);
            string2 = stringBuilder.toString();
        } else {
            string2 = string3.indexOf(46) >= 0 ? string3 : null;
        }
        return string2;
    }

    private ChooserTarget convertToChooserTarget(ShortcutManager.ShareShortcutInfo shareShortcutInfo, float f) {
        ShortcutInfo shortcutInfo = shareShortcutInfo.getShortcutInfo();
        Bundle bundle = new Bundle();
        bundle.putString("android.intent.extra.shortcut.ID", shortcutInfo.getId());
        return new ChooserTarget(shortcutInfo.getShortLabel(), null, f, shareShortcutInfo.getTargetComponent().clone(), bundle);
    }

    private ViewGroup displayContentPreview(int n, Intent object, LayoutInflater layoutInflater, ViewGroup viewGroup, ViewGroup viewGroup2) {
        if (viewGroup != null) {
            return viewGroup;
        }
        viewGroup = null;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unexpected content preview type: ");
                    ((StringBuilder)object).append(n);
                    Log.e(TAG, ((StringBuilder)object).toString());
                    object = viewGroup;
                } else {
                    object = this.displayTextContentPreview((Intent)object, layoutInflater, viewGroup2);
                }
            } else {
                object = this.displayFileContentPreview((Intent)object, layoutInflater, viewGroup2);
            }
        } else {
            object = this.displayImageContentPreview((Intent)object, layoutInflater, viewGroup2);
        }
        if (object != null) {
            this.adjustPreviewWidth(this.getResources().getConfiguration().orientation, (View)object);
        }
        return object;
    }

    private ViewGroup displayFileContentPreview(Intent object, LayoutInflater object2, ViewGroup viewGroup) {
        object2 = (ViewGroup)((LayoutInflater)object2).inflate(17367122, viewGroup, false);
        ((View)((View)object2).findViewById(16908912)).setVisibility(8);
        if ("android.intent.action.SEND".equals(((Intent)object).getAction())) {
            this.loadFileUriIntoView((Uri)((Intent)object).getParcelableExtra("android.intent.extra.STREAM"), (View)object2);
        } else {
            int n = (object = ((Intent)object).getParcelableArrayListExtra("android.intent.extra.STREAM")).size();
            if (n == 0) {
                ((View)object2).setVisibility(8);
                Log.i(TAG, "Appears to be no uris available in EXTRA_STREAM, removing preview area");
                return object2;
            }
            if (n == 1) {
                this.loadFileUriIntoView((Uri)object.get(0), (View)object2);
            } else {
                object = this.extractFileInfo((Uri)object.get(0), this.getContentResolver());
                object = this.getResources().getQuantityString(18153490, n, ((FileInfo)object).name, --n);
                ((TextView)((View)object2).findViewById(16908834)).setText((CharSequence)object);
                ((View)((View)object2).findViewById(16908833)).setVisibility(8);
                object = (ImageView)((View)object2).findViewById(16908831);
                ((ImageView)object).setVisibility(0);
                ((ImageView)object).setImageResource(17302409);
            }
        }
        return object2;
    }

    private ViewGroup displayImageContentPreview(Intent object, LayoutInflater object2, ViewGroup object3) {
        object2 = (ViewGroup)((LayoutInflater)object2).inflate(17367123, (ViewGroup)object3, false);
        this.mPreviewCoord = new ContentPreviewCoordinator((View)object2, true);
        if ("android.intent.action.SEND".equals(((Intent)object).getAction())) {
            object = (Uri)((Intent)object).getParcelableExtra("android.intent.extra.STREAM");
            this.mPreviewCoord.loadUriIntoView(16908835, (Uri)object, 0);
        } else {
            object3 = this.getContentResolver();
            Object object4 = ((Intent)object).getParcelableArrayListExtra("android.intent.extra.STREAM");
            object = new ArrayList();
            object4 = object4.iterator();
            while (object4.hasNext()) {
                Uri uri = (Uri)object4.next();
                if (!this.isImageType(((ContentResolver)object3).getType(uri))) continue;
                object.add(uri);
            }
            if (object.size() == 0) {
                Log.i(TAG, "Attempted to display image preview area with zero available images detected in EXTRA_STREAM list");
                ((View)object2).setVisibility(8);
                return object2;
            }
            this.mPreviewCoord.loadUriIntoView(16908835, (Uri)object.get(0), 0);
            if (object.size() == 2) {
                this.mPreviewCoord.loadUriIntoView(16908836, (Uri)object.get(1), 0);
            } else if (object.size() > 2) {
                this.mPreviewCoord.loadUriIntoView(16908837, (Uri)object.get(1), 0);
                this.mPreviewCoord.loadUriIntoView(16908838, (Uri)object.get(2), object.size() - 3);
            }
        }
        return object2;
    }

    private ViewGroup displayTextContentPreview(Intent object, LayoutInflater object2, ViewGroup viewGroup) {
        viewGroup = (ViewGroup)((LayoutInflater)object2).inflate(17367124, viewGroup, false);
        ((View)viewGroup.findViewById(16908847)).setOnClickListener(new _$$Lambda$ChooserActivity$59FvMzyIg7yJzeX3NNdkiEmiSgI(this));
        object2 = ((Intent)object).getCharSequenceExtra("android.intent.extra.TEXT");
        if (object2 == null) {
            ((View)viewGroup.findViewById(16908842)).setVisibility(8);
        } else {
            ((TextView)viewGroup.findViewById(16908840)).setText((CharSequence)object2);
        }
        object2 = ((Intent)object).getStringExtra("android.intent.extra.TITLE");
        if (TextUtils.isEmpty((CharSequence)object2)) {
            ((View)viewGroup.findViewById(16908845)).setVisibility(8);
        } else {
            ((TextView)viewGroup.findViewById(16908844)).setText((CharSequence)object2);
            ClipData clipData = ((Intent)object).getClipData();
            object = object2 = null;
            if (clipData != null) {
                object = object2;
                if (clipData.getItemCount() > 0) {
                    object = clipData.getItemAt(0).getUri();
                }
            }
            object2 = (ImageView)viewGroup.findViewById(16908843);
            if (object == null) {
                ((ImageView)object2).setVisibility(8);
            } else {
                this.mPreviewCoord = new ContentPreviewCoordinator(viewGroup, false);
                this.mPreviewCoord.loadUriIntoView(16908843, (Uri)object, 0);
            }
        }
        return viewGroup;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private FileInfo extractFileInfo(Uri var1_1, ContentResolver var2_2) {
        var3_5 = null;
        var4_7 = null;
        var5_8 = null;
        var6_9 = null;
        var7_10 = false;
        var8_11 = false;
        var9_12 = var7_10;
        var10_13 = this.queryResolver((ContentResolver)var2_2, (Uri)var1_1);
        var11_14 = var4_7;
        var12_16 = var8_11;
        if (var10_13 == null) ** GOTO lbl60
        var13_17 = var3_5;
        var11_14 = var4_7;
        var12_16 = var8_11;
        try {
            if (var10_13.getCount() <= 0) ** GOTO lbl60
            var13_17 = var3_5;
            var14_18 = var10_13.getColumnIndex("_display_name");
            var13_17 = var3_5;
            var15_19 = var10_13.getColumnIndex("title");
            var13_17 = var3_5;
            var16_20 = var10_13.getColumnIndex("flags");
            var13_17 = var3_5;
            var10_13.moveToFirst();
            if (var14_18 != -1) {
                var13_17 = var3_5;
                var2_2 = var10_13.getString(var14_18);
            } else {
                var2_2 = var6_9;
                if (var15_19 != -1) {
                    var13_17 = var3_5;
                    var2_2 = var10_13.getString(var15_19);
                }
            }
            var11_14 = var2_2;
            var12_16 = var8_11;
            if (var16_20 == -1) ** GOTO lbl60
            var13_17 = var2_2;
            var14_18 = var10_13.getInt(var16_20);
            var12_16 = true;
        }
        catch (Throwable var11_15) {
            try {
                throw var11_15;
            }
            catch (Throwable var2_3) {
                try {
                    var10_13.close();
                    ** GOTO lbl57
                }
                catch (Throwable var3_6) {
                    var5_8 = var13_17;
                    var9_12 = var7_10;
                    try {
                        var11_15.addSuppressed(var3_6);
lbl57: // 2 sources:
                        var5_8 = var13_17;
                        var9_12 = var7_10;
                        throw var2_3;
lbl60: // 4 sources:
                        if (var10_13 != null) {
                            var5_8 = var11_14;
                            var9_12 = var12_16;
                            var10_13.close();
                        }
                        var5_8 = var11_14;
                    }
                    catch (NullPointerException | SecurityException var2_4) {
                        this.logContentPreviewWarning((Uri)var1_1);
                        var12_16 = var9_12;
                    }
                }
            }
        }
        if ((var14_18 & 1) == 0) {
            var12_16 = false;
        }
        var11_14 = var2_2;
        ** GOTO lbl60
        var2_2 = var5_8;
        if (TextUtils.isEmpty(var5_8) == false) return new FileInfo((String)var2_2, var12_16);
        var1_1 = var1_1.getPath();
        var14_18 = var1_1.lastIndexOf(47);
        var2_2 = var1_1;
        if (var14_18 == -1) return new FileInfo((String)var2_2, var12_16);
        var2_2 = var1_1.substring(var14_18 + 1);
        return new FileInfo((String)var2_2, var12_16);
    }

    private int findPreferredContentPreview(Intent object, ContentResolver contentResolver) {
        String string2 = ((Intent)object).getAction();
        if ("android.intent.action.SEND".equals(string2)) {
            return this.findPreferredContentPreview((Uri)((Intent)object).getParcelableExtra("android.intent.extra.STREAM"), contentResolver);
        }
        if ("android.intent.action.SEND_MULTIPLE".equals(string2)) {
            if ((object = ((Intent)object).getParcelableArrayListExtra("android.intent.extra.STREAM")) != null && !object.isEmpty()) {
                object = object.iterator();
                while (object.hasNext()) {
                    if (this.findPreferredContentPreview((Uri)object.next(), contentResolver) != 2) continue;
                    return 2;
                }
                return 1;
            }
            return 3;
        }
        return 3;
    }

    private int findPreferredContentPreview(Uri uri, ContentResolver contentResolver) {
        if (uri == null) {
            return 3;
        }
        int n = this.isImageType(contentResolver.getType(uri)) ? 1 : 2;
        return n;
    }

    private AppPredictor getAppPredictor() {
        if (this.mAppPredictor == null && this.getPackageManager().getAppPredictionServicePackageName() != null) {
            IntentFilter intentFilter = this.getTargetIntentFilter();
            Parcelable parcelable = new Bundle();
            parcelable.putParcelable(APP_PREDICTION_INTENT_FILTER_KEY, intentFilter);
            parcelable = new AppPredictionContext.Builder(this).setUiSurface(APP_PREDICTION_SHARE_UI_SURFACE).setPredictedTargetCount(20).setExtras((Bundle)parcelable).build();
            this.mAppPredictor = this.getSystemService(AppPredictionManager.class).createAppPredictionSession((AppPredictionContext)parcelable);
        }
        return this.mAppPredictor;
    }

    private AppPredictor getAppPredictorForDirectShareIfEnabled() {
        AppPredictor appPredictor = !ActivityManager.isLowRamDeviceStatic() ? this.getAppPredictor() : null;
        return appPredictor;
    }

    private AppPredictor getAppPredictorForShareActivitesIfEnabled() {
        return this.getAppPredictor();
    }

    private List<ResolverActivity.DisplayResolveInfo> getDisplayResolveInfos(ChooserListAdapter chooserListAdapter) {
        ArrayList<ResolverActivity.DisplayResolveInfo> arrayList = new ArrayList<ResolverActivity.DisplayResolveInfo>();
        int n = 0;
        int n2 = chooserListAdapter.getDisplayResolveInfoCount();
        for (int i = 0; i < n2; ++i) {
            int n3;
            ResolverActivity.DisplayResolveInfo displayResolveInfo = chooserListAdapter.getDisplayResolveInfo(i);
            if (chooserListAdapter.getScore(displayResolveInfo) == 0.0f) continue;
            arrayList.add(displayResolveInfo);
            n = n3 = n + 1;
            if (n3 >= 20) break;
        }
        return arrayList;
    }

    private int getNumSheetExpansions() {
        return this.getPreferences(0).getInt(PREF_NUM_SHEET_EXPANSIONS, 0);
    }

    private int getRankedPosition(SelectableTargetInfo object) {
        object = ((SelectableTargetInfo)object).getChooserTarget().getComponentName().getPackageName();
        int n = Math.min(this.mChooserListAdapter.mDisplayList.size(), 12);
        for (int i = 0; i < n; ++i) {
            if (!((ResolverActivity.DisplayResolveInfo)this.mChooserListAdapter.mDisplayList.get((int)i)).getResolveInfo().activityInfo.packageName.equals(object)) continue;
            return i;
        }
        return -1;
    }

    private IntentFilter getTargetIntentFilter() {
        CharSequence charSequence;
        Intent intent;
        Object object;
        try {
            intent = this.getTargetIntent();
            charSequence = intent.getDataString();
            object = charSequence;
        }
        catch (Exception exception) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("failed to get target intent filter ");
            ((StringBuilder)charSequence).append(exception);
            Log.e(TAG, ((StringBuilder)charSequence).toString());
            return null;
        }
        if (TextUtils.isEmpty(charSequence)) {
            object = intent.getType();
        }
        object = new IntentFilter(intent.getAction(), (String)object);
        return object;
    }

    private void handleLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        if (this.mChooserRowAdapter != null && this.mAdapterView != null) {
            n = n3 - n - view.getPaddingLeft() - view.getPaddingRight();
            if (this.mChooserRowAdapter.consumeLayoutRequest() || this.mChooserRowAdapter.calculateChooserTargetWidth(n) || this.mAdapterView.getAdapter() == null || n != this.mCurrAvailableWidth) {
                this.mCurrAvailableWidth = n;
                this.mAdapterView.setAdapter(this.mChooserRowAdapter);
                this.getMainThreadHandler().post(new _$$Lambda$ChooserActivity$0_ugWf0NTvnoGiNRGVYJFNRQtsI(this, n4, n2));
            }
            return;
        }
    }

    private void handleScroll(View view, int n, int n2, int n3, int n4) {
        ChooserRowAdapter chooserRowAdapter = this.mChooserRowAdapter;
        if (chooserRowAdapter != null) {
            chooserRowAdapter.handleScroll(view, n2, n4);
        }
    }

    private void incrementNumSheetExpansions() {
        this.getPreferences(0).edit().putInt(PREF_NUM_SHEET_EXPANSIONS, this.getNumSheetExpansions() + 1).apply();
    }

    private boolean isPackageEnabled(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return false;
        }
        try {
            object = this.getPackageManager().getApplicationInfo((String)object, 0);
            return object != null && ((ApplicationInfo)object).enabled && (((ApplicationInfo)object).flags & 1073741824) == 0;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    private boolean isSendAction(Intent object) {
        if (object == null) {
            return false;
        }
        if ((object = ((Intent)object).getAction()) == null) {
            return false;
        }
        return "android.intent.action.SEND".equals(object) || "android.intent.action.SEND_MULTIPLE".equals(object);
        {
        }
    }

    public static /* synthetic */ void lambda$59FvMzyIg7yJzeX3NNdkiEmiSgI(ChooserActivity chooserActivity, View view) {
        chooserActivity.onCopyButtonClicked(view);
    }

    public static /* synthetic */ void lambda$mSpb8JdVEdN3REmKTSrORHIDnIo(ChooserActivity chooserActivity, View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        chooserActivity.handleLayoutChange(view, n, n2, n3, n4, n5, n6, n7, n8);
    }

    public static /* synthetic */ void lambda$n2FimsQN3_RG5vs7T6aVc1Pt9v0(ChooserActivity chooserActivity, View view, int n, int n2, int n3, int n4) {
        chooserActivity.handleScroll(view, n, n2, n3, n4);
    }

    private void loadFileUriIntoView(Uri object, View view) {
        FileInfo fileInfo = this.extractFileInfo((Uri)object, this.getContentResolver());
        ((TextView)view.findViewById(16908834)).setText(fileInfo.name);
        if (fileInfo.hasThumbnail) {
            this.mPreviewCoord = new ContentPreviewCoordinator(view, false);
            this.mPreviewCoord.loadUriIntoView(16908833, (Uri)object, 0);
        } else {
            ((View)view.findViewById(16908833)).setVisibility(8);
            object = (ImageView)view.findViewById(16908831);
            ((ImageView)object).setVisibility(0);
            ((ImageView)object).setImageResource(17302105);
        }
    }

    private void logContentPreviewWarning(Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not load (");
        stringBuilder.append(uri.toString());
        stringBuilder.append(") thumbnail/name for preview. If desired, consider using Intent#createChooser to launch the ChooserActivity, and set your Intent's clipData and flags in accordance with that method's documentation");
        Log.w(TAG, stringBuilder.toString());
    }

    private void logDirectShareTargetReceived(int n) {
        long l = n == 1718 ? this.mQueriedSharingShortcutsTimeMs : this.mQueriedTargetServicesTimeMs;
        int n2 = (int)(System.currentTimeMillis() - l);
        this.getMetricsLogger().write(new LogMaker(n).setSubtype(n2));
    }

    private void modifyTargetIntent(Intent intent) {
        if (this.isSendAction(intent)) {
            intent.addFlags(134742016);
        }
    }

    private void onCopyButtonClicked(View object) {
        Object object2;
        block8 : {
            block3 : {
                block6 : {
                    block4 : {
                        block7 : {
                            block5 : {
                                block2 : {
                                    object2 = this.getTargetIntent();
                                    if (object2 != null) break block2;
                                    this.finish();
                                    break block3;
                                }
                                object = ((Intent)object2).getAction();
                                if (!"android.intent.action.SEND".equals(object)) break block4;
                                object = ((Intent)object2).getStringExtra("android.intent.extra.TEXT");
                                object2 = (Uri)((Intent)object2).getParcelableExtra("android.intent.extra.STREAM");
                                if (object == null) break block5;
                                object = ClipData.newPlainText(null, (CharSequence)object);
                                break block6;
                            }
                            if (object2 == null) break block7;
                            object = ClipData.newUri(this.getContentResolver(), null, (Uri)object2);
                            break block6;
                        }
                        Log.w(TAG, "No data available to copy to clipboard");
                        return;
                    }
                    if ("android.intent.action.SEND_MULTIPLE".equals(object)) {
                        object2 = ((Intent)object2).getParcelableArrayListExtra("android.intent.extra.STREAM");
                        object = ClipData.newUri(this.getContentResolver(), null, (Uri)((ArrayList)object2).get(0));
                        for (int i = 1; i < ((ArrayList)object2).size(); ++i) {
                            ((ClipData)object).addItem(this.getContentResolver(), new ClipData.Item((Uri)((ArrayList)object2).get(i)));
                        }
                    }
                    break block8;
                }
                ((ClipboardManager)this.getSystemService("clipboard")).setPrimaryClip((ClipData)object);
                Toast.makeText(this.getApplicationContext(), 17039796, 0).show();
                this.finish();
            }
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Action (");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(") not supported for copying to clipboard");
        Log.w(TAG, ((StringBuilder)object2).toString());
    }

    private void queryDirectShareTargets(ChooserListAdapter chooserListAdapter, boolean bl) {
        Object object;
        this.mQueriedSharingShortcutsTimeMs = System.currentTimeMillis();
        if (!bl && (object = this.getAppPredictorForDirectShareIfEnabled()) != null) {
            ((AppPredictor)object).requestPredictionUpdate();
            return;
        }
        object = this.getTargetIntentFilter();
        if (object == null) {
            return;
        }
        AsyncTask.execute(new _$$Lambda$ChooserActivity$XR1YdxuJecnZUtHspyjpRELkDj8(this, (IntentFilter)object, this.getDisplayResolveInfos(chooserListAdapter)));
    }

    private void sendClickToAppPredictor(ResolverActivity.TargetInfo object) {
        AppPredictor appPredictor = this.getAppPredictorForDirectShareIfEnabled();
        if (appPredictor == null) {
            return;
        }
        if (!(object instanceof ChooserTargetInfo)) {
            return;
        }
        ChooserTarget chooserTarget = ((ChooserTargetInfo)object).getChooserTarget();
        object = null;
        Map<ChooserTarget, AppTarget> map = this.mDirectShareAppTargetCache;
        if (map != null) {
            object = map.get(chooserTarget);
        }
        if (object != null) {
            appPredictor.notifyAppTargetEvent(new AppTargetEvent.Builder((AppTarget)object, 1).setLaunchLocation(LAUNCH_LOCATON_DIRECT_SHARE).build());
        }
    }

    private void sendShareShortcutInfoList(List<ShortcutManager.ShareShortcutInfo> list, List<ResolverActivity.DisplayResolveInfo> object, List<AppTarget> list2) {
        int n;
        if (list2 != null && list2.size() != list.size()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("resultList and appTargets must have the same size. resultList.size()=");
            ((StringBuilder)object).append(list.size());
            ((StringBuilder)object).append(" appTargets.size()=");
            ((StringBuilder)object).append(list2.size());
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        for (n = list.size() - 1; n >= 0; --n) {
            if (this.isPackageEnabled(list.get(n).getTargetComponent().getPackageName())) continue;
            list.remove(n);
            if (list2 == null) continue;
            list2.remove(n);
        }
        boolean bl = false;
        for (n = 0; n < object.size(); ++n) {
            Object object2;
            ArrayList<ChooserTarget> arrayList = new ArrayList<ChooserTarget>();
            for (int i = 0; i < list.size(); ++i) {
                if (!((ResolverActivity.DisplayResolveInfo)object.get(n)).getResolvedComponentName().equals(list.get(i).getTargetComponent())) continue;
                ChooserTarget chooserTarget = this.convertToChooserTarget(list.get(i), Math.max(1.0f - (float)i * 0.05f, 0.0f));
                arrayList.add(chooserTarget);
                object2 = this.mDirectShareAppTargetCache;
                if (object2 == null || list2 == null) continue;
                object2.put(chooserTarget, list2.get(i));
            }
            if (arrayList.isEmpty()) continue;
            object2 = Message.obtain();
            ((Message)object2).what = 4;
            ((Message)object2).obj = new ServiceResultInfo((ResolverActivity.DisplayResolveInfo)object.get(n), arrayList, null);
            this.mChooserHandler.sendMessage((Message)object2);
            bl = true;
        }
        if (bl) {
            this.sendShortcutManagerShareTargetResultCompleted();
        }
    }

    private void sendShortcutManagerShareTargetResultCompleted() {
        Message message = Message.obtain();
        message.what = 5;
        this.mChooserHandler.sendMessage(message);
    }

    private boolean shouldDisplayLandscape(int n) {
        boolean bl = n == 2 && !this.isInMultiWindowMode();
        return bl;
    }

    private void updateAlphabeticalList() {
        this.mSortedList.clear();
        this.mSortedList.addAll(this.getDisplayList());
        Collections.sort(this.mSortedList, new AzInfoComparator(this));
    }

    private void updateLayoutWidth(int n, int n2, View object) {
        Object t = ((View)object).findViewById(n);
        if (t != null && ((View)t).getLayoutParams() != null) {
            object = ((View)t).getLayoutParams();
            ((ViewGroup.LayoutParams)object).width = n2;
            ((View)t).setLayoutParams((ViewGroup.LayoutParams)object);
        }
    }

    boolean checkTargetSourceIntent(ResolverActivity.TargetInfo object, Intent intent) {
        object = object.getAllSourceIntents();
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            if (!((Intent)object.get(i)).filterEquals(intent)) continue;
            return true;
        }
        return false;
    }

    @Override
    public ResolverActivity.ResolveListAdapter createAdapter(Context context, List<Intent> list, Intent[] arrintent, List<ResolveInfo> list2, int n, boolean bl) {
        return new ChooserListAdapter(context, list, arrintent, list2, n, bl, this.createListController());
    }

    @VisibleForTesting
    @Override
    protected ResolverListController createListController() {
        Object object = this.getAppPredictorForShareActivitesIfEnabled();
        object = object != null ? new AppPredictionServiceResolverComparator(this, this.getTargetIntent(), this.getReferrerPackageName(), (AppPredictor)object, this.getUser()) : new ResolverRankerServiceResolverComparator(this, this.getTargetIntent(), this.getReferrerPackageName(), null);
        return new ChooserListController(this, this.mPm, this.getTargetIntent(), this.getReferrerPackageName(), this.mLaunchedFromUid, (AbstractResolverComparator)object);
    }

    @Override
    protected PackageMonitor createPackageMonitor() {
        return new PackageMonitor(){

            @Override
            public void onSomePackagesChanged() {
                ChooserActivity.this.mAdapter.handlePackagesChanged();
                ChooserActivity.this.bindProfileView();
            }
        };
    }

    void filterServiceTargets(String string2, List<ChooserTarget> list) {
        if (list == null) {
            return;
        }
        PackageManager packageManager = this.getPackageManager();
        for (int i = list.size() - 1; i >= 0; --i) {
            ChooserTarget chooserTarget = list.get(i);
            Object object = chooserTarget.getComponentName();
            if (string2 != null && string2.equals(((ComponentName)object).getPackageName())) continue;
            boolean bl = false;
            try {
                object = packageManager.getActivityInfo((ComponentName)object, 0);
                if (!((ActivityInfo)object).exported || (object = ((ActivityInfo)object).permission) != null) {
                    bl = true;
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Target ");
                ((StringBuilder)object).append(chooserTarget);
                ((StringBuilder)object).append(" returned by ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" component not found");
                Log.e(TAG, ((StringBuilder)object).toString());
                bl = true;
            }
            if (!bl) continue;
            list.remove(i);
        }
    }

    @Override
    public int getLayoutResource() {
        return 17367121;
    }

    protected MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    @Override
    public Intent getReplacementIntent(ActivityInfo activityInfo, Intent intent) {
        block6 : {
            Intent intent2;
            block5 : {
                Intent intent3 = intent;
                Bundle bundle = this.mReplacementExtras;
                intent2 = intent3;
                if (bundle != null) {
                    bundle = bundle.getBundle(activityInfo.packageName);
                    intent2 = intent3;
                    if (bundle != null) {
                        intent2 = new Intent(intent);
                        intent2.putExtras(bundle);
                    }
                }
                if (activityInfo.name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_PARENT)) break block5;
                intent = intent2;
                if (!activityInfo.name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE)) break block6;
            }
            intent = Intent.createChooser(intent2, this.getIntent().getCharSequenceExtra("android.intent.extra.TITLE"));
            intent.putExtra("android.intent.extra.AUTO_LAUNCH_SINGLE_CHOICE", false);
        }
        return intent;
    }

    @VisibleForTesting
    protected boolean isImageType(String string2) {
        boolean bl = string2 != null && string2.startsWith("image/");
        return bl;
    }

    protected boolean isWorkProfile() {
        return ((UserManager)this.getSystemService("user")).getUserInfo(UserHandle.myUserId()).isManagedProfile();
    }

    public /* synthetic */ void lambda$handleLayoutChange$2$ChooserActivity(int n, int n2) {
        if (this.mResolverDrawerLayout != null && this.mChooserRowAdapter != null) {
            int n3;
            Object object = this.mSystemWindowInsets;
            int n4 = 0;
            int n5 = object != null ? this.mSystemWindowInsets.bottom : 0;
            int n6 = n5;
            int n7 = n3 = this.mChooserRowAdapter.getContentPreviewRowCount() + this.mChooserRowAdapter.getProfileRowCount() + this.mChooserRowAdapter.getServiceTargetRowCount() + this.mChooserRowAdapter.getCallerAndRankedTargetRowCount();
            if (n3 == 0) {
                n7 = this.mChooserRowAdapter.getCount();
            }
            if (n7 == 0) {
                n = this.getResources().getDimensionPixelSize(17105038);
                this.mResolverDrawerLayout.setCollapsibleHeightReserved(n6 + n);
                return;
            }
            int n8 = 0;
            int n9 = Math.min(4, n7);
            n7 = n8;
            for (n3 = 0; n3 < Math.min(n9, this.mAdapterView.getChildCount()); ++n3) {
                object = this.mAdapterView.getChildAt(n3);
                int n10 = ((View)object).getHeight();
                n6 += n10;
                n8 = n7;
                if (((View)object).getTag() != null) {
                    n8 = n7;
                    if (((View)object).getTag() instanceof DirectShareViewHolder) {
                        n8 = n10;
                    }
                }
                n7 = n8;
            }
            n3 = this.getResources().getConfiguration().orientation;
            n8 = 1;
            if (n3 != 1 || this.isInMultiWindowMode()) {
                n8 = 0;
            }
            n3 = n6;
            if (n7 != 0) {
                n3 = n6;
                if (this.isSendAction(this.getTargetIntent())) {
                    n3 = n6;
                    if (n8 != 0) {
                        n3 = (int)((float)n7 / 0.78f);
                        n7 = n4;
                        if (this.mSystemWindowInsets != null) {
                            n7 = this.mSystemWindowInsets.top;
                        }
                        n3 = Math.min(n6, n - n2 - this.mResolverDrawerLayout.getAlwaysShowHeight() - n3 - n7 - n5);
                    }
                }
            }
            this.mResolverDrawerLayout.setCollapsibleHeightReserved(Math.min(n3, n - n2));
            return;
        }
    }

    public /* synthetic */ void lambda$onCreate$0$ChooserActivity(List list) {
        if (!this.isFinishing() && !this.isDestroyed()) {
            if (this.mChooserListAdapter == null) {
                return;
            }
            if (list.isEmpty()) {
                this.queryDirectShareTargets(this.mChooserListAdapter, true);
                return;
            }
            List<ResolverActivity.DisplayResolveInfo> list2 = this.getDisplayResolveInfos(this.mChooserListAdapter);
            ArrayList<ShortcutManager.ShareShortcutInfo> arrayList = new ArrayList<ShortcutManager.ShareShortcutInfo>();
            for (AppTarget appTarget : list) {
                if (appTarget.getShortcutInfo() == null) continue;
                arrayList.add(new ShortcutManager.ShareShortcutInfo(appTarget.getShortcutInfo(), new ComponentName(appTarget.getPackageName(), appTarget.getClassName())));
            }
            this.sendShareShortcutInfoList(arrayList, list2, list);
            return;
        }
    }

    public /* synthetic */ void lambda$queryDirectShareTargets$1$ChooserActivity(IntentFilter intentFilter, List list) {
        this.sendShareShortcutInfoList(((ShortcutManager)this.getSystemService("shortcut")).getShareTargets(intentFilter), list, null);
    }

    @VisibleForTesting
    protected Bitmap loadThumbnail(Uri uri, Size object) {
        if (uri != null && object != null) {
            try {
                object = ImageUtils.loadThumbnail(this.getContentResolver(), uri, (Size)object);
                return object;
            }
            catch (IOException | NullPointerException | SecurityException exception) {
                this.logContentPreviewWarning(uri);
                return null;
            }
        }
        return null;
    }

    @Override
    public void onActivityStarted(ResolverActivity.TargetInfo object) {
        if (this.mChosenComponentSender != null && (object = object.getResolvedComponentName()) != null) {
            object = new Intent().putExtra("android.intent.extra.CHOSEN_COMPONENT", (Parcelable)object);
            try {
                this.mChosenComponentSender.sendIntent(this, -1, (Intent)object, null, null);
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to launch supplied IntentSender to report the chosen component: ");
                stringBuilder.append(sendIntentException);
                Slog.e(TAG, stringBuilder.toString());
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.adjustPreviewWidth(configuration.orientation, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected void onCreate(Bundle var1_1) {
        var2_2 = System.currentTimeMillis();
        this.mIsSuccessfullySelected = false;
        var4_3 = this.getIntent();
        var5_4 = var4_3.getParcelableExtra("android.intent.extra.INTENT");
        if (!(var5_4 instanceof Intent)) {
            var1_1 = new StringBuilder();
            var1_1.append("Target is not an intent: ");
            var1_1.append(var5_4);
            Log.w("ChooserActivity", var1_1.toString());
            this.finish();
            super.onCreate(null);
            return;
        }
        var5_5 = (Intent)var5_4;
        if (var5_5 != null) {
            this.modifyTargetIntent(var5_5);
        }
        var6_8 = var4_3.getParcelableArrayExtra("android.intent.extra.ALTERNATE_INTENTS");
        var7_9 = 1;
        if (var6_8 != null) {
            var8_10 = var5_5 == null ? 1 : 0;
            var10_12 = var9_11 = var6_8.length;
            if (var8_10 != 0) {
                var10_12 = var9_11 - 1;
            }
            var11_13 = new Intent[var10_12];
            for (var10_12 = 0; var10_12 < var6_8.length; ++var10_12) {
                if (!(var6_8[var10_12] instanceof Intent)) {
                    var1_1 = new StringBuilder();
                    var1_1.append("EXTRA_ALTERNATE_INTENTS array entry #");
                    var1_1.append(var10_12);
                    var1_1.append(" is not an Intent: ");
                    var1_1.append(var6_8[var10_12]);
                    Log.w("ChooserActivity", var1_1.toString());
                    this.finish();
                    super.onCreate(null);
                    return;
                }
                var12_14 = (Intent)var6_8[var10_12];
                if (var10_12 == 0 && var5_6 == null) {
                    var5_7 = var12_14;
                    this.modifyTargetIntent((Intent)var5_7);
                    continue;
                }
                var9_11 = var8_10 != 0 ? var10_12 - 1 : var10_12;
                var11_13[var9_11] = var12_14;
                this.modifyTargetIntent((Intent)var12_14);
            }
            this.setAdditionalTargets((Intent[])var11_13);
        }
        this.mReplacementExtras = var4_3.getBundleExtra("android.intent.extra.REPLACEMENT_EXTRAS");
        if (var5_6 == null) ** GOTO lbl60
        if (!this.isSendAction((Intent)var5_6)) {
            var12_14 = var4_3.getCharSequenceExtra("android.intent.extra.TITLE");
        } else {
            Log.w("ChooserActivity", "Ignoring intent's EXTRA_TITLE, deprecated in P. You may wish to set a preview title by using EXTRA_TITLE property of the wrapped EXTRA_INTENT.");
lbl60: // 2 sources:
            var12_14 = null;
        }
        var10_12 = var12_14 == null ? 17039652 : 0;
        var11_13 = var4_3.getParcelableArrayExtra("android.intent.extra.INITIAL_INTENTS");
        if (var11_13 == null) {
            var6_8 = null;
        } else {
            var9_11 = Math.min(((Parcelable[])var11_13).length, 2);
            var6_8 = new Intent[var9_11];
            for (var8_10 = 0; var8_10 < var9_11; ++var8_10) {
                if (!(var11_13[var8_10] instanceof Intent)) {
                    var1_1 = new StringBuilder();
                    var1_1.append("Initial intent #");
                    var1_1.append(var8_10);
                    var1_1.append(" not an Intent: ");
                    var1_1.append(var11_13[var8_10]);
                    Log.w("ChooserActivity", var1_1.toString());
                    this.finish();
                    super.onCreate(null);
                    return;
                }
                var13_15 = (ComponentName[])var11_13[var8_10];
                this.modifyTargetIntent((Intent)var13_15);
                var6_8[var8_10] = var13_15;
            }
        }
        this.mReferrerFillInIntent = new Intent().putExtra("android.intent.extra.REFERRER", this.getReferrer());
        this.mChosenComponentSender = (IntentSender)var4_3.getParcelableExtra("android.intent.extra.CHOSEN_COMPONENT_INTENT_SENDER");
        this.mRefinementIntentSender = (IntentSender)var4_3.getParcelableExtra("android.intent.extra.CHOOSER_REFINEMENT_INTENT_SENDER");
        this.setSafeForwardingMode(true);
        var14_16 = var4_3.getParcelableArrayExtra("android.intent.extra.EXCLUDE_COMPONENTS");
        if (var14_16 != null) {
            var13_15 = new ComponentName[var14_16.length];
            var8_10 = 0;
            do {
                var11_13 = var13_15;
                if (var8_10 >= var14_16.length) break;
                if (!(var14_16[var8_10] instanceof ComponentName)) {
                    var11_13 = new StringBuilder();
                    var11_13.append("Filtered component #");
                    var11_13.append(var8_10);
                    var11_13.append(" not a ComponentName: ");
                    var11_13.append(var14_16[var8_10]);
                    Log.w("ChooserActivity", var11_13.toString());
                    var11_13 = null;
                    break;
                }
                var13_15[var8_10] = (ComponentName)var14_16[var8_10];
                ++var8_10;
            } while (true);
            this.mFilteredComponentNames = var11_13;
        }
        if ((var14_16 = var4_3.getParcelableArrayExtra("android.intent.extra.CHOOSER_TARGETS")) != null) {
            var9_11 = Math.min(var14_16.length, 2);
            var13_15 = new ChooserTarget[var9_11];
            var8_10 = 0;
            do {
                var11_13 = var13_15;
                if (var8_10 >= var9_11) break;
                if (!(var14_16[var8_10] instanceof ChooserTarget)) {
                    var11_13 = new StringBuilder();
                    var11_13.append("Chooser target #");
                    var11_13.append(var8_10);
                    var11_13.append(" not a ChooserTarget: ");
                    var11_13.append(var14_16[var8_10]);
                    Log.w("ChooserActivity", var11_13.toString());
                    var11_13 = null;
                    break;
                }
                var13_15[var8_10] = (ChooserTarget)var14_16[var8_10];
                ++var8_10;
            } while (true);
            this.mCallerChooserTargets = var11_13;
        }
        this.setRetainInOnStop(var4_3.getBooleanExtra("com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP", false));
        super.onCreate((Bundle)var1_1, (Intent)var5_6, (CharSequence)var12_14, var10_12, (Intent[])var6_8, null, false);
        var15_17 = this.mChooserShownTime = System.currentTimeMillis();
        var12_14 = this.getMetricsLogger();
        var1_1 = new LogMaker(214);
        var10_12 = this.isWorkProfile() != false ? 2 : var7_9;
        var12_14.write(var1_1.setSubtype(var10_12).addTaggedData(1649, var5_6.getType()).addTaggedData(1653, var15_17 - var2_2));
        var1_1 = this.getAppPredictorForDirectShareIfEnabled();
        if (var1_1 != null) {
            this.mDirectShareAppTargetCache = new HashMap<ChooserTarget, AppTarget>();
            this.mAppPredictorCallback = new _$$Lambda$ChooserActivity$DtVuD6Mjmx25X89cZXV33qf48uk(this);
            var1_1.registerPredictionUpdates(this.getMainExecutor(), this.mAppPredictorCallback);
        }
        this.mChooserRowServiceSpacing = this.getResources().getDimensionPixelSize(17105043);
        if (this.mResolverDrawerLayout == null) return;
        this.mResolverDrawerLayout.addOnLayoutChangeListener(new _$$Lambda$ChooserActivity$mSpb8JdVEdN3REmKTSrORHIDnIo(this));
        if (this.isSendAction((Intent)var5_6)) {
            this.mResolverDrawerLayout.setOnScrollChangeListener(new _$$Lambda$ChooserActivity$n2FimsQN3_RG5vs7T6aVc1Pt9v0(this));
        }
        var1_1 = this.mResolverDrawerLayout.findViewById(16908806);
        var17_18 = var1_1.getElevation();
        var18_19 = this.getResources().getDimensionPixelSize(17105037);
        this.mAdapterView.setOnScrollListener(new AbsListView.OnScrollListener((View)var1_1, var18_19, var17_18){
            final /* synthetic */ View val$chooserHeader;
            final /* synthetic */ float val$chooserHeaderScrollElevation;
            final /* synthetic */ float val$defaultElevation;
            {
                this.val$chooserHeader = view;
                this.val$chooserHeaderScrollElevation = f;
                this.val$defaultElevation = f2;
            }

            @Override
            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
                if (absListView.getChildCount() > 0 && (n > 0 || absListView.getChildAt(0).getTop() < 0)) {
                    this.val$chooserHeader.setElevation(this.val$chooserHeaderScrollElevation);
                    return;
                }
                this.val$chooserHeader.setElevation(this.val$defaultElevation);
            }

            @Override
            public void onScrollStateChanged(AbsListView absListView, int n) {
            }
        });
        this.mResolverDrawerLayout.setOnCollapsedChangedListener(new ResolverDrawerLayout.OnCollapsedChangedListener(){
            private boolean mWrittenOnce = false;

            @Override
            public void onCollapsedChanged(boolean bl) {
                if (!bl && !this.mWrittenOnce) {
                    ChooserActivity.this.incrementNumSheetExpansions();
                    this.mWrittenOnce = true;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Object object = this.mRefinementResultReceiver;
        if (object != null) {
            ((RefinementResultReceiver)object).destroy();
            this.mRefinementResultReceiver = null;
        }
        this.unbindRemainingServices();
        this.mChooserHandler.removeAllMessages();
        object = this.mPreviewCoord;
        if (object != null) {
            ((ContentPreviewCoordinator)object).cancelLoads();
        }
        if ((object = this.mAppPredictor) != null) {
            ((AppPredictor)object).unregisterPredictionUpdates(this.mAppPredictorCallback);
            this.mAppPredictor.destroy();
        }
    }

    @Override
    public void onPrepareAdapterView(AbsListView absListView, ResolverActivity.ResolveListAdapter arrchooserTarget) {
        absListView = absListView instanceof ListView ? (ListView)absListView : null;
        this.mChooserListAdapter = (ChooserListAdapter)arrchooserTarget;
        arrchooserTarget = this.mCallerChooserTargets;
        if (arrchooserTarget != null && arrchooserTarget.length > 0) {
            this.mChooserListAdapter.addServiceResults(null, Lists.newArrayList(arrchooserTarget), false);
        }
        this.mChooserRowAdapter = new ChooserRowAdapter(this.mChooserListAdapter);
        if (absListView != null) {
            ((ListView)absListView).setItemsCanFocus(true);
        }
    }

    void onRefinementCanceled() {
        RefinementResultReceiver refinementResultReceiver = this.mRefinementResultReceiver;
        if (refinementResultReceiver != null) {
            refinementResultReceiver.destroy();
            this.mRefinementResultReceiver = null;
        }
        this.finish();
    }

    void onRefinementResult(ResolverActivity.TargetInfo targetInfo, Intent intent) {
        Object object = this.mRefinementResultReceiver;
        if (object != null) {
            ((RefinementResultReceiver)object).destroy();
            this.mRefinementResultReceiver = null;
        }
        if (targetInfo == null) {
            Log.e(TAG, "Refinement result intent did not match any known targets; canceling");
        } else if (!this.checkTargetSourceIntent(targetInfo, intent)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("onRefinementResult: Selected target ");
            ((StringBuilder)object).append(targetInfo);
            ((StringBuilder)object).append(" cannot match refined source intent ");
            ((StringBuilder)object).append(intent);
            Log.e(TAG, ((StringBuilder)object).toString());
        } else if (super.onTargetSelected(targetInfo = targetInfo.cloneFilledIn(intent, 0), false)) {
            this.updateModelAndChooserCounts(targetInfo);
            this.finish();
            return;
        }
        this.onRefinementCanceled();
    }

    @Override
    public void onSetupVoiceInteraction() {
    }

    @Override
    protected boolean onTargetSelected(ResolverActivity.TargetInfo targetInfo, boolean bl) {
        if (this.mRefinementIntentSender != null) {
            Intent intent = new Intent();
            List<Intent> list = targetInfo.getAllSourceIntents();
            if (!list.isEmpty()) {
                intent.putExtra("android.intent.extra.INTENT", list.get(0));
                if (list.size() > 1) {
                    Parcelable[] arrparcelable = new Intent[list.size() - 1];
                    int n = list.size();
                    for (int i = 1; i < n; ++i) {
                        arrparcelable[i - 1] = (Intent)list.get(i);
                    }
                    intent.putExtra("android.intent.extra.ALTERNATE_INTENTS", arrparcelable);
                }
                if ((list = this.mRefinementResultReceiver) != null) {
                    ((RefinementResultReceiver)((Object)list)).destroy();
                }
                this.mRefinementResultReceiver = new RefinementResultReceiver(this, targetInfo, null);
                intent.putExtra("android.intent.extra.RESULT_RECEIVER", this.mRefinementResultReceiver);
                try {
                    this.mRefinementIntentSender.sendIntent(this, 0, intent, null, null);
                    return false;
                }
                catch (IntentSender.SendIntentException sendIntentException) {
                    Log.e(TAG, "Refinement IntentSender failed to send", sendIntentException);
                }
            }
        }
        this.updateModelAndChooserCounts(targetInfo);
        return super.onTargetSelected(targetInfo, bl);
    }

    @VisibleForTesting
    public Cursor queryResolver(ContentResolver contentResolver, Uri uri) {
        return contentResolver.query(uri, null, null, null, null);
    }

    void queryTargetServices(ChooserListAdapter chooserListAdapter) {
        String string2 = "android.permission.BIND_CHOOSER_TARGET_SERVICE";
        this.mQueriedTargetServicesTimeMs = System.currentTimeMillis();
        PackageManager packageManager = this.getPackageManager();
        ShortcutManager shortcutManager = this.getSystemService(ShortcutManager.class);
        int n = 0;
        int n2 = chooserListAdapter.getDisplayResolveInfoCount();
        int n3 = 0;
        do {
            block5 : {
                int n4;
                block7 : {
                    block6 : {
                        Object object = chooserListAdapter;
                        if (n >= n2) break;
                        Object object2 = ((ResolverActivity.ResolveListAdapter)object).getDisplayResolveInfo(n);
                        if (((ResolverActivity.ResolveListAdapter)object).getScore((ResolverActivity.DisplayResolveInfo)object2) == 0.0f) break block5;
                        Parcelable parcelable = object2.getResolveInfo().activityInfo;
                        if (shortcutManager.hasShareTargets(parcelable.packageName)) break block5;
                        object = parcelable.metaData;
                        object = object != null ? this.convertServiceName(parcelable.packageName, ((BaseBundle)object).getString("android.service.chooser.chooser_target_service")) : null;
                        if (object == null) break block6;
                        if (this.mServicesRequested.contains(object = new ComponentName(parcelable.packageName, (String)object))) break block5;
                        this.mServicesRequested.add((ComponentName)object);
                        parcelable = new Intent("android.service.chooser.ChooserTargetService").setComponent((ComponentName)object);
                        try {
                            if (!string2.equals(packageManager.getServiceInfo((ComponentName)object, (int)0).permission)) {
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("ChooserTargetService ");
                                ((StringBuilder)object2).append(object);
                                ((StringBuilder)object2).append(" does not require permission ");
                                ((StringBuilder)object2).append(string2);
                                ((StringBuilder)object2).append(" - this service will not be queried for ChooserTargets. add android:permission=\"");
                                ((StringBuilder)object2).append(string2);
                                ((StringBuilder)object2).append("\" to the <service> tag for ");
                                ((StringBuilder)object2).append(object);
                                ((StringBuilder)object2).append(" in the manifest.");
                                Log.w(TAG, ((StringBuilder)object2).toString());
                            }
                            object = new ChooserTargetServiceConnection(this, (ResolverActivity.DisplayResolveInfo)object2);
                            n4 = n3;
                        }
                        catch (PackageManager.NameNotFoundException nameNotFoundException) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Could not look up service ");
                            ((StringBuilder)object2).append(object);
                            ((StringBuilder)object2).append("; component name not found");
                            Log.e(TAG, ((StringBuilder)object2).toString());
                        }
                        if (this.bindServiceAsUser((Intent)parcelable, (ServiceConnection)object, 5, Process.myUserHandle())) {
                            this.mServiceConnections.add((ChooserTargetServiceConnection)object);
                            n4 = n3 + 1;
                        }
                        break block7;
                        break block5;
                    }
                    n4 = n3;
                }
                n3 = n4;
                if (n4 >= 5) break;
            }
            ++n;
        } while (true);
        this.mChooserHandler.restartServiceRequestTimer();
    }

    @Override
    public boolean shouldAutoLaunchSingleChoice(ResolverActivity.TargetInfo targetInfo) {
        if (!super.shouldAutoLaunchSingleChoice(targetInfo)) {
            return false;
        }
        return this.getIntent().getBooleanExtra("android.intent.extra.AUTO_LAUNCH_SINGLE_CHOICE", true);
    }

    @Override
    public boolean shouldGetActivityMetadata() {
        return true;
    }

    @Override
    public void showTargetDetails(ResolveInfo resolveInfo) {
        if (resolveInfo == null) {
            return;
        }
        ComponentName componentName = resolveInfo.activityInfo.getComponentName();
        new ResolverTargetActionsDialogFragment(resolveInfo.loadLabel(this.getPackageManager()), componentName).show(this.getFragmentManager(), TARGET_DETAILS_FRAGMENT_TAG);
    }

    @Override
    public void startSelected(int n, boolean bl, boolean bl2) {
        block7 : {
            int n2;
            long l;
            Object object;
            int n3;
            long l2;
            Object object2;
            int n4;
            block10 : {
                int n5;
                block8 : {
                    ChooserTarget[] arrchooserTarget;
                    block9 : {
                        arrchooserTarget = this.mChooserListAdapter.targetInfoForPosition(n, bl2);
                        if (arrchooserTarget != null && arrchooserTarget instanceof NotSelectableTargetInfo) {
                            return;
                        }
                        l2 = System.currentTimeMillis();
                        l = this.mChooserShownTime;
                        super.startSelected(n, bl, bl2);
                        object2 = this.mChooserListAdapter;
                        if (object2 == null) break block7;
                        n2 = 0;
                        n5 = n;
                        n3 = -1;
                        n4 = 0;
                        object = null;
                        if ((n = ((ChooserListAdapter)object2).getPositionTargetType(n)) == 0) break block8;
                        if (n == 1) break block9;
                        if (n == 2) break block8;
                        if (n != 3) {
                            n = n2;
                            n2 = n5;
                        } else {
                            n2 = -1;
                            n = 217;
                        }
                        break block10;
                    }
                    int n6 = 216;
                    object2 = ((ChooserTargetInfo)this.mChooserListAdapter.mServiceTargets.get(n5)).getChooserTarget();
                    HashedStringCache hashedStringCache = HashedStringCache.getInstance();
                    object = new StringBuilder();
                    ((StringBuilder)object).append(((ChooserTarget)object2).getComponentName().getPackageName());
                    ((StringBuilder)object).append(((ChooserTarget)object2).getTitle().toString());
                    object2 = hashedStringCache.hashString(this, TAG, ((StringBuilder)object).toString(), this.mMaxHashSaltDays);
                    int n7 = this.getRankedPosition((SelectableTargetInfo)arrchooserTarget);
                    arrchooserTarget = this.mCallerChooserTargets;
                    n = n6;
                    n2 = n5;
                    n3 = n7;
                    object = object2;
                    if (arrchooserTarget != null) {
                        n4 = arrchooserTarget.length;
                        n = n6;
                        n2 = n5;
                        n3 = n7;
                        object = object2;
                    }
                    break block10;
                }
                n = 215;
                n2 = n5 - this.mChooserListAdapter.getSelectableServiceTargetCount();
                n4 = this.mChooserListAdapter.getCallerTargetCount();
            }
            if (n != 0) {
                object2 = new LogMaker(n).setSubtype(n2);
                if (object != null) {
                    ((LogMaker)object2).addTaggedData(1704, ((HashedStringCache.HashResult)object).hashedString);
                    ((LogMaker)object2).addTaggedData(1705, ((HashedStringCache.HashResult)object).saltGeneration);
                    ((LogMaker)object2).addTaggedData(1087, n3);
                }
                ((LogMaker)object2).addTaggedData(1086, n4);
                this.getMetricsLogger().write((LogMaker)object2);
            }
            if (this.mIsSuccessfullySelected) {
                MetricsLogger.histogram(null, "user_selection_cost_for_smart_sharing", (int)(l2 - l));
                MetricsLogger.histogram(null, "app_position_for_smart_sharing", n2);
            }
        }
    }

    void unbindRemainingServices() {
        int n = this.mServiceConnections.size();
        for (int i = 0; i < n; ++i) {
            ChooserTargetServiceConnection chooserTargetServiceConnection = this.mServiceConnections.get(i);
            this.unbindService(chooserTargetServiceConnection);
            chooserTargetServiceConnection.destroy();
        }
        this.mServicesRequested.clear();
        this.mServiceConnections.clear();
    }

    void updateModelAndChooserCounts(ResolverActivity.TargetInfo targetInfo) {
        if (targetInfo != null) {
            this.sendClickToAppPredictor(targetInfo);
            ResolveInfo resolveInfo = targetInfo.getResolveInfo();
            Intent intent = this.getTargetIntent();
            if (resolveInfo != null && resolveInfo.activityInfo != null && intent != null && this.mAdapter != null) {
                this.mAdapter.updateModel(targetInfo.getResolvedComponentName());
                this.mAdapter.updateChooserCounts(resolveInfo.activityInfo.packageName, this.getUserId(), intent.getAction());
            }
        }
        this.mIsSuccessfullySelected = true;
    }

    class AzInfoComparator
    implements Comparator<ResolverActivity.DisplayResolveInfo> {
        Collator mCollator;

        AzInfoComparator(Context context) {
            this.mCollator = Collator.getInstance(context.getResources().getConfiguration().locale);
        }

        @Override
        public int compare(ResolverActivity.DisplayResolveInfo displayResolveInfo, ResolverActivity.DisplayResolveInfo displayResolveInfo2) {
            return this.mCollator.compare(displayResolveInfo.getDisplayLabel(), displayResolveInfo2.getDisplayLabel());
        }
    }

    static class BaseChooserTargetComparator
    implements Comparator<ChooserTarget> {
        BaseChooserTargetComparator() {
        }

        @Override
        public int compare(ChooserTarget chooserTarget, ChooserTarget chooserTarget2) {
            return (int)Math.signum(chooserTarget2.getScore() - chooserTarget.getScore());
        }
    }

    private class ChooserHandler
    extends Handler {
        private static final int CHOOSER_TARGET_SERVICE_RESULT = 1;
        private static final int CHOOSER_TARGET_SERVICE_WATCHDOG_MAX_TIMEOUT = 3;
        private static final int CHOOSER_TARGET_SERVICE_WATCHDOG_MIN_TIMEOUT = 2;
        private static final int LIST_VIEW_UPDATE_MESSAGE = 6;
        private static final int SHORTCUT_MANAGER_SHARE_TARGET_RESULT = 4;
        private static final int SHORTCUT_MANAGER_SHARE_TARGET_RESULT_COMPLETED = 5;
        private static final int WATCHDOG_TIMEOUT_MAX_MILLIS = 10000;
        private static final int WATCHDOG_TIMEOUT_MIN_MILLIS = 3000;
        private boolean mMinTimeoutPassed = false;

        private ChooserHandler() {
        }

        private void maybeStopServiceRequestTimer() {
            if (this.mMinTimeoutPassed && ChooserActivity.this.mServiceConnections.isEmpty()) {
                ChooserActivity.this.logDirectShareTargetReceived(1719);
                ChooserActivity.this.sendVoiceChoicesIfNeeded();
                ChooserActivity.this.mChooserListAdapter.completeServiceTargetLoading();
            }
        }

        private void removeAllMessages() {
            this.removeMessages(6);
            this.removeMessages(2);
            this.removeMessages(3);
            this.removeMessages(1);
            this.removeMessages(4);
            this.removeMessages(5);
        }

        private void restartServiceRequestTimer() {
            this.mMinTimeoutPassed = false;
            this.removeMessages(2);
            this.removeMessages(3);
            this.sendEmptyMessageDelayed(2, 3000L);
            this.sendEmptyMessageDelayed(3, 10000L);
        }

        @Override
        public void handleMessage(Message object) {
            if (ChooserActivity.this.mChooserListAdapter != null && !ChooserActivity.this.isDestroyed()) {
                switch (((Message)object).what) {
                    default: {
                        super.handleMessage((Message)object);
                        break;
                    }
                    case 6: {
                        ChooserActivity.this.mChooserListAdapter.refreshListView();
                        break;
                    }
                    case 5: {
                        ChooserActivity.this.logDirectShareTargetReceived(1718);
                        ChooserActivity.this.sendVoiceChoicesIfNeeded();
                        break;
                    }
                    case 4: {
                        object = (ServiceResultInfo)((Message)object).obj;
                        if (((ServiceResultInfo)object).resultTargets == null) break;
                        ChooserActivity.this.mChooserListAdapter.addServiceResults(((ServiceResultInfo)object).originalTarget, ((ServiceResultInfo)object).resultTargets, true);
                        break;
                    }
                    case 3: {
                        ChooserActivity.this.unbindRemainingServices();
                        this.maybeStopServiceRequestTimer();
                        break;
                    }
                    case 2: {
                        this.mMinTimeoutPassed = true;
                        this.maybeStopServiceRequestTimer();
                        break;
                    }
                    case 1: {
                        ServiceResultInfo serviceResultInfo = (ServiceResultInfo)((Message)object).obj;
                        if (!ChooserActivity.this.mServiceConnections.contains(serviceResultInfo.connection)) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("ChooserTargetServiceConnection ");
                            ((StringBuilder)object).append(serviceResultInfo.connection);
                            ((StringBuilder)object).append(" returned after being removed from active connections. Have you considered returning results faster?");
                            Log.w(ChooserActivity.TAG, ((StringBuilder)object).toString());
                            break;
                        }
                        if (serviceResultInfo.resultTargets != null) {
                            ChooserActivity.this.mChooserListAdapter.addServiceResults(serviceResultInfo.originalTarget, serviceResultInfo.resultTargets, false);
                        }
                        ChooserActivity.this.unbindService(serviceResultInfo.connection);
                        serviceResultInfo.connection.destroy();
                        ChooserActivity.this.mServiceConnections.remove(serviceResultInfo.connection);
                        this.maybeStopServiceRequestTimer();
                    }
                }
                return;
            }
        }
    }

    public class ChooserListAdapter
    extends ResolverActivity.ResolveListAdapter {
        private static final int MAX_CHOOSER_TARGETS_PER_APP = 2;
        private static final int MAX_SERVICE_TARGETS = 8;
        private static final int MAX_SUGGESTED_APP_TARGETS = 4;
        public static final int TARGET_BAD = -1;
        public static final int TARGET_CALLER = 0;
        public static final int TARGET_SERVICE = 1;
        public static final int TARGET_STANDARD = 2;
        public static final int TARGET_STANDARD_AZ = 3;
        private final BaseChooserTargetComparator mBaseTargetComparator;
        private final List<ResolverActivity.TargetInfo> mCallerTargets;
        private final int mMaxShortcutTargetsPerApp;
        private int mNumShortcutResults;
        private ChooserTargetInfo mPlaceHolderTargetInfo;
        private final List<ChooserTargetInfo> mServiceTargets;

        public ChooserListAdapter(Context object, List<Intent> object2, Intent[] arrintent, List<ResolveInfo> object3, int n, boolean bl, ResolverListController object4) {
            super((Context)object, (List<Intent>)object2, null, (List<ResolveInfo>)object3, n, bl, (ResolverListController)object4);
            this.mMaxShortcutTargetsPerApp = ChooserActivity.this.getResources().getInteger(17694834);
            this.mNumShortcutResults = 0;
            this.mPlaceHolderTargetInfo = new PlaceHolderTargetInfo();
            this.mServiceTargets = new ArrayList<ChooserTargetInfo>();
            this.mCallerTargets = new ArrayList<ResolverActivity.TargetInfo>();
            this.mBaseTargetComparator = new BaseChooserTargetComparator();
            this.createPlaceHolders();
            if (arrintent != null) {
                PackageManager packageManager = ChooserActivity.this.getPackageManager();
                for (n = 0; n < arrintent.length; ++n) {
                    Intent intent = arrintent[n];
                    if (intent == null) continue;
                    object = null;
                    object3 = null;
                    object2 = null;
                    object4 = null;
                    if (intent.getComponent() != null) {
                        object = object3;
                        object2 = object4;
                        object4 = packageManager.getActivityInfo(intent.getComponent(), 0);
                        object = object3;
                        object2 = object4;
                        object = object3;
                        object2 = object4;
                        ResolveInfo resolveInfo = new ResolveInfo();
                        object = object3 = resolveInfo;
                        object2 = object4;
                        try {
                            ((ResolveInfo)object3).activityInfo = object4;
                            object = object3;
                            object2 = object4;
                        }
                        catch (PackageManager.NameNotFoundException nameNotFoundException) {
                            // empty catch block
                        }
                    }
                    if (object2 == null) {
                        object2 = packageManager.resolveActivity(intent, 65536);
                        object = object2 != null ? ((ResolveInfo)object2).activityInfo : null;
                        object3 = object;
                        object = object2;
                    } else {
                        object3 = object2;
                    }
                    if (object3 == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("No activity found for ");
                        ((StringBuilder)object).append(intent);
                        Log.w(ChooserActivity.TAG, ((StringBuilder)object).toString());
                        continue;
                    }
                    object2 = (UserManager)ChooserActivity.this.getSystemService("user");
                    if (intent instanceof LabeledIntent) {
                        object3 = (LabeledIntent)intent;
                        ((ResolveInfo)object).resolvePackageName = ((LabeledIntent)object3).getSourcePackage();
                        ((ResolveInfo)object).labelRes = ((LabeledIntent)object3).getLabelResource();
                        ((ResolveInfo)object).nonLocalizedLabel = ((LabeledIntent)object3).getNonLocalizedLabel();
                        ((ResolveInfo)object).iconResourceId = ((ResolveInfo)object).icon = ((LabeledIntent)object3).getIconResource();
                    }
                    if (((UserManager)object2).isManagedProfile()) {
                        ((ResolveInfo)object).noResourceId = true;
                        ((ResolveInfo)object).icon = 0;
                    }
                    object2 = ChooserActivity.this.makePresentationGetter((ResolveInfo)object);
                    this.mCallerTargets.add(new ResolverActivity.DisplayResolveInfo(intent, (ResolveInfo)object, ((ResolverActivity.ActivityInfoPresentationGetter)object2).getLabel(), ((ResolverActivity.ActivityInfoPresentationGetter)object2).getSubLabel(), intent));
                }
            }
        }

        private void createPlaceHolders() {
            this.mNumShortcutResults = 0;
            this.mServiceTargets.clear();
            for (int i = 0; i < 8; ++i) {
                this.mServiceTargets.add(this.mPlaceHolderTargetInfo);
            }
        }

        private float getBaseScore(ResolverActivity.DisplayResolveInfo displayResolveInfo, boolean bl) {
            if (displayResolveInfo == null) {
                return 900.0f;
            }
            if (bl && ChooserActivity.this.getAppPredictorForDirectShareIfEnabled() != null) {
                return 90.0f;
            }
            float f = super.getScore(displayResolveInfo);
            if (bl) {
                return 90.0f * f;
            }
            return f;
        }

        private int getMaxRankedTargets() {
            int n = ChooserActivity.this.mChooserRowAdapter == null ? 4 : ChooserActivity.this.mChooserRowAdapter.getMaxTargetsPerRow();
            return n;
        }

        private int getNumShortcutResults() {
            return this.mNumShortcutResults;
        }

        private boolean insertServiceTarget(ChooserTargetInfo chooserTargetInfo) {
            if (this.mServiceTargets.size() == 1 && this.mServiceTargets.get(0) instanceof EmptyTargetInfo) {
                return false;
            }
            Object object = this.mServiceTargets.iterator();
            while (object.hasNext()) {
                if (!chooserTargetInfo.isSimilar(object.next())) continue;
                return false;
            }
            int n = this.mServiceTargets.size();
            float f = chooserTargetInfo.getModifiedScore();
            for (int i = 0; i < Math.min(n, 8); ++i) {
                object = this.mServiceTargets.get(i);
                if (object == null) {
                    this.mServiceTargets.set(i, chooserTargetInfo);
                    return true;
                }
                if (!(f > object.getModifiedScore())) continue;
                this.mServiceTargets.add(i, chooserTargetInfo);
                return true;
            }
            if (n < 8) {
                this.mServiceTargets.add(chooserTargetInfo);
                return true;
            }
            return false;
        }

        static /* synthetic */ boolean lambda$completeServiceTargetLoading$0(ChooserTargetInfo chooserTargetInfo) {
            return chooserTargetInfo instanceof PlaceHolderTargetInfo;
        }

        private void refreshListView() {
            if (ChooserActivity.this.mListViewDataChanged) {
                super.notifyDataSetChanged();
            }
            ChooserActivity.this.mListViewDataChanged = false;
        }

        public void addServiceResults(ResolverActivity.DisplayResolveInfo displayResolveInfo, List<ChooserTarget> list, boolean bl) {
            if (list.size() == 0) {
                return;
            }
            float f = this.getBaseScore(displayResolveInfo, bl);
            Collections.sort(list, this.mBaseTargetComparator);
            int n = bl ? this.mMaxShortcutTargetsPerApp : 2;
            float f2 = 0.0f;
            boolean bl2 = false;
            int n2 = 0;
            int n3 = Math.min(list.size(), n);
            for (n = n2; n < n3; ++n) {
                float f3;
                boolean bl3;
                ChooserTarget chooserTarget = list.get(n);
                float f4 = f3 = chooserTarget.getScore() * f;
                if (n > 0) {
                    f4 = f3;
                    if (f3 >= f2) {
                        f4 = f2 * 0.95f;
                    }
                }
                if ((bl3 = this.insertServiceTarget(new SelectableTargetInfo(displayResolveInfo, chooserTarget, f4))) && bl) {
                    ++this.mNumShortcutResults;
                }
                bl2 |= bl3;
                f2 = f4;
            }
            if (bl2) {
                this.notifyDataSetChanged();
            }
        }

        public void completeServiceTargetLoading() {
            this.mServiceTargets.removeIf((Predicate<ChooserTargetInfo>)_$$Lambda$ChooserActivity$ChooserListAdapter$0o9wjP10lRaguh_ZLgVIZcGRo0w.INSTANCE);
            if (this.mServiceTargets.isEmpty()) {
                this.mServiceTargets.add(new EmptyTargetInfo());
            }
            this.notifyDataSetChanged();
        }

        int getAlphaTargetCount() {
            int n = super.getCount();
            if (n <= this.getMaxRankedTargets()) {
                n = 0;
            }
            return n;
        }

        public int getCallerTargetCount() {
            return Math.min(this.mCallerTargets.size(), 4);
        }

        @Override
        public int getCount() {
            return this.getRankedTargetCount() + this.getAlphaTargetCount() + this.getSelectableServiceTargetCount() + this.getCallerTargetCount();
        }

        @Override
        public ResolverActivity.TargetInfo getItem(int n) {
            return this.targetInfoForPosition(n, true);
        }

        public int getPositionTargetType(int n) {
            int n2 = this.getServiceTargetCount();
            if (n < n2) {
                return 1;
            }
            int n3 = 0 + n2;
            n2 = this.getCallerTargetCount();
            if (n - n3 < n2) {
                return 0;
            }
            n3 += n2;
            n2 = this.getRankedTargetCount();
            if (n - n3 < n2) {
                return 2;
            }
            if (n - (n3 + n2) < this.getAlphaTargetCount()) {
                return 3;
            }
            return -1;
        }

        int getRankedTargetCount() {
            return Math.min(this.getMaxRankedTargets() - this.getCallerTargetCount(), super.getCount());
        }

        public int getSelectableServiceTargetCount() {
            int n = 0;
            Iterator<ChooserTargetInfo> iterator = this.mServiceTargets.iterator();
            while (iterator.hasNext()) {
                int n2 = n;
                if (iterator.next() instanceof SelectableTargetInfo) {
                    n2 = n + 1;
                }
                n = n2;
            }
            return n;
        }

        public int getServiceTargetCount() {
            ChooserActivity chooserActivity = ChooserActivity.this;
            if (chooserActivity.isSendAction(chooserActivity.getTargetIntent()) && !ActivityManager.isLowRamDeviceStatic()) {
                return Math.min(this.mServiceTargets.size(), 8);
            }
            return 0;
        }

        @Override
        public int getUnfilteredCount() {
            int n;
            int n2 = n = super.getUnfilteredCount();
            if (n > this.getMaxRankedTargets()) {
                n2 = n + this.getMaxRankedTargets();
            }
            return this.getSelectableServiceTargetCount() + n2 + this.getCallerTargetCount();
        }

        @Override
        public void handlePackagesChanged() {
            this.createPlaceHolders();
            ChooserActivity.this.mServicesRequested.clear();
            this.notifyDataSetChanged();
            super.handlePackagesChanged();
        }

        @Override
        public void notifyDataSetChanged() {
            if (!ChooserActivity.this.mListViewDataChanged) {
                ChooserActivity.this.mChooserHandler.sendEmptyMessageDelayed(6, 250L);
                ChooserActivity.this.mListViewDataChanged = true;
            }
        }

        @Override
        protected void onBindView(View object, ResolverActivity.TargetInfo targetInfo) {
            super.onBindView((View)object, targetInfo);
            object = (ResolverActivity.ViewHolder)((View)object).getTag();
            if (targetInfo instanceof PlaceHolderTargetInfo) {
                int n = ChooserActivity.this.getResources().getDimensionPixelSize(17105033);
                ((ResolverActivity.ViewHolder)object).text.setMaxWidth(n);
                ((ResolverActivity.ViewHolder)object).text.setBackground(ChooserActivity.this.getResources().getDrawable(17302104, ChooserActivity.this.getTheme()));
                ((ResolverActivity.ViewHolder)object).itemView.setBackground(null);
            } else {
                ((ResolverActivity.ViewHolder)object).text.setMaxWidth(Integer.MAX_VALUE);
                ((ResolverActivity.ViewHolder)object).text.setBackground(null);
                ((ResolverActivity.ViewHolder)object).itemView.setBackground(((ResolverActivity.ViewHolder)object).defaultItemViewBackground);
            }
        }

        @Override
        public View onCreateView(ViewGroup viewGroup) {
            return this.mInflater.inflate(17367258, viewGroup, false);
        }

        @Override
        public void onListRebuilt() {
            ChooserActivity.this.updateAlphabeticalList();
            if (ActivityManager.isLowRamDeviceStatic()) {
                return;
            }
            ChooserActivity.this.queryDirectShareTargets(this, false);
            ChooserActivity.this.queryTargetServices(this);
        }

        @Override
        public boolean shouldGetResolvedFilter() {
            return true;
        }

        @Override
        public ResolverActivity.TargetInfo targetInfoForPosition(int n, boolean bl) {
            int n2 = bl ? this.getServiceTargetCount() : this.getSelectableServiceTargetCount();
            if (n < n2) {
                return this.mServiceTargets.get(n);
            }
            int n3 = 0 + n2;
            n2 = this.getCallerTargetCount();
            if (n - n3 < n2) {
                return this.mCallerTargets.get(n - n3);
            }
            n3 += n2;
            n2 = this.getRankedTargetCount();
            if (n - n3 < n2) {
                ResolverActivity.TargetInfo targetInfo = bl ? super.getItem(n - n3) : this.getDisplayResolveInfo(n - n3);
                return targetInfo;
            }
            if (n - (n2 = n3 + n2) < this.getAlphaTargetCount() && !ChooserActivity.this.mSortedList.isEmpty()) {
                return (ResolverActivity.TargetInfo)ChooserActivity.this.mSortedList.get(n - n2);
            }
            return null;
        }
    }

    public class ChooserListController
    extends ResolverListController {
        public ChooserListController(Context context, PackageManager packageManager, Intent intent, String string2, int n, AbstractResolverComparator abstractResolverComparator) {
            super(context, packageManager, intent, string2, n, abstractResolverComparator);
        }

        @Override
        boolean isComponentFiltered(ComponentName componentName) {
            if (ChooserActivity.this.mFilteredComponentNames == null) {
                return false;
            }
            ComponentName[] arrcomponentName = ChooserActivity.this.mFilteredComponentNames;
            int n = arrcomponentName.length;
            for (int i = 0; i < n; ++i) {
                if (!componentName.equals(arrcomponentName[i])) continue;
                return true;
            }
            return false;
        }
    }

    class ChooserRowAdapter
    extends BaseAdapter {
        private static final int MAX_TARGETS_PER_ROW_LANDSCAPE = 8;
        private static final int MAX_TARGETS_PER_ROW_PORTRAIT = 4;
        private static final int NUM_EXPANSIONS_TO_HIDE_AZ_LABEL = 20;
        private static final int VIEW_TYPE_AZ_LABEL = 4;
        private static final int VIEW_TYPE_CONTENT_PREVIEW = 2;
        private static final int VIEW_TYPE_DIRECT_SHARE = 0;
        private static final int VIEW_TYPE_NORMAL = 1;
        private static final int VIEW_TYPE_PROFILE = 3;
        private ChooserListAdapter mChooserListAdapter;
        private int mChooserTargetWidth;
        private DirectShareViewHolder mDirectShareViewHolder;
        private boolean mHideContentPreview;
        private final LayoutInflater mLayoutInflater;
        private boolean mLayoutRequested;
        private boolean mShowAzLabelIfPoss;

        public ChooserRowAdapter(ChooserListAdapter chooserListAdapter) {
            boolean bl = false;
            this.mChooserTargetWidth = 0;
            this.mHideContentPreview = false;
            this.mLayoutRequested = false;
            this.mChooserListAdapter = chooserListAdapter;
            this.mLayoutInflater = LayoutInflater.from(ChooserActivity.this);
            if (ChooserActivity.this.getNumSheetExpansions() < 20) {
                bl = true;
            }
            this.mShowAzLabelIfPoss = bl;
            chooserListAdapter.registerDataSetObserver(new DataSetObserver(){

                @Override
                public void onChanged() {
                    super.onChanged();
                    ChooserRowAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onInvalidated() {
                    super.onInvalidated();
                    ChooserRowAdapter.this.notifyDataSetInvalidated();
                }
            });
        }

        private View createAzLabelView(ViewGroup viewGroup) {
            return this.mLayoutInflater.inflate(17367120, viewGroup, false);
        }

        private ViewGroup createContentPreviewView(View view, ViewGroup viewGroup) {
            Intent intent = ChooserActivity.this.getTargetIntent();
            ChooserActivity chooserActivity = ChooserActivity.this;
            int n = chooserActivity.findPreferredContentPreview(intent, chooserActivity.getContentResolver());
            if (view == null) {
                ChooserActivity.this.getMetricsLogger().write(new LogMaker(1652).setSubtype(n));
            }
            return ChooserActivity.this.displayContentPreview(n, intent, this.mLayoutInflater, (ViewGroup)view, viewGroup);
        }

        private View createProfileView(View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mLayoutInflater.inflate(17367125, viewGroup, false);
            }
            view.setBackground(ChooserActivity.this.getResources().getDrawable(17302106, null));
            ChooserActivity.this.mProfileView = view.findViewById(16909258);
            ChooserActivity.this.mProfileView.setOnClickListener(new _$$Lambda$KV7a09lZoRu37HsBE4cW2uLB7o8(ChooserActivity.this));
            ChooserActivity.this.bindProfileView();
            return view;
        }

        private int getMaxTargetsPerRow() {
            int n = 4;
            ChooserActivity chooserActivity = ChooserActivity.this;
            if (chooserActivity.shouldDisplayLandscape(chooserActivity.getResources().getConfiguration().orientation)) {
                n = 8;
            }
            return n;
        }

        private RowViewHolder loadViewsIntoRow(final RowViewHolder rowViewHolder) {
            Object object;
            Object object2;
            int n = View.MeasureSpec.makeMeasureSpec(0, 0);
            int n2 = View.MeasureSpec.makeMeasureSpec(this.mChooserTargetWidth, 1073741824);
            int n3 = rowViewHolder.getColumnCount();
            boolean bl = rowViewHolder instanceof DirectShareViewHolder;
            for (int i = 0; i < n3; ++i) {
                object = this.mChooserListAdapter.createView(rowViewHolder.getRowByIndex(i));
                ((View)object).setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        ChooserActivity.this.startSelected(rowViewHolder.getItemIndex(i), false, true);
                    }
                });
                ((View)object).setOnLongClickListener(new View.OnLongClickListener(){

                    @Override
                    public boolean onLongClick(View view) {
                        ChooserActivity.this.showTargetDetails(ChooserRowAdapter.this.mChooserListAdapter.resolveInfoForPosition(rowViewHolder.getItemIndex(i), true));
                        return true;
                    }
                });
                rowViewHolder.addView(i, (View)object);
                if (bl) {
                    object2 = (ResolverActivity.ViewHolder)((View)object).getTag();
                    ((ResolverActivity.ViewHolder)object2).text.setLines(2);
                    ((ResolverActivity.ViewHolder)object2).text.setHorizontallyScrolling(false);
                    ((ResolverActivity.ViewHolder)object2).text2.setVisibility(8);
                }
                ((View)object).measure(n2, n);
                this.setViewBounds((View)object, ((View)object).getMeasuredWidth(), ((View)object).getMeasuredHeight());
            }
            object2 = rowViewHolder.getViewGroup();
            rowViewHolder.measure();
            this.setViewBounds((View)object2, -1, rowViewHolder.getMeasuredRowHeight());
            if (bl) {
                object = (DirectShareViewHolder)rowViewHolder;
                this.setViewBounds(((DirectShareViewHolder)object).getRow(0), -1, ((DirectShareViewHolder)object).getMinRowHeight());
                this.setViewBounds(((DirectShareViewHolder)object).getRow(1), -1, ((DirectShareViewHolder)object).getMinRowHeight());
            }
            ((View)object2).setTag(rowViewHolder);
            return rowViewHolder;
        }

        private void setViewBounds(View view, int n, int n2) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(n, n2));
            } else {
                layoutParams.height = n2;
                layoutParams.width = n;
            }
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        void bindViewHolder(int n, RowViewHolder rowViewHolder) {
            Object object;
            int n2 = this.getFirstRowPosition(n);
            int n3 = this.getRowType(n2);
            int n4 = this.getRowType(this.getFirstRowPosition(n - 1));
            Object object2 = rowViewHolder.getViewGroup();
            if (n3 == n4 && n != this.getContentPreviewRowCount() + this.getProfileRowCount()) {
                ((View)object2).setForeground(null);
            } else {
                ((View)object2).setForeground(ChooserActivity.this.getResources().getDrawable(17302106, null));
            }
            n4 = rowViewHolder.getColumnCount();
            for (n = n2 + n4 - 1; this.getRowType(n) != n3 && n >= n2; --n) {
            }
            if (n == n2 && this.mChooserListAdapter.getItem(n2) instanceof EmptyTargetInfo && ((View)(object = (TextView)((View)object2).findViewById(16908807))).getVisibility() != 0) {
                ((View)object).setAlpha(0.0f);
                ((View)object).setVisibility(0);
                ((TextView)object).setText(17039656);
                object2 = ObjectAnimator.ofFloat(object, "alpha", 0.0f, 1.0f);
                ((ValueAnimator)object2).setInterpolator(new DecelerateInterpolator(1.0f));
                ((View)object).setTranslationY(ChooserActivity.this.getResources().getDimensionPixelSize(17105042));
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(object, "translationY", 0.0f);
                objectAnimator.setInterpolator(new DecelerateInterpolator(1.0f));
                object = new AnimatorSet();
                ((AnimatorSet)object).setDuration(200L);
                ((AnimatorSet)object).setStartDelay(200L);
                ((AnimatorSet)object).playTogether(new Animator[]{object2, objectAnimator});
                ((AnimatorSet)object).start();
            }
            for (n3 = 0; n3 < n4; ++n3) {
                object2 = rowViewHolder.getView(n3);
                if (n2 + n3 <= n) {
                    rowViewHolder.setViewVisibility(n3, 0);
                    rowViewHolder.setItemIndex(n3, n2 + n3);
                    this.mChooserListAdapter.bindView(rowViewHolder.getItemIndex(n3), (View)object2);
                    continue;
                }
                rowViewHolder.setViewVisibility(n3, 4);
            }
        }

        public boolean calculateChooserTargetWidth(int n) {
            if (n == 0) {
                return false;
            }
            if ((n /= this.getMaxTargetsPerRow()) != this.mChooserTargetWidth) {
                this.mChooserTargetWidth = n;
                return true;
            }
            return false;
        }

        public boolean consumeLayoutRequest() {
            boolean bl = this.mLayoutRequested;
            this.mLayoutRequested = false;
            return bl;
        }

        RowViewHolder createViewHolder(int n, ViewGroup object) {
            if (n == 0) {
                object = (ViewGroup)this.mLayoutInflater.inflate(17367127, (ViewGroup)object, false);
                ViewGroup viewGroup = (ViewGroup)this.mLayoutInflater.inflate(17367126, (ViewGroup)object, false);
                ViewGroup viewGroup2 = (ViewGroup)this.mLayoutInflater.inflate(17367126, (ViewGroup)object, false);
                ((ViewGroup)object).addView(viewGroup);
                ((ViewGroup)object).addView(viewGroup2);
                this.mDirectShareViewHolder = new DirectShareViewHolder((ViewGroup)object, Lists.newArrayList(viewGroup, viewGroup2), this.getMaxTargetsPerRow());
                this.loadViewsIntoRow(this.mDirectShareViewHolder);
                return this.mDirectShareViewHolder;
            }
            object = (ViewGroup)this.mLayoutInflater.inflate(17367126, (ViewGroup)object, false);
            object = new SingleRowViewHolder((ViewGroup)object, this.getMaxTargetsPerRow());
            this.loadViewsIntoRow((RowViewHolder)object);
            return object;
        }

        public int getAzLabelRowCount() {
            int n = this.mShowAzLabelIfPoss && this.mChooserListAdapter.getAlphaTargetCount() > 0 ? 1 : 0;
            return n;
        }

        public int getCallerAndRankedTargetRowCount() {
            return (int)Math.ceil(((float)this.mChooserListAdapter.getCallerTargetCount() + (float)this.mChooserListAdapter.getRankedTargetCount()) / (float)this.getMaxTargetsPerRow());
        }

        public int getContentPreviewRowCount() {
            Object object = ChooserActivity.this;
            if (!((ChooserActivity)object).isSendAction(((ResolverActivity)object).getTargetIntent())) {
                return 0;
            }
            return !this.mHideContentPreview && (object = this.mChooserListAdapter) != null && ((ChooserListAdapter)object).getCount() != 0;
            {
            }
        }

        @Override
        public int getCount() {
            return (int)((double)(this.getContentPreviewRowCount() + this.getProfileRowCount() + this.getServiceTargetRowCount() + this.getCallerAndRankedTargetRowCount() + this.getAzLabelRowCount()) + Math.ceil((float)this.mChooserListAdapter.getAlphaTargetCount() / (float)this.getMaxTargetsPerRow()));
        }

        int getFirstRowPosition(int n) {
            int n2;
            int n3;
            int n4 = n - (this.getContentPreviewRowCount() + this.getProfileRowCount());
            if (n4 < (n3 = (int)Math.ceil((float)(n2 = this.mChooserListAdapter.getServiceTargetCount()) / 8.0f))) {
                return this.getMaxTargetsPerRow() * n4;
            }
            n = this.mChooserListAdapter.getCallerTargetCount();
            int n5 = this.mChooserListAdapter.getRankedTargetCount();
            int n6 = this.getCallerAndRankedTargetRowCount();
            if (n4 < n6 + n3) {
                return (n4 - n3) * this.getMaxTargetsPerRow() + n2;
            }
            return n + n5 + n2 + (n4 - this.getAzLabelRowCount() - n6 - n3) * this.getMaxTargetsPerRow();
        }

        @Override
        public Object getItem(int n) {
            return n;
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        @Override
        public int getItemViewType(int n) {
            int n2 = this.getContentPreviewRowCount();
            if (n2 > 0 && n < n2) {
                return 2;
            }
            int n3 = this.getProfileRowCount();
            if (n3 > 0 && n < (n2 += n3)) {
                return 3;
            }
            n3 = this.getServiceTargetRowCount();
            if (n3 > 0 && n < (n2 += n3)) {
                return 0;
            }
            n3 = this.getCallerAndRankedTargetRowCount();
            if (n3 > 0 && n < (n2 += n3)) {
                return 1;
            }
            n3 = this.getAzLabelRowCount();
            if (n3 > 0 && n < n2 + n3) {
                return 4;
            }
            return 1;
        }

        public int getProfileRowCount() {
            int n = this.mChooserListAdapter.getOtherProfile() == null ? 0 : 1;
            return n;
        }

        int getRowType(int n) {
            if ((n = this.mChooserListAdapter.getPositionTargetType(n)) == 0) {
                return 2;
            }
            if (this.getAzLabelRowCount() > 0 && n == 3) {
                return 2;
            }
            return n;
        }

        public int getServiceTargetRowCount() {
            ChooserActivity chooserActivity = ChooserActivity.this;
            return chooserActivity.isSendAction(chooserActivity.getTargetIntent()) && !ActivityManager.isLowRamDeviceStatic();
        }

        @Override
        public View getView(int n, View object, ViewGroup viewGroup) {
            int n2 = this.getItemViewType(n);
            if (n2 == 2) {
                return this.createContentPreviewView((View)object, viewGroup);
            }
            if (n2 == 3) {
                return this.createProfileView((View)object, viewGroup);
            }
            if (n2 == 4) {
                return this.createAzLabelView(viewGroup);
            }
            object = object == null ? this.createViewHolder(n2, viewGroup) : (RowViewHolder)((View)object).getTag();
            this.bindViewHolder(n, (RowViewHolder)object);
            return ((RowViewHolder)object).getViewGroup();
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }

        public void handleScroll(View object, int n, int n2) {
            int n3 = ChooserActivity.this.getResources().getConfiguration().orientation;
            int n4 = this.mChooserListAdapter.getNumShortcutResults();
            int n5 = this.getMaxTargetsPerRow();
            boolean bl = true;
            if (n4 <= n5 || n3 != 1 || ChooserActivity.this.isInMultiWindowMode()) {
                bl = false;
            }
            object = this.mDirectShareViewHolder;
            if (object != null && bl) {
                ((DirectShareViewHolder)object).handleScroll(ChooserActivity.this.mAdapterView, n, n2, this.getMaxTargetsPerRow());
            }
        }

        public void hideContentPreview() {
            this.mHideContentPreview = true;
            this.mLayoutRequested = true;
            this.notifyDataSetChanged();
        }

        @Override
        public boolean isEnabled(int n) {
            return (n = this.getItemViewType(n)) != 2 && n != 4;
            {
            }
        }

    }

    static interface ChooserTargetInfo
    extends ResolverActivity.TargetInfo {
        public ChooserTarget getChooserTarget();

        public float getModifiedScore();

        default public boolean isSimilar(ChooserTargetInfo chooserTargetInfo) {
            if (chooserTargetInfo == null) {
                return false;
            }
            ChooserTarget chooserTarget = this.getChooserTarget();
            ChooserTarget chooserTarget2 = chooserTargetInfo.getChooserTarget();
            if (chooserTarget != null && chooserTarget2 != null) {
                return chooserTarget.getComponentName().equals(chooserTarget2.getComponentName()) && TextUtils.equals(this.getDisplayLabel(), chooserTargetInfo.getDisplayLabel()) && TextUtils.equals(this.getExtendedInfo(), chooserTargetInfo.getExtendedInfo());
            }
            return false;
        }
    }

    static class ChooserTargetServiceConnection
    implements ServiceConnection {
        private ChooserActivity mChooserActivity;
        private final IChooserTargetResult mChooserTargetResult = new IChooserTargetResult.Stub(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void sendResult(List<ChooserTarget> object) throws RemoteException {
                Object object2 = ChooserTargetServiceConnection.this.mLock;
                synchronized (object2) {
                    if (ChooserTargetServiceConnection.this.mChooserActivity == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("destroyed ChooserTargetServiceConnection received result from ");
                        ((StringBuilder)object).append(ChooserTargetServiceConnection.this.mConnectedComponent);
                        ((StringBuilder)object).append("; ignoring...");
                        Log.e(ChooserActivity.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    ChooserTargetServiceConnection.this.mChooserActivity.filterServiceTargets(ChooserTargetServiceConnection.access$3500((ChooserTargetServiceConnection)ChooserTargetServiceConnection.this).getResolveInfo().activityInfo.packageName, (List<ChooserTarget>)object);
                    Message message = Message.obtain();
                    message.what = 1;
                    ServiceResultInfo serviceResultInfo = new ServiceResultInfo(ChooserTargetServiceConnection.this.mOriginalTarget, (List<ChooserTarget>)object, ChooserTargetServiceConnection.this);
                    message.obj = serviceResultInfo;
                    ChooserTargetServiceConnection.this.mChooserActivity.mChooserHandler.sendMessage(message);
                    return;
                }
            }
        };
        private ComponentName mConnectedComponent;
        private final Object mLock = new Object();
        private ResolverActivity.DisplayResolveInfo mOriginalTarget;

        public ChooserTargetServiceConnection(ChooserActivity chooserActivity, ResolverActivity.DisplayResolveInfo displayResolveInfo) {
            this.mChooserActivity = chooserActivity;
            this.mOriginalTarget = displayResolveInfo;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void destroy() {
            Object object = this.mLock;
            synchronized (object) {
                this.mChooserActivity = null;
                this.mOriginalTarget = null;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder object) {
            Object object2 = this.mLock;
            synchronized (object2) {
                if (this.mChooserActivity == null) {
                    Log.e(ChooserActivity.TAG, "destroyed ChooserTargetServiceConnection got onServiceConnected");
                    return;
                }
                object = IChooserTargetService.Stub.asInterface((IBinder)object);
                try {
                    object.getChooserTargets(this.mOriginalTarget.getResolvedComponentName(), this.mOriginalTarget.getResolveInfo().filter, this.mChooserTargetResult);
                }
                catch (RemoteException remoteException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Querying ChooserTargetService ");
                    stringBuilder.append(componentName);
                    stringBuilder.append(" failed.");
                    Log.e(ChooserActivity.TAG, stringBuilder.toString(), remoteException);
                    this.mChooserActivity.unbindService(this);
                    this.mChooserActivity.mServiceConnections.remove(this);
                    this.destroy();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mChooserActivity == null) {
                    Log.e(ChooserActivity.TAG, "destroyed ChooserTargetServiceConnection got onServiceDisconnected");
                    return;
                }
                this.mChooserActivity.unbindService(this);
                this.mChooserActivity.mServiceConnections.remove(this);
                if (this.mChooserActivity.mServiceConnections.isEmpty()) {
                    this.mChooserActivity.sendVoiceChoicesIfNeeded();
                }
                this.mConnectedComponent = null;
                this.destroy();
                return;
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ChooserTargetServiceConnection{service=");
            stringBuilder.append(this.mConnectedComponent);
            stringBuilder.append(", activity=");
            Object object = this.mOriginalTarget;
            object = object != null ? object.getResolveInfo().activityInfo.toString() : "<connection destroyed>";
            stringBuilder.append((String)object);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

    }

    private class ContentPreviewCoordinator {
        private static final int IMAGE_FADE_IN_MILLIS = 150;
        private static final int IMAGE_LOAD_INTO_VIEW = 2;
        private static final int IMAGE_LOAD_TIMEOUT = 1;
        private boolean mAtLeastOneLoaded;
        private final Handler mHandler;
        private boolean mHideParentOnFail;
        private final int mImageLoadTimeoutMillis;
        private final View mParentView;

        ContentPreviewCoordinator(View view, boolean bl) {
            this.mImageLoadTimeoutMillis = ChooserActivity.this.getResources().getInteger(17694720);
            this.mAtLeastOneLoaded = false;
            this.mHandler = new Handler(){

                @Override
                public void handleMessage(Message object) {
                    int n = ((Message)object).what;
                    if (n != 1) {
                        if (n == 2 && !ChooserActivity.this.isFinishing()) {
                            LoadUriTask loadUriTask = (LoadUriTask)((Message)object).obj;
                            RoundedRectImageView roundedRectImageView = (RoundedRectImageView)ContentPreviewCoordinator.this.mParentView.findViewById(loadUriTask.mImageResourceId);
                            if (loadUriTask.mBmp == null) {
                                roundedRectImageView.setVisibility(8);
                                ContentPreviewCoordinator.this.maybeHideContentPreview();
                                return;
                            }
                            ContentPreviewCoordinator.this.mAtLeastOneLoaded = true;
                            roundedRectImageView.setVisibility(0);
                            roundedRectImageView.setAlpha(0.0f);
                            roundedRectImageView.setImageBitmap(loadUriTask.mBmp);
                            object = ObjectAnimator.ofFloat((Object)roundedRectImageView, "alpha", 0.0f, 1.0f);
                            ((ValueAnimator)object).setInterpolator(new DecelerateInterpolator(1.0f));
                            ((ValueAnimator)object).setDuration(150L);
                            ((ValueAnimator)object).start();
                            if (loadUriTask.mExtraCount > 0) {
                                roundedRectImageView.setExtraImageCount(loadUriTask.mExtraCount);
                            }
                        }
                    } else {
                        ContentPreviewCoordinator.this.maybeHideContentPreview();
                    }
                }
            };
            this.mParentView = view;
            this.mHideParentOnFail = bl;
        }

        private void cancelLoads() {
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(1);
        }

        private void collapseParentView() {
            View view = this.mParentView;
            view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 1073741824));
            view.getLayoutParams().height = 0;
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getTop());
            view.invalidate();
        }

        private void loadUriIntoView(int n, Uri uri, int n2) {
            this.mHandler.sendEmptyMessageDelayed(1, this.mImageLoadTimeoutMillis);
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new _$$Lambda$ChooserActivity$ContentPreviewCoordinator$4EA4_6wC7DBv77gLolqI2_lsDQI(this, uri, n, n2));
        }

        private void maybeHideContentPreview() {
            if (!this.mAtLeastOneLoaded && this.mHideParentOnFail) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Hiding image preview area. Timed out waiting for preview to load within ");
                stringBuilder.append(this.mImageLoadTimeoutMillis);
                stringBuilder.append("ms.");
                Log.i(ChooserActivity.TAG, stringBuilder.toString());
                this.collapseParentView();
                if (ChooserActivity.this.mChooserRowAdapter != null) {
                    ChooserActivity.this.mChooserRowAdapter.hideContentPreview();
                }
                this.mHideParentOnFail = false;
            }
        }

        public /* synthetic */ void lambda$loadUriIntoView$0$ChooserActivity$ContentPreviewCoordinator(Uri uri, int n, int n2) {
            Bitmap bitmap = ChooserActivity.this.loadThumbnail(uri, new Size(200, 200));
            Message message = Message.obtain();
            message.what = 2;
            message.obj = new LoadUriTask(n, uri, n2, bitmap);
            this.mHandler.sendMessage(message);
        }

        class LoadUriTask {
            public final Bitmap mBmp;
            public final int mExtraCount;
            public final int mImageResourceId;
            public final Uri mUri;

            LoadUriTask(int n, Uri uri, int n2, Bitmap bitmap) {
                this.mImageResourceId = n;
                this.mUri = uri;
                this.mExtraCount = n2;
                this.mBmp = bitmap;
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface ContentPreviewType {
    }

    class DirectShareViewHolder
    extends RowViewHolder {
        private int mCellCountPerRow;
        private final boolean[] mCellVisibility;
        private int mDirectShareCurrHeight;
        private int mDirectShareMaxHeight;
        private int mDirectShareMinHeight;
        private boolean mHideDirectShareExpansion;
        private final ViewGroup mParent;
        private final List<ViewGroup> mRows;

        DirectShareViewHolder(ViewGroup viewGroup, List<ViewGroup> list, int n) {
            super(list.size() * n);
            this.mHideDirectShareExpansion = false;
            this.mDirectShareMinHeight = 0;
            this.mDirectShareCurrHeight = 0;
            this.mDirectShareMaxHeight = 0;
            this.mParent = viewGroup;
            this.mRows = list;
            this.mCellCountPerRow = n;
            this.mCellVisibility = new boolean[list.size() * n];
        }

        @Override
        public ViewGroup addView(int n, View view) {
            ViewGroup viewGroup = this.getRowByIndex(n);
            viewGroup.addView(view);
            this.mCells[n] = view;
            return viewGroup;
        }

        @Override
        public int getMeasuredRowHeight() {
            return this.mDirectShareCurrHeight;
        }

        public int getMinRowHeight() {
            return this.mDirectShareMinHeight;
        }

        @Override
        public ViewGroup getRow(int n) {
            return this.mRows.get(n);
        }

        @Override
        public ViewGroup getRowByIndex(int n) {
            return this.mRows.get(n / this.mCellCountPerRow);
        }

        @Override
        public ViewGroup getViewGroup() {
            return this.mParent;
        }

        public void handleScroll(AbsListView absListView, int n, int n2, int n3) {
            int n4 = this.mDirectShareCurrHeight == this.mDirectShareMinHeight ? 1 : 0;
            if (n4 != 0) {
                if (this.mHideDirectShareExpansion) {
                    return;
                }
                if (ChooserActivity.this.mChooserListAdapter.getSelectableServiceTargetCount() <= n3) {
                    this.mHideDirectShareExpansion = true;
                    return;
                }
            }
            n2 = (int)((float)(n2 - n) * 0.78f);
            n = this.mDirectShareCurrHeight;
            n4 = Math.max(Math.min(n + n2, this.mDirectShareMaxHeight), this.mDirectShareMinHeight);
            int n5 = n4 - n;
            if (absListView != null && absListView.getChildCount() != 0 && n5 != 0) {
                n3 = 0;
                for (n = 0; n < absListView.getChildCount(); ++n) {
                    View view = absListView.getChildAt(n);
                    if (n3 != 0) {
                        view.offsetTopAndBottom(n5);
                        n2 = n3;
                    } else {
                        n2 = n3;
                        if (view.getTag() != null) {
                            n2 = n3;
                            if (view.getTag() instanceof DirectShareViewHolder) {
                                view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(n4, 1073741824));
                                view.getLayoutParams().height = view.getMeasuredHeight();
                                view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getTop() + view.getMeasuredHeight());
                                n2 = 1;
                            }
                        }
                    }
                    n3 = n2;
                }
                if (n3 != 0) {
                    this.mDirectShareCurrHeight = n4;
                }
                return;
            }
        }

        @Override
        public void measure() {
            int n = View.MeasureSpec.makeMeasureSpec(0, 0);
            this.getRow(0).measure(n, n);
            this.getRow(1).measure(n, n);
            this.mDirectShareMinHeight = this.getRow(0).getMeasuredHeight();
            n = this.mDirectShareCurrHeight;
            if (n <= 0) {
                n = this.mDirectShareMinHeight;
            }
            this.mDirectShareCurrHeight = n;
            this.mDirectShareMaxHeight = this.mDirectShareMinHeight * 2;
        }

        @Override
        public void setViewVisibility(int n, int n2) {
            Object object;
            final View view = this.getView(n);
            if (n2 == 0) {
                this.mCellVisibility[n] = true;
                view.setVisibility(n2);
                view.setAlpha(1.0f);
            } else if (n2 == 4 && (object = this.mCellVisibility)[n]) {
                object[n] = false;
                object = ObjectAnimator.ofFloat((Object)view, "alpha", 1.0f, 0.0f);
                ((ValueAnimator)object).setDuration(200L);
                ((ValueAnimator)object).setInterpolator(new AccelerateInterpolator(1.0f));
                ((Animator)object).addListener(new AnimatorListenerAdapter(){

                    @Override
                    public void onAnimationEnd(Animator animator2) {
                        view.setVisibility(4);
                    }
                });
                ((ValueAnimator)object).start();
            }
        }

    }

    final class EmptyTargetInfo
    extends NotSelectableTargetInfo {
        EmptyTargetInfo() {
        }

        @Override
        public Drawable getDisplayIcon() {
            return null;
        }
    }

    private static class FileInfo {
        public final boolean hasThumbnail;
        public final String name;

        FileInfo(String string2, boolean bl) {
            this.name = string2;
            this.hasThumbnail = bl;
        }
    }

    abstract class NotSelectableTargetInfo
    implements ChooserTargetInfo {
        NotSelectableTargetInfo() {
        }

        @Override
        public ResolverActivity.TargetInfo cloneFilledIn(Intent intent, int n) {
            return null;
        }

        @Override
        public List<Intent> getAllSourceIntents() {
            return null;
        }

        @Override
        public ChooserTarget getChooserTarget() {
            return null;
        }

        @Override
        public CharSequence getDisplayLabel() {
            return null;
        }

        @Override
        public CharSequence getExtendedInfo() {
            return null;
        }

        @Override
        public float getModifiedScore() {
            return -0.1f;
        }

        @Override
        public ResolveInfo getResolveInfo() {
            return null;
        }

        @Override
        public ComponentName getResolvedComponentName() {
            return null;
        }

        @Override
        public Intent getResolvedIntent() {
            return null;
        }

        @Override
        public boolean isSuspended() {
            return false;
        }

        @Override
        public boolean start(Activity activity, Bundle bundle) {
            return false;
        }

        @Override
        public boolean startAsCaller(ResolverActivity resolverActivity, Bundle bundle, int n) {
            return false;
        }

        @Override
        public boolean startAsUser(Activity activity, Bundle bundle, UserHandle userHandle) {
            return false;
        }
    }

    final class PlaceHolderTargetInfo
    extends NotSelectableTargetInfo {
        PlaceHolderTargetInfo() {
        }

        @Override
        public Drawable getDisplayIcon() {
            AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable)ChooserActivity.this.getDrawable(17302103);
            animatedVectorDrawable.start();
            return animatedVectorDrawable;
        }
    }

    static class RefinementResultReceiver
    extends ResultReceiver {
        private ChooserActivity mChooserActivity;
        private ResolverActivity.TargetInfo mSelectedTarget;

        public RefinementResultReceiver(ChooserActivity chooserActivity, ResolverActivity.TargetInfo targetInfo, Handler handler) {
            super(handler);
            this.mChooserActivity = chooserActivity;
            this.mSelectedTarget = targetInfo;
        }

        public void destroy() {
            this.mChooserActivity = null;
            this.mSelectedTarget = null;
        }

        @Override
        protected void onReceiveResult(int n, Bundle object) {
            ChooserActivity chooserActivity = this.mChooserActivity;
            if (chooserActivity == null) {
                Log.e(ChooserActivity.TAG, "Destroyed RefinementResultReceiver received a result");
                return;
            }
            if (object == null) {
                Log.e(ChooserActivity.TAG, "RefinementResultReceiver received null resultData");
                return;
            }
            if (n != -1) {
                if (n != 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown result code ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" sent to RefinementResultReceiver");
                    Log.w(ChooserActivity.TAG, ((StringBuilder)object).toString());
                } else {
                    chooserActivity.onRefinementCanceled();
                }
            } else if ((object = ((Bundle)object).getParcelable("android.intent.extra.INTENT")) instanceof Intent) {
                this.mChooserActivity.onRefinementResult(this.mSelectedTarget, (Intent)object);
            } else {
                Log.e(ChooserActivity.TAG, "RefinementResultReceiver received RESULT_OK but no Intent in resultData with key Intent.EXTRA_INTENT");
            }
        }
    }

    public static class RoundedRectImageView
    extends ImageView {
        private String mExtraImageCount = null;
        private Paint mOverlayPaint = new Paint(0);
        private Path mPath = new Path();
        private int mRadius = 0;
        private Paint mRoundRectPaint = new Paint(0);
        private Paint mTextPaint = new Paint(1);

        public RoundedRectImageView(Context context) {
            super(context);
        }

        public RoundedRectImageView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public RoundedRectImageView(Context context, AttributeSet attributeSet, int n) {
            this(context, attributeSet, n, 0);
        }

        public RoundedRectImageView(Context context, AttributeSet attributeSet, int n, int n2) {
            super(context, attributeSet, n, n2);
            this.mRadius = context.getResources().getDimensionPixelSize(17105032);
            this.mOverlayPaint.setColor(-1728053248);
            this.mOverlayPaint.setStyle(Paint.Style.FILL);
            this.mRoundRectPaint.setColor(context.getResources().getColor(17170724));
            this.mRoundRectPaint.setStyle(Paint.Style.STROKE);
            this.mRoundRectPaint.setStrokeWidth(context.getResources().getDimensionPixelSize(17105039));
            this.mTextPaint.setColor(-1);
            this.mTextPaint.setTextSize(context.getResources().getDimensionPixelSize(17105040));
            this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        }

        private void updatePath(int n, int n2) {
            this.mPath.reset();
            int n3 = this.getPaddingRight();
            int n4 = this.getPaddingLeft();
            int n5 = this.getPaddingBottom();
            int n6 = this.getPaddingTop();
            Path path = this.mPath;
            float f = this.getPaddingLeft();
            float f2 = this.getPaddingTop();
            float f3 = n - n3 - n4;
            float f4 = n2 - n5 - n6;
            n = this.mRadius;
            path.addRoundRect(f, f2, f3, f4, n, n, Path.Direction.CW);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (this.mRadius != 0) {
                canvas.clipPath(this.mPath);
            }
            super.onDraw(canvas);
            int n = this.getPaddingLeft();
            int n2 = this.getPaddingRight();
            int n3 = this.getWidth() - this.getPaddingRight() - this.getPaddingLeft();
            int n4 = this.getHeight() - this.getPaddingBottom() - this.getPaddingTop();
            if (this.mExtraImageCount != null) {
                canvas.drawRect(n, n2, n3, n4, this.mOverlayPaint);
                int n5 = canvas.getWidth() / 2;
                int n6 = (int)((float)canvas.getHeight() / 2.0f - (this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f);
                canvas.drawText(this.mExtraImageCount, n5, n6, this.mTextPaint);
            }
            float f = n;
            float f2 = n2;
            float f3 = n3;
            float f4 = n4;
            n2 = this.mRadius;
            canvas.drawRoundRect(f, f2, f3, f4, n2, n2, this.mRoundRectPaint);
        }

        @Override
        protected void onSizeChanged(int n, int n2, int n3, int n4) {
            super.onSizeChanged(n, n2, n3, n4);
            this.updatePath(n, n2);
        }

        public void setExtraImageCount(int n) {
            if (n > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("+");
                stringBuilder.append(n);
                this.mExtraImageCount = stringBuilder.toString();
            } else {
                this.mExtraImageCount = null;
            }
        }

        public void setRadius(int n) {
            this.mRadius = n;
            this.updatePath(this.getWidth(), this.getHeight());
        }
    }

    abstract class RowViewHolder {
        protected final View[] mCells;
        private final int mColumnCount;
        private int[] mItemIndices;
        protected int mMeasuredRowHeight;

        RowViewHolder(int n) {
            this.mCells = new View[n];
            this.mItemIndices = new int[n];
            this.mColumnCount = n;
        }

        abstract ViewGroup addView(int var1, View var2);

        public int getColumnCount() {
            return this.mColumnCount;
        }

        public int getItemIndex(int n) {
            return this.mItemIndices[n];
        }

        public int getMeasuredRowHeight() {
            return this.mMeasuredRowHeight;
        }

        abstract ViewGroup getRow(int var1);

        abstract ViewGroup getRowByIndex(int var1);

        public View getView(int n) {
            return this.mCells[n];
        }

        abstract ViewGroup getViewGroup();

        public void measure() {
            int n = View.MeasureSpec.makeMeasureSpec(0, 0);
            this.getViewGroup().measure(n, n);
            this.mMeasuredRowHeight = this.getViewGroup().getMeasuredHeight();
        }

        public void setItemIndex(int n, int n2) {
            this.mItemIndices[n] = n2;
        }

        abstract void setViewVisibility(int var1, int var2);
    }

    final class SelectableTargetInfo
    implements ChooserTargetInfo {
        private final ResolveInfo mBackupResolveInfo;
        private CharSequence mBadgeContentDescription;
        private Drawable mBadgeIcon = null;
        private final ChooserTarget mChooserTarget;
        private Drawable mDisplayIcon;
        private final String mDisplayLabel;
        private final int mFillInFlags;
        private final Intent mFillInIntent;
        private boolean mIsSuspended = false;
        private final float mModifiedScore;
        private final ResolverActivity.DisplayResolveInfo mSourceInfo;

        private SelectableTargetInfo(SelectableTargetInfo selectableTargetInfo, Intent intent, int n) {
            this.mSourceInfo = selectableTargetInfo.mSourceInfo;
            this.mBackupResolveInfo = selectableTargetInfo.mBackupResolveInfo;
            this.mChooserTarget = selectableTargetInfo.mChooserTarget;
            this.mBadgeIcon = selectableTargetInfo.mBadgeIcon;
            this.mBadgeContentDescription = selectableTargetInfo.mBadgeContentDescription;
            this.mDisplayIcon = selectableTargetInfo.mDisplayIcon;
            this.mFillInIntent = intent;
            this.mFillInFlags = n;
            this.mModifiedScore = selectableTargetInfo.mModifiedScore;
            this.mDisplayLabel = this.sanitizeDisplayLabel(this.mChooserTarget.getTitle());
        }

        SelectableTargetInfo(ResolverActivity.DisplayResolveInfo displayResolveInfo, ChooserTarget chooserTarget, float f) {
            ActivityInfo activityInfo;
            Object object;
            this.mSourceInfo = displayResolveInfo;
            this.mChooserTarget = chooserTarget;
            this.mModifiedScore = f;
            if (displayResolveInfo != null && (object = displayResolveInfo.getResolveInfo()) != null && (activityInfo = ((ResolveInfo)object).activityInfo) != null && activityInfo.applicationInfo != null) {
                object = ChooserActivity.this.getPackageManager();
                this.mBadgeIcon = ((PackageManager)object).getApplicationIcon(activityInfo.applicationInfo);
                this.mBadgeContentDescription = ((PackageManager)object).getApplicationLabel(activityInfo.applicationInfo);
                boolean bl = (activityInfo.applicationInfo.flags & 1073741824) != 0;
                this.mIsSuspended = bl;
            }
            this.mDisplayIcon = this.getChooserTargetIconDrawable(chooserTarget);
            this.mBackupResolveInfo = displayResolveInfo != null ? null : ChooserActivity.this.getPackageManager().resolveActivity(this.getResolvedIntent(), 0);
            this.mFillInIntent = null;
            this.mFillInFlags = 0;
            this.mDisplayLabel = this.sanitizeDisplayLabel(chooserTarget.getTitle());
        }

        private Intent getBaseIntentToSend() {
            Intent intent = this.getResolvedIntent();
            if (intent == null) {
                Log.e(ChooserActivity.TAG, "ChooserTargetInfo: no base intent available to send");
            } else {
                intent = new Intent(intent);
                Intent intent2 = this.mFillInIntent;
                if (intent2 != null) {
                    intent.fillIn(intent2, this.mFillInFlags);
                }
                intent.fillIn(ChooserActivity.this.mReferrerFillInIntent, 0);
            }
            return intent;
        }

        private Drawable getChooserTargetIconDrawable(ChooserTarget object) {
            Bitmap bitmap = null;
            Object object2 = ((ChooserTarget)object).getIcon();
            if (object2 != null) {
                object2 = ((Icon)object2).loadDrawable(ChooserActivity.this);
            } else {
                Object object3 = ((ChooserTarget)object).getIntentExtras();
                object2 = bitmap;
                if (object3 != null) {
                    object2 = bitmap;
                    if (((BaseBundle)object3).containsKey("android.intent.extra.shortcut.ID")) {
                        object2 = ((Bundle)object3).getCharSequence("android.intent.extra.shortcut.ID");
                        object3 = (LauncherApps)ChooserActivity.this.getSystemService("launcherapps");
                        Object object4 = new LauncherApps.ShortcutQuery();
                        ((LauncherApps.ShortcutQuery)object4).setPackage(((ChooserTarget)object).getComponentName().getPackageName());
                        ((LauncherApps.ShortcutQuery)object4).setShortcutIds(Arrays.asList(object2.toString()));
                        ((LauncherApps.ShortcutQuery)object4).setQueryFlags(1);
                        object4 = ((LauncherApps)object3).getShortcuts((LauncherApps.ShortcutQuery)object4, ChooserActivity.this.getUser());
                        object2 = bitmap;
                        if (object4 != null) {
                            object2 = bitmap;
                            if (object4.size() > 0) {
                                object2 = ((LauncherApps)object3).getShortcutIconDrawable((ShortcutInfo)object4.get(0), 0);
                            }
                        }
                    }
                }
            }
            if (object2 == null) {
                return null;
            }
            bitmap = null;
            try {
                object = ChooserActivity.this.mPm.getActivityInfo(((ChooserTarget)object).getComponentName(), 0);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.e(ChooserActivity.TAG, "Could not find activity associated with ChooserTarget");
                object = bitmap;
            }
            if (object == null) {
                return null;
            }
            bitmap = ChooserActivity.this.makePresentationGetter((ActivityInfo)object).getIconBitmap(UserHandle.getUserHandleForUid(UserHandle.myUserId()));
            object = SimpleIconFactory.obtain(ChooserActivity.this);
            object2 = ((SimpleIconFactory)object).createAppBadgedIconBitmap((Drawable)object2, bitmap);
            ((SimpleIconFactory)object).recycle();
            return new BitmapDrawable(ChooserActivity.this.getResources(), (Bitmap)object2);
        }

        private String sanitizeDisplayLabel(CharSequence charSequence) {
            charSequence = new SpannableStringBuilder(charSequence);
            ((SpannableStringBuilder)charSequence).clearSpans();
            return ((SpannableStringBuilder)charSequence).toString();
        }

        @Override
        public ResolverActivity.TargetInfo cloneFilledIn(Intent intent, int n) {
            return new SelectableTargetInfo(this, intent, n);
        }

        @Override
        public List<Intent> getAllSourceIntents() {
            ArrayList<Intent> arrayList = new ArrayList<Intent>();
            ResolverActivity.DisplayResolveInfo displayResolveInfo = this.mSourceInfo;
            if (displayResolveInfo != null) {
                arrayList.add(displayResolveInfo.getAllSourceIntents().get(0));
            }
            return arrayList;
        }

        @Override
        public ChooserTarget getChooserTarget() {
            return this.mChooserTarget;
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
            return null;
        }

        @Override
        public float getModifiedScore() {
            return this.mModifiedScore;
        }

        @Override
        public ResolveInfo getResolveInfo() {
            Object object = this.mSourceInfo;
            object = object != null ? ((ResolverActivity.DisplayResolveInfo)object).getResolveInfo() : this.mBackupResolveInfo;
            return object;
        }

        @Override
        public ComponentName getResolvedComponentName() {
            Object object = this.mSourceInfo;
            if (object != null) {
                return ((ResolverActivity.DisplayResolveInfo)object).getResolvedComponentName();
            }
            object = this.mBackupResolveInfo;
            if (object != null) {
                return new ComponentName(object.activityInfo.packageName, this.mBackupResolveInfo.activityInfo.name);
            }
            return null;
        }

        @Override
        public Intent getResolvedIntent() {
            Object object = this.mSourceInfo;
            if (object != null) {
                return ((ResolverActivity.DisplayResolveInfo)object).getResolvedIntent();
            }
            object = new Intent(ChooserActivity.this.getTargetIntent());
            ((Intent)object).setComponent(this.mChooserTarget.getComponentName());
            ((Intent)object).putExtras(this.mChooserTarget.getIntentExtras());
            return object;
        }

        @Override
        public boolean isSuspended() {
            return this.mIsSuspended;
        }

        @Override
        public boolean start(Activity activity, Bundle bundle) {
            throw new RuntimeException("ChooserTargets should be started as caller.");
        }

        @Override
        public boolean startAsCaller(ResolverActivity resolverActivity, Bundle bundle, int n) {
            Intent intent;
            boolean bl;
            block1 : {
                intent = this.getBaseIntentToSend();
                bl = false;
                if (intent == null) {
                    return false;
                }
                intent.setComponent(this.mChooserTarget.getComponentName());
                intent.putExtras(this.mChooserTarget.getIntentExtras());
                ResolverActivity.DisplayResolveInfo displayResolveInfo = this.mSourceInfo;
                if (displayResolveInfo == null || !displayResolveInfo.getResolvedComponentName().getPackageName().equals(this.mChooserTarget.getComponentName().getPackageName())) break block1;
                bl = true;
            }
            return resolverActivity.startAsCallerImpl(intent, bundle, bl, n);
        }

        @Override
        public boolean startAsUser(Activity activity, Bundle bundle, UserHandle userHandle) {
            throw new RuntimeException("ChooserTargets should be started as caller.");
        }
    }

    static class ServiceResultInfo {
        public final ChooserTargetServiceConnection connection;
        public final ResolverActivity.DisplayResolveInfo originalTarget;
        public final List<ChooserTarget> resultTargets;

        public ServiceResultInfo(ResolverActivity.DisplayResolveInfo displayResolveInfo, List<ChooserTarget> list, ChooserTargetServiceConnection chooserTargetServiceConnection) {
            this.originalTarget = displayResolveInfo;
            this.resultTargets = list;
            this.connection = chooserTargetServiceConnection;
        }
    }

    class SingleRowViewHolder
    extends RowViewHolder {
        private final ViewGroup mRow;

        SingleRowViewHolder(ViewGroup viewGroup, int n) {
            super(n);
            this.mRow = viewGroup;
        }

        @Override
        public ViewGroup addView(int n, View view) {
            this.mRow.addView(view);
            this.mCells[n] = view;
            return this.mRow;
        }

        @Override
        public ViewGroup getRow(int n) {
            if (n == 0) {
                return this.mRow;
            }
            return null;
        }

        @Override
        public ViewGroup getRowByIndex(int n) {
            return this.mRow;
        }

        @Override
        public ViewGroup getViewGroup() {
            return this.mRow;
        }

        @Override
        public void setViewVisibility(int n, int n2) {
            this.getView(n).setVisibility(n2);
        }
    }

}

