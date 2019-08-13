/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.midi;

import android.media.midi.MidiReceiver;
import android.media.midi.MidiSender;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public final class MidiDispatcher
extends MidiReceiver {
    private final MidiReceiverFailureHandler mFailureHandler;
    private final CopyOnWriteArrayList<MidiReceiver> mReceivers = new CopyOnWriteArrayList();
    private final MidiSender mSender = new MidiSender(){

        @Override
        public void onConnect(MidiReceiver midiReceiver) {
            MidiDispatcher.this.mReceivers.add(midiReceiver);
        }

        @Override
        public void onDisconnect(MidiReceiver midiReceiver) {
            MidiDispatcher.this.mReceivers.remove(midiReceiver);
        }
    };

    public MidiDispatcher() {
        this(null);
    }

    public MidiDispatcher(MidiReceiverFailureHandler midiReceiverFailureHandler) {
        this.mFailureHandler = midiReceiverFailureHandler;
    }

    public int getReceiverCount() {
        return this.mReceivers.size();
    }

    public MidiSender getSender() {
        return this.mSender;
    }

    @Override
    public void onFlush() throws IOException {
        for (MidiReceiver midiReceiver : this.mReceivers) {
            try {
                midiReceiver.flush();
            }
            catch (IOException iOException) {
                this.mReceivers.remove(midiReceiver);
                MidiReceiverFailureHandler midiReceiverFailureHandler = this.mFailureHandler;
                if (midiReceiverFailureHandler == null) continue;
                midiReceiverFailureHandler.onReceiverFailure(midiReceiver, iOException);
            }
        }
    }

    @Override
    public void onSend(byte[] arrby, int n, int n2, long l) throws IOException {
        for (MidiReceiver midiReceiver : this.mReceivers) {
            try {
                midiReceiver.send(arrby, n, n2, l);
            }
            catch (IOException iOException) {
                this.mReceivers.remove(midiReceiver);
                MidiReceiverFailureHandler midiReceiverFailureHandler = this.mFailureHandler;
                if (midiReceiverFailureHandler == null) continue;
                midiReceiverFailureHandler.onReceiverFailure(midiReceiver, iOException);
            }
        }
    }

    public static interface MidiReceiverFailureHandler {
        public void onReceiverFailure(MidiReceiver var1, IOException var2);
    }

}

