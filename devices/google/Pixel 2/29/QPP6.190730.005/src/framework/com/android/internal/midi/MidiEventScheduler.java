/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.midi;

import android.media.midi.MidiReceiver;
import com.android.internal.midi.EventScheduler;
import java.io.IOException;

public class MidiEventScheduler
extends EventScheduler {
    private static final int POOL_EVENT_SIZE = 16;
    private static final String TAG = "MidiEventScheduler";
    private MidiReceiver mReceiver = new SchedulingReceiver();

    private MidiEvent createScheduledEvent(byte[] arrby, int n, int n2, long l) {
        MidiEvent midiEvent;
        if (n2 > 16) {
            midiEvent = new MidiEvent(arrby, n, n2, l);
        } else {
            midiEvent = (MidiEvent)this.removeEventfromPool();
            if (midiEvent == null) {
                midiEvent = new MidiEvent(16);
            }
            System.arraycopy(arrby, n, midiEvent.data, 0, n2);
            midiEvent.count = n2;
            midiEvent.setTimestamp(l);
        }
        return midiEvent;
    }

    @Override
    public void addEventToPool(EventScheduler.SchedulableEvent schedulableEvent) {
        if (schedulableEvent instanceof MidiEvent && ((MidiEvent)schedulableEvent).data.length == 16) {
            super.addEventToPool(schedulableEvent);
        }
    }

    public MidiReceiver getReceiver() {
        return this.mReceiver;
    }

    public static class MidiEvent
    extends EventScheduler.SchedulableEvent {
        public int count = 0;
        public byte[] data;

        private MidiEvent(int n) {
            super(0L);
            this.data = new byte[n];
        }

        private MidiEvent(byte[] arrby, int n, int n2, long l) {
            super(l);
            this.data = new byte[n2];
            System.arraycopy(arrby, n, this.data, 0, n2);
            this.count = n2;
        }

        public String toString() {
            String string2 = "Event: ";
            for (int i = 0; i < this.count; ++i) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(this.data[i]);
                stringBuilder.append(", ");
                string2 = stringBuilder.toString();
            }
            return string2;
        }
    }

    private class SchedulingReceiver
    extends MidiReceiver {
        private SchedulingReceiver() {
        }

        @Override
        public void onFlush() {
            MidiEventScheduler.this.flush();
        }

        @Override
        public void onSend(byte[] object, int n, int n2, long l) throws IOException {
            if ((object = MidiEventScheduler.this.createScheduledEvent(object, n, n2, l)) != null) {
                MidiEventScheduler.this.add((EventScheduler.SchedulableEvent)object);
            }
        }
    }

}

