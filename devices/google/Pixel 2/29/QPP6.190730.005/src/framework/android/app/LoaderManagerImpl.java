/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.FragmentHostCallback;
import android.app.FragmentManagerImpl;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.DebugUtils;
import android.util.Log;
import android.util.SparseArray;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Modifier;

class LoaderManagerImpl
extends LoaderManager {
    static boolean DEBUG = false;
    static final String TAG = "LoaderManager";
    boolean mCreatingLoader;
    private FragmentHostCallback mHost;
    final SparseArray<LoaderInfo> mInactiveLoaders = new SparseArray(0);
    final SparseArray<LoaderInfo> mLoaders = new SparseArray(0);
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;
    final String mWho;

    static {
        DEBUG = false;
    }

    LoaderManagerImpl(String string2, FragmentHostCallback fragmentHostCallback, boolean bl) {
        this.mWho = string2;
        this.mHost = fragmentHostCallback;
        this.mStarted = bl;
    }

    private LoaderInfo createAndInstallLoader(int n, Bundle object, LoaderManager.LoaderCallbacks<Object> loaderCallbacks) {
        try {
            this.mCreatingLoader = true;
            object = this.createLoader(n, (Bundle)object, loaderCallbacks);
            this.installLoader((LoaderInfo)object);
            return object;
        }
        finally {
            this.mCreatingLoader = false;
        }
    }

    private LoaderInfo createLoader(int n, Bundle bundle, LoaderManager.LoaderCallbacks<Object> loaderCallbacks) {
        LoaderInfo loaderInfo = new LoaderInfo(n, bundle, loaderCallbacks);
        loaderInfo.mLoader = loaderCallbacks.onCreateLoader(n, bundle);
        return loaderInfo;
    }

    @Override
    public void destroyLoader(int n) {
        if (!this.mCreatingLoader) {
            Object object;
            int n2;
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("destroyLoader in ");
                ((StringBuilder)object).append(this);
                ((StringBuilder)object).append(" of ");
                ((StringBuilder)object).append(n);
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            if ((n2 = this.mLoaders.indexOfKey(n)) >= 0) {
                object = this.mLoaders.valueAt(n2);
                this.mLoaders.removeAt(n2);
                ((LoaderInfo)object).destroy();
            }
            if ((n = this.mInactiveLoaders.indexOfKey(n)) >= 0) {
                object = this.mInactiveLoaders.valueAt(n);
                this.mInactiveLoaders.removeAt(n);
                ((LoaderInfo)object).destroy();
            }
            if (this.mHost != null && !this.hasRunningLoaders()) {
                this.mHost.mFragmentManager.startPendingDeferredFragments();
            }
            return;
        }
        throw new IllegalStateException("Called while creating a loader");
    }

    void doDestroy() {
        int n;
        StringBuilder stringBuilder;
        if (!this.mRetaining) {
            if (DEBUG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Destroying Active in ");
                stringBuilder.append(this);
                Log.v(TAG, stringBuilder.toString());
            }
            for (n = this.mLoaders.size() - 1; n >= 0; --n) {
                this.mLoaders.valueAt(n).destroy();
            }
            this.mLoaders.clear();
        }
        if (DEBUG) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Destroying Inactive in ");
            stringBuilder.append(this);
            Log.v(TAG, stringBuilder.toString());
        }
        for (n = this.mInactiveLoaders.size() - 1; n >= 0; --n) {
            this.mInactiveLoaders.valueAt(n).destroy();
        }
        this.mInactiveLoaders.clear();
        this.mHost = null;
    }

    void doReportNextStart() {
        for (int i = this.mLoaders.size() - 1; i >= 0; --i) {
            this.mLoaders.valueAt((int)i).mReportNextStart = true;
        }
    }

    void doReportStart() {
        for (int i = this.mLoaders.size() - 1; i >= 0; --i) {
            this.mLoaders.valueAt(i).reportStart();
        }
    }

    void doRetain() {
        StringBuilder stringBuilder;
        if (DEBUG) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Retaining in ");
            stringBuilder.append(this);
            Log.v(TAG, stringBuilder.toString());
        }
        if (!this.mStarted) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            stringBuilder = new StringBuilder();
            stringBuilder.append("Called doRetain when not started: ");
            stringBuilder.append(this);
            Log.w(TAG, stringBuilder.toString(), runtimeException);
            return;
        }
        this.mRetaining = true;
        this.mStarted = false;
        for (int i = this.mLoaders.size() - 1; i >= 0; --i) {
            this.mLoaders.valueAt(i).retain();
        }
    }

    void doStart() {
        Serializable serializable;
        if (DEBUG) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Starting in ");
            ((StringBuilder)serializable).append(this);
            Log.v(TAG, ((StringBuilder)serializable).toString());
        }
        if (this.mStarted) {
            serializable = new RuntimeException("here");
            ((Throwable)serializable).fillInStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Called doStart when already started: ");
            stringBuilder.append(this);
            Log.w(TAG, stringBuilder.toString(), (Throwable)serializable);
            return;
        }
        this.mStarted = true;
        for (int i = this.mLoaders.size() - 1; i >= 0; --i) {
            this.mLoaders.valueAt(i).start();
        }
    }

    void doStop() {
        Serializable serializable;
        if (DEBUG) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Stopping in ");
            ((StringBuilder)serializable).append(this);
            Log.v(TAG, ((StringBuilder)serializable).toString());
        }
        if (!this.mStarted) {
            serializable = new RuntimeException("here");
            ((Throwable)serializable).fillInStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Called doStop when not started: ");
            stringBuilder.append(this);
            Log.w(TAG, stringBuilder.toString(), (Throwable)serializable);
            return;
        }
        for (int i = this.mLoaders.size() - 1; i >= 0; --i) {
            this.mLoaders.valueAt(i).stop();
        }
        this.mStarted = false;
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        int n;
        Object object;
        Object object2;
        if (this.mLoaders.size() > 0) {
            printWriter.print(string2);
            printWriter.println("Active Loaders:");
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("    ");
            object = ((StringBuilder)object2).toString();
            for (n = 0; n < this.mLoaders.size(); ++n) {
                object2 = this.mLoaders.valueAt(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(this.mLoaders.keyAt(n));
                printWriter.print(": ");
                printWriter.println(((LoaderInfo)object2).toString());
                ((LoaderInfo)object2).dump((String)object, fileDescriptor, printWriter, arrstring);
            }
        }
        if (this.mInactiveLoaders.size() > 0) {
            printWriter.print(string2);
            printWriter.println("Inactive Loaders:");
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("    ");
            object2 = ((StringBuilder)object2).toString();
            for (n = 0; n < this.mInactiveLoaders.size(); ++n) {
                object = this.mInactiveLoaders.valueAt(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(this.mInactiveLoaders.keyAt(n));
                printWriter.print(": ");
                printWriter.println(((LoaderInfo)object).toString());
                ((LoaderInfo)object).dump((String)object2, fileDescriptor, printWriter, arrstring);
            }
        }
    }

    void finishRetain() {
        if (this.mRetaining) {
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Finished Retaining in ");
                stringBuilder.append(this);
                Log.v(TAG, stringBuilder.toString());
            }
            this.mRetaining = false;
            for (int i = this.mLoaders.size() - 1; i >= 0; --i) {
                this.mLoaders.valueAt(i).finishRetain();
            }
        }
    }

    @Override
    public FragmentHostCallback getFragmentHostCallback() {
        return this.mHost;
    }

    @Override
    public <D> Loader<D> getLoader(int n) {
        if (!this.mCreatingLoader) {
            LoaderInfo loaderInfo = this.mLoaders.get(n);
            if (loaderInfo != null) {
                if (loaderInfo.mPendingLoader != null) {
                    return loaderInfo.mPendingLoader.mLoader;
                }
                return loaderInfo.mLoader;
            }
            return null;
        }
        throw new IllegalStateException("Called while creating a loader");
    }

    public boolean hasRunningLoaders() {
        boolean bl = false;
        int n = this.mLoaders.size();
        for (int i = 0; i < n; ++i) {
            LoaderInfo loaderInfo = this.mLoaders.valueAt(i);
            boolean bl2 = loaderInfo.mStarted && !loaderInfo.mDeliveredData;
            bl |= bl2;
        }
        return bl;
    }

    @Override
    public <D> Loader<D> initLoader(int n, Bundle object, LoaderManager.LoaderCallbacks<D> object2) {
        if (!this.mCreatingLoader) {
            LoaderInfo loaderInfo = this.mLoaders.get(n);
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("initLoader in ");
                stringBuilder.append(this);
                stringBuilder.append(": args=");
                stringBuilder.append(object);
                Log.v(TAG, stringBuilder.toString());
            }
            if (loaderInfo == null) {
                object = object2 = this.createAndInstallLoader(n, (Bundle)object, (LoaderManager.LoaderCallbacks<Object>)object2);
                if (DEBUG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  Created new loader ");
                    ((StringBuilder)object).append(object2);
                    Log.v(TAG, ((StringBuilder)object).toString());
                    object = object2;
                }
            } else {
                if (DEBUG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  Re-using existing loader ");
                    ((StringBuilder)object).append(loaderInfo);
                    Log.v(TAG, ((StringBuilder)object).toString());
                }
                loaderInfo.mCallbacks = object2;
                object = loaderInfo;
            }
            if (((LoaderInfo)object).mHaveData && this.mStarted) {
                ((LoaderInfo)object).callOnLoadFinished(((LoaderInfo)object).mLoader, ((LoaderInfo)object).mData);
            }
            return ((LoaderInfo)object).mLoader;
        }
        throw new IllegalStateException("Called while creating a loader");
    }

    void installLoader(LoaderInfo loaderInfo) {
        this.mLoaders.put(loaderInfo.mId, loaderInfo);
        if (this.mStarted) {
            loaderInfo.start();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <D> Loader<D> restartLoader(int n, Bundle bundle, LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        StringBuilder stringBuilder;
        if (this.mCreatingLoader) throw new IllegalStateException("Called while creating a loader");
        LoaderInfo loaderInfo = this.mLoaders.get(n);
        if (DEBUG) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("restartLoader in ");
            stringBuilder.append(this);
            stringBuilder.append(": args=");
            stringBuilder.append(bundle);
            Log.v(TAG, stringBuilder.toString());
        }
        if (loaderInfo == null) return this.createAndInstallLoader((int)n, (Bundle)bundle, loaderCallbacks).mLoader;
        LoaderInfo loaderInfo2 = this.mInactiveLoaders.get(n);
        if (loaderInfo2 != null) {
            if (loaderInfo.mHaveData) {
                if (DEBUG) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("  Removing last inactive loader: ");
                    stringBuilder.append(loaderInfo);
                    Log.v(TAG, stringBuilder.toString());
                }
                loaderInfo2.mDeliveredData = false;
                loaderInfo2.destroy();
                loaderInfo.mLoader.abandon();
                this.mInactiveLoaders.put(n, loaderInfo);
                return this.createAndInstallLoader((int)n, (Bundle)bundle, loaderCallbacks).mLoader;
            }
            if (!loaderInfo.cancel()) {
                if (DEBUG) {
                    Log.v(TAG, "  Current loader is stopped; replacing");
                }
                this.mLoaders.put(n, null);
                loaderInfo.destroy();
                return this.createAndInstallLoader((int)n, (Bundle)bundle, loaderCallbacks).mLoader;
            }
            if (DEBUG) {
                Log.v(TAG, "  Current loader is running; configuring pending loader");
            }
            if (loaderInfo.mPendingLoader != null) {
                if (DEBUG) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("  Removing pending loader: ");
                    stringBuilder.append(loaderInfo.mPendingLoader);
                    Log.v(TAG, stringBuilder.toString());
                }
                loaderInfo.mPendingLoader.destroy();
                loaderInfo.mPendingLoader = null;
            }
            if (DEBUG) {
                Log.v(TAG, "  Enqueuing as new pending loader");
            }
            loaderInfo.mPendingLoader = this.createLoader(n, bundle, loaderCallbacks);
            return loaderInfo.mPendingLoader.mLoader;
        }
        if (DEBUG) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("  Making last loader inactive: ");
            stringBuilder.append(loaderInfo);
            Log.v(TAG, stringBuilder.toString());
        }
        loaderInfo.mLoader.abandon();
        this.mInactiveLoaders.put(n, loaderInfo);
        return this.createAndInstallLoader((int)n, (Bundle)bundle, loaderCallbacks).mLoader;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("LoaderManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        DebugUtils.buildShortClassTag(this.mHost, stringBuilder);
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    void updateHostController(FragmentHostCallback fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    final class LoaderInfo
    implements Loader.OnLoadCompleteListener<Object>,
    Loader.OnLoadCanceledListener<Object> {
        final Bundle mArgs;
        LoaderManager.LoaderCallbacks<Object> mCallbacks;
        Object mData;
        boolean mDeliveredData;
        boolean mDestroyed;
        boolean mHaveData;
        final int mId;
        boolean mListenerRegistered;
        Loader<Object> mLoader;
        LoaderInfo mPendingLoader;
        boolean mReportNextStart;
        boolean mRetaining;
        boolean mRetainingStarted;
        boolean mStarted;

        public LoaderInfo(int n, Bundle bundle, LoaderManager.LoaderCallbacks<Object> loaderCallbacks) {
            this.mId = n;
            this.mArgs = bundle;
            this.mCallbacks = loaderCallbacks;
        }

        void callOnLoadFinished(Loader<Object> loader, Object object) {
            if (this.mCallbacks != null) {
                String string2 = null;
                if (LoaderManagerImpl.this.mHost != null) {
                    string2 = LoaderManagerImpl.access$000((LoaderManagerImpl)LoaderManagerImpl.this).mFragmentManager.mNoTransactionsBecause;
                    LoaderManagerImpl.access$000((LoaderManagerImpl)LoaderManagerImpl.this).mFragmentManager.mNoTransactionsBecause = "onLoadFinished";
                }
                try {
                    if (DEBUG) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("  onLoadFinished in ");
                        stringBuilder.append(loader);
                        stringBuilder.append(": ");
                        stringBuilder.append(loader.dataToString(object));
                        Log.v(LoaderManagerImpl.TAG, stringBuilder.toString());
                    }
                    this.mCallbacks.onLoadFinished(loader, object);
                    this.mDeliveredData = true;
                }
                finally {
                    if (LoaderManagerImpl.this.mHost != null) {
                        LoaderManagerImpl.access$000((LoaderManagerImpl)LoaderManagerImpl.this).mFragmentManager.mNoTransactionsBecause = string2;
                    }
                }
            }
        }

        boolean cancel() {
            Object object;
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("  Canceling: ");
                ((StringBuilder)object).append(this);
                Log.v(LoaderManagerImpl.TAG, ((StringBuilder)object).toString());
            }
            if (this.mStarted && (object = this.mLoader) != null && this.mListenerRegistered) {
                boolean bl = ((Loader)object).cancelLoad();
                if (!bl) {
                    this.onLoadCanceled(this.mLoader);
                }
                return bl;
            }
            return false;
        }

        void destroy() {
            Loader<Object> loader;
            if (DEBUG) {
                loader = new StringBuilder();
                ((StringBuilder)((Object)loader)).append("  Destroying: ");
                ((StringBuilder)((Object)loader)).append(this);
                Log.v(LoaderManagerImpl.TAG, ((StringBuilder)((Object)loader)).toString());
            }
            this.mDestroyed = true;
            boolean bl = this.mDeliveredData;
            this.mDeliveredData = false;
            if (this.mCallbacks != null && this.mLoader != null && this.mHaveData && bl) {
                if (DEBUG) {
                    loader = new StringBuilder();
                    ((StringBuilder)((Object)loader)).append("  Reseting: ");
                    ((StringBuilder)((Object)loader)).append(this);
                    Log.v(LoaderManagerImpl.TAG, ((StringBuilder)((Object)loader)).toString());
                }
                loader = null;
                if (LoaderManagerImpl.this.mHost != null) {
                    loader = LoaderManagerImpl.access$000((LoaderManagerImpl)LoaderManagerImpl.this).mFragmentManager.mNoTransactionsBecause;
                    LoaderManagerImpl.access$000((LoaderManagerImpl)LoaderManagerImpl.this).mFragmentManager.mNoTransactionsBecause = "onLoaderReset";
                }
                try {
                    this.mCallbacks.onLoaderReset(this.mLoader);
                }
                finally {
                    if (LoaderManagerImpl.this.mHost != null) {
                        LoaderManagerImpl.access$000((LoaderManagerImpl)LoaderManagerImpl.this).mFragmentManager.mNoTransactionsBecause = loader;
                    }
                }
            }
            this.mCallbacks = null;
            this.mData = null;
            this.mHaveData = false;
            loader = this.mLoader;
            if (loader != null) {
                if (this.mListenerRegistered) {
                    this.mListenerRegistered = false;
                    loader.unregisterListener(this);
                    this.mLoader.unregisterOnLoadCanceledListener(this);
                }
                this.mLoader.reset();
            }
            if ((loader = this.mPendingLoader) != null) {
                ((LoaderInfo)((Object)loader)).destroy();
            }
        }

        public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            Object object;
            printWriter.print(string2);
            printWriter.print("mId=");
            printWriter.print(this.mId);
            printWriter.print(" mArgs=");
            printWriter.println(this.mArgs);
            printWriter.print(string2);
            printWriter.print("mCallbacks=");
            printWriter.println(this.mCallbacks);
            printWriter.print(string2);
            printWriter.print("mLoader=");
            printWriter.println(this.mLoader);
            Object object2 = this.mLoader;
            if (object2 != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("  ");
                ((Loader)object2).dump(((StringBuilder)object).toString(), fileDescriptor, printWriter, arrstring);
            }
            if (this.mHaveData || this.mDeliveredData) {
                printWriter.print(string2);
                printWriter.print("mHaveData=");
                printWriter.print(this.mHaveData);
                printWriter.print("  mDeliveredData=");
                printWriter.println(this.mDeliveredData);
                printWriter.print(string2);
                printWriter.print("mData=");
                printWriter.println(this.mData);
            }
            printWriter.print(string2);
            printWriter.print("mStarted=");
            printWriter.print(this.mStarted);
            printWriter.print(" mReportNextStart=");
            printWriter.print(this.mReportNextStart);
            printWriter.print(" mDestroyed=");
            printWriter.println(this.mDestroyed);
            printWriter.print(string2);
            printWriter.print("mRetaining=");
            printWriter.print(this.mRetaining);
            printWriter.print(" mRetainingStarted=");
            printWriter.print(this.mRetainingStarted);
            printWriter.print(" mListenerRegistered=");
            printWriter.println(this.mListenerRegistered);
            if (this.mPendingLoader != null) {
                printWriter.print(string2);
                printWriter.println("Pending Loader ");
                printWriter.print(this.mPendingLoader);
                printWriter.println(":");
                object = this.mPendingLoader;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append("  ");
                ((LoaderInfo)object).dump(((StringBuilder)object2).toString(), fileDescriptor, printWriter, arrstring);
            }
        }

        void finishRetain() {
            if (this.mRetaining) {
                if (DEBUG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("  Finished Retaining: ");
                    stringBuilder.append(this);
                    Log.v(LoaderManagerImpl.TAG, stringBuilder.toString());
                }
                this.mRetaining = false;
                boolean bl = this.mStarted;
                if (bl != this.mRetainingStarted && !bl) {
                    this.stop();
                }
            }
            if (this.mStarted && this.mHaveData && !this.mReportNextStart) {
                this.callOnLoadFinished(this.mLoader, this.mData);
            }
        }

        @Override
        public void onLoadCanceled(Loader<Object> object) {
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onLoadCanceled: ");
                ((StringBuilder)object).append(this);
                Log.v(LoaderManagerImpl.TAG, ((StringBuilder)object).toString());
            }
            if (this.mDestroyed) {
                if (DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Ignoring load canceled -- destroyed");
                }
                return;
            }
            if (LoaderManagerImpl.this.mLoaders.get(this.mId) != this) {
                if (DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Ignoring load canceled -- not active");
                }
                return;
            }
            LoaderInfo loaderInfo = this.mPendingLoader;
            if (loaderInfo != null) {
                if (DEBUG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  Switching to pending loader: ");
                    ((StringBuilder)object).append(loaderInfo);
                    Log.v(LoaderManagerImpl.TAG, ((StringBuilder)object).toString());
                }
                this.mPendingLoader = null;
                LoaderManagerImpl.this.mLoaders.put(this.mId, null);
                this.destroy();
                LoaderManagerImpl.this.installLoader(loaderInfo);
            }
        }

        @Override
        public void onLoadComplete(Loader<Object> object, Object object2) {
            Object object3;
            if (DEBUG) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("onLoadComplete: ");
                ((StringBuilder)object3).append(this);
                Log.v(LoaderManagerImpl.TAG, ((StringBuilder)object3).toString());
            }
            if (this.mDestroyed) {
                if (DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Ignoring load complete -- destroyed");
                }
                return;
            }
            if (LoaderManagerImpl.this.mLoaders.get(this.mId) != this) {
                if (DEBUG) {
                    Log.v(LoaderManagerImpl.TAG, "  Ignoring load complete -- not active");
                }
                return;
            }
            object3 = this.mPendingLoader;
            if (object3 != null) {
                if (DEBUG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  Switching to pending loader: ");
                    ((StringBuilder)object).append(object3);
                    Log.v(LoaderManagerImpl.TAG, ((StringBuilder)object).toString());
                }
                this.mPendingLoader = null;
                LoaderManagerImpl.this.mLoaders.put(this.mId, null);
                this.destroy();
                LoaderManagerImpl.this.installLoader((LoaderInfo)object3);
                return;
            }
            if (this.mData != object2 || !this.mHaveData) {
                this.mData = object2;
                this.mHaveData = true;
                if (this.mStarted) {
                    this.callOnLoadFinished((Loader<Object>)object, object2);
                }
            }
            if ((object = LoaderManagerImpl.this.mInactiveLoaders.get(this.mId)) != null && object != this) {
                ((LoaderInfo)object).mDeliveredData = false;
                ((LoaderInfo)object).destroy();
                LoaderManagerImpl.this.mInactiveLoaders.remove(this.mId);
            }
            if (LoaderManagerImpl.this.mHost != null && !LoaderManagerImpl.this.hasRunningLoaders()) {
                LoaderManagerImpl.access$000((LoaderManagerImpl)LoaderManagerImpl.this).mFragmentManager.startPendingDeferredFragments();
            }
        }

        void reportStart() {
            if (this.mStarted && this.mReportNextStart) {
                this.mReportNextStart = false;
                if (this.mHaveData && !this.mRetaining) {
                    this.callOnLoadFinished(this.mLoader, this.mData);
                }
            }
        }

        void retain() {
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("  Retaining: ");
                stringBuilder.append(this);
                Log.v(LoaderManagerImpl.TAG, stringBuilder.toString());
            }
            this.mRetaining = true;
            this.mRetainingStarted = this.mStarted;
            this.mStarted = false;
            this.mCallbacks = null;
        }

        void start() {
            Object object;
            if (this.mRetaining && this.mRetainingStarted) {
                this.mStarted = true;
                return;
            }
            if (this.mStarted) {
                return;
            }
            this.mStarted = true;
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("  Starting: ");
                ((StringBuilder)object).append(this);
                Log.v(LoaderManagerImpl.TAG, ((StringBuilder)object).toString());
            }
            if (this.mLoader == null && (object = this.mCallbacks) != null) {
                this.mLoader = object.onCreateLoader(this.mId, this.mArgs);
            }
            if ((object = this.mLoader) != null) {
                if (object.getClass().isMemberClass() && !Modifier.isStatic(this.mLoader.getClass().getModifiers())) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Object returned from onCreateLoader must not be a non-static inner member class: ");
                    ((StringBuilder)object).append(this.mLoader);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                if (!this.mListenerRegistered) {
                    this.mLoader.registerListener(this.mId, this);
                    this.mLoader.registerOnLoadCanceledListener(this);
                    this.mListenerRegistered = true;
                }
                this.mLoader.startLoading();
            }
        }

        void stop() {
            Object object;
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("  Stopping: ");
                ((StringBuilder)object).append(this);
                Log.v(LoaderManagerImpl.TAG, ((StringBuilder)object).toString());
            }
            this.mStarted = false;
            if (!this.mRetaining && (object = this.mLoader) != null && this.mListenerRegistered) {
                this.mListenerRegistered = false;
                ((Loader)object).unregisterListener(this);
                this.mLoader.unregisterOnLoadCanceledListener(this);
                this.mLoader.stopLoading();
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("LoaderInfo{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" #");
            stringBuilder.append(this.mId);
            stringBuilder.append(" : ");
            DebugUtils.buildShortClassTag(this.mLoader, stringBuilder);
            stringBuilder.append("}}");
            return stringBuilder.toString();
        }
    }

}

