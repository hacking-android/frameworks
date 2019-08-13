/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.content.Context;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.lowpan.ILowpanInterface;
import android.net.lowpan.ILowpanInterfaceListener;
import android.net.lowpan.LowpanBeaconInfo;
import android.net.lowpan.LowpanChannelInfo;
import android.net.lowpan.LowpanCommissioningSession;
import android.net.lowpan.LowpanCredential;
import android.net.lowpan.LowpanException;
import android.net.lowpan.LowpanIdentity;
import android.net.lowpan.LowpanProvision;
import android.net.lowpan.LowpanScanner;
import android.net.lowpan._$$Lambda$LowpanInterface$1$5PUJBkKF3VANgkiEem5Oq8oyB6U;
import android.net.lowpan._$$Lambda$LowpanInterface$1$9yiRqHwJmFc_LEKn1vk5rA75W0M;
import android.net.lowpan._$$Lambda$LowpanInterface$1$LMuYw1xVwTG7Wbs4COpO6TLHuQ0;
import android.net.lowpan._$$Lambda$LowpanInterface$1$Nidk8wBLJKibO6BNky__lJftmGs;
import android.net.lowpan._$$Lambda$LowpanInterface$1$a1rvbSIFSC6J5j7aKUf1ekbmIIA;
import android.net.lowpan._$$Lambda$LowpanInterface$1$bAiJozbLxVR9_EMESl7KCJxLARA;
import android.net.lowpan._$$Lambda$LowpanInterface$1$cH3X25eT4t6pHlLvzBjlSOMs2vc;
import android.net.lowpan._$$Lambda$LowpanInterface$1$i2_6hzE6WEaUSOaaltxLebbf7_E;
import android.net.lowpan._$$Lambda$LowpanInterface$1$oacwoIgJ4pmkBqVtGJfFzk7A35k;
import android.net.lowpan._$$Lambda$LowpanInterface$1$rl_ENeH3C5Kvf22BOtLnz_Ehs5c;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.Log;
import java.util.HashMap;

public class LowpanInterface {
    public static final String EMPTY_PARTITION_ID = "";
    public static final String NETWORK_TYPE_THREAD_V1 = "org.threadgroup.thread.v1";
    public static final String ROLE_COORDINATOR = "coordinator";
    public static final String ROLE_DETACHED = "detached";
    public static final String ROLE_END_DEVICE = "end-device";
    public static final String ROLE_LEADER = "leader";
    public static final String ROLE_ROUTER = "router";
    public static final String ROLE_SLEEPY_END_DEVICE = "sleepy-end-device";
    public static final String ROLE_SLEEPY_ROUTER = "sleepy-router";
    public static final String STATE_ATTACHED = "attached";
    public static final String STATE_ATTACHING = "attaching";
    public static final String STATE_COMMISSIONING = "commissioning";
    public static final String STATE_FAULT = "fault";
    public static final String STATE_OFFLINE = "offline";
    private static final String TAG = LowpanInterface.class.getSimpleName();
    private final ILowpanInterface mBinder;
    private final HashMap<Integer, ILowpanInterfaceListener> mListenerMap = new HashMap();
    private final Looper mLooper;

    public LowpanInterface(Context context, ILowpanInterface iLowpanInterface, Looper looper) {
        this.mBinder = iLowpanInterface;
        this.mLooper = looper;
    }

    public void addExternalRoute(IpPrefix ipPrefix, int n) throws LowpanException {
        try {
            this.mBinder.addExternalRoute(ipPrefix, n);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void addOnMeshPrefix(IpPrefix ipPrefix, int n) throws LowpanException {
        try {
            this.mBinder.addOnMeshPrefix(ipPrefix, n);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void attach(LowpanProvision lowpanProvision) throws LowpanException {
        try {
            this.mBinder.attach(lowpanProvision);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public LowpanScanner createScanner() {
        return new LowpanScanner(this.mBinder);
    }

    public void form(LowpanProvision lowpanProvision) throws LowpanException {
        try {
            this.mBinder.form(lowpanProvision);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public LinkAddress[] getLinkAddresses() throws LowpanException {
        int n;
        int n2;
        String[] arrstring = this.mBinder.getLinkAddresses();
        LinkAddress[] arrlinkAddress = new LinkAddress[arrstring.length];
        int n3 = 0;
        try {
            n = arrstring.length;
            n2 = 0;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        while (n2 < n) {
            arrlinkAddress[n3] = new LinkAddress(arrstring[n2]);
            ++n2;
            ++n3;
        }
        return arrlinkAddress;
    }

    public IpPrefix[] getLinkNetworks() throws LowpanException {
        try {
            IpPrefix[] arripPrefix = this.mBinder.getLinkNetworks();
            return arripPrefix;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public LowpanCredential getLowpanCredential() {
        try {
            LowpanCredential lowpanCredential = this.mBinder.getLowpanCredential();
            return lowpanCredential;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public LowpanIdentity getLowpanIdentity() {
        try {
            LowpanIdentity lowpanIdentity = this.mBinder.getLowpanIdentity();
            return lowpanIdentity;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        catch (DeadObjectException deadObjectException) {
            return new LowpanIdentity();
        }
    }

    public String getName() {
        try {
            String string2 = this.mBinder.getName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        catch (DeadObjectException deadObjectException) {
            return EMPTY_PARTITION_ID;
        }
    }

    public String getPartitionId() {
        try {
            String string2 = this.mBinder.getPartitionId();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        catch (DeadObjectException deadObjectException) {
            return EMPTY_PARTITION_ID;
        }
    }

    public String getRole() {
        try {
            String string2 = this.mBinder.getRole();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        catch (DeadObjectException deadObjectException) {
            return ROLE_DETACHED;
        }
    }

    public ILowpanInterface getService() {
        return this.mBinder;
    }

    public String getState() {
        try {
            String string2 = this.mBinder.getState();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        catch (DeadObjectException deadObjectException) {
            return STATE_FAULT;
        }
    }

    public LowpanChannelInfo[] getSupportedChannels() throws LowpanException {
        try {
            LowpanChannelInfo[] arrlowpanChannelInfo = this.mBinder.getSupportedChannels();
            return arrlowpanChannelInfo;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public String[] getSupportedNetworkTypes() throws LowpanException {
        try {
            String[] arrstring = this.mBinder.getSupportedNetworkTypes();
            return arrstring;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public boolean isCommissioned() {
        try {
            boolean bl = this.mBinder.isCommissioned();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        catch (DeadObjectException deadObjectException) {
            return false;
        }
    }

    public boolean isConnected() {
        try {
            boolean bl = this.mBinder.isConnected();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        catch (DeadObjectException deadObjectException) {
            return false;
        }
    }

    public boolean isEnabled() {
        try {
            boolean bl = this.mBinder.isEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        catch (DeadObjectException deadObjectException) {
            return false;
        }
    }

    public boolean isUp() {
        try {
            boolean bl = this.mBinder.isUp();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        catch (DeadObjectException deadObjectException) {
            return false;
        }
    }

    public void join(LowpanProvision lowpanProvision) throws LowpanException {
        try {
            this.mBinder.join(lowpanProvision);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void leave() throws LowpanException {
        try {
            this.mBinder.leave();
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void registerCallback(Callback callback) {
        this.registerCallback(callback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerCallback(Callback callback, Handler object) {
        ILowpanInterfaceListener.Stub stub = new ILowpanInterfaceListener.Stub((Handler)object, callback){
            private Handler mHandler;
            final /* synthetic */ Callback val$cb;
            final /* synthetic */ Handler val$handler;
            {
                this.val$handler = handler;
                this.val$cb = callback;
                LowpanInterface.this = this.val$handler;
                this.mHandler = LowpanInterface.this != null ? LowpanInterface.this : (LowpanInterface.this.mLooper != null ? new Handler(LowpanInterface.this.mLooper) : new Handler());
            }

            static /* synthetic */ void lambda$onConnectedChanged$1(Callback callback, boolean bl) {
                callback.onConnectedChanged(bl);
            }

            static /* synthetic */ void lambda$onEnabledChanged$0(Callback callback, boolean bl) {
                callback.onEnabledChanged(bl);
            }

            static /* synthetic */ void lambda$onLinkAddressAdded$8(Callback callback, LinkAddress linkAddress) {
                callback.onLinkAddressAdded(linkAddress);
            }

            static /* synthetic */ void lambda$onLinkAddressRemoved$9(Callback callback, LinkAddress linkAddress) {
                callback.onLinkAddressRemoved(linkAddress);
            }

            static /* synthetic */ void lambda$onLinkNetworkAdded$6(Callback callback, IpPrefix ipPrefix) {
                callback.onLinkNetworkAdded(ipPrefix);
            }

            static /* synthetic */ void lambda$onLinkNetworkRemoved$7(Callback callback, IpPrefix ipPrefix) {
                callback.onLinkNetworkRemoved(ipPrefix);
            }

            static /* synthetic */ void lambda$onLowpanIdentityChanged$5(Callback callback, LowpanIdentity lowpanIdentity) {
                callback.onLowpanIdentityChanged(lowpanIdentity);
            }

            static /* synthetic */ void lambda$onRoleChanged$3(Callback callback, String string2) {
                callback.onRoleChanged(string2);
            }

            static /* synthetic */ void lambda$onStateChanged$4(Callback callback, String string2) {
                callback.onStateChanged(string2);
            }

            static /* synthetic */ void lambda$onUpChanged$2(Callback callback, boolean bl) {
                callback.onUpChanged(bl);
            }

            @Override
            public void onConnectedChanged(boolean bl) {
                this.mHandler.post(new _$$Lambda$LowpanInterface$1$Nidk8wBLJKibO6BNky__lJftmGs(this.val$cb, bl));
            }

            @Override
            public void onEnabledChanged(boolean bl) {
                this.mHandler.post(new _$$Lambda$LowpanInterface$1$LMuYw1xVwTG7Wbs4COpO6TLHuQ0(this.val$cb, bl));
            }

            @Override
            public void onLinkAddressAdded(String string2) {
                LinkAddress linkAddress;
                try {
                    linkAddress = new LinkAddress(string2);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    String string3 = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onLinkAddressAdded: Bad LinkAddress \"");
                    stringBuilder.append(string2);
                    stringBuilder.append("\", ");
                    stringBuilder.append(illegalArgumentException);
                    Log.e(string3, stringBuilder.toString());
                    return;
                }
                this.mHandler.post(new _$$Lambda$LowpanInterface$1$i2_6hzE6WEaUSOaaltxLebbf7_E(this.val$cb, linkAddress));
            }

            @Override
            public void onLinkAddressRemoved(String string2) {
                LinkAddress linkAddress;
                try {
                    linkAddress = new LinkAddress(string2);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    String string3 = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onLinkAddressRemoved: Bad LinkAddress \"");
                    stringBuilder.append(string2);
                    stringBuilder.append("\", ");
                    stringBuilder.append(illegalArgumentException);
                    Log.e(string3, stringBuilder.toString());
                    return;
                }
                this.mHandler.post(new _$$Lambda$LowpanInterface$1$bAiJozbLxVR9_EMESl7KCJxLARA(this.val$cb, linkAddress));
            }

            @Override
            public void onLinkNetworkAdded(IpPrefix ipPrefix) {
                this.mHandler.post(new _$$Lambda$LowpanInterface$1$oacwoIgJ4pmkBqVtGJfFzk7A35k(this.val$cb, ipPrefix));
            }

            @Override
            public void onLinkNetworkRemoved(IpPrefix ipPrefix) {
                this.mHandler.post(new _$$Lambda$LowpanInterface$1$cH3X25eT4t6pHlLvzBjlSOMs2vc(this.val$cb, ipPrefix));
            }

            @Override
            public void onLowpanIdentityChanged(LowpanIdentity lowpanIdentity) {
                this.mHandler.post(new _$$Lambda$LowpanInterface$1$rl_ENeH3C5Kvf22BOtLnz_Ehs5c(this.val$cb, lowpanIdentity));
            }

            @Override
            public void onReceiveFromCommissioner(byte[] arrby) {
            }

            @Override
            public void onRoleChanged(String string2) {
                this.mHandler.post(new _$$Lambda$LowpanInterface$1$9yiRqHwJmFc_LEKn1vk5rA75W0M(this.val$cb, string2));
            }

            @Override
            public void onStateChanged(String string2) {
                this.mHandler.post(new _$$Lambda$LowpanInterface$1$5PUJBkKF3VANgkiEem5Oq8oyB6U(this.val$cb, string2));
            }

            @Override
            public void onUpChanged(boolean bl) {
                this.mHandler.post(new _$$Lambda$LowpanInterface$1$a1rvbSIFSC6J5j7aKUf1ekbmIIA(this.val$cb, bl));
            }
        };
        try {
            this.mBinder.addListener(stub);
            object = this.mListenerMap;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        synchronized (object) {
            this.mListenerMap.put(System.identityHashCode(callback), stub);
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void removeExternalRoute(IpPrefix ipPrefix) {
        try {
            this.mBinder.removeExternalRoute(ipPrefix);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            Log.e(TAG, serviceSpecificException.toString());
        }
        return;
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void removeOnMeshPrefix(IpPrefix ipPrefix) {
        try {
            this.mBinder.removeOnMeshPrefix(ipPrefix);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            Log.e(TAG, serviceSpecificException.toString());
        }
        return;
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void reset() throws LowpanException {
        try {
            this.mBinder.reset();
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void setEnabled(boolean bl) throws LowpanException {
        try {
            this.mBinder.setEnabled(bl);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public LowpanCommissioningSession startCommissioningSession(LowpanBeaconInfo object) throws LowpanException {
        try {
            this.mBinder.startCommissioningSession((LowpanBeaconInfo)object);
            object = new LowpanCommissioningSession(this.mBinder, (LowpanBeaconInfo)object, this.mLooper);
            return object;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterCallback(Callback object) {
        int n = System.identityHashCode(object);
        object = this.mListenerMap;
        synchronized (object) {
            ILowpanInterfaceListener iLowpanInterfaceListener = this.mListenerMap.get(n);
            if (iLowpanInterfaceListener != null) {
                this.mListenerMap.remove(n);
                try {
                    this.mBinder.removeListener(iLowpanInterfaceListener);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowAsRuntimeException();
                }
                catch (DeadObjectException deadObjectException) {
                    // empty catch block
                }
            }
            return;
        }
    }

    public static abstract class Callback {
        public void onConnectedChanged(boolean bl) {
        }

        public void onEnabledChanged(boolean bl) {
        }

        public void onLinkAddressAdded(LinkAddress linkAddress) {
        }

        public void onLinkAddressRemoved(LinkAddress linkAddress) {
        }

        public void onLinkNetworkAdded(IpPrefix ipPrefix) {
        }

        public void onLinkNetworkRemoved(IpPrefix ipPrefix) {
        }

        public void onLowpanIdentityChanged(LowpanIdentity lowpanIdentity) {
        }

        public void onRoleChanged(String string2) {
        }

        public void onStateChanged(String string2) {
        }

        public void onUpChanged(boolean bl) {
        }
    }

}

