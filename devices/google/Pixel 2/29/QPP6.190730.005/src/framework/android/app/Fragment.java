/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.animation.Animator;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.FragmentContainer;
import android.app.FragmentHostCallback;
import android.app.FragmentManager;
import android.app.FragmentManagerImpl;
import android.app.FragmentManagerNonConfig;
import android.app.LoaderManager;
import android.app.LoaderManagerImpl;
import android.app.SharedElementCallback;
import android.app._$$Lambda$Fragment$m7ODa2MK0_rf4XCEL5JOn14G0h8;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.util.SparseArray;
import android.util.SuperNotCalledException;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.R;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Deprecated
public class Fragment
implements ComponentCallbacks2,
View.OnCreateContextMenuListener {
    static final int ACTIVITY_CREATED = 2;
    static final int CREATED = 1;
    static final int INITIALIZING = 0;
    static final int INVALID_STATE = -1;
    static final int RESUMED = 5;
    static final int STARTED = 4;
    static final int STOPPED = 3;
    private static final Transition USE_DEFAULT_TRANSITION;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static final ArrayMap<String, Class<?>> sClassMap;
    @UnsupportedAppUsage
    boolean mAdded;
    AnimationInfo mAnimationInfo;
    Bundle mArguments;
    int mBackStackNesting;
    boolean mCalled;
    boolean mCheckedForLoaderManager;
    @UnsupportedAppUsage
    FragmentManagerImpl mChildFragmentManager;
    FragmentManagerNonConfig mChildNonConfig;
    ViewGroup mContainer;
    int mContainerId;
    boolean mDeferStart;
    boolean mDetached;
    @UnsupportedAppUsage
    int mFragmentId;
    @UnsupportedAppUsage
    FragmentManagerImpl mFragmentManager;
    boolean mFromLayout;
    boolean mHasMenu;
    boolean mHidden;
    boolean mHiddenChanged;
    @UnsupportedAppUsage
    FragmentHostCallback mHost;
    boolean mInLayout;
    @UnsupportedAppUsage
    int mIndex = -1;
    boolean mIsCreated;
    boolean mIsNewlyAdded;
    LayoutInflater mLayoutInflater;
    LoaderManagerImpl mLoaderManager;
    @UnsupportedAppUsage
    boolean mLoadersStarted;
    boolean mMenuVisible = true;
    Fragment mParentFragment;
    boolean mPerformedCreateView;
    boolean mRemoving;
    boolean mRestored;
    boolean mRetainInstance;
    boolean mRetaining;
    @UnsupportedAppUsage
    Bundle mSavedFragmentState;
    SparseArray<Parcelable> mSavedViewState;
    int mState = 0;
    String mTag;
    Fragment mTarget;
    int mTargetIndex = -1;
    int mTargetRequestCode;
    boolean mUserVisibleHint = true;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    View mView;
    @UnsupportedAppUsage
    String mWho;

    static {
        sClassMap = new ArrayMap();
        USE_DEFAULT_TRANSITION = new TransitionSet();
    }

    static /* synthetic */ Transition access$800() {
        return USE_DEFAULT_TRANSITION;
    }

    private void callStartTransitionListener() {
        Object object = this.mAnimationInfo;
        if (object == null) {
            object = null;
        } else {
            ((AnimationInfo)object).mEnterTransitionPostponed = false;
            object = ((AnimationInfo)object).mStartEnterTransitionListener;
            this.mAnimationInfo.mStartEnterTransitionListener = null;
        }
        if (object != null) {
            object.onStartEnterTransition();
        }
    }

    private AnimationInfo ensureAnimationInfo() {
        if (this.mAnimationInfo == null) {
            this.mAnimationInfo = new AnimationInfo();
        }
        return this.mAnimationInfo;
    }

    public static Fragment instantiate(Context context, String string2) {
        return Fragment.instantiate(context, string2, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Fragment instantiate(Context object, String string2, Bundle object2) {
        try {
            Class<?> class_ = sClassMap.get(string2);
            Class<?> class_2 = class_;
            if (class_ == null) {
                class_2 = ((Context)object).getClassLoader().loadClass(string2);
                if (!Fragment.class.isAssignableFrom(class_2)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Trying to instantiate a class ");
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append(" that is not a Fragment");
                    class_2 = ((StringBuilder)object2).toString();
                    object2 = new ClassCastException();
                    object = new InstantiationException((String)((Object)class_2), (Exception)object2);
                    throw object;
                }
                sClassMap.put(string2, class_2);
            }
            object = (Fragment)class_2.getConstructor(new Class[0]).newInstance(new Object[0]);
            if (object2 != null) {
                ((Bundle)object2).setClassLoader(object.getClass().getClassLoader());
                ((Fragment)object).setArguments((Bundle)object2);
            }
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to instantiate fragment ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": calling Fragment constructor caused an exception");
            throw new InstantiationException(((StringBuilder)object).toString(), invocationTargetException);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to instantiate fragment ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": could not find Fragment constructor");
            throw new InstantiationException(((StringBuilder)object).toString(), noSuchMethodException);
        }
        catch (IllegalAccessException illegalAccessException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to instantiate fragment ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": make sure class name exists, is public, and has an empty constructor that is public");
            throw new InstantiationException(((StringBuilder)object).toString(), illegalAccessException);
        }
        catch (java.lang.InstantiationException instantiationException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to instantiate fragment ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": make sure class name exists, is public, and has an empty constructor that is public");
            throw new InstantiationException(((StringBuilder)object).toString(), instantiationException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unable to instantiate fragment ");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(": make sure class name exists, is public, and has an empty constructor that is public");
            throw new InstantiationException(((StringBuilder)object2).toString(), classNotFoundException);
        }
    }

    public static /* synthetic */ void lambda$m7ODa2MK0_rf4XCEL5JOn14G0h8(Fragment fragment) {
        fragment.callStartTransitionListener();
    }

    private static Transition loadTransition(Context object, TypedArray object2, Transition transition2, Transition transition3, int n) {
        if (transition2 != transition3) {
            return transition2;
        }
        n = ((TypedArray)object2).getResourceId(n, 0);
        object2 = transition3;
        if (n != 0) {
            object2 = transition3;
            if (n != 17760256) {
                object2 = object = TransitionInflater.from((Context)object).inflateTransition(n);
                if (object instanceof TransitionSet) {
                    object2 = object;
                    if (((TransitionSet)object).getTransitionCount() == 0) {
                        object2 = null;
                    }
                }
            }
        }
        return object2;
    }

    private boolean shouldChangeTransition(Transition transition2, Transition transition3) {
        boolean bl = true;
        if (transition2 == transition3) {
            if (this.mAnimationInfo == null) {
                bl = false;
            }
            return bl;
        }
        return true;
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        Object object;
        Object object2;
        printWriter.print(string2);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.mFragmentId));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.mContainerId));
        printWriter.print(" mTag=");
        printWriter.println(this.mTag);
        printWriter.print(string2);
        printWriter.print("mState=");
        printWriter.print(this.mState);
        printWriter.print(" mIndex=");
        printWriter.print(this.mIndex);
        printWriter.print(" mWho=");
        printWriter.print(this.mWho);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.mBackStackNesting);
        printWriter.print(string2);
        printWriter.print("mAdded=");
        printWriter.print(this.mAdded);
        printWriter.print(" mRemoving=");
        printWriter.print(this.mRemoving);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.mFromLayout);
        printWriter.print(" mInLayout=");
        printWriter.println(this.mInLayout);
        printWriter.print(string2);
        printWriter.print("mHidden=");
        printWriter.print(this.mHidden);
        printWriter.print(" mDetached=");
        printWriter.print(this.mDetached);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.mMenuVisible);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.mHasMenu);
        printWriter.print(string2);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.mRetainInstance);
        printWriter.print(" mRetaining=");
        printWriter.print(this.mRetaining);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.mUserVisibleHint);
        if (this.mFragmentManager != null) {
            printWriter.print(string2);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.mFragmentManager);
        }
        if (this.mHost != null) {
            printWriter.print(string2);
            printWriter.print("mHost=");
            printWriter.println(this.mHost);
        }
        if (this.mParentFragment != null) {
            printWriter.print(string2);
            printWriter.print("mParentFragment=");
            printWriter.println(this.mParentFragment);
        }
        if (this.mArguments != null) {
            printWriter.print(string2);
            printWriter.print("mArguments=");
            printWriter.println(this.mArguments);
        }
        if (this.mSavedFragmentState != null) {
            printWriter.print(string2);
            printWriter.print("mSavedFragmentState=");
            printWriter.println(this.mSavedFragmentState);
        }
        if (this.mSavedViewState != null) {
            printWriter.print(string2);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.mSavedViewState);
        }
        if (this.mTarget != null) {
            printWriter.print(string2);
            printWriter.print("mTarget=");
            printWriter.print(this.mTarget);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.mTargetRequestCode);
        }
        if (this.getNextAnim() != 0) {
            printWriter.print(string2);
            printWriter.print("mNextAnim=");
            printWriter.println(this.getNextAnim());
        }
        if (this.mContainer != null) {
            printWriter.print(string2);
            printWriter.print("mContainer=");
            printWriter.println(this.mContainer);
        }
        if (this.mView != null) {
            printWriter.print(string2);
            printWriter.print("mView=");
            printWriter.println(this.mView);
        }
        if (this.getAnimatingAway() != null) {
            printWriter.print(string2);
            printWriter.print("mAnimatingAway=");
            printWriter.println(this.getAnimatingAway());
            printWriter.print(string2);
            printWriter.print("mStateAfterAnimating=");
            printWriter.println(this.getStateAfterAnimating());
        }
        if (this.mLoaderManager != null) {
            printWriter.print(string2);
            printWriter.println("Loader Manager:");
            object2 = this.mLoaderManager;
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  ");
            ((LoaderManagerImpl)object2).dump(((StringBuilder)object).toString(), fileDescriptor, printWriter, arrstring);
        }
        if (this.mChildFragmentManager != null) {
            printWriter.print(string2);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Child ");
            ((StringBuilder)object2).append(this.mChildFragmentManager);
            ((StringBuilder)object2).append(":");
            printWriter.println(((StringBuilder)object2).toString());
            object = this.mChildFragmentManager;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  ");
            ((FragmentManagerImpl)object).dump(((StringBuilder)object2).toString(), fileDescriptor, printWriter, arrstring);
        }
    }

    public final boolean equals(Object object) {
        return super.equals(object);
    }

    Fragment findFragmentByWho(String string2) {
        if (string2.equals(this.mWho)) {
            return this;
        }
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            return fragmentManagerImpl.findFragmentByWho(string2);
        }
        return null;
    }

    public final Activity getActivity() {
        Object object = this.mHost;
        object = object == null ? null : ((FragmentHostCallback)object).getActivity();
        return object;
    }

    public boolean getAllowEnterTransitionOverlap() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        boolean bl = animationInfo != null && animationInfo.mAllowEnterTransitionOverlap != null ? this.mAnimationInfo.mAllowEnterTransitionOverlap : true;
        return bl;
    }

    public boolean getAllowReturnTransitionOverlap() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        boolean bl = animationInfo != null && animationInfo.mAllowReturnTransitionOverlap != null ? this.mAnimationInfo.mAllowReturnTransitionOverlap : true;
        return bl;
    }

    Animator getAnimatingAway() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        return animationInfo.mAnimatingAway;
    }

    public final Bundle getArguments() {
        return this.mArguments;
    }

    public final FragmentManager getChildFragmentManager() {
        if (this.mChildFragmentManager == null) {
            this.instantiateChildFragmentManager();
            int n = this.mState;
            if (n >= 5) {
                this.mChildFragmentManager.dispatchResume();
            } else if (n >= 4) {
                this.mChildFragmentManager.dispatchStart();
            } else if (n >= 2) {
                this.mChildFragmentManager.dispatchActivityCreated();
            } else if (n >= 1) {
                this.mChildFragmentManager.dispatchCreate();
            }
        }
        return this.mChildFragmentManager;
    }

    public Context getContext() {
        Object object = this.mHost;
        object = object == null ? null : ((FragmentHostCallback)object).getContext();
        return object;
    }

    public Transition getEnterTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        return animationInfo.mEnterTransition;
    }

    SharedElementCallback getEnterTransitionCallback() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return SharedElementCallback.NULL_CALLBACK;
        }
        return animationInfo.mEnterTransitionCallback;
    }

    public Transition getExitTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        return animationInfo.mExitTransition;
    }

    SharedElementCallback getExitTransitionCallback() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return SharedElementCallback.NULL_CALLBACK;
        }
        return animationInfo.mExitTransitionCallback;
    }

    public final FragmentManager getFragmentManager() {
        return this.mFragmentManager;
    }

    public final Object getHost() {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        fragmentHostCallback = fragmentHostCallback == null ? null : fragmentHostCallback.onGetHost();
        return fragmentHostCallback;
    }

    public final int getId() {
        return this.mFragmentId;
    }

    public final LayoutInflater getLayoutInflater() {
        LayoutInflater layoutInflater = this.mLayoutInflater;
        if (layoutInflater == null) {
            return this.performGetLayoutInflater(null);
        }
        return layoutInflater;
    }

    @Deprecated
    public LoaderManager getLoaderManager() {
        Object object = this.mLoaderManager;
        if (object != null) {
            return object;
        }
        object = this.mHost;
        if (object != null) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = ((FragmentHostCallback)object).getLoaderManager(this.mWho, this.mLoadersStarted, true);
            return this.mLoaderManager;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    int getNextAnim() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return 0;
        }
        return animationInfo.mNextAnim;
    }

    int getNextTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return 0;
        }
        return animationInfo.mNextTransition;
    }

    int getNextTransitionStyle() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return 0;
        }
        return animationInfo.mNextTransitionStyle;
    }

    public final Fragment getParentFragment() {
        return this.mParentFragment;
    }

    public Transition getReenterTransition() {
        Object object = this.mAnimationInfo;
        if (object == null) {
            return null;
        }
        object = ((AnimationInfo)object).mReenterTransition == USE_DEFAULT_TRANSITION ? this.getExitTransition() : this.mAnimationInfo.mReenterTransition;
        return object;
    }

    public final Resources getResources() {
        Object object = this.mHost;
        if (object != null) {
            return ((FragmentHostCallback)object).getContext().getResources();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final boolean getRetainInstance() {
        return this.mRetainInstance;
    }

    public Transition getReturnTransition() {
        Object object = this.mAnimationInfo;
        if (object == null) {
            return null;
        }
        object = ((AnimationInfo)object).mReturnTransition == USE_DEFAULT_TRANSITION ? this.getEnterTransition() : this.mAnimationInfo.mReturnTransition;
        return object;
    }

    public Transition getSharedElementEnterTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        return animationInfo.mSharedElementEnterTransition;
    }

    public Transition getSharedElementReturnTransition() {
        Object object = this.mAnimationInfo;
        if (object == null) {
            return null;
        }
        object = ((AnimationInfo)object).mSharedElementReturnTransition == USE_DEFAULT_TRANSITION ? this.getSharedElementEnterTransition() : this.mAnimationInfo.mSharedElementReturnTransition;
        return object;
    }

    int getStateAfterAnimating() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return 0;
        }
        return animationInfo.mStateAfterAnimating;
    }

    public final String getString(int n) {
        return this.getResources().getString(n);
    }

    public final String getString(int n, Object ... arrobject) {
        return this.getResources().getString(n, arrobject);
    }

    public final String getTag() {
        return this.mTag;
    }

    public final Fragment getTargetFragment() {
        return this.mTarget;
    }

    public final int getTargetRequestCode() {
        return this.mTargetRequestCode;
    }

    public final CharSequence getText(int n) {
        return this.getResources().getText(n);
    }

    public boolean getUserVisibleHint() {
        return this.mUserVisibleHint;
    }

    public View getView() {
        return this.mView;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    void initState() {
        this.mIndex = -1;
        this.mWho = null;
        this.mAdded = false;
        this.mRemoving = false;
        this.mFromLayout = false;
        this.mInLayout = false;
        this.mRestored = false;
        this.mBackStackNesting = 0;
        this.mFragmentManager = null;
        this.mChildFragmentManager = null;
        this.mHost = null;
        this.mFragmentId = 0;
        this.mContainerId = 0;
        this.mTag = null;
        this.mHidden = false;
        this.mDetached = false;
        this.mRetaining = false;
        this.mLoaderManager = null;
        this.mLoadersStarted = false;
        this.mCheckedForLoaderManager = false;
    }

    void instantiateChildFragmentManager() {
        this.mChildFragmentManager = new FragmentManagerImpl();
        this.mChildFragmentManager.attachController(this.mHost, new FragmentContainer(){

            @Override
            public <T extends View> T onFindViewById(int n) {
                if (Fragment.this.mView != null) {
                    return Fragment.this.mView.findViewById(n);
                }
                throw new IllegalStateException("Fragment does not have a view");
            }

            @Override
            public boolean onHasView() {
                boolean bl = Fragment.this.mView != null;
                return bl;
            }
        }, this);
    }

    public final boolean isAdded() {
        boolean bl = this.mHost != null && this.mAdded;
        return bl;
    }

    public final boolean isDetached() {
        return this.mDetached;
    }

    public final boolean isHidden() {
        return this.mHidden;
    }

    boolean isHideReplaced() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return false;
        }
        return animationInfo.mIsHideReplaced;
    }

    final boolean isInBackStack() {
        boolean bl = this.mBackStackNesting > 0;
        return bl;
    }

    public final boolean isInLayout() {
        return this.mInLayout;
    }

    boolean isPostponed() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return false;
        }
        return animationInfo.mEnterTransitionPostponed;
    }

    public final boolean isRemoving() {
        return this.mRemoving;
    }

    public final boolean isResumed() {
        boolean bl = this.mState >= 5;
        return bl;
    }

    public final boolean isStateSaved() {
        FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
        if (fragmentManagerImpl == null) {
            return false;
        }
        return fragmentManagerImpl.isStateSaved();
    }

    public final boolean isVisible() {
        View view;
        boolean bl = this.isAdded() && !this.isHidden() && (view = this.mView) != null && view.getWindowToken() != null && this.mView.getVisibility() == 0;
        return bl;
    }

    void noteStateNotSaved() {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.noteStateNotSaved();
        }
    }

    public void onActivityCreated(Bundle bundle) {
        this.mCalled = true;
    }

    public void onActivityResult(int n, int n2, Intent intent) {
    }

    @Deprecated
    public void onAttach(Activity activity) {
        this.mCalled = true;
    }

    public void onAttach(Context object) {
        this.mCalled = true;
        object = this.mHost;
        object = object == null ? null : ((FragmentHostCallback)object).getActivity();
        if (object != null) {
            this.mCalled = false;
            this.onAttach((Activity)object);
        }
    }

    public void onAttachFragment(Fragment fragment) {
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onCreate(Bundle object) {
        this.mCalled = true;
        Context context = this.getContext();
        int n = context != null ? context.getApplicationInfo().targetSdkVersion : 0;
        if (n >= 24) {
            this.restoreChildFragmentState((Bundle)object, true);
            object = this.mChildFragmentManager;
            if (object != null && !((FragmentManagerImpl)object).isStateAtLeast(1)) {
                this.mChildFragmentManager.dispatchCreate();
            }
        }
    }

    public Animator onCreateAnimator(int n, boolean bl, int n2) {
        return null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.getActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return null;
    }

    public void onDestroy() {
        LoaderManagerImpl loaderManagerImpl;
        this.mCalled = true;
        if (!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
        }
        if ((loaderManagerImpl = this.mLoaderManager) != null) {
            loaderManagerImpl.doDestroy();
        }
    }

    public void onDestroyOptionsMenu() {
    }

    public void onDestroyView() {
        this.mCalled = true;
    }

    public void onDetach() {
        this.mCalled = true;
    }

    public LayoutInflater onGetLayoutInflater(Bundle object) {
        object = this.mHost;
        if (object != null) {
            object = ((FragmentHostCallback)object).onGetLayoutInflater();
            if (this.mHost.onUseFragmentManagerInflaterFactory()) {
                this.getChildFragmentManager();
                ((LayoutInflater)object).setPrivateFactory(this.mChildFragmentManager.getLayoutInflaterFactory());
            }
            return object;
        }
        throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
    }

    public void onHiddenChanged(boolean bl) {
    }

    @Deprecated
    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
    }

    public void onInflate(Context object, AttributeSet attributeSet, Bundle bundle) {
        boolean bl;
        boolean bl2;
        this.onInflate(attributeSet, bundle);
        this.mCalled = true;
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.Fragment);
        Transition transition2 = this.getEnterTransition();
        Object var6_6 = null;
        this.setEnterTransition(Fragment.loadTransition((Context)object, typedArray, transition2, null, 4));
        this.setReturnTransition(Fragment.loadTransition((Context)object, typedArray, this.getReturnTransition(), USE_DEFAULT_TRANSITION, 6));
        this.setExitTransition(Fragment.loadTransition((Context)object, typedArray, this.getExitTransition(), null, 3));
        this.setReenterTransition(Fragment.loadTransition((Context)object, typedArray, this.getReenterTransition(), USE_DEFAULT_TRANSITION, 8));
        this.setSharedElementEnterTransition(Fragment.loadTransition((Context)object, typedArray, this.getSharedElementEnterTransition(), null, 5));
        this.setSharedElementReturnTransition(Fragment.loadTransition((Context)object, typedArray, this.getSharedElementReturnTransition(), USE_DEFAULT_TRANSITION, 7));
        object = this.mAnimationInfo;
        if (object == null) {
            bl = false;
            bl2 = false;
        } else {
            bl = ((AnimationInfo)object).mAllowEnterTransitionOverlap != null;
            bl2 = this.mAnimationInfo.mAllowReturnTransitionOverlap != null;
        }
        if (!bl) {
            this.setAllowEnterTransitionOverlap(typedArray.getBoolean(9, true));
        }
        if (!bl2) {
            this.setAllowReturnTransitionOverlap(typedArray.getBoolean(10, true));
        }
        typedArray.recycle();
        object = this.mHost;
        object = object == null ? var6_6 : ((FragmentHostCallback)object).getActivity();
        if (object != null) {
            this.mCalled = false;
            this.onInflate((Activity)object, attributeSet, bundle);
        }
    }

    @Deprecated
    public void onInflate(AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
    }

    @Override
    public void onLowMemory() {
        this.mCalled = true;
    }

    @Deprecated
    public void onMultiWindowModeChanged(boolean bl) {
    }

    public void onMultiWindowModeChanged(boolean bl, Configuration configuration) {
        this.onMultiWindowModeChanged(bl);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(Menu menu2) {
    }

    public void onPause() {
        this.mCalled = true;
    }

    @Deprecated
    public void onPictureInPictureModeChanged(boolean bl) {
    }

    public void onPictureInPictureModeChanged(boolean bl, Configuration configuration) {
        this.onPictureInPictureModeChanged(bl);
    }

    public void onPrepareOptionsMenu(Menu menu2) {
    }

    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
    }

    public void onResume() {
        this.mCalled = true;
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public void onStart() {
        this.mCalled = true;
        if (!this.mLoadersStarted) {
            this.mLoadersStarted = true;
            if (!this.mCheckedForLoaderManager) {
                this.mCheckedForLoaderManager = true;
                this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
            } else {
                LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
                if (loaderManagerImpl != null) {
                    loaderManagerImpl.doStart();
                }
            }
        }
    }

    public void onStop() {
        this.mCalled = true;
    }

    @Override
    public void onTrimMemory(int n) {
        this.mCalled = true;
    }

    public void onViewCreated(View view, Bundle bundle) {
    }

    public void onViewStateRestored(Bundle bundle) {
        this.mCalled = true;
    }

    void performActivityCreated(Bundle object) {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.noteStateNotSaved();
        }
        this.mState = 2;
        this.mCalled = false;
        this.onActivityCreated((Bundle)object);
        if (this.mCalled) {
            object = this.mChildFragmentManager;
            if (object != null) {
                ((FragmentManagerImpl)object).dispatchActivityCreated();
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onActivityCreated()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    void performConfigurationChanged(Configuration configuration) {
        this.onConfigurationChanged(configuration);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchConfigurationChanged(configuration);
        }
    }

    boolean performContextItemSelected(MenuItem menuItem) {
        if (!this.mHidden) {
            if (this.onContextItemSelected(menuItem)) {
                return true;
            }
            FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
            if (fragmentManagerImpl != null && fragmentManagerImpl.dispatchContextItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    void performCreate(Bundle object) {
        Object object2 = this.mChildFragmentManager;
        if (object2 != null) {
            ((FragmentManagerImpl)object2).noteStateNotSaved();
        }
        this.mState = 1;
        this.mCalled = false;
        this.onCreate((Bundle)object);
        this.mIsCreated = true;
        if (this.mCalled) {
            object2 = this.getContext();
            int n = object2 != null ? object2.getApplicationInfo().targetSdkVersion : 0;
            if (n < 24) {
                this.restoreChildFragmentState((Bundle)object, false);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onCreate()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    boolean performCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        boolean bl = false;
        boolean bl2 = false;
        if (!this.mHidden) {
            boolean bl3 = bl2;
            if (this.mHasMenu) {
                bl3 = bl2;
                if (this.mMenuVisible) {
                    bl3 = true;
                    this.onCreateOptionsMenu(menu2, menuInflater);
                }
            }
            FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
            bl = bl3;
            if (fragmentManagerImpl != null) {
                bl = bl3 | fragmentManagerImpl.dispatchCreateOptionsMenu(menu2, menuInflater);
            }
        }
        return bl;
    }

    View performCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.noteStateNotSaved();
        }
        this.mPerformedCreateView = true;
        return this.onCreateView(layoutInflater, viewGroup, bundle);
    }

    void performDestroy() {
        Object object = this.mChildFragmentManager;
        if (object != null) {
            ((FragmentManagerImpl)object).dispatchDestroy();
        }
        this.mState = 0;
        this.mCalled = false;
        this.mIsCreated = false;
        this.onDestroy();
        if (this.mCalled) {
            this.mChildFragmentManager = null;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onDestroy()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    void performDestroyView() {
        Object object = this.mChildFragmentManager;
        if (object != null) {
            ((FragmentManagerImpl)object).dispatchDestroyView();
        }
        this.mState = 1;
        this.mCalled = false;
        this.onDestroyView();
        if (this.mCalled) {
            object = this.mLoaderManager;
            if (object != null) {
                ((LoaderManagerImpl)object).doReportNextStart();
            }
            this.mPerformedCreateView = false;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onDestroyView()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    void performDetach() {
        this.mCalled = false;
        this.onDetach();
        this.mLayoutInflater = null;
        if (this.mCalled) {
            Object object = this.mChildFragmentManager;
            if (object != null) {
                if (this.mRetaining) {
                    ((FragmentManagerImpl)object).dispatchDestroy();
                    this.mChildFragmentManager = null;
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Child FragmentManager of ");
                    ((StringBuilder)object).append(this);
                    ((StringBuilder)object).append(" was not  destroyed and this fragment is not retaining instance");
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onDetach()");
        throw new SuperNotCalledException(stringBuilder.toString());
    }

    LayoutInflater performGetLayoutInflater(Bundle bundle) {
        this.mLayoutInflater = this.onGetLayoutInflater(bundle);
        return this.mLayoutInflater;
    }

    void performLowMemory() {
        this.onLowMemory();
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchLowMemory();
        }
    }

    @Deprecated
    void performMultiWindowModeChanged(boolean bl) {
        this.onMultiWindowModeChanged(bl);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchMultiWindowModeChanged(bl);
        }
    }

    void performMultiWindowModeChanged(boolean bl, Configuration configuration) {
        this.onMultiWindowModeChanged(bl, configuration);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchMultiWindowModeChanged(bl, configuration);
        }
    }

    boolean performOptionsItemSelected(MenuItem menuItem) {
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible && this.onOptionsItemSelected(menuItem)) {
                return true;
            }
            FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
            if (fragmentManagerImpl != null && fragmentManagerImpl.dispatchOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    void performOptionsMenuClosed(Menu menu2) {
        if (!this.mHidden) {
            FragmentManagerImpl fragmentManagerImpl;
            if (this.mHasMenu && this.mMenuVisible) {
                this.onOptionsMenuClosed(menu2);
            }
            if ((fragmentManagerImpl = this.mChildFragmentManager) != null) {
                fragmentManagerImpl.dispatchOptionsMenuClosed(menu2);
            }
        }
    }

    void performPause() {
        Object object = this.mChildFragmentManager;
        if (object != null) {
            ((FragmentManagerImpl)object).dispatchPause();
        }
        this.mState = 4;
        this.mCalled = false;
        this.onPause();
        if (this.mCalled) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onPause()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    @Deprecated
    void performPictureInPictureModeChanged(boolean bl) {
        this.onPictureInPictureModeChanged(bl);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchPictureInPictureModeChanged(bl);
        }
    }

    void performPictureInPictureModeChanged(boolean bl, Configuration configuration) {
        this.onPictureInPictureModeChanged(bl, configuration);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchPictureInPictureModeChanged(bl, configuration);
        }
    }

    boolean performPrepareOptionsMenu(Menu menu2) {
        boolean bl = false;
        boolean bl2 = false;
        if (!this.mHidden) {
            boolean bl3 = bl2;
            if (this.mHasMenu) {
                bl3 = bl2;
                if (this.mMenuVisible) {
                    bl3 = true;
                    this.onPrepareOptionsMenu(menu2);
                }
            }
            FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
            bl = bl3;
            if (fragmentManagerImpl != null) {
                bl = bl3 | fragmentManagerImpl.dispatchPrepareOptionsMenu(menu2);
            }
        }
        return bl;
    }

    void performResume() {
        Object object = this.mChildFragmentManager;
        if (object != null) {
            ((FragmentManagerImpl)object).noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 5;
        this.mCalled = false;
        this.onResume();
        if (this.mCalled) {
            object = this.mChildFragmentManager;
            if (object != null) {
                ((FragmentManagerImpl)object).dispatchResume();
                this.mChildFragmentManager.execPendingActions();
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onResume()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    void performSaveInstanceState(Bundle bundle) {
        this.onSaveInstanceState(bundle);
        Object object = this.mChildFragmentManager;
        if (object != null && (object = ((FragmentManagerImpl)object).saveAllState()) != null) {
            bundle.putParcelable("android:fragments", (Parcelable)object);
        }
    }

    void performStart() {
        Object object = this.mChildFragmentManager;
        if (object != null) {
            ((FragmentManagerImpl)object).noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 4;
        this.mCalled = false;
        this.onStart();
        if (this.mCalled) {
            object = this.mChildFragmentManager;
            if (object != null) {
                ((FragmentManagerImpl)object).dispatchStart();
            }
            if ((object = this.mLoaderManager) != null) {
                ((LoaderManagerImpl)object).doReportStart();
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onStart()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    void performStop() {
        Object object = this.mChildFragmentManager;
        if (object != null) {
            ((FragmentManagerImpl)object).dispatchStop();
        }
        this.mState = 3;
        this.mCalled = false;
        this.onStop();
        if (this.mCalled) {
            if (this.mLoadersStarted) {
                this.mLoadersStarted = false;
                if (!this.mCheckedForLoaderManager) {
                    this.mCheckedForLoaderManager = true;
                    this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
                }
                if (this.mLoaderManager != null) {
                    if (this.mHost.getRetainLoaders()) {
                        this.mLoaderManager.doRetain();
                    } else {
                        this.mLoaderManager.doStop();
                    }
                }
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onStop()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    void performTrimMemory(int n) {
        this.onTrimMemory(n);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchTrimMemory(n);
        }
    }

    public void postponeEnterTransition() {
        this.ensureAnimationInfo().mEnterTransitionPostponed = true;
    }

    public void registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener(this);
    }

    public final void requestPermissions(String[] object, int n) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            fragmentHostCallback.onRequestPermissionsFromFragment(this, (String[])object, n);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    void restoreChildFragmentState(Bundle object, boolean bl) {
        Object t;
        if (object != null && (t = ((Bundle)object).getParcelable("android:fragments")) != null) {
            if (this.mChildFragmentManager == null) {
                this.instantiateChildFragmentManager();
            }
            FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
            object = bl ? this.mChildNonConfig : null;
            fragmentManagerImpl.restoreAllState((Parcelable)t, (FragmentManagerNonConfig)object);
            this.mChildNonConfig = null;
            this.mChildFragmentManager.dispatchCreate();
        }
    }

    final void restoreViewState(Bundle object) {
        SparseArray<Parcelable> sparseArray = this.mSavedViewState;
        if (sparseArray != null) {
            this.mView.restoreHierarchyState(sparseArray);
            this.mSavedViewState = null;
        }
        this.mCalled = false;
        this.onViewStateRestored((Bundle)object);
        if (this.mCalled) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onViewStateRestored()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    public void setAllowEnterTransitionOverlap(boolean bl) {
        this.ensureAnimationInfo().mAllowEnterTransitionOverlap = bl;
    }

    public void setAllowReturnTransitionOverlap(boolean bl) {
        this.ensureAnimationInfo().mAllowReturnTransitionOverlap = bl;
    }

    void setAnimatingAway(Animator animator2) {
        this.ensureAnimationInfo().mAnimatingAway = animator2;
    }

    public void setArguments(Bundle bundle) {
        if (this.mIndex >= 0 && this.isStateSaved()) {
            throw new IllegalStateException("Fragment already active");
        }
        this.mArguments = bundle;
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        SharedElementCallback sharedElementCallback2 = sharedElementCallback;
        if (sharedElementCallback == null) {
            if (this.mAnimationInfo == null) {
                return;
            }
            sharedElementCallback2 = SharedElementCallback.NULL_CALLBACK;
        }
        this.ensureAnimationInfo().mEnterTransitionCallback = sharedElementCallback2;
    }

    public void setEnterTransition(Transition transition2) {
        if (this.shouldChangeTransition(transition2, null)) {
            this.ensureAnimationInfo().mEnterTransition = transition2;
        }
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        SharedElementCallback sharedElementCallback2 = sharedElementCallback;
        if (sharedElementCallback == null) {
            if (this.mAnimationInfo == null) {
                return;
            }
            sharedElementCallback2 = SharedElementCallback.NULL_CALLBACK;
        }
        this.ensureAnimationInfo().mExitTransitionCallback = sharedElementCallback2;
    }

    public void setExitTransition(Transition transition2) {
        if (this.shouldChangeTransition(transition2, null)) {
            this.ensureAnimationInfo().mExitTransition = transition2;
        }
    }

    public void setHasOptionsMenu(boolean bl) {
        if (this.mHasMenu != bl) {
            this.mHasMenu = bl;
            if (this.isAdded() && !this.isHidden()) {
                this.mFragmentManager.invalidateOptionsMenu();
            }
        }
    }

    void setHideReplaced(boolean bl) {
        this.ensureAnimationInfo().mIsHideReplaced = bl;
    }

    final void setIndex(int n, Fragment object) {
        this.mIndex = n;
        if (object != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((Fragment)object).mWho);
            stringBuilder.append(":");
            stringBuilder.append(this.mIndex);
            this.mWho = stringBuilder.toString();
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("android:fragment:");
            ((StringBuilder)object).append(this.mIndex);
            this.mWho = ((StringBuilder)object).toString();
        }
    }

    public void setInitialSavedState(SavedState parcelable) {
        if (this.mIndex < 0) {
            parcelable = parcelable != null && parcelable.mState != null ? parcelable.mState : null;
            this.mSavedFragmentState = parcelable;
            return;
        }
        throw new IllegalStateException("Fragment already active");
    }

    public void setMenuVisibility(boolean bl) {
        if (this.mMenuVisible != bl) {
            this.mMenuVisible = bl;
            if (this.mHasMenu && this.isAdded() && !this.isHidden()) {
                this.mFragmentManager.invalidateOptionsMenu();
            }
        }
    }

    void setNextAnim(int n) {
        if (this.mAnimationInfo == null && n == 0) {
            return;
        }
        this.ensureAnimationInfo().mNextAnim = n;
    }

    void setNextTransition(int n, int n2) {
        if (this.mAnimationInfo == null && n == 0 && n2 == 0) {
            return;
        }
        this.ensureAnimationInfo();
        AnimationInfo animationInfo = this.mAnimationInfo;
        animationInfo.mNextTransition = n;
        animationInfo.mNextTransitionStyle = n2;
    }

    void setOnStartEnterTransitionListener(OnStartEnterTransitionListener object) {
        this.ensureAnimationInfo();
        if (object == this.mAnimationInfo.mStartEnterTransitionListener) {
            return;
        }
        if (object != null && this.mAnimationInfo.mStartEnterTransitionListener != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Trying to set a replacement startPostponedEnterTransition on ");
            ((StringBuilder)object).append(this);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        if (this.mAnimationInfo.mEnterTransitionPostponed) {
            this.mAnimationInfo.mStartEnterTransitionListener = object;
        }
        if (object != null) {
            object.startListening();
        }
    }

    public void setReenterTransition(Transition transition2) {
        if (this.shouldChangeTransition(transition2, USE_DEFAULT_TRANSITION)) {
            this.ensureAnimationInfo().mReenterTransition = transition2;
        }
    }

    public void setRetainInstance(boolean bl) {
        this.mRetainInstance = bl;
    }

    public void setReturnTransition(Transition transition2) {
        if (this.shouldChangeTransition(transition2, USE_DEFAULT_TRANSITION)) {
            this.ensureAnimationInfo().mReturnTransition = transition2;
        }
    }

    public void setSharedElementEnterTransition(Transition transition2) {
        if (this.shouldChangeTransition(transition2, null)) {
            this.ensureAnimationInfo().mSharedElementEnterTransition = transition2;
        }
    }

    public void setSharedElementReturnTransition(Transition transition2) {
        if (this.shouldChangeTransition(transition2, USE_DEFAULT_TRANSITION)) {
            this.ensureAnimationInfo().mSharedElementReturnTransition = transition2;
        }
    }

    void setStateAfterAnimating(int n) {
        this.ensureAnimationInfo().mStateAfterAnimating = n;
    }

    public void setTargetFragment(Fragment fragment, int n) {
        FragmentManager fragmentManager = this.getFragmentManager();
        Object object = fragment != null ? fragment.getFragmentManager() : null;
        if (fragmentManager != null && object != null && fragmentManager != object) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Fragment ");
            ((StringBuilder)object).append(fragment);
            ((StringBuilder)object).append(" must share the same FragmentManager to be set as a target fragment");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        for (object = fragment; object != null; object = object.getTargetFragment()) {
            if (object != this) {
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Setting ");
            ((StringBuilder)object).append(fragment);
            ((StringBuilder)object).append(" as the target of ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" would create a target cycle");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        this.mTarget = fragment;
        this.mTargetRequestCode = n;
    }

    public void setUserVisibleHint(boolean bl) {
        boolean bl2 = false;
        Context context = this.getContext();
        FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
        Context context2 = context;
        if (fragmentManagerImpl != null) {
            context2 = context;
            if (fragmentManagerImpl.mHost != null) {
                context2 = this.mFragmentManager.mHost.getContext();
            }
        }
        boolean bl3 = true;
        if (context2 != null) {
            bl2 = context2.getApplicationInfo().targetSdkVersion <= 23;
        }
        bl2 = bl2 ? !this.mUserVisibleHint && bl && this.mState < 4 && this.mFragmentManager != null : !this.mUserVisibleHint && bl && this.mState < 4 && this.mFragmentManager != null && this.isAdded();
        if (bl2) {
            this.mFragmentManager.performPendingDeferredStart(this);
        }
        this.mUserVisibleHint = bl;
        bl = this.mState < 4 && !bl ? bl3 : false;
        this.mDeferStart = bl;
    }

    public boolean shouldShowRequestPermissionRationale(String string2) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            return fragmentHostCallback.getContext().getPackageManager().shouldShowRequestPermissionRationale(string2);
        }
        return false;
    }

    public void startActivity(Intent intent) {
        this.startActivity(intent, null);
    }

    public void startActivity(Intent object, Bundle bundle) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            if (bundle != null) {
                fragmentHostCallback.onStartActivityFromFragment(this, (Intent)object, -1, bundle);
            } else {
                fragmentHostCallback.onStartActivityFromFragment(this, (Intent)object, -1, null);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void startActivityForResult(Intent intent, int n) {
        this.startActivityForResult(intent, n, null);
    }

    public void startActivityForResult(Intent object, int n, Bundle bundle) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            fragmentHostCallback.onStartActivityFromFragment(this, (Intent)object, n, bundle);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void startActivityForResultAsUser(Intent object, int n, Bundle bundle, UserHandle userHandle) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            fragmentHostCallback.onStartActivityAsUserFromFragment(this, (Intent)object, n, bundle, userHandle);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void startIntentSenderForResult(IntentSender object, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            fragmentHostCallback.onStartIntentSenderFromFragment(this, (IntentSender)object, n, intent, n2, n3, n4, bundle);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void startPostponedEnterTransition() {
        FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
        if (fragmentManagerImpl != null && fragmentManagerImpl.mHost != null) {
            if (Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
                this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new _$$Lambda$Fragment$m7ODa2MK0_rf4XCEL5JOn14G0h8(this));
            } else {
                this.callStartTransitionListener();
            }
        } else {
            this.ensureAnimationInfo().mEnterTransitionPostponed = false;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        DebugUtils.buildShortClassTag(this, stringBuilder);
        if (this.mIndex >= 0) {
            stringBuilder.append(" #");
            stringBuilder.append(this.mIndex);
        }
        if (this.mFragmentId != 0) {
            stringBuilder.append(" id=0x");
            stringBuilder.append(Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.mTag);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null);
    }

    static class AnimationInfo {
        private Boolean mAllowEnterTransitionOverlap;
        private Boolean mAllowReturnTransitionOverlap;
        Animator mAnimatingAway;
        private Transition mEnterTransition = null;
        SharedElementCallback mEnterTransitionCallback = SharedElementCallback.NULL_CALLBACK;
        boolean mEnterTransitionPostponed;
        private Transition mExitTransition = null;
        SharedElementCallback mExitTransitionCallback = SharedElementCallback.NULL_CALLBACK;
        boolean mIsHideReplaced;
        int mNextAnim;
        int mNextTransition;
        int mNextTransitionStyle;
        private Transition mReenterTransition = Fragment.access$800();
        private Transition mReturnTransition = Fragment.access$800();
        private Transition mSharedElementEnterTransition = null;
        private Transition mSharedElementReturnTransition = Fragment.access$800();
        OnStartEnterTransitionListener mStartEnterTransitionListener;
        int mStateAfterAnimating;

        AnimationInfo() {
        }
    }

    @Deprecated
    public static class InstantiationException
    extends AndroidRuntimeException {
        public InstantiationException(String string2, Exception exception) {
            super(string2, exception);
        }
    }

    static interface OnStartEnterTransitionListener {
        public void onStartEnterTransition();

        public void startListening();
    }

    @Deprecated
    public static class SavedState
    implements Parcelable {
        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            @Override
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        final Bundle mState;

        SavedState(Bundle bundle) {
            this.mState = bundle;
        }

        SavedState(Parcel object, ClassLoader classLoader) {
            this.mState = ((Parcel)object).readBundle();
            if (classLoader != null && (object = this.mState) != null) {
                ((Bundle)object).setClassLoader(classLoader);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeBundle(this.mState);
        }

    }

}

