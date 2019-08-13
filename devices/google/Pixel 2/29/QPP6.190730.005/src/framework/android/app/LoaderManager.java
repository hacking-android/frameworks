/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.FragmentHostCallback;
import android.app.LoaderManagerImpl;
import android.content.Loader;
import android.os.Bundle;
import java.io.FileDescriptor;
import java.io.PrintWriter;

@Deprecated
public abstract class LoaderManager {
    public static void enableDebugLogging(boolean bl) {
        LoaderManagerImpl.DEBUG = bl;
    }

    public abstract void destroyLoader(int var1);

    public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    public FragmentHostCallback getFragmentHostCallback() {
        return null;
    }

    public abstract <D> Loader<D> getLoader(int var1);

    public abstract <D> Loader<D> initLoader(int var1, Bundle var2, LoaderCallbacks<D> var3);

    public abstract <D> Loader<D> restartLoader(int var1, Bundle var2, LoaderCallbacks<D> var3);

    @Deprecated
    public static interface LoaderCallbacks<D> {
        public Loader<D> onCreateLoader(int var1, Bundle var2);

        public void onLoadFinished(Loader<D> var1, D var2);

        public void onLoaderReset(Loader<D> var1);
    }

}

