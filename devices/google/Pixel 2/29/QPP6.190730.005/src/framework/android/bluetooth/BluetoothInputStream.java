/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.InputStream;

final class BluetoothInputStream
extends InputStream {
    private BluetoothSocket mSocket;

    BluetoothInputStream(BluetoothSocket bluetoothSocket) {
        this.mSocket = bluetoothSocket;
    }

    @Override
    public int available() throws IOException {
        return this.mSocket.available();
    }

    @Override
    public void close() throws IOException {
        this.mSocket.close();
    }

    @Override
    public int read() throws IOException {
        byte[] arrby = new byte[1];
        if (this.mSocket.read(arrby, 0, 1) == 1) {
            return arrby[0] & 255;
        }
        return -1;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (arrby != null) {
            if ((n | n2) >= 0 && n2 <= arrby.length - n) {
                return this.mSocket.read(arrby, n, n2);
            }
            throw new ArrayIndexOutOfBoundsException("invalid offset or length");
        }
        throw new NullPointerException("byte array is null");
    }
}

