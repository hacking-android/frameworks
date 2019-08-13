/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.OnActivityPausedListener;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.net.Uri;
import android.nfc.BeamShareData;
import android.nfc.INfcAdapter;
import android.nfc.INfcAdapterExtras;
import android.nfc.INfcCardEmulation;
import android.nfc.INfcDta;
import android.nfc.INfcFCardEmulation;
import android.nfc.INfcTag;
import android.nfc.INfcUnlockHandler;
import android.nfc.ITagRemovedCallback;
import android.nfc.NdefMessage;
import android.nfc.NfcActivityManager;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.TechListParcel;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class NfcAdapter {
    public static final String ACTION_ADAPTER_STATE_CHANGED = "android.nfc.action.ADAPTER_STATE_CHANGED";
    public static final String ACTION_HANDOVER_TRANSFER_DONE = "android.nfc.action.HANDOVER_TRANSFER_DONE";
    public static final String ACTION_HANDOVER_TRANSFER_STARTED = "android.nfc.action.HANDOVER_TRANSFER_STARTED";
    public static final String ACTION_NDEF_DISCOVERED = "android.nfc.action.NDEF_DISCOVERED";
    public static final String ACTION_TAG_DISCOVERED = "android.nfc.action.TAG_DISCOVERED";
    public static final String ACTION_TAG_LEFT_FIELD = "android.nfc.action.TAG_LOST";
    public static final String ACTION_TECH_DISCOVERED = "android.nfc.action.TECH_DISCOVERED";
    public static final String ACTION_TRANSACTION_DETECTED = "android.nfc.action.TRANSACTION_DETECTED";
    public static final String EXTRA_ADAPTER_STATE = "android.nfc.extra.ADAPTER_STATE";
    public static final String EXTRA_AID = "android.nfc.extra.AID";
    public static final String EXTRA_DATA = "android.nfc.extra.DATA";
    public static final String EXTRA_HANDOVER_TRANSFER_STATUS = "android.nfc.extra.HANDOVER_TRANSFER_STATUS";
    public static final String EXTRA_HANDOVER_TRANSFER_URI = "android.nfc.extra.HANDOVER_TRANSFER_URI";
    public static final String EXTRA_ID = "android.nfc.extra.ID";
    public static final String EXTRA_NDEF_MESSAGES = "android.nfc.extra.NDEF_MESSAGES";
    public static final String EXTRA_READER_PRESENCE_CHECK_DELAY = "presence";
    public static final String EXTRA_SECURE_ELEMENT_NAME = "android.nfc.extra.SECURE_ELEMENT_NAME";
    public static final String EXTRA_TAG = "android.nfc.extra.TAG";
    @SystemApi
    public static final int FLAG_NDEF_PUSH_NO_CONFIRM = 1;
    public static final int FLAG_READER_NFC_A = 1;
    public static final int FLAG_READER_NFC_B = 2;
    public static final int FLAG_READER_NFC_BARCODE = 16;
    public static final int FLAG_READER_NFC_F = 4;
    public static final int FLAG_READER_NFC_V = 8;
    public static final int FLAG_READER_NO_PLATFORM_SOUNDS = 256;
    public static final int FLAG_READER_SKIP_NDEF_CHECK = 128;
    public static final int HANDOVER_TRANSFER_STATUS_FAILURE = 1;
    public static final int HANDOVER_TRANSFER_STATUS_SUCCESS = 0;
    public static final int STATE_OFF = 1;
    public static final int STATE_ON = 3;
    public static final int STATE_TURNING_OFF = 4;
    public static final int STATE_TURNING_ON = 2;
    static final String TAG = "NFC";
    static INfcCardEmulation sCardEmulationService;
    static boolean sHasBeamFeature;
    static boolean sHasNfcFeature;
    static boolean sIsInitialized;
    static HashMap<Context, NfcAdapter> sNfcAdapters;
    static INfcFCardEmulation sNfcFCardEmulationService;
    static NfcAdapter sNullContextNfcAdapter;
    @UnsupportedAppUsage
    static INfcAdapter sService;
    static INfcTag sTagService;
    final Context mContext;
    OnActivityPausedListener mForegroundDispatchListener = new OnActivityPausedListener(){

        @Override
        public void onPaused(Activity activity) {
            NfcAdapter.this.disableForegroundDispatchInternal(activity, true);
        }
    };
    final Object mLock;
    final NfcActivityManager mNfcActivityManager;
    final HashMap<NfcUnlockHandler, INfcUnlockHandler> mNfcUnlockHandlers;
    ITagRemovedCallback mTagRemovedListener;

    static {
        sIsInitialized = false;
        sNfcAdapters = new HashMap();
    }

    NfcAdapter(Context context) {
        this.mContext = context;
        this.mNfcActivityManager = new NfcActivityManager(this);
        this.mNfcUnlockHandlers = new HashMap();
        this.mTagRemovedListener = null;
        this.mLock = new Object();
    }

    @Deprecated
    @UnsupportedAppUsage
    public static NfcAdapter getDefaultAdapter() {
        Log.w(TAG, "WARNING: NfcAdapter.getDefaultAdapter() is deprecated, use NfcAdapter.getDefaultAdapter(Context) instead", new Exception());
        return NfcAdapter.getNfcAdapter(null);
    }

    public static NfcAdapter getDefaultAdapter(Context object) {
        if (object != null) {
            if ((object = ((Context)object).getApplicationContext()) != null) {
                if ((object = (NfcManager)((Context)object).getSystemService("nfc")) == null) {
                    return null;
                }
                return ((NfcManager)object).getDefaultAdapter();
            }
            throw new IllegalArgumentException("context not associated with any application (using a mock context?)");
        }
        throw new IllegalArgumentException("context cannot be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static NfcAdapter getNfcAdapter(Context object) {
        synchronized (NfcAdapter.class) {
            NfcAdapter nfcAdapter;
            if (!sIsInitialized) {
                sHasNfcFeature = NfcAdapter.hasNfcFeature();
                sHasBeamFeature = NfcAdapter.hasBeamFeature();
                boolean bl = NfcAdapter.hasNfcHceFeature();
                if (!sHasNfcFeature && !bl) {
                    Log.v(TAG, "this device does not have NFC support");
                    object = new UnsupportedOperationException();
                    throw object;
                }
                sService = NfcAdapter.getServiceInterface();
                if (sService == null) {
                    Log.e(TAG, "could not retrieve NFC service");
                    object = new UnsupportedOperationException();
                    throw object;
                }
                boolean bl2 = sHasNfcFeature;
                if (bl2) {
                    try {
                        sTagService = sService.getNfcTagInterface();
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TAG, "could not retrieve NFC Tag service");
                        UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
                        throw unsupportedOperationException;
                    }
                }
                if (bl) {
                    try {
                        sNfcFCardEmulationService = sService.getNfcFCardEmulationInterface();
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TAG, "could not retrieve NFC-F card emulation service");
                        UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
                        throw unsupportedOperationException;
                    }
                    try {
                        sCardEmulationService = sService.getNfcCardEmulationInterface();
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TAG, "could not retrieve card emulation service");
                        UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
                        throw unsupportedOperationException;
                    }
                }
                sIsInitialized = true;
            }
            if (object == null) {
                if (sNullContextNfcAdapter != null) return sNullContextNfcAdapter;
                sNullContextNfcAdapter = object = new NfcAdapter(null);
                return sNullContextNfcAdapter;
            }
            NfcAdapter nfcAdapter2 = nfcAdapter = sNfcAdapters.get(object);
            if (nfcAdapter != null) return nfcAdapter2;
            nfcAdapter2 = new NfcAdapter((Context)object);
            sNfcAdapters.put((Context)object, nfcAdapter2);
            return nfcAdapter2;
        }
    }

    private static INfcAdapter getServiceInterface() {
        IBinder iBinder = ServiceManager.getService("nfc");
        if (iBinder == null) {
            return null;
        }
        return INfcAdapter.Stub.asInterface(iBinder);
    }

    private static boolean hasBeamFeature() {
        IPackageManager iPackageManager = ActivityThread.getPackageManager();
        if (iPackageManager == null) {
            Log.e(TAG, "Cannot get package manager, assuming no Android Beam feature");
            return false;
        }
        try {
            boolean bl = iPackageManager.hasSystemFeature("android.sofware.nfc.beam", 0);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Package manager query failed, assuming no Android Beam feature", remoteException);
            return false;
        }
    }

    private static boolean hasNfcFeature() {
        IPackageManager iPackageManager = ActivityThread.getPackageManager();
        if (iPackageManager == null) {
            Log.e(TAG, "Cannot get package manager, assuming no NFC feature");
            return false;
        }
        try {
            boolean bl = iPackageManager.hasSystemFeature("android.hardware.nfc", 0);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Package manager query failed, assuming no NFC feature", remoteException);
            return false;
        }
    }

    private static boolean hasNfcHceFeature() {
        IPackageManager iPackageManager = ActivityThread.getPackageManager();
        boolean bl = false;
        if (iPackageManager == null) {
            Log.e(TAG, "Cannot get package manager, assuming no NFC feature");
            return false;
        }
        try {
            boolean bl2;
            if (iPackageManager.hasSystemFeature("android.hardware.nfc.hce", 0) || (bl2 = iPackageManager.hasSystemFeature("android.hardware.nfc.hcef", 0))) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Package manager query failed, assuming no NFC feature", remoteException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @SystemApi
    public boolean addNfcUnlockHandler(NfcUnlockHandler object, String[] arrstring) {
        // MONITORENTER : android.nfc.NfcAdapter.class
        if (!sHasNfcFeature) {
            object = new UnsupportedOperationException();
            throw object;
        }
        // MONITOREXIT : android.nfc.NfcAdapter.class
        if (arrstring.length == 0) {
            return false;
        }
        try {
            Object object2 = this.mLock;
            // MONITORENTER : object2
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, "Unable to register LockscreenDispatch", illegalArgumentException);
            return false;
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
            return false;
        }
        if (this.mNfcUnlockHandlers.containsKey(object)) {
            sService.removeNfcUnlockHandler(this.mNfcUnlockHandlers.get(object));
            this.mNfcUnlockHandlers.remove(object);
        }
        INfcUnlockHandler.Stub stub = new INfcUnlockHandler.Stub((NfcUnlockHandler)object){
            final /* synthetic */ NfcUnlockHandler val$unlockHandler;
            {
                this.val$unlockHandler = nfcUnlockHandler;
            }

            @Override
            public boolean onUnlockAttempted(Tag tag) throws RemoteException {
                return this.val$unlockHandler.onUnlockAttempted(tag);
            }
        };
        sService.addNfcUnlockHandler(stub, Tag.getTechCodesFromStrings(arrstring));
        this.mNfcUnlockHandlers.put((NfcUnlockHandler)object, stub);
        // MONITOREXIT : object2
        return true;
    }

    @UnsupportedAppUsage
    public void attemptDeadServiceRecovery(Exception object) {
        Log.e(TAG, "NFC service dead - attempting to recover", (Throwable)object);
        object = NfcAdapter.getServiceInterface();
        if (object == null) {
            Log.e(TAG, "could not retrieve NFC service during service recovery");
            return;
        }
        sService = object;
        try {
            sTagService = object.getNfcTagInterface();
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "could not retrieve NFC tag service during service recovery");
            return;
        }
        try {
            sCardEmulationService = object.getNfcCardEmulationInterface();
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "could not retrieve NFC card emulation service during service recovery");
        }
        try {
            sNfcFCardEmulationService = object.getNfcFCardEmulationInterface();
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "could not retrieve NFC-F card emulation service during service recovery");
        }
    }

    @SystemApi
    public boolean disable() {
        try {
            boolean bl = sService.disable(true);
            return bl;
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
            return false;
        }
    }

    @SystemApi
    public boolean disable(boolean bl) {
        try {
            bl = sService.disable(bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void disableForegroundDispatch(Activity object) {
        synchronized (NfcAdapter.class) {
            if (sHasNfcFeature) {
                // MONITOREXIT [2, 3] lbl3 : MonitorExitStatement: MONITOREXIT : android.nfc.NfcAdapter.class
                ActivityThread.currentActivityThread().unregisterOnActivityPausedListener((Activity)object, this.mForegroundDispatchListener);
                this.disableForegroundDispatchInternal((Activity)object, false);
                return;
            }
            object = new UnsupportedOperationException();
            throw object;
        }
    }

    void disableForegroundDispatchInternal(Activity object, boolean bl) {
        block4 : {
            sService.setForegroundDispatch(null, null, null);
            if (bl) break block4;
            try {
                if (!((Activity)object).isResumed()) {
                    object = new IllegalStateException("You must disable foreground dispatching while your activity is still resumed");
                    throw object;
                }
            }
            catch (RemoteException remoteException) {
                this.attemptDeadServiceRecovery(remoteException);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Deprecated
    public void disableForegroundNdefPush(Activity object) {
        // MONITORENTER : android.nfc.NfcAdapter.class
        if (!sHasNfcFeature) {
            object = new UnsupportedOperationException();
            throw object;
        }
        if (!sHasBeamFeature) {
            // MONITOREXIT : android.nfc.NfcAdapter.class
            return;
        }
        // MONITOREXIT : android.nfc.NfcAdapter.class
        if (object == null) throw new NullPointerException();
        this.enforceResumed((Activity)object);
        this.mNfcActivityManager.setNdefPushMessage((Activity)object, null, 0);
        this.mNfcActivityManager.setNdefPushMessageCallback((Activity)object, null, 0);
        this.mNfcActivityManager.setOnNdefPushCompleteCallback((Activity)object, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public boolean disableNdefPush() {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
                throw unsupportedOperationException;
            }
        }
        try {
            return sService.disableNdefPush();
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void disableReaderMode(Activity object) {
        synchronized (NfcAdapter.class) {
            if (sHasNfcFeature) {
                // MONITOREXIT [2, 3] lbl3 : MonitorExitStatement: MONITOREXIT : android.nfc.NfcAdapter.class
                this.mNfcActivityManager.disableReaderMode((Activity)object);
                return;
            }
            object = new UnsupportedOperationException();
            throw object;
        }
    }

    public void dispatch(Tag tag) {
        if (tag != null) {
            try {
                sService.dispatch(tag);
            }
            catch (RemoteException remoteException) {
                this.attemptDeadServiceRecovery(remoteException);
            }
            return;
        }
        throw new NullPointerException("tag cannot be null");
    }

    @SystemApi
    public boolean enable() {
        try {
            boolean bl = sService.enable();
            return bl;
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
            return false;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void enableForegroundDispatch(Activity var1_1, PendingIntent var2_3, IntentFilter[] var3_4, String[][] var4_5) {
        // MONITORENTER : android.nfc.NfcAdapter.class
        if (!NfcAdapter.sHasNfcFeature) {
            var1_1 = new UnsupportedOperationException();
            throw var1_1;
        }
        // MONITOREXIT : android.nfc.NfcAdapter.class
        if (var1_1 == null) throw new NullPointerException();
        if (var2_3 == null) throw new NullPointerException();
        if (var1_1.isResumed() == false) throw new IllegalStateException("Foreground dispatch can only be enabled when your activity is resumed");
        var6_7 = var5_6 = null;
        if (var4_5 == null) ** GOTO lbl15
        var6_7 = var5_6;
        try {
            if (var4_5.length > 0) {
                var6_7 = new TechListParcel(var4_5);
            }
lbl15: // 4 sources:
            ActivityThread.currentActivityThread().registerOnActivityPausedListener((Activity)var1_1, this.mForegroundDispatchListener);
            NfcAdapter.sService.setForegroundDispatch(var2_3, var3_4, var6_7);
            return;
        }
        catch (RemoteException var1_2) {
            this.attemptDeadServiceRecovery(var1_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Deprecated
    public void enableForegroundNdefPush(Activity object, NdefMessage ndefMessage) {
        // MONITORENTER : android.nfc.NfcAdapter.class
        if (!sHasNfcFeature) {
            object = new UnsupportedOperationException();
            throw object;
        }
        if (!sHasBeamFeature) {
            // MONITOREXIT : android.nfc.NfcAdapter.class
            return;
        }
        // MONITOREXIT : android.nfc.NfcAdapter.class
        if (object == null) throw new NullPointerException();
        if (ndefMessage == null) throw new NullPointerException();
        this.enforceResumed((Activity)object);
        this.mNfcActivityManager.setNdefPushMessage((Activity)object, ndefMessage, 0);
    }

    @SystemApi
    public boolean enableNdefPush() {
        if (sHasNfcFeature) {
            try {
                boolean bl = sService.enableNdefPush();
                return bl;
            }
            catch (RemoteException remoteException) {
                this.attemptDeadServiceRecovery(remoteException);
                return false;
            }
        }
        throw new UnsupportedOperationException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enableReaderMode(Activity object, ReaderCallback readerCallback, int n, Bundle bundle) {
        synchronized (NfcAdapter.class) {
            if (sHasNfcFeature) {
                // MONITOREXIT [2, 3] lbl3 : MonitorExitStatement: MONITOREXIT : android.nfc.NfcAdapter.class
                this.mNfcActivityManager.enableReaderMode((Activity)object, readerCallback, n, bundle);
                return;
            }
            object = new UnsupportedOperationException();
            throw object;
        }
    }

    @SystemApi
    public boolean enableSecureNfc(boolean bl) {
        if (sHasNfcFeature) {
            try {
                bl = sService.setNfcSecure(bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                this.attemptDeadServiceRecovery(remoteException);
                return false;
            }
        }
        throw new UnsupportedOperationException();
    }

    void enforceResumed(Activity activity) {
        if (activity.isResumed()) {
            return;
        }
        throw new IllegalStateException("API cannot be called while activity is paused");
    }

    @UnsupportedAppUsage
    public int getAdapterState() {
        try {
            int n = sService.getState();
            return n;
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
            return 1;
        }
    }

    public INfcCardEmulation getCardEmulationService() {
        this.isEnabled();
        return sCardEmulationService;
    }

    @UnsupportedAppUsage
    public Context getContext() {
        return this.mContext;
    }

    @UnsupportedAppUsage
    public INfcAdapterExtras getNfcAdapterExtrasInterface() {
        Object object = this.mContext;
        if (object != null) {
            try {
                object = sService.getNfcAdapterExtrasInterface(((Context)object).getPackageName());
                return object;
            }
            catch (RemoteException remoteException) {
                this.attemptDeadServiceRecovery(remoteException);
                return null;
            }
        }
        throw new UnsupportedOperationException("You need a context on NfcAdapter to use the  NFC extras APIs");
    }

    public INfcDta getNfcDtaInterface() {
        Object object = this.mContext;
        if (object != null) {
            try {
                object = sService.getNfcDtaInterface(((Context)object).getPackageName());
                return object;
            }
            catch (RemoteException remoteException) {
                this.attemptDeadServiceRecovery(remoteException);
                return null;
            }
        }
        throw new UnsupportedOperationException("You need a context on NfcAdapter to use the  NFC extras APIs");
    }

    public INfcFCardEmulation getNfcFCardEmulationService() {
        this.isEnabled();
        return sNfcFCardEmulationService;
    }

    int getSdkVersion() {
        Context context = this.mContext;
        if (context == null) {
            return 9;
        }
        return context.getApplicationInfo().targetSdkVersion;
    }

    @UnsupportedAppUsage
    public INfcAdapter getService() {
        this.isEnabled();
        return sService;
    }

    public List<String> getSupportedOffHostSecureElements() {
        ArrayList<String> arrayList = new ArrayList<String>();
        IPackageManager iPackageManager = ActivityThread.getPackageManager();
        if (iPackageManager == null) {
            Log.e(TAG, "Cannot get package manager, assuming no off-host CE feature");
            return arrayList;
        }
        try {
            if (iPackageManager.hasSystemFeature("android.hardware.nfc.uicc", 0)) {
                arrayList.add("SIM");
            }
            if (iPackageManager.hasSystemFeature("android.hardware.nfc.ese", 0)) {
                arrayList.add("eSE");
            }
            return arrayList;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Package manager query failed, assuming no off-host CE feature", remoteException);
            arrayList.clear();
            return arrayList;
        }
    }

    public INfcTag getTagService() {
        this.isEnabled();
        return sTagService;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean ignore(Tag tag, int n, OnTagRemovedListener object, final Handler handler) {
        ITagRemovedCallback.Stub stub = null;
        if (object != null) {
            stub = new ITagRemovedCallback.Stub((OnTagRemovedListener)object){
                final /* synthetic */ OnTagRemovedListener val$tagRemovedListener;
                {
                    this.val$tagRemovedListener = onTagRemovedListener;
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void onTagRemoved() throws RemoteException {
                    Handler handler2 = handler;
                    if (handler2 != null) {
                        handler2.post(new Runnable(){

                            @Override
                            public void run() {
                                val$tagRemovedListener.onTagRemoved();
                            }
                        });
                    } else {
                        this.val$tagRemovedListener.onTagRemoved();
                    }
                    Object object = NfcAdapter.this.mLock;
                    synchronized (object) {
                        NfcAdapter.this.mTagRemovedListener = null;
                        return;
                    }
                }

            };
        }
        object = this.mLock;
        synchronized (object) {
            this.mTagRemovedListener = stub;
        }
        try {
            return sService.ignore(tag.getServiceHandle(), n, stub);
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Deprecated
    public boolean invokeBeam(Activity object) {
        // MONITORENTER : android.nfc.NfcAdapter.class
        if (!sHasNfcFeature) {
            object = new UnsupportedOperationException();
            throw object;
        }
        if (!sHasBeamFeature) {
            // MONITOREXIT : android.nfc.NfcAdapter.class
            return false;
        }
        // MONITOREXIT : android.nfc.NfcAdapter.class
        if (object == null) throw new NullPointerException("activity may not be null.");
        this.enforceResumed((Activity)object);
        try {
            sService.invokeBeam();
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "invokeBeam: NFC process has died.");
            this.attemptDeadServiceRecovery(remoteException);
            return false;
        }
    }

    public boolean invokeBeam(BeamShareData beamShareData) {
        try {
            Log.e(TAG, "invokeBeamInternal()");
            sService.invokeBeamInternal(beamShareData);
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "invokeBeam: NFC process has died.");
            this.attemptDeadServiceRecovery(remoteException);
            return false;
        }
    }

    public boolean isEnabled() {
        boolean bl = false;
        try {
            int n = sService.getState();
            if (n == 3) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public boolean isNdefPushEnabled() {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
                throw unsupportedOperationException;
            }
            if (!sHasBeamFeature) {
                return false;
            }
        }
        try {
            return sService.isNdefPushEnabled();
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
            return false;
        }
    }

    public boolean isSecureNfcEnabled() {
        if (sHasNfcFeature) {
            try {
                boolean bl = sService.isNfcSecureEnabled();
                return bl;
            }
            catch (RemoteException remoteException) {
                this.attemptDeadServiceRecovery(remoteException);
                return false;
            }
        }
        throw new UnsupportedOperationException();
    }

    public boolean isSecureNfcSupported() {
        if (sHasNfcFeature) {
            try {
                boolean bl = sService.deviceSupportsNfcSecure();
                return bl;
            }
            catch (RemoteException remoteException) {
                this.attemptDeadServiceRecovery(remoteException);
                return false;
            }
        }
        throw new UnsupportedOperationException();
    }

    public void pausePolling(int n) {
        try {
            sService.pausePolling(n);
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public boolean removeNfcUnlockHandler(NfcUnlockHandler object) {
        synchronized (NfcAdapter.class) {
            if (!sHasNfcFeature) {
                object = new UnsupportedOperationException();
                throw object;
            }
        }
        try {
            Object object2 = this.mLock;
            synchronized (object2) {
                if (!this.mNfcUnlockHandlers.containsKey(object)) break block9;
            }
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
            return false;
        }
        {
            block9 : {
                sService.removeNfcUnlockHandler(this.mNfcUnlockHandlers.remove(object));
            }
            return true;
        }
    }

    public void resumePolling() {
        try {
            sService.resumePolling();
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Deprecated
    public void setBeamPushUris(Uri[] arruri, Activity activity) {
        // MONITORENTER : android.nfc.NfcAdapter.class
        if (!sHasNfcFeature) {
            arruri = new UnsupportedOperationException();
            throw arruri;
        }
        if (!sHasBeamFeature) {
            // MONITOREXIT : android.nfc.NfcAdapter.class
            return;
        }
        // MONITOREXIT : android.nfc.NfcAdapter.class
        if (activity == null) throw new NullPointerException("activity cannot be null");
        if (arruri != null) {
            int n = arruri.length;
            for (int i = 0; i < n; ++i) {
                Object object = arruri[i];
                if (object == null) throw new NullPointerException("Uri not allowed to be null");
                if ((object = ((Uri)object).getScheme()) == null) throw new IllegalArgumentException("URI needs to have either scheme file or scheme content");
                if (((String)object).equalsIgnoreCase("file")) continue;
                if (!((String)object).equalsIgnoreCase("content")) throw new IllegalArgumentException("URI needs to have either scheme file or scheme content");
            }
        }
        this.mNfcActivityManager.setNdefPushContentUri(activity, arruri);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Deprecated
    public void setBeamPushUrisCallback(CreateBeamUrisCallback object, Activity activity) {
        // MONITORENTER : android.nfc.NfcAdapter.class
        if (!sHasNfcFeature) {
            object = new UnsupportedOperationException();
            throw object;
        }
        if (!sHasBeamFeature) {
            // MONITOREXIT : android.nfc.NfcAdapter.class
            return;
        }
        // MONITOREXIT : android.nfc.NfcAdapter.class
        if (activity == null) throw new NullPointerException("activity cannot be null");
        this.mNfcActivityManager.setNdefPushContentUriCallback(activity, (CreateBeamUrisCallback)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @SystemApi
    public void setNdefPushMessage(NdefMessage object, Activity activity, int n) {
        // MONITORENTER : android.nfc.NfcAdapter.class
        if (sHasNfcFeature) {
            // MONITOREXIT : android.nfc.NfcAdapter.class
            if (activity == null) throw new NullPointerException("activity cannot be null");
            this.mNfcActivityManager.setNdefPushMessage(activity, (NdefMessage)object, n);
            return;
        }
        object = new UnsupportedOperationException();
        throw object;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void setNdefPushMessage(NdefMessage object, Activity activity2, Activity ... arractivity) {
        IllegalStateException illegalStateException2;
        int n;
        block13 : {
            synchronized (NfcAdapter.class) {
                if (!sHasNfcFeature) {
                    object = new UnsupportedOperationException();
                    throw object;
                }
                if (!sHasBeamFeature) {
                    return;
                }
            }
            n = this.getSdkVersion();
            if (activity2 != null) {
                try {
                    this.mNfcActivityManager.setNdefPushMessage(activity2, (NdefMessage)object, 0);
                    for (Activity activity2 : arractivity) {
                        if (activity2 != null) {
                            this.mNfcActivityManager.setNdefPushMessage(activity2, (NdefMessage)object, 0);
                            continue;
                        }
                        object = new NullPointerException("activities cannot contain null");
                        throw object;
                    }
                    return;
                }
                catch (IllegalStateException illegalStateException2) {
                    break block13;
                }
            }
            object = new NullPointerException("activity cannot be null");
            throw object;
        }
        if (n >= 16) throw illegalStateException2;
        Log.e(TAG, "Cannot call API with Activity that has already been destroyed", illegalStateException2);
    }

    @UnsupportedAppUsage
    public void setNdefPushMessageCallback(CreateNdefMessageCallback createNdefMessageCallback, Activity activity, int n) {
        if (activity != null) {
            this.mNfcActivityManager.setNdefPushMessageCallback(activity, createNdefMessageCallback, n);
            return;
        }
        throw new NullPointerException("activity cannot be null");
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void setNdefPushMessageCallback(CreateNdefMessageCallback object, Activity activity2, Activity ... arractivity) {
        IllegalStateException illegalStateException2;
        int n;
        block13 : {
            synchronized (NfcAdapter.class) {
                if (!sHasNfcFeature) {
                    object = new UnsupportedOperationException();
                    throw object;
                }
                if (!sHasBeamFeature) {
                    return;
                }
            }
            n = this.getSdkVersion();
            if (activity2 != null) {
                try {
                    this.mNfcActivityManager.setNdefPushMessageCallback(activity2, (CreateNdefMessageCallback)object, 0);
                    for (Activity activity2 : arractivity) {
                        if (activity2 != null) {
                            this.mNfcActivityManager.setNdefPushMessageCallback(activity2, (CreateNdefMessageCallback)object, 0);
                            continue;
                        }
                        object = new NullPointerException("activities cannot contain null");
                        throw object;
                    }
                    return;
                }
                catch (IllegalStateException illegalStateException2) {
                    break block13;
                }
            }
            object = new NullPointerException("activity cannot be null");
            throw object;
        }
        if (n >= 16) throw illegalStateException2;
        Log.e(TAG, "Cannot call API with Activity that has already been destroyed", illegalStateException2);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void setOnNdefPushCompleteCallback(OnNdefPushCompleteCallback object, Activity activity2, Activity ... arractivity) {
        IllegalStateException illegalStateException2;
        int n;
        block13 : {
            synchronized (NfcAdapter.class) {
                if (!sHasNfcFeature) {
                    object = new UnsupportedOperationException();
                    throw object;
                }
                if (!sHasBeamFeature) {
                    return;
                }
            }
            n = this.getSdkVersion();
            if (activity2 != null) {
                try {
                    this.mNfcActivityManager.setOnNdefPushCompleteCallback(activity2, (OnNdefPushCompleteCallback)object);
                    for (Activity activity2 : arractivity) {
                        if (activity2 != null) {
                            this.mNfcActivityManager.setOnNdefPushCompleteCallback(activity2, (OnNdefPushCompleteCallback)object);
                            continue;
                        }
                        object = new NullPointerException("activities cannot contain null");
                        throw object;
                    }
                    return;
                }
                catch (IllegalStateException illegalStateException2) {
                    break block13;
                }
            }
            object = new NullPointerException("activity cannot be null");
            throw object;
        }
        if (n >= 16) throw illegalStateException2;
        Log.e(TAG, "Cannot call API with Activity that has already been destroyed", illegalStateException2);
    }

    public void setP2pModes(int n, int n2) {
        try {
            sService.setP2pModes(n, n2);
        }
        catch (RemoteException remoteException) {
            this.attemptDeadServiceRecovery(remoteException);
        }
    }

    @Deprecated
    public static interface CreateBeamUrisCallback {
        public Uri[] createBeamUris(NfcEvent var1);
    }

    @Deprecated
    public static interface CreateNdefMessageCallback {
        public NdefMessage createNdefMessage(NfcEvent var1);
    }

    @SystemApi
    public static interface NfcUnlockHandler {
        public boolean onUnlockAttempted(Tag var1);
    }

    @Deprecated
    public static interface OnNdefPushCompleteCallback {
        public void onNdefPushComplete(NfcEvent var1);
    }

    public static interface OnTagRemovedListener {
        public void onTagRemoved();
    }

    public static interface ReaderCallback {
        public void onTagDiscovered(Tag var1);
    }

}

