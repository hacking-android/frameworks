/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.InsetsController;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Supplier;

public class InsetsSourceConsumer {
    protected final InsetsController mController;
    private InsetsSourceControl mSourceControl;
    private final InsetsState mState;
    private final Supplier<SurfaceControl.Transaction> mTransactionSupplier;
    private final int mType;
    protected boolean mVisible;

    public InsetsSourceConsumer(int n, InsetsState insetsState, Supplier<SurfaceControl.Transaction> supplier, InsetsController insetsController) {
        this.mType = n;
        this.mState = insetsState;
        this.mTransactionSupplier = supplier;
        this.mController = insetsController;
        this.mVisible = InsetsState.getDefaultVisibility(n);
    }

    private void applyHiddenToControl() {
        if (this.mSourceControl == null) {
            return;
        }
        SurfaceControl.Transaction transaction = this.mTransactionSupplier.get();
        if (this.mVisible) {
            transaction.show(this.mSourceControl.getLeash());
        } else {
            transaction.hide(this.mSourceControl.getLeash());
        }
        transaction.apply();
    }

    private void setVisible(boolean bl) {
        if (this.mVisible == bl) {
            return;
        }
        this.mVisible = bl;
        this.applyHiddenToControl();
        this.applyLocalVisibilityOverride();
        this.mController.notifyVisibilityChanged();
    }

    boolean applyLocalVisibilityOverride() {
        if (this.mSourceControl == null) {
            return false;
        }
        if (this.mState.getSource(this.mType).isVisible() == this.mVisible) {
            return false;
        }
        this.mState.getSource(this.mType).setVisible(this.mVisible);
        return true;
    }

    @VisibleForTesting
    public InsetsSourceControl getControl() {
        return this.mSourceControl;
    }

    int getType() {
        return this.mType;
    }

    @VisibleForTesting
    public void hide() {
        this.setVisible(false);
    }

    @VisibleForTesting
    public boolean isVisible() {
        return this.mVisible;
    }

    void notifyHidden() {
    }

    public void onWindowFocusGained() {
    }

    public void onWindowFocusLost() {
    }

    int requestShow(boolean bl) {
        return 0;
    }

    public void setControl(InsetsSourceControl insetsSourceControl) {
        if (this.mSourceControl == insetsSourceControl) {
            return;
        }
        this.mSourceControl = insetsSourceControl;
        this.applyHiddenToControl();
        if (this.applyLocalVisibilityOverride()) {
            this.mController.notifyVisibilityChanged();
        }
        if (this.mSourceControl == null) {
            this.mController.notifyControlRevoked(this);
        }
    }

    @VisibleForTesting
    public void show() {
        this.setVisible(true);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface ShowResult {
        public static final int SHOW_DELAYED = 1;
        public static final int SHOW_FAILED = 2;
        public static final int SHOW_IMMEDIATELY = 0;
    }

}

