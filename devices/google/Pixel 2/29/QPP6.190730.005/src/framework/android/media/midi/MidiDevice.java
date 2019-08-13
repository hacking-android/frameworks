/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 *  libcore.io.IoUtils
 */
package android.media.midi;

import android.media.midi.IMidiDeviceServer;
import android.media.midi.IMidiManager;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiInputPort;
import android.media.midi.MidiOutputPort;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import libcore.io.IoUtils;

public final class MidiDevice
implements Closeable {
    private static final String TAG = "MidiDevice";
    private final IBinder mClientToken;
    private final MidiDeviceInfo mDeviceInfo;
    private final IMidiDeviceServer mDeviceServer;
    private final IBinder mDeviceServerBinder;
    private final IBinder mDeviceToken;
    private final CloseGuard mGuard = CloseGuard.get();
    private boolean mIsDeviceClosed;
    private final IMidiManager mMidiManager;
    private long mNativeHandle;

    MidiDevice(MidiDeviceInfo midiDeviceInfo, IMidiDeviceServer iMidiDeviceServer, IMidiManager iMidiManager, IBinder iBinder, IBinder iBinder2) {
        this.mDeviceInfo = midiDeviceInfo;
        this.mDeviceServer = iMidiDeviceServer;
        this.mDeviceServerBinder = this.mDeviceServer.asBinder();
        this.mMidiManager = iMidiManager;
        this.mClientToken = iBinder;
        this.mDeviceToken = iBinder2;
        this.mGuard.open("close");
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
            if (this.mNativeHandle != 0L) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("MidiDevice#close() called while there is an outstanding native client 0x");
                stringBuilder.append(Long.toHexString(this.mNativeHandle));
                Log.w(TAG, stringBuilder.toString());
            }
            if (!this.mIsDeviceClosed && this.mNativeHandle == 0L) {
                this.mGuard.close();
                this.mIsDeviceClosed = true;
                try {
                    this.mMidiManager.closeDevice(this.mClientToken, this.mDeviceToken);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "RemoteException in closeDevice");
                }
            }
            return;
        }
    }

    public MidiConnection connectPorts(MidiInputPort closeable, int n) {
        if (n >= 0 && n < this.mDeviceInfo.getOutputPortCount()) {
            if (this.mIsDeviceClosed) {
                return null;
            }
            FileDescriptor fileDescriptor = closeable.claimFileDescriptor();
            if (fileDescriptor == null) {
                return null;
            }
            try {
                Binder binder = new Binder();
                if (this.mDeviceServer.connectPorts(binder, fileDescriptor, n) != Process.myPid()) {
                    IoUtils.closeQuietly((FileDescriptor)fileDescriptor);
                }
                closeable = new MidiConnection(binder, (MidiInputPort)closeable);
                return closeable;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "RemoteException in connectPorts");
                return null;
            }
        }
        throw new IllegalArgumentException("outputPortNumber out of range");
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

    public MidiDeviceInfo getInfo() {
        return this.mDeviceInfo;
    }

    public MidiInputPort openInputPort(int n) {
        FileDescriptor fileDescriptor;
        Object object;
        block4 : {
            if (this.mIsDeviceClosed) {
                return null;
            }
            try {
                object = new Binder();
                fileDescriptor = this.mDeviceServer.openInputPort((IBinder)object, n);
                if (fileDescriptor != null) break block4;
                return null;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "RemoteException in openInputPort");
                return null;
            }
        }
        object = new MidiInputPort(this.mDeviceServer, (IBinder)object, fileDescriptor, n);
        return object;
    }

    public MidiOutputPort openOutputPort(int n) {
        FileDescriptor fileDescriptor;
        Object object;
        block4 : {
            if (this.mIsDeviceClosed) {
                return null;
            }
            try {
                object = new Binder();
                fileDescriptor = this.mDeviceServer.openOutputPort((IBinder)object, n);
                if (fileDescriptor != null) break block4;
                return null;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "RemoteException in openOutputPort");
                return null;
            }
        }
        object = new MidiOutputPort(this.mDeviceServer, (IBinder)object, fileDescriptor, n);
        return object;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MidiDevice: ");
        stringBuilder.append(this.mDeviceInfo.toString());
        return stringBuilder.toString();
    }

    public class MidiConnection
    implements Closeable {
        private final CloseGuard mGuard = CloseGuard.get();
        private final IMidiDeviceServer mInputPortDeviceServer;
        private final IBinder mInputPortToken;
        private boolean mIsClosed;
        private final IBinder mOutputPortToken;

        MidiConnection(IBinder iBinder, MidiInputPort midiInputPort) {
            this.mInputPortDeviceServer = midiInputPort.getDeviceServer();
            this.mInputPortToken = midiInputPort.getToken();
            this.mOutputPortToken = iBinder;
            this.mGuard.open("close");
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
                if (this.mIsClosed) {
                    return;
                }
                this.mGuard.close();
                try {
                    this.mInputPortDeviceServer.closePort(this.mInputPortToken);
                    MidiDevice.this.mDeviceServer.closePort(this.mOutputPortToken);
                }
                catch (RemoteException remoteException) {
                    Log.e(MidiDevice.TAG, "RemoteException in MidiConnection.close");
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
    }

}

