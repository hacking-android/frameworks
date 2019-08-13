/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Fragment;
import android.app.FragmentContainer;
import android.app.FragmentHostCallback;
import android.app.FragmentManager;
import android.app.FragmentManagerImpl;
import android.app.FragmentManagerNonConfig;
import android.app.LoaderManager;
import android.app.LoaderManagerImpl;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

@Deprecated
public class FragmentController {
    @UnsupportedAppUsage
    private final FragmentHostCallback<?> mHost;

    private FragmentController(FragmentHostCallback<?> fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    public static final FragmentController createController(FragmentHostCallback<?> fragmentHostCallback) {
        return new FragmentController(fragmentHostCallback);
    }

    public void attachHost(Fragment fragment) {
        FragmentManagerImpl fragmentManagerImpl = this.mHost.mFragmentManager;
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        fragmentManagerImpl.attachController(fragmentHostCallback, fragmentHostCallback, fragment);
    }

    public void dispatchActivityCreated() {
        this.mHost.mFragmentManager.dispatchActivityCreated();
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        this.mHost.mFragmentManager.dispatchConfigurationChanged(configuration);
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        return this.mHost.mFragmentManager.dispatchContextItemSelected(menuItem);
    }

    public void dispatchCreate() {
        this.mHost.mFragmentManager.dispatchCreate();
    }

    public boolean dispatchCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        return this.mHost.mFragmentManager.dispatchCreateOptionsMenu(menu2, menuInflater);
    }

    public void dispatchDestroy() {
        this.mHost.mFragmentManager.dispatchDestroy();
    }

    public void dispatchDestroyView() {
        this.mHost.mFragmentManager.dispatchDestroyView();
    }

    public void dispatchLowMemory() {
        this.mHost.mFragmentManager.dispatchLowMemory();
    }

    @Deprecated
    public void dispatchMultiWindowModeChanged(boolean bl) {
        this.mHost.mFragmentManager.dispatchMultiWindowModeChanged(bl);
    }

    public void dispatchMultiWindowModeChanged(boolean bl, Configuration configuration) {
        this.mHost.mFragmentManager.dispatchMultiWindowModeChanged(bl, configuration);
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        return this.mHost.mFragmentManager.dispatchOptionsItemSelected(menuItem);
    }

    public void dispatchOptionsMenuClosed(Menu menu2) {
        this.mHost.mFragmentManager.dispatchOptionsMenuClosed(menu2);
    }

    public void dispatchPause() {
        this.mHost.mFragmentManager.dispatchPause();
    }

    @Deprecated
    public void dispatchPictureInPictureModeChanged(boolean bl) {
        this.mHost.mFragmentManager.dispatchPictureInPictureModeChanged(bl);
    }

    public void dispatchPictureInPictureModeChanged(boolean bl, Configuration configuration) {
        this.mHost.mFragmentManager.dispatchPictureInPictureModeChanged(bl, configuration);
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu2) {
        return this.mHost.mFragmentManager.dispatchPrepareOptionsMenu(menu2);
    }

    public void dispatchResume() {
        this.mHost.mFragmentManager.dispatchResume();
    }

    public void dispatchStart() {
        this.mHost.mFragmentManager.dispatchStart();
    }

    public void dispatchStop() {
        this.mHost.mFragmentManager.dispatchStop();
    }

    public void dispatchTrimMemory(int n) {
        this.mHost.mFragmentManager.dispatchTrimMemory(n);
    }

    public void doLoaderDestroy() {
        this.mHost.doLoaderDestroy();
    }

    public void doLoaderStart() {
        this.mHost.doLoaderStart();
    }

    public void doLoaderStop(boolean bl) {
        this.mHost.doLoaderStop(bl);
    }

    public void dumpLoaders(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        this.mHost.dumpLoaders(string2, fileDescriptor, printWriter, arrstring);
    }

    public boolean execPendingActions() {
        return this.mHost.mFragmentManager.execPendingActions();
    }

    public Fragment findFragmentByWho(String string2) {
        return this.mHost.mFragmentManager.findFragmentByWho(string2);
    }

    public FragmentManager getFragmentManager() {
        return this.mHost.getFragmentManagerImpl();
    }

    public LoaderManager getLoaderManager() {
        return this.mHost.getLoaderManagerImpl();
    }

    public void noteStateNotSaved() {
        this.mHost.mFragmentManager.noteStateNotSaved();
    }

    public View onCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
        return this.mHost.mFragmentManager.onCreateView(view, string2, context, attributeSet);
    }

    public void reportLoaderStart() {
        this.mHost.reportLoaderStart();
    }

    public void restoreAllState(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        this.mHost.mFragmentManager.restoreAllState(parcelable, fragmentManagerNonConfig);
    }

    @Deprecated
    public void restoreAllState(Parcelable parcelable, List<Fragment> list) {
        this.mHost.mFragmentManager.restoreAllState(parcelable, new FragmentManagerNonConfig(list, null));
    }

    public void restoreLoaderNonConfig(ArrayMap<String, LoaderManager> arrayMap) {
        this.mHost.restoreLoaderNonConfig(arrayMap);
    }

    public ArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        return this.mHost.retainLoaderNonConfig();
    }

    public FragmentManagerNonConfig retainNestedNonConfig() {
        return this.mHost.mFragmentManager.retainNonConfig();
    }

    @Deprecated
    public List<Fragment> retainNonConfig() {
        return this.mHost.mFragmentManager.retainNonConfig().getFragments();
    }

    public Parcelable saveAllState() {
        return this.mHost.mFragmentManager.saveAllState();
    }
}

