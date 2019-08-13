/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Rect;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public abstract class ActionMode {
    public static final int DEFAULT_HIDE_DURATION = -1;
    public static final int TYPE_FLOATING = 1;
    public static final int TYPE_PRIMARY = 0;
    private Object mTag;
    private boolean mTitleOptionalHint;
    private int mType = 0;

    public abstract void finish();

    public abstract View getCustomView();

    public abstract Menu getMenu();

    public abstract MenuInflater getMenuInflater();

    public abstract CharSequence getSubtitle();

    public Object getTag() {
        return this.mTag;
    }

    public abstract CharSequence getTitle();

    public boolean getTitleOptionalHint() {
        return this.mTitleOptionalHint;
    }

    public int getType() {
        return this.mType;
    }

    public void hide(long l) {
    }

    public abstract void invalidate();

    public void invalidateContentRect() {
    }

    public boolean isTitleOptional() {
        return false;
    }

    public boolean isUiFocusable() {
        return true;
    }

    public void onWindowFocusChanged(boolean bl) {
    }

    public abstract void setCustomView(View var1);

    public abstract void setSubtitle(int var1);

    public abstract void setSubtitle(CharSequence var1);

    public void setTag(Object object) {
        this.mTag = object;
    }

    public abstract void setTitle(int var1);

    public abstract void setTitle(CharSequence var1);

    public void setTitleOptionalHint(boolean bl) {
        this.mTitleOptionalHint = bl;
    }

    public void setType(int n) {
        this.mType = n;
    }

    public static interface Callback {
        public boolean onActionItemClicked(ActionMode var1, MenuItem var2);

        public boolean onCreateActionMode(ActionMode var1, Menu var2);

        public void onDestroyActionMode(ActionMode var1);

        public boolean onPrepareActionMode(ActionMode var1, Menu var2);
    }

    public static abstract class Callback2
    implements Callback {
        public void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
            if (view != null) {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            } else {
                rect.set(0, 0, 0, 0);
            }
        }
    }

}

