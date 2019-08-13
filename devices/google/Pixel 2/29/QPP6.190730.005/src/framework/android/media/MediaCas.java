/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.hardware.cas.V1_0.HidlCasPluginDescriptor;
import android.hardware.cas.V1_0.ICas;
import android.hardware.cas.V1_0.IMediaCasService;
import android.hardware.cas.V1_1.ICasListener;
import android.media.MediaCasException;
import android.media.MediaCasStateException;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Singleton;
import java.util.ArrayList;

public final class MediaCas
implements AutoCloseable {
    private static final String TAG = "MediaCas";
    private static final Singleton<IMediaCasService> sService = new Singleton<IMediaCasService>(){

        @Override
        protected IMediaCasService create() {
            try {
                Log.d(MediaCas.TAG, "Tried to get cas@1.1 service");
                android.hardware.cas.V1_1.IMediaCasService iMediaCasService = android.hardware.cas.V1_1.IMediaCasService.getService(true);
                if (iMediaCasService != null) {
                    return iMediaCasService;
                }
            }
            catch (Exception exception) {
                try {
                    Log.d(MediaCas.TAG, "Tried to get cas@1.0 service");
                    IMediaCasService iMediaCasService = IMediaCasService.getService(true);
                    return iMediaCasService;
                }
                catch (Exception exception2) {
                    Log.d(MediaCas.TAG, "Failed to get cas@1.0 service");
                }
            }
            return null;
        }
    };
    private final ICasListener.Stub mBinder;
    private EventHandler mEventHandler;
    private HandlerThread mHandlerThread;
    private ICas mICas;
    private android.hardware.cas.V1_1.ICas mICasV11;
    private EventListener mListener;

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public MediaCas(int n) throws MediaCasException.UnsupportedCasException {
        Throwable throwable2222;
        block7 : {
            block8 : {
                this.mBinder = new ICasListener.Stub(){

                    @Override
                    public void onEvent(int n, int n2, ArrayList<Byte> arrayList) throws RemoteException {
                        MediaCas.this.mEventHandler.sendMessage(MediaCas.this.mEventHandler.obtainMessage(0, n, n2, arrayList));
                    }

                    @Override
                    public void onSessionEvent(ArrayList<Byte> arrayList, int n, int n2, ArrayList<Byte> arrayList2) throws RemoteException {
                        Message message = MediaCas.this.mEventHandler.obtainMessage();
                        message.what = 1;
                        message.arg1 = n;
                        message.arg2 = n2;
                        Bundle bundle = new Bundle();
                        bundle.putByteArray("sessionId", MediaCas.this.toBytes(arrayList));
                        bundle.putByteArray("data", MediaCas.this.toBytes(arrayList2));
                        message.setData(bundle);
                        MediaCas.this.mEventHandler.sendMessage(message);
                    }
                };
                Object object = MediaCas.getService();
                android.hardware.cas.V1_1.IMediaCasService iMediaCasService = android.hardware.cas.V1_1.IMediaCasService.castFrom((IHwInterface)object);
                if (iMediaCasService == null) {
                    Log.d(TAG, "Used cas@1_0 interface to create plugin");
                    this.mICas = object.createPlugin(n, this.mBinder);
                } else {
                    Log.d(TAG, "Used cas@1.1 interface to create plugin");
                    object = iMediaCasService.createPluginExt(n, this.mBinder);
                    this.mICasV11 = object;
                    this.mICas = object;
                }
                if (this.mICas != null) {
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported CA_system_id ");
                ((StringBuilder)object).append(n);
                throw new MediaCasException.UnsupportedCasException(((StringBuilder)object).toString());
                {
                    catch (Throwable throwable2222) {
                        break block7;
                    }
                    catch (Exception exception) {}
                    {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Failed to create plugin: ");
                        stringBuilder.append(exception);
                        Log.e(TAG, stringBuilder.toString());
                        this.mICas = null;
                        if (this.mICas == null) break block8;
                        return;
                    }
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported CA_system_id ");
            stringBuilder.append(n);
            throw new MediaCasException.UnsupportedCasException(stringBuilder.toString());
        }
        if (this.mICas != null) throw throwable2222;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported CA_system_id ");
        stringBuilder.append(n);
        throw new MediaCasException.UnsupportedCasException(stringBuilder.toString());
    }

    private void cleanupAndRethrowIllegalState() {
        this.mICas = null;
        this.mICasV11 = null;
        throw new IllegalStateException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static PluginDescriptor[] enumeratePlugins() {
        int n;
        PluginDescriptor[] arrpluginDescriptor;
        ArrayList<HidlCasPluginDescriptor> arrayList;
        block5 : {
            arrpluginDescriptor = MediaCas.getService();
            if (arrpluginDescriptor == null) return null;
            arrayList = arrpluginDescriptor.enumeratePlugins();
            if (arrayList.size() != 0) break block5;
            return null;
        }
        try {
            arrpluginDescriptor = new PluginDescriptor[arrayList.size()];
            n = 0;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        do {
            if (n >= arrpluginDescriptor.length) return arrpluginDescriptor;
            arrpluginDescriptor[n] = new PluginDescriptor(arrayList.get(n));
            ++n;
            continue;
            break;
        } while (true);
        return null;
    }

    static IMediaCasService getService() {
        return sService.get();
    }

    public static boolean isSystemIdSupported(int n) {
        IMediaCasService iMediaCasService = MediaCas.getService();
        if (iMediaCasService != null) {
            try {
                boolean bl = iMediaCasService.isSystemIdSupported(n);
                return bl;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    private ArrayList<Byte> toByteArray(byte[] arrby) {
        if (arrby == null) {
            return new ArrayList<Byte>();
        }
        return this.toByteArray(arrby, 0, arrby.length);
    }

    private ArrayList<Byte> toByteArray(byte[] arrby, int n, int n2) {
        ArrayList<Byte> arrayList = new ArrayList<Byte>(n2);
        for (int i = 0; i < n2; ++i) {
            arrayList.add(arrby[n + i]);
        }
        return arrayList;
    }

    private byte[] toBytes(ArrayList<Byte> arrayList) {
        byte[] arrby = null;
        if (arrayList != null) {
            byte[] arrby2 = new byte[arrayList.size()];
            int n = 0;
            do {
                arrby = arrby2;
                if (n >= arrby2.length) break;
                arrby2[n] = arrayList.get(n);
                ++n;
            } while (true);
        }
        return arrby;
    }

    private void validateInternalStates() {
        if (this.mICas != null) {
            return;
        }
        throw new IllegalStateException();
    }

    @Override
    public void close() {
        ICas iCas = this.mICas;
        if (iCas != null) {
            try {
                iCas.release();
            }
            catch (Throwable throwable) {
                this.mICas = null;
                throw throwable;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            this.mICas = null;
        }
    }

    Session createFromSessionId(ArrayList<Byte> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            return new Session(arrayList);
        }
        return null;
    }

    protected void finalize() {
        this.close();
    }

    IHwBinder getBinder() {
        this.validateInternalStates();
        return this.mICas.asBinder();
    }

    public Session openSession() throws MediaCasException {
        this.validateInternalStates();
        try {
            Object object = new OpenSessionCallback();
            this.mICas.openSession((ICas.openSessionCallback)object);
            MediaCasException.throwExceptionIfNeeded(((OpenSessionCallback)object).mStatus);
            object = ((OpenSessionCallback)object).mSession;
            return object;
        }
        catch (RemoteException remoteException) {
            this.cleanupAndRethrowIllegalState();
            return null;
        }
    }

    public void processEmm(byte[] arrby) throws MediaCasException {
        this.processEmm(arrby, 0, arrby.length);
    }

    public void processEmm(byte[] arrby, int n, int n2) throws MediaCasException {
        this.validateInternalStates();
        try {
            MediaCasException.throwExceptionIfNeeded(this.mICas.processEmm(this.toByteArray(arrby, n, n2)));
        }
        catch (RemoteException remoteException) {
            this.cleanupAndRethrowIllegalState();
        }
    }

    public void provision(String string2) throws MediaCasException {
        this.validateInternalStates();
        try {
            MediaCasException.throwExceptionIfNeeded(this.mICas.provision(string2));
        }
        catch (RemoteException remoteException) {
            this.cleanupAndRethrowIllegalState();
        }
    }

    public void refreshEntitlements(int n, byte[] arrby) throws MediaCasException {
        this.validateInternalStates();
        try {
            MediaCasException.throwExceptionIfNeeded(this.mICas.refreshEntitlements(n, this.toByteArray(arrby)));
        }
        catch (RemoteException remoteException) {
            this.cleanupAndRethrowIllegalState();
        }
    }

    public void sendEvent(int n, int n2, byte[] arrby) throws MediaCasException {
        this.validateInternalStates();
        try {
            MediaCasException.throwExceptionIfNeeded(this.mICas.sendEvent(n, n2, this.toByteArray(arrby)));
        }
        catch (RemoteException remoteException) {
            this.cleanupAndRethrowIllegalState();
        }
    }

    public void setEventListener(EventListener object, Handler object2) {
        EventListener eventListener = this.mListener = object;
        object = null;
        if (eventListener == null) {
            this.mEventHandler = null;
            return;
        }
        if (object2 != null) {
            object = ((Handler)object2).getLooper();
        }
        object = object2 = object;
        if (object2 == null) {
            object = object2 = Looper.myLooper();
            if (object2 == null) {
                object = object2 = Looper.getMainLooper();
                if (object2 == null) {
                    object = this.mHandlerThread;
                    if (object == null || !((Thread)object).isAlive()) {
                        this.mHandlerThread = new HandlerThread("MediaCasEventThread", -2);
                        this.mHandlerThread.start();
                    }
                    object = this.mHandlerThread.getLooper();
                }
            }
        }
        this.mEventHandler = new EventHandler((Looper)object);
    }

    public void setPrivateData(byte[] arrby) throws MediaCasException {
        this.validateInternalStates();
        try {
            MediaCasException.throwExceptionIfNeeded(this.mICas.setPrivateData(this.toByteArray(arrby, 0, arrby.length)));
        }
        catch (RemoteException remoteException) {
            this.cleanupAndRethrowIllegalState();
        }
    }

    private class EventHandler
    extends Handler {
        private static final String DATA_KEY = "data";
        private static final int MSG_CAS_EVENT = 0;
        private static final int MSG_CAS_SESSION_EVENT = 1;
        private static final String SESSION_KEY = "sessionId";

        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            if (message.what == 0) {
                MediaCas.this.mListener.onEvent(MediaCas.this, message.arg1, message.arg2, MediaCas.this.toBytes((ArrayList)message.obj));
            } else if (message.what == 1) {
                Bundle bundle = message.getData();
                ArrayList arrayList = MediaCas.this.toByteArray(bundle.getByteArray(SESSION_KEY));
                EventListener eventListener = MediaCas.this.mListener;
                MediaCas mediaCas = MediaCas.this;
                eventListener.onSessionEvent(mediaCas, mediaCas.createFromSessionId(arrayList), message.arg1, message.arg2, bundle.getByteArray(DATA_KEY));
            }
        }
    }

    public static interface EventListener {
        public void onEvent(MediaCas var1, int var2, int var3, byte[] var4);

        default public void onSessionEvent(MediaCas mediaCas, Session session, int n, int n2, byte[] arrby) {
            Log.d("MediaCas", "Received MediaCas Session event");
        }
    }

    private class OpenSessionCallback
    implements ICas.openSessionCallback {
        public Session mSession;
        public int mStatus;

        private OpenSessionCallback() {
        }

        @Override
        public void onValues(int n, ArrayList<Byte> arrayList) {
            this.mStatus = n;
            this.mSession = MediaCas.this.createFromSessionId(arrayList);
        }
    }

    public static class PluginDescriptor {
        private final int mCASystemId;
        private final String mName;

        private PluginDescriptor() {
            this.mCASystemId = 65535;
            this.mName = null;
        }

        PluginDescriptor(HidlCasPluginDescriptor hidlCasPluginDescriptor) {
            this.mCASystemId = hidlCasPluginDescriptor.caSystemId;
            this.mName = hidlCasPluginDescriptor.name;
        }

        public String getName() {
            return this.mName;
        }

        public int getSystemId() {
            return this.mCASystemId;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PluginDescriptor {");
            stringBuilder.append(this.mCASystemId);
            stringBuilder.append(", ");
            stringBuilder.append(this.mName);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    public final class Session
    implements AutoCloseable {
        final ArrayList<Byte> mSessionId;

        Session(ArrayList<Byte> arrayList) {
            this.mSessionId = arrayList;
        }

        @Override
        public void close() {
            MediaCas.this.validateInternalStates();
            try {
                MediaCasStateException.throwExceptionIfNeeded(MediaCas.this.mICas.closeSession(this.mSessionId));
            }
            catch (RemoteException remoteException) {
                MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }

        public boolean equals(Object object) {
            if (object instanceof Session) {
                return this.mSessionId.equals(((Session)object).mSessionId);
            }
            return false;
        }

        public void processEcm(byte[] arrby) throws MediaCasException {
            this.processEcm(arrby, 0, arrby.length);
        }

        public void processEcm(byte[] arrby, int n, int n2) throws MediaCasException {
            MediaCas.this.validateInternalStates();
            try {
                MediaCasException.throwExceptionIfNeeded(MediaCas.this.mICas.processEcm(this.mSessionId, MediaCas.this.toByteArray(arrby, n, n2)));
            }
            catch (RemoteException remoteException) {
                MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }

        public void sendSessionEvent(int n, int n2, byte[] arrby) throws MediaCasException {
            MediaCas.this.validateInternalStates();
            if (MediaCas.this.mICasV11 != null) {
                try {
                    MediaCasException.throwExceptionIfNeeded(MediaCas.this.mICasV11.sendSessionEvent(this.mSessionId, n, n2, MediaCas.this.toByteArray(arrby)));
                }
                catch (RemoteException remoteException) {
                    MediaCas.this.cleanupAndRethrowIllegalState();
                }
                return;
            }
            Log.d(MediaCas.TAG, "Send Session Event isn't supported by cas@1.0 interface");
            throw new MediaCasException.UnsupportedCasException("Send Session Event is not supported");
        }

        public void setPrivateData(byte[] arrby) throws MediaCasException {
            MediaCas.this.validateInternalStates();
            try {
                MediaCasException.throwExceptionIfNeeded(MediaCas.this.mICas.setSessionPrivateData(this.mSessionId, MediaCas.this.toByteArray(arrby, 0, arrby.length)));
            }
            catch (RemoteException remoteException) {
                MediaCas.this.cleanupAndRethrowIllegalState();
            }
        }
    }

}

