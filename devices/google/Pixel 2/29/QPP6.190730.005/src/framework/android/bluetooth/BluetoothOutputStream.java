/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.OutputStream;

final class BluetoothOutputStream
extends OutputStream {
    private BluetoothSocket mSocket;

    BluetoothOutputStream(BluetoothSocket bluetoothSocket) {
        this.mSocket = bluetoothSocket;
    }

    @Override
    public void close() throws IOException {
        this.mSocket.close();
    }

    @Override
    public void flush() throws IOException {
        this.mSocket.flush();
    }

    @Override
    public void write(int n) throws IOException {
        byte by = (byte)n;
        this.mSocket.write(new byte[]{by}, 0, 1);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (arrby != null) {
            if ((n | n2) >= 0 && n2 <= arrby.length - n) {
                this.mSocket.write(arrby, n, n2);
                return;
            }
            throw new IndexOutOfBoundsException("invalid offset or length");
        }
        throw new NullPointerException("buffer is null");
    }
}

