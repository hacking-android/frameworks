/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import android.media.midi.MidiReceiver;

public abstract class MidiSender {
    public void connect(MidiReceiver midiReceiver) {
        if (midiReceiver != null) {
            this.onConnect(midiReceiver);
            return;
        }
        throw new NullPointerException("receiver null in MidiSender.connect");
    }

    public void disconnect(MidiReceiver midiReceiver) {
        if (midiReceiver != null) {
            this.onDisconnect(midiReceiver);
            return;
        }
        throw new NullPointerException("receiver null in MidiSender.disconnect");
    }

    public abstract void onConnect(MidiReceiver var1);

    public abstract void onDisconnect(MidiReceiver var1);
}

