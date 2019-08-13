/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.content.Context;
import android.net.lowpan.ILowpanInterface;
import android.net.lowpan.ILowpanManager;
import android.net.lowpan.ILowpanManagerListener;
import android.net.lowpan.LowpanException;
import android.net.lowpan.LowpanInterface;
import android.net.lowpan._$$Lambda$LowpanManager$2$2qKIy18LeIjTlm4mROg_pHOPNU0;
import android.net.lowpan._$$Lambda$LowpanManager$2$jhNE3pUzRwHtqpTRJOtHQRfgQ70;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class LowpanManager {
    private static final String TAG = LowpanManager.class.getSimpleName();
    private final Map<IBinder, WeakReference<LowpanInterface>> mBinderCache = new WeakHashMap<IBinder, WeakReference<LowpanInterface>>();
    private final Context mContext;
    private final Map<String, LowpanInterface> mInterfaceCache = new HashMap<String, LowpanInterface>();
    private final Map<Integer, ILowpanManagerListener> mListenerMap = new HashMap<Integer, ILowpanManagerListener>();
    private final Looper mLooper;
    private final ILowpanManager mService;

    public LowpanManager(Context context, ILowpanManager iLowpanManager, Looper looper) {
        this.mContext = context;
        this.mService = iLowpanManager;
        this.mLooper = looper;
    }

    LowpanManager(ILowpanManager iLowpanManager) {
        this.mService = iLowpanManager;
        this.mContext = null;
        this.mLooper = null;
    }

    public static LowpanManager from(Context context) {
        return (LowpanManager)context.getSystemService("lowpan");
    }

    public static LowpanManager getManager() {
        IBinder iBinder = ServiceManager.getService("lowpan");
        if (iBinder != null) {
            return new LowpanManager(ILowpanManager.Stub.asInterface(iBinder));
        }
        return null;
    }

    public LowpanInterface getInterface() {
        String[] arrstring = this.getInterfaceList();
        if (arrstring.length > 0) {
            return this.getInterface(arrstring[0]);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public LowpanInterface getInterface(ILowpanInterface iLowpanInterface) {
        Object object = null;
        try {
            Map<IBinder, WeakReference<LowpanInterface>> map = this.mBinderCache;
            synchronized (map) {
                if (!this.mBinderCache.containsKey(iLowpanInterface.asBinder())) break block10;
            }
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
        {
            block10 : {
                object = (LowpanInterface)this.mBinderCache.get(iLowpanInterface.asBinder()).get();
            }
            Object object2 = object;
            if (object != null) return object2;
            object2 = iLowpanInterface.getName();
            object = new LowpanInterface(this.mContext, iLowpanInterface, this.mLooper);
            Object object3 = this.mInterfaceCache;
            synchronized (object3) {
                this.mInterfaceCache.put(((LowpanInterface)object).getName(), (LowpanInterface)object);
            }
            Map<IBinder, WeakReference<LowpanInterface>> map = this.mBinderCache;
            IBinder.DeathRecipient deathRecipient = iLowpanInterface.asBinder();
            object3 = new WeakReference(object);
            map.put((IBinder)((Object)deathRecipient), (WeakReference<LowpanInterface>)object3);
            object3 = iLowpanInterface.asBinder();
            deathRecipient = new IBinder.DeathRecipient((String)object2, iLowpanInterface){
                final /* synthetic */ String val$ifaceName;
                final /* synthetic */ ILowpanInterface val$ifaceService;
                {
                    this.val$ifaceName = string2;
                    this.val$ifaceService = iLowpanInterface;
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void binderDied() {
                    Map map = LowpanManager.this.mInterfaceCache;
                    synchronized (map) {
                        LowpanInterface lowpanInterface = (LowpanInterface)LowpanManager.this.mInterfaceCache.get(this.val$ifaceName);
                        if (lowpanInterface != null && lowpanInterface.getService() == this.val$ifaceService) {
                            LowpanManager.this.mInterfaceCache.remove(this.val$ifaceName);
                        }
                        return;
                    }
                }
            };
            object3.linkToDeath(deathRecipient, 0);
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public LowpanInterface getInterface(String object) {
        Object var2_3;
        block6 : {
            var2_3 = null;
            try {
                Map<String, LowpanInterface> map = this.mInterfaceCache;
                // MONITORENTER : map
                if (!this.mInterfaceCache.containsKey(object)) break block6;
                return this.mInterfaceCache.get(object);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        ILowpanInterface iLowpanInterface = this.mService.getInterface((String)object);
        object = var2_3;
        if (iLowpanInterface != null) {
            object = this.getInterface(iLowpanInterface);
        }
        // MONITOREXIT : map
        return object;
    }

    public String[] getInterfaceList() {
        try {
            String[] arrstring = this.mService.getInterfaceList();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public LowpanInterface getInterfaceNoCreate(ILowpanInterface iLowpanInterface) {
        LowpanInterface lowpanInterface = null;
        Map<IBinder, WeakReference<LowpanInterface>> map = this.mBinderCache;
        synchronized (map) {
            if (!this.mBinderCache.containsKey(iLowpanInterface.asBinder())) return lowpanInterface;
            return (LowpanInterface)this.mBinderCache.get(iLowpanInterface.asBinder()).get();
        }
    }

    public void registerCallback(Callback callback) throws LowpanException {
        this.registerCallback(callback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerCallback(Callback callback, Handler object) throws LowpanException {
        ILowpanManagerListener.Stub stub = new ILowpanManagerListener.Stub((Handler)object, callback){
            private Handler mHandler;
            final /* synthetic */ Callback val$cb;
            final /* synthetic */ Handler val$handler;
            {
                this.val$handler = handler;
                this.val$cb = callback;
                LowpanManager.this = this.val$handler;
                this.mHandler = LowpanManager.this != null ? LowpanManager.this : (LowpanManager.this.mLooper != null ? new Handler(LowpanManager.this.mLooper) : new Handler());
            }

            public /* synthetic */ void lambda$onInterfaceAdded$0$LowpanManager$2(ILowpanInterface object, Callback callback) {
                if ((object = LowpanManager.this.getInterface((ILowpanInterface)object)) != null) {
                    callback.onInterfaceAdded((LowpanInterface)object);
                }
            }

            public /* synthetic */ void lambda$onInterfaceRemoved$1$LowpanManager$2(ILowpanInterface object, Callback callback) {
                if ((object = LowpanManager.this.getInterfaceNoCreate((ILowpanInterface)object)) != null) {
                    callback.onInterfaceRemoved((LowpanInterface)object);
                }
            }

            @Override
            public void onInterfaceAdded(ILowpanInterface object) {
                object = new _$$Lambda$LowpanManager$2$2qKIy18LeIjTlm4mROg_pHOPNU0(this, (ILowpanInterface)object, this.val$cb);
                this.mHandler.post((Runnable)object);
            }

            @Override
            public void onInterfaceRemoved(ILowpanInterface object) {
                object = new _$$Lambda$LowpanManager$2$jhNE3pUzRwHtqpTRJOtHQRfgQ70(this, (ILowpanInterface)object, this.val$cb);
                this.mHandler.post((Runnable)object);
            }
        };
        try {
            this.mService.addListener(stub);
            object = this.mListenerMap;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        synchronized (object) {
            this.mListenerMap.put(System.identityHashCode(callback), stub);
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
    public void unregisterCallback(Callback map) {
        Integer n = System.identityHashCode(map);
        map = this.mListenerMap;
        // MONITORENTER : map
        ILowpanManagerListener iLowpanManagerListener = this.mListenerMap.get(n);
        this.mListenerMap.remove(n);
        // MONITOREXIT : map
        if (iLowpanManagerListener == null) throw new RuntimeException("Attempt to unregister an unknown callback");
        try {
            this.mService.removeListener(iLowpanManagerListener);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static abstract class Callback {
        public void onInterfaceAdded(LowpanInterface lowpanInterface) {
        }

        public void onInterfaceRemoved(LowpanInterface lowpanInterface) {
        }
    }

}

