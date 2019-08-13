/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public final class Scene {
    private Context mContext;
    @UnsupportedAppUsage(maxTargetSdk=28)
    Runnable mEnterAction;
    @UnsupportedAppUsage(maxTargetSdk=28)
    Runnable mExitAction;
    private View mLayout;
    private int mLayoutId = -1;
    private ViewGroup mSceneRoot;

    public Scene(ViewGroup viewGroup) {
        this.mSceneRoot = viewGroup;
    }

    private Scene(ViewGroup viewGroup, int n, Context context) {
        this.mContext = context;
        this.mSceneRoot = viewGroup;
        this.mLayoutId = n;
    }

    public Scene(ViewGroup viewGroup, View view) {
        this.mSceneRoot = viewGroup;
        this.mLayout = view;
    }

    @Deprecated
    public Scene(ViewGroup viewGroup, ViewGroup viewGroup2) {
        this.mSceneRoot = viewGroup;
        this.mLayout = viewGroup2;
    }

    public static Scene getCurrentScene(ViewGroup viewGroup) {
        return (Scene)viewGroup.getTag(16908852);
    }

    public static Scene getSceneForLayout(ViewGroup object, int n, Context context) {
        Object object2 = (SparseArray<Object>)((View)object).getTag(16909306);
        SparseArray<Object> sparseArray = object2;
        if (object2 == null) {
            sparseArray = new SparseArray<Object>();
            ((View)object).setTagInternal(16909306, sparseArray);
        }
        if ((object2 = (Scene)sparseArray.get(n)) != null) {
            return object2;
        }
        object = new Scene((ViewGroup)object, n, context);
        sparseArray.put(n, object);
        return object;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    static void setCurrentScene(ViewGroup viewGroup, Scene scene) {
        viewGroup.setTagInternal(16908852, scene);
    }

    public void enter() {
        Runnable runnable;
        if (this.mLayoutId > 0 || this.mLayout != null) {
            this.getSceneRoot().removeAllViews();
            if (this.mLayoutId > 0) {
                LayoutInflater.from(this.mContext).inflate(this.mLayoutId, this.mSceneRoot);
            } else {
                this.mSceneRoot.addView(this.mLayout);
            }
        }
        if ((runnable = this.mEnterAction) != null) {
            runnable.run();
        }
        Scene.setCurrentScene(this.mSceneRoot, this);
    }

    public void exit() {
        Runnable runnable;
        if (Scene.getCurrentScene(this.mSceneRoot) == this && (runnable = this.mExitAction) != null) {
            runnable.run();
        }
    }

    public ViewGroup getSceneRoot() {
        return this.mSceneRoot;
    }

    boolean isCreatedFromLayoutResource() {
        boolean bl = this.mLayoutId > 0;
        return bl;
    }

    public void setEnterAction(Runnable runnable) {
        this.mEnterAction = runnable;
    }

    public void setExitAction(Runnable runnable) {
        this.mExitAction = runnable;
    }
}

