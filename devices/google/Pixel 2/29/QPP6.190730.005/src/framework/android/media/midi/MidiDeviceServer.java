/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  dalvik.system.CloseGuard
 *  libcore.io.IoUtils
 */
package android.media.midi;

import android.media.midi.IMidiDeviceServer;
import android.media.midi.IMidiManager;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiDeviceStatus;
import android.media.midi.MidiInputPort;
import android.media.midi.MidiOutputPort;
import android.media.midi.MidiReceiver;
import android.media.midi.MidiSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import com.android.internal.midi.MidiDispatcher;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import libcore.io.IoUtils;

public final class MidiDeviceServer
implements Closeable {
    private static final String TAG = "MidiDeviceServer";
    private final Callback mCallback;
    private MidiDeviceInfo mDeviceInfo;
    private final CloseGuard mGuard = CloseGuard.get();
    private final HashMap<MidiInputPort, PortClient> mInputPortClients = new HashMap();
    private final int mInputPortCount;
    private final MidiDispatcher.MidiReceiverFailureHandler mInputPortFailureHandler = new MidiDispatcher.MidiReceiverFailureHandler(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void onReceiverFailure(MidiReceiver object, IOException serializable) {
            Log.e(MidiDeviceServer.TAG, "MidiInputPort failed to send data", serializable);
            serializable = MidiDeviceServer.this.mInputPortClients;
            // MONITORENTER : serializable
            object = (PortClient)MidiDeviceServer.this.mInputPortClients.remove(object);
            // MONITOREXIT : serializable
            if (object == null) return;
            ((PortClient)object).close();
        }
    };
    private final boolean[] mInputPortOpen;
    private final MidiOutputPort[] mInputPortOutputPorts;
    private final MidiReceiver[] mInputPortReceivers;
    private final CopyOnWriteArrayList<MidiInputPort> mInputPorts = new CopyOnWriteArrayList();
    private boolean mIsClosed;
    private final IMidiManager mMidiManager;
    private final int mOutputPortCount;
    private MidiDispatcher[] mOutputPortDispatchers;
    private final int[] mOutputPortOpenCount;
    private final HashMap<IBinder, PortClient> mPortClients = new HashMap();
    private final IMidiDeviceServer mServer = new IMidiDeviceServer.Stub(){

        @Override
        public void closeDevice() {
            if (MidiDeviceServer.this.mCallback != null) {
                MidiDeviceServer.this.mCallback.onClose();
            }
            IoUtils.closeQuietly((AutoCloseable)MidiDeviceServer.this);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void closePort(IBinder object) {
            HashMap hashMap = null;
            HashMap hashMap2 = MidiDeviceServer.this.mPortClients;
            synchronized (hashMap2) {
                PortClient portClient = (PortClient)MidiDeviceServer.this.mPortClients.remove(object);
                object = hashMap;
                if (portClient != null) {
                    object = portClient.getInputPort();
                    portClient.close();
                }
            }
            if (object == null) return;
            hashMap = MidiDeviceServer.this.mInputPortClients;
            synchronized (hashMap) {
                MidiDeviceServer.this.mInputPortClients.remove(object);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int connectPorts(IBinder object, FileDescriptor object2, int n) {
            object2 = new MidiInputPort((FileDescriptor)object2, n);
            Object object3 = MidiDeviceServer.this.mOutputPortDispatchers[n];
            synchronized (object3) {
                int n2;
                ((MidiDispatcher)object3).getSender().connect((MidiReceiver)object2);
                MidiDeviceServer.access$400((MidiDeviceServer)MidiDeviceServer.this)[n] = n2 = ((MidiDispatcher)object3).getReceiverCount();
                MidiDeviceServer.this.updateDeviceStatus();
            }
            MidiDeviceServer.this.mInputPorts.add(object2);
            OutputPortClient outputPortClient = new OutputPortClient((IBinder)object, (MidiInputPort)object2);
            object3 = MidiDeviceServer.this.mPortClients;
            synchronized (object3) {
                MidiDeviceServer.this.mPortClients.put(object, outputPortClient);
            }
            object = MidiDeviceServer.this.mInputPortClients;
            synchronized (object) {
                MidiDeviceServer.this.mInputPortClients.put(object2, outputPortClient);
                return Process.myPid();
            }
        }

        @Override
        public MidiDeviceInfo getDeviceInfo() {
            return MidiDeviceServer.this.mDeviceInfo;
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public FileDescriptor openInputPort(IBinder object, int n) {
            if (MidiDeviceServer.this.mDeviceInfo.isPrivate() && Binder.getCallingUid() != Process.myUid()) {
                throw new SecurityException("Can't access private device from different UID");
            }
            if (n >= 0 && n < MidiDeviceServer.this.mInputPortCount) {
                MidiOutputPort[] arrmidiOutputPort = MidiDeviceServer.this.mInputPortOutputPorts;
                synchronized (arrmidiOutputPort) {
                    FileDescriptor[] arrfileDescriptor;
                    if (MidiDeviceServer.this.mInputPortOutputPorts[n] != null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("port ");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" already open");
                        Log.d(MidiDeviceServer.TAG, ((StringBuilder)object).toString());
                        return null;
                    }
                    try {
                        arrfileDescriptor = MidiDeviceServer.createSeqPacketSocketPair();
                        Object object2 = new MidiOutputPort(arrfileDescriptor[0], n);
                        MidiDeviceServer.access$000((MidiDeviceServer)MidiDeviceServer.this)[n] = object2;
                        ((MidiSender)object2).connect(MidiDeviceServer.this.mInputPortReceivers[n]);
                        InputPortClient inputPortClient = new InputPortClient((IBinder)object, (MidiOutputPort)object2);
                        object2 = MidiDeviceServer.this.mPortClients;
                        synchronized (object2) {
                            MidiDeviceServer.this.mPortClients.put(object, inputPortClient);
                        }
                    }
                    catch (IOException iOException) {
                        Log.e(MidiDeviceServer.TAG, "unable to create FileDescriptors in openInputPort");
                        return null;
                    }
                    {
                    }
                    MidiDeviceServer.access$100((MidiDeviceServer)MidiDeviceServer.this)[n] = true;
                    MidiDeviceServer.this.updateDeviceStatus();
                    return arrfileDescriptor[1];
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("portNumber out of range in openInputPort: ");
            ((StringBuilder)object).append(n);
            Log.e(MidiDeviceServer.TAG, ((StringBuilder)object).toString());
            return null;
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public FileDescriptor openOutputPort(IBinder object, int n) {
            if (MidiDeviceServer.this.mDeviceInfo.isPrivate()) {
                if (Binder.getCallingUid() != Process.myUid()) throw new SecurityException("Can't access private device from different UID");
            }
            if (n >= 0 && n < MidiDeviceServer.this.mOutputPortCount) {
                FileDescriptor[] arrfileDescriptor;
                MidiInputPort midiInputPort;
                Object object2;
                try {
                    int n2;
                    arrfileDescriptor = MidiDeviceServer.createSeqPacketSocketPair();
                    midiInputPort = new MidiInputPort(arrfileDescriptor[0], n);
                    if (MidiDeviceServer.this.mDeviceInfo.getType() != 2) {
                        IoUtils.setBlocking((FileDescriptor)arrfileDescriptor[0], (boolean)false);
                    }
                    object2 = MidiDeviceServer.this.mOutputPortDispatchers[n];
                    // MONITORENTER : object2
                    ((MidiDispatcher)object2).getSender().connect(midiInputPort);
                    MidiDeviceServer.access$400((MidiDeviceServer)MidiDeviceServer.this)[n] = n2 = ((MidiDispatcher)object2).getReceiverCount();
                }
                catch (IOException iOException) {
                    Log.e(MidiDeviceServer.TAG, "unable to create FileDescriptors in openOutputPort");
                    return null;
                }
                MidiDeviceServer.this.updateDeviceStatus();
                // MONITOREXIT : object2
                MidiDeviceServer.this.mInputPorts.add(midiInputPort);
                OutputPortClient outputPortClient = new OutputPortClient((IBinder)object, midiInputPort);
                object2 = MidiDeviceServer.this.mPortClients;
                // MONITORENTER : object2
                MidiDeviceServer.this.mPortClients.put(object, outputPortClient);
                // MONITOREXIT : object2
                object = MidiDeviceServer.this.mInputPortClients;
                // MONITORENTER : object
                MidiDeviceServer.this.mInputPortClients.put(midiInputPort, outputPortClient);
                // MONITOREXIT : object
                return arrfileDescriptor[1];
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("portNumber out of range in openOutputPort: ");
            ((StringBuilder)object).append(n);
            Log.e(MidiDeviceServer.TAG, ((StringBuilder)object).toString());
            return null;
        }

        @Override
        public void setDeviceInfo(MidiDeviceInfo midiDeviceInfo) {
            if (Binder.getCallingUid() == 1000) {
                if (MidiDeviceServer.this.mDeviceInfo == null) {
                    MidiDeviceServer.this.mDeviceInfo = midiDeviceInfo;
                    return;
                }
                throw new IllegalStateException("setDeviceInfo should only be called once");
            }
            throw new SecurityException("setDeviceInfo should only be called by MidiService");
        }
    };

    MidiDeviceServer(IMidiManager iMidiManager, MidiReceiver[] arrmidiReceiver, int n, Callback callback) {
        this.mMidiManager = iMidiManager;
        this.mInputPortReceivers = arrmidiReceiver;
        this.mInputPortCount = arrmidiReceiver.length;
        this.mOutputPortCount = n;
        this.mCallback = callback;
        this.mInputPortOutputPorts = new MidiOutputPort[this.mInputPortCount];
        this.mOutputPortDispatchers = new MidiDispatcher[n];
        for (int i = 0; i < n; ++i) {
            this.mOutputPortDispatchers[i] = new MidiDispatcher(this.mInputPortFailureHandler);
        }
        this.mInputPortOpen = new boolean[this.mInputPortCount];
        this.mOutputPortOpenCount = new int[n];
        this.mGuard.open("close");
    }

    MidiDeviceServer(IMidiManager iMidiManager, MidiReceiver[] arrmidiReceiver, MidiDeviceInfo midiDeviceInfo, Callback callback) {
        this(iMidiManager, arrmidiReceiver, midiDeviceInfo.getOutputPortCount(), callback);
        this.mDeviceInfo = midiDeviceInfo;
    }

    static /* synthetic */ boolean[] access$100(MidiDeviceServer midiDeviceServer) {
        return midiDeviceServer.mInputPortOpen;
    }

    static /* synthetic */ int[] access$400(MidiDeviceServer midiDeviceServer) {
        return midiDeviceServer.mOutputPortOpenCount;
    }

    private static FileDescriptor[] createSeqPacketSocketPair() throws IOException {
        FileDescriptor fileDescriptor;
        FileDescriptor fileDescriptor2;
        try {
            fileDescriptor = new FileDescriptor();
            fileDescriptor2 = new FileDescriptor();
            Os.socketpair((int)OsConstants.AF_UNIX, (int)OsConstants.SOCK_SEQPACKET, (int)0, (FileDescriptor)fileDescriptor, (FileDescriptor)fileDescriptor2);
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
        return new FileDescriptor[]{fileDescriptor, fileDescriptor2};
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void updateDeviceStatus() {
        var1_1 = Binder.clearCallingIdentity();
        var3_2 = new MidiDeviceStatus(this.mDeviceInfo, this.mInputPortOpen, this.mOutputPortOpenCount);
        var4_5 = this.mCallback;
        if (var4_5 != null) {
            var4_5.onDeviceStatusChanged(this, var3_2);
        }
        this.mMidiManager.setDeviceStatus(this.mServer, var3_2);
lbl8: // 2 sources:
        do {
            Binder.restoreCallingIdentity(var1_1);
            return;
            break;
        } while (true);
        {
            catch (Throwable var3_3) {
            }
            catch (RemoteException var3_4) {}
            {
                Log.e("MidiDeviceServer", "RemoteException in updateDeviceStatus");
                ** continue;
            }
        }
        Binder.restoreCallingIdentity(var1_1);
        throw var3_3;
    }

    public IBinder asBinder() {
        return this.mServer.asBinder();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        CloseGuard closeGuard = this.mGuard;
        synchronized (closeGuard) {
            Object object;
            if (this.mIsClosed) {
                return;
            }
            this.mGuard.close();
            for (int i = 0; i < this.mInputPortCount; ++i) {
                object = this.mInputPortOutputPorts[i];
                if (object == null) continue;
                IoUtils.closeQuietly((AutoCloseable)object);
                this.mInputPortOutputPorts[i] = null;
            }
            object = this.mInputPorts.iterator();
            while (object.hasNext()) {
                IoUtils.closeQuietly((AutoCloseable)((MidiInputPort)object.next()));
            }
            this.mInputPorts.clear();
            try {
                this.mMidiManager.unregisterDeviceServer(this.mServer);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "RemoteException in unregisterDeviceServer");
            }
            this.mIsClosed = true;
            return;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mGuard != null) {
                this.mGuard.warnIfOpen();
            }
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    IMidiDeviceServer getBinderInterface() {
        return this.mServer;
    }

    public MidiReceiver[] getOutputPortReceivers() {
        int n = this.mOutputPortCount;
        MidiReceiver[] arrmidiReceiver = new MidiReceiver[n];
        System.arraycopy(this.mOutputPortDispatchers, 0, arrmidiReceiver, 0, n);
        return arrmidiReceiver;
    }

    public static interface Callback {
        public void onClose();

        public void onDeviceStatusChanged(MidiDeviceServer var1, MidiDeviceStatus var2);
    }

    private class InputPortClient
    extends PortClient {
        private final MidiOutputPort mOutputPort;

        InputPortClient(IBinder iBinder, MidiOutputPort midiOutputPort) {
            super(iBinder);
            this.mOutputPort = midiOutputPort;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        void close() {
            this.mToken.unlinkToDeath(this, 0);
            MidiOutputPort[] arrmidiOutputPort = MidiDeviceServer.this.mInputPortOutputPorts;
            synchronized (arrmidiOutputPort) {
                int n = this.mOutputPort.getPortNumber();
                MidiDeviceServer.access$000((MidiDeviceServer)MidiDeviceServer.this)[n] = null;
                MidiDeviceServer.access$100((MidiDeviceServer)MidiDeviceServer.this)[n] = false;
                MidiDeviceServer.this.updateDeviceStatus();
            }
            IoUtils.closeQuietly((AutoCloseable)this.mOutputPort);
        }
    }

    private class OutputPortClient
    extends PortClient {
        private final MidiInputPort mInputPort;

        OutputPortClient(IBinder iBinder, MidiInputPort midiInputPort) {
            super(iBinder);
            this.mInputPort = midiInputPort;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        void close() {
            this.mToken.unlinkToDeath(this, 0);
            int n = this.mInputPort.getPortNumber();
            MidiDispatcher midiDispatcher = MidiDeviceServer.this.mOutputPortDispatchers[n];
            synchronized (midiDispatcher) {
                int n2;
                midiDispatcher.getSender().disconnect(this.mInputPort);
                MidiDeviceServer.access$400((MidiDeviceServer)MidiDeviceServer.this)[n] = n2 = midiDispatcher.getReceiverCount();
                MidiDeviceServer.this.updateDeviceStatus();
            }
            MidiDeviceServer.this.mInputPorts.remove(this.mInputPort);
            IoUtils.closeQuietly((AutoCloseable)this.mInputPort);
        }

        @Override
        MidiInputPort getInputPort() {
            return this.mInputPort;
        }
    }

    private abstract class PortClient
    implements IBinder.DeathRecipient {
        final IBinder mToken;

        PortClient(IBinder iBinder) {
            this.mToken = iBinder;
            try {
                iBinder.linkToDeath(this, 0);
            }
            catch (RemoteException remoteException) {
                this.close();
            }
        }

        @Override
        public void binderDied() {
            this.close();
        }

        abstract void close();

        MidiInputPort getInputPort() {
            return null;
        }
    }

}

