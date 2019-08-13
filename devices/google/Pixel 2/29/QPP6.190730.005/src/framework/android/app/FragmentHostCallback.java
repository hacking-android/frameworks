/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentContainer;
import android.app.FragmentManagerImpl;
import android.app.LoaderManager;
import android.app.LoaderManagerImpl;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

@Deprecated
public abstract class FragmentHostCallback<E>
extends FragmentContainer {
    private final Activity mActivity;
    private ArrayMap<String, LoaderManager> mAllLoaderManagers;
    private boolean mCheckedForLoaderManager;
    final Context mContext;
    final FragmentManagerImpl mFragmentManager;
    private final Handler mHandler;
    private LoaderManagerImpl mLoaderManager;
    @UnsupportedAppUsage
    private boolean mLoadersStarted;
    private boolean mRetainLoaders;
    final int mWindowAnimations;

    FragmentHostCallback(Activity activity) {
        this(activity, activity, activity.mHandler, 0);
    }

    FragmentHostCallback(Activity activity, Context context, Handler handler, int n) {
        this.mFragmentManager = new FragmentManagerImpl();
        this.mActivity = activity;
        this.mContext = context;
        this.mHandler = handler;
        this.mWindowAnimations = n;
    }

    public FragmentHostCallback(Context context, Handler handler, int n) {
        Activity activity = context instanceof Activity ? (Activity)context : null;
        this(activity, context, FragmentHostCallback.chooseHandler(context, handler), n);
    }

    private static Handler chooseHandler(Context context, Handler handler) {
        if (handler == null && context instanceof Activity) {
            return ((Activity)context).mHandler;
        }
        return handler;
    }

    void doLoaderDestroy() {
        LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
        if (loaderManagerImpl == null) {
            return;
        }
        loaderManagerImpl.doDestroy();
    }

    void doLoaderRetain() {
        LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
        if (loaderManagerImpl == null) {
            return;
        }
        loaderManagerImpl.doRetain();
    }

    void doLoaderStart() {
        if (this.mLoadersStarted) {
            return;
        }
        this.mLoadersStarted = true;
        LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
        if (loaderManagerImpl != null) {
            loaderManagerImpl.doStart();
        } else if (!this.mCheckedForLoaderManager) {
            this.mLoaderManager = this.getLoaderManager("(root)", this.mLoadersStarted, false);
        }
        this.mCheckedForLoaderManager = true;
    }

    void doLoaderStop(boolean bl) {
        this.mRetainLoaders = bl;
        LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
        if (loaderManagerImpl == null) {
            return;
        }
        if (!this.mLoadersStarted) {
            return;
        }
        this.mLoadersStarted = false;
        if (bl) {
            loaderManagerImpl.doRetain();
        } else {
            loaderManagerImpl.doStop();
        }
    }

    void dumpLoaders(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.print(string2);
        printWriter.print("mLoadersStarted=");
        printWriter.println(this.mLoadersStarted);
        if (this.mLoaderManager != null) {
            printWriter.print(string2);
            printWriter.print("Loader Manager ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this.mLoaderManager)));
            printWriter.println(":");
            LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("  ");
            loaderManagerImpl.dump(stringBuilder.toString(), fileDescriptor, printWriter, arrstring);
        }
    }

    Activity getActivity() {
        return this.mActivity;
    }

    Context getContext() {
        return this.mContext;
    }

    FragmentManagerImpl getFragmentManagerImpl() {
        return this.mFragmentManager;
    }

    Handler getHandler() {
        return this.mHandler;
    }

    LoaderManagerImpl getLoaderManager(String object, boolean bl, boolean bl2) {
        LoaderManagerImpl loaderManagerImpl;
        if (this.mAllLoaderManagers == null) {
            this.mAllLoaderManagers = new ArrayMap();
        }
        if ((loaderManagerImpl = (LoaderManagerImpl)this.mAllLoaderManagers.get(object)) == null && bl2) {
            loaderManagerImpl = new LoaderManagerImpl((String)object, this, bl);
            this.mAllLoaderManagers.put((String)object, loaderManagerImpl);
            object = loaderManagerImpl;
        } else {
            object = loaderManagerImpl;
            if (bl) {
                object = loaderManagerImpl;
                if (loaderManagerImpl != null) {
                    object = loaderManagerImpl;
                    if (!loaderManagerImpl.mStarted) {
                        loaderManagerImpl.doStart();
                        object = loaderManagerImpl;
                    }
                }
            }
        }
        return object;
    }

    LoaderManagerImpl getLoaderManagerImpl() {
        LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
        if (loaderManagerImpl != null) {
            return loaderManagerImpl;
        }
        this.mCheckedForLoaderManager = true;
        this.mLoaderManager = this.getLoaderManager("(root)", this.mLoadersStarted, true);
        return this.mLoaderManager;
    }

    boolean getRetainLoaders() {
        return this.mRetainLoaders;
    }

    void inactivateFragment(String string2) {
        ArrayMap<String, LoaderManager> arrayMap = this.mAllLoaderManagers;
        if (arrayMap != null && (arrayMap = (LoaderManagerImpl)arrayMap.get(string2)) != null && !((LoaderManagerImpl)arrayMap).mRetaining) {
            ((LoaderManagerImpl)((Object)arrayMap)).doDestroy();
            this.mAllLoaderManagers.remove(string2);
        }
    }

    public void onAttachFragment(Fragment fragment) {
    }

    public void onDump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    @Override
    public <T extends View> T onFindViewById(int n) {
        return null;
    }

    public abstract E onGetHost();

    public LayoutInflater onGetLayoutInflater() {
        return (LayoutInflater)this.mContext.getSystemService("layout_inflater");
    }

    public int onGetWindowAnimations() {
        return this.mWindowAnimations;
    }

    @Override
    public boolean onHasView() {
        return true;
    }

    public boolean onHasWindowAnimations() {
        return true;
    }

    public void onInvalidateOptionsMenu() {
    }

    public void onRequestPermissionsFromFragment(Fragment fragment, String[] arrstring, int n) {
    }

    public boolean onShouldSaveFragmentState(Fragment fragment) {
        return true;
    }

    public void onStartActivityAsUserFromFragment(Fragment fragment, Intent intent, int n, Bundle bundle, UserHandle userHandle) {
        if (n == -1) {
            this.mContext.startActivityAsUser(intent, userHandle);
            return;
        }
        throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n, Bundle bundle) {
        if (n == -1) {
            this.mContext.startActivity(intent);
            return;
        }
        throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
    }

    public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        if (n == -1) {
            this.mContext.startIntentSender(intentSender, intent, n2, n3, n4, bundle);
            return;
        }
        throw new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
    }

    public boolean onUseFragmentManagerInflaterFactory() {
        return false;
    }

    void reportLoaderStart() {
        LoaderManagerImpl[] arrloaderManagerImpl = this.mAllLoaderManagers;
        if (arrloaderManagerImpl != null) {
            int n;
            int n2 = arrloaderManagerImpl.size();
            arrloaderManagerImpl = new LoaderManagerImpl[n2];
            for (n = n2 - 1; n >= 0; --n) {
                arrloaderManagerImpl[n] = (LoaderManagerImpl)this.mAllLoaderManagers.valueAt(n);
            }
            for (n = 0; n < n2; ++n) {
                LoaderManagerImpl loaderManagerImpl = arrloaderManagerImpl[n];
                loaderManagerImpl.finishRetain();
                loaderManagerImpl.doReportStart();
            }
        }
    }

    void restoreLoaderNonConfig(ArrayMap<String, LoaderManager> arrayMap) {
        if (arrayMap != null) {
            int n = arrayMap.size();
            for (int i = 0; i < n; ++i) {
                ((LoaderManagerImpl)arrayMap.valueAt(i)).updateHostController(this);
            }
        }
        this.mAllLoaderManagers = arrayMap;
    }

    ArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        int n = 0;
        int n2 = 0;
        ArrayMap<String, LoaderManager> arrayMap = this.mAllLoaderManagers;
        if (arrayMap != null) {
            int n3;
            int n4 = arrayMap.size();
            LoaderManagerImpl[] arrloaderManagerImpl = new LoaderManagerImpl[n4];
            for (n3 = n4 - 1; n3 >= 0; --n3) {
                arrloaderManagerImpl[n3] = (LoaderManagerImpl)this.mAllLoaderManagers.valueAt(n3);
            }
            boolean bl = this.getRetainLoaders();
            int n5 = 0;
            n3 = n2;
            do {
                n = n3;
                if (n5 >= n4) break;
                arrayMap = arrloaderManagerImpl[n5];
                if (!((LoaderManagerImpl)arrayMap).mRetaining && bl) {
                    if (!((LoaderManagerImpl)arrayMap).mStarted) {
                        ((LoaderManagerImpl)((Object)arrayMap)).doStart();
                    }
                    ((LoaderManagerImpl)((Object)arrayMap)).doRetain();
                }
                if (((LoaderManagerImpl)arrayMap).mRetaining) {
                    n3 = 1;
                } else {
                    ((LoaderManagerImpl)((Object)arrayMap)).doDestroy();
                    this.mAllLoaderManagers.remove(((LoaderManagerImpl)arrayMap).mWho);
                }
                ++n5;
            } while (true);
        }
        if (n != 0) {
            return this.mAllLoaderManagers;
        }
        return null;
    }
}

