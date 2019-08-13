/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;
import java.lang.reflect.Field;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import sun.misc.Unsafe;

public class InetSocketAddress
extends SocketAddress {
    private static final long FIELDS_OFFSET;
    private static final Unsafe UNSAFE;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = 5076001401234631237L;
    private final transient InetSocketAddressHolder holder;

    static {
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("hostname", String.class), new ObjectStreamField("addr", InetAddress.class), new ObjectStreamField("port", Integer.TYPE)};
        try {
            Unsafe unsafe = Unsafe.getUnsafe();
            FIELDS_OFFSET = unsafe.objectFieldOffset(InetSocketAddress.class.getDeclaredField("holder"));
            UNSAFE = unsafe;
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public InetSocketAddress() {
        this.holder = new InetSocketAddressHolder(null, null, 0);
    }

    public InetSocketAddress(int n) {
        this((InetAddress)null, n);
    }

    private InetSocketAddress(int n, String string) {
        this.holder = new InetSocketAddressHolder(string, null, n);
    }

    public InetSocketAddress(String string, int n) {
        InetSocketAddress.checkHost(string);
        InetAddress inetAddress = null;
        Object var4_4 = null;
        try {
            InetAddress inetAddress2;
            inetAddress = inetAddress2 = InetAddress.getByName(string);
            string = var4_4;
        }
        catch (UnknownHostException unknownHostException) {
            // empty catch block
        }
        this.holder = new InetSocketAddressHolder(string, inetAddress, InetSocketAddress.checkPort(n));
    }

    public InetSocketAddress(InetAddress inetAddress, int n) {
        if (inetAddress == null) {
            inetAddress = Inet6Address.ANY;
        }
        this.holder = new InetSocketAddressHolder(null, inetAddress, InetSocketAddress.checkPort(n));
    }

    private static String checkHost(String string) {
        if (string != null) {
            return string;
        }
        throw new IllegalArgumentException("hostname can't be null");
    }

    private static int checkPort(int n) {
        if (n >= 0 && n <= 65535) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("port out of range:");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static InetSocketAddress createUnresolved(String string, int n) {
        return new InetSocketAddress(InetSocketAddress.checkPort(n), InetSocketAddress.checkHost(string));
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField getField = ((ObjectInputStream)object).readFields();
        String string = (String)getField.get("hostname", null);
        object = (InetAddress)getField.get("addr", null);
        int n = getField.get("port", -1);
        InetSocketAddress.checkPort(n);
        if (string == null && object == null) {
            throw new InvalidObjectException("hostname and addr can't both be null");
        }
        object = new InetSocketAddressHolder(string, (InetAddress)object, n);
        UNSAFE.putObject(this, FIELDS_OFFSET, object);
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ObjectOutputStream.PutField putField = objectOutputStream.putFields();
        putField.put("hostname", this.holder.hostname);
        putField.put("addr", this.holder.addr);
        putField.put("port", this.holder.port);
        objectOutputStream.writeFields();
    }

    public final boolean equals(Object object) {
        if (object != null && object instanceof InetSocketAddress) {
            return this.holder.equals(((InetSocketAddress)object).holder);
        }
        return false;
    }

    public final InetAddress getAddress() {
        return this.holder.getAddress();
    }

    public final String getHostName() {
        return this.holder.getHostName();
    }

    public final String getHostString() {
        return this.holder.getHostString();
    }

    public final int getPort() {
        return this.holder.getPort();
    }

    public final int hashCode() {
        return this.holder.hashCode();
    }

    public final boolean isUnresolved() {
        return this.holder.isUnresolved();
    }

    public String toString() {
        return this.holder.toString();
    }

    private static class InetSocketAddressHolder {
        private InetAddress addr;
        private String hostname;
        private int port;

        private InetSocketAddressHolder(String string, InetAddress inetAddress, int n) {
            this.hostname = string;
            this.addr = inetAddress;
            this.port = n;
        }

        private InetAddress getAddress() {
            return this.addr;
        }

        private String getHostName() {
            Object object = this.hostname;
            if (object != null) {
                return object;
            }
            object = this.addr;
            if (object != null) {
                return ((InetAddress)object).getHostName();
            }
            return null;
        }

        private String getHostString() {
            Object object = this.hostname;
            if (object != null) {
                return object;
            }
            object = this.addr;
            if (object != null) {
                if (((InetAddress)object).holder().getHostName() != null) {
                    return this.addr.holder().getHostName();
                }
                return this.addr.getHostAddress();
            }
            return null;
        }

        private int getPort() {
            return this.port;
        }

        private boolean isUnresolved() {
            boolean bl = this.addr == null;
            return bl;
        }

        public final boolean equals(Object object) {
            boolean bl = false;
            if (object != null && object instanceof InetSocketAddressHolder) {
                object = (InetSocketAddressHolder)object;
                Object object2 = this.addr;
                boolean bl2 = object2 != null ? ((InetAddress)object2).equals(((InetSocketAddressHolder)object).addr) : ((object2 = this.hostname) != null ? ((InetSocketAddressHolder)object).addr == null && ((String)object2).equalsIgnoreCase(((InetSocketAddressHolder)object).hostname) : ((InetSocketAddressHolder)object).addr == null && ((InetSocketAddressHolder)object).hostname == null);
                boolean bl3 = bl;
                if (bl2) {
                    bl3 = bl;
                    if (this.port == ((InetSocketAddressHolder)object).port) {
                        bl3 = true;
                    }
                }
                return bl3;
            }
            return false;
        }

        public final int hashCode() {
            Object object = this.addr;
            if (object != null) {
                return ((InetAddress)object).hashCode() + this.port;
            }
            object = this.hostname;
            if (object != null) {
                return ((String)object).toLowerCase().hashCode() + this.port;
            }
            return this.port;
        }

        public String toString() {
            if (this.isUnresolved()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.hostname);
                stringBuilder.append(":");
                stringBuilder.append(this.port);
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.addr.toString());
            stringBuilder.append(":");
            stringBuilder.append(this.port);
            return stringBuilder.toString();
        }
    }

}

