/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.List;

public class TabHost
extends FrameLayout
implements ViewTreeObserver.OnTouchModeChangeListener {
    private static final int TABWIDGET_LOCATION_BOTTOM = 3;
    private static final int TABWIDGET_LOCATION_LEFT = 0;
    private static final int TABWIDGET_LOCATION_RIGHT = 2;
    private static final int TABWIDGET_LOCATION_TOP = 1;
    @UnsupportedAppUsage
    protected int mCurrentTab = -1;
    private View mCurrentView = null;
    protected LocalActivityManager mLocalActivityManager = null;
    @UnsupportedAppUsage
    private OnTabChangeListener mOnTabChangeListener;
    private FrameLayout mTabContent;
    private View.OnKeyListener mTabKeyListener;
    private int mTabLayoutId;
    @UnsupportedAppUsage
    private List<TabSpec> mTabSpecs = new ArrayList<TabSpec>(2);
    private TabWidget mTabWidget;

    public TabHost(Context context) {
        super(context);
        this.initTabHost();
    }

    public TabHost(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842883);
    }

    public TabHost(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public TabHost(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TabWidget, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.TabWidget, attributeSet, typedArray, n, n2);
        this.mTabLayoutId = typedArray.getResourceId(4, 0);
        typedArray.recycle();
        if (this.mTabLayoutId == 0) {
            this.mTabLayoutId = 17367306;
        }
        this.initTabHost();
    }

    private int getTabWidgetLocation() {
        int n = this.mTabWidget.getOrientation();
        int n2 = 1;
        if (n != 1) {
            if (this.mTabContent.getTop() < this.mTabWidget.getTop()) {
                n2 = 3;
            }
        } else {
            n2 = this.mTabContent.getLeft() < this.mTabWidget.getLeft() ? 2 : 0;
        }
        return n2;
    }

    private void initTabHost() {
        this.setFocusableInTouchMode(true);
        this.setDescendantFocusability(262144);
        this.mCurrentTab = -1;
        this.mCurrentView = null;
    }

    private void invokeOnTabChangeListener() {
        OnTabChangeListener onTabChangeListener = this.mOnTabChangeListener;
        if (onTabChangeListener != null) {
            onTabChangeListener.onTabChanged(this.getCurrentTabTag());
        }
    }

    public void addTab(TabSpec tabSpec) {
        if (tabSpec.mIndicatorStrategy != null) {
            if (tabSpec.mContentStrategy != null) {
                View view = tabSpec.mIndicatorStrategy.createIndicatorView();
                view.setOnKeyListener(this.mTabKeyListener);
                if (tabSpec.mIndicatorStrategy instanceof ViewIndicatorStrategy) {
                    this.mTabWidget.setStripEnabled(false);
                }
                this.mTabWidget.addView(view);
                this.mTabSpecs.add(tabSpec);
                if (this.mCurrentTab == -1) {
                    this.setCurrentTab(0);
                }
                return;
            }
            throw new IllegalArgumentException("you must specify a way to create the tab content");
        }
        throw new IllegalArgumentException("you must specify a way to create the tab indicator.");
    }

    public void clearAllTabs() {
        this.mTabWidget.removeAllViews();
        this.initTabHost();
        this.mTabContent.removeAllViews();
        this.mTabSpecs.clear();
        this.requestLayout();
        this.invalidate();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View view;
        boolean bl = super.dispatchKeyEvent(keyEvent);
        if (!bl && keyEvent.getAction() == 0 && (view = this.mCurrentView) != null && view.isRootNamespace() && this.mCurrentView.hasFocus()) {
            int n;
            int n2;
            int n3 = this.getTabWidgetLocation();
            if (n3 != 0) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        n3 = 19;
                        n = 33;
                        n2 = 2;
                    } else {
                        n3 = 20;
                        n = 130;
                        n2 = 4;
                    }
                } else {
                    n3 = 22;
                    n = 66;
                    n2 = 3;
                }
            } else {
                n3 = 21;
                n = 17;
                n2 = 1;
            }
            if (keyEvent.getKeyCode() == n3 && this.mCurrentView.findFocus().focusSearch(n) == null) {
                this.mTabWidget.getChildTabViewAt(this.mCurrentTab).requestFocus();
                this.playSoundEffect(n2);
                return true;
            }
        }
        return bl;
    }

    @Override
    public void dispatchWindowFocusChanged(boolean bl) {
        View view = this.mCurrentView;
        if (view != null) {
            view.dispatchWindowFocusChanged(bl);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return TabHost.class.getName();
    }

    public int getCurrentTab() {
        return this.mCurrentTab;
    }

    public String getCurrentTabTag() {
        int n = this.mCurrentTab;
        if (n >= 0 && n < this.mTabSpecs.size()) {
            return this.mTabSpecs.get(this.mCurrentTab).getTag();
        }
        return null;
    }

    public View getCurrentTabView() {
        int n = this.mCurrentTab;
        if (n >= 0 && n < this.mTabSpecs.size()) {
            return this.mTabWidget.getChildTabViewAt(this.mCurrentTab);
        }
        return null;
    }

    public View getCurrentView() {
        return this.mCurrentView;
    }

    public FrameLayout getTabContentView() {
        return this.mTabContent;
    }

    public TabWidget getTabWidget() {
        return this.mTabWidget;
    }

    public TabSpec newTabSpec(String string2) {
        if (string2 != null) {
            return new TabSpec(string2);
        }
        throw new IllegalArgumentException("tag must be non-null");
    }

    @Override
    public void onTouchModeChanged(boolean bl) {
    }

    @Override
    public void sendAccessibilityEventInternal(int n) {
    }

    public void setCurrentTab(int n) {
        if (n >= 0 && n < this.mTabSpecs.size()) {
            int n2 = this.mCurrentTab;
            if (n == n2) {
                return;
            }
            if (n2 != -1) {
                this.mTabSpecs.get(n2).mContentStrategy.tabClosed();
            }
            this.mCurrentTab = n;
            TabSpec tabSpec = this.mTabSpecs.get(n);
            this.mTabWidget.focusCurrentTab(this.mCurrentTab);
            this.mCurrentView = tabSpec.mContentStrategy.getContentView();
            if (this.mCurrentView.getParent() == null) {
                this.mTabContent.addView(this.mCurrentView, new ViewGroup.LayoutParams(-1, -1));
            }
            if (!this.mTabWidget.hasFocus()) {
                this.mCurrentView.requestFocus();
            }
            this.invokeOnTabChangeListener();
            return;
        }
    }

    public void setCurrentTabByTag(String string2) {
        int n = this.mTabSpecs.size();
        for (int i = 0; i < n; ++i) {
            if (!this.mTabSpecs.get(i).getTag().equals(string2)) continue;
            this.setCurrentTab(i);
            break;
        }
    }

    public void setOnTabChangedListener(OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener;
    }

    public void setup() {
        this.mTabWidget = (TabWidget)this.findViewById(16908307);
        if (this.mTabWidget != null) {
            this.mTabKeyListener = new View.OnKeyListener(){

                @Override
                public boolean onKey(View view, int n, KeyEvent keyEvent) {
                    if (KeyEvent.isModifierKey(n)) {
                        return false;
                    }
                    if (n != 61 && n != 62 && n != 66) {
                        switch (n) {
                            default: {
                                TabHost.this.mTabContent.requestFocus(2);
                                return TabHost.this.mTabContent.dispatchKeyEvent(keyEvent);
                            }
                            case 19: 
                            case 20: 
                            case 21: 
                            case 22: 
                            case 23: 
                        }
                    }
                    return false;
                }
            };
            this.mTabWidget.setTabSelectionListener(new TabWidget.OnTabSelectionChanged(){

                @Override
                public void onTabSelectionChanged(int n, boolean bl) {
                    TabHost.this.setCurrentTab(n);
                    if (bl) {
                        TabHost.this.mTabContent.requestFocus(2);
                    }
                }
            });
            this.mTabContent = (FrameLayout)this.findViewById(16908305);
            if (this.mTabContent != null) {
                return;
            }
            throw new RuntimeException("Your TabHost must have a FrameLayout whose id attribute is 'android.R.id.tabcontent'");
        }
        throw new RuntimeException("Your TabHost must have a TabWidget whose id attribute is 'android.R.id.tabs'");
    }

    public void setup(LocalActivityManager localActivityManager) {
        this.setup();
        this.mLocalActivityManager = localActivityManager;
    }

    private static interface ContentStrategy {
        public View getContentView();

        public void tabClosed();
    }

    private class FactoryContentStrategy
    implements ContentStrategy {
        private TabContentFactory mFactory;
        private View mTabContent;
        private final CharSequence mTag;

        public FactoryContentStrategy(CharSequence charSequence, TabContentFactory tabContentFactory) {
            this.mTag = charSequence;
            this.mFactory = tabContentFactory;
        }

        @Override
        public View getContentView() {
            if (this.mTabContent == null) {
                this.mTabContent = this.mFactory.createTabContent(this.mTag.toString());
            }
            this.mTabContent.setVisibility(0);
            return this.mTabContent;
        }

        @Override
        public void tabClosed() {
            this.mTabContent.setVisibility(8);
        }
    }

    private static interface IndicatorStrategy {
        public View createIndicatorView();
    }

    private class IntentContentStrategy
    implements ContentStrategy {
        private final Intent mIntent;
        private View mLaunchedView;
        private final String mTag;

        private IntentContentStrategy(String string2, Intent intent) {
            this.mTag = string2;
            this.mIntent = intent;
        }

        @UnsupportedAppUsage
        @Override
        public View getContentView() {
            if (TabHost.this.mLocalActivityManager != null) {
                View view = this.mLaunchedView;
                Object object = TabHost.this.mLocalActivityManager.startActivity(this.mTag, this.mIntent);
                object = object != null ? ((Window)object).getDecorView() : null;
                if (view != object && view != null && view.getParent() != null) {
                    TabHost.this.mTabContent.removeView(this.mLaunchedView);
                }
                if ((object = (this.mLaunchedView = object)) != null) {
                    ((View)object).setVisibility(0);
                    this.mLaunchedView.setFocusableInTouchMode(true);
                    ((ViewGroup)this.mLaunchedView).setDescendantFocusability(262144);
                }
                return this.mLaunchedView;
            }
            throw new IllegalStateException("Did you forget to call 'public void setup(LocalActivityManager activityGroup)'?");
        }

        @UnsupportedAppUsage
        @Override
        public void tabClosed() {
            View view = this.mLaunchedView;
            if (view != null) {
                view.setVisibility(8);
            }
        }
    }

    private class LabelAndIconIndicatorStrategy
    implements IndicatorStrategy {
        private final Drawable mIcon;
        private final CharSequence mLabel;

        private LabelAndIconIndicatorStrategy(CharSequence charSequence, Drawable drawable2) {
            this.mLabel = charSequence;
            this.mIcon = drawable2;
        }

        @Override
        public View createIndicatorView() {
            Drawable drawable2;
            Context context = TabHost.this.getContext();
            View view = ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(TabHost.this.mTabLayoutId, (ViewGroup)TabHost.this.mTabWidget, false);
            TextView textView = (TextView)view.findViewById(16908310);
            ImageView imageView = (ImageView)view.findViewById(16908294);
            int n = imageView.getVisibility();
            int n2 = 1;
            boolean bl = n == 8;
            n = n2;
            if (bl) {
                n = TextUtils.isEmpty(this.mLabel) ? n2 : 0;
            }
            textView.setText(this.mLabel);
            if (n != 0 && (drawable2 = this.mIcon) != null) {
                imageView.setImageDrawable(drawable2);
                imageView.setVisibility(0);
            }
            if (context.getApplicationInfo().targetSdkVersion <= 4) {
                view.setBackgroundResource(17303669);
                textView.setTextColor(context.getColorStateList(17170989));
            }
            return view;
        }
    }

    private class LabelIndicatorStrategy
    implements IndicatorStrategy {
        private final CharSequence mLabel;

        private LabelIndicatorStrategy(CharSequence charSequence) {
            this.mLabel = charSequence;
        }

        @Override
        public View createIndicatorView() {
            Context context = TabHost.this.getContext();
            View view = ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(TabHost.this.mTabLayoutId, (ViewGroup)TabHost.this.mTabWidget, false);
            TextView textView = (TextView)view.findViewById(16908310);
            textView.setText(this.mLabel);
            if (context.getApplicationInfo().targetSdkVersion <= 4) {
                view.setBackgroundResource(17303669);
                textView.setTextColor(context.getColorStateList(17170989));
            }
            return view;
        }
    }

    public static interface OnTabChangeListener {
        public void onTabChanged(String var1);
    }

    public static interface TabContentFactory {
        public View createTabContent(String var1);
    }

    public class TabSpec {
        @UnsupportedAppUsage
        private ContentStrategy mContentStrategy;
        @UnsupportedAppUsage
        private IndicatorStrategy mIndicatorStrategy;
        private final String mTag;

        private TabSpec(String string2) {
            this.mTag = string2;
        }

        public String getTag() {
            return this.mTag;
        }

        public TabSpec setContent(int n) {
            this.mContentStrategy = new ViewIdContentStrategy(n);
            return this;
        }

        public TabSpec setContent(Intent intent) {
            this.mContentStrategy = new IntentContentStrategy(this.mTag, intent);
            return this;
        }

        public TabSpec setContent(TabContentFactory tabContentFactory) {
            this.mContentStrategy = new FactoryContentStrategy(this.mTag, tabContentFactory);
            return this;
        }

        public TabSpec setIndicator(View view) {
            this.mIndicatorStrategy = new ViewIndicatorStrategy(view);
            return this;
        }

        public TabSpec setIndicator(CharSequence charSequence) {
            this.mIndicatorStrategy = new LabelIndicatorStrategy(charSequence);
            return this;
        }

        public TabSpec setIndicator(CharSequence charSequence, Drawable drawable2) {
            this.mIndicatorStrategy = new LabelAndIconIndicatorStrategy(charSequence, drawable2);
            return this;
        }
    }

    private class ViewIdContentStrategy
    implements ContentStrategy {
        private final View mView;

        private ViewIdContentStrategy(int n) {
            this.mView = ((TabHost)TabHost.this).mTabContent.findViewById(n);
            TabHost.this = this.mView;
            if (TabHost.this != null) {
                ((View)TabHost.this).setVisibility(8);
                return;
            }
            TabHost.this = new StringBuilder();
            ((StringBuilder)TabHost.this).append("Could not create tab content because could not find view with id ");
            ((StringBuilder)TabHost.this).append(n);
            throw new RuntimeException(((StringBuilder)TabHost.this).toString());
        }

        @Override
        public View getContentView() {
            this.mView.setVisibility(0);
            return this.mView;
        }

        @Override
        public void tabClosed() {
            this.mView.setVisibility(8);
        }
    }

    private class ViewIndicatorStrategy
    implements IndicatorStrategy {
        private final View mView;

        private ViewIndicatorStrategy(View view) {
            this.mView = view;
        }

        @Override
        public View createIndicatorView() {
            return this.mView;
        }
    }

}

