/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.autofill.augmented.-$
 *  android.service.autofill.augmented.-$$Lambda
 *  android.service.autofill.augmented.-$$Lambda$FillWindow
 *  android.service.autofill.augmented.-$$Lambda$FillWindow$FillWindowPresenter
 *  android.service.autofill.augmented.-$$Lambda$FillWindow$FillWindowPresenter$EnBAJTZRgK05SBPnOQ9Edaq3VXs
 *  android.service.autofill.augmented.-$$Lambda$FillWindow$FillWindowPresenter$hdkNZGuYdsvcArvQ2SoMspO1EO8
 *  dalvik.system.CloseGuard
 */
package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.service.autofill.augmented.-$;
import android.service.autofill.augmented.AugmentedAutofillService;
import android.service.autofill.augmented.PresentationParams;
import android.service.autofill.augmented._$$Lambda$FillWindow$FillWindowPresenter$EnBAJTZRgK05SBPnOQ9Edaq3VXs;
import android.service.autofill.augmented._$$Lambda$FillWindow$FillWindowPresenter$hdkNZGuYdsvcArvQ2SoMspO1EO8;
import android.service.autofill.augmented._$$Lambda$FillWindow$j5yljDJ4VfFL37B_iWRonGGOKN4;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.autofill.IAutofillWindowPresenter;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import dalvik.system.CloseGuard;
import java.io.PrintWriter;

@SystemApi
public final class FillWindow
implements AutoCloseable {
    private static final String TAG = FillWindow.class.getSimpleName();
    @GuardedBy(value={"mLock"})
    private Rect mBounds;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    @GuardedBy(value={"mLock"})
    private boolean mDestroyed;
    @GuardedBy(value={"mLock"})
    private View mFillView;
    private final FillWindowPresenter mFillWindowPresenter = new FillWindowPresenter();
    private final Object mLock = new Object();
    private AugmentedAutofillService.AutofillProxy mProxy;
    @GuardedBy(value={"mLock"})
    private boolean mShowing;
    private final Handler mUiThreadHandler = new Handler(Looper.getMainLooper());
    @GuardedBy(value={"mLock"})
    private boolean mUpdateCalled;
    @GuardedBy(value={"mLock"})
    private WindowManager mWm;

    private void checkNotDestroyedLocked() {
        if (!this.mDestroyed) {
            return;
        }
        throw new IllegalStateException("already destroyed()");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void handleHide() {
        if (AugmentedAutofillService.sDebug) {
            Log.d(TAG, "handleHide()");
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.mWm != null && this.mFillView != null && this.mShowing) {
                this.mWm.removeView(this.mFillView);
                this.mShowing = false;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void handleShow(WindowManager.LayoutParams layoutParams) {
        if (AugmentedAutofillService.sDebug) {
            Log.d(TAG, "handleShow()");
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.mWm != null && this.mFillView != null) {
                layoutParams.flags |= 262144;
                if (!this.mShowing) {
                    this.mWm.addView(this.mFillView, layoutParams);
                    this.mShowing = true;
                } else {
                    this.mWm.updateViewLayout(this.mFillView, layoutParams);
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void hide() {
        if (AugmentedAutofillService.sDebug) {
            Log.d(TAG, "hide()");
        }
        Object object = this.mLock;
        synchronized (object) {
            this.checkNotDestroyedLocked();
            if (this.mWm != null && this.mFillView != null) {
                boolean bl;
                if (this.mProxy != null && (bl = this.mShowing)) {
                    try {
                        this.mProxy.requestHideFillUi();
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TAG, "Error requesting to hide fill window", remoteException);
                    }
                }
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("update() not called yet, or already destroyed()");
            throw illegalStateException;
        }
    }

    @Override
    public void close() throws Exception {
        this.destroy();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void destroy() {
        Object object;
        if (AugmentedAutofillService.sDebug) {
            String string2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("destroy(): mDestroyed=");
            ((StringBuilder)object).append(this.mDestroyed);
            ((StringBuilder)object).append(" mShowing=");
            ((StringBuilder)object).append(this.mShowing);
            ((StringBuilder)object).append(" mFillView=");
            ((StringBuilder)object).append(this.mFillView);
            Log.d(string2, ((StringBuilder)object).toString());
        }
        object = this.mLock;
        synchronized (object) {
            if (this.mDestroyed) {
                return;
            }
            if (this.mUpdateCalled) {
                this.hide();
                this.mProxy.report(3);
            }
            this.mDestroyed = true;
            this.mCloseGuard.close();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(String string2, PrintWriter printWriter) {
        synchronized (this) {
            printWriter.print(string2);
            printWriter.print("destroyed: ");
            printWriter.println(this.mDestroyed);
            printWriter.print(string2);
            printWriter.print("updateCalled: ");
            printWriter.println(this.mUpdateCalled);
            if (this.mFillView != null) {
                printWriter.print(string2);
                printWriter.print("fill window: ");
                String string3 = this.mShowing ? "shown" : "hidden";
                printWriter.println(string3);
                printWriter.print(string2);
                printWriter.print("fill view: ");
                printWriter.println(this.mFillView);
                printWriter.print(string2);
                printWriter.print("mBounds: ");
                printWriter.println(this.mBounds);
                printWriter.print(string2);
                printWriter.print("mWm: ");
                printWriter.println(this.mWm);
            }
            return;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.destroy();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public /* synthetic */ boolean lambda$update$0$FillWindow(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 4) {
            if (AugmentedAutofillService.sVerbose) {
                Log.v(TAG, "Outside touch detected, hiding the window");
            }
            this.hide();
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void show() {
        if (AugmentedAutofillService.sDebug) {
            Log.d(TAG, "show()");
        }
        Object object = this.mLock;
        synchronized (object) {
            this.checkNotDestroyedLocked();
            if (this.mWm != null && this.mFillView != null) {
                AugmentedAutofillService.AutofillProxy autofillProxy = this.mProxy;
                if (autofillProxy != null) {
                    try {
                        this.mProxy.requestShowFillUi(this.mBounds.right - this.mBounds.left, this.mBounds.bottom - this.mBounds.top, null, this.mFillWindowPresenter);
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TAG, "Error requesting to show fill window", remoteException);
                    }
                    this.mProxy.report(2);
                }
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("update() not called yet, or already destroyed()");
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean update(PresentationParams.Area object, View view, long l) {
        Object object2;
        Object object3;
        if (AugmentedAutofillService.sDebug) {
            object2 = TAG;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Updating ");
            ((StringBuilder)object3).append(object);
            ((StringBuilder)object3).append(" + with ");
            ((StringBuilder)object3).append(view);
            Log.d((String)object2, ((StringBuilder)object3).toString());
        }
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(((PresentationParams.Area)object).proxy);
        Preconditions.checkNotNull(view);
        object3 = ((PresentationParams.Area)object).proxy.getSmartSuggestionParams();
        if (object3 == null) {
            Log.w(TAG, "No SmartSuggestionParams");
            return false;
        }
        if (((PresentationParams.Area)object).getBounds() == null) {
            Log.wtf(TAG, "No Rect on SmartSuggestionParams");
            return false;
        }
        object2 = this.mLock;
        synchronized (object2) {
            this.checkNotDestroyedLocked();
            this.mProxy = ((PresentationParams.Area)object).proxy;
            this.mWm = view.getContext().getSystemService(WindowManager.class);
            this.mFillView = view;
            Object object4 = this.mFillView;
            _$$Lambda$FillWindow$j5yljDJ4VfFL37B_iWRonGGOKN4 _$$Lambda$FillWindow$j5yljDJ4VfFL37B_iWRonGGOKN4 = new _$$Lambda$FillWindow$j5yljDJ4VfFL37B_iWRonGGOKN4(this);
            ((View)object4).setOnTouchListener(_$$Lambda$FillWindow$j5yljDJ4VfFL37B_iWRonGGOKN4);
            this.mShowing = false;
            this.mBounds = object4 = new Rect(((PresentationParams.Area)object).getBounds());
            if (AugmentedAutofillService.sDebug) {
                object = TAG;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append("Created FillWindow: params= ");
                ((StringBuilder)object4).append(object3);
                ((StringBuilder)object4).append(" view=");
                ((StringBuilder)object4).append(view);
                Log.d((String)object, ((StringBuilder)object4).toString());
            }
            this.mUpdateCalled = true;
            this.mDestroyed = false;
            this.mProxy.setFillWindow(this);
            return true;
        }
    }

    private final class FillWindowPresenter
    extends IAutofillWindowPresenter.Stub {
        private FillWindowPresenter() {
        }

        static /* synthetic */ void lambda$hide$1(Object object) {
            ((FillWindow)object).handleHide();
        }

        static /* synthetic */ void lambda$show$0(Object object, WindowManager.LayoutParams layoutParams) {
            ((FillWindow)object).handleShow(layoutParams);
        }

        @Override
        public void hide(Rect rect) {
            if (AugmentedAutofillService.sDebug) {
                Log.d(TAG, "FillWindowPresenter.hide()");
            }
            FillWindow.this.mUiThreadHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$FillWindow$FillWindowPresenter$EnBAJTZRgK05SBPnOQ9Edaq3VXs.INSTANCE, FillWindow.this));
        }

        @Override
        public void show(WindowManager.LayoutParams layoutParams, Rect rect, boolean bl, int n) {
            if (AugmentedAutofillService.sDebug) {
                Log.d(TAG, "FillWindowPresenter.show()");
            }
            FillWindow.this.mUiThreadHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$FillWindow$FillWindowPresenter$hdkNZGuYdsvcArvQ2SoMspO1EO8.INSTANCE, FillWindow.this, layoutParams));
        }
    }

}

