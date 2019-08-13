/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import android.bluetooth.BluetoothDevice;
import android.media.midi.IMidiDeviceListener;
import android.media.midi.IMidiDeviceOpenCallback;
import android.media.midi.IMidiDeviceServer;
import android.media.midi.IMidiManager;
import android.media.midi.MidiDevice;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiDeviceServer;
import android.media.midi.MidiDeviceStatus;
import android.media.midi.MidiReceiver;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.concurrent.ConcurrentHashMap;

public final class MidiManager {
    public static final String BLUETOOTH_MIDI_SERVICE_CLASS = "com.android.bluetoothmidiservice.BluetoothMidiService";
    public static final String BLUETOOTH_MIDI_SERVICE_INTENT = "android.media.midi.BluetoothMidiService";
    public static final String BLUETOOTH_MIDI_SERVICE_PACKAGE = "com.android.bluetoothmidiservice";
    private static final String TAG = "MidiManager";
    private ConcurrentHashMap<DeviceCallback, DeviceListener> mDeviceListeners = new ConcurrentHashMap();
    private final IMidiManager mService;
    private final IBinder mToken = new Binder();

    public MidiManager(IMidiManager iMidiManager) {
        this.mService = iMidiManager;
    }

    private void sendOpenDeviceResponse(final MidiDevice midiDevice, final OnDeviceOpenedListener onDeviceOpenedListener, Handler handler) {
        if (handler != null) {
            handler.post(new Runnable(){

                @Override
                public void run() {
                    onDeviceOpenedListener.onDeviceOpened(midiDevice);
                }
            });
        } else {
            onDeviceOpenedListener.onDeviceOpened(midiDevice);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public MidiDeviceServer createDeviceServer(MidiReceiver[] arrmidiReceiver, int n, String[] arrstring, String[] arrstring2, Bundle bundle, int n2, MidiDeviceServer.Callback callback) {
        void var1_4;
        IMidiManager iMidiManager = this.mService;
        try {
            MidiDeviceServer midiDeviceServer = new MidiDeviceServer(iMidiManager, arrmidiReceiver, n, callback);
            if (this.mService.registerDeviceServer(midiDeviceServer.getBinderInterface(), arrmidiReceiver.length, n, arrstring, arrstring2, bundle, n2) != null) return midiDeviceServer;
            Log.e(TAG, "registerVirtualDevice failed");
            return null;
        }
        catch (RemoteException remoteException) {
            throw var1_4.rethrowFromSystemServer();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw var1_4.rethrowFromSystemServer();
    }

    public MidiDeviceInfo[] getDevices() {
        try {
            MidiDeviceInfo[] arrmidiDeviceInfo = this.mService.getDevices();
            return arrmidiDeviceInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void openBluetoothDevice(BluetoothDevice bluetoothDevice, OnDeviceOpenedListener object, Handler handler) {
        object = new IMidiDeviceOpenCallback.Stub((OnDeviceOpenedListener)object, handler){
            final /* synthetic */ Handler val$handlerF;
            final /* synthetic */ OnDeviceOpenedListener val$listenerF;
            {
                this.val$listenerF = onDeviceOpenedListener;
                this.val$handlerF = handler;
            }

            @Override
            public void onDeviceOpened(IMidiDeviceServer iMidiDeviceServer, IBinder iBinder) {
                MidiDevice midiDevice;
                MidiDevice midiDevice2 = midiDevice = null;
                if (iMidiDeviceServer != null) {
                    try {
                        MidiDeviceInfo midiDeviceInfo = iMidiDeviceServer.getDeviceInfo();
                        midiDevice2 = new MidiDevice(midiDeviceInfo, iMidiDeviceServer, MidiManager.this.mService, MidiManager.this.mToken, iBinder);
                    }
                    catch (RemoteException remoteException) {
                        Log.e(MidiManager.TAG, "remote exception in getDeviceInfo()");
                        midiDevice2 = midiDevice;
                    }
                }
                MidiManager.this.sendOpenDeviceResponse(midiDevice2, this.val$listenerF, this.val$handlerF);
            }
        };
        try {
            this.mService.openBluetoothDevice(this.mToken, bluetoothDevice, (IMidiDeviceOpenCallback)object);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void openDevice(final MidiDeviceInfo midiDeviceInfo, OnDeviceOpenedListener object, Handler handler) {
        object = new IMidiDeviceOpenCallback.Stub((OnDeviceOpenedListener)object, handler){
            final /* synthetic */ Handler val$handlerF;
            final /* synthetic */ OnDeviceOpenedListener val$listenerF;
            {
                this.val$listenerF = onDeviceOpenedListener;
                this.val$handlerF = handler;
            }

            @Override
            public void onDeviceOpened(IMidiDeviceServer object, IBinder iBinder) {
                object = object != null ? new MidiDevice(midiDeviceInfo, (IMidiDeviceServer)object, MidiManager.this.mService, MidiManager.this.mToken, iBinder) : null;
                MidiManager.this.sendOpenDeviceResponse((MidiDevice)object, this.val$listenerF, this.val$handlerF);
            }
        };
        try {
            this.mService.openDevice(this.mToken, midiDeviceInfo, (IMidiDeviceOpenCallback)object);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void registerDeviceCallback(DeviceCallback deviceCallback, Handler object) {
        object = new DeviceListener(deviceCallback, (Handler)object);
        try {
            this.mService.registerListener(this.mToken, (IMidiDeviceListener)object);
            this.mDeviceListeners.put(deviceCallback, (DeviceListener)object);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void unregisterDeviceCallback(DeviceCallback object) {
        if ((object = this.mDeviceListeners.remove(object)) != null) {
            try {
                this.mService.unregisterListener(this.mToken, (IMidiDeviceListener)object);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public static class DeviceCallback {
        public void onDeviceAdded(MidiDeviceInfo midiDeviceInfo) {
        }

        public void onDeviceRemoved(MidiDeviceInfo midiDeviceInfo) {
        }

        public void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) {
        }
    }

    private class DeviceListener
    extends IMidiDeviceListener.Stub {
        private final DeviceCallback mCallback;
        private final Handler mHandler;

        public DeviceListener(DeviceCallback deviceCallback, Handler handler) {
            this.mCallback = deviceCallback;
            this.mHandler = handler;
        }

        @Override
        public void onDeviceAdded(final MidiDeviceInfo midiDeviceInfo) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        DeviceListener.this.mCallback.onDeviceAdded(midiDeviceInfo);
                    }
                });
            } else {
                this.mCallback.onDeviceAdded(midiDeviceInfo);
            }
        }

        @Override
        public void onDeviceRemoved(final MidiDeviceInfo midiDeviceInfo) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        DeviceListener.this.mCallback.onDeviceRemoved(midiDeviceInfo);
                    }
                });
            } else {
                this.mCallback.onDeviceRemoved(midiDeviceInfo);
            }
        }

        @Override
        public void onDeviceStatusChanged(final MidiDeviceStatus midiDeviceStatus) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        DeviceListener.this.mCallback.onDeviceStatusChanged(midiDeviceStatus);
                    }
                });
            } else {
                this.mCallback.onDeviceStatusChanged(midiDeviceStatus);
            }
        }

    }

    public static interface OnDeviceOpenedListener {
        public void onDeviceOpened(MidiDevice var1);
    }

}

