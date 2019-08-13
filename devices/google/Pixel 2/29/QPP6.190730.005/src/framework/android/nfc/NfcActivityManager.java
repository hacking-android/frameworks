/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.Application;
import android.content.ContentProvider;
import android.net.Uri;
import android.nfc.BeamShareData;
import android.nfc.IAppCallback;
import android.nfc.INfcAdapter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.Window;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class NfcActivityManager
extends IAppCallback.Stub
implements Application.ActivityLifecycleCallbacks {
    static final Boolean DBG = false;
    static final String TAG = "NFC";
    final List<NfcActivityState> mActivities;
    @UnsupportedAppUsage
    final NfcAdapter mAdapter;
    final List<NfcApplicationState> mApps;

    public NfcActivityManager(NfcAdapter nfcAdapter) {
        this.mAdapter = nfcAdapter;
        this.mActivities = new LinkedList<NfcActivityState>();
        this.mApps = new ArrayList<NfcApplicationState>(1);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    @Override
    public BeamShareData createBeamShareData(byte var1_1) {
        block22 : {
            block21 : {
                var2_2 = new NfcEvent(this.mAdapter, var1_1);
                synchronized (this) {
                    block20 : {
                        var3_11 = this.findResumedActivityState();
                        if (var3_11 != null) break block20;
                        try {
                            return null;
                        }
                        catch (Throwable var2_3) {}
                        break block23;
                    }
                    var4_12 = var3_11.ndefMessageCallback;
                    var5_13 = var3_11.uriCallback;
                    var6_14 = var3_11.ndefMessage;
                    var7_15 = var3_11.uris;
                    var8_16 = var3_11.flags;
                    var3_11 = var3_11.activity;
                }
                var9_17 = Binder.clearCallingIdentity();
                if (var4_12 != null) {
                    try {
                        var6_14 = var4_12.createNdefMessage((NfcEvent)var2_2);
                    }
                    catch (Throwable var2_4) {
                        break block21;
                    }
                }
                if (var5_13 == null) ** GOTO lbl55
                var4_12 = var2_2;
                var7_15 = var5_13.createBeamUris((NfcEvent)var2_2);
                if (var7_15 == null) ** GOTO lbl51
                var4_12 = var2_2;
                var4_12 = var2_2;
                var5_13 = new ArrayList<Uri>();
                var4_12 = var2_2;
                for (Uri var13_20 : var7_15) {
                    if (var13_20 != null) ** GOTO lbl40
                    Log.e("NFC", "Uri not allowed to be null.");
                    continue;
lbl40: // 1 sources:
                    var4_12 = var13_20.getScheme();
                    if (var4_12 != null && (var4_12.equalsIgnoreCase("file") || var4_12.equalsIgnoreCase("content"))) {
                        var5_13.add(ContentProvider.maybeAddUserId(var13_20, var3_11.getUserId()));
                        continue;
                    }
                    Log.e("NFC", "Uri needs to have either scheme file or scheme content");
                }
                try {
                    block24 : {
                        var2_2 = var5_13.toArray(new Uri[var5_13.size()]);
                        break block24;
lbl51: // 1 sources:
                        var2_2 = var7_15;
                        break block24;
                        catch (Throwable var2_5) {}
                        break block21;
lbl55: // 1 sources:
                        var2_2 = var7_15;
                    }
                    if (var2_2 == null || var2_2.length <= 0) break block22;
                    var11_18 = var2_2.length;
                    for (var12_19 = 0; var12_19 < var11_18; ++var12_19) {
                        var3_11.grantUriPermission("com.android.nfc", var2_2[var12_19], 1);
                    }
                    break block22;
                }
                catch (Throwable var2_6) {
                    // empty catch block
                }
                {
                    continue;
                    break;
                }
            }
            Binder.restoreCallingIdentity(var9_17);
            throw var2_7;
        }
        Binder.restoreCallingIdentity(var9_17);
        return new BeamShareData(var6_14, var2_2, var3_11.getUser(), var8_16);
        {
            block23 : {
                catch (Throwable var2_8) {
                    // empty catch block
                }
            }
            do {
                try {}
                catch (Throwable var2_10) {
                    continue;
                }
                throw var2_9;
                break;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void destroyActivityState(Activity object) {
        synchronized (this) {
            object = this.findActivityState((Activity)object);
            if (object != null) {
                ((NfcActivityState)object).destroy();
                this.mActivities.remove(object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void disableReaderMode(Activity object) {
        // MONITORENTER : this
        object = this.getActivityState((Activity)object);
        ((NfcActivityState)object).readerCallback = null;
        ((NfcActivityState)object).readerModeFlags = 0;
        ((NfcActivityState)object).readerModeExtras = null;
        Binder binder = ((NfcActivityState)object).token;
        boolean bl = ((NfcActivityState)object).resumed;
        // MONITOREXIT : this
        if (!bl) return;
        this.setReaderMode(binder, 0, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void enableReaderMode(Activity object, NfcAdapter.ReaderCallback object2, int n, Bundle bundle) {
        // MONITORENTER : this
        object = this.getActivityState((Activity)object);
        ((NfcActivityState)object).readerCallback = object2;
        ((NfcActivityState)object).readerModeFlags = n;
        ((NfcActivityState)object).readerModeExtras = bundle;
        object2 = ((NfcActivityState)object).token;
        boolean bl = ((NfcActivityState)object).resumed;
        // MONITOREXIT : this
        if (!bl) return;
        this.setReaderMode((Binder)object2, n, bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    NfcActivityState findActivityState(Activity activity) {
        synchronized (this) {
            NfcActivityState nfcActivityState;
            Activity activity2;
            Iterator<NfcActivityState> iterator = this.mActivities.iterator();
            do {
                if (!iterator.hasNext()) {
                    return null;
                }
                nfcActivityState = iterator.next();
            } while ((activity2 = nfcActivityState.activity) != activity);
            return nfcActivityState;
        }
    }

    NfcApplicationState findAppState(Application application) {
        for (NfcApplicationState nfcApplicationState : this.mApps) {
            if (nfcApplicationState.app != application) continue;
            return nfcApplicationState;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    NfcActivityState findResumedActivityState() {
        synchronized (this) {
            NfcActivityState nfcActivityState222;
            boolean bl;
            Iterator<NfcActivityState> iterator = this.mActivities.iterator();
            do {
                if (!iterator.hasNext()) {
                    return null;
                }
                nfcActivityState222 = iterator.next();
            } while (!(bl = nfcActivityState222.resumed));
            return nfcActivityState222;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    NfcActivityState getActivityState(Activity activity) {
        synchronized (this) {
            NfcActivityState nfcActivityState;
            NfcActivityState nfcActivityState2 = nfcActivityState = this.findActivityState(activity);
            if (nfcActivityState == null) {
                nfcActivityState2 = new NfcActivityState(activity);
                this.mActivities.add(nfcActivityState2);
            }
            return nfcActivityState2;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onActivityDestroyed(Activity activity) {
        synchronized (this) {
            NfcActivityState nfcActivityState = this.findActivityState(activity);
            if (DBG.booleanValue()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onDestroy() for ");
                stringBuilder.append(activity);
                stringBuilder.append(" ");
                stringBuilder.append(nfcActivityState);
                Log.d(TAG, stringBuilder.toString());
            }
            if (nfcActivityState != null) {
                this.destroyActivityState(activity);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onActivityPaused(Activity object) {
        boolean bl;
        synchronized (this) {
            NfcActivityState nfcActivityState = this.findActivityState((Activity)object);
            if (DBG.booleanValue()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onPause() for ");
                stringBuilder.append(object);
                stringBuilder.append(" ");
                stringBuilder.append(nfcActivityState);
                Log.d(TAG, stringBuilder.toString());
            }
            if (nfcActivityState == null) {
                return;
            }
            nfcActivityState.resumed = false;
            object = nfcActivityState.token;
            if (nfcActivityState.readerModeFlags == 0) return;
            bl = true;
        }
        if (!bl) return;
        this.setReaderMode((Binder)object, 0, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void onActivityResumed(Activity object) {
        // MONITORENTER : this
        Object object2 = this.findActivityState((Activity)object);
        if (DBG.booleanValue()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onResume() for ");
            stringBuilder.append(object);
            stringBuilder.append(" ");
            stringBuilder.append(object2);
            Log.d(TAG, stringBuilder.toString());
        }
        if (object2 == null) {
            // MONITOREXIT : this
            return;
        }
        ((NfcActivityState)object2).resumed = true;
        object = ((NfcActivityState)object2).token;
        int n = ((NfcActivityState)object2).readerModeFlags;
        object2 = ((NfcActivityState)object2).readerModeExtras;
        // MONITOREXIT : this
        if (n != 0) {
            this.setReaderMode((Binder)object, n, (Bundle)object2);
        }
        this.requestNfcServiceCallback();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onNdefPushComplete(byte by) {
        NfcAdapter.OnNdefPushCompleteCallback onNdefPushCompleteCallback;
        Object object;
        synchronized (this) {
            object = this.findResumedActivityState();
            if (object == null) {
                return;
            }
            onNdefPushCompleteCallback = ((NfcActivityState)object).onNdefPushCompleteCallback;
        }
        object = new NfcEvent(this.mAdapter, by);
        if (onNdefPushCompleteCallback != null) {
            onNdefPushCompleteCallback.onNdefPushComplete((NfcEvent)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void onTagDiscovered(Tag tag) throws RemoteException {
        // MONITORENTER : this
        Object object = this.findResumedActivityState();
        if (object == null) {
            // MONITOREXIT : this
            return;
        }
        object = ((NfcActivityState)object).readerCallback;
        // MONITOREXIT : this
        if (object == null) return;
        object.onTagDiscovered(tag);
    }

    void registerApplication(Application application) {
        NfcApplicationState nfcApplicationState;
        NfcApplicationState nfcApplicationState2 = nfcApplicationState = this.findAppState(application);
        if (nfcApplicationState == null) {
            nfcApplicationState2 = new NfcApplicationState(application);
            this.mApps.add(nfcApplicationState2);
        }
        nfcApplicationState2.register();
    }

    void requestNfcServiceCallback() {
        try {
            NfcAdapter.sService.setAppCallback(this);
        }
        catch (RemoteException remoteException) {
            this.mAdapter.attemptDeadServiceRecovery(remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setNdefPushContentUri(Activity object, Uri[] arruri) {
        // MONITORENTER : this
        object = this.getActivityState((Activity)object);
        ((NfcActivityState)object).uris = arruri;
        boolean bl = ((NfcActivityState)object).resumed;
        // MONITOREXIT : this
        if (bl) {
            this.requestNfcServiceCallback();
            return;
        }
        this.verifyNfcPermission();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setNdefPushContentUriCallback(Activity object, NfcAdapter.CreateBeamUrisCallback createBeamUrisCallback) {
        // MONITORENTER : this
        object = this.getActivityState((Activity)object);
        ((NfcActivityState)object).uriCallback = createBeamUrisCallback;
        boolean bl = ((NfcActivityState)object).resumed;
        // MONITOREXIT : this
        if (bl) {
            this.requestNfcServiceCallback();
            return;
        }
        this.verifyNfcPermission();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setNdefPushMessage(Activity object, NdefMessage ndefMessage, int n) {
        // MONITORENTER : this
        object = this.getActivityState((Activity)object);
        ((NfcActivityState)object).ndefMessage = ndefMessage;
        ((NfcActivityState)object).flags = n;
        boolean bl = ((NfcActivityState)object).resumed;
        // MONITOREXIT : this
        if (bl) {
            this.requestNfcServiceCallback();
            return;
        }
        this.verifyNfcPermission();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setNdefPushMessageCallback(Activity object, NfcAdapter.CreateNdefMessageCallback createNdefMessageCallback, int n) {
        // MONITORENTER : this
        object = this.getActivityState((Activity)object);
        ((NfcActivityState)object).ndefMessageCallback = createNdefMessageCallback;
        ((NfcActivityState)object).flags = n;
        boolean bl = ((NfcActivityState)object).resumed;
        // MONITOREXIT : this
        if (bl) {
            this.requestNfcServiceCallback();
            return;
        }
        this.verifyNfcPermission();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setOnNdefPushCompleteCallback(Activity object, NfcAdapter.OnNdefPushCompleteCallback onNdefPushCompleteCallback) {
        // MONITORENTER : this
        object = this.getActivityState((Activity)object);
        ((NfcActivityState)object).onNdefPushCompleteCallback = onNdefPushCompleteCallback;
        boolean bl = ((NfcActivityState)object).resumed;
        // MONITOREXIT : this
        if (bl) {
            this.requestNfcServiceCallback();
            return;
        }
        this.verifyNfcPermission();
    }

    public void setReaderMode(Binder binder, int n, Bundle bundle) {
        if (DBG.booleanValue()) {
            Log.d(TAG, "Setting reader mode");
        }
        try {
            NfcAdapter.sService.setReaderMode(binder, this, n, bundle);
        }
        catch (RemoteException remoteException) {
            this.mAdapter.attemptDeadServiceRecovery(remoteException);
        }
    }

    void unregisterApplication(Application application) {
        Object object = this.findAppState(application);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("app was not registered ");
            ((StringBuilder)object).append(application);
            Log.e(TAG, ((StringBuilder)object).toString());
            return;
        }
        ((NfcApplicationState)object).unregister();
    }

    void verifyNfcPermission() {
        try {
            NfcAdapter.sService.verifyNfcPermission();
        }
        catch (RemoteException remoteException) {
            this.mAdapter.attemptDeadServiceRecovery(remoteException);
        }
    }

    class NfcActivityState {
        Activity activity;
        int flags = 0;
        NdefMessage ndefMessage = null;
        NfcAdapter.CreateNdefMessageCallback ndefMessageCallback = null;
        NfcAdapter.OnNdefPushCompleteCallback onNdefPushCompleteCallback = null;
        NfcAdapter.ReaderCallback readerCallback = null;
        Bundle readerModeExtras = null;
        int readerModeFlags = 0;
        boolean resumed = false;
        Binder token;
        NfcAdapter.CreateBeamUrisCallback uriCallback = null;
        Uri[] uris = null;

        public NfcActivityState(Activity activity) {
            if (!activity.getWindow().isDestroyed()) {
                this.resumed = activity.isResumed();
                this.activity = activity;
                this.token = new Binder();
                NfcActivityManager.this.registerApplication(activity.getApplication());
                return;
            }
            throw new IllegalStateException("activity is already destroyed");
        }

        public void destroy() {
            NfcActivityManager.this.unregisterApplication(this.activity.getApplication());
            this.resumed = false;
            this.activity = null;
            this.ndefMessage = null;
            this.ndefMessageCallback = null;
            this.onNdefPushCompleteCallback = null;
            this.uriCallback = null;
            this.uris = null;
            this.readerModeFlags = 0;
            this.token = null;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("[").append(" ");
            stringBuilder.append(this.ndefMessage);
            stringBuilder.append(" ");
            stringBuilder.append(this.ndefMessageCallback);
            stringBuilder.append(" ");
            stringBuilder.append(this.uriCallback);
            stringBuilder.append(" ");
            Uri[] arruri = this.uris;
            if (arruri != null) {
                for (Uri uri : arruri) {
                    stringBuilder.append(this.onNdefPushCompleteCallback);
                    stringBuilder.append(" ");
                    stringBuilder.append(uri);
                    stringBuilder.append("]");
                }
            }
            return stringBuilder.toString();
        }
    }

    class NfcApplicationState {
        final Application app;
        int refCount = 0;

        public NfcApplicationState(Application application) {
            this.app = application;
        }

        public void register() {
            ++this.refCount;
            if (this.refCount == 1) {
                this.app.registerActivityLifecycleCallbacks(NfcActivityManager.this);
            }
        }

        public void unregister() {
            --this.refCount;
            int n = this.refCount;
            if (n == 0) {
                this.app.unregisterActivityLifecycleCallbacks(NfcActivityManager.this);
            } else if (n < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("-ve refcount for ");
                stringBuilder.append(this.app);
                Log.e(NfcActivityManager.TAG, stringBuilder.toString());
            }
        }
    }

}

