/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothInputStream;
import android.bluetooth.BluetoothOutputStream;
import android.bluetooth.IBluetooth;
import android.bluetooth.IBluetoothManagerCallback;
import android.bluetooth.IBluetoothSocketManager;
import android.net.LocalSocket;
import android.os.ParcelFileDescriptor;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

public final class BluetoothSocket
implements Closeable {
    static final int BTSOCK_FLAG_NO_SDP = 4;
    private static final boolean DBG = Log.isLoggable("BluetoothSocket", 3);
    @UnsupportedAppUsage
    static final int EADDRINUSE = 98;
    static final int EBADFD = 77;
    static final int MAX_L2CAP_PACKAGE_SIZE = 65535;
    public static final int MAX_RFCOMM_CHANNEL = 30;
    private static final int PROXY_CONNECTION_TIMEOUT = 5000;
    static final int SEC_FLAG_AUTH = 2;
    static final int SEC_FLAG_AUTH_16_DIGIT = 16;
    static final int SEC_FLAG_AUTH_MITM = 8;
    static final int SEC_FLAG_ENCRYPT = 1;
    private static final int SOCK_SIGNAL_SIZE = 20;
    private static final String TAG = "BluetoothSocket";
    public static final int TYPE_L2CAP = 3;
    public static final int TYPE_L2CAP_BREDR = 3;
    public static final int TYPE_L2CAP_LE = 4;
    public static final int TYPE_RFCOMM = 1;
    public static final int TYPE_SCO = 2;
    private static final boolean VDBG = Log.isLoggable("BluetoothSocket", 2);
    private String mAddress;
    private final boolean mAuth;
    private boolean mAuthMitm = false;
    private BluetoothDevice mDevice;
    private final boolean mEncrypt;
    private boolean mExcludeSdp = false;
    private int mFd;
    private final BluetoothInputStream mInputStream;
    private ByteBuffer mL2capBuffer = null;
    private int mMaxRxPacketSize = 0;
    private int mMaxTxPacketSize = 0;
    private boolean mMin16DigitPin = false;
    private final BluetoothOutputStream mOutputStream;
    @UnsupportedAppUsage
    private ParcelFileDescriptor mPfd;
    @UnsupportedAppUsage
    private int mPort;
    private String mServiceName;
    @UnsupportedAppUsage
    private LocalSocket mSocket;
    private InputStream mSocketIS;
    private OutputStream mSocketOS;
    private volatile SocketState mSocketState;
    private final int mType;
    private final ParcelUuid mUuid;

    BluetoothSocket(int n, int n2, boolean bl, boolean bl2, BluetoothDevice bluetoothDevice, int n3, ParcelUuid parcelUuid) throws IOException {
        this(n, n2, bl, bl2, bluetoothDevice, n3, parcelUuid, false, false);
    }

    BluetoothSocket(int n, int n2, boolean bl, boolean bl2, BluetoothDevice object, int n3, ParcelUuid parcelUuid, boolean bl3, boolean bl4) throws IOException {
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Creating new BluetoothSocket of type: ");
            stringBuilder.append(n);
            Log.d(TAG, stringBuilder.toString());
        }
        if (n == 1 && parcelUuid == null && n2 == -1 && n3 != -2 && (n3 < 1 || n3 > 30)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid RFCOMM channel: ");
            ((StringBuilder)object).append(n3);
            throw new IOException(((StringBuilder)object).toString());
        }
        this.mUuid = parcelUuid != null ? parcelUuid : new ParcelUuid(new UUID(0L, 0L));
        this.mType = n;
        this.mAuth = bl;
        this.mAuthMitm = bl3;
        this.mMin16DigitPin = bl4;
        this.mEncrypt = bl2;
        this.mDevice = object;
        this.mPort = n3;
        this.mFd = n2;
        this.mSocketState = SocketState.INIT;
        this.mAddress = object == null ? BluetoothAdapter.getDefaultAdapter().getAddress() : ((BluetoothDevice)object).getAddress();
        this.mInputStream = new BluetoothInputStream(this);
        this.mOutputStream = new BluetoothOutputStream(this);
    }

    private BluetoothSocket(int n, int n2, boolean bl, boolean bl2, String string2, int n3) throws IOException {
        this(n, n2, bl, bl2, new BluetoothDevice(string2), n3, null, false, false);
    }

    private BluetoothSocket(BluetoothSocket bluetoothSocket) {
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Creating new Private BluetoothSocket of type: ");
            stringBuilder.append(bluetoothSocket.mType);
            Log.d(TAG, stringBuilder.toString());
        }
        this.mUuid = bluetoothSocket.mUuid;
        this.mType = bluetoothSocket.mType;
        this.mAuth = bluetoothSocket.mAuth;
        this.mEncrypt = bluetoothSocket.mEncrypt;
        this.mPort = bluetoothSocket.mPort;
        this.mInputStream = new BluetoothInputStream(this);
        this.mOutputStream = new BluetoothOutputStream(this);
        this.mMaxRxPacketSize = bluetoothSocket.mMaxRxPacketSize;
        this.mMaxTxPacketSize = bluetoothSocket.mMaxTxPacketSize;
        this.mServiceName = bluetoothSocket.mServiceName;
        this.mExcludeSdp = bluetoothSocket.mExcludeSdp;
        this.mAuthMitm = bluetoothSocket.mAuthMitm;
        this.mMin16DigitPin = bluetoothSocket.mMin16DigitPin;
    }

    private BluetoothSocket acceptSocket(String charSequence) throws IOException {
        BluetoothSocket bluetoothSocket = new BluetoothSocket(this);
        bluetoothSocket.mSocketState = SocketState.CONNECTED;
        Object[] arrobject = this.mSocket.getAncillaryFileDescriptors();
        if (DBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("socket fd passed by stack fds: ");
            stringBuilder.append(Arrays.toString(arrobject));
            Log.d(TAG, stringBuilder.toString());
        }
        if (arrobject != null && arrobject.length == 1) {
            bluetoothSocket.mPfd = new ParcelFileDescriptor((FileDescriptor)arrobject[0]);
            bluetoothSocket.mSocket = LocalSocket.createConnectedLocalSocket((FileDescriptor)arrobject[0]);
            bluetoothSocket.mSocketIS = bluetoothSocket.mSocket.getInputStream();
            bluetoothSocket.mSocketOS = bluetoothSocket.mSocket.getOutputStream();
            bluetoothSocket.mAddress = charSequence;
            bluetoothSocket.mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice((String)charSequence);
            return bluetoothSocket;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("socket fd passed from stack failed, fds: ");
        ((StringBuilder)charSequence).append(Arrays.toString(arrobject));
        Log.e(TAG, ((StringBuilder)charSequence).toString());
        bluetoothSocket.close();
        throw new IOException("bt socket acept failed");
    }

    private String convertAddr(byte[] arrby) {
        return String.format(Locale.US, "%02X:%02X:%02X:%02X:%02X:%02X", arrby[0], arrby[1], arrby[2], arrby[3], arrby[4], arrby[5]);
    }

    private void createL2capRxBuffer() {
        int n = this.mType;
        if (n == 3 || n == 4) {
            StringBuilder stringBuilder;
            if (VDBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("  Creating mL2capBuffer: mMaxPacketSize: ");
                stringBuilder.append(this.mMaxRxPacketSize);
                Log.v(TAG, stringBuilder.toString());
            }
            this.mL2capBuffer = ByteBuffer.wrap(new byte[this.mMaxRxPacketSize]);
            if (VDBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("mL2capBuffer.remaining()");
                stringBuilder.append(this.mL2capBuffer.remaining());
                Log.v(TAG, stringBuilder.toString());
            }
            this.mL2capBuffer.limit(0);
            if (VDBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("mL2capBuffer.remaining() after limit(0):");
                stringBuilder.append(this.mL2capBuffer.remaining());
                Log.v(TAG, stringBuilder.toString());
            }
        }
    }

    private int fillL2capRxBuffer() throws IOException {
        this.mL2capBuffer.rewind();
        int n = this.mSocketIS.read(this.mL2capBuffer.array());
        if (n == -1) {
            this.mL2capBuffer.limit(0);
            return -1;
        }
        this.mL2capBuffer.limit(n);
        return n;
    }

    private int getSecurityFlags() {
        int n = 0;
        if (this.mAuth) {
            n = 0 | 2;
        }
        int n2 = n;
        if (this.mEncrypt) {
            n2 = n | 1;
        }
        n = n2;
        if (this.mExcludeSdp) {
            n = n2 | 4;
        }
        n2 = n;
        if (this.mAuthMitm) {
            n2 = n | 8;
        }
        n = n2;
        if (this.mMin16DigitPin) {
            n = n2 | 16;
        }
        return n;
    }

    private int readAll(InputStream object, byte[] arrby) throws IOException {
        int n = arrby.length;
        while (n > 0) {
            int n2 = ((InputStream)object).read(arrby, arrby.length - n, n);
            if (n2 > 0) {
                if ((n -= n2) == 0) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("readAll() looping, read partial size: ");
                stringBuilder.append(arrby.length - n);
                stringBuilder.append(", expect size: ");
                stringBuilder.append(arrby.length);
                Log.w(TAG, stringBuilder.toString());
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("read failed, socket might closed or timeout, read ret: ");
            ((StringBuilder)object).append(n2);
            throw new IOException(((StringBuilder)object).toString());
        }
        return arrby.length;
    }

    private int readInt(InputStream object) throws IOException {
        byte[] arrby = new byte[4];
        int n = this.readAll((InputStream)object, arrby);
        if (VDBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("inputStream.read ret: ");
            ((StringBuilder)object).append(n);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        object = ByteBuffer.wrap(arrby);
        ((ByteBuffer)object).order(ByteOrder.nativeOrder());
        return ((ByteBuffer)object).getInt();
    }

    private String waitSocketSignal(InputStream object) throws IOException {
        Object object2 = new byte[20];
        int n = this.readAll((InputStream)object, (byte[])object2);
        if (VDBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("waitSocketSignal read 20 bytes signal ret: ");
            ((StringBuilder)object).append(n);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        object = ByteBuffer.wrap(object2);
        ((ByteBuffer)object).order(ByteOrder.nativeOrder());
        short s = ((ByteBuffer)object).getShort();
        if (s == 20) {
            object2 = new byte[6];
            ((ByteBuffer)object).get((byte[])object2);
            n = ((ByteBuffer)object).getInt();
            int n2 = ((ByteBuffer)object).getInt();
            this.mMaxTxPacketSize = ((ByteBuffer)object).getShort() & 65535;
            this.mMaxRxPacketSize = ((ByteBuffer)object).getShort() & 65535;
            object2 = this.convertAddr((byte[])object2);
            if (VDBG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("waitSocketSignal: sig size: ");
                ((StringBuilder)object).append(s);
                ((StringBuilder)object).append(", remote addr: ");
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(", channel: ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(", status: ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" MaxRxPktSize: ");
                ((StringBuilder)object).append(this.mMaxRxPacketSize);
                ((StringBuilder)object).append(" MaxTxPktSize: ");
                ((StringBuilder)object).append(this.mMaxTxPacketSize);
                Log.d(TAG, ((StringBuilder)object).toString());
            }
            if (n2 == 0) {
                return object2;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Connection failure, status: ");
            ((StringBuilder)object).append(n2);
            throw new IOException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Connection failure, wrong signal size: ");
        ((StringBuilder)object).append(s);
        throw new IOException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    BluetoothSocket accept(int n) throws IOException {
        Object object;
        if (this.mSocketState != SocketState.LISTENING) {
            throw new IOException("bt socket is not in listen state");
        }
        if (n > 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("accept() set timeout (ms):");
            ((StringBuilder)object).append(n);
            Log.d(TAG, ((StringBuilder)object).toString());
            this.mSocket.setSoTimeout(n);
        }
        object = this.waitSocketSignal(this.mSocketIS);
        if (n > 0) {
            this.mSocket.setSoTimeout(0);
        }
        synchronized (this) {
            if (this.mSocketState == SocketState.LISTENING) {
                return this.acceptSocket((String)object);
            }
            object = new IOException("bt socket is not in listen state");
            throw object;
        }
    }

    int available() throws IOException {
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("available: ");
            stringBuilder.append(this.mSocketIS);
            Log.d(TAG, stringBuilder.toString());
        }
        return this.mSocketIS.available();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    int bindListen() {
        Object object;
        int n;
        block28 : {
            Object object2;
            block27 : {
                if (this.mSocketState == SocketState.CLOSED) {
                    return 77;
                }
                object2 = BluetoothAdapter.getDefaultAdapter().getBluetoothService(null);
                if (object2 == null) {
                    Log.e(TAG, "bindListen fail, reason: bluetooth is off");
                    return -1;
                }
                if (DBG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("bindListen(): mPort=");
                    ((StringBuilder)object).append(this.mPort);
                    ((StringBuilder)object).append(", mType=");
                    ((StringBuilder)object).append(this.mType);
                    Log.d(TAG, ((StringBuilder)object).toString());
                }
                this.mPfd = object2.getSocketManager().createSocketChannel(this.mType, this.mServiceName, this.mUuid, this.mPort, this.getSecurityFlags());
                if (!DBG) break block27;
                object = new StringBuilder();
                ((StringBuilder)object).append("bindListen(), SocketState: ");
                ((StringBuilder)object).append((Object)this.mSocketState);
                ((StringBuilder)object).append(", mPfd: ");
                ((StringBuilder)object).append(this.mPfd);
                Log.d(TAG, ((StringBuilder)object).toString());
            }
            if (this.mSocketState != SocketState.INIT) {
                // MONITOREXIT : this
                return 77;
            }
            if (this.mPfd == null) {
                // MONITOREXIT : this
                return -1;
            }
            object = this.mPfd.getFileDescriptor();
            if (object == null) {
                Log.e(TAG, "bindListen(), null file descriptor");
                // MONITOREXIT : this
                return -1;
            }
            if (DBG) {
                Log.d(TAG, "bindListen(), Create LocalSocket");
            }
            this.mSocket = LocalSocket.createConnectedLocalSocket((FileDescriptor)object);
            if (DBG) {
                Log.d(TAG, "bindListen(), new LocalSocket.getInputStream()");
            }
            this.mSocketIS = this.mSocket.getInputStream();
            this.mSocketOS = this.mSocket.getOutputStream();
            // MONITOREXIT : this
            try {
                if (DBG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("bindListen(), readInt mSocketIS: ");
                    ((StringBuilder)object).append(this.mSocketIS);
                    Log.d(TAG, ((StringBuilder)object).toString());
                }
                n = this.readInt(this.mSocketIS);
                // MONITORENTER : this
                if (this.mSocketState != SocketState.INIT) break block28;
                this.mSocketState = SocketState.LISTENING;
            }
            catch (IOException iOException) {
                object2 = this.mPfd;
                if (object2 != null) {
                    try {
                        ((ParcelFileDescriptor)object2).close();
                    }
                    catch (IOException iOException2) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("bindListen, close mPfd: ");
                        ((StringBuilder)object2).append(iOException2);
                        Log.e(TAG, ((StringBuilder)object2).toString());
                    }
                    this.mPfd = null;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("bindListen, fail to get port number, exception: ");
                ((StringBuilder)object2).append(iOException);
                Log.e(TAG, ((StringBuilder)object2).toString());
                return -1;
            }
        }
        // MONITOREXIT : this
        if (DBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("bindListen(): channel=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", mPort=");
            ((StringBuilder)object).append(this.mPort);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if (this.mPort > -1) return 0;
        this.mPort = n;
        return 0;
        catch (RemoteException remoteException) {
            Log.e(TAG, Log.getStackTraceString(new Throwable()));
            return -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("close() this: ");
        stringBuilder.append(this);
        stringBuilder.append(", channel: ");
        stringBuilder.append(this.mPort);
        stringBuilder.append(", mSocketIS: ");
        stringBuilder.append(this.mSocketIS);
        stringBuilder.append(", mSocketOS: ");
        stringBuilder.append(this.mSocketOS);
        stringBuilder.append("mSocket: ");
        stringBuilder.append(this.mSocket);
        stringBuilder.append(", mSocketState: ");
        stringBuilder.append((Object)this.mSocketState);
        Log.d(TAG, stringBuilder.toString());
        if (this.mSocketState == SocketState.CLOSED) {
            return;
        }
        synchronized (this) {
            if (this.mSocketState == SocketState.CLOSED) {
                return;
            }
            this.mSocketState = SocketState.CLOSED;
            if (this.mSocket != null) {
                if (DBG) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Closing mSocket: ");
                    stringBuilder.append(this.mSocket);
                    Log.d(TAG, stringBuilder.toString());
                }
                this.mSocket.shutdownInput();
                this.mSocket.shutdownOutput();
                this.mSocket.close();
                this.mSocket = null;
            }
            if (this.mPfd != null) {
                this.mPfd.close();
                this.mPfd = null;
            }
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void connect() throws IOException {
        block13 : {
            block12 : {
                if (this.mDevice == null) throw new IOException("Connect is called on null device");
                try {
                    if (this.mSocketState == SocketState.CLOSED) ** GOTO lbl58
                    var1_1 = BluetoothAdapter.getDefaultAdapter().getBluetoothService(null);
                    if (var1_1 == null) ** GOTO lbl-1000
                    this.mPfd = var1_1.getSocketManager().connectSocket(this.mDevice, this.mType, this.mUuid, this.mPort, this.getSecurityFlags());
                    // MONITORENTER : this
                    if (!BluetoothSocket.DBG) break block12;
                    var1_1 = new StringBuilder();
                    var1_1.append("connect(), SocketState: ");
                }
                catch (RemoteException var3_4) {
                    Log.e("BluetoothSocket", Log.getStackTraceString(new Throwable()));
                    var1_1 = new StringBuilder();
                    var1_1.append("unable to send RPC: ");
                    var1_1.append(var3_4.getMessage());
                    throw new IOException(var1_1.toString());
                }
                var1_1.append((Object)this.mSocketState);
                var1_1.append(", mPfd: ");
                var1_1.append(this.mPfd);
                Log.d("BluetoothSocket", var1_1.toString());
            }
            if (this.mSocketState == SocketState.CLOSED) {
                var1_1 = new IOException("socket closed");
                throw var1_1;
            }
            if (this.mPfd == null) {
                var1_1 = new IOException("bt socket connect failed");
                throw var1_1;
            }
            this.mSocket = LocalSocket.createConnectedLocalSocket(this.mPfd.getFileDescriptor());
            this.mSocketIS = this.mSocket.getInputStream();
            this.mSocketOS = this.mSocket.getOutputStream();
            // MONITOREXIT : this
            var2_3 = this.readInt(this.mSocketIS);
            if (var2_3 <= 0) ** GOTO lbl-1000
            this.mPort = var2_3;
            this.waitSocketSignal(this.mSocketIS);
            // MONITORENTER : this
            if (this.mSocketState == SocketState.CLOSED) break block13;
            this.mSocketState = SocketState.CONNECTED;
            // MONITOREXIT : this
            return;
        }
        var1_1 = new IOException("bt socket closed");
        throw var1_1;
lbl-1000: // 1 sources:
        {
            var1_1 = new IOException("bt socket connect failed");
            throw var1_1;
        }
lbl-1000: // 1 sources:
        {
            var1_1 = new IOException("Bluetooth is off");
            throw var1_1;
lbl58: // 1 sources:
            var1_2 = new IOException("socket closed");
            throw var1_2;
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    @UnsupportedAppUsage
    void flush() throws IOException {
        if (this.mSocketOS != null) {
            if (VDBG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("flush: ");
                stringBuilder.append(this.mSocketOS);
                Log.d(TAG, stringBuilder.toString());
            }
            this.mSocketOS.flush();
            return;
        }
        throw new IOException("flush is called on null OutputStream");
    }

    public int getConnectionType() {
        int n = this.mType;
        if (n == 4) {
            return 3;
        }
        return n;
    }

    public InputStream getInputStream() throws IOException {
        return this.mInputStream;
    }

    public int getMaxReceivePacketSize() {
        return this.mMaxRxPacketSize;
    }

    public int getMaxTransmitPacketSize() {
        return this.mMaxTxPacketSize;
    }

    public OutputStream getOutputStream() throws IOException {
        return this.mOutputStream;
    }

    int getPort() {
        return this.mPort;
    }

    public BluetoothDevice getRemoteDevice() {
        return this.mDevice;
    }

    public boolean isConnected() {
        boolean bl = this.mSocketState == SocketState.CONNECTED;
        return bl;
    }

    int read(byte[] object, int n, int n2) throws IOException {
        StringBuilder stringBuilder;
        int n3;
        if (VDBG) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("read in:  ");
            stringBuilder.append(this.mSocketIS);
            stringBuilder.append(" len: ");
            stringBuilder.append(n2);
            Log.d(TAG, stringBuilder.toString());
        }
        if ((n3 = this.mType) != 3 && n3 != 4) {
            if (VDBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("default: read(): offset: ");
                stringBuilder.append(n);
                stringBuilder.append(" length:");
                stringBuilder.append(n2);
                Log.v(TAG, stringBuilder.toString());
            }
            n2 = this.mSocketIS.read((byte[])object, n, n2);
        } else {
            n3 = n2;
            if (VDBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("l2cap: read(): offset: ");
                stringBuilder.append(n);
                stringBuilder.append(" length:");
                stringBuilder.append(n2);
                stringBuilder.append("mL2capBuffer= ");
                stringBuilder.append(this.mL2capBuffer);
                Log.v(TAG, stringBuilder.toString());
            }
            if (this.mL2capBuffer == null) {
                this.createL2capRxBuffer();
            }
            if (this.mL2capBuffer.remaining() == 0) {
                if (VDBG) {
                    Log.v(TAG, "l2cap buffer empty, refilling...");
                }
                if (this.fillL2capRxBuffer() == -1) {
                    return -1;
                }
            }
            n2 = n3;
            if (n3 > this.mL2capBuffer.remaining()) {
                n2 = this.mL2capBuffer.remaining();
            }
            if (VDBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("get(): offset: ");
                stringBuilder.append(n);
                stringBuilder.append(" bytesToRead: ");
                stringBuilder.append(n2);
                Log.v(TAG, stringBuilder.toString());
            }
            this.mL2capBuffer.get((byte[])object, n, n2);
        }
        if (n2 >= 0) {
            if (VDBG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("read out:  ");
                ((StringBuilder)object).append(this.mSocketIS);
                ((StringBuilder)object).append(" ret: ");
                ((StringBuilder)object).append(n2);
                Log.d(TAG, ((StringBuilder)object).toString());
            }
            return n2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("bt socket closed, read return: ");
        ((StringBuilder)object).append(n2);
        throw new IOException(((StringBuilder)object).toString());
    }

    void removeChannel() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void requestMaximumTxDataLength() throws IOException {
        if (this.mDevice == null) {
            throw new IOException("requestMaximumTxDataLength is called on null device");
        }
        try {
            if (this.mSocketState == SocketState.CLOSED) {
                IOException iOException = new IOException("socket closed");
                throw iOException;
            }
            Object object = BluetoothAdapter.getDefaultAdapter().getBluetoothService(null);
            if (object == null) {
                object = new IOException("Bluetooth is off");
                throw object;
            }
            if (DBG) {
                Log.d(TAG, "requestMaximumTxDataLength");
            }
            object.getSocketManager().requestMaximumTxDataLength(this.mDevice);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, Log.getStackTraceString(new Throwable()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unable to send RPC: ");
            stringBuilder.append(remoteException.getMessage());
            throw new IOException(stringBuilder.toString());
        }
    }

    public void setExcludeSdp(boolean bl) {
        this.mExcludeSdp = bl;
    }

    void setServiceName(String string2) {
        this.mServiceName = string2;
    }

    int write(byte[] object, int n, int n2) throws IOException {
        StringBuilder stringBuilder;
        int n3;
        if (VDBG) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("write: ");
            stringBuilder.append(this.mSocketOS);
            stringBuilder.append(" length: ");
            stringBuilder.append(n2);
            Log.d(TAG, stringBuilder.toString());
        }
        if ((n3 = this.mType) != 3 && n3 != 4) {
            this.mSocketOS.write((byte[])object, n, n2);
        } else if (n2 <= this.mMaxTxPacketSize) {
            this.mSocketOS.write((byte[])object, n, n2);
        } else {
            int n4;
            if (DBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("WARNING: Write buffer larger than L2CAP packet size!\nPacket will be divided into SDU packets of size ");
                stringBuilder.append(this.mMaxTxPacketSize);
                Log.w(TAG, stringBuilder.toString());
            }
            n3 = n;
            for (n = n2; n > 0; n -= n4) {
                n4 = this.mMaxTxPacketSize;
                if (n <= n4) {
                    n4 = n;
                }
                this.mSocketOS.write((byte[])object, n3, n4);
                n3 += n4;
            }
        }
        if (VDBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("write out: ");
            ((StringBuilder)object).append(this.mSocketOS);
            ((StringBuilder)object).append(" length: ");
            ((StringBuilder)object).append(n2);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        return n2;
    }

    private static enum SocketState {
        INIT,
        CONNECTED,
        LISTENING,
        CLOSED;
        
    }

}

