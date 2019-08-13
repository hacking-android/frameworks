/*
 * Decompiled with CFR 0.145.
 */
package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.ContentClassification;
import android.app.contentsuggestions.ContentSelection;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.IContentSuggestionsManager;
import android.app.contentsuggestions.ISelectionsCallback;
import android.app.contentsuggestions.SelectionsRequest;
import android.app.contentsuggestions._$$Lambda$ContentSuggestionsManager$ClassificationsCallbackWrapper$bS71fhWJJl2gObzWDnBMzvYmM5w;
import android.app.contentsuggestions._$$Lambda$ContentSuggestionsManager$SelectionsCallbackWrapper$1Py0lukljawDYbfwobeRIUDvpNM;
import android.os.Binder;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.SyncResultReceiver;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
public final class ContentSuggestionsManager {
    private static final int SYNC_CALLS_TIMEOUT_MS = 5000;
    private static final String TAG = ContentSuggestionsManager.class.getSimpleName();
    private final IContentSuggestionsManager mService;
    private final int mUser;

    public ContentSuggestionsManager(int n, IContentSuggestionsManager iContentSuggestionsManager) {
        this.mService = iContentSuggestionsManager;
        this.mUser = n;
    }

    public void classifyContentSelections(ClassificationsRequest classificationsRequest, Executor executor, ClassificationsCallback classificationsCallback) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "classifyContentSelections called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            int n = this.mUser;
            ClassificationsCallbackWrapper classificationsCallbackWrapper = new ClassificationsCallbackWrapper(classificationsCallback, executor);
            iContentSuggestionsManager.classifyContentSelections(n, classificationsRequest, classificationsCallbackWrapper);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isEnabled() {
        IInterface iInterface = this.mService;
        boolean bl = false;
        if (iInterface == null) {
            return false;
        }
        iInterface = new SyncResultReceiver(5000);
        try {
            this.mService.isEnabled(this.mUser, (IResultReceiver)iInterface);
            int n = ((SyncResultReceiver)iInterface).getIntResult();
            if (n != 0) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return false;
        }
    }

    public void notifyInteraction(String string2, Bundle bundle) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "notifyInteraction called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            iContentSuggestionsManager.notifyInteraction(this.mUser, string2, bundle);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public void provideContextImage(int n, Bundle bundle) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "provideContextImage called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            iContentSuggestionsManager.provideContextImage(this.mUser, n, bundle);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public void suggestContentSelections(SelectionsRequest selectionsRequest, Executor executor, SelectionsCallback selectionsCallback) {
        IContentSuggestionsManager iContentSuggestionsManager = this.mService;
        if (iContentSuggestionsManager == null) {
            Log.e(TAG, "suggestContentSelections called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            int n = this.mUser;
            SelectionsCallbackWrapper selectionsCallbackWrapper = new SelectionsCallbackWrapper(selectionsCallback, executor);
            iContentSuggestionsManager.suggestContentSelections(n, selectionsRequest, selectionsCallbackWrapper);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public static interface ClassificationsCallback {
        public void onContentClassificationsAvailable(int var1, List<ContentClassification> var2);
    }

    private static final class ClassificationsCallbackWrapper
    extends IClassificationsCallback.Stub {
        private final ClassificationsCallback mCallback;
        private final Executor mExecutor;

        ClassificationsCallbackWrapper(ClassificationsCallback classificationsCallback, Executor executor) {
            this.mCallback = classificationsCallback;
            this.mExecutor = executor;
        }

        public /* synthetic */ void lambda$onContentClassificationsAvailable$0$ContentSuggestionsManager$ClassificationsCallbackWrapper(int n, List list) {
            this.mCallback.onContentClassificationsAvailable(n, list);
        }

        @Override
        public void onContentClassificationsAvailable(int n, List<ContentClassification> list) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$ContentSuggestionsManager$ClassificationsCallbackWrapper$bS71fhWJJl2gObzWDnBMzvYmM5w _$$Lambda$ContentSuggestionsManager$ClassificationsCallbackWrapper$bS71fhWJJl2gObzWDnBMzvYmM5w = new _$$Lambda$ContentSuggestionsManager$ClassificationsCallbackWrapper$bS71fhWJJl2gObzWDnBMzvYmM5w(this, n, list);
                executor.execute(_$$Lambda$ContentSuggestionsManager$ClassificationsCallbackWrapper$bS71fhWJJl2gObzWDnBMzvYmM5w);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }
    }

    public static interface SelectionsCallback {
        public void onContentSelectionsAvailable(int var1, List<ContentSelection> var2);
    }

    private static class SelectionsCallbackWrapper
    extends ISelectionsCallback.Stub {
        private final SelectionsCallback mCallback;
        private final Executor mExecutor;

        SelectionsCallbackWrapper(SelectionsCallback selectionsCallback, Executor executor) {
            this.mCallback = selectionsCallback;
            this.mExecutor = executor;
        }

        public /* synthetic */ void lambda$onContentSelectionsAvailable$0$ContentSuggestionsManager$SelectionsCallbackWrapper(int n, List list) {
            this.mCallback.onContentSelectionsAvailable(n, list);
        }

        @Override
        public void onContentSelectionsAvailable(int n, List<ContentSelection> list) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$ContentSuggestionsManager$SelectionsCallbackWrapper$1Py0lukljawDYbfwobeRIUDvpNM _$$Lambda$ContentSuggestionsManager$SelectionsCallbackWrapper$1Py0lukljawDYbfwobeRIUDvpNM = new _$$Lambda$ContentSuggestionsManager$SelectionsCallbackWrapper$1Py0lukljawDYbfwobeRIUDvpNM(this, n, list);
                executor.execute(_$$Lambda$ContentSuggestionsManager$SelectionsCallbackWrapper$1Py0lukljawDYbfwobeRIUDvpNM);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }
    }

}

