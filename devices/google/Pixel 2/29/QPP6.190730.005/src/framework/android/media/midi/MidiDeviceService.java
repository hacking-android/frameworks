/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import android.app.Service;
import android.content.Intent;
import android.media.midi.IMidiDeviceServer;
import android.media.midi.IMidiManager;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiDeviceServer;
import android.media.midi.MidiDeviceStatus;
import android.media.midi.MidiReceiver;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

public abstract class MidiDeviceService
extends Service {
    public static final String SERVICE_INTERFACE = "android.media.midi.MidiDeviceService";
    private static final String TAG = "MidiDeviceService";
    private final MidiDeviceServer.Callback mCallback = new MidiDeviceServer.Callback(){

        @Override
        public void onClose() {
            MidiDeviceService.this.onClose();
        }

        @Override
        public void onDeviceStatusChanged(MidiDeviceServer midiDeviceServer, MidiDeviceStatus midiDeviceStatus) {
            MidiDeviceService.this.onDeviceStatusChanged(midiDeviceStatus);
        }
    };
    private MidiDeviceInfo mDeviceInfo;
    private IMidiManager mMidiManager;
    private MidiDeviceServer mServer;

    public final MidiDeviceInfo getDeviceInfo() {
        return this.mDeviceInfo;
    }

    public final MidiReceiver[] getOutputPortReceivers() {
        MidiDeviceServer midiDeviceServer = this.mServer;
        if (midiDeviceServer == null) {
            return null;
        }
        return midiDeviceServer.getOutputPortReceivers();
    }

    @Override
    public IBinder onBind(Intent object) {
        if (SERVICE_INTERFACE.equals(((Intent)object).getAction()) && (object = this.mServer) != null) {
            return ((MidiDeviceServer)object).getBinderInterface().asBinder();
        }
        return null;
    }

    public void onClose() {
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onCreate() {
        void var2_9;
        this.mMidiManager = IMidiManager.Stub.asInterface(ServiceManager.getService("midi"));
        try {
            void var2_5;
            MidiReceiver[] arrmidiReceiver;
            MidiDeviceInfo midiDeviceInfo = this.mMidiManager.getServiceDeviceInfo(this.getPackageName(), this.getClass().getName());
            if (midiDeviceInfo == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not find MidiDeviceInfo for MidiDeviceService ");
                stringBuilder.append(this);
                Log.e(TAG, stringBuilder.toString());
                return;
            }
            this.mDeviceInfo = midiDeviceInfo;
            MidiReceiver[] arrmidiReceiver2 = arrmidiReceiver = this.onGetInputPortReceivers();
            if (arrmidiReceiver == null) {
                MidiReceiver[] arrmidiReceiver3 = new MidiReceiver[]{};
            }
            MidiDeviceServer midiDeviceServer = new MidiDeviceServer(this.mMidiManager, (MidiReceiver[])var2_5, midiDeviceInfo, this.mCallback);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "RemoteException in IMidiManager.getServiceDeviceInfo");
            Object var2_8 = null;
        }
        this.mServer = var2_9;
    }

    public void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) {
    }

    public abstract MidiReceiver[] onGetInputPortReceivers();

}

