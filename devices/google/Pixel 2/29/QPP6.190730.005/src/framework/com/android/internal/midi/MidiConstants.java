/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.midi;

public final class MidiConstants {
    public static final int[] CHANNEL_BYTE_LENGTHS = new int[]{3, 3, 3, 3, 2, 2, 3};
    public static final byte STATUS_ACTIVE_SENSING = -2;
    public static final byte STATUS_CHANNEL_MASK = 15;
    public static final byte STATUS_CHANNEL_PRESSURE = -48;
    public static final byte STATUS_COMMAND_MASK = -16;
    public static final byte STATUS_CONTINUE = -5;
    public static final byte STATUS_CONTROL_CHANGE = -80;
    public static final byte STATUS_END_SYSEX = -9;
    public static final byte STATUS_MIDI_TIME_CODE = -15;
    public static final byte STATUS_NOTE_OFF = -128;
    public static final byte STATUS_NOTE_ON = -112;
    public static final byte STATUS_PITCH_BEND = -32;
    public static final byte STATUS_POLYPHONIC_AFTERTOUCH = -96;
    public static final byte STATUS_PROGRAM_CHANGE = -64;
    public static final byte STATUS_RESET = -1;
    public static final byte STATUS_SONG_POSITION = -14;
    public static final byte STATUS_SONG_SELECT = -13;
    public static final byte STATUS_START = -6;
    public static final byte STATUS_STOP = -4;
    public static final byte STATUS_SYSTEM_EXCLUSIVE = -16;
    public static final byte STATUS_TIMING_CLOCK = -8;
    public static final byte STATUS_TUNE_REQUEST = -10;
    public static final int[] SYSTEM_BYTE_LENGTHS = new int[]{1, 2, 3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    public static boolean allowRunningStatus(byte by) {
        boolean bl = by >= -128 && by < -16;
        return bl;
    }

    public static boolean cancelsRunningStatus(byte by) {
        boolean bl = by >= -16 && by <= -9;
        return bl;
    }

    public static int getBytesPerMessage(byte by) {
        if ((by = (byte)(by & 255)) >= 240) {
            return SYSTEM_BYTE_LENGTHS[by & 15];
        }
        if (by >= 128) {
            return CHANNEL_BYTE_LENGTHS[(by >> 4) - 8];
        }
        return 0;
    }

    public static boolean isAllActiveSensing(byte[] arrby, int n, int n2) {
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            int n4 = n3;
            if (arrby[n + i] != -2) {
                n4 = n3 + 1;
            }
            n3 = n4;
        }
        boolean bl = n3 == 0;
        return bl;
    }
}

