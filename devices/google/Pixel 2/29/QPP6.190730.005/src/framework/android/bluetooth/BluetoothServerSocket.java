/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.util.Log;
import java.io.Closeable;
import java.io.IOException;

public final class BluetoothServerSocket
implements Closeable {
    private static final boolean DBG = false;
    private static final String TAG = "BluetoothServerSocket";
    private int mChannel;
    private Handler mHandler;
    private int mMessage;
    @UnsupportedAppUsage
    final BluetoothSocket mSocket;

    BluetoothServerSocket(int n, boolean bl, boolean bl2, int n2) throws IOException {
        this.mChannel = n2;
        this.mSocket = new BluetoothSocket(n, -1, bl, bl2, null, n2, null);
        if (n2 == -2) {
            this.mSocket.setExcludeSdp(true);
        }
    }

    BluetoothServerSocket(int n, boolean bl, boolean bl2, int n2, boolean bl3, boolean bl4) throws IOException {
        this.mChannel = n2;
        this.mSocket = new BluetoothSocket(n, -1, bl, bl2, null, n2, null, bl3, bl4);
        if (n2 == -2) {
            this.mSocket.setExcludeSdp(true);
        }
    }

    BluetoothServerSocket(int n, boolean bl, boolean bl2, ParcelUuid parcelUuid) throws IOException {
        this.mSocket = new BluetoothSocket(n, -1, bl, bl2, null, -1, parcelUuid);
        this.mChannel = this.mSocket.getPort();
    }

    public BluetoothSocket accept() throws IOException {
        return this.accept(-1);
    }

    public BluetoothSocket accept(int n) throws IOException {
        return this.mSocket.accept(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        synchronized (this) {
            if (this.mHandler != null) {
                this.mHandler.obtainMessage(this.mMessage).sendToTarget();
            }
        }
        this.mSocket.close();
    }

    public int getChannel() {
        return this.mChannel;
    }

    public int getPsm() {
        return this.mChannel;
    }

    void setChannel(int n) {
        Object object = this.mSocket;
        if (object != null && ((BluetoothSocket)object).getPort() != n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("The port set is different that the underlying port. mSocket.getPort(): ");
            ((StringBuilder)object).append(this.mSocket.getPort());
            ((StringBuilder)object).append(" requested newChannel: ");
            ((StringBuilder)object).append(n);
            Log.w(TAG, ((StringBuilder)object).toString());
        }
        this.mChannel = n;
    }

    void setCloseHandler(Handler handler, int n) {
        synchronized (this) {
            this.mHandler = handler;
            this.mMessage = n;
            return;
        }
    }

    void setServiceName(String string2) {
        this.mSocket.setServiceName(string2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ServerSocket: Type: ");
        int n = this.mSocket.getConnectionType();
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        stringBuilder.append("TYPE_L2CAP_LE");
                    }
                } else {
                    stringBuilder.append("TYPE_L2CAP");
                }
            } else {
                stringBuilder.append("TYPE_SCO");
            }
        } else {
            stringBuilder.append("TYPE_RFCOMM");
        }
        stringBuilder.append(" Channel: ");
        stringBuilder.append(this.mChannel);
        return stringBuilder.toString();
    }
}

