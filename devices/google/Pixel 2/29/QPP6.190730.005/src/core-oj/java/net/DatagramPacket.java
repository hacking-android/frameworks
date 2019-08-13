/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public final class DatagramPacket {
    InetAddress address;
    byte[] buf;
    int bufLength;
    int length;
    int offset;
    int port;

    public DatagramPacket(byte[] arrby, int n) {
        this(arrby, 0, n);
    }

    public DatagramPacket(byte[] arrby, int n, int n2) {
        this.setData(arrby, n, n2);
        this.address = null;
        this.port = -1;
    }

    public DatagramPacket(byte[] arrby, int n, int n2, InetAddress inetAddress, int n3) {
        this.setData(arrby, n, n2);
        this.setAddress(inetAddress);
        this.setPort(n3);
    }

    public DatagramPacket(byte[] arrby, int n, int n2, SocketAddress socketAddress) {
        this.setData(arrby, n, n2);
        this.setSocketAddress(socketAddress);
    }

    public DatagramPacket(byte[] arrby, int n, InetAddress inetAddress, int n2) {
        this(arrby, 0, n, inetAddress, n2);
    }

    public DatagramPacket(byte[] arrby, int n, SocketAddress socketAddress) {
        this(arrby, 0, n, socketAddress);
    }

    public InetAddress getAddress() {
        synchronized (this) {
            InetAddress inetAddress = this.address;
            return inetAddress;
        }
    }

    public byte[] getData() {
        synchronized (this) {
            byte[] arrby = this.buf;
            return arrby;
        }
    }

    public int getLength() {
        synchronized (this) {
            int n = this.length;
            return n;
        }
    }

    public int getOffset() {
        synchronized (this) {
            int n = this.offset;
            return n;
        }
    }

    public int getPort() {
        synchronized (this) {
            int n = this.port;
            return n;
        }
    }

    public SocketAddress getSocketAddress() {
        synchronized (this) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(this.getAddress(), this.getPort());
            return inetSocketAddress;
        }
    }

    public void setAddress(InetAddress inetAddress) {
        synchronized (this) {
            this.address = inetAddress;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setData(byte[] object) {
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                try {
                    this.buf = object;
                    this.offset = 0;
                    this.length = ((byte[])object).length;
                    this.bufLength = ((byte[])object).length;
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new NullPointerException("null packet buffer");
                throw object;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setData(byte[] object, int n, int n2) {
        synchronized (this) {
            if (n2 >= 0 && n >= 0 && n2 + n >= 0 && n2 + n <= ((byte[])object).length) {
                this.buf = object;
                this.length = n2;
                this.bufLength = n2;
                this.offset = n;
                return;
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("illegal length or offset");
            throw illegalArgumentException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setLength(int n) {
        synchronized (this) {
            if (this.offset + n <= this.buf.length && n >= 0 && this.offset + n >= 0) {
                this.bufLength = this.length = n;
                return;
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("illegal length");
            throw illegalArgumentException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPort(int n) {
        synchronized (this) {
            if (n >= 0 && n <= 65535) {
                this.port = n;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Port out of range:");
            stringBuilder.append(n);
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
    }

    public void setReceivedLength(int n) {
        this.length = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setSocketAddress(SocketAddress serializable) {
        synchronized (this) {
            if (serializable != null && serializable instanceof InetSocketAddress) {
                if (!((InetSocketAddress)(serializable = (InetSocketAddress)serializable)).isUnresolved()) {
                    this.setAddress(((InetSocketAddress)serializable).getAddress());
                    this.setPort(((InetSocketAddress)serializable).getPort());
                    return;
                }
                serializable = new IllegalArgumentException("unresolved address");
                throw serializable;
            }
            serializable = new IllegalArgumentException("unsupported address type");
            throw serializable;
        }
    }
}

