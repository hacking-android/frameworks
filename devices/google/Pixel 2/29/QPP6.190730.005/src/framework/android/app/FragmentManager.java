/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Fragment;
import android.app.FragmentManagerImpl;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

@Deprecated
public abstract class FragmentManager {
    public static final int POP_BACK_STACK_INCLUSIVE = 1;

    public static void enableDebugLogging(boolean bl) {
        FragmentManagerImpl.DEBUG = bl;
    }

    public abstract void addOnBackStackChangedListener(OnBackStackChangedListener var1);

    public abstract FragmentTransaction beginTransaction();

    public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    public abstract boolean executePendingTransactions();

    public abstract Fragment findFragmentById(int var1);

    public abstract Fragment findFragmentByTag(String var1);

    public abstract BackStackEntry getBackStackEntryAt(int var1);

    public abstract int getBackStackEntryCount();

    public abstract Fragment getFragment(Bundle var1, String var2);

    public abstract List<Fragment> getFragments();

    public abstract Fragment getPrimaryNavigationFragment();

    public void invalidateOptionsMenu() {
    }

    public abstract boolean isDestroyed();

    public abstract boolean isStateSaved();

    @Deprecated
    public FragmentTransaction openTransaction() {
        return this.beginTransaction();
    }

    public abstract void popBackStack();

    public abstract void popBackStack(int var1, int var2);

    public abstract void popBackStack(String var1, int var2);

    public abstract boolean popBackStackImmediate();

    public abstract boolean popBackStackImmediate(int var1, int var2);

    public abstract boolean popBackStackImmediate(String var1, int var2);

    public abstract void putFragment(Bundle var1, String var2, Fragment var3);

    public abstract void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks var1, boolean var2);

    public abstract void removeOnBackStackChangedListener(OnBackStackChangedListener var1);

    public abstract Fragment.SavedState saveFragmentInstanceState(Fragment var1);

    public abstract void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks var1);

    @Deprecated
    public static interface BackStackEntry {
        public CharSequence getBreadCrumbShortTitle();

        public int getBreadCrumbShortTitleRes();

        public CharSequence getBreadCrumbTitle();

        public int getBreadCrumbTitleRes();

        public int getId();

        public String getName();
    }

    @Deprecated
    public static abstract class FragmentLifecycleCallbacks {
        public void onFragmentActivityCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
        }

        public void onFragmentCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentDetached(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentPaused(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentPreAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
        }

        public void onFragmentPreCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentSaveInstanceState(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentStarted(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentStopped(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
        }

        public void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment) {
        }
    }

    @Deprecated
    public static interface OnBackStackChangedListener {
        public void onBackStackChanged();
    }

}

