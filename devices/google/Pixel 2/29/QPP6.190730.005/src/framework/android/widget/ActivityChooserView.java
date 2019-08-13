/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.database.Observable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ActivityChooserModel;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ForwardingListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.view.menu.ShowableListMenu;

public class ActivityChooserView
extends ViewGroup
implements ActivityChooserModel.ActivityChooserModelClient {
    private static final String LOG_TAG = "ActivityChooserView";
    private final LinearLayout mActivityChooserContent;
    private final Drawable mActivityChooserContentBackground;
    private final ActivityChooserViewAdapter mAdapter;
    private final Callbacks mCallbacks;
    private int mDefaultActionButtonContentDescription;
    private final FrameLayout mDefaultActivityButton;
    private final ImageView mDefaultActivityButtonImage;
    private final FrameLayout mExpandActivityOverflowButton;
    private final ImageView mExpandActivityOverflowButtonImage;
    private int mInitialActivityCount = 4;
    private boolean mIsAttachedToWindow;
    private boolean mIsSelectingDefaultActivity;
    private final int mListPopupMaxWidth;
    private ListPopupWindow mListPopupWindow;
    private final DataSetObserver mModelDataSetOberver = new DataSetObserver(){

        @Override
        public void onChanged() {
            super.onChanged();
            ActivityChooserView.this.mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
        }
    };
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

        @Override
        public void onGlobalLayout() {
            if (ActivityChooserView.this.isShowingPopup()) {
                if (!ActivityChooserView.this.isShown()) {
                    ActivityChooserView.this.getListPopupWindow().dismiss();
                } else {
                    ActivityChooserView.this.getListPopupWindow().show();
                    if (ActivityChooserView.this.mProvider != null) {
                        ActivityChooserView.this.mProvider.subUiVisibilityChanged(true);
                    }
                }
            }
        }
    };
    ActionProvider mProvider;

    public ActivityChooserView(Context context) {
        this(context, null);
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ActivityChooserView(Context object, AttributeSet object2, int n, int n2) {
        super((Context)object, (AttributeSet)object2, n, n2);
        Object object3 = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.ActivityChooserView, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.ActivityChooserView, (AttributeSet)object2, (TypedArray)object3, n, n2);
        this.mInitialActivityCount = ((TypedArray)object3).getInt(1, 4);
        object2 = ((TypedArray)object3).getDrawable(0);
        ((TypedArray)object3).recycle();
        LayoutInflater.from(this.mContext).inflate(17367075, (ViewGroup)this, true);
        this.mCallbacks = new Callbacks();
        this.mActivityChooserContent = (LinearLayout)this.findViewById(16908696);
        this.mActivityChooserContentBackground = this.mActivityChooserContent.getBackground();
        this.mDefaultActivityButton = (FrameLayout)this.findViewById(16908873);
        this.mDefaultActivityButton.setOnClickListener(this.mCallbacks);
        this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks);
        this.mDefaultActivityButtonImage = (ImageView)this.mDefaultActivityButton.findViewById(16909000);
        object3 = (FrameLayout)this.findViewById(16908897);
        ((View)object3).setOnClickListener(this.mCallbacks);
        ((View)object3).setAccessibilityDelegate(new View.AccessibilityDelegate(){

            @Override
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setCanOpenPopup(true);
            }
        });
        ((View)object3).setOnTouchListener(new ForwardingListener((View)object3){

            @Override
            public ShowableListMenu getPopup() {
                return ActivityChooserView.this.getListPopupWindow();
            }

            @Override
            protected boolean onForwardingStarted() {
                ActivityChooserView.this.showPopup();
                return true;
            }

            @Override
            protected boolean onForwardingStopped() {
                ActivityChooserView.this.dismissPopup();
                return true;
            }
        });
        this.mExpandActivityOverflowButton = object3;
        this.mExpandActivityOverflowButtonImage = (ImageView)((View)object3).findViewById(16909000);
        this.mExpandActivityOverflowButtonImage.setImageDrawable((Drawable)object2);
        this.mAdapter = new ActivityChooserViewAdapter();
        this.mAdapter.registerDataSetObserver(new DataSetObserver(){

            @Override
            public void onChanged() {
                super.onChanged();
                ActivityChooserView.this.updateAppearance();
            }
        });
        object = ((Context)object).getResources();
        this.mListPopupMaxWidth = Math.max(object.getDisplayMetrics().widthPixels / 2, ((Resources)object).getDimensionPixelSize(17105070));
    }

    private ListPopupWindow getListPopupWindow() {
        if (this.mListPopupWindow == null) {
            this.mListPopupWindow = new ListPopupWindow(this.getContext());
            this.mListPopupWindow.setAdapter(this.mAdapter);
            this.mListPopupWindow.setAnchorView(this);
            this.mListPopupWindow.setModal(true);
            this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
            this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
        }
        return this.mListPopupWindow;
    }

    private void showPopupUnchecked(int n) {
        if (this.mAdapter.getDataModel() != null) {
            this.getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
            boolean bl = this.mDefaultActivityButton.getVisibility() == 0;
            int n2 = this.mAdapter.getActivityCount();
            int n3 = bl ? 1 : 0;
            if (n != Integer.MAX_VALUE && n2 > n + n3) {
                this.mAdapter.setShowFooterView(true);
                this.mAdapter.setMaxActivityCount(n - 1);
            } else {
                this.mAdapter.setShowFooterView(false);
                this.mAdapter.setMaxActivityCount(n);
            }
            ListPopupWindow listPopupWindow = this.getListPopupWindow();
            if (!listPopupWindow.isShowing()) {
                if (!this.mIsSelectingDefaultActivity && bl) {
                    this.mAdapter.setShowDefaultActivity(false, false);
                } else {
                    this.mAdapter.setShowDefaultActivity(true, bl);
                }
                listPopupWindow.setContentWidth(Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth));
                listPopupWindow.show();
                ActionProvider actionProvider = this.mProvider;
                if (actionProvider != null) {
                    actionProvider.subUiVisibilityChanged(true);
                }
                listPopupWindow.getListView().setContentDescription(this.mContext.getString(17039458));
                listPopupWindow.getListView().setSelector(new ColorDrawable(0));
            }
            return;
        }
        throw new IllegalStateException("No data model. Did you call #setDataModel?");
    }

    private void updateAppearance() {
        if (this.mAdapter.getCount() > 0) {
            this.mExpandActivityOverflowButton.setEnabled(true);
        } else {
            this.mExpandActivityOverflowButton.setEnabled(false);
        }
        int n = this.mAdapter.getActivityCount();
        int n2 = this.mAdapter.getHistorySize();
        if (n != 1 && (n <= 1 || n2 <= 0)) {
            this.mDefaultActivityButton.setVisibility(8);
        } else {
            this.mDefaultActivityButton.setVisibility(0);
            Object object = this.mAdapter.getDefaultActivity();
            PackageManager packageManager = this.mContext.getPackageManager();
            this.mDefaultActivityButtonImage.setImageDrawable(((ResolveInfo)object).loadIcon(packageManager));
            if (this.mDefaultActionButtonContentDescription != 0) {
                object = ((ResolveInfo)object).loadLabel(packageManager);
                object = this.mContext.getString(this.mDefaultActionButtonContentDescription, object);
                this.mDefaultActivityButton.setContentDescription((CharSequence)object);
            }
        }
        if (this.mDefaultActivityButton.getVisibility() == 0) {
            this.mActivityChooserContent.setBackground(this.mActivityChooserContentBackground);
        } else {
            this.mActivityChooserContent.setBackground(null);
        }
    }

    public boolean dismissPopup() {
        if (this.isShowingPopup()) {
            this.getListPopupWindow().dismiss();
            ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
            }
        }
        return true;
    }

    public ActivityChooserModel getDataModel() {
        return this.mAdapter.getDataModel();
    }

    public boolean isShowingPopup() {
        return this.getListPopupWindow().isShowing();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ActivityChooserModel activityChooserModel = this.mAdapter.getDataModel();
        if (activityChooserModel != null) {
            activityChooserModel.registerObserver(this.mModelDataSetOberver);
        }
        this.mIsAttachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Object object = this.mAdapter.getDataModel();
        if (object != null) {
            ((Observable)object).unregisterObserver(this.mModelDataSetOberver);
        }
        if (((ViewTreeObserver)(object = this.getViewTreeObserver())).isAlive()) {
            ((ViewTreeObserver)object).removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        }
        if (this.isShowingPopup()) {
            this.dismissPopup();
        }
        this.mIsAttachedToWindow = false;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.mActivityChooserContent.layout(0, 0, n3 - n, n4 - n2);
        if (!this.isShowingPopup()) {
            this.dismissPopup();
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        LinearLayout linearLayout = this.mActivityChooserContent;
        int n3 = n2;
        if (this.mDefaultActivityButton.getVisibility() != 0) {
            n3 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(n2), 1073741824);
        }
        this.measureChild(linearLayout, n, n3);
        this.setMeasuredDimension(linearLayout.getMeasuredWidth(), linearLayout.getMeasuredHeight());
    }

    @Override
    public void setActivityChooserModel(ActivityChooserModel activityChooserModel) {
        this.mAdapter.setDataModel(activityChooserModel);
        if (this.isShowingPopup()) {
            this.dismissPopup();
            this.showPopup();
        }
    }

    public void setDefaultActionButtonContentDescription(int n) {
        this.mDefaultActionButtonContentDescription = n;
    }

    public void setExpandActivityOverflowButtonContentDescription(int n) {
        String string2 = this.mContext.getString(n);
        this.mExpandActivityOverflowButtonImage.setContentDescription(string2);
    }

    @UnsupportedAppUsage
    public void setExpandActivityOverflowButtonDrawable(Drawable drawable2) {
        this.mExpandActivityOverflowButtonImage.setImageDrawable(drawable2);
    }

    public void setInitialActivityCount(int n) {
        this.mInitialActivityCount = n;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setProvider(ActionProvider actionProvider) {
        this.mProvider = actionProvider;
    }

    public boolean showPopup() {
        if (!this.isShowingPopup() && this.mIsAttachedToWindow) {
            this.mIsSelectingDefaultActivity = false;
            this.showPopupUnchecked(this.mInitialActivityCount);
            return true;
        }
        return false;
    }

    private class ActivityChooserViewAdapter
    extends BaseAdapter {
        private static final int ITEM_VIEW_TYPE_ACTIVITY = 0;
        private static final int ITEM_VIEW_TYPE_COUNT = 3;
        private static final int ITEM_VIEW_TYPE_FOOTER = 1;
        public static final int MAX_ACTIVITY_COUNT_DEFAULT = 4;
        public static final int MAX_ACTIVITY_COUNT_UNLIMITED = Integer.MAX_VALUE;
        private ActivityChooserModel mDataModel;
        private boolean mHighlightDefaultActivity;
        private int mMaxActivityCount = 4;
        private boolean mShowDefaultActivity;
        private boolean mShowFooterView;

        private ActivityChooserViewAdapter() {
        }

        public int getActivityCount() {
            return this.mDataModel.getActivityCount();
        }

        @Override
        public int getCount() {
            int n;
            int n2 = n = this.mDataModel.getActivityCount();
            if (!this.mShowDefaultActivity) {
                n2 = n;
                if (this.mDataModel.getDefaultActivity() != null) {
                    n2 = n - 1;
                }
            }
            n2 = n = Math.min(n2, this.mMaxActivityCount);
            if (this.mShowFooterView) {
                n2 = n + 1;
            }
            return n2;
        }

        public ActivityChooserModel getDataModel() {
            return this.mDataModel;
        }

        public ResolveInfo getDefaultActivity() {
            return this.mDataModel.getDefaultActivity();
        }

        public int getHistorySize() {
            return this.mDataModel.getHistorySize();
        }

        @Override
        public Object getItem(int n) {
            int n2 = this.getItemViewType(n);
            if (n2 != 0) {
                if (n2 == 1) {
                    return null;
                }
                throw new IllegalArgumentException();
            }
            n2 = n;
            if (!this.mShowDefaultActivity) {
                n2 = n;
                if (this.mDataModel.getDefaultActivity() != null) {
                    n2 = n + 1;
                }
            }
            return this.mDataModel.getActivity(n2);
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        @Override
        public int getItemViewType(int n) {
            return this.mShowFooterView && n == this.getCount() - 1;
        }

        public boolean getShowDefaultActivity() {
            return this.mShowDefaultActivity;
        }

        @Override
        public View getView(int n, View object, ViewGroup object2) {
            View view;
            block13 : {
                block12 : {
                    block8 : {
                        block9 : {
                            View view2;
                            block11 : {
                                block10 : {
                                    int n2 = this.getItemViewType(n);
                                    if (n2 == 0) break block8;
                                    if (n2 != 1) break block9;
                                    if (object == null) break block10;
                                    view2 = object;
                                    if (((View)object).getId() == 1) break block11;
                                }
                                view2 = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(17367076, (ViewGroup)object2, false);
                                view2.setId(1);
                                ((TextView)view2.findViewById(16908310)).setText(ActivityChooserView.this.mContext.getString(17039451));
                            }
                            return view2;
                        }
                        throw new IllegalArgumentException();
                    }
                    if (object == null) break block12;
                    view = object;
                    if (((View)object).getId() == 16909073) break block13;
                }
                view = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(17367076, (ViewGroup)object2, false);
            }
            object = ActivityChooserView.this.mContext.getPackageManager();
            ImageView imageView = (ImageView)view.findViewById(16908294);
            object2 = (ResolveInfo)this.getItem(n);
            imageView.setImageDrawable(((ResolveInfo)object2).loadIcon((PackageManager)object));
            ((TextView)view.findViewById(16908310)).setText(((ResolveInfo)object2).loadLabel((PackageManager)object));
            if (this.mShowDefaultActivity && n == 0 && this.mHighlightDefaultActivity) {
                view.setActivated(true);
            } else {
                view.setActivated(false);
            }
            return view;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        public int measureContentWidth() {
            int n = this.mMaxActivityCount;
            this.mMaxActivityCount = Integer.MAX_VALUE;
            int n2 = 0;
            View view = null;
            int n3 = View.MeasureSpec.makeMeasureSpec(0, 0);
            int n4 = View.MeasureSpec.makeMeasureSpec(0, 0);
            int n5 = this.getCount();
            for (int i = 0; i < n5; ++i) {
                view = this.getView(i, view, null);
                view.measure(n3, n4);
                n2 = Math.max(n2, view.getMeasuredWidth());
            }
            this.mMaxActivityCount = n;
            return n2;
        }

        public void setDataModel(ActivityChooserModel activityChooserModel) {
            ActivityChooserModel activityChooserModel2 = ActivityChooserView.this.mAdapter.getDataModel();
            if (activityChooserModel2 != null && ActivityChooserView.this.isShown()) {
                activityChooserModel2.unregisterObserver(ActivityChooserView.this.mModelDataSetOberver);
            }
            this.mDataModel = activityChooserModel;
            if (activityChooserModel != null && ActivityChooserView.this.isShown()) {
                activityChooserModel.registerObserver(ActivityChooserView.this.mModelDataSetOberver);
            }
            this.notifyDataSetChanged();
        }

        public void setMaxActivityCount(int n) {
            if (this.mMaxActivityCount != n) {
                this.mMaxActivityCount = n;
                this.notifyDataSetChanged();
            }
        }

        public void setShowDefaultActivity(boolean bl, boolean bl2) {
            if (this.mShowDefaultActivity != bl || this.mHighlightDefaultActivity != bl2) {
                this.mShowDefaultActivity = bl;
                this.mHighlightDefaultActivity = bl2;
                this.notifyDataSetChanged();
            }
        }

        public void setShowFooterView(boolean bl) {
            if (this.mShowFooterView != bl) {
                this.mShowFooterView = bl;
                this.notifyDataSetChanged();
            }
        }
    }

    private class Callbacks
    implements AdapterView.OnItemClickListener,
    View.OnClickListener,
    View.OnLongClickListener,
    PopupWindow.OnDismissListener {
        private Callbacks() {
        }

        private void notifyOnDismissListener() {
            if (ActivityChooserView.this.mOnDismissListener != null) {
                ActivityChooserView.this.mOnDismissListener.onDismiss();
            }
        }

        private void startActivity(Intent intent, ResolveInfo resolveInfo) {
            try {
                ActivityChooserView.this.mContext.startActivity(intent);
            }
            catch (RuntimeException runtimeException) {
                CharSequence charSequence = resolveInfo.loadLabel(ActivityChooserView.this.mContext.getPackageManager());
                charSequence = ActivityChooserView.this.mContext.getString(17039459, charSequence);
                Log.e(ActivityChooserView.LOG_TAG, (String)charSequence);
                Toast.makeText(ActivityChooserView.this.mContext, charSequence, 0).show();
            }
        }

        @Override
        public void onClick(View object) {
            block7 : {
                block6 : {
                    block5 : {
                        if (object != ActivityChooserView.this.mDefaultActivityButton) break block5;
                        ActivityChooserView.this.dismissPopup();
                        object = ActivityChooserView.this.mAdapter.getDefaultActivity();
                        int n = ActivityChooserView.this.mAdapter.getDataModel().getActivityIndex((ResolveInfo)object);
                        Intent intent = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(n);
                        if (intent != null) {
                            intent.addFlags(524288);
                            this.startActivity(intent, (ResolveInfo)object);
                        }
                        break block6;
                    }
                    if (object != ActivityChooserView.this.mExpandActivityOverflowButton) break block7;
                    ActivityChooserView.this.mIsSelectingDefaultActivity = false;
                    object = ActivityChooserView.this;
                    ((ActivityChooserView)object).showPopupUnchecked(((ActivityChooserView)object).mInitialActivityCount);
                }
                return;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public void onDismiss() {
            this.notifyOnDismissListener();
            if (ActivityChooserView.this.mProvider != null) {
                ActivityChooserView.this.mProvider.subUiVisibilityChanged(false);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void onItemClick(AdapterView<?> object, View view, int n, long l) {
            int n2 = ((ActivityChooserViewAdapter)((AdapterView)object).getAdapter()).getItemViewType(n);
            if (n2 != 0) {
                if (n2 != 1) throw new IllegalArgumentException();
                ActivityChooserView.this.showPopupUnchecked(Integer.MAX_VALUE);
                return;
            } else {
                ActivityChooserView.this.dismissPopup();
                if (ActivityChooserView.this.mIsSelectingDefaultActivity) {
                    if (n <= 0) return;
                    ActivityChooserView.this.mAdapter.getDataModel().setDefaultActivity(n);
                    return;
                } else {
                    if (!ActivityChooserView.this.mAdapter.getShowDefaultActivity()) {
                        ++n;
                    }
                    object = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(n);
                    if (object == null) return;
                    ((Intent)object).addFlags(524288);
                    this.startActivity((Intent)object, ActivityChooserView.this.mAdapter.getDataModel().getActivity(n));
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (view == ActivityChooserView.this.mDefaultActivityButton) {
                if (ActivityChooserView.this.mAdapter.getCount() > 0) {
                    ActivityChooserView.this.mIsSelectingDefaultActivity = true;
                    view = ActivityChooserView.this;
                    ((ActivityChooserView)view).showPopupUnchecked(((ActivityChooserView)view).mInitialActivityCount);
                }
                return true;
            }
            throw new IllegalArgumentException();
        }
    }

}

