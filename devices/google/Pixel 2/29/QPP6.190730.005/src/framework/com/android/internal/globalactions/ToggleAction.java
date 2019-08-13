/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.globalactions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.globalactions.Action;

public abstract class ToggleAction
implements Action {
    private static final String TAG = "ToggleAction";
    protected int mDisabledIconResid;
    protected int mDisabledStatusMessageResId;
    protected int mEnabledIconResId;
    protected int mEnabledStatusMessageResId;
    protected int mMessageResId;
    protected State mState = State.Off;

    public ToggleAction(int n, int n2, int n3, int n4, int n5) {
        this.mEnabledIconResId = n;
        this.mDisabledIconResid = n2;
        this.mMessageResId = n3;
        this.mEnabledStatusMessageResId = n4;
        this.mDisabledStatusMessageResId = n5;
    }

    protected void changeStateFromPress(boolean bl) {
        State state = bl ? State.On : State.Off;
        this.mState = state;
    }

    @Override
    public View create(Context context, View view, ViewGroup view2, LayoutInflater object) {
        this.willCreate();
        view2 = ((LayoutInflater)object).inflate(17367158, (ViewGroup)view2, false);
        object = (ImageView)view2.findViewById(16908294);
        TextView textView = (TextView)view2.findViewById(16908299);
        view = (TextView)view2.findViewById(16909405);
        boolean bl = this.isEnabled();
        if (textView != null) {
            textView.setText(this.mMessageResId);
            textView.setEnabled(bl);
        }
        int n = this.mState != State.On && this.mState != State.TurningOn ? 0 : 1;
        if (object != null) {
            int n2 = n != 0 ? this.mEnabledIconResId : this.mDisabledIconResid;
            ((ImageView)object).setImageDrawable(context.getDrawable(n2));
            ((View)object).setEnabled(bl);
        }
        if (view != null) {
            n = n != 0 ? this.mEnabledStatusMessageResId : this.mDisabledStatusMessageResId;
            ((TextView)view).setText(n);
            view.setVisibility(0);
            ((TextView)view).setEnabled(bl);
        }
        view2.setEnabled(bl);
        return view2;
    }

    @Override
    public CharSequence getLabelForAccessibility(Context context) {
        return context.getString(this.mMessageResId);
    }

    @Override
    public boolean isEnabled() {
        return this.mState.inTransition() ^ true;
    }

    @Override
    public final void onPress() {
        if (this.mState.inTransition()) {
            Log.w(TAG, "shouldn't be able to toggle when in transition");
            return;
        }
        boolean bl = this.mState != State.On;
        this.onToggle(bl);
        this.changeStateFromPress(bl);
    }

    public abstract void onToggle(boolean var1);

    public void updateState(State state) {
        this.mState = state;
    }

    void willCreate() {
    }

    public static enum State {
        Off(false),
        TurningOn(true),
        TurningOff(true),
        On(false);
        
        private final boolean inTransition;

        private State(boolean bl) {
            this.inTransition = bl;
        }

        public boolean inTransition() {
            return this.inTransition;
        }
    }

}

