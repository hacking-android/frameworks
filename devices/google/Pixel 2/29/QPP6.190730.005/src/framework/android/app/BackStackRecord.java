/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Fragment;
import android.app.FragmentHostCallback;
import android.app.FragmentManager;
import android.app.FragmentManagerImpl;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.Log;
import android.util.LogWriter;
import android.view.View;
import com.android.internal.util.FastPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

final class BackStackRecord
extends FragmentTransaction
implements FragmentManager.BackStackEntry,
FragmentManagerImpl.OpGenerator {
    static final int OP_ADD = 1;
    static final int OP_ATTACH = 7;
    static final int OP_DETACH = 6;
    static final int OP_HIDE = 4;
    static final int OP_NULL = 0;
    static final int OP_REMOVE = 3;
    static final int OP_REPLACE = 2;
    static final int OP_SET_PRIMARY_NAV = 8;
    static final int OP_SHOW = 5;
    static final int OP_UNSET_PRIMARY_NAV = 9;
    static final String TAG = "FragmentManager";
    boolean mAddToBackStack;
    boolean mAllowAddToBackStack;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    ArrayList<Runnable> mCommitRunnables;
    boolean mCommitted;
    int mEnterAnim;
    int mExitAnim;
    int mIndex;
    final FragmentManagerImpl mManager;
    String mName;
    ArrayList<Op> mOps = new ArrayList();
    int mPopEnterAnim;
    int mPopExitAnim;
    boolean mReorderingAllowed;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    int mTransition;
    int mTransitionStyle;

    public BackStackRecord(FragmentManagerImpl fragmentManagerImpl) {
        boolean bl = true;
        this.mAllowAddToBackStack = true;
        this.mIndex = -1;
        this.mManager = fragmentManagerImpl;
        if (this.mManager.getTargetSdk() <= 25) {
            bl = false;
        }
        this.mReorderingAllowed = bl;
    }

    private void doAddOp(int n, Fragment object, String charSequence, int n2) {
        Serializable serializable;
        if (this.mManager.getTargetSdk() > 25) {
            serializable = object.getClass();
            int n3 = ((Class)serializable).getModifiers();
            if (((Class)serializable).isAnonymousClass() || !Modifier.isPublic(n3) || ((Class)serializable).isMemberClass() && !Modifier.isStatic(n3)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Fragment ");
                ((StringBuilder)object).append(((Class)serializable).getCanonicalName());
                ((StringBuilder)object).append(" must be a public static class to be  properly recreated from instance state.");
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
        }
        ((Fragment)object).mFragmentManager = this.mManager;
        if (charSequence != null) {
            if (((Fragment)object).mTag != null && !((String)charSequence).equals(((Fragment)object).mTag)) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Can't change tag of fragment ");
                ((StringBuilder)serializable).append(object);
                ((StringBuilder)serializable).append(": was ");
                ((StringBuilder)serializable).append(((Fragment)object).mTag);
                ((StringBuilder)serializable).append(" now ");
                ((StringBuilder)serializable).append((String)charSequence);
                throw new IllegalStateException(((StringBuilder)serializable).toString());
            }
            ((Fragment)object).mTag = charSequence;
        }
        if (n != 0) {
            if (n != -1) {
                if (((Fragment)object).mFragmentId != 0 && ((Fragment)object).mFragmentId != n) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Can't change container ID of fragment ");
                    ((StringBuilder)charSequence).append(object);
                    ((StringBuilder)charSequence).append(": was ");
                    ((StringBuilder)charSequence).append(((Fragment)object).mFragmentId);
                    ((StringBuilder)charSequence).append(" now ");
                    ((StringBuilder)charSequence).append(n);
                    throw new IllegalStateException(((StringBuilder)charSequence).toString());
                }
                ((Fragment)object).mFragmentId = n;
                ((Fragment)object).mContainerId = n;
            } else {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Can't add fragment ");
                ((StringBuilder)serializable).append(object);
                ((StringBuilder)serializable).append(" with tag ");
                ((StringBuilder)serializable).append((String)charSequence);
                ((StringBuilder)serializable).append(" to container view with no id");
                throw new IllegalArgumentException(((StringBuilder)serializable).toString());
            }
        }
        this.addOp(new Op(n2, (Fragment)object));
    }

    private static boolean isFragmentPostponed(Op object) {
        object = ((Op)object).fragment;
        boolean bl = object != null && ((Fragment)object).mAdded && ((Fragment)object).mView != null && !((Fragment)object).mDetached && !((Fragment)object).mHidden && ((Fragment)object).isPostponed();
        return bl;
    }

    @Override
    public FragmentTransaction add(int n, Fragment fragment) {
        this.doAddOp(n, fragment, null, 1);
        return this;
    }

    @Override
    public FragmentTransaction add(int n, Fragment fragment, String string2) {
        this.doAddOp(n, fragment, string2, 1);
        return this;
    }

    @Override
    public FragmentTransaction add(Fragment fragment, String string2) {
        this.doAddOp(0, fragment, string2, 1);
        return this;
    }

    void addOp(Op op) {
        this.mOps.add(op);
        op.enterAnim = this.mEnterAnim;
        op.exitAnim = this.mExitAnim;
        op.popEnterAnim = this.mPopEnterAnim;
        op.popExitAnim = this.mPopExitAnim;
    }

    @Override
    public FragmentTransaction addSharedElement(View object, String charSequence) {
        block2 : {
            block5 : {
                block6 : {
                    block4 : {
                        block3 : {
                            if ((object = ((View)object).getTransitionName()) == null) break block2;
                            if (this.mSharedElementSourceNames != null) break block3;
                            this.mSharedElementSourceNames = new ArrayList();
                            this.mSharedElementTargetNames = new ArrayList();
                            break block4;
                        }
                        if (this.mSharedElementTargetNames.contains(charSequence)) break block5;
                        if (this.mSharedElementSourceNames.contains(object)) break block6;
                    }
                    this.mSharedElementSourceNames.add((String)object);
                    this.mSharedElementTargetNames.add((String)charSequence);
                    return this;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("A shared element with the source name '");
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append(" has already been added to the transaction.");
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("A shared element with the target name '");
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append("' has already been added to the transaction.");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
    }

    @Override
    public FragmentTransaction addToBackStack(String string2) {
        if (this.mAllowAddToBackStack) {
            this.mAddToBackStack = true;
            this.mName = string2;
            return this;
        }
        throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    }

    @Override
    public FragmentTransaction attach(Fragment fragment) {
        this.addOp(new Op(7, fragment));
        return this;
    }

    void bumpBackStackNesting(int n) {
        Object object;
        if (!this.mAddToBackStack) {
            return;
        }
        if (FragmentManagerImpl.DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Bump nesting in ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" by ");
            ((StringBuilder)object).append(n);
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        int n2 = this.mOps.size();
        for (int i = 0; i < n2; ++i) {
            object = this.mOps.get(i);
            if (((Op)object).fragment == null) continue;
            Object object2 = ((Op)object).fragment;
            ((Fragment)object2).mBackStackNesting += n;
            if (!FragmentManagerImpl.DEBUG) continue;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Bump nesting of ");
            ((StringBuilder)object2).append(((Op)object).fragment);
            ((StringBuilder)object2).append(" to ");
            ((StringBuilder)object2).append(object.fragment.mBackStackNesting);
            Log.v(TAG, ((StringBuilder)object2).toString());
        }
    }

    @Override
    public int commit() {
        return this.commitInternal(false);
    }

    @Override
    public int commitAllowingStateLoss() {
        return this.commitInternal(true);
    }

    int commitInternal(boolean bl) {
        if (!this.mCommitted) {
            if (FragmentManagerImpl.DEBUG) {
                Appendable appendable = new StringBuilder();
                ((StringBuilder)appendable).append("Commit: ");
                ((StringBuilder)appendable).append(this);
                Log.v(TAG, ((StringBuilder)appendable).toString());
                appendable = new FastPrintWriter(new LogWriter(2, TAG), false, 1024);
                this.dump("  ", null, (PrintWriter)appendable, null);
                ((PrintWriter)appendable).flush();
            }
            this.mCommitted = true;
            this.mIndex = this.mAddToBackStack ? this.mManager.allocBackStackIndex(this) : -1;
            this.mManager.enqueueAction(this, bl);
            return this.mIndex;
        }
        throw new IllegalStateException("commit already called");
    }

    @Override
    public void commitNow() {
        this.disallowAddToBackStack();
        this.mManager.execSingleAction(this, false);
    }

    @Override
    public void commitNowAllowingStateLoss() {
        this.disallowAddToBackStack();
        this.mManager.execSingleAction(this, true);
    }

    @Override
    public FragmentTransaction detach(Fragment fragment) {
        this.addOp(new Op(6, fragment));
        return this;
    }

    @Override
    public FragmentTransaction disallowAddToBackStack() {
        if (!this.mAddToBackStack) {
            this.mAllowAddToBackStack = false;
            return this;
        }
        throw new IllegalStateException("This transaction is already being added to the back stack");
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        this.dump(string2, printWriter, true);
    }

    void dump(String string2, PrintWriter printWriter, boolean bl) {
        if (bl) {
            printWriter.print(string2);
            printWriter.print("mName=");
            printWriter.print(this.mName);
            printWriter.print(" mIndex=");
            printWriter.print(this.mIndex);
            printWriter.print(" mCommitted=");
            printWriter.println(this.mCommitted);
            if (this.mTransition != 0) {
                printWriter.print(string2);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.mTransition));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(Integer.toHexString(this.mTransitionStyle));
            }
            if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
                printWriter.print(string2);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.mEnterAnim));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.mExitAnim));
            }
            if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
                printWriter.print(string2);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.mPopEnterAnim));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.mPopExitAnim));
            }
            if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
                printWriter.print(string2);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.mBreadCrumbTitleText);
            }
            if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
                printWriter.print(string2);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.mBreadCrumbShortTitleText);
            }
        }
        if (!this.mOps.isEmpty()) {
            printWriter.print(string2);
            printWriter.println("Operations:");
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("    ");
            String string3 = ((StringBuilder)charSequence).toString();
            int n = this.mOps.size();
            for (int i = 0; i < n; ++i) {
                Op op = this.mOps.get(i);
                switch (op.cmd) {
                    default: {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("cmd=");
                        ((StringBuilder)charSequence).append(op.cmd);
                        charSequence = ((StringBuilder)charSequence).toString();
                        break;
                    }
                    case 9: {
                        charSequence = "UNSET_PRIMARY_NAV";
                        break;
                    }
                    case 8: {
                        charSequence = "SET_PRIMARY_NAV";
                        break;
                    }
                    case 7: {
                        charSequence = "ATTACH";
                        break;
                    }
                    case 6: {
                        charSequence = "DETACH";
                        break;
                    }
                    case 5: {
                        charSequence = "SHOW";
                        break;
                    }
                    case 4: {
                        charSequence = "HIDE";
                        break;
                    }
                    case 3: {
                        charSequence = "REMOVE";
                        break;
                    }
                    case 2: {
                        charSequence = "REPLACE";
                        break;
                    }
                    case 1: {
                        charSequence = "ADD";
                        break;
                    }
                    case 0: {
                        charSequence = "NULL";
                    }
                }
                printWriter.print(string2);
                printWriter.print("  Op #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.print((String)charSequence);
                printWriter.print(" ");
                printWriter.println(op.fragment);
                if (!bl) continue;
                if (op.enterAnim != 0 || op.exitAnim != 0) {
                    printWriter.print(string3);
                    printWriter.print("enterAnim=#");
                    printWriter.print(Integer.toHexString(op.enterAnim));
                    printWriter.print(" exitAnim=#");
                    printWriter.println(Integer.toHexString(op.exitAnim));
                }
                if (op.popEnterAnim == 0 && op.popExitAnim == 0) continue;
                printWriter.print(string3);
                printWriter.print("popEnterAnim=#");
                printWriter.print(Integer.toHexString(op.popEnterAnim));
                printWriter.print(" popExitAnim=#");
                printWriter.println(Integer.toHexString(op.popExitAnim));
            }
        }
    }

    void executeOps() {
        Object object;
        int n = this.mOps.size();
        for (int i = 0; i < n; ++i) {
            object = this.mOps.get(i);
            Object object2 = ((Op)object).fragment;
            if (object2 != null) {
                ((Fragment)object2).setNextTransition(this.mTransition, this.mTransitionStyle);
            }
            switch (((Op)object).cmd) {
                default: {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unknown cmd: ");
                    ((StringBuilder)object2).append(((Op)object).cmd);
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
                case 9: {
                    this.mManager.setPrimaryNavigationFragment(null);
                    break;
                }
                case 8: {
                    this.mManager.setPrimaryNavigationFragment((Fragment)object2);
                    break;
                }
                case 7: {
                    ((Fragment)object2).setNextAnim(((Op)object).enterAnim);
                    this.mManager.attachFragment((Fragment)object2);
                    break;
                }
                case 6: {
                    ((Fragment)object2).setNextAnim(((Op)object).exitAnim);
                    this.mManager.detachFragment((Fragment)object2);
                    break;
                }
                case 5: {
                    ((Fragment)object2).setNextAnim(((Op)object).enterAnim);
                    this.mManager.showFragment((Fragment)object2);
                    break;
                }
                case 4: {
                    ((Fragment)object2).setNextAnim(((Op)object).exitAnim);
                    this.mManager.hideFragment((Fragment)object2);
                    break;
                }
                case 3: {
                    ((Fragment)object2).setNextAnim(((Op)object).exitAnim);
                    this.mManager.removeFragment((Fragment)object2);
                    break;
                }
                case 1: {
                    ((Fragment)object2).setNextAnim(((Op)object).enterAnim);
                    this.mManager.addFragment((Fragment)object2, false);
                }
            }
            if (this.mReorderingAllowed || ((Op)object).cmd == 1 || object2 == null) continue;
            this.mManager.moveFragmentToExpectedState((Fragment)object2);
        }
        if (!this.mReorderingAllowed) {
            object = this.mManager;
            ((FragmentManagerImpl)object).moveToState(((FragmentManagerImpl)object).mCurState, true);
        }
    }

    void executePopOps(boolean bl) {
        Object object;
        for (int i = this.mOps.size() - 1; i >= 0; --i) {
            object = this.mOps.get(i);
            Object object2 = ((Op)object).fragment;
            if (object2 != null) {
                ((Fragment)object2).setNextTransition(FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
            }
            switch (((Op)object).cmd) {
                default: {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unknown cmd: ");
                    ((StringBuilder)object2).append(((Op)object).cmd);
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
                case 9: {
                    this.mManager.setPrimaryNavigationFragment((Fragment)object2);
                    break;
                }
                case 8: {
                    this.mManager.setPrimaryNavigationFragment(null);
                    break;
                }
                case 7: {
                    ((Fragment)object2).setNextAnim(((Op)object).popExitAnim);
                    this.mManager.detachFragment((Fragment)object2);
                    break;
                }
                case 6: {
                    ((Fragment)object2).setNextAnim(((Op)object).popEnterAnim);
                    this.mManager.attachFragment((Fragment)object2);
                    break;
                }
                case 5: {
                    ((Fragment)object2).setNextAnim(((Op)object).popExitAnim);
                    this.mManager.hideFragment((Fragment)object2);
                    break;
                }
                case 4: {
                    ((Fragment)object2).setNextAnim(((Op)object).popEnterAnim);
                    this.mManager.showFragment((Fragment)object2);
                    break;
                }
                case 3: {
                    ((Fragment)object2).setNextAnim(((Op)object).popEnterAnim);
                    this.mManager.addFragment((Fragment)object2, false);
                    break;
                }
                case 1: {
                    ((Fragment)object2).setNextAnim(((Op)object).popExitAnim);
                    this.mManager.removeFragment((Fragment)object2);
                }
            }
            if (this.mReorderingAllowed || ((Op)object).cmd == 3 || object2 == null) continue;
            this.mManager.moveFragmentToExpectedState((Fragment)object2);
        }
        if (!this.mReorderingAllowed && bl) {
            object = this.mManager;
            ((FragmentManagerImpl)object).moveToState(((FragmentManagerImpl)object).mCurState, true);
        }
    }

    Fragment expandOps(ArrayList<Fragment> arrayList, Fragment object) {
        int n = 0;
        Fragment fragment = object;
        while (n < this.mOps.size()) {
            int n2;
            block14 : {
                Op op;
                block11 : {
                    block12 : {
                        block13 : {
                            op = this.mOps.get(n);
                            n2 = op.cmd;
                            if (n2 == 1) break block11;
                            if (n2 == 2) break block12;
                            if (n2 == 3 || n2 == 6) break block13;
                            if (n2 == 7) break block11;
                            if (n2 != 8) {
                                n2 = n;
                                object = fragment;
                            } else {
                                this.mOps.add(n, new Op(9, fragment));
                                n2 = n + 1;
                                object = op.fragment;
                            }
                            break block14;
                        }
                        arrayList.remove(op.fragment);
                        n2 = n;
                        object = fragment;
                        if (op.fragment == fragment) {
                            this.mOps.add(n, new Op(9, op.fragment));
                            n2 = n + 1;
                            object = null;
                        }
                        break block14;
                    }
                    Fragment fragment2 = op.fragment;
                    int n3 = fragment2.mContainerId;
                    int n4 = 0;
                    object = fragment;
                    for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
                        Fragment fragment3 = arrayList.get(n2);
                        int n5 = n;
                        int n6 = n4;
                        fragment = object;
                        if (fragment3.mContainerId == n3) {
                            if (fragment3 == fragment2) {
                                n6 = 1;
                                n5 = n;
                                fragment = object;
                            } else {
                                n6 = n;
                                fragment = object;
                                if (fragment3 == object) {
                                    this.mOps.add(n, new Op(9, fragment3));
                                    n6 = n + 1;
                                    fragment = null;
                                }
                                object = new Op(3, fragment3);
                                ((Op)object).enterAnim = op.enterAnim;
                                ((Op)object).popEnterAnim = op.popEnterAnim;
                                ((Op)object).exitAnim = op.exitAnim;
                                ((Op)object).popExitAnim = op.popExitAnim;
                                this.mOps.add(n6, (Op)object);
                                arrayList.remove(fragment3);
                                n5 = n6 + 1;
                                n6 = n4;
                            }
                        }
                        n = n5;
                        n4 = n6;
                        object = fragment;
                    }
                    if (n4 != 0) {
                        this.mOps.remove(n);
                        --n;
                    } else {
                        op.cmd = 1;
                        arrayList.add(fragment2);
                    }
                    n2 = n;
                    break block14;
                }
                arrayList.add(op.fragment);
                object = fragment;
                n2 = n;
            }
            n = n2 + 1;
            fragment = object;
        }
        return fragment;
    }

    @Override
    public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (FragmentManagerImpl.DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Run: ");
            stringBuilder.append(this);
            Log.v(TAG, stringBuilder.toString());
        }
        arrayList.add(this);
        arrayList2.add(false);
        if (this.mAddToBackStack) {
            this.mManager.addBackStackState(this);
        }
        return true;
    }

    @Override
    public CharSequence getBreadCrumbShortTitle() {
        if (this.mBreadCrumbShortTitleRes != 0 && this.mManager.mHost != null) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes);
        }
        return this.mBreadCrumbShortTitleText;
    }

    @Override
    public int getBreadCrumbShortTitleRes() {
        return this.mBreadCrumbShortTitleRes;
    }

    @Override
    public CharSequence getBreadCrumbTitle() {
        if (this.mBreadCrumbTitleRes != 0 && this.mManager.mHost != null) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes);
        }
        return this.mBreadCrumbTitleText;
    }

    @Override
    public int getBreadCrumbTitleRes() {
        return this.mBreadCrumbTitleRes;
    }

    @Override
    public int getId() {
        return this.mIndex;
    }

    @Override
    public String getName() {
        return this.mName;
    }

    public int getTransition() {
        return this.mTransition;
    }

    public int getTransitionStyle() {
        return this.mTransitionStyle;
    }

    @Override
    public FragmentTransaction hide(Fragment fragment) {
        this.addOp(new Op(4, fragment));
        return this;
    }

    boolean interactsWith(int n) {
        int n2 = this.mOps.size();
        int n3 = 0;
        do {
            int n4 = 0;
            if (n3 >= n2) break;
            Op op = this.mOps.get(n3);
            if (op.fragment != null) {
                n4 = op.fragment.mContainerId;
            }
            if (n4 != 0 && n4 == n) {
                return true;
            }
            ++n3;
        } while (true);
        return false;
    }

    boolean interactsWith(ArrayList<BackStackRecord> arrayList, int n, int n2) {
        if (n2 == n) {
            return false;
        }
        int n3 = this.mOps.size();
        int n4 = -1;
        for (int i = 0; i < n3; ++i) {
            Object object = this.mOps.get(i);
            int n5 = ((Op)object).fragment != null ? object.fragment.mContainerId : 0;
            int n6 = n4;
            if (n5 != 0) {
                n6 = n4;
                if (n5 != n4) {
                    n4 = n5;
                    int n7 = n;
                    do {
                        n6 = n4;
                        if (n7 >= n2) break;
                        object = arrayList.get(n7);
                        int n8 = ((BackStackRecord)object).mOps.size();
                        for (n6 = 0; n6 < n8; ++n6) {
                            Op op = ((BackStackRecord)object).mOps.get(n6);
                            int n9 = op.fragment != null ? op.fragment.mContainerId : 0;
                            if (n9 != n5) continue;
                            return true;
                        }
                        ++n7;
                    } while (true);
                }
            }
            n4 = n6;
        }
        return false;
    }

    @Override
    public boolean isAddToBackStackAllowed() {
        return this.mAllowAddToBackStack;
    }

    @Override
    public boolean isEmpty() {
        return this.mOps.isEmpty();
    }

    boolean isPostponed() {
        for (int i = 0; i < this.mOps.size(); ++i) {
            if (!BackStackRecord.isFragmentPostponed(this.mOps.get(i))) continue;
            return true;
        }
        return false;
    }

    @Override
    public FragmentTransaction remove(Fragment fragment) {
        this.addOp(new Op(3, fragment));
        return this;
    }

    @Override
    public FragmentTransaction replace(int n, Fragment fragment) {
        return this.replace(n, fragment, null);
    }

    @Override
    public FragmentTransaction replace(int n, Fragment fragment, String string2) {
        if (n != 0) {
            this.doAddOp(n, fragment, string2, 2);
            return this;
        }
        throw new IllegalArgumentException("Must use non-zero containerViewId");
    }

    @Override
    public FragmentTransaction runOnCommit(Runnable runnable) {
        if (runnable != null) {
            this.disallowAddToBackStack();
            if (this.mCommitRunnables == null) {
                this.mCommitRunnables = new ArrayList();
            }
            this.mCommitRunnables.add(runnable);
            return this;
        }
        throw new IllegalArgumentException("runnable cannot be null");
    }

    public void runOnCommitRunnables() {
        ArrayList<Runnable> arrayList = this.mCommitRunnables;
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                this.mCommitRunnables.get(i).run();
            }
            this.mCommitRunnables = null;
        }
    }

    @Override
    public FragmentTransaction setBreadCrumbShortTitle(int n) {
        this.mBreadCrumbShortTitleRes = n;
        this.mBreadCrumbShortTitleText = null;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbShortTitle(CharSequence charSequence) {
        this.mBreadCrumbShortTitleRes = 0;
        this.mBreadCrumbShortTitleText = charSequence;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbTitle(int n) {
        this.mBreadCrumbTitleRes = n;
        this.mBreadCrumbTitleText = null;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbTitle(CharSequence charSequence) {
        this.mBreadCrumbTitleRes = 0;
        this.mBreadCrumbTitleText = charSequence;
        return this;
    }

    @Override
    public FragmentTransaction setCustomAnimations(int n, int n2) {
        return this.setCustomAnimations(n, n2, 0, 0);
    }

    @Override
    public FragmentTransaction setCustomAnimations(int n, int n2, int n3, int n4) {
        this.mEnterAnim = n;
        this.mExitAnim = n2;
        this.mPopEnterAnim = n3;
        this.mPopExitAnim = n4;
        return this;
    }

    void setOnStartPostponedListener(Fragment.OnStartEnterTransitionListener onStartEnterTransitionListener) {
        for (int i = 0; i < this.mOps.size(); ++i) {
            Op op = this.mOps.get(i);
            if (!BackStackRecord.isFragmentPostponed(op)) continue;
            op.fragment.setOnStartEnterTransitionListener(onStartEnterTransitionListener);
        }
    }

    @Override
    public FragmentTransaction setPrimaryNavigationFragment(Fragment fragment) {
        this.addOp(new Op(8, fragment));
        return this;
    }

    @Override
    public FragmentTransaction setReorderingAllowed(boolean bl) {
        this.mReorderingAllowed = bl;
        return this;
    }

    @Override
    public FragmentTransaction setTransition(int n) {
        this.mTransition = n;
        return this;
    }

    @Override
    public FragmentTransaction setTransitionStyle(int n) {
        this.mTransitionStyle = n;
        return this;
    }

    @Override
    public FragmentTransaction show(Fragment fragment) {
        this.addOp(new Op(5, fragment));
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("BackStackEntry{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            stringBuilder.append(" #");
            stringBuilder.append(this.mIndex);
        }
        if (this.mName != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.mName);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    void trackAddedFragmentsInPop(ArrayList<Fragment> arrayList) {
        for (int i = 0; i < this.mOps.size(); ++i) {
            Op op = this.mOps.get(i);
            int n = op.cmd;
            if (n != 1) {
                if (n != 3 && n != 6) {
                    if (n != 7) {
                        continue;
                    }
                } else {
                    arrayList.add(op.fragment);
                    continue;
                }
            }
            arrayList.remove(op.fragment);
        }
    }

    static final class Op {
        int cmd;
        int enterAnim;
        int exitAnim;
        Fragment fragment;
        int popEnterAnim;
        int popExitAnim;

        Op() {
        }

        Op(int n, Fragment fragment) {
            this.cmd = n;
            this.fragment = fragment;
        }
    }

}

