/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import java.io.IOException;

public abstract class MidiReceiver {
    private final int mMaxMessageSize;

    public MidiReceiver() {
        this.mMaxMessageSize = Integer.MAX_VALUE;
    }

    public MidiReceiver(int n) {
        this.mMaxMessageSize = n;
    }

    public void flush() throws IOException {
        this.onFlush();
    }

    public final int getMaxMessageSize() {
        return this.mMaxMessageSize;
    }

    public void onFlush() throws IOException {
    }

    public abstract void onSend(byte[] var1, int var2, int var3, long var4) throws IOException;

    public void send(byte[] arrby, int n, int n2) throws IOException {
        this.send(arrby, n, n2, 0L);
    }

    public void send(byte[] arrby, int n, int n2, long l) throws IOException {
        int n3 = this.getMaxMessageSize();
        while (n2 > 0) {
            int n4 = n2 > n3 ? n3 : n2;
            this.onSend(arrby, n, n4, l);
            n += n4;
            n2 -= n4;
        }
    }
}

