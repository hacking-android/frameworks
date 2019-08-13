/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ActivityChooserModel;
import android.widget.ActivityChooserView;

public class ShareActionProvider
extends ActionProvider {
    private static final int DEFAULT_INITIAL_ACTIVITY_COUNT = 4;
    public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
    private final Context mContext;
    private int mMaxShownActivityCount = 4;
    private ActivityChooserModel.OnChooseActivityListener mOnChooseActivityListener;
    private final ShareMenuItemOnMenuItemClickListener mOnMenuItemClickListener = new ShareMenuItemOnMenuItemClickListener();
    private OnShareTargetSelectedListener mOnShareTargetSelectedListener;
    private String mShareHistoryFileName = "share_history.xml";

    public ShareActionProvider(Context context) {
        super(context);
        this.mContext = context;
    }

    private void setActivityChooserPolicyIfNeeded() {
        if (this.mOnShareTargetSelectedListener == null) {
            return;
        }
        if (this.mOnChooseActivityListener == null) {
            this.mOnChooseActivityListener = new ShareActivityChooserModelPolicy();
        }
        ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName).setOnChooseActivityListener(this.mOnChooseActivityListener);
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }

    @Override
    public View onCreateActionView() {
        ActivityChooserView activityChooserView = new ActivityChooserView(this.mContext);
        if (!activityChooserView.isInEditMode()) {
            activityChooserView.setActivityChooserModel(ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName));
        }
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(16843897, typedValue, true);
        activityChooserView.setExpandActivityOverflowButtonDrawable(this.mContext.getDrawable(typedValue.resourceId));
        activityChooserView.setProvider(this);
        activityChooserView.setDefaultActionButtonContentDescription(17041012);
        activityChooserView.setExpandActivityOverflowButtonContentDescription(17041011);
        return activityChooserView;
    }

    @Override
    public void onPrepareSubMenu(SubMenu object) {
        Object object2;
        int n;
        object.clear();
        ActivityChooserModel activityChooserModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
        PackageManager packageManager = this.mContext.getPackageManager();
        int n2 = activityChooserModel.getActivityCount();
        int n3 = Math.min(n2, this.mMaxShownActivityCount);
        for (n = 0; n < n3; ++n) {
            object2 = activityChooserModel.getActivity(n);
            object.add(0, n, n, ((ResolveInfo)object2).loadLabel(packageManager)).setIcon(((ResolveInfo)object2).loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
        }
        if (n3 < n2) {
            object2 = object.addSubMenu(0, n3, n3, this.mContext.getString(17039451));
            for (n = 0; n < n2; ++n) {
                object = activityChooserModel.getActivity(n);
                object2.add(0, n, n, ((ResolveInfo)object).loadLabel(packageManager)).setIcon(((ResolveInfo)object).loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
            }
        }
    }

    public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener onShareTargetSelectedListener) {
        this.mOnShareTargetSelectedListener = onShareTargetSelectedListener;
        this.setActivityChooserPolicyIfNeeded();
    }

    public void setShareHistoryFileName(String string2) {
        this.mShareHistoryFileName = string2;
        this.setActivityChooserPolicyIfNeeded();
    }

    public void setShareIntent(Intent intent) {
        String string2;
        if (intent != null && ("android.intent.action.SEND".equals(string2 = intent.getAction()) || "android.intent.action.SEND_MULTIPLE".equals(string2))) {
            intent.addFlags(134742016);
        }
        ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName).setIntent(intent);
    }

    public static interface OnShareTargetSelectedListener {
        public boolean onShareTargetSelected(ShareActionProvider var1, Intent var2);
    }

    private class ShareActivityChooserModelPolicy
    implements ActivityChooserModel.OnChooseActivityListener {
        private ShareActivityChooserModelPolicy() {
        }

        @Override
        public boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent) {
            if (ShareActionProvider.this.mOnShareTargetSelectedListener != null) {
                ShareActionProvider.this.mOnShareTargetSelectedListener.onShareTargetSelected(ShareActionProvider.this, intent);
            }
            return false;
        }
    }

    private class ShareMenuItemOnMenuItemClickListener
    implements MenuItem.OnMenuItemClickListener {
        private ShareMenuItemOnMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem object) {
            object = ActivityChooserModel.get(ShareActionProvider.this.mContext, ShareActionProvider.this.mShareHistoryFileName).chooseActivity(object.getItemId());
            if (object != null) {
                String string2 = ((Intent)object).getAction();
                if ("android.intent.action.SEND".equals(string2) || "android.intent.action.SEND_MULTIPLE".equals(string2)) {
                    ((Intent)object).addFlags(134742016);
                }
                ShareActionProvider.this.mContext.startActivity((Intent)object);
            }
            return true;
        }
    }

}

