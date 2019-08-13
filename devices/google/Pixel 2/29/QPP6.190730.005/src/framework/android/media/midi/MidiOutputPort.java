/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 *  libcore.io.IoUtils
 */
package android.media.midi;

import android.media.midi.IMidiDeviceServer;
import android.media.midi.MidiPortImpl;
import android.media.midi.MidiReceiver;
import android.media.midi.MidiSender;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.midi.MidiDispatcher;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import libcore.io.IoUtils;

public final class MidiOutputPort
extends MidiSender
implements Closeable {
    private static final String TAG = "MidiOutputPort";
    private IMidiDeviceServer mDeviceServer;
    private final MidiDispatcher mDispatcher = new MidiDispatcher();
    private final CloseGuard mGuard = CloseGuard.get();
    private final FileInputStream mInputStream;
    private boolean mIsClosed;
    private final int mPortNumber;
    private final Thread mThread = new Thread(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            byte[] arrby = new byte[1024];
            try {
                int n;
                while ((n = MidiOutputPort.this.mInputStream.read(arrby)) >= 0) {
                    int n2 = MidiPortImpl.getPacketType(arrby, n);
                    if (n2 != 1) {
                        if (n2 != 2) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown packet type ");
                            stringBuilder.append(n2);
                            Log.e(MidiOutputPort.TAG, stringBuilder.toString());
                            continue;
                        }
                        MidiOutputPort.this.mDispatcher.flush();
                        continue;
                    }
                    n2 = MidiPortImpl.getDataOffset(arrby, n);
                    int n3 = MidiPortImpl.getDataSize(arrby, n);
                    long l = MidiPortImpl.getPacketTimestamp(arrby, n);
                    MidiOutputPort.this.mDispatcher.send(arrby, n2, n3, l);
                }
            }
            catch (Throwable throwable) {
                IoUtils.closeQuietly((AutoCloseable)MidiOutputPort.this.mInputStream);
                throw throwable;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            IoUtils.closeQuietly((AutoCloseable)MidiOutputPort.this.mInputStream);
        }
    };
    private final IBinder mToken;

    MidiOutputPort(IMidiDeviceServer iMidiDeviceServer, IBinder iBinder, FileDescriptor fileDescriptor, int n) {
        this.mDeviceServer = iMidiDeviceServer;
        this.mToken = iBinder;
        this.mPortNumber = n;
        this.mInputStream = new ParcelFileDescriptor.AutoCloseInputStream(new ParcelFileDescriptor(fileDescriptor));
        this.mThread.start();
        this.mGuard.open("close");
    }

    MidiOutputPort(FileDescriptor fileDescriptor, int n) {
        this(null, null, fileDescriptor, n);
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
            this.mInputStream.close();
            IMidiDeviceServer iMidiDeviceServer = this.mDeviceServer;
            if (iMidiDeviceServer != null) {
                try {
                    this.mDeviceServer.closePort(this.mToken);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "RemoteException in MidiOutputPort.close()");
                }
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
            this.mDeviceServer = null;
            this.close();
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    public final int getPortNumber() {
        return this.mPortNumber;
    }

    @Override
    public void onConnect(MidiReceiver midiReceiver) {
        this.mDispatcher.getSender().connect(midiReceiver);
    }

    @Override
    public void onDisconnect(MidiReceiver midiReceiver) {
        this.mDispatcher.getSender().disconnect(midiReceiver);
    }

}

