/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.os.BaseBundle;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

@Deprecated
public class TabActivity
extends ActivityGroup {
    private String mDefaultTab = null;
    private int mDefaultTabIndex = -1;
    private TabHost mTabHost;

    private void ensureTabHost() {
        if (this.mTabHost == null) {
            this.setContentView(17367304);
        }
    }

    public TabHost getTabHost() {
        this.ensureTabHost();
        return this.mTabHost;
    }

    public TabWidget getTabWidget() {
        return this.mTabHost.getTabWidget();
    }

    @Override
    protected void onChildTitleChanged(Activity callback, CharSequence charSequence) {
        if (this.getLocalActivityManager().getCurrentActivity() == callback && (callback = this.mTabHost.getCurrentTabView()) != null && callback instanceof TextView) {
            ((TextView)callback).setText(charSequence);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        TabHost tabHost = this.mTabHost = (TabHost)this.findViewById(16908306);
        if (tabHost != null) {
            tabHost.setup(this.getLocalActivityManager());
            return;
        }
        throw new RuntimeException("Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'");
    }

    @Override
    protected void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.ensureTabHost();
        if (this.mTabHost.getCurrentTab() == -1) {
            this.mTabHost.setCurrentTab(0);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle object) {
        super.onRestoreInstanceState((Bundle)object);
        this.ensureTabHost();
        object = ((BaseBundle)object).getString("currentTab");
        if (object != null) {
            this.mTabHost.setCurrentTabByTag((String)object);
        }
        if (this.mTabHost.getCurrentTab() < 0) {
            object = this.mDefaultTab;
            if (object != null) {
                this.mTabHost.setCurrentTabByTag((String)object);
            } else {
                int n = this.mDefaultTabIndex;
                if (n >= 0) {
                    this.mTabHost.setCurrentTab(n);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        String string2 = this.mTabHost.getCurrentTabTag();
        if (string2 != null) {
            bundle.putString("currentTab", string2);
        }
    }

    public void setDefaultTab(int n) {
        this.mDefaultTab = null;
        this.mDefaultTabIndex = n;
    }

    public void setDefaultTab(String string2) {
        this.mDefaultTab = string2;
        this.mDefaultTabIndex = -1;
    }
}

