/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.android.internal.app._$$Lambda$AccessibilityButtonChooserActivity$VBT2N_0vKxB2VkOg6zxi5sAX6xc;
import com.android.internal.app._$$Lambda$EK3sgUmlvAVQupMeTV9feOrWuPE;
import com.android.internal.widget.ResolverDrawerLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccessibilityButtonChooserActivity
extends Activity {
    private static final String MAGNIFICATION_COMPONENT_ID = "com.android.server.accessibility.MagnificationController";
    private AccessibilityButtonTarget mMagnificationTarget = null;
    private List<AccessibilityButtonTarget> mTargets = null;

    private static List<AccessibilityButtonTarget> getServiceAccessibilityButtonTargets(Context context) {
        List<AccessibilityServiceInfo> list2 = ((AccessibilityManager)context.getSystemService("accessibility")).getEnabledAccessibilityServiceList(-1);
        if (list2 == null) {
            return Collections.emptyList();
        }
        ArrayList<AccessibilityButtonTarget> arrayList = new ArrayList<AccessibilityButtonTarget>(list2.size());
        for (AccessibilityServiceInfo accessibilityServiceInfo : list2) {
            if ((accessibilityServiceInfo.flags & 256) == 0) continue;
            arrayList.add(new AccessibilityButtonTarget(context, accessibilityServiceInfo));
        }
        return arrayList;
    }

    private boolean isGestureNavigateEnabled() {
        boolean bl = 2 == this.getResources().getInteger(17694847);
        return bl;
    }

    private boolean isTouchExploreOn() {
        return ((AccessibilityManager)this.getSystemService("accessibility")).isTouchExplorationEnabled();
    }

    private void onTargetSelected(AccessibilityButtonTarget accessibilityButtonTarget) {
        Settings.Secure.putString(this.getContentResolver(), "accessibility_button_target_component", accessibilityButtonTarget.getId());
        this.finish();
    }

    public /* synthetic */ void lambda$onCreate$0$AccessibilityButtonChooserActivity(AdapterView adapterView, View view, int n, long l) {
        this.onTargetSelected(this.mTargets.get(n));
    }

    @Override
    protected void onCreate(Bundle object) {
        int n;
        super.onCreate((Bundle)object);
        this.setContentView(17367064);
        object = (ResolverDrawerLayout)this.findViewById(16908829);
        if (object != null) {
            ((ResolverDrawerLayout)object).setOnDismissedListener(new _$$Lambda$EK3sgUmlvAVQupMeTV9feOrWuPE(this));
        }
        object = Settings.Secure.getString(this.getContentResolver(), "accessibility_button_target_component");
        if (this.isGestureNavigateEnabled()) {
            TextView textView = (TextView)this.findViewById(16908661);
            n = this.isTouchExploreOn() ? 17039435 : 17039437;
            textView.setText(n);
        }
        if (TextUtils.isEmpty((CharSequence)object)) {
            object = (TextView)this.findViewById(16908660);
            if (this.isGestureNavigateEnabled()) {
                n = this.isTouchExploreOn() ? 17039434 : 17039436;
                ((TextView)object).setText(n);
            }
            ((View)object).setVisibility(0);
        }
        this.mMagnificationTarget = new AccessibilityButtonTarget(this, MAGNIFICATION_COMPONENT_ID, 17039438, 17302279);
        this.mTargets = AccessibilityButtonChooserActivity.getServiceAccessibilityButtonTargets(this);
        if (Settings.Secure.getInt(this.getContentResolver(), "accessibility_display_magnification_navbar_enabled", 0) == 1) {
            this.mTargets.add(this.mMagnificationTarget);
        }
        if (this.mTargets.size() < 2) {
            this.finish();
        }
        object = (GridView)this.findViewById(16908659);
        ((GridView)object).setAdapter(new TargetAdapter());
        ((AdapterView)object).setOnItemClickListener(new _$$Lambda$AccessibilityButtonChooserActivity$VBT2N_0vKxB2VkOg6zxi5sAX6xc(this));
    }

    private static class AccessibilityButtonTarget {
        public Drawable mDrawable;
        public String mId;
        public CharSequence mLabel;

        public AccessibilityButtonTarget(Context context, AccessibilityServiceInfo accessibilityServiceInfo) {
            this.mId = accessibilityServiceInfo.getComponentName().flattenToString();
            this.mLabel = accessibilityServiceInfo.getResolveInfo().loadLabel(context.getPackageManager());
            this.mDrawable = accessibilityServiceInfo.getResolveInfo().loadIcon(context.getPackageManager());
        }

        public AccessibilityButtonTarget(Context context, String string2, int n, int n2) {
            this.mId = string2;
            this.mLabel = context.getText(n);
            this.mDrawable = context.getDrawable(n2);
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }

        public String getId() {
            return this.mId;
        }

        public CharSequence getLabel() {
            return this.mLabel;
        }
    }

    private class TargetAdapter
    extends BaseAdapter {
        private TargetAdapter() {
        }

        @Override
        public int getCount() {
            return AccessibilityButtonChooserActivity.this.mTargets.size();
        }

        @Override
        public Object getItem(int n) {
            return null;
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        @Override
        public View getView(int n, View object, ViewGroup view) {
            View view2 = AccessibilityButtonChooserActivity.this.getLayoutInflater().inflate(17367065, (ViewGroup)view, false);
            object = (AccessibilityButtonTarget)AccessibilityButtonChooserActivity.this.mTargets.get(n);
            view = (ImageView)view2.findViewById(16908662);
            TextView textView = (TextView)view2.findViewById(16908663);
            ((ImageView)view).setImageDrawable(((AccessibilityButtonTarget)object).getDrawable());
            textView.setText(((AccessibilityButtonTarget)object).getLabel());
            return view2;
        }
    }

}

