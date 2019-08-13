/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.app.AlertController;

public abstract class AlertActivity
extends Activity
implements DialogInterface {
    @UnsupportedAppUsage
    protected AlertController mAlert;
    @UnsupportedAppUsage
    protected AlertController.AlertParams mAlertParams;

    public static boolean dispatchPopulateAccessibilityEvent(Activity object, AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(Dialog.class.getName());
        accessibilityEvent.setPackageName(((ContextWrapper)object).getPackageName());
        object = ((Activity)object).getWindow().getAttributes();
        boolean bl = ((ViewGroup.LayoutParams)object).width == -1 && ((ViewGroup.LayoutParams)object).height == -1;
        accessibilityEvent.setFullScreen(bl);
        return false;
    }

    @Override
    public void cancel() {
        this.finish();
    }

    @Override
    public void dismiss() {
        if (!this.isFinishing()) {
            this.finish();
        }
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return AlertActivity.dispatchPopulateAccessibilityEvent(this, accessibilityEvent);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert = AlertController.create(this, this, this.getWindow());
        this.mAlertParams = new AlertController.AlertParams(this);
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (this.mAlert.onKeyDown(n, keyEvent)) {
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (this.mAlert.onKeyUp(n, keyEvent)) {
            return true;
        }
        return super.onKeyUp(n, keyEvent);
    }

    @UnsupportedAppUsage
    protected void setupAlert() {
        this.mAlert.installContent(this.mAlertParams);
    }
}

