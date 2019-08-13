/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.app._$$Lambda$Dialog$zXRzrq3I7H1_zmZ8d_W7t2CQN0I;
import android.app._$$Lambda$oslF4K8Uk6v_6nTRoaEpCmfAptE;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.app.WindowDecorActionBar;
import com.android.internal.policy.PhoneWindow;
import java.lang.ref.WeakReference;

public class Dialog
implements DialogInterface,
Window.Callback,
KeyEvent.Callback,
View.OnCreateContextMenuListener,
Window.OnWindowDismissedCallback {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static final int CANCEL = 68;
    private static final String DIALOG_HIERARCHY_TAG = "android:dialogHierarchy";
    private static final String DIALOG_SHOWING_TAG = "android:dialogShowing";
    private static final int DISMISS = 67;
    private static final int SHOW = 69;
    private static final String TAG = "Dialog";
    private ActionBar mActionBar;
    private ActionMode mActionMode;
    private int mActionModeTypeStarting = 0;
    private String mCancelAndDismissTaken;
    @UnsupportedAppUsage
    private Message mCancelMessage;
    protected boolean mCancelable = true;
    private boolean mCanceled = false;
    @UnsupportedAppUsage
    final Context mContext;
    private boolean mCreated = false;
    View mDecor;
    private final Runnable mDismissAction = new _$$Lambda$oslF4K8Uk6v_6nTRoaEpCmfAptE(this);
    @UnsupportedAppUsage
    private Message mDismissMessage;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final Handler mHandler = new Handler();
    @UnsupportedAppUsage
    private final Handler mListenersHandler;
    @UnsupportedAppUsage
    private DialogInterface.OnKeyListener mOnKeyListener;
    @UnsupportedAppUsage
    private Activity mOwnerActivity;
    private SearchEvent mSearchEvent;
    @UnsupportedAppUsage
    private Message mShowMessage;
    @UnsupportedAppUsage
    private boolean mShowing = false;
    @UnsupportedAppUsage
    final Window mWindow;
    private final WindowManager mWindowManager;

    public Dialog(Context context) {
        this(context, 0, true);
    }

    public Dialog(Context context, int n) {
        this(context, n, true);
    }

    Dialog(Context object, int n, boolean bl) {
        if (bl) {
            int n2 = n;
            if (n == 0) {
                TypedValue typedValue = new TypedValue();
                ((Context)object).getTheme().resolveAttribute(16843528, typedValue, true);
                n2 = typedValue.resourceId;
            }
            this.mContext = new ContextThemeWrapper((Context)object, n2);
        } else {
            this.mContext = object;
        }
        this.mWindowManager = (WindowManager)((Context)object).getSystemService("window");
        this.mWindow = object = new PhoneWindow(this.mContext);
        ((Window)object).setCallback(this);
        ((Window)object).setOnWindowDismissedCallback(this);
        ((Window)object).setOnWindowSwipeDismissedCallback(new _$$Lambda$Dialog$zXRzrq3I7H1_zmZ8d_W7t2CQN0I(this));
        ((Window)object).setWindowManager(this.mWindowManager, null, null);
        ((Window)object).setGravity(17);
        this.mListenersHandler = new ListenersHandler(this);
    }

    protected Dialog(Context context, boolean bl, DialogInterface.OnCancelListener onCancelListener) {
        this(context);
        this.mCancelable = bl;
        this.updateWindowForCancelable();
        this.setOnCancelListener(onCancelListener);
    }

    @Deprecated
    protected Dialog(Context context, boolean bl, Message message) {
        this(context);
        this.mCancelable = bl;
        this.updateWindowForCancelable();
        this.mCancelMessage = message;
    }

    private ComponentName getAssociatedActivity() {
        Object var3_3;
        Activity activity = this.mOwnerActivity;
        Object object = this.getContext();
        do {
            var3_3 = null;
            Object var4_4 = null;
            if (activity != null || object == null) break;
            if (object instanceof Activity) {
                activity = (Activity)object;
                continue;
            }
            if (object instanceof ContextWrapper) {
                object = ((ContextWrapper)object).getBaseContext();
                continue;
            }
            object = var4_4;
        } while (true);
        object = activity == null ? var3_3 : activity.getComponentName();
        return object;
    }

    private void sendDismissMessage() {
        Message message = this.mDismissMessage;
        if (message != null) {
            Message.obtain(message).sendToTarget();
        }
    }

    private void sendShowMessage() {
        Message message = this.mShowMessage;
        if (message != null) {
            Message.obtain(message).sendToTarget();
        }
    }

    private void updateWindowForCancelable() {
        this.mWindow.setCloseOnSwipeEnabled(this.mCancelable);
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mWindow.addContentView(view, layoutParams);
    }

    @Override
    public void cancel() {
        Message message;
        if (!this.mCanceled && (message = this.mCancelMessage) != null) {
            this.mCanceled = true;
            Message.obtain(message).sendToTarget();
        }
        this.dismiss();
    }

    public void closeOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            this.mWindow.closePanel(0);
        }
    }

    public void create() {
        if (!this.mCreated) {
            this.dispatchOnCreate(null);
        }
    }

    @Override
    public void dismiss() {
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            this.dismissDialog();
        } else {
            this.mHandler.post(this.mDismissAction);
        }
    }

    @UnsupportedAppUsage
    void dismissDialog() {
        if (this.mDecor != null && this.mShowing) {
            if (this.mWindow.isDestroyed()) {
                Log.e(TAG, "Tried to dismissDialog() but the Dialog's window was already destroyed!");
                return;
            }
            try {
                this.mWindowManager.removeViewImmediate(this.mDecor);
                return;
            }
            finally {
                ActionMode actionMode = this.mActionMode;
                if (actionMode != null) {
                    actionMode.finish();
                }
                this.mDecor = null;
                this.mWindow.closeAllPanels();
                this.onStop();
                this.mShowing = false;
                this.sendDismissMessage();
            }
        }
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mWindow.superDispatchGenericMotionEvent(motionEvent)) {
            return true;
        }
        return this.onGenericMotionEvent(motionEvent);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        Object object = this.mOnKeyListener;
        if (object != null && object.onKey(this, keyEvent.getKeyCode(), keyEvent)) {
            return true;
        }
        if (this.mWindow.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        object = this.mDecor;
        object = object != null ? ((View)object).getKeyDispatcherState() : null;
        return keyEvent.dispatch(this, (KeyEvent.DispatcherState)object, this);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (this.mWindow.superDispatchKeyShortcutEvent(keyEvent)) {
            return true;
        }
        return this.onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
    }

    void dispatchOnCreate(Bundle bundle) {
        if (!this.mCreated) {
            this.onCreate(bundle);
            this.mCreated = true;
        }
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(this.getClass().getName());
        accessibilityEvent.setPackageName(this.mContext.getPackageName());
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        boolean bl = layoutParams.width == -1 && layoutParams.height == -1;
        accessibilityEvent.setFullScreen(bl);
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mWindow.superDispatchTouchEvent(motionEvent)) {
            return true;
        }
        return this.onTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        if (this.mWindow.superDispatchTrackballEvent(motionEvent)) {
            return true;
        }
        return this.onTrackballEvent(motionEvent);
    }

    public <T extends View> T findViewById(int n) {
        return this.mWindow.findViewById(n);
    }

    public ActionBar getActionBar() {
        return this.mActionBar;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public View getCurrentFocus() {
        Object object = this.mWindow;
        object = object != null ? ((Window)object).getCurrentFocus() : null;
        return object;
    }

    public LayoutInflater getLayoutInflater() {
        return this.getWindow().getLayoutInflater();
    }

    public final Activity getOwnerActivity() {
        return this.mOwnerActivity;
    }

    public final SearchEvent getSearchEvent() {
        return this.mSearchEvent;
    }

    public final int getVolumeControlStream() {
        return this.getWindow().getVolumeControlStream();
    }

    public Window getWindow() {
        return this.mWindow;
    }

    public void hide() {
        View view = this.mDecor;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    public void invalidateOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            this.mWindow.invalidatePanelMenu(0);
        }
    }

    public boolean isShowing() {
        View view = this.mDecor;
        boolean bl = false;
        if (view != null && view.getVisibility() == 0) {
            bl = true;
        }
        return bl;
    }

    public /* synthetic */ void lambda$new$0$Dialog() {
        if (this.mCancelable) {
            this.cancel();
        }
    }

    @Override
    public void onActionModeFinished(ActionMode actionMode) {
        if (actionMode == this.mActionMode) {
            this.mActionMode = null;
        }
    }

    @Override
    public void onActionModeStarted(ActionMode actionMode) {
        this.mActionMode = actionMode;
    }

    @Override
    public void onAttachedToWindow() {
    }

    public void onBackPressed() {
        if (this.mCancelable) {
            this.cancel();
        }
    }

    @Override
    public void onContentChanged() {
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onContextMenuClosed(Menu menu2) {
    }

    protected void onCreate(Bundle bundle) {
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        return true;
    }

    @Override
    public boolean onCreatePanelMenu(int n, Menu menu2) {
        if (n == 0) {
            return this.onCreateOptionsMenu(menu2);
        }
        return false;
    }

    @Override
    public View onCreatePanelView(int n) {
        return null;
    }

    @Override
    public void onDetachedFromWindow() {
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n != 4 && n != 111) {
            return false;
        }
        keyEvent.startTracking();
        return true;
    }

    @Override
    public boolean onKeyLongPress(int n, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if ((n == 4 || n == 111) && keyEvent.isTracking() && !keyEvent.isCanceled()) {
            this.onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onMenuItemSelected(int n, MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onMenuOpened(int n, Menu menu2) {
        if (n == 8) {
            this.mActionBar.dispatchMenuVisibilityChanged(true);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(Menu menu2) {
    }

    @Override
    public void onPanelClosed(int n, Menu menu2) {
        if (n == 8) {
            this.mActionBar.dispatchMenuVisibilityChanged(false);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu2) {
        return true;
    }

    @Override
    public boolean onPreparePanel(int n, View view, Menu menu2) {
        boolean bl = true;
        if (n == 0) {
            if (!this.onPrepareOptionsMenu(menu2) || !menu2.hasVisibleItems()) {
                bl = false;
            }
            return bl;
        }
        return true;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(DIALOG_HIERARCHY_TAG);
        if (bundle2 == null) {
            return;
        }
        this.dispatchOnCreate(bundle);
        this.mWindow.restoreHierarchyState(bundle2);
        if (bundle.getBoolean(DIALOG_SHOWING_TAG)) {
            this.show();
        }
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(DIALOG_SHOWING_TAG, this.mShowing);
        if (this.mCreated) {
            bundle.putBundle(DIALOG_HIERARCHY_TAG, this.mWindow.saveHierarchyState());
        }
        return bundle;
    }

    @Override
    public boolean onSearchRequested() {
        SearchManager searchManager = (SearchManager)this.mContext.getSystemService("search");
        ComponentName componentName = this.getAssociatedActivity();
        if (componentName != null && searchManager.getSearchableInfo(componentName) != null) {
            searchManager.startSearch(null, false, componentName, null, false);
            this.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        this.mSearchEvent = searchEvent;
        return this.onSearchRequested();
    }

    protected void onStart() {
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(true);
        }
    }

    protected void onStop() {
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(false);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mCancelable && this.mShowing && this.mWindow.shouldCloseOnTouch(this.mContext, motionEvent)) {
            this.cancel();
            return true;
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        View view = this.mDecor;
        if (view != null) {
            this.mWindowManager.updateViewLayout(view, layoutParams);
        }
    }

    @Override
    public void onWindowDismissed(boolean bl, boolean bl2) {
        this.dismiss();
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null && this.mActionModeTypeStarting == 0) {
            return actionBar.startActionMode(callback);
        }
        return null;
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback object, int n) {
        try {
            this.mActionModeTypeStarting = n;
            object = this.onWindowStartingActionMode((ActionMode.Callback)object);
            return object;
        }
        finally {
            this.mActionModeTypeStarting = 0;
        }
    }

    public void openContextMenu(View view) {
        view.showContextMenu();
    }

    public void openOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            this.mWindow.openPanel(0, null);
        }
    }

    public void registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener(this);
    }

    public final boolean requestWindowFeature(int n) {
        return this.getWindow().requestFeature(n);
    }

    public final <T extends View> T requireViewById(int n) {
        T t = this.findViewById(n);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Dialog");
    }

    public void setCancelMessage(Message message) {
        this.mCancelMessage = message;
    }

    public void setCancelable(boolean bl) {
        this.mCancelable = bl;
        this.updateWindowForCancelable();
    }

    public void setCanceledOnTouchOutside(boolean bl) {
        if (bl && !this.mCancelable) {
            this.mCancelable = true;
            this.updateWindowForCancelable();
        }
        this.mWindow.setCloseOnTouchOutside(bl);
    }

    public void setContentView(int n) {
        this.mWindow.setContentView(n);
    }

    public void setContentView(View view) {
        this.mWindow.setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mWindow.setContentView(view, layoutParams);
    }

    public void setDismissMessage(Message message) {
        this.mDismissMessage = message;
    }

    public final void setFeatureDrawable(int n, Drawable drawable2) {
        this.getWindow().setFeatureDrawable(n, drawable2);
    }

    public final void setFeatureDrawableAlpha(int n, int n2) {
        this.getWindow().setFeatureDrawableAlpha(n, n2);
    }

    public final void setFeatureDrawableResource(int n, int n2) {
        this.getWindow().setFeatureDrawableResource(n, n2);
    }

    public final void setFeatureDrawableUri(int n, Uri uri) {
        this.getWindow().setFeatureDrawableUri(n, uri);
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener object) {
        if (this.mCancelAndDismissTaken == null) {
            this.mCancelMessage = object != null ? this.mListenersHandler.obtainMessage(68, object) : null;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("OnCancelListener is already taken by ");
        ((StringBuilder)object).append(this.mCancelAndDismissTaken);
        ((StringBuilder)object).append(" and can not be replaced.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener object) {
        if (this.mCancelAndDismissTaken == null) {
            this.mDismissMessage = object != null ? this.mListenersHandler.obtainMessage(67, object) : null;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("OnDismissListener is already taken by ");
        ((StringBuilder)object).append(this.mCancelAndDismissTaken);
        ((StringBuilder)object).append(" and can not be replaced.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.mOnKeyListener = onKeyListener;
    }

    public void setOnShowListener(DialogInterface.OnShowListener onShowListener) {
        this.mShowMessage = onShowListener != null ? this.mListenersHandler.obtainMessage(69, onShowListener) : null;
    }

    public final void setOwnerActivity(Activity activity) {
        this.mOwnerActivity = activity;
        this.getWindow().setVolumeControlStream(this.mOwnerActivity.getVolumeControlStream());
    }

    public void setTitle(int n) {
        this.setTitle(this.mContext.getText(n));
    }

    public void setTitle(CharSequence charSequence) {
        this.mWindow.setTitle(charSequence);
        this.mWindow.getAttributes().setTitle(charSequence);
    }

    public final void setVolumeControlStream(int n) {
        this.getWindow().setVolumeControlStream(n);
    }

    public void show() {
        Parcelable parcelable;
        if (this.mShowing) {
            if (this.mDecor != null) {
                if (this.mWindow.hasFeature(8)) {
                    this.mWindow.invalidatePanelMenu(8);
                }
                this.mDecor.setVisibility(0);
            }
            return;
        }
        this.mCanceled = false;
        if (!this.mCreated) {
            this.dispatchOnCreate(null);
        } else {
            parcelable = this.mContext.getResources().getConfiguration();
            this.mWindow.getDecorView().dispatchConfigurationChanged((Configuration)parcelable);
        }
        this.onStart();
        this.mDecor = this.mWindow.getDecorView();
        if (this.mActionBar == null && this.mWindow.hasFeature(8)) {
            parcelable = this.mContext.getApplicationInfo();
            this.mWindow.setDefaultIcon(((ApplicationInfo)parcelable).icon);
            this.mWindow.setDefaultLogo(((ApplicationInfo)parcelable).logo);
            this.mActionBar = new WindowDecorActionBar(this);
        }
        parcelable = this.mWindow.getAttributes();
        boolean bl = false;
        if ((((WindowManager.LayoutParams)parcelable).softInputMode & 256) == 0) {
            ((WindowManager.LayoutParams)parcelable).softInputMode |= 256;
            bl = true;
        }
        this.mWindowManager.addView(this.mDecor, (ViewGroup.LayoutParams)((Object)parcelable));
        if (bl) {
            ((WindowManager.LayoutParams)parcelable).softInputMode &= -257;
        }
        this.mShowing = true;
        this.sendShowMessage();
    }

    public boolean takeCancelAndDismissListeners(String string2, DialogInterface.OnCancelListener onCancelListener, DialogInterface.OnDismissListener onDismissListener) {
        block4 : {
            block3 : {
                block2 : {
                    if (this.mCancelAndDismissTaken == null) break block2;
                    this.mCancelAndDismissTaken = null;
                    break block3;
                }
                if (this.mCancelMessage != null || this.mDismissMessage != null) break block4;
            }
            this.setOnCancelListener(onCancelListener);
            this.setOnDismissListener(onDismissListener);
            this.mCancelAndDismissTaken = string2;
            return true;
        }
        return false;
    }

    public void takeKeyEvents(boolean bl) {
        this.mWindow.takeKeyEvents(bl);
    }

    public void unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null);
    }

    private static final class ListenersHandler
    extends Handler {
        private final WeakReference<DialogInterface> mDialog;

        public ListenersHandler(Dialog dialog) {
            this.mDialog = new WeakReference<Dialog>(dialog);
        }

        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    break;
                }
                case 69: {
                    ((DialogInterface.OnShowListener)message.obj).onShow((DialogInterface)this.mDialog.get());
                    break;
                }
                case 68: {
                    ((DialogInterface.OnCancelListener)message.obj).onCancel((DialogInterface)this.mDialog.get());
                    break;
                }
                case 67: {
                    ((DialogInterface.OnDismissListener)message.obj).onDismiss((DialogInterface)this.mDialog.get());
                }
            }
        }
    }

}

