/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.midi;

import android.media.midi.MidiReceiver;
import com.android.internal.midi.MidiConstants;
import java.io.IOException;

public class MidiFramer
extends MidiReceiver {
    public String TAG = "MidiFramer";
    private byte[] mBuffer = new byte[3];
    private int mCount;
    private boolean mInSysEx;
    private int mNeeded;
    private MidiReceiver mReceiver;
    private byte mRunningStatus;

    public MidiFramer(MidiReceiver midiReceiver) {
        this.mReceiver = midiReceiver;
    }

    public static String formatMidiData(byte[] arrby, int n, int n2) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("MIDI+");
        charSequence.append(n);
        charSequence.append(" : ");
        charSequence = charSequence.toString();
        for (int i = 0; i < n2; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(String.format("0x%02X, ", arrby[n + i]));
            charSequence = stringBuilder.toString();
        }
        return charSequence;
    }

    @Override
    public void onSend(byte[] arrby, int n, int n2, long l) throws IOException {
        int n3 = this.mInSysEx ? n : -1;
        int n4 = n;
        for (int i = 0; i < n2; ++i) {
            byte by = arrby[n4];
            n = by & 255;
            if (n >= 128) {
                if (n < 240) {
                    this.mRunningStatus = by;
                    this.mCount = 1;
                    this.mNeeded = MidiConstants.getBytesPerMessage(by) - 1;
                    n = n3;
                } else if (n < 248) {
                    if (n == 240) {
                        this.mInSysEx = true;
                        n = n4;
                    } else if (n == 247) {
                        n = n3;
                        if (this.mInSysEx) {
                            this.mReceiver.send(arrby, n3, n4 - n3 + 1, l);
                            this.mInSysEx = false;
                            n = -1;
                        }
                    } else {
                        this.mBuffer[0] = by;
                        this.mRunningStatus = (byte)(false ? 1 : 0);
                        this.mCount = 1;
                        this.mNeeded = MidiConstants.getBytesPerMessage(by) - 1;
                        n = n3;
                    }
                } else {
                    n = n3;
                    if (this.mInSysEx) {
                        this.mReceiver.send(arrby, n3, n4 - n3, l);
                        n = n4 + 1;
                    }
                    this.mReceiver.send(arrby, n4, 1, l);
                }
            } else {
                n = n3;
                if (!this.mInSysEx) {
                    int n5;
                    byte[] arrby2 = this.mBuffer;
                    n = this.mCount;
                    this.mCount = n + 1;
                    arrby2[n] = by;
                    this.mNeeded = n5 = this.mNeeded - 1;
                    n = n3;
                    if (n5 == 0) {
                        n = this.mRunningStatus;
                        if (n != 0) {
                            arrby2[0] = (byte)n;
                        }
                        this.mReceiver.send(this.mBuffer, 0, this.mCount, l);
                        this.mNeeded = MidiConstants.getBytesPerMessage(this.mBuffer[0]) - 1;
                        this.mCount = 1;
                        n = n3;
                    }
                }
            }
            ++n4;
            n3 = n;
        }
        if (n3 >= 0 && n3 < n4) {
            this.mReceiver.send(arrby, n3, n4 - n3, l);
        }
    }
}

