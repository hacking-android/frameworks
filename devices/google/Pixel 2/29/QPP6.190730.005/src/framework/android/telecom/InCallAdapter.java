/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.telecom.PhoneAccountHandle;
import com.android.internal.telecom.IInCallAdapter;
import java.util.List;

public final class InCallAdapter {
    private final IInCallAdapter mAdapter;

    public InCallAdapter(IInCallAdapter iInCallAdapter) {
        this.mAdapter = iInCallAdapter;
    }

    public void answerCall(String string2, int n) {
        try {
            this.mAdapter.answerCall(string2, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void conference(String string2, String string3) {
        try {
            this.mAdapter.conference(string2, string3);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void deflectCall(String string2, Uri uri) {
        try {
            this.mAdapter.deflectCall(string2, uri);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void disconnectCall(String string2) {
        try {
            this.mAdapter.disconnectCall(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void handoverTo(String string2, PhoneAccountHandle phoneAccountHandle, int n, Bundle bundle) {
        try {
            this.mAdapter.handoverTo(string2, phoneAccountHandle, n, bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void holdCall(String string2) {
        try {
            this.mAdapter.holdCall(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void mergeConference(String string2) {
        try {
            this.mAdapter.mergeConference(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void mute(boolean bl) {
        try {
            this.mAdapter.mute(bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void phoneAccountSelected(String string2, PhoneAccountHandle phoneAccountHandle, boolean bl) {
        try {
            this.mAdapter.phoneAccountSelected(string2, phoneAccountHandle, bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void playDtmfTone(String string2, char c) {
        try {
            this.mAdapter.playDtmfTone(string2, c);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void postDialContinue(String string2, boolean bl) {
        try {
            this.mAdapter.postDialContinue(string2, bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void pullExternalCall(String string2) {
        try {
            this.mAdapter.pullExternalCall(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void putExtra(String string2, String string3, int n) {
        try {
            Bundle bundle = new Bundle();
            bundle.putInt(string3, n);
            this.mAdapter.putExtras(string2, bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void putExtra(String string2, String string3, String string4) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(string3, string4);
            this.mAdapter.putExtras(string2, bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void putExtra(String string2, String string3, boolean bl) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean(string3, bl);
            this.mAdapter.putExtras(string2, bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void putExtras(String string2, Bundle bundle) {
        try {
            this.mAdapter.putExtras(string2, bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void rejectCall(String string2, boolean bl, String string3) {
        try {
            this.mAdapter.rejectCall(string2, bl, string3);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void removeExtras(String string2, List<String> list) {
        try {
            this.mAdapter.removeExtras(string2, list);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void requestBluetoothAudio(String string2) {
        try {
            this.mAdapter.setAudioRoute(2, string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void respondToRttRequest(String string2, int n, boolean bl) {
        try {
            this.mAdapter.respondToRttRequest(string2, n, bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void sendCallEvent(String string2, String string3, int n, Bundle bundle) {
        try {
            this.mAdapter.sendCallEvent(string2, string3, n, bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void sendRttRequest(String string2) {
        try {
            this.mAdapter.sendRttRequest(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void setAudioRoute(int n) {
        try {
            this.mAdapter.setAudioRoute(n, null);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void setRttMode(String string2, int n) {
        try {
            this.mAdapter.setRttMode(string2, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void splitFromConference(String string2) {
        try {
            this.mAdapter.splitFromConference(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void stopDtmfTone(String string2) {
        try {
            this.mAdapter.stopDtmfTone(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void stopRtt(String string2) {
        try {
            this.mAdapter.stopRtt(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void swapConference(String string2) {
        try {
            this.mAdapter.swapConference(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void turnProximitySensorOff(boolean bl) {
        try {
            this.mAdapter.turnOffProximitySensor(bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void turnProximitySensorOn() {
        try {
            this.mAdapter.turnOnProximitySensor();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void unholdCall(String string2) {
        try {
            this.mAdapter.unholdCall(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }
}

