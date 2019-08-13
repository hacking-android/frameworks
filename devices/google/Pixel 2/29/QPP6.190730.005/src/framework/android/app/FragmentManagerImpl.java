/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.app.BackStackRecord;
import android.app.BackStackState;
import android.app.Fragment;
import android.app.FragmentContainer;
import android.app.FragmentHostCallback;
import android.app.FragmentManager;
import android.app.FragmentManagerNonConfig;
import android.app.FragmentManagerState;
import android.app.FragmentState;
import android.app.FragmentTransaction;
import android.app.FragmentTransition;
import android.app.LoaderManagerImpl;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.util.Log;
import android.util.LogWriter;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SuperNotCalledException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.R;
import com.android.internal.util.FastPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class FragmentManagerImpl
extends FragmentManager
implements LayoutInflater.Factory2 {
    static boolean DEBUG = false;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    @UnsupportedAppUsage
    SparseArray<Fragment> mActive;
    @UnsupportedAppUsage
    final ArrayList<Fragment> mAdded = new ArrayList();
    boolean mAllowOldReentrantBehavior;
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit = new Runnable(){

        @Override
        public void run() {
            FragmentManagerImpl.this.execPendingActions();
        }
    };
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback<?> mHost;
    final CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks = new CopyOnWriteArrayList();
    boolean mNeedMenuInvalidate;
    int mNextFragmentIndex = 0;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<OpGenerator> mPendingActions;
    ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    Fragment mPrimaryNav;
    FragmentManagerNonConfig mSavedNonConfig;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    @UnsupportedAppUsage
    boolean mStateSaved;
    ArrayList<Fragment> mTmpAddedFragments;
    ArrayList<Boolean> mTmpIsPop;
    ArrayList<BackStackRecord> mTmpRecords;

    static {
        DEBUG = false;
    }

    FragmentManagerImpl() {
    }

    private void addAddedFragments(ArraySet<Fragment> arraySet) {
        int n = this.mCurState;
        if (n < 1) {
            return;
        }
        int n2 = Math.min(n, 4);
        int n3 = this.mAdded.size();
        for (n = 0; n < n3; ++n) {
            Fragment fragment = this.mAdded.get(n);
            if (fragment.mState >= n2) continue;
            this.moveToState(fragment, n2, fragment.getNextAnim(), fragment.getNextTransition(), false);
            if (fragment.mView == null || fragment.mHidden || !fragment.mIsNewlyAdded) continue;
            arraySet.add(fragment);
        }
    }

    private void burpActive() {
        SparseArray<Fragment> sparseArray = this.mActive;
        if (sparseArray != null) {
            for (int i = sparseArray.size() - 1; i >= 0; --i) {
                if (this.mActive.valueAt(i) != null) continue;
                sparseArray = this.mActive;
                sparseArray.delete(sparseArray.keyAt(i));
            }
        }
    }

    private void checkStateLoss() {
        if (!this.mStateSaved) {
            if (this.mNoTransactionsBecause == null) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can not perform this action inside of ");
            stringBuilder.append(this.mNoTransactionsBecause);
            throw new IllegalStateException(stringBuilder.toString());
        }
        throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    private void completeExecute(BackStackRecord backStackRecord, boolean bl, boolean bl2, boolean bl3) {
        if (bl) {
            backStackRecord.executePopOps(bl3);
        } else {
            backStackRecord.executeOps();
        }
        ArrayList<BackStackRecord> arrayList = new ArrayList<BackStackRecord>(1);
        Object object = new ArrayList<Boolean>(1);
        arrayList.add(backStackRecord);
        ((ArrayList)object).add(bl);
        if (bl2) {
            FragmentTransition.startTransitions(this, arrayList, object, 0, 1, true);
        }
        if (bl3) {
            this.moveToState(this.mCurState, true);
        }
        if ((object = this.mActive) != null) {
            int n = ((SparseArray)object).size();
            for (int i = 0; i < n; ++i) {
                object = this.mActive.valueAt(i);
                if (object == null || ((Fragment)object).mView == null || !((Fragment)object).mIsNewlyAdded || !backStackRecord.interactsWith(((Fragment)object).mContainerId)) continue;
                ((Fragment)object).mIsNewlyAdded = false;
            }
        }
    }

    private void dispatchMoveToState(int n) {
        if (this.mAllowOldReentrantBehavior) {
            this.moveToState(n, false);
        } else {
            this.mExecutingActions = true;
            this.moveToState(n, false);
        }
        this.execPendingActions();
        return;
        finally {
            this.mExecutingActions = false;
        }
    }

    private void endAnimatingAwayFragments() {
        Object object = this.mActive;
        int n = object == null ? 0 : ((SparseArray)object).size();
        for (int i = 0; i < n; ++i) {
            object = this.mActive.valueAt(i);
            if (object == null || ((Fragment)object).getAnimatingAway() == null) continue;
            ((Fragment)object).getAnimatingAway().end();
        }
    }

    private void ensureExecReady(boolean bl) {
        if (!this.mExecutingActions) {
            if (Looper.myLooper() == this.mHost.getHandler().getLooper()) {
                if (!bl) {
                    this.checkStateLoss();
                }
                if (this.mTmpRecords == null) {
                    this.mTmpRecords = new ArrayList();
                    this.mTmpIsPop = new ArrayList();
                }
                this.mExecutingActions = true;
                try {
                    this.executePostponedTransaction(null, null);
                    return;
                }
                finally {
                    this.mExecutingActions = false;
                }
            }
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        throw new IllegalStateException("FragmentManager is already executing transactions");
    }

    private static void executeOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2) {
        while (n < n2) {
            BackStackRecord backStackRecord = arrayList.get(n);
            boolean bl = arrayList2.get(n);
            boolean bl2 = true;
            if (bl) {
                backStackRecord.bumpBackStackNesting(-1);
                if (n != n2 - 1) {
                    bl2 = false;
                }
                backStackRecord.executePopOps(bl2);
            } else {
                backStackRecord.bumpBackStackNesting(1);
                backStackRecord.executeOps();
            }
            ++n;
        }
    }

    private void executeOpsTogether(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2) {
        int n3;
        boolean bl = arrayList.get((int)n).mReorderingAllowed;
        ArrayList<Fragment> arrayList3 = this.mTmpAddedFragments;
        if (arrayList3 == null) {
            this.mTmpAddedFragments = new ArrayList();
        } else {
            arrayList3.clear();
        }
        this.mTmpAddedFragments.addAll(this.mAdded);
        arrayList3 = this.getPrimaryNavigationFragment();
        int n4 = n;
        int n5 = 0;
        do {
            int n6 = 1;
            if (n4 >= n2) break;
            BackStackRecord backStackRecord = arrayList.get(n4);
            if (!arrayList2.get(n4).booleanValue()) {
                arrayList3 = backStackRecord.expandOps(this.mTmpAddedFragments, (Fragment)((Object)arrayList3));
            } else {
                backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments);
            }
            n3 = n6;
            if (n5 == 0) {
                n3 = backStackRecord.mAddToBackStack ? n6 : 0;
            }
            ++n4;
            n5 = n3;
        } while (true);
        this.mTmpAddedFragments.clear();
        if (!bl) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, n, n2, false);
        }
        FragmentManagerImpl.executeOps(arrayList, arrayList2, n, n2);
        n3 = n2;
        if (bl) {
            arrayList3 = new ArraySet<Fragment>();
            this.addAddedFragments((ArraySet<Fragment>)((Object)arrayList3));
            n3 = this.postponePostponableTransactions(arrayList, arrayList2, n, n2, (ArraySet<Fragment>)((Object)arrayList3));
            this.makeRemovedFragmentsInvisible((ArraySet<Fragment>)((Object)arrayList3));
        }
        if (n3 != n && bl) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, n, n3, true);
            this.moveToState(this.mCurState, true);
        }
        while (n < n2) {
            arrayList3 = arrayList.get(n);
            if (arrayList2.get(n).booleanValue() && ((BackStackRecord)arrayList3).mIndex >= 0) {
                this.freeBackStackIndex(((BackStackRecord)arrayList3).mIndex);
                ((BackStackRecord)arrayList3).mIndex = -1;
            }
            ((BackStackRecord)((Object)arrayList3)).runOnCommitRunnables();
            ++n;
        }
        if (n5 != 0) {
            this.reportBackStackChanged();
        }
    }

    private void executePostponedTransaction(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        ArrayList<StartEnterTransitionListener> arrayList3 = this.mPostponedTransactions;
        int n = arrayList3 == null ? 0 : arrayList3.size();
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int n4;
            block7 : {
                block8 : {
                    block6 : {
                        arrayList3 = this.mPostponedTransactions.get(n2);
                        if (arrayList == null || ((StartEnterTransitionListener)((Object)arrayList3)).mIsBack || (n4 = arrayList.indexOf(((StartEnterTransitionListener)((Object)arrayList3)).mRecord)) == -1 || !arrayList2.get(n4).booleanValue()) break block6;
                        ((StartEnterTransitionListener)((Object)arrayList3)).cancelTransaction();
                        n4 = n;
                        n3 = n2;
                        break block7;
                    }
                    if (((StartEnterTransitionListener)((Object)arrayList3)).isReady()) break block8;
                    n4 = n;
                    n3 = n2;
                    if (arrayList == null) break block7;
                    n4 = n;
                    n3 = n2;
                    if (!((StartEnterTransitionListener)((Object)arrayList3)).mRecord.interactsWith(arrayList, 0, arrayList.size())) break block7;
                }
                this.mPostponedTransactions.remove(n2);
                n3 = n2 - 1;
                n4 = n - 1;
                if (arrayList != null && !((StartEnterTransitionListener)((Object)arrayList3)).mIsBack && (n2 = arrayList.indexOf(((StartEnterTransitionListener)((Object)arrayList3)).mRecord)) != -1 && arrayList2.get(n2).booleanValue()) {
                    ((StartEnterTransitionListener)((Object)arrayList3)).cancelTransaction();
                } else {
                    ((StartEnterTransitionListener)((Object)arrayList3)).completeTransaction();
                }
            }
            n2 = n3 + 1;
            n = n4;
        }
    }

    private Fragment findFragmentUnder(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        View view = fragment.mView;
        if (viewGroup != null && view != null) {
            for (int i = this.mAdded.indexOf((Object)fragment) - 1; i >= 0; --i) {
                fragment = this.mAdded.get(i);
                if (fragment.mContainer != viewGroup || fragment.mView == null) continue;
                return fragment;
            }
            return null;
        }
        return null;
    }

    private void forcePostponedTransactions() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                this.mPostponedTransactions.remove(0).completeTransaction();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        boolean bl = false;
        synchronized (this) {
            if (this.mPendingActions != null && this.mPendingActions.size() != 0) {
                int n = this.mPendingActions.size();
                int n2 = 0;
                do {
                    if (n2 >= n) {
                        this.mPendingActions.clear();
                        this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                        return bl;
                    }
                    bl |= this.mPendingActions.get(n2).generateOps(arrayList, arrayList2);
                    ++n2;
                } while (true);
            }
            return false;
        }
    }

    private void makeRemovedFragmentsInvisible(ArraySet<Fragment> arraySet) {
        int n = arraySet.size();
        for (int i = 0; i < n; ++i) {
            Fragment fragment = arraySet.valueAt(i);
            if (fragment.mAdded) continue;
            fragment.getView().setTransitionAlpha(0.0f);
        }
    }

    static boolean modifiesAlpha(Animator object) {
        block4 : {
            block3 : {
                if (object == null) {
                    return false;
                }
                if (!(object instanceof ValueAnimator)) break block3;
                object = ((ValueAnimator)object).getValues();
                for (int i = 0; i < ((PropertyValuesHolder[])object).length; ++i) {
                    if (!"alpha".equals(((PropertyValuesHolder)object[i]).getPropertyName())) continue;
                    return true;
                }
                break block4;
            }
            if (!(object instanceof AnimatorSet)) break block4;
            object = ((AnimatorSet)object).getChildAnimations();
            for (int i = 0; i < object.size(); ++i) {
                if (!FragmentManagerImpl.modifiesAlpha((Animator)object.get(i))) continue;
                return true;
            }
        }
        return false;
    }

    private boolean popBackStackImmediate(String string2, int n, int n2) {
        this.execPendingActions();
        this.ensureExecReady(true);
        Object object = this.mPrimaryNav;
        if (object != null && n < 0 && string2 == null && (object = ((Fragment)object).mChildFragmentManager) != null && ((FragmentManager)object).popBackStackImmediate()) {
            return true;
        }
        boolean bl = this.popBackStackState(this.mTmpRecords, this.mTmpIsPop, string2, n, n2);
        if (bl) {
            this.mExecutingActions = true;
            try {
                this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            }
            finally {
                this.cleanupExec();
            }
        }
        this.doPendingDeferredStart();
        this.burpActive();
        return bl;
    }

    private int postponePostponableTransactions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2, ArraySet<Fragment> arraySet) {
        int n3 = n2;
        for (int i = n2 - 1; i >= n; --i) {
            BackStackRecord backStackRecord = arrayList.get(i);
            boolean bl = arrayList2.get(i);
            boolean bl2 = backStackRecord.isPostponed() && !backStackRecord.interactsWith(arrayList, i + 1, n2);
            int n4 = n3;
            if (bl2) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList();
                }
                StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, bl);
                this.mPostponedTransactions.add(startEnterTransitionListener);
                backStackRecord.setOnStartPostponedListener(startEnterTransitionListener);
                if (bl) {
                    backStackRecord.executeOps();
                } else {
                    backStackRecord.executePopOps(false);
                }
                n4 = n3 - 1;
                if (i != n4) {
                    arrayList.remove(i);
                    arrayList.add(n4, backStackRecord);
                }
                this.addAddedFragments(arraySet);
            }
            n3 = n4;
        }
        return n3;
    }

    private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (arrayList != null && !arrayList.isEmpty()) {
            if (arrayList2 != null && arrayList.size() == arrayList2.size()) {
                this.executePostponedTransaction(arrayList, arrayList2);
                int n = arrayList.size();
                int n2 = 0;
                int n3 = 0;
                while (n3 < n) {
                    int n4 = n2;
                    int n5 = n3;
                    if (!arrayList.get((int)n3).mReorderingAllowed) {
                        if (n2 != n3) {
                            this.executeOpsTogether(arrayList, arrayList2, n2, n3);
                        }
                        n2 = n4 = n3 + 1;
                        if (arrayList2.get(n3).booleanValue()) {
                            do {
                                n2 = n4;
                                if (n4 >= n) break;
                                n2 = n4;
                                if (!arrayList2.get(n4).booleanValue()) break;
                                n2 = n4++;
                            } while (!arrayList.get((int)n4).mReorderingAllowed);
                        }
                        this.executeOpsTogether(arrayList, arrayList2, n3, n2);
                        n4 = n2;
                        n5 = n2 - 1;
                    }
                    n3 = n5 + 1;
                    n2 = n4;
                }
                if (n2 != n) {
                    this.executeOpsTogether(arrayList, arrayList2, n2, n);
                }
                return;
            }
            throw new IllegalStateException("Internal error with the back stack records");
        }
    }

    public static int reverseTransit(int n) {
        int n2 = 0;
        n = n != 4097 ? (n != 4099 ? (n != 8194 ? n2 : 4097) : 4099) : 8194;
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void scheduleCommit() {
        synchronized (this) {
            ArrayList<StartEnterTransitionListener> arrayList = this.mPostponedTransactions;
            boolean bl = false;
            boolean bl2 = arrayList != null && !this.mPostponedTransactions.isEmpty();
            boolean bl3 = bl;
            if (this.mPendingActions != null) {
                bl3 = bl;
                if (this.mPendingActions.size() == 1) {
                    bl3 = true;
                }
            }
            if (bl2 || bl3) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
            return;
        }
    }

    private void setHWLayerAnimListenerIfAlpha(View view, Animator animator2) {
        if (view != null && animator2 != null) {
            if (FragmentManagerImpl.shouldRunOnHWLayer(view, animator2)) {
                animator2.addListener(new AnimateOnHWLayerIfNeededListener(view));
            }
            return;
        }
    }

    private static void setRetaining(FragmentManagerNonConfig iterator) {
        if (iterator == null) {
            return;
        }
        List<Fragment> list = ((FragmentManagerNonConfig)((Object)iterator)).getFragments();
        if (list != null) {
            list = list.iterator();
            while (list.hasNext()) {
                ((Fragment)list.next()).mRetaining = true;
            }
        }
        if ((iterator = ((FragmentManagerNonConfig)((Object)iterator)).getChildNonConfigs()) != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                FragmentManagerImpl.setRetaining((FragmentManagerNonConfig)iterator.next());
            }
        }
    }

    static boolean shouldRunOnHWLayer(View view, Animator animator2) {
        boolean bl = false;
        if (view != null && animator2 != null) {
            if (view.getLayerType() == 0 && view.hasOverlappingRendering() && FragmentManagerImpl.modifiesAlpha(animator2)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private void throwException(RuntimeException runtimeException) {
        Log.e(TAG, runtimeException.getMessage());
        FastPrintWriter fastPrintWriter = new FastPrintWriter(new LogWriter(6, TAG), false, 1024);
        if (this.mHost != null) {
            Log.e(TAG, "Activity state:");
            try {
                this.mHost.onDump("  ", null, fastPrintWriter, new String[0]);
            }
            catch (Exception exception) {
                ((PrintWriter)fastPrintWriter).flush();
                Log.e(TAG, "Failed dumping state", exception);
            }
        } else {
            Log.e(TAG, "Fragment manager state:");
            try {
                this.dump("  ", null, fastPrintWriter, new String[0]);
            }
            catch (Exception exception) {
                ((PrintWriter)fastPrintWriter).flush();
                Log.e(TAG, "Failed dumping state", exception);
            }
        }
        ((PrintWriter)fastPrintWriter).flush();
        throw runtimeException;
    }

    public static int transitToStyleIndex(int n, boolean bl) {
        int n2 = -1;
        n = n != 4097 ? (n != 4099 ? (n != 8194 ? n2 : (bl ? 2 : 3)) : (bl ? 4 : 5)) : (bl ? 0 : 1);
        return n;
    }

    void addBackStackState(BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList();
        }
        this.mBackStack.add(backStackRecord);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addFragment(Fragment fragment, boolean bl) {
        Serializable serializable;
        if (DEBUG) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("add: ");
            ((StringBuilder)serializable).append(fragment);
            Log.v(TAG, ((StringBuilder)serializable).toString());
        }
        this.makeActive(fragment);
        if (fragment.mDetached) return;
        if (this.mAdded.contains(fragment)) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Fragment already added: ");
            ((StringBuilder)serializable).append(fragment);
            throw new IllegalStateException(((StringBuilder)serializable).toString());
        }
        serializable = this.mAdded;
        synchronized (serializable) {
            this.mAdded.add(fragment);
        }
        fragment.mAdded = true;
        fragment.mRemoving = false;
        if (fragment.mView == null) {
            fragment.mHiddenChanged = false;
        }
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        if (!bl) return;
        this.moveToState(fragment);
    }

    @Override
    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int allocBackStackIndex(BackStackRecord backStackRecord) {
        synchronized (this) {
            Serializable serializable;
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                int n = this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1);
                if (DEBUG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Adding back stack index ");
                    stringBuilder.append(n);
                    stringBuilder.append(" with ");
                    stringBuilder.append(backStackRecord);
                    Log.v(TAG, stringBuilder.toString());
                }
                this.mBackStackIndices.set(n, backStackRecord);
                return n;
            }
            if (this.mBackStackIndices == null) {
                serializable = new ArrayList();
                this.mBackStackIndices = serializable;
            }
            int n = this.mBackStackIndices.size();
            if (DEBUG) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Setting back stack index ");
                ((StringBuilder)serializable).append(n);
                ((StringBuilder)serializable).append(" to ");
                ((StringBuilder)serializable).append(backStackRecord);
                Log.v(TAG, ((StringBuilder)serializable).toString());
            }
            this.mBackStackIndices.add(backStackRecord);
            return n;
        }
    }

    public void attachController(FragmentHostCallback<?> fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment) {
        if (this.mHost == null) {
            this.mHost = fragmentHostCallback;
            this.mContainer = fragmentContainer;
            this.mParent = fragment;
            boolean bl = this.getTargetSdk() <= 25;
            this.mAllowOldReentrantBehavior = bl;
            return;
        }
        throw new IllegalStateException("Already attached");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void attachFragment(Fragment fragment) {
        Serializable serializable;
        if (DEBUG) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("attach: ");
            ((StringBuilder)serializable).append(fragment);
            Log.v(TAG, ((StringBuilder)serializable).toString());
        }
        if (!fragment.mDetached) return;
        fragment.mDetached = false;
        if (fragment.mAdded) return;
        if (this.mAdded.contains(fragment)) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Fragment already added: ");
            ((StringBuilder)serializable).append(fragment);
            throw new IllegalStateException(((StringBuilder)serializable).toString());
        }
        if (DEBUG) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("add from attach: ");
            ((StringBuilder)serializable).append(fragment);
            Log.v(TAG, ((StringBuilder)serializable).toString());
        }
        serializable = this.mAdded;
        synchronized (serializable) {
            this.mAdded.add(fragment);
        }
        fragment.mAdded = true;
        if (!fragment.mHasMenu) return;
        if (!fragment.mMenuVisible) return;
        this.mNeedMenuInvalidate = true;
    }

    @Override
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    void completeShowHideFragment(Fragment fragment) {
        if (fragment.mView != null) {
            Animator animator2 = this.loadAnimator(fragment, fragment.getNextTransition(), fragment.mHidden ^ true, fragment.getNextTransitionStyle());
            if (animator2 != null) {
                animator2.setTarget(fragment.mView);
                if (fragment.mHidden) {
                    if (fragment.isHideReplaced()) {
                        fragment.setHideReplaced(false);
                    } else {
                        final ViewGroup viewGroup = fragment.mContainer;
                        final View view = fragment.mView;
                        if (viewGroup != null) {
                            viewGroup.startViewTransition(view);
                        }
                        animator2.addListener(new AnimatorListenerAdapter(){

                            @Override
                            public void onAnimationEnd(Animator animator2) {
                                ViewGroup viewGroup2 = viewGroup;
                                if (viewGroup2 != null) {
                                    viewGroup2.endViewTransition(view);
                                }
                                animator2.removeListener(this);
                                view.setVisibility(8);
                            }
                        });
                    }
                } else {
                    fragment.mView.setVisibility(0);
                }
                this.setHWLayerAnimListenerIfAlpha(fragment.mView, animator2);
                animator2.start();
            } else {
                int n = fragment.mHidden && !fragment.isHideReplaced() ? 8 : 0;
                fragment.mView.setVisibility(n);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            }
        }
        if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void detachFragment(Fragment fragment) {
        Serializable serializable;
        if (DEBUG) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("detach: ");
            ((StringBuilder)serializable).append(fragment);
            Log.v(TAG, ((StringBuilder)serializable).toString());
        }
        if (fragment.mDetached) return;
        fragment.mDetached = true;
        if (!fragment.mAdded) return;
        if (DEBUG) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("remove from detach: ");
            ((StringBuilder)serializable).append(fragment);
            Log.v(TAG, ((StringBuilder)serializable).toString());
        }
        serializable = this.mAdded;
        // MONITORENTER : serializable
        this.mAdded.remove(fragment);
        // MONITOREXIT : serializable
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mAdded = false;
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.dispatchMoveToState(2);
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performConfigurationChanged(configuration);
        }
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null || !fragment.performContextItemSelected(menuItem)) continue;
            return true;
        }
        return false;
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        this.dispatchMoveToState(1);
    }

    public boolean dispatchCreateOptionsMenu(Menu object, MenuInflater menuInflater) {
        int n;
        if (this.mCurState < 1) {
            return false;
        }
        boolean bl = false;
        ArrayList<Fragment> arrayList = null;
        for (n = 0; n < this.mAdded.size(); ++n) {
            Fragment fragment = this.mAdded.get(n);
            boolean bl2 = bl;
            ArrayList<Fragment> arrayList2 = arrayList;
            if (fragment != null) {
                bl2 = bl;
                arrayList2 = arrayList;
                if (fragment.performCreateOptionsMenu((Menu)object, menuInflater)) {
                    bl2 = true;
                    arrayList2 = arrayList;
                    if (arrayList == null) {
                        arrayList2 = new ArrayList<Fragment>();
                    }
                    arrayList2.add(fragment);
                }
            }
            bl = bl2;
            arrayList = arrayList2;
        }
        if (this.mCreatedMenus != null) {
            for (n = 0; n < this.mCreatedMenus.size(); ++n) {
                object = this.mCreatedMenus.get(n);
                if (arrayList != null && arrayList.contains(object)) continue;
                ((Fragment)object).onDestroyOptionsMenu();
            }
        }
        this.mCreatedMenus = arrayList;
        return bl;
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        this.execPendingActions();
        this.dispatchMoveToState(0);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchDestroyView() {
        this.dispatchMoveToState(1);
    }

    public void dispatchLowMemory() {
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performLowMemory();
        }
    }

    @Deprecated
    public void dispatchMultiWindowModeChanged(boolean bl) {
        for (int i = this.mAdded.size() - 1; i >= 0; --i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performMultiWindowModeChanged(bl);
        }
    }

    public void dispatchMultiWindowModeChanged(boolean bl, Configuration configuration) {
        for (int i = this.mAdded.size() - 1; i >= 0; --i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performMultiWindowModeChanged(bl, configuration);
        }
    }

    void dispatchOnFragmentActivityCreated(Fragment fragment, Bundle bundle, boolean bl) {
        FragmentManager fragmentManager;
        Fragment object2 = this.mParent;
        if (object2 != null && (fragmentManager = object2.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentActivityCreated(fragment, bundle, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentActivityCreated(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentAttached(Fragment fragment, Context context, boolean bl) {
        FragmentManager fragmentManager;
        Fragment object2 = this.mParent;
        if (object2 != null && (fragmentManager = object2.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentAttached(fragment, context, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentAttached(this, fragment, context);
        }
    }

    void dispatchOnFragmentCreated(Fragment fragment, Bundle bundle, boolean bl) {
        FragmentManager fragmentManager;
        Fragment object2 = this.mParent;
        if (object2 != null && (fragmentManager = object2.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentCreated(fragment, bundle, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentCreated(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentDestroyed(Fragment fragment, boolean bl) {
        Object object = this.mParent;
        if (object != null && (object = ((Fragment)object).getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentDestroyed(fragment, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentDestroyed(this, fragment);
        }
    }

    void dispatchOnFragmentDetached(Fragment fragment, boolean bl) {
        FragmentManager fragmentManager;
        Fragment object2 = this.mParent;
        if (object2 != null && (fragmentManager = object2.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentDetached(fragment, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentDetached(this, fragment);
        }
    }

    void dispatchOnFragmentPaused(Fragment fragment, boolean bl) {
        FragmentManager fragmentManager;
        Fragment object2 = this.mParent;
        if (object2 != null && (fragmentManager = object2.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentPaused(fragment, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPaused(this, fragment);
        }
    }

    void dispatchOnFragmentPreAttached(Fragment fragment, Context context, boolean bl) {
        Object object = this.mParent;
        if (object != null && (object = ((Fragment)object).getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentPreAttached(fragment, context, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPreAttached(this, fragment, context);
        }
    }

    void dispatchOnFragmentPreCreated(Fragment fragment, Bundle bundle, boolean bl) {
        FragmentManager fragmentManager;
        Fragment object2 = this.mParent;
        if (object2 != null && (fragmentManager = object2.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentPreCreated(fragment, bundle, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPreCreated(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentResumed(Fragment fragment, boolean bl) {
        FragmentManager fragmentManager;
        Fragment object2 = this.mParent;
        if (object2 != null && (fragmentManager = object2.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentResumed(fragment, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentResumed(this, fragment);
        }
    }

    void dispatchOnFragmentSaveInstanceState(Fragment fragment, Bundle bundle, boolean bl) {
        FragmentManager fragmentManager;
        Fragment object2 = this.mParent;
        if (object2 != null && (fragmentManager = object2.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentSaveInstanceState(fragment, bundle, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentSaveInstanceState(this, fragment, bundle);
        }
    }

    void dispatchOnFragmentStarted(Fragment fragment, boolean bl) {
        Object object = this.mParent;
        if (object != null && (object = ((Fragment)object).getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentStarted(fragment, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentStarted(this, fragment);
        }
    }

    void dispatchOnFragmentStopped(Fragment fragment, boolean bl) {
        Object object = this.mParent;
        if (object != null && (object = ((Fragment)object).getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)object).dispatchOnFragmentStopped(fragment, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentStopped(this, fragment);
        }
    }

    void dispatchOnFragmentViewCreated(Fragment fragment, View view, Bundle bundle, boolean bl) {
        FragmentManager fragmentManager;
        Fragment object2 = this.mParent;
        if (object2 != null && (fragmentManager = object2.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentViewCreated(fragment, view, bundle, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentViewCreated(this, fragment, view, bundle);
        }
    }

    void dispatchOnFragmentViewDestroyed(Fragment fragment, boolean bl) {
        FragmentManager fragmentManager;
        Fragment object2 = this.mParent;
        if (object2 != null && (fragmentManager = object2.getFragmentManager()) instanceof FragmentManagerImpl) {
            ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentViewDestroyed(fragment, true);
        }
        for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
            if (bl && !((Boolean)pair.second).booleanValue()) continue;
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentViewDestroyed(this, fragment);
        }
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null || !fragment.performOptionsItemSelected(menuItem)) continue;
            return true;
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu2) {
        if (this.mCurState < 1) {
            return;
        }
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performOptionsMenuClosed(menu2);
        }
    }

    public void dispatchPause() {
        this.dispatchMoveToState(4);
    }

    @Deprecated
    public void dispatchPictureInPictureModeChanged(boolean bl) {
        for (int i = this.mAdded.size() - 1; i >= 0; --i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performPictureInPictureModeChanged(bl);
        }
    }

    public void dispatchPictureInPictureModeChanged(boolean bl, Configuration configuration) {
        for (int i = this.mAdded.size() - 1; i >= 0; --i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performPictureInPictureModeChanged(bl, configuration);
        }
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu2) {
        if (this.mCurState < 1) {
            return false;
        }
        boolean bl = false;
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            boolean bl2 = bl;
            if (fragment != null) {
                bl2 = bl;
                if (fragment.performPrepareOptionsMenu(menu2)) {
                    bl2 = true;
                }
            }
            bl = bl2;
        }
        return bl;
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        this.dispatchMoveToState(5);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        this.dispatchMoveToState(4);
    }

    public void dispatchStop() {
        this.dispatchMoveToState(3);
    }

    public void dispatchTrimMemory(int n) {
        for (int i = 0; i < this.mAdded.size(); ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.performTrimMemory(n);
        }
    }

    void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            boolean bl = false;
            for (int i = 0; i < this.mActive.size(); ++i) {
                Fragment fragment = this.mActive.valueAt(i);
                boolean bl2 = bl;
                if (fragment != null) {
                    bl2 = bl;
                    if (fragment.mLoaderManager != null) {
                        bl2 = bl | fragment.mLoaderManager.hasRunningLoaders();
                    }
                }
                bl = bl2;
            }
            if (!bl) {
                this.mHavePendingDeferredStart = false;
                this.startPendingDeferredFragments();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void dump(String string2, FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        int n;
        int n2;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string2);
        charSequence.append("    ");
        charSequence = charSequence.toString();
        Object object2 = this.mActive;
        if (object2 != null && (n2 = ((SparseArray)object2).size()) > 0) {
            printWriter.print(string2);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (n = 0; n < n2; ++n) {
                object2 = this.mActive.valueAt(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(object2);
                if (object2 == null) continue;
                ((Fragment)object2).dump((String)charSequence, (FileDescriptor)object, printWriter, arrstring);
            }
        }
        if ((n2 = this.mAdded.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Added Fragments:");
            for (n = 0; n < n2; ++n) {
                object2 = this.mAdded.get(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(((Fragment)object2).toString());
            }
        }
        if ((object2 = this.mCreatedMenus) != null && (n2 = ((ArrayList)object2).size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Fragments Created Menus:");
            for (n = 0; n < n2; ++n) {
                object2 = this.mCreatedMenus.get(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(((Fragment)object2).toString());
            }
        }
        if ((object2 = this.mBackStack) != null && (n2 = ((ArrayList)object2).size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Back Stack:");
            for (n = 0; n < n2; ++n) {
                object2 = this.mBackStack.get(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(((BackStackRecord)object2).toString());
                ((BackStackRecord)object2).dump((String)charSequence, (FileDescriptor)object, printWriter, arrstring);
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null && (n2 = this.mBackStackIndices.size()) > 0) {
                printWriter.print(string2);
                printWriter.println("Back Stack Indices:");
                for (n = 0; n < n2; ++n) {
                    object = this.mBackStackIndices.get(n);
                    printWriter.print(string2);
                    printWriter.print("  #");
                    printWriter.print(n);
                    printWriter.print(": ");
                    printWriter.println(object);
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                printWriter.print(string2);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        object = this.mPendingActions;
        if (object != null && (n2 = ((ArrayList)object).size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Pending Actions:");
            for (n = 0; n < n2; ++n) {
                object = this.mPendingActions.get(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n);
                printWriter.print(": ");
                printWriter.println(object);
            }
        }
        printWriter.print(string2);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(string2);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(string2);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(string2);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(string2);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(string2);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            printWriter.print(string2);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.mNoTransactionsBecause);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enqueueAction(OpGenerator object, boolean bl) {
        if (!bl) {
            this.checkStateLoss();
        }
        synchronized (this) {
            if (!this.mDestroyed && this.mHost != null) {
                if (this.mPendingActions == null) {
                    ArrayList arrayList = new ArrayList();
                    this.mPendingActions = arrayList;
                }
                this.mPendingActions.add((OpGenerator)object);
                this.scheduleCommit();
                return;
            }
            if (bl) {
                return;
            }
            object = new IllegalStateException("Activity has been destroyed");
            throw object;
        }
    }

    void ensureInflatedFragmentView(Fragment fragment) {
        if (fragment.mFromLayout && !fragment.mPerformedCreateView) {
            fragment.mView = fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), null, fragment.mSavedFragmentState);
            if (fragment.mView != null) {
                fragment.mView.setSaveFromParentEnabled(false);
                if (fragment.mHidden) {
                    fragment.mView.setVisibility(8);
                }
                fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                this.dispatchOnFragmentViewCreated(fragment, fragment.mView, fragment.mSavedFragmentState, false);
            }
        }
    }

    public boolean execPendingActions() {
        this.ensureExecReady(true);
        boolean bl = false;
        while (this.generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                bl = true;
            }
            finally {
                this.cleanupExec();
            }
        }
        this.doPendingDeferredStart();
        this.burpActive();
        return bl;
    }

    public void execSingleAction(OpGenerator opGenerator, boolean bl) {
        if (bl && (this.mHost == null || this.mDestroyed)) {
            return;
        }
        this.ensureExecReady(bl);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            }
            finally {
                this.cleanupExec();
            }
        }
        this.doPendingDeferredStart();
        this.burpActive();
    }

    @Override
    public boolean executePendingTransactions() {
        boolean bl = this.execPendingActions();
        this.forcePostponedTransactions();
        return bl;
    }

    @Override
    public Fragment findFragmentById(int n) {
        int n2;
        Object object;
        for (n2 = this.mAdded.size() - 1; n2 >= 0; --n2) {
            object = this.mAdded.get(n2);
            if (object == null || ((Fragment)object).mFragmentId != n) continue;
            return object;
        }
        object = this.mActive;
        if (object != null) {
            for (n2 = object.size() - 1; n2 >= 0; --n2) {
                object = this.mActive.valueAt(n2);
                if (object == null || ((Fragment)object).mFragmentId != n) continue;
                return object;
            }
        }
        return null;
    }

    @Override
    public Fragment findFragmentByTag(String string2) {
        int n;
        Object object;
        if (string2 != null) {
            for (n = this.mAdded.size() - 1; n >= 0; --n) {
                object = this.mAdded.get(n);
                if (object == null || !string2.equals(((Fragment)object).mTag)) continue;
                return object;
            }
        }
        if ((object = this.mActive) != null && string2 != null) {
            for (n = object.size() - 1; n >= 0; --n) {
                object = this.mActive.valueAt(n);
                if (object == null || !string2.equals(((Fragment)object).mTag)) continue;
                return object;
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String string2) {
        Object object = this.mActive;
        if (object != null && string2 != null) {
            for (int i = object.size() - 1; i >= 0; --i) {
                object = this.mActive.valueAt(i);
                if (object == null || (object = ((Fragment)object).findFragmentByWho(string2)) == null) continue;
                return object;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void freeBackStackIndex(int n) {
        synchronized (this) {
            Serializable serializable;
            this.mBackStackIndices.set(n, null);
            if (this.mAvailBackStackIndices == null) {
                serializable = new ArrayList();
                this.mAvailBackStackIndices = serializable;
            }
            if (DEBUG) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Freeing back stack index ");
                ((StringBuilder)serializable).append(n);
                Log.v(TAG, ((StringBuilder)serializable).toString());
            }
            this.mAvailBackStackIndices.add(n);
            return;
        }
    }

    @Override
    public FragmentManager.BackStackEntry getBackStackEntryAt(int n) {
        return this.mBackStack.get(n);
    }

    @Override
    public int getBackStackEntryCount() {
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        int n = arrayList != null ? arrayList.size() : 0;
        return n;
    }

    @Override
    public Fragment getFragment(Bundle object, String string2) {
        int n = ((BaseBundle)object).getInt(string2, -1);
        if (n == -1) {
            return null;
        }
        Fragment fragment = this.mActive.get(n);
        if (fragment == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Fragment no longer exists for key ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": index ");
            ((StringBuilder)object).append(n);
            this.throwException(new IllegalStateException(((StringBuilder)object).toString()));
        }
        return fragment;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<Fragment> getFragments() {
        if (this.mAdded.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<Fragment> arrayList = this.mAdded;
        synchronized (arrayList) {
            return (List)this.mAdded.clone();
        }
    }

    LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this;
    }

    @Override
    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    int getTargetSdk() {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null && (fragmentHostCallback = fragmentHostCallback.getContext()) != null && (fragmentHostCallback = ((Context)((Object)fragmentHostCallback)).getApplicationInfo()) != null) {
            return ((ApplicationInfo)fragmentHostCallback).targetSdkVersion;
        }
        return 0;
    }

    public void hideFragment(Fragment fragment) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("hide: ");
            stringBuilder.append(fragment);
            Log.v(TAG, stringBuilder.toString());
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        }
    }

    @Override
    public void invalidateOptionsMenu() {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null && this.mCurState == 5) {
            fragmentHostCallback.onInvalidateOptionsMenu();
        } else {
            this.mNeedMenuInvalidate = true;
        }
    }

    @Override
    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    boolean isStateAtLeast(int n) {
        boolean bl = this.mCurState >= n;
        return bl;
    }

    @Override
    public boolean isStateSaved() {
        return this.mStateSaved;
    }

    @UnsupportedAppUsage
    Animator loadAnimator(Fragment object, int n, boolean bl, int n2) {
        Animator animator2 = ((Fragment)object).onCreateAnimator(n, bl, ((Fragment)object).getNextAnim());
        if (animator2 != null) {
            return animator2;
        }
        if (((Fragment)object).getNextAnim() != 0 && (object = AnimatorInflater.loadAnimator(this.mHost.getContext(), ((Fragment)object).getNextAnim())) != null) {
            return object;
        }
        if (n == 0) {
            return null;
        }
        int n3 = FragmentManagerImpl.transitToStyleIndex(n, bl);
        if (n3 < 0) {
            return null;
        }
        n = n2;
        if (n2 == 0) {
            n = n2;
            if (this.mHost.onHasWindowAnimations()) {
                n = this.mHost.onGetWindowAnimations();
            }
        }
        if (n == 0) {
            return null;
        }
        object = this.mHost.getContext().obtainStyledAttributes(n, R.styleable.FragmentAnimation);
        n = ((TypedArray)object).getResourceId(n3, 0);
        ((TypedArray)object).recycle();
        if (n == 0) {
            return null;
        }
        return AnimatorInflater.loadAnimator(this.mHost.getContext(), n);
    }

    void makeActive(Fragment fragment) {
        if (fragment.mIndex >= 0) {
            return;
        }
        int n = this.mNextFragmentIndex;
        this.mNextFragmentIndex = n + 1;
        fragment.setIndex(n, this.mParent);
        if (this.mActive == null) {
            this.mActive = new SparseArray();
        }
        this.mActive.put(fragment.mIndex, fragment);
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Allocated fragment index ");
            stringBuilder.append(fragment);
            Log.v(TAG, stringBuilder.toString());
        }
    }

    void makeInactive(Fragment fragment) {
        if (fragment.mIndex < 0) {
            return;
        }
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Freeing fragment index ");
            stringBuilder.append(fragment);
            Log.v(TAG, stringBuilder.toString());
        }
        this.mActive.put(fragment.mIndex, null);
        this.mHost.inactivateFragment(fragment.mWho);
        fragment.initState();
    }

    void moveFragmentToExpectedState(Fragment fragment) {
        int n;
        if (fragment == null) {
            return;
        }
        int n2 = n = this.mCurState;
        if (fragment.mRemoving) {
            n2 = fragment.isInBackStack() ? Math.min(n, 1) : Math.min(n, 0);
        }
        this.moveToState(fragment, n2, fragment.getNextTransition(), fragment.getNextTransitionStyle(), false);
        if (fragment.mView != null) {
            Object object = this.findFragmentUnder(fragment);
            if (object != null) {
                object = ((Fragment)object).mView;
                ViewGroup viewGroup = fragment.mContainer;
                n2 = viewGroup.indexOfChild((View)object);
                n = viewGroup.indexOfChild(fragment.mView);
                if (n < n2) {
                    viewGroup.removeViewAt(n);
                    viewGroup.addView(fragment.mView, n2);
                }
            }
            if (fragment.mIsNewlyAdded && fragment.mContainer != null) {
                fragment.mView.setTransitionAlpha(1.0f);
                fragment.mIsNewlyAdded = false;
                object = this.loadAnimator(fragment, fragment.getNextTransition(), true, fragment.getNextTransitionStyle());
                if (object != null) {
                    ((Animator)object).setTarget(fragment.mView);
                    this.setHWLayerAnimListenerIfAlpha(fragment.mView, (Animator)object);
                    ((Animator)object).start();
                }
            }
        }
        if (fragment.mHiddenChanged) {
            this.completeShowHideFragment(fragment);
        }
    }

    void moveToState(int n, boolean bl) {
        block12 : {
            int n2;
            Object object;
            int n3;
            if (this.mHost == null && n != 0) {
                throw new IllegalStateException("No activity");
            }
            if (!bl && this.mCurState == n) {
                return;
            }
            this.mCurState = n;
            if (this.mActive == null) break block12;
            n = 0;
            int n4 = this.mAdded.size();
            for (n3 = 0; n3 < n4; ++n3) {
                object = this.mAdded.get(n3);
                this.moveFragmentToExpectedState((Fragment)object);
                n2 = n;
                if (((Fragment)object).mLoaderManager != null) {
                    n2 = n | ((Fragment)object).mLoaderManager.hasRunningLoaders();
                }
                n = n2;
            }
            n4 = this.mActive.size();
            n2 = n;
            for (n3 = 0; n3 < n4; ++n3) {
                block13 : {
                    block14 : {
                        object = this.mActive.valueAt(n3);
                        n = n2;
                        if (object == null) break block13;
                        if (((Fragment)object).mRemoving) break block14;
                        n = n2;
                        if (!((Fragment)object).mDetached) break block13;
                    }
                    n = n2;
                    if (!((Fragment)object).mIsNewlyAdded) {
                        this.moveFragmentToExpectedState((Fragment)object);
                        n = n2;
                        if (((Fragment)object).mLoaderManager != null) {
                            n = n2 | ((Fragment)object).mLoaderManager.hasRunningLoaders();
                        }
                    }
                }
                n2 = n;
            }
            if (n2 == 0) {
                this.startPendingDeferredFragments();
            }
            if (this.mNeedMenuInvalidate && (object = this.mHost) != null && this.mCurState == 5) {
                ((FragmentHostCallback)object).onInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    void moveToState(Fragment fragment) {
        this.moveToState(fragment, this.mCurState, 0, 0, false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void moveToState(final Fragment var1_1, int var2_2, int var3_3, int var4_4, boolean var5_5) {
        block69 : {
            block70 : {
                block62 : {
                    block68 : {
                        block67 : {
                            block66 : {
                                block65 : {
                                    block64 : {
                                        block63 : {
                                            var6_6 = FragmentManagerImpl.DEBUG;
                                            var7_7 = var1_1.mAdded;
                                            var6_6 = true;
                                            if (!var7_7 || var1_1.mDetached) {
                                                var2_2 = var8_8 = var2_2;
                                                if (var8_8 > 1) {
                                                    var2_2 = 1;
                                                }
                                            }
                                            var8_8 = var2_2;
                                            if (var1_1.mRemoving) {
                                                var8_8 = var2_2;
                                                if (var2_2 > var1_1.mState) {
                                                    var8_8 = var1_1.mState == 0 && var1_1.isInBackStack() != false ? 1 : var1_1.mState;
                                                }
                                            }
                                            var2_2 = var8_8;
                                            if (var1_1.mDeferStart) {
                                                var2_2 = var8_8;
                                                if (var1_1.mState < 4) {
                                                    var2_2 = var8_8;
                                                    if (var8_8 > 3) {
                                                        var2_2 = 3;
                                                    }
                                                }
                                            }
                                            if (var1_1.mState > var2_2) break block62;
                                            if (var1_1.mFromLayout && !var1_1.mInLayout) {
                                                return;
                                            }
                                            if (var1_1.getAnimatingAway() != null) {
                                                var1_1.setAnimatingAway(null);
                                                this.moveToState(var1_1, var1_1.getStateAfterAnimating(), 0, 0, true);
                                            }
                                            if ((var8_8 = var1_1.mState) == 0) break block63;
                                            if (var8_8 == 1) break block64;
                                            var3_3 = var2_2;
                                            if (var8_8 == 2) break block65;
                                            var4_4 = var2_2;
                                            if (var8_8 == 3) break block66;
                                            var3_3 = var2_2;
                                            if (var8_8 == 4) break block67;
                                            break block68;
                                        }
                                        if (var2_2 > 0) {
                                            if (FragmentManagerImpl.DEBUG) {
                                                var9_9 = new StringBuilder();
                                                var9_9.append("moveto CREATED: ");
                                                var9_9.append(var1_1);
                                                Log.v("FragmentManager", var9_9.toString());
                                            }
                                            var3_3 = var2_2;
                                            if (var1_1.mSavedFragmentState != null) {
                                                var1_1.mSavedViewState = var1_1.mSavedFragmentState.getSparseParcelableArray("android:view_state");
                                                var1_1.mTarget = this.getFragment(var1_1.mSavedFragmentState, "android:target_state");
                                                if (var1_1.mTarget != null) {
                                                    var1_1.mTargetRequestCode = var1_1.mSavedFragmentState.getInt("android:target_req_state", 0);
                                                }
                                                var1_1.mUserVisibleHint = var1_1.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
                                                var3_3 = var2_2;
                                                if (!var1_1.mUserVisibleHint) {
                                                    var1_1.mDeferStart = true;
                                                    var3_3 = var2_2;
                                                    if (var2_2 > 3) {
                                                        var3_3 = 3;
                                                    }
                                                }
                                            }
                                            var1_1.mHost = var9_9 = this.mHost;
                                            var10_11 = this.mParent;
                                            var1_1.mParentFragment = var10_11;
                                            var9_9 = var10_11 != null ? var10_11.mChildFragmentManager : var9_9.getFragmentManagerImpl();
                                            var1_1.mFragmentManager = var9_9;
                                            if (var1_1.mTarget != null) {
                                                if (this.mActive.get(var1_1.mTarget.mIndex) != var1_1.mTarget) {
                                                    var9_9 = new StringBuilder();
                                                    var9_9.append("Fragment ");
                                                    var9_9.append(var1_1);
                                                    var9_9.append(" declared target fragment ");
                                                    var9_9.append(var1_1.mTarget);
                                                    var9_9.append(" that does not belong to this FragmentManager!");
                                                    throw new IllegalStateException(var9_9.toString());
                                                }
                                                if (var1_1.mTarget.mState < 1) {
                                                    this.moveToState(var1_1.mTarget, 1, 0, 0, true);
                                                }
                                            }
                                            this.dispatchOnFragmentPreAttached(var1_1, this.mHost.getContext(), false);
                                            var1_1.mCalled = false;
                                            var1_1.onAttach(this.mHost.getContext());
                                            if (!var1_1.mCalled) {
                                                var9_9 = new StringBuilder();
                                                var9_9.append("Fragment ");
                                                var9_9.append(var1_1);
                                                var9_9.append(" did not call through to super.onAttach()");
                                                throw new SuperNotCalledException(var9_9.toString());
                                            }
                                            if (var1_1.mParentFragment == null) {
                                                this.mHost.onAttachFragment(var1_1);
                                            } else {
                                                var1_1.mParentFragment.onAttachFragment(var1_1);
                                            }
                                            this.dispatchOnFragmentAttached(var1_1, this.mHost.getContext(), false);
                                            if (!var1_1.mIsCreated) {
                                                this.dispatchOnFragmentPreCreated(var1_1, var1_1.mSavedFragmentState, false);
                                                var1_1.performCreate(var1_1.mSavedFragmentState);
                                                this.dispatchOnFragmentCreated(var1_1, var1_1.mSavedFragmentState, false);
                                            } else {
                                                var1_1.restoreChildFragmentState(var1_1.mSavedFragmentState, true);
                                                var1_1.mState = 1;
                                            }
                                            var1_1.mRetaining = false;
                                            var2_2 = var3_3;
                                        }
                                    }
                                    this.ensureInflatedFragmentView(var1_1);
                                    if (var2_2 > 1) {
                                        if (FragmentManagerImpl.DEBUG) {
                                            var9_9 = new StringBuilder();
                                            var9_9.append("moveto ACTIVITY_CREATED: ");
                                            var9_9.append(var1_1);
                                            Log.v("FragmentManager", var9_9.toString());
                                        }
                                        if (!var1_1.mFromLayout) {
                                            var9_9 = null;
                                            if (var1_1.mContainerId != 0) {
                                                if (var1_1.mContainerId == -1) {
                                                    var9_9 = new StringBuilder();
                                                    var9_9.append("Cannot create fragment ");
                                                    var9_9.append(var1_1);
                                                    var9_9.append(" for a container view with no id");
                                                    this.throwException(new IllegalArgumentException(var9_9.toString()));
                                                }
                                                if ((var10_11 = (ViewGroup)this.mContainer.onFindViewById(var1_1.mContainerId)) == null && !var1_1.mRestored) {
                                                    try {
                                                        var9_9 = var1_1.getResources().getResourceName(var1_1.mContainerId);
                                                    }
                                                    catch (Resources.NotFoundException var9_10) {
                                                        var9_9 = "unknown";
                                                    }
                                                    var11_13 = new StringBuilder();
                                                    var11_13.append("No view found for id 0x");
                                                    var11_13.append(Integer.toHexString(var1_1.mContainerId));
                                                    var11_13.append(" (");
                                                    var11_13.append((String)var9_9);
                                                    var11_13.append(") for fragment ");
                                                    var11_13.append(var1_1);
                                                    this.throwException(new IllegalArgumentException(var11_13.toString()));
                                                }
                                                var9_9 = var10_11;
                                            }
                                            var1_1.mContainer = var9_9;
                                            var1_1.mView = var1_1.performCreateView(var1_1.performGetLayoutInflater(var1_1.mSavedFragmentState), (ViewGroup)var9_9, var1_1.mSavedFragmentState);
                                            if (var1_1.mView != null) {
                                                var1_1.mView.setSaveFromParentEnabled(false);
                                                if (var9_9 != null) {
                                                    var9_9.addView(var1_1.mView);
                                                }
                                                if (var1_1.mHidden) {
                                                    var1_1.mView.setVisibility(8);
                                                }
                                                var1_1.onViewCreated(var1_1.mView, var1_1.mSavedFragmentState);
                                                this.dispatchOnFragmentViewCreated(var1_1, var1_1.mView, var1_1.mSavedFragmentState, false);
                                                var5_5 = var1_1.mView.getVisibility() == 0 && var1_1.mContainer != null ? var6_6 : false;
                                                var1_1.mIsNewlyAdded = var5_5;
                                            }
                                        }
                                        var1_1.performActivityCreated(var1_1.mSavedFragmentState);
                                        this.dispatchOnFragmentActivityCreated(var1_1, var1_1.mSavedFragmentState, false);
                                        if (var1_1.mView != null) {
                                            var1_1.restoreViewState(var1_1.mSavedFragmentState);
                                        }
                                        var1_1.mSavedFragmentState = null;
                                    }
                                    var3_3 = var2_2;
                                }
                                var4_4 = var3_3;
                                if (var3_3 > 2) {
                                    var1_1.mState = 3;
                                    var4_4 = var3_3;
                                }
                            }
                            var3_3 = var4_4;
                            if (var4_4 > 3) {
                                if (FragmentManagerImpl.DEBUG) {
                                    var9_9 = new StringBuilder();
                                    var9_9.append("moveto STARTED: ");
                                    var9_9.append(var1_1);
                                    Log.v("FragmentManager", var9_9.toString());
                                }
                                var1_1.performStart();
                                this.dispatchOnFragmentStarted(var1_1, false);
                                var3_3 = var4_4;
                            }
                        }
                        var2_2 = var3_3;
                        if (var3_3 > 4) {
                            if (FragmentManagerImpl.DEBUG) {
                                var9_9 = new StringBuilder();
                                var9_9.append("moveto RESUMED: ");
                                var9_9.append(var1_1);
                                Log.v("FragmentManager", var9_9.toString());
                            }
                            var1_1.performResume();
                            this.dispatchOnFragmentResumed(var1_1, false);
                            var1_1.mSavedFragmentState = null;
                            var1_1.mSavedViewState = null;
                            var2_2 = var3_3;
                        }
                    }
                    var3_3 = var2_2;
                    break block69;
                }
                if (var1_1.mState <= var2_2) break block70;
                var8_8 = var1_1.mState;
                if (var8_8 == 1) ** GOTO lbl269
                if (var8_8 == 2 || var8_8 == 3) ** GOTO lbl236
                if (var8_8 == 4) ** GOTO lbl225
                if (var8_8 != 5) {
                    var3_3 = var2_2;
                } else {
                    if (var2_2 < 5) {
                        if (FragmentManagerImpl.DEBUG) {
                            var9_9 = new StringBuilder();
                            var9_9.append("movefrom RESUMED: ");
                            var9_9.append(var1_1);
                            Log.v("FragmentManager", var9_9.toString());
                        }
                        var1_1.performPause();
                        this.dispatchOnFragmentPaused(var1_1, false);
                    }
lbl225: // 4 sources:
                    if (var2_2 < 4) {
                        if (FragmentManagerImpl.DEBUG) {
                            var9_9 = new StringBuilder();
                            var9_9.append("movefrom STARTED: ");
                            var9_9.append(var1_1);
                            Log.v("FragmentManager", var9_9.toString());
                        }
                        var1_1.performStop();
                        this.dispatchOnFragmentStopped(var1_1, false);
                    }
lbl236: // 4 sources:
                    if (var2_2 < 2) {
                        if (FragmentManagerImpl.DEBUG) {
                            var9_9 = new StringBuilder();
                            var9_9.append("movefrom ACTIVITY_CREATED: ");
                            var9_9.append(var1_1);
                            Log.v("FragmentManager", var9_9.toString());
                        }
                        if (var1_1.mView != null && this.mHost.onShouldSaveFragmentState(var1_1) && var1_1.mSavedViewState == null) {
                            this.saveFragmentViewState(var1_1);
                        }
                        var1_1.performDestroyView();
                        this.dispatchOnFragmentViewDestroyed(var1_1, false);
                        if (var1_1.mView != null && var1_1.mContainer != null) {
                            if (this.getTargetSdk() >= 26) {
                                var1_1.mView.clearAnimation();
                                var1_1.mContainer.endViewTransition(var1_1.mView);
                            }
                            var9_9 = this.mCurState > 0 && this.mDestroyed == false && var1_1.mView.getVisibility() == 0 && var1_1.mView.getTransitionAlpha() > 0.0f ? this.loadAnimator(var1_1, var3_3, false, var4_4) : null;
                            var1_1.mView.setTransitionAlpha(1.0f);
                            if (var9_9 != null) {
                                var10_12 = var1_1.mContainer;
                                var11_14 = var1_1.mView;
                                var10_12.startViewTransition(var11_14);
                                var1_1.setAnimatingAway((Animator)var9_9);
                                var1_1.setStateAfterAnimating(var2_2);
                                var9_9.addListener(new AnimatorListenerAdapter(){

                                    @Override
                                    public void onAnimationEnd(Animator object) {
                                        var10_12.endViewTransition(var11_14);
                                        object = var1_1.getAnimatingAway();
                                        var1_1.setAnimatingAway(null);
                                        if (var10_12.indexOfChild(var11_14) == -1 && object != null) {
                                            object = FragmentManagerImpl.this;
                                            Fragment fragment = var1_1;
                                            ((FragmentManagerImpl)object).moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
                                        }
                                    }
                                });
                                var9_9.setTarget(var1_1.mView);
                                this.setHWLayerAnimListenerIfAlpha(var1_1.mView, (Animator)var9_9);
                                var9_9.start();
                            }
                            var1_1.mContainer.removeView(var1_1.mView);
                        }
                        var1_1.mContainer = null;
                        var1_1.mView = null;
                        var1_1.mInLayout = false;
                    }
lbl269: // 4 sources:
                    var3_3 = var2_2;
                    if (var2_2 < 1) {
                        if (this.mDestroyed && var1_1.getAnimatingAway() != null) {
                            var9_9 = var1_1.getAnimatingAway();
                            var1_1.setAnimatingAway(null);
                            var9_9.cancel();
                        }
                        if (var1_1.getAnimatingAway() != null) {
                            var1_1.setStateAfterAnimating(var2_2);
                            var3_3 = 1;
                        } else {
                            if (FragmentManagerImpl.DEBUG) {
                                var9_9 = new StringBuilder();
                                var9_9.append("movefrom CREATED: ");
                                var9_9.append(var1_1);
                                Log.v("FragmentManager", var9_9.toString());
                            }
                            if (!var1_1.mRetaining) {
                                var1_1.performDestroy();
                                this.dispatchOnFragmentDestroyed(var1_1, false);
                            } else {
                                var1_1.mState = 0;
                            }
                            var1_1.performDetach();
                            this.dispatchOnFragmentDetached(var1_1, false);
                            var3_3 = var2_2;
                            if (!var5_5) {
                                if (!var1_1.mRetaining) {
                                    this.makeInactive(var1_1);
                                    var3_3 = var2_2;
                                } else {
                                    var1_1.mHost = null;
                                    var1_1.mParentFragment = null;
                                    var1_1.mFragmentManager = null;
                                    var3_3 = var2_2;
                                }
                            }
                        }
                    }
                }
                break block69;
            }
            var3_3 = var2_2;
        }
        if (var1_1.mState == var3_3) return;
        var9_9 = new StringBuilder();
        var9_9.append("moveToState: Fragment state for ");
        var9_9.append(var1_1);
        var9_9.append(" not updated inline; expected state ");
        var9_9.append(var3_3);
        var9_9.append(" found ");
        var9_9.append(var1_1.mState);
        Log.w("FragmentManager", var9_9.toString());
        var1_1.mState = var3_3;
    }

    @UnsupportedAppUsage
    public void noteStateNotSaved() {
        this.mSavedNonConfig = null;
        this.mStateSaved = false;
        int n = this.mAdded.size();
        for (int i = 0; i < n; ++i) {
            Fragment fragment = this.mAdded.get(i);
            if (fragment == null) continue;
            fragment.noteStateNotSaved();
        }
    }

    @Override
    public View onCreateView(View object, String object2, Context context, AttributeSet attributeSet) {
        String string2;
        int n;
        int n2;
        String string3;
        block20 : {
            block19 : {
                block18 : {
                    if (!"fragment".equals(object2)) {
                        return null;
                    }
                    string3 = attributeSet.getAttributeValue(null, "class");
                    object2 = context.obtainStyledAttributes(attributeSet, R.styleable.Fragment);
                    n2 = 0;
                    if (string3 == null) {
                        string3 = ((TypedArray)object2).getString(0);
                    }
                    n = ((TypedArray)object2).getResourceId(1, -1);
                    string2 = ((TypedArray)object2).getString(2);
                    ((TypedArray)object2).recycle();
                    if (object != null) {
                        n2 = ((View)object).getId();
                    }
                    if (n2 == -1 && n == -1 && string2 == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append(attributeSet.getPositionDescription());
                        ((StringBuilder)object).append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
                        ((StringBuilder)object).append(string3);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    object2 = n != -1 ? this.findFragmentById(n) : null;
                    object = object2;
                    if (object2 == null) {
                        object = object2;
                        if (string2 != null) {
                            object = this.findFragmentByTag(string2);
                        }
                    }
                    object2 = object;
                    if (object == null) {
                        object2 = object;
                        if (n2 != -1) {
                            object2 = this.findFragmentById(n2);
                        }
                    }
                    if (DEBUG) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("onCreateView: id=0x");
                        ((StringBuilder)object).append(Integer.toHexString(n));
                        ((StringBuilder)object).append(" fname=");
                        ((StringBuilder)object).append(string3);
                        ((StringBuilder)object).append(" existing=");
                        ((StringBuilder)object).append(object2);
                        Log.v(TAG, ((StringBuilder)object).toString());
                    }
                    if (object2 != null) break block18;
                    object = this.mContainer.instantiate(context, string3, null);
                    ((Fragment)object).mFromLayout = true;
                    int n3 = n != 0 ? n : n2;
                    ((Fragment)object).mFragmentId = n3;
                    ((Fragment)object).mContainerId = n2;
                    ((Fragment)object).mTag = string2;
                    ((Fragment)object).mInLayout = true;
                    ((Fragment)object).mFragmentManager = this;
                    ((Fragment)object).mHost = object2 = this.mHost;
                    ((Fragment)object).onInflate(((FragmentHostCallback)object2).getContext(), attributeSet, ((Fragment)object).mSavedFragmentState);
                    this.addFragment((Fragment)object, true);
                    break block19;
                }
                if (((Fragment)object2).mInLayout) break block20;
                ((Fragment)object2).mInLayout = true;
                ((Fragment)object2).mHost = this.mHost;
                if (!((Fragment)object2).mRetaining) {
                    ((Fragment)object2).onInflate(this.mHost.getContext(), attributeSet, ((Fragment)object2).mSavedFragmentState);
                }
                object = object2;
            }
            if (this.mCurState < 1 && ((Fragment)object).mFromLayout) {
                this.moveToState((Fragment)object, 1, 0, 0, false);
            } else {
                this.moveToState((Fragment)object);
            }
            if (((Fragment)object).mView != null) {
                if (n != 0) {
                    ((Fragment)object).mView.setId(n);
                }
                if (((Fragment)object).mView.getTag() == null) {
                    ((Fragment)object).mView.setTag(string2);
                }
                return ((Fragment)object).mView;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Fragment ");
            ((StringBuilder)object).append(string3);
            ((StringBuilder)object).append(" did not create a view.");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(attributeSet.getPositionDescription());
        ((StringBuilder)object).append(": Duplicate id 0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        ((StringBuilder)object).append(", tag ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(", or parent id 0x");
        ((StringBuilder)object).append(Integer.toHexString(n2));
        ((StringBuilder)object).append(" with another fragment for ");
        ((StringBuilder)object).append(string3);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public View onCreateView(String string2, Context context, AttributeSet attributeSet) {
        return null;
    }

    public void performPendingDeferredStart(Fragment fragment) {
        if (fragment.mDeferStart) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true;
                return;
            }
            fragment.mDeferStart = false;
            this.moveToState(fragment, this.mCurState, 0, 0, false);
        }
    }

    @Override
    public void popBackStack() {
        this.enqueueAction(new PopBackStackState(null, -1, 0), false);
    }

    @Override
    public void popBackStack(int n, int n2) {
        if (n >= 0) {
            this.enqueueAction(new PopBackStackState(null, n, n2), false);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad id: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void popBackStack(String string2, int n) {
        this.enqueueAction(new PopBackStackState(string2, -1, n), false);
    }

    @Override
    public boolean popBackStackImmediate() {
        this.checkStateLoss();
        return this.popBackStackImmediate(null, -1, 0);
    }

    @Override
    public boolean popBackStackImmediate(int n, int n2) {
        this.checkStateLoss();
        if (n >= 0) {
            return this.popBackStackImmediate(null, n, n2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad id: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public boolean popBackStackImmediate(String string2, int n) {
        this.checkStateLoss();
        return this.popBackStackImmediate(string2, -1, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    boolean popBackStackState(ArrayList<BackStackRecord> var1_1, ArrayList<Boolean> var2_2, String var3_3, int var4_4, int var5_5) {
        block8 : {
            var6_6 = this.mBackStack;
            if (var6_6 == null) {
                return false;
            }
            if (var3_3 == null && var4_4 < 0 && (var5_5 & 1) == 0) {
                var4_4 = var6_6.size() - 1;
                if (var4_4 < 0) {
                    return false;
                }
                var1_1.add(this.mBackStack.remove(var4_4));
                var2_2.add(true);
                return true;
            }
            var7_7 = -1;
            if (var3_3 == null && var4_4 < 0) break block8;
            for (var8_8 = this.mBackStack.size() - 1; var8_8 >= 0; --var8_8) {
                var6_6 = this.mBackStack.get(var8_8);
                if (var3_3 != null && var3_3.equals(var6_6.getName()) || var4_4 >= 0 && var4_4 == var6_6.mIndex) break;
            }
            if (var8_8 < 0) {
                return false;
            }
            var7_7 = var8_8;
            if ((var5_5 & 1) == 0) break block8;
            var5_5 = var8_8 - 1;
            do lbl-1000: // 3 sources:
            {
                var7_7 = --var5_5;
                if (var5_5 < 0) break;
                var6_6 = this.mBackStack.get(var5_5);
                if (var3_3 != null && var3_3.equals(var6_6.getName())) ** GOTO lbl-1000
                var7_7 = var5_5;
                if (var4_4 < 0) break;
                var7_7 = var5_5;
            } while (var4_4 == var6_6.mIndex);
        }
        if (var7_7 == this.mBackStack.size() - 1) {
            return false;
        }
        var4_4 = this.mBackStack.size() - 1;
        while (var4_4 > var7_7) {
            var1_1.add(this.mBackStack.remove(var4_4));
            var2_2.add(true);
            --var4_4;
        }
        return true;
    }

    @Override
    public void putFragment(Bundle bundle, String string2, Fragment fragment) {
        if (fragment.mIndex < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not currently in the FragmentManager");
            this.throwException(new IllegalStateException(stringBuilder.toString()));
        }
        bundle.putInt(string2, fragment.mIndex);
    }

    @Override
    public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean bl) {
        this.mLifecycleCallbacks.add(new Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>(fragmentLifecycleCallbacks, bl));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void removeFragment(Fragment fragment) {
        Serializable serializable;
        if (DEBUG) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("remove: ");
            ((StringBuilder)serializable).append(fragment);
            ((StringBuilder)serializable).append(" nesting=");
            ((StringBuilder)serializable).append(fragment.mBackStackNesting);
            Log.v(TAG, ((StringBuilder)serializable).toString());
        }
        boolean bl = fragment.isInBackStack();
        if (fragment.mDetached) {
            if (!(bl ^ true)) return;
        }
        serializable = this.mAdded;
        // MONITORENTER : serializable
        this.mAdded.remove(fragment);
        // MONITOREXIT : serializable
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mAdded = false;
        fragment.mRemoving = true;
    }

    @Override
    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        ArrayList<FragmentManager.OnBackStackChangedListener> arrayList = this.mBackStackChangeListeners;
        if (arrayList != null) {
            arrayList.remove(onBackStackChangedListener);
        }
    }

    void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); ++i) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void restoreAllState(Parcelable object, FragmentManagerNonConfig object2) {
        Object object3;
        List<Fragment> list;
        int n;
        int n2;
        Object object4;
        if (object == null) {
            return;
        }
        FragmentManagerState fragmentManagerState = (FragmentManagerState)object;
        if (fragmentManagerState.mActive == null) {
            return;
        }
        object = null;
        if (object2 != null) {
            object3 = ((FragmentManagerNonConfig)object2).getFragments();
            list = ((FragmentManagerNonConfig)object2).getChildNonConfigs();
            n2 = object3 != null ? object3.size() : 0;
            n = 0;
            do {
                int n3;
                object = list;
                if (n >= n2) break;
                object = object3.get(n);
                if (DEBUG) {
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("restoreAllState: re-attaching retained ");
                    ((StringBuilder)object4).append(object);
                    Log.v(TAG, ((StringBuilder)object4).toString());
                }
                for (n3 = 0; n3 < fragmentManagerState.mActive.length && fragmentManagerState.mActive[n3].mIndex != ((Fragment)object).mIndex; ++n3) {
                }
                if (n3 == fragmentManagerState.mActive.length) {
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("Could not find active fragment with index ");
                    ((StringBuilder)object4).append(((Fragment)object).mIndex);
                    this.throwException(new IllegalStateException(((StringBuilder)object4).toString()));
                }
                object4 = fragmentManagerState.mActive[n3];
                ((FragmentState)object4).mInstance = object;
                ((Fragment)object).mSavedViewState = null;
                ((Fragment)object).mBackStackNesting = 0;
                ((Fragment)object).mInLayout = false;
                ((Fragment)object).mAdded = false;
                ((Fragment)object).mTarget = null;
                if (((FragmentState)object4).mSavedFragmentState != null) {
                    ((FragmentState)object4).mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                    ((Fragment)object).mSavedViewState = ((FragmentState)object4).mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                    ((Fragment)object).mSavedFragmentState = ((FragmentState)object4).mSavedFragmentState;
                }
                ++n;
            } while (true);
        }
        this.mActive = new SparseArray(fragmentManagerState.mActive.length);
        for (n2 = 0; n2 < fragmentManagerState.mActive.length; ++n2) {
            object4 = fragmentManagerState.mActive[n2];
            if (object4 == null) continue;
            list = object3 = null;
            if (object != null) {
                list = object3;
                if (n2 < object.size()) {
                    list = (FragmentManagerNonConfig)object.get(n2);
                }
            }
            object3 = ((FragmentState)object4).instantiate(this.mHost, this.mContainer, this.mParent, (FragmentManagerNonConfig)((Object)list));
            if (DEBUG) {
                list = new StringBuilder();
                ((StringBuilder)((Object)list)).append("restoreAllState: active #");
                ((StringBuilder)((Object)list)).append(n2);
                ((StringBuilder)((Object)list)).append(": ");
                ((StringBuilder)((Object)list)).append(object3);
                Log.v(TAG, ((StringBuilder)((Object)list)).toString());
            }
            this.mActive.put(((Fragment)object3).mIndex, (Fragment)object3);
            ((FragmentState)object4).mInstance = null;
        }
        if (object2 != null) {
            list = ((FragmentManagerNonConfig)object2).getFragments();
            n2 = list != null ? list.size() : 0;
            for (n = 0; n < n2; ++n) {
                object = (Fragment)list.get(n);
                if (((Fragment)object).mTargetIndex < 0) continue;
                ((Fragment)object).mTarget = this.mActive.get(((Fragment)object).mTargetIndex);
                if (((Fragment)object).mTarget != null) continue;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Re-attaching retained fragment ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(" target no longer exists: ");
                ((StringBuilder)object2).append(((Fragment)object).mTargetIndex);
                Log.w(TAG, ((StringBuilder)object2).toString());
                ((Fragment)object).mTarget = null;
            }
        }
        this.mAdded.clear();
        if (fragmentManagerState.mAdded != null) {
            for (n2 = 0; n2 < fragmentManagerState.mAdded.length; ++n2) {
                object = this.mActive.get(fragmentManagerState.mAdded[n2]);
                if (object == null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("No instantiated fragment for index #");
                    ((StringBuilder)object2).append(fragmentManagerState.mAdded[n2]);
                    this.throwException(new IllegalStateException(((StringBuilder)object2).toString()));
                }
                ((Fragment)object).mAdded = true;
                if (DEBUG) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("restoreAllState: added #");
                    ((StringBuilder)object2).append(n2);
                    ((StringBuilder)object2).append(": ");
                    ((StringBuilder)object2).append(object);
                    Log.v(TAG, ((StringBuilder)object2).toString());
                }
                if (this.mAdded.contains(object)) {
                    throw new IllegalStateException("Already added!");
                }
                object2 = this.mAdded;
                synchronized (object2) {
                    this.mAdded.add((Fragment)object);
                    continue;
                }
            }
        }
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList(fragmentManagerState.mBackStack.length);
            for (n2 = 0; n2 < fragmentManagerState.mBackStack.length; ++n2) {
                object = fragmentManagerState.mBackStack[n2].instantiate(this);
                if (DEBUG) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("restoreAllState: back stack #");
                    ((StringBuilder)object2).append(n2);
                    ((StringBuilder)object2).append(" (index ");
                    ((StringBuilder)object2).append(((BackStackRecord)object).mIndex);
                    ((StringBuilder)object2).append("): ");
                    ((StringBuilder)object2).append(object);
                    Log.v(TAG, ((StringBuilder)object2).toString());
                    object2 = new FastPrintWriter(new LogWriter(2, TAG), false, 1024);
                    ((BackStackRecord)object).dump("  ", (PrintWriter)object2, false);
                    ((PrintWriter)object2).flush();
                }
                this.mBackStack.add((BackStackRecord)object);
                if (((BackStackRecord)object).mIndex < 0) continue;
                this.setBackStackIndex(((BackStackRecord)object).mIndex, (BackStackRecord)object);
            }
        } else {
            this.mBackStack = null;
        }
        if (fragmentManagerState.mPrimaryNavActiveIndex >= 0) {
            this.mPrimaryNav = this.mActive.get(fragmentManagerState.mPrimaryNavActiveIndex);
        }
        this.mNextFragmentIndex = fragmentManagerState.mNextFragmentIndex;
    }

    FragmentManagerNonConfig retainNonConfig() {
        FragmentManagerImpl.setRetaining(this.mSavedNonConfig);
        return this.mSavedNonConfig;
    }

    Parcelable saveAllState() {
        this.forcePostponedTransactions();
        this.endAnimatingAwayFragments();
        this.execPendingActions();
        this.mStateSaved = true;
        this.mSavedNonConfig = null;
        Object object = this.mActive;
        if (object != null && ((SparseArray)object).size() > 0) {
            int n;
            Object object2;
            Object object3;
            int n2 = this.mActive.size();
            FragmentState[] arrfragmentState = new FragmentState[n2];
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                object = this.mActive.valueAt(n);
                if (object == null) continue;
                if (((Fragment)object).mIndex < 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Failure saving state: active ");
                    ((StringBuilder)object2).append(object);
                    ((StringBuilder)object2).append(" has cleared index: ");
                    ((StringBuilder)object2).append(((Fragment)object).mIndex);
                    this.throwException(new IllegalStateException(((StringBuilder)object2).toString()));
                }
                int n4 = 1;
                object2 = new FragmentState((Fragment)object);
                arrfragmentState[n] = object2;
                if (((Fragment)object).mState > 0 && ((FragmentState)object2).mSavedFragmentState == null) {
                    ((FragmentState)object2).mSavedFragmentState = this.saveFragmentBasicState((Fragment)object);
                    if (((Fragment)object).mTarget != null) {
                        if (object.mTarget.mIndex < 0) {
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append("Failure saving state: ");
                            ((StringBuilder)object3).append(object);
                            ((StringBuilder)object3).append(" has target not in fragment manager: ");
                            ((StringBuilder)object3).append(((Fragment)object).mTarget);
                            this.throwException(new IllegalStateException(((StringBuilder)object3).toString()));
                        }
                        if (((FragmentState)object2).mSavedFragmentState == null) {
                            ((FragmentState)object2).mSavedFragmentState = new Bundle();
                        }
                        this.putFragment(((FragmentState)object2).mSavedFragmentState, TARGET_STATE_TAG, ((Fragment)object).mTarget);
                        if (((Fragment)object).mTargetRequestCode != 0) {
                            ((FragmentState)object2).mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, ((Fragment)object).mTargetRequestCode);
                        }
                    }
                } else {
                    ((FragmentState)object2).mSavedFragmentState = ((Fragment)object).mSavedFragmentState;
                }
                n3 = n4;
                if (!DEBUG) continue;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Saved state of ");
                ((StringBuilder)object3).append(object);
                ((StringBuilder)object3).append(": ");
                ((StringBuilder)object3).append(((FragmentState)object2).mSavedFragmentState);
                Log.v(TAG, ((StringBuilder)object3).toString());
                n3 = n4;
            }
            if (n3 == 0) {
                if (DEBUG) {
                    Log.v(TAG, "saveAllState: no fragments!");
                }
                return null;
            }
            object = null;
            object3 = null;
            n3 = this.mAdded.size();
            if (n3 > 0) {
                object2 = new int[n3];
                n = 0;
                do {
                    object = object2;
                    if (n >= n3) break;
                    object2[n] = this.mAdded.get((int)n).mIndex;
                    if (object2[n] < 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Failure saving state: active ");
                        ((StringBuilder)object).append(this.mAdded.get(n));
                        ((StringBuilder)object).append(" has cleared index: ");
                        ((StringBuilder)object).append((int)object2[n]);
                        this.throwException(new IllegalStateException(((StringBuilder)object).toString()));
                    }
                    if (DEBUG) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("saveAllState: adding fragment #");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(": ");
                        ((StringBuilder)object).append(this.mAdded.get(n));
                        Log.v(TAG, ((StringBuilder)object).toString());
                    }
                    ++n;
                } while (true);
            }
            ArrayList<BackStackRecord> arrayList = this.mBackStack;
            object2 = object3;
            if (arrayList != null) {
                n3 = arrayList.size();
                object2 = object3;
                if (n3 > 0) {
                    object3 = new BackStackState[n3];
                    n = 0;
                    do {
                        object2 = object3;
                        if (n >= n3) break;
                        object3[n] = new BackStackState(this, this.mBackStack.get(n));
                        if (DEBUG) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("saveAllState: adding back stack #");
                            ((StringBuilder)object2).append(n);
                            ((StringBuilder)object2).append(": ");
                            ((StringBuilder)object2).append(this.mBackStack.get(n));
                            Log.v(TAG, ((StringBuilder)object2).toString());
                        }
                        ++n;
                    } while (true);
                }
            }
            object3 = new FragmentManagerState();
            ((FragmentManagerState)object3).mActive = arrfragmentState;
            ((FragmentManagerState)object3).mAdded = object;
            ((FragmentManagerState)object3).mBackStack = object2;
            ((FragmentManagerState)object3).mNextFragmentIndex = this.mNextFragmentIndex;
            object = this.mPrimaryNav;
            if (object != null) {
                ((FragmentManagerState)object3).mPrimaryNavActiveIndex = ((Fragment)object).mIndex;
            }
            this.saveNonConfig();
            return object3;
        }
        return null;
    }

    Bundle saveFragmentBasicState(Fragment fragment) {
        Bundle bundle = null;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        fragment.performSaveInstanceState(this.mStateBundle);
        this.dispatchOnFragmentSaveInstanceState(fragment, this.mStateBundle, false);
        if (!this.mStateBundle.isEmpty()) {
            bundle = this.mStateBundle;
            this.mStateBundle = null;
        }
        if (fragment.mView != null) {
            this.saveFragmentViewState(fragment);
        }
        Bundle bundle2 = bundle;
        if (fragment.mSavedViewState != null) {
            bundle2 = bundle;
            if (bundle == null) {
                bundle2 = new Bundle();
            }
            bundle2.putSparseParcelableArray(VIEW_STATE_TAG, fragment.mSavedViewState);
        }
        bundle = bundle2;
        if (!fragment.mUserVisibleHint) {
            bundle = bundle2;
            if (bundle2 == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, fragment.mUserVisibleHint);
        }
        return bundle;
    }

    @Override
    public Fragment.SavedState saveFragmentInstanceState(Fragment object) {
        StringBuilder stringBuilder;
        if (((Fragment)object).mIndex < 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(object);
            stringBuilder.append(" is not currently in the FragmentManager");
            this.throwException(new IllegalStateException(stringBuilder.toString()));
        }
        int n = ((Fragment)object).mState;
        stringBuilder = null;
        if (n > 0) {
            Bundle bundle = this.saveFragmentBasicState((Fragment)object);
            object = stringBuilder;
            if (bundle != null) {
                object = new Fragment.SavedState(bundle);
            }
            return object;
        }
        return null;
    }

    void saveFragmentViewState(Fragment fragment) {
        if (fragment.mView == null) {
            return;
        }
        SparseArray<Parcelable> sparseArray = this.mStateArray;
        if (sparseArray == null) {
            this.mStateArray = new SparseArray();
        } else {
            sparseArray.clear();
        }
        fragment.mView.saveHierarchyState(this.mStateArray);
        if (this.mStateArray.size() > 0) {
            fragment.mSavedViewState = this.mStateArray;
            this.mStateArray = null;
        }
    }

    void saveNonConfig() {
        ArrayList<Object> arrayList = null;
        Serializable serializable = null;
        ArrayList<Object> arrayList2 = null;
        ArrayList<Object> arrayList3 = null;
        if (this.mActive != null) {
            int n = 0;
            do {
                arrayList = serializable;
                arrayList2 = arrayList3;
                if (n >= this.mActive.size()) break;
                Object object = this.mActive.valueAt(n);
                ArrayList<Object> arrayList4 = serializable;
                arrayList2 = arrayList3;
                if (object != null) {
                    int n2;
                    arrayList = serializable;
                    if (((Fragment)object).mRetainInstance) {
                        arrayList2 = serializable;
                        if (serializable == null) {
                            arrayList2 = new ArrayList<Object>();
                        }
                        arrayList2.add(object);
                        n2 = ((Fragment)object).mTarget != null ? object.mTarget.mIndex : -1;
                        ((Fragment)object).mTargetIndex = n2;
                        arrayList = arrayList2;
                        if (DEBUG) {
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("retainNonConfig: keeping retained ");
                            ((StringBuilder)serializable).append(object);
                            Log.v(TAG, ((StringBuilder)serializable).toString());
                            arrayList = arrayList2;
                        }
                    }
                    if (((Fragment)object).mChildFragmentManager != null) {
                        ((Fragment)object).mChildFragmentManager.saveNonConfig();
                        object = object.mChildFragmentManager.mSavedNonConfig;
                    } else {
                        object = ((Fragment)object).mChildNonConfig;
                    }
                    serializable = arrayList3;
                    if (arrayList3 == null) {
                        serializable = arrayList3;
                        if (object != null) {
                            arrayList3 = new ArrayList(this.mActive.size());
                            n2 = 0;
                            do {
                                serializable = arrayList3;
                                if (n2 >= n) break;
                                arrayList3.add(null);
                                ++n2;
                            } while (true);
                        }
                    }
                    arrayList4 = arrayList;
                    arrayList2 = serializable;
                    if (serializable != null) {
                        ((ArrayList)serializable).add(object);
                        arrayList2 = serializable;
                        arrayList4 = arrayList;
                    }
                }
                ++n;
                serializable = arrayList4;
                arrayList3 = arrayList2;
            } while (true);
        }
        this.mSavedNonConfig = arrayList == null && arrayList2 == null ? null : new FragmentManagerNonConfig(arrayList, arrayList2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setBackStackIndex(int n, BackStackRecord backStackRecord) {
        synchronized (this) {
            Serializable serializable;
            int n2;
            if (this.mBackStackIndices == null) {
                serializable = new ArrayList();
                this.mBackStackIndices = serializable;
            }
            if (n < n2) {
                if (DEBUG) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Setting back stack index ");
                    ((StringBuilder)serializable).append(n);
                    ((StringBuilder)serializable).append(" to ");
                    ((StringBuilder)serializable).append(backStackRecord);
                    Log.v(TAG, ((StringBuilder)serializable).toString());
                }
                this.mBackStackIndices.set(n, backStackRecord);
            } else {
                for (int i = n2 = this.mBackStackIndices.size(); i < n; ++i) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        serializable = new ArrayList();
                        this.mAvailBackStackIndices = serializable;
                    }
                    if (DEBUG) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Adding available back stack index ");
                        ((StringBuilder)serializable).append(i);
                        Log.v(TAG, ((StringBuilder)serializable).toString());
                    }
                    this.mAvailBackStackIndices.add(i);
                }
                if (DEBUG) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Adding back stack index ");
                    ((StringBuilder)serializable).append(n);
                    ((StringBuilder)serializable).append(" with ");
                    ((StringBuilder)serializable).append(backStackRecord);
                    Log.v(TAG, ((StringBuilder)serializable).toString());
                }
                this.mBackStackIndices.add(backStackRecord);
            }
            return;
        }
    }

    public void setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment != null && (this.mActive.get(fragment.mIndex) != fragment || fragment.mHost != null && fragment.getFragmentManager() != this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not an active fragment of FragmentManager ");
            stringBuilder.append(this);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.mPrimaryNav = fragment;
    }

    public void showFragment(Fragment fragment) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("show: ");
            stringBuilder.append(fragment);
            Log.v(TAG, stringBuilder.toString());
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged ^= true;
        }
    }

    void startPendingDeferredFragments() {
        if (this.mActive == null) {
            return;
        }
        for (int i = 0; i < this.mActive.size(); ++i) {
            Fragment fragment = this.mActive.valueAt(i);
            if (fragment == null) continue;
            this.performPendingDeferredStart(fragment);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("FragmentManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        Fragment fragment = this.mParent;
        if (fragment != null) {
            DebugUtils.buildShortClassTag(fragment, stringBuilder);
        } else {
            DebugUtils.buildShortClassTag(this.mHost, stringBuilder);
        }
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> copyOnWriteArrayList = this.mLifecycleCallbacks;
        synchronized (copyOnWriteArrayList) {
            int n = this.mLifecycleCallbacks.size();
            for (int i = 0; i < n; ++i) {
                if (this.mLifecycleCallbacks.get((int)i).first != fragmentLifecycleCallbacks) continue;
                this.mLifecycleCallbacks.remove(i);
                break;
            }
            return;
        }
    }

    static class AnimateOnHWLayerIfNeededListener
    implements Animator.AnimatorListener {
        private boolean mShouldRunOnHWLayer = false;
        private View mView;

        public AnimateOnHWLayerIfNeededListener(View view) {
            if (view == null) {
                return;
            }
            this.mView = view;
        }

        @Override
        public void onAnimationCancel(Animator animator2) {
        }

        @Override
        public void onAnimationEnd(Animator animator2) {
            if (this.mShouldRunOnHWLayer) {
                this.mView.setLayerType(0, null);
            }
            this.mView = null;
            animator2.removeListener(this);
        }

        @Override
        public void onAnimationRepeat(Animator animator2) {
        }

        @Override
        public void onAnimationStart(Animator animator2) {
            this.mShouldRunOnHWLayer = FragmentManagerImpl.shouldRunOnHWLayer(this.mView, animator2);
            if (this.mShouldRunOnHWLayer) {
                this.mView.setLayerType(2, null);
            }
        }
    }

    static interface OpGenerator {
        public boolean generateOps(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2);
    }

    private class PopBackStackState
    implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        public PopBackStackState(String string2, int n, int n2) {
            this.mName = string2;
            this.mId = n;
            this.mFlags = n2;
        }

        @Override
        public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
            FragmentManagerImpl fragmentManagerImpl;
            if (FragmentManagerImpl.this.mPrimaryNav != null && this.mId < 0 && this.mName == null && (fragmentManagerImpl = FragmentManagerImpl.this.mPrimaryNav.mChildFragmentManager) != null && ((FragmentManager)fragmentManagerImpl).popBackStackImmediate()) {
                return false;
            }
            return FragmentManagerImpl.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
        }
    }

    static class StartEnterTransitionListener
    implements Fragment.OnStartEnterTransitionListener {
        private final boolean mIsBack;
        private int mNumPostponed;
        private final BackStackRecord mRecord;

        public StartEnterTransitionListener(BackStackRecord backStackRecord, boolean bl) {
            this.mIsBack = bl;
            this.mRecord = backStackRecord;
        }

        public void cancelTransaction() {
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
        }

        public void completeTransaction() {
            Object object;
            int n = this.mNumPostponed;
            boolean bl = false;
            n = n > 0 ? 1 : 0;
            FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
            int n2 = fragmentManagerImpl.mAdded.size();
            for (int i = 0; i < n2; ++i) {
                object = fragmentManagerImpl.mAdded.get(i);
                ((Fragment)object).setOnStartEnterTransitionListener(null);
                if (n == 0 || !((Fragment)object).isPostponed()) continue;
                ((Fragment)object).startPostponedEnterTransition();
            }
            fragmentManagerImpl = this.mRecord.mManager;
            object = this.mRecord;
            boolean bl2 = this.mIsBack;
            if (n == 0) {
                bl = true;
            }
            fragmentManagerImpl.completeExecute((BackStackRecord)object, bl2, bl, true);
        }

        public boolean isReady() {
            boolean bl = this.mNumPostponed == 0;
            return bl;
        }

        @Override
        public void onStartEnterTransition() {
            --this.mNumPostponed;
            if (this.mNumPostponed != 0) {
                return;
            }
            this.mRecord.mManager.scheduleCommit();
        }

        @Override
        public void startListening() {
            ++this.mNumPostponed;
        }
    }

}

