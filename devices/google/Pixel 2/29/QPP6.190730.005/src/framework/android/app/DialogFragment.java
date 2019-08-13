/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentHostCallback;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import java.io.FileDescriptor;
import java.io.PrintWriter;

@Deprecated
public class DialogFragment
extends Fragment
implements DialogInterface.OnCancelListener,
DialogInterface.OnDismissListener {
    private static final String SAVED_BACK_STACK_ID = "android:backStackId";
    private static final String SAVED_CANCELABLE = "android:cancelable";
    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
    private static final String SAVED_STYLE = "android:style";
    private static final String SAVED_THEME = "android:theme";
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_NO_FRAME = 2;
    public static final int STYLE_NO_INPUT = 3;
    public static final int STYLE_NO_TITLE = 1;
    @UnsupportedAppUsage
    int mBackStackId = -1;
    boolean mCancelable = true;
    Dialog mDialog;
    @UnsupportedAppUsage
    boolean mDismissed;
    @UnsupportedAppUsage
    boolean mShownByMe;
    boolean mShowsDialog = true;
    int mStyle = 0;
    int mTheme = 0;
    @UnsupportedAppUsage
    boolean mViewDestroyed;

    public void dismiss() {
        this.dismissInternal(false);
    }

    public void dismissAllowingStateLoss() {
        this.dismissInternal(true);
    }

    void dismissInternal(boolean bl) {
        if (this.mDismissed) {
            return;
        }
        this.mDismissed = true;
        this.mShownByMe = false;
        Object object = this.mDialog;
        if (object != null) {
            ((Dialog)object).dismiss();
            this.mDialog = null;
        }
        this.mViewDestroyed = true;
        if (this.mBackStackId >= 0) {
            this.getFragmentManager().popBackStack(this.mBackStackId, 1);
            this.mBackStackId = -1;
        } else {
            object = this.getFragmentManager().beginTransaction();
            ((FragmentTransaction)object).remove(this);
            if (bl) {
                ((FragmentTransaction)object).commitAllowingStateLoss();
            } else {
                ((FragmentTransaction)object).commit();
            }
        }
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string2, fileDescriptor, printWriter, arrstring);
        printWriter.print(string2);
        printWriter.println("DialogFragment:");
        printWriter.print(string2);
        printWriter.print("  mStyle=");
        printWriter.print(this.mStyle);
        printWriter.print(" mTheme=0x");
        printWriter.println(Integer.toHexString(this.mTheme));
        printWriter.print(string2);
        printWriter.print("  mCancelable=");
        printWriter.print(this.mCancelable);
        printWriter.print(" mShowsDialog=");
        printWriter.print(this.mShowsDialog);
        printWriter.print(" mBackStackId=");
        printWriter.println(this.mBackStackId);
        printWriter.print(string2);
        printWriter.print("  mDialog=");
        printWriter.println(this.mDialog);
        printWriter.print(string2);
        printWriter.print("  mViewDestroyed=");
        printWriter.print(this.mViewDestroyed);
        printWriter.print(" mDismissed=");
        printWriter.print(this.mDismissed);
        printWriter.print(" mShownByMe=");
        printWriter.println(this.mShownByMe);
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public boolean getShowsDialog() {
        return this.mShowsDialog;
    }

    public int getTheme() {
        return this.mTheme;
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (!this.mShowsDialog) {
            return;
        }
        KeyEvent.Callback callback = this.getView();
        if (callback != null) {
            if (callback.getParent() == null) {
                this.mDialog.setContentView((View)callback);
            } else {
                throw new IllegalStateException("DialogFragment can not be attached to a container view");
            }
        }
        if ((callback = this.getActivity()) != null) {
            this.mDialog.setOwnerActivity((Activity)callback);
        }
        this.mDialog.setCancelable(this.mCancelable);
        if (this.mDialog.takeCancelAndDismissListeners("DialogFragment", this, this)) {
            if (bundle != null && (bundle = bundle.getBundle(SAVED_DIALOG_STATE_TAG)) != null) {
                this.mDialog.onRestoreInstanceState(bundle);
            }
            return;
        }
        throw new IllegalStateException("You can not set Dialog's OnCancelListener or OnDismissListener");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!this.mShownByMe) {
            this.mDismissed = false;
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        boolean bl = this.mContainerId == 0;
        this.mShowsDialog = bl;
        if (bundle != null) {
            this.mStyle = bundle.getInt(SAVED_STYLE, 0);
            this.mTheme = bundle.getInt(SAVED_THEME, 0);
            this.mCancelable = bundle.getBoolean(SAVED_CANCELABLE, true);
            this.mShowsDialog = bundle.getBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
            this.mBackStackId = bundle.getInt(SAVED_BACK_STACK_ID, -1);
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        return new Dialog(this.getActivity(), this.getTheme());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            this.mViewDestroyed = true;
            dialog.dismiss();
            this.mDialog = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!this.mShownByMe && !this.mDismissed) {
            this.mDismissed = true;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        if (!this.mViewDestroyed) {
            this.dismissInternal(true);
        }
    }

    @Override
    public LayoutInflater onGetLayoutInflater(Bundle object) {
        block6 : {
            block5 : {
                if (!this.mShowsDialog) {
                    return super.onGetLayoutInflater((Bundle)object);
                }
                this.mDialog = this.onCreateDialog((Bundle)object);
                int n = this.mStyle;
                if (n == 1 || n == 2) break block5;
                if (n != 3) break block6;
                this.mDialog.getWindow().addFlags(24);
            }
            this.mDialog.requestWindowFeature(1);
        }
        object = this.mDialog;
        if (object != null) {
            return (LayoutInflater)((Dialog)object).getContext().getSystemService("layout_inflater");
        }
        return (LayoutInflater)this.mHost.getContext().getSystemService("layout_inflater");
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        boolean bl;
        int n;
        super.onSaveInstanceState(bundle);
        Object object = this.mDialog;
        if (object != null && (object = ((Dialog)object).onSaveInstanceState()) != null) {
            bundle.putBundle(SAVED_DIALOG_STATE_TAG, (Bundle)object);
        }
        if ((n = this.mStyle) != 0) {
            bundle.putInt(SAVED_STYLE, n);
        }
        if ((n = this.mTheme) != 0) {
            bundle.putInt(SAVED_THEME, n);
        }
        if (!(bl = this.mCancelable)) {
            bundle.putBoolean(SAVED_CANCELABLE, bl);
        }
        if (!(bl = this.mShowsDialog)) {
            bundle.putBoolean(SAVED_SHOWS_DIALOG, bl);
        }
        if ((n = this.mBackStackId) != -1) {
            bundle.putInt(SAVED_BACK_STACK_ID, n);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            this.mViewDestroyed = false;
            dialog.show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.hide();
        }
    }

    public void setCancelable(boolean bl) {
        this.mCancelable = bl;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setCancelable(bl);
        }
    }

    public void setShowsDialog(boolean bl) {
        this.mShowsDialog = bl;
    }

    public void setStyle(int n, int n2) {
        this.mStyle = n;
        if ((n = this.mStyle) == 2 || n == 3) {
            this.mTheme = 16974819;
        }
        if (n2 != 0) {
            this.mTheme = n2;
        }
    }

    public int show(FragmentTransaction fragmentTransaction, String string2) {
        this.mDismissed = false;
        this.mShownByMe = true;
        fragmentTransaction.add(this, string2);
        this.mViewDestroyed = false;
        this.mBackStackId = fragmentTransaction.commit();
        return this.mBackStackId;
    }

    public void show(FragmentManager object, String string2) {
        this.mDismissed = false;
        this.mShownByMe = true;
        object = ((FragmentManager)object).beginTransaction();
        ((FragmentTransaction)object).add(this, string2);
        ((FragmentTransaction)object).commit();
    }

    @UnsupportedAppUsage
    public void showAllowingStateLoss(FragmentManager object, String string2) {
        this.mDismissed = false;
        this.mShownByMe = true;
        object = ((FragmentManager)object).beginTransaction();
        ((FragmentTransaction)object).add(this, string2);
        ((FragmentTransaction)object).commitAllowingStateLoss();
    }
}

