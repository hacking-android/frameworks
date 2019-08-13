/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.wifi.WifiManager
 *  android.net.wifi.WifiManager$WifiLock
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.PowerManager
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.os.SystemClock
 *  android.telephony.Rlog
 *  javax.sip.SipException
 *  javax.sip.address.SipURI
 */
package com.android.server.sip;

import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.sip.ISipService;
import android.net.sip.ISipSession;
import android.net.sip.ISipSessionListener;
import android.net.sip.SipErrorCode;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipSessionAdapter;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.telephony.Rlog;
import com.android.server.sip.SipSessionGroup;
import com.android.server.sip.SipSessionListenerProxy;
import com.android.server.sip.SipWakeLock;
import com.android.server.sip.SipWakeupTimer;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.sip.SipException;
import javax.sip.address.SipURI;

public final class SipService
extends ISipService.Stub {
    static final boolean DBG = true;
    private static final int DEFAULT_KEEPALIVE_INTERVAL = 10;
    private static final int DEFAULT_MAX_KEEPALIVE_INTERVAL = 120;
    private static final int EXPIRY_TIME = 3600;
    private static final int MIN_EXPIRY_TIME = 60;
    private static final int SHORT_EXPIRY_TIME = 10;
    static final String TAG = "SipService";
    private final AppOpsManager mAppOps;
    private ConnectivityReceiver mConnectivityReceiver;
    private Context mContext;
    private MyExecutor mExecutor = new MyExecutor();
    private int mKeepAliveInterval;
    private int mLastGoodKeepAliveInterval = 10;
    private String mLocalIp;
    private SipWakeLock mMyWakeLock;
    private int mNetworkType = -1;
    private Map<String, ISipSession> mPendingSessions = new HashMap<String, ISipSession>();
    private Map<String, SipSessionGroupExt> mSipGroups = new HashMap<String, SipSessionGroupExt>();
    private SipKeepAliveProcessCallback mSipKeepAliveProcessCallback;
    private boolean mSipOnWifiOnly;
    private SipWakeupTimer mTimer;
    private WifiManager.WifiLock mWifiLock;

    private SipService(Context context) {
        this.log("SipService: started!");
        this.mContext = context;
        this.mConnectivityReceiver = new ConnectivityReceiver();
        this.mWifiLock = ((WifiManager)context.getSystemService("wifi")).createWifiLock(1, TAG);
        this.mWifiLock.setReferenceCounted(false);
        this.mSipOnWifiOnly = SipManager.isSipWifiOnly(context);
        this.mMyWakeLock = new SipWakeLock((PowerManager)context.getSystemService("power"));
        this.mTimer = new SipWakeupTimer(context, this.mExecutor);
        this.mAppOps = (AppOpsManager)this.mContext.getSystemService(AppOpsManager.class);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void addPendingSession(ISipSession object) {
        synchronized (this) {
            try {
                try {
                    this.cleanUpPendingSessions();
                    this.mPendingSessions.put(object.getCallId(), (ISipSession)object);
                    object = new StringBuilder();
                    ((StringBuilder)object).append("#pending sess=");
                    ((StringBuilder)object).append(this.mPendingSessions.size());
                    this.log(((StringBuilder)object).toString());
                }
                catch (RemoteException remoteException) {
                    this.loge("addPendingSession()", remoteException);
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    private boolean callingSelf(SipSessionGroupExt object, SipSessionGroup.SipSessionImpl sipSessionImpl) {
        synchronized (this) {
            String string = sipSessionImpl.getCallId();
            for (SipSessionGroupExt sipSessionGroupExt : this.mSipGroups.values()) {
                if (sipSessionGroupExt == object || !sipSessionGroupExt.containsSession(string)) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("call self: ");
                ((StringBuilder)object).append(sipSessionImpl.getLocalProfile().getUriString());
                ((StringBuilder)object).append(" -> ");
                ((StringBuilder)object).append(sipSessionGroupExt.getLocalProfile().getUriString());
                this.log(((StringBuilder)object).toString());
                return true;
            }
            return false;
        }
    }

    private boolean canUseSip(String string, String string2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.USE_SIP", string2);
        boolean bl = this.mAppOps.noteOp(53, Binder.getCallingUid(), string) == 0;
        return bl;
    }

    private void cleanUpPendingSessions() throws RemoteException {
        for (Map.Entry entry : this.mPendingSessions.entrySet().toArray(new Map.Entry[this.mPendingSessions.size()])) {
            if (((ISipSession)entry.getValue()).getState() == 3) continue;
            this.mPendingSessions.remove(entry.getKey());
        }
    }

    private SipSessionGroupExt createGroup(SipProfile object) throws SipException {
        block4 : {
            block3 : {
                SipSessionGroupExt sipSessionGroupExt;
                block2 : {
                    String string = ((SipProfile)object).getUriString();
                    sipSessionGroupExt = this.mSipGroups.get(string);
                    if (sipSessionGroupExt != null) break block2;
                    sipSessionGroupExt = new SipSessionGroupExt((SipProfile)object, null, null);
                    this.mSipGroups.put(string, sipSessionGroupExt);
                    this.notifyProfileAdded((SipProfile)object);
                    object = sipSessionGroupExt;
                    break block3;
                }
                if (!this.isCallerCreator(sipSessionGroupExt)) break block4;
                object = sipSessionGroupExt;
            }
            return object;
        }
        throw new SipException("only creator can access the profile");
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private SipSessionGroupExt createGroup(SipProfile object, PendingIntent object2, ISipSessionListener iSipSessionListener) throws SipException {
        void var3_4;
        String string = ((SipProfile)object).getUriString();
        SipSessionGroupExt sipSessionGroupExt = this.mSipGroups.get(string);
        if (sipSessionGroupExt != null) {
            if (!this.isCallerCreator(sipSessionGroupExt)) throw new SipException("only creator can access the profile");
            sipSessionGroupExt.setIncomingCallPendingIntent((PendingIntent)object2);
            sipSessionGroupExt.setListener((ISipSessionListener)var3_4);
            return sipSessionGroupExt;
        }
        SipSessionGroupExt sipSessionGroupExt2 = new SipSessionGroupExt((SipProfile)object, (PendingIntent)object2, (ISipSessionListener)var3_4);
        this.mSipGroups.put(string, sipSessionGroupExt2);
        this.notifyProfileAdded((SipProfile)object);
        return sipSessionGroupExt2;
    }

    private static Looper createLooper() {
        HandlerThread handlerThread = new HandlerThread("SipService.Executor");
        handlerThread.start();
        return handlerThread.getLooper();
    }

    private String determineLocalIp() {
        try {
            Object object = new DatagramSocket();
            ((DatagramSocket)object).connect(InetAddress.getByName("192.168.1.1"), 80);
            object = ((DatagramSocket)object).getLocalAddress().getHostAddress();
            return object;
        }
        catch (IOException iOException) {
            this.loge("determineLocalIp()", iOException);
            return null;
        }
    }

    private int getKeepAliveInterval() {
        int n;
        block0 : {
            n = this.mKeepAliveInterval;
            if (n >= 0) break block0;
            n = this.mLastGoodKeepAliveInterval;
        }
        return n;
    }

    private static boolean isAllowedCharacter(char c) {
        boolean bl = c == '@' || c == '.';
        return bl;
    }

    private boolean isBehindNAT(String string) {
        byte by;
        byte[] arrby;
        try {
            arrby = InetAddress.getByName(string).getAddress();
        }
        catch (UnknownHostException unknownHostException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isBehindAT()");
            stringBuilder.append(string);
            this.loge(stringBuilder.toString(), unknownHostException);
        }
        if (arrby[0] == 10 || (arrby[0] & 255) == 172 && (arrby[1] & 240) == 16 || (arrby[0] & 255) == 192 && ((by = arrby[1]) & 255) == 168) {
            return true;
        }
        return false;
    }

    private boolean isCallerCreator(SipSessionGroupExt sipSessionGroupExt) {
        boolean bl = sipSessionGroupExt.getLocalProfile().getCallingUid() == Binder.getCallingUid();
        return bl;
    }

    private boolean isCallerCreatorOrRadio(SipSessionGroupExt sipSessionGroupExt) {
        boolean bl = this.isCallerRadio() || this.isCallerCreator(sipSessionGroupExt);
        return bl;
    }

    private boolean isCallerRadio() {
        boolean bl = Binder.getCallingUid() == 1001;
        return bl;
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void loge(String string, Throwable throwable) {
        Rlog.e((String)TAG, (String)string, (Throwable)throwable);
    }

    private void notifyProfileAdded(SipProfile sipProfile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("notify: profile added: ");
        stringBuilder.append(sipProfile);
        this.log(stringBuilder.toString());
        stringBuilder = new Intent("com.android.phone.SIP_ADD_PHONE");
        stringBuilder.putExtra("android:localSipUri", sipProfile.getUriString());
        this.mContext.sendBroadcast((Intent)stringBuilder);
        if (this.mSipGroups.size() == 1) {
            this.registerReceivers();
        }
    }

    private void notifyProfileRemoved(SipProfile sipProfile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("notify: profile removed: ");
        stringBuilder.append(sipProfile);
        this.log(stringBuilder.toString());
        stringBuilder = new Intent("com.android.phone.SIP_REMOVE_PHONE");
        stringBuilder.putExtra("android:localSipUri", sipProfile.getUriString());
        this.mContext.sendBroadcast((Intent)stringBuilder);
        if (this.mSipGroups.size() == 0) {
            this.unregisterReceivers();
        }
    }

    public static String obfuscateSipUri(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        if ((string = string.trim()).startsWith("sip:")) {
            n = 4;
            stringBuilder.append("sip:");
        }
        int n2 = 0;
        int n3 = string.length();
        for (int i = n; i < n3; ++i) {
            int n4;
            int n5;
            block6 : {
                block5 : {
                    n4 = string.charAt(i);
                    char c = i + 1 < n3 ? string.charAt(i + 1) : (char)'\u0000';
                    int n6 = 42;
                    if (i - n < 1 || i + 1 == n3 || SipService.isAllowedCharacter((char)n4) || n2 == 64) break block5;
                    n5 = n6;
                    if (c != '@') break block6;
                }
                n5 = n2 = n4;
            }
            stringBuilder.append((char)n5);
            n2 = n4;
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onConnectivityChanged(NetworkInfo object) {
        synchronized (this) {
            Object object2;
            block16 : {
                block15 : {
                    if (object == null || object.isConnected()) break block15;
                    object2 = object;
                    if (object.getType() == this.mNetworkType) break block16;
                }
                object2 = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
            }
            int n = object2 != null && object2.isConnected() ? object2.getType() : -1;
            int n2 = n;
            if (this.mSipOnWifiOnly) {
                n2 = n;
                if (n != 1) {
                    n2 = -1;
                }
            }
            if ((n = this.mNetworkType) == n2) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("onConnectivityChanged: ");
            ((StringBuilder)object).append(this.mNetworkType);
            ((StringBuilder)object).append(" -> ");
            ((StringBuilder)object).append(n2);
            this.log(((StringBuilder)object).toString());
            try {
                if (this.mNetworkType != -1) {
                    this.mLocalIp = null;
                    this.stopPortMappingMeasurement();
                    object = this.mSipGroups.values().iterator();
                    while (object.hasNext()) {
                        ((SipSessionGroupExt)object.next()).onConnectivityChanged(false);
                    }
                }
                this.mNetworkType = n2;
                if (this.mNetworkType != -1) {
                    this.mLocalIp = this.determineLocalIp();
                    this.mKeepAliveInterval = -1;
                    this.mLastGoodKeepAliveInterval = 10;
                    object = this.mSipGroups.values().iterator();
                    while (object.hasNext()) {
                        ((SipSessionGroupExt)object.next()).onConnectivityChanged(true);
                    }
                }
                this.updateWakeLocks();
            }
            catch (SipException sipException) {
                this.loge("onConnectivityChanged()", sipException);
            }
            return;
        }
    }

    private void onKeepAliveIntervalChanged() {
        synchronized (this) {
            Iterator<SipSessionGroupExt> iterator = this.mSipGroups.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().onKeepAliveIntervalChanged();
            }
            return;
        }
    }

    private void registerReceivers() {
        this.mContext.registerReceiver((BroadcastReceiver)this.mConnectivityReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.log("registerReceivers:");
    }

    private void restartPortMappingLifetimeMeasurement(SipProfile sipProfile, int n) {
        this.stopPortMappingMeasurement();
        this.mKeepAliveInterval = -1;
        this.startPortMappingLifetimeMeasurement(sipProfile, n);
    }

    private static void slog(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    public static void start(Context context) {
        if (SipManager.isApiSupported(context) && ServiceManager.getService((String)"sip") == null) {
            ServiceManager.addService((String)"sip", (IBinder)new SipService(context));
            context.sendBroadcast(new Intent("android.net.sip.SIP_SERVICE_UP"));
            SipService.slog("start:");
        }
    }

    private void startPortMappingLifetimeMeasurement(SipProfile sipProfile) {
        this.startPortMappingLifetimeMeasurement(sipProfile, 120);
    }

    private void startPortMappingLifetimeMeasurement(SipProfile sipProfile, int n) {
        if (this.mSipKeepAliveProcessCallback == null && this.mKeepAliveInterval == -1 && this.isBehindNAT(this.mLocalIp)) {
            int n2;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("startPortMappingLifetimeMeasurement: profile=");
            stringBuilder.append(sipProfile.getUriString());
            this.log(stringBuilder.toString());
            int n3 = n2 = this.mLastGoodKeepAliveInterval;
            if (n2 >= n) {
                this.mLastGoodKeepAliveInterval = 10;
                n3 = 10;
                stringBuilder = new StringBuilder();
                stringBuilder.append("  reset min interval to ");
                stringBuilder.append(n3);
                this.log(stringBuilder.toString());
            }
            this.mSipKeepAliveProcessCallback = new SipKeepAliveProcessCallback(sipProfile, n3, n);
            this.mSipKeepAliveProcessCallback.start();
        }
    }

    private void stopPortMappingMeasurement() {
        SipKeepAliveProcessCallback sipKeepAliveProcessCallback = this.mSipKeepAliveProcessCallback;
        if (sipKeepAliveProcessCallback != null) {
            sipKeepAliveProcessCallback.stop();
            this.mSipKeepAliveProcessCallback = null;
        }
    }

    private void unregisterReceivers() {
        this.mContext.unregisterReceiver((BroadcastReceiver)this.mConnectivityReceiver);
        this.log("unregisterReceivers:");
        this.mWifiLock.release();
        this.mNetworkType = -1;
    }

    private void updateWakeLocks() {
        Iterator<SipSessionGroupExt> iterator = this.mSipGroups.values().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isOpenedToReceiveCalls()) continue;
            int n = this.mNetworkType;
            if (n != 1 && n != -1) {
                this.mWifiLock.release();
            } else {
                this.mWifiLock.acquire();
            }
            return;
        }
        this.mWifiLock.release();
        this.mMyWakeLock.reset();
    }

    @Override
    public void close(String object, String object2) {
        synchronized (this) {
            block7 : {
                block6 : {
                    boolean bl = this.canUseSip((String)object2, "close");
                    if (bl) break block6;
                    return;
                }
                object2 = this.mSipGroups.get(object);
                if (object2 != null) break block7;
                return;
            }
            if (!this.isCallerCreatorOrRadio((SipSessionGroupExt)object2)) {
                this.log("only creator or radio can close this profile");
                return;
            }
            object = this.mSipGroups.remove(object);
            this.notifyProfileRemoved(((SipSessionGroupExt)object).getLocalProfile());
            ((SipSessionGroupExt)object).close();
            this.updateWakeLocks();
            return;
        }
    }

    @Override
    public ISipSession createSession(SipProfile object, ISipSessionListener iSipSessionListener, String string) {
        synchronized (this) {
            block9 : {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("createSession: profile");
                stringBuilder.append(object);
                this.log(stringBuilder.toString());
                boolean bl = this.canUseSip(string, "createSession");
                if (bl) break block9;
                return null;
            }
            ((SipProfile)object).setCallingUid(Binder.getCallingUid());
            if (this.mNetworkType == -1) {
                this.log("createSession: mNetworkType==-1 ret=null");
                return null;
            }
            object = this.createGroup((SipProfile)object).createSession(iSipSessionListener);
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SipProfile[] getListOfProfiles(String arrsipProfile) {
        synchronized (this) {
            if (!this.canUseSip((String)arrsipProfile, "getListOfProfiles")) {
                return new SipProfile[0];
            }
            boolean bl = this.isCallerRadio();
            ArrayList<SipProfile> arrayList = new ArrayList<SipProfile>();
            Iterator<SipSessionGroupExt> iterator = this.mSipGroups.values().iterator();
            while (iterator.hasNext()) {
                SipSessionGroupExt sipSessionGroupExt = iterator.next();
                if (!bl && !this.isCallerCreator(sipSessionGroupExt)) continue;
                arrayList.add(sipSessionGroupExt.getLocalProfile());
            }
            return arrayList.toArray(new SipProfile[arrayList.size()]);
        }
    }

    @Override
    public ISipSession getPendingSession(String object, String string) {
        synchronized (this) {
            block5 : {
                boolean bl = this.canUseSip(string, "getPendingSession");
                if (bl) break block5;
                return null;
            }
            if (object == null) {
                return null;
            }
            object = this.mPendingSessions.get(object);
            return object;
        }
    }

    @Override
    public boolean isOpened(String object, String string) {
        synchronized (this) {
            block8 : {
                boolean bl;
                block7 : {
                    block6 : {
                        bl = this.canUseSip(string, "isOpened");
                        if (bl) break block6;
                        return false;
                    }
                    object = this.mSipGroups.get(object);
                    if (object != null) break block7;
                    return false;
                }
                bl = this.isCallerCreatorOrRadio((SipSessionGroupExt)object);
                if (!bl) break block8;
                return true;
            }
            this.log("only creator or radio can query on the profile");
            return false;
        }
    }

    @Override
    public boolean isRegistered(String object, String string) {
        synchronized (this) {
            block8 : {
                boolean bl;
                block7 : {
                    block6 : {
                        bl = this.canUseSip(string, "isRegistered");
                        if (bl) break block6;
                        return false;
                    }
                    object = this.mSipGroups.get(object);
                    if (object != null) break block7;
                    return false;
                }
                if (!this.isCallerCreatorOrRadio((SipSessionGroupExt)object)) break block8;
                bl = ((SipSessionGroupExt)object).isRegistered();
                return bl;
            }
            this.log("only creator or radio can query on the profile");
            return false;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void open(SipProfile sipProfile, String string) {
        synchronized (this) {
            void var2_3;
            boolean bl = this.canUseSip((String)var2_3, "open");
            if (!bl) {
                return;
            }
            sipProfile.setCallingUid(Binder.getCallingUid());
            try {
                this.createGroup(sipProfile);
            }
            catch (SipException sipException) {
                this.loge("openToMakeCalls()", sipException);
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void open3(SipProfile sipProfile, PendingIntent object, ISipSessionListener iSipSessionListener, String charSequence) {
        synchronized (this) {
            SipSessionGroupExt sipSessionGroupExt;
            StringBuilder stringBuilder;
            void var3_4;
            boolean bl = this.canUseSip((String)((Object)stringBuilder), "open3");
            if (!bl) {
                return;
            }
            sipProfile.setCallingUid(Binder.getCallingUid());
            if (sipSessionGroupExt == null) {
                this.log("open3: incomingCallPendingIntent cannot be null; the profile is not opened");
                return;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("open3: ");
            stringBuilder.append(SipService.obfuscateSipUri(sipProfile.getUriString()));
            stringBuilder.append(": ");
            stringBuilder.append(sipSessionGroupExt);
            stringBuilder.append(": ");
            stringBuilder.append(var3_4);
            this.log(stringBuilder.toString());
            try {
                sipSessionGroupExt = this.createGroup(sipProfile, (PendingIntent)sipSessionGroupExt, (ISipSessionListener)var3_4);
                if (sipProfile.getAutoRegistration()) {
                    sipSessionGroupExt.openToReceiveCalls();
                    this.updateWakeLocks();
                }
            }
            catch (SipException sipException) {
                this.loge("open3:", sipException);
            }
            return;
        }
    }

    @Override
    public void setRegistrationListener(String object, ISipSessionListener iSipSessionListener, String string) {
        synchronized (this) {
            block8 : {
                block7 : {
                    boolean bl = this.canUseSip(string, "setRegistrationListener");
                    if (bl) break block7;
                    return;
                }
                object = this.mSipGroups.get(object);
                if (object != null) break block8;
                return;
            }
            if (this.isCallerCreator((SipSessionGroupExt)object)) {
                ((SipSessionGroupExt)object).setListener(iSipSessionListener);
            } else {
                this.log("only creator can set listener on the profile");
            }
            return;
        }
    }

    private class ConnectivityReceiver
    extends BroadcastReceiver {
        private ConnectivityReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            context = intent.getExtras();
            if (context != null) {
                context = (NetworkInfo)context.get("networkInfo");
                SipService.this.mExecutor.execute(new Runnable((NetworkInfo)context){
                    final /* synthetic */ NetworkInfo val$info;
                    {
                        this.val$info = networkInfo;
                    }

                    @Override
                    public void run() {
                        SipService.this.onConnectivityChanged(this.val$info);
                    }
                });
            }
        }

    }

    private class MyExecutor
    extends Handler
    implements Executor {
        MyExecutor() {
            super(SipService.createLooper());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void executeInternal(Runnable runnable) {
            try {
                runnable.run();
                return;
            }
            catch (Throwable throwable) {
                try {
                    SipService sipService = SipService.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("run task: ");
                    stringBuilder.append(runnable);
                    sipService.loge(stringBuilder.toString(), throwable);
                    return;
                }
                finally {
                    SipService.this.mMyWakeLock.release(runnable);
                }
            }
        }

        @Override
        public void execute(Runnable runnable) {
            SipService.this.mMyWakeLock.acquire(runnable);
            Message.obtain((Handler)this, (int)0, (Object)runnable).sendToTarget();
        }

        public void handleMessage(Message message) {
            if (message.obj instanceof Runnable) {
                this.executeInternal((Runnable)message.obj);
            } else {
                SipService sipService = SipService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handleMessage: not Runnable ignore msg=");
                stringBuilder.append((Object)message);
                sipService.log(stringBuilder.toString());
            }
        }
    }

    private class SipAutoReg
    extends SipSessionAdapter
    implements Runnable,
    SipSessionGroup.KeepAliveProcessCallback {
        private static final int MIN_KEEPALIVE_SUCCESS_COUNT = 10;
        private static final boolean SAR_DBG = true;
        private String SAR_TAG;
        private int mBackoff = 1;
        private int mErrorCode;
        private String mErrorMessage;
        private long mExpiryTime;
        private SipSessionGroup.SipSessionImpl mKeepAliveSession;
        private int mKeepAliveSuccessCount = 0;
        private SipSessionListenerProxy mProxy = new SipSessionListenerProxy();
        private boolean mRegistered;
        private boolean mRunning = false;
        private SipSessionGroup.SipSessionImpl mSession;

        private SipAutoReg() {
        }

        private int backoffDuration() {
            int n = this.mBackoff;
            int n2 = n * 10;
            if (n2 > 3600) {
                n2 = 3600;
            } else {
                this.mBackoff = n * 2;
            }
            return n2;
        }

        private void log(String string) {
            Rlog.d((String)this.SAR_TAG, (String)string);
        }

        private void loge(String string) {
            Rlog.e((String)this.SAR_TAG, (String)string);
        }

        private void loge(String string, Throwable throwable) {
            Rlog.e((String)this.SAR_TAG, (String)string, (Throwable)throwable);
        }

        private boolean notCurrentSession(ISipSession iSipSession) {
            if (iSipSession != this.mSession) {
                ((SipSessionGroup.SipSessionImpl)iSipSession).setListener(null);
                SipService.this.mMyWakeLock.release(iSipSession);
                return true;
            }
            return this.mRunning ^ true;
        }

        private void restart(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("restart: duration=");
            stringBuilder.append(n);
            stringBuilder.append("s later.");
            this.log(stringBuilder.toString());
            SipService.this.mTimer.cancel(this);
            SipService.this.mTimer.set(n * 1000, this);
        }

        private void restartLater() {
            this.loge("restartLater");
            this.mRegistered = false;
            this.restart(this.backoffDuration());
        }

        private void startKeepAliveProcess(int n) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("startKeepAliveProcess: interval=");
            ((StringBuilder)object).append(n);
            this.log(((StringBuilder)object).toString());
            object = this.mKeepAliveSession;
            if (object == null) {
                this.mKeepAliveSession = this.mSession.duplicate();
            } else {
                ((SipSessionGroup.SipSessionImpl)object).stopKeepAliveProcess();
            }
            try {
                this.mKeepAliveSession.startKeepAliveProcess(n, this);
            }
            catch (SipException sipException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("startKeepAliveProcess: interval=");
                stringBuilder.append(n);
                this.loge(stringBuilder.toString(), sipException);
            }
        }

        private void stopKeepAliveProcess() {
            SipSessionGroup.SipSessionImpl sipSessionImpl = this.mKeepAliveSession;
            if (sipSessionImpl != null) {
                sipSessionImpl.stopKeepAliveProcess();
                this.mKeepAliveSession = null;
            }
            this.mKeepAliveSuccessCount = 0;
        }

        public boolean isRegistered() {
            return this.mRegistered;
        }

        @Override
        public void onError(int n, String string) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError: errorCode=");
            stringBuilder.append(n);
            stringBuilder.append(" desc=");
            stringBuilder.append(string);
            this.loge(stringBuilder.toString());
            this.onResponse(true);
        }

        public void onKeepAliveIntervalChanged() {
            if (this.mKeepAliveSession != null) {
                int n = SipService.this.getKeepAliveInterval();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onKeepAliveIntervalChanged: interval=");
                stringBuilder.append(n);
                this.log(stringBuilder.toString());
                this.mKeepAliveSuccessCount = 0;
                this.startKeepAliveProcess(n);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onRegistering(ISipSession iSipSession) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onRegistering: ");
            ((StringBuilder)object).append(iSipSession);
            this.log(((StringBuilder)object).toString());
            object = SipService.this;
            synchronized (object) {
                if (this.notCurrentSession(iSipSession)) {
                    return;
                }
                this.mRegistered = false;
                this.mProxy.onRegistering(iSipSession);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onRegistrationDone(ISipSession iSipSession, int n) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onRegistrationDone: ");
            ((StringBuilder)object).append(iSipSession);
            this.log(((StringBuilder)object).toString());
            object = SipService.this;
            synchronized (object) {
                if (this.notCurrentSession(iSipSession)) {
                    return;
                }
                this.mProxy.onRegistrationDone(iSipSession, n);
                if (n > 0) {
                    this.mExpiryTime = SystemClock.elapsedRealtime() + (long)(n * 1000);
                    if (!this.mRegistered) {
                        int n2;
                        this.mRegistered = true;
                        n = n2 = n - 60;
                        if (n2 < 60) {
                            n = 60;
                        }
                        this.restart(n);
                        SipProfile sipProfile = this.mSession.getLocalProfile();
                        if (this.mKeepAliveSession == null && (SipService.this.isBehindNAT(SipService.this.mLocalIp) || sipProfile.getSendKeepAlive())) {
                            this.startKeepAliveProcess(SipService.this.getKeepAliveInterval());
                        }
                    }
                    SipService.this.mMyWakeLock.release(iSipSession);
                } else {
                    this.mRegistered = false;
                    this.mExpiryTime = -1L;
                    this.log("Refresh registration immediately");
                    this.run();
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
        public void onRegistrationFailed(ISipSession iSipSession, int n, String string) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onRegistrationFailed: ");
            ((StringBuilder)object).append(iSipSession);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(SipErrorCode.toString(n));
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(string);
            this.log(((StringBuilder)object).toString());
            object = SipService.this;
            synchronized (object) {
                if (this.notCurrentSession(iSipSession)) {
                    return;
                }
                if (n != -12 && n != -8) {
                    this.restartLater();
                } else {
                    this.log("   pause auto-registration");
                    this.stop();
                }
                this.mErrorCode = n;
                this.mErrorMessage = string;
                this.mProxy.onRegistrationFailed(iSipSession, n, string);
                SipService.this.mMyWakeLock.release(iSipSession);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onRegistrationTimeout(ISipSession iSipSession) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onRegistrationTimeout: ");
            ((StringBuilder)object).append(iSipSession);
            this.log(((StringBuilder)object).toString());
            object = SipService.this;
            synchronized (object) {
                if (this.notCurrentSession(iSipSession)) {
                    return;
                }
                this.mErrorCode = -5;
                this.mProxy.onRegistrationTimeout(iSipSession);
                this.restartLater();
                SipService.this.mMyWakeLock.release(iSipSession);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onResponse(boolean bl) {
            SipService sipService = SipService.this;
            synchronized (sipService) {
                if (bl) {
                    int n = SipService.this.getKeepAliveInterval();
                    if (this.mKeepAliveSuccessCount < 10) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("onResponse: keepalive doesn't work with interval ");
                        stringBuilder.append(n);
                        stringBuilder.append(", past success count=");
                        stringBuilder.append(this.mKeepAliveSuccessCount);
                        this.log(stringBuilder.toString());
                        if (n > 10) {
                            SipService.this.restartPortMappingLifetimeMeasurement(this.mSession.getLocalProfile(), n);
                            this.mKeepAliveSuccessCount = 0;
                        }
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("keep keepalive going with interval ");
                        stringBuilder.append(n);
                        stringBuilder.append(", past success count=");
                        stringBuilder.append(this.mKeepAliveSuccessCount);
                        this.log(stringBuilder.toString());
                        this.mKeepAliveSuccessCount /= 2;
                    }
                } else {
                    SipService.this.startPortMappingLifetimeMeasurement(this.mSession.getLocalProfile());
                    ++this.mKeepAliveSuccessCount;
                }
                if (this.mRunning && bl) {
                    this.mKeepAliveSession = null;
                    SipService.this.mMyWakeLock.acquire(this.mSession);
                    this.mSession.register(3600);
                    return;
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
        public void run() {
            SipService sipService = SipService.this;
            synchronized (sipService) {
                if (!this.mRunning) {
                    return;
                }
                this.mErrorCode = 0;
                this.mErrorMessage = null;
                this.log("run: registering");
                if (SipService.this.mNetworkType != -1) {
                    SipService.this.mMyWakeLock.acquire(this.mSession);
                    this.mSession.register(3600);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setListener(ISipSessionListener iSipSessionListener) {
            SipService sipService = SipService.this;
            synchronized (sipService) {
                this.mProxy.setListener(iSipSessionListener);
                try {
                    int n = this.mSession == null ? 0 : this.mSession.getState();
                    if (n != 1 && n != 2) {
                        if (this.mRegistered) {
                            n = (int)(this.mExpiryTime - SystemClock.elapsedRealtime());
                            this.mProxy.onRegistrationDone(this.mSession, n);
                        } else if (this.mErrorCode != 0) {
                            if (this.mErrorCode == -5) {
                                this.mProxy.onRegistrationTimeout(this.mSession);
                            } else {
                                this.mProxy.onRegistrationFailed(this.mSession, this.mErrorCode, this.mErrorMessage);
                            }
                        } else if (SipService.this.mNetworkType == -1) {
                            this.mProxy.onRegistrationFailed(this.mSession, -10, "no data connection");
                        } else if (!this.mRunning) {
                            this.mProxy.onRegistrationFailed(this.mSession, -4, "registration not running");
                        } else {
                            this.mProxy.onRegistrationFailed(this.mSession, -9, String.valueOf(n));
                        }
                    } else {
                        this.mProxy.onRegistering(this.mSession);
                    }
                }
                catch (Throwable throwable) {
                    this.loge("setListener: ", throwable);
                }
                return;
            }
        }

        public void start(SipSessionGroup sipSessionGroup) {
            if (!this.mRunning) {
                this.mRunning = true;
                this.mBackoff = 1;
                this.mSession = (SipSessionGroup.SipSessionImpl)sipSessionGroup.createSession(this);
                if (this.mSession == null) {
                    return;
                }
                SipService.this.mMyWakeLock.acquire(this.mSession);
                this.mSession.unregister();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("SipAutoReg:");
                stringBuilder.append(SipService.obfuscateSipUri(this.mSession.getLocalProfile().getUriString()));
                this.SAR_TAG = stringBuilder.toString();
                stringBuilder = new StringBuilder();
                stringBuilder.append("start: group=");
                stringBuilder.append(sipSessionGroup);
                this.log(stringBuilder.toString());
            }
        }

        public void stop() {
            if (!this.mRunning) {
                return;
            }
            this.mRunning = false;
            SipService.this.mMyWakeLock.release(this.mSession);
            SipSessionGroup.SipSessionImpl sipSessionImpl = this.mSession;
            if (sipSessionImpl != null) {
                sipSessionImpl.setListener(null);
                if (SipService.this.mNetworkType != -1 && this.mRegistered) {
                    this.mSession.unregister();
                }
            }
            SipService.this.mTimer.cancel(this);
            this.stopKeepAliveProcess();
            this.mRegistered = false;
            this.setListener(this.mProxy.getListener());
        }
    }

    private class SipKeepAliveProcessCallback
    implements Runnable,
    SipSessionGroup.KeepAliveProcessCallback {
        private static final int MIN_INTERVAL = 5;
        private static final int NAT_MEASUREMENT_RETRY_INTERVAL = 120;
        private static final int PASS_THRESHOLD = 10;
        private static final boolean SKAI_DBG = true;
        private static final String SKAI_TAG = "SipKeepAliveProcessCallback";
        private SipSessionGroupExt mGroup;
        private int mInterval;
        private SipProfile mLocalProfile;
        private int mMaxInterval;
        private int mMinInterval;
        private int mPassCount;
        private SipSessionGroup.SipSessionImpl mSession;

        public SipKeepAliveProcessCallback(SipProfile sipProfile, int n, int n2) {
            this.mMaxInterval = n2;
            this.mMinInterval = n;
            this.mLocalProfile = sipProfile;
        }

        private boolean checkTermination() {
            boolean bl = this.mMaxInterval - this.mMinInterval < 5;
            return bl;
        }

        private void log(String string) {
            Rlog.d((String)SKAI_TAG, (String)string);
        }

        private void loge(String string) {
            Rlog.d((String)SKAI_TAG, (String)string);
        }

        private void loge(String string, Throwable throwable) {
            Rlog.d((String)SKAI_TAG, (String)string, (Throwable)throwable);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void restart() {
            SipService sipService = SipService.this;
            synchronized (sipService) {
                if (this.mSession == null) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("restart: interval=");
                stringBuilder.append(this.mInterval);
                this.log(stringBuilder.toString());
                try {
                    this.mSession.stopKeepAliveProcess();
                    this.mPassCount = 0;
                    this.mSession.startKeepAliveProcess(this.mInterval, this);
                }
                catch (SipException sipException) {
                    this.loge("restart", sipException);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void restartLater() {
            SipService sipService = SipService.this;
            synchronized (sipService) {
                SipService.this.mTimer.cancel(this);
                SipService.this.mTimer.set(120 * 1000, this);
                return;
            }
        }

        @Override
        public void onError(int n, String string) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError: errorCode=");
            stringBuilder.append(n);
            stringBuilder.append(" desc=");
            stringBuilder.append(string);
            this.loge(stringBuilder.toString());
            this.restartLater();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onResponse(boolean bl) {
            SipService sipService = SipService.this;
            synchronized (sipService) {
                Object object;
                if (!bl) {
                    int n;
                    this.mPassCount = n = this.mPassCount + 1;
                    if (n != 10) {
                        return;
                    }
                    if (SipService.this.mKeepAliveInterval > 0) {
                        SipService.this.mLastGoodKeepAliveInterval = SipService.this.mKeepAliveInterval;
                    }
                    object = SipService.this;
                    this.mMinInterval = n = this.mInterval;
                    ((SipService)object).mKeepAliveInterval = n;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onResponse: portChanged=");
                    ((StringBuilder)object).append(bl);
                    ((StringBuilder)object).append(" mKeepAliveInterval=");
                    ((StringBuilder)object).append(SipService.this.mKeepAliveInterval);
                    this.log(((StringBuilder)object).toString());
                    SipService.this.onKeepAliveIntervalChanged();
                } else {
                    this.mMaxInterval = this.mInterval;
                }
                if (this.checkTermination()) {
                    this.stop();
                    SipService.this.mKeepAliveInterval = this.mMinInterval;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onResponse: checkTermination mKeepAliveInterval=");
                    ((StringBuilder)object).append(SipService.this.mKeepAliveInterval);
                    this.log(((StringBuilder)object).toString());
                } else {
                    this.mInterval = (this.mMaxInterval + this.mMinInterval) / 2;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onResponse: mKeepAliveInterval=");
                    ((StringBuilder)object).append(SipService.this.mKeepAliveInterval);
                    ((StringBuilder)object).append(", new mInterval=");
                    ((StringBuilder)object).append(this.mInterval);
                    this.log(((StringBuilder)object).toString());
                    this.restart();
                }
                return;
            }
        }

        @Override
        public void run() {
            SipService.this.mTimer.cancel(this);
            this.restart();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void start() {
            SipService sipService = SipService.this;
            synchronized (sipService) {
                boolean bl;
                if (this.mSession != null) {
                    return;
                }
                this.mInterval = (this.mMaxInterval + this.mMinInterval) / 2;
                this.mPassCount = 0;
                if (this.mInterval >= 10 && !(bl = this.checkTermination())) {
                    try {
                        Object object = new StringBuilder();
                        ((StringBuilder)object).append("start: interval=");
                        ((StringBuilder)object).append(this.mInterval);
                        this.log(((StringBuilder)object).toString());
                        object = this.mGroup = (object = new SipSessionGroupExt(this.mLocalProfile, null, null));
                        SipWakeupTimer sipWakeupTimer = new SipWakeupTimer(SipService.this.mContext, SipService.this.mExecutor);
                        ((SipSessionGroupExt)object).setWakeupTimer(sipWakeupTimer);
                        this.mSession = (SipSessionGroup.SipSessionImpl)this.mGroup.createSession(null);
                        this.mSession.startKeepAliveProcess(this.mInterval, this);
                    }
                    catch (Throwable throwable) {
                        this.onError(-4, throwable.toString());
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("start: measurement aborted; interval=[");
                stringBuilder.append(this.mMinInterval);
                stringBuilder.append(",");
                stringBuilder.append(this.mMaxInterval);
                stringBuilder.append("]");
                this.log(stringBuilder.toString());
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void stop() {
            SipService sipService = SipService.this;
            synchronized (sipService) {
                if (this.mSession != null) {
                    this.mSession.stopKeepAliveProcess();
                    this.mSession = null;
                }
                if (this.mGroup != null) {
                    this.mGroup.close();
                    this.mGroup = null;
                }
                SipService.this.mTimer.cancel(this);
                this.log("stop");
                return;
            }
        }
    }

    private class SipSessionGroupExt
    extends SipSessionAdapter {
        private static final boolean SSGE_DBG = true;
        private static final String SSGE_TAG = "SipSessionGroupExt";
        private SipAutoReg mAutoRegistration;
        private PendingIntent mIncomingCallPendingIntent;
        private boolean mOpenedToReceiveCalls;
        private SipSessionGroup mSipGroup;

        public SipSessionGroupExt(SipProfile sipProfile, PendingIntent pendingIntent, ISipSessionListener iSipSessionListener) throws SipException {
            this.mAutoRegistration = new SipAutoReg();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SipSessionGroupExt: profile=");
            stringBuilder.append(sipProfile);
            this.log(stringBuilder.toString());
            this.mSipGroup = new SipSessionGroup(this.duplicate(sipProfile), sipProfile.getPassword(), SipService.this.mTimer, SipService.this.mMyWakeLock);
            this.mIncomingCallPendingIntent = pendingIntent;
            this.mAutoRegistration.setListener(iSipSessionListener);
        }

        private SipProfile duplicate(SipProfile sipProfile) {
            try {
                SipProfile.Builder builder = new SipProfile.Builder(sipProfile);
                sipProfile = builder.setPassword("*").build();
                return sipProfile;
            }
            catch (Exception exception) {
                this.loge("duplicate()", exception);
                throw new RuntimeException("duplicate profile", exception);
            }
        }

        private String getUri() {
            return this.mSipGroup.getLocalProfileUri();
        }

        private void log(String string) {
            Rlog.d((String)SSGE_TAG, (String)string);
        }

        private void loge(String string, Throwable throwable) {
            Rlog.e((String)SSGE_TAG, (String)string, (Throwable)throwable);
        }

        public void close() {
            this.mOpenedToReceiveCalls = false;
            this.mSipGroup.close();
            this.mAutoRegistration.stop();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("close: ");
            stringBuilder.append(SipService.obfuscateSipUri(this.getUri()));
            stringBuilder.append(": ");
            stringBuilder.append((Object)this.mIncomingCallPendingIntent);
            this.log(stringBuilder.toString());
        }

        public boolean containsSession(String string) {
            return this.mSipGroup.containsSession(string);
        }

        public ISipSession createSession(ISipSessionListener iSipSessionListener) {
            this.log("createSession");
            return this.mSipGroup.createSession(iSipSessionListener);
        }

        public SipProfile getLocalProfile() {
            return this.mSipGroup.getLocalProfile();
        }

        public boolean isOpenedToReceiveCalls() {
            return this.mOpenedToReceiveCalls;
        }

        public boolean isRegistered() {
            return this.mAutoRegistration.isRegistered();
        }

        public void onConnectivityChanged(boolean bl) throws SipException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onConnectivityChanged: connected=");
            stringBuilder.append(bl);
            stringBuilder.append(" uri=");
            stringBuilder.append(SipService.obfuscateSipUri(this.getUri()));
            stringBuilder.append(": ");
            stringBuilder.append((Object)this.mIncomingCallPendingIntent);
            this.log(stringBuilder.toString());
            this.mSipGroup.onConnectivityChanged();
            if (bl) {
                this.mSipGroup.reset();
                if (this.mOpenedToReceiveCalls) {
                    this.openToReceiveCalls();
                }
            } else {
                this.mSipGroup.close();
                this.mAutoRegistration.stop();
            }
        }

        @Override
        public void onError(ISipSession object, int n, String string) {
            object = new StringBuilder();
            ((StringBuilder)object).append("onError: errorCode=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" desc=");
            ((StringBuilder)object).append(SipErrorCode.toString(n));
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(string);
            this.log(((StringBuilder)object).toString());
        }

        public void onKeepAliveIntervalChanged() {
            this.mAutoRegistration.onKeepAliveIntervalChanged();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onRinging(ISipSession object, SipProfile sipProfile, String charSequence) {
            SipSessionGroup.SipSessionImpl sipSessionImpl = (SipSessionGroup.SipSessionImpl)object;
            object = SipService.this;
            synchronized (object) {
                try {
                    try {
                        if (!this.isRegistered() || SipService.this.callingSelf(this, sipSessionImpl)) {
                            this.log("onRinging: end notReg or self");
                            sipSessionImpl.endCall();
                            return;
                        }
                        SipService.this.addPendingSession(sipSessionImpl);
                        Intent intent = SipManager.createIncomingCallBroadcast(sipSessionImpl.getCallId(), (String)charSequence);
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("onRinging: uri=");
                        ((StringBuilder)charSequence).append(this.getUri());
                        ((StringBuilder)charSequence).append(": ");
                        ((StringBuilder)charSequence).append((Object)sipProfile.getUri());
                        ((StringBuilder)charSequence).append(": ");
                        ((StringBuilder)charSequence).append(sipSessionImpl.getCallId());
                        ((StringBuilder)charSequence).append(" ");
                        ((StringBuilder)charSequence).append((Object)this.mIncomingCallPendingIntent);
                        this.log(((StringBuilder)charSequence).toString());
                        this.mIncomingCallPendingIntent.send(SipService.this.mContext, 101, intent);
                    }
                    catch (PendingIntent.CanceledException canceledException) {
                        this.loge("onRinging: pendingIntent is canceled, drop incoming call", canceledException);
                        sipSessionImpl.endCall();
                    }
                    return;
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        public void openToReceiveCalls() {
            this.mOpenedToReceiveCalls = true;
            if (SipService.this.mNetworkType != -1) {
                this.mSipGroup.openToReceiveCalls(this);
                this.mAutoRegistration.start(this.mSipGroup);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("openToReceiveCalls: ");
            stringBuilder.append(SipService.obfuscateSipUri(this.getUri()));
            stringBuilder.append(": ");
            stringBuilder.append((Object)this.mIncomingCallPendingIntent);
            this.log(stringBuilder.toString());
        }

        public void setIncomingCallPendingIntent(PendingIntent pendingIntent) {
            this.mIncomingCallPendingIntent = pendingIntent;
        }

        public void setListener(ISipSessionListener iSipSessionListener) {
            this.mAutoRegistration.setListener(iSipSessionListener);
        }

        void setWakeupTimer(SipWakeupTimer sipWakeupTimer) {
            this.mSipGroup.setWakeupTimer(sipWakeupTimer);
        }
    }

}

