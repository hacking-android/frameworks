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
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import libcore.io.IoUtils;

public final class MidiInputPort
extends MidiReceiver
implements Closeable {
    private static final String TAG = "MidiInputPort";
    private final byte[] mBuffer = new byte[1024];
    private IMidiDeviceServer mDeviceServer;
    private FileDescriptor mFileDescriptor;
    private final CloseGuard mGuard = CloseGuard.get();
    private boolean mIsClosed;
    private FileOutputStream mOutputStream;
    private final int mPortNumber;
    private final IBinder mToken;

    MidiInputPort(IMidiDeviceServer iMidiDeviceServer, IBinder iBinder, FileDescriptor fileDescriptor, int n) {
        super(1015);
        this.mDeviceServer = iMidiDeviceServer;
        this.mToken = iBinder;
        this.mFileDescriptor = fileDescriptor;
        this.mPortNumber = n;
        this.mOutputStream = new FileOutputStream(fileDescriptor);
        this.mGuard.open("close");
    }

    MidiInputPort(FileDescriptor fileDescriptor, int n) {
        this(null, null, fileDescriptor, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    FileDescriptor claimFileDescriptor() {
        CloseGuard closeGuard = this.mGuard;
        synchronized (closeGuard) {
            FileDescriptor fileDescriptor;
            byte[] arrby = this.mBuffer;
            synchronized (arrby) {
                fileDescriptor = this.mFileDescriptor;
                if (fileDescriptor == null) {
                    return null;
                }
                IoUtils.closeQuietly((AutoCloseable)this.mOutputStream);
                this.mFileDescriptor = null;
                this.mOutputStream = null;
            }
            this.mIsClosed = true;
            return fileDescriptor;
        }
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
            byte[] arrby = this.mBuffer;
            synchronized (arrby) {
                if (this.mFileDescriptor != null) {
                    IoUtils.closeQuietly((FileDescriptor)this.mFileDescriptor);
                    this.mFileDescriptor = null;
                }
                if (this.mOutputStream != null) {
                    this.mOutputStream.close();
                    this.mOutputStream = null;
                }
            }
            IMidiDeviceServer iMidiDeviceServer = this.mDeviceServer;
            if (iMidiDeviceServer != null) {
                try {
                    this.mDeviceServer.closePort(this.mToken);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "RemoteException in MidiInputPort.close()");
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

    IMidiDeviceServer getDeviceServer() {
        return this.mDeviceServer;
    }

    public final int getPortNumber() {
        return this.mPortNumber;
    }

    IBinder getToken() {
        return this.mToken;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onFlush() throws IOException {
        byte[] arrby = this.mBuffer;
        synchronized (arrby) {
            if (this.mOutputStream != null) {
                int n = MidiPortImpl.packFlush(this.mBuffer);
                this.mOutputStream.write(this.mBuffer, 0, n);
                return;
            }
            IOException iOException = new IOException("MidiInputPort is closed");
            throw iOException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onSend(byte[] object, int n, int n2, long l) throws IOException {
        if (n >= 0 && n2 >= 0 && n + n2 <= ((byte[])object).length) {
            if (n2 > 1015) {
                throw new IllegalArgumentException("count exceeds max message size");
            }
            byte[] arrby = this.mBuffer;
            synchronized (arrby) {
                if (this.mOutputStream != null) {
                    n = MidiPortImpl.packData(object, n, n2, l, this.mBuffer);
                    this.mOutputStream.write(this.mBuffer, 0, n);
                    return;
                }
                object = new IOException("MidiInputPort is closed");
                throw object;
            }
        }
        throw new IllegalArgumentException("offset or count out of range");
    }
}

